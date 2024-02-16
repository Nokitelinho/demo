package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.enrichers;

import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent(SaveMailbagHistoryFeatureConstants.MAIL_SEQEUNCE_NUMBER_ENRICHER)
public class MailSequenceNumberEnricher extends Enricher<MailbagHistoryVO> {
	
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	@Override
	public void enrich(MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "enrich");
		MailOperationsProxy mailOperationsProxy = new MailOperationsProxy();
		long mailSequenceNumber;
		try {
			mailSequenceNumber = mailOperationsProxy.findMailSequenceNumber(mailbagHistoryVO.getMailbagId(),
					mailbagHistoryVO.getCompanyCode());
		} catch (ProxyException e) {
			throw new SystemException(e.getMessage(),e);
		}
		mailbagHistoryVO.setMailSequenceNumber(mailSequenceNumber);
		LOGGER.exiting(this.getClass().getSimpleName(), "enrich");
	}
	
}
