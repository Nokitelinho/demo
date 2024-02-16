package com.ibsplc.icargo.business.mail.operations.event;

import java.util.Collection;
import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
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
@EventChannel(value = "mail.operations.stampResditChannel", targetClass = MailResditVO.class, targetType = ElementType.LIST, listeners = {
		@EventListener(event = StampResditFeatureConstants.LOST_RESDIT_EVENT),
		@EventListener(event = StampResditFeatureConstants.FOUND_RESDIT_EVENT) })
public class StampResditChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		if (Objects.nonNull(eventVO)) {
			Collection<MailResditVO> mailResditVOs = (Collection<MailResditVO>) eventVO.getPayload();
			if (Objects.nonNull(mailResditVOs) && !mailResditVOs.isEmpty()) {
				despatchStampResditRequest(mailResditVOs);
			}
		}
	}

	public void despatchStampResditRequest(Collection<MailResditVO> mailResditVOs)
			throws ProxyException, SystemException {
		despatchRequest("stampResdits", mailResditVOs);
	}

}
