/*
 * MailInboundModelConverter.java Created on Apr 1, 2005
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
import com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailinbound.MailInboundModel;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * Created by A-9529 on 01-04-2020.
 */
public class MailInboundModelConverter {

    private static Log log = LogFactory.getLogger("MAIL_INBOUND_MODEL_CONVERTER");
    private static final String CLASS_NAME = MailInboundModelConverter.class.getName();

    private MailInboundModelConverter() {
        throw new IllegalStateException("Utility class");
    }

    public static MailUploadVO populateMailUploadVO(MailInboundModel mailInboundModel,
                                                    MailUploadVO mailUploadVO) {
        log.entering(CLASS_NAME, "populateMailUploadVO");
        mailUploadVO.setScanType(MailConstantsVO.MAIL_STATUS_ARRIVED);
        mailUploadVO.setMailSource(MailConstantsVO.MAIL_SOURCE_HHT_MAILINBOUND);
        mailUploadVO.setCompanyCode(mailInboundModel.getCompanyCode());
        mailUploadVO.setCarrierCode(mailInboundModel.getCarrierCode());
        if(mailInboundModel.getFlightNumber() != null && !mailInboundModel.getFlightDate().isEmpty()) {
        mailUploadVO.setFlightNumber(mailInboundModel.getFlightNumber());
        LocalDate flightDate = new LocalDate(LocalDate.NO_STATION,
                Location.NONE, false);
        flightDate.setDate(mailInboundModel.getFlightDate());
        mailUploadVO.setFlightDate(flightDate);
        }
        mailUploadVO.setDestination(mailInboundModel.getAirportCode());
        mailUploadVO.setToDestination(mailInboundModel.getAirportCode());
        mailUploadVO.setContainerPol(mailInboundModel.getPol());
        mailUploadVO.setContainerPOU(mailInboundModel.getAirportCode());
        mailUploadVO.setToPOU(mailInboundModel.getAirportCode());
        mailUploadVO.setAndroidFlag(MailConstantsVO.FLAG_YES);
        mailUploadVO.setDeliverd(mailInboundModel.getIsDelivered().equals("Y"));
        mailUploadVO.setPaCode(mailInboundModel.getIsPABuild());

        //added by A-9529 for IASCB-44567
        mailUploadVO.setStorageUnit(mailInboundModel.getStorageUnit());
        if(mailInboundModel.getContainerType().equals("B") ){
            mailUploadVO.setContainerNumber(mailInboundModel.getContainer());
            mailUploadVO.setContainerType("B");
            mailUploadVO.setMailTag(mailInboundModel.getContainer());
            mailUploadVO.setMailKeyforDisplay(mailInboundModel.getMailbagId());
        }else{
            mailUploadVO.setContainerNumber(mailInboundModel.getContainer());
            mailUploadVO.setContainerType("U");
            mailUploadVO.setMailTag(mailInboundModel.getContainer());
            mailUploadVO.setMailKeyforDisplay(mailInboundModel.getMailbagId());
        }

        if (mailInboundModel.getMailbagId() != null ) {
            mailUploadVO.setOrginOE(mailInboundModel.getMailbagId().substring(0, 6));
            mailUploadVO.setDestinationOE(mailInboundModel.getMailbagId().substring(6, 12));
            mailUploadVO.setCategory(mailInboundModel.getMailbagId().substring(12, 13));
            mailUploadVO.setSubClass(mailInboundModel.getMailbagId().substring(13, 15));
            mailUploadVO.setMailKeyforDisplay(mailInboundModel.getMailbagId());
            mailUploadVO.setMailTag(mailInboundModel.getMailbagId());
        }
        log.exiting(CLASS_NAME, "populateMailUploadVO");
        return mailUploadVO;
    }

    public static MailUploadVO populateContainerMailUploadVO(MailInboundModel mailInboundModel,
                                                             MailUploadVO mailUploadVO) {

        log.entering(CLASS_NAME, "populateContainerMailUploadVO");
        mailUploadVO.setCompanyCode(mailInboundModel.getCompanyCode());
        mailUploadVO.setContainerNumber(mailInboundModel.getContainer());
        mailUploadVO.setScannedPort(mailInboundModel.getAirportCode());
        mailUploadVO.setOperationType(MailConstantsVO.OPERATION_INBOUND);
        mailUploadVO.setContainerPol(mailInboundModel.getPol());
        mailUploadVO.setPols(mailInboundModel.getPol());
        mailUploadVO.setCarrierCode(mailInboundModel.getCarrierCode());
        mailUploadVO.setFlightNumber(mailInboundModel.getFlightNumber());
        mailUploadVO.setDestination(mailInboundModel.getAirportCode());
        if (mailInboundModel.getFlightDate() != null && mailInboundModel.getFlightDate().trim().length() > 0) {
            LocalDate flightDate = new LocalDate("***", Location.NONE, false);
            flightDate.setDate(mailInboundModel.getFlightDate());
            mailUploadVO.setFlightDate(flightDate);
        }
        mailUploadVO.setContainerType(mailInboundModel.getContainerType());
        mailUploadVO.setScannedDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
        log.exiting(CLASS_NAME, "populateContainerMailUploadVO");
        return mailUploadVO;


    }

}
