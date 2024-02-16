/*
 * MailOperationsModelConverter.java Created on Jun 08, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

/**
 * Revision History
 * Revision 	 Date      	     Author			Description
 * 0.1		Jun 08, 2018	     A-2257		First draft
 */
package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.DespatchDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailEventVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailMonitorSummaryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigParameterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRuleConfigVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailboxIdVO;
import com.ibsplc.icargo.business.mail.operations.vo.OnwardRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
//import com.ibsplc.icargo.presentation.web.model.mail.operations.ConsignmentDocumentModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailInConsignment;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ConsignmentRouting;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
/*import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;*/
/**
 * 
 * @author A-2257
 *
 */
public class MailOperationsModelConverter {
	
	private static final String OUTBOUND = "O";
	/*private Map<String, String> exchangeOfficeMap;
	private static final String USPS_DOMESTIC_PA = "mailtracking.domesticmra.usps"; */

	public static ArrayList<Mailbag> constructMailbags(Collection<MailbagVO> mailbagVOs) {

		ArrayList<Mailbag> mailbags = new ArrayList<>();

		Mailbag mailbag = null;

		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagvo : mailbagVOs) {

				mailbag = constructMailbag(mailbagvo);
				mailbags.add(mailbag);
			}

		}

		return mailbags;

	}

	public static Mailbag constructMailbag(MailbagVO mailbagvo) {

		ArrayList<Mailbag> mailbags = new ArrayList<>();

		Mailbag mailbag = new Mailbag();


		mailbag.setCompanyCode(mailbagvo.getCompanyCode());
		mailbag.setId(mailbagvo.getMailSequenceNumber());
		mailbag.setMailbagId(mailbagvo.getMailbagId());
		mailbag.setMailSequenceNumber(mailbagvo.getMailSequenceNumber());
		mailbag.setDespatchId(mailbagvo.getDespatchId());
		mailbag.setOoe(mailbagvo.getOoe());
		mailbag.setDoe(mailbagvo.getDoe());
		mailbag.setMailCategoryCode(mailbagvo.getMailCategoryCode());
		mailbag.setMailSubclass(mailbagvo.getMailSubclass());
		mailbag.setMailClass(mailbagvo.getMailClass());
		mailbag.setYear(mailbagvo.getYear());
		mailbag.setDespatchSerialNumber(mailbagvo.getDespatchSerialNumber());
		mailbag.setReceptacleSerialNumber(mailbagvo.getReceptacleSerialNumber());
		mailbag.setRegisteredOrInsuredIndicator(mailbagvo.getRegisteredOrInsuredIndicator());
		mailbag.setHighestNumberedReceptacle(mailbagvo.getHighestNumberedReceptacle());
		if (mailbagvo.getConsignmentDate() != null) {
			mailbag.setConsignmentDate(mailbagvo.getConsignmentDate().toDisplayDateOnlyFormat());
		}

		mailbag.setScannedUser(mailbagvo.getScannedUser());
		mailbag.setCarrierId(mailbagvo.getCarrierId());
		if(mailbagvo.getFlightNumber()!=null && mailbagvo.getFlightNumber().equals("-1")){
			mailbag.setFlightNumber("");
		}else{
			mailbag.setFlightNumber(mailbagvo.getFlightNumber() !=null ? mailbagvo.getFlightNumber() : "");
		}
		mailbag.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
		mailbag.setSegmentSerialNumber(mailbagvo.getSegmentSerialNumber());
		mailbag.setUldNumber(mailbagvo.getUldNumber());
		mailbag.setDespatch(mailbagvo.isDespatch());
		if (mailbagvo.getReqDeliveryTime() != null) {
			mailbag.setReqDeliveryTime(mailbagvo.getReqDeliveryTime().toDisplayTimeOnlyFormat());
			mailbag.setReqDeliveryDate(mailbagvo.getReqDeliveryTime().toDisplayDateOnlyFormat());
			mailbag.setReqDeliveryDateAndTime(mailbagvo.getReqDeliveryTime().toDisplayFormat(true));
		}
		
		if (mailbagvo.getScannedDate() != null) { 
			mailbag.setScannedTime(mailbagvo.getScannedDate().toDisplayTimeOnlyFormat(true));
			mailbag.setScannedDate(mailbagvo.getScannedDate().toDisplayDateOnlyFormat());
		}
		
		mailbag.setShipmentPrefix(mailbagvo.getShipmentPrefix());
		mailbag.setMasterDocumentNumber(mailbagvo.getDocumentNumber());
		mailbag.setDocumentOwnerIdr(mailbagvo.getDocumentOwnerIdr());
		mailbag.setDuplicateNumber(mailbagvo.getDuplicateNumber());
		mailbag.setSequenceNumber(mailbagvo.getSequenceNumber());
		if (mailbagvo.getFlightDate() != null) {
			mailbag.setFlightDate(mailbagvo.getFlightDate().toDisplayDateOnlyFormat());
		}

		mailbag.setOperationalFlag(mailbagvo.getOperationalFlag());
		mailbag.setContainerType(mailbagvo.getContainerType());
		mailbag.setContainerNumber(mailbagvo.getContainerNumber());
		mailbag.setIsoffload(mailbagvo.isIsoffload());
		if (mailbagvo.getScannedDate() != null) {
			mailbag.setScannedDate(mailbagvo.getScannedDate().toDisplayDateOnlyFormat());
		}

		mailbag.setScannedPort(mailbagvo.getScannedPort());
		mailbag.setLatestStatus(mailbagvo.getLatestStatus());
		mailbag.setOperationalStatus(mailbagvo.getOperationalStatus());
		mailbag.setDamageFlag(mailbagvo.getDamageFlag());
		mailbag.setConsignmentNumber(mailbagvo.getConsignmentNumber());
		mailbag.setConsignmentSequenceNumber(mailbagvo.getConsignmentSequenceNumber());
		mailbag.setPaCode(mailbagvo.getPaCode());
		mailbag.setLastUpdateUser(mailbagvo.getLastUpdateUser());
		mailbag.setMailStatus(mailbagvo.getMailStatus());
		if (mailbagvo.getLatestScannedDate() != null) {
			mailbag.setLatestScannedDate(mailbagvo.getLatestScannedDate().toDisplayDateOnlyFormat());
		}

		mailbag.setDisplayUnit(mailbagvo.getDisplayUnit());
		mailbag.setMailRemarks(mailbagvo.getMailRemarks());
		mailbag.setCarrierCode(mailbagvo.getCarrierCode() !=null ? mailbagvo.getCarrierCode() : "");
		mailbag.setPou(mailbagvo.getPou());
		mailbag.setPol(mailbagvo.getPol());
		mailbag.setWeight(mailbagvo.getWeight());
		Measure volume = new Measure(UnitConstants.VOLUME,mailbagvo.getVol()); 
		mailbag.setVolume(volume);
		mailbag.setMailbagWeight(String.valueOf(mailbagvo.getWeight().getRoundedDisplayValue()));
		if(mailbagvo.getVolume()!=null){
			mailbag.setMailbagVolume(String.valueOf(mailbagvo.getVolume().getDisplayValue()));
		}
		mailbag.setAccepted(mailbagvo.getAccepted());
		mailbag.setUpliftAirport(mailbagvo.getUpliftAirport());    
		mailbag.setMailorigin(mailbagvo.getMailOrigin());
		mailbag.setMailDestination(mailbagvo.getMailDestination());
		Collection<MailbagHistory> mailbagHistorylist = constructMailbagHistory(mailbagvo.getMailbagHistories());
        mailbag.setMailbagHistories(mailbagHistorylist);
        mailbag.setDeviationErrors(mailbagvo.getErrorCode());
        if(mailbagvo.getActualWeight()!=null){
          mailbag.setActualWeight(mailbagvo.getActualWeight().getRoundedDisplayValue());
          mailbag.setActualWeightMeasure(mailbagvo.getActualWeight());
          mailbag.setActualWeightUnit(mailbagvo.getActualWeight().getDisplayUnit()); 
          //added by A-7815 as part of IASCB-39187
        	       	
        }  
		  
        //Added by A-8464 for ICRD-243079
        //Modified for ICRD-323389 starts
        if(MailConstantsVO.FLAG_YES.equals(mailbagvo.getOnTimeDelivery()))
        	mailbag.setOnTimeDelivery("Yes");
        else if(MailConstantsVO.FLAG_NO.equals(mailbagvo.getOnTimeDelivery()))
        	mailbag.setOnTimeDelivery("No");
        else
        	mailbag.setOnTimeDelivery("");
      //Modified for ICRD-323389 ends
        mailbag.setServicelevel(mailbagvo.getMailServiceLevel());
        mailbag.setArrivedFlag(mailbagvo.getArrivedFlag());
        mailbag.setDeliveredFlag(mailbagvo.getDeliveredFlag());
        mailbag.setDisplayWeight(mailbagvo.getWeight().getRoundedDisplayValue());
        if (mailbagvo.getScannedDate() != null) {
			mailbag.setActDeliveryDateAndTime(mailbagvo.getScannedDate().toDisplayFormat(true));
		}
        mailbag.setMailbagVolume(Double.toString(mailbagvo.getVol()));
        mailbag.setPaBuiltFlag(mailbagvo.getPaBuiltFlag());
        if (mailbagvo.getTransWindowEndTime() != null) {
			mailbag.setTransportSrvWindow(mailbagvo.getTransWindowEndTime().toDisplayFormat("dd-MMM-yyyy HH:mm"));
		}
        mailbag.setMailbagDataSource(mailbagvo.getMailbagDataSource());
        mailbag.setBellyCartId(mailbagvo.getBellyCartId());
        mailbag.setImportMailbag(mailbagvo.isImportMailbag());
		mailbags.add(mailbag);

		return mailbag;

	}

	public static ArrayList<MailbagVO> constructMailbagVOs(Collection<Mailbag> mailbagDetails,
			LogonAttributes logonAttributes) {

		ArrayList<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();

		MailbagVO mailbagVO = null;

		if (mailbagDetails != null && mailbagDetails.size() > 0) {
			for (Mailbag mailbagDetail : mailbagDetails) {

				mailbagVO = constructMailbagVO(mailbagDetail, logonAttributes);

				mailbagVOs.add(mailbagVO);
			}

		}

		return mailbagVOs;

	}

	public static MailbagVO constructMailbagVO(Mailbag mailbagDetail, LogonAttributes logonAttributes) {

		MailbagVO mailbagVO = new MailbagVO();
		Collection<DamagedMailbagVO> damagedMailbagVOs = null;
		Collection<MailbagHistoryVO> mailHistoriesVOs = null;
				
		mailbagVO.setCompanyCode(mailbagDetail.getCompanyCode());
		mailbagVO.setMailbagId(mailbagDetail.getMailbagId());
		mailbagVO.setMailSequenceNumber(mailbagDetail.getMailSequenceNumber());
		mailbagVO.setDespatchId(mailbagDetail.getDespatchId());
		mailbagVO.setOoe(mailbagDetail.getOoe());
		mailbagVO.setDoe(mailbagDetail.getDoe());
		mailbagVO.setMailCategoryCode(mailbagDetail.getMailCategoryCode());
		mailbagVO.setMailSubclass(mailbagDetail.getMailSubclass());
		mailbagVO.setMailClass(mailbagDetail.getMailClass());
		mailbagVO.setYear(mailbagDetail.getYear());
		mailbagVO.setDespatchSerialNumber(mailbagDetail.getDespatchSerialNumber());
		mailbagVO.setReceptacleSerialNumber(mailbagDetail.getReceptacleSerialNumber());
		mailbagVO.setRegisteredOrInsuredIndicator(mailbagDetail.getRegisteredOrInsuredIndicator());
		mailbagVO.setHighestNumberedReceptacle(mailbagDetail.getHighestNumberedReceptacle());

		LocalDate consignmentDate = null;
		if (mailbagDetail.getConsignmentDate() != null) {

			consignmentDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			consignmentDate.setDate(mailbagDetail.getConsignmentDate());
			mailbagVO.setConsignmentDate(consignmentDate);
		}

		mailbagVO.setScannedUser(mailbagDetail.getScannedUser());
		mailbagVO.setCarrierId(mailbagDetail.getCarrierId());
		//Added as part of IASCB-45097
		if("".equals(mailbagDetail.getFlightNumber()) && mailbagDetail.getFlightSequenceNumber()==-1 ){
			mailbagVO.setFlightNumber("-1");
		}else{
		mailbagVO.setFlightNumber(mailbagDetail.getFlightNumber());
		}
		if(mailbagDetail.getFlightSequenceNumber()==0){
			mailbagVO.setFlightSequenceNumber(-1);
		}else{
			mailbagVO.setFlightSequenceNumber(mailbagDetail.getFlightSequenceNumber());
		}
		mailbagVO.setSegmentSerialNumber(mailbagDetail.getSegmentSerialNumber());
		mailbagVO.setUldNumber(mailbagDetail.getUldNumber());
		mailbagVO.setDespatch(mailbagDetail.isDespatch());
		mailbagVO.setOriginUpdate(mailbagDetail.isOriginUpdate());
		mailbagVO.setDestinationUpdate(mailbagDetail.isDestinationUpdate());
		/*if (mailbagDetail.getReqDeliveryDate() != null) {
        LocalDate rqdDlvTim=new LocalDate(logonAttributes.getAirportCode(), ARP, false);
		StringBuilder reqDeliveryTime=new StringBuilder(mailbagDetail.getReqDeliveryDate());
		if(mailbagDetail.getReqDeliveryTime()!=null&&
				mailbagDetail.getReqDeliveryTime().trim().length()>0){
			rqdDlvTim.setDateAndTime(reqDeliveryTime.toString());
		}else{
			rqdDlvTim.setDate(reqDeliveryTime.toString());
		} 
		mailbagVO.setReqDeliveryTime(rqdDlvTim);
		}
*/
		mailbagVO.setShipmentPrefix(mailbagDetail.getShipmentPrefix());
		mailbagVO.setDocumentNumber(mailbagDetail.getMasterDocumentNumber());
		mailbagVO.setDocumentOwnerIdr(mailbagDetail.getDocumentOwnerIdr());
		mailbagVO.setDuplicateNumber(mailbagDetail.getDuplicateNumber());
		mailbagVO.setSequenceNumber(mailbagDetail.getSequenceNumber());

		LocalDate flightDate = null;
		if (mailbagDetail.getFlightDate() != null) {

			flightDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			flightDate.setDate(mailbagDetail.getFlightDate());
			mailbagVO.setFlightDate(flightDate);
		}

		mailbagVO.setOperationalFlag(mailbagDetail.getOperationalFlag());
		mailbagVO.setContainerType(mailbagDetail.getContainerType());
		mailbagVO.setContainerNumber(mailbagDetail.getContainerNumber());
		mailbagVO.setIsoffload(mailbagDetail.isIsoffload());

		LocalDate scanDate = null;
		if (mailbagDetail.getScannedDate() != null
				&& mailbagDetail.getScannedDate().trim().length()>0) {

			scanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			scanDate.setDate(mailbagDetail.getScannedDate());
			mailbagVO.setScannedDate(scanDate);
		}

		mailbagVO.setScannedPort(mailbagDetail.getScannedPort());
		mailbagVO.setLatestStatus(mailbagDetail.getLatestStatus());
		mailbagVO.setOperationalStatus(mailbagDetail.getOperationalStatus());
		mailbagVO.setDamageFlag(mailbagDetail.getDamageFlag());
		mailbagVO.setConsignmentNumber(mailbagDetail.getConsignmentNumber());
		mailbagVO.setConsignmentSequenceNumber(mailbagDetail.getConsignmentSequenceNumber());
		mailbagVO.setPaCode(mailbagDetail.getPaCode());
		mailbagVO.setLastUpdateUser(mailbagDetail.getLastUpdateUser());
		mailbagVO.setMailStatus(mailbagDetail.getLatestStatus());

		LocalDate latestScanDate = null;
		if (mailbagDetail.getLatestScannedDate() != null) {

			latestScanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
			latestScanDate.setDate(mailbagDetail.getLatestScannedDate());
			mailbagVO.setLatestScannedDate(latestScanDate);
		}

		mailbagVO.setDisplayUnit(mailbagDetail.getDisplayUnit());
		mailbagVO.setMailRemarks(mailbagDetail.getMailRemarks());
		mailbagVO.setCarrierCode(mailbagDetail.getCarrierCode());
		mailbagVO.setPou(mailbagDetail.getPou());
		mailbagVO.setPol(mailbagDetail.getPol());
		mailbagVO.setWeight(mailbagDetail.getWeight());
		mailbagVO.setVolume(mailbagDetail.getVolume());
		mailbagVO.setUpliftAirport(mailbagDetail.getUpliftAirport());
		mailbagVO.setMailOrigin(mailbagDetail.getMailorigin());
		mailbagVO.setMailDestination(mailbagDetail.getMailDestination());

		damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();

		if (mailbagDetail.getDamagedMailbags() != null && mailbagDetail.getDamagedMailbags().size() > 0) {
			
			damagedMailbagVOs = constructDamagedMailbagVOs(mailbagDetail.getDamagedMailbags(), logonAttributes);
			mailbagVO.setDamagedMailbags(damagedMailbagVOs);
		}
         if (mailbagDetail.getMailbagHistories() != null && mailbagDetail.getMailbagHistories().size() > 0) {

        	 mailHistoriesVOs = constructMailHistoryVOs(mailbagDetail.getMailbagHistories(), logonAttributes);
			mailbagVO.setMailbagHistories(mailHistoriesVOs);
         }

         //Added by A-8464 for ICRD-243079
         mailbagVO.setOnTimeDelivery(mailbagDetail.getOnTimeDelivery());
         mailbagVO.setMailServiceLevel(mailbagDetail.getServicelevel());
         mailbagVO.setArrivedFlag(mailbagDetail.getArrivedFlag());
         Measure actualWeight =null;
         if(mailbagDetail.getActualWeightUnit()!=null){
        	 actualWeight = new Measure(UnitConstants.MAIL_WGT, 0,mailbagDetail.getActualWeight(),mailbagDetail.getActualWeightUnit());
         }
         else if(Objects.nonNull(mailbagDetail.getWeight())){
        	 actualWeight = new Measure(UnitConstants.MAIL_WGT, 0,mailbagDetail.getActualWeight(),mailbagDetail.getWeight().getDisplayUnit());  
         }
         mailbagVO.setActualWeight(actualWeight);//added by a-7779 for ICRD-326752 
         mailbagVO.setPaBuiltFlag(mailbagDetail.getPaBuiltFlag());
         mailbagVO.setAcceptancePostalContainerNumber(mailbagDetail.getAcceptancePostalContainerNumber());
         mailbagVO.setAcceptanceAirportCode(mailbagDetail.getAcceptancePostalAirportCode());
         mailbagVO.setPaBuiltFlagUpdate(mailbagDetail.isPaBuiltFlagUpdate());
         mailbagVO.setPaContainerNumberUpdate(mailbagDetail.isPaContainerNumberUpdate());
         mailbagVO.setTransferFromCarrier(mailbagDetail.getTransferFromCarrier());
		 if(logonAttributes.isGHAUser() && !logonAttributes.isOwnSalesAgent()){
         mailbagVO.setGHAUser(logonAttributes.isGHAUser());
		  }
		return mailbagVO;

	}
	public static ContainerDetails constructContainer(ContainerVO containerVO, LogonAttributes logonAttributes) {

			 
			ContainerDetails container = new ContainerDetails();
		    container.setUldReferenceNo(containerVO.getUldReferenceNo());
		    container.setHbaMarked(containerVO.getHbaMarked());
		    container.setExpClsflg(containerVO.getExpClsflg());
			container.setContainerNumber(containerVO.getContainerNumber()) ;
			container.setType(containerVO.getType());
			container.setRemarks(containerVO.getRemarks());		
			container.setPaBuiltFlag(containerVO.getPaBuiltFlag());
			container.setOperationFlag(containerVO.getOperationFlag());		
			container.setCarrierId(containerVO.getCarrierId());		
			container.setFlightNumber(containerVO.getFlightNumber());  
			if(containerVO.getFlightDate()!=null){
			container.setFlightDate(containerVO.getFlightDate().toDisplayDateOnlyFormat());
			}
			container.setFlightSequenceNumber(containerVO.getFlightSequenceNumber());
			container.setSegmentSerialNumber(containerVO.getSegmentSerialNumber());		
			container.setLegSerialNumber(containerVO.getLegSerialNumber());		
			container.setCompanyCode(containerVO.getCompanyCode());		
			container.setAssignedPort(containerVO.getAssignedPort());		
			container.setAssignmentFlag(containerVO.getAssignmentFlag());
			container.setLastUpdateUser(containerVO.getLastUpdateUser());
			container.setAcceptanceFlag(containerVO.getAcceptanceFlag());
			container.setCarrierCode(containerVO.getCarrierCode());		   
			container.setAssignedBy(containerVO.getAssignedUser());
			container.setFlightClosureCheckNeeded(containerVO.isFlightClosureCheckNeeded());	
			 //container.setAirportCode(logonAttributes.getAssignedPort()); 
			container.setAirportCode(logonAttributes.getAirportCode());//to do
			container.setPou(containerVO.getPou());
			container.setPol(containerVO.getPol());		
			container.setDestination(containerVO.getFinalDestination());
			container.setOffloadFlag(containerVO.getOffloadFlag());
			container.setOwnAirlineCode(containerVO.getOwnAirlineCode());		
			container.setOwnAirlineId(containerVO.getOwnAirlineId());
			 container.setOnwadRoute(containerVO.getOnwardRoute());
			container.setArrivedStatus(containerVO.getArrivedStatus());		
			container.setTransferFlag(containerVO.getTransferFlag());	
			container.setPaBuiltOpenedFlag(containerVO.getPaBuiltOpenedFlag());
			container.setContainerSealNumber(containerVO.getContainerSealNumber());		
			container.setFromCarrier(containerVO.getFromCarrier());	
			container.setFromFltNum(containerVO.getFromFltNum());		
			container.setFlightStatus(containerVO.getFlightStatus());
			container.setDeliveredStatus(containerVO.getDeliveredStatus());
			container.setContainerJnyID(containerVO.getContainerJnyID());	
			container.setIntact(containerVO.getIntact());	
			container.setTransitFlag(containerVO.getTransitFlag());
			container.setReleasedFlag(containerVO.getReleasedFlag());	
			container.setUldFulIndFlag(containerVO.getUldFulIndFlag());	
			if(containerVO.getPlannedFlightCarrierCode()!=null && containerVO.getPlannedFlightNum()!=0 && //new
					containerVO.getPlannedFlightDate()!=null) {
				String plnfltanddat =new StringBuilder().append(containerVO.getPlannedFlightCarrierCode()).append(" ")
						.append(containerVO.getPlannedFlightNum()).append(" ")
						.append(containerVO.getPlannedFlightDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT).toUpperCase()).toString();
				container.setPlannedFlightAndDate(plnfltanddat);
					
			}
			if (containerVO.getScannedDate()!=null ) {	
				
				container.setScannedDate(containerVO.getScannedDate().toDisplayDateOnlyFormat());
			}
			
			container.setMailSource(containerVO.getMailSource());	
			container.setSource(containerVO.getSource());
			container.setFinalDestination(containerVO.getFinalDestination());
			container.setBags(containerVO.getBags());	 
	        if(containerVO.getWeight()!=null){
			 //container.setWeight(containerVO.getWeight().getFormattedDisplayValue());   
	        	container.setWeight(containerVO.getWeight().getRoundedDisplayValue());
			}
			
			//Added by A-7929 as part of ICRD-269984 starts---
			if(containerVO.getActualWeight()!=null){
				/*UnitConversionNewVO unitConversionVO= null;
	        	double toDisplayValue=0; 
	        	try {
		               unitConversionVO=
		            		   UnitFormatter.getUnitConversionForToUnit(UnitConstants.WEIGHT, containerVO.getActualWeight().getSystemUnit() ,
		            				   containerVO.getActualWeightUnit(), containerVO.getActualWeight().getSystemValue()); 
		        } catch (UnitException e) {
		              e.getMessage();
		        }
	        	toDisplayValue=Math.round(unitConversionVO.getToValue() * 100.0) / 100.0;*/
	        	/*Measure actualWeight = 
	        			new Measure(UnitConstants.MAIL_WGT,containerVO.getActualWeight().getRoundedSystemValue());*/
				container.setActualWeight(containerVO.getActualWeight().getRoundedDisplayValue());	
				container.setActualWeightMeasure(containerVO.getActualWeight());
				container.setActualWeightUnit(containerVO.getActualWeight().getDisplayUnit());
				}
			container.setLastUpdateTime(containerVO.getLastUpdateTime());
			//modified as part of IASCB-45668
			container.setAssignedOn(containerVO.getAssignedDate().toDisplayFormat(true));
			container.setUldLastUpdateTime(containerVO.getULDLastUpdateTime()); 
			//Added by A-7929 as part of ICRD-269984 ends---
			container.setContentId(containerVO.getContentId()); //Added by A-7929 as part of ICRD-219699
			container.setOffloadedInfo(containerVO.getOffloadedInfo());
			container.setOffloadCount(containerVO.getOffloadCount());
			container.setAssignedOnInMilliSec(containerVO.getAssignedDate().getTimeInMillis());
			if(containerVO.getFlightNumber().equals("-1")){
				container.setFlightDetail(containerVO.getCarrierCode());
			}else{
				container.setFlightDetail(containerVO.getCarrierCode()+"-"+containerVO.getFlightNumber());
			}
			container.setNoOfDaysInCurrentLoc(containerVO.getNoOfDaysInCurrentLoc());
			container.setSubclassGroup(containerVO.getSubclassGroup());
			
			if(containerVO.getPlannedFlightAndDate()!=null) {
				container.setPlannedFlightAndDate(containerVO.getPlannedFlightAndDate());
			}
			container.setBaseCurrency(containerVO.getBaseCurrency());
			container.setRateAvailforallMailbags(containerVO.getRateAvailforallMailbags());
			if(containerVO.getProvosionalCharge()!=null) {
				container.setProvosionalCharge(containerVO.getProvosionalCharge().getAmount());
			}
			if(containerVO.getAcceptedPort()!=null){
				container.setAcceptedPort(containerVO.getAcceptedPort());
	        }
			return container;

		}

	public static ContainerVO constructContainerVO(ContainerDetails containerDetails, LogonAttributes logonAttributes) {

		ContainerVO containerVO = new ContainerVO();
		
		containerVO.setContainerNumber(containerDetails.getContainerNumber()) ;
		containerVO.setType(containerDetails.getType());
		containerVO.setRemarks(containerDetails.getRemarks());		
		containerVO.setPaBuiltFlag(containerDetails.getPaBuiltFlag());
		containerVO.setOperationFlag(containerDetails.getOperationFlag());		
		containerVO.setCarrierId(containerDetails.getCarrierId());		
		containerVO.setFlightNumber(containerDetails.getFlightNumber());
		containerVO.setFlightSequenceNumber(containerDetails.getFlightSequenceNumber());
		containerVO.setSegmentSerialNumber(containerDetails.getSegmentSerialNumber());		
		containerVO.setLegSerialNumber(containerDetails.getLegSerialNumber());		
		containerVO.setCompanyCode(containerDetails.getCompanyCode());		
		containerVO.setAssignedPort(containerDetails.getAssignedPort());		
		containerVO.setAssignmentFlag(containerDetails.getAssignmentFlag());
		containerVO.setLastUpdateUser(containerDetails.getLastUpdateUser());
		containerVO.setAcceptanceFlag(containerDetails.getAcceptanceFlag());
		containerVO.setCarrierCode(containerDetails.getCarrierCode());	
		containerVO.setFlightClosureCheckNeeded(containerDetails.isFlightClosureCheckNeeded());	
		containerVO.setPou(containerDetails.getPou());
		containerVO.setPol(containerDetails.getPol());		
		containerVO.setOffloadFlag(containerDetails.getOffloadFlag());
		containerVO.setOwnAirlineCode(containerDetails.getOwnAirlineCode());		
		containerVO.setOwnAirlineId(containerDetails.getOwnAirlineId());
		containerVO.setArrivedStatus(containerDetails.getArrivedStatus());		
		containerVO.setTransferFlag(containerDetails.getTransferFlag());	
		containerVO.setPaBuiltOpenedFlag(containerDetails.getPaBuiltOpenedFlag());
		containerVO.setContainerSealNumber(containerDetails.getContainerSealNumber());		
		containerVO.setFromCarrier(containerDetails.getFromCarrier());	
		containerVO.setFromFltNum(containerDetails.getFromFltNum());		
		containerVO.setFlightStatus(containerDetails.getFlightStatus());
		containerVO.setDeliveredStatus(containerDetails.getDeliveredStatus());
		containerVO.setContainerJnyID(containerDetails.getContainerJnyID());	
		containerVO.setIntact(containerDetails.getIntact());	
		containerVO.setTransitFlag(containerDetails.getTransitFlag());
		containerVO.setReleasedFlag(containerDetails.getReleasedFlag());	
	    containerVO.setContentId(containerDetails.getContentId());  //Added by A-7929 as part of ICRD-219699
	    containerVO.setUldTobarrow(containerDetails.isUldTobarrow());
	    containerVO.setUldFulIndFlag(containerDetails.getUldFulIndFlag());
	    containerVO.setActWgtSta(containerDetails.getActWgtSta());
		LocalDate scanDate = null;
		
		if (containerDetails.getScannedDate()!=null && containerDetails.getScannedDate().trim().length()>0) {
			
			
			scanDate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
			
			containerVO.setScannedDate(scanDate);
		}
		
		containerVO.setMailSource(containerDetails.getMailSource());	
		containerVO.setSource(containerDetails.getSource());
		containerVO.setFinalDestination(containerDetails.getFinalDestination());
		containerVO.setBags(containerDetails.getBags());
		containerVO.setAssignedUser(containerDetails.getAssignedBy());
		
		//Added by A-7929 as part of ICRD-269984 starts...
		if(containerDetails.getFlightDate()!=null) {
		containerVO.setFlightDate(new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
				 containerDetails.getFlightDate()));
		}
		//Added by A-7540 for IASCB-25432
		Measure actualWeight =null;
        if(containerDetails.getActualWeightUnit()!=null){
       	 actualWeight = new Measure(UnitConstants.MAIL_WGT, 0,containerDetails.getActualWeight(),containerDetails.getActualWeightUnit());
        }
       /* else{
       	 actualWeight = new Measure(UnitConstants.MAIL_WGT, 0,containerDetails.getActualWeight(),containerDetails.getWeight().getDisplayUnit());  
        }*/
		containerVO.setActualWeight(actualWeight);
		if(containerDetails.getAssignedOn()!=null){
			String assignedOn = null;
			String assignedPort = null;
			if(containerDetails.getAssignedOn().trim().length()==17) {
				assignedOn = containerDetails.getAssignedOn().substring(0, 11);
			} else {
				assignedOn = containerDetails.getAssignedOn();
			}
			
			if(containerDetails.getAssignedPort()!=null && containerDetails.getAssignedPort().trim().length()>0) {
				assignedPort = containerDetails.getAssignedPort();
			} else {
				assignedPort = logonAttributes.getAirportCode();
			}
			containerVO.setAssignedDate(new LocalDate(assignedPort,ARP,false).setDate(
					assignedOn));
		}
		Measure weight=new Measure(UnitConstants.MAIL_WGT,containerDetails.getWeight());
		containerVO.setWeight(weight);
		containerVO.setLastUpdateTime(containerDetails.getLastUpdateTime()); 
		containerVO.setULDLastUpdateTime(containerDetails.getUldLastUpdateTime());
		//Added by A-7929 as part of ICRD-269984 ends...
		
		//Added by A-8672 as part of ICRD-269984 starts...
		containerVO.setOnwardRoutings(containerDetails.getOnwardRoutings());
		//Added by A-8672 as part of ICRD-269984 ends...
		containerVO.setTransferAudit(true);
		
		return containerVO;

	}
	public static Collection<DamagedMailbagVO> constructDamagedMailbagVOs(Collection<DamagedMailbag> damagedMailbags, LogonAttributes logonAttributes) {

		Collection<DamagedMailbagVO> damagedMailbagVOs = new ArrayList<DamagedMailbagVO>();
		
		DamagedMailbagVO damagedMailbagVO = null;
		
		if (damagedMailbags!=null && damagedMailbags.size()>0){
			for(DamagedMailbag damagedMailbag : damagedMailbags){
				
				damagedMailbagVO = constructDamagedMailbagVO(damagedMailbag,logonAttributes);
				damagedMailbagVOs.add(damagedMailbagVO);
			}
			
			
		}		
		
		return damagedMailbagVOs;

	}
	
	public static DamagedMailbagVO constructDamagedMailbagVO(DamagedMailbag damagedMailbag, LogonAttributes logonAttributes) {

		DamagedMailbagVO damagedMailbagVO = new DamagedMailbagVO();
		damagedMailbagVO.setCompanyCode(damagedMailbag.getCompanyCode());
		damagedMailbagVO.setDamageCode(damagedMailbag.getDamageCode());
		damagedMailbagVO.setRemarks(damagedMailbag.getRemarks());
		damagedMailbagVO.setDamageDescription(damagedMailbag.getDamageDescription());
		damagedMailbagVO.setAirportCode(damagedMailbag.getAirportCode());

		LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		damagedMailbagVO.setDamageDate(currentdate);
		damagedMailbagVO.setUserCode(damagedMailbag.getUserCode());
		damagedMailbagVO.setOperationType(damagedMailbag.getOperationType());
		damagedMailbagVO.setOperationFlag(damagedMailbag.getOperationFlag());
		damagedMailbagVO.setPaCode(damagedMailbag.getPaCode());
		damagedMailbagVO.setReturnedFlag(damagedMailbag.getReturnedFlag());

		return damagedMailbagVO;

	}
	
	public static Collection<DamagedMailbag> constructDamagedMailbagsDetails(Collection<DamagedMailbagVO> damagedMailbagVOs, LogonAttributes logonAttributes) {

		Collection<DamagedMailbag> damagedMailbags = new ArrayList<DamagedMailbag>();
		DamagedMailbag damagedMailbag = null;
		
		if(damagedMailbagVOs!=null && damagedMailbagVOs.size()>0){
			
			for(DamagedMailbagVO damagedMailbagVO : damagedMailbagVOs){
				
				damagedMailbag = constructDamagedMailbagDetails(damagedMailbagVO,logonAttributes);
				damagedMailbags.add(damagedMailbag);
			}
			
		}
		
		return damagedMailbags;

	}
	
	public static DamagedMailbag constructDamagedMailbagDetails(DamagedMailbagVO damagedMailbagVO, LogonAttributes logonAttributes) {

		DamagedMailbag damagedMailbag = new DamagedMailbag();
		damagedMailbag.setCompanyCode(damagedMailbagVO.getCompanyCode());
		damagedMailbag.setDamageCode(damagedMailbagVO.getDamageCode());
		damagedMailbag.setRemarks(damagedMailbagVO.getRemarks());
		damagedMailbag.setDamageDescription(damagedMailbagVO.getDamageDescription());
		damagedMailbag.setAirportCode(damagedMailbagVO.getAirportCode());
		damagedMailbag.setDamageDate(damagedMailbagVO.getDamageDate());
		damagedMailbag.setUserCode(damagedMailbagVO.getUserCode());
		damagedMailbag.setOperationType(damagedMailbagVO.getOperationType());
		damagedMailbag.setOperationFlag(damagedMailbagVO.getOperationFlag());
		damagedMailbag.setPaCode(damagedMailbagVO.getPaCode());
		damagedMailbag.setReturnedFlag(damagedMailbagVO.getReturnedFlag());
		damagedMailbag.setFileName(damagedMailbagVO.getFileName());
		damagedMailbag.setMailbagId(damagedMailbagVO.getMailbagId());
		return damagedMailbag;

	}
	
	public static Map<String, Collection<OneTime>> constructOneTimeValues(
			Map<String, Collection<OneTimeVO>> oneTimeValues) {
		HashMap<String, Collection<OneTime>> oneTimeValuesMap = new HashMap<String, Collection<OneTime>>();
		for (Map.Entry<String, Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()) {
			ArrayList<OneTime> oneTimes = new ArrayList<OneTime>();
			for (OneTimeVO oneTimeVO : oneTimeValue.getValue()) {
				OneTime oneTime = new OneTime();
				oneTime.setFieldType(oneTimeVO.getFieldType());
				oneTime.setFieldValue(oneTimeVO.getFieldValue());
				oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
				oneTimes.add(oneTime);
			}
			oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
		}
		return oneTimeValuesMap;
	}
	
	public static FlightValidation constructFlightValidation(FlightValidationVO flightValidationVO,
			LogonAttributes logonAttributes) {
		FlightValidation flightValidation = new FlightValidation();
		flightValidation.setCompanyCode(flightValidationVO.getCompanyCode());
		flightValidation.setAircraftType(flightValidationVO.getAircraftType());
		flightValidation.setFlightCarrierId(flightValidationVO.getFlightCarrierId());
		flightValidation.setFlightNumber(flightValidationVO.getFlightNumber());
		flightValidation.setFlightRoute(flightValidationVO.getFlightRoute());
		flightValidation.setFlightSequenceNumber(flightValidationVO.getFlightSequenceNumber());
		flightValidation.setLegSerialNumber(flightValidationVO.getLegSerialNumber());
		flightValidation.setLegStatus(flightValidationVO.getLegStatus());
		flightValidation.setTBADueRouteChange(flightValidationVO.isTBADueRouteChange());	

		if (flightValidationVO.getFlightDate()!= null) {
			
			flightValidation.setFlightDate(flightValidationVO.getFlightDate().toDisplayDateOnlyFormat());
		}
		
		LocalDate localDate = null;

		if (flightValidationVO.getAtd() != null) {
			localDate = flightValidationVO.getAtd();
			flightValidation.setDepartureTime(localDate.toDisplayTimeOnlyFormat());
		} else if (flightValidationVO.getEtd() != null) {
			localDate = flightValidationVO.getEtd();
			flightValidation.setDepartureTime(localDate.toDisplayTimeOnlyFormat());
		} else {
			localDate = flightValidationVO.getStd();
			flightValidation.setDepartureTime(localDate.toDisplayTimeOnlyFormat());
		}
		if (flightValidationVO.getApplicableDateAtRequestedAirport() != null)
			flightValidation.setApplicableDateAtRequestedAirport(
					flightValidationVO.getApplicableDateAtRequestedAirport().toDisplayFormat());
		flightValidation.setFlightType(flightValidationVO.getFlightType());
		flightValidation.setCarrierCode(flightValidationVO.getCarrierCode());
		flightValidation.setStd(flightValidationVO.getStd().toDisplayFormat());
		if (flightValidationVO.getAtd() != null)
			flightValidation.setAtd(flightValidationVO.getAtd().toDisplayFormat());
		flightValidation.setTailNumber(flightValidationVO.getTailNumber());
		flightValidation.setFlightStatus(flightValidationVO.getFlightStatus());
		flightValidation.setOperationalStatus(flightValidationVO.getOperationalStatus());
		return flightValidation;
	}
	 //Added by A-7929 as part Of ICRD-241437 starts...
	public static ArrayList<ContainerDetails> constructContainer(Collection<ContainerVO> containerVOs,LogonAttributes logonAttributes){
		
		ArrayList<ContainerDetails> containers = new ArrayList<ContainerDetails>();		
		if(containerVOs!=null && containerVOs.size()>0){
			for( ContainerVO containerVO : containerVOs){				
				containers.add(constructContainer(containerVO,logonAttributes));
			}			
		}		
		return containers;              
	}
	
	public static ArrayList<MailbagHistory> constructMailbagHistory(Collection<MailbagHistoryVO> mailbagHistoryVOs) {
		ArrayList<MailbagHistory> mailbagHistoryList= new ArrayList<MailbagHistory>();	
		MailbagHistory mailbagHistory = null;
		if (mailbagHistoryVOs!=null && mailbagHistoryVOs.size()>0){
			for(MailbagHistoryVO mailbagHistoryvo : mailbagHistoryVOs){
				mailbagHistory = constructMailbagHistory(mailbagHistoryvo);
				mailbagHistoryList.add(mailbagHistory);
			}
		}		
		return mailbagHistoryList;
	}
	public static MailbagHistory constructMailbagHistory(MailbagHistoryVO MailbagHistoryvo) {
		MailbagHistory mailbagHistory = new MailbagHistory();
		mailbagHistory.setCompanyCode(MailbagHistoryvo.getCompanyCode());
		mailbagHistory.setAdditionalInfo(MailbagHistoryvo.getAdditionalInfo());
		mailbagHistory.setAirportCode(MailbagHistoryvo.getAirportCode());
		mailbagHistory.setCarditKey(MailbagHistoryvo.getCarditKey());
		mailbagHistory.setCarrierId(MailbagHistoryvo.getCarrierId());
		mailbagHistory.setCarrierCode(MailbagHistoryvo.getCarrierCode());
		mailbagHistory.setContainerNumber(MailbagHistoryvo.getContainerNumber());
		mailbagHistory.setContainerType(MailbagHistoryvo.getContainerType());
		mailbagHistory.setDeliveryStatus(MailbagHistoryvo.getDeliveryStatus());
		mailbagHistory.setDestination(MailbagHistoryvo.getDestination());
		mailbagHistory.setDestinationExchangeOffice(MailbagHistoryvo.getDestinationExchangeOffice());
		mailbagHistory.setDsn(MailbagHistoryvo.getDsn());
		mailbagHistory.setEventCode(MailbagHistoryvo.getEventCode());
		mailbagHistory.setEventDate(MailbagHistoryvo.getEventDate());
		mailbagHistory.setFirstFlight(MailbagHistoryvo.getFirstFlight());
		mailbagHistory.setFlightDate(MailbagHistoryvo.getFlightDate());
		mailbagHistory.setFlightNumber(MailbagHistoryvo.getFlightNumber());
		mailbagHistory.setFlightDate(MailbagHistoryvo.getFlightDate());
		mailbagHistory.setFlightSequenceNumber(MailbagHistoryvo.getFlightSequenceNumber());
		mailbagHistory.setHistorySequenceNumber(MailbagHistoryvo.getHistorySequenceNumber());
		mailbagHistory.setMailbagId(MailbagHistoryvo.getMailbagId());
		mailbagHistory.setMailBoxId(MailbagHistoryvo.getMailBoxId());
		mailbagHistory.setMailCategoryCode(MailbagHistoryvo.getMailCategoryCode());
		mailbagHistory.setMailClass(MailbagHistoryvo.getMailClass());
		mailbagHistory.setMailCategoryCode(MailbagHistoryvo.getMailCategoryCode());
		mailbagHistory.setMailSource(MailbagHistoryvo.getMailSource());
		mailbagHistory.setMailStatus(MailbagHistoryvo.getMailStatus());
		mailbagHistory.setMasterDocumentNumber(MailbagHistoryvo.getMailBoxId());
		mailbagHistory.setMessageSequenceNumber(MailbagHistoryvo.getMessageSequenceNumber());
		mailbagHistory.setMessageTime(MailbagHistoryvo.getMessageTime());
		mailbagHistory.setMessageTimeDisplay(MailbagHistoryvo.getMessageTime().toDisplayDateOnlyFormat());
		mailbagHistory.setProcessedStatus(MailbagHistoryvo.getProcessedStatus());
		return mailbagHistory;
	}
	public static Collection<MailbagHistoryVO> constructMailHistoryVOs(Collection<MailbagHistory> mailbagHistories, LogonAttributes logonAttributes) {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = new ArrayList<MailbagHistoryVO>();
		MailbagHistoryVO mailbagHistoryVO = null;
		if (mailbagHistories!=null && mailbagHistories.size()>0){
			for(MailbagHistory mailbagHistory : mailbagHistories){
				mailbagHistoryVO = constructMailbagHistoryVO(mailbagHistory,logonAttributes);
				mailbagHistoryVOs.add(mailbagHistoryVO);
			}
		}		
		return mailbagHistoryVOs;
	}
	public static MailbagHistoryVO constructMailbagHistoryVO(MailbagHistory mailbagHistory, LogonAttributes logonAttributes) {
		MailbagHistoryVO mailbagHistoryVO = new MailbagHistoryVO();
		mailbagHistoryVO.setCompanyCode(mailbagHistory.getCompanyCode());
		mailbagHistoryVO.setAdditionalInfo(mailbagHistory.getAdditionalInfo());
		mailbagHistoryVO.setAirportCode(mailbagHistory.getAirportCode());
		mailbagHistoryVO.setCarditKey(mailbagHistory.getCarditKey());
		mailbagHistoryVO.setCarrierId(mailbagHistory.getCarrierId());
		mailbagHistoryVO.setCarrierCode(mailbagHistory.getCarrierCode());
		mailbagHistoryVO.setContainerNumber(mailbagHistory.getContainerNumber());
		mailbagHistoryVO.setContainerType(mailbagHistory.getContainerType());
		mailbagHistoryVO.setDeliveryStatus(mailbagHistory.getDeliveryStatus());
		mailbagHistoryVO.setDestination(mailbagHistory.getDestination());
		mailbagHistoryVO.setDestinationExchangeOffice(mailbagHistory.getDestinationExchangeOffice());
		mailbagHistoryVO.setDsn(mailbagHistory.getDsn());
		mailbagHistoryVO.setEventCode(mailbagHistory.getEventCode());
		mailbagHistoryVO.setEventDate(mailbagHistory.getEventDate());
		mailbagHistoryVO.setFirstFlight(mailbagHistory.getFirstFlight());
		mailbagHistoryVO.setFlightDate(mailbagHistory.getFlightDate());
		mailbagHistoryVO.setFlightNumber(mailbagHistory.getFlightNumber());
		mailbagHistoryVO.setFlightDate(mailbagHistory.getFlightDate());
		mailbagHistoryVO.setFlightSequenceNumber(mailbagHistory.getFlightSequenceNumber());
		mailbagHistoryVO.setHistorySequenceNumber(mailbagHistory.getHistorySequenceNumber());
		mailbagHistoryVO.setMailbagId(mailbagHistory.getMailbagId());
		mailbagHistoryVO.setMailBoxId(mailbagHistory.getMailBoxId());
		mailbagHistoryVO.setMailCategoryCode(mailbagHistory.getMailCategoryCode());
		mailbagHistoryVO.setMailClass(mailbagHistory.getMailClass());
		mailbagHistoryVO.setMailCategoryCode(mailbagHistory.getMailCategoryCode());
		mailbagHistoryVO.setMailSource(mailbagHistory.getMailSource());
		mailbagHistoryVO.setMailStatus(mailbagHistory.getMailStatus());
		mailbagHistoryVO.setMasterDocumentNumber(mailbagHistory.getMailBoxId());
		mailbagHistoryVO.setMessageSequenceNumber(mailbagHistory.getMessageSequenceNumber());
		mailbagHistoryVO.setProcessedStatus(mailbagHistory.getProcessedStatus());
		mailbagHistoryVO.setMessageTime(mailbagHistory.getMessageTime());
		return mailbagHistoryVO;
	}
	public static OnwardRoutingVO constructOnwardRoutingVO(OnwardRouting onwardRouting, LogonAttributes logonAttributes){
		
		OnwardRoutingVO onwardRoutingVO = new OnwardRoutingVO();
		
		onwardRoutingVO.setOnwardCarrierCode(onwardRouting.getOnwardCarrierCode());
		onwardRoutingVO.setOnwardFlightNumber(onwardRouting.getFlightNumber());
		onwardRoutingVO.setPou(onwardRouting.getPou());
		if(onwardRouting.getOnwardFlightDate()!=null){
			LocalDate onwardFlightDate = new LocalDate(logonAttributes.getAirportCode(),ARP,false).setDate(
					onwardRouting.getOnwardFlightDate());
			onwardRoutingVO.setOnwardFlightDate(onwardFlightDate);
		}
		onwardRoutingVO.setAssignmenrPort(logonAttributes.getAirportCode());
		onwardRoutingVO.setOperationFlag(onwardRouting.getOperationFlag());
		onwardRoutingVO.setCompanyCode(logonAttributes.getCompanyCode());
		
		
		return onwardRoutingVO;
		
	}
	
	public Consignment convertConsDocVOToConsDocModel(ConsignmentDocumentVO consignmentDocumentVO, Collection<OneTimeVO> mailServicelevel) {
		Consignment consignmentDocumentModel = new Consignment();
		if (consignmentDocumentVO != null) {
			/*
			 * consignmentDocumentModel.setAirlineCode(consignmentDocumentVO.
			 * getAirlineCode());
			 * consignmentDocumentModel.setAirportCode(consignmentDocumentVO.
			 * getAirportCode());
			 * consignmentDocumentModel.setCarrierCode(consignmentDocumentVO.
			 * getCarrierCode());
			 * consignmentDocumentModel.setCompanyCode(consignmentDocumentVO.
			 * getCompanyCode());
			 * consignmentDocumentModel.setConsignmentDate(consignmentDocumentVO
			 * .getConsignmentDate());
			 * consignmentDocumentModel.setConsignmentNumber(
			 * consignmentDocumentVO.getConsignmentNumber());
			 * consignmentDocumentModel.setConsignmentPriority(
			 * consignmentDocumentVO.getConsignmentPriority());
			 * consignmentDocumentModel.setConsignmentSequenceNumber(
			 * consignmentDocumentVO.getConsignmentSequenceNumber());
			 * consignmentDocumentModel.setDateOfDept(consignmentDocumentVO.
			 * getDateOfDept());
			 * consignmentDocumentModel.setDespatchDate(consignmentDocumentVO.
			 * getDespatchDate());
			 * consignmentDocumentModel.setDestination(consignmentDocumentVO.
			 * getDestination());
			 * consignmentDocumentModel.setDestinationCity(consignmentDocumentVO
			 * .getDestinationCity());
			 * consignmentDocumentModel.setDestinationOfficeOfExchange(
			 * consignmentDocumentVO.getDestPort());
			 * consignmentDocumentModel.setDestPort(consignmentDocumentVO.
			 * getDestPort());
			 * consignmentDocumentModel.setDOEDescription(consignmentDocumentVO.
			 * getDOEDescription());
			 * consignmentDocumentModel.setDsnNumber(consignmentDocumentVO.
			 * getDsnNumber());
			 * consignmentDocumentModel.setDespatchDate(consignmentDocumentVO.
			 * getDespatchDate());
			 * consignmentDocumentModel.setDestination(consignmentDocumentVO.
			 * getDestination());
			 * consignmentDocumentModel.setDestinationCity(consignmentDocumentVO
			 * .getDestinationCity());
			 * consignmentDocumentModel.setDestinationOfficeOfExchange(
			 * consignmentDocumentVO.getDestinationOfficeOfExchange());
			 * consignmentDocumentModel.setDestPort(consignmentDocumentVO.
			 * getDestPort());
			 */
			consignmentDocumentModel.setConsignmentNumber(consignmentDocumentVO.getConsignmentNumber());
			consignmentDocumentModel.setConsignmentSequenceNumber(consignmentDocumentVO.getConsignmentSequenceNumber());
			consignmentDocumentModel.setPaCode(consignmentDocumentVO.getPaCode());
			if(consignmentDocumentVO.getPaCode().equals("US001")){
				consignmentDocumentModel.setDomestic(true);
			}else{
				consignmentDocumentModel.setDomestic(false);
			}
			consignmentDocumentModel.setConsignmentDate(consignmentDocumentVO.getConsignmentDate().toDisplayDateOnlyFormat());
			consignmentDocumentModel.setType(consignmentDocumentVO.getType());
			consignmentDocumentModel.setSubType(consignmentDocumentVO.getSubType());
			consignmentDocumentModel.setRemarks(consignmentDocumentVO.getRemarks());
			List<ConsignmentRouting> routingCollection = new ArrayList<ConsignmentRouting>();
			routingCollection = (ArrayList<ConsignmentRouting>) getRoutingCollectionModel(consignmentDocumentVO);
			consignmentDocumentModel.setConsignmentRouting(routingCollection);
			PageResult<MailInConsignment> mailConsMdlPageResult = null;
			if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){  
			mailConsMdlPageResult = getMailInConsignmentModel(consignmentDocumentVO);
			consignmentDocumentModel.setShipmentPrefix(consignmentDocumentVO.getMailInConsignmentVOs().get(0).getShipmentPrefix());
			consignmentDocumentModel.setMasterDocumentNumber(consignmentDocumentVO.getMailInConsignmentVOs().get(0).getMasterDocumentNumber());
			}
			consignmentDocumentModel.setMailsInConsignmentPage(mailConsMdlPageResult);
			consignmentDocumentModel.setOperation(consignmentDocumentVO.getOperation());
			if(consignmentDocumentVO.getMailInConsignmentVOs()!=null){  
			consignmentDocumentModel.setExcelMailBags(getExcelMailBags(consignmentDocumentVO,mailServicelevel));
			}
		}
		return consignmentDocumentModel;

	}
	
	public PageResult<MailInConsignment> getMailInConsignmentModel(ConsignmentDocumentVO consignmentDocumentVO) {
		//Page<MailInConsignmentModel> mailInConsignmentModelPage = new Page<MailInConsignmentModel>();
		
		List<MailInConsignment> mailInConsignmentModelList = new ArrayList<MailInConsignment>(); 
		Page<MailInConsignmentVO> mailInConsignmentVOPage = consignmentDocumentVO.getMailInConsignmentVOs();
		Iterator<MailInConsignmentVO> mailInConsItr = mailInConsignmentVOPage.iterator();
		
		while (mailInConsItr.hasNext()) {
			MailInConsignmentVO mailInConsignmentVO = mailInConsItr.next();
			MailInConsignment mailInConsignmentModel = new MailInConsignment();
			mailInConsignmentModel.setAirportCode(mailInConsignmentVO.getAirportCode());
			mailInConsignmentModel.setCarrierId(mailInConsignmentVO.getCarrierId());
			mailInConsignmentModel.setCompanyCode(mailInConsignmentVO.getCompanyCode());
			mailInConsignmentModel.setConsignmentDate(mailInConsignmentVO.getConsignmentDate());
			mailInConsignmentModel.setConsignmentNumber(mailInConsignmentVO.getConsignmentNumber());
			mailInConsignmentModel.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
			mailInConsignmentModel.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
			mailInConsignmentModel.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
			mailInConsignmentModel.setDestinationExchangeOffice(mailInConsignmentVO.getDestinationExchangeOffice());
			mailInConsignmentModel.setDisplayUnit(mailInConsignmentVO.getStatedWeight().getDisplayUnit());
			mailInConsignmentModel.setDsn(mailInConsignmentVO.getDsn());
			mailInConsignmentModel.setHighestNumberedReceptacle(mailInConsignmentVO.getHighestNumberedReceptacle());
			mailInConsignmentModel.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
			mailInConsignmentModel.setMailClass(mailInConsignmentVO.getMailClass());
			mailInConsignmentModel.setMailId(mailInConsignmentVO.getMailId());
			mailInConsignmentModel.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
			mailInConsignmentModel.setMailSubclass(mailInConsignmentVO.getMailSubclass());
			mailInConsignmentModel.setOperationFlag(mailInConsignmentVO.getOperationFlag());
			mailInConsignmentModel.setOriginExchangeOffice(mailInConsignmentVO.getOriginExchangeOffice());
			mailInConsignmentModel.setPaCode(mailInConsignmentVO.getPaCode());
			mailInConsignmentModel.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
			mailInConsignmentModel
					.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getRegisteredOrInsuredIndicator());
			mailInConsignmentModel.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
			mailInConsignmentModel.setStatedBags(mailInConsignmentVO.getStatedBags());
			mailInConsignmentModel.setStatedWeight(mailInConsignmentVO.getStatedWeight().getFormattedDisplayValue());
			mailInConsignmentModel.setStrWeight(mailInConsignmentVO.getStrWeight());
			mailInConsignmentModel.setUldNumber(mailInConsignmentVO.getUldNumber());
			mailInConsignmentModel.setVolume(mailInConsignmentVO.getVolume());
			mailInConsignmentModel.setYear(mailInConsignmentVO.getYear());
			mailInConsignmentModel.setTransWindowEndTime(mailInConsignmentVO.getTransWindowEndTime());
			mailInConsignmentModel.setMailOrigin(mailInConsignmentVO.getMailOrigin());
			mailInConsignmentModel.setMailDestination(mailInConsignmentVO.getMailDestination());
			mailInConsignmentModel.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
			mailInConsignmentModel.setMailStatus(mailInConsignmentVO.getMailStatus());
			if(mailInConsignmentVO.getReqDeliveryTime()!=null){
				mailInConsignmentModel.setRequiredDlvDate(mailInConsignmentVO.getReqDeliveryTime().toDisplayDateOnlyFormat());
				mailInConsignmentModel.setRequiredDlvTime(mailInConsignmentVO.getReqDeliveryTime().toDisplayTimeOnlyFormat());
			}
			
			mailInConsignmentModelList.add(mailInConsignmentModel);

		}
		PageResult<MailInConsignment> pageResultList= new PageResult<MailInConsignment>(mailInConsignmentVOPage,mailInConsignmentModelList);
		
		return pageResultList;
	}

	public List<ConsignmentRouting> getRoutingCollectionModel(ConsignmentDocumentVO consignmentDocumentVO) {

		List<ConsignmentRouting> routingCollection = new ArrayList<ConsignmentRouting>();
		Iterator<RoutingInConsignmentVO> routConsItr = (consignmentDocumentVO.getRoutingInConsignmentVOs()).iterator();

		while (routConsItr.hasNext()) {
			RoutingInConsignmentVO routingInConsignmentVO = routConsItr.next();
			ConsignmentRouting routingInConsignmentModel = new ConsignmentRouting();
			routingInConsignmentModel.setAcceptanceFlag(routingInConsignmentVO.isAcceptanceFlag());
			routingInConsignmentModel.setCompanyCode(routingInConsignmentVO.getCompanyCode());
			routingInConsignmentModel.setConsignmentNumber(routingInConsignmentVO.getConsignmentNumber());
			routingInConsignmentModel
					.setConsignmentSequenceNumber(routingInConsignmentVO.getConsignmentSequenceNumber());
			routingInConsignmentModel
					.setDestinationOfficeOfExchange(routingInConsignmentVO.getDestinationOfficeOfExchange());
			routingInConsignmentModel.setDsn(routingInConsignmentVO.getDsn());
			routingInConsignmentModel.setFlightClosed(routingInConsignmentVO.isFlightClosed());
			routingInConsignmentModel.setInvalidFlightFlag(routingInConsignmentVO.isInvalidFlightFlag());
			routingInConsignmentModel.setIsDuplicateFlightChecked(routingInConsignmentVO.getIsDuplicateFlightChecked());
			routingInConsignmentModel.setLegSerialNumber(routingInConsignmentVO.getLegSerialNumber());
			routingInConsignmentModel.setMailCategoryCode(routingInConsignmentVO.getMailCategoryCode());
			routingInConsignmentModel.setMailClass(routingInConsignmentVO.getMailClass());
			routingInConsignmentModel.setMailSubClass(routingInConsignmentVO.getMailSubClass());
			routingInConsignmentModel.setNoOfPieces(routingInConsignmentVO.getNoOfPieces());
			routingInConsignmentModel.setOffloadFlag(routingInConsignmentVO.isOffloadFlag());
			routingInConsignmentModel.setOnwardCarrierCode(routingInConsignmentVO.getOnwardCarrierCode());
			routingInConsignmentModel.setOnwardCarrierId(routingInConsignmentVO.getOnwardCarrierId());
			routingInConsignmentModel.setOnwardCarrierSeqNum(routingInConsignmentVO.getOnwardCarrierSeqNum());
			if(routingInConsignmentVO.getOnwardFlightDate()!=null){
				routingInConsignmentModel.setOnwardFlightDate(routingInConsignmentVO.getOnwardFlightDate().toDisplayDateOnlyFormat());
			}
			routingInConsignmentModel.setOnwardFlightNumber(routingInConsignmentVO.getOnwardFlightNumber());
			routingInConsignmentModel.setOperationFlag(routingInConsignmentVO.getOperationFlag());
			routingInConsignmentModel.setOriginOfficeOfExchange(routingInConsignmentVO.getOriginOfficeOfExchange());
			routingInConsignmentModel.setPaCode(routingInConsignmentVO.getPaCode());
			routingInConsignmentModel.setPol(routingInConsignmentVO.getPol());
			routingInConsignmentModel.setPou(routingInConsignmentVO.getPou());
			routingInConsignmentModel.setPreviousNoOfPieces(routingInConsignmentVO.getPreviousNoOfPieces());
			routingInConsignmentModel.setPreviousWeight(routingInConsignmentVO.getPreviousWeight());
			routingInConsignmentModel.setRecievedNoOfPieces(routingInConsignmentVO.getRecievedNoOfPieces());
			routingInConsignmentModel.setRecievedWeight(routingInConsignmentVO.getRecievedWeight());
			routingInConsignmentModel.setRemarks(routingInConsignmentVO.getRemarks());
			routingInConsignmentModel.setRoutingSerialNumber(routingInConsignmentVO.getRoutingSerialNumber());
			routingInConsignmentModel.setSegmentSerialNumber(routingInConsignmentVO.getSegmentSerialNumber());
			routingInConsignmentModel.setWeight(routingInConsignmentVO.getWeight());
			routingInConsignmentModel.setYear(routingInConsignmentVO.getYear());
			routingInConsignmentModel.setTransportStageQualifier(routingInConsignmentVO.getTransportStageQualifier());
			routingCollection.add(routingInConsignmentModel);
		}

		return routingCollection;
	}
	
	public List<MailBagsInExcelConsignment> getExcelMailBags(ConsignmentDocumentVO consignmentDocumentVO, Collection<OneTimeVO> mailServicelevels) {
		
		List<MailBagsInExcelConsignment> excelMailBags = new ArrayList<MailBagsInExcelConsignment>();
		Page<MailInConsignmentVO> mailInConsignmentVOPage = consignmentDocumentVO.getMailInConsignmentVOs();
		Iterator<MailInConsignmentVO> mailInConsItr = mailInConsignmentVOPage.iterator();
		
		while (mailInConsItr.hasNext()) {
			MailInConsignmentVO mailInConsignmentVO = mailInConsItr.next();
			MailBagsInExcelConsignment excelMailBag = new MailBagsInExcelConsignment();
			excelMailBag.setMailId(mailInConsignmentVO.getMailId());
			excelMailBag.setOriginExchangeOffice(mailInConsignmentVO.getOriginExchangeOffice());
			excelMailBag.setDestinationExchangeOffice(mailInConsignmentVO.getDestinationExchangeOffice());
			excelMailBag.setMailCategoryCode(mailInConsignmentVO.getMailCategoryCode());
			excelMailBag.setMailClass(mailInConsignmentVO.getMailClass());
			excelMailBag.setMailSubclass(mailInConsignmentVO.getMailSubclass());
			excelMailBag.setYear(mailInConsignmentVO.getYear());
			excelMailBag.setDsn(mailInConsignmentVO.getDsn());
			excelMailBag.setReceptacleSerialNumber(mailInConsignmentVO.getReceptacleSerialNumber());
			excelMailBag.setStatedBags(mailInConsignmentVO.getStatedBags());
			excelMailBag.setHighestNumberedReceptacle(mailInConsignmentVO.getHighestNumberedReceptacle());
			excelMailBag.setRegisteredOrInsuredIndicator(mailInConsignmentVO.getRegisteredOrInsuredIndicator());
			excelMailBag.setStatedWeight(mailInConsignmentVO.getStatedWeight().getFormattedDisplayValue());
			excelMailBag.setUldNumber(mailInConsignmentVO.getUldNumber());
			excelMailBag.setMailSequenceNumber(mailInConsignmentVO.getMailSequenceNumber());
			excelMailBag.setConsignmentSequenceNumber(mailInConsignmentVO.getConsignmentSequenceNumber());
			excelMailBag.setTransWindowEndTime(mailInConsignmentVO.getTransWindowEndTime());
			excelMailBag.setReqDeliveryTime(mailInConsignmentVO.getReqDeliveryTime());
			excelMailBag.setMailOrigin(mailInConsignmentVO.getMailOrigin());
			excelMailBag.setMailDestination(mailInConsignmentVO.getMailDestination());
			//excelMailBag.setMailServiceLevel(mailInConsignmentVO.getMailServiceLevel());
			if(mailInConsignmentVO.getMailServiceLevel()!=null && mailServicelevels!=null){
				for(OneTimeVO servicelevel:mailServicelevels){
					if(mailInConsignmentVO.getMailServiceLevel().equals(servicelevel.getFieldValue())){
						excelMailBag.setMailServiceLevel(servicelevel.getFieldDescription());
						break;
					}
					
				}
			}
			
			excelMailBag.setDeclaredValue(mailInConsignmentVO.getDeclaredValue());
			excelMailBag.setCurrencyCode(mailInConsignmentVO.getCurrencyCode());
			excelMailBags.add(excelMailBag);
		}		
		return excelMailBags;
		
	}
	
	public ConsignmentDocumentVO constructConsignmentDocumentVO(Consignment consignment, LogonAttributes logonAttributes, Collection<OneTimeVO> mailServicelevels) {
		ConsignmentDocumentVO consignmentDocumentModel = new ConsignmentDocumentVO();
		if (consignment != null) {
			consignmentDocumentModel.setConsignmentNumber(consignment.getConsignmentNumber());
			consignmentDocumentModel.setPaCode(consignment.getPaCode());
			LocalDate consignmentDate = null;
			if (consignment.getConsignmentDate() != null) {

				consignmentDate = new LocalDate(logonAttributes.getAirportCode(), ARP, true);
				consignmentDate.setDate(consignment.getConsignmentDate());
				consignmentDocumentModel.setConsignmentDate(consignmentDate);
			}
			consignmentDocumentModel.setType(consignment.getType());
			consignmentDocumentModel.setSubType(consignment.getSubType());
			consignmentDocumentModel.setRemarks(consignment.getRemarks());
			Collection<RoutingInConsignmentVO> routingCollection = new ArrayList<RoutingInConsignmentVO>();
			routingCollection = (ArrayList<RoutingInConsignmentVO>) getRoutingCollectionModel(consignment, logonAttributes);
			consignmentDocumentModel.setRoutingInConsignmentVOs(routingCollection);
			Page<MailInConsignmentVO> mailConsMdlPageResult = null;
			if(consignment.isDomestic()){
				mailConsMdlPageResult = getMailInConsignmentModelDomestic(consignment,logonAttributes,mailServicelevels);
			}else{
			mailConsMdlPageResult = getMailInConsignmentModel(consignment,logonAttributes,mailServicelevels);
			}			
			consignmentDocumentModel.setMailInConsignmentVOs(mailConsMdlPageResult);
			consignmentDocumentModel.setOperation(consignment.getOperation());
			consignmentDocumentModel.setOperationFlag(consignment.getOperationFlag());
			consignmentDocumentModel.setConsignmentSequenceNumber(consignment.getConsignmentSequenceNumber());
		}
		return consignmentDocumentModel;

	}
	
	public Collection<RoutingInConsignmentVO> getRoutingCollectionModel(Consignment consignment, LogonAttributes logonAttributes) {

		Collection<RoutingInConsignmentVO> routingCollection = new ArrayList<RoutingInConsignmentVO>();
		Iterator<ConsignmentRouting> routConsItr = (consignment.getConsignmentRouting()).iterator();

		while (routConsItr.hasNext()) {
			ConsignmentRouting consignmentRouting = routConsItr.next();
			RoutingInConsignmentVO routingInConsignmentVO = new RoutingInConsignmentVO();
			routingInConsignmentVO.setAcceptanceFlag(consignmentRouting.isAcceptanceFlag());
			routingInConsignmentVO.setCompanyCode(logonAttributes.getCompanyCode());
			routingInConsignmentVO.setConsignmentNumber(consignment.getConsignmentNumber());
			routingInConsignmentVO
					.setConsignmentSequenceNumber(consignmentRouting.getConsignmentSequenceNumber());
			routingInConsignmentVO
					.setDestinationOfficeOfExchange(consignmentRouting.getDestinationOfficeOfExchange());
			routingInConsignmentVO.setDsn(consignmentRouting.getDsn());
			routingInConsignmentVO.setFlightClosed(consignmentRouting.isFlightClosed());
			routingInConsignmentVO.setInvalidFlightFlag(consignmentRouting.isInvalidFlightFlag());
			routingInConsignmentVO.setIsDuplicateFlightChecked(consignmentRouting.getIsDuplicateFlightChecked());
			routingInConsignmentVO.setLegSerialNumber(consignmentRouting.getLegSerialNumber());
			routingInConsignmentVO.setMailCategoryCode(consignmentRouting.getMailCategoryCode());
			routingInConsignmentVO.setMailClass(consignmentRouting.getMailClass());
			routingInConsignmentVO.setMailSubClass(consignmentRouting.getMailSubClass());
			routingInConsignmentVO.setNoOfPieces(consignmentRouting.getNoOfPieces());
			routingInConsignmentVO.setOffloadFlag(consignmentRouting.isOffloadFlag());
			routingInConsignmentVO.setOnwardCarrierCode(consignmentRouting.getOnwardCarrierCode());
			routingInConsignmentVO.setOnwardCarrierId(consignmentRouting.getOnwardCarrierId());
			routingInConsignmentVO.setOnwardCarrierSeqNum(consignmentRouting.getOnwardCarrierSeqNum());
			LocalDate onwardFlightDate = null;
			if (consignmentRouting.getOnwardFlightDate() != null) {

				onwardFlightDate = new LocalDate(logonAttributes.getAirportCode(), ARP, false);
				onwardFlightDate.setDate(consignmentRouting.getOnwardFlightDate());
				routingInConsignmentVO.setOnwardFlightDate(onwardFlightDate);
			}
			
			routingInConsignmentVO.setOnwardFlightNumber(consignmentRouting.getOnwardFlightNumber());
			routingInConsignmentVO.setOperationFlag(consignmentRouting.getOperationFlag());
			routingInConsignmentVO.setOriginOfficeOfExchange(consignmentRouting.getOriginOfficeOfExchange());
			routingInConsignmentVO.setPaCode(consignment.getPaCode());
			routingInConsignmentVO.setPol(consignmentRouting.getPol());
			routingInConsignmentVO.setPou(consignmentRouting.getPou());
			routingInConsignmentVO.setPreviousNoOfPieces(consignmentRouting.getPreviousNoOfPieces());
			routingInConsignmentVO.setPreviousWeight(consignmentRouting.getPreviousWeight());
			routingInConsignmentVO.setRecievedNoOfPieces(consignmentRouting.getRecievedNoOfPieces());
			routingInConsignmentVO.setRecievedWeight(consignmentRouting.getRecievedWeight());
			routingInConsignmentVO.setRemarks(consignmentRouting.getRemarks());
			routingInConsignmentVO.setRoutingSerialNumber(consignmentRouting.getRoutingSerialNumber());
			routingInConsignmentVO.setSegmentSerialNumber(consignmentRouting.getSegmentSerialNumber());
			routingInConsignmentVO.setWeight(consignmentRouting.getWeight());
			routingInConsignmentVO.setYear(consignmentRouting.getYear());
			routingInConsignmentVO.setTransportStageQualifier(consignmentRouting.getTransportStageQualifier());
			routingCollection.add(routingInConsignmentVO);
		}

		return routingCollection;
	}
	
	public Page<MailInConsignmentVO> getMailInConsignmentModel(Consignment consignment, LogonAttributes logonAttributes, Collection<OneTimeVO> mailServicelevels) {
		//Page<MailInConsignmentModel> mailInConsignmentModelPage = new Page<MailInConsignmentModel>();
		
		List<MailInConsignmentVO> mailInConsignmentModelList = new ArrayList<MailInConsignmentVO>(); 
		Collection<MailInConsignment> mailsInConsignment = consignment.getMailsInConsignment();
		Iterator<MailInConsignment> mailInConsItr = mailsInConsignment.iterator();
		
		while (mailInConsItr.hasNext()) {
			MailInConsignment mailInConsignment = mailInConsItr.next();
			MailInConsignmentVO mailInConsignmentModel = new MailInConsignmentVO();
			mailInConsignmentModel.setAirportCode(mailInConsignment.getAirportCode());
			mailInConsignmentModel.setCarrierId(mailInConsignment.getCarrierId());
			mailInConsignmentModel.setCompanyCode(mailInConsignment.getCompanyCode());
			mailInConsignmentModel.setConsignmentDate(mailInConsignment.getConsignmentDate());
			mailInConsignmentModel.setConsignmentNumber(mailInConsignment.getConsignmentNumber());
			mailInConsignmentModel.setConsignmentSequenceNumber(mailInConsignment.getConsignmentSequenceNumber());
			mailInConsignmentModel.setCurrencyCode(mailInConsignment.getCurrencyCode());
			mailInConsignmentModel.setDeclaredValue(mailInConsignment.getDeclaredValue());
			mailInConsignmentModel.setDestinationExchangeOffice(mailInConsignment.getDestinationExchangeOffice());
			mailInConsignmentModel.setDisplayUnit(mailInConsignment.getDisplayUnit());
			mailInConsignmentModel.setDsn(mailInConsignment.getDsn());
			mailInConsignmentModel.setHighestNumberedReceptacle(mailInConsignment.getHighestNumberedReceptacle());
			mailInConsignmentModel.setMailCategoryCode(mailInConsignment.getMailCategoryCode());
			mailInConsignmentModel.setMailClass(mailInConsignment.getMailClass());
			mailInConsignmentModel.setMailId(mailInConsignment.getMailId());
			mailInConsignmentModel.setMailSequenceNumber(mailInConsignment.getMailSequenceNumber());
			mailInConsignmentModel.setMailSubclass(mailInConsignment.getMailSubclass());
			mailInConsignmentModel.setOperationFlag(mailInConsignment.getOperationFlag());
			mailInConsignmentModel.setOriginExchangeOffice(mailInConsignment.getOriginExchangeOffice());
			mailInConsignmentModel.setPaCode(mailInConsignment.getPaCode());
			mailInConsignmentModel.setReceptacleSerialNumber(mailInConsignment.getReceptacleSerialNumber());
			mailInConsignmentModel
					.setRegisteredOrInsuredIndicator(mailInConsignment.getRegisteredOrInsuredIndicator());
			//mailInConsignmentModel.setReqDeliveryTime(mailInConsignment.getReqDeliveryTime());
			mailInConsignmentModel.setStatedBags(mailInConsignment.getStatedBags());
			//Measure weight=new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailInConsignment.getStatedWeight()));
			Measure weight = new Measure(UnitConstants.MAIL_WGT,0.0,Double.parseDouble(mailInConsignment.getStatedWeight()),mailInConsignment.getDisplayUnit() );  
			mailInConsignmentModel.setStatedWeight(weight);
			mailInConsignmentModel.setStrWeight(mailInConsignment.getStrWeight());
			mailInConsignmentModel.setUldNumber(mailInConsignment.getUldNumber());
			mailInConsignmentModel.setVolume(mailInConsignment.getVolume());
			mailInConsignmentModel.setYear(mailInConsignment.getYear());
			mailInConsignmentModel.setMailStatus(mailInConsignment.getMailStatus());
			if (mailInConsignment.getRequiredDlvDate() != null) {
				String reqdlvDT = null;
				LocalDate rdlvdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
				if (mailInConsignment.getRequiredDlvTime() != null) {
					if (mailInConsignment.getRequiredDlvTime().length() == 8) {
						if (mailInConsignment.getRequiredDlvTime().equals("00:00:00")) {
							LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
							reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
									.append(date.toDisplayTimeOnlyFormat(false)).toString();
						} else {
							reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
									.append(mailInConsignment.getRequiredDlvTime()).toString();
						}

					} else {
						reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
								.append(mailInConsignment.getRequiredDlvTime()).append(":00").toString();
					}

				} else {
					reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ").append("00:00:00")
							.toString();
				}

				mailInConsignmentModel.setReqDeliveryTime(rdlvdate.setDateAndTime(reqdlvDT, false));

			}
			mailInConsignmentModel.setMailOrigin(mailInConsignment.getMailOrigin());
			mailInConsignmentModel.setMailDestination(mailInConsignment.getMailDestination());
			//mailInConsignmentModel.setMailServiceLevel(mailInConsignment.getMailServiceLevel());
			if(mailInConsignment.getMailServiceLevel()!=null && mailInConsignment.getMailServiceLevel().length()==2){  //from normal view
			mailInConsignmentModel.setMailServiceLevel(mailInConsignment.getMailServiceLevel());
			}
			else if(mailInConsignment.getMailServiceLevel()!=null){    //from excel view
				for(OneTimeVO mailservicelevel:mailServicelevels){
					if(mailInConsignment.getMailServiceLevel().equals(mailservicelevel.getFieldDescription())){
						mailInConsignmentModel.setMailServiceLevel(mailservicelevel.getFieldValue());
					}	
				}
				
			}
			mailInConsignmentModel.setOperation(consignment.getOperation());
			mailInConsignmentModelList.add(mailInConsignmentModel);

		}

		int pageNumber = 0;
		int defaultPageSize = 150;
		int actualPageSize = mailInConsignmentModelList.size()-1;
		int startIndex = 149;
		int endIndex = 0;
		boolean hasNextPage = false;
		
		Page<MailInConsignmentVO> pageResultList= new Page<MailInConsignmentVO>(mailInConsignmentModelList, pageNumber, defaultPageSize, actualPageSize, startIndex, endIndex, hasNextPage);
		
		return pageResultList;
	}
	
	public Page<MailInConsignmentVO> getMailInConsignmentModelDomestic(Consignment consignment, LogonAttributes logonAttributes,Collection<OneTimeVO> mailServicelevels) {
		//Page<MailInConsignmentModel> mailInConsignmentModelPage = new Page<MailInConsignmentModel>();
		
		List<MailInConsignmentVO> mailInConsignmentModelList = new ArrayList<MailInConsignmentVO>(); 
		Collection<MailInConsignment> mailsInConsignment = consignment.getMailsInConsignment();
		Iterator<MailInConsignment> mailInConsItr = mailsInConsignment.iterator();
		
		while (mailInConsItr.hasNext()) {
			MailInConsignment mailInConsignment = mailInConsItr.next();
			MailInConsignmentVO mailInConsignmentModel = new MailInConsignmentVO();
			mailInConsignmentModel.setAirportCode(mailInConsignment.getAirportCode());
			mailInConsignmentModel.setCarrierId(mailInConsignment.getCarrierId());
			mailInConsignmentModel.setCompanyCode(mailInConsignment.getCompanyCode());
			mailInConsignmentModel.setConsignmentDate(mailInConsignment.getConsignmentDate());
			mailInConsignmentModel.setConsignmentNumber(mailInConsignment.getConsignmentNumber());
			mailInConsignmentModel.setConsignmentSequenceNumber(mailInConsignment.getConsignmentSequenceNumber());
			mailInConsignmentModel.setCurrencyCode(mailInConsignment.getCurrencyCode());
			mailInConsignmentModel.setDeclaredValue(mailInConsignment.getDeclaredValue());
			mailInConsignmentModel.setDestinationExchangeOffice(mailInConsignment.getDestinationExchangeOffice());
			mailInConsignmentModel.setDisplayUnit(mailInConsignment.getDisplayUnit());
			mailInConsignmentModel.setDsn(mailInConsignment.getDsn());
			mailInConsignmentModel.setHighestNumberedReceptacle(mailInConsignment.getHighestNumberedReceptacle());
			mailInConsignmentModel.setMailCategoryCode(mailInConsignment.getMailCategoryCode());
			mailInConsignmentModel.setMailClass(mailInConsignment.getMailClass());
			mailInConsignmentModel.setMailId(mailInConsignment.getMailId());
			mailInConsignmentModel.setMailSequenceNumber(mailInConsignment.getMailSequenceNumber());
			mailInConsignmentModel.setMailSubclass(mailInConsignment.getMailSubclass());
			mailInConsignmentModel.setOperationFlag(mailInConsignment.getOperationFlag());
			mailInConsignmentModel.setOriginExchangeOffice(mailInConsignment.getOriginExchangeOffice());
			mailInConsignmentModel.setPaCode(mailInConsignment.getPaCode());
			mailInConsignmentModel.setReceptacleSerialNumber(mailInConsignment.getReceptacleSerialNumber());
			mailInConsignmentModel.setRegisteredOrInsuredIndicator(mailInConsignment.getRegisteredOrInsuredIndicator());
			mailInConsignmentModel.setReqDeliveryTime(mailInConsignment.getReqDeliveryTime());
			mailInConsignmentModel.setStatedBags(mailInConsignment.getStatedBags());
			Measure weight=null;
			if(mailInConsignment.getStatedWeight()!=null){
				weight=new Measure(UnitConstants.MAIL_WGT,Double.parseDouble(mailInConsignment.getStatedWeight()));
			}else{
				weight=new Measure(UnitConstants.MAIL_WGT,0.0);
			}
			mailInConsignmentModel.setStatedWeight(weight);
			mailInConsignmentModel.setStrWeight(mailInConsignment.getStrWeight());
			mailInConsignmentModel.setUldNumber(mailInConsignment.getUldNumber());
			Measure volume = new Measure(UnitConstants.MAIL_WGT,0.0);
			mailInConsignmentModel.setVolume(volume);
			mailInConsignmentModel.setYear(mailInConsignment.getYear());
			mailInConsignmentModel.setMailStatus(mailInConsignment.getMailStatus());
			/*try {
				for(ConsignmentRouting consignmentRouting: consignment.getConsignmentRouting()){
					populatePCIDetailsforUSPS(mailInConsignmentModel, consignment.getAirportCode(), consignment.getCompanyCode(), consignmentRouting.getPol(),consignmentRouting.getPou(), consignment.getConsignmentDate().substring(consignment.getConsignmentDate().length()-1, consignment.getConsignmentDate().length()));
				}
			} catch (SystemException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			if (mailInConsignment.getRequiredDlvDate() != null) {
				String reqdlvDT = null;
				LocalDate rdlvdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
				if (mailInConsignment.getRequiredDlvTime() != null) {
					if (mailInConsignment.getRequiredDlvTime().length() == 8) {
						if (mailInConsignment.getRequiredDlvTime().equals("00:00:00")) {
							LocalDate date = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
							reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
									.append(date.toDisplayTimeOnlyFormat(false)).toString();
						} else {
							reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
									.append(mailInConsignment.getRequiredDlvTime()).toString();
						}

					} else {
						reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ")
								.append(mailInConsignment.getRequiredDlvTime()).append(":00").toString();
					}

				} else {
					reqdlvDT = new StringBuilder(mailInConsignment.getRequiredDlvDate()).append(" ").append("00:00:00")
							.toString();
				}

				mailInConsignmentModel.setReqDeliveryTime(rdlvdate.setDateAndTime(reqdlvDT, false));

			}
			mailInConsignmentModel.setMailOrigin(mailInConsignment.getMailOrigin());
			mailInConsignmentModel.setMailDestination(mailInConsignment.getMailDestination());
			if(mailInConsignment.getMailServiceLevel()!=null && mailInConsignment.getMailServiceLevel().length()==2){  //from normal view
			mailInConsignmentModel.setMailServiceLevel(mailInConsignment.getMailServiceLevel());
				}
				else if(mailInConsignment.getMailServiceLevel()!=null){    //from excel view
					for(OneTimeVO mailservicelevel:mailServicelevels){
						if(mailInConsignment.getMailServiceLevel().equals(mailservicelevel.getFieldDescription())){
							mailInConsignmentModel.setMailServiceLevel(mailservicelevel.getFieldValue());
						}	
					}
				}
			mailInConsignmentModel.setOperation(mailInConsignment.getOperation());
			mailInConsignmentModelList.add(mailInConsignmentModel);

		}

		int pageNumber = 0;
		int defaultPageSize = 150;
		int actualPageSize = mailInConsignmentModelList.size()-1;
		int startIndex = 149;
		int endIndex = 0;
		boolean hasNextPage = false;
		
		Page<MailInConsignmentVO> pageResultList= new Page<MailInConsignmentVO>(mailInConsignmentModelList, pageNumber, defaultPageSize, actualPageSize, startIndex, endIndex, hasNextPage);
		
		return pageResultList;
	}
	
	/*private void populatePCIDetailsforUSPS(MailInConsignmentVO mailInConsignmentVO, String airport, String companyCode, String rcpOrg, String rcpDest, String year)
		    throws SystemException
		  {
			mailInConsignmentVO.setMailSubclass(mailInConsignmentVO.getMailId().substring(3, 4)+"X");
			mailInConsignmentVO.setMailClass(mailInConsignmentVO.getMailId().substring(3, 4));
//		    String lastDigitOfYear = new LocalDate(this.ediMessageVO.getStationCode(), 
//		      Location.ARP, false).toDisplayFormat("yyyy").substring(3, 4);
		    mailInConsignmentVO.setYear(Integer.parseInt(year));
		    exchangeOfficeMap=findOfficeOfExchangeForPA(companyCode,findSystemParameterValue(USPS_DOMESTIC_PA));
		    
		    if ((this.exchangeOfficeMap != null) && (!this.exchangeOfficeMap.isEmpty()) && 
		    	      (this.exchangeOfficeMap.containsKey(rcpOrg)) && 
		    	      (this.exchangeOfficeMap.containsKey(rcpDest)))
		    	    {
		    			mailInConsignmentVO.setOriginExchangeOffice((String)this.exchangeOfficeMap.get(rcpOrg));
		    			mailInConsignmentVO.setDestinationExchangeOffice((String)this.exchangeOfficeMap.get(rcpDest));
		    	    }

		    mailInConsignmentVO.setMailCategoryCode("B");
		    mailInConsignmentVO.setDsn(generateDespatchSerialNumber("Y", airport, companyCode));
		    if ((mailInConsignmentVO.getReceptacleSerialNumber() == null) || 
		      (mailInConsignmentVO.getReceptacleSerialNumber().trim().isEmpty()))
		    {
		      String rsn = generateReceptacleSerialNumber(mailInConsignmentVO.getDsn(), airport, companyCode);
		      if (rsn.length() > 3)
		      {
		        generateDespatchSerialNumber("N", airport, companyCode);
		        mailInConsignmentVO.setDsn(generateDespatchSerialNumber("Y", airport, companyCode));
		        mailInConsignmentVO.setReceptacleSerialNumber(generateReceptacleSerialNumber(
		          mailInConsignmentVO.getDsn(), airport, companyCode));
		      }
		      else
		      {
		        mailInConsignmentVO.setReceptacleSerialNumber(rsn);
		      }
		    }
		    mailInConsignmentVO.setHighestNumberedReceptacle("9");
		    mailInConsignmentVO.setRegisteredOrInsuredIndicator("9");
		    
//		    mailInConsignmentVO.setOrigin(rcpOrg);
//		    mailInConsignmentVO.setDestination(rcpDest);
		  }
	
	private String generateDespatchSerialNumber(String currentKey, String airportCode, String companyCode)
		    throws SystemException
		  {
		    String key = null;
		    StringBuilder keyCondition = new StringBuilder();
		    keyCondition.append(new LocalDate(airportCode, 
		      Location.ARP, false).toDisplayFormat("yyyy"));
		    Criterion criterion = KeyUtils.getCriterion(companyCode, 
		      "DOM_USPS_DSN", keyCondition.toString());
		    
		    key = KeyUtils.getKey(criterion);
		    if (("Y".equals(currentKey)) && 
		      (!"1".equals(key)))
		    {
		      key = String.valueOf(Long.parseLong(key) - 1L);
		      KeyUtils.resetKey(criterion, key);
		    }
		    return checkLength(key, 4);
		  }
	
	  private String checkLength(String key, int maxLength)
	  {
	    String modifiedKey = null;
	    modifiedKey = key;
	    int keyLength = modifiedKey.length();
	    if (modifiedKey.length() < maxLength)
	    {
	      int diff = maxLength - keyLength;
	      String val = null;
	      for (int i = 0; i < diff; i++) {
	        val = "0";
	      }
	      modifiedKey = val + key;
	    }
	    return modifiedKey;
	  }
	  
	  private String generateReceptacleSerialNumber(String dsn, String airportCode, String companyCode)
			    throws SystemException
			  {
			    StringBuilder keyCondition = new StringBuilder();
			    keyCondition.append(new LocalDate(airportCode, 
			      Location.ARP, false).toDisplayFormat("yyyy"))
			      .append(dsn);
			    Criterion criterion = KeyUtils.getCriterion(companyCode, 
			      "DOM_USPS_RSN", keyCondition.toString());
			    String rsn = checkLength(KeyUtils.getKey(criterion), 3);
			    return rsn;
			  }
	  
	  private String findSystemParameterValue(String syspar)
				throws SystemException {
			String sysparValue = null;
			ArrayList<String> systemParameters = new ArrayList<String>();
			systemParameters.add(syspar);
			HashMap<String, String> systemParameterMap = new SharedDefaultsProxy()
					.findSystemParameterByCodes(systemParameters);
			if (systemParameterMap != null) {
				sysparValue = systemParameterMap.get(syspar);
			}
			return sysparValue;
		}
	  
	  public Map<String,String> findOfficeOfExchangeForPA(String companyCode,
				String paCode) throws SystemException {
			return OfficeOfExchange.findOfficeOfExchangeForPA(companyCode,paCode);
		} */
  /**
	 * A-7929
	 * @param offloadContainerDetailsVOPage
	 * @return
	 */
	public static ArrayList<OffloadDetails> constructOffloadContainerDetails(Page<ContainerVO> offloadContainerDetailsVOPage) {  

		ArrayList<OffloadDetails> offloadDetailsArrayList = new ArrayList<OffloadDetails>();

		if (offloadContainerDetailsVOPage != null && offloadContainerDetailsVOPage.size() > 0) {
			for (ContainerVO offloadDetailsVO : offloadContainerDetailsVOPage) {
				offloadDetailsArrayList.add(constructContainerDetail(offloadDetailsVO));
			}
		}
		return offloadDetailsArrayList;

	}
	  /**
		 * A-7929
		 * @param constructOffloadMailDetails
		 * @return
		 */
		public static ArrayList<OffloadDetails> constructOffloadMailDetails(Page<MailbagVO> offloadMailBagsDetailsVOPage) {  

			ArrayList<OffloadDetails> offloadDetailsArrayList = new ArrayList<OffloadDetails>();

			if (offloadMailBagsDetailsVOPage != null && offloadMailBagsDetailsVOPage.size() > 0) {
				for (MailbagVO mailbagDetailsVO : offloadMailBagsDetailsVOPage) {
					offloadDetailsArrayList.add(constructMailBagDetail(mailbagDetailsVO));
				}
			}
			return offloadDetailsArrayList;

		}
		

		/**
		 * A-7929
		 * @param offloadDespatchDetailsVOPage
		 * @return
		 */
		public static ArrayList<OffloadDetails> constructOffloadDSNDetails(Page<DespatchDetailsVO> offloadDespatchDetailsVOPage) {  

			ArrayList<OffloadDetails> offloadDetailsArrayList = new ArrayList<OffloadDetails>();

			if (offloadDespatchDetailsVOPage != null && offloadDespatchDetailsVOPage.size() > 0) {
				for (DespatchDetailsVO despatchDetailsVO : offloadDespatchDetailsVOPage) {
					offloadDetailsArrayList.add(constructDSNDetail(despatchDetailsVO));
				}
			}
			return offloadDetailsArrayList;

		}
	
   
    /**
     * A-7929
     * @param containerDetailsVO
     * @return
     */
	private static OffloadDetails constructContainerDetail(ContainerVO containerDetailsVO) {
		
		OffloadDetails offloadDetails = new OffloadDetails();
		
        offloadDetails.setContainerNo(containerDetailsVO.getContainerNumber());
        offloadDetails.setPou(containerDetailsVO.getPou());
        offloadDetails.setDestination(containerDetailsVO.getFinalDestination());
        offloadDetails.setAcceptedBags(String.valueOf(containerDetailsVO.getBags()));
        offloadDetails.setAcceptedWeight(containerDetailsVO.getWeight().getFormattedDisplayValue());
        offloadDetails.setOffloadReason(containerDetailsVO.getOffloadedReason());
        offloadDetails.setRemarks(containerDetailsVO.getRemarks());
        if((containerDetailsVO.getLastUpdateTime()!= null ) )
        offloadDetails.setLastUpdateTime(containerDetailsVO.getLastUpdateTime().toDefaultStringFormat());
        offloadDetails.setLastUpdateUser(containerDetailsVO.getLastUpdateUser());
        offloadDetails.setConsignmentNo(containerDetailsVO.getConsignmentDocumentNumber());
        offloadDetails.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
        offloadDetails.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
        offloadDetails.setPol(containerDetailsVO.getPol());
        offloadDetails.setCarrierCode(containerDetailsVO.getCarrierCode());
        offloadDetails.setCarrierId(containerDetailsVO.getCarrierId());
        offloadDetails.setFlightNumber(containerDetailsVO.getFlightNumber());
        offloadDetails.setFlightDate(containerDetailsVO.getFlightDate().getCalendarType());
        offloadDetails.setSegmentSerialNumber(containerDetailsVO.getSegmentSerialNumber());
        offloadDetails.setType(containerDetailsVO.getType());
        offloadDetails.setContainerType(containerDetailsVO.getType());
        offloadDetails.setAssignedPort(containerDetailsVO.getAssignedPort());
        offloadDetails.setPaBuildFlag(containerDetailsVO.getPaBuiltFlag());
        offloadDetails.setAcceptanceFlag(containerDetailsVO.getAcceptanceFlag());
		offloadDetails.setUldFulIndFlag(containerDetailsVO.getUldFulIndFlag()); 
		offloadDetails.setActWgtSta(containerDetailsVO.getActWgtSta());
		return offloadDetails;
	}
	
    /**
     * A-7929
     * @param mailbagDetailsVO
     * @return
     */
	  private static OffloadDetails constructMailBagDetail(MailbagVO mailbagDetailsVO) {
			OffloadDetails offloadDetails = new OffloadDetails();
			
			 offloadDetails.setContainerNo(mailbagDetailsVO.getContainerNumber());
		     offloadDetails.setPou(mailbagDetailsVO.getPou());
		     offloadDetails.setDestination(mailbagDetailsVO.getFinalDestination());
		     offloadDetails.setMailbagId(mailbagDetailsVO.getMailbagId());
		     offloadDetails.setOffloadReason(mailbagDetailsVO.getOffloadedReason());
		     offloadDetails.setRemarks(mailbagDetailsVO.getOffloadedRemarks());
		     offloadDetails.setFlightSequenceNumber(mailbagDetailsVO.getFlightSequenceNumber());
		     offloadDetails.setLegSerialNumber(mailbagDetailsVO.getLegSerialNumber());
		     offloadDetails.setPol(mailbagDetailsVO.getPol());
		     offloadDetails.setCarrierCode(mailbagDetailsVO.getCarrierCode());
		     offloadDetails.setCarrierId(mailbagDetailsVO.getCarrierId());
		     offloadDetails.setFlightNumber(mailbagDetailsVO.getFlightNumber());
		     offloadDetails.setFlightDate(mailbagDetailsVO.getFlightDate().getCalendarType());
		     offloadDetails.setSegmentSerialNumber(mailbagDetailsVO.getSegmentSerialNumber());
		     offloadDetails.setType(mailbagDetailsVO.getType());
		     offloadDetails.setAcceptanceFlag(mailbagDetailsVO.getAcceptanceFlag());
		     offloadDetails.setContainerType(mailbagDetailsVO.getContainerType());
		     offloadDetails.setDsn(mailbagDetailsVO.getDespatchSerialNumber());
		     offloadDetails.setDoe(mailbagDetailsVO.getDoe());
		     offloadDetails.setFinalDestination(mailbagDetailsVO.getFinalDestination());
		     offloadDetails.setLatestStatus(mailbagDetailsVO.getLatestStatus());
		     offloadDetails.setMailClass(mailbagDetailsVO.getMailClass());
		     offloadDetails.setMailCategoryCode(mailbagDetailsVO.getMailCategoryCode());
		     offloadDetails.setSubClass(mailbagDetailsVO.getMailSubclass());
		     offloadDetails.setOoe(mailbagDetailsVO.getOoe());
		     offloadDetails.setOperationalStatus(mailbagDetailsVO.getOperationalStatus());
		     offloadDetails.setPaBuildFlag(mailbagDetailsVO.getPaBuiltFlag());
		     offloadDetails.setAcceptanceFlag(mailbagDetailsVO.getAcceptanceFlag());
		     offloadDetails.setScannedPort(mailbagDetailsVO.getScannedPort());
		     offloadDetails.setAcceptedWeight(mailbagDetailsVO.getWeight().getFormattedDisplayValue());
		     offloadDetails.setYear(String.valueOf(mailbagDetailsVO.getYear()));
		     offloadDetails.setPacode(mailbagDetailsVO.getPaCode());
			
			return offloadDetails;
		}
	  
	  /**
	     * A-7929
	     * @param despatchDetailsVO
	     * @return
	     */
	  private static OffloadDetails constructDSNDetail(DespatchDetailsVO despatchDetailsVO) {
		  OffloadDetails offloadDetails = new OffloadDetails();
		  

	        offloadDetails.setContainerNo(despatchDetailsVO.getContainerNumber());
	        offloadDetails.setPou(despatchDetailsVO.getPou());
	        offloadDetails.setDestination(despatchDetailsVO.getDestination());
	        offloadDetails.setAcceptedBags(String.valueOf(despatchDetailsVO.getAcceptedBags()));
	        offloadDetails.setAcceptedWeight(despatchDetailsVO.getAcceptedWeight().getFormattedDisplayValue());
	        offloadDetails.setOffloadReason(despatchDetailsVO.getOffloadedReason());
	        offloadDetails.setRemarks(despatchDetailsVO.getRemarks());
	        offloadDetails.setDoe(despatchDetailsVO.getDestinationOfficeOfExchange());
	        offloadDetails.setDsn(despatchDetailsVO.getDsn());
	        offloadDetails.setOoe(despatchDetailsVO.getOriginOfficeOfExchange());
	        offloadDetails.setYear(String.valueOf(despatchDetailsVO.getYear()));
	        offloadDetails.setMailClass(despatchDetailsVO.getMailClass());
	        offloadDetails.setSubClass(despatchDetailsVO.getMailSubclass());
	        offloadDetails.setConsignmentNo(despatchDetailsVO.getConsignmentNumber());
	        offloadDetails.setPaBuildFlag(despatchDetailsVO.getPaBuiltFlag());
	        
	        
			return offloadDetails;
		}

	  
	
	public Page<MailInConsignmentVO> convertConsignmentDocumentListToPage(List<MailInConsignmentVO> mailInConsignmentModelList) {
		
		int pageNumber = 0;
		int defaultPageSize = 150;
		int actualPageSize = mailInConsignmentModelList.size()-1;
		int startIndex = 149;
		int endIndex = 0;
		boolean hasNextPage = false;
		
		Page<MailInConsignmentVO> pageResultList= new Page<MailInConsignmentVO>(mailInConsignmentModelList, pageNumber, defaultPageSize, actualPageSize, startIndex, endIndex, hasNextPage);
		
		return pageResultList;
	}
	
	public ArrayList<PostalAdministrationModel> convertPostalAdministartionVos(Collection<PostalAdministrationVO> postalAdministrationVOs){
		
		ArrayList<PostalAdministrationModel> postalAdministrationDetails = new ArrayList<PostalAdministrationModel>();

		PostalAdministrationModel postalAdministrationDetail = null;

		if (postalAdministrationVOs != null && postalAdministrationVOs.size() > 0) {
			for (PostalAdministrationVO postalAdministrationvo : postalAdministrationVOs) {

				postalAdministrationDetail = constructPostalAdministrationDetail(postalAdministrationvo);
				postalAdministrationDetails.add(postalAdministrationDetail);
			}

		}

		return postalAdministrationDetails;
		
	}
	
	private PostalAdministrationModel constructPostalAdministrationDetail(PostalAdministrationVO postalAdministrationvo){
		
		PostalAdministrationModel postalAdministrationDetail = new PostalAdministrationModel();
		postalAdministrationDetail.setAddress(postalAdministrationvo.getAddress());
		postalAdministrationDetail.setCountryCode(postalAdministrationvo.getCountryCode());
		postalAdministrationDetail.setPaCode(postalAdministrationvo.getPaCode());
		postalAdministrationDetail.setPaName(postalAdministrationvo.getPaName());		
		
		return postalAdministrationDetail;
		
	}


/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.constructDsnEnquiryFilter
	 *	Added by 	:	A-8164 on 05-Sep-2019
	 * 	Used for 	:
	 *	Parameters	:	@param carditFilter
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	DSNEnquiryFilterVO
	 */	

	public static DSNEnquiryFilterVO constructDsnEnquiryFilter(
			CarditFilter carditFilter,LogonAttributes logonAttributes){
		
		DSNEnquiryFilterVO dsnEnquiryFilterVO=new DSNEnquiryFilterVO();
		
		dsnEnquiryFilterVO.setDsn(carditFilter.getDespatchSerialNumber());
		dsnEnquiryFilterVO.setOoe(carditFilter.getOoe());
		dsnEnquiryFilterVO.setDoe(carditFilter.getDoe());
		dsnEnquiryFilterVO.setMailCategoryCode(carditFilter.getMailCategoryCode());
		dsnEnquiryFilterVO.setMailSubClass(carditFilter.getMailSubclass());
		if(carditFilter.getYear()!=null)
			dsnEnquiryFilterVO.setYear(Integer.parseInt(carditFilter.getYear()));
		dsnEnquiryFilterVO.setConsignmentNumber(carditFilter.getConDocNo());
		if(carditFilter.getConsignmentDate()!=null 
				&& carditFilter.getConsignmentDate().trim().length()>0){
			dsnEnquiryFilterVO.setConsignmentDate( 
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getConsignmentDate()));
		}
		if(carditFilter.getRdtDate()!=null && carditFilter.getRdtDate().trim().length()>0 &&
				carditFilter.getRdtTime()!=null && carditFilter.getRdtTime().trim().length()>0){
			LocalDate rdt = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			rdt.setDate(carditFilter.getRdtDate()); 
			rdt.setTime(carditFilter.getRdtTime(),true);  
			dsnEnquiryFilterVO.setRdt(rdt);
		}
		dsnEnquiryFilterVO.setCarrierCode(carditFilter.getCarrierCode());  
		dsnEnquiryFilterVO.setFlightNumber(carditFilter.getFlightNumber());
		if(carditFilter.getFlightDate()!=null 
				&& carditFilter.getFlightDate().trim().length()>0){
			dsnEnquiryFilterVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getFlightDate()));
		}
		dsnEnquiryFilterVO.setAirportCode(carditFilter.getAirportCode());
		dsnEnquiryFilterVO.setContainerNumber(carditFilter.getUldNumber());
		if(carditFilter.getFromDate()!=null 
				&& carditFilter.getFromDate().trim().length()>0){
		dsnEnquiryFilterVO.setFromDate(
				new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getFromDate()));
		}
		if(carditFilter.getToDate()!=null 
				&& carditFilter.getToDate().trim().length()>0){
		dsnEnquiryFilterVO.setToDate(
				new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getToDate()));
		}
		dsnEnquiryFilterVO.setStatus(carditFilter.getMailStatus());
		dsnEnquiryFilterVO.setPaCode(carditFilter.getPaCode());
		if(carditFilter.getAwbAttached()!=null && 
				carditFilter.getAwbAttached().trim().length()>0){
			if(MailConstantsVO.FLAG_YES.equals(carditFilter.getAwbAttached())){
				dsnEnquiryFilterVO.setAwbAttached(MailConstantsVO.FLAG_YES);
			}
			else{
				dsnEnquiryFilterVO.setAwbAttached(MailConstantsVO.FLAG_NO);
			}
		}
		dsnEnquiryFilterVO.setCapNotAcpEnabledFlag(carditFilter.getAwbAttached());
		dsnEnquiryFilterVO.setFlightType(carditFilter.getFlightType());
		dsnEnquiryFilterVO.setShipmentPrefix(carditFilter.getShipmentPrefix());
		dsnEnquiryFilterVO.setDocumentNumber(carditFilter.getDocumentNumber());
		dsnEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());

		return dsnEnquiryFilterVO;
		
	}   



/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.convertDsnVosToDsnModel
	 *	Added by 	:	A-8164 on 05-Sep-2019
	 * 	Used for 	:
	 *	Parameters	:	@param dsnvos
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<DSNDetails>
	 */
	public static Collection<DSNDetails> convertDsnVosToDsnModel(Page<DSNVO> dsnvos) {
		
		Collection<DSNDetails> dsnDetailsCollection=
				new ArrayList<>();
		if(!dsnvos.isEmpty()){
			for(DSNVO dsnvo:dsnvos){
				DSNDetails dsnDetails = new DSNDetails();  
				
				dsnDetails.setDsn(dsnvo.getDsn());
				dsnDetails.setPaCode(dsnvo.getPaCode());
				dsnDetails.setBags(dsnvo.getBags());
				dsnDetails.setOriginExchangeOffice(dsnvo.getOriginExchangeOffice());
				dsnDetails.setDestinationExchangeOffice(dsnvo.getDestinationExchangeOffice());  
				dsnDetails.setMailCategoryCode(dsnvo.getMailCategoryCode());
				dsnDetails.setMailSubclass(dsnvo.getMailSubclass());
				dsnDetails.setYear(dsnvo.getYear());
				dsnDetails.setCarrierCode(dsnvo.getCarrierCode());
				dsnDetails.setFlightNumber(dsnvo.getFlightNumber());
				if(dsnvo.getFlightDate()!=null)
					dsnDetails.setFlightDate(dsnvo.getFlightDate().toDisplayDateOnlyFormat());
				if(dsnvo.getWeight()!=null)
					dsnDetails.setWeight(Double.toString(dsnvo.getWeight().getRoundedDisplayValue()));
				dsnDetails.setShipmentPrefix(dsnvo.getShipmentCode());
				dsnDetails.setMasterDocumentNumber(dsnvo.getMasterDocumentNumber());
				dsnDetails.setCsgDocNum(dsnvo.getCsgDocNum()); 
				dsnDetails.setCompanyCode(dsnvo.getCompanyCode()); 
				if(dsnvo.getConsignmentDate()!=null)
					dsnDetails.setConsignmentDate(dsnvo.getConsignmentDate().toDisplayDateOnlyFormat());
				if(dsnvo.getReqDeliveryTime()!=null)
					dsnDetails.setRequiredDeliveryTime(dsnvo.getReqDeliveryTime().toDisplayFormat());
				dsnDetails.setUldNumber(dsnvo.getContainerNumber());
				if(dsnvo.getAcceptanceStatus()!=null){
					if(MailConstantsVO.FLAG_YES.equals(dsnvo.getAcceptanceStatus())){
						dsnDetails.setAcceptanceStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
					}
					else{
						dsnDetails.setAcceptanceStatus(MailConstantsVO.MAIL_STATUS_CAP_NOT_ACCEPTED);
					}
				}
				
				dsnDetailsCollection.add(dsnDetails);
			}
		}
		return dsnDetailsCollection;
	}

	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.constructCarditFilterVO
	 *	Added by 	:	A-8164 on 05-Sep-2019
	 * 	Used for 	:
	 *	Parameters	:	@param carditFilter
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	CarditEnquiryFilterVO
	 */
	public static CarditEnquiryFilterVO constructCarditFilterVO(CarditFilter carditFilter,
			LogonAttributes logonAttributes) {
		
		CarditEnquiryFilterVO carditEnquiryFilterVO=new CarditEnquiryFilterVO();
		
		carditEnquiryFilterVO.setDespatchSerialNumber(carditFilter.getDespatchSerialNumber());
		carditEnquiryFilterVO.setOoe(carditFilter.getOoe());
		carditEnquiryFilterVO.setDoe(carditFilter.getDoe());
		carditEnquiryFilterVO.setMailCategoryCode(carditFilter.getMailCategoryCode());
		carditEnquiryFilterVO.setMailSubclass(carditFilter.getMailSubclass());
		carditEnquiryFilterVO.setYear(carditFilter.getYear());
		carditEnquiryFilterVO.setConsignmentDocument(carditFilter.getConDocNo());
		if(carditFilter.getConsignmentDate()!=null 
				&& carditFilter.getConsignmentDate().trim().length()>0){
			carditEnquiryFilterVO.setConsignmentDate( 
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getConsignmentDate()));
		}
		if(carditFilter.getRdtDate()!=null && carditFilter.getRdtDate().trim().length()>0 &&
				carditFilter.getRdtTime()!=null && carditFilter.getRdtTime().trim().length()>0){
			LocalDate rdt = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			rdt.setDate(carditFilter.getRdtDate());
			rdt.setTime(carditFilter.getRdtTime(),true);
			carditEnquiryFilterVO.setReqDeliveryTime(rdt); 
		}
		carditEnquiryFilterVO.setCarrierCode(carditFilter.getCarrierCode());
		carditEnquiryFilterVO.setFlightNumber(carditFilter.getFlightNumber());
		if(carditFilter.getFlightDate()!=null 
				&& carditFilter.getFlightDate().trim().length()>0){
			carditEnquiryFilterVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getFlightDate()));
		}
		carditEnquiryFilterVO.setPol(carditFilter.getAirportCode());
		carditEnquiryFilterVO.setUldNumber(carditFilter.getUldNumber());
		if(carditFilter.getFromDate()!=null 
				&& carditFilter.getFromDate().trim().length()>0){
			carditEnquiryFilterVO.setFromDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getFromDate()));
		}
		if(carditFilter.getToDate()!=null 
				&& carditFilter.getToDate().trim().length()>0){
			carditEnquiryFilterVO.setToDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(carditFilter.getToDate()));
		}
		carditEnquiryFilterVO.setMailStatus(carditFilter.getMailStatus());
		carditEnquiryFilterVO.setPaoCode(carditFilter.getPaCode());
		if(carditFilter.getAwbAttached()!=null && 
				carditFilter.getAwbAttached().trim().length()>0){
			if(MailConstantsVO.FLAG_YES.equals(carditFilter.getAwbAttached())){
				carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_YES);
			}
			else{
				carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_NO);
			}
		}
		carditEnquiryFilterVO.setFlightType(carditFilter.getFlightType());
		carditEnquiryFilterVO.setShipmentPrefix(carditFilter.getShipmentPrefix());
		carditEnquiryFilterVO.setDocumentNumber(carditFilter.getDocumentNumber());
		carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode()); 
		
		return carditEnquiryFilterVO;
	}

	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.constructCarditFilterVOFromDsn
	 *	Added by 	:	A-8164 on 18-Sep-2019
	 * 	Used for 	:	
	 *	Parameters	:	@param dsnDetails
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	CarditEnquiryFilterVO
	 */
	public static CarditEnquiryFilterVO constructCarditFilterVOFromDsn(DSNDetails dsnDetails,
			LogonAttributes logonAttributes) {
		
		CarditEnquiryFilterVO carditEnquiryFilterVO=new CarditEnquiryFilterVO();
		
		carditEnquiryFilterVO.setDespatchSerialNumber(dsnDetails.getDsn());
		carditEnquiryFilterVO.setOoe(dsnDetails.getOriginExchangeOffice());
		carditEnquiryFilterVO.setDoe(dsnDetails.getDestinationExchangeOffice());
		carditEnquiryFilterVO.setMailCategoryCode(dsnDetails.getMailCategoryCode());
		carditEnquiryFilterVO.setMailSubclass(dsnDetails.getMailSubclass());
		carditEnquiryFilterVO.setYear(Integer.toString(dsnDetails.getYear()));
		carditEnquiryFilterVO.setConsignmentDocument(dsnDetails.getCsgDocNum());
		if(dsnDetails.getConsignmentDate()!=null 
				&& dsnDetails.getConsignmentDate().trim().length()>0){
			carditEnquiryFilterVO.setConsignmentDate( 
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(dsnDetails.getConsignmentDate()));
		}
		if(dsnDetails.getRequiredDeliveryTime()!=null && dsnDetails.getRequiredDeliveryTime().trim().length()>0){
			LocalDate rdt = new LocalDate(LocalDate.NO_STATION,Location.NONE, false);
			rdt.setDate(dsnDetails.getRequiredDeliveryTime().split(" ")[0]);
			rdt.setTime(dsnDetails.getRequiredDeliveryTime().split(" ")[1]);
			carditEnquiryFilterVO.setReqDeliveryTime(rdt);
		}
		carditEnquiryFilterVO.setCarrierCode(dsnDetails.getCarrierCode());
		carditEnquiryFilterVO.setFlightNumber(dsnDetails.getFlightNumber());
		if(dsnDetails.getFlightDate()!=null 
				&& dsnDetails.getFlightDate().trim().length()>0){
			carditEnquiryFilterVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(dsnDetails.getFlightDate()));
		}
		carditEnquiryFilterVO.setUldNumber(dsnDetails.getUldNumber());
		carditEnquiryFilterVO.setMailStatus(dsnDetails.getAcceptanceStatus());
		carditEnquiryFilterVO.setPaoCode(dsnDetails.getPaCode());
		carditEnquiryFilterVO.setShipmentPrefix(dsnDetails.getShipmentPrefix());
		carditEnquiryFilterVO.setDocumentNumber(dsnDetails.getMasterDocumentNumber());
		carditEnquiryFilterVO.setCompanyCode(dsnDetails.getCompanyCode());
		carditEnquiryFilterVO.setPageSize(dsnDetails.getBags());
		
		return carditEnquiryFilterVO;
}



//Added by A-8464 for ICRD-243079
public static List<MailMonitoringSummary> constructMailMonitorSummary(Collection<MailMonitorSummaryVO> summaryVO) {
		  ArrayList<MailMonitoringSummary> summaryList = new ArrayList<MailMonitoringSummary>();

		  MailMonitoringSummary mailSummary = null;

			if (summaryVO != null && summaryVO.size() > 0) {
				for (MailMonitorSummaryVO mailMonitorSummaryVO : summaryVO) {

					mailSummary = new MailMonitoringSummary();
					mailSummary.setMonitoringType(mailMonitorSummaryVO.getMonitoringType());
					mailSummary.setSector(mailMonitorSummaryVO.getSector());
					mailSummary.setValue(mailMonitorSummaryVO.getValue());
					summaryList.add(mailSummary);
				}

			}

			return summaryList;

}
public static ArrayList<Mailbag> constructExistingMailbags(Collection<ExistingMailbagVO> existingMailbagVOs) {
	ArrayList<Mailbag> mailbags = new ArrayList<Mailbag>();
	Mailbag mailbag = null;
	if (existingMailbagVOs != null && existingMailbagVOs.size() > 0) {
		for (ExistingMailbagVO mailbagvo : existingMailbagVOs) {
			mailbag = constructExistingMailbag(mailbagvo);
			mailbags.add(mailbag);
		}
	}
	return mailbags;
}
public static Mailbag constructExistingMailbag(ExistingMailbagVO mailbagvo) {
	ArrayList<Mailbag> mailbags = new ArrayList<Mailbag>();
	Mailbag mailbag = new Mailbag();
	mailbag = new Mailbag();
	mailbag.setMailbagId(mailbagvo.getMailId());
	mailbag.setBookingFlightDetailLastUpdTime(mailbagvo.getBookingFlightDetailLastUpdTime());
	mailbag.setBookingLastUpdateTime(mailbagvo.getBookingLastUpdateTime());
	mailbag.setCarrierCode(mailbagvo.getCarrierCode());
	mailbag.setCarrierId(mailbagvo.getCarrierId());
	mailbag.setContainerNumber(mailbagvo.getContainerNumber());
	mailbag.setContainerType(mailbagvo.getContainerType());
	mailbag.setCurrentAirport(mailbagvo.getCurrentAirport());
	mailbag.setFinalDestination(mailbagvo.getFinalDestination());
	//mailbag.setFlightDate(mailbagvo.getFlightDate());
	mailbag.setFlightNumber(mailbagvo.getFlightNumber());
	mailbag.setFlightSequenceNumber(mailbagvo.getFlightSequenceNumber());
	mailbag.setFlightStatus(mailbagvo.getFlightStatus());
	mailbag.setLegSerialNumber(mailbagvo.getLegSerialNumber());
	mailbag.setPol(mailbagvo.getPol());
	mailbag.setPou(mailbagvo.getPou());
	mailbag.setReassign(mailbagvo.getReassign());
	mailbag.setSegmentSerialNumber(mailbagvo.getSegmentSerialNumber());
	mailbag.setUbrNumber(mailbagvo.getUbrNumber());
	if(mailbagvo.getMailId().length() == 29) {
	  mailbag.setOoe(mailbagvo.getMailId().substring(0, 6));
	  mailbag.setDoe(mailbagvo.getMailId().substring(6, 12));
	  mailbag.setMailCategoryCode(mailbagvo.getMailId().substring(12, 13));
	  mailbag.setMailSubclass(mailbagvo.getMailId().substring(13, 15));
	 // mailbag.setYear((mailbagvo.getMailId().substring(15, 16).));
	  mailbag.setDespatchSerialNumber(mailbagvo.getMailId().substring(16, 20));
	  mailbag.setReceptacleSerialNumber(mailbagvo.getMailId().substring(20, 23));
	  mailbag.setHighestNumberedReceptacle(mailbagvo.getMailId().substring(23, 24));
	  mailbag.setRegisteredOrInsuredIndicator(mailbagvo.getMailId().substring(24, 25));
	}
	mailbag.setMailSequenceNumber(mailbagvo.getMalseqnum());
	return mailbag;
}

public static List<MailEvent> constructmailboxId(MailEventVO mail,List<MailEvent> mailevents) {
	MailEvent mailevent = new MailEvent();
	
	mailevent.setMailCategory(mail.getMailCategory());
	mailevent.setMailClass(mail.getMailClass());
	
	mailevent.setArrived(mail.isArrived());
	mailevent.setReceived(mail.isReceived());
	mailevent.setHandedOver(mail.isHandedOver());
	mailevent.setUplifted(mail.isUplifted());
	mailevent.setLoadedResditFlag(mail.isLoadedResditFlag());
	mailevent.setReturned(mail.isReturned());
	mailevent.setOnlineHandedOverResditFlag(mail.isOnlineHandedOverResditFlag());
	mailevent.setPending(mail.isPending());
	mailevent.setReadyForDelivery(mail.isReadyForDelivery());
	mailevent.setTransportationCompleted(mail.isTransportationCompleted());
	mailevent.setAssigned(mail.isAssigned());
	mailevent.setDelivered(mail.isDelivered());
	mailevent.setLostFlag(mail.isLostFlag());
	mailevent.setFoundFlag(mail.isFoundFlag());
	
	mailevents.add(mailevent);
	return mailevents;
}

public static MailboxIdVO convertMailevents(Collection<MailEvent> mailEvents, LogonAttributes logonAttributes , MailboxIdVO mailboxidVO) {
	
	List<MailEventVO> MailEventVOs = new ArrayList<MailEventVO>();
	
	for (MailEvent mailevent : mailEvents){
	
		MailEventVO mail = new MailEventVO();
			mail.setCompanyCode(logonAttributes.getCompanyCode());
			mail.setMailboxId(mailboxidVO.getMailboxID());
			mail.setMailCategory(mailevent.getMailCategory());
			mail.setMailClass(mailevent.getMailClass());
			mail.setArrived(mailevent.isArrived());
			mail.setHandedOver(mailevent.isHandedOver());	
			mail.setHandedOverReceivedResditFlag(mailevent.isHandedOverReceivedResditFlag());
			mail.setUplifted(mailevent.isUplifted());
			mail.setLoadedResditFlag(mailevent.isLoadedResditFlag());
			mail.setAssigned(mailevent.isAssigned());
			mail.setReturned(mailevent.isReturned());
			mail.setOnlineHandedOverResditFlag(mailevent.isOnlineHandedOverResditFlag());
			mail.setPending(mailevent.isPending());
			mail.setReadyForDelivery(mailevent.isReadyForDelivery());
			mail.setTransportationCompleted(mailevent.isTransportationCompleted());
			mail.setArrived(mailevent.isArrived());
			mail.setDelivered(mailevent.isDelivered());
			mail.setReceived(mailevent.isReceived());
			mail.setLostFlag(mailevent.isLostFlag());
            mail.setFoundFlag(mailevent.isFoundFlag());
			MailEventVOs.add(mail);
		}
			mailboxidVO.setMailEventVOs(MailEventVOs);
		
	return mailboxidVO;
}

public static MailRuleConfigVO convertMailRuleConfig(MailRuleConfig mailRuleConfig){
	MailRuleConfigVO mailRuleConfigVO=new MailRuleConfigVO();
	mailRuleConfigVO.setMailboxId(mailRuleConfig.getMailboxId());
	if(mailRuleConfig.getFromDate()!=null && !mailRuleConfig.getFromDate().equals("")){
	mailRuleConfigVO.setFromDate((new LocalDate(LocalDate.NO_STATION,
			Location.NONE, false).setDate(mailRuleConfig.getFromDate().split(" ")[0])));     
	}
	if(mailRuleConfig.getToDate()!=null&& !mailRuleConfig.getToDate().equals("")){
		mailRuleConfigVO.setToDate((new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate(mailRuleConfig.getToDate().split(" ")[0])));     
		}
	List<MailRuleConfigParameterVO> mailRuleConfigParametersList = new ArrayList<>();
	if(mailRuleConfig.getOoe()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getOoe(), "ORGOFCOD"));	
	}
	if(mailRuleConfig.getDoe()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getDoe(), "DSTOFCOD"));	
	}
	if(mailRuleConfig.getOriginAirport()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getOriginAirport(), "ORGARPCOD"));	
	}
	if(mailRuleConfig.getDestinationAirport()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getDestinationAirport(), "DSTARPCOD"));	
	}
	if(mailRuleConfig.getSubclass()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getSubclass(), "SUBCLSCOD"));	
	}
	if(mailRuleConfig.getContractCarrier()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getContractCarrier(), "CTRCARCOD"));	
	}
	if(mailRuleConfig.getXxresdit()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getXxresdit(), "XXRSDCOD"));	
	}
	if(mailRuleConfig.getCategoryCode()!=null){
		mailRuleConfigParametersList.addAll(createParameters(mailRuleConfig.getCategoryCode(), "CTGCOD"));	
	}
    mailRuleConfigVO.setMailRuleConfigParameters(mailRuleConfigParametersList);
	
	return mailRuleConfigVO;	
}
private static List<MailRuleConfigParameterVO> createParameters(String value, String parameterType){
	List<MailRuleConfigParameterVO> parameters=new ArrayList<>();
	MailRuleConfigParameterVO mailRuleConfigParameters=new MailRuleConfigParameterVO();
	mailRuleConfigParameters.setParameterCode(parameterType);
	mailRuleConfigParameters.setParameterValue(value);
	parameters.add(mailRuleConfigParameters);
	return parameters;
}			 


public static Collection<ConsignmentScreeningDetail> constructScreeningDetails(
		Collection<ConsignmentScreeningVO> consignmentScreening) {
	ArrayList<ConsignmentScreeningDetail> consignmentScreeningDetails = new ArrayList<ConsignmentScreeningDetail>();

	for(ConsignmentScreeningVO screeningVO : consignmentScreening){
	ConsignmentScreeningDetail consignmentScreeningDetail = new ConsignmentScreeningDetail();
		
	consignmentScreeningDetail.setScreeningLocation(screeningVO.getScreeningLocation());
	consignmentScreeningDetail.setScreeningMethodCode(screeningVO.getScreeningMethodCode());
	consignmentScreeningDetail.setStatedBags(screeningVO.getStatedBags());
	consignmentScreeningDetail.setStatedWeight(screeningVO.getStatedWeight().getRoundedDisplayValue());
	consignmentScreeningDetail.setSecurityStatusParty(screeningVO.getSecurityStatusParty());
	if(screeningVO.getSecurityStatusDate()!=null){
	consignmentScreeningDetail.setSecurityStatusDate(screeningVO.getSecurityStatusDate().toDisplayFormat());
	}
	else
	{
		consignmentScreeningDetail.setSecurityStatusDate("");
	}
	consignmentScreeningDetail.setScreeningAuthority(screeningVO.getScreeningAuthority());
	consignmentScreeningDetail.setScreeningRegulation(screeningVO.getScreeningRegulation());
	consignmentScreeningDetail.setAdditionalSecurityInfo(screeningVO.getAdditionalSecurityInfo());
	consignmentScreeningDetail.setRemarks(screeningVO.getRemarks());
	consignmentScreeningDetail.setSecurityStatusCode(screeningVO.getSecurityStatusCode());
	consignmentScreeningDetail.setSecurityReasonCode(screeningVO.getSecurityReasonCode());
	consignmentScreeningDetail.setPaCode(screeningVO.getPaCode());
	consignmentScreeningDetail.setCompanyCode(screeningVO.getCompanyCode());
	consignmentScreeningDetail.setConsignmentSequenceNumber(screeningVO.getConsignmentSequenceNumber());
	consignmentScreeningDetail.setSerialNumber(screeningVO.getSerialNumber());
	consignmentScreeningDetail.setConsignmentNumber(screeningVO.getConsignmentNumber());
	consignmentScreeningDetail.setDefaultReason("Mail");
	consignmentScreeningDetail.setCountryCode(screeningVO.getCountryCode());
	consignmentScreeningDetail.setResult(screeningVO.getResult());
	consignmentScreeningDetail.setAgenttype(screeningVO.getAgentType());
	consignmentScreeningDetail.setIsoCountryCode(screeningVO.getIsoCountryCode());
	consignmentScreeningDetail.setAgentId(screeningVO.getAgentID());
	consignmentScreeningDetail.setExpiryDate(screeningVO.getExpiryDate());

	consignmentScreeningDetail.setApplicableRegTransportDirection(screeningVO.getApplicableRegTransportDirection());
	consignmentScreeningDetail.setApplicableRegBorderAgencyAuthority(screeningVO.getApplicableRegBorderAgencyAuthority());
	consignmentScreeningDetail.setApplicableRegReferenceID(screeningVO.getApplicableRegReferenceID());
	consignmentScreeningDetail.setApplicableRegFlag(screeningVO.getApplicableRegFlag());
	consignmentScreeningDetail.setSeScreeningAuthority(screeningVO.getSeScreeningAuthority());
	consignmentScreeningDetail.setSeScreeningReasonCode(screeningVO.getSeScreeningReasonCode());
	consignmentScreeningDetail.setSeScreeningRegulation(screeningVO.getSeScreeningRegulation());
	consignmentScreeningDetails.add(consignmentScreeningDetail);
	}
	
	
	 return consignmentScreeningDetails;
}

	public static Collection<RoutingInConsignment> constructRoutingDetails(Collection<RoutingInConsignmentVO> routingInConsignmentVOs) {
	
	ArrayList<RoutingInConsignment> routingInConsignment = new ArrayList<RoutingInConsignment>();
	
		for(RoutingInConsignmentVO routing : routingInConsignmentVOs){
			
			RoutingInConsignment routingDetails = new RoutingInConsignment();
			routingDetails.setPou(routing.getPou());
			routingDetails.setPol(routing.getPol());
			routingDetails.setFlightCarrierCode(routing.getFlightCarrierCode());
			
			routingInConsignment.add(routingDetails);
		}
		
		return routingInConsignment;
	}

	public static Collection<ConsignmentScreeningVO> constructScreeningDetail(Collection<ConsignmentScreeningDetail> consignmentScreeningDetail, LogonAttributes logonAttributes) {
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<ConsignmentScreeningVO>();
		
		for(ConsignmentScreeningDetail screening : consignmentScreeningDetail){
			ConsignmentScreeningVO consignmentScreeningVO = new ConsignmentScreeningVO();
			
			consignmentScreeningVO.setScreeningLocation(screening.getScreeningLocation());
			consignmentScreeningVO.setScreeningMethodCode(screening.getScreeningMethodCode());
			consignmentScreeningVO.setStatedBags(screening.getStatedBags());
			consignmentScreeningVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, screening.getStatedWeight()));
			consignmentScreeningVO.setSecurityStatusParty(screening.getSecurityStatusParty());
			if(screening.getSecurityStatusDate()!=null){
				LocalDate date = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
				String time = screening.getSecurityStatusDate().split(" ")[1];
				if(time.length()==8){
					consignmentScreeningVO.setSecurityStatusDate((date.setDateAndTime(screening.getSecurityStatusDate()))); 
				}else{
					consignmentScreeningVO.setSecurityStatusDate((date.setDateAndTime(screening.getSecurityStatusDate(), true)));
			}
			}
			consignmentScreeningVO.setScreeningAuthority(screening.getScreeningAuthority());
			consignmentScreeningVO.setScreeningRegulation(screening.getScreeningRegulation());
			consignmentScreeningVO.setAdditionalSecurityInfo(screening.getAdditionalSecurityInfo());
			consignmentScreeningVO.setRemarks(screening.getRemarks());
			consignmentScreeningVO.setPaCode(screening.getPaCode());
			consignmentScreeningVO.setCompanyCode(logonAttributes.getCompanyCode());
			consignmentScreeningVO.setConsignmentNumber(screening.getConsignmentNumber());
			consignmentScreeningVO.setConsignmentSequenceNumber(screening.getConsignmentSequenceNumber());
			consignmentScreeningVO.setSerialNumber(screening.getSerialNumber());
			consignmentScreeningVO.setOpFlag(screening.getOpFlag());
			consignmentScreeningVO.setSecurityReasonCode(screening.getSecurityReasonCode());
			consignmentScreeningVO.setResult(screening.getResult());
			
			consignmentScreeningVOs.add(consignmentScreeningVO);
		}
			return consignmentScreeningVOs;
	}

	public static Collection<MailAttachmentVO> constructDamagedAttachmentVOs(Collection<DamagedMailbag> damagedMailbags,HashMap fileUploadMap, LogonAttributes logonAttributes) {
		Collection<MailAttachmentVO> mailAttachmentVOs = new ArrayList<>();
	
		
		MailAttachmentVO mailAttachmentVO = null;
		
		if (damagedMailbags!=null && !damagedMailbags.isEmpty()){
			int index=0;
			for(DamagedMailbag damagedMailbag : damagedMailbags){
				 if ( damagedMailbag.getFileName()!=null ) {
				mailAttachmentVO = constructDamagedAttachmentVO(damagedMailbag,fileUploadMap,index,logonAttributes);
				mailAttachmentVOs.add(mailAttachmentVO);
			}
			
				 index++;
			}	
		
		}
		return mailAttachmentVOs;
	}
		public static MailAttachmentVO constructDamagedAttachmentVO(DamagedMailbag damagedMailbag,HashMap fileUploadMap,int index, LogonAttributes logonAttributes) {
            MailAttachmentVO mailAttachmentVO=new MailAttachmentVO();	
			mailAttachmentVO.setFileName(damagedMailbag.getFileName());
			String uploadFileKey = new StringBuilder().append("UPLOAD_FILE_KEY_").append(index).toString();
			UploadFileModel file = (UploadFileModel)fileUploadMap.get(uploadFileKey);
			mailAttachmentVO.setFileData(file.getData());
			mailAttachmentVO.setContentType("IMG");
			mailAttachmentVO.setAttachmentStation(logonAttributes.getAirportCode());
			mailAttachmentVO.setAttachmentOpFlag("I");
			mailAttachmentVO.setCompanyCode(logonAttributes.getCompanyCode());
			mailAttachmentVO.setAttachmentType("IMG");
			mailAttachmentVO.setReference2(damagedMailbag.getDamageCode());
			mailAttachmentVO.setUploadUser(logonAttributes.getUserId());
			return mailAttachmentVO;
}

	public static Collection<ConsignmentScreeningDetail> constructMailbagSecurityDetails(
			Collection<ConsignmentScreeningVO> consignmentScreenings) {
		ArrayList<ConsignmentScreeningDetail> consignmentScreeningDetails = new ArrayList<>();
		boolean excludeSeconds=true;

		for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreenings) {
			ConsignmentScreeningDetail consignmentScreeningDetail = new ConsignmentScreeningDetail();
			consignmentScreeningDetail.setScreeningLocation(consignmentScreeningVO.getScreeningLocation());
			consignmentScreeningDetail.setScreeningMethodCode(consignmentScreeningVO.getScreeningMethodCode());
			consignmentScreeningDetail.setSecurityStatusParty(consignmentScreeningVO.getSecurityStatusParty());
			if (consignmentScreeningVO.getSecurityStatusDate() != null) {
				consignmentScreeningDetail.setSecurityStatusDate(
						consignmentScreeningVO.getSecurityStatusDate().toDisplayFormat(LocalDate.CALENDAR_DATE_FORMAT).toUpperCase());
				consignmentScreeningDetail.setTime(
						consignmentScreeningVO.getSecurityStatusDate().toDisplayTimeOnlyFormat(excludeSeconds));
			}
			consignmentScreeningDetail.setSourceIndicator(consignmentScreeningVO.getSource());
			consignmentScreeningDetail.setResult(consignmentScreeningVO.getResult());
			consignmentScreeningDetail.setAgenttype(consignmentScreeningVO.getAgentType());
			consignmentScreeningDetail.setIsoCountryCode(consignmentScreeningVO.getIsoCountryCode());
			consignmentScreeningDetail.setAgentId(consignmentScreeningVO.getAgentID());
			consignmentScreeningDetail.setSerialNumber(consignmentScreeningVO.getSerialNumber());
			consignmentScreeningDetail.setExpiryDate(consignmentScreeningVO.getExpiryDate());
			consignmentScreeningDetail.setScreenDetailType(consignmentScreeningVO.getScreenDetailType());

			consignmentScreeningDetails.add(consignmentScreeningDetail);
		}
		return consignmentScreeningDetails; 

	}

	public static OperationalFlightVO constructOperationalFlightVO(ContainerDetails containerData) {

		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCarrierCode(containerData.getCarrierCode());
		operationalFlightVO.setCarrierId(containerData.getCarrierId());
		operationalFlightVO.setCompanyCode(containerData.getCompanyCode());
		operationalFlightVO.setContainerNumber(containerData.getContainerNumber());

		if (containerData.getFlightNumber() != null) {
			operationalFlightVO.setFlightNumber(containerData.getFlightNumber());
		} else {
			operationalFlightVO.setFlightNumber("-1");
		}

		if (containerData.getFlightSequenceNumber() > 0) {
			operationalFlightVO.setFlightSequenceNumber(containerData.getFlightSequenceNumber());
			operationalFlightVO.setLegSerialNumber((int) containerData.getLegSerialNumber());
		} else {
			operationalFlightVO.setFlightSequenceNumber(-1);
			operationalFlightVO.setLegSerialNumber(-1);
		}
		operationalFlightVO.setRecordsPerPage(100);
		operationalFlightVO.setAirportCode(containerData.getPol());
		operationalFlightVO.setPol(containerData.getPol());
		if (containerData.getDestination() != null) {
			operationalFlightVO.setPou(containerData.getDestination());
		}

		if (containerData.getFlightDate() != null) {
			LocalDate date = new LocalDate(containerData.getPol(), Location.ARP, false);
			operationalFlightVO.setFlightDate(date.setDate(containerData.getFlightDate()));
		}
		operationalFlightVO.setDirection(OUTBOUND);
		return operationalFlightVO;
	}

}
