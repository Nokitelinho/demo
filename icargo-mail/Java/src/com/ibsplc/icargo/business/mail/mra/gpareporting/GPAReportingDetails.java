/*
 * GPAReportingDetails.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

import java.util.Calendar;
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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFlightDetailsVO;
//import com.ibsplc.icargo.framework.util.time.LocalDate;
//import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 * 
 * 
 */
@Entity
@Table(name = "MTKGPARPTDTL")
public class GPAReportingDetails {

	/**
	 * Revision History 0.1-----A-1556 initial Draft 0.2-----A-3447---Updated Pk
	 */

	private static final String MTK_MRA_GPA_RPT_GPA_DTL = "MTK_MRA_GPA_RPT_GPA_DTL";

	private Log log = LogFactory.getLogger("MAILTRACKING MRA GPAReportingDetails");

	private GPAReportingDetailsPK gpaReportingDetailsPK;

	private String countryCode;

	/**
	 * dsnDate
	 */
	private Calendar dsnDate;

	/**
	 * originOfficeExchange
	 */
	private String originOfficeExchange;

	/**
	 * destinationOfficeExchange
	 */
	private String destinationOfficeExchange;

	/**
	 * mailCategory
	 */
	private String mailCategory;

	/**
	 * mailSubClass
	 */
	private String mailSubClass;

	/**
	 * year
	 */
	private String year;

	/**
	 * dsnNumber
	 */
	private String dsnNumber;

	/**
	 * noOfMailBags
	 */
	private int noOfMailBags;

	/**
	 * weight
	 */
	private double weight;

	/**
	 * rate
	 */
	private double rate;

	/**
	 * amount
	 */
	private double amount;

	/**
	 * tax
	 */
	private double tax;

	/**
	 * total
	 */
	private double total;

	/**
	 * reportingStatus
	 */
	private String reportingStatus;

	/**
	 * gpaReportingFlightDetailses
	 */

	private String basisType;

	private String lastUpdateUser;

	private Calendar lastUpdateTime;

	private Set<GPAReportingFlightDetails> gpaReportingFlightDetailses;

	private String reportingFrom;

	private String reportingTo;

	private String receptacleSerialNumber;

	private String registeredOrInsuredIndicator;

	private String highestNumberedReceptacle;

	private String mailActualSubClass;

	/**
	 * @author A-3447
	 */
	/**
	 * cca ref no
	 */
	private String ccaReferenceNumber;
	
	
	private String reportingFromString;


	private String reportingToString;
	  
	
	
	
	
	
	
	
	/**
	 * consignment seq no
	 */

	private int consignmentSequenceNumber;

	/**
	 * consignmentDocumentNumber
	 */

	private String consignmentDocumentNumber;

	/**
	 * accounting status
	 */

	private String accountingStatus;

	/**
	 * txn Id
	 */

	private String accountingTransactionId;

	/**
	 * Constructor
	 * 
	 */
	public GPAReportingDetails() {

	}

	/**
	 * @return the accountingStatus
	 */
	@Column(name = "ACCSTA")
	public String getAccountingStatus() {
		return accountingStatus;
	}

	/**
	 * @param accountingStatus
	 *            the accountingStatus to set
	 */
	public void setAccountingStatus(String accountingStatus) {
		this.accountingStatus = accountingStatus;
	}

	/**
	 * @return the accountingTransactionId
	 */
	@Column(name = "ACCTXNIDR")
	public String getAccountingTransactionId() {
		return accountingTransactionId;
	}

	/**
	 * @param accountingTransactionId
	 *            the accountingTransactionId to set
	 */

	public void setAccountingTransactionId(String accountingTransactionId) {
		this.accountingTransactionId = accountingTransactionId;
	}

	/**
	 * @return the ccaReferenceNumber
	 */
	@Column(name = "CCAREFNUM")
	public String getCcaReferenceNumber() {
		return ccaReferenceNumber;
	}

	/**
	 * @param ccaReferenceNumber
	 *            the ccaReferenceNumber to set
	 */
	public void setCcaReferenceNumber(String ccaReferenceNumber) {
		this.ccaReferenceNumber = ccaReferenceNumber;
	}

	/**
	 * @return the consignmentDocumentNumber
	 */
	@Column(name = "CSGDOCNUM")
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}

	/**
	 * @param consignmentDocumentNumber
	 *            the consignmentDocumentNumber to set
	 */
	public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}

	/**
	 * @return the consignmentSequenceNumber
	 */
	@Column(name = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSequenceNumber
	 *            the consignmentSequenceNumber to set
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	/**
	 * @return Returns the amount.
	 */
	@Column(name = "AMT")
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            The amount to set.
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}

	/**
	 * @return Returns the countryCode.
	 */
	@Column(name = "CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the destinationOfficeExchange.
	 */
	@Column(name = "DSTEXGOFC")
	public String getDestinationOfficeExchange() {
		return destinationOfficeExchange;
	}

	/**
	 * @param destinationOfficeExchange
	 *            The destinationOfficeExchange to set.
	 */
	public void setDestinationOfficeExchange(String destinationOfficeExchange) {
		this.destinationOfficeExchange = destinationOfficeExchange;
	}

	/**
	 * @return Returns the dsnDate.
	 */
	@Column(name = "DSNDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getDsnDate() {
		return dsnDate;
	}

	/**
	 * @param dsnDate
	 *            The dsnDate to set.
	 */
	public void setDsnDate(Calendar dsnDate) {
		this.dsnDate = dsnDate;
	}

	/**
	 * @return Returns the dsnNumber.
	 */
	@Column(name = "DSN")
	public String getDsnNumber() {
		return dsnNumber;
	}

	/**
	 * @param dsnNumber
	 *            The dsnNumber to set.
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}

	/**
	 * @return Returns the gpaReportingFlightDetailses.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "BLGBAS", referencedColumnName = "BLGBAS", insertable = false, updatable = false),
			@JoinColumn(name = "BILIDR", referencedColumnName = "BILIDR", insertable = false, updatable = false),
			@JoinColumn(name = "SERNUM", referencedColumnName = "SERNUM", insertable = false, updatable = false) })
	public Set<GPAReportingFlightDetails> getGpaReportingFlightDetailses() {
		return gpaReportingFlightDetailses;
	}

	/**
	 * @param flightDetails
	 *            The flightDetails to set.
	 */
	public void setGpaReportingFlightDetailses(
			Set<GPAReportingFlightDetails> flightDetails) {
		this.gpaReportingFlightDetailses = flightDetails;
	}

	/**
	 * @return Returns the gpaReportingDetailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS")),
			@AttributeOverride(name = "billingIdentifier", column = @Column(name = "BILIDR")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SERNUM")) })
	public GPAReportingDetailsPK getGpaReportingDetailsPK() {
		return gpaReportingDetailsPK;
	}

	/**
	 * @param gpaReportingDetailsPK
	 *            The gpaReportingDetailsPK to set.
	 */
	public void setGpaReportingDetailsPK(
			GPAReportingDetailsPK gpaReportingDetailsPK) {
		this.gpaReportingDetailsPK = gpaReportingDetailsPK;
	}

	/**
	 * @return Returns the mailCategory.
	 */
	@Audit(name = "mailCategoryCode")
	@Column(name = "MALCTGCOD")
	public String getMailCategory() {
		return mailCategory;
	}

	/**
	 * @param mailCategory
	 *            The mailCategory to set.
	 */
	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	/**
	 * @return Returns the mailSubClass.
	 */
	@Column(name = "MALSUBCLS")
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
	 * @return Returns the noOfMailBags.
	 */
	@Column(name = "NUMMALBAG")
	public int getNoOfMailBags() {
		return noOfMailBags;
	}

	/**
	 * @param noOfMailBags
	 *            The noOfMailBags to set.
	 */
	public void setNoOfMailBags(int noOfMailBags) {
		this.noOfMailBags = noOfMailBags;
	}

	/**
	 * @return Returns the originOfficeExchange.
	 */
	@Column(name = "ORGEXGOFC")
	public String getOriginOfficeExchange() {
		return originOfficeExchange;
	}

	/**
	 * @param originOfficeExchange
	 *            The originOfficeExchange to set.
	 */
	public void setOriginOfficeExchange(String originOfficeExchange) {
		this.originOfficeExchange = originOfficeExchange;
	}

	/**
	 * @return Returns the rate.
	 */
	@Column(name = "RAT")
	public double getRate() {
		return rate;
	}

	/**
	 * @param rate
	 *            The rate to set.
	 */
	public void setRate(double rate) {
		this.rate = rate;
	}

	/**
	 * @return Returns the reportingStatus.
	 */
	@Column(name = "MALSTA")
	public String getReportingStatus() {
		return reportingStatus;
	}

	/**
	 * @param reportingStatus
	 *            The reportingStatus to set.
	 */
	public void setReportingStatus(String reportingStatus) {
		this.reportingStatus = reportingStatus;
	}

	/**
	 * @return Returns the tax.
	 */
	@Column(name = "TAX")
	public double getTax() {
		return tax;
	}

	/**
	 * @param tax
	 *            The tax to set.
	 */
	public void setTax(double tax) {
		this.tax = tax;
	}

	/**
	 * @return Returns the total.
	 */
	@Column(name = "TOTAMT")
	public double getTotal() {
		return total;
	}

	/**
	 * @param total
	 *            The total to set.
	 */
	public void setTotal(double total) {
		this.total = total;
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
	 * @return Returns the year.
	 */
	@Column(name = "YER")
	public String getYear() {
		return year;
	}

	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "BASTYP")
	public String getBasisType() {
		return basisType;
	}

	/**
	 * 
	 * @param basisType
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	/**
	 * @return Returns the lastUpdateTime.
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 * @param lastUpdateTime
	 *            The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser
	 *            The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the reportingFrom.
	 */
	@Column(name = "REPPRDFRMSTR")
	public String  getReportingFrom() {
		return reportingFrom;
	}

	/**
	 * @param reportingFrom
	 *            The reportingFrom to set.
	 */
	public void setReportingFrom(String reportingFrom) {
		this.reportingFrom = reportingFrom;
	}

	/**
	 * @return Returns the reportingTo.
	 */
	@Column(name = "REPPRDTOOSTR")
	public String getReportingTo() {
		return reportingTo;
	}

	/**
	 * @param reportingTo
	 *            The reportingTo to set.
	 */
	public void setReportingTo(String reportingTo) {
		this.reportingTo = reportingTo;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "RSN")
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * 
	 * @param receptacleSerialNumber
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "REGIND")
	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}

	/**
	 * 
	 * @param registeredOrInsuredIndicator
	 */
	public void setRegisteredOrInsuredIndicator(
			String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
	}

	/**
	 * 
	 * @return
	 */
	@Column(name = "HSN")
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}

	/**
	 * 
	 * @param highestNumberedReceptacle
	 */
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}

	/**
	 * @return Returns the mailActualSubClass.
	 */
	@Column(name = "MALACTSUBCLS")
	public String getMailActualSubClass() {
		return mailActualSubClass;
	}

	/**
	 * @param mailActualSubClass
	 *            The mailActualSubClass to set.
	 */
	public void setMailActualSubClass(String mailActualSubClass) {
		this.mailActualSubClass = mailActualSubClass;
	}

	/**
	 * 
	 * @param companyCode
	 * @param poaCode
	 * @param billingBasis
	 * @param reportingFromString
	 * @param reportingToString
	 * @param sequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPAReportingDetails find(String companyCode, String poaCode,
			String billingBasis, String billingIdentifier, int sequenceNumber)
			throws SystemException, FinderException {	
		
		return PersistenceController.getEntityManager().find(
				GPAReportingDetails.class,
				new GPAReportingDetailsPK(companyCode, poaCode, billingBasis,
						billingIdentifier, sequenceNumber));
	}

	

	/**
	 * 
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	public GPAReportingDetails(GPAReportingDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "GPAReportingDetails");
		if(("").equals(gpaReportingDetailsVO.getBillingBasis())) {
			gpaReportingDetailsVO.setBillingBasis("0");
		}
		populatePK(gpaReportingDetailsVO);
		populateAttributes(gpaReportingDetailsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
		//populateChildren(gpaReportingDetailsVO);
		log.exiting("GPAReportingDetails", "GPAReportingDetails");
	}

	/**
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	private void populatePK(GPAReportingDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "populatePK");
		this.setGpaReportingDetailsPK(new GPAReportingDetailsPK(
				gpaReportingDetailsVO.getCompanyCode(), gpaReportingDetailsVO
						.getPoaCode(), gpaReportingDetailsVO.getBillingBasis(),
				gpaReportingDetailsVO.getBillingIdentifier(),
				gpaReportingDetailsVO.getSequenceNumber()));
		log.exiting("GPAReportingDetails", "populatePK");
	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("GPAReportingDetails", "remove");
		removeChildren();
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
		log.exiting("GPAReportingDetails", "remove");
	}

	/**
	 * @throws SystemException
	 * 
	 */
	private void removeChildren() throws SystemException {
		log.entering("GPAReportingDetails", "removeChildren");
		removeGPAReportingFlightDetails();
		log.exiting("GPAReportingDetails", "removeChildren");
	}

	/**
	 * @throws SystemException
	 * 
	 */
	private void removeGPAReportingFlightDetails() throws SystemException {
		log.entering("GPAReportingDetails", "removeGPAReportingFlightDetails");
		if (this.gpaReportingFlightDetailses != null) {
			for (GPAReportingFlightDetails gpaReportingFlightDetails : this
					.getGpaReportingFlightDetailses()) {
				gpaReportingFlightDetails.remove();
			}
		}
		log.exiting("GPAReportingDetails", "removeGPAReportingFlightDetails");

	}

	/**
	 * 
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	public void update(GPAReportingDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "update");
		populateAttributes(gpaReportingDetailsVO);
		populateChildren(gpaReportingDetailsVO);
		log.exiting("GPAReportingDetails", "update");
	}

	/**
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	private void populateChildren(GPAReportingDetailsVO gpaReportingDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "populateChildren");
		if (gpaReportingDetailsVO.getGpaReportingFlightDetailsVOs() != null) {
			populateFlightDetails(gpaReportingDetailsVO);
		}
		log.exiting("GPAReportingDetails", "populateChildren");

	}

	/**
	 * @param gpaReportingDetailsVO
	 * @throws SystemException
	 */
	private void populateFlightDetails(
			GPAReportingDetailsVO gpaReportingDetailsVO) throws SystemException {
		log.entering("GPAReportingDetails", "populateFlightDetails");
		for (GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO : gpaReportingDetailsVO
				.getGpaReportingFlightDetailsVOs()) {
			gpaReportingFlightDetailsVO.setCompanyCode(this
					.getGpaReportingDetailsPK().getCompanyCode());
			gpaReportingFlightDetailsVO.setPoaCode(this
					.getGpaReportingDetailsPK().getPoaCode());
			gpaReportingFlightDetailsVO.setBillingBasis(this
					.getGpaReportingDetailsPK().getBillingBasis());
			//gpaReportingFlightDetailsVO.setReportingFrom(new LocalDate(
					//LocalDate.NO_STATION, Location.NONE, this
							//.getReportingFrom(), false));
			gpaReportingFlightDetailsVO.setBillingIdentifier(this
					.getGpaReportingDetailsPK().getBillingIdentifier());
			//gpaReportingFlightDetailsVO.setReportingTo(new LocalDate(
				//	LocalDate.NO_STATION, Location.NONE, this.getReportingTo(),
				//	false));
			gpaReportingFlightDetailsVO.setSequenceNumber(this
					.getGpaReportingDetailsPK().getSequenceNumber());
			if (GPAReportingDetailsVO.OPERATION_FLAG_INSERT
					.equals(gpaReportingDetailsVO.getOperationFlag())) {
				gpaReportingFlightDetailsVO
						.setOperationFlag(GPAReportingDetailsVO.OPERATION_FLAG_INSERT);
			}
			if (GPAReportingDetailsVO.OPERATION_FLAG_INSERT
					.equals(gpaReportingFlightDetailsVO.getOperationFlag())) {
				insertFlightDetails(gpaReportingFlightDetailsVO);
			} else if (GPAReportingDetailsVO.OPERATION_FLAG_UPDATE
					.equals(gpaReportingFlightDetailsVO.getOperationFlag())) {
				updateFlightDetails(gpaReportingFlightDetailsVO);
			} else if (GPAReportingDetailsVO.OPERATION_FLAG_DELETE
					.equals(gpaReportingFlightDetailsVO.getOperationFlag())) {
				removeFlightDetails(gpaReportingFlightDetailsVO);
			}
		}
		log.exiting("GPAReportingDetails", "populateFlightDetails");
	}

	/**
	 * @param gpaReportingFlightDetailsVO
	 * @throws SystemException
	 */
	private void removeFlightDetails(
			GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "removeFlightDetails");
		GPAReportingFlightDetails flightDetails = findGPAReportingFlightDetails(gpaReportingFlightDetailsVO);
		flightDetails.remove();
		log.exiting("GPAReportingDetails", "removeFlightDetails");
	}

	/**
	 * @param gpaReportingFlightDetailsVO
	 * @return
	 */
	private GPAReportingFlightDetails findGPAReportingFlightDetails(
			GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO) {
		return findGPAReportingFlightDetails(gpaReportingFlightDetailsVO
				.getFlightSequenceNumber());
	}

	/**
	 * @param flightSequenceNumber
	 * @return
	 */
	private GPAReportingFlightDetails findGPAReportingFlightDetails(
			int flightSequenceNumber) {
		log.entering("GPAReportingDetails", "findGPAReportingFlightDetails");
		GPAReportingFlightDetails gpaReportingFlightDetails = null;
		for (GPAReportingFlightDetails flightDetails : this
				.getGpaReportingFlightDetailses()) {
			if (flightDetails.getGpaReportingFlightDetailsPK()
					.getFlightSequenceNumber() == flightSequenceNumber) {
				gpaReportingFlightDetails = flightDetails;
				break;
			}
		}
		log.exiting("GPAReportingDetails", "findGPAReportingFlightDetails");
		return gpaReportingFlightDetails;
	}

	/**
	 * @param gpaReportingFlightDetailsVO
	 */
	private void updateFlightDetails(
			GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO) {
		log.entering("GPAReportingDetails", "updateFlightDetails");
		GPAReportingFlightDetails reportingFlightDetails = findGPAReportingFlightDetails(gpaReportingFlightDetailsVO);
		reportingFlightDetails.update(gpaReportingFlightDetailsVO);
		log.exiting("GPAReportingDetails", "updateFlightDetails");
	}

	/**
	 * @param gpaReportingFlightDetailsVO
	 * @throws SystemException
	 */
	private void insertFlightDetails(
			GPAReportingFlightDetailsVO gpaReportingFlightDetailsVO)
			throws SystemException {
		log.entering("GPAReportingDetails", "insertFlightDetails");
		GPAReportingFlightDetails reportingFlightDetails = new GPAReportingFlightDetails(
				gpaReportingFlightDetailsVO);
		if (this.getGpaReportingFlightDetailses() == null) {
			this
					.setGpaReportingFlightDetailses(new HashSet<GPAReportingFlightDetails>());
		}
		this.getGpaReportingFlightDetailses().add(reportingFlightDetails);
		log.exiting("GPAReportingDetails", "insertFlightDetails");

	}

	/**
	 * @param gpaReportingDetailsVO
	 */
	private void populateAttributes(GPAReportingDetailsVO gpaReportingDetailsVO) {
		log.entering("GPAReportingDetails", "--populateAttributes---");
		this.setBasisType(gpaReportingDetailsVO.getBasistype());
		this.setDsnDate(gpaReportingDetailsVO.getDsnDate());
		this.setDsnNumber(gpaReportingDetailsVO.getDsnNumber());
		this.setCountryCode(gpaReportingDetailsVO.getCountryCode());
		this.setOriginOfficeExchange(gpaReportingDetailsVO
				.getOriginOfficeExchange());
		this.setDestinationOfficeExchange(gpaReportingDetailsVO
				.getDestinationOfficeExchange());
		this.setMailCategory(gpaReportingDetailsVO.getMailCategory());
		this.setMailSubClass(gpaReportingDetailsVO.getMailSubClass());
		this.setMailActualSubClass(gpaReportingDetailsVO
				.getActualMailSubClass());
		this.setYear(gpaReportingDetailsVO.getYear());
		this.setNoOfMailBags(gpaReportingDetailsVO.getNoOfMailBags());
		this.setWeight(gpaReportingDetailsVO.getWeight());
		this.setRate(gpaReportingDetailsVO.getRate());
		// added
		this.setAmount(gpaReportingDetailsVO.getWeight()
				* gpaReportingDetailsVO.getRate());
		this.setTotal(gpaReportingDetailsVO.getWeight()
				* gpaReportingDetailsVO.getRate());

		if (gpaReportingDetailsVO.getAmount() != null) {
			this
					.setAmount(gpaReportingDetailsVO.getAmount()
							.getRoundedAmount());
		}

		this.setTax(gpaReportingDetailsVO.getTax());
		if (gpaReportingDetailsVO.getTotal() != null) {
			this.setTotal(gpaReportingDetailsVO.getTotal().getRoundedAmount());
		}
		this.setReportingStatus(gpaReportingDetailsVO.getReportingStatus());
		this.setLastUpdateUser(gpaReportingDetailsVO.getLastUpdateUser());
		this.setLastUpdateTime(gpaReportingDetailsVO.getLastUpdateTime());
		this.setReportingFrom(gpaReportingDetailsVO.getReportingFromString());
		this.setReportingTo(gpaReportingDetailsVO.getReportingToString());
		this.setHighestNumberedReceptacle(gpaReportingDetailsVO
				.getHighestNumberedReceptacle());
		this.setRegisteredOrInsuredIndicator(gpaReportingDetailsVO
				.getRegisteredOrInsuredIndicator());
		this.setReceptacleSerialNumber(gpaReportingDetailsVO
				.getReceptacleSerialNumber());
	
		
		this.setConsignmentSequenceNumber(gpaReportingDetailsVO.getConsignmentSeqNo());
		if(gpaReportingDetailsVO.getConsignementDocNo()!=null){
		this.setConsignmentDocumentNumber(gpaReportingDetailsVO.getConsignementDocNo());
		
		}
		log.exiting("GPAReportingDetails", "populateAttributes");
	}

	/**
	 * @param gpaReportFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<GPAReportingDetailsVO> findGPAReportingDetails(
			GPAReportingFilterVO gpaReportFilterVO) throws SystemException {
		try {
			return constructDAO().findGPAReportingDetails(gpaReportFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	private static MRAGPAReportingDAO constructDAO() throws SystemException {
		try {
			return MRAGPAReportingDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(
							"mail.mra.gpareporting"));
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param filterVo
	 * @throws SystemException
	 * @throws MailTrackingMRABusinessException
	 */
	public static void processGpaReport(GPAReportingFilterVO filterVo)
			throws SystemException, MailTrackingMRABusinessException {
		String outParameter = null;
		try {
			outParameter = constructDAO().processGpaReport(filterVo);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		if (!("OK").equalsIgnoreCase(outParameter)) {
			MailTrackingMRABusinessException mailBusExp = new MailTrackingMRABusinessException();
			mailBusExp
					.addError(new ErrorVO(
							MailTrackingMRABusinessException.MTK_MRA_GPAREPORTING_PROCESS_STATUS_NOTOK));
			throw mailBusExp;
		}
	}

}
