/*
 * TransactionDetailsAttributeBuilder.java Created on Jul 28, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.misc.uldmvthistory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author a-2553
 *
 */

public class TransactionDetailsAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("TransactionDetailsAttributeBuilder");


	
	@Override
	public Vector<String> getReportColumns() {

		log.entering("TransactionDetailsAttributeBuilder","getReportColumns");

		Vector<String> columns = new Vector<String>();
		columns.add("LSTUPDDAT");
		columns.add("TXNSTA");
		columns.add("RTNPTYCOD");
		columns.add("PTYCOD");
		columns.add("CNTRPTNUM");
		columns.add("TXNDAT");
		columns.add("RTNARPCOD");
		columns.add("TXNARPCOD");
		columns.add("RTNDAT");
		columns.add("RTNRMK");
		return columns;
	}

	/**
	 * Method to construct the report data. Each row in the details section of
	 * the report corresponds to one element in the outer Vector. Each element
	 * in the inner Vector corresponds to a field in the report. The order in
	 * which the data is returned should match the order in which the fields are
	 * laid out in the report
	 * @param data
	 * @param extraInfo
	 * @return Vector<Vector> the report data
	 */

 	@Override
	public Vector<Vector> getReportData(Collection data, Collection extraInfo) {

 		Vector<Vector> tableData = new Vector<Vector>();
 		log.log(Log.INFO,"-------INSIDE REPORT DATA________________");
		for (ULDTransactionDetailsVO uldTransactionDetailsVO : ((Collection<ULDTransactionDetailsVO>)data)) {
			Vector<Object> row = new Vector<Object>();			
			log.log(Log.INFO, "-------uldTransactionDetailsVO--->",
					uldTransactionDetailsVO);
			if(uldTransactionDetailsVO != null){
				if(uldTransactionDetailsVO.getLastUpdateTime() !=null){
					row.add(uldTransactionDetailsVO.getLastUpdateTime().toDisplayDateOnlyFormat());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getTransactionStatus() !=null ){					
					row.add(uldTransactionDetailsVO.getTransactionStatus());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getFromPartyCode() != null){
					row.add(uldTransactionDetailsVO.getFromPartyCode());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getToPartyCode() != null){
					row.add(uldTransactionDetailsVO.getToPartyCode());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getControlReceiptNumber() != null){
					row.add(uldTransactionDetailsVO.getControlReceiptNumber());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
					
				if(uldTransactionDetailsVO.getTransactionDate() !=null){
					row.add(uldTransactionDetailsVO.getTransactionDate().toDisplayFormat(true));
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				
				if(uldTransactionDetailsVO.getTransactionStationCode() !=null){
					row.add(uldTransactionDetailsVO.getTransactionStationCode());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getTxStationCode() !=null){
					row.add(uldTransactionDetailsVO.getTxStationCode());
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getReturnDate() !=null){
					row.add(uldTransactionDetailsVO.getReturnDate().toDisplayFormat(true));
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				if(uldTransactionDetailsVO.getReturnRemark() !=null ||
						uldTransactionDetailsVO.getTransactionRemark() != null){
					if(uldTransactionDetailsVO.getReturnRemark() !=null &&
							"To Be Invoiced".equals(uldTransactionDetailsVO.getTransactionStatus())){
						row.add(uldTransactionDetailsVO.getReturnRemark());
					}else if(!"To Be Invoiced".equals(uldTransactionDetailsVO.getTransactionStatus())){
					row.add(uldTransactionDetailsVO.getTransactionRemark());
					}
				}
				else{
					row.add(ReportConstants.EMPTY_STRING);
				}
				

			}
			tableData.add(row);
		}
		return tableData;
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
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {
		Vector<Object> reportParameters = new Vector<Object>();
		if(parameters!=null && parameters.size()>0){

		log.log(1,"Inside if----Parameters not null");
		Object dataParametersFilter = ((ArrayList<Object>)parameters).get(0);

		TransactionFilterVO transactionFilterVO = (TransactionFilterVO)dataParametersFilter;
		if(transactionFilterVO!=null)	{
			if(transactionFilterVO.getUldNumber()  != null){
				reportParameters.add(transactionFilterVO.getUldNumber());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getTxnFromDate() != null){
				reportParameters.add(transactionFilterVO.getTxnFromDate().toDisplayDateOnlyFormat());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getTxnToDate() != null){
				reportParameters.add(transactionFilterVO.getTxnToDate().toDisplayDateOnlyFormat());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getNoOfMovements() != null){
				reportParameters.add(transactionFilterVO.getNoOfMovements());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getNoOfLoanTxns() != null){
				reportParameters.add(transactionFilterVO.getNoOfLoanTxns());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getNoOfTimesDmgd() != null){
				reportParameters.add(transactionFilterVO.getNoOfTimesDmgd());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
			if(transactionFilterVO.getNoOfTimesRepaired() != null){
				reportParameters.add(transactionFilterVO.getNoOfTimesRepaired());
			}
			else{
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
		}
		else{
		  reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
	}
		return reportParameters;
	}

}

