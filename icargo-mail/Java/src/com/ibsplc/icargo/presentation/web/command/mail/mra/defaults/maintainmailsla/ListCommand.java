/*
 * ListCommand.java Created on Mar 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainmailsla;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLADetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailSLAVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMailSLASession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMailSLAForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2524
 *
 */
public class ListCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS MAINTAINMAILSLA");
	private static final String CLASS_NAME = "ListCommand";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainmailsla";
	private static final String LIST_SUCCESS="list_success";
	private static final String OPERATION_FLAG_NOT_MODIFIED = "N";
	private static final String NEW_SLA_OPTION_FLAG = "Y";
//	private static final String NO_RECORDS_FOUND =
//		"mailtracking.mra.defaults.maintainmailsla.err.norecordsfound";
	private static final String LINK_STATUS = "N";
	
	private static final String MAIL_CATEGORY = "mailtracking.defaults.mailcategory";
	
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
		
		if(maintainMailSLASession.getOneTimeValues() == null || 
				maintainMailSLASession.getOneTimeValues().size() ==0){
			getOneTimeValues(maintainMailSLASession,logonAttributes);
		}
		
		String companyCode = logonAttributes.getCompanyCode();
		log.log(Log.INFO, "\n companyCode--------->", companyCode);
		String slaId = maintainMailSLAForm.getSlaId();
		log.log(Log.INFO, "\n slaId--------->", slaId);
		MailTrackingMRADelegate delegate =  new MailTrackingMRADelegate();
		
		MailSLAVO mailSLAVo = null;
		try{
			mailSLAVo = delegate.findMailSla(companyCode,slaId);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
		}
		
		if(mailSLAVo != null){
			mailSLAVo.setOperationFlag(OPERATION_FLAG_NOT_MODIFIED);
			maintainMailSLAForm.setCurrency(mailSLAVo.getCurrency());
			maintainMailSLAForm.setDescription(mailSLAVo.getDescription());
			
			for(MailSLADetailsVO detailsVo : mailSLAVo.getMailSLADetailsVos()){
				detailsVo.setOperationFlag(OPERATION_FLAG_NOT_MODIFIED);
			}
			log.log(Log.INFO, "\n <------ Records From Server------------>",
					mailSLAVo);
			maintainMailSLASession.setMailSLAVo(mailSLAVo);
		}
		else{
			log.log(Log.INFO,"\n <------no Records Found------------>");
			//ErrorVO error =  new ErrorVO(NO_RECORDS_FOUND);
			//error.setErrorDisplayType(ErrorDisplayType.ERROR);	
			maintainMailSLAForm.setNewSLAOptionFlag(NEW_SLA_OPTION_FLAG);
			//invocationContext.addError(error);	
			maintainMailSLASession.setMailSLAVo(null);
		}
		maintainMailSLAForm.setLinkStatus(LINK_STATUS);
		invocationContext.target=LIST_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}
	/**
	 * 
	 * @param maintainMailSLASession
	 * @param logonAttributes
	 */
	 
	public void getOneTimeValues(MaintainMailSLASession maintainMailSLASession,
			LogonAttributes logonAttributes){
		log.entering(CLASS_NAME,"getOneTimeValues");
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String,Collection<OneTimeVO>> hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(MAIL_CATEGORY);
			
		try {
			log.log(Log.FINEST,"***********************************inside try");
			hashMap = sharedDefaultsDelegate.findOneTimeValues(logonAttributes.getCompanyCode(),oneTimeList);
			log.log(Log.FINEST, "hash map*****************************",
					hashMap);
			
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
			log.log(Log.SEVERE, "status fetch exception");
		}
		if(hashMap!=null){
			maintainMailSLASession.setOneTimeValues((HashMap<String,Collection<OneTimeVO>>)hashMap);
		}
		log.exiting(CLASS_NAME,"getOneTimeValues");		
		
	}


}
