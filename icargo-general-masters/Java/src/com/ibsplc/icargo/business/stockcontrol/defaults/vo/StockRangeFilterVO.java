/*
 * StockRangeFilterVO.java Created on Feb 04,2008.
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;



import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import java.util.Collection;

/**
 * Role class for the stockcontrol defaults subsystem 
 *
 * @author A-3184
 *
 */
public class StockRangeFilterVO extends AbstractVO {
	/**
	 * constant for stock holder audit product name
	 */
	public static final String STOCKHOLDER_AUDIT_PRODUCTNAME = "stockcontrol";
    /**
     * constant for stock holder audit module name
     */
	public static final String STOCKHOLDER_AUDIT_MODULENAME = "defaults";
	/**
	 * constant for stock holder entity name
	 */
	public static final String STOCKHOLDER_AUDIT_ENTITYNAME = 
								"stockcontrol.defaults.StockRangeHistory";

	private String companyCode;

	private int airlineIdentifier;

	private String fromStockHolderCode;
	
	private String toStockHolderCode;

	private String documentType;

	private String documentSubType;

	private String serialNumber;
	
	private String accountNumber;

	private String rangeType;

	private String startRange;

	private String endRange;
	
	private String status;
	
	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String startDateStr;
	
	private String endDateStr;
	
	private String awb;
	
	private String awp;
	
	private boolean isHistory;
	
	private String station;

	//added by A-2881 for ICRD-3082
	private String userId;
	
	private Collection<String> availableStatus;
	
	private int pageNumber;
	
	//Added by A-5220 for ICRD-20959 starts
	private int totalRecordsCount;
	
	 //Added as part of ICRD-105821
    private String privilegeLevelType;

    private String privilegeLevelValue;
    
    private String privilegeRule;
    private int pageSize;
	  /**
	 * @return the totalRecordsCount
	 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}	
	/**
	 * @param totalRecordsCount the totalRecordsCount to set
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	//Added by A-5220 for ICRD-20959 ends

	  /**
   * Default Constructor
   *
   */
  public StockRangeFilterVO(){
      
  }


	/**
	 * @return Returns the accountNumber.
	 */
	public String getAccountNumber() {
		return accountNumber;
	}


	/**
	 * @param accountNumber The accountNumber to set.
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	/**
	 * @return Returns the airlineIdentifier.
	 */
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}


	/**
	 * @param airlineIdentifier The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
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
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return Returns the endRange.
	 */
	public String getEndRange() {
		return endRange;
	}


	/**
	 * @param endRange The endRange to set.
	 */
	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}


	/**
	 * @return Returns the fromStockHolderCode.
	 */
	public String getFromStockHolderCode() {
		return fromStockHolderCode;
	}


	/**
	 * @param fromStockHolderCode The fromStockHolderCode to set.
	 */
	public void setFromStockHolderCode(String fromStockHolderCode) {
		this.fromStockHolderCode = fromStockHolderCode;
	}


	/**
	 * @return Returns the rangeType.
	 */
	public String getRangeType() {
		return rangeType;
	}


	/**
	 * @param rangeType The rangeType to set.
	 */
	public void setRangeType(String rangeType) {
		this.rangeType = rangeType;
	}


	/**
	 * @return Returns the serialNumber.
	 */
	public String getSerialNumber() {
		return serialNumber;
	}


	/**
	 * @param serialNumber The serialNumber to set.
	 */
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}


	/**
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return Returns the startRange.
	 */
	public String getStartRange() {
		return startRange;
	}


	/**
	 * @param startRange The startRange to set.
	 */
	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}


	/**
	 * @return Returns the status.
	 */
	public String getStatus() {
		return status;
	}


	/**
	 * @param status The status to set.
	 */
	public void setStatus(String status) {
		this.status = status;
	}


	/**
	 * @return Returns the isHistory.
	 */
	public boolean isHistory() {
		return isHistory;
	}

	/**
	 * @return Returns the isHistory.
	 */
	public boolean getIsHistory() {
		return isHistory;
	}

	/**
	 * @param isHistory The isHistory to set.
	 */
	public void setHistory(boolean isHistory) {
		this.isHistory = isHistory;
	}


	/**
	 * @return Returns the awb.
	 */
	public String getAwb() {
		return awb;
	}


	/**
	 * @param awb The awb to set.
	 */
	public void setAwb(String awb) {
		this.awb = awb;
	}


	/**
	 * @return Returns the toStockHolderCode.
	 */
	public String getToStockHolderCode() {
		return toStockHolderCode;
	}


	/**
	 * @param toStockHolderCode The toStockHolderCode to set.
	 */
	public void setToStockHolderCode(String toStockHolderCode) {
		this.toStockHolderCode = toStockHolderCode;
	}


	/**
	 * @return Returns the awp.
	 */
	public String getAwp() {
		return awp;
	}


	/**
	 * @param awp The awp to set.
	 */
	public void setAwp(String awp) {
		this.awp = awp;
	}


	/**
	 * @return the endDateStr
	 */
	public String getEndDateStr() {
		return endDateStr;
	}


	/**
	 * @param endDateStr the endDateStr to set
	 */
	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}


	/**
	 * @return the startDateStr
	 */
	public String getStartDateStr() {
		return startDateStr;
	}


	/**
	 * @param startDateStr the startDateStr to set
	 */
	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}


	/**
	 * @return the station
	 */
	public String getStation() {
		return station;
	}


	/**
	 * @param station the station to set
	 */
	public void setStation(String station) {
		this.station = station;
	}


	/**
	 * @return the userId
	 */
	public String getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}


	/**
	 * @return the availableStatus
	 */
	public Collection<String> getAvailableStatus() {
		return availableStatus;
	}


	/**
	 * @param availableStatus the availableStatus to set
	 */
	public void setAvailableStatus(Collection<String> availableStatus) {
		this.availableStatus = availableStatus;
	}

	
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}


	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	/**
	 * @return the privilegeLevelType
	 */
	public String getPrivilegeLevelType() {
		return privilegeLevelType;
	}
	/**
	 * @param privilegeLevelType the privilegeLevelType to set
	 */
	public void setPrivilegeLevelType(String privilegeLevelType) {
		this.privilegeLevelType = privilegeLevelType;
	}
	/**
	 * @return the privilegeLevelValue
	 */
	public String getPrivilegeLevelValue() {
		return privilegeLevelValue;
	}
	/**
	 * @param privilegeLevelValue the privilegeLevelValue to set
	 */
	public void setPrivilegeLevelValue(String privilegeLevelValue) {
		this.privilegeLevelValue = privilegeLevelValue;
	}
	/**
	 * @return the privilegeRule
	 */
	public String getPrivilegeRule() {
		return privilegeRule;
	}
	/**
	 * @param privilegeRule the privilegeRule to set
	 */
	public void setPrivilegeRule(String privilegeRule) {
		this.privilegeRule = privilegeRule;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageSize() {
		return pageSize;
	}
	
  
  

}
