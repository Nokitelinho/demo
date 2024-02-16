package com.ibsplc.icargo.business.uld.defaults.event;

import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.uld.defaults.event.evaluator.SendUCMChannelEvaluator;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

import java.util.Collection;

@Module("uld")
@SubModule("defaults")
@EventChannel(
		value = "uld.defaults.sendUCMChannel",
		targetClass = UCMMessageVO.class,
		targetType = ElementType.LIST,
		listeners = {@EventListener(
				evaluator = SendUCMChannelEvaluator.class,
				event = "WAREHOUSE_DEFAULTS_SAVERAMPTRANSFER"
		)}
)
public class SendUCMChannel extends AbstractChannel {
	@Override
	public void send(EventVO eventVO) throws Throwable {
		Collection<UCMMessageVO> ucmMessageVOs = (Collection<UCMMessageVO>) eventVO.getPayload();
		despatchRequest("generateUCMMessage", ucmMessageVOs);
	}

}
