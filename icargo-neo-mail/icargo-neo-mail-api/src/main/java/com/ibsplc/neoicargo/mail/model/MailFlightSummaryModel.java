package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import java.util.Map;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailFlightSummaryModel extends BaseModel {
	private String companyCode;
	private String airportCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private String carrierCode;
	private LocalDate flightDate;
	private Collection<ShipmentSummaryVO> shipmentSummaryVOs;
	private String toFlightNumber;
	private int toCarrierId;
	private String toCarrierCode;
	private long toFlightSequenceNumber;
	private int toSegmentSerialNumber;
	private int toLegSerialNumber;
	private LocalDate toFlightDate;
	private String pol;
	private String pou;
	private String toContainerNumber;
	private String finalDestination;
	private String eventCode;
	private String route;
	private Map<String, String> uldAwbMap;
	private Map<String, String> shpDetailMap;
	private String transferStatus;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
