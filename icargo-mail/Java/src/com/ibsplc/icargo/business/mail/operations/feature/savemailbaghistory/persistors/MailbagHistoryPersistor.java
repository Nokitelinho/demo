package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.persistors;

import java.util.Objects;

import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagHistory;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class MailbagHistoryPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryFeatureConstants.MODULE_SUBMODULE);

	public void persist(MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		Mailbag mailbag = findMailbag(mailbagHistoryVO);
		if (Objects.nonNull(mailbag)) {
			insertMailbagHistory(mailbagHistoryVO, mailbag);
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}

	private void insertMailbagHistory(MailbagHistoryVO mailbagHistoryVO, Mailbag mailbag) throws SystemException {
		new MailbagHistory(getMailbagPK(mailbag), mailbagHistoryVO);
	}

	private MailbagPK getMailbagPK(Mailbag mailbag) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setMailSequenceNumber(mailbag.getMailbagPK().getMailSequenceNumber());
		mailbagPK.setCompanyCode(mailbag.getMailbagPK().getCompanyCode());
		return mailbagPK;
	}

	private Mailbag findMailbag(MailbagHistoryVO mailbagHistoryVO) throws SystemException {
		Mailbag mailbag = null;
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setMailSequenceNumber(mailbagHistoryVO.getMailSequenceNumber());
		mailbagPK.setCompanyCode(mailbagHistoryVO.getCompanyCode());
		try {
			mailbag = Mailbag.find(mailbagPK);
		} catch (FinderException e) {
			LOGGER.log(Log.FINE, e.getMessage(), e);
		}
		return mailbag;
	}

}
