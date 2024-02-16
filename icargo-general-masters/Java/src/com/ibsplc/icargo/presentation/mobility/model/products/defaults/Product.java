/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.products.defaults.Product.java
 *
 *	Created by	:	A-7364
 *	Created on	:	13-Jun-2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.mobility.model.products.defaults;

import java.io.Serializable;

import com.ibsplc.icargo.framework.web.json.BaseType;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.mobility.model.products.defaults.Product.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7364	:	13-Jun-2019	:	Draft
 */
public class Product implements BaseType ,Serializable {
	
	private String productCode;
	private String productName;
	
	/**
	 * 	Getter for productCode 
	 *	Added by : A-7364 on 13-Jun-2019
	 * 	Used for :
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 *  @param productCode the productCode to set
	 * 	Setter for productCode 
	 *	Added by : A-7364 on 13-Jun-2019
	 * 	Used for :
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * 	Getter for productName 
	 *	Added by : A-7364 on 13-Jun-2019
	 * 	Used for :
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 *  @param productName the productName to set
	 * 	Setter for productName 
	 *	Added by : A-7364 on 13-Jun-2019
	 * 	Used for :
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	@Override
	public String getKey() {
		return new StringBuilder("")
				.append(this.getProductName()).toString();
	}

}
