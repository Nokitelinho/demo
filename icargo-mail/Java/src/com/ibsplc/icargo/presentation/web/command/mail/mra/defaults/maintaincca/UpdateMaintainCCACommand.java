/* UpdateMaintainCCACommand.java created on July-14-2008
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

/**
 * 
 * @author A-3447
 */

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3447
 * 
 */
public class UpdateMaintainCCACommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Sucess variable
	 */
	private static final String UPDATE_SUCCESS = "update_success";
	private static final String DSN_POPUP_SUCCESS = "dsn_popup_success";
	private static final String UPDATE_FAILURE = "update_failure";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String ERROR_MANDATORY = "mailtracking.mra.defaults.maintaincca.nofiltercriteria";
	/**
	 * Module name
	 */

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "UpdateMaintainCCACommand";
	/**
	 * Screen id
	 */

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintaincca";

	//
	private static final String MODULE = "mailtracking.mra.gpabilling";

	private static final String SCREENID_GPA = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";

	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";
	private static final String GPABILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String NO = "N";
	private static final String YES = "Y";
	private static final String AIRLINE = "A";
	private static final String GPA = "G";
	private static final String OTHER_AIRLINE = "OAL";
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		log.entering(CLASS_NAME, "execute");
		/**
		 * Obtaining form
		 */
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();

		maintainCCASession.removeMaintainCCAFilterVO();
		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();

		GPABillingInvoiceEnquirySession gPABillingInvoiceEnquirySession=null;		

		if(maintainCCAForm.getFromGPABillingInvoiceEnquiry()!=null && maintainCCAForm.getFromGPABillingInvoiceEnquiry().equals(YES)){
			gPABillingInvoiceEnquirySession=(GPABillingInvoiceEnquirySession)getScreenSession(
					MODULE, SCREENID_GPA);			
			String counter = maintainCCAForm.getCounter();
			Collection<CN66DetailsVO> cN66DetailsVOs = gPABillingInvoiceEnquirySession
			.getCN66VOs();
			ArrayList<CN66DetailsVO> cN66DetailsVOArraylist = new ArrayList<CN66DetailsVO>(
					cN66DetailsVOs);
			CN66DetailsVO cN66DetailsVO;
			log.log(Log.FINE, "inside *****<<<<counter>>>>----------  ",
					counter);
			cN66DetailsVO= cN66DetailsVOArraylist.get(Integer.parseInt(counter));
			log.log(Log.FINE, "Inside list command... >>", cN66DetailsVO);
			maintainCCAForm.setDsnNumber(cN66DetailsVO.getDsn());
			maintainCCAForm.setDsnDate(cN66DetailsVO.getReceivedDate().toDisplayDateOnlyFormat());
			maintainCCAForm.setAirlineCode("NZ");
			maintainCCAForm.setFromGPABillingInvoiceEnquiry(null);
		}
		/**
		 * @author A-4810
		 * This code is added as part of icrd-13639 
		 * This code indicates that the MCA list is navigated from ListCN51CN66  screen .
		 * This code is to list the MCA number details selected from ListCN51CN66 screen.
		 */
		if (maintainCCAForm.getFromScreen()!=null && "CN51CN66".equals(maintainCCAForm.getFromScreen())){
			maintainCCAForm.setAirlineCode(null);
			maintainCCAForm.setCcaStatus(NO);
			//maintainCCAForm.setFromListCN51CN56(null);
			Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
			maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
			log.log(Log.FINE, "onetimes----->", maintainCCASession.getOneTimeVOs());
			maintainCCAForm.setPrivilegeFlag(NO);
			try {
				if(checkWorkFlowEnabled()){
					maintainCCAForm.setPrivilegeFlag(YES);
				}
			} catch (SystemException e) {

				//e.printStackTrace();
			}
			//maintainCCAForm.setFromScreen("CN51CN66");
		 }
		maintainCCAFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		if (maintainCCAForm.getCcaNum() != null
				&& maintainCCAForm.getCcaNum().trim().length() > 0) {
			maintainCCAFilterVO.setCcaReferenceNumber(maintainCCAForm
					.getCcaNum());
		}
		if (maintainCCAForm.getDsnNumber() != null
				&& maintainCCAForm.getDsnNumber().trim().length() > 0) {
			maintainCCAFilterVO.setDsnNumber(maintainCCAForm.getDsnNumber());
		}

		if(maintainCCAForm.getAirlineCode() !=null
				&& maintainCCAForm.getAirlineCode().trim().length()>0){			
			if(maintainCCAForm.getIssueParty()!=null && 
					!"".equals(maintainCCAForm.getIssueParty())){
				maintainCCAFilterVO.setIssuingParty(maintainCCAForm.getIssueParty());
			}
		}
		if(maintainCCAForm.getAirlineCode() !=null
				&& maintainCCAForm.getAirlineCode().trim().length()>0){
			maintainCCAFilterVO.setPartyCode(maintainCCAForm.getAirlineCode());	
		}
		if(maintainCCAForm.getAirlineLoc() !=null
				&& maintainCCAForm.getAirlineLoc().trim().length()>0){
			maintainCCAFilterVO.setPartyLocation(maintainCCAForm.getAirlineLoc());	
		}
		if(maintainCCAForm.getConDocNo()!=null && maintainCCAForm.getConDocNo().trim().length()>0){
			maintainCCAFilterVO.setConsignmentDocNum(maintainCCAForm.getConDocNo());
		}
		if(maintainCCAForm.getCcaStatus()!=null && maintainCCAForm.getCcaStatus().trim().length()>0){
			maintainCCAFilterVO.setCcaStatus(maintainCCAForm.getCcaStatus());
		}


		maintainCCAFilterVO.setScreenID(SCREEN_ID);
		maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);

		/*
		 * if DSN is present and Issue Party is not given by the user.
		 */
	/*	if ( maintainCCAFilterVO!=null && (maintainCCAFilterVO.getDsnNumber() != null && 
				maintainCCAFilterVO.getDsnNumber().trim().length() > 0)
				&& (maintainCCAFilterVO.getPartyCode()==null ||
						(maintainCCAFilterVO.getPartyCode()!=null &&
								maintainCCAFilterVO.getPartyCode().trim().length() == 0))) {

			invocationContext.addError(new ErrorVO("mailtracking.mra.defaults.maintaincca.nofiltercriteria"));
			maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = UPDATE_FAILURE;
			log.log(Log.FINE, "target---"+UPDATE_FAILURE);
			maintainCCAForm.setDsnPopupFlag(NO);
			return;
		}*/

		if(maintainCCAForm.getUsrCCANumFlg()!= null && 
				YES.equals(maintainCCAForm.getUsrCCANumFlg())){
			//validations must be there if manual ref number is given

			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO displayErrorVO = null;

			/* Validating Filter Fields
			 * 1.CCA Ref. Num
			 * 2.DSN Number
			 * 3.DSN Date
			 * 4.Issuing Party Code.
			 */
			
			//Edited by Hari Kishan as part of BUG ICRD-16329 STARTS
			if ((maintainCCAFilterVO.getDsnNumber() == null || 
					maintainCCAFilterVO.getDsnNumber().trim().length() == 0) && 
					(maintainCCAForm.getCcaNum() == null ||
							 maintainCCAForm.getCcaNum().trim().length() == 0)) {
				//Edited by Hari Kishan as part of BUG ICRD-16329 ENDS

				displayErrorVO = new ErrorVO(ERROR_MANDATORY);
				displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(displayErrorVO);
				invocationContext.addAllError(errors);
				maintainCCAForm.setDsnPopupFlag(NO);
				invocationContext.target = UPDATE_FAILURE;
				maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
				return;
			}


			if(maintainCCAFilterVO.getPartyCode()!=null 
					&& maintainCCAFilterVO.getPartyCode().trim().length()>0){

				String partyCode = maintainCCAFilterVO.getPartyCode().toUpperCase();
				AirlineDelegate airlineDelegate = new AirlineDelegate();
				AirlineValidationVO airlineValidationVO = null;
				PostalAdministrationVO postalAdministrationVO = null;
				String error = null;
				boolean errorFlg=false;

				if(maintainCCAFilterVO.getIssuingParty()!= null && 
						maintainCCAFilterVO.getIssuingParty().trim().length()>0){
					if("GPA".equals(maintainCCAFilterVO.getIssuingParty()) || 
							GPA.equals(maintainCCAFilterVO.getIssuingParty())){
						//Validating GPA
						try{
							postalAdministrationVO = 
								mailTrackingMRADelegate.findPostalAdminDetails(
										logonAttributes.getCompanyCode(),partyCode);						
						}catch(BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
							errorFlg=true;
						}
						if(postalAdministrationVO==null){
							errorFlg=true;
						}else{
							/*
							 * IssuingParty in MaintainCCAFilterVO
							 * 1. "G" - GPA
							 */
							maintainCCAFilterVO.setIssuingParty(GPA);
						}
						//For error msg.
						error=" GPA : "+partyCode;
					}else{
						//Validating AirLine
						try {
							airlineValidationVO = 
								airlineDelegate.validateAlphaCode(
										logonAttributes.getCompanyCode(),partyCode);	
							/*
							 * IssuingParty in MaintainCCAFilterVO
							 * 1. "A" - Own Airline
							 * 2. "OAL" - Other AirLine
							 */
							if(logonAttributes.getOwnAirlineCode().equals(partyCode)){
								//Own Airline
								maintainCCAFilterVO.setIssuingParty(AIRLINE);
							}else{
								//Other Airline
								maintainCCAFilterVO.setIssuingParty(OTHER_AIRLINE);
							}
						}catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
							errorFlg=true;
						}	
						//For error msg.
						error=" AirLine : "+partyCode;
					}
				}
				if (errorFlg) {
					errors = new ArrayList<ErrorVO>();				
					Object[] obj = {error};
					ErrorVO errorVO = 
						new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.invalidbillto",obj);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
					maintainCCAForm.setDsnPopupFlag(NO);
					invocationContext.addAllError(errors);
					invocationContext.target = UPDATE_FAILURE;    			
					return;
				}			
			}
			maintainCCASession.setUsrCCANumFlg(YES);	
		}else{
			maintainCCASession.setUsrCCANumFlg(NO);
		}




		if(maintainCCAFilterVO!=null){
			if(maintainCCAFilterVO.getCcaReferenceNumber()!=null &&
					maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0){
				if(maintainCCAFilterVO.getDsnNumber()!=null &&
						maintainCCAFilterVO.getDsnNumber().trim().length()>0  && 
						(maintainCCAFilterVO.getUsrCCANumFlg()==null || "".equals(maintainCCAFilterVO.getUsrCCANumFlg())) ){
					/*
					 * To invoke the DSN POPUP PLAN
					 * When CCA Ref Num Is present and DSN num is also Present
					 */
//					maintainCCAForm.setDsnPopupFlag(YES);
//					invocationContext.target = DSN_POPUP_SUCCESS;
//					log.log(Log.FINE, "target---"+DSN_POPUP_SUCCESS);
					maintainCCAFilterVO.setUsrCCANumFlg(NO);
					maintainCCAForm.setDsnPopupFlag(NO);
					invocationContext.target = UPDATE_SUCCESS;
					log.log(Log.FINE, "target---", UPDATE_SUCCESS);
				}else{
					if(maintainCCAForm.getUsrCCANumFlg()!= null && 
							YES.equals(maintainCCAForm.getUsrCCANumFlg())){
						/*
						 * To invoke the DSN POPUP 
						 * When manual cca ref num
						 */
						//UsrCCANumFlg : option selected by the user for capturing CCA ref number from a CCA document(manual CCA).
						maintainCCAFilterVO.setUsrCCANumFlg(YES);
						maintainCCAForm.setDsnPopupFlag(YES);
						invocationContext.target = DSN_POPUP_SUCCESS;
						log.log(Log.FINE, "target---", DSN_POPUP_SUCCESS);
					}else{
						/*
						 * To Disable DSN POPUP PLAN
						 * When CCA Ref Num Is present and DSN num is not Present
						 */
						//UsrCCANumFlg : option selected by the user for capturing CCA ref number from a CCA document.
						maintainCCAFilterVO.setUsrCCANumFlg(NO);
						maintainCCAForm.setDsnPopupFlag(NO);
						invocationContext.target = UPDATE_SUCCESS;
						log.log(Log.FINE, "target---", UPDATE_SUCCESS);
					}
				}
			}else{/*
				 * When CCA Ref Num is not present and issue party code is also not Present
				 */
			
					/* Validating Filter Fields,to check whether all are empty
					 * 1.CCA Ref. Num
					 * 2.DSN Number
					 * 3.Issuing Party Code.
					 */
					if ((maintainCCAFilterVO.getCcaReferenceNumber() == null || 
							maintainCCAFilterVO.getCcaReferenceNumber().trim().length() == 0)
							&& (maintainCCAFilterVO.getDsnNumber() == null || 
									maintainCCAFilterVO.getDsnNumber().trim().length() == 0)) {

						invocationContext.addError(new ErrorVO("mailtracking.mra.defaults.maintaincca.nofiltercriteria"));
						maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
						invocationContext.target = UPDATE_FAILURE;
						log.log(Log.FINE, "target---", UPDATE_FAILURE);
						return;
					}
					

				if(maintainCCAForm.getUsrCCANumFlg()!=null && "AUTO".equals(maintainCCAForm.getUsrCCANumFlg())){
					maintainCCAForm.setDsnPopupFlag(YES);
					maintainCCAFilterVO.setUsrCCANumFlg(NO);
					invocationContext.target = UPDATE_SUCCESS;
				}else{
				//If CCA num not given,issuingPartyCode present,then invoke the DSN POPUP
					if(maintainCCAFilterVO.getConsignmentDocNum() == null|| 
							maintainCCAFilterVO.getConsignmentDocNum().trim().length() == 0){
						maintainCCAForm.setDsnPopupFlag(YES);
						//UsrCCANumFlg : option selected by the user for capturing CCA ref number from a CCA document.
						maintainCCAFilterVO.setUsrCCANumFlg(NO);
						invocationContext.target = DSN_POPUP_SUCCESS;
						log.log(Log.FINE, "maintainCCAForm.getDsnPopupFlag---",
								maintainCCAForm.getDsnPopupFlag());
						log.log(Log.FINE, "target---", DSN_POPUP_SUCCESS);
					}else{
						maintainCCAFilterVO.setUsrCCANumFlg(NO);
						maintainCCAForm.setDsnPopupFlag(NO);
						invocationContext.target = UPDATE_SUCCESS;
						log.log(Log.FINE, "target---", UPDATE_SUCCESS);
					}
				}
			}

		}
		

		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 * 
	 * @return
	 * @throws SystemException
	 */
	private boolean checkWorkFlowEnabled() throws SystemException {
		Boolean workFlowEnabled=true;
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
		Map<String, String> systemParameters = null;		
		try {
			systemParameters = sharedDefaultsDelegate
			.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessDelegateException e) {
			// TODO Auto-generated catch block
			
		}
		if(systemParameters!=null &&systemParameters.size()>0 ){
			if(!(systemParameters.containsValue(YES) || systemParameters.containsValue(BASED_ON_RULES))){//Modified For IASCB-2373
				workFlowEnabled=false;
			}
		}
		return workFlowEnabled;
	}
	/**
	 * 
	 * @param companyCode
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> fetchOneTimeDetails(String companyCode) {
		log.entering(CLASS_NAME,"fetchOneTimeDetails");
		Map<String, Collection<OneTimeVO>> hashMap =new HashMap<String, Collection<OneTimeVO>>();
		Collection<String> oneTimeList=new ArrayList<String>();
		oneTimeList.add(CCATYPE_ONETIME);
		oneTimeList.add(ISSUINGPARTY_ONETIME);
		oneTimeList.add(CCASTATUS_ONETIME);
		oneTimeList.add(GPABILLINGSTATUS_ONETIME);
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,
					oneTimeList);
		} catch (BusinessDelegateException e) {			
			handleDelegateException(e);
		}
		log.exiting(CLASS_NAME,"fetchOneTimeDetails");
		return hashMap;
	}
}
