/*
 * OkDamageCommand.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival.capturedamage;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class OkDamageCommand.
 *
 * @author A-5991
 */
public class OkDamageCommand extends BaseCommand {
	
   /** The log. */
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   /** The Constant TARGET. */
   private static final String TARGET = "ok_success";
   
   /** The Constant MODULE_NAME. */
   private static final String MODULE_NAME = "mail.operations";	
   
   /** The Constant SCREEN_ID. */
   private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
 
   /** The Constant CLASS_NAME. */
   private static final String CLASS_NAME = "OkDamageCommand";	 
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

		if (damagedMailbagVOs == null) {
			damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		} else {
			damagedMailbagVOs = updateDamagedMailbagVOs(damagedMailbagVOs, mailArrivalForm, 
					logonAttributes, mailArrivalSession);
		}
		
		String selectedMailBagIndex = mailArrivalForm.getSelectedMailBag();
		log.log(Log.FINE, "selectedMailBagIndex------------> ", selectedMailBagIndex);
		ContainerDetailsVO containerDetailsVO = mailArrivalSession.getContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		int index = 1;
		
		for (MailbagVO mailbagVO : mailbagVOs) {
			
			if (index == Integer.parseInt(selectedMailBagIndex)) {
				mailbagVO.setDamagedMailbags(damagedMailbagVOs);
				
				if (mailbagVO.getDamageFlag() != null && MailbagVO.FLAG_YES.equals(
						mailbagVO.getDamageFlag())) {
					
					if (mailbagVO.getDamagedMailbags() == null || 
							mailbagVO.getDamagedMailbags().size() == 0) {
						invocationContext.addError(new ErrorVO(
								"mailtracking.defaults.err.reasonfordamagemandatory"));
						break;
					}
				}
				
				if (!MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_UPDATE);
				}
				break;
			}
			index++;
		}
		
		Collection<ErrorVO> errorVOs = validateForm(mailArrivalForm);
		
		if(errorVOs.size() > 0){
			mailArrivalSession.setDamagedMailbagVOs(damagedMailbagVOs);
			invocationContext.addAllError(errorVOs);
			invocationContext.target = TARGET;
			return;
		}
		containerDetailsVO.setMailDetails(mailbagVOs);
		mailArrivalSession.setContainerDetailsVO(containerDetailsVO);		
		log.log(Log.FINE, "Updated containerDetailsVO------------> ", containerDetailsVO);
		mailArrivalSession.setDamagedMailbagVOs(null);
		
		if (invocationContext.getErrors() == null) {
			mailArrivalForm.setSelectedMailBag("CLOSE");
		}
		invocationContext.target = TARGET;
		log.exiting(CLASS_NAME, "execute");
	}
    
	/**
	 * Validate form.
	 *
	 * @param mailArrivalForm the mail arrival form
	 * @return the collection
	 */
	private Collection<ErrorVO> validateForm(MailArrivalForm mailArrivalForm) {
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		String[] damageCodes = mailArrivalForm.getDamageCode();

		if (damageCodes != null && damageCodes.length > 0) {
		int length = damageCodes.length;
		int index = 0;
		
		for (String damageCode : damageCodes) {
			
			for (int ind = length - 1; ind > index; ind--) {
				
				if (damageCode.equals(damageCodes[ind])) {
						errorVOs.add(new ErrorVO(
								"mailtracking.defaults.err.duplicatedamagecode"));
					break;
				}
			}
			++index;
			}
		}
		return errorVOs;
	}

	/**
     * Method to update the DamagedMailbagVOs in session.
     *
     * @param damagedMailbagVOs the damaged mail bag vo s
     * @param mailArrivalForm the mail arrival form
     * @param logonAttributes the log on attributes
     * @param mailArrivalSession the mail arrival session
     * @return the collection
     */
	private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
			Collection<DamagedMailbagVO> damagedMailbagVOs, MailArrivalForm mailArrivalForm, 
			LogonAttributes logonAttributes, MailArrivalSession mailArrivalSession) {
		log.entering(CLASS_NAME, "updateDamagedMailbagVOs");
		String[] damageCodes = mailArrivalForm.getDamageCode();
		String[] damageRemarks = mailArrivalForm.getDamageRemarks();

		if (damageCodes != null) {
			int index = 0;
			for (DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs) {
				damagedMailbagVO.setDamageCode(damageCodes[index]);
				damagedMailbagVO.setRemarks(damageRemarks[index]);
				damagedMailbagVO.setDamageDescription(handleDamageCodeDescription(
						mailArrivalSession, damageCodes[index]));
				damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
				damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(), 
						Location.ARP, true);
				damagedMailbagVO.setDamageDate(currentdate);
				damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
				damagedMailbagVO.setOperationType("I");      
				index++;
			}
		}
		log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ", damagedMailbagVOs);
		log.exiting(CLASS_NAME, "updateDamagedMailbagVOs");
		return damagedMailbagVOs;
	}
    
    /**
     * Method is used to get the one time description.
     *
     * @param mailArrivalSession the mail arrival session
     * @param damagecode the damage code
     * @return the string
     */
	private String handleDamageCodeDescription(MailArrivalSession mailArrivalSession, 
			String damagecode) {
		String damageDesc = "";
		Collection<OneTimeVO> damageCodes = mailArrivalSession.getOneTimeDamageCodes();
		
		for (OneTimeVO vo : damageCodes) {
			
			if (vo.getFieldValue().equals(damagecode)) {
				damageDesc = vo.getFieldDescription();
				break;
			}
		}
		return damageDesc;
	}
               
}