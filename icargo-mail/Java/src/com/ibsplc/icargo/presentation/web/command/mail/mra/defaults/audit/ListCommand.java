/*
 * ListCommand.java Created on Aug 5, 2015
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.audit;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingmatrixAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author A-5255 
 * @version	0.1, Aug 5, 2015
 * 
 *
 */
/**
 * Revision History Revision Date Author Description 0.1 Aug 5, 2015 A-5255
 * First draft
 */

public class ListCommand extends BaseCommand {
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	private static final String SHARED_MODULE="shared.audit";
	private static final String SHARED_SCREENID="shared.audit.auditenquiry";
	private static final String SCREENID = "mailtracking.mra.defaults.billingmatrixaudit";
	private static final String SUCCESS = "success";
	private static final String F = "success";
	private static final String TXNFRMDATEERROR = "shared.audit.auditenquiry.msg.err.txnfrmdateerror";
	private static final String TXNTODATEERROR = "shared.audit.auditenquiry.msg.err.txntodateerror";

	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		BillingmatrixAuditForm form = (BillingmatrixAuditForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ErrorVO error;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if ((form.getTxnFromDate() == null)
				|| (form.getTxnFromDate().trim().length() == 0)) {
			error = new ErrorVO(TXNFRMDATEERROR);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ((form.getTxnToDate() == null)
				|| (form.getTxnToDate().trim().length() == 0)) {
			error = new ErrorVO(TXNTODATEERROR);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if ((errors != null) && (errors.size() > 0)) {
			invocationContext.addAllError(errors);
			invocationContext.target = SUCCESS;
			return;
		}
	
		AuditEnquirySession auditEnquirySession = (AuditEnquirySession) getScreenSession(
				SHARED_MODULE, SHARED_SCREENID);
		/*
		 * Setting BillingMatrixFilterVO Filter
		 */
		BillingMatrixFilterVO blgMatrixFilterVO = new BillingMatrixFilterVO();
		blgMatrixFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		blgMatrixFilterVO.setBillingMatrixId(form.getBlgMatrixID());
		LocalDate fromDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
	    LocalDate toDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, false);
	    blgMatrixFilterVO.setTxnFromDate(fromDate.setDate(form.getTxnFromDate()));
	    blgMatrixFilterVO.setTxnToDate(toDate.setDate(form.getTxnToDate()));
	    
		Collection<AuditDetailsVO> auditDetailsVOs = null;
		try {
			auditDetailsVOs = new MailTrackingMRADelegate()
					.findBillingMatrixAuditDetails(blgMatrixFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
		if (errors.size() > 0) {
			invocationContext.addAllError(errors);
		}

		invocationContext.target = SUCCESS;
	}
}
