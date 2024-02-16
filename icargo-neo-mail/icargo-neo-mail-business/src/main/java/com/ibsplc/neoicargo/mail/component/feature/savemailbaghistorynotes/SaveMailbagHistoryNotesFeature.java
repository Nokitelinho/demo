package com.ibsplc.neoicargo.mail.component.feature.savemailbaghistorynotes;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistorynotes.persistors.SaveMailbagHistoryPersistor;
import com.ibsplc.neoicargo.mail.vo.MailHistoryRemarksVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component(SaveMailbagHistoryNotesConstants.SAVE_MAILBAG_NOTES_FEATURE)
@FeatureConfigSource("mail/savemailbagnotesfeature")
@Slf4j
public class SaveMailbagHistoryNotesFeature extends AbstractFeature<MailHistoryRemarksVO> {
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryNotesConstants.MODULE_SUBMODULE);

	@Override
	protected Void perform(MailHistoryRemarksVO mailHistoryRemarksVO)  {
		LOGGER.exiting(this.getClass().getSimpleName(), "perform");
		try {
			new SaveMailbagHistoryPersistor().persist(mailHistoryRemarksVO);
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return null;
	}
}
