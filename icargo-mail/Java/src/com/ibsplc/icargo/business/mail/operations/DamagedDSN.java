/*
 * DamagedDSN.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

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
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.MailbagDamageException;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedDSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This entity stores the damage details of a Consignment
 * 
 * @author A-3109
 * 
 */

@Entity
@Table(name = "MALDMGDSN")
public class DamagedDSN {

	private Log log = LogFactory.getLogger("MAIL_operations");

	private static final String MODULE = "mail.operations";

	private static final String DAMAGEDMAILS_EXCEED_ACCEPTEDMAILS = "mail.operations.err.damagedmailsexceedacceptedmail";

	private DamagedDSNPK damagedDSNPK;

	/**
	 * carrierId
	 */
	private int carrierId;

	/**
	 * flightNumber
	 */
	private String flightNumber;

	/**
	 * flightSequenceNumber
	 */
	private long flightSequenceNumber;

	/**
	 * segmentSerialNumber
	 */
	private int segmentSerialNumber;

	/**
	 * acceptedBags
	 */
	private int acceptedBags;

	/**
	 * acceptedWeight
	 */
	private double acceptedWeight;

	/**
	 * returnedBags
	 */
	private int returnedBags;

	/**
	 * returnedWeight
	 */
	private double returnedWeight;

	/**
	 * Whether full or partial
	 */
	private String returnStatus;

	/**
	 * mailClass
	 */
	private String mailClass;

	/**
	 * lastUpdateTime
	 */
	private Calendar lastUpdateTime;

	/**
	 * lastUpdateUser
	 */
	private String lastUpdateUser;

	/**
	 * damageDetails
	 */
	private Set<DamagedDSNDetail> damageDetails;

	public DamagedDSN() {

	}

	/**
	 * @return Returns the acceptedBags.
	 */
	@Column(name = "ACPBAG")
	public int getAcceptedBags() {
		return acceptedBags;
	}

	/**
	 * @param acceptedBags
	 *            The acceptedBags to set.
	 */
	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/**
	 * @return Returns the acceptedWeight.
	 */
	@Column(name = "ACPWGT")
	public double getAcceptedWeight() {
		return acceptedWeight;
	}

	/**
	 * @param acceptedWeight
	 *            The acceptedWeight to set.
	 */
	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}

	/**
	 * @return Returns the carrierId.
	 */
	@Column(name = "FLTCARIDR")
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
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
	 * @return Returns the returnedBags.
	 */
	@Column(name = "RTNBAG")
	public int getReturnedBags() {
		return returnedBags;
	}

	/**
	 * @param returnedBags
	 *            The returnedBags to set.
	 */
	public void setReturnedBags(int returnedBags) {
		this.returnedBags = returnedBags;
	}

	/**
	 * @return Returns the returnedWeight.
	 */
	@Column(name = "RTNWGT")
	public double getReturnedWeight() {
		return returnedWeight;
	}

	/**
	 * @param returnedWeight
	 *            The returnedWeight to set.
	 */
	public void setReturnedWeight(double returnedWeight) {
		this.returnedWeight = returnedWeight;
	}

	/**
	 * @return Returns the returnStatus.
	 */
	@Column(name = "RTNSTA")
	public String getReturnStatus() {
		return returnStatus;
	}

	/**
	 * @param returnStatus
	 *            The returnStatus to set.
	 */
	public void setReturnStatus(String returnStatus) {
		this.returnStatus = returnStatus;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	@Column(name = "SEGSERNUM")
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the mailClass.
	 */
	@Column(name = "MALCLS")
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass
	 *            The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the damagedDSNPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "dsn", column = @Column(name = "DSN")),
			@AttributeOverride(name = "originExchangeOffice", column = @Column(name = "ORGEXGOFC")),
			@AttributeOverride(name = "destinationExchangeOffice", column = @Column(name = "DSTEXGOFC")),
			@AttributeOverride(name = "mailCategoryCode", column = @Column(name = "MALCTGCOD")),
			@AttributeOverride(name = "mailSubclass", column = @Column(name = "MALSUBCLS")),
			@AttributeOverride(name = "year", column = @Column(name = "YER")),
			@AttributeOverride(name = "consignmentNumber", column = @Column(name = "CSGDOCNUM")),
			@AttributeOverride(name = "consignmentSequenceNumber", column = @Column(name = "CSGSEQNUM")),
			@AttributeOverride(name = "paCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "damageSequenceNumber", column = @Column(name = "DMGSEQNUM")) })
	public DamagedDSNPK getDamagedDSNPK() {
		return damagedDSNPK;
	}

	/**
	 * @param damagedDSNPK
	 *            The damagedDSNPK to set.
	 */
	public void setDamagedDSNPK(DamagedDSNPK damagedDSNPK) {
		this.damagedDSNPK = damagedDSNPK;
	}

	/**
	 * @return Returns the damageDetails.
	 */
	@OneToMany
	@JoinColumns({
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "DSN", referencedColumnName = "DSN", insertable = false, updatable = false),
			@JoinColumn(name = "ORGEXGOFC", referencedColumnName = "ORGEXGOFC", insertable = false, updatable = false),
			@JoinColumn(name = "DSTEXGOFC", referencedColumnName = "DSTEXGOFC", insertable = false, updatable = false),
			@JoinColumn(name = "MALCTGCOD", referencedColumnName = "MALCTGCOD", insertable = false, updatable = false),
			@JoinColumn(name = "MALSUBCLS", referencedColumnName = "MALSUBCLS", insertable = false, updatable = false),
			@JoinColumn(name = "YER", referencedColumnName = "YER", insertable = false, updatable = false),
			@JoinColumn(name = "CSGDOCNUM", referencedColumnName = "CSGDOCNUM", insertable = false, updatable = false),
			@JoinColumn(name = "CSGSEQNUM", referencedColumnName = "CSGSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "POACOD", referencedColumnName = "POACOD", insertable = false, updatable = false),
			@JoinColumn(name = "DMGSEQNUM", referencedColumnName = "DMGSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable = false, updatable = false) })
	public Set<DamagedDSNDetail> getDamageDetails() {
		return damageDetails;
	}

	/**
	 * @param damageDetails
	 *            The damageDetails to set.
	 */
	public void setDamageDetails(Set<DamagedDSNDetail> damageDetails) {
		this.damageDetails = damageDetails;
	}

	public DamagedDSN(DamagedDSNVO damagedDSNVO) throws SystemException {
		populatePK(damagedDSNVO);
		populateAttributes(damagedDSNVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getMessage(),
					createException);
		}
		populateChildren(damagedDSNVO);
	}

	/**
	 * A-3109
	 * 
	 * @param damagedDSNVO
	 * @throws SystemException
	 */
	private void populateChildren(DamagedDSNVO damagedDSNVO)
			throws SystemException {

		Collection<DamagedDSNDetailVO> damagedDetailVOs = damagedDSNVO
				.getDamagedDsnDetailVOs();
		if (damagedDetailVOs != null && damagedDetailVOs.size() > 0) {
			populateDamagedDSNDetails(damagedDetailVOs);
		}

	}

	/**
	 * A-3109
	 * 
	 * @param damagedDetailVOs
	 * @throws SystemException
	 */
	private void populateDamagedDSNDetails(
			Collection<DamagedDSNDetailVO> damagedDetailVOs)
			throws SystemException {
		if (getDamageDetails() == null) {
			damageDetails = new HashSet<DamagedDSNDetail>();
		}
		damagedDSNPK = getDamagedDSNPK();
		for (DamagedDSNDetailVO damageDetailVO : damagedDetailVOs) {
			// should be set from the Client Added By Karthick V
			// A work around tat has been done to add mailClass into
			// DamagedDetailVO
			damageDetailVO.setMailClass(damagedDSNPK.getMailSubclass()
					.substring(0, 1));
			damageDetails
					.add(new DamagedDSNDetail(damagedDSNPK, damageDetailVO));
		}
	}

	/**
	 * A-3109
	 * 
	 * @param damagedDSNVO
	 */
	private void populatePK(DamagedDSNVO damagedDSNVO) {
		damagedDSNPK = new DamagedDSNPK();
		damagedDSNPK.setCompanyCode(damagedDSNVO.getCompanyCode());
		damagedDSNPK.setDsn(damagedDSNVO.getDsn());
		damagedDSNPK.setOriginExchangeOffice(damagedDSNVO
				.getOriginExchangeOffice());
		damagedDSNPK.setDestinationExchangeOffice(damagedDSNVO
				.getDestinationExchangeOffice());
		damagedDSNPK.setMailCategoryCode(damagedDSNVO.getMailCategoryCode());
		damagedDSNPK.setMailSubclass(damagedDSNVO.getMailSubclass());
		damagedDSNPK.setYear(damagedDSNVO.getYear());
		damagedDSNPK.setConsignmentNumber(damagedDSNVO.getConsignmentNumber());
		damagedDSNPK.setConsignmentSequenceNumber(damagedDSNVO
				.getConsignmentSequenceNumber());
		damagedDSNPK.setPaCode(damagedDSNVO.getPaCode());
		damagedDSNPK.setAirportCode(damagedDSNVO.getAirportCode());
	}

	/**
	 * A-3109
	 * 
	 * @param damagedDSNVO
	 */
	private void populateAttributes(DamagedDSNVO damagedDSNVO) {
		setAcceptedBags(damagedDSNVO.getAcceptedBags()
				- damagedDSNVO.getLatestReturnedBags());
		/*setAcceptedWeight(damagedDSNVO.getAcceptedWeight()
				- damagedDSNVO.getLatestReturnedWeight());*/
		setAcceptedWeight(damagedDSNVO.getAcceptedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */
				- damagedDSNVO.getLatestReturnedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		setReturnedBags(damagedDSNVO.getLatestReturnedBags());
		//setReturnedWeight(damagedDSNVO.getLatestReturnedWeight());
		setReturnedWeight(damagedDSNVO.getLatestReturnedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);//added by A-7371
		setCarrierId(damagedDSNVO.getCarrierId());
		setFlightNumber(damagedDSNVO.getFlightNumber());
		setFlightSequenceNumber(damagedDSNVO.getFlightSequenceNumber());
		setSegmentSerialNumber(damagedDSNVO.getSegmentSerialNumber());
		setReturnStatus(damagedDSNVO.getReturnStatus());
		setLastUpdateUser(damagedDSNVO.getLastUpdateUser());
		setMailClass(damagedDSNVO.getMailClass());
	}

	/**
	 * @author A-3109
	 * @param damagedDSNPK
	 * @return
	 * @throws FinderException
	 * @throws SystemException
	 */
	public static DamagedDSN find(DamagedDSNPK damagedDSNPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(DamagedDSN.class,
				damagedDSNPK);
	}

	/**
	 * A-3109
	 * 
	 * @param dmgDetailVO
	 * @throws SystemException
	 */
	private void createDamagedDSNDetail(DamagedDSNDetailVO dmgDetailVO)
			throws SystemException {
		if (getDamageDetails() == null) {
			damageDetails = new HashSet<DamagedDSNDetail>();
		}
		log.log(Log.FINE,
				"The dmgDetailVO for Save createDamagedDSNDetail MTKDMGDSN",
				dmgDetailVO);
		damageDetails.add(new DamagedDSNDetail(getDamagedDSNPK(), dmgDetailVO));
	}

	/**
	 * A-3109
	 * 
	 * @param dmgDetailVO
	 * @return
	 */
	private DamagedDSNDetailPK constructDMGDetailPK(
			DamagedDSNDetailVO dmgDetailVO) {
		log.log(Log.FINE, "constructDMGDetailPK-MTKDMGDSN-dmgDetailVO ",
				dmgDetailVO);
		DamagedDSNDetailPK dmgDetailPK = new DamagedDSNDetailPK();
		damagedDSNPK = getDamagedDSNPK();
		dmgDetailPK.setCompanyCode(damagedDSNPK.getCompanyCode());
		dmgDetailPK.setDsn(damagedDSNPK.getDsn());
		dmgDetailPK.setOriginExchangeOffice(damagedDSNPK
				.getOriginExchangeOffice());
		dmgDetailPK.setDestinationExchangeOffice(damagedDSNPK
				.getDestinationExchangeOffice());
		dmgDetailPK.setMailCategoryCode(damagedDSNPK.getMailCategoryCode());
		dmgDetailPK.setMailSubclass(damagedDSNPK.getMailSubclass());
		dmgDetailPK.setYear(damagedDSNPK.getYear());
		dmgDetailPK.setConsignmentNumber(damagedDSNPK.getConsignmentNumber());
		dmgDetailPK.setConsignmentSequenceNumber(damagedDSNPK
				.getConsignmentSequenceNumber());
		dmgDetailPK.setPaCode(damagedDSNPK.getPaCode());
		dmgDetailPK.setDamageSequenceNumber(damagedDSNPK
				.getDamageSequenceNumber());
		dmgDetailPK.setAirportCode(damagedDSNPK.getAirportCode());
		dmgDetailPK.setDamageCode(dmgDetailVO.getDamageCode());
		return dmgDetailPK;
	}

	/**
	 * A-3109
	 * 
	 * @param damagedDSNVO
	 * @throws SystemException
	 * @throws MailbagDamageException
	 */
	public void update(DamagedDSNVO damagedDSNVO) throws SystemException,
			MailbagDamageException {
		log.entering("DamagedDSN", "update");

		for (DamagedDSNDetailVO dmgDetailVO : damagedDSNVO
				.getDamagedDsnDetailVOs()) {
			log.log(Log.FINE,
					" DamagedDSN.UPDATE --damagedDSNVO.getMailClass() ",
					damagedDSNVO.getMailClass());
			// Should be set from the Client Added for time Being By Karthick V
			// to
			// insert the record into MTKDMGDSNDTL
			dmgDetailVO.setMailClass(getMailClass());
			DamagedDSNDetail dmgDetail = null;
			try {
				dmgDetail = DamagedDSNDetail
						.find(constructDMGDetailPK(dmgDetailVO));
			} catch (FinderException ex) {
				createDamagedDSNDetail(dmgDetailVO);
			}
			if (dmgDetail != null) {
				int currDmgRetBags = dmgDetailVO.getLatestReturnedBags();
				// + dmgDetailVO.getReturnedBags();

				if (currDmgRetBags > this.getAcceptedBags()) {
					log.log(Log.FINEST, "exceeded for ",
							dmgDetailVO.getDamageDescription());
					throw new MailbagDamageException(
							DAMAGEDMAILS_EXCEED_ACCEPTEDMAILS,
							new String[] { dmgDetailVO.getDamageDescription() });
				}
				dmgDetail.update(dmgDetailVO);
			}
		}

		int currReturnedBags = damagedDSNVO.getLatestReturnedBags()
				+ getReturnedBags();
		/*double currReturnedWeight = damagedDSNVO.getLatestReturnedWeight()
				+ getReturnedWeight();*/
		double currReturnedWeight = damagedDSNVO.getLatestReturnedWeight().getRoundedSystemValue()
				+ getReturnedWeight();//added by A-7371
		setReturnedBags(currReturnedBags);
		setReturnedWeight(currReturnedWeight);

		setAcceptedBags(getAcceptedBags()
				- damagedDSNVO.getLatestReturnedBags());
		/*setAcceptedWeight(getAcceptedWeight()
				- damagedDSNVO.getLatestReturnedWeight());*/
		setAcceptedWeight(getAcceptedWeight()
				- damagedDSNVO.getLatestReturnedWeight().getSystemValue()/* As part of correcting the Measure usage in setting value to entity classes ICRD-288684 */);

		if (getAcceptedBags() == 0) {
			setReturnStatus(MailConstantsVO.RETURN_STATUS_FULL);
		} else {
			setReturnStatus(MailConstantsVO.RETURN_STATUS_PARTIAL);
		}
		setLastUpdateUser(damagedDSNVO.getLastUpdateUser());

		log.exiting("DamagedDSN", "update");
	}

	/**
	 * @author a-3109 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */
	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

}
