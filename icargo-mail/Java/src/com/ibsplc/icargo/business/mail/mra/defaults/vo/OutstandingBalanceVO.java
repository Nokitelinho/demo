/*
 * OutstandingBalanceVO.java Created on Jan 9,2009
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;




import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.currency.Money;

/**
 * @author A-3434
 *
 */
public class OutstandingBalanceVO extends AbstractVO {
	
	private String companycode;	
	
	private String billingBasis;
	
	private String  consignmentDocumentNumber;
	
	private int consignmentSequenceNumber;
	
	private String poaCode;
	
	private Money debit;
	
    private Money credit;
    
    private String accountName;
    
    private String accountString;
    
    private String currencyCode;
    
    private String subSystem;

	public String getPoaCode() {
		return poaCode;
	}

	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	public String getBillingBasis() {
		return billingBasis;
	}

	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAccountString() {
		return accountString;
	}

	public void setAccountString(String accountString) {
		this.accountString = accountString;
	}

	public Money getCredit() {
		return credit;
	}

	public void setCredit(Money credit) {
		this.credit = credit;
	}

	public Money getDebit() {
		return debit;
	}

	public void setDebit(Money debit) {
		this.debit = debit;
	}

	public String getSubSystem() {
		return subSystem;
	}

	public void setSubSystem(String subSystem) {
		this.subSystem = subSystem;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	
}