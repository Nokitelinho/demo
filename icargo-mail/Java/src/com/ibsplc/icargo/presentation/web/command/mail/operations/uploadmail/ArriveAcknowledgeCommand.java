/*
 * ArriveAcknowledgeCommand.java Created on Oct 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.UploadMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.UploadMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1876
 *
 */
public class ArriveAcknowledgeCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	
		
	/** 
	 * The execute method for ScreenLoadCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("ArriveAcknowledgeCommand","execute");
		UploadMailForm uploadMailForm 
					= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		Collection<ScannedMailDetailsVO> scannedArriveVOs = scannedDetailsVO.getArrivedMails();
		ScannedMailDetailsVO scannedMailDetailsVO = ((ArrayList<ScannedMailDetailsVO>)scannedArriveVOs).get(0);
		Collection<MailbagVO> mailbagArriveVOs = scannedMailDetailsVO.getMailDetails();
		
		String[] primKeyArr = uploadMailForm.getSelectArrive();
				
		MailbagVO mailbagVO = new MailbagVO();
		for(int cnt=0;cnt<primKeyArr.length;cnt++){
			int bag = Integer.parseInt(primKeyArr[cnt]);
			mailbagVO = ((ArrayList<MailbagVO>)mailbagArriveVOs).get(bag-1);
	       	if("EXPWARN".equals(mailbagVO.getErrorType())){
	       		mailbagVO.setAcknowledge("Y");
			}
	    }
		log.log(Log.INFO, "mailbagArriveVOs...for..ack.\n", mailbagArriveVOs);
		uploadMailSession.setScannedDetailsVO(scannedDetailsVO);
		
		invocationContext.target = TARGET;
		log.exiting("ArriveAcknowledgeCommand","execute");
		
	}
	
}
