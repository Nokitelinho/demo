package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailBookingDetailModel extends BaseModel {
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private int ownerId;
	private int duplicateNumber;
	private int sequenceNumber;
	private String bookingCarrierCode;
	private String bookingFlightNumber;
	private int bookingFlightSequenceNumber;
	private LocalDate bookingFlightDate;
	private String agentCode;
	private String mailScc;
	private LocalDate shipmentDate;
	private String awbOrgin;
	private String awbDestination;
	private int bookedPieces;
	private double bookedWeight;
	private double bookedVolume;
	private String bookingStatus;
	private String shipmentStatus;
	private String bookingStation;
	private LocalDate bookingDate;
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
	private LocalDate selectedFlightDate;
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
	private LocalDate flightDate;
	private String shipmentDescription;
	private String flightTime;
	private String carrierCode;
	private String attachmentSource;
	private int attachedMailBagCount;
	private Collection<MailBookingDetailModel> bookedFlights;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
