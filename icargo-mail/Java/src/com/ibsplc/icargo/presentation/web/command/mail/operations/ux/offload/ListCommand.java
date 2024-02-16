package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OffloadModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOperationsModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OffloadFilter;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.mail.operations.ux.offload.ListCommand.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7929	:	15-Feb-2019	:	Draft
 */
public class ListCommand extends AbstractCommand{
	
private Log log = LogFactory.getLogger("Mail operations offload ");
	
	private static final String MODULE_NAME = "mail.operations";
	private static final String SCREENID = "mail.operations.ux.offload";

	private static final String ULD_TYPE = "U";
	private static final String OUTBOUND = "O";
	private static final String DETAILS_MANDATORY = "mail.operations.ux.offload.msg.err.mandatorycheck";
	private static final String PARTIAL_FLIGHTDETAILS= "mail.operations.ux.offload.msg.err.enterfullflightdetails";
	private static final String PARTIAL_CONTAINERANDFLIGHTDETAILS= "mail.operations.ux.offload.msg.err.mandatorycheck";
	private static final String INVALID_ULD ="mail.operations.ux.offload.msg.err.invaliduldnumber";
	private static final String BLANKSPACE = "";
	private static final String OFFLOAD_DSN_TYPE="D";
	private static final String OFFLOAD_CONTAINER_TYPE="U";
	private static final String OFFLOAD_MAIL_TYPE="M";
	
	
	
	
	public void execute(ActionContext actionContext) throws BusinessDelegateException, CommandInvocationException {
		
		
		log.entering("ListCommand", "execute");
		OffloadModel offloadModel = (OffloadModel) actionContext.getScreenModel();
		// Filter values we are getting from react screen
		OffloadFilter offloadFilterFromUI = (OffloadFilter)offloadModel.getOffloadFilter();
		//OffloadMailFilter offloadMailFilterFromUI = (OffloadMailFilter)offloadModel.getOffloadMailFilter();
		//OffloadDSNFilter  offloadDSNFilterFromUI = (OffloadDSNFilter)offloadModel.getOffloadDSNFilter();
		
		ResponseVO responseVO = new ResponseVO();
		LogonAttributes logonAttributes = getLogonAttribute();
		AirlineDelegate airlineDelegate = new AirlineDelegate();
    	AirlineValidationVO airlineValidationVO = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		Collection<ErrorVO> errors = null;
		FlightValidationVO flightValidationVO = null;
		OffloadVO mainOffloadVO = null;
		Page<ContainerVO> offloadContainerDetailsVOPage = null;
		Page <MailbagVO> offloadMailBagsDetailsVOPage = null;
		Page <DespatchDetailsVO> offloadDespatchDetailsVOPage = null;
		String containerType="";
		
		
		if(offloadFilterFromUI != null){
		// containerType=offloadFilterFromUI.getContainerType();
		
		  /*  if("B".equals(containerType) ) {
		    	ErrorVO err = new ErrorVO("mail.operations.ux.err.bulkoffloadnotpossible");
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(err);
				return;
		 }
		    else 
		    	offloadFilterFromUI.setContainerType("U"); */
		
	
		errors = validateOffloadFilter(offloadFilterFromUI);
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		}
		
		
    	/**
    	 * VALIDATING FLIGHT CARRIER CODE
    	 */
    
		String flightCarrierCode = offloadFilterFromUI.getFlightCarrierCode();
    	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
    		try {
    			airlineValidationVO = airlineDelegate.validateAlphaCode(
    					logonAttributes.getCompanyCode(),
    					flightCarrierCode.trim().toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {flightCarrierCode.toUpperCase()};
				ErrorVO errorVO = new ErrorVO(
					"mail.operations.ux.offload.msg.err.invalidCarrier",obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
    	}
		
    	
    	/**
    	 *  VALIDATING FLIGHT NUMBER
    	 */
    	
    	if(!("").equals(offloadFilterFromUI.getFlightDate())&&!("").equals(offloadFilterFromUI.getFlightCarrierCode())&&!("").equals(offloadFilterFromUI.getFlightNumber())){
		
        FlightFilterVO flightFilterVO = handleFlightFilterVO(offloadFilterFromUI,logonAttributes);

		flightFilterVO.setCarrierCode(flightCarrierCode.toUpperCase());
		flightFilterVO.setFlightCarrierId(airlineValidationVO.getAirlineIdentifier());

		Collection<FlightValidationVO> flightValidationVOs = null;

		try {
			log.log(Log.FINE, "FlightFilterVO ------------> ",flightFilterVO);
			flightValidationVOs =
				mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		
		
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		
		//to find the flights from mail table when listed from a different airport due to route change
		if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
			try {
				log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
				flightValidationVOs =
					mailTrackingDefaultsDelegate.validateMailFlight(flightFilterVO);
				if (flightValidationVOs != null && flightValidationVOs.size() > 0) {
					for (FlightValidationVO flightValidVO : flightValidationVOs) {
						flightValidVO.setFlightStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
						flightValidVO.setLegStatus(FlightValidationVO.FLT_LEG_STATUS_TBA);
					}
				}
			}catch (BusinessDelegateException businessDelegateException) {
				errors = handleDelegateException(businessDelegateException);
			}
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		else {
			
			
			/**
			 * IF NO RESULTS RETURNED
			 */
			
			if (flightValidationVOs == null || flightValidationVOs.size() <= 0) {
				log.log(Log.FINE, "----------------FlightValidationVOs is NULL");
				Object[] obj = {offloadFilterFromUI.getFlightCarrierCode().toUpperCase(),
						offloadFilterFromUI.getFlightNumber().toUpperCase(),
						offloadFilterFromUI.getFlightDate()};
				ErrorVO errorVO = new ErrorVO(
						"mail.operations.ux.offload.msg.err.noflightDetails",obj);
				errors = new ArrayList<ErrorVO>();
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}
			
			/**
			 * IF 1 RESULT RETURNED
			 */
			else if ( flightValidationVOs.size() == 1) { 
				log.log(Log.FINE, "--------------FlightValidationVOs has one VO");
				for (FlightValidationVO flightValidVO : flightValidationVOs) {	    									
					/*if(!FlightValidationVO.FLAG_YES.equalsIgnoreCase(offloadFilterFromUI.getReListFlag()) 
							&& (FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) 
									|| FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus()))){
						if(!FlightValidationVO.FLAG_YES.equals(offloadFilterFromUI.getWarningOveride())){
						ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
						err.setErrorDisplayType(ErrorDisplayType.WARNING);
						actionContext.addError(err);
						offloadForm.setWarningFlag(FLIGHT_TBC_TBA);
						return;
						}else{
							offloadForm.setWarningFlag("");
							offloadForm.setWarningOveride(null);
							continueForTbaTbcStatus = true;
						}
						
					}*/
					
					
					flightValidationVO = flightValidVO;
					break;
				}
			}
			 /**
			  * IF MORE VOS RETURNED
			  */
			else if(flightValidationVOs.size() > 1){ 
				log.log(Log.FINE, "--------------Duplicate flight VO");
				/*duplicateFlightSession.setFlightValidationVOs(
						(ArrayList<FlightValidationVO>)flightValidationVOs);
				duplicateFlightSession.setParentScreenId(SCREEN_ID);
				duplicateFlightSession.setFlightFilterVO(flightFilterVO);
				duplicateFlightSession.setScreenOfParent("Offload");
				offloadForm.setStatus("showDuplicateFlights");*/
				
				
				//Duplicate flights skipped
				
				return;
			}
		}

	}
		
    	/**
    	 * VALIDATING ULD NUMBER
    	 */
    	
		ULDDelegate uldDelegate = new ULDDelegate();
		String containerNumber=offloadFilterFromUI.getContainerNo();
		
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
				ErrorVO errorVO = new ErrorVO(INVALID_ULD,obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
    			return;
    		}
		}    	
		
		OffloadFilterVO offloadFilterVO = handleFilterDetails(offloadFilterFromUI,logonAttributes,flightValidationVO);
		log.log(Log.FINE, "OffloadFilterVO-------------->",offloadFilterVO);
		try { 

	    	   mainOffloadVO = mailTrackingDefaultsDelegate.findOffLoadDetails(offloadFilterVO);
			if (mainOffloadVO != null) {
				if (OFFLOAD_CONTAINER_TYPE.equals(offloadFilterFromUI.getType())) {
	    	   offloadContainerDetailsVOPage = (mainOffloadVO.getOffloadContainerDetails());   
				} else if (OFFLOAD_MAIL_TYPE.equals(offloadFilterFromUI.getType())) {
					offloadMailBagsDetailsVOPage = (mainOffloadVO.getOffloadMailbags());
				} else {
					offloadDespatchDetailsVOPage = mainOffloadVO.getOffloadDSNs();
			}
    	log.log(Log.FINE, "offloadDetailsVOPage-------------->",offloadContainerDetailsVOPage);
			}
			/*else{
				ErrorVO errorVO = new ErrorVO("No records found");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				errors.add(errorVO);
				actionContext.addAllError((List<ErrorVO>) errors);
				return;
			}*/
		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		/*if (errors != null && errors.size() > 0) {
			//actionContext.addAllError((List<ErrorVO>) errors);
			//offloadFilterFromUI.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
			return;
		}*/
		
    	
    	/**
    	 *  Validating Whether Flight is closed
    	 */
		if ( !("").equals(offloadFilterFromUI.getFlightDate()) && !("").equals(offloadFilterFromUI.getFlightCarrierCode()) && !("").equals(offloadFilterFromUI.getFlightNumber())) {
    	boolean isFlightClosed = false;
    	
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
    	operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
    	operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
    	operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
    	operationalFlightVO.setDirection(OUTBOUND);
    	operationalFlightVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
    	operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
    	operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    	boolean isTbaWithoutMailBag = false;
    	if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) && mainOffloadVO!=null){
    		if(mainOffloadVO.getOffloadContainerDetails()!=null && !mainOffloadVO.getOffloadContainerDetails().isEmpty()){
    			for(ContainerVO containerVO : mainOffloadVO.getOffloadContainerDetails())
    				{
    				operationalFlightVO.setLegSerialNumber(containerVO.getLegSerialNumber());
    		}
    		}
    		else if(mainOffloadVO.getOffloadMailbags()!=null){
    			for(MailbagVO mailbagVO : mainOffloadVO.getOffloadMailbags())
    				{
    				operationalFlightVO.setLegSerialNumber(mailbagVO.getLegSerialNumber());
    		}
    	}
    	}
    	else{  
    		if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()))
    			{
    			isTbaWithoutMailBag = true;	
    			}	
    	operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
    	}
    	operationalFlightVO.setPol(logonAttributes.getAirportCode());
    	if(!isTbaWithoutMailBag){	    		
    	try {
    		isFlightClosed = mailTrackingDefaultsDelegate.isFlightClosedForMailOperations(operationalFlightVO);

		}catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors != null && errors.size() > 0) {
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
		}
		log.log(Log.FINE, "@@@@@@@@^@@@@@@@@@@isFlightClosed",isFlightClosed);
		if(!isFlightClosed){
			actionContext.addError(new ErrorVO("mail.operations.ux.offload.flightclosed"));
 	   		return;
			}
		}
	}
		
		if (mainOffloadVO == null) {
			ErrorVO errorVO = new ErrorVO("No records found");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(errorVO);
			actionContext.addAllError((List<ErrorVO>) errors);
			return;
	}
		
		PageResult pageList;
		 if(OFFLOAD_CONTAINER_TYPE.equals(offloadFilterFromUI.getType())){	
			   ArrayList offloadContainerDetailsList = MailOperationsModelConverter.constructOffloadContainerDetails(offloadContainerDetailsVOPage);  
			    pageList = new PageResult(offloadContainerDetailsVOPage, offloadContainerDetailsList);
		 }
		 else if (OFFLOAD_MAIL_TYPE.equals(offloadFilterFromUI.getType())){
			 ArrayList<OffloadDetails> offloadMailDetailsList;
			    offloadMailDetailsList = MailOperationsModelConverter.constructOffloadMailDetails(offloadMailBagsDetailsVOPage);
			    if(offloadFilterFromUI.getMailbags()!=null && offloadFilterFromUI.getMailbags().length>0){
			    	List<OffloadDetails> offloadMailDetailsListNavigation = new ArrayList<OffloadDetails>(); 
			    	for(String mailId: offloadFilterFromUI.getMailbags()){
						 for (OffloadDetails offloadVO : offloadMailDetailsList) {
							 if(offloadVO.getMailbagId().equals(mailId)){
								 offloadMailDetailsListNavigation.add(offloadVO);
							 }
						 }
					    }
			    	if(offloadMailDetailsListNavigation.size()>0){
			    		pageList = new PageResult(offloadMailBagsDetailsVOPage, offloadMailDetailsListNavigation);
			    	}else{
			    		ErrorVO errorVO = new ErrorVO("No records found");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						errors.add(errorVO);
						actionContext.addAllError((List<ErrorVO>) errors);
						return;
			    	}
			    }else{
				 pageList = new PageResult(offloadMailBagsDetailsVOPage, offloadMailDetailsList);
			    }				 
		 }
		 else{
			  ArrayList offloadDetailsList = MailOperationsModelConverter.constructOffloadDSNDetails(offloadDespatchDetailsVOPage);  
				 pageList = new PageResult(offloadDespatchDetailsVOPage, offloadDetailsList);	 
		 }
			 
			offloadModel.setOffloadDetailsPageResult(pageList);
			//invoicEnquiryModel.setInvoicDetails(pageList);
			 ArrayList<OffloadModel> result= new ArrayList<OffloadModel>();
			 result.add(offloadModel);
		    responseVO.setResults(result);
		    responseVO.setStatus("success");
		     actionContext.setResponseVO(responseVO);
		
		log.exiting("ListCommand", "execute");
	}
	
	 private OffloadFilterVO handleFilterDetails(OffloadFilter offloadFilterFromUI, LogonAttributes logonAttributes, FlightValidationVO flightValidationVO) {
		
		 log.entering("ListCommand","handleFilterDetails");

	    	OffloadFilterVO offloadFilterVO = new OffloadFilterVO();

	    	String offloadtype = offloadFilterFromUI.getType();
	    	
	    	if(flightValidationVO != null ){
			offloadFilterVO.setCarrierCode(flightValidationVO.getCarrierCode());
	    	offloadFilterVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	    	offloadFilterVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
	    	offloadFilterVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    	offloadFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    	offloadFilterVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
			offloadFilterVO.setCarrierCode(flightValidationVO.getCarrierCode());
	    	offloadFilterVO.setCarrierId(flightValidationVO.getFlightCarrierId());
	    	offloadFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	    	offloadFilterVO.setFlightDate(flightValidationVO.getApplicableDateAtRequestedAirport());
	    	offloadFilterVO.setFlightNumber(flightValidationVO.getFlightNumber());
	    	offloadFilterVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
	    	offloadFilterVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
	    	}
	    	offloadFilterVO.setPol(logonAttributes.getAirportCode());
	    	offloadFilterVO.setOffloadType(offloadFilterFromUI.getType());
	    	offloadFilterVO.setContainerType("ALL");
	    	offloadFilterVO.setTotalRecords(offloadFilterFromUI.getTotalRecords());
	    	offloadFilterVO.setDefaultPageSize(offloadFilterFromUI.getDefaultPageSize());

	    		
	        	offloadFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
	        	
	        	offloadFilterVO.setPol(logonAttributes.getAirportCode());
	        	offloadFilterVO.setOffloadType(offloadFilterFromUI.getType());

	        	String contNumber = offloadFilterFromUI.getContainerNo();
	        	offloadFilterVO.setContainerNumber(upper(contNumber));
	        	offloadFilterVO.setContainerType(offloadFilterFromUI.getContainerType());
	        	offloadFilterVO.setPageNumber(Integer.parseInt(offloadFilterFromUI.getDisplayPage()));
	        	

	    		if (("D").equals(offloadtype)) {
	    			offloadFilterVO.setDsn(offloadFilterFromUI.getDsn());
	            	offloadFilterVO.setDsnDestinationExchangeOffice(upper(offloadFilterFromUI.getDoe()));
	            	offloadFilterVO.setDsnMailClass(offloadFilterFromUI.getMailClass());
	            	offloadFilterVO.setDsnOriginExchangeOffice(upper(offloadFilterFromUI.getOoe()));
	            	if ((offloadFilterFromUI.getYear()!=null)) {
	            		offloadFilterVO.setDsnYear(offloadFilterFromUI.getYear());
	            	}
	    		}
	    		else if (("M").equals(offloadtype)) {
	    			offloadFilterVO.setMailbagCategoryCode(offloadFilterFromUI.getMailCategoryCode());
	            	offloadFilterVO.setMailbagDestinationExchangeOffice(upper(offloadFilterFromUI.getDoe()));
	            	offloadFilterVO.setMailbagDsn(upper(offloadFilterFromUI.getDsn()));
	            	offloadFilterVO.setMailbagOriginExchangeOffice(upper(offloadFilterFromUI.getOoe()));
	            	offloadFilterVO.setMailbagRsn(upper(offloadFilterFromUI.getRsn()));
	            	offloadFilterVO.setMailbagSubclass(upper(offloadFilterFromUI.getMailSubclass()));
	            	offloadFilterVO.setMailbagId(upper(offloadFilterFromUI.getMailbagId()));
	            	if ((offloadFilterFromUI.getYear()!=null)) {
	            		offloadFilterVO.setMailbagYear(Integer.parseInt(offloadFilterFromUI.getYear()));
	    		  }
	    		}
	  
			log.exiting("ListCommand","handleFilterDetails");
	    	return offloadFilterVO;

		 
	}
	 /**
	     * This method is used to convert a string to upper case if
	     * it is not null
		 * @param input
		 * @return String
		 */
		private String upper(String input) { //to convert string to uppercase

			if(input!=null){
				return input.trim().toUpperCase();
			}else{
				return BLANKSPACE;
			}
		}
	/**
     * Method to create the filter vo for flight validation
     * @param offloadFilterFromUI
     * @param logonAttributes
     * @return FlightFilterVO
     */
    private FlightFilterVO handleFlightFilterVO(
    		OffloadFilter offloadFilterFromUI,
			LogonAttributes logonAttributes){

    	log.entering("ListCommand","handleFlightFilterVO");

		FlightFilterVO flightFilterVO = new FlightFilterVO();

		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(offloadFilterFromUI.getFlightNumber().toUpperCase());
		flightFilterVO.setStation(logonAttributes.getAirportCode());
		flightFilterVO.setDirection(OUTBOUND);
		flightFilterVO.setActiveAlone(false);
		if(!("").equals(offloadFilterFromUI.getFlightDate())&&!("").equals(offloadFilterFromUI.getFlightCarrierCode())&&!("").equals(offloadFilterFromUI.getFlightNumber())){
		flightFilterVO.setStringFlightDate(offloadFilterFromUI.getFlightDate());
 		LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
 		flightFilterVO.setFlightDate(date.setDate(offloadFilterFromUI.getFlightDate()));
		}
 		
 		

 		log.exiting("ListCommand","handleFlightFilterVO");

		return flightFilterVO;
	}

	private Collection<ErrorVO> validateOffloadFilter(OffloadFilter offloadFilterFromUI) {
		Collection<ErrorVO> offloadFilterErrors = new ArrayList<ErrorVO>();
		
		if(("").equals(offloadFilterFromUI.getContainerNo())&&("").equals(offloadFilterFromUI.getFlightCarrierCode())&&
				("").equals(offloadFilterFromUI.getFlightNumber())&&("").equals(offloadFilterFromUI.getFlightDate())&&("").equals(offloadFilterFromUI.getContainerType())){
			ErrorVO errorVO = new ErrorVO(DETAILS_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			offloadFilterErrors.add(errorVO);
		}
		
		if(offloadFilterFromUI.getContainerNo()==null && offloadFilterFromUI.getFlightCarrierCode()==null && offloadFilterFromUI.getFlightNumber()==null &&offloadFilterFromUI.getFlightDate()==null && offloadFilterFromUI.getContainerType()==null){
			ErrorVO errorVO = new ErrorVO(DETAILS_MANDATORY);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			offloadFilterErrors.add(errorVO);
			
		}
		
		if(("").equals(offloadFilterFromUI.getContainerNo())&&("").equals(offloadFilterFromUI.getContainerType())){
			if(!(("").equals(offloadFilterFromUI.getFlightCarrierCode()))||!(("").equals(offloadFilterFromUI.getFlightNumber()))||!(("").equals(offloadFilterFromUI.getFlightDate()))){
				if(("").equals(offloadFilterFromUI.getFlightCarrierCode())||("").equals(offloadFilterFromUI.getFlightNumber())||("").equals(offloadFilterFromUI.getFlightDate())){
					log.log(Log.FINE, "empty container details and partial flight details");	
					ErrorVO errorVO = new ErrorVO(PARTIAL_FLIGHTDETAILS);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					offloadFilterErrors.add(errorVO);	

				}
			}
		}
		
		if(offloadFilterFromUI.getContainerNo()==null && offloadFilterFromUI.getContainerType()==null ){
			if(offloadFilterFromUI.getFlightCarrierCode() != null || offloadFilterFromUI.getFlightNumber() !=null || offloadFilterFromUI.getFlightDate() !=null){
			  if(offloadFilterFromUI.getFlightCarrierCode() == null || offloadFilterFromUI.getFlightNumber() == null || offloadFilterFromUI.getFlightDate()== null ){
			log.log(Log.FINE, "empty container details and partial flight details");	
			ErrorVO errorVO = new ErrorVO(PARTIAL_FLIGHTDETAILS);
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			offloadFilterErrors.add(errorVO);	
			  }
			}
		}
		
		if(("").equals(offloadFilterFromUI.getFlightCarrierCode())&&("").equals(offloadFilterFromUI.getFlightNumber())&&("").equals(offloadFilterFromUI.getFlightDate())){
			if(!(("").equals(offloadFilterFromUI.getContainerNo()))||!(("").equals(offloadFilterFromUI.getContainerType()))){
				if(("").equals(offloadFilterFromUI.getContainerNo())||("").equals(offloadFilterFromUI.getContainerType())){
					log.log(Log.FINE, "empty  flight details and partial container details ");
					ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					offloadFilterErrors.add(errorVO);	


				}
			}	
		}
		
       if(offloadFilterFromUI.getFlightCarrierCode() == null && offloadFilterFromUI.getFlightNumber()==null && offloadFilterFromUI.getFlightDate()==null ){
    		
    	   if((offloadFilterFromUI.getContainerNo() !=null || offloadFilterFromUI.getContainerType() !=null)){
    		   if(offloadFilterFromUI.getContainerNo()==null || offloadFilterFromUI.getContainerType()==null ){
    			log.log(Log.FINE, "empty  flight details and partial container details ");
				ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				offloadFilterErrors.add(errorVO);
    		}
    	  }
       }
		if((!(("").equals(offloadFilterFromUI.getFlightCarrierCode()))||!(("").equals(offloadFilterFromUI.getFlightNumber()))||!(("").equals(offloadFilterFromUI.getFlightDate())))
				&& (!(("").equals(offloadFilterFromUI.getContainerNo()))||!(("").equals(offloadFilterFromUI.getContainerType())))){
			if(!(("").equals(offloadFilterFromUI.getFlightCarrierCode()))&&!(("").equals(offloadFilterFromUI.getFlightNumber()))&&!(("").equals(offloadFilterFromUI.getFlightDate()))||(!(("").equals(offloadFilterFromUI.getContainerNo()))&&!(("").equals(offloadFilterFromUI.getContainerType())))){

				log.log(Log.FINE, " full  flight details or full container details ");	
			}
			else{
				log.log(Log.FINE, " partial  flight details and partial container details ");
				ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				offloadFilterErrors.add(errorVO);	
			}

		}
		
		/*if(((offloadFilterFromUI.getFlightCarrierCode() != null)||!((offloadFilterFromUI.getFlightNumber() != null))||(offloadFilterFromUI.getFlightDate() != null))
				&& ((offloadFilterFromUI.getContainerNo()!= null)||offloadFilterFromUI.getContainerType()!= null)){
				log.log(Log.FINE, " partial  flight details and partial container details ");
				ErrorVO errorVO = new ErrorVO(PARTIAL_CONTAINERANDFLIGHTDETAILS);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				offloadFilterErrors.add(errorVO);	
		}*/
		
		return offloadFilterErrors;
	}
	

}
