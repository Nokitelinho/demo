/*
 * ScreenLoadListULDTransactionCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.transaction.listuldtransaction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.transaction.ListULDTransactionSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.transaction.ListULDTransactionForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ScreenLoadListULDTransactionCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	
	/**
	 * The Module Name
	 */
	private static final String MODULE_NAME = "uld.defaults";
	
	/**
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
	
	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
    
	
			  
	private static final String TXNTYPE_ONETIME = "uld.defaults.TxnType";
	private static final String TXNNATURE_ONETIME = "uld.defaults.TxnNature";
	private static final String PARTYTYPE_ONETIME = "uld.defaults.PartyType";
	private static final String ACCESSCODE_ONETIME = "uld.defaults.accessoryCode";
	private static final String TXNSTATUS_ONETIME = "uld.defaults.transactionStatus";
    //added by a-3045 for CR QF1142 starts
	private static final String MUCSTATUS_ONETIME = "uld.defaults.mucstatus";
    //added by a-3045 for CR QF1142 ends
	private static final String RETURN_FLAG = "R";
     
    
   /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = "";
		String stationCode = "";
		String userId = "";
		companyCode = logonAttributes.getCompanyCode();		
		userId = logonAttributes.getUserId();
		
		TransactionFilterVO transactionFilterVO = new TransactionFilterVO();
		ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		
		listULDTransactionSession.setCompanyCode(companyCode);
		listULDTransactionSession.setStationCode(stationCode);
		listULDTransactionSession.setUserId(userId);
		listULDTransactionSession.setPageURL(null);
		listULDTransactionSession.setULDFlightMessageReconcileDetailsVO(null);
		
		listULDTransactionForm.setLeaseOrReturnFlg(RETURN_FLAG);
		
		
		
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(companyCode);
		
				
		Collection<OneTimeVO> txnTypes = oneTimeValues.get(TXNTYPE_ONETIME);
		log.log(Log.FINE, "****txnTypes  OneTime******", txnTypes);
		listULDTransactionSession.setTxnTypes(txnTypes);
		
		Collection<OneTimeVO> txnNatures = oneTimeValues.get(TXNNATURE_ONETIME);
		log.log(Log.FINE, "****txnNatures  OneTime******", txnNatures);
		listULDTransactionSession.setTxnNatures(txnNatures);
		
		Collection<OneTimeVO> partyTypes = oneTimeValues.get(PARTYTYPE_ONETIME);
		log.log(Log.FINE, "****partyTypes  OneTime******", partyTypes);
		listULDTransactionSession.setPartyTypes(partyTypes);
		
		Collection<OneTimeVO> accessCodes = oneTimeValues.get(ACCESSCODE_ONETIME);
		log.log(Log.FINE, "****accessCodes  OneTime******", accessCodes);
		listULDTransactionSession.setAccessoryCodes(accessCodes);
		
		Collection<OneTimeVO> txnStatus = oneTimeValues.get(TXNSTATUS_ONETIME);
		log.log(Log.FINE, "****txnStatus  OneTime******", txnStatus);
		listULDTransactionSession.setTxnStatus(txnStatus);
		
	    //added by a-3045 for CR QF1142 starts
		Collection<OneTimeVO> mucStatus = oneTimeValues.get(MUCSTATUS_ONETIME);
		log.log(Log.FINE, "****txnStatus  OneTime******", mucStatus);
		listULDTransactionSession.setMUCStatus(mucStatus);
	    //added by a-3045 for CR QF1142 ends
		
	    transactionFilterVO.setCompanyCode(listULDTransactionSession.getCompanyCode());
	    transactionFilterVO.setTransactionType("ALL");
	    
	    
	    if(logonAttributes.isAirlineUser()){
	    	transactionFilterVO.setTransactionStationCode(logonAttributes.getAirportCode());
    		listULDTransactionForm.setEnquiryDisableStatus("airline");
    	}
    	else{
    		transactionFilterVO.setTransactionStationCode(logonAttributes.getAirportCode());
    		listULDTransactionForm.setEnquiryDisableStatus("GHA");
    	}
	    
		listULDTransactionSession.setTransactionFilterVO(transactionFilterVO);
		listULDTransactionSession.setTransactionListVO(null);
		//added by a-3278 for QF1015 on25Jul08
		listULDTransactionSession.setTotalDemmurage(0.0);
		//a-3278 ends
		LocalDate date = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String txnFromDateString = TimeConvertor.toStringFormat(date.toCalendar(),
		"dd-MMM-yyyy");
		listULDTransactionForm.setTxnFromDate(txnFromDateString);
		LocalDate toDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String txnToDateString = TimeConvertor.toStringFormat(toDate.toCalendar(),
		"dd-MMM-yyyy");
		listULDTransactionForm.setTxnToDate(txnToDateString);
		
		LocalDate rtnDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String rtnFromDateString = TimeConvertor.toStringFormat(rtnDate.toCalendar(),
		"dd-MMM-yyyy");
		listULDTransactionForm.setReturnFromDate(rtnFromDateString);
		LocalDate rtnToDate = new LocalDate(getApplicationSession().getLogonVO().getAirportCode(),Location.ARP,true);
		String rtnToDateString = TimeConvertor.toStringFormat(rtnToDate.toCalendar(),
		"dd-MMM-yyyy");
		listULDTransactionForm.setReturnToDate(rtnToDateString);
		
		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	//maintainULDForm.setStatusFlag("screenload");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    	log.log(Log.FINE,"****at the end**************************");
    }


    /**
     * 
     * @param companyCode
     * @return
     */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(String companyCode){
		log.entering("ScreenLoadCommand","getOneTimeValues");
		
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate = 
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ScreenLoadCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * 
	 * @return
	 */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ScreenLoadCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(TXNTYPE_ONETIME);
    	parameterTypes.add(TXNNATURE_ONETIME);
    	parameterTypes.add(PARTYTYPE_ONETIME);
    	parameterTypes.add(ACCESSCODE_ONETIME);
    	parameterTypes.add(TXNSTATUS_ONETIME);
    	parameterTypes.add(MUCSTATUS_ONETIME);
    	
    	log.exiting("ScreenLoadCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }

}
