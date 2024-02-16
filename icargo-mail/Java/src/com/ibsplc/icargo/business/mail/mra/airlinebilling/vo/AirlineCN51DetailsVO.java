/*
 * AirlineCN51DetailsVO.java Created on Feb 15,2007
 *
 * Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.airlinebilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1946
 * 
 */
public class AirlineCN51DetailsVO extends AbstractVO {

	private String companycode;

	private int airlineidr;

	private String interlinebillingtype;

	private String invoicenumber;

	private int sequenceNumber;

	private String clearanceperiod;

	private String carriagefrom;

	private String carriageto;

	private String mailcategory;

	private String mailsubclass;

	private int totalpieces;

	private double totalweight;

	private double applicablerate;

	private Money totalamountincontractcurrency;

	private String contractcurrencycode;
	
	private String listingcurrencycode;

	private String operationFlag;

	private String airlineCode; // added for reports by a-2458

	private String airlineName;// added for reports by a-2458

	private String airlineNumber;// added for reports by a-2458

	private double weightLC;// added for reports by a-2458

	private double weightCP;// added for reports by a-2458
	
	private double weightULD;//Added For BUG : MRA240	
	
	private double weightSV;//Added For BUG : MRA240	

	private double weightEMS;//Added for EMS
	private double totalAmount;// added for reports by a-2458
	
	/**
	 * seqIdentifier is particularly used for Client side operations
	 */
	private int seqIdentifier;	//Added For BUG : MRA240	

	/**
	 * @return Returns the airlineidr.
	 */
	public int getAirlineidr() {
		return airlineidr;
	}

	/**
	 * @param airlineidr
	 *            The airlineidr to set.
	 */
	public void setAirlineidr(int airlineidr) {
		this.airlineidr = airlineidr;
	}

	/**
	 * @return Returns the applicablerate.
	 */
	public double getApplicablerate() {
		return applicablerate;
	}

	/**
	 * @param applicablerate
	 *            The applicablerate to set.
	 */
	public void setApplicablerate(double applicablerate) {
		this.applicablerate = applicablerate;
	}

	/**
	 * @return Returns the carriagefrom.
	 */
	public String getCarriagefrom() {
		return carriagefrom;
	}

	/**
	 * @param carriagefrom
	 *            The carriagefrom to set.
	 */
	public void setCarriagefrom(String carriagefrom) {
		this.carriagefrom = carriagefrom;
	}

	/**
	 * @return Returns the carriageto.
	 */
	public String getCarriageto() {
		return carriageto;
	}

	/**
	 * @param carriageto
	 *            The carriageto to set.
	 */
	public void setCarriageto(String carriageto) {
		this.carriageto = carriageto;
	}

	/**
	 * @return Returns the clearanceperiod.
	 */
	public String getClearanceperiod() {
		return clearanceperiod;
	}

	/**
	 * @param clearanceperiod
	 *            The clearanceperiod to set.
	 */
	public void setClearanceperiod(String clearanceperiod) {
		this.clearanceperiod = clearanceperiod;
	}

	/**
	 * @return Returns the companycode.
	 */
	public String getCompanycode() {
		return companycode;
	}

	/**
	 * @param companycode
	 *            The companycode to set.
	 */
	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

	/**
	 * @return Returns the contractcurrencycode.
	 */
	public String getContractcurrencycode() {
		return contractcurrencycode;
	}

	/**
	 * @param contractcurrencycode
	 *            The contractcurrencycode to set.
	 */
	public void setContractcurrencycode(String contractcurrencycode) {
		this.contractcurrencycode = contractcurrencycode;
	}

	/**
	 * @return Returns the interlinebillingtype.
	 */
	public String getInterlinebillingtype() {
		return interlinebillingtype;
	}

	/**
	 * @param interlinebillingtype
	 *            The interlinebillingtype to set.
	 */
	public void setInterlinebillingtype(String interlinebillingtype) {
		this.interlinebillingtype = interlinebillingtype;
	}

	/**
	 * @return Returns the invoicenumber.
	 */
	public String getInvoicenumber() {
		return invoicenumber;
	}

	/**
	 * @param invoicenumber
	 *            The invoicenumber to set.
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}

	/**
	 * @return Returns the mailcategory.
	 */
	public String getMailcategory() {
		return mailcategory;
	}

	/**
	 * @param mailcategory
	 *            The mailcategory to set.
	 */
	public void setMailcategory(String mailcategory) {
		this.mailcategory = mailcategory;
	}

	/**
	 * @return Returns the mailsubclass.
	 */
	public String getMailsubclass() {
		return mailsubclass;
	}

	/**
	 * @param mailsubclass
	 *            The mailsubclass to set.
	 */
	public void setMailsubclass(String mailsubclass) {
		this.mailsubclass = mailsubclass;
	}

	/**
	 * @return Returns the sequenceNumber.
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}

	/**
	 * @param sequenceNumber
	 *            The sequenceNumber to set.
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	/**
	 * @return Returns the totalamountincontractcurrency.
	 */
	public Money getTotalamountincontractcurrency() {
		return totalamountincontractcurrency;
	}

	/**
	 * @param totalamountincontractcurrency
	 *            The totalamountincontractcurrency to set.
	 */
	public void setTotalamountincontractcurrency(
			Money totalamountincontractcurrency) {
		this.totalamountincontractcurrency = totalamountincontractcurrency;
	}

	/**
	 * @return Returns the totalpieces.
	 */
	public int getTotalpieces() {
		return totalpieces;
	}

	/**
	 * @param totalpieces
	 *            The totalpieces to set.
	 */
	public void setTotalpieces(int totalpieces) {
		this.totalpieces = totalpieces;
	}

	/**
	 * @return Returns the totalweight.
	 */
	public double getTotalweight() {
		return totalweight;
	}

	/**
	 * @param totalweight
	 *            The totalweight to set.
	 */
	public void setTotalweight(double totalweight) {
		this.totalweight = totalweight;
	}

	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}

	/**
	 * @param operationFlag
	 *            The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
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
	 * @return Returns the airlineName.
	 */
	public String getAirlineName() {
		return airlineName;
	}

	/**
	 * @param airlineName
	 *            The airlineName to set.
	 */
	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	/**
	 * @return Returns the airlineNumber.
	 */
	public String getAirlineNumber() {
		return airlineNumber;
	}

	/**
	 * @param airlineNumber
	 *            The airlineNumber to set.
	 */
	public void setAirlineNumber(String airlineNumber) {
		this.airlineNumber = airlineNumber;
	}

	/**
	 * @return Returns the totalAmount.
	 */
	public double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount
	 *            The totalAmount to set.
	 */
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return Returns the weightCP.
	 */
	public double getWeightCP() {
		return weightCP;
	}

	/**
	 * @param weightCP
	 *            The weightCP to set.
	 */
	public void setWeightCP(double weightCP) {
		this.weightCP = weightCP;
	}

	/**
	 * @return Returns the weightLC.
	 */
	public double getWeightLC() {
		return weightLC;
	}

	/**
	 * @param weightLC
	 *            The weightLC to set.
	 */
	public void setWeightLC(double weightLC) {
		this.weightLC = weightLC;
	}

	/**
	 * @return Returns the listingcurrencycode.
	 */
	public String getListingcurrencycode() {
		return listingcurrencycode;
	}

	/**
	 * @param listingcurrencycode The listingcurrencycode to set.
	 */
	public void setListingcurrencycode(String listingcurrencycode) {
		this.listingcurrencycode = listingcurrencycode;
	}

	/**
	 * seqIdentifier is particularly used for Client side operations
	 * @return the seqIdentifier
	 */
	public int getSeqIdentifier() {
		return seqIdentifier;
	}

	/**
	 * seqIdentifier is particularly used for Client side operations
	 * @param seqIdentifier the seqIdentifier to set
	 */
	public void setSeqIdentifier(int seqIdentifier) {
		this.seqIdentifier = seqIdentifier;
	}

	/**
	 * @return the weightSV
	 */
	public double getWeightSV() {
		return weightSV;
	}

	/**
	 * @param weightSV the weightSV to set
	 */
	public void setWeightSV(double weightSV) {
		this.weightSV = weightSV;
	}

	/**
	 * @return the weightULD
	 */
	public double getWeightULD() {
		return weightULD;
	}

	/**
	 * @param weightULD the weightULD to set
	 */
	public void setWeightULD(double weightULD) {
		this.weightULD = weightULD;
	}
	/**
	 * @return the weightEMS
	 */
	public double getWeightEMS() {
		return weightEMS;
	}
	/**
	 * @param weightEMS the weightEMS to set
	 */
	public void setWeightEMS(double weightEMS) {
		this.weightEMS = weightEMS;
	}

}