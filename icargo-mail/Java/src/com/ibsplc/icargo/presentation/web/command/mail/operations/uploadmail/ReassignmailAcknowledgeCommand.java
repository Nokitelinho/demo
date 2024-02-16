/*
 * ReassignmailAcknowledgeCommand.java Created on Oct 08, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 *
 */
public class ReassignmailAcknowledgeCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("ReassignmailAcknowledgeCommand","execute");
		UploadMailForm uploadMailForm 
			= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		
	
    	
    	/*ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();*/
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		/**
		 * Validation 1
		 * Whether a container is assigned or not
		 */
		Collection<ScannedMailDetailsVO> scannedReassignmailsVOs = scannedDetailsVO.getReassignMails();
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		
		String[] primKeyArr = uploadMailForm.getSelectReassignmail();
		int scannedVOSize= scannedReassignmailsVOs.size();
		for(int i=0;i<scannedVOSize;i++){
		scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedReassignmailsVOs).get(i);
		Collection<MailbagVO> mailbagReassignmailVOs = scannedMailDetailsVO.getMailDetails();
		
		MailbagVO mailbagVO = new MailbagVO();
		for(int cnt=0;cnt<primKeyArr.length;cnt++){
			int bag = Integer.parseInt(primKeyArr[cnt]);
			mailbagVO = ((ArrayList<MailbagVO>)mailbagReassignmailVOs).get(bag-1);
			if(MailConstantsVO.EXCEPT_FATAL.equals(mailbagVO.getErrorType())){
				mailbagVO.setAcknowledge("Y");
			}
		}
		
		
		}
		
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		invocationContext.target = TARGET;
		log.exiting("ReassignmailAcknowledgeCommand","execute");
		
	}
	
}
