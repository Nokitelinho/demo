/* CCAdetailsVO.java created on July 15-2008,
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.  
 * This software is the proprietary information of
 *  IBS Software Services (P) Ltd.    
 * Use is subject to license terms. 
 *  
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

/**
 * @author A-3447
 */

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3447
 * 
 */
public class CCAdetailsVO extends AbstractVO {

	public static final java.lang.String MCA_STATUS_ACCEPTED = "C";
	
	public static final java.lang.String MCA_STATUS_APPROVED = "A";
	
	public static final java.lang.String MCA_STATUS_REJECTED = "R";
	 
	private String operationFlag;

	private String issueDate;

	private String billingPeriodFrom;

	private String billingPeriodTo;

	private String origin;

	private String destination;

	private String category;

	private String subClass;

	private String issuingParty;

	private String location;

	private String ccaType;

	private int ccaBillingStatusNo;

	private String ccaRemark;

	private String ccaReason;

	private String airlineCode;
	private String originOE;


	private String destinationOE;
	private String locationCode;

	private String originCode;
	
	private String destnCode;

	private String categoryCode;

	private double grossWeight;

	private double revGrossWeight;

	private double actualULDWeight;
	
	private String actualULDWeightUnit ;
	
	private String paBuiltFlag ;
	private Money chgGrossWeight;

	private Money revChgGrossWeight;

	private Money otherChgGrossWgt;

	private Money otherRevChgGrossWgt;
	
	private Money dueArl;

	private Money revDueArl;

	private Money netDueArlUSD;
	
	private Money duePostDbt;

	private double revDuePostDbt;

	private String revOrgCode;

	private String revDStCode;

	private String curCode;

	private String gpaCode;

	private String revGpaCode;

	private String gpaName;

	private String revGpaName;

	private String grossWeightChangeInd;

	private String weightChargeChangeInd;

	private String gpaChangeInd;

	private String doeChangeInd;

	private String writeOffInd;

	private String companyCode;

	private String billingBasis;

	private int serialNumber;

	private String ccaRefNumber;
	
	private int csgSequenceNumber;
	
	private String csgDocumentNumber;
	
	private String poaCode;

	private Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs;

	private String dsnNo;
	private String rsn;
	
	//Added by A-7929 as part of ICRD-132548
	private double rate;
	private double revisedRate;
	private String rateChangeInd;
	//private Collection<DocumentBillingDetailsVO> documentBillingDetailsVO;
	
	private long mailSequenceNumber;
	
	private String mailbagId;
	 private String currChangeFlag;//added for ICRD-282931
		
	private boolean voidMailbag; 
		
	public String getOriginOE() {
		return originOE;
	}
	public void setOriginOE(String originOE) {
		this.originOE = originOE;
	}
	public String getDestinationOE() {
		return destinationOE;
	}
	public void setDestinationOE(String destinationOE) {
		this.destinationOE = destinationOE;
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
	public String getRegind() {
		return regind;
	}
	public void setRegind(String regind) {
		this.regind = regind;
	}

	private String hni;
	private String regind;

	private String dsnDate;

	private String ccaStatus;
	
	private LocalDate dsDate;
	
	private LocalDate issueDat;
	
	private Collection<DSNRoutingVO> dsnRoutingVOs;
	
	private String gpaArlIndicator;
	
	private String usrccanum;
	
	
	private Money revDuePostDbtDisp;
	private Money duePostDbtDisp;
	
	private String sectFrom;
	private String sectTo;
	
	private LocalDate lastUpdateTime;
	 private String lastUpdateUser;
	 
	 private String contCurCode;
	 
	 private String payFlag;
	 
	 private String updBillTo;
	 
	 private int updBillToIdr;
	 
	//	Added By Deepthi for controlling accounting call
		
	private String fromRateAudit;
	 
	private String autorateFlag;
	//Added by A-4823 for CR ICRD-7352
	private String year;
	private double tax;
	private double revTax;
	private String revContCurCode;
	private Money revNetAmount;
	private String currChangeInd;
	private double tds;
	private int blgDtlSeqNum;
	private String countryCode;
	private double revTds;
	private String billingStatus;
	private Money netAmount;
//Added for CRQ-39791
	
	//This field should hold the  net value of the MCA
	private double diffAmount;
	
	private Money valChgUpdAmount;
	private Money mailChg;
	private Money revMailChg;
	private Money surChg;
	private Money revSurChg;
	private String overrideRounding;//Added by A-6991 for ICRD-213422
	//Added by A-7794 as part of MRA revamp
	private double wtChgAmtinCTR;
	
	
	//Added by A-7540 for ICRD-132548
	private String autoMca;
	
	//Added as part of IASCB-860 starts
	private String mcaReasonCodes;
	
	private String assignee;//Added for IASCB-2373
	
	private String acceptRejectIdentifier;//Added for IASCB-2373
	
	private String initiatorRoleGroup;//Added for IASCB-3813
	
	private double netValue;// Added for IASCB-2374
	
	private double netAmountBase;// Added for IASCB-2374
	private String displayWeightUnit;
	private String rateLineWeightUnit;
	// added for IASCB-22920 by A-9002		 
	private String displayWgtUnit;
	//Ends
	public String getDisplayWgtUnit() {
		return displayWgtUnit;
	}
	public void setDisplayWgtUnit(String displayWgtUnit) {
		this.displayWgtUnit = displayWgtUnit;
	}
	
	public String getMcaReasonCodes() {
		return mcaReasonCodes;
	}
	public void setMcaReasonCodes(String mcaReasonCodes) {
		this.mcaReasonCodes = mcaReasonCodes;
	}
	public String getAutoMca() {
		return autoMca;
	}
	public void setAutoMca(String autoMca) {
		this.autoMca = autoMca;
	}
	//Added as part of IASCB-860 ends
	/**
	 * @return the wtChgAmtinCTR
	 */
	public double getWtChgAmtinCTR() {
		return wtChgAmtinCTR;
	}
	/**
	 * @param wtChgAmtinCTR the wtChgAmtinCTR to set
	 */
	public void setWtChgAmtinCTR(double wtChgAmtinCTR) {
		this.wtChgAmtinCTR = wtChgAmtinCTR;
	}
	/**
	 * @return the diffAmount
	 */
	public double getDiffAmount() {
		return diffAmount;
	}
	/**
	 * @param diffAmount the diffAmount to set
	 */
	public void setDiffAmount(double diffAmount) {
		this.diffAmount = diffAmount;
	}

	private Money differenceAmount;
	
	/**
	 * @return the differenceAmount
	 */
	public Money getDifferenceAmount() {
		return differenceAmount;
	}
	/**
	 * @param differenceAmount the differenceAmount to set
	 */
	public void setDifferenceAmount(Money differenceAmount) {
		this.differenceAmount = differenceAmount;
	}
	/**
	 * @return the netAmount
	 */
	public Money getNetAmount() {
		return netAmount;
	}
	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Money netAmount) {
		this.netAmount = netAmount;
	}
	public String getBillingStatus() {
		return billingStatus;
	}

	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	public Money getRevNetAmount() {
		return revNetAmount;
	}

	public void setRevNetAmount(Money revNetAmount) {
		this.revNetAmount = revNetAmount;
	}

	public double getRevTds() {
		return revTds;
	}

	public void setRevTds(double revTds) {
		this.revTds = revTds;
	}

	/**
	 * @return the netDueArlUSD
	 */
	public Money getNetDueArlUSD() {
		return netDueArlUSD;
	}

	/**
	 * @param netDueArlUSD the netDueArlUSD to set
	 */
	public void setNetDueArlUSD(Money netDueArlUSD) {
		this.netDueArlUSD = netDueArlUSD;
	}

	/**
	 * @return the contCurCode
	 */
	public String getContCurCode() {
		return contCurCode;
	}

	/**
	 * @param contCurCode the contCurCode to set
	 */
	public void setContCurCode(String contCurCode) {
		this.contCurCode = contCurCode;
	}

	/**
	 * @return the gpaArlIndicator
	 */
	public String getGpaArlIndicator() {
		return gpaArlIndicator;
	}

	/**
	 * @param gpaArlIndicator the gpaArlIndicator to set
	 */
	public void setGpaArlIndicator(String gpaArlIndicator) {
		this.gpaArlIndicator = gpaArlIndicator;
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
	 * @return the dsDate
	 */
	public LocalDate getDsDate() {
		return dsDate;
	}

	/**
	 * @param dsDate the dsDate to set
	 */
	public void setDsDate(LocalDate dsDate) {
		this.dsDate = dsDate;
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
	 * @return the dsnDate
	 */
	public String getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            the dsnDate to set
	 */
	public void setDsnDate(String dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return the dsnNo
	 */
	public String getDsnNo() {
		return dsnNo;
	}

	/**
	 * @param dsnNo
	 *            the dsnNo to set
	 */
	public void setDsnNo(String dsnNo) {
		this.dsnNo = dsnNo;
	}

	/**
	 * @return the billingBasis
	 */
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis
	 *            the billingBasis to set
	 */
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
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
	 * @return the serialNumber
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * @param serialNumber
	 *            the serialNumber to set
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
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
	 * @return the issuingParty
	 */
	public String getIssuingParty() {
		return issuingParty;
	}

	/**
	 * @param issuingParty
	 *            the issuingParty to set
	 */
	public void setIssuingParty(String issuingParty) {
		this.issuingParty = issuingParty;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the billingPeriodFrom
	 */
	public String getBillingPeriodFrom() {
		return billingPeriodFrom;
	}

	/**
	 * @param billingPeriodFrom
	 *            the billingPeriodFrom to set
	 */
	public void setBillingPeriodFrom(String billingPeriodFrom) {
		this.billingPeriodFrom = billingPeriodFrom;
	}

	/**
	 * @return the billingPeriodTo
	 */
	public String getBillingPeriodTo() {
		return billingPeriodTo;
	}

	/**
	 * @param billingPeriodTo
	 *            the billingPeriodTo to set
	 */
	public void setBillingPeriodTo(String billingPeriodTo) {
		this.billingPeriodTo = billingPeriodTo;
	}

	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}

	/**
	 * @return the issueDate
	 */
	public String getIssueDate() {
		return issueDate;
	}

	/**
	 * @param issueDate
	 *            the issueDate to set
	 */
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}

	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return the subClass
	 */
	public String getSubClass() {
		return subClass;
	}

	/**
	 * @param subClass
	 *            the subClass to set
	 */
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
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
	 * @return the grossWeight
	 */
	public double getGrossWeight() {
		return grossWeight;
	}

	/**
	 * @param grossWeight
	 *            the grossWeight to set
	 */
	public void setGrossWeight(double grossWeight) {
		this.grossWeight = grossWeight;
	}

	/**
	 * @return the locationCode
	 */
	public String getLocationCode() {
		return locationCode;
	}

	/**
	 * @param locationCode
	 *            the locationCode to set
	 */
	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
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
	 * @return the revDuePostDbt
	 */
	public double getRevDuePostDbt() {
		return revDuePostDbt;
	}

	/**
	 * @param revDuePostDbt
	 *            the revDuePostDbt to set
	 */
	public void setRevDuePostDbt(double revDuePostDbt) {
		this.revDuePostDbt = revDuePostDbt;
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
	 * @return the revGrossWeight
	 */
	public double getRevGrossWeight() {
		return revGrossWeight;
	}

	/**
	 * @param revGrossWeight
	 *            the revGrossWeight to set
	 */
	public void setRevGrossWeight(double revGrossWeight) {
		this.revGrossWeight = revGrossWeight;
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
	 * @return the categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * @param categoryCode
	 *            the categoryCode to set
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

	/**
	 * @return the ccaBillingStatusNo
	 */
	public int getCcaBillingStatusNo() {
		return ccaBillingStatusNo;
	}

	/**
	 * @param ccaBillingStatusNo
	 *            the ccaBillingStatusNo to set
	 */
	public void setCcaBillingStatusNo(int ccaBillingStatusNo) {
		this.ccaBillingStatusNo = ccaBillingStatusNo;
	}

	/**
	 * @return the ccaReason
	 */
	public String getCcaReason() {
		return ccaReason;
	}

	/**
	 * @param ccaReason
	 *            the ccaReason to set
	 */
	public void setCcaReason(String ccaReason) {
		this.ccaReason = ccaReason;
	}

	/**
	 * @return the ccaRemark
	 */
	public String getCcaRemark() {
		return ccaRemark;
	}

	/**
	 * @param ccaRemark
	 *            the ccaRemark to set
	 */
	public void setCcaRemark(String ccaRemark) {
		this.ccaRemark = ccaRemark;
	}

	/**
	 * @return the doeChangeInd
	 */
	public String getDoeChangeInd() {
		return doeChangeInd;
	}

	/**
	 * @param doeChangeInd the doeChangeInd to set
	 */
	public void setDoeChangeInd(String doeChangeInd) {
		this.doeChangeInd = doeChangeInd;
	}

	/**
	 * @return the gpaChangeInd
	 */
	public String getGpaChangeInd() {
		return gpaChangeInd;
	}

	/**
	 * @param gpaChangeInd the gpaChangeInd to set
	 */
	public void setGpaChangeInd(String gpaChangeInd) {
		this.gpaChangeInd = gpaChangeInd;
	}

	/**
	 * @return the grossWeightChangeInd
	 */
	public String getGrossWeightChangeInd() {
		return grossWeightChangeInd;
	}

	/**
	 * @param grossWeightChangeInd the grossWeightChangeInd to set
	 */
	public void setGrossWeightChangeInd(String grossWeightChangeInd) {
		this.grossWeightChangeInd = grossWeightChangeInd;
	}

	/**
	 * @return the weightChargeChangeInd
	 */
	public String getWeightChargeChangeInd() {
		return weightChargeChangeInd;
	}

	/**
	 * @param weightChargeChangeInd the weightChargeChangeInd to set
	 */
	public void setWeightChargeChangeInd(String weightChargeChangeInd) {
		this.weightChargeChangeInd = weightChargeChangeInd;
	}

	/**
	 * @return the writeOffInd
	 */
	public String getWriteOffInd() {
		return writeOffInd;
	}

	/**
	 * @param writeOffInd the writeOffInd to set
	 */
	public void setWriteOffInd(String writeOffInd) {
		this.writeOffInd = writeOffInd;
	}

	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	/**
	 * @return the dsnRoutingVOs
	 */
	public Collection<DSNRoutingVO> getDsnRoutingVOs() {
		return dsnRoutingVOs;
	}

	/**
	 * @param routingVOs the dsnRoutingVOs to set
	 */
	public void setDsnRoutingVOs(Collection<DSNRoutingVO> routingVOs) {
		dsnRoutingVOs = routingVOs;
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
	 * @return the usrccanum
	 */
	public String getUsrccanum() {
		return usrccanum;
	}

	/**
	 * @param usrccanum the usrccanum to set
	 */
	public void setUsrccanum(String usrccanum) {
		this.usrccanum = usrccanum;
	}


	/**
	 * @return the sectFrom
	 */
	public String getSectFrom() {
		return sectFrom;
	}

	/**
	 * @param sectFrom the sectFrom to set
	 */
	public void setSectFrom(String sectFrom) {
		this.sectFrom = sectFrom;
	}

	/**
	 * @return the sectTo
	 */
	public String getSectTo() {
		return sectTo;
	}

	/**
	 * @param sectTo the sectTo to set
	 */
	public void setSectTo(String sectTo) {
		this.sectTo = sectTo;
	}

	/**
	 * @return the lastUpdateTime
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return the chgGrossWeight
	 */
	public Money getChgGrossWeight() {
		return chgGrossWeight;
	}

	/**
	 * @param chgGrossWeight the chgGrossWeight to set
	 */
	public void setChgGrossWeight(Money chgGrossWeight) {
		this.chgGrossWeight = chgGrossWeight;
	}

	/**
	 * @return the dueArl
	 */
	public Money getDueArl() {
		return dueArl;
	}

	/**
	 * @param dueArl the dueArl to set
	 */
	public void setDueArl(Money dueArl) {
		this.dueArl = dueArl;
	}

	/**
	 * @return the duePostDbtDisp
	 */
	public Money getDuePostDbtDisp() {
		return duePostDbtDisp;
	}

	/**
	 * @param duePostDbtDisp the duePostDbtDisp to set
	 */
	public void setDuePostDbtDisp(Money duePostDbtDisp) {
		this.duePostDbtDisp = duePostDbtDisp;
	}

	/**
	 * @return the duePostDbt
	 */
	public Money getDuePostDbt() {
		return duePostDbt;
	}

	/**
	 * @param duePostDbt the duePostDbt to set
	 */
	public void setDuePostDbt(Money duePostDbt) {
		this.duePostDbt = duePostDbt;
	}

	/**
	 * @return the revChgGrossWeight
	 */
	public Money getRevChgGrossWeight() {
		return revChgGrossWeight;
	}

	/**
	 * @param revChgGrossWeight the revChgGrossWeight to set
	 */
	public void setRevChgGrossWeight(Money revChgGrossWeight) {
		this.revChgGrossWeight = revChgGrossWeight;
	}

	/**
	 * @return the revDueArl
	 */
	public Money getRevDueArl() {
		return revDueArl;
	}

	/**
	 * @param revDueArl the revDueArl to set
	 */
	public void setRevDueArl(Money revDueArl) {
		this.revDueArl = revDueArl;
	}

	/**
	 * @return the revDuePostDbtDisp
	 */
	public Money getRevDuePostDbtDisp() {
		return revDuePostDbtDisp;
	}

	/**
	 * @param revDuePostDbtDisp the revDuePostDbtDisp to set
	 */
	public void setRevDuePostDbtDisp(Money revDuePostDbtDisp) {
		this.revDuePostDbtDisp = revDuePostDbtDisp;
	}

	/**
	 * @return the payFlag
	 */
	public String getPayFlag() {
		return payFlag;
	}

	/**
	 * @param payFlag the payFlag to set
	 */
	public void setPayFlag(String payFlag) {
		this.payFlag = payFlag;
	}

	/**
	 * @return the updBillTo
	 */
	public String getUpdBillTo() {
		return updBillTo;
	}

	/**
	 * @param updBillTo the updBillTo to set
	 */
	public void setUpdBillTo(String updBillTo) {
		this.updBillTo = updBillTo;
	}

	/**
	 * @return the updBillToIdr
	 */
	public int getUpdBillToIdr() {
		return updBillToIdr;
	}

	/**
	 * @param updBillToIdr the updBillToIdr to set
	 */
	public void setUpdBillToIdr(int updBillToIdr) {
		this.updBillToIdr = updBillToIdr;
	}

	public LocalDate getIssueDat() {
		return issueDat;
	}

	public void setIssueDat(LocalDate issueDat) {
		this.issueDat = issueDat;
	}

	/**
	 * @return the fromRateAudit
	 */
	public String getFromRateAudit() {
		return fromRateAudit;
	}

	/**
	 * @param fromRateAudit the fromRateAudit to set
	 */
	public void setFromRateAudit(String fromRateAudit) {
		this.fromRateAudit = fromRateAudit;
	}

	/**
	 * @return the autorateFlag
	 */
	public String getAutorateFlag() {
		return autorateFlag;
	}

	/**
	 * @param autorateFlag the autorateFlag to set
	 */
	public void setAutorateFlag(String autorateFlag) {
		this.autorateFlag = autorateFlag;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the tax
	 */
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax the tax to set
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return the revTax
	 */
	public double getRevTax() {
		return revTax;
	}

	/**
	 * @param revTax the revTax to set
	 */
	public void setRevTax(double revTax) {
		this.revTax = revTax;
	}

	/**
	 * @return the revContCurCode
	 */
	public String getRevContCurCode() {
		return revContCurCode;
	}

	/**
	 * @param revContCurCode the revContCurCode to set
	 */
	public void setRevContCurCode(String revContCurCode) {
		this.revContCurCode = revContCurCode;
	}

	

	/**
	 * @return the currChangeInd
	 */
	public String getCurrChangeInd() {
		return currChangeInd;
	}

	/**
	 * @param currChangeInd the currChangeInd to set
	 */
	public void setCurrChangeInd(String currChangeInd) {
		this.currChangeInd = currChangeInd;
	}

	/**
	 * @return the tds
	 */
	public double getTds() {
		return tds;
	}

	/**
	 * @param tds the tds to set
	 */
	public void setTds(double tds) {
		this.tds = tds;
	}

	/**
	 * @return the blgDtlSeqNum
	 */
	public int getBlgDtlSeqNum() {
		return blgDtlSeqNum;
	}

	/**
	 * @param blgDtlSeqNum the blgDtlSeqNum to set
	 */
	public void setBlgDtlSeqNum(int blgDtlSeqNum) {
		this.blgDtlSeqNum = blgDtlSeqNum;
	}

	/**
	 * @return the countryCode
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode the countryCode to set
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	/**
	 * @param valChgUpdAmount the valChgUpdAmount to set
	 */
	public void setValChgUpdAmount(Money valChgUpdAmount) {
		this.valChgUpdAmount = valChgUpdAmount;
	}
	/**
	 * @return the valChgUpdAmount
	 */
	public Money getValChgUpdAmount() {
		return valChgUpdAmount;
	}
	/**
	 * @return the mailChg
	 */
	public Money getMailChg() {
		return mailChg;
	}
	/**
	 * @param mailChg the mailChg to set
	 */
	public void setMailChg(Money mailChg) {
		this.mailChg = mailChg;
	}
	/**
	 * @return the revMailChg
	 */
	public Money getRevMailChg() {
		return revMailChg;
	}
	/**
	 * @param revMailChg the revMailChg to set
	 */
	public void setRevMailChg(Money revMailChg) {
		this.revMailChg = revMailChg;
	}
	/**
	 * @return the surChg
	 */
	public Money getSurChg() {
		return surChg;
	}
	/**
	 * @param surChg the surChg to set
	 */
	public void setSurChg(Money surChg) {
		this.surChg = surChg;
	}
	/**
	 * @return the revSurChg
	 */
	public Money getRevSurChg() {
		return revSurChg;
	}
	/**
	 * @param revSurChg the revSurChg to set
	 */
	public void setRevSurChg(Money revSurChg) {
		this.revSurChg = revSurChg;
	}
	/**
	 * @return the surchargeCCAdetailsVOs
	 */
	public Collection<SurchargeCCAdetailsVO> getSurchargeCCAdetailsVOs() {
		return surchargeCCAdetailsVOs;
	}
	/**
	 * @param surchargeCCAdetailsVOs the surchargeCCAdetailsVOs to set
	 */
	public void setSurchargeCCAdetailsVOs(
			Collection<SurchargeCCAdetailsVO> surchargeCCAdetailsVOs) {
		this.surchargeCCAdetailsVOs = surchargeCCAdetailsVOs;
	}
	/**
	 * @return the otherChgGrossWgt
	 */
	public Money getOtherChgGrossWgt() {
		return otherChgGrossWgt;
	}
	/**
	 * @param otherChgGrossWgt the otherChgGrossWgt to set
	 */
	public void setOtherChgGrossWgt(Money otherChgGrossWgt) {
		this.otherChgGrossWgt = otherChgGrossWgt;
	}
	/**
	 * @return the otherRevChgGrossWgt
	 */
	public Money getOtherRevChgGrossWgt() {
		return otherRevChgGrossWgt;
	}
	/**
	 * @param otherRevChgGrossWgt the otherRevChgGrossWgt to set
	 */
	public void setOtherRevChgGrossWgt(Money otherRevChgGrossWgt) {
		this.otherRevChgGrossWgt = otherRevChgGrossWgt;
	}
	/**
	 * 	Getter for overrideRounding 
	 *	Added by : A-6991 on 05-Jul-2017
	 * 	Used for :
	 */
	public String getOverrideRounding() {
		return overrideRounding;
	}
	/**
	 *  @param overrideRounding the overrideRounding to set
	 * 	Setter for overrideRounding 
	 *	Added by : A-6991 on 05-Jul-2017
	 * 	Used for :
	 */
	public void setOverrideRounding(String overrideRounding) {
		this.overrideRounding = overrideRounding;
	}
	
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	public String getMailbagId() {
		return mailbagId;
	}
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}
	/**
	 * @return the rate
	 */
	public double getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}
	/**
	 * @return the revisedRate
	 */
	public double getRevisedRate() {
		return revisedRate;
	}
	/**
	 * @param revisedRate the revisedRate to set
	 */
	public void setRevisedRate(double revisedRate) {
		this.revisedRate = revisedRate;
	}
	/**
	 * @return the rateChangeInd
	 */
	public String getRateChangeInd() {
		return rateChangeInd;
	}
	/**
	 * @param rateChangeInd the rateChangeInd to set
	 */
	public void setRateChangeInd(String rateChangeInd) {
		this.rateChangeInd = rateChangeInd;
	}
	/**
	 * @return the documentBillingDetailsVO
	 */
	/*public Collection<DocumentBillingDetailsVO> getDocumentBillingDetailsVO() {
		return documentBillingDetailsVO;
	}
	*//**
	 * @param documentBillingDetailsVO the documentBillingDetailsVO to set
	 *//*
	public void setDocumentBillingDetailsVO(Collection<DocumentBillingDetailsVO> documentBillingDetailsVO) {
		this.documentBillingDetailsVO = documentBillingDetailsVO;
	}*/
	public String getCurrChangeFlag() {
		return currChangeFlag;
	}
	public void setCurrChangeFlag(String currChangeFlag) {
		this.currChangeFlag = currChangeFlag;
	}
	/**
	 * 	Getter for voidMailbag 
	 *	Added by : A-5219 on 23-Oct-2019
	 * 	Used for :
	 */
	public boolean isVoidMailbag() {
		return voidMailbag;
	}
	/**
	 *  @param voidMailbag the voidMailbag to set
	 * 	Setter for voidMailbag 
	 *	Added by : A-5219 on 23-Oct-2019
	 * 	Used for :
	 */
	public void setVoidMailbag(boolean voidMailbag) {
		this.voidMailbag = voidMailbag;
	}
	/**
	 * @return the assignee
	 */
	public String getAssignee() {
		return assignee;
	}
	/**
	 * @param assignee the assignee to set
	 */
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	/**
	 * @return the acceptRejectIdentifier
	 */
	public String getAcceptRejectIdentifier() {
		return acceptRejectIdentifier;
	}
	/**
	 * @param acceptRejectIdentifier the acceptRejectIdentifier to set
	 */
	public void setAcceptRejectIdentifier(String acceptRejectIdentifier) {
		this.acceptRejectIdentifier = acceptRejectIdentifier;
	}
	/**
	 * @return the initiator
	 */
	public String getInitiatorRoleGroup() {
		return initiatorRoleGroup;
	}
	/**
	 * @param initiator the initiator to set
	 */
	public void setInitiatorRoleGroup(String initiatorRoleGroup) {
		this.initiatorRoleGroup = initiatorRoleGroup;
	}
	/**
	 * @return the netAmountBase
	 */
	public double getNetAmountBase() {
		return netAmountBase;
	}
	/**
	 * @param netAmountBase the netAmountBase to set
	 */
	public void setNetAmountBase(double netAmountBase) {
		this.netAmountBase = netAmountBase;
	}
	/**
	 * @return the netValue
	 */
	public double getNetValue() {
		return netValue;
	}
	/**
	 * @param netValue the netValue to set
	 */
	public void setNetValue(double netValue) {
		this.netValue = netValue;
	}
	public String getRateLineWeightUnit() {
		return rateLineWeightUnit;
	}
	public void setRateLineWeightUnit(String rateLineWeightUnit) {
		this.rateLineWeightUnit = rateLineWeightUnit;
	}
	public String getDisplayWeightUnit() {
		return displayWeightUnit;
	}
	public void setDisplayWeightUnit(String displayWeightUnit) {
		this.displayWeightUnit = displayWeightUnit;
	}
	//Added by A-8331 for IASCB-54653
	public double getActualULDWeight() {
		return actualULDWeight;
	}
	public void setActualULDWeight(double actualULDWeight) {
		this.actualULDWeight = actualULDWeight;
	}
	public String getActualULDWeightUnit() {
		return actualULDWeightUnit;
	}
	public void setActualULDWeightUnit(String actualULDWeightUnit) {
		this.actualULDWeightUnit = actualULDWeightUnit;
	}
	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}
	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}
	
}