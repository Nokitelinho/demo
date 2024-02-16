/**
 * 
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class ProductFeedbackVO extends AbstractVO implements Serializable {
	
	private String companyCode;
	
	private String date;
	
	private String productCode;
	
	private String productName;
	
	private long feedbackId ;
	
	private String name;
	
	private String emailId;
	
	private String address;
	
	private String remarks;
	
	private String lastupdatedUser;
	
	private LocalDate lastupdatedTime;
	
	private LocalDate feedbackDate;
	
	/**
	 * @return Returns the address.
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address The address to set.
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return Returns the emailId.
	 */
	public String getEmailId() {
		return emailId;
	}
	/**
	 * @param emailId The emailId to set.
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/**
	 * @return Returns the feedbackId.
	 */
	public long getFeedbackId() {
		return feedbackId;
	}
	/**
	 * @param feedbackId The feedbackId to set.
	 */
	public void setFeedbackId(long feedbackId) {
		this.feedbackId = feedbackId;
	}
	/**
	 * @return Returns the lastupdatedTime.
	 */
	public LocalDate getLastupdatedTime() {
		return lastupdatedTime;
	}
	/**
	 * @param lastupdatedTime The lastupdatedTime to set.
	 */
	public void setLastupdatedTime(LocalDate lastupdatedTime) {
		this.lastupdatedTime = lastupdatedTime;
	}
	/**
	 * @return Returns the lastupdatedUser.
	 */
	public String getLastupdatedUser() {
		return lastupdatedUser;
	}
	/**
	 * @param lastupdatedUser The lastupdatedUser to set.
	 */
	public void setLastupdatedUser(String lastupdatedUser) {
		this.lastupdatedUser = lastupdatedUser;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the productCode.
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode The productCode to set.
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the feedbackDate.
	 */
	public LocalDate getFeedbackDate() {
		return feedbackDate;
	}
	/**
	 * @param feedbackDate The feedbackDate to set.
	 */
	public void setFeedbackDate(LocalDate feedbackDate) {
		this.feedbackDate = feedbackDate;
	}
	/**
	 * @return Returns the date.
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date The date to set.
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
}
