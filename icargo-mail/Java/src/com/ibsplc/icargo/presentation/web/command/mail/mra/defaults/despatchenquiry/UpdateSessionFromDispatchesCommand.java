/*
 * UpdateSessionFromDispatchesCommand.java Created on Aug 14, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;



import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.UnaccountedDispatchesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;


/**
 * @author A-2107
 *
 */
public class UpdateSessionFromDispatchesCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	private static final String CLASS_NAME = "UpdateSessionFromDispatchesCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.unaccounteddispatches";

	private static final String SCREENFORMENQUIRY_ID = "mailtracking.mra.defaults.despatchenquiry";
	
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";




	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		
		UnaccountedDispatchesSession  unaccountedDispatchesSession = 
			(UnaccountedDispatchesSession) getScreenSession(MODULE_NAME, SCREEN_ID);
		
		DespatchEnqSession  despatchEnqSession = 
			(DespatchEnqSession) getScreenSession(MODULE_NAME, SCREENFORMENQUIRY_ID);
		
		DespatchEnquiryForm despatchEnquiryForm = 
			(DespatchEnquiryForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		DSNPopUpVO despatchEnquiryVO  = null ;
		if(despatchEnquiryForm.getSelectedDispatch() != null && 
				despatchEnquiryForm.getSelectedDispatch().length()>0){
			String[] selectedDetails = despatchEnquiryForm.getSelectedDispatch().trim().split("-");
			despatchEnquiryVO  = new DSNPopUpVO();
			despatchEnquiryVO.setCompanyCode(companyCode);
			despatchEnquiryVO.setCsgdocnum(selectedDetails[0]);
			StringBuilder sbd = new StringBuilder();
			String dsnDate = sbd.append(selectedDetails[1]).append("-").append(selectedDetails[2]).append("-").append(selectedDetails[3]).toString();
			despatchEnquiryVO.setDsnDate(dsnDate);
			despatchEnquiryVO.setBlgBasis(selectedDetails[4]);
			despatchEnquiryVO.setCsgseqnum(Integer.parseInt(selectedDetails[5]));
			despatchEnquiryVO.setGpaCode(selectedDetails[6]);
			despatchEnquiryVO.setDsn(despatchEnquiryVO.getBlgBasis().substring(16, 20));
			despatchEnquiryForm.setConDocNum(selectedDetails[0]);			
			log.log(Log.FINE, "FilterVO", despatchEnquiryVO);
			despatchEnqSession.setDispatchFilterVO(despatchEnquiryVO);
			invocationContext.target=SUCCESS;
		}else {
			invocationContext.target=FAILURE;
		}
		log.log(Log.FINE, "FilterVO", despatchEnquiryVO);
		despatchEnquiryForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
		log.exiting(CLASS_NAME, "execute");
	}
}
