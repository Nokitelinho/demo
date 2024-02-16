package com.ibsplc.neoicargo.mailmasters.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagModel extends BaseModel {
	private String mailSource;
	private String screen;
	private String companyCode;
	private String mailbagId;
	private String despatchId;
	private long malseqnum;
	private boolean remove;
	private boolean isMailUpdateFlag;
	private String screeningUser;
	private String ooe;
	private String doe;
	private String mailCategoryCode;
	private String mailSubclass;
	private String mailClass;
	private String fromCarrier;
	private int year;
	private String despatchSerialNumber;
	private String receptacleSerialNumber;
	private String registeredOrInsuredIndicator;
	private String highestNumberedReceptacle;
	private Measure weight;
	private LocalDate consignmentDate;
	private String scannedUser;
	private String reassignFlag;
	private int carrierId;
	private int fromCarrierId;
	private String fromFightNumber;
	private long fromFlightSequenceNumber;
	private int fromSegmentSerialNumber;
	private LocalDate fromFlightDate;
	private String fromContainer;
	private String fromContainerType;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String uldNumber;
	private String currencyCode;
	private String mailCompanyCode;
	private boolean isDespatch;
	private LocalDate reqDeliveryTime;
	private String shipmentPrefix;
	private String documentNumber;
	private int documentOwnerIdr;
	private int duplicateNumber;
	private int sequenceNumber;
	private String orgPaName;
	private String dstPaName;
	private String operatorOrigin;
	private String operatorDestination;
	private String type;
	private String receptacleType;
	private String mailServiceLevel;
	private String onTimeDelivery;
	private String metTransWindow;
	private String origin;
	private String destination;
	private String routingAvlFlag;
	private String pltEnableFlag;
	private String transitFlag;
	private String mailbagSource;
	private String mailbagDataSource;
	private boolean rdtProcessing;
	private String scanningWavedFlag;
	private boolean manifestInfoErrorFlag;
	private Measure actualWeight;
	private String routingInfo;
	private String rfdFlag;
	private String isFromTruck;
	private LocalDate ghttim;
	private String contractIDNumber;
	private String awbNumber;
	private String fromPanel;
	private boolean isScanTimeEntered;
	private LocalDate transWindowEndTime;
	private String serviceResponsive;
	private String messageVersion;
	private boolean isOffloadAndReassign;
	private String billingStatus;
	private boolean paBuiltFlagUpdate;
	private boolean paContainerNumberUpdate;
	private boolean needDestUpdOnDlv;
	private String triggerForReImport;
	private String containerJourneyId;
	private String securityStatusCode;
	private boolean importMailbag;
	private String messageSenderIdentifier;
	private String messageRecipientIdentifier;
	private boolean foundResditSent;
	private String autoArriveMail;
	private boolean deliveryStatusForAutoArrival;
	private boolean blockReceivedResdit;
	private double declaredValue;
	private String carrierCode;
	private String ownAirlineCode;
	private LocalDate flightDate;
	private String operationalFlag;
	private String containerType;
	private String acceptanceFlag;
	private String offloadedReason;
	private String offloadedRemarks;
	private String offloadedDescription;
	private String returnedReason;
	private String returnedRemarks;
	private String returnedDescription;
	private String containerNumber;
	private boolean isoffload;
	private Collection<MailbagHistoryModel> mailbagHistories;
	private Collection<DamagedMailbagModel> damagedMailbags;
	private String pou;
	private LocalDate scannedDate;
	private String scannedPort;
	private String latestStatus;
	private String operationalStatus;
	private String damageFlag;
	private String consignmentNumber;
	private String finalDestination;
	private Measure strWeight;
	private int consignmentSequenceNumber;
	private String paCode;
	private String arrivedFlag;
	private String undoArrivalFlag;
	private String deliveredFlag;
	private int legSerialNumber;
	private int count;
	private String errorType;
	private String errorCode;
	private String errorDescription;
	private String pol;
	private String acknowledge;
	private String transferFlag;
	private String containerFlag;
	private String resditEventString;
	private String transferFromCarrier;
	private String carditKey;
	private int carditSequenceNumber;
	private String stationCode;
	private String resditEventPort;
	private GMTDate resditEventUTCDate;
	private String carditRecipientId;
	private String handoverPartner;
	private int resditEventSeqNum;
	private String inventoryContainer;
	private String inventoryContainerType;
	private LocalDate resditEventDate;
	private String controlDocumentNumber;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String orgCityDesc;
	private String destCityDesc;
	private String subClassDesc;
	private String destCountryDesc;
	private String currentDateStr;
	private String paBuiltFlag;
	private String flagPAULDResidit;
	private int acceptedBags;
	private Measure acceptedWeight;
	private Measure volume;
	private String flightStatus;
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	private String paDescription;
	private String intact;
	private String actionMode;
	private String bellyCartId;
	private int seqIdentifier;
	private String inList;
	private String accepted;
	private String sealNumber;
	private String arrivalSealNumber;
	private String mailStatus;
	private String mraStatus;
	private LocalDate latestScannedDate;
	private boolean isDeliveryNeeded;
	private String mailOutCarrier;
	private long mailSequenceNumber;
	private boolean resditRequired;
	private MailInConsignmentModel mailConsignmentVO;
	private String newMailId;
	private boolean mailUpdated;
	private boolean fromReturnPopUp;
	private String displayUnit;
	private String upliftAirport;
	private String mailOrigin;
	private String mailDestination;
	private String stdOrStaTruckFlag;
	private double vol;
	private String volUnit;
	private String carrier;
	private String carditPresent;
	private String actualWeightUnit;
	private boolean fromDeviationList;
	private String latValidationNeeded;
	private LocalDate latestAcceptanceTime;
	private String storageUnit;
	private boolean flightClosureCheckNotNeeded;
	private String acceptanceAirportCode;
	private LocalDate acceptanceScanDate;
	private String acceptancePostalContainerNumber;
	private String previousPostalContainerNumber;
	private boolean originUpdate;
	private boolean destinationUpdate;
	private boolean securityDetailsPresent;
	private Collection<ConsignmentScreeningModel> consignmentScreeningVO;
	private String transactionLevel;
	private String mailRemarks;
	private String displayLabel;
	private String containerForInventory;
	private String containerTypeAtAirport;
	private Collection<MailAttachmentModel> attachments;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}