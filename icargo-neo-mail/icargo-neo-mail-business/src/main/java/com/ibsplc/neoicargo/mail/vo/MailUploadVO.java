package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;

@Setter
@Getter
public class MailUploadVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String mailSource;
	private String messageVersion;
	private String scanType;
	private String companyCode;
	private String mailCompanyCode;
	private String carrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private String containerPOU;
	private String containerNumber;
	private String containerType;
	private String containeDescription;
	private String containerPol;
	private String fromCarrierCode;
	private String remarks;
	private String mailTag;
	private String dateTime;
	private String damageCode;
	private String damageRemarks;
	private String offloadReason;
	private String returnCode;
	private String toContainer;
	private String toCarrierCode;
	private String toFlightNumber;
	private ZonedDateTime toFlightDate;
	private String toPOU;
	private String toDestination;
	private String orginOE;
	private String destinationOE;
	private String category;
	private String subClass;
	private int year;
	private int carrierId;
	private String processPoint;
	private String scanUser;
	private boolean isArrived;
	private boolean isAccepted;
	private ZonedDateTime scannedDate;
	private String mailKeyforDisplay;
	private boolean expRsn;
	private String fromPol;
	private String pols;
	private String overrideValidation;
	private String transferFrmFlightNum;
	private long transferFrmFlightSeqNum;
	private ZonedDateTime transferFrmFlightDate;
	private String operationType;
	private String deliverFlag;
	private String warningFlag;
	private String androidFlag;
	private String screeningUser;
	private Collection<MailAttachmentVO> attachments;
	private String containerJourneyId;
	private String uldFullIndicator;
	/** 
	* Used for stamping sender and recipient of incoming RESDIT
	*/
	private String messageSenderIdentifier;
	private String messageRecipientIdentifier;
	private boolean arrivalException;
	private boolean fromErrorHandling;
	private String processPointBeforeArrival;
	private boolean restrictErrorLogging;
	private String rawMessageBlob;
	String overrideULDVal;
	private String resolveFlagAndroid;
	private boolean rdtProcessing;
	private boolean resolveFromErrorHandling;
	private String storageUnit;
	private ZonedDateTime deviceDateAndTime;
	private String errorCode;
	private boolean forceAccepted;
	private String errorDescription;
	private boolean fromErrorHandlingForForceAcp;
	private Collection<FlightValidationVO> flightValidationVOS;

	public void setArrived(boolean isArrived) {
		this.isArrived = isArrived;
	}

	public void setAccepted(boolean isAccepted) {
		this.isAccepted = isAccepted;
	}

	private String dsn;
	private String rsn;
	private String scannedPort;
	private String destination;
	private String transactionLevel;
	private String orgin;
	private String highestnumberIndicator;
	private String registeredIndicator;
	private String consignmentDocumentNumber;
	private String paCode;
	private int totalBag;
	private Quantity totalWeight;
	private int bags;

	public void setFromGHAService(boolean isFromGHAService) {
		this.isFromGHAService = isFromGHAService;
	}

	private Quantity weight;
	private boolean isIntact;
	private int serialNumber;
	private boolean isFromGHAService;
	private long mailSequenceNumber;
	private String messageType;
	private String eventCode;
	private String totalEventCodes;
	private String nodeName;
	private long msgSequenceNumber;
	private Quantity uldActualWeight;
	private long flightSequenceNumber;
	private String cirCode;
	private boolean isDeliverd;

	public void setIntact(boolean isIntact) {
		this.isIntact = isIntact;
	}

	public void setDeliverd(boolean isDeliverd) {
		this.isDeliverd = isDeliverd;
	}

	private String securityMethods;
	private String issuedBy;
}
