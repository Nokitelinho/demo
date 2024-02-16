package com.ibsplc.icargo.presentation.web.command.mail.mra.gpareporting.ux.listinvoic;

import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpareporting.ux.ListInvoicSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpareporting.ux.ListInvoicForm;

public class RemarksCloseCommand extends BaseCommand {
	private static final String MODULE_NAME = "mail.mra";
	private static final String SCREENID = "mail.mra.gpareporting.ux.listinvoic";
	
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		ListInvoicForm listInvoicForm = (ListInvoicForm) invocationContext.screenModel;
		ListInvoicSession listinvoicsession =getScreenSession(MODULE_NAME,SCREENID);
		listInvoicForm.setRemarks("");
		listinvoicsession.setSelectedRecords(null);
		invocationContext.target = "close_success";
	}
}
