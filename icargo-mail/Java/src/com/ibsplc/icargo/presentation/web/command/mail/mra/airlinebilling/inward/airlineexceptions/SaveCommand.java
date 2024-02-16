/*
 * SaveCommand.java Created on Feb 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.inward.airlineexceptions;


import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.inward.AirlineExceptionsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.inward.AirlineExceptionsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * Command class for saving of airlineexceptions screen.
 *
 * Revision History
 *
 * Version      Date         Author          Description
 *
 * 0.1 	 Feb 19, 2007		Shivjith   		Initial draft
 *
 */
public class SaveCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING INWARD");
	
	private static final String CLASS_NAME = "ScreenloadCommand";
	/**
	 * module name
	 *
	 */
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	/**
	 * screen id
	 *
	 */
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.inward.airlineexceptions";
	
	private static final String SAVE_SUCCESS = "save_success";
	
	private static final String SAVE_FAILURE = "save_failure";
	
	private static final String BLANK = "";
	
	private static final String ERROR_KEY_NO_RECORDS = 
		"mra.airlinebilling.defaults.airlineexceptions.norecordstosave";
	
	/**
	 *
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 *
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		
		AirlineExceptionsForm airlineExceptionsForm = 
			(AirlineExceptionsForm)invocationContext.screenModel;
		
		AirlineExceptionsSession airLineExceptionsSession = 
			(AirlineExceptionsSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		Page<AirlineExceptionsVO> exceptionsVOs = null;
		
		exceptionsVOs=airLineExceptionsSession.getAirlineExceptionsVOs();
		
		if(exceptionsVOs != null && exceptionsVOs.size() > 0){
			
			updateExceptionVOs(airlineExceptionsForm, exceptionsVOs);
			
			try {
				new MailTrackingMRADelegate().saveAirlineExceptions(exceptionsVOs);
				airlineExceptionsForm.setSaveStatus("Y");
				ErrorVO error = new ErrorVO(
				"mailtracking.mra.airlinebilling.inward.airlineexceptions.datasavedsuccessfully");
				errors.add(error);
				invocationContext.target = SAVE_SUCCESS;
				airLineExceptionsSession.removeAirlineExceptionsVOs();
							
				
			} catch (BusinessDelegateException e) {
				
				errors=handleDelegateException( e );
				invocationContext.target = SAVE_FAILURE;
			}
		}else{
			
			errors.add(new ErrorVO(ERROR_KEY_NO_RECORDS));
			invocationContext.target = SAVE_FAILURE;
		}		
		
		invocationContext.addAllError(errors);
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * Updates ExceptionVOs with with current values in form
	 * @param form
	 * @param exceptionsVOs
	 */
	private void updateExceptionVOs(AirlineExceptionsForm form,
			Collection<AirlineExceptionsVO> exceptionsVOs){
		
		log.entering(CLASS_NAME, "updateExceptionVOs");
		int index = 0;
		String[] asssignees = form.getAssigneeCode();
		String[] assignDates = form.getAssignedDate();
		String[] oprFlag = form.getOperationalFlag();
		
		for(AirlineExceptionsVO expVO : exceptionsVOs){
			
			String assignee = asssignees[index];
			if(!BLANK.equals(assignee) && assignee != null){
				
				expVO.setAssigneeCode(assignee);
			}
			
			String assignDate = assignDates[index];
			if(!BLANK.equals(assignDate) && assignDate != null){
				
				expVO.setAssignedDate(convertToDate(assignDate));
			}
			
			expVO.setOperationalFlag(String.valueOf(oprFlag[index]));
			index++;
		}
		log.exiting(CLASS_NAME, "updateExceptionVOs");
	}
	
	/**
	 * Converts date format string to LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){
		
		if(date!=null && !date.equals(BLANK)){
			
			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}
	
}