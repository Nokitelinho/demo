/*
 * ScreenloadModifyULDTransactionCommand.java Created on Dec 19, 2005
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

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
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
public class ScreenloadModifyULDTransactionCommand  extends BaseCommand {
    
	 /**
	 * Logger for Maintain Uld discripency
	 */
	private Log log = LogFactory.getLogger("Loan Borrow Details Enquiry");
	private static final String SYSPAR_DUMMYRETURNSTATION = "uld.defaults.dummyreturnstation";
	
	/**
	 * target String if success
	 */
	private static final String SCREENLOAD_SUCCESS = "screenload_success";	
	//private static final String CONDITIONCODE_ONETIME = "uld.defaults.conditioncode";
	private static final String MODULE_NAME = "uld.defaults";
	private static final String SCREEN_ID = "uld.defaults.loanborrowdetailsenquiry";
	

    /** 
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ScreenloadModifyULDTransactionCommand","execute");
    	ListULDTransactionForm listULDTransactionForm = (ListULDTransactionForm) invocationContext.screenModel;   	
    	
    //	String companyCode = "";
    	ApplicationSessionImpl applicationSession = getApplicationSession();
     	LogonAttributes logonAttributes = applicationSession.getLogonVO();
     	ListULDTransactionSession listULDTransactionSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
     //	companyCode = logonAttributes.getCompanyCode();
     	
		
     //	HashMap<String, Collection<OneTimeVO>> oneTimeValues = getOneTimeValues(companyCode);
     	//Collection<OneTimeVO> conditionCodes = oneTimeValues.get(CONDITIONCODE_ONETIME);
		//listULDTransactionSession.setUldCondition(conditionCodes);
	//	listULDTransactionSession.setOneTimeValues(oneTimeValues);
		listULDTransactionForm.setScreenStatusFlag(
  				ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		listULDTransactionForm.setLoginStation(logonAttributes.getAirportCode());
    	log.log(Log.INFO, "Display page in screen load---->",
				listULDTransactionForm.getModDisplayPage());
		listULDTransactionForm.setModCurrentPage(listULDTransactionForm.getModDisplayPage());
		/*Added by A-5782 for ICRD-107212 starts*/
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ArrayList<String> parameterCodes = new ArrayList<String>();
		HashMap<String, String> systemParameterCodes = new HashMap<String, String>();
		parameterCodes.add(SYSPAR_DUMMYRETURNSTATION);
		try {
			systemParameterCodes = (HashMap<String, String>) (new SharedDefaultsDelegate().findSystemParameterByCodes(parameterCodes));
		} catch (BusinessDelegateException businessException) {
			errors=handleDelegateException(businessException);
		}
	if(systemParameterCodes!=null){
		listULDTransactionSession.setSystemParameters(systemParameterCodes);
	}
	/*Added by A-5782 for ICRD-107212 ends*/
		ULDTransactionDetailsVO uldTxnVo=listULDTransactionSession.getULDTransactionDetailsVO();	
		if(uldTxnVo !=null ){
			log.log(Log.INFO, "Form.getModTotalRecord", listULDTransactionForm.getModTotalRecords());
			log.log(Log.INFO, "uldTxnVo.getControlReceiptNumber()====", uldTxnVo.getControlReceiptNumber());
			log.log(Log.INFO, "uldTxnVo.getReturnCRN()====", uldTxnVo.getReturnCRN());
			log.log(Log.INFO, "uldTxnVo.getReturnCRN()====", uldTxnVo.getPartyType());
			log.log(Log.INFO, "generateConditionCodes --->>>>>");
	
			generateConditionCodes(uldTxnVo.getPartyType(), listULDTransactionSession);
			// Added by Preet for NCA bug ULD 71 starts
			if(uldTxnVo.getTransactionDate()!= null ||uldTxnVo.getTransactionDate().toString().trim().length()==0){
				uldTxnVo.setStrTxnTime(uldTxnVo.getTransactionDate().toDisplayTimeOnlyFormat(true));
		    }else {
		    	uldTxnVo.setStrTxnTime("00:00");
		    }			
			//Added by Preet for NCA bug ULD 71 ends
			// Added by a-3278 for CR QF1015 on 08Jul08
			if (uldTxnVo.getReturnDate() != null && uldTxnVo.getReturnDate().toString().trim().length()!=0) {//modified by a-5505 for the bug ICRD-101699 
				uldTxnVo.setStrRetTime(uldTxnVo.getReturnDate()
						.toDisplayTimeOnlyFormat(true));
			} else {
				uldTxnVo.setStrRetTime("00:00");
			}
			if (uldTxnVo.getDemurrageAmount() < 0) {
				uldTxnVo.setDemurrageAmount(0.0);
			} else {
				uldTxnVo.setDemurrageAmount(uldTxnVo.getDemurrageAmount());
	
			}
			if (uldTxnVo.getWaived() < 0) {
				uldTxnVo.setWaived(0.0);
			} else {
				uldTxnVo.setWaived(uldTxnVo.getWaived());
	
			}
			if (uldTxnVo.getTaxes() < 0) {
				uldTxnVo.setTaxes(0.0);
			} else {
				uldTxnVo.setTaxes(uldTxnVo.getTaxes());
	
			}
			//added by a-3278 for bug 18372 on 18Sep08
			if (uldTxnVo.getCurrency() != null && uldTxnVo.getCurrency().trim().length() > 0){
				uldTxnVo.setCurrency(uldTxnVo.getCurrency());
			}
			log.log(Log.FINE, "uldTxnVo.getReturnStationCode()====", uldTxnVo.getReturnStationCode());
			log.log(Log.INFO, "uldTxnVo.getULDConditionCode()====", uldTxnVo.getUldConditionCode());
			if(uldTxnVo.getUldConditionCode()!=null && uldTxnVo.getUldConditionCode().trim().length()>0){
			listULDTransactionForm.setModUldCondition(uldTxnVo.getUldConditionCode());
			}else{
				listULDTransactionForm.setModUldCondition("SER");
			}
			if(uldTxnVo.getAwbNumber()!=null && uldTxnVo.getAwbNumber().trim().length()>0){
				listULDTransactionForm.setModAwbNumber(uldTxnVo.getAwbNumber().trim());
			}
			if("N".equals(uldTxnVo.getEmptyStatus())){
				listULDTransactionForm.setModLoaded("Y");
			}else{
				listULDTransactionForm.setModLoaded("N");
			}
	//		added by nisha for NCA bugfix ends
			// added by a-3278 for bug 23062 on 29Oct08
			if (uldTxnVo.getPoolOwnerFlag() != null
					&& uldTxnVo.getPoolOwnerFlag().trim().length() > 0) {
				listULDTransactionForm
						.setPoolOwnerFlag(uldTxnVo.getPoolOwnerFlag());
			} else {
				listULDTransactionForm.setPoolOwnerFlag("N");
			}
			//a-3278 ends
			
		
			String crn = "";
			/*
			 * added by a-3278 for 28897 on 05Jan09
			 * a new CRN is maintained to save the latest and the old CRN seperately
			 * if returnCRN is present this value needs to be populated,else the old CRN itself
			 */
			if (uldTxnVo.getReturnCRN() != null
					&& uldTxnVo.getReturnCRN().trim().length() > 0) {
				crn = uldTxnVo.getReturnCRN();
			} else {
				crn = uldTxnVo.getControlReceiptNumber();
			}
			AirlineValidationVO toOwnerVO = null;
			String fromPrtyCode = uldTxnVo.getFromPartyCode();
			String airlineID = null;
			if(crn!=null && crn.length()>0){
				//uldTxnVo.setControlReceiptNumberPrefix(crn.substring(0, 4));
				//uldTxnVo.setControlReceiptNumber(crn.substring(4, crn.length()));
				 
				// Modified for the ICRD-16045 Author: A-5125 on jul25th2012
				listULDTransactionSession.setCtrlRcptNoPrefix(crn.substring(0, 3));
				listULDTransactionSession.setCtrlRcptNo(crn.substring(4, crn.length()));
			}else{
				if (uldTxnVo.getFromPartyCode() !=null ) {					
					if (fromPrtyCode != null && !("".equals(fromPrtyCode))) {
						try {
							toOwnerVO = new AirlineDelegate().validateAlphaCode(
									logonAttributes.getCompanyCode(),
									fromPrtyCode.toUpperCase());
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
					}
					
				}
				if(toOwnerVO !=null ){
					/*
					 * added by a-5125 Modified for the ICRD-16045 Author: A-5125 on Aug2nd2012
					 * This is the previous airlineID=toOwnerVO.getNumericCode()+"-" statement
					 * now removing the "-" because no need in the Screen screen Name jsp/uld/defaults/ModifyLoanDetails.jsp
					 */
					airlineID=toOwnerVO.getNumericCode();	
					listULDTransactionSession.setCtrlRcptNoPrefix(airlineID);
					listULDTransactionSession.setCtrlRcptNo("");
				}
				
			}
		}
		listULDTransactionForm.setModFlag("");
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors); /*Added by A-5782 for ICRD-107212*/
		}
    	invocationContext.target =SCREENLOAD_SUCCESS;    	
    }
    
   /* private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(
			String companyCode) {
		log.entering("ScreenLoadCommand", "getOneTimeValues");

		/*
		 * the shared defaults delegate
		 */
		/*SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "****inside try**************************"
					+ getOneTimeParameterTypes());

			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "*****in the exception");
//printStackTrrace()();
			error = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> " + oneTimeValues);

		log.exiting("ScreenLoadCommand", "getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeValues;
	}
    
   /* private Collection<String> getOneTimeParameterTypes() {
		log.entering("ScreenLoadCommand", "getOneTimeParameterTypes");
		ArrayList<String> parameterTypes = new ArrayList<String>();

		parameterTypes.add(CONDITIONCODE_ONETIME);
		log.exiting("ScreenLoadCommand", "getOneTimeParameterTypes");
		return parameterTypes;
	}*/
    /**
	 * @param session
	 * @param form
	 * @return
	 */
	private void generateConditionCodes(String partyType,
			ListULDTransactionSession  session) {
		log.log(Log.INFO, "INSIDE generateConditionCodes !!!!!!!!");
		Collection<ULDServiceabilityVO> uldServiceabilityVOs = new ArrayList<ULDServiceabilityVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
		log.log(Log.FINE, "partyType--------->>>>>>>>>>>>>>", partyType);
		try {
			uldServiceabilityVOs = delegate.listULDServiceability(
					logonAttributes.getCompanyCode(), partyType);
			log
					.log(
							Log.FINE,
							"uldServiceabilityVOs getting from delegate--------->>>>>>>>>>>>>>",
							uldServiceabilityVOs);
			session.setULDServiceabilityVOs(uldServiceabilityVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exceptions = handleDelegateException(e);
	}

	}

}
