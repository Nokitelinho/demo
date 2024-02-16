/*
 * ScreenLoadCommand.java Created on Apr 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 * Use is subject to license terms.
 * 
 */

package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.versionnumberlov;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailContractVersionLOVVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.VersionNumberLOVForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2518 
 * 
 */

public class ScreenLoadCommand extends BaseCommand {

	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		String contractReferenceNumber = null;
		String vesionNumber = null;
		Collection<MailContractVersionLOVVO> versionNumbers = null;
		VersionNumberLOVForm versionNumberLovForm = (VersionNumberLOVForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		contractReferenceNumber = versionNumberLovForm.getName();
		vesionNumber = versionNumberLovForm.getCode();
		if (contractReferenceNumber != null
				&& contractReferenceNumber.trim().length() > 0) {
			contractReferenceNumber = contractReferenceNumber.trim()
					.toUpperCase();
		}
		if (vesionNumber != null && vesionNumber.trim().length() > 0) {
			vesionNumber = vesionNumber.trim().toUpperCase();
		}
		try {
			versionNumbers = new MailTrackingMRADelegate().displayVersionLov(
					companyCode, contractReferenceNumber, vesionNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}
		if (versionNumbers != null) {
			log.log(Log.INFO, "<----------- RESULT FROM SERVER ----------->");
			for (MailContractVersionLOVVO mailContractVersionLovVo : versionNumbers) {
				log.log(Log.FINE, mailContractVersionLovVo.toString());
			}
		}
		versionNumberLovForm.setVersionNumbers(versionNumbers);
		invocationContext.target = SCREENLOAD_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}
}
