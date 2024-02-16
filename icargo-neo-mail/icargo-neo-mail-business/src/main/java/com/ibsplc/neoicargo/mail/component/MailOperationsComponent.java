package com.ibsplc.neoicargo.mail.component;

import com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails.EditScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.findarrivedcontainersforinbound.FindArrivedContainersForInboundFeature;
import com.ibsplc.neoicargo.mail.component.feature.findarrivedmailbagsforinbound.FindArrivedMailbagsForInboundFeature;
import com.ibsplc.neoicargo.mail.component.feature.findconsignmentscreeningdetails.FindConsignmentScreeningDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.listflightdetails.ListFlightDetailsFeature;
import com.ibsplc.neoicargo.mail.component.feature.saveconsignmentdetailsmaster.SaveConsignmentDetailsMasterFeature;
import com.ibsplc.neoicargo.mail.component.feature.savesecuritydetails.SaveSecurityDetailsFeature;
import com.ibsplc.neoicargo.mail.exception.MailOperationsBusinessException;
import com.ibsplc.neoicargo.mail.mapper.MailOperationsMapper;
import com.ibsplc.neoicargo.mail.model.ConsignmentDocumentModel;
import com.ibsplc.neoicargo.mail.model.ContainerDetailsModel;
import com.ibsplc.neoicargo.mail.model.MailArrivalModel;
import com.ibsplc.neoicargo.mail.model.MailbagModel;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalFilterVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component("mailOperationsComponent")
@RequiredArgsConstructor
public class MailOperationsComponent {
    private final MailOperationsMapper mailOperationsMapper;
    private final FindConsignmentScreeningDetailsFeature findConsignmentScreeningDetailsFeature;
    private final ListFlightDetailsFeature listFlightDetailsFeature;
    private final FindArrivedContainersForInboundFeature findArrivedContainersForInboundFeature;
    private final FindArrivedMailbagsForInboundFeature findArrivedMailbagsForInboundFeature;
    private final EditScreeningDetailsFeature editScreeningDetailsFeature;
    private final SaveConsignmentDetailsMasterFeature saveConsignmentDetailsMasterFeature;
    private final SaveSecurityDetailsFeature saveSecurityDetailsFeature;

    public ConsignmentDocumentModel findConsignmentScreeningDetails(
            String consignmentNumber, String companyCode, String poaCode
    ) throws MailOperationsBusinessException {
        var consignmentDocumentVO = findConsignmentScreeningDetailsFeature.perform(consignmentNumber, companyCode, poaCode);

        return mailOperationsMapper.consignmentDocumentVOToConsignmentDocumentModel(consignmentDocumentVO);
    }

    public Page<MailArrivalModel> listFlightDetails(MailArrivalVO mailArrivalVO) throws MailOperationsBusinessException {
        return listFlightDetailsFeature.perform(mailArrivalVO);
    }

    public Page<ContainerDetailsModel> findArrivedContainersForInbound(MailArrivalFilterVO mailArrivalFilterVO)
            throws MailOperationsBusinessException {
        return findArrivedContainersForInboundFeature.perform(mailArrivalFilterVO);
    }

    public Page<MailbagModel> findArrivedMailbagsForInbound(MailArrivalFilterVO mailArrivalFilterVO)
            throws MailOperationsBusinessException {
        return findArrivedMailbagsForInboundFeature.perform(mailArrivalFilterVO);
    }

    public void editScreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
        editScreeningDetailsFeature.perform(consignmentScreeningVOs);
    }

    public void saveConsignmentDetailsMaster(ConsignmentDocumentVO consignmentDocumentVO) {
        saveConsignmentDetailsMasterFeature.perform(consignmentDocumentVO);
    }

    public void saveSecurityDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVOs) {
        saveSecurityDetailsFeature.executeBatch((List<ConsignmentScreeningVO>) consignmentScreeningVOs, false);
    }
}
