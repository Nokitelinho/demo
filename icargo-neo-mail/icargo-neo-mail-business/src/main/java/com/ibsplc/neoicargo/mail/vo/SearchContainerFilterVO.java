package com.ibsplc.neoicargo.mail.vo;

import java.util.ArrayList;

import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-3109
 */
@Setter
@Getter
public class SearchContainerFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String containerNumber;
	private ZonedDateTime assignedFromDate;
	private ZonedDateTime assignedToDate;
	private String departurePort;
	private String assignedUser;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private String finalDestination;
	private ZonedDateTime flightDate;
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
	private boolean isOracleDataSource;
	private String baseQuery;
	private PageableNativeQuery<ContainerVO> pgqry;

	public void setBulkPresent(boolean isBulkPresent) {
		this.isBulkPresent = isBulkPresent;
	}

	public void setHbaMarked(String hbaMarkedFlag) {
		this.hbaMarkedFlag = hbaMarkedFlag;
	}
}
