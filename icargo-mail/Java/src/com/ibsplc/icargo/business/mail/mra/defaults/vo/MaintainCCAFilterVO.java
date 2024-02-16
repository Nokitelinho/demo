/*
 * MaintainCCAFilterVO.java Created on JULY 14,2008
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services(P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-3447
 * 
 */
public class MaintainCCAFilterVO extends AbstractVO {

	private String companyCode;	
	private String pOACode;	
	private String billingBasis;
	private String consignmentDocNum;
	private int consignmentSeqNum;
	
	private int duplicateNumber;

	private int sequenceNumber;

	private int documentOwnerID;

	private String ccaReferenceNumber;

	private String dsnNumber;

	private LocalDate dsnDate;

	private String ccaStatus;

	private String screenID;

	private String issuingParty;
	
	private String partyCode;
	
	private String partyLocation;
	
	private String usrCCANumFlg;
	
	private String usrCCANum;
	private String baseCurrency;
	private String airlineGpaIndicator;
	
	private boolean isApprovedMCAExists;
	private String gpaCode;//Added by a-7871 for ICRD-293403
	/**
	 * @return the gpaCode
	 */
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode the gpaCode to set
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	/**
	 * For Print 
	 */
	
	private String issuedBy;
	private int blgDtlSeqNum;
	
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
	 * @return the ccaReferenceNumber
	 */
	public String getCcaReferenceNumber() {
		return ccaReferenceNumber;
	}

	/**
	 * @param ccaReferenceNumber
	 *            the ccaReferenceNumber to set
	 */
	public void setCcaReferenceNumber(String ccaReferenceNumber) {
		this.ccaReferenceNumber = ccaReferenceNumber;
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
	 * @return the documentOwnerID
	 */
	public int getDocumentOwnerID() {
		return documentOwnerID;
	}

	/**
	 * @param documentOwnerID
	 *            the documentOwnerID to set
	 */
	public void setDocumentOwnerID(int documentOwnerID) {
		this.documentOwnerID = documentOwnerID;
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
	 * @return the duplicateNumber
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}

	/**
	 * @param duplicateNumber
	 *            the duplicateNumber to set
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}

	/**
	 * @return the screenID
	 */
	public String getScreenID() {
		return screenID;
	}

	/**
	 * @param screenID
	 *            the screenID to set
	 */
	public void setScreenID(String screenID) {
		this.screenID = screenID;
	}

	/**
	 * @return the sequenceNumber
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            the sequenceNumber to set
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return the issuingParty
	 */
	public String getIssuingParty() {
		return issuingParty;
	}

	/**
	 * @param issuingParty the issuingParty to set
	 */
	public void setIssuingParty(String issuingParty) {
		this.issuingParty = issuingParty;
	}

	/**
	 * @return the partyCode
	 */
	public String getPartyCode() {
		return partyCode;
	}

	/**
	 * @param partyCode the partyCode to set
	 */
	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	/**
	 * @return the partyLocation
	 */
	public String getPartyLocation() {
		return partyLocation;
	}

	/**
	 * @param partyLocation the partyLocation to set
	 */
	public void setPartyLocation(String partyLocation) {
		this.partyLocation = partyLocation;
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
	 * @return the consignmentDocNum
	 */
	public String getConsignmentDocNum() {
		return consignmentDocNum;
	}

	/**
	 * @param consignmentDocNum the consignmentDocNum to set
	 */
	public void setConsignmentDocNum(String consignmentDocNum) {
		this.consignmentDocNum = consignmentDocNum;
	}

	/**
	 * @return the consignmentSeqNum
	 */
	public int getConsignmentSeqNum() {
		return consignmentSeqNum;
	}

	/**
	 * @param consignmentSeqNum the consignmentSeqNum to set
	 */
	public void setConsignmentSeqNum(int consignmentSeqNum) {
		this.consignmentSeqNum = consignmentSeqNum;
	}

	/**
	 * @return the pOACode
	 */
	public String getPOACode() {
		return pOACode;
	}

	/**
	 * @param code the pOACode to set
	 */
	public void setPOACode(String code) {
		pOACode = code;
	}

	/**
	 * @return the usrCCANum
	 */
	public String getUsrCCANum() {
		return usrCCANum;
	}

	/**
	 * @param usrCCANum the usrCCANum to set
	 */
	public void setUsrCCANum(String usrCCANum) {
		this.usrCCANum = usrCCANum;
	}

	/**
	 * @return the issuedBy
	 */
	public String getIssuedBy() {
		return issuedBy;
	}

	/**
	 * @param issuedBy the issuedBy to set
	 */
	public void setIssuedBy(String issuedBy) {
		this.issuedBy = issuedBy;
	}

	/**
	 * @return the airlineGpaIndicator
	 */
	public String getAirlineGpaIndicator() {
		return airlineGpaIndicator;
	}

	/**
	 * @param airlineGpaIndicator the airlineGpaIndicator to set
	 */
	public void setAirlineGpaIndicator(String airlineGpaIndicator) {
		this.airlineGpaIndicator = airlineGpaIndicator;
	}

	/**
	 * @param isApprovedMCAExists the isApprovedMCAExists to set
	 */
	public void setApprovedMCAExists(boolean isApprovedMCAExists) {
		this.isApprovedMCAExists = isApprovedMCAExists;
	}

	/**
	 * @return the isApprovedMCAExists
	 */
	public boolean isApprovedMCAExists() {
		return isApprovedMCAExists;
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


	
}