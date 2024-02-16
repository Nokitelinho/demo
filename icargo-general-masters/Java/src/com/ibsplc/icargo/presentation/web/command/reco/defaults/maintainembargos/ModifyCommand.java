/*
 * ModifyCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintainembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintainembargos.MaintainEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Command class to modify embargo
 * @author A-1747
 *
 */
public class ModifyCommand extends BaseCommand{

	private static final String SUCCESS_MODE = "screenload_success";

	private static final String MODIFY_SCREENLOAD_SUCCESS = "modifyScreenloadSuccess";

	private static final String MODIFY_SCREENLOAD_FAILURE = "modifyScreenloadFailure";

	private static final String LEVELCODE = "reco.defaults.levelcodes";
	private static final String APPLICABLE = "reco.defaults.applicablecodes";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String VIA_POINT = "reco.defaults.viapoint";
	private static final String STATION="S";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
	
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	private static final String MAL_CLASS= "mailtracking.defaults.mailclass";
	private static final String MAL_CATEGORY= "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP= "mailtracking.defaults.mailsubclassgroup";
	private static final String WEIGHTTYPE_APPLICABLEON="reco.defaults.weighttype";
	private static final String WORKFLOW_SCREEN_ID = "workflow.defaults.messageinbox";
	
	private static final String WORKFLOW_MODULE_NAME = "workflow.defaults";
	private static final String APPLICABLE_ON_ORIGIN= "O";
	private static final String APPLICABLE_ON_DESTINATION= "D";
	private static final String APPLICABLE_ON_VIA_POINT= "V";
	private static final String APPLICABLE_ON_SEGMENTORIGIN= "L";//added A-7924 as part of ICRD-299901
	private static final String APPLICABLE_ON_SEGMENTDESTINATION= "U";//added A-7924 as part of ICRD-299901
	private static final String AIRCRAFT_CLASSIFICATION="shared.aircraft.aircraftClassification";
	
	private static final String LOCALE_EN_US = "en_US";
	private Log log = LogFactory.getLogger("MODIFY COMMAND");
	//added for ICRD-222276 by A-7815
	private static final String ADMIN_USER_LANGUAGES= "admin.user.language";
	private static final String APPLICABLE_LEVELS_FOR_PARAMETERS = "reco.defaults.applicableonlevelsforparameters";//added by A-7924 as part of ICRD-299901
	private static final String SERVICE_CARGO_CLASS = "operations.shipment.servicecargoclass";//added by A-5799 for IASCB-23507
	private static final String SHIPMENT_TYPE = "reco.defaults.shipmenttype"; //added by A-5799 for IASCB-23507
	private static final String SERVICE_TYPE = "message.ssim.servicetype";
	private static final String SERVICE_TYPE_FOR_TECHNICAL_STOP = "message.ssim.serviceTypeForTechnicalStop";
	private static final String UNID_PACKGING_GROUP = "shared.dgr.unid.packaginggroup";
	private static final String UNID_SUB_RISK = "unidsubrisk";
	
	/**
	 * The execute method in Modify Command
	 *
	 * @author A-1747
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainEmbargoRulesForm maintainEmbargoForm =
					(MaintainEmbargoRulesForm) invocationContext.screenModel;
		
		MessageInboxSession messageInboxSession = 
			getScreenSession(WORKFLOW_MODULE_NAME, WORKFLOW_SCREEN_ID);
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		EmbargoRulesDelegate embargoDelegate ;
		EmbargoRulesVO embargoVo = null;
		ArrayList<String> applicableOn = new ArrayList<String>();
		ArrayList<String> applicableOnParameter = new ArrayList<String>();
		String companyCode = logonAttributes.getCompanyCode();
	    embargoDelegate = new EmbargoRulesDelegate();
	    MaintainEmbargoRulesSession maintainEmbargoSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintainembargo");
		maintainEmbargoSession.setDetailsButton(true);
		
		SharedDefaultsDelegate sharedDefaultsDelegate
						= new SharedDefaultsDelegate();
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<EmbargoGlobalParameterVO> globalParams
						= new ArrayList<EmbargoGlobalParameterVO>();
		oneTimeList.add(APPLICABLE);
		oneTimeList.add(EMBARGOSTATUS);
		oneTimeList.add(LEVELCODE);
		oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
		oneTimeList.add(VIA_POINT);
		oneTimeList.add(CATEGORY_TYPES);
		oneTimeList.add(COMPLIANCE_TYPES);
		oneTimeList.add(APPLICABLE_TRANSACTIONS);
		oneTimeList.add(EMBARGO_PARAMETERS);
		oneTimeList.add(GEOGRAPHIC_LEVEL_TYPES);
		//Added by A-8130 for ICRD-232462
		oneTimeList.add(WEIGHTTYPE_APPLICABLEON);
		// Added by A-5867 for ICRD-90668 
		oneTimeList.add(FLIGHT_TYPE);
		oneTimeList.add(MAL_CATEGORY);
		oneTimeList.add(MAL_CLASS);
		oneTimeList.add(MAL_SUBCLS_GRP);		
		//added  for ICRD-213193	by A-7815			
		oneTimeList.add(ADMIN_USER_LANGUAGES);	
		oneTimeList.add(APPLICABLE_LEVELS_FOR_PARAMETERS);//Added by A-7924 as part of ICRD-299901 
		oneTimeList.add(SERVICE_CARGO_CLASS);
		oneTimeList.add(SHIPMENT_TYPE);
		oneTimeList.add(SERVICE_TYPE);
		oneTimeList.add(UNID_PACKGING_GROUP);
		oneTimeList.add(SERVICE_TYPE_FOR_TECHNICAL_STOP);
		oneTimeList.add(AIRCRAFT_CLASSIFICATION);
		oneTimeList.add(UNID_SUB_RISK);
		String[] referenceNos=maintainEmbargoForm.getRowId();
		String referenceNo="";
		if(referenceNos!=null ){
			for(int i=0;i<=referenceNos.length-1;i++){
				if(!("".equals(referenceNos[i]))){
					int index=referenceNos[i].indexOf('-');
					if(referenceNos[i].length() > 0 && index > 0) {
						referenceNo=referenceNos[i].substring(0,index);
					}
				}
			}
			maintainEmbargoForm.setRefNumber(referenceNo.toUpperCase());
		}
		else if(maintainEmbargoForm.getRefNumber()!=null){
			referenceNo = maintainEmbargoForm.getRefNumber().toUpperCase();
		}
		else if(messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM)!=null &&
				messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM).trim().length()>0){			
			referenceNo=messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM);		
		}

		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		embargoFilterVO.setCompanyCode(companyCode);
		embargoFilterVO.setEmbargoRefNumber(referenceNo);
		if(maintainEmbargoForm.getEmbargoVersion()!=null){
			embargoFilterVO.setEmbargoVersion(Integer.parseInt(maintainEmbargoForm.getEmbargoVersion()));
		}
		try {
			 // globalParams =  embargoDelegate.findGlobalParameterCodes(companyCode);
			 embargoVo=embargoDelegate.findEmbargoDetails(embargoFilterVO);
			 hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		} catch (BusinessDelegateException ex) {
		    	handleDelegateException(ex);
				
		    	invocationContext.target = MODIFY_SCREENLOAD_FAILURE;
		}
		log.log(Log.FINE, "\n\n the embargo  reference number is ", referenceNo);
		maintainEmbargoForm.setRefNumber(referenceNo);
		boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
		if(hasBusinessPrivilege)
			{
			maintainEmbargoForm.setIsPrivilegedUser("Y");
			}
		else
			maintainEmbargoForm.setIsPrivilegedUser("N");	
		if(embargoVo!=null && embargoVo.getEmbargoReferenceNumber()!=null){
			if("E".equalsIgnoreCase(embargoVo.getRuleType())){
				Collection<OneTimeVO> applicableCodes = (Collection<OneTimeVO>)hashMap.get(APPLICABLE);
				Collection<OneTimeVO> embargoStatus = (Collection<OneTimeVO>)hashMap.get(EMBARGOSTATUS);
				Collection<OneTimeVO> levelCodes = (Collection<OneTimeVO>)hashMap.get(LEVELCODE);
				Collection<OneTimeVO>  geographicLevelType= (Collection<OneTimeVO>)hashMap.get(GEOGRAPHIC_LEVEL_TYPES);
				Collection<EmbargoParameterVO> embargoParameterVos = new ArrayList<EmbargoParameterVO>();
				Collection<OneTimeVO>  dayOfOperationApplicableOn = (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
				Collection<OneTimeVO>  viaPointTypes = (Collection<OneTimeVO>)hashMap.get(VIA_POINT);
				Collection<OneTimeVO>  categoryTypes = (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES);
				Collection<OneTimeVO>  complianceTypes = (Collection<OneTimeVO>)hashMap.get(COMPLIANCE_TYPES);
				Collection<OneTimeVO>  applicableTransactions = (Collection<OneTimeVO>)hashMap.get(APPLICABLE_TRANSACTIONS);
				Collection<OneTimeVO>  embargoParameters = (Collection<OneTimeVO>)hashMap.get(EMBARGO_PARAMETERS);
				Collection<OneTimeVO>  flightType = (Collection<OneTimeVO>)hashMap.get(FLIGHT_TYPE);
				Collection<OneTimeVO>  mailClass =  (Collection<OneTimeVO>)hashMap.get(MAL_CLASS);
				Collection<OneTimeVO>  mailCategory =  (Collection<OneTimeVO>)hashMap.get(MAL_CATEGORY);
				Collection<OneTimeVO>  mailSubClsGrp =  (Collection<OneTimeVO>)hashMap.get(MAL_SUBCLS_GRP);
				//added for ICRD-222276 by A-7815
				Collection<OneTimeVO>  adminUserlanguages =  (Collection<OneTimeVO>)hashMap.get(ADMIN_USER_LANGUAGES);
				Collection<OneTimeVO>  weightsApplicableOn=(Collection<OneTimeVO>)hashMap.get(WEIGHTTYPE_APPLICABLEON);
				Collection<OneTimeVO>  geographicLevel = new ArrayList<OneTimeVO>();
				Collection<OneTimeVO> applicableLevelsForParameters = (Collection<OneTimeVO>)hashMap.get(APPLICABLE_LEVELS_FOR_PARAMETERS);//Added by A-7924 as part of ICRD-299901
				Collection<OneTimeVO>  serviceCargoClass =  (Collection<OneTimeVO>)hashMap.get(SERVICE_CARGO_CLASS);
				Collection<OneTimeVO>  shipmentType =  (Collection<OneTimeVO>)hashMap.get(SHIPMENT_TYPE);
				Collection<OneTimeVO>  serviceType =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE);
				Collection<OneTimeVO>  unPg =  (Collection<OneTimeVO>)hashMap.get(UNID_PACKGING_GROUP);
				Collection<OneTimeVO>  serviceTypeForTechnicalStop =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE_FOR_TECHNICAL_STOP);
				Collection<OneTimeVO>  aircraftclassification =  (Collection<OneTimeVO>)hashMap.get(AIRCRAFT_CLASSIFICATION);
				Collection<OneTimeVO>  subRisk = (Collection<OneTimeVO>)hashMap.get(UNID_SUB_RISK);
				populateGeographicLevel(dayOfOperationApplicableOn, geographicLevel);
				
				maintainEmbargoSession.setGeographicLevelType(geographicLevelType);
				maintainEmbargoSession.setApplicableCode(applicableCodes);
				maintainEmbargoSession.setEmbargoStatus(embargoStatus);
				maintainEmbargoSession.setLevelCode(levelCodes);
				maintainEmbargoSession.setCategoryTypes(categoryTypes);
				maintainEmbargoSession.setComplianceTypes(complianceTypes);
				maintainEmbargoSession.setApplicableTransactions(applicableTransactions);
				maintainEmbargoSession.setEmbargoParameters(embargoParameters); 
				
				// maintainEmbargoSession.setOriginTypes(originTypes);
				maintainEmbargoSession.setGlobalParameters(globalParams);
				maintainEmbargoSession.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
				maintainEmbargoSession.setGeographicLevel(geographicLevel);
				maintainEmbargoSession.setViaPointTypes(viaPointTypes);
				maintainEmbargoSession.setWeightsApplicableOn(weightsApplicableOn);
				maintainEmbargoSession.setFlightTypes(flightType);
				maintainEmbargoSession.setMailCategory(mailCategory);
				maintainEmbargoSession.setMailClass(mailClass);
				maintainEmbargoSession.setMailSubClassGrp(mailSubClsGrp);
				maintainEmbargoSession.setAdminUserlanguages(adminUserlanguages);
				maintainEmbargoSession.setApplicableLevelsForParameters(applicableLevelsForParameters);//Added by A-7924 part of ICRD-299901
				maintainEmbargoSession.setServiceCargoClass(serviceCargoClass);
				maintainEmbargoSession.setServiceType(serviceType);
				maintainEmbargoSession.setUnPg(unPg);
				maintainEmbargoSession.setServiceTypeForTechnicalStop(serviceTypeForTechnicalStop);
				maintainEmbargoSession.setShipmentType(shipmentType);
				maintainEmbargoSession.setSubRisk(subRisk);
				if(embargoVo.getParameters()!=null  && embargoVo.getParameters().size()>0){
					log.log(Log.FINE, "%%%%%%%%%%% PARAMETERS IS NOT NULL $$$$$$$$$$$$$$");
					embargoParameterVos=embargoVo.getParameters();
					for(EmbargoParameterVO embargoParameterVO:embargoParameterVos){
						embargoParameterVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);

						if("WGT".equals(embargoParameterVO.getParameterCode())){
							applicableOnParameter.add(embargoParameterVO.getApplicableLevel());
						} else {
							applicableOn.add(embargoParameterVO.getApplicableLevel());
						}
						
						applicableOn.add(embargoParameterVO.getApplicableLevel());
						for(OneTimeVO aircraftClassification:aircraftclassification){
							if(aircraftClassification.getFieldValue().equals(embargoParameterVO.getParameterValues())){
								embargoParameterVO.setParameterDescription(aircraftClassification.getFieldDescription());
							}
						
						}
						
					}
				}
				 if(applicableOnParameter.size() > 0){
					 maintainEmbargoForm.setApplicableOnParameter(applicableOnParameter.toArray(new String[applicableOnParameter.size()]));
				 }
					
			if(applicableOn.size() >0){
				maintainEmbargoForm.setApplicableOn(applicableOn.toArray(new String[applicableOn.size()]));
			}
					
				
				
				if(embargoVo.getOriginType()==null){
					embargoVo.setOriginType(STATION);
				}
				if(embargoVo.getDestinationType()==null){
					embargoVo.setDestinationType(STATION);
				}
				embargoVo.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
				embargoVo.setSuspendedStatusChanged(embargoVo.getIsSuspended());// Added by A-8374 for ICRD-340405
				maintainEmbargoSession.setEmbargoVo(embargoVo);
				maintainEmbargoForm.setRefNumberFlag("true");
				maintainEmbargoForm.setDaysOfOperation(embargoVo.getDaysOfOperation());
				//added for ICRD-213193 by A-7815 
				Map<String, String> localLanguageEmbargo = new HashMap<String, String>();
				localLanguageEmbargo.put(LOCALE_EN_US, embargoVo.getEmbargoDescription());
				Collection<EmbargoLocalLanguageVO> localLanguageVOs = embargoVo.getLocalLanguageVOs();
				if(localLanguageVOs!=null) {				
					for (EmbargoLocalLanguageVO embargoLocalLanguageVO : localLanguageVOs) {
						localLanguageEmbargo.put(embargoLocalLanguageVO.getEmbargoLocalLanguage(), embargoLocalLanguageVO.getEmbargoDescription());
					}
				} 
				maintainEmbargoSession.setLocalLanguageEmbargo(localLanguageEmbargo);
				invocationContext.target = MODIFY_SCREENLOAD_SUCCESS;
			}
			else{
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				error = new ErrorVO("reco.defaults.complianceexists");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);				
				invocationContext.addAllError(errors);
				invocationContext.target="screenload_failure";
			}
		}
		else{
			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
			ErrorVO error = null;
		    Object[] obj={""};
			error = new ErrorVO("embargo.nilembargo",obj);// Saved Successfully
			error.setErrorDisplayType(ErrorDisplayType.WARNING);
			errors.add(error);
			
			invocationContext.addAllError(errors);
			invocationContext.target="screenload_failure";
		}
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
	 * @param geographicLevel
	 * @param dayOfOperationApplicableOn
	 */
	private void populateGeographicLevel(
			Collection<OneTimeVO> dayOfOperationApplicableOn,
			Collection<OneTimeVO> geographicLevel) {
		OneTimeVO origin = null;
		OneTimeVO destination = null;
		OneTimeVO viapoint = null;
		OneTimeVO segmentorigin = null;//Added by A-7924 as part of ICRD-299901
		OneTimeVO segmentdestination=null;//Added by A-7924 as part of ICRD-299901
		for (OneTimeVO vo : dayOfOperationApplicableOn) {
			if (APPLICABLE_ON_ORIGIN.equals(vo.getFieldValue())) {
				origin = vo;
			} else if (APPLICABLE_ON_DESTINATION.equals(
					vo.getFieldValue())) {
				destination = vo;
			} else if (APPLICABLE_ON_VIA_POINT.equals(
					vo.getFieldValue())) {
				viapoint = vo;
			//Added by A-7924 as part of ICRD-299901 starts
			}else if (APPLICABLE_ON_SEGMENTORIGIN.equals(  
				vo.getFieldValue())) {
			    segmentorigin = vo;
			} else if (APPLICABLE_ON_SEGMENTDESTINATION.equals(  
				vo.getFieldValue())) {
			   segmentdestination = vo;
			}
			//Added by A-7924 as part of ICRD-299901 ends
		}
		if (origin != null) {
			geographicLevel.add(origin);
		}
		if (destination != null) {
			geographicLevel.add(destination);
		}
		if (viapoint != null) {
			geographicLevel.add(viapoint);
		}
		//Added by A-7924 as part of ICRD-299901 starts
		if (segmentorigin != null) {
			geographicLevel.add(segmentorigin);
		}
		if (segmentdestination != null) {
			geographicLevel.add(segmentdestination);
		}
		//Added by A-7924 as part of ICRD-299901 ends
	}
}
