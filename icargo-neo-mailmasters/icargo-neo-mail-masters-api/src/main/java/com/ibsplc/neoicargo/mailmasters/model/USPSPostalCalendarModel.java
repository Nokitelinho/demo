package com.ibsplc.neoicargo.mailmasters.model;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import lombok.Getter;
import lombok.Setter;
import com.ibsplc.neoicargo.common.modal.BaseModel;

@Setter
@Getter
public class USPSPostalCalendarModel extends BaseModel {
	private String periods;
	private String fromDates;
	private String toDates;
	private String cgrSubmissions;
	private String cgrDeleteCutOffs;
	private String cutWeek1s;
	private String cutWeek2s;
	private String cutWeek3s;
	private String cutWeek4s;
	private String cutWeek5s;
	private String companyCode;
	private String paymEffectiveDates;
	private String incCalcDates;
	private String incEffectiveDates;
	private String incRecvDates;
	private String gpacod;
	private String filterCalender;
	private long calSeqnum;
	private LocalDate lstUpdTime;
	private String lstUpdUsr;
	private String calOperationFlags;
	private LocalDate periodFrom;
	private LocalDate periodTo;
	private LocalDate incCalcDate;
	private LocalDate clmGenarationDate;
	private String clmGenDate;
	private String operationFlag;
	private String triggerPoint;
	private boolean ignoreWarnings;
}
