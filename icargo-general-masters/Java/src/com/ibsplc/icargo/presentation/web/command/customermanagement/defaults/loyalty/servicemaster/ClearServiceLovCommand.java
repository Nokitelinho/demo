package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.servicemaster;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ServiceLovForm;

/**
 * Command class to clear the AirCraftTypeLov
 */
public class ClearServiceLovCommand extends BaseCommand {

	private static final String CLEARSERVICELOVSUCCESS="clearserviceLov_Success";

	

	/**
	 * Method to execute the command
	 * @param invocationContext
	 * @exception  CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
        ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
	    String companyCode = logonAttributes.getCompanyCode();

	    ServiceLovForm serviceLovForm=
			(ServiceLovForm)invocationContext.screenModel;
	    serviceLovForm.setCode("");
	    serviceLovForm.setDescription("");
		serviceLovForm.setDisplayPage("1");
		serviceLovForm.setName("");
		serviceLovForm.setSelectedValues("");
		if(serviceLovForm.getServiceTypeLovPage()!=null){
			serviceLovForm.setServiceTypeLovPage(null);
		}
		invocationContext.target =CLEARSERVICELOVSUCCESS;
	}
}
