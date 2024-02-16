package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditEnquiryFilterModel extends BaseModel {
	private String companyCode;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private int legSerialNumber;
	private long flightSequenceNumber;
	private LocalDate flightDate;
	private String searchMode;
	private String resdit;
	private String consignmentDocument;
	private LocalDate fromDate;
	private LocalDate toDate;
	private String ooe;
	private String doe;
	private String mailCategoryCode;
	private String mailClass;
	private String year;
	private String despatchSerialNumber;
	private String receptacleSerialNumber;
	private String flightType;
	private String flighDirection;
	private String pol;
	private String paoCode;
	private String mailbagId;
	private int absoluteIndex;
	private String mailSubclass;
	private String uldNumber;
	private LocalDate reqDeliveryTime;
	private int totalRecordsCount;
	private int pageNumber;
	private int pageSize;
	private boolean isPendingResditChecked;
	private LocalDate consignmentDate;
	private String shipmentPrefix;
	private String documentNumber;
	private String mailOrigin;
	private String maildestination;
	private String consignmentLevelAWbAttachRequired;
	private LocalDate transportServWindow;
	private String fromScreen;
	private int mailCount;
	private String notAccepted;
	private String mailStatus;
	private String isAWBAttached;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
