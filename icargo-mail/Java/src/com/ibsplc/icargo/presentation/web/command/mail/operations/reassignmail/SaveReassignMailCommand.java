/*
 * SaveReassignMailCommand.java Created on July 04, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.InventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInInventoryListVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.CarditEnquirySession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.InventoryListSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailAcceptanceSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1876
 *
 */
public class SaveReassignMailCommand extends BaseCommand {
	
   private Log log = LogFactory.getLogger("MAILTRACKING");
	
   /**
    * TARGET
    */
   private static final String TARGET = "save_success";
   private static final String MODULE_NAME = "mail.operations";	
   private static final String SCREEN_ID = "mailtracking.defaults.reassignmail";	
   private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";
   private static final String MAILACCEPTANCE_SCREENID = "mailtracking.defaults.mailacceptance";
   private static final String CARDIT_SCREEN_ID = "mailtracking.defaults.carditenquiry";
   private static final String SCREEN_ID_INV = 
			"mailtracking.defaults.inventorylist";	 
    
	 /**
	 * This method overrides the executre method of BaseComand class
	 * @param invocationContext
	 * @throws CommandInvocationException
	 */
    public void execute(InvocationContext invocationContext)
            throws CommandInvocationException {
    	
    	log.entering("SaveReassignMailCommand","execute");
    	  
    	ReassignMailForm reassignMailForm = 
    		(ReassignMailForm)invocationContext.screenModel;
    	ReassignMailSession reassignMailSession = 
    		getScreenSession(MODULE_NAME,SCREEN_ID);
    	EmptyULDsSession emptyUldsSession = 
			  getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);
    	CarditEnquirySession carditEnquirySession = 
    		getScreenSession(MODULE_NAME,CARDIT_SCREEN_ID);
    	MailAcceptanceSession mailAcceptanceSession = 
    		getScreenSession(MODULE_NAME,MAILACCEPTANCE_SCREENID);
    	Collection<ScannedMailDetailsVO> scannedMailDetailsVOS = null;
    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
		
    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
    	FlightValidationVO flightValidationVO = null;
    	String[] primaryKeys = reassignMailForm.getSelectContainer();
		String container = primaryKeys[0];
		log.log(Log.FINE, "selected container ===>", container);
		ContainerVO containerVO = ((ArrayList<ContainerVO>)(reassignMailSession.getContainerVOs())).get(Integer.parseInt(container));
		if("FLIGHT".equals(reassignMailForm.getAssignToFlight())){
			flightValidationVO = reassignMailSession.getFlightValidationVO();
			//A-5249 from ICRD-84046
			if((FlightValidationVO.FLT_LEG_STATUS_TBA.equals(flightValidationVO.getFlightStatus()) ||
                    FlightValidationVO.FLT_LEG_STATUS_TBC.equals(flightValidationVO.getFlightStatus())||
                    FlightValidationVO.FLT_STATUS_CANCELLED.equals(flightValidationVO.getFlightStatus()))){
				Object[] obj = {flightValidationVO.getCarrierCode().toUpperCase(),flightValidationVO.getFlightNumber()};
				ErrorVO err = new ErrorVO("mailtracking.defaults.reassigncontainer.err.flightintbcortba",obj);
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				invocationContext.addError(err);
				invocationContext.target = TARGET;							
				return;
			}
			if(flightValidationVO.getOperationalStatus()!=null && ("Closed").equals(flightValidationVO.getOperationalStatus())){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.err.flightclosed",new Object[]{flightValidationVO.getCarrierCode(),flightValidationVO.getFlightNumber(),flightValidationVO.getApplicableDateAtRequestedAirport().toDisplayDateOnlyFormat()}));
				
	 	   		invocationContext.target =TARGET; 
	 	   		return; 
			}
			if(flightValidationVO.getAtd() != null){
				containerVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
			}
	    }
    	
    	if(!"FLIGHT".equals(reassignMailForm.getHideRadio())){
	    	//if(reassignMailForm.getScanDate()==null && ("").equals(reassignMailForm.getMailScanTime())){
    		if(reassignMailForm.getScanDate()==null || ("").equals(reassignMailForm.getScanDate())){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
	 	   		invocationContext.target =TARGET; 
	 	   		return; 
			}
			if(reassignMailForm.getMailScanTime()==null ||("").equals(reassignMailForm.getMailScanTime())){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
	 	   		invocationContext.target =TARGET; 
	 	   		return; 
			}
			String scanDate= new StringBuilder().append(reassignMailForm.getScanDate()).append(" ").append(reassignMailForm.getMailScanTime()).append(":00").toString();
		    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
		    scanDat.setDateAndTime(scanDate);
		    containerVO.setOperationTime(scanDat);
    	}
		
		
		if("Y".equals(containerVO.getArrivedStatus())){
			invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
	   				new Object[]{containerVO.getContainerNumber()}));
			reassignMailForm.setCloseFlag("N");
	  		invocationContext.target = TARGET;
	  		return;
		}
		if(containerVO.getContainerNumber()!= null &&
				containerVO.getContainerNumber().trim().length() > 3){
			if("OFL".equals(containerVO.getContainerNumber().substring(0,3))){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.offloadedmails",
						new Object[]{containerVO.getContainerNumber()}));
				reassignMailForm.setCloseFlag("N");
				invocationContext.target = TARGET;
				return;
			}
		}
		
		if(containerVO.getContainerNumber()!= null &&
				containerVO.getContainerNumber().length() > 5){
			if("TRASH".equals(containerVO.getContainerNumber().substring(0,5))){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.trashmails",
		   				new Object[]{containerVO.getContainerNumber()}));
				reassignMailForm.setCloseFlag("N");
		  		invocationContext.target = TARGET;
		  		return;
			}
		}
		
		containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		containerVO.setMailSource(reassignMailForm.getNumericalScreenId());//Added for ICRD-156218
		log.log(Log.FINE, "FROM SCREEN ===>", reassignMailForm.getFromScreen());
		Collection<ContainerDetailsVO> containerDetailsVOs = null;
		if("MAILBAG_ENQUIRY".equals(reassignMailForm.getFromScreen())){
			
			Collection<MailbagVO> mailbagVOs = 
				                reassignMailSession.getMailbagVOs();
			String assignTo = reassignMailForm.getAssignToFlight();
			String mailbags = "";
			int errorFlag = 0;
			if (mailbagVOs != null && mailbagVOs.size() > 0) {
				for(MailbagVO mailbagVO:mailbagVOs){
					if("FLIGHT".equals(assignTo)){
						
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& containerVO.getFlightNumber().equals(mailbagVO.getFlightNumber())
							&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& containerVO.getContainerNumber().equals(mailbagVO.getContainerNumber())){
							    errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}else{
						if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
							&& containerVO.getContainerNumber().equals(mailbagVO.getContainerNumber())
							&& containerVO.getFinalDestination().equals(mailbagVO.getPou())){
								errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = mailbagVO.getMailbagId();
				       			}else{
				       				mailbags = new StringBuilder(mailbags)
				       				           .append(",")
				       				           .append(mailbagVO.getMailbagId())
				       				           .toString();
				       			}
						}
					}
				}
			}
			
		   if(errorFlag == 1){
	    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
	    	    	new Object[]{mailbags,containerVO.getContainerNumber()}));
	    	    reassignMailForm.setCloseFlag("N");
	    	    reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	    invocationContext.target = TARGET;
	    	    return;
	       }
		   
		   
		  
		   for(MailbagVO mailbagVO:mailbagVOs){
			   
			   if(!"FLIGHT".equals(reassignMailForm.getHideRadio())){
					String scanDate = new StringBuilder().append(reassignMailForm.getScanDate()).append(" ").append(reassignMailForm.getMailScanTime()).append(":00").toString();
				    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
				    scanDat.setDateAndTime(scanDate);
				    mailbagVO.setScannedDate(scanDat);
		       }else{
		    	   mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
		       }
			   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
			   mailbagVO.setScannedUser(logonAttributes.getUserId());
			   mailbagVO.setMailSource(reassignMailForm.getNumericalScreenId());//Added for ICRD-156218
			   //Added for ICRD-204654 starts
			   if(mailbagVO.getPou()!=null&&!mailbagVO.getPou().isEmpty()){
				   if(MailConstantsVO.CONST_BULK.equals(mailbagVO.getContainerType())){
				   StringBuilder sb = new StringBuilder(10);
			   mailbagVO.setUldNumber(sb.append(MailConstantsVO.CONST_BULK).
					   append(MailConstantsVO.SEPARATOR).
					   append(mailbagVO.getPou()).toString());
				   }
			   }
			 //Added for ICRD-204654 ends
		   }
		   
		   log.log(Log.FINE, "\n\n mailbagVOs for reassign ------->", mailbagVOs);
		log.log(Log.FINE, "\n\n mailbagVOs for reassign --------->",
				containerVO);
		try {
		    
			  
			  containerDetailsVOs = 
				  new MailTrackingDefaultsDelegate().reassignMailbags(mailbagVOs,containerVO);
			  
          }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
    	  if (errors != null && errors.size() > 0) {
    		invocationContext.addAllError(errors);
    		reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
    		reassignMailForm.setCloseFlag("N");
    		invocationContext.target = TARGET;
    		return;
    	  }
    	  log.log(Log.FINE, "containerDetailsVOs ----->>", containerDetailsVOs);
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
    		  emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
    		  reassignMailForm.setCloseFlag("SHOWPOPUP");
	    	  invocationContext.target = TARGET;
	    	  return;
	      }
		}
        if("INVENTORYLIST".equals(reassignMailForm.getFromScreen()) ||
        		"MOVEMAILINV".equals(reassignMailForm.getFromScreen())	){
			
        	InventoryListSession inventoryListSession = 
        		getScreenSession(MODULE_NAME,SCREEN_ID_INV);
        	InventoryListVO inventoryListVO=inventoryListSession.getInventoryListVO();
        	 log.log(Log.FINE, "\n\n inventoryListVO-->", inventoryListVO);
			Collection<ContainerInInventoryListVO> containerInInventoryList=inventoryListVO.getContainerInInventoryList();
        	
        	if("INVENTORYLIST".equals(reassignMailForm.getFromScreen())){
				Collection<MailInInventoryListVO> mailInInventoryListVOTmp=new ArrayList<MailInInventoryListVO>();
	        	 log.log(Log.FINE, "\n\n reassignMailForm.getContainer()-->",
						reassignMailForm.getContainer());
			String[] childStrs=reassignMailForm.getContainer().split(",");
			   int size=childStrs.length;
			   for(int i=0;i<size;i++){
				   ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt((childStrs[i].split("~"))[0]));
				   Collection<MailInInventoryListVO> mailInInventoryListVOs =contvo.getMailInInventoryList();
				   if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
 					   MailInInventoryListVO mailvo = ((ArrayList<MailInInventoryListVO>)mailInInventoryListVOs).get(Integer.parseInt((childStrs[i].split("~"))[1]));
					   mailvo.setCurrentAirport(logonAttributes.getAirportCode());
					   mailvo.setCarrierCode(reassignMailForm.getCarrierCodeInv());
					   mailvo.setCarrierID(reassignMailForm.getCarrierIdInv());
					   String scanDate= new StringBuilder().append(reassignMailForm.getScanDate()).append(" ").append(reassignMailForm.getMailScanTime()).append(":00").toString();
					   LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					   scanDat.setDateAndTime(scanDate);
					   mailvo.setOperationTime(scanDat);
					   mailInInventoryListVOTmp.add(mailvo);
				   }
			   }
			   log.log(Log.FINE,
					"\n\n mailInInventoryListVOTmp for reassign ------->",
					mailInInventoryListVOTmp);
		try {
			  new MailTrackingDefaultsDelegate().reassignMailbagsForInventory(mailInInventoryListVOTmp,containerVO);
		       }catch (BusinessDelegateException businessDelegateException) {
    			errors = handleDelegateException(businessDelegateException);
    	  }
		
        }else{
			
			Collection<ContainerInInventoryListVO> containerInInventoryListVOTmp=new ArrayList<ContainerInInventoryListVO>();
	      	 log.log(Log.FINE, "\n\n reassignMailForm.getContainer()-->",
					reassignMailForm.getContainer());
			if("C".equals(reassignMailForm.getSelectMode())){
		   	    	String[] containers = reassignMailForm.getContainer().split(",");
		   	    	int size = containers.length;
		   		    for(int i=0;i<size;i++){
		   		    	if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
		   		    		ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt(containers[i]));
		   					   Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
			   					   if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
				   					   for(MailInInventoryListVO mailvo:mailInInventoryListVOs){
				   						   mailvo.setCurrentAirport(logonAttributes.getAirportCode());					   
				   						   mailvo.setCompanyCode(logonAttributes.getCompanyCode());
										   mailvo.setCarrierCode(reassignMailForm.getCarrierCodeInv());
										   mailvo.setCarrierID(reassignMailForm.getCarrierIdInv());
				   					   }
			   					   }
			   					   
			   					if(contvo.getUldNumber().equals(containerVO.getContainerNumber())){
			   						invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.samecontainer",
			   				   				new Object[]{containerVO.getContainerNumber()}));
			   						reassignMailForm.setCloseFlag("N");
			   				  		invocationContext.target = TARGET;
			   				  		return;
			   					}
			   					   
								   contvo.setCurrentAirport(logonAttributes.getAirportCode());
								   contvo.setCarrierID(reassignMailForm.getCarrierIdInv());
								   containerInInventoryListVOTmp.add(contvo);
			   				   }
		   		   }
		   		    
		   		 log
						.log(
								Log.FINE,
								"\n\n containerInInventoryListVOTmp for reassign ------->",
								containerInInventoryListVOTmp);
				try {					    
					  new MailTrackingDefaultsDelegate().moveMailbagsForInventory(containerInInventoryListVOTmp,containerVO);
				  }catch (BusinessDelegateException businessDelegateException) {
		    			errors = handleDelegateException(businessDelegateException);
		    	  }
		   		    
		   	    }else{
		   	    	Collection<MailInInventoryListVO> mailInInventoryListVOTmp=new ArrayList<MailInInventoryListVO>();		   	    	
		   	    	String[] childStrs = reassignMailForm.getContainer().split(",");
		   	       int size=childStrs.length;
		 	       for(int i=0;i<size;i++){
		 		   String[] containers=childStrs[i].split("~");
		 		  if (containerInInventoryList != null && containerInInventoryList.size() > 0) {
		 			  ContainerInInventoryListVO contvo = ((ArrayList<ContainerInInventoryListVO>)containerInInventoryList).get(Integer.parseInt((childStrs[i].split("~"))[0]));
			 				   Collection<MailInInventoryListVO> mailInInventoryListVOs = contvo.getMailInInventoryList();
			 				  if (mailInInventoryListVOs != null && mailInInventoryListVOs.size() > 0) {
			 					 MailInInventoryListVO mailvo = ((ArrayList<MailInInventoryListVO>)mailInInventoryListVOs).get(Integer.parseInt((childStrs[i].split("~"))[1]));
		 						  if(mailvo.getUldNumber().equals(containerVO.getContainerNumber())){
				   						invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.samecontainer",
				   				   				new Object[]{containerVO.getContainerNumber()}));
				   						reassignMailForm.setCloseFlag("N");
				   				  		invocationContext.target = TARGET;
				   				  		return;
				   					}
		 						   
			 					   mailvo.setCurrentAirport(logonAttributes.getAirportCode());					   
			 					   mailvo.setCompanyCode(logonAttributes.getCompanyCode());
								   mailvo.setCarrierCode(reassignMailForm.getCarrierCodeInv());
								   mailvo.setCarrierID(reassignMailForm.getCarrierIdInv());
			 					   mailInInventoryListVOTmp.add(mailvo);
	 					   }
	 				   }				   
 			      }			   
		 	      log.log(Log.FINE,
						"\n\n mailInInventoryListVOTmp for reassign ------->",
						mailInInventoryListVOTmp);
				try {
					  new MailTrackingDefaultsDelegate().reassignMailbagsForInventory(mailInInventoryListVOTmp,containerVO);
				       }catch (BusinessDelegateException businessDelegateException) {
		    			errors = handleDelegateException(businessDelegateException);
		    	  }
		   	    }
		     }
        	}
        
		if("DSN_ENQUIRY".equals(reassignMailForm.getFromScreen())){
			
			Collection<DespatchDetailsVO> despatchDetailsVOs = 
				            reassignMailSession.getDespatchDetailsVOs();
			String assignTo = reassignMailForm.getAssignToFlight();
			if(!"true".equals(reassignMailSession.getReassignStatus())){
			String mailbags = "";
			int errorFlag = 0;
			if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
				for(DespatchDetailsVO despatchDetailsVO:despatchDetailsVOs){
					
					String pk = new StringBuilder(despatchDetailsVO.getOriginOfficeOfExchange())
						.append(despatchDetailsVO.getDestinationOfficeOfExchange())
						.append(despatchDetailsVO.getMailClass())
						.append(despatchDetailsVO.getYear())
						.append(despatchDetailsVO.getDsn()).toString();
					
					if("FLIGHT".equals(assignTo)){
						if(despatchDetailsVO.getFlightSequenceNumber() != 0){
						if(despatchDetailsVO.getCarrierId() == containerVO.getCarrierId()
							&& despatchDetailsVO.getFlightNumber().equals(containerVO.getFlightNumber())
							&& despatchDetailsVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
							&& despatchDetailsVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
							&& despatchDetailsVO.getContainerNumber().equals(containerVO.getContainerNumber())){
							    errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = pk;
				       			}else{
				       				mailbags = new StringBuilder(mailbags).append(",").append(pk).toString();
				       			}
						}
					  }
					}else{
						if(despatchDetailsVO.getCarrierId() == containerVO.getCarrierId()
							&& despatchDetailsVO.getContainerNumber().equals(containerVO.getContainerNumber())
							&& despatchDetailsVO.getDestination().equals(containerVO.getFinalDestination())){
								errorFlag = 1;
								if("".equals(mailbags)){
									mailbags = pk;
				       			}else{
				       				mailbags = new StringBuilder(mailbags).append(",").append(pk).toString();
				       			}
						}
					}
				}
			}
			
		   if(errorFlag == 1){
	    	    invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.reassignsamecontainer",
	    	    		new Object[]{mailbags,containerVO.getContainerNumber()}));
	    	    reassignMailForm.setCloseFlag("N");
	    	    reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    	    invocationContext.target = TARGET;
	    	    return;
	       }
			
		  try {
			  containerDetailsVOs = 
				  new MailTrackingDefaultsDelegate().reassignDSNs(reassignMailSession.getDespatchDetailsVOs(),containerVO);
			  
	      }catch (BusinessDelegateException businessDelegateException) {
	    		errors = handleDelegateException(businessDelegateException);
	      }
	      if (errors != null && errors.size() > 0) {
		    	invocationContext.addAllError(errors);
		    	reassignMailForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    	reassignMailForm.setCloseFlag("N");
		    	invocationContext.target = TARGET;
		    	return;
	      }
	      log.log(Log.FINE, "containerDetailsVOs ------------------>>",
				containerDetailsVOs);
		if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
		    	  emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
		    	  reassignMailForm.setCloseFlag("SHOWPOPUP");
		    	  invocationContext.target = TARGET;
		    	  return;
	      }
		}
    }
		if("CONSIGNMENT".equals(reassignMailForm.getFromScreen())){
			
			String selected = reassignMailForm.getContainer();
			String[] selArr = selected.split(",") ;
			log.log(Log.FINE, "#########selected", selected);
			MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
			Collection<MailbagVO> mailsFrmSession = carditEnquirySession.getMailbagVOsCollection();
			Collection<MailbagVO> mails = new ArrayList<MailbagVO>();
			Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
			Collection<ContainerDetailsVO> oldcontainers = new ArrayList<ContainerDetailsVO>();
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());	
			mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
			mailAcceptanceVO.setPreassignNeeded(true);
			
			containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
			containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
			containerDetailsVO.setContainerType(containerVO.getType());
			containerDetailsVO.setCarrierId(containerVO.getCarrierId());
			containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
			containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			containerDetailsVO.setPol(containerVO.getAssignedPort());
			containerDetailsVO.setPou(containerVO.getPou());
			containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
			containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
			containerDetailsVO.setOwnAirlineCode(containerVO.getOwnAirlineCode());
			containerDetailsVO.setWareHouse(containerVO.getWarehouseCode());
			oldcontainers.add(containerDetailsVO);
			if(!"Y".equals(containerVO.getAcceptanceFlag())){
				if("FLIGHT".equals(reassignMailForm.getAssignToFlight())){
					mailAcceptanceVO.setFlightCarrierCode(reassignMailForm.getFlightCarrierCode());
					mailAcceptanceVO.setFlightNumber(flightValidationVO.getFlightNumber());
					mailAcceptanceVO.setFlightDate(containerVO.getFlightDate());
					mailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					mailAcceptanceVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					mailAcceptanceVO.setDestination(containerVO.getPou());//todo
					mailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					containerDetailsVO.setFlightDate(containerVO.getFlightDate());
					containerDetailsVO.setOperationFlag("I");
					containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());				
				}
				else{	
					mailAcceptanceVO.setFlightCarrierCode(containerVO.getCarrierCode());
					mailAcceptanceVO.setCarrierId(containerVO.getCarrierId());
					mailAcceptanceVO.setFlightNumber(containerVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					mailAcceptanceVO.setLegSerialNumber(-1);
					containerDetailsVO.setDestination(containerVO.getFinalDestination());
					containerDetailsVO.setFlightDate(containerVO.getFlightDate());
					containerDetailsVO.setLegSerialNumber(-1);
					containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
					containerDetailsVO.setOperationFlag("I");
					containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
					containerDetailsVO.setCarrierId(containerVO.getCarrierId());
				}
				for(int i=0;i<selArr.length;i++){
					log.log(Log.FINE, "#########selArr[i]", selArr, i);
					MailbagVO mailbagVO = (MailbagVO) new ArrayList<MailbagVO>(mailsFrmSession).get(Integer.parseInt(selArr[i]));
					
				    
				    mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				    mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				    mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				    mailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				    mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    mailbagVO.setArrivedFlag("N");
				    mailbagVO.setDeliveredFlag("N");
				    mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				    mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				    mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
				    mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				    mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				    mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
				    mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				    mailbagVO.setContainerType(containerDetailsVO.getContainerType());
				    mailbagVO.setPou(containerDetailsVO.getPou());
				    mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				    mailbagVO.setOperationalFlag("I");
				    mailbagVO.setMailSource(reassignMailForm.getNumericalScreenId());//Added for ICRD-156218
					mails.add(mailbagVO);
				}
				containerDetailsVO.setMailDetails(mails);
				containers.add(containerDetailsVO);
				containers = makeDSNVOs(containers,logonAttributes);
				mailAcceptanceVO.setContainerDetails(containers);
			}
			else{
				Collection<ContainerDetailsVO> cntDets = new ArrayList<ContainerDetailsVO>();
				try{
					cntDets =new MailTrackingDefaultsDelegate().findMailbagsInContainer(oldcontainers);
				}
				catch (BusinessDelegateException businessDelegateException){
					errors = handleDelegateException(businessDelegateException);
				}
				
				containerDetailsVO = (ContainerDetailsVO) new ArrayList<ContainerDetailsVO>(cntDets).get(0);
				containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
				containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
				containerDetailsVO.setContainerType(containerVO.getType());
				containerDetailsVO.setCarrierId(containerVO.getCarrierId());
				containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
				containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
				containerDetailsVO.setPol(containerVO.getAssignedPort());
				containerDetailsVO.setPou(containerVO.getPou());
				containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
				containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
				containerDetailsVO.setOwnAirlineCode(containerVO.getOwnAirlineCode());
				containerDetailsVO.setWareHouse(containerVO.getWarehouseCode());
				containerDetailsVO.setOperationFlag("U");
				Collection<MailbagVO> mailVOs = containerDetailsVO.getMailDetails();
				if(mailVOs !=null && mailVOs.size()>0){
					for(MailbagVO mailbag : mailVOs){
						mailbag.setOperationalFlag("U");
					}
				}
				if("FLIGHT".equals(reassignMailForm.getAssignToFlight())){
					
					
					mailAcceptanceVO.setFlightCarrierCode(reassignMailForm.getFlightCarrierCode());
					mailAcceptanceVO.setFlightNumber(flightValidationVO.getFlightNumber());
					mailAcceptanceVO.setFlightDate(containerVO.getFlightDate());
					mailAcceptanceVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
					mailAcceptanceVO.setCarrierId(flightValidationVO.getFlightCarrierId());
					mailAcceptanceVO.setDestination(containerVO.getPou());//todo
					mailAcceptanceVO.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
					containerDetailsVO.setFlightDate(containerVO.getFlightDate());					
					containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());				
				}
				else{				
					containerDetailsVO.setDestination(containerVO.getFinalDestination());
					containerDetailsVO.setFlightDate(containerVO.getFlightDate());
					containerDetailsVO.setLegSerialNumber(-1);
					containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
					containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
					containerDetailsVO.setCarrierId(containerVO.getCarrierId());
					mailAcceptanceVO.setFlightCarrierCode(containerVO.getCarrierCode());
					mailAcceptanceVO.setCarrierId(containerVO.getCarrierId());
					mailAcceptanceVO.setFlightNumber(containerVO.getFlightNumber());
					mailAcceptanceVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
					mailAcceptanceVO.setLegSerialNumber(-1);
				}
				
				cntDets.add(containerDetailsVO);
				makeServerDSNVOs(cntDets,logonAttributes);
				
				for(int i=0;i<selArr.length;i++){
					log.log(Log.FINE, "#########selArr[i]", selArr, i);
					MailbagVO mailbagVO = (MailbagVO) new ArrayList<MailbagVO>(mailsFrmSession).get(Integer.parseInt(selArr[i]));					
				    mailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
				    mailbagVO.setContainerNumber(containerDetailsVO.getContainerNumber());
				    mailbagVO.setCarrierCode(mailAcceptanceVO.getFlightCarrierCode());
				    mailbagVO.setFlightDate(mailAcceptanceVO.getFlightDate());
				    mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				    mailbagVO.setArrivedFlag("N");
				    mailbagVO.setDeliveredFlag("N");
				    mailbagVO.setScannedUser(logonAttributes.getUserId().toUpperCase());
				    mailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
				    mailbagVO.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
				    mailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
				    mailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
				    mailbagVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
				    mailbagVO.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
				    mailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
				    mailbagVO.setContainerType(containerDetailsVO.getContainerType());
				    mailbagVO.setPou(containerDetailsVO.getPou());	
				    mailbagVO.setOperationalFlag("I");
				    mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					mails.add(mailbagVO);
				}
				mails.addAll(mailVOs);
				ContainerDetailsVO contVO = (ContainerDetailsVO) new ArrayList<ContainerDetailsVO>(cntDets).get(0);
				contVO.setMailDetails(mails);
				containers.add(contVO);				
				log.log(Log.FINE, "#########containers", containers);
				makeDSNVOs(containers,logonAttributes);
				mailAcceptanceVO.setContainerDetails(containers);
				
			}		
			
			
			log.log(Log.FINE, "Going To Save ...in command----",
					mailAcceptanceVO);
			try {
				  scannedMailDetailsVOS=new MailTrackingDefaultsDelegate().saveAcceptanceDetails(mailAcceptanceVO);
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	    	  if(scannedMailDetailsVOS!=null && scannedMailDetailsVOS.size()>0){
	      		
	      		ScannedMailDetailsVO scannedMailDetailsVO =
	      			((ArrayList<ScannedMailDetailsVO>)scannedMailDetailsVOS).get(0);
	      		if( scannedMailDetailsVO.getExistingMailbagVOS()!=null 
	      				&&  (scannedMailDetailsVO.getExistingMailbagVOS().size())>0){
	      			
	  	    		Collection<ExistingMailbagVO> existingMailbagVOS =  scannedMailDetailsVO.getExistingMailbagVOS();
	  	    		reassignMailForm.setExistingMailbagFlag("Y");
	  	    		
	  	        	mailAcceptanceSession.setExistingMailbagVO(existingMailbagVOS);
	  	        	mailAcceptanceSession.setMailAcceptanceVO(mailAcceptanceVO);
	  	        	invocationContext.target = TARGET;
	  	        	return;
	      		}
	      		
	      	
	      	
	  		}
		}
		
		
		
		reassignMailForm.setCloseFlag("Y");
		invocationContext.target = TARGET;
    	log.exiting("SaveReassignMailCommand","execute");
    	
    }
    /**
      * Mehtod to make serverDSNVOs 
	 * @param newContainerDetailsVOs
	 * @param logonAttributes
	 * @return Collection<ContainerDetailsVO>
	 */
    private void makeServerDSNVOs(Collection<ContainerDetailsVO> newContainerDetailsVOs,
    		LogonAttributes logonAttributes){

    	if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
				HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
				 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
					for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
						int numBags = 0;
						double bagWgt = 0;
						int stdNumBags = 0;
						double stdBagWgt = 0;
						String outerpk = despatchVO.getOriginOfficeOfExchange()
						           +despatchVO.getDestinationOfficeOfExchange()
						           //+despatchVO.getMailClass()
						           //added by anitha for change in pk
						           +despatchVO.getMailCategoryCode()
						           +despatchVO.getMailSubclass()
						           +despatchVO.getDsn()
						           +despatchVO.getYear();
						if(dsnMap.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
//							added by anitha for change in pk
							dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag("N");
							dsnVO.setPou(popupVO.getPou());
						for(DespatchDetailsVO innerVO:despatchDetailsVOs){
							String innerpk = innerVO.getOriginOfficeOfExchange()
					           +innerVO.getDestinationOfficeOfExchange()
					           //+innerVO.getMailClass()
					           //added by anitha for change in pk
					           +innerVO.getMailCategoryCode()
					           +innerVO.getMailSubclass()
					           +innerVO.getDsn()
					           +innerVO.getYear();
							if(outerpk.equals(innerpk)){
								numBags = numBags + innerVO.getAcceptedBags();
								//bagWgt = bagWgt + innerVO.getAcceptedWeight();
								bagWgt = bagWgt + innerVO.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
								stdNumBags = stdNumBags + innerVO.getStatedBags();
								//stdBagWgt = stdBagWgt + innerVO.getStatedWeight();
								stdBagWgt = stdBagWgt + innerVO.getStatedWeight().getRoundedSystemValue();//added by A-7371
							}
						}
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(bagWgt);
						dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnVO.setStatedBags(stdNumBags);
						//dsnVO.setStatedWeight(stdBagWgt);
						dsnVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,stdBagWgt));//added by A-7371
						dsnVO.setPrevBagCount(numBags);
						//dsnVO.setPrevBagWeight(bagWgt);
						dsnVO.setPrevBagWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnVO.setPrevStatedBags(stdNumBags);
						//dsnVO.setPrevStatedWeight(stdBagWgt);
						dsnVO.setPrevStatedWeight(new Measure(UnitConstants.MAIL_WGT,stdBagWgt));//added by A-7371
						dsnMap.put(outerpk,dsnVO);
						numBags = 0;
						bagWgt = 0;
						stdNumBags = 0;
						stdBagWgt = 0;
						}
					}
				}

				Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){
						int numBags = 0;
						double bagWgt = 0;
						String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
						           +(mailbagVO.getMailSubclass())
					           +mailbagVO.getMailCategoryCode()
						           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
						if(dsnMap.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag("Y");
							dsnVO.setPou(popupVO.getPou());
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
					           +(innerVO.getMailSubclass())
					           +innerVO.getMailCategoryCode()
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								log.log(Log.FINE, "TIGHT");
								numBags = numBags + 1;
								//bagWgt = bagWgt + innerVO.getWeight();
								bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
							}
						}
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(bagWgt);
						dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnVO.setPrevBagCount(numBags);
						//dsnVO.setPrevBagWeight(bagWgt);
						dsnVO.setPrevBagWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnMap.put(outerpk,dsnVO);
						numBags = 0;
						bagWgt = 0;
						}
					}
				}
				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMap.keySet()){
					DSNVO dsnVO = dsnMap.get(key);
					newDSNVOs.add(dsnVO);
				}
				popupVO.setDsnVOs(newDSNVOs);
			}
    	}    	
    }
    /**
	 * Mehtod to update DSN Summary VOs
	 * @param newContainerDetailsVOs
	 * @param logonAttributes
	 * @return Collection<ContainerDetailsVO>
	 */
    public Collection<ContainerDetailsVO>  makeDSNVOs(Collection<ContainerDetailsVO> newContainerDetailsVOs,
    		LogonAttributes logonAttributes){

    	if(newContainerDetailsVOs != null && newContainerDetailsVOs.size() > 0){
			for(ContainerDetailsVO popupVO:newContainerDetailsVOs){
				HashMap<String,DSNVO> dsnMap = new HashMap<String,DSNVO>();
				Collection<DespatchDetailsVO> despatchDetailsVOs = popupVO.getDesptachDetailsVOs();
				 if(despatchDetailsVOs != null && despatchDetailsVOs.size() > 0){
					for(DespatchDetailsVO despatchVO:despatchDetailsVOs){
						int numBags = 0;
						double bagWgt = 0;
						int stdNumBags = 0;
						double stdBagWgt = 0;
						String outerpk = despatchVO.getOriginOfficeOfExchange()
						           +despatchVO.getDestinationOfficeOfExchange()
						           //+despatchVO.getMailClass()
						           //added by anitha for change in pk
						           +despatchVO.getMailCategoryCode()
						           +despatchVO.getMailSubclass()
						           +despatchVO.getDsn()
						           +despatchVO.getYear();
						if(dsnMap.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(despatchVO.getDsn());
							dsnVO.setOriginExchangeOffice(despatchVO.getOriginOfficeOfExchange());
							dsnVO.setDestinationExchangeOffice(despatchVO.getDestinationOfficeOfExchange());
							dsnVO.setMailClass(despatchVO.getMailClass());
//							added by anitha for change in pk
							dsnVO.setMailCategoryCode(despatchVO.getMailCategoryCode());
							dsnVO.setMailSubclass(despatchVO.getMailSubclass());
							dsnVO.setYear(despatchVO.getYear());
							dsnVO.setPltEnableFlag("N");
							dsnVO.setPou(popupVO.getPou());
						for(DespatchDetailsVO innerVO:despatchDetailsVOs){
							String innerpk = innerVO.getOriginOfficeOfExchange()
					           +innerVO.getDestinationOfficeOfExchange()
					           //+innerVO.getMailClass()
					           //added by anitha for change in pk
					           +innerVO.getMailCategoryCode()
					           +innerVO.getMailSubclass()
					           +innerVO.getDsn()
					           +innerVO.getYear();
							if(outerpk.equals(innerpk)){
								numBags = numBags + innerVO.getAcceptedBags();
								//bagWgt = bagWgt + innerVO.getAcceptedWeight();
								bagWgt = bagWgt + innerVO.getAcceptedWeight().getRoundedSystemValue();//added by A-7371
								stdNumBags = stdNumBags + innerVO.getStatedBags();
								//stdBagWgt = stdBagWgt + innerVO.getStatedWeight();
								stdBagWgt = stdBagWgt + innerVO.getStatedWeight().getRoundedSystemValue();//added by A-7371
							}
						}
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(bagWgt);
						dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnVO.setStatedBags(stdNumBags);
						//dsnVO.setStatedWeight(stdBagWgt);
						dsnVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT,stdBagWgt));//added by A-7371
						dsnMap.put(outerpk,dsnVO);
						numBags = 0;
						bagWgt = 0;
						stdNumBags = 0;
						stdBagWgt = 0;
						}
					}
				}

				Collection<MailbagVO> mailbagVOs = popupVO.getMailDetails();
				 if(mailbagVOs != null && mailbagVOs.size() > 0){
					for(MailbagVO mailbagVO:mailbagVOs){
						int numBags = 0;
						double bagWgt = 0;
						String outerpk = mailbagVO.getOoe()+mailbagVO.getDoe()
						           +(mailbagVO.getMailSubclass())
					           +mailbagVO.getMailCategoryCode()
						           +mailbagVO.getDespatchSerialNumber()+mailbagVO.getYear();
						if(dsnMap.get(outerpk) == null){
							DSNVO dsnVO = new DSNVO();
							dsnVO.setCompanyCode(logonAttributes.getCompanyCode());
							dsnVO.setDsn(mailbagVO.getDespatchSerialNumber());
							dsnVO.setOriginExchangeOffice(mailbagVO.getOoe());
							dsnVO.setDestinationExchangeOffice(mailbagVO.getDoe());
							dsnVO.setMailClass(mailbagVO.getMailSubclass().substring(0,1));
							dsnVO.setMailSubclass(mailbagVO.getMailSubclass());
							dsnVO.setMailCategoryCode(mailbagVO.getMailCategoryCode());
							dsnVO.setYear(mailbagVO.getYear());
							dsnVO.setPltEnableFlag("Y");
							dsnVO.setPou(popupVO.getPou());
						for(MailbagVO innerVO:mailbagVOs){
							String innerpk = innerVO.getOoe()+innerVO.getDoe()
					           +(innerVO.getMailSubclass())
					           +innerVO.getMailCategoryCode()
					           +innerVO.getDespatchSerialNumber()+innerVO.getYear();
							if(outerpk.equals(innerpk)){
								numBags = numBags + 1;
								//bagWgt = bagWgt + innerVO.getWeight();
								bagWgt = bagWgt + innerVO.getWeight().getRoundedSystemValue();//added by A-7371
							}
						}
						dsnVO.setBags(numBags);
						//dsnVO.setWeight(bagWgt);
						dsnVO.setWeight(new Measure(UnitConstants.MAIL_WGT,bagWgt));//added by A-7371
						dsnMap.put(outerpk,dsnVO);
						numBags = 0;
						bagWgt = 0;
						}
					}
				}

				Collection<DSNVO> newDSNVOs = new ArrayList<DSNVO>();
				for(String key:dsnMap.keySet()){
					DSNVO dsnVO = dsnMap.get(key);
					newDSNVOs.add(dsnVO);
				}
				Collection<DSNVO> oldDSNVOs = popupVO.getDsnVOs();
				int numBags = 0;
				double wgtBags = 0;
				if(newDSNVOs != null && newDSNVOs.size() > 0){
					for(DSNVO newDSNV:newDSNVOs){
						String outerpk = newDSNV.getOriginExchangeOffice()
						   +newDSNV.getDestinationExchangeOffice()
						   +newDSNV.getMailCategoryCode()
				           +newDSNV.getMailSubclass()
				           +newDSNV.getDsn()+newDSNV.getYear();
						int flag = 0;
						if(oldDSNVOs != null && oldDSNVOs.size() > 0){
							for(DSNVO oldDSN:oldDSNVOs){
								String innerpk = oldDSN.getOriginExchangeOffice()
								   +oldDSN.getDestinationExchangeOffice()
								   +oldDSN.getMailCategoryCode()
						           +oldDSN.getMailSubclass()
						           +oldDSN.getDsn()+oldDSN.getYear();
								if(outerpk.equals(innerpk)){
									if(!"I".equals(oldDSN.getOperationFlag())){
										newDSNV.setOperationFlag("U");
										newDSNV.setPrevBagCount(oldDSN.getPrevBagCount());
										newDSNV.setPrevBagWeight(oldDSN.getPrevBagWeight());
										newDSNV.setPrevStatedBags(oldDSN.getPrevStatedBags());
                                        newDSNV.setPrevStatedWeight(oldDSN.getPrevStatedWeight());
									}
									flag = 1;
								}
							}
						}
						if(flag == 1){
							if(!"U".equals(newDSNV.getOperationFlag())){
								newDSNV.setOperationFlag("I");
							}
							flag = 0;
						}else{
							newDSNV.setOperationFlag("I");
						}
						numBags = numBags + newDSNV.getBags();
						//wgtBags = wgtBags + newDSNV.getWeight();
						wgtBags = wgtBags + newDSNV.getWeight().getRoundedSystemValue();//added by A-7371
					}
				}
				popupVO.setTotalBags(numBags);
				//popupVO.setTotalWeight(wgtBags);
				popupVO.setTotalWeight(new Measure(UnitConstants.MAIL_WGT,wgtBags));//added by A-7371
				popupVO.setDsnVOs(newDSNVOs);
			}
    	 }

    	return newContainerDetailsVOs;
    }

       
}
