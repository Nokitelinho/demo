package com.ibsplc.icargo.business.mail.operations.cache;

import com.ibsplc.icargo.business.mail.operations.PostalAdministration;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class PostalAdministrationPartyIdentifierForPARetrievalStrategy implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("PostalAdministration");
    public String retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE PostalAdministrationPartyIdentifierForPARetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String paCode = (String)args[1];
		
		try{
			return PostalAdministration.findPartyIdentifierForPA(companyCode, paCode);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
