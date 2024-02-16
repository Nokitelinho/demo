package com.ibsplc.icargo.business.mail.operations.proxy;

import java.util.Collection;
import java.util.HashMap;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**

 * This is the product proxy for Shared Defaults
 * @author A-8353
 *
 */
@Module("shared")
@SubModule("defaults")
public class SharedDefaultProductProxy extends ProductProxy {
	/**
	 *  Used for getting system parameters 
	 * @author A-8353
	 * @param systemParameterCodes
	 * @return 
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public HashMap<String,String> findSystemParameterByCodes(Collection<String> 
	systemParameterCodes)throws ProxyException, SystemException {
		return despatchRequest("findSystemParameterByCodes", systemParameterCodes);
	}

}
