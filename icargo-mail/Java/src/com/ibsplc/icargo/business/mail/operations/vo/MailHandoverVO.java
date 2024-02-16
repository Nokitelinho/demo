/*
 * MailHandoverVO.java Created on JUL 03, 2018
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
 *  
 * @author A-6986
 * 
 */
public class MailHandoverVO extends AbstractVO{

	private String companyCode;
	private String gpaCode;
	private String hoAirportCodes;
	private String serviceLevels;
	private String handoverTimes;
	private String hoOperationFlags;
	//Added by A-7929 as part of IASCB-35577 starts
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int serialNumber;

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	// Added by A-7929 as part of IASCB-35577 ends
	
	public static final java.lang.String OPERATION_FLAG_INSERT = "I";
	  
	public static final java.lang.String OPERATION_FLAG_DELETE = "D";
	  
	public static final java.lang.String OPERATION_FLAG_UPDATE = "U";

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
	 * @return the hoAirportCodes
	 */
	public String getHoAirportCodes() {
		return hoAirportCodes;
	}
	/**
	 * @param hoAirportCodes the hoAirportCodes to set
	 */
	public void setHoAirportCodes(String hoAirportCodes) {
		this.hoAirportCodes = hoAirportCodes;
	}
	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevels() {
		return serviceLevels;
	}
	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevels(String serviceLevel) {
		this.serviceLevels = serviceLevel;
	}
	/**
	 * @return the handoverTime
	 */
	public String getHandoverTimes() {
		return handoverTimes;
	}
	/**
	 * @param handoverTime the handoverTime to set
	 */
	public void setHandoverTimes(String handoverTime) {
		this.handoverTimes = handoverTime;
	}
	/**
	 * @return the hoOperationFlags
	 */
	public String getHoOperationFlags() {
		return hoOperationFlags;
	}
	/**
	 * @param hoOperationFlags the hoOperationFlags to set
	 */
	public void setHoOperationFlags(String hoOperationFlags) {
		this.hoOperationFlags = hoOperationFlags;
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
	 * @return the exchangeOffice
	 */
	public String getExchangeOffice() {
		return exchangeOffice;
	}

	/**
	 * @param exchangeOffice the exchangeOffice to set
	 */
	public void setExchangeOffice(String exchangeOffice) {
		this.exchangeOffice = exchangeOffice;
	}
	
	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}
	/**
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
}
