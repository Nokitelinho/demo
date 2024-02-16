/*
 * ListDamageDetailsCommand.java Created on July 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbagenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DamagedMailBagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DamagedMailBagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListDamageDetailsCommand extends BaseCommand {

	private static final String SUCCESS = "list_success";
	
	private Log log = LogFactory.getLogger("ListDamageDetailsCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.damagedmailbag";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the list command----------> \n\n");
    	
    	DamagedMailBagForm damagedMailBagForm =
							(DamagedMailBagForm)invocationContext.screenModel;
    	DamagedMailBagSession damagedMailBagSession = 
    									getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();
	    Collection<ErrorVO> errors = null;

	    MailTrackingDefaultsDelegate delegate = new MailTrackingDefaultsDelegate();
	    
	    String mailBagId = damagedMailBagSession.getMailBagId().toUpperCase();
	    
	    Collection<DamagedMailbagVO> damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
	    
	    try{
	    	damagedMailbagVOs = delegate.findMailbagDamages(companyCode,mailBagId);
	    }catch(BusinessDelegateException businessDelegateException){
	    	errors = handleDelegateException(businessDelegateException);
	    }
	    
	    log.log(Log.FINE, "\n\n damagedMailbagVOs---------->  ",
				damagedMailbagVOs);
		damagedMailBagForm.setMailbagId(mailBagId);
	    
	    damagedMailBagSession.setDamagedMailbagVOs(damagedMailbagVOs);
		
	    damagedMailBagForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = SUCCESS;
	}
}
