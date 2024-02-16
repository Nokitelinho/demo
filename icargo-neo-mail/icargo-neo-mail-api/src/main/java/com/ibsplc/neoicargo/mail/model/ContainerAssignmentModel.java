package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class ContainerAssignmentModel extends BaseModel {
	private String companyCode;
	private String airportCode;
	private String carrierCode;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private LocalDate flightDate;
	private String destination;
	private String flightStatus;
	private int legSerialNumber;
	private String containerNumber;
	private String acceptanceFlag;
	private String pou;
	private String containerType;
	private String arrivalFlag;
	private String transferFlag;
	private String journeyID;
	private String remark;
	private String uldFulIndFlag;
	private String releasedFlag;
	private String transactionCode;
	private String transitFlag;
	private String shipperBuiltCode;
	private String offloadStatus;
	private String poaFlag;
	private Measure actualWeight;
	private LocalDate assignedDate;
	private long uldReferenceNo;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
