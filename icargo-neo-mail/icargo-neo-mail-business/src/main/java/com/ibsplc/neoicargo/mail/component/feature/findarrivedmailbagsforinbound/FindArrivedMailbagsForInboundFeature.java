package com.ibsplc.neoicargo.mail.component.feature.findarrivedmailbagsforinbound;

import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import com.ibsplc.neoicargo.mail.vo.MailArrivalFilterVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component("mail.operations.findArrivedMailbagsForInboundFeature")
@RequiredArgsConstructor
public class FindArrivedMailbagsForInboundFeature {
    private final MailController mailController;
    private final MailOperationsMapper mailOperationsMapper;

    public Page<MailbagModel> perform(MailArrivalFilterVO mailArrivalFilterVO)
            throws MailOperationsBusinessException {
        log.info("Invoke FindArrivedMailbagsForInboundFeature");
        try {
            Page<MailbagVO> mailbagDetailsVOsPage = mailController.findArrivedMailbagsForInbound(mailArrivalFilterVO);
            List<MailbagModel> mailbagDetailsModelsList;
            if (Objects.isNull(mailbagDetailsVOsPage)) {
                mailbagDetailsModelsList = new ArrayList<>(Collections.emptyList());
                return new Page<>(mailbagDetailsModelsList, 1, 100, 0, 0, 0, 0,
                        false, 0);
            }
            ArrayList<MailbagVO> containerDetailsVOsList = new ArrayList<>(mailbagDetailsVOsPage);
            mailbagDetailsModelsList = mailOperationsMapper.mailbagVOsToMailbagModel(containerDetailsVOsList);
            return new Page<>(mailbagDetailsModelsList,
                    mailbagDetailsVOsPage.getPageNumber(), mailbagDetailsVOsPage.getDefaultPageSize(), mailbagDetailsVOsPage.getActualPageSize(),
                    mailbagDetailsVOsPage.getStartIndex(), mailbagDetailsVOsPage.getEndIndex(), mailbagDetailsVOsPage.getAbsoluteIndex(),
                    mailbagDetailsVOsPage.hasNextPage(), mailbagDetailsVOsPage.getTotalRecordCount());
        } catch (PersistenceException e) {
            throw new MailOperationsBusinessException(e.getMessage());
        }
    }
}
