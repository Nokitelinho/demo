/*
 * ListCommand.java Created on Sep 1, 2010
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldserviceabilitymaster;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDServiceabilityVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDServiceabilityMasterSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDServiceabilityForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 *
 */
public class ListCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String SCREENID = "uld.defaults.uldserviceability";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("ListCommand");

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		log.entering("ListCommand---------------->>>>", "Entering");
		String companyCode = logonAttributes.getCompanyCode();
		ULDServiceabilityForm form = (ULDServiceabilityForm) invocationContext.screenModel;
		ULDServiceabilityMasterSession session = getScreenSession(MODULE,
				SCREENID);
		String partyType = form.getPartyType().toUpperCase();
		session.setPartyTypeValue(partyType);
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();

		Collection<ULDServiceabilityVO> uldServiceabilityVOs = new ArrayList<ULDServiceabilityVO>();

		log.log(Log.FINE, "before setting to delegate--------->>>>>>>>>>>>>>");
		log.log(Log.FINE, "partyType-------->>>", partyType);
		session.setFacilityCode(null);
		Collection<ErrorVO> exceptions = new ArrayList<ErrorVO>();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {

			uldServiceabilityVOs = delegate.listULDServiceability(companyCode,
					partyType);
			log
					.log(
							Log.FINE,
							"uldServiceabilityVOs getting from delegate--------->>>>>>>>>>>>>>",
							uldServiceabilityVOs);
			session.setULDServiceabilityVOs(uldServiceabilityVOs);
		} catch (BusinessDelegateException e) {
			e.getMessage();
			exceptions = handleDelegateException(e);
		}
		if (uldServiceabilityVOs == null || uldServiceabilityVOs.size() == 0) {
			form.setAfterList("Listed");
			invocationContext.addError(new ErrorVO(
					"uld.defaults.uldserviceability.msg.err.noresults", null));
			invocationContext.addAllError(errors);
			invocationContext.target = LIST_FAILURE;
			session.setULDServiceabilityVOs(null);
			log.log(Log.ALL, "session.getULDAirportLocationVOs()", session.getULDServiceabilityVOs());
			return;
		} else {
			form.setAfterList("Listed");
			invocationContext.target = LIST_SUCCESS;
		}

	}
}
