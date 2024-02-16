package com.ibsplc.icargo.business.mail.operations.feature.closemailinboundflight;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.performclosemailinboundflight")
public class PerformCloseMailInboundFlight {

	private static final Log LOGGER = LogFactory.getLogger(PerformCloseMailInboundFlight.class.getSimpleName());

	public void perform(OperationalFlightVO operationalFlightVO) throws SystemException, ULDDefaultsProxyException{
		LOGGER.entering(getClass().getSimpleName(), "perform");
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.closeInboundFlight(operationalFlightVO);
		LOGGER.exiting(getClass().getSimpleName(), "perform");
	}

}
