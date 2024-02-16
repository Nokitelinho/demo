package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.rdtoffset;


import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ClearCommand extends BaseCommand {

private static final String SUCCESS = "clear_success";
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
log.log(Log.FINE, "\n\n in the clear command----------> \n\n");
    	
    	MailPerformanceForm mailperformanceForm =
							(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailperformanceSession = 
										getScreenSession(MODULE_NAME,SCREEN_ID);
		
		mailperformanceForm.setRdtAirport("");
		mailperformanceForm.setRdtPacode("");       
		mailperformanceForm.setDisableSave("Y");
		mailperformanceSession.setMailRdtMasterVOs(null);
		mailperformanceForm.setScreenFlag("rdtRadioBtn");
		mailperformanceForm.setScreenStatusFlag
					(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
    	invocationContext.target = SUCCESS;
    	mailperformanceForm.setStatusFlag("List_fail"); 
	}
	
}