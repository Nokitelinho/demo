package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignorDetailpopup;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetailpopup;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	28-Apr-2022	:	Draft
 */
public class SaveCommand extends AbstractCommand {

	private static final String COMMAND_NAME = "SaveCommand";
	 private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	 private static final String SCREEN_ID ="MTK073";
	 private static final String INVALID_LOCATION= "mail.operations.ux.mailbagsecuritydetails.msg.err.invalidlocation";
	 private static final String INVALID_COUNTRY_CODE= "mail.operations.ux.mailbagsecuritydetails.msg.err.invalidcountrycode";
	 private static final String STATUS_SUCCESS = "success";
	 public static final String PLAN_ROUTE_MISSING_FOR_MAILBAG_SCREENING = "Screening cannot captured as CARDIT/Plan Route file is missing";
	 public static final String INVALID_MAILFORAMT ="The MailBag format is invalid";
	 public static final String INVALID_OFFICEOFEXCHANGE ="Invalid office of exchange";
	 public static final String INVALID_PA ="Invalid Postal Authority";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		LOGGER.info("entering into" + COMMAND_NAME + "class");     
		MailbagSecurityModel mailbagSecurityModel = (MailbagSecurityModel) actionContext.getScreenModel();
		Collection<ConsignmentScreeningVO> consignmentScreeningVO =new ArrayList<>();
		LogonAttributes logonAttributes = getLogonAttribute();
		String editStatus;
		Collection<ErrorVO> errors = null;
		ResponseVO responseVO = new ResponseVO();

		AreaDelegate areaDelegate = new AreaDelegate();	
		ScreeningDetailpopup screeningDetailpopup =  mailbagSecurityModel.getScreeningDetailpopup();
		ConsignorDetailpopup consignorDetailpopup = mailbagSecurityModel.getConsignorDetailpopup();

		ConsignmentScreeningVO consignmentScreeningvo =new ConsignmentScreeningVO();
		consignmentScreeningvo.setCompanyCode(logonAttributes.getCompanyCode()); 
		consignmentScreeningvo.setSource(SCREEN_ID); 
		
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		editStatus= (mailbagSecurityModel.getScreeningDetailpopup()!=null)?screeningDetailpopup.getEditStatus():consignorDetailpopup.getEditStatus();

		
		long malseq = Long.parseLong((mailbagSecurityModel.getScreeningDetailpopup()!=null)?screeningDetailpopup.getMailseqnum():consignorDetailpopup.getMailseqnum());
		long sernum = Long.parseLong((mailbagSecurityModel.getScreeningDetailpopup()!=null)?screeningDetailpopup.getSernum():consignorDetailpopup.getSernum());
		
		consignmentScreeningvo.setMalseqnum(malseq);
		consignmentScreeningvo.setSerialNumber(sernum);
		
		if(mailbagSecurityModel.getScreeningDetailpopup()!=null)
		{				
			consignmentScreeningvo.setCompanyCode(logonAttributes.getCompanyCode()); 
			String screenLocation= screeningDetailpopup.getLocation();
			if (screenLocation != null && !"".equals(screenLocation)) {
				try {
					areaDelegate.validateAirportCode(	logonAttributes.getCompanyCode(),screenLocation);

				}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				}
				if (errors!=null) {
					 ErrorVO error =  new ErrorVO(INVALID_LOCATION);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);				
						actionContext.addError(error);
					    responseVO.setStatus(STATUS_SUCCESS);
						actionContext.setResponseVO(responseVO);
						return;		
				}
				else
				{
			consignmentScreeningvo.setScreeningLocation(screeningDetailpopup.getLocation());
				}
				
				}
			consignmentScreeningvo.setMailIdr(screeningDetailpopup.getMailbagid());
			consignmentScreeningvo.setIssuedBy(screeningDetailpopup.getSecurityStatus());
			screeningDetailToConsignmentscreeningVO(consignmentScreeningVO, screeningDetailpopup,
					consignmentScreeningvo);
		}
		
		if(mailbagSecurityModel.getConsignorDetailpopup()!=null)
		{
			consignmentScreeningvo.setAgentType(consignorDetailpopup.getAgenttype());
			consignmentScreeningvo.setAgentID(consignorDetailpopup.getAgentId());
			Collection<String> countrycode = new ArrayList<>();
			countrycode.add(consignorDetailpopup.getIsoCountryCode());
			if (null!= consignorDetailpopup.getIsoCountryCode()  && !"".equals(consignorDetailpopup.getIsoCountryCode())) {
			try {
				areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrycode);
				}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				}
				if (errors!=null) {
					 ErrorVO error =  new ErrorVO(INVALID_COUNTRY_CODE);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);				
						actionContext.addError(error);
					    responseVO.setStatus(STATUS_SUCCESS);
						actionContext.setResponseVO(responseVO);
						return;	
				}
				else{
			consignmentScreeningvo.setIsoCountryCode(consignorDetailpopup.getIsoCountryCode());
				}
			}
			consignmentScreeningvo.setExpiryDate(consignorDetailpopup.getExpiryDate());
			consignmentScreeningvo.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
			consignmentScreeningvo.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
			consignmentScreeningvo.setScreeningLocation(logonAttributes.getAirportCode()); 
			consignmentScreeningvo.setMailIdr(consignorDetailpopup.getMailbagid());
			consignmentScreeningVO.add(consignmentScreeningvo);
		}

		if(MailConstantsVO.FLAG_YES.equals(editStatus))
		{
			try {
				delegate.editscreeningDetails( consignmentScreeningVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
					businessDelegateException.getMessage();
				}
		}
		else
		{
			try {
			delegate.saveSecurityDetails( consignmentScreeningVO);
			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
			if (errors!=null) {
				
				for (ErrorVO err : errors) {
					if (PLAN_ROUTE_MISSING_FOR_MAILBAG_SCREENING
							.equalsIgnoreCase(err.getErrorCode())) {
						
						ErrorVO error = new ErrorVO(
								PLAN_ROUTE_MISSING_FOR_MAILBAG_SCREENING);
						errors.add(error);
						actionContext.addError(error);
						return;
					} else if (INVALID_MAILFORAMT
							.equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO(
								INVALID_MAILFORAMT);
						errors.add(error);
						actionContext.addError(error);
						return;
					}
					else if (INVALID_OFFICEOFEXCHANGE
							.equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO(
								INVALID_OFFICEOFEXCHANGE);
						errors.add(error);
						actionContext.addError(error);
						return;
					}
					else if (INVALID_PA
							.equalsIgnoreCase(err.getErrorCode())){
						ErrorVO error = new ErrorVO(
								INVALID_PA);
						errors.add(error);
						actionContext.addError(error);
						return;
					}
					else{
						return;
					}
				}
			}
		}
	}
	private void screeningDetailToConsignmentscreeningVO(Collection<ConsignmentScreeningVO> consignmentScreeningVO,
			ScreeningDetailpopup screeningDetailpopup, ConsignmentScreeningVO consignmentScreeningvo) {
		consignmentScreeningvo.setSecurityStatusParty(screeningDetailpopup.getSecurityStatus());
		consignmentScreeningvo.setScreeningMethodCode(screeningDetailpopup.getMethod());
		consignmentScreeningvo.setResult(screeningDetailpopup.getStatus());
		
		if(null!=screeningDetailpopup.getDate()	&&null!=screeningDetailpopup.getTime()){
		LocalDate sd = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		String scanDT = new StringBuilder(screeningDetailpopup.getDate()).append(" ")
						.append(screeningDetailpopup.getTime()).append(":00").toString();
		consignmentScreeningvo.setSecurityStatusDate(sd.setDateAndTime(scanDT,false));
		}
		consignmentScreeningvo.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_SCREENING);
		consignmentScreeningvo.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
		consignmentScreeningVO.add(consignmentScreeningvo);
	}

}