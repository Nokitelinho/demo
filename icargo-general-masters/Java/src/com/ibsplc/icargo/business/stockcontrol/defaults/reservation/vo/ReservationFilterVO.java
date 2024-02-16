/*
 * ReservationFilterVO.java Created on Jan 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1619
 *
 */
public class ReservationFilterVO extends AbstractVO {
    
    private String companyCode;
    private String airportCode;
    private String airlineCode;
    private int airlineIdentifier;
    private String customerCode;
    private String documentType;
    private LocalDate reservationFromDate;
    private LocalDate reservationToDate;
    private LocalDate expiryFromDate;
    private LocalDate expiryToDate;
    private LocalDate currentDate;
    private int pageNumber;

    
    /**
	 * @return Returns the pageNumber.
	 */
	public int getPageNumber() {
		return this.pageNumber;
	}
	/**
	 * @param pageNumber The pageNumber to set.
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
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
     * @return Returns the expiryFromDate.
     */
    public LocalDate getExpiryFromDate() {
        return expiryFromDate;
    }
    /**
     * @param expiryFromDate The expiryFromDate to set.
     */
    public void setExpiryFromDate(LocalDate expiryFromDate) {
        this.expiryFromDate = expiryFromDate;
    }
    /**
     * @return Returns the expiryToDate.
     */
    public LocalDate getExpiryToDate() {
        return expiryToDate;
    }
    /**
     * @param expiryToDate The expiryToDate to set.
     */
    public void setExpiryToDate(LocalDate expiryToDate) {
        this.expiryToDate = expiryToDate;
    }
    /**
     * @return Returns the reservationFromDate.
     */
    public LocalDate getReservationFromDate() {
        return reservationFromDate;
    }
    /**
     * @param reservationFromDate The reservationFromDate to set.
     */
    public void setReservationFromDate(LocalDate reservationFromDate) {
        this.reservationFromDate = reservationFromDate;
    }
    /**
     * @return Returns the reservationToDate.
     */
    public LocalDate getReservationToDate() {
        return reservationToDate;
    }
    /**
     * @param reservationToDate The reservationToDate to set.
     */
    public void setReservationToDate(LocalDate reservationToDate) {
        this.reservationToDate = reservationToDate;
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
	 * @return Returns the currentDate.
	 */
	public LocalDate getCurrentDate() {
		return currentDate;
	}
	/**
	 * @param currentDate The currentDate to set.
	 */
	public void setCurrentDate(LocalDate currentDate) {
		this.currentDate = currentDate;
	}
}
