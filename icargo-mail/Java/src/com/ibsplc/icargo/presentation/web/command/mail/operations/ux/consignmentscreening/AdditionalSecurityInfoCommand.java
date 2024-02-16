/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening.SaveScreeningDetailsCommand.java;;
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	18-July-2022	:	Draft
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.consignmentscreening;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentScreeningModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetails;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;


public class AdditionalSecurityInfoCommand extends AbstractCommand{
	private static final String COMMAND_NAME = "AdditionalSecurityInfoCommand";
	 private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	 private static final String STATUS_SUCCESS = "STATUS_SUCCESS";
	 private static final String STATUS_FAIL = "STATUS_FAIL";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		LOGGER.info("entering into" + COMMAND_NAME + "class");     
		String response ="";
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		Collection<ErrorVO> errors = new ArrayList<>();
		ConsignmentScreeningModel consignmentScreeningModel = (ConsignmentScreeningModel)actionContext.getScreenModel();
		ScreeningDetails screeningDetails= consignmentScreeningModel.getScreeningDetails();
		ConsignmentDocumentVO consignmentDocumentVO =new ConsignmentDocumentVO();
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();

		consignmentDocumentVO.setCompanyCode(logonAttributes.getCompanyCode()); 
		consignmentDocumentVO.setConsignmentNumber(screeningDetails.getConsignmentNumber());
		consignmentDocumentVO.setConsignmentSequenceNumber(Integer.parseInt(screeningDetails.getConsignmentSequenceNumber()));
		consignmentDocumentVO.setPaCode(screeningDetails.getPaCode());
		consignmentDocumentVO.setAdditionalSecurityInfo(screeningDetails.getMstAddionalSecurityInfo());
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

	}
	
	}
		

