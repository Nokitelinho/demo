package com.ibsplc.neoicargo.mail.vo;

import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author a-5991
 */
@Setter
@Getter
public class DSNEnquiryFilterVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String dsn;
	private String originCity;
	private String destinationCity;
	private String mailClass;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private ZonedDateTime fromDate;
	private ZonedDateTime toDate;
	private String consignmentNumber;
	private String carrierCode;
	private String flightNumber;
	private ZonedDateTime flightDate;
	private int carrierId;
	private String pltEnabledFlag;
	private String capNotAcpEnabledFlag;
	private String containerNumber;
	private String containerType;
	private String airportCode;
	private String paCode;
	private String operationType;
	private String transitFlag;
	private String doe;
	private String ooe;
	private String status;
	private int absoluteIndex;
	private String userIdentifier;
	private int totalRecords;
	private int pageNumber;
	private ZonedDateTime consignmentDate;
	private ZonedDateTime rdt;
	private String flightType;
	private int pageSize;
	private String shipmentPrefix;
	private String documentNumber;
	private String awbAttached;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	/** 
	* @author A-3227This is exclusively used for server side listing
	*/
	private ArrayList<Object> parameters;
}
