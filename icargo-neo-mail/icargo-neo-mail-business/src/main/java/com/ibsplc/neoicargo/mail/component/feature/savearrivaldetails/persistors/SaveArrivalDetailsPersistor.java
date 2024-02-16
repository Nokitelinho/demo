package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors;

import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.component.MailArrival;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.CapacityBookingProxyException;
import com.ibsplc.neoicargo.mail.exception.ContainerAssignmentException;
import com.ibsplc.neoicargo.mail.exception.DuplicateDSNException;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.FlightClosedException;
import com.ibsplc.neoicargo.mail.exception.InvalidFlightSegmentException;
import com.ibsplc.neoicargo.mail.exception.InventoryForArrivalFailedException;
import com.ibsplc.neoicargo.mail.exception.MailBookingException;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.exception.MailbagIncorrectlyDeliveredException;
import com.ibsplc.neoicargo.mail.exception.ULDDefaultsProxyException;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Slf4j
@Component("saveArrivalDetailsPersistor")
public class SaveArrivalDetailsPersistor {

    public void persist(MailArrivalVO mailArrivalVO, Collection<MailbagVO> arrivedMailBagsForMonitorSLA, Collection<MailbagVO> deliveredMailBagsForMonitorSLA) throws ContainerAssignmentException,
            DuplicateMailBagsException, MailbagIncorrectlyDeliveredException, InvalidFlightSegmentException,
            FlightClosedException, InventoryForArrivalFailedException, ULDDefaultsProxyException, DuplicateDSNException,
            CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
        log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Entering");
        ContextUtil.getInstance().getBean(MailArrival.class).saveArrivalDetails(mailArrivalVO, arrivedMailBagsForMonitorSLA,
                deliveredMailBagsForMonitorSLA);
        log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Exiting");
    }
}
