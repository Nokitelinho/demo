/*
 * AWBStockHistoryAttributeBuilder.java Created on 4th April, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.stockcontrol.defaults;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockRangeHistoryVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;
/**
 * 
 * @author A-4562
 *
 */
public class AWBStockHistoryAttributeBuilder extends AttributeBuilderAdapter {
	
	
	private static final Log log = LogFactory.getLogger("stockcontrol defaults AWBSTOCKHISTORY");
	private static final String HISTORY = "History";
	private static final String CURRENT="Current";
	/**
	 * Method to construct the report column names. The column names corresponds
	 * to the column names of the view used while laying out the report. The
	 * order of the column names should match the order in which the database
	 * fields are laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<String> the column names
	 */
	@Override 
	public Vector<String> getReportColumns() {
		
		Vector<String> columns = new Vector<String>();
		columns.add("FRMSTKHLDCOD");
		columns.add("TOSTKHLDCOD");
		columns.add("DOCTYP");
		columns.add("DOCSUBTYP");
		columns.add("STATUS");
		columns.add("RANGE");
		columns.add("NUMDOC");
		columns.add("TXNDAT");
		columns.add("VODCHG");
		columns.add("REMARKS");
		return columns;
	}

/**
	 * Method to construct the report parameters. The report parameters
	 * corresponds to the parameter fields in the report. The order of the
	 * parameters should match the order in which the parameter fields are laid
	 * out in the report
	 * @param parameters the parameter data
	 * @param extraInfo information required for formatting the parameters
	 * @return Vector the report parameters
	 */
	@Override
	public Vector<Object> getReportParameters(Collection parameters, Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		Object filterDatas = ((ArrayList<Object>) parameters).get(0);
		StockRangeFilterVO stockRangeFilterVO =(StockRangeFilterVO)filterDatas;
		if(stockRangeFilterVO.isHistory()) {
			
			reportParameters.add(HISTORY);
		} else {
			
			reportParameters.add(CURRENT);
		}
		if(stockRangeFilterVO.getFromStockHolderCode()!= null) {
			
			log.log(Log.INFO, "getFromStockHolderCode()------->",
					stockRangeFilterVO.getFromStockHolderCode());
			reportParameters.add(stockRangeFilterVO.getFromStockHolderCode());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);  
		}
		
		
		
		if(stockRangeFilterVO.getUserId()!=null) {                                                  
			reportParameters.add(stockRangeFilterVO.getUserId());
		}
		else{
			
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(stockRangeFilterVO.getStartRange()!=null) {
			reportParameters.add(stockRangeFilterVO.getStartRange());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (stockRangeFilterVO.getEndRange()!=null) {
			log.log(Log.INFO, "getEndRange()------->", stockRangeFilterVO.getEndRange());
			reportParameters.add(stockRangeFilterVO.getEndRange()); 
		}
		else{
			log.log(Log.INFO, "getEndRange()------->", stockRangeFilterVO.getEndRange());
			reportParameters.add(ReportConstants.EMPTY_STRING); 
		}
		if (stockRangeFilterVO.getStartDate()!=null) {
			reportParameters.add(stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat());
		} else {
			log
					.log(Log.INFO, "stockRangeFilterVO.getStartDate------->",
							stockRangeFilterVO.getStartDate().toDisplayDateOnlyFormat());
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (stockRangeFilterVO.getEndDate()!=null) {
			reportParameters.add(stockRangeFilterVO.getEndDate()
					.toDisplayDateOnlyFormat());
		} else {
			log.log(Log.INFO, "stockRangeFilterVO.getEndDate------->",
					stockRangeFilterVO.getEndDate().toDisplayDateOnlyFormat());
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if (stockRangeFilterVO.getStatus()!=null) {
			reportParameters.add(stockRangeFilterVO.getStatus());
		}else {
			log.log(Log.INFO, "getStatus------->", stockRangeFilterVO.getStatus());
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}

		if(stockRangeFilterVO.getDocumentType()!=null){
			reportParameters.add(stockRangeFilterVO.getDocumentType());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		if(stockRangeFilterVO.getDocumentSubType()!=null) {
			reportParameters.add(stockRangeFilterVO.getDocumentSubType());
		}
		else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}	
		
		log.log(Log.FINE, "reportParameters is ----------------->>>>>>",
				reportParameters);
		return reportParameters;
		
	}

	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param parameters
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */
	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

		Vector<Vector> detailsTableData = new Vector<Vector>();

		//Object values= ((ArrayList)extraInfo).get(0);
		Collection<StockRangeHistoryVO> rangeVOs = (Collection<StockRangeHistoryVO>)data;
		log.log(Log.INFO, "Attribute Filter getReportData values------->",
				rangeVOs);
		if (rangeVOs != null && rangeVOs.size() > 0) {			
			for (StockRangeHistoryVO rangeVO : rangeVOs) {
				Vector<Object> row = new Vector<Object>();
				
				if(!(rangeVO.getFromStockHolderCode()==null))
				{
					log.log(Log.INFO, "fromstockholdercode------->", rangeVO.getFromStockHolderCode());
					row.add(rangeVO.getFromStockHolderCode());
					
				}
				
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(!(rangeVO.getToStockHolderCode()==null)){
					log.log(Log.INFO, "getToStockHolderCode------->", rangeVO.getToStockHolderCode());
					row.add(rangeVO.getToStockHolderCode());
				
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(!(rangeVO.getRangeType()==null)){
					log.log(Log.INFO, "getRangeTypee------->", rangeVO.getRangeType());
					row.add(rangeVO.getRangeType());
					
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(!(rangeVO.getDocumentSubType()==null)){
					log.log(Log.INFO, "getDocumentSubType------->", rangeVO.getDocumentSubType());
					row.add(rangeVO.getDocumentSubType());
					
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				
				if(!(rangeVO.getStatus()==null)){
					log.log(Log.INFO, "getStatus------->", rangeVO.getStatus());
					row.add(rangeVO.getStatus());
					
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(!(rangeVO.getAwbRange()==null)){
					log.log(Log.INFO, "getRangeType------->", rangeVO.getAwbRange());
					row.add(rangeVO.getAwbRange());
					
				} 
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(!(rangeVO.getNumberOfDocuments()==0)){
					log.log(Log.INFO, "getNumberOfDocuments------->", rangeVO.getNumberOfDocuments());
					row.add(rangeVO.getNumberOfDocuments());
				
			   }  
			   else{
				row.add(ReportConstants.EMPTY_STRING);
			   }
				
				if(!(rangeVO.getTransactionDate()==null)){
					log.log(Log.INFO, "getTransactionDate------->", rangeVO.getTransactionDate());
					String stDate = TimeConvertor.toStringFormat(rangeVO
							.getTransactionDate(),
							TimeConvertor.CALENDAR_DATE_FORMAT);
					log.log(Log.INFO, "stDate------->", stDate);
					row.add(stDate);
					
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				
				log.log(Log.INFO, "getVoidingCharge------->", rangeVO.getVoidingCharge());
					String svoidingcharge=null;
				    if(rangeVO.getCurrencyCode()!=null){
				    	svoidingcharge=String.valueOf(rangeVO.getVoidingCharge())+rangeVO.getCurrencyCode();
				    }
				    else{  
				    	 svoidingcharge=String.valueOf(rangeVO.getVoidingCharge());
				    }
				log.log(Log.INFO, "svoidingcharge------->", svoidingcharge);
					row.add(svoidingcharge);
				//}
				/*else{
					row.add(ReportConstants.EMPTY_STRING);
				}*/
				if(!(rangeVO.getRemarks()==null)){
					log.log(Log.INFO, "getRemarks------->", rangeVO.getRemarks());
					row.add(rangeVO.getRemarks());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
			detailsTableData.add(row);	
		}
		}
		return detailsTableData;		
		
		
	}	
		
		

		
}
