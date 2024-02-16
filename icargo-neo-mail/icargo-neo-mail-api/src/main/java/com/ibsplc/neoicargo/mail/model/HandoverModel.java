package com.ibsplc.neoicargo.mail.model;

import java.util.List;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class HandoverModel extends BaseModel {
	private String companyCode;
	private String dstExgOffice;
	private String destAirport;
	private String handOverType;
	private String handOverID;
	private String nestID;
	private String handOverHandler;
	private String handOverCarrierCode;
	private String carrierCode;
	private String flightNumber;
	private String origin;
	private String destination;
	private LocalDate handOverdate_time;
	private List<String> mailId;
	private List<String> action;
	private String attributeReason;
	private String attributeCarrier;
	private String dateTime;
	private long fltSeqNum;
	private LocalDate flightDate;
	private String orgExgOffice;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
