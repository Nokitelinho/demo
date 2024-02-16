/*
 * MailBagForSegment.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

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
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
//TODO

@Entity
@Table(name = "MALMRAFLNMALSEG")
@Staleable

public class MailBagForSegment {

    private MailBagForSegmentPK mailBagForSegmentPK;
    private String mailClass;
    private String originExchangeOffice;
    private String destinationExchangeOffice;
    private String mailCategoryCode;
    private String mailSubclass;
    private String dsn;
    private int year;
    private String receptacleSerialNumber;
    private String registeredOrInsuredIndicator;
    private String highestNumberedReceptacle;
    private String consignmentDocumentNumber;
    private int consignmentDocumentSequenceNumber;
    private Calendar despatchDate;
    private String scannedPort;
    private String gpaCode;
    private double weight;


    /**
     * @return Returns the consignmentDocumentNumber.
     */
   @Column(name="CSGDOCNUM")
    public String getConsignmentDocumentNumber() {
        return consignmentDocumentNumber;
    }
    /**
     * @return Returns the consignmentDocumentSequenceNumber.
     */
   @Column(name="CSGSEQNUM")
    public int getConsignmentDocumentSequenceNumber() {
        return consignmentDocumentSequenceNumber;
    }
    /**
     * @return Returns the despatchDate.
     */
   @Column(name="DSPDAT")

	@Temporal(TemporalType.DATE)
    public Calendar getDespatchDate() {
        return despatchDate;
    }
    /**
     * @return Returns the destinationExchangeOffice.
     */
  @Column(name="DSTEXGOFC")
    public String getDestinationExchangeOffice() {
        return destinationExchangeOffice;
    }
    /**
     * @return Returns the dsn.
     */
    @Column(name="DSN")
    public String getDsn() {
        return dsn;
    }
    /**
     * @return Returns the gpaCode.
     */
   @Column(name="GPACOD")
    public String getGpaCode() {
        return gpaCode;
    }
    /**
     * @return Returns the highestNumberedReceptacle.
     */
    @Column(name="HSN")
    public String getHighestNumberedReceptacle() {
        return highestNumberedReceptacle;
    }
    /**
     * @return Returns the mailBagForSegmentPK.
     */

   @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="carrierId", column=@Column(name="FLTCARIDR")),
		@AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
		@AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
		@AttributeOverride(name="segmentSerialNumber", column=@Column(name="SEGSERNUM")),
		@AttributeOverride(name="mailbagId", column=@Column(name="MALIDR"))
		}
	)
    public MailBagForSegmentPK getMailBagForSegmentPK() {
        return mailBagForSegmentPK;
    }
    /**
     * @return Returns the mailCategoryCode.
     */
   @Column(name="MALCTGCOD")
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }
    /**
     * @return Returns the mailClass.
     */
     @Column(name="MALCLS")
    public String getMailClass() {
        return mailClass;
    }
    /**
     * @return Returns the mailSubclass.
     */
     @Column(name="MALSUBCLS")
    public String getMailSubclass() {
        return mailSubclass;
    }
    /**
     * @return Returns the originExchangeOffice.
     */
     @Column(name="ORGEXGOFC")
    public String getOriginExchangeOffice() {
        return originExchangeOffice;
    }
    /**
     * @return Returns the receptacleSerialNumber.
     */
 @Column(name="RSN")
    public String getReceptacleSerialNumber() {
        return receptacleSerialNumber;
    }
    /**
     * @return Returns the registeredOrInsuredIndicator.
     */
    @Column(name="REGIND")
    public String getRegisteredOrInsuredIndicator() {
        return registeredOrInsuredIndicator;
    }
    /**
     * @return Returns the scannedPort.
     */
   @Column(name="SCNPRT")
    public String getScannedPort() {
        return scannedPort;
    }
    /**
     * @return Returns the weight.
     */
 @Column(name="WGT")
    public double getWeight() {
        return weight;
    }
    /**
     * @return Returns the year.
     */
    @Column(name="YER")
    public int getYear() {
        return year;
    }


    /**
     * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
     */
    public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
        this.consignmentDocumentNumber = consignmentDocumentNumber;
    }
    /**
     * @param consignmentDocumentSequenceNumber The consignmentDocumentSequenceNumber to set.
     */
    public void setConsignmentDocumentSequenceNumber(
            int consignmentDocumentSequenceNumber) {
        this.consignmentDocumentSequenceNumber = consignmentDocumentSequenceNumber;
    }
    /**
     * @param despatchDate The despatchDate to set.
     */
    public void setDespatchDate(Calendar despatchDate) {
        this.despatchDate = despatchDate;
    }
    /**
     * @param destinationExchangeOffice The destinationExchangeOffice to set.
     */
    public void setDestinationExchangeOffice(String destinationExchangeOffice) {
        this.destinationExchangeOffice = destinationExchangeOffice;
    }
    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }

    /**
	 * @param year The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
    }

    /**
     * @param gpaCode The gpaCode to set.
     */
    public void setGpaCode(String gpaCode) {
        this.gpaCode = gpaCode;
    }
    /**
     * @param highestNumberedReceptacle The highestNumberedReceptacle to set.
     */
    public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
        this.highestNumberedReceptacle = highestNumberedReceptacle;
    }
    /**
     * @param mailBagForSegmentPK The mailBagForSegmentPK to set.
     */
    public void setMailBagForSegmentPK(MailBagForSegmentPK mailBagForSegmentPK) {
        this.mailBagForSegmentPK = mailBagForSegmentPK;
    }
    /**
     * @param mailCategoryCode The mailCategoryCode to set.
     */
    public void setMailCategoryCode(String mailCategoryCode) {
        this.mailCategoryCode = mailCategoryCode;
    }
    /**
     * @param mailClass The mailClass to set.
     */
    public void setMailClass(String mailClass) {
        this.mailClass = mailClass;
    }
    /**
     * @param mailSubclass The mailSubclass to set.
     */
    public void setMailSubclass(String mailSubclass) {
        this.mailSubclass = mailSubclass;
    }
    /**
     * @param originExchangeOffice The originExchangeOffice to set.
     */
    public void setOriginExchangeOffice(String originExchangeOffice) {
        this.originExchangeOffice = originExchangeOffice;
    }
    /**
     * @param receptacleSerialNumber The receptacleSerialNumber to set.
     */
    public void setReceptacleSerialNumber(String receptacleSerialNumber) {
        this.receptacleSerialNumber = receptacleSerialNumber;
    }
    /**
     * @param registeredOrInsuredIndicator The registeredOrInsuredIndicator to set.
     */
    public void setRegisteredOrInsuredIndicator(
            String registeredOrInsuredIndicator) {
        this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
    }
    /**
     * @param scannedPort The scannedPort to set.
     */
    public void setScannedPort(String scannedPort) {
        this.scannedPort = scannedPort;
    }
    /**
     * @param weight The weight to set.
     */
    public void setWeight(double weight) {
        this.weight = weight;
    }

}
