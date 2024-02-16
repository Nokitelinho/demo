/*
 * ListULDIntMvtCommand.java Created on Mar 26, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldintmvthistory;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDIntMvtHistoryFilterVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ULDIntMvtHistorySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDIntMvtHistoryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-2412
 * This method is called on List click from the Internal ULD Movement Screen
 * 
 */
public class ListULDIntMvtCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ULD_MOVEMENT_HISTORY");

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE = "uld.defaults";

	private static final String SCREENID = "uld.defaults.misc.uldintmvthistory";
	//added by a-3045 for uld378 on 30Apr08
	private static final String BLANK = "";
	//ends by a-3045 on 30Apr08

	/**
	 * @param invocationContext	
	 * @return
	 * @throws CommandInvocationException 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ULDIntMvtHistoryFilterVO uldIntMvtFilterVO = new ULDIntMvtHistoryFilterVO();
		ULDIntMvtHistoryForm form = (ULDIntMvtHistoryForm) invocationContext.screenModel;
		ULDIntMvtHistorySession session = getScreenSession(MODULE, SCREENID);
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		// Update session with latest values from Form
		updateFilterVO(form, uldIntMvtFilterVO, companyCode);
		log.log(Log.INFO, "after updating---->>", uldIntMvtFilterVO);
		session.setULDIntMvtHistoryFilterVO(uldIntMvtFilterVO);

		// Validate ULD Number format and From ,To Date
		errors = validateForm(form, session, companyCode);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			form.setAfterList(BLANK);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		Page<ULDIntMvtDetailVO> uldIntMvtDetailVOs = new Page<ULDIntMvtDetailVO>(
				new ArrayList<ULDIntMvtDetailVO>(), 0, 0, 0, 0, 0, false);

		int pageNumber = Integer.parseInt(form.getDisplayPage());

		// Check if ULD exists in system
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		ULDValidationVO uldValidationVO = new ULDValidationVO();
		String uldNumber = form.getUldNumber();

		try {
			uldValidationVO = uldDefaultsDelegate.validateULD(companyCode,
					uldNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE, "inside try...caught businessDelegateException");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}

		if (uldValidationVO == null) {
			ErrorVO err = new ErrorVO(
					"uld.defaults.uldintmvthistory.msg.err.ulddoesnotexist");
			errors.add(err);
			invocationContext.addAllError(errors);
			form.setAfterList(BLANK);
			invocationContext.target = LIST_FAILURE;
			return;
		}

		// Find the movement details
		try {
			uldIntMvtDetailVOs = uldDefaultsDelegate.findIntULDMovementHistory(
					uldIntMvtFilterVO, pageNumber);
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		session.setIntULDMvtDetails(uldIntMvtDetailVOs);
		log.log(Log.INFO, "uldIntMvtDetailVOs returned ---->",
				uldIntMvtDetailVOs);
		if (uldIntMvtDetailVOs == null || uldIntMvtDetailVOs.size() == 0) {
			ErrorVO err = new ErrorVO(
					"uld.defaults.uldintmvthistory.msg.err.norecords");
			errors.add(err);
			invocationContext.addAllError(errors);
			form.setAfterList(BLANK);
			invocationContext.target = LIST_FAILURE;
			return;
		}
		log.log(Log.INFO, "uldValidationVO.getLocation()--->", uldValidationVO);
		if (uldValidationVO.getLocation() != null) {
			form.setCurrentLocation(uldValidationVO.getLocation());
		}
		if (uldValidationVO.getOwnerAirlineCode() != null) {
			form.setOwnerCode(uldValidationVO.getOwnerAirlineCode());
		}
		if (uldValidationVO.getOwnerStation() != null) {
			form.setOwnerStation(uldValidationVO.getOwnerStation());
		}
		form.setAfterList("Listed");
		invocationContext.target = LIST_SUCCESS;
	}

	private void updateFilterVO(ULDIntMvtHistoryForm form,
			ULDIntMvtHistoryFilterVO uldIntMvtFilterVO, String companyCode) {
		log.entering("ListULDIntMvtCommand", "updateFilterVO");
		uldIntMvtFilterVO.setCompanyCode(companyCode);
		String uldNumber = form.getUldNumber().toUpperCase();
		if (uldNumber != null && uldNumber.trim().length() > 0) {
			uldIntMvtFilterVO.setUldNumber(uldNumber);
		} else {
			uldIntMvtFilterVO.setUldNumber("");
		}

		if (form.getFromDate() != null
				&& form.getFromDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getFromDate(), "dd-MMM-yyyy")) {
				LocalDate frmDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				frmDate.setDate(form.getFromDate());
				uldIntMvtFilterVO.setFromDate(frmDate);
			}

		} else {
			uldIntMvtFilterVO.setFromDate(null);
		}
		if (form.getToDate() != null && form.getToDate().trim().length() > 0) {
			if (DateUtilities.isValidDate(form.getToDate(), "dd-MMM-yyyy")) {
				LocalDate toDate = new LocalDate(getApplicationSession()
						.getLogonVO().getAirportCode(), Location.ARP, false);
				toDate.setDate(form.getToDate());
				uldIntMvtFilterVO.setToDate(toDate);
			}

		} else {
			uldIntMvtFilterVO.setToDate(null);
		}
		log.exiting("List Internal ULDMovementHistoryCommand",
				"uldIntMvtFilterVO");
	}

	private Collection<ErrorVO> validateForm(ULDIntMvtHistoryForm form,
			ULDIntMvtHistorySession session, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		log.entering("ListIntMvtCommand--->", "validateForm");		
		if (form.getUldNumber() != null
				&& form.getUldNumber().trim().length() > 0) {
			try {
				new ULDDefaultsDelegate().validateULDFormat(companyCode, form
						.getUldNumber().toUpperCase());

			} catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}

		if (((!("").equals(form.getFromDate())) && form.getFromDate() != null)
				&& ((!("").equals(form.getToDate())) && form.getToDate() != null)) {
			if (!form.getFromDate().equals(form.getToDate())) {
				if (!DateUtilities.isLessThan(form.getFromDate(), form
						.getToDate(), "dd-MMM-yyyy")) {
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.uldintmvthistory.fromdateearliertodate");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}

		log.exiting("ListIntMvtCommand", "validateForm");
		return errors;
	}

}
