package com.ibsplc.neoicargo.mail.vo;

import java.util.Collection;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-5991This Vo is returned to the client in MailAcceptance
 */
@Setter
@Getter
public class ContainerDetailsVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String containerNumber;
	private String pou;
	private String pol;
	private ZonedDateTime assignmentDate;
	private String route;
	private int totalBags;
	private Quantity totalWeight;
	private String wareHouse;
	private String location;
	private String remarks;
	private String undoArrivalFlag;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String containerType;
	private Collection<DSNVO> dsnVOs;
	private Collection<MailbagVO> mailDetails;
	private Collection<MailbagVO> deletedMailDetails;
	private Collection<DespatchDetailsVO> desptachDetailsVOs;
	private Collection<OnwardRouteForSegmentVO> onwardRoutingForSegmentVOs;
	private Collection<MailSummaryVO> mailSummaryVOs;
	private ZonedDateTime flightDate;
	private String carrierCode;
	private String destination;
	private String paBuiltFlag;
	private String acceptedFlag;
	private String offloadFlag;
	private String operationFlag;
	private int receivedBags;
	private Quantity receivedWeight;
	private int deliveredBags;
	private Quantity deliveredWeight;
	private Money provosionalCharge;
	private String baseCurrency;
	private String rateAvailforallMailbags;
	private String ownAirlineCode;
	private String arrivedStatus;
	private int legSerialNumber;
	private String transferFlag;
	private String containerOperationFlag;
	private Quantity netWeight;
	private boolean isReassignFlag;
	private boolean isMailUpdateFlag;

	public void setMailUpdateFlag(boolean isMailUpdateFlag) {
		this.isMailUpdateFlag = isMailUpdateFlag;
	}

	private String transferFromCarrier;
	private String assignedUser;
	private boolean isPreassignFlag;
	private ZonedDateTime lastUpdateTime;
	private ZonedDateTime uldLastUpdateTime;
	/** 
	* ADDED BY RENO K ABRAHAM FOR SB
	*/
	private String flagPAULDResidit;
	private String deliveredStatus;
	private String onwardFlights;
	private String deliverPABuiltContainer;
	private String fromScreen;
	/** 
	* ADDED BY RENO K ABRAHAM FOR INTACT
	*/
	private String intact;
	private String transactionCode;
	/** 
	* AirNZ - 985
	*/
	private String bellyCartId;
	private String containerJnyId;
	/** 
	* paCode - Contains the PA Code(Shipper Code), who build the SB ULD.
	*/
	private String paCode;
	private Page<MailDetailVO> mailDetailPageVOs;
	/** 
	* Transit Flag
	*/
	private String transitFlag;
	/** 
	* ULD RELEASE FLAG This flag is used to determine whether  the uld is released from a segment. ULD will be released at the time of  Inbound Flight Closure.
	*/
	private String releasedFlag;
	private ZonedDateTime scanDate;
	private String flightStatus;
	private Quantity tareWeight;
	private boolean mailModifyflag;
	private boolean oflToRsnFlag;
	private String contour;
	private String airportCode;
	private String containerGroup;
	private int containercount;
	private int mailbagcount;
	private Quantity mailbagwt;
	private Quantity receptacleWeight;
	private int receptacleCount;
	private ZonedDateTime assignedDate;
	private String assignedPort;
	private String contentId;
	private String containerPureTransfer;
	private Quantity actualWeight;
	private boolean foundTransfer;
	private ZonedDateTime minReqDelveryTime;
	private int totalRecordSize;
	private MailbagEnquiryFilterVO additionalFilters;
	private Collection<String> offloadedInfo;
	private int offloadCount;
	private boolean aquitULDFlag;
	private String activeTab;
	private boolean fromContainerTab;
	private String uldFulIndFlag;
	private long uldReferenceNo;
	private String fromDetachAWB;
	private String transistPort;

	/** 
	* @param isReassignFlag The isReassignFlag to set.
	*/
	public void setReassignFlag(boolean isReassignFlag) {
		this.isReassignFlag = isReassignFlag;
	}

	/** 
	* @param isPreassignFlag The isPreassignFlag to set.
	*/
	public void setPreassignFlag(boolean isPreassignFlag) {
		this.isPreassignFlag = isPreassignFlag;
	}

	public String getTransactonCode() {
		return transactionCode;
	}
}
