package com.ibsplc.icargo.business.mail.operations.cache;

import java.util.Collection;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-8353
 *
 */
public class OfficeOfExchangeAirportRetrievalStrategy implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("OfficeOfExchange");
    public Collection<String>   retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE OfficeOfExchangeCityRetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String airportCode = (String)args[1];
		try{
			return OfficeOfExchange.findOfficeOfExchangesForAirport(companyCode,
					airportCode);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
