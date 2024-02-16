package com.ibsplc.icargo.presentation.web.command.mail.operations.errorhandling;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.admin.monitoring.ErrorHandlingSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailTrackingErrorHandlingForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class ShowFiltersCommand extends BaseCommand {




	/**
	 * Logger
	 */
	private Log log = LogFactory.getLogger("MAIL OPERATIONS");
	
	/**
	 * The Module Name
	 */
	private static final String CLASS = "ShowFiltersCommand";
	
	/**
	 * Target mappings for succes 
	 */
	private static final String SWITCH_SUCCESS = "switch_success";
	
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS, "execute");
		ErrorHandlingSession errorHandlingSession = (ErrorHandlingSession) getScreenSession("admin.monitoring", "admin.monitoring.errorhandling");
		MailTrackingErrorHandlingForm errorHandlingForm = (MailTrackingErrorHandlingForm) invocationContext.screenModel;
		errorHandlingForm.setAirportcode(errorHandlingSession.getAirportCode());
		invocationContext.target = SWITCH_SUCCESS;
		log.exiting(CLASS, "execute");

	}
}
