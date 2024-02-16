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
public class UCRAttributeBuilder extends AttributeBuilderAdapter {

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

		reportColumns.add("ADDRESS");
		reportColumns.add("ORIGINATOR");

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

		reportColumns.add("DATEOFTRANSFER");
		reportColumns.add("DATEOFTRANSFER1");
		reportColumns.add("DATEOFTRANSFER2");
		reportColumns.add("DATEOFTRANSFER3");
		reportColumns.add("DATEOFTRANSFER4");
		reportColumns.add("DATEOFTRANSFER5");
		reportColumns.add("DATEOFTRANSFER6");
		reportColumns.add("LOCALTIME");
		reportColumns.add("LOCALTIME1");
		reportColumns.add("LOCALTIME2");
		reportColumns.add("LOCALTIME3");

		reportColumns.add("RECIEVEDBY");
		reportColumns.add("RECIEVEDBY1");
		reportColumns.add("RECIEVEDBY2");
		reportColumns.add("RECIEVEDBY3");
		reportColumns.add("RECIEVEDBY4");
		reportColumns.add("RECIEVEDBY5");
		reportColumns.add("RECIEVEDBY6");
		reportColumns.add("RECIEVEDBY7");
		reportColumns.add("RECIEVEDBY8");
		reportColumns.add("TRANSFEREDBY");
		reportColumns.add("TRANSFEREDBY1");
		reportColumns.add("TRANSFEREDBY2");
		reportColumns.add("TRANSFEREDBY3");
		reportColumns.add("TRANSFEREDBY4");
		reportColumns.add("TRANSFEREDBY5");
		reportColumns.add("TRANSFEREDBY6");
		reportColumns.add("TRANSFEREDBY7");
		reportColumns.add("TRANSFEREDBY8");

		reportColumns.add("TRANSFERPOINT");
		reportColumns.add("TRANSFERPOINT1");
		reportColumns.add("TRANSFERPOINT2");
		reportColumns.add("CONTROLRECEIPTNOA1");
		reportColumns.add("CONTROLRECEIPTNOA2");
		reportColumns.add("CONTROLRECEIPTNOA3");
		reportColumns.add("CONTROLRECEIPTNOA4");
		reportColumns.add("CONTROLRECEIPTNOA5");
		reportColumns.add("CONTROLRECEIPTNOA6");
		reportColumns.add("CONTROLRECEIPTNOA7");
		reportColumns.add("CONTROLRECEIPTNOA8");
		reportColumns.add("CONTROLRECEIPTNOA9");
		reportColumns.add("CONTROLRECEIPTNOA10");
		reportColumns.add("CONTROLRECEIPTNOA11");
		reportColumns.add("CONTROLRECEIPTNOA12");
		reportColumns.add("FINALDESTN1");
		reportColumns.add("FINALDESTN11");
		reportColumns.add("FINALDESTN12");
		reportColumns.add("CONDITIONCODE1");
		reportColumns.add("CONDITIONCODE11");
		reportColumns.add("CONDITIONCODE12");

		reportColumns.add("IATACODPREFIX2");
		reportColumns.add("IATACODPREFIX21");
		reportColumns.add("IATACODPREFIX22");
		reportColumns.add("IATACODSERNO2");
		reportColumns.add("IATACODSERNO21");
		reportColumns.add("IATACODSERNO22");
		reportColumns.add("IATACODSERNO23");
		reportColumns.add("IATACODSERNO24");
		reportColumns.add("IATACODOWNER2");
		reportColumns.add("IATACODOWNER21");
		reportColumns.add("CONTROLRECEIPTNOB1");
		reportColumns.add("CONTROLRECEIPTNOB2");
		reportColumns.add("CONTROLRECEIPTNOB3");
		reportColumns.add("CONTROLRECEIPTNOB4");
		reportColumns.add("CONTROLRECEIPTNOB5");
		reportColumns.add("CONTROLRECEIPTNOB6");
		reportColumns.add("CONTROLRECEIPTNOB7");
		reportColumns.add("CONTROLRECEIPTNOB8");
		reportColumns.add("CONTROLRECEIPTNOB9");
		reportColumns.add("CONTROLRECEIPTNOB10");
		reportColumns.add("CONTROLRECEIPTNOB11");
		reportColumns.add("CONTROLRECEIPTNOB12");
		reportColumns.add("FINALDESTN2");
		reportColumns.add("FINALDESTN21");
		reportColumns.add("FINALDESTN22");
		reportColumns.add("CONDITIONCODE2");
		reportColumns.add("CONDITIONCODE21");
		reportColumns.add("CONDITIONCODE22");

		reportColumns.add("IATACODPREFIX3");
		reportColumns.add("IATACODPREFIX31");
		reportColumns.add("IATACODPREFIX32");
		reportColumns.add("IATACODSERNO3");
		reportColumns.add("IATACODSERNO31");
		reportColumns.add("IATACODSERNO32");
		reportColumns.add("IATACODSERNO33");
		reportColumns.add("IATACODSERNO34");
		reportColumns.add("IATACODOWNER3");
		reportColumns.add("IATACODOWNER31");
		reportColumns.add("CONTROLRECEIPTNOC1");
		reportColumns.add("CONTROLRECEIPTNOC2");
		reportColumns.add("CONTROLRECEIPTNOC3");
		reportColumns.add("CONTROLRECEIPTNOC4");
		reportColumns.add("CONTROLRECEIPTNOC5");
		reportColumns.add("CONTROLRECEIPTNOC6");
		reportColumns.add("CONTROLRECEIPTNOC7");
		reportColumns.add("CONTROLRECEIPTNOC8");
		reportColumns.add("CONTROLRECEIPTNOC9");
		reportColumns.add("CONTROLRECEIPTNOC10");
		reportColumns.add("CONTROLRECEIPTNOC11");
		reportColumns.add("CONTROLRECEIPTNOC12");
		reportColumns.add("FINALDESTN3");
		reportColumns.add("FINALDESTN31");
		reportColumns.add("FINALDESTN32");
		reportColumns.add("CONDITIONCODE3");
		reportColumns.add("CONDITIONCODE31");
		reportColumns.add("CONDITIONCODE32");

	//	reportColumns.add("REMARKS");

		reportColumns.add("ULDRELEASEDEMTY1");
		reportColumns.add("ULDRELEASEDLOADED1");

		reportColumns.add("ULDRELEASEDEMTY2");
		reportColumns.add("ULDRELEASEDLOADED2");

		reportColumns.add("AWBNO1");
		reportColumns.add("AWBNO2");

		reportColumns.add("ULDSUPPORTNETS");
		reportColumns.add("ULDSUPPORTDOORS");
		reportColumns.add("ULDSUPPORTSTRAPS");
		reportColumns.add("ULDSUPPORTFITTINGS");

		reportColumns.add("CUSTOMER");
		reportColumns.add("ACCNO");
		reportColumns.add("CUSTADDRESS");

		reportColumns.add("DEMERAGEDATE");
		reportColumns.add("DEMERAGETIME");

		reportColumns.add("ULDRELEASERECIVEDATE");
		reportColumns.add("ULDRELEASERECIEVETIME");
		reportColumns.add("ULDRELEASETRANSFERDATE");
		reportColumns.add("ULDRELEASETRANSFERTIME");

		reportColumns.add("ULDRETURNRECIVEDATE");
		reportColumns.add("ULDRETURNRECIEVETIME");
		reportColumns.add("ULDRETURNTRANSFERDATE");
		reportColumns.add("ULDRETURNTRANSFERTIME");

		reportColumns.add("EXCESSNODAYS");
		reportColumns.add("EXCESSPERDAYCHARGE");
		reportColumns.add("OTHERCHARGE");
		reportColumns.add("OTHERCHARGELOSS");
		reportColumns.add("OTHERCHARGEDEMRG");
		reportColumns.add("TOTALCHARGE");

		reportColumns.add("REMARKS");

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
	/*@Override
	public Vector<Object> getReportParameters(Collection parameters,
			Collection extraInfo) {
		return null;
	}*/

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

		String nextCrn="";
		String firstCrn="";
		String previousCrn="";
		int count = 0;
		String txnRemarks  = null;
		for (ULDTransactionDetailsVO transactionDetailsVO : transctionDetails) {
			firstCrn = transactionDetailsVO.getControlReceiptNumber().substring(5,transactionDetailsVO.getControlReceiptNumber().length());
			Collection<ULDTransactionDetailsVO> sameDetails = new ArrayList<ULDTransactionDetailsVO>();
			count=0;
			log.log(Log.ALL, "firstCrn=========", firstCrn);
			log.log(Log.ALL, "nextCrn=========", nextCrn);
			if(!nextCrn.equals(firstCrn)){
				log.log(Log.ALL, "not equal=========");
				for(ULDTransactionDetailsVO vo : transctionDetails) {
					previousCrn=vo.getControlReceiptNumber().substring(5,vo.getControlReceiptNumber().length());
					if(firstCrn.equals(previousCrn)){
						count++;
						sameDetails.add(vo);
					}
				}
				if(count==1){
					txnDetails.addAll(sameDetails);
					txnDetails.add(new ULDTransactionDetailsVO());
					txnDetails.add(new ULDTransactionDetailsVO());
				}else if(count==2){
					txnDetails.addAll(sameDetails);
					txnDetails.add(new ULDTransactionDetailsVO());
				}else if(count==3){
					txnDetails.addAll(sameDetails);
				}
			}
			nextCrn=firstCrn;
		}
		log.log(Log.ALL, "txnDetails=========", txnDetails);
		int flag = 0;
		String ctrlno = "";

		Vector<Object> row = null;
		for(ULDTransactionDetailsVO transactionDetailsVO : txnDetails){
			if(flag == 3){
				flag = 0;
			}
			log.log(Log.INFO, "%%%%%%%%%%%%%transactionDetailsVO ",
					transactionDetailsVO);
			log.log(Log.INFO, "%%%%%%%%%%%%%flag ", flag);
			flag++;
			if(flag == 1){

				log.log(Log.INFO,"%%%%%%%%%%%%%1 ");

				row = new Vector<Object>();
				//ADDRESS
				row.add(ReportConstants.EMPTY_STRING);
				//ORIGINATOR
				row.add(ReportConstants.EMPTY_STRING);
			}
			//IATACODPREFIX1
			/*to add the 3 letter uld code. if any mismatch in lengh, then blank space will be added
			 * and if uld code not present also blank space will be added.
			 */
			if(transactionDetailsVO.getUldType() != null){
				for (int i=0; i < transactionDetailsVO.getUldType().length(); i++) {
					String uldType1 = String.valueOf(transactionDetailsVO.getUldType().charAt(i));
					row.add(uldType1);
				}

			}else{
				for (int i=0; i < 3; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}


			//IATACODSERNO1
			if(transactionDetailsVO.getUldSerialNumber() != null){
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getUldSerialNumber().charAt(0));
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

			//IATACODOWNER1
			if(transactionDetailsVO.getUldOwnerCode() != null){
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getUldOwnerCode().charAt(0));
				int uldOwnerCount = 0;
				for (int i=0; i < transactionDetailsVO.getUldOwnerCode().length(); i++) {
					String uldOwnerCode = String.valueOf(transactionDetailsVO.getUldOwnerCode().charAt(i));
					row.add(uldOwnerCode);
					uldOwnerCount++;
				}
				if (uldOwnerCount < 2) {
					for (int i=0 ; i < (2-uldOwnerCount); i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}else{
				for (int i=0 ; i < 2 ; i++) {
					row.add(ReportConstants.EMPTY_STRING);
				}
			}

			if(flag == 1){

				log.log(Log.INFO,"%%%%%%%%%%%%%1 ");
				String trnDate = null;
				String trnTime = null;

				if(transactionDetailsVO.getStrTxnDate() !=null){
					String datearr[] = transactionDetailsVO.getStrTxnDate().toUpperCase().split("-");
					String txndate=datearr[0];
					String txnTime=datearr[1];
					if(txndate !=null){
						log.log(Log.INFO, "%%%%%%%%%%%%%1 ", txndate);
						int txndateCount = 0;
						for (int i=0; i < txndate.length(); i++) {
							String txndateCode = String.valueOf(txndate.charAt(i));
							row.add(txndateCode);
							txndateCount++;
						}
						if (txndateCount < 7) {
							for (int i=0 ; i <(7-txndateCount); i++) {
								row.add(ReportConstants.EMPTY_STRING);
							}
						}
					}
					if(txnTime !=null){
						log.log(Log.INFO, "%%%%%%%%%%%%%1 ", txnTime);
						int txnTimeCount = 0;
						for (int i=0; i < txnTime.length(); i++) {
							String txnTimeCode = String.valueOf(txnTime.charAt(i));
							row.add(txnTimeCode);
							txnTimeCount++;
						}
						if (txnTimeCount < 4) {
							for (int i=0 ; i <(4-txnTimeCount); i++) {
								row.add(ReportConstants.EMPTY_STRING);
							}
						}
					}
				}
				else{
					for (int i=0 ; i < 11 ; i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}

				//RECIEVEDBY
				int toPartyCodeMaxLength = 9;
				if(transactionDetailsVO.getToPartyCode() != null){
					if(transactionDetailsVO.getPartyType() != null && "G".equals(transactionDetailsVO.getPartyType())){
						row.add("Y");
						row.add("Y");
						toPartyCodeMaxLength -= 2;
					}
					log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getToPartyCode().charAt(0));
					int toPartyCodeCount = 0;
					for (int i=0; i < transactionDetailsVO.getToPartyCode().length(); i++) {
						String toPartyCode = String.valueOf(transactionDetailsVO.getToPartyCode().charAt(i));
						row.add(toPartyCode);
						toPartyCodeCount++;
						if(toPartyCodeCount == toPartyCodeMaxLength){
							break;
						}

					}
					if (toPartyCodeCount < toPartyCodeMaxLength) {
						for (int i=0 ; i < (toPartyCodeMaxLength-toPartyCodeCount); i++) {
							row.add(ReportConstants.EMPTY_STRING);
						}
					}
				}else{
					for (int i=0 ; i < toPartyCodeMaxLength ; i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}

				//TRANSFEREDBY
				if(transactionDetailsVO.getFromPartyCode() != null){
					log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getFromPartyCode().charAt(0));
					int fromPartyCodeCount = 0;
					for (int i=0; i < transactionDetailsVO.getFromPartyCode().length(); i++) {
						String fromPartyCode = String.valueOf(transactionDetailsVO.getFromPartyCode().charAt(i));
						row.add(fromPartyCode);
						fromPartyCodeCount++;
					}
					if (fromPartyCodeCount < 9) {
						for (int i=0 ; i <(9-fromPartyCodeCount); i++) {
							row.add(ReportConstants.EMPTY_STRING);
						}
					}
				}else{
					for (int i=0 ; i < 9 ; i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
				//TRANSFERPOINT
				if(transactionDetailsVO.getTransactionStationCode() != null){
					log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getTransactionStationCode().charAt(0));
					int trnsStationCount = 0;
					for (int i=0; i < transactionDetailsVO.getTransactionStationCode().length(); i++) {
						String trnsStationCode = String.valueOf(transactionDetailsVO.getTransactionStationCode().charAt(i));
						row.add(trnsStationCode);
						trnsStationCount++;
					}
					if (trnsStationCount < 3) {
						for (int i=0 ; i <(3-trnsStationCount); i++) {
							row.add(ReportConstants.EMPTY_STRING);
						}
					}
				}else{
					for (int i=0 ; i < 3 ; i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			}
				//CONTROLRECEIPTNO
				if(transactionDetailsVO.getControlReceiptNumber() != null){
					log.log(Log.INFO, "%%%%%%%%%% receipt",
							transactionDetailsVO.getControlReceiptNumber());
					ctrlno = transactionDetailsVO.getControlReceiptNumber().toString();
					/*ctrlno = ctrlno.substring(5);
					log.log(Log.INFO,"%%%%%%%%%% receipt"+ctrlno);
					StringBuilder ctrlNoBuilder = new StringBuilder(ctrlno.charAt(0));
					ctrlNoBuilder.append("   ");
					ctrlNoBuilder.append(ctrlno.charAt(1));
					ctrlNoBuilder.append("    ");
					ctrlNoBuilder.append(ctrlno.charAt(2));
					ctrlNoBuilder.append("   ");
					ctrlNoBuilder.append(ctrlno.charAt(3));
					ctrlNoBuilder.append("   ");
					ctrlNoBuilder.append(ctrlno.charAt(4));
					ctrlNoBuilder.append("   ");
					ctrlNoBuilder.append(ctrlno.charAt(5));
					ctrlNoBuilder.append("   ");
					ctrlNoBuilder.append(ctrlno.charAt(6));
					ctrlNoBuilder.append("    ");
					row.add(ctrlNoBuilder.toString());	*/

					int controlReceiptNumberCount = 0;
					for (int i=0; i < transactionDetailsVO.getControlReceiptNumber().length(); i++) {
						String controlReceiptNumber = String.valueOf(transactionDetailsVO.getControlReceiptNumber().charAt(i));
						row.add(controlReceiptNumber);
						controlReceiptNumberCount++;
					}
					if (controlReceiptNumberCount < 12) {
						for (int i=0 ; i <(12-controlReceiptNumberCount); i++) {
							row.add(ReportConstants.EMPTY_STRING);
						}
					}
				}else{
					for (int i=0 ; i < 12 ; i++) {
						row.add(ReportConstants.EMPTY_STRING);
					}
				}
			//FINALDESTN1
			if(transactionDetailsVO.getTxStationCode() != null){
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getTxStationCode().charAt(0));
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
			//CONDITIONCODE1
			if(transactionDetailsVO.getUldConditionCode() != null){
				log.log(Log.INFO, "%%%%%%%%%%%%%1 ", transactionDetailsVO.getUldConditionCode().charAt(0));
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
			if(flag == 3){
				log.log(Log.INFO,"%%%%%%%%%%%%%3 ");

				//REMARKS
//				if(transactionDetailsVO.getTransactionRemark() != null && transactionDetailsVO.getTransactionRemark().trim().length() >0){
//					row.add(transactionDetailsVO.getTransactionRemark());
//				}else{
//				row.add(ReportConstants.EMPTY_STRING);
//				}
				//ULDRELEASEDEMTY1
				//row.add("E");
				row.add(ReportConstants.EMPTY_STRING);
				/*
				if(transactionDetailsVO.isEmpty()){
					row.add("E");
				}else{
					row.add("L");
				}
				*/

				//ULDRELEASEDLOADED1
				row.add(ReportConstants.EMPTY_STRING);
				//ULDRELEASEDEMTY2
				row.add(ReportConstants.EMPTY_STRING);
				//ULDRELEASEDLOADED2
				row.add(ReportConstants.EMPTY_STRING);
				//AWBNO1
				row.add(ReportConstants.EMPTY_STRING);
				//AWBNO2
				row.add(ReportConstants.EMPTY_STRING);

				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);

				//CUSTOMER
				row.add(ReportConstants.EMPTY_STRING);
				//ACCNO
				row.add(ReportConstants.EMPTY_STRING);
				//CUSTADDRESS
				row.add(ReportConstants.EMPTY_STRING);

				//DEMERAGEDATE
				row.add(ReportConstants.EMPTY_STRING);
				//DEMERAGETIME
				row.add(ReportConstants.EMPTY_STRING);

				//ULDRELEASERECIVEDATE
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);

				//ULDRETURNRECIVEDATE
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);

				//ULDRETURNRECIVEDATE
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				row.add(ReportConstants.EMPTY_STRING);
				ucrDetails.add(row);
			}
		}
		if(flag == 1 || flag == 2){
			int k;
			for(k = 0 ; k < 5 ; k++){
				row.add(ReportConstants.EMPTY_STRING);
			}
			if(flag == 1){
				for(k = 0 ; k < 5 ; k++){
					row.add(ReportConstants.EMPTY_STRING);
				}
			}
			log.log(Log.INFO, "%%%%%%%%%%%%% flag ", flag);
				//REMARKS
			//row.add(ReportConstants.EMPTY_STRING);
			//ULDRELEASEDEMTY1
			//if(transactionDetailsVO.isEmpty()){
				row.add("E");
			//}else{
				//row.add("L");
			//}
			//ULDRELEASEDLOADED1
			row.add(ReportConstants.EMPTY_STRING);
			//ULDRELEASEDEMTY2
			row.add(ReportConstants.EMPTY_STRING);
			//ULDRELEASEDLOADED2
			row.add(ReportConstants.EMPTY_STRING);
			//AWBNO1
			row.add(ReportConstants.EMPTY_STRING);
			//AWBNO2
			row.add(ReportConstants.EMPTY_STRING);

			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);

			//CUSTOMER
			row.add(ReportConstants.EMPTY_STRING);
			//ACCNO
			row.add(ReportConstants.EMPTY_STRING);
			//CUSTADDRESS
			row.add(ReportConstants.EMPTY_STRING);

			//DEMERAGEDATE
			row.add(ReportConstants.EMPTY_STRING);
			//DEMERAGETIME
			row.add(ReportConstants.EMPTY_STRING);

			//ULDRELEASERECIVEDATE
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);

			//ULDRETURNRECIVEDATE
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);

			//ULDRETURNRECIVEDATE
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			row.add(ReportConstants.EMPTY_STRING);
			ucrDetails.add(row);
		}

		if(transctionDetails != null && transctionDetails.size() >0){

			txnRemarks = transctionDetails.iterator().next().getTransactionRemark();
			if(txnRemarks != null && txnRemarks.trim().length()>0 && txnRemarks.trim().length() >35  ){
				txnRemarks = txnRemarks.substring(0,35);
			}

		}
			//txnRemarks = transctionDetails.iterator().next().getTransactionRemark().substring(0,20);
			if(txnRemarks != null){
				 row.add(txnRemarks);
			}else{
			 row.add(ReportConstants.EMPTY_STRING);
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
