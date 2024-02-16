package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListCustomerPointsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class NavigateCustomerLoyaltiesCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

	private static final String ACTION_SUCCESS = "list_success";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customer", "change customer status command");
		ListCustomerPointsForm form = (ListCustomerPointsForm) invocationContext.screenModel;
		ListCustomerPointsSession listPointsSession = getScreenSession(
				MODULENAME, SCREENID);
		ArrayList<String> customerCodes = listPointsSession
				.getSelectedCustomerCodes();
		form.setCustomerCode(customerCodes.get(Integer.parseInt(form
				.getDispPageNum()) - 1));
		invocationContext.target = ACTION_SUCCESS;

	}
}
