/*
 * ListBillingEntriesReportCommand.java Created on Nov 20,2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.report;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.ListBillingEntriesFilterVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.ListBillingEntriesForm;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2270
 *
 */
public class ListBillingEntriesReportCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_defaults");
	private static final String REPORT_ID = "RPTMRA043";
	private static final String RESOURCE_BUNDLE_KEY = "listbillingentries";
	private static final String ACTION = "generateFlownRevenueReport";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String PRODUCT = "mailtracking";
	private static final String SUBPRODUCT = "mra";
	private static final String FROMDATE_MUST_NOT_BE_GREATER="mailtracking.mra.defaults.listbillingentries.msg.err.fromdatenotgreater";
	private static final String FROMDATE_MUST_NOT_BE_NULL="mailtracking.mra.defaults.listbillingentries.msg.err.fromdatenotnull";
	private static final String TODATE_MUST_NOT_BE_NULL="mailtracking.mra.defaults.listbillingentries.todatenotnull";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("MAILTRACKING_deaults","ListBillingEntriesReportCommand entered");
		ListBillingEntriesForm form =
										(ListBillingEntriesForm)invocationContext.screenModel;
		String companyCode = getApplicationSession().getLogonVO().getCompanyCode();
		ListBillingEntriesFilterVO filterVO=new ListBillingEntriesFilterVO();
		LocalDate fromDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
        LocalDate toDate=new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		filterVO.setCompanyCode(companyCode);
		/* Client Side validation of filter fields*/
		ErrorVO error=null;
		Collection<ErrorVO> errors=new ArrayList<ErrorVO>();
    	 
         		if(form.getFromDate()!=null && form.getFromDate().trim().length()>0){
                  fromDate.setDate(form.getFromDate());
                  filterVO.setFromDate(fromDate);
         		}
         		else{
         			error=new ErrorVO(FROMDATE_MUST_NOT_BE_NULL);
         			errors.add(error);
         			}
          
         		 if(form.getToDate()!=null && form.getToDate().trim().length()>0){
         			 toDate.setDate(form.getToDate());
         			filterVO.setToDate(toDate);
         		 }
         		 else{
         			error=new ErrorVO(TODATE_MUST_NOT_BE_NULL);
         			errors.add(error);
         		 }
         		  if ((filterVO.getFromDate() != null) && (!("").equals(filterVO.getFromDate().toString()))&&
         					filterVO.getToDate() != null && ((!("").equals(filterVO.getToDate().toString())))){
                 if(filterVO.getFromDate().isGreaterThan(filterVO.getToDate())){
         				error=new ErrorVO(FROMDATE_MUST_NOT_BE_GREATER);
         				errors.add(error);
                  }
                  }
         		  if(errors!=null && errors.size()>0){
                	 log.log(Log.FINE,"!!!inside errors!= null**************");
        				invocationContext.addAllError(errors);
        				invocationContext.target=PRINT_UNSUCCESSFUL;
        				return;
        			}else{
        				log.log(Log.FINE,"in Else block^^^^^^^^^^^^^^^^^^^^^^^^");
					        if(form.getToDate()!=null && form.getToDate().trim().length()>0){
								 toDate.setDate(form.getToDate());
								filterVO.setToDate(toDate);
							 }
							if(form.getFromDate()!=null && form.getFromDate().trim().length()>0){
								fromDate.setDate(form.getFromDate());
								filterVO.setFromDate(fromDate);
							 }
							ReportSpec reportSpec = getReportSpec();
							reportSpec.setReportId(REPORT_ID);
							reportSpec.setProductCode(PRODUCT);
							reportSpec.setSubProductCode(SUBPRODUCT);
							reportSpec.setPreview(true);
							reportSpec.setHttpServerBase(invocationContext.httpServerBase);
							reportSpec.addFilterValue(filterVO);
							reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
							reportSpec.setAction(ACTION);
							
							generateReport();
							
							if(getErrors() != null && getErrors().size() > 0){
								log.log(Log.FINE,"!!!print unsuccessful&&&&&&&&&&&&&&&&&&&&&&");
								invocationContext.addAllError(getErrors());
								invocationContext.target = PRINT_UNSUCCESSFUL;
								return;
							}
					        log.exiting("MRA_DEFAULTS","PrintCommand exit");
							invocationContext.target = getTargetPage();
        			}
		
	}
}