/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ke.mail.operations.KoreanMailController.java
 *
 *	Created by	:	A-8061
 *	Created on	:	13-Apr-2018
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.ke.mail.operations;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.CTOShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseXaddonMailController;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.DateUtilities;


/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.ke.mail.operations.KoreanMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-8061	:	13-Apr-2018	:	Draft
 */
@Module("bsmail")
@SubModule("operations")
public class KoreanMailController extends   BaseXaddonMailController{

	private Log log = LogFactory.getLogger("BSMAIL");
	   public static final String STATUS_EXECUTED = "E";
	   public static final String STATUS_AGG_TYPE_LEASE = "L";
	   private static String SYS_PARA_IMPORT_MAILSFOR_SPLITBOOKING="mail.operations.importmailsforsplitbooking";
	   private static String FIRST_FLIGHT="F";
	   private static String BOOKED_FLIGHT="B";
	   /**
	   *
	   * 	Method		:	KoreanMailController.performMailAWBTransactionsFlow
	   *	Added by 	:	a-8061 on 13-Apr-2018
	   * 	Used for 	:
	   *	Parameters	:	@param mailFlightSummaryVO
	   *	Parameters	:	@param eventCode
	   *	Parameters	:	@throws SystemException
	   *	Return type	: 	void
	   */
	   
	  public void performMailAWBTransactionsFlow(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)
	  		throws SystemException{
	  	log.entering("KoreanMailController", "performMailAWBTransactions");
	  	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
	  			.getLogonAttributesVO();
	  	log.log(Log.FINE, "MAAWB Event : "+eventCode);
	  	String importMailsForSplitBooking=null;
	  	importMailsForSplitBooking=findSystemParameterValue(SYS_PARA_IMPORT_MAILSFOR_SPLITBOOKING);
	  	mailFlightSummaryVO.setEventCode(eventCode);//a-8061 added as part of  ICRD-249916
	  	if("ACP".equals(eventCode)){
	  		 boolean reassignRequired=false;
	  		 String airportCode=null;
	  		if(mailFlightSummaryVO!=null && mailFlightSummaryVO.getShipmentSummaryVOs()!=null && mailFlightSummaryVO.getShipmentSummaryVOs().size()>0){

	  			//fetching carriercode using carrierId modified for ICRD-236397
	  	        AirlineValidationVO airlineValidationVO;
	  	        String fltDate=null;
	  	        if(mailFlightSummaryVO.getFlightDate()!=null){
	  	        try{	
				fltDate= DateUtilities.format(mailFlightSummaryVO.getFlightDate(), "ddMM");
	  	        }catch(Exception e){
	  	        	log.log(Log.SEVERE, e.getMessage());
	  	        }
				
				}

	  			airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
	  					mailFlightSummaryVO.getCarrierId());
	  		   ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
	  		  Set<String> spccontKey = new HashSet<String>();
	  			//fetching flight details
	              FlightFilterVO filghtFilterVO = new FlightFilterVO();
	              filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	              filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	              filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
	               filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	              filghtFilterVO.setDirection(FlightFilterVO.OUTBOUND);
	              if(mailFlightSummaryVO.getAirportCode()!=null){
	              filghtFilterVO.setStation(mailFlightSummaryVO.getAirportCode());
	              airportCode=mailFlightSummaryVO.getAirportCode();
	              }else{
	            	  filghtFilterVO.setStation(logonAttributes.getAirportCode());
	            	  airportCode=logonAttributes.getAirportCode();
	              }
	              
	              Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	              List<String> awbKey = new ArrayList<String>();
	              Collection<MailbagVO> acpMails = new ArrayList<MailbagVO>();
	  			for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
						boolean blockReceivedResdit = false;
						if(MailConstantsVO.FLAG_YES.equals(shipmentSummaryVO.getTranshipmentFlag()) ){//added by A-7371 as part of ICRD-256798
							blockReceivedResdit = true; 
						}
						
					if(BOOKED_FLIGHT.equals(importMailsForSplitBooking)){	
	  				 scannedMailDetailsVO = new Mailbag().findMailbagsForAWB
	  						(shipmentSummaryVO,mailFlightSummaryVO);
					}else if(FIRST_FLIGHT.equals(importMailsForSplitBooking)) {
						 scannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO,mailFlightSummaryVO);
					}
	  				 
	  				ArrayList<MailbagVO> oflChkMails = new ArrayList<MailbagVO>();
	  				/*
	  				 * reassignFlag is for scenario done in ICRD-264253
	  				 * reassignFlag condition(Direct reassign of already ACP mailbags in different flight) 
	  				 */
					boolean reassignFlag=findMailBagsforReassign(scannedMailDetailsVO,shipmentSummaryVO,mailFlightSummaryVO);
					/*if the mailbag is offloaded and is in ASG status under carrier and is then assigned to this flight then we need separate that mailbag 
	                 * and  pass it through reassign flow ,here  this flag represents that such mailbag is present in this collection
	                 */
					boolean reassignaftOFL=false;//added by A-7371 as part of ICRD-275041
	  				/*In case of OFFLOAD AND REASSIGN TO ANOTHER FLIGHT : After acceptance AWB offloaded from flight F1 and re attached mail bags in another flight F2 
					 * while re manifesting F1 mail bags will be offload from mail module(offload to carrier) and the mal status will be ASG
					 * while manifesting F2 offloaded mail bags should be reassign to F2 
					 * OFLMAILBAG: setting from  AWBAttachedMailbagDetailsMapper
					 * */	
					Collection<MailbagVO> reassignMails=new ArrayList<MailbagVO>(); 
					if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
					for(MailbagVO mailbagVO:scannedMailDetailsVO.getMailDetails()){
						if((MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO
								.getMailStatus()) || MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO
										.getMailStatus()))
						&& "OFLMAILBAG".equals(mailbagVO
								.getOffloadedRemarks()) || reassignFlag){
							if("OFLMAILBAG".equals(mailbagVO.getOffloadedRemarks()) && (mailbagVO.getMailStatus().equals(MailConstantsVO.MAIL_TXNCOD_ASG) ||mailbagVO.getMailStatus().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED))){
								reassignaftOFL=true;
							}
							reassignMails.add(mailbagVO);
							//MAIL_STATUS_ARRIVED condition added for ICRD-315331
						}else if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO
						.getMailStatus())||MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO
						.getMailStatus())){ 
							if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO.getMailStatus())){//added separate  if  condition to avoid regression . 
							acpMails.add(mailbagVO); 
							}else if(shipmentSummaryVO.getDestination()!=null && !shipmentSummaryVO.getDestination().equals(airportCode)){//ICRD-346634 added condition to restrict mail bag transfer from destination airport 
								acpMails.add(mailbagVO);
							}
						}
						else if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO
								.getMailStatus())){
							oflChkMails.add(mailbagVO);
						}
						
						
					}
					}
					if (scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty()
							&& reassignaftOFL || reassignFlag ) {
						StringBuilder conNum=null;		
						String opsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
		  						.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
		  						.append("~").append(shipmentSummaryVO.getDuplicateNumber())
		  						.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
		  				awbKey.add(opsKey); 
							
							boolean success=true;
							scannedMailDetailsVO.setProcessPoint(eventCode);
							scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
							scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getCarrierCode());
							for(MailbagVO mailbag:scannedMailDetailsVO.getMailDetails()){

								 if(flightValidationVOs!=null &&flightValidationVOs.size()>0){
									 mailbag.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
								 }
								 mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
								 mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
								 mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
								 mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
								 mailbag.setFinalDestination(mailbag.getPou());
								 mailbag.setInventoryContainer(mailbag.getContainerNumber());
								 mailbag.setInventoryContainerType(mailbag.getContainerType());
								 mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);

							 }
							scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getCarrierId());
							scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getFlightNumber());
							scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getFlightDate());
							scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
							scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							scannedMailDetailsVO.setScannedContainerDetails(null);
			
							
							if(mailFlightSummaryVO.getPou()==null){
								mailFlightSummaryVO.setPou(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegDestination());
							}
							
							if(mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())){
							scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
							scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
							}else{
								String routes[] = mailFlightSummaryVO.getRoute().split("-");
										if(Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())){
											scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
											scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
										}else{
											scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
											scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
										}
							}
							
							
							if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
							       scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
							       if(mailFlightSummaryVO.getFlightDate()!=null){
							    	   scannedMailDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
							       }else{
							       scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
							       }
							       
											}
											if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
												//To update the ULD info  from manifested shipment details
												ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
												manifestFilterVO.setManifestPrint(false);
												manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
											
												manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
												manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
												manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
												
												manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
												Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
												Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<ShipmentManifestVO>();
												HashMap<String,String> uldMap= new HashMap<String,String>();
												if(uldManifestVOs!=null && uldManifestVOs.size()>0){
													for(UldManifestVO uldManifestVO : uldManifestVOs){
														uldMap.put(uldManifestVO.getUldNumber(),uldManifestVO.getBarrowFlag());
														manifestShipmentDetails.addAll(uldManifestVO.getManifestShipmentDetails());
													}
												}
												if(manifestShipmentDetails!=null && manifestShipmentDetails.size()>0){
													for(ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails){
														if(shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix()) &&
																shipmentSummaryVO.getMasterDocumentNumber().equals(shipmentManifestVO.getMasterDocumentNumber())){
															for(MailbagVO mailBagVO:scannedMailDetailsVO.getMailDetails()){
																mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
																//scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());
																if("Y".equals(uldMap.get(shipmentManifestVO.getUldNumber()))|| MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
																	mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
																	scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
																	scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
																	if(shipmentManifestVO.getPou()!=null && shipmentManifestVO.getPou().trim().length()>0
																			&& mailFlightSummaryVO.getRoute()!=null && mailFlightSummaryVO.getRoute().split("-").length>2){
																		scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
																		if(shipmentManifestVO.getDestination()!=null){
																			scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
																		}
																	}
																}else{
																	mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
																	scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
																	scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
																	if(shipmentManifestVO.getPou()!=null && shipmentManifestVO.getPou().trim().length()>0
																			&& mailFlightSummaryVO.getRoute()!=null && mailFlightSummaryVO.getRoute().split("-").length>2){
																		scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
																		if(shipmentManifestVO.getDestination()!=null){
																			scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
																		}
																	}
																}

																scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
																if(MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
																	 conNum = new StringBuilder("").append(shipmentManifestVO.getUldNumber())
																			.append(scannedMailDetailsVO.getPou()).append(mailFlightSummaryVO.getFlightNumber());
																			;	
																	 if(fltDate!=null){
																		 conNum.append(fltDate);
																	 }
								  									mailBagVO.setContainerNumber(conNum.toString());
								  									scannedMailDetailsVO.setContainerNumber(conNum.toString());
																	}
								  									else{
								  										mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
									  									scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());	
								  									}
																
																
															}
														}
													}
												}
												
												
												//Added as part of bug ICRD-333932 by A-5526 starts
												if(scannedMailDetailsVO.getMailDetails()!=null  && scannedMailDetailsVO.getMailDetails().size()>0){
										            
										           
										            Collection<MailbagVO> reassignedMailbags=new ArrayList<MailbagVO>();
										             
								    	 for(MailbagVO mailbagInTransaction:scannedMailDetailsVO.getMailDetails()){              
												          
										  				//Added as part of BUG ICRD-332154 by A-5526 starts
										  				MailbagPK mailPK = new MailbagPK();
										  				Mailbag mail=null;          
														mailPK.setCompanyCode(mailbagInTransaction.getCompanyCode());
														mailPK.setMailSequenceNumber(mailbagInTransaction.getMailSequenceNumber());
														try {
															 mail = Mailbag.find(mailPK);  
														}catch (FinderException e) {    
															mail=null;    
														}
														   if(mail!=null && MailConstantsVO.OPERATION_OUTBOUND.equals(mail.getOperationalStatus()) &&
																		//mail.getUldNumber()!=null && !mail.getUldNumber().equals(mailbagInTransaction.getContainerNumber()) &&
																		mail.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()) && 
																		mail.getFlightSequenceNumber()==mailFlightSummaryVO.getFlightSequenceNumber() &&
																		mail.getScannedPort().equals(logonAttributes.getAirportCode())){
															   if(mail.getUldNumber()!=null && mail.getUldNumber().equals(mailbagInTransaction.getContainerNumber()))
															   {
																   reassignRequired=false;
															   }else{
																   reassignedMailbags.add(mailbagInTransaction);
																   reassignRequired=true;  
															   }    
															             
																
											             }     
								    	 }
								    	 
								    	 if(reassignRequired){
								    		
								    		 reassignMails=reassignedMailbags;     
								    		    
								    	 }
								    	 
								     }    //Added as part of bug ICRD-333932 by A-5526 ends
												
												//Ends
												//Collection<MailbagVO> mailsToReassign=findReassignableMailsAtFlightItself(scannedMailDetailsVO);
												if(!reassignMails.isEmpty()){
													scannedMailDetailsVO.setMailDetails(reassignMails);
												}
												  
											}
											
							
					        
								ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO ();
								containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
								containerDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
								containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
								containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
								containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
								containerDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
								containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
								containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
								containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
								containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
								containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
								containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
								containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
								containerDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
								containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
								
								
								containerDetailsVO.setPol(logonAttributes.getAirportCode());
								containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
								containerDetailsVO.setAcceptedFlag("Y");
								containerDetailsVO.setArrivedStatus("N");
								containerDetailsVO.setTransactionCode("ASG");
								containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								containerDetailsVO.setContainerOperationFlag("I");
								containerDetailsVO.setOflToRsnFlag(true);
								containerDetailsVO.setTransitFlag("Y");//added by A-8353 for ICRD-343079
								//scannedMailDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
								scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
								//success = saveContainerforReassign(containerDetailsVO,mailFlightSummaryVO);
								scannedMailDetailsVO.setScannedContainerDetails(null);
								
								scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);	
								success=true;
								
								
								
								
						
							scannedMailDetailsVO.setScannedContainerDetails(null);
							if(success){
							try {
							
							new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
							//Added as part of bug ICRD-333932 by A-5526 starts  
							 if(reassignRequired){
							unassignEmptyULDs(mailFlightSummaryVO);
							 }
							//Added as part of bug ICRD-333932 by A-5526 ends  
							} catch (MailHHTBusniessException e) {
								log.log(Log.SEVERE, e.getMessage());
							//	throw new SystemException(e.getMessage());
							} catch (MailMLDBusniessException e) {
								log.log(Log.SEVERE, e.getMessage());
							//	throw new SystemException(e.getMessage());
							}
							catch (MailTrackingBusinessException e) {
								log.log(Log.SEVERE, e.getMessage());
							//	throw new SystemException(e.getMessage());
							}
							catch(RemoteException e){
								log.log(Log.SEVERE, e.getMessage());
								//throw new SystemException(e.getMessage());
							}
							catch(Exception e){
								log.log(Log.SEVERE, e.getMessage());
								//throw new SystemException(e.getMessage());
							}
							
						}


						}
		
					/*if (oflChkMails!=null && !oflChkMails.isEmpty() ) {
						String cargoOpsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
		  						.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
		  						.append("~").append(shipmentSummaryVO.getDuplicateNumber())
		  						.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
		  				awbKey.add(cargoOpsKey);
		  					int oflbags=0;
		  					ScannedMailDetailsVO	scannedOFLDMailDetailsVO = new ScannedMailDetailsVO();
			  				 try {
			 					BeanUtils.copyProperties(scannedOFLDMailDetailsVO, scannedMailDetailsVO);
			 				} catch (IllegalAccessException e) {
			 					throw new SystemException(e.getMessage());
			 				} catch (InvocationTargetException e) {
			 					throw new SystemException(e.getMessage());
			 				}
			  				Collection<MailbagVO> oflMailBags = new ArrayList<MailbagVO>();
			  				oflbags=new Mailbag().findAwbPartialOflPcs(shipmentSummaryVO, mailFlightSummaryVO);
			  				 for(int i=0;i<oflbags;i++){
			  					oflMailBags.add(oflChkMails.get(i));
			  				 }
							scannedOFLDMailDetailsVO.setMailDetails(oflMailBags);
							for(MailbagVO mailVO : scannedOFLDMailDetailsVO.getMailDetails()){
								mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
								mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
								mailVO.setScannedUser(logonAttributes.getUserId());
								mailVO.setLastUpdateUser(logonAttributes.getUserId());
								mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
								mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
								mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
								mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								mailVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
								mailVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					}
							scannedOFLDMailDetailsVO.setScannedContainerDetails(null);
							scannedOFLDMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
							scannedOFLDMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
							scannedOFLDMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
							scannedOFLDMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
							scannedOFLDMailDetailsVO.setScannedUser(logonAttributes.getUserId());
							scannedOFLDMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
							scannedOFLDMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
							scannedOFLDMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
							scannedOFLDMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
							if(oflMailBags!=null&&!oflMailBags.isEmpty()){
							try {
								new MailUploadController().saveAndProcessMailBags(scannedOFLDMailDetailsVO);
							}
							catch (MailHHTBusniessException e) {
								throw new SystemException(e.getMessage());
							} catch (MailMLDBusniessException e) {
								throw new SystemException(e.getMessage());
							} catch (MailTrackingBusinessException e) {
								throw new SystemException(e.getMessage());
							} catch(RemoteException e){
								throw new SystemException(e.getMessage());
							}
							}
						}*/
					
					
					
						
					//acceptance
					if(!acpMails.isEmpty() && scannedMailDetailsVO.getMailDetails() != null
							&& !scannedMailDetailsVO.getMailDetails().isEmpty()) {
							
					HashMap<String,String> uldMap= new HashMap<String,String>();
					StringBuilder conNum=null;		
							
					scannedMailDetailsVO.setMailDetails(acpMails);
					
	  				String cargoOpsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
	  						.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
	  						.append("~").append(shipmentSummaryVO.getDuplicateNumber())
	  						.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
	  				awbKey.add(cargoOpsKey);
	  				if(scannedMailDetailsVO!=null){
	  				scannedMailDetailsVO.setProcessPoint(eventCode);
	  				
	  				if(mailFlightSummaryVO.getAirportCode()!=null){
	  				scannedMailDetailsVO.setAirportCode(mailFlightSummaryVO.getAirportCode());
	  				}else{
	  				scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
	  				}
						
						
	  				if(mailFlightSummaryVO.getPou()==null){
						mailFlightSummaryVO.setPou(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegDestination());
					}	
						
	  				if(mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())){
	  				scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
	  				scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
	  				}else if (mailFlightSummaryVO.getRoute()!=null){
	  					
	  					//A-8061 added for ICRD-253703 starts
	  					String routes[] = mailFlightSummaryVO.getRoute().split("-");
	  					/*	IF case
	  					 *  Flight F1 Route A-B-C and AWB orign is A and Destination is C ,
	  					 *  POU of  mailFlightSummaryVO is B so we r setting shipmentSummaryVO's destination as scannedMailDetailsVO POU.
	  					 *  else case
	  					 *  AWB A-C , F1 A-B and F2 B-C . while accepting mailbag in flight F1 then scannedMailDetailsVO  POU should be mailFlightSummaryVO POU , if we take
	  					 *  shipmentSummaryVO POU then we cannot arrive mail bag at station B .
	  					 */
	  							if(Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())){
	  								scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
	  								scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
	  							}else{
	  								scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
	  								scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
	  							}
	  					//A-8061 added for ICRD-253703 end
	  				}else{
	  					scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
	  				}
	  				
	  				if(scannedMailDetailsVO.getAirportCode()!=null){
	  					scannedMailDetailsVO.setPol(scannedMailDetailsVO.getAirportCode());
	  				}else{
	  				scannedMailDetailsVO.setPol(logonAttributes.getAirportCode());
	  				}
	  				
	  				scannedMailDetailsVO.setMailSource("EXPFLTFIN_ACPMAL");
	  				scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
	  				if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
	         scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	         
			       if(mailFlightSummaryVO.getFlightDate()!=null){
			    	   scannedMailDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
			       }else{
			       scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
			       }

	  				}
	  				if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
	  					ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
	  					manifestFilterVO.setManifestPrint(false);
	  					manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());

						manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
						manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		
					
					if(scannedMailDetailsVO.getAirportCode()!=null){
	  					manifestFilterVO.setPointOfLading(scannedMailDetailsVO.getAirportCode());
					}else{
	  					manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
					}
	  					Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
	  					Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<ShipmentManifestVO>();
	  					if(uldManifestVOs!=null && uldManifestVOs.size()>0){
	  						for(UldManifestVO uldManifestVO : uldManifestVOs){
	  							uldMap.put(uldManifestVO.getUldNumber(),uldManifestVO.getBarrowFlag());
	  							manifestShipmentDetails.addAll(uldManifestVO.getManifestShipmentDetails());
	  						}
	  					}
	  					if(manifestShipmentDetails!=null && manifestShipmentDetails.size()>0){
	  						for(ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails){
	  							if(shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix()) &&
	  									shipmentSummaryVO.getMasterDocumentNumber().equals(shipmentManifestVO.getMasterDocumentNumber())){
	  								for(MailbagVO mailBagVO:scannedMailDetailsVO.getMailDetails()){
											if(blockReceivedResdit){
												mailBagVO.setBlockReceivedResdit(blockReceivedResdit);
											}
											
									/* if("Y".equals(shipmentSummaryVO.getTranshipmentFlag())){
										mailBagVO.setTransferFromCarrier(shipmentSummaryVO.getFromCarrier());
									 }*/
											
								
										scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
										scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
										scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
										scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
										scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										mailBagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
										mailBagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
										mailBagVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										mailBagVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
									

											//if(MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
	  									//if awbs are accepted in barrow from OPS .
	  									//while flight finalization all the barrows in that flight will be convert to BULK .
	  									if("Y".equals(uldMap.get(shipmentManifestVO.getUldNumber()))||MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
												mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
												scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
												scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
												if(shipmentManifestVO.getPou()!=null && shipmentManifestVO.getPou().trim().length()>0
														&& mailFlightSummaryVO.getRoute()!=null && mailFlightSummaryVO.getRoute().split("-").length>2){
													scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
													if(shipmentManifestVO.getDestination()!=null){
														scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
													}
												}
	  									}else{
												mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
												scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
	  										scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());//a-8061 added for ICRD-255007
	  										if(shipmentManifestVO.getPou()!=null && shipmentManifestVO.getPou().trim().length()>0
	  												&& mailFlightSummaryVO.getRoute()!=null && mailFlightSummaryVO.getRoute().split("-").length>2){
	  										scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
	  										if(shipmentManifestVO.getDestination()!=null){
	  										scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
	  										}
	  										}
	  										
	  									}
	  									
										if(MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())){
										 conNum = new StringBuilder("").append(shipmentManifestVO.getUldNumber())
												.append(scannedMailDetailsVO.getPou()).append(mailFlightSummaryVO.getFlightNumber());
												;	
										 if(fltDate!=null){
											 conNum.append(fltDate);
										 }
	  									mailBagVO.setContainerNumber(conNum.toString());
	  									scannedMailDetailsVO.setContainerNumber(conNum.toString());
										}
	  									else{
	  										mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
		  									scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());	
	  									}
										
										if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
											scannedMailDetailsVO.setDestination(scannedMailDetailsVO.getPou());
										}
										
										
										
	  								}
	  							}
	  						}
	  					}
	  				}

	  				}


	  			try {
	  				new MailUploadController().saveAcceptanceFromUpload(scannedMailDetailsVO,logonAttributes);
	  				checkforATDCaptureFlight(mailFlightSummaryVO);//added by A-7371 as part of ICRD-233074 for triggering uplift resdit for ATD capture in Export Manifest Screen
	  				//success =true;
	  				acpMails = new ArrayList<MailbagVO>();
	  			} catch (MailHHTBusniessException e) {
	  				throw new SystemException(e.getMessage());
	  			} catch (MailMLDBusniessException e) {
	  				throw new SystemException(e.getMessage());
	  			}catch (ForceAcceptanceException e) {
	  				throw new SystemException(e.getMessage());
	  			}



	  		}
					
					

				}
	  			//partial offload
	  			boolean offloadReq = false;
				try{
					ScannedMailDetailsVO scnMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
							(null,mailFlightSummaryVO);
					//Logic to identify offloaded mail AWBs start
					for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
						String mftAwbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
								.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~").append(shipmentSummaryVO.getDuplicateNumber())
								.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
						awbKey.add(mftAwbKey);
					}
					//Logic to identify offloaded mail AWBs end
					Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
					int count = 0;
					double weight= 0.0;
					
					ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
					manifestFilterVO.setManifestPrint(false);
					manifestFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
					manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
					Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
					HashMap<String,String> uldMap= new HashMap<String,String>();
					HashMap<String,Integer> oflUldMap= new HashMap<String,Integer>();
					HashMap<String,String> acpUldMap= new HashMap<String,String>();
					HashMap<String,String> addedULDMap= new HashMap<String,String>();
					
		  			HashMap<Long,String> dupCheckMap = new HashMap<Long,String>();
		  			Collection<MailbagVO> duplicationRemovedMails=new ArrayList<MailbagVO>();
		  			
					if(uldManifestVOs!=null && uldManifestVOs.size()>0){
						for(UldManifestVO uldManifestVO : uldManifestVOs){
							uldMap.put(uldManifestVO.getUldNumber(),uldManifestVO.getBarrowFlag());
							if(MailConstantsVO.CONST_BULK.equals(uldManifestVO.getUldNumber())){//ICRD-345024
								StringBuilder	 bulkNum = new StringBuilder("").append(uldManifestVO.getUldNumber())
										.append(uldManifestVO.getPou()).append(mailFlightSummaryVO.getFlightNumber()).append(fltDate);
								uldMap.put(bulkNum.toString(),uldManifestVO.getBarrowFlag());
								}
						}
					}
					
					if(scnMailDetailsVO != null && scnMailDetailsVO.getMailDetails() != null
							&& !scnMailDetailsVO.getMailDetails().isEmpty()){
						for(MailbagVO mailVO : scnMailDetailsVO.getMailDetails()){
							if(mailVO.getShipmentPrefix() != null && mailVO.getDocumentNumber() != null
									&& mailVO.getDuplicateNumber() > 0 && mailVO.getSequenceNumber() > 0){
								String mailOpsKey = new StringBuilder("").append(mailVO.getShipmentPrefix())
										.append("~").append(mailVO.getDocumentNumber())
										.append("~").append(mailVO.getDuplicateNumber())
										.append("~").append(mailVO.getSequenceNumber()).toString();
								if(awbKey.contains(mailOpsKey)){
									if(mailVO.getContainerNumber()!=null && !acpUldMap.containsKey(mailVO.getContainerNumber())){
										acpUldMap.put(mailVO.getContainerNumber(), mailVO.getContainerNumber());
									}
									
								}
								
							}
							
							try{
							MailbagPK pk = new MailbagPK();
							pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
							pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
							Mailbag bag = Mailbag.find(pk);
							mailVO.setFlightNumber(bag.getFlightNumber());
							mailVO.setFlightSequenceNumber(bag.getFlightSequenceNumber());
							mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
							if(!dupCheckMap.containsKey(mailVO.getMailSequenceNumber())){
								duplicationRemovedMails.add(mailVO);
								dupCheckMap.put(mailVO.getMailSequenceNumber(), mailVO.getMailbagId());
							}
							}catch(FinderException ex){
								 log.log(Log.SEVERE, ex.getMessage());
							}

						}
					}
					
					scnMailDetailsVO.setMailDetails(duplicationRemovedMails);

					//Added as part of bug ICRD-345241 by A-5526 starts
					//The below flag is used to restrict container as such offload.if the value is true ,we will restrict the container as such offload else proceed
					boolean restrictContainerOffload=false;
					HashMap<String,ScannedMailDetailsVO> restrictedAWBDetailsForOffload= new HashMap<String,ScannedMailDetailsVO>();
					if(scnMailDetailsVO != null && scnMailDetailsVO.getMailDetails() != null
							&& !scnMailDetailsVO.getMailDetails().isEmpty()){
						for(MailbagVO mailVO : scnMailDetailsVO.getMailDetails()){
							if(mailVO.getShipmentPrefix() != null && mailVO.getDocumentNumber() != null
									&& mailVO.getDuplicateNumber() > 0 && mailVO.getSequenceNumber() > 0){
								String mailOpsKey = new StringBuilder("").append(mailVO.getShipmentPrefix())
										.append("~").append(mailVO.getDocumentNumber())
										.append("~").append(mailVO.getDuplicateNumber())
										.append("~").append(mailVO.getSequenceNumber()).toString();
								if(!awbKey.contains(mailOpsKey)){
									offloadReq = true;
									if(mailVO.getContainerNumber()!=null && !uldMap.containsKey(mailVO.getContainerNumber())  && !acpUldMap.containsKey(mailVO.getContainerNumber())){
										if(oflUldMap.containsKey(mailVO.getContainerNumber())){
										oflUldMap.put(mailVO.getContainerNumber(), oflUldMap.get((mailVO.getContainerNumber()))+1);	
										}else{
										oflUldMap.put(mailVO.getContainerNumber(), 1);
										}
									}else{
									count++;

									if(mailVO.getContainerNumber()!=null){
										spccontKey.add(mailVO.getContainerNumber());
									}
									weight = weight+ mailVO.getWeight().getDisplayValue();
									mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
									mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
									mailVO.setScannedUser(logonAttributes.getUserId());
									mailVO.setLastUpdateUser(logonAttributes.getUserId());
									mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
									mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
									mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
									mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
									mailVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
									mailVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
									mailVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
									try{
										MailbagPK pk = new MailbagPK();
										pk.setCompanyCode(logonAttributes.getCompanyCode());
										pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
										Mailbag bag = Mailbag.find(pk);
										if(bag!=null){
											mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
											scnMailDetailsVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
										}
										//Added as part of bug ICRD-345241 by A-5526 starts
										//Offload should happen only for export side mailbags ,which are present in the same flight itself.
										//If any of the mailbag is arrived/delivered  at import side,then such mailbags shouldn't get offloaded from the flight (in the mail side)

										if(MailConstantsVO.OPERATION_OUTBOUND.equals(bag.getOperationalStatus()) &&
												(bag.getFlightNumber()!=null && bag.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber())  && bag.getFlightSequenceNumber()==mailFlightSummaryVO.getFlightSequenceNumber()) 
												&&(airportCode!=null && airportCode.equals(mailVO.getScannedPort()))){
											mailbags.add(mailVO);    
											//validOffload=true;
										} 
										//Added as part of bug ICRD-345241 by A-5526 ends
									}catch(FinderException exception){
										  log.log(Log.SEVERE, exception.getMessage());
									}
									
									

										 
									
									
										
									

								}
									
								}
								
								//Added as part of bug ICRD-345241 by A-5526 starts
								//The below code is used to restrict full offload(container as such offlod) if any of the mailabg is delivered or arrived(reached at import side) 
								//This is used to avoid the removal of mail manifest for an already arrived/delivered bags
								if (restrictedAWBDetailsForOffload != null && !restrictedAWBDetailsForOffload.isEmpty()
										&& restrictedAWBDetailsForOffload.containsKey(mailVO.getDocumentNumber())) {
									//Already validated
									
								} else {
									ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
									shipmentSummaryVO.setCompanyCode(mailVO.getCompanyCode());
									shipmentSummaryVO.setOwnerId(mailVO.getCarrierId());
									shipmentSummaryVO.setMasterDocumentNumber(mailVO.getDocumentNumber());
									shipmentSummaryVO.setSequenceNumber(mailVO.getSequenceNumber());
									shipmentSummaryVO.setDuplicateNumber(mailVO.getDuplicateNumber());
									ScannedMailDetailsVO newScannedMailDetailsVO = new Mailbag()
											.findAWBAttachedMailbags(shipmentSummaryVO, mailFlightSummaryVO);
									for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
										restrictedAWBDetailsForOffload.put(mailVO.getDocumentNumber(),
												newScannedMailDetailsVO);
										
										if ((airportCode!=null && !airportCode.equals(mailbagVO.getScannedPort())) || MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()) || 
												(mailbagVO.getFlightNumber()!=null && !mailbagVO.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()) )|| mailbagVO.getFlightSequenceNumber()!=mailFlightSummaryVO.getFlightSequenceNumber() ) {
											
											restrictContainerOffload = true;    
											break;      
										}
									}
									
								}
								//Added as part of bug ICRD-345241 by A-5526 ends
								
							}
						}
						scnMailDetailsVO.setMailDetails(mailbags);
					/*	if(scnMailDetailsVO.getContainerNumber() != null &&
								!scnMailDetailsVO.getContainerNumber().equals(scannedMailDetailsVO.getContainerNumber())){
							scnMailDetailsVO.setContOffloadReq(true);
						}*/
						
					}
					if(offloadReq){
					scnMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
					scnMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
					scnMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					scnMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					scnMailDetailsVO.setScannedUser(logonAttributes.getUserId());
					scnMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
					scnMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
					//Added as part of bug ICRD-345241 by A-5526 
					if (restrictContainerOffload) {
						scnMailDetailsVO.setScannedContainerDetails(null);
					}
	  				else if(scnMailDetailsVO.getScannedContainerDetails() != null &&
							!scnMailDetailsVO.getScannedContainerDetails().isEmpty()){
						Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
						for(ContainerVO containerVO : scnMailDetailsVO.getScannedContainerDetails()){
							if(oflUldMap.containsKey(containerVO.getContainerNumber()) && !addedULDMap.containsKey(containerVO.getContainerNumber())&& !uldMap.containsKey(containerVO.getContainerNumber())  && !acpUldMap.containsKey(containerVO.getContainerNumber())){
							addedULDMap.put(containerVO.getContainerNumber(), containerVO.getContainerNumber());
							containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
							containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
							containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
							containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
							containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
							containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
							//containerVO.setSegmentSerialNumber(scnMailDetailsVO.getSegmentSerialNumber());
							//Changed for bug ICRD-330260 by A-5526 starts
							if(mailFlightSummaryVO.getPou()!=null && !mailFlightSummaryVO.getPou().isEmpty())
							containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
			  					else      
			  					containerVO.setFinalDestination(containerVO.getPou());
								//Changed for bug ICRD-330260 by A-5526 ends
							containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
							containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
							containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
							containerVO.setOffload(true);
							containerVO.setBags(oflUldMap.get(containerVO.getContainerNumber()));
							containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
							containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
							containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							containerVO.setLastUpdateUser(logonAttributes.getUserId());
							containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
							contVOs.add(containerVO);
							}
						}
						scnMailDetailsVO.setScannedContainerDetails(contVOs);
					}
					
						if(scnMailDetailsVO.getScannedContainerDetails()!=null && !scnMailDetailsVO.getScannedContainerDetails().isEmpty()){
							scnMailDetailsVO.setContOffloadReq(true);
							new MailUploadController().saveAndProcessMailBags(scnMailDetailsVO);
						}
						if(mailbags!=null && !mailbags.isEmpty()){
						scnMailDetailsVO.setContOffloadReq(false);	
						scnMailDetailsVO.setScannedContainerDetails(null);
						scnMailDetailsVO.setMailDetails(mailbags);
						new MailUploadController().saveAndProcessMailBags(scnMailDetailsVO);
					}

			        //PersistenceController.getEntityManager().flush();
	                              checkforATDCaptureFlight(mailFlightSummaryVO);//added by A-7371 as part of ICRD-233074 for triggering uplift resdit for ATD capture in Export Manifest Screen

					}

					} catch (MailHHTBusniessException e) {
						log.log(Log.SEVERE, e.getMessage());
					} catch (MailMLDBusniessException e) {
						log.log(Log.SEVERE, e.getMessage());
					} catch (MailTrackingBusinessException e) {
						log.log(Log.SEVERE, e.getMessage());
					} catch(RemoteException e){
						log.log(Log.SEVERE, e.getMessage());
					}catch(ForceAcceptanceException e){
						log.log(Log.SEVERE, e.getMessage());
					}
			}
			else{	 //full offload
				//Added as part of bug ICRD-345241 by A-5526 
				//The below flag is used to restrict container as such offload.if the value is true ,we will restrict the container as such offload else proceed
				boolean restrictContainerOffload=false;
					   int segSerial = 1;
						boolean isFlightClosed = false;
						String arpCod=null;
						HashMap<String,String> uldMap= new HashMap<String,String>();
						OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
						operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
						operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
						operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
						operationalFlightVO.setDirection(FlightFilterVO.OUTBOUND);
						try {
							isFlightClosed = new MailController().isFlightClosedForOperations(
									operationalFlightVO);
						} catch (SystemException systemException) {
							 log.log(Log.SEVERE, "SystemException",systemException.getMessage());
						}

						arpCod=mailFlightSummaryVO.getAirportCode()!=null?mailFlightSummaryVO.getAirportCode():logonAttributes.getAirportCode();
				isFlightClosed	= true;//Added for temporary fixing offload issue , if flight is not closed then mail bags should be reassigned to carrier .			
	  			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
	  					(null,mailFlightSummaryVO);

	  			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
	  					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
						if(isFlightClosed){	
	  			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
						}else{
					scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);		
						}
	  			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	  			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
	  			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
	  			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
	  			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
	  			scannedMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
	  			scannedMailDetailsVO.setContOffloadReq(true);
	  			int count = 0;
	  			double weight= 0.0;
	  			Collection<MailbagVO> offloadMails=new ArrayList<MailbagVO>();
	  			HashMap<Long,String> dupCheckMap = new HashMap<Long,String>();
	  			Collection<MailbagVO> duplicationRemovedMails=new ArrayList<MailbagVO>();
	  			
	  			for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
	  				try{
						MailbagPK pk = new MailbagPK();
						pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
						pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
						Mailbag bag = Mailbag.find(pk);
						mailVO.setFlightNumber(bag.getFlightNumber());
						mailVO.setFlightSequenceNumber(bag.getFlightSequenceNumber());
						mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
						if(!dupCheckMap.containsKey(mailVO.getMailSequenceNumber())){
							duplicationRemovedMails.add(mailVO);
							dupCheckMap.put(mailVO.getMailSequenceNumber(), mailVO.getMailbagId());
						}
					}catch(Exception exception){
						log.log(Log.FINE, exception.getMessage());
					}
	  				 
	  			}
	  			scannedMailDetailsVO.setMailDetails(duplicationRemovedMails); 
	  			
	  			HashMap<String,ScannedMailDetailsVO> restrictedAWBDetailsForOffload= new HashMap<String,ScannedMailDetailsVO>();
	  				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
	  					count++;
	  					weight = weight+ mailVO.getWeight().getDisplayValue();
	  					mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
	  					mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
	  					mailVO.setScannedUser(logonAttributes.getUserId());
	  					mailVO.setLastUpdateUser(logonAttributes.getUserId());
							if(isFlightClosed){
	  					mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
	  					mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
							}
	  					mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
	  					mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					try{
						MailbagPK pk = new MailbagPK();
						pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
						pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
						Mailbag bag = Mailbag.find(pk);
						if(bag != null && bag.getSegmentSerialNumber() >0){
							segSerial = bag.getSegmentSerialNumber();
						}
						if(MailConstantsVO.OPERATION_OUTBOUND.equals(bag.getOperationalStatus()) &&
								(bag.getFlightNumber()!=null && bag.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()) )&& bag.getFlightSequenceNumber()==mailFlightSummaryVO.getFlightSequenceNumber() 
								&&(arpCod!=null && arpCod.equals(mailVO.getScannedPort()))){
							offloadMails.add(mailVO);      
							
						}
					}catch(Exception exception){
						log.log(Log.FINE, exception.getMessage());
					}
					//Added as part of bug ICRD-345241 by A-5526 starts
				//The below code is used to restrict full offload(container as such offlod) if any of the mailabg is delivered or arrived(reached at import side) 
					//This is used to avoid the removal of mail manifest for an already arrived/delivered bags
					
					if (restrictedAWBDetailsForOffload != null && !restrictedAWBDetailsForOffload.isEmpty()
							&& restrictedAWBDetailsForOffload.containsKey(mailVO.getDocumentNumber())) {
						//Already validated
						
					} else {    
						ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
						shipmentSummaryVO.setCompanyCode(mailVO.getCompanyCode());
						shipmentSummaryVO.setOwnerId(mailVO.getCarrierId());
						shipmentSummaryVO.setMasterDocumentNumber(mailVO.getDocumentNumber());
						shipmentSummaryVO.setSequenceNumber(mailVO.getSequenceNumber());
						shipmentSummaryVO.setDuplicateNumber(mailVO.getDuplicateNumber());
						ScannedMailDetailsVO newScannedMailDetailsVO = new Mailbag()
								.findAWBAttachedMailbags(shipmentSummaryVO, mailFlightSummaryVO);
						for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
							restrictedAWBDetailsForOffload.put(mailVO.getDocumentNumber(),
									newScannedMailDetailsVO);
							
							if ( (arpCod!=null && !arpCod.equals(mailbagVO.getScannedPort())) || MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus()) || MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus()) || 
									(mailbagVO.getFlightNumber()!=null && !mailbagVO.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()) )|| mailbagVO.getFlightSequenceNumber()!=mailFlightSummaryVO.getFlightSequenceNumber() ) {
								
								restrictContainerOffload = true;    
								break;      
							}
						}
						
					}

					}
	  			//Added as part of bug ICRD-345241 by A-5526 ends
	  				scannedMailDetailsVO.setMailDetails(offloadMails);  
	  			//Added as part of bug ICRD-345241 by A-5526 
					if (restrictContainerOffload) {
						scannedMailDetailsVO.setScannedContainerDetails(null);
					}
	  				else if( scannedMailDetailsVO.getScannedContainerDetails() != null &&
	  					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
							String containerNumber = "";
							Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
	  				for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
								if(!uldMap.containsKey(containerVO.getContainerNumber())){
									
						uldMap.put(containerVO.getContainerNumber(), containerVO.getContainerNumber());
									
	  					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setSegmentSerialNumber(segSerial);
	  					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	  					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
						//Changed for bug ICRD-330260 by A-5526 starts
	  					if(mailFlightSummaryVO.getPou()!=null && !mailFlightSummaryVO.getPou().isEmpty())
	  					containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
	  					else      
	  					containerVO.setFinalDestination(containerVO.getPou());
						//Changed for bug ICRD-330260 by A-5526 ends
								if(isFlightClosed){
	  					containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
	  					containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
	  					containerVO.setOffload(true);
	  					containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
								}
	  					containerVO.setBags(count);
	  					containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
	  					containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
	  					containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  					containerVO.setLastUpdateUser(logonAttributes.getUserId());
	  					containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
								contVOs.add(containerVO);
								}
							}
							scannedMailDetailsVO.setScannedContainerDetails(contVOs);
						}

						
	  			try {
	  				new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
	  			} catch (MailHHTBusniessException e) {
				log.log(Log.FINE, e.getMessage());
	  			} catch (MailMLDBusniessException e) {
				log.log(Log.FINE, e.getMessage());
	  			} catch (MailTrackingBusinessException e) {
				log.log(Log.FINE, e.getMessage());
	  			} catch(RemoteException e){
				log.log(Log.FINE, e.getMessage());
	  			}catch(ForceAcceptanceException e){
				log.log(Log.FINE, e.getMessage());
	  			}
	  			checkforATDCaptureFlight(mailFlightSummaryVO);
	  		}
					
					
					
	  			}

	  	}else if("ARR".equals(eventCode)){
	             //fetching carriercode using carrierId
	  	        AirlineValidationVO airlineValidationVO;
	        int segSerial=1;
	        int shpSegSerNum=0;
	  		airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
	  				mailFlightSummaryVO.getCarrierId());
	  			//fetching flight details
	  			FlightFilterVO filghtFilterVO = new FlightFilterVO();
	  			filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	  			filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	  			filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
	  			filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	  			filghtFilterVO.setDirection("I");
	             Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	  		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
	  			String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
	  					.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
	  					.append("~").append(shipmentSummaryVO.getDuplicateNumber())
	  					.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
	  			boolean isBKDExist=false;
	  			boolean isACPExist=false;
	  			
	  			if(mailFlightSummaryVO.getUldAwbMap()!=null && mailFlightSummaryVO.getUldAwbMap().containsKey(awbKey)){
	  			
	  			ScannedMailDetailsVO scannedMailDetailsVO =null;
	  			if(BOOKED_FLIGHT.equals(importMailsForSplitBooking)){	
	  				 scannedMailDetailsVO = new Mailbag().findMailbagsForAWB
	  					(shipmentSummaryVO,mailFlightSummaryVO);
					}else if(FIRST_FLIGHT.equals(importMailsForSplitBooking)) {
						 scannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO,mailFlightSummaryVO);
			  						
					}
	  			
	  			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
	  					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
	  				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getStatus())){
	  					continue;
	  				}

	  				
					ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
					manifestFilterVO.setManifestPrint(false);
					manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());

						manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
						manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			
					manifestFilterVO.setAirportCode(logonAttributes.getAirportCode());
					manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
					ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
					shipmentValidationVO.setOwnerId(shipmentSummaryVO.getOwnerId());
					shipmentValidationVO.setDocumentNumber(shipmentSummaryVO.getMasterDocumentNumber());
					shipmentValidationVO.setDuplicateNumber(shipmentSummaryVO.getDuplicateNumber());
					shipmentValidationVO.setSequenceNumber(shipmentSummaryVO.getSequenceNumber());
					CTOShipmentManifestVO manifestvo = new OperationsFltHandlingProxy().findShipmentForImportManifest(manifestFilterVO,shipmentValidationVO);
					if(manifestvo !=null){
						segSerial = manifestvo.getSegmentSerialNumber();
					}
					
					

	  			//Added by A-5219 for ICRD-256200 start
	              String containerNumber = "";
	              String uldPol="";
	              String uldArr[]=null;
	  			if(mailFlightSummaryVO.getUldAwbMap() != null && !mailFlightSummaryVO.getUldAwbMap().isEmpty()){
	  				//containerNumber = mailFlightSummaryVO.getUldAwbMap().get(awbKey);
	  			uldArr=	mailFlightSummaryVO.getUldAwbMap().get(awbKey).split("~");
	  			containerNumber =uldArr[0];
	  			if(containerNumber.length()>1){	 //ICRD-318922
	  				uldPol=uldArr[1];
	  			}
	
	  			}
	  			//ICRD-316411
	  			
	  			if(mailFlightSummaryVO.getShpDetailMap() != null && !mailFlightSummaryVO.getShpDetailMap().isEmpty()){//ICRD-320931
	  				try{
	  				shpSegSerNum = Integer.parseInt(mailFlightSummaryVO.getShpDetailMap().get(awbKey));
	  				}catch(NumberFormatException numberFormatException){
	  					log.log(Log.FINE, numberFormatException.getMessage());
	  				}
	  				if(shpSegSerNum>0){
	  				segSerial=shpSegSerNum;
	  				}
	  			}
	  			
		  		  for(MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()){
		  			if(MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO.getMailStatus())){
		  				isBKDExist=true;
		  			}
		 			if(MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getMailStatus())){
		  				isACPExist=true;
		  			}
		  		  }
	  			
					if ( scannedMailDetailsVO.getContainerNumber() == null
							|| (!(mailFlightSummaryVO.getFlightNumber().equals(scannedMailDetailsVO.getFlightNumber())
									&& mailFlightSummaryVO.getFlightSequenceNumber() == scannedMailDetailsVO
											.getFlightSequenceNumber()))) {
					if( !(isBKDExist && isACPExist)){	
	  				scannedMailDetailsVO.setFoundArrival(true);
	  				scannedMailDetailsVO.setContainerNumber(containerNumber);
	  				if(uldPol!=null&&uldPol.trim().length()>0){
	  					scannedMailDetailsVO.setPol(uldPol);
	  				}//ICRD-318922
	  				
					if(MailConstantsVO.CONST_BULK.equals(containerNumber)){
		  				scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		  			}else{
		  				scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		  			}
					
					}
	  			}
					

	  			//Added by A-5219 for ICRD-256200 end
	  			scannedMailDetailsVO.setProcessPoint(eventCode);
	  			scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  			
	  			if(mailFlightSummaryVO!=null && mailFlightSummaryVO.getAirportCode()!=null){
	  				scannedMailDetailsVO.setAirportCode(mailFlightSummaryVO.getAirportCode());
	  			}else{
	  			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
	  			}

	  			scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
	  			if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
	             scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	             
	             if(mailFlightSummaryVO.getFlightDate()!=null){
	            	 scannedMailDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate()); 
	             }else{
	             scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
	             }
	             
	             
	  			}
	             for(MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()){
	            	 if( isBKDExist && isACPExist && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getMailStatus())){	
			  				scannedMailDetailsVO.setContainerNumber(mailbagVO.getContainerNumber());
			  				scannedMailDetailsVO.setContainerType(mailbagVO.getContainerType());
			  				scannedMailDetailsVO.setPol(mailbagVO.getPol());
			  				scannedMailDetailsVO.setDestination(mailbagVO.getPou());
	            	 }

        	  // if(!scannedMailDetailsVO.isSplitBooking()){
					scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					scannedMailDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
					scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
					if(segSerial > 0)
						scannedMailDetailsVO.setSegmentSerialNumber(segSerial);
					mailbagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					mailbagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					if(segSerial > 0)
						mailbagVO.setSegmentSerialNumber(segSerial);
					mailbagVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
				//}
        	   		if(scannedMailDetailsVO.isFoundArrival()&&uldPol!=null&&uldPol.trim().length()>0){
        	   			mailbagVO.setPol(uldPol);
				}//ICRD-318922
	  				mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
	  				mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
	  				mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
	  				mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
	  				mailbagVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	  			  if(mailFlightSummaryVO.getFlightDate()!=null){
	  				mailbagVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
	  			  	}else{
	  				mailbagVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
	  			   }
	  				mailbagVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  				mailbagVO.setScannedUser(logonAttributes.getUserId());	  	
	  				
	  				
	  			}
           if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
	             for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
	        	  // if(!scannedMailDetailsVO.isSplitBooking()){
		        	   containerVO.setCarrierCode(airlineValidationVO.getAlphaCode());
		        	   containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		        	   containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		        	   if(mailFlightSummaryVO.getFlightDate()!=null){
		        		   containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());	   
		        	   }else{
		        	   containerVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
		        	   }
		        	   containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		        	   if(segSerial > 0)
		        		   containerVO.setSegmentSerialNumber(segSerial);
		        	   containerVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	        	 //  }
	          	   containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
	          	   containerVO.setType(scannedMailDetailsVO.getContainerType());
	  			}
	  			try {
	  				new MailUploadController().saveArrivalFromUpload(scannedMailDetailsVO,logonAttributes);
	  			} catch (MailHHTBusniessException e) {
				log.log(Log.FINE, e.getMessage());
	  			} catch (MailMLDBusniessException e) {
				log.log(Log.FINE, e.getMessage());
	  			}catch (ForceAcceptanceException e) {
				log.log(Log.FINE, e.getMessage());
	  			}
	  			}
	  		}
	  			}
	  		}
	  	
	  	}else if("DLV".equals(eventCode)){
		int segSerial = 1;
	  		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
	  			/*ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
	  					(shipmentSummaryVO,mailFlightSummaryVO.getFlightNumber(),mailFlightSummaryVO.getFlightSequenceNumber());*/
	  			Collection<MailbagVO> dlvMails = new ArrayList<MailbagVO>();
	  			HashMap<String,String> dupCheckMap= new HashMap<String,String>();
	  			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
	  					(shipmentSummaryVO,mailFlightSummaryVO);
	  			scannedMailDetailsVO.setProcessPoint(eventCode);
	  			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			if(
					scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()){
				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
					MailbagPK mailPK = new MailbagPK();
					mailPK.setCompanyCode(mailVO.getCompanyCode());
					mailPK.setMailSequenceNumber(mailVO.getMailSequenceNumber());
					try {
						Mailbag mail = Mailbag.find(mailPK);
						if(mail != null && mail.getFlightNumber() != null && !"-1".equals(mail.getFlightNumber())){
							scannedMailDetailsVO.setCarrierId(mail.getCarrierId());
							scannedMailDetailsVO.setFlightNumber(mail.getFlightNumber());
							scannedMailDetailsVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
							mailFlightSummaryVO.setCarrierId(mail.getCarrierId());
							mailFlightSummaryVO.setFlightNumber(mail.getFlightNumber());
							mailFlightSummaryVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
							mailVO.setCarrierId(mail.getCarrierId());
							mailVO.setFlightNumber(mail.getFlightNumber());
							mailVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
						}
					} catch (FinderException e) {
						log.log(Log.FINE, e.getMessage());
					}
				}
			}
	  			
	  			//Added by A-8149 for ICRD-257715 starts
	  			FlightFilterVO flightFilterVO=null;
	  			
	  			scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  			
	  			for(MailbagVO mailbagVo:scannedMailDetailsVO.getMailDetails()){
	  				
	  				mailbagVo.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  				
	  				
	  				flightFilterVO = new FlightFilterVO();
	  				flightFilterVO.setCompanyCode(mailbagVo.getCompanyCode());
				flightFilterVO.setDirection(FlightFilterVO.INBOUND);
				if(scannedMailDetailsVO.isSplitBooking()){
	  				flightFilterVO.setFlightNumber(mailbagVo.getFlightNumber());
	  				flightFilterVO.setFlightCarrierId(mailbagVo.getCarrierId());
	  				flightFilterVO.setFlightSequenceNumber(mailbagVo.getFlightSequenceNumber());
				}else{
					flightFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					flightFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
					flightFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				}
				
	              if(mailFlightSummaryVO.getAirportCode()!=null){
	            	  flightFilterVO.setStation(mailFlightSummaryVO.getAirportCode());
	              }else{
	            	  flightFilterVO.setStation(logonAttributes.getAirportCode());
	              }
	              
	  	           Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(flightFilterVO);
	  	         
	  	           if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
	  	        	   mailbagVo.setCarrierCode(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getCarrierCode());
	  	        	   
	  	        	   if(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getSta()!=null){
	  	        	   mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getSta());
	  	        	   }else{
	  	        	   mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
	  	        	   }
	  	        	   
	  	          	}
	           ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
				manifestFilterVO.setManifestPrint(false);
				manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
				if(scannedMailDetailsVO.isSplitBooking()){
					manifestFilterVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
					manifestFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
					manifestFilterVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
				}else{
					manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				}
				manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
				manifestFilterVO.setAirportCode(logonAttributes.getAirportCode());
				manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
				ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
				shipmentValidationVO.setOwnerId(shipmentSummaryVO.getOwnerId());
				shipmentValidationVO.setDocumentNumber(shipmentSummaryVO.getDocumentNumber());
				shipmentValidationVO.setDuplicateNumber(shipmentSummaryVO.getDuplicateNumber());
				shipmentValidationVO.setSequenceNumber(shipmentSummaryVO.getSequenceNumber());
				CTOShipmentManifestVO manifestvo = new OperationsFltHandlingProxy().findShipmentForImportManifest(manifestFilterVO,shipmentValidationVO);
				if(manifestvo !=null){
					segSerial = manifestvo.getSegmentSerialNumber();
				}
				if(!scannedMailDetailsVO.isSplitBooking()){
					scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					scannedMailDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
					scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
					scannedMailDetailsVO.setSegmentSerialNumber(segSerial);
					mailbagVo.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					mailbagVo.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					mailbagVo.setSegmentSerialNumber(segSerial);
					mailbagVo.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
					mailbagVo.setPol(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegOrigin());
					 
				}
				if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
						!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
					for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
			        	   if(!scannedMailDetailsVO.isSplitBooking()){
				        	   containerVO.setCarrierCode(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getCarrierCode());
				        	   containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				        	   containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				        	   
				        	   if(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getSta()!=null){
				        	   containerVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getSta());
				        	   }else{
				        	   containerVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
				        	   }
				        	   
				        	   containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				        	   if(segSerial > 0)
				        		   containerVO.setSegmentSerialNumber(segSerial);
				        	   containerVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
			        	   }
			        	   containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			        	   containerVO.setType(scannedMailDetailsVO.getContainerType());
			        	   containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
			        	   containerVO.setAssignedPort(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegOrigin());
			        	   containerVO.setPol(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegOrigin());
			        	   
					}
				}
				//Added as part of bug ICRD-337691 by A-5526 starts
			String nearestAirport=findNearestAirportOfCity(mailbagVo.getCompanyCode(),mailbagVo.getDoe());
			boolean includeDeliveryFlag=true;
			if(logonAttributes.getAirportCode()!=null && !logonAttributes.getAirportCode().equals(nearestAirport) ){
				includeDeliveryFlag=false;    
			}//Added as part of bug ICRD-337691 by A-5526 ends
			//Modified as part of bug ICRD-337691 by A-5526 
				if(!dupCheckMap.containsKey(mailbagVo.getMailbagId()) && includeDeliveryFlag){
					dlvMails.add(mailbagVo);
				}
				dupCheckMap.put(mailbagVo.getMailbagId(), mailbagVo.getMailbagId());
	  			}
	  			
	  			//Added by A-8149 for ICRD-257715 ends
	  			scannedMailDetailsVO.setMailDetails(dlvMails);
	  			
	  			
	  			try {
	  				new MailUploadController().saveDeliverFromUpload(scannedMailDetailsVO,logonAttributes);
	  			} catch (MailHHTBusniessException e) {
				log.log(Log.FINE, e.getMessage());
	  			} catch (MailMLDBusniessException e) {
	  				log.log(Log.FINE, e.getMessage());
	  			} catch (ForceAcceptanceException e) {
	  				log.log(Log.FINE, e.getMessage());
	  			}
	  		}
	  	}      
	  	else if("RMPTRA".equals(eventCode)){    
//Removed TRA event code as it has no relevance in KE business.Also in some of the transactions it caused data corruption also.
	  		performMailAWBTransactionsFlow(mailFlightSummaryVO,"ARR");
	  		FlightFilterVO filghtFilterVO = new FlightFilterVO();
	  		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	  		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	  		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
	  		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	  		filghtFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
	  		filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
	          Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	          
	  	  	log.log(Log.FINE, "flightValidationVOs : "+flightValidationVOs);
	  	  	
	  		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
	  			ScannedMailDetailsVO scannedMailDetailsVO = null;
	  			if(BOOKED_FLIGHT.equals(importMailsForSplitBooking)){	
	  				 scannedMailDetailsVO = new Mailbag().findMailbagsForAWB
	  					(shipmentSummaryVO,mailFlightSummaryVO);
					}else if(FIRST_FLIGHT.equals(importMailsForSplitBooking)) {
						 scannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO,mailFlightSummaryVO);
			  						
					}
	  			
	  			boolean success=true;
	  			
	  	 	  	log.log(Log.FINE, "scannedMailDetailsVO Mail size : "+scannedMailDetailsVO.getMailDetails().size());
	  			
	  	 	  	if(scannedMailDetailsVO.getMailDetails()!=null && !scannedMailDetailsVO.getMailDetails().isEmpty()){
	  	 	  	
	  			scannedMailDetailsVO.setProcessPoint(eventCode);
	  			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());//ICRD-307372
	  			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
	  			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
	  			scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getToCarrierCode());
	  			for(MailbagVO mailbag:scannedMailDetailsVO.getMailDetails()){
	  				//Added as part of BUG ICRD-332154 by A-5526 starts
	  				MailbagPK mailPK = new MailbagPK();
	  				Mailbag mail=null;
					mailPK.setCompanyCode(mailbag.getCompanyCode());
					mailPK.setMailSequenceNumber(mailbag.getMailSequenceNumber());
					try {
						 mail = Mailbag.find(mailPK);
					}catch (FinderException e) {
						mail=null;
					}
					if(mail!=null && "BKD".equals(mail.getLatestStatus())){
						success=false;    
						break;    
					}
					//Added as part of BUG ICRD-332154 by A-5526 ends

	  				mailbag.setScannedPort(logonAttributes.getAirportCode());//ICRD-307372
	  				
	  				 if(flightValidationVOs!=null &&flightValidationVOs.size()>0){
	  					 mailbag.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	  				 }
	  				 mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
	  				 mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	  				 mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
				 if(mailbag.getFlightSequenceNumber() == 0){
					 scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					 mailbag.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				 }
	  				 mailbag.setArrivedFlag(MailConstantsVO.FLAG_YES);
	  				 mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
	  				 mailbag.setFinalDestination(mailbag.getPou());
	  				 mailbag.setInventoryContainer(mailbag.getContainerNumber());
	  				 mailbag.setInventoryContainerType(mailbag.getContainerType());
	  				 mailbag.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);

	  			 }
	  			scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getToCarrierId());
	  			scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getToFlightNumber());
	  			scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getToFlightDate());
	  			scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	  			scannedMailDetailsVO.setOperationTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	  			scannedMailDetailsVO.setScannedContainerDetails(null);
	  	        boolean isWetLeased=false;
	        if(mailFlightSummaryVO.getToFlightNumber()!=null && mailFlightSummaryVO.getToFlightNumber().trim().length()>0){//added by A-7371 for ICRD-266250
	  	        isWetLeased=checkForWetleasedFlight(mailFlightSummaryVO,logonAttributes);//added by A-7371 for ICRD-256262
	        }
	  			//added by A-7371 as part of ICRD-231527 starts ends
	  			if(logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getToCarrierCode()) ||isWetLeased ){
	  				ContainerDetailsVO containerDetailsVO =new ContainerDetailsVO ();
				containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getToCarrierCode());//modified as part of ICRD-266250
				containerDetailsVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
	  				containerDetailsVO.setContainerNumber(mailFlightSummaryVO.getToContainerNumber());
	  				containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
	  				containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
	  				containerDetailsVO.setFlightDate(mailFlightSummaryVO.getToFlightDate());
	  				containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
	  				containerDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
	  				containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getToLegSerialNumber());
	  				containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
	  				containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
	  				containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
	  				//containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);//reassign is not possible to bulk from operations side . A-8061 commented ICRD-307372
	  				if(MailConstantsVO.CONST_BULK.equals(containerDetailsVO.getContainerNumber())){//A-8061 Added ICRD-307372
	  					containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		  			}else{
		  				containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		  			}
	  				containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
	  				//scannedMailDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
	  				scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
	  				success = saveContainerforTransfer(containerDetailsVO,mailFlightSummaryVO);
	  				scannedMailDetailsVO.setScannedContainerDetails(null);
	  			}
	  			scannedMailDetailsVO.setScannedContainerDetails(null);
	  			if(success){
	  			try {
	  			 	log.log(Log.FINE, "saveTransferFromUpload ");
	  				new MailUploadController().saveTransferFromUpload(scannedMailDetailsVO,logonAttributes);
	  			} catch (MailHHTBusniessException e) {
	  				log.log(Log.FINE, e.getMessage());
	  				throw new SystemException(e.getMessage());
	  			} catch (MailMLDBusniessException e) {
	  				log.log(Log.FINE, e.getMessage());
	  				throw new SystemException(e.getMessage());
	  			} catch (MailTrackingBusinessException e) {
	  				log.log(Log.FINE, e.getMessage());
	  				throw new SystemException(e.getMessage());
	  			}catch (ForceAcceptanceException e) {
	  				log.log(Log.FINE, e.getMessage());
	  				throw new SystemException(e.getMessage());
	  			}
	  		}
	  			
	  		}
	  	}
	  	}
	  	log.exiting("BaseXaddonMailController", "performMailAWBTransactions");
	  }

	
}
