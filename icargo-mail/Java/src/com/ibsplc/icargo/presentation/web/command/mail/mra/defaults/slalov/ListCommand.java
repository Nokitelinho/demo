/*
 * ListCommand.java Created on Dec 1, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.slalov;



import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLAFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.SLALovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String LIST_SUCCESS = "list_success";
		
	private static final String CLASS_NAME = "ListCommand";
	
	private static final String BLANK = "";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.entering(CLASS_NAME, "execute");
		SLALovForm slaLovForm = (SLALovForm)invocationContext.screenModel;
		
		Collection<SLADetailsVO> slaDetailsVOs = null;
		
		String slaID = slaLovForm.getCode();
		SLAFilterVO slaFilterVO = new SLAFilterVO();
		
		slaFilterVO.setCompanyCode(getApplicationSession().getLogonVO().getCompanyCode());
		
		if(!BLANK.equals(slaID)){
			slaFilterVO.setSlaID(slaID);
		}
		
		
		try {			 
			slaDetailsVOs = new MailTrackingMRADelegate().displaySLADetails(slaFilterVO);
			
		} catch(BusinessDelegateException businessDelegateException) {			
			
			businessDelegateException.getMessage();
			handleDelegateException(businessDelegateException);
		}
		
		slaLovForm.setSlaDetailsVOs(slaDetailsVOs);
		log.exiting(CLASS_NAME,"execute");
    	invocationContext.target = LIST_SUCCESS;

	}

}
