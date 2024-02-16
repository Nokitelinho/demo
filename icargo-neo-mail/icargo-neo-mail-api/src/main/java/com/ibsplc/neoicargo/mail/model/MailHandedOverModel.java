package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailHandedOverModel extends BaseModel {
    private String mailbagId;
    private String dsn;
    private String inwardFlightCarrierCode;
    private String inwardFlightNum;
    private LocalDate inwardFlightDate;
    private LocalDate onwardFlightDate;
    private String onwardCarrier;
    private String onwardFlightNum;
    private String ooe;
    private String doe;
    private Measure weight;
}
