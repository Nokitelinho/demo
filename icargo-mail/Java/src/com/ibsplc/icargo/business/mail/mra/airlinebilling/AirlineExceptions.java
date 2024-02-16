/* AirlineExceptions.java Created on Feb15, 2007
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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsFilterVO;
import com.ibsplc.icargo.business.mail.mra.airlinebilling.vo.AirlineExceptionsVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.airlinebilling.MRAAirlineBillingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-2407
 */

@Entity
@Table(name = "MALMRAARLEXPDTL")
//@Staleable
public class AirlineExceptions {

	private static final String MODULE_NAME = "mail.mra.airlinebilling";

	private AirlineExceptionsPK airlineExceptionsPK;


	private String interlineBlgType;

	private String airlineCode;

	private String despatchSerNo;

	private String receptacleSerNo;

	private int year;

	private String origin;

	private String orgOfficeOfExchange;

	private String destination;

	private String destOfficeOfExchange;

	private String mailSubClass;

	private String mailCategoryCode;

	private String assigneeCode;

	private Calendar assignedDate;

	private String memoCode;

	private String exceptionStatus;

	private double provisionalRate;

	private double provisionalWeight;

	private double reportedWeight;
	
	private Calendar lastUpdateTime;
	
	private String lastUpdateUser;
	
	//Added By Deepthi
	//private String billingBasis;
	//private String consignmentDocumentNumber;
	//private int consignmentSequenceNumber;
	//private String poaCode;
	private double provisionalAmt;
	private double reportedAmt;
	private double reportedRate;
	private String remark;
	
	
	 //Added by A-7929 as part of ICRD-265471
		private String dsnIdr;
		private long mailSeqNumber;
	
	/**
	 * @return Returns the airlineExceptionsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")),
			@AttributeOverride(name = "exceptionCode", column = @Column(name = "EXPCOD")),
			@AttributeOverride(name = "serialNumber", column = @Column(name = "SERNUM")),
			@AttributeOverride(name = "invoiceNumber", column = @Column(name = "INVNUM")),
			@AttributeOverride(name = "clearancePeriod", column = @Column(name = "CLRPRD"))})
	public AirlineExceptionsPK getAirlineExceptionsPK() {
		return airlineExceptionsPK;
	}

	/**
	 * @param airlineExceptionsPK
	 *            The airlineExceptionsPK to set.
	 */
	public void setAirlineExceptionsPK(AirlineExceptionsPK airlineExceptionsPK) {
		this.airlineExceptionsPK = airlineExceptionsPK;
	}
	

	/**
	 * @return the lastUpdateTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
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
	 * @return the lastUpdateUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * @return Returns the airlineCode.
	 */
	@Column(name = "ARLCOD")
	public String getAirlineCode() {
		return airlineCode;
	}

	/**
	 * @param airlineCode
	 *            The airlineCode to set.
	 */
	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	/**
	 * @return Returns the assignedDate.
	 */
	@Column(name = "ASGDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getAssignedDate() {
		return assignedDate;
	}

	/**
	 * @param assignedDate
	 *            The assignedDate to set.
	 */
	public void setAssignedDate(Calendar assignedDate) {
		this.assignedDate = assignedDate;
	}

	/**
	 * @return Returns the assigneeCode.
	 */
	@Column(name = "ASGCOD")
	public String getAssigneeCode() {
		return assigneeCode;
	}

	/**
	 * @param assigneeCode
	 *            The assigneeCode to set.
	 */
	public void setAssigneeCode(String assigneeCode) {
		this.assigneeCode = assigneeCode;
	}



	/**
	 * @return Returns the despatchSerNo.
	 */
	@Column(name = "DSN")
	public String getDespatchSerNo() {
		return despatchSerNo;
	}

	/**
	 * @param despatchSerNo
	 *            The despatchSerNo to set.
	 */
	public void setDespatchSerNo(String despatchSerNo) {
		this.despatchSerNo = despatchSerNo;
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
	 * @return Returns the destOfficeOfExchange.
	 */
	@Column(name = "DSTEXGOFC")
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}

	/**
	 * @param destOfficeOfExchange
	 *            The destOfficeOfExchange to set.
	 */
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}

	/**
	 * @return Returns the exceptionStatus.
	 */
	@Column(name = "EXPSTA")
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus
	 *            The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
	}

	/**
	 * @return Returns the interlineBlgType.
	 */
	@Column(name = "INTBLGTYP")
	public String getInterlineBlgType() {
		return interlineBlgType;
	}




	/**
	 * @param interlineBlgType The interlineBlgType to set.
	 */
	public void setInterlineBlgType(String interlineBlgType) {
		this.interlineBlgType = interlineBlgType;
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
	 * @return Returns the orgOfficeOfExchange.
	 */
	@Column(name = "ORGEXGOFC")
	public String getOrgOfficeOfExchange() {
		return orgOfficeOfExchange;
	}

	/**
	 * @param orgOfficeOfExchange
	 *            The orgOfficeOfExchange to set.
	 */
	public void setOrgOfficeOfExchange(String orgOfficeOfExchange) {
		this.orgOfficeOfExchange = orgOfficeOfExchange;
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
	 * @return Returns the receptacleSerNo.
	 */
	@Column(name = "RSN")
	public String getReceptacleSerNo() {
		return receptacleSerNo;
	}

	/**
	 * @param receptacleSerNo
	 *            The receptacleSerNo to set.
	 */
	public void setReceptacleSerNo(String receptacleSerNo) {
		this.receptacleSerNo = receptacleSerNo;
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
	 * @return Returns the memoCode.
	 */
	@Column(name = "MEMCOD")
	public String getMemoCode() {
		return memoCode;
	}

	/**
	 * @param memoCode
	 *            The memoCode to set.
	 */
	public void setMemoCode(String memoCode) {
		this.memoCode = memoCode;
	}

	/**
	 * @return Returns the provisionalRate.
	 */
	@Column(name = "PVNRAT")
	public double getProvisionalRate() {
		return provisionalRate;
	}

	/**
	 * @param provisionalRate
	 *            The provisionalRate to set.
	 */
	public void setProvisionalRate(double provisionalRate) {
		this.provisionalRate = provisionalRate;
	}

	/**
	 * @return Returns the provisionalWeight.
	 */
	@Column(name = "PVNWGT")
	public double getProvisionalWeight() {
		return provisionalWeight;
	}

	/**
	 * @param provisionalWeight
	 *            The provisionalWeight to set.
	 */
	public void setProvisionalWeight(double provisionalWeight) {
		this.provisionalWeight = provisionalWeight;
	}

	/**
	 * @return Returns the reportedWeight.
	 */
	@Column(name = "RPDWGT")
	public double getReportedWeight() {
		return reportedWeight;
	}

	/**@param reportedWeight
	 * @return void
	 * @exception
	 * @stts Returns the reportedWeight.
	 */
	public void setReportedWeight(double reportedWeight) {
		this.reportedWeight = reportedWeight;
	}

	@Column(name = "DSNIDR") 
	public String getDsnIdr() {
		return dsnIdr;
	}

	public void setDsnIdr(String dsnIdr) {
		this.dsnIdr = dsnIdr;
	}
  
	
	/*public String getMailSeqNumber() {
		return mailSeqNumber;
	}

	public void setMailSeqNumber(String mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}
*/
	/**
	 *
	 *
	 */
	public AirlineExceptions() { // //default constructor

	}
	@Column(name = "MALSEQNUM") 
	public long getMailSeqNumber() {
		return mailSeqNumber;
	}

	public void setMailSeqNumber(long mailSeqNumber) {
		this.mailSeqNumber = mailSeqNumber;
	}

	/**
	 * @exception SystemException
	 * @param airlineExceptionsVO
	 * @return void
	 */
	public AirlineExceptions(AirlineExceptionsVO airlineExceptionsVO)
			throws SystemException { // //default constructor
		populatePK(airlineExceptionsVO);
		populateAttribute(airlineExceptionsVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}
	}

	/*
	 * @param airlineExceptionsVO
	 */
	private void populatePK(AirlineExceptionsVO airlineExceptionsVO) {
		AirlineExceptionsPK airlinePK = new AirlineExceptionsPK();
		airlinePK.setCompanyCode(   airlineExceptionsVO.getCompanyCode());
		airlinePK.setAirlineIdentifier(   airlineExceptionsVO.getAirlineIdentifier());
		airlinePK.setExceptionCode(   airlineExceptionsVO.getExceptionCode());
		airlinePK.setSerialNumber(   airlineExceptionsVO.getSerialNumber());
		airlinePK.setInvoiceNumber(   airlineExceptionsVO.getInvoiceNumber());
		airlinePK.setClearancePeriod(   airlineExceptionsVO.getClearancePeriod());
		this.setAirlineExceptionsPK(airlinePK);

	}

	/*
	 * @param airlineExceptionsVO
	 */
	private void populateAttribute(AirlineExceptionsVO airlineExceptionsVO) {


		this.setDespatchSerNo(airlineExceptionsVO.getDespatchSerNo());
		this.setProvisionalRate(airlineExceptionsVO.getProvRate());
		this.setProvisionalWeight(airlineExceptionsVO.getProvWeight());
		this.setReportedWeight(airlineExceptionsVO.getRptdWeight());
		this.setMemoCode(airlineExceptionsVO.getMemCode());
		this.setAssigneeCode(airlineExceptionsVO.getAssigneeCode());
	}

	/**
	 * @param pk
	 * @return static  AirlineExceptions
	 * @exception SystemException
	 * @exception FinderException
	 */
	public static AirlineExceptions find(AirlineExceptionsPK pk)
			throws SystemException, FinderException {
		AirlineExceptionsPK airlinePK = new AirlineExceptionsPK();
		airlinePK.setCompanyCode(   pk.getCompanyCode());
		airlinePK.setAirlineIdentifier(   pk.getAirlineIdentifier());
		airlinePK.setExceptionCode(   pk.getExceptionCode());
		airlinePK.setSerialNumber(   pk.getSerialNumber());
		airlinePK.setInvoiceNumber(   pk.getInvoiceNumber());
		airlinePK.setClearancePeriod(   pk.getClearancePeriod());
		return PersistenceController.getEntityManager().find(
				AirlineExceptions.class, airlinePK);
	}

	/**
	 * @param airlineExceptionsVO
	 * @return void
	 * @exception
	 */
	public void update(AirlineExceptionsVO airlineExceptionsVO) {
		AirlineExceptionsPK airlinePK = new AirlineExceptionsPK();
		airlinePK.setCompanyCode(   airlineExceptionsVO.getCompanyCode());
		airlinePK.setAirlineIdentifier(   airlineExceptionsVO
				.getAirlineIdentifier());
		airlinePK.setExceptionCode(airlineExceptionsVO.getExceptionCode());
		airlinePK.setSerialNumber(airlineExceptionsVO.getSerialNumber());
		airlinePK.setInvoiceNumber(airlineExceptionsVO.getInvoiceNumber());
		airlinePK.setClearancePeriod(airlineExceptionsVO.getClearancePeriod());

		this.setAirlineExceptionsPK(airlinePK);

		this.setDespatchSerNo(airlineExceptionsVO.getDespatchSerNo());
		this.setProvisionalRate(airlineExceptionsVO.getProvRate());
		this.setProvisionalWeight(airlineExceptionsVO.getProvWeight());
		this.setReportedWeight(airlineExceptionsVO.getRptdWeight());
		this.setMemoCode(airlineExceptionsVO.getMemCode());
		this.setAssigneeCode(airlineExceptionsVO.getAssigneeCode());
		//if(airlineExceptionsVO.getAssignedDate()!=null){
		this.setAssignedDate(airlineExceptionsVO.getAssignedDate());
		//}
		// PersistenceController.getEntityManager().modify(this);
		this.setLastUpdateUser(airlineExceptionsVO.getLastUpdatedUser());
		this.setLastUpdateTime(airlineExceptionsVO.getLastUpdatedTime());
	}

	/**
	 * @param airlineExceptionsFilterVO
	 *            The reportedWeight to set.
	 * @return static Collection<AirlineExceptionsVO>
	 * @exception  SystemException
	 */
	public static Page<AirlineExceptionsVO> displayAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		 //System.out.println("displayAirlineExceptions Entry");
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			return mRAAirlineBillingDAO
					.displayAirlineExceptions(airlineExceptionsFilterVO);
		} catch (PersistenceException persistenceException) {
			//System.out.println("Exception caught "+persistenceException);
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	 /*Added by A-2391*/
	/**
	 * @param airlineExceptionsFilterVO
	 *            The reportedWeight to set.
	 * @return static Collection<AirlineExceptionsVO>
	 * @exception  SystemException
	 */
	public static Collection<AirlineExceptionsVO> findAirlineExceptions(
			AirlineExceptionsFilterVO airlineExceptionsFilterVO)
			throws SystemException {
		try {
			MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
					.cast(PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME));
			return mRAAirlineBillingDAO
					.findAirlineExceptions(airlineExceptionsFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
    /**
     *
     * @param airlineExceptionsFilterVO
     * @return Collection<AirlineExceptionsVO>
     * @throws SystemException
     *
     */
    public static Collection<AirlineExceptionsVO> printExceptionReportDetail(
    		AirlineExceptionsFilterVO airlineExceptionsFilterVO) throws SystemException {
        try {
        	MRAAirlineBillingDAO mRAAirlineBillingDAO = MRAAirlineBillingDAO.class
			.cast(PersistenceController.getEntityManager().getQueryDAO(
					MODULE_NAME));
         	return mRAAirlineBillingDAO.printExceptionReportDetail(airlineExceptionsFilterVO);
        } catch(PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode(), persistenceException);
        }
    }
	/**
	 * @return the billingBasis
	 */
   /* @Column(name = "BLGBAS")
	public String getBillingBasis() {
		return billingBasis;
	}*/

	/**
	 * @param billingBasis the billingBasis to set
	 */
	/*public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}*/

	/**
	 * @return the consignmentDocumentNumber
	 */
	/*@Column(name = "CSGDOCNUM")
	public String getConsignmentDocumentNumber() {
		return consignmentDocumentNumber;
	}*/

	/**
	 * @param consignmentDocumentNumber the consignmentDocumentNumber to set
	 */
	/*public void setConsignmentDocumentNumber(String consignmentDocumentNumber) {
		this.consignmentDocumentNumber = consignmentDocumentNumber;
	}*/
	/**
	 * @return the consignmentSequenceNumber
	 */
	/*@Column(name = "CSGSEQNUM")
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}*/

	/**
	 * @return the provisionalAmt
	 */
	//@Column(name = "PVNAMT")
	@Column(name = "PVNAMTLSTCUR")    //Modified by A-7929 as part of ICRD-265471
	public double getProvisionalAmt() {
		return provisionalAmt;
	}

	/**
	 * @param provisionalAmt the provisionalAmt to set
	 */
	public void setProvisionalAmt(double provisionalAmt) {
		this.provisionalAmt = provisionalAmt;
	}

	/**
	 * @return the reportedAmt
	 */
	//@Column(name = "RPDAMT")
	@Column(name = "RPDAMTLSTCUR")   //Modified by A-7929 as part of ICRD-265471
	public double getReportedAmt() {
		return reportedAmt;
	}

	/**
	 * @param reportedAmt the reportedAmt to set
	 */
	public void setReportedAmt(double reportedAmt) {
		this.reportedAmt = reportedAmt;
	}

	/**
	 * @return the reportedRate
	 */
	@Column(name = "RPDRAT")
	public double getReportedRate() {
		return reportedRate;
	}

	/**
	 * @param reportedRate the reportedRate to set
	 */
	public void setReportedRate(double reportedRate) {
		this.reportedRate = reportedRate;
	}

	/**
	 * @param consignmentSequenceNumber the consignmentSequenceNumber to set
	 */
	/*public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}*/

	/**
	 * @return the poaCode
	 */
	/*@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}*/

	/**
	 * @param poaCode the poaCode to set
	 */
	/*public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}*/
	
	/**
	 * @return the remark
	 */
	@Column(name = "RMK")
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * to trigger Accept Button Accounting at despatch level
	 * @author A-3429
	 * @param rejectionVO
	 * @throws SystemException
	 */
	public static void triggerAcceptDSNAccounting(
			AirlineExceptionsVO airlineExceptionVO) throws SystemException {
		try {
			MRAAirlineBillingDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							MODULE_NAME)).triggerAcceptDSNAccounting(
									airlineExceptionVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
}
