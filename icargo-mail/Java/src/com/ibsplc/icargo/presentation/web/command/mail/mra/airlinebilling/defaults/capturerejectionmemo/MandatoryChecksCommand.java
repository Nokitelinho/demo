/*
 * MandatoryChecksCommand.java Created on Feb 21,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.defaults.capturerejectionmemo;

import static com.ibsplc.xibase.server.framework.vo.ErrorDisplayType.ERROR;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.MemoFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.airlinebilling.defaults.CaptureRejectionMemoSession;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for checking for mandatory fields.
 *
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1        Feb 21,2007   Ruby  Abraham 		        Initial draft
 */
public class MandatoryChecksCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA AIRLINEBILLING DEFAULTS");
	
	private static final String CLASS_NAME = "MandatoryChecksCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";
	
	private static final String SCREEN_ID = "mailtracking.mra.airlinebilling.defaults.capturerejectionmemo";
	
	/**
	 * Execute method
	 *
	 * @param invocationContext InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
											throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		CaptureRejectionMemoSession  captureRejectionMemoSession = 
			(CaptureRejectionMemoSession)getScreenSession(MODULE_NAME, SCREEN_ID);
		
		Collection<ErrorVO> errors=null;
		
		errors = validateForm(captureRejectionMemoSession);
		if(errors != null && errors.size()>0){
			invocationContext.addAllError(errors);	
			
		}
		
		log.exiting(CLASS_NAME, "execute");
	}
	
	/**
	 * 
	 * @param captureRejectionMemoSession
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(CaptureRejectionMemoSession  captureRejectionMemoSession){
	
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("MandatoryChecksCommand","validateForm");
		ErrorVO error = null;
		MemoFilterVO memoFilterVO = captureRejectionMemoSession.getMemoFilterVO();				
		
		if( (memoFilterVO.getAirlineCodeFilter() == null) ||
				memoFilterVO.getAirlineCodeFilter().trim().length() == 0){
			error = new ErrorVO("mailtracking.mra.airlinebilling.defaults.capturerejectionmemo.msg.err.airlinecode.mandatory");
			error.setErrorDisplayType(ERROR);
			errors.add(error);			
		}			
		

		log.exiting("MandatoryChecksCommand","validateForm");
		return errors;
	}
}