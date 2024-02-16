package com.ibsplc.icargo.business.products.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The Class ProductAttributeMappingPK.
 */
@Embeddable
public class ProductAttributeMappingPK implements Serializable{
	private String companyCode; 
	private String productName; 
	/**
	 * Returns the hash code for that product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(productName).toString().hashCode();
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
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(83);
		sbul.append("ProductAttributeMappingPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', productName '").append(this.productName);
		sbul.append("' ]");
		return sbul.toString();
	}
		
}
