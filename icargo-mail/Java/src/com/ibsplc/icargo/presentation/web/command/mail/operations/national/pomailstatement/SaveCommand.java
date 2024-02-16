/*
 * SaveCommand.java Created on jul 06 , 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.pomailstatement;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.national.POMailStatementVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.national.POMailStatementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.POMailStatementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5137
 *
 */
public class SaveCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING");



	private static final String MODULE_NAME = "mail.operations";	

	private static final String SCREEN_ID = "mailtracking.defaults.national.mailstatement";	
	private static final String SAVE_SUCCESS = "save_success"; 	  
	private static final String SCREENLOAD_FAILURE = "screenload_failure";
	private static final String MAIL_POSTAL_CODE ="mailtracking.defaults.national.postalcode";

	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		POMailStatementForm poMailStatementForm =(POMailStatementForm)invocationContext.screenModel;
		POMailStatementSession poMailStatementSession = getScreenSession(MODULE_NAME,SCREEN_ID);
		poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();		
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Page<POMailStatementVO> pOMailStatementVOs = null; 			
		pOMailStatementVOs = poMailStatementSession.getSelectedPOMailStatementVOs();		
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			log.log(Log.FINE, "Saving Summary For ------------> ",
					pOMailStatementVOs);
				mailTrackingDefaultsDelegate.saveRemarksFromPOMailStatement(updateSessionVoWithFormValues(pOMailStatementVOs,poMailStatementForm));

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		poMailStatementForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL); 
		invocationContext.target = SAVE_SUCCESS;
		
		
		
		
	}
	private Collection<POMailStatementVO> updateSessionVoWithFormValues(Page<POMailStatementVO> pOMailStatementVOs,POMailStatementForm poMailStatementForm) {
		int i =0;
		for(POMailStatementVO pOMailStatementVO : pOMailStatementVOs) {			
			pOMailStatementVO.setRemarks(poMailStatementForm.getRemarks()[i]);
			i++;
		}
		Collection<POMailStatementVO> pOMailStatementVOsCol = new ArrayList<POMailStatementVO>();
		pOMailStatementVOsCol.addAll(pOMailStatementVOs);
		return pOMailStatementVOsCol;
	}
}
