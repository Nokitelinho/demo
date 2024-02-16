/*
 * ULDFlightMessageReconcile.java Created on Jul 20, 2006
 *
 * Copyright 2006 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import java.util.Set;
import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Version;

import com.ibsplc.icargo.business.uld.defaults.message.vo.FlightFilterMessageVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.message.vo.ULDFlightMessageReconcileVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsPersistenceConstants;

import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;

import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-2048
 * 
 * @generated "UML to Java
 *            (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
@Table(name = "ULDFLTMSGREC")
@Entity
public class ULDFlightMessageReconcile {

	private Log log = LogFactory.getLogger("ULD MANAGEMENT");

	private static final String MODULE = "uld.defaults";

	private ULDFlightMessageReconcilePK reconcilePK;

	private Calendar flightDate;

	private String errorCode;

	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;

	private Set<ULDFlightMessageReconcileDetails> reconcileDetails;

	private String outSequenceNumber;

	private String specialInformation;

	private String messageSendFlag;

	private String messageSource;//Added by a-3459 as part of ICRD-192413
	// Added by a-3459 as part of bug 29943 starts
	private Calendar actualDate;

	// Added by a-3459 as part of bug 29943 ends
    /**
     * 
     * 	Method		:	ULDFlightMessageReconcile.getMessageSource
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:   CRD-192413
     *	Parameters	:	@return 
     *	Return type	: 	String
     */
	@Column(name = "UCMMSGSRC")
	public String getMessageSource() {
		return messageSource;
	}
    /**
     * 
     * 	Method		:	ULDFlightMessageReconcile.setMessageSource
     *	Added by 	:	A-7359 on 24-Aug-2017
     * 	Used for 	:   ICRD-192413
     *	Parameters	:	@param messageSource 
     *	Return type	: 	void
     */
	public void setMessageSource(String messageSource) {
		this.messageSource = messageSource;
	}
	/**
	 * @return the actualDate
	 */
	@Column(name = "ACTDAT")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getActualDate() {
		return actualDate;
	}

	/**
	 * @param actualDate
	 *            the actualDate to set
	 */
	public void setActualDate(Calendar actualDate) {
		this.actualDate = actualDate;
	}

	/**
	 * @return String Returns the messageSendFlag.
	 */
	@Column(name = "MSGSNDFLG")
	public String getMessageSendFlag() {
		return this.messageSendFlag;
	}

	/**
	 * @param messageSendFlag
	 *            The messageSendFlag to set.
	 */
	public void setMessageSendFlag(String messageSendFlag) {
		this.messageSendFlag = messageSendFlag;
	}

	/**
	 * @return String Returns the specialInformation.
	 */
	@Column(name = "SPLINF")
	public String getSpecialInformation() {
		return this.specialInformation;
	}

	/**
	 * @param specialInformation
	 *            The specialInformation to set.
	 */
	public void setSpecialInformation(String specialInformation) {
		this.specialInformation = specialInformation;
	}

	/**
	 * @return String Returns the outSequenceNumber.
	 */
	@Column(name = "OUTSEQNUM")
	public String getOutSequenceNumber() {
		return this.outSequenceNumber;
	}

	/**
	 * @param outSequenceNumber
	 *            The outSequenceNumber to set.
	 */
	public void setOutSequenceNumber(String outSequenceNumber) {
		this.outSequenceNumber = outSequenceNumber;
	}

	/**
	 * @return String Returns the errorCode.
	 */
	@Column(name = "ERRCOD")
	public String getErrorCode() {
		return this.errorCode;
	}

	/**
	 * @param errorCode
	 *            The errorCode to set.
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * @return Calendar Returns the flightDate.
	 */
	@Column(name = "FLTDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return this.flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Calendar Returns the lastUpdatedTime.
	 */
	@Column(name = "LSTUPDTIM")
	@Version
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return this.lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime
	 *            The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return String Returns the lastUpdatedUser.
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return this.lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 *            The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return ULDFlightMessageReconcilePK Returns the reconcilePK.
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "flightCarrierIdentifier", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "messageType", column = @Column(name = "MSGTYP")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public ULDFlightMessageReconcilePK getReconcilePK() {
		return this.reconcilePK;
	}

	/**
	 * @param reconcilePK
	 *            The reconcilePK to set.
	 */
	public void setReconcilePK(ULDFlightMessageReconcilePK reconcilePK) {
		this.reconcilePK = reconcilePK;
	}

	/**
	 * @return Set<ULDFlightMessageReconcileDetails> Returns the
	 *         reconcileDetails.
	 */
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "FLTCARIDR", referencedColumnName = "FLTCARIDR", insertable = false, updatable = false),
			@JoinColumn(name = "FLTNUM", referencedColumnName = "FLTNUM", insertable = false, updatable = false),
			@JoinColumn(name = "FLTSEQNUM", referencedColumnName = "FLTSEQNUM", insertable = false, updatable = false),
			@JoinColumn(name = "ARPCOD", referencedColumnName = "ARPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable = false, updatable = false),
			@JoinColumn(name = "MSGTYP", referencedColumnName = "MSGTYP", insertable = false, updatable = false),
			@JoinColumn(name = "SEQNUM", referencedColumnName = "SEQNUM", insertable = false, updatable = false) })
	public Set<ULDFlightMessageReconcileDetails> getReconcileDetails() {
		return this.reconcileDetails;
	}

	/**
	 * @param reconcileDetails
	 *            The reconcileDetails to set.
	 */
	public void setReconcileDetails(
			Set<ULDFlightMessageReconcileDetails> reconcileDetails) {
		this.reconcileDetails = reconcileDetails;
	}

	/**
	 * 
	 * 
	 */
	public ULDFlightMessageReconcile() {

	}

	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public ULDFlightMessageReconcile(ULDFlightMessageReconcileVO reconcileVO)
			throws SystemException {
		log.entering("ULDFlightMessageReconcileDetails",
				"ULDFlightMessageReconcile");
		populatePK(reconcileVO);
		populateAttributes(reconcileVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}

		reconcileVO.setSequenceNumber(String.valueOf(this.getReconcilePK().getSequenceNumber()));

		populateChildren(reconcileVO);

	}

	/**
	 * 
	 * @param reconcileVO
	 */
	public void populatePK(ULDFlightMessageReconcileVO reconcileVO) {
		log.entering("ULDFlightMessageReconcileDetails", "populatePK");
		ULDFlightMessageReconcilePK messageReconcilePK = new ULDFlightMessageReconcilePK();
		messageReconcilePK.setFlightCarrierIdentifier(reconcileVO
				.getFlightCarrierIdentifier());
		log.log(Log.INFO, "%%%%%%%%%  reconcileVO.getFlightNumber() ",
				reconcileVO.getFlightNumber());
		if (reconcileVO.getFlightNumber().trim().length() == 1) {
			messageReconcilePK.setFlightNumber("000"
					+ reconcileVO.getFlightNumber());
		} else if (reconcileVO.getFlightNumber().trim().length() == 2) {
			messageReconcilePK.setFlightNumber("00"
					+ reconcileVO.getFlightNumber());
		} else if (reconcileVO.getFlightNumber().trim().length() == 3) {
			messageReconcilePK.setFlightNumber("0"
					+ reconcileVO.getFlightNumber());
		} else {
			messageReconcilePK.setFlightNumber(reconcileVO.getFlightNumber());
		}
		log.log(Log.INFO, "%%%%%%%%%  reconcileVO.getFlightNumber() ",
				reconcileVO.getFlightNumber());
		// added by a-3045 for bug18957 ends
		messageReconcilePK.setFlightSequenceNumber(reconcileVO
				.getFlightSequenceNumber());
		messageReconcilePK.setAirportCode(reconcileVO.getAirportCode());
		messageReconcilePK.setCompanyCode(reconcileVO.getCompanyCode());
		messageReconcilePK.setMessageType(reconcileVO.getMessageType());
		setReconcilePK(messageReconcilePK);

	}

	/**
	 * 
	 * @param reconcileVO
	 */
	public void populateAttributes(ULDFlightMessageReconcileVO reconcileVO)
			throws SystemException {
		log.entering("ULDFlightMessageReconcileDetails", "populateAttributes");
		LogonAttributes logon = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		if (reconcileVO.getFlightDate() != null) {
			setFlightDate(reconcileVO.getFlightDate().toCalendar());
		}
		setOutSequenceNumber(reconcileVO.getOutSequenceNumber());
		setErrorCode(reconcileVO.getErrorCode());
		setLastUpdatedUser(logon.getUserId());
		setMessageSendFlag(reconcileVO.getMessageSendFlag());
		setSpecialInformation(reconcileVO.getSpecialInformation());
		setActualDate(reconcileVO.getActualDate());
		if(reconcileVO.getMessageSource()!=null){
			setMessageSource(reconcileVO.getMessageSource());
		}else{
			setMessageSource(ULDFlightMessageReconcileVO.NIL);
		}
	}

	/**
	 * @param reconcileVO
	 * @throws SystemException
	 * 
	 * 
	 */
	public void populateChildren(ULDFlightMessageReconcileVO reconcileVO)
			throws SystemException {
		log.entering("ULDFlightMessageReconcileDetails", "PopulateChildren");
		if (reconcileVO.getReconcileDetailsVOs() != null
				&& reconcileVO.getReconcileDetailsVOs().size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileVO
					.getReconcileDetailsVOs()) {
				detailsVO.setFlightCarrierIdentifier(reconcileVO.getFlightCarrierIdentifier());
				detailsVO.setFlightNumber(reconcileVO.getFlightNumber());
				detailsVO.setFlightSequenceNumber(reconcileVO.getFlightSequenceNumber());				
				detailsVO.setAirportCode(reconcileVO.getAirportCode());
				detailsVO.setCompanyCode(reconcileVO.getCompanyCode());
				detailsVO.setMessageType(reconcileVO.getMessageType());
				detailsVO.setSequenceNumber(reconcileVO.getSequenceNumber());
				new ULDFlightMessageReconcileDetails(detailsVO);
			}
		}
	}

	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void update(ULDFlightMessageReconcileVO reconcileVO)
			throws SystemException {
		log.entering("ULDFlightMessageReconcileDetails", "update");
		populateAttributes(reconcileVO);
		updateChildren(reconcileVO);
	}

	/**
	 * 
	 * @param reconcileVO
	 * @throws SystemException
	 */
	public void updateChildren(ULDFlightMessageReconcileVO reconcileVO)
			throws SystemException {
		log.entering("ULDFlightMessageReconcileDetails", "updateChildren");
		if (reconcileVO.getReconcileDetailsVOs() != null
				&& reconcileVO.getReconcileDetailsVOs().size() > 0) {
			for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileVO
					.getReconcileDetailsVOs()) {
				if (ULDFlightMessageReconcileVO.OPERATION_FLAG_DELETE
						.equalsIgnoreCase(detailsVO.getOperationFlag())) {
					ULDFlightMessageReconcileDetails messageReconcileDetails = null;
					messageReconcileDetails = ULDFlightMessageReconcileDetails
							.find(detailsVO);
					messageReconcileDetails.remove();
				}
			}
			for (ULDFlightMessageReconcileDetailsVO detailsVO : reconcileVO
					.getReconcileDetailsVOs()) {
				if (ULDFlightMessageReconcileVO.OPERATION_FLAG_INSERT
						.equalsIgnoreCase(detailsVO.getOperationFlag())) {
					new ULDFlightMessageReconcileDetails(detailsVO);
				} else if (ULDFlightMessageReconcileVO.OPERATION_FLAG_UPDATE
						.equalsIgnoreCase(detailsVO.getOperationFlag())) {
					ULDFlightMessageReconcileDetails messageReconcileDetails = null;
					messageReconcileDetails = ULDFlightMessageReconcileDetails
							.find(detailsVO);
					messageReconcileDetails.update(detailsVO);
				}

			}
		}
	}

	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static ULDFlightMessageReconcile find(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException,
			FinderException {
		ULDFlightMessageReconcilePK messageReconcilePK = new ULDFlightMessageReconcilePK();
		messageReconcilePK.setFlightCarrierIdentifier(reconcileVO
				.getFlightCarrierIdentifier());
		messageReconcilePK.setFlightNumber(reconcileVO.getFlightNumber());
		messageReconcilePK.setFlightSequenceNumber(reconcileVO
				.getFlightSequenceNumber());
		messageReconcilePK.setAirportCode(reconcileVO.getAirportCode());
		messageReconcilePK.setCompanyCode(reconcileVO.getCompanyCode());
		messageReconcilePK.setMessageType(reconcileVO.getMessageType());
		messageReconcilePK.setSequenceNumber(Long.parseLong(reconcileVO.getSequenceNumber()));
		EntityManager entityManager = PersistenceController.getEntityManager();
		return entityManager.find(ULDFlightMessageReconcile.class,
				messageReconcilePK);
	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		if (this.getReconcileDetails() != null) {
			for (ULDFlightMessageReconcileDetails details : this
					.getReconcileDetails()) {
				details.remove();
			}
		}
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode());
		}
	}

	/**
	 * This method returns the ULDDefaultsDAO.
	 * 
	 * @throws SystemException
	 */
	private static ULDDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return ULDDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileVO listUCMMessage(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUCMMessage(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileVO listUCMINMessage(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUCMINMessage(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileVO listUCMOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUCMOUTMessage(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDFlightMessageReconcileVO> listUCMErrors(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUCMErrors(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @author A-1950
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findUCMNoLOV(FlightFilterMessageVO filterVO)
			throws SystemException {
		try {
			return constructDAO().findUCMNoLOV(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDFlightMessageReconcileDetailsVO> listUldErrors(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUldErrors(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * @author A-1950
	 * 
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDFlightMessageReconcileVO> listUCMsForComparison(
			FlightFilterMessageVO flightFilterVO) throws SystemException {
		try {
			return constructDAO().listUCMsForComparison(flightFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static Collection<ULDFlightMessageReconcileVO> listUCMsForFlightMovement(
			FlightFilterMessageVO flightFilterVO) throws SystemException {
		try {
			return constructDAO().listUCMsForFlightMovement(flightFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-1950
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileVO findCounterUCM(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException {
		try {
			return constructDAO().findCounterUCM(reconcileVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDFlightMessageReconcileDetailsVO> listUCMINOUTMessage(
			FlightFilterMessageVO filterVO) throws SystemException {
		try {
			return constructDAO().listUCMINOUTMessage(filterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param flightFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ULDFlightMessageReconcileDetailsVO> listUCMOUTForInOutMismatch(
			FlightFilterMessageVO flightFilterVO) throws SystemException {
		try {
			return constructDAO().listUCMOUTForInOutMismatch(flightFilterVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param flightMessageReconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static ULDFlightMessageReconcileVO checkForINOUT(
			ULDFlightMessageReconcileVO flightMessageReconcileVO)
			throws SystemException {
		try {
			return constructDAO().checkForINOUT(flightMessageReconcileVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findInMesssageAirports(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException {
		try {
			return constructDAO().findInMesssageAirports(reconcileVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}

	/**
	 * 
	 * A-1950
	 * 
	 * @param detailsVO
	 * @return
	 * @throws SystemException
	 */
	public static boolean checkForPoolOwners(
			ULDFlightMessageReconcileDetailsVO detailsVO)
			throws SystemException {
		try {
			return constructDAO().checkForPoolOwners(detailsVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author A-3459
	 * @param uldFlightMessageReconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static Page<ULDFlightMessageReconcileVO> findMissingUCMs(
			ULDFlightMessageReconcileVO uldFlightMessageReconcileVO)
			throws SystemException {
		Log log = LogFactory.getLogger("ULD");
		log.entering("ULDFlightMessageReconcile", "findMissingUCMs");
		EntityManager em = PersistenceController.getEntityManager();
		try {
			ULDDefaultsDAO uldDefaultsDAO = ULDDefaultsDAO.class.cast(em
					.getQueryDAO(ULDDefaultsPersistenceConstants.MODULE_NAME));
			return uldDefaultsDAO.findMissingUCMs(uldFlightMessageReconcileVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findFlightArrivalStatus(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException {
		try {
			return constructDAO().findFlightArrivalStatus(reconcileVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * 
	 * @param reconcileVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> findUcmInStatus(
			ULDFlightMessageReconcileVO reconcileVO) throws SystemException {
		try {
			return constructDAO().findUcmInStatus(reconcileVO);

		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
	/**
	 * 
	 * 	Method		:	ULDFlightMessageReconcile.findlatestUCMsFromAllSources
	 *	Added by 	:	A-7359 on 07-Sep-2017
	 * 	Used for 	:   ICRD-192413
	 *	Parameters	:	@param uldFlightMessageFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ArrayList<ULDFlightMessageReconcileVO>
	 */
	public static ArrayList<ULDFlightMessageReconcileVO> findlatestUCMsFromAllSources(FlightFilterMessageVO  uldFlightMessageFilterVO) throws SystemException{
		try {
			return constructDAO().findlatestUCMsFromAllSources(uldFlightMessageFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
}
