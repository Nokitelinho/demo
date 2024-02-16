/*
 * PrintCommand.java Created on Dec 08, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.uldmovementhistory.report;



import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;

//import java.util.ArrayList;
//import java.util.Collection;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
//import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ListULDMovementForm;



/**
 *
 * @author A-2122
 *
 */

public class PrintCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTLST033";
	private Log log = LogFactory.getLogger("List ULD Movement");
	private static final String BLANK = "";
	private static final String SELECT="ALL";
	private static final String STATUS_ONETIME="uld.defaults.overallStatus";
	private static final String CONTENT_ONETIME="uld.defaults.contentcodes";
	private static final String RESOURCE_BUNDLE_KEY = "listuldmovementResources";

	//private static final String ACTION = "printlistuldmovement";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";

	// Added by a-2412
	private static final String ACTION = "printlistuldmovement";



    /**
     * execute method
     * @param invocationContext
     * @throws CommandInvocationException
    */
public void execute(InvocationContext invocationContext)
										throws	CommandInvocationException {
log.log(log.INFO,"entering printcommand");
ApplicationSessionImpl applicationSession = getApplicationSession();
LogonAttributes logonAttributesVO  =  applicationSession.getLogonVO();
// Commented by Manaf for INT ULD510
//Collection<ErrorVO> errorVos= new ArrayList<ErrorVO>();

ListULDMovementForm listULDMovementForm =
    		(ListULDMovementForm)invocationContext.screenModel;
//ULDDefaultsDelegate uldDefaultsDelegate = null;
LocalDate fromDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);
LocalDate toDate = new LocalDate(logonAttributesVO.getAirportCode(),Location.ARP,false);

/*Map<String, Collection<OneTimeVO>>  oneTimes=getOneTimeDetails();
Collection<OneTimeVO> currentStatus = new ArrayList<OneTimeVO>();
Collection<OneTimeVO> content = new ArrayList<OneTimeVO>();

if(oneTimes!=null){//to get onetime values
	currentStatus =	oneTimes.get(STATUS_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************shipmentTypes********"+
			currentStatus);

	content = oneTimes.get(CONTENT_ONETIME);
	log.log(Log.FINE,"\n\n\n\n*************wareHouseCodes********"+
			content);
}*/



ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
//ULDValidationVO uldValidationVO = new ULDValidationVO();
uldMovementFilterVO.setCompanyCode(logonAttributesVO.getCompanyCode());
//String uldNumber = listULDMovementForm.getUldNumber().toUpperCase();
//String companyCode = logonAttributesVO.getCompanyCode().toUpperCase();
uldMovementFilterVO.setUldNumber((listULDMovementForm.getUldNumber().trim().
																toUpperCase()));
if(listULDMovementForm.getFromDate()!= null && !BLANK.equals
			(listULDMovementForm.getFromDate())&& !SELECT.equals
										(listULDMovementForm.getFromDate())){
	uldMovementFilterVO.setFromDate(fromDate.setDate
										(listULDMovementForm.getFromDate()));
	}
if(listULDMovementForm.getToDate()!= null && !BLANK.equals
		(listULDMovementForm.getToDate())&& !SELECT.equals
										(listULDMovementForm.getToDate())){
uldMovementFilterVO.setToDate(toDate.setDate(listULDMovementForm.getToDate()));
}

int pageNumber=Integer.parseInt(listULDMovementForm.getDisplayPage());
uldMovementFilterVO.setPageNumber(pageNumber);

log.log(Log.FINE, "\n\n\n---Filter Vo sent to server----->",
		uldMovementFilterVO);
		ReportSpec reportSpec = getReportSpec();
		log.log(Log.INFO, "report spec----------->", reportSpec);
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(listULDMovementForm.getProduct());
		reportSpec.setSubProductCode(listULDMovementForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(uldMovementFilterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);

		generateReport();

		if(getErrors() != null && getErrors().size() > 0){

			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}

		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();


}



}





