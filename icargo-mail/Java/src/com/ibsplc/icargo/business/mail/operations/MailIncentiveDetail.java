/*
 * MailIncentiveDetail.java Created on SEP 10, 2018
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

import com.ibsplc.icargo.business.mail.operations.vo.IncentiveConfigurationDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-6986
 *
 */
@Entity
@Table(name = "MALMRAINCCFGDTL")
@Staleable
public class MailIncentiveDetail {

	private static final String MODULE_NAME = "mail.operations";

	private Log log = LogFactory.getLogger("MAILTRACKING_DEFAULTS");

	private static final String KEY_INCENTIVE_CONFIGURATION =
			"INCENTIVE_CONFIGURATION";

	private String parameterType;
	private String parameterCode;
	private String parameterValue;
	private String excludeFlag;
	private Calendar lastUpdatedTime;
	private String lastUpdatedUser;
	private MailIncentiveDetailPK mailIncentiveDetailPK;

	/**
	 * @return the parameterType
	 */
	@Column(name = "PARTYP")
	public String getParameterType() {
		return parameterType;
	}
	/**
	 * @param parameterType the parameterType to set
	 */
	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}
	/**
	 * @return the parameterCode
	 */
	@Column(name = "PARCOD")
	public String getParameterCode() {
		return parameterCode;
	}
	/**
	 * @param parameterCode the parameterCode to set
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	/**
	 * @return the parameterValue
	 */
	@Column(name = "PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}
	/**
	 * @param parameterValue the parameterValue to set
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	/**
	 * @return the excludeFlag
	 */
	@Column(name = "EXCFLG")
	public String getExcludeFlag() {
		return excludeFlag;
	}
	/**
	 * @param excludeFlag the excludeFlag to set
	 */
	public void setExcludeFlag(String excludeFlag) {
		this.excludeFlag = excludeFlag;
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
	 * @return the mailIncentiveDetailPK
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "incentiveSerialNumber", column = @Column(name = "INSSERNUM")),
			@AttributeOverride(name = "sequenceNumber", column = @Column(name = "SEQNUM")) })
	public MailIncentiveDetailPK getMailIncentiveDetailPK() {
		return mailIncentiveDetailPK;
	}
	/**
	 * @param mailIncentiveDetailPK the mailIncentiveDetailPK to set
	 */
	public void setMailIncentiveDetailPK(MailIncentiveDetailPK mailIncentiveDetailPK) {
		this.mailIncentiveDetailPK = mailIncentiveDetailPK;
	}

	public MailIncentiveDetail() {

	}

	public MailIncentiveDetail(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO,String incentiveFlag) throws SystemException {
		log.entering(MODULE_NAME, "MailIncentiveDetail");

		generateSequnceNumberForIncentiveDetail(incentiveConfigurationDetailVO);
		populatePK(incentiveConfigurationDetailVO);
		populateAttributes(incentiveConfigurationDetailVO, incentiveFlag);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException exception) {
			throw new SystemException(exception.getMessage(), exception);
		}

		log.exiting(MODULE_NAME, "MailIncentiveDetail");

	}


	private void populatePK(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO){

		MailIncentiveDetailPK mailIncentiveDetailPK = new MailIncentiveDetailPK();
		mailIncentiveDetailPK.setCompanyCode(incentiveConfigurationDetailVO.getCompanyCode());
		mailIncentiveDetailPK.setIncentiveSerialNumber(incentiveConfigurationDetailVO.getIncentiveSerialNumber());
		mailIncentiveDetailPK.setSequenceNumber(Integer.parseInt(incentiveConfigurationDetailVO.getSequenceNumber()));

		this.mailIncentiveDetailPK = mailIncentiveDetailPK;
	}

	private void populateAttributes(IncentiveConfigurationDetailVO incentiveConfigurationDetailVO,String incentiveFlag){
		if(MailConstantsVO.FLAG_YES.equals(incentiveFlag)){

			this.setParameterCode(incentiveConfigurationDetailVO.getIncParameterCode());
			this.setParameterType(incentiveConfigurationDetailVO.getIncParameterType());
			this.setParameterValue(incentiveConfigurationDetailVO.getIncParameterValue());

		}else if(MailConstantsVO.FLAG_NO.equals(incentiveFlag)){

			this.setParameterCode(incentiveConfigurationDetailVO.getDisIncParameterCode());
			this.setParameterType(incentiveConfigurationDetailVO.getDisIncParameterType());
			this.setParameterValue(incentiveConfigurationDetailVO.getDisIncParameterValue());
		}

		this.setExcludeFlag(incentiveConfigurationDetailVO.getExcludeFlag());
		this.setLastUpdatedTime(incentiveConfigurationDetailVO.getLastUpdatedTime());
		this.setLastUpdatedUser(incentiveConfigurationDetailVO.getLastUpdatedUser());
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

    private void generateSequnceNumberForIncentiveDetail(IncentiveConfigurationDetailVO detailVO)
    		throws SystemException{
    	log.entering("MailIncentiveDetail", "generateSequnceNumberForIncentiveDetail");
    	String keyCode = detailVO.getCompanyCode().concat(String.valueOf(detailVO.getIncentiveSerialNumber()));
		Criterion incentiveCriterion = KeyUtils.getCriterion(detailVO.getCompanyCode(),
				KEY_INCENTIVE_CONFIGURATION,keyCode);
    	detailVO.setSequenceNumber(KeyUtils.getKey(incentiveCriterion));
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

}
