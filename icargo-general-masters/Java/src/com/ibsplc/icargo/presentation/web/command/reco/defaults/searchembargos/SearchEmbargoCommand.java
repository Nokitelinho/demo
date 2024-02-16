/*
 * SearchEmbargoCommand.java Created on May 14, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.searchembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLeftPanelParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.searchembargos.SearchEmbargoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.SearchEmbargoForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/** * Command class for  Search Embargos 
 *
 * @author A-5867
 *
 */
public class SearchEmbargoCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("RECO.DEFAULTS");
	
	private static final String MODULE_NAME = "reco";
	private static final String SCREENID ="reco.defaults.searchembargo";
	
	private static final String ERROR_INVALID_COUNTRY = "shared.area.invalidcountry";
	private static final String ERROR_INVALID_AIRPORT ="shared.area.invalidairport";
	private static final String AIRPORT = "A";
	private static final String AIRPORT_GROUP = "ARPGRP";
	private static final String COUNTRY = "C";
	private static final String COUNTRY_GROUP = "CNTGRP";
	private static final String SIMPLE_SEARCH = "1";
	private static final String SUCCESS = "success";
	private static final String FAILURE = "failure";
	private static final String PARAMETER_CODE_SCC = "SCC";
	private static final String PARAMETER_CODE_SCC_GROUP = "SCCGRP";
	private static final String PARAMETER_CODE_TIME = "TIM";
	private static final String PARAMETER_CODE_CARRIER = "CAR";
	private static final String PARAMETER_CODE_HEIGHT = "HGT";
	private static final String PARAMETER_CODE_LENGTH = "LEN";
	private static final String PARAMETER_CODE_WIDTH = "WID";
	private static final String PARAMETER_CODE_WEIGHT = "WGT";
	private static final String PARAMETER_CODE_NAT = "GOODS";
	private static final String PARAMETER_CODE_COM = "COM";
	private static final String PARAMETER_CODE_PRD = "PRD";
	private static final String PARAMETER_CODE_AWBPFX = "AWBPRE";
	private static final String PARAMETER_CODE_UNCLS = "UNCLS";
	private static final String PARAMETER_CODE_FLTNUM = "FLTNUM";
	private static final String PARAMETER_CODE_PAYTYP = "PAYTYP";
	private static final String PARAMETER_CODE_FLTTYP = "FLTTYP";
	private static final String PARAMETER_CODE_SLTIND = "SPLIT";
	private static final String PARAMETER_CODE_FLTOWN = "FLTOWR";
	private static final String PARAMETER_CODE_UNDNUM = "UNNUM";
	private static final String PARAMETER_CODE_PKGINS = "PKGINS";
	private static final String PARAMETER_CODE_UNWGT = "UNWGT";
	private static final String PARAMETER_CODE_DVCST = "DVCST";
	private static final String PARAMETER_CODE_DVCRG = "DVCRG";
	private static final String PARAMETER_CODE_DATE="DAT";
	private static final String PARAMETER_CODE_ARLGRP="ARLGRP";
	private static final String GEOGRAPHICAL_LEVEL_ORIGIN ="O";
	private static final String GEOGRAPHICAL_LEVEL_VIA_POINT ="V";
	private static final String GEOGRAPHICAL_LEVEL_DESTINATION ="D";
	private static final String GEOGRAHICAL_LEVEL_ALL = "A";
	private static final String GEOGRAPHICAL_LEVEL_SEGMENTORIGIN ="L";
	private static final String GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION ="U";
	private static final String VALUE_ALL="-";
	private static final String PARAMETER="PRM";
	private static final String PRODUCT="PRD";
	private static final String PARAMETER_CODE_AGT = "AGT";
	private static final String PARAMETER_CODE_AGT_GROUP = "AGTGRP";
	//added by A-5799 for IASCB-23507 starts
	public static final String SERVICE_CARGO_CLASS = "SRVCRGCLS";
	/*public static final String AIRCRAFT_CLASS_ORIGIN="ACRCLSORG";
	public static final String AIRCRAFT_CLASS_VIA_POINT="ACRCLSVIA";
	public static final String AIRCRAFT_CLASS_DESTINATION ="ACRCLSDST";
	public static final String AIRCRAFT_CLASS_ALL= "ACRCLSALL";*/
	public static final String AIRCRAFT_CLASSIFICATION = "ACRCLS";
	public static final String SHIPPER = "SHP";
	public static final String SHIPPER_GROUP = "SHPGRP";
	public static final String CONSIGNEE = "CNS";
	public static final String CONSIGNEE_GROUP = "CNSGRP";
	public static final String SHIPMENT_TYPE = "SHPTYP";
	public static final String CONSOL = "CNSL";
	 //added by A-5799 for IASCB-23507 ends
	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering("SearchEmbargoCommand","Execute");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		EmbargoSearchVO embargoSearchVO=null;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		SearchEmbargoForm searchEmbargoForm = (SearchEmbargoForm)invocationContext.screenModel;
		SearchEmbargoSession searchEmbargoSession =getScreenSession(MODULE_NAME,SCREENID);
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
		embargoFilterVO.setLeftPanelParameters(searchEmbargoSession.getLeftPanelParameters());
		embargoFilterVO.setCategories(searchEmbargoSession.getCategories());
		embargoFilterVO.setComplianceTypes(searchEmbargoSession.getComplianceTypes());
		embargoFilterVO.setParameterCodes(searchEmbargoSession.getParameterCodes());
		embargoFilterVO.setFlightTypes(searchEmbargoSession.getFlightTypes());
		embargoFilterVO = getFilter(searchEmbargoForm,logonAttributes,embargoFilterVO,errors);
		//Added by A-5984 for ICRD-215196
		EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
		Page<EmbargoDetailsVO> embargoDetails=null;
		HashMap<String, String> indexMap = getIndexMap(searchEmbargoSession.getIndexMap(), invocationContext);
		if (searchEmbargoSession.getIndexMap() != null) {
			indexMap = searchEmbargoSession.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.INFO, "INDEX MAP IS NULL");
			indexMap = new HashMap<String, String>();
			indexMap.put("1", "1");
		}
				
		if(!SIMPLE_SEARCH .equals(searchEmbargoForm.getSimpleSearch())){
		errors=validateForm(searchEmbargoForm,embargoFilterVO,logonAttributes, errors);
		}
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			embargoSearchVO= new EmbargoSearchVO();
			searchEmbargoSession.setEmbargoSearchVO(embargoSearchVO);	
			invocationContext.target = FAILURE;
			return;
		}else{	
			searchEmbargoSession.setFilterVO(embargoFilterVO);					
			embargoSearchVO = searchEmbargos(embargoFilterVO);
			if(embargoSearchVO != null && embargoSearchVO.getEmbargoLeftPanelParameterVOs()!= null && embargoSearchVO.getEmbargoLeftPanelParameterVOs().size() > 0 ){
				for(EmbargoLeftPanelParameterVO embargoLeftPanelParameterVO : embargoSearchVO.getEmbargoLeftPanelParameterVOs()){
					List<EmbargoParameterVO> paramaters = embargoLeftPanelParameterVO.getParameters();
					if(PARAMETER.equals(embargoLeftPanelParameterVO.getFieldValue())){
						if(paramaters!=null && paramaters.size()>0){
							for(EmbargoParameterVO parameter : paramaters){
								List<String> subModules = parameter.getSubModules();
								List<String> productwithDescription = new ArrayList<String>();
								ProductVO productVO = new ProductVO();
								if(PRODUCT.equals(parameter.getParameterCode()) && subModules!=null && subModules.size()>0){
									Map<String, ProductVO> productNames = new HashMap<String, ProductVO>();
									try {
										productNames=delegate.validateProductNames(logonAttributes.getCompanyCode(), subModules);
									} catch (BusinessDelegateException e) {
										handleDelegateException(e);
									}
									for(String str: subModules){
										log.log(Log.FINE, str);
										productVO= productNames.get(str);
										if(productVO!=null){
										str= str+"~"+productVO.getDescription();
										}else{
											str= str+"~"+str;
										}
										productwithDescription.add(str);
										
									}
									if(productwithDescription != null && productwithDescription.size() >0){
										parameter.setSubModules(null);
										parameter.setSubModules(productwithDescription);
									}
							    }
						}
					}
					}
				}
			}
			searchEmbargoSession.setEmbargoSearchVO(embargoSearchVO);
			if(null !=embargoSearchVO && (null==embargoSearchVO.getEmbargoDetails()
					|| embargoSearchVO.getEmbargoDetails().isEmpty())){
				searchEmbargoSession.setRegulatoryComplianceRules(null);
				ErrorVO error = new ErrorVO("embargo.nulllist");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(error);
				invocationContext.target = FAILURE;
				return;
			}
			
			String toDisplayPage = searchEmbargoForm.getDisplayPage();
			int displayPage = Integer.parseInt(toDisplayPage);
			embargoFilterVO.setPageNumber(displayPage);
			if(SearchEmbargoForm.PAGINATION_MODE_FROM_FILTER.equals(searchEmbargoForm.getNavigationMode())) {
				log.log(Log.FINE, "NavigationMode>>>>>>>",searchEmbargoForm.getNavigationMode());
				log.log(Log.FINE, "Page Number>>>>>>>>>", displayPage);
				embargoFilterVO.setTotalRecordCount(-1);	
			}else if(SearchEmbargoForm.PAGINATION_MODE_FROM_NAVIGATION.equals(searchEmbargoForm.getNavigationMode())) {
				log.log(Log.FINE, "NavigationMode>>>>>>>",searchEmbargoForm.getNavigationMode());
				log.log(Log.FINE, "Page Number>>>>>>>>>", displayPage);
				embargoFilterVO.setTotalRecordCount(searchEmbargoSession.getTotalRecords());			
			}else {
				embargoFilterVO.setTotalRecordCount(-1);
			}
			embargoDetails =findRegulatoryComplianceRules(embargoFilterVO, displayPage);
			if(null !=embargoDetails && embargoDetails.size()>0){
				searchEmbargoSession.setRegulatoryComplianceRules(embargoDetails);
				searchEmbargoSession.setTotalRecords(embargoDetails.getTotalRecordCount());
			}else{
				searchEmbargoSession.setRegulatoryComplianceRules(null);
			}
		}
		invocationContext.target =SUCCESS;
		log.exiting("ScreenloadOneTimeMasterCommand","Execute");
	}
	
	private Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filter, int displayPage){
		Page<EmbargoDetailsVO> embargoDetails = null;
		EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
		try{
			embargoDetails = delegate.findRegulatoryComplianceRules(filter, displayPage);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
		}
		return embargoDetails;
	}

	private EmbargoSearchVO searchEmbargos(EmbargoFilterVO filter){
		EmbargoSearchVO embargoSearchVO = null;
		EmbargoRulesDelegate delegate = new EmbargoRulesDelegate();
		try{
			embargoSearchVO = delegate.searchEmbargos(filter);
		}catch(BusinessDelegateException e){
			handleDelegateException(e);
		}
		return embargoSearchVO;
	}
	
	private EmbargoFilterVO getFilter(SearchEmbargoForm form,
			LogonAttributes logonAttributes,EmbargoFilterVO filterVO,Collection<ErrorVO> errors){
		Collection<ErrorVO> errorsList=null;
		AreaDelegate areaDelegate=new AreaDelegate();
		GeneralMasterGroupingDelegate generalMasterGroupingDelegate = new GeneralMasterGroupingDelegate();
		LocalDate date = null;	
		boolean isValid =  false;
		Collection<String> airports = null;
		Collection<String> airportgroups = null;
		Collection<String> countrys = null;
		Collection<String> countrygroups = null;
		Map<String,AirportValidationVO> airportValidationVOMap = null;
		String parameterValue=null;
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(null !=form.getSimpleSearch() && form.getSimpleSearch().equals(SIMPLE_SEARCH)){
			/*** Simple search starts **/
			filterVO.setSimpleSearch(true);
			date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			filterVO.setStartDate(date);			
			if(isValidSimpleSearch(form)){
				form.setGeographicLevel(form.getGeographicLevel().toUpperCase());
				filterVO.setGeographicLevelType(form.getGeographicLevelType());
				filterVO.setGeographicLevel(form.getGeographicLevel());
				if(form.getGeographicLevelType().equalsIgnoreCase(AIRPORT)){
					if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
						filterVO.setAirportCodeOrigin(form.getGeographicLevel());
						filterVO.setAirportCodeDestination(form.getGeographicLevel());
						filterVO.setAirportCodeViaPoint(form.getGeographicLevel());
						filterVO.setOriginType(form.getGeographicLevelType());
						filterVO.setOrigin(form.getGeographicLevel());
						filterVO.setDestinationType(form.getGeographicLevelType());
						filterVO.setDestination(form.getGeographicLevel());
						filterVO.setViaPointType(form.getGeographicLevelType());
						filterVO.setViaPoint(form.getGeographicLevel());
						//Added by A-7924 as part of ICRD-318460 LH bug starts
						filterVO.setSegmentOriginType(form.getGeographicLevelType());
						filterVO.setSegmentOrigin(form.getGeographicLevel());
						filterVO.setSegmentDestinationType(form.getGeographicLevelType());
						filterVO.setSegmentDestination(form.getGeographicLevel());
						//Added by A-7924 as part of ICRD-318460 LH bug ends
						// Added by A-5290 for ICRD-352717
						filterVO.setAirportCodeSegmentOrigin(form.getGeographicLevel());
						filterVO.setAirportCodeSegmentDestination(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
						filterVO.setAirportCodeOrigin(form.getGeographicLevel());
						filterVO.setOriginType(form.getGeographicLevelType());
						filterVO.setOrigin(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
						filterVO.setAirportCodeDestination(form.getGeographicLevel());
						filterVO.setDestinationType(form.getGeographicLevelType());
						filterVO.setDestination(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
						filterVO.setAirportCodeViaPoint(form.getGeographicLevel());
						filterVO.setViaPointType(form.getGeographicLevelType());
						filterVO.setViaPoint(form.getGeographicLevel());
					}//added by A-7924 as part of ICRD-318460 LH bug starts
					else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN)){ 
						filterVO.setAirportCodeSegmentOrigin(form.getGeographicLevel());
						filterVO.setSegmentOriginType(form.getGeographicLevelType());
						filterVO.setSegmentOrigin(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION)){
						filterVO.setAirportCodeSegmentDestination(form.getGeographicLevel());
						filterVO.setSegmentDestinationType(form.getGeographicLevelType());
						filterVO.setSegmentDestination(form.getGeographicLevel());
					}
					//added by A-7924 as part of ICRD-318460 LH bug starts
					airports = new ArrayList<String>();
					airports.add(form.getGeographicLevel());
					try{
						airportValidationVOMap=areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(),airports);
					}catch(BusinessDelegateException businessDelegateException) {
						errorsList = handleDelegateException(businessDelegateException);
						if(errorsList != null && errorsList.size()>0){
							for(ErrorVO err : errorsList){
								if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
									ErrorVO error = new ErrorVO("reco.defaults.invalidairport");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
								}
							}
						}
					}
					if(null !=airportValidationVOMap && airportValidationVOMap.size()>0 && 
							null !=airportValidationVOMap.get(form.getGeographicLevel())){
						if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
							filterVO.setCountryCodeOrigin(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
							filterVO.setCountryCodeDestination(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
							filterVO.setCountryCodeViaPoint(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
							filterVO.setCountryCodeOrigin(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
							filterVO.setCountryCodeDestination(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
							filterVO.setCountryCodeViaPoint(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}
						//added by A-7924 as part of ICRD-318460 LH bug starts
						else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN)){
							filterVO.setCountryCodeSegmentOrigin(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION)){
							filterVO.setCountryCodeSegmentDestination(airportValidationVOMap.get(form.getGeographicLevel()).getCountryCode());
						}
						//added by A-7924 as part of ICRD-318460 LH bug ends
					}
				}else if(form.getGeographicLevelType().equalsIgnoreCase(AIRPORT_GROUP)){
					if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
						filterVO.setAirportGroupOrigin(form.getGeographicLevel());
						filterVO.setAirportGroupDestination(form.getGeographicLevel());
						filterVO.setAirportGroupViaPoint(form.getGeographicLevel());
						filterVO.setOriginType(form.getGeographicLevelType());
						filterVO.setOrigin(form.getGeographicLevel());
						filterVO.setDestinationType(form.getGeographicLevelType());
						filterVO.setDestination(form.getGeographicLevel());
						filterVO.setViaPointType(form.getGeographicLevelType());
						filterVO.setViaPoint(form.getGeographicLevel());
						
						//Added by A-5799 for IASCB-31950
						filterVO.setAirportGroupSegmentOrigin(form.getGeographicLevel());    
						filterVO.setAirportGroupSegmentDestination(form.getGeographicLevel());
						filterVO.setSegmentOriginType(form.getGeographicLevelType());
						filterVO.setSegmentOrigin(form.getGeographicLevel());
						filterVO.setSegmentDestinationType(form.getGeographicLevelType());
						filterVO.setSegmentDestination(form.getGeographicLevel());
						
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
						filterVO.setAirportGroupOrigin(form.getGeographicLevel());
						filterVO.setOriginType(form.getGeographicLevelType());
						filterVO.setOrigin(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
						filterVO.setAirportGroupDestination(form.getGeographicLevel());
						filterVO.setDestinationType(form.getGeographicLevelType());
						filterVO.setDestination(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
						filterVO.setAirportGroupViaPoint(form.getGeographicLevel());
						filterVO.setViaPointType(form.getGeographicLevelType());
						filterVO.setViaPoint(form.getGeographicLevel());
					}//added by A-7924 as part of ICRD-318460 LH bug starts
					else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN)){
						filterVO.setAirportGroupSegmentOrigin(form.getGeographicLevel());
						filterVO.setSegmentOriginType(form.getGeographicLevelType());
						filterVO.setSegmentOrigin(form.getGeographicLevel());
					}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION)){
						filterVO.setAirportGroupSegmentDestination(form.getGeographicLevel());
						filterVO.setSegmentDestinationType(form.getGeographicLevelType());
						filterVO.setSegmentDestination(form.getGeographicLevel());
					}
					//added by A-7924 as part of ICRD-318460 LH bug ends
					airportgroups = new ArrayList<String>();
					airportgroups.add(form.getGeographicLevel());
					try{
						String groupType = "ARPGRP";
						isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), airportgroups, groupType);
						if(!isValid){
							ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);
						}
					}catch(BusinessDelegateException businessDelegateException) {
						errorsList = handleDelegateException(businessDelegateException);
						if(errorsList != null && errorsList.size()>0){									
							ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
							error.setErrorDisplayType(ErrorDisplayType.ERROR);
							errors.add(error);				
						}
					}
				}else if(form.getGeographicLevelType().equalsIgnoreCase(COUNTRY)){
						if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
							filterVO.setCountryCodeOrigin(form.getGeographicLevel());
							filterVO.setCountryCodeDestination(form.getGeographicLevel());
							filterVO.setCountryCodeViaPoint(form.getGeographicLevel());
							filterVO.setOriginType(form.getGeographicLevelType());
							filterVO.setOrigin(form.getGeographicLevel());
							filterVO.setDestinationType(form.getGeographicLevelType());
							filterVO.setDestination(form.getGeographicLevel());
							filterVO.setViaPointType(form.getGeographicLevelType());
							filterVO.setViaPoint(form.getGeographicLevel());
							//added by A-7924 as part of ICRD-318460 LH bug starts
							filterVO.setSegmentOriginType(form.getGeographicLevelType());
							filterVO.setSegmentOrigin(form.getGeographicLevel());
							filterVO.setSegmentDestinationType(form.getGeographicLevelType());
							filterVO.setSegmentDestination(form.getGeographicLevel());
							//added by A-7924 as part of ICRD-318460 LH bug ends
							// Added by A-5290 for ICRD-352717
							filterVO.setCountryCodeSegmentOrigin(form.getGeographicLevel());
							filterVO.setCountryCodeSegmentDestination(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
							filterVO.setCountryCodeOrigin(form.getGeographicLevel());
							filterVO.setOriginType(form.getGeographicLevelType());
							filterVO.setOrigin(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
							filterVO.setCountryCodeDestination(form.getGeographicLevel());
							filterVO.setDestinationType(form.getGeographicLevelType());
							filterVO.setDestination(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
							filterVO.setCountryCodeViaPoint(form.getGeographicLevel());
							filterVO.setViaPointType(form.getGeographicLevelType());
							filterVO.setViaPoint(form.getGeographicLevel());
						}
						//added by A-7924 as part of ICRD-318460 LH bug starts
						else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN)){
							filterVO.setCountryCodeSegmentOrigin(form.getGeographicLevel());
							filterVO.setSegmentOriginType(form.getGeographicLevelType());
							filterVO.setSegmentOrigin(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION)){
							filterVO.setCountryCodeSegmentDestination(form.getGeographicLevel());
							filterVO.setSegmentDestinationType(form.getGeographicLevelType());
							filterVO.setSegmentDestination(form.getGeographicLevel());
						}
						//added by A-7924 as part of ICRD-318460 LH bug ends
						countrys = new ArrayList<String>();
						countrys.add(form.getGeographicLevel());
						try{
							areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrys);
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){
								for(ErrorVO err : errorsList){
									if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
										ErrorVO error = new ErrorVO("reco.defaults.invalidcountry");
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
									}
								}
							}
						}
				}else if(form.getGeographicLevelType().equalsIgnoreCase(COUNTRY_GROUP)){
						if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
							filterVO.setCountryGroupOrigin(form.getGeographicLevel());
							filterVO.setCountryGroupDestination(form.getGeographicLevel());
							filterVO.setCountryGroupViaPoint(form.getGeographicLevel());
							filterVO.setOriginType(form.getGeographicLevelType());
							filterVO.setOrigin(form.getGeographicLevel());
							filterVO.setDestinationType(form.getGeographicLevelType());
							filterVO.setDestination(form.getGeographicLevel());
							filterVO.setViaPointType(form.getGeographicLevelType());
							filterVO.setViaPoint(form.getGeographicLevel());
							//added by A-7924 as part of ICRD-318460 LH bug starts
							filterVO.setSegmentOriginType(form.getGeographicLevelType());
							filterVO.setSegmentOrigin(form.getGeographicLevel());
							filterVO.setSegmentDestinationType(form.getGeographicLevelType());
							filterVO.setSegmentDestination(form.getGeographicLevel());
							//added by A-7924 as part of LH  bug ends
							// Added by A-5290 for ICRD-352717
							filterVO.setCountryGroupSegmentOrigin(form.getGeographicLevel());
							filterVO.setCountryGroupSegmentDestination(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
							filterVO.setCountryGroupOrigin(form.getGeographicLevel());
							filterVO.setOriginType(form.getGeographicLevelType());
							filterVO.setOrigin(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
							filterVO.setCountryGroupDestination(form.getGeographicLevel());
							filterVO.setDestinationType(form.getGeographicLevelType());
							filterVO.setDestination(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
							filterVO.setCountryGroupViaPoint(form.getGeographicLevel());
							filterVO.setViaPointType(form.getGeographicLevelType());
							filterVO.setViaPoint(form.getGeographicLevel());
						}//added by A-7924 as part of ICRD-318460 LH bug starts
						else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTORIGIN)){
							filterVO.setCountryGroupSegmentOrigin(form.getGeographicLevel());
							filterVO.setSegmentOriginType(form.getGeographicLevelType());
							filterVO.setSegmentOrigin(form.getGeographicLevel());
						}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_SEGMENTDESTINATION)){
							filterVO.setCountryGroupSegmentDestination(form.getGeographicLevel());
							filterVO.setSegmentDestinationType(form.getGeographicLevelType());
							filterVO.setSegmentDestination(form.getGeographicLevel());
						}
						//added by A-7924 as part of ICRD-318460 LH bug ends
						countrygroups = new ArrayList<String>();
						countrygroups.add(form.getGeographicLevel());
						try{
							String groupType = "CNTGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), countrygroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}							
				}
			}else if(null ==form.getGeographicLevel() || form.getGeographicLevel().length()<1){
				ErrorVO error = new ErrorVO("reco.defaults.geographiclevelvalueempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			filterVO.setDaysOfOperationApplicableOn(form.getDayOfOperationApplicableOn());
			form.setOriginType(null);
			form.setOrigin(null);
			form.setDestinationType(null);
			form.setDestination(null);
			form.setViaPointType(null);
			form.setViaPoint(null);
			if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_ORIGIN)){
				form.setOriginType(form.getGeographicLevelType());
				form.setOrigin(form.getGeographicLevel());
			}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_DESTINATION)){
				form.setDestinationType(form.getGeographicLevelType());
				form.setDestination(form.getGeographicLevel());
			}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAPHICAL_LEVEL_VIA_POINT)){
				form.setViaPointType(form.getGeographicLevelType());
				form.setViaPoint(form.getGeographicLevel());
			}else if(form.getDayOfOperationApplicableOn().equalsIgnoreCase(GEOGRAHICAL_LEVEL_ALL)){
				form.setOriginType(form.getGeographicLevelType());
				form.setOrigin(form.getGeographicLevel());
				form.setDestinationType(form.getGeographicLevelType());
				form.setDestination(form.getGeographicLevel());
				form.setViaPointType(form.getGeographicLevelType());
				form.setViaPoint(form.getGeographicLevel());
			}			
			/*** Simple search ends **/
		}else{
			/*** Detailed search starts **/
			if(null !=form.getOriginType() && form.getOriginType().length()>0 && 
					null !=form.getOrigin() && form.getOrigin().length()>0){
				form.setOrigin(form.getOrigin().toUpperCase());
				filterVO.setOriginType(form.getOriginType());
				filterVO.setOrigin(form.getOrigin());
				
				if(form.getOriginType().equalsIgnoreCase(AIRPORT)){
					filterVO.setAirportCodeOrigin(form.getOrigin());
					airports = new ArrayList<String>();
					airports.add(filterVO.getAirportCodeOrigin());
					try{
						airportValidationVOMap=areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(),airports);
					}catch(BusinessDelegateException businessDelegateException) {
						errorsList = handleDelegateException(businessDelegateException);
						if(errorsList != null && errorsList.size()>0){
							for(ErrorVO err : errorsList){
								if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
									ErrorVO error = new ErrorVO("reco.defaults.invalidairport");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
								}
							}
						}
					}
					if(null !=airportValidationVOMap && airportValidationVOMap.size()>0 && null !=airportValidationVOMap.get(filterVO.getAirportCodeOrigin())){
						filterVO.setCountryCodeOrigin(airportValidationVOMap.get(filterVO.getAirportCodeOrigin()).getCountryCode());
					}
				}else if(form.getOriginType().equalsIgnoreCase(AIRPORT_GROUP)){
						filterVO.setAirportGroupOrigin(form.getOrigin());
						airportgroups = new ArrayList<String>();
						airportgroups.add(filterVO.getAirportGroupOrigin());
						try{
							String groupType = "ARPGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), airportgroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}
				}else if(form.getOriginType().equalsIgnoreCase(COUNTRY)){
						filterVO.setCountryCodeOrigin(form.getOrigin());
						countrys = new ArrayList<String>();
						countrys.add(filterVO.getCountryCodeOrigin());
						try{
							areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrys);
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){
								for(ErrorVO err : errorsList){
									if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
										ErrorVO error = new ErrorVO("reco.defaults.invalidcountry");
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
									}
								}
							}
						}
				}else if(form.getOriginType().equalsIgnoreCase(COUNTRY_GROUP)){
						filterVO.setCountryGroupOrigin(form.getOrigin());
						countrygroups = new ArrayList<String>();
						countrygroups.add(filterVO.getCountryGroupOrigin());
						try{
							String groupType = "CNTGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), countrygroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}							
				}
			}
			
			if(null !=form.getDestinationType() && form.getDestinationType().length()>0 
					&& null !=form.getDestination() && form.getDestination().length()>0){
				form.setDestination(form.getDestination().toUpperCase());
				filterVO.setDestinationType(form.getDestinationType());
				filterVO.setDestination(form.getDestination());
				
				if(form.getDestinationType().equalsIgnoreCase(AIRPORT)){
					filterVO.setAirportCodeDestination(form.getDestination());
					airports = new ArrayList<String>();
					airports.add(filterVO.getAirportCodeDestination());
					try{
						airportValidationVOMap=areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(),airports);
					}catch(BusinessDelegateException businessDelegateException) {
						errorsList = handleDelegateException(businessDelegateException);
						if(errorsList != null && errorsList.size()>0){
							for(ErrorVO err : errorsList){
								if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
									ErrorVO error = new ErrorVO("reco.defaults.invalidairport");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
								}
							}
						}
					}
					if(null !=airportValidationVOMap && airportValidationVOMap.size()>0 && null !=airportValidationVOMap.get(filterVO.getAirportCodeDestination())){
						filterVO.setCountryCodeDestination(airportValidationVOMap.get(filterVO.getAirportCodeDestination()).getCountryCode());
					}
				}else if(form.getDestinationType().equalsIgnoreCase(AIRPORT_GROUP)){
						filterVO.setAirportGroupDestination(form.getDestination());
						airportgroups = new ArrayList<String>();
						airportgroups.add(filterVO.getAirportGroupDestination());
						try{
							String groupType = "ARPGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), airportgroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}
				}else if(form.getDestinationType().equalsIgnoreCase(COUNTRY)){
						filterVO.setCountryCodeDestination(form.getDestination());
						countrys = new ArrayList<String>();
						countrys.add(filterVO.getCountryCodeDestination());
						try{
							areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrys);
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){
								for(ErrorVO err : errorsList){
									if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
										ErrorVO error = new ErrorVO("reco.defaults.invalidcountry");
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
									}
								}
							}
						}
				}else if(form.getDestinationType().equalsIgnoreCase(COUNTRY_GROUP)){
						filterVO.setCountryGroupDestination(form.getDestination());
						countrygroups = new ArrayList<String>();
						countrygroups.add(filterVO.getCountryGroupDestination());
						try{
							String groupType = "CNTGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), countrygroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}							
				}
			}
			
			if(null !=form.getViaPointType() && form.getViaPointType().length()>0 &&
					null !=form.getViaPoint() && form.getViaPoint().length()>0){
				form.setViaPoint(form.getViaPoint().toUpperCase());
				filterVO.setViaPointType(form.getViaPointType());
				filterVO.setViaPoint(form.getViaPoint());
				
				if(form.getViaPointType().equalsIgnoreCase(AIRPORT)){
					filterVO.setAirportCodeViaPoint(form.getViaPoint());
					airports = new ArrayList<String>();
					airports.add(filterVO.getAirportCodeViaPoint());
					try{
						airportValidationVOMap=areaDelegate.validateAirportCodes(logonAttributes.getCompanyCode(),airports);
					}catch(BusinessDelegateException businessDelegateException) {
						errorsList = handleDelegateException(businessDelegateException);
						if(errorsList != null && errorsList.size()>0){
							for(ErrorVO err : errorsList){
								if(ERROR_INVALID_AIRPORT.equals(err.getErrorCode())){
									ErrorVO error = new ErrorVO("reco.defaults.invalidairport");
									error.setErrorDisplayType(ErrorDisplayType.ERROR);
									errors.add(error);
								}
							}
						}
					}
					if(null !=airportValidationVOMap && airportValidationVOMap.size()>0 && null !=airportValidationVOMap.get(filterVO.getAirportCodeViaPoint())){
						filterVO.setCountryCodeViaPoint(airportValidationVOMap.get(filterVO.getAirportCodeViaPoint()).getCountryCode());
					}
				}else if(form.getViaPointType().equalsIgnoreCase(AIRPORT_GROUP)){
						filterVO.setAirportGroupViaPoint(form.getViaPoint());
						airportgroups = new ArrayList<String>();
						airportgroups.add(filterVO.getAirportGroupViaPoint());
						try{
							String groupType = "ARPGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), airportgroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidairportgroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}
				}else if(form.getViaPointType().equalsIgnoreCase(COUNTRY)){
						filterVO.setCountryCodeViaPoint(form.getViaPoint());
						countrys = new ArrayList<String>();
						countrys.add(filterVO.getCountryCodeViaPoint());
						try{
							areaDelegate.validateCountryCodes(logonAttributes.getCompanyCode(), countrys);
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){
								for(ErrorVO err : errorsList){
									if(ERROR_INVALID_COUNTRY.equals(err.getErrorCode())){
										ErrorVO error = new ErrorVO("reco.defaults.invalidcountry");
										error.setErrorDisplayType(ErrorDisplayType.ERROR);
										errors.add(error);
									}
								}
							}
						}
				}else if(form.getViaPointType().equalsIgnoreCase(COUNTRY_GROUP)){
						filterVO.setCountryGroupViaPoint(form.getViaPoint());
						countrygroups = new ArrayList<String>();
						countrygroups.add(filterVO.getCountryGroupViaPoint());
						try{
							String groupType = "CNTGRP";
							isValid = generalMasterGroupingDelegate.validateGroupNames(logonAttributes.getCompanyCode(), countrygroups, groupType);
							if(!isValid){
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);
							}
						}catch(BusinessDelegateException businessDelegateException) {
							errorsList = handleDelegateException(businessDelegateException);
							if(errorsList != null && errorsList.size()>0){									
								ErrorVO error = new ErrorVO("reco.defaults.invalidcountrygroup");
								error.setErrorDisplayType(ErrorDisplayType.ERROR);
								errors.add(error);				
							}
						}							
				}
			} if( ((null ==form.getOriginType() || form.getOriginType().length()== 0) && 
					(null ==form.getOrigin() || form.getOrigin().length()==0)) && 
					((null ==form.getDestinationType() || form.getDestinationType().length()==0 )
							&& (null ==form.getDestination() || form.getDestination().length()==0 ))&& 
							((null ==form.getViaPointType() || form.getViaPointType().length()==0 )||
									(null ==form.getViaPoint() || form.getViaPoint().length()==0))){
				/*Added as part of ICRD-288644
				 * Either Origin , Destination or Via Point is mandatory in specific search in order to avoid performance problems.
				 */
				ErrorVO error = new ErrorVO("reco.defaults.originOrDestOrViaMandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
			
			
			if(null !=form.getApplicableTransaction() && form.getApplicableTransaction().length()>0){
				filterVO.setApplicableTransactions(form.getApplicableTransaction());
			}
			if(null !=form.getEmbargoDate() && form.getEmbargoDate().length()>0){
				date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);	
				date.setDate(form.getEmbargoDate());
				filterVO.setEmbargoDate(date);
			}
			if(null !=form.getFromDate() && form.getFromDate().length()>0 && 
					null !=form.getToDate() && form.getToDate().length()>0){
				LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);	
				fromDate.setDate(form.getFromDate());
				LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);	
				toDate.setDate(form.getToDate());
				if(toDate.isLesserThan(fromDate)){
					ErrorVO error = new ErrorVO("reco.defaults.fromdatecompare");
					errors.add(error);
				}
			}
			if(null !=form.getFromDate() && form.getFromDate().length()>0){
				date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
				date.setDate(form.getFromDate());
				filterVO.setStartDate(date);
				LocalDate dateToCompare = new LocalDate(logonAttributes.getStationCode(), Location.STN, false);
				if(date.isLesserThan(dateToCompare))
					{
					filterVO.setStartDate(dateToCompare);
					}
			}else{
				date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
				filterVO.setStartDate(date);
			}
			if(null !=form.getToDate() && form.getToDate().length()>0){
				date = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);	
				date.setDate(form.getToDate());
				filterVO.setEndDate(date);
			}
			if(null !=form.getRuleType() && form.getRuleType().length()>0){
				filterVO.setRuleType(form.getRuleType());
			}
			if(null !=form.getLevelCode() && form.getLevelCode().length()>0){
				filterVO.setEmbargoLevel(form.getLevelCode());
			}
			if(null !=form.getParameterCode() && form.getParameterCode().length()>0 && 
					null !=form.getParameterValue() && form.getParameterValue().length()>0){
				parameterValue=form.getParameterValue().toUpperCase();
				filterVO.setParameterCode(form.getParameterCode());
				filterVO.setParameterValues(form.getParameterValue());
				if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SCC)){
					filterVO.setScc(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SCC_GROUP)){
					filterVO.setSccGroup(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_TIME)){
					filterVO.setTime(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_CARRIER)){
					filterVO.setAirlineCode(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_HEIGHT)){
					filterVO.setHeight(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_WIDTH)){
					filterVO.setWidth(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_LENGTH)){
					filterVO.setLength(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_WEIGHT)){
					filterVO.setWeight(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_NAT)){
					filterVO.setNatureOfGoods(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_COM)){
					filterVO.setCommodity(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PRD)){
					filterVO.setProduct(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_AWBPFX)){
					filterVO.setAwbPrefix(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTNUM)){
					filterVO.setFlightNumber(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PAYTYP)){
					filterVO.setPaymentType(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTTYP)){
					filterVO.setFlightType(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_SLTIND)){
					filterVO.setSplitIndicator(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_FLTOWN)){
					filterVO.setFlightOwner(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_UNDNUM)){
					filterVO.setUnNumber(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_DATE)){
					filterVO.setGeographicalDate(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_ARLGRP)){
					filterVO.setAirlineGroup(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_PKGINS)){
					filterVO.setPackingInstruction(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_UNCLS)){
					filterVO.setUnIds(parameterValue);
				//}
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_UNWGT)){
					filterVO.setUnWeight(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_DVCST)){
					filterVO.setDvForCustoms(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_DVCRG)){
					filterVO.setDvForCarriage(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_AGT)){
					filterVO.setAgentCode(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(PARAMETER_CODE_AGT_GROUP)){
					filterVO.setAgentGroup(parameterValue);
					
				}else if(form.getParameterCode().equalsIgnoreCase(SERVICE_CARGO_CLASS)){
					filterVO.setServiceCargoClass(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(AIRCRAFT_CLASSIFICATION)){
					filterVO.setAircraftClassification(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(SHIPPER)){
					filterVO.setShipperCode(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(SHIPPER_GROUP)){
					filterVO.setShipperGroup(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(CONSIGNEE)){
					filterVO.setConsigneeCode(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(CONSIGNEE_GROUP)){
					filterVO.setConsigneeGroup(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(SHIPMENT_TYPE)){
					filterVO.setShipmentType(parameterValue);
				}else if(form.getParameterCode().equalsIgnoreCase(CONSOL)){
					filterVO.setConsol(parameterValue);
				}
				
			}
			}
			
			if(null !=form.getCategory() && form.getCategory().length()>0){
				filterVO.setCategory(form.getCategory());
			}
			boolean isDatePresent = true;
			boolean isGeographicLevelPresent = true;
			boolean isParameterPresent = true;
			if((null==form.getEmbargoDate() || form.getEmbargoDate().trim().length()==0) 
				&& (null == form.getFromDate() || form.getFromDate().trim().length() ==0)
				&& (null == form.getToDate() || form.getToDate().trim().length() ==0)){
				isDatePresent = false;
				
			}
			if(!isDatePresent && (null == form.getOrigin() || form.getOrigin().trim().length()==0 || null == form.getOriginType() || form.getOriginType().trim().length()==0)
					&& (null == form.getDestination() || form.getDestination().trim().length()==0 || null == form.getDestinationType() || form.getDestinationType().trim().length()==0)
					&& (null == form.getViaPoint() || form.getViaPoint().trim().length()==0 || null == form.getViaPointType() || form.getViaPointType().trim().length()==0)){
				isGeographicLevelPresent = false;
				
			}
			if(!isDatePresent && !isGeographicLevelPresent && (null == form.getParameterCode() || form.getParameterCode().trim().length()==0
					|| null == form.getParameterValue() || form.getParameterValue().trim().length() ==0)){
				isParameterPresent = false;
						
				}
			if(!isDatePresent &&  !isGeographicLevelPresent && !isParameterPresent){
				ErrorVO error = new ErrorVO("reco.defaults.enteranysection");
				errors.add(error);
			}
			/*** Detailed search ends **/
			return filterVO;
		}

	


	private boolean isValidSimpleSearch(SearchEmbargoForm form) {
		//Below code has been added as part of ICRD-288644 . If the applicable on is not selected , then setting the the value as "ALL" , so that performance issue can be avoided.
		if(null ==form.getDayOfOperationApplicableOn() || form.getDayOfOperationApplicableOn().length()==0){
			form.setDayOfOperationApplicableOn(GEOGRAHICAL_LEVEL_ALL);
		}
		return null !=form.getGeographicLevelType() && form.getGeographicLevelType().length()>0 &&
				null !=form.getGeographicLevel() && form.getGeographicLevel().length()>0 &&
				null !=form.getDayOfOperationApplicableOn() && form.getDayOfOperationApplicableOn().length()>0;
	}
    
    private Collection<ErrorVO> validateForm(SearchEmbargoForm form,EmbargoFilterVO embargoFilterVO,
    		LogonAttributes logonAttributes,Collection<ErrorVO> errors) {
		ErrorVO error = null;	
		LocalDate currentDate=null;
		LocalDate date=null;
		if((null !=form.getOriginType()&& form.getOriginType().trim().length()>0)
				&& (null ==form.getOrigin() || form.getOrigin().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterorigin");
			errors.add(error);
		}
		if((null !=form.getOrigin()&& form.getOrigin().trim().length()>0)
				&& (null ==form.getOriginType() || form.getOriginType().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterorigintype");
			errors.add(error);
		}
		if((null !=form.getDestinationType()&& form.getDestinationType().trim().length()>0)
				&& (null ==form.getDestination() || form.getDestination().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterdestination");
			errors.add(error);
		}
		if((null !=form.getDestination()&& form.getDestination().trim().length()>0)
				&& (null ==form.getDestinationType() || form.getDestinationType().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterdestinationtype");
			errors.add(error);
		}
		if((null !=form.getViaPointType()&& form.getViaPointType().trim().length()>0)
				&& (null ==form.getViaPoint() || form.getViaPoint().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterviapoint");
			errors.add(error);
		}
		if((null !=form.getViaPoint()&& form.getViaPoint().trim().length()>0)
				&& (null ==form.getViaPointType() || form.getViaPointType().trim().length()<1)){
			error = new ErrorVO("reco.defaults.enterviapointtype");
			errors.add(error);
		}
		if((null !=form.getParameterCode()&& form.getParameterCode().trim().length()>0)
				&& (null ==form.getParameterValue() || form.getParameterValue().trim().length()<1)){
			error = new ErrorVO("reco.defaults.parametervalueempty");
			errors.add(error);
		}
		if((null !=form.getDefaultText()&& form.getDefaultText().trim().length()>0)
				&& (null ==form.getParameterCode() || form.getParameterCode().trim().length()<1)){
			error = new ErrorVO("reco.defaults.parametercodeempty");
			errors.add(error);
		}
		
		if(null !=form.getFromDate()&& form.getFromDate().trim().length()>0){
			currentDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			date= new LocalDate(logonAttributes.getStationCode(), Location.STN, false);
			date.setDate(form.getFromDate());
			if(date.isLesserThan(currentDate)){
				error = new ErrorVO("reco.defaults.notvalidstartdate");
				errors.add(error);
			}
		}
		if(null !=form.getToDate()&& form.getToDate().trim().length()>0){
			currentDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			date= new LocalDate(logonAttributes.getStationCode(), Location.STN, false);
			date.setDate(form.getToDate());
			if(date.isLesserThan(currentDate)){
				error = new ErrorVO("reco.defaults.notvalidenddate");
				errors.add(error);
			}
		}
		if(null !=form.getEmbargoDate()&& form.getEmbargoDate().trim().length()>0){
			currentDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
			date= new LocalDate(logonAttributes.getStationCode(), Location.STN, false);
			date.setDate(form.getEmbargoDate());
			if(date.isLesserThan(currentDate)){
				error = new ErrorVO("reco.defaults.notvalidembargodate");
				errors.add(error);
			}
		}
		return errors;
	}

}
