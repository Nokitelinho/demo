/*
 * DisplayCommand.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.upucalendar.maintainupucalendar;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.UPUCalendarVO;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.UPUCalendarSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.UPUCalendarForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2521
 *
 */
public class DisplayCommand extends BaseCommand {
	
	private  Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
		
	private static final String CLASS_NAME = "DisplayCommand";
		
	private static final String MODULE_NAME = "mailtracking.mra";

	 private static final String SCREENID =
		 "mailtracking.mra.airlinebilling.defaults.upucalendar";
	
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String ERROR_KEY_NO_INVALID_DATE = "mra.airlinebilling.defaults.upucalendar.notvaliddate";
	
	private static final String ERROR_KEY_NO_RESULTS = "mra.airlinebilling.defaults.upucalendar.noresultsfound";
	
	private static final String BLANK =  "" ;
	
	/**
	 * Method  implementing listing of clerance periods
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
										throws CommandInvocationException {
		
		String fromDate = null;
		String toDate = null;
		ErrorVO error = null;
		log.entering(CLASS_NAME,"execute");
		
		
		Collection<UPUCalendarVO> upuCalendarVOs = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();	
		
		UPUCalendarForm upuCalendarForm = (UPUCalendarForm)invocationContext.screenModel;
		
		fromDate = upuCalendarForm.getFromDateLst();
		toDate	= upuCalendarForm.getToDateLst();
		
		if( !(validateDate( fromDate, toDate )) ){
			
			error = new ErrorVO( ERROR_KEY_NO_INVALID_DATE );
			errors.add(error);
			invocationContext.addAllError(errors);
			upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
			return;
		}		

		UPUCalendarSession upuCalendarSession = (UPUCalendarSession)getScreenSession(
																MODULE_NAME, SCREENID);
		upuCalendarSession.removeUPUCalendarVOs();
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
				
		UPUCalendarFilterVO upuCalendarFilterVO = new UPUCalendarFilterVO();
		upuCalendarFilterVO.setCompanyCode(companyCode);
		
		if( fromDate != null && !BLANK.equals(fromDate)){
			
			upuCalendarFilterVO.setFromDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
					setDate( fromDate ));
		}
		
		if( toDate != null && !BLANK.equals(toDate)){
			
			upuCalendarFilterVO.setToDate( new LocalDate(LocalDate.NO_STATION,Location.NONE, false).
					setDate( toDate));
		}
		
		log.log(Log.FINE,
				"\n\n\n****************** UPUCalendar Filter VO \n\n\n",
				upuCalendarFilterVO);
		try {
			
			upuCalendarVOs = new MailTrackingMRADelegate().displayUPUCalendarDetails( upuCalendarFilterVO );
			
		} catch (BusinessDelegateException e) {
			
			e.getMessage();
			errors=handleDelegateException( e );
		}
		
		if(upuCalendarVOs == null || upuCalendarVOs.size() == 0){
			
			error = new ErrorVO(ERROR_KEY_NO_RESULTS);
			errors.add(error);
			invocationContext.addAllError(errors);
			//upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
					
		}else {
			upuCalendarSession.setUPUCalendarVOs( upuCalendarVOs );
			upuCalendarForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;
		}
	}
	
	/**
	 * validating fromdate and todate 
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){
		
		if( ((toDate != null)&&(toDate.trim().length()>0)) && 
				((fromDate != null) &&(fromDate.trim().length()>0))) {
			
			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );
			
		}else{
			
			return true;
		}
	}
	
}
