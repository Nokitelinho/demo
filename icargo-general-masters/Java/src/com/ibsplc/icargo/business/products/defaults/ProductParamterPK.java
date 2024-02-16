/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductParamterPK.java
 *
 *	Created by	:	A-7740
 *	Created on	:	03-Oct-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;
import java.io.Serializable;
import com.ibsplc.xibase.server.framework.audit.Audit;
/**
 *	Java file	: 	com.ibsplc.icargo.business.products.defaults.ProductParamterPK.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7740	:	03-Oct-2018	:	Draft
 */
public class ProductParamterPK implements Serializable{
	private String companyCode;
	private String productCode;
	private String parameterCode;
	public boolean equals(Object other) {
		return (other != null) && ((hashCode() == other.hashCode()));
	}
	public int hashCode() {
		return new StringBuffer(companyCode).append(productCode)
		.append(parameterCode).toString().hashCode();
	}
	public void setCompanyCode(java.lang.String companyCode) {
		this.companyCode=companyCode;
	}
	public java.lang.String getCompanyCode() {
		return this.companyCode;
	}
	public void setProductCode(java.lang.String productCode) {
		this.productCode=productCode;
	}
	public java.lang.String getProductCode() {
		return this.productCode;
	}
	public void setParameterCode(java.lang.String parameterCode) {
		this.parameterCode=parameterCode;
	}
	@Audit(name = "ParameterCode")
	public java.lang.String getParameterCode() {
		return this.parameterCode;
	}
	@Override
	public String toString() {
		StringBuilder sbul = new StringBuilder(90);
		sbul.append("ProductParamterPK [ ");
		sbul.append("companyCode '").append(this.companyCode);
		sbul.append("', parameterCode '").append(this.parameterCode);
		sbul.append("', productCode '").append(this.productCode);
		sbul.append("' ]");
		return sbul.toString();
	}
}