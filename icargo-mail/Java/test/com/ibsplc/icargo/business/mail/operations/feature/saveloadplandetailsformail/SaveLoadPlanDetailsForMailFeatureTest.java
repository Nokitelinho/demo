package com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail;

import com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.persistors.SaveLoadPlanDetailsForMailPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

public class SaveLoadPlanDetailsForMailFeatureTest extends AbstractFeatureTest {

	private SaveLoadPlanDetailsForMailFeature spy;
	private SaveLoadPlanDetailsForMailPersistor saveLoadPlanDetailsForMailPersistor;

	@Override
	public void setup() throws Exception {
		saveLoadPlanDetailsForMailPersistor =mockBean(SaveLoadPlanDetailsForMailFeatureConstants.SAVE_LOADPLAN_PERSISTOR, SaveLoadPlanDetailsForMailPersistor.class);
		spy = spy((SaveLoadPlanDetailsForMailFeature) ICargoSproutAdapter.getBean(SaveLoadPlanDetailsForMailFeatureConstants.SAVE_LOADPLAN_FEATURE));
	}
	@Test()
	public void saveFligthLoadPlanForMail_WhenInvoked() throws Exception {
		Collection<FlightLoadPlanContainerVO> flightLoadPlanContainerVOs = new ArrayList();
		FlightLoadPlanContainerVO flightLoadPlanContainerVO = new FlightLoadPlanContainerVO();
		flightLoadPlanContainerVOs.add(flightLoadPlanContainerVO);
		doNothing().when(saveLoadPlanDetailsForMailPersistor).persist(flightLoadPlanContainerVO);
		spy.execute(flightLoadPlanContainerVO);
		verify(spy, times(1)).perform(any(FlightLoadPlanContainerVO.class));
	}
}
