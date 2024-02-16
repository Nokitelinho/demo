package com.ibsplc.icargo.business.uld.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.msgbroker.config.format.vo.MessageRuleDefenitionVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("msgbroker")
@SubModule("config")
public class MsgBrokerConfigProxy extends ProductProxy {

	/**
	 * 
	 * 	Method		:	MsgBrokerConfigProxy.findMessageTypeAndVersion
	 *	Added by 	:	A-5163 on Apr 18, 2013
	 * 	Used for 	:	Finding the highest version of a specific message
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param messageType
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<MessageRuleDefenitionVO>
	 */
	public Collection<MessageRuleDefenitionVO> findMessageTypeAndVersion(
			String companyCode, String messageType) throws SystemException {
		try {
			return despatchRequest("findMessageRuleWithoutVersionAndStandard",
					companyCode, messageType);
		} catch (ProxyException e) {
			throw new SystemException(e.getErrors());
		}
	}

}
