/*
 * AddMultipleULDCommand.java Created on Aug 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.maintainuld;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.impl.uld.defaults.MaintainULDSessionImpl;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MultipleULDForm;

/**
 * This command class is used to add a new uld
 *
 * @author A-2001
 */
public class AddMultipleULDCommand extends BaseCommand {
	
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
   
	private static final String ADDMULTIPLEULD_SUCCESS = "adduld_success";
    
   
    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
     public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDSessionImpl maintainULDSessionImpl = 
    		getScreenSession(MODULE, SCREENID);
    	MultipleULDForm multipleULDForm = 
    				(MultipleULDForm) invocationContext.screenModel;
    	ArrayList<String> uldNos = maintainULDSessionImpl.getUldNumbers();
    	 if(uldNos == null ) {
    		 uldNos = new ArrayList<String>();
    	 }
    	 if(uldNos != null &&
     			uldNos.size() > 0) {
 	    	String uldFormNos[] = multipleULDForm.getUldNos();
 	        for(int j = 0; j < uldFormNos.length; j++) {
 	    		uldNos.set(j,uldFormNos[j].toUpperCase());
 	    	}
     	}
        String uldNo = "";
        uldNos.add(uldNo);
        multipleULDForm.setOnloadStatusFlag("add");
        invocationContext.target = ADDMULTIPLEULD_SUCCESS;
    }
}
