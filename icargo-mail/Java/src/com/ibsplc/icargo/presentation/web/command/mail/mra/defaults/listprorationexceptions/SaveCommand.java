/*
 * SaveCommand.java Created on Sep, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.listprorationexceptions;

import java.util.Collection;
import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListMailProrationExceptionsForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ListProrationExceptionsSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationExceptionVO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

/**
 * @author A-3108
 *
 */
public class SaveCommand extends BaseCommand {

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA PRORATION");	
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.listmailprorationexceptions";
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String CLASS_NAME = "SaveCommand";
	private static final String SAVE_SUCCESS="save_success";
	private static final String BLANK = "";
	
	private static final String INFO_SAVESUCCESS = "mra.proration.listexceptions.msg.info.datasavedsuccessfully";
	


	/**
	 * Execute method
	 *
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
		throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		
		ListMailProrationExceptionsForm listExceptionForm = (ListMailProrationExceptionsForm) invocationContext.screenModel;
		ListProrationExceptionsSession listExceptionSession=getScreenSession(MODULE_NAME,SCREEN_ID);
		Collection<ProrationExceptionVO> proUpdateExpVOs= new ArrayList<ProrationExceptionVO>();
		
		

		int index=0;
		
		for(ProrationExceptionVO proExpVO:listExceptionSession.getProrationExceptionVOs()){
				if(index <listExceptionSession.getProrationExceptionVOs().size()){
		
				    if (listExceptionForm.getAssignedUser()[index] != null && listExceptionForm.getAssignedTime()[index] != null)
				    		
						{
				    	
				    	
				    	
				    	 if(listExceptionForm.getAssignedUser()[index]!=null){
				    		proExpVO.setAssignedUser(listExceptionForm.getAssignedUser()[index]);
				    		log.log(Log.INFO, "USER========>>",
									listExceptionForm.getAssignedUser(), index);
				    	 }
				    		
				    	 if(listExceptionForm.getAssignedTime()[index]!=null && !BLANK.equals(listExceptionForm.getAssignedTime()[index])){
				    		 log.log(Log.INFO, "TIME========>>",
									listExceptionForm.getAssignedTime(), index);
							proExpVO.setAssignedTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(listExceptionForm.getAssignedTime()[index]));
				    		
				    	 }
				    		proUpdateExpVOs.add(proExpVO);   
				    	
				    	log.log(Log.INFO, "Updated Row========>>", proExpVO);
				    }
				    index++;
				}
		}
		
		log.log(Log.INFO, "Updated Colln========>>", proUpdateExpVOs);
		try{
			new MailTrackingMRADelegate().saveProrationExceptions(proUpdateExpVOs);
		}catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			businessDelegateException.getMessage();
		}
		
		ErrorVO errorVO = new ErrorVO(INFO_SAVESUCCESS);
		invocationContext.addError(errorVO);

		log.exiting(CLASS_NAME, "execute");
		listExceptionSession.removeAllAttributes();
		invocationContext.target = SAVE_SUCCESS;
    }

}
