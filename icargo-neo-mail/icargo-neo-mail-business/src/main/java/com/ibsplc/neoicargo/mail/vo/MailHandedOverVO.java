package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
public class MailHandedOverVO extends AbstractVO {
    private String mailbagId;
    private String dsn;
    private String inwardFlightCarrierCode;
    private String inwardFlightNum;
    private ZonedDateTime inwardFlightDate;
    private ZonedDateTime onwardFlightDate;
    private String onwardCarrier;
    private String onwardFlightNum;
    private String ooe;
    private String doe;
    private Quantity weight;
}

