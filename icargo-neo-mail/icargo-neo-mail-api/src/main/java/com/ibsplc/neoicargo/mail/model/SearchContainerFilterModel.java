package com.ibsplc.neoicargo.mail.model;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class SearchContainerFilterModel extends BaseModel {
	private String containerNumber;
	private LocalDate assignedFromDate;
	private LocalDate assignedToDate;
	private String departurePort;
	private String assignedUser;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private String finalDestination;
	private LocalDate flightDate;
	private String companyCode;
	private int absoluteIndex;
	private int destionationCarrierId;
	private int totalRecords;
	private String operationType;
	private String strFromDate;
	private String strToDate;
	private String strFlightDate;
	private String flightCarrierCode;
	private String currentAirport;
	private String transferStatus;
	private String searchMode;
	private String destination;
	private String subclassGroup;
	private String notClosedFlag;
	private String mailAcceptedFlag;
	private ArrayList<String> partnerCarriers;
	private String showEmptyContainer;
	private int pageSize;
	private ArrayList<String> containersToList;
	private boolean isBulkPresent;
	private String flightNumberFromInbound;
	private String flightCarrierCodeFromInbound;
	private String flightSeqNumber;
	private String legSerialNumber;
	private String showUnreleasedContainer;
	private boolean navigation;
	private String uldFulIndFlag;
	private String segOrigin;
	private String segDestination;
	private boolean excATDCapFlights;
	private boolean unplannedContainers;
	private String containerView;
	private String source;
	private String hbaMarkedFlag;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
