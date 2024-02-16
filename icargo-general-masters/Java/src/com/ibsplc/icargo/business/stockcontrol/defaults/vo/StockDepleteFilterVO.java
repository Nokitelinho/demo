/*
 * StockDepleteFilterVO.java Created on Sep 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.vo;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1366
 *
 */
public class StockDepleteFilterVO extends AbstractVO {

    /**
     * Company Code
     */
    private String companyCode;

	/**
	* airline Identifier
	*/
    private int airlineId;
    
    private String stockHolderCode;
    
    private String documentType;
    
    private String documentSubType;

    private String reopenFlag;

    private GMTDate actualDate;
    // Added under BUG_ICRD-23686_AiynaSuresh_28Mar13
    private int logClearingThreshold;

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
	 * @return Returns the reopenFlag.
	 */
	public String getReopenFlag() {
		return reopenFlag;
	}

	/**
	 * @param reopenFlag The reopenFlag to set.
	 */
	public void setReopenFlag(String reopenFlag) {
		this.reopenFlag = reopenFlag;
	}

	/**
	 * @return Returns the actualDate.
	 */
	public GMTDate getActualDate() {
		return actualDate;
	}

	/**
	 * @param actualDate The actualDate to set.
	 */
	public void setActualDate(GMTDate actualDate) {
		this.actualDate = actualDate;
	}

	/**
	 * @return the logClearingThreshold
	 */
	public int getLogClearingThreshold() {
		return logClearingThreshold;
	}

	/**
	 * @param logClearingThreshold the logClearingThreshold to set
	 */
	public void setLogClearingThreshold(int logClearingThreshold) {
		this.logClearingThreshold = logClearingThreshold;
	}

	

}
