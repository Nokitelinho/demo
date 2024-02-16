package com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.persistors;

import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagHistory;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

import java.util.Objects;

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
		new MailbagHistory(mailbagHistoryVO);
	}

	private MailbagPK getMailbagPK(Mailbag mailbag) {
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setMailSequenceNumber(mailbag.getMailSequenceNumber());
		mailbagPK.setCompanyCode(mailbag.getCompanyCode());
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
