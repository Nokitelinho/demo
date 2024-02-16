/**
 * 
 */
package com.ibsplc.icargo.business.customermanagement.defaults.loyalty.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1883
 *
 */
public class AirWayBillLoyaltyProgramVO extends AbstractVO {
	
	/**
	 * Active Status
	 */
	public static final String ACTIVE_STATUS = "A";
	/**
     * module
     */
	public static final String MODULE ="customermanagement"; 
	/**
	 * submodule
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * entity
	 */
	public static final String ENTITY ="customermanagement.defaults.AirWayBillLoyaltyProgram";
	private String companyCode;
	private String customerCode;
	private String masterAwbNumber;
	private int documentOwnerIdentifier;
	private int duplicateNumber;
    private int sequenceNumber;
    private String awbNumber;
    private LocalDate awbDate;
    private Collection<ParameterVO> parameterVOs;
    private Collection<LoyaltyAttributeVO> attributeVOs;
    // 
    private String loyaltyProgrammeCode;
    private String loyaltyAttribute;
    private String loyaltyAttributeUnit;
	private double pointsAccrued;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	/**
	 * @return Returns the attributeVOs.
	 */
	public Collection<LoyaltyAttributeVO> getAttributeVOs() {
		return attributeVOs;
	}
	/**
	 * @param attributeVOs The attributeVOs to set.
	 */
	public void setAttributeVOs(Collection<LoyaltyAttributeVO> attributeVOs) {
		this.attributeVOs = attributeVOs;
	}
	/**
	 * @return Returns the awbNumber.
	 */
	public String getAwbNumber() {
		return awbNumber;
	}
	/**
	 * @param awbNumber The awbNumber to set.
	 */
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
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
	 * @return Returns the customerCode.
	 */
	public String getCustomerCode() {
		return customerCode;
	}
	/**
	 * @param customerCode The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * @return Returns the documentOwnerIdentifier.
	 */
	public int getDocumentOwnerIdentifier() {
		return documentOwnerIdentifier;
	}
	/**
	 * @param documentOwnerIdentifier The documentOwnerIdentifier to set.
	 */
	public void setDocumentOwnerIdentifier(int documentOwnerIdentifier) {
		this.documentOwnerIdentifier = documentOwnerIdentifier;
	}
	/**
	 * @return Returns the duplicateNumber.
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	/**
	 * @param duplicateNumber The duplicateNumber to set.
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the loyaltyProgrammeCode.
	 */
	public String getLoyaltyProgrammeCode() {
		return loyaltyProgrammeCode;
	}
	/**
	 * @param loyaltyProgrammeCode The loyaltyProgrammeCode to set.
	 */
	public void setLoyaltyProgrammeCode(String loyaltyProgrammeCode) {
		this.loyaltyProgrammeCode = loyaltyProgrammeCode;
	}
	/**
	 * @return Returns the masterAwbNumber.
	 */
	public String getMasterAwbNumber() {
		return masterAwbNumber;
	}
	/**
	 * @param masterAwbNumber The masterAwbNumber to set.
	 */
	public void setMasterAwbNumber(String masterAwbNumber) {
		this.masterAwbNumber = masterAwbNumber;
	}
	/**
	 * @return Returns the parameterVOs.
	 */
	public Collection<ParameterVO> getParameterVOs() {
		return parameterVOs;
	}
	/**
	 * @param parameterVOs The parameterVOs to set.
	 */
	public void setParameterVOs(Collection<ParameterVO> parameterVOs) {
		this.parameterVOs = parameterVOs;
	}
	/**
	 * @return Returns the pointsAccrued.
	 */
	public double getPointsAccrued() {
		return pointsAccrued;
	}
	/**
	 * @param pointsAccrued The pointsAccrued to set.
	 */
	public void setPointsAccrued(double pointsAccrued) {
		this.pointsAccrued = pointsAccrued;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the awbDate.
	 */
	public LocalDate getAwbDate() {
		return awbDate;
	}
	/**
	 * @param awbDate The awbDate to set.
	 */
	public void setAwbDate(LocalDate awbDate) {
		this.awbDate = awbDate;
	}
	/**
	 * @return Returns the loyaltyAttribute.
	 */
	public String getLoyaltyAttribute() {
		return loyaltyAttribute;
	}
	/**
	 * @param loyaltyAttribute The loyaltyAttribute to set.
	 */
	public void setLoyaltyAttribute(String loyaltyAttribute) {
		this.loyaltyAttribute = loyaltyAttribute;
	}
	/**
	 * @return Returns the loyaltyAttributeUnit.
	 */
	public String getLoyaltyAttributeUnit() {
		return loyaltyAttributeUnit;
	}
	/**
	 * @param loyaltyAttributeUnit The loyaltyAttributeUnit to set.
	 */
	public void setLoyaltyAttributeUnit(String loyaltyAttributeUnit) {
		this.loyaltyAttributeUnit = loyaltyAttributeUnit;
	}
}
