/*
 * PartnerCarrier.java Created on Aug 10, 2006
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.mail.operations.vo.PartnerCarrierVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-1303
 * 
 */
@Entity
@Table(name = "MALPRTCAR")
public class PartnerCarrier {
	private PartnerCarrierPK partnerCarrierPK;

	
	private int partnerCarrierId;

	private String partnerCarrierName;
	
	private String lastUpdateUser;

	private Calendar lastUpdateTime;
	
	private String mldTfdReq;

	private static final String MODULE = "mail.operations";

	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/*
	 * The Default Constructor
	 */
	public PartnerCarrier() {
	}

	/**
	 * @param partnerCarrierVo
	 * @throws SystemException
	 */
	public PartnerCarrier(PartnerCarrierVO partnerCarrierVo)
			throws SystemException {
		populatePK(partnerCarrierVo);
		populateAtrributes(partnerCarrierVo);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	

	/**
	 * @param partnerCarrierVo
	 */
	private void populatePK(PartnerCarrierVO partnerCarrierVo) {
		PartnerCarrierPK partnerCarrierPk = new PartnerCarrierPK();
		partnerCarrierPk.setCompanyCode(   partnerCarrierVo.getCompanyCode());
		partnerCarrierPk.setAirportCode(   partnerCarrierVo.getAirportCode());
		partnerCarrierPk.setOwnCarrierCode(   partnerCarrierVo.getOwnCarrierCode());
		partnerCarrierPk.setPartnerCarrierCode(   partnerCarrierVo
				.getPartnerCarrierCode());
		this.setPartnerCarrierPK(partnerCarrierPk);
	}

	/**
	 * @author A-1936
	 * @param partnerCarrierVo
	 * @throws SystemException
	 */
	private void populateAtrributes(PartnerCarrierVO partnerCarrierVo)
			throws SystemException {
		this.setPartnerCarrierId(Integer.parseInt(partnerCarrierVo.getPartnerCarrierId()));
		this.setPartnerCarrierName(partnerCarrierVo.getPartnerCarrierName());
		this.setLastUpdateUser(partnerCarrierVo.getLastUpdateUser());
	}


	/**
	 * @return Returns the partnerCarrierName.
	 */
	@Column(name = "PRTCARNAM")
	public String getPartnerCarrierName() {
		return partnerCarrierName;
	}
	@Column(name = "PRTCARIDR")
	public int getPartnerCarrierId() {
		return partnerCarrierId;
	}

	public void setPartnerCarrierId(int partnerCarrierId) {
		this.partnerCarrierId = partnerCarrierId;
	}

	/**
	 * @param partnerCarrierName
	 *            The partnerCarrierName to set.
	 */
	public void setPartnerCarrierName(String partnerCarrierName) {
		this.partnerCarrierName = partnerCarrierName;
	}
	
	@Column(name = "MLDTFDREQ")
	public String getMldTfdReq() {
		return mldTfdReq;
	}

	public void setMldTfdReq(String mldTfdReq) {
		this.mldTfdReq = mldTfdReq;
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
	 * @return Returns the partnerCarrierPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "ownCarrierCode", column = @Column(name = "OWNCARCOD")),
			@AttributeOverride(name = "partnerCarrierCode", column = @Column(name = "PRTCARCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")) })
	public PartnerCarrierPK getPartnerCarrierPK() {
		return partnerCarrierPK;
	}

	/**
	 * @param partnerCarrierPK
	 *            The partnerCarrierPK to set.
	 */
	public void setPartnerCarrierPK(PartnerCarrierPK partnerCarrierPK) {
		this.partnerCarrierPK = partnerCarrierPK;
	}

	/**
	 * @param partnerCarrierPk
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static PartnerCarrier find(PartnerCarrierPK partnerCarrierPk)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(PartnerCarrier.class, partnerCarrierPk);
	}

	/**
	 * @author A-1936
	 * @param partnerCarrierVo
	 * @throws SystemException
	 */
	public void update(PartnerCarrierVO partnerCarrierVo)
			throws SystemException {
		populateAtrributes(partnerCarrierVo);
		this.setLastUpdateTime(partnerCarrierVo.getLastUpdateTime());
	}

	/**
	 * @author a-1876 This method is used to list the PartnerCarriers.
	 * @param companyCode
	 * @param ownCarrierCode
	 * @param airportCode
	 * @return Collection<PartnerCarrierVO>
	 * @throws SystemException
	 */
	public static Collection<PartnerCarrierVO> findAllPartnerCarriers(
			String companyCode, String ownCarrierCode, String airportCode)
			throws SystemException {
		try {
			return constructDAO().findAllPartnerCarriers(companyCode,
					ownCarrierCode, airportCode);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * @author a-1936 methods the DAO instance ..
	 * @return
	 * @throws SystemException
	 */

	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em.getQueryDAO(MODULE));
		} catch (PersistenceException ex) {
			ex.getErrorCode();
			throw new SystemException(ex.getMessage());
		}
	}

	/**
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException removeException) {
			throw new SystemException(removeException.getErrorCode(),
					removeException);
		}
	}

	
	/**
	 * @author A-1936 This method is used to validatePartnerCarrier
	 * @param companyCode
	 * @param airlineCode
	 * @return
	 * @throws SystemException
	 */
	public AirlineValidationVO validatePartnerCarrier(String companyCode,
			String airlineCode) throws SystemException {
		AirlineValidationVO airlineValidationVO = null;
		try {
			airlineValidationVO = new SharedAirlineProxy().validateAlphaCode(
					companyCode, airlineCode);
		} catch (SharedProxyException ex) {
			log.log(Log.INFO, "<<<<<<INVALID AIRLINE >>>>>");
		}
		return airlineValidationVO;
	}

}
