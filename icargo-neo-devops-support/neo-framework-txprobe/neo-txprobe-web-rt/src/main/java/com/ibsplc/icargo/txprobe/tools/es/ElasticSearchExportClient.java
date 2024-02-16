/*
 * ElasticSearchExportClient.java Created on 22-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import com.beust.jcommander.JCommander;
import com.ibsplc.icargo.txprobe.api.LogEntryFields;
import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataConstants;
import com.ibsplc.icargo.txprobe.tools.Command;
import com.ibsplc.icargo.txprobe.tools.utils.ElasticSearchUtils;
import com.ibsplc.icargo.txprobe.tools.utils.Utils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.index.query.MultiMatchQueryBuilder.Type;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			22-Apr-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class ElasticSearchExportClient implements Command, ProbeDataConstants, ConnectionProvider, LogEntryFields {

    static final Logger logger = LoggerFactory.getLogger(ElasticSearchExportClient.class);
    public static final char NOT_OPR = '!';

    private final String indexName = System.getProperty("es.indexName", "icargo_txprobe_index-*");
    private final String apiIndexName = System.getProperty("es.apiIndexName", "icargo_txprobe_api_index-*");
    private final String logIndexName = System.getProperty("es.logIndexName", "icargo_log_index-*");
    private final int maxFetchSize = Integer.getInteger("es.maxFetchSize", 1000);
    private final int maxDepthSize = Integer.getInteger("es.maxDepthSize", 10000);

    private SimpleDateFormat esDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private SimpleDateFormat esDateFormatLocal = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    private SimpleDateFormat esStrictDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
    private SimpleDateFormat formDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    {
        esDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        esStrictDateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        esDateFormatLocal.setTimeZone(TimeZone.getDefault());
    }

    private final Comparator<ProbeData> eventSequenceComparator = Comparator.comparingLong(ProbeData::getStartTime).thenComparingInt(ProbeData::getSequence);

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.Command#name()
     */
    @Override
    public String name() {
        return "export";
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.Command#usage()
     */
    @Override
    public String usage() {
        OptionsBean bean = new OptionsBean();
        JCommander commander = new JCommander(bean);
        StringBuilder sbul = new StringBuilder();
        commander.usage(sbul);
        sbul.append("\n Specify connectionUrl using env argument -Des.serverUrl=estc://host1:9300,host2:9300");
        sbul.append("\n indexName -Des.indexName , maxFetchSize -Des.maxFetchSize");
        sbul.append("\n </SparC>");
        return sbul.toString();
    }

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.Command#execute(java.lang.String[])
     */
    @Override
    public void execute(String[] args) throws Exception {
        OptionsBean options = parseArgs(args);
        if (options.isHelp) {
            System.err.println(usage());
            return;
        }
        String serverUrl = System.getProperty("es.serverUrl");
        if (serverUrl == null || serverUrl.isEmpty())
            throw new IllegalArgumentException("elastic search url missing ( specify -Des.serverUrl=estc://host1:9300,host2:9300 )");
        RestHighLevelClient conn = connect(serverUrl);
        Map<String, Long> uuids = null;
        if (options.getCorrelationId() == null)
            uuids = executeAndMap(conn, options);
        else {
            uuids = new HashMap<>(2);
            uuids.put(options.getCorrelationId().trim(), Long.valueOf(0));
        }
        if (uuids.isEmpty()) {
            logger.debug("no records fetched.");
            return;
        }
        Map<String, List<ProbeData>> hitMap = fetchForUuids(conn, uuids, false);
        showSummary(hitMap, true);
        final File dir = options.getOutputDirectory() == null ? new File(System.getProperty("user.dir")) : new File(options.getOutputDirectory());
        final Exporter exporter = resolveExporter(options.getFormat());
        hitMap.forEach((k, v) -> {
                    try {
                        exporter.doExport(dir, v);
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
        );
    }

    public Exporter resolveExporter(String fmt) {
        switch (fmt) {
            case "txt":
                return new TextExporter();
            case "xls":
                return new XLSXExporter();
            case "html":
                return new HtmlExporter();
            default:
                throw new IllegalArgumentException("invalid format : " + fmt);
        }
    }

    public Map<String, List<ProbeData>> fetchForUuids(RestHighLevelClient conn, Map<String, Long> uuids, boolean partial) {
        Map<String, List<ProbeData>> hitMap = new HashMap<>(uuids.size());
        uuids.forEach((uuid, depth) -> {
            try {
                List<ProbeData> pd = fetchForUuid(conn, uuid, partial);
                if (pd.isEmpty())
                    logger.error("unable to fetch data for correlationId : {}", uuid);
                else
                    hitMap.put(uuid, pd);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return hitMap;
    }

    @SuppressWarnings("resource")
    public List<String[]> showSummary(Map<String, List<ProbeData>> hitMap, boolean print) {
        StringBuilder sbul = new StringBuilder(1000);
        String formatDefn = "| %1$-23s | %2$-36s | %3$-17s | %4$-70s |\n";
        Formatter fmt = new Formatter(sbul);
        fmt.format(formatDefn, "timestamp", "correlationId", "probeType", "operation");
        fmt.flush();
        char[] line = new char[sbul.length()];
        Arrays.fill(line, '-');
        line[line.length - 1] = '\n';
        sbul.append(line);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // sort the entries based on timestamp
        List<String> sorted = new ArrayList<>(hitMap.keySet());
        Comparator<String> c = (s1, s2) -> {
            long l1 = hitMap.get(s1).get(0).getStartTime();
            long l2 = hitMap.get(s2).get(0).getStartTime();
            return Long.compare(l2, l1); // make it desc
        };
        Collections.sort(sorted, c);
        List<String[]> rows = new ArrayList<>(sorted.size());
        sorted.forEach(s -> {
            ProbeData first = hitMap.get(s).get(0);
            String ts = sdf.format(new Date(first.getStartTime()));
            String op = createShortDesc(first);
            if (print)
                fmt.format(formatDefn, ts, s, first.getProbe(), op);
            rows.add(new String[]{ts, s, first.getProbe().toString(), op});
        });
        if (print) {
            fmt.flush();
            fmt.close();
            System.out.println("\nShowing results which matches the filter criteria :\n");
            System.out.println(sbul.toString());
            System.out.println();
        }
        return rows;
    }

    @SuppressWarnings("resource")
    public List<Map.Entry<ErrorSeverity, Object[]>> showDetailedSummary(Map<String, List<ProbeData>> hitMap, Map<String, Long> uuidMap, boolean print) {
        StringBuilder sbul = new StringBuilder(1000);
        String formatDefn = "| %1$-23s | %2$-36s | %3$-17s | %4$-25s | %5$-25s | %6$-6s | %7$-4s | %8$-6s | %9$-70s |\n";
        Formatter fmt = new Formatter(sbul);
        fmt.format(formatDefn, "timestamp", "correlationId", "probeType", "user", "nodeName", "elaspsedTime", "depth", "status", "operation");
        fmt.flush();
        char[] line = new char[sbul.length()];
        Arrays.fill(line, '-');
        line[line.length - 1] = '\n';
        sbul.append(line);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        // sort the entries based on timestamp
        List<String> sorted = new ArrayList<>(hitMap.keySet());
        Comparator<String> c = (s1, s2) -> {
            long l1 = hitMap.get(s1).get(0).getStartTime();
            long l2 = hitMap.get(s2).get(0).getStartTime();
            return Long.compare(l2, l1); // make it desc
        };
        Collections.sort(sorted, c);
        List<Map.Entry<ErrorSeverity, Object[]>> rows = new ArrayList<>(sorted.size());
        sorted.forEach(s -> {
            List<ProbeData> pds = hitMap.get(s);
            ProbeData first = pds.get(0);
            String user = first.getUser();
            if (pds.size() > 1)
                user = pds.get(1).getUser();
            String ts = sdf.format(new Date(first.getStartTime()));
            String op = createShortDesc(first);
            int elapsedTime = first.getElapsedTime();
            // for async txns
            if (Probe.INTERFACE_MESSAGE == first.getProbe() && pds.size() > 1)
                elapsedTime = pds.get(1).getElapsedTime();
            ErrorSeverity sev = pds.stream().map(p -> resolveErrorSeverity(p)).reduce(ErrorSeverity.GREEN, (i, n) -> i.compareTo(n) <= 0 ? n : i);
            if (print)
                fmt.format(formatDefn, ts, s, first.getProbe(), user, first.getNodeName(), elapsedTime, uuidMap.get(s).intValue(), sev, op);
            Object[] value = new Object[]{ts, s, first.getProbe().toString(), user, first.getNodeName(),
                    elapsedTime, uuidMap.get(s).intValue(), op};
            rows.add(new EntryImpl(sev, value));
        });
        if (print) {
            fmt.flush();
            fmt.close();
            System.out.println("\nShowing results which matches the filter criteria :\n");
            System.out.println(sbul.toString());
            System.out.println();
        }
        return rows;
    }

    /**
     * @author A-2394
     * A severity representation of the transaction errors.
     */
    public enum ErrorSeverity {GREEN, YELLOW, RED}

    /**
     * @author A-2394
     */
    protected static class EntryImpl implements Map.Entry<ErrorSeverity, Object[]> {

        public EntryImpl(ErrorSeverity key, Object[] value) {
            super();
            this.key = key;
            this.value = value;
        }

        final ErrorSeverity key;
        final Object[] value;

        @Override
        public ErrorSeverity getKey() {
            return this.key;
        }

        @Override
        public Object[] getValue() {
            return this.value;
        }

        @Override
        public Object[] setValue(Object[] value) {
            throw new UnsupportedOperationException("frozen");
        }

    }

    protected ErrorSeverity resolveErrorSeverity(ProbeData data) {
        if (data.isSuccess())
            return ErrorSeverity.GREEN;
        if (data.getProbe() == Probe.SERVICE) {
            String errorType = Optional.ofNullable(data.getMiscellaneousAttributes()).orElse(Collections.emptyMap())
                    .getOrDefault(ProbeDataConstants.ERROR_TYPE, "RTE");
            return "BSE".equals(errorType) ? ErrorSeverity.YELLOW : ErrorSeverity.RED;
        }
        return ErrorSeverity.RED;
    }

    private String createShortDesc(ProbeData first) {
        String op = first.getMiscellaneousAttributes().get(URL);
        if (op == null && first.getMiscellaneousAttributes().get(SOAPACTION) != null)
            op = first.getMiscellaneousAttributes().get(SOAPACTION);
        if (op == null && first.getMiscellaneousAttributes().get(ACTION) != null) {
            op = new StringBuilder().append(first.getMiscellaneousAttributes().get(MODULE))
                    .append('.').append(first.getMiscellaneousAttributes().get(SUBMODULE))
                    .append('.').append(first.getMiscellaneousAttributes().get(ACTION)).toString();
        }
        if (first.getProbe() == Probe.HTTP && op != null) {
            try {
                String screenId = Utils.parseJsonFields(first.getRequest(), "numericalScreenId").get("numericalScreenId");
                if (screenId != null)
                    op = new StringBuilder(op.length() + 10).append(screenId).append(" - ").append(op).toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (first.getProbe() == Probe.INTERFACE_MESSAGE) {
            String infsys = first.getMiscellaneousAttributes().get(INTERFACESYS);
            StringBuilder desc = new StringBuilder();
            desc.append('[').append(infsys).append(']');
            String msg = first.getRequest();
            if (msg != null) {
                msg = msg.replaceAll("[\r\n]", " ");
                if (msg.length() > 60)
                    msg = msg.substring(0, 60);
                desc.append(" - ").append(msg);
            }
            op = desc.toString();
        }
        op = op == null ? "-" : op;
        return op;
    }

    public List<ProbeData> fetchForUuid(RestHighLevelClient conn, String uuid, boolean partial)
            throws IOException {
        BoolQueryBuilder qbul = QueryBuilders.boolQuery();
        applyTermPredicate(qbul, CORRELATION_ID, uuid);
        QueryBuilder finalQuery = null;
        SearchSourceBuilder sbul = new SearchSourceBuilder();
        SearchRequest req = new SearchRequest();
        req.source(sbul);
        if (partial) {
            req.indices(indexName, apiIndexName);
            qbul.should(QueryBuilders.termsQuery(SEQUENCE, Arrays.asList(0, 1))).should(QueryBuilders.termQuery(SUCCESS, false));
            finalQuery = QueryBuilders.constantScoreQuery(qbul);
        } else {
            req.indices(logIndexName, indexName, apiIndexName);
            finalQuery = qbul;
        }
        sbul.query(finalQuery);
        sbul.size(maxDepthSize);
        if (partial)
            sbul.fetchSource(null, new String[]{REQ_HEADERS, RES_BODY, RES_HEADERS, ERROR, LOGON_ATTRIBUTES});
        SearchResponse resp = conn.search(req, RequestOptions.DEFAULT);
        List<ProbeData> answer = mapSearchHits(resp);
        // remove the request body in case of partial - hack to prevent OOM
        final String EMPTY_JSON = "{}";
        if (partial)
            answer.stream().filter(pd -> pd.getProbe() == Probe.SERVICE).forEach(pd -> pd.setRequest(EMPTY_JSON));
        return answer;
    }

    private List<ProbeData> mapSearchHits(SearchResponse resp) {
        List<ProbeData> datas = new ArrayList<>((int) resp.getHits().getTotalHits().value);
        for (SearchHit hit : resp.getHits().getHits()) {
            Map<String, Object> cols = hit.getSourceAsMap();
            String probeType = cols.get(PROBE_TYPE).toString();
            ProbeData data = new ProbeData();
            if (Probe.LOG.toString().equals(probeType)) {
                try {
                    mapLogEntry(data, cols);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            } else {
                cols.forEach((k, v) -> {
                    if (START_TIME.equals(k)) {
                        try {
                            Date startTime = esDateFormat.parse(v.toString());
                            v = startTime.getTime();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                    data.mapField(k, v);
                });
            }
            datas.add(data);
        }
        Collections.sort(datas, eventSequenceComparator);
        return datas;
    }

    private void mapLogEntry(ProbeData data, Map<String, Object> cols) throws Exception {
        Date startTime = esDateFormat.parse(cols.remove(LTIMESTAMP).toString());
        data.setStartTime(startTime.getTime());
        data.setThreadName((String) cols.remove(LTHREADID));
        data.setError((String) cols.remove(LSTACKTRACE));
        String nodeName = (String)cols.remove(NODE_NAME);
        if(nodeName == null)
            data.setNodeName((String) cols.remove(LHOST));
        else
            data.setNodeName(nodeName);
        cols.remove(LLEVEL_VALUE);
        String level = cols.get(LLEVEL).toString();
        data.setSuccess(!"ERROR".equals(level) && !"WARN".equals(level));
        for (Map.Entry<String, Object> e : cols.entrySet())
            data.mapField(e.getKey(), e.getValue());
    }


    public Map<String, Long> executeAndMap(RestHighLevelClient conn, OptionsBean options) throws Exception {
        QueryBuilder qbul = createQuery(options);
        SearchSourceBuilder sbul = new SearchSourceBuilder();
        //sbul.addField(CORRELATION_ID);
        sbul.query(qbul);
        final String aggName = "corrIdCount";
        TermsAggregationBuilder aggbul = AggregationBuilders.terms(aggName).field(CORRELATION_ID);
        aggbul.size(maxFetchSize);
        sbul.aggregation(aggbul);
        sbul.size(0);
        //sbul.searchType(SearchType.QUERY_THEN_FETCH);
        //sbul.addSort(START_TIME, SortOrder.ASC);

        logger.debug("query : {}", sbul);
        SearchRequest req = new SearchRequest(indexName, apiIndexName);
        req.source(sbul);
        SearchResponse resp = conn.search(req, RequestOptions.DEFAULT);
        long hits = resp.getHits().getTotalHits().value;
        logger.debug("documents matched  : {}", hits);
        if (hits == 0)
            return Collections.emptyMap();
        Terms terms = resp.getAggregations().get(aggName);
        List<? extends Terms.Bucket> corrBucket = terms.getBuckets();
        logger.debug("number of transactions : {}", corrBucket.size());
        Map<String, Long> hitMap = new HashMap<>(corrBucket.size());
        for (Terms.Bucket corrId : corrBucket) {
            if (options.getDepth() < corrId.getDocCount())
                hitMap.put(corrId.getKeyAsString(), corrId.getDocCount());
        }
        return hitMap;
    }

    private QueryBuilder createQuery(OptionsBean options) throws ParseException {
        BoolQueryBuilder qbul = QueryBuilders.boolQuery();

        applyTermPredicate(qbul, CORRELATION_ID, options.getCorrelationId());
        applyTermPredicate(qbul, INVOCATIONID, options.getInvocationId());
        applyTermPredicate(qbul, NODE_NAME, options.getNodeName());
        applyTermPredicate(qbul, USER, options.getUser());
        applyTermPredicate(qbul, MODULE, options.getModule());
        applyTermPredicate(qbul, SUBMODULE, options.getSubmodule());
        applyTermPredicate(qbul, ERROR_TYPE, options.getErrorType());
        applyTermsPredicate(qbul, PROBE_TYPE, false, Probe.JVM.toString(), Probe.LOG.toString());

        if (options.getProbeType() != null)
            applyTermPredicate(qbul, PROBE_TYPE, options.getProbeType().toUpperCase());

        if (options.getStartDate() != null || options.getEndDate() != null) {
            RangeQueryBuilder frag = QueryBuilders.rangeQuery(START_TIME);
            frag.format("strict_date_hour_minute");
            if (options.getStartDate() != null)
                frag.from(toESDate(options.getStartDate()));
            if (options.getEndDate() != null)
                frag.to(toESDate(options.getEndDate()));
            qbul.must(frag);
        }
        if (options.isErrorsOnly()) {
            TermQueryBuilder frag = QueryBuilders.termQuery(SUCCESS, false);
            qbul.must(frag);
        }
        if (options.getSearch() != null) {
            String search = options.getSearch();
            boolean orOpr = search.contains("|");
            search = search.replaceAll("\\W", " ");
            QueryStringQueryBuilder mbul = QueryBuilders.queryStringQuery(search)
                    .analyzeWildcard(true).defaultField("*")
                    .defaultOperator(orOpr ? Operator.OR : Operator.AND);

            mbul.type(Type.CROSS_FIELDS);
			/*MatchQueryBuilder mbul = QueryBuilders.matchQuery("_all", search);
			mbul.operator(orOpr ? Operator.OR : Operator.AND);*/
            qbul.must(mbul);
        }
        return qbul;
    }

    private Object toESDate(String dateStr) throws ParseException {
        Date date = formDateFormat.parse(dateStr);
        return esStrictDateTimeFormat.format(date);
    }

    private void applyTermsPredicate(BoolQueryBuilder parent, String field, boolean must, String... terms) {
        if (terms == null || terms.length == 0)
            return;
        terms = Arrays.stream(terms).map(String::trim).toArray(String[]::new);
        QueryBuilder frag;
        if (terms.length == 1)
            frag = QueryBuilders.termQuery(field, terms[0]);
        else
            frag = QueryBuilders.termsQuery(field, terms);
        if (must)
            parent.must(frag);
        else
            parent.mustNot(frag);
    }

    private void applyTermPredicate(BoolQueryBuilder parent, String field, String term) {
        if (term == null)
            return;
        term = term.trim();
        boolean must = true;
        if (term.charAt(0) == NOT_OPR) {
            term = term.substring(1);
            must = false;
        }
        TermQueryBuilder frag = QueryBuilders.termQuery(field, term);
        if (must)
            parent.must(frag);
        else
            parent.mustNot(frag);
    }

    /**
     * establish connection with the cluster
     *
     * @throws Exception
     */
    public RestHighLevelClient connect(String serverUrl) throws Exception {
        return ElasticSearchUtils.createClient(serverUrl);
    }

    private OptionsBean parseArgs(String[] args) throws Exception {
        OptionsBean bean = new OptionsBean();
        JCommander commander = new JCommander(bean);
        if (args == null || args.length == 0) {
            System.err.println(usage());
            throw new IllegalStateException("please specify command arguments");
        }
        try {
            commander.parse(args);
        } catch (com.beust.jcommander.ParameterException e) {
            System.err.println("Malformed input : " + e.getMessage() + "\n");
            System.err.println(usage());
            System.exit(1);
        }
        if (bean.isHelp()) {
            System.err.println(usage());
            System.exit(1);
        }
        return bean;
    }

}
