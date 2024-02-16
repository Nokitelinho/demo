package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3109Onward Routing for ULD
 */
@Setter
@Getter
public class OnwardRoutingVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String onwardCarrierCode;
	private int onwardCarrierId;
	private String onwardFlightNumber;
	private ZonedDateTime onwardFlightDate;
	private String pou;
	private String assignmenrPort;
	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int routingSerialNumber;
}
