package com.ibsplc.neoicargo.cca.component.feature.getccalistview;

import com.ibsplc.neoicargo.cca.dao.CcaDao;
import com.ibsplc.neoicargo.cca.mapper.CcaMasterMapper;
import com.ibsplc.neoicargo.cca.modal.CcaListViewModal;
import com.ibsplc.neoicargo.cca.modal.viewfilter.DateRangeFilterData;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.masters.shared.SharedComponent;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class GetCCAListViewFeatureTest {

    @Mock
    private CcaDao ccaDao;

    @Mock
    private SharedComponent sharedComponent;

    @Spy
    private CcaMasterMapper ccaMasterMapper = Mappers.getMapper(CcaMasterMapper.class);

    @InjectMocks
    private GetCCAListViewFeature getCCAListViewFeature;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideCCAMasterAndCcaFilterVOAndExpectedResult")
    void shouldReturnCcaListViewNotNull(CCAListViewFilterVO ccaListViewFilterVO) {
        // Given
        final var pageable = PageRequest.of(0, 25);
        final var maxResult = pageable.getPageSize();
        final var firstResult = pageable.getPageNumber() * pageable.getPageSize();
        final var agentCodesByAgentGroups = Map.of(
                "ABC", List.of("A5555"),
                "AGGRP", List.of("AIEDXB")
        );

        // When
        doReturn(new PageImpl<>(new ArrayList<CcaListViewVO>(), pageable, 5))
                .when(ccaDao).findCcaListViewVO(ccaListViewFilterVO, pageable, maxResult, firstResult);
        doReturn(agentCodesByAgentGroups)
                .when(sharedComponent)
                .findGroupEntitiesOfGroupNames("AUTRAT", "AGTGRP", List.of("ABC", "AGGRP"));

        // Then
        assertNotNull(getCCAListViewFeature.perform(ccaListViewFilterVO));
    }

    private static Stream<CCAListViewFilterVO> provideCCAMasterAndCcaFilterVOAndExpectedResult() {
        // Given
        final var dateRangeFilterData = new DateRangeFilterData();
        dateRangeFilterData.setTo("2022-06-06");
        dateRangeFilterData.setFrom("2022-06-06");

        final var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setCcaIssueDate(dateRangeFilterData);
        ccaListViewFilterVO.setExportBillingStatus(new ArrayList<>(List.of("CASS Billed", "CASS Billable", "Customer Billed")));
        ccaListViewFilterVO.setImportBillingStatus(new ArrayList<>(List.of("Import Billed", "Import Billable", "Withdrawn", "CASS Import Billable")));

        final var ccaListViewFilterVO2 = new CCAListViewFilterVO();
        ccaListViewFilterVO2.setCcaIssueDate(new DateRangeFilterData());

        final var ccaListViewFilterVO3 = new CCAListViewFilterVO();
        ccaListViewFilterVO3.setAgentGroup(List.of("ABC", "AGGRP"));

        return Stream.of(new CCAListViewFilterVO(), ccaListViewFilterVO, ccaListViewFilterVO2, ccaListViewFilterVO3);
    }

    @Test
    void shouldMapWithoutErrors() {
        // Then
        assertDoesNotThrow(() -> ccaMasterMapper.mapCcaListViewVOToPageInfo(List.of(new CcaListViewModal()), 0, 5));
    }

}