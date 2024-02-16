package com.ibsplc.neoicargo.tracking.component.feature.getshipment.calculateflightdata.enricher;

import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.tracking.vo.MilestoneCodeEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneNameEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneStatusEnum;
import com.ibsplc.neoicargo.tracking.vo.MilestoneVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentDetailsVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestoneEventVO;
import com.ibsplc.neoicargo.tracking.vo.ShipmentMilestonePlanVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.summingInt;

@Slf4j
@Component("milestoneEnricher")
public class MilestoneEnricher extends Enricher<ShipmentDetailsVO> {

    @Override
    public void enrich(ShipmentDetailsVO shipmentDetailsVO) {

        var mainAirports = List.of(shipmentDetailsVO.getOriginAirportCode(), shipmentDetailsVO.getDestinationAirportCode());
        var piecesPerMilestoneCodeActual = shipmentDetailsVO.getEvents().stream()
                .filter(event -> mainAirports.contains(event.getAirportCode()))
                .collect(groupingBy(ShipmentMilestoneEventVO::getMilestoneCode,
                        summingInt(ShipmentMilestoneEventVO::getPieces)));

        var piecesPerMilestoneCodePlaned = shipmentDetailsVO.getPlans().stream()
                .filter(event -> mainAirports.contains(event.getAirportCode()))
                .collect(groupingBy(ShipmentMilestonePlanVO::getMilestoneCode,
                        summingInt(ShipmentMilestonePlanVO::getPieces)));

        var milestones = new ArrayList<MilestoneVO>();
        milestones.add(new MilestoneVO(MilestoneNameEnum.ACCEPTED,
                defineMilestoneAcceptedStatus(piecesPerMilestoneCodeActual, piecesPerMilestoneCodePlaned)));

        milestones.add(new MilestoneVO(MilestoneNameEnum.DEPARTED,
                defineMilestoneStatus(MilestoneCodeEnum.DEP, piecesPerMilestoneCodeActual,
                        piecesPerMilestoneCodePlaned)));

        milestones.add(new MilestoneVO(MilestoneNameEnum.ARRIVED,
                defineMilestoneStatus(MilestoneCodeEnum.ARR, piecesPerMilestoneCodeActual,
                        piecesPerMilestoneCodePlaned)));

        milestones.add(new MilestoneVO(MilestoneNameEnum.DELIVERED,
                defineMilestoneStatus(MilestoneCodeEnum.DLV, piecesPerMilestoneCodeActual,
                        piecesPerMilestoneCodePlaned)));

        shipmentDetailsVO.setMilestones(milestones);
    }

    private MilestoneStatusEnum defineMilestoneStatus(MilestoneCodeEnum milestone,
                                                      Map<MilestoneCodeEnum, Integer> piecesPerMilestoneCodeActual,
                                                      Map<MilestoneCodeEnum, Integer> piecesPerMilestoneCodePlaned) {
        var actualPieces = piecesPerMilestoneCodeActual.getOrDefault(milestone, 0)
                + calcActualPiecesCorrection(milestone, piecesPerMilestoneCodeActual);
        if (actualPieces == 0) {
            return MilestoneStatusEnum.TO_DO;
        }
        var planedPieces = piecesPerMilestoneCodePlaned.getOrDefault(milestone, 0);
        if (planedPieces > actualPieces) {
            return MilestoneStatusEnum.IN_PROGRESS;
        }
        return MilestoneStatusEnum.DONE;
    }
    private Integer calcActualPiecesCorrection(
            MilestoneCodeEnum milestone,
            Map<MilestoneCodeEnum, Integer> piecesPerMilestoneCodeActual) {
        if (milestone.equals(MilestoneCodeEnum.DLV)) {
            return piecesPerMilestoneCodeActual.getOrDefault(MilestoneCodeEnum.DRN, 0) * -1;
        }
        return 0;
    }

    private MilestoneStatusEnum defineMilestoneAcceptedStatus(Map<MilestoneCodeEnum, Integer> piecesPerMilestoneCodeActual,
                                                      Map<MilestoneCodeEnum, Integer> piecesPerMilestoneCodePlaned) {
        var status = defineMilestoneStatus(MilestoneCodeEnum.RCS, piecesPerMilestoneCodeActual, piecesPerMilestoneCodePlaned);
        if(status.equals(MilestoneStatusEnum.TO_DO) &&
                (piecesPerMilestoneCodeActual.getOrDefault(MilestoneCodeEnum.FOH, 0) > 0)) {
            return MilestoneStatusEnum.IN_PROGRESS;
        }

        return status;
    }
}
