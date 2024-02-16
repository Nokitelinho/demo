package com.ibsplc.icargo.business.xaddons.qf.mail.operations.event;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;

public class UpdateActualWeightChannelTest  extends AbstractFeatureTest {
	private UpdateActualWeightChannel updateActualWeightChannel;

	@Override
	public void setup() throws Exception {
		updateActualWeightChannel = spy(UpdateActualWeightChannel.class);
	}
	@Test
	public void shouldDespatchRequestWhenMethodIsCalled() throws Throwable {
		ContainerVO containerVO = new ContainerVO();
		doNothing().when(updateActualWeightChannel).despatchUpdateActualWeightRequest(containerVO);
		updateActualWeightChannel.send(new EventVO("containerVO", containerVO));
		verify(updateActualWeightChannel,times(1)).despatchUpdateActualWeightRequest(containerVO);
	}
	@Test
	public void shouldNotDespatchRequest_When_COntainerIsNull() throws Throwable {
		updateActualWeightChannel.send(new EventVO("containerVO", null));
	}	
	
}
