/*
 * ULDAgreementVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDAgreementVO extends AbstractVO implements Serializable{
	
	/**
	 * 
	 */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.misc.ULDAgreement";
    //Company Code
    private String companyCode;
    private String agreementNumber;
    private String txnType;
    private String agreementStatus;
    private LocalDate agreementDate;
    private String partyType;
    private String partyCode;
    private String partyName;
    //added as part of ICRD-232684 by A-4393
    private String fromPartyType;
    private String fromPartyCode;
    private String fromPartyName;
    //added as part of ICRD-232684 by A-4393 ends 
    public String getFromPartyType() {
		return fromPartyType;
	}
	public void setFromPartyType(String fromPartyType) {
		this.fromPartyType = fromPartyType;
	}
	public String getFromPartyCode() {
		return fromPartyCode;
	}
	public void setFromPartyCode(String fromPartyCode) {
		this.fromPartyCode = fromPartyCode;
	}
	public String getFromPartyName() {
		return fromPartyName;
	}
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
	}
    private int freeLoanPeriod;
    private LocalDate agreementFromDate;
    private LocalDate agreementToDate;
    private double demurrageRate;
    private String demurrageFrequency;
    private double tax;
    private String currency;
    private String remark;
    private String operationFlag;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	
	private boolean validateFlag; 

    
    //To be reviewed Collection<ULDAgreementDetailsVO>
    private Collection <ULDAgreementDetailsVO> uldAgreementDetailVOs;


  

    /**
     * @return Returns the agreementDate.
     */
    public LocalDate getAgreementDate() {
        return agreementDate;
    }
    /**
     * @param agreementDate The agreementDate to set.
     */
    public void setAgreementDate(LocalDate agreementDate) {
        this.agreementDate = agreementDate;
    }
    /**
     * @return Returns the agreementFromDate.
     */
    public LocalDate getAgreementFromDate() {
        return agreementFromDate;
    }
    /**
     * @param agreementFromDate The agreementFromDate to set.
     */
    public void setAgreementFromDate(LocalDate agreementFromDate) {
        this.agreementFromDate = agreementFromDate;
    }
    /**
     * @return Returns the agreementNumber.
     */
    public String getAgreementNumber() {
        return agreementNumber;
    }
    /**
     * @param agreementNumber The agreementNumber to set.
     */
    public void setAgreementNumber(String agreementNumber) {
        this.agreementNumber = agreementNumber;
    }
    /**
     * @return Returns the agreementStatus.
     */
    public String getAgreementStatus() {
        return agreementStatus;
    }
    /**
     * @param agreementStatus The agreementStatus to set.
     */
    public void setAgreementStatus(String agreementStatus) {
        this.agreementStatus = agreementStatus;
    }
    /**
     * @return Returns the agreementToDate.
     */
    public LocalDate getAgreementToDate() {
        return agreementToDate;
    }
    /**
     * @param agreementToDate The agreementToDate to set.
     */
    public void setAgreementToDate(LocalDate agreementToDate) {
        this.agreementToDate = agreementToDate;
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
     * @return Returns the demurrageFrequency.
     */
    public String getDemurrageFrequency() {
        return demurrageFrequency;
    }
    /**
     * @param demurrageFrequency The demurrageFrequency to set.
     */
    public void setDemurrageFrequency(String demurrageFrequency) {
        this.demurrageFrequency = demurrageFrequency;
    }
    /**
     * @return Returns the demurrageRate.
     */
    public double getDemurrageRate() {
        return demurrageRate;
    }
    /**
     * @param demurrageRate The demurrageRate to set.
     */
    public void setDemurrageRate(double demurrageRate) {
        this.demurrageRate = demurrageRate;
    }
    /**
     * @return Returns the freeLoanPeriod.
     */
    public int getFreeLoanPeriod() {
        return freeLoanPeriod;
    }
    /**
     * @param freeLoanPeriod The freeLoanPeriod to set.
     */
    public void setFreeLoanPeriod(int freeLoanPeriod) {
        this.freeLoanPeriod = freeLoanPeriod;
    }
    /**
     * @return Returns the partyCode.
     */
    public String getPartyCode() {
        return partyCode;
    }
    /**
     * @param partyCode The partyCode to set.
     */
    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
    /**
     * @return Returns the partyName.
     */
    public String getPartyName() {
        return partyName;
    }
    /**
     * @param partyName The partyName to set.
     */
    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }
    /**
     * @return Returns the partyType.
     */
    public String getPartyType() {
        return partyType;
    }
    /**
     * @param partyType The partyType to set.
     */
    public void setPartyType(String partyType) {
        this.partyType = partyType;
    }
    /**
     * @return Returns the remark.
     */
    public String getRemark() {
        return remark;
    }
    /**
     * @param remark The remark to set.
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
    /**
     * @return Returns the tax.
     */
    public double getTax() {
        return tax;
    }
    /**
     * @param tax The tax to set.
     */
    public void setTax(double tax) {
        this.tax = tax;
    }
    /**
     * @return Returns the txnType.
     */
    public String getTxnType() {
        return txnType;
    }
    /**
     * @param txnType The txnType to set.
     */
    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }
    /**
     * @return Returns the uldAgreementDetailVOs.
     */
    public Collection<ULDAgreementDetailsVO> getUldAgreementDetailVOs() {
        return uldAgreementDetailVOs;
    }
    /**
     * @param uldAgreementDetailVOs The uldAgreementDetailVOs to set.
     */
    public void setUldAgreementDetailVOs(Collection<ULDAgreementDetailsVO> uldAgreementDetailVOs) {
        this.uldAgreementDetailVOs = uldAgreementDetailVOs;
    }
    
    /**
     * @return Returns the lastUpdatedTime.
     */
    public LocalDate getLastUpdatedTime() {
        return lastUpdatedTime;
    }
    /**
     * @param lastUpdatedTime The lastUpdatedTime to set.
     */
    public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
        this.lastUpdatedTime = lastUpdatedTime;
    }
    /**
     * @return Returns the lastUpdatedUser.
     */
    public String getLastUpdatedUser() {
        return lastUpdatedUser;
    }
    /**
     * @param lastUpdatedUser The lastUpdatedUser to set.
     */
    public void setLastUpdatedUser(String lastUpdatedUser) {
        this.lastUpdatedUser = lastUpdatedUser;
    }
	/**
	 * @return String
	 */
    public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return boolean Returns the validateFlag.
	 */
	public boolean isValidateFlag() {
		return this.validateFlag;
	}
	/**
	 * @param validateFlag The validateFlag to set.
	 */
	public void setValidateFlag(boolean validateFlag) {
		this.validateFlag = validateFlag;
	}
	
}
