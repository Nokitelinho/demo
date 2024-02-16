/*
 * PrintDamageRepairDetailsCommand.java Created on Jul 28, 2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;



import com.ibsplc.icargo.business.uld.defaults.misc.vo.UldDmgRprFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;



/**
 *
 * @author A-2883
 *
 */

public class PrintDamageRepairDetailsCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST275";
	private Log log = LogFactory.getLogger("ULD Damage Repair Report");
	private static final String RESOURCE_BUNDLE_KEY = "listuldmovementResources";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String ACTION = "listDamageRepairDetailsReport";
	
    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
public void execute(InvocationContext invocationContext)
										throws	CommandInvocationException {
		log.log(Log.FINE, "PrintDamageRepairDeatilsCommand");
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		ListULDMovementForm form = (ListULDMovementForm) invocationContext.screenModel;
		UldDmgRprFilterVO filterVO = new UldDmgRprFilterVO();
		filterVO.setCompanyCode(companyCode);
		filterVO.setUldNumber(form.getUldNumber().toUpperCase());
		filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));			
		//LocalDate fDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
	    if(form.getFromDate()!=null && form.getFromDate().trim().length()!=0){
	    	//filterVO.setFromDate(fDate.setDate(form.getFromDate()));
		filterVO.setFromDate(form.getFromDate());
	    }
	   // LocalDate tDate = new LocalDate(LocalDate.NO_STATION, Location.NONE,false);
	    if(form.getToDate()!=null && form.getToDate().trim().length()!=0){
	    	//filterVO.setToDate(tDate.setDate(form.getToDate()));
		filterVO.setToDate(form.getToDate());
	    }
		//filterVO.setFromDate(form.getFromDate());
		//filterVO.setToDate(form.getToDate());
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
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





