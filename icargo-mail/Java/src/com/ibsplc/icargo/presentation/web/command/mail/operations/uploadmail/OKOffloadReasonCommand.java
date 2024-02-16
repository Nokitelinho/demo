/*
 * OKOffloadReasonCommand.java Created on Sept 22, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2107
 *
 */
public class OKOffloadReasonCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	private static final String REASONCODE = "mailtracking.defaults.offload.reasoncode";
	
	private static final String CLOSE = "CLOSE";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("UploadCommand","execute");
		UploadMailForm uploadMailForm 
		= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ScannedDetailsVO scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		String offload = uploadMailForm.getOffloadDetails();
		
		Collection<ScannedMailDetailsVO> scannedOffloadVOs = scannedDetailsVO.getOffloadMails();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		
		String[] primKeyArr = offload.split(",");
		scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedOffloadVOs).get(0);
		Collection<MailbagVO> mailbagOffloadVOs = scannedMailDetailsVO.getMailDetails();
		MailbagVO mailbagVO = new MailbagVO();
		for(int cnt=0;cnt<primKeyArr.length;cnt++){
			int bag = Integer.parseInt(primKeyArr[cnt]);
			mailbagVO = ((ArrayList<MailbagVO>)mailbagOffloadVOs).get(bag-1);
			mailbagVO.setOffloadedReason(uploadMailForm.getOffloadCode());
			Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
			Collection<OneTimeVO> offloadVOs = new ArrayList<OneTimeVO>();
			   if(oneTimes!=null){
				   offloadVOs = oneTimes.get("mailtracking.defaults.offload.reasoncode");
			   }
			   for(OneTimeVO offloadVO:offloadVOs){
				   if(offloadVO.getFieldValue().equals(mailbagVO.getOffloadedReason())){
					   mailbagVO.setOffloadedDescription(offloadVO.getFieldDescription());
				   }
			   }
			   mailbagVO.setOffloadedRemarks(uploadMailForm.getOffloadRemarks());
		}
		
	   uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		uploadMailForm.setOffloadFlag(CLOSE);
		
		invocationContext.target = TARGET;
		log.exiting("UploadCommand","execute");
		
	}
	
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();
			fieldValues.add(REASONCODE);
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;
		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}
	

}
