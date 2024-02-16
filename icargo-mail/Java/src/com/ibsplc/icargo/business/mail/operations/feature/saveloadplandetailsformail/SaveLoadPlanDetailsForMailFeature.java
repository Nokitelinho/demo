package com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.feature.saveloadplandetailsformail.persistors.SaveLoadPlanDetailsForMailPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.FlightLoadPlanContainerVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveLoadPlanDetailsForMailFeatureConstants.SAVE_LOADPLAN_FEATURE)
@Feature(exception = MailTrackingBusinessException.class)
public class SaveLoadPlanDetailsForMailFeature extends AbstractFeature<FlightLoadPlanContainerVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveLoadPlanDetailsForMailFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(FlightLoadPlanContainerVO loadPlanVO) {
					return getBECConfigurationDetails();
	}
	private FeatureConfigVO getBECConfigurationDetails() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();	
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Void perform(FlightLoadPlanContainerVO loadPlanVO) throws SystemException, BusinessException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		new SaveLoadPlanDetailsForMailPersistor().persist(loadPlanVO);
		return null;
	}
}
