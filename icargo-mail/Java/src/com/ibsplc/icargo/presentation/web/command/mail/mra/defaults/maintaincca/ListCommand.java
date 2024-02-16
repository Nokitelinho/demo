/*
 * ListCommand.java Created on July-14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

/**
 * @author A-3447
 */
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;

/**
 * Command class for listing.
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 14-July-2008 Muralee(a-3447) For CRQ_172
 */
public class ListCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * Class name
	 */
	private static final String CLASS_NAME = "ListCommand";

	/**
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	/**
	 * DSN POPUP SCREENID
	 */
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	/**
	 * target action
	 */
	private static final String LIST_SUCCESS = "list_success";

	/**
	 * LIST_FAILURE
	 */
	private static final String LIST_FAILURE = "list_failure";

	/**
	 * For Error Tags
	 */
	private static final String ERROR_MANDATORY = "mailtracking.mra.defaults.maintaincca.nofiltercriteria";
	private static final String ERROR_INVALID_FILTER = "mailtracking.mra.defaults.maintaincca.invalidfiltercriteria";
	private static final String ERROR_MCA_DOES_NOT_EXIST = "mailtracking.mra.defaults.maintaincca.mcadoesnotexist";
	private static final String ERROR_NO_RESULTS = "mailtracking.mra.defaults.maintaincca.noresults";
	private static final String CCATYPE_ONETIME = "mra.defaults.ccatype";
	private static final String ISSUINGPARTY_ONETIME = "mra.defaults.issueparty";
	private static final String CCASTATUS_ONETIME = "mra.defaults.ccastatus";
	private static final String GPABILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String CURRENCY_CODE = "NZD";
	private static final String AIRLINE = "A";
	private static final String GPA = "G";
	private static final String OTHER_AIRLINE = "OAL";
	private static final String TO_BE_RATED = "RR";
	private static final String ERROR_TO_BE_RATED = "mailtracking.mra.defaults.maintaincca.toberatedstatus";
	private static final String ERROR_ONHOLD = "mailtracking.mra.defaults.maintaincca.onholdmailbags";
	private static final String ERROR_INTERLINE = "mailtracking.mra.defaults.maintaincca.interlinemailbags";
	private static final String BILLINGSTATUS_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";//added by a-7929 for ICRD-250606
	private static final String WEIGHT_UNIT_ONETIME="mail.mra.defaults.weightunit"; // added by A-9002

	private static final String ERROR_VOID_MAILBAGS = "mailtracking.mra.gpabilling.voidmailbags";
	/**
	 * Execute method
	 * 
	 * @param invocationContext *
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;
		MaintainCCASession maintainCCASession = 
			(MaintainCCASession) getScreenSession(MODULE_NAME, MAINTAINCCA_SCREEN);

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();

		Collection<CCAdetailsVO> ccaDetailsVOs = null;
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO displayErrorVO = null;
		maintainCCAForm.setCcaPresent("");

		MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();

		if("gpabillinginvoiceenquiry".equals(maintainCCAForm.getFromScreen())){
			if(maintainCCASession.getMaintainCCAFilterVO().getCcaReferenceNumber()!=null 
					&& maintainCCASession.getMaintainCCAFilterVO().getCcaReferenceNumber().trim().length()>0){
				maintainCCAFilterVO = maintainCCASession.getMaintainCCAFilterVO();
				maintainCCAForm.setCcaNum(maintainCCAFilterVO.getCcaReferenceNumber());

			}else{
				maintainCCAForm.setAirlineCode(maintainCCAFilterVO.getPartyCode());
				maintainCCAForm.setDsnPopupFlag(FLAG_YES);
				Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
				maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
				log.log(Log.FINE, "onetimes----->", maintainCCASession.getOneTimeVOs());
			}

		}
		maintainCCAFilterVO = maintainCCASession.getMaintainCCAFilterVO();
		if(maintainCCAFilterVO.getDsnNumber()!=null && maintainCCAFilterVO.getDsnNumber().trim().length()!=0)
		{
		if(maintainCCAFilterVO.getDsnNumber().trim().length()>4)
		{
			maintainCCAFilterVO.setBillingBasis(maintainCCAFilterVO.getDsnNumber());
			
		}
		}    
		log.log(Log.FINE, "maintainCCAFilterVO----->", maintainCCAFilterVO);
		maintainCCAForm.setAutoratedFlag("N");
		maintainCCAForm.setRateAuditedFlag("N");
		if("RateAuditDetails".equals(maintainCCAForm.getFromScreen())||"listCCA".equals(maintainCCAForm.getFromScreen())){
			if("RateAuditDetails".equals(maintainCCAForm.getFromScreen())){
				maintainCCAForm.setDsnPopupFlag(FLAG_YES);	
			}
			maintainCCAForm.setAirlineCode(maintainCCAFilterVO.getPartyCode());
			Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());		
			maintainCCASession.setOneTimeVOs((HashMap<String,Collection<OneTimeVO>>)oneTimeValues);
			log.log(Log.FINE, "onetimes----->", maintainCCASession.getOneTimeVOs());
		}
		if("Y".equals(maintainCCASession.getPopupFlag())){
			maintainCCASession.setPopupFlag(FLAG_NO);
			maintainCCAForm.setDsnPopupFlag(FLAG_YES);
		}
		log.log(Log.FINE, "maintainCCAForm.getDsnPopupFlag()----->",
				maintainCCAForm.getDsnPopupFlag());
		if(FLAG_YES.equals(maintainCCAForm.getDsnPopupFlag())){
			DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE_NAME,DSNPOPUP_SCREENID);
			DSNPopUpVO dsnPopUpVO = dSNPopUpSession.getSelectedDespatchDetails();

			maintainCCAForm.setDsnPopupFlag(FLAG_NO);
			//maintainCCAForm.setDsnNumber(dsnPopUpVO.getBlgBasis());

			maintainCCAForm.setDsnDate(dsnPopUpVO.getDsnDate());
			maintainCCAFilterVO.setCompanyCode(dsnPopUpVO.getCompanyCode());
			//maintainCCAFilterVO.setDsnNumber(dsnPopUpVO.getBlgBasis());	
			maintainCCAFilterVO.setBillingBasis(dsnPopUpVO.getBlgBasis());
			maintainCCAFilterVO.setDsnNumber(dsnPopUpVO.getBlgBasis());
			maintainCCAFilterVO.setDsnDate(new LocalDate(
					LocalDate.NO_STATION,Location.NONE,false).setDate(dsnPopUpVO.getDsnDate()));
			maintainCCAFilterVO.setConsignmentDocNum(dsnPopUpVO.getCsgdocnum());
			maintainCCAFilterVO.setConsignmentSeqNum(dsnPopUpVO.getCsgseqnum());
			maintainCCAFilterVO.setPOACode(dsnPopUpVO.getGpaCode());
		}

		/* Validating Filter Fields
		 * 1.CCA Ref. Num
		 * 2.DSN Number
		 * 3.DSN Date
		 * 4.Issuing Party Code.
		 */
		if ((maintainCCAFilterVO.getCcaReferenceNumber() == null || 
				maintainCCAFilterVO.getCcaReferenceNumber().trim().length() == 0)
				&& (maintainCCAFilterVO.getDsnNumber() == null || 
						maintainCCAFilterVO.getDsnNumber().trim().length() == 0)
		) {

			displayErrorVO = new ErrorVO(ERROR_MANDATORY);
			displayErrorVO.setErrorDisplayType(ErrorDisplayType.INFO);
			errors.add(displayErrorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			return;
		}

		/**
		 * @author A-3227
		 * Validating Issuing Party Code (AirLine/GPA)
		 */
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
					new ErrorVO("mailtracking.mra.defaults.rateauditdetails.msg.err.invalidbillto...",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;    			
				return;
			}			
		}
		/* Added for bug 38119
		 * This is to find gpaarlbillingflag in MTKMRABLGDTL
		 * That flag is setting in maintainccafiltervo as gpaarlindicator
		 * to find the ccareferencenumber
		 * 
		 */
		/*RateAuditVO rateAuditVO = null;
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs = null;
		RateAuditFilterVO rateAuditFilterVO = new RateAuditFilterVO();
		rateAuditFilterVO.setCompanyCode(maintainCCAFilterVO.getCompanyCode());
		rateAuditFilterVO.setBillingBasis(maintainCCAFilterVO.getDsnNumber());
		rateAuditFilterVO.setCsgDocNum(maintainCCAFilterVO.getConsignmentDocNum());
		rateAuditFilterVO.setCsgSeqNum(maintainCCAFilterVO.getConsignmentSeqNum());
		rateAuditFilterVO.setGpaCode(maintainCCAFilterVO.getPOACode());
		rateAuditFilterVO.setDsnDate(maintainCCAFilterVO.getDsnDate());
		if(rateAuditFilterVO.getBillingBasis()!=null && rateAuditFilterVO.getCsgDocNum()!= null){
		try {
		rateAuditVO=mailTrackingMRADelegate.findListRateAuditDetails(rateAuditFilterVO);

		} catch (BusinessDelegateException e) {
			errors=handleDelegateException(e);
		}
		}
		if(rateAuditVO != null){
			rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
		}
		if(!OTHER_AIRLINE.equals(maintainCCAFilterVO.getIssuingParty())){
			if(rateAuditDetailsVOs != null && rateAuditDetailsVOs.size()>0){
				for(RateAuditDetailsVO rateAuditDetailsVO:rateAuditDetailsVOs){
					if("R".equals(rateAuditDetailsVO.getPayFlag())){
						maintainCCAFilterVO.setAirlineGpaIndicator(rateAuditDetailsVO.getGpaarlBillingFlag());
					}
				}
			}
		}else{
			if(rateAuditDetailsVOs != null && rateAuditDetailsVOs.size()>0){
				for(RateAuditDetailsVO rateAuditDetailsVO:rateAuditDetailsVOs){
					if("P".equals(rateAuditDetailsVO.getPayFlag())){
						maintainCCAFilterVO.setAirlineGpaIndicator(rateAuditDetailsVO.getGpaarlBillingFlag());
					}
				}
			}
		}*/
		/*
		 *  Logic of Code :
		 * 
		 * 1. CCA Ref Num is given by the user.
		 * 		* System will directly search for that particular CCA Ref Num in MTKMRACCADTL,
		 * 			-In case found,the details for the particular CCA will be listed directly from MTKMRACCADTL. 
		 * 			-In Case not found, Error is thrown.
		 * 2. CCA Num not given, but other data present.
		 * 		* System will invoke a Pop Up listing the Related CCAs for the given filter details.
		 * 			-Once the Pop Up is invoked, user can select the CCA Ref Num as he wish.
		 * 				-In this case, we will get the CCA Ref Num, and we can proceed by looking 
		 * 					into the MTKMRACCADTL table
		 * 			-Else he can create a new CCA for that filter condition
		 * 				- In this case, details may not be present in MTKMRACCADTL,
		 * 					so join MTKMRABLGMST and MTKMRABLGDTL for taking the particular record.
		 * 
		 */			
		Collection<String> ccaRefNums=null ;

		Collection<CCAdetailsVO> ccaDetailsVOsForCcaPopup=null;
		try {
			//Collecting CCA Reference Number(s)
			ccaDetailsVOsForCcaPopup = mailTrackingMRADelegate.findCCA(maintainCCAFilterVO);	 

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		if(ccaDetailsVOsForCcaPopup!=null){
			ccaDetailsVOs=new ArrayList<CCAdetailsVO>();			
			ccaDetailsVOs.addAll(ccaDetailsVOsForCcaPopup);
			ccaRefNums =  new ArrayList<String>();
			for(CCAdetailsVO cCAdetailsVOIt : ccaDetailsVOsForCcaPopup){
				ccaRefNums.add(cCAdetailsVOIt.getCcaRefNumber());			
				
				if ((MRAConstantsVO.VOIDED.equals(cCAdetailsVOIt.getBillingStatus()))){
					displayErrorVO = new ErrorVO(ERROR_VOID_MAILBAGS);
					displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(displayErrorVO);
					invocationContext.addAllError(errors);				
					invocationContext.target = LIST_FAILURE;
					populateIssuingParty(maintainCCAFilterVO);
					maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
					maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					return;
				}
				
				
			}
			
		}


		if(ccaRefNums!=null && ccaRefNums.size()>0){
			maintainCCASession.removeCCARefNumbers();
			maintainCCASession.removeCCAdetailsVOs();
			maintainCCASession.setCCARefNumbers(ccaRefNums);
			maintainCCASession.setCCAdetailsVOs(ccaDetailsVOs);
			if(maintainCCASession.getCCAdetailsVOs()!=null){
				for(CCAdetailsVO ccAdetailsVO: maintainCCASession.getCCAdetailsVOs()){
					if("A".equals(ccAdetailsVO.getCcaStatus()))
						{
						maintainCCAFilterVO.setApprovedMCAExists(true);
						}
					maintainCCASession.setCCAdetailsVO(ccAdetailsVO);
					maintainCCAFilterVO.setUsrCCANumFlg("N");
				}
			}
			//updateSession(maintainCCASession, maintainCCAFilterVO, maintainCCASession.getCCAdetailsVOs(), maintainCCAForm);

			log.log(Log.FINE, "maintainCCAFilterVO----->", maintainCCAFilterVO);
			if(maintainCCAFilterVO.getCcaReferenceNumber()!=null 
					&& maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0){

				try {
					//CCA Ref Num Present So this call will take the Details from MTKMRACCADTL
					ccaDetailsVOs = mailTrackingMRADelegate.findCCAdetails(maintainCCAFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
				
				Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
		        
		        ArrayList<CCAdetailsVO> ccaVOs = new ArrayList<CCAdetailsVO>();
		        if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
					
		        	if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
		        		ccaVOs=(ArrayList<CCAdetailsVO>)ccaDetailsVOs;
						
						if (ccaVOs.get(0).getDisplayWgtUnit() != null  && oneTimeValues != null && !oneTimeValues.isEmpty() && oneTimeValues.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
							for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) {
								if (oneTimeVO.getFieldValue().equals(ccaVOs.get(0).getDisplayWgtUnit()) ) {																								
									ccaVOs.get(0).setDisplayWgtUnit(oneTimeVO.getFieldDescription());
								}
							}
						}
					}
					
					
				}
				
				//added for IASCB-858
				Collection<CRAParameterVO> cRAParameterVOs = maintainCCASession.getCRAParameterVOs();
				if(cRAParameterVOs != null && !cRAParameterVOs.isEmpty()){
					for(CRAParameterVO cRAParameterVO : cRAParameterVOs){
						if(ccaDetailsVOs!=null&&ccaDetailsVOs.size()>0){
							for(CCAdetailsVO ccadetailsVO:ccaDetailsVOs){
								if(ccadetailsVO.getMcaReasonCodes()!=null){
								String reasonCode[]=ccadetailsVO.getMcaReasonCodes().split(",");
								
								for(int i=0 ; i <reasonCode.length ; ++i){
									if(cRAParameterVO.getParameterCode().equals(reasonCode[i])){
										cRAParameterVO.setParameter(reasonCode[i]);
										break;
									}
								}
								}
							}
							
						}
						else{
							cRAParameterVO.setParameter(null);
						}
					}
				}
				
				if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
					for(CCAdetailsVO ccadetailsVO:ccaDetailsVOs){
						maintainCCAFilterVO.setPartyCode(ccadetailsVO.getIssuingParty());
					}
					maintainCCAFilterVO.setIssuingParty("ARL");
					maintainCCASession.setCCAdetailsVO(updateRouting(ccaDetailsVOs));	
				}
				//
				if(maintainCCAForm.getShowpopUP()!=null &&
						"TRUE".equals(maintainCCAForm.getShowpopUP())){
					//Disabling Popup
					maintainCCAForm.setShowpopUP("FALSE");
				}
			}else{
				/*
				 * Invoking PopUp to list CCA Ref Num.s
				 */
				if(!"TRUE".equals(maintainCCAForm.getShowpopUP())){
					maintainCCAForm.setShowpopUP("TRUE");
				}else{
					//Disabling Popup
					maintainCCAForm.setShowpopUP("FALSE");
					if(FLAG_YES.equals(maintainCCAForm.getCreateCCAFlg())){
						maintainCCAForm.setCreateCCAFlg(FLAG_NO);
						try {
							/*
							 * Creating CCA.
							 * In this Case the details should be taken from 
							 * MTKMRABLGMST & MTKMRABLGDTL table
							 * -----------------------------------------
							 * Modified the flow by A-4823 on 14/06/12 as per change in business.
							 * While creating if there are already approved MCAs, 
							 * then the revised amount of latest approved mca should come as the original amount for the new one
							 * If there are no approved MCA and an MCA exists with status NEW 
							 * then cannot create anther MCA of status N
							 */
							ccaDetailsVOs = mailTrackingMRADelegate.findApprovedMCA(maintainCCAFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
						if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
							maintainCCASession.setCCAdetailsVO(updateRouting(ccaDetailsVOs));	
						}
					}
				}
			}
		}else{
			if(maintainCCAFilterVO.getCcaReferenceNumber()!=null 
					&& maintainCCAFilterVO.getCcaReferenceNumber().trim().length()>0){

				if((maintainCCAForm.getUsrCCANumFlg()== null) || 
						(maintainCCAForm.getUsrCCANumFlg()!= null && 
								("".equals(maintainCCAForm.getUsrCCANumFlg())) ||
								"N".equals(maintainCCAForm.getUsrCCANumFlg()))) {
					/*
					 * This case arises when user give an invalid CCA Ref Num.
					 */
					displayErrorVO = new ErrorVO(ERROR_NO_RESULTS);
					displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(displayErrorVO);
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					populateIssuingParty(maintainCCAFilterVO);
					
					maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
					maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					return;
				}else if(maintainCCAForm.getUsrCCANumFlg()!= null && 
						"Y".equals(maintainCCAForm.getUsrCCANumFlg())){
					try {
						/*
						 * CCA Ref Num not given either by user or not present in MTKMRACCADTL table
						 * So this server call will take the Details from MTKMRABLGMST & MTKMRABLGDTL 
						 * with the rest of filter dtls
						 */
						ccaDetailsVOs = mailTrackingMRADelegate.findCCAdetails(maintainCCAFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){

						maintainCCASession.setCCAdetailsVO(updateRouting(ccaDetailsVOs));	
					}else if(ccaDetailsVOs!=null){
						/*
						 * filter for listing form MTKMRABLGMST & MTKMRABLGDTL is wrong no results.... 
						 */
						displayErrorVO = new ErrorVO(ERROR_NO_RESULTS);
						displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(displayErrorVO);
						invocationContext.addAllError(errors);				
						invocationContext.target = LIST_FAILURE;					
						populateIssuingParty(maintainCCAFilterVO);
						maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
						maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
						return;	
					}

				}				
			}else{	
				if(!"AUTO".equals(maintainCCAForm.getUsrCCANumFlg())){
					/*
					 * This case arises when user give an invalid CCA Ref Num.
					 */
					displayErrorVO = new ErrorVO(ERROR_INVALID_FILTER);
					displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(displayErrorVO);
					invocationContext.addAllError(errors);				
					invocationContext.target = LIST_FAILURE;
					populateIssuingParty(maintainCCAFilterVO);
					maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
					maintainCCAForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
					return;
				}else{
					try {
						/*
						 * CCA Ref Num not given either by user or not present in MTKMRACCADTL table
						 * So this server call will take the Details from MTKMRABLGMST & MTKMRABLGDTL 
						 * with the rest of filter dtls
						 */
						ccaDetailsVOs = mailTrackingMRADelegate.findCCAdetails(maintainCCAFilterVO);
					} catch (BusinessDelegateException businessDelegateException) {
						errors = handleDelegateException(businessDelegateException);
					}
					
					

					Map<String, Collection<OneTimeVO>> oneTimeValues = fetchOneTimeDetails(logonAttributes.getCompanyCode());
			        
			        ArrayList<CCAdetailsVO> ccaVOs = new ArrayList<CCAdetailsVO>();
			        if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
						
			        	if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
			        		ccaVOs=(ArrayList<CCAdetailsVO>)ccaDetailsVOs;
							
							if (ccaVOs.get(0).getDisplayWgtUnit() != null  && oneTimeValues != null && !oneTimeValues.isEmpty() && oneTimeValues.get(WEIGHT_UNIT_ONETIME)!=null && !oneTimeValues.get(WEIGHT_UNIT_ONETIME).isEmpty() ) {
								for (OneTimeVO oneTimeVO : oneTimeValues.get(WEIGHT_UNIT_ONETIME)) {
									if (oneTimeVO.getFieldValue().equals(ccaVOs.get(0).getDisplayWgtUnit()) ) {																								
										ccaVOs.get(0).setDisplayWgtUnit(oneTimeVO.getFieldDescription());
									}
								}
							}
						}
												
					}					

					if(ccaDetailsVOs!=null && ccaDetailsVOs.size()>0){
						CCAdetailsVO ccaDetailsVO=null;
						ccaDetailsVO=((ArrayList<CCAdetailsVO>)ccaDetailsVOs).get(0);
						if(TO_BE_RATED.equals(ccaDetailsVO.getBillingStatus())){
							displayErrorVO = new ErrorVO(ERROR_TO_BE_RATED);
							displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(displayErrorVO);
							invocationContext.addAllError(errors);				
							invocationContext.target = LIST_FAILURE;					
							populateIssuingParty(maintainCCAFilterVO);
							maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
							maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							maintainCCAForm.setShowpopUP("FALSE");
							return;	
						}else if(MRAConstantsVO.ONHOLD.equals(ccaDetailsVO.getBillingStatus())){
							displayErrorVO = new ErrorVO(ERROR_ONHOLD);
							displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(displayErrorVO);
							invocationContext.addAllError(errors);				
							invocationContext.target = LIST_FAILURE;					
							populateIssuingParty(maintainCCAFilterVO);
							maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
							maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							maintainCCAForm.setShowpopUP("FALSE");
						}else if(ccaDetailsVO.getBillingStatus() == null){
							displayErrorVO = new ErrorVO(ERROR_INTERLINE);
							displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(displayErrorVO);
							invocationContext.addAllError(errors);				
							invocationContext.target = LIST_FAILURE;					
							populateIssuingParty(maintainCCAFilterVO);
							maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
							maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
							maintainCCAForm.setShowpopUP("FALSE");
						}
						ccaDetailsVO.setOtherRevChgGrossWgt(ccaDetailsVO.getOtherChgGrossWgt());
						maintainCCASession.setCCAdetailsVO(updateRouting(ccaDetailsVOs));	
						
					}else if(ccaDetailsVOs!=null){
						/*
						 * filter for listing form MTKMRABLGMST & MTKMRABLGDTL is wrong no results.... 
						 */
						displayErrorVO = new ErrorVO(ERROR_NO_RESULTS);
						displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(displayErrorVO);
						invocationContext.addAllError(errors);				
						invocationContext.target = LIST_FAILURE;					
						populateIssuingParty(maintainCCAFilterVO);
						maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
						maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
						return;	
					}
				}
			}
		}
		log.log(Log.FINE, "	maintainCCAForm.getshowpopup------>>",
				maintainCCAForm.getShowpopUP());
		
		updateSession(maintainCCASession,maintainCCAFilterVO,maintainCCASession.getCCAdetailsVO(),maintainCCAForm,logonAttributes);		
		
		//Added as part of bug-icrd-19830 by a-4810
		if(maintainCCAFilterVO.getCcaReferenceNumber()==null || maintainCCAFilterVO.getCcaReferenceNumber().length()==0){
			maintainCCAForm.setCcaPresent("N");
		}
		maintainCCAForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);

		/**
		 * @author A-2554
		 * to implement rounding for weight & volume
		 */
		UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
		maintainCCASession.setWeightRoundingVO(unitRoundingVO);
		maintainCCASession.setVolumeRoundingVO(unitRoundingVO);		
		setUnitComponent(logonAttributes.getStationCode(),maintainCCASession);

		invocationContext.target = LIST_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * @author A-3227 RENO K ABRAHM
	 * @param maintainCCAFilterVO
	 */
	private void populateIssuingParty(MaintainCCAFilterVO maintainCCAFilterVO){
		if(maintainCCAFilterVO.getIssuingParty()!=null){
			if(GPA.equals(maintainCCAFilterVO.getIssuingParty())){
				maintainCCAFilterVO.setIssuingParty("GPA");
			}else{
				maintainCCAFilterVO.setIssuingParty("ARL");
			}
		}
	}

	/**
	 * @author A-3447,A-3227
	 * @param maintainCCASession
	 * @param maintainCCAFilterVO
	 * @param maintainCCAForm
	 * @param logonAttributes 
	 */

	public void updateSession(MaintainCCASession maintainCCASession,
			MaintainCCAFilterVO maintainCCAFilterVO,CCAdetailsVO ccaDetailVO,MRAMaintainCCAForm maintainCCAForm, LogonAttributes logonAttributes ) {
		if(ccaDetailVO!=null && ccaDetailVO.getBillingBasis()!=null){		


			maintainCCAFilterVO.setUsrCCANum(ccaDetailVO.getUsrccanum());
			if(ccaDetailVO.getCcaRefNumber()!=null  && (!"N".equals(maintainCCAFilterVO.getUsrCCANumFlg()))){
				maintainCCAFilterVO.setCcaReferenceNumber(ccaDetailVO.getCcaRefNumber());
			}
			//maintainCCAFilterVO.setDsnNumber(ccaDetailVO.getBillingBasis());
			if(ccaDetailVO.getCcaStatus()!=null && (!"N".equals(maintainCCAFilterVO.getUsrCCANumFlg()))){
				maintainCCAFilterVO.setCcaStatus(ccaDetailVO.getCcaStatus());
			}
			//maintainCCAFilterVO.setPartyCode(ccaDetailVO.getIssuingParty());
			maintainCCAFilterVO.setPartyLocation(ccaDetailVO.getLocation());
			/*if(ccaDetailVO.getGpaArlIndicator()!=null){
				if(GPA.equals(ccaDetailVO.getGpaArlIndicator())){
					maintainCCAFilterVO.setIssuingParty("GPA");				
				}else{
					maintainCCAFilterVO.setIssuingParty("ARL");
				}			
			}*/
			//			if(ccaDetailVO.getGpaArlIndicator()== null){
			//				if(maintainCCAFilterVO.getIssuingParty()!=null){ 
			//					if(GPA.equals(maintainCCAFilterVO.getIssuingParty())){
			//						maintainCCAFilterVO.setIssuingParty("GPA");
			//					}else{
			//						maintainCCAFilterVO.setIssuingParty("ARL");						
			//					}	
			//				}
			//			}
			if(ccaDetailVO.getDsnDate()!=null){
			maintainCCAFilterVO.setDsnDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(ccaDetailVO.getDsnDate()));
			}else{
				maintainCCAFilterVO.setDsnDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));	
			}
			
			//NDA - NDG		
			/*double revduepostdebdisp = -(ccaDetailVO.getRevDueArl().getAmount());
			double duepostdebdisp = -(ccaDetailVO.getDueArl().getAmount());
			Money duePostDbtdisp = null;
			Money revDuePostDbtDisp = null;
			try {
				duePostDbtdisp = CurrencyHelper.getMoney(CURRENCY_CODE);
				duePostDbtdisp.setAmount(duepostdebdisp);
				ccaDetailVO.setDuePostDbtDisp(duePostDbtdisp);
				revDuePostDbtDisp = CurrencyHelper.getMoney(CURRENCY_CODE);
				revDuePostDbtDisp.setAmount(revduepostdebdisp);
				ccaDetailVO.setRevDuePostDbtDisp(revDuePostDbtDisp);

			}catch (CurrencyException e) {
					log.log(Log.SEVERE,"\n\n$$$$$$$$ CurrencyException Check !!!!\n\n");
					e.getErrorCode();
				}	*/


			if(ccaDetailVO.getRevGpaCode()==null||"".equals(ccaDetailVO.getRevGpaCode().trim())){
				ccaDetailVO.setRevGpaCode(ccaDetailVO.getGpaCode());
				ccaDetailVO.setRevGpaName(ccaDetailVO.getGpaName());
				maintainCCAForm.setRevGpaCode(ccaDetailVO.getGpaCode());
				maintainCCAForm.setRevGpaName(ccaDetailVO.getGpaName());
			}
			else{				
				maintainCCAForm.setRevGpaCode(ccaDetailVO.getRevGpaCode());
			}
			if("TRUE".equals(maintainCCAForm.getShowpopUP())){
				ccaDetailVO.getRevChgGrossWeight().setAmount(0);
				ccaDetailVO.getChgGrossWeight().setAmount(0);
				ccaDetailVO.getOtherChgGrossWgt().setAmount(0);
				ccaDetailVO.getOtherRevChgGrossWgt().setAmount(0);
			}
			if(ccaDetailVO.getRevGrossWeight()==0.0 && ccaDetailVO.getUsrccanum()==null){

				ccaDetailVO.setRevGrossWeight(ccaDetailVO.getGrossWeight());				
			}

			
			
			if(ccaDetailVO.getRevChgGrossWeight()!=null){
				//Added check for bug fix ICRD-16263
				if(!"A".equals(ccaDetailVO.getCcaStatus())){


					if(ccaDetailVO.getRevChgGrossWeight().getAmount()==0 && ccaDetailVO.getUsrccanum()==null ){
						ccaDetailVO.getRevChgGrossWeight().setAmount(ccaDetailVO.getChgGrossWeight().getAmount());				
						
						maintainCCAForm.setRevisedChargeGrossWeignt(Double.toString(ccaDetailVO.getChgGrossWeight().getAmount()));
						
						
						if(ccaDetailVO.getNetAmount()!=null){
							ccaDetailVO.getRevNetAmount().setAmount(ccaDetailVO.getNetAmount().getAmount());
						}
					}
				}
				else if("A".equals(ccaDetailVO.getCcaStatus())){




					if(ccaDetailVO.getRevChgGrossWeight()!=null){
						if(ccaDetailVO.getRevChgGrossWeight().getAmount()==0 && ccaDetailVO.getUsrccanum()==null){
							ccaDetailVO.getRevChgGrossWeight().setAmount(-ccaDetailVO.getChgGrossWeight().getAmount());

							
							
							//Added as part of ICRD-154137 starts
							if(ccaDetailVO.getOtherRevChgGrossWgt()!=null&&ccaDetailVO.getOtherChgGrossWgt()!=null){
							ccaDetailVO.getOtherRevChgGrossWgt().setAmount(-ccaDetailVO.getOtherChgGrossWgt().getAmount());
							}
							//Added as part of ICRD-154137 ends
							if(ccaDetailVO.getRevTax()==0){
								ccaDetailVO.setRevTax(-(ccaDetailVO.getTax()));
							}
							

						}
					}



				}
			}

			/*if(ccaDetailVO.getRevDueArl().getAmount()==0){
				ccaDetailVO.getRevDueArl().setAmount(ccaDetailVO.getDueArl().getAmount());				
			}
			if(ccaDetailVO.getRevDuePostDbtDisp().getAmount()==0){
				ccaDetailVO.getRevDuePostDbtDisp().setAmount(ccaDetailVO.getDuePostDbtDisp().getAmount());				
			}*/
			if(ccaDetailVO.getRevContCurCode()==null || "".equals(ccaDetailVO.getRevContCurCode().trim())){
				ccaDetailVO.setRevContCurCode(ccaDetailVO.getContCurCode());
				maintainCCAForm.setRevCurCode(ccaDetailVO.getContCurCode());
			}
			else{
				maintainCCAForm.setRevCurCode(ccaDetailVO.getRevContCurCode());
			}
			if(ccaDetailVO.getRate()!=0){
				
			
				maintainCCAForm.setRate(Double.toString(ccaDetailVO.getRate()));
			}
			
			
			
			if(ccaDetailVO.getRevTax()==0.0||"".equals(String.valueOf(ccaDetailVO.getRevTax()))){
				if(!"A".equals(ccaDetailVO.getCcaStatus())){
					ccaDetailVO.setRevTax(ccaDetailVO.getTax());
					maintainCCAForm.setRevTax(String.valueOf(ccaDetailVO.getTax()));
				}
			}
			else{
				maintainCCAForm.setRevTax(String.valueOf(ccaDetailVO.getRevTax()));
			}
			if(ccaDetailVO.getRevTds()==0.0 || "".equals(String.valueOf(ccaDetailVO.getRevTds()))){
				if(!"A".equals(ccaDetailVO.getCcaStatus())){
					ccaDetailVO.setRevTds(ccaDetailVO.getTds());	
				}
			}

			maintainCCASession.setCCAFilterVO(maintainCCAFilterVO);
			maintainCCAFilterVO.setConsignmentDocNum(ccaDetailVO.getCsgDocumentNumber());
			maintainCCAFilterVO.setConsignmentSeqNum(ccaDetailVO.getCsgSequenceNumber());
			maintainCCAFilterVO.setDsnNumber(ccaDetailVO.getBillingBasis());
			maintainCCAFilterVO.setCcaStatus(ccaDetailVO.getCcaStatus());
			maintainCCAFilterVO.setBillingBasis(ccaDetailVO.getBillingBasis());
			maintainCCAFilterVO.setPOACode(ccaDetailVO.getPoaCode());
			maintainCCAFilterVO.setBlgDtlSeqNum(ccaDetailVO.getBlgDtlSeqNum());
			if(maintainCCAForm.getFromScreen()!=null && 
					maintainCCAForm.getFromScreen().trim().length()>0 &&
					"creatCCA".equals(maintainCCAForm.getFromScreen())){
						
				ccaDetailVO.setCcaType("I");                   
				maintainCCAForm.setFromScreen("");
			}
			maintainCCASession.setCCAdetailsVO(ccaDetailVO);
		}
		log.log(Log.FINE, "ccaDetailVO----->", ccaDetailVO);
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
		oneTimeList.add("mailtracking.mra.surchargeChargeHead");
		oneTimeList.add(GPABILLINGSTATUS_ONETIME);
		oneTimeList.add(WEIGHT_UNIT_ONETIME);
		
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


	/**
	 * @author A-3227
	 * @param cCAdetailsVOs
	 * @return
	 */
	public CCAdetailsVO updateRouting(Collection<CCAdetailsVO> cCAdetailsVOs){
		/*
		 * This method determines the Origin and Destination of the DSN 
		 * based on Flight Routings.
		 */
		CCAdetailsVO ccaDetailsVO=null;
		if(cCAdetailsVOs!=null && cCAdetailsVOs.size()>0){
			ccaDetailsVO=((ArrayList<CCAdetailsVO>)cCAdetailsVOs).get(0);
			Collection<DSNRoutingVO> dsnRoutingVOs = ccaDetailsVO.getDsnRoutingVOs();
			Collection<DSNRoutingVO> dsnRoutings = new ArrayList<DSNRoutingVO>();
			if(dsnRoutingVOs!=null && dsnRoutingVOs.size()>0){
				Set<Integer> routeSerNum =new  TreeSet<Integer>();
				for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
					if(dsnRoutingVO.getRoutingSerialNumber()!=0){
						routeSerNum.add(dsnRoutingVO.getRoutingSerialNumber());						
					}
				}
				if(routeSerNum.size()>0){

					Collection<Integer> routeSerNumList =new ArrayList<Integer>();
					for(Integer serialNumber : routeSerNum){
						routeSerNumList.add(serialNumber);
						boolean firstTime=true;
						for(DSNRoutingVO dsnRouting : dsnRoutingVOs){
							if(serialNumber==dsnRouting.getRoutingSerialNumber() && 
									firstTime){
								dsnRoutings.add(dsnRouting);
								firstTime=false;
							}
						}
					}
					int minSerNum = ((ArrayList<Integer>) routeSerNumList).get(0);
					int maxSerNum = ((ArrayList<Integer>) routeSerNumList).get(routeSerNumList.size()-1);
					String firstPol=null;
					String lastPou=null;
					log.log(Log.FINE, " Min Routing Serial Number----->",
							minSerNum);
					log.log(Log.FINE, " Max Routing Serial Number----->",
							maxSerNum);
					for(DSNRoutingVO dsnRoutingVO : dsnRoutingVOs){
						if(dsnRoutingVO.getRoutingSerialNumber()!=0 ){
							if(dsnRoutingVO.getRoutingSerialNumber()==minSerNum){
								firstPol=dsnRoutingVO.getPol();
							}
							if(dsnRoutingVO.getRoutingSerialNumber()==maxSerNum){
								lastPou=dsnRoutingVO.getPou();
							}
						}
					}
					if(firstPol!=null && firstPol.length()>0){
						ccaDetailsVO.setOriginCode(firstPol);
						ccaDetailsVO.setRevOrgCode(firstPol);
						log.log(Log.FINE, " Origin Code----->", firstPol);
					}
					if(lastPou!=null && lastPou.length()>0){
						ccaDetailsVO.setDestnCode(lastPou);
						if(ccaDetailsVO.getRevDStCode()==null){
							ccaDetailsVO.setRevDStCode(lastPou);
						}
						log.log(Log.FINE, " Destination Code ----->", lastPou);
					}
					ccaDetailsVO.setDsnRoutingVOs(dsnRoutings);
				}

			}
		}
		return ccaDetailsVO;		

	}
	/**
	 * @author A-2554
	 * @param stationCode
	 * @param maintainCCASession
	 */
	private void setUnitComponent(String stationCode,
			MaintainCCASession maintainCCASession){
		UnitRoundingVO unitRoundingVO = null;
		try{
			log.log(Log.FINE, "\n\n<----STATION CODE IS----------->",
					stationCode);
			unitRoundingVO = UnitFormatter.getUnitRoundingForUnitCode(UnitConstants.WEIGHT, UnitConstants.WEIGHT_UNIT_KILOGRAM);			
			log.log(Log.FINE,
					"\n\n<----UNIT ROUNDING VO FOR WEIGHT IN SESSION--->",
					unitRoundingVO);
			maintainCCASession.setWeightRoundingVO(unitRoundingVO);
			unitRoundingVO = UnitFormatter.getStationDefaultUnit(
					stationCode, UnitConstants.VOLUME);
			log.log(Log.FINE,
					"\n\n<----UNIT ROUNDING VO FOR VOLUME IN SESSION--->",
					unitRoundingVO);
			maintainCCASession.setVolumeRoundingVO(unitRoundingVO);

		}catch(UnitException unitException) {
			unitException.getErrorCode();
		}

	}



}
