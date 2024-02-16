package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class NavigateCustomerDetailsCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";

	private static final String ACTION_SUCCESS = "list_success";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customer", "change customer status command");
		MaintainRegCustomerForm form = (MaintainRegCustomerForm) invocationContext.screenModel;
		MaintainCustomerRegistrationSession maintainSession = getScreenSession(
				MODULENAME, SCREEN_ID);
		ArrayList<String> customerCodes = maintainSession
				.getCustomerCodesFromListing();
		form.setCustomerCode(customerCodes.get(Integer.parseInt(form
				.getDisplayPage()) - 1));
		form.setFromCustListing("Y");
		invocationContext.target = ACTION_SUCCESS;

	}
}
