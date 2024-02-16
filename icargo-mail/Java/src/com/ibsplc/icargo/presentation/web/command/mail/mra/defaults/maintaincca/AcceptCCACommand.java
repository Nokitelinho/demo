/*
 * AcceptCCACommand.java Created on Aug 26,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintaincca;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainCCAFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SurchargeCCAdetailsVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.currency.CurrencyDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainCCASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAMaintainCCAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class AcceptCCACommand extends BaseCommand {


	/**
	 * 
	 * Module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	/**
	 * Screen ID
	 */
	private static final String MAINTAINCCA_SCREEN = "mailtracking.mra.defaults.maintaincca";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");
	private static final String ACCEPT_SUCCESS = "accept_success";
	private static final String ACCEPT_FAILURE = "accept_failure";
	private static final String ACTUAL = "A";
	private static final String UPDATE = "UPDATE";
	private static final String NO = "N";
	private static final String YES = "Y";
	private static final String APPROVED = "A";
	private static final String BILLABLE = "BB";
	private static final String INVALID_CURCODE = "mailtracking.mra.defaults.maintaincca.invalidcurcode";
	private static final String BASE_CURRENCY = "shared.airline.basecurrency";
	private static final String SYS_PARAM_WRKFLOWENABLED="mailtracking.mra.workflowneededforMCA";
	private static final String BASED_ON_RULES = "R";//Added for IASCB-2373
	private static final String ACCEPTMCA = "ACPMCA";//Added for IASCB-2373
	private static final String ACCEPTED = "C";//Added as part of ICRD-329873

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering("DeleteCCACommand", "execute");
		MaintainCCASession maintainCCASession = (MaintainCCASession) getScreenSession(
				MODULE_NAME, MAINTAINCCA_SCREEN);
		MRAMaintainCCAForm maintainCCAForm = (MRAMaintainCCAForm) invocationContext.screenModel;		
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();				
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		RateAuditVO rateAuditVO =  maintainCCASession.getRateAuditVO();
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs =null;
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();
		Collection<String> systemParameterCodes = new ArrayList<String>();
		Collection<ErrorVO> errs = validateRevCurCod(maintainCCAForm,logonAttributes.getCompanyCode());
		if (errs != null && errs.size() > 0) {
			errors.add(new ErrorVO(INVALID_CURCODE));
			invocationContext.addAllError(errors);
			invocationContext.target = ACCEPT_FAILURE;
			return;
		}
		int changesDone = new SaveCCACommand().multiModificationRestriction(maintainCCAForm,ccaDetailsVO);
			if(changesDone > 1){
				updateForm(maintainCCAForm,maintainCCASession);
				ErrorVO displayErrorVO = new ErrorVO("mailtracking.mra.defaults.maintaincca.multichangerestrict");
				displayErrorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(displayErrorVO);
				invocationContext.addAllError(errors);
				invocationContext.target = ACCEPT_FAILURE;
				return;
			}
		ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		if(maintainCCAForm.getRevGpaCode().equals(maintainCCAForm.getGpaCode())){
			ccaDetailsVO.setRevGpaCode(null);
		}

		/*Added by A-3434 for bug 46427
		 * When revGpaCode changes,
		 * updbillto in MTKMRABLGDTL should update,
		 * if rateAuditVO is not null.
		 * if rateAuditVO is  null, code is written in saveHistoryDetails method of
		 * MRADefaultsController..
		 */
		if(ccaDetailsVO.getRevGpaCode()!= null && !"".equals(ccaDetailsVO.getRevGpaCode())){
			if(rateAuditVO != null){
				rateAuditDetailsVOs =  rateAuditVO.getRateAuditDetails();
			}

			if(rateAuditDetailsVOs!=null && rateAuditDetailsVOs.size()>0){
				for(RateAuditDetailsVO rateAuditDetailsVO:rateAuditDetailsVOs){
					if("R".equals(rateAuditDetailsVO.getPayFlag())){
						rateAuditDetailsVO.setBillTO(ccaDetailsVO.getRevGpaCode());
					}
				}
			}
		}
		log.log(Log.INFO, "rate audit vo in ACCEPT CCA COMMAND ", rateAuditVO);
		if(rateAuditVO!=null){

			rateAuditVO.setOperationFlag(UPDATE);
			try {
				rateAuditVO.setLastUpdateUser(logonAttributes.getUserId());
				mailTrackingMRADelegate.saveRateAuditDetails(rateAuditVO);
			} catch (BusinessDelegateException e) {
				errors=handleDelegateException(e);
			}
		}
		/* CHANGE DONE BY INDU
		//when no wt changes are done before autorate rateaudit vo will  be null 
		//and history details(log) will get saved through saveHistoryDetails.
		// rate audit vo null check is done before saveHistoryDetails to avoid duplicate entries.(Added for bug 44972)*/

		if(ccaDetailsVO!=null){		
			log.log(Log.INFO, "ccaDetailsVO in ACCEPT CCA COMMAND ",
					ccaDetailsVO);
			//ccaDetailsVO.setCcaType(ACTUAL);	
			ccaDetailsVO.setCcaStatus(APPROVED);

			ccaDetailsVO.setBillingStatus(BILLABLE);
			//Added for ICRD-154137 starts,updating session when Accept button clicked without clicking surchargeCCA button
			Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs=null;
			surchargeCCAdetailsVOs=maintainCCASession.getSurchargeCCAdetailsVOs();
			if(surchargeCCAdetailsVOs==null || surchargeCCAdetailsVOs.size()==0){
				Collection<String> codes = new ArrayList<String>();
				codes.add(BASE_CURRENCY);
				Map<String, String> results = new HashMap<String, String>();
				try {
					results = new SharedDefaultsDelegate()
							.findSystemParameterByCodes(codes);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				String baseCurrency = results.get(BASE_CURRENCY);
				MaintainCCAFilterVO maintainCCAFilterVO = new MaintainCCAFilterVO();
				maintainCCAFilterVO = maintainCCASession.getMaintainCCAFilterVO();
				if (maintainCCAFilterVO != null) {
					maintainCCAFilterVO.setBaseCurrency(baseCurrency);
					if (maintainCCAFilterVO.getCcaReferenceNumber() != null) {
						try {
							surchargeCCAdetailsVOs = mailTrackingMRADelegate
									.getSurchargeCCADetails(maintainCCAFilterVO);
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
						}
					}
					ccaDetailsVO
							.setSurchargeCCAdetailsVOs((ArrayList<SurchargeCCAdetailsVO>) surchargeCCAdetailsVOs);
				}
				}
			//Added for ICRD-154137 ends
			
			//Added for IASCB-2373 starts
			
			systemParameterCodes.add(SYS_PARAM_WRKFLOWENABLED);
			Map<String, String> systemParameters = null;		
			try {
				systemParameters = sharedDefaultsDelegate
				.findSystemParameterByCodes(systemParameterCodes);
			} catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				
			}
			if(systemParameters!=null &&systemParameters.size()>0 ){
				if(systemParameters.containsValue(BASED_ON_RULES)){//Modified For IASCB-2373
					ccaDetailsVO.setAcceptRejectIdentifier(ACCEPTMCA);
				}
			}
			String origin = ccaDetailsVO.getOrigin();
			AirportValidationVO airportValidationVO = null;
			String countryCode= null;
			if(origin !=null){
				try {
					airportValidationVO = areaDelegate.validateAirportCode(
		    				logonAttributes.getCompanyCode(),origin);
				}catch(BusinessDelegateException e){
					log.log(Log.INFO, "country is null");
				}
				if(airportValidationVO!=null)
					countryCode = airportValidationVO.getCountryCode();
			}
			 
			if(countryCode!= null){
				ccaDetailsVO.setCountryCode(countryCode);
			}
			if(ccaDetailsVO.getMcaReasonCodes()!= null){
				ccaDetailsVO.setCcaReason(ccaDetailsVO.getMcaReasonCodes());
			}
			if(ccaDetailsVO.getContCurCode()!= null){
				ccaDetailsVO.setCurCode(ccaDetailsVO.getContCurCode());
			}else{
				ccaDetailsVO.setCurCode(ccaDetailsVO.getNetAmount().getCurrencyCode());
			}
			//Added for IASCB-2373 ends
			if(rateAuditVO==null){
				try {
					ccaDetailsVO.setLastUpdateUser(logonAttributes.getUserId());
			   //Added as part of ICRD-340886 starts
					String netvalue=null;
					//added as part of ICRD-341976 starts
					if(maintainCCAForm.getDifferenceAmount()!=null){
					netvalue=String.valueOf(maintainCCAForm.getDifferenceAmount());
	    	    	if(netvalue.contains("-"))
	    	    	ccaDetailsVO.setNetValue(Double.valueOf(netvalue.substring(1)));	
	    	    	else
					ccaDetailsVO.setNetValue(Double.valueOf(maintainCCAForm.getDifferenceAmount()));
					}
					//added as part of ICRD-341976 ends
					//Added as part of ICRD-340886 ends
					//Added as part of ICRD-340892 starts
					String ooe;
					ooe=ccaDetailsVO.getBillingBasis();
					ccaDetailsVO.setOriginOE(ooe.substring(0,6));
					ccaDetailsVO.setDestinationOE(ooe.substring(6,12));
					//Added as part of ICRD-340892 ends
					mailTrackingMRADelegate.saveHistoryDetails(ccaDetailsVO);
				} catch (BusinessDelegateException e) {
					errors=handleDelegateException(e);
				}
			}
			
		}

		maintainCCAForm.setDisableFlag(YES);
		//Added as part of ICRD-329873
		String status=null;
		try{
			status=	mailTrackingMRADelegate.getmcastatus(ccaDetailsVO);
		}
		 catch (BusinessDelegateException e) {
				// TODO Auto-generated catch block
				
			}
		if((ACCEPTED.equals(status))){
			
			maintainCCASession.setStatusinfo("C");
		}
				
		else		
		maintainCCASession.setStatusinfo(UPDATE);
		invocationContext.target = ACCEPT_SUCCESS;


	}
	/**
	 * 
	 * @param maintainCCAForm
	 * @param maintainCCASession
	 */
	public void updateForm(MRAMaintainCCAForm maintainCCAForm,MaintainCCASession maintainCCASession){
		CCAdetailsVO ccaDetailsVO = maintainCCASession.getCCAdetailsVO();
		ccaDetailsVO.setCcaType(maintainCCAForm.getCcaType());
		ccaDetailsVO.setRevGrossWeight(Double.parseDouble(maintainCCAForm.getRevGrossWeight()));
		ccaDetailsVO.getRevChgGrossWeight().setAmount(Double.parseDouble(maintainCCAForm.getRevChgGrossWeight()));
		if(maintainCCAForm.getRevDueArl()!=null){
			ccaDetailsVO.getRevDueArl().setAmount(Double.parseDouble(maintainCCAForm.getRevDueArl()));
		}
		if(maintainCCAForm.getRevDuePostDbt()!=null){
			ccaDetailsVO.getRevDuePostDbtDisp().setAmount(Double.parseDouble(maintainCCAForm.getRevDuePostDbt()));	
		}
		ccaDetailsVO.setRevDStCode(maintainCCAForm.getRevDStCode());
		ccaDetailsVO.setRevGpaCode(maintainCCAForm.getRevGpaCode());
		ccaDetailsVO.setRevGpaName(maintainCCAForm.getRevGpaName());
		ccaDetailsVO.setCcaRemark(maintainCCAForm.getRemarks());	
		//Added for CRQ-7352
		ccaDetailsVO.setCcaStatus(maintainCCAForm.getCcaStatus());
		ccaDetailsVO.setRevTax(Double.parseDouble(maintainCCAForm.getRevTax()));
		ccaDetailsVO.setRevContCurCode(maintainCCAForm.getRevCurCode());
		ccaDetailsVO.setCurrChangeInd(maintainCCAForm.getCurChgInd());
		ccaDetailsVO.setRevisedRate(Double.parseDouble(maintainCCAForm.getRevisedRate())); //Added by A-7929 as part of ICRD-278016
		//ccaDetailsVO.setMailChg(maintainCCAForm.getRevChgGrossWeight()); 
	}
	/**
	 * 
	 * @param maintainCCAForm
	 * @param companyCode
	 */
	private Collection<ErrorVO> validateRevCurCod(MRAMaintainCCAForm maintainCCAForm,String companyCode) {
		try {
			new CurrencyDelegate().validateCurrency(companyCode, maintainCCAForm
					.getRevCurCode().trim().toUpperCase());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "getRevCurCode not valid....");
			businessDelegateException.getMessage();
			return handleDelegateException(businessDelegateException);
		}
		return null;
	}

}
