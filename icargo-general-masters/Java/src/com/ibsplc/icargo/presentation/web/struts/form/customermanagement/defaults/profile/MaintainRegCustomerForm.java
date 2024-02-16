/*
 * MaintainRegCustomerForm.java Created on Dec 19, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.customermanagement.defaults.profile;

import com.ibsplc.icargo.framework.model.ScreenModel;


/**
 * @author A-1496
 *
 */
public class MaintainRegCustomerForm extends ScreenModel {


	private static final String BUNDLE = "maintainregcustomerform";
	private static final String PRODUCT = "customermanagement";
	private static final String SUBPRODUCT = "defaults";
	private static final String SCREENID = "customermanagement.defaults.maintainregcustomer";


	private String bundle;
	private String customerCode;
	private String companyCode;
	private String globalCustomer;
	private String custName;
	private String station;
	private String status;
	private String address1;
	private String address2;
	private String area;
	private String city;
	private String country;
	private String ajaxErrorStatusFlag;
	private String zipCode;
	private String telephone;
	private String mobile;
	private String sita;
	private String email;
	private String fax;
	private String[] hiddenOpFlagForAgent;
	private String[] hiddenOpFlagForCustomer;
	private String creditLimit;
	private String creditAccountNo;
	private String creditPeriod;
	private String creditOutStanding;
	private String creditLimitCurrencyCode;
	
	private String creditOutStandingCurrencyCode;
	private String customerStatus;
	private String creditPeriods;
	private String customerGroup;
	private String zone;
	private String state;//Added by A-3774 
	private String screenStatus="";
	private boolean isFromReservation;//Added by A-2390 for register customer link from reservation screen 
	private boolean isFromPermanentBooking;//added by A-3254 for register customer link permanent booking screen
	private String customerShortCode="";//Added by A-5214 as part from the ICRD-21666
	private String[] hiddenOpFlagForAddtContacts;
	/**
	 * Default Notification Mode
	 * */
	private String defaultNotifyMode="";
	
	private String[] contactCode;
	private String[] contactName;
	private String[] contactDesignation;
	private String[] contactTelephone;
	private String[] contactMobile;
	private String[] contactFax;
	private String[] contactSita;
	private String[] contactEmail;
	private String[] active;
	private String[] primaryContacts;
	private String primaryContact;
	private String[] contactRemarks;
	
	private String[] agentCode;
	private String[] agentName;
	private String[] agentStation;
	private String[] agentRemarks;
	private String[] export;
	private String[] imported;
	private String[] sales;
	private String[] opFlag;
	private String[] agentScc;
	private String[] agentCarrier; // Added by A-8823 for IASCB-4841
	
	private String checkedExport;
	private String checkedImport;
	private String checkedSales;
	private String custCodePresent;
	private String pageURL="";
	private String[] operationContactFlag;
	private String[] operationAgentFlag;
 	
	//pagination variables
	private String lastPageNumber = "0";
	private String displayPage = "1";
	//from customer listing screen
	private String fromCustListing;
	

	
	
	private String[] selectedContactDetails;
	
	private String[] selectedClearingAgentDetails;
	//for showing message on list if vo is null
	private String listStatus;
	
	//for indicating whether the customer is a global customer
	
	private String globalCustomerFlag;
	
	
	private String checkedStatus;
	
	private String[] operationFlag;
	//checkbox variable for setting the values from customer listing screen
	private String[] check;
	//BUG_19590_Tarun_07Oct08
	private String frmCusLisCreate;
	
	//added by a-3045 for CR HA94 on 27Mar09 starts
	private String knownShipper;
	
	private String accountNumber;
	//added by a-3045 for CR HA94 on 27Mar09 ends
	//added by a-3045 for CR HA16 on 27May09 starts
	private String establishedDate;
	private String remarks;
	private String billingStreet;
	private String billingLocation;
	private String billingCityCode;
	private String billingState;
	private String billingCountry;
	private String billingZipcode;
	private String billingTelephone;
	private String billingFax;
	private String billingEmail;
	
	//Added by A-7905 as part of ICRD-228463 starts
	private String billingEmailOne;
	private String billingEmailTwo;
	//Added as part of CR ICRD-253447 by A-8154
	private String stockAutomationRequired;
	private String stockAutomationUserConfirmed;
	private String screenFlag;
	private String duplicateStockHolder;
	private String duplicateStockHolderOverride;
	// Added BY A-8374 as a part of CR ICRD-247693
    private boolean clearingAgentFlag;
    /*Added by A-7567 as part of ICRD-305684*/
    private String cntLocBillingApplicableTo;
    
	public boolean isClearingAgentFlag() {
		return clearingAgentFlag;
	}
	public void setClearingAgentFlag(boolean clearingAgentFlag) {
		this.clearingAgentFlag = clearingAgentFlag;
	}
	  /**
	 * @return the billingEmailOne
	 */
	public String getBillingEmailOne() {
		return billingEmailOne;
	}
	
	
	/**
	 * @param billingEmailOne the billingEmailOne to set
	 */
	public void setBillingEmailOne(String billingEmailOne) {
		this.billingEmailOne = billingEmailOne;
	}
	
	
	/**
	 * @return the billingEmailTwo
	 */
	public String getBillingEmailTwo() {
		return billingEmailTwo;
	}
	
	
	/**
	 * @param billingEmailTwo the billingEmailTwo to set
	 */
	public void setBillingEmailTwo(String billingEmailTwo) {
		this.billingEmailTwo = billingEmailTwo;
	}
	//Added by A-7905 as part of ICRD-228463 ends
	private String iacNumber;
	private String iacExpiryDate;
	private String apiacsspNumber;
	private String apiacsspExpiryDate; 
	private String[] ccsfNumber;
	private String[] ccsfCityName;
	private String[] ccsfExpiryDate;
	private String[] selectedCCSFDetails;
	private String[] hiddenOpFlagForCCSF;
	//added by a-3045 for CR HA16 on 27May09 ends
	//added by a-3045 for CR HA113 on 03Jul09 starts
	private String stopCredit;
	private String invoiceToCustomer;
	//added by a-3045 for CR HA113 on 03Jul09 starts
	//added by a-3045 for bug 53620 on 16Jul09 starts
	private String adminPrivilege;
	//added by a-3045 for bug 53620 on 16Jul09 ends
	
	//CR JetBlue31-1 on 23Apr10
	//private String  billingPeriod;
	private String  billingPeriodDescription;
	private String billPeriod;
	//CR JetBlue 31-1 ends
	
	private String email2;
	private String customerCompanyCode;
	private String branch;
	private String billingCode;
	private String naacsbbAgentCode;
	private String naccsInvoiceCode;
	private String spotForwarder;
	private String defaultHawbLength;
	private String handledCustomerImport="Y";
	private String handledCustomerExport="Y";
	private String handledCustomerForwarder="Y";
	private String forwarderType="FW";
	private String consolidator;
	
	//Added for ICRD-33334 by A-5163 Starts...
	private String invoiceClubbingIndicator;
	//Added for ICRD-33334 by A-5163 Ends...
	
	private String poa;
	private String naccsDeclarationCode;
	private String naccsAircargoAgentCode;
	private String branchName;
	
	private String customerPOAValidity;
	
	private String eoriNo;
	private String billingPeriod="";
	//Added by A-5165	for ICRD-35135
	private String billingDueDays="";
	//added by a-7555 for ICRD-319714
	private String dueDateBasis = "";
	private String scc;
	private String customsLocationNo; //Added by A-5169 for ICRD-31552 on 25-APR-2013
	// Added By A-5183 for ICRD-18283 Starts

	private String vatRegNumber;
	private String fromDate;
	private String toDate;
	private String agent;
	private String customer;
	private String cccollector;
	private String billingTo;
	private String clName;
	private String iataCode;
	private String iataAgentCode;
	private String agentInformation;
	private String cassIdentifier;
	private String settlementCurrency;
	private String salesId;
	private String holdingCompany;
	private String normalComm;
	private String normalCommFixed;
	private String ownSalesFlag;
	private String salesReportAgent;
	private String controllingLocation;
	private String sellingLocation;
	private String proformaInv;
	private String airportCode;
	private String aircraftTypeHandled;
	private String dateOfExchange;
	private boolean cassBillingIndicator;
	private String cassCode;
	private boolean billingThroughInterline;
	private String airlineCode;
	private String enduranceFlag;
	
	//Added by A-5493 for ICRD-17199 to represent the vendor flag
	private String vendorFlag;
	//Added by A-5493 for ICRD-17199 ends
	
	private String  aircraftTypeHandledList;
	
	//Added for ICRD-67442 by A-5163 starts
	private String displayPopupPage;
	private String totalViewRecords;
    private String isHistoryPresent;
    private String isHistoryPopulated;
    private String totalRecords;
    private String[] versionNumbers;
    private String[] navVersionNumbers;
    private String cusVersionNumber;
    private String statusFlag;
    private String isLatestVersion;
	//Added for ICRD-67442 by A-5163 ends
    //Added for ICRD-84538 by A-5223 starts
    private String sourcePage;
    //Added for ICRD-84538 by A-5223 ends
    
	/**
	 * 
	 * @return
	 */
	//Added by A-5791 for ICRD-59602
	private String customerAgentCode;
	private String customerAgentName;
	//Added by A-5220 for ICRD-55852
	private String[] contactTypes;
	
	//added by a-6162 ICRD - 38408
	private String invoiceType ;
	
	//Added for ICRD-58628 by A-5163 starts
	private String[] customerCertificateType;
	private String[] customerCertificateNumber;
	private String[] certificateValidFrom;
	private String[] certificateValidTo;
	private String[] hiddenOpFlagForCertificate;
	private String[] certificateSequenceNumber;
	private String[] rowIdCertifications;
	//Added for ICRD-58628 by A-5163 ends
	
	private String excludeRounding;//Added for ICRD-57704 BY A-5117
	//CRQ ID:117235 - A-5127 added
    private String recipientCode;
    private String cassImportIdentifier;
    //End - CRQ ID:117235 - A-5127 added
    //Added by A-6841 icrd-152555
    private String notificationLanguageCode; 
    private String selectedContactIndex; 
    private String[] eventCode; 
    private String emailFlag; 
    private String mobileFlag; 
    private String faxFlag; 
    private String[] notificationSerialNumber; 
     
    private String[] contactMode; 
    private String[] contactAddress; 
    private String[] additionalContactSerialNumber; 
    private String[] additionalContactsOpFlag; 
    private String[] preferenceCode; 
    private String[] preferenceValue;  
    private String reloadParent;
    private String contactType;
    //CRQ ID: ICRD-162691 - A-5127 added
    private String notificationFormat; 
    private String internalAccountHolder;//Added by A-7534 for ICRD-228903

    //Added by A-7656 for ICRD-242148
    private boolean isCcFeeDueGHA;
     //Added by E-1289 for ICRD-309437 starts
  	private String notifyShipmentType ;
  	//Added by E-1289 for ICRD-309437 ends
	// added by A-8531 for IASCB-IASCB-11603 starts
	private String[] agentType;
	private String[] origin;
	private String[] agnetValidFrom;
	private String[] agnetValidTo;
	// added by A-8531 for IASCB-IASCB-11603 starts
	
	/*Added by 201930 for IASCB-131790 */
	private String cassCountry;
	
	public String[] getAgentType() {
		return agentType;
	}
	public void setAgentType(String[] agentType) {
		this.agentType = agentType;
	}
	public String[] getOrigin() {
		return origin;
	}
	public void setOrigin(String[] origin) {
		this.origin = origin;
	}
	public String[] getAgnetValidFrom() {
		return agnetValidFrom;
	}
	public void setAgnetValidFrom(String[] agnetValidFrom) {
		this.agnetValidFrom = agnetValidFrom;
	}
	public String[] getAgnetValidTo() {
		return agnetValidTo;
	}
	public void setAgnetValidTo(String[] agnetValidTo) {
		this.agnetValidTo = agnetValidTo;
	}
	public String getContactType() {
		return contactType;
	}
	
	
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getReloadParent() {
		return reloadParent;
	}
	public void setReloadParent(String reloadParent) {
		this.reloadParent = reloadParent;
	}
	public String[] getPreferenceCode() {
		return preferenceCode;
	}
	public void setPreferenceCode(String[] preferenceCode) {
		this.preferenceCode = preferenceCode;
	}
	public String[] getPreferenceValue() {
		return preferenceValue;
	}
	public void setPreferenceValue(String[] preferenceValue) {
		this.preferenceValue = preferenceValue;
	}
	public String getNotificationLanguageCode() {
		return notificationLanguageCode;
	}
	public void setNotificationLanguageCode(String notificationLanguageCode) {
		this.notificationLanguageCode = notificationLanguageCode;
	}
	public String getSelectedContactIndex() {
		return selectedContactIndex;
	}
	public void setSelectedContactIndex(String selectedContactIndex) {
		this.selectedContactIndex = selectedContactIndex;
	}
	public String[] getEventCode() {
		return eventCode;
	}
	public void setEventCode(String[] eventCode) {
		this.eventCode = eventCode;
	}
	public String getEmailFlag() {
		return emailFlag;
	}
	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}
	public String getMobileFlag() {
		return mobileFlag;
	}
	public void setMobileFlag(String mobileFlag) {
		this.mobileFlag = mobileFlag;
	}
	public String getFaxFlag() {
		return faxFlag;
	}
	public void setFaxFlag(String faxFlag) {
		this.faxFlag = faxFlag;
	}
	public String[] getNotificationSerialNumber() {
		return notificationSerialNumber;
	}
	public void setNotificationSerialNumber(String[] notificationSerialNumber) {
		this.notificationSerialNumber = notificationSerialNumber;
	}
	public String[] getContactMode() {
		return contactMode;
	}
	public void setContactMode(String[] contactMode) {
		this.contactMode = contactMode;
	}
	public String[] getContactAddress() {
		return contactAddress;
	}
	public void setContactAddress(String[] contactAddress) {
		this.contactAddress = contactAddress;
	}
	public String[] getAdditionalContactSerialNumber() {
		return additionalContactSerialNumber;
	}
	public void setAdditionalContactSerialNumber(
			String[] additionalContactSerialNumber) {
		this.additionalContactSerialNumber = additionalContactSerialNumber;
	}
	public String[] getAdditionalContactsOpFlag() {
		return additionalContactsOpFlag;
	}
	public void setAdditionalContactsOpFlag(String[] additionalContactsOpFlag) {
		this.additionalContactsOpFlag = additionalContactsOpFlag;
	}
	/**
	 * @return the excludeRounding
	 */
	public String getExcludeRounding() {
		return excludeRounding;
	}
	/**
	 * @param excludeRounding the excludeRounding to set
	 */
	public void setExcludeRounding(String excludeRounding) {
		this.excludeRounding = excludeRounding;
	}
	public String getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getCustomerAgentCode() {
		return customerAgentCode;
	}
	public void setCustomerAgentCode(String customerAgentCode) {
		this.customerAgentCode = customerAgentCode;
	}
	public String getCustomerAgentName() {
		return customerAgentName;
	}
	public void setCustomerAgentName(String customerAgentName) {
		this.customerAgentName = customerAgentName;
	}
	public String getAircraftTypeHandledList() {
		return aircraftTypeHandledList;
	}
	/**
	 * 
	 * @param aircraftTypeHandledList
	 */
	public void setAircraftTypeHandledList(String aircraftTypeHandledList) {
		this.aircraftTypeHandledList = aircraftTypeHandledList;
	}
	//Ends
	//private String awbEnduranceType;
	//private String invoiceEnduranceType;	
	//Added by A-4772 for ICRD-34844 starts here
	private String customerTypecheckFlag;
	
	/**
	 * @author A-4772
	 * @return the customerTypecheckFlag
	 */
	public String getCustomerTypecheckFlag() {
		return customerTypecheckFlag;
	}

	/**
	 * @author A-4772
	 * @param customerTypecheckFlag the customerTypecheckFlag to set
	 */
	public void setCustomerTypecheckFlag(String customerTypecheckFlag) {
		this.customerTypecheckFlag = customerTypecheckFlag;
	}
	//Added by A-4772 for ICRD-34844 ends here
	
	public String getEnduranceFlag() {
		return enduranceFlag;
	}

	public void setEnduranceFlag(String enduranceFlag) {
		this.enduranceFlag = enduranceFlag;
	}

	//private String billingCurrency;
	private String billingremark;
	
	private String enduranceValue;
	private String endurancePercentage;
	private String enduranceMaxValue;
	private String customerType;
	
	// Added By A-5183 for ICRD-18283 Starts
	
	private String misopcheck;
	private String resave;
    private String[] miscDetailCode;
    
    private String[] miscDetailValue;
    
    private String[] miscDetailValidFrom;
    
    private String[] miscDetailValidTo;
    
    private String[] miscDetailRemarks;
    
    private String[] miscDetailOpFlag;
    
    private String[] oprFlag;
    
    private String[] miscSeqNum;
                    
    private String bankcode;
    
    private String bankbranch;
    
    private String bankcityname;
    
    private String bankstatename;
    
    private String bankcountryname;
    
    private String bankpincode;
    
    private String[] rowIdmis;

    private String restrictionFlag ;
    
    //Added by A-5807 for ICRD-73246
    private String[] restrictedFOPs;
	
    //Added by A-5163 for the ICRD-78230 starts
    private String gibCustomerFlag; 
	private String gibRegistrationDate;
	private String publicSectorFlag; 
	//Added by A-5163 for the ICRD-78230 ends
	private String[] agentReferenceId;
	public String getRestrictionFlag() {
		return restrictionFlag;
	}

	public void setRestrictionFlag(String restrictionFlag) {
		this.restrictionFlag = restrictionFlag;
	}

	public String getMisopcheck() {
		return misopcheck;
	}

	public void setMisopcheck(String misopcheck) {
		this.misopcheck = misopcheck;
	}

	public String getResave() {
		return resave;
	}

	public void setResave(String resave) {
		this.resave = resave;
	}

	public String[] getMiscDetailCode() {
		return miscDetailCode;
	}

	public void setMiscDetailCode(String[] miscDetailCode) {
		this.miscDetailCode = miscDetailCode;
	}

	public String[] getMiscDetailValue() {
		return miscDetailValue;
	}

	public void setMiscDetailValue(String[] miscDetailValue) {
		this.miscDetailValue = miscDetailValue;
	}

	public String[] getMiscDetailValidFrom() {
		return miscDetailValidFrom;
	}

	public void setMiscDetailValidFrom(String[] miscDetailValidFrom) {
		this.miscDetailValidFrom = miscDetailValidFrom;
	}

	public String[] getMiscDetailValidTo() {
		return miscDetailValidTo;
	}

	public void setMiscDetailValidTo(String[] miscDetailValidTo) {
		this.miscDetailValidTo = miscDetailValidTo;
	}

	public String[] getMiscDetailRemarks() {
		return miscDetailRemarks;
	}

	public void setMiscDetailRemarks(String[] miscDetailRemarks) {
		this.miscDetailRemarks = miscDetailRemarks;
	}

	public String[] getMiscDetailOpFlag() {
		return miscDetailOpFlag;
	}

	public void setMiscDetailOpFlag(String[] miscDetailOpFlag) {
		this.miscDetailOpFlag = miscDetailOpFlag;
	}

	public String[] getOprFlag() {
		return oprFlag;
	}

	public void setOprFlag(String[] oprFlag) {
		this.oprFlag = oprFlag;
	}

	public String[] getMiscSeqNum() {
		return miscSeqNum;
	}

	public void setMiscSeqNum(String[] miscSeqNum) {
		this.miscSeqNum = miscSeqNum;
	}

	public String getBankcode() {
		return bankcode;
	}

	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}

	public String getBankbranch() {
		return bankbranch;
	}

	public void setBankbranch(String bankbranch) {
		this.bankbranch = bankbranch;
	}

	public String getBankcityname() {
		return bankcityname;
	}

	public void setBankcityname(String bankcityname) {
		this.bankcityname = bankcityname;
	}

	public String getBankstatename() {
		return bankstatename;
	}

	public void setBankstatename(String bankstatename) {
		this.bankstatename = bankstatename;
	}

	public String getBankcountryname() {
		return bankcountryname;
	}

	public void setBankcountryname(String bankcountryname) {
		this.bankcountryname = bankcountryname;
	}

	public String getBankpincode() {
		return bankpincode;
	}

	public void setBankpincode(String bankpincode) {
		this.bankpincode = bankpincode;
	}

	public String[] getRowIdmis() {
		return rowIdmis;
	}

	public void setRowIdmis(String[] rowIdmis) {
		this.rowIdmis = rowIdmis;
	}

	public String getBillingremark() {
		return billingremark;
	}

	public void setBillingremark(String billingremark) {
		this.billingremark = billingremark;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public String getEnduranceMaxValue() {
		return enduranceMaxValue;
	}

	public void setEnduranceMaxValue(String enduranceMaxValue) {
		this.enduranceMaxValue = enduranceMaxValue;
	}

	public String getEnduranceValue() {
		return enduranceValue;
	}

	public void setEnduranceValue(String enduranceValue) {
		this.enduranceValue = enduranceValue;
	}

	public String getEndurancePercentage() {
		return endurancePercentage;
	}

	public void setEndurancePercentage(String endurancePercentage) {
		this.endurancePercentage = endurancePercentage;
	}

	
	public boolean isBillingThroughInterline() {
		return billingThroughInterline;
	}

	public void setBillingThroughInterline(boolean billingThroughInterline) {
		this.billingThroughInterline = billingThroughInterline;
	}

	public String getVatRegNumber() {
		return vatRegNumber;
	}

	public void setVatRegNumber(String vatRegNumber) {
		this.vatRegNumber = vatRegNumber;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getCccollector() {
		return cccollector;
	}

	public void setCccollector(String cccollector) {
		this.cccollector = cccollector;
	}

	public String getBillingTo() {
		return billingTo;
	}

	public void setBillingTo(String billingTo) {
		this.billingTo = billingTo;
	}

	public String getClName() {
		return clName;
	}

	public void setClName(String clName) {
		this.clName = clName;
	}

	public String getIataCode() {
		return iataCode;
	}

	public void setIataCode(String iataCode) {
		this.iataCode = iataCode;
	}

	public String getIataAgentCode() {
		return iataAgentCode;
	}

	public void setIataAgentCode(String iataAgentCode) {
		this.iataAgentCode = iataAgentCode;
	}

	public String getAgentInformation() {
		return agentInformation;
	}

	public void setAgentInformation(String agentInformation) {
		this.agentInformation = agentInformation;
	}

	public String getCassIdentifier() {
		return cassIdentifier;
	}

	public void setCassIdentifier(String cassIdentifier) {
		this.cassIdentifier = cassIdentifier;
	}

	public String getSettlementCurrency() {
		return settlementCurrency;
	}

	public void setSettlementCurrency(String settlementCurrency) {
		this.settlementCurrency = settlementCurrency;
	}

	public String getSalesId() {
		return salesId;
	}

	public void setSalesId(String salesId) {
		this.salesId = salesId;
	}

	public String getHoldingCompany() {
		return holdingCompany;
	}

	public void setHoldingCompany(String holdingCompany) {
		this.holdingCompany = holdingCompany;
	}

	public String getNormalComm() {
		return normalComm;
	}

	public void setNormalComm(String normalComm) {
		this.normalComm = normalComm;
	}

	public String getNormalCommFixed() {
		return normalCommFixed;
	}

	public void setNormalCommFixed(String normalCommFixed) {
		this.normalCommFixed = normalCommFixed;
	}

	public String getOwnSalesFlag() {
		return ownSalesFlag;
	}

	public void setOwnSalesFlag(String ownSalesFlag) {
		this.ownSalesFlag = ownSalesFlag;
	}

	public String getSalesReportAgent() {
		return salesReportAgent;
	}

	public void setSalesReportAgent(String salesReportAgent) {
		this.salesReportAgent = salesReportAgent;
	}

	public String getControllingLocation() {
		return controllingLocation;
	}

	public void setControllingLocation(String controllingLocation) {
		this.controllingLocation = controllingLocation;
	}

	public String getSellingLocation() {
		return sellingLocation;
	}

	public void setSellingLocation(String sellingLocation) {
		this.sellingLocation = sellingLocation;
	}

	public String getProformaInv() {
		return proformaInv;
	}

	public void setProformaInv(String proformaInv) {
		this.proformaInv = proformaInv;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAircraftTypeHandled() {
		return aircraftTypeHandled;
	}

	public void setAircraftTypeHandled(String aircraftTypeHandled) {
		this.aircraftTypeHandled = aircraftTypeHandled;
	}

	public String getDateOfExchange() {
		return dateOfExchange;
	}

	public void setDateOfExchange(String dateOfExchange) {
		this.dateOfExchange = dateOfExchange;
	}

	public boolean isCassBillingIndicator() {
		return cassBillingIndicator;
	}

	public void setCassBillingIndicator(boolean cassBillingIndicator) {
		this.cassBillingIndicator = cassBillingIndicator;
	}

	public String getCassCode() {
		return cassCode;
	}

	public void setCassCode(String cassCode) {
		this.cassCode = cassCode;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/*public String getAwbEnduranceType() {
		return awbEnduranceType;
	}

	public void setAwbEnduranceType(String awbEnduranceType) {
		this.awbEnduranceType = awbEnduranceType;
	}*/

	/*public String getInvoiceEnduranceType() {
		return invoiceEnduranceType;
	}

	public void setInvoiceEnduranceType(String invoiceEnduranceType) {
		this.invoiceEnduranceType = invoiceEnduranceType;
	}*/

	
	
	/**
	 * @return the eoriNo
	 */
	public String getEoriNo() {
		return eoriNo;
	}
	public String getBillPeriod() {
		return billPeriod;
	}
	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}
	/**
	 * @param eoriNo the eoriNo to set
	 */
	public void setEoriNo(String eoriNo) {
		this.eoriNo = eoriNo;
	}
	public String getCustomsLocationNo() {
		return customsLocationNo;
	}

	public void setCustomsLocationNo(String customsLocationNo) {
		this.customsLocationNo = customsLocationNo;
	}

	/**
	 * @return the frmCusLisCreate
	 */
	public String getFrmCusLisCreate() {
		return frmCusLisCreate;
	}
	/**
	 * @param frmCusLisCreate the frmCusLisCreate to set
	 */
	public void setFrmCusLisCreate(String frmCusLisCreate) {
		this.frmCusLisCreate = frmCusLisCreate;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getOperationFlag() {
		return operationFlag;
	}
	/**
	 * 
	 * @param operationFlag
	 */
	public void setOperationFlag(String[] operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheckedStatus() {
		return checkedStatus;
	}
	/**
	 * 
	 * @param checkedStatus
	 */
	public void setCheckedStatus(String checkedStatus) {
		this.checkedStatus = checkedStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String getListStatus() {
		return listStatus;
	}
	/**
	 * 
	 * @param listStatus
	 */
	public void setListStatus(String listStatus) {
		this.listStatus = listStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getSelectedClearingAgentDetails() {
		return selectedClearingAgentDetails;
	}
	/**
	 * 
	 * @param selectedClearingAgentDetails
	 */
	public void setSelectedClearingAgentDetails(
			String[] selectedClearingAgentDetails) {
		this.selectedClearingAgentDetails = selectedClearingAgentDetails;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getSelectedContactDetails() {
		return selectedContactDetails;
	}
	/**
	 * 
	 * 
	 * @param selectedContactDetails
	 */
	public void setSelectedContactDetails(String[] selectedContactDetails) {
		this.selectedContactDetails = selectedContactDetails;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAgentCode() {
		return agentCode;
	}
	/**
	 * 
	 * @param agentCode
	 */
	public void setAgentCode(String[] agentCode) {
		this.agentCode = agentCode;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAgentName() {
		return agentName;
	}
	/**
	 * 
	 * @param agentName
	 */
	public void setAgentName(String[] agentName) {
		this.agentName = agentName;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAgentRemarks() {
		return agentRemarks;
	}
	/**
	 * 
	 * @param agentRemarks
	 */
	public void setAgentRemarks(String[] agentRemarks) {
		this.agentRemarks = agentRemarks;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getAgentStation() {
		return agentStation;
	}
	/**
	 * 
	 * @param agentStation
	 */
	public void setAgentStation(String[] agentStation) {
		this.agentStation =agentStation;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getActive() {
		return active;
	}
	/**
	 * 
	 * @param active
	 */
	public void setActive(String[] active) {
		this.active = active;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactCode() {
		return contactCode;
	}
	/**
	 * 
	 * @param contactCode
	 */
	public void setContactCode(String[] contactCode) {
		this.contactCode = contactCode;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactDesignation() {
		return contactDesignation;
	}
	/**
	 * 
	 * @param contactDesignation
	 */
	public void setContactDesignation(String[] contactDesignation) {
		this.contactDesignation = contactDesignation;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactEmail() {
		return contactEmail;
	}
	/**
	 * 
	 * @param contactEmail
	 */
	public void setContactEmail(String[] contactEmail) {
		this.contactEmail = contactEmail;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactFax() {
		return contactFax;
	}
	/**
	 * 
	 * @param contactFax
	 */
	public void setContactFax(String[] contactFax) {
		this.contactFax = contactFax;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactMobile() {
		return contactMobile;
	}
	/**
	 * 
	 * @param contactMobile
	 */
	public void setContactMobile(String[] contactMobile) {
		this.contactMobile = contactMobile;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactName() {
		return contactName;
	}
	/**
	 * 
	 * @param contactName
	 */
	public void setContactName(String[] contactName) {
		this.contactName = contactName;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactRemarks() {
		return contactRemarks;
	}
	/**
	 * 
	 * @param contactRemarks
	 */
	public void setContactRemarks(String[] contactRemarks) {
		this.contactRemarks = contactRemarks;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactSita() {
		return contactSita;
	}
	/**
	 * 
	 * @param contactSita
	 */
	public void setContactSita(String[] contactSita) {
		this.contactSita = contactSita;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getContactTelephone() {
		return contactTelephone;
	}
	/**
	 * 
	 * @param contactTelephone
	 */
	public void setContactTelephone(String[] contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
/**
 * 
 * @return
 */
public String getZone() {
		return zone;
	}
/**
 * 
 * @param zone
 */
	public void setZone(String zone) {
		this.zone = zone;
	}
/**
 * 
 * @return
 */
	public String getCustomerGroup() {
		return customerGroup;
	}
	/**
	 * 
	 * @param customerGroup
	 */
	public void setCustomerGroup(String customerGroup) {
		this.customerGroup = customerGroup;
	}
	/**
	 * 
	 * @return
	 */
    public String getCustomerStatus() {
		return customerStatus;
	}
    /**
     * 
     * @param customerStatus
     */

	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}

/**
 * 
 */
    public String getScreenId() {
        return SCREENID;
    }

   /**
    * 
    */
    public String getProduct() {
        return PRODUCT;
    }

/**
 * 
 */
    public String getSubProduct() {
        return SUBPRODUCT;
    }
    /**
     * 
     * @return
     */
	public String getAddress1() {
		return address1;
	}
	/**
	 * 
	 * @param address1
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	/**
	 * 
	 * @return
	 */
	public String getAddress2() {
		return address2;
	}
	/**
	 * 
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	/**
	 * 
	 * @return
	 */
	public String getArea() {
		return area;
	}
	/**
	 * 
	 * @param area
	 */
	public void setArea(String area) {
		this.area = area;
	}

/**
 * 
 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * 
	 * @param bundle
	 */
	public void setBundle(String bundle) {
		this.bundle = bundle;
	}
	/**
	 * 
	 * @return
	 */
	public String getCity() {
		return city;
	}
	/**
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}
	/**
	 * 
	 * @return
	 */
	public String getCountry() {
		return country;
	}
	/**
	 * 
	 * @param country
	 */
	public void setCountry(String country) {
		this.country = country;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditAccountNo() {
		return creditAccountNo;
	}
	/**
	 * 
	 * @param creditAccountNo
	 */
	public void setCreditAccountNo(String creditAccountNo) {
		this.creditAccountNo = creditAccountNo;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditLimit() {
		return creditLimit;
	}
	/**
	 * 
	 * @param creditLimit
	 */
	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditOutStanding() {
		return creditOutStanding;
	}
	/**
	 * 
	 * @param creditOutStanding
	 */
	public void setCreditOutStanding(String creditOutStanding) {
		this.creditOutStanding = creditOutStanding;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditPeriod() {
		return creditPeriod;
	}
	/**
	 * 
	 * @param creditPeriod
	 */
	public void setCreditPeriod(String creditPeriod) {
		this.creditPeriod = creditPeriod;
	}
	/**
	 * 
	 * @return
	 */
	public String getCustName() {
		return custName;
	}
	/**
	 * 
	 * @param custName
	 */
	public void setCustName(String custName) {
		this.custName = custName;
	}
	/**
	 * 
	 * @return
	 */
	public String getCustomerCode() {
		return customerCode;
	}	
	/**
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * 
	 * @return
	 */
	public String getFax() {
		return fax;
	}
	/**
	 * 
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}
	/**
	 * 
	 * @return
	 */
	public String getMobile() {
		return mobile;
	}
	/**
	 * 
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	/**
	 * 
	 * @return
	 */
	public String getSita() {
		return sita;
	}
	/**
	 * 
	 * @param sita
	 */
	public void setSita(String sita) {
		this.sita = sita;
	}
	/**
	 * 
	 * @return
	 */
	public String getStation() {
		return station;
	}
	/**
	 * 
	 * @param station
	 */
	public void setStation(String station) {
		this.station = station;
	}
	/**
	 * 
	 * @return
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */
	public String getTelephone() {
		return telephone;
	}
	/**
	 * 
	 * @param telephone
	 */
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	/**
	 * 
	 * @return
	 */
	public String getGlobalCustomer() {
		return globalCustomer;
	}
/**
 * 
 * @param globalCustomer
 */
	public void setGlobalCustomer(String globalCustomer) {
		this.globalCustomer = globalCustomer;
	}
	/**
	 * 
	 * @return
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditLimitCurrencyCode() {
		return creditLimitCurrencyCode;
	}
	/**
	 * 
	 * @param creditLimitCurrencyCode
	 */
	public void setCreditLimitCurrencyCode(String creditLimitCurrencyCode) {
		this.creditLimitCurrencyCode = creditLimitCurrencyCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditOutStandingCurrencyCode() {
		return creditOutStandingCurrencyCode;
	}
	/**
	 * 
	 * @param creditOutStandingCurrencyCode
	 */
	public void setCreditOutStandingCurrencyCode(
			String creditOutStandingCurrencyCode) {
		this.creditOutStandingCurrencyCode = creditOutStandingCurrencyCode;
	}
	/**
	 * 
	 * @return
	 */
	public String getCreditPeriods() {
		return creditPeriods;
	}
	/**
	 * 
	 * @param creditPeriods
	 */
	public void setCreditPeriods(String creditPeriods) {
		this.creditPeriods = creditPeriods;
	}
	/**
	 * 
	 * @return
	 */
	public String getGlobalCustomerFlag() {
		return globalCustomerFlag;
	}
	/**
	 * 
	 * @param globalCustomerFlag
	 */
	public void setGlobalCustomerFlag(String globalCustomerFlag) {
		this.globalCustomerFlag = globalCustomerFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getExport() {
		return export;
	}
	/**
	 * 
	 * @param export
	 */
	public void setExport(String[] export) {
		this.export = export;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getImported() {
		return imported;
	}
	/**
	 * 
	 * @param imported
	 */
	public void setImported(String[] imported) {
		this.imported = imported;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getSales() {
		return sales;
	}
	/**
	 * 
	 * @param sales
	 */
	public void setSales(String[] sales) {
		this.sales = sales;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheckedExport() {
		return checkedExport;
	}
	/**
	 * 
	 * @param checkedExport
	 */
	public void setCheckedExport(String checkedExport) {
		this.checkedExport = checkedExport;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheckedImport() {
		return checkedImport;
	}
	/**
	 * 
	 * @param checkedImport
	 */
			
	public void setCheckedImport(String checkedImport) {
		this.checkedImport = checkedImport;
	}
	/**
	 * 
	 * @return
	 */
	public String getCheckedSales() {
		return checkedSales;
	}
	/**
	 * 
	 * @param checkedSales
	 */
	public void setCheckedSales(String checkedSales) {
		this.checkedSales = checkedSales;
	}
	/**
	 * @return Returns the custCodePresent.
	 */
	public String getCustCodePresent() {
		return custCodePresent;
	}
	/**
	 * @param custCodePresent The custCodePresent to set.
	 */
	public void setCustCodePresent(String custCodePresent) {
		this.custCodePresent = custCodePresent;
	}
	/**
	 * @return Returns the pageURL.
	 */
	public String getPageURL() {
		return pageURL;
	}
	/**
	 * @param pageURL The pageURL to set.
	 */
	public void setPageURL(String pageURL) {
		this.pageURL = pageURL;
	}
	/**
	 * @return Returns the opFlag.
	 */
	public String[] getOpFlag() {
		return opFlag;
	}
	/**
	 * @param opFlag The opFlag to set.
	 */
	public void setOpFlag(String[] opFlag) {
		this.opFlag = opFlag;
	}
	/**
	 * @return Returns the screenStatus.
	 */
	public String getScreenStatus() {
		return screenStatus;
	}
	/**
	 * @param screenStatus The screenStatus to set.
	 */
	public void setScreenStatus(String screenStatus) {
		this.screenStatus = screenStatus;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getCheck() {
		return check;
	}
	/**
	 * 
	 * @param check
	 */
	public void setCheck(String[] check) {
		this.check = check;
	}
	/**
	 * 
	 * @return
	 */
	public String get() {
		return displayPage;
	}
	/**
	 * 
	 * @param displayPage
	 */
	public void setDisplayPage(String displayPage) {
		this.displayPage = displayPage;
	}
	/**
	 * 
	 * @return
	 */
	public String getLastPageNumber() {
		return lastPageNumber;
	}
	/**
	 * 
	 * @param lastPageNumber
	 */
	public void setLastPageNumber(String lastPageNumber) {
		this.lastPageNumber = lastPageNumber;
	}
	/**
	 * 
	 * @return
	 */
	public String getFromCustListing() {
		return fromCustListing;
	}
	/**
	 * 
	 * @param fromCustListing
	 */
	public void setFromCustListing(String fromCustListing) {
		this.fromCustListing = fromCustListing;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getOperationAgentFlag() {
		return operationAgentFlag;
	}
	/**
	 * 
	 * @param operationAgentFlag
	 */
	public void setOperationAgentFlag(String[] operationAgentFlag) {
		this.operationAgentFlag = operationAgentFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getOperationContactFlag() {
		return operationContactFlag;
	}
	/**
	 * 
	 * @param operationContactFlag
	 */
	public void setOperationContactFlag(String[] operationContactFlag) {
		this.operationContactFlag = operationContactFlag;
	}
	/**
	 * 
	 * @return
	 */
	public String getPrimaryContact() {
		return primaryContact;
	}
	/**
	 * 
	 * @param primaryContact
	 */
	public void setPrimaryContact(String primaryContact) {
		this.primaryContact = primaryContact;
	}
	/**
	 * 
	 * @return
	 */
	public String[] getPrimaryContacts() {
		return primaryContacts;
	}
	/**
	 * 
	 * @param primaryContacts
	 */
	public void setPrimaryContacts(String[] primaryContacts) {
		this.primaryContacts = primaryContacts;
	}
	/**
	 * @return Returns the defaultNotifyMode.
	 */
	public String getDefaultNotifyMode() {
		return defaultNotifyMode;
	}
	/**
	 * @param defaultNotifyMode The defaultNotifyMode to set.
	 */
	public void setDefaultNotifyMode(String defaultNotifyMode) {
		this.defaultNotifyMode = defaultNotifyMode;
	}
	public String[] getHiddenOpFlagForCustomer() {
		return hiddenOpFlagForCustomer;
	}
	public void setHiddenOpFlagForCustomer(String[] hiddenOpFlagForCustomer) {
		this.hiddenOpFlagForCustomer = hiddenOpFlagForCustomer;
	}
	public String[] getHiddenOpFlagForAgent() {
		return hiddenOpFlagForAgent;
	}
	public void setHiddenOpFlagForAgent(String[] hiddenOpFlagForAgent) {
		this.hiddenOpFlagForAgent = hiddenOpFlagForAgent;
	}
	/**
	 * @return Returns the isFromReservation.
	 */
	public boolean isFromReservation() {
		return isFromReservation;
	}
	/**
	 * @param isFromReservation The isFromReservation to set.
	 */
	public void setFromReservation(boolean isFromReservation) {
		this.isFromReservation = isFromReservation;
	}
	/**
	 * @return Returns the isFromPermanentBooking.
	 */
	public boolean isFromPermanentBooking() {
		return isFromPermanentBooking;
	}
	/**
	 * @param isFromPermanentBooking The isFromPermanentBooking to set.
	 */
	public void setFromPermanentBooking(boolean isFromPermanentBooking) {
		this.isFromPermanentBooking = isFromPermanentBooking;
	}
	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}
	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	/**
	 * @return the knownShipper
	 */
	public String getKnownShipper() {
		return knownShipper;
	}
	/**
	 * @param knownShipper the knownShipper to set
	 */
	public void setKnownShipper(String knownShipper) {
		this.knownShipper = knownShipper;
	}
	/**
	 * @return the apiacsspExpiryDate
	 */
	public String getApiacsspExpiryDate() {
		return apiacsspExpiryDate;
	}
	/**
	 * @param apiacsspExpiryDate the apiacsspExpiryDate to set
	 */
	public void setApiacsspExpiryDate(String apiacsspExpiryDate) {
		this.apiacsspExpiryDate = apiacsspExpiryDate;
	}
	/**
	 * @return the apiacsspNumber
	 */
	public String getApiacsspNumber() {
		return apiacsspNumber;
	}
	/**
	 * @param apiacsspNumber the apiacsspNumber to set
	 */
	public void setApiacsspNumber(String apiacsspNumber) {
		this.apiacsspNumber = apiacsspNumber;
	}
	/**
	 * @return the billingCityCode
	 */
	public String getBillingCityCode() {
		return billingCityCode;
	}
	/**
	 * @param billingCityCode the billingCityCode to set
	 */
	public void setBillingCityCode(String billingCityCode) {
		this.billingCityCode = billingCityCode;
	}
	/**
	 * @return the billingCountry
	 */
	public String getBillingCountry() {
		return billingCountry;
	}
	/**
	 * @param billingCountry the billingCountry to set
	 */
	public void setBillingCountry(String billingCountry) {
		this.billingCountry = billingCountry;
	}
	/**
	 * @return the billingEmail
	 */
	public String getBillingEmail() {
		return billingEmail;
	}
	/**
	 * @param billingEmail the billingEmail to set
	 */
	public void setBillingEmail(String billingEmail) {
		this.billingEmail = billingEmail;
	}
	/**
	 * @return the billingFax
	 */
	public String getBillingFax() {
		return billingFax;
	}
	/**
	 * @param billingFax the billingFax to set
	 */
	public void setBillingFax(String billingFax) {
		this.billingFax = billingFax;
	}
	/**
	 * @return the billingLocation
	 */
	public String getBillingLocation() {
		return billingLocation;
	}
	/**
	 * @param billingLocation the billingLocation to set
	 */
	public void setBillingLocation(String billingLocation) {
		this.billingLocation = billingLocation;
	}
	/**
	 * @return the billingState
	 */
	public String getBillingState() {
		return billingState;
	}
	/**
	 * @param billingState the billingState to set
	 */
	public void setBillingState(String billingState) {
		this.billingState = billingState;
	}
	/**
	 * @return the billingStreet
	 */
	public String getBillingStreet() {
		return billingStreet;
	}
	/**
	 * @param billingStreet the billingStreet to set
	 */
	public void setBillingStreet(String billingStreet) {
		this.billingStreet = billingStreet;
	}
	/**
	 * @return the billingTelephone
	 */
	public String getBillingTelephone() {
		return billingTelephone;
	}
	/**
	 * @param billingTelephone the billingTelephone to set
	 */
	public void setBillingTelephone(String billingTelephone) {
		this.billingTelephone = billingTelephone;
	}
	/**
	 * @return the billingZipcode
	 */
	public String getBillingZipcode() {
		return billingZipcode;
	}
	/**
	 * @param billingZipcode the billingZipcode to set
	 */
	public void setBillingZipcode(String billingZipcode) {
		this.billingZipcode = billingZipcode;
	}
	/**
	 * @return the ccsfCityName
	 */
	public String[] getCcsfCityName() {
		return ccsfCityName;
	}
	/**
	 * @param ccsfCityName the ccsfCityName to set
	 */
	public void setCcsfCityName(String[] ccsfCityName) {
		this.ccsfCityName = ccsfCityName;
	}
	/**
	 * @return the ccsfExpiryDate
	 */
	public String[] getCcsfExpiryDate() {
		return ccsfExpiryDate;
	}
	/**
	 * @param ccsfExpiryDate the ccsfExpiryDate to set
	 */
	public void setCcsfExpiryDate(String[] ccsfExpiryDate) {
		this.ccsfExpiryDate = ccsfExpiryDate;
	}
	/**
	 * @return the ccsfNumber
	 */
	public String[] getCcsfNumber() {
		return ccsfNumber;
	}
	/**
	 * @param ccsfNumber the ccsfNumber to set
	 */
	public void setCcsfNumber(String[] ccsfNumber) {
		this.ccsfNumber = ccsfNumber;
	}
	/**
	 * @return the establishedDate
	 */
	public String getEstablishedDate() {
		return establishedDate;
	}
	/**
	 * @param establishedDate the establishedDate to set
	 */
	public void setEstablishedDate(String establishedDate) {
		this.establishedDate = establishedDate;
	}
	/**
	 * @return the iacExpiryDate
	 */
	public String getIacExpiryDate() {
		return iacExpiryDate;
	}
	/**
	 * @param iacExpiryDate the iacExpiryDate to set
	 */
	public void setIacExpiryDate(String iacExpiryDate) {
		this.iacExpiryDate = iacExpiryDate;
	}
	/**
	 * @return the iacNumber
	 */
	public String getIacNumber() {
		return iacNumber;
	}
	/**
	 * @param iacNumber the iacNumber to set
	 */
	public void setIacNumber(String iacNumber) {
		this.iacNumber = iacNumber;
	}
	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return the selectedCCSFDetails
	 */
	public String[] getSelectedCCSFDetails() {
		return selectedCCSFDetails;
	}
	/**
	 * @param selectedCCSFDetails the selectedCCSFDetails to set
	 */
	public void setSelectedCCSFDetails(String[] selectedCCSFDetails) {
		this.selectedCCSFDetails = selectedCCSFDetails;
	}
	/**
	 * @return the hiddenOpFlagForCCSF
	 */
	public String[] getHiddenOpFlagForCCSF() {
		return hiddenOpFlagForCCSF;
	}
	/**
	 * @param hiddenOpFlagForCCSF the hiddenOpFlagForCCSF to set
	 */
	public void setHiddenOpFlagForCCSF(String[] hiddenOpFlagForCCSF) {
		this.hiddenOpFlagForCCSF = hiddenOpFlagForCCSF;
	}
	/**
	 * @return the invoiceToCustomer
	 */
	public String getInvoiceToCustomer() {
		return invoiceToCustomer;
	}
	/**
	 * @param invoiceToCustomer the invoiceToCustomer to set
	 */
	public void setInvoiceToCustomer(String invoiceToCustomer) {
		this.invoiceToCustomer = invoiceToCustomer;
	}
	/**
	 * @return the stopCredit
	 */
	public String getStopCredit() {
		return stopCredit;
	}
	/**
	 * @param stopCredit the stopCredit to set
	 */
	public void setStopCredit(String stopCredit) {
		this.stopCredit = stopCredit;
	}
	/**
	 * @return the adminPrivilege
	 */
	public String getAdminPrivilege() {
		return adminPrivilege;
	}
	/**
	 * @param adminPrivilege the adminPrivilege to set
	 */
	public void setAdminPrivilege(String adminPrivilege) {
		this.adminPrivilege = adminPrivilege;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the ajaxErrorStatusFlag
	 */
	public String getAjaxErrorStatusFlag() {
		return ajaxErrorStatusFlag;
	}
	/**
	 * @param ajaxErrorStatusFlag the ajaxErrorStatusFlag to set
	 */
	public void setAjaxErrorStatusFlag(String ajaxErrorStatusFlag) {
		this.ajaxErrorStatusFlag = ajaxErrorStatusFlag;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the billingPeriod
	 */
	public String getBillingPeriod() {
		return billingPeriod;
	}
	/**
	 * @param billingPeriod the billingPeriod to set
	 */
	public void setBillingPeriod(String billingPeriod) {
		this.billingPeriod = billingPeriod;
	}
	/**
	 * @return the billingPeriodDescription
	 */
	public String getBillingPeriodDescription() {
		return billingPeriodDescription;
	}
	/**
	 * @param billingPeriodDescription the billingPeriodDescription to set
	 */
	public void setBillingPeriodDescription(String billingPeriodDescription) {
		this.billingPeriodDescription = billingPeriodDescription;
	}
	/**
	 * @return Returns the billingCode.
	 */
	public String getBillingCode() {
		return billingCode;
	}
	/**
	 * @param billingCode The billingCode to set.
	 */
	public void setBillingCode(String billingCode) {
		this.billingCode = billingCode;
	}
	/**
	 * @return Returns the branch.
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch The branch to set.
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * @return Returns the branchName.
	 */
	public String getBranchName() {
		return branchName;
	}
	/**
	 * @param branchName The branchName to set.
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	/**
	 * @return Returns the consolidator.
	 */
	public String getConsolidator() {
		return consolidator;
	}
	/**
	 * @param consolidator The consolidator to set.
	 */
	public void setConsolidator(String consolidator) {
		this.consolidator = consolidator;
	}
	/**
	 * @return Returns the customerCompanyCode.
	 */
	public String getCustomerCompanyCode() {
		return customerCompanyCode;
	}
	/**
	 * @param customerCompanyCode The customerCompanyCode to set.
	 */
	public void setCustomerCompanyCode(String customerCompanyCode) {
		this.customerCompanyCode = customerCompanyCode;
	}
	/**
	 * @return Returns the customerPOAValidity.
	 */
	public String getCustomerPOAValidity() {
		return customerPOAValidity;
	}
	/**
	 * @param customerPOAValidity The customerPOAValidity to set.
	 */
	public void setCustomerPOAValidity(String customerPOAValidity) {
		this.customerPOAValidity = customerPOAValidity;
	}
	/**
	 * @return Returns the defaultHawbLength.
	 */
	public String getDefaultHawbLength() {
		return defaultHawbLength;
	}
	/**
	 * @param defaultHawbLength The defaultHawbLength to set.
	 */
	public void setDefaultHawbLength(String defaultHawbLength) {
		this.defaultHawbLength = defaultHawbLength;
	}
	/**
	 * @return Returns the email2.
	 */
	public String getEmail2() {
		return email2;
	}
	/**
	 * @param email2 The email2 to set.
	 */
	public void setEmail2(String email2) {
		this.email2 = email2;
	}
	/**
	 * @return Returns the forwarderType.
	 */
	public String getForwarderType() {
		return forwarderType;
	}
	/**
	 * @param forwarderType The forwarderType to set.
	 */
	public void setForwarderType(String forwarderType) {
		this.forwarderType = forwarderType;
	}
	/**
	 * @return Returns the handledCustomerExport.
	 */
	public String getHandledCustomerExport() {
		return handledCustomerExport;
	}
	/**
	 * @param handledCustomerExport The handledCustomerExport to set.
	 */
	public void setHandledCustomerExport(String handledCustomerExport) {
		this.handledCustomerExport = handledCustomerExport;
	}
	/**
	 * @return Returns the handledCustomerForwarder.
	 */
	public String getHandledCustomerForwarder() {
		return handledCustomerForwarder;
	}
	/**
	 * @param handledCustomerForwarder The handledCustomerForwarder to set.
	 */
	public void setHandledCustomerForwarder(String handledCustomerForwarder) {
		this.handledCustomerForwarder = handledCustomerForwarder;
	}
	/**
	 * @return Returns the handledCustomerImport.
	 */
	public String getHandledCustomerImport() {
		return handledCustomerImport;
	}
	/**
	 * @param handledCustomerImport The handledCustomerImport to set.
	 */
	public void setHandledCustomerImport(String handledCustomerImport) {
		this.handledCustomerImport = handledCustomerImport;
	}
	/**
	 * @return Returns the naacsbbAgentCode.
	 */
	public String getNaacsbbAgentCode() {
		return naacsbbAgentCode;
	}
	/**
	 * @param naacsbbAgentCode The naacsbbAgentCode to set.
	 */
	public void setNaacsbbAgentCode(String naacsbbAgentCode) {
		this.naacsbbAgentCode = naacsbbAgentCode;
	}
	/**
	 * @return Returns the naccsAircargoAgentCode.
	 */
	public String getNaccsAircargoAgentCode() {
		return naccsAircargoAgentCode;
	}
	/**
	 * @param naccsAircargoAgentCode The naccsAircargoAgentCode to set.
	 */
	public void setNaccsAircargoAgentCode(String naccsAircargoAgentCode) {
		this.naccsAircargoAgentCode = naccsAircargoAgentCode;
	}
	/**
	 * @return Returns the naccsDeclarationCode.
	 */
	public String getNaccsDeclarationCode() {
		return naccsDeclarationCode;
	}
	/**
	 * @param naccsDeclarationCode The naccsDeclarationCode to set.
	 */
	public void setNaccsDeclarationCode(String naccsDeclarationCode) {
		this.naccsDeclarationCode = naccsDeclarationCode;
	}
	/**
	 * @return Returns the naccsInvoiceCode.
	 */
	public String getNaccsInvoiceCode() {
		return naccsInvoiceCode;
	}
	/**
	 * @param naccsInvoiceCode The naccsInvoiceCode to set.
	 */
	public void setNaccsInvoiceCode(String naccsInvoiceCode) {
		this.naccsInvoiceCode = naccsInvoiceCode;
	}
	/**
	 * @return Returns the poa.
	 */
	public String getPoa() {
		return poa;
	}
	/**
	 * @param poa The poa to set.
	 */
	public void setPoa(String poa) {
		this.poa = poa;
	}
	/**
	 * @return Returns the spotForwarder.
	 */
	public String getSpotForwarder() {
		return spotForwarder;
	}
	/**
	 * @param spotForwarder The spotForwarder to set.
	 */
	public void setSpotForwarder(String spotForwarder) {
		this.spotForwarder = spotForwarder;
	}
	/**
	 * @return the agentScc
	 */
	public String[] getAgentScc() {
		return agentScc;
	}
	/**
	 * @param agentScc the agentScc to set
	 */
	public void setAgentScc(String[] agentScc) {
		this.agentScc = agentScc;
	}
	/**
	 * @return the scc
	 */
	public String getScc() {
		return scc;
	}
	/**
	 * @param scc the scc to set
	 */
	public void setScc(String scc) {
		this.scc = scc;
	}
	public void setCustomerShortCode(String customerShortCode) {
		this.customerShortCode = customerShortCode;
	}
	public String getCustomerShortCode() {
		return customerShortCode;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getInvoiceClubbingIndicator
	 *	Added by 	:	A-5163 on Mar 21, 2013
	 * 	Used for 	:	Getting the clubbing check box value
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getInvoiceClubbingIndicator() {
		return invoiceClubbingIndicator;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setInvoiceClubbingIndicator
	 *	Added by 	:	A-5163 on Mar 21, 2013
	 * 	Used for 	:	Setting the clubbing check box value
	 *	Parameters	:	@param isBillingClubbed 
	 *	Return type	: 	void
	 */
	public void setInvoiceClubbingIndicator(String invoiceClubbingIndicator) {
		this.invoiceClubbingIndicator = invoiceClubbingIndicator;
	}
	/**
	 * @return the vendorFlag
	 */
	public String getVendorFlag() {
		return vendorFlag;
	}
	/**
	 * @param vendorFlag the vendorFlag to set
	 */
	public void setVendorFlag(String vendorFlag) {
		this.vendorFlag = vendorFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getDisplayPopupPage
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Display Pop up Page. Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getDisplayPopupPage() {
		return displayPopupPage;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setDisplayPopupPage
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set Display Pop up Page. Added for ICRD-67442.
	 *	Parameters	:	@param displayPopupPage 
	 *	Return type	: 	void
	 */
	public void setDisplayPopupPage(String displayPopupPage) {
		this.displayPopupPage = displayPopupPage;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getTotalViewRecords
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Total View Records. Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getTotalViewRecords() {
		return totalViewRecords;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setTotalViewRecords
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set Total View Records. Added for ICRD-67442.
	 *	Parameters	:	@param totalViewRecords 
	 *	Return type	: 	void
	 */
	public void setTotalViewRecords(String totalViewRecords) {
		this.totalViewRecords = totalViewRecords;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getIsHistoryPresent
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get is History Present.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getIsHistoryPresent() {
		return isHistoryPresent;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setIsHistoryPresent
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set is History Present.Added for ICRD-67442.
	 *	Parameters	:	@param isHistoryPresent 
	 *	Return type	: 	void
	 */
	public void setIsHistoryPresent(String isHistoryPresent) {
		this.isHistoryPresent = isHistoryPresent;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getTotalRecords
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Total Records.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getTotalRecords() {
		return totalRecords;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setTotalRecords
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set Total Records.Added for ICRD-67442.
	 *	Parameters	:	@param totalRecords 
	 *	Return type	: 	void
	 */
	public void setTotalRecords(String totalRecords) {
		this.totalRecords = totalRecords;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getDisplayPage
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Display Page.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getDisplayPage() {
		return displayPage;
	}
	/**
	 * @param billingDueDays the billingDueDays to set
	 */
	public void setBillingDueDays(String billingDueDays) {
		this.billingDueDays = billingDueDays;
	}
	/**
	 * @return the billingDueDays
	 */
	public String getBillingDueDays() {
		return billingDueDays;
	}
	/**
	 * 	Getter for contactType 
	 *	Added by : a-5220 on 24-Apr-2014 for ICRD-55852
	 * 	Used for : getting contactTypes
	 */
	public String[] getContactTypes() {
		return contactTypes;
	}
	/**
	 *  @param contactType the contactTypes to set
	 * 	Setter for contactTypes 
	 *	Added by : a-5220 on 24-Apr-2014 for ICRD-55852
	 * 	Used for : setting contactTypes
	 */
	public void setContactTypes(String[] contactTypes) {
		this.contactTypes = contactTypes;
	}
	/**
	 * @return the restrictedFOPs
	 */
	public String[] getRestrictedFOPs() {
		return restrictedFOPs;
	}
	/**
	 * @param restrictedFOPs the restrictedFOPs to set
	 */
	public void setRestrictedFOPs(String[] restrictedFOPs) {
		this.restrictedFOPs = restrictedFOPs;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getVersionNumbers
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Version Numbers.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getVersionNumbers() {
		return versionNumbers;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setVersionNumbers
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	: 	Set Version Numbers.Added for ICRD-67442.
	 *	Parameters	:	@param versionNumbers 
	 *	Return type	: 	void
	 */
	public void setVersionNumbers(String[] versionNumbers) {
		this.versionNumbers = versionNumbers;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getNavVersionNumbers
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get NAV Version Numbers.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getNavVersionNumbers() {
		return navVersionNumbers;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setNavVersionNumbers
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set NAV Version Numbers.Added for ICRD-67442.
	 *	Parameters	:	@param navVersionNumbers 
	 *	Return type	: 	void
	 */
	public void setNavVersionNumbers(String[] navVersionNumbers) {
		this.navVersionNumbers = navVersionNumbers;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCusVersionNumber
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Get Version Number.Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getCusVersionNumber() {
		return cusVersionNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCusVersionNumber
	 *	Added by 	:	A-5163 on August 21, 2014
	 * 	Used for 	:	Set Version Number.Added for ICRD-67442.
	 *	Parameters	:	@param cusVersionNumber 
	 *	Return type	: 	void
	 */
	public void setCusVersionNumber(String cusVersionNumber) {
		this.cusVersionNumber = cusVersionNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getIsHistoryPopulated
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Get is History Populated. Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getIsHistoryPopulated() {
		return isHistoryPopulated;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setIsHistoryPopulated
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Set is History Populated. Added for ICRD-67442.
	 *	Parameters	:	@param isHistoryPopulated 
	 *	Return type	: 	void
	 */
	public void setIsHistoryPopulated(String isHistoryPopulated) {
		this.isHistoryPopulated = isHistoryPopulated;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getStatusFlag
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Get Status Flag. Added for ICRD-67442.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getStatusFlag() {
		return statusFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setStatusFlag
	 *	Added by 	:	A-5163 on August 22, 2014
	 * 	Used for 	:	Get Status Flag. Added for ICRD-67442.
	 *	Parameters	:	@param statusFlag 
	 *	Return type	: 	void
	 */
	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getIsLatestVersion
	 *	Added by 	:	A-5163 on August 28, 2014
	 * 	Used for 	:	Get Is Latest Version.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getIsLatestVersion() {
		return isLatestVersion;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setIsLatestVersion
	 *	Added by 	:	A-5163 on August 28, 2014
	 * 	Used for 	:	Set Is Latest Version.
	 *	Parameters	:	@param isLatestVersion 
	 *	Return type	: 	void
	 */
	public void setIsLatestVersion(String isLatestVersion) {
		this.isLatestVersion = isLatestVersion;
	}
	/**
	 * @return the sourcePage
	 */
	public String getSourcePage() {
		return sourcePage;
	}
	/**
	 * @param sourcePage the sourcePage to set
	 */
	public void setSourcePage(String sourcePage) {
		this.sourcePage = sourcePage;
	}
	public String getGibCustomerFlag() {
		return gibCustomerFlag;
	}
	public void setGibCustomerFlag(String gibCustomerFlag) {
		this.gibCustomerFlag = gibCustomerFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getGibRegistrationDate
	 *	Added by 	:	A-5163 on November 5, 2014
	 * 	Used for 	:	Added for ICRD-78230.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getGibRegistrationDate() {
		return gibRegistrationDate;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setGibRegistrationDate
	 *	Added by 	:	A-5163 on November 5, 2014
	 * 	Used for 	:	Added for ICRD-78230.
	 *	Parameters	:	@param gibRegistrationDate 
	 *	Return type	: 	void
	 */
	public void setGibRegistrationDate(String gibRegistrationDate) {
		this.gibRegistrationDate = gibRegistrationDate;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getPublicSectorFlag
	 *	Added by 	:	A-5163 on November 5, 2014
	 * 	Used for 	:	Added for ICRD-78230.
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getPublicSectorFlag() {
		return publicSectorFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setPublicSectorFlag
	 *	Added by 	:	A-5163 on November 5, 2014
	 * 	Used for 	:	Added for ICRD-78230.
	 *	Parameters	:	@param publicSectorFlag 
	 *	Return type	: 	void
	 */
	public void setPublicSectorFlag(String publicSectorFlag) {
		this.publicSectorFlag = publicSectorFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCustomerCertificateType
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getCustomerCertificateType() {
		return customerCertificateType;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCustomerCertificateType
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param customerCertificateType 
	 *	Return type	: 	void
	 */
	public void setCustomerCertificateType(String[] customerCertificateType) {
		this.customerCertificateType = customerCertificateType;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCustomerCertificateNumber
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getCustomerCertificateNumber() {
		return customerCertificateNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCustomerCertificateNumber
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param customerCertificateNumber 
	 *	Return type	: 	void
	 */
	public void setCustomerCertificateNumber(String[] customerCertificateNumber) {
		this.customerCertificateNumber = customerCertificateNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCertificateValidFrom
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getCertificateValidFrom() {
		return certificateValidFrom;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCertificateValidFrom
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param certificateValidFrom 
	 *	Return type	: 	void
	 */
	public void setCertificateValidFrom(String[] certificateValidFrom) {
		this.certificateValidFrom = certificateValidFrom;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCertificateValidTo
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getCertificateValidTo() {
		return certificateValidTo;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCertificateValidTo
	 *	Added by 	:	A-5163 on December 29, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param certificateValidTo 
	 *	Return type	: 	void
	 */
	public void setCertificateValidTo(String[] certificateValidTo) {
		this.certificateValidTo = certificateValidTo;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getHiddenOpFlagForCertificate
	 *	Added by 	:	A-5163 on December 30, 2014
	 * 	Used for 	:	ICRD-58628.	
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getHiddenOpFlagForCertificate() {
		return hiddenOpFlagForCertificate;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setHiddenOpFlagForCertificate
	 *	Added by 	:	A-5163 on December 30, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param hiddenOpFlagForCertificate 
	 *	Return type	: 	void
	 */
	public void setHiddenOpFlagForCertificate(String[] hiddenOpFlagForCertificate) {
		this.hiddenOpFlagForCertificate = hiddenOpFlagForCertificate;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCertificateSequenceNumber
	 *	Added by 	:	A-5163 on December 30, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getCertificateSequenceNumber() {
		return certificateSequenceNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCertificateSequenceNumber
	 *	Added by 	:	A-5163 on December 30, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param certificateSequenceNumber 
	 *	Return type	: 	void
	 */
	public void setCertificateSequenceNumber(String[] certificateSequenceNumber) {
		this.certificateSequenceNumber = certificateSequenceNumber;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getRowIdCertifications
	 *	Added by 	:	A-5163 on December 31, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 */
	public String[] getRowIdCertifications() {
		return rowIdCertifications;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setRowIdCertifications
	 *	Added by 	:	A-5163 on December 31, 2014
	 * 	Used for 	:	ICRD-58628.
	 *	Parameters	:	@param rowIdCertifications 
	 *	Return type	: 	void
	 */
	public void setRowIdCertifications(String[] rowIdCertifications) {
		this.rowIdCertifications = rowIdCertifications;
	}
	//CRQ ID:117235 - A-5127 added
	/**
	 * @return the recipientCode
	 */
	public String getRecipientCode() {
		return recipientCode;
	}
	/**
	 * @param recipientCode the recipientCode to set
	 */
	public void setRecipientCode(String recipientCode) {
		this.recipientCode = recipientCode;
	}
	/**
	 * @return the cassImportIdentifier
	 */
	public String getCassImportIdentifier() {
		return cassImportIdentifier;
	}
	/**
	 * @param cassImportIdentifier the cassImportIdentifier to set
	 */
	public void setCassImportIdentifier(String cassImportIdentifier) {
		this.cassImportIdentifier = cassImportIdentifier;
	}
	/**
	 * @return the hiddenOpFlagForAddtContacts
	 */
	public String[] getHiddenOpFlagForAddtContacts() {
		return hiddenOpFlagForAddtContacts;
	}
	/**
	 * @param hiddenOpFlagForAddtContacts the hiddenOpFlagForAddtContacts to set
	 */
	public void setHiddenOpFlagForAddtContacts(String[] hiddenOpFlagForAddtContacts) {
		this.hiddenOpFlagForAddtContacts = hiddenOpFlagForAddtContacts;
	}
	//End - CRQ ID:117235 - A-5127 added
	//Start - CRQ ID:162691 - A-5127 added
	/**
	 * 	Getter for notificationFormat 
	 *	Added by : a-5127 on Dec 8, 2016
	 * 	Used for :
	 */
	public String getNotificationFormat() {
		return notificationFormat;
	}
	/**
	 *  @param notificationFormat the notificationFormat to set
	 * 	Setter for notificationFormat 
	 *	Added by : a-5127 on Dec 8, 2016
	 * 	Used for :
	 */
	public void setNotificationFormat(String notificationFormat) {
		this.notificationFormat = notificationFormat;
	}
	//End - CRQ ID:162691 - A-5127 added  
/**
	 * @return the notifyShipmentType
	 */
	public String getNotifyShipmentType() {
		return notifyShipmentType;
	}
	/**
	 * @param notifyShipmentType the notifyShipmentType to set
	 */
	public void setNotifyShipmentType(String notifyShipmentType) {
		this.notifyShipmentType = notifyShipmentType;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getInternalAccountHolder
	 *	Added by 	:	A-7534 on 12-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getInternalAccountHolder() {
		return internalAccountHolder;
	}
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setInternalAccountHolder
	 *	Added by 	:	A-7534 on 12-Feb-2018
	 * 	Used for 	:
	 *	Parameters	:	@param internalAccountHolder 
	 *	Return type	: 	void
	 */
	public void setInternalAccountHolder(String internalAccountHolder) {
		this.internalAccountHolder = internalAccountHolder;
	}

	/**
	 * 
	 * @return stockAutomationRequired
	 */
	public String getStockAutomationRequired() {
		return stockAutomationRequired;
	}


	public void setStockAutomationRequired(String stockAutomationRequired) {
		this.stockAutomationRequired = stockAutomationRequired;
	}

	/**
	 * 
	 * @return stockAutomationUserConfirmed
	 */
	public String getStockAutomationUserConfirmed() {
		return stockAutomationUserConfirmed;
	}


	public void setStockAutomationUserConfirmed(String stockAutomationUserConfirmed) {
		this.stockAutomationUserConfirmed = stockAutomationUserConfirmed;
	}

	/**
	 * 
	 * @return screenFlag
	 */
	public String getScreenFlag() {
		return screenFlag;
	}


	public void setScreenFlag(String screenFlag) {
		this.screenFlag = screenFlag;
	}


	public String getDuplicateStockHolder() {
		return duplicateStockHolder;
	}


	public void setDuplicateStockHolder(String duplicateStockHolder) {
		this.duplicateStockHolder = duplicateStockHolder;
	}


	public String getDuplicateStockHolderOverride() {
		return duplicateStockHolderOverride;
	}


	public void setDuplicateStockHolderOverride(String duplicateStockHolderOverride) {
		this.duplicateStockHolderOverride = duplicateStockHolderOverride;
	}
	 

    /**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.isCcFeeDueGHA
	 *	Added by 	:	A-7656 on Apr 9, 2018
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	boolean
	 */
	public boolean isCcFeeDueGHA() { 
		return isCcFeeDueGHA;
	}

    /**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCcFeeDueGHA
	 *	Added by 	:	A-7656 on Apr 9, 2018
	 * 	Used for 	:
	 *	Parameters	:	@param isCcFeeDueGHA 
	 *	Return type	: 	void
	 */
	public void setCcFeeDueGHA(boolean isCcFeeDueGHA) {
		this.isCcFeeDueGHA = isCcFeeDueGHA;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getDueDateBasis
	 *	Added by 	:	A-7555 on Apr 04, 2019
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getDueDateBasis() {
		return dueDateBasis;
	}

	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setDueDateBasis
	 *	Added by 	:	A-7555 on Apr 04, 2019
	 * 	Used for 	:
	 *	Parameters	:	@param dueDateBasis 
	 *	Return type	: 	void
	 */
	public void setDueDateBasis(String dueDateBasis) {
		this.dueDateBasis = dueDateBasis;
	}
	
	
	public String getCntLocBillingApplicableTo() {
		return cntLocBillingApplicableTo;
	}
	public void setCntLocBillingApplicableTo(String cntLocBillingApplicableTo) {
		this.cntLocBillingApplicableTo = cntLocBillingApplicableTo;
	}
	
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.getCarrier
	 *	Added by 	:	A-8823 on May 03, 2018
	 * 	Used for 	:	Gets the carrier.
	 *	Parameters	:	@return 
	 *	Return type	: 	String[]
	 *
	 *	@return the station code
	 */
	public String[] getAgentCarrier() {
		return agentCarrier;
	}
	
	/**
	 * 
	 * 	Method		:	MaintainRegCustomerForm.setCarrier
	 *	Added by 	:	A-8823 on May 03, 2018
	 * 	Used for 	:	Sets the carrier.
	 *	Parameters	:	@param carrier 
	 *	Return type	: 	void
	 *
	 *	@param stationCode the new station code
	 */
	public void setAgentCarrier(String[] agentCarrier) {
		this.agentCarrier = agentCarrier;
	}
	/**
	 * 	Getter for agentReferenceId 
	 *	Added by : A-8146 on 04-Feb-2020
	 * 	Used for :
	 */
	public String[] getAgentReferenceId() {
		return agentReferenceId;
	}
	/**
	 *  @param agentReferenceId the agentReferenceId to set
	 * 	Setter for agentReferenceId 
	 *	Added by : A-8146 on 04-Feb-2020
	 * 	Used for :
	 */
	public void setAgentReferenceId(String[] agentReferenceId) {
		this.agentReferenceId = agentReferenceId;
	}
	
	/*Added by 201930 for IASCB-131790 */
	public String getCassCountry() {
		return cassCountry;
	}
	public void setCassCountry(String cassCountry) {
		this.cassCountry = cassCountry;
	}
}
