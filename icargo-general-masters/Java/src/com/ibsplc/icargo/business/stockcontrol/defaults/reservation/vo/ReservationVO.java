/*
 * ReservationVO.java Created on Jan 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1619
 *
 */
public class ReservationVO extends AbstractVO {
    
    private String companyCode;
    private String airportCode;
    private int airlineIdentifier;
    private String airlineCode;
    private String documentNumber;
    private String shipmentPrefix;
    private String customerCode;
    private String documentType;
    private String documentSubType;
    private LocalDate reservationDate;
    private LocalDate expiryDate;
    private String documentStatus;
    private String reservationRemarks;
    private String operationFlag;
    private Collection<String> documentNumbers; 
    private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String stockHolderCode;
	private String agentCode;

    
    /**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
     * @return Returns the airlineCode.
     */
    public String getAirlineCode() {
        return airlineCode;
    }
    /**
     * @param airlineCode The airlineCode to set.
     */
    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode;
    }
    /**
     * @return Returns the airlineIdentifier.
     */
    public int getAirlineIdentifier() {
        return airlineIdentifier;
    }
    /**
     * @param airlineIdentifier The airlineIdentifier to set.
     */
    public void setAirlineIdentifier(int airlineIdentifier) {
        this.airlineIdentifier = airlineIdentifier;
    }
    /**
     * @return Returns the airportCode.
     */
    public String getAirportCode() {
        return airportCode;
    }
    /**
     * @param airportCode The airportCode to set.
     */
    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode;
    }
    /**
     * @return Returns the companyCode.
     */
    public String getCompanyCode() {
        return companyCode;
    }
    /**
     * @param companyCode The companyCode to set.
     */
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }
    /**
     * @return Returns the customerCode.
     */
    public String getCustomerCode() {
        return customerCode;
    }
    /**
     * @param customerCode The customerCode to set.
     */
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    /**
     * @return Returns the documentNumber.
     */
    public String getDocumentNumber() {
        return documentNumber;
    }
    /**
     * @param documentNumber The documentNumber to set.
     */
    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }
    /**
     * @return Returns the documentNumbers.
     */
    public Collection<String> getDocumentNumbers() {
        return documentNumbers;
    }
    /**
     * @param documentNumbers The documentNumbers to set.
     */
    public void setDocumentNumbers(Collection<String> documentNumbers) {
        this.documentNumbers = documentNumbers;
    }
    /**
     * @return Returns the documentStatus.
     */
    public String getDocumentStatus() {
        return documentStatus;
    }
    /**
     * @param documentStatus The documentStatus to set.
     */
    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }
    /**
     * @return Returns the documentType.
     */
    public String getDocumentType() {
        return documentType;
    }
    /**
     * @param documentType The documentType to set.
     */
    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }
    
    /**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
     * @return Returns the expiryDate.
     */
    public LocalDate getExpiryDate() {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * @return Returns the operationFlag.
     */
    public String getOperationFlag() {
        return operationFlag;
    }
    /**
     * @param operationFlag The operationFlag to set.
     */
    public void setOperationFlag(String operationFlag) {
        this.operationFlag = operationFlag;
    }
    /**
     * @return Returns the reservationDate.
     */
    public LocalDate getReservationDate() {
        return reservationDate;
    }
    /**
     * @param reservationDate The reservationDate to set.
     */
    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }
    /**
     * @return Returns the reservationRemarks.
     */
    public String getReservationRemarks() {
        return reservationRemarks;
    }
    /**
     * @param reservationRemarks The reservationRemarks to set.
     */
    public void setReservationRemarks(String reservationRemarks) {
        this.reservationRemarks = reservationRemarks;
    }
    /**
     * @return Returns the shipmentPrefix.
     */
    public String getShipmentPrefix() {
        return shipmentPrefix;
    }
    /**
     * @param shipmentPrefix The shipmentPrefix to set.
     */
    public void setShipmentPrefix(String shipmentPrefix) {
        this.shipmentPrefix = shipmentPrefix;
    }
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

}
