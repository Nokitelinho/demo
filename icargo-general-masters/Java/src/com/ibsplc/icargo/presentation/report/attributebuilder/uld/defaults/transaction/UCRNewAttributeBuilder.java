/*
 * UCRAttributeBuilder.java Created on May11, 2011
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.report.attributebuilder.uld.defaults.transaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Vector;

import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.report.normal.formatter.AttributeBuilderAdapter;
import com.ibsplc.icargo.framework.report.util.ReportConstants;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1950
 * This class is used for
 */
public class UCRNewAttributeBuilder extends AttributeBuilderAdapter {

	private Log log = LogFactory.getLogger("UCRAttributeBuilder");
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

		Vector<String> reportColumns = new Vector<String>();
		reportColumns.add("SERIALNUMBER");
		reportColumns.add("IATACODPREFIX1");
		reportColumns.add("IATACODPREFIX11");
		reportColumns.add("IATACODPREFIX12");		
		reportColumns.add("IATACODSERNO1");
		reportColumns.add("IATACODSERNO11");
		reportColumns.add("IATACODSERNO12");
		reportColumns.add("IATACODSERNO13");
		reportColumns.add("IATACODSERNO14");
		reportColumns.add("IATACODOWNER1");
		reportColumns.add("IATACODOWNER11");
		reportColumns.add("ULDSUPEQP1");
		reportColumns.add("ULDSUPEQP11");
		reportColumns.add("ULDSUPEQP12");
		reportColumns.add("ULDSUPEQP13");
		reportColumns.add("FINALDESTN1");
		reportColumns.add("FINALDESTN11");
		reportColumns.add("FINALDESTN12");
		reportColumns.add("DEMURRAGECODE1");
		reportColumns.add("DEMURRAGECODE11");
		reportColumns.add("DEMURRAGECODE12");
		reportColumns.add("DMGCODE");
		reportColumns.add("ODLNCODE1");
		reportColumns.add("ODLNCODE11");
		reportColumns.add("DMGREMARK");
		log.log(Log.FINE, "reportColumns is --------->", reportColumns);
		return reportColumns;
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
		Object dataParameters = ((ArrayList<Object>) parameters).get(0);
		ULDTransactionDetailsVO transctionDetailvo = (ULDTransactionDetailsVO) dataParameters;
		String crnNumbers =null;  
		if(extraInfo!=null){
			Object objCrnNumbers = ((ArrayList<String>)extraInfo).get(0);
			crnNumbers =(String)objCrnNumbers;
		}else{
			crnNumbers = transctionDetailvo.getControlReceiptNumber();  
		}  
		
		//1.Originator
		if(transctionDetailvo.getOriginatorName()!=null &&
				!transctionDetailvo.getOriginatorName().isEmpty()){ 
			log.log(Log.INFO, "getReportParameters %%%%%%%%%% originator ",
					transctionDetailvo.getOriginatorName());
			reportParameters.add(transctionDetailvo.getOriginatorName());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
		//2. 12 letter crn Number
		/*
		 * CRN number will be passed by single characters.
		 * If more than 10 records exists then 2 page UCR report to be generated in this case
		 * page 1 should contain CRN of first ULD and page 2 should contain CRN of 11th ULD
		 * to handle multiple page each CRN number to display in each page need to set in extra info.
		 * that is first and 11th CRN in above example.
		 * Since CRN number in report is passed by single character for multiple CRN
		 * each character from all CRN number should be concat with out seperator and passed to report
		 * IN rpt based on page number this string will be seperated out. 
		 * eg: CRN-NUMBERs "695-00000883,695-00000884" this is passed to report as
		 * 66,99,55,--,00,00..88,34. In report first page should display 695-00000883 and second page 
		 * 695-00000884. Based on pagenumber in report it will display first or second character in report.
		 * This is handled in report text manual formating rule.
		 */
		if(crnNumbers != null){
			log.log(Log.INFO, "getReportParameters %%%%%%%%%% crn number ",	crnNumbers);
			
			for(int i=0; i < 12; i++){
				StringBuilder crnChar = new StringBuilder();    
				for(String crn :crnNumbers.split(",")){  
					/*if(crnChar==null)
						crnChar=new StringBuilder().append(crn.length()>i?crn.charAt(i):ReportConstants.EMPTY_STRING);
					else*/
						crnChar.append(crn.length()>i?crn.charAt(i):ReportConstants.EMPTY_STRING);   
				}
				reportParameters.add(crnChar!=null?crnChar.toString():ReportConstants.EMPTY_STRING);   
			}   
			/*int crnCount = 0;
			for (int i=0; i < crnNumbers.length(); i++) {
				String controlReceiptNumber = String.valueOf(transctionDetailvo.
						getControlReceiptNumber().charAt(i));
				reportParameters.add(controlReceiptNumber);
				crnCount++;
			}
			if (crnCount < 12) {
				for (int i=0 ; i <(12-crnCount); i++) {
					reportParameters.add(ReportConstants.EMPTY_STRING);
				}
			}*/
		}else{
			for (int i=0 ; i < 12 ; i++) {
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
		}
		
	 // 3. Transfered by 
		if(transctionDetailvo.getFromPartyCode() != null){
			log.log(Log.INFO, "getReportParameters %%%%%%%%%% Transfered by ", 
					transctionDetailvo.getFromPartyCode());
			reportParameters.add(transctionDetailvo.getFromPartyCode());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
				
		// 4. Received by 		
				
		int toPartyCodeMaxLength = 9;
		StringBuilder toPartyCodeForReprt =new StringBuilder();  
		if(transctionDetailvo.getToPartyCode() != null){
			if(transctionDetailvo.getPartyType() != null && "G".equals(transctionDetailvo.getPartyType())){
				toPartyCodeForReprt.append("Y");
				toPartyCodeForReprt.append("Y");
				toPartyCodeMaxLength -= 2;
			}   
			log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transctionDetailvo.getToPartyCode().charAt(0));
			int toPartyCodeCount = 0;
			for (int i=0; i < transctionDetailvo.getToPartyCode().length(); i++) {
				String toPartyCode = String.valueOf(transctionDetailvo.getToPartyCode().charAt(i));
				toPartyCodeForReprt.append(toPartyCode);
				toPartyCodeCount++;
				if(toPartyCodeCount == toPartyCodeMaxLength){
					break;
				}
			}
			log.log(Log.INFO, "getReportParameters %%%%%%%%%% Received by ", 
					toPartyCodeForReprt.toString());
			reportParameters.add(toPartyCodeForReprt.toString());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
		//5. Date of Transfer
		//6. time of Transfer
		String trnDate = null;
		String trnTime = null;

		if(transctionDetailvo.getStrTxnDate() !=null){
			String datearr[] = transctionDetailvo.getStrTxnDate().toUpperCase().split("-");
			String txndate=datearr[0];
			String txnTime=datearr[1];
			
			if(txndate !=null){
				log.log(Log.INFO, "getReportParameters %%%%%%%%%% Date ", txndate);
				int txndateCount = 0;
				for (int i=0; i < txndate.length(); i++) {
					String txndateCode = String.valueOf(txndate.charAt(i));
					reportParameters.add(txndateCode);
					txndateCount++;
				}
				if (txndateCount < 9) {
					for (int i=0 ; i <(7-txndateCount); i++) {
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
				}
			}
			if(txnTime !=null){
				log.log(Log.INFO, "getReportParameters %%%%%%%%%% Time ", txnTime);
				int txnTimeCount = 0;
				for (int i=0; i < txnTime.length(); i++) {
					String txnTimeCode = String.valueOf(txnTime.charAt(i));
					reportParameters.add(txnTimeCode);
					txnTimeCount++;
				}
				if (txnTimeCount < 4) {
					for (int i=0 ; i <(4-txnTimeCount); i++) {
						reportParameters.add(ReportConstants.EMPTY_STRING);
					}
				}
			}
		}
		else{
			for (int i=0 ; i < 13 ; i++) {
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
		}

		//7. Transfer point		
		if(transctionDetailvo.getTransactionStationCode() != null){
			log.log(Log.INFO, "getReportParameters %%%%%%%%%% transfer point ", 
					transctionDetailvo.getTransactionStationCode());
			int trnsStationCount = 0;
			for (int i=0; i < transctionDetailvo.getTransactionStationCode().length(); i++) {
				String trnsStationCode = String.valueOf(transctionDetailvo.getTransactionStationCode().charAt(i));
				reportParameters.add(trnsStationCode);
				trnsStationCount++;
			}
			if (trnsStationCount < 3) {
				for (int i=0 ; i <(3-trnsStationCount); i++) {
					reportParameters.add(ReportConstants.EMPTY_STRING);
				}
			}
		}else{
			for (int i=0 ; i < 3 ; i++) {
				reportParameters.add(ReportConstants.EMPTY_STRING);
			}
		}
		
		//8.REMARKS
		if(transctionDetailvo.getTransactionRemark() != null && 
				!transctionDetailvo.getTransactionRemark().isEmpty()){
			reportParameters.add(transctionDetailvo.getTransactionRemark());
		}else{
			reportParameters.add(ReportConstants.EMPTY_STRING);
		}
		
		
		
		return reportParameters;
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

		Vector<Vector> ucrDetails = new Vector<Vector>();
		Collection<ULDTransactionDetailsVO> transctionDetails = new ArrayList<ULDTransactionDetailsVO>(data);
		log.log(Log.INFO, "%%%%%%%%%%%%%transctionDetails ", transctionDetails);
		Collection<ULDTransactionDetailsVO> txnDetails = new ArrayList<ULDTransactionDetailsVO>();
		/*
		 * Each page needs to have 10 ULD rows. New page will be set after 10th record in rpt
		 */
		//int txnDetailSize = transctionDetails.size();		
		
		//int UCRReportCount = 10; //As per new UCR report standard
		//if(txnDetailSize<UCRReportCount){
			txnDetails.addAll(transctionDetails);
		while(txnDetails.size()% 10 != 0){
				txnDetails.add(new ULDTransactionDetailsVO());
			//txnDetailSize++;
		}     
		//} 
		/*if(txnDetailSize>UCRReportCount){       
			int size=0;
			for(ULDTransactionDetailsVO detailVo:transctionDetails){
				if(size>=UCRReportCount){
					break;
				}
				txnDetails.add(detailVo);
				size++;
			}
		}*/
		
		log.log(Log.ALL, "txnDetails=========", txnDetails);
		int serialNumber =1;
		for(ULDTransactionDetailsVO transactionDetailsVO : txnDetails){
			if(serialNumber>10) 
				{
				serialNumber=1;
				}
			Vector<Object> row = new Vector<Object>();
			//1. Serial Number
			log.log(Log.INFO, "getReportData %%%%%%%%%%%%% serialNumber ", serialNumber);
			row.add(String.valueOf(serialNumber));
			
			//2. 3 letter ULD TYPE CODE 
			/*to add the 3 letter uld code. if any mismatch in lengh, then blank space will be added
			 * and if uld code not present also blank space will be added.
			 */
			if(transactionDetailsVO.getUldType() != null){
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getUldSerialNumber());
				for (int i=0; i < transactionDetailsVO.getUldType().length(); i++) {
					String uldType1 = String.valueOf(transactionDetailsVO.getUldType().charAt(i));
					row.add(uldType1);
				}

			}else{
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getUldSerialNumber());
				for (int i=0; i < 3; i++) {
					row.add(ReportConstants.EMPTY_STRING); 
				}
			}
			
			//3. 5 Digit ULD serial number
			if(transactionDetailsVO.getUldSerialNumber() != null){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% uldSerialNumber ", 
						transactionDetailsVO.getUldSerialNumber());
				int uldSerCount = 0;
				for (int i=0; i < transactionDetailsVO.getUldSerialNumber().length(); i++) {
					String uldSerNumber = String.valueOf(transactionDetailsVO.getUldSerialNumber().charAt(i));
					row.add(uldSerNumber);
					uldSerCount++;
				}
				if (uldSerCount < 5) {
					for (int i=0 ; i <(5-uldSerCount); i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}else{
				for (int i=0 ; i < 5 ; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}
			
			//4. 2 Letter ULD Owner Code
			if(transactionDetailsVO.getUldOwnerCode() != null){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% uldownerCode ", 
						transactionDetailsVO.getUldOwnerCode());
				for (int i=0; i < transactionDetailsVO.getUldOwnerCode().length(); i++) {
					String uldOwnerCode = String.valueOf(transactionDetailsVO.getUldOwnerCode().charAt(i));
					row.add(uldOwnerCode);
				}				
			}else{
				for (int i=0 ; i < 2 ; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}
			
			//5. ULD Support Equipment Number 
			// This has to be manually entered in the report so set empty string
			for (int i=0 ; i < 4 ; i++) {
				row.add(ReportConstants.EMPTY_STRING);
			}
			
			//6. 3 letter Final Destination
			
			if(transactionDetailsVO.getTxStationCode() != null &&
					!transactionDetailsVO.getTxStationCode().isEmpty()){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% finaldestination ",
						transactionDetailsVO.getTxStationCode());
				int txStationCount = 0;
				for (int i=0; i < transactionDetailsVO.getTxStationCode().length(); i++) {
					String txStationCode = String.valueOf(transactionDetailsVO.getTxStationCode().charAt(i));
					row.add(txStationCode);
					txStationCount++;
				}
				if (txStationCount < 3) {
					for (int i=0 ; i <(3-txStationCount); i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}else{
				for (int i=0 ; i < 3 ; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}
			
			//7. 3 letter DEMURRAGE CODE
			
			if(transactionDetailsVO.getUldConditionCode() != null){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% demurragecode ", 
						transactionDetailsVO.getUldConditionCode());
				int uldCondCount = 0;
				for (int i=0; i < transactionDetailsVO.getUldConditionCode().length(); i++) {
					String uldCondCode = String.valueOf(transactionDetailsVO.getUldConditionCode().charAt(i));
					row.add(uldCondCode);
					uldCondCount++;
				}
				if (uldCondCount < 3) {
					for (int i=0 ; i <(3-uldCondCount); i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}else{
				for (int i=0 ; i < 3 ; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}
			
			//8. Damage flag captured from screen ULD010
			log.log(Log.INFO, "getReportData %%%%%%%%%%%%% damage Flag ", 
					transactionDetailsVO.getDamageFlagFromScreen());
			if(ULDTransactionDetailsVO.FLAG_YES.equals(transactionDetailsVO.getDamageFlagFromScreen())){
				row.add(ULDTransactionDetailsVO.FLAG_YES);
			}else{
				row.add(ULDTransactionDetailsVO.FLAG_NO);      
			}
			
			//9. ODLN Code captured from screen ULD010			
			if(transactionDetailsVO.getOdlnCode()!=null &&
					!transactionDetailsVO.getOdlnCode().isEmpty()){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% ODLN CODE ", 				
												transactionDetailsVO.getOdlnCode());
				
				int odlnCodeCount = 0;
				for (int i=0; i < transactionDetailsVO.getOdlnCode().length(); i++) {
					String odlnCode = String.valueOf(transactionDetailsVO.getOdlnCode().charAt(i));
					row.add(odlnCode);
					odlnCodeCount++;
				}
				if (odlnCodeCount < 2) {
					for (int i=0 ; i <(2-odlnCodeCount); i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}else{
				for (int i=0 ; i < 2 ; i++) {
				row.add(ReportConstants.EMPTY_STRING);
				}     
			}
			
			//10. Damage remark captured from screen ULD010			
			if(transactionDetailsVO.getDamageRemark()!=null &&
					!transactionDetailsVO.getDamageRemark().isEmpty()){
				log.log(Log.INFO, "getReportData %%%%%%%%%%%%% Damage Remark ", 
						transactionDetailsVO.getDamageRemark()); 
				row.add(transactionDetailsVO.getDamageRemark());
			}else{
				row.add(ReportConstants.EMPTY_STRING);
			}
			serialNumber++;
			ucrDetails.add(row);
		}	

		log.log(Log.INFO, "ucrDetails", ucrDetails);
		return ucrDetails;
	}

	/**
	 * Method to obtain the sub-report data. There can be can be multiple
	 * sub-reports in a report. Each element in the outer-most Vector
	 * corresponds to one sub-report. Each element in the inner Vector
	 * corresponds to a row in the sub-report. The order in which the data is
	 * returned should match the order in which the fields are laid out in the
	 * report. This method returns the manifest details for SCC grouped AWBs.
	 * @param subReportData the data to be printed in the sub-report
	 * @param extraInfo the data required for formatting the data
	 * @return Vector<Vector<Vector>> the data in the report
	 */
	/*@Override
	public Vector<Vector<Vector>> getSubReportData(Collection subReportData,
			Collection extraInfo) {
		return null;
	}*/
}
