package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class FlightLoadPlanContainerModel extends BaseModel {
	private String companyCode;
	private String containerNumber;
	private int carrierId;
	private String flightNumber;
	private long flightSequenceNumber;
	private String segOrigin;
	private String segDestination;
	private int loadPlanVersion;
	private long uldReferenceNo;
	private String containerType;
	private int segSerialNumber;
	private int plannedPieces;
	private double plannedWeight;
	private String operationFlag;
	private String planStatus;
	private String uldNumber;
	private String plannedPosition;
	private double plannedVolume;
	private String lastUpdatedUser;
	private String uldFullIndicator;
	private String subClassGroup;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
