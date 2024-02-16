package com.ibsplc.neoicargo.mail.component.feature.findarrivedcontainersforinbound;

import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalFilterVO;
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
@Component("mail.operations.findArrivedContainersForInboundFeature")
@RequiredArgsConstructor
public class FindArrivedContainersForInboundFeature {
    private final MailController mailController;
    private final MailOperationsMapper mailOperationsMapper;

    public Page<ContainerDetailsModel> perform(MailArrivalFilterVO mailArrivalFilterVO)
            throws MailOperationsBusinessException {
        log.info("Invoke FindArrivedContainersForInboundFeature");
        try {
            Page<ContainerDetailsVO> containerDetailsVOsPage = mailController.findArrivedContainersForInbound(mailArrivalFilterVO);
            List<ContainerDetailsModel> containerDetailsModelsList;
            if (Objects.isNull(containerDetailsVOsPage)) {
                containerDetailsModelsList = new ArrayList<>(Collections.emptyList());
                return new Page<>(containerDetailsModelsList, 1, 100, 0, 0, 0, 0,
                        false, 0);
            }
            List<ContainerDetailsVO> containerDetailsVOsList = new ArrayList<>(containerDetailsVOsPage);
            containerDetailsModelsList = mailOperationsMapper.containerDetailsVOsToContainerDetailsModels(containerDetailsVOsList);
            return new Page<>(containerDetailsModelsList,
                    containerDetailsVOsPage.getPageNumber(), containerDetailsVOsPage.getDefaultPageSize(), containerDetailsVOsPage.getActualPageSize(),
                    containerDetailsVOsPage.getStartIndex(), containerDetailsVOsPage.getEndIndex(), containerDetailsVOsPage.getAbsoluteIndex(),
                    containerDetailsVOsPage.hasNextPage(), containerDetailsVOsPage.getTotalRecordCount());
        } catch (PersistenceException e) {
            throw new MailOperationsBusinessException(e.getMessage());
        }
    }
}
