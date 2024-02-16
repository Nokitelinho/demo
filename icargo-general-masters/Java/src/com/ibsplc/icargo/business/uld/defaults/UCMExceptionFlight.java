/*
 * UCMExceptionFlight.java Created on Jan 5, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverrides;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.ibsplc.icargo.business.uld.defaults.vo.UCMExceptionFlightVO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1936 This entity is used to save all the Flights which contains a
 *         "SPACE" to load the ULDs of the Other Flights ..
 *         UCM Message Needs to be sent manually for these Flights....
 * 
 * 
 */
@Entity
@Table(name = "ULDUCMEXPFLT")
@Staleable
public class UCMExceptionFlight {

	private Log log = LogFactory.getLogger("ULD_DEFAULTS");

	private static final String MODULE = "uld.defaults";

	/**
	 * The UCMExceptionFlightPK
	 */
	private UCMExceptionFlightPK ucmExceptionFlight;

	/**
	 * The carrierCode
	 */
	private String carrierCode;

	/**
	 * The flightDate
	 */

	private Calendar flightDate;

	/**
	 * The Pol
	 */
	private String pol;

	/**
	 * The default constructor
	 * 
	 */
	public UCMExceptionFlight() {
	}

	/**
	 * @author A-1936
	 * The Constructot tat is used to persist the Entity Details .
	 * @param ucmExceptionFlightVO
	 * @throws SystemException
	 */
	public UCMExceptionFlight(UCMExceptionFlightVO ucmExceptionFlightVO)
			throws SystemException {
		log.entering("UldExceptionFligt", "Persist"); 
		log.log(Log.FINE, "The  flight Vo for the Save ", ucmExceptionFlightVO);
		populatePK(ucmExceptionFlightVO);
		populateAttributes(ucmExceptionFlightVO);
		EntityManager em = PersistenceController.getEntityManager();
		try {
			em.persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
		log.exiting("UldExceptionFligt", "Persist");
	}

	/**
	 * @author A-1936 This method is used to populate the PK
	 * @param ucmExceptionFlightVO
	 * @throws SystemException
	 */
	private void populatePK(UCMExceptionFlightVO ucmExceptionFlightVO)
			throws SystemException {
		log.entering("ULDException Flight ", "populatePK");
		UCMExceptionFlightPK exceptionFlightPK = new UCMExceptionFlightPK();
		exceptionFlightPK.setCompanyCode(ucmExceptionFlightVO.getCompanyCode());
		exceptionFlightPK.setCarrierId(ucmExceptionFlightVO.getCarrierId());
		exceptionFlightPK.setFlightNumber(ucmExceptionFlightVO.getFlightNumber());
		exceptionFlightPK.setLegSerialNumber(ucmExceptionFlightVO
				.getLegSerialNumber());
		exceptionFlightPK.setFlightSequenceNumber(ucmExceptionFlightVO
				.getFlightSequenceNumber());
		this.setUcmExceptionFlight(exceptionFlightPK);
	}

	/**
	 * @author A-1936 This method is used to populate the Attributes of the
	 *         Entity
	 * @param ucmExceptionFlightVO
	 * @throws SystemException
	 */

	private void populateAttributes(UCMExceptionFlightVO ucmExceptionFlightVO)
			throws SystemException {
		log.entering("ULDExceptionFlight", "populatePK");
		this.setCarrierCode(ucmExceptionFlightVO.getCarrierCode());
		this.setPol(ucmExceptionFlightVO.getPol());
		if (ucmExceptionFlightVO.getFlightDate() != null) {
			this.setFlightDate(ucmExceptionFlightVO.getFlightDate()
					.toCalendar());
		}

	}

	/**
@Entity
	 * @param ucmExceptionFlightVO
	 * @throws SystemException
	 */
	public void update(UCMExceptionFlightVO ucmExceptionFlightVO)
			throws SystemException {
		log.entering("ULDException Flight ", "update");
		populateAttributes(ucmExceptionFlightVO);
	}

	/**
	 * @return Returns the carrierCode.
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

	/**
	 * @return Returns the flightDate.
	 */
	@Column(name = "FLTDAT")

	@Temporal(TemporalType.DATE)
	public Calendar getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(Calendar flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the pol.
	 */
	@Column(name = "POL")
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol
	 *            The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the ucmExceptionFlight.
	 */

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "carrierId", column = @Column(name = "FLTCARIDR")),
			@AttributeOverride(name = "flightNumber", column = @Column(name = "FLTNUM")),
			@AttributeOverride(name = "flightSequenceNumber", column = @Column(name = "FLTSEQNUM")),
			@AttributeOverride(name = "legSerialNumber", column = @Column(name = "LEGSERNUM")) })
	public UCMExceptionFlightPK getUcmExceptionFlight() {
		return ucmExceptionFlight;
	}

	/**
	 * @param ucmExceptionFlight
	 *            The ucmExceptionFlight to set.
	 */
	public void setUcmExceptionFlight(UCMExceptionFlightPK ucmExceptionFlight) {
		this.ucmExceptionFlight = ucmExceptionFlight;
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
	 * This method is used to find all the fligts For which the UCM has to be
	 * sent for the ULDs Manually ..
	 * 
	 * @param companyCode
	 * @param pol
	 * @return
	 * @throws SystemException
	 */
	public static Collection<UCMExceptionFlightVO> findExceptionFlights(
			String companyCode, String pol) throws SystemException {
		try {
			return constructDAO().findExceptionFlights(companyCode, pol);
		} catch (PersistenceException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}

	/**
	 * 
	 * This method is used to find the Instance of the Entity
	 * 
	 * @param ucmExceptionFlightPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static UCMExceptionFlight find(
			UCMExceptionFlightPK ucmExceptionFlightPK) throws SystemException,
			FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(UCMExceptionFlight.class, ucmExceptionFlightPK);

	}

	/**
	 * @author A-1936 This method is used to remove the business object. It
	 *         interally calls the remove method within EntityManager
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		log.entering("INSIDE THE ULDExceptionFlight", "REMOVE");
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}

}
