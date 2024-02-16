package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SplitDetailsItemVO extends AbstractVO {

    private String itemId;
    private String nextItemId;
    private String originAirportCode;
    private String milestoneStatus; //nullable
    private LocalDateTime milestoneTime;
    private String milestoneTimePostfix;
    private Integer pieces;
    private String carrierCode;
    private String flightNumber;
    private ActualFlightDataVO actualFlightData; //nullable
    private List<SplitDetailsItemVO> subSplits;

    //Uncomment for testing purpose only!
    /*@Override
    public String toString() {
        return new StringBuilder()
                .append("{\n")
                .append("\t\titemId: " + itemId + ",\n")
                .append("\t\tnextItemId: " + nextItemId + ",\n")
                .append("\t\toriginAirportCode: " + originAirportCode + "\n,")
                .append("\t\tmilestoneStatus: " + milestoneStatus + ",\n")
                .append("\t\tmilestoneTime: " + milestoneTime + ",\n")
                .append("\t\tmilestoneTimePostfix: " + milestoneTimePostfix + ",\n")
                .append("\t\tpieces: " + pieces + ",\n")
                .append("\t\tcarrierCode: " + carrierCode + ",\n")
                .append("\t\tflightNumber: " + flightNumber + ",\n")
                .append("\t\tactualFlightData: " + actualFlightData + ",\n")
                .append("\t\tsubSplits: " + subSplits.toString() + ",\n")
                .append("}")
                .toString();
    }*/
}
