package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991 Despatch Details entered for Acceptance
 */
@Setter
@Getter
public class DespatchDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String dsn;
	private int acceptedBags;
	private Quantity acceptedWeight;
	private String mailClass;
	private String mailCategoryCode;
	private String containerType;
	private String mailSubclass;
	private String acceptanceFlag;
	private String containerNumber;
	private String uldNumber;
	private String pou;
	private String destination;
	private boolean isOffload;
	private String ownAirlineCode;
	private int legSerialNumber;
	/** 
	* Accepted Date and time
	*/
	private ZonedDateTime acceptedDate;
	private String acceptedUser;
	private String pltEnabledFlag;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String operationalFlag;
	private String consignmentNumber;
	private ZonedDateTime consignmentDate;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private int statedBags;
	private Quantity statedWeight;
	private int year;
	private int prevAcceptedBags;
	private Quantity prevAcceptedWeight;
	/** 
	* Added By Karthick V as the part of the NCA Mail Tracking Cr 
	*/
	private String containerForInventory;
	private String containerTypeAtAirport;
	private int prevStatedBags;
	private Quantity prevStatedWeight;
	private ZonedDateTime receivedDate;
	private String offloadedReason;
	private String offloadedRemarks;
	private String offloadedDescription;
	private String airportCode;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private String paCode;
	private int consignmentSequenceNumber;
	private int deliveredBags;
	private Quantity deliveredWeight;
	private int receivedBags;
	private Quantity receivedWeight;
	private int prevDeliveredBags;
	private Quantity prevDeliveredWeight;
	private int prevReceivedBags;
	private Quantity prevReceivedWeight;
	private String offloadFlag;
	private String operationType;
	private String transferFlag;
	private String paBuiltFlag;
	private Collection<String> containers;
	private Quantity statedVolume;
	private String capNotAcceptedStatus;
	private String latestStatus;
	private int offloadedBags;
	private Quantity offloadedWeight;
	private boolean delivered;
	/** 
	* @author a-2107For BUGID :- 28977
	*/
	private Quantity acceptedVolume;
	private String transactionCode;
	/** 
	* These properties are used in ReassignDSN client, to show the Available Bag Count and weight.
	*/
	private int acceptedPcsToDisplay;
	private Quantity acceptedWgtToDisplay;
	/** 
	* Indicates that this despatch was newly arrived
	*/
	private boolean isNewInbound;
	private String ubrNumber;
	private ZonedDateTime bookingLastUpdateTime;
	private ZonedDateTime bookingFlightDetailLastUpdTime;
	/** 
	* Added for HHT if exception occurs to set error Code and err description
	*/
	private String errorType;
	private String errorDescription;
	/** 
	* This is to For HHT to ack of
	*/
	private String acknowledge;
	private String displayLabel;
	private int transferredPieces;
	private Quantity transferredWeight;
	private boolean istransfermail;
	private int alreadyTransferredPieces;
	private Quantity alreadyTransferredWeight;
	private boolean isDomesticTransfer;
	private String remarks;
	private String csgOrigin;
	private String csgDestination;

	/** 
	* @param isDomesticTransfer the isDomesticTransfer to set
	*/
	public void setDomesticTransfer(boolean isDomesticTransfer) {
		this.isDomesticTransfer = isDomesticTransfer;
	}

	/** 
	* @param isOffload The isOffload to set.
	*/
	public void setOffload(boolean isOffload) {
		this.isOffload = isOffload;
	}

	/** 
	* @param isNewInbound The isNewInbound to set.
	*/
	public void setNewInbound(boolean isNewInbound) {
		this.isNewInbound = isNewInbound;
	}
}
