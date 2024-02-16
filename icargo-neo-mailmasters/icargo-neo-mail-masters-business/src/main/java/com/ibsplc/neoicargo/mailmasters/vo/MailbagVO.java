package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class MailbagVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	/** 
	* The ModuleName
	*/
	public static final String MODULE = "mail";
	/** 
	* The SubModuleName
	*/
	public static final String SUBMODULE = "operations";
	/** 
	* The EntityName
	*/
	public static final String ENTITY = "mail.operations.Mailbag";
	private String mailSource;
	private String screen;
	private String companyCode;
	private String mailbagId;
	private String despatchId;
	private long malseqnum;
	private boolean remove;
	private boolean isMailUpdateFlag;
	private String screeningUser;

	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}

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
	private Quantity weight;
	private ZonedDateTime consignmentDate;
	private String scannedUser;
	private String reassignFlag;
	private int carrierId;
	private int fromCarrierId;
	private String fromFightNumber;
	private long fromFlightSequenceNumber;
	private int fromSegmentSerialNumber;
	private ZonedDateTime fromFlightDate;
	private String fromContainer;
	private String fromContainerType;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String uldNumber;
	private String currencyCode;
	private String mailCompanyCode;
	private boolean isDespatch;
	private ZonedDateTime reqDeliveryTime;
	public String shipmentPrefix;
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
	boolean manifestInfoErrorFlag;
	private Quantity actualWeight;
	private String routingInfo;
	private String rfdFlag;
	private String isFromTruck;
	private ZonedDateTime ghttim;
	private String contractIDNumber;
	private String awbNumber;
	private String fromPanel;
	private boolean isScanTimeEntered;
	private ZonedDateTime transWindowEndTime;
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
	private ZonedDateTime flightDate;
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
	private Collection<MailbagHistoryVO> mailbagHistories;
	private Collection<DamagedMailbagVO> damagedMailbags;
	private String pou;
	private ZonedDateTime scannedDate;
	private String scannedPort;
	private String latestStatus;
	private String operationalStatus;
	private String damageFlag;
	private String consignmentNumber;
	private String finalDestination;
	private Quantity strWeight;
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
	private ZonedDateTime resditEventUTCDate;
	private String carditRecipientId;
	private String handoverPartner;
	private int resditEventSeqNum;
	private String inventoryContainer;
	private String inventoryContainerType;
	private ZonedDateTime resditEventDate;
	private String controlDocumentNumber;
	private ZonedDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String orgCityDesc;
	private String destCityDesc;
	private String subClassDesc;
	private String destCountryDesc;
	private String currentDateStr;
	private String paBuiltFlag;
	private String flagPAULDResidit;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private Quantity volume;
	private String flightStatus;
	private String ubrNumber;
	private ZonedDateTime bookingLastUpdateTime;
	private ZonedDateTime bookingFlightDetailLastUpdTime;
	private String paDescription;
	private String intact;
	private String actionMode;
	/** 
	* AirNZ-985
	*/
	private String bellyCartId;
	/** 
	* @return
	*/
	private int seqIdentifier;
	private String inList;
	private String accepted;
	private String sealNumber;
	private String arrivalSealNumber;
	private String mailStatus;
	private String mraStatus;
	private ZonedDateTime latestScannedDate;
	/** 
	* This attribute is implemented to incorporate Delivery Functionality in HHT Arrival Screen
	*/
	private boolean isDeliveryNeeded;
	private String mailOutCarrier;
	private long mailSequenceNumber;
	private boolean resditRequired;
	private MailInConsignmentVO mailConsignmentVO;
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
	private ZonedDateTime latestAcceptanceTime;
	private String storageUnit;
	private boolean flightClosureCheckNotNeeded;
	private String acceptanceAirportCode;
	private ZonedDateTime acceptanceScanDate;
	private String acceptancePostalContainerNumber;
	private String previousPostalContainerNumber;
	private boolean originUpdate;
	private boolean destinationUpdate;
	private boolean securityDetailsPresent;
	private Collection<ConsignmentScreeningVO> consignmentScreeningVO;
	private String transactionLevel;
	private String mailRemarks;
	private String displayLabel;
	/** 
	* Added By Karthick V as the part of the NCA Mail Tracking CR
	*/
	private String containerForInventory;
	private String containerTypeAtAirport;
	private Collection<MailAttachmentVO> attachments;

	/** 
	* @param isDeliveryNeeded the isDeliveryNeeded to set
	*/
	public void setDeliveryNeeded(boolean isDeliveryNeeded) {
		this.isDeliveryNeeded = isDeliveryNeeded;
	}

	/** 
	* @param isDespatch the isDespatch to set
	*/
	public void setDespatch(boolean isDespatch) {
		this.isDespatch = isDespatch;
	}

	/** 
	* @param isScanTimeEntered the isScanTimeEntered to set
	*/
	public void setScanTimeEntered(boolean isScanTimeEntered) {
		this.isScanTimeEntered = isScanTimeEntered;
	}

	/** 
	* @param isOffloadAndReassign the isOffloadAndReassign to setSetter for isOffloadAndReassign  Added by : A-8061 on 12-Aug-2020 Used for :
	*/
	public void setOffloadAndReassign(boolean isOffloadAndReassign) {
		this.isOffloadAndReassign = isOffloadAndReassign;
	}
}
