package com.ibsplc.neoicargo.mail.component.feature.savearrivaldetails.enrichers;

import com.ibsplc.icargo.business.shared.area.airport.vo.AirportVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.vo.ContainerDetailsVO;
import com.ibsplc.neoicargo.mail.vo.DespatchDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailArrivalVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagHistoryVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Slf4j
@Deprecated
@Component("generateConsignmentDocumentNoForArrivalEnricher")
public class GenerateConsignmentDocumentNoForArrivalEnricher extends Enricher<MailArrivalVO> {
    @Autowired
    private LocalDate localDateUtil;

    @Override
    public void enrich(MailArrivalVO mailArrivalVO) {
        log.debug(getClass().getSimpleName() + " : " + "enrich" + " Entering");
        Collection<ContainerDetailsVO> contDetVOs = mailArrivalVO.getContainerDetails();
        if (contDetVOs != null && !contDetVOs.isEmpty()) {
            for (ContainerDetailsVO containerDetailsVO : contDetVOs) {
                Collection<DespatchDetailsVO> despatchDetailsVOs = containerDetailsVO.getDesptachDetailsVOs();
                if (despatchDetailsVOs != null && !despatchDetailsVOs.isEmpty()) {
                    for (DespatchDetailsVO despatchDetailsVO : despatchDetailsVOs) {
                        if (despatchDetailsVO.getConsignmentNumber() == null
                                || despatchDetailsVO.getConsignmentNumber().isEmpty()) {
                            //TODO: Master service to be corrected in Neo
//							AirportVO airportVO = sharedAreaProxy.findAirportDetails(mailArrivalVO.getCompanyCode(),
//									mailArrivalVO.getAirportCode());
                            AirportVO airportVO = null;
                            log.debug("" + "AIRPORT VO" + " " + airportVO);
                            String id = new StringBuilder().append(airportVO.getCountryCode())
                                    .append(airportVO.getCityCode()).toString();
                            //TODO: key generation to be correted
                            String key = "";//KeyUtils.getKey();
                            String str = "";
                            int count = 0;
                            for (int i = 0; i < (7 - key.length()); i++) {
                                if (count == 0) {
                                    str = "0";
                                    count = 1;
                                } else {
                                    str = new StringBuilder().append(str).append("0").toString();
                                }
                            }
                            log.debug("" + "222222222%%%%%%%%%%str" + " " + str);
                            String conDocNo = new StringBuilder().append(id).append("S").append(str).append(key)
                                    .toString();
                            log.debug("" + "conDocNo" + " " + conDocNo);
                            if (despatchDetailsVO.getConsignmentDate() == null) {
                                if (airportVO.getAirportCode() != null) {
                                    despatchDetailsVO
                                            .setConsignmentDate(localDateUtil.getLocalDate(
                                                    airportVO.getAirportCode(), false));
                                } else {
                                    despatchDetailsVO
                                            .setConsignmentDate(localDateUtil.getLocalDate(
                                                    null, false));
                                }
                            }
                            despatchDetailsVO.setConsignmentNumber(conDocNo);
                            log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentNumber-->" + " "
                                    + despatchDetailsVO.getConsignmentNumber());
                            log.debug("" + "%%%%%despatchDetailsVO%%%%-ConsignmentDate-->" + " "
                                    + despatchDetailsVO.getConsignmentDate());
                            log.debug(
                                    "" + "%%%%%despatchDetailsVO%%%%-PaCode-->" + " " + despatchDetailsVO.getPaCode());
                        }
                    }
                }
            }
        }
        log.debug(getClass().getSimpleName() + " : " + "enrich" + " Exiting");
    }
}
