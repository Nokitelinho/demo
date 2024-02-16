package com.ibsplc.neoicargo.tracking.vo;

import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SplitVO extends AbstractVO {

    private Integer splitNumber;
    private Integer pieces;
    private TransitStationVO transitStations;
    private String milestoneStatus;
    private List<SplitDetailsItemVO> splitDetails;

    //Uncomment for testing purpose only..
    /*@Override
    public String toString() {
        return new StringBuilder()
                .append("{\n")
                .append("\tsplitNumber: " + splitNumber + ",\n")
                .append("\tpieces: " + pieces + ",\n")
                .append("\ttransitStations: " + transitStations + "\n,")
                .append("\tmilestoneStatus: " + milestoneStatus + ",\n")
                .append("\tsplitDetails: " + splitDetails.toString() + ",\n")
                .append("}")
                .toString();
    }*/

}
