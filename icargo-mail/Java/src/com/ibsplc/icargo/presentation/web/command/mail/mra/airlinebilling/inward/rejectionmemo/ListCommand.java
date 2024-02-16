/*
 * ListCommand.java Created on May 18, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.rejectionmemo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAAirlineFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAAirlineVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.RejectionMemoVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.RejectionMemoSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.RejectionMemoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2408
 * 
 */
public class ListCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory
			.getLogger("MRA AIRLINEBILLING INWARD REJECTION MEMO");

	private static final String CLASS_NAME = "ScreenloadCommand";

	/**
	 * module name
	 * 
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	/**
	 * screen id
	 * 
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.rejectionmemo";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "failure_success";

	private static final String BLANK = "";

	private static final String MEMONO_MANDATORY = "mailtracking.mra.airlinebilling.rejectionmemo.err.memonomandatory";

	private static final String NO_RESULTS = "mailtracking.mra.airlinebilling.rejectionmemo.err.noresults";

	private static final String ON = "on";

	private static final String TRUE = "T";

	private static final String EXP_SCREEN = "invexpscreen";

	private static final String VWEXP_SCREEN = "invexpscreenview";
	
	private static final String  LISTEXCEPTION_DETAILS="airlineexceptions";
	
	private static final String MODULE = "mailtracking.mra.defaults";
	
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	
	private static final String MEM_STATUS = "OB";
	
	private static final String YES = "Y";
	
	private static final String ICH="I";
	
	private static final String ACH="A";
	
	private static final String REJECTION1STSTAGE = "1";
	private static final String REJECTION2NDSTAGE = "2";
	
    private static final String REJECTION_STAGE = "cra.sisbilling.rejectionstage";
	

	/**
	 * 
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");

		RejectionMemoForm rejectionMemoForm = (RejectionMemoForm) invocationContext.screenModel;

		RejectionMemoSession session = (RejectionMemoSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE,DSNPOPUP_SCREENID);
		String memoNumber = rejectionMemoForm.getMemoCode();
		String invoiceNumber = rejectionMemoForm.getInvoiceNumber();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = null;
		RejectionMemoFilterVO filterVO = new RejectionMemoFilterVO();
		RejectionMemoVO rejectionMemoVO = null;
		if (EXP_SCREEN.equals(rejectionMemoForm.getFromScreenFlag())) {

			rejectionMemoVO = session.getRejectionMemoVO();
			log.log(Log.INFO, "inside from screeen exp", rejectionMemoVO);
			if (rejectionMemoVO != null) {
				rejectionMemoForm.setMemoCode(rejectionMemoVO.getMemoCode());
			}
		} else {
			String companyCode = getApplicationSession().getLogonVO()
					.getCompanyCode();
			filterVO.setCompanyCode(companyCode);
			if (!BLANK.equals(memoNumber.trim())) {
				filterVO.setMemoCode(memoNumber.trim().toUpperCase());
			} else if(!(LISTEXCEPTION_DETAILS.equals(rejectionMemoForm.getInvokingScreen()))){
				errorVO = new ErrorVO(MEMONO_MANDATORY);
				errors.add(errorVO);
			}
			if (!BLANK.equals(invoiceNumber.trim())) {
				filterVO.setInvoiceNumber(invoiceNumber.trim().toUpperCase());
			}
		if("Y".equals(rejectionMemoForm.getLovClicked())){
			if(dSNPopUpSession.getSelectedDespatchDetails()!=null && dSNPopUpSession.getSelectedDespatchDetails().getDsnDate()!=null&&!"".equals(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate())){
				filterVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(dSNPopUpSession.getSelectedDespatchDetails().getDsnDate()));
			}
			rejectionMemoForm.setDsn(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());
			filterVO.setBillingBasis(dSNPopUpSession.getSelectedDespatchDetails().getBlgBasis());				
			filterVO.setCsgDocNum(dSNPopUpSession.getSelectedDespatchDetails().getCsgdocnum());
			filterVO.setCsgSeqNum(dSNPopUpSession.getSelectedDespatchDetails().getCsgseqnum());
			filterVO.setPoaCode(dSNPopUpSession.getSelectedDespatchDetails().getGpaCode());
			}
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			log.log(Log.INFO, "filterVo", filterVO);
			// To do delegate Call
			try {
				rejectionMemoVO = new MailTrackingMRADelegate()
						.findRejectionMemo(filterVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
			if (errors != null && errors.size() > 0) {
				session.setRejectionMemoVO(null);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}

		if (rejectionMemoVO != null) {
			RejectionMemoVO newRejectionMemoVO = updateRejectionMemoVO(rejectionMemoVO);
			
			

		   	AirlineDelegate airlineDelegate = new AirlineDelegate();
            try {

	           CRAAirlineFilterVO craAirlineFilterVO = new CRAAirlineFilterVO();
	           craAirlineFilterVO.setAirlineNumber(newRejectionMemoVO.getAirlineIdentifier()+"");
	           craAirlineFilterVO.setCompanyCode(getApplicationSession().getLogonVO()
						.getCompanyCode());
	           
	           
	           CRAAirlineVO   cRAAirlineVO = airlineDelegate.displayCRAAirlineDetails(craAirlineFilterVO);
	      
	           
	           if(cRAAirlineVO!=null&&cRAAirlineVO.getClearanceIndicator()!=null){
	        	   
	        	   if(ICH.equals(cRAAirlineVO.getClearanceIndicator())){
	        		   newRejectionMemoVO.setRejectionStage(REJECTION1STSTAGE);
	        	   }else if(ACH.equals(cRAAirlineVO.getClearanceIndicator())){
	        		   newRejectionMemoVO.setRejectionStage(REJECTION2NDSTAGE);
	        	   }
	           }
	           
            } catch (BusinessDelegateException businessDelegateException){
            	handleDelegateException(businessDelegateException);
            }

			Collection<String> oneTimeActiveStatusList = new ArrayList<String>();
		    Map activeStatusHashMap = null;
		    SharedDefaultsDelegate sharedDefaultsDelegate =
	        	new SharedDefaultsDelegate();
		    oneTimeActiveStatusList.add(REJECTION_STAGE);

		    try {

	    		activeStatusHashMap = sharedDefaultsDelegate
	    				.findOneTimeValues(getApplicationSession().getLogonVO()
	    						.getCompanyCode(),oneTimeActiveStatusList);

	    	} catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
	    	}
			if(activeStatusHashMap!=null){
				session.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)activeStatusHashMap);
			}

			if (VWEXP_SCREEN.equals(rejectionMemoForm.getFromScreenFlag())) {
				rejectionMemoForm.setScreenFlag("disableall");
			} else if (EXP_SCREEN.equals(rejectionMemoForm.getFromScreenFlag())) {
				rejectionMemoForm.setScreenFlag("disablefilter");
			} else if (LISTEXCEPTION_DETAILS.equals(rejectionMemoForm.getInvokingScreen())) {
				rejectionMemoForm.setScreenFlag("enableallfields");
			} else if (!MEM_STATUS.equals(rejectionMemoVO.getMemoStatus())) {
				rejectionMemoForm.setScreenFlag("disableSave");
			} else if (MEM_STATUS.equals(rejectionMemoVO.getMemoStatus())) {
				rejectionMemoForm.setScreenFlag("enableSave");
			} else {
				rejectionMemoForm.setScreenFlag("list");
			}
			if(newRejectionMemoVO.getInwardInvoiceNumber()!=null && newRejectionMemoVO.getInwardInvoiceNumber().trim().length()>0){
				if(rejectionMemoVO.getInwardInvoiceDate()!=null){
					rejectionMemoForm.setMonthOfTransaction(rejectionMemoVO.getInwardInvoiceDate().toDisplayFormat(" MMM "));
					newRejectionMemoVO.setOutwardClearancePeriod(rejectionMemoVO.getInwardClearancePeriod());
				}
			}else if(rejectionMemoVO.getOutwardInvoiceDate()!=null){          
				rejectionMemoForm.setMonthOfTransaction(rejectionMemoVO.getOutwardInvoiceDate().toDisplayFormat(" MMM "));
				
			}
			session.setRejectionMemoVO(newRejectionMemoVO);
			log.log(Log.INFO, "listed vo", newRejectionMemoVO);
			
			
			
		} else {
			rejectionMemoForm.setScreenFlag("screenload");
			session.setRejectionMemoVO(null);
			errorVO = new ErrorVO(NO_RESULTS);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
		}
		rejectionMemoForm.setLovClicked("");
		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * @param vo
	 * @return
	 */
	private RejectionMemoVO updateRejectionMemoVO(RejectionMemoVO vo) {

		log.entering(CLASS_NAME, "update values");
		if (TRUE.equals(vo.getChargeNotConvertedToContractIndicator())) {
			vo.setChargeNotConvertedToContractIndicator(ON);
		} else {
			vo.setChargeNotConvertedToContractIndicator(null);
		}
		if (TRUE.equals(vo.getChargeNotCoveredByContractIndicator())) {
			vo.setChargeNotCoveredByContractIndicator(ON);
		} else {
			vo.setChargeNotCoveredByContractIndicator(null);
		}
		if (TRUE.equals(vo.getIncorrectExchangeRateIndicator())) {
			vo.setIncorrectExchangeRateIndicator(ON);
		} else {
			vo.setIncorrectExchangeRateIndicator(null);
		}
		if (TRUE.equals(vo.getDuplicateBillingIndicator())) {
			vo.setDuplicateBillingIndicator(ON);
		} else {
			vo.setDuplicateBillingIndicator(null);
		}
		if (TRUE.equals(vo.getNoApprovalIndicator())) {
			vo.setNoApprovalIndicator(ON);
		} else {
			vo.setNoApprovalIndicator(null);
		}
		if (TRUE.equals(vo.getNoReceiptIndicator())) {
			vo.setNoReceiptIndicator(ON);
		} else {
			vo.setNoReceiptIndicator(null);
		}
		if (TRUE.equals(vo.getRequestAuthorisationIndicator())) {
			vo.setRequestAuthorisationIndicator(ON);
		} else {
			vo.setRequestAuthorisationIndicator(null);
		}
		if (TRUE.equals(vo.getOutTimeLimitsForBillingIndicator())) {
			vo.setOutTimeLimitsForBillingIndicator(ON);
		} else {
			vo.setOutTimeLimitsForBillingIndicator(null);
		}
		if (TRUE.equals(vo.getOtherIndicator())) {
			vo.setOtherIndicator(YES);

		} else {
			vo.setOtherIndicator(null);

		}
		log.exiting(CLASS_NAME, "update values");
		return vo;

	}
}
