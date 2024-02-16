/*
 * ListDsnAuditCommand.java Created on Jul 1, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.audit;



import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.MailAuditFilterVO;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnAuditForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-5991
 *
 */
public class ListDsnAuditCommand extends BaseCommand {

	private Log log = LogFactory.getLogger("ListDespatchAuditCommand");
	
	private static final String SCREENID_AUD = "shared.audit.auditenquiry";

	private static final String MODULE_NAME_AUD = "shared.audit";
	
	private static final String SUCCESS = "list_success";
	private static final String FAILURE = "list_failure";

	/**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
									throws CommandInvocationException {
		log.entering("ListDsnAuditCommand","execute"); 
    	
    	DsnAuditForm dsnAuditForm = (DsnAuditForm)invocationContext.screenModel;
    	AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME_AUD,
				SCREENID_AUD);
    	
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	/**
    	 * Validate Mandatory fields
    	 */
    	errors = validateForm(dsnAuditForm);
    	if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = FAILURE;
			return;
		}
    	log.log(Log.FINE, "dsnAuditForm.getOoe()-->", dsnAuditForm.getOoe());
		MailAuditFilterVO mailAuditFilterVO = new MailAuditFilterVO();
	    mailAuditFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    if(dsnAuditForm.getOoe() != null && !("").equals(dsnAuditForm.getOoe())) {
	    	mailAuditFilterVO.setOoe(dsnAuditForm.getOoe().toUpperCase());
	    }
	    if(dsnAuditForm.getDoe() != null && !("").equals(dsnAuditForm.getDoe())) {
	    	mailAuditFilterVO.setDoe(dsnAuditForm.getDoe().toUpperCase());
	    }
	    if(dsnAuditForm.getCategory() != null && !("").equals(dsnAuditForm.getCategory())) {
	    	mailAuditFilterVO.setCategory(dsnAuditForm.getCategory().toUpperCase());
	    }
	    if(dsnAuditForm.getSubclass() != null && !("").equals(dsnAuditForm.getSubclass())) {
	    	mailAuditFilterVO.setSubclass(dsnAuditForm.getSubclass().toUpperCase());
	    }
	    if(dsnAuditForm.getDsn() != null && !("").equals(dsnAuditForm.getDsn())) {
	    	mailAuditFilterVO.setDsn(dsnAuditForm.getDsn().toUpperCase());
	    }
	    if(dsnAuditForm.getMailClass() != null && !("").equals(dsnAuditForm.getMailClass())) {
	    	mailAuditFilterVO.setMailClass(dsnAuditForm.getMailClass().toUpperCase());
	    }
	    if(dsnAuditForm.getYear() != null && dsnAuditForm.getYear().trim().length()>0 && !(" ").equals(dsnAuditForm.getYear())) {
	      try{	
	    	mailAuditFilterVO.setYear(Integer.parseInt(dsnAuditForm.getYear()));
	      }catch(NumberFormatException nfe){
	    	  log.log(Log.SEVERE, "Number Format Exception Caught");
	      }
	    }
	    
	    if(dsnAuditForm.getTxnFromDate() != null && !("").equals(dsnAuditForm.getTxnFromDate())) {
	    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			mailAuditFilterVO.setTxnFromDate(fromDate.setDate(dsnAuditForm.getTxnFromDate()));
	    }
	    if(dsnAuditForm.getTxnToDate() != null && !("").equals(dsnAuditForm.getTxnToDate())) {
	    	LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			mailAuditFilterVO.setTxnToDate(toDate.setDate(dsnAuditForm.getTxnToDate()));
	    }
	    /**
	     * Testing
	     */
//	    if(dsnAuditForm.getDsn() != null && !("").equals(dsnAuditForm.getDsn())) {
//	    	if(("1005").equals(dsnAuditForm.getDsn())) {
//		    	invocationContext.addError(new ErrorVO("mailtracking.defaults.invalidcarrier"));
//		    	invocationContext.target = SUCCESS;
//		    	return;
//	    	}
//	    }
	    
	    Collection<AuditDetailsVO> auditDetailsVOs = new ArrayList<AuditDetailsVO>();
	    try {
	    	auditDetailsVOs = new MailTrackingDefaultsDelegate().findDSNAuditDetails(mailAuditFilterVO);
	    	log.log(Log.FINE, "auditDetailsVOs DSN:", auditDetailsVOs);
	    }catch(BusinessDelegateException businessDelegateException) {
	    	handleDelegateException(businessDelegateException);
	    }
	    
	    if (auditDetailsVOs == null || auditDetailsVOs.size() == 0) {
	    	auditDetailsVOs = new ArrayList<AuditDetailsVO>();
	    	invocationContext.addError(new ErrorVO("shared.audit.auditEnquiry.msg.err.noflightDetails"));
	    }
	    
	    auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
	    invocationContext.target = SUCCESS;
	    
	    log.exiting("ListDsnAuditCommand","execute"); 

	}
	
	
	/**
	 * Used to validate for mandatory checks
	 * 
	 * @param dsnAuditForm DsnAuditForm
	 * @return Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(DsnAuditForm dsnAuditForm) {

		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

		log.log(Log.FINE, "Dates  ---> from date", dsnAuditForm.getTxnFromDate());
		log.log(Log.FINE, "Dates >>>>>>> to date", dsnAuditForm.getTxnToDate());
		if((dsnAuditForm.getTxnFromDate() != null && 
				dsnAuditForm.getTxnFromDate().trim().length()>0) &&
			(dsnAuditForm.getTxnToDate() != null && 
					dsnAuditForm.getTxnToDate().trim().length()>0)){
			
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);
			LocalDate frmDate = new LocalDate(LocalDate.NO_STATION,
					Location.NONE, false);	
			frmDate.setDate(dsnAuditForm.getTxnFromDate());
			toDate.setDate(dsnAuditForm.getTxnToDate());
			if(toDate.isLesserThan(frmDate)){
				ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.todatelessthanfrmdate");
				errors.add(error);						
			}	
		}else{
				if (dsnAuditForm.getTxnFromDate() == null
						|| dsnAuditForm.getTxnFromDate().trim().length() == 0) {

					ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txnfrmdateerror");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);

				}
				if (dsnAuditForm.getTxnToDate() == null
						|| dsnAuditForm.getTxnToDate().trim().length() == 0) {
					ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txntodateerror");
					error.setErrorDisplayType(ErrorDisplayType.ERROR);
					errors.add(error);
				}
		}

		return errors;

	}
	
	

}
