/*
 * ClearReassignDSNCommand.java Created on Apr 02, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigndsn;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author Reno K Abraham
 * Command class : ClearReassignDSNCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 01, 2008  	 RENO K ABRAHAM         Coding
 */
public class ClearReassignDSNCommand  extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "clear_success";   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";	
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ClearReassignDSNCommand","execute");
	    	  
	    	ReassignDSNForm reassignDSNForm = (ReassignDSNForm)invocationContext.screenModel;
	    	ReassignDSNSession reassignDSNSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
			reassignDSNSession.setContainerVOs(containerVOs);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			reassignDSNSession.setFlightValidationVO(flightValidationVO);
			reassignDSNForm.setDeparturePort(logonAttributes.getAirportCode());

			//START : Updating the ReassignedPcs And ReassignedWgt 
	    	Collection<DespatchDetailsVO> despatchDetailVOs=reassignDSNSession.getDespatchDetailsVOs();
			int k=0;
			if(despatchDetailVOs!=null && despatchDetailVOs.size()>0){
				for(DespatchDetailsVO despatchDetailsVO: despatchDetailVOs){
					if((reassignDSNForm.getReAssignedPcs()[k]!=null || !("").equals(reassignDSNForm.getReAssignedPcs()[k]))
							&& reassignDSNForm.getReAssignedPcs()[k].length()>0){
						despatchDetailsVO.setAcceptedBags(Integer.parseInt(reassignDSNForm.getReAssignedPcs()[k]));
					}else{
						despatchDetailsVO.setAcceptedBags(0);
					}
					if((reassignDSNForm.getReAssignedWt()[k]!=null || !("").equals(reassignDSNForm.getReAssignedWt()[k]))
							&& reassignDSNForm.getReAssignedWt()[k].length()>0){
						//despatchDetailsVO.setAcceptedWeight(Double.parseDouble(reassignDSNForm.getReAssignedWt()[k]));
						despatchDetailsVO.setAcceptedWeight(reassignDSNForm.getReAssignedWtMeasure()[k]);//added by A-7371
					}else{
						despatchDetailsVO.setAcceptedWeight(new Measure(UnitConstants.MAIL_WGT,0));//added by A-7371
					}
					k++;			
				}
		    	reassignDSNSession.setDespatchDetailsVOs(despatchDetailVOs);
			}
	    	//END
			
			if("FLIGHT".equals(reassignDSNForm.getHideRadio())){
				reassignDSNForm.setAssignToFlight("DESTINATION");
			}else if("CARRIER".equals(reassignDSNForm.getHideRadio())){
				reassignDSNForm.setAssignToFlight(CONST_FLIGHT);
				
			}else{
				reassignDSNForm.setHideRadio("NONE");
				reassignDSNForm.setAssignToFlight(CONST_FLIGHT);
				reassignDSNForm.setInitialFocus(FLAG_YES);
			}
			
			reassignDSNForm.setFlightCarrierCode("");
			reassignDSNForm.setFlightNumber("");
			reassignDSNForm.setDepDate("");
			reassignDSNForm.setCarrierCode("");
			reassignDSNForm.setDestination("");
			reassignDSNForm.setDuplicateFlightStatus(FLAG_NO);
			reassignDSNForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
			
			invocationContext.target = TARGET;
	       	
	    	log.exiting("ClearReassignDSNCommand","execute");
	    	
	    }

}
