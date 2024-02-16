package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.enrichers;

import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveMailbagHistoryFeatureConstants.MAIL_SOURCE_ENRICHER)
public class MailSourceEnricher extends Enricher<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void enrich(MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");
		String triggeringPoint = ContextUtils.getRequestContext()
				.getParameter(SaveMailbagHistoryFeatureConstants.REQ_TRIGGERPOINT);
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
