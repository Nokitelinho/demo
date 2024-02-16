/*
 * ViewExceptionCommand Created on NOV 26, 2006
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmaildetails;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailDetailsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3227
 *
 */
public class ViewExceptionCommand  extends BaseCommand {
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.batchmailupload";
	private static final String VIEW_EXCEPTION_SUCCESS = "view_exception_success";

	public void execute(InvocationContext invocationContext) 
	throws CommandInvocationException {		
		log.entering("ViewExceptionCommand","execute");
		UploadMailDetailsForm uploadMailDetailsForm = 
			(UploadMailDetailsForm)invocationContext.screenModel;
		UploadMailDetailsSession uploadMailDetailsSession = 
			(UploadMailDetailsSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		//LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String selectedRow = uploadMailDetailsForm.getSelectedScannedVOIndx();
		Collection<ScannedMailDetailsVO> scannedMailDetailsVOs = uploadMailDetailsSession.getScannedMailDetailsVOs();
		ScannedMailDetailsVO selectedSession = null;
		if(selectedRow != null && selectedRow.trim().length() > 0 && 
				scannedMailDetailsVOs != null && scannedMailDetailsVOs.size() > 0){
			selectedSession = ((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOs).get(Integer.parseInt(selectedRow));
			uploadMailDetailsSession.setSelectedScannedMailDetailsVO(selectedSession);
			if(selectedSession != null && selectedSession.getExceptionBagCout() > 0){
				uploadMailDetailsSession.setMailExceptionDetails(selectedSession.getErrorMailDetails());
			}
		}
		invocationContext.target = VIEW_EXCEPTION_SUCCESS;
		log.exiting("ViewExceptionCommand","execute");
	}
}
