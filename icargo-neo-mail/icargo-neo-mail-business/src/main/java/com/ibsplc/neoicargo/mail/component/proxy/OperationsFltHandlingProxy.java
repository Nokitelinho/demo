package com.ibsplc.neoicargo.mail.component.proxy;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.operations.shipment.vo.UldInFlightVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.OperationsFltHandlingEProxy;
import org.springframework.beans.factory.annotation.Autowired;

/** 
 * @author A-1739
 */
@Component
@Slf4j
public class OperationsFltHandlingProxy {
	@Autowired
	private OperationsFltHandlingEProxy operationsFltHandlingEProxy;

	public void saveOperationalULDsInFlight(Collection<UldInFlightVO> uldInFlightVOs) {
		log.debug("OperationsFltHandlingProxy" + " : " + "saveOperationalULDsInFlight" + " Entering");
		log.debug("" + " uldinflts for save " + " " + uldInFlightVOs);
		operationsFltHandlingEProxy.saveOperationalULDsInFlight(uldInFlightVOs);
		log.debug("OperationsFltHandlingProxy" + " : " + "saveOperationalULDsInFlight" + " Exiting");
	}
	public void updateOperationalFlightStatus(OperationalFlightVO operationalFlightVO) {
		log.debug("OperationsFltHandlingProxy" + " : " + "updateOperationalFlightStatus" + " Entering");
		log.debug("" + " operationalFlightVO " + " " + operationalFlightVO);
		operationsFltHandlingEProxy.updateOperationalFlightStatus(operationalFlightVO);
		log.debug("OperationsFltHandlingProxy" + " : " + "updateOperationalFlightStatus" + " Exiting");
	}
}
