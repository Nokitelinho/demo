/*
 * ElasticSearchIndexingHandler.java Created on 01-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound.es;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.ibsplc.icargo.txprobe.aggregator.handler.outbound.BackEndHandler;
import com.ibsplc.icargo.txprobe.api.FieldMetadata;
import com.ibsplc.icargo.txprobe.api.LogEntryFields;
import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeDataHolder;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.elasticsearch.ElasticsearchStatusException;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequest;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequest;
import org.elasticsearch.action.admin.indices.alias.get.GetAliasesRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.GetAliasesResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.health.ClusterHealthStatus;
import org.elasticsearch.cluster.metadata.AliasAction;
import org.elasticsearch.cluster.metadata.IndexMetadata;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			01-Feb-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
@Singleton
@Named("out.elasticSearchHandler")
public class ElasticSearchIndexingHandler implements BackEndHandler, Runnable {

    static final Logger logger = LoggerFactory.getLogger(ElasticSearchIndexingHandler.class);

    static final String TXPROBE_TEMPLATE = "icargo_txprobe_template";
    static final String LOG_TEMPLATE_NAME = "icargo_log_template";

    @ConfigProperty(name = "out.handler.es.serverUrl")
    String serverUrl;

    @ConfigProperty(name = "out.handler.es.transportWorkerCount", defaultValue = "4")
    int transportWorkerCount;

    @ConfigProperty(name = "out.handler.es.indexName", defaultValue = "icargo_txprobe_index")
    String indexName;

    @ConfigProperty(name = "out.handler.es.apiIndexName", defaultValue = "icargo_txprobe_api_index")
    String apiIndexName;

    @ConfigProperty(name = "out.handler.es.numberOfReplicas", defaultValue = "1")
    int numberOfReplicas;

    @ConfigProperty(name = "out.handler.es.numberOfShards", defaultValue = "1")
    int numberOfShards;

    @ConfigProperty(name = "out.handler.es.maxBatchSize", defaultValue = "500")
    int maxBatchSize;

    @ConfigProperty(name = "out.handler.es.refreshInterval", defaultValue = "25s")
    String refreshInterval;

    @ConfigProperty(name = "out.handler.es.partitionBy", defaultValue = "byHour")
    String partitionBy;// byHour or byDay

    @ConfigProperty(name = "out.handler.es.apiPartitionBy", defaultValue = "byDay")
    String apiPartitionBy;// byHour or byDay

    @ConfigProperty(name = "out.handler.es.translogDurability", defaultValue = "async")
    String translogDurability;

    @ConfigProperty(name = "out.handler.es.translogFlushInterval", defaultValue = "15s")
    String translogFlushInterval;

    @ConfigProperty(name = "out.handler.es.translogFlushSize", defaultValue = "512mb")
    String translogFlushSize;

    private final Object mutex = new Object();
    private RestHighLevelClient clientRef;
    private ElasticSearchHelper helper;
    private final AtomicBoolean isConnected = new AtomicBoolean(false);
    private ScheduledExecutorService schedulerService;
    private volatile boolean isDestroyed;
    private volatile boolean isInitialized;
    private RangeMap<Long, String> indexPartitionMap;
    private RangeMap<Long, String> apiIndexPartitionMap;
    private final Set<String> indexCreationRegistry = new HashSet<>(16);

    private BulkRequest bulkRequest;

    /* (non-Javadoc)
     * @see com.lmax.disruptor.EventHandler#onEvent(java.lang.Object, long, boolean)
     */
    @Override
    public void onEvent(ProbeDataHolder event, long sequence, boolean endOfBatch) throws Exception {
        RestHighLevelClient client = getClient();
        doIndex(client, event, endOfBatch);
    }

    protected void doIndex(RestHighLevelClient client, ProbeDataHolder event, boolean endOfBatch) throws Exception {
        boolean isExtApiLogging = event.getProbeData().isEnableApiLogging();
        String partitionedIndexName = resolveIndexName(event.getProbeData().getStartTime(), isExtApiLogging);
        /* create index if absent */
        if (!indexCreationRegistry.contains(partitionedIndexName))
            createIndex(partitionedIndexName, client);
        IndexRequest req = helper.createIndexRequest(partitionedIndexName, event.getProbeData());
        boolean flushBatch = bulkRequest != null && bulkRequest.numberOfActions() >= maxBatchSize;
        if (endOfBatch || flushBatch) {
            if (bulkRequest == null) {
                client.index(req, RequestOptions.DEFAULT);
                //client.indexAsync(req, RequestOptions.DEFAULT, NoopListenerImpl.INSTANCE);
            } else {
                bulkRequest.add(req);
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
                //client.bulkAsync(bulkRequest, RequestOptions.DEFAULT, NoopListenerImpl.INSTANCE);
                bulkRequest = null;
            }
        } else {
            if (bulkRequest == null)
                bulkRequest = new BulkRequest();
            bulkRequest.add(req);
        }
    }

    /**
     * A No-op listener impl
     */
    static class NoopListenerImpl implements ActionListener {

        static final NoopListenerImpl INSTANCE = new NoopListenerImpl();

        @Override
        public void onResponse(Object o) {
        }

        @Override
        public void onFailure(Exception e) {
        }
    }

    private RestHighLevelClient getClient() {
        while (!isConnected.get() && !isDestroyed) {
            synchronized (mutex) {
                try {
                    mutex.wait();
                } catch (InterruptedException e) {
                    // ignored
                }
            }
        }
        if (!isInitialized) {
            try {
                //initIndexAndMappings(this.clientRef);
                refreshTemplate(this.clientRef);
                this.isInitialized = true;
            } catch (Exception e) {
               logger.warn("Error in refreshTemplate", e);
            }
        }
        return this.clientRef;
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @PreDestroy
    public void destroy() throws IOException, InterruptedException{
        this.isDestroyed = true;
        this.schedulerService.shutdownNow();
        this.schedulerService.awaitTermination(5, TimeUnit.SECONDS);
        if (this.clientRef != null)
            this.clientRef.close();
    }

    /* (non-Javadoc)
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @PostConstruct
    public void afterPropertiesSet() {
        buildPartitionMap();
        this.helper = new ElasticSearchHelper(FieldMetadata.getFieldMetadata());
        final UncaughtExceptionHandler ueh = (t, e) -> logger.error("error thrown by thread : {}", t.getName(), e);
        ThreadFactory f = r -> {
            Thread t = new Thread(r);
            t.setUncaughtExceptionHandler(ueh);
            t.setName("elasticsearch-txprobe-health-checker");
            return t;
        };
        this.schedulerService = Executors.newSingleThreadScheduledExecutor(f);
        this.schedulerService.scheduleWithFixedDelay(this, 0L, 10, TimeUnit.SECONDS);
        try {
            RestHighLevelClient client = getClient(); // refresh the template and mappings
            createIndex(resolveIndexName(System.currentTimeMillis(), false), client);
            createIndex(resolveIndexName(System.currentTimeMillis(), true), client);
            getClient();
        } catch (IOException e) {
            logger.warn("IOException on init", e);
        }
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        if (this.isDestroyed)
            return;
        RestHighLevelClient client = this.clientRef;
        if (client == null) {
            try {
                client = ElasticSearchUtils.createClient(this.serverUrl);
                this.clientRef = client;
            } catch (Throwable e) {
                logger.error("error while connecting to elastic search", e);
                return;
            }
        }
        boolean isAvailable = isAvailable(client);
        if (isAvailable) {
            boolean changed = this.isConnected.compareAndSet(false, true);
            if (changed) {
                synchronized (mutex) {
                    mutex.notifyAll();
                }
            }
            return;
        } else
            this.isConnected.compareAndSet(true, false);
    }

    /**
     * Method to create index mappings dynamically.
     *
     * @throws IOException
     */
    protected void createIndex(String indexName, RestHighLevelClient client)
            throws IOException {
        GetIndexRequest indexRequest = new GetIndexRequest(indexName);
        boolean exists = client.indices().exists(indexRequest, RequestOptions.DEFAULT);
        if (exists) {
            logger.info("index present in the cluster : {}", indexName);
        } else {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(indexName);
            CreateIndexResponse createIndexResponse = client.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            logger.info("index creation status : {}", createIndexResponse.isAcknowledged());
            if (!createIndexResponse.isAcknowledged())
                throw new IllegalArgumentException("unable to create index mapping.");
            createIndexAliasMappingForExtIndex(client);
        }
        this.indexCreationRegistry.add(indexName);
    }

    protected void refreshTemplate(RestHighLevelClient client) throws IOException {
        Settings indexConf = Settings.builder()
                .put(IndexMetadata.SETTING_NUMBER_OF_REPLICAS, this.numberOfReplicas)
                .put(IndexMetadata.SETTING_NUMBER_OF_SHARDS, this.numberOfShards)
                .put("index.translog.durability", this.translogDurability)
                .put("index.translog.flush_threshold_size", this.translogFlushSize)
                .put("index.translog.sync_interval", this.translogFlushInterval)
                .put("index.refresh_interval", this.refreshInterval)
                .put("refresh_interval", this.refreshInterval).build();

        GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(TXPROBE_TEMPLATE);
        boolean createTemplate = true;
        try {
            GetIndexTemplatesResponse response = client.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
            if (response.getIndexTemplates() != null && !response.getIndexTemplates().isEmpty()) {
                createTemplate = false;
                logger.info("Index template present in the cluster : {}", TXPROBE_TEMPLATE);
            }
        } catch (ElasticsearchStatusException e) {
            logger.info("Index template not present in the cluster : {}", TXPROBE_TEMPLATE);
        }
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest(TXPROBE_TEMPLATE);
        putIndexTemplateRequest.patterns(List.of(this.indexName + "-*", this.apiIndexName + "-*"));
        putIndexTemplateRequest.mapping(helper.createMappingBody());
        putIndexTemplateRequest.create(createTemplate);
        putIndexTemplateRequest.settings(indexConf);
        logger.info("Put Template Request : \n {}", putIndexTemplateRequest);
        AcknowledgedResponse acknowledgedResponse = client.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
        logger.info("Index template refresh response : {}", acknowledgedResponse.isAcknowledged());
        refreshLogTemplate(client);
        createIndexAliasMappingForExtIndex(client);
    }

    protected void createIndexAliasMappingForExtIndex(RestHighLevelClient client) throws IOException {
        /*
        GetAliasesRequest request = new GetAliasesRequest("icargo_txprobe_all");
        boolean createAlias = true;
        try {
            GetAliasesResponse response = client.indices().getAlias(request, RequestOptions.DEFAULT);
            if (response.getAliases() != null && !response.getAliases().isEmpty()){
                createAlias = false;
            }
        }catch (ElasticsearchStatusException e){
            logger.info("Alias not present in the cluster", e);
        }*/
        IndicesAliasesRequest createRequest = new IndicesAliasesRequest();
        IndicesAliasesRequest.AliasActions actions = new IndicesAliasesRequest.AliasActions(IndicesAliasesRequest.AliasActions.Type.ADD);
        actions = actions.indices(this.apiIndexName + "-*", this.indexName + "-*").alias("icargo_txprobe_all");
        createRequest.addAliasAction(actions);
        AcknowledgedResponse response = client.indices().updateAliases(createRequest, RequestOptions.DEFAULT);
        logger.info("Update alias response {}", response.isAcknowledged());
    }

    protected void refreshLogTemplate(RestHighLevelClient client) throws IOException {
        Settings indexConf = Settings.builder()
                .put(IndexMetadata.SETTING_NUMBER_OF_REPLICAS, this.numberOfReplicas)
                .put(IndexMetadata.SETTING_NUMBER_OF_SHARDS, this.numberOfShards)
                .put("index.translog.durability", this.translogDurability)
                .put("index.translog.flush_threshold_size", this.translogFlushSize)
                .put("index.translog.sync_interval", this.translogFlushInterval)
                .put("index.refresh_interval", this.refreshInterval)
                .put("refresh_interval", this.refreshInterval).build();

        GetIndexTemplatesRequest request = new GetIndexTemplatesRequest(LOG_TEMPLATE_NAME);
        boolean createTemplate = true;
        try {
            GetIndexTemplatesResponse response = client.indices().getIndexTemplate(request, RequestOptions.DEFAULT);
            if (response.getIndexTemplates() != null && !response.getIndexTemplates().isEmpty()) {
                logger.info("index log template present in the cluster : {}", LOG_TEMPLATE_NAME);
                createTemplate = false;
            }
        } catch (ElasticsearchStatusException e) {
            logger.info("index log template not present in th the cluster : {}", LOG_TEMPLATE_NAME);
        }
        PutIndexTemplateRequest putIndexTemplateRequest = new PutIndexTemplateRequest(LOG_TEMPLATE_NAME);
        putIndexTemplateRequest.patterns(Collections.singletonList(LogEntryFields.INDEX_NAME + "-*"));
        putIndexTemplateRequest.mapping(helper.createLogMappingBody());
        putIndexTemplateRequest.create(createTemplate);
        putIndexTemplateRequest.settings(indexConf);
        logger.info("Put Log Template Request : \n {}", putIndexTemplateRequest);
        AcknowledgedResponse acknowledgedResponse = client.indices().putTemplate(putIndexTemplateRequest, RequestOptions.DEFAULT);
        logger.info("index log template refresh response : {}", acknowledgedResponse.isAcknowledged());
    }

    protected String resolveIndexName(final long time, boolean extApiLogging) {
        String answer = extApiLogging ? this.apiIndexPartitionMap.get(time) : this.indexPartitionMap.get(time);
        // spilled over
        if (answer == null) {
            buildPartitionMap();
            answer = extApiLogging ? this.apiIndexPartitionMap.get(time) : this.indexPartitionMap.get(time);
        }
        return answer;
    }

    private void buildPartitionMap() {
        if ("byHour".equals(this.partitionBy))
            this.indexPartitionMap = buildHourlyPartitionMap(this.indexName);
        else
            this.indexPartitionMap = buildDailyPartitionMap(this.indexName);
        if ("byHour".equals(this.apiPartitionBy))
            this.apiIndexPartitionMap = buildHourlyPartitionMap(this.apiIndexName);
        else
            this.apiIndexPartitionMap = buildDailyPartitionMap(this.apiIndexName);
        // clear the registry
        this.indexCreationRegistry.clear();
    }

    private static RangeMap<Long, String> buildHourlyPartitionMap(String indexName) {
        ImmutableRangeMap.Builder<Long, String> builder = ImmutableRangeMap.builder();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.MINUTE, 0);
        gcal.set(Calendar.SECOND, 0);
        gcal.set(Calendar.MILLISECOND, 0);
        gcal.add(Calendar.HOUR, -48);
        long time = gcal.getTimeInMillis();
        final int ONE_HOUR = 60 * 60 * 1000;
        final int ONE_TICK = 1;
        long from = time;
        DateFormat dfHour = new SimpleDateFormat("-yyyyMMddHH");
        for (int x = 0; x < 480; x++) {
            long to = from + ONE_HOUR - ONE_TICK;
            Date fromDate = new Date(from);
            builder.put(Range.closedOpen(from, to), indexName + dfHour.format(fromDate));
            from = to + ONE_TICK;
        }
        return builder.build();
    }

    private static RangeMap<Long, String> buildDailyPartitionMap(String indexName) {
        ImmutableRangeMap.Builder<Long, String> builder = ImmutableRangeMap.builder();
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.HOUR_OF_DAY, 0);
        gcal.set(Calendar.MINUTE, 0);
        gcal.set(Calendar.SECOND, 0);
        gcal.set(Calendar.MILLISECOND, 0);
        gcal.add(Calendar.DATE, -1);
        long time = gcal.getTimeInMillis();
        final int ONE_DAY = 24 * 60 * 60 * 1000;
        final int ONE_TICK = 1;
        long from = time;
        DateFormat dfday = new SimpleDateFormat("-yyyyMMdd");
        for (int x = 0; x < 16; x++) {
            long to = from + ONE_DAY - ONE_TICK;
            Date fromDate = new Date(from);
            builder.put(Range.closedOpen(from, to), indexName + dfday.format(fromDate));
            from = to + ONE_TICK;
        }
        return builder.build();
    }

    protected boolean isAvailable(RestHighLevelClient client) {
        try {
            // check the status of the ES cluster OK if yellow or green
            ClusterHealthResponse healthResponse;
            ClusterHealthRequest request = Requests.clusterHealthRequest();
            request.timeout(TimeValue.timeValueSeconds(5));
            client.ping(RequestOptions.DEFAULT);
            healthResponse = client.cluster().health(request, RequestOptions.DEFAULT);
            boolean answer = !ClusterHealthStatus.RED.equals(healthResponse.getStatus());
            if (!answer)
                logger.warn("cluster health for index {} is {}", indexName, healthResponse);
            return answer;
        } catch (Exception e) {
            logger.warn("error while pinging repository : " + indexName, e);
        }
        return false;
    }

}
