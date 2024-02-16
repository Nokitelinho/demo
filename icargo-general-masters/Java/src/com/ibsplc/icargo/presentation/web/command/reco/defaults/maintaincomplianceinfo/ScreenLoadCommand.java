/*
 * ScreenloadCommand.java Created on Jun 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.reco.defaults.maintaincomplianceinfo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.reco.defaults.maintaincomplianceinfo.MaintainComplianceInfoSession;
import com.ibsplc.icargo.presentation.web.struts.form.reco.defaults.MaintainComplianceInfoForm;
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
public class ScreenLoadCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	private static final String APPLICABLE = "reco.defaults.applicablecodes";
	private static final String EMBARGOSTATUS = "reco.defaults.status";
	private static final String GEOGRAPHIC_LEVEL_TYPES= "reco.defaults.geographicleveltype";
	private static final String DAY_OF_OPERATION_APPLICABLE_ON = "reco.defaults.dayofoperationapplicableon";
	private static final String OPERATION_FLAG_INSERT = "I";
	private static final String CATEGORY_TYPES= "reco.defaults.category";
	private static final String PRIVILEGE_CODE = "reco.defaults.adminprivilege";
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
		    MaintainComplianceInfoSession maintainComplianceSession= getScreenSession( "reco.defaults","reco.defaults.maintaincomplianceinfo");
		   	if(maintainComplianceSession.getEmbargoVo()!=null){
		   		maintainComplianceSession.setEmbargoVo(null);
		   	}//Added for ICRD-167922 starts
		   	if (maintainComplianceSession.getEmbargoParameters() != null) {
		        maintainComplianceSession.setEmbargoParameters(null);
		    }//Added for ICRD-167922 ends
		   	MaintainComplianceInfoForm maintainComplianceForm=(MaintainComplianceInfoForm)invocationContext.screenModel;
			SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
			EmbargoRulesVO embargoVo=new EmbargoRulesVO();
			Map hashMap = null;
			log.log(Log.FINE, "Controller:getScreenLoadDetails");

			Collection<String> oneTimeList = new ArrayList<String>();
			oneTimeList.add(APPLICABLE);
			oneTimeList.add(EMBARGOSTATUS);
			oneTimeList.add(GEOGRAPHIC_LEVEL_TYPES);
			oneTimeList.add(DAY_OF_OPERATION_APPLICABLE_ON);
			oneTimeList.add(CATEGORY_TYPES);
			
			
			Collection<EmbargoGlobalParameterVO> globalParams = new ArrayList<EmbargoGlobalParameterVO>();
			try {
				hashMap = sharedDefaultsDelegate.findOneTimeValues(companyCode,oneTimeList);

			} catch (BusinessDelegateException exception) {
				handleDelegateException(exception);
				log.log(Log.SEVERE, "onetime fetch exception");
			}

			Collection<OneTimeVO> applicableCodesTemp = (Collection<OneTimeVO>)hashMap.get(APPLICABLE);
			Collection<OneTimeVO> embargoStatus = (Collection<OneTimeVO>)hashMap.get(EMBARGOSTATUS);
			Collection<OneTimeVO>  geographicLevelType= (Collection<OneTimeVO>)hashMap.get(GEOGRAPHIC_LEVEL_TYPES);
			Collection<OneTimeVO>  dayOfOperationApplicableOnTemp = (Collection<OneTimeVO>)hashMap.get(DAY_OF_OPERATION_APPLICABLE_ON);
			Collection<OneTimeVO>  categoryTypes = (Collection<OneTimeVO>)hashMap.get(CATEGORY_TYPES);

			Collection<OneTimeVO>  dayOfOperationApplicableOn = new ArrayList<OneTimeVO>();
			if(dayOfOperationApplicableOnTemp!=null){
				OneTimeVO origin = null;
				OneTimeVO destination = null;
				OneTimeVO viapoint = null;
				for(OneTimeVO vo : dayOfOperationApplicableOnTemp){
					if("O".equals(vo.getFieldValue())){
						origin = vo;
					}
					if("D".equals(vo.getFieldValue())){
						destination=vo;
					}
					if("V".equals(vo.getFieldValue())){
						viapoint = vo;
					}
				}
				if(origin!=null)
					{
					dayOfOperationApplicableOn.add(origin);
					}
				if(destination!=null)
					{
					dayOfOperationApplicableOn.add(destination);
					}
				if(viapoint!=null)
					{
					dayOfOperationApplicableOn.add(viapoint);
			}
			}
			Collection<OneTimeVO>  applicableCodes = new ArrayList<OneTimeVO>();
			if(applicableCodesTemp!=null){
				OneTimeVO include=null;
				OneTimeVO exclude=null;
				for(OneTimeVO vo : applicableCodesTemp){
					if("IN".equals(vo.getFieldValue())){
						include = vo;
					}
					if("EX".equals(vo.getFieldValue())){
						exclude=vo;
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
			}
			log.log(Log.FINE, "Got from ONETIME");
			ArrayList<EmbargoParameterVO> paramVos = new ArrayList<EmbargoParameterVO>();
			EmbargoParameterVO paramVo = new EmbargoParameterVO();
			paramVo.setParameterCode("");
			paramVo.setParameterValues("");
			paramVo.setOperationalFlag(OPERATION_FLAG_INSERT);
			paramVo.setApplicable("");
			paramVos.add(paramVo);
			maintainComplianceSession.setApplicableCode(applicableCodes);
			maintainComplianceSession.setEmbargoStatus(embargoStatus);			
			maintainComplianceSession.setGeographicLevelType(geographicLevelType);
			maintainComplianceSession.setDayOfOperationApplicableOn(dayOfOperationApplicableOn);
			maintainComplianceSession.setCategoryTypes(categoryTypes);
			maintainComplianceForm.setStartDate(DateUtilities.getCurrentDate(DateTimeFormatHelper.getDefaultDateFormat()));
			maintainComplianceForm.setEndDate(DateUtilities.getCurrentDate(DateTimeFormatHelper.getDefaultDateFormat()));

			maintainComplianceForm.setRefNumber("");
			maintainComplianceForm.setCategory("");
			maintainComplianceForm.setScc("");
			maintainComplianceForm.setSccGroup("");
			maintainComplianceForm.setParameterValue("");
			maintainComplianceForm.setParameterCode("");
			//Setting Values in embargoVo
			embargoVo.setEmbargoLevel("");
			embargoVo.setEmbargoReferenceNumber("");
			embargoVo.setStatus("");
			/*for(OneTimeVO statusVO: embargoStatus){
		    	if(statusVO.getFieldDescription().equals("DRAFT")){
		    		embargoVo.setStatus(statusVO.getFieldValue());
		    	}
		    }*/
			embargoVo.setDaysOfOperationApplicableOn("");
			
			boolean hasBusinessPrivilege = checkBusinessPrivilege(PRIVILEGE_CODE);
			if(hasBusinessPrivilege)
				{
				maintainComplianceForm.setIsPrivilegedUser("Y");
				}
			else
				{
				maintainComplianceForm.setIsPrivilegedUser("N");	
				}	
			
			Date date = TimeConvertor.getCurrentDate();
			String startDate = TimeConvertor.toStringFormat(date,DateTimeFormatHelper.getDefaultDateFormat());
			LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);			
			embargoVo.setStartDate(localStartDate.setDate(startDate));
			
			embargoVo.setOperationalFlag(EmbargoRulesVO.OPERATION_FLAG_INSERT);
			
			//embargoVo.setParameters(paramVos);
			log.log(Log.FINE,"\n\n embargo vo ------------------------------ "+embargoVo);
			//maintainEmbargoSession.setEmbargoParameterVos(paramVos);
			maintainComplianceSession.setEmbargoVo(embargoVo);
			
			if(maintainComplianceSession.getIsSaved() != null){
				if(maintainComplianceSession.getIsSaved().trim().length()>0){
					Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
					ErrorVO error = null;
					String referenceNoObj= maintainComplianceSession.getIsSaved();
					Object[] obj={referenceNoObj};
					error = new ErrorVO("compliance.save",obj);// Saved Successfully
					error.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(error);
					invocationContext.addAllError(errors);

				}
			}
			maintainComplianceForm.setRefNumberFlag("false");
			maintainComplianceSession.removeIsSaved();
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
