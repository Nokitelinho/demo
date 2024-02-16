package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;



import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;

public class SaveSecurityStatusCommand extends AbstractCommand {

	private static final String COMMAND_NAME = "SaveSecurityStatusCode";
	private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.info("entering into" + COMMAND_NAME + "class");
		
		ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();

		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		ConsignmentDocumentVO consignmentDocumentVO =new ConsignmentDocumentVO();
		
		consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode()); 
		consignmentDocumentVO.setConsignmentNumber(consignmentScreeningModel.getConsignmentNumber());
		consignmentDocumentVO.setConsignmentSequenceNumber(consignmentScreeningModel.getConsignmentSequenceNumber());
		consignmentDocumentVO.setPaCode(consignmentScreeningModel.getPaCode());
		consignmentDocumentVO.setSecurityStatusCode(consignmentScreeningModel.getSecurityStatusCode());

		try {
			delegate.saveConsignmentDetailsMaster( consignmentDocumentVO);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
		responseVO.setStatus("STATUS_SUCCESS");
		actionContext.setResponseVO(responseVO);  
	}
}








