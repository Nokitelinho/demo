/*
 * ScreenloadCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
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
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateTimeFormatHelper;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *
 * @author A-1747
 *
 */
public class ScreenloadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String LEVELCODE = "reco.defaults.levelcodes";
	private static final String APPLICABLE = "reco.defaults.applicablecodes";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String ULD_POS = "uld.defaults.position"; //Added by A-8810 for IASCB-6097
	private static final String VIA_POINT = "reco.defaults.viapoint";
	private static final String STATION="S";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
	private static final String DEFAPPLICABLE_TRANSACTIONS = "reco.defaults.defaultapplicabletransactions";
	//Added for MAIL Embargo
	private static final String MAL_CLASS= "mailtracking.defaults.mailclass";
	private static final String MAL_CATEGORY= "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP= "mailtracking.defaults.mailsubclassgroup";
	private static final String APPLICABLE_ON_ORIGIN= "O";
	private static final String APPLICABLE_ON_DESTINATION= "D";
	private static final String APPLICABLE_ON_VIA_POINT= "V";
	private static final String APPLICABLE_ON_SEGMENTORIGIN= "L";//added A-7924 as part of ICRD-299901
	private static final String APPLICABLE_ON_SEGMENTDESTINATION= "U";//added A-7924 as part of ICRD-299901
	private static final String APPLICABLE_ON_ALL= "A";
	private static final String APPLICABLE_ON_ANY= "Y";
	private static final String ADMIN_USER_LANGUAGES= "admin.user.language";
	private static final String WEIGHTTYPE_APPLICABLEON="reco.defaults.weighttype";	
	private static final String APPLICABLE_LEVELS_FOR_PARAMETERS = "reco.defaults.applicableonlevelsforparameters";//added by A-7924 as part of ICRD-299901
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass"; //added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	private static final String SERVICE_TYPE = "message.ssim.servicetype";
	private static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "message.ssim.serviceTypeForTechnicalStop";
	private static final String UNID_PACKGING_GROUP = "shared.dgr.unid.packaginggroup";
	private static final String UNID_SUB_RISK = "unidsubrisk";
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
			log.log(Log.FINE,"<-----------Screenload Commmand----------------------->");
		
		   ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		   EmbargoRulesDelegate embargoRulesDelegate = new EmbargoRulesDelegate();
		    LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		    String companyCode = logonAttributes.getCompanyCode();
		    String stationCode = logonAttributes.getStationCode();
		    String userId 	= logonAttributes.getUserId();
		    MaintainEmbargoRulesSession maintainEmbargoSession= getScreenSession( "reco.defaults","reco.defaults.maintainembargo");
		   	if(maintainEmbargoSession.getEmbargoVo()!=null){
		   		maintainEmbargoSession.setEmbargoVo(null);
		   	}


		   	MaintainEmbargoRulesForm maintainEmbargoForm=(MaintainEmbargoRulesForm)invocationContext.screenModel;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			EmbargoRulesVO embargoVo=new EmbargoRulesVO();
			Map hashMap = null;
			log.log(Log.FINE, "Controller:getScreenLoadDetails");

			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(LEVELCODE);
			oneTimeList.add(APPLICABLE);
			oneTimeList.add(EMBARGOSTATUS);
			oneTimeList.add(GEOGRAPHIC_LEVEL_TYPES);
			oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
			oneTimeList.add(VIA_POINT);
			oneTimeList.add(CATEGORY_TYPES);
			oneTimeList.add(COMPLIANCE_TYPES);
			oneTimeList.add(APPLICABLE_TRANSACTIONS);
			oneTimeList.add(EMBARGO_PARAMETERS);
			oneTimeList.add(FLIGHT_TYPE);
			oneTimeList.add(ULD_POS);
			//Added by A-8130 for ICRD-232462
			oneTimeList.add(WEIGHTTYPE_APPLICABLEON);
			oneTimeList.add(MAL_CATEGORY);
			oneTimeList.add(MAL_CLASS);
			oneTimeList.add(MAL_SUBCLS_GRP);
			//added for ICRD-213193	by A-7815			
			oneTimeList.add(ADMIN_USER_LANGUAGES);		
			oneTimeList.add(APPLICABLE_LEVELS_FOR_PARAMETERS);//Added by A-7924 as part of ICRD-299901 
			oneTimeList.add(SERVICE_CARGO_CLASS);
			oneTimeList.add(SHIPMENT_TYPE);
			oneTimeList.add(SERVICE_TYPE);
			oneTimeList.add(SERVICE_TYPE_FOR_TECHNICAL_STOP);
			oneTimeList.add(UNID_PACKGING_GROUP);
			oneTimeList.add(UNID_SUB_RISK);
			Collection<EmbargoGlobalParameterVO> globalParams = new ArrayList<EmbargoGlobalParameterVO>();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
				//globalParams = embargoRulesDelegate.findGlobalParameterCodes(companyCode);

			} catch (BusinessDelegateException exception) {
				handleDelegateException(exception);
				log.log(Log.SEVERE, "onetime fetch exception");
			}
				

			Collection<OneTimeVO> levelCodes = (Collection<OneTimeVO>)hashMap.get(LEVELCODE);
			Collection<OneTimeVO> applicableCodesTemp = (Collection<OneTimeVO>)hashMap.get(APPLICABLE);
			Collection<OneTimeVO> embargoStatus = (Collection<OneTimeVO>)hashMap.get(EMBARGOSTATUS);
			Collection<OneTimeVO>  geographicLevelType= (Collection<OneTimeVO>)hashMap.get(GEOGRAPHIC_LEVEL_TYPES);
			Collection<OneTimeVO>  dayOfOperationApplicableOnTemp = (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
			Collection<OneTimeVO>  viaPointTypes = (Collection<OneTimeVO>)hashMap.get(VIA_POINT);
			Collection<OneTimeVO>  categoryTypes = (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES);
			Collection<OneTimeVO>  complianceTypes = (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPES);
			Collection<OneTimeVO>  applicableTransactions = (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTIONS);
			Collection<OneTimeVO>  embargoParameters = (Collection<OneTimeVO>)hashMap.get(EMBARGO_PARAMETERS);
			Collection<OneTimeVO>  flightTypes = (Collection<OneTimeVO>)hashMap.get(FLIGHT_TYPE);
			Collection<OneTimeVO>  uldPos = (Collection<OneTimeVO>)hashMap.get(ULD_POS);
			Collection<OneTimeVO>  weightsApplicableOn=(Collection<OneTimeVO>)hashMap.get(WEIGHTTYPE_APPLICABLEON);
			Collection<OneTimeVO>  mailClass =  (Collection<OneTimeVO>)hashMap.get(MAL_CLASS);
			Collection<OneTimeVO>  mailCategory =  (Collection<OneTimeVO>)hashMap.get(MAL_CATEGORY);
			Collection<OneTimeVO>  mailSubClsGrp =  (Collection<OneTimeVO>)hashMap.get(MAL_SUBCLS_GRP);			
			Collection<OneTimeVO>  adminUserlanguages =  (Collection<OneTimeVO>)hashMap.get(ADMIN_USER_LANGUAGES);	
			Collection<OneTimeVO>  serviceCargoClass =  (Collection<OneTimeVO>)hashMap.get(SERVICE_CARGO_CLASS);
			Collection<OneTimeVO>  shipmentType =  (Collection<OneTimeVO>)hashMap.get(SHIPMENT_TYPE);
			Collection<OneTimeVO>  serviceType =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE);
			Collection<OneTimeVO>  serviceTypeForTechnicalStop =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE_FOR_TECHNICAL_STOP);
			Collection<OneTimeVO>  unPg = (Collection<OneTimeVO>)hashMap.get(UNID_PACKGING_GROUP);
			Collection<OneTimeVO>  subRisk = (Collection<OneTimeVO>)hashMap.get(UNID_SUB_RISK);
			Collection<OneTimeVO>  dayOfOperationApplicableOn = new ArrayList<OneTimeVO>();
			Collection<OneTimeVO>  geographicLevel = new ArrayList<OneTimeVO>();
			Collection<OneTimeVO> applicableLevelsForParameters = (Collection<OneTimeVO>)hashMap.get(APPLICABLE_LEVELS_FOR_PARAMETERS);//Added by A-7924 as part of ICRD-299901 
			if (dayOfOperationApplicableOnTemp != null) {
				populateApplicableOnAndGeographicLevel(
						dayOfOperationApplicableOnTemp,
						dayOfOperationApplicableOn, geographicLevel);
			}
			Collection<OneTimeVO>  applicableCodes = new ArrayList<OneTimeVO>();
			if(applicableCodesTemp!=null){
				OneTimeVO include=null;
				OneTimeVO exclude=null;
				OneTimeVO excludeIf=null;
				for(OneTimeVO vo : applicableCodesTemp){
					if("IN".equals(vo.getFieldValue())){
						include = vo;
					}
					if("EX".equals(vo.getFieldValue())){
						exclude=vo;
					}
					if("EXIF".equals(vo.getFieldValue())){
						excludeIf=vo;
					}
				}
				if(include!=null)
					{
					applicableCodes.add(include);
					}
				if(exclude!=null)
					{
					applicableCodes.add(exclude);
					}
				if(excludeIf!=null)
					{
					applicableCodes.add(excludeIf);					
			}
			}
			log.log(Log.FINE, "Got from ONETIME");
			ArrayList<EmbargoParameterVO> paramVos = new ArrayList<EmbargoParameterVO>();
			EmbargoParameterVO paramVo = new EmbargoParameterVO();
			paramVo.setParameterCode("");
			paramVo.setParameterValues("");
			paramVo.setOperationalFlag(OPERATION_FLAG_INSERT);
			paramVo.setApplicable("");
			paramVos.add(paramVo);
			maintainEmbargoSession.setApplicableCode(applicableCodes);
			maintainEmbargoSession.setLevelCode(levelCodes);
			maintainEmbargoSession.setEmbargoStatus(embargoStatus);
			maintainEmbargoSession.setGlobalParameters(globalParams);
			maintainEmbargoSession.setFlightTypes(flightTypes);
			maintainEmbargoSession.setUldPos(uldPos);
			maintainEmbargoSession.setMailCategory(mailCategory);
			maintainEmbargoSession.setMailClass(mailClass);
			maintainEmbargoSession.setMailSubClassGrp(mailSubClsGrp);
			maintainEmbargoSession.setWeightsApplicableOn(weightsApplicableOn);
			maintainEmbargoSession.setGeographicLevelType(geographicLevelType);
			maintainEmbargoSession.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
			maintainEmbargoSession.setGeographicLevel(geographicLevel);
			maintainEmbargoSession.setViaPointTypes(viaPointTypes);
			maintainEmbargoSession.setCategoryTypes(categoryTypes);
			maintainEmbargoSession.setComplianceTypes(complianceTypes);
			maintainEmbargoSession.setApplicableTransactions(applicableTransactions);
			maintainEmbargoSession.setEmbargoParameters(embargoParameters);
			maintainEmbargoSession.setAdminUserlanguages(adminUserlanguages);
			maintainEmbargoSession.setApplicableLevelsForParameters(applicableLevelsForParameters);//Added by A-7924 part of ICRD-299901
			maintainEmbargoSession.setServiceCargoClass(serviceCargoClass);
			maintainEmbargoSession.setShipmentType(shipmentType);
			maintainEmbargoSession.setServiceType(serviceType);
			maintainEmbargoSession.setServiceTypeForTechnicalStop(serviceTypeForTechnicalStop);
			maintainEmbargoSession.setUnPg(unPg);
			maintainEmbargoSession.setSubRisk(subRisk);
			maintainEmbargoForm.setStartDate(DateUtilities.getCurrentDate(DateTimeFormatHelper.getDefaultDateFormat()));
			maintainEmbargoForm.setEndDate(DateUtilities.getCurrentDate(DateTimeFormatHelper.getDefaultDateFormat()));
			maintainEmbargoForm.setOriginType(STATION);
			maintainEmbargoForm.setOrigin("");
			maintainEmbargoForm.setDestinationType(STATION);
			maintainEmbargoForm.setDestination("");
			maintainEmbargoForm.setRefNumber("");
			maintainEmbargoForm.setViaPoint("");
			maintainEmbargoForm.setViaPointType("");
			//maintainEmbargoForm.setDaysOfOperation("1234567"); 
			maintainEmbargoForm.setDaysOfOperation(""); 
			maintainEmbargoForm.setDaysOfOperationApplicableOn("");
			maintainEmbargoForm.setApplicableTransactions("");
			maintainEmbargoForm.setCategory("");
			maintainEmbargoForm.setComplianceType("");
			maintainEmbargoForm.setIsDuplicatePresent("N");
			//String startDate=DateUtilities.getCurrentDate("dd-MMM-yyyy");
			//LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			
			boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
			if(hasBusinessPrivilege)
				{
				maintainEmbargoForm.setIsPrivilegedUser("Y");
				}
			else
				{
				maintainEmbargoForm.setIsPrivilegedUser("N");	
				}	
			//Setting Values in embargoVo
			embargoVo.setEmbargoLevel("");
			embargoVo.setEmbargoReferenceNumber("");
			embargoVo.setStatus("");			
			
			//if syspar present defaulting the applicable transactions
			Collection<String> systemparameterCodes = new ArrayList<String>();
			systemparameterCodes.add(DEFAPPLICABLE_TRANSACTIONS);
			Map<String,String> sysParMap = new HashMap<String, String>();
			try {
				sysParMap = sharedDefaultsDelegate.findSystemParameterByCodes(systemparameterCodes);
			} catch (BusinessDelegateException e) {
				log.log(Log.SEVERE, "Exception in findSystemParameterByCodes");
			}			
			if(sysParMap !=null && sysParMap.get(DEFAPPLICABLE_TRANSACTIONS)!=null){
				embargoVo.setApplicableTransactions(sysParMap.get(DEFAPPLICABLE_TRANSACTIONS));
			}
			else{
				embargoVo.setApplicableTransactions("");
			}
			
			
			/*if(hasBusinessPrivilege){
			for(OneTimeVO statusVO: embargoStatus){
		    	if(statusVO.getFieldDescription().equals("DRAFT")){
		    		embargoVo.setStatus(statusVO.getFieldValue());
		    	}
		    }
			}
			else{
				for(OneTimeVO statusVO: embargoStatus){
			    	if(statusVO.getFieldDescription().equals("ACTIVE")){
			    		embargoVo.setStatus(statusVO.getFieldValue());
			    	}
			    }
			}*/
			embargoVo.setDaysOfOperationApplicableOn("");
			
			Date date = TimeConvertor.getCurrentDate();
			String startDate = TimeConvertor.toStringFormat(date,DateTimeFormatHelper.getDefaultDateFormat());
			LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);			
			embargoVo.setStartDate(localStartDate.setDate(startDate));
			
			embargoVo.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
			
			//embargoVo.setParameters(paramVos);
			log.log(Log.FINE,"\n\n embargo vo ------------------------------ "+embargoVo);
			//maintainEmbargoSession.setEmbargoParameterVos(paramVos);
			maintainEmbargoSession.setEmbargoVo(embargoVo);
			
			if(maintainEmbargoSession.getIsSaved() != null){
				if(maintainEmbargoSession.getIsSaved().trim().length()>0){
					Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					String referenceNoObj= maintainEmbargoSession.getIsSaved();
					Object[] obj={referenceNoObj};
					error = new ErrorVO("embargo.save",obj);// Saved Successfully
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);

				}
			}
			maintainEmbargoForm.setRefNumberFlag("false");
			maintainEmbargoSession.removeIsSaved();
			//added for ICRD-213193 by A-7815 
			maintainEmbargoSession.setLocalLanguageEmbargo(null);
			invocationContext.target = "screenload_success";
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
	/**
	 * A-5642
	 * @param dayOfOperationApplicableOnTemp
	 * @param dayOfOperationApplicableOn
	 */
	private void populateApplicableOnAndGeographicLevel(
			Collection<OneTimeVO> dayOfOperationApplicableOnTemp,
			Collection<OneTimeVO> dayOfOperationApplicableOn,
			Collection<OneTimeVO> geographicLevel) {
		OneTimeVO origin = null;
		OneTimeVO destination = null;
		OneTimeVO viapoint = null;
		OneTimeVO segmentorigin = null;//Added by A-7924 as part of ICRD-299901
		OneTimeVO segmentdestination=null;//Added by A-7924 as part of ICRD-299901
		OneTimeVO all = null;
		OneTimeVO any = null;
		for (OneTimeVO vo : dayOfOperationApplicableOnTemp) {
			if (APPLICABLE_ON_ORIGIN.equals(vo.getFieldValue())) {
				origin = vo;
			} else if (APPLICABLE_ON_DESTINATION.equals(
					vo.getFieldValue())) {
				destination = vo;
			} else if (APPLICABLE_ON_VIA_POINT.equals(
					vo.getFieldValue())) {
				viapoint = vo;
				//Added by A-7924 as part of ICRD-299901 starts
			} else if (APPLICABLE_ON_SEGMENTORIGIN.equals(  
					vo.getFieldValue())) {
				segmentorigin = vo;
			} else if (APPLICABLE_ON_SEGMENTDESTINATION.equals(  
					vo.getFieldValue())) {
				segmentdestination = vo;
				//Added by A-7924 as part of ICRD-299901 ends
			} else if (APPLICABLE_ON_ALL.equals(
					vo.getFieldValue())) {
				all = vo;
			} else if (APPLICABLE_ON_ANY.equals(
					vo.getFieldValue())) {
				any = vo;
			}
		}
		if (origin != null) {
			dayOfOperationApplicableOn.add(origin);
			geographicLevel.add(origin);
		}
		if (destination != null) {
			dayOfOperationApplicableOn.add(destination);
			geographicLevel.add(destination);
		}
		if (viapoint != null) {
			dayOfOperationApplicableOn.add(viapoint);
			geographicLevel.add(viapoint);
		}
		//Added by A-7924 as part of ICRD-299901 starts
		if (segmentorigin != null) {
			dayOfOperationApplicableOn.add(segmentorigin);
			geographicLevel.add(segmentorigin);
		}
		if (segmentdestination != null) {
			dayOfOperationApplicableOn.add(segmentdestination);
			geographicLevel.add(segmentdestination);
		}
		//Added by A-7924 as part of ICRD-299901 ends
		if (all != null) {
			dayOfOperationApplicableOn.add(all);
		}
		if (any != null) {
			dayOfOperationApplicableOn.add(any);
		}
	}
}
