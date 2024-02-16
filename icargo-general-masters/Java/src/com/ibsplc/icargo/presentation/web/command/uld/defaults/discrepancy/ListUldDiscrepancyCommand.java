/*
 * ListUldDiscrepancyCommand.java Created on Aprl, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.discrepancy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.ListUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.ListULDDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */
public class ListUldDiscrepancyCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String LIST_FAILURE = "list_failure";

	private static final String SCREENID = "uld.defaults.listulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("ListUldDiscrepancyCommand");

	private static final String BLANK = "";	
	
	// added by a-3278 for bug 24473 on 07Nov08
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";
	private static final String DISCREPANCY_STATUS = "uld.defaults.discrepancyCode";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("ListUldDiscrepancyCommand", "ENTRY");

		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ListULDDiscrepanciesForm form = (ListULDDiscrepanciesForm) invocationContext.screenModel;
		ListUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		String companyCode = logonAttributes.getCompanyCode();
		AirlineValidationVO airlineValidationVO = null;
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		//added by a-3278 for bug 24473 on 07Nov08
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		session.setOneTimeValues(oneTimeValues);
		//added by a-3278 for bug 24473 on 07Nov08 ends
		form.setButtonStatusFlag("");
		ULDDiscrepancyFilterVO uldDiscrepancyFilterVO = new ULDDiscrepancyFilterVO();

		if (session.getULDDiscrepancyFilterVODetails() != null
				&& !"Y".equals(form.getFromList())) {
			uldDiscrepancyFilterVO = session.getULDDiscrepancyFilterVODetails();
		} else {
			
			if (form.getUldNo() != null && form.getUldNo().trim().length() > 0) {
				uldDiscrepancyFilterVO.setUldNumber(form.getUldNo()
						.toUpperCase().trim());
			}else{
				if(session.getUldNumber() != null && session.getUldNumber().trim().length() > 0
						&& session.getMode() != null && session.getMode().trim().length() > 0){
					if(("modifymode").equals(session.getMode())){
						uldDiscrepancyFilterVO.setUldNumber(session.getUldNumber());
					}
				}
				session.setUldNumber("");
				session.setMode("");
			}
			if (form.getReportingStation() != null
					&& form.getReportingStation().trim().length() > 0) {
				uldDiscrepancyFilterVO.setReportingStation(form
						.getReportingStation().toUpperCase().trim());
			}
			if (form.getOwnerStation() != null
					&& form.getOwnerStation().trim().length() > 0) {
				uldDiscrepancyFilterVO.setOwnerStation(form.getOwnerStation()
						.toUpperCase().trim());
			}
			if (form.getDiscrepancyStatus() != null
					&& form.getDiscrepancyStatus().trim().length() > 0) {
				uldDiscrepancyFilterVO.setDiscrepancyStatus(form.getDiscrepancyStatus()
						.toUpperCase().trim());
			} 
			if (form.getAirlineCode() != null
					&& form.getAirlineCode().trim().length() > 0) {
				uldDiscrepancyFilterVO.setAirlineCode(form.getAirlineCode()
						.toUpperCase().trim());
			}
			session.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
			/*if (form.getUldNo() != null && form.getUldNo().trim().length() > 0) {
				try {
					delegate.validateULDFormat(companyCode, form.getUldNo()
							.toUpperCase());

				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				if (errors != null && errors.size() > 0) {
					form.setUldNo("");
					uldDiscrepancyFilterVO.setUldNumber("");
					session
							.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
					session.removeULDDiscrepancyVODetails();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}*/
			if (form.getUldNo() != null
					&& form.getUldNo().trim().length() > 0) {
				errors = validateULDCode(form, companyCode);
				if (errors != null && errors.size() > 0) {
					form.setUldNo("");
					uldDiscrepancyFilterVO.setUldNumber("");
					session
							.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
					session.removeULDDiscrepancyVODetails();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			if (form.getAirlineCode() != null
					&& form.getAirlineCode().trim().length() > 0) {
				String airlineCode = form.getAirlineCode().toUpperCase();
				airlineValidationVO = validateAirlineCode(form, companyCode);
				if (airlineValidationVO == null) {
					log
							.log(Log.FINE,
									"airlineValidationVO == null-------->>>>");
					errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					Object[] obj = { airlineCode };
					error = new ErrorVO(
							"uld.defaults.listulddiscrepancies.msg.err.invalidawbprefix",
							obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
					form.setAirlineCode("");
					uldDiscrepancyFilterVO.setAirlineCode("");
				} else {
					uldDiscrepancyFilterVO
							.setUldOwnerIdentifier(airlineValidationVO
									.getAirlineIdentifier());
					log.log(Log.FINE,
							"filterVO.getMasterOwnerIdentifier()------->>>>",
							uldDiscrepancyFilterVO
									.getUldOwnerIdentifier());
					session.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
				}
				if (errors != null && errors.size() > 0) {
					session
							.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
					session.removeULDDiscrepancyVODetails();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			if (form.getOwnerStation() != null
					&& form.getOwnerStation().trim().length() > 0) {
				errors = validateOwnerStation(form, companyCode);
				if (errors != null && errors.size() > 0) {
					form.setOwnerStation("");
					uldDiscrepancyFilterVO.setOwnerStation("");
					session
							.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
					session.removeULDDiscrepancyVODetails();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}
			if (form.getReportingStation() != null
					&& form.getReportingStation().trim().length() > 0) {
				errors = validateReportingStationCode(form, companyCode);
				if (errors != null && errors.size() > 0) {
					uldDiscrepancyFilterVO.setReportingStation("");
					session
							.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
					session.removeULDDiscrepancyVODetails();
					invocationContext.addAllError(errors);
					invocationContext.target = LIST_FAILURE;
					return;
				}
			}

			uldDiscrepancyFilterVO.setCompanyCode(companyCode);
			log.log(Log.FINE, "form.getFromList()---> ", form.getFromList());
			if ("Y".equals(form.getFromList())) {
				//uldDiscrepancyFilterVO.setPageNumber(1);
				form.setFromList("");
			} else {
				//uldDiscrepancyFilterVO.setPageNumber(Integer.parseInt(form
						//.getDisplayPage()));
			}
		}
		Page<ULDDiscrepancyVO> uldDiscrepancyVOs = null;
		uldDiscrepancyFilterVO.setPageNumber(Integer.parseInt(form
				.getDisplayPage()));
		session.setULDDiscrepancyFilterVODetails(uldDiscrepancyFilterVO);
		log.log(Log.FINE, " session.setULDDiscrepancyFilterVODetails", session.getULDDiscrepancyFilterVODetails());
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		
		log.log(Log.INFO, "navigationMode: ", form.getNavigationMode());
		log.log(Log.INFO, "lastPageNumber: ", form.getLastPageNum());
		if(ListULDDiscrepanciesForm.NAV_MOD_LIST.equalsIgnoreCase(form.getNavigationMode())){
			log.log(Log.INFO, "list action");
			uldDiscrepancyFilterVO.setTotalRecordsCount(-1);
			uldDiscrepancyFilterVO.setPageNumber(1);
		}else if(ListULDDiscrepanciesForm.NAV_MOD_PAGINATION.equalsIgnoreCase(form.getNavigationMode())||form.getNavigationMode() == null){
			log.log(Log.INFO, "navigation action");
			if( session !=null ){
				uldDiscrepancyFilterVO.setTotalRecordsCount(session.getTotalRecordCount());
			}
			if(form.getDisplayPage() != null && form.getDisplayPage().trim().length() > 0 ){
				uldDiscrepancyFilterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
			}
		}
		log.log(Log.INFO, "pageNumber: ", uldDiscrepancyFilterVO.getPageNumber());
		try {
			uldDiscrepancyVOs = delegate
					.listULDDiscrepancyDetails(uldDiscrepancyFilterVO);
		} catch (BusinessDelegateException e) {			
			e.getMessage();
			error = handleDelegateException(e);
		}
		
		if (uldDiscrepancyVOs != null && uldDiscrepancyVOs.size() > 0) {
			//Modified by A-5220 for ICRD-22824 ends
			if(session != null){
				session.setULDDiscrepancyVODetails(uldDiscrepancyVOs);
				session.setTotalRecordCount(uldDiscrepancyVOs.getTotalRecordCount());
			}
			//Modified by A-5220 for ICRD-22824 ends
			form.setFromList("");
			form.setListStatus("afterlist");
			invocationContext.target = LIST_SUCCESS;
			return;
		} else {
			Collection<ErrorVO> errorscol = null;
			errorscol = validateUldDiscrepancyVOs(uldDiscrepancyVOs);
			if (errorscol != null && errorscol.size() > 0) {
				session.removeULDDiscrepancyVODetails();
				log.log(Log.FINE, "exception");
				invocationContext.addAllError(errorscol);
				form.setListStatus("afterlist");
				invocationContext.target = LIST_FAILURE;
				return;
			}
		}
		log.exiting("ListUldDiscrepancyCommand", "EXIT");
	}

	private Collection<ErrorVO> validateUldDiscrepancyVOs(
			Collection<ULDDiscrepancyVO> vos) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO errorVO = new ErrorVO(
				"uld.defaults.listulddiscrepancies.norecordsfound");
		errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
		errors.add(errorVO);
		return errors;
	}

	private Collection<ErrorVO> validateReportingStationCode(
			ListULDDiscrepanciesForm form, String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getReportingStation()
					.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}

	private Collection<ErrorVO> validateOwnerStation(
			ListULDDiscrepanciesForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getOwnerStation()
					.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
	}

	private AirlineValidationVO validateAirlineCode(
			ListULDDiscrepanciesForm form, String companyCode) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AirlineValidationVO airlineValidationVO = null;
		try {
			AirlineDelegate delegate = new AirlineDelegate();
			airlineValidationVO = delegate.validateAlphaCode(logonAttributes
					.getCompanyCode().toUpperCase(), form.getAirlineCode()
					.toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return airlineValidationVO;
	}
	
	private Collection<ErrorVO> validateULDCode(
			ListULDDiscrepanciesForm form, String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		ULDValidationVO uldValidationVO = null;
		try {
			uldValidationVO = delegate.validateULD(logonAttributes
					.getCompanyCode().toUpperCase(), form
					.getUldNo().toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		if(uldValidationVO==null){
			ErrorVO errorVO = new ErrorVO(
				"uld.defaults.listulddiscrepancies.invaliduld");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		return errors;
	}
	
	/**
	 * @author a-3278
	 * for bug 24473 on 07Nov08
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("ListUldDiscrepancyCommand","getOneTimeValues");
		/*
		 * Obtain the logonAttributes
		 */
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		/*
		 * the shared defaults delegate
		 */
		SharedDefaultsDelegate sharedDefaultsDelegate =
			new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> oneTimeValues = null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		try {
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(),
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("ListUldDiscrepancyCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
	/**
	 * @author a-3278
	 * for bug 24473 on 07Nov08
	 * Method to populate the collection of
	 * onetime parameters to be obtained
     * @return parameterTypes
     */
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("ListUldDiscrepancyCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();    	
    	parameterTypes.add(FACILITY_TYPE);    	
    	//DISCREPANCY_STATUS
    	parameterTypes.add(DISCREPANCY_STATUS);  
    	return parameterTypes;
    }

}
