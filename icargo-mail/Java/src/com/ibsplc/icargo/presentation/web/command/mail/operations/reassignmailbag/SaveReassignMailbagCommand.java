/*
 * SaveReassignMailbagCommand.java 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 * A-3251 SREEJITH P.C.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.reassignmailbag;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.EmptyULDsSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.ReassignMailbagSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.ReassignMailbagForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-3251
 *
 */
public class SaveReassignMailbagCommand extends BaseCommand {
	
	   private Log log = LogFactory.getLogger("MAILTRACKING");
		
	   /**
	    * TARGET
	    */
	   private static final String TARGET = "save_success";
	   private static final String MODULE_NAME = "mail.operations";	
	   private static final String SCREEN_ID = "mailtracking.defaults.reassignmailbag";	
	   private static final String EMPTYULD_SCREEN_ID = "mailtracking.defaults.emptyulds";
	   private static final String CARDIT_SCREEN_ID = "mailtracking.defaults.carditenquiry";
	   private static final String SCREEN_NUMERICAL_ID = "MTK026";
	  	 
	    
		 /**
		 * This method overrides the executre method of BaseComand class
		 * @param invocationContext
		 * @throws CommandInvocationException
		 */
	    public void execute(InvocationContext invocationContext)
	            throws CommandInvocationException {
	    	
	    	log.entering("SaveReassignMailbagCommand","execute");
	    	  
	    	ReassignMailbagForm reassignMailbagForm = 
	    		(ReassignMailbagForm)invocationContext.screenModel;
	    	ReassignMailbagSession reassignMailbagSession = 
	    		getScreenSession(MODULE_NAME,SCREEN_ID);
	    	EmptyULDsSession emptyUldsSession = 
				  getScreenSession(MODULE_NAME,EMPTYULD_SCREEN_ID);
	    	//CarditEnquirySession carditEnquirySession = 
	    		getScreenSession(MODULE_NAME,CARDIT_SCREEN_ID);
	    	
	    	LogonAttributes logonAttributes = getApplicationSession().getLogonVO();
			
	    	Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
	    	FlightValidationVO flightValidationVO = null;
	    	String[] primaryKeys = reassignMailbagForm.getSelectContainer();
			String container = primaryKeys[0];
			log.log(Log.FINE, "selected container ===>", container);
			ContainerVO containerVO = ((ArrayList<ContainerVO>)(reassignMailbagSession.getContainerVOs())).get(Integer.parseInt(container));
			
			if("FLIGHT".equals(reassignMailbagForm.getAssignToFlight())){
				flightValidationVO = reassignMailbagSession.getFlightValidationVO();
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
				if(flightValidationVO.getAtd() != null){
					containerVO.setFlightStatus(MailConstantsVO.FLIGHT_STATUS_DEPARTED);
				}
		    }
	    	
	    	if(!"FLIGHT".equals(reassignMailbagForm.getHideRadio())){
		    	if(reassignMailbagForm.getScanDate()==null || ("").equals(reassignMailbagForm.getScanDate().trim())){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanDate"));
		 	   		invocationContext.target =TARGET; 
		 	   		return; 
				}
				if(reassignMailbagForm.getMailScanTime()==null ||("").equals(reassignMailbagForm.getMailScanTime())){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.emptyScanTime"));
		 	   		invocationContext.target =TARGET; 
		 	   		return; 
				}
				String scanDate= new StringBuilder().append(reassignMailbagForm.getScanDate()).append(" ").append(reassignMailbagForm.getMailScanTime()).append(":00").toString();
			    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			    scanDat.setDateAndTime(scanDate);
			    containerVO.setOperationTime(scanDat);
	    	}			
			
			if("Y".equals(containerVO.getArrivedStatus())){
				invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.containerarrived",
		   				new Object[]{containerVO.getContainerNumber()}));
				reassignMailbagForm.setCloseFlag("N");
		  		invocationContext.target = TARGET;
		  		return;
			}

			if(containerVO.getContainerNumber()!= null &&
					containerVO.getContainerNumber().trim().length() > 3){
				if("OFL".equals(containerVO.getContainerNumber().substring(0,3))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.offloadedmails",
							new Object[]{containerVO.getContainerNumber()}));
					reassignMailbagForm.setCloseFlag("N");
					invocationContext.target = TARGET;
					return;
				}
			}
			
			if(containerVO.getContainerNumber()!= null &&
					containerVO.getContainerNumber().length() > 5){
				if("TRASH".equals(containerVO.getContainerNumber().substring(0,5))){
					invocationContext.addError(new ErrorVO("mailtracking.defaults.reassignmail.trashmails",
			   				new Object[]{containerVO.getContainerNumber()}));
					reassignMailbagForm.setCloseFlag("N");
			  		invocationContext.target = TARGET;
			  		return;
				}
			}
			
			containerVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
			containerVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
			containerVO.setMailSource(SCREEN_NUMERICAL_ID);
			Collection<ContainerDetailsVO> containerDetailsVOs = null;
			
				Collection<MailbagVO> mailbagVOs  = reassignMailbagSession.getMailbagVOs();
				Collection<MailbagVO> selmailbagVOs = new ArrayList<MailbagVO>();					
				
			
				String selmail []=reassignMailbagForm.getSelmailbags().split("-");
				int i=0;
				
				for(MailbagVO mailbagVO: mailbagVOs)
				{
					for(int d=0;d<selmail.length;d++)
					{
						log.log(Log.FINE, "Selected Mailbags ", selmail, d);
						if(i==Integer.parseInt(selmail[d]))
						{
						selmailbagVOs.add(mailbagVO);
					}
					
					}
					i++;
				}
				
				    log.log(Log.FINE, "Full Mailbags", mailbagVOs);
					log.log(Log.FINE, "Selected Mailbags ", selmailbagVOs);
				String assignTo = reassignMailbagForm.getAssignToFlight();
				String mailbags = "";
				int errorFlag = 0;
				if (selmailbagVOs != null && selmailbagVOs.size() > 0) {
					for(MailbagVO mailbagVO:selmailbagVOs){
						if("FLIGHT".equals(assignTo)){
							
							if(mailbagVO.getCarrierId() == containerVO.getCarrierId()
								&& mailbagVO.getFlightNumber().equals(containerVO.getFlightNumber())
								&& mailbagVO.getFlightSequenceNumber() == containerVO.getFlightSequenceNumber()
								&& mailbagVO.getSegmentSerialNumber() == containerVO.getSegmentSerialNumber()
								&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())){
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
								&& mailbagVO.getContainerNumber().equals(containerVO.getContainerNumber())
								&& mailbagVO.getPou().equals(containerVO.getFinalDestination())){
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
		    	    reassignMailbagForm.setCloseFlag("N");
		    	    reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		    	    invocationContext.target = TARGET;
		    	    return;
		       }
			   
			   
			  
			   for(MailbagVO mailbagVO:selmailbagVOs){
				   
				   if(!"FLIGHT".equals(reassignMailbagForm.getHideRadio())){
						String scanDate = new StringBuilder().append(reassignMailbagForm.getScanDate()).append(" ").append(reassignMailbagForm.getMailScanTime()).append(":00").toString();
					    LocalDate scanDat = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
					    scanDat.setDateAndTime(scanDate);
					    mailbagVO.setScannedDate(scanDat);
			       }else{
			    	   mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			       }
				   mailbagVO.setScannedPort(logonAttributes.getAirportCode());
				   mailbagVO.setScannedUser(logonAttributes.getUserId());
				   mailbagVO.setMailSource(SCREEN_NUMERICAL_ID);
			   }
			   
			   log.log(Log.FINE, "\n\n selmailbagVOs for reassign ------->",
					selmailbagVOs);
			try {
			    
				  containerDetailsVOs = 
					  new MailTrackingDefaultsDelegate().reassignMailbags(selmailbagVOs,containerVO);
				  
	          }catch (BusinessDelegateException businessDelegateException) {
	    			errors = handleDelegateException(businessDelegateException);
	    	  }
	    	  if (errors != null && errors.size() > 0) {
	    		invocationContext.addAllError(errors);
	    		reassignMailbagForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
	    		reassignMailbagForm.setCloseFlag("N");
	    		invocationContext.target = TARGET;
	    		return;
	    	  }
	    	  log.log(Log.FINE, "containerDetailsVOs ----->>",
					containerDetailsVOs);
			if (containerDetailsVOs != null && containerDetailsVOs.size() > 0) {
	    		  emptyUldsSession.setContainerDetailsVOs(containerDetailsVOs);
//	    		  reassignMailbagForm.setCloseFlag("SHOWPOPUP");
//		    	  invocationContext.target = TARGET;
//		    	  return;

		      }
			
			reassignMailbagForm.setCloseFlag("Y");
			invocationContext.target = TARGET;
	    	log.exiting("SaveReassignMailbagCommand","execute");
	    	
	    }
	   

	       
	}
