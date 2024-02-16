package com.ibsplc.neoicargo.cca.component.feature.getccalistview.enricher;

import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.masters.shared.SharedComponent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
public class AgentCodesEnricher extends Enricher<CCAListViewFilterVO> {

    private final SharedComponent sharedComponent;

    @Override
    public void enrich(CCAListViewFilterVO ccaListViewFilterVO) {
        if (Optional.ofNullable(ccaListViewFilterVO.getAgentGroup()).map(List::isEmpty).orElse(true)) {
            return;
        }

        var agentCodesByAgentGroups = sharedComponent.findGroupEntitiesOfGroupNames(
                "AUTRAT",
                "AGTGRP",
                ccaListViewFilterVO.getAgentGroup()
        )
                .values()
                .stream()
                .flatMap(Collection::stream);
        ccaListViewFilterVO.setAgentCode(
                Stream.concat(
                        agentCodesByAgentGroups,
                        Optional.ofNullable(ccaListViewFilterVO.getAgentCode())
                                .stream()
                                .flatMap(Collection::stream)
                )
                        .collect(Collectors.toList())
        );
    }
}
