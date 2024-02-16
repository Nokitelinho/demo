/*
 * PrintCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.mailbaghistory.report;



import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailBagHistoryForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST060";
	private Log log = LogFactory.getLogger("Mail History");
	private static final String PRINT_FAILURE = "print_failure";
	private static final String MODULENAME = "mail.operations";
	private static final String SCREENID = "mailtracking.defaults.mailbaghistory";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String BLANK = "";
	private static final String MAIL_STATUS = "mailtracking.defaults.mailstatus";
	private static final String ACTION = "generateFindMailbagHistoriesReport";

	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes  =  applicationSession.getLogonVO();


			String companyCode = logonAttributes.getCompanyCode().toUpperCase();
			MailBagHistoryForm mailBagHistoryForm =
				(MailBagHistoryForm)invocationContext.screenModel;

			//Collection<ErrorVO> errors = new ArrayList<ErrorVO>();



		     log.log(Log.FINE, "\n\n form mailbag id---------->  ",
					mailBagHistoryForm.getMailbagId().toUpperCase());
			String mailBagId=null;
		      mailBagId =mailBagHistoryForm.getMailbagId().toUpperCase();

		      OneTimeVO oneTimeVO=new OneTimeVO();
		      oneTimeVO.setCompanyCode(companyCode);
		      oneTimeVO.setFieldDescription(logonAttributes.getAirportCode());
		      oneTimeVO.setFieldType(mailBagId);


		  ReportSpec reportSpec = getReportSpec();
			reportSpec.setReportId(REPORT_ID);
			reportSpec.setProductCode(PRODUCTCODE);
			reportSpec.setSubProductCode(SUBPRODUCTCODE);
			reportSpec.setPreview(true);
			reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		//reportSpec.addExtraInfo(hashMap);
		reportSpec.addFilterValue(oneTimeVO);
		reportSpec.setResourceBundle("mailbagHistoryResources");
		reportSpec.setAction(ACTION);

			generateReport();
			invocationContext.target = getTargetPage();
		}


	}







