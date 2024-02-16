/*
 * MailInboundModel.java Created on Apr 1, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.model.mail.operations.mailinbound;

import java.io.Serializable;

/**
 * Created by A-9529 on 01-04-2020.
 */
public class MailInboundModel implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String carrierCode;
    private String flightNumber;
    private String flightDate;
    private String companyCode;
    private String airportCode;
    private String container;
    private String isPABuild;
    private String containerType;
    private String pol;
    private String mailbagId;
    private String isDelivered;

    //added by A-9529 for IASCB-44567
    private String storageUnit;
    public String getStorageUnit() {
        return storageUnit;
    }
    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }
    public String getCompanyCode() {
        return companyCode;
    }

    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }

    public String getMailbagId() {
        return mailbagId;
    }

    public void setMailbagId(String mailbagId) {
        this.mailbagId = mailbagId;
    }

    public String getCarrierCode() {
        return carrierCode;
    }

    public void setCarrierCode(String carrierCode) {
        this.carrierCode = carrierCode;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public String getIsPABuild() {
        return isPABuild;
    }

    public void setIsPABuild(String isPABuild) {
        this.isPABuild = isPABuild;
    }

    public String getPol() {
        return pol;
    }

    public void setPol(String pol) {
        this.pol = pol;
    }

    public String getContainerType() {
        return containerType;
    }

    public void setContainerType(String containerType) {
        this.containerType = containerType;
    }

    public String getIsDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(String isDelivered) {
        this.isDelivered = isDelivered;
    }
}
