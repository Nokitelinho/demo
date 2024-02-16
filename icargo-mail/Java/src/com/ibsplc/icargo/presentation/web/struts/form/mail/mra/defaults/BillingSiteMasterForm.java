/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteForm.java
 *
 *	Created by	:	A-5219
 *	Created on	:	15-Nov-2013
 *
 *  Copyright 2013 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;

/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults.BillingSiteForm.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5219	:	15-Nov-2013	:	Draft
 */
public class BillingSiteMasterForm extends ScreenModel{
	
	/** The Constant BUNDLE. */
	private static final String BUNDLE = "billingsiteresource";

	/** The Constant PRODUCT. */
	private static final String PRODUCT = "mail";
	
	/** The Constant SUBPRODUCT. */
	private static final String SUBPRODUCT = "mra";
	
	/** The Constant SCREENID. */
	private static final String SCREENID = "mailtracking.mra.defaults.billingsitemaster";
	
	/** The billing site code. */
	private String billingSiteCode;
	
	/** The billing site. */
	private String billingSite;
	
	/** The from date. */
	private String fromDate;
	
	/** The to date. */
	private String toDate;
	
	/** The airline address. */
	private String airlineAddress;
	
	/** The correspondence address. */
	private String correspondenceAddress;
	
	/** The gpa countries. */
	private String[] gpaCountries;
	
	/** The currencies. */
	private String[] currencies;
	
	/** The bank name. */
	private String[] bankName;
	
	/** The branch. */
	private String[] branch;
	
	/** The accno. */
	private String[] accno;
	
	/** The city. */
	private String[] city;
	
	/** The country. */
	private String[] country;
	
	/** The swift code. */
	private String[] swiftCode;
	
	/** The iban no. */
	private String[] ibanNo;
	
	/** The signator1. */
	private String signator1;
	
	/** The designation1. */
	private String designation1;
	
	/** The signator2. */
	private String signator2;
	
	/** The designation2. */
	private String designation2;
	
	/** The free text. */
	private String freeText;
	
	/** The hidden op flag for country. */
	private String[] hiddenOpFlagForCountry;
	
	/** The hidden op flag for bank. */
	private String[] hiddenOpFlagForBank;
	
	/** The check for country. */
	private String[] checkForCountry;
	
	/** The check for bank. */
	private String[] checkForBank;
	
	/** The serial number for country. */
	private String[] serialNumberForCountry;
	
	/** The serial number for bank. */
	private String[] serialNumberForBank;
	
	/** The status. */
	private String status;
	
	/** The site expired. */
	private String siteExpired;
	
	/** The current dialog option. */
	private String currentDialogOption;
	
	/** The current dialog id. */
	private String currentDialogId;
	
	/**
	 * Gets the serial number for country.
	 *
	 * @return the serialNumberForCountry
	 */
	public String[] getSerialNumberForCountry() {
		return serialNumberForCountry;
	}

	/**
	 * Sets the serial number for country.
	 *
	 * @param serialNumberForCountry the serialNumberForCountry to set
	 */
	public void setSerialNumberForCountry(String[] serialNumberForCountry) {
		this.serialNumberForCountry = serialNumberForCountry;
	}

	/**
	 * Gets the serial number for bank.
	 *
	 * @return the serialNumberForBank
	 */
	public String[] getSerialNumberForBank() {
		return serialNumberForBank;
	}

	/**
	 * Sets the serial number for bank.
	 *
	 * @param serialNumberForBank the serialNumberForBank to set
	 */
	public void setSerialNumberForBank(String[] serialNumberForBank) {
		this.serialNumberForBank = serialNumberForBank;
	}

	/** The check all countries box. */
	private String[] checkAllCountriesBox;
	
	/** The check all banks box. */
	private String[] checkAllBanksBox;
	
	/** The check flag. */
	private String checkFlag;

	/**
	 * Getter for billingSiteCode
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}

	/**
	 * Getter for billingSite
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setBillingSite(String billingSite) {
		this.billingSite = billingSite;
	}

	/**
	 * Getter for fromDate
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the from date
	 */
	public String getFromDate() {
		return fromDate;
	}

	/**
	 * Sets the from date.
	 *
	 * @param fromDate the fromDate to set
	 * Setter for fromDate
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	/**
	 * Getter for toDate
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the to date
	 */
	public String getToDate() {
		return toDate;
	}

	/**
	 * Sets the to date.
	 *
	 * @param toDate the toDate to set
	 * Setter for toDate
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	/**
	 * Getter for airlineAddress
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setAirlineAddress(String airlineAddress) {
		this.airlineAddress = airlineAddress;
	}

	/**
	 * Getter for correspondenceAddress
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCorrespondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}

	/**
	 * Getter for gpaCountries
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the gpa countries
	 */
	public String[] getGpaCountries() {
		return gpaCountries;
	}

	/**
	 * Sets the gpa countries.
	 *
	 * @param gpaCountries the gpaCountries to set
	 * Setter for gpaCountries
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setGpaCountries(String[] gpaCountries) {
		this.gpaCountries = gpaCountries;
	}

	/**
	 * Getter for currencies
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the currencies
	 */
	public String[] getCurrencies() {
		return currencies;
	}

	/**
	 * Sets the currencies.
	 *
	 * @param currencies the currencies to set
	 * Setter for currencies
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCurrencies(String[] currencies) {
		this.currencies = currencies;
	}

	/**
	 * Getter for bankName
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the bank name
	 */
	public String[] getBankName() {
		return bankName;
	}

	/**
	 * Sets the bank name.
	 *
	 * @param bankName the bankName to set
	 * Setter for bankName
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setBankName(String[] bankName) {
		this.bankName = bankName;
	}

	/**
	 * Getter for branch
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the branch
	 */
	public String[] getBranch() {
		return branch;
	}

	/**
	 * Sets the branch.
	 *
	 * @param branch the branch to set
	 * Setter for branch
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setBranch(String[] branch) {
		this.branch = branch;
	}

	/**
	 * Getter for accno
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the accno
	 */
	public String[] getAccno() {
		return accno;
	}

	/**
	 * Sets the accno.
	 *
	 * @param accno the accno to set
	 * Setter for accno
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setAccno(String[] accno) {
		this.accno = accno;
	}

	/**
	 * Getter for city
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the city
	 */
	public String[] getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the city to set
	 * Setter for city
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCity(String[] city) {
		this.city = city;
	}

	/**
	 * Getter for country
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the country
	 */
	public String[] getCountry() {
		return country;
	}

	/**
	 * Sets the country.
	 *
	 * @param country the country to set
	 * Setter for country
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCountry(String[] country) {
		this.country = country;
	}

	/**
	 * Getter for swiftCode
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the swift code
	 */
	public String[] getSwiftCode() {
		return swiftCode;
	}

	/**
	 * Sets the swift code.
	 *
	 * @param swiftCode the swiftCode to set
	 * Setter for swiftCode
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setSwiftCode(String[] swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * Getter for ibanNo
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the iban no
	 */
	public String[] getIbanNo() {
		return ibanNo;
	}

	/**
	 * Sets the iban no.
	 *
	 * @param ibanNo the ibanNo to set
	 * Setter for ibanNo
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setIbanNo(String[] ibanNo) {
		this.ibanNo = ibanNo;
	}

	/**
	 * Getter for signator1
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setSignator1(String signator1) {
		this.signator1 = signator1;
	}

	/**
	 * Getter for designation1
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the designation1
	 */
	public String getDesignation1() {
		return designation1;
	}

	/**
	 * Sets the designation1.
	 *
	 * @param designation1 the designation1 to set
	 * Setter for designation1
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setDesignation1(String designation1) {
		this.designation1 = designation1;
	}

	/**
	 * Getter for signator2
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setSignator2(String signator2) {
		this.signator2 = signator2;
	}

	/**
	 * Getter for designation2
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the designation2
	 */
	public String getDesignation2() {
		return designation2;
	}

	/**
	 * Sets the designation2.
	 *
	 * @param designation2 the designation2 to set
	 * Setter for designation2
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setDesignation2(String designation2) {
		this.designation2 = designation2;
	}

	/**
	 * Getter for freeText
	 * Added by : A-5219 on 18-Nov-2013
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
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}

	/**
	 * Getter for hiddenOpFlagForCountry
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the hidden op flag for country
	 */
	public String[] getHiddenOpFlagForCountry() {
		return hiddenOpFlagForCountry;
	}

	/**
	 * Sets the hidden op flag for country.
	 *
	 * @param hiddenOpFlagForCountry the hiddenOpFlagForCountry to set
	 * Setter for hiddenOpFlagForCountry
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setHiddenOpFlagForCountry(String[] hiddenOpFlagForCountry) {
		this.hiddenOpFlagForCountry = hiddenOpFlagForCountry;
	}

	/**
	 * Getter for hiddenOpFlagForBank
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the hidden op flag for bank
	 */
	public String[] getHiddenOpFlagForBank() {
		return hiddenOpFlagForBank;
	}

	/**
	 * Sets the hidden op flag for bank.
	 *
	 * @param hiddenOpFlagForBank the hiddenOpFlagForBank to set
	 * Setter for hiddenOpFlagForBank
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setHiddenOpFlagForBank(String[] hiddenOpFlagForBank) {
		this.hiddenOpFlagForBank = hiddenOpFlagForBank;
	}

	/**
	 * Getter for checkForCountry
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the check for country
	 */
	public String[] getCheckForCountry() {
		return checkForCountry;
	}

	/**
	 * Sets the check for country.
	 *
	 * @param checkForCountry the checkForCountry to set
	 * Setter for checkForCountry
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCheckForCountry(String[] checkForCountry) {
		this.checkForCountry = checkForCountry;
	}

	/**
	 * Getter for checkForBank
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the check for bank
	 */
	public String[] getCheckForBank() {
		return checkForBank;
	}

	/**
	 * Sets the check for bank.
	 *
	 * @param checkForBank the checkForBank to set
	 * Setter for checkForBank
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCheckForBank(String[] checkForBank) {
		this.checkForBank = checkForBank;
	}

	/**
	 * Getter for checkAllCountriesBox
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the check all countries box
	 */
	public String[] getCheckAllCountriesBox() {
		return checkAllCountriesBox;
	}

	/**
	 * Sets the check all countries box.
	 *
	 * @param checkAllCountriesBox the checkAllCountriesBox to set
	 * Setter for checkAllCountriesBox
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCheckAllCountriesBox(String[] checkAllCountriesBox) {
		this.checkAllCountriesBox = checkAllCountriesBox;
	}

	/**
	 * Getter for checkAllBanksBox
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the check all banks box
	 */
	public String[] getCheckAllBanksBox() {
		return checkAllBanksBox;
	}

	/**
	 * Sets the check all banks box.
	 *
	 * @param checkAllBanksBox the checkAllBanksBox to set
	 * Setter for checkAllBanksBox
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :
	 */
	public void setCheckAllBanksBox(String[] checkAllBanksBox) {
		this.checkAllBanksBox = checkAllBanksBox;
	}

	/**
	 * Getter for bundle
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the bundle
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * Getter for product
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the product
	 */
	public String getProduct() {
		return PRODUCT;
	}

	/**
	 * Getter for subproduct
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the sub product
	 */
	public String getSubProduct() {
		return SUBPRODUCT;
	}

	/**
	 * Getter for screenid
	 * Added by : A-5219 on 18-Nov-2013
	 * Used for :.
	 *
	 * @return the screen id
	 */
	public String getScreenId() {
		return SCREENID;
	}
	
	/**
	 * Sets the check flag.
	 *
	 * @param checkFlag the checkFlag to set
	 */
	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	/**
	 * Gets the check flag.
	 *
	 * @return the checkFlag
	 */
	public String getCheckFlag() {
		return checkFlag;
	}

	/**
	 * Reset.
	 */
	public void reset(){
		billingSiteCode="";
		
		 billingSite="";
		
		 fromDate="";
		
		 toDate="";
		
		 airlineAddress="";
		
		 correspondenceAddress="";
		
		 gpaCountries=null;
		
		 currencies=null;
		
		 bankName=null;
		
		 branch=null;
		
		 accno=null;
		
		 city=null;
		
		 country=null;
		
		 swiftCode=null;
		
		 ibanNo=null;
		
		 signator1="";
		
		 designation1="";
		
		 signator2="";
		
		 designation2="";
		
		 freeText="";
		 
		 checkFlag="";
		 
		 siteExpired="";
		 
		 status="";
		 
		 currentDialogOption="";
	}

	/**
	 * Sets the status.
	 *
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the site expired.
	 *
	 * @param siteExpired the siteExpired to set
	 */
	public void setSiteExpired(String siteExpired) {
		this.siteExpired = siteExpired;
	}

	/**
	 * Gets the site expired.
	 *
	 * @return the siteExpired
	 */
	public String getSiteExpired() {
		return siteExpired;
	}

	/**
	 * Sets the current dialog option.
	 *
	 * @param currentDialogOption the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * Gets the current dialog option.
	 *
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * Sets the current dialog id.
	 *
	 * @param currentDialogId the currentDialogId to set
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * Gets the current dialog id.
	 *
	 * @return the currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

}