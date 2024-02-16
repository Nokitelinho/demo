package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.dispatchroutingcarrierconfig;

/**
 * CloseCommand
 * 
 * @author A-4452
 * 
 */
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.MRARoutingCarrierSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRARoutingCarrierForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class CloseCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("MAILTRACKING MRA DEFAULTS DESPATCH ROUTING CARRIER CONFIG");

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.dispatchroutingcarrierconfig";

	private static final String CLASS_NAME = "CloseCommand";
	
	private static final String CLOSE_SUCCESS = "close_success";
	private static final String CLOSE_TOPRORATIONEXCEPTION_SUCCESS = "closeprorationexception_success";
	
   public void execute(InvocationContext invocationContext)
   throws CommandInvocationException {

		log.entering(CLASS_NAME,"execute");
		MRARoutingCarrierForm mraRoutingCarrierForm=(MRARoutingCarrierForm)invocationContext.screenModel;
		MRARoutingCarrierSession session = (MRARoutingCarrierSession)getScreenSession(MODULE, SCREENID);
		session.removeAllAttributes();
		invocationContext.target = CLOSE_SUCCESS;
	}


}