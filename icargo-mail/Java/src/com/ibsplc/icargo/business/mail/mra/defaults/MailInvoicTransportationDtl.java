/*
 * MailInvoicTransportationDtl.java Created on July 19, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailInvoicTransportationDtlVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 * @author A-2408
 *
 */
@Entity
@Table(name = "MTKINVTDT")
@Staleable
@Deprecated
public class MailInvoicTransportationDtl {
	
	private MailInvoicTransportationDtlPK mailInvoicTransportationDtlPK;
	
	private String scannedCarrierCode;
	
	private String scannedFlightNumber;
	
	private String originalOrigin;
	
	private String finalDestination;
	
	private Calendar depatureDate;
	
	private Calendar arrivalDate;
	
	private String possessionScanCarrier;
	
	private String possessionScanFlightNumber;
	
	private String possessionScanLocation;
	
	private Calendar possesionScanDate;
	
	private String bookedScanCarrier;
	
	private String bookedScanFlightNumber;
	
	private String bookedScanLocation;
	
	private Calendar bookedScanDate;
	
	private String loadScanCarrier;
	
	private String loadScanFlightNumber;
	
	private String loadScanLocation;
	
	private Calendar loadScanDate;
	
	private String firstCarrierCode;
	
	private String secondFlightNumber;
	
	private String firstScanLocation;
	
	private Calendar firstScanDate;
	
	private String secondScanCarrier;
	
	private String thirdFlightNumber;
	
	private String secondScanLocation;
	
	private Calendar secondScanDate;
	
	private String thirdScanCarrier;
	
	private String fourthFlightNumber;
	
	private String thirdScanLocation;
	
	private Calendar thirdScanDate;
	
	private String fifthScanCarrier;
	
	private String fifthFlightNumber;
	
	private String fifthScanLocation;
	
	private Calendar fifthScanDate;
	
	private String deliveryScanCarrierCode;
	
	private String deliveryScanLocation;
	
	private Calendar deliveryScanDate;
	
	/**
	 * 
	 */
	public MailInvoicTransportationDtl(){
		
	}
	/**
	 * @param transportVO
	 * @throws SystemException
	 */
	public MailInvoicTransportationDtl(MailInvoicTransportationDtlVO transportVO)
	throws SystemException{
		MailInvoicTransportationDtlPK transportPK= new MailInvoicTransportationDtlPK();
		
		transportPK.setCompanyCode(transportVO.getCompanyCode());
		transportPK.setInvoiceKey(transportVO.getInvoiceKey());
		transportPK.setPoaCode(transportVO.getPoaCode());
		transportPK.setReceptacleIdentifier(transportVO.getReceptacleIdentifier());
		transportPK.setSectorDestination(transportVO.getSectorDestination());
		transportPK.setSectorOrigin(transportVO.getSectorOrigin());
		this.setMailInvoicTransportationDtlPK(transportPK);
		populateAttributes(transportVO);
		try{
	    	PersistenceController.getEntityManager().persist(this);
	    	}
	    	catch(CreateException e){
	    		throw new SystemException(e.getErrorCode());
	    	}
	}
	/**
	
	 * @return Returns the arrivalDate.
	 */
	@Column(name="ARLDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getArrivalDate() {
		return arrivalDate;
	}

	/**
	 * @param arrivalDate The arrivalDate to set.
	 */
	public void setArrivalDate(Calendar arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	/**
	 * @return Returns the bookedScanCarrier.
	 */
	@Column(name="BKDSCNCAR")
	public String getBookedScanCarrier() {
		return bookedScanCarrier;
	}

	/**
	 * @param bookedScanCarrier The bookedScanCarrier to set.
	 */
	public void setBookedScanCarrier(String bookedScanCarrier) {
		this.bookedScanCarrier = bookedScanCarrier;
	}

	/**
	 * @return Returns the bookedScanDate.
	 */
	@Column(name="BKDSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getBookedScanDate() {
		return bookedScanDate;
	}

	/**
	 * @param bookedScanDate The bookedScanDate to set.
	 */
	public void setBookedScanDate(Calendar bookedScanDate) {
		this.bookedScanDate = bookedScanDate;
	}

	/**
	 * @return Returns the bookedScanFlightNumber.
	 */
	@Column(name="BKDFLTNUM")
	public String getBookedScanFlightNumber() {
		return bookedScanFlightNumber;
	}

	/**
	 * @param bookedScanFlightNumber The bookedScanFlightNumber to set.
	 */
	public void setBookedScanFlightNumber(String bookedScanFlightNumber) {
		this.bookedScanFlightNumber = bookedScanFlightNumber;
	}

	/**
	 * @return Returns the bookedScanLocation.
	 */
	@Column(name="BKDSCNLOC")
	public String getBookedScanLocation() {
		return bookedScanLocation;
	}

	/**
	 * @param bookedScanLocation The bookedScanLocation to set.
	 */
	public void setBookedScanLocation(String bookedScanLocation) {
		this.bookedScanLocation = bookedScanLocation;
	}

	/**
	 * @return Returns the deliveryScanCarrierCode.
	 */
	@Column(name="DLVSCNCAR")
	
	public String getDeliveryScanCarrierCode() {
		return deliveryScanCarrierCode;
	}

	/**
	 * @param deliveryScanCarrierCode The deliveryScanCarrierCode to set.
	 */
	public void setDeliveryScanCarrierCode(String deliveryScanCarrierCode) {
		this.deliveryScanCarrierCode = deliveryScanCarrierCode;
	}

	/**
	 * @return Returns the deliveryScanDate.
	 */
	@Column(name="DLVSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getDeliveryScanDate() {
		return deliveryScanDate;
	}

	/**
	 * @param deliveryScanDate The deliveryScanDate to set.
	 */
	public void setDeliveryScanDate(Calendar deliveryScanDate) {
		this.deliveryScanDate = deliveryScanDate;
	}

	/**
	 * @return Returns the deliveryScanLocation.
	 */
	@Column(name="DLVSCNLOC")
	
	public String getDeliveryScanLocation() {
		return deliveryScanLocation;
	}

	/**
	 * @param deliveryScanLocation The deliveryScanLocation to set.
	 */
	public void setDeliveryScanLocation(String deliveryScanLocation) {
		this.deliveryScanLocation = deliveryScanLocation;
	}

	/**
	 * @return Returns the depatureDate.
	 */
	@Column(name="DEPDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getDepatureDate() {
		return depatureDate;
	}

	/**
	 * @param depatureDate The depatureDate to set.
	 */
	public void setDepatureDate(Calendar depatureDate) {
		this.depatureDate = depatureDate;
	}

	/**
	 * @return Returns the fifthFlightNumber.
	 */
	@Column(name="FFTFLTNUM")
	
	public String getFifthFlightNumber() {
		return fifthFlightNumber;
	}

	/**
	 * @param fifthFlightNumber The fifthFlightNumber to set.
	 */
	public void setFifthFlightNumber(String fifthFlightNumber) {
		this.fifthFlightNumber = fifthFlightNumber;
	}

	/**
	 * @return Returns the fifthScanCarrier.
	 */
	@Column(name="FTHSCNCAR")
	
	public String getFifthScanCarrier() {
		return fifthScanCarrier;
	}

	/**
	 * @param fifthScanCarrier The fifthScanCarrier to set.
	 */
	public void setFifthScanCarrier(String fifthScanCarrier) {
		this.fifthScanCarrier = fifthScanCarrier;
	}

	/**
	 * @return Returns the fifthScanDate.
	 */
	@Column(name="FTHSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFifthScanDate() {
		return fifthScanDate;
	}

	/**
	 * @param fifthScanDate The fifthScanDate to set.
	 */
	public void setFifthScanDate(Calendar fifthScanDate) {
		this.fifthScanDate = fifthScanDate;
	}

	/**
	 * @return Returns the fifthScanLocation.
	 */
	@Column(name="FTHSCNLOC")
	
	public String getFifthScanLocation() {
		return fifthScanLocation;
	}

	/**
	 * @param fifthScanLocation The fifthScanLocation to set.
	 */
	public void setFifthScanLocation(String fifthScanLocation) {
		this.fifthScanLocation = fifthScanLocation;
	}

	/**
	 * @return Returns the finalDestination.
	 */
	@Column(name="FNLDST")
	
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 * @param finalDestination The finalDestination to set.
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the firstCarrierCode.
	 */
	@Column(name="FSTCAR")
	
	public String getFirstCarrierCode() {
		return firstCarrierCode;
	}

	/**
	 * @param firstCarrierCode The firstCarrierCode to set.
	 */
	public void setFirstCarrierCode(String firstCarrierCode) {
		this.firstCarrierCode = firstCarrierCode;
	}

	/**
	 * @return Returns the firstScanDate.
	 */
	@Column(name="FSTSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFirstScanDate() {
		return firstScanDate;
	}

	/**
	 * @param firstScanDate The firstScanDate to set.
	 */
	public void setFirstScanDate(Calendar firstScanDate) {
		this.firstScanDate = firstScanDate;
	}

	/**
	 * @return Returns the firstScanLocation.
	 */
	@Column(name="FSTSCNLOC")
	
	public String getFirstScanLocation() {
		return firstScanLocation;
	}

	/**
	 * @param firstScanLocation The firstScanLocation to set.
	 */
	public void setFirstScanLocation(String firstScanLocation) {
		this.firstScanLocation = firstScanLocation;
	}

	/**
	 * @return Returns the fourthFlightNumber.
	 */
	@Column(name="FTHFLTNUM")
	
	public String getFourthFlightNumber() {
		return fourthFlightNumber;
	}

	/**
	 * @param fourthFlightNumber The fourthFlightNumber to set.
	 */
	public void setFourthFlightNumber(String fourthFlightNumber) {
		this.fourthFlightNumber = fourthFlightNumber;
	}

	/**
	 * @return Returns the loadScanCarrier.
	 */
	@Column(name="LODSCNCAR")
	
	public String getLoadScanCarrier() {
		return loadScanCarrier;
	}

	/**
	 * @param loadScanCarrier The loadScanCarrier to set.
	 */
	public void setLoadScanCarrier(String loadScanCarrier) {
		this.loadScanCarrier = loadScanCarrier;
	}

	/**
	 * @return Returns the loadScanDate.
	 */
	@Column(name="LODSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getLoadScanDate() {
		return loadScanDate;
	}

	/**
	 * @param loadScanDate The loadScanDate to set.
	 */
	public void setLoadScanDate(Calendar loadScanDate) {
		this.loadScanDate = loadScanDate;
	}

	/**
	 * @return Returns the loadScanFlightNumber.
	 */
	@Column(name="LODSCNFLTNUM")
	
	public String getLoadScanFlightNumber() {
		return loadScanFlightNumber;
	}

	/**
	 * @param loadScanFlightNumber The loadScanFlightNumber to set.
	 */
	public void setLoadScanFlightNumber(String loadScanFlightNumber) {
		this.loadScanFlightNumber = loadScanFlightNumber;
	}

	/**
	 * @return Returns the loadScanLocation.
	 */
	@Column(name="LODSCNLOC")
	
	public String getLoadScanLocation() {
		return loadScanLocation;
	}

	/**
	 * @param loadScanLocation The loadScanLocation to set.
	 */
	public void setLoadScanLocation(String loadScanLocation) {
		this.loadScanLocation = loadScanLocation;
	}

	/**
	 * @return Returns the mailInvoicTransportationDtlPK.
	 */
	 @EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="invoiceKey", column=@Column(name="INVKEY")),
			@AttributeOverride(name="poaCode", column=@Column(name="POACOD")),
			@AttributeOverride(name="receptacleIdentifier", column=@Column(name="RCPIDR")),
			@AttributeOverride(name="sectorOrigin", column=@Column(name="SECORG")),
			@AttributeOverride(name="sectorDestination", column=@Column(name="SECDST"))}
		)
	public MailInvoicTransportationDtlPK getMailInvoicTransportationDtlPK() {
		return mailInvoicTransportationDtlPK;
	}

	/**
	 * @param mailInvoicTransportationDtlPK The mailInvoicTransportationDtlPK to set.
	 */
	public void setMailInvoicTransportationDtlPK(
			MailInvoicTransportationDtlPK mailInvoicTransportationDtlPK) {
		this.mailInvoicTransportationDtlPK = mailInvoicTransportationDtlPK;
	}

	/**
	 * @return Returns the originalOrigin.
	 */
	@Column(name="OGLORG")
	
	public String getOriginalOrigin() {
		return originalOrigin;
	}

	/**
	 * @param originalOrigin The originalOrigin to set.
	 */
	public void setOriginalOrigin(String originalOrigin) {
		this.originalOrigin = originalOrigin;
	}

	/**
	 * @return Returns the possesionScanDate.
	 */
	@Column(name="POSSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getPossesionScanDate() {
		return possesionScanDate;
	}

	/**
	 * @param possesionScanDate The possesionScanDate to set.
	 */
	public void setPossesionScanDate(Calendar possesionScanDate) {
		this.possesionScanDate = possesionScanDate;
	}

	/**
	 * @return Returns the possessionScanCarrier.
	 */
	@Column(name="POSSCNCAR")
	
	public String getPossessionScanCarrier() {
		return possessionScanCarrier;
	}

	/**
	 * @param possessionScanCarrier The possessionScanCarrier to set.
	 */
	public void setPossessionScanCarrier(String possessionScanCarrier) {
		this.possessionScanCarrier = possessionScanCarrier;
	}

	/**
	 * @return Returns the possessionScanFlightNumber.
	 */
	@Column(name="POSSCNFLTNUM")
	
	public String getPossessionScanFlightNumber() {
		return possessionScanFlightNumber;
	}

	/**
	 * @param possessionScanFlightNumber The possessionScanFlightNumber to set.
	 */
	public void setPossessionScanFlightNumber(String possessionScanFlightNumber) {
		this.possessionScanFlightNumber = possessionScanFlightNumber;
	}

	/**
	 * @return Returns the possessionScanLocation.
	 */
	@Column(name="POSSCNLOC")
	
	public String getPossessionScanLocation() {
		return possessionScanLocation;
	}

	/**
	 * @param possessionScanLocation The possessionScanLocation to set.
	 */
	public void setPossessionScanLocation(String possessionScanLocation) {
		this.possessionScanLocation = possessionScanLocation;
	}

	/**
	 * @return Returns the scannedCarrierCode.
	 */
	@Column(name="SCNCARCOD")
	
	public String getScannedCarrierCode() {
		return scannedCarrierCode;
	}

	/**
	 * @param scannedCarrierCode The scannedCarrierCode to set.
	 */
	public void setScannedCarrierCode(String scannedCarrierCode) {
		this.scannedCarrierCode = scannedCarrierCode;
	}

	/**
	 * @return Returns the scannedFlightNumber.
	 */
	@Column(name="SCNFLTNUM")
	
	public String getScannedFlightNumber() {
		return scannedFlightNumber;
	}

	/**
	 * @param scannedFlightNumber The scannedFlightNumber to set.
	 */
	public void setScannedFlightNumber(String scannedFlightNumber) {
		this.scannedFlightNumber = scannedFlightNumber;
	}

	/**
	 * @return Returns the secondFlightNumber.
	 */
	@Column(name="SCDFLTNUM")
	
	public String getSecondFlightNumber() {
		return secondFlightNumber;
	}

	/**
	 * @param secondFlightNumber The secondFlightNumber to set.
	 */
	public void setSecondFlightNumber(String secondFlightNumber) {
		this.secondFlightNumber = secondFlightNumber;
	}

	/**
	 * @return Returns the secondScanCarrier.
	 */
	@Column(name="SCDSCNCAR")
	
	public String getSecondScanCarrier() {
		return secondScanCarrier;
	}

	/**
	 * @param secondScanCarrier The secondScanCarrier to set.
	 */
	public void setSecondScanCarrier(String secondScanCarrier) {
		this.secondScanCarrier = secondScanCarrier;
	}

	/**
	 * @return Returns the secondScanDate.
	 */
	@Column(name="SCDSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getSecondScanDate() {
		return secondScanDate;
	}

	/**
	 * @param secondScanDate The secondScanDate to set.
	 */
	public void setSecondScanDate(Calendar secondScanDate) {
		this.secondScanDate = secondScanDate;
	}

	/**
	 * @return Returns the secondScanLocation.
	 */
	@Column(name="SCDSCNLOC")
	
	public String getSecondScanLocation() {
		return secondScanLocation;
	}

	/**
	 * @param secondScanLocation The secondScanLocation to set.
	 */
	public void setSecondScanLocation(String secondScanLocation) {
		this.secondScanLocation = secondScanLocation;
	}

	/**
	 * @return Returns the thirdFlightNumber.
	 */
	@Column(name="THDFLTNUM")
	
	public String getThirdFlightNumber() {
		return thirdFlightNumber;
	}

	/**
	 * @param thirdFlightNumber The thirdFlightNumber to set.
	 */
	public void setThirdFlightNumber(String thirdFlightNumber) {
		this.thirdFlightNumber = thirdFlightNumber;
	}

	/**
	 * @return Returns the thirdScanCarrier.
	 */
	@Column(name="THDSCNCAR")
	
	public String getThirdScanCarrier() {
		return thirdScanCarrier;
	}

	/**
	 * @param thirdScanCarrier The thirdScanCarrier to set.
	 */
	public void setThirdScanCarrier(String thirdScanCarrier) {
		this.thirdScanCarrier = thirdScanCarrier;
	}

	/**
	 * @return Returns the thirdScanDate.
	 */
	@Column(name="THDSCNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getThirdScanDate() {
		return thirdScanDate;
	}

	/**
	 * @param thirdScanDate The thirdScanDate to set.
	 */
	public void setThirdScanDate(Calendar thirdScanDate) {
		this.thirdScanDate = thirdScanDate;
	}

	/**
	 * @return Returns the thirdScanLocation.
	 */
	@Column(name="THDSCNLOC")
	
	public String getThirdScanLocation() {
		return thirdScanLocation;
	}

	/**
	 * @param thirdScanLocation The thirdScanLocation to set.
	 */
	public void setThirdScanLocation(String thirdScanLocation) {
		this.thirdScanLocation = thirdScanLocation;
	}
	/**
	 * @param transportVO
	 */
	private void populateAttributes(MailInvoicTransportationDtlVO transportVO){
		this.setScannedCarrierCode(transportVO.getScannedCarrierCode());
		this.setScannedFlightNumber(transportVO.getScannedFlightNumber());
		this.setOriginalOrigin(transportVO.getOriginalOrigin());
		this.setFinalDestination(transportVO.getFinalDestination());
		this.setDepatureDate(transportVO.getDepatureDate());
		this.setArrivalDate(transportVO.getArrivalDate());
		this.setPossessionScanCarrier(transportVO.getPossessionScanCarrier());
		this.setPossessionScanFlightNumber(transportVO.getPossessionScanFlightNumber());
		this.setPossessionScanLocation(transportVO.getPossessionScanLocation());
		this.setPossesionScanDate(transportVO.getPossesionScanDate());
		this.setBookedScanCarrier(transportVO.getBookedScanCarrier());
		this.setBookedScanFlightNumber(transportVO.getBookedScanFlightNumber());
		this.setBookedScanLocation(transportVO.getBookedScanLocation());
		this.setBookedScanDate(transportVO.getBookedScanDate());
		this.setLoadScanCarrier(transportVO.getLoadScanCarrier());
		this.setLoadScanFlightNumber(transportVO.getLoadScanFlightNumber());
		this.setLoadScanLocation(transportVO.getLoadScanLocation());
		this.setLoadScanDate(transportVO.getLoadScanDate());
		this.setFirstCarrierCode(transportVO.getFirstCarrierCode());
		this.setSecondFlightNumber(transportVO.getSecondFlightNumber());
		this.setFirstScanLocation(transportVO.getFirstScanLocation());
		this.setFirstScanDate(transportVO.getFirstScanDate());
		this.setSecondScanCarrier(transportVO.getSecondScanCarrier());
		this.setThirdFlightNumber(transportVO.getThirdFlightNumber());
		this.setSecondScanLocation(transportVO.getSecondScanLocation());
		this.setSecondScanDate(transportVO.getSecondScanDate());
		this.setThirdScanCarrier(transportVO.getThirdScanCarrier());
		this.setFourthFlightNumber(transportVO.getFourthFlightNumber());
		this.setThirdScanLocation(transportVO.getThirdScanLocation());
		this.setThirdScanDate(transportVO.getThirdScanDate());
		this.setFifthScanCarrier(transportVO.getFifthScanCarrier());
		this.setFifthFlightNumber(transportVO.getFifthFlightNumber());
		this.setFifthScanLocation(transportVO.getFifthScanLocation());
		this.setFifthScanDate(transportVO.getFifthScanDate());
		this.setDeliveryScanCarrierCode(transportVO.getDeliveryScanCarrierCode());
		this.setDeliveryScanLocation(transportVO.getDeliveryScanLocation());
		this.setDeliveryScanDate(transportVO.getDeliveryScanDate());
		
	}
}