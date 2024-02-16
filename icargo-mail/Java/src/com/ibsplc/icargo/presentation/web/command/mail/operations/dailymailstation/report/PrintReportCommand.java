/*
 * PrintReportCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dailymailstation.report;

import com.ibsplc.icargo.business.mail.operations.vo.DailyMailStationFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DailyMailStationForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class PrintReportCommand extends AbstractPrintCommand {

	private static final String REPORT_ID = "RPTOPS011";
	private Log log = LogFactory.getLogger("DailyMailStation");
	private static final String GENERATE_FAILURE = "generate_failure";
	private static final String GENERATE_SUCCESS = "generate_success";
	private static final String MODULENAME = "mail.operations";
	private static final String SCREENID = "mailtracking.defaults.DailyMailStation";
	private static final String PRODUCTCODE = "mail";
	private static final String SUBPRODUCTCODE = "operations";
	private static final String ACTION = "generateDailyMailStationReport";
	private static final String BUNDLE = "DailyMailStationResources";
	/**
	 * execute method
	 * @param invocationContext
	 * @throws CommandInvocationException
	*/
	public void execute(InvocationContext invocationContext) throws CommandInvocationException {
			
		 
	    log.log(Log.FINE, "Inside GenerateReportCommand---------->  ");
			
	    	DailyMailStationForm form =(DailyMailStationForm)invocationContext.screenModel;
			DailyMailStationFilterVO filterVO =new DailyMailStationFilterVO();
			
			filterVO.setCarrierCode(form.getAccarrierCode());
			filterVO.setCompanyCode(form.getAccompanyCode());
			filterVO.setDestination(form.getAcdestination());
			
			//filterVO.setFilghtDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(form.getAcfilghtDate()));
			//Commented by A-6991 for CR ICRD-197259
			filterVO.setFlightFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(form.getAcfilghtFromDate()));
			filterVO.setFlightToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(form.getAcfilghtToDate()));
			filterVO.setFlightCarrireID(form.getAcflightCarrireID());
			filterVO.setFlightNumber(form.getAcflightNumber());
			filterVO.setFlightSeqNumber(form.getAcflightSeqNumber());
			filterVO.setOrigin(form.getAcorigin());
			
			    ReportSpec reportSpec = getReportSpec();
				reportSpec.setReportId(REPORT_ID);
				reportSpec.setProductCode(PRODUCTCODE);
				reportSpec.setSubProductCode(SUBPRODUCTCODE);
				reportSpec.setPreview(false);
				reportSpec.setHttpServerBase(invocationContext.httpServerBase);
				reportSpec.addFilterValue(filterVO);
				reportSpec.setResourceBundle(BUNDLE);
				reportSpec.setAction(ACTION);
				generateReport();
				invocationContext.target = getTargetPage();
			    		    
		}
   
    
	}



