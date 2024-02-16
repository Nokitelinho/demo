/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ScreenLoadCommand.java
 *
 *	Created by	:	A-4809
 *	Created on	:	Dec 17, 2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.SecurityAgent;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.mra.common.MailMRAModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.mra.gpabilling.GPABillingEnquiryModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.ux.gpabillingenquiry.ScreenLoadCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	Dec 17, 2018	:	Draft
 */
public class ScreenLoadCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("Mail Mra Invoic enquiry ");
	
	private static final String MODULE_NAME = "mail.mra";
	private static final String CLASS_NAME="ScreenLoadCommand";
	private static final String SCREENID = "mail.mra.gpabilling.ux.gpabillingenquiry";
	
	/**One Times**/
	private static final String KEY_BILLING_TYPE_ONETIME = "mailtracking.mra.gpabilling.gpabillingstatus";
	private static final String KEY_CATEGORY_ONETIME = "mailtracking.defaults.mailcategory";
	private static final String USPS_PERFORMED="mailtracking.mra.gpabilling.uspsperformed";
	private static final String RATE_BASIS="mailtracking.mra.gpabilling.ratebasis";
	// Added by A-8527 for IASCB-22915
	private static final String KEY_WGTUNITCOD_ONETIME = "mail.mra.defaults.weightunit"; 
	/**System Parameters **/
	private static final String LEVEL_FOR_DATA_IMPORT_TO_MRA="mailtracking.defaults.DsnLevelImportToMRA";
	private static final String SYS_PAR_OVERRIDE_ROUNDING = "mailtracking.mra.overrideroundingvalue";
	private static final String SYS_PAR_MAX_PAGE_FETCH = "system.defaults.maxPagefetchSize";
	
	/**Privilege **/
	private static final String PRVILIGE_CODE_AUTOMCA="mailtracking.mra.defaults.automcaprivilege";
	private static final String SYS_PAR_CONTAINERRATE_PALIST = "mailtracking.mra.PAlisttoapplycontainerrate";
	

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		this.log.entering(MODULE_NAME, CLASS_NAME);
		LogonAttributes logonAttributes = getLogonAttribute();
		String companyCode = logonAttributes.getCompanyCode();
		GPABillingEnquiryModel gpaBillingEnquiryModel = (GPABillingEnquiryModel)actionContext.getScreenModel();
	    SharedDefaultsDelegate sharedDefaultsDelegate = new SharedDefaultsDelegate();
	    Map<String, Collection<OneTimeVO>> oneTimeValues = null;
	    Map<String, String> systemParameters = null;
	    String autoMCAPrivilege = MRAConstantsVO.FLAG_NO;
	    Collection<String> parameterTypes = new ArrayList<String>();
	    parameterTypes.add(LEVEL_FOR_DATA_IMPORT_TO_MRA);
	    parameterTypes.add(SYS_PAR_OVERRIDE_ROUNDING);
		parameterTypes.add(SYS_PAR_MAX_PAGE_FETCH); 
	    parameterTypes.add(SYS_PAR_CONTAINERRATE_PALIST);
	    
	     try{
    	      	oneTimeValues = sharedDefaultsDelegate.findOneTimeValues(companyCode,getOneTimeParameterTypes());
    	        systemParameters = sharedDefaultsDelegate.findSystemParameterByCodes(parameterTypes);
    	  }catch (BusinessDelegateException e){
    	      actionContext.addAllError(handleDelegateException(e));
    	  }
	     this.log.log(5, new Object[] { "oneTimeValues ---> ", oneTimeValues });
	     this.log.log(5, new Object[] { "systemParameters ---> ", systemParameters });
	     boolean hasAutoMCAPrivilege = false ;
   		try {
   			SecurityAgent agent = SecurityAgent.getInstance();
			 hasAutoMCAPrivilege = agent.checkPrivilegeForAction(PRVILIGE_CODE_AUTOMCA);
		  } catch (SystemException e) {
			  this.log.log(6, new Object[]{"Error in security agent finding"});
		  }  
   		 if(hasAutoMCAPrivilege){
   			autoMCAPrivilege = MRAConstantsVO.FLAG_YES;
   		 }
   		 
   		 if(systemParameters!=null && systemParameters.get(SYS_PAR_CONTAINERRATE_PALIST)!=null && !systemParameters.get(SYS_PAR_CONTAINERRATE_PALIST).isEmpty()){
   			 gpaBillingEnquiryModel.setContainerRatingPAList(systemParameters.get(SYS_PAR_CONTAINERRATE_PALIST)); 
   		 }
   		 
   		 gpaBillingEnquiryModel.setMcaPrivilege(autoMCAPrivilege);
	     gpaBillingEnquiryModel.setOneTimeValues(MailMRAModelConverter.constructOneTimeValues(oneTimeValues));
	     gpaBillingEnquiryModel.setMaxPageCount(systemParameters.get(SYS_PAR_MAX_PAGE_FETCH));
	     List<GPABillingEnquiryModel> results = new ArrayList<GPABillingEnquiryModel>();
	     results.add(gpaBillingEnquiryModel);
		 ResponseVO responseVO = new ResponseVO();
		 responseVO.setStatus("success");
		 responseVO.setResults(results); 
		 actionContext.setResponseVO(responseVO);
		this.log.exiting(MODULE_NAME, CLASS_NAME);
		
	}
	
	/**
	 * 	Method		:	ScreenLoadCommand.getOneTimeParameterTypes
	 *	Added by 	:	A-4809 on Dec 27, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<String>
	 */
	  private Collection<String> getOneTimeParameterTypes(){
	    Collection<String> parameterTypes = new ArrayList();
	    parameterTypes.add(KEY_BILLING_TYPE_ONETIME);
	    parameterTypes.add(KEY_CATEGORY_ONETIME);
	    parameterTypes.add(USPS_PERFORMED);
	    parameterTypes.add(RATE_BASIS);
	  //Added by A-8527 for IASCB-22915
	    parameterTypes.add(KEY_WGTUNITCOD_ONETIME);
	    return parameterTypes;
	  }
}
