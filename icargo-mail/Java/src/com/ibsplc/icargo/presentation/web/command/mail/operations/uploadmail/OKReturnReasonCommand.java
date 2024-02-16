/*
 * OKReturnReasonCommand.java Created on Oct 08, 2005
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

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
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
 * @author A-1876
 *
 */
public class OKReturnReasonCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	private static final String REASONCODE = "mailtracking.defaults.return.reasoncode";
	
		
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
		
		String returns = uploadMailForm.getReturnDetails();
		log.log(Log.INFO, "returns....\n", returns);
		Collection<ScannedMailDetailsVO> scannedReturnVOs = scannedDetailsVO.getReturnedMails();
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		
		String[] primKeyArr = returns.split(",");
		log.log(Log.INFO,"primKeyArr....\n");
		scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedReturnVOs).get(0);
		Collection<MailbagVO> mailbagReturnVOs = scannedMailDetailsVO.getMailDetails();
		log.log(Log.INFO,"mailbagReturnVOs....\n");
		MailbagVO mailbagVO = new MailbagVO();
		DamagedMailbagVO dmgMailbagVO = new DamagedMailbagVO();
		Collection<DamagedMailbagVO> damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		for(int cnt=0;cnt<primKeyArr.length;cnt++){
			int bag = Integer.parseInt(primKeyArr[cnt]);
			mailbagVO = ((ArrayList<MailbagVO>)mailbagReturnVOs).get(bag-1);
			damagedMailbagVOs = mailbagVO.getDamagedMailbags();
			dmgMailbagVO = ((ArrayList<DamagedMailbagVO>)damagedMailbagVOs).get(0);
			dmgMailbagVO.setDamageCode(uploadMailForm.getReturnCode());
//			added by anitha for setting description-START
			Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
			Collection<OneTimeVO> dmgVOs = new ArrayList<OneTimeVO>();
			   if(oneTimes!=null){
				   dmgVOs = oneTimes.get("mailtracking.defaults.return.reasoncode");
			   }
			   for(OneTimeVO dmgVO:dmgVOs){
				   if(dmgVO.getFieldValue().equals(dmgMailbagVO.getDamageCode())){
					   dmgMailbagVO.setDamageDescription(dmgVO.getFieldDescription());
				   }
			   }
//			 added by anitha for setting description-END
			dmgMailbagVO.setRemarks(uploadMailForm.getRemarks());
		}
		
	    log.log(Log.INFO, "scannedDetailsVO....after server.\n",
				scannedDetailsVO);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		uploadMailForm.setReturnFlag("CLOSE");
		
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
