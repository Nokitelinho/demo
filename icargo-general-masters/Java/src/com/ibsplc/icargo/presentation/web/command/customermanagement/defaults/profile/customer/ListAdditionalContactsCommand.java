/*
 * Customer.java Created on May 20, 2016
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.AdditionalContactVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerContactVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ListAdditionalContactsCommand extends BaseCommand{
	
	
	private static final String MODULE = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";
    private static final String LIST_SUCCESS = "list_success";
    private Log log = LogFactory.getLogger("CUSTOMERMANAGEMENT");
    
	public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
		MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	MaintainCustomerRegistrationSession session = getScreenSession(MODULE,SCREEN_ID);
    	Collection<AdditionalContactVO> additionalContactVOs = null;
    	int selectedIndex = Integer.parseInt(form.getSelectedContactIndex());
		
    	if(session.getCustomerVO() !=null
				 && session.getCustomerVO().getCustomerContactVOs() != null) {
			ArrayList<CustomerContactVO> customerContactVOs = (ArrayList<CustomerContactVO>) session.getCustomerVO().getCustomerContactVOs();
			if(selectedIndex <= (customerContactVOs.size()-1)) {
				additionalContactVOs = customerContactVOs.get(selectedIndex).getAdditionalContacts();
			}
		}
    	if(additionalContactVOs != null && additionalContactVOs.size()>0) {
				session.setAdditionalContacts(new ArrayList<AdditionalContactVO>(additionalContactVOs));
			}
		else {
			session.setAdditionalContacts(null);
		}
    	
    	invocationContext.target=LIST_SUCCESS;
		
	}

}
