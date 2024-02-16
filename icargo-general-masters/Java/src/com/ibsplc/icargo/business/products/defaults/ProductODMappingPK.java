/*
 * ProductODMappingPK.java Created on Sep 30, 2016
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
 * @author A-4823
 *
 */
@Embeddable
public class ProductODMappingPK implements Serializable{
	private String companyCode; 
	private String productName; 
	private String originCode;
	private String destinationCode;
	/**
	 * Returns the hash code for that product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(productName).append(originCode).append(destinationCode).toString().hashCode();
	}
	/**
	 * Compare two objects and return a boolean value
	 * @param other
	 * @return boolean
	 */
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	public String getCompanyCode() {
			return companyCode;
		}
		
	/**
	 * Sets the company code.
	 *
	 * @param companyCode the new company code
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	
	/**
	 * Gets the product name.
	 *
	 * @return the product name
	 */
	public String getProductName() {
		return productName;
	}
	
	/**
	 * Sets the product name.
	 *
	 * @param productName the new product name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}	
	
	/**
	 * @return the originCode
	 */
	public String getOriginCode() {
		return originCode;
	}
	/**
	 * @param originCode the originCode to set
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}
	/**
	 * @return the destinationCode
	 */
	public String getDestinationCode() {
		return destinationCode;
	}
	/**
	 * @param destinationCode the destinationCode to set
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(83);
		sbul.append("ProductODMappingPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', productName '").append(this.productName);
		sbul.append("', originCode '").append(this.originCode);
		sbul.append("', destinationCode '").append(this.destinationCode);
		sbul.append("' ]");
		return sbul.toString();
	}
		
}
