package com.ibsplc.icargo.business.mail.operations.event;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

@Module("mail")
@SubModule("operations")
@EventChannel(value = "mail.operations.saveMailbagHistoryChannel", targetClass = MailbagHistoryVO.class, targetType = ElementType.LIST, listeners = {
		@EventListener(event = "MAIL_OPERATIONS_SAVEMAILBAGHISTORY_CONSIGNMENT_UPLOAD") })
public class SaveMailbagHistoryChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		Collection<MailbagHistoryVO> mailbagHistoryVOs = (Collection<MailbagHistoryVO>) eventVO.getPayload();
		if (Objects.nonNull(mailbagHistoryVOs) && !mailbagHistoryVOs.isEmpty()) {
			despatchsaveMailbagHistoryRequest(mailbagHistoryVOs);
		}
	}

	public void despatchsaveMailbagHistoryRequest(Collection<MailbagHistoryVO> mailbagHistoryVOs)
			throws ProxyException, SystemException {
		despatchRequest("saveMailbagHistory", mailbagHistoryVOs);
	}

}
