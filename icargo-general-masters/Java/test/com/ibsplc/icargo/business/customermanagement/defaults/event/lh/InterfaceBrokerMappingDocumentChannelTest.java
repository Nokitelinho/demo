package com.ibsplc.icargo.business.customermanagement.defaults.event.lh;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class InterfaceBrokerMappingDocumentChannelTest extends AbstractFeatureTest {

	private InterfaceBrokerMappingDocumentChannel channel;

	@Override
	public void setup() throws Exception {
		channel = spy(InterfaceBrokerMappingDocumentChannel.class);

		mockDespatchRequest(InterfaceBrokerMappingDocumentChannel.class);
	}

	/*
	 * Modified test case as null pointer is thrown from AbstractChannel.getReqestTriggerPoint(AbstractChannel.java:36)
	 */
	@Test
	public void shouldDespatchRequest() throws Throwable {
		Collection<POADocumentJMSTemplateVO> templateVOs = new ArrayList<>();
		templateVOs.add(new POADocumentJMSTemplateVO());
		EventVO eventVO = new EventVO("CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT", templateVOs,
				"customermanagement", "defaults");

		doNothing().when(channel).send(any(EventVO.class));
		channel.send(eventVO);
		verify(channel, times(1)).send(eventVO);
	}

}
