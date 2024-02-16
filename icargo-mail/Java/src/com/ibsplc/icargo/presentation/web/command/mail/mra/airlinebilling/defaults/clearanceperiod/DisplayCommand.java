/*
 * DisplayCommand.java Created on Sep 11, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.clearanceperiod;

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
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.defaults.ClearancePeriodLovForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2521
 *
 */
public class DisplayCommand extends BaseCommand {
	
	private  Log log = LogFactory.getLogger("CRA_AIRLINEBILLING");
		
	private static final String CLASS_NAME = "DisplayCommand";
		
	private static final String LIST_SUCCESS = "list_success";
	
	private static final String LIST_FAILURE = "list_failure";
	
	private static final String BLANK =  "" ;
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
										throws CommandInvocationException {
		
		log.entering(CLASS_NAME,"execute");
		
		String fromDate = null;
		String toDate = null;
		String clearancePeriod = null;
		Collection<UPUCalendarVO> upuCalendarVOs = null;
		
		ClearancePeriodLovForm clearancePeriodLovForm 	= (ClearancePeriodLovForm)invocationContext.screenModel;
		
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
				
		UPUCalendarFilterVO upuCalendarFilterVO = new UPUCalendarFilterVO();
		upuCalendarFilterVO.setCompanyCode(companyCode);
		
		fromDate = clearancePeriodLovForm.getFromDateLst();
		toDate = clearancePeriodLovForm.getToDateLst();
		clearancePeriod = clearancePeriodLovForm.getCode();
		
		log.log(Log.FINE, "\n\n\n****************** UPUCalendar Filter VO ",
				upuCalendarFilterVO);
		if( fromDate != null && !BLANK.equals(fromDate)){
			
			upuCalendarFilterVO.setFromDate( new LocalDate(LocalDate.NO_STATION,Location.NONE,false).
					setDate( fromDate ));
		}
		
		if( toDate != null && !BLANK.equals(toDate)){
			
			upuCalendarFilterVO.setToDate( new LocalDate(LocalDate.NO_STATION,Location.NONE, false).
					setDate( toDate));
		}
		
		if( clearancePeriod != null && !BLANK.equals(clearancePeriod)){
			
			upuCalendarFilterVO.setClearancePeriod(clearancePeriod);
		}
		
		try {
			
			upuCalendarVOs = new MailTrackingMRADelegate().displayUPUCalendarDetails( upuCalendarFilterVO );
			
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
			e.getMessage();
		}
		
		if(upuCalendarVOs == null || upuCalendarVOs.size() == 0){
			
			clearancePeriodLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = LIST_FAILURE;
					
		}else {
			clearancePeriodLovForm.setUpucalendarVOs( upuCalendarVOs );
			clearancePeriodLovForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;
		}
	}
	
}
