package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class GPAContractFilterModel extends BaseModel {
	private String companyCode;
	private String paCode;
	private String origin;
	private String destination;
	private String contractID;
	private String region;
	private int recordsPerPage;
	private int totalRecords;
	private int pageNumber;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
