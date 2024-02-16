/*
 * AirlineCN51Summary.java Created on Feb 15, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.airlinebilling;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51FilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineCN66DetailsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceLovVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineInvoiceReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.ArlInvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
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
@Table(name = "MALMRAARLC51SMY")
@Entity
public class AirlineCN51Summary {

	/**
	 * class name string
	 */
	public static final String CLASSNAME = "AirlineCN51Summary";

	/**
	 * module name
	 */
	public static final String MODULE_NAME = "mail.mra.airlinebilling";

	/**
	 * denotes default string value
	 */
	public static final String DEFAULT_BLANK_VALUE = "";

	/**
	 * denote default double value
	 */
	public static final double DEFAULT_NUMBER_VALUE = 0.0D;

	private Log log = LogFactory.getLogger("MAilTRACKING:MRA:AIRLINEBILLING");

	

	/**
	 * method for getting log variable from a static reference within the
	 * class..
	 * 
	 * @return Log
	 */
	public static Log staticLogger() {
		return LogFactory.getLogger("MailTracking:Mra");
	}

	private AirlineCN51SummaryPK airlineCN51SummaryPK;

	private String airlinecode;

	private String cn51status;

	private String billingcurrencycode;

	private String contractCurrencycode;

	private String invSrc;

	private double totalAmountInBillingCurrency;

	private double totalAmountInContractCurrency;

	private Calendar billeddate;

	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;

	private Set<AirlineCN51Details> airlineCN51Details;

	// added for capture invoice 
	/**
	 * @author a-3456
	 */
	private String listingCurrency;

	private double netAmount;

	private double exchangeRate;

	private double amountInusd;

	private Calendar invRcvdate;

	private double totalWt;

	private String invStatus;

	private String invFormstatus;
	
	private double c51Amount;
	
	//Added By Deepthi as a part of BUG35399
	private Collection<AirlineCN66Details> airlineCN66Details;

	//Added by A-7929 for ICRD-245605
		private String interfacedFileName;
		private Calendar interfacedTime;
	//Added by A-7929 as part of ICRD-265471
		private String billingType;
		
	

	/*------------------------------------------------------------------------------------------------------------*/
	//@Column(name = "C51AMT")     
	@Column(name = "C51AMTLSTCUR")                          //Modified by A-7929 as part of ICRD-265471
	/**
	 * @return Returns the c51Amount.
	 */
	public double getC51Amount() {
		return c51Amount;
	}

	/**
	 * @param amount The c51Amount to set.
	 */
	public void setC51Amount(double amount) {
		c51Amount = amount;
	}
	// added by A-3456 for captureinvoice
	//@Column(name = "LSTCUR")
	@Column(name = "LSTCURCOD")      //Modified by A-7929 as part of ICRD-265471
	public String getListingCurrency() {
		return listingCurrency;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setListingCurrency(String listingCurrency) {
		this.listingCurrency = listingCurrency;
	}

	//@Column(name = "NETAMT")  
	@Column(name = "NETAMTLSTCUR")     //Modified by A-7929 as part of ICRD-265471
	public double getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount
	 *            The netAmount to set.
	 */
	public void setNetAmount(double netAmount) {
		this.netAmount = netAmount;
	}

	//@Column(name = "EXGRAT")       
	@Column(name = "EXGRATCTRLST")    //Modified by A-7929 as part of ICRD-265471
	public double getExchangeRate() {
		return exchangeRate;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setExchangeRate(double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	//@Column(name = "AMTINUSD")
	@Column(name = "NETAMTUSD")         //Modified by A-7929 as part of ICRD-265471
	public double getAmountInusd() {
		return amountInusd;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setAmountInusd(double amountInusd) {
		this.amountInusd = amountInusd;
	}

	@Column(name = "TOTWGT")
	public double getTotalWt() {
		return totalWt;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setTotalWt(double totalWt) {
		this.totalWt = totalWt;
	}

	@Column(name = "INVSTA")
	public String getInvStatus() {
		return invStatus;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	@Column(name = "INVFRMONESTA")
	public String getInvFormstatus() {
		return invFormstatus;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setInvFormstatus(String invFormstatus) {
		this.invFormstatus = invFormstatus;
	}

	/**
	 * @return the invRcvdate
	 */
	@Column(name = "INVRCPTDAT")
	public Calendar getInvRcvdate() {
		return invRcvdate;
	}

	/**
	 * @param invRcvdate
	 *            the invRcvdate to set
	 */
	public void setInvRcvdate(Calendar invRcvdate) {
		this.invRcvdate = invRcvdate;
	}
	@Column(name = "INTINVTYP")
	public String getBillingType() {
			return billingType;
		}

		public void setBillingType(String billingType) {
			this.billingType = billingType;
	}
	
	/**
	 * @return the airlineCN66Details
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "INTBLGTYP", referencedColumnName = "INTBLGTYP", insertable = false, updatable = false),
			@JoinColumn(name = "INVNUM", referencedColumnName = "INVNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR", insertable = false, updatable = false),
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CLRPRD", referencedColumnName = "CLRPRD", insertable = false, updatable = false) })
	public Collection<AirlineCN66Details> getAirlineCN66Details() {
		return airlineCN66Details;
	}

	/**
	 * @param airlineCN66Details the airlineCN66Details to set
	 */
	public void setAirlineCN66Details(
			Collection<AirlineCN66Details> airlineCN66Details) {
		this.airlineCN66Details = airlineCN66Details;
	}
	/**
	 * @return Returns the airlineCN51Details.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "INTBLGTYP", referencedColumnName = "INTBLGTYP", insertable = false, updatable = false),
			@JoinColumn(name = "INVNUM", referencedColumnName = "INVNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARLIDR", referencedColumnName = "ARLIDR", insertable = false, updatable = false),
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CLRPRD", referencedColumnName = "CLRPRD", insertable = false, updatable = false) })
	public Set<AirlineCN51Details> getAirlineCN51Details() {
		return airlineCN51Details;
	}

	/**
	 * @param airlineCN51Details
	 *            The airlineCN51Details to set.
	 */
	public void setAirlineCN51Details(Set<AirlineCN51Details> airlineCN51Details) {
		this.airlineCN51Details = airlineCN51Details;
	}

	/**
	 * @return Returns the airlineCN51SummaryPK.
	 */

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "interlinebillingtype", column = @Column(name = "INTBLGTYP")),
			@AttributeOverride(name = "invoicenumber", column = @Column(name = "INVNUM")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD")) })
	public AirlineCN51SummaryPK getAirlineCN51SummaryPK() {
		return airlineCN51SummaryPK;
	}

	/**
	 * @param airlineCN51SummaryPK
	 *            The airlineCN51SummaryPK to set.
	 */
	public void setAirlineCN51SummaryPK(
			AirlineCN51SummaryPK airlineCN51SummaryPK) {
		this.airlineCN51SummaryPK = airlineCN51SummaryPK;
	}

	/**
	 * @return Returns the airlinecode.
	 */
	@Column(name = "ARLCOD")
	public String getAirlinecode() {
		return airlinecode;
	}

	/**
	 * @param airlinecode
	 *            The airlinecode to set.
	 */
	public void setAirlinecode(String airlinecode) {
		this.airlinecode = airlinecode;
	}

	/**
	 * @return Returns the billeddate.
	 */
	@Column(name = "BLDDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getBilleddate() {
		return billeddate;
	}

	/**
	 * @param billeddate
	 *            The billeddate to set.
	 */
	public void setBilleddate(Calendar billeddate) {
		this.billeddate = billeddate;
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
	 * @return Returns the cn51status.
	 */
	@Column(name = "C51STA")
	public String getCn51status() {
		return cn51status;
	}

	/**
	 * @param cn51status
	 *            The cn51status to set.
	 */
	public void setCn51status(String cn51status) {
		this.cn51status = cn51status;
	}

	/**
	 * @return Returns the contractCurrencycode.
	 */
	@Column(name = "CRTCURCOD")
	public String getContractCurrencycode() {
		return contractCurrencycode;
	}

	/**
	 * @param contractCurrencycode
	 *            The contractCurrencycode to set.
	 */
	public void setContractCurrencycode(String contractCurrencycode) {
		this.contractCurrencycode = contractCurrencycode;
	}

	/**
	 * @return Returns the totalAmountInBillingCurrency.
	 */
	//@Column(name = "TOTAMTBLGCUR")
	@Column(name = "TOTAMTLSTCUR")    //Modified by A-7929 as part of ICRD-265471
	public double getTotalAmountInBillingCurrency() {
		return totalAmountInBillingCurrency;
	}

	/**
	 * @param totalAmountInBillingCurrency
	 *            The totalAmountInBillingCurrency to set.
	 */
	public void setTotalAmountInBillingCurrency(
			double totalAmountInBillingCurrency) {
		this.totalAmountInBillingCurrency = totalAmountInBillingCurrency;
	}

	/**
	 * @return Returns the totalAmountInContractCurrency.
	 */
	@Audit(name = "totalAmtContractCurrency")
	@Column(name = "TOTAMTCRTCUR")
	public double getTotalAmountInContractCurrency() {
		return totalAmountInContractCurrency;
	}

	/**
	 * @param totalAmountInContractCurrency
	 *            The totalAmountInContractCurrency to set.
	 */
	public void setTotalAmountInContractCurrency(
			double totalAmountInContractCurrency) {
		this.totalAmountInContractCurrency = totalAmountInContractCurrency;
	}

	/**
	 * @return the lastUpdatedTime
	 */
	// @Version
	@Column(name = "LSTUPDTIM")
	// @Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            the lastUpdatedUser to set
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the invSrc.
	 */
	@Column(name = "INVSRC")
	public String getInvSrc() {
		return invSrc;
	}

	/**
	 * @param invSrc
	 *            The invSrc to set.
	 */
	public void setInvSrc(String invSrc) {
		this.invSrc = invSrc;
	}

	/**
	 * @return the interfacedFileName
	 */
	@Column(name = "INTFCDFILNAM")
	public String getInterfacedFileName() {
		return interfacedFileName;
	}
	/**
	 * @param interfacedFileName the interfacedFileName to set
	 */
	
	public void setInterfacedFileName(String interfacedFileName) {
		this.interfacedFileName = interfacedFileName;
	}
	/**
	 * @return the interfacedTime
	 */
	@Column(name = "INTFCDTIM")
	public Calendar getInterfacedTime() {
		return interfacedTime;
	}
	/**
	 * @param interfacedTime the interfacedTime to set
	 */
	public void setInterfacedTime(Calendar interfacedTime) {
		this.interfacedTime = interfacedTime;
	}

	/**
	 * 
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 */
	public static AirlineCN51SummaryVO findCN51Details(
			AirlineCN51FilterVO cn51FilterVO) throws SystemException {
		staticLogger().entering("AirlineCN51Summary", "findCN51Details");
		AirlineCN51SummaryVO vOFromDatabase = null;
		try {
			vOFromDatabase = constructMRAAirlineBillingDAO().findCN51Details(
					cn51FilterVO);
		} catch (PersistenceException persistenceException) {
			staticLogger().log(Log.INFO, " ####### PersistenceException ");
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
		staticLogger().exiting("AirlineCN51Summary", "findCN51Details");
		return vOFromDatabase;
	}
	/**
	 * 
	 * @param cn51FilterVO
	 * @return AirlineCN51SummaryVO
	 * @throws SystemException
	 */
	public static AirlineCN51SummaryVO findCN51DetailColection(
			AirlineCN51FilterVO cn51FilterVO) throws SystemException {
		staticLogger().entering("AirlineCN51Summary", "findCN51Details");
		AirlineCN51SummaryVO vOFromDatabase = null;
		try {
			vOFromDatabase = constructMRAAirlineBillingDAO().findCN51DetailColection(
					cn51FilterVO);
		} catch (PersistenceException persistenceException) {
			staticLogger().log(Log.INFO, " ####### PersistenceException ");
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
		staticLogger().exiting("AirlineCN51Summary", "findCN51Details");
		return vOFromDatabase;
	}
	/**
	 * Method for getting a query dao class corresponding to the bubproduct ..
	 * 
	 * @return MRAAirlineBillingDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static MRAAirlineBillingDAO constructMRAAirlineBillingDAO()
			throws SystemException, PersistenceException {
		return (MRAAirlineBillingDAO) PersistenceController.getEntityManager()
				.getQueryDAO(MODULE_NAME);
	}

	/**
	 * 
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param interlineBillingType
	 * @param invoiceNumber
	 * @param clearancePeriod
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static AirlineCN51Summary find(String companyCode,
			int airlineIdentifier, String interlineBillingType,
			String invoiceNumber, String clearancePeriod)
			throws SystemException, FinderException {
		AirlineCN51SummaryPK pk = new AirlineCN51SummaryPK();
		pk.setCompanyCode(companyCode);
		pk.setAirlineIdentifier(airlineIdentifier);
		pk.setInterlinebillingtype(interlineBillingType);
		pk.setInvoicenumber(invoiceNumber);
		pk.setClearancePeriod(clearancePeriod);
		return PersistenceController.getEntityManager().find(
				AirlineCN51Summary.class, pk);
	}

	/**
	 * 
	 * @param invoiceLovFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<AirlineInvoiceLovVO> displayInvoiceLOV(
			InvoiceLovFilterVO invoiceLovFilterVO) throws SystemException {
		try {
			MRAAirlineBillingDAO mraAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.airlinebilling"));
			return mraAirlineBillingDAO.displayInvoiceLOV(invoiceLovFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * default constructor
	 * 
	 */
	public AirlineCN51Summary() {

	}

	/**
	 * constructor for normal use
	 * 
	 * @param cn51SummaryVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public AirlineCN51Summary(AirlineCN51SummaryVO cn51SummaryVO)
			throws SystemException, CreateException {
		log.entering(CLASSNAME, " a new AirlineCN51Summary ");
		this.populateAirlineCN51SummaryPK(cn51SummaryVO);
		this.populateAllAttributes(cn51SummaryVO);
		PersistenceController.getEntityManager().persist(this);
		log.exiting(CLASSNAME, " a new AirlineCN51Summary ");
	}

	/**
	 * 
	 * @param cn51SummaryVO
	 */
	private void populateAirlineCN51SummaryPK(AirlineCN51SummaryVO cn51SummaryVO) {
		log.entering(CLASSNAME, "populateAirlineCN51SummaryPK");
		AirlineCN51SummaryPK createdPK = new AirlineCN51SummaryPK(cn51SummaryVO
				.getCompanycode(), cn51SummaryVO.getAirlineidr(), cn51SummaryVO
				.getInterlinebillingtype(), cn51SummaryVO.getInvoicenumber(),
				cn51SummaryVO.getClearanceperiod());
		this.setAirlineCN51SummaryPK(createdPK);
		log.exiting(CLASSNAME, "populateAirlineCN51SummaryPK");
	}

	/**
	 * 
	 * @param cn51SummaryVO
	 * @throws SystemException
	 */
	private void populateAllAttributes(AirlineCN51SummaryVO cn51SummaryVO)
			throws SystemException {
		log.entering(CLASSNAME, "populateAllAttributes");
		log.log(Log.INFO, "#populateAllAttributes", cn51SummaryVO);
		this.setAirlinecode(cn51SummaryVO.getAirlinecode());
		if (cn51SummaryVO.getBilleddate() != null) {
			this.setBilleddate(cn51SummaryVO.getBilleddate().toCalendar());
		}
	
		this.setInvSrc(cn51SummaryVO.getInvSrc());
		
		/**
		 * @author a-3447 starts 
		 */
		this.setListingCurrency(cn51SummaryVO.getListingCurrency());
		if(cn51SummaryVO.getNetAmount()!=null){
		this.setNetAmount(cn51SummaryVO.getNetAmount().getAmount());
		}
		if(cn51SummaryVO.getExchangeRate()!=null){
		this.setExchangeRate(cn51SummaryVO.getExchangeRate().getAmount());
		}
		if(cn51SummaryVO.getAmountInusd()!=null){
		this.setAmountInusd(cn51SummaryVO.getAmountInusd().getAmount());
		}
		if (cn51SummaryVO.getInvRcvdate()!=null){
		this.setInvRcvdate(cn51SummaryVO.getInvRcvdate());
		}
		if(cn51SummaryVO.getC51Amount()>0){
		this.setC51Amount(cn51SummaryVO.getC51Amount());
		}
		this.setTotalWt(cn51SummaryVO.getTotWt());
		
		if(cn51SummaryVO.getInvStatus()!=null){
		this.setInvStatus(cn51SummaryVO.getInvStatus());
		}
		if(cn51SummaryVO.getInvFormstatus()!=null){
		this.setInvFormstatus(cn51SummaryVO.getInvFormstatus());
		}
		/**
		 * @author a-3447 ends 
		 */

		this.setBillingcurrencycode(DEFAULT_BLANK_VALUE);
		
		if("I".equals(cn51SummaryVO.getInterlinebillingtype())){
		if(cn51SummaryVO.getListingCurrency()!=null){
			
		this.setContractCurrencycode(cn51SummaryVO.getListingCurrency());	
		
			}	
		}else{
		this.setContractCurrencycode(DEFAULT_BLANK_VALUE);
		}
			
		this.setCn51status(DEFAULT_BLANK_VALUE);
		this.setTotalAmountInBillingCurrency(DEFAULT_NUMBER_VALUE);
		/**
		 * optimistic locking
		 */
		if(cn51SummaryVO.getLastUpdatedTime()!=null){
		this.setLastUpdatedTime(cn51SummaryVO.getLastUpdatedTime());
		}
		if(cn51SummaryVO.getLastUpdatedUser()!=null){
		this.setLastUpdatedUser(cn51SummaryVO.getLastUpdatedUser());
		}
		double totAmrForCN51Details = 0.0;
		if (cn51SummaryVO.getCn51DetailsVOs() != null
				&& cn51SummaryVO.getCn51DetailsVOs().size() > 0) {
			log.log(Log.INFO, "##### child level updation starts ");
			populateAirlineCN51ChildDetails(cn51SummaryVO.getCn51DetailsVOs());
			totAmrForCN51Details = calculateC51DtlsTotalAmtInCrtCurrency();
			log.log(Log.INFO, "##### child level updation over ");

			double totalAmtInCrtCurrency = totAmrForCN51Details
					+ cn51SummaryVO.getDifferenceAmount();
			this.setTotalAmountInContractCurrency(totalAmtInCrtCurrency);

		} else {
			// enters only if update is done from saveRejection memo screen
			/**
			 * @author a-3447 added for AirNZ 24898
			 */
			if("I".equals(cn51SummaryVO.getInterlinebillingtype())){
				if(cn51SummaryVO.getNetAmount()!=null)	{					
				this.setTotalAmountInContractCurrency(cn51SummaryVO.getNetAmount().getAmount());
				}
			}else{
			this.setTotalAmountInContractCurrency(cn51SummaryVO
					.getTotalAmountInContractCurrency());
			}
		}
        //Added by A-7929 as part of ICRD-265471 
		if(cn51SummaryVO.getBillingType()!=null){
			this.setBillingType(cn51SummaryVO.getBillingType());
			}
		log.exiting(CLASSNAME, "populateAllAttributes");
	}

	/**
	 * 
	 * @return
	 */
	public double calculateC51DtlsTotalAmtInCrtCurrency() {
		log.entering(CLASSNAME, "findTotalC51DtlsAmountInContractCurrency");
		double totalAmtInCrtCurr = 0.0;
		log.log(Log.INFO, " size of child ", this.getAirlineCN51Details().size());
		for (AirlineCN51Details child_detail_Itr : this.getAirlineCN51Details()) {
			totalAmtInCrtCurr = totalAmtInCrtCurr
					+ child_detail_Itr.getTotalamountincontractcurrency();
		}
		log.exiting(CLASSNAME, "findTotalC51DtlsAmountInContractCurrency");
		return totalAmtInCrtCurr;
	}

	/**
	 * 
	 * @param airlineCN51DetailVOs
	 * @throws SystemException
	 */
	public void populateAirlineCN51ChildDetails(
			Collection<AirlineCN51DetailsVO> airlineCN51DetailVOs)
			throws SystemException {
		log.entering(CLASSNAME, "populateAirlineCN51ChildDetails");
		if(airlineCN51DetailVOs != null && airlineCN51DetailVOs.size() > 0){
			try {

				for (AirlineCN51DetailsVO airlineCN51DetailVO : airlineCN51DetailVOs) {

					AirlineCN51Details cN51Detail = null;
					String operationFlag = airlineCN51DetailVO.getOperationFlag();

					if (operationFlag != null
							&& OPERATION_FLAG_DELETE.equals(operationFlag)) {
						AirlineCN51Details entityForRemoval = AirlineCN51Details
						.find(airlineCN51DetailVO.getCompanycode(),
								airlineCN51DetailVO.getAirlineidr(),
								airlineCN51DetailVO
								.getInterlinebillingtype(),
								airlineCN51DetailVO.getInvoicenumber(),
								airlineCN51DetailVO.getClearanceperiod(),
								airlineCN51DetailVO.getSequenceNumber());
						entityForRemoval.remove();

					}

					else if (operationFlag != null
							&& OPERATION_FLAG_UPDATE.equals(operationFlag)) {
						AirlineCN51Details entityForUpdation = AirlineCN51Details
						.find(airlineCN51DetailVO.getCompanycode(),
								airlineCN51DetailVO.getAirlineidr(),
								airlineCN51DetailVO
								.getInterlinebillingtype(),
								airlineCN51DetailVO.getInvoicenumber(),
								airlineCN51DetailVO.getClearanceperiod(),
								airlineCN51DetailVO.getSequenceNumber());
						entityForUpdation.update(airlineCN51DetailVO);

					}

					else if (operationFlag != null
							&& OPERATION_FLAG_INSERT.equals(operationFlag)) {
						log.log(Log.INFO, " <-- insert child ---> ");
						log.log(Log.INFO, " the vo for insertion ",
								airlineCN51DetailVO);
						if("ULD".equals(airlineCN51DetailVO.getMailsubclass())){
							airlineCN51DetailVO.setMailsubclass("UL");
						}
						else if("SAL".equals(airlineCN51DetailVO.getMailsubclass())){
							airlineCN51DetailVO.setMailsubclass("SL");
						}
						cN51Detail = new AirlineCN51Details(airlineCN51DetailVO);
						if (this.getAirlineCN51Details() == null
								|| this.getAirlineCN51Details().size() <= 0) {
							log.log(Log.INFO, " -- adding for first time ---- ");
							this
							.setAirlineCN51Details(new HashSet<AirlineCN51Details>());
						}
						this.getAirlineCN51Details().add(cN51Detail);
					}

				}// end of for
			} catch (RemoveException removeException) {
				log.log(Log.SEVERE, "##### RemoveException Occured ");
				log
				.log(Log.SEVERE,
						"##### populateAirlineCN51ChildDetails failed ");
				throw new SystemException(removeException.getMessage(),
						removeException);
			} catch (FinderException finderException) {
				log.log(Log.SEVERE, "##### FinderException Occured ");
				log
				.log(Log.SEVERE,
						"##### populateAirlineCN51ChildDetails failed ");
				throw new SystemException(finderException.getMessage(),
						finderException);
			} catch (CreateException createException) {
				log.log(Log.SEVERE, "##### CreateException Occured ");
				log
				.log(Log.SEVERE,
						"##### populateAirlineCN51ChildDetails failed ");
				throw new SystemException(createException.getMessage(),
						createException);
			}
		}

		log.exiting(CLASSNAME, "populateAirlineCN51ChildDetails");
	}

	/**
	 * 
	 * @param airlineCN51SummaryVO
	 * @throws SystemException
	 */
	public void update(AirlineCN51SummaryVO airlineCN51SummaryVO)
			throws SystemException {
		staticLogger().entering(CLASSNAME, "update");
		populateAllAttributes(airlineCN51SummaryVO);
		staticLogger().exiting(CLASSNAME, "update");
	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void removeAllDetails() throws SystemException {
		log.entering(CLASSNAME, "removeAllDetails");
		log.log(Log.INFO, " before deleting all details the size was ", this.getAirlineCN51Details().size());
		Collection<AirlineCN51Details> tmpCn51Details = new ArrayList<AirlineCN51Details>();
		try {
			for (AirlineCN51Details detail_For_Removal : this
					.getAirlineCN51Details()) {
				tmpCn51Details.add(detail_For_Removal);
				// code for removing the entity from database
				detail_For_Removal.remove();
			}
		} catch (RemoveException removeException) {
			log.log(Log.SEVERE, " ###### remove failed ");
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}

		// code for removing all the elements from the Set<AirlineCN51Details>
		this.getAirlineCN51Details().removeAll(tmpCn51Details);

		log.log(Log.INFO, " -- after all removal -- ");
		if (this.getAirlineCN51Details().size() == 0) {
			log.log(Log.INFO, " removeAllDetails success ");
		}

		log.exiting(CLASSNAME, "removeAllDetails");
	}

	/**
	 * method to remove
	 * 
	 * @return
	 * @param memoInInvoiceVO
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering(CLASSNAME, "remove");
		try {
			// removing child
			removeAllDetails();
			// removing parent
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			log.log(Log.SEVERE, " ###### remove failed ");
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting(CLASSNAME, "remove");
	}

	/**
	 * @author a-2270
	 * @param airlineCN51FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<AirlineInvoiceReportVO> findInvoiceDetailsForReport(
			AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException {
		try {
			return constructMRAAirlineBillingDAO().findInvoiceDetailsForReport(
					airlineCN51FilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
	}
	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<AirlineCN51SummaryVO> generateInvoiceReports(
			AirlineCN51FilterVO filterVO) throws SystemException {
		try {
			return constructMRAAirlineBillingDAO().generateInvoiceReports(
					filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * 
	 * @param cn51FilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<AirlineCN51SummaryVO> findCN51s(
			AirlineCN51FilterVO cn51FilterVO) throws SystemException {
		staticLogger().entering(CLASSNAME, "findCN51s");
		Page<AirlineCN51SummaryVO> summaryVOs = null;
		try {
			summaryVOs = constructMRAAirlineBillingDAO()
					.findCN51s(cn51FilterVO);
		} catch (PersistenceException persistenceException) {
			staticLogger().log(Log.INFO,
					" ##### PERSISTENCE EXCEPTION ... Fetching Failed ");
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
		staticLogger().exiting(CLASSNAME, "findCN51s");
		return summaryVOs;
	}
/**
 * @author a-3447
 * @param airlineCN51FilterVO
 * @return airlineCN51SummaryVO
 * @throws SystemException
 */
	public static AirlineCN51SummaryVO findCaptureInvoiceDetails(
			AirlineCN51FilterVO airlineCN51FilterVO) throws SystemException {
		staticLogger().entering(CLASSNAME, "findCaptureInvoiceDetails");
		AirlineCN51SummaryVO airlineCN51SummaryVO = null;
		try {
			airlineCN51SummaryVO = constructMRAAirlineBillingDAO()
					.findCaptureInvoiceDetails(airlineCN51FilterVO);
		} catch (PersistenceException persistenceException) {
			staticLogger().log(Log.INFO,
					" PERSISTENCE EXCEPTION!! ");
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
		staticLogger().exiting(CLASSNAME, "findCaptureInvoiceDetails");
		return airlineCN51SummaryVO;
	}
	/**
	  * 
	  * @param cN51CN66FilterVO
	  * @return
	  * @throws SystemException
	  */
	 public static ArlInvoiceDetailsReportVO generateCN66InvoiceReport(AirlineCN66DetailsFilterVO filterVO)
		throws SystemException{
		 try{
			 return constructDAO().generateCN66InvoiceReport(filterVO);
		 }
		 catch(PersistenceException persistenceException){
	    		throw new SystemException(persistenceException.getMessage(),persistenceException);
	    	}
}
	  /**
	     * method for calling up the DAO for the submodule
	     * @author a-2049
	     * @return queryDAO
	     * @throws SystemException
	     */
	    private static MRAAirlineBillingDAO constructDAO()
	    									throws SystemException {
	    	MRAAirlineBillingDAO queryDAO =null;
	        try {
				 queryDAO = (MRAAirlineBillingDAO)PersistenceController
				 								.getEntityManager()
								 				.getQueryDAO(MODULE_NAME);
	        } catch (PersistenceException e) {
					throw new SystemException(e.getMessage(),e);
	        }

	        return queryDAO;
	    }
	    /**
		 * @author a-2391
		 * @param companyCode
		 * @param airlineIdentifier
		 * @return
		 * @throws SystemException
		 */
		public static AirlineVO findAirlineAddresss(String companyCode, int airlineIdentifier)  throws SystemException{
			try {
				return constructDAO().findAirlineAddresss(companyCode,airlineIdentifier);	
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getMessage());
			} 
        }

		
			
		

		
}
