/*
 * DeleteMailEventCommand.java Created on June 16, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.masters;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

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
public class DeleteMailEventCommand extends BaseCommand {

	private static final String SUCCESS = "delete_success";
	
	private Log log = LogFactory.getLogger("DeleteMailEventCommand");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = 
						"mailtracking.defaults.masters.postaladministration";
	
	private static final String OPERATION_FLAG_INS_DEL ="I_D";
	
	
	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
    	log.log(Log.FINE, "\n\n in the delete command----------> \n\n");
    	
    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
    	
    	PostalAdministrationForm paMasterForm =
						(PostalAdministrationForm)invocationContext.screenModel;
		PostalAdministrationSession paSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);

		ArrayList<MailEventVO> mailEventVOs = new ArrayList<MailEventVO>();
    	
    	ArrayList<MailEventVO> newMailEventVOs = new ArrayList<MailEventVO>();
    	
    	String[] opFlag = paMasterForm.getOperationFlag();
		String[] rowIds = paMasterForm.getRowId();

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

				String catogory = paMasterForm.
											getMailCategory()[i].toUpperCase();
				String mailClas = paMasterForm.getMailClass()[i].toUpperCase();
				
				mailEventVO.setPaCode(paMasterForm.getPaCode());
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
		
		paMasterForm.setRowId(null);
		
		if(rowIds != null) {
			int size = rowIds.length;
			if(mailEventVOs!=null && mailEventVOs.size()>0) {
				int index=0;
				for(MailEventVO mailEventVO:mailEventVOs) {
					for (int i = 0; i < size; i++) {
						if (index == Integer.parseInt(rowIds[i])) {
							
							/*
							 * If the IncompatibleSCCVO has come from the server
							 * then update the operationflag to D
							 */
							if (("NA").equals(mailEventVO.getOperationFlag())
									|| mailEventVO.getOperationFlag().equals(
											OPERATION_FLAG_UPDATE)) {
								log.log(Log.FINE,
										"NA,U #######-------------> ", rowIds,
										i);
								mailEventVO.setOperationFlag
														(OPERATION_FLAG_DELETE);


							}
							/*
							 * If the RouteDetailsVO is a newly created one,
							 * then set the operationflag as insert_to_delete
							 */
							else if (mailEventVO.getOperationFlag().equals(
									OPERATION_FLAG_INSERT)) {
								log.log(Log.FINE,
										"I #######-----------------> ", rowIds,
										i);
								mailEventVO.setOperationFlag
													(OPERATION_FLAG_INS_DEL);
							}
						}
					}

					/*
					 * If the operation flag is insert_to_delete
					 * then remove the incompatibleSCCVO from the existing
					 * collection.
					 */
					 if (!mailEventVO.getOperationFlag().equals(
									OPERATION_FLAG_INS_DEL)) {
						log.log(Log.FINE, "U,D,I,NA -----------------------> ",
								index);
						newMailEventVOs.add(mailEventVO);

					}
					index++;
				}
			}
		}
		
		PostalAdministrationVO paVO = new PostalAdministrationVO();
		paVO.setCompanyCode(companyCode);
		paVO.setPaCode(paMasterForm.getPaCode());
		paVO.setPaName(paMasterForm.getPaName());
		paVO.setCountryCode(paMasterForm.getCountryCode());
		paVO.setAddress(paMasterForm.getAddress());
		paVO.setMessagingEnabled(paMasterForm.getMessagingEnabled());
	//	paVO.setMailEvents(newMailEventVOs);
		paVO.setOperationFlag(paMasterForm.getOpFlag());
		
		log.log(Log.FINE, "\n\n paVO----------> ", paVO);
		paSession.setPaVO(paVO);
		
		
		paMasterForm.setScreenStatusFlag
							(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		invocationContext.target = SUCCESS;

	}


}
