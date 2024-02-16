/**
 * RecoDefaultsWorkflowBuilder.java Created on Aug 14, 2014
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.proxy.WorkflowDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.AssigneeFilterVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterConstantsVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.ParameterInProcessVO;
import com.ibsplc.icargo.business.workflow.defaults.vo.WorkflowVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class RecoDefaultsWorkflowBuilder.
 */
public class RecoDefaultsWorkflowBuilder extends AbstractActionBuilder {
	private Log log = LogFactory.getLogger("RecoDefaultsWorkflowBuilder");
	/** The Constant RULE_TYPE. */
	private static final String RULE_TYPE = "reco.defaults.ruletype";
	
	/** The Constant EMBARGOSTATUS. */
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	
	/**
	 * Generate workflow for embargo creation.
	 *
	 * @param embargoRulesVO the embargo rules vo
	 * @throws SystemException the system exception
	 * @author a-5160
	 */
	public void generateWorkflowForEmbargoCreation(EmbargoRulesVO embargoRulesVO) throws SystemException{
		if("D".equals(embargoRulesVO.getStatus()) ||
				(("A".equals(embargoRulesVO.getStatus())|| "C".equals(embargoRulesVO.getStatus()) || "S".equals(embargoRulesVO.getStatus())) && 
						("OK2F".equals(embargoRulesVO.getComplianceType()) || "OK2Forward".equals(embargoRulesVO.getComplianceType())))){
			/*EmbargoRules embargoRules = null;
			try {
				embargoRules = EmbargoRules.find(embargoRulesVO.getCompanyCode(),
						embargoRulesVO.getEmbargoReferenceNumber(),embargoRulesVO.getEmbargoVersion());
			} catch (FinderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace()
			}
			if(embargoRules!=null){*/
				Collection<WorkflowVO> workflowVos = new ArrayList<WorkflowVO>();
				WorkflowVO workflowVO = new WorkflowVO();
				Map<String, String> systemParameters = getSystemParameters();
		    	LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
		    	workflowVO.setCompanyCode(logonAttributes.getCompanyCode());
		    	workflowVO.setStationCode(embargoRulesVO.getAirportCode());
	
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
					onetimevalues = new SharedDefaultsProxy().findOneTimeValues(embargoRulesVO
							.getCompanyCode(), onetimecode);
				} catch (ProxyException proxyException) {
					log.log(Log.SEVERE, "ProxyException caught");
				}		
				String ruleType = embargoRulesVO.getRuleType();
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
				String status = embargoRulesVO.getStatus();
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
				assigneeFilterVO.setAirportCode(embargoRulesVO.getAirportCode());
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
				param.setParamaterValue(embargoRulesVO.getEmbargoReferenceNumber());
				workflowVO.getParametersInProcess().add(param);
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_EMBARGO);
				param.setParamaterValue(embargoRulesVO.getEmbargoDescription());
				workflowVO.getParametersInProcess().add(param);
				param = new ParameterInProcessVO(); 
				param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STACOD);
				param.setParamaterValue(status);
				workflowVO.getParametersInProcess().add(param);
				//Added by a-5160 for ICRD-79521 starts
				if(("A".equals(embargoRulesVO.getStatus())|| "C".equals(embargoRulesVO.getStatus()) || "S".equals(embargoRulesVO.getStatus())) && 
						("OK2F".equals(embargoRulesVO.getComplianceType()) || "OK2Forward".equals(embargoRulesVO.getComplianceType()))){
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
				workflowVO.setLastUpdateUser(embargoRulesVO.getLastUpdatedUser());
				workflowVos.add(workflowVO);
		    
				try {  
					if(workflowVos!=null && !workflowVos.isEmpty()){
						new WorkflowDefaultsProxy().generateNotification(workflowVos);
					}
				} catch (ProxyException e) {
					throw new SystemException(e.getMessage(), e);
				}
			//}
		}
	}
	
	/**
	 * Generate workflow for embargo updation.
	 * @author a-5160
	 * @param embargoDetailsVOs the embargo details v os
	 * @throws SystemException the system exception
	 */
	public void generateWorkflowForEmbargoUpdation(Collection<EmbargoDetailsVO> embargoDetailsVOs) throws SystemException{
		for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
				if(("A".equals(embargoDetailsVO.getStatus())|| "C".equals(embargoDetailsVO.getStatus()) || "S".equals(embargoDetailsVO.getStatus())) && 
						("OK2F".equals(embargoDetailsVO.getComplianceType()) || "OK2Forward".equals(embargoDetailsVO.getComplianceType()))){
				
					Collection<WorkflowVO> workflowVos = new ArrayList<WorkflowVO>();
					WorkflowVO workflowVO = new WorkflowVO();
					Map<String, String> systemParameters = getSystemParameters();
			    	LogonAttributes logonAttributes=ContextUtils.getSecurityContext().getLogonAttributesVO();
			    	workflowVO.setCompanyCode(logonAttributes.getCompanyCode());
			    	workflowVO.setStationCode(embargoDetailsVO.getAirportCode());
		
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
						onetimevalues = new SharedDefaultsProxy().findOneTimeValues(embargoDetailsVO
								.getCompanyCode(), onetimecode);
					} catch (ProxyException proxyException) {
						log.log(Log.SEVERE, "ProxyException caught");
					}		
					String ruleType = embargoDetailsVO.getRuleType();
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
					String status = embargoDetailsVO.getStatus();
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
					assigneeFilterVO.setAirportCode(embargoDetailsVO.getAirportCode());
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
					param.setParamaterValue(embargoDetailsVO.getEmbargoReferenceNumber());
					workflowVO.getParametersInProcess().add(param);
					param = new ParameterInProcessVO(); 
					param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_EMBARGO);
					param.setParamaterValue(embargoDetailsVO.getEmbargoDescription());
					workflowVO.getParametersInProcess().add(param);
					param = new ParameterInProcessVO(); 
					param.setParameterCode(ParameterConstantsVO.WRKFLO_PARCOD_STACOD);
					param.setParamaterValue(status);
					workflowVO.getParametersInProcess().add(param);
					//Added by a-5160 for ICRD-79521 starts
					if(("A".equals(embargoDetailsVO.getStatus())|| "C".equals(embargoDetailsVO.getStatus()) || "S".equals(embargoDetailsVO.getStatus())) 
							&& ("OK2F".equals(embargoDetailsVO.getComplianceType()) || "OK2Forward".equals(embargoDetailsVO.getComplianceType()))){
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
					workflowVO.setLastUpdateUser(embargoDetailsVO.getLastUpdatedUser());
					workflowVos.add(workflowVO);
			    
					try {  
						if(workflowVos!=null && !workflowVos.isEmpty()){
							new WorkflowDefaultsProxy().generateNotification(workflowVos);
						}
					} catch (ProxyException e) {
						throw new SystemException(e.getMessage(), e);
					}
				}
		}
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
	
}