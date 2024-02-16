/*
 * AddOnwardRoutingCommand.java Created on June 22, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigncontainer;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignContainerForm;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory; 


/**
 * @author A-1861
 *
 */
public class AddOnwardRoutingCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignContainer";
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String TARGET_SUCCESS = "add_onwardroute_success"; 
   private static final String TARGET_FAILURE = "add_onwardroute_failure";
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
    	  
    	ReassignContainerForm reassignContainerForm = 
    		(ReassignContainerForm)invocationContext.screenModel;
    	ReassignContainerSession reassignContainerSession = 
    		(ReassignContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID);
    	
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = null;
				
		Collection<ContainerVO> selectedContainerVOs = reassignContainerSession.getSelectedContainerVOs();		
		log.log(Log.INFO, "SelectedContainerVOs before:-------->>",
				selectedContainerVOs);
		// UPDATING THE CONTAINERVO IN SESSION
		ContainerVO currentvo = updateContainerVO(
				reassignContainerSession,
				reassignContainerForm,
				logonAttributes);
		
		//	VALIDATING FORM
		errors = validateForm(reassignContainerForm,logonAttributes);
		if (errors != null && errors.size() > 0) {
			invocationContext.addAllError(errors);
			invocationContext.target = TARGET_FAILURE;
			return;
		}
		
		//	ADDING NEW VO
		Collection <OnwardRoutingVO> onwardRoutings = currentvo.getOnwardRoutings();
		if (onwardRoutings == null) {
			onwardRoutings = new ArrayList<OnwardRoutingVO>();
		}
		OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
		onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
		onwardRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
		onwardRoutingVO.setAssignmenrPort(logonAttributes.getAirportCode());
		onwardRoutings.add(onwardRoutingVO);
		currentvo.setOnwardRoutings(onwardRoutings);
		
		log.log(Log.INFO, "currentvo:-------->>", currentvo);
		reassignContainerSession.setContainerVO(currentvo);
		
    	invocationContext.target = TARGET_SUCCESS;		 	
       	
    	log.exiting("AddOnwardRoutingCommand","execute");
    	
    }
   /**
    * Method for updating the currnet vo that is displayed
    * @param reassignContainerSession
    * @param reassignContainerForm
    * @param logonAttributes
    * @return ContainerVO
    */
    private ContainerVO updateContainerVO(
    		ReassignContainerSession reassignContainerSession,
    		ReassignContainerForm reassignContainerForm,
    		LogonAttributes logonAttributes) {
    	
    	String reassignedto = reassignContainerForm.getReassignedto();
    	log.log(Log.FINE, "ASSIGNED TO ------------> ", reassignedto);
		String[] fltCarriers = reassignContainerForm.getFltCarrier();
		String[] fltNos = reassignContainerForm.getFltNo();
		String[] depDates = reassignContainerForm.getDepDate();
		String[] pous = reassignContainerForm.getPointOfUnlading();
		
		ContainerVO containerVO = reassignContainerSession.getContainerVO();
						
		Collection <OnwardRoutingVO> onwardRoutings = containerVO.getOnwardRoutings();
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
	
		return containerVO;   	
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
	 * @param reassignContainerForm - ReassignContainerForm
	 * @param logonAttributes - LogonAttributes
	 * @return errors - Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateForm(
			ReassignContainerForm reassignContainerForm,
			LogonAttributes logonAttributes) {

		Collection<ErrorVO> formErrors = new ArrayList<ErrorVO>();
		
		String reassignedto = reassignContainerForm.getReassignedto();
		
		if (CONST_FLIGHT.equals(reassignedto)) {    		
			if (("").equals(reassignContainerForm.getFlightCarrierCode())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightCarrierCode");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightNumber())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightNumber");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightDate())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightDate");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
			if (("").equals(reassignContainerForm.getFlightPou())) {
				ErrorVO errorVO = new ErrorVO(
						"mailtracking.defaults.reassigncontainer.msg.err.noFlightPou");
				errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
				formErrors.add(errorVO);
			}
						
			String[] fltCarriers = reassignContainerForm.getFltCarrier();
			String[] fltNos = reassignContainerForm.getFltNo();
			String[] depDates = reassignContainerForm.getDepDate();
			String[] pous = reassignContainerForm.getPointOfUnlading();
			
			if (fltCarriers != null) {
				boolean hasFlightError = false;
				for (int i = 0 ; i < fltCarriers.length ; i++) {
					if (("").equals(fltCarriers[i])) {
						ErrorVO errorVO = new ErrorVO(
								"mailtracking.defaults.reassigncontainer.msg.err.noFltCarrier");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(fltNos[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltNo");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(depDates[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noFltDate");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (("").equals(pous[i])) {
						ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.noPou");
						errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
						formErrors.add(errorVO);
						hasFlightError = true;
					}
					if (hasFlightError) {
						break;
					}
				}
				log.log(Log.INFO, "hasFlightError-----------------",
						hasFlightError);
				// validating whether POU is equal to current airport
				boolean hasPouError = false;
				for (int i = 0 ; i < pous.length ; i++) {
					if ( pous[i] != null && !("").equals(pous[i])) {
						if (pous[i].equalsIgnoreCase(logonAttributes.getAirportCode())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.msg.err.pouEqualsCurrentAirport");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							formErrors.add(errorVO);
							hasPouError = true;
						}
						if (pous[i].equalsIgnoreCase(reassignContainerForm.getFlightPou())) {
							ErrorVO errorVO = new ErrorVO("mailtracking.defaults.reassigncontainer.onwardroutingpousame");
							errorVO.setErrorDisplayType(ErrorDisplayType.ERROR);
							formErrors.add(errorVO);
							hasPouError = true;
							
						}
					}
					if (hasPouError) {
						break;
					}
				}										
			}		
		}
		
		return formErrors;
	}
}