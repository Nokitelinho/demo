/*
 * MRAAirlineBillingDetail.java Created on Feb 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-1946
 *
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision 		Date 					Author 		Description
 * ------------------------------------------------------------------------- 
 * 0.1			  feb 15, 2007			    A-1946		Created	
 */

@Staleable
@Table(name="MTKARLBLGDTL")
@Entity
@Deprecated
public class MRAAirlineBillingDetail {

     private static final String MODULE_NAME = "mail.mra";

    //private Log log = LogFactory.getLogger("MailTRACKING  MRA");

    private MRAAirlineBillingDetailPK mraAirlineBillingDetailPK;
    
    
    private String basisType;
	
	private String originOfficeOfExchange;
	
	private String destinationOfficeOfExchange;
	
	private String mailCategory;
	
	private String mailSubclass;
	
	private String receptacleSerialNumber;
	
	private String despatchSerialNumber;
	
	private String highestReceptacleNumber;
	
	private int year;
	
	private String registeredInsuredInd;
	
	private double  billableAmount;
	
	private String billingCurrenyCode;
	
	private Calendar receivedDate;
	
	private int receivedPieces;
	
	private double  receivedWeight;
	
	private double  applicablerate;
	
	private String billingStatus;
	
	private String invoicenumber;
	
	private String  consignmentDocumentNumber;
	
	private int consignmentSequenceNumber;
	
	private String flightNumber;

	private int flightSequencenumber;

	private int segmentSerialNumber;

	private String uldNumber;

	private String containerNumber;

	private String poaCode;

	private String billingRemarks;
    
  
	/**
	 * @return Returns the receivedDate.
	 */
	@Column(name = "RCVDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getReceivedDate() {
		return receivedDate;
	}

	/**
	 * @param receivedDate The receivedDate to set.
	 */
	public void setReceivedDate(Calendar receivedDate) {
		this.receivedDate = receivedDate;
	}

	/**
	 * @return Returns the mraAirlineBillingDetailPK.
	 */
	 @EmbeddedId
	@AttributeOverrides({
	        @AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
	        @AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
	        @AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")),
	        @AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS"))})
	public MRAAirlineBillingDetailPK getMraAirlineBillingDetailPK() {
		return mraAirlineBillingDetailPK;
	}

	/**
	 * @param mraAirlineBillingDetailPK The mraAirlineBillingDetailPK to set.
	 */
	public void setMraAirlineBillingDetailPK(
			MRAAirlineBillingDetailPK mraAirlineBillingDetailPK) {
		this.mraAirlineBillingDetailPK = mraAirlineBillingDetailPK;
	}

	/**
	 * @return Returns the applicablerate.
	 */
	@Column(name = "APLRAT")
	public double getApplicablerate() {
		return applicablerate;
	}

	/**
	 * @param applicablerate The applicablerate to set.
	 */
	public void setApplicablerate(double applicablerate) {
		this.applicablerate = applicablerate;
	}

	/**
	 * @return Returns the basisType.
	 */
	@Column(name = "BASTYP")
	public String getBasisType() {
		return basisType;
	}

	/**
	 * @param basisType The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	/**
	 * @return Returns the billableAmount.
	 */
	@Column(name = "BLBAMT")
	public double getBillableAmount() {
		return billableAmount;
	}

	/**
	 * @param billableAmount The billableAmount to set.
	 */
	public void setBillableAmount(double billableAmount) {
		this.billableAmount = billableAmount;
	}

	/**
	 * @return Returns the billingCurrenyCode.
	 */
	@Column(name = "BLGCURCOD")
	public String getBillingCurrenyCode() {
		return billingCurrenyCode;
	}

	/**
	 * @param billingCurrenyCode The billingCurrenyCode to set.
	 */
	public void setBillingCurrenyCode(String billingCurrenyCode) {
		this.billingCurrenyCode = billingCurrenyCode;
	}

	/**
	 * @return Returns the billingRemarks.
	 */
	@Column(name = "BLGRMK")
	public String getBillingRemarks() {
		return billingRemarks;
	}

	/**
	 * @param billingRemarks The billingRemarks to set.
	 */
	public void setBillingRemarks(String billingRemarks) {
		this.billingRemarks = billingRemarks;
	}

	/**
	 * @return Returns the billingStatus.
	 */
	@Column(name = "BLGSTA")
	public String getBillingStatus() {
		return billingStatus;
	}

	/**
	 * @param billingStatus The billingStatus to set.
	 */
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}

	/**
	 * @return Returns the consignmentDocumentNumber.
	 */
	@Column(name = "CSGDOCNUM")
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	/**
	 * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	@Column(name = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	@Column(name = "CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	@Column(name = "DSN")
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the destinationOfficeOfExchange.
	 */
	@Column(name = "DSTEXGOFC")
	public String getDestinationOfficeOfExchange() {
		return destinationOfficeOfExchange;
	}

	/**
	 * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
	 */
	public void setDestinationOfficeOfExchange(String destinationOfficeOfExchange) {
		this.destinationOfficeOfExchange = destinationOfficeOfExchange;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequencenumber.
	 */
	@Column(name = "FLTSEQNUM")
	public int getFlightSequencenumber() {
		return flightSequencenumber;
	}

	/**
	 * @param flightSequencenumber The flightSequencenumber to set.
	 */
	public void setFlightSequencenumber(int flightSequencenumber) {
		this.flightSequencenumber = flightSequencenumber;
	}

	/**
	 * @return Returns the highestReceptacleNumber.
	 */
	@Column(name = "HSN")
	public String getHighestReceptacleNumber() {
		return highestReceptacleNumber;
	}

	/**
	 * @param highestReceptacleNumber The highestReceptacleNumber to set.
	 */
	public void setHighestReceptacleNumber(String highestReceptacleNumber) {
		this.highestReceptacleNumber = highestReceptacleNumber;
	}

	/**
	 * @return Returns the invoicenumber.
	 */
	@Column(name = "INVNUM")
	public String getInvoicenumber() {
		return invoicenumber;
	}

	/**
	 * @param invoicenumber The invoicenumber to set.
	 */
	public void setInvoicenumber(String invoicenumber) {
		this.invoicenumber = invoicenumber;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	@Column(name = "MALCTGCOD")
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	@Column(name = "MALSUBCLS")
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	@Column(name = "ORGEXGOFC")
	public String getOriginOfficeOfExchange() {
		return originOfficeOfExchange;
	}

	/**
	 * @param originOfficeOfExchange The originOfficeOfExchange to set.
	 */
	public void setOriginOfficeOfExchange(String originOfficeOfExchange) {
		this.originOfficeOfExchange = originOfficeOfExchange;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@Column(name = "POACOD")
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
	 * @return Returns the receivedPieces.
	 */
	@Column(name = "RCVPCS")
	public int getReceivedPieces() {
		return receivedPieces;
	}

	/**
	 * @param receivedPieces The receivedPieces to set.
	 */
	public void setReceivedPieces(int receivedPieces) {
		this.receivedPieces = receivedPieces;
	}

	/**
	 * @return Returns the receivedWeight.
	 */
	@Column(name = "RCVWGT")
	public double getReceivedWeight() {
		return receivedWeight;
	}

	/**
	 * @param receivedWeight The receivedWeight to set.
	 */
	public void setReceivedWeight(double receivedWeight) {
		this.receivedWeight = receivedWeight;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	@Column(name = "RSN")
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the registeredInsuredInd.
	 */
	@Column(name = "REGIND")
	public String isRegisteredInsuredInd() {
		return registeredInsuredInd;
	}

	/**
	 * @param registeredInsuredInd The registeredInsuredInd to set.
	 */
	public void setRegisteredInsuredInd(String registeredInsuredInd) {
		this.registeredInsuredInd = registeredInsuredInd;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	@Column(name = "ULDNUM")
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the year.
	 */
	@Column(name = "YER")
	public int getYear() {
		return year;
	}

	/**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	

}
