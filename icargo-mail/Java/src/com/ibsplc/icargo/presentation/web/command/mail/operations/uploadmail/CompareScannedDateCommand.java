/*
 * CompareScannedDateCommand.java Created on Nov 03, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.uploadmail;

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
 * @author A-2107
 *
 */
public class CompareScannedDateCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	/*
	 * The Module Name
	 */
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.uploadmail";	
	
	private static final String TARGET = "success";
	
		
	/** 
	 * The execute method for CheckScannedIdentifierCommand
	 * (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
				
		log.entering("CheckScannedIdentifierCommand","execute");
		UploadMailForm uploadMailForm 
					= (UploadMailForm)invocationContext.screenModel;
		UploadMailSession uploadMailSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		
		log.log(Log.INFO, " uploadMailForm..\n", uploadMailForm.getSavemode());
		ScannedDetailsVO  scannedDetailsVO = uploadMailSession.getScannedDetailsVO();
		
		ScannedDetailsVO fromscannedDetailsVO = checkscannedDetailsVO(scannedDetailsVO,uploadMailForm.getSavemode());
		
		 uploadMailSession.setScannedDetailsVO(fromscannedDetailsVO);
		
		log.log(Log.INFO, " scannedDetailsVO..\n", fromscannedDetailsVO);
		invocationContext.target = TARGET;
		log.exiting("CheckScannedIdentifierCommand","execute");
		
	}
	
	/**
	 * This function checks whether the ScannedMailDetailsVO arrived for save have
	 * mailbagvo with scanned date greater that any other same mailbagvo in whole ScannedDetailsVO collection
	 * For Save all this function is called from Corresponding savecommand.For that a boolean Enablemode is set.
	 * For Individual save in command.xml it is specified as order = 1
	 * @param scannedDetailsVO
	 * @param savemode
	 * @return
	 */
	
	public static ScannedDetailsVO checkscannedDetailsVO (ScannedDetailsVO scannedDetailsVO,String savemode){
				Collection<ScannedMailDetailsVO> firstscannedVOs = null;
		
		if("ACP".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getOutboundMails();
		}else if("ARR".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getArrivedMails();
		}else if("RET".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getReturnedMails();
		}else if("RSGM".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getReassignMails();
		}else if("TRA".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getTransferMails();
		}else if("OFL".equalsIgnoreCase(savemode.trim())){
			firstscannedVOs = scannedDetailsVO.getOffloadMails();
		}
		if(firstscannedVOs != null && firstscannedVOs.size() > 0) {
			for(ScannedMailDetailsVO firstscannedvo:firstscannedVOs){
				Collection<MailbagVO> firstmailbagvos = firstscannedvo.getMailDetails();
				for(MailbagVO firstmailbagvo:firstmailbagvos){
					if(!"ACP".equalsIgnoreCase(firstmailbagvo.getActionMode().trim()) ){
						Collection<ScannedMailDetailsVO> acceptscannedvos = scannedDetailsVO.getOutboundMails();
						if(acceptscannedvos != null && acceptscannedvos.size() >0 ){
							for(ScannedMailDetailsVO acpscannedvo:acceptscannedvos){
								Collection<MailbagVO> acceptmailbagvos = acpscannedvo.getMailDetails();
								for(MailbagVO acceptmailbagvo:acceptmailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(acceptmailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(acceptmailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in accepted status");
										}
									}
								}
							}
						}
					}

					if(!"ARR".equalsIgnoreCase(firstmailbagvo.getActionMode().trim())){
						Collection<ScannedMailDetailsVO> arrivescannedvos = scannedDetailsVO.getArrivedMails();
						if(arrivescannedvos != null && arrivescannedvos.size() >0 ){
							for(ScannedMailDetailsVO arrscannedvo:arrivescannedvos){
								Collection<MailbagVO> arrmailbagvos = arrscannedvo.getMailDetails();
								for(MailbagVO arrmailbagvo:arrmailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(arrmailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(arrmailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in arrival status");
										}
									}
								}
							}
						}
					}

					if(!"RET".equalsIgnoreCase(firstmailbagvo.getActionMode().trim())){
						Collection<ScannedMailDetailsVO> retscannedvos = scannedDetailsVO.getReturnedMails();
						if(retscannedvos != null && retscannedvos.size() >0 ){
							for(ScannedMailDetailsVO retscannedvo:retscannedvos){
								Collection<MailbagVO> retmailbagvos = retscannedvo.getMailDetails();
								for(MailbagVO retmailbagvo:retmailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(retmailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(retmailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in return status");
										}
									}
								}
							}
						}
					}

					if(!"RSGM".equalsIgnoreCase(firstmailbagvo.getActionMode().trim())){
						Collection<ScannedMailDetailsVO> rsgmscannedvos = scannedDetailsVO.getReassignMails();
						if(rsgmscannedvos != null && rsgmscannedvos.size() >0 ){
							for(ScannedMailDetailsVO rsgmscannedvo:rsgmscannedvos){
								Collection<MailbagVO> rsgmmailbagvos = rsgmscannedvo.getMailDetails();
								for(MailbagVO rsgmmailbagvo:rsgmmailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(rsgmmailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(rsgmmailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in reassign status");
										}
									}
								}
							}
						}
					}

					if(!"TRA".equalsIgnoreCase(firstmailbagvo.getActionMode().trim())){
						Collection<ScannedMailDetailsVO> trascannedvos = scannedDetailsVO.getTransferMails();
						if(trascannedvos != null && trascannedvos.size() >0 ){
							for(ScannedMailDetailsVO trascannedvo:trascannedvos){
								Collection<MailbagVO> tramailbagvos = trascannedvo.getMailDetails();
								for(MailbagVO tramailbagvo:tramailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(tramailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(tramailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in transfer status");
										}
									}
								}
							}
						}
					}

					if(!"OFL".equalsIgnoreCase(firstmailbagvo.getActionMode().trim())){
						Collection<ScannedMailDetailsVO> offscannedvos = scannedDetailsVO.getOffloadMails();
						if(offscannedvos != null && offscannedvos.size() >0 ){
							for(ScannedMailDetailsVO offscannedvo:offscannedvos){
								Collection<MailbagVO> offmailbagvos = offscannedvo.getMailDetails();
								for(MailbagVO offmailbagvo:offmailbagvos){
									if(firstmailbagvo.getMailbagId().equalsIgnoreCase(offmailbagvo.getMailbagId())){
										if(firstmailbagvo.getScannedDate().isGreaterThan(offmailbagvo.getScannedDate())){
											firstmailbagvo.setErrorType("EXPERR");
											firstmailbagvo.setErrorDescription("Mailbag already exist in offload status");
										}
									}
								}
							}
						}
					}
				}
			}
		}
		
		return scannedDetailsVO;
	}
	
}
