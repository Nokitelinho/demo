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
public class ClearPointAccumulationCommand extends BaseCommand {

	private static final String MODULENAME = "customermanagement.defaults";

	private static final String SCREENID = "customermanagement.defaults.customerlisting";

	private static final String BLANK = "";

	private static final String CLEAR_SUCCESS = "clear_success";

/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		Log log = LogFactory.getLogger("customerlisting");
		log.entering("customer", "point accumulation clear command");

		ListCustomerForm form = (ListCustomerForm) invocationContext.screenModel;
		ListCustomerSession session = getScreenSession(MODULENAME, SCREENID);
		session.setLoyaltyPrograms(null);
		session.setAwbLoyaltyVos(null);
		form.setMasterDocumentNumber(BLANK);
		form.setDocumentNumber(BLANK);
		form.setOwnerId(BLANK);
		form.setDuplicateAWBStatus(BLANK);
		form.setCanEnableShowPoints(BLANK);	
		session.removeAttributeFromSession(ShipmentVO.KEY_SHIPMENTVO);//Added by A-5218 as part of CR ICRD-21184
		invocationContext.target = CLEAR_SUCCESS;

	}
}
