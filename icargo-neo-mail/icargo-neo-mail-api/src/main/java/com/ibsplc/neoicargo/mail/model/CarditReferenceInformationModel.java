package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditReferenceInformationModel extends BaseModel {
	private String transportContractReferenceQualifier;
	private String consignmentContractReferenceNumber;
	private String carditType;
	private String carditKey;
	private String refNumber;
	private String rffQualifier;
	private String orgin;
	private String destination;
	private String contractRef;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
