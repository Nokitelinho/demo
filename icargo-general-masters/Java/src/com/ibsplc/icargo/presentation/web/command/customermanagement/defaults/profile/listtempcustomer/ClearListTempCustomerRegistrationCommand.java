package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.listtempcustomer;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.ListTempCustomerForm;
/**
 * @author A-2135
 *
 */
public class ClearListTempCustomerRegistrationCommand  extends BaseCommand {

    private static final String MODULE = "customermanagement.defaults";
    private static final String SCREENID ="customermanagement.defaults.listtempcustomerform";
    private static final String CLEAR_SUCCESS = "clear_success";
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		 	
			ListtempCustomerSession listtempCustomerSession = getScreenSession(MODULE, SCREENID);
			ListTempCustomerForm listTempCustomerForm = (ListTempCustomerForm) invocationContext.screenModel;
			listTempCustomerForm.setCustomerName("");
			listTempCustomerForm.setStation("");
			listTempCustomerForm.setStatus("");
			listTempCustomerForm.setListTempId("");
			listTempCustomerForm.setValidFrom("");
			listTempCustomerForm.setValidTo("");	
			listTempCustomerForm.setCloseStatus("");
			listTempCustomerForm.setCustomerCode("");
			listtempCustomerSession.removeListTempCustomerDetails();
			listtempCustomerSession.removeListCustomerRegistration();
			
			invocationContext.target = CLEAR_SUCCESS;
	}
}