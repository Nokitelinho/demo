/*
 * ScreenLoadCopySLACommand.java Created on April 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SLAFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class ScreenLoadCopySLACommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "ScreenLoadCopySLACommand";
	private static final String ACTION_SUCCESS="action_success";	

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		
		String companyCode = logonAttributes.getCompanyCode();	
		String slaid = maintainMailSLAForm.getSlaIdrForPopup();
		MailTrackingMRADelegate delegate =  new MailTrackingMRADelegate();		
		SLAFilterVO sLAFilterVo = new SLAFilterVO();
		if(slaid != null && slaid.trim().length()>0){
			sLAFilterVo.setSlaID(slaid);
		}		
		sLAFilterVo.setCompanyCode(companyCode);
		Collection<SLADetailsVO> sLADetailsVos =null;
		try{
			sLADetailsVos = delegate.displaySLADetails(sLAFilterVo);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
		}
		
		if(sLADetailsVos != null){					
			log.log(Log.INFO, "\n <------ Records From Server------------>",
					sLADetailsVos);
			maintainMailSLAForm.setSlaDetailsVos(sLADetailsVos);			
		}
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}


}
