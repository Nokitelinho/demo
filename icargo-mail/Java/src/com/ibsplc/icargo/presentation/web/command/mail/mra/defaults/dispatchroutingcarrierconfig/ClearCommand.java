package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * ClearCommand
 * 
 * @author A-4452
 * 
 */
import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS DESPATCH ROUTING CARRIER CONFIG");
	
	private static final String CLASS_NAME = "ClearCommand";
	
	private static final String MODULE_NAME = "mailtracking.mra.defaults";
	
	private static final String SCREEN_ID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";
	
	
	private static final String CLEAR_SUCCESS = "clear_success";
	
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
log.entering(CLASS_NAME, "execute");

MRARoutingCarrierSession  despatchRoutingSession = 
(MRARoutingCarrierSession)getScreenSession(MODULE_NAME, SCREEN_ID);
MRARoutingCarrierForm mraRoutingCarrierForm=(MRARoutingCarrierForm)invocationContext.screenModel;


mraRoutingCarrierForm.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
mraRoutingCarrierForm.setOriginCity("");
mraRoutingCarrierForm.setDestCity("");
mraRoutingCarrierForm.setCarrier("");
mraRoutingCarrierForm.setValidFromDate("");
mraRoutingCarrierForm.setValidToDate("");
despatchRoutingSession.setRoutingCarrierVOs(null);


invocationContext.target = CLEAR_SUCCESS;
log.exiting(CLASS_NAME, "execute");
}
}
