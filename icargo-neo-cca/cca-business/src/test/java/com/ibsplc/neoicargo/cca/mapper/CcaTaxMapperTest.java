package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.neoicargo.cca.constants.TaxFilterConstant;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaTaxDetailsVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.MASTER_DOCUMENT_NUMBER;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.SHIPMENT_PREFIX;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getFullMockCcaAwbVO;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getLoginProfile;
import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getRateClassJson;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class CcaTaxMapperTest {

    private static final LocalDateTime LOCAL_DATE_TIME = LocalDateTime.of(2000, 1, 15, 0, 0, 0);
    private static final LocalDate LOCAL_DATE = LocalDate.of(2000, 1, 15);
    private static final String LOCAL_DATE_ISO = "2000-01-15";

    @Mock
    private ContextUtil contextUtil;

    private final CcaTaxMapper ccaAwbMapper = Mappers.getMapper(CcaTaxMapper.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getDateOfJourneyShouldGetInIsoFormatFromExecutionDate() {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        ccaAwbVO.setExecutionDate(LOCAL_DATE);

        // When + Then
        var actual = ccaAwbMapper.getDateOfJourney(ccaAwbVO);
        assertEquals(LOCAL_DATE_ISO, actual);

    }

    @Test
    void getDateOfJourneyShouldGetInIsoFormatFromCaptureDateWhenExecutionDateNull() {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        ccaAwbVO.setExecutionDate(null);
        ccaAwbVO.setCaptureDate(LOCAL_DATE_TIME);

        // When + Then
        var actual = ccaAwbMapper.getDateOfJourney(ccaAwbVO);
        assertEquals(LOCAL_DATE_ISO, actual);

    }

    @Test
    void getDateOfJourneyShouldGetLocalDateNowInIsoFormatWhenCaptureDateExecutionDateNull() {
        // Given
        var ccaAwbVO = getCcaAwbVO();
        ccaAwbVO.setExecutionDate(null);
        ccaAwbVO.setCaptureDate(null);

        // When + Then
        var actual = ccaAwbMapper.getDateOfJourney(ccaAwbVO);
        assertEquals(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE), actual);

    }

    @Test
    void fromCcaAwbVOToTaxFilterVO() {
        // Given
        var ccaAwbVO = getCcaAwbVO();

        // When
        doReturn(getLoginProfile())
                .when(contextUtil).callerLoginProfile();

        // Then
        var actual = ccaAwbMapper.fromCcaAwbVOToTaxFilterVO(ccaAwbVO, contextUtil);
        assertNotNull(actual);
    }

    @Test
    void fromCcaAwbVOToTaxFilterVOShouldReturnNull() {
        // When
        doReturn(getLoginProfile())
                .when(contextUtil).callerLoginProfile();

        // Then
        var actual = ccaAwbMapper.fromCcaAwbVOToTaxFilterVO(null, contextUtil);
        assertNull(actual);
    }

    @Test
    void fromCcaAwbVOToCcaAwbTaxDetails() {
        // Given
        var taxDetailsVO = new CcaTaxDetailsVO();
        taxDetailsVO.setSerialNumber(100_000L);
        taxDetailsVO.setConfigurationType(TaxFilterConstant.CONFIGURATIONTYPE_TAX);
        taxDetailsVO.setTaxDetails(getRateClassJson());

        // When + Then
        var actual = ccaAwbMapper.fromCcaAwbVOToCcaAwbTaxDetails(taxDetailsVO);
        assertNotNull(actual);
    }

    @Test
    void fromCcaAwbVOToCcaAwbTaxDetailsShouldReturnNull() {
        // When + Then
        var actual = ccaAwbMapper.fromCcaAwbVOToCcaAwbTaxDetails(null);
        assertNull(actual);
    }

    private CcaAwbVO getCcaAwbVO() {
        return getFullMockCcaAwbVO(SHIPMENT_PREFIX, MASTER_DOCUMENT_NUMBER, CCA_RECORD_TYPE_REVISED);
    }
}