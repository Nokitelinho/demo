package com.ibsplc.icargo.business.xaddons.lh.mail.operations.proxy;

import com.ibsplc.icargo.business.xaddons.lh.mail.operations.vo.HbaMarkingVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author 203168
 *
 */

@Module("mail")
@SubModule("operations")
public class MailOperationsProxy extends ProductProxy{
	private static final Log LOGGER = LogFactory.getLogger("lhMAIL");
	
	/**
	 * 
	 * 	Method		:	MailOperationsProxy.updateIntFlgAsNForMailBagsInConatiner
	 *	Added by 	:	203168 on 20-10-2022
	 * 	Used for 	:
	 *	Parameters	:	@param hbaMarkingVO
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void updateIntFlgAsNForMailBagsInConatiner(HbaMarkingVO hbaMarkingVO)throws  SystemException {
		try {
			despatchRequest("updateIntFlgAsNForMailBagsInConatiner",hbaMarkingVO);
		} catch (ProxyException proxyException) {
			LOGGER.log(Log.SEVERE,proxyException);
			throw new SystemException(proxyException.getMessage());
		}
	 }
}
