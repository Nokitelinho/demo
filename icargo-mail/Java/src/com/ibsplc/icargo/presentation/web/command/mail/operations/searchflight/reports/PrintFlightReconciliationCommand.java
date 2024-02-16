/*
 * PrintFlightReconciliationCommand.java Created on Sep 08 2009
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of 
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.searchflight.reports;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.FlightReconciliationSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.FlightReconciliationForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

/**
 * @author a-3817
 * 
 */
public class PrintFlightReconciliationCommand extends AbstractPrintCommand {
	private static final String MODULE_NAME = "mail.operations";

	private static final String SCREENID = "mailtracking.defaults.flightreconcilation";

	private static final String ACTION = "printMailsForReconciliation";

	private static final String PRODUCT_CODE = "mail";

	private static final String SUBPRODUCT_CODE = "operations";

	private static final String BUNDLE = "flightreconcilationResources";
	
	private static final String REPORT_ID="04MTK005";
	
	private static final String PARAMETER_KEY_REC_EXC = "mailtracking.defaults.reconcileexceptions";
	
	private static final String PARAMETER_KEY_REC_STA = "mailtracking.defaults.reconcilestatus";

	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		FlightReconciliationSession flightReconciliationSession = (FlightReconciliationSession) getScreenSession(
				MODULE_NAME, SCREENID);
		FlightReconciliationForm flightReconciliationForm = (FlightReconciliationForm) invocationContext.screenModel;
		OperationalFlightVO operationalFlightVO = flightReconciliationSession
				.getOperationalFlightVO();
		ReportSpec reportSpec = getReportSpec();
		Collection<String> parameters = new ArrayList<String>();
		parameters.add(PARAMETER_KEY_REC_EXC);
		parameters.add(PARAMETER_KEY_REC_STA);
		LogonAttributes logonAttributes=getApplicationSession().getLogonVO();
		HashMap<String, Collection<OneTimeVO>>oneTimeMap=getOnetimes(logonAttributes.getCompanyCode(), parameters);
		Collection<OneTimeVO>exceptionStatusVOs=oneTimeMap.get(PARAMETER_KEY_REC_EXC);
		Collection<OneTimeVO>reconcileStatusVOs=oneTimeMap.get(PARAMETER_KEY_REC_STA);
		
		reportSpec.setAction(ACTION);
		reportSpec.setProductCode(PRODUCT_CODE);
		reportSpec.setSubProductCode(SUBPRODUCT_CODE);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setPreview(true);
		reportSpec.addFilterValue(operationalFlightVO);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.setResourceBundle(BUNDLE);
		reportSpec.addExtraInfo(flightReconciliationForm.getFlightMessage());
		reportSpec.addExtraInfo(exceptionStatusVOs);
		reportSpec.addExtraInfo(reconcileStatusVOs);
		generateReport();
		invocationContext.target = getTargetPage();

	}
	/**
	 * The method to obtain the onetime values. The method will call the
	 * sharedDefaults delegate and returns the map of requested onetimes
	 * 
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOnetimes(
			String companyCode, Collection<String> parameters) {
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		Collection<ErrorVO> errors = null;
		try {
			oneTimeMap = new SharedDefaultsDelegate().findOneTimeValues(
					companyCode, parameters);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		return (HashMap<String, Collection<OneTimeVO>>) oneTimeMap;
	}

}
