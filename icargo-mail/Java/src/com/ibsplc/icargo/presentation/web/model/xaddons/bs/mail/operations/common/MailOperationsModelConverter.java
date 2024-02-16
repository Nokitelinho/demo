package com.ibsplc.icargo.presentation.web.model.xaddons.bs.mail.operations.common;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.xaddons.bs.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;
import com.ibsplc.icargo.presentation.web.model.shared.defaults.common.OneTime;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



public class MailOperationsModelConverter {
	
	
	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.constructMailBookingFilterVO
	 *	Added by 	:	A-8164 on 08-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param awbFilter
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	MailBookingFilterVO
	 */
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
	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.convertMailBookingDetailVosToModel
	 *	Added by 	:	A-8164 on 08-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailVOs
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingDetail>
	 */
	public static Collection<MailBookingDetail> convertMailBookingDetailVosToModel(
			Page<MailBookingDetailVO> mailBookingDetailVOs){
		Collection<MailBookingDetail> mailBookingDetailsCollection =
				new ArrayList<>();
		if(mailBookingDetailVOs!=null && 
				mailBookingDetailVOs.size()>0){
			for(MailBookingDetailVO mailBookingDetailVO:mailBookingDetailVOs){
				MailBookingDetail mailBookingDetail= new MailBookingDetail();
				mailBookingDetail.setShipmentPrefix(mailBookingDetailVO.getShipmentPrefix());
				mailBookingDetail.setMasterDocumentNumber(mailBookingDetailVO.getMasterDocumentNumber());
				mailBookingDetail.setSelectedFlightNumber(mailBookingDetailVO.getSelectedFlightNumber());
				if(mailBookingDetailVO.getSelectedFlightNumber()!=null && !("-1".equals(mailBookingDetailVO.getSelectedFlightNumber()))
						&& mailBookingDetailVO.getSelectedFlightNumber().length()>0 ){
					mailBookingDetail.setFlightDetails(mailBookingDetailVO.getSelectedFlightNumber().split(","));
				}
				mailBookingDetail.setAgentCode(mailBookingDetailVO.getAgentCode());
				mailBookingDetail.setMailScc(mailBookingDetailVO.getMailScc());
				if(mailBookingDetailVO.getShipmentDate()!=null){
					mailBookingDetail.setShipmentDate(mailBookingDetailVO.getShipmentDate().toDisplayDateOnlyFormat());
				}
				mailBookingDetail.setAwbOrgin(mailBookingDetailVO.getAwbOrgin());
				mailBookingDetail.setAwbDestination(mailBookingDetailVO.getAwbDestination());
				mailBookingDetail.setBookedPieces(Integer.toString(mailBookingDetailVO.getBookedPieces()));
				mailBookingDetail.setBookedWeight(Double.toString(mailBookingDetailVO.getBookedWeight()));
				mailBookingDetail.setBookedVolume(Double.toString(mailBookingDetailVO.getBookedVolume()));
				mailBookingDetail.setBookingStatus(mailBookingDetailVO.getBookingStatus());
				mailBookingDetail.setShipmentStatus(mailBookingDetailVO.getShipmentStatus());
				mailBookingDetail.setBookingStation(mailBookingDetailVO.getBookingStation());
				if(mailBookingDetailVO.getBookingDate()!=null){
					mailBookingDetail.setBookingDate(mailBookingDetailVO.getBookingDate().toDisplayDateOnlyFormat());
				}
				mailBookingDetail.setRemarks(mailBookingDetailVO.getRemarks());
				mailBookingDetail.setOwnerId(Integer.toString(mailBookingDetailVO.getOwnerId()));
				mailBookingDetail.setDuplicateNumber(Integer.toString(mailBookingDetailVO.getDuplicateNumber()));
				mailBookingDetail.setSequenceNumber(Integer.toString(mailBookingDetailVO.getSequenceNumber()));
				mailBookingDetail.setBookingCarrierCode(mailBookingDetailVO.getBookingCarrierCode());
				mailBookingDetail.setBookingFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
				mailBookingDetail.setBookingFlightSequenceNumber(Integer.toString(mailBookingDetailVO.getBookingFlightSequenceNumber()));
				if(mailBookingDetailVO.getBookingFlightDate()!=null){
					mailBookingDetail.setBookingFlightDate(mailBookingDetailVO.getBookingFlightDate().toDisplayDateOnlyFormat());
				}
				mailBookingDetail.setStatedPieces(Integer.toString(mailBookingDetailVO.getStatedPieces()));
				mailBookingDetail.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				mailBookingDetail.setSegementserialNumber(Integer.toString(mailBookingDetailVO.getSegementserialNumber()));
				mailBookingDetail.setSerialNumber(Integer.toString(mailBookingDetailVO.getSerialNumber()));
				mailBookingDetail.setMailSequenceNumber(Long.toString(mailBookingDetailVO.getMailSequenceNumber()));
				mailBookingDetail.setOrigin(mailBookingDetailVO.getOrigin());
				mailBookingDetail.setDestination(mailBookingDetailVO.getDestination());
				mailBookingDetail.setBookingFlightCarrierid(Integer.toString(mailBookingDetailVO.getBookingFlightCarrierid()));
				if(mailBookingDetailVO.getSelectedFlightDate()!=null){
					mailBookingDetail.setSelectedFlightDate(mailBookingDetailVO.getSelectedFlightDate().toDisplayDateOnlyFormat());
				}
				mailBookingDetail.setSplitBooking(mailBookingDetailVO.isSplitBooking());
				mailBookingDetail.setDestinationCheckReq(mailBookingDetailVO.isDestinationCheckReq());
				mailBookingDetail.setSegementserialNumber(Integer.toString(mailBookingDetailVO.getSegementserialNumber()));
				mailBookingDetail.setBookedFlights(mailBookingDetailVO.getBookedFlights()); 
				mailBookingDetailsCollection.add(mailBookingDetail);
			}
		}
		return mailBookingDetailsCollection;
	}
	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.constructOneTimeValues
	 *	Added by 	:	A-8164 on 08-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param oneTimeValues
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,Collection<OneTime>>
	 */
	public static Map<String,Collection<OneTime>> constructOneTimeValues(Map<String,Collection<OneTimeVO>> oneTimeValues){
		HashMap<String,Collection<OneTime>> oneTimeValuesMap = new HashMap<String,Collection<OneTime>>();
		for(Map.Entry<String,Collection<OneTimeVO>> oneTimeValue : oneTimeValues.entrySet()){
			ArrayList<OneTime> oneTimes= new ArrayList<OneTime>();
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
	/**
	 * 
	 * 	Method		:	MailOperationsModelConverter.convertMailBookingModelCollectionToVos
	 *	Added by 	:	A-8164 on 09-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailBookingDetailCollection
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<MailBookingDetailVO>
	 */
	public static Collection<MailBookingDetailVO> convertMailBookingModelCollectionToVos(
			Collection<MailBookingDetail> mailBookingDetailCollection){
		Collection<MailBookingDetailVO> mailBookingDetailVOs=
				new ArrayList<>();
		if(mailBookingDetailCollection!=null && mailBookingDetailCollection.size()>0){
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
				if(mailBookingDetailVO.getSelectedFlightDate()!=null){
					mailBookingDetailVO.setSelectedFlightDate(
							new LocalDate(LocalDate.NO_STATION,Location.NONE, false).setDate(mailBookingDetail.getSelectedFlightDate()));
				}
				mailBookingDetailVO.setSplitBooking(mailBookingDetail.isSplitBooking());
				mailBookingDetailVO.setDestinationCheckReq(mailBookingDetail.isDestinationCheckReq());
				mailBookingDetailVO.setSegementserialNumber(Integer.parseInt(mailBookingDetail.getSegementserialNumber()));
				mailBookingDetailVO.setBookedFlights(mailBookingDetail.getBookedFlights());
				mailBookingDetailVOs.add(mailBookingDetailVO);
			}
		}
		return mailBookingDetailVOs;
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
}