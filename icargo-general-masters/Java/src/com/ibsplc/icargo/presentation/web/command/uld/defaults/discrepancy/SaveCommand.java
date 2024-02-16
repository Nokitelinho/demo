/*
 * SaveCommand.java Created on Dec 19, 2005
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

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.MaintainUldDiscrepancySession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.MaintainUldDiscrepanciesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2052
 * 
 */

public class SaveCommand extends BaseCommand {

	private static final String SCREENID = "uld.defaults.maintainulddiscrepancies";

	private static final String MODULE = "uld.defaults";

	private Log log = LogFactory.getLogger("SaveCommand");

	private static final String SAVE_SUCCESS = "save_success";

	private static final String SAVEMAINTAIN_SUCCESS = "savemaintain_success";

	private static final String SAVE_FAILURE = "save_failure";
		
	private static final String DISCREPANCYCODE = "uld.defaults.discrepancyCode";
	
	private static final String FACILITY_TYPE = "uld.defaults.facilitytypes";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SaveCommand", "ENTRY");
		MaintainUldDiscrepanciesForm form = (MaintainUldDiscrepanciesForm) invocationContext.screenModel;
		MaintainUldDiscrepancySession session = getScreenSession(MODULE, SCREENID);
		ULDDiscrepancyVO uldDiscrepancyVO = new ULDDiscrepancyVO();
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companycode = logonAttributes.getCompanyCode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ULDDefaultsDelegate delegate = new ULDDefaultsDelegate();
		Collection<ULDDiscrepancyVO> uldDiscrepancyVOs = null;
		HashMap<String,Collection<OneTimeVO>> oneTimeValues = getOneTimeValues();
		session.setOneTimeValues(oneTimeValues);
		String uldnumberStation = null;
		String flag = null;
		if (form.getReportingStationChild() != null
				&& form.getReportingStationChild().trim().length() > 0) {
			errors = validateReportingStationCode(form, companycode);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}
		if (form.getUldNoChild() != null
				&& form.getUldNoChild().trim().length() > 0) {
			errors = validateULDCode(form, companycode);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}
		if (form.getDiscrepancyDate() != null
				&& form.getDiscrepancyDate().trim().length() > 0) {
			errors = validateDiscrepancyDate(form, companycode);
			if (errors != null && errors.size() > 0) {
				invocationContext.addAllError(errors);
				invocationContext.target = SAVE_FAILURE;
				return;
			}
		}
		/*
		 * if (form.getUldNoChild() != null &&
		 * form.getUldNoChild().trim().length() > 0) { try {
		 * delegate.validateULDFormat(companycode, form.getUldNoChild()
		 * .toUpperCase()); } catch (BusinessDelegateException e) { errors =
		 * handleDelegateException(e); } if (errors != null && errors.size() >
		 * 0) { invocationContext.addAllError(errors); invocationContext.target =
		 * SAVE_FAILURE; return; } }
		 */

		// For Fetching the polLocation of ULD
		// Added By Sreekumar S as a part of AirNZ CR 434
		ULDDiscrepancyVO uLDDiscrepancyVO = new ULDDiscrepancyVO();
/*		String polLocation = "";
		if (form.getUldNoChild() != null
				&& form.getUldNoChild().trim().length() > 0) {
			try {
				polLocation = delegate.findpolLocationForULD(logonAttributes
						.getCompanyCode(), form.getUldNoChild().toUpperCase()
						.trim());
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
			session.setPolLocation(polLocation);
		}*/
		if (form.getLocation() != null
				&& form.getLocation().trim().length() > 0) {
			uLDDiscrepancyVO.setLocation(form.getLocation());
		}
		if(form.getDefaultComboValue()!= null && form.getDefaultComboValue().trim().length() > 0){
			uLDDiscrepancyVO.setFacilityType(form.getDefaultComboValue());
		}
		if(form.getDiscrepancyDate()!= null && form.getDiscrepancyDate().trim().length() > 0){
			LocalDate discDate = new LocalDate(form.getReportingStationChild().toUpperCase(), Location.ARP, false);
			uLDDiscrepancyVO.setDiscrepencyDate(discDate.setDate(form.getDiscrepancyDate()));
		}
		if(form.getReportingStationChild()!= null && form.getReportingStationChild().trim().length() > 0){
			uLDDiscrepancyVO.setReportingStation(form.getReportingStationChild().toUpperCase().trim());
		}
		if(form.getRemarks()!= null && form.getRemarks().trim().length() > 0){
			uLDDiscrepancyVO.setRemarks(form.getRemarks());
		}
		if(form.getDiscrepancyCode()!= null && form.getDiscrepancyCode().trim().length() > 0){
			uldDiscrepancyVO.setDiscrepencyCode(form.getDiscrepancyCode());
		}
		session.setDiscrepancyDetails(uLDDiscrepancyVO);
		
		
		//Checking for valid facilityCode and location
		boolean isValidFacilityCode = false;
		Collection<ULDAirportLocationVO> uldAirportLocationVOs = null;
		ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
		Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
		
		///added by a-3278 for 23043 on 29Oct08
		if (ULDVO.NO_LOCATION.equals(form.getDefaultComboValue().toUpperCase())) {
			isValidFacilityCode = true;
		} else {
		try {
			
			uldAirportLocationVOs = uldDefaultsDelegate.listULDAirportLocation(
					logonAttributes.getCompanyCode(), form.getReportingStationChild()
							.toUpperCase(), form
							.getDefaultComboValue().toUpperCase());
		} catch (BusinessDelegateException exception) {
			exception.getMessage();
			errors = handleDelegateException(exception);
		}
		log.log(Log.INFO, "uldAirportLocationVOs", uldAirportLocationVOs);
		for (ULDAirportLocationVO uldAirportLocationVO : uldAirportLocationVOs) {
			log.log(Log.INFO, "uldAirportLocationVO.getAirportCode()",
					uldAirportLocationVO.getAirportCode());
			log.log(Log.INFO, "form.getReportingStationChild().toUpperCase()",
					form.getReportingStationChild()
							.toUpperCase());
			log.log(Log.INFO, "	uldAirportLocationVO.getFacilityType()",
					uldAirportLocationVO.getFacilityType());
			log.log(Log.INFO, "	form.getLocation().toUpperCase()", form.getLocation().toUpperCase());
			if (uldAirportLocationVO.getAirportCode() != null
					&& uldAirportLocationVO.getAirportCode().equals(
							form.getReportingStationChild()
							.toUpperCase())
					&& uldAirportLocationVO.getFacilityType() != null
					&& uldAirportLocationVO.getFacilityType().equals(
							form.getDefaultComboValue().toUpperCase())) {

				if (uldAirportLocationVO.getFacilityCode() != null
						&& uldAirportLocationVO.getFacilityCode().equals(
								form.getLocation().toUpperCase())) {
					isValidFacilityCode = true;
					break;
				}
			}
		}
		}
		 ///isValidFacilityCode  remove the validation for ICRD-120395
			log.log(Log.INFO, "\n\nform.getFlag()---------------->" + form.getFlag());
			if (("".equals(form.getFlag()))
					|| ("createmode").equals(form.getFlag()) 
							|| ("createmode").equals(form.getDiscrepancymode())) {
				uldDiscrepancyVOs = getFormDetails(form, logonAttributes);
				form.setButtonStatusFlag("");
				try {
					if (uldDiscrepancyVOs != null && uldDiscrepancyVOs.size() > 0) {
						log.log(Log.INFO,
								"\n\nCollection to be saved------------->",
								uldDiscrepancyVOs);
						uldnumberStation = delegate
								.saveULDDiscrepencyDetails(uldDiscrepancyVOs);
					}
				} catch (BusinessDelegateException e) {
					errors = handleDelegateException(e);
				}
				log.log(Log.FINE, "uldnumberStation---------->>>",
						uldnumberStation);
				if (uldnumberStation != null) {
					flag = "uldnumberStationisnotnull";
					String[] array = uldnumberStation.split("-");
					String uldNo = array[0];
					String station = array[1];
					Object[] obj = new Object[] { uldNo.toUpperCase(),
							station.toUpperCase() };
					log.log(Log.FINE, "array[4]------------->>", array);
					if(array[4].length() > 0 && "internallymoved".equals(array[4])){
						log.log(Log.FINE, "array[0]", array);
						log.log(Log.FINE, "array[1]", array);
						log.log(Log.FINE, "array[2]", array);
						log.log(Log.FINE, "array[3]", array);
						obj = new Object[] { array[0].toUpperCase(),
								array[1].toUpperCase(),
								array[2].toUpperCase(),
								array[3].toUpperCase()};
						ErrorVO errorVO = new ErrorVO(
								"uld.defaults.uldinternallymoved",
								obj);
						errors.add(errorVO);
					}else if(array[7].length() > 0 && ("recorduldmovement").equals(array[7])){
						//form.setErrorStatus("discrepancytobesolved");
						flag = "uldnumberStationisnotnull";
						form.setRecordUldNumber(array[0].toString());
						form.setRecordCode(array[1].toString());
						form.setRecordDate(array[2].toString());
						if ("Missing".equals(form.getRecordCode())) {
							form.setRecordPOL(array[5].toString());
							log.log(Log.FINE, "session.gssssss)------->>>",
									session.getCloseFlag());
							form.setRecordPOU(array[6].toString());
						} else if ("Found".equals(form.getRecordCode())) {
							form.setRecordPOL(array[6].toString());
							form.setRecordPOU(array[5].toString());
						}
						form.setRecordCurrentStation(array[6].toString());
						if (("createmode").equals(form.getFlag())) {
							session.setCloseFlag("createmode");
						} else {
							session.setCloseFlag("frommenu");
						}
						
						log.log(Log.INFO, "form.getRecordCode()", form.getRecordCode());
						log.log(Log.INFO, "array[6]", array);
						log.log(Log.INFO, "array[5]", array);
							obj = new Object[] {array[0].toUpperCase(),
									array[1].toUpperCase(),
									array[2].toUpperCase(),
									array[3].toUpperCase(),
									array[4].toUpperCase(),
									array[5].toUpperCase()};
						
						
						ErrorVO errorVO = new ErrorVO(
								"uld.defaults.discrepancytobesolved",
								obj);
						errors.add(errorVO);
					}else{
						ErrorVO errorVO = new ErrorVO(
								"uld.defaults.discrepancyforuldatthestationissorted",
								obj);
						errors.add(errorVO);
					}
				} else {
					flag = "uldnumberStationisnull";
				}
	
			}
			

		if (("modifymode").equals(form.getFlag())
				|| ("modifymode").equals(form.getDiscrepancymode())) {
			log.log(Log.INFO,"*****inside MODIFY MODE*********");
			ArrayList<ULDDiscrepancyVO> uldDiscrepancyVos = session
					.getULDDiscrepancyVOs();
			if (uldDiscrepancyVos != null) {
				ULDDiscrepancyVO uldDiscrepancyVo = uldDiscrepancyVos
						.get(Integer.parseInt(form.getUldCurrentPageNum()) - 1);
				NavigateCommand command = new NavigateCommand();
				uldDiscrepancyVO = command.updateULDDiscrepancyVO(session,
						uldDiscrepancyVo, form, logonAttributes);
				uldDiscrepancyVos.set(Integer.parseInt(form
						.getUldCurrentPageNum()) - 1, uldDiscrepancyVO);
				session.setULDDiscrepancyVOs(uldDiscrepancyVos);
			}
			form.setButtonStatusFlag("");
			try {
				if (uldDiscrepancyVos != null && uldDiscrepancyVos.size() > 0) {
					log.log(Log.INFO,
							"\n\nCollection to be saved------------->",
							uldDiscrepancyVos);
					delegate.saveULDDiscrepencyDetails(uldDiscrepancyVos);
				}
			} catch (BusinessDelegateException e) {
				errors = handleDelegateException(e);
			}
		}
		log.exiting("SaveCommand", "EXIT");

		if (errors != null && errors.size() > 0) {

		// now added
			
			log.log(Log.FINE, "flag----->>???", flag);
			if ("uldnumberStationisnotnull".equals(flag)) {
				
				form.setDiscrepancyDate("");				
				if(form.getDiscDisableStat() !=null && "airline".equals(form.getDiscDisableStat())){
					form.setReportingStationChild("");
				}	
				form.setRemarks("");
				form.setUldNoChild("");
				form.setLocation("");
			}		
			//invocationContext.addAllError(errors);
			//invocationContext.target = SAVE_FAILURE;
			//return;
		} 
		//updated by A-5844	for the BUG ICRD-52247
		if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if (("uld.defaults.discrepancyalreadycaught").equals(error.getErrorCode())) {
					errorVOs.add(error);
				}
				else if (("uld.defaults.uldisnotinthestock").equals(error.getErrorCode())) {
					errorVOs.add(error);
				}
				else if (("uld.defaults.uldcannotbemissing").equals(error.getErrorCode())) {
					errorVOs.add(error);
				}
				else if (("uld.defaults.uldalreadyinthesamelocation").equals(error.getErrorCode())) {
					errorVOs.add(error);
					form.setLocation("");
				}
				else if (("uld.defaults.uldinternallymoved").equals(error.getErrorCode())) {
					errorVOs.add(error);
					form.setLocation("");
				}
				// Added by Preet for bug 18685 on 15Oct08 starts
				else if (("uld.defaults.discrepancytobesolved").equals(error.getErrorCode())) {
					// added a-5125 for BUG ICRD-50590
					if(!error.getErrorCode().equals(errors.iterator().next().getErrorCode())){
						errorVOs.add(error);
					}
					form.setLocation("");
				}
			}
			invocationContext.addAllError(errorVOs);
			invocationContext.target = SAVE_FAILURE;
		} /*else if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if (error.getErrorCode().equals(
						"uld.defaults.uldisnotinthestock")) {
					errors.add(error);
				}
			}
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}
		else if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if (error.getErrorCode().equals(
						"uld.defaults.uldcannotbemissing")) {
					errors.add(error);
				}
			}
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}
		else if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if (error.getErrorCode().equals(
						"uld.defaults.uldalreadyinthesamelocation")) {
					errors.add(error);
				}
			}
			form.setLocation("");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}
		else if (errors != null && errors.size() > 0) {
			for (ErrorVO error : errors) {
				if (error.getErrorCode().equals(
						"uld.defaults.uldinternallymoved")) {
					errors.add(error);
				}
				// Added by Preet for bug 18685 on 15Oct08 starts
				if (error.getErrorCode().equals(
					"uld.defaults.discrepancytobesolved")) {
					errors.add(error);
				}
				// Added by Preet for bug 18685 on 15Oct08 ends
			}
			form.setLocation("");
			log.log(Log.FINE,"---INSIDE INTERNALLY MOVED-------->>");
			invocationContext.addAllError(errors);
			invocationContext.target = SAVE_FAILURE;
		}*/
		
		// added for scm reconcile

		/*
		 * else if (session.getPageURL() != null &&
		 * PAGE_URL.equals(session.getPageURL())) { session.setPageURL(null);
		 * 
		 * log.log(Log.FINE,"FROM SCM RECONCILE SCREEN");
		 * invocationContext.target = CLOSESCM_SUCCESS; return; }
		 */

		else {

			if (("modifymode").equals(form.getFlag())) {
				form.setFlag("");
				log.log(Log.FINE, "------------------------>>>Flag", form.getUldNoChild());
				session.setUldNumber(form.getUldNoChild());
				session.setMode("modifymode");
				log.log(Log.FINE, "------------------------>>>modify mode");
				invocationContext.target = SAVE_SUCCESS;
				return;
			} else {
				log.log(Log.FINE, "form.getFlag()----->>", form.getFlag());
				form.setButtonStatusFlag("savedSuccessfully");
				if ("uldnumberStationisnull".equals(flag)) {
					Object[] obj = new Object[] { form
							.getReportingStationChild().toUpperCase() };
					ErrorVO errorVO = new ErrorVO(
							"uld.defaults.discrepancycreatedfor", obj);
					errors.add(errorVO);
					
					
				} else {
					ULDDiscrepancyFilterVO vo = session
							.getULDDiscrepancyFilterVODetails();
					//Check added as part of bug 109175 by A-3767 on 29Mar11
					if(vo!=null){
						vo.setUldNumber("");
						vo.setReportingStation("");
					}
				}
				form.setDiscrepancyDate("");
				log.log(Log.INFO, "FORM DISABLE STATUS IS ----->>>", form.getDiscDisableStat());
				if(form.getDiscDisableStat() !=null && "airline".equals(form.getDiscDisableStat())){
					form.setReportingStationChild("");
				}				
				form.setRemarks("");
				form.setUldNoChild("");
				form.setLocation("");
				form.setUldNo("");
				
				/*ErrorVO error = new ErrorVO(
						"uld.defaults.discrepancy.savedsuccessfully");
				error.setErrorDisplayType(ErrorDisplayType.STATUS);
				errors = new ArrayList<ErrorVO>();
				errors.add(error);*/
				invocationContext.addAllError(errors);
				invocationContext.target = SAVEMAINTAIN_SUCCESS;
				return;
			}
		}

	}

	/**
	 * Function to get the details to be saved
	 * 
	 * @param form
	 * @param logonAttributes
	 * @return
	 */
	private Collection<ULDDiscrepancyVO> getFormDetails(
			MaintainUldDiscrepanciesForm form, LogonAttributes logonAttributes) {
		Collection<ULDDiscrepancyVO> uldDiscrepancyVOs = new ArrayList<ULDDiscrepancyVO>();
		ULDDiscrepancyVO uldDiscrepancyVO = new ULDDiscrepancyVO();

		uldDiscrepancyVO.setCompanyCode(logonAttributes.getCompanyCode());
		uldDiscrepancyVO.setDiscrepencyCode(form.getDiscrepancyCode());
		uldDiscrepancyVO.setLastUpdatedUser(logonAttributes.getUserId());
		uldDiscrepancyVO
				.setOperationFlag(ULDDiscrepancyVO.OPERATION_FLAG_INSERT);
		uldDiscrepancyVO.setRemarks(form.getRemarks().toUpperCase());
		uldDiscrepancyVO.setReportingStation(form.getReportingStationChild()
				.toUpperCase().trim());
		LocalDate discDate = new LocalDate(uldDiscrepancyVO
				.getReportingStation(), Location.ARP, false);
		if (form.getDiscrepancyDate() != null) {
			uldDiscrepancyVO.setDiscrepencyDate(discDate.setDate(form
					.getDiscrepancyDate()));
		}
		uldDiscrepancyVO
				.setUldNumber(form.getUldNoChild().toUpperCase().trim());
		uldDiscrepancyVO.setFacilityType(form.getDefaultComboValue());
		uldDiscrepancyVO.setLocation(form.getLocation());
		uldDiscrepancyVOs.add(uldDiscrepancyVO);
		return uldDiscrepancyVOs;
	}

	private Collection<ErrorVO> validateReportingStationCode(
			MaintainUldDiscrepanciesForm form, String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		AreaDelegate delegate = new AreaDelegate();
		AirportValidationVO airportValidationVO = null;
		try {
			airportValidationVO = delegate.validateAirportCode(logonAttributes
					.getCompanyCode().toUpperCase(), form
					.getReportingStationChild().toUpperCase());

		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
		return errors;
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
		if (uldValidationVO == null) {
			ErrorVO errorVO = new ErrorVO(
					"uld.defaults.listulddiscrepancies.invaliduld");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		return errors;
	}

	private Collection<ErrorVO> validateDiscrepancyDate(
			MaintainUldDiscrepanciesForm form, String companyCode) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		LocalDate localDate = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, false);
		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(),
				Location.ARP, false);
		log.log(Log.FINE, "\n\n\n\n currentdate", currentdate);
		log.log(Log.FINE, "\n\n\n\n disdate", localDate.setDate(form.getDiscrepancyDate()));
		if (currentdate.isLesserThan(localDate.setDate(form
				.getDiscrepancyDate()))) {
			errors
					.add(new ErrorVO(
							"uld.defaults.listulddiscrepancies.msg.err.discrepancydategreaterthancurrent"));
		}
		return errors;
	}
	/**
	 * The method to obtain the onetime values.
	 * The method will call the sharedDefaults delegate
	 * and returns the map of requested onetimes
	 * @return oneTimeValues
	 */
	private HashMap<String, Collection<OneTimeVO>> getOneTimeValues(){
		log.entering("CreateCommand","getOneTimeValues");
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
			log.log(Log.FINE, "****inside try**************************",
					getOneTimeParameterTypes());
			oneTimeValues =	sharedDefaultsDelegate.findOneTimeValues(
					logonAttributes.getCompanyCode(), 
					getOneTimeParameterTypes());
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.FINE,"*****in the exception");
			businessDelegateException.getMessage();
			errors = handleDelegateException(businessDelegateException);
		}
		log.log(Log.INFO, "oneTimeValues ---> ", oneTimeValues);
		log.exiting("CreateCommand","getOneTimeValues");
		return (HashMap<String, Collection<OneTimeVO>>)oneTimeValues;
	}
	
    private Collection<String> getOneTimeParameterTypes() {
    	log.entering("CreateCommand","getOneTimeParameterTypes");
    	ArrayList<String> parameterTypes = new ArrayList<String>();
    	
    	parameterTypes.add(DISCREPANCYCODE);
    	parameterTypes.add(FACILITY_TYPE);
    	log.exiting("CreateCommand","getOneTimeParameterTypes");
    	return parameterTypes;    	
    }
}
