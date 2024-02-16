/*
 * GenerateInvoiceCommand.java Created on March 08, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.mra.airlinebilling.outward.generateoutwardbillinginvoice;

import static com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD;
import java.util.ArrayList;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarFilterVO;
import com.ibsplc.icargo.business.cra.defaults.masters.vo.IATACalendarVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.mra.MailTrackingMRADelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.airlinebilling.outward.GenerateOutwardBillingInvoiceForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Shivjith A
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jun 20, 2007   Shivjith A 				Initial draft , Added method execute
 */
public class GenerateInvoiceCommand extends BaseCommand {

	private  Log log = LogFactory.getLogger("MRA AIRLINE BILLING");

	private static final String CLASS_NAME = "GenerateInvoiceCommand";

	private static final String MODULE_NAME = "mailtracking.mra.airlinebilling";

	private static final String SCREENID = "mailtracking.mra.airlinebilling.outward.generateinvoice";

	private static final String GEN_SUCCESS = "generate_success";

	private static final String GEN_FAILURE = "generate_failure";
	
	private static final String BLANK = "";
	
	private static final String GENERATE_SUCCESS = "mailtracking.mra.airlinebilling.outward.generateinvoicesuccess";



//	private static final String KEY_NO_INVOICE_GENERATED 
//		= "mailtracking.mra.airlinebilling.outward.noinvoicegenerated";
//
	private static final String KEY_NOT_VALID_AIRLINE 
		= "mailtracking.mra.airlinebilling.outward.invalidairline";
	
	/**
	 * genrates invoice 
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
    throws CommandInvocationException {

    	log.entering(CLASS_NAME,"execute");
    	
    	String validationTemp = "";
    	String validationTemps="";
    	String companyCode = null;
    	
    	ErrorVO error = null;
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	Collection<ErrorVO> clearencePeriodError = new ArrayList<ErrorVO>();
    	AirlineValidationVO airlineValidationVO = null;
    	
    	GenerateOutwardBillingInvoiceForm generateInvoiceForm =
    		(GenerateOutwardBillingInvoiceForm)invocationContext.screenModel;
    	
    	
    	companyCode = getApplicationSession().getLogonVO().getCompanyCode();
    
    	
    	InvoiceLovFilterVO invoiceFilterVO = new InvoiceLovFilterVO();

    	invoiceFilterVO.setCompanycode(companyCode);
    	
    	invoiceFilterVO.setClearingHouse(generateInvoiceForm.getClearingHouse());
		
    	validationTemp = generateInvoiceForm.getAirlineCode();
    	
    	if(!BLANK.equals(validationTemp)){
    		
    		try {
    			airlineValidationVO = new AirlineDelegate().validateAlphaCode(
    											companyCode, validationTemp);
    			invoiceFilterVO.setAirlineCode(validationTemp);
        		invoiceFilterVO.setAirlineidr(airlineValidationVO.getAirlineIdentifier());
        		
    		} catch (BusinessDelegateException e) {
				
    			//errors.add(new ErrorVO(KEY_NOT_VALID_AIRLINE));
    			Object[] obj = {validationTemp};
    			ErrorVO err = new ErrorVO(KEY_NOT_VALID_AIRLINE,obj);
    			err.setErrorDisplayType(ErrorDisplayType.ERROR);
    			errors.add(err);
//    			errors = handleDelegateException(e);
    			invocationContext.addAllError(errors);
        		invocationContext.target = GEN_FAILURE;
        		return;
			}
    		
    	}
    	
    	//Method call to validate clearance period Added for AirNZ CR177
    	IATACalendarFilterVO iatacalendarfiltervo = new IATACalendarFilterVO();
		iatacalendarfiltervo.setCompanyCode(companyCode);
		iatacalendarfiltervo.setClearancePeriod(generateInvoiceForm
				.getClearancePeriod());
    	
    	if (generateInvoiceForm.getClearancePeriod() != null
				&& generateInvoiceForm.getClearancePeriod().trim().length() > 0) {
			clearencePeriodError = validateClearencePeriod(iatacalendarfiltervo);

		}
    	if (clearencePeriodError != null && clearencePeriodError.size() > 0) {
			log.log(Log.FINE, "errors--", clearencePeriodError.size());
			generateInvoiceForm
					.setScreenStatusFlag(SCREEN_STATUS_SCREENLOAD);
			invocationContext.addAllError(clearencePeriodError);
			invocationContext.target = GEN_FAILURE;
			return;

		}
    	
    	validationTemps = generateInvoiceForm.getClearancePeriod();
    	
    	if(!BLANK.equals(validationTemps)){
    		invoiceFilterVO.setClearanceperiod(validationTemps);
    	}
    	
    	log.log(Log.FINE, "invoiceFilterVO", invoiceFilterVO);
		try{
    		new MailTrackingMRADelegate().generateOutwardBillingInvoice(invoiceFilterVO);
    		generateInvoiceForm.setHasGenerated(true);
    		
    	}catch(BusinessDelegateException e){
    		log.log(Log.FINE,"\n\nerrors");
    		errors = this.handleDelegateException(e);
    	}

    	if(errors != null && errors.size() > 0 ){
    		invocationContext.target = GEN_FAILURE;
    		invocationContext.addAllError(errors);
    		return;
    	}
    	
    	else{
    		error = new ErrorVO(GENERATE_SUCCESS );
    		
			errors.add(error);
			
			invocationContext.addAllError(errors);
			
    	}
    	invocationContext.target = GEN_SUCCESS;
    	log.exiting(CLASS_NAME,"execute");
    }
    /**
	 * Method for validating Clearence period
	 * 
	 * @author a-3229
	 * @param iatacalendarfiltervo
	 * @return Collection<ErrorVO>
	 */

	private Collection<ErrorVO> validateClearencePeriod(
			IATACalendarFilterVO iatacalendarfiltervo) {
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		IATACalendarVO iatacalendarvo = null;
		try {
			
			iatacalendarvo = new MailTrackingMRADelegate()
					.validateClearancePeriod(iatacalendarfiltervo);
			log.log(Log.INFO, "iatacalendarvo", iatacalendarvo);
		} catch (BusinessDelegateException e) {
			errors = handleDelegateException(e);
		}
	
		if (iatacalendarvo != null ) {
			log.log(Log.INFO, "iatacalendarvos not null ", iatacalendarvo);

		} else {
			log.log(log.INFO, "iatacalendarvo null--->");
			ErrorVO err = new ErrorVO("mailtracking.mra.airlinebilling.error.invalidClearancePeriod");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}

		return errors;

	}
}
