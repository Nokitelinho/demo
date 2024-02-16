package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OfficeOfExchangeFoPARetrievalStrategy implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("OfficeOfExchange");
    public Map<String, String>   retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE OfficeOfExchangeFoPARetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String paCode = (String)args[1]; 
		try{
			return OfficeOfExchange.findOfficeOfExchangeForPA(companyCode,
					paCode);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }
}
