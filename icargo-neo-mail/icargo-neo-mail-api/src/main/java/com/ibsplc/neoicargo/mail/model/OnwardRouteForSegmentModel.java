package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OnwardRouteForSegmentModel extends BaseModel {
	private int routingSerialNumber;
	private String onwardCarrierCode;
	private LocalDate onwardFlightDate;
	private String pou;
	private int onwardCarrierId;
	private String onwardFlightNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
