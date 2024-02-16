/*
 * ScreenLoadCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;

import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.reco.defaults.EmbargoRulesDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1747
 *
 */
public class ScreenLoadCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
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
	private static final String ULD_POS = "uld.defaults.position"; //Added by A-8810 for IASCB-6097
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
	
	//Added for MAIL Embargo
	private static final String MAL_CLASS= "mailtracking.defaults.mailclass";
	private static final String MAL_CATEGORY= "mailtracking.defaults.mailcategory";
	private static final String MAL_SUBCLS_GRP= "mailtracking.defaults.mailsubclassgroup";
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
		ApplicationSessionImpl applSessionImpl = getApplicationSession();
		 EmbargoRulesDelegate embargoDelegate = new EmbargoRulesDelegate();
		  LogonAttributes logonAttributes
		    			= applSessionImpl.getLogonVO();
		 String companyCode = logonAttributes.getCompanyCode();
		 ListEmbargoRulesSession session
		    				= getScreenSession( "reco.defaults","reco.defaults.listembargo");
		  ListEmbargoRulesForm listEmbargoForm
							=(ListEmbargoRulesForm)invocationContext.screenModel;
		SharedDefaultsDelegate sharedDefaultsDelegate
										= new SharedDefaultsDelegate();
		Map hashMap = null;
		session.removeAllAttributes();

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
		oneTimeList.add(ULD_POS);
		oneTimeList.add(MAL_CATEGORY);
		oneTimeList.add(MAL_CLASS);
		oneTimeList.add(MAL_SUBCLS_GRP);
		oneTimeList.add(SERVICE_CARGO_CLASS);
		oneTimeList.add(SHIPMENT_TYPE);
		oneTimeList.add(SERVICE_TYPE);
		oneTimeList.add(UNID_PACKGING_GROUP);
		oneTimeList.add(SERVICE_TYPE_FOR_TECHNICAL_STOP);
		oneTimeList.add(UNID_SUB_RISK);
		Collection<EmbargoGlobalParameterVO> globalParams = new ArrayList<EmbargoGlobalParameterVO>();
		try {
			hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
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
		Collection<OneTimeVO>  uldPos = (Collection<OneTimeVO>)hashMap.get(ULD_POS);
		Collection<OneTimeVO>  mailClass =  (Collection<OneTimeVO>)hashMap.get(MAL_CLASS);
		Collection<OneTimeVO>  mailCategory =  (Collection<OneTimeVO>)hashMap.get(MAL_CATEGORY);
		Collection<OneTimeVO>  mailSubClsGrp =  (Collection<OneTimeVO>)hashMap.get(MAL_SUBCLS_GRP);
		Collection<OneTimeVO>  serviceCargoClass =  (Collection<OneTimeVO>)hashMap.get(SERVICE_CARGO_CLASS);
		Collection<OneTimeVO>  shipmentType =  (Collection<OneTimeVO>)hashMap.get(SHIPMENT_TYPE);
		Collection<OneTimeVO>  serviceType =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE);
		Collection<OneTimeVO>  unPg = (Collection<OneTimeVO>)hashMap.get(UNID_PACKGING_GROUP);
		Collection<OneTimeVO>  serviceTypeForTechnicalStop =  (Collection<OneTimeVO>)hashMap.get(SERVICE_TYPE_FOR_TECHNICAL_STOP);
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
		session.removeFilterVO();
		session.setViaPointTypes(viaPointTypes);
		session.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
		session.setFlightTypes(flightType);
		session.setUldPos(uldPos);
		session.setMailCategory(mailCategory);
		session.setMailClass(mailClass);
		session.setMailSubClassGrp(mailSubClsGrp);
		session.setServiceCargoClass(serviceCargoClass);
		session.setShipmentType(shipmentType);
		session.setServiceType(serviceType);
		session.setUnPg(unPg);
		session.setServiceTypeForTechnicalStop(serviceTypeForTechnicalStop);
		session.setSubRisk(subRisk);
			listEmbargoForm.setDestinationType("");
			listEmbargoForm.setDestination("");
			listEmbargoForm.setEmbargoDesc("");
			listEmbargoForm.setEndDate("");
			listEmbargoForm.setLevel("");
			listEmbargoForm.setOriginType("");
			listEmbargoForm.setOrigin("");
			//Added by A-7924 as part of ICRD-313966 starts
			listEmbargoForm.setSegmentOriginType("");
			listEmbargoForm.setSegmentOrigin("");
			listEmbargoForm.setSegmentDestinationType("");
			listEmbargoForm.setSegmentDestination("");
			//Added by A-7924 as part of ICRD-313966 ends
			listEmbargoForm.setParameterCode("");
			listEmbargoForm.setParameterValue("");
			//listEmbargoForm.setParaValue("");
			listEmbargoForm.setRefNumber("");
			listEmbargoForm.setRemarks("");
			listEmbargoForm.setStartDate("");
			listEmbargoForm.setViaPoint("");
			listEmbargoForm.setViaPointType("");
			listEmbargoForm.setDaysOfOperation("");
			listEmbargoForm.setDaysOfOperationApplicableOn("");
			listEmbargoForm.setApplicableTransactions("");
			listEmbargoForm.setCategory("");
			listEmbargoForm.setComplianceType("");
			boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
			if(hasBusinessPrivilege)
				{
				listEmbargoForm.setIsPrivilegedUser("Y");
				}
			else
				{
				listEmbargoForm.setIsPrivilegedUser("N");	
				}	
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
}
