package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.AttachLoyaltyProgrammeVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ListLoyaltyDetailsCommand extends BaseCommand {

	private static final String LIST_SUCCESS = "list_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	/**
	 * @param invocationContext
	 * @throws  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");

		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		Collection<AttachLoyaltyProgrammeVO> loyaltyVOs = null;

		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		log.log(Log.FINE, "\n\n\ncustomer code from form---->"
				+ form.getCustomerCodeChild());
		String customerCode = form.getCustomerCodeChild().toUpperCase();

		String groupCode = BLANK;
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		for (CustomerVO customerVO : customerVOs) {
			if (customerCode.equals(customerVO.getCustomerCode())) {
				groupCode = customerVO.getCustomerGroup();
			}
		}
		log.log(Log.FINE, "\n\n\ngroup flag --------------->" + groupCode);
		try {
			loyaltyVOs = delegate.listLoyaltyPgmToCustomers(companyCode,
					customerCode, groupCode);
		} catch (BusinessDelegateException ex) {
//printStackTrrace()();
			handleDelegateException(ex);
		}
		log.log(Log.FINE, "loyaltyvos from server-------->" + loyaltyVOs);
		if (loyaltyVOs != null) {
			session
					.setLoyaltyVOs((ArrayList<AttachLoyaltyProgrammeVO>) loyaltyVOs);
		}

		invocationContext.target = LIST_SUCCESS;

	}
}
