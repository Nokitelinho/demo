/*
 * ProductRestrictionSegmentPK.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-1358
 *
 */
@Embeddable
public class ProductRestrictionSegmentPK implements Serializable{
	/**
	 * Company Code
	 */
    private String companyCode;
    /**
     * Product Code
     */
    private String productCode;
    /**
     * Origin station
     */
    private String origin;
    /**
     * Destination station
     */
    private String destination;
    
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
	 * @return Returns the destination.
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
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
	 * Return the hash code corresponding to a product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(productCode).append(origin).append(destination).toString().hashCode();
	}
	/**
	 * Compare two product
	 * @param other
	 * @return boolean
	 */
    public boolean equals(Object other) {
	 return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:11 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(114);
		sbul.append("ProductRestrictionSegmentPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', destination '").append(this.destination);
		sbul.append("', origin '").append(this.origin);
		sbul.append("', productCode '").append(this.productCode);
		sbul.append("' ]");
		return sbul.toString();
	}

}