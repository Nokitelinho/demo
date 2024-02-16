package com.ibsplc.neoicargo.cca.component.feature.maintainccalist;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaAwbMapper;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaDataFilter;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.awb.AirportUtil;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.MockModelsGeneratorUtils.getCcaListMasterVO;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetCCAListFeatureTest {

    private static final String DEFAULT_DATE_PATTERN = "dd-MM-yyyy";

    @Mock
    private CcaDao ccaDao;

    @Spy
    private final CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @Mock
    private AirportUtil airportUtil;

    @InjectMocks
    private GetCCAListFeature getCCAListFeature;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(ccaMasterMapper, "moneyMapper", new MoneyMapper());
        ReflectionTestUtils.setField(ccaMasterMapper, "ccaAwbMapper", Mappers.getMapper(CcaAwbMapper.class));
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("ccaMasterVOValues")
    void shouldGetCCAList(Set<GetCcaListMasterVO> ccaMasterVOs) {
        // When
        doReturn(Map.of("DXB", "UA", "PAR", "FR")).when(airportUtil).validateAirportCodes(any(Set.class));
        doReturn(ccaMasterVOs).when(ccaDao).getCCAList(anyList());

        // Then
        assertNotNull(getCCAListFeature.perform(new CcaDataFilter()));
    }

    @Test
    void shouldReturnProperDataFormat() {
        // Given
        final var ccaMasterVO = getCcaListMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO.setShipmentDetailVOs(List.of(getCcaAwbVO(CCA_RECORD_TYPE_REVISED), getCcaAwbVO(CCA_RECORD_TYPE_ORIGINAL)));

        // Then
        doReturn(Map.of("DXB", "UA", "PAR", "FR")).when(airportUtil).validateAirportCodes(any(Set.class));
        doReturn(Set.of(ccaMasterVO)).when(ccaDao).getCCAList(anyList());

        // Then
        final var ccaMasterDataList = getCCAListFeature.perform(new CcaDataFilter());
        assertNotNull(ccaMasterDataList);
        assertTrue(ccaMasterDataList.size() > 0);
        ccaMasterDataList.forEach(masterData -> assertTrue(isValidDateForDefaultPatter(masterData.getCcaIssueDate())));

    }

    private static Stream<Set<GetCcaListMasterVO>> ccaMasterVOValues() {
        // Given
        final var revisedCcaAwbVO = getCcaAwbVO(CCA_RECORD_TYPE_REVISED);
        final var originalCcaAwbVO = getCcaAwbVO(CCA_RECORD_TYPE_ORIGINAL);

        final var ccaMasterVO1 = getCcaListMasterVO("44440000", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO1.setShipmentDetailVOs(List.of(revisedCcaAwbVO, originalCcaAwbVO));
        ccaMasterVO1.setCassIndicator("B");
        final var ccaMasterVO2 = getCcaListMasterVO("44440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO2.setShipmentDetailVOs(List.of(revisedCcaAwbVO, originalCcaAwbVO));

        final var ccaMasterVO3 = getCcaListMasterVO("44440030", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO3.setCassIndicator("I");
        ccaMasterVO3.setShipmentDetailVOs(List.of(revisedCcaAwbVO, originalCcaAwbVO));

        final var ccaMasterVO4 = getCcaListMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO4.setCassIndicator("D");
        ccaMasterVO4.setShipmentDetailVOs(List.of(revisedCcaAwbVO, originalCcaAwbVO));

        final var ccaMasterVO5 = getCcaListMasterVO("47440001", "CCAA000001",
                LocalDate.of(2020, 12, 3), CcaStatus.N);
        ccaMasterVO5.setCassIndicator(null);
        ccaMasterVO5.setShipmentDetailVOs(List.of(revisedCcaAwbVO, originalCcaAwbVO));

        return Stream.of(Set.of(ccaMasterVO1), Set.of(ccaMasterVO2), Set.of(ccaMasterVO5), Set.of(ccaMasterVO3, ccaMasterVO4));
    }

    private static CcaAwbVO getCcaAwbVO(String ccaRecordTypeRevised) {
        final var revisedCcaAwbVO = new CcaAwbVO();
        revisedCcaAwbVO.setRecordType(ccaRecordTypeRevised);
        revisedCcaAwbVO.setDestination("DXB");
        revisedCcaAwbVO.setOrigin("CDG");
        return revisedCcaAwbVO;
    }

    private boolean isValidDateForDefaultPatter(String dateStr) {
        var dateFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_PATTERN, Locale.US)
                .withResolverStyle(ResolverStyle.STRICT);
        try {
            dateFormatter.parse(dateStr);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
