package com.ibsplc.neoicargo.mail.component.feature.stampresdit.persistors;
	

import com.ibsplc.neoicargo.mail.component.MailResdit;
import com.ibsplc.neoicargo.mail.component.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.MailResditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import org.springframework.stereotype.Component;


@Component(StampResditFeatureConstants.MAIL_RESDIT_PERSISTOR)
public class MailResditPersistor {
	
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MAIL_OPERATIONS);
	

	public void persist(MailResditVO mailResditVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		new MailResdit(mailResditVO);
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}


}
