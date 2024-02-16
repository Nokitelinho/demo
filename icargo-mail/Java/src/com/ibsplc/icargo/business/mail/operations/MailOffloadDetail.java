/*
 * MailOffloadDetail.java Created on Jun 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.OffloadDetailVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-5991 The entity containing the Offload Details
 */
@Entity
@Table(name = "MALOFLDTL")
@Staleable
public class MailOffloadDetail {
	private MailOffloadDetailPK mailOffloadDetailPK; 

	private Calendar offloadedDate;

	private String offloadUser;

	private int offloadedBags;

	private double offloadedWeight;

	private String offloadRemarks;

	private String offloadReasonCode;

	private String carrierCode;

	private String offloadType;



	private String containerNumber;

	private String airportCode;
	
	private long mailSequenceNumber;
	
	private Calendar lastupdatedTime;
	private String lastupdateUser;
	// private static final String ENTITY="OFFLOADDETAIL";

	/**
	 * @return the lastupdatedTime
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastupdatedTime() {
		return lastupdatedTime;
	}
	/**
	 * @param lastupdatedTime the lastupdatedTime to set
	 */
	public void setLastupdatedTime(Calendar lastupdatedTime) {
		this.lastupdatedTime = lastupdatedTime;
	}
	/**
	 * @return the lastupdateUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastupdateUser() {
		return lastupdateUser;
	}
	/**
	 * @param lastupdateUser the lastupdateUser to set
	 */
	public void setLastupdateUser(String lastupdateUser) {
		this.lastupdateUser = lastupdateUser;
	}
	private static final String MODULE = "mail.operations";

	private Log log = LogFactory.getLogger("MAIL_OPERATIONS");

	/**
	 * @return Returns the carrierCode.
	 * 
	 */
	@Column(name = "FLTCARCOD")
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

	@Column(name ="MALSEQNUM")
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}

	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}

	/**
	 * @return Returns the offloadedBags.
	 * 
	 */
	@Column(name = "OFLBAG")
	public int getOffloadedBags() {
		return offloadedBags;
	}

	/**
	 * @param offloadedBags
	 *            The offloadedBags to set.
	 */
	public void setOffloadedBags(int offloadedBags) {
		this.offloadedBags = offloadedBags;
	}

	/**
	 * The Pk for the Entity
	 * 
	 * @return
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR ")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM ")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM ")),
			@AttributeOverride(name = "segmentSerialNumber", column = @Column(name = "SEGSERNUM ")),
			@AttributeOverride(name = "offloadSerialNumber", column = @Column(name = "SERNUM")) })
	public MailOffloadDetailPK getMailOffloadDetailPK() {
		return mailOffloadDetailPK;
	}

	/**
	 * The setter method for PK A-1936
	 * 
	 * @param mailOffloadDetailPK
	 */
	public void setMailOffloadDetailPK(MailOffloadDetailPK mailOffloadDetailPK) {
		this.mailOffloadDetailPK = mailOffloadDetailPK;
	}

	/**
	 * @return Returns the offloadedDate.
	 * 
	 */
	@Column(name = "OFLDAT")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getOffloadedDate() {
		return offloadedDate;
	}

	/**
	 * @param offloadedDate
	 *            The offloadedDate to set.
	 */
	public void setOffloadedDate(Calendar offloadedDate) {
		this.offloadedDate = offloadedDate;
	}

	/**
	 * @return Returns the offloadedWeight.
	 * 
	 */
	@Column(name = "OFLWGT")
	public double getOffloadedWeight() {
		return offloadedWeight;
	}

	/**
	 * @param offloadedWeight
	 *            The offloadedWeight to set.
	 */
	public void setOffloadedWeight(double offloadedWeight) {
		this.offloadedWeight = offloadedWeight;
	}

	/**
	 * @return Returns the offloadReasonCode.
	 * 
	 */
	@Column(name = "OFLRSNCOD")
	public String getOffloadReasonCode() {
		return offloadReasonCode;
	}

	/**
	 * @param offloadReasonCode
	 *            The offloadReasonCode to set.
	 */
	public void setOffloadReasonCode(String offloadReasonCode) {
		this.offloadReasonCode = offloadReasonCode;
	}

	/**
	 * @return Returns the offloadRemarks.
	 * 
	 */
	@Column(name = "RMK")
	public String getOffloadRemarks() {
		return offloadRemarks;
	}

	/**
	 * @param offloadRemarks
	 *            The offloadRemarks to set.
	 */
	public void setOffloadRemarks(String offloadRemarks) {
		this.offloadRemarks = offloadRemarks;
	}

	/**
	 * @return Returns the offloadUser.
	 * 
	 */
	@Column(name = "OFLUSR")
	public String getOffloadUser() {
		return offloadUser;
	}

	/**
	 * @param offloadUser
	 *            The offloadUser to set.
	 */
	public void setOffloadUser(String offloadUser) {
		this.offloadUser = offloadUser;
	}


	
	/**
	 * @return Returns the offloadType.
	 */
	@Column(name = "OFLTYP")
	public String getOffloadType() {
		return offloadType;
	}

	/**
	 * @param offloadType
	 *            The offloadType to set.
	 */
	public void setOffloadType(String offloadType) {
		this.offloadType = offloadType;
	}

	
	/**
	 * @return Returns the airportCode.
	 */
	@Column(name = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	@Column(name = "CONNUM")
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}



	/**
	 * The DefaultConstructor For the Entity
	 * 
	 */
	public MailOffloadDetail() {

	}

	public MailOffloadDetail(OffloadDetailVO offloadDetailVO)
			throws SystemException {
		log.entering("OffloadDetail", "Create Instance of the Entity");
		populatePK(offloadDetailVO);
		populateAttributes(offloadDetailVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * 
	 * @param offloadDetailVO
	 */
	private void populatePK(OffloadDetailVO offloadDetailVO) {
		log.entering("OffloadDetail", "populatePK");
		log.log(Log.FINE, "The OFFLOAD DETAIL VO IS ", offloadDetailVO);
		MailOffloadDetailPK mailoffloadDetailPK = new MailOffloadDetailPK();
		mailoffloadDetailPK.setCompanyCode(   offloadDetailVO.getCompanyCode());
		mailoffloadDetailPK.setCarrierId(   offloadDetailVO.getCarrierId());
		mailoffloadDetailPK.setFlightNumber(   offloadDetailVO.getFlightNumber());
		mailoffloadDetailPK.setFlightSequenceNumber(   offloadDetailVO
				.getFlightSequenceNumber());
		mailoffloadDetailPK.setSegmentSerialNumber(   offloadDetailVO
				.getSegmentSerialNumber());
		mailoffloadDetailPK.setOffloadSerialNumber(offloadDetailVO
				.getOffloadSerialNumber());
		this.setMailOffloadDetailPK(mailoffloadDetailPK);

	}

	/**
	 * @param offloadDetailVO
	 */
	private void populateAttributes(OffloadDetailVO offloadDetailVO) {
		log.entering("OffloadDetail", "poipulateAttributes");
		this.setAirportCode(offloadDetailVO.getAirportCode());
		this.setCarrierCode(offloadDetailVO.getCarrierCode());
		this.setContainerNumber(offloadDetailVO.getContainerNumber());
		/*this.setDestinationExchangeOffice(offloadDetailVO
				.getDestinationExchangeOffice());
		this.setDsn(offloadDetailVO.getDsn());
		this.setMailClass(offloadDetailVO.getMailClass());
		this.setMailId(offloadDetailVO.getMailId());*/
		this.setOffloadedBags(offloadDetailVO.getOffloadedBags());
		this
				.setOffloadedDate(offloadDetailVO.getOffloadedDate() != null ? offloadDetailVO
						.getOffloadedDate().toCalendar()
						: new LocalDate(offloadDetailVO.getAirportCode(),
								Location.ARP, true));
		//this.setOffloadedWeight(offloadDetailVO.getOffloadedWeight());
		if(offloadDetailVO.getOffloadedWeight()!=null){//added by a-7531 for ICRD-250960
		this.setOffloadedWeight(offloadDetailVO.getOffloadedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		}
		this.setMailSequenceNumber(offloadDetailVO.getMailSequenceNumber());
		this.setOffloadReasonCode(offloadDetailVO.getOffloadReasonCode());
		log.log(Log.FINE, "THE REMARKS IN OFFLOAD DETAIL IS ", offloadDetailVO.getOffloadRemarks());
		this.setOffloadRemarks(offloadDetailVO.getOffloadRemarks());
		this.setOffloadType(offloadDetailVO.getOffloadType());
		this.setOffloadUser(offloadDetailVO.getOffloadUser());      
		this.setLastupdateUser(offloadDetailVO.getOffloadUser());
		/*this.setOriginExchangeOffice(offloadDetailVO.getOriginExchangeOffice());
		this.setYear(offloadDetailVO.getYear());
		this.setMailCategoryCode(offloadDetailVO.getMailCategoryCode());
		this.setMailSubclass(offloadDetailVO.getMailSubclass());*/
		
	}
	
	/**
	 * @author a-5991 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	private static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}
	/**
	 * TODO Purpose
	 * Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param receptacleID
	 * @return
	 * @throws SystemException 
	 */
	public static String findOffloadReasonForMailbag(
			String companyCode, String receptacleID) throws SystemException {
		try {
			return constructDAO().findOffloadReasonForMailbag(
					companyCode, receptacleID);
		} catch(PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}
	/**
	 * TODO Purpose
	 * Sep 14, 2006, a-1739
	 * @param companyCode
	 * @param containerNumber
	 * @return
	 * @throws SystemException 
	 */
	public static String findOffloadReasonForULD(
			String companyCode, String containerNumber) throws SystemException {
		try {
			return constructDAO().findOffloadReasonForULD(
					companyCode, containerNumber);
		} catch(PersistenceException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}
	}


}
