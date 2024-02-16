/*
 * SaveCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintaincomplianceinfo;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo.MaintainComplianceInfoSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;
/**
 * Command class to save embargo
 * @author A-1854
 *
 */
public class SaveCommand extends BaseCommand{

	private static final String SUCCESS_MODE = "success";

	private Log log = LogFactory.getLogger("SAVE COMMAND");

	private static final String COUNTRY ="C";
	
	private static final String  AIRPORT="A";

	private static final String AIRPORT_GROUP ="ARPGRP";
	private static final String COUNTRY_GROUP ="CNTGRP";
	private static final String ORIGIN ="O";
	private static final String DESTINATION ="D";
	private static final String VIAPOINT ="V";
	
	private static final String ERROR_INVALID_AIRPORT ="shared.area.invalidairport";
	private static final String ERROR_INVALID_COUNTRY = "shared.area.invalidcountry";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";

	private static final String WORKFLOW_SCREEN_ID = "workflow.defaults.messageinbox";
	
	private static final String WORKFLOW_MODULE_NAME = "workflow.defaults";

	

	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1747
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainComplianceInfoForm maintainComplianceForm =
			(MaintainComplianceInfoForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();

		MessageInboxSession messageInboxSession = 
			getScreenSession(WORKFLOW_MODULE_NAME, WORKFLOW_SCREEN_ID);

		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
		MaintainComplianceInfoSession maintainComplianceSession =
			getScreenSession("reco.defaults", "reco.defaults.maintaincomplianceinfo");

		EmbargoRulesVO embargoVO = new EmbargoRulesVO();
		AreaDelegate areaDelegate=new AreaDelegate();
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		boolean isValid =  false;

		Collection<ErrorVO> errors = null;

		errors = validateForm(maintainComplianceForm,maintainComplianceSession, companyCode);
		
		boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
		if(hasBusinessPrivilege)
			maintainComplianceForm.setIsPrivilegedUser("Y");
		else
			maintainComplianceForm.setIsPrivilegedUser("N");	
		
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			maintainComplianceForm.setRefNumberFlag("true");
			maintainComplianceSession.removeEmbargoParameters();
			invocationContext.target = "screenload_failure";
			return;
		}
		else {
			errors = new ArrayList<ErrorVO>();
			//embargoVO = getEmbargoDetails(
					//maintainComplianceForm, maintainComplianceSession, companyCode);
			embargoVO = maintainComplianceSession.getEmbargoVo();
			//Active embargo on updation
			String[] geographicLevel = maintainComplianceForm.getGeographicLevel();
			String[] geographicLevelType = maintainComplianceForm.getGeographicLevelType();
			String[] geographicLevelValues= maintainComplianceForm.getGeographicLevelValues();
			String[] glOperationFlag = maintainComplianceForm.getGlOperationFlag();
			
			Collection<String> originAirports = new ArrayList<String>();
			Collection<String> destinationAirports = new ArrayList<String>();
			Collection<String> viapointAirports = new ArrayList<String>();
			Collection<String> airportgroups = new ArrayList<String>();
			Collection<String> originCountrys = new ArrayList<String>();
			Collection<String> destinationCountrys = new ArrayList<String>();
			Collection<String> viapointcountrys = new ArrayList<String>();
			Collection<String> countrygroups = new ArrayList<String>();
			if(glOperationFlag!=null){
				for(int i=0; i<glOperationFlag.length - 1 ; i++){
					if(!"NOOP".equals(glOperationFlag[i])){
						if(EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(glOperationFlag[i]) || EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){
							if(geographicLevelValues[i] != null || geographicLevelValues[i].trim().length()>0){								
								if(AIRPORT.equalsIgnoreCase(geographicLevelType[i])){
									String[] geoval  = geographicLevelValues[i].split(",");
									if(ORIGIN.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											originAirports.add(geoval[j].toUpperCase());
									}
									else if(DESTINATION.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											destinationAirports.add(geoval[j].toUpperCase());
									}
									else if(VIAPOINT.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											viapointAirports.add(geoval[j].toUpperCase());
									}
								}
								if(COUNTRY.equalsIgnoreCase(geographicLevelType[i])){	
									String[] geoval  = geographicLevelValues[i].split(",");									
									if(ORIGIN.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											originCountrys.add(geoval[j].toUpperCase());
									}
									else if(DESTINATION.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											destinationCountrys.add(geoval[j].toUpperCase());
									}
									else if(VIAPOINT.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											viapointcountrys.add(geoval[j].toUpperCase());
									}
								}
								if(AIRPORT_GROUP.equalsIgnoreCase(geographicLevelType[i])){									
									String[] geoval  = geographicLevelValues[i].split(",");
									for(int j=0;j<geoval.length;j++)
										airportgroups.add(geoval[j].toUpperCase());
								}
								if(COUNTRY_GROUP.equalsIgnoreCase(geographicLevelType[i])){									
									String[] geoval  = geographicLevelValues[i].split(",");
									for(int j=0;j<geoval.length;j++)
										countrygroups.add(geoval[j].toUpperCase());
								}								
							}
						}
					}
				}
			}

			try {
				if(originCountrys!=null && originCountrys.size()>0){
					areaDelegate.validateCountryCodes(companyCode, originCountrys);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invalidorigincountry");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			try {
				if(destinationCountrys!=null && destinationCountrys.size()>0){
					areaDelegate.validateCountryCodes(companyCode, destinationCountrys);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invaliddestinationcountry");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			try {
				if(viapointcountrys!=null && viapointcountrys.size()>0){
					areaDelegate.validateCountryCodes(companyCode, viapointcountrys);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invalidviapointcountry");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			
			try{
				if(originAirports!=null && originAirports.size()>0){
					areaDelegate.validateAirportCodes(companyCode, originAirports);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invalidoriginairport");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			try{
				if(destinationAirports!=null && destinationAirports.size()>0){
					areaDelegate.validateAirportCodes(companyCode, destinationAirports);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invaliddestinationairport");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			try{
				if(viapointAirports!=null && viapointAirports.size()>0){
					areaDelegate.validateAirportCodes(companyCode, viapointAirports);
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){
					for(ErrorVO err : errors){
						if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
							ErrorVO error = new ErrorVO("reco.defaults.invalidviapointairport");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							invocationContext.addError(error);
						}
					}
				}
			}
			try{
				if(airportgroups!=null && airportgroups.size()>0){
					String groupType = "ARPGRP";
					isValid = generalMasterGroupingDelegate.validateGroupNames(companyCode, airportgroups, groupType);
					if(!isValid){
						ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){									
					ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);					
				}
			}
			try{
				if(countrygroups!=null && countrygroups.size()>0){
					String groupType = "CNTGRP";
					isValid = generalMasterGroupingDelegate.validateGroupNames(companyCode, countrygroups, groupType);
					if(!isValid){
						ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(error);
					}
				}
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				//	invocationContext.addAllError(errors);
				if(errors != null && errors.size()>0){									
					ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					invocationContext.addError(error);					
				}
			}
			
			
			

			if(invocationContext.getErrors() != null && invocationContext.getErrors().size() > 0) {
				maintainComplianceForm.setCanSave("N");
				maintainComplianceForm.setRefNumberFlag("true");
				invocationContext.target = "screenload_failure";
				return;
			}
			Collection<EmbargoParameterVO> paramTemp = new ArrayList<EmbargoParameterVO>();
			Collection<EmbargoGeographicLevelVO> geoTemp = new ArrayList<EmbargoGeographicLevelVO>();
			if(("A".equals(embargoVO.getStatus()) || "S".equals(embargoVO.getStatus())) && EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(embargoVO.getOperationalFlag())){
				embargoVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				embargoVO.setStatus("D");
				if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
					for(EmbargoParameterVO param:embargoVO.getParameters()){
						if(!EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(param.getOperationalFlag())){
						param.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
							paramTemp.add(param);
						}
					}
				}
				if(embargoVO.getGeographicLevels()!=null && embargoVO.getGeographicLevels().size()>0){
					for(EmbargoGeographicLevelVO geolevel: embargoVO.getGeographicLevels()){
						if(!EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(geolevel.getOperationFlag())){
						geolevel.setOperationFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
							geoTemp.add(geolevel);
						}
					}
				}
				embargoVO.setParameters(paramTemp);
				embargoVO.setGeographicLevels(geoTemp);
			}
			if(EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag()))
				embargoVO.setStatus("D");
			ArrayList<EmbargoParameterVO> parameterVOs
			= (ArrayList<EmbargoParameterVO>)maintainComplianceSession.getEmbargoVo().getParameters();
			if(parameterVOs == null) {
				parameterVOs = new ArrayList<EmbargoParameterVO>();
			}
			boolean originInclude = false;
			//boolean originExclude = false;
			boolean destinationInclude = false;
			//boolean destinationExclude = false;
			boolean viapointInclude = false;
			//boolean viapointExclude = false;
			boolean isValidGeoPresent = false;
			if(embargoVO.getGeographicLevels()!=null && embargoVO.getGeographicLevels().size()>0){
				for(EmbargoGeographicLevelVO embargoGeographicLevelVO: embargoVO.getGeographicLevels()){
					if("IN".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "O".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						originInclude = true;
					}
					
					if("IN".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "D".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						destinationInclude = true;
					}
				
					if("IN".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "V".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						viapointInclude = true;
					}
					
					if(EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(embargoGeographicLevelVO.getOperationFlag()) || EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(embargoGeographicLevelVO.getOperationFlag()))
						isValidGeoPresent=true;
					EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
					embargoParameterVO.setParameterCode(embargoGeographicLevelVO.getGeographicLevelType());
					embargoParameterVO.setParameterLevel(embargoGeographicLevelVO.getGeographicLevel());
					embargoParameterVO.setParameterValues(embargoGeographicLevelVO.getGeographicLevelValues());
					embargoParameterVO.setApplicable(embargoGeographicLevelVO.getGeographicLevelApplicableOn());
					if("-".equals(embargoGeographicLevelVO.getGeographicLevelValues())){
						embargoParameterVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
					}
					else{
					embargoParameterVO.setOperationalFlag(embargoGeographicLevelVO.getOperationFlag());
					}
					embargoParameterVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterVO);
				}
				if(!isValidGeoPresent){
					EmbargoParameterVO embargoParameterOriginVO = new EmbargoParameterVO();
					embargoParameterOriginVO.setParameterCode(AIRPORT);
					embargoParameterOriginVO.setParameterLevel(ORIGIN);
					embargoParameterOriginVO.setParameterValues("-");
					embargoParameterOriginVO.setApplicable("IN");
					embargoParameterOriginVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
					embargoParameterOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterOriginVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterOriginVO);
					EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
					embargoParameterDestinationVO.setParameterCode(AIRPORT);
					embargoParameterDestinationVO.setParameterLevel(DESTINATION);
					embargoParameterDestinationVO.setParameterValues("-");
					embargoParameterDestinationVO.setApplicable("IN");
					embargoParameterDestinationVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
					embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterDestinationVO);
					EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
					embargoParameterViaPointVO.setParameterCode(AIRPORT);
					embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
					embargoParameterViaPointVO.setParameterValues("-");
					embargoParameterViaPointVO.setApplicable("IN");
					embargoParameterViaPointVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
					embargoParameterViaPointVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterViaPointVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterViaPointVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterViaPointVO);
				}
				embargoVO.setParameters(parameterVOs);
				ArrayList<EmbargoParameterVO> parameVOs = new ArrayList<EmbargoParameterVO>();
				parameVOs.addAll(embargoVO.getParameters());
				
				boolean isOrigin = false;
				boolean isDestination = false;
				boolean isViapoint = false;
				for(EmbargoParameterVO paraVo: embargoVO.getParameters()){
					if(!"P".equals(paraVo.getParameterLevel()) && !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(paraVo.getOperationalFlag()) && isValidGeoPresent){
						
							if(!originInclude && !isOrigin){
								isOrigin = true;
								EmbargoParameterVO embargoParameterOriginVO = new EmbargoParameterVO();
								embargoParameterOriginVO.setParameterCode(AIRPORT);
								embargoParameterOriginVO.setParameterLevel(ORIGIN);
								embargoParameterOriginVO.setParameterValues("-");
								embargoParameterOriginVO.setApplicable("IN");
								embargoParameterOriginVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
								embargoParameterOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
								embargoParameterOriginVO.setCompanyCode(embargoVO.getCompanyCode());
								embargoParameterOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
								parameVOs.add(embargoParameterOriginVO);
							}
							if(!destinationInclude && !isDestination){
								isDestination = true;
								EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
								embargoParameterDestinationVO.setParameterCode(AIRPORT);
								embargoParameterDestinationVO.setParameterLevel(DESTINATION);
								embargoParameterDestinationVO.setParameterValues("-");
								embargoParameterDestinationVO.setApplicable("IN");
								embargoParameterDestinationVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
								embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
								embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
								embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
								parameVOs.add(embargoParameterDestinationVO);
							}
							if(!viapointInclude && !isViapoint){
								isViapoint = true;
								EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
								embargoParameterViaPointVO.setParameterCode(AIRPORT);
								embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
								embargoParameterViaPointVO.setParameterValues("-");
								embargoParameterViaPointVO.setApplicable("IN");
								embargoParameterViaPointVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
								embargoParameterViaPointVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
								embargoParameterViaPointVO.setCompanyCode(embargoVO.getCompanyCode());
								embargoParameterViaPointVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
								parameVOs.add(embargoParameterViaPointVO);
							}
						
					}
					
				}
					
				embargoVO.setParameters(parameVOs);
				
			}
			else{
				ArrayList<EmbargoParameterVO> parameVOs = new ArrayList<EmbargoParameterVO>();
				if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0)
					parameVOs.addAll(embargoVO.getParameters());
				EmbargoParameterVO embargoParameterOriginVO = new EmbargoParameterVO();
				embargoParameterOriginVO.setParameterCode(AIRPORT);
				embargoParameterOriginVO.setParameterLevel(ORIGIN);
				embargoParameterOriginVO.setParameterValues("-");
				embargoParameterOriginVO.setApplicable("IN");
				embargoParameterOriginVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				embargoParameterOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterOriginVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterOriginVO);
				EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
				embargoParameterDestinationVO.setParameterCode(AIRPORT);
				embargoParameterDestinationVO.setParameterLevel(DESTINATION);
				embargoParameterDestinationVO.setParameterValues("-");
				embargoParameterDestinationVO.setApplicable("IN");
				embargoParameterDestinationVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterDestinationVO);
				EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
				embargoParameterViaPointVO.setParameterCode(AIRPORT);
				embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
				embargoParameterViaPointVO.setParameterValues("-");
				embargoParameterViaPointVO.setApplicable("IN");
				embargoParameterViaPointVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				embargoParameterViaPointVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterViaPointVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterViaPointVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterViaPointVO);
				embargoVO.setParameters(parameVOs);
			}
			log.log(Log.INFO, "EmbargoVO----------->", embargoVO.toString());
			if("Y".equals(maintainComplianceForm.getIsApproveFlag())){
				
				//if("D".equals(maintainComplianceForm.getStatus())){
					int displayPage = 1;
					EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
					embargoFilterVO.setEmbargoRefNumber(embargoVO.getEmbargoReferenceNumber());
					embargoFilterVO.setCompanyCode(embargoVO.getCompanyCode());
					//embargoFilterVO.setStatus("A");
					embargoFilterVO.setTotalRecordCount(-1);
					embargoFilterVO.setPageNumber(displayPage);
					Page<EmbargoDetailsVO> pg = findEmbargoVos(embargoFilterVO, displayPage);
					Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
					if(pg!=null){			
						for(EmbargoDetailsVO embargoDetailsVO: pg){
							if("A".equals(embargoDetailsVO.getStatus()) || "S".equals(embargoDetailsVO.getStatus())){
								embargoDetailsVO.setStatus("I");
								embargoDetailsVOs.add(embargoDetailsVO);
							}
						}
						if(embargoDetailsVOs!=null && embargoDetailsVOs.size()>0){
			try {
								embargoRulesDelegate.approveEmbargo(embargoDetailsVOs);
							} catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
								invocationContext.addAllError(errors);
								maintainComplianceForm.setCanSave("N");
								invocationContext.target = "screenload_failure";
								//e.printStackTrace();
								log.log(Log.INFO, "BusinessDelegateException----------->", businessDelegateException.getMessage());
							}
						}
					}
				//}
				if (embargoVO.getIsSuspended()) {
					 embargoVO.setStatus("S");
		    	}
		    	else{
		    		embargoVO.setStatus("A");
				}
			}
			try {
				String referenceNo =
					embargoRulesDelegate.saveEmbargoDetails(embargoVO);
				ErrorVO error = null;
				String referenceNoObj=referenceNo;
				maintainComplianceSession.setIsSaved(referenceNoObj);
				Object[] obj={referenceNoObj};
				error = new ErrorVO("embargo.save",obj);// Saved Successfully
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				maintainComplianceForm.setMode(SUCCESS_MODE);
				maintainComplianceForm.setCanSave("N");
				maintainComplianceForm.setRefNumber("");
				maintainComplianceSession.removeEmbargoParameters();
				LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				if("Y".equals(maintainComplianceForm.getIsApproveFlag()) && messageInboxSession.getMessageDetails()!=null)
					invocationContext.target = "approve_success";
				else
				invocationContext.target = "screenload_success";
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				invocationContext.addAllError(errors);
				maintainComplianceForm.setCanSave("N");
				maintainComplianceSession.removeEmbargoParameters();
				invocationContext.target = "screenload_failure";
			}
		}
	}

	private Collection<ErrorVO> validateForm(MaintainComplianceInfoForm form,
			MaintainComplianceInfoSession maintainComplianceSession, String companyCode) {
		EmbargoRulesVO embargoVo=getEmbargoDetails(form,maintainComplianceSession, companyCode);
		form.setRefNumber(embargoVo.getEmbargoReferenceNumber());
		//maintainComplianceSession.setEmbargoVo(embargoVo);
		//Setting values from form to vo
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		boolean isDuplicateValue = false;
		boolean isDuplicateParameter = false;
		boolean isnullParameter = false;
		boolean isnullValue = false;

		log.log(log.FINE,"validateForm()");
		

		if(form.getCategory() == null || form.getCategory().trim().length() == 0){
			error = new ErrorVO("reco.defaults.categoryempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		else{
			if((form.getRemarks()==null || form.getRemarks().trim().length()==0) && "O".equals(form.getCategory()) ){
				error = new ErrorVO("reco.defaults.remarksmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}

		if(form.getStartDate() == null || form.getStartDate().trim().length() == 0){
			error = new ErrorVO("reco.defaults.startdatempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		if(form.getEndDate() == null || form.getEndDate().trim().length() == 0){
			error = new ErrorVO("reco.defaults.enddateempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		String currentDate= DateUtilities.getCurrentDate(LocalDate.CALENDAR_DATE_FORMAT);
		if(form.getStartDate() != null && form.getStartDate().trim().length()>0){
			if(!form.getStartDate().equals(currentDate)){
				if(!DateUtilities.isValidDate(form.getStartDate(),
						LocalDate.CALENDAR_DATE_FORMAT)){
					error = new ErrorVO("reco.defaults.invalidstartdate");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}else if (DateUtilities.isLessThan(form.getStartDate(), currentDate,
						LocalDate.CALENDAR_DATE_FORMAT)
						&& !form.getIsSuspended()) {
					log.log(Log.INFO, "Validate Form : StartDate", form.getStartDate());
					isValid = false;
					log.log(log.FINE,"Validate Form : StartDate");
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.startdatecompare", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		if(form.getEndDate() != null && form.getEndDate().trim().length()>0){
			if(!form.getEndDate().equals(currentDate)){
				if (!DateUtilities.isValidDate(form.getEndDate(),
						LocalDate.CALENDAR_DATE_FORMAT)) {
					log.log(Log.INFO, "Validate Form : StartDate", form.getStartDate());
					isValid = false;
					log.log(log.FINE,"Validate Form : StartDate");
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.invalidenddate", obj);
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
					error = new ErrorVO("reco.defaults.invaliddate", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}
		}
		if(form.getParameterCode()!=null && form.getParameterCode().trim().length()>0
				&& (form.getParameterValue()==null || form.getParameterValue().trim().length()==0)){
			error = new ErrorVO("reco.defaults.parametervalueempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(form.getParameterCode()==null ||  form.getParameterCode().trim().length()==0
				&& (form.getParameterValue()!=null && form.getParameterValue().trim().length()>0)){
			error = new ErrorVO("reco.defaults.parametercodeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}		
				
		String embargoDesc = form.getEmbargoDesc();
		if(form.getEmbargoDesc() == null || form.getEmbargoDesc().trim().length()==0){
			isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.ComplianceDescription", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else if(embargoDesc != null && embargoDesc.length() > 3000){
			error = new ErrorVO("reco.defaults.complianceDescLengthexceeded");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
	
		if(form.getRemarks() != null && form.getRemarks().length() > 500){
			error = new ErrorVO("reco.defaults.embargoRemarksLengthexceeded");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		String[] flags=form.getOperationalFlag();
		int count=0;
		if(flags!=null){
			for(int i = 0;i<flags.length; i++) {
				if(EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(flags[i])){
					count++;
				}
			}
			log.log(Log.INFO, "flags.length = ", flags.length);
			log.log(Log.INFO, "count = ", count);
		}

		String[] geographicLevel = form.getGeographicLevel();
		String[] geographicLevelType = form.getGeographicLevelType();
		String[] geographicLevelValues= form.getGeographicLevelValues();
		String[] geoApplicableOn = form.getGeographicLevelApplicableOn();
		String[] glOperationFlag = form.getGlOperationFlag();
		
		boolean isDuplicate = false;
		boolean isDuplicateGeoValue = false;
		boolean isValidGeographicLevel = true;
		if(glOperationFlag!=null){
			for(int i=0; i<glOperationFlag.length - 1 ; i++){
				if(!"NOOP".equals(glOperationFlag[i])){
					if(EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(glOperationFlag[i]) || EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){
						if(geographicLevelValues[i] == null || geographicLevelValues[i].trim().length()==0){
							error = new ErrorVO("reco.defaults.geographiclevelvalueempty");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break;
						}
						else{							
							
							String[] valuesForParams = geographicLevelValues[i].split(",");
							if (valuesForParams.length > 0) {
								for (int y = 0; y < valuesForParams.length; y++) {
									for (int z = y + 1; z < valuesForParams.length; z++) {
										if (valuesForParams[y]
												.equals(valuesForParams[z])) {
											isDuplicateGeoValue = true;
											break;
										}
									}
								}
							}
						}
						for(int j = i+1; j<glOperationFlag.length;j++){
							if(!"NOOP".equals(glOperationFlag[j]) && !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(glOperationFlag[j]) && !"DELETE".equals(glOperationFlag[j])){
								if(geographicLevel[i].equalsIgnoreCase(geographicLevel[j]) && geographicLevelType[i].equalsIgnoreCase(geographicLevelType[j])){
									isDuplicate=true;
									break;
								}
								if(geographicLevelType[i].equalsIgnoreCase(geographicLevelType[j]) && geoApplicableOn[i].equalsIgnoreCase(geoApplicableOn[j])){
									if(!"NOOP".equals(glOperationFlag[j]) && !EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(glOperationFlag[j]) && !"DELETE".equals(glOperationFlag[j])){
										if((AIRPORT.equals(geographicLevelType[i]) && AIRPORT.equals(geographicLevelType[j]))
												|| (COUNTRY.equals(geographicLevelType[i]) && COUNTRY.equals(geographicLevelType[j]))){									
									String[] geovalprev  = geographicLevelValues[i].split(",");
									String[] geovalnext = geographicLevelValues[j].split(",");
									for(String prevval : geovalprev){
										for(String nextval : geovalnext){
											if(prevval.equalsIgnoreCase(nextval)){
												isValidGeographicLevel=false;
												break;
											}												
										}
									}
								}
							}
						}
					}
				}
			}
		}
			}
		}
		if(isDuplicate){
			error = new ErrorVO("reco.defaults.duplicategeographiclevel");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(isDuplicateGeoValue){
			error = new ErrorVO("reco.defaults.duplicategeographicvalue");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(!isValidGeographicLevel){
			error = new ErrorVO("reco.defaults.invalidgeographiclevel");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
	
		SCCDelegate sccDelegate = new SCCDelegate();
		String[] valuesParams = null;
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		if("SCC".equals(form.getParameterCode())){
			valuesParams = form.getParameterValue().toUpperCase().split(",");	
			Collection<String> sccs = new ArrayList<String>();
			Collection<SCCValidationVO> validsccs = null;
			try{					
				for(int j=0;j<valuesParams.length;j++)
					sccs.add(valuesParams[j].toUpperCase());						
				validsccs = sccDelegate.validateSCCCodes(companyCode, sccs);
			}
			catch (Exception e) {
				log.log(Log.SEVERE, "Exception caught");
			}
			if(validsccs == null){
				error = new ErrorVO(
				"reco.defaults.invalidscc");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}
		}
		if("SCCGRP".equals(form.getParameterCode())){
			valuesParams = form.getParameterValue().toUpperCase().split(",");	
			Collection<String> sccgroups = new ArrayList<String>();
			boolean isValidScc=true;
			try{					
				for(int j=0;j<valuesParams.length;j++)
					sccgroups.add(valuesParams[j].toUpperCase());						
				String groupType = "SCCGRP";
				isValidScc = generalMasterGroupingDelegate.validateGroupNames(companyCode, sccgroups, groupType);
			}
			catch (Exception e) {
				log.log(Log.SEVERE, "Exception caught");
			}
			if(!isValidScc){
				error = new ErrorVO(
				"reco.defaults.invalidsccgroup");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
				
			}
		}
		if("PRD".equals(form.getParameterCode())){
			
			LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			valuesParams = form.getParameterValue().toUpperCase().split(",");	
			for (String productName : valuesParams) {
				if(form.getStartDate() !=null && form.getStartDate().trim().length() >0 &&
						form.getEndDate() !=null && form.getEndDate().trim().length() >0){
					LocalDate strDate = new LocalDate(logonAttributes.getAirportCode(),
							Location.ARP, false);
					strDate.setDate(form.getStartDate());
					LocalDate endDate = new LocalDate(logonAttributes.getAirportCode(),
							Location.ARP, false);
					endDate.setDate(form.getEndDate());						
					String productCode = null;
					try {
						productCode = new EmbargoRulesDelegate().validateProduct(logonAttributes
								.getCompanyCode(), productName, strDate, endDate);
						if (productCode == null || productCode.trim().length() <= 0) {
							error = new ErrorVO("reco.defaults.invalidproduct");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							
						}
					} catch (BusinessDelegateException e) {
						log.log(Log.SEVERE, "<--BusinessDelegateException-->");
						errors= handleDelegateException(e);
					}
				}
				
			}
		
			
		}
		boolean isDuplicateParamValue =false;
		String scc = form.getScc();
		String sccgroup = form.getSccGroup();
		if(scc!=null){
			String[] valuesForParams = scc.split(",");
			if (valuesForParams.length > 0) {
				for (int y = 0; y < valuesForParams.length; y++) {
					for (int z = y + 1; z < valuesForParams.length; z++) {
						if (valuesForParams[y]
								.equals(valuesForParams[z])) {
							isDuplicateParamValue = true;
							break;
						}
					}
				}
			}
		}
		if(sccgroup!=null){
			String[] valuesForParams = sccgroup.split(",");
			if (valuesForParams.length > 0) {
				for (int y = 0; y < valuesForParams.length; y++) {
					for (int z = y + 1; z < valuesForParams.length; z++) {
						if (valuesForParams[y]
								.equals(valuesForParams[z])) {
							isDuplicateParamValue = true;
							break;
						}
					}
				}
			}
		}
		if(isDuplicateParamValue){
			error = new ErrorVO("reco.defaults.duplicateparametervalue");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(errors != null && errors.size() > 0){
			embargoVo.setParameters(null);
		}
		return errors;
	}

	private EmbargoRulesVO getEmbargoDetails(
			MaintainComplianceInfoForm maintainComplianceForm ,
			MaintainComplianceInfoSession session, String companyCode){

		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();
		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();

		EmbargoRulesVO embargoVO = null;
		if(session.getEmbargoVo()!=null) {
			embargoVO=session.getEmbargoVo();
		} else {
			embargoVO = new EmbargoRulesVO();
		}

		embargoVO.setCompanyCode(companyCode);
		embargoVO.setRuleType("C");
		embargoVO.setComplianceType("COM");
		embargoVO.setEmbargoDescription(maintainComplianceForm.getEmbargoDesc());
		//setting value for status
		String statusDescription=maintainComplianceForm.getStatus();
		Collection<OneTimeVO> embargoStatus = session.getEmbargoStatus();
		if(embargoStatus != null){
			for(OneTimeVO statusVO: embargoStatus){
				if(statusVO.getFieldDescription().equals(statusDescription)){
					embargoVO.setStatus(statusVO.getFieldValue());
				}
			}
		}		
		embargoVO.setIsSuspended(maintainComplianceForm.getIsSuspended());
		embargoVO.setCategory(maintainComplianceForm.getCategory());
	
		if((maintainComplianceForm.getParameterCode()!=null && maintainComplianceForm.getParameterCode().trim().length()>0)
				|| (maintainComplianceForm.getParameterValue()!=null && maintainComplianceForm.getParameterValue().trim().length()>0)){
			Collection<EmbargoParameterVO> parameters = new ArrayList<EmbargoParameterVO>();
				EmbargoParameterVO parameter = new EmbargoParameterVO();
				if(embargoVO.getParameters()==null){
					parameter.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				parameter.setCompanyCode(companyCode);
				parameter.setParameterCode(maintainComplianceForm.getParameterCode());
				parameter.setApplicable("IN");
				parameter.setParameterValues(maintainComplianceForm.getParameterValue().toUpperCase());
				parameter.setParameterLevel("P");
				parameters.add(parameter);
				}
				else{					
					for(EmbargoParameterVO param : embargoVO.getParameters()){
					if(maintainComplianceForm.getParameterCode().equals(param.getParameterCode())){
							param.setOperationalFlag(embargoVO.getOperationalFlag());
							param.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
							param.setCompanyCode(companyCode);
						param.setParameterValues(maintainComplianceForm.getParameterValue().toUpperCase());
							param.setEmbargoVersion(embargoVO.getEmbargoVersion());
						}
					else{
						param.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
						param.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
						param.setCompanyCode(companyCode);
						param.setParameterValues(maintainComplianceForm.getParameterValue().toUpperCase());
						param.setEmbargoVersion(embargoVO.getEmbargoVersion());
						parameters.add(param);
						parameter = new EmbargoParameterVO();
					parameter.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
				parameter.setCompanyCode(companyCode);
						parameter.setParameterCode(maintainComplianceForm.getParameterCode());
				parameter.setApplicable("IN");
						parameter.setParameterValues(maintainComplianceForm.getParameterValue().toUpperCase());
				parameter.setParameterLevel("P");
				parameters.add(parameter);
			}
				}					
			}
			if(parameters.size()>0)
			embargoVO.setParameters(parameters);
		}
			
		else{
			if(embargoVO.getParameters()!=null){
				for(EmbargoParameterVO param : embargoVO.getParameters()){
					param.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
					param.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					param.setCompanyCode(companyCode);
					param.setEmbargoVersion(embargoVO.getEmbargoVersion());
				}
			}
			else
			embargoVO.setParameters(null);
		}
		//Added by A-5160 for ICRD-27155
		logonAttributes =
			applicationSessionImpl.getLogonVO();
		String airportCode = logonAttributes.getAirportCode();
		embargoVO.setAirportCode(airportCode);

		LocalDate localStartDate = new LocalDate(
				LocalDate.NO_STATION,Location.NONE, false);
		if(maintainComplianceForm.getStartDate()!=null &&
				maintainComplianceForm.getStartDate().trim().length()>0) {
			embargoVO.setStartDate(localStartDate.setDate(
					maintainComplianceForm.getStartDate()));
		}

		LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(maintainComplianceForm.getEndDate() != null &&
				maintainComplianceForm.getEndDate().trim().length()>0) {
			embargoVO.setEndDate(localEndDate.setDate(
					maintainComplianceForm.getEndDate()));
		}

		
		String[] geographicLevel = maintainComplianceForm.getGeographicLevel();
		String[] geographicLevelType = maintainComplianceForm.getGeographicLevelType();
		String[] geographicLevelApplicableOn= maintainComplianceForm.getGeographicLevelApplicableOn();
		String[] geographicLevelValues= maintainComplianceForm.getGeographicLevelValues();
		String[] glOperationFlag = maintainComplianceForm.getGlOperationFlag();
		
		ArrayList<EmbargoGeographicLevelVO> geographicLevelVOs = null;
		if(session.getEmbargoVo().getGeographicLevels()!=null)
			geographicLevelVOs = (ArrayList<EmbargoGeographicLevelVO>)session.getEmbargoVo().getGeographicLevels();
		if(geographicLevelVOs == null) {
			geographicLevelVOs = new ArrayList<EmbargoGeographicLevelVO>();
			embargoVO.setGeographicLevels(geographicLevelVOs);
			//session.setEmbargoVo(embargoVO);
		}
		if(glOperationFlag!=null){
			Collection<EmbargoGeographicLevelVO> embargoGeographicLevelVOs = new ArrayList<EmbargoGeographicLevelVO>();
			for(int i = 0; i < glOperationFlag.length - 1 ; i++) {
				EmbargoGeographicLevelVO embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
				EmbargoGeographicLevelVO embargoGeographicLevelVOTemp = new EmbargoGeographicLevelVO();
				if(!"NOOP".equals(glOperationFlag[i])){
					if(EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(glOperationFlag[i])){  
						embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
						
					}
					else if(EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){ 
						embargoGeographicLevelVOTemp = geographicLevelVOs.get(i);
						//embargoGeographicLevelVO = geographicLevelVOs.get(i);
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
							embargoGeographicLevelVO.setOperationFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
							embargoGeographicLevelVOTemp.setOperationFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
						}
						
					}
					else if(EmbargoRulesVO.OPERATION_FLAG_DELETE.equals(glOperationFlag[i])){
						embargoGeographicLevelVO = geographicLevelVOs.get(i);
						/*embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());*/
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);						
					}
					else if("DELETE".equals(glOperationFlag[i])){
						embargoGeographicLevelVOTemp = new EmbargoGeographicLevelVO();
						embargoGeographicLevelVOTemp = geographicLevelVOs.get(i);
						embargoGeographicLevelVOTemp.setOperationFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
					}
				}
			}
			if(embargoGeographicLevelVOs!=null){
				embargoVO.setGeographicLevels((ArrayList<EmbargoGeographicLevelVO>)embargoGeographicLevelVOs);
			}
		}
	
		embargoVO.setRemarks(maintainComplianceForm.getRemarks());
		//LocalDate localLastUpdatedTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		embargoVO.setLastUpdatedUser(logonAttributes.getUserId());
		//embargoVO.setLastUpdatedTime(localLastUpdatedTime);
		session.setEmbargoVo(embargoVO);
		session.setEmbargoParameters(embargoVO.getParameters());
		log.log(Log.INFO, "session.setEmbargoVo = ", session.getEmbargoVo());
		return embargoVO;
	}
	
	private boolean checkBusinessPrivilege(String privilegeCode) {	
		SecurityAgent agent;
		boolean hasPrivilege = false;	
	    try {
			agent = SecurityAgent.getInstance();
			hasPrivilege=agent.checkPrivilegeForAction(privilegeCode);
		} catch(SystemException ex){
			log.log(Log.SEVERE, "Exception caught!");
		}
		return hasPrivilege;
	}
	private Page<EmbargoDetailsVO> findEmbargoVos(EmbargoFilterVO filter, int displayPage){
		Page<EmbargoDetailsVO> detailsVo = null;
		
		
		EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
		try{
			detailsVo = delegate.findEmbargos(filter, displayPage);
	    } catch (BusinessDelegateException e) {
			handleDelegateException(e);

		}
		return detailsVo;
	}
}
