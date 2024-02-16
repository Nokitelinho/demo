package com.ibsplc.neoicargo.tracking.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class DLVPiecesUsageVO {
    private Integer unusedDLVPieces;
    private Integer unusedDRNPieces;
    private Map<String, DLVPiecesUsageVO> deliveredByAirport;

    public DLVPiecesUsageVO(List<ShipmentMilestoneEventVO> awbEvents, Integer awbPieces) {
        var totallyDelivered = awbEvents.stream()
                .filter(event -> event.getMilestoneCode().equals(MilestoneCodeEnum.DLV))
                .map(ShipmentMilestoneEventVO::getPieces)
                .reduce(0, Integer::sum);
        var totallyReturned = awbEvents.stream()
                .filter(event -> event.getMilestoneCode().equals(MilestoneCodeEnum.DRN))
                .map(ShipmentMilestoneEventVO::getPieces)
                .reduce(0, Integer::sum);

        var piecesCorrection = totallyDelivered > awbPieces
                ? totallyDelivered - awbPieces
                : 0;

        this.unusedDLVPieces = totallyDelivered - piecesCorrection;
        this.unusedDRNPieces = totallyReturned - piecesCorrection;
        this.deliveredByAirport = new HashMap<>();
    }

    private DLVPiecesUsageVO(Integer unusedDLVPieces, Integer unusedDRNPieces) {
        this.unusedDLVPieces = unusedDLVPieces + unusedDRNPieces;
        this.unusedDRNPieces = unusedDRNPieces;
        this.deliveredByAirport = Collections.emptyMap();
    }

    public void logDeliveryUsageByAirport(String airportItemId, Pair<Integer, Integer> deliveredAndReturned) {
        this.deliveredByAirport.put(
                airportItemId,
                new DLVPiecesUsageVO(deliveredAndReturned.getLeft(), deliveredAndReturned.getRight()));
    }
    public DLVPiecesUsageVO getDeliveryUsageByAirport(String airportItemId) {
        return this.deliveredByAirport.get(airportItemId);
    }

}
