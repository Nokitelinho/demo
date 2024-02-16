/*
 * ListMailManifestCommand.java Created on Jul 1 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.manifest;

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
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestVO;
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
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailManifestSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailManifestForm;
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
public class ListMailManifestCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILOPERATIONS");
	
   /**
    * TARGET
    */
   private static final String TARGET = "list_success";
   private static final String TARGET_SUCCESS = "attach_success";
   
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.mailmanifest";	
   /**
    * Screen id
    */
   private static final String SCREEN_ID_DUPFLIGHT = "flight.operation.duplicateflight";
   /**
    * Module name
    */
   private static final String MODULE_NAME_FLIGHT =  "flight.operation";
   
   private static final String OUTBOUND = "O";
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("ListMailManifestCommand","execute");
    	  
    	MailManifestForm mailManifestForm = 
    		(MailManifestForm)invocationContext.screenModel;
    	MailManifestSession mailManifestSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
		DuplicateFlightSession duplicateFlightSession = getScreenSession(
				MODULE_NAME_FLIGHT, SCREEN_ID_DUPFLIGHT);
		mailManifestForm.setOperationalStatus("");		
    	ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		
		MailManifestVO mailManifestVO = mailManifestSession.getMailManifestVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO(); 
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		//Modified by A-7794 as part of ICRD-197439
		operationalFlightVO.setPol(mailManifestForm.getDeparturePort());
		
		MailManifestVO newMailManifestVO = new MailManifestVO();
		FlightValidationVO flightValidationVO = new FlightValidationVO();
		
		//String fromscreen = mailAcceptanceForm.getFromScreen();		
		if(TARGET_SUCCESS.equals(mailManifestForm.getAutoAttachAWB())){
			log.log(Log.INFO, "AutoAttaching AWB Done successfully");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.manifest.autoattachawb.success"));
		}
		if(FLAG_YES.equals(mailManifestForm.getDuplicateFlightStatus())){
			mailManifestSession.setFlightValidationVO(duplicateFlightSession.getFlightValidationVO());
			mailManifestForm.setDuplicateFlightStatus(FLAG_NO);
			//A-5249 from ICRD-84046				
			FlightValidationVO flightValidVO = duplicateFlightSession.getFlightValidationVO();
			if(FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidVO.getFlightStatus()) ||
					FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidVO.getFlightStatus())){
				if(!FlightValidationVO.FLAG_YES.equals(mailManifestForm.getWarningOveride())){
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
				mailManifestForm.setWarningFlag("list_flight_tba_tbc");
				return;
				}else{
					mailManifestForm.setWarningFlag("");
					mailManifestForm.setWarningOveride(null);
					flightValidVO.setFlightRoute(null);
						flightValidVO.setAircraftType(null);
						flightValidVO.setEta(null);
						flightValidVO.setEtd(null);
						flightValidVO.setSta(null);
						flightValidVO.setStd(null);
						flightValidVO.setAta(null);
						flightValidVO.setAtd(null);
				}
				mailManifestForm.setDisableButtonsForTBA(FlightValidationVO.FLAG_YES);
			}else if(FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidVO.getFlightStatus())){
            	Object[] obj = {flightValidVO.getCarrierCode().toUpperCase(),flightValidVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.consignment.err.flightcancelled",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = TARGET;
				return;
			} 
			//added for icrd-98835 by a-4810
			flightValidVO.setDirection("O");
			
		}
		log.log(Log.FINE, "flightValidationVO in Manifest session...",
				mailManifestSession.getFlightValidationVO());
		flightValidationVO = mailManifestSession.getFlightValidationVO();
		Map<String, Collection<OneTimeVO>> oneTimes = findOneTimeDescription(logonAttributes.getCompanyCode());
		if(oneTimes!=null){
			Collection<OneTimeVO> resultStatus =
				oneTimes.get("flight.operation.flightlegstatus");
			log.log(Log.FINE, "*******flightlegstatus******");
			
			flightValidationVO.setStatusDescription(findOneTimeDescription(resultStatus,flightValidationVO.getLegStatus()));
		}
		flightValidationVO.setDirection("O");
		mailManifestSession.setFlightValidationVO(flightValidationVO);
		
		operationalFlightVO.setFlightNumber(mailManifestVO.getFlightNumber());
		operationalFlightVO.setCarrierCode(mailManifestVO.getFlightCarrierCode());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightDate(mailManifestVO.getDepDate());
		operationalFlightVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setFlightRoute(flightValidationVO.getFlightRoute());
		operationalFlightVO.setDirection(OUTBOUND);
		
		log.log(Log.FINE, "operationalFlightVO in Manifest session...",
				operationalFlightVO);
		try {
		    	newMailManifestVO = new MailTrackingDefaultsDelegate().findContainersInFlightForManifest(operationalFlightVO);
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		mailManifestSession.setMailManifestVO(mailManifestVO);
    		invocationContext.target = TARGET;
    		return;
    	  }else{
    		  FlightValidationVO fltVal=mailManifestSession.getFlightValidationVO();
    		  log.log(Log.FINE, "*******newMailManifestVO******",
					newMailManifestVO);
				log.log(Log.FINE, "*******fltVal******", fltVal);
				if(fltVal!=null){
    	    		if(newMailManifestVO.getFlightStatus()==null || "".equals(newMailManifestVO.getFlightStatus())){
    					mailManifestForm.setOperationalStatus("NONE");	    					
    				}else{
    				if(oneTimes!=null){
    					Collection<OneTimeVO> resultStatus=
    						oneTimes.get("mailtracking.defaults.flightstatus");
    					log.log(Log.FINE, "*******flightlegstatus******");
    					fltVal.setOperationalStatus(findOneTimeDescription(resultStatus,newMailManifestVO.getFlightStatus()));
    				}
    				
    				if("O".equals(newMailManifestVO.getFlightStatus())){
    					mailManifestForm.setOperationalStatus("OPEN");	  	    					
    				}else
    				if("C".equals(newMailManifestVO.getFlightStatus())){
    					mailManifestForm.setOperationalStatus("CLOSED");	  	    					
    				}}
    	    		
    	    		mailManifestSession.setFlightValidationVO(fltVal);
    	    	}
    	  }
			
		
		 
    	  if(newMailManifestVO == null){
    		  newMailManifestVO = new MailManifestVO();
    	  }
    	  newMailManifestVO.setCompanyCode(logonAttributes.getCompanyCode());
    	//Modified by A-7794 as part of ICRD-197439
    	  newMailManifestVO.setDepPort(mailManifestForm.getDeparturePort());
    	  newMailManifestVO.setFlightCarrierCode(mailManifestVO.getFlightCarrierCode());
		  newMailManifestVO.setFlightNumber(mailManifestVO.getFlightNumber());
		  newMailManifestVO.setDepDate(mailManifestVO.getDepDate());
		  newMailManifestVO.setCarrierId(mailManifestVO.getCarrierId());
		  newMailManifestVO.setStrDepDate(mailManifestVO.getStrDepDate());
		//Added by A-7794 as part of ICRD-197439
		  if(!Objects.equals(logonAttributes.getAirportCode(), mailManifestForm.getDeparturePort())){
			   mailManifestForm.setDisableButtonsForAirport(FLAG_YES);
			    }   else {
				mailManifestForm.setDisableButtonsForAirport(FLAG_NO);
				}
    	
    	Collection<ContainerDetailsVO> containerDetails = newMailManifestVO.getContainerDetails();  
    	if(containerDetails == null ||containerDetails.size() == 0){
    		invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.nocontainerdetails"));
    		mailManifestForm.setDisableSaveFlag(FLAG_YES);
    	}
    	if("Y".equals(mailManifestForm.getUldsPopupCloseFlag())||
        		"Y".equals(mailManifestSession.getMessageStatus())	){
    		mailManifestForm.setUldsPopupCloseFlag("");
    		mailManifestSession.setMessageStatus("");
	    	Object[] obj = {newMailManifestVO.getFlightCarrierCode(),
	    			newMailManifestVO.getFlightNumber(),newMailManifestVO.getStrDepDate()};
			ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailacceptance.flightclosed",obj);
			invocationContext.addError(errorVO);
    	}
    	if("REOPENED".equals(mailManifestSession.getMessageStatus())	){ 
    		mailManifestSession.setMessageStatus("");
    		Object[] obj = {mailManifestVO.getFlightCarrierCode(),
					mailManifestVO.getFlightNumber(),
					mailManifestVO.getStrDepDate()};
	       invocationContext.addError(new ErrorVO("mailtracking.defaults.mailacceptance.flightreopened",obj));
    	}
    	log.log(Log.FINE, "*******MAIL MANIFEST VOS******", newMailManifestVO);
		mailManifestSession.setMailManifestVO(newMailManifestVO);
		 //Added by A-5160 for ICRD-92869 starts
		HashMap<String, Collection<String>> polPouMap = new HashMap<String, Collection<String>>();
		if(newMailManifestVO!=null){
			if(newMailManifestVO.getContainerDetails()!=null && newMailManifestVO.getContainerDetails().size()>0){
				for(ContainerDetailsVO containerDetailsVO : newMailManifestVO.getContainerDetails()){					
					
					//Added as part of Bug ICRD-160911 by A-5526 starts.
					double manifestedWeight=0;
					 int manifestedBags=0;
					
					
					Collection<DSNVO> dSNVOs =  new ArrayList<DSNVO>();     
					dSNVOs = containerDetailsVO.getDsnVOs();
					if( dSNVOs != null && dSNVOs.size() >0){
						for (DSNVO  dsnvo : dSNVOs){
						
							//manifestedWeight=manifestedWeight+dsnvo.getWeight();
							manifestedWeight=manifestedWeight+dsnvo.getWeight().getRoundedSystemValue(); //Added by A-7550
							manifestedBags=manifestedBags+dsnvo.getBags();
						
						}
				} 
					containerDetailsVO.setTotalBags(manifestedBags);
					//containerDetailsVO.setTotalWeight(manifestedWeight);
					containerDetailsVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT, manifestedWeight)); //Added by A-7550
					
					//Added as part of Bug ICRD-160911 by A-5526 ends.
					
					
					
					
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
					 newMailManifestVO.setPolPouMap(polPouMap);
				}
			}
		}
		 //Added by A-5160 for ICRD-92869 ends
    	mailManifestSession.setOperationalFlightVO(operationalFlightVO);
		mailManifestForm.setSelectDSN(null);
		mailManifestForm.setSelectMail(null);
		mailManifestForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    	invocationContext.target = TARGET;
    	log.exiting("ListMailManifestCommand","execute");
    	
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
