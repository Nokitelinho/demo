package com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common;


import static com.ibsplc.icargo.framework.util.time.Location.ARP;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common.MailBookingDetail;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



public class MailOperationsModelConverter {
	
	private MailOperationsModelConverter(){
		
	}
	
	public static MailBookingFilterVO constructMailBookingFilterVO(AwbFilter awbFilter,LogonAttributes logonAttributes) {
		MailBookingFilterVO mailBookingFilterVO= new MailBookingFilterVO();
		LocalDate date = null;
		mailBookingFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
    	mailBookingFilterVO.setAgentCode(awbFilter.getAgentCode());
    	mailBookingFilterVO.setBookingCarrierCode(awbFilter.getBookingCarrierCode());
    	mailBookingFilterVO.setBookingFlightFrom(awbFilter.getBookingFlightFrom());
    	mailBookingFilterVO.setBookingFlightNumber(awbFilter.getBookingFlightNumber());
    	mailBookingFilterVO.setBookingFlightTo(awbFilter.getBookingFlightTo());
    	if(awbFilter.getBookingFrom()!=null &&  awbFilter.getBookingFrom().trim().length()>0){
    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    	mailBookingFilterVO.setBookingFrom(date.setDate(awbFilter.getBookingFrom()));
    	}
    	mailBookingFilterVO.setBookingStatus(awbFilter.getBookingStatus());
    	if(awbFilter.getBookingTo()!=null&& awbFilter.getBookingTo().trim().length()>0){
    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    	mailBookingFilterVO.setBookingTo(date.setDate(awbFilter.getBookingTo()));
    	}
    	mailBookingFilterVO.setBookingUserId(awbFilter.getBookingUserId());
    	mailBookingFilterVO.setCustomerCode(awbFilter.getCustomerCode());
    	mailBookingFilterVO.setDestinationOfBooking(awbFilter.getDestinationOfBooking());
    	mailBookingFilterVO.setMailScc(awbFilter.getMailScc());
    	mailBookingFilterVO.setMasterDocumentNumber(awbFilter.getMasterDocumentNumber());
    	mailBookingFilterVO.setOrginOfBooking(awbFilter.getOrginOfBooking());
    	mailBookingFilterVO.setProduct(awbFilter.getMailProduct());
    	if(awbFilter.getShipmentDate()!=null && awbFilter.getShipmentDate().trim().length()>0){
    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
    		mailBookingFilterVO.setShipmentDate(date.setDate(awbFilter.getShipmentDate()));
    	}
    	mailBookingFilterVO.setScreenName(awbFilter.getScreenName());
    	mailBookingFilterVO.setShipmentPrefix((awbFilter.getShipmentPrefix()));
    	mailBookingFilterVO.setStationOfBooking(awbFilter.getStationOfBooking());
    	mailBookingFilterVO.setViaPointOfBooking(awbFilter.getViaPointOfBooking());
    	return mailBookingFilterVO;
	}
	
	public static Collection<MailBookingDetail> convertMailBookingDetailVosToModel(
			Page<MailBookingDetailVO> mailBookingDetailVOs){
		Collection<MailBookingDetail> mailBookingDetailsCollection =
				new ArrayList<>();
		if(mailBookingDetailVOs!=null && !mailBookingDetailVOs.isEmpty()){
			for(MailBookingDetailVO mailBookingDetailVO:mailBookingDetailVOs){
				MailBookingDetail mailBookingDetail= new MailBookingDetail();
				mailBookingDetail.setShipmentPrefix(mailBookingDetailVO.getShipmentPrefix());
				mailBookingDetail.setMasterDocumentNumber(mailBookingDetailVO.getMasterDocumentNumber());
				mailBookingDetail.setSelectedFlightNumber(mailBookingDetailVO.getSelectedFlightNumber());
				mailBookingDetail.setAgentCode(mailBookingDetailVO.getAgentCode());
				mailBookingDetail.setMailScc(mailBookingDetailVO.getMailScc());
				mailBookingDetail.setAwbOrgin(mailBookingDetailVO.getAwbOrgin());
				mailBookingDetail.setAwbDestination(mailBookingDetailVO.getAwbDestination());
				mailBookingDetail.setBookedPieces(Integer.toString(mailBookingDetailVO.getBookedPieces()));
				mailBookingDetail.setBookedWeight(Double.toString(mailBookingDetailVO.getBookedWeight()));
				mailBookingDetail.setBookedVolume(Double.toString(mailBookingDetailVO.getBookedVolume()));
				mailBookingDetail.setBookingStatus(mailBookingDetailVO.getBookingStatus());
				mailBookingDetail.setShipmentStatus(mailBookingDetailVO.getShipmentStatus());
				mailBookingDetail.setBookingStation(mailBookingDetailVO.getBookingStation());
				mailBookingDetail.setRemarks(mailBookingDetailVO.getRemarks());
				mailBookingDetail.setOwnerId(Integer.toString(mailBookingDetailVO.getOwnerId()));
				mailBookingDetail.setDuplicateNumber(Integer.toString(mailBookingDetailVO.getDuplicateNumber()));
				mailBookingDetail.setSequenceNumber(Integer.toString(mailBookingDetailVO.getSequenceNumber()));
				mailBookingDetail.setBookingCarrierCode(mailBookingDetailVO.getBookingCarrierCode());
				mailBookingDetail.setBookingFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
				mailBookingDetail.setBookingFlightSequenceNumber(Integer.toString(mailBookingDetailVO.getBookingFlightSequenceNumber()));
				mailBookingDetail.setStatedPieces(Integer.toString(mailBookingDetailVO.getStatedPieces()));
				mailBookingDetail.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				mailBookingDetail.setSegementserialNumber(Integer.toString(mailBookingDetailVO.getSegementserialNumber()));
				mailBookingDetail.setSerialNumber(Integer.toString(mailBookingDetailVO.getSerialNumber()));
				mailBookingDetail.setMailSequenceNumber(Long.toString(mailBookingDetailVO.getMailSequenceNumber()));
				mailBookingDetail.setOrigin(mailBookingDetailVO.getOrigin());
				mailBookingDetail.setDestination(mailBookingDetailVO.getDestination());
				mailBookingDetail.setBookingFlightCarrierid(Integer.toString(mailBookingDetailVO.getBookingFlightCarrierid()));
				mailBookingDetail.setSplitBooking(mailBookingDetailVO.isSplitBooking());
				mailBookingDetail.setDestinationCheckReq(mailBookingDetailVO.isDestinationCheckReq());
				mailBookingDetail.setSegementserialNumber(Integer.toString(mailBookingDetailVO.getSegementserialNumber()));
				mailBookingDetail.setBookedFlights(mailBookingDetailVO.getBookedFlights()); 
				mailBookingDetail.setAttachmentSource(mailBookingDetailVO.getAttachmentSource());
				populateDatesForMailBookingVos(mailBookingDetailVO,mailBookingDetail);
				
				mailBookingDetailsCollection.add(mailBookingDetail);
			}
		}
		return mailBookingDetailsCollection;
	}
		
		private static MailBookingDetail populateDatesForMailBookingVos(MailBookingDetailVO mailBookingDetailVO,
			MailBookingDetail mailBookingDetail) {
			if(mailBookingDetailVO.getSelectedFlightNumber()!=null && !("-1".equals(mailBookingDetailVO.getSelectedFlightNumber()))
					&& mailBookingDetailVO.getSelectedFlightNumber().length()>0 ){
				mailBookingDetail.setFlightDetails(mailBookingDetailVO.getSelectedFlightNumber().split(","));
			}
			if(mailBookingDetailVO.getShipmentDate()!=null){
				mailBookingDetail.setShipmentDate(mailBookingDetailVO.getShipmentDate().toDisplayDateOnlyFormat());
			}
			if(mailBookingDetailVO.getBookingDate()!=null){
				mailBookingDetail.setBookingDate(mailBookingDetailVO.getBookingDate().toDisplayDateOnlyFormat());
			}
			if(mailBookingDetailVO.getBookingFlightDate()!=null){
				mailBookingDetail.setBookingFlightDate(mailBookingDetailVO.getBookingFlightDate().toDisplayDateOnlyFormat());
			}
			if(mailBookingDetailVO.getSelectedFlightDate()!=null){
				mailBookingDetail.setSelectedFlightDate(mailBookingDetailVO.getSelectedFlightDate().toDisplayDateOnlyFormat());
			}
			return mailBookingDetail;
		}

		public static Map<String,Collection<OneTime>> constructOneTimeValues(Map<String,Collection<OneTimeVO>> oneTimeValues){
			HashMap<String,Collection<OneTime>> oneTimeValuesMap = new HashMap<>();
			for(Map.Entry<String,Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()){
				ArrayList<OneTime> oneTimes= new ArrayList<>();
				for(OneTimeVO oneTimeVO : oneTimeValue.getValue()){
					OneTime oneTime =  new OneTime();
					oneTime.setFieldType(oneTimeVO.getFieldType());
					oneTime.setFieldValue(oneTimeVO.getFieldValue());
					oneTime.setFieldDescription(oneTimeVO.getFieldDescription());
					oneTimes.add(oneTime);
				}
				oneTimeValuesMap.put(oneTimeValue.getKey(), oneTimes);
			}
			return oneTimeValuesMap;
		}
		
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
			carditEnquiryFilterVO.setMailCount(Integer.valueOf(carditFilter.getMailCount()));
			
			return carditEnquiryFilterVO;
		}
		
		public static Collection<MailBookingDetailVO> convertMailBookingModelCollectionToVos(
				Collection<MailBookingDetail> mailBookingDetailCollection){
			Collection<MailBookingDetailVO> mailBookingDetailVOs=
					new ArrayList<>();
			if(mailBookingDetailCollection!=null && !mailBookingDetailCollection.isEmpty()){
				for(MailBookingDetail mailBookingDetail:mailBookingDetailCollection){
					MailBookingDetailVO mailBookingDetailVO= new MailBookingDetailVO();
					mailBookingDetailVO.setShipmentPrefix(mailBookingDetail.getShipmentPrefix());
					mailBookingDetailVO.setMasterDocumentNumber(mailBookingDetail.getMasterDocumentNumber());
					mailBookingDetailVO.setSelectedFlightNumber(mailBookingDetail.getSelectedFlightNumber());				
					mailBookingDetailVO.setAgentCode(mailBookingDetail.getAgentCode());
					mailBookingDetailVO.setMailScc(mailBookingDetail.getMailScc());
					if(mailBookingDetailVO.getShipmentDate()!=null){
						mailBookingDetailVO.setShipmentDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailBookingDetail.getShipmentDate()));
					}
					mailBookingDetailVO.setAwbOrgin(mailBookingDetail.getAwbOrgin());
					mailBookingDetailVO.setAwbDestination(mailBookingDetail.getAwbDestination());
					mailBookingDetailVO.setBookedPieces(Integer.parseInt(mailBookingDetail.getBookedPieces()));
					mailBookingDetailVO.setBookedWeight(Double.parseDouble(mailBookingDetail.getBookedWeight()));
					mailBookingDetailVO.setBookedVolume(Double.parseDouble(mailBookingDetail.getBookedVolume()));
					mailBookingDetailVO.setBookingStatus(mailBookingDetail.getBookingStatus());
					mailBookingDetailVO.setShipmentStatus(mailBookingDetail.getShipmentStatus());
					mailBookingDetailVO.setBookingStation(mailBookingDetail.getBookingStation());
					if(mailBookingDetailVO.getBookingDate()!=null){
						mailBookingDetailVO.setBookingDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailBookingDetail.getBookingDate()));
					}
					mailBookingDetailVO.setRemarks(mailBookingDetail.getRemarks());
					mailBookingDetailVO.setOwnerId(Integer.parseInt(mailBookingDetail.getOwnerId()));
					mailBookingDetailVO.setDuplicateNumber(Integer.parseInt(mailBookingDetail.getDuplicateNumber()));
					mailBookingDetailVO.setSequenceNumber(Integer.parseInt(mailBookingDetail.getSequenceNumber()));
					mailBookingDetailVO.setBookingCarrierCode(mailBookingDetail.getBookingCarrierCode());
					mailBookingDetailVO.setBookingFlightNumber(mailBookingDetail.getBookingFlightNumber());
					mailBookingDetailVO.setBookingFlightSequenceNumber(Integer.parseInt(mailBookingDetail.getBookingFlightSequenceNumber()));
					if(mailBookingDetailVO.getBookingFlightDate()!=null){
						mailBookingDetailVO.setBookingFlightDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailBookingDetail.getBookingFlightDate()));
					}
					mailBookingDetailVO.setStatedPieces(Integer.parseInt(mailBookingDetail.getStatedPieces()));
					mailBookingDetailVO.setCompanyCode(mailBookingDetail.getCompanyCode());
					mailBookingDetailVO.setSegementserialNumber(Integer.parseInt(mailBookingDetail.getSegementserialNumber()));
					mailBookingDetailVO.setSerialNumber(Integer.parseInt(mailBookingDetail.getSerialNumber()));
					mailBookingDetailVO.setMailSequenceNumber(Long.parseLong(mailBookingDetail.getMailSequenceNumber()));
					mailBookingDetailVO.setOrigin(mailBookingDetail.getOrigin());
					mailBookingDetailVO.setDestination(mailBookingDetail.getDestination());
					mailBookingDetailVO.setBookingFlightCarrierid(Integer.parseInt(mailBookingDetail.getBookingFlightCarrierid()));
					validateFlightDate(mailBookingDetailVO,mailBookingDetail);
					mailBookingDetailVO.setSplitBooking(mailBookingDetail.isSplitBooking());
					mailBookingDetailVO.setDestinationCheckReq(mailBookingDetail.isDestinationCheckReq());
					mailBookingDetailVO.setSegementserialNumber(Integer.parseInt(mailBookingDetail.getSegementserialNumber()));
					mailBookingDetailVO.setBookedFlights(mailBookingDetail.getBookedFlights());
					mailBookingDetailVO.setAttachmentSource(mailBookingDetail.getAttachmentSource());
					mailBookingDetailVOs.add(mailBookingDetailVO);
				}
			}
			return mailBookingDetailVOs;
		}

		private static MailBookingDetailVO validateFlightDate(MailBookingDetailVO mailBookingDetailVO,
				MailBookingDetail mailBookingDetail) {
			if(mailBookingDetailVO.getSelectedFlightDate()!=null){
				mailBookingDetailVO.setSelectedFlightDate(
						new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailBookingDetail.getSelectedFlightDate()));
			}
			return mailBookingDetailVO;
		}

		public static Collection<MailbagVO> constructMailBagVOs(Collection<Mailbag> selectedMailbagVO, LogonAttributes logonAttributes) {
			ArrayList<MailbagVO> mailbagVOs = new ArrayList<>();
			MailbagVO mailbagVO = null;

			if (selectedMailbagVO != null) {
				for (Mailbag mailbagDetail : selectedMailbagVO) {
					mailbagVO = constructMailbagVO(mailbagDetail, logonAttributes);
					mailbagVOs.add(mailbagVO);
				}
			}
			return mailbagVOs;
		}
		
		public static MailbagVO constructMailbagVO(Mailbag mailbagDetail,LogonAttributes logonAttributes) {

			MailbagVO mailbagVO = new MailbagVO();
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
	         mailbagVO.setActualWeight(actualWeight); 
	         mailbagVO.setPaBuiltFlag(mailbagDetail.getPaBuiltFlag());
	         mailbagVO.setAcceptancePostalContainerNumber(mailbagDetail.getAcceptancePostalContainerNumber());
	         mailbagVO.setAcceptanceAirportCode(mailbagDetail.getAcceptancePostalAirportCode());
	         mailbagVO.setPaBuiltFlagUpdate(mailbagDetail.isPaBuiltFlagUpdate());
	         mailbagVO.setPaContainerNumberUpdate(mailbagDetail.isPaContainerNumberUpdate());
	         mailbagVO.setTransferFromCarrier(mailbagDetail.getTransferFromCarrier());
			return mailbagVO;

		}

		public static MailBookingFilterVO constructLoadPlanFilterVO(AwbFilter awbFilter, LogonAttributes logonAttributes) {
			MailBookingFilterVO mailBookingFilterVO = new MailBookingFilterVO();
			LocalDate date = null;
			mailBookingFilterVO.setShipmentPrefix(awbFilter.getShipmentPrefix());
			mailBookingFilterVO.setMasterDocumentNumber(awbFilter.getMasterDocumentNumber());
			if(awbFilter.getPlannedFlightDateFrom()!=null &&  awbFilter.getPlannedFlightDateFrom().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setPlannedFlightDateFrom(date.setDate(awbFilter.getPlannedFlightDateFrom()));
	    	}
	    	if(awbFilter.getPlannedFlightDateTo()!=null&& awbFilter.getPlannedFlightDateTo().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setPlannedFlightDateTo(date.setDate(awbFilter.getPlannedFlightDateTo()));
	    	}
	    	mailBookingFilterVO.setFlightNumber(awbFilter.getFlightNumber());
	    	mailBookingFilterVO.setCarrierCode(awbFilter.getCarrierCode());
			if(awbFilter.getFlightDate()!=null&& awbFilter.getFlightDate().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setFlightDate(date.setDate(awbFilter.getFlightDate()));
	    	}
			mailBookingFilterVO.setOrigin(awbFilter.getOrigin());
			mailBookingFilterVO.setDestination(awbFilter.getDestination());
			mailBookingFilterVO.setPol(awbFilter.getPol());
			mailBookingFilterVO.setPou(awbFilter.getPou());
			mailBookingFilterVO.setMailScc(awbFilter.getMailScc());
			
			return mailBookingFilterVO;
		}

		public static Collection<MailBookingDetail> convertLoadPlanDetailVOsToModel(
				Page<MailBookingDetailVO> loadPlanDetailVos) {
			Collection<MailBookingDetail> loadPlanDetailCollection = new ArrayList<>();
			if(loadPlanDetailVos!=null && !loadPlanDetailVos.isEmpty()){
				for(MailBookingDetailVO loadPlanDetailVO : loadPlanDetailVos){
					MailBookingDetail loadPlanDetail = new MailBookingDetail();
					loadPlanDetail.setAwbNumber(loadPlanDetailVO.getAwbNumber());
					loadPlanDetail.setStandardPieces(loadPlanDetailVO.getStandardPieces());
					loadPlanDetail.setStandardWeight(loadPlanDetailVO.getStandardWeight());
					loadPlanDetail.setVolume(loadPlanDetailVO.getVolume());
					loadPlanDetail.setOrigin(loadPlanDetailVO.getOrigin());
					loadPlanDetail.setDestination(loadPlanDetailVO.getDestination());
					loadPlanDetail.setPol(loadPlanDetailVO.getPol());
					loadPlanDetail.setPou(loadPlanDetailVO.getPou());
					loadPlanDetail.setScc(loadPlanDetailVO.getScc());
					loadPlanDetail.setAwbOrgin(loadPlanDetailVO.getOrigin());
					loadPlanDetail.setAwbDestination(loadPlanDetailVO.getDestination());
					loadPlanDetail.setPlannedWeight(loadPlanDetailVO.getPlannedWeight());
					loadPlanDetail.setPlannedPieces(loadPlanDetailVO.getPlannedPieces());
					loadPlanDetail.setPlannedFlight(loadPlanDetailVO.getPlannedFlight());
					loadPlanDetail.setPlannedSegment(loadPlanDetailVO.getPlannedSegment());
					if(loadPlanDetailVO.getFlightDate()!=null){
						loadPlanDetail.setFlightDate(loadPlanDetailVO.getFlightDate().toDisplayDateOnlyFormat());
						loadPlanDetail.setFlightTime(loadPlanDetailVO.getFlightDate().toDisplayTimeOnlyFormat(true));
					}
					loadPlanDetail.setShipmentDescription(loadPlanDetailVO.getShipmentDescription());
					loadPlanDetail.setShipmentStatus(loadPlanDetailVO.getShipmentStatus());
					loadPlanDetail.setBookingFlightCarrierid(Integer.toString(loadPlanDetailVO.getBookingFlightCarrierid()));
					loadPlanDetail.setBookingFlightSequenceNumber(Integer.toString(loadPlanDetailVO.getBookingFlightSequenceNumber()));
					loadPlanDetail.setCompanyCode(loadPlanDetailVO.getCompanyCode());
					loadPlanDetail.setSplitBooking(loadPlanDetailVO.isSplitBooking());
					loadPlanDetail.setBookedFlights(loadPlanDetailVO.getBookedFlights()); 
					loadPlanDetail.setOwnerId(Integer.toString(loadPlanDetailVO.getOwnerId()));
					loadPlanDetail.setDuplicateNumber(Integer.toString(loadPlanDetailVO.getDuplicateNumber()));
					loadPlanDetail.setSequenceNumber(Integer.toString(loadPlanDetailVO.getSequenceNumber()));
					loadPlanDetail.setAttachmentSource(loadPlanDetailVO.getAttachmentSource());
					loadPlanDetailCollection.add(loadPlanDetail);
				}
			}
			return loadPlanDetailCollection;
		}

		public static Collection<MailBookingDetailVO> convertLoadPlanModelCollectionToVos(
				List<MailBookingDetail> loadPlanDetailsCollection) {
			Collection<MailBookingDetailVO> loadPlanDetailVOs=
					new ArrayList<>();
			if(loadPlanDetailsCollection!=null && !loadPlanDetailsCollection.isEmpty()){
				for(MailBookingDetail loadPlanDetail:loadPlanDetailsCollection){
					MailBookingDetailVO loadPlanDetailVO = new MailBookingDetailVO();
					loadPlanDetailVO.setShipmentPrefix(loadPlanDetail.getShipmentPrefix());
					loadPlanDetailVO.setMasterDocumentNumber(loadPlanDetail.getMasterDocumentNumber());
					loadPlanDetailVO.setAwbNumber(loadPlanDetail.getAwbNumber());
					loadPlanDetailVO.setStandardPieces(loadPlanDetail.getStandardPieces());
					loadPlanDetailVO.setStandardWeight(loadPlanDetail.getStandardWeight());
					loadPlanDetailVO.setVolume(loadPlanDetail.getVolume());
					loadPlanDetailVO.setOrigin(loadPlanDetail.getOrigin());
					loadPlanDetailVO.setDestination(loadPlanDetail.getDestination());
					loadPlanDetailVO.setPol(loadPlanDetail.getPol());
					loadPlanDetailVO.setPou(loadPlanDetail.getPou());
					loadPlanDetailVO.setScc(loadPlanDetail.getScc());
					loadPlanDetailVO.setAwbOrgin(loadPlanDetail.getOrigin());
					loadPlanDetailVO.setAwbDestination(loadPlanDetail.getDestination());
					loadPlanDetail.setPlannedPieces(loadPlanDetail.getPlannedPieces());
					loadPlanDetailVO.setPlannedWeight(loadPlanDetail.getPlannedWeight());
					loadPlanDetailVO.setPlannedSegment(loadPlanDetail.getPlannedSegment());
					loadPlanDetailVO.setPlannedFlight(loadPlanDetail.getPlannedFlight());
					loadPlanDetailVO.setFlightDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(loadPlanDetail.getFlightDate()));
					loadPlanDetailVO.setShipmentDescription(loadPlanDetail.getShipmentDescription());
					loadPlanDetailVO.setFlightTime(loadPlanDetail.getFlightTime());
					loadPlanDetailVO.setBookingFlightSequenceNumber(Integer.parseInt(loadPlanDetail.getBookingFlightSequenceNumber()));
					loadPlanDetailVO.setBookingFlightCarrierid(Integer.parseInt(loadPlanDetail.getBookingFlightCarrierid()));
					loadPlanDetailVO.setCompanyCode(loadPlanDetail.getCompanyCode());
					loadPlanDetailVO.setSplitBooking(loadPlanDetail.isSplitBooking());
					loadPlanDetailVO.setBookedFlights(loadPlanDetail.getBookedFlights());
					loadPlanDetailVO.setOwnerId(Integer.parseInt(loadPlanDetail.getOwnerId()));
					loadPlanDetailVO.setDuplicateNumber(Integer.parseInt(loadPlanDetail.getDuplicateNumber()));
					loadPlanDetailVO.setSequenceNumber(Integer.parseInt(loadPlanDetail.getSequenceNumber()));
					loadPlanDetailVO.setAttachmentSource(loadPlanDetail.getAttachmentSource());
					loadPlanDetailVO.setShipmentStatus(loadPlanDetail.getShipmentStatus());
					loadPlanDetailVOs.add(loadPlanDetailVO);
				}
		}
			return loadPlanDetailVOs;
		}
		public static MailBookingFilterVO constructManifestFilterVO(AwbFilter awbFilter, LogonAttributes logonAttributes) {
			MailBookingFilterVO mailBookingFilterVO = new MailBookingFilterVO();
			LocalDate date = null;
			mailBookingFilterVO.setShipmentPrefix(awbFilter.getShipmentPrefix());
			mailBookingFilterVO.setMasterDocumentNumber(awbFilter.getMasterDocumentNumber());
			if(awbFilter.getManifestDateFrom()!=null &&  awbFilter.getManifestDateFrom().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setManifestDateFrom(date.setDate(awbFilter.getManifestDateFrom()));
	    	}
	    	if(awbFilter.getManifestDateTo()!=null&& awbFilter.getManifestDateTo().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setManifestDateTo(date.setDate(awbFilter.getManifestDateTo()));
	    	}
	    	mailBookingFilterVO.setFlightNumber(awbFilter.getFlightNumber());
	    	mailBookingFilterVO.setCarrierCode(awbFilter.getCarrierCode());
			if(awbFilter.getFlightDate()!=null&& awbFilter.getFlightDate().trim().length()>0){
	    		date = new LocalDate(logonAttributes.getAirportCode(),Location.ARP,true);
	    		mailBookingFilterVO.setFlightDate(date.setDate(awbFilter.getFlightDate()));
	    	}
			mailBookingFilterVO.setOrigin(awbFilter.getOrigin());
			mailBookingFilterVO.setDestination(awbFilter.getDestination());
			mailBookingFilterVO.setPol(awbFilter.getPol());
			mailBookingFilterVO.setPou(awbFilter.getPou());
			mailBookingFilterVO.setMailScc(awbFilter.getMailScc());
			return mailBookingFilterVO;
		}
		public static Collection<MailBookingDetail> convertManifestDetailVOsToModel(
				Page<MailBookingDetailVO> manifestDetailVos) {
			Collection<MailBookingDetail> manifestDetailCollection = new ArrayList<>();
			if(manifestDetailVos!=null && !manifestDetailVos.isEmpty()){
				for(MailBookingDetailVO manifestDetailVO : manifestDetailVos){
					MailBookingDetail manifestDetail = new MailBookingDetail();
					manifestDetail.setAwbNumber(manifestDetailVO.getAwbNumber());
					manifestDetail.setStandardPieces(manifestDetailVO.getStandardPieces());
					manifestDetail.setStandardWeight(manifestDetailVO.getStandardWeight());
					manifestDetail.setVolume(manifestDetailVO.getVolume());
					manifestDetail.setOrigin(manifestDetailVO.getOrigin());
					manifestDetail.setDestination(manifestDetailVO.getDestination());
					manifestDetail.setPol(manifestDetailVO.getPol());
					manifestDetail.setPou(manifestDetailVO.getPou());
					manifestDetail.setScc(manifestDetailVO.getScc());
					manifestDetail.setAwbOrgin(manifestDetailVO.getOrigin());
					manifestDetail.setAwbDestination(manifestDetailVO.getDestination());
					manifestDetail.setPlannedWeight(manifestDetailVO.getPlannedWeight());
					manifestDetail.setPlannedPieces(manifestDetailVO.getPlannedPieces());
					manifestDetail.setPlannedFlight(manifestDetailVO.getPlannedFlight());
					manifestDetail.setPlannedSegment(manifestDetailVO.getPlannedSegment());
					if(manifestDetailVO.getFlightDate()!=null){
						manifestDetail.setFlightDate(manifestDetailVO.getFlightDate().toDisplayDateOnlyFormat());
						manifestDetail.setFlightTime(manifestDetailVO.getFlightDate().toDisplayTimeOnlyFormat(true));
					}
					manifestDetail.setShipmentDescription(manifestDetailVO.getShipmentDescription());
					manifestDetail.setBookingFlightCarrierid(Integer.toString(manifestDetailVO.getBookingFlightCarrierid()));
					manifestDetail.setBookingFlightSequenceNumber(Integer.toString(manifestDetailVO.getBookingFlightSequenceNumber()));
					manifestDetail.setCompanyCode(manifestDetailVO.getCompanyCode());
					manifestDetail.setSplitBooking(manifestDetailVO.isSplitBooking());
					manifestDetail.setBookedFlights(manifestDetailVO.getBookedFlights()); 
					manifestDetail.setOwnerId(Integer.toString(manifestDetailVO.getOwnerId()));
					manifestDetail.setDuplicateNumber(Integer.toString(manifestDetailVO.getDuplicateNumber()));
					manifestDetail.setSequenceNumber(Integer.toString(manifestDetailVO.getSequenceNumber()));
					manifestDetail.setAttachmentSource(manifestDetailVO.getAttachmentSource());
					manifestDetailCollection.add(manifestDetail);
				}
			}
			return manifestDetailCollection;
		}
		public static Collection<MailBookingDetailVO> convertManifestModelCollectionToVos(
				List<MailBookingDetail> manifestDetailsCollection) {
			Collection<MailBookingDetailVO> manifestDetailVOs=
					new ArrayList<>();
			if(manifestDetailsCollection!=null && !manifestDetailsCollection.isEmpty()){
				for(MailBookingDetail manifestDetail:manifestDetailsCollection){
					MailBookingDetailVO manifestDetailVO = new MailBookingDetailVO();
					manifestDetailVO.setShipmentPrefix(manifestDetail.getShipmentPrefix());
					manifestDetailVO.setMasterDocumentNumber(manifestDetail.getMasterDocumentNumber());
					manifestDetailVO.setAwbNumber(manifestDetail.getAwbNumber());
					manifestDetailVO.setStandardPieces(manifestDetail.getStandardPieces());
					manifestDetailVO.setStandardWeight(manifestDetail.getStandardWeight());
					manifestDetailVO.setVolume(manifestDetail.getVolume());
					manifestDetailVO.setOrigin(manifestDetail.getOrigin());
					manifestDetailVO.setDestination(manifestDetail.getDestination());
					manifestDetailVO.setPol(manifestDetail.getPol());
					manifestDetailVO.setPou(manifestDetail.getPou());
					manifestDetailVO.setScc(manifestDetail.getScc());
					manifestDetailVO.setAwbOrgin(manifestDetail.getOrigin());
					manifestDetailVO.setAwbDestination(manifestDetail.getDestination());
					manifestDetailVO.setPlannedPieces(manifestDetail.getPlannedPieces());
					manifestDetailVO.setPlannedWeight(manifestDetail.getPlannedWeight());
					manifestDetailVO.setPlannedSegment(manifestDetail.getPlannedSegment());
					manifestDetailVO.setPlannedFlight(manifestDetail.getPlannedFlight());
					manifestDetailVO.setFlightDate(
								new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(manifestDetail.getFlightDate()));
					manifestDetailVO.setShipmentDescription(manifestDetail.getShipmentDescription());
					manifestDetailVO.setFlightTime(manifestDetail.getFlightTime());
					manifestDetailVO.setBookingFlightSequenceNumber(Integer.parseInt(manifestDetail.getBookingFlightSequenceNumber()));
					manifestDetailVO.setBookingFlightCarrierid(Integer.parseInt(manifestDetail.getBookingFlightCarrierid()));
					manifestDetailVO.setCompanyCode(manifestDetail.getCompanyCode());
					manifestDetailVO.setSplitBooking(manifestDetail.isSplitBooking());
					manifestDetailVO.setBookedFlights(manifestDetail.getBookedFlights());
					manifestDetailVO.setOwnerId(Integer.parseInt(manifestDetail.getOwnerId()));
					manifestDetailVO.setDuplicateNumber(Integer.parseInt(manifestDetail.getDuplicateNumber()));
					manifestDetailVO.setSequenceNumber(Integer.parseInt(manifestDetail.getSequenceNumber()));
					manifestDetailVO.setAttachmentSource(manifestDetail.getAttachmentSource());
					manifestDetailVOs.add(manifestDetailVO);
				}
		}
			return manifestDetailVOs;
		}
	}