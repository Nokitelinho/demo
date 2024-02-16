/*
 * ProbeData.java Created on 28-Jan-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.api;

import java.util.HashMap;
import java.util.Map;

import static com.ibsplc.icargo.txprobe.api.ProbedState.*;


/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			28-Jan-2016       		Jens J P 			First Draft
 */

/**
 * @author A-2394
 */
public class ProbeData implements ProbeDataConstants {

    private Probe probe;
    private String correlationId;
    private String user;
    private long startTime;
    private int elapsedTime;
    private boolean success;
    private int sequence;
    private String error;
    private String requestHeaders;
    private String responseHeaders;
    private String request;
    private String response;
    private String invocationId;
    private String nodeName;
    private String threadName;
    private boolean merged;
    private boolean isLast;
    private int docSize;
    private boolean incoming;
    private int sqlRowCount;
    private boolean sqlResponse;
    private String tenant;
    private String serviceName;
    private String topic;
    private boolean enableApiLogging;
    private Map<String, String> miscellaneousAttributes = new HashMap<>(8);

    public void clear() {
        this.probe = null;
        this.correlationId = null;
        this.user = null;
        this.startTime = 0L;
        this.elapsedTime = 0;
        this.success = false;
        this.sequence = 0;
        this.error = null;
        this.requestHeaders = null;
        this.responseHeaders = null;
        this.request = null;
        this.response = null;
        this.invocationId = null;
        this.nodeName = null;
        this.merged = false;
        this.isLast = false;
        this.threadName = null;
        this.docSize = 0;
        this.incoming = false;
        this.sqlRowCount = 0;
        this.sqlResponse = false;
        this.tenant = null;
        this.serviceName = null;
        this.topic = null;
        this.enableApiLogging = false;
        this.miscellaneousAttributes.clear();
    }

    public void mapField(final String field, Object value) {
        if (value == null)
            return;
        switch (field) {
            case PROBE_TYPE:
                this.probe = Probe.valueOf(value.toString());
                break;
            case CORRELATION_ID:
                this.correlationId = value.toString();
                break;
            case START_TIME:
                this.startTime = Long.parseLong(value.toString());
                break;
            case ELASPSED_TIME:
                this.elapsedTime = Integer.parseInt(value.toString());
                break;
            case SUCCESS:
                this.success = Boolean.parseBoolean(value.toString());
                break;
            case SEQUENCE:
                this.sequence = Integer.parseInt(value.toString());
                break;
            case USER:
                this.user = value.toString();
                break;
            case ERROR:
                this.error = value.toString();
                break;
            case NODE_NAME:
                this.nodeName = value.toString();
                break;
            case INVOCATIONID:
                this.invocationId = value.toString();
                break;
            case REQ_BODY:
                this.request = value.toString();
                break;
            case REQ_HEADERS:
                this.requestHeaders = value.toString();
                break;
            case RES_BODY:
                this.response = value.toString();
                break;
            case RES_HEADERS:
                this.responseHeaders = value.toString();
                break;
            case THREAD_NAME:
                this.threadName = value.toString();
                break;
            case INCOMING:
                this.incoming = Boolean.parseBoolean(value.toString());
                break;
            case SQL_RESPONSE:
                miscellaneousAttributes.put(field, value.toString());
                this.sqlResponse = Boolean.parseBoolean(value.toString());
                break;
            case SQL_ROWCOUNT:
                miscellaneousAttributes.put(field, value.toString());
                this.sqlRowCount = Integer.parseInt(value.toString());
                break;
            case TENANT:
                this.tenant = value.toString();
                break;
            case SERVICE_APP_NAME:
                this.serviceName = value.toString();
                break;
            case TOPIC_NAME:
                this.topic = value.toString();
                break;
            case ENABLE_API_LOGGING:
                this.enableApiLogging = Boolean.parseBoolean(value.toString());
                break;
            default:
                miscellaneousAttributes.put(field, value.toString());
                break;
        }
    }

    public void fromStream(Object[][] data, boolean all) {
        ProbedState state = null;
        for (int x = 0; x < data.length; x++) {
            String key = data[x][0].toString();
            Object value = data[x][1];

            switch (key) {
                case PROBE_TYPE:
                    this.probe = Probe.valueOf((String) value);
                    break;
                case CORRELATION_ID:
                    this.correlationId = (String) value;
                    break;
                case PROBE_STATE:
                    state = ProbedState.valueOf((String) value);
                    break;
                case START_TIME:
                    if (all || (state == BEFORE || state == ON))
                        this.startTime = (Long) value;
                    break;
                case ELASPSED_TIME:
                    if (all || (state == AFTER))
                        this.elapsedTime = (Integer) value;
                    break;
                case SUCCESS:
                    if (all || (state == AFTER || state == ON))
                        this.success = (Boolean) value;
                    break;
                case SEQUENCE:
                    if (all || (state == BEFORE || state == ON))
                        this.sequence = (Integer) value;
                    break;
                case USER:
                    if (all || (state == BEFORE || state == ON))
                        this.user = (String) value;
                    break;
                case ERROR:
                    if (all || (state == AFTER || state == ON))
                        this.error = (String) value;
                    break;
                case NODE_NAME:
                    this.nodeName = (String) value;
                    break;
                case INVOCATIONID:
                    this.invocationId = String.valueOf(value == null ? INVOCATIONID_DEFAULT : value);
                    break;
                case BODY:
                    if (state == BEFORE || state == ON)
                        this.request = (String) value;
                    else
                        this.response = (String) value;
                    break;
                case HEADERS:
                    if (state == BEFORE || state == ON)
                        this.requestHeaders = (String) value;
                    else
                        this.responseHeaders = (String) value;
                    break;
                case ERROR_TYPE:
                    if (value != null)
                        this.miscellaneousAttributes.put(ERROR_TYPE, value.toString());
                    break;
                case THREAD_NAME:
                    this.threadName = (String) value;
                    break;
                case INCOMING:
                    if (all || (state == BEFORE || state == ON))
                        this.incoming = (Boolean) value;
                    break;
                case SQL_ROWCOUNT:
                    if (all || (state == AFTER || state == ON))
                        this.sqlRowCount = (Integer) value;
                    break;
                case SQL_RESPONSE:
                    if (all || (state == AFTER || state == ON))
                        this.sqlResponse = (Boolean) value;
                    break;
                case TENANT:
                    this.tenant = (String) value;
                    break;
                case SERVICE_APP_NAME:
                    this.serviceName = (String) value;
                    break;
                case TOPIC_NAME:
                    this.topic = (String) value;
                    break;
                case ENABLE_API_LOGGING:
                    this.enableApiLogging = (Boolean)value;
                    break;
                default:
                    if (value != null && (state == BEFORE || state == ON))
                        this.miscellaneousAttributes.put(key, value.toString());
                    break;
            }
        }
    }

    /**
     * @return the correlationId
     */
    public String getCorrelationId() {
        return correlationId;
    }

    /**
     * @param correlationId the correlationId to set
     */
    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    /**
     * @return the probe
     */
    public Probe getProbe() {
        return probe;
    }

    /**
     * @param probe the probe to set
     */
    public void setProbe(Probe probe) {
        this.probe = probe;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the elapsedTime
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * @param elapsedTime the elapsedTime to set
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    /**
     * @return the success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     * @return the sequence
     */
    public int getSequence() {
        return sequence;
    }

    /**
     * @param sequence the sequence to set
     */
    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @param error the error to set
     */
    public void setError(String error) {
        this.error = error;
    }

    /**
     * @return the requestHeaders
     */
    public String getRequestHeaders() {
        return requestHeaders;
    }

    /**
     * @param requestHeaders the requestHeaders to set
     */
    public void setRequestHeaders(String requestHeaders) {
        this.requestHeaders = requestHeaders;
    }

    /**
     * @return the responseHeaders
     */
    public String getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * @param responseHeaders the responseHeaders to set
     */
    public void setResponseHeaders(String responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    /**
     * @return the request
     */
    public String getRequest() {
        return request;
    }

    /**
     * @param request the request to set
     */
    public void setRequest(String request) {
        this.request = request;
    }

    /**
     * @return the response
     */
    public String getResponse() {
        return response;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(String response) {
        this.response = response;
    }

    /**
     * @return the invocationId
     */
    public String getInvocationId() {
        return invocationId;
    }

    /**
     * @param invocationId the invocationId to set
     */
    public void setInvocationId(String invocationId) {
        this.invocationId = invocationId;
    }

    /**
     * @return the nodeName
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * @param nodeName the nodeName to set
     */
    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    /**
     * @return the merged
     */
    public boolean isMerged() {
        return merged;
    }

    /**
     * @param merged the merged to set
     */
    public void setMerged(boolean merged) {
        this.merged = merged;
    }

    /**
     * @return the miscellaneousAttributes
     */
    public Map<String, String> getMiscellaneousAttributes() {
        return miscellaneousAttributes;
    }

    /**
     * @param miscellaneousAttributes the miscellaneousAttributes to set
     */
    public void setMiscellaneousAttributes(Map<String, String> miscellaneousAttributes) {
        this.miscellaneousAttributes = miscellaneousAttributes;
    }

    /**
     * @return the isLast
     */
    public boolean isLast() {
        return isLast;
    }

    /**
     * @param isLast the isLast to set
     */
    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }


    /**
     * @return the threadName
     */
    public String getThreadName() {
        return threadName;
    }

    /**
     * @param threadName the threadName to set
     */
    public void setThreadName(String threadName) {
        this.threadName = threadName;
    }

    /**
     * @return the docSize
     */
    public int getDocSize() {
        return docSize;
    }

    /**
     * @param docSize the docSize to set
     */
    public void setDocSize(int docSize) {
        this.docSize = docSize;
    }

    /**
     * @return the incoming
     */
    public boolean isIncoming() {
        return incoming;
    }

    /**
     * @param incoming the incoming to set
     */
    public void setIncoming(boolean incoming) {
        this.incoming = incoming;
    }

    /**
     * @return the sqlRowCount
     */
    public int getSqlRowCount() {
        return sqlRowCount;
    }

    /**
     * @param sqlRowCount the sqlRowCount to set
     */
    public void setSqlRowCount(int sqlRowCount) {
        this.sqlRowCount = sqlRowCount;
    }

    /**
     * @return the sqlResponse
     */
    public boolean isSqlResponse() {
        return sqlResponse;
    }

    /**
     * @param sqlResponse the sqlResponse to set
     */
    public void setSqlResponse(boolean sqlResponse) {
        this.sqlResponse = sqlResponse;
    }

    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public boolean isEnableApiLogging() {
        return enableApiLogging;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ProbeData [probe=");
        builder.append(probe);
        builder.append(", correlationId=");
        builder.append(correlationId);
        builder.append(", user=");
        builder.append(user);
        builder.append(", sequence=");
        builder.append(sequence);
        builder.append(", invocationId=");
        builder.append(invocationId);
        builder.append(", nodeName=");
        builder.append(nodeName);
        builder.append(", merged=");
        builder.append(merged);
        builder.append("]");
        return builder.toString();
    }

}
