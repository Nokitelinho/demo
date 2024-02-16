/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO.java
 *
 *	Created by	:	A-4809
 *	Created on	:	May 27, 2019
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting.vo;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.gpareporting.vo.ClaimVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-4809	:	May 27, 2019	:	Draft
 */
public class ClaimVO  extends AbstractVO{
	
	private String companyCode;
	private String consignmentCompletionDate;
	private String invoiceNumber;
	private String partyQualifier;
	private String carrierCode;
	private String carrierName;
	private  String totalClaimAmount;
	private String lineCount;
	private String segmentCount;
	private String poaCode;
	private String assigneCode;
	private String messageText;
	private String invDate;
	private double claimAmt;
	private String regionCode;
	private String contractNumber;
	private String newPayInvoicNumber;
	
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getConsignmentCompletionDate() {
		return consignmentCompletionDate;
	}
	public void setConsignmentCompletionDate(String consignmentCompletionDate) {
		this.consignmentCompletionDate = consignmentCompletionDate;
	}
	public String getInvoiceNumber() {
		return invoiceNumber;
	}
	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}
	public String getPartyQualifier() {
		return partyQualifier;
	}
	public void setPartyQualifier(String partyQualifier) {
		this.partyQualifier = partyQualifier;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	/**
	 * @return the lineCount
	 */
	public String getLineCount() {
		return lineCount;
	}
	/**
	 * @param lineCount the lineCount to set
	 */
	public void setLineCount(String lineCount) {
		this.lineCount = lineCount;
	}
	/**
	 * @return the totalClaimAmount
	 */
	public String getTotalClaimAmount() {
		return totalClaimAmount;
	}
	/**
	 * @param totalClaimAmount the totalClaimAmount to set
	 */
	public void setTotalClaimAmount(String totalClaimAmount) {
		this.totalClaimAmount = totalClaimAmount;
	}
	/**
	 * @return the segmentCount
	 */
	public String getSegmentCount() {
		return segmentCount;
	}
	/**
	 * @param segmentCount the segmentCount to set
	 */
	public void setSegmentCount(String segmentCount) {
		this.segmentCount = segmentCount;
	}
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
	public String getAssigneCode() {
		return assigneCode;
	}
	public void setAssigneCode(String assigneCode) {
		this.assigneCode = assigneCode;
	}
	public String getMessageText() {
		return messageText;
	}
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}
	public String getInvDate() {
		return invDate;
	}
	public void setInvDate(String invDate) {
		this.invDate = invDate;
	}
	public double getClaimAmt() {
		return claimAmt;
	}
	public void setClaimAmt(double claimAmt) {
		this.claimAmt = claimAmt;
	}
	/**
	 * 	Getter for contractNumber 
	 *	Added by : A-8061 on 19-Jun-2019
	 * 	Used for :
	 */
	public String getContractNumber() {
		return contractNumber;
	}
	/**
	 *  @param contractNumber the contractNumber to set
	 * 	Setter for contractNumber 
	 *	Added by : A-8061 on 19-Jun-2019
	 * 	Used for :
	 */
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
	/**
	 * 	Getter for regionCode 
	 *	Added by : A-8061 on 19-Jun-2019
	 * 	Used for :
	 */
	public String getRegionCode() {
		return regionCode;
	}
	/**
	 *  @param regionCode the regionCode to set
	 * 	Setter for regionCode 
	 *	Added by : A-8061 on 19-Jun-2019
	 * 	Used for :
	 */
	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}
	public String getNewPayInvoicNumber() {
		return newPayInvoicNumber;
	}
	public void setNewPayInvoicNumber(String newPayInvoicNumber) {
		this.newPayInvoicNumber = newPayInvoicNumber;
	}

	

}
