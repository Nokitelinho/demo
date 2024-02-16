
/*
 * ProductSCC.java Created on Dec 10, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.io.Serializable;

import javax.persistence.Embeddable;

import com.ibsplc.xibase.server.framework.persistence.keygen.Key;
import com.ibsplc.xibase.server.framework.persistence.keygen.KeyCondition;
import com.ibsplc.xibase.server.framework.persistence.keygen.SequenceKeyGenerator;

/**
 * The Class ProductSuggestConfigurationPK.
 */
@SequenceKeyGenerator(name="ID_GEN", sequence="PRDSUGCFGMST_SEQ")
@Embeddable                                    
public class ProductSuggestConfigurationPK implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The company code. */
	private String companyCode; 
	
	/** The product code. */
	private String productName; 
	
	
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;  
	}

	/** The serial number. */
	private long serialNumber;
	
	/**
	 * Gets the company code.
	 *
	 * @return the company code
	 */
	@KeyCondition(column="CMPCOD") 
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
     * Gets the serial number.
     *
     * @return the serial number
     */
    @Key(generator="ID_GEN", startAt="1")
	public long getSerialNumber() {
		return serialNumber;
	}
	
	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the new serial number
	 */
	public void setSerialNumber(long serialNumber) {
		this.serialNumber = serialNumber;
	}
	/**
	 * @param other
	 * @param boolean
	 */
	 public boolean equals(Object other) {
	        return (other != null)&& (other instanceof ProductSuggestConfigurationPK) && (hashCode() == other.hashCode());
	    }
	 /**
	  * @return int
	  */
	 public int hashCode() {
	        return new StringBuffer(companyCode).append(productName).toString().hashCode();
	    }
	 
 	/* (non-Javadoc)
 	 * @see java.lang.Object#toString()
 	 */
 	public String toString()
	  {
	    StringBuilder sbul = new StringBuilder();
	    sbul.append("ProductSuggestConfigurationPK [ ");
	    sbul.append("companyCode '").append(this.companyCode);
	    sbul.append("', productName '").append(this.productName);
	    sbul.append("' ]");
	    return sbul.toString();
	  }
}
