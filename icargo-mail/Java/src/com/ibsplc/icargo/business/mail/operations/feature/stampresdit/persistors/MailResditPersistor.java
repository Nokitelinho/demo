package com.ibsplc.icargo.business.mail.operations.feature.stampresdit.persistors;
	
import com.ibsplc.icargo.business.mail.operations.MailResdit;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


@FeatureComponent(StampResditFeatureConstants.MAIL_RESDIT_PERSISTOR)
public class MailResditPersistor {
	
	private static final String MAIL_OPERATIONS = "MAIL OPERATIONS";
	private static final Log LOGGER = LogFactory.getLogger(MAIL_OPERATIONS);
	

	public void persist(MailResditVO mailResditVO) throws SystemException {
		LOGGER.entering(this.getClass().getSimpleName(), "persist");
		new MailResdit (mailResditVO);
		LOGGER.exiting(this.getClass().getSimpleName(), "persist");
	}


}
