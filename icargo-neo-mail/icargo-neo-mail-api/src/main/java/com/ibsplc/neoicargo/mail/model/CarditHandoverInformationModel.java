package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditHandoverInformationModel extends BaseModel {
	private String carditKey;
	private String carditType;
	private String handoverOriginLocationQualifier;
	private String handoverOriginLocationIdentifier;
	private String handoverOriginLocationName;
	private String handoverOriginCodeListAgency;
	private String handoverOriginCodeListQualifier;
	private String handoverDestnLocationQualifier;
	private String handoverDestnLocationIdentifier;
	private String handoverDestnLocationName;
	private String handoverDestnCodeListAgency;
	private String handoverDestnCodeListQualifier;
	private String transportStageQualifier;
	private String dateTimePeriodQualifier;
	private LocalDate originCutOffPeriod;
	private LocalDate destinationCutOffPeriod;
	private String dateTimeFormatQualifier;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
