package com.ibsplc.neoicargo.mail.vo;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991
 */
@Setter
@Getter
public class HandoverVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	private ZonedDateTime handOverdate_time;
	private List<String> mailId;
	private List<String> action;
	private String attributeReason;
	private String attributeCarrier;
	private String dateTime;
	private long fltSeqNum;
	private ZonedDateTime flightDate;
	private String orgExgOffice;
}
