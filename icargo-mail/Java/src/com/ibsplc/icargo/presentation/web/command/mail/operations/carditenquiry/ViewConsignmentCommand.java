/*
 * ViewConsignmentCommand.java Created on Jul 1 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.carditenquiry;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.CarditEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ViewConsignmentCommand  extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILOPERATIONS");

	/**
	 * TARGET
	 */
	private static final String TARGET = "success";

	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.carditenquiry";	
	private static final String SCREEN_FLAG="VIEW_CONSIGNMENT";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.log(Log.FINE,"****inside mail search command*************************");

		log.entering("MailSearchCommand","execute");  
		CarditEnquiryForm carditEnquiryForm = (CarditEnquiryForm)invocationContext.screenModel;
		CarditEnquirySession carditEnquirySession = getScreenSession(MODULE_NAME,SCREEN_ID);
		/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		Collection<MailbagVO> mailbagVOs = carditEnquirySession.getMailbagVOsCollection();
		String[] primaryKey = carditEnquiryForm.getSelectMail();
		int cnt=0;
		int count = 0;
		int primaryKeyLen = primaryKey.length;

		if (mailbagVOs != null && mailbagVOs.size() != 0) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				if ((cnt < primaryKeyLen) &&( String.valueOf(count)).
						equalsIgnoreCase(primaryKey[cnt].trim())) {

					carditEnquiryForm.setPaCode(mailbagVO.getPaCode());
					carditEnquiryForm.setConDocNo(mailbagVO.getConsignmentNumber());


				}
          		count++;	
			}
		}	
		
		carditEnquiryForm.setScreenFlag(SCREEN_FLAG);
		carditEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = TARGET;
		log.exiting("ScreenloadCarditEnquiryCommand","execute");
	}
}
