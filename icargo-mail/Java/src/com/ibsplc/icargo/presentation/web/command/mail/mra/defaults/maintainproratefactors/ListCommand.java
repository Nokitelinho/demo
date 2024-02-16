/*
 * ListCommand.java Created on Oct 31, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.maintainproratefactors;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_DETAIL;
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import static com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION;
import static com.ibsplc.icargo.framework.util.time.Location.NONE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFactorVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MaintainMraProrateFactorsSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MaintainMraProrateFactorsForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * Revision History
 * 
 * Version Date Author Description
 * 
 * 0.1 Oct 31, 2006 Rani Rose John Initial draft
 */
public class ListCommand extends BaseCommand {
	/**
	 * Logger and the file name
	 */
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS");

	private static final String CLASS_NAME = "ListCommand";

	private static final String MODULE_NAME = "mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.maintainproratefactors";

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String LINKSTATUS = "enable";

	private static final String NOT_MODIFIED = "N";

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
		if (invocationContext.getErrors() == null
				|| invocationContext.getErrors().size() == 0) {
			Collection<ErrorVO> errors = null;
			ProrationFactorFilterVO prorationFactorFilterVO = null;
			errors = checkMandatory(maintainProrateFactorsForm);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				maintainProrateFactorsForm
						.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
				invocationContext.target = LIST_FAILURE;
			} else {
				prorationFactorFilterVO = populateFilter(
						maintainProrateFactorsForm,
						maintainProrateFactorsSession);
				Collection<ProrationFactorVO> prorateFactorVOs = null;
				MailTrackingMRADelegate mailTrackingMRADelegate = new MailTrackingMRADelegate();
				log.log(Log.INFO, "VO to server>>>>>", prorationFactorFilterVO);
				try {
					prorateFactorVOs = mailTrackingMRADelegate
							.findProrationFactors(prorationFactorFilterVO);
				} catch (BusinessDelegateException businessDelegateException) {
					handleDelegateException(businessDelegateException);
				}
				log.log(Log.INFO, "VOs from server>>>>>", prorateFactorVOs);
				if (!(prorateFactorVOs != null && prorateFactorVOs.size() > 0)) {
					log.log(Log.FINE, "\n\n\n\n resultset empty");
					maintainProrateFactorsForm.setNoResultFlag(FLAG_YES);
					invocationContext.target = LIST_SUCCESS;
				} else {
					for (ProrationFactorVO prorationFactorVO : prorateFactorVOs) {
						prorationFactorVO.setOperationFlag(NOT_MODIFIED);
					}
					maintainProrateFactorsSession
							.setFactors((ArrayList<ProrationFactorVO>) prorateFactorVOs);
					maintainProrateFactorsSession
							.setFilterDetails(prorationFactorFilterVO);
					// viewExceptionsForm.setStatusFlag("listsuccess");
					maintainProrateFactorsForm.setLinkStatusFlag(LINKSTATUS);

					invocationContext.target = LIST_SUCCESS;
				}
				maintainProrateFactorsForm
						.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			}
		} else {
			maintainProrateFactorsForm
					.setScreenStatusFlag(SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_FAILURE;
		}

		log.exiting(CLASS_NAME, "execute");

	}

	/**
	 * Method to check mandatory
	 * 
	 * @param maintainProrateFactorsForm
	 * @return
	 */
	private Collection<ErrorVO> checkMandatory(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm) {
		// TODO Auto-generated method stub
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if ((maintainProrateFactorsForm.getOriginCityCode() == null || maintainProrateFactorsForm
				.getOriginCityCode().trim().length() == 0)) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.mra.defaults.maintainproratefactors.msg.err.citycodemandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);

		}
		if ((maintainProrateFactorsForm.getOriginCityName() == null || maintainProrateFactorsForm
				.getOriginCityName().trim().length() == 0)) {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.mra.defaults.maintainproratefactors.msg.err.citynamemandatory");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		return errors;
	}

	/**
	 * Method to populate filter fields
	 * 
	 * @param maintainProrateFactorsForm
	 * @param maintainProrateFactorsSession
	 */
	private ProrationFactorFilterVO populateFilter(
			MaintainMraProrateFactorsForm maintainProrateFactorsForm,
			MaintainMraProrateFactorsSession maintainProrateFactorsSession) {
		// TODO Auto-generated method stub
		ProrationFactorFilterVO prorationFactorFilterVO = new ProrationFactorFilterVO();
		prorationFactorFilterVO.setCompanyCode(getApplicationSession()
				.getLogonVO().getCompanyCode());
		if ((maintainProrateFactorsForm.getOriginCityCode() != null && maintainProrateFactorsForm
				.getOriginCityCode().trim().length() > 0)) {
			prorationFactorFilterVO
					.setOriginCityCode(maintainProrateFactorsForm
							.getOriginCityCode().toUpperCase());
		}
		if ((maintainProrateFactorsForm.getOriginCityName() != null && maintainProrateFactorsForm
				.getOriginCityName().trim().length() > 0)) {
			prorationFactorFilterVO
					.setOriginCityName(maintainProrateFactorsForm
							.getOriginCityName().toUpperCase());
		}
		if ((maintainProrateFactorsForm.getDestCityCode() != null && maintainProrateFactorsForm
				.getDestCityCode().trim().length() > 0)) {
			prorationFactorFilterVO
					.setDestinationCityCode(maintainProrateFactorsForm
							.getDestCityCode().toUpperCase());
		}
		if ((maintainProrateFactorsForm.getDestCityName() != null && maintainProrateFactorsForm
				.getDestCityName().trim().length() > 0)) {
			prorationFactorFilterVO
					.setDestinationCityName(maintainProrateFactorsForm
							.getDestCityName().toUpperCase());
		}
		if ((maintainProrateFactorsForm.getStatusFilter() != null && maintainProrateFactorsForm
				.getStatusFilter().trim().length() > 0)) {
			prorationFactorFilterVO
					.setProrationFactorStatus(maintainProrateFactorsForm
							.getStatusFilter().toUpperCase());
		}
		if ((maintainProrateFactorsForm.getSourceFilter() != null && maintainProrateFactorsForm
				.getSourceFilter().trim().length() > 0)) {
			prorationFactorFilterVO
					.setProrationFactorSource(maintainProrateFactorsForm
							.getSourceFilter().toUpperCase());
		}
		if (maintainProrateFactorsForm.getEffFromDate() != null
				&& maintainProrateFactorsForm.getEffFromDate().trim().length() > 0) {
			LocalDate fromDate = new LocalDate(NO_STATION, NONE, false);
			fromDate.setDate(maintainProrateFactorsForm.getEffFromDate());
			prorationFactorFilterVO.setFromDate(fromDate);

		}
		if (maintainProrateFactorsForm.getEffToDate() != null
				&& maintainProrateFactorsForm.getEffToDate().trim().length() > 0) {
			LocalDate toDate = new LocalDate(NO_STATION, NONE, false);
			toDate.setDate(maintainProrateFactorsForm.getEffToDate());
			prorationFactorFilterVO.setToDate(toDate);
		}

		maintainProrateFactorsSession.setFilterDetails(prorationFactorFilterVO);
		return prorationFactorFilterVO;
	}
}
