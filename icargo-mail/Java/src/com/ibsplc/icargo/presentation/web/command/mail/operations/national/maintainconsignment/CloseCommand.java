/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;

/**
 * @author A-3817
 *
 */
public class CloseCommand extends BaseCommand{
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET = "success";
	/**
	 * 
	 */
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		resetFormFields(consignmentForm);
		resetSessionEntries(consignmentSession);
		invocationContext.target = TARGET;
		
		
	}
	/**
	 * 
	 * @param consignmentForm
	 */
	private void resetFormFields(CaptureMailDocumentForm consignmentForm){
		consignmentForm.setConDate("");
		consignmentForm.setConDocNo("");
		consignmentForm.setOrigin("");
		consignmentForm.setDestination("");
		
	}
	
	/**
	 * 
	 * @param consignmentSession
	 */
	private void resetSessionEntries(ConsignmentSession consignmentSession){
		consignmentSession.removeAllAttributes();
		
	}

}
