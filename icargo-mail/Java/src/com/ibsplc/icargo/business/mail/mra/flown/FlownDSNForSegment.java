/*
 * FlownDSNForSegment.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

/**
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;



/**
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */

@Entity
@Table(name = "MRADSNSEG")
@Staleable
@Deprecated
public class FlownDSNForSegment {

    private FlownDSNForSegmentPK flownDSNForSegmentPK;
    private String mailClass;
    private int acceptedBags;
    private double acceptedWeight;

    /**
     * @return Returns the acceptedBags.
     */
 @Column(name="ACPBAG")
    public int getAcceptedBags() {
        return acceptedBags;
    }
    /**
     * @param acceptedBags The acceptedBags to set.
     */
    public void setAcceptedBags(int acceptedBags) {
        this.acceptedBags = acceptedBags;
    }
    /**
     * @return Returns the acceptedWeight.
     */
 @Column(name="ACPWGT")
    public double getAcceptedWeight() {
        return acceptedWeight;
    }
    /**
     * @param acceptedWeight The acceptedWeight to set.
     */
    public void setAcceptedWeight(double acceptedWeight) {
        this.acceptedWeight = acceptedWeight;
    }
    /**
     * @return Returns the flownDSNForSegmentPK.
     */


   @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="carrierId", column=@Column(name="FLTCARIDR")),
		@AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
		@AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
		@AttributeOverride(name="segmentSerialNumber", column=@Column(name="SEGSERNUM")),
		@AttributeOverride(name="originExchangeOffice", column=@Column(name="ORGEXGOFC")),
		@AttributeOverride(name="destinationExchangeOffice", column=@Column(name="DSTEXGOFC")),
		@AttributeOverride(name="mailCategoryCode", column=@Column(name="MALCTGCOD")),
		@AttributeOverride(name="mailSubclass", column=@Column(name="MALSUBCLS")),
		@AttributeOverride(name="dsn", column=@Column(name="DSN")),
		@AttributeOverride(name="year", column=@Column(name="YER"))}
	)
    public FlownDSNForSegmentPK getFlownDSNForSegmentPK() {
        return flownDSNForSegmentPK;
    }
    /**
     * @param flownDSNForSegmentPK The flownDSNForSegmentPK to set.
     */
    public void setFlownDSNForSegmentPK(FlownDSNForSegmentPK flownDSNForSegmentPK) {
        this.flownDSNForSegmentPK = flownDSNForSegmentPK;
    }
    /**
     * @return Returns the mailClass.
     */
 @Column(name="MALCLS")
    public String getMailClass() {
        return mailClass;
    }
    /**
     * @param mailClass The mailClass to set.
     */
    public void setMailClass(String mailClass) {
        this.mailClass = mailClass;
    }
    /**
     */
    public static void findFlownMails() {
    }
}
