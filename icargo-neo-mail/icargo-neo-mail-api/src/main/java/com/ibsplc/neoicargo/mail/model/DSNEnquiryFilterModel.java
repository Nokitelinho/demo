package com.ibsplc.neoicargo.mail.model;

import java.util.ArrayList;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNEnquiryFilterModel extends BaseModel {
	private String companyCode;
	private String dsn;
	private String originCity;
	private String destinationCity;
	private String mailClass;
	private String mailCategoryCode;
	private String mailSubClass;
	private int year;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String consignmentNumber;
	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
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
	private LocalDate consignmentDate;
	private LocalDate rdt;
	private String flightType;
	private int pageSize;
	private String shipmentPrefix;
	private String documentNumber;
	private String awbAttached;
	private String originOfficeOfExchange;
	private String destinationOfficeOfExchange;
	private ArrayList<Object> parameters;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
