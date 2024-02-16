/*
 * Reservation.java Created on Jan 8, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.stockcontrol.defaults.reservation;

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

import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.reservation.vo.ReservationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockFilterVO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.stockcontrol.defaults.StockControlDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;

/**
 * @author A-1619
 *
 */
@Table(name = "STKRESAWB")
@Entity
// @Staleable
public class Reservation {
	private static final String MODULE_NAME = "stockcontrol.defaults";
	private static final String DOCUMENT_STATUS_RESERVED = "R";
	private ReservationPK reservationPK;

	private String shipmentPrefix;

	private String customerCode;

	private String documentType;

	private Calendar reservationDate;

	private Calendar expiryDate;

	private String documentStatus;

	private String reservationRemarks;

	private Calendar lastUpdateTime;

	private String lastUpdateUser;

	/**
	 * @return Returns the customerCode.
	 */
	@Column(name = "CUSCOD")
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * @param customerCode
	 *            The customerCode to set.
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * @return Returns the documentStatus.
	 */
	@Column(name = "DOCSTA")
	public String getDocumentStatus() {
		return documentStatus;
	}

	/**
	 * @param documentStatus
	 *            The documentStatus to set.
	 */
	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	/**
	 * @return Returns the documentType.
	 */
	@Column(name = "DOCTYP")
	public String getDocumentType() {
		return documentType;
	}

	/**
	 * @param documentType
	 *            The documentType to set.
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 * @return Returns the expiryDate.
	 */
	@Column(name = "EXPDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate
	 *            The expiryDate to set.
	 */
	public void setExpiryDate(Calendar expiryDate) {
		this.expiryDate = expiryDate;
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
	 * @return Returns the reservationDate.
	 */
	@Column(name = "RESDAT")
	@Temporal(TemporalType.DATE)
	public Calendar getReservationDate() {
		return reservationDate;
	}

	/**
	 * @param reservationDate
	 *            The reservationDate to set.
	 */
	public void setReservationDate(Calendar reservationDate) {
		this.reservationDate = reservationDate;
	}

	/**
	 * @return Returns the reservationPK.
	 *
	 *
	 */
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "documentNumber", column = @Column(name = "DOCNUM")),
			@AttributeOverride(name = "airlineIdentifier", column = @Column(name = "ARLIDR")) })
	public ReservationPK getReservationPK() {
		return reservationPK;
	}

	/**
	 * @param reservationPK
	 *            The reservationPK to set.
	 */
	public void setReservationPK(ReservationPK reservationPK) {
		this.reservationPK = reservationPK;
	}

	/**
	 * @return Returns the reservationRemarks.
	 */
	@Column(name = "RESRMK")
	public String getReservationRemarks() {
		return reservationRemarks;
	}

	/**
	 * @param reservationRemarks
	 *            The reservationRemarks to set.
	 */
	public void setReservationRemarks(String reservationRemarks) {
		this.reservationRemarks = reservationRemarks;
	}

	/**
	 * @return Returns the shipmentPrefix.
	 */
	@Column(name = "SHPPFX")
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	/**
	 * @param shipmentPrefix
	 *            The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	/**
	 *
	 */
	public Reservation() {

	}

	/**
	 *
	 * @param reservationVO
	 * @throws SystemException
	 */
	public void update(ReservationVO reservationVO) throws SystemException {
		this.setCustomerCode(reservationVO.getCustomerCode());
		this.setDocumentStatus(reservationVO.getDocumentStatus());
		this.setDocumentType(reservationVO.getDocumentSubType());
		if(reservationVO.getExpiryDate() != null){
			this.setExpiryDate(reservationVO.getExpiryDate().toCalendar());
		}
		if(reservationVO.getLastUpdateUser() != null){
			this.setLastUpdateUser(reservationVO.getLastUpdateUser());
		}
		if(reservationVO.getReservationDate() != null){
			this.setReservationDate(reservationVO.getReservationDate().toCalendar());
		}
		this.setReservationRemarks(reservationVO.getReservationRemarks());
		this.setShipmentPrefix(reservationVO.getShipmentPrefix());
		this.setLastUpdateTime(reservationVO.getLastUpdateTime());
	}

	/**
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException{
		PersistenceController.getEntityManager().remove(this);


	}

	private static StockControlDefaultsDAO constructDAO()
		throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return StockControlDefaultsDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(), persistenceException);
		}

	}


	/**
	 *
	 * @param reservationFilterVO
	 * @return Collection<ReservationVO>
	 * @throws SystemException
	 */
	public static Collection<ReservationVO> findReservationListing(
			ReservationFilterVO reservationFilterVO) throws SystemException {
		try {
			StockControlDefaultsDAO stockControlDefaultsDAO = StockControlDefaultsDAO.class
					.cast(PersistenceController
							.getEntityManager()
							.getQueryDAO(
									StockControlDefaultsPersistenceConstants.MODULE_NAME));
			return stockControlDefaultsDAO
					.findReservationListing(reservationFilterVO);

		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @param reservationVO
	 * @throws SystemException
	 * @throws CreateException
	 */
	public Reservation(ReservationVO reservationVO) throws SystemException,
			CreateException {
		populatePk(reservationVO);
		populateAttributes(reservationVO);
		PersistenceController.getEntityManager().persist(this);
	}

	private void populatePk(ReservationVO reservationVO) {
		ReservationPK reservationpk = new ReservationPK();
		reservationpk.setCompanyCode(reservationVO.getCompanyCode());
		reservationpk.setAirlineIdentifier(reservationVO.getAirlineIdentifier());
		reservationpk.setAirportCode(reservationVO.getAirportCode());
		reservationpk.setDocumentNumber(reservationVO.getDocumentNumber());
		this.reservationPK = reservationpk;
	}

	private void populateAttributes(ReservationVO reservationVO) {
		this.setCustomerCode(reservationVO.getCustomerCode());
		this.setDocumentType(reservationVO.getDocumentSubType());
		if(reservationVO.getExpiryDate() != null){
			this.setExpiryDate(reservationVO.getExpiryDate().toCalendar());
		}
		this.setLastUpdateUser(reservationVO.getLastUpdateUser());
		if(reservationVO.getReservationDate() != null){
			this.setReservationDate(reservationVO.getReservationDate().toCalendar());
		}
		this.setReservationRemarks(reservationVO.getReservationRemarks());
		this.setShipmentPrefix(reservationVO.getShipmentPrefix());
		this.setDocumentStatus(DOCUMENT_STATUS_RESERVED);
		this.setLastUpdateTime(reservationVO.getLastUpdateTime());
		
	}


	/**
	 * @param companyCode
	 * @param airlineIdentifier
	 * @param airportCode
	 * @param documentNumber
	 * @return
	 * @throws SystemException
	 */
	public static Reservation find(String companyCode, int airlineIdentifier,
			String airportCode, String documentNumber) throws SystemException {
		ReservationPK reservationpk = new ReservationPK();
		reservationpk.setCompanyCode(companyCode);
		reservationpk.setAirlineIdentifier(airlineIdentifier);
		reservationpk.setAirportCode(airportCode);
		reservationpk.setDocumentNumber(documentNumber);
		EntityManager em = PersistenceController.getEntityManager();
		Reservation reservation = null;

		try {

			reservation = em.find(Reservation.class, reservationpk);
		} catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode(),
					finderException);
		}
		return reservation;
	}

	/**
	 * @param reservationFilterVO
	 * @param expiryPeriod
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ReservationVO> findExpiredReserveAwbs(
			ReservationFilterVO reservationFilterVO, int expiryPeriod)
		throws SystemException{
		return constructDAO().findExpiredReserveAwbs(reservationFilterVO, expiryPeriod);
	}



	/**
	 * Checks whether any document in any range contained in the Stock,
	 * is already reserved
	 *
	 * @param stkAllocationVO
	 * @return
	 * @throws SystemException
	 */
/*	Commented for Query optimisation, ( removing the use of LPAD )
 * 	re-implemented with overLoaded method
 */
/*	public static Collection<String> checkReservedDocumentExists(StockAllocationVO stkAllocationVO)
			throws SystemException{
		try {
			return constructDAO().checkReservedDocumentExists(stkAllocationVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}
*/

	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<String> checkReservedDocumentExists(StockFilterVO filterVO)
			throws SystemException{
		try {
			return constructDAO().checkReservedDocumentExists(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}


	/**
	 * @param filterVO
	 * @return
	 * @throws SystemException
	 */
	public static ReservationVO findReservationDetails(DocumentFilterVO filterVO) throws SystemException{
		try {
			return constructDAO().findReservationDetails(filterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
	}



}
