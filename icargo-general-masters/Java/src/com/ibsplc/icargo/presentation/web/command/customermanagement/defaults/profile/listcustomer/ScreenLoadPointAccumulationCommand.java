package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2046
 * 
 */
public class ScreenLoadPointAccumulationCommand extends BaseCommand {
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
		log.entering("customer", "point accumulation screenload");
		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		String[] checked = form.getCheck();
		if (checked != null) {
			log.log(Log.FINE, "checked------------->" + checked[0]);
			session.setCustomerCode(session.getCustomerVOs().get(
					Integer.parseInt(checked[0])).getCustomerCode());
		}
		session.setLoyaltyPrograms(null);
		session.setAwbLoyaltyVos(null);
		form.setCanEnableShowPoints("");
		session.removeAttributeFromSession(ShipmentVO.KEY_SHIPMENTVO);//Added by A-5218 as part of CR ICRD-21184
		invocationContext.target = SCREENLOAD_SUCCESS;

	}
}
