package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

public class MailRdtMasterVO extends AbstractVO{
	
	private String companyCode; 
	private String gpaCode;
	private String originAirportCodes;
	private String airportCodes;
	private String operationFlag;
    private LocalDate lastUpdateTime;
	private String lastUpdateUser;
    private long seqnum;
    private int rdtOffset ;
    private int rdtDay;
    private String rdtRule;
    private String mailServiceLevel;
    private String mailClass;
    private String mailType;
    private String rdtCfgType;
	/**
	 * @return the companyCode
	 */
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
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}
	/**
	 * @return the 	OriginAirportCodes
	 */
	public String getOriginAirportCodes() {
		return originAirportCodes;
	}   
	/**
	 * @param OriginAirportCodes the OriginAirportCodes to set
	 */
	public void setOriginAirportCodes(String originAirportCodes) {
		this.originAirportCodes = originAirportCodes;
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
	 * @return the seqnum
	 */
	public long getSeqnum() {
		return seqnum;
	}
	/**
	 * @param seqnum the seqnum to set
	 */
	public void setSeqnum(long seqnum) {
		this.seqnum = seqnum;
	}
	/**
	 * @return the rdtOffset
	 */
	public int getRdtOffset() {
		return rdtOffset;
	}
	/**
	 * @param rdtOffset the rdtOffset to set
	 */
	public void setRdtOffset(int rdtOffset) {
		this.rdtOffset = rdtOffset;
	}
	/**
	 * @return the rdtDay
	 */
	public int getRdtDay() {
		return rdtDay;
	}
	/**
	 * @param rdtDay the rdtDay to set
	 */
	public void setRdtDay(int rdtDay) {
		this.rdtDay = rdtDay;
	}
	/**
	 * @return the rdtRule
	 */
	public String getRdtRule() {
		return rdtRule;
	}
	/**
	 * @param rdtRule the rdtRule to set
	 */
	public void setRdtRule(String rdtRule) {
		this.rdtRule = rdtRule;
	}
	/**
	 * @return the mailServiceLevel
	 */
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	/**
	 * @param mailServiceLevel the mailServiceLevel to set
	 */
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	/**
	 * @return the mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return the airportCodes
	 */
	public String getAirportCodes() {
		return airportCodes;
	}
	/**
	 * @param AirportCodes the AirportCodes to set
	 */
	public void setAirportCodes(String airportCodes) {
		this.airportCodes = airportCodes;
	}
	/**
	 * @return the mailType
	 */
	public String getMailType() {
		return mailType;
	}
	/**
	 * @param mailType the mailType to set
	 */
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getRdtCfgType() {
		return rdtCfgType;
	}
	public void setRdtCfgType(String rdtCfgType) {
		this.rdtCfgType = rdtCfgType;
	}
	/**
	 * 	Getter for airportCode 
	 *	Added by : A-6991 on 18-Jul-2018
	 * 	Used for :ICRD-212544
	 */
/*	public String getAirportCode() {
		return airportCode;
	}
	*//**
	 *  @param airportCode the airportCode to set
	 * 	Setter for airportCode 
	 *	Added by : A-6991 on 18-Jul-2018
	 * 	Used for :ICRD-212544
	 *//*
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}*/
   
}
