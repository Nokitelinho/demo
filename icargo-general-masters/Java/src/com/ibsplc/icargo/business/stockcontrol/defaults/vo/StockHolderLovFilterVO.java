/*
 * StockHolderLovFilterVO.java Created on Jul 20, 2005
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
 * This VO is used as filter for the stock holder lov screen 
 */
public class StockHolderLovFilterVO extends AbstractVO {
    /**
     * company code
     */
    private String companyCode;
    /**
     * stock holder code
     */
    private String stockHolderCode;
    /**
     * description
     */
    private String description;
    
    /**
     * Denotes the stockholder type selected in the scren. Possible 
     * values are H-headqarters, R-region, S-station,A-agent
     */
    private String stockHolderType;
    /**
     * document type
     */
    private String documentType;
    /**
     * document sub type
     */
    private String documentSubType;
    /**
     * stock holder name
     */
    private String stockHolderName;
    /**
     * approver code
     */
    private String approverCode;
    /**
     * flag is used to make query using  StockHolder Type 
     * along with inner query
     */
    private boolean isRequestedBy;
    /**
     * getting approver code
     * @return approverCode
     */
	public String getApproverCode() {
		return approverCode;
	}
	/**
	 * method for setting approver code
	 * @param approverCode
	 */
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}
	/**
	 * method for getting company code
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * Method for setting company code
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * Method for getting description
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * Method for setting description
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * Method for getting document sub type
	 * @return documentSubType
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}
	/**
	 * Method for setting document sub type
	 * @param documentSubType
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}
	/**
	 * Method for getting document type
	 * @return documentType
	 */
	public String getDocumentType() {
		return documentType;
	}
	/**
	 * Method for setting document type
	 * @param documentType
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}
	/**
	 * method for getting stock holder code
	 * @return stockHolderCode
	 */
	public String getStockHolderCode() {
		return stockHolderCode;
	}
	/**
	 * Method for setting stock holder code
	 * @param stockHolderCode
	 */
	public void setStockHolderCode(String stockHolderCode) {
		this.stockHolderCode = stockHolderCode;
	}
	/**
	 * Method for getting stock holder name
	 * @return stockHolderName
	 */
	public String getStockHolderName() {
		return stockHolderName;
	}
	/**
	 * Method for setting stock Holder Name
	 * @param stockHolderName
	 */
	public void setStockHolderName(String stockHolderName) {
		this.stockHolderName = stockHolderName;
	}
	/**
	 * Method for getting stock holder type
	 * @return stockHolderType
	 */
	public String getStockHolderType() {
		return stockHolderType;
	}
	/**
	 * Method for setting stock holdet type
	 * @param stockHolderType
	 */
	public void setStockHolderType(String stockHolderType) {
		this.stockHolderType = stockHolderType;
	}
	/**
	 * @return Returns the isRequestedBy.
	 */
	public boolean isRequestedBy() {
		return isRequestedBy;
	}
	/**
	 * @param isRequestedBy The isRequestedBy to set.
	 */
	public void setRequestedBy(boolean isRequestedBy) {
		this.isRequestedBy = isRequestedBy;
	}
    
}
