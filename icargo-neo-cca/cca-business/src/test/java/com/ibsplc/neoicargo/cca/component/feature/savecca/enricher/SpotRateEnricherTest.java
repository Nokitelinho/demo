package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.neoicargo.cca.mapper.CcaRateDetailsMapper;
import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.spring.LoginProfileExtractor;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.util.config.ConfigProviderImpl;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.currency.vo.MoneyVO;
import com.ibsplc.neoicargo.pricing.PricingWebAPI;
import com.ibsplc.neoicargo.pricing.modal.CalculateSpotRateResponse;
import com.ibsplc.neoicargo.pricing.modal.SpotRateRequest;
import com.ibsplc.neoicargo.pricing.modal.SpotRateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.CCA_REFERENCE_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCCAMasterVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getUnitOfMeasure;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
public class SpotRateEnricherTest {

    @Mock
    private PricingWebAPI pricingWebAPI;

    @Spy
    private CcaRateDetailsMapper ccaRateDetailsMapper = Mappers.getMapper(CcaRateDetailsMapper.class);

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ObjectProvider<LoginProfileExtractor> loginProfileExtractor;

    @Mock
    private ConfigProviderImpl configProvider;

    @InjectMocks
    private SpotRateEnricher spotRateEnricher;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        var contextUtil = new ContextUtil(applicationContext, loginProfileExtractor);
        contextUtil.setContext(new RequestContext());
        when(contextUtil.getBean(ConfigProviderImpl.class)).thenReturn(configProvider);

        var moneyMapper = new MoneyMapper();
        ReflectionTestUtils.setField(moneyMapper, "contextUtil", contextUtil);

        ReflectionTestUtils.setField(ccaRateDetailsMapper, "moneyMapper", moneyMapper);
    }

    @Test
    void shouldEnrichSpotRate() throws BusinessException {
        // Given
        var ccaAwbVO = getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
        ccaAwbVO.setUnitOfMeasure(getUnitOfMeasure("K", "B", "C", "USD"));
        var ccaMasterVO = getCCAMasterVO(MASTER_DOCUMENT_NUMBER, CCA_REFERENCE_NUMBER, LocalDate.now());
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);

        var validateSpotRateResp = new SpotRateResponse();
        ReflectionTestUtils.setField(validateSpotRateResp, "isValidated", true);

        var calculateSpotRateResp = new CalculateSpotRateResponse();
        calculateSpotRateResp.setSpotRateId("spotRateId");
        calculateSpotRateResp.setSpotRateStatus("APR");
        calculateSpotRateResp.setCharge(10.0);

        var moneyVO = new MoneyVO();
        moneyVO.setCurrencyCode("USD");

        // When
        doReturn(moneyVO).when(configProvider).getMoney("USD");
        doReturn(validateSpotRateResp).when(pricingWebAPI)
                .validateSpotRate(any(SpotRateRequest.class));
        doReturn(calculateSpotRateResp).when(pricingWebAPI)
                .calculateSpotRate(any(SpotRateRequest.class));

        // Then
        assertDoesNotThrow(() -> spotRateEnricher.enrich(ccaMasterVO));
    }

}
