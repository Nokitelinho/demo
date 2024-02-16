/*
 * EmbargoController.java Created on Jul 26, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;


import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.ibm.icu.text.SimpleDateFormat;
import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.admin.user.vo.UserRoleGroupDetailsVO;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.reco.defaults.builder.RecoDefaultsAuditBuilder;
import com.ibsplc.icargo.business.reco.defaults.proxy.AdminUserProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.SharedMasterGroupingProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.WorkflowDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGeographicLevelVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoJobSchedulerVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoPublishVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoSearchVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ExceptionEmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.RegulatoryMessageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.ShipmentDetailsVO;
import com.ibsplc.icargo.business.shared.area.station.InvalidStationException;
import com.ibsplc.icargo.business.shared.area.station.vo.StationVO;
import com.ibsplc.icargo.business.shared.embargo.EmbargoBusinessException;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.AssigneeFilterVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterInProcessVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.event.annotations.Raise;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 * @author A-1358
 *
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Module("reco")
@SubModule("defaults")
public class EmbargoRulesController {
	private Log log = LogFactory.getLogger("EMBARGO");
	private static final String GENERAL_GROUP_CATEGORY="GEN";
	private static final String EMBARGO_GROUP_CATEGORY="EMB";
	private static final String AIRPORT_GROUP ="ARPGRP";
	private static final String COUNTRY_GROUP ="CNTGRP";
	private static final String SCC_GROUP ="SCCGRP"; 
	private static final String RULE_TYPE = "reco.defaults.ruletype";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String MALCAT = "MALCAT";
	private static final String MALSUBCLS = "MALSUBCLS";
	private static final String SUBCLSGRP = "SUBCLSGRP";
	private static final String MALCLS = "MALCLS";
	private static final String TILDE = "~";
    private static final String COMMA = ",";
    /** The Constant ERROR. */
    private static final String ERROR = "Error";
	/** The Constant WARNING. */
    private static final String WARNING = "Warning";
	/** The Constant INFO. */
    private static final String INFO = "Info";
	/** The Constant TO_INFO. */
    private static final String TO_INFO = "I";
	/** The Constant TO_WARNING. */
    private static final String TO_WARNING = "W";
	/** The Constant TO_ERROR. */
    private static final String TO_ERROR = "E";
    private static final String CANCEL= "C";
    private static final String INACTIVE = "I";
    private static final String HYPHEN_SEPERATOR = "-";
    private static final String MALACP = "MALACP";
	private static final String MALTRA = "MALTRA";
	private static final String MALARR = "MALARR";
	private static final String EMBARGO_GROUPING_DETAILS = "EMBARGO_GROUPING_DETAILS";
	private static final String SHIPPER_GROUP = "SHPGRP";
	private static final String CONSIGNEE_GROUP = "CNSGRP";
	private static final String SHIPPER = "SHP";
	private static final String CONSIGNEE = "CNS";

//	Log localLogger=LogFactory.getLogger("SHARED_EMBARGO");
	private  Log localLogger() {
		return LogFactory.getLogger("RECO_DEFAULTS");
	}

	/**
	 * This method is used to create/modify embargo details. For create
	 * operationFlag is set as I and for modify, operationFlag is set as 'U'
	 *
	 * @param embargoVO
	 * @return
	 * @throws SystemException
	 * @throws EmbargoBusinessException
	 */
	
	
		@Advice(name = "reco.defaults.saveEmbargoDetails", phase = Phase.POST_INVOKE)
		@Raise(module="reco",submodule="defaults",event="SAVE_EMBARGO_EVENT",methodId="reco.defaults.saveEmbargoDetails")
	public String saveEmbargoDetails(EmbargoRulesVO embargoVO)
			throws SystemException, EmbargoRulesBusinessException {

		try {

			EmbargoRules embargo = null;
			boolean canSave = true;
			boolean isDuplicate = false;
			/*if(embargoVO.getOperationalFlag().equalsIgnoreCase("U")){
				isDuplicate = Embargo.checkDuplicateEmbargo(embargoVO);
			}			
			else{
				canSave = true;
				isDuplicate = false;
			}*/
			/*Collection<EmbargoGlobalParameterVO> embargoGlobalParameterVOs =
				findGlobalParameterCodes(embargoVO.getCompanyCode());
			String entity = null;

			if (!isDuplicate) {
				canSave = true;
				if(embargoVO.getParameters() !=null){
					for (EmbargoParameterVO parameterVO :
						embargoVO.getParameters()) {
						if (!parameterVO.getOperationalFlag().
								equalsIgnoreCase("D")) {
							entity = getEntityName(embargoGlobalParameterVOs,
									parameterVO.getParameterCode());
							
							if (entity != null) {
								Collection<String> collection =
									new ArrayList<String>();
								for (String value : parameterVO
										.getParameterValues().split(",")){
									collection.add(value);
								}
								
								if (EmbargoEntityFactory.validateParamterCodes(
										embargoVO.getCompanyCode(),
										entity, collection)) {
									canSave = true;
								} else {
									return null;
								}
							}
						}
					}
				}
			}	*/		 

			if (canSave) {
				if (EmbargoRulesVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(embargoVO.getOperationalFlag())) {

					/*EmbargoAuditVO embargoAuditVO = new EmbargoAuditVO(
							EmbargoAuditVO.AUDIT_MODULENAME,EmbargoAuditVO.
					    	AUDIT_SUBMODULENAME,EmbargoAuditVO.AUDIT_ENTITY);*/

					embargo = EmbargoRules.find(embargoVO.getCompanyCode(),
							embargoVO.getEmbargoReferenceNumber(),embargoVO.getEmbargoVersion());
					/*embargoAuditVO=(EmbargoAuditVO)AuditUtils.
		    		populateAuditDetails(embargoAuditVO,embargo,false);*/
					log.log(Log.INFO,"Got Entity Going to find UnitConversion");
					log.log(Log.FINE, "********embargoVO**********", embargoVO);
					//embargoVO.setEmbargoVersion(embargoVO.getEmbargoVersion()+1);
					embargo.update(embargoVO);
					//Added by A-8374 for ICRD-340405 starts
					Date dateFormat = embargo.getLastUpdatedTime().getInstance().getTime(); 
					SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
					String dateString= simpleDate.format(dateFormat);
					SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
					String timeString= simpleTime.format(dateFormat);
					embargoVO.setDateString(dateString);
					embargoVO.setTimeString(timeString);
					if((embargoVO.isSuspendedStatusChanged() && embargoVO.getIsSuspended()) || 
							 (!embargoVO.isSuspendedStatusChanged() && !embargoVO.getIsSuspended())){
						 embargoVO.setSuspendedStatusChanged(false); 
					 }
					 else{
						 embargoVO.setSuspendedStatusChanged(true);
					 }
					//Added by A-8374 for ICRD-340405 ends
					//auditEmbargo(embargoVO);
					/*embargoAuditVO=(EmbargoAuditVO)AuditUtils.
		    		populateAuditDetails(embargoAuditVO,embargo,false);
					auditEmbargo(embargo,embargoAuditVO,AuditVO.UPDATE_ACTION, embargoVO);*/
					//Added by a-5160 for ICRD-79521
					/*if(("A".equals(embargo.getStatus())|| "C".equals(embargo.getStatus()) || "S".equals(embargo.getStatus())) && "OK2F".equals(embargo.getComplianceType())){
						generateWorkflowForEmbargoCreation(embargo);
					}*/
					// Added by A-5867 for updating REC_VIEW
					/*if("A".equals(embargo.getStatus()))
						EmbargoRules.updateEmbargoView();*/
					return embargoVO.getEmbargoReferenceNumber();


				} else if (EmbargoRulesVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(embargoVO.getOperationalFlag())) {
					log.log(Log.INFO,"Inside insert in controller");
					/*Criterion criterion = KeyUtils.getCriterion(
							embargoVO.getCompanyCode(),
							"RECO_DEFAULTS_EMBARGO_VERSION");
					embargoVO.setEmbargoVersion(KeyUtils.getKey(criterion));*/
					/*EmbargoAuditVO embargoAuditVO = new EmbargoAuditVO(
							EmbargoAuditVO.AUDIT_MODULENAME,EmbargoAuditVO.
					    	AUDIT_SUBMODULENAME,EmbargoAuditVO.AUDIT_ENTITY);
					 log.log(Log.INFO, "Going To Save Embargo = ", embargoVO);*/
					 EmbargoRules emb = new EmbargoRules(embargoVO);
					 //Added by A-8374 for ICRD-340405 starts
					 Date dateFormat = emb.getLastUpdatedTime().getInstance().getTime(); 
					 SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
					 String dateString= simpleDate.format(dateFormat);
					 SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
					 String timeString= simpleTime.format(dateFormat);
					 embargoVO.setDateString(dateString);
					 embargoVO.setTimeString(timeString);
					//Added by A-8374 for ICRD-340405 ends
					/*embargoAuditVO=(EmbargoAuditVO)AuditUtils.
		    		populateAuditDetails(embargoAuditVO,emb,true);
					auditEmbargo(emb,embargoAuditVO,AuditVO.CREATE_ACTION, embargoVO);*/
					
					//generateWorkflowForEmbargoCreation(emb);
					//auditEmbargo(embargoVO);
					/*if("A".equals(emb.getStatus()))
						EmbargoRules.updateEmbargoView();*/
					log.log(Log.INFO,"Before return in CONTROLLERd");
					return emb.getEmbargoRulesPk().getEmbargoReferenceNumber();


				}
			} else {
				localLogger().log(Log.INFO, "THROW FROM CONTROLLER");
				throw new EmbargoRulesBusinessException(
						"reco.defaults.DuplicateEmbargo");
			}

		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (FinderException e) {

			throw new SystemException(e.getErrorCode(), e);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (BusinessException e) {
//printStackTraccee()();
			throw new EmbargoRulesBusinessException(e);

		}
		return null;
	}

	/**
	 * This method is used to cancel an embargo.
	 *
	 * @param embargoDetailsVO
	 * @throws SystemException
	 * @throws EmbargoBusinessException
	 */
	
	@Advice(name = "reco.defaults.statusUpdate", phase = Phase.POST_INVOKE)
	@Raise(module="reco",submodule="defaults",event="UPDATE_EMBARGO_EVENT",methodId="reco.defaults.updateEmbargoDetails")
	public void cancelEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
			throws SystemException, EmbargoRulesBusinessException {
		try {
				for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
			EmbargoRules embargo = new EmbargoRules();
			embargo = EmbargoRules.find(embargoDetailsVO.getCompanyCode(),
					embargoDetailsVO.getEmbargoReferenceNumber(),embargoDetailsVO.getEmbargoVersion());
			EmbargoPublishVO embargoOldDataVO = populateEmbargoPublishVO(embargo);
			embargoDetailsVO.setEmbargoPublishVO(embargoOldDataVO);
			embargo.setStatus("C");
			 log.log(Log.FINE, "THE LAST UPDATE TIME ", embargoDetailsVO.getLastUpdateTime());
			log.log(Log.FINE, "THE LAST UPDATE TIME 1  ", embargo.getLastUpdatedTime());
			embargo.setLastUpdatedTime(embargoDetailsVO.getLastUpdateTime());
			Date dateFormat = embargoDetailsVO.getLastUpdateTime().getInstance().getTime(); 
			SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MMM-yyyy");
			String dateString= simpleDate.format(dateFormat);
			SimpleDateFormat simpleTime = new SimpleDateFormat("HH:mm");
			String timeString= simpleTime.format(dateFormat); 
			embargoDetailsVO.setDateString(dateString);
			embargoDetailsVO.setTimeString(timeString);
			//Added by a-5160 for ICRD-79521
			/*if(("A".equals(embargo.getStatus())|| "C".equals(embargo.getStatus()) || "S".equals(embargo.getStatus())) && "OK2F".equals(embargo.getComplianceType())){
				generateWorkflowForEmbargoCreation(embargo);
			}*/
			 log.log(Log.FINE, "THE LAST UPDATE TIME 2", embargo.getLastUpdatedTime());
				}									
			} catch (FinderException e) {

			throw new SystemException(e.getMessage(), e);
		}

	}
	/**
	 * 
	 * @param embargo
	 * @return EmbargoPublishVO
	 */
	private EmbargoPublishVO populateEmbargoPublishVO(EmbargoRules embargo) {

		EmbargoPublishVO embargoOldVO = new EmbargoPublishVO();
		embargoOldVO.setEmbargoVersion(embargo.getEmbargoRulesPk().getEmbargoVersion());
		embargoOldVO.setCompanyCode(embargo.getEmbargoRulesPk().getCompanyCode());
		
		embargoOldVO.setEmbargoDescription(embargo.getEmbargoDescription());
		embargoOldVO.setCategory(embargo.getCategory());
		embargoOldVO.setComplianceType(embargo.getComplianceType());
		embargoOldVO.setApplicableTransactions(embargo.getApplicableTransactions());
		if(ERROR.equals(embargo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_ERROR);
			}
		else if(WARNING.equals(embargo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_WARNING);
			}
		else if(INFO.equals(embargo.getEmbargoLevel()))
			{
			embargoOldVO.setEmbargoLevel(TO_INFO);
			}
		else
			{
			embargoOldVO.setEmbargoLevel(embargo.getEmbargoLevel());
			}
		embargoOldVO.setEmbargoReferenceNumber(embargo.getEmbargoRulesPk().getEmbargoReferenceNumber());
		LocalDate endDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoOldVO.setEndDate(endDate.setDate(TimeConvertor.toStringFormat(embargo.getEndDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		if("Y".equals(embargo.getIsSuspended())){
			embargoOldVO.setIsSuspended(true);
		}
		Collection<EmbargoParameterVO> parameters = new ArrayList<EmbargoParameterVO>();
		EmbargoParameterVO embargoParameterVO = null;
		EmbargoGeographicLevelVO embargoGeographicLevelVO = null;
		Collection<EmbargoGeographicLevelVO> geographicLevels = new ArrayList<EmbargoGeographicLevelVO>();
		if(embargo.getParameters() != null && embargo.getParameters().size()>0){
		for(EmbargoRulesParameter embargoRulesParameter : embargo.getParameters()){
			if("O".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterType())){
				embargoOldVO.setOrigin(embargoRulesParameter.getParameterValues());
				embargoOldVO.setOriginType(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());	
			}
			else if("D".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterType())){
				embargoOldVO.setDestination(embargoRulesParameter.getParameterValues());
				embargoOldVO.setDestinationType(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());	
			}
			else if ("V".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterType())){
				embargoOldVO.setViaPoint(embargoRulesParameter.getParameterValues());
				embargoOldVO.setViaPointType(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());
			}
			else if ("P".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterType()) && "DOW".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterCode())){
				embargoOldVO.setDaysOfOperation(embargoRulesParameter.getParameterValues());
				embargoOldVO.setDaysOfOperationApplicableOn(embargoRulesParameter.getApplicableLevel());
			}
			else{
				embargoParameterVO = new EmbargoParameterVO();
			embargoParameterVO.setCompanyCode(embargoRulesParameter.getEmbargoParameterPk().getCompanyCode());
			embargoParameterVO
			.setEmbargoReferenceNumber(embargoRulesParameter.getEmbargoParameterPk().getEmbargoReferenceNumber());
			embargoParameterVO.setParameterLevel(embargoRulesParameter.getEmbargoParameterPk().getParameterType());
			embargoParameterVO.setEmbargoVersion(embargoRulesParameter.getEmbargoParameterPk().getEmbargoVersion());		
				embargoParameterVO.setApplicable(embargoRulesParameter.getEmbargoParameterPk().getApplicableOn());	
				embargoParameterVO.setApplicableLevel(embargoRulesParameter.getApplicableLevel());
				embargoParameterVO.setParameterCode(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());
				embargoParameterVO.setParameterValues(embargoRulesParameter.getParameterValues());	
			if("FLTNUM".equals(embargoParameterVO.getParameterCode()) && embargoParameterVO.getParameterValues()!=null) {
				String[] values =  embargoParameterVO.getParameterValues().split("~");
				if(values.length>1) {
					embargoParameterVO.setCarrierCode(values[0]);
					embargoParameterVO.setFlightNumber(values[1]);
					StringBuffer paramValue = new StringBuffer(embargoParameterVO.getCarrierCode())
					.append("~").append(embargoParameterVO.getFlightNumber());
					embargoParameterVO.setParameterValues(paramValue.toString());
				}
			}
			parameters.add(embargoParameterVO);  
		}
		if(!"P".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterType()) ){
				embargoGeographicLevelVO = new EmbargoGeographicLevelVO();
				embargoGeographicLevelVO.setCompanyCode(embargoRulesParameter.getEmbargoParameterPk().getCompanyCode());
				embargoGeographicLevelVO
				.setEmbargoReferenceNumber(embargoRulesParameter.getEmbargoParameterPk().getCompanyCode());
				embargoGeographicLevelVO.setEmbargoVersion(embargoRulesParameter.getEmbargoParameterPk().getEmbargoVersion());
				embargoGeographicLevelVO.setGeographicLevel(embargoRulesParameter.getEmbargoParameterPk().getParameterType());
				embargoGeographicLevelVO.setGeographicLevelType(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());
				embargoGeographicLevelVO.setGeographicLevelApplicableOn(embargoRulesParameter.getEmbargoParameterPk().getApplicableOn());
				embargoGeographicLevelVO.setGeographicLevelValues(embargoRulesParameter.getParameterValues());
				geographicLevels.add(embargoGeographicLevelVO);
			}
		}
		}
		
		embargoOldVO.setGeographicLevels(geographicLevels);
		embargoOldVO.setParameters(parameters);
		embargoOldVO.setRemarks(embargo.getRemarks());
		LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		embargoOldVO.setStartDate(fromDate.setDate(TimeConvertor.toStringFormat(embargo.getStartDate(),TimeConvertor.CALENDAR_DATE_FORMAT)));
		if(CANCEL.equals(embargo.getStatus()))
			{
			embargoOldVO.setStatus(INACTIVE);
			}
		else
			{
			embargoOldVO.setStatus(embargo.getStatus());
			}
		
		return embargoOldVO;
	
		
	}

	/**
	 * Used to list the details of a selected embargo
	 *
	 * @param companyCode
	 * @param embargoReferenceNumber
	 * @return EmbargoVO
	 * @throws SystemException
	 */
	public EmbargoRulesVO findEmbargoDetails(EmbargoFilterVO embargoFilterVO) {
		try {
			return EmbargoRules.findEmbargoDetails(embargoFilterVO);
		} catch (PersistenceException e) {
			LogFactory.getLogger("SHARED").log(Log.SEVERE,e.toString() );

//printStackTraccee()();
		} catch (SystemException e) {
			LogFactory.getLogger("SHARED").log(Log.SEVERE,e.toString() );
			// To be reviewed Auto-generated catch block

		}
		return null;
	}

	/**
	 * This method finds embargos which meet the filter
	 *
	 * @param filterVO
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public Page findEmbargos(EmbargoFilterVO filterVO, int pageNumber)
			throws SystemException {
		try {
			return EmbargoRules.findEmbargos(filterVO, pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);

		}

	}
	
	/**
	 * This method identifies all embargos associated with the given shipment
	 *
	 * @param shipmentVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws SystemException
	 * @throws ProxyException
	 * @throws ServiceNotAccessibleException
	 * @throws RemoteException
	 * @throws InvalidStationException
	 * @throws SystemException
	 * @throws EmbargoBusinessException
	 */
	public Collection<EmbargoDetailsVO> checkForEmbargo(
			Collection<ShipmentDetailsVO> shipmentVOs) throws SystemException,
			EmbargoRulesBusinessException {
		Collection<EmbargoDetailsVO> detailsVOs = new ArrayList<EmbargoDetailsVO>();
		//Return if the shipmentvo or its parameters are not speciofied
		if(shipmentVOs == null || shipmentVOs.size() == 0 
			) {
			return null;
		}

		else{	
			LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			Collection<GeneralMasterGroupVO> generalMasterGroups = null;
		
			//Group the shipment based on the common properties		
			//boolean to be introduced to identify whther grouping needed/not
			Collection<ShipmentDetailsVO> groupedShipmentVOS = groupShipmentVos(shipmentVOs);
			
			if(groupedShipmentVOS != null ){
				for(ShipmentDetailsVO shipmentVO :groupedShipmentVOS){
		try {
			
			Collection<String> stationCodes = new HashSet<>();
					if(shipmentVO.getDstStation() != null && shipmentVO.getDstStation().trim().length() >0 ) {
				stationCodes.add(shipmentVO.getDstStation());
							//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
							generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 								
									AIRPORT_GROUP,shipmentVO.getDstStation());
								shipmentVO.setDstArpGrp(getCommaSeparatedGroupNames(generalMasterGroups));
							if (shipmentVO.getDstArpGrp() != null) {								
								shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),shipmentVO.getDstStation(),ShipmentDetailsVO.AIRPORT_GRP, shipmentVO.getDstArpGrp()));
								//Added for ICRD-331282 by Prashant Behera starts
								if(shipmentVO.isEnhancedChecks()){
									Map<String,String> groupingDetails=new HashMap<String,String>();
									groupingDetails.put(shipmentVO.getDstStation(), shipmentVO.getDstArpGrp());
									if(ContextUtils.getTxBusinessParameter(EMBARGO_GROUPING_DETAILS)==null){
										ContextUtils.storeTxBusinessParameter(EMBARGO_GROUPING_DETAILS, (Serializable) groupingDetails);
										}else{
											Map<String,String> grpMap=(Map<String, String>) ContextUtils.getTxBusinessParameter(EMBARGO_GROUPING_DETAILS);
											grpMap.putAll(groupingDetails);
											ContextUtils.storeTxBusinessParameter(EMBARGO_GROUPING_DETAILS, (Serializable) grpMap);
										}								}
								//Added for ICRD-331282 by Prashant Behera ends
							}
					}
					//Added for IASCB-23507 Embargo find the shipper group and consignee group start
					Collection<String> shipperCodes = shipmentVO.getMap().get(SHIPPER);
					if(shipperCodes != null && shipperCodes.size() > 0 ){
									Collection<String> shipperGroups = new ArrayList<>();
									for(String shipperCode : shipperCodes){
											generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 								
											SHIPPER_GROUP,shipperCode);
											if(generalMasterGroups != null && generalMasterGroups.size() > 0){
												shipperGroups.add(((GeneralMasterGroupVO)generalMasterGroups.toArray()[0]).getGroupName());
										}
									}
									if(shipperGroups.size() > 0) {
										shipmentVO.getMap().put(SHIPPER_GROUP,shipperGroups);
										}
							}
					Collection<String> consigneeCodes = shipmentVO.getMap().get(CONSIGNEE);
					if(consigneeCodes != null && consigneeCodes.size() > 0 ){
									Collection<String> consigneeGroups = new ArrayList<>();
									for(String consigneeCode : consigneeCodes){{
										generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 								
											CONSIGNEE_GROUP,consigneeCode);
										if(generalMasterGroups != null && generalMasterGroups.size() > 0){
											consigneeGroups.add(((GeneralMasterGroupVO)generalMasterGroups.toArray()[0]).getGroupName());
										}
									}
									if(consigneeGroups.size() > 0){
										shipmentVO.getMap().put(CONSIGNEE_GROUP,consigneeGroups);
									}
							}
					}
					//Added for IASCB-23507 Embargo find the shipper group and consignee group end
					
					if(shipmentVO.getOrgStation()!= null && shipmentVO.getOrgStation().trim().length() >0 ){
				stationCodes.add(shipmentVO.getOrgStation());
							//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
							generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
								AIRPORT_GROUP,shipmentVO.getOrgStation());
							shipmentVO.setOrgArpGrp(getCommaSeparatedGroupNames(generalMasterGroups));							
							if (shipmentVO.getOrgArpGrp() != null) {
							shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),shipmentVO.getOrgStation(),ShipmentDetailsVO.AIRPORT_GRP, shipmentVO.getOrgArpGrp()));
							//Added for ICRD-331282 by Prashant Behera starts
							if(shipmentVO.isEnhancedChecks()){
								Map<String,String> groupingDetails=(Map<String, String>) ContextUtils.getTxBusinessParameter(EMBARGO_GROUPING_DETAILS);
								if(groupingDetails!=null){
									groupingDetails.put(shipmentVO.getOrgStation(), shipmentVO.getOrgArpGrp());
								}else{
								groupingDetails=new HashMap<String,String>();
								groupingDetails.put(shipmentVO.getOrgStation(), shipmentVO.getOrgArpGrp());
								}
								ContextUtils.storeTxBusinessParameter(EMBARGO_GROUPING_DETAILS, (Serializable) groupingDetails);
							}
							//Added for ICRD-331282 by Prashant Behera ends
						}
					}
				//added as part of implementing viapoint check in embargo starts
				if(shipmentVO.getViaPointStation() != null && shipmentVO.getViaPointStation().size() > 0){
					stationCodes.addAll(shipmentVO.getViaPointStation());
						Collection <String> viaPntArpGrps = new ArrayList<String>();
							String commaSeparatedGroupNames = null;
						for(String viapoint : shipmentVO.getViaPointStation()){
								//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
								generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
									AIRPORT_GROUP,viapoint);
								commaSeparatedGroupNames = getCommaSeparatedGroupNames(generalMasterGroups);
								if(commaSeparatedGroupNames != null){
									viaPntArpGrps.add(commaSeparatedGroupNames);
									shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),viapoint,ShipmentDetailsVO.AIRPORT_GRP, commaSeparatedGroupNames));
									if(shipmentVO.isEnhancedChecks()){
										Map<String,String> groupingDetails=(Map<String, String>) ContextUtils.getTxBusinessParameter(EMBARGO_GROUPING_DETAILS);
										if(groupingDetails!=null){
											groupingDetails.put(viapoint, commaSeparatedGroupNames);
										}else{
										groupingDetails=new HashMap<String,String>();
										groupingDetails.put(viapoint, commaSeparatedGroupNames);
										}
										ContextUtils.storeTxBusinessParameter(EMBARGO_GROUPING_DETAILS, (Serializable) groupingDetails);
									}
								}
							}
							shipmentVO.setViaPntArpGrp((viaPntArpGrps.size() > 0)?viaPntArpGrps:null);
					}
					Map<String, StationVO> stationValidation = null;
				//added as part of implementing viapoint check in embargo ends
					if(stationCodes != null && stationCodes.size() > 0){
						stationValidation = Proxy.getInstance().get(SharedAreaProxy.class).validateStationCodes(shipmentVO
								.getCompanyCode(), stationCodes);
					}
				

			if(stationValidation != null) {
				StationVO destVo = stationValidation.get(shipmentVO.getDstStation());
				StationVO orgVo = stationValidation.get(shipmentVO.getOrgStation());
				//added as part of implementing viapoint check in embargo starts
				Collection<StationVO> viaPointVOs = new ArrayList<StationVO>();
				StationVO viaPointVo = null;
				if(shipmentVO.getViaPointStation() != null){
					for(String viaPointStnVo : shipmentVO.getViaPointStation()){						
						viaPointVo =stationValidation.get(viaPointStnVo);
						viaPointVOs.add(viaPointVo);
					}
				}
				//added as part of implementing viapoint check in embargo ends
						
				if(destVo != null){
					shipmentVO.setDstCountry(destVo.getCountryCode());
					shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),destVo.getStationCode(),ShipmentDetailsVO.COUNTRY, shipmentVO.getDstCountry()));
						if(destVo.getCountryCode() != null && destVo.getCountryCode().trim().length()>0){
									//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
									generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
									COUNTRY_GROUP,destVo.getCountryCode());
										shipmentVO.setDstCntGrp(getCommaSeparatedGroupNames(generalMasterGroups));
									if (shipmentVO.getDstCntGrp() != null) {
										shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),destVo.getStationCode(),ShipmentDetailsVO.COUNTRY_GRP, shipmentVO.getDstCntGrp()));
							}
						}
				}
				if(orgVo != null){
					shipmentVO.setOrgCountry(orgVo.getCountryCode());
					shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),orgVo.getStationCode(),ShipmentDetailsVO.COUNTRY, shipmentVO.getOrgCountry()));
						if(orgVo.getCountryCode() != null && orgVo.getCountryCode().trim().length()>0){
									//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
									generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
									COUNTRY_GROUP,orgVo.getCountryCode());
										shipmentVO.setOrgCntGrp(getCommaSeparatedGroupNames(generalMasterGroups));
									if (shipmentVO.getOrgCntGrp() != null) {
										shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),orgVo.getStationCode(),ShipmentDetailsVO.COUNTRY_GRP, shipmentVO.getOrgCntGrp()));
							}
						
						}
				}
				//added as part of implementing viapoint check in embargo starts
				if(viaPointVOs != null && viaPointVOs.size() > 0){
					Collection<String> viaPointCountryVos = new ArrayList<String>();
						Collection<String> viaPointCntGrpVos = new ArrayList<String>();
								String commaSeparatedGroupNames = null;
					for(StationVO viaPointCountryVo : viaPointVOs ){
						viaPointCountryVos.add(viaPointCountryVo.getCountryCode());
						shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),viaPointCountryVo.getStationCode(),ShipmentDetailsVO.COUNTRY, viaPointCountryVo.getCountryCode()));
							if(viaPointCountryVo.getCountryCode() != null && viaPointCountryVo.getCountryCode().trim().length()>0){
										//Modified for BUG_ICRD-284545_AiynaSuresh_21Sep2018
										generalMasterGroups = getGeneralMasterGroups(shipmentVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
										COUNTRY_GROUP,viaPointCountryVo.getCountryCode());
										commaSeparatedGroupNames = getCommaSeparatedGroupNames(generalMasterGroups);
										if(commaSeparatedGroupNames != null){
											viaPointCntGrpVos.add(commaSeparatedGroupNames);
											shipmentVO.setTypeMap(populateTypeMap(shipmentVO.getTypeMap(),viaPointCountryVo.getStationCode(),ShipmentDetailsVO.COUNTRY_GRP, commaSeparatedGroupNames));
										}
											
							}
						}	
						shipmentVO.setViaPntCntGrp(viaPointCntGrpVos);
					shipmentVO.setViaPointCountry(viaPointCountryVos);
				}
				//added as part of implementing viapoint check in embargo ends
			}
			// added for ICRD-213193 by A-7815
			shipmentVO.setUserLocale(shipmentVO.isUserLocaleNeeded()?(logonAttributes.getLocale()!=null?logonAttributes.getLocale().toString():null):null);

		} catch (ProxyException e) {
			throw new EmbargoRulesBusinessException(e);
		}

		}
				//for populating dummy and 'all and any' parameters
				populateDummyAndAllParameters(groupedShipmentVOS);
		/*
		 * Identify embargos
		 */










						//	if ("Y".equals(parameterVO.getAplFlg())) {
						//} else if ("N".equals(parameterVO.getAplFlg())) {
		return EmbargoRules.checkForEmbargo(groupedShipmentVOS);
		}
			else{
				return null;
			}
		}
	}
	/**
	 * Added for BUG_ICRD-284545_AiynaSuresh_21Sep2018
	 * @param generalMasterGroupVOs
	 * @return
	 */
	public String getCommaSeparatedGroupNames(Collection<GeneralMasterGroupVO> generalMasterGroupVOs){
		StringBuilder commaSeparatedGroups = null;
		if (generalMasterGroupVOs != null && generalMasterGroupVOs.size() > 0) {			
			for(GeneralMasterGroupVO vo:generalMasterGroupVOs){
				commaSeparatedGroups = (commaSeparatedGroups == null)?new StringBuilder(vo.getGroupName()):
					commaSeparatedGroups.append(",").append(vo.getGroupName());
			}
		}		
		log.log(Log.FINE, "commaSeparatedGroups -- >"+commaSeparatedGroups);
		return commaSeparatedGroups == null?null:commaSeparatedGroups.toString();
	}
	
	/**
	 * 
	 * @param map 
	 * @param code
	 * @param type
	 * @param typeValue
	 * @return
	 * Method to Set the Map used for storing a code plus its possible types Eg: OriginCode "-" It's Airport group, Country, CountryGroup
	 * Eg: key:TRV-ARPGRP value:INDIAGRP, key:TRV-CNT value:IN, key:TRV-CNTGRP value:ASGRP
	 */
	private Map<String, String> populateTypeMap(Map<String, String> typeMap, String code,
			String type, String typeValue) {
		if(typeMap ==null){
			typeMap = new HashMap<String, String>();
		}		
		StringBuilder codeBuilder = new StringBuilder().append(code).append(HYPHEN_SEPERATOR).append(type);
		typeMap.put(codeBuilder.toString(), typeValue);
		return typeMap;
	}
	/**
	 * @author A-4823
	 * @param shipmentVOs
	 * @return 	
	 * Mail bags with common characteristics need to be goruped before firing the embargo query for better performance
	 * charactreistics for mail taken into consdertaion : mailClass, subclass, subclassgroup, category, origin and destination	 
	 */
	private Collection<ShipmentDetailsVO> groupShipmentVos(
			Collection<ShipmentDetailsVO> shipmentVOs) {
		String mailClass ="";
		String mailCategory ="";
		String mailSubCls ="";
		String subClsGrp ="";
		String orgStation = "";
		String dstStation = "";
		Map <String,ShipmentDetailsVO> filterGroupedMap = null;
		Collection<ShipmentDetailsVO> shipmentDetailsVO =
				new ArrayList<ShipmentDetailsVO>();
		//modified by A-5642 for ICRD-69895
		ShipmentDetailsVO shipmentVODummy = shipmentVOs.iterator().next();
		//modified by A-5642 for ICRD-69895
		if (MALACP.equals(shipmentVODummy.getApplicableTransaction())||MALTRA.equals(shipmentVODummy.getApplicableTransaction())||MALARR.equals(shipmentVODummy.getApplicableTransaction())) {
			for (ShipmentDetailsVO shipmentVO : shipmentVOs) {
				if (shipmentVO.getMap() != null ) {
				for (String parameterCode : shipmentVO.getMap().keySet()) {
					for (String parameterValue : shipmentVO.getMap().get(parameterCode)) {
							if (MALCLS.equals(parameterCode)) {
							mailClass = parameterValue;
						}
							if (MALCAT.equals(parameterCode)) {
							mailCategory= parameterValue;
						}
							if (MALSUBCLS.equals(parameterCode)) {
							mailSubCls= parameterValue;
						}
							if (SUBCLSGRP.equals(parameterCode)) {
							subClsGrp= parameterValue;
						}
					}
				}
			}
				if (shipmentVO.getOrgStation() != null) {
					orgStation = shipmentVO.getOrgStation();
				}
				if (shipmentVO.getDstStation() != null) {
					dstStation = shipmentVO.getDstStation();
				}
				StringBuilder key = new StringBuilder(orgStation).append(
						dstStation).append(mailClass).append(
								mailCategory).append(mailSubCls).append(subClsGrp);
				if(filterGroupedMap == null){
				filterGroupedMap = new HashMap<String,ShipmentDetailsVO> ();
				filterGroupedMap.put(key.toString(), shipmentVO);
				} else {
				StringBuilder shipmentIds = new StringBuilder();
				ShipmentDetailsVO detailsVO = filterGroupedMap.get(key.toString());
					if (detailsVO!= null) {
						shipmentIds.append(
								detailsVO.getShipmentID()).append(",").append((
										shipmentVO.getShipmentID()));
					detailsVO.setShipmentID(shipmentIds.toString());
					} else {
					filterGroupedMap.put(key.toString(), shipmentVO);
					}
				}
			}
		}
		if (filterGroupedMap != null) {
			shipmentDetailsVO.addAll(
					filterGroupedMap.values());
			return shipmentDetailsVO;
		} else {
			return shipmentVOs;
		}
	}
	
	private  String getEntityName(
			Collection<EmbargoGlobalParameterVO> collection,
			String parameterCode) {

		for (EmbargoGlobalParameterVO parameterVO : collection) {

			if (parameterVO.getParameterCode().equals(parameterCode)) {

				return parameterVO.getEntityReference();
			}

		}

		return null;
	}
	
	/**
	 * Alert embargo expiry.
	 *
	 * @param embargoJobSchedulerVO the embargo job scheduler vo
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @author A-5160
	 */
	public void alertEmbargoExpiry(EmbargoJobSchedulerVO embargoJobSchedulerVO)throws SystemException,RemoteException{
		Map<String, String> systemParameters = getSystemParameters();
		if (systemParameters != null) {
			if ("Y".equals(systemParameters.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_REQUIRED)) && systemParameters.get(EmbargoDetailsVO.NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY) != null) {				
				Collection<EmbargoDetailsVO> embargoCandidatesForExpiry = null;
				EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO(); 
				embargoFilterVO.setNumberOfDaysPriorEmbargoExpiry(systemParameters.get(EmbargoDetailsVO.NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY));
				embargoCandidatesForExpiry = new EmbargoRules().findEmbargoCandidatesForExpiry(embargoFilterVO);
				if (embargoCandidatesForExpiry != null && !embargoCandidatesForExpiry.isEmpty()) {
					generateWorkFlowForEmbargoForExpiry(embargoCandidatesForExpiry);      
				}
			}
		}
	}
	
	/**
	 * Generate work flow for embargo for expiry.
	 *
	 * @param embargoCandidatesForExpiry the embargo candidates for expiry
	 * @throws SystemException the system exception
	 * @author A-5160
	 */
	private void generateWorkFlowForEmbargoForExpiry(Collection<EmbargoDetailsVO> embargoCandidatesForExpiry) throws SystemException{
		
		
		Collection<EmbargoDetailsVO> embargoVOs = new ArrayList<EmbargoDetailsVO>();
		Collection<EmbargoDetailsVO> complianceVOs = new ArrayList<EmbargoDetailsVO>();
		for (EmbargoDetailsVO embargoDetails : embargoCandidatesForExpiry) { 
			if("E".equals(embargoDetails.getRuleType())){
				embargoVOs.add(embargoDetails);
				}
				else{
				complianceVOs.add(embargoDetails);
			}
		}
		if(embargoVOs!=null && embargoVOs.size()>0){
			Collection<WorkflowVO> workflowVos = new ArrayList<WorkflowVO>();
			Map<String, Collection<EmbargoDetailsVO>> airportMap = new HashMap<String, Collection<EmbargoDetailsVO>>();
			Collection<EmbargoDetailsVO> embargoDetailsVO = null;
			for (EmbargoDetailsVO embargoDetails : embargoVOs) { 
				if(embargoDetails.getAirportCode()!=null){
					if(airportMap.containsKey(embargoDetails.getAirportCode())){
						embargoDetailsVO = airportMap.get(embargoDetails.getAirportCode());
					embargoDetailsVO.add(embargoDetails);
					airportMap.put(embargoDetails.getAirportCode(), embargoDetailsVO);
				}
					else{
						embargoDetailsVO = new ArrayList<EmbargoDetailsVO>();
						embargoDetailsVO.add(embargoDetails);
						airportMap.put(embargoDetails.getAirportCode(), embargoDetailsVO);
					}
			}
		}
		Object[] keys = airportMap.keySet().toArray();
        Arrays.sort(keys);
        Collection<EmbargoDetailsVO> embargos = null;
        
        for(Object key : keys){
        	embargos = airportMap.get(key);
        	
        	WorkflowVO workflowVO = new WorkflowVO();
        	Map<String, String> systemParameters = getSystemParameters();
        	LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
        	workflowVO.setCompanyCode(logonAttributes.getCompanyCode());
        	workflowVO.setStationCode(key.toString());

			if (systemParameters != null) {

				if (systemParameters
						.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW) != null) {
					workflowVO.setWorkflowName(systemParameters
							.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW));
					
					workflowVO.setMessageType(systemParameters
							.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW));
					
				}
			}
			Collection<AssigneeFilterVO> assigneeFilterVOs = new ArrayList<AssigneeFilterVO>();
			AssigneeFilterVO assigneeFilterVO = new AssigneeFilterVO();
			assigneeFilterVO.setAirportCode(key.toString());
			assigneeFilterVOs.add(assigneeFilterVO);
			workflowVO.setAssigneeFilterVOs(assigneeFilterVOs);
			workflowVO.setParametersInProcess(new ArrayList<ParameterInProcessVO>());
			StringBuilder builder = new StringBuilder() ;
			ParameterInProcessVO param = new ParameterInProcessVO();  
			param = new ParameterInProcessVO(); 
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_ARLCOD);
			param.setParamaterValue(logonAttributes.getCompanyCode());
			workflowVO.getParametersInProcess().add(param);
			param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RECRULTYP);
				param.setParamaterValue("Embargo");
			workflowVO.getParametersInProcess().add(param);
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY);
				param.setParamaterValue(systemParameters.get(EmbargoDetailsVO.NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY));
				workflowVO.getParametersInProcess().add(param);
			builder.append('\n'); 
			for(EmbargoDetailsVO embargo : embargos){
				
				builder.append(embargo.getEmbargoReferenceNumber())
						.append(" ")
						.append(embargo.getEndDate().toDisplayDateOnlyFormat())
						.append(" ")
						.append(embargo.getEmbargoDescription());
				if(embargo.getRemarks()!=null){
					builder.append(", ");
					builder.append(embargo.getRemarks());
				}
				if(embargos.size()>1){
					builder.append('\n');
				}
				
				workflowVO.setLastUpdateUser(embargo.getLastUpdatedUser());
			}
			
			
			param = new ParameterInProcessVO();
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_EMBARGO);
			param.setParamaterValue(builder.toString());
			workflowVO.getParametersInProcess().add(param);
			workflowVos.add(workflowVO);
        }
		try {  
			if(workflowVos!=null && !workflowVos.isEmpty()){
				new WorkflowDefaultsProxy().generateNotification(workflowVos);
			}
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
		}	
		if(complianceVOs!=null && complianceVOs.size()>0){
			Collection<WorkflowVO> workflowVos = new ArrayList<WorkflowVO>();
			Map<String, Collection<EmbargoDetailsVO>> airportMap = new HashMap<String, Collection<EmbargoDetailsVO>>();
			Collection<EmbargoDetailsVO> embargoDetailsVO = null;
			for (EmbargoDetailsVO embargoDetails : complianceVOs) { 
				if(embargoDetails.getAirportCode()!=null){
					if(airportMap.containsKey(embargoDetails.getAirportCode())){
						embargoDetailsVO = airportMap.get(embargoDetails.getAirportCode());
						embargoDetailsVO.add(embargoDetails);
						airportMap.put(embargoDetails.getAirportCode(), embargoDetailsVO);
					}
					else{
						embargoDetailsVO = new ArrayList<EmbargoDetailsVO>();
						embargoDetailsVO.add(embargoDetails);
						airportMap.put(embargoDetails.getAirportCode(), embargoDetailsVO);
					}
				}
			}
			Object[] keys = airportMap.keySet().toArray();
	        Arrays.sort(keys);
	        Collection<EmbargoDetailsVO> embargos = null;
	        
	        for(Object key : keys){
	        	embargos = airportMap.get(key);
	        	
	        	WorkflowVO workflowVO = new WorkflowVO();
	        	Map<String, String> systemParameters = getSystemParameters();
	        	LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
	        	workflowVO.setCompanyCode(logonAttributes.getCompanyCode());
	        	workflowVO.setStationCode(key.toString());
	
				if (systemParameters != null) {
	
					if (systemParameters
							.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW) != null) {
						workflowVO.setWorkflowName(systemParameters
								.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW));
						
						workflowVO.setMessageType(systemParameters
								.get(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW));
						
					}
				}
				Collection<AssigneeFilterVO> assigneeFilterVOs = new ArrayList<AssigneeFilterVO>();
				AssigneeFilterVO assigneeFilterVO = new AssigneeFilterVO();
				assigneeFilterVO.setAirportCode(key.toString());
				assigneeFilterVOs.add(assigneeFilterVO);
				workflowVO.setAssigneeFilterVOs(assigneeFilterVOs);
				workflowVO.setParametersInProcess(new ArrayList<ParameterInProcessVO>());
				StringBuilder builder = new StringBuilder() ;
				ParameterInProcessVO param = new ParameterInProcessVO();  
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_ARLCOD);
				param.setParamaterValue(logonAttributes.getCompanyCode());
				workflowVO.getParametersInProcess().add(param);
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RECRULTYP);
				param.setParamaterValue("Compliance Info");
				workflowVO.getParametersInProcess().add(param);
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY);
				param.setParamaterValue(systemParameters.get(EmbargoDetailsVO.NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY));
				workflowVO.getParametersInProcess().add(param);
				builder.append('\n'); 
				for(EmbargoDetailsVO embargo : embargos){
					
					builder.append(embargo.getEmbargoReferenceNumber())
							.append(" ")
							.append(embargo.getEndDate().toDisplayDateOnlyFormat())
							.append(" ")
							.append(embargo.getEmbargoDescription());
					if(embargo.getRemarks()!=null){
						builder.append(", ");
						builder.append(embargo.getRemarks());
					}
					if(embargos.size()>1){
						builder.append('\n');
					}
					
					workflowVO.setLastUpdateUser(embargo.getLastUpdatedUser());
				}
				
				
				param = new ParameterInProcessVO();
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_EMBARGO);
				param.setParamaterValue(builder.toString());
				workflowVO.getParametersInProcess().add(param);
				workflowVos.add(workflowVO);
	        }
			try {  
				if(workflowVos!=null && !workflowVos.isEmpty()){
					new WorkflowDefaultsProxy().generateNotification(workflowVos);
				}
			} catch (ProxyException e) {
				throw new SystemException(e.getMessage(), e);
			}
		}
		log.exiting("EmbargoController", "generateWorkFlowForEmbargoForExpiry");
		
		
	}
	
	/**
	 * Gets the system parameters.
	 *
	 * @return the system parameters
	 * @throws SystemException the system exception
	 * @author A-5160
	 */
	private Map<String, String> getSystemParameters() throws SystemException {

		Collection<String> systemParameterCodes = new ArrayList<String>();
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		Map<String, String> sysParameters = null;
		
		systemParameterCodes
				.add(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_WORKFLOW);
		systemParameterCodes.add(EmbargoDetailsVO.EMBARGOEXPIRY_ALERT_REQUIRED);
		systemParameterCodes.add(EmbargoDetailsVO.NUMBEROFDAYS_PRIOR_EMBARGOEXPIRY);
		systemParameterCodes.add(EmbargoDetailsVO.EMBARGOCREATION_ALERT_WORKFLOW);
		
		try {
			sysParameters = sharedDefaultsProxy.findSystemParameterByCodes(systemParameterCodes);
		} catch (ProxyException proxyException) {
			return null;
		}
		return sysParameters;

	}
	
	@Advice(name = "reco.defaults.approveEmbargo", phase = Phase.POST_INVOKE)
	public void approveEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws SystemException, EmbargoRulesBusinessException, EmbargoBusinessException, RemoteException {
		try {
				for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
			EmbargoRules embargo = new EmbargoRules();
			embargo = EmbargoRules.find(embargoDetailsVO.getCompanyCode(),
					embargoDetailsVO.getEmbargoReferenceNumber(),embargoDetailsVO.getEmbargoVersion());
					embargo.setStatus(embargoDetailsVO.getStatus());
					//Added by a-5160 for ICRD-79521
					/*if(("A".equals(embargo.getStatus())|| "C".equals(embargo.getStatus()) || "S".equals(embargo.getStatus())) && "OK2F".equals(embargo.getComplianceType())){
						generateWorkflowForEmbargoCreation(embargo);
					}*/
			//embargo.setLastUpdatedTime(embargoRulesVO.getLastUpdateTime());
			//Added by A-7941 as part of ICRD-335522
			if(!embargoDetailsVO.isApproveFlag()){
				EmbargoRulesVO embargoRulesVO = new EmbargoRulesVO();
				EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
				embargoFilterVO.setCompanyCode(embargoDetailsVO.getCompanyCode());
				embargoFilterVO.setEmbargoRefNumber(embargoDetailsVO.getEmbargoReferenceNumber());
				embargoFilterVO.setEmbargoVersion(embargoDetailsVO.getEmbargoVersion());
				embargoRulesVO=findEmbargoDetails(embargoFilterVO);
				embargoRulesVO.setStatus(embargoDetailsVO.getStatus());
				RecoDefaultsAuditBuilder recoDefaultsAuditBuilder = new RecoDefaultsAuditBuilder();
				recoDefaultsAuditBuilder.auditForEmbargoDetails(embargoRulesVO);
			}
			 log.log(Log.FINE, "THE LAST UPDATE TIME 2", embargo.getLastUpdatedTime());
			// Added by A-5867 for updating REC_VIEW
				/* if("A".equals(embargo.getStatus()))
					EmbargoRules.updateEmbargoView();	*/
				}
			
			} catch (FinderException e) {
		
			throw new SystemException(e.getMessage(), e);
		}

	}
	
	@Advice(name = "reco.defaults.rejectEmbargo", phase = Phase.POST_INVOKE)
	public void rejectEmbargo(Collection<EmbargoDetailsVO> embargoDetailsVOs)
	throws SystemException, EmbargoRulesBusinessException {
		try {
				for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
			EmbargoRules embargo = new EmbargoRules();
			embargo = EmbargoRules.find(embargoDetailsVO.getCompanyCode(),
					embargoDetailsVO.getEmbargoReferenceNumber(),embargoDetailsVO.getEmbargoVersion());
			embargo.setStatus("R");
			//embargo.setLastUpdatedTime(embargoRulesVO.getLastUpdateTime());
			 log.log(Log.FINE, "THE LAST UPDATE TIME 2", embargo.getLastUpdatedTime());
				}
			} catch (FinderException e) {
		
			throw new SystemException(e.getMessage(), e);
		}

	}

	
	/** Added by A-5867 for ICRD-68630 starts**/
	/**
	 * Search Embargos.
	 * @author A-5867
	 * @param filterVO the filter vo
	 * @return the EmbargoSearchVO
	 * @throws SystemException the system exception
	 */
	public EmbargoSearchVO  searchEmbargos(EmbargoFilterVO filterVO)throws SystemException {
		log.entering("EmbargoRulesController", "searchEmbargos");
		setGroupingDetails(filterVO);
		try {
			return EmbargoRules.searchEmbargos(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}		
	}

	private void setGroupingDetails(EmbargoFilterVO filterVO)
			throws SystemException {
		GeneralMasterGroupVO generalMasterGroup=null;
		if(null !=filterVO.getAirportCodeOrigin() && filterVO.getAirportCodeOrigin().length()>0){			
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					AIRPORT_GROUP, filterVO.getAirportCodeOrigin());
			if (generalMasterGroup != null) {
				filterVO.setAirportGroupOrigin(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getAirportCodeDestination() && filterVO.getAirportCodeDestination().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					AIRPORT_GROUP, filterVO.getAirportCodeDestination());
			if (generalMasterGroup != null) {
				filterVO.setAirportGroupDestination(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getAirportCodeViaPoint() && filterVO.getAirportCodeViaPoint().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					AIRPORT_GROUP, filterVO.getAirportCodeViaPoint());
			if (generalMasterGroup != null) {
				filterVO.setAirportGroupViaPoint(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getCountryCodeOrigin() && filterVO.getCountryCodeOrigin().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					COUNTRY_GROUP, filterVO.getCountryCodeOrigin());
			if (generalMasterGroup != null) {
				filterVO.setCountryGroupOrigin(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getCountryCodeDestination() && filterVO.getCountryCodeDestination().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					COUNTRY_GROUP, filterVO.getCountryCodeDestination());
			if (generalMasterGroup != null) {
				filterVO.setCountryGroupDestination(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getCountryCodeViaPoint() && filterVO.getCountryCodeViaPoint().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					COUNTRY_GROUP, filterVO.getCountryCodeViaPoint());
			if (generalMasterGroup != null) {
				filterVO.setCountryGroupViaPoint(generalMasterGroup.getGroupName());
			}
		}
		if(null !=filterVO.getScc() && filterVO.getScc().length()>0){
			generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
					SCC_GROUP, filterVO.getScc());
			if (generalMasterGroup != null) {
				filterVO.setSccGroup(generalMasterGroup.getGroupName());
			}
		}
	}
	/**
	 * find All Regulatory Messages.
	 * @author A-5867
	 *
	 * @param regulatoryMessageFilterVO the regulatory Message Filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public  List<RegulatoryMessageVO> findAllRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException {
		log.entering("EmbargoRulesController", "findAllRegulatoryMessages");
		List<RegulatoryMessageVO> regulatoryMessages=RegulatoryComposeMessage.findAllRegulatoryMessages(regulatoryMessageFilter);
		log.exiting("EmbargoRulesController", "findAllRegulatoryMessages");
		return regulatoryMessages;
	}
	
	
	/**
	 * find Regulatory Messages.
	 * @author A-5867
	 *
	 * @param regulatoryMessageFilterVO the regulatory Message Filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 */
	public Page<RegulatoryMessageVO> findRegulatoryMessages(RegulatoryMessageFilterVO regulatoryMessageFilter)
			throws SystemException {
		log.entering("EmbargoRulesController", "findRegulatoryMessages");
		Page<RegulatoryMessageVO> regulatoryMessageVOs=RegulatoryComposeMessage.findRegulatoryMessages(regulatoryMessageFilter);
		log.exiting("EmbargoRulesController", "findRegulatoryMessages");
		return regulatoryMessageVOs;
	}
	
	

	/**
	 * save Regulatory Messages
	 * @author A-5867
	 *
	 * @param regulatoryMessageVOs the regulatory Message VOs 
	 * @throws SystemException the system exception
	 * @throws CreateException the Create Exception
	 * @throws FinderException the Finder Exception
	 * @throws RemoveException the Remove Exception
	 */
	public void saveRegulatoryMessages(Collection<RegulatoryMessageVO> regulatoryMessageVOs)
			throws SystemException{
		log.entering("EmbargoRulesController", "saveRegulatoryMessages");
		RegulatoryComposeMessage composeMessage = null;
		try{
			for (RegulatoryMessageVO regulatoryMessageVO : regulatoryMessageVOs) {
				if (EmbargoRulesVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(regulatoryMessageVO.getOperationFlag())) {
					composeMessage = new RegulatoryComposeMessage(regulatoryMessageVO);
				} else if (EmbargoRulesVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(regulatoryMessageVO.getOperationFlag())) {
					composeMessage =RegulatoryComposeMessage.find(
							regulatoryMessageVO.getCompanyCode(),regulatoryMessageVO.getSerialNumber());
					composeMessage.remove();
				} else if (EmbargoRulesVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(regulatoryMessageVO.getOperationFlag())) {
					composeMessage =RegulatoryComposeMessage.find(
							regulatoryMessageVO.getCompanyCode(),regulatoryMessageVO.getSerialNumber());
					composeMessage.update(regulatoryMessageVO);
				}
			}
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(), e);
		}		
		log.exiting("EmbargoRulesController", "saveRegulatoryMessages");
	}
	
	/**
	 * @author A-5867
	 *
	 * @param companyCode the companyCode
	 * @param groupCategory the groupCategory  
	 * @param groupType the groupType 
	 * @param groupEntity the groupEntity 
	 * @return GeneralMasterGroupVO
	 * @throws SystemException the System Exception
	 */
	private GeneralMasterGroupVO getGeneralMasterGroup(String companyCode,String groupCategory,String groupType,String groupEntity)
	throws SystemException{
		SharedMasterGroupingProxy sharedMasterGroupingProxy= new SharedMasterGroupingProxy();
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO= new GeneralMasterGroupFilterVO();
		Collection<GeneralMasterGroupVO> generalMasterGroupVOList=null;
		GeneralMasterGroupVO generalMasterGroup=null;
		generalMasterGroupFilterVO.setCompanyCode(companyCode);
		generalMasterGroupFilterVO.setGroupCategory(groupCategory);
		generalMasterGroupFilterVO.setGroupType(groupType);
		generalMasterGroupFilterVO.setGroupEntity(groupEntity);
		try {
			generalMasterGroupVOList= sharedMasterGroupingProxy.findGroupNamesofGroupEntity(generalMasterGroupFilterVO);			
			if(null !=generalMasterGroupVOList && generalMasterGroupVOList.size()>0){
				for(GeneralMasterGroupVO generalMaster:generalMasterGroupVOList){
					generalMasterGroup=generalMaster;
				}
			}
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"\n\n  +++++++++++++++EXCEPTION++++++++++++++++++++++++++++");
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		return generalMasterGroup;
	}
/**
	 * Added for BUG_ICRD-284545_AiynaSuresh_21Sep2018
	 * @param companyCode
	 * @param groupCategory
	 * @param groupType
	 * @param groupEntity
	 * @return
	 * @throws SystemException
	 */
	private Collection<GeneralMasterGroupVO> getGeneralMasterGroups(String companyCode,
			String groupCategory,String groupType,String groupEntity)
	throws SystemException{
		
		GeneralMasterGroupFilterVO generalMasterGroupFilterVO = new GeneralMasterGroupFilterVO();
		Collection<GeneralMasterGroupVO> generalMasterGroupVOList = null;
		generalMasterGroupFilterVO.setCompanyCode(companyCode);
		generalMasterGroupFilterVO.setGroupCategory(groupCategory);
		generalMasterGroupFilterVO.setGroupType(groupType);
		generalMasterGroupFilterVO.setGroupEntity(groupEntity);
		try {
			generalMasterGroupVOList = Proxy.getInstance().get(SharedMasterGroupingProxy.class).
					findGroupNamesofGroupEntity(generalMasterGroupFilterVO);
			log.log(Log.FINE, "generalMasterGroupVOList --> "+generalMasterGroupVOList);
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"\n\n  +++++++++++++++EXCEPTION++++++++++++++++++++++++++++");
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		return generalMasterGroupVOList;
	}
	
	/**
	 * @author A-5867
	 * @param roleGroupCodes
	 * @param companyCode
	 * @return Collection<UserRoleGroupDetailsVO>
	 * @throws SystemException
	 */
	public Collection<UserRoleGroupDetailsVO> validateRoleGroup(Collection<String> roleGroupCodes, String companyCode)
			throws SystemException {
		log.entering("EmbargoRulesController", " validateRoleGroup ");
		Collection<UserRoleGroupDetailsVO> roleGroups =null;
		AdminUserProxy adminUserProxy= new AdminUserProxy();
		try {
			roleGroups= adminUserProxy.validateRoleGroup(roleGroupCodes, companyCode);		
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"\n\n  +++++++++++++++EXCEPTION++++++++++++++++++++++++++++");
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		log.log(Log.INFO, "ValideUsers = ", roleGroups);
		return roleGroups;
	}
	
	/**
	 * 
	 * @author a-5160
	 * @param filterVO the filter vo
	 * @return the embargo search vo
	 * @throws SystemException the system exception
	 */
	public Collection<EmbargoDetailsVO>  findDuplicateEmbargos(EmbargoFilterVO filterVO)throws SystemException {
		log.entering("EmbargoRulesController", "findDuplicateEmbargos");
		GeneralMasterGroupVO generalMasterGroup=null;
		if(null !=filterVO.getAirportCodeOrigin() && filterVO.getAirportCodeOrigin().length()>0){
			for(String airport:filterVO.getAirportCodeOrigin().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupOrigin(null !=filterVO.getAirportGroupOrigin()? 
								filterVO.getAirportGroupOrigin()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getAirportCodeOriginExc() && filterVO.getAirportCodeOriginExc().length()>0){
			for(String airport:filterVO.getAirportCodeOriginExc().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupOriginExc(null !=filterVO.getAirportGroupOriginExc()? 
								filterVO.getAirportGroupOriginExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getAirportCodeDestination() && filterVO.getAirportCodeDestination().length()>0){
			for(String airport:filterVO.getAirportCodeDestination().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupDestination(null !=filterVO.getAirportGroupDestination()? 
								filterVO.getAirportGroupDestination()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getAirportCodeDestinationExc() && filterVO.getAirportCodeDestinationExc().length()>0){
			for(String airport:filterVO.getAirportCodeDestinationExc().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupDestinationExc(null !=filterVO.getAirportGroupDestinationExc()? 
								filterVO.getAirportGroupDestinationExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getAirportCodeViaPoint() && filterVO.getAirportCodeViaPoint().length()>0){
			for(String airport:filterVO.getAirportCodeViaPoint().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupViaPoint(null !=filterVO.getAirportGroupViaPoint()? 
								filterVO.getAirportGroupViaPoint()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getAirportCodeViaPointExc() && filterVO.getAirportCodeViaPointExc().length()>0){
			for(String airport:filterVO.getAirportCodeViaPointExc().split(",")){
				if(null !=airport && airport.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							AIRPORT_GROUP,airport);
					if (generalMasterGroup != null) {
						filterVO.setAirportGroupViaPointExc(null !=filterVO.getAirportGroupViaPointExc()? 
								filterVO.getAirportGroupViaPointExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}			
		}
		if(null !=filterVO.getCountryCodeOrigin() && filterVO.getCountryCodeOrigin().length()>0){
			for(String country:filterVO.getCountryCodeOrigin().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupOrigin(null !=filterVO.getCountryGroupOrigin()? 
								filterVO.getCountryGroupOrigin()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getCountryCodeOriginExc() && filterVO.getCountryCodeOriginExc().length()>0){
			for(String country:filterVO.getCountryCodeOriginExc().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupOriginExc(null !=filterVO.getCountryGroupOriginExc()? 
								filterVO.getCountryGroupOriginExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getCountryCodeDestination() && filterVO.getCountryCodeDestination().length()>0){
			for(String country:filterVO.getCountryCodeDestination().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupDestination(null !=filterVO.getCountryGroupDestination()? 
								filterVO.getCountryGroupDestination()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getCountryCodeDestinationExc() && filterVO.getCountryCodeDestinationExc().length()>0){
			for(String country:filterVO.getCountryCodeDestinationExc().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupDestinationExc(null !=filterVO.getCountryGroupDestinationExc()? 
								filterVO.getCountryGroupDestinationExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getCountryCodeViaPoint() && filterVO.getCountryCodeViaPoint().length()>0){
			for(String country:filterVO.getCountryCodeViaPoint().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupViaPoint(null !=filterVO.getCountryGroupViaPoint()? 
								filterVO.getCountryGroupViaPoint()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getCountryCodeViaPointExc() && filterVO.getCountryCodeViaPointExc().length()>0){
			for(String country:filterVO.getCountryCodeViaPointExc().split(",")){
				if(null !=country && country.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							COUNTRY_GROUP, country);
					if (generalMasterGroup != null) {
						filterVO.setCountryGroupViaPointExc(null !=filterVO.getCountryGroupViaPointExc()? 
								filterVO.getCountryGroupViaPointExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getScc() && filterVO.getScc().length()>0){
			for(String scc:filterVO.getScc().split(",")){
				if(null !=scc && scc.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							SCC_GROUP,scc);
					if (generalMasterGroup != null) {
						filterVO.setSccGroup(null !=filterVO.getSccGroup()? 
								filterVO.getSccGroup()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		if(null !=filterVO.getSccExc() && filterVO.getSccExc().length()>0){
			for(String scc:filterVO.getSccExc().split(",")){
				if(null !=scc && scc.length()>0){
					generalMasterGroup=getGeneralMasterGroup(filterVO.getCompanyCode(),EMBARGO_GROUP_CATEGORY, 
							SCC_GROUP,scc);
					if (generalMasterGroup != null) {
						filterVO.setSccGroupExc(null !=filterVO.getSccGroupExc()? 
								filterVO.getSccGroupExc()+","+generalMasterGroup.getGroupName():generalMasterGroup.getGroupName());
					}
				}
			}
		}
		try {
			return EmbargoRules.findDuplicateEmbargos(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);
		}			
	}
	
	/**
	 * Generate workflow for embargo creation.
	 * @author a-5160
	 * @param embargoRules the embargo rules
	 * @throws SystemException the system exception
	 */
	/*private void generateWorkflowForEmbargoCreation(EmbargoRules embargoRules) throws SystemException{
		Collection<WorkflowVO> workflowVos = new ArrayList<WorkflowVO>();
		WorkflowVO workflowVO = new WorkflowVO();
		Map<String, String> systemParameters = getSystemParameters();
    	LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
    	workflowVO.setCompanyCode(logonAttributes.getCompanyCode());
    	workflowVO.setStationCode(embargoRules.getAirportCode());

		if (systemParameters != null) {

			if (systemParameters
					.get(EmbargoDetailsVO.EMBARGOCREATION_ALERT_WORKFLOW) != null) {
				workflowVO.setWorkflowName(systemParameters
						.get(EmbargoDetailsVO.EMBARGOCREATION_ALERT_WORKFLOW));
				
				workflowVO.setMessageType(systemParameters
						.get(EmbargoDetailsVO.EMBARGOCREATION_ALERT_WORKFLOW));
				
			}
		}
		 Collection<String> onetimecode = new ArrayList<String>();
		onetimecode.add(RULE_TYPE);
		onetimecode.add(EMBARGOSTATUS);
		Map<String, Collection<OneTimeVO>> onetimevalues = null;
		try {
			onetimevalues = new SharedDefaultsProxy().findOneTimeValues(embargoRules
					.getEmbargoRulesPk().getCompanyCode(), onetimecode);
		} catch (ProxyException proxyException) {
			
		}		
		String ruleType = embargoRules.getRuleType();
		if (onetimevalues != null) {
			Collection<OneTimeVO> ruleTypes = onetimevalues.get(RULE_TYPE);
			if (ruleTypes != null && ruleTypes.size() > 0) {
				for (OneTimeVO onetimevo : ruleTypes) {
					
					if (ruleType != null && ruleType.trim().length() > 0) {
						if (ruleType.equalsIgnoreCase(onetimevo.getFieldValue())) {
							ruleType = onetimevo.getFieldDescription();												
						}
					}
				}
			}
		}			
		String status = embargoRules.getStatus();
		if (onetimevalues != null) {
			Collection<OneTimeVO> statusTypes = onetimevalues.get(EMBARGOSTATUS);
			if (statusTypes != null && statusTypes.size() > 0) {
				for (OneTimeVO onetimevo : statusTypes) {					
					if (status != null && status.trim().length() > 0) {
						if (status.equalsIgnoreCase(onetimevo.getFieldValue())) {
							status = onetimevo.getFieldDescription();												
						}
					}
				}
			}
		}		
		Collection<AssigneeFilterVO> assigneeFilterVOs = new ArrayList<AssigneeFilterVO>();
		AssigneeFilterVO assigneeFilterVO = new AssigneeFilterVO();
		assigneeFilterVO.setAirportCode(embargoRules.getAirportCode());
		assigneeFilterVOs.add(assigneeFilterVO);
		workflowVO.setAssigneeFilterVOs(assigneeFilterVOs);
		workflowVO.setParametersInProcess(new ArrayList<ParameterInProcessVO>());
		ParameterInProcessVO param = new ParameterInProcessVO();  
		param = new ParameterInProcessVO(); 
		param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_ARLCOD);
		param.setParamaterValue(logonAttributes.getCompanyCode());
		workflowVO.getParametersInProcess().add(param);
		param = new ParameterInProcessVO(); 
		param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_RECRULTYP);
		param.setParamaterValue(ruleType.toUpperCase());
		workflowVO.getParametersInProcess().add(param);
		param = new ParameterInProcessVO(); 
		param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM);
		param.setParamaterValue(embargoRules.getEmbargoRulesPk().getEmbargoReferenceNumber());
		workflowVO.getParametersInProcess().add(param);
		param = new ParameterInProcessVO(); 
		param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_EMBARGO);
		param.setParamaterValue(embargoRules.getEmbargoDescription());
		workflowVO.getParametersInProcess().add(param);
		param = new ParameterInProcessVO(); 
		param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STACOD);
		param.setParamaterValue(status);
		workflowVO.getParametersInProcess().add(param);
		//Added by a-5160 for ICRD-79521 starts
		if(("A".equals(embargoRules.getStatus())|| "C".equals(embargoRules.getStatus()) || "S".equals(embargoRules.getStatus())) && "OK2F".equals(embargoRules.getComplianceType())){
			param = new ParameterInProcessVO(); 
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REMARKS);
			param.setParamaterValue("MODIFIED EMBARGO");
			workflowVO.getParametersInProcess().add(param);
		}
		else{
			param = new ParameterInProcessVO(); 
			param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_REMARKS);
			param.setParamaterValue("CREATED");
			workflowVO.getParametersInProcess().add(param);
		}
		//Added by a-5160 for ICRD-79521 ends
		workflowVO.setLastUpdateUser(embargoRules.getLastUpdatedUser());
		workflowVos.add(workflowVO);
    
		try {  
			if(workflowVos!=null && !workflowVos.isEmpty()){
				new WorkflowDefaultsProxy().generateNotification(workflowVos);
			}
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}*/
	
	/**
     * This method finds embargos/regulatory compliance which meet the filter 
     * @param filterVO
     * @param pageNumber To be reviewed
     * @return Collection
     * @throws SystemException
     * Page<EmbargoDetailsVO>
     */
	public Page<EmbargoDetailsVO> findRegulatoryComplianceRules(EmbargoFilterVO filterVO, int pageNumber)
			throws SystemException {
		// Added by A-5867 for ICRD-86701
		setGroupingDetails(filterVO);
		try {
			return EmbargoRules.findRegulatoryComplianceRules(filterVO, pageNumber);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);

		}

	}
	
	/**
	 * 
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
	throws SystemException, RemoteException
	  {    
		String productCode = null;
		 try {
			productCode = (new ProductDefaultsProxy()).validateProduct(companyCode, productName, startDate, endDate);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
		 return productCode;		
	}
	
	/**
	 * @author A-5153
	 * @param
	 * @return
	 * @throws SystemException
	 */
	public void updateEmbargoView()throws SystemException {
		log.entering("EmbargoRulesController", "updateEmbargoView");
		EmbargoRules.updateEmbargoView();
	}
	/** Added by A-6843 for ICRD-69906 starts **/
	/**
	 * Find exception embargo details.
	 * @author A-6843
	 * @param exceptionEmbargoFilterVO the exception embargo filter vo
	 * @return the page
	 * @throws SystemException the system exception
	 * @throws PersistenceException 
	 */
	public Page<ExceptionEmbargoDetailsVO> findExceptionEmbargoDetails(ExceptionEmbargoFilterVO exceptionEmbargoFilterVO)
	throws SystemException{
		log.entering("EmbargoRulesController", "findExceptionEmbargoDetails");
		Page<ExceptionEmbargoDetailsVO> exceptionEmbargoDetailsVO=EmbargoExceptionRules.findExceptionEmbargoDetails(exceptionEmbargoFilterVO);
		log.exiting("EmbargoRulesController", "findExceptionEmbargoDetails");
		return exceptionEmbargoDetailsVO;

	}
	/**
	 * 
	 * @param exceptionEmbargoVOs
	 * @throws RemoteException
	 * @throws SystemException
	 */
	public void saveExceptionEmbargoDetails(Collection<ExceptionEmbargoDetailsVO> exceptionEmbargoVOs)
	throws RemoteException,SystemException{
		log.entering("EmbargoRulesController", "saveExceptionEmbargoDetails");
		
		EmbargoExceptionRules exceptionEmbargo = null;
		try{
			for (ExceptionEmbargoDetailsVO exceptionEmbargoVO : exceptionEmbargoVOs) {
				if (ExceptionEmbargoDetailsVO.OPERATION_FLAG_INSERT.equalsIgnoreCase(exceptionEmbargoVO.getOperationFlag())) {
					exceptionEmbargo = new EmbargoExceptionRules(exceptionEmbargoVO);
				} else if (ExceptionEmbargoDetailsVO.OPERATION_FLAG_DELETE.equalsIgnoreCase(exceptionEmbargoVO.getOperationFlag())) {
					exceptionEmbargo =EmbargoExceptionRules.find(
							exceptionEmbargoVO.getCompanyCode(),exceptionEmbargoVO.getShipmentPrefix(),exceptionEmbargoVO.getMasterDocumentNumber(),exceptionEmbargoVO.getSerialNumbers());
					exceptionEmbargo.remove();
				} else if (ExceptionEmbargoDetailsVO.OPERATION_FLAG_UPDATE.equalsIgnoreCase(exceptionEmbargoVO.getOperationFlag())) {
					exceptionEmbargo =EmbargoExceptionRules.find(
							exceptionEmbargoVO.getCompanyCode(),exceptionEmbargoVO.getShipmentPrefix(),exceptionEmbargoVO.getMasterDocumentNumber(),exceptionEmbargoVO.getSerialNumbers());
					exceptionEmbargo.update(exceptionEmbargoVO);
				}
			}
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		} catch (RemoveException e) {
			throw new SystemException(e.getErrorCode(), e);
		}		
		log.exiting("EmbargoRulesController", "saveExceptionEmbargoDetails");
	
	}
	/** Added by A-6843 for ICRD-69906 ends **/
	/**
	 * @author A-5642
	 * @param shipmentDetailsVOs
	 * @return 
	 */
	private void populateDummyAndAllParameters(
			Collection<ShipmentDetailsVO> shipmentDetailsVOs) {
		for (ShipmentDetailsVO shipmentDetailsVO : shipmentDetailsVOs) {
			if (shipmentDetailsVO.getMap() != null ) {
				Map<String, Collection<String>> shipmentDetailsMap =
						shipmentDetailsVO.getMap();
				// populating all parameters
				populateAllAndAnyParameters(shipmentDetailsMap);
				Set<String> parameterSet = shipmentDetailsVO.getMap().keySet();
				//populating embargo parameters
				
				String[] embargoParameters = {ShipmentDetailsVO.MAIL_CLASS,ShipmentDetailsVO.MAIL_CATEGORY,
						ShipmentDetailsVO.MAIL_SUB_CLS,ShipmentDetailsVO.MAIL_SUB_CLS_GRP,
						ShipmentDetailsVO.CARRIER_ORG,ShipmentDetailsVO.CARRIER_DST,
						ShipmentDetailsVO.CARRIER_VIA,ShipmentDetailsVO.CARRIER_ALL,
						ShipmentDetailsVO.SHARED_COMMODITY,ShipmentDetailsVO.SHARED_SCC,ShipmentDetailsVO.SHARED_PAYTYP,
						ShipmentDetailsVO.FLTNUM_ORG,ShipmentDetailsVO.FLTNUM_DST,
						ShipmentDetailsVO.FLTNUM_VIA,ShipmentDetailsVO.FLTNUM_ALL,
						ShipmentDetailsVO.PRODUCT,ShipmentDetailsVO.GOODS,ShipmentDetailsVO.AWB_PREFIX,
						ShipmentDetailsVO.FLTOWR_ORG,ShipmentDetailsVO.FLTOWR_DST,
						ShipmentDetailsVO.FLTOWR_VIA,ShipmentDetailsVO.FLTOWR_ALL,
						ShipmentDetailsVO.FLTTYPE_ORG,ShipmentDetailsVO.FLTTYPE_DST,
						ShipmentDetailsVO.FLTTYPE_VIA,ShipmentDetailsVO.FLTTYPE_ALL,ShipmentDetailsVO.SCC_GROUP,
						ShipmentDetailsVO.AIRLINE_GRP_ORG,ShipmentDetailsVO.AIRLINE_GRP_DST,
						ShipmentDetailsVO.AIRLINE_GRP_VIA,ShipmentDetailsVO.AIRLINE_GRP_ALL,
						ShipmentDetailsVO.UN_NUMBER,ShipmentDetailsVO.HEIGHT,ShipmentDetailsVO.LENGTH,ShipmentDetailsVO.WIDTH,
						ShipmentDetailsVO.WEIGHT,ShipmentDetailsVO.PER_PIECE_WEIGHT,
						ShipmentDetailsVO.DV_CUSTOMS,ShipmentDetailsVO.DV_CARRIAGE
						,ShipmentDetailsVO.PKGINS,ShipmentDetailsVO.AGENT,ShipmentDetailsVO.AGENTGRP,
						ShipmentDetailsVO.VOLUME,ShipmentDetailsVO.ULD,ShipmentDetailsVO.AIRCRAFT_TYPE_ORIGIN,
						ShipmentDetailsVO.AIRCRAFT_TYPE_DESTINATION,ShipmentDetailsVO.AIRCRAFT_TYPE_VIA_POINT,
						ShipmentDetailsVO.AIRCRAFT_TYPE_ALL,ShipmentDetailsVO.AIRCRAFT_TYPE_GROUP_ORIGIN,ShipmentDetailsVO.AIRCRAFT_TYPE_GROUP_DESTINATION,
						ShipmentDetailsVO.AIRCRAFT_TYPE_GROUP_VIA_POINT,ShipmentDetailsVO.AIRCRAFT_TYPE_GROUP_ALL,
						ShipmentDetailsVO.UNIDs 
						,ShipmentDetailsVO.PKGINS,ShipmentDetailsVO.AGENT,ShipmentDetailsVO.AGENTGRP,
						ShipmentDetailsVO.ULD_POSITION,ShipmentDetailsVO.ULD_TYPE,ShipmentDetailsVO.SERVICE_CARGO_CLASS
						,ShipmentDetailsVO.AIRCRAFT_CLASS_ORIGIN, ShipmentDetailsVO.AIRCRAFT_CLASS_DESTINATION,
						ShipmentDetailsVO.AIRCRAFT_CLASS_VIA_POINT, ShipmentDetailsVO.AIRCRAFT_CLASS_ALL, 
						ShipmentDetailsVO.SHIPPER, ShipmentDetailsVO.SHIPPER_GROUP,
						ShipmentDetailsVO.CONSIGNEE, ShipmentDetailsVO.CONSIGNEE_GROUP, ShipmentDetailsVO.SHIPMENT_TYPE, 
						ShipmentDetailsVO.CONSOL,
						ShipmentDetailsVO.SERVICE_TYPE,
						
						ShipmentDetailsVO.SERVICE_TYPE_FOR_TECHNICAL_STOP, ShipmentDetailsVO.UNID_PACKGING_GROUP, ShipmentDetailsVO.UNID_SUB_RISK, 
					
						ShipmentDetailsVO.UNKNOWN_SHIPPER}; 
				for (String parameterCode : embargoParameters) {
					//if not contains populate dummy value
					if (!(parameterSet.contains(parameterCode))) {
						shipmentDetailsMap.put(
								parameterCode,
								Arrays.asList(TILDE));
					}
				}
				// Added by A-5290 for ICRD-186572
				if(shipmentDetailsVO.getViaPointStation() == null || shipmentDetailsVO.getViaPointStation().isEmpty()) {
					shipmentDetailsVO.setViaPointStation(Arrays.asList(TILDE));
					shipmentDetailsVO.setViaPointCountry(Arrays.asList(TILDE));
					shipmentDetailsVO.setViaPntArpGrp(Arrays.asList(TILDE));
					shipmentDetailsVO.setViaPntCntGrp(Arrays.asList(TILDE));
				}
			}
			if(shipmentDetailsVO.getSplitIndicator() == null || shipmentDetailsVO.getSplitIndicator().trim().length() <=0 ){
				shipmentDetailsVO.setSplitIndicator(TILDE);
			}
			//added by 202766
			if(shipmentDetailsVO.getUnknownShipper() == null || shipmentDetailsVO.getUnknownShipper().isEmpty() ){
				shipmentDetailsVO.setUnknownShipper(TILDE);
			}
			// Added by A-5290 for ICRD-186572
			if(shipmentDetailsVO.getViaPointStation() == null || shipmentDetailsVO.getViaPointStation().isEmpty()) {
				shipmentDetailsVO.setViaPointStation(Arrays.asList(TILDE));
				shipmentDetailsVO.setViaPointCountry(Arrays.asList(TILDE));
				shipmentDetailsVO.setViaPntArpGrp(Arrays.asList(TILDE));
				shipmentDetailsVO.setViaPntCntGrp(Arrays.asList(TILDE));
			}
		}
	}
	/**
	 *This method checks any embargo exists in system
	 * @param embargoFilterVO
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean checkAnyEmbargoExists(EmbargoFilterVO embargoFilterVO)
			throws SystemException {
		log.entering("EmbargoRulesController", "checkAnyEmbargoExists");
		return EmbargoRules
				.checkAnyEmbargoExists(embargoFilterVO);
	}
	
	/**
	 * This method returns exception awbs
	 * @param exceptionEmbargoFilterVOs
	 * @return Collection<String>
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	public Collection<String> findExceptionEmbargos(
			Collection<ExceptionEmbargoFilterVO> exceptionEmbargoFilterVOs)
			throws SystemException {
		log.entering("EmbargoRulesController", "findExceptionEmbargos");
		return EmbargoExceptionRules
				.findExceptionEmbargos(exceptionEmbargoFilterVOs);
	}
	/**
	 * @author A-5642
	 * @param shipmentDetailsMap
	 * @return 
	 */
	private void populateAllAndAnyParameters(
			Map<String, Collection<String>> shipmentDetailsMap) {
		Set<String> parameterSetCarriers = getParameterValues(shipmentDetailsMap,
				ShipmentDetailsVO.CARRIER_ORG,
				ShipmentDetailsVO.CARRIER_DST, ShipmentDetailsVO.CARRIER_VIA);
		Set<String> parameterSetFlightNumbers = getParameterValues(shipmentDetailsMap,
				ShipmentDetailsVO.FLTNUM_ORG,
				ShipmentDetailsVO.FLTNUM_DST, ShipmentDetailsVO.FLTNUM_VIA);
		Set<String> parameterSetFlightOwners = getParameterValues(shipmentDetailsMap,
				ShipmentDetailsVO.FLTOWR_ORG,
				ShipmentDetailsVO.FLTOWR_DST, ShipmentDetailsVO.FLTOWR_VIA);
		Set<String> parameterSetFlightTypes = getParameterValues(shipmentDetailsMap,
				ShipmentDetailsVO.FLTTYPE_ORG,
				ShipmentDetailsVO.FLTTYPE_DST, ShipmentDetailsVO.FLTTYPE_VIA);
		Set<String> parameterSetAirlineGroups = getParameterValues(shipmentDetailsMap,
				ShipmentDetailsVO.AIRLINE_GRP_ORG,
				ShipmentDetailsVO.AIRLINE_GRP_DST, ShipmentDetailsVO.AIRLINE_GRP_VIA);
		Set<String> parameterSetAircraftType = getParameterValues(shipmentDetailsMap,
				"ACRTYPORG",
				"ACRTYPDST", "ACRTYPVIA");
		Set<String> parameterSetAircraftTypeGroup = getParameterValues(shipmentDetailsMap,
				"ACRTYPGRPORG",
				"ACRTYPGRPDST", "ACRTYPGRPVIA");
		if (parameterSetCarriers.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetCarriers, ShipmentDetailsVO.CARRIER_ALL);
		}
		if (parameterSetFlightNumbers.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetFlightNumbers, ShipmentDetailsVO.FLTNUM_ALL);
		}
		if (parameterSetFlightOwners.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetFlightOwners, ShipmentDetailsVO.FLTOWR_ALL);
		}
		if (parameterSetFlightTypes.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetFlightTypes, ShipmentDetailsVO.FLTTYPE_ALL);
		}
		if (parameterSetAirlineGroups.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetAirlineGroups, ShipmentDetailsVO.AIRLINE_GRP_ALL);
		}
		if (parameterSetAircraftType.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetAircraftType, ShipmentDetailsVO.AIRCRAFT_TYPE_ALL);
		}
		if (parameterSetAircraftTypeGroup.size() > 0) {
			populateShipmentDetailsMap(shipmentDetailsMap,
					parameterSetAircraftTypeGroup, ShipmentDetailsVO.AIRCRAFT_TYPE_GROUP_ALL);
		}
	}
	/**
	 * @author A-5642
	 * @param shipmentDetailsMap
	 * @return 
	 */
	private Set<String> getParameterValues(
			Map<String, Collection<String>> shipmentDetailsMap,
			String orgCode, String dstCode, String viaCode) {
		Set<String> parameterSetValues = new HashSet<String>();
		Collection<String> orgValues = shipmentDetailsMap.get(
				orgCode);
		Collection<String> dstValues = shipmentDetailsMap.get(
				dstCode);
		Collection<String> viaValues = shipmentDetailsMap.get(
				viaCode);
		if (orgValues != null && orgValues.size() > 0) {
			parameterSetValues.addAll(orgValues);
		}
		if (dstValues != null && dstValues.size() > 0) {
			parameterSetValues.addAll(dstValues);
		}
		if (viaValues != null && viaValues.size() > 0) {
			parameterSetValues.addAll(viaValues);
		}
		return parameterSetValues;
	}
	/**
	 * @author A-5642
	 * @param shipmentDetailsMap
	 * @return 
	 */
	private void populateShipmentDetailsMap(
			Map<String, Collection<String>> shipmentDetailsMap,
			Set<String> parameterValues, String applicableCode) {
		/*converting to comma separated,splitting using comma
		for generating individual elements in set */
		Set<String> parameterSetValues = new HashSet<String>();
		parameterSetValues.addAll(Arrays.asList((StringUtils.join(
				parameterValues.toArray(), COMMA)).split(COMMA)));
		shipmentDetailsMap.put(
				applicableCode,
				parameterSetValues);
	}
	
	/**
	 * 
	 * 	Method		:	EmbargoRulesController.getEmbargoEnquiryDetails
	 *	Added by 	:	A-5153 on Feb 16, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param filterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	List<EmbargoDetailsVO>
	 */
	public List<EmbargoDetailsVO> getEmbargoEnquiryDetails(
			EmbargoFilterVO filterVO) throws SystemException {

		try {
			return EmbargoRules.getEmbargoEnquiryDetails(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getMessage(), e);

		}
	}
	/**
	 * @author A-4823
	 * @param EmbargoFilterVO
	 * @return Collection<EmbargoDetailsVO>
	 * @throws SystemException
	 */
	public Collection<EmbargoDetailsVO> parameterSelectiveEmbargoSearch(
			EmbargoFilterVO filterVO) throws SystemException
			{
		return EmbargoRules.parameterSelectiveEmbargoSearch(filterVO);
			}
	public Map<String, ProductVO> validateProductNames(String companyCode,
    		Collection<String> productNames) throws SystemException,
    		InvalidProductException{
		 try {
			 return(new ProductDefaultsProxy()).validateProductNames(companyCode,productNames);
		} catch (ProxyException proxyException) {
			throw new SystemException(proxyException.getMessage(),
					proxyException);
		}
			}

}
