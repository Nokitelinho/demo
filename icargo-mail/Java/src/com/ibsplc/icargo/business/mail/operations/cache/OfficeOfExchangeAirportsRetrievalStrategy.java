package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class OfficeOfExchangeAirportsRetrievalStrategy implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("OfficeOfExchange");
    public HashMap<String, String>   retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE OfficeOfExchangeCityRetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		Collection<String> officeOfExchanges = (Collection<String>)args[1]; 
		try{
			return OfficeOfExchange.findAirportForOfficeOfExchange(companyCode,
					officeOfExchanges);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
