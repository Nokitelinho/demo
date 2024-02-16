
/*
 * ListTempCustRegCommand.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1496
 *
 */
public class ListTempCustRegCommand  extends BaseCommand {

    private static final String LIST_SUCCESS = "list_success";
    private static final String LIST_FAILURE = "list_failure";
    private static final String MODULE = "customermanagement.defaults";
    
	private static final String SCREENID = "customermanagement.defaults.maintaintempcustomerform";
	
	private Log log = LogFactory.getLogger("ListTempCustRegCommand");

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	        ApplicationSessionImpl applicationSession = getApplicationSession();
    			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
    			MaintainTempCustomerForm form = 
    				(MaintainTempCustomerForm) invocationContext.screenModel;
    			ListtempCustomerSession session =
    	    		getScreenSession(MODULE, SCREENID);
    			String companyCode = logonAttributes.getCompanyCode();
    			
    			CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();		
    			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    			
    			log.log(Log.INFO, "@@form.getCloseFlag()@@", form.getCloseFlag());
				ArrayList<String> tempIDs = session.getTempIDs();
    	    	log.log(Log.FINE, "tempIDs from session", tempIDs);
				String tempIDFromSession = "";
    	    	log
						.log(
								Log.FINE,
								"\n\n\ncurreny display page value is------------------->",
								form.getDisplayPage());
				if(tempIDs != null){
    	    		tempIDFromSession = tempIDs.get(Integer.parseInt(form.getDisplayPage())-1);
    	    		log.log(Log.INFO,
							"@@@@@@@@@@@@@@tempIDFromSession@@@@@@@@@@@",
							tempIDFromSession);
    	    	}
    	    	if("listtempcustomerform".equals(session.getPageURL()))
				{
    	    		log.log(log.FINE,"INSIDE SESSION IN LIST");
					form.setTempId(tempIDFromSession);
					form.setPageURL(session.getPageURL());
					form.setSaveFlag(false);
					
				}
				
    	    	
    			TempCustomerVO tempCustomerVo = new TempCustomerVO();
    			log.log(Log.INFO, "form.getTempId()------------------>", form.getTempId());
					if(!"".equals(form.getTempId()) && form.getTempId().trim().length()>0){
    				String tempID = form.getTempId().toUpperCase();		
    					try {
    						log.log(log.FINE,"before setting to delegate--------------->");
    						log.log(Log.INFO, "companyCode------------------>",
									companyCode);
							log.log(Log.INFO, "tempID------------------>",
									tempID);
							tempCustomerVo = delegate.listTempCustomer(companyCode,tempID);
    						log
									.log(
											Log.INFO,
											"@@@@@@@@@@@@@@@@@@@@@@@tempCustomerVo after delegate call is------------>",
											tempCustomerVo);
    			
    					}catch(BusinessDelegateException e) {
//printStackTrrace()();
    						handleDelegateException(e);
    						}	
    					log
								.log(
										Log.INFO,
										"@@@@@@@@@@@@@@@@@@@@@@@tempCustomerVo is------------>",
										tempCustomerVo);
						if(tempCustomerVo!=null){
    					session.setTempCustomerDetails(tempCustomerVo);
    					if(("I".equals(tempCustomerVo.getActiveStatus()) 
    							&& tempCustomerVo.getCustomerCode()!=null
    							&& tempCustomerVo.getCustomerCode().length()>0) ||
    							("B".equals(tempCustomerVo.getActiveStatus()) 
    							&& tempCustomerVo.getCustomerCode()!=null
    							&& tempCustomerVo.getCustomerCode().length()>0)	)
    					{
    						form.setCustCodeFlag("ISPRESENT");
    					}
    					else
    					{
    						form.setCustCodeFlag("");
    					}
    					log.log(Log.INFO, "***********************", session.getListTempCustomerDetails());
    					}else{
    						ErrorVO error  = new ErrorVO("customermanagement.defaults.listtempcustreg.msg.err.nomorerecordsfound");
    	    				error.setErrorDisplayType(ErrorDisplayType.ERROR);
    	    				errors.add(error);	    				
    	    				invocationContext.addAllError(errors);
    	    				session.removeTempCustomerDetails();
    	    				session.removeListtempcustomerregistration();    	    			
    	    				invocationContext.target = LIST_FAILURE;
    	    				return;
    					}
    					
    			}else if(!"".equals(tempIDFromSession) && tempIDFromSession.trim().length()>0){
    				
    				log.log(Log.INFO,
							"$$$$$$$$$$$$$tempIDFromSession$$$$$$$$$$$$$$$",
							tempIDFromSession);
							try {
						log.log(log.FINE,"before setting to delegate--------------->");
						log.log(Log.INFO, "companyCode------------------>",
								companyCode);
						log.log(Log.INFO,
								"tempIDFromSession------------------>",
								tempIDFromSession);
						tempCustomerVo = delegate.listTempCustomer(companyCode,tempIDFromSession);
						log.log(Log.INFO, "TEMPID from listed collection", form.getTempId());
						log
								.log(
										Log.INFO,
										"@@@@@@@@@@@@@@@@@@@@@@@tempCustomerVo after delegate call is------------>",
										tempCustomerVo);
			
					}catch(BusinessDelegateException e) {
//printStackTrrace()();
						handleDelegateException(e );
					}	
					log
							.log(
									Log.INFO,
									"@@@@@@@@@@@@@@@@@@@@@@@tempCustomerVo is------------>",
									tempCustomerVo);
					if(tempCustomerVo!=null){
					session.setTempCustomerDetails(tempCustomerVo);
					log.log(Log.INFO, "***********************", session.getListTempCustomerDetails());
					}else{
						ErrorVO error  = new ErrorVO("customermanagement.defaults.listtempcustreg.msg.err.nomorerecordsfound");
	    				error.setErrorDisplayType(ErrorDisplayType.ERROR);
	    				errors.add(error);	    				
	    				invocationContext.addAllError(errors);
	    				session.removeTempCustomerDetails();
	    				session.removeListtempcustomerregistration();    	
	    				invocationContext.target = LIST_FAILURE;
	    				return;
					}
    			}
    					else {
    				
    				ErrorVO error  = new ErrorVO("customermanagement.defaults.listtempcustreg.msg.err.tempidismandatory");
    					error.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors.add(error);    					
    					invocationContext.addAllError(errors);
    					invocationContext.target = LIST_FAILURE;
    					return;
    				}
    			
    			/*if(session.getListTempCustomerDetails()==null ){
    				ErrorVO error  = new ErrorVO("customermanagement.defaults.listtempcustreg.msg.err.nomorerecordsfound");
    				error.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(error);	    				
    				invocationContext.addAllError(errors);
    				invocationContext.target = LIST_FAILURE;
    				return;
    			}*/
    			
    			if(errors!=null && errors.size() > 0) {
    				session.removeListTempCustomerDetails();    				
    				invocationContext.addAllError(errors);
    				invocationContext.target = LIST_FAILURE;
    				return;
    			}else {
    				form.setOperationMode("update");
    				invocationContext.target=LIST_SUCCESS;
    			}
    	    
    	


    }


}
