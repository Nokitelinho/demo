/*
 * DeleteCommand.java Created on Feb 24, 2007
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
public class DeleteCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "DeleteCommand";
	
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
		
		if (gpaReportingDetailsVOs != null && (gpaReportingDetailsVOs.size()>0)) {	
			
			log.log(Log.FINE,"gpaReportingDetailsVOs is not null");
			
			gpaReportingDetailsVOs.remove(session.getSelectedGPAReportingDetailsVO());			
		if (gpaReportingDetailsVOs != null && (gpaReportingDetailsVOs.size()>0)) {
			log.log(Log.FINE,"gpaReportingDetailsVOs after deleting the selected VO is not null");
			session.setSelectedGPAReportingDetailsVO(((ArrayList<GPAReportingDetailsVO>) gpaReportingDetailsVOs).get(0));	
			
		}else{
			log.log(Log.FINE,"gpaReportingDetailsVOs after deleting the selected VO is null");
			form.setPopUpStatusFlag("");
			form.setScreenFlag("mainScreen");
		}
		
	}else{
		log.log(Log.FINE,"gpaReportingDetailsVOs is null");
		form.setPopUpStatusFlag("");
		form.setScreenFlag("mainScreen");
	}
	log.log(Log.FINE, "session.getSelectedVO", session.getSelectedGPAReportingDetailsVO());
	String gpaselect=session.getSelectedGPAReportingDetailsVO().getDsnDateForDisplay()
		+ ""
		+ session.getSelectedGPAReportingDetailsVO().getBillingBasis();
	form.setGpaselect(gpaselect);
	log.log(Log.INFO, "form.getGpaselect()", form.getGpaselect());
	invocationContext.target =ACTION_SUCCESS;  	
		log.exiting(CLASS_NAME, "execute");

	}

}
