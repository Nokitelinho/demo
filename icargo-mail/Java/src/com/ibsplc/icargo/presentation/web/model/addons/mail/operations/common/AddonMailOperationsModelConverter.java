package com.ibsplc.icargo.presentation.web.model.addons.mail.operations.common;

import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.CarditFilter;

public class AddonMailOperationsModelConverter {

	public CarditEnquiryFilterVO constructCarditFilterVO(CarditFilter carditFilter, LogonAttributes logonAttributes) {

		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();

		carditEnquiryFilterVO.setDespatchSerialNumber(carditFilter.getDespatchSerialNumber());
		carditEnquiryFilterVO.setOoe(carditFilter.getOoe());
		carditEnquiryFilterVO.setDoe(carditFilter.getDoe());
		carditEnquiryFilterVO.setMailCategoryCode(carditFilter.getMailCategoryCode());
		carditEnquiryFilterVO.setMailSubclass(carditFilter.getMailSubclass());
		carditEnquiryFilterVO.setYear(carditFilter.getYear());
		carditEnquiryFilterVO.setConsignmentDocument(carditFilter.getConDocNo());
		if (carditFilter.getConsignmentDate() != null && carditFilter.getConsignmentDate().trim().length() > 0) {
			carditEnquiryFilterVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false)
					.setDate(carditFilter.getConsignmentDate()));
		}
		if (carditFilter.getRdtDate() != null && carditFilter.getRdtDate().trim().length() > 0
				&& carditFilter.getRdtTime() != null && carditFilter.getRdtTime().trim().length() > 0) {
			LocalDate rdt = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
			rdt.setDate(carditFilter.getRdtDate());
			rdt.setTime(carditFilter.getRdtTime(), true);
			carditEnquiryFilterVO.setReqDeliveryTime(rdt);
		}
		carditEnquiryFilterVO.setCarrierCode(carditFilter.getCarrierCode());
		carditEnquiryFilterVO.setFlightNumber(carditFilter.getFlightNumber());
		if (carditFilter.getFlightDate() != null && carditFilter.getFlightDate().trim().length() > 0) {
			carditEnquiryFilterVO.setFlightDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(carditFilter.getFlightDate()));
		}
		carditEnquiryFilterVO.setPol(carditFilter.getAirportCode());
		carditEnquiryFilterVO.setUldNumber(carditFilter.getUldNumber());
		if (carditFilter.getFromDate() != null && carditFilter.getFromDate().trim().length() > 0) {
			carditEnquiryFilterVO.setFromDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(carditFilter.getFromDate()));
		}
		if (carditFilter.getToDate() != null && carditFilter.getToDate().trim().length() > 0) {
			carditEnquiryFilterVO.setToDate(
					new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(carditFilter.getToDate()));
		}
		carditEnquiryFilterVO.setMailStatus(carditFilter.getMailStatus());
		carditEnquiryFilterVO.setPaoCode(carditFilter.getPaCode());
		if (carditFilter.getAwbAttached() != null && carditFilter.getAwbAttached().trim().length() > 0) {
			if (MailConstantsVO.FLAG_YES.equals(carditFilter.getAwbAttached())) {
				carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_YES);
			} else {
				carditEnquiryFilterVO.setIsAWBAttached(MailConstantsVO.FLAG_NO);
			}
		}
		carditEnquiryFilterVO.setFlightType(carditFilter.getFlightType());
		carditEnquiryFilterVO.setShipmentPrefix(carditFilter.getShipmentPrefix());
		carditEnquiryFilterVO.setDocumentNumber(carditFilter.getMasterDocumentNumber());
		carditEnquiryFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		if (carditFilter.getMailCount() != null && !carditFilter.getMailCount().isEmpty()) {
			carditEnquiryFilterVO.setMailCount(Integer.parseInt(carditFilter.getMailCount()));
		}

		return carditEnquiryFilterVO;
	}
}
