package com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes.persistors;

import com.ibsplc.icargo.business.mail.operations.MailHistoryRemarks;
import com.ibsplc.icargo.business.mail.operations.feature.savemailbaghistorynotes.SaveMailbagHistoryNotesConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailHistoryRemarksVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class SaveMailbagHistoryPersistor {
	private static final Log LOGGER = LogFactory.getLogger(SaveMailbagHistoryNotesConstants.MODULE_SUBMODULE);

	public void persist(MailHistoryRemarksVO mailHistoryRemarksVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		MailHistoryRemarks mailHistoryRemarks = new MailHistoryRemarks(mailHistoryRemarksVO);
		try {

			PersistenceController.getEntityManager().persist(mailHistoryRemarks);
		} catch (CreateException createException) {
			createException.getErrorCode();
	            throw new SystemException(createException.getMessage(), createException);
		}
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
		
	}

}
