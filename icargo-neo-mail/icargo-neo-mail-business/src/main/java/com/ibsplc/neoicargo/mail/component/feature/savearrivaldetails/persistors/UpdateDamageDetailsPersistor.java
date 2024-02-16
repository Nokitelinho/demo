package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors;

import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
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
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("updateDamageDetailsPersistor")
public class UpdateDamageDetailsPersistor {

    public void persist(MailbagVO mailbagVO) throws ContainerAssignmentException,
            DuplicateMailBagsException, MailbagIncorrectlyDeliveredException, InvalidFlightSegmentException,
            FlightClosedException, InventoryForArrivalFailedException, ULDDefaultsProxyException, DuplicateDSNException,
            CapacityBookingProxyException, MailBookingException, MailOperationsBusinessException {
        log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Entering");
        MailbagPK mailbagPk = new MailbagPK();
        Mailbag mailbag = null;
        mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
        mailbagPk.setMailSequenceNumber(mailbagVO.getMailSequenceNumber() > 0 ? mailbagVO.getMailSequenceNumber()
                : findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode()));
        try {
            mailbag = Mailbag.find(mailbagPk);
        } catch (FinderException e) {
            log.error("Finder Exception Caught");
        }
        if (mailbag != null) {
            mailbag.setMailCompanyCode(mailbagVO.getMailCompanyCode());
            if (MailConstantsVO.FLAG_YES.equals(mailbagVO.getDamageFlag())) {
                mailbag.setDamageFlag(mailbagVO.getDamageFlag());
                new Mailbag().updateDamageDetails(mailbagVO);
            }
        }
        log.debug(this.getClass().getSimpleName() + " : " + "persist" + " Exiting");
    }

    private long findMailSequenceNumber(String mailIdr, String companyCode) {
        return Mailbag.findMailBagSequenceNumberFromMailIdr(mailIdr, companyCode);
    }
}
