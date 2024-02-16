package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.masters.shared.SharedComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(JUnitPlatform.class)
class AgentCodesEnricherTest {

    @InjectMocks
    private AgentCodesEnricher agentCodesEnricher;

    @Mock
    private SharedComponent sharedComponent;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldEnrichAgentCodes() {
        // Given
        final var agentCodesByAgentGroups = Map.of(
                "ABC", List.of("A5555"),
                "AGGRP", List.of("AIEDXB")
        );

        var ccaListViewFilterVO = new CCAListViewFilterVO();
        ccaListViewFilterVO.setAgentGroup(List.of("ABC", "AGGRP"));

        // When
        doReturn(agentCodesByAgentGroups)
                .when(sharedComponent)
                .findGroupEntitiesOfGroupNames("AUTRAT", "AGTGRP", List.of("ABC", "AGGRP"));
        agentCodesEnricher.enrich(ccaListViewFilterVO);

        // Then
        assertEquals(
                Set.of("AIEDXB", "A5555"),
                Set.copyOf(ccaListViewFilterVO.getAgentCode())
        );
    }

    @Test
    void shouldSkipEnrichingIfNoAgentGroups() {
        // Then
        assertDoesNotThrow(() -> agentCodesEnricher.enrich(new CCAListViewFilterVO()));
    }
}
