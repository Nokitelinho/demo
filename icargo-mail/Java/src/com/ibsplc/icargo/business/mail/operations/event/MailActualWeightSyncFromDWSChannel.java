package com.ibsplc.icargo.business.mail.operations.event;


import java.util.Collection;


import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.event.evaluator.MailActualWeightSyncFromDWSEvaluator;


import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;

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
@EventChannel(value = "mailActualWeightSyncFromDWSChannel", targetClass = ContainerVO.class,  listeners = {
		@EventListener(evaluator = MailActualWeightSyncFromDWSEvaluator.class,event = "SAVE_DWS") })
public class MailActualWeightSyncFromDWSChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		if (Objects.nonNull(eventVO)) {
			Collection <ContainerVO> containerVOs = (Collection<ContainerVO>) eventVO.getPayload();
			for(ContainerVO containerVO : containerVOs)  {
				if(!"MAIL".equals(containerVO.getSourceIndicator())) {
					savedespatchRequest(containerVO);
				}
		}
		}
	}

	public void savedespatchRequest(ContainerVO containerVO) throws ProxyException, SystemException {
		despatchRequest("updateActualWeightForMailContainer",containerVO);
		
	}

}
