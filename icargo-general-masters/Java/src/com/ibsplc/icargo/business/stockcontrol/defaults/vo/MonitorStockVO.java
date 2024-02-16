/*
 * MonitorStockVO.java Created on Sep 10, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1366
 *
 */
public class MonitorStockVO extends AbstractVO {
	/**
	 * Company Code
	 */
	private String companyCode;
	
	/**
	 * Stock Holder Code
	 */
	private String stockHolderCode;
	/**
	 * Reference
	 */
	private String reference;
	
	/**
	 * Document Type
	 */
	private String documentType;
	
	/**
	 * Document Sub Type
	 */
	private String documentSubType;
	
	/**
	 * Available Stock
	 */
	private long availableStock;
	
	/**
	 * Allocated Stock
	 */
	private long allocatedStock;
	
	/**
	 * Requests received
	 */
	private long requestsReceived;
	
	/**
	 * Requests Placed
	 */
	private long requestsPlaced;
	/**
	 * physically Allocated Stock
	 */
	private long phyAllocatedStock;
	/**
	 * physically Available Stock
	 */
	private long phyAvailableStock;
	/**
	 * Manual allocated stock
	 */
	private long manAllocatedStock;
	/**
	 * Manual available stock
	 */
	private long manAvailableStock;
	
	private String stockHolderType;
	
	private String approverCode;
	
	private String stockHolderName;
	/**
	 * Collection of stocks held by the user. Each stock represents the ranges
	 * held by the user against a particyular document type
	 * Set<MonitorStockVO>
	 */
	private Collection<MonitorStockVO> monitorStock;
	/**
	 * @return Returns the manAllocatedStock.
	 */
	public long getManAllocatedStock() {
		return manAllocatedStock;
	}
	/**
	 * @return Returns the manAvailableStock.
	 */
	public long getManAvailableStock() {
		return manAvailableStock;
	}
	/**
	 * @return Returns the phyAllocatedStock.
	 */
	public long getPhyAllocatedStock() {
		return phyAllocatedStock;
	}
	/**
	 * @return Returns the phyAvailableStock.
	 */
	public long getPhyAvailableStock() {
		return phyAvailableStock;
	}
	/**
	 * @param manAllocatedStock The manAllocatedStock to set.
	 */
	public void setManAllocatedStock(long manAllocatedStock) {
		this.manAllocatedStock = manAllocatedStock;
	}
	/**
	 * @param manAvailableStock The manAvailableStock to set.
	 */
	public void setManAvailableStock(long manAvailableStock) {
		this.manAvailableStock = manAvailableStock;
	}
	/**
	 * @param phyAllocatedStock The phyAllocatedStock to set.
	 */
	public void setPhyAllocatedStock(long phyAllocatedStock) {
		this.phyAllocatedStock = phyAllocatedStock;
	}
	/**
	 * @param phyAvailableStock The phyAvailableStock to set.
	 */
	public void setPhyAvailableStock(long phyAvailableStock) {
		this.phyAvailableStock = phyAvailableStock;
	}
	/**
	 * Default constructor
	 *
	 */
	public MonitorStockVO(){
		
	}
	/**
	 * @return Returns the stock.
	 * Collection<MonitorStockVO>
	 */
	public Collection<MonitorStockVO> getMonitorStock() {
		return monitorStock;
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
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * @return Returns the reference.
	 */
	public String getReference() {
		return reference;
	}
	/**
	 * @param reference The reference to set.
	 */
	public void setReference(String reference) {
		this.reference = reference;
	}
	/**
	 * @return Returns the requestsPlaced.
	 */
	public long getRequestsPlaced() {
		return requestsPlaced;
	}
	/**
	 * @param requestsPlaced The requestsPlaced to set.
	 */
	public void setRequestsPlaced(long requestsPlaced) {
		this.requestsPlaced = requestsPlaced;
	}
	/**
	 * @return Returns the requestsReceived.
	 */
	public long getRequestsReceived() {
		return requestsReceived;
	}
	/**
	 * @param requestsReceived The requestsReceived to set.
	 */
	public void setRequestsReceived(long requestsReceived) {
		this.requestsReceived = requestsReceived;
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
	 *  The monitorstock to set.
	 * @param monitorStock
	 */
	public void setMonitorStock(Collection<MonitorStockVO> monitorStock) {
		this.monitorStock = monitorStock;
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
	 * 
	 * @return
	 */
	public String getApproverCode() {
		return approverCode;
	}
	
	/**
	 * 
	 * @param approverCode
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}
	
	/**
	 * 
	 * @return
	 */
	public String getStockHolderName() {
		return stockHolderName;
	}
	/**
	 * 
	 * @param stockHolderName
	 */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}
}
