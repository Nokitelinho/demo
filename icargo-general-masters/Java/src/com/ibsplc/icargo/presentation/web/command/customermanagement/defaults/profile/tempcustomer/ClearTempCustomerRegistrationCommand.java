package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
/**
 * @author A-2135
 *
 */
public class ClearTempCustomerRegistrationCommand  extends BaseCommand {

    private static final String MODULE = "customermanagement.defaults";
    private static final String SCREENID ="customermanagement.defaults.maintaintempcustomerform";
    private static final String CLEAR_SUCCESS = "clear_success";



/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

		 	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
			ListtempCustomerSession listtempCustomerSession = getScreenSession(MODULE, SCREENID);
			MaintainTempCustomerForm maintainTempCustomerForm = (MaintainTempCustomerForm) invocationContext.screenModel;
			maintainTempCustomerForm.setActive("");
			maintainTempCustomerForm.setAddress("");
			maintainTempCustomerForm.setCustomerName("");
			maintainTempCustomerForm.setEmailId("");
			maintainTempCustomerForm.setPhoneNo("");
			maintainTempCustomerForm.setRemark("");
			maintainTempCustomerForm.setStation("");
			maintainTempCustomerForm.setTempId("");	
			maintainTempCustomerForm.setCustCodeFlag("");
			maintainTempCustomerForm.setOperationMode("");
			listtempCustomerSession.removeListTempCustomerDetails();	
			listtempCustomerSession.removeTempCustomerDetails();
			maintainTempCustomerForm.setScreenStatusFlag(
	    			ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			invocationContext.target = CLEAR_SUCCESS;
	}
}