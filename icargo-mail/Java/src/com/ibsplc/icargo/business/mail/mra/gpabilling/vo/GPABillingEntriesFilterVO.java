/*
 * GPABillingEntriesFilterVO.java Created on Jan 8, 2007
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
/**
 * @author A-2408
 *
 */
public class GPABillingEntriesFilterVO extends AbstractVO {

    private String companyCode;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String gpaCode;
    private String countryCode;
    //added for report
    private String gpaName;
    //possible values are BB(Billable),BD(Billed),OH(on Hold)
    private String billingStatus;
    
    //added by A-3434 for Pagination
    private int pageNumber;
    private int absoluteIndex;
    
    //Added by A-4809 for Bug ICRD-17509 filter values 
    private String conDocNumber;
	private String dsnNumber;
	//Added by A-5175 for CR ICRD-21098
	private int totalRecordCount;

	private String origin;		
	private String destination;	 
	private String isUSPSPerformed; //Added by A-7871 for ICRD-232381
	private int defaultPageSize;
	private String rateFilter;
	private String paBuilt ;
	
	
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String assignedStatus;
	private String assignee;
	private String flightDetails;
	private String exceptionCode;
	private long mailSequenceNumber;
	private String fromScreen;
	private String transferPA;
	private String transferAirline;
	private String mailSeqNumbers;
	public String getTxnCode() {
		return txnCode;
	}
	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}
	public int getTxnSerialNum() {
		return txnSerialNum;
	}
	public void setTxnSerialNum(int txnSerialNum) {
		this.txnSerialNum = txnSerialNum;
	}
	private String txnCode;
	private int txnSerialNum;
	
	
	
	/**
	 * @return the isUSPSPerformed
	 */
	public String getIsUSPSPerformed() {
		return isUSPSPerformed;
	}
	/**
	 * @param isUSPSPerformed the isUSPSPerformed to set
	 */
	public void setIsUSPSPerformed(String isUSPSPerformed) {
		this.isUSPSPerformed = isUSPSPerformed;
	}
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	public String getMailSubclass() {
		return mailSubclass;
	}
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getHni() {
		return hni;
	}
	public void setHni(String hni) {
		this.hni = hni;
	}
	public String getRegInd() {
		return regInd;
	}
	public void setRegInd(String regInd) {
		this.regInd = regInd;
	}
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private String mailCategoryCode;
	private String mailSubclass;
	private String year;
	
	private String rsn;
	private String hni;
	private String regInd;
	private String mailbagId;//Added as part of ICRD-205027
	
//Added by A-6991 for ICRD-137019
	private String ContractRate;
	private String UPURate;
	private String fromCsgGroup;
	private String mcaIndicator;
	private String currencyCode;
	private String mcaNumber;
	
	
    /**
	 * 	Getter for contractRate 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public String getContractRate() {
		return ContractRate;
	}
	/**
	 *  @param contractRate the contractRate to set
	 * 	Setter for contractRate 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public void setContractRate(String contractRate) {
		ContractRate = contractRate;
	}
	/**
	 * 	Getter for uPURate 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public String getUPURate() {
		return UPURate;
	}
	/**
	 *  @param uPURate the uPURate to set
	 * 	Setter for uPURate 
	 *	Added by : A-6991 on 05-Apr-2017
	 * 	Used for : ICRD-137019
	 */
	public void setUPURate(String uPURate) {
		UPURate = uPURate;
	}
	/**
	 * 	Getter for totalRecordCount 
	 *	Added by : A-5175 on 15-Oct-2012
	 * 	Used for :CR ICRD-21098
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 *  @param totalRecordCount the totalRecordCount to set
	 * 	Setter for totalRecordCount 
	 *	Added by : A-5175 on 15-Oct-2012
	 * 	Used for :CR ICRD-210980
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}
	/**
	 * @return the conDocNumber
	 */
	public String getConDocNumber() {
		return conDocNumber;
	}
	/**
	 * @param conDocNumber the conDocNumber to set
	 */
	public void setConDocNumber(String conDocNumber) {
		this.conDocNumber = conDocNumber;
	}
	/**
	 * @return the dsnNumber
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}
	/**
	 * @param dsnNumber the dsnNumber to set
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
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
	/**
     * Possible values are BLB(Billable),BLD(Billed),HLD(on Hold)
     * @return Returns the billingStatus.
     */
    public String getBillingStatus() {
        return billingStatus;
    }
    /**
     * Possible values are BLB(Billable),BLD(Billed),HLD(on Hold)
     * @param billingStatus The billingStatus to set.
     */
    public void setBillingStatus(String billingStatus) {
        this.billingStatus = billingStatus;
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
     * @return Returns the countryCode.
     */
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode The countryCode to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
    /**
     * @return Returns the fromDate.
     */
    public LocalDate getFromDate() {
        return fromDate;
    }
    /**
     * @param fromDate The fromDate to set.
     */
    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
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
     * @return Returns the toDate.
     */
    public LocalDate getToDate() {
        return toDate;
    }
    /**
     * @param toDate The toDate to set.
     */
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
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
	 * 	Getter for mailbagId 
	 *	Added by : A-6991 on 15-Dec-2017
	 * 	Used for :
	 */
	public String getMailbagId() {
		return mailbagId;
	}
	public String getMcaNumber() {
		return mcaNumber;
	}
	public void setMcaNumber(String mcaNumber) {
		this.mcaNumber = mcaNumber;
	}
	public String getMcaIndicator() {
		return mcaIndicator;
	}
	public void setMcaIndicator(String mcaIndicator) {
		this.mcaIndicator = mcaIndicator;
	}
	/**
	 *  @param mailbagId the mailbagId to set
	 * 	Setter for mailbagId 
	 *	Added by : A-6991 on 15-Dec-2017
	 * 	Used for :
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	/**
	 * 	Getter for origin 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for :
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 *  @param origin the origin to set
	 * 	Setter for origin 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for :
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * 	Getter for destination 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for : ICRD-258393
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 *  @param destination the destination to set
	 * 	Setter for destination 
	 *	Added by : A-4809 on May 8, 2018
	 * 	Used for : ICRD-258393
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * 	Method		:	GPABillingEntriesFilterVO.getDefaultPageSize
	 *	Added by 	:	A-4809 on Feb 7, 2019
	 * 	Used for 	:   setting pageSize from react screen
	 *	Parameters	:	@return 
	 *	Return type	: 	int
	 */
	public int getDefaultPageSize() {
		return defaultPageSize;
	}
	/**
	 * 	Method		:	GPABillingEntriesFilterVO.setDefaultPageSize
	 *	Added by 	:	A-4809 on Feb 7, 2019
	 * 	Used for 	: setting pageSize from react screen
	 *	Parameters	:	@param defaultPageSize 
	 *	Return type	: 	void
	 */
	public void setDefaultPageSize(int defaultPageSize) {
		this.defaultPageSize = defaultPageSize;
	}
	/**
	 * @return the rateFilter
	 */
	public String getRateFilter() {
		return rateFilter;
	}
	/**
	 * @param rateFilter the rateFilter to set
	 */
	public void setRateFilter(String rateFilter) {
		this.rateFilter = rateFilter;
	}
	/**
	 * @return the fromCsgGroup
	 */
	public String getFromCsgGroup() {
		return fromCsgGroup;
	}
	/**
	 * @param fromCsgGroup the fromCsgGroup to set
	 */
	public void setFromCsgGroup(String fromCsgGroup) {
		this.fromCsgGroup = fromCsgGroup;
	}
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getPaBuilt() {
		return paBuilt;
	}
	public void setPaBuilt(String paBuilt) {
		this.paBuilt = paBuilt;
	}
	/**
	 * 	Getter for carrierCode 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 *  @param carrierCode the carrierCode to set
	 * 	Setter for carrierCode 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * 	Getter for flightNumber 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 *  @param flightNumber the flightNumber to set
	 * 	Setter for flightNumber 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * 	Getter for flightDate 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 *  @param flightDate the flightDate to set
	 * 	Setter for flightDate 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * 	Getter for assignedStatus 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getAssignedStatus() {
		return assignedStatus;
	}
	/**
	 *  @param assignedStatus the assignedStatus to set
	 * 	Setter for assignedStatus 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setAssignedStatus(String assignedStatus) {
		this.assignedStatus = assignedStatus;
	}
	/**
	 * 	Getter for assignee 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getAssignee() {
		return assignee;
	}
	/**
	 *  @param assignee the assignee to set
	 * 	Setter for assignee 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	/**
	 * 	Getter for flightDetails 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getFlightDetails() {
		return flightDetails;
	}
	/**
	 *  @param flightDetails the flightDetails to set
	 * 	Setter for flightDetails 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setFlightDetails(String flightDetails) {
		this.flightDetails = flightDetails;
	}
	/**
	 * 	Getter for exceptionCode 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 *  @param exceptionCode the exceptionCode to set
	 * 	Setter for exceptionCode 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	/**
	 * 	Getter for mailSequenceNumber 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 *  @param mailSequenceNumber the mailSequenceNumber to set
	 * 	Setter for mailSequenceNumber 
	 *	Added by : A-8061 on 15-Dec-2020
	 * 	Used for :
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * 	Getter for fromScreen 
	 *	Added by : A-8061 on 17-Dec-2020
	 * 	Used for :
	 */
	public String getFromScreen() {
		return fromScreen;
	}
	/**
	 *  @param fromScreen the fromScreen to set
	 * 	Setter for fromScreen 
	 *	Added by : A-8061 on 17-Dec-2020
	 * 	Used for :
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}
	/**
	 * 	Getter for transferPA 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public String getTransferPA() {
		return transferPA;
	}
	/**
	 *  @param transferPA the transferPA to set
	 * 	Setter for transferPA 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public void setTransferPA(String transferPA) {
		this.transferPA = transferPA;
	}
	/**
	 * 	Getter for transferAirline 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public String getTransferAirline() {
		return transferAirline;
	}
	/**
	 *  @param transferAirline the transferAirline to set
	 * 	Setter for transferAirline 
	 *	Added by : A-8061 on 30-Dec-2020
	 * 	Used for :
	 */
	public void setTransferAirline(String transferAirline) {
		this.transferAirline = transferAirline;
	}
	/**
	 * @return the mailSeqNumbers
	 */
	public String getMailSeqNumbers() {
		return mailSeqNumbers;
	}
	/**
	 * @param mailSeqNumbers the mailSeqNumbers to set
	 */
	public void setMailSeqNumbers(String mailSeqNumbers) {
		this.mailSeqNumbers = mailSeqNumbers;
	}
	
    
}
