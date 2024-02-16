/*
 * StockHolderVO.java Created on Jul 20, 2005
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
public class StockHolderVO extends AbstractVO {
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
	public static final String STOCKHOLDER_AUDIT_ENTITYNAME = "stockcontrol.defaults.StockHolder";
	/**
	 * Constant for stock holder station
	 */
	public static final String STOCKHOLDER_STATION="S";
	/**
	 * Constant for stock holer agent 
	 */
	public static final String STOCKHOLDER_AGENT = "A";
	/**
	 * company code
	 */
	private String companyCode;
    /**
     * stockHolder code
     */
    private String stockHolderCode;
    
    /**
     * Denotes the stockholder type. Possible values are H-headqarters,
     * R-region, S-station,A-agent
     */
    private String stockHolderType;
    
    /**
     * Full qualified name of the stock holder. 
     */
    private String stockHolderName;
    
    /**
     * Description of the stock holder
     */
    private String description;
    
    /**
     * Users holding this privilege would be able to perform actions over
     * the stock held by this stock holder
     */
    private String controlPrivilege;
    
    private String stockHolderContactDetails;
    
    /**
     * Collection of stocks held by the user. Each stock represents the ranges
     * held by the user against a particyular document type
     * Set<StockVO>
     */
    private Collection<StockVO> stock;
    
    /**
     * For optimistic locking
     */
    private String lastUpdateUser;
    
    /**
     * For optimistic locking
     */
    private LocalDate lastUpdateTime;
    /**
     * operaton flag
     */
    private String operationFlag;
 
    /**
     * @return companyCode
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
     * @return stockHolderCode
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
     * @return Returns the controlPrivilege.
     */
    public String getControlPrivilege() {
        return controlPrivilege;
    }
    
    /**
     * @param controlPrivilege The controlPrivilege to set.
     */
    public void setControlPrivilege(String controlPrivilege) {
        this.controlPrivilege = controlPrivilege;
    }

    /**
     * @return Returns the description.
     */   
    public String getDescription() {
        return description;
    }
    
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return Returns the stock.
     * Collection<StockVO>
     */	                                 
    public Collection<StockVO> getStock() {
        return stock;
    }
    
    /**
     * @param stock The stock to set.
     */
    public void setStock(Collection<StockVO> stock) {
        this.stock = stock;
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
    /**
     * getting operation flag
     * @return operationFlag
     */
    public String getOperationFlag(){
    	return operationFlag;
    }
    /**
     * Method for setting operationFlag
     * @param operationFlag
     */
    public void setOperationFlag(String operationFlag){
    	this.operationFlag=operationFlag;
    }

	/**
	 * @return
	 */
	public String getStockHolderContactDetails() {
		return stockHolderContactDetails;
	}

	/**
	 * @param stockHolderContactDetails
	 */
	public void setStockHolderContactDetails(String stockHolderContactDetails) {
		this.stockHolderContactDetails = stockHolderContactDetails;
	}
}
