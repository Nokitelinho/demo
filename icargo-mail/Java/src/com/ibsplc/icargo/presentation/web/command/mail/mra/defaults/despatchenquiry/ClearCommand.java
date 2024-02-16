/*
 * ClearCommand.java Created on ul 10,2008
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DespatchEnqSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2391
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MRA DEFAULTS CLEARCOMMAND");
	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE = "mailtracking.mra.defaults";
	private static final String SCREENID = "mailtracking.mra.defaults.despatchenquiry";
	private static final String CLEAR_SUCCESS="clear_success";
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dsnselectpopup";
	/*
	 *  (non-Javadoc)
	 * @see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 */
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		log.entering(CLASS_NAME,"execute");
		DespatchEnquiryForm despatchEnquiryForm=(DespatchEnquiryForm)invocationContext.screenModel;
		DespatchEnqSession despatchEnqSession=getScreenSession(MODULE,SCREENID);
		DSNPopUpSession popUpSession=getScreenSession(MODULE,SCREEN_ID);

		despatchEnqSession.setDespatchEnquiryVO(new DespatchEnquiryVO());
		despatchEnquiryForm.setBlgBasis("");
		despatchEnquiryForm.setListed("NO");
		despatchEnquiryForm.setCurrency("");
		despatchEnquiryForm.setDespatchNum("");
		despatchEnquiryForm.setDsnFilterDate("");
		despatchEnquiryForm.setFinYear("");
		despatchEnquiryForm.setGpaCode("");
		despatchEnquiryForm.setGpaName("");
		despatchEnquiryForm.setLovClicked("");
		despatchEnquiryForm.setRemarks("");
		despatchEnquiryForm.setDespatchEnqTyp("G");
		despatchEnquiryForm.setAbsIndex("");
		despatchEnquiryForm.setPageNum("");
		despatchEnquiryForm.setLastPageNumber("");
		despatchEnquiryForm.setDisplayPage("1");
		
		despatchEnqSession.removeGPABillingDtls();
		despatchEnqSession.setAirlineBillingDetailsVO(null);
		despatchEnqSession.setFlownDetails(null);
		//despatchEnqSession.setAccountingDetails(null);
		//despatchEnqSession.setUSPSReportingDetails(null);
		//despatchEnqSession.setIndexMap(null);
		popUpSession.removeSelectedDespatchDetails();
		 invocationContext.target=CLEAR_SUCCESS;
		log.exiting(CLASS_NAME,"execute");

	}


}
