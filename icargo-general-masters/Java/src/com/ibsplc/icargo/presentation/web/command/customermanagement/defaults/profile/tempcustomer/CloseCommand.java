package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author a-1496
 *
 */
public class CloseCommand extends BaseCommand{


	private Log log = LogFactory.getLogger("CloseCommand");

	/**
	 * The Module Name
	 */
	private static final String MODULE = "customermanagement.defaults.maintaintempcustomerform";

	private static final String SCREENID =
		"customermanagement.defaults.maintaintempcustomerform";
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSETEMPCUSTREG_SUCCESS = "close_tempcustreg_success";


/**
 * @param invocationContext
 * @throws CommandInvocationException
 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("CloseCommand");
    	log.entering("Close Command","CloseCommand");
    	ListtempCustomerSession listtempCustomerSession =
			(ListtempCustomerSession)getScreenSession(MODULE,
					SCREENID);
    	MaintainTempCustomerForm maintainTempCustomerForm =
			(MaintainTempCustomerForm) invocationContext.screenModel;

    	log.log(Log.FINE, "listtempCustomerSession.getPageURL()",
				listtempCustomerSession.getPageURL());
		maintainTempCustomerForm.setSaveFlag(false);
    	
       if(listtempCustomerSession.getPageURL()!=null){
        	 invocationContext.target=CLOSETEMPCUSTREG_SUCCESS;
        	 return;
         }
         else{
        	 invocationContext.target=CLOSE_SUCCESS;
         	 return;
         }
    	}
    	



}
