package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.customerpoints;

import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListCustomerPointsSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListCustomerPointsForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2052
 * 
 */
public class ClearCustomerPointsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ClearCustomerPointsCommand");
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

  /**
   * @param invocationContext
   * @throws CommandInvocationException
   */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ClearCustomerPointsCommand","Enter");
    	ListCustomerPointsForm form = (ListCustomerPointsForm)invocationContext.screenModel;
    	ListCustomerPointsSession session = getScreenSession(MODULENAME,SCREENID);    	
    	form.setAwbPrefix("");
    	form.setDocumentNumber("");
    	form.setHawbNumber("");
    	form.setCustomerCode("");
    	form.setFromDate("");
    	form.setToDate("");
    	form.setTotal("");
    	session.removePage();
    	session.removeFilterDetailsOnReturn();
    	// A-5273 Added for ICRD-21184 
    	session.removeAttributeFromSession(ShipmentVO.KEY_SHIPMENTVO);
    	log.exiting("ClearCustomerPointsCommand","Exit");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    }
}
