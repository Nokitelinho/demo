package com.ibsplc.icargo.business.addons.mail.operations.proxy;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedDefaultsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	Ashil MN	:	23-Sep-2021	:	Draft
 */
@Module("shared")
@SubModule("defaults")
public class SharedDefaultsProxy extends ProductProxy {

	/**
	 * 
	 * 	Method		:	SharedDefaultsProxy.findSystemParameterByCodes
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param systemParameterCodes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,String>
	 */
	public Map<String, String> findSystemParameterByCodes(Collection<String> systemParameterCodes)
			throws SystemException {
		try {
			return despatchRequest("findSystemParameterByCodes", systemParameterCodes);
		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, proxyException);
		} 

	}
	/**
	 * 
	 * 	Method		:	SharedDefaultsProxy.findOneTimeValues
	 *	Added by 	:	Ashil M N on 23-Sep-2021
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param parameterTypes
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,Collection<OneTimeVO>>
	 */
	public Map<String, Collection<OneTimeVO>> findOneTimeValues(String companyCode, Collection<String> parameterTypes)
			throws SystemException {
		try {
			return despatchRequest("findOneTimeValues", companyCode, parameterTypes);
		} catch (ProxyException proxyException) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE, proxyException);
		} 
	}
	   
	
	
}
