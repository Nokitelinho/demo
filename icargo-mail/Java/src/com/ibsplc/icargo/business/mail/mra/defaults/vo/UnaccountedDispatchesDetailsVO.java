/*
 * UnaccountedDispatchesDetailsVO.java created on Feb 27, 2007
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;


import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-2107
 * 
 */
public class UnaccountedDispatchesDetailsVO extends AbstractVO {

	private String dsn;
	
	private String origin;
	
	private String destination;
	
	private String flightNumber;
	
	private String flightDate;
	
	private String billType;
	
	private String currency;
	
	private String rate;
	
	private Money amount;
	
	private String sectorFrom;
	
	private String sectorTo;
	
	private Money proratedAmt;
	
	private String reason;
	
	private String bilBase;
	
	private String dsnSqnNo;
	
	private String postalCde;
	
	private Double weight;
	
	private Double rates;
	
	private LocalDate acceptedDate;
	
	private String companyCode;
	
	private Money proratedAmtinCtrcur;
	
	private String mailCategory;
	
	private String mailSubClass;
	
	private String mailClass;
	
	private int serialNo;
	
	private int segmentSerialNo;
	
	private int flightSeqNo;
	
	private String lastUpdateduser;
	
	private String lastUpdatedTime;
	
	private String csgDocNum;
	
	private String flightNo;
	
	private int flightCarrierId;
	
	/**
	public String getNoOfDispatches() {
		return noOfDispatches;
	}

	public void setNoOfDispatches(String noOfDispatches) {
		this.noOfDispatches = noOfDispatches;
	}

	public String getPropratedAmt() {
		return propratedAmt;
	}

	public void setPropratedAmt(String propratedAmt) {
		this.propratedAmt = propratedAmt;
	}

	/**
	 * @return the amount
	 */
	public Money getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Money amount) {
		this.amount = amount;
	}

	/**
	 * @return the billType
	 */
	public String getBillType() {
		return billType;
	}

	/**
	 * @param billType the billType to set
	 */
	public void setBillType(String billType) {
		this.billType = billType;
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

	/**
	 * @return the dsn
	 */
	public String getDsn() {
		return dsn;
	}

	/**
	 * @param dsn the dsn to set
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 * @return the flightDate
	 */
	public String getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
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
	 * @return the proratedAmt
	 */
	public Money getProratedAmt() {
		return proratedAmt;
	}

	/**
	 * @param proratedAmt the proratedAmt to set
	 */
	public void setProratedAmt(Money proratedAmt) {
		this.proratedAmt = proratedAmt;
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
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}

	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}

	/**
	 * @return the sectorFrom
	 */
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom the sectorFrom to set
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return the sectorTo
	 */
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo the sectorTo to set
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	/**
	 * @return the bilBase
	 */
	public String getBilBase() {
		return bilBase;
	}

	/**
	 * @param bilBase the bilBase to set
	 */
	public void setBilBase(String bilBase) {
		this.bilBase = bilBase;
	}

	/**
	 * @return the dsnSqnNo
	 */
	public String getDsnSqnNo() {
		return dsnSqnNo;
	}

	/**
	 * @param dsnSqnNo the dsnSqnNo to set
	 */
	public void setDsnSqnNo(String dsnSqnNo) {
		this.dsnSqnNo = dsnSqnNo;
	}

	/**
	 * @return the postalCde
	 */
	public String getPostalCde() {
		return postalCde;
	}

	/**
	 * @param postalCde the postalCde to set
	 */
	public void setPostalCde(String postalCde) {
		this.postalCde = postalCde;
	}

	/**
	 * @return the weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @param weight the weight to set
	 */
	public void setWeight(Double weight) {
		this.weight = weight;
	}

	/**
	 * @return the rates
	 */
	public Double getRates() {
		return rates;
	}

	/**
	 * @param rates the rates to set
	 */
	public void setRates(Double rates) {
		this.rates = rates;
	}

	/**
	 * @return the acceptedDate
	 */
	public LocalDate getAcceptedDate() {
		return acceptedDate;
	}

	/**
	 * @param acceptedDate the acceptedDate to set
	 */
	public void setAcceptedDate(LocalDate acceptedDate) {
		this.acceptedDate = acceptedDate;
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
	 * @return the proratedAmtinCtrcur
	 */
	public Money getProratedAmtinCtrcur() {
		return proratedAmtinCtrcur;
	}

	/**
	 * @param proratedAmtinCtrcur the proratedAmtinCtrcur to set
	 */
	public void setProratedAmtinCtrcur(Money proratedAmtinCtrcur) {
		this.proratedAmtinCtrcur = proratedAmtinCtrcur;
	}

	/**
	 * @return the mailCategory
	 */
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory the mailCategory to set
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return the mailClass
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass the mailClass to set
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return the mailSubClass
	 */
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass the mailSubClass to set
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return the csgDocNum
	 */
	public String getCsgDocNum() {
		return csgDocNum;
	}

	/**
	 * @param csgDocNum the csgDocNum to set
	 */
	public void setCsgDocNum(String csgDocNum) {
		this.csgDocNum = csgDocNum;
	}

	/**
	 * @return the flightCarrierId
	 */
	public int getFlightCarrierId() {
		return flightCarrierId;
	}

	/**
	 * @param flightCarrierId the flightCarrierId to set
	 */
	public void setFlightCarrierId(int flightCarrierId) {
		this.flightCarrierId = flightCarrierId;
	}

	/**
	 * @return the flightNo
	 */
	public String getFlightNo() {
		return flightNo;
	}

	/**
	 * @param flightNo the flightNo to set
	 */
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}
	/**
	 * @return the flightSeqNo
	 */
	public int getFlightSeqNo() {
		return flightSeqNo;
	}

	/**
	 * @param flightSeqNo the flightSeqNo to set
	 */
	public void setFlightSeqNo(int flightSeqNo) {
		this.flightSeqNo = flightSeqNo;
	}

	/**
	 * @return the segmentSerialNo
	 */
	public int getSegmentSerialNo() {
		return segmentSerialNo;
	}

	/**
	 * @param segmentSerialNo the segmentSerialNo to set
	 */
	public void setSegmentSerialNo(int segmentSerialNo) {
		this.segmentSerialNo = segmentSerialNo;
	}

	/**
	 * @return the serialNo
	 */
	public int getSerialNo() {
		return serialNo;
	}

	/**
	 * @param serialNo the serialNo to set
	 */
	public void setSerialNo(int serialNo) {
		this.serialNo = serialNo;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(String lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdateduser
	 */
	public String getLastUpdateduser() {
		return lastUpdateduser;
	}

	/**
	 * @param lastUpdateduser the lastUpdateduser to set
	 */
	public void setLastUpdateduser(String lastUpdateduser) {
		this.lastUpdateduser = lastUpdateduser;
	}

}
