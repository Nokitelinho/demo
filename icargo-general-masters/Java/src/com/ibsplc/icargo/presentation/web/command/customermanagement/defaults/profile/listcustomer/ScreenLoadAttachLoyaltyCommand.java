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
public class ScreenLoadAttachLoyaltyCommand extends BaseCommand {
	private static final String SCREENLOAD_SUCCESS = "screenload_success";

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO()
				.getCompanyCode();
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		Page<CustomerVO> customerVOs = session.getCustomerVOs();
		log.log(Log.FINE, "customer vos ------------->" + customerVOs);
		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		String[] checked = form.getCheck();
		if (checked != null) {
			log.log(Log.FINE, "checked length-------->" + checked.length);
			log.log(Log.FINE, "checked[0]------>" + checked[0]);
			String customerCode = "";
			String groupCode = "";
			String customerName = "";
			if (checked != null) {
				customerCode = customerVOs.get(Integer.parseInt(checked[0]))
						.getCustomerCode();
			}
			log.log(Log.FINE, "selected customer code-------------->"
					+ customerCode);
			for (CustomerVO customerVO : customerVOs) {
				if (customerVO.getCustomerCode().equals(customerCode)) {
					log.log(Log.FINE, "selected vo----------------->" + customerVO);

					groupCode = customerVO.getCustomerGroup();
					customerName = customerVO.getCustomerName();
				}
			}
			form.setCustomerCodeChild(customerCode);
			form.setCustName(customerName);
			log.log(Log.FINE, "selected group code-------------->" + groupCode);
			log.log(Log.FINE, "selected customer name-------------->"
					+ customerName);
			Collection<AttachLoyaltyProgrammeVO> loyaltyVOs = null;
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
		}
		invocationContext.target = SCREENLOAD_SUCCESS;
	}

}
