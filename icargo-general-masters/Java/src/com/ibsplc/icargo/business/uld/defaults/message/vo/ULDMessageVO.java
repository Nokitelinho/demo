/*
 * ULDMessageVO.java Created on AUG 01, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 */

/**
 * @author a-2412
 *
 */
public class ULDMessageVO extends AbstractVO{
	private String originalMessage;
	private String msgType;
	private String airportCode;
	private LocalDate date;
	private String airlineCode;
	private String companyCode;
	 private int airlineIdentifier;
	 private String scmSequenceNumber;
	/**
	 * @return the airlineIdentifier
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
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
	 * @return the date
	 */
	public LocalDate getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(LocalDate date) {
		this.date = date;
	}
	/**
	 * @return the msgType
	 */
	public String getMsgType() {
		return msgType;
	}
	/**
	 * @param msgType the msgType to set
	 */
	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}
	/**
	 * @return the originalMessage
	 */
	public String getOriginalMessage() {
		return originalMessage;
	}
	/**
	 * @param originalMessage the originalMessage to set
	 */
	public void setOriginalMessage(String originalMessage) {
		this.originalMessage = originalMessage;
	}
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
	 * @return the scmSequenceNumber
	 */
	public String getScmSequenceNumber() {
		return scmSequenceNumber;
	}
	/**
	 * @param scmSequenceNumber the scmSequenceNumber to set
	 */
	public void setScmSequenceNumber(String scmSequenceNumber) {
		this.scmSequenceNumber = scmSequenceNumber;
	}
}
