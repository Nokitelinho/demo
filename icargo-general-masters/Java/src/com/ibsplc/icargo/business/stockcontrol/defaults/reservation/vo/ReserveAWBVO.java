/*
 * ReserveAWBVO.java Created on Jan 9, 2006
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
public class ReserveAWBVO extends AbstractVO {

    private String companyCode;
    private String airportCode;
    private String airlineCode;
    private String documentType;
    private String documentSubType;
    private int airlineIdentifier;
    private String customerCode;
    private LocalDate expiryDate;
    private LocalDate reservationDate;
    private String remarks;
    private boolean isGeneralMode;
    private int numberOfDocuments;
    private boolean isSpecificMode;
    private Collection<String> specificDocNumbers;
    private String shipmentPrefix;
    private Collection<String> documentNumbers;
    private int generatedNumOfDocuments;

    private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private boolean hasMinReorderLevel; 



    /**
	 * @return Returns the hasMinReorderLevel.
	 */
	public boolean isHasMinReorderLevel() {
		return this.hasMinReorderLevel;
	}
	/**
	 * @param hasMinReorderLevel The hasMinReorderLevel to set.
	 */
	public void setHasMinReorderLevel(boolean hasMinReorderLevel) {
		this.hasMinReorderLevel = hasMinReorderLevel;
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
     * @return Returns the documentNumbers.
     */
    public Collection<String> getSpecificDocNumbers() {
        return specificDocNumbers;
    }
    /**
     * @param specificDocNumbers
     */
    public void setSpecificDocNumbers(Collection<String> specificDocNumbers) {
        this.specificDocNumbers = specificDocNumbers;
    }


   /**
	 * @return Returns the generatedNumOfDocuments.
	 */
	public int getGeneratedNumOfDocuments() {
		return generatedNumOfDocuments;
	}
	/**
	 * @param generatedNumOfDocuments The generatedNumOfDocuments to set.
	 */
	public void setGeneratedNumOfDocuments(int generatedNumOfDocuments) {
		this.generatedNumOfDocuments = generatedNumOfDocuments;
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
     * @return Returns the isGeneralMode.
     */
    public boolean isGeneralMode() {
        return isGeneralMode;
    }
    /**
     * @param isGeneralMode The isGeneralMode to set.
     */
    public void setGeneralMode(boolean isGeneralMode) {
        this.isGeneralMode = isGeneralMode;
    }
    /**
     * @return Returns the isSpecificMode.
     */
    public boolean isSpecificMode() {
        return isSpecificMode;
    }
    /**
     * @param isSpecificMode The isSpecificMode to set.
     */
    public void setSpecificMode(boolean isSpecificMode) {
        this.isSpecificMode = isSpecificMode;
    }
    /**
     * @return Returns the numberOfDocuments.
     */
    public int getNumberOfDocuments() {
        return numberOfDocuments;
    }
    /**
     * @param numberOfDocuments The numberOfDocuments to set.
     */
    public void setNumberOfDocuments(int numberOfDocuments) {
        this.numberOfDocuments = numberOfDocuments;
    }
    /**
     * @return Returns the remarks.
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
     * @return
     */
    public String toString() {
        return super.toString();
    }

}
