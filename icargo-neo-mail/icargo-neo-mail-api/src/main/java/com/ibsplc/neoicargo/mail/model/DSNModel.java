package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNModel extends BaseModel {
	private String companyCode;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailCategoryCode;
	private String mailSubclass;
	private int bags;
	private Measure weight;
	private int shipmentCount;
	private Measure shipmentWeight;
	private Measure shipmentVolume;
	private int prevBagCount;
	private Measure prevBagWeight;
	private int statedBags;
	private Measure statedWeight;
	private int prevStatedBags;
	private Measure prevStatedWeight;
	private Collection<MailbagModel> mailbags;
	private Collection<DSNAtAirportModel> dsnAtAirports;
	private String pltEnableFlag;
	private String operationFlag;
	private String containerType;
	private String acceptanceFlag;
	private int segmentSerialNumber;
	private String pou;
	private String destination;
	private String carrierCode;
	private int deliveredBags;
	private Measure deliveredWeight;
	private int receivedBags;
	private Measure receivedWeight;
	private int prevDeliveredBags;
	private Measure prevDeliveredWeight;
	private int prevReceivedBags;
	private Measure prevReceivedWeight;
	private int documentOwnerIdentifier;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private String documentOwnerCode;
	private String shipmentCode;
	private String shipmentDescription;
	private String origin;
	private String transferFlag;
	private Collection<String> dsnContainers;
	private LocalDate dsnUldSegLastUpdateTime;
	private String mailbagId;
	private String upliftAirport;
	private String consignmentNumber;
	private String containerNumber;
	private String routingAvl;
	private String pol;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private double mailrate;
	private String currencyCode;
	private double chargeInBase;
	private double chargeInUSD;
	private String ubrNumber;
	private LocalDate bookingLastUpdateTime;
	private LocalDate bookingFlightDetailLastUpdTime;
	private Measure acceptedVolume;
	private Measure statedVolume;
	private String csgDocNum;
	private String paCode;
	private int csgSeqNum;
	private LocalDate consignmentDate;
	private LocalDate acceptedDate;
	private LocalDate receivedDate;
	private String currentPort;
	private int transferredPieces;
	private Measure transferredWeight;
	private String remarks;
	private long mailSequenceNumber;
	private String awbNumber;
	private LocalDate flightDate;
	private String acceptanceStatus;
	private LocalDate reqDeliveryTime;
	private String status;
	private String scannedUser;
	private String triggerPoint;
	private boolean ignoreWarnings;
}