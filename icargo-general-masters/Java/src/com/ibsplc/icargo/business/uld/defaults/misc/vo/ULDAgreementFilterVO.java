/*
 * ULDAgreementFilterVO.java Created on Dec 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1496
 *
 */
public class ULDAgreementFilterVO  extends AbstractVO{
    
    private String companyCode;
//Need to be filled if called from List ULD Agreement    
    private String agreementNumber;
    private String partyType;
    private String partyCode;
   // added as part of ICRD-232684 by A-4393 starts 
    private String fromPartyName;
	private String fromPartyType;
    private String fromPartyCode;
    //added as part of ICRD-232684 by A-4393 ends 
    private String txnType;
//  Need to be filled if called from List ULD Agreement    
    private String agreementStatus;
//  Need to be filled if called from List ULD Agreement    
    private LocalDate agreementDate;
//  Need to be filled if called from List ULD Agreement    
    private LocalDate agreementFromDate;
//  Need to be filled if called from List ULD Agreement    
    private LocalDate agreementToDate;
//  Need to be filled from ULD Txn or from Return ULD Txn
    private String transactionStation;
/*  Need to be filled from ULD Txn or from Return ULD Txn
 * Party name is mandatory if the Party Code is "Others"
 */    
    
    private String uldTypeCode;
    
    private String stationCode;
    
    private String partyName;
//Needs to be filled if from ULD Txn or from Return ULD Txn   
    private LocalDate transactionDate;
//Needs to be filled if from ULD Txn or from Return ULD Txn    
    //To be reviewed Collection<ULDTypeAgreementFilterVO>
    private Collection<ULDTypeAgreementFilterVO> uldTypeAgreementFilterVOs;
    
    private int pageNumber;
    
    //Added By A-5183 For < ICRD-22824 > Starts    
    private int totalRecordsCount;   
    
    //Added by A-8445
    private String uldTypeCodeFilter;
    
    public String getUldTypeCodeFilter() {
		return uldTypeCodeFilter;
	}
	public void setUldTypeCodeFilter(String uldTypeCodeFilter) {
		this.uldTypeCodeFilter = uldTypeCodeFilter;
	}
	
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}	
     //Added By A-5183 For < ICRD-22824 > Ends


    /**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}
	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
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
 	* @return Returns the transactionDate.
 	*/
	public LocalDate getTransactionDate() {
    	return transactionDate;
	}
	/**
 	* @param transactionDate The transactionDate to set.
 	*/
	public void setTransactionDate(LocalDate transactionDate) {
	this.transactionDate = transactionDate;
	}
	/**
 	* @return Returns the transactionStation.
 	*/
	public String getTransactionStation() {
		return transactionStation;
	}
	/**
 	* @param transactionStation The transactionStation to set.
 	*/
	public void setTransactionStation(String transactionStation) {
		this.transactionStation = transactionStation;
	}
	/**
 	* @return Returns the uldTypeAgreementFilterVOs.
 	*/
	public Collection<ULDTypeAgreementFilterVO> getUldTypeAgreementFilterVOs() {
    	return uldTypeAgreementFilterVOs;
	}
	/**
 	* @param uldTypeAgreementFilterVOs The uldTypeAgreementFilterVOs to set.
 	*/
	public void setUldTypeAgreementFilterVOs(Collection<ULDTypeAgreementFilterVO> uldTypeAgreementFilterVOs) {
		this.uldTypeAgreementFilterVOs = uldTypeAgreementFilterVOs;
	}
	/**
 	* @return Returns the pageNumber.
 	*/
	public int getPageNumber() {
		return pageNumber;
	}
	/**
 	* @param pageNumber The pageNumber to set.
 	*/
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getFromPartyName() {
		return fromPartyName;
	}
	public void setFromPartyName(String fromPartyName) {
		this.fromPartyName = fromPartyName;
	}
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
}
