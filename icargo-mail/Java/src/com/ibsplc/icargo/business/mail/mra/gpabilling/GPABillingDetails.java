/*
 * GPABillingDetails.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author Philip
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Jan 8, 2007   Philip 		            Initial draft
 *  0.2         Jan 18,2007   Indu V.K. 				Implemented the entity methods and findGPABillingEntries
 *
 *
 */


@Entity
@Table(name = "MTKGPABLGDTL")
@Staleable


public class GPABillingDetails {

    private GPABillingDetailsPK gpaBillingDetailsPK;
    private String dsn;
    private String originOfficeOfExchange;
    private String destinationOfficeOfExchange;
    private String mailCategoryCode;
    private String mailSubclass;
    private int year;
    private String receptacleSerialNumber;
    private String registeredOrInsuredIndicator;
    private String highestNumberedReceptacle;
    private Calendar receivedDate;
    private int piecesReceived;
    private double weightReceived;
    private double applicableRate;
    private double amountBillable;
    private String currencyCode;
    private String billingStatus;
    private String invoiceNumber;
    private String countryCode;
    private String ownerGPACode;
    private String consignmentNumber;
    private String remarks;
    private int consignmentSequenceNumber;
    private int flightCarrierIdentifier; 
    private String flightNumber;
    private long flightSequenceNumber;
    private int segmentSerialNumber;
    private String uldNumber;
    private String containerNumber;
    private Log log = LogFactory.getLogger("MRA GPABILLINGMASTER ");
    private static final String MODULE_NAME = "mail.mra.gpabilling";
    /**
     * Value can be M(Mailbag) if the billing basis is Mailbag
     * or D (despatch)if the billing basis is despatch
     */
    private String basisType;



    public GPABillingDetails() {

    }
    /**
     * @return Returns the amountBillable.
     */
     @Column(name="BLBAMT")
    public double getAmountBillable() {
        return amountBillable;
    }
    /**
     * @param amountBillable The amountBillable to set.
     */
    public void setAmountBillable(double amountBillable) {
        this.amountBillable = amountBillable;
    }
    /**
     * @return Returns the billingCurrencyCode.
     */
     @Column(name="CURCOD")
    public String getCurrencyCode() {
        return currencyCode;
    }
    /**
     * @param currencyCode The billingCurrencyCode to set.
     */
    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
    /**
     * @return Returns the receivedDate.
     */
     @Column(name="RCVDAT")

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
     * @return Returns the destinationOfficeOfExchange.
     */
     @Column(name="DSTEXGOFC")
    public String getDestinationOfficeOfExchange() {
        return destinationOfficeOfExchange;
    }
    /**
     * @param destinationOfficeOfExchange The destinationOfficeOfExchange to set.
     */
    public void setDestinationOfficeOfExchange(
            String destinationOfficeOfExchange) {
        this.destinationOfficeOfExchange = destinationOfficeOfExchange;
    }
    /**
     * @return Returns the dsn.
     */
    @Column(name="DSN")
    public String getDsn() {
        return dsn;
    }
    /**
     * @param dsn The dsn to set.
     */
    public void setDsn(String dsn) {
        this.dsn = dsn;
    }
    /**
     * @return Returns the gpaBillingDetailsPK.
     */


    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
		@AttributeOverride(name="billingBasis", column=@Column(name="BLGBAS")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
	)
    public GPABillingDetailsPK getGpaBillingDetailsPK() {
        return gpaBillingDetailsPK;
    }
    /**
     * @param gpaBillingDetailsPK The gpaBillingDetailsPK to set.
     */
    public void setGpaBillingDetailsPK(GPABillingDetailsPK gpaBillingDetailsPK) {
        this.gpaBillingDetailsPK = gpaBillingDetailsPK;
    }
    /**
     * @return Returns the highestNumberedReceptacle.
     */
 @Column(name="HSN")
    public String getHighestNumberedReceptacle() {
        return highestNumberedReceptacle;
    }
    /**
     * @param highestNumberedReceptacle The highestNumberedReceptacle to set.
     */
    public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
        this.highestNumberedReceptacle = highestNumberedReceptacle;
    }
    /**
     * @return Returns the mailCategoryCode.
     */
 @Column(name="MALCTGCOD")
    public String getMailCategoryCode() {
        return mailCategoryCode;
    }
    /**
     * @param mailCategoryCode The mailCategoryCode to set.
     */
    public void setMailCategoryCode(String mailCategoryCode) {
        this.mailCategoryCode = mailCategoryCode;
    }

    /**
     * @return Returns the remarks.
     */
 @Column(name="BLGRMK")
    public String getRemarks() {
        return remarks;
    }
    /**
     * @param remarks The remarks to set.
     */
    public void setRemarks(String remarks) {
    	log.log(log.INFO,"remarks set");
        this.remarks = remarks;
    }
    /**
     * @return Returns the mailSubclass.
     */
 @Column(name="MALSUBCLS")
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
 @Column(name="ORGEXGOFC")
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
     * @return Returns the piecesReceived.
     */
 @Column(name="RCVPCS")
    public int getPiecesReceived() {
        return piecesReceived;
    }
    /**
     * @param piecesReceived The piecesReceived to set.
     */
    public void setPiecesReceived(int piecesReceived) {
        this.piecesReceived = piecesReceived;
    }
    /**
     * @return Returns the rateApplicable.
     */
 @Column(name="APLRAT")
    public double getApplicableRate() {
        return applicableRate;
    }
    /**
     * @param applicableRate The rateApplicable to set.
     */
    public void setApplicableRate(double applicableRate) {
        this.applicableRate = applicableRate;
    }
    /**
     * @return Returns the receptacleSerialNumber.
     */
 @Column(name="RSN")
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
     * @return Returns the registeredOrInsuredIndicator.
     */
 @Column(name="REGIND")
    public String getRegisteredOrInsuredIndicator() {
        return registeredOrInsuredIndicator;
    }
    /**
     * @param registeredOrInsuredIndicator The registeredOrInsuredIndicator to set.
     */
    public void setRegisteredOrInsuredIndicator(
            String registeredOrInsuredIndicator) {
        this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
    }
    /**
     * @return Returns the weightReceived.
     */
 @Column(name="RCVWGT")
    public double getWeightReceived() {
        return weightReceived;
    }
    /**
     * @param weightReceived The weightReceived to set.
     */
    public void setWeightReceived(double weightReceived) {
        this.weightReceived = weightReceived;
    }
    /**
     * @return Returns the last digit of the year.
     */
 @Column(name="YER")
    public int getYear() {
        return year;
    }
    /**
     * @param year The year to set.
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return Returns the basisType.
     *
     * Value can be M(Mailbag) if the billing basis is Mailbag
     * or D (despatch)if the billing basis is despatch
     */
 @Column(name="BASTYP")
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
     * @return Returns the billingStatus.
     */
 @Column(name="BLGSTA")
    public String getBillingStatus() {
        return billingStatus;
    }
    /**
     * @param billingStatus The billingStatus to set.
     */
    public void setBillingStatus(String billingStatus) {
    	log.log(log.INFO,"Billing Status set");
        this.billingStatus = billingStatus;
    }

    /**
     * @return Returns the invoiceNumber.
     */
 @Column(name="INVNUM")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    /**
     * @param invoiceNumber The invoiceNumber to set.
     */
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    /**
     * @return Returns the countryCode.
     */
 @Column(name="CNTCOD")
    public String getCountryCode() {
        return countryCode;
    }
    /**
     * @param countryCode The countryCode to set.
     */
    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    /**
     * @return Returns the ownerGPACode.
     */
@Column(name="OWNGPACOD")
    public String getOwnerGPACode() {
        return ownerGPACode;
    }
    /**
     * @param ownerGPACode The ownerGPACode to set.
     */
    public void setOwnerGPACode(String ownerGPACode) {
        this.ownerGPACode = ownerGPACode;
    }

 @Column(name="CSGDOCNUM")
    /**
     * @return Returns the consignmentNumber.
     */
    public String getConsignmentNumber() {
        return consignmentNumber;
    }
    /**
     * @param consignmentNumber The consignmentNumber to set.
     */
    public void setConsignmentNumber(String consignmentNumber) {
        this.consignmentNumber = consignmentNumber;
    }

    @Column(name="CSGSEQNUM")
    /**
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}
	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}
	@Column(name="CONNUM")
	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	@Column(name="FLTCARIDR")
	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	/**
	 * @param flightCarrierIdentifier The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	@Column(name="FLTNUM")
	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	@Column(name="FLTSEQNUM")
	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	/**
	 * @param flightSequenceNumber The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	@Column(name="SEGSERNUM")
	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}
	/**
	 * @param segmentSerialNumber The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}
	@Column(name="ULDNUM")
	/**
	 * @return Returns the uldNumber.
	 */
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
     * Finds tand returns the GPA Billing entries available
     * This includes billed, billable and on hold despatches
     *
     * @param gpaBillingEntriesFilterVO
     * @return Collection<GPABillingDetailsVO>
     * @throws SystemException
     */

    public static Collection<GPABillingDetailsVO> findGPABillingEntries(
            GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
    throws SystemException{
    	Collection<GPABillingDetailsVO> gpaBillingDetailsVOs=null;
    	try{
		gpaBillingDetailsVOs=constructDAO().findGPABillingEntries(gpaBillingEntriesFilterVO);
	      }catch(PersistenceException persistenceException) {
		persistenceException.getErrorCode();
	    }

        return gpaBillingDetailsVOs;
    }
    /**
     * @author A-2391
     *  finds the entity
     * @param companyCode
     * @param gpaCode
     * @param billingBasis
     * @param sequenceNumber
     * @return GPABillingDetails
     * @throws SystemException
     * @throws FinderException
     */
    public static GPABillingDetails find(String companyCode,
            String gpaCode,String billingBasis,int sequenceNumber)
    throws SystemException,FinderException {
    	GPABillingDetailsPK pk = new GPABillingDetailsPK();
		pk.setCompanyCode(   companyCode);
		pk.setGpaCode(   gpaCode);
		pk.setBillingBasis(   billingBasis);
		pk.setSequenceNumber( sequenceNumber);
		return PersistenceController.getEntityManager().find(
				GPABillingDetails.class, pk);

    }
    /**
     * @author A-2391
     * removes the entity
     * @throws RemoveException
     * @throws SystemException
     */

    public void remove()throws RemoveException,SystemException{
    	PersistenceController.getEntityManager().remove(this);
    }
    /**
     * @author A-2391
     * updates the entity
     * @throws SystemException
     * @throws RemoveException
     * @throws CreateException
     * @throws FinderException
     */
    public void update() throws SystemException,
	RemoveException,CreateException,FinderException{}
    /**
     * method for calling up the DAO for the submodule
     * @author a-2391
     * @return queryDAO
     * @throws SystemException
     */
    private static MRAGPABillingDAO constructDAO()
    									throws SystemException {
    	MRAGPABillingDAO queryDAO =null;
        try {
			 queryDAO = (MRAGPABillingDAO)PersistenceController
			 								.getEntityManager()
							 				.getQueryDAO(MODULE_NAME);
        } catch (PersistenceException e) {
				throw new SystemException(e.getMessage(),e);
        }

        return queryDAO;
    }
}
