/*
 * StockHolderStockDetailsVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class StockHolderStockDetailsVO extends AbstractVO {

	private String companyCode;
	
	private String stockHolderCode;
	
	private String contactDetails;
	
	/**
	 * 
	 */
	private String docType;
	
	private String docSubType;
	
	private int autoProcessingQty;
	
	private boolean isAutoProcessing;
	
	private int manualStockAvailable;
	
	private int physicalStockAvailable;
	
	private boolean isReorderAlertFlag;
	
	private long reorderLevel;
	
	/**
	 * 
	 */
	private int reorderQuantity;
	
	private String stockApproverCode;
	
	private String airlineId;

	/**
	 * @return
	 */
	public int getAutoProcessingQty() {
		return autoProcessingQty;
	}

	/**
	 * @param autoProcessingQty
	 */
	public void setAutoProcessingQty(int autoProcessingQty) {
		this.autoProcessingQty = autoProcessingQty;
	}

	/**
	 * @return
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return
	 */
	public String getContactDetails() {
		return contactDetails;
	}

	/**
	 * @param contactDetails
	 */
	public void setContactDetails(String contactDetails) {
		this.contactDetails = contactDetails;
	}

	/**
	 * @return
	 */
	public String getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return
	 */
	public boolean isAutoProcessing() {
		return isAutoProcessing;
	}

	/**
	 * @param isAutoProcessing
	 */
	public void setAutoProcessing(boolean isAutoProcessing) {
		this.isAutoProcessing = isAutoProcessing;
	}

	/**
	 * @return
	 */
	public boolean isReorderAlertFlag() {
		return isReorderAlertFlag;
	}

	/**
	 * @param isReorderAlertFlag
	 */
	public void setReorderAlertFlag(boolean isReorderAlertFlag) {
		this.isReorderAlertFlag = isReorderAlertFlag;
	}

	/**
	 * @return
	 */
	public int getManualStockAvailable() {
		return manualStockAvailable;
	}

	/**
	 * @param manualStockAvailable
	 */
	public void setManualStockAvailable(int manualStockAvailable) {
		this.manualStockAvailable = manualStockAvailable;
	}

	/**
	 * @return
	 */
	public int getPhysicalStockAvailable() {
		return physicalStockAvailable;
	}

	/**
	 * @param physicalStockAvailable
	 */
	public void setPhysicalStockAvailable(int physicalStockAvailable) {
		this.physicalStockAvailable = physicalStockAvailable;
	}

	/**
	 * @return
	 */
	public long getReorderLevel() {
		return reorderLevel;
	}

	/**
	 * @param reorderLevel
	 */
	public void setReorderLevel(long reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * @return
	 */
	public int getReorderQuantity() {
		return reorderQuantity;
	}

	/**
	 * @param reorderQuantity
	 */
	public void setReorderQuantity(int reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	/**
	 * @return
	 */
	public String getStockApproverCode() {
		return stockApproverCode;
	}

	/**
	 * @param stockApproverCode
	 */
	public void setStockApproverCode(String stockApproverCode) {
		this.stockApproverCode = stockApproverCode;
	}

	/**
	 * @return
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return the airlineId
	 */
	public String getAirlineId() {
		return airlineId;
	}

	/**
	 * @param airlineId the airlineId to set
	 */
	public void setAirlineId(String airlineId) {
		this.airlineId = airlineId;
	}
	 
	 
}
