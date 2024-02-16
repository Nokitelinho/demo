
/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.defaults.vo.AWMProrationDetailsVO.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-7371	:	11-May-2018	:	Draft
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;
import java.util.Collection;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
public class AWMProrationDetailsVO extends AbstractVO{
	
	private String companyCode;
	private int flightCarrierIdentifier;
	private String flightNumber;
	private int flightSequenceNumber;
	private String sector;
	private String sectorStatus;
	private String sectorFrom;
	private String sectorTo;
	private int numberOfPieces;
	private double weight;
	private String prorationType;
	private double prorationPercentage;
	private Money prorationAmtInUsd;
	private Money prorationAmtInSdr;
	private Money prorationAmtInBaseCurr;
	private Money prorationAmtInCtrCurr;
	private String carrierCode;
	private int carrierId;
	private Money surProrationAmtInUsd;
	private Money surProrationAmtInSdr;
	private Money surProrationAmtInBaseCurr;
	private Money surProratedAmtInCtrCur;
	private String poaCode;
	private String currency;


	private Collection<AWMProrationSurchargeDetailsVO> awmProrationSurchargeDetailsVO;
	public Collection<AWMProrationSurchargeDetailsVO> getAwmProrationSurchargeDetailsVO() {
		return awmProrationSurchargeDetailsVO;
	}
	public void setAwmProrationSurchargeDetailsVO(
			Collection<AWMProrationSurchargeDetailsVO> awmProrationSurchargeDetailsVO) {
		this.awmProrationSurchargeDetailsVO = awmProrationSurchargeDetailsVO;
	}
	public String getSectorStatus() {
		return sectorStatus;
	}
	public void setSectorStatus(String sectorStatus) {
		this.sectorStatus = sectorStatus;
	}
	public String getSector() {
		return sector;
	}
	public void setSector(String sector) {
		this.sector = sector;
	}
	
	public Money getProrationAmtInCtrCurr() {
		return prorationAmtInCtrCurr;
	}
	public void setProrationAmtInCtrCurr(Money prorationAmtInCtrCurr) {
		this.prorationAmtInCtrCurr = prorationAmtInCtrCurr;
	}
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
	public int getFlightCarrierIdentifier() {
		return flightCarrierIdentifier;
	}
	public void setFlightCarrierIdentifier(int flightCarrierIdentifier) {
		this.flightCarrierIdentifier = flightCarrierIdentifier;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public int getFlightSequenceNumber() {
		return flightSequenceNumber;
	}
	public void setFlightSequenceNumber(int flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}
	public String getSectorFrom() {
		return sectorFrom;
	}
	public void setSectorFrom(String sectorFrom) {
		this.sectorFrom = sectorFrom;
	}
	public String getSectorTo() {
		return sectorTo;
	}
	public void setSectorTo(String sectorTo) {
		this.sectorTo = sectorTo;
	}
	public int getNumberOfPieces() {
		return numberOfPieces;
	}
	public void setNumberOfPieces(int numberOfPieces) {
		this.numberOfPieces = numberOfPieces;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getProrationType() {
		return prorationType;
	}
	public void setProrationType(String prorationType) {
		this.prorationType = prorationType;
	}
	public double getProrationPercentage() {
		return prorationPercentage;
	}
	public void setProrationPercentage(double prorationPercentage) {
		this.prorationPercentage = prorationPercentage;
	}
	public Money getProrationAmtInUsd() {
		return prorationAmtInUsd;
	}
	public void setProrationAmtInUsd(Money prorationAmtInUsd) {
		this.prorationAmtInUsd = prorationAmtInUsd;
	}
	public Money getProrationAmtInSdr() {
		return prorationAmtInSdr;
	}
	public void setProrationAmtInSdr(Money prorationAmtInSdr) {
		this.prorationAmtInSdr = prorationAmtInSdr;
	}
	public Money getProrationAmtInBaseCurr() {
		return prorationAmtInBaseCurr;
	}
	public void setProrationAmtInBaseCurr(Money prorationAmtInBaseCurr) {
		this.prorationAmtInBaseCurr = prorationAmtInBaseCurr;
	}
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public int getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
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
	public String getPoaCode() {
		return poaCode;
	}
	public void setPoaCode(String poaCode) {
		this.poaCode = poaCode;
	}
}
