/*
 * ULDDamageDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;



/**
 * @author A-1347
 *
 */
public class ULDRepairInvoiceFilterVO extends AbstractVO{
    
    private String repairHead;
    private String repairStation;
    private String amount;    
    private String currency;
    private String remarks;
	/**
	 * @return Returns the amount.
	 */
	public String getAmount() {
		return amount;
	}
	/**
	 * @param amount The amount to set.
	 */
	public void setAmount(String amount) {
		this.amount = amount;
	}
	/**
	 * @return Returns the currency.
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
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
	 * @return Returns the repairHead.
	 */
	public String getRepairHead() {
		return repairHead;
	}
	/**
	 * @param repairHead The repairHead to set.
	 */
	public void setRepairHead(String repairHead) {
		this.repairHead = repairHead;
	}
	/**
	 * @return Returns the repairStation.
	 */
	public String getRepairStation() {
		return repairStation;
	}
	/**
	 * @param repairStation The repairStation to set.
	 */
	public void setRepairStation(String repairStation) {
		this.repairStation = repairStation;
	}  

    
}
