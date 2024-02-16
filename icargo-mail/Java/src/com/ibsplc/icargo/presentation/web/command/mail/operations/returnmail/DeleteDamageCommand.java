/*
 * DeleteDamageCommand.java Created on July 18, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returnmail;

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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnMailForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class DeleteDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "delete_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returnmail";	
   
   private static final String OPERFLAG_INSERT_DELETE = "ID";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("DeleteDamageCommand","execute");
    	  
    	ReturnMailForm returnMailForm = 
    		(ReturnMailForm)invocationContext.screenModel;
    	ReturnMailSession returnMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		Collection<DamagedMailbagVO> damagedMailbagVOs = returnMailSession.getDamagedMailbagVOs();
		Collection<DamagedMailbagVO> updatedvos = new ArrayList<DamagedMailbagVO>();
		
		if (damagedMailbagVOs == null) {
			damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		}
		else {
			damagedMailbagVOs = updateDamagedMailbagVOs(
					damagedMailbagVOs,
					returnMailForm,
					logonAttributes);
		}
		
		String[] selectedRows = returnMailForm.getReturnSubCheck();    	
    	int size = selectedRows.length;
    	int row = 0;
    	for(DamagedMailbagVO damagedMailbagVO:damagedMailbagVOs) {
    		for (int j = 0; j < size; j++) {
    			if (row == Integer.parseInt(selectedRows[j])) {
    				if (DamagedMailbagVO.OPERATION_FLAG_INSERT.equals(damagedMailbagVO.getOperationFlag())) {
    					damagedMailbagVO.setOperationFlag(OPERFLAG_INSERT_DELETE);
					}
					else {
						damagedMailbagVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_DELETE);
					}		    				
    			}	    
    		}
    		if (!OPERFLAG_INSERT_DELETE.equals(damagedMailbagVO.getOperationFlag())) {
				updatedvos.add(damagedMailbagVO);
			}
    		row++;
    	}    	
    	
		log.log(Log.FINE, "After deleting DamagedMailbagVOs------------> ",
				updatedvos);
		returnMailSession.setDamagedMailbagVOs(updatedvos);
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("DeleteDamageCommand","execute");
    	
    }
    /**
     * Method to update the DamagedMailbagVOs in session
     * @param damagedMailbagVOs
     * @param returnMailForm
     * @param logonAttributes
     * @return
     */
    private Collection<DamagedMailbagVO> updateDamagedMailbagVOs(
    		Collection<DamagedMailbagVO> damagedMailbagVOs,
    		ReturnMailForm returnMailForm,
    		LogonAttributes logonAttributes) {
    	
    	log.entering("DeleteDamageCommand","updateDamagedMailbagVOs");
    	
    	String[] damageCodes = returnMailForm.getDamageCode();
    	String[] damageRemarks = returnMailForm.getDamageRemarks();
    	
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
		log.exiting("DeleteDamageCommand","updateDamagedMailbagVOs");
    	
    	return damagedMailbagVOs;    	
    }
               
}