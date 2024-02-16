package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.enrichers;

import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.feature.savemailbaghistory.SaveMailbagHistoryFeatureConstants;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Component("updateMailbagsEnricher")
public class UpdateMailbagsEnricher extends Enricher<MailArrivalVO> {

    @Override
    public void enrich(MailArrivalVO mailArrivalVO) {
        log.debug(getClass().getSimpleName() + " : " + "enrich" + " Entering");

        Collection<MailbagVO> mailVOs = new ArrayList<MailbagVO>();
        Collection<ContainerDetailsVO> containerDetailsVOs = mailArrivalVO.getContainerDetails();
        if (containerDetailsVOs != null && !containerDetailsVOs.isEmpty()) {
            for (ContainerDetailsVO contVO : containerDetailsVOs) {
                if (contVO.getMailDetails() != null && !contVO.getMailDetails().isEmpty()) {
                    Collection<MailbagVO> mailbagVOs = contVO.getMailDetails();
                    if (!mailArrivalVO.isFlightChange()) {
                        for (MailbagVO mailbagVO : mailbagVOs) {
                            if (mailbagVO.getMailSequenceNumber() > 0 && mailbagVO.getOperationalFlag() != null) {
                                mailVOs.add(mailbagVO);
                            }
                        }
                    }
                }
            }
            updateMailbagVOs(mailVOs);
            mailArrivalVO.setMailVOUpdated(true);
        }
        log.debug(getClass().getSimpleName() + " : " + "enrich" + " Exiting");
    }

    private static void updateMailbagVOs(Collection<MailbagVO> mailbagVOs) {
        long[] seqNums = new long[mailbagVOs.size()];
        final Map<Long, Collection<MailbagHistoryVO>> historyMap;
        final Map<Long, MailInConsignmentVO> consignmentsMap;
        if (!mailbagVOs.isEmpty()) {
            String companyCode = mailbagVOs.iterator().next().getCompanyCode();
            int i = 0;
            for (MailbagVO mailVO : mailbagVOs) {
                seqNums[i++] = mailVO.getMailSequenceNumber();
            }
            historyMap = Mailbag.findMailbagHistoriesMap(companyCode, seqNums);
            consignmentsMap = Mailbag.findAllConsignmentDetailsForMailbag(companyCode, seqNums);
            for (MailbagVO mailVO : mailbagVOs) {
                if (historyMap != null && !historyMap.isEmpty()) {
                    if (historyMap.containsKey(mailVO.getMailSequenceNumber())) {
                        mailVO.setMailbagHistories(historyMap.get(mailVO.getMailSequenceNumber()));
                    }
                }
                if (consignmentsMap != null && !consignmentsMap.isEmpty()) {
                    if (consignmentsMap.containsKey(mailVO.getMailSequenceNumber())) {
                        mailVO.setMailConsignmentVO(consignmentsMap.get(mailVO.getMailSequenceNumber()));
                    }
                }
            }
        }
    }
}
