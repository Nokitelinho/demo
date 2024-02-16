package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class AWBDetailModel extends BaseModel {
	private String companyCode;
	private int ownerId;
	private String masterDocumentNumber;
	private int duplicateNumber;
	private int sequenceNumber;
	private int statedPieces;
	private Measure statedWeight;
	private Measure statedVolume;
	private String shipmentDescription;
	private String origin;
	private String destination;
	private String operationFlag;
	private String shipmentPrefix;
	private String statedWeightCode;
	private String agentCode;
	private String documentSubType;
	private String ownerCode;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
