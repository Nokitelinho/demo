package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * TODO Add the purpose of this class
 * @author A-3109
 */
@Setter
@Getter
public class OnwardRouteForSegmentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private int routingSerialNumber;
	private String onwardCarrierCode;
	private ZonedDateTime onwardFlightDate;
	private String pou;
	private int onwardCarrierId;
	private String onwardFlightNumber;
	private String operationFlag;
}
