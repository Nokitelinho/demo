/*
 * ClearCommand.java Created on Feb 22, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
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
public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "ClearCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/*
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
		log.entering(CLASS_NAME, "execute");

		CaptureGPAReportSession session = 
			(CaptureGPAReportSession)getScreenSession(
													MODULE_NAME, SCREENID);			
		CaptureGPAReportForm form = 
			(CaptureGPAReportForm)invocationContext.screenModel;
		
		session.setGPAReportingDetailsPage(null);
		
		//session.setGPAReportingDetailsVOs(null);
		
		session.setGPAReportingFilterVO(new GPAReportingFilterVO());
		
		session.setModifiedGPAReportingDetailsVOs(null);
		
		session.setSelectedGPAReportingDetailsVO(null);
		
		session.setIndexMap(null);
		
		form.setAllProcessed(GPAReportingDetailsVO.FLAG_YES);
		form.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		form.setBasistype("");
		
		form.setGpaselect("");
		
		invocationContext.target = ACTION_SUCCESS;
				
		log.exiting(CLASS_NAME, "execute");

	}

}
