package com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.persistors.MailbagHistoryPersistor;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
@Component("mail.operations.savemailbaghistoryfeature")
@FeatureConfigSource("mail/savemailbaghistory")
@Slf4j
public class SaveMailbagHistoryFeature extends AbstractFeature<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	protected Void perform(MailbagHistoryVO mailbagHistoryVO) throws MailOperationsBusinessException {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		try {
			new MailbagHistoryPersistor().persist(mailbagHistoryVO);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return null;
	}

}
