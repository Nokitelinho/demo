package com.ibsplc.neoicargo.mailmasters.component.proxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy.MsgBrokerMessageAsyncEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** 
 * @author a-1303
 */
@Component
@Slf4j
public class MsgBrokerMessageProxy {
	@Autowired
	private MsgBrokerMessageAsyncEProxy msgBrokerMessageAsyncEProxy;
	private static final String MODULE = "MsgBrokerMessageEProxy";

	public void encodeAndSaveMessageAsync(BaseMessageVO baseMessageVO) {
		log.debug(MODULE + " : " + "encodeAndSaveMessageAsync" + " Entering");
		log.debug("" + "INSIDE THE MSG BROKER MESSAGE PROXY baseMessageVO VO IS " + " " + baseMessageVO);
		try {
			//TODO: make the request dispatch order isordered=true
			 msgBrokerMessageAsyncEProxy.encodeAndSaveMessage(baseMessageVO);
		} catch (SystemException proxyException) {
			throw new SystemException(proxyException.getMessage(), proxyException);
		}
	}

}
