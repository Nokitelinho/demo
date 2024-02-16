package com.ibsplc.neoicargo.mail.vo;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class ConsignmentDocumentVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String companyCode;
	private String consignmentNumber;
	private String paCode;
	private int consignmentSequenceNumber;
	private ZonedDateTime consignmentDate;
	private String airportCode;
	private boolean isScanned;
	private String type;
	private String subType;
	private String operation;
	private Collection<RoutingInConsignmentVO> routingInConsignmentVOs;
	private Page<MailInConsignmentVO> mailInConsignmentVOs;
	private String lastUpdateUser;
	private ZonedDateTime lastUpdateTime;
	private String remarks;
	private int statedBags;
	private Quantity statedWeight = ContextUtil.getInstance().getBean(Quantities.class)
			.getQuantity(Quantities.MAIL_WGT, BigDecimal.valueOf(0.0));
	private String operationFlag;
	private Collection<MailInConsignmentVO> mailInConsignmentcollVOs;
	private String orgin;
	private String destination;
	private String despatchDate;
	private ArrayList<String> subsequentFlight;
	private String pou;
	private String orginCity;
	private String destinationCity;
	private String orginAirport;
	private String fltDate;
	private String dateOfDept;
	private String flight;
	private String carrierCode;
	private String destPort;
	private String reportType;
	private String mailCategory;
	private String categoryDescription;
	private Collection<MailInConsignmentVO> mailInConsignment;
	private String destinationOfficeOfExchange;
	private String originOfficeOfExchange;
	private int year;
	private String dsnNumber;
	private String uldNumber;
	private String mailSubClass;
	private String mailClass;
	private String operatorOrigin;
	private String operatorDestination;
	private String ooeDescription;
	private String doeDescription;
	private String consignmentPriority;
	private String transportationMeans;
	private String flightDetails;
	private String flightRoute;
	private ZonedDateTime firstFlightDepartureDate;
	private String airlineCode;
	private int totalSacks;
	private Quantity totalWeight;
	private Quantity totalLetterWeight;
	private Quantity totalParcelWeight;
	private String currency;
	private double rate;
	private String isUspsPerformed;
	private String mcaNumber;
	private Collection<ConsignmentScreeningVO> consignementScreeningVOs;
	private String securityStatusParty;
	private String securityStatusCode;
	private String additionalSecurityInfo;
	private ZonedDateTime securityStatusDate;
	private String applicableRegTransportDirection;
	private String applicableRegBorderAgencyAuthority;
	private String applicableRegReferenceID;
	private String countryCode;
	private String applicableRegFlag;
	private String consignmentOrigin;
	private String consigmentDest;
	private Map<String, Collection<OneTimeVO>> oneTimes;
	private String airlineName;
	private String flightDates;
	private int subClassLC;
	private int subClassCP;
	private int subClassEMS;
	private String airportOftransShipment;
	private String paName;
	private boolean priorityMalExists;
	private boolean airMalExists;
	private boolean priority;
	private boolean nonPriority;
	private boolean byAir;
	private boolean bySal;
	private boolean bySurface;
	private boolean priorityByAir;
	private boolean nonPrioritySurface;
	private boolean parcels;
	private boolean ems;
	private String tranAirportName;
	private String pouName;
	private int lineCount;
	private String shipmentPrefix;
	private String masterDocumentNumber;
	private String consignmentIssuerName;
	private String shipperUpuCode;
	private String consigneeUpuCode;
	private String originUpuCode;
	private String destinationUpuCode;
	private String consignmentMasterDocumentNumber;
	private Collection<ConsignmentDocumentVO> existingConsignmentDocumentVOs;
	private Collection<RoutingInConsignmentVO> acceptanceInfo;
	private Collection<RoutingInConsignmentVO> routingInfo;

	public void setScanned(boolean isScanned) {
		this.isScanned = isScanned;
	}

	private String securityReasonCode;
	private String agentType;
	private String agentID;
	private String expiryDate;
	private String isoCountryCode;
	private String csgIssuerName;
	private String mstAddionalSecurityInfo;
	private String source;
}