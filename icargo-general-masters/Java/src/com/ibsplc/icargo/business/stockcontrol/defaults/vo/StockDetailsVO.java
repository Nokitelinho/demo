/*
 * StockDetailsVO.java Created on July 9, 2008
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
 * @author A-3184
 *
 */
public class StockDetailsVO extends AbstractVO {

    /**
     * Company Code
     */
    private String companyCode;

	/**
	* airline Identifier
	*/
    private int airlineId;
    
    private String approverCode;
    
    private String documentType;
    
    private String documentSubType;
    
    private String stockApproverName;
    
    private String stockHolderCode;
    
    private String stockHolderName;
    
    private String avlStartRange;
    
    private String avlEndRange;
    
    private long avlNumberDocuments;
    
    private String allocStartRange;
    
    private String allocEndRange;
    
    private long allocNumberDocuments;
    
    private String usedStartRange;
    
    private String usedEndRange;
    
    private long usedNumberDocuments;
    
    private String awbPrefix;
   
    private long totalStockAvailed;
    
    private long requestReceived;
    
    private long requestPlaced;
    
    private long availableStock;
    
    private long allocatedStock;
    
    private String allocatedTo;
    
    public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public long getTransferredStock() {
		return transferredStock;
	}

	public void setTransferredStock(long transferredStock) {
		this.transferredStock = transferredStock;
	}

	public long getReturnedStock() {
		return returnedStock;
	}

	public void setReturnedStock(long returnedStock) {
		this.returnedStock = returnedStock;
	}

    private long usedStock;
    
    private String stockHolderType;
	
	private Collection<RangeVO> customerRanges;
	/***
	 * Added by chippy for CR1878
	 * *****/
	private long openingBalance; 
       
	private long blacklistedStock;
	private long receivedStock;
    private LocalDate transactionDate;   
    private long transferredStock;
    private long returnedStock;
   
	private long utilizedStock;
	private String lastUpdatedUser;
	private LocalDate lastUpdatedTime;
	private long returnedUtilizedStock;
	private long availableBalance;
	
	public long getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(long availableBalance) {
		this.availableBalance = availableBalance;
	}

	public long getReturnedUtilizedStock() {
		return returnedUtilizedStock;
	}

	public void setReturnedUtilizedStock(long returnedUtilizedStock) {
		this.returnedUtilizedStock = returnedUtilizedStock;
	}

	public long getUtilizedStock() {
		return utilizedStock;
	}

	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	public void setUtilizedStock(long utilizedStock) {
		this.utilizedStock = utilizedStock;
	}

	public long getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(long openingBalance) {
		this.openingBalance = openingBalance;
	}

	public long getBlacklistedStock() {
		return blacklistedStock;
	}

	public void setBlacklistedStock(long blacklistedStock) {
		this.blacklistedStock = blacklistedStock;
	}

	public long getReceivedStock() {
		return receivedStock;
	}

	public void setReceivedStock(long receivedStock) {
		this.receivedStock = receivedStock;
	}
   
	/**
	 * @return Returns the customerRanges.
	 */
	public Collection<RangeVO> getCustomerRanges() {
		return customerRanges;
	}

	/**
	 * @param customerRanges The customerRanges to set.
	 */
	public void setCustomerRanges(Collection<RangeVO> customerRanges) {
		this.customerRanges = customerRanges;
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
	 * @return airlineId
	 */
	public int getAirlineId(){
		return airlineId;
	}
	/**
	 * @param airlineId
	 */
	public void setAirlineId(int airlineId){
		this.airlineId = airlineId;
	}


	/**
	 * @return Returns the stockHolderCode.
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}

	/**
	 * @param stockHolderCode The stockHolderCode to set.
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}


	/**
	 * @return Returns the allocatedStock.
	 */
	public long getAllocatedStock() {
		return allocatedStock;
	}

	/**
	 * @param allocatedStock The allocatedStock to set.
	 */
	public void setAllocatedStock(long allocatedStock) {
		this.allocatedStock = allocatedStock;
	}

	/**
	 * @return Returns the allocatedTo.
	 */
	public String getAllocatedTo() {
		return allocatedTo;
	}

	/**
	 * @param allocatedTo The allocatedTo to set.
	 */
	public void setAllocatedTo(String allocatedTo) {
		this.allocatedTo = allocatedTo;
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
	 * @return Returns the stockApproverName.
	 */
	public String getStockApproverName() {
		return stockApproverName;
	}

	/**
	 * @param stockApproverName The stockApproverName to set.
	 */
	public void setStockApproverName(String stockApproverName) {
		this.stockApproverName = stockApproverName;
	}

	/**
	 * @return Returns the availableStock.
	 */
	public long getAvailableStock() {
		return availableStock;
	}

	/**
	 * @param availableStock The availableStock to set.
	 */
	public void setAvailableStock(long availableStock) {
		this.availableStock = availableStock;
	}

	/**
	 * @return Returns the requestPlaced.
	 */
	public long getRequestPlaced() {
		return requestPlaced;
	}

	/**
	 * @param requestPlaced The requestPlaced to set.
	 */
	public void setRequestPlaced(long requestPlaced) {
		this.requestPlaced = requestPlaced;
	}

	/**
	 * @return Returns the requestReceived.
	 */
	public long getRequestReceived() {
		return requestReceived;
	}

	/**
	 * @param requestReceived The requestReceived to set.
	 */
	public void setRequestReceived(long requestReceived) {
		this.requestReceived = requestReceived;
	}

	/**
	 * @return Returns the stockHolderName.
	 */
	public String getStockHolderName() {
		return stockHolderName;
	}

	/**
	 * @param stockHolderName The stockHolderName to set.
	 */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}

	/**
	 * @return Returns the totalStockAvailed.
	 */
	public long getTotalStockAvailed() {
		return totalStockAvailed;
	}

	/**
	 * @param totalStockAvailed The totalStockAvailed to set.
	 */
	public void setTotalStockAvailed(long totalStockAvailed) {
		this.totalStockAvailed = totalStockAvailed;
	}

	/**
	 * @return Returns the usedStock.
	 */
	public long getUsedStock() {
		return usedStock;
	}

	/**
	 * @param usedStock The usedStock to set.
	 */
	public void setUsedStock(long usedStock) {
		this.usedStock = usedStock;
	}

	/**
	 * @return Returns the documentSubType.
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 * @param documentSubType The documentSubType to set.
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 * @return Returns the documentType.
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the stockHolderType.
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}

	/**
	 * @param stockHolderType The stockHolderType to set.
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}

	/**
	 * @return Returns the allocEndRange.
	 */
	public String getAllocEndRange() {
		return allocEndRange;
	}

	/**
	 * @param allocEndRange The allocEndRange to set.
	 */
	public void setAllocEndRange(String allocEndRange) {
		this.allocEndRange = allocEndRange;
	}

	/**
	 * @return Returns the allocNumberDocuments.
	 */
	public long getAllocNumberDocuments() {
		return allocNumberDocuments;
	}

	/**
	 * @param allocNumberDocuments The allocNumberDocuments to set.
	 */
	public void setAllocNumberDocuments(long allocNumberDocuments) {
		this.allocNumberDocuments = allocNumberDocuments;
	}

	/**
	 * @return Returns the allocStartRange.
	 */
	public String getAllocStartRange() {
		return allocStartRange;
	}

	/**
	 * @param allocStartRange The allocStartRange to set.
	 */
	public void setAllocStartRange(String allocStartRange) {
		this.allocStartRange = allocStartRange;
	}

	/**
	 * @return Returns the avlEndRange.
	 */
	public String getAvlEndRange() {
		return avlEndRange;
	}

	/**
	 * @param avlEndRange The avlEndRange to set.
	 */
	public void setAvlEndRange(String avlEndRange) {
		this.avlEndRange = avlEndRange;
	}

	/**
	 * @return Returns the avlNumberDocuments.
	 */
	public long getAvlNumberDocuments() {
		return avlNumberDocuments;
	}

	/**
	 * @param avlNumberDocuments The avlNumberDocuments to set.
	 */
	public void setAvlNumberDocuments(long avlNumberDocuments) {
		this.avlNumberDocuments = avlNumberDocuments;
	}

	/**
	 * @return Returns the avlStartRange.
	 */
	public String getAvlStartRange() {
		return avlStartRange;
	}

	/**
	 * @param avlStartRange The avlStartRange to set.
	 */
	public void setAvlStartRange(String avlStartRange) {
		this.avlStartRange = avlStartRange;
	}

	/**
	 * @return Returns the awbPrefix.
	 */
	public String getAwbPrefix() {
		return awbPrefix;
	}

	/**
	 * @param awbPrefix The awbPrefix to set.
	 */
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}

	/**
	 * @return Returns the usedEndRange.
	 */
	public String getUsedEndRange() {
		return usedEndRange;
	}

	/**
	 * @param usedEndRange The usedEndRange to set.
	 */
	public void setUsedEndRange(String usedEndRange) {
		this.usedEndRange = usedEndRange;
	}

	/**
	 * @return Returns the usedNumberDocuments.
	 */
	public long getUsedNumberDocuments() {
		return usedNumberDocuments;
	}

	/**
	 * @param usedNumberDocuments The usedNumberDocuments to set.
	 */
	public void setUsedNumberDocuments(long usedNumberDocuments) {
		this.usedNumberDocuments = usedNumberDocuments;
	}

	/**
	 * @return Returns the usedStartRange.
	 */
	public String getUsedStartRange() {
		return usedStartRange;
	}

	/**
	 * @param usedStartRange The usedStartRange to set.
	 */
	public void setUsedStartRange(String usedStartRange) {
		this.usedStartRange = usedStartRange;
	}


}


