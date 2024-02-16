package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAttachmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.model.UploadFileModel;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.PageResult;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DamagedMailbag;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.DSNDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailInboundModelConverter.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	11-Dec-2018		:	Draft
 */

public class MailInboundModelConverter {
	
	private static final String INBOUND = "I";
	
	public static  ArrayList<MailinboundDetails> convertMailArrivalVosToModel(Collection<MailArrivalVO> mailArrivalVos) {
		

		ArrayList<MailinboundDetails> mailinboundDetailsCollection=       
				new ArrayList<MailinboundDetails>(); 
	
		for(MailArrivalVO mailArrivalVO: mailArrivalVos){  
			MailinboundDetails mailinboundDetails=new MailinboundDetails();
			if(null!=mailArrivalVO.getFlightNumber()){
				mailinboundDetails.setFlightNo(mailArrivalVO.getFlightNumber()); 
			}
			if(null!=mailArrivalVO.getFlightCarrierCode()){
				mailinboundDetails.setCarrierCode(mailArrivalVO.getFlightCarrierCode());
			}
			if(null!=mailArrivalVO.getFlightDate()){
				/*mailinboundDetails.setFlightDate(new StringBuffer()
						.append(mailArrivalVO.getFlightDate().toDisplayFormat("dd-MMM-yyyy hh:mm:ss")).append(" ")
						.append(mailArrivalVO.getArrivalTimeType()).toString());
			*/
				mailinboundDetails.setFlightDate(new StringBuffer().append(mailArrivalVO.getFlightDate().toDisplayDateOnlyFormat()+" "+mailArrivalVO.getFlightDate().toDisplayTimeOnlyFormat()).
				append(" ").append(mailArrivalVO.getArrivalTimeType()).toString());
			
			}
			if(null!=mailArrivalVO.getRoute()){
				mailinboundDetails.setFlightRoute(mailArrivalVO.getRoute());
				String ports[]=mailArrivalVO.getRoute().split("-");
				int pos=0;     
				for(String port:ports){
					if(null!=mailArrivalVO.getAirportCode()&&
							port.equalsIgnoreCase(mailArrivalVO.getAirportCode())){
						if(pos==0){
							pos++;
							continue;
							//commented as part of BUG IASCB-58553
							//mailinboundDetails.setPrevPort("--");
						}
						//else{
						//	mailinboundDetails.setPrevPort(ports[pos-1]);
						//}
						mailinboundDetails.setPrevPort(ports[pos-1]);
						break;
					}
					pos++;
				}
			}
			if(null!=mailArrivalVO.getFlightStatus()){
				if(mailArrivalVO.getFlightStatus().equals("ACT"))
					mailinboundDetails.setStatus("Active");  
				else if(mailArrivalVO.getFlightStatus().equals("CAN"))
					mailinboundDetails.setStatus("Cancelled");  
				
			}
			if(null!=mailArrivalVO.getAircraftType()){
				mailinboundDetails.setAircraftType(mailArrivalVO.getAircraftType()); 
			}
			if(null!=mailArrivalVO.getGateInfo()){
				mailinboundDetails.setGateInfo(mailArrivalVO.getGateInfo()); 
			}
			if(null!=mailArrivalVO.getAirportCode()){
				mailinboundDetails.setPort(mailArrivalVO.getAirportCode()); 
			}
			if(null!=mailArrivalVO.getRecievedInfo()){
				mailinboundDetails.setRecievedInfo(mailArrivalVO.getRecievedInfo()); 
			}
			if(null!=mailArrivalVO.getPol()){
				mailinboundDetails.setLegOrigin(mailArrivalVO.getPol()); 
			}
			if(0!=mailArrivalVO.getFlightSequenceNumber()){
				mailinboundDetails.setFlightSeqNumber(Long.toString(mailArrivalVO.getFlightSequenceNumber())); 
			}
			if(0!=mailArrivalVO.getCarrierId()){
				mailinboundDetails.setCarrierId(Integer.toString(mailArrivalVO.getCarrierId())); 
			}
			if(0!=mailArrivalVO.getLegSerialNumber()){
				mailinboundDetails.setLegSerialNumber(Integer.toString(mailArrivalVO.getLegSerialNumber())); 
			}
			if(null!=mailArrivalVO.getOperationalStatus()){
				if("O".equals(mailArrivalVO.getOperationalStatus())){ 
					mailinboundDetails.setFlightOperationStatus("Open");   
				}
				else if("C".equals(mailArrivalVO.getOperationalStatus())){
					mailinboundDetails.setFlightOperationStatus("Closed");     
				}
				else{
					mailinboundDetails.setFlightOperationStatus("New");
				}
				
			}
			if(null!=mailArrivalVO.getManifestInfo()){
				mailinboundDetails.setManifestInfo(mailArrivalVO.getManifestInfo()); 
			}	
			mailinboundDetails.setFlightStatus(mailArrivalVO.getFlightStatus());
			if(mailArrivalVO.getArrivalDate()!=null){
				mailinboundDetails.setSta(mailArrivalVO.getArrivalDate().toDisplayDateOnlyFormat());
			}
			mailinboundDetailsCollection.add(mailinboundDetails);
			
			if(null!=mailArrivalVO.getOnlineAirportParam()){
				mailinboundDetails.setOnlineAirportParam(mailArrivalVO.getOnlineAirportParam());
			}
		}

		return mailinboundDetailsCollection;
	}
	
	public static  OperationalFlightVO constructOperationalFlightVo(MailinboundDetails mailinboundDetails,LogonAttributes logonAttributes) {
		
		OperationalFlightVO operationalFlightVO=
				new OperationalFlightVO();

		
		String flightCarrierCode = null;
	    flightCarrierCode = 
	    		mailinboundDetails.getCarrierCode().trim().toUpperCase();
		operationalFlightVO.setCompanyCode(logonAttributes.getCompanyCode());
		operationalFlightVO.setPou(mailinboundDetails.getPort());
			
		operationalFlightVO.setFlightNumber(mailinboundDetails.getFlightNo());  
		operationalFlightVO.setCarrierCode(flightCarrierCode); 
		operationalFlightVO.setCarrierId(Integer.parseInt(mailinboundDetails.getCarrierId()));
		operationalFlightVO.setFlightDate((new LocalDate(LocalDate.NO_STATION,
				Location.NONE, false).setDate(mailinboundDetails.getFlightDate().split(" ")[0])));     
		operationalFlightVO.setLegSerialNumber(Integer.parseInt(mailinboundDetails.getLegSerialNumber()));
		operationalFlightVO.setFlightSequenceNumber(Long.parseLong(mailinboundDetails.getFlightSeqNumber()));
		operationalFlightVO.setFlightRoute(mailinboundDetails.getFlightRoute());
		operationalFlightVO.setDirection(INBOUND);
		operationalFlightVO.setPol(mailinboundDetails.getLegOrigin());
		
		return operationalFlightVO;
	}
	
	public static Collection<DamagedMailbagVO> populateDamagedMailbagVOs(
			ArrayList<DamagedMailbag> damagedMailBagCollection,LogonAttributes logonAttributes, HashMap<String, Collection<OneTimeVO>> oneTime){
		
		Collection<DamagedMailbagVO> damagedMailbagVOs = 
				new ArrayList<DamagedMailbagVO>();
		for(DamagedMailbag damagedMailbag:damagedMailBagCollection){
			DamagedMailbagVO damagedMailbagVO=new DamagedMailbagVO();
			
			damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
			damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			LocalDate currentdate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, 
					true);
			damagedMailbagVO.setDamageDate(currentdate);
			damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
			damagedMailbagVO.setOperationFlag(DamagedMailbagVO.OPERATION_FLAG_INSERT);
			damagedMailbagVO.setDamageCode(damagedMailbag.getDamageCode());
			damagedMailbagVO.setRemarks(damagedMailbag.getRemarks());
			damagedMailbagVO.setDamageDescription(handleDamageCodeDescription(damagedMailbag.getDamageCode(),oneTime));
			damagedMailbagVO.setAirportCode(logonAttributes.getAirportCode());
			damagedMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			LocalDate currentdate1 = new LocalDate(logonAttributes.getAirportCode(), 
					Location.ARP, true);
			damagedMailbagVO.setDamageDate(currentdate1);
			damagedMailbagVO.setUserCode(logonAttributes.getUserId().toUpperCase());
			damagedMailbagVO.setOperationType("I");   
			damagedMailbagVOs.add(damagedMailbagVO);
		}
		
		return damagedMailbagVOs;
	}
	
	
	private static String handleDamageCodeDescription(String damageCode, HashMap<String, Collection<OneTimeVO>> oneTime) {
		
		String damageDesc = "";
		Collection<OneTimeVO> damageCodes = oneTime.get("mailtracking.defaults.return.reasoncode");
		
		for (OneTimeVO vo : damageCodes) {
			
			if (vo.getFieldValue().equals(damageCode)) {
				damageDesc = vo.getFieldDescription();
				break;
			}
		}
		return damageDesc;
	}

	public static PageResult<ContainerDetails> convertContainerDetailsToModel(
			Page<ContainerDetailsVO> containerDetails, String flightCarrierCode) {
		
		ArrayList<ContainerDetails> containerDetailsCollection=
				new ArrayList<ContainerDetails>();
		for(ContainerDetailsVO containerDetailsVO:containerDetails){
			  
			ContainerDetails containerDetail=new ContainerDetails();
			containerDetail.setContainerno(containerDetailsVO.getContainerGroup());
			containerDetail.setContainerNumberWithBulk(containerDetailsVO.getContainerNumber());
			containerDetail.setMailBagCount(new StringBuffer()
					.append(Integer.toString(containerDetailsVO.getReceivedBags())).append('-')
						.append(Integer.toString(containerDetailsVO.getMailbagcount())).toString());
			if(null!=containerDetailsVO.getTotalWeight())
				containerDetail.setMailBagWeight(new StringBuffer(). 
						append(Double.toString(containerDetailsVO.getReceivedWeight()!=null?containerDetailsVO.getReceivedWeight().getRoundedDisplayValue():0)).append('-')
							.append(Double.toString(containerDetailsVO.getMailbagwt()!=null?containerDetailsVO.getMailbagwt().getRoundedDisplayValue():0)).toString()
					);  
			if(null!=containerDetailsVO.getTotalWeight())
				containerDetail.setWeightUnit(containerDetailsVO.getTotalWeight().getDisplayUnit());
			containerDetail.setPol(containerDetailsVO.getPol());
			containerDetail.setPou(containerDetailsVO.getPou());
			if(null!=containerDetailsVO.getDsnVOs()){
				containerDetail.setDsnVos((ArrayList<DSNVO>) containerDetailsVO.getDsnVOs());
			}
			if(0!=containerDetailsVO.getCarrierId()){  
				containerDetail.setCarrierId(Integer.toString(containerDetailsVO.getCarrierId()));
			}
			if(null!=flightCarrierCode){
				containerDetail.setCarrierCode(flightCarrierCode);   
			}
			if(0!=containerDetailsVO.getFlightSequenceNumber()){
				containerDetail.setFlightSeqNumber(Long.toString(containerDetailsVO.getFlightSequenceNumber()));
			}
			if(null!=containerDetailsVO.getFlightNumber()){
				containerDetail.setFlightNumber(containerDetailsVO.getFlightNumber());
			}
			if(null!=containerDetailsVO.getPaCode()){
				containerDetail.setContainerPa(containerDetailsVO.getPaCode());
			}
			if(null!=containerDetailsVO.getAssignedDate()){     
				containerDetail.setAssignmentDate(containerDetailsVO.getAssignedDate().toDisplayFormat());           
			}  
			if(null!=containerDetailsVO.getOnwardFlights()){
				containerDetail.setOnwardFlight(containerDetailsVO.getOnwardFlights());  
			}
			if(null!=containerDetailsVO.getTransferFromCarrier()){
				containerDetail.setTransfferCarrier(containerDetailsVO.getTransferFromCarrier());
			}
			if(null!=containerDetailsVO.getWareHouse()){
				containerDetail.setWareHouse(containerDetailsVO.getWareHouse());
			}
			if(null!=containerDetailsVO.getLocation()){
				containerDetail.setLocation(containerDetailsVO.getLocation());
			}
			if(null!=containerDetailsVO.getRemarks()){
				containerDetail.setRemarks(containerDetailsVO.getRemarks());
			}
			if(null!=containerDetailsVO.getBellyCartId()){
				containerDetail.setBellyCartId(containerDetailsVO.getBellyCartId());
			}
			if(null!=containerDetailsVO.getContainerType()){
				containerDetail.setContainerType(containerDetailsVO.getContainerType());
				if(containerDetailsVO.getContainerType().equals(MailConstantsVO.BULK_TYPE))
					containerDetail.setBarrowCheck(true);
				else
					containerDetail.setBarrowCheck(false);
			}
			if(null!=containerDetailsVO.getDeliveredStatus()){
				if(containerDetailsVO.getDeliveredStatus().equals(MailConstantsVO.FLAG_YES))
					containerDetail.setDeliveredCheck(true);
				else
					containerDetail.setDeliveredCheck(false);
			}
			if(null!=containerDetailsVO.getArrivedStatus()){
				if(containerDetailsVO.getArrivedStatus().equals(MailConstantsVO.FLAG_YES))
					containerDetail.setRecievedCheck(true);
				else
					containerDetail.setRecievedCheck(false);
			}
			if(null!=containerDetailsVO.getPaBuiltFlag()){
				if(containerDetailsVO.getPaBuiltFlag().equals(MailConstantsVO.FLAG_YES))
					containerDetail.setPaBuiltCheck(true);
				else
					containerDetail.setPaBuiltCheck(false);
			}
			if(null!=containerDetailsVO.getIntact()){
				if(containerDetailsVO.getIntact().equals(MailConstantsVO.FLAG_YES))
					containerDetail.setIntactCheck(true);
				else
					containerDetail.setIntactCheck(false);
			}
			if(null!=containerDetailsVO.getDestination()){
				containerDetail.setDestination(containerDetailsVO.getDestination());
			}if(null!=containerDetailsVO.getDestination()){
				containerDetail.setDestination(containerDetailsVO.getDestination());
			}
			if(null!=containerDetailsVO.getMinReqDelveryTime()){
				containerDetail.setMinReqDelveryTime(containerDetailsVO.getMinReqDelveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm"));
			}
			containerDetail.setContainerPureTransfer(containerDetailsVO.getContainerPureTransfer());//Added by A-8464 for ICRD-328502
			
			containerDetail.setContainerNumber(containerDetailsVO.getContainerNumber());
			containerDetail.setActWgtSta(containerDetailsVO.getActWgtSta());
			if(containerDetailsVO.getActualWeight()!=null)
			containerDetail.setActualWeight(containerDetailsVO.getActualWeight().getRoundedDisplayValue());
			containerDetail.setContentId(containerDetailsVO.getContentId());
			containerDetail.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
			containerDetail.setTransitFlag(containerDetailsVO.getTransitFlag());
			containerDetailsCollection.add(containerDetail);
		}
		
		PageResult<ContainerDetails> containerDetailsPageList =
				new PageResult<ContainerDetails>(containerDetails, containerDetailsCollection);
		return containerDetailsPageList;
	}
	
	public static PageResult<MailBagDetails> convertMailBagDetailsToModel(
			Page<MailbagVO> mailbagVOs) {
		
		ArrayList<MailBagDetails> mailBagDetailsCollection=
				new ArrayList<MailBagDetails>();
		if(mailbagVOs.size()>0)  
		for(MailbagVO mailBagVo:mailbagVOs){ 
			
			MailBagDetails mailBagDetails=new MailBagDetails();
			mailBagDetails.setMailBagId(mailBagVo.getMailbagId());  
			mailBagDetails.setMailBagStatus(mailBagVo.getMailStatus()); 
			if(null!=mailBagVo.getScannedDate()){
				mailBagDetails.setScanDate(
						mailBagVo.getScannedDate().toDisplayFormat()); 
			}
			if(null!=mailBagVo.getOrigin()&&null!=mailBagVo.getDestination()){ 
				mailBagDetails.setOriginDestPair(new StringBuffer()
						.append(mailBagVo.getOrigin()).append("-")
							.append(mailBagVo.getDestination()).toString());
			}  
			if(null!=mailBagVo.getPou()){
				mailBagDetails.setPou(mailBagVo.getPou()); 
			}
			if(0!=mailBagVo.getMailSequenceNumber()){
				mailBagDetails.setMailSequenceNumber(mailBagVo.getMailSequenceNumber());    
			}
			if(null!=mailBagVo.getOoe()){
				mailBagDetails.setOoe(mailBagVo.getOoe());
			}
			if(null!=mailBagVo.getDoe()){
				mailBagDetails.setDoe(mailBagVo.getDoe());
			}
			if(null!=mailBagVo.getMailCategoryCode()){
				mailBagDetails.setCategory(mailBagVo.getMailCategoryCode());
			}
			if(null!=mailBagVo.getMailSubclass()){
				mailBagDetails.setSubClass(mailBagVo.getMailSubclass());
			}
			mailBagDetails.setYear(Integer.toString(mailBagVo.getYear()));

			if(null!=mailBagVo.getDespatchSerialNumber()){
				mailBagDetails.setDSN(mailBagVo.getDespatchSerialNumber());
			}
			if(null!=mailBagVo.getReceptacleSerialNumber()){
				mailBagDetails.setRSN(mailBagVo.getReceptacleSerialNumber());
			}
			if(null!=mailBagVo.getWeight()){
				mailBagDetails.setWeight(Double.toString(mailBagVo.getWeight().getRoundedDisplayValue()));
			}
			if(null!=mailBagVo.getWeight()){
				mailBagDetails.setWeightUnit(mailBagVo.getWeight().getDisplayUnit());
			}
			if(null!=mailBagVo.getStrWeight()){
				mailBagDetails.setStationWeight(Double.toString(mailBagVo.getStrWeight().getRoundedDisplayValue()));
			}
			if(null!=mailBagVo.getStrWeight()){
				mailBagDetails.setStationUnit(mailBagVo.getStrWeight().getDisplayUnit());
			}
			if(null!=mailBagVo.getVolume()){
				mailBagDetails.setVolume(Double.toString(mailBagVo.getVolume().getRoundedDisplayValue()));
			}
			if(null!=mailBagVo.getTransferFromCarrier()){
				mailBagDetails.setTransfferCarrier(mailBagVo.getTransferFromCarrier());
			}
			if(null!=mailBagVo.getConsignmentNumber()){
				mailBagDetails.setConsignmentNumber(mailBagVo.getConsignmentNumber());
			}
			if(null!=mailBagVo.getSealNumber()){
				mailBagDetails.setSealNo(mailBagVo.getSealNumber());
			}
			if(null!=mailBagVo.getBellyCartId()){
				mailBagDetails.setBellyCartId(mailBagVo.getBellyCartId());
			}
			if(null!=mailBagVo.getDocumentNumber()){
				mailBagDetails.setAwb(mailBagVo.getDocumentNumber());
			}
			//Added by A-7540
			if(mailBagVo.getShipmentPrefix()!=null){
				mailBagDetails.setShipmentPrefix(mailBagVo.getShipmentPrefix());
			}
			if(null!=mailBagVo.getPaCode()){
				mailBagDetails.setPaCode(mailBagVo.getPaCode());
			}
			if(null!=mailBagVo.getMailRemarks()){
				mailBagDetails.setRemarks(mailBagVo.getMailRemarks());
			}
			if(0!=mailBagVo.getCarditSequenceNumber()){
				mailBagDetails.setCarditNo(Integer.toString(mailBagVo.getCarditSequenceNumber()));
			}
			if(null!=mailBagVo.getHighestNumberedReceptacle()){
				mailBagDetails.setHni(mailBagVo.getHighestNumberedReceptacle());
			}
			if(null!=mailBagVo.getRegisteredOrInsuredIndicator()){
				mailBagDetails.setRi(mailBagVo.getRegisteredOrInsuredIndicator());
			}
			if(null!=mailBagVo.getTransferFlag()){
				mailBagDetails.setTransferFlag(mailBagVo.getTransferFlag());
			}
			if(null!=mailBagVo.getArrivedFlag()){
				mailBagDetails.setArriveFlag(mailBagVo.getArrivedFlag());
			}
			if(null!=mailBagVo.getDeliveredFlag()){
				mailBagDetails.setDeliverFlag(mailBagVo.getDeliveredFlag());
			}
			if(null!=mailBagVo.getPltEnableFlag()){
				mailBagDetails.setPltEnableFlag(mailBagVo.getPltEnableFlag());
			}
			if(null!=mailBagVo.getTransitFlag()){
				mailBagDetails.setTransitFlag(mailBagVo.getTransitFlag());
			}
			if(null!=mailBagVo.getRoutingAvlFlag()){
				mailBagDetails.setRoutingAvlFlag(mailBagVo.getRoutingAvlFlag());
			}
			if(null!=mailBagVo.getMailCompanyCode()){
				mailBagDetails.setMailCompanyCode(mailBagVo.getMailCompanyCode()); 
			}
			if(null!=mailBagVo.getDamagedMailbags()&&mailBagVo.getDamagedMailbags().size()>0){  
				String damageInfo=""; 
				for(DamagedMailbagVO damagedMailbagVO:mailBagVo.getDamagedMailbags()){
					damageInfo=new StringBuffer().append(damageInfo)
							.append(damagedMailbagVO.getDamageDescription()).append("--")
									.append(damagedMailbagVO.getDamageDate()).append("--").toString();
				}
				mailBagDetails.setDamageInfo(damageInfo);      
			}
			 //Added by A-7929 as part of ICRD-346830
			if(null!=mailBagVo.getOnTimeDelivery()){
				mailBagDetails.setOnTimeDelivery(mailBagVo.getOnTimeDelivery());
			}
			if(null!=mailBagVo.getScanningWavedFlag()){
				mailBagDetails.setScanningWavedFlag(mailBagVo.getScanningWavedFlag());
			}
			if(null!=mailBagVo.getReqDeliveryTime()){
				mailBagDetails.setReqDeliveryDateAndTime(mailBagVo.getReqDeliveryTime().toDisplayFormat("dd-MMM-yyyy HH:mm"));
			}
			if(null!=mailBagVo.getTransWindowEndTime()){
				mailBagDetails.setTransportSrvWindow(mailBagVo.getTransWindowEndTime().toDisplayFormat("dd-MMM-yyyy HH:mm"));
			}
			if(null!=mailBagVo.getMailServiceLevel()){
				mailBagDetails.setServicelevel(mailBagVo.getMailServiceLevel());
			}
			mailBagDetailsCollection.add(mailBagDetails);
			
		}
		
		PageResult<MailBagDetails> mailbagDetailsPageList =
				new PageResult<MailBagDetails>(mailbagVOs, mailBagDetailsCollection);
		return mailbagDetailsPageList;
	}
	
	public static PageResult<DSNDetails> convertDsnDetailsToModel(
			Page<DSNVO> dsnvos) {
		
		ArrayList<DSNDetails> dsnDetailsCollection=
				new ArrayList<DSNDetails>(); 
		if(dsnvos.size()>0)
		for(DSNVO dsnvo:dsnvos){
			
			DSNDetails dsnDetails=new DSNDetails();
			if(null!=dsnvo.getCompanyCode()){
				dsnDetails.setCompanyCode(dsnvo.getCompanyCode());
			}
			if(null!=dsnvo.getDsn()){
				dsnDetails.setDsn(dsnvo.getDsn());
			}
			if(null!=dsnvo.getOriginExchangeOffice()){
				dsnDetails.setOriginExchangeOffice(dsnvo.getOriginExchangeOffice());
			}
			if(null!=dsnvo.getDestinationExchangeOffice()){
				dsnDetails.setDestinationExchangeOffice(dsnvo.getDestinationExchangeOffice());
			}
			if(null!=dsnvo.getMailClass()){
				dsnDetails.setMailClass(dsnvo.getMailClass());
			}
			if(0!=dsnvo.getYear()){
				dsnDetails.setYear(dsnvo.getYear());
			}
			if(null!=dsnvo.getMailCategoryCode()){
				dsnDetails.setMailCategoryCode(dsnvo.getMailCategoryCode());
			}
			if(null!=dsnvo.getMailSubclass()){
				dsnDetails.setMailSubclass(dsnvo.getMailSubclass());
			}
			if(null!=dsnvo.getRemarks()){
				dsnDetails.setRemarks(dsnvo.getRemarks());
			}
			if(0!=dsnvo.getBags()){
				dsnDetails.setBags(dsnvo.getBags());
			}
			if(null!=dsnvo.getCsgDocNum()){
				dsnDetails.setCsgDocNum(dsnvo.getCsgDocNum());
			}
			if(0!=dsnvo.getReceivedBags()){
				dsnDetails.setReceivedBags(dsnvo.getReceivedBags());
			}
			if(null!=dsnvo.getWeight()){
				dsnDetails.setWeight(dsnvo.getWeight()); 
			}
			if(null!=dsnvo.getReceivedWeight()){
				dsnDetails.setReceivedWeight(dsnvo.getReceivedWeight());
			}
			if(null!=dsnvo.getMasterDocumentNumber()){
				dsnDetails.setMasterDocNumber(dsnvo.getMasterDocumentNumber());
			}
			
			//Added by A-7540
			if(dsnvo.getDocumentOwnerCode()!=null){
				dsnDetails.setShipmentPrefix(dsnvo.getDocumentOwnerCode());
			}
			
			if(null!=dsnvo.getOrigin()){
				dsnDetails.setOrigin(dsnvo.getOrigin());
			}
			if(null!=dsnvo.getPou()){
				dsnDetails.setDestination(dsnvo.getPou());   
			}
			if(null!=dsnvo.getPaCode()){
				dsnDetails.setPaCode(dsnvo.getPaCode());
			}
			
			if(null!=dsnvo.getPltEnableFlag()){
				if(MailConstantsVO.FLAG_YES.equals(dsnvo.getPltEnableFlag()))
					dsnDetails.setPltEnable(true);
				else
					dsnDetails.setPltEnable(false);
			}
			if(null!=dsnvo.getRoutingAvl()){
				if(MailConstantsVO.FLAG_YES.equals(dsnvo.getRoutingAvl()))
					dsnDetails.setRoutingAvl(true);
				else
					dsnDetails.setRoutingAvl(false);
			}
			
			dsnDetailsCollection.add(dsnDetails);
			
		} 
		
		PageResult<DSNDetails> dsnDetailsPageList =
				new PageResult<DSNDetails>(dsnvos, dsnDetailsCollection);
		return dsnDetailsPageList;
	}
	
	public static AddMailbag populateMailDetails(MailbagVO mailBagVo) {
		
			AddMailbag mailBagDetails=new AddMailbag();     
			mailBagDetails.setMailbagId(mailBagVo.getMailbagId());   
			if(null!=mailBagVo.getScannedDate()){
				mailBagDetails.setScannedDate(
						mailBagVo.getScannedDate().toDisplayFormat()); 
			} 
			if(null!=mailBagVo.getOoe()){
				mailBagDetails.setOoe(mailBagVo.getOoe());
			}
			if(null!=mailBagVo.getDoe()){
				mailBagDetails.setDoe(mailBagVo.getDoe());
			}
			if(null!=mailBagVo.getMailCategoryCode()){
				mailBagDetails.setMailCategoryCode(mailBagVo.getMailCategoryCode());
			}
			if(null!=mailBagVo.getMailSubclass()){
				mailBagDetails.setMailSubclass(mailBagVo.getMailSubclass());
			}
			if(0!=mailBagVo.getYear()){
				mailBagDetails.setYear(Integer.toString(mailBagVo.getYear()));
			}
			if(null!=mailBagVo.getDespatchSerialNumber()){
				mailBagDetails.setDespatchSerialNumber(mailBagVo.getDespatchSerialNumber());
			}
			if(null!=mailBagVo.getReceptacleSerialNumber()){
				mailBagDetails.setReceptacleSerialNumber(mailBagVo.getReceptacleSerialNumber());
			}
			if(null!=mailBagVo.getWeight()){
				mailBagDetails.setWeight(Double.toString(mailBagVo.getWeight().getRoundedDisplayValue()));
			}
			if(null!=mailBagVo.getVolume()){
				mailBagDetails.setVolume(Double.toString(mailBagVo.getVolume().getRoundedDisplayValue()));
			}
			if(null!=mailBagVo.getSealNumber()){
				mailBagDetails.setSealNumber(mailBagVo.getSealNumber());
			}
			if(null!=mailBagVo.getMailRemarks()){
				mailBagDetails.setRemarks(mailBagVo.getMailRemarks());
			}
			if(null!=mailBagVo.getHighestNumberedReceptacle()){
				mailBagDetails.setHighestNumberedReceptacle(mailBagVo.getHighestNumberedReceptacle());
			}
			if(null!=mailBagVo.getRegisteredOrInsuredIndicator()){
				mailBagDetails.setRegisteredOrInsuredIndicator(mailBagVo.getRegisteredOrInsuredIndicator());
			}
			if(null!=mailBagVo.getArrivedFlag()){
				mailBagDetails.setRcvd(mailBagVo.getArrivedFlag());
			}
			if(null!=mailBagVo.getDeliveredFlag()){
				mailBagDetails.setDlvd(mailBagVo.getDeliveredFlag());
			}
			if(null!=mailBagVo.getMailOrigin()){
				 mailBagDetails.setMailOrigin(mailBagVo.getMailOrigin());
			}
			if(null!=mailBagVo.getMailDestination()){
				mailBagDetails.setMailDestination(mailBagVo.getMailDestination());
			}
			return mailBagDetails;
		}
	public ContainerVO convertContainerDetailsToContainerVO(ContainerDetails containerDetail, LogonAttributes logonAttributes){
		ContainerVO containerVO =  new ContainerVO();
		containerVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerVO.setAssignedPort(containerDetail.getPol());
		if(containerDetail.getCarrierId()!=null){
			int carrierId = Integer.parseInt(containerDetail.getCarrierId());
			containerVO.setCarrierId(carrierId);
		}
		containerVO.setContainerNumber(containerDetail.getContainerNumber());
		containerVO.setFlightNumber(containerDetail.getFlightNumber());
		if(containerDetail.getFlightSeqNumber()!=null){
			long flightSeqNumber = Long.parseLong(containerDetail.getFlightSeqNumber());
			containerVO.setFlightSequenceNumber(flightSeqNumber);
		}
		containerVO.setLegSerialNumber(containerDetail.getLegSerialNumber());
		containerVO.setPol(containerDetail.getPol());
		containerVO.setPou(containerDetail.getPou());
		containerVO.setCarrierCode(containerDetail.getCarrierCode());
		return containerVO;
		}
	
	public static Collection<MailAttachmentVO> constructDamagedAttachmentVOs(Collection<DamagedMailbag> damagedMailbags,HashMap fileUploadMap, LogonAttributes logonAttributes) {
		Collection<MailAttachmentVO> mailAttachmentVOs = new ArrayList<MailAttachmentVO>();
	
		
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
		public static ContainerDetailsVO constructContainerDetailsVO(ContainerDetails containerDetails, LogonAttributes logonAttributes) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCarrierId (Integer.valueOf( (containerDetails.getCarrierId())));
			containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			containerDetailsVO.setFlightNumber(containerDetails.getFlightNumber());
		    containerDetailsVO.setContainerNumber(containerDetails.getContainerNumber());
		    containerDetailsVO.setFlightSequenceNumber( Long.parseLong((containerDetails.getFlightSeqNumber())));
		   if(containerDetails.getDesptachDetailsCollection()!=null) {
			  List<DSNVO> despatchVos = constructDSNVOs(containerDetails.getDesptachDetailsCollection());
			  containerDetailsVO.setDsnVOs(despatchVos);
		    }
		   if ((containerDetails.getMailBagDetailsCollection() != null) && (containerDetails.getMailBagDetailsCollection().size() > 0))
		    {  
		    	List<MailbagVO> localmailbagList = new ArrayList<>();
		        Iterator<MailBagDetails> localIterator = containerDetails.getMailBagDetailsCollection().iterator();
		    	 while (localIterator.hasNext()) {
		    	    	    MailbagVO mailbagvo= null; 
		    	    	    MailBagDetails mailbag = localIterator.next();
		    	    	    mailbagvo=constructMailbagVO(mailbag);
		    	    	    localmailbagList.add(mailbagvo);
		    	   }
		    	 containerDetailsVO.setMailDetails(localmailbagList);
		    }
		    return containerDetailsVO; 
		}
		public static List<DSNVO> constructDSNVOs(Collection<DespatchDetails> despatchDetails) {
			List<DSNVO> localDSNVoList = new ArrayList<>();
			 if ((despatchDetails != null)  && (despatchDetails.size() > 0))
			    {
			      Iterator<DespatchDetails> localIterator = despatchDetails.iterator();
			      while (localIterator.hasNext()) {
			    DSNVO dsnVO = new DSNVO();
			    DespatchDetails despatch= localIterator.next();
			    dsnVO.setAwbNumber(despatch.getMasterDocumentNumber());
			    dsnVO.setDsn(despatch.getDsn());
			    dsnVO.setOriginExchangeOffice(despatch.getOriginExchangeOffice());
			    dsnVO.setDestinationExchangeOffice(despatch.getDestinationExchangeOffice());
			    dsnVO.setMailClass(despatch.getMailClass());
			    dsnVO.setYear(despatch.getYear());
			    dsnVO.setMailCategoryCode(despatch.getMailCategoryCode());
			    dsnVO.setMailSubclass(despatch.getMailSubclass());
			    localDSNVoList.add(dsnVO);
			      }
			    }
			 return localDSNVoList;
		}
		public static MailbagVO constructMailbagVO(MailBagDetails mailbagDetail) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setMailSequenceNumber(mailbagDetail.getMailSequenceNumber());
			mailbagVO.setOoe(mailbagDetail.getOoe());
			mailbagVO.setDoe(mailbagDetail.getDoe());
			mailbagVO.setMailCategoryCode(mailbagDetail.getCategory());
			mailbagVO.setMailSubclass(mailbagDetail.getSubClass());
			mailbagVO.setYear(Integer.parseInt((mailbagDetail.getYear())));
		    return mailbagVO;
		}
}