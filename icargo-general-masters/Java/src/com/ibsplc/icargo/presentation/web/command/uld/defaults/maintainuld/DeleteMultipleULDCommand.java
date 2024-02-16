/*
 * DeleteMultipleULDCommand.java Created on Aug 1, 2005
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
 * This command class is used to delete a uld from the display
 *
 * @author A-2001
 */
public class DeleteMultipleULDCommand extends BaseCommand {
	
	/*
	 * The Module Name
	 */
	private static final String MODULE = "uld.defaults";
	
	/*
	 * Screen Id of maintain uld screen
	 */
	private static final String SCREENID =
		"uld.defaults.maintainuld";
	private static final String DELETEMULTIPLEULD_SUCCESS = 
    	"deletemultipleuld_success";
   
    /**
     * @param invocationContext
     * @return 
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	MaintainULDSessionImpl maintainULDSessionImpl = 
    		getScreenSession(MODULE, SCREENID);
    	ArrayList<String> uldNos = 
    							maintainULDSessionImpl.getUldNumbers();
    	MultipleULDForm multipleULDForm = 
    					(MultipleULDForm) invocationContext.screenModel;
    	if(uldNos != null &&
    			uldNos.size() > 0) {
	    	String uldFormNos[] = multipleULDForm.getUldNos();
	    	for(int j = 0; j < uldFormNos.length; j++) {
	    		uldNos.set(j,uldFormNos[j].toUpperCase());
	    	}
     	}
    	
    	if(multipleULDForm.getSelectedRows() != null &&
    			multipleULDForm.getSelectedRows().length > 0) {
	    	String selectedRows[] = multipleULDForm.getSelectedRows();
	    	for(int i = selectedRows.length - 1; i >= 0; i--) {
	    		uldNos.remove(Integer.parseInt(selectedRows[i]));
	    	}
    	}
    	if(uldNos != null &&
    			uldNos.size() > 0) {
    		int size =  uldNos.size();
	    	for(int j = 0; j < size; j++) {
	    		uldNos.set(j,uldNos.get(j));
	    	
 	    	}
     	}
    	        	
    	multipleULDForm.setOnloadStatusFlag("delete");
    	invocationContext.target = DELETEMULTIPLEULD_SUCCESS;
    }
}
