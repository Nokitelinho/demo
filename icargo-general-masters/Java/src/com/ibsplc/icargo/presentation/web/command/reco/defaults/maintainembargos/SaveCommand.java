/*
 * SaveCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.agent.vo.AgentVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.shared.scc.vo.SCCValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.agent.AgentDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.commodity.CommodityDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.generalmastergrouping.GeneralMasterGroupingDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.scc.SCCDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
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

	private static final String ERROR_INVALID_COUNTRY = "shared.area.invalidcountry";

	private static final String ERROR_INVALID_STATION ="shared.station.invalidstation";

	//added as part of bug 109630 by A-3767 on 29Mar11
	private static final String ERROR_INVALID_AIRPORT ="shared.area.invalidairport";
	
	private static final String ERROR_INVALID_AIRPORTGROUP="shared.generalmastergrouping.invalidgroupname";
	
	private static final String STATION ="S";

	private static final String COUNTRY ="C";
	
	//added as part of bug 109630 by A-3767 on 29Mar11
	private static final String  AIRPORT="A";
	private static final String  FLIGHT_NO="FLTNUM";
	private static final String  PAYTYP="PAYTYP";
	// A-5153
	//private static final String PARAMETERTYPE_VALUES="tariff.others.paymenttype";
	private static final String PARAMETERTYPE_VALUES = "reco.defaults.paymenttype";

	private static final String AIRPORT_GROUP ="ARPGRP";
	private static final String COUNTRY_GROUP ="CNTGRP";

	// Added by A-5153 for ICRD-49150
	private static final String ORIGIN ="O";
	private static final String DESTINATION ="D";
	private static final String VIAPOINT ="V";
	private static final String INCLUDE = "IN";
	private static final String EXCLUDE = "EX";
	private static final String PARAMETER = "P";
	private static final String ACTIVE = "A";
	private static final String DRAFT = "D";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String OPERATION_FLAG_UPDATE = "U";
	private static final String OPERATION_FLAG_DELETE = "D";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
	
	private static final String WORKFLOW_SCREEN_ID = "workflow.defaults.messageinbox";
	
	private static final String WORKFLOW_MODULE_NAME = "workflow.defaults";
	
	private static final String LOCALE_EN_US = "en_US";
	private static final String SEG_ORIGIN ="L";//added by A-7924 as part of ICRD-299901 
	private static final String SEG_DESTINATION ="U";//added by A-7924 as part of ICRD-299901 
	private static final String SEGMENT ="S"; //added by A-7924 as part of ICRD-299901 
	private static final String EMBARGO ="EMB";
	/**
	 * The execute method in BaseCommand
	 *
	 * @author A-1747
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		MaintainEmbargoRulesForm maintainEmbargoForm =
			(MaintainEmbargoRulesForm) invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl =
			getApplicationSession();

		MessageInboxSession messageInboxSession = 
			getScreenSession(WORKFLOW_MODULE_NAME, WORKFLOW_SCREEN_ID);

		LogonAttributes logonAttributes =
			applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();

		EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
		MaintainEmbargoRulesSession maintainEmbargoSession =
			getScreenSession("reco.defaults", "reco.defaults.maintainembargo");

		EmbargoRulesVO embargoVO = new EmbargoRulesVO();
		AreaDelegate areaDelegate=new AreaDelegate();
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		

		Collection<ErrorVO> errors = null;

		errors = validateForm(maintainEmbargoForm,maintainEmbargoSession, companyCode);
		boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
		if(hasBusinessPrivilege)
			maintainEmbargoForm.setIsPrivilegedUser("Y");
		else
			maintainEmbargoForm.setIsPrivilegedUser("N");	
		
		if ((errors != null && errors.size() > 0) || (invocationContext.getErrors() != null)) {
			if(maintainEmbargoForm.getStatus()==null || maintainEmbargoForm.getStatus().trim().length()==0)
				maintainEmbargoSession.getEmbargoVo().setStatus(null);
			else if(maintainEmbargoForm.getStatus()!=null && maintainEmbargoForm.getStatus().trim().length()>0){ 
				String statusDescription=maintainEmbargoForm.getStatus();
				Collection<OneTimeVO> embargoStatus = maintainEmbargoSession.getEmbargoStatus();
				String statusValue = null;
				if(embargoStatus != null){
					for(OneTimeVO statusVO: embargoStatus){
						if(statusVO.getFieldDescription().equals(statusDescription)){
							statusValue = statusVO.getFieldValue();
						}
					}
				}
				if(!statusValue.equals(maintainEmbargoSession.getEmbargoVo().getStatus())){
					maintainEmbargoSession.getEmbargoVo().setStatus(statusValue);
					maintainEmbargoSession.getEmbargoVo().setOperationalFlag(OPERATION_FLAG_UPDATE);
				}
			}
			invocationContext.addAllError(errors);
			maintainEmbargoForm.setRefNumberFlag("true");
			invocationContext.target = "screenload_failure";
			return;
		}
		else {
			errors = new ArrayList<ErrorVO>();
			/*embargoVO = getEmbargoDetails(
					maintainEmbargoForm, maintainEmbargoSession, companyCode);*/
			embargoVO = maintainEmbargoSession.getEmbargoVo();
			//boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
			//Active embargo on updation
			
			String[] geographicLevel = maintainEmbargoForm.getGeographicLevel();
			String[] geographicLevelType = maintainEmbargoForm.getGeographicLevelType();
			String[] geographicLevelValues= maintainEmbargoForm.getGeographicLevelValues();
			String[] glOperationFlag = maintainEmbargoForm.getGlOperationFlag();
			

			Collection<String> originAirports = new ArrayList<String>();
			Collection<String> destinationAirports = new ArrayList<String>();
			Collection<String> viapointAirports = new ArrayList<String>();
			Collection<String> segOriginAirports = new ArrayList<String>();//Added by A-7924 as part of ICRD-299901  
			Collection<String> segDestAirports = new ArrayList<String>();//Added by A-7924 as part of ICRD-299901
			Collection<String> airportgroups = new ArrayList<String>();
			Collection<String> originCountrys = new ArrayList<String>();
			Collection<String> destinationCountrys = new ArrayList<String>();
			Collection<String> viapointcountrys = new ArrayList<String>();
			Collection<String> segOrigincountrys = new ArrayList<String>();//Added by A-7924 as part of ICRD-299901 
			Collection<String> segDestcountrys = new ArrayList<String>();//Added by A-7924 as part of ICRD-299901 
			Collection<String> countrygroups = new ArrayList<String>();
			if(glOperationFlag!=null){
				for(int i=0; i<glOperationFlag.length - 1 ; i++){
					if(!"NOOP".equals(glOperationFlag[i])){
						if(OPERATION_FLAG_INSERT.equals(glOperationFlag[i]) || OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){
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
									//Added by A-7924 as part of ICRD-299901 starts
									else if(SEG_ORIGIN.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											segOriginAirports.add(geoval[j].toUpperCase());
									}
									else if(SEG_DESTINATION.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											segDestAirports.add(geoval[j].toUpperCase());
									}
									//Added by A-7924 as part of ICRD-299901 ends
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
									//Added by A-7924 as part of ICRD-299901 starts
									else if(SEG_ORIGIN.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											segOrigincountrys.add(geoval[j].toUpperCase());
									}
									else if(SEG_DESTINATION.equals(geographicLevel[i])){
										for(int j=0;j<geoval.length;j++)
											segDestcountrys.add(geoval[j].toUpperCase());
									}
									//Added by A-7924 as part of ICRD-299901 ends
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
				if(originCountrys!=null && originCountrys.size()>0 || (segOrigincountrys!=null && segOrigincountrys.size()>0)){  //Modified  by A-7924 as part of ICRD-299901 
					areaDelegate.validateCountryCodes(companyCode, originCountrys);
					areaDelegate.validateCountryCodes(companyCode, segOrigincountrys);
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
				if(destinationCountrys!=null && destinationCountrys.size()>0 || (segDestcountrys!=null && segDestcountrys.size()>0)){  //Modified  by A-7924 as part of ICRD-299901 
					areaDelegate.validateCountryCodes(companyCode, destinationCountrys);
					areaDelegate.validateCountryCodes(companyCode, segDestcountrys);
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
				if(originAirports!=null && originAirports.size()>0 || (segOriginAirports!=null && segOriginAirports.size()>0)){  //Modified  by A-7924 as part of ICRD-299901 
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
				if(destinationAirports!=null && destinationAirports.size()>0 || (segDestAirports!=null && segDestAirports.size()>0)){  //Modified  by A-7924 as part of ICRD-299901
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
					//Changed for IASCB-144575
					Collection<GeneralMasterGroupVO> generalMasterGroupVOs=null;
					Collection<String> groupNamesSet=null;
					generalMasterGroupVOs = generalMasterGroupingDelegate
	    			.findGroupDetailsForGroupNames(companyCode
	    									,EMBARGO,groupType,airportgroups);
					if(generalMasterGroupVOs!=null){
		        		groupNamesSet=new ArrayList<>();
		        		for(GeneralMasterGroupVO generalMasterGroupVO:generalMasterGroupVOs){
		        			groupNamesSet.add(generalMasterGroupVO.getGroupName());
		        		}
		        	}
					if(groupNamesSet==null || airportgroups.size()!=groupNamesSet.size()){
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
					//Changed for IASCB-144575
					Collection<GeneralMasterGroupVO> generalMasterGroupVOs=null;
					Collection<String> groupNamesSet=null;
					generalMasterGroupVOs = generalMasterGroupingDelegate
	    			.findGroupDetailsForGroupNames(companyCode
	    									,EMBARGO,groupType,countrygroups);
					if(generalMasterGroupVOs!=null){
		        		groupNamesSet=new ArrayList<>();
		        		for(GeneralMasterGroupVO generalMasterGroupVO:generalMasterGroupVOs){
		        			groupNamesSet.add(generalMasterGroupVO.getGroupName());
		        		}
		        	}
					if(groupNamesSet==null || countrygroups.size()!=groupNamesSet.size()){
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
				if(maintainEmbargoForm.getStatus()==null || maintainEmbargoForm.getStatus().trim().length()==0)
					maintainEmbargoSession.getEmbargoVo().setStatus(null);
				else if(maintainEmbargoForm.getStatus()!=null && maintainEmbargoForm.getStatus().trim().length()>0){ 
					String statusDescription=maintainEmbargoForm.getStatus();
					Collection<OneTimeVO> embargoStatus = maintainEmbargoSession.getEmbargoStatus();
					String statusValue = null;
					if(embargoStatus != null){
						for(OneTimeVO statusVO: embargoStatus){
							if(statusVO.getFieldDescription().equals(statusDescription)){
								statusValue = statusVO.getFieldValue();
							}
						}
					}
					if(!statusValue.equals(maintainEmbargoSession.getEmbargoVo().getStatus())){
						maintainEmbargoSession.getEmbargoVo().setStatus(statusValue);
						maintainEmbargoSession.getEmbargoVo().setOperationalFlag(OPERATION_FLAG_UPDATE);
					}
				}
				maintainEmbargoForm.setCanSave("N");
				maintainEmbargoForm.setRefNumberFlag("true");
				invocationContext.target = "screenload_failure";
				return;
			}
			Collection<EmbargoParameterVO> paramTemp = new ArrayList<EmbargoParameterVO>();
			Collection<EmbargoGeographicLevelVO> geoTemp = new ArrayList<EmbargoGeographicLevelVO>();
			/*if("LH".equals(companyCode)){
				if((ACTIVE.equals(embargoVO.getStatus()) || "S".equals(embargoVO.getStatus()))  && OPERATION_FLAG_UPDATE.equals(embargoVO.getOperationalFlag())){
					embargoVO.setOperationalFlag(OPERATION_FLAG_INSERT);*/
			if(OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag())){
					//embargoVO.setStatus(DRAFT);
					if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
						for(EmbargoParameterVO param:embargoVO.getParameters()){
							if(!OPERATION_FLAG_DELETE.equals(param.getOperationalFlag())){
								param.setOperationalFlag(OPERATION_FLAG_INSERT);
								paramTemp.add(param);
							}
						}
					}
					if(embargoVO.getGeographicLevels()!=null && embargoVO.getGeographicLevels().size()>0){
						for(EmbargoGeographicLevelVO geolevel: embargoVO.getGeographicLevels()){
							if(!OPERATION_FLAG_DELETE.equals(geolevel.getOperationFlag())){
								geolevel.setOperationFlag(OPERATION_FLAG_INSERT);
								geoTemp.add(geolevel);
							}
						}
					}
					embargoVO.setParameters(paramTemp);
					embargoVO.setGeographicLevels(geoTemp);
				}
			/*	}
			}*/
			/*if(OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag()) && "LH".equals(companyCode))
				embargoVO.setStatus(DRAFT);
			else if(!"LH".equals(companyCode)){
				embargoVO.setStatus(ACTIVE);
			}*/
			ArrayList<EmbargoParameterVO> parameterVOs
			= (ArrayList<EmbargoParameterVO>)maintainEmbargoSession.getEmbargoVo().getParameters();
			if(parameterVOs == null) {
				parameterVOs = new ArrayList<EmbargoParameterVO>();
			}
			boolean originInclude = false;
			boolean segoriginInclude = false; //Added by A-7924 as part of ICRD-299901 
			//boolean originExclude = false;
			boolean destinationInclude = false;
			boolean segdestinationInclude = false; //Added by A-7924 as part of ICRD-299901 
			//boolean destinationExclude = false;
			boolean viapointInclude = false;
			//boolean viapointExclude = false;
			
			boolean isValidGeoPresent = false;
			if(embargoVO.getGeographicLevels()!=null && embargoVO.getGeographicLevels().size()>0){
				for(EmbargoGeographicLevelVO embargoGeographicLevelVO: embargoVO.getGeographicLevels()){
					if(INCLUDE.equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && ORIGIN.equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						originInclude = true;
					}
					/*if("EX".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "O".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !"D".equals(embargoGeographicLevelVO.getOperationFlag())){
						originExclude = true;
					}*/
					if(INCLUDE.equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && DESTINATION.equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						destinationInclude = true;
					}
					/*if("EX".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "D".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !"D".equals(embargoGeographicLevelVO.getOperationFlag())){
						destinationExclude = true;
					}*/
					if(INCLUDE.equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && VIAPOINT.equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						viapointInclude = true;
					}
					//Added by A-7924 as part of ICRD-299901 starts
					if(INCLUDE.equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && SEG_ORIGIN.equals(embargoGeographicLevelVO.getGeographicLevel()) //Modified by A-7924 as part of ICRD-299901 starts
							&& !OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						segoriginInclude = true;
					}
					/*if("EX".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "O".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !"D".equals(embargoGeographicLevelVO.getOperationFlag())){
						originExclude = true;
					}*/
					if(INCLUDE.equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && SEG_DESTINATION.equals(embargoGeographicLevelVO.getGeographicLevel()) //Modified by A-7924 as part of ICRD-299901 starts
							&& !OPERATION_FLAG_DELETE.equals(embargoGeographicLevelVO.getOperationFlag())){
						segdestinationInclude = true;
					}
					//Added by A-7924 as part of ICRD-299901 ends
					/*if("EX".equals(embargoGeographicLevelVO.getGeographicLevelApplicableOn()) && "V".equals(embargoGeographicLevelVO.getGeographicLevel())
							&& !"D".equals(embargoGeographicLevelVO.getOperationFlag())){
						viapointExclude = true;
					}*/
					if(OPERATION_FLAG_INSERT.equals(embargoGeographicLevelVO.getOperationFlag()) || OPERATION_FLAG_UPDATE.equals(embargoGeographicLevelVO.getOperationFlag()))
						isValidGeoPresent=true;
					EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
					embargoParameterVO.setParameterCode(embargoGeographicLevelVO.getGeographicLevelType());
					embargoParameterVO.setParameterLevel(embargoGeographicLevelVO.getGeographicLevel());
					embargoParameterVO.setParameterValues(embargoGeographicLevelVO.getGeographicLevelValues());
					embargoParameterVO.setApplicable(embargoGeographicLevelVO.getGeographicLevelApplicableOn());
					if("-".equals(embargoGeographicLevelVO.getGeographicLevelValues())){
						embargoParameterVO.setOperationalFlag(OPERATION_FLAG_DELETE);
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
					embargoParameterOriginVO.setApplicable(INCLUDE);
					embargoParameterOriginVO.setOperationalFlag(OPERATION_FLAG_INSERT);
					embargoParameterOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterOriginVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterOriginVO);
					EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
					embargoParameterDestinationVO.setParameterCode(AIRPORT);
					embargoParameterDestinationVO.setParameterLevel(DESTINATION);
					embargoParameterDestinationVO.setParameterValues("-");
					embargoParameterDestinationVO.setApplicable(INCLUDE);
					embargoParameterDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
					embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					//Added by A-7924 as part of ICRD-299901 starts
					parameterVOs.add(embargoParameterDestinationVO);
					EmbargoParameterVO embargoParameterSegOriginVO = new EmbargoParameterVO();
					embargoParameterSegOriginVO.setParameterCode(AIRPORT);
					embargoParameterSegOriginVO.setParameterLevel(SEG_ORIGIN);
					embargoParameterSegOriginVO.setParameterValues("-");
					embargoParameterSegOriginVO.setApplicable(INCLUDE);
					embargoParameterSegOriginVO.setOperationalFlag(OPERATION_FLAG_INSERT);
					embargoParameterSegOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterSegOriginVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterSegOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterSegOriginVO);
					EmbargoParameterVO embargoParameterSegDestinationVO = new EmbargoParameterVO();
					embargoParameterSegDestinationVO.setParameterCode(AIRPORT);
					embargoParameterSegDestinationVO.setParameterLevel(SEG_DESTINATION);
					embargoParameterSegDestinationVO.setParameterValues("-");
					embargoParameterSegDestinationVO.setApplicable(INCLUDE);
					embargoParameterSegDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
					embargoParameterSegDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
					embargoParameterSegDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
					embargoParameterSegDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
					parameterVOs.add(embargoParameterSegDestinationVO);
					//Added by A-7924 as part of ICRD-299901 ends
					EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
					embargoParameterViaPointVO.setParameterCode(AIRPORT);
					embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
					embargoParameterViaPointVO.setParameterValues("-");
					embargoParameterViaPointVO.setApplicable(INCLUDE);
					embargoParameterViaPointVO.setOperationalFlag(OPERATION_FLAG_INSERT);
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
					boolean isSegOrigin = false;
					boolean isSegDestination = false;
					boolean isViapoint = false;
					for(EmbargoParameterVO paraVo: embargoVO.getParameters()){
					if(EXCLUDE.equals(paraVo.getApplicable()) && !OPERATION_FLAG_DELETE.equals(paraVo.getOperationalFlag()) && PARAMETER.equals(paraVo.getParameterLevel())){							
								String parCode = paraVo.getParameterCode();
								boolean isInclude = false;
								for(EmbargoParameterVO parVo: embargoVO.getParameters()){
									if(parCode.equals(parVo.getParameterCode()) && INCLUDE.equals(parVo.getApplicable()) && !OPERATION_FLAG_DELETE.equals(parVo.getOperationalFlag())){
										isInclude=true;
									}
								}
								if(!isInclude /* &&!OPERATION_FLAG_UPDATE.equals(paraVo.getOperationalFlag())*/){
									
										EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
										embargoParameterVO.setParameterCode(paraVo.getParameterCode());
										embargoParameterVO.setParameterLevel(paraVo.getParameterLevel());
										embargoParameterVO.setApplicableLevel(paraVo.getApplicableLevel());
										embargoParameterVO.setParameterValues("-");
										embargoParameterVO.setApplicable(INCLUDE);
										if (paraVo.getOperationalFlag() != null) {
											embargoParameterVO.setOperationalFlag(paraVo.getOperationalFlag());
										} else {
										embargoParameterVO.setOperationalFlag(OPERATION_FLAG_INSERT);
										}
										embargoParameterVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
										embargoParameterVO.setCompanyCode(embargoVO.getCompanyCode());
										embargoParameterVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
										parameVOs.add(embargoParameterVO);
									
								}
							}
					else if(!PARAMETER.equals(paraVo.getParameterLevel()) && !OPERATION_FLAG_DELETE.equals(paraVo.getOperationalFlag()) && isValidGeoPresent){
						if(!originInclude && !isOrigin){
									isOrigin=true;
							EmbargoParameterVO embargoParameterOriginVO = new EmbargoParameterVO();
							embargoParameterOriginVO.setParameterCode(AIRPORT);
							embargoParameterOriginVO.setParameterLevel(ORIGIN);
							embargoParameterOriginVO.setParameterValues("-");
							embargoParameterOriginVO.setApplicable(INCLUDE);
							if (paraVo.getOperationalFlag() != null) {
								embargoParameterOriginVO
										.setOperationalFlag(paraVo
												.getOperationalFlag());
							} else {
								embargoParameterOriginVO
										.setOperationalFlag(OPERATION_FLAG_INSERT);
							}
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
							embargoParameterDestinationVO.setApplicable(INCLUDE);
							if (paraVo.getOperationalFlag() != null) {
								embargoParameterDestinationVO
										.setOperationalFlag(paraVo
												.getOperationalFlag());
							} else {
								embargoParameterDestinationVO
										.setOperationalFlag(OPERATION_FLAG_INSERT);
							}
							embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
							embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
							embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
							parameVOs.add(embargoParameterDestinationVO);
								}
						//Added by A-7924 as part of ICRD-299901 starts
						if(!segdestinationInclude && !isSegDestination){
							isSegDestination = true;
							EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
							embargoParameterDestinationVO.setParameterCode(AIRPORT);
							embargoParameterDestinationVO.setParameterLevel(SEG_DESTINATION);
							embargoParameterDestinationVO.setParameterValues("-");
							embargoParameterDestinationVO.setApplicable(INCLUDE);
							embargoParameterDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
							embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
							embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
							embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
							parameVOs.add(embargoParameterDestinationVO);
						}
						if(!segoriginInclude && !isSegOrigin){
							isSegOrigin = true;
							EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
							embargoParameterDestinationVO.setParameterCode(AIRPORT);
							embargoParameterDestinationVO.setParameterLevel(SEG_ORIGIN);
							embargoParameterDestinationVO.setParameterValues("-");
							embargoParameterDestinationVO.setApplicable(INCLUDE);
							embargoParameterDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
							embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
							embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
							embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
							parameVOs.add(embargoParameterDestinationVO);
						}
						//Added by A-7924 as part of ICRD-299901 ends
						if(!viapointInclude && !isViapoint){
									isViapoint = true;
							EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
							embargoParameterViaPointVO.setParameterCode(AIRPORT);
							embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
							embargoParameterViaPointVO.setParameterValues("-");
							embargoParameterViaPointVO.setApplicable(INCLUDE);
							if (paraVo.getOperationalFlag() != null) {
								embargoParameterViaPointVO
										.setOperationalFlag(paraVo
												.getOperationalFlag());
							} else {
								embargoParameterViaPointVO
										.setOperationalFlag(OPERATION_FLAG_INSERT);
							}
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
				embargoParameterOriginVO.setApplicable(INCLUDE);
				embargoParameterOriginVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoParameterOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterOriginVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterOriginVO);
				EmbargoParameterVO embargoParameterDestinationVO = new EmbargoParameterVO();
				embargoParameterDestinationVO.setParameterCode(AIRPORT);
				embargoParameterDestinationVO.setParameterLevel(DESTINATION);
				embargoParameterDestinationVO.setParameterValues("-");
				embargoParameterDestinationVO.setApplicable(INCLUDE);
				embargoParameterDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoParameterDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterDestinationVO);
				//Added by A-7924 as part of ICRD-299901 starts
				EmbargoParameterVO embargoParameterSegOriginVO = new EmbargoParameterVO();
				embargoParameterSegOriginVO.setParameterCode(AIRPORT);
				embargoParameterSegOriginVO.setParameterLevel(SEG_ORIGIN);
				embargoParameterSegOriginVO.setParameterValues("-");
				embargoParameterSegOriginVO.setApplicable(INCLUDE);
				embargoParameterSegOriginVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoParameterSegOriginVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterSegOriginVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterSegOriginVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterSegOriginVO);
				EmbargoParameterVO embargoParameterSegDestinationVO = new EmbargoParameterVO();
				embargoParameterSegDestinationVO.setParameterCode(AIRPORT);
				embargoParameterSegDestinationVO.setParameterLevel(SEG_DESTINATION);
				embargoParameterSegDestinationVO.setParameterValues("-");
				embargoParameterSegDestinationVO.setApplicable(INCLUDE);
				embargoParameterSegDestinationVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoParameterSegDestinationVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterSegDestinationVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterSegDestinationVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterSegDestinationVO);
				//Added by A-7924 as part of ICRD-299901 ends
				EmbargoParameterVO embargoParameterViaPointVO = new EmbargoParameterVO();
				embargoParameterViaPointVO.setParameterCode(AIRPORT);
				embargoParameterViaPointVO.setParameterLevel(VIAPOINT);
				embargoParameterViaPointVO.setParameterValues("-");
				embargoParameterViaPointVO.setApplicable(INCLUDE);
				embargoParameterViaPointVO.setOperationalFlag(OPERATION_FLAG_INSERT);
				embargoParameterViaPointVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterViaPointVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterViaPointVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterViaPointVO);
				
				for(EmbargoParameterVO paraVo: embargoVO.getParameters()){
					if(EXCLUDE.equals(paraVo.getApplicable()) && !OPERATION_FLAG_DELETE.equals(paraVo.getOperationalFlag()) && PARAMETER.equals(paraVo.getParameterLevel())){							
								String parCode = paraVo.getParameterCode();
								boolean isInclude = false;
								for(EmbargoParameterVO parVo: embargoVO.getParameters()){
									if(parCode.equals(parVo.getParameterCode()) && INCLUDE.equals(parVo.getApplicable()) && !OPERATION_FLAG_DELETE.equals(parVo.getOperationalFlag())){
										isInclude=true;
									}
								}
								if(!isInclude){
									
										EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
										embargoParameterVO.setParameterCode(paraVo.getParameterCode());
										embargoParameterVO.setParameterLevel(paraVo.getParameterLevel());
										embargoParameterVO.setApplicableLevel(paraVo.getApplicableLevel());
										embargoParameterVO.setParameterValues("-");
										embargoParameterVO.setApplicable(INCLUDE);
										embargoParameterVO.setOperationalFlag(OPERATION_FLAG_INSERT);
										embargoParameterVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
										embargoParameterVO.setCompanyCode(embargoVO.getCompanyCode());
										embargoParameterVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
										parameVOs.add(embargoParameterVO);
									
								}
							}
				}
				embargoVO.setParameters(parameVOs);
			}
			if(embargoVO.getDaysOfOperationFlag()!=null && embargoVO.getDaysOfOperationFlag().trim().length()>0){
				ArrayList<EmbargoParameterVO> parameVOs = new ArrayList<EmbargoParameterVO>();
				if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0)
					parameVOs.addAll(embargoVO.getParameters());
				EmbargoParameterVO embargoParameterVO = new EmbargoParameterVO();
				embargoParameterVO.setParameterCode("DOW");
				embargoParameterVO.setParameterLevel(PARAMETER);
				embargoParameterVO.setApplicable("EQ");
				embargoParameterVO.setParameterValues(embargoVO.getDaysOfOperation());
				embargoParameterVO.setApplicableLevel(embargoVO.getDaysOfOperationApplicableOn());
				embargoParameterVO.setOperationalFlag(embargoVO.getDaysOfOperationFlag());
				embargoParameterVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
				embargoParameterVO.setCompanyCode(embargoVO.getCompanyCode());
				embargoParameterVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
				parameVOs.add(embargoParameterVO);
				embargoVO.setParameters(parameVOs);
				
			}
			
			if((maintainEmbargoSession.getEmbargoVo().getStatus()==null || maintainEmbargoSession.getEmbargoVo().getStatus().trim().length()==0) 
					&& (invocationContext.getErrors() == null)){
				maintainEmbargoSession.getEmbargoVo().setStatus("A");
			}
			
			/*if("Y".equals(maintainEmbargoForm.getIsApproveFlag())){ 
				
				//if(DRAFT.equals(maintainEmbargoForm.getStatus())){
					int displayPage = 1;
					EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
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
							}else if("D".equals(embargoDetailsVO.getStatus())){
								embargoDetailsVO.setStatus(embargoVO.getIsSuspended() ? "S" : "A");
								newVerToBeCreated = false;
								embargoDetailsVOs.add(embargoDetailsVO);
							}
						}
						if(embargoDetailsVOs!=null && embargoDetailsVOs.size()>0){
							try {
								embargoRulesDelegate.approveEmbargo(embargoDetailsVOs);
							} catch (BusinessDelegateException businessDelegateException) {
								errors = handleDelegateException(businessDelegateException);
								invocationContext.addAllError(errors);
								maintainEmbargoForm.setCanSave("N");
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
				}*/
			if(embargoVO.getCategory() == null || embargoVO.getCategory().trim().length() == 0){
				embargoVO.setCategory("O");
			}
			if(embargoVO.getComplianceType() == null || embargoVO.getComplianceType().trim().length() == 0){
				// Modified by A-5290 for ICRD-185725
				embargoVO.setComplianceType("EMB");
			}
			log.log(Log.INFO, "EmbargoVO----------->", embargoVO.toString());
			//added for ICRD-213193 by A-7815 
			Collection<EmbargoLocalLanguageVO> localLanguageVOs =new ArrayList<EmbargoLocalLanguageVO>();
			Map<String, String> localLanguageEmbargos = (HashMap<String, String>) maintainEmbargoSession.getLocalLanguageEmbargo();
		   	if(localLanguageEmbargos!=null && !localLanguageEmbargos.isEmpty()) {
		   		EmbargoLocalLanguageVO localLanguageVO =null;
		   		for(String language: localLanguageEmbargos.keySet()){
		   			if (!language.equalsIgnoreCase(LOCALE_EN_US) && localLanguageEmbargos.get(language) != null 
		   					&& localLanguageEmbargos.get(language).trim().length() >0) {
		   				localLanguageVO=new EmbargoLocalLanguageVO();
			   			localLanguageVO.setCompanyCode(companyCode);
			   			localLanguageVO.setEmbargoDescription(localLanguageEmbargos.get(language));
			   			localLanguageVO.setEmbargoLocalLanguage(language);
			   			localLanguageVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
			   			localLanguageVO.setEmbargoVersion(embargoVO.getEmbargoVersion());
			   			localLanguageVOs.add(localLanguageVO);
					}
		   		}
		   	embargoVO.setLocalLanguageVOs(localLanguageVOs);
		   	}
			try {
				String referenceNo = null;
				if("Y".equals(maintainEmbargoForm.getIsApproveFlag())){
					boolean newVerToBeCreated = true;
					//if(DRAFT.equals(maintainEmbargoForm.getStatus())){ 
						int displayPage = 1;
						EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
						embargoFilterVO.setEmbargoRefNumber(embargoVO.getEmbargoReferenceNumber());
						embargoFilterVO.setCompanyCode(embargoVO.getCompanyCode());
						//embargoFilterVO.setStatus("A");
						embargoFilterVO.setTotalRecordCount(-1);
						embargoFilterVO.setPageNumber(displayPage);
						Page<EmbargoDetailsVO> pg = findEmbargoVos(embargoFilterVO, displayPage);
						Collection<EmbargoDetailsVO> embargoDetailsVOs = new ArrayList<EmbargoDetailsVO>();
						if(pg!=null){			
							for(EmbargoDetailsVO embargoDetailsVO: pg){
								embargoDetailsVO.setApproveFlag(true);
								embargoDetailsVO.setSourceId(maintainEmbargoForm.getNumericalScreenId());
								if("A".equals(embargoDetailsVO.getStatus()) || "S".equals(embargoDetailsVO.getStatus())){
									embargoDetailsVO.setStatus("I");
									embargoDetailsVOs.add(embargoDetailsVO);
								}else if("D".equals(embargoDetailsVO.getStatus())){
									//Version check is addded by E-1289 for ICRD-210543 in order to prevent multiple drafts becoming in active status
									if(embargoDetailsVO.getEmbargoVersion() == embargoVO.getEmbargoVersion()){
									embargoDetailsVO.setStatus(embargoVO.getIsSuspended() ? "S" : "A");
									newVerToBeCreated = false;
									embargoDetailsVOs.add(embargoDetailsVO);
								}
								}
							}
						}
					

					if(embargoDetailsVOs!=null && embargoDetailsVOs.size()>0){
						try {
							embargoRulesDelegate.approveEmbargo(embargoDetailsVOs);
						} catch (BusinessDelegateException businessDelegateException) {
							errors = handleDelegateException(businessDelegateException);
							invocationContext.addAllError(errors);
							maintainEmbargoForm.setCanSave("N");
							invocationContext.target = "screenload_failure";
							//e.printStackTrace();
							log.log(Log.INFO, "BusinessDelegateException----------->", businessDelegateException.getMessage());
						}
					 }
					//ICRD-183607 Start by E-1138
					if (embargoVO != null) {
						embargoVO.setStatus(embargoVO.getIsSuspended() ? "S" : "A");
						embargoVO.setOperationalFlag(newVerToBeCreated?OPERATION_FLAG_INSERT:OPERATION_FLAG_UPDATE);
						if(newVerToBeCreated==false){
							LocalDate localLastUpdatedTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
							embargoVO.setLastUpdatedTime(localLastUpdatedTime);
						}
				    	referenceNo = embargoRulesDelegate.saveEmbargoDetails(embargoVO);
					}
				}else{
					referenceNo = embargoRulesDelegate.saveEmbargoDetails(embargoVO);
				}
				ErrorVO error = null;
				String referenceNoObj=referenceNo;
				maintainEmbargoSession.setIsSaved(referenceNoObj);
				Object[] obj={referenceNoObj};
				error = new ErrorVO("embargo.save",obj);// Saved Successfully
				error.setErrorDisplayType(ErrorDisplayType.INFO);
				errors.add(error);
				invocationContext.addAllError(errors);
				maintainEmbargoForm.setMode(SUCCESS_MODE);
				maintainEmbargoForm.setCanSave("N");
				maintainEmbargoForm.setRefNumber("");
				maintainEmbargoForm.setOriginType(
						maintainEmbargoForm.getOriginType());
				maintainEmbargoForm.setOrigin(
						maintainEmbargoForm.getOrigin());
				maintainEmbargoForm.setDestination(
						maintainEmbargoForm.getDestination());
				maintainEmbargoForm.setDestinationType(
						maintainEmbargoForm.getDestinationType());

				LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				if(("Y".equals(maintainEmbargoForm.getIsApproveFlag()) || "Y".equals(maintainEmbargoForm.getIsModifyFlag()))&& messageInboxSession.getMessageDetails()!=null)
					invocationContext.target = "approve_success";
				else
				invocationContext.target = "screenload_success";
			}catch(BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
				invocationContext.addAllError(errors);
				maintainEmbargoForm.setCanSave("N");
				invocationContext.target = "screenload_failure";
			}
		}
	}

	private Collection<ErrorVO> validateForm(MaintainEmbargoRulesForm form,
			MaintainEmbargoRulesSession maintainEmbargoSession, String companyCode) {
		EmbargoRulesVO embargoVo=getEmbargoDetails(form,maintainEmbargoSession, companyCode);
		form.setRefNumber(embargoVo.getEmbargoReferenceNumber());
		Collection<OneTimeVO>  embargoParameters  = maintainEmbargoSession.getEmbargoParameters();
		//maintainEmbargoSession.setEmbargoVo(embargoVo);
		//Setting values from form to vo
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		boolean isValid = true;
		boolean isDuplicateValue = false;
		boolean isDuplicateParameter = false;
		boolean isnullParameter = false;
		boolean isnullValue = false;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		log.log(log.FINE,"validateForm()");
		if ("".equals(form.getEmbargoLevel()) || 
				form.getEmbargoLevel() == null) {
			isValid = false;
			Object[] obj = { "Level" };
			error = new ErrorVO("reco.defaults.level", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		/*if(form.getCategory() == null || form.getCategory().trim().length() == 0){
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
		if(form.getComplianceType() == null || form.getComplianceType().trim().length() == 0){
			error = new ErrorVO("reco.defaults.compliancetypeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}*/
		
		if(form.getApplicableTransactions() == null || form.getApplicableTransactions().trim().length() == 0){
			error = new ErrorVO("reco.defaults.applicabletransactionempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
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

				if(form.getOriginType()!=null && form.getDestinationType()!=null 
				&& form.getDestinationType().equals(form.getOriginType())
				&& form.getOrigin()!=null && form.getDestination()!=null
				&& form.getOrigin().trim().length()>0 && form.getDestination().trim().length()>0 
				&& form.getOrigin().equalsIgnoreCase(form.getDestination())){

					isValid = false;
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.origin.destination", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
				
				if(form.getViaPointType()!=null 
					&& (form.getViaPointType().equals(form.getOriginType())|| form.getViaPointType().equals(form.getDestinationType()) )
					&& form.getViaPoint()!=null && form.getViaPoint().trim().length()>0 
					&& (form.getViaPoint().equalsIgnoreCase(form.getDestination()) || form.getViaPoint().equalsIgnoreCase(form.getOrigin()))){

							isValid = false;
							Object[] obj = { "" };
							error = new ErrorVO("reco.defaults.viapoint.origindestination", obj);
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
					}

		String embargoDesc = form.getEmbargoDesc();
		if(form.getEmbargoDesc() == null || form.getEmbargoDesc().trim().length()==0){
			isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.Description", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}else if(embargoDesc != null && embargoDesc.length() > 3000){
			error = new ErrorVO("reco.defaults.embargoDescLengthexceeded");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		String[] geographicLevel = form.getGeographicLevel(); //added by A-7924 as part of ICRD-299901  
		
		// Added by A-5153 for ICRD-49150
		if(form.getDaysOfOperation()!=null && form.getDaysOfOperation().trim().length()>0) {
			if(form.getDaysOfOperationApplicableOn()==null || form.getDaysOfOperationApplicableOn().trim().length()==0) {
				error = new ErrorVO("reco.defaults.enterapplicableon");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}else{
				//String[] geographicLevel = form.getGeographicLevel();  //commented by A-7924 as part of ICRD-299901 
				String[] geographicLevelValues= form.getGeographicLevelValues();
				String[] glOperationFlag = form.getGlOperationFlag();
				Collection<String> segList = new ArrayList<String>(); //added by A-7924 as part of ICRD-299901 
				boolean isPresent = false;
				if(glOperationFlag!=null){
					for(int i=0; i<glOperationFlag.length - 1 ; i++){
						if(!"NOOP".equals(glOperationFlag[i]) && !"DELETE".equals(glOperationFlag[i]) ){  //modified by A-7924 as part of ICRD-299901
							//added by A-7924 as part of ICRD-299901 starts
							if(geographicLevel[i].equalsIgnoreCase(SEG_ORIGIN)||geographicLevel[i].equalsIgnoreCase(SEG_DESTINATION)){
								segList.add(geographicLevel[i]);
							}
							//added by A-7924 as part of ICRD-299901 ends
							if(form.getDaysOfOperationApplicableOn().equals(geographicLevel[i]) 
									&& geographicLevelValues[i]!=null && geographicLevelValues[i].trim().length()>0){
								isPresent = true;
								break;
							}
						}
					}
				}	
				if(!segList.isEmpty()&&!segList.contains(form.getDaysOfOperationApplicableOn())){
					error = new ErrorVO("reco.defaults.segmentmismatch");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
				else{
					if(segList.isEmpty() && (form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_DESTINATION)||form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_ORIGIN))){
						error = new ErrorVO("reco.defaults.segmentmismatch");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
				}
				if(form.getDaysOfOperationApplicableOn().equalsIgnoreCase(DESTINATION) && !isPresent){
						error = new ErrorVO("reco.defaults.enterdestination");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}else if(form.getDaysOfOperationApplicableOn().equalsIgnoreCase(ORIGIN) && !isPresent){
						error = new ErrorVO("reco.defaults.enterorigin");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}else if(form.getDaysOfOperationApplicableOn().equalsIgnoreCase(VIAPOINT) && !isPresent){
						error = new ErrorVO("reco.defaults.enterviapoint");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				//added by A-7924 as part of ICRD-299901 starts
				}else if(form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_ORIGIN) && !isPresent){  
						error = new ErrorVO("reco.defaults.entersegmentorigin");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
				}else if(form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_DESTINATION) && !isPresent){
					error = new ErrorVO("reco.defaults.entersegmentdestination");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
				//added by A-7924 as part of ICRD-299901 ends
			}
		}
		//added by A-7924 as part of ICRD-299901 starts
		else{
			String[] glOperationFlag = form.getGlOperationFlag();
			Collection<String> segList = new ArrayList<String>(); 
			if(geographicLevel !=null && glOperationFlag!=null){
				for (int i=0;i<geographicLevel.length-1;i++){
					if(!"NOOP".equals(glOperationFlag[i]) && !"DELETE".equals(glOperationFlag[i]) ){  
						if(geographicLevel[i].equalsIgnoreCase(SEG_ORIGIN)||geographicLevel[i].equalsIgnoreCase(SEG_DESTINATION)){
							segList.add(geographicLevel[i]);
						}
				}
			}
				if(!segList.isEmpty() && !segList.contains(form.getDaysOfOperationApplicableOn())){
					error = new ErrorVO("reco.defaults.segmentmismatch");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
				else{
					if(segList.isEmpty() && (form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_ORIGIN)|| form.getDaysOfOperationApplicableOn().equalsIgnoreCase(SEG_DESTINATION))){
						error = new ErrorVO("reco.defaults.segmentmismatch");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
				}
			}
		}
		// Added by A-7924 as part of ICRD-299901 ends
		log.log(Log.INFO, " checkboxxxxxx!!form!!Cool!", form.getIsCool());
		log.log(Log.INFO, "checkboxxxxxx!!form!Frozen!", form.getIsFrozen());
		log.log(Log.INFO, "checkboxxxxxx!!form!!getIsCC!", form.getIsCC());
		if(form.getRemarks() != null && form.getRemarks().length() > 500){
			error = new ErrorVO("reco.defaults.embargoRemarksLengthexceeded");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}

		String[] flags=form.getOperationalFlag();
		int count=0;
		if(flags!=null){
			for(int i = 0;i<flags.length; i++) {
				if(flags[i].equals(OPERATION_FLAG_DELETE)){
					count++;
				}
			}
			log.log(Log.INFO, " flags.length = ", flags.length);
			log.log(Log.INFO, "count = ", count);
		}

		// String[] geographicLevel = form.getGeographicLevel();  //commented by A-7924 as part of ICRD-299901
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
					if(OPERATION_FLAG_INSERT.equals(glOperationFlag[i]) || OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){
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
							if(!"NOOP".equals(glOperationFlag[j]) && !OPERATION_FLAG_DELETE.equals(glOperationFlag[j]) && !"DELETE".equals(glOperationFlag[j])){
								if(geographicLevel[i].equals(geographicLevel[j]) && geographicLevelType[i].equals(geographicLevelType[j])){
									isDuplicate=true;
									break;
								}
							}
							if(!"NOOP".equals(glOperationFlag[j]) && !OPERATION_FLAG_DELETE.equals(glOperationFlag[j]) && !"DELETE".equals(glOperationFlag[j])){
								
							if(geographicLevelType[i].equalsIgnoreCase(geographicLevelType[j]) && geoApplicableOn[i].equalsIgnoreCase(geoApplicableOn[j])){								
									if(AIRPORT.equals(geographicLevelType[i]) && AIRPORT.equals(geographicLevelType[j])){
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
		String[] parametercodes = form.getParameterCode();
		String[] applicablevalues = form.getIsIncluded();
		String[] values = form.getValues();
		String[] operationalFlags = form.getParamOperationalFlag();
		String [] applicableOn = form.getApplicableOn(); //Added by A-7924 as part of ICRD-299901 
		boolean isValidParameter =true;
		boolean isDuplicateParamValue =false;
		if(operationalFlags != null ){
			for (int i = 0; i < operationalFlags.length -1; i++) {					
				if(parametercodes[i].trim().length()>0){
				if(!"NOOP".equals(operationalFlags[i])){
					if(OPERATION_FLAG_INSERT.equals(operationalFlags[i]) || OPERATION_FLAG_UPDATE.equals(operationalFlags[i])){
						if(values[i] == null || values[i].trim().length()==0){
							error = new ErrorVO("reco.defaults.parametervalueempty");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break;
						}
						else if(FLIGHT_NO.equals(parametercodes[i])){
							if(values[i].split("~").length==2){
								String carrierCode = values[i].split("~")[0];
								String flightNum = values[i].split("~")[1];
								if(carrierCode == null || carrierCode.trim().length()==0 || flightNum == null || flightNum.trim().length()==0){
							error = new ErrorVO("reco.defaults.parametervalueempty");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break;
								}
							}
							else{
								error = new ErrorVO("reco.defaults.parametervalueempty");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break;
							}
						}
						else{							
							
							String[] valuesForParams = values[i].split(",");
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
						/* A-6843 for ICRD-161208 starts*/
						for(int j = i+1; j<operationalFlags.length;j++){
							if(!"NOOP".equals(operationalFlags[j]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[j]) && !"DELETE".equals(operationalFlags[j])){
								if(parametercodes[i].equalsIgnoreCase(parametercodes[j])){
									if(isDuplicateCheckReq(parametercodes[i],applicablevalues[i],applicablevalues[j])){ 
									//if( applicablevalues[i].equalsIgnoreCase(applicablevalues[j])){
									isDuplicateParameter=true;
									break;
								//}
									/*else{
										String[] valprev  = values[i].split(",");
										String[] valnext = values[j].split(",");
										for(String prevval : valprev){
											for(String nextval : valnext){
												if(prevval.equalsIgnoreCase(nextval)){
													isValidParameter=false;
													break;
												}												
											}
										}
									}*/
								}
								}/* A-6843 for ICRD-161208 ends*/
							}
						}
					}
				}
			}
		}
		}
       //Added by A-7924 as part of ICRD-299901 starts
		// Added by A-5290 for IASCB-78839
		boolean geoSegmentConfigFound = false;
		Collection<String> parameterCodeObjects = new ArrayList<String>(); 
		if(applicableOn != null){
			for (int j = 0; j < glOperationFlag.length; j++) {
				if(!"NOOP".equals(glOperationFlag[j]) && !"DELETE".equals(glOperationFlag[j]) && !"D".equals(glOperationFlag[j])){
						for (int i = 0; i < applicableOn.length-1; i++) {
						if(!"NOOP".equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
								if(applicableOn[i].equalsIgnoreCase(SEGMENT)){
									String[] seg_array = {SEG_ORIGIN, SEG_DESTINATION};
									List<String> list = Arrays.asList(seg_array);
									if(!(list.contains(geographicLevel[j]) && list.contains(form.getDaysOfOperationApplicableOn()))){
									String parameterCodeObj = parametercodes[i];
									if(embargoParameters != null && embargoParameters.size() > 0){
											for (OneTimeVO oneTimeVO : embargoParameters) {
												if(parametercodes[i].equals(oneTimeVO.getFieldValue())){
												parameterCodeObj = oneTimeVO.getFieldDescription();
													break;
												}
											}
										}
									// Added by A-5290 for IASCB-78839 Starts
									if(!parameterCodeObjects.contains(parameterCodeObj)){
										parameterCodeObjects.add(parameterCodeObj);
									}
								} else {
									log.log(Log.INFO, " Atleast one geographic level has Segment based configuration. ");
									geoSegmentConfigFound = true;;
								}
							}
						}
					}
				}
			}
		}
		if(!geoSegmentConfigFound && !parameterCodeObjects.isEmpty()){
			for(String parCodeObj : parameterCodeObjects){
				Object[] obj = {parCodeObj};
										error = new ErrorVO("reco.defaults.segmentconfigurationmissing",obj);
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
									}
								}
		// Added by A-5290 for IASCB-78839 Ends
		//Added by A-7924 as part of ICRD-299901 ends
		if(!isValidParameter){
			error = new ErrorVO("reco.defaults.sameparametervalues");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(isDuplicateParameter){
			error = new ErrorVO("reco.defaults.duplicateparameter");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if(isDuplicateParamValue){
			error = new ErrorVO("reco.defaults.duplicateparametervalue");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		Collection<String> oneTimeList = new ArrayList<String>();
		Map<String, Collection<OneTimeVO>> hashMap = null;
		oneTimeList.add(PARAMETERTYPE_VALUES);
		hashMap = findOneTimeValues(companyCode, oneTimeList);
		CommodityDelegate commodityDelegate = new CommodityDelegate();
		SCCDelegate sccDelegate = new SCCDelegate();
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
		AgentDelegate agentDelegate = new AgentDelegate();
		ULDDelegate uldDelegate =new ULDDelegate();

		if (hashMap != null && hashMap.size() > 0) {
			Collection<OneTimeVO> paymentTypeValues = (Collection<OneTimeVO>) hashMap
					.get(PARAMETERTYPE_VALUES);
			Collection<String> paramValues = new ArrayList<String>();
			for (OneTimeVO paymentType : paymentTypeValues) {
				paramValues.add(paymentType.getFieldDescription());
			}
			String[] valuesForParams = null;
			outer: for (int i = 0; i < values.length; i++) {
				if(PAYTYP.equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].split(",");
					for (String paymentTypeValue : valuesForParams) {
						if (!paramValues.contains(paymentTypeValue
								.toUpperCase())) {
							error = new ErrorVO(
									"reco.defaults.invalidpaymenttype");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break outer;
						}
					}
				}

				if("COM".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> commodities = new ArrayList<String>();
					Map<String, CommodityValidationVO> validcommodities = null;
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							commodities.add(valuesForParams[j].toUpperCase());						
						validcommodities = commodityDelegate.validateCommodityCodes(companyCode, commodities);
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidcommodity");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					if(validcommodities == null){
						error = new ErrorVO(
						"reco.defaults.invalidcommodity");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
				}
				if("SCC".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> sccs = new ArrayList<String>();
					Collection<SCCValidationVO> validsccs = null;
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							sccs.add(valuesForParams[j].toUpperCase());						
						validsccs = sccDelegate.validateSCCCodes(companyCode, sccs);
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidscc");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					if(validsccs == null){
						error = new ErrorVO(
						"reco.defaults.invalidscc");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
				}
				if("SCCGRP".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> sccgroups = new ArrayList<>();
					
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							sccgroups.add(valuesForParams[j].toUpperCase());						
						String groupType = "SCCGRP";
						//Changed for IASCB-144575
						Collection<GeneralMasterGroupVO> generalMasterGroupVOs=null;
						Collection<String> groupNamesSet=null;
						generalMasterGroupVOs = generalMasterGroupingDelegate
		    			.findGroupDetailsForGroupNames(companyCode
		    									,EMBARGO,groupType,sccgroups);
						if(generalMasterGroupVOs!=null){
			        		groupNamesSet=new ArrayList<>();
			        		for(GeneralMasterGroupVO generalMasterGroupVO:generalMasterGroupVOs){
			        			groupNamesSet.add(generalMasterGroupVO.getGroupName());
			        		}
			        	}
						if(groupNamesSet==null || sccgroups.size()!=groupNamesSet.size()){
							error = new ErrorVO(
									"reco.defaults.invalidsccgroup");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break outer;
				    	}	
						
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidsccgroup");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					
				}
				if(("CAR".equals(parametercodes[i]) || "FLTOWN".equals(parametercodes[i])) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> carrier = new ArrayList<String>();
					Map<String,AirlineValidationVO> airlineValidationMap=null;
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							carrier.add(valuesForParams[j].toUpperCase());						
						airlineValidationMap = airlineDelegate.validateAlphaCodes(companyCode, carrier);
					}
					catch (Exception e) {
						if("CAR".equals(parametercodes[i])){
							error = new ErrorVO(
							"reco.defaults.invalidcarrier");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break outer;
						}
						else{
							error = new ErrorVO(
							"reco.defaults.invalidflightowner");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break outer;
						}
					}
					if(airlineValidationMap==null){
						if("CAR".equals(parametercodes[i])){
							error = new ErrorVO(
							"reco.defaults.invalidcarrier");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break outer;
						}
						else{
							error = new ErrorVO(
							"reco.defaults.invalidflightowner");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
							break outer;
						}
					}
				}
				// Added by A-5867 for ICRD-84146 starts
				//added conditions as part of ICRD-328467
				if("PRD".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i]) &&
						form.getStartDate()!= null && form.getStartDate().trim().length()> 0 &&
						form.getEndDate()!= null && form.getEndDate().trim().length()> 0){
					valuesForParams = values[i].split(",");	
					for (String productName : valuesForParams) {
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
								break outer;
							}
						} catch (BusinessDelegateException e) {
							log.log(Log.SEVERE, "<--BusinessDelegateException-->");
							errors= handleDelegateException(e);
						}
					}
				}
				// Added by A-5867 for ICRD-84146 ends
				if("ARLGRP".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> airlineGroups = new ArrayList<>();
				
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							airlineGroups.add(valuesForParams[j].toUpperCase());						
						String groupType = "ARLGRP";
						//Changed for IASCB-144575
						Collection<GeneralMasterGroupVO> generalMasterGroupVOs=null;
						Collection<String> groupNamesSet=null;
						generalMasterGroupVOs = generalMasterGroupingDelegate
		    			.findGroupDetailsForGroupNames(companyCode
		    									,EMBARGO,groupType,airlineGroups);
						if(generalMasterGroupVOs!=null){
			        		groupNamesSet=new ArrayList<>();
			        		for(GeneralMasterGroupVO generalMasterGroupVO:generalMasterGroupVOs){
			        			groupNamesSet.add(generalMasterGroupVO.getGroupName());
			        		}
			        	}
						if(groupNamesSet==null || airlineGroups.size()!=groupNamesSet.size()){
							error = new ErrorVO(
									"reco.defaults.invalidairlinegroup");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break outer;
				    	}	
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidairlinegroup");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					
				}
				if("AGT".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> agent = new ArrayList<String>();
					Map<String, AgentVO> agentValidationMap=null; 
					
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							agent.add(valuesForParams[j].toUpperCase());						
						agentValidationMap = agentDelegate.validateAgents(logonAttributes.getCompanyCode(), agent);
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidagent");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					if(agentValidationMap == null){
						error = new ErrorVO(
						"reco.defaults.invalidagent");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
				}
				if("AGTGRP".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> agtgroups = new ArrayList<>();
					
					try{					
						for(int j=0;j<valuesForParams.length;j++)
							agtgroups.add(valuesForParams[j].toUpperCase());						
						String groupType = "AGTGRP";
						//Changed for IASCB-144575
						Collection<GeneralMasterGroupVO> generalMasterGroupVOs=null;
						Collection<String> groupNamesSet=null;
						generalMasterGroupVOs = generalMasterGroupingDelegate
		    			.findGroupDetailsForGroupNames(companyCode
		    									,EMBARGO,groupType,agtgroups);
						if(generalMasterGroupVOs!=null){
			        		groupNamesSet=new ArrayList<>();
			        		for(GeneralMasterGroupVO generalMasterGroupVO:generalMasterGroupVOs){
			        			groupNamesSet.add(generalMasterGroupVO.getGroupName());
			        		}
			        	}
						if(groupNamesSet==null || agtgroups.size()!=groupNamesSet.size()){
							error = new ErrorVO(
									"reco.defaults.invalidagentgroup");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
									break outer;
				    	}	
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invalidagentgroup");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
						
					}
					
				}
				
				if("ULD".equals(parametercodes[i]) && !"NOOP".equals(operationalFlags[i]) && !OPERATION_FLAG_DELETE.equals(operationalFlags[i]) && !"DELETE".equals(operationalFlags[i])){
					valuesForParams = values[i].toUpperCase().split(",");	
					Collection<String> ulds = new ArrayList<String>();
					Map<String, ULDTypeValidationVO> uldValidationMap=null; 
					
					try{					
						for(int j=0;j<valuesForParams.length;j++){
							ulds.add(valuesForParams[j].toUpperCase());						
						}
						uldValidationMap = uldDelegate.validateULDTypeOrGroup(companyCode, ulds);
					}
					catch (Exception e) {
						error = new ErrorVO(
						"reco.defaults.invaliduld");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
					if(uldValidationMap == null){
						error = new ErrorVO(
						"reco.defaults.invaliduld");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
				}
			}
		}
		/*String[] parametervalues= form.getParameterCode();
		String[] applicablevalues= form.getIsIncluded();
		String[] values = form.getValues();
		String[] opFlag = form.getOperationalFlag();
		String[] carrierCode = form.getCarrierCode();
		String[] flightNum = form.getFlightNumber();*/
//		log.log(Log.INFO, "parametervalues!@@@@@"+form.getParameterCode());




		/*	if(parametervalues==null){
				error = new ErrorVO("reco.defaults.OneRow");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
		}*/
		
		AirlineValidationVO airlineValidationVO = null;
		
		
		/*if(parametervalues!=null){
			for (int i = 0; i < parametervalues.length; i++) {
				//Modified by A-3767 on 02Mar11 for bug 108218
				if(!"D".equals(opFlag[i])){

					if(parametervalues[i].equals("")){
						log.log(Log.INFO, "parametervalues[i] ",
								parametervalues, i);
						isValid = false;
						isnullParameter =true;
						//break;
					}
					if(FLIGHT_NO.equals(parametervalues[i])) {
						if(carrierCode[i].equals("") || flightNum[i].equals("")) {
							isValid = false;
							isnullValue =true;
							//break;
						}
						if(!carrierCode[i].equals("")) {
							try {
								airlineValidationVO = airlineDelegate.validateAlphaCode(
										logonAttributes.getCompanyCode(), carrierCode[i].toUpperCase());
							} catch (BusinessDelegateException businessDelegateException) {
								businessDelegateException.getMessageVO().getErrors();
								Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
								errors.addAll(err);
							}
						}
					} else {
						if(values[i].equals("")){
							isValid = false;
							isnullValue =true;
							//break;
						}
					}

					if(isnullParameter || isnullValue ) {
						break;
					}
				}	
			}

			if(isnullParameter){
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.Parameter", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			if(isnullValue){
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.Values", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

			//Commented by A-3767 on 02Mar11 for bug 108218
			/*if(isnullParameter){
				if(form.getIsCool()==false && form.getIsFrozen()==false && form.getIsCC()==false  ){
					log.log(log.FINE,"@@@@@@@@@detailssss********* ");
					isValid = false;
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.entervalues", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error); 
				}
			}
			if(isnullValue){
				if(form.getIsCool()==false && form.getIsFrozen()==false && form.getIsCC()==false  ){
					log.log(log.FINE,"@@@@@@@@@detailssss********* ");
					isValid = false;
					Object[] obj = { "" };
					error = new ErrorVO("reco.defaults.entervalues", obj);
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
			}*/
			//to restrict theparameter values to  (db size - 200)
			/*if(! isnullParameter && ! isnullValue){
				outer : for(int i = 0 ; i < parametervalues.length ; i ++){
					log
							.log(Log.FINE, "\n\n the parameter ",
									parametervalues, i);
					if(values[i] !=  null && values[i].length() >200){
						log.log(Log.FINE, "\n\n the parameter length ",
								values[i].length());
						Object[] obj = {parametervalues[i]};
						error = new ErrorVO("reco.defaults.maximumparametervalues", obj);
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
						break outer;
					}
				}	
			}
		}*/

		/*if (errors == null || errors.size() == 0) {
			/*if (parametervalues != null) {
				if (parametervalues.length > 1) {
					for (int i = 0; i < parametervalues.length - 1; i++) {
						for (int j = i + 1; j < parametervalues.length; j++) {
							if (!(flags[i].equals("D"))) {
								if (parametervalues[i]
										.equals(parametervalues[j])) {
									isDuplicateParameter = true;
									break;
								}
							}
						}
					}
				}
			}
			if (isDuplicateParameter) {
				error = new ErrorVO("reco.defaults.DuplicateParameter");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}

			if (values != null) {
				/*for (int i = 0; i < values.length; i++) {
					String[] valuesForParams = values[i].split(",");
					if (valuesForParams.length > 0) {
						for (int j = 0; j < valuesForParams.length; j++) {
							for (int k = j + 1; k < valuesForParams.length; k++) {
								if (valuesForParams[j]
										.equals(valuesForParams[k])) {
									isDuplicateValue = true;
									break;
								}
							}
						}
					}
				}
				// Added by A-5247 for BUG-ICRD-48336 starts
				// paymentType values validation check
				
				Collection<String> oneTimeList = new ArrayList<String>();
				Map<String, Collection<OneTimeVO>> hashMap = null;
				/*oneTimeList.add(PARAMETERTYPE_VALUES);
				hashMap = findOneTimeValues(companyCode, oneTimeList);

				if (hashMap != null && hashMap.size() > 0) {
					Collection<OneTimeVO> paymentTypeValues = (Collection<OneTimeVO>) hashMap
							.get(PARAMETERTYPE_VALUES);
					Collection<String> paramValues = new ArrayList<String>();
					for (OneTimeVO paymentType : paymentTypeValues) {
						paramValues.add(paymentType.getFieldDescription());
					}
					String[] valuesForParams = null;
					outer: for (int i = 0; i < values.length; i++) {
						if(PAYTYP.equals(parametervalues[i])){
						valuesForParams = values[i].split(",");
						for (String paymentTypeValue : valuesForParams) {
							if (!paramValues.contains(paymentTypeValue
									.toUpperCase())) {
								error = new ErrorVO(
										"reco.defaults.invalidpaymenttype");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
								break outer;
							}
						}
						}


					}
				}*/
				// Added by A-5247 for BUG-ICRD-48366 Ends
			/*}
			if (isDuplicateValue) {
				error = new ErrorVO("reco.defaults.DuplicateParameterValues");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}*/

		
		ArrayList<EmbargoParameterVO> paramDetails = new ArrayList<EmbargoParameterVO>();
		/*EmbargoParameterVO vo;
		if(parametervalues!=null){
			for (int i = 0; i < parametervalues.length; i++) {
				vo = new EmbargoParameterVO();
				vo.setParameterCode(parametervalues[i]);
				if(FLIGHT_NO.equals(vo.getParameterCode())) {
					String parVal = new StringBuffer(carrierCode[i].toUpperCase()).append("~").append(flightNum[i].toUpperCase()).toString();
					vo.setParameterValues(parVal);
					vo.setCarrierCode(carrierCode[i]);
					vo.setFlightNumber(flightNum[i]);
					
				} else {
					vo.setParameterValues(values[i].toUpperCase());
				}
				vo.setIsIncluded(false);
				if(applicablevalues[i].equals("I")){
					vo.setIsIncluded(true);
				}
				paramDetails.add(vo);
			}*/
			//maintainEmbargoSession.setEmbargoParameterVos(paramDetails);
			maintainEmbargoSession.setEmbargoParameterVos(null);
		
		
		return errors;
	}
	/* A-6843 for ICRD-161208 starts*/
	/**
	 * 
	 * @param parametercodes
	 * @param originalApplicablevalue
	 * @param newApplicablevalue
	 * @return
	 */
	private boolean isDuplicateCheckReq(String parametercodes,String originalApplicablevalue,String newApplicablevalue){
		boolean isDuplicateCheckReq=true;
		if("SCC".equals(parametercodes)&&(!originalApplicablevalue.equals(newApplicablevalue))&&(!"EX".equals(originalApplicablevalue)&&!"EX".equals(newApplicablevalue))){
			isDuplicateCheckReq=false;}
		if("FLTNUM".equals(parametercodes)&&(!originalApplicablevalue.equals(newApplicablevalue))){
			isDuplicateCheckReq=false;
		}
		if("TIM".equals(parametercodes)&&(!originalApplicablevalue.equals(newApplicablevalue))){
			isDuplicateCheckReq=false;
		}
		return isDuplicateCheckReq;
	}
	/* A-6843 for ICRD-161208 ends*/
	private EmbargoRulesVO getEmbargoDetails(
			MaintainEmbargoRulesForm maintainEmbargoForm ,
			MaintainEmbargoRulesSession session, String companyCode){

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
		embargoVO.setRuleType("E");
		embargoVO.setEmbargoLevel(maintainEmbargoForm.getEmbargoLevel());
		embargoVO.setEmbargoDescription(maintainEmbargoForm.getEmbargoDesc());
		//setting value for status
		/*String statusDescription=maintainEmbargoForm.getStatus();
		Collection<OneTimeVO> embargoStatus = session.getEmbargoStatus();
		if(embargoStatus != null){
			for(OneTimeVO statusVO: embargoStatus){
				if(statusVO.getFieldDescription().equals(statusDescription)){
					embargoVO.setStatus(statusVO.getFieldValue());
				}
			}
		}*/		
		
		embargoVO.setIsSuspended(maintainEmbargoForm.getIsSuspended());
		embargoVO.setCategory(maintainEmbargoForm.getCategory());
		embargoVO.setComplianceType(maintainEmbargoForm.getComplianceType());
		embargoVO.setApplicableTransactions(maintainEmbargoForm.getApplicableTransactions());

		//Added by A-5160 for ICRD-27155
		logonAttributes =
			applicationSessionImpl.getLogonVO();
		String airportCode = logonAttributes.getAirportCode();
		embargoVO.setAirportCode(airportCode);

		LocalDate localStartDate = new LocalDate(
				LocalDate.NO_STATION,Location.NONE, false);
		if(maintainEmbargoForm.getStartDate()!=null &&
				maintainEmbargoForm.getStartDate().trim().length()>0) {
			embargoVO.setStartDate(localStartDate.setDate(
					maintainEmbargoForm.getStartDate()));
		}

		LocalDate localEndDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);

		if(maintainEmbargoForm.getEndDate() != null &&
				maintainEmbargoForm.getEndDate().trim().length()>0) {
			embargoVO.setEndDate(localEndDate.setDate(
					maintainEmbargoForm.getEndDate()));
		}

		if(EmbargoRulesVO.OPERATION_FLAG_UPDATE.equals(embargoVO.getOperationalFlag()) ||
				EmbargoRulesVO.OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag())){

			/*if(maintainEmbargoForm.getOrigin()!= null &&
					maintainEmbargoForm.getOrigin().trim().length()>0){
				embargoVO.setOrigin(
						maintainEmbargoForm.getOrigin().toUpperCase());
				embargoVO.setOriginType(maintainEmbargoForm.getOriginType());
			}		    
			else {
				embargoVO.setOriginType(null);
				embargoVO.setOrigin(null);
			}

			if(maintainEmbargoForm.getDestination() != null &&
					maintainEmbargoForm.getDestination().trim().length()>0){
				embargoVO.setDestination(
						maintainEmbargoForm.getDestination().toUpperCase());
				embargoVO.setDestinationType(maintainEmbargoForm.getDestinationType());
			}
			else { 
				embargoVO.setDestinationType(null);
				embargoVO.setDestination(null);	    	
			}
			
			if(maintainEmbargoForm.getViaPoint()!= null &&
					maintainEmbargoForm.getViaPoint().trim().length()>0){
				embargoVO.setViaPoint(
						maintainEmbargoForm.getViaPoint().toUpperCase());
				embargoVO.setViaPointType(maintainEmbargoForm.getViaPointType());
			}		    
			else {
				embargoVO.setViaPointType(null);
				embargoVO.setViaPoint(null);
			}
			*/
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
					embargoVO.setDaysOfOperationFlag(EmbargoRulesVO.OPERATION_FLAG_DELETE);
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
			geographicLevelVOs
		= (ArrayList<EmbargoGeographicLevelVO>)session.getEmbargoVo().getGeographicLevels();
		}
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
					if(OPERATION_FLAG_INSERT.equals(glOperationFlag[i])){  
						embargoGeographicLevelVO.setGeographicLevel(geographicLevel[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelType(geographicLevelType[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelApplicableOn(geographicLevelApplicableOn[i].toUpperCase());
						embargoGeographicLevelVO.setGeographicLevelValues(geographicLevelValues[i].toUpperCase());
						embargoGeographicLevelVO.setOperationFlag(glOperationFlag[i]);
						embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
						
					}
					else if(OPERATION_FLAG_UPDATE.equals(glOperationFlag[i])){ 
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
							embargoGeographicLevelVO.setOperationFlag(OPERATION_FLAG_INSERT);
							embargoGeographicLevelVOTemp.setOperationFlag(OPERATION_FLAG_DELETE);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVO);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
						}
						if(EXCLUDE.equals(geographicLevelApplicableOn[i].toUpperCase())){
							embargoGeographicLevelVOTemp = new EmbargoGeographicLevelVO();
							embargoGeographicLevelVOTemp = geographicLevelVOs.get(i);
							embargoGeographicLevelVOTemp.setOperationFlag(OPERATION_FLAG_DELETE);
							embargoGeographicLevelVOTemp.setGeographicLevelValues("-");
							embargoGeographicLevelVOTemp.setGeographicLevelApplicableOn(INCLUDE);
							embargoGeographicLevelVOs.add(embargoGeographicLevelVOTemp);
						}
					}
					else if(OPERATION_FLAG_DELETE.equals(glOperationFlag[i])){
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
		
		String[] applicableLevelParameter = maintainEmbargoForm.getApplicableOnParameter();
	
		String[] applicableLevel = maintainEmbargoForm.getApplicableOn();
		
		
		
		ArrayList<EmbargoParameterVO> parameterVOs = null;
		if(session.getEmbargoVo().getParameters()!=null)
			parameterVOs = (ArrayList<EmbargoParameterVO>)session.getEmbargoVo().getParameters();
		if(parameterVOs == null) {
			parameterVOs = new ArrayList<EmbargoParameterVO>();
			embargoVO.setParameters(parameterVOs);
		}

		if(operationalFlags != null ){
			Collection<EmbargoParameterVO> embargoParameterVOs = new ArrayList<EmbargoParameterVO>();
			int j=0;
			int k=0;
			for (int i = 0; i < operationalFlags.length -1; i++ ) {			
				if(parametercodes[i].trim().length()>0){
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
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
								if(values[i].split("~")[1]!=null)
									embargoParameterVO.setFlightNumber(values[i].split("~")[1]);
							}
							if(values[i].split("~").length==1){
								if(values[i].indexOf("~")<0)
									embargoParameterVO.setFlightNumber(values[i].split("~")[0]);
								else
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									
							}
						}
						embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);
						if("WGT".equals(embargoParameterVO.getParameterCode())){
							embargoParameterVO.setApplicableLevel(applicableLevelParameter[j]);
							j++;
						} else{
							embargoParameterVO.setApplicableLevel(applicableLevel[k]);
							k++;
						}
						
						embargoParameterVO.setParameterLevel(PARAMETER);
						embargoParameterVOs.add(embargoParameterVO);
						//Added by A-8146
						if (("COM".equals(embargoParameterVO.getParameterCode())
								|| "PRD".equals(embargoParameterVO.getParameterCode()))
								&& "EX".equals(embargoParameterVO.getApplicable())){
							EmbargoParameterVO embargoParamVO =new EmbargoParameterVO();
							embargoParamVO.setCompanyCode(companyCode);
							embargoParamVO.setParameterCode(parametercodes[i].toUpperCase());
							embargoParamVO.setParameterValues("-");
							embargoParamVO.setApplicable("IN");
							embargoParamVO.setOperationalFlag(operationalFlags[i]);
							embargoParamVO.setApplicableLevel(applicableLevel[i]);
							embargoParamVO.setParameterLevel(PARAMETER);
							embargoParameterVOs.add(embargoParamVO);
						}
						
						if (("COM".equals(embargoParameterVO.getParameterCode())
                                || "PRD".equals(embargoParameterVO.getParameterCode()))
                                && "EX".equals(embargoParameterVO.getApplicable())){
                            EmbargoParameterVO embargoParamVO =new EmbargoParameterVO();
                            embargoParamVO.setCompanyCode(companyCode);
                            embargoParamVO.setParameterCode(parametercodes[i].toUpperCase());
                            embargoParamVO.setParameterValues("-");
                            embargoParamVO.setApplicable("IN");
                            embargoParamVO.setOperationalFlag(operationalFlags[i]);
                            embargoParamVO.setApplicableLevel(applicableLevel[i]);
                            embargoParamVO.setParameterLevel(PARAMETER);
                            embargoParameterVOs.add(embargoParamVO);
                        }
						
						
					}
					else if(OPERATION_FLAG_UPDATE.equals(operationalFlags[i])){ 
						embargoParameterVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
						
							embargoParameterVOTemp = parameterVOs.get(i);
							//embargoParameterVO = parameterVOs.get(i);
						embargoParameterVO = new EmbargoParameterVO();
						embargoParameterVO.setCompanyCode(companyCode);
						embargoParameterVO.setEmbargoReferenceNumber(embargoParameterVOTemp.getEmbargoReferenceNumber());
						embargoParameterVO.setEmbargoVersion(embargoParameterVOTemp.getEmbargoVersion());
						embargoParameterVO.setParameterCode(parametercodes[i].toUpperCase());
						embargoParameterVO.setParameterValues(values[i].toUpperCase());
						if(FLIGHT_NO.equals(parametercodes[i])){							
							if(values[i].split("~").length==2){
								if(values[i].split("~")[0]!=null)
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
								if(values[i].split("~")[1]!=null)
									embargoParameterVO.setFlightNumber(values[i].split("~")[1]);
							}
							if(values[i].split("~").length==1){
								if(values[i].indexOf("~")<0)
									embargoParameterVO.setFlightNumber(values[i].split("~")[0]);
								else
									embargoParameterVO.setCarrierCode(values[i].split("~")[0]);
									
							}
						}
						
						embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());
						//Added for IASCB-117616
						embargoParameterVO.setOperationalFlag(operationalFlags[i]);						
						if("WGT".equals(embargoParameterVO.getParameterCode())){
							embargoParameterVO.setApplicableLevel(applicableLevelParameter[j]);
							j++;
						} else{
							embargoParameterVO.setApplicableLevel(applicableLevel[k]);
							k++;
						}
						embargoParameterVO.setParameterLevel(PARAMETER);
							if(parametercodes[i].equalsIgnoreCase(embargoParameterVOTemp.getParameterCode())
									&& applicablevalues[i].equalsIgnoreCase(embargoParameterVOTemp.getApplicable())
									&& PARAMETER.equals(embargoParameterVOTemp.getParameterLevel())){
						embargoParameterVOs.add(embargoParameterVO);
						}
							else{
								embargoParameterVO.setOperationalFlag(OPERATION_FLAG_INSERT);
								embargoParameterVOTemp.setOperationalFlag(OPERATION_FLAG_DELETE);
								embargoParameterVOs.add(embargoParameterVO);
								embargoParameterVOs.add(embargoParameterVOTemp);
							}
						//}
						
					}
					else if(OPERATION_FLAG_DELETE.equals(operationalFlags[i])){
						
							embargoParameterVO = parameterVOs.get(i);
							embargoParameterVO.setCompanyCode(companyCode);
							/*embargoParameterVO.setParameterCode(parametercodes[i].toUpperCase());
							embargoParameterVO.setParameterValues(values[i].toUpperCase());
							embargoParameterVO.setApplicable(applicablevalues[i].toUpperCase());*/
							embargoParameterVO.setOperationalFlag(operationalFlags[i]);
							/*embargoParameterVO.setApplicableLevel(applicableLevel[i]);
							embargoParameterVO.setParamketerLevel("P");*/
							embargoParameterVOs.add(embargoParameterVO);	
						//}
					}
					else if("DELETE".equals(operationalFlags[i])){
						embargoParameterVOTemp = new EmbargoParameterVO();
						embargoParameterVOTemp = parameterVOs.get(i);
						embargoParameterVOTemp.setOperationalFlag(OPERATION_FLAG_DELETE);
						embargoParameterVOs.add(embargoParameterVOTemp);
					}
				}
			}
			}
			if(embargoParameterVOs!=null){
				embargoVO.setParameters((ArrayList<EmbargoParameterVO>)embargoParameterVOs);
			}	

		}




		embargoVO.setRemarks(maintainEmbargoForm.getRemarks());
		//LocalDate localLastUpdatedTime = new LocalDate(LocalDate.NO_STATION,Location.NONE, true);
		embargoVO.setLastUpdatedUser(logonAttributes.getUserId());
		//embargoVO.setLastUpdatedTime(localLastUpdatedTime);
		session.setEmbargoVo(embargoVO);
		log.log(Log.INFO, "session.setEmbargoVo = ", session.getEmbargoVo());
		return embargoVO;
	}
	/**
	 * @author a-5247
	 * used to find onetime values
	 * @param companyCode
	 * @param oneTimeList
	 * @return
	 */
	private Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode,Collection<String> oneTimeList){
		
		SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
		Map<String, Collection<OneTimeVO>> hashMap = null;
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(
					companyCode, oneTimeList);
		} catch (BusinessDelegateException exception) {
			handleDelegateException(exception);
			log.log(Log.SEVERE, "onetime fetch exception");
		}
		return hashMap;		
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
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
			
		}
		return detailsVo;
	}
}
