package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory;

import java.util.ArrayList;
import java.util.List;

import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.persistors.MailbagHistoryPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveMailbagHistoryFeatureConstants.SAVE_MAILBAG_HISTORY_FEATURE)
@Feature(exception = BusinessException.class)
public class SaveMailbagHistoryFeature extends AbstractFeature<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailbagHistoryVO mailbagHistoryVO) {
		return getBECConfigurationDetails();
	}

	private FeatureConfigVO getBECConfigurationDetails() {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		List<String> enricherIds = new ArrayList<>();
		enricherIds.add(SaveMailbagHistoryFeatureConstants.MAIL_SEQEUNCE_NUMBER_ENRICHER);
		enricherIds.add(SaveMailbagHistoryFeatureConstants.MAIL_SOURCE_ENRICHER);
		featureConfigVO.setEnricherId(enricherIds);
		return featureConfigVO;
	}

	@Override
	protected Void perform(MailbagHistoryVO mailbagHistoryVO) throws SystemException, BusinessException {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		new MailbagHistoryPersistor().persist(mailbagHistoryVO);
		return null;
	}

}
