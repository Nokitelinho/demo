
/*
 * ProductSCC.java Created on Dec 10, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * The Class ProductSuggestConfigurationVO.
 */
public class ProductSuggestConfigurationVO extends AbstractVO implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The company code. */
	private String companyCode; 
	
	/** The product Name. */
	private String productName;

	/** The serial number. */
	private long serialNumber;
	
	/** The parameter code. */
	private String parameterCode;
	 
	/** The parameter value. */
	private String parameterValue;
	
	/** The priority. */
	private int priority;
	
	private String productConditionString;
    
    private String sourceIndicator;

	
	/**
	 * Gets the Product code.
	 *
	 * @return the company code
	 */ 
	public String getProductName() {
		return productName;
	}

	/**
	 * Sets the product Name. 
	 *
	 * @param productName the new product Name
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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
	 * Gets the serial number.
	 *
	 * @return the serial number
	 */
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
	 * Gets the parameter code.
	 *
	 * @return the parameter code
	 */
	public String getParameterCode() {
		return parameterCode;
	}

	/**
	 * Sets the parameter code.
	 *
	 * @param paramterCode the new parameter code
	 */
	public void setParameterCode(String paramterCode) {
		this.parameterCode = paramterCode;
	}

	/**
	 * Gets the parameter value.
	 *
	 * @return the parameter value
	 */
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * Sets the parameter value.
	 *
	 * @param paramterValue the new parameter value
	 */
	public void setParameterValue(String paramterValue) {
		this.parameterValue = paramterValue;
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

	/**
	 * 	Getter for productConditionString 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public String getProductConditionString() {
		return productConditionString;
	}

	/**
	 *  @param productConditionString the productConditionString to set
	 * 	Setter for productConditionString 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public void setProductConditionString(String productConditionString) {
		this.productConditionString = productConditionString;
	}

	/**
	 * 	Getter for sourceIndicator 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public String getSourceIndicator() {
		return sourceIndicator;
	}

	/**
	 *  @param sourceIndicator the sourceIndicator to set
	 * 	Setter for sourceIndicator 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public void setSourceIndicator(String sourceIndicator) {
		this.sourceIndicator = sourceIndicator;
	}
}
