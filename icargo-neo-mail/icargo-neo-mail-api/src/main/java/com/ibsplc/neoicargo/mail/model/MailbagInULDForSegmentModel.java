package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailbagInULDForSegmentModel extends BaseModel {
	private String mailId;
	private String containerNumber;
	private String damageFlag;
	private LocalDate scannedDate;
	private Measure weight;
	private String mailClass;
	private String mailSubclass;
	private String mailCategoryCode;
	private String transferFromCarrier;
	private String sealNumber;
	private String acceptanceFlag;
	private String arrivalFlag;
	private String deliveredStatus;
	private String transferFlag;
	private String scannedPort;
	private String ubrNumber;
	private String controlDocumentNumber;
	private String transferToCarrier;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String arrivalSealNumber;
	private long mailSequenceNumber;
	private String recievedFlag;
	private String deliveredFlag;
	private Measure deliveredWeight;
	private LocalDate ghttim;
	private String flightNumber;
	private long flightSequenceNumber;
	private int segmentSerialNumber;
	private int carrierId;
	private String companyCode;
	private String assignedPort;
	private String containerType;
	private int legSerialNumber;
	private String paBuiltFlag;
	private String isFromTruck;
	private String mraStatus;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
