package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

@Setter
@Getter
public class MailBookingDetailVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int ownerId;
	private int duplicateNumber;
	private int sequenceNumber;
	private String bookingCarrierCode;
	private String bookingFlightNumber;
	private int bookingFlightSequenceNumber;
	private ZonedDateTime bookingFlightDate;
	private String agentCode;
	private String mailScc;
	private ZonedDateTime shipmentDate;
	private String awbOrgin;
	private String awbDestination;
	private int bookedPieces;
	private double bookedWeight;
	private double bookedVolume;
	private String bookingStatus;
	private String shipmentStatus;
	private String bookingStation;
	private ZonedDateTime bookingDate;
	private String remarks;
	private int statedPieces;
	private String companyCode;
	private int segementserialNumber;
	private int serialNumber;
	private long mailSequenceNumber;
	private String origin;
	private String destination;
	private int bookingFlightCarrierid;
	private String selectedFlightNumber;
	private ZonedDateTime selectedFlightDate;
	private boolean splitBooking;
	private boolean destinationCheckReq;
	private String awbNumber;
	private String standardPieces;
	private String standardWeight;
	private String volume;
	private String pol;
	private String pou;
	private String scc;
	private String plannedPieces;
	private String plannedWeight;
	private String plannedFlight;
	private String plannedSegment;
	private ZonedDateTime flightDate;
	private String shipmentDescription;
	private String flightTime;
	private String carrierCode;
	private String attachmentSource;
	private int attachedMailBagCount;
	private Collection<MailBookingDetailVO> bookedFlights;
}
