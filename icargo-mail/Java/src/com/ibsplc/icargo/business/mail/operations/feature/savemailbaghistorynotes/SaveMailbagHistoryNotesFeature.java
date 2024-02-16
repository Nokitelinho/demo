package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes.persistors.SaveMailbagHistoryPersistor;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveMailbagHistoryNotesConstants.SAVE_MAILBAG_NOTES_FEATURE)
@Feature(exception = BusinessException.class)
public class SaveMailbagHistoryNotesFeature extends AbstractFeature<MailHistoryRemarksVO> {
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryNotesConstants.MODULE_SUBMODULE);

	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailHistoryRemarksVO mailHistoryRemarksVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	
	}
	
	@Override
	protected Void perform(MailHistoryRemarksVO mailHistoryRemarksVO) throws SystemException, BusinessException {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
			new SaveMailbagHistoryPersistor().persist(mailHistoryRemarksVO);
		return null;
	}
}
