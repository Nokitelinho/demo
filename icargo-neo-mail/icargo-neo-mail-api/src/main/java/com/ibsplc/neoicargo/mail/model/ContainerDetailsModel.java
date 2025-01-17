package com.ibsplc.neoicargo.mail.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ContainerDetailsModel extends BaseModel {
	private String companyCode;
	private String containerNumber;
	private String pou;
	private String pol;
	private LocalDate assignmentDate;
	private String route;
	private int totalBags;
	private Measure totalWeight;
	private String wareHouse;
	private String location;
	private String remarks;
	private String undoArrivalFlag;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private String containerType;
	private Collection<DSNModel> dsnVOs;
	private Collection<MailbagModel> mailDetails;
	private Collection<MailbagModel> deletedMailDetails;
	private Collection<DespatchDetailsModel> desptachDetailsVOs;
	private Collection<OnwardRouteForSegmentModel> onwardRoutingForSegmentVOs;
	private Collection<MailSummaryModel> mailSummaryVOs;
	private LocalDate flightDate;
	private String carrierCode;
	private String destination;
	private String paBuiltFlag;
	private String acceptedFlag;
	private String offloadFlag;
	private String operationFlag;
	private int receivedBags;
	private Measure receivedWeight;
	private int deliveredBags;
	private Measure deliveredWeight;
	private Money provosionalCharge;
	private String baseCurrency;
	private String rateAvailforallMailbags;
	private String ownAirlineCode;
	private String arrivedStatus;
	private int legSerialNumber;
	private String transferFlag;
	private String containerOperationFlag;
	private boolean isReassignFlag;
	private boolean isMailUpdateFlag;
	private String transferFromCarrier;
	private String assignedUser;
	private boolean isPreassignFlag;
	private LocalDate lastUpdateTime;
	private LocalDate uldLastUpdateTime;
	private String flagPAULDResidit;
	private String deliveredStatus;
	private String onwardFlights;
	private String deliverPABuiltContainer;
	private String fromScreen;
	private String intact;
	private String transactionCode;
	private String bellyCartId;
	private String containerJnyId;
	private String paCode;
	private Page<MailDetailModel> mailDetailPageVOs;
	private String transitFlag;
	private String releasedFlag;
	private LocalDate scanDate;
	private String flightStatus;
	private Measure tareWeight;
	private boolean mailModifyflag;
	private boolean oflToRsnFlag;
	private String contour;
	private String airportCode;
	private String containerGroup;
	private int containercount;
	private int mailbagcount;
	private Measure mailbagwt;
	private Measure receptacleWeight;
	private int receptacleCount;
	private LocalDate assignedDate;
	private String assignedPort;
	private String contentId;
	private String containerPureTransfer;
	private Measure actualWeight;
	private boolean foundTransfer;
	private LocalDate minReqDelveryTime;
	private int totalRecordSize;
	private MailbagEnquiryFilterModel additionalFilters;
	private Collection<String> offloadedInfo;
	private int offloadCount;
	private boolean aquitULDFlag;
	private String activeTab;
	private boolean fromContainerTab;
	private String uldFulIndFlag;
	private long uldReferenceNo;
	private String fromDetachAWB;
	private String transistPort;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
