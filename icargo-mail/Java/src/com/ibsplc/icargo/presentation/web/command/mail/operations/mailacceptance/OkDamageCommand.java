/*
 * OkDamageCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class OkDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "ok_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("OkDamageCommand","execute");
    	  
    	MailAcceptanceForm mailAcceptanceForm = 
    		(MailAcceptanceForm)invocationContext.screenModel;
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		Collection<DamagedMailbagVO> damagedMailbagVOs = mailAcceptanceSession.getDamagedMailbagVOs();
		
		if (damagedMailbagVOs == null) {
			damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		}
		else {
			damagedMailbagVOs = updateDamagedMailbagVOs(
					damagedMailbagVOs,
					mailAcceptanceForm,
					logonAttributes,
					mailAcceptanceSession);
		}
		
		String selectedMailBagIndex = mailAcceptanceForm.getSelectedMailBag();
		log.log(Log.FINE, "selectedMailBagIndex------------> ",
				selectedMailBagIndex);
		ContainerDetailsVO containerDetailsVO = mailAcceptanceSession.getContainerDetailsVO();
		Collection<MailbagVO> mailbagVOs = containerDetailsVO.getMailDetails();
		int index = 1;
		//modified for icrd-85496 by a-4810 begins
		 for(MailbagVO mailbagVO : mailbagVOs) {
			 if (index == Integer.parseInt(selectedMailBagIndex)) {
				 mailbagVO.setDamagedMailbags(damagedMailbagVOs);	
				 if(mailbagVO.getDamageFlag() != null && "Y".equals(mailbagVO.getDamageFlag())) {
					 if(mailbagVO.getDamagedMailbags()== null || mailbagVO.getDamagedMailbags().size() == 0){
						 invocationContext.addError(new ErrorVO("mailtracking.defaults.err.reasonfordamagemandatory")); 
						 break;
					  }
					 //Added by A-8527 for ICRD-329488 starts
					 	LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	   					mailbagVO.setScannedDate(currentdate);
	   				//Added by A-8527 for ICRD-329488 Ends
		//modified for icrd-85496 by a-4810 ends
				 }
				 if (!MailbagVO.OPERATION_FLAG_INSERT.equals(mailbagVO.getOperationalFlag())) {
					 mailbagVO.setOperationalFlag(MailbagVO.OPERATION_FLAG_UPDATE);
				 }
				 break;
			 }
			 index++;
		 }
		 containerDetailsVO.setMailDetails(mailbagVOs);
		 mailAcceptanceSession.setContainerDetailsVO(containerDetailsVO);
		 log.log(Log.FINE, "Updated containerDetailsVO------------> ",
				containerDetailsVO);
		mailAcceptanceSession.setDamagedMailbagVOs(null);
		if(invocationContext.getErrors()== null){
		 mailAcceptanceForm.setSelectedMailBag("CLOSE");
		}
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("OkDamageCommand","execute");
    	
    }
    /**
     * Method to update the DamagedMailbagVOs in session
     * @param damagedMailbagVOs
     * @param mailAcceptanceForm
     * @param logonAttributes
     * @param mailAcceptanceSession
     * @return
     */
    private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
    		Collection<DamagedMailbagVO> damagedMailbagVOs,
    		MailAcceptanceForm mailAcceptanceForm,
    		LogonAttributes logonAttributes,
    		MailAcceptanceSession mailAcceptanceSession)  {
    	
    	log.entering("OkDamageCommand","updateDamagedMailbagVOs");
    	
    	String[] damageCodes = mailAcceptanceForm.getDamageCode();
    	String[] damageRemarks = mailAcceptanceForm.getDamageRemarks();
    	
    	if (damageCodes != null) {
    		int index = 0;
    		for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOs) {
    			damagedMailbagVO.setDamageCode(damageCodes[index]);
    			damagedMailbagVO.setRemarks(damageRemarks[index]);
    			damagedMailbagVO.setDamageDescription(
    					handleDamageCodeDescription(mailAcceptanceSession,damageCodes[index]));
    			damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
    			damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
    			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    			damagedMailbagVO.setDamageDate(currentdate);
    			damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
    			damagedMailbagVO.setOperationType("O");
    			index++;
    		}
    	}
    	
    	log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ",
				damagedMailbagVOs);
		log.exiting("OkDamageCommand","updateDamagedMailbagVOs");
    	
    	return damagedMailbagVOs;    	
    }
    /**
     * Method is used to get the onetime description
     * @param mailAcceptanceSession
     * @param damagecode
     * @return
     */
    private String handleDamageCodeDescription(
    		MailAcceptanceSession mailAcceptanceSession,
    		String damagecode) {
    	
    	String damageDesc = "";
    	
    	Collection<OneTimeVO> damageCodes = mailAcceptanceSession.getOneTimeDamageCodes();
    	for (OneTimeVO vo : damageCodes) {
    		if (vo.getFieldValue().equals(damagecode)) {
    			damageDesc = vo.getFieldDescription();
    			break;
    		}
    	}
    	
    	return damageDesc;    	
    }
               
}
