/*
 * ProductCommodityGroupMappingPK.java Created on Jul 07, 2016
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
 * @author A-5642
 *
 */
@Embeddable
public class ProductCommodityGroupMappingPK implements Serializable{
    /**
     * Company Code
     */
    private String companyCode;
    /**
     * productName
     */
    private String productName;
    /**
     * commodityGroup
     */
    private String commodityGroup;
	/**
	 * Returns the hash code for that product
	 * @return int
	 */
	public int hashCode() {
			return new StringBuilder().append(companyCode)
				.append(productName).append(commodityGroup).toString().hashCode();
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
	 * @return Returns the commodityGroup.
	 */
	public String getCommodityGroup() {
		return commodityGroup;
	}
	/**
	 * @param commodityGroup The commodityGroup to set.
	 */
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	/**
	 * generated by xibase.tostring plugin at 1 October, 2014 1:14:11 PM IST
	 */
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(83);
		sbul.append("ProductCommodityGroupMappingPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', productName '").append(this.productName);
		sbul.append("', commodityGroup '").append(this.commodityGroup);
		sbul.append("' ]");
		return sbul.toString();
	}
}
