package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailHandoverFilterModel extends BaseModel {
	private String companyCode;
	private String gpaCode;
	private String airportCode;
	private String mailClass;
	private String exchangeOffice;
	private String mailSubClass;
	private int recordsPerPage;
	private int totalRecords;
	private int pageNumber;
	private int defaultPageSize;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
