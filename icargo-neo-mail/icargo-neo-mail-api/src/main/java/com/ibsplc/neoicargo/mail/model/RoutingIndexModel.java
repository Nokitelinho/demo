package com.ibsplc.neoicargo.mail.model;

import java.util.Calendar;
import java.util.Collection;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class RoutingIndexModel extends BaseModel {
	private String clsOutTim;
	private String companyCode;
	private int dayCount;
	private String contractType;
	private String destination;
	private int equitableTender;
	private String finalDlvTime;
	private String hazardousIndicator;
	private Calendar lastUpdateTime;
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
	private LocalDate scannedDate;
	private Collection<RoutingIndexLegModel> routingIndexLegVO;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
