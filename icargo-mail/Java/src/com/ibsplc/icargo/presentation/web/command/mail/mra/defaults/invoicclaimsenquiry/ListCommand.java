/*
 * ListCommand.java Created on Jul 30, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.defaults.invoicclaimsenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicClaimsFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.mra.defaults.InvoicClaimsEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.InvoicClaimsEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;

/**
 *
 * @author a-2270
 *
 */
public class ListCommand extends BaseCommand {

	private static final String MODULE = "mailtracking.mra.defaults";

	private static final String SCREENID = "mailtracking.mra.defaults.invoicclaimsenquiry";

	private static final String LISTDETAILS_SUCCESS = "list_success";

	private static final String LISTDETAILS_FAILURE = "list_failure";

	private static final String CLASS_NAME = "ListCommand";

	private static final String BLANK = "";

	private static final String KEY_NO_RESULTS_FOUND = "mailtracking.mra.defaults.invoicclaimsenquiry.nodatafound";

	private static final String KEY_MANDATORYCHK = "mailtracking.mra.defaults.invoicclaimsenquiry.mandatorychk";

	private static final String ERROR_KEY_NO_INVALID_DATE = "mailtracking.mra.defaults.invoicclaimsenquiry.invalidDaterange";

	private Log log = LogFactory.getLogger("MRA_DEFAULTS");

	/**
	 * execute method
	 *
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {

		log.entering(CLASS_NAME, "execute");
		InvoicClaimsEnquiryForm form = (InvoicClaimsEnquiryForm)invocationContext.screenModel;
		InvoicClaimsEnquirySession session = (InvoicClaimsEnquirySession)getScreenSession(MODULE,SCREENID);
		ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		String companyCode = logonAttributes.getCompanyCode().toUpperCase();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailInvoicClaimsFilterVO filterVO = new MailInvoicClaimsFilterVO();

		if(("").equals(form.getGpaCode()) || BLANK.equals(form.getFromDate()) || BLANK.equals(form.getToDate())){
		session.removeMailInvoicClaimsEnquiryVOs();	
		errors.add(new ErrorVO(KEY_MANDATORYCHK));
		invocationContext.addAllError(errors);
		invocationContext.target = LISTDETAILS_FAILURE;
		return;
		}else if(!validateDate(form.getFromDate(),form.getToDate())){
		session.removeMailInvoicClaimsEnquiryVOs();	
		errors.add(new ErrorVO(ERROR_KEY_NO_INVALID_DATE));
		invocationContext.addAllError(errors);
		invocationContext.target = LISTDETAILS_FAILURE;
		return;
		}

		Page<MailInvoicClaimsEnquiryVO> mailInvoicClaimsEnquiryVOs = null;
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		log.log(Log.INFO, "poaCode is -->", form.getGpaCode());
		log.log(Log.INFO, "Todate is -->", form.getToDate());
		filterVO.setCompanyCode(companyCode);
		filterVO.setPoaCode(form.getGpaCode());
		filterVO.setClaimStatus(form.getClaimStatus());
		filterVO.setClaimType(form.getClaimType());
		filterVO.setFromDate(convertToDate(form.getFromDate()));
		filterVO.setToDate(convertToDate(form.getToDate()));
		//filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));

		log.log(Log.INFO, "FilterVo to Delegate-->", filterVO);
			if(InvoicClaimsEnquiryForm.PAGINATION_MODE_FROM_LIST.equals(form.getNavigationMode())
					|| form.getNavigationMode() == null){
				
				filterVO.setTotalRecordsCount(-1);
				filterVO.setPageNumber(1); 
				log.log(Log.INFO, "PAGINATION_MODE_FROM_NAVIGATION_LIST ");
				
			}else if(InvoicClaimsEnquiryForm.PAGINATION_MODE_FROM_NAVIGATION_LINK.equals(form.getNavigationMode()))
			{			
				filterVO.setTotalRecordsCount(session.getTotalRecordsCount());
				filterVO.setPageNumber(Integer.parseInt(form.getDisplayPage()));
				
			}			
			
		// Added by A-5183 for < ICRD-21098 > Ends
		
		
		/*
		 * calling MailTrackingMRADelegate
		 */
		try {
			log.log(Log.FINE,
			"Inside try : Calling findInvoicEnquiryDetails");
			mailInvoicClaimsEnquiryVOs = delegate.findInvoicClaimsEnquiryDetails(filterVO);
			log.log(Log.FINE, "mailInvoicClaimsEnquiryVOs from Server:--> ",
					mailInvoicClaimsEnquiryVOs);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
			log.log(Log.FINE, "caught Exception");
		}

		// for setting the invocation context in case of success
		if (mailInvoicClaimsEnquiryVOs !=null&&mailInvoicClaimsEnquiryVOs.size()>0
				|| mailInvoicClaimsEnquiryVOs !=null && mailInvoicClaimsEnquiryVOs.size()>0) {

			session.setMailInvoicClaimsEnquiryVOs(mailInvoicClaimsEnquiryVOs);
			session.setTotalRecordsCount(mailInvoicClaimsEnquiryVOs.getTotalRecordCount());
			
			invocationContext.target = LISTDETAILS_SUCCESS;
		}
		// for setting the invouaction context in case of failure
		if(mailInvoicClaimsEnquiryVOs ==null || mailInvoicClaimsEnquiryVOs.size()==0
				&& mailInvoicClaimsEnquiryVOs ==null || mailInvoicClaimsEnquiryVOs.size()==0){
			log.log(Log.FINE, "results from Server is :::::::::::::::NULL::");
			session.removeMailInvoicClaimsEnquiryVOs();	
			errors.add(new ErrorVO(KEY_NO_RESULTS_FOUND));
			invocationContext.addAllError(errors);
			invocationContext.target = LISTDETAILS_FAILURE;
		}
		form.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		log.exiting(CLASS_NAME, "execute");
	}
	/**
	 *
	 * @param date
	 * @return LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !("").equals(date)){

			return(new LocalDate
					(LocalDate.NO_STATION,Location.NONE,false).setDate( date ));
		}
		return null;
	}

	/**
	 * validating fromdate and todate
	 * @param fromDate
	 * @param toDate
	 * @return
	 */
	private boolean validateDate( String fromDate, String toDate ){

		if( ((toDate != null)&&(toDate.trim().length()>0)) &&
				((fromDate != null) &&(fromDate.trim().length()>0))) {

			return DateUtilities.isLessThan( fromDate, toDate, LocalDate.CALENDAR_DATE_FORMAT );

		}else{

			return true;
		}
	}

}


