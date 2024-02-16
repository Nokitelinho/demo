/*
 * SaveMUCTrackingCommand.java Created on Aug 07, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.messaging.muctracking;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.messaging.MUCTrackingSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.messaging.MUCTrackingForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3045
 *
 */
public class SaveMUCTrackingCommand extends BaseCommand{
	
	/**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("MUC Tracking");

	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";

	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.messaging.muctracking";

	/**
	 * Target if success
	 */
	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String BLANK = "";
	
	private static final String LOANED = "L";
	
	private static final String STR_LOANED = "Loan";
	
	private static final String BORROWED = "B";
	
	private static final String STR_BORROWED = "Borrow";
	
	private static final String SPACE = " ";
	
	private static final String UPDATED = "U";
	
	private static final String RESENT = "R";
	
	private static final String SENT = "S";
	
	private static final String STRUPDATED = "Updated";
	
	private static final String STRRESENT = "Re-Sent";
	
	private static final String STRSENT = "Sent";
	
	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
		
		
	
	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering("ListMUCTrackingCommand", "execute");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MUCTrackingForm mucTrackingForm = (MUCTrackingForm) invocationContext.screenModel;
		MUCTrackingSession mucTrackingSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		TransactionListVO transactionListVO = mucTrackingSession.getListDisplayColl();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String companyCode = logonAttributes.getCompanyCode();
		transactionFilterVO.setCompanyCode(companyCode);
		
		String[] uldNumber=mucTrackingForm.getUldNumber();
		String[] date=mucTrackingForm.getMucDate();
		String[] time=mucTrackingForm.getMucTime();
		String[] txnAirport=mucTrackingForm.getTxnAirport();
		String[] crnNumber=mucTrackingForm.getCRN();
		String[] destAirport=mucTrackingForm.getDestAirport();
		String[] condition=mucTrackingForm.getCondition();
		String[] iataStatus=mucTrackingForm.getIataStatus();
				
		log.log(Log.FINE, "uldNumber------------>>>>>>", uldNumber.length);
		log.log(Log.FINE, "date------------>>>>>>", date.length);
		log.log(Log.FINE, "crnNumber------------>>>>>>", crnNumber.length);
		log.log(Log.FINE, "condition------------>>>>>>", condition.length);
		log.log(Log.FINE, "time------------>>>>>>", time.length);
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = transactionListVO.getUldTransactionsDetails();	
		Collection<ErrorVO> errs = null;	
						
		
		Collection<ULDTransactionDetailsVO> uldTxnDetailsVOs = new ArrayList<ULDTransactionDetailsVO>();
	//	Collection<ULDTransactionDetailsVO> uldTxnDetailsforCRN = new ArrayList<ULDTransactionDetailsVO>();
		int count = 0;
		for(ULDTransactionDetailsVO uldTxnDtlsVO : uldTransactionDetailsVOs){	
					
					if (!(BLANK).equalsIgnoreCase(uldNumber[count])
							&& uldNumber[count] != null) {
						uldTxnDtlsVO.setUldNumber(uldNumber[count].toUpperCase());
					}					
					if(STR_LOANED.equals(uldTxnDtlsVO.getTransactionType())){											
				    	if(!(BLANK).equalsIgnoreCase(txnAirport[count]) && txnAirport[count] != null){
				    		uldTxnDtlsVO.setTransactionStationCode(txnAirport[count].toUpperCase());
				    	}		    					
					}else{
						if(!(BLANK).equalsIgnoreCase(txnAirport[count]) && txnAirport[count] != null){
				    		uldTxnDtlsVO.setReturnStationCode(txnAirport[count].toUpperCase());
				    	}
					}						
					if(STR_LOANED.equals(uldTxnDtlsVO.getTransactionType())){									
						LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				    	if(!(BLANK).equalsIgnoreCase(date[count]) && date[count] != null){
				    		if(!(BLANK).equalsIgnoreCase(time[count]) && time[count] != null){
				    			StringBuilder txndat = new StringBuilder();    			
				    			txndat.append(date[count]).append(SPACE).append(time[count]);
				    			log.log(Log.FINE,
										"TransactionDate ~~~~~~~~~~~~~~~~~",
										txndat.toString());
								uldTxnDtlsVO.setTransactionDate(ldte.setDateAndTime(txndat.toString()));
				    		}else{
				    			uldTxnDtlsVO.setTransactionDate(ldte.setDate(date[count]));
				    		}
				    		uldTxnDtlsVO.setStrTxnDate(date[count]);
				    		uldTxnDtlsVO.setStrTxnTime(time[count]);
						 }else {
						    uldTxnDtlsVO.setStrTxnDate(BLANK);
						 }					
					}else{
						LocalDate ldte = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				    	if(!(BLANK).equalsIgnoreCase(date[count]) && date[count] != null){
				    		if(!(BLANK).equalsIgnoreCase(time[count]) && time[count] != null){
				    			StringBuilder rtndat = new StringBuilder();    			
				    			rtndat.append(date[count]).append(SPACE).append(time[count]);    			
				    			uldTxnDtlsVO.setReturnDate(ldte.setDateAndTime(rtndat.toString()));
				    		}else{
				    			uldTxnDtlsVO.setReturnDate(ldte.setDate(date[count]));
				    		}
				    		uldTxnDtlsVO.setStrRetDate(date[count]);
				    		uldTxnDtlsVO.setStrRetTime(time[count]);
						 }else {
						    uldTxnDtlsVO.setStrRetDate(BLANK);
						 }			
					}							
					if (!(BLANK).equalsIgnoreCase(crnNumber[count])
							&& crnNumber[count] != null) {
						uldTxnDtlsVO.setControlReceiptNumber(crnNumber[count].toUpperCase());
					}									
					if(STR_LOANED.equals(uldTxnDtlsVO.getTransactionType())){											
				    	if(!(BLANK).equalsIgnoreCase(destAirport[count]) && destAirport[count] != null){
				    		uldTxnDtlsVO.setTxStationCode(destAirport[count].toUpperCase());
				    	}		    					
					}else{
						if(!(BLANK).equalsIgnoreCase(destAirport[count]) && destAirport[count] != null){
				    		uldTxnDtlsVO.setReturnStationCode(destAirport[count].toUpperCase());
				    	}
					}															
					if (!(BLANK).equalsIgnoreCase(condition[count])
							&& condition[count] != null) {
						uldTxnDtlsVO.setUldConditionCode(condition[count].toUpperCase());
					}	
					count++;
					uldTxnDetailsVOs.add(uldTxnDtlsVO);
		}
		log.log(Log.FINE, "uldTxnDetailsVOs before setting------------>>>>>>",
				uldTxnDetailsVOs);
		transactionListVO.setUldTransactionsDetails(uldTxnDetailsVOs);
		//Validate Destination Airport Codes
		Collection<String> airports = new ArrayList<String>();
		if (destAirport != null && destAirport.length > 0) {
			log.log(Log.FINE, "destAirport------------>>>>>>", destAirport.length);
			for(int i=0;i < destAirport.length;i++){
				if (destAirport[i] != null
						&& destAirport[i].trim().length() > 0) {
					if(!airports.contains(destAirport[i])){
						airports.add(destAirport[i]);
					}					
				}
			}
		}	
		log.log(Log.INFO, "destAirport are ---->>>>>", airports);
		errs = validateAirportCodes(companyCode, airports);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			log.log(Log.INFO, "destAirport are SAVE_FAILURE---->>>>>");
			invocationContext.addAllError(errors);	
			mucTrackingSession.setListDisplayColl(transactionListVO);
			invocationContext.target = SAVE_FAILURE;							
			return;
		}
		//Validate Txn Airport Codes
		Collection<String> txnairports = new ArrayList<String>();
		if (txnAirport != null && txnAirport.length > 0) {
			log.log(Log.FINE, "txnAirport------------>>>>>>", txnAirport.length);
			for(int i=0;i < txnAirport.length;i++){
				if (txnAirport[i] != null
						&& txnAirport[i].trim().length() > 0) {
					if(!txnairports.contains(txnAirport[i])){
						txnairports.add(txnAirport[i]);
					}					
				}
			}
		}	
		log.log(Log.INFO, "txnAirport are ---->>>>>", txnairports);
		errs = validateAirportCodes(companyCode, txnairports);
		errors.addAll(errs);
		if (errors != null && errors.size() > 0) {
			log.log(Log.INFO, "txnAirport are SAVE_FAILURE---->>>>>");
			invocationContext.addAllError(errors);	
			mucTrackingSession.setListDisplayColl(transactionListVO);
			invocationContext.target = SAVE_FAILURE;							
			return;
		}
		Collection<ULDTransactionDetailsVO> uldTxnVOs = new ArrayList<ULDTransactionDetailsVO>();
		int statusCount = 0;
		for(ULDTransactionDetailsVO uldTxnVO : uldTxnDetailsVOs){
			SharedDefaultsDelegate sharedDefaultsDelegate = 
				new SharedDefaultsDelegate();
			Map<String, Collection<OneTimeVO>> oneTimeValues = null;
			Collection<ErrorVO> error = new ArrayList<ErrorVO>();
			Collection<String> oneTimeContentList = new ArrayList<String>();
			oneTimeContentList.add(TXNTYPE_ONETIME);
			try {
				log.log(Log.FINE, "****inside try**************************",
						oneTimeContentList);
				oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
						companyCode, oneTimeContentList);
			} catch (BusinessDelegateException businessDelegateException) {
				log.log(Log.FINE,"*****in the exception");
				businessDelegateException.getMessage();
				error = handleDelegateException(businessDelegateException);
			}
			log.log(Log.INFO, "oneTimeValues TXNTYPE_ONETIME---> ",
					oneTimeValues);
			Collection<OneTimeVO> txnType = oneTimeValues.get(TXNTYPE_ONETIME);
			for(OneTimeVO oneTimeVO : txnType){
				if(uldTxnVO.getTransactionType().equals(oneTimeVO.getFieldDescription())){
					uldTxnVO.setTransactionType(oneTimeVO.getFieldValue());
				}
			}
			if ((UPDATED).equals(iataStatus[statusCount])
					&& iataStatus[statusCount] != null) {
				uldTxnVO.setMucIataStatus(UPDATED);
			}else{
				if(uldTxnVO.getMucIataStatus().equals(STRUPDATED)){
					uldTxnVO.setMucIataStatus(UPDATED);
				}else if(uldTxnVO.getMucIataStatus().equals(STRRESENT)){
					uldTxnVO.setMucIataStatus(RESENT);
				}else if(uldTxnVO.getMucIataStatus().equals(STRSENT)){
					uldTxnVO.setMucIataStatus(SENT);
				}
			}
			log.log(Log.FINE, "uldTxnDtlsVO.getMucIataStatus()------>>>>>>",
					uldTxnVO.getMucIataStatus());
			statusCount++;
			uldTxnVOs.add(uldTxnVO);
		}
		transactionListVO.setUldTransactionsDetails(uldTxnVOs);
		try {
			if(uldTxnDetailsVOs != null && uldTxnDetailsVOs.size() > 0){
			uldDefaultsDelegate.updateULDTransaction(uldTxnDetailsVOs);		
			}
		}catch (BusinessDelegateException businessDelegateException) {
		  businessDelegateException.getMessage();
		  errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				mucTrackingSession.setListDisplayColl(transactionListVO);
		}else {
				mucTrackingSession.setListFilterVO(null);
				mucTrackingSession.setListDisplayColl(null);
		}
		
	//Modified by a-5165 for ICRD-31854 for Save Message
		invocationContext.addError(new ErrorVO("uld.defaults.loanborrowuld.msg.muc.savedsuccessfully"));
	//Modified by a-5165 for ICRD-31854 for Save Message-ends
	invocationContext.target =SAVE_SUCCESS;	
	
	}
	
	private Collection<ErrorVO> validateAirportCodes(String companyCode,
			Collection<String> airports) {
		log.log(Log.FINE, "validateAirportCodes__________________");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			new AreaDelegate().validateAirportCodes(companyCode, airports);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		return errors;
	}
}
