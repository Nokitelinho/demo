/*
 * TransferManifestVO.java Created on Jun 30, 2016
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * 
 * @author A-3109
 * 
 */
public class TransferManifestVO extends AbstractVO {

	/**
	 * module
	 */
	public static final String MODULE = "mail";

	/**
	 * submodule
	 */
	public static final String SUBMODULE = "operations";

	/**
	 * entity
	 */
	public static final String ENTITY = "mail.operations.TransferManifest";

	private String companyCode;
	private String transferManifestId;
	private String airPort;
	private String transferredToCarrierCode;
	private String transferredToFltNumber;
	private String transferredFromCarCode;
	private String transferredFromFltNum;
	private LocalDate fromFltDat;
	private LocalDate toFltDat;
	private LocalDate transferredDate;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String toCarCodeDesc;
	private String fromCarCodeDesc;
	private int totalBags;
	//private double totalWeight;
	private Measure totalWeight;//added by A-7371
	private String transferStatus;

	private Collection<DSNVO> dsnVOs;
	private long transferredfrmFltSeqNum;
	private int transferredfrmSegSerNum;
	private String containerNumber;
	private String mailbagId;
	private long mailsequenceNumber;
	private String tranferSource;
	
	
	private String status;
	
    public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
    /**
     * 
     * @return totalWeight
     */
	public Measure getTotalWeight() {
		return totalWeight;
	}
    /**
     * 
     * @param totalWeight
     */
	public void setTotalWeight(Measure totalWeight) {
		this.totalWeight = totalWeight;
	}

	public String getAirPort() {
		return airPort;
	}

	public void setAirPort(String airPort) {
		this.airPort = airPort;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public Collection<DSNVO> getDsnVOs() {
		return dsnVOs;
	}

	public void setDsnVOs(Collection<DSNVO> dsnVOs) {
		this.dsnVOs = dsnVOs;
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

	public String getTransferManifestId() {
		return transferManifestId;
	}

	public void setTransferManifestId(String transferManifestId) {
		this.transferManifestId = transferManifestId;
	}

	public LocalDate getTransferredDate() {
		return transferredDate;
	}

	public void setTransferredDate(LocalDate transferredDate) {
		this.transferredDate = transferredDate;
	}

	public String getTransferredToCarrierCode() {
		return transferredToCarrierCode;
	}

	public void setTransferredToCarrierCode(String transferredToCarrierCode) {
		this.transferredToCarrierCode = transferredToCarrierCode;
	}

	public String getTransferredToFltNumber() {
		return transferredToFltNumber;
	}

	public void setTransferredToFltNumber(String transferredToFltNumber) {
		this.transferredToFltNumber = transferredToFltNumber;
	}

	public String getFromCarCodeDesc() {
		return fromCarCodeDesc;
	}

	public void setFromCarCodeDesc(String fromCarCodeDesc) {
		this.fromCarCodeDesc = fromCarCodeDesc;
	}

	public String getToCarCodeDesc() {
		return toCarCodeDesc;
	}

	public void setToCarCodeDesc(String toCarCodeDesc) {
		this.toCarCodeDesc = toCarCodeDesc;
	}

	public LocalDate getFromFltDat() {
		return fromFltDat;
	}

	public void setFromFltDat(LocalDate fromFltDat) {
		this.fromFltDat = fromFltDat;
	}

	public LocalDate getToFltDat() {
		return toFltDat;
	}

	public void setToFltDat(LocalDate toFltDat) {
		this.toFltDat = toFltDat;
	}

	public String getTransferredFromCarCode() {
		return transferredFromCarCode;
	}

	public void setTransferredFromCarCode(String transferredFromCarCode) {
		this.transferredFromCarCode = transferredFromCarCode;
	}

	public String getTransferredFromFltNum() {
		return transferredFromFltNum;
	}

	public void setTransferredFromFltNum(String transferredFromFltNum) {
		this.transferredFromFltNum = transferredFromFltNum;
	}

	public int getTotalBags() {
		return totalBags;
	}

	public void setTotalBags(int totalBags) {
		this.totalBags = totalBags;
	}
	public long getTransferredfrmFltSeqNum() {
		return transferredfrmFltSeqNum;
	}
	public void setTransferredfrmFltSeqNum(long transferredfrmFltSeqNum) {
		this.transferredfrmFltSeqNum = transferredfrmFltSeqNum;
	}
	public int getTransferredfrmSegSerNum() {
		return transferredfrmSegSerNum;
	}
	public void setTransferredfrmSegSerNum(int transferredfrmSegSerNum) {
		this.transferredfrmSegSerNum = transferredfrmSegSerNum;
	}
	public String getTransferStatus() {
		return transferStatus;
	}
	public void setTransferStatus(String transferStatus) {
		this.transferStatus = transferStatus;
	}
	public String getContainerNumber() {
		return containerNumber;
	}
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	public long getMailsequenceNumber() {
		return mailsequenceNumber;
	}
	public void setMailsequenceNumber(long mailsequenceNumber) {
		this.mailsequenceNumber = mailsequenceNumber;
	}
	public String getTranferSource() {
		return tranferSource;
	}
	public void setTranferSource(String tranferSource) {
		this.tranferSource = tranferSource;
	}
	
	
    
	/*public double getTotalWeight() {
		return totalWeight;
	}

	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}*/
	
	

}
