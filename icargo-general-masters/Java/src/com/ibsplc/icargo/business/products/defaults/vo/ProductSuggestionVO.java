/*
 * ProductSuggestionVO.java Created on July 05, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3353
 *
 */
public class ProductSuggestionVO extends AbstractVO{
	public String getSccCode() {
		return sccCode;
	}
	public void setSccCode(String sccCode) {
		this.sccCode = sccCode;
	}
	public static final String MODULE ="product";
	public static final String SUBMODULE ="defaults";
	public static final String ENTITY ="product.defaults.productsuggestion.ProductSuggestion";
	
	private String companyCode;	
	private String productName;
	/**
	 * sccCodes as filter. comma separated scc
	 */
	private String sccCodes;
	/**
	 * sccCode is set Product , SCC map is returned
	 */
	private String sccCode;
	private String serviceCode;
	private String commodityCode;
	private String parameterCode;
	private String source;
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getParameterCode() {
		return parameterCode;
	}
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	private String parameterValue;
	private String productConfigurationString;
	
	
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	public String getProductConfigurationString() {
		return productConfigurationString;
	}
	public void setProductConfigurationString(String productConfigurationString) {
		this.productConfigurationString = productConfigurationString;
	}
	public String getCommodityCode() {
		return commodityCode;
	}
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public String getSccCodes() {
		return sccCodes;
	}
	public void setSccCodes(String sccCodes) {
		this.sccCodes = sccCodes;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	
	
}
