package com.ibsplc.neoicargo.mail.component.feature.findconsignmentscreeningdetails;

import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("mail.operations.findConsignmentScreeningDetailsFeature")
@RequiredArgsConstructor
public class FindConsignmentScreeningDetailsFeature {
    private final MailController mailController;
    public ConsignmentDocumentVO perform(String consignmentNumber, String companyCode, String poaCode)
            throws MailOperationsBusinessException {
        log.debug("Invoke FindConsignmentScreeningDetailsFeature");
        try {
            return mailController.findConsignmentScreeningDetails(consignmentNumber, companyCode, poaCode);
        } catch (FinderException | PersistenceException e) {
            throw new MailOperationsBusinessException(e.getMessage());
        }
    }
}
