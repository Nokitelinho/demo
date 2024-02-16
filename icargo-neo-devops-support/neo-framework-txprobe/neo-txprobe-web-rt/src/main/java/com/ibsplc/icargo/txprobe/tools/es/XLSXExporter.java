/*
 * XLSXExporter.java Created on 25-Apr-2016
 *
 * Copyright 2012 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.txprobe.tools.es;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

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
public class XLSXExporter implements Exporter, ProbeDataConstants{
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private XSSFCellStyle dataStyle;
	private XSSFCellStyle headerStyle;
	
	static{
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	} 
	
	/* (non-Javadoc)
	 * @see com.ibsplc.icargo.txprobe.aggregator.tools.es.Exporter#doExport(java.io.File, java.util.List)
	 */
	@Override
	public void doExport(File dir, List<ProbeData> datas) throws Exception {
		SXSSFWorkbook workbook = new SXSSFWorkbook(-1);
		SXSSFSheet sheet = workbook.createSheet("txprobe export");
		Map<String, Integer> collMap = mapColumns();
		writeHeaders(sheet, collMap);
		
		for(int x = 0 ; x < datas.size(); x++){
			SXSSFRow row = sheet.createRow(x + 1);
			writeRow(datas.get(x), collMap, row);
		}
		ProbeData first = datas.get(0);
		File file = new File(dir, first.getCorrelationId() + ".txprobe.xlsx");
		System.out.println("Writing file : " + file.getAbsolutePath());
		FileOutputStream fos = new FileOutputStream(file);
		workbook.write(fos);
		fos.flush();
		fos.close();
		workbook.close();
	}

	protected void writeRow(ProbeData data, Map<String, Integer> collMap, SXSSFRow row){
		//double excelDate = DateUtil.getExcelDate());
		writeCell(row, sdf.format(new Date(data.getStartTime())), collMap.get(START_TIME));
		//writeCell(row, data.getTenant())
		writeCell(row, data.getProbe(), collMap.get(PROBE_TYPE));
		writeCell(row, data.getServiceName(), collMap.get(SERVICE_APP_NAME));
		writeCell(row, data.getTenant(), collMap.get(TENANT));
		writeCell(row, data.getTopic(), collMap.get(TOPIC_NAME));
		writeCell(row, data.getElapsedTime(), collMap.get(ELASPSED_TIME));
		writeCell(row, data.isSuccess(), collMap.get(SUCCESS));
		writeCell(row, data.getMiscellaneousAttributes().get(ERROR_TYPE), collMap.get(ERROR_TYPE));
		writeCell(row, data.getUser(), collMap.get(USER));
		writeCell(row, data.getNodeName(), collMap.get(NODE_NAME));
		writeCell(row, data.getThreadName(), collMap.get(THREAD_NAME));
		writeCell(row, data.getInvocationId(), collMap.get(INVOCATIONID));
		writeCell(row, data.getSequence(), collMap.get(SEQUENCE));
		writeCell(row, data.getMiscellaneousAttributes().get(URL), collMap.get(URL));
		writeCell(row, data.getMiscellaneousAttributes().get(MODULE), collMap.get(MODULE));
		writeCell(row, data.getMiscellaneousAttributes().get(SUBMODULE), collMap.get(SUBMODULE));
		writeCell(row, data.getMiscellaneousAttributes().get(ACTION), collMap.get(ACTION));
		writeCell(row, data.getMiscellaneousAttributes().get(SOAPACTION), collMap.get(SOAPACTION));
		writeCell(row, data.getMiscellaneousAttributes().get(INTERFACESYS), collMap.get(INTERFACESYS));
		writeCell(row, data.getMiscellaneousAttributes().get(JSESSIONID), collMap.get(JSESSIONID));
		writeFmtCell(row, data.getMiscellaneousAttributes().get(LOGON_ATTRIBUTES), collMap.get(LOGON_ATTRIBUTES));
		writeFmtCell(row, data.getRequestHeaders(), collMap.get(REQ_HEADERS));
		if(Probe.SQL == data.getProbe())
			writeSqlFmtCell(row, data.getRequest(), collMap.get(REQ_BODY));
		else
			writeFmtCell(row, data.getRequest(), collMap.get(REQ_BODY));
		writeCell(row, data.getError(), collMap.get(ERROR));
		writeFmtCell(row, data.getResponseHeaders(), collMap.get(RES_HEADERS));
		writeFmtCell(row, data.getResponse(), collMap.get(RES_BODY));
		
		List<String> exclusions = Arrays.asList(URL, MODULE, SUBMODULE, ACTION, SOAPACTION, INTERFACESYS, JSESSIONID, LOGON_ATTRIBUTES, ERROR_TYPE);
		for(Map.Entry<String, String> e : data.getMiscellaneousAttributes().entrySet()){
			if(exclusions.contains(e.getKey()))
				continue;
			int index = 0;
			if(collMap.containsKey(e.getKey()))
				index = collMap.get(e.getKey());
			else{
				index = collMap.size() + 1;
				collMap.put(e.getKey(), index);
				writeHeader(row.getSheet(), e.getKey(), index);
			}
			writeCell(row, e.getValue(), index);
		}
	}
	
	protected SXSSFCell writeCell(SXSSFRow row, Object value, int index){
		if(value == null)
			return null;
		SXSSFCell cell = row.createCell(index);
		if(value instanceof Date){
			cell.setCellValue(Date.class.cast(value));
		}else if(value instanceof Boolean){
			cell.setCellValue(Boolean.class.cast(value));
		}else if(value instanceof Double){
			cell.setCellValue(Double.class.cast(value));
		}else{
			String answer = value.toString();
			if(answer.length() > SpreadsheetVersion.EXCEL2007.getMaxTextLength()){
				StringBuilder sbul = new StringBuilder(32767);
				sbul.append(answer.subSequence(0, 32750));
				sbul.append("\n..TRUNCATED..");
				answer = sbul.toString();
			}
			cell.setCellValue(answer);
		}
		cell.setCellStyle(createDataStyle(row.getSheet().getWorkbook()));
		return cell;
	}
	
	protected void writeFmtCell(SXSSFRow row, Object value, int index){
		if(value == null)
			return;
		if(TextExporter.isJson(value.toString())){
			try {
				value = Utils.formatJson(value.toString());
			} catch (Exception e) {}
		}
		writeCell(row, value, index);
	}
	
	protected void writeSqlFmtCell(SXSSFRow row, Object value, int index){
		if(value == null)
			return;
		value = Utils.formatSql(value.toString().trim());
		writeCell(row, value, index);
	}
	
	protected void writeHeader(SXSSFSheet sheet, String name, int index){
		SXSSFRow row = sheet.getRow(0);
		XSSFCellStyle style = createHeaderStyle(sheet.getWorkbook());
		SXSSFCell cell = row.createCell(index);
		cell.setCellStyle(style);
		cell.setCellValue(name);
	}
	
	protected void writeHeaders(SXSSFSheet sheet, Map<String, Integer> colMap){
		SXSSFRow row = sheet.createRow(0);
		// freeze the first row ie our primary header
		sheet.createFreezePane(0, 1, 0, 1);
		// primary header style
		XSSFCellStyle style = createHeaderStyle(sheet.getWorkbook());
		
		for(Map.Entry<String, Integer> e : colMap.entrySet()){
			SXSSFCell cell = row.createCell(e.getValue());
			cell.setCellStyle(style);
			cell.setCellValue(e.getKey());
		}
	}

	@SuppressWarnings("deprecation")
	protected XSSFCellStyle createHeaderStyle(SXSSFWorkbook workbook){
		if(this.headerStyle != null)
			return this.headerStyle;
		XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
		style.setBorderBottom(CellStyle.BORDER_MEDIUM);
		style.setBorderTop(CellStyle.BORDER_MEDIUM);
		style.setBorderLeft(CellStyle.BORDER_MEDIUM);
		style.setBorderRight(CellStyle.BORDER_MEDIUM);
		style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);

		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_NORMAL);
		style.setFont(font);
		
		this.headerStyle = style;
		return style;
	}
	
	protected XSSFCellStyle createDataStyle(SXSSFWorkbook workbook){
		if(this.dataStyle != null)
			return this.dataStyle;
		XSSFCellStyle style = (XSSFCellStyle)workbook.createCellStyle();
		/*style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setBorderRight(CellStyle.BORDER_THIN);*/
		
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short)9);
		style.setFont(font);
		this.dataStyle = style;
		return style;
	}
	
	protected Map<String, Integer> mapColumns(){
		Map<String, Integer> colMap = new HashMap<>(32);
		int index = 0;
		colMap.put(START_TIME, index++);
		colMap.put(PROBE_TYPE, index++);
		colMap.put(TENANT, index++);
		colMap.put(SERVICE_APP_NAME, index++);
		colMap.put(ELASPSED_TIME, index++);
		colMap.put(SUCCESS, index++);
		colMap.put(ERROR_TYPE, index++);
		colMap.put(USER, index++);
		colMap.put(NODE_NAME, index++);
		colMap.put(THREAD_NAME, index++);
		colMap.put(INVOCATIONID, index++);
		colMap.put(SEQUENCE, index++);
		colMap.put(TOPIC_NAME, index++);
		colMap.put(URL, index++);
		colMap.put(MODULE, index++);
		colMap.put(SUBMODULE, index++);
		colMap.put(ACTION, index++);
		colMap.put(SOAPACTION, index++);
		colMap.put(INTERFACESYS, index++);
		colMap.put(JSESSIONID, index++);
		colMap.put(LOGON_ATTRIBUTES, index++);
		colMap.put(REQ_HEADERS, index++);
		colMap.put(REQ_BODY, index++);
		colMap.put(ERROR, index++);
		colMap.put(RES_HEADERS, index++);
		colMap.put(RES_BODY, index++);
		return colMap;
	}
	
}
