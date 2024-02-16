
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationSurchargeDetailsVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7371	:	11-May-2018	:	Draft
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
public class AWMProrationSurchargeDetailsVO  extends AbstractVO {
	
	private String companyCode;
	private String sector;
	private String chargeHead;
	private Money surProrationAmtInUsd;
	private Money surProrationAmtInSdr;
	private Money surProrationAmtInBaseCurr;
	private Money surProratedAmtInCtrCur;
	private Money subTotalSurProrationAmtInUsd;
	private Money subTotalSurProrationAmtInSdr;
	private Money subTotalSurProrationAmtInBaseCurr;
	private Money subTotalSurProrationAmtInCtrCur;
	private String currency;

	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	public String getChargeHead() {
		return chargeHead;
	}
	public void setChargeHead(String chargeHead) {
		this.chargeHead = chargeHead;
	}
	public Money getSurProrationAmtInUsd() {
		return surProrationAmtInUsd;
	}
	public void setSurProrationAmtInUsd(Money surProrationAmtInUsd) {
		this.surProrationAmtInUsd = surProrationAmtInUsd;
	}
	public Money getSurProrationAmtInSdr() {
		return surProrationAmtInSdr;
	}
	public void setSurProrationAmtInSdr(Money surProrationAmtInSdr) {
		this.surProrationAmtInSdr = surProrationAmtInSdr;
	}
	public Money getSurProrationAmtInBaseCurr() {
		return surProrationAmtInBaseCurr;
	}
	public void setSurProrationAmtInBaseCurr(Money surProrationAmtInBaseCurr) {
		this.surProrationAmtInBaseCurr = surProrationAmtInBaseCurr;
	}
	public Money getSurProratedAmtInCtrCur() {
		return surProratedAmtInCtrCur;
	}
	public void setSurProratedAmtInCtrCur(Money surProratedAmtInCtrCur) {
		this.surProratedAmtInCtrCur = surProratedAmtInCtrCur;
	}
	public Money getSubTotalSurProrationAmtInUsd() {
		return subTotalSurProrationAmtInUsd;
	}
	public void setSubTotalSurProrationAmtInUsd(Money subTotalSurProrationAmtInUsd) {
		this.subTotalSurProrationAmtInUsd = subTotalSurProrationAmtInUsd;
	}
	public Money getSubTotalSurProrationAmtInSdr() {
		return subTotalSurProrationAmtInSdr;
	}
	public void setSubTotalSurProrationAmtInSdr(Money subTotalSurProrationAmtInSdr) {
		this.subTotalSurProrationAmtInSdr = subTotalSurProrationAmtInSdr;
	}
	public Money getSubTotalSurProrationAmtInBaseCurr() {
		return subTotalSurProrationAmtInBaseCurr;
	}
	public void setSubTotalSurProrationAmtInBaseCurr(Money subTotalSurProrationAmtInBaseCurr) {
		this.subTotalSurProrationAmtInBaseCurr = subTotalSurProrationAmtInBaseCurr;
	}
	public Money getSubTotalSurProrationAmtInCtrCur() {
		return subTotalSurProrationAmtInCtrCur;
	}
	public void setSubTotalSurProrationAmtInCtrCur(Money subTotalSurProrationAmtInCtrCur) {
		this.subTotalSurProrationAmtInCtrCur = subTotalSurProrationAmtInCtrCur;
	}

	
	
	
	
}
