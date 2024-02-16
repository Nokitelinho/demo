/*
 * ScreenloadReassignMailbagCommand.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmailbag;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class ScreenloadReassignMailbagCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "success";
	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.reassignmailbag";
	   
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ScreenloadReassignMailbagCommand","execute");
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();  
	    	ReassignMailbagForm reassignMailbagForm =(ReassignMailbagForm)invocationContext.screenModel;	    	
	    	ReassignMailbagSession reassignMailbagSession = getScreenSession(MODULE_NAME,SCREEN_ID);   	
	    	
	    	reassignMailbagForm.setHideRadio("CARRIER");
	    	reassignMailbagForm.setAssignToFlight("FLIGHT");
	    	reassignMailbagForm.setDeparturePort(logonAttributes.getAirportCode());
	    	Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();	    	
	    	
	    	LocalDate dat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			String date = dat.toDisplayDateOnlyFormat();
			String time = dat.toDisplayFormat("HH:mm");		
			reassignMailbagForm.setScanDate(date);
			reassignMailbagForm.setMailScanTime(time);			
	    				
	    	reassignMailbagSession.setContainerVOs(containerVOs);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			reassignMailbagSession.setFlightValidationVO(flightValidationVO);

			reassignMailbagForm.setDisplayPage(MailConstantsVO.ONE);
			reassignMailbagForm.setLastPageNum(MailConstantsVO.ZERO);
		   	invocationContext.target = TARGET;
	    	log.exiting("ScreenloadReassignMailCommand","execute");
	    	
	    }
	    
	    
	}