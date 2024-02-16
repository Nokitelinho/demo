package com.ibsplc.icargo.business.products.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * @author A-6843
 *
 */
@Embeddable
public class ProductGroupRecommendationMappingPK implements Serializable{
	
	private String companyCode; 
	private String commodityCode; 
	/**
	 * Returns the hash code for that product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(commodityCode).toString().hashCode();
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
	 * Gets the commodity code.
	 *
	 * @return the commodity code
	 */
	public String getCommodityCode() {
		return commodityCode;
	}
	
	/**
	 * Sets the commodity code.
	 *
	 * @param commodityCode the new commodity code
	 */
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(83);
		sbul.append("ProductGroupRecommendationMappingPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', commodityCode '").append(this.commodityCode);
		sbul.append("' ]");
		return sbul.toString();
	}

}
