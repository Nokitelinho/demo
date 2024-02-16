/*
 * ScreenLoadMultipleULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainULDSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked on the start up of the ULD screen
 * 
 * @author A-2001
 */
public class ScreenLoadMultipleULDCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ScreenloadMultipleUld");
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
   
    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDSession maintainULDSession = 
    		getScreenSession(MODULE, SCREENID);
    	MultipleULDForm multipleULDForm = 
    						(MultipleULDForm) invocationContext.screenModel;
    	ArrayList<String> uldnos = new ArrayList<String>();
    	if(maintainULDSession.getUldNumbersSaved()!= null &&
    			maintainULDSession.getUldNumbersSaved().size() > 0) {
    		ArrayList<String> uldSavedNumbers = 
    								maintainULDSession.getUldNumbersSaved();
    		for(String uldNumber : uldSavedNumbers) {
    			uldnos.add(uldNumber);
    		}
    		
    	}
    	if(maintainULDSession.getULDVO() == null) {
    		ULDVO uldVO = new ULDVO();
    		maintainULDSession.setULDVO(uldVO);
    	}
    	
    	maintainULDSession.setUldNumbers(uldnos);
    	log.log(Log.FINE, "ULDTYPE-------->>", multipleULDForm.getUldType());
		log.log(Log.FINE, "AIRLINE-------->>", multipleULDForm.getOwnerAirlineCode());
		multipleULDForm.setOnloadStatusFlag("screenLoad");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    }
    
    
	
}
