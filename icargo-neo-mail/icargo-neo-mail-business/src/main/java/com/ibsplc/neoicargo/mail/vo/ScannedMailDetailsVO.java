package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.core.lang.error.ErrorVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.neoicargo.mail.vo.ExistingMailbagVO;

/** 
 * @author A-3109
 */
@Setter
@Getter
public class ScannedMailDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
	private Collection<MailbagVO> mailDetails;
	private Collection<MailbagVO> errorMailDetails;
	private Collection<DespatchDetailsVO> despatchDetails;
	private ZonedDateTime flightDate;
	private String carrierCode;
	private String destination;
	private String acceptedFlag;
	private String operationFlag;
	private int scannedBags;
	private Quantity scannedWeight;
	private int unsavedBags;
	private Quantity unsavedWeight;
	private int savedBags;
	private Quantity savedWeight;
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
	private ZonedDateTime inboundFlightDate;
	private Collection<ExistingMailbagVO> existingMailbagVOS;
	private ContainerDetailsVO validatedContainer;
	private String containerReassignFlag;
	private Collection<ErrorVO> containerSpecificErrors;
	private Collection<ContainerVO> scannedContainerDetails;
	private String toFlightNumber;
	private int toCarrierid;
	private String toCarrierCode;
	private long toFlightSequenceNumber;
	private int toSegmentSerialNumber;
	private int toLegSerialNumber;
	private ZonedDateTime toFlightDate;
	private ZonedDateTime operationTime;
	private String remarks;
	private String ContainerProcessPoint;
	private String containerArrivalFlag;
	private String containerDeliveryFlag;
	private Collection<MailUploadVO> offlineMailDetails;
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
	private ZonedDateTime transferFrmFlightDate;
	private String isContainerPabuilt;
	private String androidFlag;
	private String operationType;
	private boolean arrivalException;
	private String processPointBeforeArrival;
	private boolean flightBypassFlag;
	private String containerAsSuchArrOrDlvFlag;
	private int totalMailbagCount;
	private Quantity totalMailbagWeight;
	private int receivedMailbagCount;
	private Quantity receivedMailbagWeight;
	private int deliveredMailbagCount;
	private Quantity deliveredMailbagWeight;
	private String transactionLevel;
	private boolean notReqSecurityValAtDel;
	private boolean notReqAppReqFlgVal;
	private int transferFrmCarrierId;
	private boolean isValidateULDExists;
	private boolean foundTransfer;
	private String storageUnit;
	private String messageVersion;
	private ZonedDateTime deviceDateAndTime;
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
	private Quantity actualUldWeight;
	private Collection<ConsignmentScreeningVO> consignmentScreeningVos;
	private boolean isScreeningPresent;
	private ContainerAssignmentVO latestContainerAssignmentVO;

	public void setScreeningPresent(boolean isScreeningPresent) {
		this.isScreeningPresent = isScreeningPresent;
	}

	public void setValidateULDExists(boolean isValidateULDExists) {
		this.isValidateULDExists = isValidateULDExists;
	}

	private boolean isContainerDestChanged;

	public void setContainerDestChanged(boolean isContainerDestChanged) {
		this.isContainerDestChanged = isContainerDestChanged;
	}

	/** 
	* @param isFoundDelivery the isFoundDelivery to setSetter for isFoundDelivery  Added by : A-8061 on 01-Aug-2020 Used for :
	*/
	public void setFoundDelivery(boolean isFoundDelivery) {
		this.isFoundDelivery = isFoundDelivery;
	}
}
