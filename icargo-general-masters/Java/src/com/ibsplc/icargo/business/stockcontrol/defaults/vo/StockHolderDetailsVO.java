/*
 * StockHolderDetailsVO.java Created on Jul 20, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;


/**
 * @author A-1358
 * 
 */
public class StockHolderDetailsVO extends AbstractVO {

	private String companyCode;

	private String stockHolderCode;

	/**
	 * Denotes the stockholder type. Possible values are H-headqarters,
	 * R-region, S-station,A-agent
	 */
	private String stockHolderType;
	/**
	 * document type
	 */
	private String docType;
	/**
	 * document sub type
	 */
	private String docSubType;
	/**
	 * reorder level
	 */
	private long reorderLevel;
	/**
	 * reorder quantity
	 */
	private long reorderQuantity;
	/**
	 * reorder alert
	 */
	private boolean isReorderAlert;
	/**
	 * auto stock request
	 */
	private boolean isAutoStockRequest;
	/**
	 * approver code
	 */
	private String approverCode;
	
	private String contactDetails;
	
	private LocalDate lastUpdateTime;
	
	private String lastUpdateUser;
	
	private String displayPage;
	
	/*
	 * Added by A-2589 for 102543
	 */
	private String awbPrefix;
	/*
	 * End - #102543
	 */
	
	/**
	 * stockHolder details
	 */
	private Collection<StockHolderDetailsVO> stockHolderDetails;

	/**
	 * @return Returns the stockHolderDetails.
	 */

	/**
	 * @return Returns the isAutoStockRequest.
	 */
	public boolean isAutoStockRequest() {
		return isAutoStockRequest;
	}

	/**
	 * @param isAutoStockRequest
	 *            The autoStockRequest to set.
	 */
	public void setAutoStockRequest(boolean isAutoStockRequest) {
		this.isAutoStockRequest = isAutoStockRequest;
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
	 * @return Returns the docSubType.
	 */
	public String getDocSubType() {
		return docSubType;
	}

	/**
	 * @param docSubType
	 *            The docSubType to set.
	 */
	public void setDocSubType(String docSubType) {
		this.docSubType = docSubType;
	}

	/**
	 * @return Returns the docType.
	 */
	public String getDocType() {
		return docType;
	}

	/**
	 * @param docType
	 *            The docType to set.
	 */
	public void setDocType(String docType) {
		this.docType = docType;
	}

	/**
	 * @return Returns the isReorderAlert.
	 */
	public boolean isReorderAlert() {
		return isReorderAlert;
	}

	/**
	 * @param isReorderAlert
	 *            The reorderAlert to set.
	 */
	public void setReorderAlert(boolean isReorderAlert) {
		this.isReorderAlert = isReorderAlert;
	}

	
	/**
	 * @return Returns the reorderLevel.
	 */
	public long getReorderLevel() {
		return reorderLevel;
	}

	/**
	 * @param reorderLevel The reorderLevel to set.
	 */
	public void setReorderLevel(long reorderLevel) {
		this.reorderLevel = reorderLevel;
	}

	/**
	 * @return Returns the reorderQuantity.
	 */
	public long getReorderQuantity() {
		return reorderQuantity;
	}

	/**
	 * @param reorderQuantity The reorderQuantity to set.
	 */
	public void setReorderQuantity(long reorderQuantity) {
		this.reorderQuantity = reorderQuantity;
	}

	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode
	 *            The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}

	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * @param stockHolderType
	 *            The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return Returns the approverCode.
	 */
	public String getApproverCode() {
		return approverCode;
	}

	/**
	 * @param approverCode The approverCode to set.
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
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
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
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
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public String getDisplayPage() {
		return displayPage;
	}

	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}

	public Collection<StockHolderDetailsVO> getStockHolderDetails() {
		return stockHolderDetails;
	}

	public void setStockHolderDetails(
			Collection<StockHolderDetailsVO> stockHolderDetails) {
		this.stockHolderDetails = stockHolderDetails;
	}

	/**
	 * @return the awbPrefix
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}

	/**
	 * @param awbPrefix the awbPrefix to set
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	/*public Page<StockHolderDetailsVO> getStockHolderDetails() {
		return stockHolderDetails;
	}

	public void setStockHolderDetails(Page<StockHolderDetailsVO> stockHolderDetails) {
		this.stockHolderDetails = stockHolderDetails;
	}*/
}
