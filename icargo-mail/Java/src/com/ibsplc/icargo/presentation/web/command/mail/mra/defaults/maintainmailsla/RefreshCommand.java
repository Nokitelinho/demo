/*
 * RefreshCommand.java Created on April 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;


import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-2524
 *
 */
public class RefreshCommand extends BaseCommand {

		/** Target constants */

	private static final String CLASS_NAME = "RefreshCommand";
	private static final String ACTION_SUCCESS = "action_success";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String NEW_SLA_OPTION_FLAG = "N";
	private static final String LINK_STATUS = "N";
	private static final String CALLPOPUP_STATUS = "N";
	
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");

	/**
	 * Method to execute the command
	 *
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		maintainMailSLAForm.setNewSLAOptionFlag(NEW_SLA_OPTION_FLAG);
		maintainMailSLAForm.setCallPopup(CALLPOPUP_STATUS);
		
		MailSLAVO mailSLAVo  = maintainMailSLASession.getMailSLAVo();
		maintainMailSLAForm.setSlaId(mailSLAVo.getSlaId());
		maintainMailSLAForm.setDescription(mailSLAVo.getDescription());
		maintainMailSLAForm.setCurrency(mailSLAVo.getCurrency());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");


    }
}

