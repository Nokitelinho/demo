package com.ibsplc.neoicargo.mail.vo;

import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-3429
 */
@Setter
@Getter
public class FlightLoadPlanContainerVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
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
	public static final String OPERATION_FLAG_INSERT = "I";
	public static final String OPERATION_FLAG_DELETE = "D";
	public static final String OPERATION_FLAG_UPDATE = "U";
	private String uldFullIndicator;
	private String subClassGroup;
}
