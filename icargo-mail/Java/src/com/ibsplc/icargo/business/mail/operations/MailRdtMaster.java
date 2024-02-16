/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMaster.java
 *
 *	Created by	:	A-6991
 *	Created on	:	17-Jul-2018
 *
 *  Copyright 2018 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
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

import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
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
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.MailRdtMaster.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	17-Jul-2018	:	Draft
 */
@Entity
@Table(name = "MALRDTREQDLVTIM")
public class MailRdtMaster {
	
	private MailRdtMasterPK mailRdtMasterPK;
	private String destinationAirportCodes;
	private String originAirportCodes;
	private String malServiceLevel;
	private int rdtOffset;
	private int rdtDay;
	private String rdtRule;
	private String malClass;
	private String malType;
	private String lastUpdateUser;
    private Calendar lastUpdateTime;
  
    /**
     * The GPACode
     */
	private String gpaCode;
	/**
	 * 	Getter for gpaCode 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "GPACOD")
	public String getGpaCode() {
		return gpaCode;
	}
	/**
	 *  @param gpaCode the gpaCode to set
	 * 	Setter for gpaCode 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	private static final String MODULE = "mail.operations";

	private Log log = LogFactory.getLogger("MailTracking_Defaults");

	/*
	 * The Default Constructor
	 */
	public MailRdtMaster() {
	}

	/**
	 * 	Getter for mailRdtMasterPK 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "SERNUM", column = @Column(name = "SERNUM")) })
	public MailRdtMasterPK getMailRdtMasterPK() {
		return mailRdtMasterPK;
	}

	/**
	 *  @param mailRdtMasterPK the mailRdtMasterPK to set
	 * 	Setter for mailRdtMasterPK 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setMailRdtMasterPK(MailRdtMasterPK mailRdtMasterPK) {
		this.mailRdtMasterPK = mailRdtMasterPK;
	}

	/**
	 * 	Getter for destinationAirportCodes
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "DSTARPCOD")
	public String getDestinationAirportCodes() {
		return destinationAirportCodes;
	}
	public void setDestinationAirportCodes(String destinationAirportCodes) {
		this.destinationAirportCodes = destinationAirportCodes;
	}
	/**
	 * 	Getter for originAirportCodes
	 *	
	 * 	
	 */
	@Column(name = "ORGARPCOD")
	public String getOriginAirportCodes() {
		return originAirportCodes;
	}

	/**
	 *  @param airportCode the airportCode to set
	 * 	Setter for Origin AirportCodes  
	 *	
	 * 	
	 */
	public void setOriginAirportCodes(String originAirportCodes) {
		this.originAirportCodes = originAirportCodes;
	}

	/**
	 * 	Getter for malServiceLevel 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "MALSRVLVL")
	public String getMalServiceLevel() {
		return malServiceLevel;
	}

	/**
	 *  @param malServiceLevel the malServiceLevel to set
	 * 	Setter for malServiceLevel 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setMalServiceLevel(String malServiceLevel) {
		this.malServiceLevel = malServiceLevel;
	}

	/**
	 * 	Getter for rdtOffset 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "RDTOFT")
	public int getRdtOffset() {
		return rdtOffset;
	}

	/**
	 *  @param rdtOffset the rdtOffset to set
	 * 	Setter for rdtOffset 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setRdtOffset(int rdtOffset) {
		this.rdtOffset = rdtOffset;
	}

	/**
	 * 	Getter for rdtDay 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "RDTDAY")
	public int getRdtDay() {
		return rdtDay;
	}

	/**
	 *  @param rdtDay the rdtDay to set
	 * 	Setter for rdtDay 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	
	public void setRdtDay(int rdtDay) {
		this.rdtDay = rdtDay;
	}

	/**
	 * 	Getter for rdtRule 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "RDTRUL")
	public String getRdtRule() {
		return rdtRule;
	}

	/**
	 *  @param rdtRule the rdtRule to set
	 * 	Setter for rdtRule 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setRdtRule(String rdtRule) {
		this.rdtRule = rdtRule;
	}

	/**
	 * 	Getter for malClass 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "MALCLS")
	public String getMalClass() {
		return malClass;
	}

	/**
	 *  @param malClass the malClass to set
	 * 	Setter for malClass 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setMalClass(String malClass) {
		this.malClass = malClass;
	}

	/**
	 * 	Getter for malType 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "MALTYP")
	public String getMalType() {
		return malType;
	}

	/**
	 *  @param malType the malType to set
	 * 	Setter for malType 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setMalType(String malType) {
		this.malType = malType;
	}

	/**
	 * 	Getter for lastUpdateUser 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Column(name = "LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	/**
	 *  @param lastUpdateUser the lastUpdateUser to set
	 * 	Setter for lastUpdateUser 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * 	Getter for lastUpdateTime 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	@Version
	@Column(name = "LSTUPDTIM")

	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}

	/**
	 *  @param lastUpdateTime the lastUpdateTime to set
	 * 	Setter for lastUpdateTime 
	 *	Added by : A-6991 on 17-Jul-2018
	 * 	Used for :ICRD-212544
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	
	public MailRdtMaster(MailRdtMasterVO mailRdtMasterVO)
			throws SystemException {
		populatePK(mailRdtMasterVO);
		populateAtrributes(mailRdtMasterVO);
		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException ex) {
			throw new SystemException(ex.getErrorCode(), ex);
		}
	}
	/**
	 * 
	 * 	Method		:	MailRdtMaster.populatePK
	 *	Added by 	:	A-6991 on 17-Jul-2018
	 * 	Used for 	:   ICRD-212544
	 *	Parameters	:	@param mailRdtMasterVO 
	 *	Return type	: 	void
	 */
	private void populatePK(MailRdtMasterVO mailRdtMasterVO) {
		MailRdtMasterPK mailRdtMasterPK = new MailRdtMasterPK();
		mailRdtMasterPK.setCompanyCode(mailRdtMasterVO.getCompanyCode());
		
		this.setMailRdtMasterPK(mailRdtMasterPK);
	}

/**
 * 
 * 	Method		:	MailRdtMaster.populateAtrributes
 *	Added by 	:	A-6991 on 17-Jul-2018
 * 	Used for 	:   ICRD-212544
 *	Parameters	:	@param mailRdtMasterVO
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	void
 */
	private void populateAtrributes(MailRdtMasterVO mailRdtMasterVO)
			throws SystemException {
		this.setLastUpdateUser(mailRdtMasterVO.getLastUpdateUser());
		this.setLastUpdateTime(mailRdtMasterVO.getLastUpdateTime());
		this.setDestinationAirportCodes(mailRdtMasterVO.getAirportCodes());
		this.setOriginAirportCodes(mailRdtMasterVO.getOriginAirportCodes());
		this.setMalClass(mailRdtMasterVO.getMailClass());
		this.setMalServiceLevel(mailRdtMasterVO.getMailServiceLevel());
		this.setMalType(mailRdtMasterVO.getMailType());
		this.setRdtDay(mailRdtMasterVO.getRdtDay());
		this.setRdtOffset(mailRdtMasterVO.getRdtOffset());
		this.setRdtRule(mailRdtMasterVO.getRdtRule());
		this.setGpaCode(mailRdtMasterVO.getGpaCode());
        
	}
	
	public static MailRdtMaster find(MailRdtMasterPK mailRdtMasterPK)
			throws SystemException, FinderException {
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(MailRdtMaster.class, mailRdtMasterPK);
	}

	/**
	 * @param coTerminusVO
	 * @throws SystemException
	 */
	public void update(MailRdtMasterVO mailRdtMasterVO)
			throws SystemException {
		populateAtrributes(mailRdtMasterVO);
		this.setLastUpdateTime(mailRdtMasterVO.getLastUpdateTime());
	}

	/**
	 * @param companyCode
	 * @param ownCarrierCode
	 * @param airportCode
	 * @return Collection<CoTerminusVO>
	 * @throws SystemException
	 */
	public static Collection<MailRdtMasterVO> findRdtMasterDetails(
			RdtMasterFilterVO filterVO)
			throws SystemException {
		try {
			return constructDAO().findRdtMasterDetails(filterVO);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
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
}
