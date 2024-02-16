/*
 * MRAProrationDetails.java Created on Mar 07, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchLovVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-2518
 * 
 */
@Entity
@Table(name = "MTKPRODTL")
@Staleable
@Deprecated
public class MRAProrationDetails {
	private static final String CLASS_NAME = "ProrationDetails";

	// private Log log = LogFactory.getLogger("MRA DEFAULTS");

	private static final String MODULE_NAME = "mail.mra.defaults";

	private MRAProrationDetailsPK prorationDetailsPk;

	private String basisType;

	private String origingExchangeOffice;

	private String destinationExchangeOffice;

	private String mailCategoryCode;

	private String mailSubclass;

	private int pieces;

	private double weight;

	private String consigneeDocumentNumber;

	private int flightCarrierIdentifier;

	private String flightNumber;

	private int flightSequenceNumber;

	private int segmentSequenceNumber;

	private String postalAuthorityCode;

	private String sectorFrom;

	private String sectorTo;

	private String prorationType;

	private double prorationPercentage;

	private String payableFlag;

	private double prorationAmountInUsd;

	private Calendar flightDate;

	private double prorationAmountInSdr;

	private double prorationAmountInBasCurr;

	private double proratedValue;

	/**
	 * @return Returns the basisType.
	 */
	@Column(name = "BASTYP")
	public String getBasisType() {
		return basisType;
	}

	/**
	 * @param basisType
	 *            The basisType to set.
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	/**
	 * @return Returns the consigneeDocumentNumber.
	 */
	@Column(name = "CSGDOCNUM")
	public String getConsigneeDocumentNumber() {
		return consigneeDocumentNumber;
	}

	/**
	 * @param consigneeDocumentNumber
	 *            The consigneeDocumentNumber to set.
	 */
	public void setConsigneeDocumentNumber(String consigneeDocumentNumber) {
		this.consigneeDocumentNumber = consigneeDocumentNumber;
	}

	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	@Column(name = "DSTEXGOFC")
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice
	 *            The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
	}

	/**
	 * @return Returns the flightCarrierIdentifier.
	 */
	@Column(name = "FLTCARIDR")
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}

	/**
	 * @param flightCarrierIdentifier
	 *            The flightCarrierIdentifier to set.
	 */
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}

	/**
	 * @return Returns the flightDate.
	 */
	@Column(name = "FLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	@Column(name = "FLTNUM")
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	@Column(name = "FLTSEQNUM")
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	@Column(name = "MALCTGCOD")
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	@Column(name = "MALSUBCLS")
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass
	 *            The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return Returns the origingExchangeOffice.
	 */
	@Column(name = "ORGEXGOFC")
	public String getOrigingExchangeOffice() {
		return origingExchangeOffice;
	}

	/**
	 * @param origingExchangeOffice
	 *            The origingExchangeOffice to set.
	 */
	public void setOrigingExchangeOffice(String origingExchangeOffice) {
		this.origingExchangeOffice = origingExchangeOffice;
	}

	/**
	 * @return Returns the payableFlag.
	 */
	@Column(name = "PAYFLG")
	public String getPayableFlag() {
		return payableFlag;
	}

	/**
	 * @param payableFlag
	 *            The payableFlag to set.
	 */
	public void setPayableFlag(String payableFlag) {
		this.payableFlag = payableFlag;
	}

	/**
	 * @return Returns the pieces.
	 */
	@Column(name = "PCS")
	public int getPieces() {
		return pieces;
	}

	/**
	 * @param pieces
	 *            The pieces to set.
	 */
	public void setPieces(int pieces) {
		this.pieces = pieces;
	}

	/**
	 * @return Returns the postalAuthorityCode.
	 */
	@Column(name = "POACOD")
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode
	 *            The postalAuthorityCode to set.
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 * @return Returns the prorationAmountInBasCurr.
	 */
	@Column(name = "PROAMTBAS")
	public double getProrationAmountInBasCurr() {
		return prorationAmountInBasCurr;
	}

	/**
	 * @param prorationAmountInBasCurr
	 *            The prorationAmountInBasCurr to set.
	 */
	public void setProrationAmountInBasCurr(double prorationAmountInBasCurr) {
		this.prorationAmountInBasCurr = prorationAmountInBasCurr;
	}

	/**
	 * @return Returns the prorationAmountInSdr.
	 */
	@Column(name = "PROAMTSDR")
	public double getProrationAmountInSdr() {
		return prorationAmountInSdr;
	}

	/**
	 * @param prorationAmountInSdr
	 *            The prorationAmountInSdr to set.
	 */
	public void setProrationAmountInSdr(double prorationAmountInSdr) {
		this.prorationAmountInSdr = prorationAmountInSdr;
	}

	/**
	 * @return Returns the prorationAmountInUsd.
	 */
	@Column(name = "PROAMTUSD")
	public double getProrationAmountInUsd() {
		return prorationAmountInUsd;
	}

	/**
	 * @param prorationAmountInUsd
	 *            The prorationAmountInUsd to set.
	 */
	public void setProrationAmountInUsd(double prorationAmountInUsd) {
		this.prorationAmountInUsd = prorationAmountInUsd;
	}

	/**
	 * @return Returns the prorationDetailsPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "billingBasisNumber", column = @Column(name = "BLGBASNUM")) })
	public MRAProrationDetailsPK getProrationDetailsPk() {
		return prorationDetailsPk;
	}

	/**
	 * @param prorationDetailsPk
	 *            The prorationDetailsPk to set.
	 */
	public void setProrationDetailsPk(MRAProrationDetailsPK prorationDetailsPk) {
		this.prorationDetailsPk = prorationDetailsPk;
	}

	/**
	 * @return Returns the prorationPercentage.
	 */
	@Column(name = "PROPRC")
	public double getProrationPercentage() {
		return prorationPercentage;
	}

	/**
	 * @param prorationPercentage
	 *            The prorationPercentage to set.
	 */
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}

	/**
	 * @return Returns the prorationType.
	 */
	@Column(name = "PROTYP")
	public String getProrationType() {
		return prorationType;
	}

	/**
	 * @param prorationType
	 *            The prorationType to set.
	 */
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}

	/**
	 * @return Returns the sectorFrom.
	 */
	@Column(name = "SECFRM")
	public String getSectorFrom() {
		return sectorFrom;
	}

	/**
	 * @param sectorFrom
	 *            The sectorFrom to set.
	 */
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}

	/**
	 * @return Returns the sectorTo.
	 */
	@Column(name = "SECTOO")
	public String getSectorTo() {
		return sectorTo;
	}

	/**
	 * @param sectorTo
	 *            The sectorTo to set.
	 */
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}

	/**
	 * @return Returns the segmentSequenceNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSequenceNumber() {
		return segmentSequenceNumber;
	}

	/**
	 * @param segmentSequenceNumber
	 *            The segmentSequenceNumber to set.
	 */
	public void setSegmentSequenceNumber(int segmentSequenceNumber) {
		this.segmentSequenceNumber = segmentSequenceNumber;
	}

	/**
	 * @return Returns the weight.
	 */
	@Column(name = "WGT")
	public double getWeight() {
		return weight;
	}

	/**
	 * @param weight
	 *            The weight to set.
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * @return Returns the proratedValue.
	 */
	@Column(name = "PROVAL")
	public double getProratedValue() {
		return proratedValue;
	}

	/**
	 * @param proratedValue
	 *            The proratedValue to set.
	 */
	public void setProratedValue(double proratedValue) {
		this.proratedValue = proratedValue;
	}

	/**
	 * default constructor
	 * 
	 */
	public MRAProrationDetails() {
	}

	/**
	 * This method displays Mail Proration Details
	 * 
	 * @author a-2518
	 * @param prorationFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ProrationDetailsVO> displayProrationDetails(
			ProrationFilterVO prorationFilterVO) throws SystemException {
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "displayProrationDetails");
		MRADefaultsDAO mraDefaultsDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "displayProrationDetails");
			return mraDefaultsDao.displayProrationDetails(prorationFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * @param companyCode
	 * @param dsn
	 * @param despatch
	 * @param gpaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<DespatchLovVO> findDespatchLov(String companyCode,String dsn,
			String despatch,String gpaCode, int pageNumber) throws SystemException{
		Log log = LogFactory.getLogger("MRA DEFAULTS");
		log.entering(CLASS_NAME, "findDespatchLov");
		MRADefaultsDAO mraDefaultsDao = null;
		try{
			EntityManager em = PersistenceController.getEntityManager();
			mraDefaultsDao = MRADefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
			log.exiting(CLASS_NAME, "findDespatchLov");
			return mraDefaultsDao.findDespatchLov(companyCode,dsn,despatch,gpaCode,pageNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
