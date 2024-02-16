/*
 * MandatoryChecksCommand.java Created on Feb 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

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
 * 0.1     		  Feb 22, 2007			a-2257		Created
 */
public class MandatoryChecksCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "MandatoryChecksCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";	
	
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
		
		log.entering(CLASS_NAME, "execute");
		
		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);	
		GPAReportingFilterVO gpaReportingFilterVO = session.getGPAReportingFilterVO();
		
		Collection<ErrorVO> errors = validatFilter(gpaReportingFilterVO);	
		
		if(errors != null && errors.size()>0){
			
			log.log(Log.INFO,"**************Inside Errors**************");
			
			invocationContext.addAllError(errors);	
			
			return;
			
		}		
		log.exiting(CLASS_NAME, "execute");

	}
	private Collection<ErrorVO> validatFilter(GPAReportingFilterVO filterVO){
		
		log.entering(CLASS_NAME, "validatFilter");
				
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		ErrorVO error = null;
		
		if(filterVO.getPoaCode()==null || filterVO.getPoaCode().trim().length()==0){
			
			log.log(Log.INFO,"**************Inside 1 Error**************");
			error = new ErrorVO("mailtraking.mra.gpareport.capturegpareport.gpacode.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		if(filterVO.getReportingPeriodFrom()==null ){
			
			log.log(Log.INFO,"**************Inside 3 Error**************");
			error = new ErrorVO("mailtraking.mra.gpareport.capturegpareport.fromdate.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(filterVO.getReportingPeriodTo()==null ){
			
			log.log(Log.INFO,"**************Inside 4 Error**************");
			error = new ErrorVO("mailtraking.mra.gpareport.capturegpareport.todate.mandatory");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		if ((filterVO.getReportingPeriodFrom()!= null)
				&& (!("").equals(filterVO.getReportingPeriodFrom().toString()))){
				
				if ((filterVO.getReportingPeriodTo()!= null)
						&&(!("").equals(filterVO.getReportingPeriodTo().toString()))){
						
					if(filterVO.getReportingPeriodFrom().isGreaterThan(filterVO.getReportingPeriodTo())){
							log.log(Log.INFO,"**************Inside 5 Error**************");
							ErrorVO errorVO = new ErrorVO(
							 "mailtraking.mra.gpareport.capturegpareport.fromdategreaterthantodate");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(errorVO);
					}
				}
		}
		log.exiting(CLASS_NAME, "validatFilter");
		return errors;
		
	}
}
