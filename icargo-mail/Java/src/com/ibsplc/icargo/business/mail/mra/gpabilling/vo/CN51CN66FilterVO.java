/*
 * CN51CN66FilterVO.java Created on Jan 8, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1556
 *
 */
public class CN51CN66FilterVO extends AbstractVO {
    
    private String companyCode;  
    private String billingPeriod;  
    private String gpaCode; 
    private String invoiceNumber;
    private String gpaName;
    private String airlineCode;
    /** Added by A-5526 as part of BUG ICRD-64187*/
    private String category;
    private String orgin;
    private String destination;
    
    private int sequenceNumber;
    private String billingBasis;
	private String consigneeDocumentNumber;
	
	private int consigneeSequenceNumber;
	private String chargeHead;
	private String baseCurrency;
	private String mcaNumber;
	private String dsnNumber;
	private long mailSeqnum;//added by a-7871 for ICRD-214766
	private String airlineName;
	private String rebillInvNumber;
	private int rebillRound;
    /**
	 * @return the mailSeqnum
	 */
	public long getMailSeqnum() {
		return mailSeqnum;
	}
	/**
	 * @param mailSeqnum the mailSeqnum to set
	 */
	public void setMailSeqnum(long mailSeqnum) {
		this.mailSeqnum = mailSeqnum;
	}
    /**
     * 
     * @return category
     */
    public String getCategory() {
		return category;
	}
    /**
     * 
     * @param category
     */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * 
	 * @return orgin
	 */
	public String getOrgin() {
		return orgin;
	}
	/**
	 * 
	 * @param orgin
	 */
	public void setOrgin(String orgin) {
		this.orgin = orgin;
	}
	/**
	 * 
	 * @return destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * 
	 * @param destination
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	 /** Ends the changes as part of BUG ICRD-64187*/
	
    /**
	 * @return the recordSize
	 */
	public int getRecordSize() {
		return recordSize;
	}
	/**
	 * @param recordSize the recordSize to set
	 */
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	private int recordSize;
	private int totalRecordCount;
    /**
	 * @return the absoluteIndex
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex the absoluteIndex to set
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
	/**
	 * @return the totalRecords
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	/**
	 * @param totalRecords the totalRecords to set
	 */
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
	private int absoluteIndex;
    private int totalRecords;
    private int totalRecordsUnlabelled;
	/**
	 * @return the totalRecordsUnlabelled
	 */
	public int getTotalRecordsUnlabelled() {
		return totalRecordsUnlabelled;
	}
	/**
	 * @param totalRecordsUnlabelled the totalRecordsUnlabelled to set
	 */
	public void setTotalRecordsUnlabelled(int totalRecordsUnlabelled) {
		this.totalRecordsUnlabelled = totalRecordsUnlabelled;
	}
	
	private int pageNumberCn66;
    /**
	 * @return the pageNumberCn66
	 */
	public int getPageNumberCn66() {
		return pageNumberCn66;
	}
	/**
	 * @param pageNumberCn66 the pageNumberCn66 to set
	 */
	public void setPageNumberCn66(int pageNumberCn66) {
		this.pageNumberCn66 = pageNumberCn66;
	}
	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}
	/**
	 * @param pageNumber the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	private int pageNumber;
    
    
    
    /**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}
	/**
	 * @param airlineCode The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}
	/**
	 * @return Returns the gpaName.
	 */
	public String getGpaName() {
		return gpaName;
	}
	/**
	 * @param gpaName The gpaName to set.
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}
	/**
     * @return Returns the billingPeriod.
     */
    public String getBillingPeriod() {
        return billingPeriod;
    }
    /**
     * @param billingPeriod The billingPeriod to set.
     */
    public void setBillingPeriod(String billingPeriod) {
        this.billingPeriod = billingPeriod;
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
	 * @return the sequenceNumber
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}
	/**
	 * @param billingBasis the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	/**
	 * @return the consigneeDocumentNumber
	 */
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}
	/**
	 * @param consigneeDocumentNumber the consigneeDocumentNumber to set
	 */
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}
	
	/**
	 * @return the consigneeSequenceNumber
	 */
	public int getConsigneeSequenceNumber() {
		return consigneeSequenceNumber;
	}
	/**
	 * @param consigneeSequenceNumber the consigneeSequenceNumber to set
	 */
	public void setConsigneeSequenceNumber(int consigneeSequenceNumber) {
		this.consigneeSequenceNumber = consigneeSequenceNumber;
	}
	/**
	 * @return the chargeHead
	 */
	public String getChargeHead() {
		return chargeHead;
	}
	/**
	 * @param chargeHead the chargeHead to set
	 */
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}
	/**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}
	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}
	public String getMcaNumber() {
		return mcaNumber;
	}
	public void setMcaNumber(String mcaNumber) {
		this.mcaNumber = mcaNumber;
	}
	/**
	 * 	Getter for dsnNumber 
	 *	Added by : A-6991 on 30-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}
	/**
	 *  @param dsnNumber the dsnNumber to set
	 * 	Setter for dsnNumber 
	 *	Added by : A-6991 on 30-Aug-2017
	 * 	Used for : ICRD-211662
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}
	public String getAirlineName() {
		return airlineName;
	}
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}
	/**
	 * @return the rebillInvNumber
	 */
	public String getRebillInvNumber() {
		return rebillInvNumber;
	}
	/**
	 * @param rebillInvNumber the rebillInvNumber to set
	 */
	public void setRebillInvNumber(String rebillInvNumber) {
		this.rebillInvNumber = rebillInvNumber;
	}
	/**
	 * @return the rebillRound
	 */
	public int getRebillRound() {
		return rebillRound;
	}
	/**
	 * @param rebillRound the rebillRound to set
	 */
	public void setRebillRound(int rebillRound) {
		this.rebillRound = rebillRound;
	}
    
}
