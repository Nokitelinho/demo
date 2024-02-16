package com.ibsplc.neoicargo.mail.component.feature.editscreeningdetails.persistor;

import com.ibsplc.neoicargo.mail.component.ConsignmentScreeningDetails;
import com.ibsplc.neoicargo.mail.component.ConsignmentScreeningDetailsPK;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

@Slf4j
@Component("mail.operations.editScreeningDetailsPersistor")
@RequiredArgsConstructor
public class EditScreeningDetailsPersistor {

    public void persist(ConsignmentScreeningVO consignmentScreeningVO) {
        long sernum = new ConsignmentScreeningDetails().findLatestRegAgentIssuing(consignmentScreeningVO);
        Collection<ConsignmentScreeningVO> consignmentScreeningVos = null;
        consignmentScreeningVos = new ConsignmentScreeningDetails().findScreeningMethodsForStampingRegAgentIssueMapping(consignmentScreeningVO);
        if (consignmentScreeningVos != null) {
            consignmentScreeningVos.forEach(consignmentScreening -> consignmentScreening.setAgentSerialNumber(sernum));
            editscreeningDetails(consignmentScreeningVos);
        }
    }
    public void editscreeningDetails(Collection<ConsignmentScreeningVO> consignmentScreeningVos){
        log.debug("Invoke EditScreeningDetailsPersistor");
        for (ConsignmentScreeningVO consignmentScreeningVO : consignmentScreeningVos) {
            try {
                var pk = new ConsignmentScreeningDetailsPK();
                pk.setCompanyCode(consignmentScreeningVO.getCompanyCode());
                pk.setSerialNumber(consignmentScreeningVO.getSerialNumber());
                var screening = ConsignmentScreeningDetails
                        .find(consignmentScreeningVO.getCompanyCode(), consignmentScreeningVO.getSerialNumber());
                screening.setRemarks(consignmentScreeningVO.getRemarks());
                screening.setScreeningRegulation(consignmentScreeningVO.getScreeningRegulation());
                screening.setStatedBags(consignmentScreeningVO.getStatedBags());
                screening.setStatedWeight(populateStatedWeight(consignmentScreeningVO));
                screening.setScreeningLocation(consignmentScreeningVO.getScreeningLocation());
                screening.setSecurityStatusParty(consignmentScreeningVO.getSecurityStatusParty());
                screening.setScreeningMethodCode(consignmentScreeningVO.getScreeningMethodCode());
                screening.setScreenLevelValue(consignmentScreeningVO.getScreenLevelValue());
                screening.setScreenDetailType(populateScreenDetailType(consignmentScreeningVO));
                screening.setResult(consignmentScreeningVO.getResult());
                screening.setSecurityStatusDate(consignmentScreeningVO.getSecurityStatusDate());
                screening.setAgentID(consignmentScreeningVO.getAgentID());
                screening.setAgentType(consignmentScreeningVO.getAgentType());
                screening.setCountryCode(consignmentScreeningVO.getIsoCountryCode());
                screening.setExpiryDate(consignmentScreeningVO.getExpiryDate());
                screening.setAdditionalSecurityInfo(consignmentScreeningVO.getAdditionalSecurityInfo());
                screening.setScreeningAuthority(consignmentScreeningVO.getScreeningAuthority());
                screening.setSeScreeningAuthority(consignmentScreeningVO.getSeScreeningAuthority());
                screening.setSeScreeningReasonCode(consignmentScreeningVO.getSeScreeningReasonCode());
                screening.setSeScreeningRegulation(consignmentScreeningVO.getSeScreeningRegulation());
                screening.setApplicableRegTransportDirection(
                        consignmentScreeningVO.getApplicableRegTransportDirection());
                screening.setApplicableRegBorderAgencyAuthority(
                        consignmentScreeningVO.getApplicableRegBorderAgencyAuthority());
                screening.setApplicableRegReferenceID(consignmentScreeningVO.getApplicableRegReferenceID());
                screening.setApplicableRegFlag(consignmentScreeningVO.getApplicableRegFlag());
                screening.setAgentSerialNumber(consignmentScreeningVO.getAgentSerialNumber());
            } catch (FinderException e) {
                log.debug("EditScreeningDetailsPersistor Exception :", e);
            }
        }
    }

    private String populateScreenDetailType(ConsignmentScreeningVO consignmentScreeningVO) {
        if (consignmentScreeningVO.getSecurityReasonCode() != null) {
            return consignmentScreeningVO.getSecurityReasonCode();
        } else {
            return consignmentScreeningVO.getScreenDetailType();
        }
    }

    private double populateStatedWeight(ConsignmentScreeningVO consignmentScreeningVO) {
        if (Objects.nonNull(consignmentScreeningVO.getStatedWeight())) {
            return consignmentScreeningVO.getStatedWeight().getValue().doubleValue();
        }
        return 0;
    }
}
