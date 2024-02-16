/*
 * SaveCustomerGroupCommand.java Created on May 11, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.maintaincustomergroup;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.area.vo.AreaValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.CustomerGroupSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.CustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 * This command class is used to save the details of the 
 * specified customergroup
 * 
 * @author A-2122
 */
public class SaveCustomerGroupCommand extends BaseCommand {
    
	
	private Log log = LogFactory.getLogger("MAINTAIN CUSTOMER GROUP");	
	private static final String MODULE_NAME = "customermanagement.defaults";
	private static final String SCREEN_ID = 
								"customermanagement.defaults.customergroup";
	private static final String MODULELIST_NAME = "customermanagement.defaults";
	private static final String SCREENLIST_ID = 
								"customermanagement.defaults.listcustomergroup";
    private static final String SAVE_SUCCESS = "save_success";
    private static final String SAVE_FAILURE = "save_failure";
    private static final String LIST_FORWARD = "list_forward";
    
    
    private static final String SAVE = "U";
    private static final String INSERT="I";
   
   /**
    * 
    */ 
     /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveCustomerGroupCommand", "execute"); 
    	CustomerGroupForm customerGroupForm = 
			(CustomerGroupForm)invocationContext.screenModel; 
    	
    	CustomerGroupSession customerGroupSession = 
    		(CustomerGroupSession ) getScreenSession(MODULE_NAME, SCREEN_ID);
    	
	    ApplicationSessionImpl applicationSession = getApplicationSession();
	    LogonAttributes logonAttributes = applicationSession.getLogonVO();
	    Collection<ErrorVO>  errors =  new ArrayList<ErrorVO>();
    	String lastupdateuser=logonAttributes.getUserId();
    	String companyCode=logonAttributes.getCompanyCode().toUpperCase();
    	
    	ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULELIST_NAME,SCREENLIST_ID);
    	
    	CustomerGroupVO customerGroupVO = null;
    	if(customerGroupSession.getCustomerGroupVO() != null){
    		customerGroupVO =
        		customerGroupSession.getCustomerGroupVO();
    	}
    	else{
    		customerGroupVO = new CustomerGroupVO();
    	}
    	
    	log.log(Log.FINE, "CustomerGroupVO from session-->", customerGroupVO);
		errors=validateForm(customerGroupForm,companyCode);  
    	log.log(Log.FINE, "Status Flag inside save", customerGroupForm.getStatusFlag());
	if(customerGroupForm.getStatusFlag().equals(SAVE)){
        	customerGroupVO.setOperationFlag(CustomerGroupVO.OPERATION_FLAG_UPDATE);
    		log.log(Log.FINE, "OpnFlaginside save", customerGroupVO.getOperationFlag());
			customerGroupForm.setScreenModifiedFlag("SCREEN_MODIFIED");
    	}  
        else if(customerGroupForm.getStatusFlag().equals(INSERT)){
        	
        	customerGroupVO.setOperationFlag(CustomerGroupVO.OPERATION_FLAG_INSERT);
        	log.log(Log.FINE, "OperationFlag------>inside save",
					customerGroupVO.getOperationFlag());
			customerGroupForm.setScreenModifiedFlag("");
         }
    	 
       		customerGroupVO.setGroupName(customerGroupForm.getGroupName().toUpperCase());
    		customerGroupVO.setStationCode(customerGroupForm.getStation().toUpperCase());
    		//customerGroupVO.setLoyaltyGroupCode(customerGroupForm.getGroupCode());    			
			customerGroupVO.setGroupRemarks(customerGroupForm.getRemarks());
			customerGroupVO.setLastUpdatedUser(lastupdateuser);
    		customerGroupVO.setCompanyCode(companyCode);
    		log.log(Log.FINE,"SaveCustomerGroupCommand..after setting vo");
    		log.log(Log.FINE, "customerGroupVO--->>>>", customerGroupVO);
			customerGroupSession.setCustomerGroupVO(customerGroupVO);
    	    Collection<CustomerGroupVO> customerGroupVOs = new ArrayList<CustomerGroupVO>();
    	if (errors != null && errors.size() > 0) {	

          	 //log.log(Log.INFO,"invocationCtxt.target:"+invocationContext.target);
          	 invocationContext.addAllError(errors);
         	 invocationContext.target = SAVE_FAILURE;
         	 return;
          
      	}else{
      			CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
      			try{
	    		customerGroupVOs.add(customerGroupVO);
	    		String groupCode = customerDelegate.saveCustomerGroupDetails(customerGroupVOs);
	    		if(groupCode != null){
	    			Object[] obj = {groupCode};
	    			ErrorVO err = new ErrorVO("customermanagement.defaults.customerGroup.groupCode", obj);
					err.setErrorDisplayType(ErrorDisplayType.INFO);
					errors.add(err);
					invocationContext.addAllError(errors);
					customerGroupSession.setCustomerGroupVO(null);
					invocationContext.target = SAVE_SUCCESS; 
					return;
	    		}
	    		customerGroupSession.setCustomerGroupVO(null);
	    		customerGroupForm.setStatusFlag("I");
				invocationContext.target = SAVE_SUCCESS;
		    	}catch(BusinessDelegateException businessDelegateException){
		    		errors = handleDelegateException(businessDelegateException);
		    	}
		    	if (errors != null && errors.size() > 0) {	
					invocationContext.addAllError(errors);
		          	invocationContext.target = SAVE_FAILURE;
		          	return;
		    	}
      	}
    	   	
    	
    	Collection<CustomerGroupVO> customerGroupvos=null;
    	if("From List".equals(customerGroupForm.getDetailsFlag())){
   		listCustomerGroupSession.setListStatus("noListForm");
    	if(customerGroupSession.getCustomerGroupVOMap() != null && 
    			customerGroupSession.getCustomerGroupVOMap().values()!= null && 
    			customerGroupSession.getCustomerGroupVOMap().values().size()>0){
    		customerGroupvos=customerGroupSession.getCustomerGroupVOMap().values();
    	}
    	
   	
    	log.log(Log.FINE, "Values inside session --->", customerGroupvos);
		if(customerGroupvos != null && customerGroupvos.size() > 0){
	   	for(CustomerGroupVO customerGroupvo:customerGroupvos)
	   			{
	   			customerGroupvo.setOperationFlag(CustomerGroupVO.OPERATION_FLAG_UPDATE);
	    		}
	    			
	  		}
	    		log.log(Log.FINE, "Collection inside command-------->",
						customerGroupvos);
			Collection<ErrorVO> onSaveErrors = null;
	    		try{
	    			new CustomerMgmntDefaultsDelegate().saveCustomerGroupDetails(customerGroupvos);
	    		}catch(BusinessDelegateException businessDelegateException){
	   			      onSaveErrors = handleDelegateException(businessDelegateException);		
	   		}
	        	invocationContext.target = LIST_FORWARD; 
                
                /* ****** code added for showing save confirmation message starts ****** */
                if(onSaveErrors == null ){
                    log.log(Log.FINE, "@@@@@@@@@@@@@@@@@ data saved successfully ");
                    customerGroupForm.setSaveSuccessFlag(SAVE_SUCCESS);
                }
                /* ****** code added for showing save confirmation message starts ****** */
                
	   	}

    	log.exiting("SaveCustomerGroupCommand","execute");
    }
    /**
     * 
     * @param customerGroupForm
     * @param companyCode
     * @return
     */
    public Collection<ErrorVO> validateForm
		(CustomerGroupForm customerGroupForm,String companyCode){
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	log.entering("SaveCustomerGroupCommand","validateForm");
    	ErrorVO error = null;
    	if(customerGroupForm.getGroupName()==null ||
    			customerGroupForm.getGroupName().trim().length()==0){
    		error = new ErrorVO
    		("customermanagement.defaults.customergroup.msg.err.groupname.mandatory");
    		 error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		 errors.add(error);
    	}
    	
    	if(customerGroupForm.getStation() == null || customerGroupForm.getStation().trim().length()==0){
    		error = new ErrorVO
    		("customermanagement.defaults.customergroup.msg.err.station.mandatory");
    		 error.setErrorDisplayType(ErrorDisplayType.ERROR);
    		 errors.add(error);
    	}
    	else{
    		AreaValidationVO areaValidationVO = null; 
        	try {
        		
    			AreaDelegate delegate = new AreaDelegate();
    			areaValidationVO = delegate.validateLevel(companyCode,"STN",customerGroupForm.getStation().toUpperCase());			
    			log.log(Log.FINE,
						"!!!!!!!!!!!!areaValidationVO!!!!!!!!!!!!!!!!!",
						areaValidationVO);
        	} catch (BusinessDelegateException e) {
        		log.log(Log.INFO," errors occur in validating station ");
        		Collection<ErrorVO> stationErrors = null;
        		stationErrors = handleDelegateException(e);
        		errors.addAll(stationErrors);
    		}
    	}
    	/*if(customerGroupForm.getGroupCode()!= null && customerGroupForm.getGroupCode().trim().length()>0){
    		Collection<CustomerLoyaltyGroupVO> customerVos = null;
    		CustomerMgmntDefaultsDelegate customerDelegate = new CustomerMgmntDefaultsDelegate();
    		try {
    			customerVos=customerDelegate.listCustomerGroup(companyCode,customerGroupForm.getGroupCode().toUpperCase());
    			}catch (BusinessDelegateException e) {
    				log.log(Log.SEVERE," invalid loyalty Code found ");
    				Collection<ErrorVO> loyaltyErrors = null;
    				loyaltyErrors = handleDelegateException(e);
    				errors.addAll(loyaltyErrors);
    			}	
    			if(customerVos == null || customerVos.size() == 0){
    				error = new ErrorVO("customermanagement.defaults.customergroup.msg.err.invalidloyaltycode");
    				error.setErrorDisplayType(ErrorDisplayType.ERROR);
    	    		errors.add(error);
    			}
    	}*/
    	log.exiting("SaveCustomerGroupCommand","validateForm");
    	return errors;
    }   
    
  }

