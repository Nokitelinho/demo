/*
 * AcknowledgeCommand.java Created on 09, Jul 2009
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227
 *
 */
public class AcknowledgeCommand  extends BaseCommand{
	
	private Log log = LogFactory.getLogger("UPLOAD MAIL DETAILS");
	
	private static final String TARGET_SUCCESS = "acknowledge_exception_success";	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";
	/**
	 * execute the BaseCommand
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException {
		log.entering("AcknowledgeCommand", "execute");
		UploadMailDetailsForm uploadMailDetailsForm = 
			(UploadMailDetailsForm)invocationContext.screenModel;
		UploadMailDetailsSession uploadMailDetailsSession = 
			(UploadMailDetailsSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String [] selectedRows = uploadMailDetailsForm.getSelectedExceptions();
		ScannedMailDetailsVO selectedScannedSessionVO = uploadMailDetailsSession.getSelectedScannedMailDetailsVO();
		Collection<MailbagVO> exceptionMails = null;
		Collection<MailbagVO> acknowledgedMails = new ArrayList<MailbagVO>();
		String processPoint = null;
		if(selectedScannedSessionVO != null){
			processPoint = selectedScannedSessionVO.getProcessPoint();
			exceptionMails = (ArrayList<MailbagVO>)selectedScannedSessionVO.getErrorMailDetails();
			if(selectedRows != null && selectedRows.length >0){
				for (int i=0;i<selectedRows.length;i++){
					MailbagVO acknowledgedMailBagVO = 
						((ArrayList<MailbagVO>)exceptionMails).get(Integer.parseInt(selectedRows[i]));
					acknowledgedMails.add(acknowledgedMailBagVO);
				}
			}
		}
		if(acknowledgedMails.size() > 0){
			for(MailbagVO acknowledgedVO : acknowledgedMails){ 
				if(MailConstantsVO.EXCEPT_FATAL.equals(acknowledgedVO.getErrorType())){
					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.batchmailupload.msg.err.fatalerror");
					invocationContext.addError(errorVO);
					return;
				}
				if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(processPoint)){
					if(MailConstantsVO.EXCEPT_WARN.equals(acknowledgedVO.getErrorType())){
						acknowledgedVO.setReassignFlag(MailConstantsVO.FLAG_YES);
						selectedScannedSessionVO.setAcknowledged(true);
						acknowledgedVO.setErrorType(null);
						acknowledgedVO.setErrorDescription(null);
					}
				}				
			}
			exceptionMails.removeAll(acknowledgedMails);
			selectedScannedSessionVO.getMailDetails().addAll(acknowledgedMails);
		}
		
		invocationContext.target = TARGET_SUCCESS;
		log.exiting("AcknowledgeCommand", "execute");
	}
}
