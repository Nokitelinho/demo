/*
 * ListMailSubClassCommand.java Created on June 07, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailSubClassVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailSubClassMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailSubClassForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class ListMailSubClassCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListMailSubClassCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.masters.subclass";
	
	private static final String NO_MATCH = 
					"mailtracking.defaults.mailsubclassmaster.msg.err.noMatch";
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	MailSubClassForm mailSubClassForm =
				(MailSubClassForm)invocationContext.screenModel;
    	MailSubClassMasterSession subClassSession = 
    			getScreenSession(MODULE_NAME,SCREEN_ID);
    	
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    
	    String subClassCode = "";
	    if(!("").equals(mailSubClassForm.getSubClassFilter())) {
	    	subClassCode = mailSubClassForm.getSubClassFilter().toUpperCase();
	    }
	    
	    Collection<MailSubClassVO> mailSubClassVOs = null;
	    try {
	    	mailSubClassVOs = new MailTrackingDefaultsDelegate().findMailSubClassCodes(
	    			logonAttributes.getCompanyCode(),subClassCode);
	    	log
					.log(Log.FINE, "\n\n mailSubClassVOs=========>",
							mailSubClassVOs);
			if(mailSubClassVOs == null || mailSubClassVOs.size()==0) {
	    		log.log(Log.SEVERE,"\n\n *******no match found********** \n\n");
				invocationContext.addError(new ErrorVO(NO_MATCH));
				subClassSession.setMailSubClassVOs(null);
		    	mailSubClassForm.setScreenStatusFlag
		    	 		(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		    	invocationContext.target = FAILURE;
		    	return;
	    	}
	    }catch(BusinessDelegateException businessDelegateException) {
	    	handleDelegateException(businessDelegateException);
	    }
	    
	    subClassSession.setMailSubClassVOs(mailSubClassVOs);
	    mailSubClassForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    invocationContext.target = SUCCESS;

	}

}
