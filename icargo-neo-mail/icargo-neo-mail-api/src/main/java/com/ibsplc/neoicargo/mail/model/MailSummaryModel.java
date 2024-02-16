package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailSummaryModel extends BaseModel {
	private String destination;
	private String mailCategory;
	private String originPA;
	private String DestinationPA;
	private int bagCount;
	private Measure totalWeight;
	private String origin;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
