package com.ibsplc.icargo.business.mail.operations.feature.savearrivaldetails;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.InventoryForArrivalFailedException;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailbagIncorrectlyDeliveredException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.stampresdit.StampResditFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.savearrivalfeature")
@Feature(exception = MailTrackingBusinessException.class, event = StampResditFeatureConstants.FOUND_RESDIT_EVENT)
public class SaveArrivalFeature extends AbstractFeature<MailArrivalVO> {

	private static final Log LOGGER = LogFactory.getLogger(SaveArrivalFeature.class.getSimpleName());
	private static final String PERFORM_SAVE_ARRIVAL_BEAN = "mail.operations.performsavearrival";

	@Override
	protected Void perform(MailArrivalVO mailArrivalVO)
			throws SystemException, MailTrackingBusinessException, ULDDefaultsProxyException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		PerformSaveArrival performSaveArrival = (PerformSaveArrival) ICargoSproutAdapter
				.getBean(PERFORM_SAVE_ARRIVAL_BEAN);

		try {
			performSaveArrival.perform(mailArrivalVO);

		} catch (ULDDefaultsProxyException | DuplicateMailBagsException | ContainerAssignmentException
				| MailbagIncorrectlyDeliveredException | InvalidFlightSegmentException | FlightClosedException
				| InventoryForArrivalFailedException | DuplicateDSNException | CapacityBookingProxyException
				| MailBookingException e) {
			throw new MailTrackingBusinessException(e);
		}

		LOGGER.exiting(getClass().getSimpleName(), "perform");
		return null;

	}

	@Override
	protected FeatureConfigVO fetchFeatureConfig(MailArrivalVO mailArrivalVO) {
		FeatureConfigVO featureConfigVO = new FeatureConfigVO();
		featureConfigVO.setEnricherId(new ArrayList<>());
		featureConfigVO.setValidatorIds(new ArrayList<>());
		return featureConfigVO;
	}

}
