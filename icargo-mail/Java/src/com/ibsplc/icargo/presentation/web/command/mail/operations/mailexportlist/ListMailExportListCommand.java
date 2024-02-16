/*
 * ListMailExportListCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.mailexportlist;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.delegate.shared.defaults.SharedDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.flight.operation.DuplicateFlightSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailExportListSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailExportListForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
/**
 * @author A-5991
 *
 */
public class ListMailExportListCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailexportlist";
   private static final String SCREEN_ID_EMPTYULD = "mailtracking.defaults.emptyulds";	
   
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   
   private static final String CONST_FLIGHT = "FLIGHT";
   private static final String CONST_ASSIGNCONTAINER = "ASSIGNCONTAINER";
   private static final String OUTBOUND = "O";
   private static final String FLIGHT_TBC_TBA = "flight_tba_tbc";
   private static final String FLIGHT_TBC_TBA_STATUS = "flight_tba_tbc_status";
   
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListMailExportListCommand","execute");
    	  
    	MailExportListForm mailExportListForm =  (MailExportListForm)invocationContext.screenModel;
    	MailExportListSession mailExportListSession = getScreenSession(MODULE_NAME,SCREEN_ID);    	
		DuplicateFlightSession duplicateFlightSession = getScreenSession(MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
    	EmptyULDsSession emptyULDsSession = getScreenSession(MODULE_NAME,SCREEN_ID_EMPTYULD);
    	
		mailExportListForm.setSelectMail(null);
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MailAcceptanceVO mailAcceptanceVO = mailExportListSession.getMailAcceptanceVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Modified by A-7794 as part of ICRD-197439
		operationalFlightVO.setPol(mailExportListForm.getDeparturePort());
		
		 MailAcceptanceVO newMailAcceptanceVO = new MailAcceptanceVO();
		 FlightValidationVO flightValidationVO = new FlightValidationVO();
		 if(FLAG_YES.equals(mailExportListForm.getDuplicateFlightStatus())){
			 mailExportListForm.setAssignToFlight(CONST_FLIGHT);
		 }
		String assignTo = mailExportListForm.getAssignToFlight();
		log.log(Log.FINE, "assignTo === ", assignTo);
		String fromscreen = mailExportListForm.getFromScreen();
		
		/*If Any EMPTY ULD is present in the emptyULDsSession,
		 * Then EMPTY_ULD Popup should come.
		 *And once if the EMPTY_ULD Popup is invoked and closed,
		 *the session should be cleared.
		 */
		if("EMPTY_ULD".equals(fromscreen)){
			mailExportListForm.setFromScreen("");
			emptyULDsSession.setContainerDetailsVOs(null);
		}
		if(emptyULDsSession.getContainerDetailsVOs()!=null 
				&& emptyULDsSession.getContainerDetailsVOs().size()>0){
			mailExportListForm.setStatus("SHOW_EMPTY_ULD_POPUP");
    		invocationContext.target = TARGET;
    		return;
		}
		if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
			
			log.log(Log.FINE, "*******FLIGHT MODE******");			
				if(FLAG_YES.equals(mailExportListForm.getDuplicateFlightStatus())){
					mailExportListSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
					mailExportListForm.setDuplicateFlightStatus(FLAG_NO);
					//Added by A-5249 for the ICRD-84046 starts				
					FlightValidationVO flightValidVO=duplicateFlightSession.getFlightValidationVO();					
                    if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
                                FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
                          if(!FlightValidationVO.FLAG_YES.equals(mailExportListForm.getWarningOveride())){
                        	  flightValidVO.setFlightRoute(null);
  							flightValidVO.setAircraftType(null);
  							flightValidVO.setEta(null);
  							flightValidVO.setEtd(null);
  							flightValidVO.setSta(null);
  							flightValidVO.setStd(null);
  							flightValidVO.setAta(null);
  							flightValidVO.setAtd(null);
                          ErrorVO err = new ErrorVO("mailtracking.defaults.flightintbcortba");
                          err.setErrorDisplayType(ErrorDisplayType.WARNING);
                          invocationContext.addError(err);
                          invocationContext.target = TARGET;
                          mailExportListForm.setWarningFlag("list_flight_tba_tbc");
                          mailExportListForm.setDuplicateAndTbaTbc(MailConstantsVO.FLAG_YES);
                          return;
                      }else{
                    	  mailExportListForm.setWarningFlag("");
                    	  mailExportListForm.setWarningOveride(null);
                    	  flightValidVO.setFlightRoute(null);
							flightValidVO.setAircraftType(null);
							flightValidVO.setEta(null);
							flightValidVO.setEtd(null);
							flightValidVO.setSta(null);
							flightValidVO.setStd(null);
							flightValidVO.setAta(null);
							flightValidVO.setAtd(null);
                       }                        
                          mailExportListForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
                    }else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
                    	Object[] obj = {flightValidVO.getCarrierCode().toUpperCase(),flightValidVO.getFlightNumber()};
						ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
						err.setErrorDisplayType(ErrorDisplayType.ERROR);
						invocationContext.addError(err);
						invocationContext.target = TARGET;
						return;
					} 
                  //Added by A-5249 for the ICRD-84046 ends
					
					
				}
				
				log.log(Log.FINE, "flightValidationVO in MA session...",
						mailExportListSession.getFlightValidationVO());
				flightValidationVO = mailExportListSession.getFlightValidationVO();
				Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
				if(oneTimes!=null){
					Collection<OneTimeVO> resultStatus=
						oneTimes.get("flight.operation.flightlegstatus");
					log.log(Log.FINE, "*******flightlegstatus******");
					flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
					flightValidationVO.setDirection(OUTBOUND);
				}
				mailExportListSession.setFlightValidationVO(flightValidationVO);
				
				operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
				operationalFlightVO.setCarrierCode(flightValidationVO.getCarrierCode());
				operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
				operationalFlightVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
				operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
				operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
				operationalFlightVO.setFlightStatus(flightValidationVO.getFlightStatus());
				operationalFlightVO.setDirection(OUTBOUND);
			
			log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
			    	newMailAcceptanceVO = new MailTrackingDefaultsDelegate().findFlightAcceptanceDetails(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		mailExportListSession.setMailAcceptanceVO(mailAcceptanceVO);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	    	  FlightValidationVO fltVal=mailExportListSession.getFlightValidationVO();
	    	  log.log(Log.FINE, "*******newMailAcceptanceVO******",
					newMailAcceptanceVO);
			log.log(Log.FINE, "*******fltVal******", fltVal);
			if(fltVal!=null){
	    		  if(newMailAcceptanceVO.getFlightStatus()==null || "".equals(newMailAcceptanceVO.getFlightStatus())){
	    			  mailExportListForm.setOperationalStatus("NONE");	    					
	    		  }else{
	    			  if(oneTimes!=null){
	    				  Collection<OneTimeVO> resultStatus=
	    					  oneTimes.get("mailtracking.defaults.flightstatus");
	    				  log.log(Log.FINE, "*******flightlegstatus******");
	    				  fltVal.setOperationalStatus(findOneTimeDescription(resultStatus,newMailAcceptanceVO.getFlightStatus()));
	    			  }	    				
	    			  if("O".equals(newMailAcceptanceVO.getFlightStatus())){
	    				  mailExportListForm.setOperationalStatus("OPEN");	  	    					
	    			  }else
	    				  if("C".equals(newMailAcceptanceVO.getFlightStatus())){
	    					  mailExportListForm.setOperationalStatus("CLOSED");	  	    					
	    				  }
	    		  }
	    		  mailExportListSession.setFlightValidationVO(fltVal);
	    	  }
		}else{
			log.log(Log.FINE, "*******DESTINATION MODE******");			
			
				operationalFlightVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				operationalFlightVO.setCarrierId(mailAcceptanceVO.getCarrierId());
				operationalFlightVO.setPou(mailAcceptanceVO.getDestination());
	            operationalFlightVO.setFlightNumber("-1");
	            operationalFlightVO.setLegSerialNumber(-1);
	            operationalFlightVO.setFlightSequenceNumber(-1);
			
			log.log(Log.FINE, "operationalFlightVO in MA session...",
					operationalFlightVO);
			try {
			    	newMailAcceptanceVO = new MailTrackingDefaultsDelegate().findDestinationAcceptanceDetails(operationalFlightVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		mailExportListSession.setMailAcceptanceVO(mailAcceptanceVO);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
		}
		//Added by A-7794 as part of ICRD-197439
		if(!Objects.equals(logonAttributes.getAirportCode(), mailExportListForm.getDeparturePort())){
			   mailExportListForm.setDisableButtonsForAirport(FLAG_YES);
			    }   else {
				mailExportListForm.setDisableButtonsForAirport(FLAG_NO);
				}
    	  if(newMailAcceptanceVO == null){
    		  newMailAcceptanceVO = new MailAcceptanceVO();
    	  }
    	  newMailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
    	//Modified by A-7794 as part of ICRD-197439
    	  newMailAcceptanceVO.setPol(mailExportListForm.getDeparturePort());
    	  if(CONST_FLIGHT.equalsIgnoreCase(assignTo)){
    		  newMailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
    		  newMailAcceptanceVO.setFlightNumber(mailAcceptanceVO.getFlightNumber());
    		  newMailAcceptanceVO.setFlightDate(mailAcceptanceVO.getFlightDate());
    		  newMailAcceptanceVO.setCarrierId(mailAcceptanceVO.getCarrierId());
    		  newMailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
    		  //Added by A-5160 when flight status changes to TBA leg serial number in flight table and mail table will be different
    		  if (!(newMailAcceptanceVO.getLegSerialNumber() > 0)) {
    		  newMailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
  			  }
    		  else{
    			  operationalFlightVO.setLegSerialNumber(newMailAcceptanceVO.getLegSerialNumber());
    		  }
    		  newMailAcceptanceVO.setStrFlightDate(mailAcceptanceVO.getStrFlightDate());

    		  if(newMailAcceptanceVO.getContainerDetails()==null){    			  
    			  mailExportListSession.getMailAcceptanceVO().setContainerDetails(null);
    			  ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailexportlist.nocontainers");
    			  invocationContext.addError(errorVO);
    			  mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    			  invocationContext.target = TARGET;
    			  return;
    		  }
    	  }else{
    		  newMailAcceptanceVO.setDestination(mailAcceptanceVO.getDestination());
    		  newMailAcceptanceVO.setFlightCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
    		  newMailAcceptanceVO.setCarrierId(mailAcceptanceVO.getCarrierId());
    		  newMailAcceptanceVO.setFlightNumber("-1");
    		  newMailAcceptanceVO.setFlightSequenceNumber(-1);
    		  newMailAcceptanceVO.setLegSerialNumber(-1);
    		  mailExportListForm.setDisableDestnFlag(FLAG_YES);

    		  if(newMailAcceptanceVO.getContainerDetails()==null){
    			  mailExportListSession.getMailAcceptanceVO().setContainerDetails(null);  	     		 
    			  ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailexportlist.nocontainersforcarrier");
    			  invocationContext.addError(errorVO);
    			  mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    			  invocationContext.target = TARGET;
    			  return;
    		  }
    	  }
    	Collection<DSNVO> dsnVOs = new ArrayList<DSNVO>();
    	Collection<ContainerDetailsVO> containerDetailsVOs = newMailAcceptanceVO.getContainerDetails(); 
    	if(containerDetailsVOs !=null && containerDetailsVOs.size()>0){
    		for(ContainerDetailsVO containerDetailsVO : containerDetailsVOs){
    			double manifestedWeight=0;
    			int manifestedBags=0; 
    	    	if(containerDetailsVO.getDsnVOs() !=null && containerDetailsVO.getDsnVOs().size()>0){    	
    	    		for(DSNVO dsnVO : containerDetailsVO.getDsnVOs()){
    	    			//manifestedWeight=manifestedWeight+dsnVO.getWeight();
    	    			manifestedWeight=manifestedWeight+dsnVO.getWeight().getRoundedSystemValue(); // Added by A-7550
    					manifestedBags=manifestedBags+dsnVO.getBags();
    	    			dsnVOs.add(dsnVO);
    	    		}
    	    	}
    	    	containerDetailsVO.setTotalBags(manifestedBags);
    			//containerDetailsVO.setTotalWeight(manifestedWeight);
    	    	//Added to avoid count and weight mismatch for bug ICRD-209095
    	    	containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, manifestedWeight)); // Added by A-7550
    		}    	  
    	}
    	log.log(Log.FINE, "*******MAIL ACCEPTANCE VOS******",
				newMailAcceptanceVO);
		mailExportListForm.setSelectDSN(null);
    	mailExportListSession.setMailAcceptanceVO(newMailAcceptanceVO);
    	 //Added by A-5160 for ICRD-92869 starts
    	HashMap<String, Collection<String>> polPouMap = new HashMap<String, Collection<String>>();
		if(newMailAcceptanceVO!=null){
			if(newMailAcceptanceVO.getContainerDetails()!=null && newMailAcceptanceVO.getContainerDetails().size()>0){
				for(ContainerDetailsVO containerDetailsVO : newMailAcceptanceVO.getContainerDetails()){					
					 Collection<String> pous = new ArrayList<String>();
					 if(containerDetailsVO.getPol()!=null && containerDetailsVO.getPou()!=null){
						if(polPouMap.containsKey(containerDetailsVO.getPol())){
							pous = polPouMap.get(containerDetailsVO.getPol());
							if(!pous.contains(containerDetailsVO.getPou())){
								pous.add(containerDetailsVO.getPou());
								polPouMap.put(containerDetailsVO.getPol(), pous);
							}
						}
						else{
							pous = new ArrayList<String>();
							pous.add(containerDetailsVO.getPou());
							polPouMap.put(containerDetailsVO.getPol(), pous);
						}
					 }
					 newMailAcceptanceVO.setPolPouMap(polPouMap);
				}
			}
		}
		 //Added by A-5160 for ICRD-92869 ends
    	mailExportListSession.setOperationalFlightVO(operationalFlightVO);
    	mailExportListSession.setContainerDetailsVOs(containerDetailsVOs);
    	mailExportListSession.setDSNVOs(dsnVOs);
		mailExportListForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET;
    	log.exiting("ListMailExportListCommand","execute");
    	
    }
    
	/**
	 * This method will be invoked at the time of screen load
	 * @param companyCode
	 * @return Map<String, Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeDescription(String companyCode) {
		Map<String, Collection<OneTimeVO>> oneTimes = null;
		Collection<ErrorVO> errors = null;
		try{
			Collection<String> fieldValues = new ArrayList<String>();

			fieldValues.add("flight.operation.flightlegstatus");
			fieldValues.add("mailtracking.defaults.flightstatus");
			
			oneTimes = new SharedDefaultsDelegate().findOneTimeValues(companyCode,fieldValues) ;

		}catch(BusinessDelegateException businessDelegateException){
			errors = handleDelegateException(businessDelegateException);
		}
		return oneTimes;
	}

	/**
	 * This method will the status description corresponding to the value from onetime
	 * @param oneTimeVOs
	 * @param status
	 * @return String
	 */
	private String findOneTimeDescription(Collection<OneTimeVO> oneTimeVOs, String status){
		if (oneTimeVOs != null) {
			for (OneTimeVO oneTimeVO:oneTimeVOs){
				if(status.equals(oneTimeVO.getFieldValue())){
					return oneTimeVO.getFieldDescription();
				}
			}
		}

		return null;
	}
       
}
