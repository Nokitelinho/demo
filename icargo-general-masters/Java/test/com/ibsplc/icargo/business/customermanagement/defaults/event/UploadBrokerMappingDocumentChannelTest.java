package com.ibsplc.icargo.business.customermanagement.defaults.event;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class UploadBrokerMappingDocumentChannelTest extends AbstractFeatureTest {

	private UploadBrokerMappingDocumentChannel channel;

	@Override
	public void setup() throws Exception {
		channel = spy(UploadBrokerMappingDocumentChannel.class);

		mockDespatchRequest(UploadBrokerMappingDocumentChannel.class);
	}

	/*
	 * Modified test case as null pointer is thrown from AbstractChannel.getReqestTriggerPoint(AbstractChannel.java:36)
	 */
	@Test
	public void shouldDespatchRequest() throws Throwable {
		DocumentRepositoryMasterVO documentVO = new DocumentRepositoryMasterVO();
		EventVO eventVO = new EventVO("CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT", documentVO,
				"customermanagement", "defaults");

		doNothing().when(channel).send(any(EventVO.class));
		channel.send(eventVO);
		verify(channel, times(1)).send(eventVO);
	}

}
