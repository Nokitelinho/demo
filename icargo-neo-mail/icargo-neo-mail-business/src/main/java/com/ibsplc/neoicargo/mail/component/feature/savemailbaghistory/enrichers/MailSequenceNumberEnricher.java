package com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.enrichers;


import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component(SaveMailbagHistoryFeatureConstants.MAIL_SEQEUNCE_NUMBER_ENRICHER)
public class MailSequenceNumberEnricher extends Enricher<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void enrich(MailbagHistoryVO mailbagHistoryVO) {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");

		long mailSequenceNumber;

			mailSequenceNumber = new MailController().findMailSequenceNumber(mailbagHistoryVO.getMailbagId(),
					mailbagHistoryVO.getCompanyCode());

		mailbagHistoryVO.setMailSequenceNumber(mailSequenceNumber);
		LOGGER.exiting(this.getClass().getSimpleName(), "enrich");
	}
	
}
