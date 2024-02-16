/*
 * ResiditRestrictonVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3109
 * 
 */
public class ResiditRestrictonVO extends AbstractVO {

	private String companyCode;
	private String airportCode;

	private String carrierCode;
	private String carrierName;
	private String paCode;

	private int carrierid;

	private String operationFlag;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	// Added by A-4072 For Cr ICQFBS-1147 Starts
	private String carditOverrideFlag;
	private String inboundFlag;
	private String outboundFlag;

	// Added by A-4072 For Cr ICQFBS-1147 end

	/**
	 * @return Returns the airportCode.
	 */
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carrierName.
	 */
	public String getCarrierName() {
		return carrierName;
	}

	/**
	 * @param carrierName
	 *            The carrierName to set.
	 */
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	/**
	 * @return Returns the carrierid.
	 */
	public int getCarrierid() {
		return carrierid;
	}

	/**
	 * @param carrierid
	 *            The carrierid to set.
	 */
	public void setCarrierid(int carrierid) {
		this.carrierid = carrierid;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return the carditOverrideFlag
	 */
	public String getCarditOverrideFlag() {
		return carditOverrideFlag;
	}

	/**
	 * @param carditOverrideFlag
	 *            the carditOverrideFlag to set
	 */
	public void setCarditOverrideFlag(String carditOverrideFlag) {
		this.carditOverrideFlag = carditOverrideFlag;
	}

	/**
	 * @return the inboundFlag
	 */
	public String getInboundFlag() {
		return inboundFlag;
	}

	/**
	 * @param inboundFlag
	 *            the inboundFlag to set
	 */
	public void setInboundFlag(String inboundFlag) {
		this.inboundFlag = inboundFlag;
	}

	/**
	 * @return the outboundFlag
	 */
	public String getOutboundFlag() {
		return outboundFlag;
	}

	/**
	 * @param outboundFlag
	 *            the outboundFlag to set
	 */
	public void setOutboundFlag(String outboundFlag) {
		this.outboundFlag = outboundFlag;
	}

}
