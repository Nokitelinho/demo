/*
 * AddDamageCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailacceptance;

import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailAcceptanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class AddDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "add_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailacceptance";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddDamageCommand","execute");
    	  
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
					logonAttributes);
		}
		
		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
		damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		damagedMailbagVO.setDamageDate(currentdate);
		damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
		damagedMailbagVO.setOperationFlag(DamagedMailbagVO.OPERATION_FLAG_INSERT);
		
		damagedMailbagVOs.add(damagedMailbagVO);
		
		log.log(Log.FINE, "After adding DamagedMailbagVOs------------> ",
				damagedMailbagVOs);
		mailAcceptanceSession.setDamagedMailbagVOs(damagedMailbagVOs);
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("AddDamageCommand","execute");
    	
    }
    /**
     * Method to update the DamagedMailbagVOs in session
     * @param damagedMailbagVOs
     * @param mailAcceptanceForm
     * @param logonAttributes
     * @return
     */
    private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
    		Collection<DamagedMailbagVO> damagedMailbagVOs,
    		MailAcceptanceForm mailAcceptanceForm,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("AddDamageCommand","updateDamagedMailbagVOs");
    	
    	String[] damageCodes = mailAcceptanceForm.getDamageCode();
    	String[] damageRemarks = mailAcceptanceForm.getDamageRemarks();
    	
    	if (damageCodes != null) {
    		int index = 0;
    		for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOs) {
    			damagedMailbagVO.setDamageCode(damageCodes[index]);
    			damagedMailbagVO.setRemarks(damageRemarks[index]);
    			damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
    			damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
    			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    			damagedMailbagVO.setDamageDate(currentdate);
    			damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
    			index++;
    		}
    	}
    	
    	log.log(Log.FINE, "Updated DamagedMailbagVOs------------> ",
				damagedMailbagVOs);
		log.exiting("AddDamageCommand","updateDamagedMailbagVOs");
    	
    	return damagedMailbagVOs;    	
    }
               
}
