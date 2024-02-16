/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening.SaveScreeningDetailsCommand.java;
 *
 *	Created by	:	A-9084
 *	Created on	:	Nov 10, 2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ApplicableRegulation;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.SecurityExemption;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveScreeningDetailsCommand extends AbstractCommand{
	
	private Log log = LogFactory.getLogger("MAIL");
	 private static final String STATUS_SUCCESS = "STATUS_SUCCESS";
	 private static final String STATUS_FAIL = "STATUS_FAIL";
	 private static final String SCREEN_LEVEL_VALUE ="C";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("SaveScreeningDetails", "execute");
		String response ="";
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		Collection<ErrorVO> errors = new ArrayList<>();
		ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();
		ScreeningDetails screeningDetails= consignmentScreeningModel.getScreeningDetails();
		ConsignerDetails consignerDetails=consignmentScreeningModel.getConsignerDetails();
		SecurityExemption securityExemption =consignmentScreeningModel.getSecurityExemption();
		ApplicableRegulation applicableRegulation = consignmentScreeningModel.getApplicableRegulation();
		ConsignmentScreeningVO consignmentScreeningvo =new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVO =new ArrayList<>();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		AreaDelegate areaDelegate = new AreaDelegate();	
		consignmentScreeningvo.setCompanyCode(logonAttributes.getCompanyCode()); 
		if(consignmentScreeningModel.getScreeningDetails()!=null){
				setScreeningDetails(consignmentScreeningvo, screeningDetails);
				errors = setIssuerNamerMaster(actionContext, responseVO, logonAttributes, errors, screeningDetails,
						delegate);
		}
		else if(consignmentScreeningModel.getConsignerDetails()!=null){
			Collection<String> countrycode = new ArrayList<>();
			countrycode.add(consignerDetails.getIsoCountryCode());
			if (null!= consignerDetails.getIsoCountryCode()  && !"".equals(consignerDetails.getIsoCountryCode())) {
				try {
					areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrycode);
					}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
					}
					if (!errors.isEmpty()) {
						ErrorVO error =  new ErrorVO("INVALID_COUNTRY_CODE");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);				
						actionContext.addError(error);
						responseVO.setStatus(STATUS_SUCCESS);
						actionContext.setResponseVO(responseVO);
						return;	
					}
		else{
				consignmentScreeningvo.setIsoCountryCode(consignerDetails.getIsoCountryCode());
					}
				}
			setConsignerDetails(consignmentScreeningvo, consignerDetails);
		}
		else if(consignmentScreeningModel.getSecurityExemption()!=null){
			setSecurityExemption(securityExemption, consignmentScreeningvo);
		}
		else
		{
			setApplicableRegulation(applicableRegulation, consignmentScreeningvo);
		}
		consignmentScreeningvo.setScreenLevelValue(SCREEN_LEVEL_VALUE);
		consignmentScreeningvo.setScreeningLocation(logonAttributes.getAirportCode());
		consignmentScreeningVO.add(consignmentScreeningvo);
		if(MailConstantsVO.FLAG_YES.equals(consignmentScreeningvo.getOpFlag())){
			setSerialNumber(consignmentScreeningModel, screeningDetails, consignerDetails, securityExemption,
					applicableRegulation, consignmentScreeningvo);
			try {
				delegate.editscreeningDetails( consignmentScreeningVO);
				} catch (BusinessDelegateException businessDelegateException) {
					errors=handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
				}
		response=((errors==null)?STATUS_SUCCESS:STATUS_FAIL);
		}
		else{
		try {
			delegate.saveSecurityDetails(consignmentScreeningVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();	
				}
		response=((errors==null)?STATUS_SUCCESS:STATUS_FAIL);}
		if(errors == null){
	        responseVO.setStatus(response);
			actionContext.setResponseVO(responseVO);
			 return;
		}
		 log.exiting("SaveScreeningDetails","execute");
	}



	private Collection<ErrorVO> setIssuerNamerMaster(ActionContext actionContext, ResponseVO responseVO,
			LogonAttributes logonAttributes, Collection<ErrorVO> errors, ScreeningDetails screeningDetails,
			MailTrackingDefaultsDelegate delegate) {
		String response;
		ConsignmentDocumentVO consignmentDocumentVO =new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode()); 
		consignmentDocumentVO.setConsignmentNumber(screeningDetails.getConsignmentNumber());
		consignmentDocumentVO.setConsignmentSequenceNumber(Integer.parseInt(screeningDetails.getConsignmentSequenceNumber()));
		consignmentDocumentVO.setPaCode(screeningDetails.getPaCode());
		consignmentDocumentVO.setSecurityReasonCode(screeningDetails.getSecurityReasonCode());
		consignmentDocumentVO.setConsignmentIssuerName(screeningDetails.getSecurityStatusParty());
		try {
			delegate.saveConsignmentDetailsMaster( consignmentDocumentVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors=handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
			response=((errors==null)?STATUS_SUCCESS:STATUS_FAIL);
			if(errors == null){
		        responseVO.setStatus(response);
				actionContext.setResponseVO(responseVO);
			}
		return errors;
	}
	private void setSerialNumber(ConsignmentScreeningModel consignmentScreeningModel, ScreeningDetails screeningDetails,
			ConsignerDetails consignerDetails, SecurityExemption securityExemption,
			ApplicableRegulation applicableRegulation, ConsignmentScreeningVO consignmentScreeningvo) {
		long sernum;
		if(consignmentScreeningModel.getScreeningDetails()!=null){
			 sernum = Long.parseLong(screeningDetails.getSerialnum());
		}
		else if(consignmentScreeningModel.getConsignerDetails()!=null){
			 sernum = Long.parseLong(consignerDetails.getSerialnum());
		}
		else if(consignmentScreeningModel.getSecurityExemption()!=null){
			 sernum = Long.parseLong(securityExemption.getSerialnum());
		}
		else
		{
			 sernum = Long.parseLong(applicableRegulation.getSerialnum());
		}
		consignmentScreeningvo.setSerialNumber(sernum);
	}



	private void setSecurityExemption(SecurityExemption securityExemption,
			ConsignmentScreeningVO consignmentScreeningvo) {
		consignmentScreeningvo.setSeScreeningReasonCode(securityExemption.getScreeningMethodCode());
		consignmentScreeningvo.setSeScreeningAuthority(securityExemption.getScreeningAuthority());
		consignmentScreeningvo.setSeScreeningRegulation(securityExemption.getScreeningRegulation());
		consignmentScreeningvo.setConsignmentNumber(securityExemption.getConsignmentNumber());
		consignmentScreeningvo.setConsignmentSequenceNumber(Integer.parseInt(securityExemption.getConsignmentSequenceNumber()));
		consignmentScreeningvo.setPaCode(securityExemption.getPaCode());
		consignmentScreeningvo.setSecurityReasonCode(securityExemption.getSecurityReasonCode());
		consignmentScreeningvo.setOpFlag(securityExemption.getOpFlag());
	}
	private void setApplicableRegulation(ApplicableRegulation applicableRegulation,
			ConsignmentScreeningVO consignmentScreeningvo) {
		consignmentScreeningvo.setApplicableRegBorderAgencyAuthority(applicableRegulation.getApplicableRegBorderAgencyAuthority());
		consignmentScreeningvo.setApplicableRegTransportDirection(applicableRegulation.getApplicableRegTransportDirection());
		consignmentScreeningvo.setApplicableRegReferenceID(applicableRegulation.getApplicableRegReferenceID());
		consignmentScreeningvo.setApplicableRegFlag(applicableRegulation.getApplicableRegFlag());
		consignmentScreeningvo.setConsignmentNumber(applicableRegulation.getConsignmentNumber());
		consignmentScreeningvo.setConsignmentSequenceNumber(Integer.parseInt(applicableRegulation.getConsignmentSequenceNumber()));
		consignmentScreeningvo.setPaCode(applicableRegulation.getPaCode());
		consignmentScreeningvo.setSecurityReasonCode(applicableRegulation.getSecurityReasonCode());
		consignmentScreeningvo.setOpFlag(applicableRegulation.getOpFlag());
	}
	private void setConsignerDetails(ConsignmentScreeningVO consignmentScreeningvo, ConsignerDetails consignerDetails) {
		consignmentScreeningvo.setAgentType(consignerDetails.getAgenttype());
		consignmentScreeningvo.setAgentID(consignerDetails.getAgentId());
		consignmentScreeningvo.setIsoCountryCode(consignerDetails.getIsoCountryCode());
		consignmentScreeningvo.setExpiryDate(consignerDetails.getExpiryDate());
		consignmentScreeningvo.setConsignmentNumber(consignerDetails.getConsignmentNumber());
		consignmentScreeningvo.setConsignmentSequenceNumber(Integer.parseInt(consignerDetails.getConsignmentSequenceNumber()));
		consignmentScreeningvo.setPaCode(consignerDetails.getPaCode());
		consignmentScreeningvo.setSecurityReasonCode(consignerDetails.getSecurityReasonCode());
		consignmentScreeningvo.setOpFlag(consignerDetails.getOpFlag());
	}
	private void setScreeningDetails(ConsignmentScreeningVO consignmentScreeningvo, ScreeningDetails screeningDetails) {
				consignmentScreeningvo.setScreeningLocation(screeningDetails.getScreeningLocation());
				consignmentScreeningvo.setScreeningMethodCode(screeningDetails.getScreeningMethodCode());
				consignmentScreeningvo.setStatedBags(Integer.parseInt(screeningDetails.getStatedBags()));
				if(screeningDetails.getStatedWeight()!=null)
				{
					Double statedWeight= Double.parseDouble(screeningDetails.getStatedWeight());
					consignmentScreeningvo.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,statedWeight));
				}
				consignmentScreeningvo.setScreeningRegulation(screeningDetails.getScreeningRegulation());
				consignmentScreeningvo.setAdditionalSecurityInfo(screeningDetails.getAdditionalSecurityInfo());
				consignmentScreeningvo.setResult(screeningDetails.getResult());
				consignmentScreeningvo.setRemarks(screeningDetails.getRemarks());
				consignmentScreeningvo.setConsignmentNumber(screeningDetails.getConsignmentNumber());
				consignmentScreeningvo.setConsignmentSequenceNumber(Integer.parseInt(screeningDetails.getConsignmentSequenceNumber()));
				consignmentScreeningvo.setScreeningAuthority(screeningDetails.getScreeningAuthority());
				consignmentScreeningvo.setSecurityStatusParty(screeningDetails.getSecurityStatusParty());
				consignmentScreeningvo.setSecurityReasonCode(screeningDetails.getSecurityReasonCode());
				consignmentScreeningvo.setPaCode(screeningDetails.getPaCode());
		consignmentScreeningvo.setOpFlag(screeningDetails.getOpFlag());
		if(null!=screeningDetails.getSecurityStatusDate()&&null!=screeningDetails.getTime() && ""!=screeningDetails.getSecurityStatusDate())
				{
					LocalDate sd = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		      String scanDT = screeningDetails.getSecurityStatusDate() + " " + screeningDetails.getTime() + ":00";	
						consignmentScreeningvo.setSecurityStatusDate(sd.setDateAndTime(scanDT,false));
					}
	}

}
