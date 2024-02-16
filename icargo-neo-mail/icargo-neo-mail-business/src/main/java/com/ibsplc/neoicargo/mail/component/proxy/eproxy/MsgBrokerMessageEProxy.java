package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;

@EProductProxy(module = "msgbroker", submodule = "message", name = "msgBrokerMessageEProxy")
public interface MsgBrokerMessageEProxy {
	MessageVO findMessageDetails(String companyCode, String stationCode, String messageType, int sequenceNumber);

	Void sendMessageText(MessageVO messageVo);

	Collection<MessageVO> encodeAndSaveMessage(BaseMessageVO baseMessageVO);
}
