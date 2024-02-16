/*
 * MRAFuelChargeMaster.java Created on Apr 23, 2009
 *
 * Copyright 2009 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.rmi.RemoteException;
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

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FuelSurchargeVO;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsDAO;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
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
 * @author A-2391
 *
 */

@Entity
@Table(name = "MTKMRAFULCHGMST")
@Staleable
@Deprecated
public class MRAFuelChargeMaster {

	private MRAFuelChargeMasterPK mRAFuelChargeMasterPK;

	 private String rateInd;

	 private double fulChg;
	 
	 private String currency;
	 
	private Calendar validityStartDate;

	private Calendar validityEndDate;

	private Calendar lastUpdatedTime;
	
	private Log log = LogFactory.getLogger("MRA_DEFAULTS");
	
	private String lastUpdatedUser;

	

	public MRAFuelChargeMaster() {
	}

	public MRAFuelChargeMaster(FuelSurchargeVO surchargeVO) throws SystemException {
		log.log(Log.INFO, "vo in master entity ", surchargeVO);
		MRAFuelChargeMasterPK mRAFuelChargeMasterpk=new MRAFuelChargeMasterPK(surchargeVO.getCompanyCode(),
				surchargeVO.getGpaCode(),surchargeVO.getSeqNum());
		this.setMRAFuelChargeMasterPK(mRAFuelChargeMasterpk);
		populateAttributes(surchargeVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException e) {
			throw new SystemException(e.getErrorCode());
		}

	}

	/**
	 * @return the lastUpdatedTime
	 */
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
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
	 * @return the lastUPdatedUser
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}


	/**
	 * @return Returns the rateLinePK.
	 */

	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "gpaCode", column = @Column(name = "POACOD")),
			@AttributeOverride(name = "seqNum", column = @Column(name = "SEQNUM")) })
	public MRAFuelChargeMasterPK getMRAFuelChargeMasterPK() {
		return mRAFuelChargeMasterPK;
	}

	

	/**
	 * @return Returns the validityEndDate.
	 */

	@Column(name = "VLDTOO")
	@Temporal(TemporalType.DATE)
	public Calendar getValidityEndDate() {
		return validityEndDate;
	}

	/**
	 * @return Returns the validityStartDate.
	 */

	@Column(name = "VLDFRM")
	@Temporal(TemporalType.DATE)
	public Calendar getValidityStartDate() {
		return validityStartDate;
	}

	

	/**
	 * @param validityEndDate The validityEndDate to set.
	 */
	public void setValidityEndDate(Calendar validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	/**
	 * @param validityStartDate The validityStartDate to set.
	 */
	public void setValidityStartDate(Calendar validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	/**
	 * @param companyCode
	 * @param rateCardID
	 * @param companyCode
	 * @param ratelineSequenceNumber
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static MRAFuelChargeMaster find(String companyCode, String gpaCode,
			int seqNum) throws SystemException, FinderException {

		MRAFuelChargeMasterPK mRAFuelChargeMasterpk = new MRAFuelChargeMasterPK(companyCode, gpaCode,
				seqNum);
		MRAFuelChargeMaster mRAFuelChargeMaster = null;
		try {
			mRAFuelChargeMaster = PersistenceController.getEntityManager().find(
					MRAFuelChargeMaster.class, mRAFuelChargeMasterpk);
		} catch (FinderException e) {
			throw new SystemException(e.getErrorCode(), e);
		}
		return mRAFuelChargeMaster;
	}

	
	

	/**
	 * @param rateLineVO
	 * @return
	 * @throws SystemException
	 */
	public void update(FuelSurchargeVO surchargeVO) throws SystemException {

		populateAttributes(surchargeVO);
	}

	/**
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws SystemException {
		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException e) {
			throw new SystemException(e.getMessage(), e);
		}
	}

	private void populateAttributes(FuelSurchargeVO surchargeVO){
		this.setRateInd(surchargeVO.getRateCharge());
		this.setFulChg(Double.parseDouble(surchargeVO.getValues()));
		this.setCurrency(surchargeVO.getCurrency());
		this.setValidityStartDate(surchargeVO.getValidityStartDate());
		this.setValidityEndDate(surchargeVO.getValidityEndDate());
		this.setLastUpdatedUser(surchargeVO.getLastUpdateUser());
		this.setLastUpdatedTime(surchargeVO.getLastUpdateTime());
	}

	/**
	 * @return Returns the currency.
	 */
	@Column(name = "CURCOD")
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency The currency to set.
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return Returns the fulChg.
	 */
	@Column(name = "FULCHG")
	public double getFulChg() {
		return fulChg;
	}

	/**
	 * @param fulChg The fulChg to set.
	 */
	public void setFulChg(double fulChg) {
		this.fulChg = fulChg;
	}

	

	/**
	 * @param mraFuelChargeMasterPK The mraFuelChargeMasterPK to set.
	 */
	public void setMRAFuelChargeMasterPK(MRAFuelChargeMasterPK mRAFuelChargeMasterPK) {
		this.mRAFuelChargeMasterPK = mRAFuelChargeMasterPK;
	}

	/**
	 * @return Returns the rateInd.
	 */
	@Column(name = "RATIND")
	public String getRateInd() {
		return rateInd;
	}

	/**
	 * @param rateInd The rateInd to set.
	 */
	public void setRateInd(String rateInd) {
		this.rateInd = rateInd;
	}
	/**
	 * @param companyCode
	 * @param invoicKey
	 * @param poaCode
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Collection<FuelSurchargeVO> displayFuelSurchargeDetails(String gpaCode,String cmpCod)
 	throws BusinessDelegateException,RemoteException, SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults")).displayFuelSurchargeDetails(
									gpaCode, cmpCod);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	/**
	 * @param fuelSurchargeVO
	 * @return int
	 * @throws SystemException
	 */
	public static int findExistingFuelSurchargeVOs(FuelSurchargeVO fuelSurchargeVO)
 	throws BusinessDelegateException,RemoteException, SystemException {
		try {
			return MRADefaultsDAO.class.cast(
					PersistenceController.getEntityManager().getQueryDAO(
							"mail.mra.defaults")).findExistingFuelSurchargeVOs(
									fuelSurchargeVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getMessage());
		}
	}
	
}
