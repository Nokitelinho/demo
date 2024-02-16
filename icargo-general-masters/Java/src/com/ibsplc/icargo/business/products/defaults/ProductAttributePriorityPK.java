package com.ibsplc.icargo.business.products.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

/**
 * The Class ProductAttributePriorityPK.
 * Author A-6843
 */
@Embeddable
public class ProductAttributePriorityPK implements Serializable{
	
	private String companyCode; 
	 private String attributeName; 
	 private int priority;
	/**
	 * Returns the hash code for that product
	 * @return int
	 */
	public int hashCode() {
		return new StringBuilder().append(companyCode)
			.append(attributeName).append(priority).toString().hashCode();
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
	 * Gets the attribute name.
	 *
	 * @return the attribute name
	 */
	public String getAttributeName() {
		return attributeName;
	}
	
	/**
	 * Sets the attribute name.
	 *
	 * @param attributeName the new attribute name
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}
	
	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
	
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(83);
		sbul.append("ProductAttributePriorityPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', attributeName '").append(this.attributeName);
		sbul.append("', priority '").append(this.priority);
		sbul.append("' ]");
		return sbul.toString();
	}
}
