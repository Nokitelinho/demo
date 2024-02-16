/*
 * ProductRestrictionCommodityPK.java Created on Jun 27, 2005
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
public class ProductRestrictionCommodityPK implements Serializable{
    /**
     * Company Code
     */
    private String companyCode;
    /**
     * Product Code
     */	
    private String productCode;
    /**
     * Commodity Code
     */
    private String commodityCode;
    
	/**
	 * @return Returns the commodityCode.
	 */
	public String getCommodityCode() {
		return commodityCode;
	}
	/**
	 * @param commodityCode The commodityCode to set.
	 */
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
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
	 * returns the hash code corresponding to a product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(productCode).append(commodityCode).toString().hashCode();
	}
	/**
	 * Compare two product and return boolean value
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
		StringBuilder sbul = new StringBuilder(100);
		sbul.append("ProductRestrictionCommodityPK [ ");
		sbul.append("commodityCode '").append(this.commodityCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', productCode '").append(this.productCode);
		sbul.append("' ]");
		return sbul.toString();
	}

}