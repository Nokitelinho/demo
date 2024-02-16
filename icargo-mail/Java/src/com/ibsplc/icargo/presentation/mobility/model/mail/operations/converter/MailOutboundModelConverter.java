/*
 * MailOutboundModelConverter.java Created on Apr 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.model.mail.operations.converter;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailoutbound.MailOutboundModel;

/**
 * Created by A-9498 on 17-03-2020.
 */
public class MailOutboundModelConverter {

	public static final String RESPONSE_STATUS_SUCCESS = "SUCCESS";
	public static final String RESPONSE_STATUS_NO_DATA = "NO_DATA";
	public static final String RESPONSE_STATUS_ERROR = "ERROR";

	private MailOutboundModelConverter() {
		throw new IllegalStateException("Utility class");
	}

	public static MailUploadVO populateMailUploadVO(MailOutboundModel mailOutboundModel, MailUploadVO mailUploadVO) {
		mailUploadVO.setProcessPoint("ACP");
		mailUploadVO.setMailSource(MailConstantsVO.SCAN+":HHT046");
		mailUploadVO.setAndroidFlag(MailConstantsVO.FLAG_YES);
		mailUploadVO.setDamageCode("");
		mailUploadVO.setCompanyCode(mailOutboundModel.getCompanyCode());
		mailUploadVO.setCarrierCode(mailOutboundModel.getCarrierCode());
		mailUploadVO.setFlightNumber(mailOutboundModel.getFlightNumber());
		mailUploadVO.setContainerNumber(mailOutboundModel.getContainer());
		mailUploadVO.setDestination(mailOutboundModel.getOutboundDestination());
		mailUploadVO.setToPOU(mailOutboundModel.getPou());
		mailUploadVO.setContainerPOU(mailOutboundModel.getPou());
		mailUploadVO.setMailTag(mailOutboundModel.getContainer());
		if (mailOutboundModel.getFlightDate() != null) {
			LocalDate flightDate = new LocalDate("***", Location.NONE, false);
			flightDate.setDate(mailOutboundModel.getFlightDate());
			mailUploadVO.setFlightDate(flightDate);
		}
		mailUploadVO.setContainerType(mailOutboundModel.getContainerType());
		mailUploadVO.setMailKeyforDisplay(mailOutboundModel.getContainer());
		mailUploadVO.setPaCode(mailOutboundModel.getPaBuiltFlag());
		mailUploadVO.setTransferFrmFlightDate(mailOutboundModel.getTruckFlightDate());
		mailUploadVO.setTransferFrmFlightNum(mailOutboundModel.getTruckFlightNum());
		mailUploadVO.setFromCarrierCode(mailOutboundModel.getTruckFlighCarrierCode());
		mailUploadVO.setScannedPort(mailOutboundModel.getAirportCode());
		mailUploadVO.setContainerPol(mailOutboundModel.getAirportCode());
		mailUploadVO.setScreeningUser(mailOutboundModel.getScreeningUser());
		if (mailOutboundModel.getMailBagID() != null
				&& !mailOutboundModel.getMailBagID().isEmpty()) {
			
			mailUploadVO.setDestinationOE(mailOutboundModel.getMailBagID().substring(6, 12));
			mailUploadVO.setOrginOE(mailOutboundModel.getMailBagID().substring(0, 6));
			mailUploadVO.setCategory(mailOutboundModel.getMailBagID().substring(12, 13));
			mailUploadVO.setSubClass(mailOutboundModel.getMailBagID().substring(13, 15));
			mailUploadVO.setMailKeyforDisplay(mailOutboundModel.getMailBagID());
			mailUploadVO.setMailTag(mailOutboundModel.getMailBagID());
		}
		mailUploadVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		return mailUploadVO;
	}

	public static MailUploadVO populateMailUploadVOForContainer(MailOutboundModel mailOutboundModel,
			MailUploadVO mailUploadVO) {
		mailUploadVO.setOperationType(MailConstantsVO.OPERATION_OUTBOUND);
		mailUploadVO.setDamageCode("");
		mailUploadVO.setCompanyCode(mailOutboundModel.getCompanyCode());
		mailUploadVO.setCarrierCode(mailOutboundModel.getCarrierCode());
		mailUploadVO.setFlightNumber(mailOutboundModel.getFlightNumber());
		mailUploadVO.setContainerNumber(mailOutboundModel.getContainer());
		mailUploadVO.setDestination(mailOutboundModel.getOutboundDestination());
		mailUploadVO.setMailTag(mailOutboundModel.getContainer());
		if (mailOutboundModel.getFlightDate() != null && mailOutboundModel.getFlightDate().trim().length() > 0) {
			LocalDate flightDate = new LocalDate("***", Location.NONE, false);
			flightDate.setDate(mailOutboundModel.getFlightDate());
			mailUploadVO.setFlightDate(flightDate);
		}
		mailUploadVO.setContainerType(mailOutboundModel.getContainerType());
		mailUploadVO.setMailKeyforDisplay(mailOutboundModel.getContainer());
		mailUploadVO.setPaCode(mailOutboundModel.getPaBuiltFlag());
		mailUploadVO.setTransferFrmFlightDate(mailOutboundModel.getTruckFlightDate());
		mailUploadVO.setTransferFrmFlightNum(mailOutboundModel.getTruckFlightNum());
		mailUploadVO.setFromCarrierCode(mailOutboundModel.getTruckFlighCarrierCode());
		mailUploadVO.setScannedPort(mailOutboundModel.getAirportCode());
		mailUploadVO.setContainerPol(mailOutboundModel.getAirportCode());
		if (mailOutboundModel.getMailBagID() != null) {
			mailUploadVO.setDestinationOE(mailOutboundModel.getMailBagID().substring(6, 12));
			mailUploadVO.setOrginOE(mailOutboundModel.getMailBagID().substring(0, 6));
			mailUploadVO.setCategory(mailOutboundModel.getMailBagID().substring(12, 13));
			mailUploadVO.setSubClass(mailOutboundModel.getMailBagID().substring(13, 15));
			mailUploadVO.setMailKeyforDisplay(mailOutboundModel.getMailBagID());
			mailUploadVO.setMailTag(mailOutboundModel.getMailBagID());
		}
		mailUploadVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		return mailUploadVO;
	}

}