/*
 * CN51Details.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling;



import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibm.icu.util.Calendar;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/*
 * @author A-2280
 * Revision History
 *
 * Version      Date           Author          		    Description
 *
 *  0.1         Dec 28, 2006   Philip Scaria 				Initial draft
 *  0.2         Jan 18, 2007   Prem Kumar.M                  Implemented findCn51Details
 *  0.3			Dec 07,	2017	Remya I R					Updated Table field changes
 */
/**
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
//TODO

@Entity
@Table(name = "MALMRAGPAC51DTL")
@Staleable


public class CN51Details {

	private static final String CLASS_NAME="CN51Details";
	private static final String MRA_GPABILLING="mail.mra.gpabilling";
	private Log log=localLogger();
    private CN51DetailsPK cn51DetailsPK;
    private String origin;
    private String destination;
    private String mailCategoryCode;
    private String mailSubclass;
    private int totalPieces;
    private double totalWeight;
    private String unitCode;
    private double applicableRate;
    private double totalAmountinBillingCurr;
    private String contractCurrencyCode;
    private String billingCurrencyCode;
    private double vatAmountInBillingCurrency;
    private double totalAmtExcludeVATinBlgCurrency;
    private double serviceTaxInBillingCurr;
    private double tdsInBillingCurr;
    private double netAmountInBillingCurr;
    private double valuationChargeInBillingCurrency;
    private double mailChargeInBillingCurr;
    private double otherChargesInBillingCurr;
    private String lastUpdatedUser;
    private Calendar lastUpdatedTime;
    
	
	

    public CN51Details() {

    }
   
    @EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="invoiceNumber", column=@Column(name="INVNUM")),
		@AttributeOverride(name="gpaCode", column=@Column(name="GPACOD")),
		@AttributeOverride(name="invSerialNumber", column=@Column(name="INVSERNUM")),
		@AttributeOverride(name="sequenceNumber", column=@Column(name="SEQNUM"))}
	)
    public CN51DetailsPK getCn51DetailsPK() {
        return cn51DetailsPK;
    }
    /**
     * @param cn51DetailsPK The cn51DetailsPK to set.
     */
    public void setCn51DetailsPK(CN51DetailsPK cn51DetailsPK) {
        this.cn51DetailsPK = cn51DetailsPK;
    }
   

    /**
	 * @return the origin
	 */
    @Column(name = "ORGCOD")
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
	 * @return the destination
	 */
	@Column(name = "DSTCOD")
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
	 * @return the mailCategoryCode
	 */
	@Column(name = "MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode the mailCategoryCode to set
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return the mailSubclass
	 */
	@Column(name = "SUBCLSGRP")
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass the mailSubclass to set
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return the totalPieces
	 */
	@Column(name = "TOTPCS")
	public int getTotalPieces() {
		return totalPieces;
	}

	/**
	 * @param totalPieces the totalPieces to set
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * @return the totalWeight
	 */
	@Column(name = "TOTWGT")
	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight the totalWeight to set
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return the unitCode
	 */
	@Column(name = "UNTCOD")
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the applicableRate
	 */
	@Column(name = "APLRAT")
	public double getApplicableRate() {
		return applicableRate;
	}

	/**
	 * @param applicableRate the applicableRate to set
	 */
	public void setApplicableRate(double applicableRate) {
		this.applicableRate = applicableRate;
	}

	/**
	 * @return the totalAmountinBillingCurr
	 */
	@Column(name = "TOTAMTBLGCUR")
	public double getTotalAmountinBillingCurr() {
		return totalAmountinBillingCurr;
	}

	/**
	 * @param totalAmountinBillingCurr the totalAmountinBillingCurr to set
	 */
	public void setTotalAmountinBillingCurr(double totalAmountinBillingCurr) {
		this.totalAmountinBillingCurr = totalAmountinBillingCurr;
	}

	/**
	 * @return the contractCurrencyCode
	 */
	@Column(name = "CTRCURCOD")
	public String getContractCurrencyCode() {
		return contractCurrencyCode;
	}

	/**
	 * @param contractCurrencyCode the contractCurrencyCode to set
	 */
	public void setContractCurrencyCode(String contractCurrencyCode) {
		this.contractCurrencyCode = contractCurrencyCode;
	}

	/**
	 * @return the billingCurrencyCode
	 */
	@Column(name = "BLGCURCOD")
	public String getBillingCurrencyCode() {
		return billingCurrencyCode;
	}

	/**
	 * @param billingCurrencyCode the billingCurrencyCode to set
	 */
	public void setBillingCurrencyCode(String billingCurrencyCode) {
		this.billingCurrencyCode = billingCurrencyCode;
	}

	/**
	 * @return the vatAmountInBillingCurrency
	 */
	@Column(name = "VATAMTBLGCUR")
	public double getVatAmountInBillingCurrency() {
		return vatAmountInBillingCurrency;
	}

	/**
	 * @param vatAmountInBillingCurrency the vatAmountInBillingCurrency to set
	 */
	public void setVatAmountInBillingCurrency(double vatAmountInBillingCurrency) {
		this.vatAmountInBillingCurrency = vatAmountInBillingCurrency;
	}

	/**
	 * @return the totalAmtExcludeVATinBlgCurrency
	 */
	@Column(name = "TOTAMTEXCVATBLGCUR")
	public double getTotalAmtExcludeVATinBlgCurrency() {
		return totalAmtExcludeVATinBlgCurrency;
	}

	/**
	 * @param totalAmtExcludeVATinBlgCurrency the totalAmtExcludeVATinBlgCurrency to set
	 */
	public void setTotalAmtExcludeVATinBlgCurrency(
			double totalAmtExcludeVATinBlgCurrency) {
		this.totalAmtExcludeVATinBlgCurrency = totalAmtExcludeVATinBlgCurrency;
	}

	/**
	 * @return the serviceTaxInBillingCurr
	 */
	@Column(name = "SRVTAXBLGCUR")
	public double getServiceTaxInBillingCurr() {
		return serviceTaxInBillingCurr;
	}

	/**
	 * @param serviceTaxInBillingCurr the serviceTaxInBillingCurr to set
	 */
	public void setServiceTaxInBillingCurr(double serviceTaxInBillingCurr) {
		this.serviceTaxInBillingCurr = serviceTaxInBillingCurr;
	}

	/**
	 * @return the tdsInBillingCurr
	 */
	@Column(name = "TDSBLGCUR")
	public double getTdsInBillingCurr() {
		return tdsInBillingCurr;
	}

	/**
	 * @param tdsInBillingCurr the tdsInBillingCurr to set
	 */
	public void setTdsInBillingCurr(double tdsInBillingCurr) {
		this.tdsInBillingCurr = tdsInBillingCurr;
	}

	/**
	 * @return the netAmountInBillingCurr
	 */
	@Column(name = "NETAMTBLGCUR")
	public double getNetAmountInBillingCurr() {
		return netAmountInBillingCurr;
	}

	/**
	 * @param netAmountInBillingCurr the netAmountInBillingCurr to set
	 */
	public void setNetAmountInBillingCurr(double netAmountInBillingCurr) {
		this.netAmountInBillingCurr = netAmountInBillingCurr;
	}

	/**
	 * @return the valuationChargeInBillingCurrency
	 */
	@Column(name = "VALCHGBLGCUR")
	public double getValuationChargeInBillingCurrency() {
		return valuationChargeInBillingCurrency;
	}

	/**
	 * @param valuationChargeInBillingCurrency the valuationChargeInBillingCurrency to set
	 */
	public void setValuationChargeInBillingCurrency(
			double valuationChargeInBillingCurrency) {
		this.valuationChargeInBillingCurrency = valuationChargeInBillingCurrency;
	}

	/**
	 * @return the mailChargeInBillingCurr
	 */
	@Column(name = "MALCHGBLGCUR")
	public double getMailChargeInBillingCurr() {
		return mailChargeInBillingCurr;
	}

	/**
	 * @param mailChargeInBillingCurr the mailChargeInBillingCurr to set
	 */
	public void setMailChargeInBillingCurr(double mailChargeInBillingCurr) {
		this.mailChargeInBillingCurr = mailChargeInBillingCurr;
	}

	/**
	 * @return the otherChargesInBillingCurr
	 */
	@Column(name = "OTHCHGBLGCUR")
	public double getOtherChargesInBillingCurr() {
		return otherChargesInBillingCurr;
	}

	/**
	 * @param otherChargesInBillingCurr the otherChargesInBillingCurr to set
	 */
	public void setOtherChargesInBillingCurr(double otherChargesInBillingCurr) {
		this.otherChargesInBillingCurr = otherChargesInBillingCurr;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
     *
     * @param companyCode
     * @param invoiceNumber
     * @param gpaCode
     * @param sequenceNumber
     * @return
     * @throws SystemException
     * @throws FinderException
     */
	//Implemented by A-7794 as part of MRA revamp
    public static CN51Details find(String companyCode, String invoiceNumber,
            String gpaCode,int sequenceNumber, int invSerialNumber)
    throws SystemException,FinderException {
    	CN51DetailsPK pk = new CN51DetailsPK();
    	pk.setCompanyCode(companyCode);
    	pk.setGpaCode(gpaCode);
    	pk.setInvoiceNumber(invoiceNumber);
    	pk.setSequenceNumber(sequenceNumber);
    	pk.setInvSerialNumber(invSerialNumber);
    	return PersistenceController.getEntityManager().find(
    			CN51Details.class, pk);
    }
    /**
     *
     * @throws RemoveException
     * @throws SystemException
     */
    public void remove()throws RemoveException,SystemException{
    	log.entering(CLASS_NAME,"remove"); 
    	PersistenceController.getEntityManager().remove(this);
    	log.exiting(CLASS_NAME,"remove");
    }

    /**
     *
     * @throws SystemException
     * @throws RemoveException
     * @throws CreateException
     * @throws FinderException
     */
    public void update() throws SystemException,
	RemoveException,CreateException,FinderException{}
    /**
     * This method is to get the refernec of log
     * @author A-2280
     * @return
     */
    private static Log localLogger() {
		return LogFactory.getLogger("MRA GPABilling");
	}
    /**
     * Finds and returns the CN51 details
     *
     * @param cn51CN66FilterVO
     * @return Collection<CN51DetailsVO>
     * @throws SystemException
     */
    //TODO Collection<CN51DetailsVO>
    public Page<CN51DetailsVO> findCN51Details(CN51CN66FilterVO cn51CN66FilterVO)
    throws SystemException{
    	localLogger().entering(CLASS_NAME,"findCN51Details");
    	Page<CN51DetailsVO> cn51DetailsVOs=null;
    	try {
    		cn51DetailsVOs=constructDAO().findCN51Details(cn51CN66FilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		}
		localLogger().exiting(CLASS_NAME,"findCN51Details");
        return cn51DetailsVOs;
    }

    /**
     * @author A-2280
     * This method is to get the SqlDao Reference
     * @return
     * @throws PersistenceException
     * @throws SystemException
     */
    private static MRAGPABillingDAO constructDAO()throws PersistenceException, SystemException{


		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRAGPABillingDAO.class.cast(
				entityManager.getQueryDAO(MRA_GPABILLING));
	}
}
