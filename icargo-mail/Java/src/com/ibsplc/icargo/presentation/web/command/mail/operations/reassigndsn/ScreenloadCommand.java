/*
 * ScreenloadCommand.java Created on Apr 01, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassigndsn;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.icargo.framework.util.unit.UnitFormatter;
import com.ibsplc.icargo.framework.util.unit.vo.UnitRoundingVO;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignDSNSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignDSNForm;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author RENO K ABRAHAM
 * Command class : ScreenloadCommand
 *
 * Revision History
 *
 * Version      	Date      	    Author        		Description
 *
 *  0.1         APR 01, 2008  	 RENO K ABRAHAM           Coding
 */
public class ScreenloadCommand  extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "screenload_success";	   
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String CONST_FLIGHT = "FLIGHT";
	   private static final String SCREEN_ID = "mailtracking.defaults.reassigndsn";	
	   private static final String SCREEN_ID_EXPMAL = "mailtracking.defaults.mailexportlist";	
	   
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("ScreenloadReassignDSNCommand","execute");

	    	  
	    	ReassignDSNForm reassignDsnForm = (ReassignDSNForm)invocationContext.screenModel;
	    	ReassignDSNSession reassignDSNSession = getScreenSession(MODULE_NAME,SCREEN_ID);
	    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID_EXPMAL);
	    	
	    	ApplicationSessionImpl applicationSession = getApplicationSession();
			LogonAttributes logonAttributes = applicationSession.getLogonVO();
			
			//Added for Unit Component
			UnitRoundingVO unitRoundingVO = new UnitRoundingVO();
			reassignDSNSession.setWeightRoundingVO(unitRoundingVO);
			setUnitComponent(logonAttributes.getStationCode(),reassignDSNSession);	
			
			log.log(Log.FINE, "From Screen ===>", reassignDsnForm.getFromScreen());
			String selectedDSNs = reassignDsnForm.getSeldsn();
			Collection<ContainerDetailsVO> containerDetailsVOs = mailExportListSession.getContainerDetailsVOs();
			for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs ){
				if(containerDetailsVO.getDsnVOs()!=null && containerDetailsVO.getDsnVOs().size() >0 ){
					for(DSNVO dsnVO :containerDetailsVO.getDsnVOs()){
						if (selectedDSNs!=null && selectedDSNs.length() > 0 && 
								dsnVO.getDsn()!=null && dsnVO.getDsn().equals(selectedDSNs)) {
							String info=new StringBuilder(String.valueOf(dsnVO.getBags())).append("Pcs, ")
																						  .append(String.valueOf(dsnVO.getWeight()))
																						  .append("Kg").toString();
							reassignDsnForm.setSeldsninfo(info);
						}				
					}					
				}				
			}
			Collection<ContainerVO> containerVOs = new ArrayList<ContainerVO>();
			reassignDSNSession.setContainerVOs(containerVOs);
			FlightValidationVO flightValidationVO = new FlightValidationVO();
			reassignDSNSession.setFlightValidationVO(flightValidationVO);
			reassignDsnForm.setDeparturePort(logonAttributes.getAirportCode());			
			
			if("FLIGHT".equals(reassignDsnForm.getHideRadio())){
				reassignDsnForm.setAssignToFlight("DESTINATION");
			}else if("CARRIER".equals(reassignDsnForm.getHideRadio())){
				reassignDsnForm.setAssignToFlight(CONST_FLIGHT);
				
			}else{
				reassignDsnForm.setHideRadio("NONE");
				reassignDsnForm.setAssignToFlight(CONST_FLIGHT);
				reassignDsnForm.setInitialFocus(FLAG_YES);
			}
			reassignDsnForm.setReassignFocus("Y");
			reassignDsnForm.setDisplayPage("1");
			reassignDsnForm.setLastPageNum("0");
			reassignDsnForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_SCREENLOAD);
	    	invocationContext.target=TARGET;
	    	log.exiting("ScreenloadReassignDSNCommand","execute");
	    	
	    	
	    }
	    /**
		 * A-3251
		 * @param stationCode
		 * @param mailAcceptanceSession
		 * @return 
		 */
		private void setUnitComponent(String stationCode,
				ReassignDSNSession reassignDSNSession){
			UnitRoundingVO unitRoundingVO = null;
			try{
				log.log(Log.FINE, "station code is ----------->>", stationCode);
				unitRoundingVO = UnitFormatter.getStationDefaultUnit(
						stationCode, UnitConstants.WEIGHT);			
				log.log(Log.FINE, "unit vo for wt--in session---",
						unitRoundingVO);
				reassignDSNSession.setWeightRoundingVO(unitRoundingVO);		
				
			   }catch(UnitException unitException) {
					unitException.getErrorCode();
			   }
			
		}

}
