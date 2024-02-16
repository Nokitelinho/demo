/*
 * ListEmbargosCommand.java Created on Sep 27, 2005
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;



/**
 * command class for ListEmbargosCommand
 * @author A-1747
 */
public class ListEmbargosCommand extends BaseCommand{
	

	private Log log = LogFactory.getLogger("ListEmbargosCommand");
	private static final String ACTIVE = "A";
	private static final String ALL = "ALL";
	private static final String SUSPENDED = "S";
	private static final boolean TRUE = true;
	
	
	private static final String MAIL_CLASS = "MALCLS";
	private static final String MAIL_CATEGORY = "MALCAT";
	private static final String MAIL_SUBCLASS_GROUP = "MALSUBCLSGRP";
	private static final String LEVEL_CODE = "reco.defaults.levelcodes";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	private static final String VIA_POINT = "shared.embargo.viapoint";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	private static final String RULE_TYPE = "reco.defaults.ruletype";
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
	//Added for MAIL Embargo
	private static final String MAL_CLASS= "mailtracking.defaults.mailclass";
	private static final String MAL_CATEGORY= "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP= "mailtracking.defaults.mailsubclassgroup";
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass"; //added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	private static final String SERVICE_TYPE = "message.ssim.servicetype";
	private static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "message.ssim.serviceTypeForTechnicalStop";
	private static final String SRVCTYP = "SRVCTYP";
	private static final String SRVCTYPFRTECSTP = "SRVCTYPFRTECSTP";
	private static final String UNID_PACKGING_GROUP = "shared.dgr.unid.packaginggroup";
	private static final String UNID_SUB_RISK = "unidsubrisk";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */

	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		ListEmbargoRulesForm listForm
				= (ListEmbargoRulesForm)invocationContext.screenModel;
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		ListEmbargoRulesSession session = getScreenSession(
				"reco.defaults", "reco.defaults.listembargo");
		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO() ;
		EmbargoFilterVO filterVO = new EmbargoFilterVO();
		/**
		 * 
		 */
		populateOnetimeVOs(session);
	    Collection<OneTimeVO> levelCodes = session.getLevelCode();
		Collection<OneTimeVO> statusCodes = session.getStatus();
		Collection<OneTimeVO> ruleType = session.getRuleType();
		Collection<OneTimeVO> category = session.getCategoryTypes();
		Collection<OneTimeVO> applicableTransactions = session.getApplicableTransactions();
		Collection<OneTimeVO> complianceType = session.getComplianceTypes();
		Collection<OneTimeVO> embargoParameters = session.getEmbargoParameters();
		Collection<OneTimeVO> geographicLevelType = session.getGeographicLevelType();
		Collection<OneTimeVO> flightType = session.getFlightTypes();
		Collection<OneTimeVO>  mailClass =  session.getMailClass();
		Collection<OneTimeVO>  mailCategory =  session.getMailCategory();
		Collection<OneTimeVO>  mailSubClsGrp =  session.getMailSubClassGrp();
		Collection<OneTimeVO>  serviceCargoClass =  session.getServiceCargoClass();
		Collection<OneTimeVO>  shipmentType =  session.getShipmentType();
		Collection<OneTimeVO>  serviceType =  session.getServiceType();
		Collection<OneTimeVO>  unPg = session.getUnPg();
		Collection<OneTimeVO>  subRisk = session.getSubRisk();
		Collection<EmbargoGlobalParameterVO> globalCodes = session.getGlobalParameters();
		session.setEmabrgoDetailVOs(null);
		/*if(listForm.getIsButtonClicked().equals("Y")){
			listForm.setDisplayPage("1");
			listForm.setLastPageNum("0");
		}*/
		listForm.setIsButtonClicked("N");
		//HashMap<String,String> indexMap = null;
		//Modified by A-5237 for ICRD-20902 starts
		HashMap<String, String> indexMap = getIndexMap(session.getIndexMap(), invocationContext);
		//Modified by A-5237 for ICRD-20902 ends
		HashMap<String,String> finalMap = null;
		if (session.getIndexMap()!=null){
			indexMap = session.getIndexMap();
		}
		if (indexMap == null) {
			log.log(Log.FINE,"INDEX MAP IS NULL");
			indexMap = new HashMap<String,String>();
			indexMap.put("1", "1");
		}
		//int nAbsoluteIndex = 0;
		String toDisplayPage = listForm.getDisplayPage();
		int displayPage = Integer.parseInt(toDisplayPage);
		//String strAbsoluteIndex = (String)indexMap.get(toDisplayPage);
		/*if(strAbsoluteIndex != null){
			nAbsoluteIndex = Integer.parseInt(strAbsoluteIndex);
		}*/
		
		Collection<ErrorVO> errors = null;
		if(listForm.getNavigationMode()!=null){
		errors = validateForm(listForm);
		}
		boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
		if(hasBusinessPrivilege)
			{
			listForm.setIsPrivilegedUser("Y");
			}
		else
			{
			listForm.setIsPrivilegedUser("N");	
			}	
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = "failure";
			return;
		}
		else{
			if(("true").equals(listForm.getIsDisplayDetails())){
				embargoFilterVO = session.getFilterVO();
				if(embargoFilterVO == null){
					embargoFilterVO = new EmbargoFilterVO();
					embargoFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
				}
				populateForm(listForm,embargoFilterVO);

				
				listForm.setIsDisplayDetails("");
			}
			else{
				
				Collection<ErrorVO> err = null;
				try {
					embargoFilterVO = getFilter(listForm,logonAttributes, filterVO);
				} catch (BusinessDelegateException businessDelegateException) {				
					err = handleDelegateException(businessDelegateException);
				}

				if (err != null && err.size() > 0) {
					invocationContext.addAllError(err);
					invocationContext.target = "failure";
					return;
				}
			}
			session.setFilterVO(embargoFilterVO);
			listForm.setFilterSummaryDetails(getFilters(session.getFilterVO(), session));
			log.log(Log.FINE, "\n\n embargoFilterVO------------------------",
					embargoFilterVO);
			log.log(Log.FINE, "\n\n displayPage------------------------",
					displayPage);
			//embargoFilterVO.setAbsoluteIndex(nAbsoluteIndex);
			//added by a-5175 for icrd-21634 starts
			embargoFilterVO.setPageNumber(displayPage);
			if(ListEmbargoRulesForm.PAGINATION_MODE_FROM_FILTER.equals(listForm.getNavigationMode()) || listForm.getNavigationMode()==null ) {
				log.log(Log.FINE,"PAGINATION FROM FILTER");
				embargoFilterVO.setTotalRecordCount(-1);
				
			}else if(ListEmbargoRulesForm.PAGINATION_MODE_FROM_NAVIGATION.equals(listForm.getNavigationMode())) {
				log.log(Log.FINE,"PAGINATION FROM NAVIGATION");
				embargoFilterVO.setTotalRecordCount(session.getTotalRecords());
				embargoFilterVO.setPageNumber(Integer.parseInt(listForm.getDisplayPage()));
			}
			
			//added by a-5175 for icrd-21634 ends
			if(listForm.getUnIds()!=null){
				embargoFilterVO.setUnIds(listForm.getUnIds());
			}
			Page<EmbargoDetailsVO> pg = findEmbargoVos(embargoFilterVO, displayPage);
			if (errors != null && errors.size() > 0) {
				
				invocationContext.addAllError(errors);
				invocationContext.target = "failure";
				return;
				
			}
			
			
			if(pg !=null && pg.size()>0){
				
				//added by a-5175 for icrd-21634 starts
				log.log(Log.FINE,"<---------------Error wHile save----------");
				log.log(Log.FINE, "<---------------TOTAL RECORDS----------", pg.getTotalRecordCount());
				session.setTotalRecords(pg.getTotalRecordCount());
				//added by a-5175 for icrd-21634 ends
				Page<EmbargoDetailsVO> pgDetails = getDetails(pg,session);
				session.setEmabrgoDetailVOs(pgDetails);
			}
			else{
				Collection<ErrorVO> errorsCol = new ArrayList<ErrorVO>();
				ErrorVO errorVO = null;
				Object[] obj = { "" };
				errorVO = new ErrorVO("embargo.nulllist", obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errorsCol.add(errorVO);
				session.removeEmabrgoDetailVOs();
				invocationContext.addAllError(errorsCol);
				invocationContext.target = "failure";
			}
			finalMap = indexMap;
			if(session.getEmabrgoDetailVOs() != null) {
				finalMap = buildIndexMap(indexMap, session.getEmabrgoDetailVOs());
			}
			//session.setIndexMap(finalMap);
			//added by A-5237 for ICRD-20902 starts
			session.setIndexMap((HashMap<String,String>)super.setIndexMap(finalMap, invocationContext));
			//added by A-5237 for ICRD-20902 ends
			session.setGlobalParameters(globalCodes);
			session.setLevelCode(levelCodes);
			session.setRuleType(ruleType);
			session.setCategoryTypes(category);
			session.setApplicableTransactions(applicableTransactions);
			session.setComplianceTypes(complianceType);
			session.setStatus(statusCodes);
			session.setEmbargoParameters(embargoParameters);
			session.setGeographicLevelType(geographicLevelType);
			session.setFlightTypes(flightType);
			session.setMailCategory(mailCategory);
			session.setMailClass(mailClass);
			session.setMailSubClassGrp(mailSubClsGrp);
			session.setServiceCargoClass(serviceCargoClass);
			session.setShipmentType(shipmentType);
			session.setServiceType(serviceType);
			session.setUnPg(unPg);
			session.setSubRisk(subRisk);
			invocationContext.target = "success";
		}
	}
	private String getFilters(EmbargoFilterVO filterVO,ListEmbargoRulesSession session) {
		StringBuilder filters=new StringBuilder();
		StringBuilder frequency=new StringBuilder();// TODO Auto-generated method stub
		if(filterVO.getOrigin() != null && !"".equals(filterVO.getOrigin())){
			if(!"".equals(filters.toString())){
				filters.append(",");
    		}
			filters.append("Origin :").append(filterVO.getOrigin());
		}
		if(filterVO.getDestination() != null && !"".equals(filterVO.getDestination())){
			if(!"".equals(filters.toString())){
				filters.append(",");
    		}
			filters.append("Destination :").append(filterVO.getDestination());
		}
		if(filterVO.getViaPoint() != null && !"".equals(filterVO.getViaPoint())){
			if(!"".equals(filters.toString())){
				filters.append(",");
    		}
			filters.append("ViaPoint :").append(filterVO.getViaPoint());
		}
		
		//Added by A-7924 as part of ICRD-313966 starts
		if(filterVO.getSegmentOrigin()!=null&&!filterVO.getSegmentOrigin().equals("")){
			if(filters.toString()!=""){
				filters.append(",");
    		}
			filters.append("SegmentOrigin :").append(filterVO.getSegmentOrigin());
		}
		if(filterVO.getSegmentDestination()!=null&&!filterVO.getSegmentDestination().equals("")){
			if(filters.toString()!=""){
				filters.append(",");
    		}
			filters.append("SegmentDestination :").append(filterVO.getSegmentDestination());
		}
		//Added by A-7924 as part of ICRD-313966 ends
		if(filterVO.getEmbargoRefNumber()!=null&&!filterVO.getEmbargoRefNumber().equals("")){
			if(filters.toString()!=""){
				filters.append(",");
    		}
			filters.append("RefNumber :").append(filterVO.getEmbargoRefNumber());
		}
		if(filterVO.getParameterValues() != null && !"".equals(filterVO.getParameterValues())){
			if(!"".equals(filters.toString())){
				filters.append(",");
    		}
			filters.append("ParameterValues :").append(filterVO.getParameterValues());
		}
		if(filterVO.getStartDate() != null && !"".equals(filterVO.getStartDate())){
			if(!"".equals(filters.toString())){
				filters.append(",");
    		}
			filters.append("From Date :").append(filterVO.getStartDate().toDisplayDateOnlyFormat());
		}
    	if(filterVO.getEndDate() != null && !"".equals(filterVO.getEndDate())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("To Date :").append(filterVO.getEndDate().toDisplayDateOnlyFormat());
    	}
    	if(filterVO.getOriginType() != null && !"".equals(filterVO.getOriginType())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		//MOdified As part of ICRD-264494 start
    		filters.append("Origin Type:").append(getDescription(filterVO.getOriginType(),session.getGeographicLevelType()));
    		//Collection<OneTimeVO> originOneTimeVOs = session.getGeographicLevelType();
    		//for(OneTimeVO origin:originOneTimeVOs){
    		//	origin.getFieldDescription();
    	    //}
    		}
    		//filters=filters+"Origin Type :"+getDescription(filterVO.getOriginType(),);	
    	if(filterVO.getViaPointType() != null && !"".equals(filterVO.getViaPointType())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("Via Point:").append(getDescription(filterVO.getViaPointType(),session.getGeographicLevelType()));
    		/*filters.append("Via Point:").append(filterVO.getViaPointType());
    		Collection<OneTimeVO> ViaPointOneTimeVOs = session.getGeographicLevelType();
    		for(OneTimeVO ViaPoint:ViaPointOneTimeVOs){
    			ViaPoint.getFieldDescription();
    		}*/	
    		}
    		//filters=filters+"Via Point Type :"+getDescription(filterVO.getViaPointType().);	
    	if(filterVO.getDestinationType() != null && !"".equals(filterVO.getDestinationType())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("Destination Type:").append(getDescription(filterVO.getDestinationType(),session.getGeographicLevelType()));
    		/*filters.append("Destination Type:").append(filterVO.getDestinationType());
    			Collection<OneTimeVO> DestinationOneTimeVOs = session.getGeographicLevelType();
        		for(OneTimeVO Destination:DestinationOneTimeVOs){
        			Destination.getFieldDescription();
    		}*/
    	}
    		//filters=filters+"Destination Type :"+getDescription(filterVO.getDestinationType(),);	
    	//Added by A-7924 as part of ICRD-313966 starts
    	if(filterVO.getSegmentOriginType()!=null&&!filterVO.getSegmentOriginType().equals("")){
    		if(filters.toString()!=""){
				filters.append(",");
    		}
    		//MOdified As part of ICRD-264494 start
    		filters.append("Segment Origin Type:").append(getDescription(filterVO.getSegmentOriginType(),session.getGeographicLevelType()));
    		//Collection<OneTimeVO> originOneTimeVOs = session.getGeographicLevelType();
    		//for(OneTimeVO origin:originOneTimeVOs){
    		//	origin.getFieldDescription();
    	    //}
    		}
    		//filters=filters+"Origin Type :"+getDescription(filterVO.getOriginType(),);
    	if(filterVO.getSegmentDestinationType()!=null&&!filterVO.getSegmentDestinationType().equals("")){
    		if(filters.toString()!=""){
				filters.append(",");
    		}
    		filters.append("Segment Destination Type:").append(getDescription(filterVO.getSegmentDestinationType(),session.getGeographicLevelType()));
    		/*filters.append("Destination Type:").append(filterVO.getDestinationType());
    			Collection<OneTimeVO> DestinationOneTimeVOs = session.getGeographicLevelType();
        		for(OneTimeVO Destination:DestinationOneTimeVOs){
        			Destination.getFieldDescription();
    		}*/
    	}
    		//filters=filters+"Destination Type :"+getDescription(filterVO.getDestinationType(),);
    	//Added by A-7924 as part of ICRD-313966 ends
    	if(filterVO.getStatus()!=null&&!filterVO.getStatus().equals("")){
    		if(filters.toString()!=""){
				filters.append(",");
    		}
    		filters.append("Status:").append(getDescription(filterVO.getStatus(),session.getStatus()));
    		/*Collection<OneTimeVO> StatusOneTimeVOs = session.getStatus();
    		for(OneTimeVO Status:StatusOneTimeVOs){
    			Status.getFieldDescription();
    		}*/
    	}
    		//filters=filters+"Status :"+getDescription(filterVO.getStatus(),);	
    	if(filterVO.getParameterCode() != null && !"".equals(filterVO.getParameterCode())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("Parameter Code:").append(getDescription(filterVO.getParameterCode(),session.getEmbargoParameters()));
    		/*Collection<OneTimeVO> embargoparametersOneTimeVOs = session.getEmbargoParameters();
    		for(OneTimeVO embargoparameters:embargoparametersOneTimeVOs){
    			embargoparameters.getFieldDescription();
    		}*/
    	//	filters=filters+"Parameter Code :"+getDescription(filterVO.getParameterCode(),);	
    	}
    	if(filterVO.getApplicableTransactions() != null && !"".equals(filterVO.getApplicableTransactions())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("Applicable Transactions:").append(getDescription(filterVO.getApplicableTransactions(),session.getApplicableTransactions()));
    			/*Collection<OneTimeVO> applicabletransactionsOneTimeVOs = session.getApplicableTransactions();
        		for(OneTimeVO applicabletransactions:applicabletransactionsOneTimeVOs){
        			applicabletransactions.getFieldDescription();
        		}*/	
    		//filters=filters+"Applicable Transactions :"+getDescription(filterVO.getApplicableTransactions(),);	
    		}
    	if(filterVO.getEmbargoLevel() != null && !"".equals(filterVO.getEmbargoLevel())){
    		if(!"".equals(filters.toString())){
				filters.append(",");
    		}
    		filters.append("Level:").append(getDescription(filterVO.getEmbargoLevel(),session.getLevelCode()));
    		/*Collection<OneTimeVO> levelcodesOneTimeVOs = session.getLevelCode();
    		for(OneTimeVO levelcodes:levelcodesOneTimeVOs){
    			levelcodes.getFieldDescription();
    		//filters=filters+"Level :"+getDescription(filterVO.getEmbargoLevel(),);	
    	}*/
    	}
		if(filterVO.getDaysOfOperation() != null && !"".equals(filterVO.getDaysOfOperation())){
    		int num=1,freq=Integer.parseInt(filterVO.getDaysOfOperation());
    		String days[]={"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"}; 
    		if(!"".equals(filters.toString())){
    			filters.append(",").append("Days Of Operation:");
    		}
    		while(num!=0){
    			num=freq/(int)(Math.pow(10, (String.valueOf(freq).length()-1)));
    			freq=freq%(int)(Math.pow(10, (String.valueOf(freq).length()-1)));
    		if(num!=0){
    			frequency.append(days[num-1]).append(",");
    			}else{
					frequency.append(days[freq]);
				}				
    		}
			filters.append(frequency.toString());
    	}
    	return filters.toString();
    	}
	/**
	 * 
	 * @param session
	 */
	private void populateOnetimeVOs(ListEmbargoRulesSession session) {
		  LogonAttributes logonAttributes
			= getApplicationSession().getLogonVO();
		  String companyCode = logonAttributes.getCompanyCode();
		Collection<String> oneTimeList = new ArrayList<String>();
		oneTimeList.add(LEVEL_CODE);
		oneTimeList.add(EMBARGOSTATUS);
		oneTimeList.add(GEOGRAPHIC_LEVEL_TYPES);
		oneTimeList.add(VIA_POINT);
		oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
		oneTimeList.add(CATEGORY_TYPES);
		oneTimeList.add(COMPLIANCE_TYPES);
		oneTimeList.add(APPLICABLE_TRANSACTIONS);
		oneTimeList.add(EMBARGO_PARAMETERS);
		oneTimeList.add(RULE_TYPE);
		oneTimeList.add(FLIGHT_TYPE);
		oneTimeList.add(MAL_CATEGORY);
		oneTimeList.add(MAL_CLASS);
		oneTimeList.add(MAL_SUBCLS_GRP);
		oneTimeList.add(SERVICE_CARGO_CLASS);
		oneTimeList.add(SHIPMENT_TYPE);
		oneTimeList.add(SERVICE_TYPE);
		oneTimeList.add(SERVICE_TYPE_FOR_TECHNICAL_STOP);
		oneTimeList.add(UNID_PACKGING_GROUP);
		oneTimeList.add(UNID_SUB_RISK);
		Map hashMap = null;
		Collection<EmbargoGlobalParameterVO> globalParams = new ArrayList<EmbargoGlobalParameterVO>();
		  try {
				hashMap = new SharedDefaultsDelegate().findOneTimeValues(companyCode,oneTimeList);
				//globalParams = embargoDelegate.findGlobalParameterCodes(companyCode);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			}
			Collection<OneTimeVO> levelCodes
					= (Collection<OneTimeVO>)hashMap.get(LEVEL_CODE);
			Collection<OneTimeVO> statusCodes
					= (Collection<OneTimeVO>)hashMap.get(EMBARGOSTATUS);
			Collection<OneTimeVO> geographicLevelType
			= (Collection<OneTimeVO>)hashMap.get(GEOGRAPHIC_LEVEL_TYPES);
			Collection<OneTimeVO>  dayOfOperationApplicableOn = (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
			Collection<OneTimeVO>  viaPointTypes = (Collection<OneTimeVO>)hashMap.get(VIA_POINT);
			Collection<OneTimeVO>  categoryTypes = (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES);
			Collection<OneTimeVO>  complianceTypes = (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPES);
			Collection<OneTimeVO>  applicableTransactions = (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTIONS);
			Collection<OneTimeVO>  embargoParameters = (Collection<OneTimeVO>)hashMap.get(EMBARGO_PARAMETERS);
			Collection<OneTimeVO>  ruleType = (Collection<OneTimeVO>)hashMap.get(RULE_TYPE);
			Collection<OneTimeVO>  flightType = (Collection<OneTimeVO>)hashMap.get(FLIGHT_TYPE);
			Collection<OneTimeVO>  mailClass =  (Collection<OneTimeVO>)hashMap.get(MAL_CLASS);
			Collection<OneTimeVO>  mailCategory =  (Collection<OneTimeVO>)hashMap.get(MAL_CATEGORY);
			Collection<OneTimeVO>  mailSubClsGrp =  (Collection<OneTimeVO>)hashMap.get(MAL_SUBCLS_GRP);
			Collection<OneTimeVO>  serviceCargoClass =  (Collection<OneTimeVO>)hashMap.get(SERVICE_CARGO_CLASS);
			Collection<OneTimeVO>  shipmentType =  (Collection<OneTimeVO>)hashMap.get(SHIPMENT_TYPE);
			Collection<OneTimeVO>  serviceType =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE);
			Collection<OneTimeVO>  serviceTypeForTechnicalStop =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE_FOR_TECHNICAL_STOP);
			Collection<OneTimeVO>  unPg = (Collection<OneTimeVO>)hashMap.get(UNID_PACKGING_GROUP);
			Collection<OneTimeVO>  subRisk = (Collection<OneTimeVO>)hashMap.get(UNID_SUB_RISK);
			
			session.setRuleType(ruleType);
			session.setCategoryTypes(categoryTypes);
			session.setComplianceTypes(complianceTypes);
			session.setApplicableTransactions(applicableTransactions);
			session.setEmbargoParameters(embargoParameters);
			session.setLevelCode(levelCodes);
			session.setGlobalParameters(globalParams);
			session.setStatus(statusCodes);
			session.setGeographicLevelType(geographicLevelType);
			session.removeEmabrgoDetailVOs();
			//session.removeFilterVO();
			session.setViaPointTypes(viaPointTypes);
			session.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
			session.setFlightTypes(flightType);
			session.setMailCategory(mailCategory);
			session.setMailClass(mailClass);
			session.setMailSubClassGrp(mailSubClsGrp);
			session.setServiceCargoClass(serviceCargoClass);
			session.setShipmentType(shipmentType);
			session.setServiceType(serviceType);
			session.setUnPg(unPg);
			session.setServiceTypeForTechnicalStop(serviceTypeForTechnicalStop);
			session.setSubRisk(subRisk);
	}
	/**Method getFilter returns the EmbargoFilterVO
	 * @param listForm
	 * @param session
	 * @param logonAttributes
	 * @param filterVO
	 * @return EmbargoFilterVO
	 * @throws BusinessDelegateException 
	 */
	private EmbargoFilterVO getFilter(ListEmbargoRulesForm listForm,
			LogonAttributes logonAttributes, EmbargoFilterVO filterVO) throws BusinessDelegateException {

		filterVO = new EmbargoFilterVO();
		filterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if(listForm.getRefNumber()!= null && 
				listForm.getRefNumber().trim().length()>0){
			String refnum = listForm.getRefNumber().toUpperCase();
			int zeroAppendCount = 5-refnum.length();
			while(zeroAppendCount-- > 0){
				refnum = "0"+refnum;
				
			}
			listForm.setRefNumber(refnum);
			filterVO.setEmbargoRefNumber(refnum);
		}else{
			filterVO.setEmbargoRefNumber(null);
		}
		if(listForm.getStartDate() != null && 
				listForm.getStartDate().trim().length()>0){
			LocalDate localStartDate
					= new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			filterVO.setStartDate(localStartDate.setDate(listForm.getStartDate()));
		}else{
			filterVO.setStartDate(null);
		}
		if(listForm.getEndDate() != null &&
				listForm.getEndDate().trim().length()>0){
			LocalDate localEndDate
				= new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			filterVO.setEndDate(localEndDate.setDate(listForm.getEndDate()));
		}else{
			filterVO.setEndDate(null);
		}
		if(listForm.getOriginType()!= null && 
				listForm.getOriginType().trim().length()>0){
			String origin = listForm.getOrigin().toUpperCase();
			filterVO.setOrigin(origin);
			filterVO.setOriginType(listForm.getOriginType());
		}else {
			filterVO.setOrigin(null);
			filterVO.setOriginType(null);
		}
		if(listForm.getDestinationType()!= null && 
				listForm.getDestinationType().trim().length()>0){
			String destination = listForm.getDestination().toUpperCase();
			filterVO.setDestination(destination);
			filterVO.setDestinationType(listForm.getDestinationType());
		}else{
			filterVO.setDestination(null);
			filterVO.setDestinationType(null);
		}
		//Added by A-7924 as part of starts
		if(listForm.getSegmentOriginType()!= null && 
				listForm.getSegmentOriginType().trim().length()>0){
			String segmentOrigin = listForm.getSegmentOrigin().toUpperCase();
			filterVO.setSegmentOrigin(segmentOrigin);
			filterVO.setSegmentOriginType(listForm.getSegmentOriginType());
		}else {
			filterVO.setSegmentOrigin(null);
			filterVO.setSegmentOriginType(null);
		}
		if(listForm.getSegmentDestinationType()!= null && 
				listForm.getSegmentDestinationType().trim().length()>0){
			String segmentDestination = listForm.getSegmentDestination().toUpperCase();
			filterVO.setSegmentDestination(segmentDestination);
			filterVO.setSegmentDestinationType(listForm.getSegmentDestinationType());
		}else{
			filterVO.setSegmentDestination(null);
			filterVO.setSegmentDestinationType(null);
		}
		//Added by A-7924 as part of ends
		if(listForm.getStatus() != null && 
				listForm.getStatus().trim().length()>0){
			
			// the below code is commented to list the embargo purely based on selected status.
			// Added by A-5290 for ICRD-203884
			/*if(listForm.getStatus().equals(SUSPENDED)){
				filterVO.setSuspendFlag("Y");
			} else*/
			
			 if(!listForm.getStatus().equals(ALL)) {
				filterVO.setStatus(listForm.getStatus());
			} else {
				filterVO.setStatus("");
			}
		}
		if(listForm.getParameterCode()!= null && 
				listForm.getParameterCode().trim().length()>0){
			if(!listForm.getParameterCode().equals(ALL)){
				filterVO.setParameterCode(listForm.getParameterCode());
			}
			else{
				filterVO.setParameterCode("");
			}
		}
		if(listForm.getParameterValue()!= null && listForm.getParameterValue().trim().length()>0 ){
			String value =  listForm.getParameterValue().trim().toUpperCase();
			filterVO.setParameterValues(value);
		}
		
		if("FLTNUM".equals(listForm.getParameterCode())) {
			if(listForm.getCarrierCode()!=null && listForm.getCarrierCode().trim().length()>0) {
				AirlineValidationVO airlineValidationVO = validateAlphaCode(listForm.getCarrierCode().toUpperCase());
				filterVO.setParameterValues(listForm.getCarrierCode().toUpperCase());
				
			}
			if(listForm.getFlightNumber()!=null && listForm.getFlightNumber().trim().length()>0) {
				StringBuffer fltDetails = null;
				if(filterVO.getParameterValues()!=null && filterVO.getParameterValues().trim().length()>0) {
					fltDetails = new StringBuffer(filterVO.getParameterValues()).append("~")
					.append(listForm.getFlightNumber());
				
				} else {
					fltDetails = new StringBuffer("~")
					.append(listForm.getFlightNumber());

				}
				
				filterVO.setParameterValues(fltDetails.toString());
			}
			
		}
		//Added by A-8130 for ICRD-232462 starts
		if("UNWGT".equals(listForm.getParameterCode())){
			if(listForm.getUnNumber()!=null && listForm.getUnNumber().trim().length()>0 ){
				filterVO.setUnWeight(listForm.getUnNumber().toUpperCase());
			}
		}
		if("DVCST".equals(listForm.getParameterCode())){
			if(listForm.getDvForCustoms()!=null && listForm.getDvForCustoms().trim().length()>0 ){
				filterVO.setDvForCustoms(listForm.getDvForCustoms().toUpperCase());
			}
		}
		if("DVCRG".equals(listForm.getParameterCode())){
			if(listForm.getDvForCarriage()!=null && listForm.getDvForCarriage().trim().length()>0 ){
				filterVO.setDvForCarriage(listForm.getDvForCarriage().toUpperCase());
			}
		}
		//Added by A-8130 for ICRD-232462 ends
		if(listForm.getLevel()!= null && listForm.getLevel().trim().length()>0){
			if(!listForm.getLevel().equals(ALL)){
				filterVO.setEmbargoLevel(listForm.getLevel());
			}
			else{
				filterVO.setEmbargoLevel("");
			}
		}
		
		if(listForm.getViaPointType()!= null && 
				listForm.getViaPointType().trim().length()>0){
			filterVO.setViaPoint(listForm.getViaPoint().toUpperCase());
			filterVO.setViaPointType(listForm.getViaPointType());
		}
		
		if(listForm.getDaysOfOperation()!= null && 
				listForm.getDaysOfOperation().trim().length()>0){
			filterVO.setDaysOfOperation(listForm.getDaysOfOperation());
		}
		
		if(listForm.getRuleType()!=null && listForm.getRuleType().trim().length()>0){
			filterVO.setRuleType(listForm.getRuleType());
		}
		if(listForm.getCategory()!=null && listForm.getCategory().trim().length()>0){
			filterVO.setCategory(listForm.getCategory());
		}
		if(listForm.getComplianceType()!=null && listForm.getComplianceType().trim().length()>0){
			filterVO.setComplianceType(listForm.getComplianceType());
		}
		if(listForm.getApplicableTransactions()!=null && listForm.getApplicableTransactions().trim().length()>0){
			filterVO.setApplicableTransactions(listForm.getApplicableTransactions());
		}
		log.log(Log.FINE, "getFilter()  FilterVO", filterVO);
		return filterVO;
	}


	/**
	 * Method validateForm validate sthe form before the server call.
	 * IF the form data is not valid,errorVO is returned
	 * @param form
	 * @param session
	 * @param logonAttributes
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(ListEmbargoRulesForm form) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO error = null;
		/*boolean isValid = true;*/
		boolean isValidDate = true;
		boolean isValidDateCheck=true;
		LocalDate fromDate = null;
		LocalDate toDate= null;

		if(form.getStartDate()!=null && form.getStartDate().trim().length()>0){
			if(!DateUtilities.isValidDate(form.getStartDate(),LocalDate.CALENDAR_DATE_FORMAT)){
				//isValid = false;
				isValidDate = false;
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.notvalidstartdate", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}
		}

		if(form.getEndDate()!=null && form.getEndDate().trim().length()>0){
			if(!DateUtilities.isValidDate(form.getEndDate(),LocalDate.CALENDAR_DATE_FORMAT)){
				//isValid = false;
				isValidDate = false;
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.notvalidenddate", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);

			}
		}

		if(form.getEndDate()!=null && form.getEndDate().trim().length()>0
				&& form.getStartDate()!=null && form.getStartDate().trim().length()>0
						&& isValidDate == isValidDateCheck){
			LocalDate localStartDate
					= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			 fromDate = localStartDate.setDate(form.getStartDate());
			LocalDate localEndDate
					= new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			 toDate = localEndDate.setDate(form.getEndDate());
			 Calendar calTodate = toDate.toCalendar();
			 Calendar calFromdate = fromDate.toCalendar();

			if(calTodate.before(calFromdate)){
			//	isValid = false;
				Object[] obj = { "" };
				error = new ErrorVO("reco.defaults.calendar", obj);
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}
		}
		if((form.getDestinationType()!=null && form.getDestinationType().trim().length()>0)
				&& (form.getDestination()==null || form.getDestination().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.enterdestination", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((form.getDestination()!=null && form.getDestination().trim().length()>0)
				&& (form.getDestinationType()==null || form.getDestinationType().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.enterdestinationtype", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}	
		if((form.getOriginType()!=null && form.getOriginType().trim().length()>0)
				&& (form.getOrigin()==null || form.getOrigin().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.enterorigin", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((form.getOrigin()!=null && form.getOrigin().trim().length()>0)
				&& (form.getOriginType()==null || form.getOriginType().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.enterorigintype", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}	
		
		if((form.getViaPointType()!=null && form.getViaPointType().trim().length()>0)
				&& (form.getViaPoint()==null || form.getViaPoint().trim().length()==0)){
			error = new ErrorVO("reco.defaults.enterviapoint");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		
		if((form.getViaPoint()!=null && form.getViaPoint().trim().length()>0)
				&& (form.getViaPointType()==null || form.getViaPointType().trim().length()==0)){
			error = new ErrorVO("reco.defaults.enterviapointtype");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}	
		//Added by A-7924 as part of ICRD-313966 starts
		if((form.getSegmentDestinationType()!=null && form.getSegmentDestinationType().trim().length()>0)
				&& (form.getSegmentDestination()==null || form.getSegmentDestination().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.entersegmentdestination", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((form.getSegmentDestination()!=null && form.getSegmentDestination().trim().length()>0)
				&& (form.getSegmentDestinationType()==null || form.getSegmentDestinationType().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.entersegmentdestinationtype", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}	
		if((form.getSegmentOriginType()!=null && form.getSegmentOriginType().trim().length()>0)
				&& (form.getSegmentOrigin()==null || form.getSegmentOrigin().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.entersegmentorigin", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((form.getSegmentOrigin()!=null && form.getSegmentOrigin().trim().length()>0)
				&& (form.getSegmentOriginType()==null || form.getSegmentOriginType().trim().length()==0)){
			//isValid = false;
			Object[] obj = { "" };
			error = new ErrorVO("reco.defaults.entersegmentorigintype", obj);
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		//Added by A-7924 as part of ICRD-313966 ends
		if("FLTNUM".equals(form.getParameterCode())) {
				
			if(form.getCarrierCode()!=null && form.getCarrierCode().trim().length()>0) {
				try {
					AirlineValidationVO airlineValidationVO = validateAlphaCode(form.getCarrierCode().toUpperCase());
				} catch (BusinessDelegateException businessDelegateException) {
					Collection<ErrorVO> err = handleDelegateException(businessDelegateException);
					errors.addAll(err);
				}
			}
			if(form.getCarrierCode()==null || form.getCarrierCode().trim().length()==0 || form.getFlightNumber()==null || form.getFlightNumber().trim().length()==0){
				error = new ErrorVO("reco.defaults.parametervalueempty");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);
			}	
		}
		if((form.getParameterCode()!=null && form.getParameterCode().trim().length()>0)
				&& (form.getParameterValue()==null || form.getParameterValue().trim().length()==0)){
			error = new ErrorVO("reco.defaults.parametervalueempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}
		if((form.getParameterCode()==null ||  form.getParameterCode().trim().length()==0)
				&& (form.getParameterValue()!=null && form.getParameterValue().trim().length()>0)){
			error = new ErrorVO("reco.defaults.parametercodeempty");
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
		}		
		return errors;
	}

	/**This method returns the EmbargoDetails as a page after obtaining the description for
	 * various codes.*/
	 /** @param pg
	 * @param session
	 * @return Page<EmbargoDetailsVO>
	 */
	private Page<EmbargoDetailsVO> getDetails(Page<EmbargoDetailsVO> pg,
							ListEmbargoRulesSession session) {
		for(EmbargoDetailsVO vo:pg){
			String description = getStatusDescription(vo.getStatus(),
									vo.getIsSuspended(),session);
			String embLevel
					= getLevelDescription(vo.getEmbargoLevel(),session);
			String ruleType = getRuleTypeDescription(vo.getRuleType(),session);
			String catType = getCategoryDescription(vo.getCategory(),session);
			String appTxnType = getApplicableTransactionsDescription(vo.getApplicableTransactions(),session);
			String compType = getComplianceTypeDescription(vo.getComplianceType(),session);
			if(vo.getParams()!=null && vo.getParams().size()>0){
				StringBuilder mailCategoryBuilder = null;
				StringBuilder mailClassBuilder = null;
				StringBuilder mailSubClassGroupBuilder = null;
				StringBuilder serviceCargoClassBuilder = null;
				StringBuilder shipmentTypeBuilder = null;
				StringBuilder serviceTypeBuilder = null;
				for(EmbargoParameterVO param : vo.getParams()){
					if(MAIL_CATEGORY.equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String mailCat[] = param.getParameterValues().split(",");
						for(String cat : mailCat){
							if(mailCategoryBuilder==null){
								mailCategoryBuilder = new StringBuilder();
								mailCategoryBuilder.append(getParameterDescription(cat,session,param.getParameterCode()));
							}
							else{
								mailCategoryBuilder.append(",");
								mailCategoryBuilder.append(getParameterDescription(cat,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(mailCategoryBuilder.toString());
					}	
					if(MAIL_SUBCLASS_GROUP.equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String subClassGrp[] = param.getParameterValues().split(",");
						for(String subclassgroup : subClassGrp){
							if(mailSubClassGroupBuilder==null){
								mailSubClassGroupBuilder = new StringBuilder();
								mailSubClassGroupBuilder.append(getParameterDescription(subclassgroup,session,param.getParameterCode()));
							}
							else{
								mailSubClassGroupBuilder.append(",");
								mailSubClassGroupBuilder.append(getParameterDescription(subclassgroup,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(mailSubClassGroupBuilder.toString());
					}	
					if(MAIL_CLASS.equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String mailClass[] = param.getParameterValues().split(",");
						for(String cls : mailClass){
							if(mailClassBuilder==null){
								mailClassBuilder = new StringBuilder();
								mailClassBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
							else{
								mailClassBuilder.append(",");
								mailClassBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(mailClassBuilder.toString());
					}					
					if("SRVCRGCLS".equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String srvcrgcls[] = param.getParameterValues().split(",");
						for(String cls : srvcrgcls){
							if(serviceCargoClassBuilder==null){
								serviceCargoClassBuilder = new StringBuilder();
								serviceCargoClassBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
							else{
								serviceCargoClassBuilder.append(",");
								serviceCargoClassBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(serviceCargoClassBuilder.toString());
					}	
					if("SHPTYP".equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String shipTyp[] = param.getParameterValues().split(",");
						for(String cls : shipTyp){
							if(shipmentTypeBuilder==null){
								shipmentTypeBuilder = new StringBuilder();
								shipmentTypeBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
							else{
								shipmentTypeBuilder.append(",");
								shipmentTypeBuilder.append(getParameterDescription(cls,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(shipmentTypeBuilder.toString());
					}
					if(SRVCTYP.equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String []servTyp = param.getParameterValues().split(",");
						for(String cat : servTyp){
							if(serviceTypeBuilder==null){
								serviceTypeBuilder = new StringBuilder();
								serviceTypeBuilder.append(getParameterDescription(cat,session,param.getParameterCode()));
							}
							else{
								serviceTypeBuilder.append(",");
								serviceTypeBuilder.append(getParameterDescription(cat,session,param.getParameterCode()));
							}
						}
						param.setParameterValues(serviceTypeBuilder.toString());
					}	
					if(SRVCTYPFRTECSTP.equals(param.getParameterCode()) && !"-".equals(param.getParameterValues())){
						String[] servTypFrTecStp = param.getParameterValues().split(",");
						param.setParameterValues(Arrays.toString(servTypFrTecStp));
					}
				}
			}
			vo.setStatus(description);
			vo.setEmbargoLevel(embLevel);
			vo.setRuleType(ruleType);
			vo.setCategory(catType);
			vo.setApplicableTransactions(appTxnType);
			vo.setComplianceType(compType);
		}
		return pg;
	}

	/**This method returns the status description corresponding to the status code.
	 * And also changes the status to 'SUSPENDED' if the  suspendedFlag is true
	 * @param statusCode
	 * @param isSuspended
	 * @param listSession
	 * @return String statusDescription
	 */
	private String getStatusDescription(String statusCode,
			boolean isSuspended,ListEmbargoRulesSession listSession){

    	String statusDescription = null;        	/*
    	 * Obtain the statusDescription from oneTimeVOs
    	 */
    	if(statusCode != null && statusCode.trim().length()>0){
	    	if(isSuspended==TRUE && statusCode.equals(ACTIVE)){
	    		statusCode = SUSPENDED;
	    	}
    	}
    	Collection<OneTimeVO> oneTimeVOs = listSession.getStatus();
    	if(oneTimeVOs != null && oneTimeVOs.size()>0){
	    	for(OneTimeVO onetimeVO : oneTimeVOs) {
	    		if(onetimeVO.getFieldDescription() != null &&
	    				onetimeVO.getFieldValue().equals(statusCode)) {
	    			statusDescription = onetimeVO.getFieldDescription();
	    		}
	    	}
    	}

    	return statusDescription;
	}


	/**This method returns the levelDescription corresponding to the levelCode
	 * @param levelCode
	 * @param listInterface
	 * @return String levelDescription
	 */
	private String getLevelDescription(String levelCode,
			ListEmbargoRulesSession listInterface){

    	String levelDescription = null;        	/*
    	 * Obtain the statusDescription from oneTimeVOs
    	 */

    	Collection<OneTimeVO> oneTimeVOs = listInterface.getLevelCode();
    	if(oneTimeVOs != null && oneTimeVOs.size()>0){
    	for(OneTimeVO onetimeVO : oneTimeVOs) {
    		if(onetimeVO.getFieldDescription() != null &&
    				onetimeVO.getFieldValue().equals(levelCode)) {
    			levelDescription = onetimeVO.getFieldDescription();

    		}
    	}
    	}
    	return levelDescription;
	}
	
	/**This method returns the parameterDescription corresponding to the levelCode
	 * @param levelCode
	 * @param listInterface
	 * @return String levelDescription
	 */
	private String getParameterDescription(String parameterCode,
			ListEmbargoRulesSession listInterface,String parameterType){

    	String parameterDescription = null;        	/*
    	 * Obtain the statusDescription from oneTimeVOs
    	 */
    	Collection<OneTimeVO> oneTimeVOs = null;
    	if(MAIL_CATEGORY.equals(parameterType)){
    		oneTimeVOs = listInterface.getMailCategory();
    	}
    	else if(MAIL_CLASS.equals(parameterType)){
    		oneTimeVOs = listInterface.getMailClass();
    	}
    	else if(MAIL_SUBCLASS_GROUP.equals(parameterType)){
    		oneTimeVOs = listInterface.getMailSubClassGrp();
    	}
    	else if("SRVCRGCLS".equals(parameterType)){  
    		oneTimeVOs = listInterface.getServiceCargoClass();
    	}
    	else if("SHPTYP".equals(parameterType)){     
    		oneTimeVOs = listInterface.getShipmentType();
    	}
    	else if(SRVCTYP.equals(parameterType)){     
    		oneTimeVOs = listInterface.getServiceType();
    	}
    	else if(SRVCTYPFRTECSTP.equals(parameterType)){     
    		oneTimeVOs = listInterface.getServiceTypeForTechnicalStop();
    	}
    	else {
        /* Do Nothing */}
    	
    	if(oneTimeVOs != null && oneTimeVOs.size()>0){
    	for(OneTimeVO onetimeVO : oneTimeVOs) {
    		if(onetimeVO.getFieldDescription() != null &&
    				onetimeVO.getFieldValue().equals(parameterCode)) {
    			if(
        				SRVCTYP.equals(parameterType)) {
    				parameterDescription = onetimeVO.getFieldValue();
    			}
    			else {
    				parameterDescription = onetimeVO.getFieldDescription();
    			}
    		}
    	}
    	}
    	if(oneTimeVOs != null && oneTimeVOs.size()>0){
        	for(OneTimeVO onetimeVO : oneTimeVOs) {
        		if(onetimeVO.getFieldDescription() != null &&
        				onetimeVO.getFieldValue().equals(parameterCode)) {
        			if(
            				SRVCTYPFRTECSTP.equals(parameterType)) {
    				parameterDescription = onetimeVO.getFieldValue();
    			}
    			else {
    				parameterDescription = onetimeVO.getFieldDescription();
    			}
    			
    		}
    	}
    	}
    	return parameterDescription;
	}

	/**
	 * @param existingMap
	 * @param page
	 * @return HashMap
	 */
	private HashMap<String,String> buildIndexMap(HashMap<String,String> existingMap, Page<EmbargoDetailsVO> page) {
		HashMap<String,String> finalMap = existingMap;
		/*String currentPage = String.valueOf(page.getPageNumber());
		String currentAbsoluteIndex = String.valueOf(page.getAbsoluteIndex());*/
		String indexPage = String.valueOf((page.getPageNumber()+1));

		boolean isPageExits = false;
		Set<Map.Entry<String, String>> set = existingMap.entrySet();
		for (Map.Entry<String, String> entry : set) {
			String pageNum = entry.getKey();
			if (pageNum.equals(indexPage)) {
				isPageExits = true;
			}
		}

		if (!isPageExits) {
			finalMap.put(indexPage, String.valueOf(page.getAbsoluteIndex()));
		}
		return finalMap;
	}
	
	private void populateForm(ListEmbargoRulesForm form, EmbargoFilterVO filtervo) {
		if(filtervo.getEmbargoRefNumber()!=null && 
				filtervo.getEmbargoRefNumber().trim().length()>0 ) {
			form.setRefNumber(filtervo.getEmbargoRefNumber().trim());			
		}
		else {			
			form.setRefNumber("");
		}
		if(filtervo.getEmbargoLevel()!=null && 
				filtervo.getEmbargoLevel().trim().length()>0 ) {
			form.setLevel(filtervo.getEmbargoLevel().trim());			
		}
		else {			
			form.setLevel("");
		}
		if(filtervo.getEndDate()!=null) {
			form.setEndDate(filtervo.getEndDate().toDisplayDateOnlyFormat());			
		}
		else {			
			form.setEndDate("");
		}
		if(filtervo.getStartDate()!=null) {
			form.setStartDate(filtervo.getStartDate().toDisplayDateOnlyFormat());			
		}
		else {			
			form.setStartDate("");
		}
		if(filtervo.getStatus()!=null && filtervo.getStatus().trim().length()>0) {
			form.setStatus(filtervo.getStatus().trim());			
		}
		else {			
			form.setStatus("");
		}
		if(filtervo.getDestinationType()!=null && 
				filtervo.getDestinationType().trim().length()>0 &&
					filtervo.getDestination()!=null && 
						filtervo.getDestination().trim().length()>0	) {
				form.setDestination(filtervo.getDestination().trim());
				form.setDestinationType(filtervo.getDestinationType().trim());				
			
		}
		else {			
			form.setDestinationType("");
			form.setDestination("");			
		}
		if(filtervo.getOriginType()!=null && 
				filtervo.getOriginType().trim().length()>0 &&
					filtervo.getOrigin()!=null && 
						filtervo.getOrigin().trim().length()>0	) {
			form.setOrigin(filtervo.getOrigin().trim());
			form.setOriginType(filtervo.getOriginType().trim());				
		}
		else {			
			form.setOriginType("");
			form.setOrigin("");			
		}
		//Added by A-7924 as part of ICRD-313966 starts
		if(filtervo.getSegmentOriginType()!=null && 
				filtervo.getSegmentOriginType().trim().length()>0 &&
					filtervo.getSegmentOrigin()!=null && 
						filtervo.getSegmentOrigin().trim().length()>0	) {
			form.setSegmentOrigin(filtervo.getSegmentOrigin().trim());
			form.setSegmentOriginType(filtervo.getSegmentOriginType().trim());				
		}
		else {			
			form.setSegmentOriginType("");
			form.setSegmentOrigin("");			
		}
		if(filtervo.getSegmentDestinationType()!=null && 
				filtervo.getSegmentDestinationType().trim().length()>0 &&
					filtervo.getSegmentDestination()!=null && 
						filtervo.getSegmentDestination().trim().length()>0	) {
				form.setSegmentDestination(filtervo.getSegmentDestination().trim());
				form.setSegmentDestinationType(filtervo.getSegmentDestinationType().trim());				
		}
		else {			
			form.setSegmentDestinationType("");
			form.setSegmentDestination("");			
		}
		//Added by A-7924 as part of ICRD-313966 ends
		if(filtervo.getParameterCode()!=null && filtervo.getParameterCode().trim().length()>0) {
			form.setParameterCode(filtervo.getParameterCode().trim());			
		}
		else {			
			form.setParameterCode("");
		}
		if(filtervo.getParameterValues()!=null && filtervo.getParameterValues().trim().length()>0) {
			if("FLTNUM".equals(filtervo.getParameterCode())) {
				if(filtervo.getParameterValues().contains("~")) {
					String[] fltDetails = filtervo.getParameterValues().split("~");
					form.setCarrierCode(fltDetails[0]);
					form.setFlightNumber(fltDetails[1]);
					
				} else {
					form.setCarrierCode(filtervo.getParameterValues());
				}
				
				form.setParameterValue("");
				
			} else {
				form.setParameterValue(filtervo.getParameterValues().trim());
			}
		}
		else {			
			form.setParameterValue("");
		}		
		//Added for ICRD-167922 starts
		if ((filtervo.getDaysOfOperation() != null) && (filtervo.getDaysOfOperation().trim().length() > 0)) {
		      form.setDaysOfOperation(filtervo.getDaysOfOperation().trim());
		    }
	    else {
	    	form.setDaysOfOperation("");
	    	}
	    if ((filtervo.getRuleType() != null) && (filtervo.getRuleType().trim().length() > 0)) {
	    	form.setRuleType(filtervo.getRuleType().trim());
	    	}
	    else {
	    	form.setRuleType("");
	    	}
	    form.setDefaultText("");
		    //Added for ICRD-167922 ends
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
	
	/**
	 *
	 * @param ownerCode
	 * @return airlineValidationVO
	 * @throws BusinessDelegateException 
	 */
   public AirlineValidationVO validateAlphaCode(String ownerCode) throws BusinessDelegateException {
   	log.entering("ListEmbargosCommand", "validateAlphaCode");

	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
	LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	
   	AirlineValidationVO airlineValidationVO = null;
   	AirlineDelegate airlineDelegate = new AirlineDelegate();
	 	
   	return airlineDelegate.validateAlphaCode(logonAttributes.getCompanyCode(), ownerCode);
   }
   
   private String getCategoryDescription(String catCode,
			ListEmbargoRulesSession listInterface){

   	String catDescription = null;        	/*
   	 * Obtain the statusDescription from oneTimeVOs
   	 */

   	Collection<OneTimeVO> oneTimeVOs = listInterface.getCategoryTypes();
   	for(OneTimeVO onetimeVO : oneTimeVOs) {
   		if(onetimeVO.getFieldDescription() != null &&
   				onetimeVO.getFieldValue().equals(catCode)) {
   			catDescription = onetimeVO.getFieldDescription();

   		}
   	}
   	return catDescription;
	}
   private String getApplicableTransactionsDescription(String appTxnCode,
			ListEmbargoRulesSession listInterface){
      	/*
  	 * Obtain the statusDescription from oneTimeVOs
  	 */
  	StringBuilder builder = null;
  	if(appTxnCode!=null){
  	String app[] = appTxnCode.split(",");

  	Collection<OneTimeVO> oneTimeVOs = listInterface.getApplicableTransactions();
  	for(String apptxn: app){
  	for(OneTimeVO onetimeVO : oneTimeVOs) {
  		if(onetimeVO.getFieldDescription() != null &&
	  				onetimeVO.getFieldValue().equals(apptxn)) {
	  			if(builder!=null)
	  				{
	  				builder.append(",").append(onetimeVO.getFieldDescription());
	  				}
	  			else{
	  				builder = new StringBuilder();
	  				builder.append(onetimeVO.getFieldDescription());
	  			}
	  		}
	  	}
  	}
  	// Added by A-5867 for ICRD-90610 
  	return null !=builder?builder.toString():null;
  	}
  	else{
  		return null;
  	}
  	
	}
   private String getComplianceTypeDescription(String compTypeCode,
			ListEmbargoRulesSession listInterface){

  	String compTypeDescription = null;        	/*
  	 * Obtain the statusDescription from oneTimeVOs
  	 */

  	Collection<OneTimeVO> oneTimeVOs = listInterface.getComplianceTypes();
  	for(OneTimeVO onetimeVO : oneTimeVOs) {
  		if(onetimeVO.getFieldDescription() != null &&
  				onetimeVO.getFieldValue().equals(compTypeCode)) {
  			compTypeDescription = onetimeVO.getFieldDescription();

  		}
  	}
  	return compTypeDescription;
	}
   private String getRuleTypeDescription(String ruleTypeCode,
			ListEmbargoRulesSession listInterface){

  	String ruleTypeDescription = null;        	/*
  	 * Obtain the statusDescription from oneTimeVOs
  	 */

  	Collection<OneTimeVO> oneTimeVOs = listInterface.getRuleType();
  	for(OneTimeVO onetimeVO : oneTimeVOs) {
  		if(onetimeVO.getFieldDescription() != null &&
  				onetimeVO.getFieldValue().equals(ruleTypeCode)) {
  			ruleTypeDescription = onetimeVO.getFieldDescription();

  		}
  	}
  	return ruleTypeDescription;
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
   private String getDescription(String status,Collection<OneTimeVO> originOneTimeVOs){
   	String description="";
   	for (OneTimeVO oneTimeVO : originOneTimeVOs ) {
   		if(oneTimeVO.getFieldValue().equals(status)){
   			description=oneTimeVO.getFieldDescription();
   			break;
   		}
   	}
   	return description;
	}
}
