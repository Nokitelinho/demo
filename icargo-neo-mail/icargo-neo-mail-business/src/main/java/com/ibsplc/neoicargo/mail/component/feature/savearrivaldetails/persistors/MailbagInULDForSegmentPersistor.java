package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors;

import com.ibsplc.neoicargo.mail.component.MailbagInULDForSegment;
import com.ibsplc.neoicargo.mail.component.MailbagInULDForSegmentPK;
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
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mailbagInULDForSegmentPersistor")
public class MailbagInULDForSegmentPersistor {

    public void persist(MailbagVO mailbagVO) throws ContainerAssignmentException,
            DuplicateMailBagsException, MailbagIncorrectlyDeliveredException, InvalidFlightSegmentException,
            FlightClosedException, InventoryForArrivalFailedException, ULDDefaultsProxyException, DuplicateDSNException,
            CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
        MailbagInULDForSegment mailbagInULDForSegment = null;
        MailbagInULDForSegmentPK mailbagInULDForSegmentPK = new MailbagInULDForSegmentPK();
        mailbagInULDForSegmentPK.setCompanyCode(mailbagVO.getCompanyCode());
        mailbagInULDForSegmentPK.setCarrierId(mailbagVO.getCarrierId());
        mailbagInULDForSegmentPK.setFlightNumber(mailbagVO.getFlightNumber());
        mailbagInULDForSegmentPK.setFlightSequenceNumber(mailbagVO.getFlightSequenceNumber());
        mailbagInULDForSegmentPK.setSegmentSerialNumber(mailbagVO.getSegmentSerialNumber());
        mailbagInULDForSegmentPK.setUldNumber(mailbagVO.getUldNumber());
        try {
            mailbagInULDForSegment = MailbagInULDForSegment.find(mailbagInULDForSegmentPK);
        } catch (FinderException e) {
            log.error("Finder Exception Caught");
        }
        if (mailbagInULDForSegment != null) {
            if (mailbagVO.getArrivalSealNumber() != null && !mailbagVO.getArrivalSealNumber().trim().isEmpty()) {
                mailbagInULDForSegment.setArrivalsealNumber(mailbagVO.getArrivalSealNumber());
            }
        }
        log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Exiting");
    }
}
