package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MLDMasterModel extends BaseModel {
	private String barcodeValue;
	private String containerType;
	private String companyCode;
	private String mailSource;
	private long messageSequence;
	private int serialNumber;
	private String barcodeType;
	private String weightCode;
	private Measure weight;
	private String messageVersion;
	private String eventCOde;
	private String reasonCode;
	private LocalDate scanTime;
	private String senderAirport;
	private String receiverAirport;
	private String destAirport;
	private String expectedInd;
	private String uldNumber;
	private String uldWeightCode;
	private Measure uldWeight;
	private String lastUpdatedUser;
	private LocalDate lastUpdateTime;
	private String processStatus;
	private String messagingMode;
	private String addrCarrier;
	private long mailSequenceNumber;
	private boolean allocationRequired;
	private boolean recRequired;
	private boolean upliftedRequired;
	private boolean hNdRequired;
	private boolean dLVRequired;
	private boolean sTGRequired;
	private boolean nSTRequired;
	private boolean rCFRequired;
	private boolean tFDRequired;
	private boolean rCTRequired;
	private boolean rETRequired;
	private String transactionLevel;
	private LocalDate msgTimUTC;
	private MLDDetailModel mldDetailVO;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
