/*
 * AddOnwardRoutingCommand.java Created on Jun 30 2016
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

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.shared.airline.AirlineDelegate;
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
public class AddOnwardRoutingCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.assignContainer";	
   private static final String TARGET_SUCCESS = "add_onwardroute_success"; 
   private static final String TARGET_FAILURE = "add_onwardroute_failure";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String BLANKSPACE = "";
  
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("AddOnwardRoutingCommand","execute");
    	  
    	AssignContainerForm assignContainerForm = 
    		(AssignContainerForm)invocationContext.screenModel;
    	AssignContainerSession assignContainerSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID); 
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
    	Collection<ErrorVO> errors = null;
    	
    	int currentindex = assignContainerForm.getCurrentIndex();
    	
    	updateBeforeValidation(
				assignContainerSession.getSelectedContainerVOs(),
				assignContainerForm,
				currentindex,
				logonAttributes,
				assignContainerSession);
    	
    	// VALIDATING FORM
		errors = validateForm(assignContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
				
		Collection<ContainerVO> selectedContainerVOs = assignContainerSession.getSelectedContainerVOs();		
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		// UPDATING THE CONTAINERVO CURRENTLY DISPLAYED
		ContainerVO currentvo = updateContainerVO(
				selectedContainerVOs,
				assignContainerForm,
				currentindex,
				logonAttributes);
		
		String contNo = assignContainerForm.getContainerNumber();
		log.log(Log.INFO, "contNo:-------->>", contNo);
		if (currentvo != null) {
			log.log(Log.FINE, "Currentvo ------------> ", currentvo);
			//	UPDATE THE selectedContainerVOs WITH CURRENTVO
			int index = 0;
			for (ContainerVO vo : selectedContainerVOs) {
				if (index == currentindex) {
					vo = currentvo;
					break;
				}				
				index++;
			}
		}					
		
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
		log.log(Log.INFO, "SelectedContainerVOs after:-------->>",
				assignContainerSession.getSelectedContainerVOs());
		invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("AddOnwardRoutingCommand","execute");
    	
    }
    /**
     * Method for updating the currnet vo that is displayed
     * @param selectedContainerVOs
     * @param assignContainerForm
     * @param currentIndex
     * @param logonAttributes
     * @return ContainerVO
     */
    private ContainerVO updateContainerVO(
    		Collection<ContainerVO> selectedContainerVOs,
    		AssignContainerForm assignContainerForm,
    		int currentIndex,
    		LogonAttributes logonAttributes) {
    	
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
				displayVO.setType(assignContainerForm.getContainerType());
				displayVO.setContainerNumber(assignContainerForm.getContainerNumber());
				displayVO.setPou(upper(assignContainerForm.getPou()));
				if ("Y".equals(assignContainerForm.getPaBuilt())) {
					displayVO.setPaBuiltFlag("Y");
				}
				else {
					displayVO.setPaBuiltFlag("N");
				}
				displayVO.setFinalDestination(upper(assignContainerForm.getContainerDestination()));
				displayVO.setRemarks(assignContainerForm.getRemarks());
								
				Collection <OnwardRoutingVO> onwardRoutings = displayVO.getOnwardRoutings();
				if (onwardRoutings == null) {
					onwardRoutings = new ArrayList<OnwardRoutingVO>();
				}
				if (fltCarriers != null) {						
					int row = 0;
					for (OnwardRoutingVO routevo : onwardRoutings) {
						//routevo.setCarrierId();
						routevo.setOnwardCarrierCode(upper(fltCarriers[row]));						
						routevo.setOnwardFlightNumber(fltNos[row]);
						LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);			 		
						if (depDates[row] != null 
								&& !BLANKSPACE.equals(depDates[row])) {
							routevo.setOnwardFlightDate(date.setDate(
									depDates[row]));
						}
						routevo.setPou(upper(pous[row]));
						
						row++;
					}		
				}
				//	ADDING NEW VO
				OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
				onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
				onwardRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
				onwardRoutingVO.setAssignmenrPort(logonAttributes.getAirportCode());
				onwardRoutings.add(onwardRoutingVO);
				displayVO.setOnwardRoutings(onwardRoutings);
				
				log.log(Log.FINE, "DisplayVO ------------> ", displayVO);
				break;
			}
			index++;
		}		
		return displayVO;   	
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
		String[] fltCarriers = assignContainerForm.getFltCarrier();
		String[] fltNos = assignContainerForm.getFltNo();
		String[] depDates = assignContainerForm.getDepDate();
		String[] pous = assignContainerForm.getPointOfUnlading();

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
					Collection <OnwardRoutingVO> onwardRoutings = vo.getOnwardRoutings();
					if (fltCarriers != null) {
						int row = 0;
						for (OnwardRoutingVO routevo : onwardRoutings) {

							routevo.setOnwardCarrierCode(upper(fltCarriers[row]));
							routevo.setOnwardFlightNumber(fltNos[row]);
							LocalDate date = new LocalDate(logonAttributes.getAirportCode(),ARP,false);
							if (depDates[row] != null
									&& !BLANKSPACE.equals(depDates[row])) {
								routevo.setOnwardFlightDate(date.setDate(
										depDates[row]));
							}
							routevo.setPou(upper(pous[row]));

							row++;
						}
						vo.setOnwardRoutings(onwardRoutings);
					}
				}
				break;
			}
			index++;
		}
		assignContainerSession.setSelectedContainerVOs(selectedContainerVOs);
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
		String pou = assignContainerForm.getPou();
		String[] fltCarriers = assignContainerForm.getFltCarrier();
		String[] fltNos = assignContainerForm.getFltNo();
		String[] depDates = assignContainerForm.getDepDate();
		String[] pous = assignContainerForm.getPointOfUnlading();
		String[] opFlag = assignContainerForm.getOpFlag();
		
		if (CONST_FLIGHT.equals(assignedto)) {
			if (pou != null && !("").equals(pou)) {
				if (("").equals(assignContainerForm.getContainerDestination())) {
					ErrorVO errorVO = new ErrorVO(
							"mailtracking.defaults.assigncontainer.msg.err.noDestn");
					errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
					formErrors.add(errorVO);
				}
				else {
					if (pou.equalsIgnoreCase(assignContainerForm.getContainerDestination())) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.assigncontainer.msg.err.pouequalsdestn");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
					}
					else {
						if (fltCarriers != null) {
							boolean hasFlightError = false;
							for (int i = 0 ; i < fltCarriers.length ; i++) {
								
								if (("").equals(fltCarriers[i])) {
									ErrorVO errorVO = new ErrorVO(
											"mailtracking.defaults.assigncontainer.msg.err.noFltCarrier");
									errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
									formErrors.add(errorVO);
									hasFlightError = true;
								}
								
//			    	VALIDATE FLIGHT CARRIER CODE--added by anitha--START
		    		AirlineDelegate airlineDelegate = new AirlineDelegate();
		    		AirlineValidationVO airlineValidationVO = null;
		    		String flightCarrierCode = fltCarriers[i];        	
		        	if (flightCarrierCode != null && !"".equals(flightCarrierCode)) {
		        		Collection<ErrorVO> errors=null;
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
		    				formErrors.add(new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.invalidCarrier"
		        					,obj));
		    				hasFlightError = true;
		    				 
		        		}
		        	}
//					VALIDATE FLIGHT CARRIER CODE--added by anitha--END					   
					        	
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
							log.log(Log.INFO,
									"hasFlightError-----------------",
									hasFlightError);
						}
					}
				}
			}
			else {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.assigncontainer.msg.err.noPou");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			
			if (pous != null) {
				
				// validating whether POU is equal to current airport
				boolean hasPouError = false;
				for (int i = 0 ; i < pous.length ; i++) {
					if ( pous[i] != null && !("").equals(pous[i])) {
						if (pous[i].equalsIgnoreCase(logonAttributes.getAirportCode())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.pouEqualsCurrentAirport");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							formErrors.add(errorVO);
							hasPouError = true;
						}
					}
					if (hasPouError) {
						break;
					}
				}
				
				// validating whether full route is already covered
				
				int length = pous.length;
				String lastPou = pous[length - 1];
				if(opFlag[length - 1]==null || !"D".equals(opFlag[length - 1])){
				String finalDestn = assignContainerForm.getContainerDestination();
				if (lastPou != null && !("").equals(lastPou)) {
					if (finalDestn != null && !("").equals(finalDestn)) {
						if (lastPou.equalsIgnoreCase(finalDestn)) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.assigncontainer.msg.err.noRouting");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							formErrors.add(errorVO);
						}
					}
				}}
			}
		}
		
		return formErrors;
	}
          
}
