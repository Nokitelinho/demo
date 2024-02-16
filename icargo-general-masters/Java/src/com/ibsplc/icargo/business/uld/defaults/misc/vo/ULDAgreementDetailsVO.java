/*
 * ULDAgreementDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.io.Serializable;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDAgreementDetailsVO extends AbstractVO implements Serializable ,Cloneable{
    
    private String companyCode;
    private String agreementNumber;
    private int sequenceNumber;
    private String uldTypeCode;
    private String station;
    private LocalDate agreementFromDate;
    private LocalDate agreementToDate;
    private int freeLoanPeriod;
    private String demurrageFrequency;
    private double demurrageRate;
    private double tax;
    private String currency;
    private String remark;
    private String operationFlag;
    private int index;
    
    /**
     * 
     */
    public Object clone(){
    	ULDAgreementDetailsVO object = new ULDAgreementDetailsVO();	
    	object.agreementFromDate=this.agreementFromDate;
    	object.station=this.station;
    	object.uldTypeCode=this.uldTypeCode;
    	object.companyCode=this.companyCode;
    	object.agreementToDate=this.agreementToDate;
    	return object;
    }
/**
 * @return int
 */
    public int getIndex() {
		return index;
	}
    /**
     * @param index
     */
	public void setIndex(int index) {
		this.index = index;
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
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    /**
     * @param sequenceNumber The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    /**
     * @return Returns the station.
     */
    public String getStation() {
        return station;
    }
    /**
     * @param station The station to set.
     */
    public void setStation(String station) {
        this.station = station;
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
     * @return Returns the uldTypeCode.
     */
    public String getUldTypeCode() {
        return uldTypeCode;
    }
    /**
     * @param uldTypeCode The uldTypeCode to set.
     */
    public void setUldTypeCode(String uldTypeCode) {
        this.uldTypeCode = uldTypeCode;
    }
}
