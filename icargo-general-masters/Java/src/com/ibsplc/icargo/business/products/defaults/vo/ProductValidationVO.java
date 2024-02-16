/*
 * ProductValidationVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;

/**
 * @author A-1358
 *
 */
public class ProductValidationVO extends AbstractVO implements Serializable {

    private static final long serialVersionUID = -3779033069519276895L;

	private String productCode;

    private String productName;
    
    private LocalDate startDate;

    private LocalDate endDate;
    
    private String description;
    
    private boolean isBookingMandatory;
    private boolean isFlightMandatory;

    /**
     * @return Returns the productCode.
     */
    public String getProductCode() {
        return productCode;
    }
    /**
     * @param productCode The productCode to set.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    /**
     * @return Returns the productName.
     */
    public String getProductName() {
        return productName;
    }
    /**
     * @param productName The productName to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * @return Returns the endDate.
     */
    public LocalDate getEndDate() {
        return endDate;
    }
    /**
     * @param endDate The endDate to set.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    /**
     * @return Returns the startDate.
     */
    public LocalDate getStartDate() {
        return startDate;
    }
    /**
     * @param startDate The startDate to set.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return Returns the isBookingMandatory.
	 */
	public boolean isBookingMandatory() {
		return isBookingMandatory;
	}
	/**
	 * @param isBookingMandatory The isBookingMandatory to set.
	 */
	public void setBookingMandatory(boolean isBookingMandatory) {
		this.isBookingMandatory = isBookingMandatory;
	}
	/**
	 * @return the isFlightMandatory
	 */
	public boolean isFlightMandatory() {
		return isFlightMandatory;
	}
	/**
	 * @param isFlightMandatory the isFlightMandatory to set
	 */
	public void setFlightMandatory(boolean isFlightMandatory) {
		this.isFlightMandatory = isFlightMandatory;
	}
}
