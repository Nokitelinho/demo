/*
 * ListCommand.java Created on SEP 10, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.incentiveconfiguration;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
public class ListCommand extends BaseCommand{


	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";

	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String GPA_EMPTY =
			"mailtracking.defaults.ux.mailperformance.msg.err.gpaempty";
	private static final String GPA_NOT_EXISTS = "mailtracking.defaults.ux.mailperformance.msg.err.gpanotexists";
	private static final String NO_RESULTS_FOUND = "mailtracking.defaults.ux.mailperformance.msg.err.noresultsfound";


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		log.log(Log.FINE, "\n\n in the Incentive Configuration list command----------> \n\n");

		MailPerformanceForm mailPerformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailPerformanceSession =
							getScreenSession(MODULE_NAME,SCREEN_ID);

		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();

	    IncentiveConfigurationFilterVO incentiveConfigurationFilterVO = null;
	    ArrayList<IncentiveConfigurationVO> incentiveConfigurationVOs = null;
	    Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

	    MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
	    		new MailTrackingDefaultsDelegate();
	    PostalAdministrationVO postalVO = new PostalAdministrationVO();

	    incentiveConfigurationFilterVO = new IncentiveConfigurationFilterVO();
	    incentiveConfigurationFilterVO.setCompanyCode(companyCode);
	    incentiveConfigurationFilterVO.setPaCode(mailPerformanceForm.getIncPaCode());

	    if(mailPerformanceForm.getIncPaCode() == null || mailPerformanceForm.getIncPaCode().length()==0){

	    	log.log(Log.FINE, "\n\n GPA CODE EMPTY =============> \n\n");
	    	ErrorVO error = new ErrorVO(GPA_EMPTY);
	    	errors.add(error);
	    	invocationContext.addAllError(errors);
	    	mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
	    	mailPerformanceForm.setStatusFlag("List_failure");
	    	invocationContext.target = FAILURE;

	    	return;

	    }else{
		    try
		    {
		      postalVO = mailTrackingDefaultsDelegate.findPACode(companyCode, mailPerformanceForm.getIncPaCode());
		    }
		    catch (BusinessDelegateException businessDelegateException) {
		      this.log.log(6, "\n\n GPA CODE NOT EXISTS =============> \n\n");
		    }
		    if (postalVO == null) {
		    	log.log(Log.FINE, "\n\n GPA CODE NOT EXISTS =============> \n\n");
		      ErrorVO error = new ErrorVO(GPA_NOT_EXISTS);
		      errors.add(error);
		      invocationContext.addAllError(errors);
		      mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
		      mailPerformanceForm.setStatusFlag("List_failure");
		      invocationContext.target = FAILURE;
		      return;
		    }
	    }

	    try{

	    	incentiveConfigurationVOs =  (ArrayList<IncentiveConfigurationVO>)mailTrackingDefaultsDelegate.findIncentiveConfigurationDetails(incentiveConfigurationFilterVO);

	    }catch(BusinessDelegateException businessDelegateException) {

	    	errors = handleDelegateException(businessDelegateException);
	    }

	    if(incentiveConfigurationVOs == null || incentiveConfigurationVOs.size() == 0 ){
	    	ErrorVO error = new ErrorVO(NO_RESULTS_FOUND,new Object[]{""});
			error.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(error);
			invocationContext.addAllError(errors);
			mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
			mailPerformanceForm.setStatusFlag("List_fail_NoRecords");
			mailPerformanceForm.setIncFlag("Y");
			mailPerformanceForm.setServiceResponsiveFlag("Y");
			mailPerformanceSession.setPaCode((String)mailPerformanceForm.getIncPaCode());
			mailPerformanceSession.setParameterVO(null);
			invocationContext.target = FAILURE;
			return;
	    }
	    
	    if(incentiveConfigurationVOs!= null && incentiveConfigurationVOs.size()>0){
	    	ArrayList<IncentiveConfigurationVO> newConfigurationVOs = new ArrayList<IncentiveConfigurationVO>();
	    	for(IncentiveConfigurationVO configVO : incentiveConfigurationVOs){
	    		
	    		if("N".equals(configVO.getIncentiveFlag())){
	    			if("Y".equals(configVO.getServiceRespFlag())){
	    				configVO.setDisIncSrvPercentage(configVO.getDisIncPercentage());
	    				configVO.setDisIncSrvValidFrom(configVO.getDisIncValidFrom());
	    				configVO.setDisIncSrvValidTo(configVO.getDisIncValidTo());
	    				configVO.setSrvBasis(configVO.getBasis());
	    				configVO.setSrvFormula(configVO.getFormula());
	    				
	    				Collection<IncentiveConfigurationDetailVO> detailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
	    				for(IncentiveConfigurationDetailVO detailVO : configVO.getIncentiveConfigurationDetailVOs()){
	    					detailVO.setDisIncSrvParameterCode(detailVO.getDisIncParameterCode());
	    					detailVO.setDisIncSrvParameterType(detailVO.getDisIncParameterType());
	    					detailVO.setDisIncSrvParameterValue(detailVO.getDisIncParameterValue());
	    					detailVO.setSrvExcludeFlag(detailVO.getExcludeFlag());
	    					
	    					detailVOs.add(detailVO);
	    				}
	    				configVO.setIncentiveConfigurationDetailVOs(detailVOs);
	    				
	    			}else if("N".equals(configVO.getServiceRespFlag())){
	    				
	    				configVO.setDisIncNonSrvPercentage(configVO.getDisIncPercentage());
	    				configVO.setDisIncNonSrvValidFrom(configVO.getDisIncValidFrom());
	    				configVO.setDisIncNonSrvValidTo(configVO.getDisIncValidTo());
	    				configVO.setNonSrvBasis(configVO.getBasis());
	    				configVO.setNonSrvFormula(configVO.getFormula());
	    				
	    				Collection<IncentiveConfigurationDetailVO> detailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
	    				for(IncentiveConfigurationDetailVO detailVO : configVO.getIncentiveConfigurationDetailVOs()){
	    					detailVO.setDisIncNonSrvParameterCode(detailVO.getDisIncParameterCode());
	    					detailVO.setDisIncNonSrvParameterType(detailVO.getDisIncParameterType());
	    					detailVO.setDisIncNonSrvParameterValue(detailVO.getDisIncParameterValue());
	    					detailVO.setNonSrvExcludeFlag(detailVO.getExcludeFlag());
	    					
	    					detailVOs.add(detailVO);
	    				}
	    				configVO.setIncentiveConfigurationDetailVOs(detailVOs);
	    				
	    			}else if("B".equals(configVO.getServiceRespFlag())){
	    				
	    				configVO.setDisIncBothSrvPercentage(configVO.getDisIncPercentage());
	    				configVO.setDisIncBothSrvValidFrom(configVO.getDisIncValidFrom());
	    				configVO.setDisIncBothSrvValidTo(configVO.getDisIncValidTo());
	    				configVO.setBothSrvBasis(configVO.getBasis());
	    				configVO.setBothSrvFormula(configVO.getFormula());
	    				
	    				Collection<IncentiveConfigurationDetailVO> detailVOs = new ArrayList<IncentiveConfigurationDetailVO>();
	    				for(IncentiveConfigurationDetailVO detailVO : configVO.getIncentiveConfigurationDetailVOs()){
	    					detailVO.setDisIncBothSrvParameterCode(detailVO.getDisIncParameterCode());
	    					detailVO.setDisIncBothSrvParameterType(detailVO.getDisIncParameterType());
	    					detailVO.setDisIncBothSrvParameterValue(detailVO.getDisIncParameterValue());
	    					detailVO.setBothSrvExcludeFlag(detailVO.getExcludeFlag());
	    					
	    					detailVOs.add(detailVO);
	    				}
	    				configVO.setIncentiveConfigurationDetailVOs(detailVOs);
	    				
	    			}
	    			newConfigurationVOs.add(configVO);
	    		}else{
	    			newConfigurationVOs.add(configVO);
	    		}
	    	}
	    	mailPerformanceSession.setTotalRecords(incentiveConfigurationVOs.size());
			mailPerformanceSession.setIncentiveConfigurationVOs(newConfigurationVOs);
		    mailPerformanceForm.setScreenFlag("incentiveRadioBtn");
		    mailPerformanceForm.setIncFlag("Y");
		    mailPerformanceForm.setServiceResponsiveFlag("Y");
		    mailPerformanceForm.setStatusFlag("List_success");
		    mailPerformanceSession.setPaCode((String)mailPerformanceForm.getIncPaCode());
		    mailPerformanceSession.setParameterVO(null);
		    invocationContext.target = SUCCESS;
	    }
	}
}
