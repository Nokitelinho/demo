package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.msgbroker.message.vo.BaseMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.MsgBrokerMessageEProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

/** 
 * Proxy for MessageBroker
 * @author A-1739
 */
@Component
public class MsgBrokerMessageProxy {
	@Autowired
	private MsgBrokerMessageEProxy msgBrokerMessageEProxy;

	/** 
	* TODO Purpose Oct 10, 2007, a-1739
	* @param companyCode
	* @param stationCode
	* @param messageType
	* @param sequenceNumber
	* @return
	* @throws SystemException
	*/
	public MessageVO findMessageDetails(String companyCode, String stationCode, String messageType,
			int sequenceNumber) {
		return msgBrokerMessageEProxy.findMessageDetails(companyCode, stationCode, messageType, sequenceNumber);
	}

	/** 
	* This method is used to send  the Cardit Message to the Transferred Carriers wenever a Transfer of MailBags Happens TODO Purpose Oct 10, 2007, a-1739
	* @param messageVo
	* @throws SystemException
	*/
	public void sendMessageText(MessageVO messageVo) {
		msgBrokerMessageEProxy.sendMessageText(messageVo);
	}

	public Collection<MessageVO>  encodeAndSaveMessage(BaseMessageVO baseMessageVO){
		return msgBrokerMessageEProxy.encodeAndSaveMessage(baseMessageVO);
	}
}
