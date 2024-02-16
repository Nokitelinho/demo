package com.ibsplc.icargo.business.uld.defaults.feature.sendscmreminder;

import java.util.ArrayList;

import com.ibsplc.icargo.business.operations.shipment.ShipmentBusinessException;
import com.ibsplc.icargo.business.uld.defaults.vo.SCMReminderFeatureVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("uld.defaults.scmRemainderNotificationsFeature")
@Feature(exception=ShipmentBusinessException.class, event="SENDSCMREMINDER_EVENT", methodId="uld.defaults.triggerSCMReminder")
public class SCMReminderNotificationsFeature extends AbstractFeature<SCMReminderFeatureVO> {

	private static final Log log = LogFactory.getLogger(SCMReminderNotificationsFeature.class.getSimpleName());
	@Override
	protected FeatureConfigVO fetchFeatureConfig(SCMReminderFeatureVO featureVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	}

	@Override
	protected <R> R perform(SCMReminderFeatureVO featureVO) throws SystemException, BusinessException {
		log.entering(SCMReminderNotificationsFeature.class.getSimpleName(), "perform");
		log.exiting(SCMReminderNotificationsFeature.class.getSimpleName(), "perform");
		return null;
	}
	
}
