/*
 * MailActivityDetail.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.ibsplc.icargo.business.mail.operations.vo.MailActivityDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author a-3109
 * 
 */
@Entity
@Table(name = "MALACTDTL")
@Staleable
public class MailActivityDetail {

	private static final String PRODUCT_NAME = "mail.operations";

	private static final String CLASS_NAME = "MailActivityDetail";

	private Log log = LogFactory.getLogger("MAIL_operations");

	/**
	 * airlineIdentifier
	 */
	private int airlineIdentifier;

	/**
	 * airlineCode
	 */
	private String airlineCode;

	/**
	 * gpaCode
	 */
	private String gpaCode;

	/**
	 * slaIdentifier
	 */
	private String slaIdentifier;

	/**
	 * flightCarrierIdentifier
	 */
	private int flightCarrierIdentifier;

	/**
	 * flightCarrierCode
	 */
	private String flightCarrierCode;

	/**
	 * flightNumber
	 */
	private String flightNumber;

	/**
	 * mailCategoryCode
	 */
	private String mailCategoryCode;

	/**
	 * plannedTime
	 */
	private Calendar plannedTime;

	/**
	 * actualTime
	 */
	private Calendar actualTime;

	/**
	 * slaStatus
	 */
	private String slaStatus;

	/**
	 * alertStatus
	 */
	private String alertStatus;

	/**
	 * numberOfChasers
	 */
	private int numberOfChasers;

	private MailActivityDetailPK mailActivityDetailPk;

	/**
	 * @return Returns the actualTime.
	 */
	@Column(name = "ACTTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getActualTime() {
		return actualTime;
	}

	/**
	 * @param actualTime
	 *            The actualTime to set.
	 */
	public void setActualTime(Calendar actualTime) {
		this.actualTime = actualTime;
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
	 * @return Returns the airlineIdentifier.
	 */
	@Column(name = "ARLIDR")
	public int getAirlineIdentifier() {
		return airlineIdentifier;
	}

	/**
	 * @param airlineIdentifier
	 *            The airlineIdentifier to set.
	 */
	public void setAirlineIdentifier(int airlineIdentifier) {
		this.airlineIdentifier = airlineIdentifier;
	}

	/**
	 * @return Returns the flightCarrierCode.
	 */
	@Column(name = "FLTCARCOD")
	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	/**
	 * @param flightCarrierCode
	 *            The flightCarrierCode to set.
	 */
	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
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
	 * @return Returns the gpaCode.
	 */
	@Column(name = "GPACOD")
	public String getGpaCode() {
		return gpaCode;
	}

	/**
	 * @param gpaCode
	 *            The gpaCode to set.
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
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
	 * @return Returns the plannedTime.
	 */
	@Column(name = "PLNTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getPlannedTime() {
		return plannedTime;
	}

	/**
	 * @param plannedTime
	 *            The plannedTime to set.
	 */
	public void setPlannedTime(Calendar plannedTime) {
		this.plannedTime = plannedTime;
	}

	/**
	 * @return Returns the slaIdentifier.
	 */
	@Column(name = "SLAIDR")
	public String getSlaIdentifier() {
		return slaIdentifier;
	}

	/**
	 * @param slaIdentifier
	 *            The slaIdentifier to set.
	 */
	public void setSlaIdentifier(String slaIdentifier) {
		this.slaIdentifier = slaIdentifier;
	}

	/**
	 * @return Returns the slaStatus.
	 */
	@Column(name = "SLASTA")
	public String getSlaStatus() {
		return slaStatus;
	}

	/**
	 * @param slaStatus
	 *            The slaStatus to set.
	 */
	public void setSlaStatus(String slaStatus) {
		this.slaStatus = slaStatus;
	}

	/**
	 * @return Returns the alertStatus.
	 */
	@Column(name = "ALRSTA")
	public String getAlertStatus() {
		return alertStatus;
	}

	/**
	 * @param alertStatus
	 *            The alertStatus to set.
	 */
	public void setAlertStatus(String alertStatus) {
		this.alertStatus = alertStatus;
	}

	/**
	 * @return Returns the numberOfChasers.
	 */
	@Column(name = "CHRCNT")
	public int getNumberOfChasers() {
		return numberOfChasers;
	}

	/**
	 * @param numberOfChasers
	 *            The numberOfChasers to set.
	 */
	public void setNumberOfChasers(int numberOfChasers) {
		this.numberOfChasers = numberOfChasers;
	}

	/**
	 * @return Returns the mailActivityDetailPk.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "mailBagNumber", column = @Column(name = "MALBAGNUM")),
			@AttributeOverride(name = "slaActivity", column = @Column(name = "SLAACT")) })
	public MailActivityDetailPK getMailActivityDetailPk() {
		return mailActivityDetailPk;
	}

	/**
	 * @param mailActivityDetailPk
	 *            The mailActivityDetailPk to set.
	 */
	public void setMailActivityDetailPk(
			MailActivityDetailPK mailActivityDetailPk) {
		this.mailActivityDetailPk = mailActivityDetailPk;
	}

	/**
	 * Default constructor
	 * 
	 */
	public MailActivityDetail() {
	}

	/**
	 * 
	 * @param mailActivityDetailVo
	 * @throws SystemException
	 */
	public MailActivityDetail(MailActivityDetailVO mailActivityDetailVo)
			throws SystemException {
		log.entering(CLASS_NAME, CLASS_NAME);
		MailActivityDetailPK mailActivityDetailPkToPersist = new MailActivityDetailPK(
				mailActivityDetailVo.getCompanyCode(),
				mailActivityDetailVo.getMailBagNumber(),
				mailActivityDetailVo.getServiceLevelActivity());
		setMailActivityDetailPk(mailActivityDetailPkToPersist);
		populateAttributes(mailActivityDetailVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
		log.exiting(CLASS_NAME, CLASS_NAME);
	}

	/**
	 * 
	 * @param mailActivityDetailVo
	 * @throws SystemException
	 */
	private void populateAttributes(MailActivityDetailVO mailActivityDetailVo)
			throws SystemException {
		log.entering(CLASS_NAME, "populateAttributes");
		setActualTime(mailActivityDetailVo.getActualTime());
		setAirlineCode(mailActivityDetailVo.getAirlineCode());
		setAirlineIdentifier(mailActivityDetailVo.getAirlineIdentifier());
		setFlightCarrierCode(mailActivityDetailVo.getFlightCarrierCode());
		setFlightCarrierIdentifier(mailActivityDetailVo.getFlightCarrierId());
		setFlightNumber(mailActivityDetailVo.getFlightNumber());
		setGpaCode(mailActivityDetailVo.getGpaCode());
		setMailCategoryCode(mailActivityDetailVo.getMailCategory());
		setPlannedTime(mailActivityDetailVo.getPlannedTime());
		setSlaIdentifier(mailActivityDetailVo.getSlaIdentifier());
		setSlaStatus(mailActivityDetailVo.getSlaStatus());
		setAlertStatus(mailActivityDetailVo.getAlertStatus());
		if (mailActivityDetailVo.getNumberOfChasers() != 0) {
			setNumberOfChasers(mailActivityDetailVo.getNumberOfChasers());
		}
		log.exiting(CLASS_NAME, "populateAttributes");
	}

	/**
	 * 
	 * @param mailActivityDetailVo
	 * @throws SystemException
	 */
	public void update(MailActivityDetailVO mailActivityDetailVo)
			throws SystemException {
		log.entering(CLASS_NAME, "update");
		populateAttributes(mailActivityDetailVo);
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
	 * @param mailBagNumber
	 * @param slaActivity
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MailActivityDetail find(String companyCode,
			String mailBagNumber, String slaActivity) throws SystemException,
			FinderException {
		Log log = LogFactory.getLogger("MAIL_OPERATIONS");
		log.entering(CLASS_NAME, "find");
		MailActivityDetailPK mailActivityDetailPkToFind = new MailActivityDetailPK(
				companyCode, mailBagNumber, slaActivity);
		log.exiting(CLASS_NAME, "find");
		return PersistenceController.getEntityManager().find(
				MailActivityDetail.class, mailActivityDetailPkToFind);
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(PRODUCT_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException("No DAO found", persistenceException);
		}
	}
	
	/**
	 * @author a-2518
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 */
	public static String findPostalAuthorityCode(String companyCode,
			String officeOfExchange) throws SystemException {
		Log log = LogFactory.getLogger("MAIL_OPERATIONS");
		log.entering(CLASS_NAME, "findPostalAuthorityCode");
		try {
			return constructDAO().findPostalAuthorityCode(companyCode,
					officeOfExchange);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
	}
	
	
	/**
	 * @author a-2518
	 * @param companyCode
	 * @param gpaCode
	 * @param origin
	 * @param destination
	 * @param mailCategory
	 * @param activity
	 * @param scanDate
	 * @return MailActivityDetailVO
	 * @throws SystemException
	 */
	public static MailActivityDetailVO findServiceTimeAndSLAId(
			String companyCode, String gpaCode, String origin,
			String destination, String mailCategory, String activity,
			LocalDate scanDate) throws SystemException {
		Log log = LogFactory.getLogger("MAIL_OPERATIONS");
		log.entering(CLASS_NAME, "findServiceTime");
		try {
			return constructDAO().findServiceTimeAndSLAId(companyCode, gpaCode,
					origin, destination, mailCategory, activity, scanDate);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getMessage(),
					persistenceException);
		}
	}

}
