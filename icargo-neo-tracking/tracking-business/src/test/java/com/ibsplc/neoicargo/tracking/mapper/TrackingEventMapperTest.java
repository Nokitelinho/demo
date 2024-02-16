package com.ibsplc.neoicargo.tracking.mapper;

import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
public class TrackingEventMapperTest {

	@Mock
	private ContextUtil contextUtil;
	@Mock
	private LoginProfile loginProfile;
	private TrackingEventMapper trackingEventMapper;
	private Quantities quantities;


	@BeforeEach
	public void setup() {
		quantities = MockQuantity.performInitialisation(null, null, null, null);
		trackingEventMapper = MockQuantity.injectMapper(quantities, TrackingEventMapper.class);
		MockitoAnnotations.initMocks(this);
		doReturn(loginProfile).when(contextUtil).callerLoginProfile();
		doReturn("CODE").when(loginProfile).getCompanyCode();
	}

	@Test
	void shouldConstructMilestoneEventVo() {
		// given
		var historyEvent = MockDataHelper.constructShipmentHistoryEvent();
		var createdEventVO = MockDataHelper.constructShipmentMilestoneEventVOWithWeight(quantities);

		// when
		var shipmentMilestoneEventVO = trackingEventMapper.constructShipmentMilestoneEventVO(historyEvent, contextUtil);

		// then
		Assertions.assertEquals(createdEventVO, shipmentMilestoneEventVO);
	}

	@Test
	void shouldHandleShipmentMilestonePlanCreatedEvent() {

		// given
		var plan = MockDataHelper.constructShipmentMilestonePlanCreatedEvent();
		var createdPlanVOs = MockDataHelper.constructShipmentMilestonePlanCreatedVO();

		// when
		var shipmentMilestonePlanCreatedVO = trackingEventMapper.constructShipmentMilestonePlanCreatedVO(plan, contextUtil);

		// then
		Assertions.assertEquals(createdPlanVOs, shipmentMilestonePlanCreatedVO);
	}
}
