/*
 * AirlineCN66Details.java Created on Feb15, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

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


import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
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

/**
 * @author A-2407
 */

@Staleable
@Table(name = "MALMRAARLC66DTL")
@Entity
public class AirlineCN66Details {
	private static final String CLASS_NAME = "AirlineCN66Details";
	private static final String WITHDRAWN="D";

	private Log log = LogFactory.getLogger("MRA AIRLINEBILLING");

	private AirlineCN66DetailsPK airlineCN66DetailsPK;

	private String carriageFrom;

	private String carriageTo;

	private String carrierCode;

	private String flightNumber;

	private Calendar flightDate;

	private String origin;

	private String destination;

	private String originExchangeOffice;

	private String destinationExchangeOffice;

	private String mailCategoryCode;

	private String mailSubClass;

	private String despatchSerialNo;

	private String receptacleSerialNo;
	
	//private String hni;
	
	
	//private String regInd;

	private int year;

	private String despatchStatus;

	private int totalPieces;

	private double totalWeight;

	private Calendar despatchDate;

	private int flightCarrierIdentifier;

	private Calendar lastUpdateTime;

	private  String lastUpdatedUser;
	
	private double bldAmt;
	
	private double aplRate;
	
	private String curCod;
	//private String consignmentDocumentNumber;  Commented as part of ICRD-265471
	//private int consignmentSequenceNumber;
	//private String poaCode;
	//private String billingBasis;
	 //Added by A-7929 as part of ICRD-265471
	
	private String contractCurrency;
	private double mailChargeinListingCurrency;
	private double otherChargeinListingCurrency;
	
	

	@Column(name = "CRTCURCOD") 
	public String getContractCurrency() {
		return contractCurrency;
	}

	public void setContractCurrency(String contractCurrency) {
		this.contractCurrency = contractCurrency;
	}

	
	

	
	
	@Column(name = "MALCHGLSTCUR") 
	public double getMailChargeinListingCurrency() {
		return mailChargeinListingCurrency;
	}
	@Column(name = "OTHCHGLSTCUR") 
	public double getOtherChargeinListingCurrency() {
		return otherChargeinListingCurrency;
	}

	public void setOtherChargeinListingCurrency(double otherChargeinListingCurrency) {
		this.otherChargeinListingCurrency = otherChargeinListingCurrency;
	}

	public void setMailChargeinListingCurrency(double mailChargeinListingCurrency) {
		this.mailChargeinListingCurrency = mailChargeinListingCurrency;
	}

	/**
	 * @return Returns the curCod.
	 */
	/**
	 * @return Returns the aplRate.
	 */
	//@Column(name = "CURCOD")
	@Column(name = "LSTCURCOD")    //Modified by A-7929 as part of ICRD-265471
	public String getCurCod() {
		return curCod;
	}

	/**
	 * @param curCod The curCod to set.
	 */
	public void setCurCod(String curCod) {
		this.curCod = curCod;
	}

	/**
	 * @return Returns the aplRate.
	 */
	@Column(name = "APLRAT")
	public double getAplRate() {
		return aplRate;
	}

	/**
	 * @param aplRate The aplRate to set.
	 */
	public void setAplRate(double aplRate) {
		this.aplRate = aplRate;
	}

	/**
	 * @return Returns the totalChg.
	 */
	
	@Column(name = "BLDAMTLSTUR")    //Modified by A-7929 as part of ICRD-265471
	public double getBldAmt() {
		return bldAmt;
	}

	/**
	 * @param totalChg The totalChg to set.
	 */
	public void setBldAmt(double bldAmt) {
		this.bldAmt = bldAmt;
	}
		
	/**
	 * @return Returns the billingBasis.
	 */
	/*@Column(name = "BLGBAS")
	public String getBillingBasis() {
		return billingBasis;
	}

	/**
	 * @param billingBasis The billingBasis to set.
	 */
	
	/*public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}*/

	/**
	 * @return Returns the consignmentDocumentNumber.
	 */
	/*@Column(name = "CSGDOCNUM")
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}*/

	/**
	 * @param consignmentDocumentNumber The consignmentDocumentNumber to set.
	 */
	/*public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}*/

	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	/*@Column(name = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}*/

	/**
	 * @param consignmentSequenceNumber The consignmentSequenceNumber to set.
	 */
	
	/*public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}*/

	
	
	/**
	 * @return Returns the poaCode.
	 */
	/*@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}*/

	/**
	 * @param poaCode The poaCode to set.
	 */
	
	/*public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
      */
	/**
	 * @return Returns the airlineCN66DetailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM")),
			@AttributeOverride(name = "interlineBillingType", column = @Column(name = "INTBLGTYP")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")),
			@AttributeOverride(name = "dsnIdr", column = @Column(name = "DSNIDR")),
	        @AttributeOverride(name = "mailSeqNumber", column = @Column(name = "MALSEQNUM")) })
           
	
	
	public AirlineCN66DetailsPK getAirlineCN66DetailsPK() {
		return airlineCN66DetailsPK;
	}

	/**
	 * @param airlineCN66DetailsPK
	 *            The airlineCN66DetailsPK to set.
	 */
	public void setAirlineCN66DetailsPK(
			AirlineCN66DetailsPK airlineCN66DetailsPK) {
		this.airlineCN66DetailsPK = airlineCN66DetailsPK;
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
	 * @return the lastUpdateTime
	 */
	//@Version
	@Column(name = "LSTUPDTIM")
	//@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime the lastUpdateTime to set
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	@Column(name = "CARCOD")
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the carriageFrom.
	 */
	@Column(name = "CARFRM")
	public String getCarriageFrom() {
		return carriageFrom;
	}

	/**
	 * @param carriageFrom
	 *            The carriageFrom to set.
	 */
	public void setCarriageFrom(String carriageFrom) {
		this.carriageFrom = carriageFrom;
	}

	/**
	 * @return Returns the carriageTo.
	 */
	@Column(name = "CARTOO")
	public String getCarriageTo() {
		return carriageTo;
	}

	/**
	 * @param carriageTo
	 *            The carriageTo to set.
	 */
	public void setCarriageTo(String carriageTo) {
		this.carriageTo = carriageTo;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	@Column(name = "DSNDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getDespatchDate() {
		return despatchDate;
	}

	/**
	 * @param despatchDate
	 *            The despatchDate to set.
	 */
	public void setDespatchDate(Calendar despatchDate) {
		this.despatchDate = despatchDate;
	}

	/**
	 * @return Returns the despatchSerialNo.
	 */
	@Column(name = "DSN")
	public String getDespatchSerialNo() {
		return despatchSerialNo;
	}

	/**
	 * @param despatchSerialNo
	 *            The despatchSerNo to set.
	 */
	public void setDespatchSerialNo(String despatchSerialNo) {
		this.despatchSerialNo = despatchSerialNo;
	}

	/**
	 * @return the hni
	 */
	/*@Column(name = "HSN")
	public String getHni() {
		return hni;
	}*/
	/**
	 * @param hni the hni to set
	 */
	/*public void setHni(String hni) {
		this.hni = hni;
	}*/
	/**
	 * @return the regInd
	 */
	/*@Column(name = "REGIND")
	public String getRegInd() {
		return regInd;
	}*/
	/**
	 * @param regInd the regInd to set
	 */
	/*public void setRegInd(String regInd) {
		this.regInd = regInd;
	}*/
	/**
	 * @return Returns the despatchStatus.
	 */
	//@Column(name = "DSNSTA")
	@Column(name = "MALSTA")   // //Modified by A-7929 as part of ICRD-265471
	public String getDespatchStatus() {
		return despatchStatus;
	}

	/**
	 * @param despatchStatus
	 *            The despatchStatus to set.
	 */
	public void setDespatchStatus(String despatchStatus) {
		this.despatchStatus = despatchStatus;
	}

	/**
	 * @return Returns the destination.
	 */
	@Column(name = "DSTCOD")
	public String getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 *            The destination to set.
	 */
	public void setDestination(String destination) {
		this.destination = destination;
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
	 * @return Returns the mailSubClass.
	 */
	@Column(name = "SUBCLSGRP")
	public String getMailSubClass() {
		return mailSubClass;
	}

	/**
	 * @param mailSubClass
	 *            The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}

	/**
	 * @return Returns the origin.
	 */
	@Column(name = "ORGCOD")
	public String getOrigin() {
		return origin;
	}

	/**
	 * @param origin
	 *            The origin to set.
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}

	/**
	 * @return Returns the originExchangeOffice.
	 */
	@Column(name = "ORGEXGOFC")
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 *
	 * @param originExchangeOffice
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return Returns the receptacleSerialNo.
	 */
	@Column(name = "RSN")
	public String getReceptacleSerialNo() {
		return receptacleSerialNo;
	}

	/**
	 *
	 * @param receptacleSerialNo
	 */
	public void setReceptacleSerialNo(String receptacleSerialNo) {
		this.receptacleSerialNo = receptacleSerialNo;
	}

	/**
	 * @return Returns the totalPieces.
	 */
	@Column(name = "TOTPCS")
	public int getTotalPieces() {
		return totalPieces;
	}

	/**
	 * @param totalPieces
	 *            The totalPieces to set.
	 */
	public void setTotalPieces(int totalPieces) {
		this.totalPieces = totalPieces;
	}

	/**
	 * @return Returns the totalWeight.
	 */
	@Audit(name="totalWeight")
	@Column(name = "TOTWGT")
	public double getTotalWeight() {
		return totalWeight;
	}

	/**
	 * @param totalWeight
	 *            The totalWeight to set.
	 */
	public void setTotalWeight(double totalWeight) {
		this.totalWeight = totalWeight;
	}

	/**
	 * @return Returns the year.
	 */
	@Column(name = "YER")
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
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
	 * default constructor
	 */
	public AirlineCN66Details() {
	}

	/**
	 *
	 * @param airlineCn66DetailsVo
	 * @throws SystemException
	 */
	public AirlineCN66Details(AirlineCN66DetailsVO airlineCn66DetailsVo)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		AirlineCN66DetailsPK airlineCn66DetailsPk = new AirlineCN66DetailsPK(
				airlineCn66DetailsVo.getCompanyCode(), airlineCn66DetailsVo
						.getAirlineIdentifier(), airlineCn66DetailsVo
						.getInvoiceNumber(), airlineCn66DetailsVo
						.getInterlineBillingType(), airlineCn66DetailsVo
						.getSequenceNumber(), airlineCn66DetailsVo
						.getClearancePeriod(),
						airlineCn66DetailsVo.getDsnIdr(),
						airlineCn66DetailsVo.getMalSeqNum()
						);
		setAirlineCN66DetailsPK(airlineCn66DetailsPk);
		populateAttributes(airlineCn66DetailsVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, CLASS_NAME);
	}

	/**
	 *
	 * @param airlineCn66DetailsVo
	 */
	public void populateAttributes(AirlineCN66DetailsVO airlineCn66DetailsVo) {
		log.entering(CLASS_NAME, "populateAttributes");
		log.log(Log.INFO, "vo in populateattributes ", airlineCn66DetailsVo);
		setCarrierCode(airlineCn66DetailsVo.getCarrierCode());
		setCarriageFrom(airlineCn66DetailsVo.getCarriageFrom());
		setCarriageTo(airlineCn66DetailsVo.getCarriageTo());
		setDespatchDate(airlineCn66DetailsVo.getDespatchDate());
		setDespatchSerialNo(airlineCn66DetailsVo.getDespatchSerialNo());
		setDespatchStatus(airlineCn66DetailsVo.getDespatchStatus());
		setDestination(airlineCn66DetailsVo.getDestination());
		setDestinationExchangeOffice(airlineCn66DetailsVo
				.getDestinationExchangeOffice());
		setFlightDate(airlineCn66DetailsVo.getFlightDate());
		setFlightNumber(airlineCn66DetailsVo.getFlightNumber());
		setMailCategoryCode(airlineCn66DetailsVo.getMailCategoryCode());
		setMailSubClass(airlineCn66DetailsVo.getMailSubClass());
		setOrigin(airlineCn66DetailsVo.getOrigin());
		setOriginExchangeOffice(airlineCn66DetailsVo.getOriginExchangeOffice());
		setReceptacleSerialNo(airlineCn66DetailsVo.getReceptacleSerialNo());
		//setHni(airlineCn66DetailsVo.getHni());
		//setRegInd(airlineCn66DetailsVo.getRegInd());
		setTotalPieces(airlineCn66DetailsVo.getTotalPieces());
		setTotalWeight(airlineCn66DetailsVo.getTotalWeight());
		setYear(airlineCn66DetailsVo.getYear());
		if(airlineCn66DetailsVo.getAmount()!=null){
		setBldAmt(airlineCn66DetailsVo.getAmount().getRoundedAmount());
		setMailChargeinListingCurrency(airlineCn66DetailsVo.getAmount().getRoundedAmount());
		setOtherChargeinListingCurrency(airlineCn66DetailsVo.getAmount().getRoundedAmount());
		}
		
		setAplRate(airlineCn66DetailsVo.getRate());
		setFlightCarrierIdentifier(airlineCn66DetailsVo
				.getFlightCarrierIdentifier());
		if(airlineCn66DetailsVo.getLastUpdatedTime()!=null){
			setLastUpdateTime(airlineCn66DetailsVo.getLastUpdatedTime());
		}
		setLastUpdatedUser(airlineCn66DetailsVo.getLastUpdatedUser());
		
		//setConsignmentDocumentNumber(airlineCn66DetailsVo.getCsgdocnum());
		//setConsignmentSequenceNumber(airlineCn66DetailsVo.getCsgseqnum());
		//setPoaCode(airlineCn66DetailsVo.getPoaCode());
		//setBillingBasis(airlineCn66DetailsVo.getBillingBasis());
		setCurCod(airlineCn66DetailsVo.getCurCod());
		log.log(Log.INFO, "vo in populateattributes end.. ",
				airlineCn66DetailsVo);
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 *
	 * @param airlineCn66DetailsVo
	 * @throws SystemException
	 */
	public void update(AirlineCN66DetailsVO airlineCn66DetailsVo)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(airlineCn66DetailsVo);
		log.exiting(CLASS_NAME, "update");
	}

	/**
	 *
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering(CLASS_NAME, "remove");
		try {
			PersistenceController.getEntityManager().remove(this);
			log.exiting(CLASS_NAME, "remove");
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}

	/**
	 *
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param invoiceNumber
	 * @param interlineBillingType
	 * @param sequenceNumber
	 * @param clearancePeriod
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static AirlineCN66Details find(String companyCode,
			int airlineIdentifier, String invoiceNumber,
			String interlineBillingType, int sequenceNumber,
			String clearancePeriod,String dsnIdr,long malSeqNum) throws SystemException, FinderException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "find");
		AirlineCN66DetailsPK airlineCn66DetailsPkToFind = new AirlineCN66DetailsPK(
				companyCode, airlineIdentifier, invoiceNumber,
				interlineBillingType, sequenceNumber, clearancePeriod,dsnIdr,malSeqNum);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				AirlineCN66Details.class, airlineCn66DetailsPkToFind);
	}

	/**
	 * Method to list CN66 details
	 * @param cn66FilterVo
	 * @return
	 * @throws SystemException
	 */
	public static Page<AirlineCN66DetailsVO> findCN66Details(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "findCN66Details");
		MRAAirlineBillingDAO mraAirlineBillingDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraAirlineBillingDao = MRAAirlineBillingDAO.class
					.cast(em
							.getQueryDAO(MRAAirlineBillingPersistenceConstants.MODULE_NAME));
			log.exiting(CLASS_NAME, "findCN66Details");
			return mraAirlineBillingDao.findCN66Details(cn66FilterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**@author A-3434
	 * Method to list CN66 details
	 * @param cn66FilterVo
	 * @return
	 * @throws SystemException
	 */
	public static Collection<AirlineCN66DetailsVO> findCN66DetailsVOCollection(
			AirlineCN66DetailsFilterVO cn66FilterVo) throws SystemException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "findCN66Details");
		MRAAirlineBillingDAO mraAirlineBillingDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraAirlineBillingDao = MRAAirlineBillingDAO.class
					.cast(em
							.getQueryDAO(MRAAirlineBillingPersistenceConstants.MODULE_NAME));
			log.exiting(CLASS_NAME, "findCN66Details");
			return mraAirlineBillingDao.findCN66DetailsVOCollection(cn66FilterVo);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-2408
	 * @param cn66FilterVo
	 * @throws SystemException
	 */
	public static String processMail(AirlineCN66DetailsFilterVO cn66FilterVo)
	throws SystemException{
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "processMail");
		MRAAirlineBillingDAO mraAirlineBillingDao = null;
		try{
			EntityManager em = PersistenceController.getEntityManager();
			mraAirlineBillingDao = MRAAirlineBillingDAO.class.cast(em.getQueryDAO(MRAAirlineBillingPersistenceConstants.MODULE_NAME));
			log.exiting(CLASS_NAME, "processMail");
			return mraAirlineBillingDao.processMail(cn66FilterVo);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
    /**
    * 
	 * 	Method		:	withdrawMailBags
	 *	Added by 	:	A-8061 
	 * 	Used for 	:
	 *	Parameters	:	@return 
	 *	Return type	: 	void
	 * @param airlineCN66DetailsVOs 
	 * @throws BusinessDelegateException 
	 */
	public static void withdrawMailBags(Collection<AirlineCN66DetailsVO> airlineCN66DetailsVOs)
	throws SystemException{
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "processMail");
		MRAAirlineBillingDAO mraAirlineBillingDao = null;
		try{
			EntityManager em = PersistenceController.getEntityManager();
			mraAirlineBillingDao = MRAAirlineBillingDAO.class.cast(em.getQueryDAO(MRAAirlineBillingPersistenceConstants.MODULE_NAME));
			log.exiting(CLASS_NAME, "processMail");
			for(AirlineCN66DetailsVO airlineCN66DetailsVO : airlineCN66DetailsVOs){
				if(!WITHDRAWN.equals(airlineCN66DetailsVO.getDespatchStatus())){
				 mraAirlineBillingDao.withdrawMailBag(airlineCN66DetailsVO);
				}
				
			}
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static Collection<AirlineCN66DetailsVO> findCN66DetailsVOsForStatusChange(
			AirlineCN66DetailsFilterVO airlineCN66DetailsFilterVO, long mailSequenceNumber) throws SystemException {
		Log log = LogFactory.getLogger("MRA AIRLINEBILLING");
		log.entering(CLASS_NAME, "findCN66DetailsVOsForStatusChange");
		MRAAirlineBillingDAO mraAirlineBillingDao = null;
		try {
			EntityManager em = PersistenceController.getEntityManager();
			mraAirlineBillingDao = MRAAirlineBillingDAO.class
					.cast(em
							.getQueryDAO(MRAAirlineBillingPersistenceConstants.MODULE_NAME));
			log.exiting(CLASS_NAME, "findCN66DetailsVOsForStatusChange");
			return mraAirlineBillingDao.findCN66DetailsVOsForStatusChange(airlineCN66DetailsFilterVO,mailSequenceNumber);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	
	
}
