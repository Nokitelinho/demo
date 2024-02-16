package com.ibsplc.neoicargo.mailmasters.model;

import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class IncentiveConfigurationModel extends BaseModel {
	private String companyCode;
	private String paCode;
	private int incentiveSerialNumber;
	private String incentiveFlag;
	private String serviceRespFlag;
	private String formula;
	private String basis;
	private Double incPercentage;
	private String incValidFrom;
	private String incValidTo;
	private Double disIncPercentage;
	private String disIncValidFrom;
	private String disIncValidTo;
	private Collection<IncentiveConfigurationDetailModel> incentiveConfigurationDetailVOs;
	private String incOperationFlags;
	private String disIncOperationFlags;
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	private String disIncSrvOperationFlags;
	private String disIncNonSrvOperationFlags;
	private String disIncBothSrvOperationFlags;
	private String srvFormula;
	private String srvBasis;
	private Double disIncSrvPercentage;
	private String disIncSrvValidFrom;
	private String disIncSrvValidTo;
	private String nonSrvFormula;
	private String nonSrvBasis;
	private Double disIncNonSrvPercentage;
	private String disIncNonSrvValidFrom;
	private String disIncNonSrvValidTo;
	private String bothSrvFormula;
	private String bothSrvBasis;
	private Double disIncBothSrvPercentage;
	private String disIncBothSrvValidFrom;
	private String disIncBothSrvValidTo;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
