/*
 * TransferContainerCommand.java Created on Jun 30 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.command.mail.operations.arrival;

import java.util.ArrayList;
import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.session.ApplicationSessionImpl;
import com.ibsplc.icargo.framework.struts.comp.config.ComponentAttributeConstants;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.BaseCommand;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.MailArrivalSession;
import com.ibsplc.icargo.presentation.web.session.interfaces.mail.operations.TransferContainerSession;
import com.ibsplc.icargo.presentation.web.struts.form.mail.operations.MailArrivalForm;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-5991
 *
 */
public class TransferContainerCommand extends BaseCommand {
	
	private Log log = LogFactory.getLogger("MAILOPERATIONS");
	/**
	 * TARGET
	 */
	private static final String TARGET_SUCCESS= "transfer_success";
	private static final String TARGET_FAIL= "transfer_fail";
	
	private static final String MODULE_NAME = "mail.operations";	
	private static final String SCREEN_ID = "mailtracking.defaults.mailarrival";	
	private static final String SCREEN_ID_REASSIGN = "mailtracking.defaults.transfercontainer";
	private static final String MAILS_TO_DELIVER = "deliverable_mails_present";
	
	
	/**
	 * This method overrides the execute method of BaseComand class
	 * @param invocationContext
	 * @return
	 * @throws CommandInvocationException
	 */
	public void execute(InvocationContext invocationContext)
	throws CommandInvocationException {
		
		log.entering("TransferContainerCommand","execute");
		
		MailArrivalForm mailArrivalForm = 
			(MailArrivalForm)invocationContext.screenModel;
		MailArrivalSession mailArrivalSession = 
			getScreenSession(MODULE_NAME,SCREEN_ID);
		TransferContainerSession transferContainerSession = 
			(TransferContainerSession)getScreenSession(MODULE_NAME, SCREEN_ID_REASSIGN);
		
		ApplicationSessionImpl applicationSession = getApplicationSession();
		LogonAttributes logonAttributes = applicationSession.getLogonVO();
		MailArrivalVO newMailArrivalVO=mailArrivalSession.getMailArrivalVO();
		ArrayList<ContainerDetailsVO> containerDetails = (ArrayList<ContainerDetailsVO>)newMailArrivalVO.getContainerDetails();
		Collection<ContainerVO> containerVOs =  new ArrayList<ContainerVO>();
		String airport = logonAttributes.getAirportCode();
		String[] dsns=mailArrivalForm.getContainer().split(",");
		int size=dsns.length;
		int arrivedCount=0;
		String[] conts = mailArrivalForm.getSelectContainer();
		Collection<ContainerDetailsVO> containerDetailsVOs =  new ArrayList<ContainerDetailsVO>();
		if(containerDetails!=null && !containerDetails.isEmpty() && 
				 conts!=null && conts.length>0 && containerDetails.size() >= conts.length){
			for(String arr: conts){
				containerDetailsVOs.add(containerDetails.get(Integer.parseInt(arr)));
			}
		}
		/*Code commented as part of ICRD-196662
		 * 1.Validation was included as part of ICRD-154709,but as per standard CR ICRD-95515 
		 * container transfer is possible even if logged in station is container destination.
		 * 2.As per new mail design,even if one of the mail is arrived 
		 * the container is released from flight,so else portion is also be commented
		 * as same is handled in script.
		for(ContainerDetailsVO containerVO : containerDetailsVOs){
	    	   if(logonAttributes.getAirportCode().equals(containerVO.getDestination())){
	    		   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailarrival.err.containeratfinaldest");
	    		   invocationContext.addError(errorVO);
	    		   invocationContext.target = TARGET_FAIL;
	      	    	return;
	    	   }else{
	    		   Collection<MailbagVO> vos = containerVO.getMailDetails();
	    		   if(vos != null && !vos.isEmpty()){
	    			   for(MailbagVO bagVO : vos){
	    				   if("Y".equals(bagVO.getArrivedFlag())){
	    					   arrivedCount++;
	    				   }
	    			   }
	    			   if(arrivedCount >0){
		    			   if(arrivedCount == vos.size()){
		    				   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailarrival.err.alreadyrelased");
	    		    		   invocationContext.addError(errorVO);
	    		    		   invocationContext.target = TARGET_FAIL;
	    		    		   return;
		    			   }else{
		    				   ErrorVO errorVO = new ErrorVO("mailtracking.defaults.mailarrival.err.partialarrivalmail",new Object[]{containerVO.getContainerNumber()});
	    		    		   invocationContext.addError(errorVO);
	    		    		   invocationContext.target = TARGET_FAIL;
	    		    		   return;
		    			   }
	    			   }
	    		   }
	    	   }
	       }*/
		Collection<String> nearbyOEToCurrentAirport  = new ArrayList<String>();
		for(int i=0;i<size;i++){
			ContainerDetailsVO contVO = ((ArrayList<ContainerDetailsVO>)(containerDetails)).get(Integer.parseInt(dsns[i].split("~")[0]));
			//Commented for icrd-95515
			/*if((logonAttributes.getAirportCode()).equals(contVO.getDestination())){
			mailArrivalForm.setChkFlag("");
			invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.destncurrentarp",
			new Object[]{contVO.getContainerNumber()}));					
			invocationContext.target = TARGET_FAIL;
			return;					
			}*/
			if(contVO.getReceivedBags()==MailConstantsVO.ZERO){
				/**
				 * Commented for bug 82312 starts
				 */
//				mailArrivalForm.setChkFlag("");
//				invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.noreceivedbags"));					
//				invocationContext.target = TARGET_FAIL;
//				return;	
				/**
				 * Commented for bug 82312 Ends
				 */
				
			}
			/**
			 * @author A-3227
			 * Added by Reno K Abraham for Validating DOE of MailBag/Despatch with Current Airport Code
			 */
			Collection<DSNVO> dsnVOs = contVO.getDsnVOs();
			Collection<MailbagVO> mailbagvo = contVO.getMailDetails();
			Collection<ArrayList<String>> groupedOECityArpCodes = null;
			Collection<String> does=new ArrayList<String>();
		if(dsnVOs != null && dsnVOs.size()!=0){
				for(DSNVO dsnVO:dsnVOs){
					log.log(Log.INFO, "<<<<<<DOE --- >>>", dsnVO.getDestinationExchangeOffice());
					if(!does.contains(dsnVO.getDestinationExchangeOffice())){
		 				does.add(dsnVO.getDestinationExchangeOffice());
		 			}					
				}
				if(does != null && does.size()!=0){
					try {
         				
         			    /* * findCityAndAirportForOE method returns Collection<ArrayList<String>> in which,
         			     * the inner collection contains the values in the order :
         			     * 0.OFFICE OF EXCHANGE
         			     * 1.CITY NEAR TO OE
         			     * 2.NEAREST AIRPORT TO CITY*/
         			     
         				groupedOECityArpCodes = 
         					new MailTrackingDefaultsDelegate().findCityAndAirportForOE(
         							logonAttributes.getCompanyCode(), does);
         			}catch (BusinessDelegateException businessDelegateException) {
						Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
						log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
					}				
					int errorFlag = 0;				
         			if(groupedOECityArpCodes != null && groupedOECityArpCodes.size() > 0){
         				for(String doe : does){
         					for(ArrayList<String> cityAndArpForOE : groupedOECityArpCodes) {
         						if(cityAndArpForOE.size() == 3 && 
         								doe.equals(cityAndArpForOE.get(0)) && 
         								airport.equals(cityAndArpForOE.get(2))){
         							errorFlag = 1;
         							break;
         						}			
         					}
         					if(errorFlag == 1) {
         						break;
         					}
         				}
         			}						
         			if(errorFlag == 1){
						log.log(Log.INFO,"<<----DOE of Mailbag/Despatch is Same as that of the current Airport --->>");
						/**
						 * Commented for bug 82312 starts
						 */
//						invocationContext.addError(new ErrorVO("mailtracking.defaults.mailarrival.cannottransfercontainers"));
//						mailArrivalForm.setChkFlag("");
//						invocationContext.target = TARGET_SUCCESS;
//						return;
						/**
						 * Commented for bug 82312 Ends
						 */
					}

					
				}
			}
			int errorFlag = 0;
			MailbagVO mailbagToDeliver = null;
			try {
				 nearbyOEToCurrentAirport  = 
					new MailTrackingDefaultsDelegate().findOfficeOfExchangesForAirport(
							logonAttributes.getCompanyCode(), airport);
			}catch (BusinessDelegateException businessDelegateException) {
				Collection<ErrorVO> errors = handleDelegateException(businessDelegateException);
				log.log(Log.INFO,"ERROR--SERVER------findCityAndAirportForOE---->>");
			}		
			Collection<MailbagVO> mailbagVOs = contVO.getMailDetails();
			if(mailbagVOs != null && mailbagVOs.size() >0){
				for(MailbagVO  mailvo:mailbagVOs){
						if (!MailConstantsVO.FLAG_YES.equals(mailvo.getDeliveredFlag())) { 
							if(isTerminating(nearbyOEToCurrentAirport,mailvo)){	
							 mailbagToDeliver = mailvo;
								errorFlag = 1;
									break;
							}	
					}
				}
			}
			if(errorFlag ==1){
				if(!MailConstantsVO.FLAG_YES.equals(mailArrivalForm.getWarningOveride())){
					Object[] obj = {mailbagToDeliver.getDoe()};
					ErrorVO err = new ErrorVO("mailtracking.defaults.deliverableMailbagsPresent",obj);
					err.setErrorDisplayType(ErrorDisplayType.WARNING);
					invocationContext.addError(err);
					invocationContext.target = TARGET_FAIL;
					mailArrivalForm.setChkFlag("");
					mailArrivalForm.setWarningFlag(MAILS_TO_DELIVER);
					return;
				}
				else {
					mailArrivalForm.setWarningFlag("");
					mailArrivalForm.setWarningOveride(null);
				}
			}
			ContainerVO vo=new ContainerVO();
			vo.setCompanyCode(contVO.getCompanyCode());
			vo.setContainerNumber(contVO.getContainerNumber());
			vo.setCarrierId(contVO.getCarrierId());
			vo.setFlightNumber(contVO.getFlightNumber());
			
			LocalDate flightDate = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
			vo.setFlightDate(flightDate.setDate(mailArrivalForm.getArrivalDate()));
			vo.setFlightSequenceNumber(contVO.getFlightSequenceNumber());
			vo.setLegSerialNumber(contVO.getLegSerialNumber());
			vo.setSegmentSerialNumber(contVO.getSegmentSerialNumber());
			vo.setFinalDestination(contVO.getDestination());
			vo.setAssignedPort(contVO.getPol());
			vo.setAssignedUser(logonAttributes.getUserId());
			vo.setType(contVO.getContainerType());
			vo.setPaBuiltFlag(contVO.getPaBuiltFlag());
			vo.setCarrierCode(newMailArrivalVO.getFlightCarrierCode());
			/*
			 * Added By Karthick V as the part of the Optimistic Locking Mechanism
			 * 
			 * 
			 */
			vo.setLastUpdateTime(contVO.getLastUpdateTime());
			vo.setULDLastUpdateTime(contVO.getUldLastUpdateTime());
			vo.setPou(contVO.getPou());
			/*
			 * 
			 * FOR M39 (CARDIT_1.2/RESDIT_1.0)
			 */
			vo.setShipperBuiltCode(contVO.getPaCode());
			vo.setContainerJnyID(contVO.getContainerJnyId());
			
			//Added by Deepu for CR QF1545 starts
			Collection<OnwardRoutingVO> onwardRoutingVos = new ArrayList<OnwardRoutingVO>();
			String onwardFlightRouting = contVO.getOnwardFlights();
			if (onwardFlightRouting != null) {
				String [] onwardFlightRoutings = onwardFlightRouting.split(",");
				if (onwardFlightRoutings != null && onwardFlightRoutings.length > 0) {
					for (String onwardFlightRoute : onwardFlightRoutings) {
						String [] onwardFlightRouteDetails =  onwardFlightRoute.split("-");
						OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
						onwardRoutingVO.setOnwardCarrierCode(onwardFlightRouteDetails[0]);
						onwardRoutingVO.setOnwardFlightNumber(onwardFlightRouteDetails[1]);
						StringBuilder OwnflightDate = new StringBuilder(onwardFlightRouteDetails[2]);
						OwnflightDate.append("-").append(onwardFlightRouteDetails[3]).append("-").append(onwardFlightRouteDetails[4]);
						LocalDate date =  new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
						date.setDate(OwnflightDate.toString());
						onwardRoutingVO.setOnwardFlightDate(date);
						onwardRoutingVO.setPou(onwardFlightRouteDetails[5]);
						onwardRoutingVO.setOperationFlag(OnwardRoutingVO.OPERATION_FLAG_INSERT);
						onwardRoutingVos.add(onwardRoutingVO);
					}
				}
				
				vo.setOnwardRoutings(onwardRoutingVos);
			}
		
//			Added by Deepu for CR QF1545 Ends
			containerVOs.add(vo);
			
		}
		if (size == 1) {
			transferContainerSession.setContainerVO(containerVOs.iterator().next());  //Added by Deepu for CR QF1545
		} else {
		transferContainerSession.setContainerVO(new ContainerVO());
		}
		transferContainerSession.setSelectedContainerVOs(containerVOs);
		mailArrivalForm.setChkFlag("showTransferScreen");
		mailArrivalForm.setScreenStatusFlag(ComponentAttributeConstants.SCREEN_STATUS_DETAIL);
		
		invocationContext.target = TARGET_SUCCESS;
		
		log.exiting("TransferContainerCommand","execute");
		
	}
	
	private boolean isTerminating(Collection<String> nearbyOEToCurrentAirport,
			MailbagVO mailbagvo){
		log.entering("TransferContainerCommand","break;");
		boolean isTerminating = false;
		if(nearbyOEToCurrentAirport != null && nearbyOEToCurrentAirport.size() > 0){
			for(String officeOfExchange : nearbyOEToCurrentAirport){
				if(mailbagvo != null && !"Y".equals(mailbagvo.getDeliveredFlag())){
					isTerminating = officeOfExchange.equals(mailbagvo.getDoe()) ? true : false;
					if(isTerminating){
						break;	
					}
				}
			}
		}
		log.exiting("TransferContainerCommand","break;");
		return isTerminating;
	}
}
