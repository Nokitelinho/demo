package com.ibsplc.icargo.business.uld.defaults.feature.sendscmreminder;

import static org.junit.Assert.assertNull;

import org.junit.Test;

import com.ibsplc.icargo.business.uld.defaults.vo.SCMReminderFeatureVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class SCMReminderNotificationsFeatureTest extends AbstractFeatureTest {

	private SCMReminderNotificationsFeature scmReminderNotificationsFeature;
	
	private SCMReminderFeatureVO scmReminderFeatureVO;
	
	@Override
	public void setup() throws Exception {
		scmReminderNotificationsFeature = (SCMReminderNotificationsFeature) ICargoSproutAdapter.getBean("uld.defaults.scmRemainderNotificationsFeature");
	}

	@Test
	public void checkIfSendSCMReminderNotificationsFeatureIsCallable() throws BusinessException, SystemException {
		assertNull(scmReminderNotificationsFeature.execute(scmReminderFeatureVO));
	}
}
