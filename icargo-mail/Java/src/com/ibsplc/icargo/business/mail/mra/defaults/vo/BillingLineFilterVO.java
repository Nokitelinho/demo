/*
 * BillingLineFilterVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2280
 *
 */
public class BillingLineFilterVO extends AbstractVO{


    private String companyCode;
    private String billingMatrixId;
	private int absoluteIndex;
	private int pageNumber;
	private String poaCode;
	private String airlineCode;
	private String billingSector;
    private Calendar validityStartDate;
    private Calendar validityEndDate; 
    private String billingLineStatus;  
    private Collection<String> mailCategoryCode;
    private Collection<String> mailSubclass;
    private Collection<String> mailClass;
    private Collection<String> uldType;
    private String originRegion;
    private String originCountry;
    private String originCity;
    private String destinationRegion;
    private String destinationCountry;
    private String destinationCity;   
    private int billingLineId;//Added for ICRD-162338
    private String unitCode;
    private String origin;
    private String destination;
    private String originLevel;
    private String destinationLevel;
    private String uplift;
    private String discharge;
    private String upliftLevel;
    private String dischargeLevel;
    /*
     * Added by A-5497 for ICRD-69455
     */
    private int totalRecordsCount;
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
	 * @return Returns the billingMatrixId.
	 */
	public String getBillingMatrixId() {
		return billingMatrixId;
	}
	/**
	 * @param billingMatrixId The billingMatrixId to set.
	 */
	public void setBillingMatrixId(String billingMatrixId) {
		this.billingMatrixId = billingMatrixId;
	}
	/**
	 * @return Returns the absoluteIndex.
	 */
	public int getAbsoluteIndex() {
		return absoluteIndex;
	}
	/**
	 * @param absoluteIndex The absoluteIndex to set.
	 */
	public void setAbsoluteIndex(int absoluteIndex) {
		this.absoluteIndex = absoluteIndex;
	}
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
	 * @return Returns the billingLineStatus.
	 */
	public String getBillingLineStatus() {
		return billingLineStatus;
	}
	/**
	 * @param billingLineStatus The billingLineStatus to set.
	 */
	public void setBillingLineStatus(String billingLineStatus) {
		this.billingLineStatus = billingLineStatus;
	}
	/**
	 * @return Returns the billingSector.
	 */
	public String getBillingSector() {
		return billingSector;
	}
	/**
	 * @param billingSector The billingSector to set.
	 */
	public void setBillingSector(String billingSector) {
		this.billingSector = billingSector;
	}
	/**
	 * @return Returns the mailCategoryCode.
	 */
	public Collection<String> getMailCategoryCode() {
		return mailCategoryCode;
	}
	/**
	 * @param mailCategoryCode The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(Collection<String> mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}
	/**
	 * @return Returns the mailClass.
	 */
	public Collection<String> getMailClass() {
		return mailClass;
	}
	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(Collection<String> mailClass) {
		this.mailClass = mailClass;
	}
	/**
	 * @return Returns the mailSubclass.
	 */
	public Collection<String> getMailSubclass() {
		return mailSubclass;
	}
	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(Collection<String> mailSubclass) {
		this.mailSubclass = mailSubclass;
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
	 * @return Returns the uldType.
	 */
	public Collection<String> getUldType() {
		return uldType;
	}
	/**
	 * @param uldType The uldType to set.
	 */
	public void setUldType(Collection<String> uldType) {
		this.uldType = uldType;
	}
	/**
	 * @return Returns the validityEndDate.
	 */
	public Calendar getValidityEndDate() {
		return validityEndDate;
	}
	/**
	 * @param validityEndDate The validityEndDate to set.
	 */
	public void setValidityEndDate(Calendar validityEndDate) {
		this.validityEndDate = validityEndDate;
	}
	/**
	 * @return Returns the validityStartDate.
	 */
	public Calendar getValidityStartDate() {
		return validityStartDate;
	}
	/**
	 * @param validityStartDate The validityStartDate to set.
	 */
	public void setValidityStartDate(Calendar validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
	/**
	 * @return Returns the destinationCity.
	 */
	public String getDestinationCity() {
		return destinationCity;
	}
	/**
	 * @param destinationCity The destinationCity to set.
	 */
	public void setDestinationCity(String destinationCity) {
		this.destinationCity = destinationCity;
	}
	/**
	 * @return Returns the destinationCountry.
	 */
	public String getDestinationCountry() {
		return destinationCountry;
	}
	/**
	 * @param destinationCountry The destinationCountry to set.
	 */
	public void setDestinationCountry(String destinationCountry) {
		this.destinationCountry = destinationCountry;
	}
	/**
	 * @return Returns the destinationRegion.
	 */
	public String getDestinationRegion() {
		return destinationRegion;
	}
	/**
	 * @param destinationRegion The destinationRegion to set.
	 */
	public void setDestinationRegion(String destinationRegion) {
		this.destinationRegion = destinationRegion;
	}
	/**
	 * @return Returns the originCity.
	 */
	public String getOriginCity() {
		return originCity;
	}
	/**
	 * @param originCity The originCity to set.
	 */
	public void setOriginCity(String originCity) {
		this.originCity = originCity;
	}
	/**
	 * @return Returns the originCountry.
	 */
	public String getOriginCountry() {
		return originCountry;
	}
	/**
	 * @param originCountry The originCountry to set.
	 */
	public void setOriginCountry(String originCountry) {
		this.originCountry = originCountry;
	}
	/**
	 * @return Returns the originRegion.
	 */
	public String getOriginRegion() {
		return originRegion;
	}
	/**
	 * @param originRegion The originRegion to set.
	 */
	public void setOriginRegion(String originRegion) {
		this.originRegion = originRegion;
	}
	/**
	 * 	Getter for totalRecordsCount 
	 *	Added by : A-5497 on 29-Apr-2014
	 * 	Used for :
	 */
	public int getTotalRecordsCount() {
		return totalRecordsCount;
	}
	/**
	 *  @param totalRecordsCount the totalRecordsCount to set
	 * 	Setter for totalRecordsCount 
	 *	Added by : A-5497 on 29-Apr-2014
	 * 	Used for :
	 */
	public void setTotalRecordsCount(int totalRecordsCount) {
		this.totalRecordsCount = totalRecordsCount;
	}
	/**
	 * @return the billingLineId
	 */
	public int getBillingLineId() {
		return billingLineId;
	}
	/**
	 * @param billingLineId the billingLineId to set
	 */
	public void setBillingLineId(int billingLineId) {
		this.billingLineId = billingLineId;
	}
	public String getUnitCode() {
		return unitCode;
	}
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOriginLevel() {
		return originLevel;
	}

	public void setOriginLevel(String originLevel) {
		this.originLevel = originLevel;
	}

	public String getDestinationLevel() {
		return destinationLevel;
	}

	public void setDestinationLevel(String destinationLevel) {
		this.destinationLevel = destinationLevel;
	}

	public String getUplift() {
		return uplift;
	}

	public void setUplift(String uplift) {
		this.uplift = uplift;
	}

	public String getDischarge() {
		return discharge;
	}

	public void setDischarge(String discharge) {
		this.discharge = discharge;
	}

	public String getUpliftLevel() {
		return upliftLevel;
	}

	public void setUpliftLevel(String upliftLevel) {
		this.upliftLevel = upliftLevel;
	}

	public String getDischargeLevel() {
		return dischargeLevel;
	}

	public void setDischargeLevel(String dischargeLevel) {
		this.dischargeLevel = dischargeLevel;
	}
}
