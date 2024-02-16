package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * ScreenloadCommand
 * 
 * @author A-4452
 * 
 */
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ScreenloadCommand extends BaseCommand {
	
	


	private Log log = LogFactory.getLogger("DespatchRoutingCarrierConfig ScreenloadCommand");

	private static final String CLASS_NAME = "ScreenLoadCommand";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	/*
	 * Target mappings for succes 
	 */
	private static final String ACTION_SUCCESS = "screenload_success";
	
	/**
	 * @param invocationContext
	 * @exception CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering(CLASS_NAME, "execute");
    	
    	MRARoutingCarrierForm form=(MRARoutingCarrierForm)invocationContext.screenModel;
   	
    	MRARoutingCarrierSession  dsnRoutingSession = 
			(MRARoutingCarrierSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	
    	dsnRoutingSession.removeRoutingCarrierVOs();
    	dsnRoutingSession.removeRoutingCarrierFilterVO();
    	invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
    }


}
