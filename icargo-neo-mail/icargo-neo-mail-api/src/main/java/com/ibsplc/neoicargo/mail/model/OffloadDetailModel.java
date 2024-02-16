package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class OffloadDetailModel extends BaseModel {
	private String companyCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int offloadSerialNumber;
	private LocalDate offloadedDate;
	private String offloadUser;
	private int offloadedBags;
	private Measure offloadedWeight;
	private String offloadRemarks;
	private String offloadReasonCode;
	private String carrierCode;
	private String offloadDescription;
	private String offloadType;
	private String dsn;
	private String originExchangeOffice;
	private String destinationExchangeOffice;
	private String mailClass;
	private int year;
	private String mailId;
	private String containerNumber;
	private String airportCode;
	private String mailSubclass;
	private String mailCategoryCode;
	private long mailSequenceNumber;
	private String offloadedStation;
	private LocalDate flightDate;
	private String billingBasis;
	private int csgSequenceNumber;
	private String csgDocumentNumber;
	private String poaCode;
	private String irregularityStatus;
	private int rescheduledFlightCarrierId;
	private String rescheduledFlightNo;
	private long rescheduledFlightSeqNo;
	private LocalDate rescheduledFlightDate;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
