package com.ibsplc.neoicargo.mail.model;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class CarditContainerModel extends BaseModel {
	private String equipmentQualifier;
	private String codeListResponsibleAgency;
	private String containerType;
	private String typeCodeListResponsibleAgency;
	private String measurementDimension;
	private Measure containerWeight;
	private String sealNumber;
	private String containerNumber;
	private String containerJourneyIdentifier;
	private String carditType;
	private String lastUpdateUser;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
