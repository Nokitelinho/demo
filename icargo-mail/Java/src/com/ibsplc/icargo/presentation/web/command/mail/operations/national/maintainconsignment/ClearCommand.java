/**
 * 
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.national.maintainconsignment;

import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ConsignmentSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.national.CaptureMailDocumentForm;

/**
 * @author A-3817
 *
 */
public class ClearCommand extends BaseCommand{
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREEN_ID = "mailtracking.defaults.national.consignment";
	private static final String TARGET = "success";
	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		CaptureMailDocumentForm consignmentForm = (CaptureMailDocumentForm) invocationContext.screenModel;
		ConsignmentSession consignmentSession = getScreenSession(MODULE_NAME,
				SCREEN_ID);
		consignmentForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
		//Added as part if icrd-15420 by A-4810 for first time flow of delete command
			if("Y".equals(consignmentForm.getDeleteFlag())){
				invocationContext.target = TARGET;
			}
			else
			{
				resetFormFields(consignmentForm);
				resetSessionEntries(consignmentSession);
				invocationContext.target = TARGET;
			}
		
		
	}
	private void resetFormFields(CaptureMailDocumentForm consignmentForm){
		consignmentForm.setConDate("");
		consignmentForm.setConDocNo("");
		consignmentForm.setOrigin("");
		consignmentForm.setDestination("");
		consignmentForm.setConsignmentOriginFlag("N");
		consignmentForm.setScreenLoadFlag("Y");
		consignmentForm.setDataFlag("N");
		//Added as part of bug-fix -icrd-12637 by A-4810
		consignmentForm.setNoconDocNo("");
		consignmentForm.setNoError("");
		consignmentForm.setValidateAcceptance("");
		consignmentForm.setDeleteFlag("");
		//Added as part of bug-fix -icrd-18645 by A-4810
		consignmentForm.setMailCategory("");
		
	}
	
	private void resetSessionEntries(ConsignmentSession consignmentSession){
		consignmentSession.setConsignmentDocumentVO(null);
		
		
	}
	

}
