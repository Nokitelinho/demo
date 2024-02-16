/*
 * HtmlExporter.java Created on 26-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import com.ibsplc.icargo.txprobe.tools.utils.Utils;
import com.ibsplc.icargo.txprobe.api.LogEntryFields;
import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataConstants;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.poi.util.IOUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			26-Apr-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class HtmlExporter implements Exporter, ProbeDataConstants {

    static final String[] COLORS = {"#FE2712", "#FC600A", "#FB9902", "#FCCC1A", "#FEFE33", "#B2D732", "#66B032", "#347C98", "#0247FE", "#4424D6", "#8601AF", "#C21460" };
    static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    /* (non-Javadoc)
     * @see com.ibsplc.icargo.txprobe.aggregator.tools.es.Exporter#doExport(java.io.File, java.util.List)
     */
    @Override
    public void doExport(File dir, List<ProbeData> datas) throws Exception {
        ProbeData first = datas.get(0);
        File file = new File(dir, first.getCorrelationId() + ".txprobe.html");
        System.out.println("Writing file : " + file.getAbsolutePath());
        BufferedWriter bw = prepHtmlFile(file);
        bw.append("<body>\n");
        Map<String, String> nodeColorMap = new HashMap<>(COLORS.length);
        for (int x = 0; x < datas.size(); x++) {
            ProbeData data = datas.get(x);
            String nodeName = data.getNodeName() == null ? "na" : data.getNodeName();
            String nodeColor = nodeColorMap.computeIfAbsent(nodeName, node -> {
              int size = nodeColorMap.size();
              return size >= COLORS.length ? "#000000" : COLORS[size];
            });
            writeEntry(bw, data, x, nodeColor);
        }
        bw.append("</body></html>\n");
        bw.flush();
        bw.close();
    }

    protected void writeEntry(BufferedWriter bw, ProbeData data, int idx, String nodeColor) throws Exception {
        String timestamp = sdf.format(new Date(data.getStartTime()));
        String idStr = "chkbox_" + idx;
        bw.append("<label class=\"collapse-label\" style=\"color:").append(data.isSuccess() ? "blue" : "red")
                .append("\" for=\"").append(idStr).append("\">");
        bw.append(timestamp).append(" - ");
        if (data.getNodeName() != null)
            bw.append("<span style=\"color:").append(nodeColor).append("\">").append(data.getNodeName()).append("</span> - ");
        bw.append(data.getProbe().toString()).append(" [ ").append(createSummaryDesc(data)).append(" ]").append("</label>\n");
        bw.append("<input id=\"").append(idStr).append("\" type=\"checkbox\" onclick=\"toggleColor(this)\"/>");
        bw.write("<div><table class=\"table-striped table-hover\">\n");
        writeEntry(bw, START_TIME, timestamp);
        writeEntry(bw, TENANT, data.getTenant());
        writeEntry(bw, SERVICE_APP_NAME, data.getServiceName());
        writeEntry(bw, PROBE_TYPE, data.getProbe());
        writeEntry(bw, ELASPSED_TIME, data.getElapsedTime());
        writeEntry(bw, SUCCESS, data.isSuccess());
        writeEntry(bw, ERROR_TYPE, data.getMiscellaneousAttributes().get(ERROR_TYPE));
        writeEntry(bw, USER, data.getUser());
        writeEntry(bw, NODE_NAME, data.getNodeName());
        writeEntry(bw, THREAD_NAME, data.getThreadName());
        writeEntry(bw, INVOCATIONID, data.getInvocationId());
        writeEntry(bw, SEQUENCE, data.getSequence());

        writeEntry(bw, TOPIC_NAME, data.getTopic());
        writeEntry(bw, URL, data.getMiscellaneousAttributes().get(URL));
        writeEntry(bw, MODULE, data.getMiscellaneousAttributes().get(MODULE));
        writeEntry(bw, SUBMODULE, data.getMiscellaneousAttributes().get(SUBMODULE));
        writeEntry(bw, ACTION, data.getMiscellaneousAttributes().get(ACTION));
        writeEntry(bw, SOAPACTION, data.getMiscellaneousAttributes().get(SOAPACTION));
        writeEntry(bw, INTERFACESYS, data.getMiscellaneousAttributes().get(INTERFACESYS));
        writeEntry(bw, JSESSIONID, data.getMiscellaneousAttributes().get(JSESSIONID));
        writeFmtEntry(bw, LOGON_ATTRIBUTES, data.getMiscellaneousAttributes().get(LOGON_ATTRIBUTES), "json");
        List<String> exclusions = Arrays.asList(URL, MODULE, SUBMODULE, ACTION, SOAPACTION, INTERFACESYS, JSESSIONID, LOGON_ATTRIBUTES, ERROR_TYPE);
        for (Map.Entry<String, String> e : data.getMiscellaneousAttributes().entrySet()) {
            if (exclusions.contains(e.getKey()))
                continue;
            writeEntry(bw, e.getKey(), e.getValue());
        }
        writeFmtEntry(bw, REQ_HEADERS, data.getRequestHeaders(), null);
        String type = Probe.SQL == data.getProbe() ? "sql" : null;
        writeFmtEntry(bw, REQ_BODY, data.getRequest(), type);
        writeFmtEntry(bw, ERROR, data.getError(), "nohighlight");
        writeFmtEntry(bw, RES_HEADERS, data.getResponseHeaders(), null);
        writeFmtEntry(bw, RES_BODY, data.getResponse(), null);

        bw.write("</table></div>\n");
    }

    private String createSummaryDesc(ProbeData pd) {
        switch (pd.getProbe()) {
            case HTTP:
                return pd.getMiscellaneousAttributes().get(URL);
            case INTERFACE_MESSAGE:
                return pd.getMiscellaneousAttributes().get(INTERFACESYS);
            case SERVICE:
                return new StringBuilder().append(pd.getMiscellaneousAttributes().get(MODULE))
                        .append('.').append(pd.getMiscellaneousAttributes().get(SUBMODULE))
                        .append('.').append(pd.getMiscellaneousAttributes().get(ACTION)).toString();
            case SQL:
                try {
                    Map<String, String> md = Utils.parseJsonFields(pd.getRequestHeaders(), "connectionId", "type", "method");
                    return new StringBuilder().append(md.get("type")).append('.')
                            .append(md.get("method"))
                            .append("  (").append(md.get("connectionId")).append(")").toString();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return "";
            case WEBSERVICE_HTTP:
            case WEBSERVICE_JMS:
                String sa = pd.getMiscellaneousAttributes().get(SOAPACTION);
                if (sa == null)
                    sa = pd.getMiscellaneousAttributes().get(URL);
                return sa;
            case LOG:
                return new StringBuilder().append(pd.getMiscellaneousAttributes().get(LogEntryFields.LLEVEL))
                        .append(" - ").append(pd.getMiscellaneousAttributes().get(LogEntryFields.LLOGGER)).toString();
            case KAFKA:
                return pd.getTopic();
            default:
                break;
        }
        return "";
    }

    private void writeFmtEntry(BufferedWriter bw, String key, Object value, String type) throws IOException {
        if (value == null)
            return;
        if (TextExporter.isJson(value.toString())) {
            try {
                value = Utils.formatJson(value.toString());
                type = "json";
            } catch (Exception e) {
            }
        }
        if ("xml".equals(type) || (type == null && TextExporter.isXml(value.toString()))) {
            type = "xml";
            String valueStr = Utils.formalXml(value.toString()).toString();
            value = StringEscapeUtils.escapeHtml4(valueStr);
        }
        if ("sql".equals(type)) {
            value = Utils.formatSql(value.toString().trim());
        }
        bw.append("<tr><td><b>").append(key).append("</b></td>\n");
        bw.append("<td><pre><code");
        if (type != null) {
            bw.append(" class=\"").append(type).append("\">");
        } else
            bw.append(">");
        bw.append(value.toString()).append("</code></pre></td></tr>\n");
    }

    private void writeEntry(BufferedWriter bw, String key, Object value) throws IOException {
        if (value == null)
            return;
        bw.append("<tr><td><b>").append(key).append("</b></td>\n");
        bw.append("<td><b>").append(value.toString()).append("</b></td></tr>\n");
    }

    protected BufferedWriter prepHtmlFile(File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        InputStream is = HtmlExporter.class.getResourceAsStream("res/export.template.html");
        IOUtils.copy(is, fos);
        fos.flush();
        fos.close();
        FileWriter fw = new FileWriter(file, true);
        BufferedWriter bw = new BufferedWriter(fw);
        is.close();
        return bw;
    }

}
