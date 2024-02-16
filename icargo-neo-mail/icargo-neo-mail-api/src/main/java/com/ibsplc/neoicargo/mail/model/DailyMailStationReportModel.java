package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class DailyMailStationReportModel {
    private String flightNumber;
    private int flightCarrireID;
    private int flightSeqNumber;
    private int segsernum;
    private String carrierCode;
    private String uldnum;
    private String destination;
    private Measure netweight;
    private Measure grossweight;
    private String remark;
    private String bagCount;

}
