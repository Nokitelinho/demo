/*
 * ResditTransactionDetailVO.java Created on Jun 30, 2016
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
public class ResditTransactionDetailVO extends AbstractVO {

	private String transaction;

	private String receivedResditFlag;

	private String assignedResditFlag;

	private String upliftedResditFlag;

	private String handedOverResditFlag;

	private String loadedResditFlag;

	private String handedOverReceivedResditFlag;

	private String operationFlag;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;

	private String deliveredResditFlag;
	private String readyForDeliveryFlag;
	private String transportationCompletedResditFlag;
	private String arrivedResditFlag;

	private String returnedResditFlag;

	/**
	 * @return Returns the assignedResditFlag.
	 */
	public String getAssignedResditFlag() {
		return assignedResditFlag;
	}

	/**
	 * @param assignedResditFlag
	 *            The assignedResditFlag to set.
	 */
	public void setAssignedResditFlag(String assignedResditFlag) {
		this.assignedResditFlag = assignedResditFlag;
	}

	/**
	 * @return Returns the departedResditFlag.
	 */
	public String getLoadedResditFlag() {
		return loadedResditFlag;
	}

	/**
	 * @param departedResditFlag
	 *            The departedResditFlag to set.
	 */
	public void setLoadedResditFlag(String departedResditFlag) {
		this.loadedResditFlag = departedResditFlag;
	}

	/**
	 * @return Returns the handedOverResditFlag.
	 */
	public String getHandedOverResditFlag() {
		return handedOverResditFlag;
	}

	/**
	 * @param handedOverResditFlag
	 *            The handedOverResditFlag to set.
	 */
	public void setHandedOverResditFlag(String handedOverResditFlag) {
		this.handedOverResditFlag = handedOverResditFlag;
	}

	/**
	 * @return Returns the receivedResditFlag.
	 */
	public String getReceivedResditFlag() {
		return receivedResditFlag;
	}

	/**
	 * @param receivedResditFlag
	 *            The receivedResditFlag to set.
	 */
	public void setReceivedResditFlag(String receivedResditFlag) {
		this.receivedResditFlag = receivedResditFlag;
	}

	/**
	 * @return Returns the transaction.
	 */
	public String getTransaction() {
		return transaction;
	}

	/**
	 * @param transaction
	 *            The transaction to set.
	 */
	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	/**
	 * @return Returns the upliftedResditFlag.
	 */
	public String getUpliftedResditFlag() {
		return upliftedResditFlag;
	}

	/**
	 * @param upliftedResditFlag
	 *            The upliftedResditFlag to set.
	 */
	public void setUpliftedResditFlag(String upliftedResditFlag) {
		this.upliftedResditFlag = upliftedResditFlag;
	}

	/**
	 * @return Returns the handedOverReceivedResditFlag.
	 */
	public String getHandedOverReceivedResditFlag() {
		return handedOverReceivedResditFlag;
	}

	/**
	 * @param handedOverReceivedResditFlag
	 *            The handedOverReceivedResditFlag to set.
	 */
	public void setHandedOverReceivedResditFlag(
			String handedOverReceivedResditFlag) {
		this.handedOverReceivedResditFlag = handedOverReceivedResditFlag;
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

	/**
	 * @return the deliveredResditFlag
	 */
	public String getDeliveredResditFlag() {
		return deliveredResditFlag;
	}

	/**
	 * @param deliveredResditFlag
	 *            the deliveredResditFlag to set
	 */
	public void setDeliveredResditFlag(String deliveredResditFlag) {
		this.deliveredResditFlag = deliveredResditFlag;
	}

	/**
	 * @return the returnedResditFlag
	 */
	public String getReturnedResditFlag() {
		return returnedResditFlag;
	}

	/**
	 * @param returnedResditFlag
	 *            the returnedResditFlag to set
	 */
	public void setReturnedResditFlag(String returnedResditFlag) {
		this.returnedResditFlag = returnedResditFlag;
	}

	/**
	 * Getter for readyForDeliveryFlag Added by : A-5201 on 13-Oct-2014 Used for
	 * :
	 */
	public String getReadyForDeliveryFlag() {
		return readyForDeliveryFlag;
	}

	/**
	 * @param readyForDeliveryFlag
	 *            the readyForDeliveryFlag to set Setter for
	 *            readyForDeliveryFlag Added by : A-5201 on 13-Oct-2014 Used for
	 *            :
	 */
	public void setReadyForDeliveryFlag(String readyForDeliveryFlag) {
		this.readyForDeliveryFlag = readyForDeliveryFlag;
	}

	/**
	 * Getter for transportationCompletedResditFlag Added by : A-5201 on
	 * 13-Oct-2014 Used for :
	 */
	public String getTransportationCompletedResditFlag() {
		return transportationCompletedResditFlag;
	}

	/**
	 * @param transportationCompletedResditFlag
	 *            the transportationCompletedResditFlag to set Setter for
	 *            transportationCompletedResditFlag Added by : A-5201 on
	 *            13-Oct-2014 Used for :
	 */
	public void setTransportationCompletedResditFlag(
			String transportationCompletedResditFlag) {
		this.transportationCompletedResditFlag = transportationCompletedResditFlag;
	}

	/**
	 * Getter for arrivedResditFlag Added by : A-5201 on 13-Oct-2014 Used for :
	 */
	public String getArrivedResditFlag() {
		return arrivedResditFlag;
	}

	/**
	 * @param arrivedResditFlag
	 *            the arrivedResditFlag to set Setter for arrivedResditFlag
	 *            Added by : A-5201 on 13-Oct-2014 Used for :
	 */
	public void setArrivedResditFlag(String arrivedResditFlag) {
		this.arrivedResditFlag = arrivedResditFlag;
	}

}
