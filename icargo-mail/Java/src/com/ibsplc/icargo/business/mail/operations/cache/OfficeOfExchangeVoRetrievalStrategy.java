package com.ibsplc.icargo.business.mail.operations.cache;

import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.xibase.server.framework.cache.CacheException;
import com.ibsplc.xibase.server.framework.cache.RetrievalStrategy;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author A-8353
 *
 */
public class OfficeOfExchangeVoRetrievalStrategy  implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("OfficeOfExchange");
    public Page<OfficeOfExchangeVO> retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE OfficeOfExchangeVoRetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String officeOfExchange = (String)args[1];
		int pageNumber = (int)args[2];
		
		try{
			return OfficeOfExchange.findOfficeOfExchange(companyCode,
					officeOfExchange, pageNumber);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
