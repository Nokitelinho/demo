package com.ibsplc.neoicargo.mail.vo;

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
public class MailbagHistoryVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailbagId;
	private String mailStatus;
	private int historySequenceNumber;
	private ZonedDateTime scanDate;
	private String scanUser;
	private String flightNumber;
	private int carrierId;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String scannedPort;
	private String containerNumber;
	private String containerType;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String pou;
	private ZonedDateTime utcScandate;
	private String mailCategoryCode;
	private String mailSubclass;
	private ZonedDateTime messageTime;
	private String paBuiltFlag;
	private String interchangeControlReference;
	private ZonedDateTime messageTimeUTC;
	private String mailSource;
	private ZonedDateTime eventDate;
	private ZonedDateTime utcEventDate;
	private String residitFailReasonCode;
	private String residitSend;
	private String eventCode;
	private String processedStatus;
	private String mailBoxId;
	private String carditKey;
	private String paCarrierCode;
	private int messageSequenceNumber;
	private ZonedDateTime secondFlightDate;
	private String rsn;
	private Quantity weight;
	private ZonedDateTime reqDeliveryTime;
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
	private Quantity actualWeight;
	private String mailSerLvl;
	private long mailSequenceNumber;
	private String consignmentNumber;
	private ZonedDateTime consignmentDate;
	private ZonedDateTime transportSrvWindow;
	private String poacod;
	private String screeningUser;
	private String storageUnit;
	private boolean fomDeviationList;
	private String messageVersion;
	private String billingStatus;
	private String acceptancePostalContainerNumber;

	public ZonedDateTime getUtcScanDate() {
		return utcScandate;
	}

	/** 
	* @param utcScandate
	*/
	public void setUtcScanDate(ZonedDateTime utcScandate) {
		this.utcScandate = utcScandate;
	}
}
