/*
 * MRAMaintainCCAForm.java Created on Feb 28, 2007
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.web.struts.form.mail.mra.defaults;

import com.ibsplc.icargo.framework.model.ScreenModel;


    /**
	 * @author A-3447
	 *
     */

public class MRAMaintainCCAForm extends ScreenModel {

	private static final long serialVersionUID = 1L;

	private static final String BUNDLE = "maintainCCA";

	// private String bundle;

	private static final String PRODUCT = "mail";

	private static final String SUBPRODUCT = "mra";

	private static final String SCREENID = "mailtracking.mra.defaults.maintaincca";

	private String ccaNum;

	private String dsnNumber;

	private String dsnDate;

	private String ccaStatus;

	private String issueDate;

	private String issueParty;

	private String airlineCode;

	private String airlineLoc;

	private String ccaType;

	private String bilfrmdate;

	private String biltodate;

	private String origin;

	private String destination;

	private String category;

	private String subclass;

	private String reason1;

	private String reason2;

	private String reason3;

	private String reason4;

	private String reason5;

	private String remarks;

	private String comboFlag;

	private String grossWeight;

	private String revGrossWeight;

	private String revChgGrossWeight;

	private String chgGrossWeight;

	private String weightCharge;

	private String dueAirline;

	private String revDueArl;

	private String duePost;

	private String revDuePost;

	private String dstCode;

	private String originCode;
	
	private String destnCode;

	private String revOrgCode;

	private String revDStCode;

	private String curCode;

	private String gpaCode;

	private String revGpaCode;

	private String gpaName;

	private String revGpaName;

	private String showpopUP;

	private String selectedRow;

	private String popupon;

	private String currentDialogOption;
	private String currentDialogId;
	private String revDuePostDbt;
	
	private String count;
	
	private String revisedChargeGrossWeignt;
	
	private String closeFlag;
	
	private String dsnPopupFlag;
	
	private String createCCAFlg;
	
	private String fromScreen;
	
	private String usrCCANumFlg;
	
	private String autoratedFlag;
	
	private String rateAuditedFlag;
	//Added for CRQ-39791
	private String differenceAmount; 
	//added for AirNZ846
	private String billingStatus; 
	//Added for ICRD-76551
	private String[] chargeHeadName;
	private String[] revSurCharge;
	private String[] orgSurCharge;
	private String[] surchargeOpFlag;
	private String otherRevChgGrossWgt;
	private String otherChgGrossWgt;
	private String surChargeAction;
	private String  revSurChargeTotal;
	private String  orgSurChargeTotal;
	
	private String overrideRounding;//added by a-7871 for ICRD-214766
	
	private String rate; //Added by A-7929 as part of ICRD-132548
    private String revisedRate; //Added by A-7929 as part of ICRD-132548

	//Added by A-7540

    private String[]  surchareOrgRate ;
	private String[] surchargeRevRate;
	private String rateChangeInd;
    private String isAutoMca;
    //added for IASCB-858
    private String[] reasonCheck;
    private String reasonCodeRestrictionFlag ;
    
    // added for navigation from MRA079 into MRA072 starts here 
    
    private String blgFromDate;
    
    private String blgToDate;
    
    private String blgCsgDocNum;
    
    public String[] getSurchareOrgRate() {
		return surchareOrgRate;
	}

	public void setSurchareOrgRate(String[] surchareOrgRate) {
		this.surchareOrgRate = surchareOrgRate;
	}

    private String blgBillingStatus;
    
    private String blgGpaCode;
    
    private String blgOriginOfficeofExchange;
    
    private String blgDestinationOfficeofExchange;
    
    
    private String blgOrigin ;
    
    private String blgDestination ;
    
    private String blgCategory ;
    
    private String blgSubclass;
    
    private String blgYear ;
    
    private String blgDSN ;
    
    private String blgRSN ;
    
    private String blgHNI ;
    
    private String blgRI ;
    
    private String blgMailbagId ;
    
    private String blgUSPSPerformancemet ;
    
    private String blgratebasis ;
    
    private String blgTotalRecords;
    private String blgDefaultPageSize ;
    private String blgDisplayPage ;
    // added for navigation from MRA079 ends here 

    // added for IASCB-22920 by A-9002		 
 	private double displayWgt;	 
 	public double getDisplayWgt() {
		return displayWgt;
	}

	public void setDisplayWgt(double displayWgt) {
		this.displayWgt = displayWgt;
	}

	public String getDisplayWgtUnit() {
		return displayWgtUnit;
	}

	public void setDisplayWgtUnit(String displayWgtUnit) {
		this.displayWgtUnit = displayWgtUnit;
	}

	private String displayWgtUnit;
 	//Ends
    
    
	public String getIsAutoMca() {
		return isAutoMca;
	}

	public void setIsAutoMca(String isAutoMca) {
		this.isAutoMca = isAutoMca;
	}
	


		

	public String[] getSurchargeRevRate() {
		return surchargeRevRate;
	}

	public void setSurchargeRevRate(String[] surchargeRevRate) {
		this.surchargeRevRate = surchargeRevRate;
	}

	public String getRateChangeInd() {
		return rateChangeInd;
	}

	public void setRateChangeInd(String rateChangeInd) {
		this.rateChangeInd = rateChangeInd;
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
	 * @return the otherRevChgGrossWgt
	 */
	public String getOtherRevChgGrossWgt() {
		return otherRevChgGrossWgt;
	}

	/**
	 * @param otherRevChgGrossWgt the otherRevChgGrossWgt to set
	 */
	public void setOtherRevChgGrossWgt(String otherRevChgGrossWgt) {
		this.otherRevChgGrossWgt = otherRevChgGrossWgt;
	}

	/**
	 * @return the otherChgGrossWgt
	 */
	public String getOtherChgGrossWgt() {
		return otherChgGrossWgt;
	}

	/**
	 * @param otherChgGrossWgt the otherChgGrossWgt to set
	 */
	public void setOtherChgGrossWgt(String otherChgGrossWgt) {
		this.otherChgGrossWgt = otherChgGrossWgt;
	}

	/**
	 * @return the chargeHeadName
	 */
	public String[] getChargeHeadName() {
		return chargeHeadName;
	}

	/**
	 * @param chargeHeadName the chargeHeadName to set
	 */
	public void setChargeHeadName(String[] chargeHeadName) {
		this.chargeHeadName = chargeHeadName;
	}

	/**
	 * @return the revSurCharge
	 */
	public String[] getRevSurCharge() {
		return revSurCharge;
	}

	/**
	 * @param revSurCharge the revSurCharge to set
	 */
	public void setRevSurCharge(String[] revSurCharge) {
		this.revSurCharge = revSurCharge;
	}

	/**
	 * @return the orgSurCharge
	 */
	public String[] getOrgSurCharge() {
		return orgSurCharge;
	}

	/**
	 * @param orgSurCharge the orgSurCharge to set
	 */
	public void setOrgSurCharge(String[] orgSurCharge) {
		this.orgSurCharge = orgSurCharge;
	}

	/**
	 * @return the differenceAmount
	 */
	public String getDifferenceAmount() {
		return differenceAmount;
	}

	/**
	 * @param differenceAmount the differenceAmount to set
	 */
	public void setDifferenceAmount(String differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	//added for AirNZ846
	
	private String fromGPABillingInvoiceEnquiry;
	private String counter;
//	Added By Deepthi for DSN pop up
	private String showDsnPopUp;
	//Added by deepthi for controlling the from screen flag
	private String afterSave;
	
	private String disableFlag;
	//Added by a-4823 for ICRD-7352
	private String conDocNo;
	private String revCurCode;
	private String revTax;
	private String curChgInd;
	private String privilegeFlag;
	//Added as part of bug-icrd-19830 by a-4810
	private String  CcaPresent;
	public String getPrivilegeFlag() {
		return privilegeFlag;
	}

	public void setPrivilegeFlag(String privilegeFlag) {
		this.privilegeFlag = privilegeFlag;
	}

	public String getCurChgInd() {
		return curChgInd;
	}

	public void setCurChgInd(String curChgInd) {
		this.curChgInd = curChgInd;
	}

	private String tax;



	public String getRevTax() {
		return revTax;
	}

	public void setRevTax(String revTax) {
		this.revTax = revTax;
	}

	public String getTax() {
		return tax;
	}

	public void setTax(String tax) {
		this.tax = tax;
	}

	public String getRevCurCode() {
		return revCurCode;
	}

	public void setRevCurCode(String revCurCode) {
		this.revCurCode = revCurCode;
	}

	public String getConDocNo() {
		return conDocNo;
	}

	public void setConDocNo(String conDocNo) {
		this.conDocNo = conDocNo;
	}

	/**
	 * @return the showDsnPopUp
	 */
	public String getShowDsnPopUp() {
		return showDsnPopUp;
	}

	/**
	 * @param showDsnPopUp the showDsnPopUp to set
	 */
	public void setShowDsnPopUp(String showDsnPopUp) {
		this.showDsnPopUp = showDsnPopUp;
	}

	/**
	 * @return the rateAuditedFlag
	 */
	public String getRateAuditedFlag() {
		return rateAuditedFlag;
	}

	/**
	 * @param rateAuditedFlag the rateAuditedFlag to set
	 */
	public void setRateAuditedFlag(String rateAuditedFlag) {
		this.rateAuditedFlag = rateAuditedFlag;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public String getFromGPABillingInvoiceEnquiry() {
		return fromGPABillingInvoiceEnquiry;
	}

	public void setFromGPABillingInvoiceEnquiry(String fromGPABillingInvoiceEnquiry) {
		this.fromGPABillingInvoiceEnquiry = fromGPABillingInvoiceEnquiry;
	}

	/**
	 * @return the closeFlag
	 */
	public String getCloseFlag() {
		return closeFlag;
	}

	/**
	 * @param closeFlag the closeFlag to set
	 */
	public void setCloseFlag(String closeFlag) {
		this.closeFlag = closeFlag;
	}

	/**
	 * @return the count
	 */
	public String getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(String count) {
		this.count = count;
	}

	/**
	 * @return the revDuePostDbt
	 */
	public String getRevDuePostDbt() {
		return revDuePostDbt;
	}

	/**
	 * @param revDuePostDbt the revDuePostDbt to set
	 */
	public void setRevDuePostDbt(String revDuePostDbt) {
		this.revDuePostDbt = revDuePostDbt;
	}

	/**
	 * @return the currentDialogOption
	 */
	public String getCurrentDialogOption() {
		return currentDialogOption;
	}

	/**
	 * @param currentDialogOption
	 *            the currentDialogOption to set
	 */
	public void setCurrentDialogOption(String currentDialogOption) {
		this.currentDialogOption = currentDialogOption;
	}

	/**
	 * @return the popupon
	 */
	public String getPopupon() {
		return popupon;
	}

	/**
	 * @param popupon
	 *            the popupon to set
	 */
	public void setPopupon(String popupon) {
		this.popupon = popupon;
	}

	/**
	 * @return the selectedRow
	 */
	public String getSelectedRow() {
		return selectedRow;
	}

	/**
	 * @param selectedRow
	 *            the selectedRow to set
	 */
	public void setSelectedRow(String selectedRow) {
		this.selectedRow = selectedRow;
	}

	/**
	 * @return the showpopUP
	 */
	public String getShowpopUP() {
		return showpopUP;
	}

	/**
	 * @param showpopUP
	 *            the showpopUP to set
	 */
	public void setShowpopUP(String showpopUP) {
		this.showpopUP = showpopUP;
	}

	/**
	 * @return the curCode
	 */
	public String getCurCode() {
		return curCode;
	}

	/**
	 * @param curCode
	 *            the curCode to set
	 */
	public void setCurCode(String curCode) {
		this.curCode = curCode;
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
	 * @return the revDStCode
	 */
	public String getRevDStCode() {
		return revDStCode;
	}

	/**
	 * @param revDStCode
	 *            the revDStCode to set
	 */
	public void setRevDStCode(String revDStCode) {
		this.revDStCode = revDStCode;
	}

	/**
	 * @return the revGpaCode
	 */
	public String getRevGpaCode() {
		return revGpaCode;
	}

	/**
	 * @param revGpaCode
	 *            the revGpaCode to set
	 */
	public void setRevGpaCode(String revGpaCode) {
		this.revGpaCode = revGpaCode;
	}

	/**
	 * @return the revGpaName
	 */
	public String getRevGpaName() {
		return revGpaName;
	}

	/**
	 * @param revGpaName
	 *            the revGpaName to set
	 */
	public void setRevGpaName(String revGpaName) {
		this.revGpaName = revGpaName;
	}

	/**
	 * @return the revOrgCode
	 */
	public String getRevOrgCode() {
		return revOrgCode;
	}

	/**
	 * @param revOrgCode
	 *            the revOrgCode to set
	 */
	public void setRevOrgCode(String revOrgCode) {
		this.revOrgCode = revOrgCode;
	}

	/**
	 * @return the comboFlag
	 */
	public String getComboFlag() {
		return comboFlag;
	}

	/**
	 * @param comboFlag
	 *            the comboFlag to set
	 */
	public void setComboFlag(String comboFlag) {
		this.comboFlag = comboFlag;
	}

	/**
	 *
	 */
	public String getScreenId() {
		// TODO Auto-generated method stub
		return SCREENID;
	}

	/**
	 *
	 */
	public String getProduct() {
		// TODO Auto-generated method stub
		return PRODUCT;
	}

	/**
	 *
	 */
	public String getSubProduct() {
		// TODO Auto-generated method stub
		return SUBPRODUCT;
	}

	/**
	 * @return Returns the bundle.
	 */
	public String getBundle() {
		return BUNDLE;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the airlineLoc.
	 */
	public String getAirlineLoc() {
		return airlineLoc;
	}

	/**
	 * @param airlineLoc
	 *            The airlineLoc to set.
	 */
	public void setAirlineLoc(String airlineLoc) {
		this.airlineLoc = airlineLoc;
	}

	/**
	 * @return Returns the bilfrmdate.
	 */
	public String getBilfrmdate() {
		return bilfrmdate;
	}

	/**
	 * @param bilfrmdate
	 *            The bilfrmdate to set.
	 */
	public void setBilfrmdate(String bilfrmdate) {
		this.bilfrmdate = bilfrmdate;
	}

	/**
	 * @return Returns the biltodate.
	 */
	public String getBiltodate() {
		return biltodate;
	}

	/**
	 * @param biltodate
	 *            The biltodate to set.
	 */
	public void setBiltodate(String biltodate) {
		this.biltodate = biltodate;
	}

	/**
	 * @return Returns the category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            The category to set.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return Returns the ccaNum.
	 */
	public String getCcaNum() {
		return ccaNum;
	}

	/**
	 * @param ccaNum
	 *            The ccaNum to set.
	 */
	public void setCcaNum(String ccaNum) {
		this.ccaNum = ccaNum;
	}

	/**
	 * @return Returns the ccaStatus.
	 */
	public String getCcaStatus() {
		return ccaStatus;
	}

	/**
	 * @param ccaStatus
	 *            The ccaStatus to set.
	 */
	public void setCcaStatus(String ccaStatus) {
		this.ccaStatus = ccaStatus;
	}

	/**
	 * @return Returns the ccaType.
	 */
	public String getCcaType() {
		return ccaType;
	}

	/**
	 * @param ccaType
	 *            The ccaType to set.
	 */
	public void setCcaType(String ccaType) {
		this.ccaType = ccaType;
	}

	/**
	 * @return Returns the destn.
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destn
	 *            The destn to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the dsnNumber
	 */
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber
	 *            the dsnNumber to set
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return Returns the dsnDate.
	 */
	public String getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            The dsnDate to set.
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return Returns the issueDate.
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate
	 *            The issueDate to set.
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return Returns the issueParty.
	 */
	public String getIssueParty() {
		return issueParty;
	}

	/**
	 * @param issueParty
	 *            The issueParty to set.
	 */
	public void setIssueParty(String issueParty) {
		this.issueParty = issueParty;
	}

	/**
	 * @return Returns the origin.
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the reason1.
	 */
	public String getReason1() {
		return reason1;
	}

	/**
	 * @param reason1
	 *            The reason1 to set.
	 */
	public void setReason1(String reason1) {
		this.reason1 = reason1;
	}

	/**
	 * @return Returns the reason2.
	 */
	public String getReason2() {
		return reason2;
	}

	/**
	 * @param reason2
	 *            The reason2 to set.
	 */
	public void setReason2(String reason2) {
		this.reason2 = reason2;
	}

	/**
	 * @return Returns the reason3.
	 */
	public String getReason3() {
		return reason3;
	}

	/**
	 * @param reason3
	 *            The reason3 to set.
	 */
	public void setReason3(String reason3) {
		this.reason3 = reason3;
	}

	/**
	 * @return Returns the reason4.
	 */
	public String getReason4() {
		return reason4;
	}

	/**
	 * @param reason4
	 *            The reason4 to set.
	 */
	public void setReason4(String reason4) {
		this.reason4 = reason4;
	}

	/**
	 * @return Returns the reason5.
	 */
	public String getReason5() {
		return reason5;
	}

	/**
	 * @param reason5
	 *            The reason5 to set.
	 */
	public void setReason5(String reason5) {
		this.reason5 = reason5;
	}

	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks
	 *            The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return Returns the subclass.
	 */
	public String getSubclass() {
		return subclass;
	}

	/**
	 * @param subclass
	 *            The subclass to set.
	 */
	public void setSubclass(String subclass) {
		this.subclass = subclass;
	}

	/**
	 * @return the grossWeight
	 */
	public String getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight
	 *            the grossWeight to set
	 */
	public void setGrossWeight(String grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return the revGrossWeight
	 */
	public String getRevGrossWeight() {
		return revGrossWeight;
	}

	/**
	 * @param revGrossWeight
	 *            the revGrossWeight to set
	 */
	public void setRevGrossWeight(String revGrossWeight) {
		this.revGrossWeight = revGrossWeight;
	}

	/**
	 * @return the dueAirline
	 */
	public String getDueAirline() {
		return dueAirline;
	}

	/**
	 * @param dueAirline
	 *            the dueAirline to set
	 */
	public void setDueAirline(String dueAirline) {
		this.dueAirline = dueAirline;
	}

	/**
	 * @return the duePost
	 */
	public String getDuePost() {
		return duePost;
	}

	/**
	 * @param duePost
	 *            the duePost to set
	 */
	public void setDuePost(String duePost) {
		this.duePost = duePost;
	}

	/**
	 * @return the revDueAirline
	 */
	public String getRevDueArl() {
		return revDueArl;
	}

	/**
	 * @param revDueAirline
	 *            the revDueAirline to set
	 */
	public void setRevDueArl(String revDueArl) {
		this.revDueArl = revDueArl;
	}

	/**
	 * @return the revDuePost
	 */
	public String getRevDuePost() {
		return revDuePost;
	}

	/**
	 * @param revDuePost
	 *            the revDuePost to set
	 */
	public void setRevDuePost(String revDuePost) {
		this.revDuePost = revDuePost;
	}

	/**
	 * @return the weightCharge
	 */
	public String getWeightCharge() {
		return weightCharge;
	}

	/**
	 * @param weightCharge
	 *            the weightCharge to set
	 */
	public void setWeightCharge(String weightCharge) {
		this.weightCharge = weightCharge;
	}

	/**
	 * @return the dstCode
	 */
	public String getDstCode() {
		return dstCode;
	}

	/**
	 * @param dstCode
	 *            the dstCode to set
	 */
	public void setDstCode(String dstCode) {
		this.dstCode = dstCode;
	}

	/**
	 * @return the originCode
	 */
	public String getOriginCode() {
		return originCode;
	}

	/**
	 * @param originCode
	 *            the originCode to set
	 */
	public void setOriginCode(String originCode) {
		this.originCode = originCode;
	}

	/**
	 * @return the revChgGrossWeight
	 */
	public String getRevChgGrossWeight() {
		return revChgGrossWeight;
	}

	/**
	 * @param revChgGrossWeight
	 *            the revChgGrossWeight to set
	 */
	public void setRevChgGrossWeight(String revChgGrossWeight) {
		this.revChgGrossWeight = revChgGrossWeight;
	}

	/**
	 * @return the chgGrossWeight
	 */
	public String getChgGrossWeight() {
		return chgGrossWeight;
	}

	/**
	 * @param chgGrossWeight
	 *            the chgGrossWeight to set
	 */
	public void setChgGrossWeight(String chgGrossWeight) {
		this.chgGrossWeight = chgGrossWeight;
	}

	/**
	 * @return the currentDialogId
	 */
	public String getCurrentDialogId() {
		return currentDialogId;
	}

	/**
	 * @param currentDialogId the currentDialogId to set
	 */
	public void setCurrentDialogId(String currentDialogId) {
		this.currentDialogId = currentDialogId;
	}

	/**
	 * @return the destnCode
	 */
	public String getDestnCode() {
		return destnCode;
	}

	/**
	 * @param destnCode the destnCode to set
	 */
	public void setDestnCode(String destnCode) {
		this.destnCode = destnCode;
	}

	/**
	 * @return the dsnPopupFlag
	 */
	public String getDsnPopupFlag() {
		return dsnPopupFlag;
	}

	/**
	 * @param dsnPopupFlag the dsnPopupFlag to set
	 */
	public void setDsnPopupFlag(String dsnPopupFlag) {
		this.dsnPopupFlag = dsnPopupFlag;
	}

	/**
	 * @return the createCCAFlg
	 */
	public String getCreateCCAFlg() {
		return createCCAFlg;
	}

	/**
	 * @param createCCAFlg the createCCAFlg to set
	 */
	public void setCreateCCAFlg(String createCCAFlg) {
		this.createCCAFlg = createCCAFlg;
	}

	/**
	 * @return the fromScreen
	 */
	public String getFromScreen() {
		return fromScreen;
	}

	/**
	 * @param fromScreen the fromScreen to set
	 */
	public void setFromScreen(String fromScreen) {
		this.fromScreen = fromScreen;
	}

	/**
	 * @return the usrCCANumFlg
	 */
	public String getUsrCCANumFlg() {
		return usrCCANumFlg;
	}

	/**
	 * @param usrCCANumFlg the usrCCANumFlg to set
	 */
	public void setUsrCCANumFlg(String usrCCANumFlg) {
		this.usrCCANumFlg = usrCCANumFlg;
	}

	/**
	 * @return the autoratedFlag
	 */
	public String getAutoratedFlag() {
		return autoratedFlag;
	}

	/**
	 * @param autoratedFlag the autoratedFlag to set
	 */
	public void setAutoratedFlag(String autoratedFlag) {
		this.autoratedFlag = autoratedFlag;
	}

	/**
	 * @return the afterSave
	 */
	public String getAfterSave() {
		return afterSave;
	}

	/**
	 * @param afterSave the afterSave to set
	 */
	public void setAfterSave(String afterSave) {
		this.afterSave = afterSave;
	}

	/**
	 * @return the disableFlag
	 */
	public String getDisableFlag() {
		return disableFlag;
	}

	/**
	 * @param disableFlag the disableFlag to set
	 */
	public void setDisableFlag(String disableFlag) {
		this.disableFlag = disableFlag;
	}
	public String getCcaPresent() {
		return CcaPresent;
	}
	public void setCcaPresent(String ccaPresent) {
		CcaPresent = ccaPresent;
	}

	/**
	 * @return the surChargeAction
	 */
	public String getSurChargeAction() {
		return surChargeAction;
	}

	/**
	 * @param surChargeAction the surChargeAction to set
	 */
	public void setSurChargeAction(String surChargeAction) {
		this.surChargeAction = surChargeAction;
	}

	/**
	 * @return the surchargeOpFlag
	 */
	public String[] getSurchargeOpFlag() {
		return surchargeOpFlag;
	}

	/**
	 * @param surchargeOpFlag the surchargeOpFlag to set
	 */
	public void setSurchargeOpFlag(String[] surchargeOpFlag) {
		this.surchargeOpFlag = surchargeOpFlag;
	}

	/**
	 * @return the revSurChargeTotal
	 */
	public String getRevSurChargeTotal() {
		return revSurChargeTotal;
	}

	/**
	 * @param revSurChargeTotal the revSurChargeTotal to set
	 */
	public void setRevSurChargeTotal(String revSurChargeTotal) {
		this.revSurChargeTotal = revSurChargeTotal;
	}

	/**
	 * @return the orgSurChargeTotal
	 */
	public String getOrgSurChargeTotal() {
		return orgSurChargeTotal;
	}

	/**
	 * @param orgSurChargeTotal the orgSurChargeTotal to set
	 */
	public void setOrgSurChargeTotal(String orgSurChargeTotal) {
		this.orgSurChargeTotal = orgSurChargeTotal;
	}

	/**
	 * 	Getter for billingStatus 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 *  @param billingStatus the billingStatus to set
	 * 	Setter for billingStatus 
	 *	Added by : A-6991 on 13-Sep-2017
	 * 	Used for : ICRD-211662
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return the revisedRate
	 */
	public String getRevisedRate() {
		return revisedRate;
	}
	/**
	 * @param revisedRate the revisedRate to set
	 */
	public void setRevisedRate(String revisedRate) {
		this.revisedRate = revisedRate;
	}

	/**
	 * 	Getter for reasonCheck 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	public String[] getReasonCheck() {
		return reasonCheck;
	}

	/**
	 *  @param reasonCheck the reasonCheck to set
	 * 	Setter for reasonCheck 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	public void setReasonCheck(String[] reasonCheck) {
		this.reasonCheck = reasonCheck;
	}

	/**
	 * 	Getter for reasonCodeRestrictionFlag 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	public String getReasonCodeRestrictionFlag() {
		return reasonCodeRestrictionFlag;
	}
	
	/**
	 *  @param reasonCodeRestrictionFlag the reasonCodeRestrictionFlag to set
	 * 	Setter for reasonCodeRestrictionFlag 
	 *	Added by : A-7531 on 06-Feb-2019
	 * 	Used for :
	 */
	public void setReasonCodeRestrictionFlag(String reasonCodeRestrictionFlag) {
		this.reasonCodeRestrictionFlag = reasonCodeRestrictionFlag;
	}
	
	//added for MRA079 navigation
	/**
	 * 
	 * @return
	 */

	public String getBlgFromDate() {
		return blgFromDate;
	}

	public void setBlgFromDate(String blgFromDate) {
		this.blgFromDate = blgFromDate;
	}

	public String getBlgToDate() {
		return blgToDate;
	}

	public void setBlgToDate(String blgToDate) {
		this.blgToDate = blgToDate;
	}

	
	
	public String getBlgCsgDocNum() {
		return blgCsgDocNum;
	}

	/*public LocalDate getBlgFromDate() {
		return blgFromDate;
	}

	public void setBlgFromDate(LocalDate blgFromDate) {
		this.blgFromDate = blgFromDate;
	}

	public LocalDate getBlgToDate() {
		return blgToDate;
	}

	public void setBlgToDate(LocalDate blgToDate) {
		this.blgToDate = blgToDate;
	}*/

	public void setBlgCsgDocNum(String blgCsgDocNum) {
		this.blgCsgDocNum = blgCsgDocNum;
	}

	public String getBlgBillingStatus() {
		return blgBillingStatus;
	}

	public void setBlgBillingStatus(String blgBillingStatus) {
		this.blgBillingStatus = blgBillingStatus;
	}

	public String getBlgGpaCode() {
		return blgGpaCode;
	}

	public void setBlgGpaCode(String blgGpaCode) {
		this.blgGpaCode = blgGpaCode;
	}

	public String getBlgOriginOfficeofExchange() {
		return blgOriginOfficeofExchange;
	}

	public void setBlgOriginOfficeofExchange(String blgOriginOfficeofExchange) {
		this.blgOriginOfficeofExchange = blgOriginOfficeofExchange;
	}

	public String getBlgDestinationOfficeofExchange() {
		return blgDestinationOfficeofExchange;
	}

	public void setBlgDestinationOfficeofExchange(String blgDestinationOfficeofExchange) {
		this.blgDestinationOfficeofExchange = blgDestinationOfficeofExchange;
	}

	public String getBlgOrigin() {
		return blgOrigin;
	}

	public void setBlgOrigin(String blgOrigin) {
		this.blgOrigin = blgOrigin;
	}

	public String getBlgDestination() {
		return blgDestination;
	}

	public void setBlgDestination(String blgDestination) {
		this.blgDestination = blgDestination;
	}

	public String getBlgCategory() {
		return blgCategory;
	}

	public void setBlgCategory(String blgCategory) {
		this.blgCategory = blgCategory;
	}

	public String getBlgSubclass() {
		return blgSubclass;
	}

	public void setBlgSubclass(String blgSubclass) {
		this.blgSubclass = blgSubclass;
	}

	public String getBlgYear() {
		return blgYear;
	}

	public void setBlgYear(String blgYear) {
		this.blgYear = blgYear;
	}

	public String getBlgDSN() {
		return blgDSN;
	}

	public void setBlgDSN(String blgDSN) {
		this.blgDSN = blgDSN;
	}

	public String getBlgRSN() {
		return blgRSN;
	}

	public void setBlgRSN(String blgRSN) {
		this.blgRSN = blgRSN;
	}

	public String getBlgHNI() {
		return blgHNI;
	}

	public void setBlgHNI(String blgHNI) {
		this.blgHNI = blgHNI;
	}

	public String getBlgRI() {
		return blgRI;
	}

	public void setBlgRI(String blgRI) {
		this.blgRI = blgRI;
	}

	public String getBlgMailbagId() {
		return blgMailbagId;
	}

	public void setBlgMailbagId(String blgMailbagId) {
		this.blgMailbagId = blgMailbagId;
	}

	/*public String getBlgUSPSrate() {
		return blgUSPSrate;
	}

	public void setBlgUSPSrate(String blgUSPSrate) {
		this.blgUSPSrate = blgUSPSrate;
	}*/

	public String getBlgratebasis() {
		return blgratebasis;
	}

	public void setBlgratebasis(String blgratebasis) {
		this.blgratebasis = blgratebasis;
	}

	public String getBlgUSPSPerformancemet() {
		return blgUSPSPerformancemet;
	}

	public void setBlgUSPSPerformancemet(String blgUSPSPerformancemet) {
		this.blgUSPSPerformancemet = blgUSPSPerformancemet;
	}
	public String getBlgTotalRecords() {
		return blgTotalRecords;
	}
	public void setBlgTotalRecords(String blgTotalRecords) {
		this.blgTotalRecords = blgTotalRecords;
	}
	public String getBlgDefaultPageSize() {
		return blgDefaultPageSize;
	}
	public void setBlgDefaultPageSize(String blgDefaultPageSize) {
		this.blgDefaultPageSize = blgDefaultPageSize;
	}
	public String getBlgDisplayPage() {
		return blgDisplayPage;
	}
	public void setBlgDisplayPage(String blgDisplayPage) {
		this.blgDisplayPage = blgDisplayPage;
	}

	public String getRevisedChargeGrossWeignt() {
		return revisedChargeGrossWeignt;
	}

	public void setRevisedChargeGrossWeignt(String revisedChargeGrossWeignt) {
		this.revisedChargeGrossWeignt = revisedChargeGrossWeignt;
	}

	
	
	//added for MRA079 navigation ends here 
	
	

}
