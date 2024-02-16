package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.customer;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.MaintainCustomerRegistrationSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainRegCustomerForm;
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
	private static final String MODULE = "customermanagement.defaults";    
	private static final String SCREENID = "customermanagement.defaults.listtempcustomerform";	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSETEMPCUSTREG_SUCCESS = "close_tempcustreg_success";
	private static final String MODULENAME = "customermanagement.defaults";
    private static final String SCREEN_ID = "customermanagement.defaults.maintainregcustomer";
	// Added by A-8227 for ICRD-236527 starts
	private static final String CUSTOMER_CONSOLE_SCREEN = "customermanagement.defaults.customerconsole";
	private static final String CLOSE_CUSTOMER_CONSOLE_SUCCESS = "close_customer_console_success";
	private static final String CLOSE_DPRDO_HISTORY="close_customer_dprdo";
	// Added by A-8227 for ICRD-236527 ends

	/**
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	Log log = LogFactory.getLogger("CloseCommand");
    	log.entering("Close Command","CloseCommand");
    	ListtempCustomerSession session = getScreenSession(MODULE, SCREENID);
    	MaintainCustomerRegistrationSession maintainSession = getScreenSession(MODULENAME,SCREEN_ID);
    	MaintainRegCustomerForm form = (MaintainRegCustomerForm)invocationContext.screenModel;
    	if("dprdohistory".equals(maintainSession.getSourcePage())){ 
    		invocationContext.target = CLOSE_DPRDO_HISTORY;
	    	}else{
		    	session.setPageURL("");
		    	maintainSession.setPageURL(null);
		    	 maintainSession.setCustomerVO(null);
		    	
		    	//Added for ICRD-67442 by A-5163 starts
		        maintainSession.setCustomerDetailVOsInMap(null);
		        maintainSession.setSourcePage(null);
		        maintainSession.removeValidationErrors();
		    	//Added for ICRD-67442 by A-5163 ends
		        
		    	log.log(Log.FINE, "maintainSession.getPageURL()", maintainSession.getPageURL());
				log.log(Log.FINE, "form.getPageURL()", form.getPageURL(), "******");
				if(form.getPageURL()!=null && form.getPageURL().length()>2){
					// Added by A-8227 for ICRD-236527 starts
					if(form.getPageURL().equals(CUSTOMER_CONSOLE_SCREEN)){
						invocationContext.target = CLOSE_CUSTOMER_CONSOLE_SUCCESS;
						return;
					}
					// Added by A-8227 for ICRD-236527 ends
					else{
		        	 invocationContext.target=CLOSETEMPCUSTREG_SUCCESS;
		        	 session.setPageURL("fromCustomerRegn");
		        	 log.log(Log.FINE,"inside 1");
		        	
		        	 return;
		         }
				} else {
		        	 invocationContext.target=CLOSE_SUCCESS;
		       		 log.log(Log.FINE,"inside 2");
		         	 return;
		         } 	
	    	}
    	}
    	
 
}
