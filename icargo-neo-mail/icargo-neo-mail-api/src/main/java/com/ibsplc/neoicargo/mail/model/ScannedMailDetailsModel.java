package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ExistingMailbagVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ScannedMailDetailsModel extends BaseModel {
	private String airportCode;
	private String companyCode;
	private String mailSource;
	private String containerNumber;
	private String pou;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private String flightStatus;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String containerType;
	private Collection<MailbagModel> mailDetails;
	private Collection<MailbagModel> errorMailDetails;
	private Collection<DespatchDetailsModel> despatchDetails;
	private LocalDate flightDate;
	private String carrierCode;
	private String destination;
	private String acceptedFlag;
	private String operationFlag;
	private int scannedBags;
	private Measure scannedWeight;
	private int unsavedBags;
	private Measure unsavedWeight;
	private int savedBags;
	private Measure savedWeight;
	private String ownAirlineCode;
	private int legSerialNumber;
	private String status;
	private String summaryFlag;
	private String newContainer;
	private String errorType;
	private String errorDescription;
	private String processPoint;
	private int exceptionBagCout;
	private int deletedExceptionBagCout;
	private boolean acknowledged;
	private boolean hasErrors;
	private String inboundCarrierCode;
	private String inboundFlightNumber;
	private LocalDate inboundFlightDate;
	private Collection<ExistingMailbagVO> existingMailbagVOS;
	private ContainerDetailsModel validatedContainer;
	private String containerReassignFlag;
	private Collection<ErrorVO> containerSpecificErrors;
	private Collection<ContainerModel> scannedContainerDetails;
	private String toFlightNumber;
	private int toCarrierid;
	private String toCarrierCode;
	private long toFlightSequenceNumber;
	private int toSegmentSerialNumber;
	private int toLegSerialNumber;
	private LocalDate toFlightDate;
	private LocalDate operationTime;
	private String remarks;
	private String ContainerProcessPoint;
	private String containerArrivalFlag;
	private String containerDeliveryFlag;
	private Collection<MailUploadModel> offlineMailDetails;
	private String duplicateMailOverride;
	private String transferFromCarrier;
	private boolean expReassign;
	private String scannedUser;
	private boolean fromPolExist;
	private Collection<String> pols;
	private Collection<String> pous;
	private Collection<FlightValidationVO> flightValidationVOS;
	private boolean foundArrival;
	private boolean splitBooking;
	private boolean contOffloadReq;
	private String overrideValidations;
	private String transferFrmFlightNum;
	private long transferFrmFlightSeqNum;
	private LocalDate transferFrmFlightDate;
	private String isContainerPabuilt;
	private String androidFlag;
	private String operationType;
	private boolean arrivalException;
	private String processPointBeforeArrival;
	private boolean flightBypassFlag;
	private String containerAsSuchArrOrDlvFlag;
	private int totalMailbagCount;
	private Measure totalMailbagWeight;
	private int receivedMailbagCount;
	private Measure receivedMailbagWeight;
	private int deliveredMailbagCount;
	private Measure deliveredMailbagWeight;
	private String transactionLevel;
	private boolean notReqSecurityValAtDel;
	private boolean notReqAppReqFlgVal;
	private int transferFrmCarrierId;
	private boolean isValidateULDExists;
	private boolean foundTransfer;
	private String storageUnit;
	private String messageVersion;
	private LocalDate deviceDateAndTime;
	private int transferFrmFlightLegSerialNumber;
	private boolean isFoundDelivery;
	private boolean atdCaptured;
	private boolean forceAcpAfterErr;
	private boolean mailbagValidationRequired;
	private String errorCode;
	private boolean forceAccepted;
	private boolean notAccepted;
	private boolean originScan;
	private boolean containerFoundTransfer;
	private boolean rsgnmailbagFromdiffContainer;
	private String containerJourneyId;
	private String uldFulIndFlag;
	private String flightDateString;
	private String transitFlag;
	private long uldReferenceNo;
	private Measure actualUldWeight;
	private Collection<ConsignmentScreeningModel> consignmentScreeningVos;
	private boolean isScreeningPresent;
	private boolean isContainerDestChanged;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
