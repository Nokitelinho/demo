/*
 * ModifyCommand.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareport;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
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
 * 0.1     		  Feb 23, 2007			a-2257		Created
 */
public class ModifyCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");
	
	private static final String CLASS_NAME = "ModifyCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra";
	
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";
	
	/**
	 * constant for invoking popup screen
	 */
	private static final String POPUP = "POPUP";
	
	private static final String MODIFY = "MODIFY";
		
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
		
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		//Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = session.getGPAReportingDetailsVOs();
		
		Collection<GPAReportingDetailsVO> gpaReportingDetailsVOs = session.getGPAReportingDetailsPage();
		
		Collection<GPAReportingDetailsVO> newGpaReportingDetailsVOs = new ArrayList<GPAReportingDetailsVO>();
				
		String[] checked = form.getSelectedRows().split(",");	

		//log.log(Log.FINE, "checkedRow" + checked);
		
		for (int i = 0; i < checked.length; i++) {	
			int indexOfChecked=0;
			for(GPAReportingDetailsVO gpaRepDetVO : gpaReportingDetailsVOs){
				if(!GPAReportingDetailsVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(gpaRepDetVO.getOperationFlag())){
					if(indexOfChecked==Integer.parseInt(checked[i])){
												
					log.log(Log.FINE, "gpaRepDetVO", gpaRepDetVO);
					if("A".equals(gpaRepDetVO.getReportingStatus())
							|| "P".equals(gpaRepDetVO.getReportingStatus())){
						
						errors.add(new ErrorVO("mailtraking.mra.gpareport.capturegpareportpopup.aleadyprocessed"));
						invocationContext.addAllError(errors);
						form.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
						invocationContext.target = ACTION_SUCCESS;						
						log.exiting(CLASS_NAME, "execute");
						
						return;
						
					}
					
					newGpaReportingDetailsVOs.add(gpaRepDetVO);
					
					}
					indexOfChecked++;
				}
			}
		}
		
		session.setModifiedGPAReportingDetailsVOs(newGpaReportingDetailsVOs);
		log.log(Log.FINE, "newGpaReportingDetailsVOs",
				newGpaReportingDetailsVOs);
		GPAReportingDetailsVO selectedVO =(
				(ArrayList<GPAReportingDetailsVO>)session.getModifiedGPAReportingDetailsVOs()).get(0);
		
		session.setSelectedGPAReportingDetailsVO(selectedVO);
		
		log.log(Log.FINE, "session.setSelectedVO", session.getSelectedGPAReportingDetailsVO());
		String gpaselect=session.getSelectedGPAReportingDetailsVO().getDsnDateForDisplay()
				+ ""
				+ session.getSelectedGPAReportingDetailsVO().getBillingBasis();
		form.setGpaselect(gpaselect);
		log.log(Log.INFO, "form.getGpaselect()", form.getGpaselect());
		form.setScreenFlag(POPUP);
		form.setPopUpStatusFlag(MODIFY);
		log.log(Log.INFO, "form.getScreenFlag()-->", form.getScreenFlag());
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute"); 
	 }
	}
