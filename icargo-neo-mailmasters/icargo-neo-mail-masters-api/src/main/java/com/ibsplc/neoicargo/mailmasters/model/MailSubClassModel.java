package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class MailSubClassModel extends BaseModel {
	private String code;
	private String description;
	private String operationFlag;
	private String companyCode;
	private String subClassGroup;
	private LocalDate lastUpdateTime;
	private String lastUpdateUser;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
