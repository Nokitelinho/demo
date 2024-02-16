/*
 * PrintCommand.java Created on Sep 12, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.customermanagement.defaults.loyalty.listloyalty.report;


import com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo.LoyaltyProgrammeFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.session.interfaces.customermanagement.defaults.loyalty.ListLoyaltySession;
import com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.loyalty.ListLoyaltyForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1862
 *
 */

public class PrintCommand extends AbstractPrintCommand {
	
    private static final String REPORT_ID = "RPTLST045";
	private Log log = LogFactory.getLogger("List Loyalty");	
	private static final String PRINT_FAILURE = "print_failure";
	private static final String MODULENAME = "customermanagement.defaults";
	private static final String SCREENID = "customermanagement.defaults.listloyalty";
	private static final String PRODUCTCODE = "customermanagement";
	private static final String SUBPRODUCTCODE = "defaults";
	private static final String ACTION = "generateLoyaltyDetailsReport";

	
	/**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */

public void execute(InvocationContext invocationContext) throws CommandInvocationException {

	ApplicationSessionImpl applicationSession = getApplicationSession();
	LogonAttributes logonAttributes  =  applicationSession.getLogonVO();
	String  compCode = logonAttributes.getCompanyCode();
	
	ListLoyaltyForm listLoyaltyForm = 
		(ListLoyaltyForm) invocationContext.screenModel;
	ListLoyaltySession listLoyaltySession = 
		(ListLoyaltySession)getScreenSession(MODULENAME,SCREENID);
	
	log.log(Log.FINE, "listLoyaltyForm.getFromList() ---> ", listLoyaltyForm.getFromList());
	log
			.log(Log.FINE, "\n\n\n----------1----", logonAttributes.getStationCode());
	LoyaltyProgrammeFilterVO loyaltyProgrammeFilterVO=new LoyaltyProgrammeFilterVO();
	
	
	loyaltyProgrammeFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	
	if(listLoyaltyForm.getLoyaltyName()!=null &&
			listLoyaltyForm.getLoyaltyName().length()!=0 )
    {
		loyaltyProgrammeFilterVO.setLoyaltyProgrammeCode(listLoyaltyForm.getLoyaltyName().toUpperCase());
    }
	log.log(Log.FINE,"\n\n\n----------2----");
	if(listLoyaltyForm.getToDate()!=null &&
			listLoyaltyForm.getToDate().length()!=0 )
    {
		LocalDate toDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
		loyaltyProgrammeFilterVO.setToDate(toDate.setDate(
				listLoyaltyForm.getToDate()));
    }
	log.log(Log.FINE,"\n\n\n----------3----");
	if(listLoyaltyForm.getFromDate()!=null &&
			listLoyaltyForm.getFromDate().length()!=0 )
    {
		LocalDate fromDate = new LocalDate(logonAttributes.getStationCode(),Location.STN, false);
		loyaltyProgrammeFilterVO.setFromDate(fromDate.setDate(
				listLoyaltyForm.getFromDate()));
    }

	log.log(Log.FINE,"\n\n\n----------4----");
	String toDisplayPage = listLoyaltyForm.getDisplayPage();
	log.log(Log.FINE, "\n\n\n----------5----", toDisplayPage);
	int displayPage = Integer.parseInt(toDisplayPage);
	log.log(Log.FINE, "\n\n\n----------6----", loyaltyProgrammeFilterVO);
	loyaltyProgrammeFilterVO.setPageNumber(displayPage);
	log.log(Log.FINE, "loyaltyProgrammeFilterVO  from form ---> ",
			loyaltyProgrammeFilterVO);
		getReportSpec().addFilterValue(loyaltyProgrammeFilterVO);
		getReportSpec().setReportId(REPORT_ID);
		getReportSpec().setPreview(false);
		getReportSpec().setProductCode(PRODUCTCODE);
		getReportSpec().setSubProductCode(SUBPRODUCTCODE);
		getReportSpec().setResourceBundle("listloyaltyResources");
		getReportSpec().setAction(ACTION);
		log.log(Log.FINE, "\n\n\n----------REPORT_ID----->", REPORT_ID);
		//log.log(Log.FINE,"\n\n\n----------reportMetaData----->"+reportMetaData);
		generateReport();
		
		log.log(Log.FINE,"\n\n\n----------AFTER GENERATE REPORT----");
		invocationContext.target = getTargetPage();
		log.log(Log.FINE, "\n\n\n----------report----->",
				invocationContext.target);
		}
		

}







