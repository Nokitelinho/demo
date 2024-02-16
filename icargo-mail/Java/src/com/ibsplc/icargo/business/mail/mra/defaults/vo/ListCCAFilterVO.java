/* ListCCAFilterVO.java created on Aug 08, 2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-3429
 * 
 */
public class ListCCAFilterVO extends AbstractVO {

	private String companyCode;

	private String ccaRefNumber;

	private String ccaType;
	private String originOE;
	public String getOriginOE() {
		return originOE;
	}

	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}

	public String getCategoryCode() {
		return categoryCode;
	}

	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	public String getSubClass() {
		return subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
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

	public String getDestinationOE() {
		return destinationOE;
	}

	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
	}

	private String categoryCode;
	private String subClass;
	private String year;
	private String rsn;
	private String hni;
	private String regInd;
	private String destinationOE;
	private String dsn;

	private LocalDate dsnDate;

	private String ccaStatus;

	private String issueParty;
	
	private String arlGpaIndicator;

	private String airlineCode;

	private String gpaCode;

	private String gpaName;

	private LocalDate fromDate;

	private LocalDate toDate;

	private int pageNumber;
	//Added for CR ICRD-7352
	private String origin;
	private String destination;
	
	private int totalRecords;//Added by A-5201 as part for the ICRD-21098
	
	private String billingStatus;
	private String mcacreationtype;//Added by A-7540
	
	public String getMcacreationtype() {
		return mcacreationtype;
	}
	public void setMcacreationtype(String mcacreationtype) {
		this.mcacreationtype = mcacreationtype;
	}

	/**
	 * @return the pageNumber
	 */
	public int getPageNumber() {
		return pageNumber;
	}

	/**
	 * @param pageNumber
	 *            the pageNumber to set
	 */
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	/**
	 * @return the airlineCode
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	
	/**
	 * @param airlineCode
	 *            the airlineCode to set
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return the ccaRefNumber
	 */
	public String getCcaRefNumber() {
		return ccaRefNumber;
	}

	/**
	 * @param ccaRefNumber
	 *            the ccaRefNumber to set
	 */
	public void setCcaRefNumber(String ccaRefNumber) {
		this.ccaRefNumber = ccaRefNumber;
	}

	/**
	 * @return the ccaStatus
	 */
	public String getCcaStatus() {
		return ccaStatus;
	}

	/**
	 * @param ccaStatus
	 *            the ccaStatus to set
	 */
	public void setCcaStatus(String ccaStatus) {
		this.ccaStatus = ccaStatus;
	}

	/**
	 * @return the ccaType
	 */
	public String getCcaType() {
		return ccaType;
	}

	/**
	 * @param ccaType
	 *            the ccaType to set
	 */
	public void setCcaType(String ccaType) {
		this.ccaType = ccaType;
	}

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn
	 *            the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the dsnDate
	 */
	public LocalDate getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            the dsnDate to set
	 */
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return the fromDate
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * @param fromDate
	 *            the fromDate to set
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * @return the gpaName
	 */
	public String getGpaName() {
		return gpaName;
	}

	/**
	 * @param gpaName
	 *            the gpaName to set
	 */
	public void setGpaName(String gpaName) {
		this.gpaName = gpaName;
	}

	/**
	 * @return the issueParty
	 */
	public String getIssueParty() {
		return issueParty;
	}

	/**
	 * @param issueParty
	 *            the issueParty to set
	 */
	public void setIssueParty(String issueParty) {
		this.issueParty = issueParty;
	}

	/**
	 * @return the toDate
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * @return the arlGpaIndicator
	 */
	public String getArlGpaIndicator() {
		return arlGpaIndicator;
	}

	/**
	 * @param arlGpaIndicator the arlGpaIndicator to set
	 */
	public void setArlGpaIndicator(String arlGpaIndicator) {
		this.arlGpaIndicator = arlGpaIndicator;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	//Added by A-5201 as part for the ICRD-21098 starts
	/**
	 * @param setTotalRecords to set total number of records
	*/
	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}
 
    /**
	 * @return the total number of records
	 */
	public int getTotalRecords() {
		return totalRecords;
	}
	//Added by A-5201 as part for the ICRD-21098 end

	/**
	 * 	Getter for billingStatus 
	 *	Added by : A-6991 on 04-Oct-2017
	 * 	Used for : ICRD-211662
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 *  @param billingStatus the billingStatus to set
	 * 	Setter for billingStatus 
	 *	Added by : A-6991 on 04-Oct-2017
	 * 	Used for : ICRD-211662
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
}