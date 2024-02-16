package com.ibsplc.neoicargo.mailmasters.vo;

import lombok.Getter;
import lombok.Setter;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.orchestration.vo.AbstractVO;

/** 
 * Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO.java Version		:	Name	:	Date			:	Updation --------------------------------------------------- 0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
@Setter
@Getter
public class USPSPostalCalendarVO extends AbstractVO {
	private boolean ignoreWarnings;
	private String triggerPoint;
	private String operationFlag;
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
	private ZonedDateTime lstUpdTime;
	private String lstUpdUsr;
	private String calOperationFlags;
	private ZonedDateTime periodFrom;
	private ZonedDateTime periodTo;
	private ZonedDateTime incCalcDate;
	private ZonedDateTime clmGenarationDate;
	private String clmGenDate;

	public void setOperationFlag(String calOperationFlags) {
		this.calOperationFlags = calOperationFlags;
	}
	@Override
	public String getBusinessId() {
		return this.gpacod+","+this.periods;
	}
}
