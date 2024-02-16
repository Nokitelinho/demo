package com.ibsplc.neoicargo.mail.component.feature.closemailinboundflight;

import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.springframework.stereotype.Component;

@Component("mail.operations.performclosemailinboundflight")
@Slf4j
public class PerformCloseMailInboundFlight {
	public void perform(OperationalFlightVO operationalFlightVO) throws ULDDefaultsProxyException {
		log.debug(getClass().getSimpleName() + " : " + "perform" + " Entering");
		MailController mailController = ContextUtil.getInstance().getBean(MailController.class);
		mailController.closeInboundFlight(operationalFlightVO);
		log.debug(getClass().getSimpleName() + " : " + "perform" + " Exiting");
	}
}
