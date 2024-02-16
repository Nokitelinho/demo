/*
 * SelectVOCommand.java Created on Feb 24, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
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
 * 0.1     		  Feb 24, 2007			a-2257		Created
 */
public class SelectVOCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "SelectVOCommand";
	
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
			
		Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = session.getModifiedGPAReportingDetailsVOs();
		
		if (invocationContext.getErrors() != null
				&& invocationContext.getErrors().size() > 0) {			
			invocationContext.target = ACTION_SUCCESS;
			log.exiting(CLASS_NAME, "execute");
			return;
		} 
		int pageNo = Integer.parseInt(form.getDisplayPopUpPage());
		log.log(Log.INFO, "pageNo-->", pageNo);
		if(gpaReportingDetailsVOs!=null){
			session.setSelectedGPAReportingDetailsVO(((ArrayList<GPAReportingDetailsVO>)gpaReportingDetailsVOs).get(pageNo));				
		 }	
		log.log(Log.INFO, "session.setSelectedVO-->", session.getSelectedGPAReportingDetailsVO());
		String gpaselect=session.getSelectedGPAReportingDetailsVO().getDsnDateForDisplay()
									+ ""
									+ session.getSelectedGPAReportingDetailsVO().getBillingBasis();
		form.setGpaselect(gpaselect);
		log.log(Log.INFO, "form.getGpaselect()", form.getGpaselect());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");

	}

}
