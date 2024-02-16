/*
 * AddMailEventCommand.java Created on July 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.PostalAdministrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.PostalAdministrationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2047
 *
 */
public class AddMailEventCommand extends BaseCommand {

	private static final String SUCCESS = "add_success";
	
	private Log log = LogFactory.getLogger("AddMailEventCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
						"mailtracking.defaults.masters.postaladministration";
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the add command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
		PostalAdministrationSession paSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ArrayList<MailEventVO> mailEventVOs = new ArrayList<MailEventVO>();
    	    	
    	String[] opFlag = paMasterForm.getOperationFlag();
		String[] rowIds = paMasterForm.getRowId();
		int index = 0;
		if(rowIds != null)	{
			if(rowIds.length == 1){
				index = Integer.parseInt(rowIds[0])+1;
			}else {
				index = opFlag.length;
			}
			
		}else {
			if(opFlag!=null){
				index = opFlag.length;
			}else{
				index=0;
			}
		}
		
		if(opFlag != null) {
			MailEventVO mailEventVO = null;
			
			String recieved = paMasterForm.getRecievedArray();
			String[] recievedArray = recieved.split(",");
			
			String uplifted = paMasterForm.getUpliftedArray();
			String[] upliftedArray = uplifted.split(",");
			
			String assigned = paMasterForm.getAssignedArray();
			String[] assignedArray = assigned.split(",");
			
			String returned = paMasterForm.getReturnedArray();
			String[] returnedArray = returned.split(",");
			
			String handedOver = paMasterForm.getHandedOverArray();
			String[] handedOverArray = handedOver.split(",");
			
			String pending = paMasterForm.getPendingArray();
			String[] pendingArray = pending.split(",");
			
			String delivered = paMasterForm.getDeliveredArray();
			String[] deliveredArray = delivered.split(",");
			
			for(int i=0;i<opFlag.length;i++) {
				mailEventVO = new MailEventVO();
				
				String catogory = "";
				String mailClas = "";
				
				if(!("").equals(paMasterForm.getMailCategory()[i])) {
					catogory = paMasterForm.getMailCategory()[i].toUpperCase();
				}
				
		//		if(("").equals(paMasterForm.getMailClass()[i])) {
					mailClas = paMasterForm.getMailClass()[i].toUpperCase();
		//		}
				
				mailEventVO.setPaCode(paMasterForm.getPaCode().toUpperCase());
				mailEventVO.setMailCategory(catogory);
				mailEventVO.setMailClass(mailClas);
				if(("true").equals(recievedArray[i])) {
					mailEventVO.setReceived(true);
				}else {
					mailEventVO.setReceived(false);
				}
				
				if(("true").equals(upliftedArray[i])) {
					mailEventVO.setUplifted(true);
				}else {
					mailEventVO.setUplifted(false);
				}
				
				if(("true").equals(assignedArray[i])) {
					mailEventVO.setAssigned(true);
				}else {
					mailEventVO.setAssigned(false);
				}
				
				if(("true").equals(returnedArray[i])) {
					mailEventVO.setReturned(true);
				}else {
					mailEventVO.setReturned(false);
				}
				
				if(("true").equals(handedOverArray[i])) {
					mailEventVO.setHandedOver(true);
				}else {
					mailEventVO.setHandedOver(false);
				}
				
				if(("true").equals(pendingArray[i])) {
					mailEventVO.setPending(true);
				}else {
					mailEventVO.setPending(false);
				}
				
				if(("true").equals(deliveredArray[i])) {
					mailEventVO.setDelivered(true);
				}else {
					mailEventVO.setDelivered(false);
				}
				mailEventVO.setCompanyCode(companyCode);
				mailEventVO.setOperationFlag
										(paMasterForm.getOperationFlag()[i]);
				mailEventVOs.add(mailEventVO);
			}
			
		}
		
		MailEventVO newMailEventVO = new MailEventVO();
		
		log
				.log(Log.FINE, "\n\n getPaCode()-------> ", paMasterForm.getPaCode());
		newMailEventVO.setPaCode(paMasterForm.getPaCode().toUpperCase());
		newMailEventVO.setMailCategory("");
		newMailEventVO.setMailClass("");
		newMailEventVO.setReceived(false);
		newMailEventVO.setUplifted(false);
		newMailEventVO.setAssigned(false);
		newMailEventVO.setReturned(false);
		newMailEventVO.setHandedOver(false);
		newMailEventVO.setPending(false);
		newMailEventVO.setDelivered(false);
		newMailEventVO.setCompanyCode(companyCode);
		newMailEventVO.setOperationFlag(OPERATION_FLAG_INSERT);
		mailEventVOs.add(index,newMailEventVO);
		
		PostalAdministrationVO paVO = new PostalAdministrationVO();
		paVO.setCompanyCode(companyCode);
		paVO.setPaCode(paMasterForm.getPaCode().toUpperCase());
		paVO.setPaName(paMasterForm.getPaName());
		paVO.setCountryCode(paMasterForm.getCountryCode().toUpperCase());
		paVO.setAddress(paMasterForm.getAddress());
		paVO.setMessagingEnabled(paMasterForm.getMessagingEnabled());
	//	paVO.setMailEvents(mailEventVOs);
		paVO.setOperationFlag(paMasterForm.getOpFlag());
		
		log.log(Log.FINE, "\n\n paVO----------> ", paVO);
		paSession.setPaVO(paVO);
		

		
		
		paMasterForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;
	}

}
