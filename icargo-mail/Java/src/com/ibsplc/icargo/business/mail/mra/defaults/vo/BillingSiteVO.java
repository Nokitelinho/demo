/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainBillingSiteVO.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.ArrayList;
import java.util.Calendar;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.MaintainBillingSiteVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteVO extends AbstractVO{
	
	/** The company code. */
	private String companyCode;
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The billing site. */
	private String billingSite;
	
	/** The airline address. */
	private String airlineAddress;
	
	/** The correspondence address. */
	private String correspondenceAddress;
	
	/** The signator1. */
	private String signator1;
	
	/** The designator1. */
	private String designator1;
	
	/** The signator2. */
	private String signator2;
	
	/** The designator2. */
	private String designator2;
	
	/** The free text. */
	private String freeText;
	
	/** The from date. */
	private LocalDate fromDate;
	
	/** The to date. */
	private LocalDate toDate;
	
	/** The last updated user. */
	private String lastUpdatedUser;
	
	/** The last updated time. */
	private Calendar lastUpdatedTime;
	
	/** The operation flag. */
	private String operationFlag;
	
	/** The is auto generate. */
	private boolean isAutoGenerate;
	
	/** The serial number. */
	private int serialNumber;
	
	/** The gpa country. */
	private String gpaCountry;
	
	/** The is overlapping. */
	private boolean isOverlapping;
	
	/** The is duplicate. */
	private boolean isDuplicate;
	
	/** The is updated. */
	private boolean isUpdated;
	
	/** The new field value. */
	private String newFieldValue;
	
	/** The generated billing site code. */
	private String generatedBillingSiteCode;
	
	/** The is error trown. */
	private boolean isErrorTrown;
	
	/** The maintain billing site bank details vo. */
	private ArrayList<BillingSiteBankDetailsVO> billingSiteBankDetailsVO;
	
	/** The maintain billing site bank details vo. */
	private ArrayList<BillingSiteGPACountriesVO> billingSiteGPACountriesVO;

	/**
	 * Getter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the company code
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * Sets the company code.
	 *
	 * @param companyCode the companyCode to set
	 * Setter for companyCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Getter for billingSiteCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site code
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}

	/**
	 * Sets the billing site code.
	 *
	 * @param billingSiteCode the billingSiteCode to set
	 * Setter for billingSiteCode
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}

	/**
	 * Getter for billingSite
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site
	 */
	public String getBillingSite() {
		return billingSite;
	}

	/**
	 * Sets the billing site.
	 *
	 * @param billingSite the billingSite to set
	 * Setter for billingSite
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBillingSite(String billingSite) {
		this.billingSite = billingSite;
	}

	/**
	 * Getter for airlineAddress
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the airline address
	 */
	public String getAirlineAddress() {
		return airlineAddress;
	}

	/**
	 * Sets the airline address.
	 *
	 * @param airlineAddress the airlineAddress to set
	 * Setter for airlineAddress
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setAirlineAddress(String airlineAddress) {
		this.airlineAddress = airlineAddress;
	}

	/**
	 * Getter for correspondenceAddress
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the correspondence address
	 */
	public String getCorrespondenceAddress() {
		return correspondenceAddress;
	}

	/**
	 * Sets the correspondence address.
	 *
	 * @param correspondenceAddress the correspondenceAddress to set
	 * Setter for correspondenceAddress
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setCorrespondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}

	/**
	 * Getter for signator1
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the signator1
	 */
	public String getSignator1() {
		return signator1;
	}

	/**
	 * Sets the signator1.
	 *
	 * @param signator1 the signator1 to set
	 * Setter for signator1
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setSignator1(String signator1) {
		this.signator1 = signator1;
	}

	/**
	 * Getter for designator1
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the designator1
	 */
	public String getDesignator1() {
		return designator1;
	}

	/**
	 * Sets the designator1.
	 *
	 * @param designator1 the designator1 to set
	 * Setter for designator1
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setDesignator1(String designator1) {
		this.designator1 = designator1;
	}

	/**
	 * Getter for signator2
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the signator2
	 */
	public String getSignator2() {
		return signator2;
	}

	/**
	 * Sets the signator2.
	 *
	 * @param signator2 the signator2 to set
	 * Setter for signator2
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setSignator2(String signator2) {
		this.signator2 = signator2;
	}

	/**
	 * Getter for designator2
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the designator2
	 */
	public String getDesignator2() {
		return designator2;
	}

	/**
	 * Sets the designator2.
	 *
	 * @param designator2 the designator2 to set
	 * Setter for designator2
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setDesignator2(String designator2) {
		this.designator2 = designator2;
	}

	/**
	 * Getter for freeText
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the free text
	 */
	public String getFreeText() {
		return freeText;
	}

	/**
	 * Sets the free text.
	 *
	 * @param freeText the freeText to set
	 * Setter for freeText
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	/**
	 * Getter for fromDate
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the from date
	 */
	public LocalDate getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the from date.
	 *
	 * @param fromDate the fromDate to set
	 * Setter for fromDate
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Getter for toDate
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the to date
	 */
	public LocalDate getToDate() {
		return toDate;
	}

	/**
	 * Sets the to date.
	 *
	 * @param toDate the toDate to set
	 * Setter for toDate
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	/**
	 * Sets the last updated user.
	 *
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * Gets the last updated user.
	 *
	 * @return the lastUpdatedUser
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * Sets the last updated time.
	 *
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * Gets the last updated time.
	 *
	 * @return the lastUpdatedTime
	 */
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * Sets the operation flag.
	 *
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * Gets the operation flag.
	 *
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * Sets the maintain billing site bank details vo.
	 *
	 * @param billingSiteBankDetailsVO the billingSiteBankDetailsVO to set
	 * Setter for billingSiteBankDetailsVO
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteBankDetailsVO(
			ArrayList<BillingSiteBankDetailsVO> billingSiteBankDetailsVO) {
		this.billingSiteBankDetailsVO = billingSiteBankDetailsVO;
	}

	/**
	 * Getter for billingSiteBankDetailsVO
	 * Added by : A-5219 on 15-Nov-2013
	 * Used for :.
	 *
	 * @return the billing site bank details vo
	 */
	public ArrayList<BillingSiteBankDetailsVO> getBillingSiteBankDetailsVO() {
		return billingSiteBankDetailsVO;
	}

	/**
	 * Sets the maintain billing site bank details vo.
	 *
	 * @param billingSiteGPACountriesVO the billingSiteGPACountriesVO to set
	 * Setter for billingSiteGPACountriesVO
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteGPACountriesVO(
			ArrayList<BillingSiteGPACountriesVO> billingSiteGPACountriesVO) {
		this.billingSiteGPACountriesVO = billingSiteGPACountriesVO;
	}

	/**
	 * Getter for billingSiteGPACountriesVO
	 * Added by : A-5219 on 19-Nov-2013
	 * Used for :.
	 *
	 * @return the maintain billing site bank details vo
	 */
	public ArrayList<BillingSiteGPACountriesVO> getBillingSiteGPACountriesVO() {
		return billingSiteGPACountriesVO;
	}

	/**
	 * Sets the auto generate.
	 *
	 * @param isAutoGenerate the isAutoGenerate to set
	 */
	public void setAutoGenerate(boolean isAutoGenerate) {
		this.isAutoGenerate = isAutoGenerate;
	}

	/**
	 * Checks if is auto generate.
	 *
	 * @return the isAutoGenerate
	 */
	public boolean isAutoGenerate() {
		return isAutoGenerate;
	}

	/**
	 * Sets the serial number.
	 *
	 * @param serialNumber the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * Gets the serial number.
	 *
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * Sets the gpa country.
	 *
	 * @param gpaCountry the gpaCountry to set
	 */
	public void setGpaCountry(String gpaCountry) {
		this.gpaCountry = gpaCountry;
	}

	/**
	 * Gets the gpa country.
	 *
	 * @return the gpaCountry
	 */
	public String getGpaCountry() {
		return gpaCountry;
	}

	/**
	 * Sets the overlapping.
	 *
	 * @param isOverlapping the isOverlapping to set
	 */
	public void setOverlapping(boolean isOverlapping) {
		this.isOverlapping = isOverlapping;
	}

	/**
	 * Checks if is overlapping.
	 *
	 * @return the isOverlapping
	 */
	public boolean isOverlapping() {
		return isOverlapping;
	}

	/**
	 * Sets the duplicate.
	 *
	 * @param isDuplicate the isDuplicate to set
	 */
	public void setDuplicate(boolean isDuplicate) {
		this.isDuplicate = isDuplicate;
	}

	/**
	 * Checks if is duplicate.
	 *
	 * @return the isDuplicate
	 */
	public boolean isDuplicate() {
		return isDuplicate;
	}


	/**
	 * Sets the updated.
	 *
	 * @param isUpdated the isUpdated to set
	 */
	public void setUpdated(boolean isUpdated) {
		this.isUpdated = isUpdated;
	}

	/**
	 * Checks if is updated.
	 *
	 * @return the isUpdated
	 */
	public boolean isUpdated() {
		return isUpdated;
	}

	/**
	 * Sets the new field value.
	 *
	 * @param newFieldValue the newFieldValue to set
	 */
	public void setNewFieldValue(String newFieldValue) {
		this.newFieldValue = newFieldValue;
	}

	/**
	 * Gets the new field value.
	 *
	 * @return the newFieldValue
	 */
	public String getNewFieldValue() {
		return newFieldValue;
	}

	/**
	 * Sets the generated billing site code.
	 *
	 * @param generatedBillingSiteCode the new generated billing site code
	 */
	public void setGeneratedBillingSiteCode(String generatedBillingSiteCode) {
		this.generatedBillingSiteCode = generatedBillingSiteCode;
	}

	/**
	 * Gets the generated billing site code.
	 *
	 * @return the generetedBillingSiteCode
	 */
	public String getGeneratedBillingSiteCode() {
		return generatedBillingSiteCode;
	}

	/**
	 * Sets the error trown.
	 *
	 * @param isErrorTrown the isErrorTrown to set
	 */
	public void setErrorTrown(boolean isErrorTrown) {
		this.isErrorTrown = isErrorTrown;
	}

	/**
	 * Checks if is error trown.
	 *
	 * @return the isErrorTrown
	 */
	public boolean isErrorTrown() {
		return isErrorTrown;
	}
	
	

}
