/*
 * ListDSNDetailCommand.java Created on Jul 17, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.despatchenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.DSNPopUpSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.DespatchEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3429 DEEPTHI.E.S
 * 
 */
public class ListDSNDetailCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("RATE AUDITDETAILS");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID_DSN_POPUP = "mailtracking.mra.defaults.dsnselectpopup";

	private static final String FLAG_TRUE = "TRUE";

	private static final String FLAG_FALSE = "FALSE";

	private static final String SHOW_NO_POPUP = "show_no_popup";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String KEY_IMPORT_EXP = "mailtracking.mra.defaults.dsnselectpopup.nodetailsdatafound";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.despatchenquiry.noresultsfound";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListDSNDetailCommand", "excecute");

		DespatchEnquiryForm despatchEnquiryForm = (DespatchEnquiryForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		DSNPopUpSession dsnPopUpSession = getScreenSession(MODULE,
				SCREENID_DSN_POPUP);
		dsnPopUpSession.setSelectedDespatchDetails(null);
		dsnPopUpSession.setDsnPopUpFilterDetails(null);
		dsnPopUpSession.setDespatchDetails(null);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
		Page<DSNPopUpVO> despatchLovVOs = null;
		DSNPopUpVO dsnPopUpVO = new DSNPopUpVO();
		String companyCode = logonAttributes.getCompanyCode();
		String dsnNumber = despatchEnquiryForm.getDespatchNum();
		String dsnDate = despatchEnquiryForm.getDespatchDate();
		String fromScreen = "despatchenq";
		int pageNumber = 1;

		DSNPopUpFilterVO dsnPopUpFilterVO = new DSNPopUpFilterVO();
		dsnPopUpFilterVO.setCompanyCode(companyCode);
		dsnPopUpFilterVO.setDsn(dsnNumber);
		dsnPopUpFilterVO.setDsnDate("");
		dsnPopUpFilterVO.setFromScreen(fromScreen);
		dsnPopUpFilterVO.setPageNumber(pageNumber);

		if(dsnNumber!=null && dsnNumber.trim().length()>0){
			/*
			 * Code Length can be a dynamic value.
			 * It can be a 4 digit DSN number or a 20 character Billing Basis.
			 * In both the case we need to take the dsn number,when billing basis 
			 * is given,then take the last four digits,that represents the dsn.
			 */
			int codeLength = dsnNumber.length();
			if(codeLength == 4) {
				dsnNumber = dsnNumber.toUpperCase();
				dsnPopUpFilterVO.setDsn(dsnNumber);
			}else if(codeLength == 20){
				dsnNumber = dsnNumber.toUpperCase().substring(16, 20);
				dsnPopUpFilterVO.setDsn(dsnNumber);
			}
		}
		try {
			despatchLovVOs = mailTrackingMRADelegate.findDsnSelectLov(
					companyCode, dsnNumber, dsnDate, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (despatchLovVOs != null && despatchLovVOs.size() > 0) {
			/*
			 * IF ONLY ONE DESPATCH IS RETIREVED THEN NO NEED TO SHOW THE LOV
			 * JUST KEEP IT IN SESSION AND TAKE IT.
			 */
			if (despatchLovVOs.size() == 1) {
				for (DSNPopUpVO dsnPopUp : despatchLovVOs) {
					try {
						BeanHelper.copyProperties(dsnPopUpVO, dsnPopUp);
					} catch (SystemException e) {
						log
								.log(Log.FINE,
										"--------------DSN POPUP VO CLONING FAILED ---------");
					}
				}
				if (dsnPopUpVO.getBillingDetailsCount() == 0) {
					log
							.log(Log.INFO,
									" NO ENTRY IN BILLING DETAILS TABLE : ERROR IN IMPORTING DATA");
					errors = new ArrayList<ErrorVO>();
					errors.add(new ErrorVO(KEY_IMPORT_EXP));
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					despatchEnquiryForm.setShowDsnPopUp(FLAG_FALSE);
					dsnPopUpSession.removeDsnPopUpFilterDetails();
					return;
				}
				dsnPopUpSession.setSelectedDespatchDetails(dsnPopUpVO);
				despatchEnquiryForm.setShowDsnPopUp(FLAG_FALSE);
				dsnPopUpSession.removeDsnPopUpFilterDetails();
				invocationContext.target = SHOW_NO_POPUP;
			} else {
				dsnPopUpSession.setDsnPopUpFilterDetails(dsnPopUpFilterVO);
				despatchEnquiryForm.setShowDsnPopUp(FLAG_TRUE);
				invocationContext.target = LIST_SUCCESS;
			}
		} else {
			log.log(Log.INFO, "despatchLovVOs null");
			errors = new ArrayList<ErrorVO>();
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		log.exiting("ListDSNDetailCommand", "excecute");
	}
}