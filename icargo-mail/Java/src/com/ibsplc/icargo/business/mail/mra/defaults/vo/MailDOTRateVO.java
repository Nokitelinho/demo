/*
 * MailDOTRateVO.java created on Aug 03, 2007
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2408
 * 
 */
public class MailDOTRateVO extends AbstractVO {

	private String companyCode;

	private String originCode;

	private String destinationCode;

	private double circleMiles;

	private String rateCode;

	private String rateDescription;

	private String regionCode;

	private double lineHaulRate;

	private String lineHaulRateString;

	private double terminalHandlingRate;

	private double dotRate;

	private String operationFlag;

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
	 * @return Returns the dotRate.
	 */
	public double getDotRate() {
		return dotRate;
	}

	/**
	 * @param dotRate
	 *            The dotRate to set.
	 */
	public void setDotRate(double dotRate) {
		this.dotRate = dotRate;
	}

	/**
	 * @return Returns the lineHaulRate.
	 */
	public double getLineHaulRate() {
		return lineHaulRate;
	}

	/**
	 * @param lineHaulRate
	 *            The lineHaulRate to set.
	 */
	public void setLineHaulRate(double lineHaulRate) {
		this.lineHaulRate = lineHaulRate;
	}

	/**
	 * @return Returns the rateCode.
	 */
	public String getRateCode() {
		return rateCode;
	}

	/**
	 * @param rateCode
	 *            The rateCode to set.
	 */
	public void setRateCode(String rateCode) {
		this.rateCode = rateCode;
	}

	/**
	 * @return Returns the rateDescription.
	 */
	public String getRateDescription() {
		return rateDescription;
	}

	/**
	 * @param rateDescription
	 *            The rateDescription to set.
	 */
	public void setRateDescription(String rateDescription) {
		this.rateDescription = rateDescription;
	}

	/**
	 * @return Returns the regionCode.
	 */
	public String getRegionCode() {
		return regionCode;
	}

	/**
	 * @param regionCode
	 *            The regionCode to set.
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	/**
	 * @return Returns the terminalHandlingRate.
	 */
	public double getTerminalHandlingRate() {
		return terminalHandlingRate;
	}

	/**
	 * @param terminalHandlingRate
	 *            The terminalHandlingRate to set.
	 */
	public void setTerminalHandlingRate(double terminalHandlingRate) {
		this.terminalHandlingRate = terminalHandlingRate;
	}

	/**
	 * @return Returns the circleMiles.
	 */
	public double getCircleMiles() {
		return circleMiles;
	}

	/**
	 * @param circleMiles
	 *            The circleMiles to set.
	 */
	public void setCircleMiles(double circleMiles) {
		this.circleMiles = circleMiles;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String getDestinationCode() {
		return destinationCode;
	}

	/**
	 * @param destinationCode
	 *            The destinationCode to set.
	 */
	public void setDestinationCode(String destinationCode) {
		this.destinationCode = destinationCode;
	}

	/**
	 * @return Returns the originCode.
	 */
	public String getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode
	 *            The originCode to set.
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
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
	 * @return Returns the lineHaulRateString.
	 */
	public String getLineHaulRateString() {
		return lineHaulRateString;
	}

	/**
	 * @param lineHaulRateString
	 *            The lineHaulRateString to set.
	 */
	public void setLineHaulRateString(String lineHaulRateString) {
		this.lineHaulRateString = lineHaulRateString;
	}

}