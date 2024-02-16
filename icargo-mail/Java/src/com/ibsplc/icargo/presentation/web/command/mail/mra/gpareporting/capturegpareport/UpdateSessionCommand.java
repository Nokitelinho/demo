/*
 * UpdateSessionCommand.java Created on Feb 21, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *  
 * @author A-1739
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1     		  Feb 21, 2007			a-2257		Created
 */
public class UpdateSessionCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "UpdateSessionCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";	
	/**
	 * Target mappings for succes and failure
	 */
	private static final String ACTION_SUCCESS = "action_success";
	
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	public void execute(InvocationContext invocationContext)
					throws CommandInvocationException {
		log.entering("UpdateSessionCommand", "execute");
		log.entering(CLASS_NAME, "execute");
		
		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);	
		CaptureGPAReportForm form = 
			(CaptureGPAReportForm)invocationContext.screenModel;
		
		GPAReportingFilterVO gpaReportingFilterVO = updateFilter(form);
		
		session.setGPAReportingFilterVO(gpaReportingFilterVO);	
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		return;
		
	}
	/**
	 * 
	 * TODO Purpose
	 * Mar 11, 2007, a-2257
	 * @param form
	 * @return
	 */
	public GPAReportingFilterVO updateFilter(CaptureGPAReportForm form ){
		
		log.entering(CLASS_NAME, "updateFilter");
		
		GPAReportingFilterVO gpaReportingFilterVO = new GPAReportingFilterVO();
		
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		
		LocalDate toDate =  new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		
		if(form.getGpaCode() != null && 
				form.getGpaCode().trim().length() > 0){
			gpaReportingFilterVO.setPoaCode(
					form.getGpaCode().toUpperCase());
		    }else{
		    	gpaReportingFilterVO.setPoaCode(null);
		    }
		
		if(form.getGpaName() != null && 
				form.getGpaName().trim().length() > 0){
			gpaReportingFilterVO.setPoaName(
					form.getGpaName().toUpperCase());
		    }else{
		    	gpaReportingFilterVO.setPoaName(null);
		    }
		
		if(form.getCountry() != null && 
				form.getCountry().trim().length() > 0){
			gpaReportingFilterVO.setCountry(
					form.getCountry().toUpperCase());
		    }else{
		    	gpaReportingFilterVO.setCountry(null);
		    }
	
		if (form.getFrmDate() != null && form.getFrmDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFrmDate(), "dd-MMM-yyyy")) {

				fromDate.setDate(form.getFrmDate());
				gpaReportingFilterVO.setReportingPeriodFrom(fromDate);
			}
		} else {
			gpaReportingFilterVO.setReportingPeriodFrom(null);
		}
		
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {

				toDate.setDate(form.getToDate());
				gpaReportingFilterVO.setReportingPeriodTo(toDate);
			}
		} else {
			gpaReportingFilterVO.setReportingPeriodTo(null);
		}
		log.exiting(CLASS_NAME, "updateFilter");
		
		
		return gpaReportingFilterVO;
	}

	}
