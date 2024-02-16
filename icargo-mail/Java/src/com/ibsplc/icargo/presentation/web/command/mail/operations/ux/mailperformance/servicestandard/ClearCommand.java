/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailperformance.servicestandard;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ux.MailPerformanceSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailPerformanceForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-8149
 *
 */
public class ClearCommand extends BaseCommand{

	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mail.operations.ux.mailperformance";
	private static final String SUCCESS = "servicestandard_clear_success";
	
	@Override
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		
		log.log(Log.FINE, "\n\n in the service Standard clear command----------> \n\n");
		MailPerformanceForm mailperformanceForm =
				(MailPerformanceForm)invocationContext.screenModel;
		MailPerformanceSession mailperformanceSession = 
							getScreenSession(MODULE_NAME,SCREEN_ID);
		
		mailperformanceForm.setServiceStandardsPacode("");
		mailperformanceForm.setServiceStandardsOrigin("");
		mailperformanceForm.setServiceStandardsDestination("");
		mailperformanceForm.setServiceStandard("");
		mailperformanceForm.setContractId("");
		mailperformanceForm.setServiceLevel("");//added by A-8353 as part of ICRD-293367
		mailperformanceForm.setScanningWaived(null);
		mailperformanceSession.removeMailServiceStandardVOs();
		mailperformanceSession.setMailServiceStandardVOs(null);
		mailperformanceSession.setMailServiceStndVOs(null);
		
		mailperformanceForm.setStatusFlag("Clear_screen");
		mailperformanceForm.setScreenFlag("serviceStandards");
		invocationContext.target = SUCCESS;
	}

	
}
