/*
 * MailbagVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.GMTDate;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class MailbagVO extends AbstractVO {
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
	private String screeningUser;//Added by A-9498 as part of IASCB-44577

	public String getScreeningUser() {
		return screeningUser;
	}
	public void setScreeningUser(String screeningUser) {
		this.screeningUser = screeningUser;
	}
	public boolean isMailUpdateFlag() {
		return isMailUpdateFlag;
	}
	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}
	public String getDespatchId() {
		return despatchId;
	}
	public void setDespatchId(String despatchId) {
		this.despatchId = despatchId;
	}

	private String ooe;

	private String doe;

	private String mailCategoryCode;

	private String mailSubclass;

	private String mailClass;

	private String fromCarrier;

	/*
	 * Last Digit of year
	 */
	private int year;

	private String despatchSerialNumber;

	private String receptacleSerialNumber;

	private String registeredOrInsuredIndicator;

	private String highestNumberedReceptacle;

	//private double weight;
	private Measure weight;//added by A-7371

	private LocalDate consignmentDate;

	private String scannedUser;

	private String reassignFlag;

	private int carrierId;

	/*
	 * Contains the Arrival Details..
	 */
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
	private LocalDate reqDeliveryTime;//Added as part of ICRD-214795
	//added by a-7531 for icrd-192536
	public String shipmentPrefix;
	private String documentNumber;
	//Added for ICRD-211205 Starts
	private int documentOwnerIdr;
	private int duplicateNumber;
	private int sequenceNumber;
	//Added for ICRD-211205 ends
	//Added for ICRD-108366 by a-7871 starts
	private String orgPaName;
	private String dstPaName;
	private String operatorOrigin;
	private String operatorDestination;
	private String type;
	private String receptacleType;
	//Added for ICRD-108366 by a-7871 ends
	private String mailServiceLevel;//Added as part of ICRD-243469
	private String onTimeDelivery;
	private String metTransWindow; //Dummy added as part of  ICRD-243421
	private String origin;
	private String destination;
	private String routingAvlFlag;
	private String pltEnableFlag;
	private String transitFlag;
	private String mailbagSource; // Added by A-4809 for IASCB-137
	private String mailbagDataSource;
	private boolean rdtProcessing;
	//Added by A-7794 as part of ICRD-232299
	private String scanningWavedFlag;
	
	//Added as part of ICRD-229584 starts
	boolean manifestInfoErrorFlag;
	
	//private double actualWeight; //Added By A-8672 for ICRD-255039
	private Measure actualWeight;//added by A-7779 for ICRD-326752
	private String routingInfo;//Added by A-8464 for ICRD-273761
    private String rfdFlag;    //Added by A-7540 
	private String isFromTruck;

    private LocalDate ghttim;
    private String contractIDNumber;
    
    private String awbNumber;
    private String fromPanel;
    private boolean isScanTimeEntered;
	
    private LocalDate transWindowEndTime;
    private String serviceResponsive;
    private String messageVersion;//Added by A-8527 for IASCB-58918
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
	private String mailboxId;
	private String originOfExchangeOffice;
	private String destinationOfExchangeOffice;
	private String dsn;
	private String rsn;
	private int lcCount;
	private int cpCount;
	private int othersCount;
	private String destinatonPortCode;
	public String getDestinatonPortCode() {
		return destinatonPortCode;
	}
	public void setDestinatonPortCode(String destinatonPortCode) {
		this.destinatonPortCode = destinatonPortCode;
	}
	public int getLcCount() {
		return lcCount;
	}
	public void setLcCount(int lcCount) {
		this.lcCount = lcCount;
	}
	public int getCpCount() {
		return cpCount;
	}
	public void setCpCount(int cpCount) {
		this.cpCount = cpCount;
	}
	public int getOthersCount() {
		return othersCount;
	}
	public void setOthersCount(int othersCount) {
		this.othersCount = othersCount;
	}
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public String getOriginOfExchangeOffice() {
		return originOfExchangeOffice;
	}
	public void setOriginOfExchangeOffice(String originOfExchangeOffice) {
		this.originOfExchangeOffice = originOfExchangeOffice;
	}
	public String getDestinationOfExchangeOffice() {
		return destinationOfExchangeOffice;
	}
	public void setDestinationOfExchangeOffice(String destinationOfExchangeOffice) {
		this.destinationOfExchangeOffice = destinationOfExchangeOffice;
	}
	public String getServiceResponsive() {
		return serviceResponsive;
	}
	public void setServiceResponsive(String serviceResponsive) {
		this.serviceResponsive = serviceResponsive;
	}
	public String getContractIDNumber() {
		return contractIDNumber;
	}
	public void setContractIDNumber(String contractIDNumber) {
		this.contractIDNumber = contractIDNumber;
	}
	public boolean isManifestInfoErrorFlag() {
		return manifestInfoErrorFlag;
	}
	public void setManifestInfoErrorFlag(boolean manifestInfoErrorFlag) {
		this.manifestInfoErrorFlag = manifestInfoErrorFlag;
	}
	private String autoArriveMail;
	
	private boolean deliveryStatusForAutoArrival;

	public String getAutoArriveMail() {
		return autoArriveMail;
	}
	public void setAutoArriveMail(String autoArriveMail) {
		this.autoArriveMail = autoArriveMail;
	}
	//Added as part of ICRD-229584 ends
	/**
	 * @return the metTransWindow
	 */
	public String getMetTransWindow() {
		return metTransWindow;
	}
	/**
	 * @param metTransWindow the metTransWindow to set
	 */
	public void setMetTransWindow(String metTransWindow) {
		this.metTransWindow = metTransWindow;
	}
	/**
	 * @return the operatorOrigin
	 */
	public String getOperatorOrigin() {
		return operatorOrigin;
	}
	/**
	 * @param type The operatorOrigin to operatorOrigin.
	 */
	public void setOperatorOrigin(String operatorOrigin) {
		this.operatorOrigin = operatorOrigin;
	}
	/**
	 * @return the operatorDestination
	 */
	public String getOperatorDestination() {
		return operatorDestination;
	}
	/**
	 * @param type The operatorDestination to operatorDestination.
	 */
	public void setOperatorDestination(String operatorDestination) {
		this.operatorDestination = operatorDestination;
	}
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type The type to set.
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the receptacleType
	 */
	public String getReceptacleType() {
		return receptacleType;
	}
	/**
	 * @param type The receptacleType to receptacleType.
	 */
	public void setReceptacleType(String receptacleType) {
		this.receptacleType = receptacleType;
	}
	/**
	 * @return the orgPaName
	 */
	public String getOrgPaName() {
		return orgPaName;
	}
	/**
	 * @param orgPaName The orgPaName to set.
	 */
	public void setOrgPaName(String orgPaName) {
		this.orgPaName = orgPaName;
	}
	/**
	 * @return the dstPaName
	 */ 
	public String getDstPaName() {
		return dstPaName;
	}
	/**
	 * @param dstPaName The dstPaName to set.
	 */
	public void setDstPaName(String dstPaName) {
		this.dstPaName = dstPaName;
	}
	//added by A-7371 as part of ICRD-256798
	private boolean blockReceivedResdit;
	/**
	 * @return the currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	/**
	 * @param currencyCode the currencyCode to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	/**
	 * @return the declaredValue
	 */
	public double getDeclaredValue() {
		return declaredValue;
	}
	/**
	 * @param declaredValue the declaredValue to set
	 */
	public void setDeclaredValue(double declaredValue) {
		this.declaredValue = declaredValue;
	}

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

	private Collection<MailbagHistoryVO> mailbagHistories;

	private Collection<DamagedMailbagVO> damagedMailbags;

	private String pou;

	private LocalDate scannedDate;

	private String scannedPort;

	private String latestStatus;

	private String operationalStatus;

	private String damageFlag;

	private String consignmentNumber;

	private String finalDestination;

	// Added by Roopak for Accept mail
	//private String strWeight;
	private Measure strWeight;//added by A-7371

	private int consignmentSequenceNumber;

	public String getMessageSenderIdentifier() {
		return messageSenderIdentifier;
	}
	public void setMessageSenderIdentifier(String messageSenderIdentifier) {
		this.messageSenderIdentifier = messageSenderIdentifier;
	}
	public String getMessageRecipientIdentifier() {
		return messageRecipientIdentifier;
	}
	public void setMessageRecipientIdentifier(String messageRecipientIdentifier) {
		this.messageRecipientIdentifier = messageRecipientIdentifier;
	}

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

	//Added as a Part of the NCA -CR to link the Cardit against MessagingMsgMst
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

	/*
	 * Added By Karthick V as the part of the NCA Mail Tracking Bug Fix
	 *
	 */
	private String controlDocumentNumber;

	private LocalDate lastUpdateTime;

	private String lastUpdateUser;


	/*For MailTag Print*/

	private String orgCityDesc;
	private String destCityDesc;
	private String subClassDesc;
	private String destCountryDesc;
	private String currentDateStr;

	private String paBuiltFlag;

	private String flagPAULDResidit;

	private int acceptedBags;

	//private double acceptedWeight;

	private Measure acceptedWeight;//added by A-7371


	//private double volume;
	private Measure volume;//added by A-7371

	private String flightStatus;

    /*
     * Added by RENO For CR : AirNZ 404 : Mail Allocation
     */
    private String ubrNumber;
    private LocalDate bookingLastUpdateTime;
    private LocalDate bookingFlightDetailLastUpdTime;

	private String paDescription;

	private String intact;

	private String actionMode;
	/**
	 * AirNZ-985
	 */
	private String bellyCartId;

	/**
	 *
	 * @return
	 */
	private int seqIdentifier;

	private String inList;

	private String accepted;

	private String sealNumber;

	private String arrivalSealNumber;

	private String mailStatus;
	private String mraStatus;
	
	//Added as part of Bug ICRD-129234
	private LocalDate latestScannedDate;
	/**
	 * This attribute is implemented to incorporate
	 * Delivery Functionality in HHT Arrival Screen
	 */
	private boolean isDeliveryNeeded;
	//Added for Resdit Generation
	private String mailOutCarrier;
	private long mailSequenceNumber;

	private boolean resditRequired;
	private MailInConsignmentVO mailConsignmentVO;
	private String newMailId;
	private boolean mailUpdated;
	private boolean fromReturnPopUp;
	private String displayUnit;//added by A-7371 for ICRD-234919
	// Added by A-8176 for ICRD-259958
	private String upliftAirport;
	private String mailOrigin;
	private String mailDestination;
	private String stdOrStaTruckFlag;
	public String getCarrier() {
		return carrier;
	}
	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
	private double vol;
	private String volUnit;
	private String carrier;
	
	//Added by A-8893 for ICRD-338285
	private String carditPresent;
	
	private String actualWeightUnit;
	//Added by A-7540
	private boolean fromDeviationList;
	private String latValidationNeeded;
	private LocalDate latestAcceptanceTime;

	//added by A-9529 for IASCB-44567
	private String storageUnit;
	public String getStorageUnit() {
		return storageUnit;
	}
	public void setStorageUnit(String storageUnit) {
		this.storageUnit = storageUnit;
	}

	private boolean flightClosureCheckNotNeeded;
	private String acceptanceAirportCode;
	private LocalDate acceptanceScanDate; 
	private String acceptancePostalContainerNumber;
	private String previousPostalContainerNumber;
	private boolean originUpdate;
	private boolean destinationUpdate;
	private boolean securityDetailsPresent;
	private Collection<ConsignmentScreeningVO> consignmentScreeningVO;
	private String transactionLevel;
	private boolean isGHAUser;
  	private String screeningFlag;

	private String actWgtSta;
	/**
	 * @return the latValidationNeeded
	 */
	public String getLatValidationNeeded() {
		return latValidationNeeded;
	}
	/**
	 * @param latValidationNeeded the latValidationNeeded to set
	 */
	public void setLatValidationNeeded(String latValidationNeeded) {
		this.latValidationNeeded = latValidationNeeded;
	}
	/**
	 * 
	 * @return volume
	 */
	public Measure getVolume() {
		return volume;
	}
	/**
	 * @return the fromDeviationList
	 */
	public boolean isFromDeviationList() {
		return fromDeviationList;
	}
	/**
	 * @param fromDeviationList the fromDeviationList to set
	 */
	public void setFromDeviationList(boolean fromDeviationList) {
		this.fromDeviationList = fromDeviationList;
	}
	//Added by A-8893 for ICRD-338285 starts
	public String getCarditPresent() {
		return carditPresent;
	}
	public void setCarditPresent(String carditPresent) {
		this.carditPresent = carditPresent;
	}
	//Added by A-8893 for ICRD-338285 ends
	/**
	 * 
	 * @param volume
	 */
	public void setVolume(Measure volume) {
		this.volume = volume;
	}
	
	//Added by A-7540 for ICRD-197419
	private String mailRemarks;
		
		/**
		 * 	Getter for mailRemarks 
		 *	Added by : a-7540 on 18-Jul-2017
		 * 	Used for :ICRD-197419 for new field 'remarks'
		 */
		public String getMailRemarks() {
			return mailRemarks;
		}
		/**
		 *  @param mailRemarks the mailRemarks to set
		 * 	Setter for mailRemarks 
		 *	Added by : a-7540 on 18-Jul-2017
		 * 	Used for :ICRD-197419 for new field 'remarks'
		 */
		public void setMailRemarks(String mailRemarks) {
			this.mailRemarks = mailRemarks;
		}
	
	/**
	 * @return the mailSequenceNumber
	 */
	public long getMailSequenceNumber() {
		return mailSequenceNumber;
	}
	/**
	 * @param mailSequenceNumber the mailSequenceNumber to set
	 */
	public void setMailSequenceNumber(long mailSequenceNumber) {
		this.mailSequenceNumber = mailSequenceNumber;
	}
	/**
	 * @return Returns the seqIdentifier.
	 */
	public int getSeqIdentifier() {
		return seqIdentifier;
	}
	/**
	 * @param seqIdentifier The seqIdentifier to set.
	 */
	public void setSeqIdentifier(int seqIdentifier) {
		this.seqIdentifier = seqIdentifier;
	}

	/*
	 * Added For ANZ BUG : 37646
	 * Done in regard with Performance Improvement
	 */
	private String displayLabel;


	public String getBellyCartId() {
		return bellyCartId;
	}

	public void setBellyCartId(String bellyCartId) {
		this.bellyCartId = bellyCartId;
	}

	public String getFlightStatus() {
		return flightStatus;
	}

	public void setFlightStatus(String flightStatus) {
		this.flightStatus = flightStatus;
	}

	/**
	 * @return Returns the flagPAULDResidit.
	 */
	public String getFlagPAULDResidit() {
		return flagPAULDResidit;
	}

	/**
	 * @param flagPAULDResidit The flagPAULDResidit to set.
	 */
	public void setFlagPAULDResidit(String flagPAULDResidit) {
		this.flagPAULDResidit = flagPAULDResidit;
	}

	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	/**
	 * Added By Karthick V as the part of the NCA Mail Tracking CR
	 *
	 */

	private String  containerForInventory;

	private String  containerTypeAtAirport;
	
	
    private Collection<MailAttachmentVO> attachments;
	
	public Collection<MailAttachmentVO> getAttachments() {
		return attachments;
	}
	public void setAttachments(Collection<MailAttachmentVO> attachments) {
		this.attachments = attachments;
	}
	
	public String getContainerForInventory() {
		return containerForInventory;
	}

	public void setContainerForInventory(String containerForInventory) {
		this.containerForInventory = containerForInventory;
	}

	/**
	 * @return Returns the pol.
	 */
	public String getPol() {
		return pol;
	}

	/**
	 * @param pol The pol to set.
	 */
	public void setPol(String pol) {
		this.pol = pol;
	}

	/**
	 * @return Returns the damagedMailbags.
	 */
	public Collection<DamagedMailbagVO> getDamagedMailbags() {
		return damagedMailbags;
	}

	/**
	 * @param damagedMailbags
	 *            The damagedMailbags to set.
	 */
	public void setDamagedMailbags(Collection<DamagedMailbagVO> damagedMailbags) {
		this.damagedMailbags = damagedMailbags;
	}

	/**
	 * @return Returns the consignmentSequenceNumber.
	 */
	public int getConsignmentSequenceNumber() {
		return consignmentSequenceNumber;
	}

	/**
	 * @param consignmentSequenceNumber
	 *            The consignmentSequenceNumber to set.
	 */
	public void setConsignmentSequenceNumber(int consignmentSequenceNumber) {
		this.consignmentSequenceNumber = consignmentSequenceNumber;
	}

	/**
	 * @return Returns the paCode.
	 */
	public String getPaCode() {
		return paCode;
	}

	/**
	 * @param paCode
	 *            The paCode to set.
	 */
	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	/**
	 * @return Returns the opeartionalStatus.
	 */
	public String getOperationalStatus() {
		return operationalStatus;
	}

	/**
	 * @param opeartionalStatus
	 *            The opeartionalStatus to set.
	 */
	public void setOperationalStatus(String opeartionalStatus) {
		this.operationalStatus = opeartionalStatus;
	}

	/**
	 * @return Returns the carrierId.
	 */
	public int getCarrierId() {
		return carrierId;
	}

	/**
	 * @param carrierId
	 *            The carrierId to set.
	 */
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode
	 *            The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return Returns the despatchDate.
	 */
	public LocalDate getConsignmentDate() {
		return consignmentDate;
	}

	/**
	 * @param despatchDate
	 *            The despatchDate to set.
	 */
	public void setConsignmentDate(LocalDate despatchDate) {
		this.consignmentDate = despatchDate;
	}

	/**
	 * @return Returns the despatchSerialNumber.
	 */
	public String getDespatchSerialNumber() {
		return despatchSerialNumber;
	}

	/**
	 * @param despatchSerialNumber
	 *            The despatchSerialNumber to set.
	 */
	public void setDespatchSerialNumber(String despatchSerialNumber) {
		this.despatchSerialNumber = despatchSerialNumber;
	}

	/**
	 * @return Returns the doe.
	 */
	public String getDoe() {
		return doe;
	}

	/**
	 * @param doe
	 *            The doe to set.
	 */
	public void setDoe(String doe) {
		this.doe = doe;
	}

	/**
	 * @return Returns the flightNumber.
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * @param flightNumber
	 *            The flightNumber to set.
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/**
	 * @return Returns the flightSequenceNumber.
	 */
	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	/**
	 * @param flightSequenceNumber
	 *            The flightSequenceNumber to set.
	 */
	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	/**
	 * @return Returns the highestNumberedReceptacle.
	 */
	public String getHighestNumberedReceptacle() {
		return highestNumberedReceptacle;
	}

	/**
	 * @param highestNumberedReceptacle
	 *            The highestNumberedReceptacle to set.
	 */
	public void setHighestNumberedReceptacle(String highestNumberedReceptacle) {
		this.highestNumberedReceptacle = highestNumberedReceptacle;
	}

	/**
	 * @return Returns the latestStatus.
	 */
	public String getLatestStatus() {
		return latestStatus;
	}

	/**
	 * @param latestStatus
	 *            The latestStatus to set.
	 */
	public void setLatestStatus(String latestStatus) {
		this.latestStatus = latestStatus;
	}

	/**
	 * @return Returns the mailbagHistories.
	 */
	public Collection<MailbagHistoryVO> getMailbagHistories() {
		return mailbagHistories;
	}

	/**
	 * @param mailbagHistories
	 *            The mailbagHistories to set.
	 */
	public void setMailbagHistories(
			Collection<MailbagHistoryVO> mailbagHistories) {
		this.mailbagHistories = mailbagHistories;
	}

	/**
	 * @return Returns the mailbagId.
	 */
	public String getMailbagId() {
		return mailbagId;
	}

	/**
	 * @param mailbagId
	 *            The mailbagId to set.
	 */
	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	/**
	 * @return Returns the mailCategoryCode.
	 */
	public String getMailCategoryCode() {
		return mailCategoryCode;
	}

	/**
	 * @param mailCategoryCode
	 *            The mailCategoryCode to set.
	 */
	public void setMailCategoryCode(String mailCategoryCode) {
		this.mailCategoryCode = mailCategoryCode;
	}

	/**
	 * @return Returns the mailSubclass.
	 */
	public String getMailSubclass() {
		return mailSubclass;
	}

	/**
	 * @param mailSubclass
	 *            The mailSubclass to set.
	 */
	public void setMailSubclass(String mailSubclass) {
		this.mailSubclass = mailSubclass;
	}

	/**
	 * @return Returns the ooe.
	 */
	public String getOoe() {
		return ooe;
	}

	/**
	 * @param ooe
	 *            The ooe to set.
	 */
	public void setOoe(String ooe) {
		this.ooe = ooe;
	}

	/**
	 * @return Returns the pou.
	 */
	public String getPou() {
		return pou;
	}

	/**
	 * @param pou
	 *            The pou to set.
	 */
	public void setPou(String pou) {
		this.pou = pou;
	}

	/**
	 * @return Returns the receptacleSerialNumber.
	 */
	public String getReceptacleSerialNumber() {
		return receptacleSerialNumber;
	}

	/**
	 * @param receptacleSerialNumber
	 *            The receptacleSerialNumber to set.
	 */
	public void setReceptacleSerialNumber(String receptacleSerialNumber) {
		this.receptacleSerialNumber = receptacleSerialNumber;
	}

	/**
	 * @return Returns the registeredOrInsuredIndicator.
	 */
	public String getRegisteredOrInsuredIndicator() {
		return registeredOrInsuredIndicator;
	}

	/**
	 * @param registeredOrInsuredIndicator
	 *            The registeredOrInsuredIndicator to set.
	 */
	public void setRegisteredOrInsuredIndicator(
			String registeredOrInsuredIndicator) {
		this.registeredOrInsuredIndicator = registeredOrInsuredIndicator;
	}

	/**
	 * @return Returns the scannedDate.
	 */
	public LocalDate getScannedDate() {
		return scannedDate;
	}

	/**
	 * @param scannedDate
	 *            The scannedDate to set.
	 */
	public void setScannedDate(LocalDate scannedDate) {
		this.scannedDate = scannedDate;
	}

	/**
	 * @return Returns the scannedPort.
	 */
	public String getScannedPort() {
		return scannedPort;
	}

	/**
	 * @param scannedPort
	 *            The scannedPort to set.
	 */
	public void setScannedPort(String scannedPort) {
		this.scannedPort = scannedPort;
	}

	/**
	 * @return Returns the scannedUser.
	 */
	public String getScannedUser() {
		return scannedUser;
	}

	/**
	 * @param scannedUser
	 *            The scannedUser to set.
	 */
	public void setScannedUser(String scannedUser) {
		this.scannedUser = scannedUser;
	}

	/**
	 * @return Returns the segmentSerialNumber.
	 */
	public int getSegmentSerialNumber() {
		return segmentSerialNumber;
	}

	/**
	 * @param segmentSerialNumber
	 *            The segmentSerialNumber to set.
	 */
	public void setSegmentSerialNumber(int segmentSerialNumber) {
		this.segmentSerialNumber = segmentSerialNumber;
	}

	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}

	/**
	 * @param uldNumber
	 *            The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	/**
	 * @return Returns the weight.
	 */
	/*public double getWeight() {
		return weight;
	}

	*//**
	 * @param weight
	 *            The weight to set.
	 *//*
	public void setWeight(double weight) {
		this.weight = weight;
	}*/

	/**
	 * @return Returns the year.
	 */
	public int getYear() {
		return year;
	}
/**
 * 
 * 	Method		:	MailbagVO.getWeight
 *	Added by 	:	A-7371 on 08-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getWeight() {
		return weight;
	}
	/**
	 * 
	 * 	Method		:	MailbagVO.setWeight
	 *	Added by 	:	A-7371 on 08-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param weight 
	 *	Return type	: 	void
	 */
	public void setWeight(Measure weight) {
		this.weight = weight;
	}
	/**
	 * @param year
	 *            The year to set.
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return Returns the mailClass.
	 */
	public String getMailClass() {
		return mailClass;
	}

	/**
	 * @param mailClass
	 *            The mailClass to set.
	 */
	public void setMailClass(String mailClass) {
		this.mailClass = mailClass;
	}

	/**
	 * @return Returns the flightDate.
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}

	/**
	 * @param flightDate
	 *            The flightDate to set.
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	/**
	 * @return Returns the carrierCode.
	 */
	public String getCarrierCode() {
		return carrierCode;
	}

	/**
	 * @param carrierCode
	 *            The carrierCode to set.
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	/**
	 * @return Returns the operationalFlag.
	 */
	public String getOperationalFlag() {
		return this.operationalFlag;
	}

	/**
	 * @param operationalFlag
	 *            The operationalFlag to set.
	 */
	public void setOperationalFlag(String operationalFlag) {
		this.operationalFlag = operationalFlag;
	}

	/**
	 * @return Returns the acceptanceFlag.
	 */
	public String getAcceptanceFlag() {
		return acceptanceFlag;
	}

	/**
	 * @param acceptanceFlag
	 *            The acceptanceFlag to set.
	 */
	public void setAcceptanceFlag(String acceptanceFlag) {
		this.acceptanceFlag = acceptanceFlag;
	}

	/**
	 * @return Returns the containerType.
	 */
	public String getContainerType() {
		return containerType;
	}

	/**
	 * @param containerType
	 *            The containerType to set.
	 */
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	/**
	 * @return Returns the damageFlag.
	 */
	public String getDamageFlag() {
		return damageFlag;
	}

	/**
	 * @param damageFlag
	 *            The damageFlag to set.
	 */
	public void setDamageFlag(String damageFlag) {
		this.damageFlag = damageFlag;
	}

	/**
	 * @return Returns the strWeight.
	 *//*
	public String getStrWeight() {
		return this.strWeight;
	}

	*//**
	 * @param strWeight
	 *            The strWeight to set.
	 *//*
	public void setStrWeight(String strWeight) {
		this.strWeight = strWeight;
	}*/

	/**
	 * @return Returns the offloadedDescription.
	 */
	public String getOffloadedDescription() {
		return offloadedDescription;
	}
/**
 * 
 * 	Method		:	MailbagVO.getStrWeight
 *	Added by 	:	A-7371 on 04-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getStrWeight() {
		return strWeight;
	}
	/**
	 * 
	 * 	Method		:	MailbagVO.setStrWeight
	 *	Added by 	:	A-7371 on 04-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param strWeight 
	 *	Return type	: 	void
	 */
	public void setStrWeight(Measure strWeight) {
		this.strWeight = strWeight;
	}
	/**
	 * @param offloadedDescription
	 *            The offloadedDescription to set.
	 */
	public void setOffloadedDescription(String offloadedDescription) {
		this.offloadedDescription = offloadedDescription;
	}

	/**
	 * @return Returns the offloadedReason.
	 */
	public String getOffloadedReason() {
		return offloadedReason;
	}

	/**
	 * @param offloadedReason
	 *            The offloadedReason to set.
	 */
	public void setOffloadedReason(String offloadedReason) {
		this.offloadedReason = offloadedReason;
	}

	/**
	 * @return Returns the offloadedRemarks.
	 */
	public String getOffloadedRemarks() {
		return offloadedRemarks;
	}

	/**
	 * @param offloadedRemarks
	 *            The offloadedRemarks to set.
	 */
	public void setOffloadedRemarks(String offloadedRemarks) {
		this.offloadedRemarks = offloadedRemarks;
	}

	/**
	 * @return Returns the containerNumber.
	 */
	public String getContainerNumber() {
		return containerNumber;
	}

	/**
	 * @param containerNumber
	 *            The containerNumber to set.
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	/**
	 * @return Returns the isoffload.
	 */
	public boolean isIsoffload() {
		return isoffload;
	}

	/**
	 * @param isoffload
	 *            The isoffload to set.
	 */
	public void setIsoffload(boolean isoffload) {
		this.isoffload = isoffload;
	}

	/**
	 * @return Returns the consignmentNumber.
	 */
	public String getConsignmentNumber() {
		return consignmentNumber;
	}

	/**
	 * @param consignmentNumber
	 *            The consignmentNumber to set.
	 */
	public void setConsignmentNumber(String consignmentNumber) {
		this.consignmentNumber = consignmentNumber;
	}

	/**
	 * @return Returns the destinationCode.
	 */
	public String getFinalDestination() {
		return finalDestination;
	}

	/**
	 *
	 * @param finalDestination
	 */
	public void setFinalDestination(String finalDestination) {
		this.finalDestination = finalDestination;
	}

	/**
	 * @return Returns the arrivedFlag.
	 */
	public String getArrivedFlag() {
		return arrivedFlag;
	}

	/**
	 * @param arrivedFlag
	 *            The arrivedFlag to set.
	 */
	public void setArrivedFlag(String arrivedFlag) {
		this.arrivedFlag = arrivedFlag;
	}

	/**
	 * @return Returns the deliveredFlag.
	 */
	public String getDeliveredFlag() {
		return deliveredFlag;
	}

	/**
	 * @param deliveredFlag
	 *            The deliveredFlag to set.
	 */
	public void setDeliveredFlag(String deliveredFlag) {
		this.deliveredFlag = deliveredFlag;
	}

	/**
	 * @return Returns the ownAirlineCode.
	 */
	public String getOwnAirlineCode() {
		return ownAirlineCode;
	}

	/**
	 * @param ownAirlineCode
	 *            The ownAirlineCode to set.
	 */
	public void setOwnAirlineCode(String ownAirlineCode) {
		this.ownAirlineCode = ownAirlineCode;
	}

	/**
	 * @return Returns the legSerialNumber.
	 */
	public int getLegSerialNumber() {
		return this.legSerialNumber;
	}

	/**
	 * @param legSerialNumber The legSerialNumber to set.
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return Returns the count.
	 */
	public int getCount() {
		return this.count;
	}

	/**
	 * @param count The count to set.
	 */
	public void setCount(int count) {
		this.count = count;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorType() {
		return errorType;
	}

	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}

	/**
	 * @return Returns the acknowledge.
	 */
	public String getAcknowledge() {
		return this.acknowledge;
	}

	/**
	 * @param acknowledge The acknowledge to set.
	 */
	public void setAcknowledge(String acknowledge) {
		this.acknowledge = acknowledge;
	}

	/**
	 * @return Returns the transferFlag.
	 */
	public String getTransferFlag() {
		return transferFlag;
	}

	/**
	 * @param transferFlag The transferFlag to set.
	 */
	public void setTransferFlag(String transferFlag) {
		this.transferFlag = transferFlag;
	}

	/**
	 * @return Returns the containerFlag.
	 */
	public String getContainerFlag() {
		return this.containerFlag;
	}

	/**
	 * @param containerFlag The containerFlag to set.
	 */
	public void setContainerFlag(String containerFlag) {
		this.containerFlag = containerFlag;
	}

	/**
	 * @return Returns the resditEventString.
	 */
	public String getResditEventString() {
		return resditEventString;
	}

	/**
	 * @param resditEventString The resditEventString to set.
	 */
	public void setResditEventString(String resditEventString) {
		this.resditEventString = resditEventString;
	}

	/**
	 * @return Returns the transferFromCarrier.
	 */
	public String getTransferFromCarrier() {
		return transferFromCarrier;
	}

	/**
	 * @param transferFromCarrier The transferFromCarrier to set.
	 */
	public void setTransferFromCarrier(String transferFromCarrier) {
		this.transferFromCarrier = transferFromCarrier;
	}

	/**
	 * @return Returns the carditKey.
	 */
	public String getCarditKey() {
		return carditKey;
	}

	/**
	 * @param carditKey The carditKey to set.
	 */
	public void setCarditKey(String carditKey) {
		this.carditKey = carditKey;
	}

	/**
	 * @return Returns the carditSequenceNumber.
	 */
	public int getCarditSequenceNumber() {
		return carditSequenceNumber;
	}

	/**
	 * @param carditSequenceNumber The carditSequenceNumber to set.
	 */
	public void setCarditSequenceNumber(int carditSequenceNumber) {
		this.carditSequenceNumber = carditSequenceNumber;
	}

	/**
	 * @return Returns the stationCode.
	 */
	public String getStationCode() {
		return stationCode;
	}

	/**
	 * @param stationCode The stationCode to set.
	 */
	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	/**
	 * @return Returns the resditEventDate.
	 */
	public GMTDate getResditEventUTCDate() {
		return resditEventUTCDate;
	}

	/**
	 * @param resditEventDate The resditEventDate to set.
	 */
	public void setResditEventUTCDate(GMTDate resditEventDate) {
		this.resditEventUTCDate = resditEventDate;
	}

	/**
	 * @return Returns the resditEventPort.
	 */
	public String getResditEventPort() {
		return resditEventPort;
	}

	/**
	 * @param resditEventPort The resditEventPort to set.
	 */
	public void setResditEventPort(String resditEventPort) {
		this.resditEventPort = resditEventPort;
	}

	/**
	 * @return Returns the carditRecipientId.
	 */
	public String getCarditRecipientId() {
		return carditRecipientId;
	}

	/**
	 * @param carditRecipientId The carditRecipientId to set.
	 */
	public void setCarditRecipientId(String carditRecipientId) {
		this.carditRecipientId = carditRecipientId;
	}

	/**
	 * @return Returns the handoverPartner.
	 */
	public String getHandoverPartner() {
		return handoverPartner;
	}

	/**
	 * @param handoverPartner The handoverPartner to set.
	 */
	public void setHandoverPartner(String handoverPartner) {
		this.handoverPartner = handoverPartner;
	}

	/**
	 * @return Returns the resditEventSeqNum.
	 */
	public int getResditEventSeqNum() {
		return resditEventSeqNum;
	}

	/**
	 * @param resditEventSeqNum The resditEventSeqNum to set.
	 */
	public void setResditEventSeqNum(int resditEventSeqNum) {
		this.resditEventSeqNum = resditEventSeqNum;
	}

	public String getFromCarrier() {
		return fromCarrier;
	}

	public void setFromCarrier(String fromCarrier) {
		this.fromCarrier = fromCarrier;
	}

	/**
	 * @return Returns the containerTypeAtAirport.
	 */
	public String getContainerTypeAtAirport() {
		return containerTypeAtAirport;
	}

	/**
	 * @param containerTypeAtAirport The containerTypeAtAirport to set.
	 */
	public void setContainerTypeAtAirport(String containerTypeAtAirport) {
		this.containerTypeAtAirport = containerTypeAtAirport;
	}

	public String getInventoryContainer() {
		return inventoryContainer;
	}

	public void setInventoryContainer(String inventoryContainer) {
		this.inventoryContainer = inventoryContainer;
	}

	public String getInventoryContainerType() {
		return inventoryContainerType;
	}

	public void setInventoryContainerType(String inventoryContainerType) {
		this.inventoryContainerType = inventoryContainerType;
	}

	public String getFromContainer() {
		return fromContainer;
	}

	public void setFromContainer(String fromContainer) {
		this.fromContainer = fromContainer;
	}

	public String getFromContainerType() {
		return fromContainerType;
	}

	public void setFromContainerType(String fromContainerType) {
		this.fromContainerType = fromContainerType;
	}

	public String getFromFightNumber() {
		return fromFightNumber;
	}

	public void setFromFightNumber(String fromFightNumber) {
		this.fromFightNumber = fromFightNumber;
	}

	public LocalDate getFromFlightDate() {
		return fromFlightDate;
	}

	public void setFromFlightDate(LocalDate fromFlightDate) {
		this.fromFlightDate = fromFlightDate;
	}

	public long getFromFlightSequenceNumber() {
		return fromFlightSequenceNumber;
	}

	public void setFromFlightSequenceNumber(long fromFlightSequenceNumber) {
		this.fromFlightSequenceNumber = fromFlightSequenceNumber;
	}

	public int getFromSegmentSerialNumber() {
		return fromSegmentSerialNumber;
	}

	public void setFromSegmentSerialNumber(int fromSegmentSerialNumber) {
		this.fromSegmentSerialNumber = fromSegmentSerialNumber;
	}

	public String getControlDocumentNumber() {
		return controlDocumentNumber;
	}

	public void setControlDocumentNumber(String controlDocumentNumber) {
		this.controlDocumentNumber = controlDocumentNumber;
	}

	public String getDestCityDesc() {
		return destCityDesc;
	}

	public void setDestCityDesc(String destCityDesc) {
		this.destCityDesc = destCityDesc;
	}

	public String getDestCountryDesc() {
		return destCountryDesc;
	}

	public void setDestCountryDesc(String destCountryDesc) {
		this.destCountryDesc = destCountryDesc;
	}

	public String getOrgCityDesc() {
		return orgCityDesc;
	}

	public void setOrgCityDesc(String orgCityDesc) {
		this.orgCityDesc = orgCityDesc;
	}

	public String getSubClassDesc() {
		return subClassDesc;
	}

	public void setSubClassDesc(String subClassDesc) {
		this.subClassDesc = subClassDesc;
	}

	public String getCurrentDateStr() {
		return currentDateStr;
	}

	public void setCurrentDateStr(String currentDateStr) {
		this.currentDateStr = currentDateStr;
	}

	public String getPaBuiltFlag() {
		return paBuiltFlag;
	}

	public void setPaBuiltFlag(String paBuiltFlag) {
		this.paBuiltFlag = paBuiltFlag;
	}

	public int getAcceptedBags() {
		return acceptedBags;
	}

	public void setAcceptedBags(int acceptedBags) {
		this.acceptedBags = acceptedBags;
	}

	/*public double getAcceptedWeight() {
		return acceptedWeight;
	}

	public void setAcceptedWeight(double acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
*/
	/**
	 * @return the reassignFlag
	 */
	public String getReassignFlag() {
		return reassignFlag;
	}
/**
 * 
 * 	Method		:	MailbagVO.getAcceptedWeight
 *	Added by 	:	A-7371 on 18-Aug-2017
 * 	Used for 	:
 *	Parameters	:	@return 
 *	Return type	: 	Measure
 */
	public Measure getAcceptedWeight() {
		return acceptedWeight;
	}
	/**
	 * 
	 * 	Method		:	MailbagVO.setAcceptedWeight
	 *	Added by 	:	A-7371 on 18-Aug-2017
	 * 	Used for 	:
	 *	Parameters	:	@param acceptedWeight 
	 *	Return type	: 	void
	 */
	public void setAcceptedWeight(Measure acceptedWeight) {
		this.acceptedWeight = acceptedWeight;
	}
	/**
	 * @param reassignFlag the reassignFlag to set
	 */
	public void setReassignFlag(String reassignFlag) {
		this.reassignFlag = reassignFlag;
	}

	/*public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}*/

	/**
	 * @return the bookingLastUpdateTime
	 */
	public LocalDate getBookingLastUpdateTime() {
		return bookingLastUpdateTime;
	}

	/**
	 * @param bookingLastUpdateTime the bookingLastUpdateTime to set
	 */
	public void setBookingLastUpdateTime(LocalDate bookingLastUpdateTime) {
		this.bookingLastUpdateTime = bookingLastUpdateTime;
	}

	/**
	 * @return the ubrNumber
	 */
	public String getUbrNumber() {
		return ubrNumber;
	}

	/**
	 * @param ubrNumber the ubrNumber to set
	 */
	public void setUbrNumber(String ubrNumber) {
		this.ubrNumber = ubrNumber;
	}

	public String getReturnedDescription() {
		return returnedDescription;
	}

	public void setReturnedDescription(String returnedDescription) {
		this.returnedDescription = returnedDescription;
	}

	public String getReturnedReason() {
		return returnedReason;
	}

	public void setReturnedReason(String returnedReason) {
		this.returnedReason = returnedReason;
	}

	public String getReturnedRemarks() {
		return returnedRemarks;
	}

	public void setReturnedRemarks(String returnedRemarks) {
		this.returnedRemarks = returnedRemarks;
	}

	public String getPaDescription() {
		return paDescription;
	}

	public void setPaDescription(String paDescription) {
		this.paDescription = paDescription;
	}

	/**
	 * @return the bookingFlightDetailLastUpdTime
	 */
	public LocalDate getBookingFlightDetailLastUpdTime() {
		return bookingFlightDetailLastUpdTime;
	}

	/**
	 * @param bookingFlightDetailLastUpdTime the bookingFlightDetailLastUpdTime to set
	 */
	public void setBookingFlightDetailLastUpdTime(
			LocalDate bookingFlightDetailLastUpdTime) {
		this.bookingFlightDetailLastUpdTime = bookingFlightDetailLastUpdTime;
	}

	public String getIntact() {
		return intact;
	}

	public void setIntact(String intact) {
		this.intact = intact;
	}


	public String getActionMode() {
		return actionMode;
	}

	public void setActionMode(String actionMode) {
		this.actionMode = actionMode;
	}

	/**
	 * @return the displayLabel
	 */
	public String getDisplayLabel() {
		return displayLabel;
	}

	/**
	 * @param displayLabel the displayLabel to set
	 */
	public void setDisplayLabel(String displayLabel) {
		this.displayLabel = displayLabel;
	}
	/**
	 * @return the accepted
	 */
	public String getAccepted() {
		return accepted;
	}
	/**
	 * @param accepted the accepted to set
	 */
	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}
	/**
	 * @return the inList
	 */
	public String getInList() {
		return inList;
	}
	/**
	 * @param inList the inList to set
	 */
	public void setInList(String inList) {
		this.inList = inList;
	}
	/**
	 * @return the sealNumber
	 */
	public String getSealNumber() {
		return sealNumber;
	}
	/**
	 * @param sealNumber the sealNumber to set
	 */
	public void setSealNumber(String sealNumber) {
		this.sealNumber = sealNumber;
	}
	/**
	 * @return the arrivalSealNumber
	 */
	public String getArrivalSealNumber() {
		return arrivalSealNumber;
	}
	/**
	 * @param arrivalSealNumber the arrivalSealNumber to set
	 */
	public void setArrivalSealNumber(String arrivalSealNumber) {
		this.arrivalSealNumber = arrivalSealNumber;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	/**
	 * @return the isDeliveryNeeded
	 */
	public boolean isDeliveryNeeded() {
		return isDeliveryNeeded;
	}
	/**
	 * @param isDeliveryNeeded the isDeliveryNeeded to set
	 */
	public void setDeliveryNeeded(boolean isDeliveryNeeded) {
		this.isDeliveryNeeded = isDeliveryNeeded;
	}
	/**
	 * @return Returns the resditEventDate.
	 */
	public LocalDate getResditEventDate() {
		return resditEventDate;
	}
	/**
	 * @param resditEventDate The resditEventDate to set.
	 */
	public void setResditEventDate(LocalDate resditEventDate) {
		this.resditEventDate = resditEventDate;
	}
	/**
	 * @return Returns the mailOutCarrier.
	 */
	public String getMailOutCarrier() {
		return mailOutCarrier;
	}
	/**
	 * @param mailOutCarrier The mailOutCarrier to set.
	 */
	public void setMailOutCarrier(String mailOutCarrier) {
		this.mailOutCarrier = mailOutCarrier;
	}
	/**
	 * @param mailCompanyCode the mailCompanyCode to set
	 */
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	/**
	 * @return the mailCompanyCode
	 */
	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	/**
	 * 	Getter for mailStatus
	 *	Added by : a-4809 on Nov 18, 2014
	 * 	Used for :
	 */
	public String getMailStatus() {
		return mailStatus;
	}
	/**
	 *  @param mailStatus the mailStatus to set
	 * 	Setter for mailStatus
	 *	Added by : a-4809 on Nov 18, 2014
	 * 	Used for :
	 */
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	/**
	 * 	Getter for mailSource
	 *	Added by : A-4803 on 05-Nov-2014
	 * 	Used for :
	 */
	public String getMailSource() {
		return mailSource;
	}
	public String getScreen() {
		return screen;
	}
	public void setScreen(String screen) {
		this.screen = screen;
	}
	/**
	 *  @param mailSource the mailSource to set
	 * 	Setter for mailSource
	 *	Added by : A-4803 on 05-Nov-2014
	 * 	Used for :
	 */
	public void setMailSource(String mailSource) {
		this.mailSource = mailSource;
	}
	/**
	 * @return the fromCarrierId
	 */
	public int getFromCarrierId() {
		return fromCarrierId;
	}
	/**
	 * @param fromCarrierId the fromCarrierId to set
	 */
	public void setFromCarrierId(int fromCarrierId) {
		this.fromCarrierId = fromCarrierId;
	}
	public String getMraStatus() {
		return mraStatus;
	}
	public void setMraStatus(String mraStatus) {
		this.mraStatus = mraStatus;
	}
/**
 * Method to get the value of undoArrivalFlag
 * @return
 */
	public String getUndoArrivalFlag() {
		return undoArrivalFlag;
	}
	/**
	 * Method to set the value of undoArrivalFlag
	 * @param undoArrivalFlag
	 */
	public void setUndoArrivalFlag(String undoArrivalFlag) {
		this.undoArrivalFlag = undoArrivalFlag;
	}
	/**
	 * Method to get the value of latestScannedDate
	 * @return
	 */
	public LocalDate getLatestScannedDate() {
		return latestScannedDate;
	}
	/**
	 * Method to set the value of latestScannedDate
	 * @param latestScannedDate
	 */
	public void setLatestScannedDate(LocalDate latestScannedDate) {
		this.latestScannedDate = latestScannedDate;
	}
	/**
	 * @return the isDespatch
	 */
	public boolean isDespatch() {
		return isDespatch;
	}
	/**
	 * @param isDespatch the isDespatch to set
	 */
	public void setDespatch(boolean isDespatch) {
		this.isDespatch = isDespatch;
	}
	/**
	 * @param resditRequired the resditRequired to set
	 */
	public void setResditRequired(boolean resditRequired) {
		this.resditRequired = resditRequired;
	}
	/**
	 * @return the resditRequired
	 */
	public boolean isResditRequired() {
		return resditRequired;

	}
	/**
	 * @param mailConsignmentVOs the mailConsignmentVOs to set
	 */
	public void setMailConsignmentVO(MailInConsignmentVO mailConsignmentVO) {
		this.mailConsignmentVO = mailConsignmentVO;
	}
	/**
	 * @return the mailConsignmentVOs
	 */
	public MailInConsignmentVO getMailConsignmentVO() {
		return mailConsignmentVO;
	}
	/**
	 * @param newMailId the newMailId to set
	 */
	public void setNewMailId(String newMailId) {
		this.newMailId = newMailId;
	}
	/**
	 * @return the newMailId
	 */
	public String getNewMailId() {
		return newMailId;
	}
	/**
	 * @param mailUpdated the mailUpdated to set
	 */
	public void setMailUpdated(boolean mailUpdated) {
		this.mailUpdated = mailUpdated;
	}
	/**
	 * @return the mailUpdated
	 */
	public boolean isMailUpdated() {
		return mailUpdated;
	}
	/**
	 * @param fromReturnPopUp the fromReturnPopUp to set
	 */
	public void setFromReturnPopUp(boolean fromReturnPopUp) {
		this.fromReturnPopUp = fromReturnPopUp;
	}
	/**
	 * @return the fromReturnPopUp
	 */
	public boolean isFromReturnPopUp() {
		return fromReturnPopUp;
	}
	/**
	 * 	Getter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public LocalDate getReqDeliveryTime() {
		return reqDeliveryTime;
	}
	/**
	 *  @param reqDeliveryTime the reqDeliveryTime to set
	 * 	Setter for reqDeliveryTime 
	 *	Added by : A-6245 on 26-Jul-2017
	 * 	Used for :
	 */
	public void setReqDeliveryTime(LocalDate reqDeliveryTime) {
		this.reqDeliveryTime = reqDeliveryTime;
	}

	/**
	 * 	Getter for shipmentPrefix 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 *  @param shipmentPrefix the shipmentPrefix to set
	 * 	Setter for shipmentPrefix 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}
	/**
	 * 	Getter for documentNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public String getDocumentNumber() {
		return documentNumber;
	}
	/**
	 *  @param documentNumber the documentNumber to set
	 * 	Setter for documentNumber 
	 *	Added by : A-7531 on 03-Aug-2017
	 * 	Used for :
	 */
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	/**
	 * 	Getter for documentOwnerIdr 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public int getDocumentOwnerIdr() {
		return documentOwnerIdr;
	}
	/**
	 *  @param documentOwnerIdr the documentOwnerIdr to set
	 * 	Setter for documentOwnerIdr 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public void setDocumentOwnerIdr(int documentOwnerIdr) {
		this.documentOwnerIdr = documentOwnerIdr;
	}
	/**
	 * 	Getter for duplicateNumber 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public int getDuplicateNumber() {
		return duplicateNumber;
	}
	/**
	 *  @param duplicateNumber the duplicateNumber to set
	 * 	Setter for duplicateNumber 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public void setDuplicateNumber(int duplicateNumber) {
		this.duplicateNumber = duplicateNumber;
	}
	/**
	 * 	Getter for sequenceNumber 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public int getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 *  @param sequenceNumber the sequenceNumber to set
	 * 	Setter for sequenceNumber 
	 *	Added by : U-1267 on Nov 1, 2017
	 * 	Used for : ICRD-211205
	 */
	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * 
	 * @return displayUnit
	 */
	public String getDisplayUnit() {
		return displayUnit;
	}
	/**
	 * 
	 * @param displayUnit the displayUnit to set
	 */
	public void setDisplayUnit(String displayUnit) {
		this.displayUnit = displayUnit;
	}
	/**
	 * @return the mailServiceLevel
	 */
	public String getMailServiceLevel() {
		return mailServiceLevel;
	}
	/**
	 * @param mailServiceLevel the mailServiceLevel to set
	 */
	public void setMailServiceLevel(String mailServiceLevel) {
		this.mailServiceLevel = mailServiceLevel;
	}
	/**
	 * @author A-7371
	 * @return blockReceivedResdit
	 */
	public boolean isBlockReceivedResdit() {
		return blockReceivedResdit;
	}
	/**
	 *@author A-7371 
	 * @param blockReceivedResdit the blockReceivedResdit to set
	 */
	public void setBlockReceivedResdit(boolean blockReceivedResdit) {
		this.blockReceivedResdit = blockReceivedResdit;
	}
	/**
	 * @return the onTimeDelivery
	 */
	public String getOnTimeDelivery() {
		return onTimeDelivery;
	}
	/**
	 * @param onTimeDelivery the onTimeDelivery to set
	 */
	public void setOnTimeDelivery(String onTimeDelivery) {
		this.onTimeDelivery = onTimeDelivery;
	}
	
	/**
	 * @return origin 
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getUpliftAirport() {
		return upliftAirport;
	}
	public void setUpliftAirport(String upliftAirport) {
		this.upliftAirport = upliftAirport;
	}
	public String getMailOrigin() {
		return mailOrigin;
	}
	public void setMailOrigin(String mailOrigin) {
		this.mailOrigin = mailOrigin;
	}
	public String getMailDestination() {
		return mailDestination;
	}
	public void setMailDestination(String mailDestination) {
		this.mailDestination = mailDestination;
	}
	public String getRoutingAvlFlag() {
		return routingAvlFlag;
	}
	public void setRoutingAvlFlag(String routingAvlFlag) {
		this.routingAvlFlag = routingAvlFlag;
	}
	public String getPltEnableFlag() {
		return pltEnableFlag;
	}
	public void setPltEnableFlag(String pltEnableFlag) {
		this.pltEnableFlag = pltEnableFlag;
	}
	public String getTransitFlag() {
		return transitFlag;
	}
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
	
	/**
	 * 
	 * 	Method		:	MailbagVO.getMailbagSource
	 *	Added by 	:	A-4809 on Nov 22, 2018
	 * 	Used for 	: IASCB-137
	 *	Parameters	:	@return 
	 *	Return type	: 	String
	 */
	public String getMailbagSource() {
		return mailbagSource;
	}
	public void setMailbagSource(String mailbagSource) {
		this.mailbagSource = mailbagSource;
	}
	/**
	 * @return the rdtProcessing
	 */
	public boolean isRdtProcessing() {
		return rdtProcessing;
	}
	/**
	 * @param rdtProcessing the rdtProcessing to set
	 */
	public void setRdtProcessing(boolean rdtProcessing) {
		this.rdtProcessing = rdtProcessing;
	}
	/**
	 * @return the scanningWavedFlag
	 */
	public String getScanningWavedFlag() {
		return scanningWavedFlag;
	}
	/**
	 * @param scanningWavedFlag the scanningWavedFlag to set
	 */
	public void setScanningWavedFlag(String scanningWavedFlag) {
		this.scanningWavedFlag = scanningWavedFlag;
	}
	public String getMailbagDataSource() {
		return mailbagDataSource;
	}
	public void setMailbagDataSource(String mailbagDataSource) {
		this.mailbagDataSource = mailbagDataSource;
	}
	//Added By A-8672 for ICRD-255039 starts- commented as part of measure impl by a-7779 for ICRD-326752
	/*public double getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}*/
	//Added By A-8672 for ICRD-255039 ends
	public String getRoutingInfo() {
		return routingInfo;
	}
	public void setRoutingInfo(String routingInfo) {
		this.routingInfo = routingInfo;
	}
	/**
	 * @return the rfdFlag
	 */
	public String getRfdFlag() {
		return rfdFlag;
	}
	/**
	 * @param rfdFlag the rfdFlag to set
	 */
	public void setRfdFlag(String rfdFlag) {
		this.rfdFlag = rfdFlag;
	}
	/**
	 * @return the ghttim
	 */
	public LocalDate getGhttim() {
		return ghttim;
	}
	/**
	 * @param ghttim the ghttim to set
	 */
	public void setGhttim(LocalDate ghttim) {
		this.ghttim = ghttim;
	}
	public String getIsFromTruck() {
		return isFromTruck;
	}
	public void setIsFromTruck(String isFromTruck) {
		this.isFromTruck = isFromTruck;
	}
	public boolean isDeliveryStatusForAutoArrival() {
		return deliveryStatusForAutoArrival;
	}
	public void setDeliveryStatusForAutoArrival(boolean deliveryStatusForAutoArrival) {
		this.deliveryStatusForAutoArrival = deliveryStatusForAutoArrival;
	}
	
	 public Measure getActualWeight() {
		return actualWeight;
	}
	public void setActualWeight(Measure actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @author A-7371
	 * @return stdOrStaTruckFlag
	 */
	public String getStdOrStaTruckFlag() {
		return stdOrStaTruckFlag;
	}
	/**
	 * @author A-7371
	 * @param stdOrStaTruckFlag
	 */
	public void setStdOrStaTruckFlag(String stdOrStaTruckFlag) {
		this.stdOrStaTruckFlag = stdOrStaTruckFlag;
	}
	/**
	 * @return the vol
	 */
	public double getVol() {
		return vol;
	}
	/**
	 * @param vol the vol to set
	 */
	public void setVol(double vol) {
		this.vol = vol;
	}
	/**
	 * @return the volUnit
	 */
	public String getVolUnit() {
		return volUnit;
	}
	/**
	 * @param volUnit the volUnit to set
	 */
	public void setVolUnit(String volUnit) {
		this.volUnit = volUnit;
	}
	public String getActualWeightUnit() {
		return actualWeightUnit;
	}
	public void setActualWeightUnit(String actualWeightUnit) {
		this.actualWeightUnit = actualWeightUnit;
	}
	/**
	 * @return the transWindowEndTime
	 */
	public LocalDate getTransWindowEndTime() {
		return transWindowEndTime;
	}
	/**
	 * @param transWindowEndTime the transWindowEndTime to set
	 */
	public void setTransWindowEndTime(LocalDate transWindowEndTime) {
		this.transWindowEndTime = transWindowEndTime;
	}
	public String getAwbNumber() {
		return awbNumber;
	}
	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}
	public LocalDate getLatestAcceptanceTime() {
		return latestAcceptanceTime;
	}

	public void setLatestAcceptanceTime(LocalDate latestAcceptanceTime) {
		this.latestAcceptanceTime = latestAcceptanceTime;
	}
	/**
	 * @return the fromPanel
	 */
	public String getFromPanel() {
		return fromPanel;
	}
	/**
	 * @param fromPanel the fromPanel to set
	 */
	public void setFromPanel(String fromPanel) {
		this.fromPanel = fromPanel;
	}
	/**
	 * @return the isScanTimeEntered
	 */
	public boolean isScanTimeEntered() {
		return isScanTimeEntered;
	}
	/**
	 * @param isScanTimeEntered the isScanTimeEntered to set
	 */
	public void setScanTimeEntered(boolean isScanTimeEntered) {
		this.isScanTimeEntered = isScanTimeEntered;
	}
	public boolean isFlightClosureCheckNotNeeded() {
		return flightClosureCheckNotNeeded;
	}
	
	public void setFlightClosureCheckNotNeeded(boolean flightClosureCheckNotNeeded) {
		this.flightClosureCheckNotNeeded = flightClosureCheckNotNeeded;
	}
	public String getMessageVersion() {
		return messageVersion;
	}
	public void setMessageVersion(String messageVersion) {
		this.messageVersion = messageVersion;
	}
	/**
	 * 	Getter for isOffloadAndReassign 
	 *	Added by : A-8061 on 12-Aug-2020
	 * 	Used for :
	 */
	public boolean isOffloadAndReassign() {
		return isOffloadAndReassign;
	}
	/**
	 *  @param isOffloadAndReassign the isOffloadAndReassign to set
	 * 	Setter for isOffloadAndReassign 
	 *	Added by : A-8061 on 12-Aug-2020
	 * 	Used for :
	 */
	public void setOffloadAndReassign(boolean isOffloadAndReassign) {
		this.isOffloadAndReassign = isOffloadAndReassign;
	}
	public boolean isRemove() {
		return remove;
	}
	public void setRemove(boolean remove) {
		this.remove = remove;
	}
	public String getBillingStatus() {
		return billingStatus;
	}
	public void setBillingStatus(String billingStatus) {
		this.billingStatus = billingStatus;
	}
	public String getAcceptanceAirportCode() {
		return acceptanceAirportCode;
	}
	public void setAcceptanceAirportCode(String acceptanceAirportCode) {
		this.acceptanceAirportCode = acceptanceAirportCode;
	}
	public LocalDate getAcceptanceScanDate() {
		return acceptanceScanDate;
	}
	public void setAcceptanceScanDate(LocalDate acceptanceScanDate) {
		this.acceptanceScanDate = acceptanceScanDate;
	}
	public String getAcceptancePostalContainerNumber() {
		return acceptancePostalContainerNumber;
	}
	public void setAcceptancePostalContainerNumber(String acceptancePostalContainerNumber) {
		this.acceptancePostalContainerNumber = acceptancePostalContainerNumber;
	}
	public boolean isPaBuiltFlagUpdate() {
		return paBuiltFlagUpdate;
	}
	public void setPaBuiltFlagUpdate(boolean paBuiltFlagUpdate) {
		this.paBuiltFlagUpdate = paBuiltFlagUpdate;
	}
	public boolean isPaContainerNumberUpdate() {
		return paContainerNumberUpdate;
	}
	public void setPaContainerNumberUpdate(boolean paContainerNumberUpdate) {
		this.paContainerNumberUpdate = paContainerNumberUpdate;
	}
	public String getPreviousPostalContainerNumber() {
		return previousPostalContainerNumber;
	}
	public void setPreviousPostalContainerNumber(String previousPostalContainerNumber) {
		this.previousPostalContainerNumber = previousPostalContainerNumber;
	}  
    public boolean isNeedDestUpdOnDlv() {
		return needDestUpdOnDlv;
	}
	public void setNeedDestUpdOnDlv(boolean needDestUpdOnDlv) {
		this.needDestUpdOnDlv = needDestUpdOnDlv;
    }
	public boolean isOriginUpdate() {
		return originUpdate;
	}
	public void setOriginUpdate(boolean originUpdate) {
		this.originUpdate = originUpdate;
	}
	public boolean isDestinationUpdate() {
		return destinationUpdate;
	}
	public void setDestinationUpdate(boolean destinationUpdate) {
		this.destinationUpdate = destinationUpdate;
	}

	public String getTriggerForReImport() {
		return triggerForReImport;
	}
	public void setTriggerForReImport(String triggerForReImport) {
		this.triggerForReImport = triggerForReImport;
	}

	public String getContainerJourneyId() {
		return containerJourneyId;
	}
	public void setContainerJourneyId(String containerJourneyId) {
		this.containerJourneyId = containerJourneyId;
	}
	
	public Collection<ConsignmentScreeningVO> getConsignmentScreeningVO() {
		return consignmentScreeningVO;
	}

	public void setConsignmentScreeningVO(Collection<ConsignmentScreeningVO> consignmentScreeningVO) {
		this.consignmentScreeningVO = consignmentScreeningVO;
	}
	public boolean isSecurityDetailsPresent() {
		return securityDetailsPresent;
	}
	public void setSecurityDetailsPresent(boolean securityDetailsPresent) {
		this.securityDetailsPresent = securityDetailsPresent;
	}
		public String getSecurityStatusCode() {
		return securityStatusCode;
	}
	public void setSecurityStatusCode(String securityStatusCode) {
		this.securityStatusCode = securityStatusCode;
	}
	public String getTransactionLevel() {
		return transactionLevel;
	}
	public void setTransactionLevel(String transactionLevel) {
		this.transactionLevel = transactionLevel;
	}
	public boolean isImportMailbag() {
		return importMailbag;
	}
	public void setImportMailbag(boolean importMailbag) {
		this.importMailbag = importMailbag;
	}
	public long getMalseqnum() {
		return malseqnum;
	}
	public void setMalseqnum(long malseqnum) {
		this.malseqnum = malseqnum;
	}
	public boolean isFoundResditSent() {
		return foundResditSent;
	}
	public void setFoundResditSent(boolean foundResditSent) {
		this.foundResditSent = foundResditSent;
	}
	public String getMailboxId() {
		return mailboxId;
	}
	public void setMailboxId(String mailboxId) {
		this.mailboxId = mailboxId;
	}
	public boolean isGHAUser() {
		return isGHAUser;
	}
	public void setGHAUser(boolean isGHAUser) {
		this.isGHAUser = isGHAUser;
	}

	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}  
public String getScreeningFlag() {
		return screeningFlag;
	}
	public void setScreeningFlag(String screeningFlag) {
		this.screeningFlag = screeningFlag;
	}
}
