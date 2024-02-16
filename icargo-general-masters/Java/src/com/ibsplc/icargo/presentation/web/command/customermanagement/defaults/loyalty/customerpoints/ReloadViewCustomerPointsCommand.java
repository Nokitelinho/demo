package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.customerpoints;

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
public class ReloadViewCustomerPointsCommand extends BaseCommand {
	private Log log = LogFactory.getLogger("ReloadViewCustomerPointsCommand");
    private static final String SCREENLOAD_SUCCESS = "screenload_success";
    private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREENID = "customermanagement.defaults.listcustomerpoints";

   /**
    * @param invocationContext
    * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	log.entering("ReloadViewCustomerPointsCommand","Enter");
    	ListCustomerPointsForm form = (ListCustomerPointsForm)invocationContext.screenModel;
    	ListCustomerPointsSession session = getScreenSession(MODULENAME,SCREENID);
    	
    	log.exiting("ReloadViewCustomerPointsCommand","Exit");
    	invocationContext.target = SCREENLOAD_SUCCESS;
    	

    }
}
