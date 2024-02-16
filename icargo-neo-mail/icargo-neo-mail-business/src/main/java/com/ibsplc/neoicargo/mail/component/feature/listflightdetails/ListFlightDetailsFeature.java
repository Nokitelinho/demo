package com.ibsplc.neoicargo.mail.component.feature.listflightdetails;

import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.MailArrivalModel;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component("mail.operations.listFlightDetailsFeature")
@RequiredArgsConstructor
public class ListFlightDetailsFeature {
    private final MailController mailController;
    private final MailOperationsMapper mailOperationsMapper;

    public Page<MailArrivalModel> perform(MailArrivalVO mailArrivalVO)
            throws MailOperationsBusinessException {
        log.info("Invoke ListFlightDetailsFeature");
        try {
            Page<MailArrivalVO> mailArrivalVOsPage = mailController.listFlightDetails(mailArrivalVO);
            List<MailArrivalVO> mailArrivalVOsList = new ArrayList<>(mailArrivalVOsPage);
            List<MailArrivalModel> mailArrivalModelsList = mailOperationsMapper.mailArrivalVOsToMailArrivalModel(mailArrivalVOsList);
            return new Page<>(mailArrivalModelsList,
                    mailArrivalVOsPage.getPageNumber(), mailArrivalVOsPage.getDefaultPageSize(), mailArrivalVOsPage.getActualPageSize(),
                    mailArrivalVOsPage.getStartIndex(), mailArrivalVOsPage.getEndIndex(), mailArrivalVOsPage.getAbsoluteIndex(), mailArrivalVOsPage.hasNextPage(),
                    mailArrivalVOsPage.getTotalRecordCount());
        } catch (PersistenceException e) {
            throw new MailOperationsBusinessException(e.getMessage());
        }
    }
}
