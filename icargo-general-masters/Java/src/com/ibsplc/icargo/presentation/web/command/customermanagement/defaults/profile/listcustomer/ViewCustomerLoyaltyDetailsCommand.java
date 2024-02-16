package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;

import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListCustomerPointsForm;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ViewCustomerLoyaltyDetailsCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

	private static final String SCRREN_ID = "customermanagement.defaults.customerlisting";

	private static final String LIST_SUCCESS = "list_success";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customermanagemt");
		ListCustomerPointsForm form = (ListCustomerPointsForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCRREN_ID);
		ListCustomerPointsSession listPointsSession = getScreenSession(
				MODULENAME, SCREENID);
		String[] checked = form.getCheck();
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		ArrayList<String> custCodes = new ArrayList<String>();
		ArrayList<String> selectedCustCodes = new ArrayList<String>();
		for (CustomerVO custVO : customerVOs) {
			custCodes.add(custVO.getCustomerCode());
		}

		for (int i = 0; i < checked.length; i++) {
			selectedCustCodes.add(custCodes.get(Integer.parseInt(checked[i])));
		}
		log.log(Log.FINE, "selected customer codes are----->"
				+ selectedCustCodes);
		form.setCustomerCode(selectedCustCodes.get(0));
		listPointsSession.setSelectedCustomerCodes(selectedCustCodes);
		form.setFromListing("Y");
		form.setDispPageNum("1");
		invocationContext.target = LIST_SUCCESS;
	}
}
