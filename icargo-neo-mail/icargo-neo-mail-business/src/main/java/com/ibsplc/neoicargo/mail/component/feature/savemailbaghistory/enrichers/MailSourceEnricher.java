package com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.enrichers;

import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.xibase.server.framework.frontcontroller.AbstractControl;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(SaveMailbagHistoryFeatureConstants.MAIL_SOURCE_ENRICHER)
public class MailSourceEnricher extends Enricher<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void enrich(MailbagHistoryVO mailbagHistoryVO)  {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");

		String triggeringPoint = (String) ContextUtil.getRequestContext().getContextMap()
				.get(AbstractControl.REQ_TRIGGER_POINT);
		if (mailbagHistoryVO.getMailSource() != null && mailbagHistoryVO.getMailSource()
				.startsWith(MailConstantsVO.SCAN + SaveMailbagHistoryFeatureConstants.COLON)) {
			mailbagHistoryVO.setMailSource(mailbagHistoryVO.getMailSource().replace(
					MailConstantsVO.SCAN + SaveMailbagHistoryFeatureConstants.COLON,
					SaveMailbagHistoryFeatureConstants.EMPTY));
		} else if (mailbagHistoryVO.getMailSource() != null
				&& MailConstantsVO.MLD.equals(mailbagHistoryVO.getMailSource())) {
			mailbagHistoryVO.setMailSource(MailConstantsVO.MLD + SaveMailbagHistoryFeatureConstants.SPACE
					+ mailbagHistoryVO.getMessageVersion());
		} else {
			mailbagHistoryVO.setMailSource(triggeringPoint);
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "enrich");
	}

}
