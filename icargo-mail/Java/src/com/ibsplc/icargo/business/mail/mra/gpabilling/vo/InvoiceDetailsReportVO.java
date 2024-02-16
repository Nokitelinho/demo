/*
 * InvoiceDetailsReportVO.java Created on Mar 02, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-2270
 * 
 */
public class InvoiceDetailsReportVO extends AbstractVO {

	private String gpaCode;

	private String sector;

	private LocalDate fromBillingPeriod;

	private LocalDate toBillingPeriod;

	private String invoiceNumber;

	private LocalDate billedDate;

	private String billingCurrencyCode;

	private String contractCurrencyCode;

	private double totalAmountinBillingCurrency;

	private double totalAmountinContractCurrency;

	private String fromDateString;

	private String toDateString;

	private String billedDateString;

	private String paName;

	private String address;

	private String countryCode;

	private String conPerson;

	private String state;

	private String country;

	private String mobile;

	private String postCod;

	private String phone1;

	private String phone2;

	private String fax;

	private String email;

	private String city;

	private String mailCategoryCode;
	
	private String vatNumber;
	
	private double totalAmountinsettlementCurrency;

	private long duedays;
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
	/** The signatorOne. */
	private String signatorOne;
	/** The designatorOne. */
	private String designatorOne;
	/** The signatorTwo. */
	private String signatorTwo;
	/** The designatorTwo. */
	private String designatorTwo;
	/** The free text. */
	private String freeText;
	private String currency;
	/** The bank name. */
	private String bankName;
	/** The branch. */
	private String branch;
	/** The acc no. */
	private String accNo;
	/** The city. */
	private String bankCity;
	/** The country. */
	private String bankCountry;
	/** The swift code. */
	private String swiftCode;
	/** The iban no. */
	private String ibanNo;
	private String currencyOne;
	/** The bank name. */
	private String bankNameOne;
	/** The branch. */
	private String branchOne;
	/** The acc no. */
	private String accNoOne;
	/** The city. */
	private String bankCityOne;
	/** The country. */
	private String bankCountryOne;
	/** The swift code. */
	private String swiftCodeOne;
	/** The iban no. */
	private String ibanNoOne;
	private String clearComId;
	private String lastUpdatedUser;
	private String overrideRounding;//added by a-7871 for ICRD-214766
	private Money totalAmtinBillingCurr;//added by a-7871 for ICRD-214766
	
	private String totalAmtinBillingCurrString;
	
	
	
	//Added by A-8331 starts
	
	private String bankCurrency;
	/** The bank name. */
	private String nameOfBank;
	/** The branch. */
	private String bankBranch;
	/** The acc no. */
	private String accountNumber;
	/** The city. */
	private String cityOfBank;
	/** The country. */
	private String CountryOfBank;
	/** The swift code. */
	private String swiftCodeOfBank;
	/** The iban no. */
	private String ibanNoOfBank;
	
	
	//added by A-8331 ends
	
	/**
	 * @author A-10383
	 */
	private String cn66orgcod;
	private String cn66dstcod;
	private String cn66malctgcod;
	private String cn66dsn;
	private String cntidr;
	private String fltdat;
	private String fltnum;
	private String cn66malsubcls;
	private String cn66totwgt;
	private String bldamt;
	private String totamtcp;
	private String srvtax;
	private String netamt;
	private String c51smyblgcurcod;
	private String bldprd;
	private String invdat;
	private String cn66gpacod;
	private String cn66invnum;
	
			private String dateperiod;
	
	public String getDateperiod() {
		return dateperiod;
	}
	public void setDateperiod(String dateperiod) {
		this.dateperiod = dateperiod;
	}
	
	
	public String getCn66orgcod() {
		return cn66orgcod;
	}
	public void setCn66orgcod(String cn66orgcod) {
		this.cn66orgcod = cn66orgcod;
	}
	public String getCn66dstcod() {
		return cn66dstcod;
	}
	public void setCn66dstcod(String cn66dstcod) {
		this.cn66dstcod = cn66dstcod;
	}
	public String getCn66malctgcod() {
		return cn66malctgcod;
	}
	public void setCn66malctgcod(String cn66malctgcod) {
		this.cn66malctgcod = cn66malctgcod;
	}
	public String getCn66dsn() {
		return cn66dsn;
	}
	public void setCn66dsn(String cn66dsn) {
		this.cn66dsn = cn66dsn;
	}

	public String getCntidr() {
		return cntidr;
	}
	public void setCntidr(String cntidr) {
		this.cntidr = cntidr;
	}
	public String getFltdat() {
		return fltdat;
	}
	public void setFltdat(String fltdat) {
		this.fltdat = fltdat;
	}
	public String getFltnum() {
		return fltnum;
	}
	public void setFltnum(String fltnum) {
		this.fltnum = fltnum;
	}
	public String getCn66malsubcls() {
		return cn66malsubcls;
	}
	public void setCn66malsubcls(String cn66malsubcls) {
		this.cn66malsubcls = cn66malsubcls;
	}
	public String getCn66totwgt() {
		return cn66totwgt;
	}
	public void setCn66totwgt(String cn66totwgt) {
		this.cn66totwgt = cn66totwgt;
	}
	public String getBldamt() {
		return bldamt;
	}
	public void setBldamt(String bldamt) {
		this.bldamt = bldamt;
	}
	public String getTotamtcp() {
		return totamtcp;
	}
	public void setTotamtcp(String totamtcp) {
		this.totamtcp = totamtcp;
	}
	public String getSrvtax() {
		return srvtax;
	}
	public void setSrvtax(String srvtax) {
		this.srvtax = srvtax;
	}
	public String getNetamt() {
		return netamt;
	}
	public void setNetamt(String netamt) {
		this.netamt = netamt;
	}
	public String getC51smyblgcurcod() {
		return c51smyblgcurcod;
	}
	public void setC51smyblgcurcod(String c51smyblgcurcod) {
		this.c51smyblgcurcod = c51smyblgcurcod;
	}
	public String getBldprd() {
		return bldprd;
	}
	public void setBldprd(String bldprd) {
		this.bldprd = bldprd;
	}
	public String getInvdat() {
		return invdat;
	}
	public void setInvdat(String invdat) {
		this.invdat = invdat;
	}
	public String getCn66gpacod() {
		return cn66gpacod;
	}
	public void setCn66gpacod(String cn66gpacod) {
		this.cn66gpacod = cn66gpacod;
	}
	public String getCn66invnum() {
		return cn66invnum;
	}
	public void setCn66invnum(String cn66invnum) {
		this.cn66invnum = cn66invnum;
	}
	/**
	 * @author A-10383
	 */
	
	
	/**
	 * @return the totalAmtinBillingCurrString
	 */
	public String getTotalAmtinBillingCurrString() {
		return totalAmtinBillingCurrString;
	}
	/**
	 * @param totalAmtinBillingCurrString the totalAmtinBillingCurrString to set
	 */
	public void setTotalAmtinBillingCurrString(String totalAmtinBillingCurrString) {
		this.totalAmtinBillingCurrString = totalAmtinBillingCurrString;
	}
	/**
	 * @return the totalAmtinBillingCurr
	 */
	public Money getTotalAmtinBillingCurr() {
		return totalAmtinBillingCurr;
	}
	/**
	 * @param totalAmtinBillingCurr the totalAmtinBillingCurr to set
	 */
	public void setTotalAmtinBillingCurr(Money totalAmtinBillingCurr) {
		this.totalAmtinBillingCurr = totalAmtinBillingCurr;
	}
	/**
	 * @return the overrideRounding
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}
	/**
	 * @param overrideRounding the overrideRounding to set
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	/**
	 * @return the currencyOne
	 */
	public String getCurrencyOne() {
		return currencyOne;
	}
	/**
	 * @param currencyOne the currencyOne to set
	 */
	public void setCurrencyOne(String currencyOne) {
		this.currencyOne = currencyOne;
	}
	/**
	 * @return the bankNameOne
	 */
	public String getBankNameOne() {
		return bankNameOne;
	}
	/**
	 * @param bankNameOne the bankNameOne to set
	 */
	public void setBankNameOne(String bankNameOne) {
		this.bankNameOne = bankNameOne;
	}
	/**
	 * @return the branchOne
	 */
	public String getBranchOne() {
		return branchOne;
	}
	/**
	 * @param branchOne the branchOne to set
	 */
	public void setBranchOne(String branchOne) {
		this.branchOne = branchOne;
	}
	/**
	 * @return the accNoOne
	 */
	public String getAccNoOne() {
		return accNoOne;
	}
	/**
	 * @param accNoOne the accNoOne to set
	 */
	public void setAccNoOne(String accNoOne) {
		this.accNoOne = accNoOne;
	}
	/**
	 * @return the bankCityOne
	 */
	public String getBankCityOne() {
		return bankCityOne;
	}
	/**
	 * @param bankCityOne the bankCityOne to set
	 */
	public void setBankCityOne(String bankCityOne) {
		this.bankCityOne = bankCityOne;
	}
	/**
	 * @return the bankCountryOne
	 */
	public String getBankCountryOne() {
		return bankCountryOne;
	}
	/**
	 * @param bankCountryOne the bankCountryOne to set
	 */
	public void setBankCountryOne(String bankCountryOne) {
		this.bankCountryOne = bankCountryOne;
	}
	/**
	 * @return the swiftCodeOne
	 */
	public String getSwiftCodeOne() {
		return swiftCodeOne;
	}
	/**
	 * @param swiftCodeOne the swiftCodeOne to set
	 */
	public void setSwiftCodeOne(String swiftCodeOne) {
		this.swiftCodeOne = swiftCodeOne;
	}
	/**
	 * @return the ibanNoOne
	 */
	public String getIbanNoOne() {
		return ibanNoOne;
	}
	/**
	 * @param ibanNoOne the ibanNoOne to set
	 */
	public void setIbanNoOne(String ibanNoOne) {
		this.ibanNoOne = ibanNoOne;
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
	 * @return the billingSiteCode
	 */
	public String getBillingSiteCode() {
		return billingSiteCode;
	}
	/**
	 * @param billingSiteCode the billingSiteCode to set
	 */
	public void setBillingSiteCode(String billingSiteCode) {
		this.billingSiteCode = billingSiteCode;
	}
	/**
	 * @return the billingSite
	 */
	public String getBillingSite() {
		return billingSite;
	}
	/**
	 * @param billingSite the billingSite to set
	 */
	public void setBillingSite(String billingSite) {
		this.billingSite = billingSite;
	}
	/**
	 * @return the airlineAddress
	 */
	public String getAirlineAddress() {
		return airlineAddress;
	}
	/**
	 * @param airlineAddress the airlineAddress to set
	 */
	public void setAirlineAddress(String airlineAddress) {
		this.airlineAddress = airlineAddress;
	}
	/**
	 * @return the correspondenceAddress
	 */
	public String getCorrespondenceAddress() {
		return correspondenceAddress;
	}
	/**
	 * @param correspondenceAddress the correspondenceAddress to set
	 */
	public void setCorrespondenceAddress(String correspondenceAddress) {
		this.correspondenceAddress = correspondenceAddress;
	}
	/**
	 * @return the signatorOne
	 */
	public String getSignatorOne() {
		return signatorOne;
	}
	/**
	 * @param signatorOne the signatorOne to set
	 */
	public void setSignatorOne(String signatorOne) {
		this.signatorOne = signatorOne;
	}
	/**
	 * @return the designatorOne
	 */
	public String getDesignatorOne() {
		return designatorOne;
	}
	/**
	 * @param designatorOne the designatorOne to set
	 */
	public void setDesignatorOne(String designatorOne) {
		this.designatorOne = designatorOne;
	}
	/**
	 * @return the signatorTwo
	 */
	public String getSignatorTwo() {
		return signatorTwo;
	}
	/**
	 * @param signatorTwo the signatorTwo to set
	 */
	public void setSignatorTwo(String signatorTwo) {
		this.signatorTwo = signatorTwo;
	}
	/**
	 * @return the designatorTwo
	 */
	public String getDesignatorTwo() {
		return designatorTwo;
	}
	/**
	 * @param designatorTwo the designatorTwo to set
	 */
	public void setDesignatorTwo(String designatorTwo) {
		this.designatorTwo = designatorTwo;
	}
	/**
	 * @return the freeText
	 */
	public String getFreeText() {
		return freeText;
	}
	/**
	 * @param freeText the freeText to set
	 */
	public void setFreeText(String freeText) {
		this.freeText = freeText;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	/**
	 * @return the bankName
	 */
	public String getBankName() {
		return bankName;
	}
	/**
	 * @param bankName the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	/**
	 * @return the branch
	 */
	public String getBranch() {
		return branch;
	}
	/**
	 * @param branch the branch to set
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}
	/**
	 * @return the accNo
	 */
	public String getAccNo() {
		return accNo;
	}
	/**
	 * @param accNo the accNo to set
	 */
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	/**
	 * @return the bankCity
	 */
	public String getBankCity() {
		return bankCity;
	}
	/**
	 * @param bankCity the bankCity to set
	 */
	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}
	/**
	 * @return the bankCountry
	 */
	public String getBankCountry() {
		return bankCountry;
	}
	/**
	 * @param bankCountry the bankCountry to set
	 */
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
	/**
	 * @return the swiftCode
	 */
	public String getSwiftCode() {
		return swiftCode;
	}
	/**
	 * @param swiftCode the swiftCode to set
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}
	/**
	 * @return the ibanNo
	 */
	public String getIbanNo() {
		return ibanNo;
	}
	/**
	 * @param ibanNo the ibanNo to set
	 */
	public void setIbanNo(String ibanNo) {
		this.ibanNo = ibanNo;
	}
	/**
	 * @return the mailCategoryCode
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city
	 *            the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the conPerson
	 */
	public String getConPerson() {
		return conPerson;
	}

	/**
	 * @param conPerson
	 *            the conPerson to set
	 */
	public void setConPerson(String conPerson) {
		this.conPerson = conPerson;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 *            the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return the phone1
	 */
	public String getPhone1() {
		return phone1;
	}

	/**
	 * @param phone1
	 *            the phone1 to set
	 */
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	/**
	 * @return the phone2
	 */
	public String getPhone2() {
		return phone2;
	}

	/**
	 * @param phone2
	 *            the phone2 to set
	 */
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	/**
	 * @return the postCod
	 */
	public String getPostCod() {
		return postCod;
	}

	/**
	 * @param postCod
	 *            the postCod to set
	 */
	public void setPostCod(String postCod) {
		this.postCod = postCod;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return the paName
	 */
	public String getPaName() {
		return paName;
	}

	/**
	 * @param paName
	 *            the paName to set
	 */
	public void setPaName(String paName) {
		this.paName = paName;
	}

	/**
	 * @return Returns the billedDateString.
	 */
	public String getBilledDateString() {
		return billedDateString;
	}

	/**
	 * @param billedDateString
	 *            The billedDateString to set.
	 */
	public void setBilledDateString(String billedDateString) {
		this.billedDateString = billedDateString;
	}

	/**
	 * @return Returns the fromDateString.
	 */
	public String getFromDateString() {
		return fromDateString;
	}

	/**
	 * @param fromDateString
	 *            The fromDateString to set.
	 */
	public void setFromDateString(String fromDateString) {
		this.fromDateString = fromDateString;
	}

	/**
	 * @return Returns the toDateString.
	 */
	public String getToDateString() {
		return toDateString;
	}

	/**
	 * @param toDateString
	 *            The toDateString to set.
	 */
	public void setToDateString(String toDateString) {
		this.toDateString = toDateString;
	}

	/**
	 * @return Returns the billedDate.
	 */
	public LocalDate getBilledDate() {
		return billedDate;
	}

	/**
	 * @param billedDate
	 *            The billedDate to set.
	 */
	public void setBilledDate(LocalDate billedDate) {
		this.billedDate = billedDate;
	}

	/**
	 * @return Returns the billingCurrencyCode.
	 */
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}

	/**
	 * @param billingCurrencyCode
	 *            The billingCurrencyCode to set.
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}

	/**
	 * @return Returns the fromBillingPeriod.
	 */
	public LocalDate getFromBillingPeriod() {
		return fromBillingPeriod;
	}

	/**
	 * @param fromBillingPeriod
	 *            The fromBillingPeriod to set.
	 */
	public void setFromBillingPeriod(LocalDate fromBillingPeriod) {
		this.fromBillingPeriod = fromBillingPeriod;
	}

	/**
	 * @return Returns the gpaCode.
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
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
	 * @param invoiceNumber
	 *            The invoiceNumber to set.
	 */
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	/**
	 * @return Returns the sector.
	 */
	public String getSector() {
		return sector;
	}

	/**
	 * @param sector
	 *            The sector to set.
	 */
	public void setSector(String sector) {
		this.sector = sector;
	}

	/**
	 * @return Returns the toBillingPeriod.
	 */
	public LocalDate getToBillingPeriod() {
		return toBillingPeriod;
	}

	/**
	 * @param toBillingPeriod
	 *            The toBillingPeriod to set.
	 */
	public void setToBillingPeriod(LocalDate toBillingPeriod) {
		this.toBillingPeriod = toBillingPeriod;
	}

	/**
	 * @return Returns the totalAmountinBillingCurrency.
	 */
	public double getTotalAmountinBillingCurrency() {
		return totalAmountinBillingCurrency;
	}

	/**
	 * @param totalAmountinBillingCurrency
	 *            The totalAmountinBillingCurrency to set.
	 */
	public void setTotalAmountinBillingCurrency(
			double totalAmountinBillingCurrency) {
		this.totalAmountinBillingCurrency = totalAmountinBillingCurrency;
	}

	/**
	 * @return Returns the contractCurrencyCode.
	 */
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}

	/**
	 * @param contractCurrencyCode
	 *            The contractCurrencyCode to set.
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}

	/**
	 * @return Returns the totalAmountinContractCurrency.
	 */
	public double getTotalAmountinContractCurrency() {
		return totalAmountinContractCurrency;
	}

	/**
	 * @param totalAmountinContractCurrency
	 *            The totalAmountinContractCurrency to set.
	 */
	public void setTotalAmountinContractCurrency(
			double totalAmountinContractCurrency) {
		this.totalAmountinContractCurrency = totalAmountinContractCurrency;
	}

	/**
	 * @return the totalAmountinsettlementCurrency
	 */
	public double getTotalAmountinsettlementCurrency() {
		return totalAmountinsettlementCurrency;
	}

	/**
	 * @param totalAmountinsettlementCurrency the totalAmountinsettlementCurrency to set
	 */
	public void setTotalAmountinsettlementCurrency(
			double totalAmountinsettlementCurrency) {
		this.totalAmountinsettlementCurrency = totalAmountinsettlementCurrency;
	}

	public String getVatNumber() {
		return vatNumber;
	}

	public void setVatNumber(String vatNumber) {
		this.vatNumber = vatNumber;
	}

	/**
	 * @param clearComId the clearComId to set
	 */
	public void setClearComId(String clearComId) {
		this.clearComId = clearComId;
	}
	/**
	 * @return the clearComId
	 */
	public String getClearComId() {
		return clearComId;
	}
	public long getDuedays() {
		return duedays;
	}
	public void setDuedays(long duedays) {
		this.duedays = duedays;
	}
	public String getBankCurrency() {
		return bankCurrency;
	}
	public void setBankCurrency(String bankCurrency) {
		this.bankCurrency = bankCurrency;
	}
	public String getNameOfBank() {
		return nameOfBank;
	}
	public void setNameOfBank(String nameOfBank) {
		this.nameOfBank = nameOfBank;
	}
	public String getBankBranch() {
		return bankBranch;
	}
	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getCityOfBank() {
		return cityOfBank;
	}
	public void setCityOfBank(String cityOfBank) {
		this.cityOfBank = cityOfBank;
	}
	public String getCountryOfBank() {
		return CountryOfBank;
	}
	public void setCountryOfBank(String countryOfBank) {
		CountryOfBank = countryOfBank;
	}
	public String getSwiftCodeOfBank() {
		return swiftCodeOfBank;
	}
	public void setSwiftCodeOfBank(String swiftCodeOfBank) {
		this.swiftCodeOfBank = swiftCodeOfBank;
	}
	public String getIbanNoOfBank() {
		return ibanNoOfBank;
	}
	public void setIbanNoOfBank(String ibanNoOfBank) {
		this.ibanNoOfBank = ibanNoOfBank;
	}
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	private String invDateMMMformat;
	private String invNumberFinancial;
	public String getInvNumberFinancial() {
		return invNumberFinancial;
	}
	public void setInvNumberFinancial(String invNumberFinancial) {
		this.invNumberFinancial = invNumberFinancial;
	}
	public String getInvDateMMMformat() {
		return invDateMMMformat;
	}
	public void setInvDateMMMformat(String invDateMMMformat) {
		this.invDateMMMformat = invDateMMMformat;
	}
	
	private String bnkdtlAccnum;
	public String getBnkdtlAccnum() {
		return bnkdtlAccnum;
	}
	public void setBnkdtlAccnum(String bnkdtlAccnum) {
		this.bnkdtlAccnum = bnkdtlAccnum;
	}
}
