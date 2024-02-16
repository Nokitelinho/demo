package com.ibsplc.icargo.business.mail.operations.cache;

import com.ibsplc.icargo.business.mail.operations.PostalAdministration;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
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
public class PostalAdministrationCodeRetrievalStrategy  implements RetrievalStrategy {

	private Log log = LogFactory.getLogger("PostalAdministration");
    public PostalAdministrationVO retrieve(Object... args) throws CacheException {
		
		log.log(Log.INFO, "ENTERED THE PostalAdministrationCodeRetrievalStrategy CLASS");
		
		String companyCode = (String)args[0];
		String paCode = (String)args[1];
		
		try{
			return PostalAdministration.findPACode(companyCode, paCode);
		
		}catch(SystemException e){
			throw new CacheException(CacheException.UNEXPECTED_SERVER_ERROR, e);
		
		}
    }

}
