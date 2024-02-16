package com.ibsplc.icargo.business.xaddons.oz.mail.operations.proxy;

import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.framework.proxy.AsyncProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
/**
 * 
 * @author A-8816
 *
 */

@Module("mail")
@SubModule("operations")
public class MailOperationsAsyncProxy extends AsyncProxy{
	
	/**
	 * 
	 * 	Method		:	MailOperationsAsyncProxy.performMailAWBTransactions
	 *	Added by 	:	A-8816	7 Aug 2019
	 * 	Used for 	:
	 *	Parameters	:	@param mailFlightSummaryVO
	 *	Parameters	:	@param evenCode 
	 *	Return type	: 	void
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public void performMailAWBTransactions(
			MailFlightSummaryVO mailFlightSummaryVO, String eventCode) throws ProxyException, SystemException {
		dispatchAsyncRequest("performMailAWBTransactions",false,mailFlightSummaryVO,eventCode);
	 }
}
