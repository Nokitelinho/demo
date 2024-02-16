package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;


import com.ibsplc.icargo.business.mail.operations.vo.MailScreeningFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentScreeningDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ListMailbagSecurityCommand extends AbstractCommand {

	private static final String COMMAND_NAME = "ListMailbagSecurityCommand";
	 private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	
	 private static final String NO_MAILBAG = "mail.operations.ux.mailbagsecuritydetails.msg.err.noMailbag";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
	
       LOGGER.info("entering into" + COMMAND_NAME + "class");     
		MailbagSecurityModel mailbagSecurityModel = (MailbagSecurityModel) actionContext.getScreenModel();
		MailScreeningFilterVO mailScreeningFilterVo = new MailScreeningFilterVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		mailScreeningFilterVo.setMailBagId(mailbagSecurityModel.getMailbagId());
		mailScreeningFilterVo.setCompanyCode(logonAttributes.getCompanyCode());
		MailbagVO mailbagVO = new MailbagVO();
		mailbagSecurityModel.setAirportCode(logonAttributes.getAirportCode());
		mailbagSecurityModel.setLoginUser(logonAttributes.getUserId());
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		ResponseVO responseVO = new ResponseVO();
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	
		try {
			mailbagVO = delegate.listmailbagSecurityDetails(mailScreeningFilterVo);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		
		try {
			oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());

		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
			e.getMessage();
		}

		if (mailbagVO != null && mailbagVO.getMailSequenceNumber()!=0) {

			mailbagSecurityModel.setOrigin(mailbagVO.getOrigin());
			mailbagSecurityModel.setDestination(mailbagVO.getDestination());
			mailbagSecurityModel.setMalseqnum(mailbagVO.getMailSequenceNumber());
			mailbagSecurityModel.setSecurityStatusCode(mailbagVO.getSecurityStatusCode());  
			mailbagSecurityModel.setAirportCode(logonAttributes.getAirportCode());
			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			TimeZone timezone = sd.getTimeZone();
			mailbagSecurityModel.setTimeZone(timezone.getID());
			Collection<ConsignmentScreeningDetail> consignmentScreeningDetail = MailOperationsModelConverter
					.constructMailbagSecurityDetails(mailbagVO.getConsignmentScreeningVO());
			mailbagSecurityModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
			ArrayList<MailbagSecurityModel> result = new ArrayList<>();
			mailbagSecurityModel.setConsignmentScreeningDetail(consignmentScreeningDetail);
			result.add(mailbagSecurityModel);
			responseVO.setResults(result);
			responseVO.setStatus("success");
			actionContext.setResponseVO(responseVO);
		} else {
			mailbagSecurityModel.setAirportCode(logonAttributes.getAirportCode());
			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
			TimeZone timezone = sd.getTimeZone();
			mailbagSecurityModel.setTimeZone(timezone.getID());
			mailbagSecurityModel.setOneTimeValues(MailOperationsModelConverter.constructOneTimeValues(oneTimeValues));
			ArrayList<MailbagSecurityModel> result = new ArrayList<>();
			result.add(mailbagSecurityModel);
			responseVO.setResults(result);
			actionContext.setResponseVO(responseVO);
			if(!mailbagSecurityModel.isWarningFlag()){
			ErrorVO error = new ErrorVO(NO_MAILBAG);
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			actionContext.setResponseVO(responseVO);
			 actionContext.addError(error);
			}

		}

	}
	
	private Collection<String> getOneTimeParameterTypes() {
	List<String> parameterTypes = new ArrayList<>();
		parameterTypes.add("mail.operations.screeningmethod");
		parameterTypes.add("mail.operations.consignorstatuscode");
		parameterTypes.add("mail.operations.results"); 
		parameterTypes.add("mail.operations.securitystatuscode");

		return parameterTypes;
	}

}
