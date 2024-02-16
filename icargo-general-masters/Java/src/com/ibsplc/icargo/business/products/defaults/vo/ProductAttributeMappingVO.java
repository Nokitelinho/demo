/*
 * The Class ProductAttributeMappingVO.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * Author A-6843
 */
public class ProductAttributeMappingVO extends AbstractVO
implements Serializable{
	
	private String companyCode; 
	private String productName; 
	private String attributeName;
	private String eDIChannelFlag;
	private String sOCOChannelFlag;
	private String portalChannelFlag;
	private String upsellingProductCodes;
	private String lastUpdateUser; 
	private LocalDate lastUpdateTime;
	
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
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
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
	public String getUpsellingProductCodes() {
		return upsellingProductCodes;
	}
	public void setUpsellingProductCodes(String upsellingProductCodes) {
		this.upsellingProductCodes = upsellingProductCodes;
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
