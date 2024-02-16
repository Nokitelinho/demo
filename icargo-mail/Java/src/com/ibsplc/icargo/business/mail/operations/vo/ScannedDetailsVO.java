/*
 * ScannedDetailsVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * All the scannedMailDetailsVO are in this VO
 * 
 * @author A-3109
 * 
 */

public class ScannedDetailsVO extends AbstractVO {

	private Collection<ScannedMailDetailsVO> outboundMails;

	private Collection<ScannedMailDetailsVO> arrivedMails;

	private Collection<ScannedMailDetailsVO> returnedMails;

	private Collection<ScannedMailDetailsVO> reassignMails;

	private Collection<ScannedMailDetailsVO> transferMails;

	private Collection<ScannedMailDetailsVO> offloadMails;

	private Collection<ScannedMailDetailsVO> reassignDespatch;

	private String preassignFlag;

	/**
	 * @return Returns the arrivedMails.
	 */
	public Collection<ScannedMailDetailsVO> getArrivedMails() {
		return arrivedMails;
	}

	/**
	 * @param arrivedMails
	 *            The arrivedMails to set.
	 */
	public void setArrivedMails(Collection<ScannedMailDetailsVO> arrivedMails) {
		this.arrivedMails = arrivedMails;
	}

	/**
	 * @return Returns the outboundMails.
	 */
	public Collection<ScannedMailDetailsVO> getOutboundMails() {
		return outboundMails;
	}

	/**
	 * @param outboundMails
	 *            The outboundMails to set.
	 */
	public void setOutboundMails(Collection<ScannedMailDetailsVO> outboundMails) {
		this.outboundMails = outboundMails;
	}

	/**
	 * @return Returns the returnedMails.
	 */
	public Collection<ScannedMailDetailsVO> getReturnedMails() {
		return returnedMails;
	}

	/**
	 * @param returnedMails
	 *            The returnedMails to set.
	 */
	public void setReturnedMails(Collection<ScannedMailDetailsVO> returnedMails) {
		this.returnedMails = returnedMails;
	}

	public String getPreassignFlag() {
		return preassignFlag;
	}

	public void setPreassignFlag(String preassignFlag) {
		this.preassignFlag = preassignFlag;
	}

	public Collection<ScannedMailDetailsVO> getOffloadMails() {
		return offloadMails;
	}

	public void setOffloadMails(Collection<ScannedMailDetailsVO> offloadMails) {
		this.offloadMails = offloadMails;
	}

	public Collection<ScannedMailDetailsVO> getReassignMails() {
		return reassignMails;
	}

	public void setReassignMails(Collection<ScannedMailDetailsVO> reassignMails) {
		this.reassignMails = reassignMails;
	}

	public Collection<ScannedMailDetailsVO> getTransferMails() {
		return transferMails;
	}

	public void setTransferMails(Collection<ScannedMailDetailsVO> transferMails) {
		this.transferMails = transferMails;
	}

	public Collection<ScannedMailDetailsVO> getReassignDespatch() {
		return reassignDespatch;
	}

	public void setReassignDespatch(
			Collection<ScannedMailDetailsVO> reassignDespatch) {
		this.reassignDespatch = reassignDespatch;
	}

}
