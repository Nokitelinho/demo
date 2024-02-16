/*
 * ProductGroupRecommendationMappingVO class
 */
package com.ibsplc.icargo.business.products.defaults.vo;
import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * The Class ProductGroupRecommendationMappingVO.
 * Author A-6843
 */
public class ProductGroupRecommendationMappingVO extends AbstractVO
implements Serializable{
	
	/** POSSIBLE_BKG_TYPE_BUP. */
	public static final String POSSIBLE_BKG_TYPE_BUP = "B";
	/** POSSIBLE_BKG_TYPE_LOOSE. */
	public static final String POSSIBLE_BKG_TYPE_LOOSE = "L";
	/** POSSIBLE_BKG_TYPE_BUP_LOOSE. */
	public static final String POSSIBLE_BKG_TYPE_BUP_LOOSE = "BL";
	
	/** The Constant POSSIBLE_CONSOL_STATUS_TYPE_CHECKED. */
	public static final String POSSIBLE_CONSOL_STATUS_TYPE_CHECKED= "C";
	
	/** The Constant POSSIBLE_CONSOL_STATUS_TYPE_UNCHECKED. */
	public static final String POSSIBLE_CONSOL_STATUS_TYPE_UNCHECKED= "N";
	
	private String companyCode; 
	 private String commodityCode;
	 private String productGroup;
	 private String productPriority;
	 private String eDIChannelFlag;
	 private String sOCOChannelFlag;
	 private String portalChannelFlag;
	 private String lastUpdateUser; 
	 private LocalDate lastUpdateTime;
	 private String possibleBookingType;
	 private String consolShipment;

	 public String getConsolShipment() {
		return consolShipment;
	}
	public void setConsolShipment(String consolShipment) {
		this.consolShipment = consolShipment;
	}
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
	public String getCommodityCode() {
		return commodityCode;
	}
	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
	public String getProductGroup() {
		return productGroup;
	}
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	public String getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	public String geteDIChannelFlag() {
		return eDIChannelFlag;
	}
	public void seteDIChannelFlag(String eDIChannelFlag) {
		this.eDIChannelFlag = eDIChannelFlag;
	}
	public String getsOCOChannelFlag() {
		return sOCOChannelFlag;
	}
	public void setsOCOChannelFlag(String sOCOChannelFlag) {
		this.sOCOChannelFlag = sOCOChannelFlag;
	}
	public String getPortalChannelFlag() {
		return portalChannelFlag;
	}
	public void setPortalChannelFlag(String portalChannelFlag) {
		this.portalChannelFlag = portalChannelFlag;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	 
}
