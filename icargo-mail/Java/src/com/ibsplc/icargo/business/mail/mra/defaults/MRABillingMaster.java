/*
 * MRABillingMaster.java Created on Feb 23, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
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
import javax.persistence.Version;

import com.ibsplc.icargo.business.cra.defaults.vo.CRAParameterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.AuditHelper;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.RejectionMemo;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.defaults.vo.AirlineBillingFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.InvoiceLovFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.CCAdetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNPopUpVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DSNRoutingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DespatchEnquiryVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentStatisticsFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAAccountingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MailProrationLogVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.OutstandingBalanceVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.ProrationFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.SectorRevenueDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.USPSReportingVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.UnaccountedDispatchesVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN51Details;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN51Summary;
import com.ibsplc.icargo.business.mail.mra.gpabilling.CN66Details;
import com.ibsplc.icargo.business.mail.mra.gpabilling.MRAGPABillingDetails;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GenerateInvoiceFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.MailGPAInvoicDetail;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailScanDetailVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1747
 *
 */


@Table(name = "MALMRABLGMST")
@Entity
public class MRABillingMaster {

    private static final String OPFLAG_INS = "I";
    private static final String OPFLAG_UPD = "U";

    private static final String MODULE_NAME = "mail.mra.defaults";

    private static final String CLASS_NAME = "MRABillingMaster";

    private static final Log log = LogFactory.getLogger("MRA BILLINGLINE");

    private static final String WITHDRAWN_DIRECT = "WD";

	private static final String COMMA=",";
	private static final String BLANK="";

	private MRABillingMasterPK billingMasterPK;

	private String orgExchangeOffice;
	private String dstExchangeOffice;
	private String mailCatagoryCode;
	private String mailSubClass;
	private String receptacleSerNumber;
	private String dsn;
	private String highestNumberedReceptacle;
	private int year;
	private String registeredIndicator;
    private String lastUpdatedUser;
    private Calendar lastUpdateTime;
    private Calendar recievableDate;
    private String route;
    private double totpcs;
    private double grsWgt;


    //for rate audit details

	//private String dsnaccsta;
	private String poaFlag;
	private String transfercarcode;
	private double declaredValue;
	private String currencyCode;
	//Added by A-7794 as part of MRA Revamp


	private String mailIdentifier;
	private String consignmentDocNumber;
	private int consignmentSeqNumber;
	private String postalAuthorityCode;
	private String uldNumber;
	private String mailOriginArptCode;
	private String destAirportCode;
	private String originCityCode;
	private String destCityCode;
	private String mailStatus;
	private String mailNatureFlag;
	private String mailCompanyCode;
	private String transferPACode;





    /**
	 * 	Getter for declaredValue
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	@Column(name="DCLVAL")
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 *  @param declaredValue the declaredValue to set
	 * 	Setter for declaredValue
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}
	/**
	 * 	Getter for currencyCode
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	@Column(name="CURCOD")
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 *  @param currencyCode the currencyCode to set
	 * 	Setter for currencyCode
	 *	Added by : A-5219 on 25-Feb-2014
	 * 	Used for :
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

    private Set<MRABillingDetails> billingDetails;
    private Set<MRAGPABillingDetails> gpaBillingDetails;

    // for cca details
    private Collection<CCADetail> ccaDetails;

    private Set<MailGPAInvoicDetail> gpaInvoicDetails;
	/**
	 * @return the poaFlag
	 */
    @Column(name="POAFLG")
	public String getPoaFlag() {
		return poaFlag;
	}
	/**
	 * @param poaFlag the poaFlag to set
	 */
	public void setPoaFlag(String poaFlag) {
		this.poaFlag = poaFlag;
	}
	/**
	 * @return the transfercarcode
	 */
	 @Column(name="TRFCARCOD")
	public String getTransfercarcode() {
		return transfercarcode;
	}
	/**
	 * @param transfercarcode the transfercarcode to set
	 */
	public void setTransfercarcode(String transfercarcode) {
		this.transfercarcode = transfercarcode;
	}
	/**
	 * @return the grsWgt
	 */
    @Column(name="GRSWGT")
	public double getGrsWgt() {
		return grsWgt;
	}
	/**
	 * @param grsWgt the grsWgt to set
	 */
	public void setGrsWgt(double grsWgt) {
		this.grsWgt = grsWgt;
	}
	/**
	 * @return the totpcs
	 */
	 @Column(name="TOTPCS")
	public double getTotpcs() {
		return totpcs;
	}
	/**
	 * @param totpcs the totpcs to set
	 */
	public void setTotpcs(double totpcs) {
		this.totpcs = totpcs;
	}
	/**
	 * @return the route
	 */
    @Column(name="ROU")
	public String getRoute() {
		return route;
	}
	/**
	 * @param route the route to set
	 */
	public void setRoute(String route) {
		this.route = route;
	}
	/**
	 * @return Returns the recievableDate.
	 */
    @Column(name="RCVDAT")
	public Calendar getRecievableDate() {
		return recievableDate;
	}
	/**
	 * @param recievableDate The recievableDate to set.
	 */
	public void setRecievableDate(Calendar recievableDate) {
		this.recievableDate = recievableDate;
	}
	
	/**
	 * 	Getter for transferPACode 
	 *	Added by : A-8061 on 07-Jan-2021
	 * 	Used for :
	 */
	@Column(name="TRFPOACOD")
	public String getTransferPACode() {
		return transferPACode;
	}
	/**
	 *  @param transferPACode the transferPACode to set
	 * 	Setter for transferPACode 
	 *	Added by : A-8061 on 07-Jan-2021
	 * 	Used for :
	 */
	public void setTransferPACode(String transferPACode) {
		this.transferPACode = transferPACode;
	}
	
	/**
	 *
	 * @return
	 */
	//Modified by A-7794 as part of MRA Revamp
	@EmbeddedId
	@AttributeOverrides( {
			//@AttributeOverride(name = "consignmentSeqNumber", column = @Column(name = "CSGSEQNUM")),
			//@AttributeOverride(name = "consignmentDocNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			//@AttributeOverride(name = "postalAuthorityCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "mailSeqNumber", column = @Column(name = "MALSEQNUM"))})
			//@AttributeOverride(name = "mailIdentifier", column = @Column(name = "MALIDR"))})

	public MRABillingMasterPK getBillingMasterPK() {
		return billingMasterPK;
	}
	/**
	 *
	 * @param billingMasterPK
	 */
	public void setBillingMasterPK(MRABillingMasterPK billingMasterPK) {
		this.billingMasterPK = billingMasterPK;
	}



	/**
	 * @return the billingDetails
	 */
	//Modified by A-7794 as part of MRA Revamp
	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
		//@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
		//@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
		//@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false),
		@JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable = false, updatable = false)
		//@JoinColumn(name = "MALIDR", referencedColumnName = "MALIDR", insertable = false, updatable = false)
	})
	public Set<MRABillingDetails> getBillingDetails() {
		return billingDetails;
	}
	/**
	 * @param billingDetails the billingDetails to set
	 */
	public void setBillingDetails(Set<MRABillingDetails> billingDetails) {
		this.billingDetails = billingDetails;
	}




	@OneToMany
	@JoinColumns({
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
		//@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
		//@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
		//@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false),
		@JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable = false, updatable = false),
		//@JoinColumn(name = "MALIDR", referencedColumnName = "MALIDR", insertable = false, updatable = false)
	})
	public Set<MRAGPABillingDetails> getGpaBillingDetails() {
		return gpaBillingDetails;
	}
	/**
	 * @param gpaBillingDetails the gpaBillingDetails to set
	 */
	public void setGpaBillingDetails(Set<MRAGPABillingDetails> gpaBillingDetails) {
		this.gpaBillingDetails = gpaBillingDetails;
	}
	/**
     * @return Returns the ccaDetails.
     */
	//Modified by A-7794 as part of MRA Revamp

    @OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 //@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable=false, updatable=false),
	 //@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable=false,updatable=false),
	 //@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable=false, updatable=false),
	 @JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable=false,updatable=false)
	 //@JoinColumn(name = "MALIDR", referencedColumnName = "MALIDR", insertable=false, updatable=false)
	 })

    public Collection<CCADetail> getCCADetail() {
        return ccaDetails;
    }
    /**
	 *
	 * @param billingMasterPK
	 */
	public void setCCADetail(Collection<CCADetail> ccaDetails) {
		this.ccaDetails = ccaDetails;
	}
	/**
	 * @return the mailIdentifier
	 */
	@Column(name="MALIDR")
	public String getMailIdentifier() {
		return mailIdentifier;
	}

	/**
	 * @param mailIdentifier the mailIdentifier to set
	 */
	public void setMailIdentifier(String mailIdentifier) {
		this.mailIdentifier = mailIdentifier;
	}

	/**
	 * @return the consignmentDocNumber
	 */
	@Column(name="CSGDOCNUM")
	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}

	/**
	 * @param consignmentDocNumber the consignmentDocNumber to set
	 */
	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}

	/**
	 * @return the consignmentSeqNumber
	 */
	@Column(name="CSGSEQNUM")
	public int getConsignmentSeqNumber() {
		return consignmentSeqNumber;
	}

	/**
	 * @param consignmentSeqNumber the consignmentSeqNumber to set
	 */
	public void setConsignmentSeqNumber(int consignmentSeqNumber) {
		this.consignmentSeqNumber = consignmentSeqNumber;
	}

	/**
	 * @return the postalAuthorityCode
	 */
	@Column(name="POACOD")
	public String getPostalAuthorityCode() {
		return postalAuthorityCode;
	}

	/**
	 * @param postalAuthorityCode the postalAuthorityCode to set
	 */
	public void setPostalAuthorityCode(String postalAuthorityCode) {
		this.postalAuthorityCode = postalAuthorityCode;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="DSN")
	public String getDsn() {
		return dsn;
	}
	/**
	 *
	 * @param destpatchSerNumber
	 */
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="DSTEXGOFC")
	public String getDstExchangeOffice() {
		return dstExchangeOffice;
	}
	/**
	 *
	 * @param dstExchangeOffice
	 */
	public void setDstExchangeOffice(String dstExchangeOffice) {
		this.dstExchangeOffice = dstExchangeOffice;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="HSN")
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}
	/**
	 *
	 * @param highestSerNumber
	 */
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}

	@Column(name="LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	@Version
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 *
	 * @param lastUpdateTime
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="MALCTGCOD")
	public String getMailCatagoryCode() {
		return mailCatagoryCode;
	}
	/**
	 *
	 * @param mailCatagoryCode
	 */
	public void setMailCatagoryCode(String mailCatagoryCode) {
		this.mailCatagoryCode = mailCatagoryCode;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="MALSUBCLS")
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 *
	 * @param mailSubClass
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="ORGEXGOFC")
	public String getOrgExchangeOffice() {
		return orgExchangeOffice;
	}
	/**
	 *
	 * @param orgExchangeOffice
	 */
	public void setOrgExchangeOffice(String orgExchangeOffice) {
		this.orgExchangeOffice = orgExchangeOffice;
	}



	/**
	 *
	 * @return
	 */
	@Column(name="RSN")
	public String getReceptacleSerNumber() {
		return receptacleSerNumber;
	}
	/**
	 *
	 * @param receptacleSerNumber
	 */
	public void setReceptacleSerNumber(String receptacleSerNumber) {
		this.receptacleSerNumber = receptacleSerNumber;
	}
	/**
	 *
	 * @return
	 */
	@Column(name="REGIND")
	public String getRegisteredIndicator() {
		return registeredIndicator;
	}
	/**
	 *
	 * @param registeredIndicator
	 */
	public void setRegisteredIndicator(String registeredIndicator) {
		this.registeredIndicator = registeredIndicator;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="YER")
	public int getYear() {
		return year;
	}
	/**
	 *
	 * @param year
	 */
	public void setYear(int year) {
		this.year = year;
	}

	//Added by A-7794 as part of MRA Revamp


	/**
	 * @return the uldNumber
	 */
	@Column(name="ULDNUM")
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber the uldNumber to set
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return the mailOriginArptCode
	 */
	@Column(name="ORGARPCOD")
	public String getMailOriginArptCode() {
		return mailOriginArptCode;
	}
	/**
	 * @param mailOriginArptCode the mailOriginArptCode to set
	 */
	public void setMailOriginArptCode(String mailOriginArptCode) {
		this.mailOriginArptCode = mailOriginArptCode;
	}
	/**
	 * @return the destAirportCode
	 */
	@Column(name="DSTARPCOD")
	public String getDestAirportCode() {
		return destAirportCode;
	}
	/**
	 * @param destAirportCode the destAirportCode to set
	 */
	public void setDestAirportCode(String destAirportCode) {
		this.destAirportCode = destAirportCode;
	}
	/**
	 * @return the originCityCode
	 */
	@Column(name="ORGCTYCOD")
	public String getOriginCityCode() {
		return originCityCode;
	}
	/**
	 * @param originCityCode the originCityCode to set
	 */
	public void setOriginCityCode(String originCityCode) {
		this.originCityCode = originCityCode;
	}
	/**
	 * @return the destCityCode
	 */
	@Column(name="DSTCTYCOD")
	public String getDestCityCode() {
		return destCityCode;
	}
	/**
	 * @param destCityCode the destCityCode to set
	 */
	public void setDestCityCode(String destCityCode) {
		this.destCityCode = destCityCode;
	}
	/**
	 * @return the mailStatus
	 */
	@Column(name="MRASTA")
	public String getMailStatus() {
		return mailStatus;
	}
	/**
	 * @param mailStatus the mailStatus to set
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	/**
	 * @return the mailNatureFlag
	 */
	@Column(name="DOMINTFLG")
	public String getMailNatureFlag() {
		return mailNatureFlag;
	}
	/**
	 * @param mailNatureFlag the mailNatureFlag to set
	 */
	public void setMailNatureFlag(String mailNatureFlag) {
		this.mailNatureFlag = mailNatureFlag;
	}
	/**
	 * @return the mailCompanyCode
	 */
	@Column(name="MALCMPCOD")
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	/**
	 * @param mailCompanyCode the mailCompanyCode to set
	 */
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}

	public MRABillingMaster() {

	  }


	/**
	 * @author a-1747
	 * @param companyCode
	 * @param billingBasis
	 * @param serialNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	 public static MRABillingMaster find(String companyCode,long  mailSeqNumber)
	    throws SystemException,FinderException {
		 MRABillingMasterPK pk = new MRABillingMasterPK();
			pk.setCompanyCode(companyCode);
			pk.setMailSeqNumber(mailSeqNumber);
			return PersistenceController.getEntityManager().find(
					MRABillingMaster.class, pk);

	    }



	/** method to generate an invoice
	 * @author a-2270
	 * @param generateInvoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */

    public static void generateInvoice(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException,MailTrackingMRABusinessException {

    	String outParameters[] = null;
		StringBuilder remarks = new StringBuilder();
		StringBuilder finalRemarks = new StringBuilder();
		String txnSta = "";
		String failureFlag = null;
		String successFlag = null;
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();

    	try{
    		outParameters = constructDAO().generateInvoice(generateInvoiceFilterVO);
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		//Commented as part of ICRD-136375
		/*if( "invalid billing period".equalsIgnoreCase(outParameters[2]) ) {
			MailTrackingMRABusinessException mailBusExp
			= new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.
					NOT_VALID_BILLINGPERIOD));
			throw mailBusExp;
	    }

		if(  ("INV_ALREADY_GEN").equalsIgnoreCase(outParameters[2]) ) {
			MailTrackingMRABusinessException mailBusExp
							= new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.
					MTK_MRA_GPABILLING_STATUS_INVALRGEN));
			throw mailBusExp;

		}else if(("NO_BLB_RECORDS").equalsIgnoreCase(outParameters[2]) ) {
			MailTrackingMRABusinessException mailBusExp
			= new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.
							MTK_MRA_GPABILLING_NO_BILLABLE_DSNS));
				throw mailBusExp;

		}*/
		//Added for ICRD-136375
		if(outParameters!=null && outParameters.length > 0){
			String[] outParam = outParameters[0].split("#");

		log.log(Log.FINE, "outParameter[0]",outParameters[0]);
		log.log(Log.FINE, "outParameter[1]",outParameters[1]);
		log.log(Log.FINE, "outParameter[2]",outParameters[2]);

		if(outParam!=null && outParam.length>0){
			if(!("OK".equals(outParam[0]))){
				failureFlag = "Y";
				 log.log(Log.FINE, "outParameter[1] before setting rmk111",outParameters[1] );
				// if ( outParameter[1].trim().length() > 0) {
					remarks.append(outParameters[1]);
						log.log(Log.FINE, "remarks inside",remarks.toString() );
				//}
				}else{
					successFlag="Y";
				}
			}
		if (generateInvoiceFilterVO.getCountryCode() != null
				&& generateInvoiceFilterVO.getCountryCode().trim().length() > 0) {
			finalRemarks.append("Country:")
					.append(generateInvoiceFilterVO.getCountryCode()).append(COMMA);
		}if (generateInvoiceFilterVO.getGpaCode()!= null
				&& generateInvoiceFilterVO.getGpaCode().trim().length() > 0) {
			finalRemarks.append("GpaCod:")
					.append(generateInvoiceFilterVO.getGpaCode()).append(COMMA);
		}
		finalRemarks.append("From Date:").append(generateInvoiceFilterVO.getBillingPeriodFrom().toDisplayFormat("dd/MM/yyyy"))
				.append(COMMA).append("To Date:")
				.append(generateInvoiceFilterVO.getBillingPeriodTo().toDisplayFormat("dd/MM/yyyy"));
	   if("Y".equals(failureFlag) && "Y".equals(successFlag)){
		   txnSta = "P";
		   finalRemarks.append("\n").append(",Failure Reason:");
	   }else if("Y".equals(failureFlag)){
		   txnSta = "F";
		   finalRemarks.append("\n").append(",Failure Reason:");
	   }else if("Y".equals(successFlag)){
		   txnSta = "C";
		   remarks.append(COMMA);
		   remarks.append(outParameters[1]);
	   }
	   if(remarks != null){
			log.log(Log.FINE, "remarks last",remarks.toString() );
			//finalRemarks.append(COMMA);
			finalRemarks.append(remarks.toString());
       }
	   invoiceTransactionLogVO.setCompanyCode(generateInvoiceFilterVO.getCompanyCode());
	   invoiceTransactionLogVO.setInvoiceType(generateInvoiceFilterVO.getInvoiceType());
	   invoiceTransactionLogVO.setTransactionCode(generateInvoiceFilterVO.getTransactionCode());
	   invoiceTransactionLogVO.setSerialNumber(generateInvoiceFilterVO.getInvoiceLogSerialNumber());
	   invoiceTransactionLogVO.setInvoiceGenerationStatus(txnSta);
	   if(finalRemarks != null && finalRemarks.length() >0){
	   invoiceTransactionLogVO.setRemarks(finalRemarks.toString());
	   }else{
		   invoiceTransactionLogVO.setRemarks(BLANK);
	   }
		try {
		     new CRADefaultsProxy().updateTransactionandRemarks(invoiceTransactionLogVO);
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage());
		}
		}


	}
    /** method to generate an invoice
	 * @author a-2270
	 * @param generateInvoiceFilterVO
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */

    public static void performAccountingForDSNs(Collection<UnaccountedDispatchesDetailsVO> unAccountedDSNVOs)
	throws SystemException,MailTrackingMRABusinessException {
    	String outParameter = null;
    	try{
    		outParameter = constructDAO().performAccountingForDSNs(unAccountedDSNVOs);
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}

		if( ! ("OK").equalsIgnoreCase(outParameter) ) {
			MailTrackingMRABusinessException mailBusExp
							= new MailTrackingMRABusinessException();
			mailBusExp.addError(new ErrorVO(MailTrackingMRABusinessException.
					MAILTRACKING_MRA_EXCEPTION_UNACCOUNTEDDSNACCOUNTING_FAILED));
			throw mailBusExp;
		}

    }
    /**
	 * This method constructs
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRADefaultsDAO constructDAO()
	throws PersistenceException, SystemException{
		Log log = LogFactory.getLogger("MRA BILLING");
		log.entering("MRABillingMaster","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRADefaultsDAO.class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}

	/**
	 *  Method to find the billingperiod of the agent/billing periods of all
	 *  the agents of the country specified
	 *  Method is called on clickig the suggestbutton
	 * @param nrainvoicefiltervo
	 * @return
	 * A-3108
	 * @throws SystemException
	 */
	public static Collection<String> findBillingPeriods(GenerateInvoiceFilterVO generateInvoiceFilterVO) throws SystemException {

			try {
				Log log = LogFactory.getLogger("MRA GPABILLING");
				log.entering("BillingMaster","entity");
				return constructDAO().findBillingPeriods(generateInvoiceFilterVO);
			} catch (PersistenceException e) {
				throw new SystemException(e.getErrorCode(), e);
			}
}
	/**
	    * @param companyCode
	    * @param blgbasis
	    * @param despatchDate
	    * @return DespatchEnquiryVO
	    * @throws SystemException
	    */
		public static DespatchEnquiryVO findDespatchDetails(DSNPopUpVO popUpVO)throws SystemException{
			try{
				return MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findDespatchDetails(popUpVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}
		/**
		 *
		 * @param companyCode
		 * @param blgbasis
		 * @param despatchDate
		 * @return Collection<GPABillingDetailsVO>
		 * @throws SystemException
		 */
		public static Collection<GPABillingDetailsVO> findGPABillingDetails(DSNPopUpVO popUpVO)throws SystemException{
			try{
				return MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findGPABillingDetails(popUpVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}

		 public static  Page<DSNPopUpVO> findDsnSelectLov(String companyCode,
					String dsnNum,String dsnDate,int pageNumber)
		    throws SystemException{

		    	try{
		    		return MRADefaultsDAO.class.cast(
							PersistenceController.getEntityManager().
							getQueryDAO(MODULE_NAME)).findDsnSelectLov(companyCode,dsnNum,dsnDate,pageNumber);
		    	}
		    	catch(PersistenceException persistenceException){
		    		throw new SystemException(persistenceException.getMessage(),persistenceException);
		    	}
		    }
		 /**
			 * @author a-3108
			* @return Collection<RateAuditVO>
			 * @throws SystemException
			*/
			public static Page<RateAuditVO> findRateAuditDetails(RateAuditFilterVO rateAuditFilterVO) throws SystemException {
				try {
					Log log = LogFactory.getLogger("MRA GPABILLING");
					log.entering("BillingMaster","entity");
					return constructDAO().findRateAuditDetails(rateAuditFilterVO);
				} catch (PersistenceException e) {
					throw new SystemException(e.getErrorCode(), e);
				}
			}

			 /**
			 * @author a-3434
			* @return Collection<RateAuditVO>
			 * @throws SystemException
			*/
			public static Collection<RateAuditVO> findRateAuditDetailsCol(RateAuditFilterVO rateAuditFilterVO) throws SystemException {
				try {
					Log log = LogFactory.getLogger("MRA GPABILLING");
					log.entering("BillingMaster","entity");
					return constructDAO().findRateAuditDetailsCol(rateAuditFilterVO);
				} catch (PersistenceException e) {
					throw new SystemException(e.getErrorCode(), e);
				}
			}

			/**
			  *
			  * @param rateAuditFilterVO
			  * @return
			  * @throws SystemException
			  */
			 public static RateAuditVO findListRateAuditDetails(RateAuditFilterVO rateAuditFilterVO)throws SystemException{
					try{
						return MRADefaultsDAO.class.cast(
								PersistenceController.getEntityManager().
								getQueryDAO(MODULE_NAME)).findListRateAuditDetails(rateAuditFilterVO);
				    }catch (PersistenceException persistenceException) {
						  persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}


			 public static Collection<ProrationDetailsVO> listProrationDetails(ProrationFilterVO prorationFilterVO) throws SystemException {

					try {
						Log log = LogFactory.getLogger("MRA GPABILLING");
						log.entering("BillingMaster","entity");
						return constructDAO().listProrationDetails(prorationFilterVO);
					} catch (PersistenceException e) {
						throw new SystemException(e.getErrorCode(), e);
					}
		}


			 /***
				 * @author A-3434
				 * @param airlineBillingFilterVO
				 * return DocumentBillingDetailsVO
				* @throws SystemException
				 */
				public  static Page<DocumentBillingDetailsVO> findInterlineBillingEntries(AirlineBillingFilterVO airlineBillingFilterVO)
				throws SystemException{

					return MRABillingDetails.findInterlineBillingEntries(airlineBillingFilterVO);

				}

				/** method to save in billingdetails
				 * @author a-3229
				 * @param ProrationDetailsVO
				 * @throws SystemException
				 */
				 public static  void saveProrationDetails(ProrationDetailsVO prorationDetailsVO) throws SystemException {
					 log.log(Log.INFO,
							"ProrationDetailsVO*********************",
							prorationDetailsVO);
						MRABillingDetailsVO mraBillingDetailsvo=new MRABillingDetailsVO();
						mraBillingDetailsvo.setSequenceNumber(prorationDetailsVO.getSerialNumber());
						mraBillingDetailsvo.setCsgSeqNumber(Integer.parseInt(prorationDetailsVO.getConsigneeSequenceNumber()));
						mraBillingDetailsvo.setCsgDocumentNumber(prorationDetailsVO.getConsigneeDocumentNumber());
						mraBillingDetailsvo.setBillingBasis(prorationDetailsVO.getBillingBasis());
						mraBillingDetailsvo.setCompanyCode(prorationDetailsVO.getCompanyCode());
						mraBillingDetailsvo.setPoaCode(prorationDetailsVO.getPostalAuthorityCode());

						mraBillingDetailsvo.setFlightCarrierCode(prorationDetailsVO.getCarrierCode());
						mraBillingDetailsvo.setFlightCarrierID(prorationDetailsVO.getCarrierId());
						mraBillingDetailsvo.setSectorStatus(prorationDetailsVO.getSectorStatus());
						mraBillingDetailsvo.setSegFrom(prorationDetailsVO.getSectorFrom());
						mraBillingDetailsvo.setSegTo(prorationDetailsVO.getSectorTo());
						mraBillingDetailsvo.setPieces(prorationDetailsVO.getNumberOfPieces());
						mraBillingDetailsvo.setWeight(prorationDetailsVO.getWeight());

						mraBillingDetailsvo.setWgtChargeBas(prorationDetailsVO.getProrationAmtInBaseCurr().getAmount());
						mraBillingDetailsvo.setWgtCharge(prorationDetailsVO.getProratedAmtInCtrCur().getAmount());
						mraBillingDetailsvo.setWgtChargeSdr(prorationDetailsVO.getProrationAmtInSdr().getAmount());
						mraBillingDetailsvo.setWgtChargeUsd(prorationDetailsVO.getProrationAmtInUsd().getAmount());

						mraBillingDetailsvo.setProratedValue(prorationDetailsVO.getProrationFactor());
						mraBillingDetailsvo.setProrationPercentage(prorationDetailsVO.getProrationPercentage());
						mraBillingDetailsvo.setProrationType(prorationDetailsVO.getProrationType());

						mraBillingDetailsvo.setPaymentFlag(prorationDetailsVO.getPayableFlag());
						mraBillingDetailsvo.setContractCurrCode(prorationDetailsVO.getCtrCurrencyCode());
						mraBillingDetailsvo.setGpaArlBlgStatus(prorationDetailsVO.getGpaarlBillingFlag());

						 log.log(Log.INFO,
								"mraBillingDetailsvo*********************",
								mraBillingDetailsvo);
					new MRABillingDetails(mraBillingDetailsvo);

			}


				 /**
					 * method to generate outward billing invoice
				     * @param invoiceFilterVO
				     * @return String
				     * @throws SystemException
				     */
				    public static String generateOutwardBillingInvoice(InvoiceLovFilterVO invoiceFilterVO)
				    	throws SystemException {

				    	String outParameter = null;

				    	try {
							outParameter = constructDAO()
												.generateOutwardBillingInvoice(invoiceFilterVO);
						} catch (PersistenceException e) {
							throw new SystemException(e.getErrorCode(), e);
						}
						return outParameter;
				    }
				 /**
					 * This method updates the parent entity and its child entities
					 * @author A-3251
					 * @param newRateAuditVO
					 * @throws SystemException
					 */
					public void update(RateAuditVO newRateAuditVO)throws SystemException{
					 populateAttributes(newRateAuditVO);
						populateChildren(newRateAuditVO);
				 }

					/**
					 * @param rateAuditVO
					 */
					private void populateAttributes(RateAuditVO rateAuditVO) {


						this.setOrgExchangeOffice(rateAuditVO.getOriginOE());
						this.setDstExchangeOffice(rateAuditVO.getDestinationOE());
						this.setMailCatagoryCode(rateAuditVO.getCategory());
						this.setMailSubClass(rateAuditVO.getSubClass());
						this.setDsn(rateAuditVO.getDsn());
						this.setYear(rateAuditVO.getYear());
						this.setRoute(rateAuditVO.getRoute());
						this.setTotpcs(Double.parseDouble(rateAuditVO.getPcs()));
						this.setGrsWgt(rateAuditVO.getGrossWt());
						this.setRecievableDate(rateAuditVO.getReceivedDate());
						//this.setDsnaccsta(rateAuditVO.getDsnaccsta());
						this.setHighestNumberedReceptacle(rateAuditVO.getHsn());
						this.setRegisteredIndicator(rateAuditVO.getRegInd());
						this.setReceptacleSerNumber(rateAuditVO.getRsn());
						this.setLastUpdateTime(rateAuditVO.getLastUpdateTime());
						this.setLastUpdatedUser(rateAuditVO.getLastUpdateUser());
						this.setPoaFlag(rateAuditVO.getPoaFlag());
						this.setTransfercarcode(rateAuditVO.getTransfercarcode());
						this.setMailIdentifier(rateAuditVO.getMailbagId());
						this.setConsignmentDocNumber(rateAuditVO.getConDocNum());
						this.setConsignmentSeqNumber(rateAuditVO.getConSerNum());
						this.setPostalAuthorityCode(rateAuditVO.getGpaCode());
						this.setMailOriginArptCode(rateAuditVO.getOrigin());
						this.setDestAirportCode(rateAuditVO.getDestination());
						this.setOriginCityCode(rateAuditVO.getOriginCityCode());
						this.setDestCityCode(rateAuditVO.getDestinationCityCode());

					}

					/**
					 * This method populates the Child tables
					 * @author A-3251
					 * @param passengerBaggageVO
					 */
					private void populateChildren(RateAuditVO rateAuditVO) throws SystemException  {
						log.entering("MRABillingMaster", "populateChildren");

						if(rateAuditVO!=null){

						Collection<RateAuditDetailsVO>  rateAuditDetailsVOs = new ArrayList<RateAuditDetailsVO>();
						MRABillingDetails mRABillingDetails = null;
						rateAuditDetailsVOs = rateAuditVO.getRateAuditDetails();
						if(rateAuditDetailsVOs!=null){
							for(RateAuditDetailsVO rateAuditDetailsVO : rateAuditDetailsVOs){
								 mRABillingDetails =new MRABillingDetails(rateAuditDetailsVO);
								if(this.billingDetails!=null){
									this.billingDetails.add(mRABillingDetails);
								}else{
									this.billingDetails =  new HashSet<MRABillingDetails>();
									this.billingDetails.add(mRABillingDetails);
									}
							 }
						}

						}


						log.exiting("MRABillingMaster", "populateChildren");
					}

					/**
					 * This method populates the Temptables by calling procedure spr_cpt_tot
					 * @author A-3251
					 * @param rateAuditVO
					 */
					public static String populateDataInTempTables(RateAuditVO rateAuditVO) throws SystemException  {
						log.entering("MRABillingMaster", "populateDataInTempTables");

						try{
							return MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().
									getQueryDAO(MODULE_NAME)).populateDataInTempTables(rateAuditVO);
					    }catch (PersistenceException persistenceException) {
							  persistenceException.getErrorCode();
							throw new SystemException(persistenceException.getMessage());
						}
					}

					/**
				     * @author A-3434
				     *  updates BillingStatus
				     * @param gPABillingStatusVO
				     * @throws RemoveException
				     * @throws SystemException
				     * @throws CreateException
				     * @throws FinderException
				     */

				    public  void updateBillingStatus(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs) throws SystemException,
					CreateException,FinderException,ChangeStatusException{
				    	log.entering(MODULE_NAME,"update");

				    	CN66Details cn66Details=null;
				    	MRAGPABillingDetails mraGPABillingDetails=null;
				    	MRABillingMaster mraBillingMaster=null;
				    	RejectionMemo rejectionMemo=null;
				    	double totalAmountInBillingCurrency=0.0;

				    	for(DocumentBillingDetailsVO documentBillingDetailsVO:documentBillingDetailsVOs){

				    		String companycode=documentBillingDetailsVO.getCompanyCode();
/*				    		String billingBasis=documentBillingDetailsVO.getBillingBasis();
				    		String poaCode=documentBillingDetailsVO.getPoaCode();*/
				    		String invNumber=documentBillingDetailsVO.getInvoiceNumber();
				    		int sequenceNumber=documentBillingDetailsVO.getSerialNumber();
				    		//Added by A-7794 as part of MRA revamp
				    		long mailsequenceNumber= documentBillingDetailsVO.getMailSequenceNumber();
				    		int invSerialNumber=documentBillingDetailsVO.getInvSerialNumber();
/*				    		int csgSequenceNumber=documentBillingDetailsVO.getCsgSequenceNumber();
				    		String csgDocumentNumber=documentBillingDetailsVO.getCsgDocumentNumber();*/
				    		String gpaCode=documentBillingDetailsVO.getGpaCode();
				    		LocalDate lastUpdatedTime=documentBillingDetailsVO.getLastUpdatedTime();
				    		String billingStatus=documentBillingDetailsVO.getBillingStatus();
				    		String remarks=documentBillingDetailsVO.getRemarks();
				    		String ccaReferenceNo=documentBillingDetailsVO.getCcaRefNumber();
				    		int airlineIdentifier=documentBillingDetailsVO.getAirlineIdr();
				    		CN51Summary cn51Summary=null;
				    		Collection<CN51Details> cN51Details = new ArrayList<CN51Details>();
				    		boolean isWithdrawn = false;
				    		MRABillingDetails mraBillingDetails=null;
				    		Collection<MRABillingDetailsVO> mRABillingDetailsVOs=null;
				    		MRABillingDetailsVO mRABillingDetailsVO=null;


				    		try{

				    			//Modified by A-8061 for ICRD-302776  Begin
				    			if(documentBillingDetailsVO.getAirlineCode()==null){

				    			MRABillingDetailsVO mRABillingDetailsFilterVO = new MRABillingDetailsVO();
				    			mRABillingDetailsFilterVO.setCompanyCode(companycode);
				    			mRABillingDetailsFilterVO.setMailSequenceNumber(documentBillingDetailsVO.getMailSequenceNumber());
				    			mRABillingDetailsFilterVO.setBillTo("G");
				    			mRABillingDetailsFilterVO.setPaymentFlag("R");
				    			mRABillingDetailsVOs=MRABillingDetails.findMRABillingDetails(mRABillingDetailsFilterVO);

				    			if(mRABillingDetailsVOs!=null&&!mRABillingDetailsVOs.isEmpty()){
				    				mRABillingDetailsVO=((ArrayList<MRABillingDetailsVO>)mRABillingDetailsVOs).get(0);
				    				mraBillingDetails=MRABillingDetails.find(companycode,documentBillingDetailsVO.getMailSequenceNumber(),mRABillingDetailsVO.getSequenceNumber());
				    			}
				    			//Modified by A-8061 for ICRD-302776  end
				    			}else{
				    				mraBillingDetails=MRABillingDetails.find(companycode,documentBillingDetailsVO.getMailSequenceNumber(),sequenceNumber);
				    			}

				    			if(mraBillingDetails != null){
				    				isWithdrawn = WITHDRAWN_DIRECT.equals(mraBillingDetails.getBlgStatus()) ? true : false;
				    			}

				    		}catch(FinderException exception){
				    			log.log(Log.INFO, "exception ", exception.getMessage());
				    		}catch(SystemException exception){
				    			log.log(Log.INFO, "exception ", exception.getMessage());
				    		}
				    		/*
				    		 * for records in MTKARLMEM table
				    		 **/
				    		if("Y".equals(documentBillingDetailsVO.getMemoFlag())){
				    			//Here ccaReferenceNo is the memocode
				    			try{
				    				rejectionMemo=RejectionMemo.find(companycode,airlineIdentifier,ccaReferenceNo);

				    			}
				    			catch (FinderException e) {
				    				e.getErrorCode();
				    				throw new SystemException(e.getMessage());
				    			}
				    			rejectionMemo.setLastUpdatedTime(lastUpdatedTime);
				    			rejectionMemo.setMemoStatus(billingStatus);
				    			rejectionMemo.setRemarks(remarks);
				    		}
				    		else{
				    		/*
				    		 * for records in MTKGPAC66DTL table
				    		 **/
				    		if(invNumber!=null && invNumber.trim().length()>0 && !isWithdrawn){
				    			log.log(Log.INFO, "invNumber ", invNumber);
								try{
									//Modified by A-7794 as part of MRA revamp
				    				cn66Details=CN66Details.find(companycode,invNumber,gpaCode,mailsequenceNumber,invSerialNumber);

				    			}
				    			catch (FinderException e) {
				    				e.getErrorCode();
				    				continue;
				    				//throw new SystemException(e.getMessage());
				    			}
				    double amount = cn66Details.getBilledAmountInBillingCurr();
					if (WITHDRAWN_DIRECT.equals(documentBillingDetailsVO
							.getBillingStatus())) {
						//Modified by A-7794 as part of MRA revamp
						cn51Summary = CN51Summary.find(companycode, invNumber,invSerialNumber,
								gpaCode);
						cN51Details = cn51Summary.getCn51details();
						totalAmountInBillingCurrency = cn51Summary
								.getTotalAmountInBillingCurr();
						// amount becomes present amount in billing currency
						// -amount
						cn51Summary
								.setTotalAmountInBillingCurr(totalAmountInBillingCurrency
										- amount);
						if (cN51Details != null) {
							for (CN51Details cN51Detail : cN51Details) {
								if (cn66Details.getMailCategoryCode().equals(
										cN51Detail.getMailCategoryCode())
										&& cn66Details
												.getActualSubclass()
												.equals(
														cN51Detail
																.getMailSubclass())
										&& cn66Details.getBillSectorOrigin().equals(
												cN51Detail.getOrigin())
										&& cn66Details.getBillSectorToo().equals(
												cN51Detail.getDestination())
										&& cn66Details.getApplicableRate() == cN51Detail
												.getApplicableRate()
										&& cn66Details
												.getContrctCurrencyCode()
												.equals(
														cN51Detail
																.getBillingCurrencyCode())) {
									double totalCN51AmtInBillingCurrency = cN51Detail
											.getTotalAmountinBillingCurr()
											- cn66Details.getBilledAmountInBillingCurr();
									cN51Detail.setTotalAmountinBillingCurr(cN51Detail
											.getTotalAmountinBillingCurr()
											- cn66Details.getBilledAmountInBillingCurr());
									cN51Detail.setTotalWeight(cN51Detail
											.getTotalWeight()
											- cn66Details.getTotalWeight());
									log
											.log(
													Log.FINE,
													"totalCN51AmtInBillingCurrency===>>",
													totalCN51AmtInBillingCurrency);
									if (totalCN51AmtInBillingCurrency == 0.0) {
										log
												.log(Log.FINE,
														"inside if totalCN51AmtInBillingCur===>>");
										try {// removing the entity
											cN51Detail.remove();
										} catch (RemoveException e) {
											e.getErrorCode();
										}
									}
								}
							}
						}
						try {// removing the entity
							cn66Details.remove();
						} catch (RemoveException e) {
							e.getErrorCode();
						}

					} else {
						cn66Details.setLastUpdateTime(lastUpdatedTime);
						cn66Details.setBillingStatus(billingStatus);
						cn66Details.setRemarks(remarks);
					}

				    			/*
								 * for reflecting the billingStatus status
								 * change in MTKMRABLGDTL
								 */
					//Modified by A-7794 as part of ICRD-285558 and ICRD-287225
					if(mraBillingDetails != null && mraBillingDetails.getBillTooPartyType().equals("A")){
						mraBillingDetails.setBlgStatus(billingStatus);
						mraBillingDetails.setRemarks(remarks);
					}else{
				    			try
				    			{
				    				//Modified by A-7794 as part of MRA revamp
				    				mraGPABillingDetails=MRAGPABillingDetails.find(companycode,cn66Details.getCn66DetailsPK().getMailsequenceNumber(),sequenceNumber);
				    			}
				    			catch (SystemException e) {
				    				e.getMessage();
				    				throw new SystemException(e.getMessage());
				    			}
				    			catch (FinderException e) {
				    				e.getErrorCode();
				    				throw new SystemException(e.getMessage());
				    			}
				    			mraGPABillingDetails.setBillingStatus(billingStatus);
				    			mraGPABillingDetails.setRemarks(remarks);
				    			log.log(Log.INFO,
										" updating MTKMRABLGDTL...BlgStatus ",
										mraGPABillingDetails.getBillingStatus());


					}

				    		}


				    		/*
				    		 * for records in MTKMRABLGDTL  table
				    		 **/
				    		else{
				    			log.log(Log.INFO, "invNumber null", invNumber);
								mraBillingMaster=MRABillingMaster.find(
				    					companycode,mailsequenceNumber);//modified as part of ICRD-264427
				    			if(sequenceNumber !=0){
								//mraBillingDetails should be populated from parent entity,rather than entity find.Code to be changed appropriately
				    				//Modified by A-7794 as part of MRA revamp
				    				//Modified by A-7794 as part of ICRD-285558 and ICRD-287225
				    				if(mraBillingDetails != null && mraBillingDetails.getBillTooPartyType().equals("A")){
										mraBillingDetails.setBlgStatus(billingStatus);
										mraBillingDetails.setRemarks(remarks);
									}else{
				    				mraGPABillingDetails=MRAGPABillingDetails.find(companycode,mailsequenceNumber,sequenceNumber );
									}
				    			}
				    			ArrayList<CCADetail> ccadetails=null;
				    			if(mraBillingMaster!=null)
				    			{
				    			ccadetails=new ArrayList<CCADetail>(mraBillingMaster.getCCADetail());
				    			}
				    			log.log(Log.INFO, "billingstatus",
										billingStatus);
								/*
				    			 * checks whether a despatch contains an actual CCA
				    			 * if it contains an actual CCA,it will throw the
				    			 * business exception.Otherwise billingstatus will change.
				    			 **/
				    			if(ccadetails!=null)
				    			{
				    			findActualCCA(ccadetails);
				    			}

				    			if(ccaReferenceNo != null && ccaReferenceNo.trim().length() > 0){
				    				CCAdetailsVO cCAdetailsVO = new CCAdetailsVO();
				    				cCAdetailsVO.setCompanyCode(companycode);
				    				cCAdetailsVO.setCcaRefNumber(ccaReferenceNo);
				    				cCAdetailsVO.setMailSequenceNumber(mailsequenceNumber);
				    				try{
				    					CCADetail cca = CCADetail.find(cCAdetailsVO);
				    					if(cca != null && !"BD".equals(cca.getBillingStatus()) &&
				    							gpaCode.equals(cca.getRevGpaCode())){
				    						cca.setBillingStatus(billingStatus);
				    					}
				    				}catch(FinderException exception){
				    					log.log(Log.INFO, "FinderException",
				    							ccaReferenceNo);
				    				}
				    			}
				    			/*if(findActualCCA(ccadetails)&&(!(mraBillingDetails.getBlgStatus().equals(billingStatus)))){
				    				log.log(log.INFO,"Actual cca");
				    				ErrorVO	error = new ErrorVO(ChangeStatusException.MRA_ACTUALCCA_EXISTS);
				    				ChangeStatusException changeStatusException= new ChangeStatusException();
				    				changeStatusException.addError(error);
				    				throw changeStatusException;
				    			}*/

				    			//else
				    			if(mraGPABillingDetails != null &&(!"BD".equals(mraGPABillingDetails.getBillingStatus()))){
				    				log.log(log.INFO,"not Actual");

				    				mraGPABillingDetails.setBillingStatus(billingStatus);
				    				mraGPABillingDetails.setRemarks(remarks);
				    				//Added as a part of bug ICRD-18449
				    				if(ccadetails!=null && findActualCCA(ccadetails)){
				    					for(CCADetail ccadetail:ccadetails){
				    						if(!"BD".equalsIgnoreCase(ccadetail.getBillingStatus())
				    								&& gpaCode.equalsIgnoreCase(ccadetail.getRevGpaCode())){
				    							ccadetail.setBillingStatus(billingStatus);
				    						}
				    					}
				    				}
				    				String parameterValue = null;
				    				String parameterCode="mailtracking.mra.dsnauditrequired";
				    				Collection<String> parameterCodes = new ArrayList<String>();
				    				parameterCodes.add(parameterCode);
				    				Map<String, String> systemParameterMap = null;
									try {
										systemParameterMap = new SharedDefaultsProxy()
										.findSystemParameterByCodes(parameterCodes);
									} catch (ProxyException e) {
										// TODO Auto-generated catch block
										e.getMessage();
									}
				    				log.log(Log.FINE, " systemParameterMap ",
											systemParameterMap);
									//call for DSN audit for Change billing status on billing basis
				    				if (systemParameterMap != null) {
				    					parameterValue = systemParameterMap.get (parameterCode);
				    					log.log(Log.FINE,
												" parameterValue--> ",
												parameterValue);
										if ("Y".equals (parameterValue)){
				    						AuditHelper.auditDSNForBillingBasis(documentBillingDetailsVO);
				    					}
				    				}

				    			}else {
				    				if(findActualCCA(ccadetails)){
				    					for(CCADetail ccadetail:ccadetails){
				    						if(!"BD".equalsIgnoreCase(ccadetail.getBillingStatus())
				    								&& gpaCode.equalsIgnoreCase(ccadetail.getRevGpaCode()))
				    						{
				    						ccadetail.setBillingStatus(billingStatus);
				    						}
				    					}
				    				}
				    			}
				    		}
				    		}
				    		log
									.log(
											Log.INFO,
											"COLLECTION OF GPABILLING DETAILS AFTER SETTING",
											documentBillingDetailsVO);

				    	}

				     }
					public static boolean findActualCCA(ArrayList<CCADetail> ccadetails) throws SystemException{
						boolean isActual=false;
						for(CCADetail ccadetail:ccadetails){

							log
									.log(Log.INFO, "CcaType", ccadetail.getMcaType());
						if("A".equals(ccadetail.getMcaType()) && !"BD".equalsIgnoreCase(ccadetail.getBillingStatus())){
							isActual=true;
						}

						}

							return isActual;

					}

					/**
					 *
					 * @author A-3251
					 * @param rateAuditDetailsVO
					 */
					public static Integer  findProrateFactor(RateAuditDetailsVO rateAuditDetailsVO) throws SystemException  {
						log.entering("MRABillingMaster", "findProrateFactor");
						Integer proval = 0;
						try{

							proval = MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().
									getQueryDAO(MODULE_NAME)).findProrateFactor(rateAuditDetailsVO);
					    }catch (PersistenceException persistenceException) {
							  persistenceException.getErrorCode();
							throw new SystemException(persistenceException.getMessage());
						}

					    return proval;
					}


					/**
					 * This method is used for listing UnaccountedDispatches
					 * @author A-2107
					 * @param unaccountedDispatchesFilterVO
					 * @return UnaccountedDispatchesVO
					 * @throws SystemException
					 *
					 */
					public static Page<UnaccountedDispatchesDetailsVO> listUnaccountedDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
					throws SystemException{
						try {
							return MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().getQueryDAO(
											MODULE_NAME)).listUnaccountedDispatches(unaccountedDispatchesFilterVO);
						} catch (PersistenceException persistenceException) {
							persistenceException.getErrorCode();
							throw new SystemException(persistenceException.getMessage());
						}
					}

					/**
					 * @author A-2107
					 * @param unaccountedDispatchesFilterVO
					 * @return  Collection<UnaccountedDispatchesDetailsVO>
					 * @throws SystemException
					 */
					public static Collection<UnaccountedDispatchesDetailsVO> findUnaccountedDispatchesReport(UnaccountedDispatchesFilterVO
							unaccountedDispatchesFilterVO)throws SystemException{
						try{
							return MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().getQueryDAO(
											MODULE_NAME)).findUnaccountedDispatchesReport(unaccountedDispatchesFilterVO);
						}
						catch(PersistenceException persistenceException){
							throw new SystemException(persistenceException.getErrorCode());

						}
					}


					 /**
					 * @author a-3229
					 * @param dsnFilterVO
					 * @return MailProrationLogVO
					 * @throws SystemException
					*/
					public static Collection<MailProrationLogVO> findProrationLogDetails(DSNFilterVO dsnFilterVO)
					throws SystemException {
						try {
							Log log = LogFactory.getLogger("MRA DEFAULTS");
							log.entering("MRABillingMaster","entity");
							return constructDAO().findProrationLogDetails(dsnFilterVO);
						} catch (PersistenceException e) {
							throw new SystemException(e.getErrorCode(), e);
						}
					}


					 /**
					 * @author a-3229
					 * @param dsnFilterVO
					* @return MailProrationLogVO
					 * @throws SystemException
					*/
					public static Collection<ProrationDetailsVO> viewProrationLogDetails(ProrationFilterVO prorationFilterVO)
					throws SystemException {
						try {
							Log log = LogFactory.getLogger("MRA DEFAULTS");
							log.entering("MRABillingMaster","entity");
							return constructDAO().viewProrationLogDetails(prorationFilterVO);
						} catch (PersistenceException e) {
							throw new SystemException(e.getErrorCode(), e);
						}
					}

					 /**
					 * @author a-3229
					 * @param dsnFilterVO
					* @return MailProrationLogVO
					 * @throws SystemException
					*/
					public static Collection<SectorRevenueDetailsVO> findFlownDetails(DSNPopUpVO popUpVO)throws SystemException{
						try{
							return MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().
									getQueryDAO(MODULE_NAME)).findFlownDetails(popUpVO);
					    }catch (PersistenceException persistenceException) {
							  persistenceException.getErrorCode();
							throw new SystemException(persistenceException.getMessage());
						}
					}
					/**
					 * to trigger flown and interline provision accounting
					 * @author A-2565
					 * @param newRateAuditVO
					 * @throws SystemException
					 */
					public void triggerFlownAndInterlineProvAccounting(RateAuditVO newRateAuditVO) throws SystemException {
						log.entering("MRABillingMaster", "triggerFlownAndInterlineProvAccounting");

						try{//triggering flown acounting

							 MRADefaultsDAO.class.cast(
									PersistenceController.getEntityManager().
									getQueryDAO(MODULE_NAME)).triggerFlownAndInterlineProvAccounting(newRateAuditVO);

					    }catch (PersistenceException persistenceException) {
							  persistenceException.getErrorCode();
							throw new SystemException(persistenceException.getMessage());
						}


						log.exiting("MRABillingMaster", "triggerFlownAndInterlineProvAccounting");
					}

					/**
					  * This method is used for printing the Document Statistics
					  * @author A-3429
					  * @param DocumentStatisticsFilterVO
					  * @return Collection<DocumentStatisticsDetailsVO>
					  * @throws SystemException
					  *
					  */
					 public static Collection<DocumentStatisticsDetailsVO>
					 				printDocumentStatisticsReport(DocumentStatisticsFilterVO statisticsFilterVO)
					 throws SystemException{
					 	try {
					 		return MRADefaultsDAO.class.cast(
					 				PersistenceController.getEntityManager().getQueryDAO(
					 						MODULE_NAME)).printDocumentStatisticsReport(statisticsFilterVO);
					 	} catch (PersistenceException persistenceException) {
					 		persistenceException.getErrorCode();
					 		throw new SystemException(persistenceException.getMessage());
					 	}
					 }


				 /**
					 *
					 * @param rateAuditVO
					 * @throws SystemException
					 */
					public MRABillingMaster(RateAuditVO rateAuditVO )throws SystemException{
						populatePK(rateAuditVO);
						populateAttributes(rateAuditVO);
						populateChildren(rateAuditVO);
						try {
							PersistenceController.getEntityManager().persist(this);
						  } catch (CreateException e) {
							e.getErrorCode();
							throw new SystemException(e.getMessage());
						}


					}

					/**
					 * @param rateAuditVO
					 */
					private void populatePK(RateAuditVO rateAuditVO) {
						//Modified by A-7794 as part of MRA revamp
						MRABillingMasterPK mRABillingMasterPK = new MRABillingMasterPK();
						mRABillingMasterPK.setCompanyCode(rateAuditVO.getCompanyCode());
						mRABillingMasterPK.setMailSeqNumber(rateAuditVO.getMailSequenceNumber());
						this.setBillingMasterPK(mRABillingMasterPK);
					}


			    /**
			     * @throws RemoveException
			     * @throws SystemException
			     */
			    public void remove()
			    throws SystemException{
			    	Set<MRABillingDetails> billingDetailVOs = this.getBillingDetails();
			    	if(billingDetailVOs!=null && billingDetailVOs.size()>0){
			    		for(MRABillingDetails billingDetailVO: billingDetailVOs){
			    			billingDetailVO.remove();
			    		}
			    	}
			    	try{
			    	PersistenceController.getEntityManager().remove(this);
			    	}
			    	catch(RemoveException removeException){
			    		throw new SystemException(removeException.getMessage(),removeException);
			    	}
			    }
				/**
				 * @return the dsnaccsta
				 */
			 /*   @Column(name="DSNACCSTA")
				public String getDsnaccsta() {
					return dsnaccsta;
				}*/
				/**
				 * @param dsnaccsta the dsnaccsta to set
				 */
				/*public void setDsnaccsta(String dsnaccsta) {
					this.dsnaccsta = dsnaccsta;
				}*/
				/**
			     * @author A-2391
			     * Finds tand returns the GPA Billing entries available
			     * This includes billed, billable and on hold despatches
			     *
			     * @param gpaBillingEntriesFilterVO
			     * @return Collection<DocumentBillingDetailsVO>
			     * @throws SystemException
			     */

			    public static Page<DocumentBillingDetailsVO> findGPABillingEntries(
			            GPABillingEntriesFilterVO gpaBillingEntriesFilterVO)
			    throws SystemException{

			    	// log.entering(MODULE_NAME,"findGPABillingEntries");
			    	Page<DocumentBillingDetailsVO> documentBillingDetailsVOs=null;

			    	return MRABillingDetails.findGPABillingEntries(gpaBillingEntriesFilterVO);

			    }


			    /**
			     * This method is used for getTotalOfDispatches
			     * @author A-2107
			     * @param unaccountedDispatchesFilterVO
			     * @return UnaccountedDispatchesVO
			     * @throws SystemException
			     *
			     */
			    public static  Collection<UnaccountedDispatchesVO> getTotalOfDispatches(UnaccountedDispatchesFilterVO unaccountedDispatchesFilterVO)
			    throws SystemException{
			    	try {
			    		return MRADefaultsDAO.class.cast(
			    				PersistenceController.getEntityManager().getQueryDAO(
			    						MODULE_NAME)).getTotalOfDispatches(unaccountedDispatchesFilterVO);
			    	} catch (PersistenceException persistenceException) {
			    		persistenceException.getErrorCode();
			    		throw new SystemException(persistenceException.getMessage());
			    	}
			    }



				 /**
				 * @author a-3229
				 * @param popUpVO
				 * @return Collection<MRAAccountingVO>
				 * @throws SystemException
				 */
				public static Page<MRAAccountingVO> findAccountingDetails(DSNPopUpVO popUpVO)throws SystemException{
					log.entering("MRABillingMaster", "findAccountingDetails");
					try{
						return MRADefaultsDAO.class.cast(
								PersistenceController.getEntityManager().
								getQueryDAO(MODULE_NAME)).findAccountingDetails(popUpVO);
				    }catch (PersistenceException persistenceException) {
						  persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}

				 /**
				 * @author a-3229
				 * @param popUpVO
				 * @return Collection<USPSReportingVO>
				 * @throws SystemException
				 */
				public static Collection<USPSReportingVO> findUSPSReportingDetails(DSNPopUpVO popUpVO)throws SystemException{
					log.entering("MRABillingMaster", "findUSPSReportingDetails");
					try{
						return MRADefaultsDAO.class.cast(
								PersistenceController.getEntityManager().
								getQueryDAO(MODULE_NAME)).findUSPSReportingDetails(popUpVO);
				    }catch (PersistenceException persistenceException) {
						  persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}

				/**
				 * @author A-3251
				 * @param dsnRoutingVOs
				 *
				 */
				public static void reProrateDSN(Collection<DSNRoutingVO> dsnRoutingVOs)throws SystemException{
					log.entering("MRABillingMaster", "reProrateDSN");
					try{
						 MRADefaultsDAO.class.cast(
								PersistenceController.getEntityManager().
								getQueryDAO(MODULE_NAME)).reProrateDSN(dsnRoutingVOs);
				    }catch (PersistenceException persistenceException) {
						  persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}
				 /**
				 * @author a-3434
				 * @param outstandingBalanceVO
				 * @return Collection<OutstandingBalanceVO>
				 * @throws SystemException
				 */
				public static Collection<OutstandingBalanceVO> findOutstandingBalances(OutstandingBalanceVO outstandingBalanceVO)
				throws SystemException{
					log.entering("MRABillingMaster", "findOutstandingBalances");
					try{
						return MRADefaultsDAO.class.cast(
								PersistenceController.getEntityManager().
								getQueryDAO(MODULE_NAME)).findOutstandingBalances(outstandingBalanceVO);
				    }catch (PersistenceException persistenceException) {
						  persistenceException.getErrorCode();
						throw new SystemException(persistenceException.getMessage());
					}
				}
    /**
     * 	Method		:	MRABillingMaster.generateInvoiceTK
     *	Added by 	:	A-4809 on 07-Jan-2014
     * 	Used for 	:	ICRD-42160 to generateInvoice for TK
     *	Parameters	:	@param generateInvoiceFilterVo
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Return type	: 	String
     */
	public static String[] generateInvoiceTK(GenerateInvoiceFilterVO generateInvoiceFilterVO)
	throws SystemException{
		String outParameter[] = null;
    	try{
    		outParameter = constructDAO().generateInvoice(generateInvoiceFilterVO);
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return outParameter;
	}
	/**
	 *
	 * 	Method		:	MRABillingMaster.reRateMailbags
	 *	Added by 	:	A-7531
	 *	Parameters	:	@param documentBillingDetailsVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Return type	: 	String
	 */
	public String reRateMailbags(Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs)
			throws SystemException{
		String returnPar ="";
		try{
			returnPar= MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().
					getQueryDAO(MODULE_NAME)).reRateMailbags(documentBillingDetailsVOs);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
		return returnPar;
	}

		/**
		 *
		 * 	Method		:	MRABillingMaster.findRerateBillableMails
		 *	Added by 	:	A-7531
		 *	Parameters	:	@param documentBillingVO
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Return type	: 	Collection<DocumentBillingDetailsVO>
		 */
		public static Collection<DocumentBillingDetailsVO> findRerateBillableMails(DocumentBillingDetailsVO documentBillingVO,
				String companyCode) throws SystemException {
			try{
				return MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findRerateBillableMails(documentBillingVO,companyCode);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}

		}
		/**
		 *
		 * 	Method		:	MRABillingMaster.findRerateInterlineBillableMails
		 *	Added by 	:	A-7531
		 *	Parameters	:	@param documentBillingVO
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Return type	: 	Collection<DocumentBillingDetailsVO>
		 */

		public static Collection<DocumentBillingDetailsVO> findRerateInterlineBillableMails(DocumentBillingDetailsVO documentBillingVO,
				String companyCode) throws SystemException {
			try{
				return MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).findRerateInterlineBillableMails(documentBillingVO,companyCode);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}

		}
		/**
		 * @author A-3429
		 * @param dsnRoutingVOs
		 *
		 */
		public static void saveMRADataForRatingJob(RateAuditVO rateAuditVO)throws SystemException{
			log.entering("MRABillingMaster", "reProrateDSN");
			try{
				 MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).saveMRADataForRatingJob(rateAuditVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}

		/**
		 *
		 * 	Method		:	MRABillingMaster.reRateMailbags
		 *	Added by 	:	A-7531
		 *	Parameters	:	@param documentBillingDetailsVOs
		 *	Parameters	:	@return
		 *	Parameters	:	@throws SystemException
		 *	Return type	: 	String
		 */
		public static String validateAgent(RateAuditVO rateAuditVO, String agentType)
				throws SystemException{
			String returnPar ="";
			try{
				returnPar= MRADefaultsDAO.class.cast(
						PersistenceController.getEntityManager().
						getQueryDAO(MODULE_NAME)).validateAgent(rateAuditVO,agentType);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
			return returnPar;
		}
		/**
		 * 	Method		:	MRABillingMaster.findReproarteMails
		 *	Added by 	:	A-7531 on 09-Nov-2017
		 * 	Used for 	:
		 *	Parameters	:	@param documentBillingVO
		 *	Parameters	:	@param companyCode
		 *	Parameters	:	@return
		 *	Return type	: 	Collection<DocumentBillingDetailsVO>
		 */
		public static Collection<DocumentBillingDetailsVO> findReproarteMails(
				DocumentBillingDetailsVO documentBillingVO) throws SystemException{
			try{
				return constructDAO().findReproarteMails(documentBillingVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}
		/**
		 * 	Method		:	MRABillingMaster.reProrateExceptionMails
		 *	Added by 	:	A-7531 on 09-Nov-2017
		 * 	Used for 	:
		 *	Parameters	:	@param documentBillingDetailsVOs
		 *	Parameters	:	@return
		 *	Return type	: 	String
		 */
		public String reProrateExceptionMails(
				Collection<DocumentBillingDetailsVO> documentBillingDetailsVOs) throws SystemException{
			String returnParameter ="";
			try{
					returnParameter=constructDAO().reProrateExceptionMails(documentBillingDetailsVOs);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
			return returnParameter;
		}

		
		/**
		 * 
		 * @param VOs
		 * @throws SystemException
		 */
		public void voidMailbags(Collection<DocumentBillingDetailsVO> VOs)throws SystemException{
			
			try{
				constructDAO().voidMailbags(VOs);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
		}

	/**
	 * 	
	 * 	Method		:	MRABillingMaster.findMailbagBillingStatus
	 *	Added by 	:	a-8331 on 25-Oct-2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailbagvo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<DocumentBillingDetailsVO>
	 */
    
	public  DocumentBillingDetailsVO findMailbagBillingStatus(MailbagVO mailbagvo) throws SystemException {
		
		try{
			return constructDAO().findMailbagBillingStatus(mailbagvo);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}

/**
 * 	Method		:	MRABillingMaster.findMailbagExistInMRA
 *	Added by 	:	A-4809 on Jan 2, 2019
 * 	Used for 	:
 *	Parameters	:	@param documentBillingDetailsVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Return type	: 	String
 */
		public String findMailbagExistInMRA(DocumentBillingDetailsVO documentBillingDetailsVO) throws SystemException{
			String mailbagPresent ="";
			try{
				mailbagPresent=constructDAO().findMailbagExistInMRA(documentBillingDetailsVO);
		    }catch (PersistenceException persistenceException) {
				  persistenceException.getErrorCode();
				throw new SystemException(persistenceException.getMessage());
			}
			return mailbagPresent;
		}
/**
 * 	Method		:	MRABillingMaster.listGPABillingEntries
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<DocumentBillingDetailsVO>
 */
	public static Page<DocumentBillingDetailsVO> listGPABillingEntries(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO) throws SystemException{
		log.entering(CLASS_NAME, "listGPABillingEntries");
    	return MRABillingDetails.listGPABillingEntries(gpaBillingEntriesFilterVO);
	}
/**
 * 	Method		:	MRABillingMaster.listConsignmentDetails
 *	Added by 	:	A-4809 on Jan 29, 2019
 * 	Used for 	:
 *	Parameters	:	@param gpaBillingEntriesFilterVO
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException
 *	Return type	: 	Page<ConsignmentDocumentVO>
 */
	public static Page<ConsignmentDocumentVO> listConsignmentDetails(GPABillingEntriesFilterVO gpaBillingEntriesFilterVO,Collection<CRAParameterVO> craParameterVOs) throws SystemException{
		log.entering(CLASS_NAME, "listGPABillingEntries");
    	return MRABillingDetails.listConsignmentDetails(gpaBillingEntriesFilterVO,craParameterVOs);
		}

	public static void importResditDataToMRA(MailScanDetailVO mailScanDetailVO) throws SystemException{
		try{
			constructDAO().importResditDataToMRA(mailScanDetailVO);
	    }catch (PersistenceException persistenceException) {
			  persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}

	}
	/**
	 * 	Getter for gpaInvoicDetails
	 *	Added by : A-5219 on 13-Feb-2020
	 * 	Used for :
	 */
	@OneToMany
    @JoinColumns( {
	 @JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
	 @JoinColumn(name = "MALSEQNUM", referencedColumnName = "MALSEQNUM", insertable=false,updatable=false)
	 })
	public Set<MailGPAInvoicDetail> getGpaInvoicDetails() {
		return gpaInvoicDetails;
	}
	/**
	 *  @param gpaInvoicDetails the gpaInvoicDetails to set
	 * 	Setter for gpaInvoicDetails
	 *	Added by : A-5219 on 13-Feb-2020
	 * 	Used for :
	 */
	public void setGpaInvoicDetails(Set<MailGPAInvoicDetail> gpaInvoicDetails) {
		this.gpaInvoicDetails = gpaInvoicDetails;
	}
	
}






