/*
 * ValidateContainerDetailsCommand.java Created on July 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.assigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerAssignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.area.AreaDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.uld.ULDDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.AssignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.AssignContainerForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class ValidateContainerDetailsCommand extends BaseCommand {

   private Log log = LogFactory.getLogger("MAILOPERATIONS");

   private static final String MODULE_NAME = "mail.operations";
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";
   private static final String TARGET_SUCCESS_ADDNEW = "validate_container_addnew_success";
   private static final String TARGET_SUCCESS_OK = "validate_container_ok_success";
   private static final String TARGET_FAILURE = "validate_container_failure";
   private static final String CONST_ADDNEW = "ADDNEW";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_CREATE = "CREATE";
   private static final String BLANKSPACE = "";
   private static final String CONST_ULD = "U";
   private static final String CONST_BULK = "B";
   private static final String FLAG_FLIGHT = "F";

   private static final String CON_VALIDATION_WARN = "contValidationFailed";
   private static final String OVERRIDE_CON_VALIDATION = "overrideConValidation";
   private static final String CON_REASSIGN_WARN_FLIGHT = "reassignContainerForFlight";
   private static final String CON_REASSIGN_WARN_DESTN = "reassignContainerForDestn";
   private static final String ULD_NOT_RELEASED_FROM_INBOUND_FLIGHT = "uldnotreleasedfrominboundflight";
   private static final String ULD_ALREADY_ASSIGNED_TO_CARRIER = "uldalreadyassignedtocarrier";
   
   private static final String CON_ASSIGNEDTO_DIFFFLT="uldalreadyassignedtoflgt";

   private static final String ULD_TYPE = "U";
   private static final String INVALID_ULD="mailtracking.defaults.assigncontainer.msg.err.invaliduldnumber";
   private static final String CHANGE_FLIGHT ="CHANGE_FLIGHT";

	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {

    	log.entering("ValidateContainerDetailsCommand","execute");

    	AssignContainerForm assignContainerForm =
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession =
    		getScreenSession(MODULE_NAME,SCREEN_ID);

    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
		boolean isCreateErrorExists = false;
		String currentaction = assignContainerForm.getCurrentAction();
		int currentindex = assignContainerForm.getCurrentIndex();

		updateBeforeValidation(
				assignContainerSession.getSelectedContainerVOs(),
				assignContainerForm,
				currentindex,
				logonAttributes,
				assignContainerSession);

		//	VALIDATING FORM
		errors = validateForm(assignContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}

		// VALIDATING DESTINATION
		String destn = assignContainerForm.getContainerDestination();
		AreaDelegate areaDelegate = new AreaDelegate();
    	AirportValidationVO airportValidationVO = null;
    	errors = new ArrayList<ErrorVO>();
    	if (destn != null && !"".equals(destn)) {
    		try {
    			airportValidationVO = areaDelegate.validateAirportCode(
    					logonAttributes.getCompanyCode(),
    					destn.toUpperCase());

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    	}


    	// VALIDATING POUS
    	String[] pous = assignContainerForm.getPointOfUnlading();
    	Map<String,AirportValidationVO> stations = new HashMap<String,AirportValidationVO>();
    	if (pous != null) {
    		Set<String> stns = new HashSet<String>();
    		String[] opFlag = assignContainerForm.getOpFlag();
    		for (int i = 0 ; i < pous.length ; i++) {
    			if(!"NOOP".equals(opFlag[i])  && !"D".equals(opFlag[i]) ){
    			   stns.add(pous[i].toUpperCase());
    			}
    		}

    		try {
    			stations = areaDelegate.validateAirportCodes(
    					logonAttributes.getCompanyCode(),
    					stns);

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    	}

    	// VALIDATING FLIGHT CARRIERS
    	String[] fltCarriers = assignContainerForm.getFltCarrier();
    	AirlineDelegate airlineDelegate = new AirlineDelegate();
    	Map<String,AirlineValidationVO> carrierIds = new HashMap<String,AirlineValidationVO>();
    	if (fltCarriers != null) {
    		Set<String> carriers = new HashSet<String>();
    		String[] opFlag = assignContainerForm.getOpFlag();
    		for (int i = 0 ; i < fltCarriers.length ; i++) {
    			if(!"NOOP".equals(opFlag[i])){
    			     carriers.add(fltCarriers[i].toUpperCase());
    			}
    		}

    		try {
    			carrierIds = airlineDelegate.validateAlphaCodes(
    					logonAttributes.getCompanyCode(),
    					carriers);

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    	}

    	// VALIDATING ULD NO
    	if (CONST_CREATE.equals(assignContainerForm.getStatus())
    			&& CONST_ULD.equals(assignContainerForm.getContainerType())) {
    		ULDDelegate uldDelegate = new ULDDelegate();
        	String uldNo = assignContainerForm.getContainerNumber();
        	try {
        		ULDValidationVO uldValidationVO = uldDelegate.validateULD(
        				logonAttributes.getCompanyCode(),
        				upper(uldNo));

    		}catch (BusinessDelegateException businessDelegateException) {
        		errors = handleDelegateException(businessDelegateException);
    		}
    		if (errors != null && errors.size() > 0) {
    			errors = new ArrayList<ErrorVO>();
    			Object[] obj = {uldNo};
				ErrorVO errorVO = new ErrorVO(
					INVALID_ULD,obj);
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);//Changed as part of ICRD-131163
				errors.add(errorVO);
    			invocationContext.addAllError(errors);
    			invocationContext.target = TARGET_FAILURE;
    			return;
    		}
    	}


    	if (CONST_CREATE.equals(assignContainerForm.getStatus())) {

    		String uldNo = upper(assignContainerForm.getContainerNumber());

    		Collection<ContainerVO> containerVOs = assignContainerSession.getContainerVOs();
    		int flag = 0;
    		if (containerVOs != null && containerVOs.size() > 0) {
    			for(ContainerVO containerVO:containerVOs){
    				//Modified as part of bug ICRD-129838 by A-5526
    				if(containerVO.getContainerNumber().equals(uldNo)){  
    					flag++;
    				}
    			}
    		}
    		if(flag == 1){
    			//Modified for ICRD-128507 by A-4810 for preventing invalid Error
    			isCreateErrorExists = true;
    			/*invocationContext.addError(new ErrorVO("mailtracking.defaults.sameflight"));
    			invocationContext.target = TARGET_FAILURE;
    			return;*/
    		}
    		flag = 0;
    		Collection<ContainerVO> selContainerVOs =
    			assignContainerSession.getSelectedContainerVOs();
    		if (selContainerVOs != null && selContainerVOs.size() > 0) {
    			for(ContainerVO containerVO:selContainerVOs){
    				//Modified as part of bug ICRD-129838 by A-5526
    				if(containerVO.getContainerNumber().equals(uldNo)){    
    					flag++;
    				}
    			}
    		}
    		if(flag == 2){
    			//Modified for ICRD-128507 by A-4810 for preventing invalid Error
    			isCreateErrorExists = true;
    			/*invocationContext.addError(new ErrorVO("mailtracking.defaults.sameflight"));
    			invocationContext.target = TARGET_FAILURE;
    			return;*/
    		}


    	}



		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		ContainerVO currentvo = updateContainerVO(
				selectedContainerVOs,
				assignContainerForm,
				currentindex,
				logonAttributes,
				carrierIds);


		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate =
    		new MailTrackingDefaultsDelegate();

		String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
    	String fromScreen = assignContainerForm.getFromScreen();
		String overrideFlag = assignContainerForm.getOverrideFlag();
        log.log(Log.FINE, "OverrideFlag------------> ", overrideFlag);
		if(!OVERRIDE_CON_VALIDATION.equals(overrideFlag)) {
    		if (currentvo != null) {
    			if (CONST_FLIGHT.equals(assignedto)) {
    				currentvo.setAssignmentFlag(FLAG_FLIGHT);
    			}
    			if(CHANGE_FLIGHT.equals(fromScreen)){
    				currentvo.setAssignedPort(currentvo.getPol());
    			}
    			else {
    				// validate whether the final destn is same as the carrier destn
    				/*String carrierDestn = assignContainerForm.getDestination();
    				String finalDest = currentvo.getFinalDestination();
    				if (!carrierDestn.equalsIgnoreCase(finalDest)) {
    					ErrorVO errorVO = new ErrorVO("mailtracking.defaults.finaldestinationinvalid");
    					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
    					errors = new ArrayList<ErrorVO>();
    					errors.add(errorVO);
    					invocationContext.addAllError(errors);
    					invocationContext.target = TARGET_FAILURE;
    					return;
    				}*/
    			}
    			log.log(Log.FINE, "Currentvo ------------> ", currentvo);
				if (!ContainerVO.OPERATION_FLAG_UPDATE.equals(currentvo.getOperationFlag())) {
    				try {
    					currentvo = mailTrackingDefaultsDelegate.validateContainer(logonAttributes.getAirportCode(), currentvo);
    					currentvo.setLastUpdateUser(logonAttributes.getUserId().toUpperCase());

    					log.log(Log.FINE, "Resultvo ------------> ", currentvo);

    				}catch (BusinessDelegateException businessDelegateException) {
    					errors = handleDelegateException(businessDelegateException);
    					assignContainerForm.setWarningCode(CON_VALIDATION_WARN);
    					if (errors != null && errors.size() > 0) {
    						for(ErrorVO vo : errors) {
    							if (("mailtracking.defaults.openedflight").equals(vo.getErrorCode())) {
    								assignContainerForm.setWarningCode(CON_REASSIGN_WARN_FLIGHT);
    								Object[] obj = vo.getErrorData();
    								ContainerAssignmentVO assignmentvo = (ContainerAssignmentVO)obj[2];
    								log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Flight)------------> ",
													assignmentvo);
									assignContainerSession.setContainerAssignmentVO(assignmentvo);
    								break;
    							}
    							else if (("mailtracking.defaults.canreassigned").equals(vo.getErrorCode())) {
    								assignContainerForm.setWarningCode(CON_REASSIGN_WARN_DESTN);
    								Object[] obj = vo.getErrorData();
    								ContainerAssignmentVO assignmentvo = (ContainerAssignmentVO)obj[2];
    								log
											.log(
													Log.FINE,
													"ContainerAssignmentVO (Destn)------------> ",
													assignmentvo);
									assignContainerSession.setContainerAssignmentVO(assignmentvo);
    								break;
    								//Added By Deepu for CR QF1551	starts
    							} else if (("mailtracking.defaults.err.uldnotreleasedfrominboundflight").equals(vo.getErrorCode()))  {
    								
    								Object[] obj = vo.getErrorData();
    								ContainerAssignmentVO assignmentvo = (ContainerAssignmentVO)obj[5];
    								log
											.log(
													Log.FINE,
													"ContainerAssignmentVO -----------> ",
													assignmentvo);
									if (assignmentvo.getFlightSequenceNumber() > 0) {
    									ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.uldnotreleasedfrominboundflight");
        								errorVO.setErrorData(obj);
        								assignContainerForm.setWarningCode(ULD_NOT_RELEASED_FROM_INBOUND_FLIGHT);
        								assignContainerSession.setContainerAssignmentVO(assignmentvo);
        								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
        								invocationContext.addError(errorVO);
    								} else {
    									ErrorVO errorVO = new ErrorVO("mailtracking.defaults.err.uldalreadyassignedtocarrier.warning");
        								errorVO.setErrorDisplayType(ErrorDisplayType.WARNING);
        								errorVO.setErrorData(obj);
        								assignContainerForm.setWarningCode(ULD_ALREADY_ASSIGNED_TO_CARRIER);
        								assignContainerSession.setContainerAssignmentVO(assignmentvo);
        								invocationContext.addError(errorVO);
    								}
    								
    								//invocationContext.addError(new ErrorVO(""));
    	    						invocationContext.target = TARGET_FAILURE;
    	    						return;
    							}
    							//Added By Deepu for CR QF1551 Ends
    							else if (("mailtracking.defaults.uldalreadyassignedtoflgt").equals(vo.getErrorCode())) {
    								assignContainerForm.setWarningCode(CON_ASSIGNEDTO_DIFFFLT);
    								Object[] obj = vo.getErrorData();
    								ContainerAssignmentVO assignmentvo = (ContainerAssignmentVO)obj[1];
    								assignContainerSession.setContainerAssignmentVO(assignmentvo);
    								ErrorVO errorVO = new ErrorVO("mailtracking.defaults.uldalreadyassignedtoflgt.warning");
    								errorVO.setErrorData(obj);
    								invocationContext.addError(errorVO);
    								invocationContext.target = TARGET_FAILURE;
    	    						return;
    							}
    						}
    						invocationContext.addAllError(errors);
    						invocationContext.target = TARGET_FAILURE;
    						return;
    					}
    				}
    			}
    		}
        }

		if(isCreateErrorExists){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.sameflight"));
			invocationContext.target = TARGET_FAILURE;
			return;
		}
    	// validate Final destination and last POU
    	log.log(Log.FINE,"validate Final destination and last POU ------------> " );
        Collection<OnwardRoutingVO> onwardRouting =
            currentvo.getOnwardRoutings();
       /* if(onwardRouting != null && onwardRouting.size() > 0) {
            String finalDest = currentvo.getFinalDestination();
            ArrayList<OnwardRoutingVO> routingList =
                (ArrayList<OnwardRoutingVO>)onwardRouting;
            OnwardRoutingVO finalRoute =
                routingList.get(onwardRouting.size() - 1);
            if(!finalDest.equalsIgnoreCase(finalRoute.getPou())) {
                ErrorVO errorVO = new ErrorVO("mailtracking.defaults.finaldestinationinvalid");
                errors.add(errorVO);
            }
        } */

		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		// UPDATE THE selectedContainerVOs WITH CURRENTVO
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentindex) {
				vo = currentvo;
				break;
			}
			index++;
		}


		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				assignContainerSession.getSelectedContainerVOs());
		if (CONST_ADDNEW.equals(currentaction)) {
			invocationContext.target = TARGET_SUCCESS_ADDNEW;
		}
		else {
			invocationContext.target = TARGET_SUCCESS_OK;
		}

    	log.exiting("ValidateContainerDetailsCommand","execute");

    }
    /**
     * Method used to update the current displayed vo
     * after validations
     * @param selectedContainerVOs
     * @param assignContainerForm
     * @param currentIndex
     * @param logonAttributes
     * @param carrierIds
     * @return
     */
    private ContainerVO updateContainerVO(
    		Collection<ContainerVO> selectedContainerVOs,
    		AssignContainerForm assignContainerForm,
    		int currentIndex,
    		LogonAttributes logonAttributes,
    		Map<String,AirlineValidationVO> carrierIds) {

    	ContainerVO displayVO = null;

    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		String[] fltCarriers = assignContainerForm.getFltCarrier();
		String[] fltNos = assignContainerForm.getFltNo();
		String[] depDates = assignContainerForm.getDepDate();
		String[] pous = assignContainerForm.getPointOfUnlading();

		// GETTING THE VO AT SPECIFIED INDEX AND UPDATING IT
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentIndex) {

				displayVO = vo;
				log.log(Log.FINE, "DisplayVO ------------> ", displayVO);
				break;
			}
			index++;
		}
		return displayVO;
    }
    /**
     * Method used to update the current displayed vo
     * @param selectedContainerVOs
     * @param assignContainerForm
     * @param currentIndex
     * @param logonAttributes
     * @param assignContainerSession
     */
    private void updateBeforeValidation(
    		Collection<ContainerVO> selectedContainerVOs,
    		AssignContainerForm assignContainerForm,
    		int currentIndex,
    		LogonAttributes logonAttributes,
    		AssignContainerSession assignContainerSession) {

    	String assignedto = assignContainerForm.getAssignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);
		// GETTING THE VO AT SPECIFIED INDEX AND UPDATING IT
		int index = 0;
		for (ContainerVO vo : selectedContainerVOs) {
			if (index == currentIndex) {

				vo.setType(assignContainerForm.getContainerType());
				vo.setContainerNumber(upper(assignContainerForm.getContainerNumber()));
				vo.setPou(upper(assignContainerForm.getPou()));
				if ("Y".equals(assignContainerForm.getPaBuilt())) {
					vo.setPaBuiltFlag("Y");
				}
				else {
					vo.setPaBuiltFlag("N");
				}
				vo.setFinalDestination(upper(assignContainerForm.getContainerDestination()));
				vo.setRemarks(assignContainerForm.getRemarks());

				if (CONST_FLIGHT.equals(assignedto)) {
					
				String[] oprflags = assignContainerForm.getOpFlag();
				Collection <OnwardRoutingVO> onwardRoutings = vo.getOnwardRoutings();
		    	
		    	int size = 0;
		    	if(onwardRoutings != null && onwardRoutings.size() > 0){
					   size = onwardRoutings.size();
		    	}
		    	Collection<OnwardRoutingVO> newOnwardRoutingVOs = new ArrayList<OnwardRoutingVO>();
		    	StringBuilder onwardFlightBuilder = new StringBuilder();
				for(int indx=0; indx<oprflags.length;indx++){
					if(indx >= size){
						if(!"NOOP".equals(oprflags[indx])){
							OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
							onwardRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
							onwardRoutingVO.setAssignmenrPort(logonAttributes.getAirportCode());
							onwardRoutingVO.setOnwardCarrierCode(assignContainerForm.getFltCarrier()[indx].toUpperCase().trim());
							onwardRoutingVO.setOnwardFlightNumber(assignContainerForm.getFltNo()[indx]);
							if (assignContainerForm.getDepDate()[indx] != null
									&& !BLANKSPACE.equals(assignContainerForm.getDepDate()[indx])) {
								LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
								onwardRoutingVO.setOnwardFlightDate(date.setDate(assignContainerForm.getDepDate()[indx]));
							}
							onwardRoutingVO.setPou(assignContainerForm.getPointOfUnlading()[indx].toUpperCase());
							onwardRoutingVO.setOperationFlag(assignContainerForm.getOpFlag()[indx]);
							newOnwardRoutingVOs.add(onwardRoutingVO);
							
							
							if (!OnwardRoutingVO.OPERATION_FLAG_DELETE.equals(onwardRoutingVO.getOperationFlag())) {

								if(onwardFlightBuilder.length() > 0) {
									onwardFlightBuilder.append(", ");
								}
								onwardFlightBuilder.append(onwardRoutingVO.getOnwardCarrierCode()).append("-")
								.append(onwardRoutingVO.getOnwardFlightNumber()).append("-")
								.append(assignContainerForm.getDepDate()[indx]).append("-")
								.append(onwardRoutingVO.getPou());
						    }
						}
					}else{
						int count = 0;
						if(onwardRoutings != null && onwardRoutings.size() > 0){
						   for(OnwardRoutingVO onwardRoutingVO:onwardRoutings){
							   if(count == indx){
								   if(!"NOOP".equals(oprflags[indx])){
									  //StringBuilder onwardFlightBuilder = new StringBuilder();
									   onwardRoutingVO.setOnwardCarrierCode(assignContainerForm.getFltCarrier()[indx].toUpperCase().trim());
									   onwardRoutingVO.setOnwardFlightNumber(assignContainerForm.getFltNo()[indx]);
									   if (assignContainerForm.getDepDate()[indx] != null
												&& !BLANKSPACE.equals(assignContainerForm.getDepDate()[indx])) {
									      LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
									      onwardRoutingVO.setOnwardFlightDate(date.setDate(assignContainerForm.getDepDate()[indx]));
									   }
									   onwardRoutingVO.setPou(assignContainerForm.getPointOfUnlading()[indx]);
									   if("N".equals(oprflags[index])){
										   onwardRoutingVO.setOperationFlag("U");//Modified for ICRD-152734
									   }else{
										   onwardRoutingVO.setOperationFlag(oprflags[indx]);
									   }
									   newOnwardRoutingVOs.add(onwardRoutingVO);
									   if (!OnwardRoutingVO.OPERATION_FLAG_DELETE.equals(onwardRoutingVO.getOperationFlag())) {

											if(onwardFlightBuilder.length() > 0) {
												onwardFlightBuilder.append(", ");
											}
											onwardFlightBuilder.append(onwardRoutingVO.getOnwardCarrierCode()).append("-")
											.append(onwardRoutingVO.getOnwardFlightNumber()).append("-")
											.append(assignContainerForm.getDepDate()[indx]).append("-")
											.append(onwardRoutingVO.getPou());
									   }
								   }
							   }
							   count++;
						   }
						}
					}
				}
		    	log.log(Log.FINE, "Updated onwardRoutingVOs------------> ",
						newOnwardRoutingVOs);
				vo.setOnwardRoutings(newOnwardRoutingVOs);
		    	vo.setOnwardFlights(onwardFlightBuilder.toString());
			  }
			}
			index++;
		}
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.FINE, "Updated selectedContainerVOs----> ",
				selectedContainerVOs);
    }
    /**
     * Method is used to get the carrierId for particule Carrier code
     * from the Map
     * @param carriercode
     * @param carrierIds
     * @return int
     */
    private int getCarrierIdForAirline(
    		String carriercode,
    		Map<String,AirlineValidationVO> carrierIds) {

    	int carrierId = 0;

    	AirlineValidationVO airlineValidationVO = carrierIds.get(upper(carriercode));

    	return airlineValidationVO.getAirlineIdentifier();
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
		}
			return BLANKSPACE;

	}

    /**
	 * This method is used for validating the form for the particular action
	 * @param assignContainerForm - AssignContainerForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			AssignContainerForm assignContainerForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();

		String assignedto = assignContainerForm.getAssignedto();
		String contType = assignContainerForm.getContainerType();

		if (("").equals(assignContainerForm.getContainerDestination())) {
//			ErrorVO errorVO = new ErrorVO(
//					"mailtracking.defaults.assigncontainer.msg.err.noDestn");
//			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
//			formErrors.add(errorVO);
		}
		else if (logonAttributes.getAirportCode().equalsIgnoreCase(assignContainerForm.getContainerDestination())
				&&!"CHANGE_FLIGHT".equals(assignContainerForm.getFromScreen())){
			ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.DestinationEqualsCurrentAirport");
			errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
			formErrors.add(errorVO);
		}
		//if (CONST_ULD.equals(contType)) {
			if (("").equals(assignContainerForm.getContainerNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noContainerNumber");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
		//}
		if (CONST_FLIGHT.equals(assignedto)) {

			String pou = assignContainerForm.getPou();
			String[] fltCarriers = assignContainerForm.getFltCarrier();
			String[] fltNos = assignContainerForm.getFltNo();
			String[] depDates = assignContainerForm.getDepDate();
			String[] pous = assignContainerForm.getPointOfUnlading();
			String[] opFlag = assignContainerForm.getOpFlag();
			if(assignContainerForm.getContainerDestination()==null ||
					assignContainerForm.getContainerDestination().trim().length()==0){
					ErrorVO errorVO = new ErrorVO(
					"mailtracking.defaults.assigncontainer.msg.err.noDestn");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
			}
			if (("").equals(pou)) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noPou");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			else if (pou.equalsIgnoreCase(assignContainerForm.getContainerDestination())
					&&!"CHANGE_FLIGHT".equals(assignContainerForm.getFromScreen())){
				if (pous != null) {
					for (int i = 0 ; i < pous.length ; i++) {
						if(!"NOOP".equals(opFlag[i])){
							ErrorVO errorVO = new ErrorVO(
									"mailtracking.defaults.assigncontainer.msg.err.pouequalsdestn");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							formErrors.add(errorVO);
							break;
						}
					}
				}
			}
			if (CONST_BULK.equals(contType)) {
				if (!("").equals(pou)
						&& !("").equals(assignContainerForm.getContainerDestination())) {
					if (!pou.equalsIgnoreCase(assignContainerForm.getContainerDestination())) {
						ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.PouNotEqualDestn");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
				}
			}

			if (fltCarriers != null) {
				boolean hasFlightError = false;
				for (int i = 0 ; i < fltCarriers.length ; i++) {
					if(!"NOOP".equals(opFlag[i]) && !"D".equals(opFlag[i])){             
					if (("").equals(fltCarriers[i])) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.assigncontainer.msg.err.noFltCarrier");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(fltNos[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noFltNo");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(depDates[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noFltDate");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(pous[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noPou");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (hasFlightError) {
						break;
					}
				  }
				}
				log.log(Log.INFO, "hasFlightError-----------------",
						hasFlightError);
			}

			if (pous != null) {

				// validating whether pou is current airport
				boolean hasPouError = false;
				for (int i = 0 ; i < pous.length ; i++) {
					if(!"NOOP".equals(opFlag[i])){
						if ( pous[i] != null && !("").equals(pous[i])) {
							if (pous[i].equalsIgnoreCase(logonAttributes.getAirportCode())) {
								ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.pouEqualsCurrentAirport");
								errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
								formErrors.add(errorVO);
								hasPouError = true;
							}
							if(pou !=null && pou.trim().length() >0){
								if(pous[i].equalsIgnoreCase(pou)){
									ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.pouequalspou");
									errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
									formErrors.add(errorVO);
									hasPouError = true;
								}
								
							}
						}
					}
					if (hasPouError) {
						break;
					}
				}

				// validation for duplicate pous
				int rows = pous.length;
				for (int i=0; i<rows; i++) {
					for (int j=i+1; j<rows; j++) {
						if(!"NOOP".equals(opFlag[i])){
							if((opFlag[i]==null || !"D".equals(opFlag[i])) &&
									(opFlag[j]==null || !"D".equals(opFlag[j]))){
								if ((pous[i] != null) &&
										(pous[i].equalsIgnoreCase(pous[j]))){
									Object obj[] = {pous[i].toUpperCase()};
									ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.pouduplicated",obj);
									errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
									formErrors.add(errorVO);
									break;
								}
							}
						}
					}
				}
			}

			}


		return formErrors;
	}

}
