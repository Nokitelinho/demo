package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OnwardRoutingModel extends BaseModel {
	private String operationFlag;
	private String onwardCarrierCode;
	private int onwardCarrierId;
	private String onwardFlightNumber;
	private LocalDate onwardFlightDate;
	private String pou;
	private String assignmenrPort;
	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int routingSerialNumber;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
