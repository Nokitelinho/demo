package com.ibsplc.icargo.business.mail.operations.feature.closemailinboundflight;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.closemailinboundflightfeature")
@Feature(exception = MailTrackingBusinessException.class, event = StampResditFeatureConstants.LOST_RESDIT_EVENT)
public class CloseMailInboundFlightFeature extends AbstractFeature<OperationalFlightVO> {

	private static final Log LOGGER = LogFactory.getLogger(CloseMailInboundFlightFeature.class.getSimpleName());
	private static final String PERFORM_CLOSE_INBOUND_FLIGHT_BEAN = "mail.operations.performclosemailinboundflight";

	@Override
	protected Void perform(OperationalFlightVO operationalFlightVO)
			throws SystemException, MailTrackingBusinessException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		PerformCloseMailInboundFlight performCloseInboundFlight = (PerformCloseMailInboundFlight) ICargoSproutAdapter.getBean(PERFORM_CLOSE_INBOUND_FLIGHT_BEAN);
		try {
			performCloseInboundFlight.perform(operationalFlightVO);
		} catch (ULDDefaultsProxyException e) {
			throw new MailTrackingBusinessException(e);
		}
		LOGGER.exiting(getClass().getSimpleName(), "perform");
		return null;
	}

	@Override
	protected FeatureConfigVO fetchFeatureConfig(OperationalFlightVO operationalFlightVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	}
}
