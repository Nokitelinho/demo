/*
 * SubProductRestrictionCommodityPK.java Created on Jun 27, 2005
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
public class SubProductRestrictionCommodityPK implements Serializable{
	 /**
	  *Company Code 
	  */
	 private String companyCode;
	 /**
	  * Product Code
	  */
	 private String productCode;
	 /**
	  * SubProduct Code
	  */
	 private String subProductCode;
	 /**
	  * Version Number
	  */
	 private int versionNumber;
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
	 * @return Returns the subProductCode.
	 */
	public String getSubProductCode() {
		return subProductCode;
	}
	/**
	 * @param subProductCode The subProductCode to set.
	 */
	public void setSubProductCode(String subProductCode) {
		this.subProductCode = subProductCode;
	}
	/**
	 * @return Returns the versionNumber.
	 */
	public int getVersionNumber() {
		return versionNumber;
	}
	/**
	 * @param versionNumber The versionNumber to set.
	 */
	public void setVersionNumber(int versionNumber) {
		this.versionNumber = versionNumber;
	}
	/**
     * Constructor for SubProduct Restriction Commodity
     *
     */
	 public SubProductRestrictionCommodityPK(){
    	
	 }
    /**
     * 
     * @param companyCode
     * @param productCode
     * @param subProductCode
     * @param versionNumber
     * @param commodityCode
     */	
	 public SubProductRestrictionCommodityPK(String companyCode,String productCode,String subProductCode,
			int versionNumber,String commodityCode){
		this.companyCode = companyCode;
		this.productCode = productCode;
		this.subProductCode = subProductCode;
		this.versionNumber = versionNumber;
		this.commodityCode = commodityCode;
	 }
	/**
	 * @return int
	 */
	 public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(productCode).append(subProductCode).append(versionNumber).append(commodityCode).toString().hashCode();
	 }
    /**
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
		StringBuilder sbul = new StringBuilder(154);
		sbul.append("SubProductRestrictionCommodityPK [ ");
		sbul.append("commodityCode '").append(this.commodityCode);
		sbul.append("', companyCode '").append(this.companyCode);
		sbul.append("', productCode '").append(this.productCode);
		sbul.append("', subProductCode '").append(this.subProductCode);
		sbul.append("', versionNumber '").append(this.versionNumber);
		sbul.append("' ]");
		return sbul.toString();
	}

}