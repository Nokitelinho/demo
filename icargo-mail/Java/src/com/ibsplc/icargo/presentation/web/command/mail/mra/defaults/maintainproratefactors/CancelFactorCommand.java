/*
 * CancelFactorCommand.java Created on Dec 02, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class for clearing
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Dec 02, 2006 Rani Rose John Initial draft
 */
public class CancelFactorCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "ClearCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String CANCEL_SUCCESS = "cancel_success";

	/**
	 * Execute method
	 * 
	 * @param invocationContext
	 *            InvocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		MaintainMraProrateFactorsForm maintainProrateFactorsForm = (MaintainMraProrateFactorsForm) invocationContext.screenModel;
		MaintainMraProrateFactorsSession maintainProrateFactorsSession = (MaintainMraProrateFactorsSession) getScreenSession(
				MODULE_NAME, SCREEN_ID);
		updateDetails(maintainProrateFactorsForm, maintainProrateFactorsSession);
		String[] row = maintainProrateFactorsForm.getSelectedElements();
		ArrayList<ProrationFactorVO> prorateFactorVos = maintainProrateFactorsSession
				.getFactors();
		ProrationFactorVO prorateFactorVO = null;
		int index = 0;
		index = Integer.parseInt(row[0]);
		log.log(Log.INFO, "selected row>>>>", index);
		prorateFactorVO = prorateFactorVos.get(index);
		prorateFactorVO.setProrationFactorStatus("C");
		prorateFactorVO
				.setOperationFlag(ProrationFactorVO.OPERATION_FLAG_UPDATE);
		log.log(Log.INFO, "prorateFactorVO to server--->>", prorateFactorVO);
		try {
			MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
			mailTrackingMRADelegate
					.changeProrationFactorStatus(prorateFactorVO);
		} catch (BusinessDelegateException e) {
			handleDelegateException(e);
		}
		maintainProrateFactorsForm.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
		invocationContext.target = CANCEL_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
	}

	/**
	 * Method to update form
	 * 
	 * @param maintainProrateFactorsForm
	 * @param maintainProrateFactorsSession
	 */
	private void updateDetails(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm,
			MaintainMraProrateFactorsSession maintainProrateFactorsSession) {
		log.entering(CLASS_NAME, "updateDetails");
		log.log(Log.INFO, "Session before updation ",
				maintainProrateFactorsSession.getFactors());
		if (maintainProrateFactorsForm.getDestinationCityCode() != null
				&& maintainProrateFactorsForm.getDestinationCityCode().length > 0) {
			String[] cityCodes = maintainProrateFactorsForm
					.getDestinationCityCode();
			String[] cityNames = maintainProrateFactorsForm
					.getDestinationCityName();
			double[] factors = maintainProrateFactorsForm.getProrationFactor();
			String[] source = maintainProrateFactorsForm
					.getProrationFactorSource();
			String[] fromDate = maintainProrateFactorsForm.getFromDate();
			String[] toDate = maintainProrateFactorsForm.getToDate();
			String[] operationFlags = maintainProrateFactorsForm
					.getOperationFlag();
			ArrayList<ProrationFactorVO> insertPage = new ArrayList<ProrationFactorVO>();
			log.log(Log.INFO, "no of rows>>>>>>>>>>>>>>", cityCodes.length);
			ProrationFactorVO prorateFactorVO = null;
			for (int i = 0; i < cityCodes.length - 1; i++) {
				log.log(Log.INFO, "value of i>>>>>>>>>>>>>>", i);
				if (!(OPERATION_FLAG_DELETE.equals(operationFlags[i]) && "NOOP"
						.equals(operationFlags[i]))) {
					log
							.log(
									Log.INFO,
									"maintainProrateFactorsSession.getFactors().get(i)>>>>>>>>>>>>>>",
									maintainProrateFactorsSession
											.getFactors().get(i));
					prorateFactorVO = maintainProrateFactorsSession
							.getFactors().get(i);
					prorateFactorVO.setCompanyCode(getApplicationSession()
							.getLogonVO().getCompanyCode());
					log.log(Log.INFO, "cityCodes[i]>>>>>>>>>>>>>>", cityCodes,
							i);
					prorateFactorVO.setDestinationCityCode(cityCodes[i]
							.toUpperCase());
					log.log(Log.INFO, "cityNames[i]>>>>>>>>>>>>>>", cityNames,
							i);
					prorateFactorVO.setDestinationCityName(cityNames[i]
							.toUpperCase());
					log.log(Log.INFO, "factors[i]>>>>>>>>>>>>>>", factors, i);
					prorateFactorVO.setProrationFactor(factors[i]);
					log.log(Log.INFO, "source[i]>>>>>>>>>>>>>>", source, i);
					prorateFactorVO.setProrationFactorSource(source[i]
							.toUpperCase());
					log.log(Log.INFO, "going to update fromDates", fromDate, i);
					if (fromDate[i] != null && fromDate[i].trim().length() > 0) {
						LocalDate frmDate = new LocalDate(NO_STATION, NONE,
								false);
						frmDate.setDate(fromDate[i]);
						prorateFactorVO.setFromDate(frmDate);

					}
					log.log(Log.INFO, "going to update toDate", toDate, i);
					if (toDate[i] != null && toDate[i].trim().length() > 0) {
						LocalDate endDate = new LocalDate(NO_STATION, NONE,
								false);
						endDate.setDate(toDate[i]);
						prorateFactorVO.setToDate(endDate);
					}
					log.log(Log.INFO, "going to update getOriginCityCode",
							maintainProrateFactorsForm.getOriginCityCode());
					prorateFactorVO
							.setOriginCityCode(maintainProrateFactorsForm
									.getOriginCityCode());
					prorateFactorVO.setOperationFlag(operationFlags[i]);
					insertPage.add(prorateFactorVO);
				}
			}
			maintainProrateFactorsSession.setFactors(insertPage);
			log.log(Log.INFO, "Session after updation ",
					maintainProrateFactorsSession.getFactors());
			log.exiting(CLASS_NAME, "updateDetails");
		}

	}
}
