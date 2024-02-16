package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;

import com.ibsplc.icargo.framework.web.command.CommandInvocationException;

import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;

import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignorDetailpopup;

import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ScreeningDetailpopup;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;

import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10383	:	28-Apr-2022		:	Draft
 */
public class DeleteCommand extends AbstractCommand {

	private static final String COMMAND_NAME = "DeleteCommand";
	 private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	 private static final String SCREEN_ID ="MTK073";
	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
	
		LOGGER.info("entering into" + COMMAND_NAME + "class");     
		MailbagSecurityModel mailbagSecurityModel = (MailbagSecurityModel) actionContext.getScreenModel();
		Collection<ConsignmentScreeningVO> consignmentScreeningVO =new ArrayList<>();
		LogonAttributes logonAttributes = getLogonAttribute();
				
		ScreeningDetailpopup screeningDetailpopup =  mailbagSecurityModel.getScreeningDetailpopup();
		ConsignorDetailpopup consignorDetailpopup = mailbagSecurityModel.getConsignorDetailpopup();

		ConsignmentScreeningVO consignmentScreeningvo =new ConsignmentScreeningVO();
		consignmentScreeningvo.setCompanyCode(logonAttributes.getCompanyCode()); 
		consignmentScreeningvo.setSource(SCREEN_ID); 
		
		MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
		long sernum = Long.parseLong((mailbagSecurityModel.getScreeningDetailpopup()!=null)?screeningDetailpopup.getSernum():consignorDetailpopup.getSernum());

		consignmentScreeningvo.setSerialNumber(sernum);
		consignmentScreeningVO.add(consignmentScreeningvo);

		try {
			delegate.deletescreeningDetails( consignmentScreeningVO);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
				businessDelegateException.getMessage();
			}
	}
}