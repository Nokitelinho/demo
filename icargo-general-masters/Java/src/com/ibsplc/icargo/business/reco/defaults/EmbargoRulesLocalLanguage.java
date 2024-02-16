/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.EmbargoRulesLocalLanguage.java
 *
 *	Created by	:	a-7815
 *	Created on	:	05-Sep-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.reco.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoLocalLanguageVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoRulesVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;

/**
 *	Java file	: 	com.ibsplc.icargo.business.reco.defaults.EmbargoRulesLocalLanguage.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	a-7815	:	05-Sep-2017	:	Draft
 */
@Staleable
@Table(name = "RECLCLLNGMST")
@Entity
public class EmbargoRulesLocalLanguage {
	
	private EmbargoRulesLocalLanguagePK embargoRulesLocalLanguagePk;
	
	private String embargoDescription;
	
	private Calendar lastUpdatedTime;

	private String lastUpdatedUser;
	
	public EmbargoRulesLocalLanguage() {
	}

	public EmbargoRulesLocalLanguage(EmbargoLocalLanguageVO embargoLocalLanguageVO, EmbargoRulesVO embargoVO) throws SystemException, CreateException {
		populatePk(embargoVO, embargoLocalLanguageVO);
		populateAttributes(embargoLocalLanguageVO,embargoVO);

	}
	/**
	 * 	Getter for embargoRulesLocalLanguagePk 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "embargoReferenceNumber", column = @Column(name = "REFNUM")),
			@AttributeOverride(name = "embargoVersion", column = @Column(name = "VERNUM")),
			@AttributeOverride(name = "embargoLocalLanguage", column = @Column(name = "RECLCLLNG"))}

	)
	public EmbargoRulesLocalLanguagePK getEmbargoRulesLocalLanguagePk() {
		return embargoRulesLocalLanguagePk;
	}
	/**
	 *  @param embargoRulesLocalLanguagePk the embargoRulesLocalLanguagePk to set
	 * 	Setter for embargoRulesLocalLanguagePk 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoRulesLocalLanguagePk(
			EmbargoRulesLocalLanguagePK embargoRulesLocalLanguagePk) {
		this.embargoRulesLocalLanguagePk = embargoRulesLocalLanguagePk;
	}
	/**
	 * 	Getter for embargoDescription 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	@Column(name = "RECDES")
	public String getEmbargoDescription() {
		return embargoDescription;
	}
	/**
	 *  @param embargoDescription the embargoDescription to set
	 * 	Setter for embargoDescription 
	 *	Added by : a-7815 on 05-Sep-2017
	 * 	Used for :
	 */
	public void setEmbargoDescription(String embargoDescription) {
		this.embargoDescription = embargoDescription;
	}
	/**
	 * This method Removes the particular entry from the Database 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {

		PersistenceController.getEntityManager().remove(this);
	}
	/**
	 * 
	 * 	Method		:	EmbargoRulesLocalLanguage.update
	 *	Added by 	:	a-7815 on 13-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param embargoLocalLanguageVO
	 *	Parameters	:	@param embargoVO
	 *	Parameters	:	@throws CreateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void update(EmbargoLocalLanguageVO embargoLocalLanguageVO,EmbargoRulesVO embargoVO)
			throws CreateException, SystemException {
		populateAttributes(embargoLocalLanguageVO,embargoVO);
	}
	/**
	 * 
	 * 	Method		:	EmbargoRulesLocalLanguage.populateAttributes
	 *	Added by 	:	a-7815 on 13-Sep-2017
	 * 	Used for 	:
	 *	Parameters	:	@param embargoLocalLanguageVO
	 *	Parameters	:	@param embargoVO
	 *	Parameters	:	@throws CreateException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	private void populateAttributes(
			EmbargoLocalLanguageVO embargoLocalLanguageVO, EmbargoRulesVO embargoVO) throws CreateException, SystemException {
		setEmbargoDescription(embargoLocalLanguageVO.getEmbargoDescription());
		setLastUpdatedUser(embargoVO.getLastUpdatedUser());
		PersistenceController.getEntityManager().persist(this);
		
	}
	
	public void populatePk(EmbargoRulesVO embargoVO,
			EmbargoLocalLanguageVO embargoLocalLanguageVO) throws SystemException {
		EmbargoRulesLocalLanguagePK embargoRulesLocalLanguagePK = new EmbargoRulesLocalLanguagePK();
		embargoRulesLocalLanguagePK.setCompanyCode(embargoVO.getCompanyCode());
		embargoRulesLocalLanguagePK.setEmbargoLocalLanguage(embargoLocalLanguageVO.getEmbargoLocalLanguage());
		embargoRulesLocalLanguagePK.setEmbargoReferenceNumber(embargoVO.getEmbargoReferenceNumber());
		embargoRulesLocalLanguagePK.setEmbargoVersion(embargoVO.getEmbargoVersion());
		setEmbargoRulesLocalLanguagePk(embargoRulesLocalLanguagePK);
	}

	/**
	 * 	Getter for lastUpdatedTime 
	 *	Added by : a-7815 on 13-Sep-2017
	 * 	Used for :
	 */
	@Version
	@Column(name = "LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 *  @param lastUpdatedTime the lastUpdatedTime to set
	 * 	Setter for lastUpdatedTime 
	 *	Added by : a-7815 on 13-Sep-2017
	 * 	Used for :
	 */
	public void setLastUpdatedTime(Calendar lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * 	Getter for lastUpdatedUser 
	 *	Added by : a-7815 on 13-Sep-2017
	 * 	Used for :
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 *  @param lastUpdatedUser the lastUpdatedUser to set
	 * 	Setter for lastUpdatedUser 
	 *	Added by : a-7815 on 13-Sep-2017
	 * 	Used for :
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}
}
