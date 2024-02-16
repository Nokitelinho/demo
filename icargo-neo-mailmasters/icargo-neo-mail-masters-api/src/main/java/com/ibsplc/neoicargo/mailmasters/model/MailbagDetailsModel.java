package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagDetailsModel extends BaseModel {
	private Long mailSequenceNumber;
	private String mailbagId;
	private Measure weight;
	private String flightCarrierCode;
	private String flightNumber;
	private String segmentOrigin;
	private String segmentDestination;
	private LocalDate std;
	private LocalDate stdutc;
	private LocalDate sta;
	private LocalDate stautc;
	private String inboundResditEvtCode;
	private String inboundSource;
	private String arrivalAirport;
	private String outboundResditEvtCode;
	private String outboundSource;
	private String departureAirport;
	private String containerId;
	private String hbaType;
	private String hbaPosition;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
