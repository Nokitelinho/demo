package com.ibsplc.icargo.business.mail.operations.feature.closemailinboundflight;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;

public class CloseMailInboundFlightFeatureTest extends AbstractFeatureTest {
	
	private CloseMailInboundFlightFeature spy;
	private OperationalFlightVO operationalFlightVO;
	private PerformCloseMailInboundFlight performCloseMailInboundFlight;

	@Override
	public void setup() throws Exception {
		spy = spy((CloseMailInboundFlightFeature) ICargoSproutAdapter.getBean("mail.operations.closemailinboundflightfeature"));
		performCloseMailInboundFlight = mockBean("mail.operations.performclosemailinboundflight",PerformCloseMailInboundFlight.class);
		operationalFlightVO = setUpOperationalFlightVO();
		
	}

	private OperationalFlightVO setUpOperationalFlightVO() {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(getCompanyCode());
		operationalFlightVO.setFlightNumber("0023");
		operationalFlightVO.setFlightSequenceNumber(1);
		operationalFlightVO.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		operationalFlightVO.setCarrierCode(getCompanyCode());
		operationalFlightVO.setLegSerialNumber(1);
		return operationalFlightVO;
	}
	
	@Test()
	public void shouldPerformCloseMailInboundFlight_WhenInvoked() throws Exception {
		spy.execute(operationalFlightVO);
		verify(performCloseMailInboundFlight, times(1)).perform(operationalFlightVO);
	}
	
	@Test(expected = MailTrackingBusinessException.class)
	public void shouldThrowBusinessException_When_performCloseMailInboundFlightFails() throws Exception {
		doThrow(ULDDefaultsProxyException.class).when(performCloseMailInboundFlight).perform(operationalFlightVO);
		spy.execute(operationalFlightVO);
	}

}
