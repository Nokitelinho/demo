/*
 * MLDConfiguration.java Created on Dec 17, 2015
 *
 * Copyright 2015 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.Collection;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MLDConfigurationVO;
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
 * To save the MLD configurations for carrier-airport pair
 * 
 * @author A-5526
 * 
 */
@Entity
@Table(name = "MALMLDCFG")
@Staleable
public class MLDConfiguration {

	private static final String MAILTRACKING_DEFAULTS = "mail.operations";
	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private MLDConfigurationPK mLDConfigurationPK;
	//Removed carrierCode as part of  bug ICRD-143797 by A-5526


	private String allocationRequired;

	private String receivedRequired;

	private String upliftedRequired;

	private String hndRequired;

	private String deliveredRequired;
	
	//Added for CRQ ICRD-135130 by A-8061 starts
	 private String mldversion; 
	 private String stagedRequired;
	 private String nestedRequired;
	 private String receivedFromFightRequired;
	 private String transferredFromOALRequired;
	 private String receivedFromOALRequired;
	 private String returnedRequired;
	//Added for CRQ ICRD-135130 by A-8061 end
	
	

	public MLDConfiguration() {

	}

	/**
	 * @return Returns the mailResditPK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "airportCode", column = @Column(name = "ARPCOD")),
			@AttributeOverride(name = "carrierIdentifier", column = @Column(name = "CARIDR")) })
	public MLDConfigurationPK getMLDConfigurationPK() {
		return mLDConfigurationPK;
	}

	/**
	 * @param mailResditPK
	 *            The mailResditPK to set.
	 */
	public void setMLDConfigurationPK(MLDConfigurationPK mLDConfigurationPK) {
		this.mLDConfigurationPK = mLDConfigurationPK;
	}



	@Column(name = "ALLREQFLG")
	public String getAllocationRequired() {
		return allocationRequired;
	}

	public void setAllocationRequired(String allocationRequired) {
		this.allocationRequired = allocationRequired;
	}

	@Column(name = "RECREQFLG")
	public String getReceivedRequired() {
		return receivedRequired;
	}

	public void setReceivedRequired(String receivedRequired) {
		this.receivedRequired = receivedRequired;
	}

	@Column(name = "UPLREQFLG")
	public String getUpliftedRequired() {
		return upliftedRequired;
	}

	public void setUpliftedRequired(String upliftedRequired) {
		this.upliftedRequired = upliftedRequired;
	}

	@Column(name = "HNDREQFLG")
	public String getHndRequired() {
		return hndRequired;
	}

	public void setHndRequired(String hndRequired) {
		this.hndRequired = hndRequired;
	}

	@Column(name = "DLVREQFLG")
	public String getDeliveredRequired() {
		return deliveredRequired;
	}

	public void setDeliveredRequired(String deliveredRequired) {
		this.deliveredRequired = deliveredRequired;
	}

	/**
	 * 
	 * @param mLDConfigurationVO
	 * @throws SystemException
	 */
	public MLDConfiguration(MLDConfigurationVO mLDConfigurationVO)
			throws SystemException {
		log.entering("MLDConfiguration", "init");
		populatePK(mLDConfigurationVO);
		populateAttributes(mLDConfigurationVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
		log.exiting("MLDConfiguration", "init");
	}

	/**
	 * Method to populate attributes
	 * 
	 * @param mLDConfigurationVO
	 */
	private void populateAttributes(MLDConfigurationVO mLDConfigurationVO) {
		//Removed setCarrierCode as part of bug ICRD-143797 
		setAllocationRequired(mLDConfigurationVO.getAllocatedRequired());
		setDeliveredRequired(mLDConfigurationVO.getDeliveredRequired());
		setHndRequired(mLDConfigurationVO.gethNDRequired());
		setReceivedRequired(mLDConfigurationVO.getReceivedRequired());
		setUpliftedRequired(mLDConfigurationVO.getUpliftedRequired());
		//Added for CRQ ICRD-135130 by A-8061 starts
		setMldversion(mLDConfigurationVO.getMldversion());
		setStagedRequired(mLDConfigurationVO.getStagedRequired());
		setNestedRequired(mLDConfigurationVO.getNestedRequired());
		setReceivedFromFightRequired(mLDConfigurationVO.getReceivedFromFightRequired());
		setTransferredFromOALRequired(mLDConfigurationVO.getTransferredFromOALRequired());
		setReceivedFromOALRequired(mLDConfigurationVO.getReceivedFromOALRequired());
		setReturnedRequired(mLDConfigurationVO.getReturnedRequired());
		//Added for CRQ ICRD-135130 by A-8061 end

	}
	@Column(name = "MLDVER")
	public String getMldversion() {
		return mldversion;
	}

	public void setMldversion(String mldversion) {
		this.mldversion = mldversion;
	}
	@Column(name = "STGREQFLG")
	public String getStagedRequired() {
		return stagedRequired;
	}

	public void setStagedRequired(String stagedRequired) {
		this.stagedRequired = stagedRequired;
	}
	@Column(name = "NSTREQFLG")
	public String getNestedRequired() {
		return nestedRequired;
	}

	public void setNestedRequired(String nestedRequired) {
		this.nestedRequired = nestedRequired;
	}
	@Column(name = "RCFREQFLG")
	public String getReceivedFromFightRequired() {
		return receivedFromFightRequired;
	}

	public void setReceivedFromFightRequired(String receivedFromFightRequired) {
		this.receivedFromFightRequired = receivedFromFightRequired;
	}
	@Column(name = "TFDREQFLG")
	public String getTransferredFromOALRequired() {
		return transferredFromOALRequired;
	}

	public void setTransferredFromOALRequired(String transferredFromOALRequired) {
		this.transferredFromOALRequired = transferredFromOALRequired;
	}
	@Column(name = "RCTREQFLG")
	public String getReceivedFromOALRequired() {
		return receivedFromOALRequired;
	}

	public void setReceivedFromOALRequired(String receivedFromOALRequired) {
		this.receivedFromOALRequired = receivedFromOALRequired;
	}
	@Column(name = "RETREQFLG")
	public String getReturnedRequired() {
		return returnedRequired;
	}

	public void setReturnedRequired(String returnedRequired) {
		this.returnedRequired = returnedRequired;
	}

	/**
	 * Method to populate MLDConfigurationPK
	 * 
	 * @param mLDConfigurationVO
	 */
	private void populatePK(MLDConfigurationVO mLDConfigurationVO) {
		log.entering("MLDConfiguration", "populatePK");
		log.log(Log.FINE, "THE mLDConfigurationVO VO>>>>>>>>>>",
				mLDConfigurationVO);
		mLDConfigurationPK = new MLDConfigurationPK();
		mLDConfigurationPK.setCompanyCode(mLDConfigurationVO.getCompanyCode());
		mLDConfigurationPK.setAirportCode(mLDConfigurationVO.getAirportCode());
		mLDConfigurationPK.setCarrierIdentifier(mLDConfigurationVO
				.getCarrierIdentifier());
		log.exiting("MLDConfiguration", "populatepK");

	}

	/**
	 * 
	 * @param MLDConfigurationPK
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */

	public static MLDConfiguration find(MLDConfigurationPK mLDConfigurationPK)
			throws SystemException, FinderException {
		return PersistenceController.getEntityManager().find(
				MLDConfiguration.class, mLDConfigurationPK);

	}

	/**
	 * 
	 * @throws SystemException
	 */
	public void remove() throws SystemException {

		try {
			PersistenceController.getEntityManager().remove(this);
		} catch (RemoveException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}
	}

	/**
	 * @return
	 * @throws SystemException
	 */
	public static MailTrackingDefaultsDAO constructDAO() throws SystemException {
		try {
			return MailTrackingDefaultsDAO.class.cast(PersistenceController
					.getEntityManager().getQueryDAO(MAILTRACKING_DEFAULTS));
		} catch (PersistenceException exception) {
			throw new SystemException("No dao impl found", exception);
		}
	}

	/**
	 * This method is to find MLDConfigurations
	 * 
	 * @param mLDConfigurationFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static Collection<MLDConfigurationVO> findMLDCongfigurations(
			MLDConfigurationFilterVO mLDConfigurationFilterVO)
			throws SystemException {

		try {
			return constructDAO().findMLDCongfigurations(
					mLDConfigurationFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
