/*
 * ExceptionsInFlownSegment.java Created on Dec 28, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.flown;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailExceptionVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1556
 *
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */


@Entity
@Table(name = "MTKFLNSEGEXP")

public class ExceptionsInFlownSegment {
    /**
     * Comment for <code>mraflightsegment</code>
     * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
     */
   // private MRAFlightSegment mraflightsegment;

	private static final String MODULE_NAME = "mail.mra.flown";
	private Log log = LogFactory.getLogger("MAIL MRA EXCEPTION IN FLOWNSEGMENT");

	private ExceptionsInFlownSegmentPK exceptionsInFlownSegmentPK;

	private String dsn;

	private String originExchangeOffice;

	private String destinationExchangeOffice;

	private int year;

	private String mailSubclass;

	private String mailCategoryCode;

	private String uldNumber;

	private String basisType;

	private String mailClass;

	private String 	containerNumber;

	private String consignmentDocNumber;

	private String poaCode;

	private long consignmentseqnumber;

	private String assigneeCode;

	private Calendar assignedDate;

	private String exceptionStatus;

	private Calendar resolvedDate;

    private Calendar lastUpdateTime;

	private String lastUpdatedUser;



	/**
	 * @return the lastUpdatedUser
	 */
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
	 * @return Returns the assignedDate.
	 */
	 @Column(name="ASGDAT")
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
	 * @return Returns the assigneeCode.
	 */
	@Column(name="ASGCOD")
	public String getAssigneeCode() {
		return assigneeCode;
	}

	/**
	 * @param assigneeCode The assigneeCode to set.
	 */
	public void setAssigneeCode(String assigneeCode) {
		this.assigneeCode = assigneeCode;
	}

	/**
	 * @return Returns the baseType.
	 */
	@Column(name="BASTYP")
	public String getBasisType() {
		return basisType;
	}


	/**
	 * @param basisType
	 */
	public void setBasisType(String basisType) {
		this.basisType = basisType;
	}

	/**
	 * @return Returns the consignmentDocNumber.
	 */
	@Column(name="CSGDOCNUM")
	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}

	/**
	 * @param consignmentDocNumber The consignmentDocNumber to set.
	 */
	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}

	/**
	 * @return Returns the consignmentseqnumber.
	 */
	@Column(name="CSGSEQNUM")
	public long getConsignmentseqnumber() {
		return consignmentseqnumber;
	}

	/**
	 * @param consignmentseqnumber The consignmentseqnumber to set.
	 */
	public void setConsignmentseqnumber(long consignmentseqnumber) {
		this.consignmentseqnumber = consignmentseqnumber;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	@Column(name="CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the destinationExchangeOffice.
	 */
	@Column(name="DSTEXGOFC")
	public String getDestinationExchangeOffice() {
		return destinationExchangeOffice;
	}

	/**
	 * @param destinationExchangeOffice The destinationExchangeOffice to set.
	 */
	public void setDestinationExchangeOffice(String destinationExchangeOffice) {
		this.destinationExchangeOffice = destinationExchangeOffice;
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
	 * @return Returns the exceptionsInFlownSegmentPK.
	 */
	 @EmbeddedId
	@AttributeOverrides({
		 	@AttributeOverride(name="billingBasis", column=@Column(name="BLGBAS")),
			@AttributeOverride(name="flightNumber", column=@Column(name="FLTNUM")),
			@AttributeOverride(name="carrierId", column=@Column(name="FLTCARIDR")),
			@AttributeOverride(name="exceptionCode", column=@Column(name="EXPCOD")),
			@AttributeOverride(name="flightSequenceNumber", column=@Column(name="FLTSEQNUM")),
			@AttributeOverride(name="segmentSerialNumber", column=@Column(name="SEGSERNUM")),
			@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM")),
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD"))
			}
		)
	public ExceptionsInFlownSegmentPK getExceptionsInFlownSegmentPK() {
		return exceptionsInFlownSegmentPK;
	}

	/**
	 * @param exceptionsInFlownSegmentPK The exceptionsInFlownSegmentPK to set.
	 */
	public void setExceptionsInFlownSegmentPK(
			ExceptionsInFlownSegmentPK exceptionsInFlownSegmentPK) {
		this.exceptionsInFlownSegmentPK = exceptionsInFlownSegmentPK;
	}

	/**
	 * @return Returns the exceptionStatus.
	 */
	@Column(name="EXPSTA")
	public String getExceptionStatus() {
		return exceptionStatus;
	}

	/**
	 * @param exceptionStatus The exceptionStatus to set.
	 */
	public void setExceptionStatus(String exceptionStatus) {
		this.exceptionStatus = exceptionStatus;
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
	 * @return Returns the mailClass.
	 */
	@Column(name="MALCLS")
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
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
	 * @return Returns the originExchangeOffice.
	 */
	@Column(name="ORGEXGOFC")
	public String getOriginExchangeOffice() {
		return originExchangeOffice;
	}

	/**
	 * @param originExchangeOffice The originExchangeOffice to set.
	 */
	public void setOriginExchangeOffice(String originExchangeOffice) {
		this.originExchangeOffice = originExchangeOffice;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@Column(name="POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	/**
	 * @return Returns the resolvedDate.
	 */
	@Column(name="RESDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getResolvedDate() {
		return resolvedDate;
	}

	/**
	 * @param resolvedDate The resolvedDate to set.
	 */
	public void setResolvedDate(Calendar resolvedDate) {
		this.resolvedDate = resolvedDate;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	@Column(name="ULDNUM")
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
	 * @return Returns the year.
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
	 * default constructor
	 *
	 */
	public ExceptionsInFlownSegment(){

	}

	public ExceptionsInFlownSegment(FlownMailExceptionVO flownMailExceptionVO)
												throws SystemException{
		log.entering("ExceptionsInFlownSegment", "ExceptionsInFlownSegment");
		populatePK(flownMailExceptionVO);
		populateAttributes(flownMailExceptionVO);
		try{
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode(),
					createException);
		}

	}



	/**
	 * @param flownMailExceptionVO
	 */
	private void populatePK(FlownMailExceptionVO flownMailExceptionVO){
			log.entering("ExceptionsInFlownSegment","populate PK");
			this.setExceptionsInFlownSegmentPK(new ExceptionsInFlownSegmentPK(
					flownMailExceptionVO.getCompanyCode(),
					flownMailExceptionVO.getFlightCarrierId(),
					flownMailExceptionVO.getFlightNumber(),
					flownMailExceptionVO.getFlightSequenceNumber(),
					flownMailExceptionVO.getSegmentSerialNumber(),
					flownMailExceptionVO.getSerialNumber(),
					flownMailExceptionVO.getBillingBasis(),
					flownMailExceptionVO.getExceptionCode()));
		}





	/**
	 * @param flownMailExceptionVO
	 */
	private void populateAttributes(FlownMailExceptionVO flownMailExceptionVO){
		this.setAssignedDate(flownMailExceptionVO.getAssignedDate());
		this.setAssigneeCode(flownMailExceptionVO.getAssigneeCode());
		this.setBasisType(flownMailExceptionVO.getBasisType());
		this.setConsignmentDocNumber(flownMailExceptionVO.getConsignmentDocNumber());
		this.setConsignmentseqnumber(flownMailExceptionVO.getConsignmentseqnumber());
		this.setContainerNumber(flownMailExceptionVO.getContainerNumber());
		this.setDestinationExchangeOffice(flownMailExceptionVO.getDestinationOfficeOfExchange());
		this.setDsn(flownMailExceptionVO.getDsnNumber());
		this.setExceptionStatus(flownMailExceptionVO.getExceptionStatus());
		this.setMailCategoryCode(flownMailExceptionVO.getMailCategoryCode());
		this.setMailClass(flownMailExceptionVO.getMailClass());
		this.setMailSubclass(flownMailExceptionVO.getMailSubclass());
		this.setOriginExchangeOffice(flownMailExceptionVO.getOriginOfficeOfExchange());
		this.setPoaCode(flownMailExceptionVO.getPoaCode());
		this.setResolvedDate(flownMailExceptionVO.getResolvedDate());
		this.setUldNumber(flownMailExceptionVO.getUldNumber());
		this.setYear(flownMailExceptionVO.getYear());
		this.setLastUpdatedUser(flownMailExceptionVO.getLastUpdatedUser());
		this.setLastUpdateTime(flownMailExceptionVO.getLastUpdatedTime());


	}

	/**
	 * @param companyCode
	 * @param carrierId
	 * @param flightNumber
	 * @param flightSequenceNumber
	 * @param segmentSerialNumber
	 * @param serialNumber
	 * @param billingBasis
	 * @param exceptionCode
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ExceptionsInFlownSegment find(String companyCode, int carrierId,
            String flightNumber, long flightSequenceNumber,
            int segmentSerialNumber,long serialNumber,
            String billingBasis,String exceptionCode)throws
			SystemException,FinderException{

			return PersistenceController.getEntityManager().find(
					ExceptionsInFlownSegment.class,new ExceptionsInFlownSegmentPK(
							companyCode,carrierId,flightNumber,flightSequenceNumber,
							segmentSerialNumber,serialNumber,billingBasis,exceptionCode));
	}

	/**
	 * @param flownMailExceptionVO
	 * @throws SystemException
	 */
	public void update(FlownMailExceptionVO flownMailExceptionVO)throws
			SystemException{
		log.entering("ExceptionInFlownSegment","update");
		populateAttributes(flownMailExceptionVO);
		log.exiting("ExceptionInFlownSegment","update");
	}

	/**
     * method for cfindFlownMailExceptions
     * @author a-2401
     * @return Collection<FlownMailExceptionVO>
     * @throws SystemException
     */

	/*public static Collection<FlownMailExceptionVO>
			findFlownMailExceptions(FlownMailFilterVO flownMailFilterVO) throws SystemException{
		Collection<FlownMailExceptionVO> flownMailExceptionVOs = null;
		try{
			flownMailExceptionVOs = constructDAO().findFlownMailExceptions(flownMailFilterVO);
		}catch(PersistenceException e){
			throw new SystemException(e.getMessage(),e);
		}
		return flownMailExceptionVOs;
	}*/

	/**
	 * @param flownMailExceptionVO
	 * @throws SystemException
	 */
	public void assignFlownMailExceptions(FlownMailExceptionVO flownMailExceptionVO)
							throws SystemException{
		log.entering("ExceptionInFlownSegment","assignFlownMailExceptions");

			this.setAssigneeCode(flownMailExceptionVO.getAssigneeCode());
			log.log(log.FINE,"assignee is set");

		if(flownMailExceptionVO.getAssignedDate()!=null){
			this.setAssignedDate(flownMailExceptionVO.getAssignedDate().toCalendar());
			log.log(log.FINE,"assigned date  is set");
		}
		log.exiting("ExceptionInFlownSegment","assignFlownMailExceptions");
	}


	/**
     * method for calling up the DAO for the submodule
     * @author a-2401
     * @return queryDAO
     * @throws SystemException
     */

/*	private static MRAFlownDAO constructDAO()
						throws SystemException,PersistenceException {
		MRAFlownDAO queryDAO = null;
		try{
		queryDAO = (MRAFlownDAO) PersistenceController.
					getEntityManager().getQueryDAO(MODULE_NAME);
		}catch(PersistenceException e){
			throw new SystemException(e.getMessage(),e);
		}
		return(queryDAO);

	}*/

}
