/**
 * RecoDefaultsAuditBuilder.java Created on Aug 01, 2014
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults.builder;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoAuditVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoParameterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.embargo.EmbargoBusinessException;
import com.ibsplc.icargo.business.shared.embargo.vo.EmbargoVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditAction;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class RecoDefaultsAuditBuilder.
 *
 * @author a-5160
 */
public class RecoDefaultsAuditBuilder extends AbstractActionBuilder {

	/** The Constant log. */
	private static final Log log = LogFactory.getLogger("RECO DEFAULTS AUDIT");
	
	/** The embargo audit vo. */
	private EmbargoAuditVO embargoAuditVO;
	
	/** The Constant LEVELCODE. */
	private static final String LEVELCODE = "reco.defaults.levelcodes";
	
	/** The Constant APPLICABLE. */
	private static final String APPLICABLE = "reco.defaults.applicablecodes";
	
	/** The Constant EMBARGOSTATUS. */
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	
	/** The Constant GEOGRAPHIC_LEVEL_TYPES. */
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	
	/** The Constant DAY_OF_OPERATION_APPLICABLE_ON. */
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	
	/** The Constant FLIGHT_TYPE. */
	private static final String FLIGHT_TYPE = "shared.aircraft.flighttypes";
	
	/** The Constant CATEGORY_TYPES. */
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	
	/** The Constant COMPLIANCE_TYPES. */
	private static final String COMPLIANCE_TYPES= "reco.defaults.compliancetype";
	
	/** The Constant APPLICABLE_TRANSACTIONS. */
	private static final String APPLICABLE_TRANSACTIONS= "reco.defaults.applicabletransactions";
	
	/** The Constant EMBARGO_PARAMETERS. */
	private static final String EMBARGO_PARAMETERS= "reco.defaults.embargoparameters";
	
	/** The Constant RULE_TYPE. */
	private static final String RULE_TYPE = "reco.defaults.ruletype";

	/**
	 * Gets the audit vo.
	 *
	 * @return the audit vo
	 */
	/*public SharedEntityAuditVO getAuditVO() {
		return new SharedEntityAuditVO(SharedEntityAuditVO.MODULE_NAME,
				SharedEntityAuditVO.SUB_MODULE,
				SharedEntityAuditVO.AUDIT_NAME);
	}*/

	public EmbargoAuditVO getAuditVO() {
		return new EmbargoAuditVO(
			EmbargoAuditVO.AUDIT_MODULENAME,EmbargoAuditVO.
	    	AUDIT_SUBMODULENAME,EmbargoAuditVO.AUDIT_ENTITY);
	}
	
	/**
	 * Audit for embargo details.
	 *
	 * @param embargoVO the embargo vo
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @throws EmbargoBusinessException the embargo business exception
	 */
	public void auditForEmbargoDetails(EmbargoRulesVO embargoVO) 
	throws SystemException,RemoteException,EmbargoBusinessException{
		log.entering("RecoDefaultsAuditBuilder", "auditForEmbargoDetails");
		
		StringBuilder addInfo = new StringBuilder();
		embargoAuditVO = getAuditVO();
		
		embargoAuditVO.setCompanyCode(embargoVO.getCompanyCode());
		embargoAuditVO.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
		if(EmbargoVO.OPERATION_FLAG_INSERT.equals(embargoVO.getOperationalFlag())){
			if(embargoVO.getIsSuspended()){
				embargoAuditVO.setActionCode(AuditVO.UPDATE_ACTION);
				embargoAuditVO.setAuditRemarks(AuditAction.UPDATE.toString());
			}else{
			embargoAuditVO.setActionCode(AuditVO.CREATE_ACTION);
			embargoAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
			}
		}
		else{
			embargoAuditVO.setActionCode(AuditVO.UPDATE_ACTION);
			embargoAuditVO.setAuditRemarks(AuditAction.UPDATE.toString());
		}
		embargoAuditVO.setUserId(embargoVO.getLastUpdatedUser());
		embargoAuditVO.setTxnTime(embargoVO.getLastUpdatedTime());
		  Collection<String> onetimecode = new ArrayList<String>();
			onetimecode.add(LEVELCODE);
			onetimecode.add(EMBARGOSTATUS);
			onetimecode.add(GEOGRAPHIC_LEVEL_TYPES);
			onetimecode.add(DAY_OF_OPERATION_APPLICABLE_ON);
			onetimecode.add(FLIGHT_TYPE);
			onetimecode.add(CATEGORY_TYPES);
			onetimecode.add(COMPLIANCE_TYPES);
			onetimecode.add(APPLICABLE_TRANSACTIONS);
			onetimecode.add(EMBARGO_PARAMETERS);
			Map<String, Collection<OneTimeVO>> onetimevalues = null;
			try {
				onetimevalues = new SharedDefaultsProxy().findOneTimeValues(embargoVO
						.getCompanyCode(), onetimecode);
			} catch (ProxyException proxyException) {
				LogFactory.getLogger("FLIGHT").log(Log.SEVERE,
						proxyException.toString());
				// To be reviewed
			}
			addInfo.append("Ref No: ").append(embargoVO.getEmbargoReferenceNumber());
			
			if(embargoVO.getStartDate()!=null){
				addInfo.append(" ").append("Start Date:").append(embargoVO.getStartDate().toDisplayDateOnlyFormat()).append(" ");
			}
			if(embargoVO.getEndDate()!=null){
				addInfo.append(" ").append("End Date:").append(embargoVO.getEndDate().toDisplayDateOnlyFormat()).append(" ");
			}
		 
			if(embargoVO.getStatus()!=null && embargoVO.getStatus().trim().length()>0){
				String newStatus = embargoVO.getStatus();
				if (onetimevalues != null) {
					Collection<OneTimeVO> statustypes = onetimevalues.get(EMBARGOSTATUS);
					if (statustypes != null && statustypes.size() > 0) {
						for (OneTimeVO onetimevo : statustypes) {
							
							if (newStatus != null && newStatus.trim().length() > 0) {
								if (newStatus.equalsIgnoreCase(onetimevo.getFieldValue())) {
									newStatus = onetimevo.getFieldDescription();	
									break;
								}
							}
						}
					}
				}
			
				if(newStatus!=null && newStatus.trim().length()>0)
					{
					addInfo.append(" ").append("Status:").append(newStatus).append(" ");
					}
				
			}
			if(embargoVO.getEmbargoLevel()!=null && embargoVO.getEmbargoLevel().trim().length()>0){
				String newembargoLevel = embargoVO.getEmbargoLevel();
				if (onetimevalues != null) {
					Collection<OneTimeVO> newembargoLevelTypes = onetimevalues.get(LEVELCODE);
					if (newembargoLevelTypes != null && newembargoLevelTypes.size() > 0) {
						for (OneTimeVO onetimevo : newembargoLevelTypes) {
							
							if (newembargoLevel != null && newembargoLevel.trim().length() > 0) {
								if (newembargoLevel.equalsIgnoreCase(onetimevo.getFieldValue())) {
									newembargoLevel = onetimevo.getFieldDescription();		
									break;
								}
							}
						}
					}
				}
				if(newembargoLevel!=null && newembargoLevel.trim().length()>0)
					{
					addInfo.append(" ").append("EmbargoLevel:").append(newembargoLevel).append(" ");
					}
			
			}
			if(embargoVO.getCategory()!=null && embargoVO.getCategory().trim().length()>0){
				String newcategory = embargoVO.getCategory();
				if (onetimevalues != null) {
					Collection<OneTimeVO> categoryTypes = onetimevalues.get(CATEGORY_TYPES);
					if (categoryTypes != null && categoryTypes.size() > 0) {
						for (OneTimeVO onetimevo : categoryTypes) {
							
							if (newcategory != null && newcategory.trim().length() > 0) {
								if (newcategory.equalsIgnoreCase(onetimevo.getFieldValue())) {
									newcategory = onetimevo.getFieldDescription();
									break;
								}
							}
						}
					}
				}
				if(newcategory!=null && newcategory.trim().length()>0)
					{
					addInfo.append(" ").append("Category:").append(newcategory).append(" ");
					}
				
			}
			if(embargoVO.getComplianceType()!=null && embargoVO.getComplianceType().trim().length()>0){
				String newcomplianceType = embargoVO.getComplianceType();
				if (onetimevalues != null) {
					Collection<OneTimeVO> complianceTypes = onetimevalues.get(COMPLIANCE_TYPES);
					if (complianceTypes != null && complianceTypes.size() > 0) {
						for (OneTimeVO onetimevo : complianceTypes) {										
							if (newcomplianceType != null && newcomplianceType.trim().length() > 0) {
								if (newcomplianceType.equalsIgnoreCase(onetimevo.getFieldValue())) {
									newcomplianceType = onetimevo.getFieldDescription();	
									break;
								}
							}
						}
					}
				}
				if(newcomplianceType!=null && newcomplianceType.trim().length()>0)
					{
					addInfo.append(" ").append("Compliance Type:").append(newcomplianceType).append(" ");
					}
				
			}
			if(embargoVO.getApplicableTransactions()!=null && embargoVO.getApplicableTransactions().trim().length()>0){
				String newapplicableTransactions = embargoVO.getApplicableTransactions();
				if (onetimevalues != null) {
					Collection<OneTimeVO> applicableTransactions = onetimevalues.get(APPLICABLE_TRANSACTIONS);
					if (applicableTransactions != null && applicableTransactions.size() > 0) {
						for (OneTimeVO onetimevo : applicableTransactions) {										
							if (newapplicableTransactions != null && newapplicableTransactions.trim().length() > 0) {
								if (newapplicableTransactions.equalsIgnoreCase(onetimevo.getFieldValue())) {
									newapplicableTransactions = onetimevo.getFieldDescription();	
									break;
								}
							}
						}
					}
				}
				if(newapplicableTransactions!=null && newapplicableTransactions.trim().length()>0)
					{
					addInfo.append(" ").append("Applicable Transactions:").append(newapplicableTransactions).append(" ");
					}
				
			}
			if(embargoVO.getEmbargoDescription()!=null && embargoVO.getEmbargoDescription().trim().length()>0){
				addInfo.append(" ").append("Embargo Description:").append(embargoVO.getEmbargoDescription()).append(" ");
			}
			if(embargoVO.getRemarks()!=null && embargoVO.getRemarks().trim().length()>0){
				addInfo.append(" ").append("Remarks:").append(embargoVO.getRemarks()).append(" ");
			}
			if(embargoVO.getIsSuspended())
				{
				addInfo.append(" ").append("Issuspended:Y").append(" ");
				}
			else
				{
				addInfo.append(" ").append("Issuspended:N").append(" ");
				}
			
			
			
		  if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
			  for(EmbargoParameterVO embargoRulesParameter : embargoVO.getParameters()){
				  if(!"P".equals(embargoRulesParameter.getParameterLevel()) && !"-".equals(embargoRulesParameter.getParameterValues())){
					  String geographicLevel = embargoRulesParameter.getParameterLevel();
						if (onetimevalues != null) {
							Collection<OneTimeVO> geographicLevels = onetimevalues.get(DAY_OF_OPERATION_APPLICABLE_ON);
							if (geographicLevels != null && geographicLevels.size() > 0) {
								for (OneTimeVO onetimevo : geographicLevels) {
									if (geographicLevel != null && geographicLevel.trim().length() > 0) {
										if (geographicLevel.equalsIgnoreCase(onetimevo.getFieldValue())) {
											geographicLevel = onetimevo.getFieldDescription();		
											break;
										}
									}
								}
							}
						}
						String geographicLevelType =embargoRulesParameter.getParameterCode();
						if (onetimevalues != null) {
							Collection<OneTimeVO> geographicLevelTypes = onetimevalues.get(GEOGRAPHIC_LEVEL_TYPES);
							if (geographicLevelTypes != null && geographicLevelTypes.size() > 0) {
								for (OneTimeVO onetimevo : geographicLevelTypes) {
									if (geographicLevelType != null && geographicLevelType.trim().length() > 0) {
										if (geographicLevelType.equalsIgnoreCase(onetimevo.getFieldValue())) {
											geographicLevelType = onetimevo.getFieldDescription();		
											break;
										}
									}
								}
							}
						}
						if(!"-".equals(embargoRulesParameter.getParameterValues()))
					  {
					   addInfo.append(" Geographic Level : ").append(geographicLevel).append(" Geographic Level Type : ").append(geographicLevelType).append(" Geographic Values : ").append(embargoRulesParameter.getParameterValues()).append(" ");
					  }
				  }
			  }
		  }
		  if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
			  for(EmbargoParameterVO embargoRulesParameter : embargoVO.getParameters()){
				  if("P".equals(embargoRulesParameter.getParameterLevel())){
					  if("DOW".equals(embargoRulesParameter.getParameterCode())){
						  String applicableLevel =embargoRulesParameter.getApplicableLevel();
							if (onetimevalues != null) {
								Collection<OneTimeVO> applicableLevels = onetimevalues.get(DAY_OF_OPERATION_APPLICABLE_ON);
								if (applicableLevels != null && applicableLevels.size() > 0) {
									for (OneTimeVO onetimevo : applicableLevels) {
										if (applicableLevel != null && applicableLevel.trim().length() > 0) {
											if (applicableLevel.equalsIgnoreCase(onetimevo.getFieldValue())) {
												applicableLevel = onetimevo.getFieldDescription();		
												break;
											}
										}
									}
								}
							}
						  addInfo.append(" Days Of Week : ").append(embargoRulesParameter.getParameterValues())
				  			.append(" Applicable On : ").append(applicableLevel).append(" ");
					  }
				  }
			  }
		  }
		  if(embargoVO.getParameters()!=null && embargoVO.getParameters().size()>0){
			  for(EmbargoParameterVO embargoRulesParameter : embargoVO.getParameters()){
				  if("P".equals(embargoRulesParameter.getParameterLevel()) && !"-".equals(embargoRulesParameter.getParameterValues())){
					  /*if("DIM".equals(embargoRulesParameter.getEmbargoParameterPk().getParameterCode())){
						  addInfo.append(" Parameter : ").append(embargoRulesParameter.getEmbargoParameterPk().getParameterCode());
				  
				  }
					  else*/ 
					  String paramCode =embargoRulesParameter.getParameterCode();
						if (onetimevalues != null) {
							Collection<OneTimeVO> paramCodes = onetimevalues.get(EMBARGO_PARAMETERS);
							if (paramCodes != null && paramCodes.size() > 0) {
								for (OneTimeVO onetimevo : paramCodes) {
									if (paramCode != null && paramCode.trim().length() > 0) {
										if (paramCode.equalsIgnoreCase(onetimevo.getFieldValue())) {
											paramCode = onetimevo.getFieldDescription();		
											break;
										}
									}
								}
							}
						}
					  if(!"DOW".equals(embargoRulesParameter.getParameterCode())){
						  addInfo.append(" Parameter : ").append(paramCode)
					  			.append(" Parameter Values : ").append(embargoRulesParameter.getParameterValues()).append(" ");
					  }
				  }
			  }
		  }
		 
		  embargoAuditVO.setAdditionalInformation(addInfo.toString());
		  AuditUtils.performAudit(embargoAuditVO);
		   log.log(Log.FINE,"----------AFTER PERFORM AUDIT");

		log.exiting("RecoDefaultsAuditBuilder","auditForEmbargoDetails");
	}
	
	
	/**
	 * Audit for status update.
	 *
	 * @param embargoDetailsVOs the embargo details v os
	 * @throws SystemException the system exception
	 * @throws RemoteException the remote exception
	 * @throws EmbargoBusinessException the embargo business exception
	 */
	public void auditForStatusUpdate(Collection<EmbargoDetailsVO> embargoDetailsVOs) 
	throws SystemException,RemoteException,EmbargoBusinessException{
		log.entering("RecoDefaultsAuditBuilder", "auditForStatusUpdate");
		for(EmbargoDetailsVO embargoDetailsVO : embargoDetailsVOs){
			embargoAuditVO=getAuditVO();
			embargoAuditVO.setUserId(embargoDetailsVO.getLastUpdatedUser());
			embargoAuditVO.setCompanyCode(embargoDetailsVO.getCompanyCode());
			embargoAuditVO.setEmbargoReferenceNumber(embargoDetailsVO.getEmbargoReferenceNumber());
			embargoAuditVO.setActionCode(AuditVO.UPDATE_ACTION);
			embargoAuditVO.setAuditRemarks(AuditAction.UPDATE.toString());
			embargoAuditVO.setUserId(embargoDetailsVO.getLastUpdatedUser());
			embargoAuditVO.setTxnTime(embargoDetailsVO.getLastUpdateTime());
			if("C".equals(embargoDetailsVO.getStatus())){
				embargoAuditVO.setAdditionalInformation(new StringBuilder("Ref Number: ").append(embargoDetailsVO.getEmbargoReferenceNumber())
				.append(" is Cancelled").toString());
				embargoAuditVO.setAuditRemarks("Embargo master cancelled");
			}
			else if("A".equals(embargoDetailsVO.getStatus())){
				embargoAuditVO.setAdditionalInformation(new StringBuilder("Ref Number: ").append(embargoDetailsVO.getEmbargoReferenceNumber())
				.append(" is Approved").toString());
				embargoAuditVO.setAuditRemarks("Embargo master approved");
			}
			else if("I".equals(embargoDetailsVO.getStatus())){
				embargoAuditVO.setAdditionalInformation(new StringBuilder("Ref Number: ").append(embargoDetailsVO.getEmbargoReferenceNumber())
				.append(" is Inactive").toString());
				embargoAuditVO.setAuditRemarks("Embargo master inactive");
			}
			else if("R".equals(embargoDetailsVO.getStatus())){
				embargoAuditVO.setAdditionalInformation(new StringBuilder("Ref Number: ").append(embargoDetailsVO.getEmbargoReferenceNumber())
				.append(" is Rejected").toString());
				embargoAuditVO.setAuditRemarks("Embargo master rejected");
			}
			else if("S".equals(embargoDetailsVO.getStatus())){
				embargoAuditVO.setAdditionalInformation(new StringBuilder("Ref Number: ").append(embargoDetailsVO.getEmbargoReferenceNumber())
				.append(" is Suspended").toString());
				embargoAuditVO.setAuditRemarks("Embargo master suspended");
			}
			AuditUtils.performAudit(embargoAuditVO);
		}
		
		log.exiting("RecoDefaultsAuditBuilder","auditForStatusUpdate");
	}
}
