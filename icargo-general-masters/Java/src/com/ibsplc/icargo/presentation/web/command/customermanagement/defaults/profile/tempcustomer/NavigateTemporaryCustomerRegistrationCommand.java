package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.profile.tempcustomer;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.profile.ListtempCustomerSession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile.MaintainTempCustomerForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 *
 * @author A-2035
 *
 */
public class NavigateTemporaryCustomerRegistrationCommand extends BaseCommand{

    private static final String SCREENID = "customermanagement.defaults.maintaintempcustomerform";
    private static final String MODULE = "customermanagement.defaults";
    private static final String BLANK= "";
    private static final String NAVIGATE_SUCCESS = "navigate_success";

 /**
  * @param invocationContext
  * @throws CommandInvocationException
  */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
        Log log = LogFactory.getLogger("NavigateTemporaryCustomerRegistrationCommand");
        log.entering("CUSTOMERMANAGEMENT","Navigate Command");

		   MaintainTempCustomerForm form =
				(MaintainTempCustomerForm) invocationContext.screenModel;
	           ListtempCustomerSession session =
	    		getScreenSession(MODULE, SCREENID);

    	ArrayList<String> tempIDs = session.getTempIDs();
    	log.log(Log.FINE,"tempIDs from session"+tempIDs);
    	String tempID = BLANK;
    	if(tempIDs != null){
    		tempID = tempIDs.get(Integer.parseInt(form.getDisplayPage())-1);
    	}
    	log.log(Log.FINE,"\n\n\ndiplay page is-------------->"+form.getDisplayPage());
    	log.log(Log.FINE,"\n\n\nform.getCloseFlag() is-------------->"+form.getCloseFlag());
    	log.log(Log.FINE,"new tempID number---->"+tempID);
    	form.setCloseFlag(session.getPageURL());
    	form.setTempId(tempID);    	
    	form.setPageURL(session.getPageURL());
    	form.setNavigate(true);
    	form.setSaveFlag(false);
    	log.log(Log.FINE,"tempID---------------->"+tempID);
    	invocationContext.target = NAVIGATE_SUCCESS;
    }
}
