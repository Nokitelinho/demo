package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.report;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ux.MailBagHistoryUxForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailbaghistory.report.PrintCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	17-Sep-2018		:	Draft
 */
public class PrintCommand extends AbstractPrintCommand {
	
	private static final String REPORT_ID = "RPTLST060";
	private Log log = LogFactory.getLogger("Mail History");
	private static final String PRINT_FAILURE = "print_failure";
	private static final String MODULENAME = "mail.operations";
	private static final String SCREENID = "mail.operations.ux.mailbaghistory";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String BLANK = "";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String ACTION = "generateFindMailbagHistoriesReport";
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-8164 on 17-Sep-2018
	 * 	Used for 	:
	 *	Parameters	:	@param invocationContext
	 *	Parameters	:	@throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes  =  applicationSession.getLogonVO();


		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		MailBagHistoryUxForm mailBagHistoryForm =
			(MailBagHistoryUxForm)invocationContext.screenModel; 


	     log.log(Log.FINE, "\n\n form mailbag id---------->  ",
				mailBagHistoryForm.getMailbagId().toUpperCase());
		String mailBagId=null;
	      mailBagId =mailBagHistoryForm.getMailbagId().toUpperCase();

	      OneTimeVO oneTimeVO=new OneTimeVO();
	      oneTimeVO.setCompanyCode(companyCode);
	      oneTimeVO.setFieldDescription(logonAttributes.getAirportCode());
	      oneTimeVO.setFieldType(mailBagId);
	      oneTimeVO.setFieldValue(String.valueOf(mailBagHistoryForm.getMailSequenceNumber()));


	  ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(PRODUCTCODE);
		reportSpec.setSubProductCode(SUBPRODUCTCODE);
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
	reportSpec.addFilterValue(oneTimeVO);
	reportSpec.setResourceBundle("mailbagHistoryResources");
	reportSpec.setAction(ACTION);

		generateReport();
		invocationContext.target = getTargetPage();
	}

}
