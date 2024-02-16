/*
 * DeleteDamageCommand.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.capturedamage;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * The Class DeleteDamageCommand.
 *
 * @author A-5991
 */
public class DeleteDamageCommand extends BaseCommand {
	
   /** The log. */
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   /** The Constant TARGET. */
   private static final String TARGET = "delete_success";   
   
   /** The Constant MODULE_NAME. */
   private static final String MODULE_NAME = "mail.operations";	
   
   /** The Constant SCREEN_ID. */
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	   
   
   /** The Constant OPERFLAG_INSERT_DELETE. */
   private static final String OPERFLAG_INSERT_DELETE = "ID";
   
   /** The Constant CLASS_NAME. */
   private static final String CLASS_NAME = "DeleteDamageCommand";	 
	 /**
 	 * This method overrides the execute method of BaseComand class.
 	 *
 	 * @param invocationContext the invocation context
 	 * @throws CommandInvocationException the command invocation exception
 	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MailArrivalForm mailArrivalForm = (MailArrivalForm) invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = getScreenSession(MODULE_NAME, SCREEN_ID);
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<DamagedMailbagVO> damagedMailbagVOs = mailArrivalSession
				.getDamagedMailbagVOs();
		Collection<DamagedMailbagVO> updatedvos = new ArrayList<DamagedMailbagVO>();

		if (damagedMailbagVOs == null) {
			damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		} else {
			damagedMailbagVOs = updateDamagedMailbagVOs(damagedMailbagVOs, mailArrivalForm, 
					logonAttributes);
		}
		String[] selectedRows = mailArrivalForm.getDamageSubCheck();
		int size = selectedRows.length;
		int row = 0;
		
		for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
			
			for (int j = 0; j < size; j++) {
				
				if (row == Integer.parseInt(selectedRows[j])) {
					
					if (DamagedMailbagVO.OPERATION_FLAG_INSERT.equals(
							damagedMailbagVO.getOperationFlag())) {
						damagedMailbagVO.setOperationFlag(OPERFLAG_INSERT_DELETE);
					} else {
						damagedMailbagVO.setOperationFlag(
								OnwardRoutingVO.OPERATION_FLAG_DELETE);
					}
				}
			}
			
			if (!OPERFLAG_INSERT_DELETE.equals(damagedMailbagVO.getOperationFlag())) {
				updatedvos.add(damagedMailbagVO);
			}
			row++;
		}

		log.log(Log.FINE, "After deleting DamagedMailbagVOs------------> ", updatedvos);
		mailArrivalSession.setDamagedMailbagVOs(updatedvos);
		invocationContext.target = TARGET;
		log.exiting(CLASS_NAME, "execute");
	}
    
    /**
     * Method to update the DamagedMailbagVOs in session.
     *
     * @param damagedMailbagVOs the damaged mailbag v os
     * @param mailArrivalForm the mail arrival form
     * @param logonAttributes the logon attributes
     * @return the collection
     */
	private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
			Collection<DamagedMailbagVO> damagedMailbagVOs, MailArrivalForm mailArrivalForm, 
			LogonAttributes logonAttributes) {
		log.entering(CLASS_NAME, "updateDamagedMailbagVOs");
		String[] damageCodes = mailArrivalForm.getDamageCode();
		String[] damageRemarks = mailArrivalForm.getDamageRemarks();

		if (damageCodes != null) {
			int index = 0;
			
			for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
				damagedMailbagVO.setDamageCode(damageCodes[index]);
				damagedMailbagVO.setRemarks(damageRemarks[index]);
				damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
				damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(), 
						Location.ARP, true);
				damagedMailbagVO.setDamageDate(currentdate);
				damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
				index++;
			}
		}
		log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ", damagedMailbagVOs);
		log.exiting(CLASS_NAME, "updateDamagedMailbagVOs");
		return damagedMailbagVOs;
	}
               
}