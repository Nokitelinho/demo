/*
 * AirlineCN51Details.java Created on Feb 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;



import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1946
 * 
 */
/*
 * Revision History
 * -------------------------------------------------------------------------
 * Revision Date Author Description
 * ------------------------------------------------------------------------- 0.1
 * feb 16, 2007 A-1946 Created
 */

@Staleable
@Table(name = "MALMRAARLC51DTL")
@Entity
public class AirlineCN51Details {

	private static final String MODULE_NAME = "mail.mra.airlinebilling";

	private static final String CLASSNAME = "AirlineCN51Details";

	private Log log = LogFactory.getLogger("MAilTRACKING  MRA");

	private static final int DEFAULT_NUMBER_VALUE = 0;

	private static final String DEFAULT_BLANK_VALUE = "";

	private AirlineCN51DetailsPK airlineCN51DetailsPK;

	// private String clearanceperiod;
	private String carriagefrom;

	private String carriageto;

	private String mailcategory;

	private String mailsubclass;

	private int totalpieces;

	private double totalweight;

	private double applicablerate;

	private double totalamountincontractcurrency;

	private double totalamountinbillingcurrency;

	private String billingcurrencycode;

	private String contractcurrencycode;
	//added by a-7929 as part of ICRD-265471
	
	private String listingcurrencycode;
	private double otherChargeinlistingcurrency;
	private double mailChargeincontractcurrency;
	

	

	/**
	 * @return Returns the airlineCN51DetailsPK.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineidr", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "interlinebillingtype", column = @Column(name = "INTBLGTYP")),
			@AttributeOverride(name = "invoicenumber", column = @Column(name = "INVNUM")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")),
			@AttributeOverride(name = "sequencenumber", column = @Column(name = "SEQNUM")) })
	public AirlineCN51DetailsPK getAirlineCN51DetailsPK() {
		return airlineCN51DetailsPK;
	}

	/**
	 * @param airlineCN51DetailsPK
	 *            The airlineCN51DetailsPK to set.
	 */
	public void setAirlineCN51DetailsPK(
			AirlineCN51DetailsPK airlineCN51DetailsPK) {
		this.airlineCN51DetailsPK = airlineCN51DetailsPK;
	}

	/**
	 * @return Returns the applicablerate.
	 */
	@Column(name = "APLRAT")
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
	@Column(name = "CARFRM")
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
	@Column(name = "CARTOO")
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
	/*
	 * @Column(name = "CLRPRD") public String getClearanceperiod() { return
	 * clearanceperiod; }
	 */
	/**
	 * @param clearanceperiod
	 *            The clearanceperiod to set.
	 */
	/*
	 * public void setClearanceperiod(String clearanceperiod) {
	 * this.clearanceperiod = clearanceperiod; }
	 */

	/**
	 * @return Returns the contractcurrencycode.
	 */
	@Column(name = "CRTCURCOD")
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
	 * @return Returns the mailcategory.
	 */
	@Column(name = "MALCTGCOD")
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
	@Column(name = "SUBCLSGRP")
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
	 * @return Returns the totalamountincontractcurrency.
	 */
	@Column(name = "TOTAMTCRTCUR")
	public double getTotalamountincontractcurrency() {
		return totalamountincontractcurrency;
	}

	/**
	 * @param totalamountincontractcurrency
	 *            The totalamountincontractcurrency to set.
	 */
	public void setTotalamountincontractcurrency(
			double totalamountincontractcurrency) {
		this.totalamountincontractcurrency = totalamountincontractcurrency;
	}

	/**
	 * @return Returns the totalpieces.
	 */
	@Column(name = "TOTPCS")
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
	@Column(name = "TOTWGT")
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
	 * @return Returns the billingcurrencycode.
	 */
	@Column(name = "BLGCURCOD")
	public String getBillingcurrencycode() {
		return billingcurrencycode;
	}

	/**
	 * @param billingcurrencycode
	 *            The billingcurrencycode to set.
	 */
	public void setBillingcurrencycode(String billingcurrencycode) {
		this.billingcurrencycode = billingcurrencycode;
	}

	/**
	 * @return Returns the totalamountinbillingcurrency.
	 */
	@Column(name = "TOTAMTLSTCUR")   //Modified by A-7929 as part of ICRD-265471
	public double getTotalamountinbillingcurrency() {
		return totalamountinbillingcurrency;
	}

	/**
	 * @param totalamountinbillingcurrency
	 *            The totalamountinbillingcurrency to set.
	 */
	public void setTotalamountinbillingcurrency(
			double totalamountinbillingcurrency) {
		this.totalamountinbillingcurrency = totalamountinbillingcurrency;
	}
	/**
	 * @return listingcurrencycode
	 */
	
	@Column(name = "LSTCURCOD")
	public String getListingcurrencycode() {
		return listingcurrencycode;
	}
	/**
	 * 
	 * @param listingcurrencycode
	 */
	public void setListingcurrencycode(String listingcurrencycode) {
		this.listingcurrencycode = listingcurrencycode;
	}
	/**
	 *  @return otherChargeinlistingcurrency
	 */
	@Column(name = "OTHCHGLSTCUR")
	public double getOtherChargeinlistingcurrency() {
		return otherChargeinlistingcurrency;
	}
	/**
	 * 
	 * @param otherChargeinlistingcurrency
	 */
	public void setOtherChargeinlistingcurrency(double otherChargeinlistingcurrency) {
		this.otherChargeinlistingcurrency = otherChargeinlistingcurrency;
	}
	/**
	 * 
	 * @return mailChargeincontractcurrency
	 */
	@Column(name = "MALCHGLSTCUR")
	public double getMailChargeincontractcurrency() {
		return mailChargeincontractcurrency;
	}

   /**
     * 
     * @param mailChargeincontractcurrency
     */
	public void setMailChargeincontractcurrency(double mailChargeincontractcurrency) {
		this.mailChargeincontractcurrency = mailChargeincontractcurrency;
	}

	private static Log staticLogger() {
		return LogFactory.getLogger("MAILTRACKING:MRA");
	}

	/**
	 * default constructor
	 * 
	 */
	public AirlineCN51Details() {

	}

	/**
	 * 
	 * @param cn51DetailsVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public AirlineCN51Details(AirlineCN51DetailsVO cn51DetailsVO)
			throws SystemException, CreateException {
		log.entering(CLASSNAME, "new AirlineCN51Details Creation ");
		this.populateCN51DetailsPK(cn51DetailsVO);
		this.populateAllAttributes(cn51DetailsVO);
		log.log(Log.FINE, "###### going for persistence ");
		PersistenceController.getEntityManager().persist(this);
		log.entering(CLASSNAME, "new AirlineCN51Details Creation ");
	}

	/**
	 * 
	 * @param cn51DetailsVO
	 */
	private void populateCN51DetailsPK(AirlineCN51DetailsVO cn51DetailsVO) {
		log.entering(CLASSNAME, "populateCN51DetailsPK");
		AirlineCN51DetailsPK pkForCreate = new AirlineCN51DetailsPK(
				cn51DetailsVO.getCompanycode(), cn51DetailsVO.getAirlineidr(),
				cn51DetailsVO.getInterlinebillingtype(), cn51DetailsVO
						.getInvoicenumber(),
				cn51DetailsVO.getClearanceperiod(), cn51DetailsVO
						.getSequenceNumber());
		this.setAirlineCN51DetailsPK(pkForCreate);
		log.exiting(CLASSNAME, "populateCN51DetailsPK");
	}

	/**
	 * 
	 * @param cn51DetailsVO
	 */
	private void populateAllAttributes(AirlineCN51DetailsVO cn51DetailsVO) {
		log.entering(CLASSNAME, "populateAllAttributes");
		this.setCarriagefrom(cn51DetailsVO.getCarriagefrom());
		this.setCarriageto(cn51DetailsVO.getCarriageto());
		this.setMailcategory(cn51DetailsVO.getMailcategory());
		this.setMailsubclass(cn51DetailsVO.getMailsubclass());

		this.setTotalpieces(DEFAULT_NUMBER_VALUE);
		this.setTotalamountinbillingcurrency(DEFAULT_NUMBER_VALUE);
		this.setContractcurrencycode(DEFAULT_BLANK_VALUE);
		this.setBillingcurrencycode(DEFAULT_BLANK_VALUE);

		double modifiedRate = cn51DetailsVO.getApplicablerate();
		double modifiedWeight = cn51DetailsVO.getTotalweight();
		this.setTotalweight(modifiedWeight);
		this.setApplicablerate(modifiedRate);
		this.setTotalamountincontractcurrency(cn51DetailsVO.getTotalamountincontractcurrency().getRoundedAmount());

		log.exiting(CLASSNAME, "populateAllAttributes");
	}

	/**
	 * 
	 * @param cn51DetailsVO
	 */
	public void update(AirlineCN51DetailsVO cn51DetailsVO) {
		log.entering(CLASSNAME, "update");
		populateAllAttributes(cn51DetailsVO);
		log.exiting(CLASSNAME, "update");
	}

	/**
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		log.entering(CLASSNAME, "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (OptimisticConcurrencyException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
		log.exiting(CLASSNAME, "remove");
	}

	/**
	 * 
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param interlineBillingType
	 * @param invoiceNumber
	 * @param clearancePeriod
	 * @param sequenceNumber
	 * @return AirlineCN51Details
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static AirlineCN51Details find(String companyCode,
			int airlineIdentifier, String interlineBillingType,
			String invoiceNumber, String clearancePeriod, int sequenceNumber)
			throws FinderException, SystemException {
		staticLogger().entering(CLASSNAME, "find");
		AirlineCN51DetailsPK pkForFinding = new AirlineCN51DetailsPK(
				companyCode, airlineIdentifier, interlineBillingType,
				invoiceNumber, clearancePeriod, sequenceNumber);
		AirlineCN51Details foundEntity = null;
		foundEntity = PersistenceController.getEntityManager().find(
				AirlineCN51Details.class, pkForFinding);

		staticLogger().exiting(CLASSNAME, "find");

		return foundEntity;
	}

	/**
	 * Method findInwardInvoicesCollection
	 * 
	 * @param airlineCN51FilterVO
	 * @throws SystemException
	 * @return airlineCN51DetailsVos
	 * @author A-2458 added for reports
	 */
	public static Collection<AirlineCN51DetailsVO> findInwardInvoicesCollection(
			AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		Collection<AirlineCN51DetailsVO> airlineCN51DetailsVos = null;
		log.entering(CLASSNAME, "findInwardInvoicesCollection");
		try {
			airlineCN51DetailsVos = constructDAO()
					.findInwardInvoicesCollection(airlineCN51FilterVO);
		} catch (SystemException e) {
			throw new SystemException(e.getMessage());
		}
		log.exiting(CLASSNAME, "findInwardInvoicesCollection");
		return airlineCN51DetailsVos;
	}

	private static MRAAirlineBillingDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MRAAirlineBillingDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}

	}

}
