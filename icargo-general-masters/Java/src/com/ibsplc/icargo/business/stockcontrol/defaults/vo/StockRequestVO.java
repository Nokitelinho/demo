/*
 * StockRequestVO.java Created on Aug 25, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockRequestVO extends AbstractVO {
    /**
     * Flag Yes
     */
	public static final String FLAG_YES="Y";
	/**
	 * Flag No
	 */
	public static final String FLAG_NO="N";
	/**
	 * Status New
	 */
	public static final String STATUS_NEW="N";
	/**
	 * Status Cancelled
	 */
	public static final String STATUS_CANCEL="C";
	/**
	 * Status Rejected
	 */
	public static final String STATUS_REJECTED="R";
	/**
	 * Status Approved
	 */
	public static final String STATUS_APPROVED="A";
	/**
	 * Status Allocated
	 */
	public static final String STATUS_ALLOCATED="L";
	/**
	 * Status Completed
	 */
	public static final String STATUS_COMPLETED="M";
	/**
	 * Flag Cancel
	 */
	public static final String OPERATION_FLAG_CANCEL="C";
	/**
	 * This is for identifying the product for audit
	 */
	public static final String STOCKREQUEST_AUDIT_PRODUCTNAME = "stockcontrol";
    /**
     * This is for identifying the module for audit
     */
	public static final String STOCKREQUEST_AUDIT_MODULENAME = "defaults";
	/**
	 * This will be used for identifying entity for audit and also for creating the audit vo
	 */
	public static final String STOCKREQUEST_AUDIT_ENTITYNAME = "com.ibsplc.icargo.business.stockcontrol.defaults.StockRequest";
	/**
     *  Company code
     */
    private String companyCode;
    /**
     * Stock Request reference number
     */
    private String requestRefNumber;
    /**
     * Stock requester code
     */
    private String stockHolderCode;
    /**
     * Stock Holder Type
     */
    private String stockHolderType;
     /**
     * Request date
     */
    private LocalDate requestDate;
    /**
     * Document type
     */
    private String documentType;
    /**
     * Document sub type
     */
    private String documentSubType;
    /**
     * flag for indicating manual stock for physical documents
     */
    private boolean isManual;
    
    private String productName;

    /**
     * Status of stock request.Possible values are
     * N - New
     * A - Approved
     * R - Rejected
     * L - Allocated
     * M - Completed
     * C - Cancelled
     */
    private String status;
    
    private String awbPrefix;
    
	/**
     * Quantity of requested stock
     */
    private long requestedStock;

    /**
     * Quantity of Approved stock
     */
    private long persistedApprovedStock;
    /**
     * Quantity of approved stock
     */
    private long approvedStock;
    /**
     * Quantity of allocated stock
     */
    private long allocatedStock;
    /**
     * Remarks
     */
    private String remarks;
    /**
     * Remarks of the approver
     */
    private String approvalRemarks;

    /**
     * Operation Flag.Possible values are
     * I - Insert
     * U - Update
     * D - Delete
     */
    private String operationFlag;
    /**
     * Last updated date
     */
    private LocalDate lastUpdateDate;
    /**
     * Last updated user
     */
    private String lastUpdateUser;

    /**
     * Stock Holder Name
     */
    private String stockHolderName;

    private String agentCode;

    private String agentName;

    /**
     * Product Code
     */
    private String productCode;

    private LocalDate lastStockHolderUpdateTime;
    
    /**
     * Added by A-2589
     * for #102543
     */
    private String airlineIdentifier;
    /*
     * requestCreatedBy
     */
    private String requestCreatedBy;
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
     * @return Returns the approvalRemarks.
     */
    public String getApprovalRemarks() {
        return approvalRemarks;
    }
    /**
     * @param approvalRemarks The approvalRemarks to set.
     */
    public void setApprovalRemarks(String approvalRemarks) {
        this.approvalRemarks = approvalRemarks;
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
     * @return Returns the isManual.
     */
    public boolean isManual() {
        return isManual;
    }
    /**
     * @param isManual The isManual to set.
     */
    public void setManual(boolean isManual) {
        this.isManual = isManual;
    }
    /**
     * @return Returns the lastUpdateDate.
     */
    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }
    /**
     * @param lastUpdateDate The lastUpdateDate to set.
     */
    public void setLastUpdateDate(LocalDate lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }
     /**
	     * @return Returns the lastUpdateDate.
	     */
	    public LocalDate getLastStockHolderUpdateTime() {
	        return lastStockHolderUpdateTime;
	    }
	    /**
	     * @param lastUpdateDate The lastUpdateDate to set.
	     */
	    public void setLastStockHolderUpdateTime(LocalDate lastUpdateDate) {
	        this.lastStockHolderUpdateTime = lastUpdateDate;
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
    /**
     * @return Returns the remarks.
     */
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    /**
     * @return Returns the requestDate.
     */
    public LocalDate getRequestDate() {
        return requestDate;
    }
    /**
     * @param requestDate The requestDate to set.
     */
    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = requestDate;
    }
    /**
     * @return Returns the requestedStock.
     */
    public long getRequestedStock() {
        return requestedStock;
    }
    /**
     * @param requestedStock The requestedStock to set.
     */
    public void setRequestedStock(long requestedStock) {
        this.requestedStock = requestedStock;
    }
    /**
     * @return Returns the requestRefNumber.
     */
    public String getRequestRefNumber() {
        return requestRefNumber;
    }
    /**
     * @param requestRefNumber The requestRefNumber to set.
     */
    public void setRequestRefNumber(String requestRefNumber) {
        this.requestRefNumber = requestRefNumber;
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
     * @return Returns the stockRequesterCode.
     */
    public String getStockHolderCode() {
        return stockHolderCode;
    }
    /**
     * @param stockHolderCode The stockRequesterCode to set.
     */
    public void setStockHolderCode(String stockHolderCode) {
        this.stockHolderCode = stockHolderCode;
    }

    /**
     * @return Returns the approvedStock.
     */
    public long getApprovedStock() {
        return approvedStock;
    }
    /**
     * @param approvedStock The approvedStock to set.
     */
    public void setApprovedStock(long approvedStock) {
        this.approvedStock = approvedStock;
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
	/**
	 * @return Returns the persistedApprovedStock.
	 */
	public long getPersistedApprovedStock() {
		return persistedApprovedStock;
	}
	/**
	 * @param persistedApprovedStock The persistedApprovedStock to set.
	 */
	public void setPersistedApprovedStock(long persistedApprovedStock) {
		this.persistedApprovedStock = persistedApprovedStock;
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
	 * @return
	 */
	public String getProductCode() {
		return productCode;
	}
	/**
	 * @param productCode
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	/**
	 * @return Returns the agentCode.
	 */
	public String getAgentCode() {
		return agentCode;
	}
	/**
	 * @param agentCode The agentCode to set.
	 */
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return Returns the productName.
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName The productName to set.
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/**
	 * @return the airlineIdentifier
	 */
	public String getAirlineIdentifier() {
		return airlineIdentifier;
	}
	/**
	 * @param airlineIdentifier the airlineIdentifier to set
	 */
	public void setAirlineIdentifier(String airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}
	/**
	 * 
	 * 	Method		:	StockRequestVO.setRequestCreatedBy
	 *	Added by 	:	A-5258 on Aug 23, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param requestCreatedBy 
	 *	Return type	: 	void
	 */
	public void setRequestCreatedBy(String requestCreatedBy) {
		this.requestCreatedBy = requestCreatedBy;
	}
	/**
	 * 
	 * 	Method		:	StockRequestVO.getRequestCreatedBy
	 *	Added by 	:	A-5258 on Aug 23, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getRequestCreatedBy() {
		return requestCreatedBy;
	}
	public String getAwbPrefix() {
		return awbPrefix;
	}
	public void setAwbPrefix(String awbPrefix) {
		this.awbPrefix = awbPrefix;
	}
}
