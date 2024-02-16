package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.icargo.business.reco.defaults.vo.EmbargoDetailsVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailArrivalVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String airportCode;
	private String flightNumber;
	private String flightCarrierCode;
	private int carrierId;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private ZonedDateTime arrivalDate;
	private String arrivedUser;
	private boolean isScanned;
	private String flightStatus;
	private String mailStatus;
	private String transferCarrier;
	private int transferCarrierId;
	private String arrivalPA;
	private String mailSource;
	private boolean isDeliveryCheckNeeded;
	private ZonedDateTime scanDate;
	private String ownAirlineCode;
	private int ownAirlineId;
	private boolean isPartialDelivery;
	/** 
	* This attribute is implemented to incorporate Delivery Functionality in HHT Arrival Screen
	*/
	private boolean isDeliveryNeeded;
	private int segmentSerialNumber;
	private ZonedDateTime flightDate;
	private String pou;
	private String pol;
	private String route;
	private String changeFlightFlag;
	private boolean isOfflineJob;
	private boolean mailVOUpdated;
	private String isFromTruck;
	/** 
	* @return the isFromTruck
	*/
	private String aircraftType;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
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
	private ZonedDateTime actualArrivalTime;
	private String legDestination;
	private String mailCompanyCode;
	private String onlineAirportParam;
	private Collection<EmbargoDetailsVO> embargoDetails;
	private String flightType;
	private boolean isFoundResditSent;
	private Collection<ContainerDetailsVO> containerDetails;
	private boolean flightChange;

	/** 
	* @param isScanned The isScanned to set.
	*/
	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	/** 
	* @param isDeliveryCheckNeeded The isDeliveryCheckNeeded to set.
	*/
	public void setDeliveryCheckNeeded(boolean isDeliveryCheckNeeded) {
		this.isDeliveryCheckNeeded = isDeliveryCheckNeeded;
	}

	/** 
	* @param isPartialDelivery The isPartialDelivery to set.
	*/
	public void setPartialDelivery(boolean isPartialDelivery) {
		this.isPartialDelivery = isPartialDelivery;
	}

	/** 
	* @param isDeliveryNeeded the isDeliveryNeeded to set
	*/
	public void setDeliveryNeeded(boolean isDeliveryNeeded) {
		this.isDeliveryNeeded = isDeliveryNeeded;
	}

	/** 
	* @param isOfflineJob the isOfflineJob to set
	*/
	public void setOfflineJob(boolean isOfflineJob) {
		this.isOfflineJob = isOfflineJob;
	}

	/** 
	* @param isArrivalAndDeliveryMarkedTogether the isArrivalAndDeliveryMarkedTogether to set
	*/
	public void setArrivalAndDeliveryMarkedTogether(boolean isArrivalAndDeliveryMarkedTogether) {
		this.isArrivalAndDeliveryMarkedTogether = isArrivalAndDeliveryMarkedTogether;
	}

	public void setFoundResditSent(boolean isFoundResditSent) {
		this.isFoundResditSent = isFoundResditSent;
	}
}
