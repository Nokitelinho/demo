/*
 * ClearScreenCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.listembargos;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.listembargos.ListEmbargoRulesSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.ListEmbargoRulesForm;

/**
 * @author A-1747
 *
 */
public class ClearScreenCommand extends BaseCommand{

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return
	 */
	public void execute(InvocationContext invocationContext)
					throws CommandInvocationException {

		ListEmbargoRulesForm listForm
					= (ListEmbargoRulesForm)invocationContext.screenModel;

		ListEmbargoRulesSession session = getScreenSession(
				"reco.defaults", "reco.defaults.listembargo");
		Collection<OneTimeVO> levelCodes = session.getLevelCode();
		Collection<OneTimeVO> statusCodes = session.getStatus();
		Collection<EmbargoGlobalParameterVO> globalCodes = session.getGlobalParameters();
		Collection<OneTimeVO> viaPointTypes = session.getViaPointTypes();
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
		Collection<OneTimeVO>  serviceTypeForTechnicalStop =  session.getServiceType();
		Collection<OneTimeVO>  subRisk = session.getSubRisk();

		session.removeAllAttributes();
		session.removeFilterVO();
		session.removeEmabrgoDetailVOs();	
		listForm.setDestinationType("");
		listForm.setDestination("");
		listForm.setEndDate("");
		listForm.setRefNumber("");
		listForm.setStartDate("");
		listForm.setIsDisplayDetails("");
		listForm.setOriginType("");
		listForm.setOrigin("");
		//Added by A-7924 as part of ICRD-313966 starts
		listForm.setSegmentOriginType("");
		listForm.setSegmentOrigin("");
		listForm.setSegmentDestinationType("");
		listForm.setSegmentDestination("");
		//Added by A-7924 as part of ICRD-313966 ends
		listForm.setParameterCode("");
		listForm.setParameterValue("");
		listForm.setStatus("");
		listForm.setLevel("");
		listForm.setViaPoint("");
		listForm.setViaPointType("");
		listForm.setDaysOfOperation("");
		listForm.setDaysOfOperationApplicableOn("");
		listForm.setCarrierCode("");
		listForm.setFlightNumber("");
		listForm.setApplicableTransactions("");
		listForm.setComplianceType("");
		listForm.setCategory("");
		listForm.setRuleType("");
		//Added for ICRD-167922 starts
	    listForm.setCarrier("");
	    listForm.setCategory("");
	    listForm.setCarrierCode("");
	    listForm.setCommodity("");
	    listForm.setDates("");
	    listForm.setTime("");
	    listForm.setFlightOwner("");
	    listForm.setFlightType("");
	    listForm.setNatureOfGoods("");
	    listForm.setPaymentType("");
	    listForm.setProductCode("");
	    listForm.setScc("");
	    listForm.setSccGroup("");
	    listForm.setSplitIndicator("");
	    listForm.setUnNumber("");
	    listForm.setWeight("");
	    listForm.setParameterLength("");
	    listForm.setWidth("");
	    listForm.setHeight("");
	    listForm.setAwbPrefix("");
	    listForm.setMailClass("");
	    listForm.setMailSubClass("");
	    listForm.setMailCategory("");
	    listForm.setMailSubClsGrp("");
	    listForm.setAirlineGroup("");
	    listForm.setDefaultText("");
	    listForm.setServiceCargoClass("");
	    listForm.setAircraftClassification("");
	    listForm.setShipperCode("");
	    listForm.setShipperGroup("");
	    listForm.setConsigneeCode("");
	    listForm.setConsigneeGroup("");
	    listForm.setShipmentType("");
	    listForm.setConsol("");
	    //added by 202766
	    listForm.setUnknownShipper("");
	  //Added for ICRD-167922 ends
	    listForm.setServiceType("");
	    listForm.setServiceTypeForTechnicalStop("");
		session.setGlobalParameters(globalCodes);
		session.setLevelCode(levelCodes);
		session.setStatus(statusCodes);
		session.setViaPointTypes(viaPointTypes);
		session.setRuleType(ruleType);
		session.setCategoryTypes(category);
		session.setApplicableTransactions(applicableTransactions);
		session.setComplianceTypes(complianceType);
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
		session.setServiceTypeForTechnicalStop(serviceTypeForTechnicalStop);
		session.setSubRisk(subRisk);
		invocationContext.target = "clear_success";
	}
}
