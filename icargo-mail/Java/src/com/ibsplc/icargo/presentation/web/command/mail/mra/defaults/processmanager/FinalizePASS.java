
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.processmanager;

import java.util.ArrayList;
import java.util.Collection;



import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.ProcessManagerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.MRAProcessManagerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


public class FinalizePASS extends BaseCommand {

	private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String CLASS_NAME = "FinalizePASS";

	private static final String MODULE_NAME = "mailtracking.mra.defaults";

	private static final String SCREEN_ID = "mailtracking.mra.defaults.processmanager";

	private static final String ACTION_SUCCESS = "action_success";
	private static final String ACTION_FAILURE = "action_failure";

	private static final String NEW="N";
	private static final String INITIATED="I";
	private static final String FINALIZE_PASS_FILTER_MANDATORY="mailtracking.mra.processManager.msg.err.finalizepassfiltermandatory";
	private static final String FINALIZE_PASS_INVALID_PERIODNUMBER="mailtracking.mra.processManager.msg.err.invalidperiodnum";
	private static final String FINALIZE_PASS_INVALID_DATERANGE="mailtracking.mra.processManager.msg.err.invalidpassdaterange";
	
	private static final String BILLING_TYPE_GPA="G";


	public void execute(InvocationContext invocationContext)
			throws CommandInvocationException {
		log.entering(CLASS_NAME, "execute");
		LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		MRAProcessManagerForm processManagerForm = (MRAProcessManagerForm) invocationContext.screenModel;
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		ProcessManagerSession processManagerSession = getScreenSession(
				MODULE_NAME, SCREEN_ID);
		Page<CN51SummaryVO> invoiceVOs =null;
		Collection<ErrorVO> errors = null;
		
		if(INITIATED.equals(processManagerForm.getCompletionFlag())){
			
		errors = validateForm(processManagerForm,delegate, logonAttributes);

	    if (!errors.isEmpty()) {
	    	processManagerForm.setCompletionFlag(constructErrorString(errors));
	        invocationContext.addAllError(errors);
	        invocationContext.target = ACTION_FAILURE;
	        return;
	        
	      } 
			
	    findAllInvoices(logonAttributes,processManagerForm,delegate,processManagerSession,invocationContext);

		}

		invoiceVOs =  processManagerSession.getInvoices();

		if(invoiceVOs==null || invoiceVOs.isEmpty()){
			processManagerForm.setCompletionFlag(MRAConstantsVO.FLAG_YES);
			processManagerForm.setTotalInvoiceCount(0);
			processManagerForm.setFinalizedInvoiceCount(0);
			
		}else if(processManagerForm.getNextFetchValue()<invoiceVOs.size()){

			Collection<CN51SummaryVO> summaryVOs = new ArrayList<>();
			CN51SummaryVO summaryVO =invoiceVOs.get(processManagerForm.getNextFetchValue());
			summaryVOs.add(summaryVO);
			
			try {
				delegate.finalizeProformaInvoice(summaryVOs);
			} catch (BusinessDelegateException businessDelegateException) {
				handleDelegateException(businessDelegateException);
			    errors = businessDelegateException.getMessageVO().getErrors();
		    	processManagerForm.setCompletionFlag(constructErrorString(errors));
		        invocationContext.addAllError(errors);
		        invocationContext.target = ACTION_FAILURE;
		        return;
			}

			processManagerForm.setFinalizedInvoiceCount(processManagerForm.getFinalizedInvoiceCount()+1);
			processManagerForm.setNextFetchValue(processManagerForm.getNextFetchValue()+1);
			
			if(processManagerForm.getNextFetchValue()>=invoiceVOs.size()){
				processManagerForm.setCompletionFlag(MRAConstantsVO.FLAG_YES);
			}else{
				processManagerForm.setCompletionFlag(MRAConstantsVO.FLAG_NO);
			}
		}
		
		invocationContext.target = ACTION_SUCCESS;
		log.exiting(CLASS_NAME, "execute");
		
	}


	private void findAllInvoices(LogonAttributes logonAttributes, MRAProcessManagerForm processManagerForm,
			MailTrackingMRADelegate delegate, ProcessManagerSession processManagerSession,
			InvocationContext invocationContext) {
		Collection<ErrorVO> errors=null;
		CN51SummaryFilterVO cn51SummaryFilterVO = new CN51SummaryFilterVO();
		cn51SummaryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if (processManagerForm.getPassBillingPeriodFrom() != null
				&& processManagerForm.getPassBillingPeriodFrom().length() > 0
				&& processManagerForm.getPassBillingPeriodTo() != null
				&& processManagerForm.getPassBillingPeriodTo().length() > 0) {
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			fromDate.setDate(processManagerForm.getPassBillingPeriodFrom());
			cn51SummaryFilterVO.setFromDate(fromDate);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			toDate.setDate(processManagerForm.getPassBillingPeriodTo());
			cn51SummaryFilterVO.setToDate(toDate);
		}
		
		cn51SummaryFilterVO.setPeriodNumber(processManagerForm.getPeriodNumber());
		cn51SummaryFilterVO.setFetchAllResult(true);
		cn51SummaryFilterVO.setInvoiceStatus(NEW);

		try {
			Page<CN51SummaryVO> invoices = delegate.findAllInvoices(cn51SummaryFilterVO);

			processManagerSession.setInvoices(invoices);
			if(invoices!=null && !invoices.isEmpty()){
				processManagerForm.setTotalInvoiceCount(invoices.size());
			}
			
		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		    errors = businessDelegateException.getMessageVO().getErrors();
	    	processManagerForm.setCompletionFlag(constructErrorString(errors));
	        invocationContext.addAllError(errors);
	        invocationContext.target = ACTION_FAILURE;
	        return;
		}
		
	}





	private Collection<ErrorVO> validateForm(MRAProcessManagerForm processManagerForm,MailTrackingMRADelegate delegate,LogonAttributes logonAttributes) {
		
		Collection<ErrorVO> errors = new ArrayList<>();

		if ((processManagerForm.getPeriodNumber() == null || processManagerForm.getPeriodNumber().trim().length() == 0)
				&& (processManagerForm.getPassBillingPeriodFrom() == null
						|| processManagerForm.getPassBillingPeriodFrom().trim().length() == 0
						|| processManagerForm.getPassBillingPeriodTo() == null
						|| processManagerForm.getPassBillingPeriodTo().trim().length() == 0)) {

			ErrorVO err = new ErrorVO(FINALIZE_PASS_FILTER_MANDATORY);
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);

		}
		
		validatePeriodNumberAndDate(processManagerForm,errors,delegate, logonAttributes);

		return errors;
	}


	private void validatePeriodNumberAndDate(MRAProcessManagerForm processManagerForm, Collection<ErrorVO> errors,MailTrackingMRADelegate delegate,LogonAttributes logonAttributes) {
		
		Collection<BillingScheduleDetailsVO> billingScheduleDetailsVOs=null;
		BillingScheduleFilterVO billingScheduleFilterVO= new BillingScheduleFilterVO();
		billingScheduleFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		billingScheduleFilterVO.setBillingType(BILLING_TYPE_GPA);
		billingScheduleFilterVO.setSource(MRAConstantsVO.PASS_BILLINGPERIOD_VALIDATION);
		try {
			
				if(processManagerForm.getPeriodNumber()!=null&&processManagerForm.getPeriodNumber().trim().length()>0){
					billingScheduleFilterVO.setPeriodNumber(processManagerForm.getPeriodNumber());
				
					billingScheduleDetailsVOs= delegate.findBillingType(billingScheduleFilterVO, 1);
					
					if(billingScheduleDetailsVOs==null || billingScheduleDetailsVOs.isEmpty()){
						  ErrorVO err = new ErrorVO(FINALIZE_PASS_INVALID_PERIODNUMBER);
					      err.setErrorDisplayType(ErrorDisplayType.ERROR);
					      errors.add(err);
					}
				}
			if (errors.isEmpty() && processManagerForm.getPassBillingPeriodFrom() != null
					&& processManagerForm.getPassBillingPeriodFrom().length() > 0
					&& processManagerForm.getPassBillingPeriodTo() != null
					&& processManagerForm.getPassBillingPeriodTo().length() > 0) {

				LocalDate fromDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				fromDate.setDate(processManagerForm.getPassBillingPeriodFrom());

				LocalDate toDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				toDate.setDate(processManagerForm.getPassBillingPeriodTo());

				billingScheduleFilterVO.setBillingPeriodFromDate(fromDate);
				billingScheduleFilterVO.setBillingPeriodToDate(toDate);

				billingScheduleDetailsVOs = delegate.findBillingType(billingScheduleFilterVO, 1);

				if (billingScheduleDetailsVOs == null || billingScheduleDetailsVOs.isEmpty()) {
					ErrorVO err = new ErrorVO(FINALIZE_PASS_INVALID_DATERANGE);
					err.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(err);
				}
			}

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		    errors.addAll(businessDelegateException.getMessageVO().getErrors());
		}
		
	}

	  private String constructErrorString(Collection<ErrorVO> errors) {
		    StringBuilder str = new StringBuilder();
		    str.append("ER-");
		    for (ErrorVO err : errors) {
		      str.append(err.getErrorCode());
		      str.append("-");
		    } 
		    return str.toString();
		  }	 

}
