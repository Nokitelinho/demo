/*
 * AddDamageCommand.java Created on July 24, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.returndsn;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReturnDsnSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReturnDsnForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1861
 *
 */
public class AddDamageCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "add_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.returndsn";	
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddDamageCommand","execute");
    	  
    	ReturnDsnForm returnDsnForm = 
    		(ReturnDsnForm)invocationContext.screenModel;
    	ReturnDsnSession returnDsnSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		Collection<DamagedDSNVO> damagedDsnVOs = returnDsnSession.getDamagedDsnVOs();
		
		int currentVoIndex = returnDsnForm.getCurrentPage();
		log.log(Log.INFO, "currentpage:-->", currentVoIndex);
		// adding new vo
		int index = 1;
		Collection<DamagedDSNDetailVO> damagedDsnDetailVOs = null;
		for (DamagedDSNVO vo : damagedDsnVOs) {
			
			if (index == currentVoIndex) {
				damagedDsnDetailVOs = vo.getDamagedDsnDetailVOs();
				
				if (damagedDsnDetailVOs == null) {
					damagedDsnDetailVOs = new ArrayList<DamagedDSNDetailVO>();
				}
				
				// adding new DamagedDSNDetailVO
				DamagedDSNDetailVO dsnDetailVO = new DamagedDSNDetailVO();
				dsnDetailVO.setOperationFlag(DamagedDSNDetailVO.OPERATION_FLAG_INSERT);
				dsnDetailVO.setAirportCode(logonAttributes.getAirportCode());				
				damagedDsnDetailVOs.add(dsnDetailVO);
				
				vo.setDamagedDsnDetailVOs(damagedDsnDetailVOs);
				break;
			}										
			index++;
		}
		
		log.log(Log.FINE, "After adding DamagedDsnVOs------------> ",
				damagedDsnVOs);
		returnDsnSession.setDamagedDsnVOs(damagedDsnVOs);
    	
    	invocationContext.target = TARGET;
    	
       	log.exiting("AddDamageCommand","execute");
    	
    }
                 
}
