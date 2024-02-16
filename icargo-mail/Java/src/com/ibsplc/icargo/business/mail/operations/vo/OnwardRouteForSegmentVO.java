/*
 * OnwardRouteForSegmentVO.java Created on Jun 30, 2016
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
/*
 * Revision History
 * --------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * Jun 14, 2006 A-1739 First Draft
 */
public class OnwardRouteForSegmentVO extends AbstractVO {

	private int routingSerialNumber;

	private String onwardCarrierCode;

	private LocalDate onwardFlightDate;

	private String pou;

	private int onwardCarrierId;

	private String onwardFlightNumber;
	
	private String operationFlag;

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
	 * @return Returns the onwardFightDate.
	 */
	public LocalDate getOnwardFlightDate() {
		return onwardFlightDate;
	}

	/**
	 * @param onwardFightDate
	 *            The onwardFightDate to set.
	 */
	public void setOnwardFlightDate(LocalDate onwardFightDate) {
		this.onwardFlightDate = onwardFightDate;
	}

	/**
	 * @return Returns the onwardFightNumber.
	 */
	public String getOnwardFlightNumber() {
		return onwardFlightNumber;
	}

	/**
	 * @param onwardFightNumber
	 *            The onwardFightNumber to set.
	 */
	public void setOnwardFlightNumber(String onwardFightNumber) {
		this.onwardFlightNumber = onwardFightNumber;
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
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

}
