/*
 * ListULDAuditEnquiryCommand.java Created on April 03,2008
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.uld.defaults.misc.audit;


import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.business.shared.audit.vo.AuditDetailsVO;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.struts.form.uld.defaults.misc.ULDAuditEnquiryForm;
import com.ibsplc.icargo.presentation.web.session.interfaces.shared.audit.AuditEnquirySession;
import com.ibsplc.icargo.presentation.delegate.uld.defaults.ULDDefaultsDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-2667
 */

public class ListULDAuditEnquiryCommand extends BaseCommand{
	
	
	private static final String MODULE_NAME = "shared.audit";
	private static final String SCREEN_ID = "shared.audit.auditenquiry";
	private static final String SCREENLOAD_SUCCESS = "screenload_success";
	private Log log = LogFactory.getLogger("ULD_MANAGEMENT");
	   /**
	   * execute method
	   * @param invocationContext
	   * @throws CommandInvocationException
	   */
	 public void execute(InvocationContext invocationContext)
     throws CommandInvocationException {
		 	log.entering("ListULDAuditEnquiryCommand", "Entry");
		    ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			log.log(Log.INFO, "logonAttributes------------>", logonAttributes);
			AuditEnquirySession auditEnquirySession = getScreenSession(MODULE_NAME, SCREEN_ID);
			Collection<AuditDetailsVO> auditDetailsVOs = null;
			ULDAuditEnquiryForm uldAuditEnquiryForm = 
				(ULDAuditEnquiryForm) invocationContext.screenModel;
			RelocateULDVO relocateULDVO = new RelocateULDVO();
			String companyCode=logonAttributes.getCompanyCode();
			Collection<ErrorVO> errors = null;
			errors =  validateForm(uldAuditEnquiryForm);
			if(errors!=null && errors.size()>0){
				invocationContext.addAllError(errors);
				invocationContext.target = SCREENLOAD_SUCCESS;
				return;
			}
			log.log(Log.INFO, "relocateULDVO.getCompanyCode()-------->",
					relocateULDVO.getCompanyCode());
			relocateULDVO = populateRelocateVO(companyCode,uldAuditEnquiryForm);
			log.log(Log.FINE, "Relocate VO in list command", relocateULDVO);
			ULDDefaultsDelegate uldDefaultsDelegate = new ULDDefaultsDelegate();
			try {
				auditDetailsVOs = uldDefaultsDelegate.findULDAuditEnquiryDetails(relocateULDVO);
			} catch (BusinessDelegateException e) {
				// To be reviewed Auto-generated catch block
				e.getMessage();
			}
			if(auditDetailsVOs != null && auditDetailsVOs.size() > 0){
				auditEnquirySession.setAuditDetailsVOs(auditDetailsVOs);
			}else{
				auditEnquirySession.setAuditDetailsVOs(null);
				invocationContext.addError(new ErrorVO("shared.audit.auditEnquiry.msg.err.noflightDetails"));
			}
			log.log(Log.FINE, "auditDetailsVOs in ListCommand ",
					auditDetailsVOs);
			uldAuditEnquiryForm.setUldNumber("");
			uldAuditEnquiryForm.setLocation("");
			uldAuditEnquiryForm.setUldAirport("");
			uldAuditEnquiryForm.setUldSuffix("");
			log.exiting("ListULDAuditEnquiryCommand", "Exit");
			invocationContext.target = SCREENLOAD_SUCCESS;
			
	 }
	 
	 /**
	  * Function to populate RelocateVO
	  * @author A-2667
	  * @param uldAuditEnquiryForm
	  * @return RelocateULDVO
	  */ 
	 private RelocateULDVO populateRelocateVO(String companyCode,ULDAuditEnquiryForm uldAuditEnquiryForm){
		 RelocateULDVO relocateULDVO =new RelocateULDVO();
		 relocateULDVO.setCompanyCode(companyCode);
		 if(uldAuditEnquiryForm.getUldNumber() != null && 
				 uldAuditEnquiryForm.getUldNumber().trim().length() > 0){
			 relocateULDVO.setUldNumber(uldAuditEnquiryForm.getUldNumber().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getUldSuffix() != null && 
				 uldAuditEnquiryForm.getUldSuffix().trim().length() > 0){
			 relocateULDVO.setUldSuffix(uldAuditEnquiryForm.getUldSuffix().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getLocation() != null && 
				 uldAuditEnquiryForm.getLocation().trim().length() > 0){
			 relocateULDVO.setLocation(uldAuditEnquiryForm.getLocation().trim().toUpperCase());
		 }
		 if(uldAuditEnquiryForm.getUldAirport() != null && 
				 uldAuditEnquiryForm.getUldAirport().trim().length() > 0){
			 relocateULDVO.setCurrentStation(uldAuditEnquiryForm.getUldAirport().trim().toUpperCase());
		 }
		 //changed by a-3045  for bug 19986 starts
		 if(uldAuditEnquiryForm.getFacilityType() != null && 
				 uldAuditEnquiryForm.getFacilityType().trim().length() > 0){
			 relocateULDVO.setFacilityType(uldAuditEnquiryForm.getFacilityType().trim().toUpperCase());
		 }
		 //changed by a-3045  for bug 19986 ends
		 if(uldAuditEnquiryForm.getTxnFromDate() != null && !("").equals(uldAuditEnquiryForm.getTxnFromDate())) {
		    	LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		    	relocateULDVO.setTxnFromDate(fromDate.setDate(uldAuditEnquiryForm.getTxnFromDate()));
		    }
		    if(uldAuditEnquiryForm.getTxnToDate() != null && !("").equals(uldAuditEnquiryForm.getTxnToDate())) {
		    	LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
		    	relocateULDVO.setTxnToDate(toDate.setDate(uldAuditEnquiryForm.getTxnToDate()));
		    }

		 return relocateULDVO;
	 }
	 /**
	 * @param uldAuditEnquiryForm
	 * @return
	 */
	private Collection<ErrorVO> validateForm(ULDAuditEnquiryForm uldAuditEnquiryForm) {

			Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

			log.log(Log.FINE, "Dates  ---> from date", uldAuditEnquiryForm.getTxnFromDate());
			log.log(Log.FINE, "Dates >>>>>>> to date", uldAuditEnquiryForm.getTxnToDate());
			if((uldAuditEnquiryForm.getTxnFromDate() != null && 
					uldAuditEnquiryForm.getTxnFromDate().trim().length()>0) &&
				(uldAuditEnquiryForm.getTxnToDate() != null && 
						uldAuditEnquiryForm.getTxnToDate().trim().length()>0)){
				
				LocalDate toDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);
				LocalDate frmDate = new LocalDate(LocalDate.NO_STATION,
						Location.NONE, false);	
				frmDate.setDate(uldAuditEnquiryForm.getTxnFromDate());
				toDate.setDate(uldAuditEnquiryForm.getTxnToDate());
				if(toDate.isLesserThan(frmDate)){
					ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.todatelessthanfrmdate");
					errors.add(error);						
				}	
			}else{
					if (uldAuditEnquiryForm.getTxnFromDate() == null
							|| uldAuditEnquiryForm.getTxnFromDate().trim().length() == 0) {

						ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txnfrmdateerror");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);

					}
					if (uldAuditEnquiryForm.getTxnToDate() == null
							|| uldAuditEnquiryForm.getTxnToDate().trim().length() == 0) {
						ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.txntodateerror");
						error.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(error);
					}
			}
			//BUG_92309_SowmyaK_19May10
			if((uldAuditEnquiryForm.getUldNumber() == null)||(uldAuditEnquiryForm.getUldNumber().trim().length() == 0)){
				ErrorVO error = new ErrorVO("shared.audit.auditenquiry.msg.err.uldmandatory");
				error.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(error);	
			}
			//BUG_92309_SowmyaK_19May10 ends
			return errors;

		}
}
