package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * @author A-6986
 */
@Setter
@Getter
public class IncentiveConfigurationVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	Collection<IncentiveConfigurationDetailVO> incentiveConfigurationDetailVOs;
	private String incOperationFlags;
	private String disIncOperationFlags;
	private ZonedDateTime lastUpdatedTime;
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
}
