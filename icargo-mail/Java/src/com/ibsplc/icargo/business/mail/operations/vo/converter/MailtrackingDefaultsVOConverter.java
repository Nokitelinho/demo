/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter.java
 *
 *	Created by	:	a-4809
 *	Created on	:	Jul 31, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo.converter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.customs.defaults.vo.CustomsDefaultsFilterVO;
import com.ibsplc.icargo.business.customs.defaults.vo.CustomsDefaultsVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditMessageVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDAtAirportVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagInULDForSegmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-4809	:	Jul 31, 2014	:	Draft
 */
public class MailtrackingDefaultsVOConverter {

	private static final Log log = LogFactory.getLogger("MAILTRACKING DEFAULTS");
	private static final String FLAG_YES = "Y";
	private static final String FLAG_NO = "N";

	/**
	 * 	Method		:	MailtrackingDefaultsVOConverter.convertToMailbagInULDForSegmentVOs
	 *	Added by 	:	a-4809 on Jul 31, 2014
	 * 	Used for 	:   Vo convertion from MailbagInULDAtAirportVO to MailbagInULDForSegmentVO
	 *	Parameters	:	@param mailbagInULDAtAirportVOs
	 *	Parameters	:	@param airportCode
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	Collection<MailbagInULDForSegmentVO>
	 */
	public static Collection<MailbagInULDForSegmentVO> convertToMailbagInULDForSegmentVOs(Collection<MailbagInULDAtAirportVO> mailbagInULDAtAirportVOs,String airportCode)
			throws SystemException{
		log.entering("MailtrackingDefaultsVOConverter", "convertToMailbagInULDForSegmentVOs");
		Collection<MailbagInULDForSegmentVO> mailbagsInULDSeg = new ArrayList<MailbagInULDForSegmentVO>();
		for (MailbagInULDAtAirportVO mailbagInULDArp : mailbagInULDAtAirportVOs) {
			MailbagInULDForSegmentVO mailbagInULDSeg = new MailbagInULDForSegmentVO();
			mailbagInULDSeg.setMailId(mailbagInULDArp.getMailId());
			mailbagInULDSeg.setContainerNumber(mailbagInULDArp.getContainerNumber());
			mailbagInULDSeg.setDamageFlag(mailbagInULDArp.getDamageFlag());
			mailbagInULDSeg.setScannedDate(mailbagInULDArp.getScannedDate());
			mailbagInULDSeg.setWeight(mailbagInULDArp.getWeight());
			mailbagInULDSeg.setMailClass(mailbagInULDArp.getMailClass());
			mailbagInULDSeg.setMailSubclass(mailbagInULDArp.getMailSubclass());
			mailbagInULDSeg.setMailCategoryCode(mailbagInULDArp.getMailCategoryCode());
			mailbagInULDSeg.setTransferFromCarrier(mailbagInULDArp.getTransferFromCarrier());
			mailbagInULDSeg.setSealNumber(mailbagInULDArp.getSealNumber());
			mailbagInULDSeg.setAcceptanceFlag(FLAG_YES);
			mailbagInULDSeg.setArrivalFlag(FLAG_NO);
			mailbagInULDSeg.setDeliveredStatus(FLAG_NO);
			mailbagInULDSeg.setMraStatus(FLAG_NO);
			mailbagInULDSeg.setScannedPort(airportCode);
			mailbagInULDSeg.setScannedDate(new LocalDate(airportCode,Location.ARP, true));
			mailbagInULDSeg.setMailClass(mailbagInULDArp.getMailClass());
			mailbagInULDSeg.setMailSequenceNumber(mailbagInULDArp.getMailSequenceNumber());
			mailbagsInULDSeg.add(mailbagInULDSeg);
		}
		log.log(Log.FINE, "MailbagInULDForSegmentVO constructed :-",mailbagsInULDSeg);
		log.exiting("MailtrackingDefaultsVOConverter", "convertToMailbagInULDForSegmentVOs");
		return mailbagsInULDSeg;
	}
	/**
	 *
	 * @param containerVO
	 * @return
	 * @throws SystemException
	 */
	public static ContainerDetailsVO convertToContainerDetails(ContainerVO containerVO)
	throws SystemException{
		log.entering("MailtrackingDefaultsVOConverter", "convertToContainerDetails");
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setAcceptedFlag(containerVO.getAcceptanceFlag());
		containerDetailsVO.setArrivedStatus(containerVO.getArrivedStatus());
		containerDetailsVO.setAssignedUser(containerVO.getAssignedUser());
		containerDetailsVO.setAssignmentDate(containerVO.getAssignedDate());
		//containerDetailsVO.setBellyCartId(containerVO.getellyCartId);
		containerDetailsVO.setCarrierCode(containerVO.getCarrierCode());
		containerDetailsVO.setCompanyCode(containerVO.getCompanyCode());
		containerDetailsVO.setContainerJnyId(containerVO.getContainerJnyID());
		containerDetailsVO.setContainerNumber(containerVO.getContainerNumber());
		containerDetailsVO.setContainerOperationFlag(containerVO.getOperationFlag());
		containerDetailsVO.setContainerType(containerVO.getType());
		containerDetailsVO.setDeliveredStatus(containerVO.getDeliveredStatus());
		//containerDetailsVO.setDeliverPABuiltContainer(containerVO.getPABuiltContainer);
		//containerDetailsVO.setDesptachDetailsVOs(containerVO.get);
		//containerDetailsVO.setDestination(containerVO.get);
		//containerDetailsVO.setDsnVOs(containerVO.getdsnVOs);
		//containerDetailsVO.setFlagPAULDResidit(containerVO.getflagPAULDResidit);
		//containerDetailsVO.setFlightDate(flightDate);
		containerDetailsVO.setFlightNumber(containerVO.getFlightNumber());
		containerDetailsVO.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
		containerDetailsVO.setIntact(containerVO.getIntact());
		containerDetailsVO.setLastUpdateTime(containerVO.getLastUpdateTime());
		containerDetailsVO.setLegSerialNumber(containerVO.getLegSerialNumber());
		containerDetailsVO.setLocation(containerVO.getLocationCode());
		//containerDetailsVO.setMailDetailPageVOs(containerVO.getmailDetailPageVOs);
		//containerDetailsVO.setMailDetails(containerVO.getmailDetails);
		//containerDetailsVO.setMailSummaryVOs(mailSummaryVOs);
		containerDetailsVO.setOffloadFlag(containerVO.getOffloadFlag());
		containerDetailsVO.setOnwardFlights(containerVO.getOnwardFlights());
		//containerDetailsVO.setOnwardRoutingForSegmentVOs(containerVO.getOnwardRoutings());
		containerDetailsVO.setOperationFlag(containerVO.getOperationFlag());
		containerDetailsVO.setOwnAirlineCode(containerVO.getOwnAirlineCode());
		containerDetailsVO.setPaBuiltFlag(containerVO.getPaBuiltFlag());
		containerDetailsVO.setPaCode(containerVO.getPaCode());
		//containerDetailsVO.setPol(containerVO.getpol);
		containerDetailsVO.setPou(containerVO.getPou());
		containerDetailsVO.setPreassignFlag(containerVO.isPreassignNeeded());
		containerDetailsVO.setReassignFlag(containerVO.isReassignFlag());
		//containerDetailsVO.setReceivedBags(containerVO.getreceivedBags);
		//containerDetailsVO.setReceivedWeight(containerVO.getreceivedWeight);
		containerDetailsVO.setReleasedFlag(containerVO.getReleasedFlag());
		containerDetailsVO.setRemarks(containerVO.getRemarks());
		//containerDetailsVO.setRoute(containerVO.getroute);
		containerDetailsVO.setScanDate(containerVO.getScannedDate());
		containerDetailsVO.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());
		containerDetailsVO.setTotalBags(containerVO.getBags());
		containerDetailsVO.setTotalWeight(containerVO.getWeight());
		containerDetailsVO.setTransactionCode(containerVO.getTransactionCode());
		containerDetailsVO.setTransferFlag(containerVO.getTransferFlag());
		//containerDetailsVO.setTransferFromCarrier(containerVO.getransferFromCarrier);
		containerDetailsVO.setTransitFlag(containerVO.getTransitFlag());
		containerDetailsVO.setUldLastUpdateTime(containerVO.getULDLastUpdateTime());
		containerDetailsVO.setWareHouse(containerVO.getWarehouseCode());
		containerDetailsVO.setCarrierId(containerVO.getCarrierId());
		
		log.entering("MailtrackingDefaultsVOConverter", "convertToContainerDetails");
		return containerDetailsVO;
	}


	public static MailbagVO convertToMailBagVO(DespatchDetailsVO despatchDetailsVO){
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId(createMailBag(despatchDetailsVO));
		mailbagVO.setContainerNumber(despatchDetailsVO.getContainerNumber());
		mailbagVO.setCarrierId(despatchDetailsVO.getCarrierId());
		mailbagVO.setFlightNumber(despatchDetailsVO.getFlightNumber());
		mailbagVO.setFlightSequenceNumber(despatchDetailsVO.getFlightSequenceNumber());
		mailbagVO.setUldNumber(despatchDetailsVO.getUldNumber());
		mailbagVO.setAcceptedWeight(despatchDetailsVO.getAcceptedWeight());
		mailbagVO.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
		mailbagVO.setMailClass(despatchDetailsVO.getMailClass());
		mailbagVO.setMailSubclass(despatchDetailsVO.getMailSubclass());
		mailbagVO.setSegmentSerialNumber(despatchDetailsVO.getSegmentSerialNumber());
		mailbagVO.setScannedDate(despatchDetailsVO.getAcceptedDate());
		mailbagVO.setScannedPort(despatchDetailsVO.getAirportCode());
		mailbagVO.setOperationalFlag(despatchDetailsVO.getOperationalFlag());
		mailbagVO.setPaBuiltFlag(despatchDetailsVO.getPaBuiltFlag());
		mailbagVO.setPou(despatchDetailsVO.getPou());
		mailbagVO.setScannedUser(despatchDetailsVO.getAcceptedUser());
		mailbagVO.setAcceptedBags(despatchDetailsVO.getAcceptedBags());
		mailbagVO.setDespatch(true);
		return mailbagVO;


	}

	public static String createMailBag(DespatchDetailsVO despatchDetailsVO){
		StringBuilder mailBagid=new StringBuilder();
		mailBagid.append(despatchDetailsVO.getOriginOfficeOfExchange())
		.append(despatchDetailsVO.getDestinationOfficeOfExchange())
		.append(despatchDetailsVO.getMailCategoryCode())
		.append(despatchDetailsVO.getMailSubclass())
		.append(despatchDetailsVO.getYear())
		.append(despatchDetailsVO.getDsn());
		return mailBagid.toString();
	}

	public static String createMailBag(DSNVO dsnvo){
		StringBuilder mailBagid=new StringBuilder();
		mailBagid.append(dsnvo.getOriginExchangeOffice())
		.append(dsnvo.getDestinationExchangeOffice())
		.append(dsnvo.getMailCategoryCode())
		.append(dsnvo.getMailSubclass())
		.append(dsnvo.getYear())
		.append(dsnvo.getDsn());
		return mailBagid.toString();
	}

	public static FlightFilterVO constructFlightFilterVOForContainer(
			   MailbagVO mailbagvo) {
	        FlightFilterVO flightFilterVO = new FlightFilterVO();
	        flightFilterVO.setCompanyCode(mailbagvo.getCompanyCode());
	        flightFilterVO.setFlightCarrierId(mailbagvo.getCarrierId());
	        flightFilterVO.setFlightNumber(mailbagvo.getFlightNumber());
	        flightFilterVO.setFlightDate(mailbagvo.getFlightDate());
	                flightFilterVO.setDirection(FlightFilterVO.INBOUND);
	        flightFilterVO.setStation(mailbagvo.getPou());
	        flightFilterVO.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
	        return flightFilterVO;
	 }

	/*
	public static Collection<MailbagVO> constructMailbagVOFromMailtracing(
			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> oldMailbagVO) {mailtracking Module has been removed }
	
	public static void updateMailbagHisoryVOs (Collection<MailbagHistoryVO> newVOs,
			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO> oldVOs){
		for(com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO oldVO: oldVOs){mailtracking Module has been removed}
	}
	public static void updateDamagedVOs (Collection<DamagedMailbagVO> newVOs,
			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO> oldVOs){
		for(com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO oldVO: oldVOs){mailtracking Module has been removed}
	}
	public static Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> convertResditEventVOs(
			Collection<ResditEventVO> eventVOs){
		Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> returnVOs = new
						ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO>();
		for(ResditEventVO eveVO: eventVOs){mailtracking Module has been removed}
		return returnVOs;
	}
	public static Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> constructOldMailbagVOFromMailtracing(
			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> oldMailbagVO) {mailtracking Module has been removed}
	public static void updateOldMailbagHisoryVOs (Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO> newVOs,
			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO> oldVOs){
		for(com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO oldVO: oldVOs){mailtracking Module has been removed}
	}
	public static void updateDamagedOldVOs (Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO> newVOs,
			Collection<com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO> oldVOs){mailtracking Module has been removed}
	public static Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> convertResditEventVOsFromMail(
			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> eventVOs){mailtracking Module has been removed}
	*/

//	public static Collection<MailbagVO> constructMailbagVOFromMailtracing(
//			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> oldMailbagVO) {
//		Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
//		for(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO oldVO: oldMailbagVO){
//			MailbagVO mailbagVO = new MailbagVO();
//			   mailbagVO.setAcceptanceFlag(oldVO.getAcceptanceFlag());
//			   mailbagVO.setAccepted(oldVO.getAccepted());
//			   mailbagVO.setAcceptedBags(oldVO.getAcceptedBags());
//			  // mailbagVO.setAcceptedWeight(oldVO.getAcceptedWeight());
//			   mailbagVO.setAcknowledge(oldVO.getAcknowledge());
//			   mailbagVO.setActionMode(oldVO.getActionMode());
//			   mailbagVO.setArrivalSealNumber(oldVO.getArrivalSealNumber());
//			   mailbagVO.setArrivedFlag(oldVO.getArrivedFlag());
//			   mailbagVO.setBellyCartId(oldVO.getBellyCartId());
//			   if(oldVO.getBookingFlightDetailLastUpdTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getBookingFlightDetailLastUpdTime().getStationCode()) || oldVO.getBookingFlightDetailLastUpdTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getBookingFlightDetailLastUpdTime().getStationCode();
//			      mailbagVO.setBookingFlightDetailLastUpdTime(new LocalDate(stnCode_temp,oldVO.getBookingFlightDetailLastUpdTime().getLocation(),oldVO.getBookingFlightDetailLastUpdTime(),oldVO.getBookingFlightDetailLastUpdTime().isTimePresent()));
//			   }
//			   if(oldVO.getBookingLastUpdateTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getBookingLastUpdateTime().getStationCode()) || oldVO.getBookingLastUpdateTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getBookingLastUpdateTime().getStationCode();
//			      mailbagVO.setBookingLastUpdateTime(new LocalDate(stnCode_temp,oldVO.getBookingLastUpdateTime().getLocation(),oldVO.getBookingLastUpdateTime(),oldVO.getBookingLastUpdateTime().isTimePresent()));
//			   }
//			   mailbagVO.setCarditKey(oldVO.getCarditKey());
//			   mailbagVO.setCarditRecipientId(oldVO.getCarditRecipientId());
//			   mailbagVO.setCarditSequenceNumber(oldVO.getCarditSequenceNumber());
//			   mailbagVO.setCarrierCode(oldVO.getCarrierCode());
//			   mailbagVO.setCarrierId(oldVO.getCarrierId());
//			   mailbagVO.setCompanyCode(oldVO.getCompanyCode());
//			   if(oldVO.getConsignmentDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getConsignmentDate().getStationCode()) || oldVO.getConsignmentDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getConsignmentDate().getStationCode();
//			      mailbagVO.setConsignmentDate(new LocalDate(stnCode_temp,oldVO.getConsignmentDate().getLocation(),oldVO.getConsignmentDate(),oldVO.getConsignmentDate().isTimePresent()));
//			   }
//			   mailbagVO.setConsignmentNumber(oldVO.getConsignmentNumber());
//			   mailbagVO.setConsignmentSequenceNumber(oldVO.getConsignmentSequenceNumber());
//			   mailbagVO.setContainerFlag(oldVO.getContainerFlag());
//			   mailbagVO.setContainerForInventory(oldVO.getContainerForInventory());
//			   mailbagVO.setContainerNumber(oldVO.getContainerNumber());
//			   mailbagVO.setContainerType(oldVO.getContainerType());
//			   mailbagVO.setContainerTypeAtAirport(oldVO.getContainerTypeAtAirport());
//			   mailbagVO.setControlDocumentNumber(oldVO.getControlDocumentNumber());
//			   mailbagVO.setCount(oldVO.getCount());
//			   mailbagVO.setCurrencyCode(oldVO.getCurrencyCode());
//			   mailbagVO.setCurrentDateStr(oldVO.getCurrentDateStr());
//			   mailbagVO.setDamageFlag(oldVO.getDamageFlag());
//			   if(oldVO.getDamagedMailbags() != null && !oldVO.getDamagedMailbags().isEmpty()){
//			      Collection<DamagedMailbagVO> newDamageVOs = new ArrayList<DamagedMailbagVO>();
//			      updateDamagedVOs(newDamageVOs,oldVO.getDamagedMailbags());
//			   }
//			   mailbagVO.setDeclaredValue(oldVO.getDeclaredValue());
//			   mailbagVO.setDeliveredFlag(oldVO.getDeliveredFlag());
//			   mailbagVO.setDeliveryNeeded(oldVO.isDeliveryNeeded());
//			   //mailbagVO.setDespatch(oldVO.isDespatch());
//			   //mailbagVO.setDespatchId(oldVO.getDespatchId());
//			   mailbagVO.setDespatchSerialNumber(oldVO.getDespatchSerialNumber());
//			   mailbagVO.setDestCityDesc(oldVO.getDestCityDesc());
//			   mailbagVO.setDestCountryDesc(oldVO.getDestCountryDesc());
//			   mailbagVO.setDisplayLabel(oldVO.getDisplayLabel());
//			   mailbagVO.setDoe(oldVO.getDoe());
//			   mailbagVO.setErrorCode(oldVO.getErrorCode());
//			   mailbagVO.setErrorDescription(oldVO.getErrorDescription());
//			   mailbagVO.setErrorType(oldVO.getErrorType());
//			   mailbagVO.setFinalDestination(oldVO.getFinalDestination());
//			   mailbagVO.setFlagPAULDResidit(oldVO.getFlagPAULDResidit());
//			   if(oldVO.getFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
//			      mailbagVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
//			   }
//			   mailbagVO.setFlightNumber(oldVO.getFlightNumber());
//			   mailbagVO.setFlightSequenceNumber(oldVO.getFlightSequenceNumber());
//			   mailbagVO.setFlightStatus(oldVO.getFlightStatus());
//			   mailbagVO.setFromCarrier(oldVO.getFromCarrier());
//			   mailbagVO.setFromCarrierId(oldVO.getFromCarrierId());
//			   mailbagVO.setFromContainer(oldVO.getFromContainer());
//			   mailbagVO.setFromContainerType(oldVO.getFromContainerType());
//			   mailbagVO.setFromFightNumber(oldVO.getFromFightNumber());
//			   if(oldVO.getFromFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFromFlightDate().getStationCode()) || oldVO.getFromFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFromFlightDate().getStationCode();
//			      mailbagVO.setFromFlightDate(new LocalDate(stnCode_temp,oldVO.getFromFlightDate().getLocation(),oldVO.getFromFlightDate(),oldVO.getFromFlightDate().isTimePresent()));
//			   }
//			   mailbagVO.setFromFlightSequenceNumber(oldVO.getFromFlightSequenceNumber());
//			   mailbagVO.setFromSegmentSerialNumber(oldVO.getFromSegmentSerialNumber());
//			   mailbagVO.setHandoverPartner(oldVO.getHandoverPartner());
//			   mailbagVO.setHighestNumberedReceptacle(oldVO.getHighestNumberedReceptacle());
//			   mailbagVO.setInList(oldVO.getInList());
//			   mailbagVO.setIntact(oldVO.getIntact());
//			   mailbagVO.setInventoryContainer(oldVO.getInventoryContainer());
//			   mailbagVO.setInventoryContainerType(oldVO.getInventoryContainerType());
//			   mailbagVO.setIsoffload(oldVO.isIsoffload());
//			   if(oldVO.getLastUpdateTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getLastUpdateTime().getStationCode()) || oldVO.getLastUpdateTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getLastUpdateTime().getStationCode();
//			      mailbagVO.setLastUpdateTime(new LocalDate(stnCode_temp,oldVO.getLastUpdateTime().getLocation(),oldVO.getLastUpdateTime(),oldVO.getLastUpdateTime().isTimePresent()));
//			   }
//			   mailbagVO.setLastUpdateUser(oldVO.getLastUpdateUser());
//			   if(oldVO.getLatestScannedDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getLatestScannedDate().getStationCode()) || oldVO.getLatestScannedDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getLatestScannedDate().getStationCode();
//			      mailbagVO.setLatestScannedDate(new LocalDate(stnCode_temp,oldVO.getLatestScannedDate().getLocation(),oldVO.getLatestScannedDate(),oldVO.getLatestScannedDate().isTimePresent()));
//			   }
//			   mailbagVO.setLatestStatus(oldVO.getLatestStatus());
//			   mailbagVO.setLegSerialNumber(oldVO.getLegSerialNumber());
//			   mailbagVO.setMailCategoryCode(oldVO.getMailCategoryCode());
//			   mailbagVO.setMailClass(oldVO.getMailClass());
//			   mailbagVO.setMailCompanyCode(oldVO.getMailCompanyCode());
//			   mailbagVO.setMailOutCarrier(oldVO.getMailOutCarrier());
//			   //mailbagVO.setMailSequenceNumber(oldVO.getMailSequenceNumber());
//			   mailbagVO.setMailSource(oldVO.getMailSource());
//			   mailbagVO.setMailStatus(oldVO.getMailStatus());
//			   mailbagVO.setMailSubclass(oldVO.getMailSubclass());
//			   if(oldVO.getMailbagHistories() != null && !oldVO.getMailbagHistories().isEmpty()){
//				   Collection<MailbagHistoryVO> newVOs = new ArrayList<MailbagHistoryVO>();
//				   updateMailbagHisoryVOs(newVOs, oldVO.getMailbagHistories());
//			   }
//			   mailbagVO.setMailbagId(oldVO.getMailbagId());
//			   mailbagVO.setMraStatus(oldVO.getMraStatus());
//			   mailbagVO.setOffloadedDescription(oldVO.getOffloadedDescription());
//			   mailbagVO.setOffloadedReason(oldVO.getOffloadedReason());
//			   mailbagVO.setOffloadedRemarks(oldVO.getOffloadedRemarks());
//			   mailbagVO.setOoe(oldVO.getOoe());
//			   mailbagVO.setOperationalFlag(oldVO.getOperationalFlag());
//			   mailbagVO.setOperationalStatus(oldVO.getOperationalStatus());
//			   mailbagVO.setOrgCityDesc(oldVO.getOrgCityDesc());
//			   mailbagVO.setOwnAirlineCode(oldVO.getOwnAirlineCode());
//			   mailbagVO.setPaBuiltFlag(oldVO.getPaBuiltFlag());
//			   mailbagVO.setPaCode(oldVO.getPaCode());
//			   mailbagVO.setPaDescription(oldVO.getPaDescription());
//			   mailbagVO.setPol(oldVO.getPol());
//			   mailbagVO.setPou(oldVO.getPou());
//			   mailbagVO.setReassignFlag(oldVO.getReassignFlag());
//			   mailbagVO.setReceptacleSerialNumber(oldVO.getReceptacleSerialNumber());
//			   mailbagVO.setRegisteredOrInsuredIndicator(oldVO.getRegisteredOrInsuredIndicator());
//			   if(oldVO.getResditEventDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getResditEventDate().getStationCode()) || oldVO.getResditEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getResditEventDate().getStationCode();
//			      mailbagVO.setResditEventDate(new LocalDate(stnCode_temp,oldVO.getResditEventDate().getLocation(),oldVO.getResditEventDate(),oldVO.getResditEventDate().isTimePresent()));
//			   }
//			   mailbagVO.setResditEventPort(oldVO.getResditEventPort());
//			   mailbagVO.setResditEventSeqNum(oldVO.getResditEventSeqNum());
//			   mailbagVO.setResditEventString(oldVO.getResditEventString());
//			   if(oldVO.getResditEventUTCDate() != null){
//			      mailbagVO.setResditEventUTCDate(new GMTDate(oldVO.getResditEventUTCDate(),oldVO.getResditEventUTCDate().isTimePresent()));
//			   }
//			   mailbagVO.setReturnedDescription(oldVO.getReturnedDescription());
//			   mailbagVO.setReturnedReason(oldVO.getReturnedReason());
//			   mailbagVO.setReturnedRemarks(oldVO.getReturnedRemarks());
//			   if(oldVO.getScannedDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getScannedDate().getStationCode()) || oldVO.getScannedDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getScannedDate().getStationCode();
//			      mailbagVO.setScannedDate(new LocalDate(stnCode_temp,oldVO.getScannedDate().getLocation(),oldVO.getScannedDate(),oldVO.getScannedDate().isTimePresent()));
//			   }
//			   mailbagVO.setScannedPort(oldVO.getScannedPort());
//			   mailbagVO.setScannedUser(oldVO.getScannedUser());
//			   mailbagVO.setSealNumber(oldVO.getSealNumber());
//			   mailbagVO.setSegmentSerialNumber(oldVO.getSegmentSerialNumber());
//			   mailbagVO.setSeqIdentifier(oldVO.getSeqIdentifier());
//			   mailbagVO.setStationCode(oldVO.getStationCode());
//			  //mailbagVO.setStrWeight(oldVO.getStrWeight());
//			   mailbagVO.setSubClassDesc(oldVO.getSubClassDesc());
//			   mailbagVO.setTransferFlag(oldVO.getTransferFlag());
//			   mailbagVO.setTransferFromCarrier(oldVO.getTransferFromCarrier());
//			   mailbagVO.setUbrNumber(oldVO.getUbrNumber());
//			   mailbagVO.setUldNumber(oldVO.getUldNumber());
//			   mailbagVO.setUndoArrivalFlag(oldVO.getUndoArrivalFlag());
//			  // mailbagVO.setVolume(oldVO.getVolume());
//			  // mailbagVO.setWeight(oldVO.getWeight());
//			   mailbagVO.setYear(oldVO.getYear());
//			   newMailbagVOs.add(mailbagVO);
//		}
//		return newMailbagVOs;
//	}
	/*public static void updateMailbagHisoryVOs (Collection<MailbagHistoryVO> newVOs,
			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO> oldVOs){
		for(com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO oldVO: oldVOs){
			MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
			   //mailbagHistoryVO.setCarditKey(oldVO.getCarditKey());
			   mailbagHistoryVO.setCarrierCode(oldVO.getCarrierCode());
			   mailbagHistoryVO.setCarrierId(oldVO.getCarrierId());
			   mailbagHistoryVO.setCompanyCode(oldVO.getCompanyCode());
			   mailbagHistoryVO.setContainerNumber(oldVO.getContainerNumber());
			   mailbagHistoryVO.setContainerType(oldVO.getContainerType());
			   mailbagHistoryVO.setDestinationExchangeOffice(oldVO.getDestinationExchangeOffice());
			   mailbagHistoryVO.setDsn(oldVO.getDsn());
			   //mailbagHistoryVO.setEventCode(oldVO.getEventCode());
			   /*if(oldVO.getEventDate() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getEventDate().getStationCode()) || oldVO.getEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getEventDate().getStationCode();
			      mailbagHistoryVO.setEventDate(new LocalDate(stnCode_temp,oldVO.getEventDate().getLocation(),oldVO.getEventDate(),oldVO.getEventDate().isTimePresent()));
			   }
			   if(oldVO.getFlightDate() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
			      mailbagHistoryVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
			   }
			   mailbagHistoryVO.setFlightNumber(oldVO.getFlightNumber());
			   mailbagHistoryVO.setFlightSequenceNumber(oldVO.getFlightSequenceNumber());
			   mailbagHistoryVO.setHistorySequenceNumber(oldVO.getHistorySequenceNumber());
			   mailbagHistoryVO.setInterchangeControlReference(oldVO.getInterchangeControlReference());*/
			   //mailbagHistoryVO.setMailBoxId(oldVO.getMailBoxId());
			  /* mailbagHistoryVO.setMailCategoryCode(oldVO.getMailCategoryCode());
			   mailbagHistoryVO.setMailClass(oldVO.getMailClass());
			   mailbagHistoryVO.setMailSource(oldVO.getMailSource());
			   mailbagHistoryVO.setMailStatus(oldVO.getMailStatus());
			   mailbagHistoryVO.setMailSubclass(oldVO.getMailSubclass());
			   mailbagHistoryVO.setMailbagId(oldVO.getMailbagId());
			   //mailbagHistoryVO.setMessageSequenceNumber(oldVO.getMessageSequenceNumber());
			   if(oldVO.getMessageTime() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getMessageTime().getStationCode()) || oldVO.getMessageTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getMessageTime().getStationCode();
			      mailbagHistoryVO.setMessageTime(new LocalDate(stnCode_temp,oldVO.getMessageTime().getLocation(),oldVO.getMessageTime(),oldVO.getMessageTime().isTimePresent()));
			   }
			   if(oldVO.getMessageTimeUTC() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getMessageTimeUTC().getStationCode()) || oldVO.getMessageTimeUTC().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getMessageTimeUTC().getStationCode();
			      mailbagHistoryVO.setMessageTimeUTC(new LocalDate(stnCode_temp,oldVO.getMessageTimeUTC().getLocation(),oldVO.getMessageTimeUTC(),oldVO.getMessageTimeUTC().isTimePresent()));
			   }
			   mailbagHistoryVO.setOriginExchangeOffice(oldVO.getOriginExchangeOffice());
			   mailbagHistoryVO.setPaBuiltFlag(oldVO.getPaBuiltFlag());*/
			   //mailbagHistoryVO.setPaCarrierCode(oldVO.getPaCarrierCode());
			  // mailbagHistoryVO.setPou(oldVO.getPou());*/
			   //mailbagHistoryVO.setProcessedStatus(oldVO.getProcessedStatus());
			   //mailbagHistoryVO.setResiditFailReasonCode(oldVO.getResiditFailReasonCode());
			   //mailbagHistoryVO.setResiditSend(oldVO.getResiditSend());
			  /* if(oldVO.getScanDate() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getScanDate().getStationCode()) || oldVO.getScanDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getScanDate().getStationCode();
			      mailbagHistoryVO.setScanDate(new LocalDate(stnCode_temp,oldVO.getScanDate().getLocation(),oldVO.getScanDate(),oldVO.getScanDate().isTimePresent()));
			   }
			   mailbagHistoryVO.setScanUser(oldVO.getScanUser());
			   mailbagHistoryVO.setScannedPort(oldVO.getScannedPort());
			   mailbagHistoryVO.setSegmentSerialNumber(oldVO.getSegmentSerialNumber());
			   /*if(oldVO.getUtcEventDate() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getUtcEventDate().getStationCode()) || oldVO.getUtcEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getUtcEventDate().getStationCode();
			      mailbagHistoryVO.setUtcEventDate(new LocalDate(stnCode_temp,oldVO.getUtcEventDate().getLocation(),oldVO.getUtcEventDate(),oldVO.getUtcEventDate().isTimePresent()));
			   }*/
			  /* if(oldVO.getUtcScanDate() != null){
			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getUtcScanDate().getStationCode()) || oldVO.getUtcScanDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getUtcScanDate().getStationCode();
			      mailbagHistoryVO.setUtcScanDate(new LocalDate(stnCode_temp,oldVO.getUtcScanDate().getLocation(),oldVO.getUtcScanDate(),oldVO.getUtcScanDate().isTimePresent()));
			   }
			   mailbagHistoryVO.setYear(oldVO.getYear());
			   newVOs.add(mailbagHistoryVO);
		}
	}*/
	
	// As part of 4.10 movement to git 
	// com.ibsplc.icargo.business.mailtracking package is not using now.
	// And these methods has no reference now so its commenting, further any usages are required then uncomment and made the corrections if required.

	
//	public static void updateDamagedVOs (Collection<DamagedMailbagVO> newVOs,
//			Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO> oldVOs){
//		for(com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO oldVO: oldVOs){
//			DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
//			   damagedMailbagVO.setAirportCode(oldVO.getAirportCode());
//			   damagedMailbagVO.setCarrierCode(oldVO.getCarrierCode());
//			   damagedMailbagVO.setCompanyCode(oldVO.getCompanyCode());
//			   damagedMailbagVO.setCurrencyCode(oldVO.getCurrencyCode());
//			   damagedMailbagVO.setDamageCode(oldVO.getDamageCode());
//			   if(oldVO.getDamageDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getDamageDate().getStationCode()) || oldVO.getDamageDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getDamageDate().getStationCode();
//			      damagedMailbagVO.setDamageDate(new LocalDate(stnCode_temp,oldVO.getDamageDate().getLocation(),oldVO.getDamageDate(),oldVO.getDamageDate().isTimePresent()));
//			   }
//			   damagedMailbagVO.setDamageDescription(oldVO.getDamageDescription());
//			   damagedMailbagVO.setDamageType(oldVO.getDamageType());
//			   damagedMailbagVO.setDeclaredValue(oldVO.getDeclaredValue());
//			   damagedMailbagVO.setDeclaredValueTot(oldVO.getDeclaredValueTot());
//			   damagedMailbagVO.setDestinationExchangeOffice(oldVO.getDestinationExchangeOffice());
//			   damagedMailbagVO.setDsn(oldVO.getDsn());
//			   if(oldVO.getFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
//			      damagedMailbagVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
//			   }
//			   damagedMailbagVO.setFlightDestination(oldVO.getFlightDestination());
//			   damagedMailbagVO.setFlightNumber(oldVO.getFlightNumber());
//			   damagedMailbagVO.setFlightOrigin(oldVO.getFlightOrigin());
//			   damagedMailbagVO.setMailClass(oldVO.getMailClass());
//			   damagedMailbagVO.setMailbagId(oldVO.getMailbagId());
//			   damagedMailbagVO.setOperationFlag(oldVO.getOperationFlag());
//			   damagedMailbagVO.setOperationType(oldVO.getOperationType());
//			   damagedMailbagVO.setOriginExchangeOffice(oldVO.getOriginExchangeOffice());
//			   damagedMailbagVO.setPaCode(oldVO.getPaCode());
//			   damagedMailbagVO.setRemarks(oldVO.getRemarks());
//			   damagedMailbagVO.setReturnedFlag(oldVO.getReturnedFlag());
//			   damagedMailbagVO.setSubClassCode(oldVO.getSubClassCode());
//			   damagedMailbagVO.setSubClassGroup(oldVO.getSubClassGroup());
//			   damagedMailbagVO.setTotCurrencyCode(oldVO.getTotCurrencyCode());
//			   damagedMailbagVO.setUserCode(oldVO.getUserCode());
//			   //damagedMailbagVO.setWeight(oldVO.getWeight());
//			   damagedMailbagVO.setYear(oldVO.getYear());
//			   newVOs.add(damagedMailbagVO);
//		}
//	}
//	public static Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> convertResditEventVOs(
//			Collection<ResditEventVO> eventVOs){
//		Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO> returnVOs = new
//						ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO>();
//		for(ResditEventVO eveVO: eventVOs){
//			com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO resditEventVO = new com.ibsplc.icargo.business.mailtracking.defaults.vo.ResditEventVO();
//			   resditEventVO.setActualResditEvent(eveVO.getActualResditEvent());
//			   resditEventVO.setActualSenderId(eveVO.getActualSenderId());
//			   resditEventVO.setCarditExist(eveVO.getCarditExist());
//			   resditEventVO.setCompanyCode(eveVO.getCompanyCode());
//			   resditEventVO.setConsignmentNumber(eveVO.getConsignmentNumber());
//			   resditEventVO.setEventPort(eveVO.getEventPort());
//			   resditEventVO.setEventPortName(eveVO.getEventPortName());
//			   resditEventVO.setInterchangeControlReference(eveVO.getInterchangeControlReference());
//			   resditEventVO.setMessageSequenceNumber(eveVO.getMessageSequenceNumber());
//			   resditEventVO.setPaCode(eveVO.getPaCode());
//			   resditEventVO.setResditEventCode(eveVO.getResditEventCode());
//			   resditEventVO.setResditEventStatus(eveVO.getResditEventStatus());
//			   if(eveVO.getResditMailbagVOs() != null && !eveVO.getResditMailbagVOs().isEmpty()){
//				   Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> oldVOs =
//					   	new ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO>();
//				   Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> oldMailbagVOs = constructOldMailbagVOFromMailtracing(eveVO.getResditMailbagVOs());
//				   resditEventVO.setResditMailbagVOs(oldMailbagVOs);
//			   }
//			   resditEventVO.setResditVersion(eveVO.getResditVersion());
//			   resditEventVO.setUniqueIdForFlag(eveVO.getUniqueIdForFlag());
//			   returnVOs.add(resditEventVO);
//		}
//		return returnVOs;
//	}
//	public static Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> constructOldMailbagVOFromMailtracing(
//			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> oldMailbagVO) {
//		Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO> newMailbagVOs = new ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO>();
//		for(com.ibsplc.icargo.business.mail.operations.vo.MailbagVO oldVO: oldMailbagVO){
//			com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO mailbagVO = new com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagVO();
//			   mailbagVO.setAcceptanceFlag(oldVO.getAcceptanceFlag());
//			   mailbagVO.setAccepted(oldVO.getAccepted());
//			   mailbagVO.setAcceptedBags(oldVO.getAcceptedBags());
//			  // mailbagVO.setAcceptedWeight(oldVO.getAcceptedWeight());
//			   mailbagVO.setAcknowledge(oldVO.getAcknowledge());
//			   mailbagVO.setActionMode(oldVO.getActionMode());
//			   mailbagVO.setArrivalSealNumber(oldVO.getArrivalSealNumber());
//			   mailbagVO.setArrivedFlag(oldVO.getArrivedFlag());
//			   mailbagVO.setBellyCartId(oldVO.getBellyCartId());
//			   if(oldVO.getBookingFlightDetailLastUpdTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getBookingFlightDetailLastUpdTime().getStationCode()) || oldVO.getBookingFlightDetailLastUpdTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getBookingFlightDetailLastUpdTime().getStationCode();
//			      mailbagVO.setBookingFlightDetailLastUpdTime(new LocalDate(stnCode_temp,oldVO.getBookingFlightDetailLastUpdTime().getLocation(),oldVO.getBookingFlightDetailLastUpdTime(),oldVO.getBookingFlightDetailLastUpdTime().isTimePresent()));
//			   }
//			   if(oldVO.getBookingLastUpdateTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getBookingLastUpdateTime().getStationCode()) || oldVO.getBookingLastUpdateTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getBookingLastUpdateTime().getStationCode();
//			      mailbagVO.setBookingLastUpdateTime(new LocalDate(stnCode_temp,oldVO.getBookingLastUpdateTime().getLocation(),oldVO.getBookingLastUpdateTime(),oldVO.getBookingLastUpdateTime().isTimePresent()));
//			   }
//			   mailbagVO.setCarditKey(oldVO.getCarditKey());
//			   mailbagVO.setCarditRecipientId(oldVO.getCarditRecipientId());
//			   mailbagVO.setCarditSequenceNumber(oldVO.getCarditSequenceNumber());
//			   mailbagVO.setCarrierCode(oldVO.getCarrierCode());
//			   mailbagVO.setCarrierId(oldVO.getCarrierId());
//			   mailbagVO.setCompanyCode(oldVO.getCompanyCode());
//			   if(oldVO.getConsignmentDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getConsignmentDate().getStationCode()) || oldVO.getConsignmentDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getConsignmentDate().getStationCode();
//			      mailbagVO.setConsignmentDate(new LocalDate(stnCode_temp,oldVO.getConsignmentDate().getLocation(),oldVO.getConsignmentDate(),oldVO.getConsignmentDate().isTimePresent()));
//			   }
//			   mailbagVO.setConsignmentNumber(oldVO.getConsignmentNumber());
//			   mailbagVO.setConsignmentSequenceNumber(oldVO.getConsignmentSequenceNumber());
//			   mailbagVO.setContainerFlag(oldVO.getContainerFlag());
//			   mailbagVO.setContainerForInventory(oldVO.getContainerForInventory());
//			   mailbagVO.setContainerNumber(oldVO.getContainerNumber());
//			   mailbagVO.setContainerType(oldVO.getContainerType());
//			   mailbagVO.setContainerTypeAtAirport(oldVO.getContainerTypeAtAirport());
//			   mailbagVO.setControlDocumentNumber(oldVO.getControlDocumentNumber());
//			   mailbagVO.setCount(oldVO.getCount());
//			   mailbagVO.setCurrencyCode(oldVO.getCurrencyCode());
//			   mailbagVO.setCurrentDateStr(oldVO.getCurrentDateStr());
//			   mailbagVO.setDamageFlag(oldVO.getDamageFlag());
//			   if(oldVO.getDamagedMailbags() != null && !oldVO.getDamagedMailbags().isEmpty()){
//			      Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO> newDamageVOs = new ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO>();
//			      updateDamagedOldVOs(newDamageVOs,oldVO.getDamagedMailbags());
//			   }
//			   mailbagVO.setDeclaredValue(oldVO.getDeclaredValue());
//			   mailbagVO.setDeliveredFlag(oldVO.getDeliveredFlag());
//			   mailbagVO.setDeliveryNeeded(oldVO.isDeliveryNeeded());
//			   //mailbagVO.setDespatch(oldVO.isDespatch());
//			   //mailbagVO.setDespatchId(oldVO.getDespatchId());
//			   mailbagVO.setDespatchSerialNumber(oldVO.getDespatchSerialNumber());
//			   mailbagVO.setDestCityDesc(oldVO.getDestCityDesc());
//			   mailbagVO.setDestCountryDesc(oldVO.getDestCountryDesc());
//			   mailbagVO.setDisplayLabel(oldVO.getDisplayLabel());
//			   mailbagVO.setDoe(oldVO.getDoe());
//			   mailbagVO.setErrorCode(oldVO.getErrorCode());
//			   mailbagVO.setErrorDescription(oldVO.getErrorDescription());
//			   mailbagVO.setErrorType(oldVO.getErrorType());
//			   mailbagVO.setFinalDestination(oldVO.getFinalDestination());
//			   mailbagVO.setFlagPAULDResidit(oldVO.getFlagPAULDResidit());
//			   if(oldVO.getFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
//			      mailbagVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
//			   }
//			   mailbagVO.setFlightNumber(oldVO.getFlightNumber());
//			   mailbagVO.setFlightSequenceNumber(oldVO.getFlightSequenceNumber());
//			   mailbagVO.setFlightStatus(oldVO.getFlightStatus());
//			   mailbagVO.setFromCarrier(oldVO.getFromCarrier());
//			   mailbagVO.setFromCarrierId(oldVO.getFromCarrierId());
//			   mailbagVO.setFromContainer(oldVO.getFromContainer());
//			   mailbagVO.setFromContainerType(oldVO.getFromContainerType());
//			   mailbagVO.setFromFightNumber(oldVO.getFromFightNumber());
//			   if(oldVO.getFromFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFromFlightDate().getStationCode()) || oldVO.getFromFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFromFlightDate().getStationCode();
//			      mailbagVO.setFromFlightDate(new LocalDate(stnCode_temp,oldVO.getFromFlightDate().getLocation(),oldVO.getFromFlightDate(),oldVO.getFromFlightDate().isTimePresent()));
//			   }
//			   mailbagVO.setFromFlightSequenceNumber(oldVO.getFromFlightSequenceNumber());
//			   mailbagVO.setFromSegmentSerialNumber(oldVO.getFromSegmentSerialNumber());
//			   mailbagVO.setHandoverPartner(oldVO.getHandoverPartner());
//			   mailbagVO.setHighestNumberedReceptacle(oldVO.getHighestNumberedReceptacle());
//			   mailbagVO.setInList(oldVO.getInList());
//			   mailbagVO.setIntact(oldVO.getIntact());
//			   mailbagVO.setInventoryContainer(oldVO.getInventoryContainer());
//			   mailbagVO.setInventoryContainerType(oldVO.getInventoryContainerType());
//			   mailbagVO.setIsoffload(oldVO.isIsoffload());
//			   if(oldVO.getLastUpdateTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getLastUpdateTime().getStationCode()) || oldVO.getLastUpdateTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getLastUpdateTime().getStationCode();
//			      mailbagVO.setLastUpdateTime(new LocalDate(stnCode_temp,oldVO.getLastUpdateTime().getLocation(),oldVO.getLastUpdateTime(),oldVO.getLastUpdateTime().isTimePresent()));
//			   }
//			   mailbagVO.setLastUpdateUser(oldVO.getLastUpdateUser());
//			   if(oldVO.getLatestScannedDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getLatestScannedDate().getStationCode()) || oldVO.getLatestScannedDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getLatestScannedDate().getStationCode();
//			      mailbagVO.setLatestScannedDate(new LocalDate(stnCode_temp,oldVO.getLatestScannedDate().getLocation(),oldVO.getLatestScannedDate(),oldVO.getLatestScannedDate().isTimePresent()));
//			   }
//			   mailbagVO.setLatestStatus(oldVO.getLatestStatus());
//			   mailbagVO.setLegSerialNumber(oldVO.getLegSerialNumber());
//			   mailbagVO.setMailCategoryCode(oldVO.getMailCategoryCode());
//			   mailbagVO.setMailClass(oldVO.getMailClass());
//			   mailbagVO.setMailCompanyCode(oldVO.getMailCompanyCode());
//			   mailbagVO.setMailOutCarrier(oldVO.getMailOutCarrier());
//			   //mailbagVO.setMailSequenceNumber(oldVO.getMailSequenceNumber());
//			   mailbagVO.setMailSource(oldVO.getMailSource());
//			   mailbagVO.setMailStatus(oldVO.getMailStatus());
//			   mailbagVO.setMailSubclass(oldVO.getMailSubclass());
//			   if(oldVO.getMailbagHistories() != null && !oldVO.getMailbagHistories().isEmpty()){
//				   Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO> newVOs = new ArrayList<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO>();
//				   updateOldMailbagHisoryVOs(newVOs, oldVO.getMailbagHistories());
//			   }
//			   mailbagVO.setMailbagId(oldVO.getMailbagId());
//			   mailbagVO.setMraStatus(oldVO.getMraStatus());
//			   mailbagVO.setOffloadedDescription(oldVO.getOffloadedDescription());
//			   mailbagVO.setOffloadedReason(oldVO.getOffloadedReason());
//			   mailbagVO.setOffloadedRemarks(oldVO.getOffloadedRemarks());
//			   mailbagVO.setOoe(oldVO.getOoe());
//			   mailbagVO.setOperationalFlag(oldVO.getOperationalFlag());
//			   mailbagVO.setOperationalStatus(oldVO.getOperationalStatus());
//			   mailbagVO.setOrgCityDesc(oldVO.getOrgCityDesc());
//			   mailbagVO.setOwnAirlineCode(oldVO.getOwnAirlineCode());
//			   mailbagVO.setPaBuiltFlag(oldVO.getPaBuiltFlag());
//			   mailbagVO.setPaCode(oldVO.getPaCode());
//			   mailbagVO.setPaDescription(oldVO.getPaDescription());
//			   mailbagVO.setPol(oldVO.getPol());
//			   mailbagVO.setPou(oldVO.getPou());
//			   mailbagVO.setReassignFlag(oldVO.getReassignFlag());
//			   mailbagVO.setReceptacleSerialNumber(oldVO.getReceptacleSerialNumber());
//			   mailbagVO.setRegisteredOrInsuredIndicator(oldVO.getRegisteredOrInsuredIndicator());
//			   if(oldVO.getResditEventDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getResditEventDate().getStationCode()) || oldVO.getResditEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getResditEventDate().getStationCode();
//			      mailbagVO.setResditEventDate(new LocalDate(stnCode_temp,oldVO.getResditEventDate().getLocation(),oldVO.getResditEventDate(),oldVO.getResditEventDate().isTimePresent()));
//			   }
//			   mailbagVO.setResditEventPort(oldVO.getResditEventPort());
//			   mailbagVO.setResditEventSeqNum(oldVO.getResditEventSeqNum());
//			   mailbagVO.setResditEventString(oldVO.getResditEventString());
//			   if(oldVO.getResditEventUTCDate() != null){
//			      mailbagVO.setResditEventUTCDate(new GMTDate(oldVO.getResditEventUTCDate(),oldVO.getResditEventUTCDate().isTimePresent()));
//			   }
//			   mailbagVO.setReturnedDescription(oldVO.getReturnedDescription());
//			   mailbagVO.setReturnedReason(oldVO.getReturnedReason());
//			   mailbagVO.setReturnedRemarks(oldVO.getReturnedRemarks());
//			   if(oldVO.getScannedDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getScannedDate().getStationCode()) || oldVO.getScannedDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getScannedDate().getStationCode();
//			      mailbagVO.setScannedDate(new LocalDate(stnCode_temp,oldVO.getScannedDate().getLocation(),oldVO.getScannedDate(),oldVO.getScannedDate().isTimePresent()));
//			   }
//			   mailbagVO.setScannedPort(oldVO.getScannedPort());
//			   mailbagVO.setScannedUser(oldVO.getScannedUser());
//			   mailbagVO.setSealNumber(oldVO.getSealNumber());
//			   mailbagVO.setSegmentSerialNumber(oldVO.getSegmentSerialNumber());
//			   mailbagVO.setSeqIdentifier(oldVO.getSeqIdentifier());
//			   mailbagVO.setStationCode(oldVO.getStationCode());
//			  // mailbagVO.setStrWeight(oldVO.getStrWeight());
//			   mailbagVO.setSubClassDesc(oldVO.getSubClassDesc());
//			   mailbagVO.setTransferFlag(oldVO.getTransferFlag());
//			   mailbagVO.setTransferFromCarrier(oldVO.getTransferFromCarrier());
//			   mailbagVO.setUbrNumber(oldVO.getUbrNumber());
//			   mailbagVO.setUldNumber(oldVO.getUldNumber());
//			   mailbagVO.setUndoArrivalFlag(oldVO.getUndoArrivalFlag());
//			  // mailbagVO.setVolume(oldVO.getVolume());
//			   //mailbagVO.setWeight(oldVO.getWeight());
//			   mailbagVO.setYear(oldVO.getYear());
//			   newMailbagVOs.add(mailbagVO);
//		}
//		return newMailbagVOs;
//	}
//	public static void updateOldMailbagHisoryVOs (Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO> newVOs,
//			Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO> oldVOs){
//		for(com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO oldVO: oldVOs){
//			com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO mailbagHistoryVO = new com.ibsplc.icargo.business.mailtracking.defaults.vo.MailbagHistoryVO();
//			   //mailbagHistoryVO.setCarditKey(oldVO.getCarditKey());
//			   mailbagHistoryVO.setCarrierCode(oldVO.getCarrierCode());
//			   mailbagHistoryVO.setCarrierId(oldVO.getCarrierId());
//			   mailbagHistoryVO.setCompanyCode(oldVO.getCompanyCode());
//			   mailbagHistoryVO.setContainerNumber(oldVO.getContainerNumber());
//			   mailbagHistoryVO.setContainerType(oldVO.getContainerType());
//			   mailbagHistoryVO.setDestinationExchangeOffice(oldVO.getDestinationExchangeOffice());
//			   mailbagHistoryVO.setDsn(oldVO.getDsn());
//			   //mailbagHistoryVO.setEventCode(oldVO.getEventCode());
//			   /*if(oldVO.getEventDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getEventDate().getStationCode()) || oldVO.getEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getEventDate().getStationCode();
//			      mailbagHistoryVO.setEventDate(new LocalDate(stnCode_temp,oldVO.getEventDate().getLocation(),oldVO.getEventDate(),oldVO.getEventDate().isTimePresent()));
//			   }*/
//			   if(oldVO.getFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
//			      mailbagHistoryVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
//			   }
//			   mailbagHistoryVO.setFlightNumber(oldVO.getFlightNumber());
//			   mailbagHistoryVO.setFlightSequenceNumber(oldVO.getFlightSequenceNumber());
//			   mailbagHistoryVO.setHistorySequenceNumber(oldVO.getHistorySequenceNumber());
//			   mailbagHistoryVO.setInterchangeControlReference(oldVO.getInterchangeControlReference());
//			   //mailbagHistoryVO.setMailBoxId(oldVO.getMailBoxId());
//			   mailbagHistoryVO.setMailCategoryCode(oldVO.getMailCategoryCode());
//			   mailbagHistoryVO.setMailClass(oldVO.getMailClass());
//			   mailbagHistoryVO.setMailSource(oldVO.getMailSource());
//			   mailbagHistoryVO.setMailStatus(oldVO.getMailStatus());
//			   mailbagHistoryVO.setMailSubclass(oldVO.getMailSubclass());
//			   mailbagHistoryVO.setMailbagId(oldVO.getMailbagId());
//			   //mailbagHistoryVO.setMessageSequenceNumber(oldVO.getMessageSequenceNumber());
//			   if(oldVO.getMessageTime() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getMessageTime().getStationCode()) || oldVO.getMessageTime().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getMessageTime().getStationCode();
//			      mailbagHistoryVO.setMessageTime(new LocalDate(stnCode_temp,oldVO.getMessageTime().getLocation(),oldVO.getMessageTime(),oldVO.getMessageTime().isTimePresent()));
//			   }
//			   if(oldVO.getMessageTimeUTC() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getMessageTimeUTC().getStationCode()) || oldVO.getMessageTimeUTC().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getMessageTimeUTC().getStationCode();
//			      mailbagHistoryVO.setMessageTimeUTC(new LocalDate(stnCode_temp,oldVO.getMessageTimeUTC().getLocation(),oldVO.getMessageTimeUTC(),oldVO.getMessageTimeUTC().isTimePresent()));
//			   }
//			   mailbagHistoryVO.setOriginExchangeOffice(oldVO.getOriginExchangeOffice());
//			   mailbagHistoryVO.setPaBuiltFlag(oldVO.getPaBuiltFlag());
//			   //mailbagHistoryVO.setPaCarrierCode(oldVO.getPaCarrierCode());
//			   mailbagHistoryVO.setPou(oldVO.getPou());
//			   //mailbagHistoryVO.setProcessedStatus(oldVO.getProcessedStatus());
//			   //mailbagHistoryVO.setResiditFailReasonCode(oldVO.getResiditFailReasonCode());
//			   //mailbagHistoryVO.setResiditSend(oldVO.getResiditSend());
//			   if(oldVO.getScanDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getScanDate().getStationCode()) || oldVO.getScanDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getScanDate().getStationCode();
//			      mailbagHistoryVO.setScanDate(new LocalDate(stnCode_temp,oldVO.getScanDate().getLocation(),oldVO.getScanDate(),oldVO.getScanDate().isTimePresent()));
//			   }
//			   mailbagHistoryVO.setScanUser(oldVO.getScanUser());
//			   mailbagHistoryVO.setScannedPort(oldVO.getScannedPort());
//			   mailbagHistoryVO.setSegmentSerialNumber(oldVO.getSegmentSerialNumber());
//			   /*if(oldVO.getUtcEventDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getUtcEventDate().getStationCode()) || oldVO.getUtcEventDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getUtcEventDate().getStationCode();
//			      mailbagHistoryVO.setUtcEventDate(new LocalDate(stnCode_temp,oldVO.getUtcEventDate().getLocation(),oldVO.getUtcEventDate(),oldVO.getUtcEventDate().isTimePresent()));
//			   }*/
//			   if(oldVO.getUtcScanDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getUtcScanDate().getStationCode()) || oldVO.getUtcScanDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getUtcScanDate().getStationCode();
//			      mailbagHistoryVO.setUtcScanDate(new LocalDate(stnCode_temp,oldVO.getUtcScanDate().getLocation(),oldVO.getUtcScanDate(),oldVO.getUtcScanDate().isTimePresent()));
//			   }
//			   mailbagHistoryVO.setYear(oldVO.getYear());
//			   newVOs.add(mailbagHistoryVO);
//		}
//	}
//	public static void updateDamagedOldVOs (Collection<com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO> newVOs,
//			Collection<com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO> oldVOs){
//		for(com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO oldVO: oldVOs){
//			com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO damagedMailbagVO = new com.ibsplc.icargo.business.mailtracking.defaults.vo.DamagedMailbagVO();
//			   damagedMailbagVO.setAirportCode(oldVO.getAirportCode());
//			   damagedMailbagVO.setCarrierCode(oldVO.getCarrierCode());
//			   damagedMailbagVO.setCompanyCode(oldVO.getCompanyCode());
//			   damagedMailbagVO.setCurrencyCode(oldVO.getCurrencyCode());
//			   damagedMailbagVO.setDamageCode(oldVO.getDamageCode());
//			   if(oldVO.getDamageDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getDamageDate().getStationCode()) || oldVO.getDamageDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getDamageDate().getStationCode();
//			      damagedMailbagVO.setDamageDate(new LocalDate(stnCode_temp,oldVO.getDamageDate().getLocation(),oldVO.getDamageDate(),oldVO.getDamageDate().isTimePresent()));
//			   }
//			   damagedMailbagVO.setDamageDescription(oldVO.getDamageDescription());
//			   damagedMailbagVO.setDamageType(oldVO.getDamageType());
//			   damagedMailbagVO.setDeclaredValue(oldVO.getDeclaredValue());
//			   damagedMailbagVO.setDeclaredValueTot(oldVO.getDeclaredValueTot());
//			   damagedMailbagVO.setDestinationExchangeOffice(oldVO.getDestinationExchangeOffice());
//			   damagedMailbagVO.setDsn(oldVO.getDsn());
//			   if(oldVO.getFlightDate() != null){
//			      String stnCode_temp = (LocalDate.NO_STATION.equals(oldVO.getFlightDate().getStationCode()) || oldVO.getFlightDate().getStationCode()== null) ? LocalDate.NO_STATION : oldVO.getFlightDate().getStationCode();
//			      damagedMailbagVO.setFlightDate(new LocalDate(stnCode_temp,oldVO.getFlightDate().getLocation(),oldVO.getFlightDate(),oldVO.getFlightDate().isTimePresent()));
//			   }
//			   damagedMailbagVO.setFlightDestination(oldVO.getFlightDestination());
//			   damagedMailbagVO.setFlightNumber(oldVO.getFlightNumber());
//			   damagedMailbagVO.setFlightOrigin(oldVO.getFlightOrigin());
//			   damagedMailbagVO.setMailClass(oldVO.getMailClass());
//			   damagedMailbagVO.setMailbagId(oldVO.getMailbagId());
//			   damagedMailbagVO.setOperationFlag(oldVO.getOperationFlag());
//			   damagedMailbagVO.setOperationType(oldVO.getOperationType());
//			   damagedMailbagVO.setOriginExchangeOffice(oldVO.getOriginExchangeOffice());
//			   damagedMailbagVO.setPaCode(oldVO.getPaCode());
//			   damagedMailbagVO.setRemarks(oldVO.getRemarks());
//			   damagedMailbagVO.setReturnedFlag(oldVO.getReturnedFlag());
//			   damagedMailbagVO.setSubClassCode(oldVO.getSubClassCode());
//			   damagedMailbagVO.setSubClassGroup(oldVO.getSubClassGroup());
//			   damagedMailbagVO.setTotCurrencyCode(oldVO.getTotCurrencyCode());
//			   damagedMailbagVO.setUserCode(oldVO.getUserCode());
//			 // damagedMailbagVO.setWeight(oldVO.getWeight());
//			   damagedMailbagVO.setYear(oldVO.getYear());
//			   newVOs.add(damagedMailbagVO);
//		}
//	}
//	public static Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> convertResditEventVOsFromMail(
//			Collection<ResditEventVO> eventVOs){
//		Collection<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO> returnVOs = new
//						ArrayList<com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO>();
//		for(ResditEventVO eveVO: eventVOs){
//			com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO resditEventVO = new com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO();
//			   resditEventVO.setActualResditEvent(eveVO.getActualResditEvent());
//			   resditEventVO.setActualSenderId(eveVO.getActualSenderId());
//			   resditEventVO.setCarditExist(eveVO.getCarditExist());
//			   resditEventVO.setCompanyCode(eveVO.getCompanyCode());
//			   resditEventVO.setConsignmentNumber(eveVO.getConsignmentNumber());
//			   resditEventVO.setEventPort(eveVO.getEventPort());
//			   resditEventVO.setEventPortName(eveVO.getEventPortName());
//			   resditEventVO.setInterchangeControlReference(eveVO.getInterchangeControlReference());
//			   resditEventVO.setMessageSequenceNumber(eveVO.getMessageSequenceNumber());
//			   resditEventVO.setPaCode(eveVO.getPaCode());
//			   resditEventVO.setResditEventCode(eveVO.getResditEventCode());
//			   resditEventVO.setResditEventStatus(eveVO.getResditEventStatus());
//			   if(eveVO.getResditMailbagVOs() != null && !eveVO.getResditMailbagVOs().isEmpty()){
//				   Collection<com.ibsplc.icargo.business.mail.operations.vo.MailbagVO> oldMailbagVOs = constructMailbagVOFromMailtracing(eveVO.getResditMailbagVOs());
//				   resditEventVO.setResditMailbagVOs(oldMailbagVOs);
//			   }
//			   resditEventVO.setResditVersion(eveVO.getResditVersion());
//			   resditEventVO.setUniqueIdForFlag(eveVO.getUniqueIdForFlag());
//			   returnVOs.add(resditEventVO);
//		}
//		return returnVOs;
//	 }

	/**
	 * 	Method		:	MailtrackingDefaultsVOConverter.constructOperationalFlightVO
	 *	Added by 	:	A-7371 on 24-Nov-2017
	 * 	Used for 	:
	 *	Parameters	:	@param flightValidationVO
	 *	Parameters	:	@return 
	 *	Return type	: 	OperationalFlightVO
	 */
	public static OperationalFlightVO constructOperationalFlightVO(
			FlightValidationVO flightValidationVO) {
		// TODO Auto-generated method stub
		
		OperationalFlightVO operationalFlightVO=new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(flightValidationVO.getCompanyCode());
		operationalFlightVO.setCarrierId(flightValidationVO.getFlightCarrierId());
		operationalFlightVO.setFlightNumber(flightValidationVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		operationalFlightVO.setPol(flightValidationVO.getAirportCode());
		return operationalFlightVO;
	 }
	/**
	 * 	Method		:	MailtrackingDefaultsVOConverter.populateCustomsDefaultsVO
	 *	Added by 	:	A-8083 on 25-Nov-2018
	 * 	Used for 	:
	 *	Parameters	:	@param mailArrivalVO
	 *	Parameters	:	@return 
	 *	Return type	: 	CustomsDefaultsVO
	 */
	public static CustomsDefaultsVO populateCustomsDefaultsVO(MailArrivalVO mailArrivalVO) {
		CustomsDefaultsVO customsDefaultsVO = new CustomsDefaultsVO();
		customsDefaultsVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		customsDefaultsVO.setAirportCode(mailArrivalVO.getAirportCode());
		customsDefaultsVO.setSource("AUTO");
		Collection<String> airportCodes = new ArrayList<String>();
		airportCodes.add(mailArrivalVO.getAirportCode());
		customsDefaultsVO.setAirportCodes(airportCodes);
		customsDefaultsVO.setBusinessTransactionId(CustomsDefaultsVO.CST_FLT_ARRIVAL_MAIL_ASYNC);
		CustomsDefaultsFilterVO customsDefaultsFilterVO = new CustomsDefaultsFilterVO();
		customsDefaultsFilterVO.setCompanyCode(mailArrivalVO.getCompanyCode());
		customsDefaultsFilterVO.setAirportCode(mailArrivalVO.getAirportCode());
		customsDefaultsFilterVO.setFlightDate(mailArrivalVO.getFlightDate());
		customsDefaultsFilterVO.setFlightCarrierId(mailArrivalVO.getCarrierId());
		customsDefaultsFilterVO.setFlightNumber(mailArrivalVO.getFlightNumber());
		customsDefaultsFilterVO.setFlightSequenceNumber(mailArrivalVO.getFlightSequenceNumber());
		customsDefaultsFilterVO.setBusinessTxnName(CustomsDefaultsVO.CST_FLT_ARRIVAL_MAIL_ASYNC);
		customsDefaultsVO.setCustomsDefaultsFilterVO(customsDefaultsFilterVO);
		return customsDefaultsVO;
	 }

	
	public static MailResditMessageVO populateMailResditMessageVO(Collection<ResditEventVO> resditEventVOs,Collection<MessageVO> messageVOs){
		
		String msgTxt = null;
		MailResditMessageVO mailResditMessageVO = new MailResditMessageVO();
		ResditEventVO resditEventVO = resditEventVOs.iterator().next();
		MessageVO messageVO = messageVOs.iterator().next();
		StringBuilder evtCodes=new StringBuilder();
		/**
		 * Added HashSet of event codes as part of ICRD-344287
		 * Prevent column length exceeds exception if there are multiple events in the message
		 */
		HashSet<String> eventCodesSet = new HashSet<>();
		for(ResditEventVO rdtEventVO:resditEventVOs){
			if(!eventCodesSet.contains(rdtEventVO.getResditEventCode())) {
				eventCodesSet.add(rdtEventVO.getResditEventCode());
			evtCodes.append(rdtEventVO.getResditEventCode()).append(",");
			}
		}
		msgTxt = messageVOs.iterator().next().getRawMessageBlob();
		mailResditMessageVO.setMsgSequenceNumber(messageVOs.iterator().next().getSequenceNumber());
		mailResditMessageVO.setMsgDetails(msgTxt);
        if(msgTxt.length()>4000){
        	mailResditMessageVO.setMsgText(msgTxt.trim().substring(0, 3500));
        }
        else{
        	mailResditMessageVO.setMsgText(msgTxt);
        }
        mailResditMessageVO.setPoaCode(resditEventVO.getPaCode());	
        mailResditMessageVO.setMsgVersionNumber(resditEventVO.getResditVersion());
        mailResditMessageVO.setCarditExist(resditEventVO.getCarditExist());
        mailResditMessageVO.setMessageType(messageVO.getMessageType());
        mailResditMessageVO.setCompanyCode(messageVO.getCompanyCode());
        //mailResditMessageVO.setMessageIdentifier(messageVO.getMessageIdentifierTwo());
        mailResditMessageVO.setEventCode(evtCodes.toString().substring(0, evtCodes.length()-1));
        mailResditMessageVO.setEventDate(resditEventVO.getEventDate());
        mailResditMessageVO.setEventPort(resditEventVO.getEventPort());
        
        
        
		return mailResditMessageVO;
	}

	public static TransferManifestVO constructDSNLevelVOs(TransferManifestVO transferManifestVO) {
		Collection<DSNVO> dsnVOs = new ArrayList();
		String currDSNKey = null;
		String prevDSNKey = null;
		DSNVO dsnVO = null;
		String awbNumber=null;
		if(transferManifestVO!=null && transferManifestVO.getDsnVOs()!=null && !transferManifestVO.getDsnVOs().isEmpty()){
			for(DSNVO dSNVO:transferManifestVO.getDsnVOs()){
				currDSNKey = new StringBuilder().append(transferManifestVO.getCompanyCode())
						.append(transferManifestVO.getTransferManifestId()).append(dSNVO.getDsn())
						.append(dSNVO.getOriginExchangeOffice()).append(dSNVO.getDestinationExchangeOffice())
						.append(dSNVO.getMailSubclass()).append(dSNVO.getMailCategoryCode()).append(dSNVO.getYear())
						.toString();
				if(!currDSNKey.equals(prevDSNKey)){
					dsnVO = dSNVO;
					prevDSNKey=currDSNKey;
					dsnVOs.add(dsnVO);
				}else{
					awbNumber=dSNVO.getAwbNumber();
					dsnVO.setBags(dsnVO.getBags()+dSNVO.getBags());
					try {
						dsnVO.setWeight(Measure.addMeasureValues(dsnVO.getWeight(), dSNVO.getWeight()));
					} catch (UnitException unitException) {
						log.log(Log.INFO,unitException);
					}
					
					if(dsnVO.getAwbNumber()!=null && awbNumber!=null && !awbNumber.isEmpty() && !dsnVO.getAwbNumber().contains(awbNumber)){
					dsnVO.setAwbNumber(dsnVO.getAwbNumber()+" ,"+awbNumber);
					}else if (awbNumber!=null && !awbNumber.isEmpty()){
						dsnVO.setAwbNumber(awbNumber);	
					}
				}
		}
			transferManifestVO.setDsnVOs(dsnVOs);
		}
		return transferManifestVO;
	}

	
	
	/**
	 * Added for CRQ IASCB-74698
	 * @author A-5526
	 * @param mailUploadVO
	 * @return
	 * @throws SystemException 
	 */
	public static List<DocumentRepositoryAttachmentVO> convetToDocumentRepositoryAttachmentVOs(
			MailUploadVO mailUploadVO) throws SystemException {
		List<DocumentRepositoryAttachmentVO> documentRepositoryAttachmentVOs =new ArrayList<>();
		if(mailUploadVO.getAttachments() != null && !mailUploadVO.getAttachments().isEmpty()) {
			
			for(MailAttachmentVO mailAttachmentVO : mailUploadVO.getAttachments()) {
					
				documentRepositoryAttachmentVOs.add(convetToDocumentRepositoryAttachmentVO(mailAttachmentVO,mailUploadVO));
			}
		}			
		return documentRepositoryAttachmentVOs;
	}
	
	/**
	 * Added for CRQ IASCB-74698
	 * @author A-5526
	 * @param mailAttachmentVO
	 * @param mailbagId
	 * @return
	 * @throws SystemException
	 */
	public static DocumentRepositoryAttachmentVO convetToDocumentRepositoryAttachmentVO(
			MailAttachmentVO mailAttachmentVO,MailUploadVO mailUploadVO) throws SystemException {
		
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();			
		    DocumentRepositoryAttachmentVO documentRepositoryAttachmentVO = new DocumentRepositoryAttachmentVO();
		    documentRepositoryAttachmentVO.setCompanyCode(mailAttachmentVO.getCompanyCode());
		    documentRepositoryAttachmentVO.setFileName(mailAttachmentVO.getFileName());
		    documentRepositoryAttachmentVO.setAttachmentData(mailAttachmentVO.getFileData());
		    documentRepositoryAttachmentVO.setAttachmentType(mailAttachmentVO.getAttachmentType());
		    documentRepositoryAttachmentVO.setAirportCode(logonAttributes.getAirportCode());
		    documentRepositoryAttachmentVO.setOperationFlag(mailAttachmentVO.getAttachmentOpFlag());
		    documentRepositoryAttachmentVO.setDocumentRepoSerialNumber(mailAttachmentVO.getDocSerialNumber());
		    documentRepositoryAttachmentVO.setRemarks(mailAttachmentVO.getRemarks());
		    documentRepositoryAttachmentVO.setTxnRefereceOverride(true);
		    documentRepositoryAttachmentVO.setReference1("MALIDR");
		    documentRepositoryAttachmentVO.setTransactionDataRef1(mailUploadVO.getMailTag());
		    documentRepositoryAttachmentVO.setReference2("DMGCOD");
		    documentRepositoryAttachmentVO.setTransactionDataRef2(mailUploadVO.getDamageCode());
		return documentRepositoryAttachmentVO;
		  }
	

	
	
}
