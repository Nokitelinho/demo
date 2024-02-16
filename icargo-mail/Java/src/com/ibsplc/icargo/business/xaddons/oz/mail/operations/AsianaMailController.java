/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.oz.mail.operations.AsianaMailController.java
 *
 *	Created by	:	a-7779
 *	Created on	:	23-Aug-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.xaddons.oz.mail.operations;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailDefaultStorageUnitException;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.CTOShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.BaseXaddonMailController;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.xaddons.oz.mail.operations.AsianaMailController.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7779	:	23-Aug-2017	:	Draft
 */
@Module("bsmail")
@SubModule("operations")
public class AsianaMailController extends BaseXaddonMailController{

	private Log log = LogFactory.getLogger("BSMAIL");
	   public static final String STATUS_EXECUTED = "E";
	   public static final String STATUS_AGG_TYPE_LEASE = "L";




/**
 *
 * 	Method		:	AsianaMailController.performMailAWBTransactionsFlow
 *	Added by 	:	a-7779 on 13-Sep-2017
 * 	Used for 	:
 *	Parameters	:	@param mailFlightSummaryVO
 *	Parameters	:	@param eventCode
 *	Parameters	:	@throws SystemException
 *	Return type	: 	void
 */
//@Advice(name = "mail.operations.performMailAWBTransactions" , phase=Phase.POST_INVOKE)
public void performMailAWBTransactionsFlow(MailFlightSummaryVO mailFlightSummaryVO,String eventCode)
		throws SystemException{
	log.entering("AsianaMailController", "performMailAWBTransactions");
	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	mailFlightSummaryVO.setEventCode(eventCode);//a-8061 added as part of  ICRD-249916
	if("ACP".equals(eventCode)){
		if(mailFlightSummaryVO!=null && mailFlightSummaryVO.getShipmentSummaryVOs()!=null && mailFlightSummaryVO.getShipmentSummaryVOs().size()>0){

			//fetching carriercode using carrierId modified for ICRD-236397
	        AirlineValidationVO airlineValidationVO;

			airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
					mailFlightSummaryVO.getCarrierId());
			//fetching flight details
            FlightFilterVO filghtFilterVO = new FlightFilterVO();
            filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
            filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
            filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
             filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
            filghtFilterVO.setDirection(FlightFilterVO.OUTBOUND);
            Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
            List<String> awbKey = new ArrayList<String>();
            Set<String> allcontKey = new HashSet<String>();
            Set<String> spccontKey = new HashSet<String>();
            Collection<MailbagVO> reassignedMails = new ArrayList<MailbagVO>();
            Collection<MailbagVO> acpMails = new ArrayList<MailbagVO>();
            ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
			for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
				boolean blockReceivedResdit = false;
				if(MailConstantsVO.FLAG_YES.equals(shipmentSummaryVO.getTranshipmentFlag()) ){//added by A-7371 as part of ICRD-256798
					blockReceivedResdit = true;
				}
				scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
						(shipmentSummaryVO,mailFlightSummaryVO);

				boolean reassignFlag=findMailBagsforReassign(scannedMailDetailsVO,shipmentSummaryVO,mailFlightSummaryVO);
				ScannedMailDetailsVO scnVOForReassign = new ScannedMailDetailsVO();
				/*String cargoOpsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
						.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
						.append("~").append(shipmentSummaryVO.getDuplicateNumber())
						.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
				awbKey.add(cargoOpsKey);*/
				if(scannedMailDetailsVO!=null){
				scannedMailDetailsVO.setProcessPoint(eventCode);
				//Modified as part of ICRD-337678
				scannedMailDetailsVO.setAirportCode(mailFlightSummaryVO.getAirportCode()!=null
						&& mailFlightSummaryVO.getAirportCode().trim().length()>0?
								mailFlightSummaryVO.getAirportCode(): logonAttributes.getAirportCode());
				if(mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())){
				scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
				scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
				}else{
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
				}
				//Modified as part of ICRD-337678
				scannedMailDetailsVO.setPol(mailFlightSummaryVO.getAirportCode()!=null
						&& mailFlightSummaryVO.getAirportCode().trim().length()>0?
								mailFlightSummaryVO.getAirportCode(): logonAttributes.getAirportCode());
				scannedMailDetailsVO.setMailSource("EXPFLTFIN_ACPMAL");
				scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
				if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
       scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
       scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
				}
				
				Collection<Long> malseqnums=new ArrayList<Long>();
				if(scannedMailDetailsVO.getMailDetails()!=null && scannedMailDetailsVO.getMailDetails().size()>0){
					ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
					manifestFilterVO.setManifestPrint(false);
					manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
					manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					//Modified as part of ICRD-337678
					manifestFilterVO.setPointOfLading(mailFlightSummaryVO.getAirportCode()!=null
							&& mailFlightSummaryVO.getAirportCode().trim().length()>0?
									mailFlightSummaryVO.getAirportCode(): logonAttributes.getAirportCode());
					Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
					Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<ShipmentManifestVO>();
					if(uldManifestVOs!=null && uldManifestVOs.size()>0){
						for(UldManifestVO uldManifestVO : uldManifestVOs){
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
									if(!scannedMailDetailsVO.isSplitBooking()){
										scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
										scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
										scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
										scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
										scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										mailBagVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
										mailBagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
										mailBagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
										mailBagVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
										mailBagVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
									}
									if(mailBagVO.getContainerNumber()!=null){
										allcontKey.add(mailBagVO.getContainerNumber());
									}
									mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
									scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());
									if("BULK".equals(shipmentManifestVO.getUldNumber())){
										mailBagVO.setContainerType("B");
										scannedMailDetailsVO.setContainerType("B");
									}else{
										mailBagVO.setContainerType("U");
										scannedMailDetailsVO.setContainerType("U");
										scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());//a-8061 added for ICRD-255007
									}
	                               if(reassignFlag){//added by A-7371 as part of ICRD-264253
	                            	   reassignedMails.add(mailBagVO);
									}
	                               //ICRD-347367- wrong POU was set for bulk
	                               if(MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())){
										scannedMailDetailsVO.setDestination(scannedMailDetailsVO.getPou());
									}
									if(MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailBagVO.getMailStatus())){
										 boolean duplicateReassignFlag=false;//added by A-7371 as part of ICRD-275788
		                            	   if(reassignedMails!=null && reassignedMails.size()>0){
		                            		   if(malseqnums.contains(mailBagVO.getMailSequenceNumber())){
		                            			   duplicateReassignFlag=true;
		                            		   }
		                            	   }
		                            	   if(!duplicateReassignFlag){
										reassignedMails.add(mailBagVO);
                            			   malseqnums.add(mailBagVO.getMailSequenceNumber());
		                            	   }
										mailBagVO.setCarrierCode(scannedMailDetailsVO.getCarrierCode());
										mailBagVO.setLegSerialNumber(scannedMailDetailsVO.getLegSerialNumber());
									}else if(!reassignFlag){
										if(scannedMailDetailsVO.getFlightNumber() ==  null){
											scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
											scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
											scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
											scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
											scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
											scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
											mailBagVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
											mailBagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
											mailBagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
											mailBagVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
											mailBagVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
										}
										acpMails.add(mailBagVO);
									}

								}
							}
						}
					}
				}

				}


			try {
				if(!acpMails.isEmpty() && scannedMailDetailsVO.getMailDetails() != null
						&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
				new MailUploadController().saveAcceptanceFromUpload(scannedMailDetailsVO,logonAttributes);
				acpMails = new ArrayList<MailbagVO>();
				}
				if(!reassignedMails.isEmpty()){
					try {
						BeanUtils.copyProperties(scnVOForReassign, scannedMailDetailsVO);
						scnVOForReassign.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);
						scnVOForReassign.setMailDetails(reassignedMails);
							ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
							containerDetailsVO.setCompanyCode(scnVOForReassign.getCompanyCode());
							containerDetailsVO.setContainerNumber(scnVOForReassign.getContainerNumber());
							containerDetailsVO.setContainerType(scnVOForReassign.getContainerType());
							containerDetailsVO.setCarrierCode(scnVOForReassign.getCarrierCode());
							containerDetailsVO.setCarrierId(scnVOForReassign.getCarrierId());
							containerDetailsVO.setFlightNumber(scnVOForReassign.getFlightNumber());
							containerDetailsVO.setFlightSequenceNumber(
									scnVOForReassign.getFlightSequenceNumber());
							containerDetailsVO.setLegSerialNumber(scnVOForReassign.getLegSerialNumber());
							containerDetailsVO.setSegmentSerialNumber(
									scnVOForReassign.getSegmentSerialNumber());
							containerDetailsVO.setOperationFlag(scnVOForReassign.getOperationFlag());
							containerDetailsVO.setContainerOperationFlag("I");
							containerDetailsVO.setPol(scnVOForReassign.getPol());
							containerDetailsVO.setPou(scnVOForReassign.getPou());
							containerDetailsVO.setDestination(scnVOForReassign.getDestination());
							containerDetailsVO.setFlightDate(scnVOForReassign.getFlightDate());
							containerDetailsVO.setOflToRsnFlag(true);
							scnVOForReassign.setValidatedContainer(containerDetailsVO);
					/*		if(scnVOForReassign.getScannedContainerDetails() != null &&
								!scnVOForReassign.getScannedContainerDetails().isEmpty()){
								for(ContainerVO contVO : scnVOForReassign.getScannedContainerDetails()){
									contVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
									contVO.setOflToRsnFlag(true);
								}
							}*///commented as part of ICRD-264283
							//added by A-7371 as part of ICRD-264283 starts
							scnVOForReassign.setScannedContainerDetails(null);
							//Reassigning a  mailbag requires container information in mail module, we need to persist container details as well as to-flight details, entry in MALFLT and MALFLTSEG
							boolean success=false;//added by A-7371
							success = saveContainerforReassign(containerDetailsVO,mailFlightSummaryVO);
                           if(success){
							new MailUploadController().saveAndProcessMailBags(scnVOForReassign);
                           }
							//added by A-7371 as part of ICRD-264283 ends
					} catch (IllegalAccessException e) {
						//throw new SystemException(e.getMessage());
						log.log(Log.SEVERE, e.getMessage());
					} catch (InvocationTargetException e) {
						log.log(Log.SEVERE, e.getMessage());
					}
				}
				checkforATDCaptureFlight(mailFlightSummaryVO);//added by A-7371 as part of ICRD-233074 for triggering uplift resdit for ATD capture in Export Manifest Screen
				reassignedMails = new ArrayList<MailbagVO>();
				//success =true;
			} catch (MailHHTBusniessException e) {
				log.log(Log.SEVERE, e.getMessage());
				reassignedMails = new ArrayList<MailbagVO>();
			} catch (MailMLDBusniessException e) {
				log.log(Log.SEVERE, e.getMessage());
				reassignedMails = new ArrayList<MailbagVO>();
			}catch (MailTrackingBusinessException e) {
				log.log(Log.SEVERE, e.getMessage());
				reassignedMails = new ArrayList<MailbagVO>();
			} catch(RemoteException e){
				log.log(Log.SEVERE, e.getMessage());
				reassignedMails = new ArrayList<MailbagVO>();
			}catch(ForceAcceptanceException e){
				log.log(Log.SEVERE, e.getMessage());
				reassignedMails = new ArrayList<MailbagVO>();
			}



		}
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
								}catch(FinderException exception){
									  log.log(Log.SEVERE, exception.getMessage());
								}
								mailbags.add(mailVO);

							}
						}
					}
					scnMailDetailsVO.setMailDetails(mailbags);
					if(scnMailDetailsVO.getContainerNumber() != null &&
							!scnMailDetailsVO.getContainerNumber().equals(scannedMailDetailsVO.getContainerNumber())){
						scnMailDetailsVO.setContOffloadReq(true);
					}
				}
				if(offloadReq){
				scnMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				scnMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				scnMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
				scnMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				scnMailDetailsVO.setScannedUser(logonAttributes.getUserId());
				scnMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
				scnMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
				if(scnMailDetailsVO.getScannedContainerDetails() != null &&
						!scnMailDetailsVO.getScannedContainerDetails().isEmpty()){
					String containerNumber = "";
					Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
					for(ContainerVO containerVO : scnMailDetailsVO.getScannedContainerDetails()){
						if(!containerNumber.equals(scnMailDetailsVO.getContainerNumber())){
							containerNumber = scnMailDetailsVO.getContainerNumber();
						containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
						containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
						containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
						containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
						containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
						containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
						containerVO.setSegmentSerialNumber(scnMailDetailsVO.getSegmentSerialNumber());
						containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
						containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
						containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
						containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
						containerVO.setOffload(true);
						containerVO.setBags(count);
						containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
						containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
						containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						containerVO.setLastUpdateUser(logonAttributes.getUserId());
						containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
						contVOs.add(containerVO);
						}
					}
					scnMailDetailsVO.setScannedContainerDetails(contVOs);//added by A-7371 as part of ICRD-264283
				}
					new MailUploadController().saveAndProcessMailBags(scnMailDetailsVO);



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
			else{
				int segSerial = 1;
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(null,mailFlightSummaryVO);
			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
			scannedMailDetailsVO.setContOffloadReq(true);
			int count = 0;
			double weight= 0.0;
				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
					count++;
					weight = weight+ mailVO.getWeight().getDisplayValue();
					mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					mailVO.setScannedUser(logonAttributes.getUserId());
					mailVO.setLastUpdateUser(logonAttributes.getUserId());
					mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
					mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
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
					}catch(Exception exception){
						log.log(Log.SEVERE, exception.getMessage());
					}
				}

			if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
				String containerNumber = "";
				Collection<ContainerVO> contVOs = new ArrayList<ContainerVO>();
				for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
					if(!containerNumber.equals(scannedMailDetailsVO.getContainerNumber())){
						containerNumber = scannedMailDetailsVO.getContainerNumber();
					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setSegmentSerialNumber(segSerial);
					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
					containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
					containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
					containerVO.setOffload(true);
					containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
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
			checkforATDCaptureFlight(mailFlightSummaryVO);
		}
			}

	}else if("ARR".equals(eventCode)){
           //fetching carriercode using carrierId
	        AirlineValidationVO airlineValidationVO;
	        //Commented as part of ICRD-351981- this collection was holding older mails while in iteration, from different awbs
	        //Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
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
			/*
			 * Added as part of ICRD-351981- while arrival all shipments from flight was considered rather than 
			 * the arrived awbs, this maap check is to make sure only the arrived awbs are considered for mail save
			 */
			String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
  					.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
  					.append("~").append(shipmentSummaryVO.getDuplicateNumber())
  					.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();
  			if(mailFlightSummaryVO.getUldAwbMap()!=null && mailFlightSummaryVO.getUldAwbMap().containsKey(awbKey)){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			if(scannedMailDetailsVO != null && scannedMailDetailsVO.getMailDetails() != null
					&& !scannedMailDetailsVO.getMailDetails().isEmpty()){
				 //Added as part of ICRD-351981- this collection is for each awb
				  Collection<MailbagVO> mailbags = new ArrayList<MailbagVO>();
				//Added as part of ICRD-351981-Already arrived mails need not be arrived again
				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getStatus())){
  					continue;
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
				//Added as part of ICRD-347367- wrong airpot was going when arrival done via Msg or Job
				manifestFilterVO.setAirportCode(mailFlightSummaryVO.getAirportCode()!=null
						&& mailFlightSummaryVO.getAirportCode().trim().length()>0?
								mailFlightSummaryVO.getAirportCode(): logonAttributes.getAirportCode());
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
/*				if(MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getStatus())){
					continue;
				}*/
				//Commented as part of ICRD-351981- code moved above
			/*String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix())
					.append("~").append(shipmentSummaryVO.getMasterDocumentNumber())
					.append("~").append(shipmentSummaryVO.getDuplicateNumber())
					.append("~").append(shipmentSummaryVO.getSequenceNumber()).toString();*/
			//Added by A-5219 for ICRD-256200 start
            String containerNumber = "";
            String uldPol="";
            String uldArr[]=null;
			if(mailFlightSummaryVO.getUldAwbMap() != null && !mailFlightSummaryVO.getUldAwbMap().isEmpty()){
				//containerNumber = mailFlightSummaryVO.getUldAwbMap().get(awbKey);
				uldArr=	mailFlightSummaryVO.getUldAwbMap().get(awbKey).split("~");
	  			containerNumber =uldArr[0];
	  			if(containerNumber.length()>1){	 //ICRD-351560
	  				uldPol=uldArr[1];
	  			}
			}
			//ICRD-355451
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
			if(scannedMailDetailsVO.getContainerNumber() == null){
				scannedMailDetailsVO.setFoundArrival(true);
				scannedMailDetailsVO.setContainerNumber(containerNumber);
				//Added as part of ICRD-351560
				if(uldPol!=null&&uldPol.trim().length()>0){
  					scannedMailDetailsVO.setPol(uldPol);
  				}
			}
			//Added by A-5219 for ICRD-256200 end
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setAirportCode(mailFlightSummaryVO.getAirportCode()!=null
					&& mailFlightSummaryVO.getAirportCode().trim().length()>0?
							mailFlightSummaryVO.getAirportCode(): logonAttributes.getAirportCode());
			/*
			 *  Added as part of ICRD-347367- outer if added because
			 *  already scanned Bulk container [ACP] was replaced with ULD container type as part of arrival.
			 *  So will set countainer type from containerNumber if its null and found arrival 
			 */
			if(scannedMailDetailsVO.getContainerType()==null && scannedMailDetailsVO.isFoundArrival()) {
			if(MailConstantsVO.CONST_BULK.equals(containerNumber)){
				scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			}else{
				scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}
			}

			scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());
			if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
           scannedMailDetailsVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
           scannedMailDetailsVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
			}
           for(MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()){
        	   if(!scannedMailDetailsVO.isSplitBooking()){
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
				}
        	   //Added as part of ICRD-351560
        	   if(scannedMailDetailsVO.isFoundArrival()&&uldPol!=null&&uldPol.trim().length()>0){
   	   				mailbagVO.setPol(uldPol);
				}
				mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
				mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
				mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
				mailbagVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
				mailbagVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
				MailbagPK mailPK = new MailbagPK();
				mailPK.setCompanyCode(mailbagVO.getCompanyCode());
				mailPK.setMailSequenceNumber(mailbagVO.getMailSequenceNumber());
				try {
					Mailbag mail = Mailbag.find(mailPK);
					if(mail != null){
						if(mail.getFlightNumber() != null && !"-1".equals(mail.getFlightNumber())){
							mailbags.add(mailbagVO);
						}
					}
				}catch(Exception e){
					log.log(Log.FINE, e.getMessage());
				}
			}
           if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
           for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
	        	   if(!scannedMailDetailsVO.isSplitBooking()){
		        	   containerVO.setCarrierCode(airlineValidationVO.getAlphaCode());
		        	   containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		        	   containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		        	   containerVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
		        	   containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		        	   if(segSerial > 0)
		        		   containerVO.setSegmentSerialNumber(segSerial);
		        	   containerVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
	        	   }
        	   containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
        	   containerVO.setType(scannedMailDetailsVO.getContainerType());
				}
			}
           scannedMailDetailsVO.setMailDetails(mailbags);
			try {
				new MailUploadController().saveArrivalFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				log.log(Log.FINE, e.getMessage());
			} catch (MailMLDBusniessException e) {
				log.log(Log.FINE, e.getMessage());
				}catch(ForceAcceptanceException e){
					log.log(Log.SEVERE, e.getMessage());
				}
			}
		}
	}
	}else if("DLV".equals(eventCode)){
		int segSerial = 1;
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			/*ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO.getFlightNumber(),mailFlightSummaryVO.getFlightSequenceNumber());*/
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
			if(scannedMailDetailsVO.getFlightNumber() == null &&
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
			for(MailbagVO mailbagVo:scannedMailDetailsVO.getMailDetails()){

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
	           Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(flightFilterVO);

	           if(flightValidationVOs!=null && !flightValidationVOs.isEmpty()){
	        	   mailbagVo.setCarrierCode(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getCarrierCode());
	        	   mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
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
				}
				if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
						!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
					for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
			        	   if(!scannedMailDetailsVO.isSplitBooking()){
				        	   containerVO.setCarrierCode(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getCarrierCode());
				        	   containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				        	   containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				        	   containerVO.setFlightDate(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getFlightDate());
				        	   containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				        	   if(segSerial > 0)
				        		   containerVO.setSegmentSerialNumber(segSerial);
				        	   containerVO.setLegSerialNumber(((ArrayList<FlightValidationVO>)flightValidationVOs).get(0).getLegSerialNumber());
			        	   }
			        	   containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			        	   containerVO.setType(scannedMailDetailsVO.getContainerType());
					}
				}
			}
			//Added by A-8149 for ICRD-257715 ends



			try {
				new MailUploadController().saveDeliverFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				log.log(Log.FINE, e.getMessage());
			} catch (MailMLDBusniessException e) {
				log.log(Log.FINE, e.getMessage());
			}catch(ForceAcceptanceException e){
				log.log(Log.SEVERE, e.getMessage());
			}
		}
	}else if("TRA".equals(eventCode)){
		//added by A-7371 as part of ICRD-231527 starts
		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		filghtFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
        Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			boolean success=true;
			scannedMailDetailsVO.setProcessPoint(eventCode);


			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getToCarrierCode());
			for(MailbagVO mailbag:scannedMailDetailsVO.getMailDetails()){

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
				containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);//reassign is not possible to bulk from operations side
				containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
				//scannedMailDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
				scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
				success = saveContainerforTransfer(containerDetailsVO,mailFlightSummaryVO);
				scannedMailDetailsVO.setScannedContainerDetails(null);
			}
			scannedMailDetailsVO.setScannedContainerDetails(null);
			if(success){
			try {
				new MailUploadController().saveTransferFromUpload(scannedMailDetailsVO,logonAttributes);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailTrackingBusinessException e) {
				throw new SystemException(e.getMessage());
			}catch(ForceAcceptanceException e){
				throw new SystemException(e.getMessage());
			}
		}
	}
	}//OFL condition added for ICRD-253863 by A-5219
	else if (MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(eventCode)){
		for(ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()){
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails
					(shipmentSummaryVO,mailFlightSummaryVO);
			scannedMailDetailsVO.setProcessPoint(eventCode);
			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setMailSource("OPSOFL");
			int count = 0;
			double weight= 0.0;
			if(scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()){
				for(MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()){
					count++;
					if(mailVO.getWeight() != null){
						weight = weight+ mailVO.getWeight().getDisplayValue();
					}
					mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					mailVO.setScannedUser(logonAttributes.getUserId());
					mailVO.setLastUpdateUser(logonAttributes.getUserId());
					mailVO.setOffloadedReason("62");
					mailVO.setOffloadedRemarks("Unknown");
					mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					mailVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}
			}
			if(scannedMailDetailsVO.getScannedContainerDetails() != null &&
					!scannedMailDetailsVO.getScannedContainerDetails().isEmpty()){
				for(ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()){
					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					containerVO.setFinalDestination(shipmentSummaryVO.getDestination());
					containerVO.setOffloadedReason("62");
					containerVO.setOffloadedRemarks("Unknown");
					containerVO.setOffload(true);
					containerVO.setBags(count);
					containerVO.setWeight(new Measure(UnitConstants.WEIGHT,weight));
					containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					containerVO.setLastUpdateTime( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
					containerVO.setLastUpdateUser(logonAttributes.getUserId());
					containerVO.setScannedDate( new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
				}
			}
			try {
				new MailUploadController().saveAndProcessMailBags(scannedMailDetailsVO);
			} catch (MailHHTBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailMLDBusniessException e) {
				throw new SystemException(e.getMessage());
			} catch (MailTrackingBusinessException e) {
				throw new SystemException(e.getMessage());
			} catch(RemoteException e){
				throw new SystemException(e.getMessage());
		    }catch(ForceAcceptanceException e){
			throw new SystemException(e.getMessage());
		}
	}
}
	log.exiting("AsianaMailController", "performMailAWBTransactions");
}


/**
 * @author A-7371
 * @param containerDetailsVO
 * @param mailFlightSummaryVO
 * @return
 * @throws SystemException
 */
private boolean saveContainerforReassignforAsiana(ContainerDetailsVO containerDetailsVO,
		MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {


	LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
			.getLogonAttributesVO();

	Collection<ContainerDetailsVO> containers = new ArrayList<ContainerDetailsVO>();
	containerDetailsVO.setAcceptedFlag("Y");
	containerDetailsVO.setArrivedStatus("N");
	containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
	containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
	containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
	containers.add(containerDetailsVO);

	MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
	mailAcceptanceVO.setFlightCarrierCode(mailFlightSummaryVO.getCarrierCode());
	mailAcceptanceVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
	mailAcceptanceVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
	mailAcceptanceVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
	mailAcceptanceVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
	mailAcceptanceVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
	mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
	mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
	mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
	mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
	mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
	mailAcceptanceVO.setPreassignNeeded(false);
	mailAcceptanceVO.setDestination(mailFlightSummaryVO.getFinalDestination());
	mailAcceptanceVO.setContainerDetails(containers);


	 boolean success = false;
		Transaction tx = null;
		TransactionProvider tm = PersistenceController.getTransactionProvider();
		tx = tm.getNewTransaction(true);
				try {
					new MailController().saveAcceptanceDetails(mailAcceptanceVO);
					success=true;
				} catch (DuplicateMailBagsException | FlightClosedException
						| ContainerAssignmentException
						| InvalidFlightSegmentException | ULDDefaultsProxyException
						| DuplicateDSNException | CapacityBookingProxyException
						| MailBookingException | MailDefaultStorageUnitException e) {
					throw new SystemException(e.getMessage());
				}
		finally {
	        try{
	               if (success) {
	                      tx.commit();
	                     return success;
	               }
				tx.rollback();
	        } catch(OptimisticConcurrencyException concurrencyException){
	        throw new SystemException(concurrencyException.getErrorCode(), concurrencyException);
	        }

		}
		return success;
}
/**
 * @author A-7371
 * @return
 * @throws SystemException
 */
/*private boolean findMailBagsforReassign(ScannedMailDetailsVO scannedMailDetailsVO,ShipmentSummaryVO shipmentSummaryVO,MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {

	here we find entire mailbags under a AWB with ACP status , what we do is that while when reassign transaction comes,
	we will compare the flights and if its is different flight to be which finalise transaction is undergoing with ACP status
	then it is Reassign scenario 

	List<MailbagVO> mailbagVOs=null;
	try {
		mailbagVOs=Mailbag.findMailBagsforReassign(shipmentSummaryVO,mailFlightSummaryVO);
	} catch (PersistenceException | SystemException e) {

		 throw new SystemException(e.getMessage());
	}
     if(scannedMailDetailsVO.getMailDetails()!=null  && scannedMailDetailsVO.getMailDetails().size()>0
                       && mailbagVOs!=null && mailbagVOs.size()>0){
	            for(MailbagVO mailbagInTransaction:scannedMailDetailsVO.getMailDetails()){

		            for(MailbagVO mailbag:mailbagVOs){
		             if(mailbag.getMailSequenceNumber()==mailbagInTransaction.getMailSequenceNumber()){

				           if(mailbag.getFlightNumber()!=null&&mailbagInTransaction.getFlightNumber()!=null
						       && !mailbag.getFlightNumber().equals(mailbagInTransaction.getFlightNumber())){
					         return true;
				            }else if(mailbag.getFlightSequenceNumber()!=mailbagInTransaction.getFlightSequenceNumber()){
					        return true;
				             }
		               }
	            }
	     }
    }

	return false;
}*/

}
