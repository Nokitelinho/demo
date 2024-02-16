package com.ibsplc.neoicargo.tracking.component.feature.getmilestones;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ibsplc.neoicargo.framework.core.security.spring.oauth2.AuthorizedService;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.tests.security.utils.MockQuantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.neoicargo.tracking.dao.TrackingDAO;
import com.ibsplc.neoicargo.tracking.helper.MockDataHelper;
import com.ibsplc.neoicargo.tracking.mapper.TrackingEntityMapper;

@RunWith(JUnitPlatform.class)
class GetMilestonesFeatureTest {

	@InjectMocks
	private GetMilestonesFeature getMilestonesFeature;
	@Mock
	private TrackingDAO trackingDAO;
	@Mock
	private AuthorizedService authService;
	@Mock
	private ContextUtil contextUtil;

	private TrackingEntityMapper trackingEntityMapper;
	private Quantities quantities;

	@BeforeEach
	public void setup() {
		quantities = MockQuantity.performInitialisation(null, null, "TRV", null);
		trackingEntityMapper = MockQuantity.injectMapper(quantities, TrackingEntityMapper.class);

		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void shouldReturnMilestones() {

		// given

		var milestones = MockDataHelper.constructMilestoneEntity();

		doReturn(milestones).when(trackingDAO).findAllMilestones();

		// when
		var milestoneVOList = getMilestonesFeature.getMilestones();

		// then
		assertEquals(milestoneVOList.size(), milestones.size());

		var isNotifMilestone = getMilestonesFeature.isNotificationMilestone("ARR");
		assertEquals(isNotifMilestone, true);
		isNotifMilestone = getMilestonesFeature.isNotificationMilestone("TRM");
		assertEquals(isNotifMilestone, false);
		var isActivtyMilestone = getMilestonesFeature.isActivityViewMilestone("ARR");
		assertEquals(isActivtyMilestone, true);
		isActivtyMilestone = getMilestonesFeature.isActivityViewMilestone("TRM");
		assertEquals(isActivtyMilestone, false);

	}

}
