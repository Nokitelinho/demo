/*
 * OnwardRoutingAtAirportVO.java Created on Jun 30, 2016
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
 * TODO Add the purpose of this class
 * 
 * @author A-3109
 * 
 */

public class OnwardRoutingAtAirportVO extends AbstractVO {

	private int routingSerialNumber;

	private String onwardCarrierCode;

	private LocalDate onwardFlightDate;

	private String pou;

	private int onwardCarrierId;

	private String onwardFlightNumber;

	/**
	 * @return Returns the routingSerialNumber.
	 */
	public int getRoutingSerialNumber() {
		return routingSerialNumber;
	}

	/**
	 * @param routingSerialNumber
	 *            The routingSerialNumber to set.
	 */
	public void setRoutingSerialNumber(int routingSerialNumber) {
		this.routingSerialNumber = routingSerialNumber;
	}

	/**
	 * @return Returns the onwardCarrierCode.
	 */
	public String getOnwardCarrierCode() {
		return onwardCarrierCode;
	}

	/**
	 * @param onwardCarrierCode
	 *            The onwardCarrierCode to set.
	 */
	public void setOnwardCarrierCode(String onwardCarrierCode) {
		this.onwardCarrierCode = onwardCarrierCode;
	}

	/**
	 * @return Returns the onwardCarrierId.
	 */
	public int getOnwardCarrierId() {
		return onwardCarrierId;
	}

	/**
	 * @param onwardCarrierId
	 *            The onwardCarrierId to set.
	 */
	public void setOnwardCarrierId(int onwardCarrierId) {
		this.onwardCarrierId = onwardCarrierId;
	}

	/**
	 * @return Returns the onwardFlightDate.
	 */
	public LocalDate getOnwardFlightDate() {
		return onwardFlightDate;
	}

	/**
	 * @param onwardFlightDate
	 *            The onwardFlightDate to set.
	 */
	public void setOnwardFlightDate(LocalDate onwardFlightDate) {
		this.onwardFlightDate = onwardFlightDate;
	}

	/**
	 * @return Returns the onwardFlightNumber.
	 */
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	/**
	 * @param onwardFlightNumber
	 *            The onwardFlightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFlightNumber) {
		this.onwardFlightNumber = onwardFlightNumber;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}
}
