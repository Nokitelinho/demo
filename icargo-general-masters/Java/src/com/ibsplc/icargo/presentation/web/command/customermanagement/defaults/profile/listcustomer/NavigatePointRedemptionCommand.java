package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listcustomer;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-2052
 *
 */
public class NavigatePointRedemptionCommand extends BaseCommand{
	
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.customerlisting";
	private Log log = LogFactory.getLogger("NavigatePointRedemptionCommand");
	
/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
	public void execute(InvocationContext invocationContext)throws CommandInvocationException{
		log.entering("NavigatePointRedemptionCommand","ENTER");
		ListCustomerForm form = (ListCustomerForm)invocationContext.screenModel;
    	ListCustomerSession session = getScreenSession(MODULENAME,SCREENID);    	
    	ArrayList<String> customerCodes = session.getCustomerCodes();
    	ArrayList<String> customerNames = session.getCustomerNames();
    	log.log(Log.FINE, "customerCodes from sesssion", customerCodes);
		log.log(Log.FINE, "customerNames from sesssion", customerNames);
		String custCode = "";
    	String custName = "";
    	if(customerCodes != null){
    		custCode = customerCodes.get(Integer.parseInt(form.getDispPage())-1);
    		custName = customerNames.get(Integer.parseInt(form.getDispPage())-1);
    	}
    	log.log(Log.FINE, "\n\n\ndiplay page is-------------->", form.getDispPage());
		log.log(Log.FINE, "new custCode--->", custCode);
		log.log(Log.FINE, "new custName--->", custName);
		form.setCustomerCodePointRdmd(custCode);
    	form.setCustomerNamePointRdmd(custName);
    	log.exiting("NavigatePointRedemptionCommand","EXIT");
        invocationContext.target=SCREENLOAD_SUCCESS;
	}
}

