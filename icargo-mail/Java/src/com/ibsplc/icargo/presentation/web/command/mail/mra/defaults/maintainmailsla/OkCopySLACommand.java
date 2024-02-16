/*
 * OkCopySLACommand.java Created on April 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2524
 *
 */
public class OkCopySLACommand extends BaseCommand {
	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "OkCopySLACommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String ACTION_SUCCESS="action_success";
	private static final String OPERATION_FLAG_NOT_MODIFIED = "N";
	
	

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		MaintainMailSLASession maintainMailSLASession=getScreenSession(MODULE_NAME,SCREEN_ID);
		MaintainMailSLAForm maintainMailSLAForm=(MaintainMailSLAForm)invocationContext.screenModel;
		
		String companyCode = logonAttributes.getCompanyCode();
		Collection<MailSLADetailsVO> detailsVosFromSession=null;		
		
		String selectedChildRow = maintainMailSLAForm.getCheckIdChild()[0];
		int index = Integer.parseInt(selectedChildRow);
		log.log(Log.INFO, " Selected Row is-----> ", index);
		String slaIdFromPopup = maintainMailSLAForm.getSlaID()[index];
		log.log(Log.INFO, "\n slaIdFromPopup--------->", slaIdFromPopup);
		MailSLAVO mailSLAVo  = null;
		MailTrackingMRADelegate delegate =  new MailTrackingMRADelegate();
				
		try{
			mailSLAVo = delegate.findMailSla(companyCode,slaIdFromPopup);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
		}
			
		if(mailSLAVo != null){			
			Collection<MailSLADetailsVO> detailsVoFromServer = mailSLAVo.getMailSLADetailsVos();
			mailSLAVo.setOperationFlag(MailSLAVO.OPERATION_FLAG_UPDATE);
			for(MailSLADetailsVO details : detailsVoFromServer){
				details.setOperationFlag(OPERATION_FLAG_NOT_MODIFIED);
			}
			detailsVosFromSession = maintainMailSLASession.getMailSLADetailsVOs();
			detailsVoFromServer.addAll(detailsVosFromSession);
			mailSLAVo.setMailSLADetailsVos(detailsVoFromServer);			
			maintainMailSLASession.setMailSLAVo(mailSLAVo);
			log.log(Log.INFO, "\n Final VO after copy--------->", mailSLAVo);
			
		}
		maintainMailSLAForm.setReloadParent(Boolean.TRUE.toString());
		invocationContext.target=ACTION_SUCCESS;
		log.exiting(CLASS_NAME,"execute");


	}
	

	
	
}
