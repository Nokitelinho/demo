package com.ibsplc.icargo.business.mail.operations.vo;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	04-Jul-2018	:	Draft
 */
public class USPSPostalCalendarVO extends AbstractVO{
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
	//Added by A-8527 for ICRD-262451 starts
	private String clmGenDate;
	public String getCalOperationFlags() {
		return calOperationFlags;
	}
	public void setOperationFlag(String calOperationFlags) {
		this.calOperationFlags = calOperationFlags; 
	}
	public String getPeriods() {
		return periods;
	}
	public void setPeriods(String periods) {
		this.periods = periods;
	}
	public String getFromDates() {
		return fromDates;
	}
	public void setFromDates(String fromDates) {
		this.fromDates = fromDates;
	}
	public String getToDates() {
		return toDates;
	}
	public void setToDates(String toDates) {
		this.toDates = toDates;
	}
	public String getCgrSubmissions() {
		return cgrSubmissions;
	}
	public void setCgrSubmissions(String cgrSubmissions) {
		this.cgrSubmissions = cgrSubmissions;
	}
	public String getCgrDeleteCutOffs() {
		return cgrDeleteCutOffs;
	}
	public void setCgrDeleteCutOffs(String cgrDeleteCutOffs) {
		this.cgrDeleteCutOffs = cgrDeleteCutOffs;
	}
	public String getCutWeek1s() {
		return cutWeek1s;
	}
	public void setCutWeek1s(String cutWeek1s) {
		this.cutWeek1s = cutWeek1s;
	}
	public String getCutWeek2s() {
		return cutWeek2s;
	}
	public void setCutWeek2s(String cutWeek2s) {
		this.cutWeek2s = cutWeek2s;
	}
	public String getCutWeek3s() {
		return cutWeek3s;
	}
	public void setCutWeek3s(String cutWeek3s) {
		this.cutWeek3s = cutWeek3s;
	}
	public String getCutWeek4s() {
		return cutWeek4s;
	}
	public void setCutWeek4s(String cutWeek4s) {
		this.cutWeek4s = cutWeek4s;
	}
	public String getCutWeek5s() {
		return cutWeek5s;
	}
	public void setCutWeek5s(String cutWeek5s) {
		this.cutWeek5s = cutWeek5s;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getPaymEffectiveDates() {
		return paymEffectiveDates;
	}
	public void setPaymEffectiveDates(String paymEffectiveDates) {
		this.paymEffectiveDates = paymEffectiveDates;
	}
	public String getIncCalcDates() {
		return incCalcDates;
	}
	public void setIncCalcDates(String incCalcDates) {
		this.incCalcDates = incCalcDates;
	}
	public String getIncEffectiveDates() {
		return incEffectiveDates;
	}
	public void setIncEffectiveDates(String incEffectiveDates) {
		this.incEffectiveDates = incEffectiveDates;
	}
	public String getIncRecvDates() {
		return incRecvDates;
	}
	public void setIncRecvDates(String incRecvDates) {
		this.incRecvDates = incRecvDates;
	}
	public void setCalOperationFlags(String calOperationFlags) {
		this.calOperationFlags = calOperationFlags;
	}
	public String getGpacod() {
		return gpacod;
	}
	public void setGpacod(String gpacod) {
		this.gpacod = gpacod;
	}
	public String getFilterCalender() {
		return filterCalender;
	}
	public void setFilterCalender(String filterCalender) {
		this.filterCalender = filterCalender;
	}
	public long getCalSeqnum() {
		return calSeqnum;
	}
	public void setCalSeqnum(long calSeqnum) {
		this.calSeqnum = calSeqnum;
	}
	public LocalDate getLstUpdTime() { 
		return lstUpdTime;
	}
	public void setLstUpdTime(LocalDate lstUpdTime) {
		this.lstUpdTime = lstUpdTime;
	}
	public String getLstUpdUsr() {
		return lstUpdUsr;
	}
	public void setLstUpdUsr(String lstUpdUsr) {
		this.lstUpdUsr = lstUpdUsr;
	}
	public LocalDate getPeriodFrom() {
		return periodFrom;
	}
	public void setPeriodFrom(LocalDate periodFrom) {
		this.periodFrom = periodFrom;
	}
	public LocalDate getPeriodTo() {
		return periodTo;
	}
	public void setPeriodTo(LocalDate periodTo) {
		this.periodTo = periodTo;
	}
	public LocalDate getIncCalcDate() {
		return incCalcDate;
	}
	public void setIncCalcDate(LocalDate incCalcDate) {
		this.incCalcDate = incCalcDate;
	}
	public String getClmGenDate() {
		return clmGenDate;
	}
	public void setClmGenDate(String clmGenDate) {
		this.clmGenDate = clmGenDate;
	}
	public LocalDate getClmGenarationDate() {
		return clmGenarationDate;
	}
	public void setClmGenarationDate(LocalDate clmGenarationDate) {
		this.clmGenarationDate = clmGenarationDate;
	}	
}