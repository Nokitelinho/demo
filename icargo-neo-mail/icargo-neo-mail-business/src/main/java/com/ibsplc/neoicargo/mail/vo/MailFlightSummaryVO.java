package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	a-7779	:	30-Aug-2017	:	Draft
 */
@Setter
@Getter
public class MailFlightSummaryVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String airportCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private Collection<ShipmentSummaryVO> shipmentSummaryVOs;
	private String toFlightNumber;
	private int toCarrierId;
	private String toCarrierCode;
	private long toFlightSequenceNumber;
	private int toSegmentSerialNumber;
	private int toLegSerialNumber;
	private ZonedDateTime toFlightDate;
	private String pol;
	private String pou;
	private String toContainerNumber;
	private String finalDestination;
	private String eventCode;
	private String route;
	private Map<String, String> uldAwbMap;
	private Map<String, String> shpDetailMap;
	private String transferStatus;
}
