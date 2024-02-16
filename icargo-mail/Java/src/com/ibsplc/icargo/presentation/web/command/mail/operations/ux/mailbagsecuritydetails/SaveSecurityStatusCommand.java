package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;

public class SaveSecurityStatusCommand extends AbstractCommand {

	private static final String COMMAND_NAME = "SaveSecurityStatusCode";
	private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.info("entering into" + COMMAND_NAME + "class");
		MailbagSecurityModel mailbagSecurityModel = (MailbagSecurityModel) actionContext.getScreenModel();
		MailbagVO mailbagVO = new MailbagVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		mailbagVO.setMailbagId(mailbagSecurityModel.getMailbagId());
		mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailbagVO.setMailSequenceNumber(mailbagSecurityModel.getMalseqnum());
		mailbagVO.setSecurityStatusCode(mailbagSecurityModel.getSecurityStatusCode());

		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		try {
			delegate.saveMailSecurityStatus(mailbagVO);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		ResponseVO responseVO = new ResponseVO(); 
		responseVO.setStatus("success");
		actionContext.setResponseVO(responseVO);  

	}

}
