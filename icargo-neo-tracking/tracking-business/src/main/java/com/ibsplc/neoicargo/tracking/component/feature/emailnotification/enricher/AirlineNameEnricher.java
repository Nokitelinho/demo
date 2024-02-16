package com.ibsplc.neoicargo.tracking.component.feature.emailnotification.enricher;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.tracking.vo.EmailMilestoneNotificationVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component("airlineEnricher")
@RequiredArgsConstructor
public class AirlineNameEnricher extends Enricher<EmailMilestoneNotificationVO> {

    private final AirlineWebComponent airlineComponent;

    @Override
    public void enrich(EmailMilestoneNotificationVO emailVO) throws BusinessException {
        log.info("Enriching email VO with airline name for AWB: {}", emailVO.getShipmentKey());
        var airlineModel = airlineComponent.validateNumericCode(emailVO.getShipmentKey().split("-")[0]);
        emailVO.setAirline(airlineModel.getAirlineName());
    }
}
