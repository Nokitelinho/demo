/*
 * DeleteULDNumberCommand.java Created on jan 29, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.recorduldmovement;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.RecordUldMovementSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.RecordULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
 /**
  * 
  * @author A-1936 Karthick.V
  *
  */
public class DeleteULDNumberCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	/**
	 * The execute method in BaseCommand
	 * @author A-1936
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.entering("DeleteRowCommand","execute");
		RecordULDMovementForm  recordULDMovementForm = (RecordULDMovementForm) invocationContext.screenModel;
		RecordUldMovementSession session = (RecordUldMovementSession)
							getScreenSession( "uld.defaults","uld.defaults.misc.recorduldmovement");
		String[] uldNumbers=recordULDMovementForm.getUldNumber();
		String[] checkForDelete=recordULDMovementForm.getCheckForUld();
		int iterator=0;

	    
		Collection<String> uldNos=session.getULDNumbers();
		if(uldNos!=null){
			log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE AFTER IF");
			for(String  uldNumber:uldNos){
				uldNumber=uldNumbers[iterator];
					iterator++;
			   log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE AFTER FOR");
			 }
		   session.setULDNumbers(uldNos);
		}
		ArrayList<String> list = new ArrayList<String>(session.getULDNumbers());
		ArrayList<String> listSession = new ArrayList<String>(session.getULDNumbers());

		     log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
			if(list!=null && checkForDelete!=null){
				log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
				log.entering("INSIDE THE MAIN DELETE","INSIDE THE MAIN DELETE");
					for(int i=0; i<checkForDelete.length; i++){
						   String str = list.get(Integer.parseInt(checkForDelete[i]));
						 listSession.remove(str);
					}
				session.setULDNumbers(listSession);
			}
		
	    log.exiting("DeleteRowCommand","execute");
		invocationContext.target = "save_success";
	}
}
