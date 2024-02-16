package com.ibsplc.icargo.business.mail.operations.cache;

import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.xibase.server.framework.cache.Cache;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.Group;
import com.ibsplc.xibase.server.framework.cache.Invalidator;
import com.ibsplc.xibase.server.framework.cache.Strategy;
/**
 * 
 * @author A-8353
 *
 */
public interface PostalAdministrationCache extends Cache, Invalidator{
	public static final String ENTITY_NAME = "mail.operations.postaladministration";
    public static final String  POSTALADMINISTRATION_CACHE_GROUP = "mail.postaladministrationcache.group";
    public static final String  POSTALADMINISTRATIONPARTYIDENTIFIER_CACHE_GROUP = "mail.postaladministrationpartyidentifiercache.group";
    public static final String  POSTALADMINISTRATIONDENSITYFACTOR_CACHE_GROUP = "mail.postaladministrationdensityfactorrcache.group";
    
    @Group("mail.postaladministrationcache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCodeRetrievalStrategy")
    PostalAdministrationVO findPACode(String companyCode,
		String paCode)  throws CacheException;
    @Group("mail.postaladministrationpartyidentifiercache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationPartyIdentifierForPARetrievalStrategy")
    String findPartyIdentifierForPA(String companyCode,
		String paCode)  throws CacheException;
    
    @Group("mail.postaladministrationdensityfactorrcache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationDensityFactorForPARetrievalStrategy")
    String findDensityfactorForPA(String companyCode,
		String paCode)  throws CacheException;
    
}