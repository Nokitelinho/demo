package com.ibsplc.neoicargo.mailmasters.component;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javax.persistence.*;

import com.ibsplc.neoicargo.framework.tenant.jpa.entity.BaseEntity;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.vo.OfficeOfExchangeVO;
import com.ibsplc.neoicargo.mailmasters.exception.OfficeOfExchangeException;
import com.ibsplc.neoicargo.mailmasters.exception.SharedProxyException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mailmasters.dao.MailMastersDAO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import lombok.Getter;
import lombok.Setter;

/** 
 * @author a-3109
 */
@Setter
@Getter
@Slf4j
@Entity
@IdClass(OfficeOfExchangePK.class)
@Table(name = "MALEXGOFCMST")
public class OfficeOfExchange  extends BaseEntity implements Serializable {
	private static final String ACTFLG_ACTIVE = "A";
	private static final String ACTFLG_INACTIVE = "I";
	private static final String MODULE_NAME = "mail.masters";

	@Id
	@Column(name = "EXGOFCCOD")
	private String officeOfExchange;
	/** 
	* Variable storing the exchange office description
	*/
	@Column(name = "EXGCODDES")
	private String codeDescription;
	/** 
	* Variable indicating the countryCode
	*/
	@Column(name = "CNTCOD")
	private String countryCode;
	/** 
	* Variable indicating the city code
	*/
	@Column(name = "CTYCOD")
	private String cityCode;
	/** 
	* Flag indicating if the data is active or not
	*/
	@Column(name = "ACTFLG")
	private String activeFlag;
	/** 
	* Office Code
	*/
	@Column(name = "OFCCOD")
	private String officeCode;
	/** 
	* The postal administration code
	*/
	@Column(name = "POACOD")
	private String poaCode;
	/**
	* aiport code
	*/
	@Column(name = "ARPCOD")
	private String airportCode;
	/** 
	* Mailbox Id
	*/
	@Column(name = "MALBOXIDR")
	private String mailboxId;

	/** 
	* Empty constructor
	*/
	public OfficeOfExchange() {
	}

	/** 
	* @param officeOfExchangeVO
	* @throws SystemException
	*/
	public OfficeOfExchange(OfficeOfExchangeVO officeOfExchangeVO) {
		try {
			populatepk(officeOfExchangeVO);
			populateAttribute(officeOfExchangeVO);
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode());
		}
	}

    public static String findPAForMailboxID(String companyCode, String mailboxId, String originOE) {
		return constructDAO().findPAForMailboxID(companyCode, mailboxId,originOE);
	}

    /**
	* @param officeOfExchangeVO
	*/
	private void populatepk(OfficeOfExchangeVO officeOfExchangeVO) {
		this.setCompanyCode(officeOfExchangeVO.getCompanyCode());
		this.setOfficeOfExchange(officeOfExchangeVO.getCode());
	}

	/** 
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
		this.setLastUpdatedUser(officeOfExchangeVO.getLastUpdateUser());
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
	public static OfficeOfExchange find(String companyCode, String officeOfExchange) throws FinderException {
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
	public void update(OfficeOfExchangeVO officeOfExchangeVO) {

		populateAttribute(officeOfExchangeVO);
		this.setLastUpdatedTime(Timestamp.valueOf(officeOfExchangeVO.getLastUpdateTime().toLocalDateTime()));
		log.info("BEFORE flush for OfficeOfExchangeCache  Update");

			//TODO: NEO Cache to be corrected
//		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGE_CACHE_GROUP);
//		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGEAIRPORT_CACHE_GROUP);
	}

	/** 
	* Method to delete a row of Office of Exchange
	* @throws RemoveException
	* @throws SystemException
	*/
	public void remove() throws RemoveException {
		PersistenceController.getEntityManager().remove(this);
		log.info("BEFORE flush for OfficeOfExchangeCache  Update");
		//TODO: NEO Cache to be corrected
//		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGE_CACHE_GROUP);
//		cache.invalidateForGroup(OfficeOfExchangeCache.OFFICEOFEXCHANGEAIRPORT_CACHE_GROUP);
	}

	private static MailMastersDAO constructDAO() {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return MailMastersDAO.class.cast(em.getQueryDAO(MODULE_NAME));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* @author A-2037Method for OfficeOfExchangeLOV containing code and description
	* @param pageNumber
	* @return
	* @throws SystemException
	*/
	public static Page<OfficeOfExchangeVO> findOfficeOfExchangeLov(OfficeOfExchangeVO officeofExchangeVO,
			int pageNumber, int defaultSize) {
		try {
			return constructDAO().findOfficeOfExchangeLov(officeofExchangeVO, pageNumber, defaultSize);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/** 
	* TODO Purpose Nov 1, 2006,
	* @param officeOfExchangeVO
	* @throws SystemException
	* @throws OfficeOfExchangeException
	*/
	public void validateNewOfficeOfExchange(OfficeOfExchangeVO officeOfExchangeVO) throws OfficeOfExchangeException {
		SharedAreaProxy sharedAreaProxy = ContextUtil.getInstance().getBean(SharedAreaProxy.class);
		log.debug("OfficeOfExchange" + " : " + "validateNewOfficeOfExchange" + " Entering");
		String countryCd = officeOfExchangeVO.getCountryCode();
		Collection<String> countryCodes = new ArrayList<String>();
		countryCodes.add(countryCd);
		String companyCode = officeOfExchangeVO.getCompanyCode();
		try {
			sharedAreaProxy.validateCountryCodes(companyCode, countryCodes);
		} catch (SharedProxyException exception) {
			throw new OfficeOfExchangeException(OfficeOfExchangeException.INVALID_COUNTRYCODE,
					new Object[] { countryCd });
		}
		String cityCd = officeOfExchangeVO.getCityCode();
		Collection<String> cityCodes = new ArrayList<String>();
		cityCodes.add(cityCd);
		try {
			sharedAreaProxy.validateCityCodes(companyCode, cityCodes);
		} catch (SharedProxyException exception) {
			throw new OfficeOfExchangeException(exception);
		}
		if (new MailController().findPACode(companyCode, officeOfExchangeVO.getPoaCode()) == null) {
			throw new OfficeOfExchangeException(OfficeOfExchangeException.INVALID_POSTAL_ADMIN,
					new Object[] { officeOfExchangeVO.getPoaCode() });
		}
		log.debug("OfficeOfExchange" + " : " + "validateNewOfficeOfExchange" + " Exiting");
	}

	/** 
	* Method		:	OfficeOfExchange.findAgentCodeForPA Added by 	:	U-1267 on Nov 2, 2017 Used for 	:	ICRD-211205 Parameters	:	@param companyCode Parameters	:	@param officeOfExchange Parameters	:	@return Parameters	:	@throws SystemException  Return type	: 	String
	*/
	public static String findAgentCodeForPA(String companyCode, String officeOfExchange) {
		try {
			return constructDAO().findAgentCodeForPA(companyCode, officeOfExchange);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
		/** 
	* Method to findOfficeOfExchange
	* @param pageNumber
	* @param officeOfExchange
	* @param companyCode
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

	public static boolean validateMailSubClass(String companyCode, String subclass) throws SystemException{
		try {
			return constructDAO().validateMailSubClass(companyCode,
					subclass);
		} catch (PersistenceException persistenceException) {
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
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
	public static HashMap<String,String> findAirportForOfficeOfExchange(
			String companyCode, Collection<String> officeOfExchanges)
			throws SystemException {
		try {
			return constructDAO().findAirportForOfficeOfExchange(companyCode, officeOfExchanges);
		}catch(PersistenceException persistenceException){
			persistenceException.getErrorCode();
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

}
