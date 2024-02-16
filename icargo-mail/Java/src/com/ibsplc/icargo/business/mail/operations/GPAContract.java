/*
 * GPAContract.java Created on JUL 23, 2006
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Calendar;
import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.GPAContractFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.GPAContractVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
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
 * @author A-6986
 *
 */
@Entity
@Table(name = "MALGPACTR")
@Staleable
public class GPAContract {

	private static final String MODULE_NAME = "mail.operations";

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private String origin;
	private String destination;
	private Calendar fromDate;
	private Calendar toDate;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private GPAContractPK gpaContractPK;
	private String contractID;
	private String region;
	private String amotflag;
	/**
	 * @return the origin
	 */
	@Column(name = "ORGARP")
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	@Column(name = "DSTARP")
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the fromDate
	 */
	@Column(name = "VLDFRM")
	public Calendar getFromDate() {
		return fromDate;
	}
	/**
	 * @param fromDate the fromDate to set
	 */
	public void setFromDate(Calendar fromDate) {
		this.fromDate = fromDate;
	}
	/**
	 * @return the toDate
	 */
	@Column(name = "VDLTOO")
	public Calendar getToDate() {
		return toDate;
	}
	/**
	 * @param toDate the toDate to set
	 */
	public void setToDate(Calendar toDate) {
		this.toDate = toDate;
	}
	/**
	 * @return the lastUpdatedTime
	 */

	@Column(name = "LSTUPDTIM")
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}
	/**
	 * @param lastUpdatedTime the lastUpdatedTime to set
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	/**
	 * @return the lastUpdatedUser
	 */
	@Column(name = "LSTUPDUSR")
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
	 * @return the gpaContractid
	 */
	//Added by A-8527 for BUG ICRD-308683
	/**
	 * @return the contractID
	 */
	@Column(name = "CTRIDR")
	public String getContractID() {
		return contractID;
	}
	/**
	 * @param contractID the contractID to set
	 */
	public void setContractID(String contractID) {
		this.contractID = contractID;
	}
	/**
	 * @return the region
	 */
	@Column(name = "REGCOD")
	public String getRegion() {
		return region;
	}
	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "AMOTFLG")
	/**
	 * @return the amotflag
	 */
	public String getAmotflag() {
		return amotflag;
	}
	/**
	 * @param amotflag the amotflag to set
	 */
	public void setAmotflag(String amotflag) {
		this.amotflag = amotflag;
	}
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpaCode", column = @Column(name = "GPACOD")),
			@AttributeOverride(name = "sernum", column = @Column(name = "SERNUM")) })

	public GPAContractPK getGpaContractPK() {
		return gpaContractPK;
	}
	/**
	 * @param gpaContractPK the gpaContractPK to set
	 */
	public void setGpaContractPK(GPAContractPK gpaContractPK) {
		this.gpaContractPK = gpaContractPK;
	}

	public GPAContract() {

	}
	public GPAContract(GPAContractVO gpaContractVO) throws SystemException {
		log.entering(MODULE_NAME, "GPAContract");

		populatePK(gpaContractVO);
		populateAttributes(gpaContractVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting(MODULE_NAME, "GPAContract");

	}

	private void populatePK(GPAContractVO gpaContractVO){
		gpaContractPK = new GPAContractPK();
		gpaContractPK.setCompanyCode(gpaContractVO.getCompanyCode());
		gpaContractPK.setGpaCode(gpaContractVO.getPaCode());
		this.setGpaContractPK(gpaContractPK);

	}

	private void populateAttributes(GPAContractVO gpaContractVO){
		setOrigin(gpaContractVO.getOriginAirports());
		setDestination(gpaContractVO.getDestinationAirports());
		LocalDate fromDateToSave = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		fromDateToSave.setDate(gpaContractVO.getCidFromDates());
		setFromDate(fromDateToSave.toCalendar());
		LocalDate toDateToSave = new LocalDate(LocalDate.NO_STATION, Location.NONE, true);
		toDateToSave.setDate(gpaContractVO.getCidToDates());
		setToDate(toDateToSave.toCalendar());
		setLastUpdatedTime(gpaContractVO.getLastUpdatedTime());
		setLastUpdatedUser(gpaContractVO.getLastUpdatedUser());
		//Added by A-8527 for BUG ICRD-308683
		setContractID(gpaContractVO.getContractIDs());
		setRegion(gpaContractVO.getRegions());
		setAmotflag(gpaContractVO.getAmot());
	}

	public static GPAContract find(GPAContractPK gpaContractPK)
			throws FinderException, SystemException {
		return PersistenceController.getEntityManager().find(
				GPAContract.class,gpaContractPK);
	}

	 /**
     * This method returns the Instance of the DAO
     * @return
     * @throws SystemException
     */
    private static MailTrackingDefaultsDAO constructDAO()
    		throws SystemException {
    	try {
    		EntityManager em = PersistenceController.getEntityManager();
    		return MailTrackingDefaultsDAO.class.cast(em.
    				getQueryDAO(MODULE_NAME));
    	}
    	catch(PersistenceException persistenceException) {
    		throw new SystemException(persistenceException.getErrorCode());
    	}
    }
    /**
	 *
	 * @throws SystemException
	 * @throws RemoveException
	 */
	public void remove() throws SystemException, RemoveException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

	}
	public void update (GPAContractVO gpaContractVO)throws SystemException{
		populateAttributes(gpaContractVO);

	}
	public static  Collection<GPAContractVO> listContractdetails(
			GPAContractFilterVO contractFilterVO) throws SystemException{
		try {
			return constructDAO().listContractdetails(contractFilterVO);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	public static  Collection<GPAContractVO> listODForContract(
			GPAContractFilterVO contractFilterVO) throws SystemException{
		try {
			return constructDAO().listContractdetails(contractFilterVO);
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
