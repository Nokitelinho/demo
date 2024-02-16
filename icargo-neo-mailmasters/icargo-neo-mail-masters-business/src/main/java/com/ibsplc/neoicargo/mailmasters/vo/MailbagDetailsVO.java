package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailbagDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private Long mailSequenceNumber;
	private String mailbagId;
	private Quantity weight;
	private String flightCarrierCode;
	private String flightNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private ZonedDateTime std;
	private ZonedDateTime stdutc;
	private ZonedDateTime sta;
	private ZonedDateTime stautc;
	private String inboundResditEvtCode;
	private String inboundSource;
	private String arrivalAirport;
	private String outboundResditEvtCode;
	private String outboundSource;
	private String departureAirport;
	private String containerId;
	private String hbaType;
	private String hbaPosition;
}
