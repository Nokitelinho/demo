/*
 * TransactionListVO.java Created on Jan 5, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.transaction.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class TransactionListVO   extends AbstractVO implements Serializable {
    
	/*
	 * This variable is to decide whether to print the ucr-report
	 */
    private boolean isToBePrinted;
    private String transactionType;
    private String transactionNature;
    private String strRetDate;
    private String strRetTime;
    private LocalDate ReturnDate;
    private String returnStationCode;
	
    private Collection<ULDTransactionDetailsVO> uldTransactionsDetails;
    private Page<ULDTransactionDetailsVO> transactionDetailsPage;
    
    private Collection<AccessoryTransactionVO> accessoryTransactions;

	/**
	 * @return Returns the transactionType.
	 */
	public String getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType The transactionType to set.
	 */
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	/**
	 * @return Returns the accessoryTransactions.
	 */
	public Collection<AccessoryTransactionVO> getAccessoryTransactions() {
		return accessoryTransactions;
	}

	/**
	 * @param accessoryTransactions The accessoryTransactions to set.
	 */
	public void setAccessoryTransactions(
			Collection<AccessoryTransactionVO> accessoryTransactions) {
		this.accessoryTransactions = accessoryTransactions;
	}

	/**
	 * @return Returns the uldTransactionsDetails.
	 */
	public Collection<ULDTransactionDetailsVO> getUldTransactionsDetails() {
		return uldTransactionsDetails;
	}

	/**
	 * @param uldTransactionsDetails The uldTransactionsDetails to set.
	 */
	public void setUldTransactionsDetails(
			Collection<ULDTransactionDetailsVO> uldTransactionsDetails) {
		this.uldTransactionsDetails = uldTransactionsDetails;
	}

	/**
	 * @return Returns the returnStationCode.
	 */
	public String getReturnStationCode() {
		return this.returnStationCode;
	}

	/**
	 * @param returnStationCode The returnStationCode to set.
	 */
	public void setReturnStationCode(String returnStationCode) {
		this.returnStationCode = returnStationCode;
	}

	/**
	 * @return Returns the strRetDate.
	 */
	public String getStrRetDate() {
		return this.strRetDate;
	}

	/**
	 * @param strRetDate The strRetDate to set.
	 */
	public void setStrRetDate(String strRetDate) {
		this.strRetDate = strRetDate;
	}

	/**
	 * @return Returns the transactionNature.
	 */
	public String getTransactionNature() {
		return this.transactionNature;
	}

	/**
	 * @param transactionNature The transactionNature to set.
	 */
	public void setTransactionNature(String transactionNature) {
		this.transactionNature = transactionNature;
	}

	/**
	 * @return Returns the returnDate.
	 */
	public LocalDate getReturnDate() {
		return this.ReturnDate;
	}

	/**
	 * @param returnDate The returnDate to set.
	 */
	public void setReturnDate(LocalDate returnDate) {
		this.ReturnDate = returnDate;
	}
/**
 * 
 * @return strRetTime
 */
	public String getStrRetTime() {
		return strRetTime;
	}
/**
 * 
 * @param strRetTime
 */
	public void setStrRetTime(String strRetTime) {
		this.strRetTime = strRetTime;
	}

/**
 * @return Returns the transactionDetailsPage.
 */
public Page<ULDTransactionDetailsVO> getTransactionDetailsPage() {
	return this.transactionDetailsPage;
}

/**
 * @param transactionDetailsPage The transactionDetailsPage to set.
 */
public void setTransactionDetailsPage(
		Page<ULDTransactionDetailsVO> transactionDetailsPage) {
	this.transactionDetailsPage = transactionDetailsPage;
}

/**
 * @return Returns the isToBePrinted.
 */
public boolean isToBePrinted() {
	return this.isToBePrinted;
}

/**
 * @param isToBePrinted The isToBePrinted to set.
 */
public void setToBePrinted(boolean isToBePrinted) {
	this.isToBePrinted = isToBePrinted;
}
   

}
