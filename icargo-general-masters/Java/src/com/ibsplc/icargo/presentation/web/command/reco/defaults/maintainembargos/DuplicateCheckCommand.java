
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.scc.SCCDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

public class DuplicateCheckCommand extends BaseCommand{
	private Log log = LogFactory.getLogger("DuplicateCheckCommand");
	private static final String AIRPORT = "A";
	private static final String AIRPORT_GROUP = "ARPGRP";
	private static final String COUNTRY = "C";
	private static final String COUNTRY_GROUP = "CNTGRP";
	private static final String ERROR_INVALID_COUNTRY = "shared.area.invalidcountry";
	private static final String ERROR_INVALID_AIRPORT ="shared.area.invalidairport";
	private static final String ORIGIN ="O";
	private static final String DESTINATION ="D";
	private static final String VIAPOINT ="V";
	private static final String GEOGRAPHIC_IN ="IN";
	private static final String SCC ="SCC";
	private static final String SCCGRP ="SCCGRP";
	private static final String PRODUCT ="PRD";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String OPERATION_FLAG_DELETE = "D";
	private static final String PARAMETER = "P";
	private static final String VALUE_ALL="-";
	private static final String  FLIGHT_NO="FLTNUM";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintainembargo");
	    MaintainEmbargoRulesForm maintainEmbargoForm =
			(MaintainEmbargoRulesForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl =getApplicationSession();		
		EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
		Collection<EmbargoDetailsVO> embargoDetailsList=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		LogonAttributes logonAttributes =applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();		
		Collection<EmbargoRulesVO> embargoVOs = new ArrayList<EmbargoRulesVO>();
		EmbargoRulesVO embargoVO = null;
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		embargoFilterVO.setCompanyCode(companyCode);
		if(maintainEmbargoForm.getEmbargoVersion()!=null){
			embargoFilterVO.setEmbargoVersion(Integer.parseInt(maintainEmbargoForm.getEmbargoVersion()));
		}
		EmbargoRulesVO embargoVo=getEmbargoDetails(maintainEmbargoForm,maintainEmbargoSession, companyCode);
		embargoFilterVO = getFilter(maintainEmbargoForm,logonAttributes,embargoFilterVO,errors);
		if(null !=errors && errors.size()>0){
			invocationContext.addAllError(errors);
			maintainEmbargoForm.setIsDuplicatePresent("N");
			invocationContext.target = "modifyScreenloadFailure";
			return;
		}
		try {	
			embargoDetailsList =embargoRulesDelegate.findDuplicateEmbargos(embargoFilterVO);			
			invocationContext.target = "modifyScreenloadSuccess";
		}catch(BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
			invocationContext.addAllError(errors);
			maintainEmbargoForm.setIsDuplicatePresent("N");
			invocationContext.target = "modifyScreenloadFailure";
			return;
		}
		if(embargoDetailsList!=null && embargoDetailsList.size()>0){
			for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsList){
				embargoVO = new EmbargoRulesVO();
				embargoVO.setEmbargoReferenceNumber(embargoDetailsVO.getEmbargoReferenceNumber());
				embargoVO.setEmbargoDescription(embargoDetailsVO.getEmbargoDescription());
				embargoVOs.add(embargoVO);
			}		
		}
		if(embargoVOs!=null && embargoVOs.size()>0){
			maintainEmbargoSession.setEmbargoVos(embargoVOs);
			maintainEmbargoForm.setIsDuplicatePresent("Y");
		}else{
			ErrorVO error = new ErrorVO("embargo.nulllist");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			invocationContext.addError(error);		
			maintainEmbargoSession.setEmbargoVos(null);
			maintainEmbargoForm.setIsDuplicatePresent("N");
			invocationContext.target = "modifyScreenloadFailure";
			return;
		}
	invocationContext.target = "modifyScreenloadSuccess";	
	}
	
	private EmbargoFilterVO getFilter(MaintainEmbargoRulesForm form,
			LogonAttributes logonAttributes,EmbargoFilterVO filterVO,Collection<ErrorVO> errors){
		Collection<ErrorVO> errorsList=null;
		AreaDelegate areaDelegate=new AreaDelegate();
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		boolean isValid =  false;
		LocalDate date = null;
		Collection<String> airports = null;
		Collection<String> airportgroups = null;
		Collection<String> countrys = null;
		Collection<String> countrygroups = null;
		Map<String,AirportValidationVO> airportValidationVOMap = null;
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		filterVO.setEmbargoRefNumber(form.getRefNumber());
		
		String currentDate= DateUtilities.getCurrentDate(LocalDate.CALENDAR_DATE_FORMAT);
		if(form.getStartDate() != null && form.getStartDate().trim().length()>0){
			if(!form.getStartDate().equals(currentDate)){
				if(!DateUtilities.isValidDate(form.getStartDate(),
						LocalDate.CALENDAR_DATE_FORMAT)){
					ErrorVO error = new ErrorVO("reco.defaults.invalidstartdate");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else if (DateUtilities.isLessThan(form.getStartDate(), currentDate,
						LocalDate.CALENDAR_DATE_FORMAT)
						&& !form.getIsSuspended()) {
					log.log(Log.INFO, "Validate Form : StartDate", form.getStartDate());
					isValid = false;
					log.log(log.FINE,"Validate Form : StartDate");
					Object[] obj = { "" };
					ErrorVO error = new ErrorVO("reco.defaults.startdatecompare", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else{
					date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
					date.setDate(form.getStartDate());
					filterVO.setStartDate(date);
				}
			}else{
				date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
				date.setDate(form.getStartDate());
				filterVO.setStartDate(date);
			}
		}else{
			date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			filterVO.setStartDate(date);
		}
		if(form.getEndDate() != null && form.getEndDate().trim().length()>0){
			date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			date.setDate(form.getEndDate());
			filterVO.setEndDate(date);
			if(!form.getEndDate().equals(currentDate)){
				if (!DateUtilities.isValidDate(form.getEndDate(),
						LocalDate.CALENDAR_DATE_FORMAT)) {
					log.log(Log.INFO, "Validate Form : StartDate", form.getStartDate());
					isValid = false;
					log.log(log.FINE,"Validate Form : StartDate");
					Object[] obj = { "" };
					ErrorVO error = new ErrorVO("reco.defaults.invalidenddate", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		if(form.getEndDate()  != null && form.getEndDate().trim().length()>0 
				&& form.getStartDate() != null && form.getStartDate().trim().length()>0){
			if(!form.getStartDate().equals(form.getEndDate())){
				if (!DateUtilities.isLessThan(form.getStartDate(), form.getEndDate(),
						LocalDate.CALENDAR_DATE_FORMAT)) {
					isValid = false;
					Object[] obj = { "" };
					ErrorVO error = new ErrorVO("reco.defaults.invaliddate", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		String[] geographicLevel = form.getGeographicLevel();
		String[] geographicLevelType = form.getGeographicLevelType();
		String[] geographicLevelValues= form.getGeographicLevelValues();
		String[] glOperationFlag = form.getGlOperationFlag();
		String[] geographicLevelApplicableOn=form.getGeographicLevelApplicableOn();
		boolean isAirportError=true;
		boolean isAirportGroupError=true;
		boolean isCountryError=true;
		boolean isCountryGroupError=true;
		boolean isGeographicalLevel=false;
		boolean isParameter=false;
		if(glOperationFlag!=null){			
			for(int i = 0; i < glOperationFlag.length - 1 ; i++) {
				if(!"NOOP".equals(glOperationFlag[i]) && !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(glOperationFlag[i])){
					if(null !=geographicLevel && geographicLevel.length>0 && 
							null !=geographicLevelType && geographicLevelType.length>0 &&
							null !=geographicLevelValues && geographicLevelValues.length>0){				
						isGeographicalLevel=true;
							if(geographicLevelType[i].equalsIgnoreCase(AIRPORT) 
									&& null !=geographicLevelValues[i] && geographicLevelValues[i].length()>0){
								for(String geographicLevelValue:geographicLevelValues[i].split(",")){
									if(null !=geographicLevelValue && geographicLevelValue.length()>0  &&
											!geographicLevelValue.equals(VALUE_ALL)){
										geographicLevelValue=geographicLevelValue.toUpperCase();
										if(geographicLevel[i].equalsIgnoreCase(ORIGIN)){
											if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
												filterVO.setAirportCodeOrigin(null !=filterVO.getAirportCodeOrigin()?
														filterVO.getAirportCodeOrigin()+","+geographicLevelValue:geographicLevelValue);
											}else{
												filterVO.setAirportCodeOriginExc(null !=filterVO.getAirportCodeOriginExc()?
														filterVO.getAirportCodeOriginExc()+","+geographicLevelValue:geographicLevelValue);
											}
										}else if(geographicLevel[i].equalsIgnoreCase(DESTINATION)){
											if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
												filterVO.setAirportCodeDestination(null !=filterVO.getAirportCodeDestination()?
														filterVO.getAirportCodeDestination()+","+geographicLevelValue:geographicLevelValue);
											}else{
												filterVO.setAirportCodeDestinationExc(null !=filterVO.getAirportCodeDestinationExc()?
														filterVO.getAirportCodeDestinationExc()+","+geographicLevelValue:geographicLevelValue);
											}
										}else if(geographicLevel[i].equalsIgnoreCase(VIAPOINT)){
											if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
												filterVO.setAirportCodeViaPoint(null !=filterVO.getAirportCodeViaPoint()?
														filterVO.getAirportCodeViaPoint()+","+geographicLevelValue:geographicLevelValue);
											}else{
												filterVO.setAirportCodeViaPointExc(null !=filterVO.getAirportCodeViaPointExc()?
														filterVO.getAirportCodeViaPointExc()+","+geographicLevelValue:geographicLevelValue);
											}
										}
										airports = new ArrayList<String>();
										airports.add(geographicLevelValue);
										try{
											airportValidationVOMap=areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(),airports);
										}catch(BusinessDelegateException businessDelegateException) {
											errorsList = handleDelegateException(businessDelegateException);
											if(errorsList != null && errorsList.size()>0){
												for(ErrorVO err : errorsList){
													if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
														if(isAirportError){
															ErrorVO error = new ErrorVO("reco.defaults.invalidairport");
															error.setErrorDisplayType(ErrorDisplayType.ERROR);
															errors.add(error);
															isAirportError=false;
														}
													}
												}
											}
										}
										if(null !=airportValidationVOMap && airportValidationVOMap.size()>0 && null !=airportValidationVOMap.get(geographicLevelValue)){
											if(geographicLevel[i].equalsIgnoreCase(ORIGIN)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeOrigin(null !=filterVO.getCountryCodeOrigin()?
															filterVO.getCountryCodeOrigin()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												}else{
													filterVO.setCountryCodeOriginExc(null !=filterVO.getCountryCodeOriginExc()?
															filterVO.getCountryCodeOriginExc()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												} 	
											}else if(geographicLevel[i].equalsIgnoreCase(DESTINATION)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeDestination(null !=filterVO.getCountryCodeDestination()?
															filterVO.getCountryCodeDestination()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												}else{
													filterVO.setCountryCodeDestinationExc(null !=filterVO.getCountryCodeDestinationExc()?
															filterVO.getCountryCodeDestinationExc()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												} 	
											
											}else if(geographicLevel[i].equalsIgnoreCase(VIAPOINT)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeViaPoint(null !=filterVO.getCountryCodeViaPoint()?
															filterVO.getCountryCodeViaPoint()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												}else{
													filterVO.setCountryCodeViaPointExc(null !=filterVO.getCountryCodeViaPointExc()?
															filterVO.getCountryCodeViaPointExc()+","+airportValidationVOMap.get(geographicLevelValue).getCountryCode():airportValidationVOMap.get(geographicLevelValue).getCountryCode());
												} 	
											
											}	
																			
										}
									}
									}
								}else if(geographicLevelType[i].equalsIgnoreCase(AIRPORT_GROUP) 
										&& null !=geographicLevelValues[i] && geographicLevelValues[i].length()>0){					
									airportgroups = new ArrayList<String>();
									for(String geographicLevelValue:geographicLevelValues[i].split(",")){
										if(null !=geographicLevelValue && geographicLevelValue.length()>0   &&
												!geographicLevelValue.equals(VALUE_ALL)){
											geographicLevelValue=geographicLevelValue.toUpperCase();
											if(geographicLevel[i].equalsIgnoreCase(ORIGIN)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setAirportGroupOrigin(null !=filterVO.getAirportGroupOrigin()?
															filterVO.getAirportGroupOrigin()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setAirportGroupOriginExc(null !=filterVO.getAirportGroupOriginExc()?
															filterVO.getAirportGroupOriginExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(DESTINATION)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setAirportGroupDestination(null !=filterVO.getAirportGroupDestination()?
															filterVO.getAirportGroupDestination()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setAirportGroupDestinationExc(null !=filterVO.getAirportGroupDestinationExc()?
															filterVO.getAirportGroupDestinationExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(VIAPOINT)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setAirportGroupViaPoint(null !=filterVO.getAirportGroupViaPoint()?
															filterVO.getAirportGroupViaPoint()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setAirportGroupViaPointExc(null !=filterVO.getAirportGroupViaPointExc()?
															filterVO.getAirportGroupViaPointExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}
											airportgroups.add(geographicLevelValue);
										}	
									}
									try{
										String groupType = "ARPGRP";
										isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), airportgroups,groupType);
										if(!isValid){
											if(isAirportGroupError){
												ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
												error.setErrorDisplayType(ErrorDisplayType.ERROR);
												errors.add(error);
												isAirportGroupError=false;
											}
										}
									}catch(BusinessDelegateException businessDelegateException) {
										errorsList = handleDelegateException(businessDelegateException);
										if(errorsList != null && errorsList.size()>0){	
											if(isAirportGroupError){
												ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
												error.setErrorDisplayType(ErrorDisplayType.ERROR);
												errors.add(error);	
												isAirportGroupError=false;
											}
										}
									}
							}else if(geographicLevelType[i].equalsIgnoreCase(COUNTRY) 
									&& null !=geographicLevelValues[i] && geographicLevelValues[i].length()>0){									
									countrys = new ArrayList<String>();
									for(String geographicLevelValue:geographicLevelValues[i].split(",")){
										if(null !=geographicLevelValue && geographicLevelValue.length()>0   &&
												!geographicLevelValue.equals(VALUE_ALL)){
											geographicLevelValue=geographicLevelValue.toUpperCase();
											if(geographicLevel[i].equalsIgnoreCase(ORIGIN)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeOrigin(null !=filterVO.getCountryCodeOrigin()?
															filterVO.getCountryCodeOrigin()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryCodeOriginExc(null !=filterVO.getCountryCodeOriginExc()?
															filterVO.getCountryCodeOriginExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(DESTINATION)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeDestination(null !=filterVO.getCountryCodeDestination()?
															filterVO.getCountryCodeDestination()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryCodeDestinationExc(null !=filterVO.getCountryCodeDestinationExc()?
															filterVO.getCountryCodeDestinationExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(VIAPOINT)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryCodeViaPoint(null !=filterVO.getCountryCodeViaPoint()?
															filterVO.getCountryCodeViaPoint()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryCodeViaPointExc(null !=filterVO.getCountryCodeViaPointExc()?
															filterVO.getCountryCodeViaPointExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}
											countrys.add(geographicLevelValue);
										}	
									}
									try{
										areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrys);
									}catch(BusinessDelegateException businessDelegateException) {
										errorsList = handleDelegateException(businessDelegateException);
										if(errorsList != null && errorsList.size()>0){
											for(ErrorVO err : errorsList){
												if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
													if(isCountryError){
														ErrorVO error = new ErrorVO("reco.defaults.invalidcountry");
														error.setErrorDisplayType(ErrorDisplayType.ERROR);
														errors.add(error);
														isCountryError=false;
													}
												}
											}
										}
									}
							}else if(geographicLevelType[i].equalsIgnoreCase(COUNTRY_GROUP) 
									&& null !=geographicLevelValues[i] && geographicLevelValues[i].length()>0){
									countrygroups = new ArrayList<String>();
									for(String geographicLevelValue:geographicLevelValues[i].split(",")){
										if(null !=geographicLevelValue && geographicLevelValue.length()>0   &&
												!geographicLevelValue.equals(VALUE_ALL)){
											geographicLevelValue=geographicLevelValue.toUpperCase();
											if(geographicLevel[i].equalsIgnoreCase(ORIGIN)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryGroupOrigin(null !=filterVO.getCountryGroupOrigin()?
															filterVO.getCountryGroupOrigin()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryGroupOriginExc(null !=filterVO.getCountryGroupOriginExc()?
															filterVO.getCountryGroupOriginExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(DESTINATION)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryGroupDestination(null !=filterVO.getCountryGroupDestination()?
															filterVO.getCountryGroupDestination()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryGroupDestinationExc(null !=filterVO.getCountryGroupDestinationExc()?
															filterVO.getCountryGroupDestinationExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}else if(geographicLevel[i].equalsIgnoreCase(VIAPOINT)){
												if(geographicLevelApplicableOn[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
													filterVO.setCountryGroupViaPoint(null !=filterVO.getCountryGroupViaPoint()?
															filterVO.getCountryGroupViaPoint()+","+geographicLevelValue:geographicLevelValue);
												}else{
													filterVO.setCountryGroupViaPointExc(null !=filterVO.getCountryGroupViaPointExc()?
															filterVO.getCountryGroupViaPointExc()+","+geographicLevelValue:geographicLevelValue);
												}
											}
											countrygroups.add(geographicLevelValue);
										}	
									}
									try{
										String groupType = "CNTGRP";
										isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), countrygroups,groupType);
										if(!isValid){
											if(isCountryGroupError){
												ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
												error.setErrorDisplayType(ErrorDisplayType.ERROR);
												errors.add(error);
												isCountryGroupError=false;
											}
										}
									}catch(BusinessDelegateException businessDelegateException) {
										errorsList = handleDelegateException(businessDelegateException);
										if(errorsList != null && errorsList.size()>0){	
											if(isCountryGroupError){
												ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
												error.setErrorDisplayType(ErrorDisplayType.ERROR);
												errors.add(error);	
												isCountryGroupError=false;
											}
										}
									}							
							}
					}
				}
			}
		}						
		String[] parametercodes = form.getParameterCode();
		String[] applicablevalues = form.getIsIncluded();
		String[] values = form.getValues();
		String[] operationalFlags = form.getParamOperationalFlag();
		SCCDelegate sccDelegate = new SCCDelegate();
		
		Collection<SCCValidationVO> validsccs = null;
		boolean isSccError=true;
		boolean isSccGroupError=true;
		String[] valuesForParams = null;
		if(operationalFlags != null ){				
			for (int i = 0; i < values.length -1; i++) {			
				if(!"NOOP".equals(operationalFlags[i]) && !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(operationalFlags[i])){
					if(null !=parametercodes && parametercodes.length>0 && null !=values && values.length>0){
						if(parametercodes[i].equalsIgnoreCase(SCC) && null !=values[i] && values[i].length()>0){
							valuesForParams=values[i].toUpperCase().split(",");
							isParameter=true;
							Collection<String> sccs = new ArrayList<String>();
							try{					
								for(int j=0;j<valuesForParams.length;j++)
									{
									sccs.add(valuesForParams[j].toUpperCase());						
									}						
								validsccs = sccDelegate.validateSCCCodes(logonAttributes.getCompanyCode(), sccs);
								if(validsccs == null){
									if(isSccError){
										ErrorVO error = new ErrorVO("reco.defaults.invalidscc");
										errors.add(error);
										isSccError=false;
									}
								}	
							}catch (BusinessDelegateException e) {
								log.log(Log.SEVERE, "<--BusinessDelegateException-->");
								errors= handleDelegateException(e);
							}
												
							if(applicablevalues[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
								filterVO.setScc(null !=filterVO.getScc()?filterVO.getScc()+","+values[i].toUpperCase():values[i].toUpperCase());
							}else{
								filterVO.setSccExc(null !=filterVO.getSccExc()?filterVO.getSccExc()+","+values[i].toUpperCase():values[i].toUpperCase());
							}
						}else if(parametercodes[i].equalsIgnoreCase(SCCGRP) && null !=values[i] && values[i].length()>0){
							valuesForParams=values[i].toUpperCase().split(",");
							isParameter=true;
							Collection<String> sccgroups = new ArrayList<String>();
							boolean isValidScc=true;
							try{					
								for(int j=0;j<valuesForParams.length;j++)
									{
									sccgroups.add(valuesForParams[j].toUpperCase());		
									}		
								isValidScc = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), sccgroups,SCCGRP);
								if(!isValidScc){
									if(isSccGroupError){
										ErrorVO error = new ErrorVO("reco.defaults.invalidsccgroup");
										errors.add(error);
										isSccGroupError=false;
									}
								}
							}catch (BusinessDelegateException e) {
								log.log(Log.SEVERE, "<--BusinessDelegateException-->");
								errors= handleDelegateException(e);
							}						
							if(applicablevalues[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
								filterVO.setSccGroup(null !=filterVO.getSccGroup()?filterVO.getSccGroup()+","+values[i].toUpperCase():values[i].toUpperCase());
							}else{
								filterVO.setSccGroupExc(null !=filterVO.getSccGroupExc()?filterVO.getSccGroupExc()+","+values[i].toUpperCase():values[i].toUpperCase());
							}
						}else if(parametercodes[i].equalsIgnoreCase(PRODUCT) && null !=values[i] && values[i].length()>0){
							valuesForParams=values[i].toUpperCase().split(",");
							isParameter=true;
							Collection<String> products = new ArrayList<String>();
							LocalDate strDate = new LocalDate(logonAttributes.getAirportCode(),
									Location.ARP, false);
							if(null !=form.getStartDate() && form.getStartDate().trim().length()>0){
								strDate.setDate(form.getStartDate());
							}
							LocalDate endDate = new LocalDate(logonAttributes.getAirportCode(),
									Location.ARP, false);
							if(null !=form.getEndDate() && form.getEndDate().trim().length()>0){
								endDate.setDate(form.getEndDate());
							}
							String productCode = null;
							try{					
								for(int j=0;j<valuesForParams.length;j++){
									products.add(valuesForParams[j].toUpperCase());													
									productCode = new EmbargoRulesDelegate().validateProduct(logonAttributes
											.getCompanyCode(), valuesForParams[j].toUpperCase(), strDate, endDate);
									if (productCode == null || productCode.trim().length() <= 0){
										ErrorVO error = new ErrorVO("reco.defaults.invalidproduct");
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
										break ;
									}
								}							
							}catch (BusinessDelegateException e) {
								log.log(Log.SEVERE, "<--BusinessDelegateException-->");
								errors= handleDelegateException(e);
							}
							
							if(applicablevalues[i].equalsIgnoreCase(GEOGRAPHIC_IN)){
								filterVO.setProduct(null !=filterVO.getProduct()?filterVO.getProduct()+","+values[i].toUpperCase():values[i].toUpperCase());
							}else{
								filterVO.setProductExc(null !=filterVO.getProductExc()?filterVO.getProductExc()+","+values[i].toUpperCase():values[i].toUpperCase());
							}
						}
					}
				}
			}
		}	
		if(!isParameter && !isGeographicalLevel){
			ErrorVO error = new ErrorVO("embargo.nulllist");
			errors.add(error);
		}			
		return filterVO;
	} 
	
	
	
	private EmbargoRulesVO getEmbargoDetails(MaintainEmbargoRulesForm maintainEmbargoForm ,
			MaintainEmbargoRulesSession session, String companyCode){
		ApplicationSessionImpl applicationSessionImpl =getApplicationSession();
		LogonAttributes logonAttributes =applicationSessionImpl.getLogonVO();
		EmbargoRulesVO embargoVO = null;
		if(session.getEmbargoVo()!=null) {
			embargoVO=session.getEmbargoVo();
		} else {
			embargoVO = new EmbargoRulesVO();
		}
		embargoVO.setCompanyCode(companyCode);
		embargoVO.setEmbargoReferenceNumber(maintainEmbargoForm.getRefNumber());
		embargoVO.setRuleType("E");
		embargoVO.setEmbargoLevel(maintainEmbargoForm.getEmbargoLevel());
		embargoVO.setEmbargoDescription(maintainEmbargoForm.getEmbargoDesc());			
		//setting value for status
		String statusDescription=maintainEmbargoForm.getStatus();
		Collection<OneTimeVO> embargoStatus = session.getEmbargoStatus();
		if(embargoStatus != null){
			for(OneTimeVO statusVO: embargoStatus){
				if(statusVO.getFieldDescription().equals(statusDescription)){
					embargoVO.setStatus(statusVO.getFieldValue());
				}
			}
		}		
		embargoVO.setIsSuspended(maintainEmbargoForm.getIsSuspended());
		embargoVO.setCategory(maintainEmbargoForm.getCategory());
		embargoVO.setComplianceType(maintainEmbargoForm.getComplianceType());
		embargoVO.setApplicableTransactions(maintainEmbargoForm.getApplicableTransactions());
		embargoVO.setRemarks(maintainEmbargoForm.getRemarks());
		//Added by A-5160 for ICRD-27155
		logonAttributes =applicationSessionImpl.getLogonVO();
		String airportCode = logonAttributes.getAirportCode();
		embargoVO.setAirportCode(airportCode);
		LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		if(maintainEmbargoForm.getStartDate()!=null &&
				maintainEmbargoForm.getStartDate().trim().length()>0) {
			embargoVO.setStartDate(localStartDate.setDate(maintainEmbargoForm.getStartDate()));
		}
		LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		if(maintainEmbargoForm.getEndDate() != null &&
				maintainEmbargoForm.getEndDate().trim().length()>0) {
			embargoVO.setEndDate(localEndDate.setDate(maintainEmbargoForm.getEndDate()));
		}
		if(EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(embargoVO.getOperationalFlag()) ||
				EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag())){
			if(maintainEmbargoForm.getDaysOfOperation()!= null &&
					maintainEmbargoForm.getDaysOfOperation().trim().length()>0){
				if((embargoVO.getDaysOfOperation()==null || embargoVO.getDaysOfOperation().trim().length()==0)
						&& embargoVO.getDaysOfOperationApplicableOn()==null ){
					embargoVO.setDaysOfOperationFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				}
				if((embargoVO.getDaysOfOperation()!=null && embargoVO.getDaysOfOperation().trim().length()>0)
					|| embargoVO.getDaysOfOperationApplicableOn()!=null){
					embargoVO.setDaysOfOperationFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
				}
				embargoVO.setDaysOfOperation(maintainEmbargoForm.getDaysOfOperation());
				if(maintainEmbargoForm.getDaysOfOperationApplicableOn()!=null) {
					embargoVO.setDaysOfOperationApplicableOn(maintainEmbargoForm.getDaysOfOperationApplicableOn().toUpperCase());
				}
			}
			//Added by A-5160 for ICRD-49991
			else{
				if(embargoVO.getDaysOfOperation()!=null && embargoVO.getDaysOfOperation().trim().length()>0)
					{
					embargoVO.setDaysOfOperationFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
					}
				embargoVO.setDaysOfOperation("");
				embargoVO.setDaysOfOperationApplicableOn(null);
			}
		}
		String[] geographicLevel = maintainEmbargoForm.getGeographicLevel();
		String[] geographicLevelType = maintainEmbargoForm.getGeographicLevelType();
		String[] geographicLevelApplicableOn= maintainEmbargoForm.getGeographicLevelApplicableOn();
		String[] geographicLevelValues= maintainEmbargoForm.getGeographicLevelValues();
		String[] glOperationFlag = maintainEmbargoForm.getGlOperationFlag();
		ArrayList<EmbargoGeographicLevelVO> geographicLevelVOs = null;
		
		if(session.getEmbargoVo().getGeographicLevels()!=null){
			geographicLevelVOs= (ArrayList<EmbargoGeographicLevelVO>)session.getEmbargoVo().getGeographicLevels();
		}
		if(geographicLevelVOs == null) {
			geographicLevelVOs = new ArrayList<EmbargoGeographicLevelVO>();
		embargoVO.setGeographicLevels(geographicLevelVOs);
		}
		if(glOperationFlag!=null){
			Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs = new ArrayList<EmbargoGeographicLevelVO>();
			for(int i = 0; i < glOperationFlag.length - 1 ; i++) {
				EmbargoGeographicLevelVO embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
				EmbargoGeographicLevelVO embargoGeographicLevelVOTemp = new EmbargoGeographicLevelVO();
				if(!"NOOP".equals(glOperationFlag[i])){
					if(OPERATION_FLAG_INSERT.equals(glOperationFlag[i])){  
						embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);						
						
					}else if(OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){ 
						embargoGeographicLevelVOTemp = geographicLevelVOs.get(i);
						embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
						embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVO.setCompanyCode(embargoGeographicLevelVOTemp.getCompanyCode());
						embargoGeographicLevelVO.setEmbargoReferenceNumber(embargoGeographicLevelVOTemp.getEmbargoReferenceNumber());
						embargoGeographicLevelVO.setEmbargoVersion(embargoGeographicLevelVOTemp.getEmbargoVersion());				
						
						if(geographicLevel[i].equalsIgnoreCase(embargoGeographicLevelVOTemp.getGeographicLevel())
								&& geographicLevelType[i].equalsIgnoreCase(embargoGeographicLevelVOTemp.getGeographicLevelType())
								&& geographicLevelApplicableOn[i].equalsIgnoreCase(embargoGeographicLevelVOTemp.getGeographicLevelApplicableOn())){
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
						}
						else{
							embargoGeographicLevelVO.setOperationFlag(OPERATION_FLAG_INSERT);
							embargoGeographicLevelVOTemp.setOperationFlag(OPERATION_FLAG_DELETE);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
						}						
						
					}
					else if(OPERATION_FLAG_DELETE.equals(glOperationFlag[i])){
						embargoGeographicLevelVO = geographicLevelVOs.get(i);
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);						
					}
					else if("DELETE".equals(glOperationFlag[i])){
						embargoGeographicLevelVOTemp = new EmbargoGeographicLevelVO();
						embargoGeographicLevelVOTemp = geographicLevelVOs.get(i);
						embargoGeographicLevelVOTemp.setOperationFlag(OPERATION_FLAG_DELETE);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
					}
				}
			}
			if(embargoGeographicLevelVOs!=null){
				embargoVO.setGeographicLevels((ArrayList<EmbargoGeographicLevelVO>)embargoGeographicLevelVOs);
			}
		}
		String[] parametercodes = maintainEmbargoForm.getParameterCode();
		String[] applicablevalues = maintainEmbargoForm.getIsIncluded();
		String[] values = maintainEmbargoForm.getValues();
		String[] operationalFlags = maintainEmbargoForm.getParamOperationalFlag();
		String[] applicableLevel = maintainEmbargoForm.getApplicableOn();
		
		ArrayList<EmbargoParameterVO> parameterVOs = null;
		if(session.getEmbargoVo().getParameters()!=null)
			{
			parameterVOs = (ArrayList<EmbargoParameterVO>)session.getEmbargoVo().getParameters();
			}
		if(parameterVOs == null) {
			parameterVOs = new ArrayList<EmbargoParameterVO>();
		embargoVO.setParameters(parameterVOs);
		}
		if(operationalFlags != null ){
			Collection<EmbargoParameterVO> embargoParameterVOs = new ArrayList<EmbargoParameterVO>();
			for (int i = 0; i < operationalFlags.length -1; i++) {							
				EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
				EmbargoParameterVO embargoParameterVOTemp = new EmbargoParameterVO();
				if(!"NOOP".equals(operationalFlags[i])){
					if(OPERATION_FLAG_INSERT.equals(operationalFlags[i])){  
						embargoParameterVO.setCompanyCode(companyCode);
						embargoParameterVO.setParameterCode(parametercodes[i].toUpperCase());
						embargoParameterVO.setParameterValues(values[i].toUpperCase());
						if(FLIGHT_NO.equals(parametercodes[i])){
							if(values[i].split("~").length==2){
								if(values[i].split("~")[0]!=null)
									{
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									}
								if(values[i].split("~")[1]!=null)
									{
									embargoParameterVO.setFlightNumber(values[i].split("~")[1]);
							}
							}
							if(values[i].split("~").length==1){
								if(values[i].indexOf("~")<0)
									{
									embargoParameterVO.setFlightNumber(values[i].split("~")[0]);
									}
								else
									{
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									}
									
							}
						}
						embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);
						embargoParameterVO.setApplicableLevel(applicableLevel[i]);
						embargoParameterVO.setParameterLevel(PARAMETER);
						embargoParameterVOs.add(embargoParameterVO);						
					}else if(OPERATION_FLAG_UPDATE.equals(operationalFlags[i])){ 
						embargoParameterVOTemp = parameterVOs.get(i);
						embargoParameterVO = new EmbargoParameterVO();
						embargoParameterVO.setCompanyCode(companyCode);
						embargoParameterVO.setEmbargoReferenceNumber(embargoParameterVOTemp.getEmbargoReferenceNumber());
						embargoParameterVO.setEmbargoVersion(embargoParameterVOTemp.getEmbargoVersion());
						embargoParameterVO.setParameterCode(parametercodes[i].toUpperCase());
						embargoParameterVO.setParameterValues(values[i].toUpperCase());
						if(FLIGHT_NO.equals(parametercodes[i])){
							if(values[i].split("~").length==2){
								if(values[i].split("~")[0]!=null)
									{
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									}
								if(values[i].split("~")[1]!=null)
									{
									embargoParameterVO.setFlightNumber(values[i].split("~")[1]);
							}
							}
							if(values[i].split("~").length==1){
								if(values[i].indexOf("~")<0)
									{
									embargoParameterVO.setFlightNumber(values[i].split("~")[0]);
									}
								else
									{
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									}
									
							}
						}
						embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);
						embargoParameterVO.setApplicableLevel(applicableLevel[i]);
						embargoParameterVO.setParameterLevel(PARAMETER);
							if(parametercodes[i].equalsIgnoreCase(embargoParameterVOTemp.getParameterCode())
									&& applicablevalues[i].equalsIgnoreCase(embargoParameterVOTemp.getApplicable())
										&& PARAMETER.equals(embargoParameterVOTemp.getParameterLevel())){
							embargoParameterVOs.add(embargoParameterVO);
							}else{
								embargoParameterVO.setOperationalFlag(OPERATION_FLAG_INSERT);
								embargoParameterVOTemp.setOperationalFlag(OPERATION_FLAG_DELETE);
								embargoParameterVOs.add(embargoParameterVO);
								embargoParameterVOs.add(embargoParameterVOTemp);
							}						
					}else if(OPERATION_FLAG_DELETE.equals(operationalFlags[i])){						
						embargoParameterVO = parameterVOs.get(i);
						embargoParameterVO.setCompanyCode(companyCode);
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);
						embargoParameterVOs.add(embargoParameterVO);	
					}else if("DELETE".equals(operationalFlags[i])){
						embargoParameterVOTemp = new EmbargoParameterVO();
						embargoParameterVOTemp = parameterVOs.get(i);
						embargoParameterVOTemp.setOperationalFlag(OPERATION_FLAG_DELETE);
						embargoParameterVOs.add(embargoParameterVOTemp);
					}					
				}			
			}
			if(embargoParameterVOs!=null){
				embargoVO.setParameters((ArrayList<EmbargoParameterVO>)embargoParameterVOs);
			}	
		}
		embargoVO.setRemarks(maintainEmbargoForm.getRemarks());
		embargoVO.setLastUpdatedUser(logonAttributes.getUserId());
		session.setEmbargoVo(embargoVO);
		log.log(Log.INFO, "session.setEmbargoVo = ", session.getEmbargoVo());
		return embargoVO;
	}
	
}
