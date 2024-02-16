package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class BookingTimeModel extends BaseModel {
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
