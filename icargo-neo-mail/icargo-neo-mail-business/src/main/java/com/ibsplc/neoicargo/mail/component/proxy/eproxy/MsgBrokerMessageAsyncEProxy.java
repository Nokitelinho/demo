package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;
import com.ibsplc.neoicargo.mail.vo.BaseMessageVO;

@EProductProxy(module = "msgbroker", submodule = "message", name = "msgBrokerMessageAsyncEProxy", asyncDispatch=true)
public interface MsgBrokerMessageAsyncEProxy {
	void encodeAndSaveMessage(BaseMessageVO baseMessageVO);
}
