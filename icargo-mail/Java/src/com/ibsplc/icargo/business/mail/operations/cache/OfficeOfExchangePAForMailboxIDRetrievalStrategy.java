package com.ibsplc.icargo.business.mail.operations.cache;


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
public class OfficeOfExchangePAForMailboxIDRetrievalStrategy implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("OfficeOfExchange");
    public String   retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE OfficeOfExchangePAForMailboxIDRetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String mailboxId = (String)args[1];
		String originOE = (String)args[2];
		try{
			return OfficeOfExchange.findPAForMailboxID(companyCode,mailboxId,originOE);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
