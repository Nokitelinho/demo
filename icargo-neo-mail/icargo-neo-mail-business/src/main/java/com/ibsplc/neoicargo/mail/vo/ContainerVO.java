package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class ContainerVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String containerNumber;
	private String type;
	private String finalDestination;
	private ZonedDateTime assignedDate;
	private String assignedUser;
	private String onwardRoute;
	private String onwardFlights;
	private int bags;
	private Quantity weight;
	private String remarks;
	private String paBuiltFlag;
	private String operationFlag;
	private int carrierId;
	private String flightNumber;
	private long eventSequenceNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int legSerialNumber;
	private String companyCode;
	private String assignedPort;
	private String assignmentFlag;
	private String lastUpdateUser;
	private String acceptanceFlag;
	private boolean isReassign;
	private String carrierCode;
	private ZonedDateTime flightDate;
	private boolean isOffload;
	private String carditKey;
	private String consignmentDocumentNumber;
	private ZonedDateTime consignmentDate;
	private String carditRecipientId;
	private int resditEventSeqNum;
	private String resditEventPort;
	private String handedOverPartner;
	private boolean isFlightClosureCheckNeeded;
	private String offloadedReason;
	private String offloadedRemarks;
	private String offloadedDescription;
	private String pou;
	private String pol;
	private String warehouseCode;
	private String locationCode;
	/** 
	* This flag indicates whether this was an offloaded container
	*/
	private String offloadFlag;
	private String ownAirlineCode;
	private int ownAirlineId;
	private String arrivedStatus;
	private String transferFlag;
	private boolean isPreassignNeeded;
	private String paBuiltOpenedFlag;
	private boolean overrideUMSFlag;
	private ZonedDateTime operationTime;
	private String resditEventString;
	private int resditEventSequenceNumber;
	private String paCode;
	private ZonedDateTime resditEventUTCDate;
	private String eventCode;
	private ZonedDateTime eventTime;
	private String typeCode;
	private String equipmentQualifier;
	private Quantity containerWeight;
	private String measurementDimension;
	private String codeListResponsibleAgency;
	private String typeCodeListResponsibleAgency;
	private String containerSealNumber;
	private Quantity actualWeight;
	private String fromCarrier;
	private String fromFltNum;
	private ZonedDateTime fromFltDat;
	private String contentId;
	private String transactionCode;
	private boolean oflToRsnFlag;
	private String flightStatus;
	private boolean foundTransfer;
	private String screeningUser;
	private boolean fromDeviationList;
	private ZonedDateTime GHTtime;
	private boolean fromCarditList;
	private boolean fromWSCL;
	private boolean deleteEmptyContainer;
	private String retainFlag;
	private long frmFltSeqNum;
	private int frmSegSerNum;
	private boolean exportTransfer;
	private boolean handoverReceived;
	private boolean remove;
	private boolean mailbagPresent;
	private ZonedDateTime firstAssignDate;
	private String plannedFlightCarrierCode;
	private int plannedFlightNum;
	private ZonedDateTime plannedFlightDate;
	private String plannedFlightAndDate;
	private double expectedOrProvisionalCharge;
	private String position;
	private String messageVersion;
	private String weighingDeviceName;
	private ZonedDateTime weighingTime;
	private String transactionLevel;
	private Money provosionalCharge;
	private String baseCurrency;
	private String rateAvailforallMailbags;
	private String acceptedPort;
	private int receivedBags;
	private Quantity receivedWeight;
	/** 
	* module
	*/
	public static final String MODULE = "mail";
	/** 
	* submodule
	*/
	public static final String SUBMODULE = "operations";
	/** 
	* entity
	*/
	public static final String ENTITY = "mail.operations.Container";
	private ZonedDateTime lastUpdateTime;
	private ZonedDateTime uldLastUpdateTime;
	private String uldLastUpdateUser;
	/** 
	* ADDED BY RENO K ABRAHAM FOR SB ULD DeliveryStatus
	*/
	private String deliveredStatus;
	private Collection<OnwardRoutingVO> onwardRoutings;
	private Collection<BookingTimeVO> bookingTimeVOs;
	private String containerJnyID;
	/** 
	* shipperBuiltCode - Contains the Shipper Code(PA Code), who build the SB ULD.
	*/
	private String shipperBuiltCode;
	/** 
	* INTACT
	*/
	private String intact;
	/** 
	* Transit Flag
	*/
	private String transitFlag;
	/** 
	* ULD RELEASE FLAG This flag is used to determine whether  the uld is released from a segment. ULD will be released at the time of  Inbound Flight Closure.
	*/
	private String releasedFlag;
	private ZonedDateTime scannedDate;
	private Collection<String> offloadedInfo;
	private String subclassGroup;
	private int noOfDaysInCurrentLoc;
	private String mailSource;
	private String actualWeightUnit;
	private boolean uldTobarrow;
	private boolean barrowToUld;
	private boolean transferAudit;
	private String prevFlightPou;
	private String uldFulIndFlag;
	private long uldReferenceNo;
	private String expClsflg;
	private String hbaMarked;
	private String planStatus;
	private int offloadCount;
	private String source;

	/** 
	* @return Returns the reassignFlag.
	*/
	public boolean isReassignFlag() {
		return isReassign;
	}

	/** 
	* @param isReassign
	*/
	public void setReassignFlag(boolean isReassign) {
		this.isReassign = isReassign;
	}

	/** 
	* @param isFlightClosureCheckNeeded The isFlightClosureCheckNeeded to set.
	*/
	public void setFlightClosureCheckNeeded(boolean isFlightClosureCheckNeeded) {
		this.isFlightClosureCheckNeeded = isFlightClosureCheckNeeded;
	}

	/** 
	* @param isOffload The isOffload to set.
	*/
	public void setOffload(boolean isOffload) {
		this.isOffload = isOffload;
	}

	/** 
	* @param isPreassignNeeded The isPreassignNeeded to set.
	*/
	public void setPreassignNeeded(boolean isPreassignNeeded) {
		this.isPreassignNeeded = isPreassignNeeded;
	}

	/** 
	* @return Returns the uldLastUpdateTime.
	*/
	public ZonedDateTime getULDLastUpdateTime() {
		return uldLastUpdateTime;
	}

	/** 
	* @param uldLastUpdateTime The uldLastUpdateTime to set.
	*/
	public void setULDLastUpdateTime(ZonedDateTime uldLastUpdateTime) {
		this.uldLastUpdateTime = uldLastUpdateTime;
	}

	/** 
	* @return Returns the uldLastUpdateUser.
	*/
	public String getULDLastUpdateUser() {
		return uldLastUpdateUser;
	}

	/** 
	* @param uldLastUpdateUser The uldLastUpdateUser to set.
	*/
	public void setULDLastUpdateUser(String uldLastUpdateUser) {
		this.uldLastUpdateUser = uldLastUpdateUser;
	}

	private boolean isContainerDestChanged;

	public void setContainerDestChanged(boolean isContainerDestChanged) {
		this.isContainerDestChanged = isContainerDestChanged;
	}

	private boolean transStatus;

	public boolean isfromWSCL() {
		return fromWSCL;
	}
	@Override
	public String getBusinessId() {
		return this.containerNumber;
	}
}
