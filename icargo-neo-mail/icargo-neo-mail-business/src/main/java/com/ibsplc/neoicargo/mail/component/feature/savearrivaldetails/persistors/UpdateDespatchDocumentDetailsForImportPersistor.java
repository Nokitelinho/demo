package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.persistors;

import com.ibsplc.neoicargo.mail.component.DocumentController;
import com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.SaveArrivalDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.exception.DuplicateMailBagsException;
import com.ibsplc.neoicargo.mail.exception.MailbagAlreadyAcceptedException;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO.OPERATION_FLAG_INSERT;

@Slf4j
@Component("updateDespatchDocumentDetailsForImportPersistor")
public class UpdateDespatchDocumentDetailsForImportPersistor {

    public void persist(MailArrivalVO mailArrivalVO) throws DuplicateMailBagsException {
        log.debug(getClass().getSimpleName() + " : " + "persist" + " Entering");
        Collection<ContainerDetailsVO> containerDetails = compareAndCalculateTotalsOfDespatches(
                mailArrivalVO.getContainerDetails());
        Map<String, Collection<DespatchDetailsVO>> despatchMap = groupDespatchesForConsignment(containerDetails);
        log.debug("" + "despatch map -->" + " " + despatchMap);
        try {
            for (Map.Entry<String, Collection<DespatchDetailsVO>> despatch : despatchMap.entrySet()) {
                Collection<DespatchDetailsVO> despatches = despatch.getValue();
                ConsignmentDocumentVO consignDocVO = constructConsignmentDocVO(despatch.getKey(), despatches,
                        mailArrivalVO.getAirportCode());
                consignDocVO.setScanned(mailArrivalVO.isScanned());
                int consignmentSeqNum = new DocumentController().saveConsignmentForAcceptance(consignDocVO);
                updateDespatchesSequenceNum(consignmentSeqNum, despatches);
            }
        } catch (MailbagAlreadyAcceptedException mailbagAlreadyAcceptedException) {
        }
        log.debug(getClass().getSimpleName() + " : " + "persist" + " Exiting");
    }

    private Collection<ContainerDetailsVO> compareAndCalculateTotalsOfDespatches(
            Collection<ContainerDetailsVO> containers) {
        log.debug(SaveArrivalDetailsFeatureConstants.CLASS + " : " + "compareAndCalculateTotalsOfDespatches" + " Entering");
        DespatchDetailsVO despvo1 = null;
        DespatchDetailsVO despvo2 = null;
        Collection<DespatchDetailsVO> despatchVOs = null;
        List<DespatchDetailsVO> innerDespatchVOList = null;
        List<DespatchDetailsVO> outerDespatchVOList = null;
        if (containers != null && containers.size() > 0) {
            List<ContainerDetailsVO> containerVOList = (List<ContainerDetailsVO>) containers;
            int containerVOSize = containerVOList.size();
            for (int i = 0; i < containerVOSize; i++) {
                despatchVOs = containerVOList.get(i).getDesptachDetailsVOs();
                if (despatchVOs != null && despatchVOs.size() > 1) {
                    innerDespatchVOList = (List<DespatchDetailsVO>) despatchVOs;
                    int innerDespatchVOSize = innerDespatchVOList.size();
                    for (int j = 0; j < innerDespatchVOSize; j++) {
                        despvo1 = innerDespatchVOList.get(j);
                        for (int k = (j + 1); k < innerDespatchVOSize; k++) {
                            despvo2 = innerDespatchVOList.get(k);
                            if (compareDespatchDetailsVOs(despvo1, despvo2)) {
                                log.debug("Consignment no is same");
                                despvo1.setAcceptedBags(despvo1.getAcceptedBags() + despvo2.getAcceptedBags());
                                despvo1.setStatedBags(despvo1.getStatedBags() + despvo2.getStatedBags());
                                try {
                                    despvo1.setAcceptedWeight(
                                            despvo1.getAcceptedWeight().add(despvo2.getAcceptedWeight()));
                                } finally {
                                }
                                try {
                                    despvo1.setStatedWeight(despvo1.getStatedWeight().add(despvo2.getStatedWeight()));
                                } finally {
                                }
                                try {
                                    despvo1.setStatedVolume(despvo1.getStatedVolume().add(despvo2.getStatedVolume()));
                                } finally {
                                }
                                try {
                                    despvo1.setAcceptedVolume(
                                            despvo1.getAcceptedVolume().add(despvo2.getAcceptedVolume()));
                                } finally {
                                }
                                despvo1.setOperationalFlag(DespatchDetailsVO.OPERATION_FLAG_INSERT);
                                innerDespatchVOList.remove(despvo2);
                                j--;
                                innerDespatchVOSize = innerDespatchVOList.size();
                            }
                        }
                        for (int l = (i + 1); l < containerVOSize; l++) {
                            despatchVOs = containerVOList.get(l).getDesptachDetailsVOs();
                            if (despatchVOs != null && despatchVOs.size() > 1) {
                                outerDespatchVOList = (List<DespatchDetailsVO>) despatchVOs;
                                int outerDespatchVOSize = outerDespatchVOList.size();
                                for (int m = 0; m < outerDespatchVOSize; m++) {
                                    despvo2 = outerDespatchVOList.get(m);
                                    if (compareDespatchDetailsVOs(despvo1, despvo2)) {
                                        log.debug("Consignment no is same");
                                        despvo1.setAcceptedBags(despvo1.getAcceptedBags() + despvo2.getAcceptedBags());
                                        despvo1.setStatedBags(despvo1.getStatedBags() + despvo2.getStatedBags());
                                        try {
                                            despvo1.setAcceptedWeight(
                                                    despvo1.getAcceptedWeight().add(despvo2.getAcceptedWeight()));
                                        } finally {
                                        }
                                        try {
                                            despvo1.setStatedWeight(
                                                    despvo1.getStatedWeight().add(despvo2.getStatedWeight()));
                                        } finally {
                                        }
                                        try {
                                            despvo1.setStatedVolume(
                                                    despvo1.getStatedVolume().add(despvo2.getStatedVolume()));
                                        } finally {
                                        }
                                        try {
                                            despvo1.setAcceptedVolume(
                                                    despvo1.getAcceptedVolume().add(despvo2.getAcceptedVolume()));
                                        } finally {
                                        }
                                        despvo1.setOperationalFlag(DespatchDetailsVO.OPERATION_FLAG_INSERT);
                                        outerDespatchVOList.remove(despvo2);
                                        m--;
                                        outerDespatchVOSize = outerDespatchVOList.size();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return containers;
    }

    private Map<String, Collection<DespatchDetailsVO>> groupDespatchesForConsignment(
            Collection<ContainerDetailsVO> containerDetails) {
        Map<String, Collection<DespatchDetailsVO>> despatchMap = new HashMap<String, Collection<DespatchDetailsVO>>();
        if (containerDetails != null && containerDetails.size() > 0) {
            for (ContainerDetailsVO containerDetailsVO : containerDetails) {
                if (containerDetailsVO.getOperationFlag() != null) {
                    Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
                    if (despatchDetailsVOs != null && despatchDetailsVOs.size() > 0) {
                        for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
                            if (despatchDetailsVO.getOperationalFlag() != null
                                    && OPERATION_FLAG_INSERT.equals(despatchDetailsVO.getOperationalFlag())) {
                                String documentPK = constructDocumentPK(despatchDetailsVO);
                                Collection<DespatchDetailsVO> docDespatches = despatchMap.get(documentPK);
                                if (docDespatches == null) {
                                    docDespatches = new ArrayList<DespatchDetailsVO>();
                                    despatchMap.put(documentPK, docDespatches);
                                }
                                if (MailConstantsVO.FLAG_YES.equals(containerDetailsVO.getPaBuiltFlag())) {
                                    despatchDetailsVO.setUldNumber(containerDetailsVO.getContainerNumber());
                                } else {
                                    despatchDetailsVO.setUldNumber(null);
                                }
                                docDespatches.add(despatchDetailsVO);
                            }
                        }
                    }
                }
            }
        }
        return despatchMap;
    }

    private ConsignmentDocumentVO constructConsignmentDocVO(String docPK, Collection<DespatchDetailsVO> despatches,
                                                            String airportCode) {
        String[] tokens = docPK.split(SaveArrivalDetailsFeatureConstants.ID_SEP);
        int idx = 0;
        ConsignmentDocumentVO docVO = new ConsignmentDocumentVO();
        docVO.setCompanyCode(tokens[idx++]);
        docVO.setConsignmentNumber(tokens[idx++]);
        docVO.setPaCode(tokens[idx++]);
        docVO.setAirportCode(airportCode);
        constructMailConsignsFromDespatch(despatches, docVO);
        docVO.setOperation(MailConstantsVO.OPERATION_OUTBOUND);
        return docVO;
    }

    private void constructMailConsignsFromDespatch(Collection<DespatchDetailsVO> despatches,
                                                   ConsignmentDocumentVO docVO) {
        Page<MailInConsignmentVO> mailInConsigns = new Page<MailInConsignmentVO>(new ArrayList<MailInConsignmentVO>(),
                0, 0, 0, 0, 0, false);
        int statedBagDlt = 0;
        double statedWtDlt = 0;
        for (DespatchDetailsVO despatchDetailsVO : despatches) {
            MailInConsignmentVO mailInConsign = new MailInConsignmentVO();
            mailInConsign.setCompanyCode(despatchDetailsVO.getCompanyCode());
            mailInConsign.setConsignmentNumber(despatchDetailsVO.getConsignmentNumber());
            mailInConsign.setConsignmentSequenceNumber(despatchDetailsVO.getConsignmentSequenceNumber());
            mailInConsign.setPaCode(despatchDetailsVO.getPaCode());
            mailInConsign.setDsn(despatchDetailsVO.getDsn());
            mailInConsign.setOriginExchangeOffice(despatchDetailsVO.getOriginOfficeOfExchange());
            mailInConsign.setDestinationExchangeOffice(despatchDetailsVO.getDestinationOfficeOfExchange());
            mailInConsign.setMailClass(despatchDetailsVO.getMailClass());
            mailInConsign.setMailSubclass(despatchDetailsVO.getMailSubclass());
            mailInConsign.setMailCategoryCode(despatchDetailsVO.getMailCategoryCode());
            mailInConsign.setYear(despatchDetailsVO.getYear());
            mailInConsign.setStatedBags(despatchDetailsVO.getStatedBags());
            mailInConsign.setStatedWeight(despatchDetailsVO.getStatedWeight());
            if (despatchDetailsVO.getStatedBags() > 0) {
                mailInConsign.setStatedBags(despatchDetailsVO.getStatedBags());
                mailInConsign.setStatedWeight(despatchDetailsVO.getStatedWeight());
            } else {
                mailInConsign.setStatedBags(despatchDetailsVO.getReceivedBags());
                mailInConsign.setStatedWeight(despatchDetailsVO.getReceivedWeight());
            }
            mailInConsigns.add(mailInConsign);
            statedBagDlt += (despatchDetailsVO.getStatedBags() - despatchDetailsVO.getPrevStatedBags());
            //TODO: Neo to verify below code
//			statedWtDlt += (despatchDetailsVO.getStatedWeight().getRoundedSystemValue()
//					- despatchDetailsVO.getPrevStatedWeight().getRoundedSystemValue());
            statedWtDlt += (despatchDetailsVO.getStatedWeight().getRoundedValue().subtract(despatchDetailsVO.getPrevStatedWeight().getRoundedValue())).doubleValue();
            mailInConsign.setUldNumber(despatchDetailsVO.getUldNumber());
        }
        docVO.setMailInConsignmentVOs(mailInConsigns);
    }

    private void updateDespatchesSequenceNum(int consignmentSeqNum, Collection<DespatchDetailsVO> despatches) {
        for (DespatchDetailsVO despatchDetailsVO : despatches) {
            despatchDetailsVO.setConsignmentSequenceNumber(consignmentSeqNum);
        }
    }

    private boolean compareDespatchDetailsVOs(DespatchDetailsVO despvo1, DespatchDetailsVO despvo2) {
        return despvo1.getConsignmentNumber().equalsIgnoreCase(despvo2.getConsignmentNumber())
                && despvo1.getOriginOfficeOfExchange().equals(despvo2.getOriginOfficeOfExchange())
                && despvo1.getDestinationOfficeOfExchange().equals(despvo2.getDestinationOfficeOfExchange())
                && despvo1.getMailCategoryCode().equals(despvo2.getMailCategoryCode())
                && despvo1.getMailSubclass().equals(despvo2.getMailSubclass())
                && despvo1.getYear() == (despvo2.getYear()) && despvo1.getDsn().equals(despvo2.getDsn());
    }

    private String constructDocumentPK(DespatchDetailsVO despatchDetailsVO) {
        //TODO: Below code to be corrected in Neo- refer classic
        return new StringBuilder().append(despatchDetailsVO.getCompanyCode()).append(SaveArrivalDetailsFeatureConstants.ID_SEP)
                .append(despatchDetailsVO.getConsignmentNumber()).append(SaveArrivalDetailsFeatureConstants.ID_SEP).append(despatchDetailsVO.getPaCode())
                .append(SaveArrivalDetailsFeatureConstants.ID_SEP).append(despatchDetailsVO.getConsignmentDate()).toString();
    }
}
