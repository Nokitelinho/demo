/*
 * ElasticSearchHelper.java Created on 01-Feb-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.aggregator.handler.outbound.es;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
import static org.elasticsearch.common.xcontent.XContentFactory.smileBuilder;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import com.ibsplc.icargo.txprobe.aggregator.utils.Utils;
import com.ibsplc.icargo.txprobe.api.*;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			01-Feb-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
class ElasticSearchHelper implements ProbeDataConstants{

    private static Map<String, String> MAPPINGS;
	
	public static final String ES_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";//date_time
	public static final String ES_DATE_TYPE = "date";
	private final DateFormat basicDateTimeFormat;

	static {
		MAPPINGS = new HashMap<>();
		MAPPINGS.put(String.class.getName(), "string");
		MAPPINGS.put(Calendar.class.getName(), ES_DATE_TYPE);
		MAPPINGS.put(Date.class.getName(), ES_DATE_TYPE);
		MAPPINGS.put(java.sql.Date.class.getName(), ES_DATE_TYPE);
		MAPPINGS.put(Integer.class.getName(), "integer");
		MAPPINGS.put(Long.class.getName(), "long");
		MAPPINGS.put(Short.class.getName(), "short");
		MAPPINGS.put(Double.class.getName(), "double");
		MAPPINGS.put(Float.class.getName(), "float");
		MAPPINGS.put(Boolean.class.getName(), "boolean");
	}
	
	private Map<String, FieldMetadata> fieldMD;
	
	public ElasticSearchHelper(Map<String, FieldMetadata> fieldMD) {
		super();
		this.fieldMD = fieldMD;
		this.basicDateTimeFormat = new SimpleDateFormat(ES_DATE_FORMAT);
		this.basicDateTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	public IndexRequest createIndexRequest(String indexName, ProbeData data) throws IOException{
		IndexRequest iReq = new IndexRequest(indexName);
		//iReq.parent(data.getCorrelationId());
		//iReq.timestamp(String.valueOf(data.getStartTime()));
		XContentBuilder xbul = smileBuilder();
		xbul.startObject();
		
		xbul.field(PROBE_TYPE, data.getProbe().toString());
		xbul.field(CORRELATION_ID, data.getCorrelationId());
		xbul.field(START_TIME, new Date(data.getStartTime()));
		xbul.field(ELASPSED_TIME, data.getElapsedTime());
		xbul.field(SUCCESS, data.isSuccess());
		xbul.field(SEQUENCE, data.getSequence());
		xbul.field(USER, data.getUser());
		xbul.field(ERROR, data.getError());
		xbul.field(NODE_NAME, data.getNodeName());
		xbul.field(INVOCATIONID, data.getInvocationId());
		xbul.field(REQ_BODY, data.getRequest());
		xbul.field(REQ_HEADERS, data.getRequestHeaders());
		xbul.field(RES_BODY, data.getResponse());
		xbul.field(RES_HEADERS, data.getResponseHeaders());
		xbul.field(THREAD_NAME, data.getThreadName());
		xbul.field(DOCSIZE, data.getDocSize());
		xbul.field(INCOMING, data.isIncoming());
		xbul.field(TENANT, data.getTenant());
		xbul.field(SERVICE_APP_NAME, data.getServiceName());
		if(data.getTopic() != null)
			xbul.field(TOPIC_NAME, data.getTopic());
		if(Probe.SQL == data.getProbe()) {
			xbul.field(SQL_RESPONSE, data.isSqlResponse());
			xbul.field(SQL_ROWCOUNT, data.getSqlRowCount());
			xbul.field(SQL_HASH, Utils.findSqlHash(data.getRequest()));
		}
		if(data.getMiscellaneousAttributes() != null){
			for(Map.Entry<String, String> e : data.getMiscellaneousAttributes().entrySet()){
				xbul.field(e.getKey(), e.getValue());
			}
		}
		xbul.endObject();
		iReq.source(xbul);
		return iReq;
	}
	
	public XContentBuilder createMappingBody() throws IOException{
		XContentBuilder xbul = jsonBuilder();
		xbul.startObject();
		//xbul.startObject("_source").field("enabled", false).endObject();
		xbul.startObject("properties");
		for(Map.Entry<String, FieldMetadata> e : fieldMD.entrySet()){
			String key = e.getKey();
			xbul.startObject(key);
			String esType = mapType(e.getValue().getType());
			// es 5.x changes
			if("string".equals(esType))
				esType = e.getValue().isAnalyzed() ? "text" : "keyword";
			xbul.field("type", esType);
			xbul.field("index", e.getValue().isIndexed());
			if(ES_DATE_TYPE.equals(esType))
				xbul.field("format", "date_time");
			xbul.endObject();
		}
		xbul.endObject().endObject();
		return xbul;
	}

	public XContentBuilder createLogMappingBody() throws IOException{
		Map<String, FieldMetadata> logFieldMD = LogEntryFields.getFieldMetadata();
		XContentBuilder xbul = jsonBuilder();
		xbul.startObject();
		xbul.startObject("properties");
		for(Map.Entry<String, FieldMetadata> e : logFieldMD.entrySet()){
			String key = e.getKey();
			xbul.startObject(key);
			String esType = mapType(e.getValue().getType());
			// es 5.x changes
			if("string".equals(esType))
				esType = e.getValue().isAnalyzed() ? "text" : "keyword";
			xbul.field("type", esType);
			xbul.field("index", e.getValue().isIndexed());
			if(ES_DATE_TYPE.equals(esType))
				xbul.field("format", "date_time");
			xbul.endObject();
		}
		xbul.endObject().endObject();
		return xbul;
	}
	
	private static String mapType(String javaType){
		String esType = MAPPINGS.get(javaType);
		if(esType == null)
			throw new IllegalArgumentException("unknown data type : " + javaType);
		return esType;
	}
	
	public String formatDate(Object dateCal){
		if(dateCal instanceof Number){
			long timeInMs = Number.class.cast(dateCal).longValue();
			Date date = new Date(timeInMs);
			return basicDateTimeFormat.format(date);
		}
		if(dateCal instanceof Calendar){
			return basicDateTimeFormat.format(Calendar.class.cast(dateCal).getTime());
		}
		if(dateCal instanceof Date){
			return basicDateTimeFormat.format(Date.class.cast(dateCal));
		}
		if(dateCal instanceof java.sql.Date){
			return basicDateTimeFormat.format(java.sql.Date.class.cast(dateCal));
		}
		return null;
	}
}
