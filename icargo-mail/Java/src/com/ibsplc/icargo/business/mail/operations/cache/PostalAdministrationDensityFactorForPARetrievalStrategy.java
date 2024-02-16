package com.ibsplc.icargo.business.mail.operations.cache;
import com.ibsplc.icargo.business.mail.operations.PostalAdministration;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
public class PostalAdministrationDensityFactorForPARetrievalStrategy implements RetrievalStrategy {
 public String retrieve(Object... args) throws CacheException {
		String companyCode = (String)args[0];
		String paCode = (String)args[1];
		try{
			return PostalAdministration.findDensityfactorForPA(companyCode, paCode);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		}
    }
}
