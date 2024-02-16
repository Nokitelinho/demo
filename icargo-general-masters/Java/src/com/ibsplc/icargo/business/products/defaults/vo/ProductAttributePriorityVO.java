/*
 * The Class ProductAttributePriorityVO.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/** 
 * Author A-6843
 */
public class ProductAttributePriorityVO extends AbstractVO
implements Serializable{
	
	private String companyCode; 
	 private String attributeName; 
	 private int priority;
	 private String lastUpdateUser; 
	 private LocalDate lastUpdateTime;

	 
	 public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
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
