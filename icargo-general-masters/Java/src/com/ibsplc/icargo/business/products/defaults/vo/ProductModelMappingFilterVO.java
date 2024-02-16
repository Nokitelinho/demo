package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class ProductModelMappingFilterVO extends AbstractVO implements Serializable{
	
	private String companyCode;
	private String groupCategoryCode;
	private String commodityCode;
	private boolean productValidityCheckForChannelSoco;
	private String productGroupName;
	private String productPriority;
    private String productCode;
	
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
	public String getGroupCategoryCode() {
		return groupCategoryCode;
	}
	public void setGroupCategoryCode(String groupCategoryCode) {
		this.groupCategoryCode = groupCategoryCode;
	}
	public boolean isProductValidityCheckForChannelSoco() {
		return productValidityCheckForChannelSoco;
	}
	public void setProductValidityCheckForChannelSoco(
			boolean productValidityCheckForChannelSoco) {
		this.productValidityCheckForChannelSoco = productValidityCheckForChannelSoco;
	}
	public void setProductGroupName(String productGroupName) {
		this.productGroupName = productGroupName;
	}
	public String getProductGroupName() {
		return productGroupName;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	public String getProductPriority() {
		return productPriority;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
