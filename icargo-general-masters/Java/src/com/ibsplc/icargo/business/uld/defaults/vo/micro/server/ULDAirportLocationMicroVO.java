package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2052
 *
 */
public class ULDAirportLocationMicroVO extends AbstractVO{

	private String companyCode;
	private String airportCode;
	private String facilityType;
	private String sequenceNumber;
	private String facilityCode;
	private String description;
	private String operationFlag;
	private String defaultFlag;
	/**
	 * @return String Returns the defaultFlag.
	 */
	public String getDefaultFlag() {
		return this.defaultFlag;
	}
	/**
	 * @param defaultFlag The defaultFlag to set.
	 */
	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}
	/**
	 * @return String Returns the airportCode.
	 */
	public String getAirportCode() {
		return this.airportCode;
	}
	/**
	 * @param airportCode The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
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
	 * @return String Returns the description.
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return String Returns the facilityCode.
	 */
	public String getFacilityCode() {
		return this.facilityCode;
	}
	/**
	 * @param facilityCode The facilityCode to set.
	 */
	public void setFacilityCode(String facilityCode) {
		this.facilityCode = facilityCode;
	}
	/**
	 * @return String Returns the facilityType.
	 */
	public String getFacilityType() {
		return this.facilityType;
	}
	/**
	 * @param facilityType The facilityType to set.
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return String Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return String Returns the sequenceNumber.
	 */
	public String getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}


}
