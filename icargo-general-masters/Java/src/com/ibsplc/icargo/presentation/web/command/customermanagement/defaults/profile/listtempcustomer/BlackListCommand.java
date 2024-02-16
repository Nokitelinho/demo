package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.shared.customer.vo.TempCustomerVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.customermanagement.defaults.CustomerMgmntDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2135
 * 
 */

public class BlackListCommand extends BaseCommand {

	private static final String MODULE = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";

	private static final String BLACKLIST_SUCCESS = "blacklist_success";

	private static final String REVOKE = "revoke";

	/**
	 * execute method
	 * 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {

		Log log = LogFactory.getLogger("customermanagement");

		ListtempCustomerSession listtempCustomerSession = getScreenSession(
				MODULE, SCREENID);
		ListTempCustomerForm listTempCustomerForm = (ListTempCustomerForm) invocationContext.screenModel;

		Collection<TempCustomerVO> tempCustomerVOs = listtempCustomerSession
				.getListCustomerRegistration();
		ArrayList<TempCustomerVO> tempCust = new ArrayList<TempCustomerVO>();
		ArrayList<TempCustomerVO> selectedTempCust = new ArrayList<TempCustomerVO>();

		String[] checked = listTempCustomerForm.getRowId();

		for (TempCustomerVO tempCustomerVO : tempCustomerVOs) {

			tempCust.add(tempCustomerVO);
		}

		for (int i = 0; i < checked.length; i++) {

			selectedTempCust.add(tempCust.get(Integer.parseInt(checked[i])));

		}

		for (TempCustomerVO tempCustomerVO : selectedTempCust) {
			if (REVOKE.equals(listTempCustomerForm.getFlag())) {
				tempCustomerVO.setActiveStatus("A");
			} else {
				tempCustomerVO.setActiveStatus("B");
			}
			tempCustomerVO.setOperationalFlag(AbstractVO.OPERATION_FLAG_UPDATE);

		}
		log.log(Log.FINE, "selectedTempCust-----" + selectedTempCust);

		CustomerMgmntDefaultsDelegate delegate = new CustomerMgmntDefaultsDelegate();
		try {
			delegate.saveTempCustomer(selectedTempCust);
		} catch (BusinessDelegateException e) {
//printStackTrrace()();
			handleDelegateException( e);
		}

		invocationContext.target = BLACKLIST_SUCCESS;
	}
}