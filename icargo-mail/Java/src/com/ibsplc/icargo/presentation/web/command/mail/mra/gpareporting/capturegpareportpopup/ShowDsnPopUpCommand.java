/*
 * ShowDsnPopUpCommand.java Created on Oct-23-2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved. *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 *  
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.capturegpareportpopup;
/**
 * @author a-3447
 */
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.CaptureGPAReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.CaptureGPAReportForm;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;

/**
 *  /* Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Oct-23-2008 a-3447 Created
 */

public class ShowDsnPopUpCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("Mailtracking MRA");

	private static final String CLASS_NAME = "ShowDsnPopUpCommand";
	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String MODULE_NAME = "mailtracking.mra";
	private static final String DSNPOPUP_SCREENID = "mailtracking.mra.defaults.dsnselectpopup";
	private static final String SCREENID = "mailtracking.mra.gpareporting.capturegpareport";

	private static final String ACTION_SUCCESS = "action_success";
	private static final String FALSE="false";
	private static final String TRUE="true";

	/**
	 * *Oct-23-2008,a-3447
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 *             Execute Method for showing Dsn Pop UP
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, MODULE_NAME);
		Page<DSNPopUpVO> despatchLovVOs = null;
		CaptureGPAReportSession captureGPAReportSession = (CaptureGPAReportSession) getScreenSession(
				MODULE_NAME, SCREENID);
		DSNPopUpSession dSNPopUpSession = getScreenSession(MODULE,DSNPOPUP_SCREENID);
		CaptureGPAReportForm captureGPAReportForm = (CaptureGPAReportForm) invocationContext.screenModel;
		//String dsnDate = null;
		//String dsnNo = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<DSNPopUpVO> despatchLovVOss = new ArrayList<DSNPopUpVO>();
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		String dsnNum="";
		String dsnDate="";
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		GPAReportingDetailsVO gpaReportingDetailsVO= captureGPAReportSession.getSelectedGPAReportingDetailsVO();
		captureGPAReportForm.setDsnFlag("true");
		if(captureGPAReportForm.getDsn()!=null && captureGPAReportForm.getDsn().trim().length()>0){
			dsnNum=captureGPAReportForm.getDsn().toUpperCase();
		}else{
			
			captureGPAReportForm.setShowDsnPopUp(FALSE);
			//invocationContext.target = ACTION_SUCCESS;
		}
		/*if(captureGPAReportForm.getDate()!=null && captureGPAReportForm.getDate().trim().length()>0){
			dsnDate=captureGPAReportForm.getDate().toUpperCase();
		}*/

	if(!(captureGPAReportForm.getShowDsnPopUp().equals(FALSE))){
		log.log(Log.INFO, "dsnNum", dsnNum);
		try {
			despatchLovVOs = mailTrackingMRADelegate.findDsnSelectLov(
					logonAttributes.getCompanyCode(), dsnNum, dsnDate, 1);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}

		for(DSNPopUpVO dSNPopUpVO :despatchLovVOs){
			despatchLovVOss.add(dSNPopUpVO);


		}
		
		if(despatchLovVOss==null){
			log.log(Log.INFO, "null case");
			captureGPAReportForm.setShowDsnPopUp(FALSE);
			invocationContext.target = ACTION_SUCCESS;
			return;
		} 
		else {
			if (despatchLovVOss.size() == 1) {
				//Collection<GPAReportingDetailsVO> modifiedDetailsVOs=new ArrayList<GPAReportingDetailsVO>();
				for(DSNPopUpVO dSNPopUpVO :despatchLovVOss){
					log.log(Log.INFO, "size is one ", gpaReportingDetailsVO.getPoaCode());
					//Commented by A-3434 for INTMRA404 
					//if(gpaReportingDetailsVO.getPoaCode().equals(dSNPopUpVO.getGpaCode())){
					gpaReportingDetailsVO.setConsignementDocNo(dSNPopUpVO.getCsgdocnum());	
					gpaReportingDetailsVO.setConsignmentSeqNo(dSNPopUpVO.getCsgseqnum());
					gpaReportingDetailsVO.setPoaCode(dSNPopUpVO.getGpaCode());
					gpaReportingDetailsVO.setBillingBasis(dSNPopUpVO.getBlgBasis());
					//}else{
						
						//gpaReportingDetailsVO.setConsignmentSeqNo(-1);
					//}
					//modifiedDetailsVOs.add(gpaReportingDetailsVO);
				}
				log.log(Log.INFO, "size is one  ", despatchLovVOss);
				captureGPAReportForm.setShowDsnPopUp(FALSE);
				invocationContext.target = ACTION_SUCCESS;
			}
			
			if(despatchLovVOss.size() == 0){
				log.log(Log.INFO, "size is zero  ", despatchLovVOss.size());
				gpaReportingDetailsVO.setConsignmentSeqNo(-1);
				
			}
			if( despatchLovVOss.size() > 1){
				log.log(Log.INFO, "Size >1", despatchLovVOss);
				captureGPAReportForm.setShowDsnPopUp(TRUE);
				log.log(Log.INFO, "dSNPopUpSession", dSNPopUpSession.getSelectedDespatchDetails());
				invocationContext.target = ACTION_SUCCESS;

			}
		
		}
	}
		invocationContext.target = ACTION_SUCCESS;
	}
}



