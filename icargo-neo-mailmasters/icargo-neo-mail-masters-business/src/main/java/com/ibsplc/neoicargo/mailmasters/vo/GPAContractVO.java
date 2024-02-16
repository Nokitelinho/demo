package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class GPAContractVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String companyCode;
	private String paCode;
	private String originAirports;
	private String destinationAirports;
	private String contractIDs;
	private String regions;
	private String cidFromDates;
	private String cidToDates;
	private ZonedDateTime lastUpdatedTime;
	private String lastUpdatedUser;
	private String conOperationFlags;
	private int sernum;
	private String amot;
	public static final String OPERATION_FLAG_INSERT = "I";
	public static final String OPERATION_FLAG_DELETE = "D";
	public static final String OPERATION_FLAG_UPDATE = "U";
}
