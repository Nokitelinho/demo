package com.ibsplc.neoicargo.mail.vo;

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
public class DSNVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	/** 
	* The ModuleName
	*/
	public static final String MODULE_NAME = "mail";
	/** 
	* The SubModuleName
	*/
	public static final String SUBMODULE_NAME = "operations";
	/** 
	* The EntityName
	*/
	public static final String ENTITY_NAME = "mail.operations.DSN";
	private String companyCode;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailCategoryCode;
	private String mailSubclass;
	private int bags;
	private Quantity weight;
	private int shipmentCount;
	private Quantity shipmentWeight;
	private Quantity shipmentVolume;
	private int prevBagCount;
	private Quantity prevBagWeight;
	private int statedBags;
	private Quantity statedWeight;
	private int prevStatedBags;
	private Quantity prevStatedWeight;
	private Collection<MailbagVO> mailbags;
	private Collection<DSNAtAirportVO> dsnAtAirports;
	private String pltEnableFlag;
	private String operationFlag;
	private String containerType;
	private String acceptanceFlag;
	private int segmentSerialNumber;
	private String pou;
	private String destination;
	private String carrierCode;
	private int deliveredBags;
	private Quantity deliveredWeight;
	private int receivedBags;
	private Quantity receivedWeight;
	private int prevDeliveredBags;
	private Quantity prevDeliveredWeight;
	private int prevReceivedBags;
	private Quantity prevReceivedWeight;
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
	private ZonedDateTime dsnUldSegLastUpdateTime;
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
	private ZonedDateTime bookingLastUpdateTime;
	private ZonedDateTime bookingFlightDetailLastUpdTime;
	private Quantity acceptedVolume;
	private Quantity statedVolume;
	private String csgDocNum;
	private String paCode;
	private int csgSeqNum;
	private ZonedDateTime consignmentDate;
	private ZonedDateTime acceptedDate;
	private ZonedDateTime receivedDate;
	private String currentPort;
	private int transferredPieces;
	private Quantity transferredWeight;
	private String remarks;
	private long mailSequenceNumber;
	private String awbNumber;
	private ZonedDateTime flightDate;
	private String acceptanceStatus;
	private ZonedDateTime reqDeliveryTime;
	private String status;
	private String scannedUser;
}
