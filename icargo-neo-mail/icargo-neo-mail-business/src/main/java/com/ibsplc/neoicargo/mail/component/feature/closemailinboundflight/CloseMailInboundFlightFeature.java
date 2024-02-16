package com.ibsplc.neoicargo.mail.component.feature.closemailinboundflight;

import com.ibsplc.neoicargo.framework.orchestration.AbstractFeature;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.springframework.stereotype.Component;
import com.ibsplc.neoicargo.framework.orchestration.FeatureConfigSource;

@Component("mail.operations.closemailinboundflightfeature")
@FeatureConfigSource("feature/closemailinboundflight")
@Slf4j
public class CloseMailInboundFlightFeature extends AbstractFeature<OperationalFlightVO> {

	@Override
	protected Void perform(OperationalFlightVO operationalFlightVO) throws MailOperationsBusinessException {
		log.debug(getClass().getSimpleName() + " : " + "perform" + " Entering");
		PerformCloseMailInboundFlight performCloseInboundFlight = ContextUtil.getInstance()
				.getBean(PerformCloseMailInboundFlight.class);
		try {
			performCloseInboundFlight.perform(operationalFlightVO);
		} catch (ULDDefaultsProxyException e) {
			throw new MailOperationsBusinessException(e);
		}
		log.debug(getClass().getSimpleName() + " : " + "perform" + " Exiting");
		return null;
	}
}
