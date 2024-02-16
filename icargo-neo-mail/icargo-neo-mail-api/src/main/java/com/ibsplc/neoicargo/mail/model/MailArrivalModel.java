package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.neoicargo.common.modal.BaseModel;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Setter
@Getter
public class MailArrivalModel extends BaseModel {
	private String companyCode;
	private String airportCode;
	private String flightNumber;
	private String flightCarrierCode;
	private int carrierId;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private LocalDate arrivalDate;
	private String arrivedUser;
	private boolean isScanned;
	private String flightStatus;
	private String mailStatus;
	private String transferCarrier;
	private int transferCarrierId;
	private String arrivalPA;
	private String mailSource;
	private boolean isDeliveryCheckNeeded;
	private LocalDate scanDate;
	private String ownAirlineCode;
	private int ownAirlineId;
	private boolean isPartialDelivery;
	private boolean isDeliveryNeeded;
	private int segmentSerialNumber;
	private LocalDate flightDate;
	private String pou;
	private String pol;
	private String route;
	private String changeFlightFlag;
	private boolean isOfflineJob;
	private boolean mailVOUpdated;
	private String isFromTruck;
	private String aircraftType;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String arrivalTimeType;
	private String operationalStatus;
	private String gateInfo;
	private String manifestInfo;
	private String recievedInfo;
	private Double containerCount;
	private Double totalWeight;
	private Double mailCount;
	private int defaultPageSize;
	private int pageNumber;
	private boolean mailFlightChecked;
	private String mailDataSource;
	private boolean flightBypassFlag;
	private String paBuiltFlag;
	private String operatingReference;
	private String messageVersion;
	private String deliveryRemarks;
	private int offset;
	private boolean isArrivalAndDeliveryMarkedTogether;
	private LocalDate actualArrivalTime;
	private String legDestination;
	private String mailCompanyCode;
	private String onlineAirportParam;
	private Collection<EmbargoDetailsVO> embargoDetails;
	private String flightType;
	private boolean isFoundResditSent;
	private Collection<ContainerDetailsModel> containerDetails;
	private boolean flightChange;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
