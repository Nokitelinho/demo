package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagHistoryModel extends BaseModel {
	private String companyCode;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailbagId;
	private String mailStatus;
	private int historySequenceNumber;
	private LocalDate scanDate;
	private String scanUser;
	private String flightNumber;
	private int carrierId;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String scannedPort;
	private String containerNumber;
	private String containerType;
	private String carrierCode;
	private LocalDate flightDate;
	private String pou;
	private LocalDate utcScandate;
	private String mailCategoryCode;
	private String mailSubclass;
	private LocalDate messageTime;
	private String paBuiltFlag;
	private String interchangeControlReference;
	private LocalDate messageTimeUTC;
	private String mailSource;
	private LocalDate eventDate;
	private LocalDate utcEventDate;
	private String residitFailReasonCode;
	private String residitSend;
	private String eventCode;
	private String processedStatus;
	private String mailBoxId;
	private String carditKey;
	private String paCarrierCode;
	private int messageSequenceNumber;
	private LocalDate secondFlightDate;
	private String rsn;
	private Measure weight;
	private LocalDate reqDeliveryTime;
	private String mailRemarks;
	private String masterDocumentNumber;
	private String malClass;
	private String malType;
	private String origin;
	private String destination;
	private int pieces;
	private String airportCode;
	private String deliveryStatus;
	private String firstFlight;
	private String secondFlight;
	private String onTimeDelivery;
	private String additionalInfo;
	private Measure actualWeight;
	private String mailSerLvl;
	private long mailSequenceNumber;
	private String consignmentNumber;
	private LocalDate consignmentDate;
	private LocalDate transportSrvWindow;
	private String poacod;
	private String screeningUser;
	private String storageUnit;
	private boolean fomDeviationList;
	private String messageVersion;
	private String billingStatus;
	private String acceptancePostalContainerNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
