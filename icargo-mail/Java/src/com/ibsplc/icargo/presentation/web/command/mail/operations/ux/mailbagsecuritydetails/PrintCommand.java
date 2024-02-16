package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbagsecuritydetails;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.spring.command.AbstractPrintCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.web.model.mail.operations.MailbagSecurityModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.util.log.ExtendedLogManager;
import com.ibsplc.xibase.server.framework.util.log.Logger;

public class PrintCommand extends AbstractPrintCommand {

	private static final String COMMAND_NAME = "PrintCommand";
	private static final String REPORT_ID = "RPRMTK302";
	private static final String ACTION = "generateMailSecurityReport";
	private static final Logger LOGGER = ExtendedLogManager.getLogger(COMMAND_NAME);
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String REPORT_TITLE = "Mailbag Security Report";

	@Override
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {

		MailbagSecurityModel mailbagSecurityModel = (MailbagSecurityModel) actionContext.getScreenModel();
		LogonAttributes logonAttributes = getLogonAttribute();

		LOGGER.info("entering into" + COMMAND_NAME + "class");  
		String mailbagId = mailbagSecurityModel.getMailbagId();
		String companyCode = logonAttributes.getCompanyCode();
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setPreview(true);
		getReportSpec().setProductCode(PRODUCTCODE);
		getReportSpec().setSubProductCode(SUBPRODUCTCODE);
		getReportSpec().setResourceBundle("mailbagSecurityResourceBundle");
		getReportSpec().setAction(ACTION);
		getReportSpec().setReportTitle(REPORT_TITLE);
		getReportSpec().addFilterValue(mailbagId);
		getReportSpec().addFilterValue(companyCode);

		LOGGER.info("exiting from" + COMMAND_NAME + "class");  
		generateReport(actionContext);

	}

}
