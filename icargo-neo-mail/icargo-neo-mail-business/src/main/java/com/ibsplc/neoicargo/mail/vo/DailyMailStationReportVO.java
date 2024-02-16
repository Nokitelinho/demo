package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DailyMailStationReportVO extends AbstractVO{

    private String flightNumber;
    private int flightCarrireID;
    private int flightSeqNumber;
    private int segsernum;
    private String carrierCode;
    private String uldnum;
    private String destination;
    private Quantity netweight;
    private Quantity grossweight;
    private String remark;
    private String bagCount;
}
