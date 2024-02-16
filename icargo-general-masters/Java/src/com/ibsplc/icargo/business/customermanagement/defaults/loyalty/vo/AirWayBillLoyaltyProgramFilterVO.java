/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2048
 *
 */
public class AirWayBillLoyaltyProgramFilterVO extends AbstractVO {
	private String companyCode;
	private String customerCode;
	private String masterAwbNumber;
	private String awbNumber;
	private String loyaltyProgramee;
	private int ownerId;
	private int duplicateNumber;
	private int sequenceNumber;
	
	/**
	 * @return int Returns the duplicateNumber.
	 */
	public int getDuplicateNumber() {
		return this.duplicateNumber;
	}
	/**
	 * @param duplicateNumber The duplicateNumber to set.
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * @return int Returns the ownerId.
	 */
	public int getOwnerId() {
		return this.ownerId;
	}
	/**
	 * @param ownerId The ownerId to set.
	 */
	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}
	/**
	 * @return int Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return String Returns the awbNumber.
	 */
	public String getAwbNumber() {
		return this.awbNumber;
	}
	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	/**
	 * @return String Returns the companyCode.
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return String Returns the customerCode.
	 */
	public String getCustomerCode() {
		return this.customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return String Returns the loyaltyProgramee.
	 */
	public String getLoyaltyProgramee() {
		return this.loyaltyProgramee;
	}
	/**
	 * @param loyaltyProgramee The loyaltyProgramee to set.
	 */
	public void setLoyaltyProgramee(String loyaltyProgramee) {
		this.loyaltyProgramee = loyaltyProgramee;
	}
	/**
	 * @return String Returns the masterAwbNumber.
	 */
	public String getMasterAwbNumber() {
		return this.masterAwbNumber;
	}
	/**
	 * @param masterAwbNumber The masterAwbNumber to set.
	 */
	public void setMasterAwbNumber(String masterAwbNumber) {
		this.masterAwbNumber = masterAwbNumber;
	}
	
	
	
}
