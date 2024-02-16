/*
 * GPAReportingClaimDetails.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpareporting;

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

import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingClaimDetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.GPAReportingFilterVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1453
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */


@Entity
@Table(name = "MTKGPARPTCLM")

public class GPAReportingClaimDetails {

	private  Log log = LogFactory.getLogger("MRA GPAREPORTING");
	private static final String MODULE_NAME="mail.mra.gpareporting";

	private GPAReportingClaimDetailsPK gpaReportingClaimDetailsPK;


	/**
	 * exceptionCode
	 */
	private String exceptionCode;
	/**
	 * assignedUser
	 */
	private String assignedUser;
	/**
	 * assignedDate
	 */
	private Calendar assignedDate;
	/**
	 * rsolvedDate
	 */
	private Calendar resolvedDate;
	/**
	 * basisType
	 */
	private String basisType;
	/**
	 * year
	 */
	private String year;
	/**
	 * originOfficeOfExchange
	 */
	private String originOfficeOfExchange;
	/**
	 * destOfficeOfExchange
	 */
	private String destOfficeOfExchange;
	/**
	 * mailSubClass
	 */
	private String mailSubClass;
	/**
	 * mailCategoryCode
	 */
	private String mailCategoryCode;
	/**
	 * dsnNumber
	 */
	private String dsnNumber;
	/**
	 * actualWeight
	 */
	private double actualWeight;
	/**
	 * reportedWeight
	 */
	private double reportedWeight;
	/**
	 * actualRate
	 */
	private double actualRate;
	/**
	 * reportedRate
	 */
	private double reportedRate;

	/**
	 * lastUpdatedUser
	 */
	private String lastUpdatedUser;
	/**
	 * lastUpdatedTime
	 */
	private Calendar lastUpdatedTime;
	/**
	 * reporting period From
	 */
	private Calendar reportingPeriodFrom;
	/**
	 * reporting period To
	 */
	private Calendar reportingPeriodTo;

	/**
	 * ccaRefNum
	 */
	private String ccaRefNum;

	/**
	 * actualCharge
	 */
	private double actualCharge;

	/**
	 * reportedCharge
	 */
	private double reportedCharge;

	/**
	 * @return the actualCharge
	 */
	@Column(name= "ACTCHG")
	public double getActualCharge() {
		return actualCharge;
	}


	/**
	 * @param actualCharge the actualCharge to set
	 */
	public void setActualCharge(double actualCharge) {
		this.actualCharge = actualCharge;
	}


	/**
	 * @return the ccaRefNum
	 */
	@Column(name= "CCAREFNUM")
	public String getCcaRefNum() {
		return ccaRefNum;
	}


	/**
	 * @param ccaRefNum the ccaRefNum to set
	 */
	public void setCcaRefNum(String ccaRefNum) {
		this.ccaRefNum = ccaRefNum;
	}


	/**
	 * @return the reportedCharge
	 */
	@Column(name= "RPTCHG")
	public double getReportedCharge() {
		return reportedCharge;
	}


	/**
	 * @param reportedCharge the reportedCharge to set
	 */
	public void setReportedCharge(double reportedCharge) {
		this.reportedCharge = reportedCharge;
	}


	/**
	 * Default Constructor
	 *
	 */
	public GPAReportingClaimDetails() {

	}


	/**
	 * @return Returns the reportingPeriodFrom.
	 */
	@Column (name="REPPRDFRM")
	@Temporal(TemporalType.DATE)
	public Calendar getReportingPeriodFrom() {
		return reportingPeriodFrom;
	}



	/**
	 * @param reportingPeriodFrom The reportingPeriodFrom to set.
	 */
	public void setReportingPeriodFrom(Calendar reportingPeriodFrom) {
		this.reportingPeriodFrom = reportingPeriodFrom;
	}



	/**
	 * @return Returns the reportingPeriodTo.
	 */
	@Column(name="REPPRDTO")
	@Temporal(TemporalType.DATE)
	public Calendar getReportingPeriodTo() {
		return reportingPeriodTo;
	}



	/**
	 * @param reportingPeriodTo The reportingPeriodTo to set.
	 */
	public void setReportingPeriodTo(Calendar reportingPeriodTo) {
		this.reportingPeriodTo = reportingPeriodTo;
	}



	/**
	 * @return Returns the actualRate.
	 */
	@Column(name= "ACTRAT")
	public double getActualRate() {
		return actualRate;
	}
	/**
	 * @param actualRate The actualRate to set.
	 */
	public void setActualRate(double actualRate) {
		this.actualRate = actualRate;
	}
	/**
	 * @return Returns the actualWeight.
	 */
	@Column(name= "ACTWGT")
	public double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight The actualWeight to set.
	 */
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @return Returns the assignedDate.
	 */
	@Column(name= "ASDTIM")

	@Temporal(TemporalType.DATE)
	public Calendar getAssignedDate() {
		return assignedDate;
	}
	/**
	 * @param assignedDate The assignedDate to set.
	 */
	public void setAssignedDate(Calendar assignedDate) {
		this.assignedDate = assignedDate;
	}
	/**
	 * @return Returns the assignedUser.
	 */
	@Column(name= "ASDUSR")
	public String getAssignedUser() {
		return assignedUser;
	}
	/**
	 * @param assignedUser The assignedUser to set.
	 */
	public void setAssignedUser(String assignedUser) {
		this.assignedUser = assignedUser;
	}
	/**
	 * @return Returns the basisType.
	 */
	@Column(name= "BASTYP")
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
	 * @return Returns the destOfficeOfExchange.
	 */
	@Column(name= "DSTEXGOFC")
	public String getDestOfficeOfExchange() {
		return destOfficeOfExchange;
	}
	/**
	 * @param destOfficeOfExchange The destOfficeOfExchange to set.
	 */
	public void setDestOfficeOfExchange(String destOfficeOfExchange) {
		this.destOfficeOfExchange = destOfficeOfExchange;
	}
	/**
	 * @return Returns the dsnNumber.
	 */
	@Column(name= "DSN")
	public String getDsnNumber() {
		return dsnNumber;
	}
	/**
	 * @param dsnNumber The dsnNumber to set.
	 */
	public void setDsnNumber(String dsnNumber) {
		this.dsnNumber = dsnNumber;
	}
	/**
	 * @return Returns the exceptionCode.
	 */
	@Column(name= "EXPCODE")
	public String getExceptionCode() {
		return exceptionCode;
	}
	/**
	 * @param exceptionCode The exceptionCode to set.
	 */
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}


	/**
	 * @return Returns the gpaReportingClaimDetailsPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
		@AttributeOverride(name = "poaCode", column = @Column(name = "POACOD")),
		@AttributeOverride(name = "billingBasis", column = @Column(name = "BLGBAS")),
		@AttributeOverride(name = "exceptionSequenceNumber", column=@Column(name="EXPSEQNUM")),
		@AttributeOverride(name= "reportingPeriodFromstring",column=@Column(name="REPPRDFRMSTR")),
		@AttributeOverride(name="reportingPeriodToString",column=@Column(name="REPPRDTOSTR"))})
		public GPAReportingClaimDetailsPK getGpaReportingClaimDetailsPK() {
		return gpaReportingClaimDetailsPK;
	}
	/**
	 * @param gpaReportingClaimDetailsPK The gpaReportingClaimDetailsPK to set.
	 */
	public void setGpaReportingClaimDetailsPK(
			GPAReportingClaimDetailsPK gpaReportingClaimDetailsPK) {
		this.gpaReportingClaimDetailsPK = gpaReportingClaimDetailsPK;
	}
	/**
	 * @return Returns the lastUpdatedTime.
	 */
	@Version
	@Column(name= "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return Returns the lastUpdatedUser.
	 */
	@Column(name= "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}
	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
	/**
	 * @return Returns the mailCategoryCode.
	 */
	@Column(name= "MALCTGCOD")
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
	 * @return Returns the mailSubClass.
	 */
	@Column(name= "MALSUBCLS")
	public String getMailSubClass() {
		return mailSubClass;
	}
	/**
	 * @param mailSubClass The mailSubClass to set.
	 */
	public void setMailSubClass(String mailSubClass) {
		this.mailSubClass = mailSubClass;
	}
	/**
	 * @return Returns the originOfficeOfExchange.
	 */
	@Column(name= "ORGEXGOFC")
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
	 * @return Returns the reportedRate.
	 */
	@Column(name= "RPTRAT")
	public double getReportedRate() {
		return reportedRate;
	}
	/**
	 * @param reportedRate The reportedRate to set.
	 */
	public void setReportedRate(double reportedRate) {
		this.reportedRate = reportedRate;
	}
	/**
	 * @return Returns the reportedWeight.
	 */
	@Column(name= "RPTWGT")
	public double getReportedWeight() {
		return reportedWeight;
	}
	/**
	 * @param reportedWeight The reportedWeight to set.
	 */
	public void setReportedWeight(double reportedWeight) {
		this.reportedWeight = reportedWeight;
	}

	/**
	 * @return Returns the rsolvedDate.
	 */
	@Column(name= "RSDTIM")

	@Temporal(TemporalType.DATE)
	public Calendar getResolvedDate() {
		return resolvedDate;
	}
	/**
	 * @param resolvedDate The rsolvedDate to set.
	 */
	public void setResolvedDate(Calendar resolvedDate) {
		this.resolvedDate = resolvedDate;
	}
	/**
	 * @return Returns the year.
	 */
	@Column(name= "YER")
	public String getYear() {
		return year;
	}
	/**
	 * @param year The year to set.
	 */
	public void setYear(String year) {
		this.year = year;
	}
	/**
	 * Default Constructor
	 * @throws SystemException 
	 * @throws CreateException 
	 * @param gpaReportingClaimDetailsVO
	 *
	 */
	public GPAReportingClaimDetails(GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO)
	throws CreateException, SystemException{
		log.entering("GPAReportingClaimDetails","GPAReportingClaimDetails");
		populatePK(gpaReportingClaimDetailsVO);
		populateAttributes(gpaReportingClaimDetailsVO);
		PersistenceController.getEntityManager().persist(this);
		log.exiting("GPAReportingClaimDetails","GPAReportingClaimDetails");

	}
	/**
	 * 
	 * @param gpaReportingClaimDetailsVO
	 */
	private void populateAttributes(GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO) {
		log.entering("GPAReportingClaimDetails","populateAttributes");
		this.setActualRate(gpaReportingClaimDetailsVO.getActualRate());
		this.setActualWeight(gpaReportingClaimDetailsVO.getActualWeight());
		this.setAssignedDate(gpaReportingClaimDetailsVO.getAssignedDate());
		this.setAssignedUser(gpaReportingClaimDetailsVO.getAssignedUser());
		this.setBasisType(gpaReportingClaimDetailsVO.getBasisType());
		this.setDestOfficeOfExchange(gpaReportingClaimDetailsVO.getDestOfficeOfExchange());			
		this.setDsnNumber(gpaReportingClaimDetailsVO.getDsnNumber());
		this.setExceptionCode(gpaReportingClaimDetailsVO.getExceptionCode());
		this.setLastUpdatedTime(gpaReportingClaimDetailsVO.getLastUpdatedime());
		this.setLastUpdatedUser(gpaReportingClaimDetailsVO.getLastUpdatedUser());
		this.setMailCategoryCode(gpaReportingClaimDetailsVO.getMailCategoryCode());
		this.setMailSubClass(gpaReportingClaimDetailsVO.getMailSubClass());
		this.setOriginOfficeOfExchange(gpaReportingClaimDetailsVO.getOriginOfficeOfExchange());
		this.setReportedRate(gpaReportingClaimDetailsVO.getReportedRate());
		this.setReportedWeight(gpaReportingClaimDetailsVO.getReportedWeight());
		this.setResolvedDate(gpaReportingClaimDetailsVO.getResolvedDate());
		this.setReportingPeriodFrom(gpaReportingClaimDetailsVO.getReportingPeriodFrom());
		this.setReportingPeriodTo(gpaReportingClaimDetailsVO.getReportingPeriodTo());
		if(gpaReportingClaimDetailsVO.getCcaRefNum()!=null){
			this.setCcaRefNum(gpaReportingClaimDetailsVO.getCcaRefNum());
		}
		
		log.exiting("GPAReportingClaimDetails","populateAttributes");

	}



	/**
	 * 
	 * @param gpaReportingClaimDetailsVO
	 */
	private void populatePK(GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO) {
		log.entering("GPAReportingClaimDetails","populatePK");
		GPAReportingClaimDetailsPK gpaReportClaimDetailsPK=new GPAReportingClaimDetailsPK();
		gpaReportClaimDetailsPK.setCompanyCode( gpaReportingClaimDetailsVO.getCompanyCode());
		gpaReportClaimDetailsPK.setPoaCode( gpaReportingClaimDetailsVO.getPoaCode());
		gpaReportClaimDetailsPK.setBillingBasis( gpaReportingClaimDetailsVO.getBillingBasis());
		gpaReportClaimDetailsPK.setReportingPeriodFromstring( gpaReportingClaimDetailsVO.getReportingFromString());
		gpaReportClaimDetailsPK.setReportingPeriodToString( gpaReportingClaimDetailsVO.getReportingToString());
		gpaReportClaimDetailsPK.setExceptionSequenceNumber( gpaReportingClaimDetailsVO.getExceptionSequenceNumber());
		this.setGpaReportingClaimDetailsPK(gpaReportClaimDetailsPK);
		log.exiting("GPAReportingClaimDetails","populatePK");

	}



	/**
	 * @author A-2280
	 * @param companyCode
	 * @param gpaCode
	 * @param billingBasis
	 * @param reportingFromStr
	 * @param reportingToStr
	 * @param exceptionSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static GPAReportingClaimDetails find(String companyCode,
			String gpaCode,String billingBasis,String reportingFromStr,String
			reportingToStr,int exceptionSequenceNumber)
	throws SystemException,FinderException {
		returnLogger().entering("GPAReportingClaimDetails","find");
		GPAReportingClaimDetails gpaReportingClaimDetails=null;
		gpaReportingClaimDetails=PersistenceController.getEntityManager().find(
				GPAReportingClaimDetails.class, new GPAReportingClaimDetailsPK(companyCode, gpaCode,
						billingBasis, reportingFromStr,reportingToStr,exceptionSequenceNumber));
		returnLogger().exiting("GPAReportingClaimDetails","find");
		return gpaReportingClaimDetails;
	}
	/**
	 *@author A-2280
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove()throws RemoveException,SystemException{
		log.entering("GPAReportingClaimDetails","remove");
		PersistenceController.getEntityManager().remove(this);
		log.exiting("GPAReportingClaimDetails","remove");
	}
	/**
	 *@author A-2280
	 *@param gpaReportingClaimDetailsVO
	 * @throws SystemException
	 * 
	 */
	public void update(GPAReportingClaimDetailsVO gpaReportingClaimDetailsVO) throws SystemException
	{
		log.entering("GPAReportingClaimDetails","update");

		populateAttributes(gpaReportingClaimDetailsVO);

		log.exiting("GPAReportingClaimDetails","update");

	}

	/**
	 * 
	 * @return
	 */
	public static Log returnLogger() {
		return LogFactory.getLogger("GPA REPORTING");
	}
	/**
	 * @author A-2280
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<GPAReportingClaimDetailsVO>findClaimDetails(GPAReportingFilterVO gpaReportingFilterVO)
	throws SystemException{
		returnLogger().entering("GPAReportingClaimDetails","findClaimDetails");
		try {
			return constructDAO().findClaimDetails(gpaReportingFilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 




	}
	/**
	 * Method to construct DAO
	 * @return
	 * @throws PersistenceException
	 * @throws SystemException
	 */
	private static MRAGPAReportingDAO constructDAO()
	throws PersistenceException, SystemException{

		returnLogger().entering("GPAReportingClaimDetails","constructDAO");
		EntityManager entityManager =
			PersistenceController.getEntityManager();
		return MRAGPAReportingDAO .class.cast(
				entityManager.getQueryDAO(MODULE_NAME));
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeDetails(
			GPAReportingFilterVO gpaReportingFilterVO)throws SystemException{
		returnLogger().entering("GPAReportingClaimDetails","printExceptionsReportAssigneeDetails");
		try {
			return constructDAO().printExceptionsReportAssigneeDetails(gpaReportingFilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 
	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<GPAReportingClaimDetailsVO> printExceptionsReportAssigneeSummary(
			GPAReportingFilterVO gpaReportingFilterVO)throws SystemException{
		returnLogger().entering("GPAReportingClaimDetails","printExceptionsReportAssigneeSummary");
		try {
			return constructDAO().printExceptionsReportAssigneeSummary(gpaReportingFilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 

	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<GPAReportingClaimDetailsVO> printExceptionsReportDetails(
			GPAReportingFilterVO gpaReportingFilterVO)throws SystemException{
		returnLogger().entering("GPAReportingClaimDetails","printExceptionsReportDetails");
		try {
			return constructDAO().printExceptionsReportDetails(gpaReportingFilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 

	}

	/**
	 * @author A-2245
	 * @param gpaReportingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<GPAReportingClaimDetailsVO> printExceptionsReportSummary(
			GPAReportingFilterVO gpaReportingFilterVO)throws SystemException{
		returnLogger().entering("GPAReportingClaimDetails","printExceptionsReportSummary");
		try {
			return constructDAO().printExceptionsReportSummary(gpaReportingFilterVO);
		} catch (PersistenceException e) {

			e.getErrorCode();
			throw new SystemException(e.getMessage());
		} 

	}

}
