/*
 * UpdateDiscrepancyCommand.java Created on 29 Sep, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3278 
 * This command class is to populate the uld details on changing
 * the discrepency code to 'Missing'
 */
public class UpdateDiscrepancyCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("UPDATE DISCREPENCY");

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private static final String UPDATE_SUCCESS = "update_success";

	private static final String UPDATE_FAILURE = "update_failure";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("UpdateDiscrepancyCommand", "ENTRY");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MaintainUldDiscrepanciesForm form = (MaintainUldDiscrepanciesForm) invocationContext.screenModel;
		String companyCode = logonAttributes.getCompanyCode();
		LocalDate date = new LocalDate(getApplicationSession().getLogonVO()
				.getAirportCode(), Location.ARP, true);
		String discrepencyDateString = TimeConvertor.toStringFormat(date
				.toCalendar(), "dd-MMM-yyyy");

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		if (form.getUldNoChild() != null
				&& form.getUldNoChild().trim().length() > 0) {
			errors = validateULDCode(form, companyCode);
			log.log(Log.INFO, "errors------------->", errors);
			if (errors != null && errors.size() > 0) {
				log.log(Log.INFO, "errors--not null----------->");
				invocationContext.addAllError(errors);
				invocationContext.target = UPDATE_FAILURE;
				return;
			}
		}
		ULDVO uldVO = null;
		try {

			uldVO = new ULDDefaultsDelegate().findULDDetails(companyCode, form
					.getUldNoChild().toUpperCase());

		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.FINE, "uldVO is ----------->", uldVO);
		if (uldVO == null) {
			log.log(Log.FINE, "!!!!!uldVO is null ");			
		} else {
			if (uldVO.getUldNumber() != null) {
				form.setUldNoChild(uldVO.getUldNumber());
			}
			if (discrepencyDateString != null) {
				form.setDiscrepancyDate(discrepencyDateString);
			}
			if (uldVO.getCurrentStation() != null) {
				form.setReportingStationChild(uldVO.getCurrentStation());
			}
			//Added as part of bug 108294 by A-3767 on 06Apr11
			if (uldVO.getLocation() != null
					&& !("NIL").equals(uldVO.getLocation())) {
				form.setLocation(uldVO.getLocation());
			}
			//Added as part of bug 108294 by A-3767 on 06Apr11
			if (uldVO.getFacilityType() != null
					&& !("NIL").equals(uldVO.getFacilityType())) {
				form.setFacilityType(uldVO.getFacilityType());
			}
			form.setListFlag("Y");
		}		
		invocationContext.target = UPDATE_SUCCESS;
		return;

	}

	private Collection<ErrorVO> validateULDCode(MaintainUldDiscrepanciesForm form,
			String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDValidationVO uldValidationVO = null;
		try {
			uldValidationVO = delegate.validateULD(logonAttributes
					.getCompanyCode().toUpperCase(), form.getUldNoChild()
					.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		log.log(Log.FINE, "uldValidationVO--------->", uldValidationVO);
		if (uldValidationVO == null) {
			log.log(Log.FINE,"uldValidationVO-- null-------");
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.listulddiscrepancies.invaliduld");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		
		return errors;
	}

}
