/*
 * TempCustListingCommand.java Created on Dec 19, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.ListTempCustomerVO;
import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 * @author A-1496
 * 
 */
public class TempCustListingCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";

	private Log log = LogFactory.getLogger("TempCustListingCommand");

	private static final String BLANK = "";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListTempCustomerForm form = (ListTempCustomerForm) invocationContext.screenModel;
		ListtempCustomerSession session = getScreenSession(MODULE, SCREENID);
		String companyCode = logonAttributes.getCompanyCode();

		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ListTempCustomerVO listTempCustomerVO = new ListTempCustomerVO();
		Collection<TempCustomerVO> col = new ArrayList<TempCustomerVO>();

		log.log(Log.FINE, "session.getPageURL()---> ", session.getPageURL());
		log.log(Log.FINE, "form.getDetailsFlag()---> ", form.getDetailsFlag());
		log.log(Log.FINE, "form.getCloseStatus()---> ", form.getCloseStatus());
		/*
		 * if(("listtempcustomerform").equals(session.getPageURL())){
		 * 
		 * 
		 * log.log(Log.FINE, "session.getPageURL()---> " +
		 * session.getPageURL()); session.setPageURL("");
		 * 
		 * form.setScreenStatusFlag(
		 * ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		 * invocationContext.target = LIST_SUCCESS; return; }
		 */
		if (("detailsflag").equals(form.getDetailsFlag())) {

			log.log(Log.FINE, "form.getDetailsFlag()---> ", form.getDetailsFlag());
			form.setDetailsFlag("");

			form
					.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			invocationContext.target = LIST_SUCCESS;
			return;
		}

		if (("Y".equals(form.getCloseStatus()) || "fromCustomerRegn"
				.equals(session.getPageURL()))) {
			session.setPageURL("");

			listTempCustomerVO = session.getListTempCustomerDetails();

		} else {

			errors = validateDates(col, form);
			if (errors != null && errors.size() > 0) {
				log.log(Log.FINE, "validateDate-------------->>");
				// session.removeListTempCustomerDetails();
				session.removeListCustomerRegistration();
				invocationContext.addAllError(errors);
				invocationContext.target = LIST_FAILURE;
				return;
			}
			// ErrorVO error1=null;
			// Collection<ErrorVO> errorvos1=new ArrayList<ErrorVO>();
			if (form.getStation() != null
					&& form.getStation().trim().length() > 0) {
				errors = validateStationCode(form, companyCode);
				if (errors != null && errors.size() > 0) {
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}

			listTempCustomerVO.setCompanyCode(companyCode);
			if (form.getCustomerName() != null
					&& form.getCustomerName().trim().length() > 0) {
				listTempCustomerVO.setTempCustName(form.getCustomerName()
						.toUpperCase());
			}

			if (form.getStation() != null
					&& form.getStation().trim().length() > 0) {
				listTempCustomerVO.setStationCode(form.getStation()
						.toUpperCase());
			}
			if (form.getStatus() != null
					&& form.getStatus().trim().length() > 0) {
				listTempCustomerVO.setActiveStatus(form.getStatus()
						.toUpperCase());
			}
			if (form.getListTempId() != null
					&& form.getListTempId().trim().length() > 0) {
				listTempCustomerVO.setTempCustCode(form.getListTempId()
						.toUpperCase());
			}
			if (form.getListTempId() != null
					&& form.getListTempId().trim().length() > 0) {
				listTempCustomerVO.setTempCustCode(form.getListTempId()
						.toUpperCase());
			}
			if (form.getValidFrom() != null
					&& !BLANK.equals(form.getValidFrom())) {
				LocalDate date = new LocalDate(
						logonAttributes.getStationCode(), Location.STN, false);
				listTempCustomerVO.setFromDate(date
						.setDate(form.getValidFrom()));
			}
			if (form.getValidTo() != null && !BLANK.equals(form.getValidTo())) {
				LocalDate date = new LocalDate(
						logonAttributes.getStationCode(), Location.STN, false);
				listTempCustomerVO.setToDate(date.setDate(form.getValidTo()));
			}
			if (form.getStatus() != null && !BLANK.equals(form.getStatus())) {
				listTempCustomerVO.setActiveStatus(form.getStatus());
			}
			if (form.getCustomerCode() != null
					&& !BLANK.equals(form.getCustomerCode())) {
				listTempCustomerVO.setCustomerCode(form.getCustomerCode()
						.toUpperCase());
			}

			listTempCustomerVO.setDisplayPage(Integer.parseInt(form
					.getDisplayPageNum()));
		}
		

		Page<TempCustomerVO> tempcustomervos = null;
		session.setListTempCustomerDetails(listTempCustomerVO);
		
		log.log(Log.FINE, " session.setListTempCustomerDetails", session.getListTempCustomerDetails());
		if (listTempCustomerVO != null) {
			//Added by A-5218 to enable Last Link in Pagination to start
			if(ListTempCustomerForm.PAGINATION_MODE_FROM_FILTER.equals(
			        form.getPaginationMode())){
					listTempCustomerVO.setTotalRecordCount(-1);
				}
				else{
					listTempCustomerVO.setTotalRecordCount(
    			session.getTotalRecords().intValue());
				}
			//Added by A-5218 to enable Last Link in Pagination to end
			try {
				tempcustomervos = delegate
						.listTempCustomerDetails(listTempCustomerVO);

			} catch (BusinessDelegateException e) {
//printStackTrrace()();
				handleDelegateException(e);
			}

			if (tempcustomervos != null && tempcustomervos.size() > 0) {

				session.setListCustomerRegistration(tempcustomervos);
				session.setTotalRecords(
						tempcustomervos.getTotalRecordCount());
				form.setFromList("");
				invocationContext.target = LIST_SUCCESS;
				return;
			}

			else {
				Collection<ErrorVO> errorscol = null;
				errorscol = validateForm(tempcustomervos);
				if (errorscol != null && errorscol.size() > 0) {
					// session.removeListTempCustomerDetails();
					session.removeListCustomerRegistration();
					log.log(Log.FINE, "exception");
					invocationContext.addAllError(errorscol);
					invocationContext.target = LIST_FAILURE;
					return;

				}
			}
		} else {
			session.setListCustomerRegistration(null);
			form.setFromList("");
			invocationContext.target = LIST_SUCCESS;
			return;
		}
	}

	private Collection<ErrorVO> validateForm(Collection<TempCustomerVO> vos) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = new ErrorVO(
				"customermanagement.defaults.norecordsfound");
		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(errorVO);
		return errors;
	}

	/**
	 * 
	 * @param tempCustomerVOs
	 * @param form
	 * @return
	 */
	private Collection<ErrorVO> validateDates(
			Collection<TempCustomerVO> tempCustomerVOs,
			ListTempCustomerForm form) {
		log.entering("TempCustListingCommand", "validateDates");
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if ((!("").equals(form.getValidFrom()))
				&& (!("").equals(form.getValidTo()))) {
			if (!form.getValidFrom().equals(form.getValidTo())) {
				if (DateUtilities.isGreaterThan(form.getValidFrom(), form
						.getValidTo(), "dd-MMM-yyyy")) {
					ErrorVO errorVO = new ErrorVO(
							"customermanagement.defaults.msg.err.fromdategreaterthantodate");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(errorVO);
				}
			}
		}
		log.exiting("TempCustListingCommand", "validateDates");
		return errors;
	}

	/**
	 * 
	 * @param listTempCustomerForm
	 * @param companyCode
	 * @return
	 */
	private Collection<ErrorVO> validateStationCode(
			ListTempCustomerForm listTempCustomerForm, String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();

		AreaValidationVO areaValidationVO = null;
		try {
			AreaDelegate delegate = new AreaDelegate();

			areaValidationVO = delegate.validateLevel(logonAttributes
					.getCompanyCode().toUpperCase(), "STN",
					listTempCustomerForm.getStation().toUpperCase());

		} catch (BusinessDelegateException e) {

			errors = handleDelegateException(e);
		}

		return errors;
	}

}
