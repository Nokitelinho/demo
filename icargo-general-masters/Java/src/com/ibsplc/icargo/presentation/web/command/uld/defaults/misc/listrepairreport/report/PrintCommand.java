/*
 * PrintCommand.java Created on Dec 03, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.listrepairreport.report;

import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDRepairFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.uld.defaults.misc.ListRepairReportSession;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListRepairReportForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * 
 * @author A-2883
 *
 */
public class PrintCommand extends AbstractPrintCommand{
	
	
	private static final String SCREENID = "uld.defaults.listrepairreport";
	private static final String MODULE_NAME = "uld.defaults";
	private Log log = LogFactory.getLogger("List Repair Reports");
	private static final String REPORT_ID = "RPTLST078";
	private static final String ACTION = "printListRepairULD";
	 private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
		
		
		ListRepairReportSession session = getScreenSession(MODULE_NAME, SCREENID);
		ListRepairReportForm listRepairReportForm =(ListRepairReportForm) invocationContext.screenModel; 
		ULDRepairFilterVO filterVO = new ULDRepairFilterVO();
		filterVO=session.getULDRepairFilterVO();
		filterVO.setPageNumber(Integer.parseInt(listRepairReportForm.getDisplayPage()));
		//Added by A-7359 for ICRD-268766 starts here
		if(listRepairReportForm.getCurrencyValue()!=null){
			filterVO.setCurrency(listRepairReportForm.getCurrencyValue());
		}
		//Added by A-7359 for ICRD-268766 ends here
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listRepairReportForm.getProduct());
		reportSpec.setSubProductCode(listRepairReportForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle("listRepairReportResources");
		reportSpec.setAction(ACTION);
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		invocationContext.target = getTargetPage();

		
	}

}
