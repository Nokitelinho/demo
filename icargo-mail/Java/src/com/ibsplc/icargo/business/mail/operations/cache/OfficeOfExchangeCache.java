package com.ibsplc.icargo.business.mail.operations.cache;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.cache.Cache;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.Group;
import com.ibsplc.xibase.server.framework.cache.Invalidator;
import com.ibsplc.xibase.server.framework.cache.Strategy;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
/**
 * 
 * @author A-8353
 *
 */
public interface OfficeOfExchangeCache extends Cache, Invalidator{
	public static final String ENTITY_NAME = "mail.operations.officeofexchange";
    public static final String  OFFICEOFEXCHANGE_CACHE_GROUP = "mail.officeofexchangecache.group";
    public static final String  OFFICEOFEXCHANGEAIRPORT_CACHE_GROUP = "mail.officeofexchangeairportcache.group";
    
    @Group("mail.officeofexchangecache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeVoRetrievalStrategy")
    Page<OfficeOfExchangeVO> findOfficeOfExchange(String companyCode,
			String officeOfExchange, int pageNumber)  throws CacheException;    
    @Group("mail.officeofexchangecache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeCityRetrievalStrategy")
    HashMap<String, String> findCityForOfficeOfExchange(String companyCode,
    		Collection<String> officeOfExchanges)  throws CacheException;
    @Group("mail.officeofexchangecache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeAirportRetrievalStrategy")
    Collection<String> findOfficeOfExchangesForAirport(String companyCode,
			String airportCode)  throws CacheException;
    @Group("mail.officeofexchangecache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangePAForMailboxIDRetrievalStrategy")
    String findPAForMailboxID(String companyCode,
    		 String mailboxId, String originOE)  throws CacheException;
    @Group("mail.officeofexchangecache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeFoPARetrievalStrategy")
    Map<String, String> findOfficeOfExchangeForPA(String companyCode,String
    		paCode)  throws CacheException;
    @Group("mail.officeofexchangeairportcache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.OfficeOfExchangeAirportsRetrievalStrategy")
    HashMap<String, String> findAirportForOfficeOfExchange(String companyCode,
    		 Collection<String> officeOfExchanges)  throws CacheException;

}
