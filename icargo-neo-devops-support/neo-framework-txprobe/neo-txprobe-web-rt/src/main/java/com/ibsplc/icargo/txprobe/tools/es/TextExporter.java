/*
 * TextExporter.java Created on 25-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.txprobe.api.Probe;
import com.ibsplc.icargo.txprobe.api.ProbeData;
import com.ibsplc.icargo.txprobe.api.ProbeDataConstants;
import com.ibsplc.icargo.txprobe.tools.utils.Utils;

/*
 * Revision History
 * Revision 		Date      				Author			    Description
 * 1.0   			25-Apr-2016       		Jens J P 			First Draft
 */
/**
 * @author A-2394
 *
 */
public class TextExporter implements ProbeDataConstants, Exporter{

	static final char LF = '\n';
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	@Override
	public void doExport(File dir, List<ProbeData> datas) throws Exception{
		ProbeData first = datas.get(0);
		File file = new File(dir, first.getCorrelationId() + ".txprobe.log");
		System.out.println("Writing file : " + file.getAbsolutePath());
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		char[] line = new char[150];
		Arrays.fill(line, '-');
		
		for(ProbeData data : datas){
			writeData(bw, data);
			bw.write(line);
			bw.write(LF);
		}
		bw.flush();
		bw.close();
	}
	
	protected void writeData(BufferedWriter bw, ProbeData data) throws Exception{
		writeEntry(bw, START_TIME, sdf.format(new Date(data.getStartTime())));
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
		writeFmtEntry(bw, LOGON_ATTRIBUTES, data.getMiscellaneousAttributes().get(LOGON_ATTRIBUTES));
		List<String> exclusions = Arrays.asList(URL, MODULE, SUBMODULE, ACTION, SOAPACTION, INTERFACESYS, JSESSIONID, LOGON_ATTRIBUTES, ERROR_TYPE);
		for(Map.Entry<String, String> e : data.getMiscellaneousAttributes().entrySet()){
			if(exclusions.contains(e.getKey()))
				continue;
			writeEntry(bw, e.getKey(), e.getValue());
		}
		writeFmtEntry(bw, REQ_HEADERS, data.getRequestHeaders());
		if(Probe.SQL == data.getProbe())
			writeSqlFmtEntry(bw, REQ_BODY, data.getRequest());
		else
			writeFmtEntry(bw, REQ_BODY, data.getRequest());
		writeEntry(bw, ERROR, data.getError());
		writeFmtEntry(bw, RES_HEADERS, data.getResponseHeaders());
		writeFmtEntry(bw, RES_BODY, data.getResponse());
	}
	
	private void writeFmtEntry(BufferedWriter bw, String key, Object value) throws IOException{
		if(value == null)
			return;
		if(isJson(value.toString())){
			try {
				value = Utils.formatJson(value.toString());
			} catch (Exception e) {
			}
		}
		writeEntry(bw, key, value);
	}
	
	private void writeSqlFmtEntry(BufferedWriter bw, String key, Object value) throws IOException{
		if(value == null)
			return;
		value = Utils.formatSql(value.toString().trim());
		writeEntry(bw, key, value);
	}
	
	private void writeEntry(BufferedWriter bw, String key, Object value) throws IOException{
		if(value == null)
			return;
		StringBuilder sbul = new StringBuilder();
		sbul.append(key).append(" : ").append(value).append(LF);
		bw.write(sbul.toString());
	}
	
	public static boolean isJson(String str){
		for(int x = 0 ; x < str.length(); x++){
			if(Character.isWhitespace(str.charAt(x)))
				continue;
			if('{' == str.charAt(x))
				return true;
			else
				return false;
		}
		return false;
	}
	
	public static boolean isXml(String str){
		for(int x = 0 ; x < str.length(); x++){
			if(Character.isWhitespace(str.charAt(x)))
				continue;
			if('<' == str.charAt(x))
				return true;
			else
				return false;
		}
		return false;
	}
}
