package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class ProductModelMappingVO extends AbstractVO implements Serializable {

	private String companyCode;
	private String commodityGroup;
	private String commodityName;
	private String productName;
	private String iscommodityValidForBookingChannelEDI;
	private String iscommodityValidForBookingChannelEbooking;
	private String iscommodityAvailableInSoCo;
	private LocalDate  productStartDate;
	private LocalDate productEndDate;
	private String productPriority;
	private String productGroup;
	private String commodityGroupDescription;
	private String commodityDescription;
	private String productGroupDescription;
	public String getProductGroupDescription() {
		return productGroupDescription;
	}
	public void setProductGroupDescription(String productGroupDescription) {
		this.productGroupDescription = productGroupDescription;
	}
	public String getCommodityGroupDescription() {
		return commodityGroupDescription;
	}
	public void setCommodityGroupDescription(String commodityGroupDescription) {
		this.commodityGroupDescription = commodityGroupDescription;
	}
	public String getCommodityDescription() {
		return commodityDescription;
	}
	public void setCommodityDescription(String commodityDescription) {
		this.commodityDescription = commodityDescription;
	}
	//private String productDescription;
	private String attributeName;
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	private int productDescriptionPriority;
	private String isProductValidForBookingChannelEDI;
	private String isProductValidForBookingChannelEbooking;
	private String isProductAvailableInSoCo;
	private String recProductGroup;
	private String recProductPriority;
	private String possibleBookingType;
	private String consolShipment;
	private String upsellingProductCodes;

	public String getPossibleBookingType() {
		return possibleBookingType;
	}
	public void setPossibleBookingType(String possibleBookingType) {
		this.possibleBookingType = possibleBookingType;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCommodityGroup() {
		return commodityGroup;
	}
	public void setCommodityGroup(String commodityGroup) {
		this.commodityGroup = commodityGroup;
	}
	public String getCommodityName() {
		return commodityName;
	}
	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getIscommodityValidForBookingChannelEDI() {
		return iscommodityValidForBookingChannelEDI;
	}
	public void setIscommodityValidForBookingChannelEDI(
			String iscommodityValidForBookingChannelEDI) {
		this.iscommodityValidForBookingChannelEDI = iscommodityValidForBookingChannelEDI;
	}
	public String getIscommodityValidForBookingChannelEbooking() {
		return iscommodityValidForBookingChannelEbooking;
	}
	public void setIscommodityValidForBookingChannelEbooking(
			String iscommodityValidForBookingChannelEbooking) {
		this.iscommodityValidForBookingChannelEbooking = iscommodityValidForBookingChannelEbooking;
	}
	public String getIscommodityAvailableInSoCo() {
		return iscommodityAvailableInSoCo;
	}
	public void setIscommodityAvailableInSoCo(String iscommodityAvailableInSoCo) {
		this.iscommodityAvailableInSoCo = iscommodityAvailableInSoCo;
	}
	
	public LocalDate getProductStartDate() {
		return productStartDate;
	}
	public void setProductStartDate(LocalDate productStartDate) {
		this.productStartDate = productStartDate;
	}
	public LocalDate getProductEndDate() {
		return productEndDate;
	}
	public void setProductEndDate(LocalDate productEndDate) {
		this.productEndDate = productEndDate;
	}
	public String getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	public int getProductDescriptionPriority() {
		return productDescriptionPriority;
	}
	public void setProductDescriptionPriority(int productDescriptionPriority) {
		this.productDescriptionPriority = productDescriptionPriority;
	}
	public String getIsProductValidForBookingChannelEDI() {
		return isProductValidForBookingChannelEDI;
	}
	public void setIsProductValidForBookingChannelEDI(
			String isProductValidForBookingChannelEDI) {
		this.isProductValidForBookingChannelEDI = isProductValidForBookingChannelEDI;
	}
	public String getIsProductValidForBookingChannelEbooking() {
		return isProductValidForBookingChannelEbooking;
	}
	public void setIsProductValidForBookingChannelEbooking(
			String isProductValidForBookingChannelEbooking) {
		this.isProductValidForBookingChannelEbooking = isProductValidForBookingChannelEbooking;
	}
	public String getIsProductAvailableInSoCo() {
		return isProductAvailableInSoCo;
	}
	public void setIsProductAvailableInSoCo(String isProductAvailableInSoCo) {
		this.isProductAvailableInSoCo = isProductAvailableInSoCo;
	}
	public String getRecProductGroup() {
		return recProductGroup;
	}
	public void setRecProductGroup(String recProductGroup) {
		this.recProductGroup = recProductGroup;
	}
	public String getRecProductPriority() {
		return recProductPriority;
	}
	public void setRecProductPriority(String recProductPriority) {
		this.recProductPriority = recProductPriority;
	}
	public String getConsolShipment() {
		return consolShipment;
	}
	public void setConsolShipment(String consolShipment) {
		this.consolShipment = consolShipment;
	}
	public String getUpsellingProductCodes() {
		return upsellingProductCodes;
	}
	public void setUpsellingProductCodes(String upsellingProductCodes) {
		this.upsellingProductCodes = upsellingProductCodes;
	}
}
