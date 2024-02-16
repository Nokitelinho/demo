/*
 * UpdateSessionCommand.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.flown.assignexceptions;

import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;

import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.flown.AssignExceptionSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.flown.AssignExceptionsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2401
 *
 */
public class UpdateSessionCommand extends BaseCommand{

	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA ");
	/**
	 * 
	 * class name
	 */
	private static final String CLASS_NAME = "UpdateSessionCommand";
	/**
	 * 
	 * screen name
	 */
	private static final String SCREEN_ID = "mailtracking.mra.flown.assignexceptions";
	/**
	 * 
	 * module name
	 */
	private static final String MODULE_NAME = "mailtracking.mra.flown";
	
	private static final String LISTSTATUS ="toList";
	private static final String SAVESTATUS ="toSave";
	private static final String EMPTY ="";
	private static final String ACTION_SUCCESS = "action_success";
	
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
		AssignExceptionsForm assignExceptionsForm = 
			(AssignExceptionsForm)invocationContext.screenModel;
		AssignExceptionSession assignExceptionSession = (AssignExceptionSession)
				getScreenSession(MODULE_NAME, SCREEN_ID);
		log.log(Log.FINE, "flag**************", assignExceptionsForm.getStatusFlag());
		if((LISTSTATUS).equals(assignExceptionsForm.getStatusFlag())) {
			updateFilterDetails(assignExceptionsForm,assignExceptionSession);
		}
		if((SAVESTATUS).equals(assignExceptionsForm.getStatusFlag())) {
			if(assignExceptionSession.getExceptions() != null &&
					assignExceptionSession.getExceptions().size() > 0) {
				updateExceptions(assignExceptionsForm,assignExceptionSession);
			}
		}
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");	
	}
	
	
	/**
	 * 
	 * @param assignExceptionsForm
	 * @param assignExceptionSession
	 * 
	 */
	public void updateFilterDetails(
			AssignExceptionsForm assignExceptionsForm,
			AssignExceptionSession assignExceptionSession){
		log.entering(CLASS_NAME, "updateFilterDetails");
		/**
		 * 
		 * updating session from form
		 */
		Collection<OneTimeVO> onetimes = null;
		FlownMailFilterVO flownMailFilterVO = 
			new FlownMailFilterVO();
		
		if(assignExceptionsForm.getFlightCarrierCode() != null && 
			assignExceptionsForm.getFlightCarrierCode().trim().length() > 0){
			flownMailFilterVO.setFlightCarrierCode(
					assignExceptionsForm.getFlightCarrierCode().toUpperCase());
	    }
		
		if(assignExceptionsForm.getFlightNumber() != null && 
			assignExceptionsForm.getFlightNumber().trim().length() > 0){
			flownMailFilterVO.setFlightNumber(
					assignExceptionsForm.getFlightNumber().toUpperCase());
	    }
		
		if(assignExceptionsForm.getFromDate() != null && 
				assignExceptionsForm.getFromDate().trim().length() > 0){
			LocalDate fromDate = new LocalDate(
					NO_STATION,NONE,false);
	   		fromDate.setDate(assignExceptionsForm.getFromDate());
	   		flownMailFilterVO.setFromDate(fromDate);
				
		}
		if(assignExceptionsForm.getToDate() != null && 
				assignExceptionsForm.getToDate().trim().length() > 0){
			LocalDate toDate = new LocalDate(
					NO_STATION,NONE,false);
			toDate.setDate(assignExceptionsForm.getToDate());
			flownMailFilterVO.setToDate(toDate);
		}
		log.log(Log.FINE, "**********before if", flownMailFilterVO.getExceptionCode(), "*****", assignExceptionsForm.getExceptionCode());
		if(assignExceptionsForm.getExceptionCode() != null && 
			assignExceptionsForm.getExceptionCode().trim().length() > 0){			
			onetimes = assignExceptionSession.getOneTimeValues().get("maitracking.flown.exceptioncode");
			log.log(Log.FINE, "**********onetime", onetimes);
			if(onetimes != null && onetimes.size() > 0){
				for(OneTimeVO onetimeVO : onetimes){
					log.log(Log.FINE, "*******", onetimeVO.getFieldDescription(), "*****", onetimeVO.getFieldValue());
					if(onetimeVO.getFieldDescription().
							equalsIgnoreCase(assignExceptionsForm.getExceptionCode())){						
						flownMailFilterVO.setExceptionCode(onetimeVO.getFieldValue());
						assignExceptionsForm.setExceptionCodeForPrint(onetimeVO.getFieldValue());
					}
				}
			}
			
	    }
		log.log(Log.FINE, "**********onetime", assignExceptionsForm.getExceptionCodeForPrint());
		if(assignExceptionsForm.getAssignee() != null && 
			assignExceptionsForm.getAssignee().trim().length() > 0){
			flownMailFilterVO.setAssigneeCode(
					assignExceptionsForm.getAssignee().toUpperCase());
	    }
		
		if(assignExceptionsForm.getAssignedDate() != null && 
				assignExceptionsForm.getAssignedDate().trim().length() > 0){
			LocalDate assignedDate = new LocalDate(
					NO_STATION,NONE,false);
			assignedDate.setDate(assignExceptionsForm.getAssignedDate());
			flownMailFilterVO.setAssignedDate(assignedDate);
		}
		if(assignExceptionsForm.getSegmentOrigin() != null && 
				assignExceptionsForm.getSegmentOrigin().trim().length() > 0){
			flownMailFilterVO.setSegmentOrigin(
						assignExceptionsForm.getSegmentOrigin().toUpperCase());
		    }
		if(assignExceptionsForm.getSegmentDestination() != null && 
				assignExceptionsForm.getSegmentDestination().trim().length() > 0){
				flownMailFilterVO.setSegmentDestination(
						assignExceptionsForm.getSegmentDestination().toUpperCase());
		    }
		
		/*
		if(assignExceptionsForm.getResolvedDate() != null && 
				assignExceptionsForm.getResolvedDate().trim().length() > 0){
			LocalDate resolvedDate = new LocalDate(
					NO_STATION,NONE,false);
			resolvedDate.setDate(assignExceptionsForm.getResolvedDate());
			flownMailFilterVO.setResolvedDate(resolvedDate);
		}
		*/
		
		flownMailFilterVO.setCompanyCode(
				getApplicationSession().getLogonVO().getCompanyCode());
		assignExceptionSession.setFilterDetails(flownMailFilterVO);
		log.log(Log.INFO, "Updated Filtervo___>>>>", assignExceptionSession.getFilterDetails());
		log.exiting(CLASS_NAME, "updateFilterDetails");
	}
	
	/**
	 * 
	 * @param assignExceptionsForm
	 * @param assignExceptionSession
	 * 
	 */
	public void updateExceptions(
			AssignExceptionsForm assignExceptionsForm,
			AssignExceptionSession assignExceptionSession){
		log.entering(CLASS_NAME, "updateExceptions");
		ArrayList<FlownMailExceptionVO> exceptionVOs = new ArrayList<FlownMailExceptionVO>(
				assignExceptionSession.getExceptions());
		FlownMailExceptionVO exceptionVO = null;		
		LocalDate date=new LocalDate(NO_STATION,NONE,false);
		String[] flags = assignExceptionsForm.getOperationFlag();
		String[] assigneeCodes = assignExceptionsForm.getAsigneeCodes();
		int index=0;
		for(String flag:flags) {
			if(flag.equals(FlownMailExceptionVO.OPERATION_FLAG_UPDATE)) {
				exceptionVO = exceptionVOs.get(index);
				exceptionVO.setAssigneeCode(assigneeCodes[index].toUpperCase());
				
				if((EMPTY).equals(assigneeCodes[index])) {
					exceptionVO.setAssignedDate(null);
				}
				else {
					exceptionVO.setAssignedDate(date);
				}
				exceptionVO.setOperationFlag(
						FlownMailExceptionVO.OPERATION_FLAG_UPDATE);
			}
			index++;
		}
		log.log(Log.INFO, "Updated Exceptions>>>>", exceptionVOs);
		log.exiting(CLASS_NAME, "updateExceptions");
	}
		
	
}
