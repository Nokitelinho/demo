/*
 * OfficeOfExchange.java Created on June 27, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangeException;
import com.ibsplc.icargo.business.mail.operations.SharedProxyException;
import com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCache;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author a-3109
 */

@Entity
@Table(name = "MALEXGOFCMST")
public class OfficeOfExchange {
	/*
	 * Added By Karthick V
	 */
	private static final String ACTFLG_ACTIVE = "A";

	private static final String ACTFLG_INACTIVE = "I";

	private static final String MODULE_NAME = "mail.operations";

	private Log log = LogFactory.getLogger("MAIL operations");

	private OfficeOfExchangePK officeOfExchangePK;
	/**
	 * Variable storing the exchange office description
	 */
	private String codeDescription;

	/**
	 * Variable indicating the countryCode
	 */
	private String countryCode;

	/**
	 * Variable indicating the city code
	 */
	private String cityCode;

	/**
	 * Flag indicating if the data is active or not
	 */
	private String activeFlag;

	/**
	 * Office Code
	 */
	private String officeCode;

	/**
	 * The postal administration code
	 */
	private String poaCode;

	/**
	 * Last Updated User
	 */
	private String lastUpdateUser;

	/**
	 * Last updated time
	 */
	private Calendar lastUpdateTime;

	/**
	 * aiport code
	 */
	private String airportCode;

	/**
	 * Mailbox Id
	 */
	private String mailboxId;

	/**
	 * @return Returns the activeFlag.
	 */
	@Column(name = "ACTFLG")
	public String getActiveFlag() {
		return activeFlag;
	}

	/**
	 * @param activeFlag
	 *            The activeFlag to set.
	 */
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	/**
	 * @return Returns the cityCode.
	 */
	@Column(name = "CTYCOD")
	public String getCityCode() {
		return cityCode;
	}

	/**
	 * @param cityCode
	 *            The cityCode to set.
	 */
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	/**
	 * @return Returns the codeDescription.
	 */
	@Column(name = "EXGCODDES")
	public String getCodeDescription() {
		return codeDescription;
	}

	/**
	 * @param codeDescription
	 *            The codeDescription to set.
	 */
	public void setCodeDescription(String codeDescription) {
		this.codeDescription = codeDescription;
	}

	/**
	 * @return Returns the countryCode.
	 */
	@Column(name = "CNTCOD")
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * @param countryCode
	 *            The countryCode to set.
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * @return Returns the officeCode.
	 */
	@Column(name = "OFCCOD")
	public String getOfficeCode() {
		return officeCode;
	}

	/**
	 * @param officeCode
	 *            The officeCode to set.
	 */
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
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

	/*
	 * Added by A-5945 for ICRD-71956 starts
	 */
	/**
	 * @return Returns the airportCode.
	 */
	@Column(name = "ARPCOD")
	public String getAirportCode() {
		return airportCode;
	}

	/**
	 * @param airportCode
	 *            The airportCode to set.
	 */
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	/**
	 * @return Returns the poaCode.
	 */
	@Column(name = "POACOD")
	public String getPoaCode() {
		return poaCode;
	}

	/**
	 * @param poaCode
	 *            The poaCode to set.
	 */
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}

	@Column(name = "MALBOXIDR")
	public String getMailboxId() {
		return mailboxId;
	}

	public void setMailboxId(String mailboxId) {
		this.mailboxId = mailboxId;
	}

	/**
	 * @return Returns the officeOfExchangePK.
	 */
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "companyCode", column = @Column(name = "CMPCOD")),
			@AttributeOverride(name = "officeOfExchange", column = @Column(name = "EXGOFCCOD")) })
	public OfficeOfExchangePK getOfficeOfExchangePK() {
		return officeOfExchangePK;
	}

	/**
	 * @param officeOfExchangePK
	 *            The officeOfExchangePK to set.
	 */
	public void setOfficeOfExchangePK(OfficeOfExchangePK officeOfExchangePK) {
		this.officeOfExchangePK = officeOfExchangePK;
	}

	/**
	 * Empty constructor
	 */
	public OfficeOfExchange() {

	}

	/**
	 * @param officeOfExchangeVO
	 * @throws SystemException
	 */
	public OfficeOfExchange(OfficeOfExchangeVO officeOfExchangeVO)
			throws SystemException {
		try {
			populatepk(officeOfExchangeVO);
			populateAttribute(officeOfExchangeVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

	/**
	 * 
	 * @param officeOfExchangeVO
	 */
	private void populatepk(OfficeOfExchangeVO officeOfExchangeVO) {
		OfficeOfExchangePK officeOfExchangePk = new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode(officeOfExchangeVO.getCompanyCode());
		officeOfExchangePk.setOfficeOfExchange(officeOfExchangeVO.getCode());
		this.setOfficeOfExchangePK(officeOfExchangePk);
	}

	/**
	 * 
	 * @param officeOfExchangeVO
	 */
	private void populateAttribute(OfficeOfExchangeVO officeOfExchangeVO) {
		if (officeOfExchangeVO.isActive()) {
			this.setActiveFlag(ACTFLG_ACTIVE);
		} else {
			this.setActiveFlag(ACTFLG_INACTIVE);
		}
		this.setCityCode(officeOfExchangeVO.getCityCode());
		this.setCodeDescription(officeOfExchangeVO.getCodeDescription());
		this.setCountryCode(officeOfExchangeVO.getCountryCode());
		this.setOfficeCode(officeOfExchangeVO.getOfficeCode());
		this.setPoaCode(officeOfExchangeVO.getPoaCode());
		this.setLastUpdateUser(officeOfExchangeVO.getLastUpdateUser());
		this.setAirportCode(officeOfExchangeVO.getAirportCode());
		this.setMailboxId(officeOfExchangeVO.getMailboxId());

	}

	/**
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 * @throws FinderException
	 */
	public static OfficeOfExchange find(String companyCode,
			String officeOfExchange) throws SystemException, FinderException {
		OfficeOfExchangePK officeOfExchangePk = new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode(companyCode);
		officeOfExchangePk.setOfficeOfExchange(officeOfExchange);
		EntityManager em = PersistenceController.getEntityManager();
		return em.find(OfficeOfExchange.class, officeOfExchangePk);
	}

	/**
	 * @param officeOfExchangeVO
	 * @throws SystemException
	 */
	public void update(OfficeOfExchangeVO officeOfExchangeVO)
			throws SystemException {
		populateAttribute(officeOfExchangeVO);
		this.setLastUpdateTime(officeOfExchangeVO.getLastUpdateTime());
		log.log(Log.INFO,"BEFORE flush for OfficeOfExchangeCache  Update");
		CacheFactory factory = CacheFactory.getInstance();
		OfficeOfExchangeCache cache = factory.getCache(OfficeOfExchangeCache.ENTITY_NAME);
		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGE_CACHE_GROUP);
		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGEAIRPORT_CACHE_GROUP);
	}

	/**
	 * Method to delete a row of Office of Exchange
	 * 
	 * @throws RemoveException
	 * @throws SystemException
	 */
	public void remove() throws RemoveException, SystemException {
		PersistenceController.getEntityManager().remove(this);
		log.log(Log.INFO,"BEFORE flush for OfficeOfExchangeCache  Update");
		CacheFactory factory = CacheFactory.getInstance();
		OfficeOfExchangeCache cache = factory.getCache(OfficeOfExchangeCache.ENTITY_NAME);
		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGE_CACHE_GROUP);  
		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGEAIRPORT_CACHE_GROUP);
	}

	private static MailTrackingDefaultsDAO constructDAO()
			throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailTrackingDefaultsDAO.class.cast(em
					.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	
	/**
	 * @author A-2037
	 * This method is used to findMailSubClassCodes
	 * @param companyCode
	 * @param officeOfExchange
	 * @param pageNumber
	 * @return
	 * @throws SystemException
	 */
	public static Page<OfficeOfExchangeVO> findOfficeOfExchange
	(String companyCode,String officeOfExchange,int pageNumber)
	throws SystemException{
		try{
			return constructDAO().findOfficeOfExchange(companyCode,
					officeOfExchange,pageNumber);
		}
		catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * TODO Purpose
	 * Sep 13, 2006, a-1739
	 * @param companyCode
	 * @param officeOfExchange
	 * @return
	 * @throws SystemException
	 */
	public static String findPAForOfficeOfExchange(
			String companyCode, String officeOfExchange)
	throws SystemException {
		try {
			return constructDAO().findPAForOfficeOfExchange(companyCode, officeOfExchange);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}



/**
 * findCityForOfficeOfExchange
 * @param companyCode
 * @param officeOfExchanges
 * @return
 * @throws SystemException
 */
public static HashMap<String,String> findCityForOfficeOfExchange(
		String companyCode, Collection<String> officeOfExchanges)
throws SystemException {
	try {
		return constructDAO().findCityForOfficeOfExchange(companyCode, officeOfExchanges);
	}catch(PersistenceException persistenceException){
		persistenceException.getErrorCode();
		throw new SystemException(persistenceException.getErrorCode());
	}
}

/**
 * @author a-1936
 * This method is used to validate the OfficeOfExchange
 * @param companyCode
 * @param officeOfExchange
 * @return
 * @throws SystemException
 */
public static OfficeOfExchangeVO validateOfficeOfExchange(String companyCode,String officeOfExchange)
   throws SystemException{

	try{
		return constructDAO().validateOfficeOfExchange(companyCode,officeOfExchange);
	}catch(PersistenceException persistenceException){
		persistenceException.getErrorCode();
		throw new SystemException(persistenceException.getErrorCode());
	}

}
/**
 * @author A-2037
 * Method for OfficeOfExchangeLOV containing code and description
 * @param companyCode
 * @param code
 * @param description
 * @param pageNumber
 * @return
 * @throws SystemException
 */
public static Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(
		OfficeOfExchangeVO officeofExchangeVO,int pageNumber,int defaultSize)
		throws SystemException{
	try{
		return constructDAO().findOfficeOfExchangeLov(officeofExchangeVO,pageNumber,defaultSize);
	}
	catch(PersistenceException persistenceException){
		persistenceException.getErrorCode();
		throw new SystemException(persistenceException.getErrorCode());
	}
}

/**
 * TODO Purpose
 * Nov 1, 2006,
 * @param officeOfExchangeVO
 * @throws SystemException
 * @throws OfficeOfExchangeException
 */
public void validateNewOfficeOfExchange(
		OfficeOfExchangeVO officeOfExchangeVO)
	throws SystemException, OfficeOfExchangeException {
	log.entering("OfficeOfExchange", "validateNewOfficeOfExchange");

	String countryCd = officeOfExchangeVO.getCountryCode();
	Collection<String> countryCodes = new ArrayList<String>();
	countryCodes.add(countryCd);
	String companyCode = officeOfExchangeVO.getCompanyCode();
	try {
		new SharedAreaProxy().validateCountryCodes(companyCode, countryCodes);
	} catch(SharedProxyException exception) {
		throw new OfficeOfExchangeException(
				OfficeOfExchangeException.INVALID_COUNTRYCODE,
				new Object[]{countryCd});
	}

	String cityCd = officeOfExchangeVO.getCityCode();
	Collection<String> cityCodes = new ArrayList<String>();
	cityCodes.add(cityCd);

	try {
		new SharedAreaProxy().validateCityCodes(
				companyCode, cityCodes);
	} catch(SharedProxyException exception) {
		throw new OfficeOfExchangeException(exception);
	}

	if(new MailController().findPACode(companyCode,
			officeOfExchangeVO.getPoaCode()) == null) {
		throw new OfficeOfExchangeException(
				OfficeOfExchangeException.INVALID_POSTAL_ADMIN,
				new Object[]{officeOfExchangeVO.getPoaCode()});
	}

	log.exiting("OfficeOfExchange", "validateNewOfficeOfExchange");
}

/**
 * Added for icrd-110909
 * @param companyCode
 * @param airportCode
 * @return
 * @throws SystemException
 */
public static Collection<String> findOfficeOfExchangesForAirport(
		String companyCode, String airportCode)
throws SystemException {
	try {
		return constructDAO().findOfficeOfExchangesForAirport(companyCode, airportCode);
	}catch(PersistenceException persistenceException){
		persistenceException.getErrorCode();
		throw new SystemException(persistenceException.getErrorCode());
	}
}
/**
 * Added for bug ICRD-158989 by A_5526 starts
 * Method to fetch PA code for mailbox id
 * @param companyCode
 * @param mailboxId
 * @return
 * @throws SystemException
 */
public static String findPAForMailboxID(String companyCode,
		String mailboxId,String originOE) throws SystemException {
	try {
		return constructDAO().findPAForMailboxID(companyCode, mailboxId,originOE);
	}catch(PersistenceException persistenceException){
		persistenceException.getErrorCode();
		throw new SystemException(persistenceException.getErrorCode());
	}
}
/**
 * 	Method		:	OfficeOfExchange.findOfficeOfExchangeForPA
 *	Added by 	:	a-6245 on 10-Jul-2017
 * 	Used for 	:
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param paCode
 *	Parameters	:	@return 
 *	Return type	: 	HashMap<String,String>
 */
public static HashMap<String,String> findOfficeOfExchangeForPA(String companyCode,
			String paCode)
			throws SystemException{
		try {
			return constructDAO().findOfficeOfExchangeForPA(companyCode, paCode);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
/**
 * 
 * 	Method		:	OfficeOfExchange.findAgentCodeForPA
 *	Added by 	:	U-1267 on Nov 2, 2017
 * 	Used for 	:	ICRD-211205
 *	Parameters	:	@param companyCode
 *	Parameters	:	@param officeOfExchange
 *	Parameters	:	@return
 *	Parameters	:	@throws SystemException 
 *	Return type	: 	String
 */
	public static String findAgentCodeForPA(String companyCode,
			String officeOfExchange) throws SystemException {
		try {
			return constructDAO().findAgentCodeForPA(companyCode,
					officeOfExchange);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public static HashMap<String,String> findAirportForOfficeOfExchange(String companyCode, Collection<String> officeOfExchanges) throws SystemException{
		try {	
			return constructDAO().findAirportForOfficeOfExchange(companyCode,officeOfExchanges);
		}
		catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
}
