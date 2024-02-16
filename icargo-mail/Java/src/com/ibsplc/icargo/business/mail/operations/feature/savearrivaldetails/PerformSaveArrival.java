package com.ibsplc.icargo.business.mail.operations.feature.savearrivaldetails;

import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.InventoryForArrivalFailedException;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.MailbagIncorrectlyDeliveredException;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

@FeatureComponent("mail.operations.performsavearrival")
public class PerformSaveArrival {

	private static final Log LOGGER = LogFactory.getLogger(PerformSaveArrival.class.getSimpleName());

	public void perform(MailArrivalVO mailArrivalVO)
			throws SystemException, MailTrackingBusinessException, ULDDefaultsProxyException,
			ContainerAssignmentException, DuplicateMailBagsException, MailbagIncorrectlyDeliveredException,
			InvalidFlightSegmentException, FlightClosedException, InventoryForArrivalFailedException,
			DuplicateDSNException, CapacityBookingProxyException, MailBookingException {
		LOGGER.entering(getClass().getSimpleName(), "perform");
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.saveArrivalDetailsOld(mailArrivalVO);
		LOGGER.exiting(getClass().getSimpleName(), "perform");
	}

}
