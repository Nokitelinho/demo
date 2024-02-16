package com.ibsplc.neoicargo.cca.component.feature.savecca.enricher;

import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditData;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.framework.core.context.RequestContext;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.core.security.spring.LoginProfileExtractor;
import com.ibsplc.neoicargo.framework.core.services.icargo.ServiceProxy;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.FeatureTestSupport;
import com.ibsplc.neoicargo.framework.util.config.ConfigProviderImpl;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.currency.vo.MoneyVO;
import com.ibsplc.neoicargo.masters.airline.AirlineWebComponent;
import com.ibsplc.neoicargo.masters.airline.modal.AirlineModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpMethod;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(JUnitPlatform.class)
class CcaNetValueEnricherTest {

    @Mock
    private AirlineWebComponent airlineComponent;

    @Mock
    private ServiceProxy<QualityAuditedDetails> serviceProxy;

    @Mock
    private CcaMasterMapper ccaMasterMapper;

    @Mock
    private ConfigProviderImpl configProvider;

    @Mock
    private ApplicationContext applicationContext;

    @Mock
    private ObjectProvider<LoginProfileExtractor> loginProfileExtractor;

    private CCAMasterVO ccaMasterVO;

    @InjectMocks
    private CCANetValueEnricher ccaNetValueEnricher;

    @BeforeEach
    public void setup() {
        ccaMasterVO = new CCAMasterVO();
        MockitoAnnotations.openMocks(this);

        // Given
        ReflectionTestUtils.setField(ccaNetValueEnricher, "eblUrl", "http://localhost:9591/ebl-nbridge/v1/");
        var contextUtil = new ContextUtil(applicationContext, loginProfileExtractor);
        contextUtil.setContext(new RequestContext());

        var moneyMapper = new MoneyMapper();
        ReflectionTestUtils.setField(moneyMapper, "contextUtil", contextUtil);
        ReflectionTestUtils.setField(ccaNetValueEnricher, "moneyMapper", moneyMapper);

        //When
        when(contextUtil.getBean(ConfigProviderImpl.class)).thenReturn(configProvider);
        FeatureTestSupport.mockFeatureContext();
    }

    @Test
    void shouldEnrichNetValuesToCcaMasterData() throws BusinessException {
        //Given
        var moneyVO = new MoneyVO();
        moneyVO.setCurrencyCode("USD");

        buildCcaMasterVO();

        var qualityAuditData = new QualityAuditData();
        qualityAuditData.setExportDueAirline(123.0);
        qualityAuditData.setImportDueAirline(456.0);

        var airlineModel = new AirlineModel();
        airlineModel.setAirlineIdentifier(1134);

        // When
        doReturn(new QualityAuditedDetails()).when(ccaMasterMapper).constructQualityAuditedDetailsFromCcaAwbVO(any(), any());
        doNothing().when(ccaMasterMapper).constructTotalValues(any(), any());
        doReturn(airlineModel).when(airlineComponent).validateNumericCode(any());

        doReturn(qualityAuditData).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));
        doReturn(moneyVO).when(configProvider).getMoney("USD");

        ccaNetValueEnricher.enrich(ccaMasterVO);

        //Then
        assertEquals(123.0, ccaMasterVO.getRevisedShipmentVO().getNetValueExport().getAmount().doubleValue());
        assertEquals(456.0, ccaMasterVO.getRevisedShipmentVO().getNetValueImport().getAmount().doubleValue());
    }

    @Test
    void shouldNotEnrich() throws BusinessException {
        //When
        ccaNetValueEnricher.enrich(ccaMasterVO);

        //Then
        verify(serviceProxy, never()).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));
    }

    @Test
    void shouldHandleException() {
        //Given
        buildCcaMasterVO();

        //When
        doThrow(RuntimeException.class).when(serviceProxy).dispatch(anyString(),
                eq(HttpMethod.POST), any(QualityAuditedDetails.class), any(Class.class));
        //Then
        assertThrows(Exception.class, () -> ccaNetValueEnricher.enrich(ccaMasterVO));
    }

    private void buildCcaMasterVO() {
        ccaMasterVO.setRevisedShipmentVO(new CcaAwbVO());
        Units units = new Units();
        units.setCurrencyCode("USD");
        ccaMasterVO.setUnitOfMeasure(units);
    }
}
