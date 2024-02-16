package com.ibsplc.neoicargo.tracking.component.feature.emailnotification.enricher;

import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import com.ibsplc.neoicargo.tracking.vo.EmailMilestoneNotificationVO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AirlineNameEnricherTest {

    @Mock
    private AirlineWebComponent airlineComponent;

    @InjectMocks
    AirlineNameEnricher enricher;


    @Test
    void shouldEnrich() throws BusinessException {
        var emailVO = new EmailMilestoneNotificationVO();
        emailVO.setShipmentKey("123-45678910");
        var airlineModel = new AirlineModel();
        airlineModel.setAirlineName("testName");
        when(airlineComponent.validateNumericCode("123")).thenReturn(airlineModel);

        enricher.enrich(emailVO);

        assertEquals(airlineModel.getAirlineName(), emailVO.getAirline());
    }

    @Test
    void shouldPropagateBusinessException() throws BusinessException {
        var emailVO = new EmailMilestoneNotificationVO();
        emailVO.setShipmentKey("123-45678910");


        when(airlineComponent.validateNumericCode("123")).thenThrow(new BusinessException("", ""));

        assertThrows(BusinessException.class, () -> enricher.enrich(emailVO));

    }
}