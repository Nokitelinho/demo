package com.ibsplc.neoicargo.mailmasters.vo;

import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.RoutingIndexVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-7531	:	31-Aug-2018	:	Draft
 */
@Setter
@Getter
public class RoutingIndexVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
	private String clsOutTim;
	private String companyCode;
	private int dayCount;
	private String contractType;
	private String destination;
	private int equitableTender;
	private String finalDlvTime;
	private String hazardousIndicator;
	private LocalDateTime lastUpdateTime;
	private String lastUpdateUser;
	private String mailClass;
	private int mailCost;
	private double maxWgt;
	private double minWgt;
	private int onetimePercent;
	private String origin;
	private String perishableIndicator;
	private String plannedDisDate;
	private String plannedEffectiveDate;
	private String planRouteGenInd;
	private String planRouteScnInd;
	private String priorityCode;
	private String routingIndex;
	private int routingSeqNum;
	private int tagIndex;
	private String wgtUnit;
	private int frequency;
	private ZonedDateTime scannedDate;
	private Collection<RoutingIndexLegVO> routingIndexLegVO;
}
