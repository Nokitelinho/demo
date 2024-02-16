/*
 * GenerateInvoiceCommand.java Created on Jun 20, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.gpabilling.generateinvoice;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.GenerateGPABillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jun 20, 2007   Kiran S P 				Initial draft , Added method execute
 *  0.2         jul 12 2007    Sandeep.T                modified for removing the repprdfrm and repprdto from screen
 *  0.3			Jul 25 2016    Sruthi S 				Modified for removing GPA mandatory validation and adding locks
 */
public class GenerateInvoiceCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA GPABILLING");

	private static final String CLASS_NAME = "GenerateInvoiceCommand";

	private static final String MODULE_NAME = "mailtracking.mra.gpabilling";

	private static final String SCREENID = "mailtracking.mra.gpabilling.generateinvoice";

	private static final String GEN_SUCCESS = "generate_success";

	private static final String GEN_FAILURE = "generate_failure";

	private static final String ERROR_DATES_MANDATORY ="mailtracking.mra.gpabilling.generateinvoice.err.blgprdmandatory";
	
	private static final String INVALID_DATE_RANGE ="mailtracking.mra.gpabilling.generateinvoice.err.fromdategreaterthantodate";
	
	private static final String INVALID_BILLING_SOURCE ="mailtracking.mra.gpabilling.generateinvoice.err.invalidGPABillingSource";

	private static final String STATUS_NOT_ACTIVE="mailtracking.mra.gpabilling.generateinvoice.err.statusNotActive";
	
	private static final String ACTIVE="ACTIVE";
	
	private static final String OBJECT_LOCKED="mailtracking.mra.gpabilling.objectalreadylocked";
	private static final String CONFRM_MSG="mailtracking.mra.gpabilling.generateinvoiceconfirmation";
	
	private static final String CONFRM_PROFORMA_MSG="mailtracking.mra.gpabilling.generateproformainvoiceconfirmation";
	
	private static final String GPABILLING_INVOICE = "GB";


	private static final String BLGPERIOD_NOT_VALID = "mailtracking.mra.gpabilling.notvalidbillingperiod";
	
	private static final String NOT_A_PASSPA = "mailtracking.mra.gpabilling.notpasspa";
	private static final String FINAL_NOT_PASSPA = "mailtracking.mra.gpabilling.finalinvoicecannotbegeneratedforpass";
	private static final String EMPTY_STRING = "";
	private static final String PROFORMA = "P";
	private static final String FINAL = "F";
	private static final String PARCODE_PASS = "PASBLGID";
	
	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.web.command.Command#execute(com.ibsplc.icargo.framework.web.command.InvocationContext)
	 *	Added by 			: A-4809 on 03-Jan-2014
	 * 	Used for 	:	ICRD-42160 generateInvoice and Send email
	 *	Parameters	:	@param arg0
	 *	Parameters	:	@throws CommandInvocationException 
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {
		log.entering(MODULE_NAME, CLASS_NAME);



    	ApplicationSessionImpl applicationSessionImpl = getApplicationSession();
		LogonAttributes logonAttributes = applicationSessionImpl.getLogonVO();
		GenerateGPABillingInvoiceForm generateInvoiceForm =(GenerateGPABillingInvoiceForm)invocationContext.screenModel;
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		boolean isLocked = false;
		boolean isCorrectPeriod = true;
		GenerateInvoiceFilterVO generateInvoiceFilterVO =new GenerateInvoiceFilterVO();
		HashMap<String,Collection<PostalAdministrationDetailsVO> >postalAdministrationDetailsVOs = new HashMap<String,Collection<PostalAdministrationDetailsVO> >();
		Collection<PostalAdministrationDetailsVO> paDetails = null;
    	
		
    	errors = validateForm(generateInvoiceForm);
    	if(errors != null && errors.size() > 0 ){
			log.log(Log.INFO," ######### GEN_FAILURE ######");
			invocationContext.addAllError(errors);
			invocationContext.target = GEN_FAILURE;
			return;
		}
		if(generateInvoiceForm.getGpacode()!= null && !("".equals(generateInvoiceForm.getGpacode().trim()))){
			errors = validateGpaCode(generateInvoiceForm,logonAttributes);
		}
		if(errors != null && errors.size() > 0 ){
    		log.log(Log.INFO," ######### GEN_FAILURE ####");
    		invocationContext.addAllError(errors);
    		invocationContext.target = GEN_FAILURE;
    		return;
    	}

		//Re-arranged the code by A-7794 as part of ICRD-258160
		String billingSource = null;
		String billingFrequency = null; 
		generateInvoiceFilterVO.setBillingPeriodFrom(convertToDate(generateInvoiceForm.getBlgPeriodFrom()));
		generateInvoiceFilterVO.setBillingPeriodTo(convertToDate(generateInvoiceForm.getBlgPeriodTo()));
		generateInvoiceFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceFilterVO.setCountryCode(generateInvoiceForm.getCountry());
		generateInvoiceFilterVO.setInvoiceType(generateInvoiceForm.getInvoiceType());
			
		PostalAdministrationVO gpaValidationVO = null;  
		if(generateInvoiceForm.getGpacode()!=null && !generateInvoiceForm.getGpacode().isEmpty()){
		 gpaValidationVO = validateGPABillingSource
				(getApplicationSession().getLogonVO().getCompanyCode(),
						generateInvoiceForm.getGpacode());
		}
		if(gpaValidationVO !=null){
			postalAdministrationDetailsVOs=gpaValidationVO.getPostalAdministrationDetailsVOs();
			Optional<PostalAdministrationDetailsVO> passDetailVO =Optional.empty();
			if(postalAdministrationDetailsVOs!= null && postalAdministrationDetailsVOs.size()>0){
				
				Collection<PostalAdministrationDetailsVO> invDetails =postalAdministrationDetailsVOs.get("INVINFO");
				if(invDetails!=null && !invDetails.isEmpty()){
					passDetailVO= 	invDetails.stream().filter(paDet->PARCODE_PASS.equals(paDet.getParCode())).findAny();
				}
				
				
				if(MRAConstantsVO.INV_TYP_PASS.equals(generateInvoiceFilterVO.getInvoiceType()) && !passDetailVO.isPresent()){
					errors = new ArrayList<>(1);
					errors.add(new ErrorVO(NOT_A_PASSPA));
					invocationContext.target = GEN_FAILURE;
					invocationContext.addAllError(errors);
					return;
				}
				if(MRAConstantsVO.INV_TYP_FINAL.equals(generateInvoiceFilterVO.getInvoiceType()) && passDetailVO.isPresent()){
					errors = new ArrayList<>(1);
					errors.add(new ErrorVO(FINAL_NOT_PASSPA));
					invocationContext.target = GEN_FAILURE;
					invocationContext.addAllError(errors);
					return;
				}
				paDetails=postalAdministrationDetailsVOs.get("BLGINFO");
				log.log(Log.FINE, " <<<<<-------- paDetails--->>",
						paDetails);
				for(PostalAdministrationDetailsVO postalAdministrationDetailsVO:paDetails){

					Calendar filterValidFrom=null;
					Calendar filterValidTo=null;
					Calendar fromDate=null;
					Calendar toDate=null;
					filterValidFrom=generateInvoiceFilterVO.getBillingPeriodFrom().toCalendar();
					filterValidTo= generateInvoiceFilterVO.getBillingPeriodTo().toCalendar();
					fromDate=postalAdministrationDetailsVO.getValidFrom().toCalendar();
					toDate=postalAdministrationDetailsVO.getValidTo().toCalendar();

					if(filterValidTo.equals(toDate)||filterValidTo.before(toDate)){

						if(filterValidFrom.equals(fromDate)||filterValidFrom.after(fromDate)){

							billingSource=postalAdministrationDetailsVO.getBillingSource();
							billingFrequency = postalAdministrationDetailsVO.getBillingFrequency();
						}
					}
				}
			}
		}
		
		generateInvoiceFilterVO.setBillingFrequency(billingFrequency);
		if(gpaValidationVO !=null){
		isCorrectPeriod = validateGpaBillingPeriod(generateInvoiceFilterVO);
		if(!isCorrectPeriod){
			errors = new ArrayList<ErrorVO>(1);
			errors.add(new ErrorVO(BLGPERIOD_NOT_VALID));
			invocationContext.target = GEN_FAILURE;
			invocationContext.addAllError(errors);
			return;

		}
		log.log(Log.FINE, " the billing source ", billingSource);
		if( ! PostalAdministrationVO.BILLING_SOURCE_BILLING.equals(billingSource) ) {
        	log.log(Log.INFO," invoice cannot be generated ");
        	errors = new ArrayList<ErrorVO>(1);
    		errors.add(new ErrorVO(INVALID_BILLING_SOURCE));
    		invocationContext.target = GEN_FAILURE;
    		invocationContext.addAllError(errors);
    		return;
        }            
			if(!(ACTIVE.equalsIgnoreCase(gpaValidationVO.getStatus()))){
        	errors = new ArrayList<ErrorVO>(1);
    		errors.add(new ErrorVO(STATUS_NOT_ACTIVE));
    		invocationContext.target = GEN_FAILURE;
    		invocationContext.addAllError(errors);
    		return;

        }
        }
		/**
		 * Add lock before generating invoice and if lock 
		 * is present generate Invoice should not be called 
		 */
/*	Collection<LockVO> lockvos=new ArrayList<LockVO>();
		TransactionLockVO generateInvoiceLockVO = new TransactionLockVO("GENINVMRA");
		generateInvoiceLockVO.setAction("GENINV");
		generateInvoiceLockVO.setClientType(ClientType.WEB);
		generateInvoiceLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		generateInvoiceLockVO.setDescription("Lock on Invoice generate");
		generateInvoiceLockVO.setRemarks("MANUAL LOCK");
		generateInvoiceLockVO.setStationCode(logonAttributes.getStationCode());
		lockvos.add(generateInvoiceLockVO);
		Collection<LockVO> acquiredLocks = new ArrayList<LockVO>();
		log.log(Log.FINE, " going for locking-=-=->"+lockvos);*/
		Collection<LockVO> acquiredLocks = new ArrayList<LockVO>();
		try{
			acquiredLocks = (Collection<LockVO>)delegate.generateInvoiceLock(logonAttributes.getCompanyCode());
			log.log(Log.FINE, " Lock VOs acquiredLocks-=-=->"+acquiredLocks);
		}
		catch(Exception exception){
			//exception.printStackTrace();
			log.log(Log.INFO, " error msg \n\n ",exception.getMessage());
			isLocked = true;
			log.log(Log.SEVERE, "Already locked");	
			errors = new ArrayList<ErrorVO>(1);
			errors.add(new ErrorVO(OBJECT_LOCKED));
		}
/*		TransactionLockVO txLockVO = new TransactionLockVO(
				PROCEDURE_GENERATE_INV);
		txLockVO.setAction(LOCK_ACTION);
		txLockVO.setCompanyCode(logonAttributes.getCompanyCode());
		txLockVO.setClientType(ClientType.WEB);
		txLockVO.setStationCode(logonAttributes.getStationCode());
		txLockVO.setDescription(DESCRIPTION_INVOICE);
		txLockVO.setRemarks(REMARKS_INVOICE);
		txLockVO.setScreenId(SCREENID_GENINV);*/
		if(errors != null && errors.size() > 0){
			invocationContext.target = GEN_FAILURE;
			invocationContext.addAllError(errors);
			return;
		}
		if(!isLocked){
			
    	
			if(generateInvoiceForm.getGpacode() != null && generateInvoiceForm.getGpacode().length() > 0){

    		//Added by A-6991 for ICRD-211662 Starts
    		if(!"Y".equalsIgnoreCase(gpaValidationVO.getProformaInvoiceRequired()) 
    				&& "P".equalsIgnoreCase(generateInvoiceForm.getInvoiceType())){
    			ErrorVO error = null; 
				Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
				error = new ErrorVO(CONFRM_PROFORMA_MSG);
		        error.setErrorDisplayType(ErrorDisplayType.ERROR);
		        saveerrors.add(error);
		        invocationContext.target = GEN_FAILURE;  
		        invocationContext.addAllError(saveerrors);  
		        return;
    		}
    		if(EMPTY_STRING.equals(generateInvoiceForm.getInvoiceType())){
    			if("Y".equalsIgnoreCase(gpaValidationVO.getProformaInvoiceRequired())){
    				generateInvoiceFilterVO.setInvoiceType(PROFORMA);
    				
    			}else{
    				generateInvoiceFilterVO.setInvoiceType(FINAL);
    			}
    		}else{
    			generateInvoiceFilterVO.setInvoiceType(generateInvoiceForm.getInvoiceType());
    		}    		    
    		generateInvoiceFilterVO.setAddNew(generateInvoiceForm.isAddNew());

    		//Added by A-6991 for ICRD-211662 Ends
    		
			// Added by A-3434 for CR ICRD-114599 on 01/OCT/2015
				//Same code is placed before creating lock by A-7794 as part of ICRD-258160
    	/*isCorrectPeriod = validateGpaBillingPeriod(generateInvoiceFilterVO);
		 if(!isCorrectPeriod){
			 errors = new ArrayList<ErrorVO>(1);
			 errors.add(new ErrorVO(BLGPERIOD_NOT_VALID));
			 invocationContext.target = GEN_FAILURE;
			 invocationContext.addAllError(errors);
			 return;

			 }*/
			}
		invoiceTransactionLogVO.setCompanyCode(logonAttributes.getCompanyCode());
		invoiceTransactionLogVO.setInvoiceType(GPABILLING_INVOICE);
		invoiceTransactionLogVO.setTransactionDate ( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
   		invoiceTransactionLogVO.setPeriodFrom(generateInvoiceFilterVO.getBillingPeriodFrom());
   		invoiceTransactionLogVO.setPeriodTo(generateInvoiceFilterVO.getBillingPeriodTo());
   		invoiceTransactionLogVO.setInvoiceGenerationStatus("I");
   		invoiceTransactionLogVO.setStationCode(logonAttributes.getStationCode());
		invoiceTransactionLogVO.setRemarks("Invoice Generation Initiated");
		invoiceTransactionLogVO.setSubSystem("M");
		invoiceTransactionLogVO.setTransactionTime( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setTransactionTimeUTC( new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
	    invoiceTransactionLogVO.setUser(logonAttributes.getUserId());
    	try{
   		invoiceTransactionLogVO = delegate.initiateTransactionLogForInvoiceGeneration( invoiceTransactionLogVO );
	       }catch(BusinessDelegateException ex){
		errors = this.handleDelegateException(ex);
	    }
	       log.log(Log.SEVERE, " invoiceTransactionLogVO before-- " + invoiceTransactionLogVO);
	    	// Added by A-3434 for CR ICRD-114599 on 01/OCT/2015 ends
			generateInvoiceFilterVO.setGpaCode(generateInvoiceForm.getGpacode());
			generateInvoiceFilterVO.setGpaName(generateInvoiceForm.getGpaname());
			// Added by A-3434 for CR ICRD-114599 on 01/OCT/2015
		//generateInvoiceFilterVO.setInvoiceType("PB"); //Commented by A-6991 for CR ICRD-211662 as it has been already set
		generateInvoiceFilterVO.setInvoiceLogSerialNumber(invoiceTransactionLogVO.getSerialNumber());
		generateInvoiceFilterVO.setTransactionCode(invoiceTransactionLogVO.getTransactionCode());
	    	// Added by A-3434 for CR ICRD-114599 on 01/OCT/2015 ends
	    	log.log(Log.SEVERE, " generateInvoiceFilterVO before-- " + generateInvoiceFilterVO);
    	try{
				delegate.generateInvoiceTK(generateInvoiceFilterVO);
			} catch (BusinessDelegateException e) {
				handleDelegateException(e);
				errors = handleDelegateException(e);
    	}
/*			if(errors!=null && errors.size()>0){
    		invocationContext.target = GEN_FAILURE;
    		invocationContext.addAllError(errors);
    		return;
			}else{*/
				//Added by A-4809 to show confirmation message for TK Starts
				ErrorVO error = null; 
				Collection<ErrorVO> saveerrors = new ArrayList<ErrorVO>();
				error = new ErrorVO(CONFRM_MSG);
		        error.setErrorDisplayType(ErrorDisplayType.INFO);
		        saveerrors.add(error);
				invocationContext.addAllError(saveerrors);

    	invocationContext.target = GEN_SUCCESS;
    	log.exiting(CLASS_NAME,"execute");
				//Added by A-4809 to show confirmation message for TK Ends
			//}
			log.exiting(MODULE_NAME, CLASS_NAME);
		}
	}
	/**
	 * 
	 * 	Method		:	GenerateInvoiceCommand.validateGPABillingSource
	 *	Added by 	:	A-4809 on 03-Jan-2014
	 * 	Used for 	:	to validate billing source in GPA given
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param gpaCode
	 *	Parameters	:	@return 
	 *	Return type	: 	PostalAdministrationVO
	 */
    private PostalAdministrationVO validateGPABillingSource( String companyCode, String gpaCode) {
    	log.entering("GenerateInvoiceCommand","validateGPABillingSource");
    	MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
    	PostalAdministrationVO postalAdminVO = null;
    	try {
    		postalAdminVO = delegate.findPostalAdminDetails(companyCode,gpaCode);
    		
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE, " ----- validateGPABillingSource Failed ------- ");
		}
    	return postalAdminVO;
    }


    /**
	 * 	Method		:	GenerateInvoiceCommand.convertToDate
	 *	Added by 	:	A-4809 on 03-Jan-2014
	 * 	Used for 	:	to convert date to Localdate
	 *	Parameters	:	@param date
	 *	Parameters	:	@return 
	 *	Return type	: 	LocalDate
	 */
	private LocalDate convertToDate(String date){

		if(date!=null && !("").equals(date)){
			return(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).setDate( date ));
		}
		return null;
	}

    /**
	 * 	Method		:	GenerateInvoiceCommand.validateGpaCode
	 *	Added by 	:	A-4809 on 03-Jan-2014
	 * 	Used for 	:	to validate the GPA entered in screen
	 *	Parameters	:	@param generateInvoiceForm
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateGpaCode(
			GenerateGPABillingInvoiceForm form,



		
			
		




		
		

    
			LogonAttributes logonAttributes) {
		
		String gpaCode=form.getGpacode();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		if(gpaCode!=null && gpaCode.trim().length()>0){
			
	  	log.log(Log.FINE, "Going To validate GPA code ...in command");
			try {
		  			PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
					postalAdministrationVO  = new MailTrackingMRADelegate().findPACode(
									logonAttributes.getCompanyCode(),gpaCode.toUpperCase());
					log.log(Log.FINE, "postalAdministrationVO",
							postalAdministrationVO);
					if(postalAdministrationVO == null) {
		  				Object[] obj = {gpaCode.toUpperCase()};
		  				errors.add(new ErrorVO("mailtracking.mra.gpabilling.gpacode.invalid",obj));
		  			}
		  	
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		return errors;
	}
	/**
	 * 	Method		:	GenerateInvoiceCommand.validateForm
	 *	Added by 	:	A-4809 on 03-Jan-2014
	 * 	Used for 	:	to check mandatory values,
	 *  billing period from and to date mandatory in screen
	 *	Parameters	:	@param generateInvoiceForm
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			GenerateGPABillingInvoiceForm form) {
		log.entering("GenerateInvoiceCommand", "validateDates");
		Collection<ErrorVO> errors = null;
		ErrorVO errorVO = null;
		String strFromDate = form.getBlgPeriodFrom();
		String strToDate = form.getBlgPeriodTo();
		if( !(strFromDate != null && strFromDate.length() > 0 &&
				strToDate != null && strToDate.length() > 0 ) ){
			log.log(Log.INFO," <--- mandatory not entered --> ");
			errors = new ArrayList<ErrorVO>();
			errorVO = new ErrorVO(ERROR_DATES_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
		}
		else {
			LocalDate fromDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			fromDate.setDate(strFromDate);
			LocalDate toDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
			toDate.setDate(strToDate);
			if(fromDate.isGreaterThan(toDate)){
				log.log(Log.INFO," <--- from date > to date -->> ");
				errors = new ArrayList<ErrorVO>();
				errorVO = new ErrorVO(INVALID_DATE_RANGE);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
		}
		return errors;
	}
	/**
	 * 
	 * 	Method		:	GenerateInvoiceCommand.validateGpaBillingPeriod
	 *	Added by 	:	A-3434 on 01-Oct-2015
	 * 	Used for 	:	to validate billing period for a GPA given
	 *	Parameters	:	@param generateInvoiceFilterVO
	 *	Parameters	:	
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	private boolean validateGpaBillingPeriod( GenerateInvoiceFilterVO generateInvoiceFilterVO) {
    	log.entering("GenerateInvoiceCommand","validateGpaBillingPeriod");
    	MailTrackingMRADelegate delegate = new MailTrackingMRADelegate();
    	boolean isvalidPeriod = true;
    	try {
    		isvalidPeriod = delegate.validateGpaBillingPeriod(generateInvoiceFilterVO);
		} catch (BusinessDelegateException e) {
			log.log(Log.SEVERE, " ----- validateGpaBillingPeriod Failed ------- ");
		}
    	return isvalidPeriod;
	}

    

}
