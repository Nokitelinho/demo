/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author a-5991
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * JUN 30, 2016 A-5991 First Draft
 */
public class MailCarrierVO extends AbstractVO{
/**
 * Airport Code
 */
private String airportCode;
/**
 * Level Type
 */
private String levelType;
/**
 * Level Value
 */
private String levelValue;
/**
 * Carrier Status
 */
private String carrierStatus;
/**
 * Default Carrier Status
 */
private String defaultCarrier;

/**
 * Default Carrier Code
 */
private String carrierCode;
/**
 * Default Company Code
 */
private String companyCode;
/**
 * @return the operationFlag
 */
private String operationFlag;
/**
 * @return the code
 */
private String code;


private String lastUpdateUser;


private int sequenceNumber;

private int defCarrierIdentifier;

private LocalDate lastUpdateTime;


/**
 * @return the lastUpdateTime
 */
public LocalDate getLastUpdateTime() {
	return lastUpdateTime;
}
/**
 * @param lastUpdateTime the lastUpdateTime to set
 */
public void setLastUpdateTime(LocalDate lastUpdateTime) {
	this.lastUpdateTime = lastUpdateTime;
}
/**
 * @return the sequenceNumber
 */
public int getSequenceNumber() {
	return sequenceNumber;
}
/**
 * @param sequenceNumber the sequenceNumber to set
 */
public void setSequenceNumber(int sequenceNumber) {
	this.sequenceNumber = sequenceNumber;
}
/**
 * @return the lastUpdateUser
 */
public String getLastUpdateUser() {
	return lastUpdateUser;
}
/**
 * @param lastUpdateUser the lastUpdateUser to set
 */
public void setLastUpdateUser(String lastUpdateUser) {
	this.lastUpdateUser = lastUpdateUser;
}
/**
 * @return the code
 */
public String getCode() {
	return code;
}
/**
 * @param code the code to set
 */
public void setCode(String code) {
	this.code = code;
}
/**
 * @return the operationFlag
 */
public String getOperationFlag() {
	return operationFlag;
}
/**
 * @param operationFlag the operationFlag to set
 */
public void setOperationFlag(String operationFlag) {
	this.operationFlag = operationFlag;
}
public String getCompanyCode() {
	return companyCode;
}
/**
 * @param companyCode the companyCode to set
 */
public void setCompanyCode(String companyCode) {
	this.companyCode = companyCode;
}
/**
 * @return the carrierCode
 */
public String getCarrierCode() {
	return carrierCode;
}
/**
 * @param carrierCode the carrierCode to set
 */
public void setCarrierCode(String carrierCode) {
	this.carrierCode = carrierCode;
}
/**
 * @return the airportCode
 */
public String getAirportCode() {
	return airportCode;
}
/**
 * @param airportCode the airportCode to set
 */
public void setAirportCode(String airportCode) {
	this.airportCode = airportCode;
}
/**
 * @return the carrierStatus
 */
public String getCarrierStatus() {
	return carrierStatus;
}
/**
 * @param carrierStatus the carrierStatus to set
 */
public void setCarrierStatus(String carrierStatus) {
	this.carrierStatus = carrierStatus;
}

/**
 * @return the levelType
 */
public String getLevelType() {
	return levelType;
}
/**
 * @param levelType the levelType to set
 */
public void setLevelType(String levelType) {
	this.levelType = levelType;
}
/**
 * @return the levelValue
 */
public String getLevelValue() {
	return levelValue;
}
/**
 * @param levelValue the levelValue to set
 */
public void setLevelValue(String levelValue) {
	this.levelValue = levelValue;
}
/**
 * @return the defaultCarrier
 */
public String getDefaultCarrier() {
	return defaultCarrier;
}
/**
 * @param defaultCarrier the defaultCarrier to set
 */
public void setDefaultCarrier(String defaultCarrier) {
	this.defaultCarrier = defaultCarrier;
}

/**
 * @return the defCarrierIdentifier
 */
public int getDefCarrierIdentifier() {
	return defCarrierIdentifier;
}
/**
 * @param defCarrierIdentifier the defCarrierIdentifier to set
 */
public void setDefCarrierIdentifier(int defCarrierIdentifier) {
	this.defCarrierIdentifier = defCarrierIdentifier;
}


}
