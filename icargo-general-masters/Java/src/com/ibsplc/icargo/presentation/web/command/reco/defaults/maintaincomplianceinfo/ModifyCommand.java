/*
 * ModifyCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintaincomplianceinfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoFilterVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoGlobalParameterVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo.MaintainComplianceInfoSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.workflow.defaults.MessageInboxSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm;
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

	private static final String APPLICABLE = "reco.defaults.applicablecodes";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String STATION="S";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";	
	
	private static final String WORKFLOW_SCREEN_ID = "workflow.defaults.messageinbox";
	
	private static final String WORKFLOW_MODULE_NAME = "workflow.defaults";
	
	private Log log = LogFactory.getLogger("MODIFY COMMAND");

	
	/**
	 * The execute method in Modify Command
	 *
	 * @author A-1747
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		MaintainComplianceInfoForm maintainComplianceForm =
					(MaintainComplianceInfoForm) invocationContext.screenModel;
		
		MessageInboxSession messageInboxSession = 
			getScreenSession(WORKFLOW_MODULE_NAME, WORKFLOW_SCREEN_ID);
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		EmbargoRulesDelegate embargoDelegate ;
		EmbargoRulesVO embargoVo = null;
		String companyCode = logonAttributes.getCompanyCode();
	    embargoDelegate = new EmbargoRulesDelegate();
	    MaintainComplianceInfoSession maintainComplianceSession = getScreenSession(
				"reco.defaults", "reco.defaults.maintaincomplianceinfo");
		maintainComplianceSession.setDetailsButton(true);
		
		SharedDefaultsDelegate sharedDefaultsDelegate
						= new SharedDefaultsDelegate();
		Map hashMap = null;
		Collection<String> oneTimeList = new ArrayList<String>();
		Collection<EmbargoGlobalParameterVO> globalParams
						= new ArrayList<EmbargoGlobalParameterVO>();
		oneTimeList.add(APPLICABLE);
		oneTimeList.add(EMBARGOSTATUS);
		oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
		oneTimeList.add(CATEGORY_TYPES);
		oneTimeList.add(GEOGRAPHIC_LEVEL_TYPES);
		
		String[] referenceNos=maintainComplianceForm.getRowId();
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
			maintainComplianceForm.setRefNumber(referenceNo.toUpperCase());
		}
		else if(maintainComplianceForm.getRefNumber()!=null){
			referenceNo = maintainComplianceForm.getRefNumber().toUpperCase();
		}
		else if(messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM)!=null &&
				messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM).trim().length()>0){			
			referenceNo=messageInboxSession.getParameterMap().get(ParameterConstantsVO.WRKFLO_PARCOD_REFNUM);		
		}

		EmbargoFilterVO embargoFilterVO = new EmbargoFilterVO();
		embargoFilterVO.setCompanyCode(companyCode);
		embargoFilterVO.setEmbargoRefNumber(referenceNo);
		if(maintainComplianceForm.getEmbargoVersion()!=null){
			embargoFilterVO.setEmbargoVersion(Integer.parseInt(maintainComplianceForm.getEmbargoVersion()));
		}
		try {
			 //globalParams = embargoDelegate.findGlobalParameterCodes(companyCode);
			 embargoVo=embargoDelegate.findEmbargoDetails(embargoFilterVO);
			 hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);
		} catch (BusinessDelegateException ex) {
		    	handleDelegateException(ex);
				
		    	invocationContext.target = MODIFY_SCREENLOAD_FAILURE;
		}
		log.log(Log.FINE, "\n\n the embargo reference number is ", referenceNo);
		maintainComplianceForm.setRefNumber(referenceNo);
		boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
		if(hasBusinessPrivilege)
			{
			maintainComplianceForm.setIsPrivilegedUser("Y");
			}
		else
			maintainComplianceForm.setIsPrivilegedUser("N");	
		if(embargoVo!=null && embargoVo.getEmbargoReferenceNumber()!=null){
			if("C".equalsIgnoreCase(embargoVo.getRuleType())){
				
				Collection<OneTimeVO> applicableCodes = (Collection<OneTimeVO>)hashMap.get(APPLICABLE);
				Collection<OneTimeVO> embargoStatus = (Collection<OneTimeVO>)hashMap.get(EMBARGOSTATUS);
				Collection<OneTimeVO>  geographicLevelType= (Collection<OneTimeVO>)hashMap.get(GEOGRAPHIC_LEVEL_TYPES);
				Collection<EmbargoParameterVO> embargoParameterVos = new ArrayList<EmbargoParameterVO>();
				Collection<OneTimeVO>  dayOfOperationApplicableOn = (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
				Collection<OneTimeVO>  categoryTypes = (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES);
	
				maintainComplianceSession.setGeographicLevelType(geographicLevelType);
				maintainComplianceSession.setApplicableCode(applicableCodes);
				maintainComplianceSession.setEmbargoStatus(embargoStatus);
				maintainComplianceSession.setCategoryTypes(categoryTypes);
				
				maintainComplianceSession.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
				if(embargoVo.getParameters()!=null  && embargoVo.getParameters().size()>0){
					log.log(Log.FINE, "%%%%%%%%%%% PARAMETERS IS NOT NULL $$$$$$$$$$$$$$");
					embargoParameterVos=embargoVo.getParameters();
					for(EmbargoParameterVO embargoParameterVO:embargoParameterVos){
						embargoParameterVO.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
					}
				}
				if(embargoVo.getParameters().size()==0){
					embargoVo.setParameters(null);
				}
				if(embargoVo.getOriginType()==null){
					embargoVo.setOriginType(STATION);
				}
				if(embargoVo.getDestinationType()==null){
					embargoVo.setDestinationType(STATION);
				}
				embargoVo.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_UPDATE);
				maintainComplianceSession.setEmbargoVo(embargoVo);
				maintainComplianceSession.setEmbargoParameters(embargoVo.getParameters());
				maintainComplianceForm.setRefNumberFlag("true");
				invocationContext.target = MODIFY_SCREENLOAD_SUCCESS;
			}
			else{
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				ErrorVO error = null;
				error = new ErrorVO("reco.defaults.embargoexists");
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
			error = new ErrorVO("reco.defaults.nilcomplianceinfo",obj);// Saved Successfully
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
}
