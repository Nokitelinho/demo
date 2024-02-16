/*
 * Created on : 21-Dec-2021 
 * Name       : ClearGroupDetailsCommand.java
 * Created by : A-2569
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * 
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListGroupDetailsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearGroupDetailsCommand extends BaseCommand{

	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";
	private static final String SUCCESS="groupdetails_success";

	
	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("ActivatingCustomer");
		log.entering("ClearGroupDetailsCommand","");
		ListGroupDetailsForm form = (ListGroupDetailsForm)invocationContext.screenModel;
		MaintainCustomerRegistrationSession session = getScreenSession(MODULENAME,SCREENID);
		form.setGroupType("");
		form.setGroupName("");
		form.setGroupDescription("");
		form.setCategory("");
		session.removeCustomerGroupDetails();
		invocationContext.target = SUCCESS;  
		return;
	}
	

}
