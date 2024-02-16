package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class DSNInConsignmentForContainerAtAirportModel extends BaseModel {
	private String consignmentNumber;
	private LocalDate consignmentDate;
	private int acceptedBags;
	private Measure acceptedWeight;
	private int statedBags;
	private Measure statedWeight;
	private String acceptedUser;
	private String mailClass;
	private LocalDate acceptedDate;
	private String paCode;
	private int sequenceNumber;
	private String offloadFlag;
	private Measure statedVolume;
	private Measure acceptedVolume;
	private String remarks;
	private String transactionCode;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
