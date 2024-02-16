/**
 * PrintCommand.java Created on Mar 30, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoicesettlement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPASettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.InvoiceSettlementSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.InvoiceSettlementForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-4823
 *
 */
public class PrintCommand extends AbstractPrintCommand{
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	private static final String ACTION = "printSettlementDetails";
	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";
	private static final String SCREEN_ID ="mailtracking.mra.gpabilling.invoicesettlement";  
	private static final String REPORT_ID="RPRMTK075";
	private static final String SETTLEMENT_STATUS="mailtracking.mra.invoicestatus";
	private static final String RESOURCE_BUNDLE_KEY="invoicesettlement";
	
	private static final String PRINT_SUCCESS="print_success";
	private static final String PRINT_UNSUCCESSFUL="normal-report-error-jsp";
	public void execute(InvocationContext invocationcontext)
	throws CommandInvocationException {
		log.entering("MAILTRACKING_GPABILLING","Print Command entered");
		InvoiceSettlementForm form = (InvoiceSettlementForm)invocationcontext.screenModel;
		InvoiceSettlementSession session =(InvoiceSettlementSession)getScreenSession(MODULE_NAME,SCREEN_ID);
		InvoiceSettlementFilterVO filterVO=new InvoiceSettlementFilterVO();
		Collection<GPASettlementVO> gpaSettlementVOs = new ArrayList<GPASettlementVO>();
//		Collection<GPASettlementVO> gpaSettlementVOs = session.getGPASettlementVO();
//		if(gpaSettlementVOs!=null && gpaSettlementVOs.size()>0){
//			Collection<InvoiceSettlementVO> invoiceSettlementVOs= gpaSettlementVOs.iterator().next().getInvoiceSettlementVOs();
//			for(InvoiceSettlementVO invoiceSettlementVO: invoiceSettlementVOs){
//				filterVO.setCompanyCode(invoiceSettlementVO.getCompanyCode());
//				filterVO.setGpaCode(invoiceSettlementVO.getGpaCode());
//				filterVO.setInvoiceNumber(invoiceSettlementVO.getInvoiceNumber());
//				filterVO.setSettlementStatus(invoiceSettlementVO.getSettlementStatus());
//							}
//		}
		if(session.getInvoiceSettlementFilterVO()!=null){
			gpaSettlementVOs = session.getGPASettlementVO();
		
			filterVO=session.getInvoiceSettlementFilterVO();
			filterVO.setSettlementReferenceNumber(gpaSettlementVOs.iterator().next().getSettlementId());
		}
		else{
			String companyCode=getApplicationSession().getLogonVO().getCompanyCode();
			filterVO.setCompanyCode(companyCode);

			String gpaCode=form.getGpaCodeFilter();
			String invNumber=form.getInvRefNumberFilter();
			String fromDate=form.getFromDate();
			String toDate=form.getToDate();
			String settlementStatus=form.getSettlementStatusFilter();
			//Changed for CR ICRD-7316
			String gpaName = form.getGpaNameFilter();
			String invoiceStatus = form.getInvoiceStatusFilter();
			String chequeNo= form.getChequeNumberFilter();

			if(gpaCode!=null && gpaCode.trim().length()>0){
				filterVO.setGpaCode(gpaCode.trim().toUpperCase());
			}
			if(invNumber!=null && invNumber.trim().length()>0){
				filterVO.setInvoiceNumber(invNumber.trim().toUpperCase());
			}
			if(fromDate!=null && fromDate.trim().length()>0){
				filterVO.setFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(fromDate));
			}
			if(toDate!=null && toDate.trim().length()>0){
				filterVO.setToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false).setDate(toDate));
			}
			if(invoiceStatus != null && invoiceStatus.trim().length()>0){
				filterVO.setSettlementStatus(invoiceStatus.trim().toUpperCase());
			}    	
			if(gpaName != null && gpaName.trim().length()>0){
				filterVO.setGpaName(gpaName);
			}
			if(chequeNo != null && chequeNo.trim().length()>0){
				filterVO.setChequeNumber(chequeNo);	

			}
		}
		// Added by A-5235 for ICRD-26293 begin
		LogonAttributes logonAtrributes = getApplicationSession().getLogonVO();
        Collection<String> oneTimeVOs = new ArrayList<String>();
        Map<String, Collection<OneTimeVO>> oneTimeValues = null;
        oneTimeVOs.add(SETTLEMENT_STATUS);
        try {
                oneTimeValues = (new SharedDefaultsDelegate()).findOneTimeValues(
                                logonAtrributes.getCompanyCode(), oneTimeVOs);
        } catch (BusinessDelegateException e) {
                log.log(Log.SEVERE, "Exception caught!");
        }
        // ICRD-26293 Ends
		//Map<String, Collection<OneTimeVO>> oneTimMap = session.getOneTimeVOs();
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(form.getProduct());
		reportSpec.setSubProductCode(form.getSubProduct());
		reportSpec.setPreview(true);
		if(oneTimeValues!=null && oneTimeValues.size()>0){
			reportSpec.addExtraInfo(oneTimeValues.get(SETTLEMENT_STATUS)); 
		}
		reportSpec.setHttpServerBase(invocationcontext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setData(gpaSettlementVOs);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		generateReport();
		if(getErrors() != null && getErrors().size() > 0){
			invocationcontext.addAllError(getErrors());
			invocationcontext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationcontext.target = getTargetPage();


	}

}
