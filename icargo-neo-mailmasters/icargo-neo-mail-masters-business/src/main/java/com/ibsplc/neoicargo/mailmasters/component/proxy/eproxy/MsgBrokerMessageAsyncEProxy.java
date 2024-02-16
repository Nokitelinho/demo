package com.ibsplc.neoicargo.mailmasters.component.proxy.eproxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

@EProductProxy(module = "msgbroker", submodule = "message", name = "msgBrokerMessageEProxy", asyncDispatch=true)
public interface MsgBrokerMessageAsyncEProxy {
	void encodeAndSaveMessage(BaseMessageVO baseMessageVO);
}
