/*
 * CloseCommand.java  *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * Author(s)			: A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.offload;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.OffloadSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.OffloadForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class CloseCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String CLOSE_TO_MAILBAGENQ = "close_success_mbenq";	   
	   private static final String CLOSE_SUCCESS = "close_success";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.offload";	
	   private static final String CLOSE_TO_SEARCHCON = "close_success_searchcon";
	   private static final String CLOSE_TO_DSNENQ = "close_success_dsnenq";
	   
	   private static final String FLAG_NORMAL = "NORMAL";
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @return
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("CloseCommand","execute");
	    	  
	    	OffloadForm offloadForm = 
	    		(OffloadForm)invocationContext.screenModel;
	    	OffloadSession offloadSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			log.log(Log.FINE, "\n\ngetFlightStatus--------->>", offloadForm.getFlightStatus());
			if(("emptyuld").equals(offloadForm.getFlightStatus())){
				ErrorVO error = new ErrorVO("mailtracking.defaults.offload.info.offloadsuccess");
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				errors.add(error);
				invocationContext.addAllError(errors);
			}
			
			log.log(Log.FINE, "\nMODE--------->>", offloadForm.getMode());
			if(FLAG_NORMAL.equals(offloadForm.getMode())) {
				offloadForm.setContainerNumber("");
				offloadForm.setContainerType("");
				offloadForm.setDate("");
				offloadForm.setDeparturePort(logonAttributes.getAirportCode());
				offloadForm.setDespatchSn("");
				offloadForm.setDestnOE("");
				offloadForm.setFlightCarrierCode("");
				offloadForm.setFlightNumber("");
				offloadForm.setMailbagCategory("");
				offloadForm.setMailbagDestnOE("");
				offloadForm.setMailbagDsn("");
				offloadForm.setMailbagOriginOE("");
				offloadForm.setMailbagRsn("");
				offloadForm.setMailbagId("");//added as part of ICRD-205027
				offloadForm.setMailbagSubClass("");
				offloadForm.setMailbagYear("");
				offloadForm.setMailClass("U");
				offloadForm.setOriginOE("");		
				offloadForm.setStatus("");
				offloadForm.setMode("");
				offloadForm.setType("");
				offloadForm.setYear("");
				offloadForm.setFlightStatus("");
				
				offloadSession.setFlightValidationVO(null);
			}
			else {
				offloadForm.setContainerNumber("");
				offloadForm.setDespatchSn("");
				offloadForm.setDestnOE("");
				offloadForm.setMailbagCategory("");
				offloadForm.setMailbagDestnOE("");
				offloadForm.setMailbagDsn("");
				offloadForm.setMailbagOriginOE("");
				offloadForm.setMailbagRsn("");
				offloadForm.setMailbagId("");//added as part of ICRD-205027
				offloadForm.setMailbagSubClass("");
				offloadForm.setMailbagYear("");
				offloadForm.setMailClass("U");
				offloadForm.setOriginOE("");		
				offloadForm.setMode("");
				offloadForm.setYear("");
			}	
			
			
			offloadSession.setOffloadVO(null);

			log.log(Log.FINE, "\n offloadForm.getFromScreen()--------->>",
					offloadForm.getFromScreen());
			offloadForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
			if(("MAILBAGENQUIRY").equals(offloadForm.getFromScreen()))
			{
				invocationContext.target = CLOSE_TO_MAILBAGENQ;
			}
			else if(("SEARCHCONTAINER").equals(offloadForm.getFromScreen()))
			{
				invocationContext.target = CLOSE_TO_SEARCHCON;
			}
			else if(("DSNENQUIRY").equals(offloadForm.getFromScreen()))
			{
				invocationContext.target = CLOSE_TO_DSNENQ;
			}			
			else
			{
				invocationContext.target = CLOSE_SUCCESS;
			}
	    
			offloadForm.setFromScreen("");
	    	log.exiting("CloseCommand","execute");
	    	
	    }      
	}
