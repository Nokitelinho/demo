package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditTransportationModel extends BaseModel {
	private String arrivalPort;
	private String arrivalPlaceName;
	private String departurePort;
	private String departurePlaceName;
	private LocalDate departureTime;
	private LocalDate arrivalDate;
	private int carrierID;
	private String carrierCode;
	private String carrierName;
	private String arrivalCodeListAgency;
	private String departureCodeListAgency;
	private String agencyForCarrierCodeList;
	private String conveyanceReference;
	private String modeOfTransport;
	private String transportStageQualifier;
	private String transportLegRate;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int transportSerialNum;
	private int segmentSerialNum;
	private String transportIdentification;
	private String contractReference;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
