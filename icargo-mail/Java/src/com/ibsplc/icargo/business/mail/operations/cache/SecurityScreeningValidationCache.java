package com.ibsplc.icargo.business.mail.operations.cache;
import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.SecurityScreeningValidationVO;
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
public interface SecurityScreeningValidationCache extends Cache, Invalidator{
	public static final String ENTITY_NAME = "mail.operations.securityscreeningvalidation";
    public static final String  SECURITYSCREENINGVALIDATION_CACHE_GROUP = "mail.securityscreeningvalidationcache.group";
  
    @Group("mail.securityscreeningvalidationcache.group")
    @Strategy("com.ibsplc.icargo.business.mail.operations.cache.SecurityScreeningValidatioVoRetrievalStrategy")
    Collection<SecurityScreeningValidationVO> checkForSecurityScreeningValidation(SecurityScreeningValidationFilterVO securityScreeningValidationFilterVO)  
    		throws CacheException;    
}
