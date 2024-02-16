/*
 * GPABillingStatusVO.java Created on Jan 9, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class GPABillingStatusVO extends AbstractVO {

    private String companyCode;
    private String gpaCode;
    private String poaCode;
    private String billingBasis;
    private int csgSequenceNumber;
    private int sequenceNumber;
    private String csgDocumentNumber;
    private String invoiceNumber;
    private LocalDate lastUpdatedTime;
    private String LastUpdateduser;
    private String status;
    private String remarks;
    private String ccaReferenceNo;
    

    /**
	 * @return Returns the ccaReferenceNo.
	 */
	public String getCcaReferenceNo() {
		return ccaReferenceNo;
	}
	/**
	 * @param ccaReferenceNo The ccaReferenceNo to set.
	 */
	public void setCcaReferenceNo(String ccaReferenceNo) {
		this.ccaReferenceNo = ccaReferenceNo;
	}
	
	/**
	 * @return Returns the lastUpdateduser.
	 */
	public String getLastUpdateduser() {
		return LastUpdateduser;
	}
	/**
	 * @param lastUpdateduser The lastUpdateduser to set.
	 */
	public void setLastUpdateduser(String lastUpdateduser) {
		LastUpdateduser = lastUpdateduser;
	}
	/**
	 * @return Returns the invoiceNumber.
	 */
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	/**
	 * @param invoiceNumber The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	/**
	 * @return Returns the csgDocumentNumber.
	 */
	public String getCsgDocumentNumber() {
		return csgDocumentNumber;
	}
	/**
	 * @param csgDocumentNumber The csgDocumentNumber to set.
	 */
	public void setCsgDocumentNumber(String csgDocumentNumber) {
		this.csgDocumentNumber = csgDocumentNumber;
	}
	/**
	 * @return Returns the csgSequenceNumber.
	 */
	public int getCsgSequenceNumber() {
		return csgSequenceNumber;
	}
	/**
	 * @param csgSequenceNumber The csgSequenceNumber to set.
	 */
	public void setCsgSequenceNumber(int csgSequenceNumber) {
		this.csgSequenceNumber = csgSequenceNumber;
	}
	/**
     * @return Returns the billingBasis.
     */
    public String getBillingBasis() {
        return billingBasis;
    }
    /**
     * @param billingBasis The billingBasis to set.
     */
    public void setBillingBasis(String billingBasis) {
        this.billingBasis = billingBasis;
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
	 * @return Returns the poaCode.
	 */
	public String getPoaCode() {
		return poaCode;
	}
	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
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
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 * @param gpaCode The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
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
}
