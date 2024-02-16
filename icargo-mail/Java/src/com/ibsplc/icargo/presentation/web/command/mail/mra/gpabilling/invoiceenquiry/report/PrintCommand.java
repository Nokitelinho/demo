/*
 * PrintCommand.java Created on Aug 13,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package  com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.invoiceenquiry.report;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GpaBillingInvoiceEnquiryFilterVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.framework.web.command.report.AbstractPrintCommand;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.gpabilling.GPABillingInvoiceEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GPABillingInvoiceEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * @author A-3108
 * 
 *
 */
public class PrintCommand extends AbstractPrintCommand {
	private Log log = LogFactory.getLogger("MRA_GPABILLING");
	
	private static final String MODULE = "mailtracking.mra.gpabilling";
	private static final String SCREENID = "mailtracking.mra.gpabilling.gpabillinginvoiceenquiry";
	private static final String REPORT_ID = "RPTLST282";
	private static final String RESOURCE_BUNDLE_KEY = "gpabillinginvoiceenquiry";
	private static final String ACTION = "printgpabillinginvoiceenquiry";
	private static final String PRINT_UNSUCCESSFUL = "normal-report-error-jsp";
	private static final String BILLING_STATUS = "mra.gpabilling.billingstatus";
	private static final String INVOICE_STATUS = "mra.gpabilling.invoicestatus";
	private static final String KEY_NO_INVNUM = "mailtracking.mra.gpabilling.cn51cn66.noinvnum";
	private static final String BLANK="";
	private static final String CLASS_NAME = "PrintCommand";
	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("MRA_GPABILLING","Print Command entered");
		GPABillingInvoiceEnquiryForm gPABillingInvoiceEnquiryForm = 
			(GPABillingInvoiceEnquiryForm)invocationContext.screenModel;
		GPABillingInvoiceEnquirySession session = (GPABillingInvoiceEnquirySession)getScreenSession(
				MODULE, SCREENID);
		GpaBillingInvoiceEnquiryFilterVO filterVO 	= new GpaBillingInvoiceEnquiryFilterVO();
		
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode();
		errors=validateForm(gPABillingInvoiceEnquiryForm,companyCode);
		
		if(errors!=null && errors.size()>0){
			invocationContext.addAllError(errors);
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		filterVO.setCompanyCode(companyCode);
		if(gPABillingInvoiceEnquiryForm.getInvoiceNo()!=null && gPABillingInvoiceEnquiryForm.getInvoiceNo().trim().length()>0){
		filterVO.setInvoiceNumber(gPABillingInvoiceEnquiryForm.getInvoiceNo());
		}
		
		if(gPABillingInvoiceEnquiryForm.getGpaCode()!=null&& gPABillingInvoiceEnquiryForm.getGpaCode().trim().length()>0){
		filterVO.setGpaCode(gPABillingInvoiceEnquiryForm.getGpaCode());
		}
		if(gPABillingInvoiceEnquiryForm.getGpaName()!=null&& gPABillingInvoiceEnquiryForm.getGpaName().trim().length()>0){
			filterVO.setGpaName(gPABillingInvoiceEnquiryForm.getGpaName());
			}
		log.log(Log.INFO, "filterVo", filterVO);
		/*
		 * Getting OneTime values
		 */
		Map<String, Collection<OneTimeVO>> oneTimes = getOneTimeDetails();
		log.log(Log.INFO, "OneTimes+++===>", oneTimes);
		Collection<OneTimeVO> invoiceStatus = new ArrayList<OneTimeVO>();
		Collection<OneTimeVO> billingStatus = new ArrayList<OneTimeVO>();
		if (oneTimes != null) {
			invoiceStatus = oneTimes.get(INVOICE_STATUS);
			
		}
		if (oneTimes != null) {
			billingStatus = oneTimes.get(BILLING_STATUS);
			
		}
		ReportSpec reportSpec = getReportSpec();
		reportSpec.setReportId(REPORT_ID);
		reportSpec.setProductCode(gPABillingInvoiceEnquiryForm.getProduct());
		reportSpec.setSubProductCode(gPABillingInvoiceEnquiryForm.getSubProduct());
		reportSpec.setPreview(true);
		reportSpec.setHttpServerBase(invocationContext.httpServerBase);
		reportSpec.addFilterValue(filterVO);
		reportSpec.setResourceBundle(RESOURCE_BUNDLE_KEY);
		reportSpec.setAction(ACTION);
		reportSpec.addExtraInfo(invoiceStatus);
		reportSpec.addExtraInfo(billingStatus);
		generateReport();
		
		if(getErrors() != null && getErrors().size() > 0){
			
			invocationContext.addAllError(getErrors());
			invocationContext.target = PRINT_UNSUCCESSFUL;
			return;
		}
		
		log.exiting("MRA_GPABILLING","PrintCommand exit");
		invocationContext.target = getTargetPage();
	}
	 /* *//**
     * Validating the Filter parameters
     * @param maintainCCCollectorForm
     * @param companyCode
     * @return
     */
    private Collection<ErrorVO> validateForm(
    		GPABillingInvoiceEnquiryForm form , String companyCode){
    	
    	log.entering(CLASS_NAME,"validateForm");
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		ErrorVO cn51ErrorVO = null;
		
		String invNumber = form.getInvoiceNo();
		
		
		if(invNumber == null || ("").equals(invNumber.trim())){
			cn51ErrorVO= new ErrorVO(KEY_NO_INVNUM);
				if(errors == null){
					errors = new ArrayList<ErrorVO>();
				}
				errors.add(cn51ErrorVO);			
		}  
		log.exiting(CLASS_NAME,"validateForm");
        return errors;
    }
    /**
	 * This method is used to get the one time details
	 * 
	 * @return
	 */
	public Map<String, Collection<OneTimeVO>> getOneTimeDetails() {
		// server call for onetime
		log.entering("PrintCommand", "getOneTimeDetails");
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> error = new ArrayList<ErrorVO>();
		try {
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add(BILLING_STATUS);
			fieldValues.add(INVOICE_STATUS);
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(
					getApplicationSession().getLogonVO().getCompanyCode(),
					fieldValues);

		} catch (BusinessDelegateException businessDelegateException) {
			businessDelegateException.getMessageVO().getErrors();
			businessDelegateException.getMessage();
			error = handleDelegateException(businessDelegateException);
		}
		log.exiting("PrintCommand", "getOneTimeDetails");
		return oneTimes;
	}
	}	

