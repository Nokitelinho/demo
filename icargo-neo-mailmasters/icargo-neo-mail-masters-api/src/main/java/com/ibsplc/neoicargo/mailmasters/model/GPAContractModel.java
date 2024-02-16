package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class GPAContractModel extends BaseModel {
	private String companyCode;
	private String paCode;
	private String originAirports;
	private String destinationAirports;
	private String contractIDs;
	private String regions;
	private String cidFromDates;
	private String cidToDates;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String conOperationFlags;
	private int sernum;
	private String amot;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
