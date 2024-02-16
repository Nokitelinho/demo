/*
 * DeleteCustomerGroupCommand.java  Created on May 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.customergroup.listcustomergroup;



import java.util.ArrayList;
import java.util.Collection;


import com.ibsplc.icargo.business.shared.customer.vo.CustomerGroupVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.customergroup.ListCustomerGroupSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.customergroup.ListCustomerGroupForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This command class is invoked for deleting customergroup
 *
 * @author A-2122
 */
public class DeleteCustomerGroupCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("LIST CUSTOMER GROUP");
	private static final String MODULE_NAME = "customermanagement.defaultsr";

	 private static final String SCREEN_ID = 
			"customermanagement.defaults.listcustomergroup";

    private static final String DELETE_SUCCESS = "delete_success";
    private static final String DELETE_FAILURE = "delete_failure";

    /**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 * @return 
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	invocationContext.target = DELETE_SUCCESS;
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		
		ListCustomerGroupForm listCustomerGroupForm = 
			(ListCustomerGroupForm) invocationContext.screenModel;
        ListCustomerGroupSession listCustomerGroupSession = 
			getScreenSession(MODULE_NAME, SCREEN_ID);
		Page<CustomerGroupVO>  customerGroupVOs=
			listCustomerGroupSession.getCustomerGroupVO();
		 Collection<CustomerGroupVO> customerGroupColl=
			 				new ArrayList<CustomerGroupVO>();
    	if(listCustomerGroupForm.getSelect() != null &&
    			listCustomerGroupForm.getSelect().length > 0) {
    		String select[] = listCustomerGroupForm.getSelect();
    		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    		
	    	for(int i = select.length - 1; i >= 0; i--) {
	    		CustomerGroupVO customerGroupVO = 
	    								new CustomerGroupVO();
	    		customerGroupVO.setOperationFlag(
	    				CustomerGroupVO.OPERATION_FLAG_DELETE);
	    		customerGroupVO.setCompanyCode(
	    							logonAttributes.getCompanyCode());
	    		customerGroupVO.setGroupCode(
	    				customerGroupVOs.get(Integer.parseInt(select[i])).
	    									getGroupCode().toUpperCase());
	    		customerGroupVO.setGroupName(
	    				customerGroupVOs.get(Integer.parseInt
	    						(select[i])).getGroupName().toUpperCase());
	    		customerGroupVO.setStationCode(customerGroupVOs.get(Integer.parseInt
	    						(select[i])).getStationCode().toUpperCase());
	    		customerGroupVO.setLoyaltyGroupCode(customerGroupVOs.get(Integer.parseInt
	    				(select[i])).getLoyaltyGroupCode());
	    		customerGroupVO.setPointsAccruded(customerGroupVOs.get(Integer.parseInt
	    				(select[i])).getPointsAccruded());
	    		customerGroupVO.setGroupRemarks(customerGroupVOs.get(Integer.parseInt
	    				(select[i])).getGroupRemarks());
	    		customerGroupColl.add(customerGroupVO);
    			log.log(Log.FINE, "Inside delete CustomerGroupVO------>>>",
						customerGroupVO);
        	  	}
	    	
    		try {
				new CustomerMgmntDefaultsDelegate().deleteCustomerGroups(customerGroupColl);
			}
			catch(BusinessDelegateException businessDelegateException) {
				errors = 
						handleDelegateException(businessDelegateException);
			}
	    		
			if(errors != null &&
	    			errors.size() > 0 ) {
					invocationContext.addAllError(errors);
	    			invocationContext.target = DELETE_FAILURE;
	    			return;
	    	}
    		for(int i = select.length - 1; i >= 0; i--) {
    			customerGroupVOs.remove(customerGroupVOs.get(
						Integer.parseInt(select[i])));
    		
    		}
	    	
    	}
    	
    	invocationContext.target = DELETE_SUCCESS;	

    }

}

