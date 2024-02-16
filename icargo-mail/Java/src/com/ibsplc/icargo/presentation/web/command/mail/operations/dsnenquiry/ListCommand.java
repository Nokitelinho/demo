/*
 * ListCommand.java Created on July 01, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.dsnenquiry;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.DsnEnquirySession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.DsnEnquiryForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;


/**
 * @author A-5991
 *
 */
public class ListCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   /**
    * TARGET
    */
   private static final String TARGET_SUCCESS = "list_success";
   private static final String TARGET_FAILURE = "list_failure";

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.dsnEnquiry";
   private static final String BLANKSPACE = "";
   private static final String CONST_MAILBAGENQUIRY = "ListFromMailbagEnquiry";
   private static final String ULD_TYPE = "U";	//Added for ICRD-128804
   private static final String INVALID_ULD = "mailtracking.defaults.dsnenquiry.msg.err.invaliduldnumber";	//Added for ICRD-128804

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ListCommand","execute");

    	DsnEnquiryForm dsnEnquiryForm =
    		(DsnEnquiryForm)invocationContext.screenModel;
    	DsnEnquirySession dsnEnquirySession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;

		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();
		DSNEnquiryFilterVO dsnEnquiryFilterVO = null;
		if(dsnEnquirySession.getDsnEnquiryFilterVO()!=null && dsnEnquiryForm.getScreenStatusFlag()!=null && "detail".equals(dsnEnquiryForm.getScreenStatusFlag())){
		
			dsnEnquiryFilterVO=dsnEnquirySession.getDsnEnquiryFilterVO();
			dsnEnquiryForm.setCategory(dsnEnquiryFilterVO.getMailCategoryCode());
			dsnEnquiryForm.setConsignmentNo(dsnEnquiryFilterVO.getConsignmentNumber());
			dsnEnquiryForm.setContainerType(dsnEnquiryFilterVO.getContainerType());
			dsnEnquiryForm.setDestnCity(dsnEnquiryFilterVO.getDestinationCity());
			dsnEnquiryForm.setDsn(dsnEnquiryFilterVO.getDsn());
			dsnEnquiryForm.setFlightCarrierCode(dsnEnquiryFilterVO.getCarrierCode());
			if (dsnEnquiryFilterVO.getFlightDate() != null) {
				dsnEnquiryForm.setFlightDate(dsnEnquiryFilterVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}else {
				dsnEnquiryForm.setFlightDate("");
			}
			dsnEnquiryForm.setFlightNumber(dsnEnquiryFilterVO.getFlightNumber());	
			dsnEnquiryForm.setOperationType(dsnEnquiryFilterVO.getOperationType());
			if (dsnEnquiryFilterVO.getFromDate() != null) {
				dsnEnquiryForm.setFromDate(dsnEnquiryFilterVO.getFromDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}
			dsnEnquiryForm.setMailClass(dsnEnquiryFilterVO.getMailClass());
			dsnEnquiryForm.setOriginCity(dsnEnquiryFilterVO.getOriginCity());
			dsnEnquiryForm.setPort(dsnEnquiryFilterVO.getAirportCode());
			dsnEnquiryForm.setPlt(Boolean.valueOf(dsnEnquiryFilterVO.getPltEnabledFlag()));
			dsnEnquiryForm.setCapNotAcp(Boolean.valueOf(dsnEnquiryFilterVO.getCapNotAcpEnabledFlag()));
			if (dsnEnquiryFilterVO.getToDate() != null) {
				dsnEnquiryForm.setToDate(dsnEnquiryFilterVO.getToDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}
			dsnEnquiryForm.setStatus("");
			dsnEnquiryForm.setDisplayPage("1");
			dsnEnquiryForm.setLastPageNum("0");
			dsnEnquiryForm.setSubCheck(null);
			dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
		}
		else
			{
			errors = validateForm(dsnEnquiryForm);
			}
		log.log(Log.INFO, "after validating==>>", dsnEnquiryForm.getToDate());
		
		log.log(Log.INFO,"after validating==>>");
		if (errors != null && errors.size() > 0) {
			if (!CONST_MAILBAGENQUIRY.equals(dsnEnquiryForm.getStatus())) {
				dsnEnquirySession.setDespatchDetailsVOs(null);
			}
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		log.log(Log.FINE, "Status --------->>", dsnEnquiryForm.getStatus());
		if (CONST_MAILBAGENQUIRY.equals(dsnEnquiryForm.getStatus())) {
			dsnEnquiryFilterVO = dsnEnquirySession.getDsnEnquiryFilterVO();
			dsnEnquiryForm.setCategory(dsnEnquiryFilterVO.getMailCategoryCode());
			dsnEnquiryForm.setConsignmentNo(dsnEnquiryFilterVO.getConsignmentNumber());
			dsnEnquiryForm.setContainerType(dsnEnquiryFilterVO.getContainerType());
			dsnEnquiryForm.setDestnCity(dsnEnquiryFilterVO.getDestinationCity());
			dsnEnquiryForm.setDsn(dsnEnquiryFilterVO.getDsn());
			dsnEnquiryForm.setFlightCarrierCode(dsnEnquiryFilterVO.getCarrierCode());
			if (dsnEnquiryFilterVO.getFlightDate() != null) {
				dsnEnquiryForm.setFlightDate(dsnEnquiryFilterVO.getFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}else {
				dsnEnquiryForm.setFlightDate("");
			}
			dsnEnquiryForm.setFlightNumber(dsnEnquiryFilterVO.getFlightNumber());	
			dsnEnquiryForm.setOperationType(dsnEnquiryFilterVO.getOperationType());
			if (dsnEnquiryFilterVO.getFromDate() != null) {
				dsnEnquiryForm.setFromDate(dsnEnquiryFilterVO.getFromDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}
			dsnEnquiryForm.setMailClass(dsnEnquiryFilterVO.getMailClass());
			dsnEnquiryForm.setOriginCity(dsnEnquiryFilterVO.getOriginCity());
			dsnEnquiryForm.setPort(dsnEnquiryFilterVO.getAirportCode());
			dsnEnquiryForm.setPlt(Boolean.valueOf(dsnEnquiryFilterVO.getPltEnabledFlag()));
			dsnEnquiryForm.setCapNotAcp(Boolean.valueOf(dsnEnquiryFilterVO.getCapNotAcpEnabledFlag()));
			if (dsnEnquiryFilterVO.getToDate() != null) {
				dsnEnquiryForm.setToDate(dsnEnquiryFilterVO.getToDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT));
			}

			dsnEnquiryForm.setStatus("");
			dsnEnquiryForm.setDisplayPage("1");
			dsnEnquiryForm.setLastPageNum("0");
			dsnEnquiryForm.setSubCheck(null);
			dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.
					SCREEN_STATUS_SCREENLOAD);
		}
		else {
			log.log(Log.INFO,"inside else=>>");
			dsnEnquirySession.setDespatchDetailsVOs(null);
			dsnEnquiryForm.setSubCheck(null);
			
			String fromDate = dsnEnquiryForm.getFromDate();
			String toDate = dsnEnquiryForm.getToDate();
			log.log(Log.INFO, "fromdate===>>", fromDate);
			log.log(Log.INFO, "toDate===>>", toDate);
			if(fromDate == null || fromDate.trim().length() == 0 ){
				log.log(Log.INFO,"inside fromDate.trim().length()=>>");
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.dsnenquiry.msg.err.nofromdate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			} 
			if(toDate == null || toDate.trim().length() == 0){
				log.log(Log.INFO,"inside toDate.trim().length()=>>");
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.dsnenquiry.msg.err.notodate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
			}
			if (errors != null && errors.size() > 0) {
				if (!CONST_MAILBAGENQUIRY.equals(dsnEnquiryForm.getStatus())) {
					dsnEnquirySession.setDespatchDetailsVOs(null);
					dsnEnquirySession.setDsnEnquiryFilterVO(null);
				}
				invocationContext.addAllError(errors);
				invocationContext.target = TARGET_FAILURE;
				return;
			}

			//	VALIDATING FLIGHT CARRIER
	    	String carrier = dsnEnquiryForm.getFlightCarrierCode();
	    	AirlineDelegate airlineDelegate = new AirlineDelegate();
	    	AirlineValidationVO airlineValidationVO = null;
	    	errors = null;
	    	if (carrier != null && !"".equals(carrier)) {

	    		try {
	    			airlineValidationVO = airlineDelegate.validateAlphaCode(
	    					logonAttributes.getCompanyCode(),
	    					carrier.trim().toUpperCase());

	    		}catch (BusinessDelegateException businessDelegateException) {
	        		errors = handleDelegateException(businessDelegateException);
	    		}
	    		if (errors != null && errors.size() > 0) {
	    			log.log(Log.FINE, " errors.size() --------->>", errors.size());
					for(ErrorVO error:errors){
	    				error.setErrorCode("mailtracking.defaults.invalidcarrier");
	    			}
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET_FAILURE;
	    			return;
	    		}
	    	}
	    	
	    	
//	    	VALIDATING PORT
	    	String port = dsnEnquiryForm.getPort();
	    	errors = null;
	    	if (port != null && !"".equals(port)) {

	    		try {
	    			 new AreaDelegate().validateAirportCode(
						logonAttributes.getCompanyCode(),port.toUpperCase());
				}
				catch(BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
	   			}
	    		if (errors != null && errors.size() > 0) {
	    			errors = new ArrayList<ErrorVO>();
	    			log.log(Log.FINE, " errors.size() --------->>", errors.size());
					errors.add(new ErrorVO("mailtracking.defaults.invalidairport",
			   				new Object[]{port.toUpperCase()}));
	    			invocationContext.addAllError(errors);
	    			invocationContext.target = TARGET_FAILURE;
	    			return;
	    		}
	    	}
	    	
	    	
	    	
	    	
	    	
	    	if("I".equals(dsnEnquiryForm.getOperationType())){
	    		String fltNo = dsnEnquiryForm.getFlightNumber();
	    		String fltDt = dsnEnquiryForm.getFlightDate();
				if (fltNo == null || "".equals(fltNo) || fltDt == null || "".equals(fltDt)) {
					invocationContext.addError(new ErrorVO("mailtracking.defaults.dsnenquiry.flightnumbermandatory"));
	    			invocationContext.target = TARGET_FAILURE;
	    			return;
				}
	    	}
	    	//VALIDATE ULD NUMBER Added for ICRD-128804 starts
    		ULDDelegate uldDelegate = new ULDDelegate();
    		String containerNumber=dsnEnquiryForm.getUldNo();
    		String containerType=dsnEnquiryForm.getContainerType();
    		boolean isULDType = ULD_TYPE.equals(containerType);
    		if(isULDType&&containerNumber!=null&&containerNumber.trim().length()>0){
				try {
					uldDelegate.validateULD(logonAttributes.getCompanyCode(),containerNumber);
				}catch (BusinessDelegateException businessDelegateException) {
					errors = handleDelegateException(businessDelegateException);
				}
        		if (errors != null && errors.size() > 0) {
        			errors = new ArrayList<ErrorVO>();
        			Object[] obj = {containerNumber};
    				ErrorVO errorVO = new ErrorVO(
    					INVALID_ULD,obj);
    				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    				errors.add(errorVO);
        			invocationContext.addAllError(errors);
        			invocationContext.target = TARGET_FAILURE;
        			return;
        		}
			}
    		//VALIDATE ULD NUMBER Added for ICRD-128804 ends

			// CREATING FILTER
			dsnEnquiryFilterVO = new DSNEnquiryFilterVO();
			dsnEnquiryFilterVO.setCarrierCode(upper(dsnEnquiryForm.getFlightCarrierCode()));
			dsnEnquiryFilterVO.setCarrierId(airlineValidationVO.getAirlineIdentifier());
			dsnEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
			dsnEnquiryFilterVO.setAirportCode(upper(dsnEnquiryForm.getPort()));
			dsnEnquiryFilterVO.setConsignmentNumber(upper(dsnEnquiryForm.getConsignmentNo()));
			dsnEnquiryFilterVO.setContainerNumber(upper(dsnEnquiryForm.getUldNo()));
			dsnEnquiryFilterVO.setContainerType(dsnEnquiryForm.getContainerType());
			dsnEnquiryFilterVO.setDestinationCity(upper(dsnEnquiryForm.getDestnCity()));
			dsnEnquiryFilterVO.setDsn(dsnEnquiryForm.getDsn());
			dsnEnquiryFilterVO.setPaCode(upper(dsnEnquiryForm.getPostalAuthorityCode()));
			dsnEnquiryFilterVO.setOperationType(dsnEnquiryForm.getOperationType());
			dsnEnquiryFilterVO.setTransitFlag(dsnEnquiryForm.getTransit());

			if (!("").equals(dsnEnquiryForm.getFlightDate())) {
				LocalDate fltdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
				dsnEnquiryFilterVO.setFlightDate(fltdate.setDate(dsnEnquiryForm.getFlightDate()));
			}

			String fltNo = dsnEnquiryForm.getFlightNumber();
			if (fltNo != null && !("").equals(fltNo)) {
				dsnEnquiryFilterVO.setFlightNumber(upper(fltNo));
			}

			if (!("").equals(dsnEnquiryForm.getFromDate())) {
				LocalDate fromdate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
				dsnEnquiryFilterVO.setFromDate(fromdate.setDate(dsnEnquiryForm.getFromDate()));
			}
			dsnEnquiryFilterVO.setMailCategoryCode(dsnEnquiryForm.getCategory());
			dsnEnquiryFilterVO.setMailClass(dsnEnquiryForm.getMailClass());
			dsnEnquiryFilterVO.setOriginCity(upper(dsnEnquiryForm.getOriginCity()));
			dsnEnquiryFilterVO.setPltEnabledFlag(String.valueOf(dsnEnquiryForm.isPlt()));
			dsnEnquiryFilterVO.setCapNotAcpEnabledFlag(String.valueOf(dsnEnquiryForm.isCapNotAcp()));

			if (!("").equals(dsnEnquiryForm.getToDate())) {
				LocalDate todate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,false);
				dsnEnquiryFilterVO.setToDate(todate.setDate(dsnEnquiryForm.getToDate()));
			}
			//dsnEnquiryFilterVO.setYear();

			log.log(Log.FINE, "DsnEnquiryFilterVO --------->>",
					dsnEnquiryFilterVO);
			dsnEnquirySession.setDsnEnquiryFilterVO(dsnEnquiryFilterVO);
		}

		Page<DespatchDetailsVO> despatchDetailsVOPage = null;
		 if(!"Y".equals(dsnEnquiryForm.getCountTotalFlag()) && dsnEnquirySession.getTotalRecords()!=null)
			 {
			 dsnEnquiryFilterVO.setTotalRecords(dsnEnquirySession.getTotalRecords().intValue());
			 }
	        else
	        	{
	        	dsnEnquiryFilterVO.setTotalRecords(-1);
	        	}
	        log.log(3, (new StringBuilder()).append("checked.length-------------------->").append(dsnEnquiryFilterVO).toString());
	       

		try {
			despatchDetailsVOPage = mailTrackingDefaultsDelegate.findDSNs(
					dsnEnquiryFilterVO,
					Integer.parseInt(dsnEnquiryForm.getDisplayPage()));

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		if(  despatchDetailsVOPage != null &&  despatchDetailsVOPage.size() > 0 ) {
			log.log(Log.FINE, " the total records in the    list:>",
					despatchDetailsVOPage.getTotalRecordCount());
			log.log(Log.FINE, " caching in screen session ");
			dsnEnquirySession.setTotalRecords(despatchDetailsVOPage.getTotalRecordCount()); 
		}

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		if (despatchDetailsVOPage != null && despatchDetailsVOPage.size() > 0) {
			log.log(Log.FINE, "Page --------->>", despatchDetailsVOPage);
			dsnEnquirySession.setDespatchDetailsVOs(despatchDetailsVOPage);
		}
		else {
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.dsnenquiry.msg.err.nodetails");
			errors = new ArrayList<ErrorVO>();
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		dsnEnquiryForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);

    	invocationContext.target = TARGET_SUCCESS;

    	log.exiting("ListCommand","execute");

    }
    /**
     * This method is used to convert a string to upper case if
     * it is not null
	 * @param input
	 * @return String
	 */
	private String upper(String input){//to convert sting to uppercase

		if(input!=null){
			return input.trim().toUpperCase();
		}else{
			return BLANKSPACE;
		}
	}
	/**
	 * This method is used for validating the form for the particular action
	 * @param dsnEnquiryForm - DsnEnquiryForm
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			DsnEnquiryForm dsnEnquiryForm) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		if ((dsnEnquiryForm.getFlightNumber()!=null && dsnEnquiryForm.getFlightNumber().trim().length()>0) ){
			if (dsnEnquiryForm.getFlightCarrierCode().trim().length() == 0) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.dsnenquiry.msg.err.noCarrierCode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if(dsnEnquiryForm.getFlightDate().trim().length() == 0){
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.dsnenquiry.msg.err.noFlightDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}	
		}else {
			if(dsnEnquiryForm.getFlightCarrierCode()!=null && dsnEnquiryForm.getFlightCarrierCode().trim().length() == 0){
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.dsnenquiry.msg.err.noCarrierCodeAndNoFlightDetails");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}else if(dsnEnquiryForm.getFlightDate()!=null && dsnEnquiryForm.getFlightDate().trim().length() > 0){
				ErrorVO errorVO = new ErrorVO(
				"mailtracking.defaults.dsnenquiry.msg.err.noFlightNumber");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
		}
		
		String fromDate = dsnEnquiryForm.getFromDate();
		String toDate = dsnEnquiryForm.getToDate();
		log.log(Log.INFO, "fromdate===>>", fromDate);
		log.log(Log.INFO, "toDate===>>", toDate);
		if (fromDate!=null && toDate!=null && !"".equals(fromDate) && !"".equals(toDate)) {
			if (!fromDate.equals(toDate)) {
				if (!DateUtilities.isLessThan(fromDate, toDate,"dd-MMM-yyyy")) {
			    	log.log(Log.FINE, "FROM DATE GRTR THAN TO DATE=========>");
			    	ErrorVO errorVO = new ErrorVO(
			    	"mailtracking.defaults.fromdategreatertodate");
			    	formErrors.add(errorVO);
			    }
			}
		}
		log.log(Log.INFO,"before returnign form errors()=>>");
		return formErrors;
	}
	
	
	/**
	 * This method is to format flightNumber
	 * Not using accoding to the CRQ-AirNZ989-12
	 * @param flightNumber 
	 * @return String
	 */
	/*private String formatFlightNumber(String flightNumber){
		
		int numLength = flightNumber.length();
		String newFlightNumber = "" ;
	    
		if(numLength == 1) { 
	    	newFlightNumber = new  StringBuilder("000").append(flightNumber).toString();
	    }else if(numLength == 2) {
	    	newFlightNumber = new  StringBuilder("00").append(flightNumber).toString();
	    }else if(numLength == 3) { 
	    	newFlightNumber = new  StringBuilder("0").append(flightNumber).toString();
	    }else {
	    	newFlightNumber = flightNumber ;
	    }
		return newFlightNumber;
		
	}*/
	
}
