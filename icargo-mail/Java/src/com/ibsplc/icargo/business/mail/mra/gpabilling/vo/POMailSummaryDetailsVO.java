/**
 * POMailSummaryDetailsVO.java Created on May 11, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.gpabilling.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-4823
 *
 */
public class POMailSummaryDetailsVO extends AbstractVO{

	private String subClassCode;
	private String mailType;
	private String pol;
	private String pou;
	private String destinationStation;
	private String originStation;
	private String flightNumber;
	private LocalDate flightDate;
	private Double weight;
	private Double distance;
	private String billingCurrency;
	private Double rate;
	private Money amount;
	private Double serviceTax;
	private Double tds;
	private Money payableAmount;
	private Money chequeAmount;
	private Money dueAmount;
	private String chequeNumber;
	private LocalDate chequeDate;
	private LocalDate settlementDate;
	private String remarks;
	public String getSubClassCode() {
		return subClassCode;
	}
	public void setSubClassCode(String subClassCode) {
		this.subClassCode = subClassCode;
	}
	public String getMailType() {
		return mailType;
	}
	public void setMailType(String mailType) {
		this.mailType = mailType;
	}
	public String getPol() {
		return pol;
	}
	public void setPol(String pol) {
		this.pol = pol;
	}
	public String getPou() {
		return pou;
	}
	public void setPou(String pou) {
		this.pou = pou;
	}
	public String getDestinationStation() {
		return destinationStation;
	}
	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}
	public String getOriginStation() {
		return originStation;
	}
	public void setOriginStation(String originStation) {
		this.originStation = originStation;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getDistance() {
		return distance;
	}
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	public String getBillingCurrency() {
		return billingCurrency;
	}
	public void setBillingCurrency(String billingCurrency) {
		this.billingCurrency = billingCurrency;
	}
	public Double getRate() {
		return rate;
	}
	public void setRate(Double rate) {
		this.rate = rate;
	}
	public Money getAmount() {
		return amount;
	}
	public void setAmount(Money amount) {
		this.amount = amount;
	}
	public Double getServiceTax() {
		return serviceTax;
	}
	public void setServiceTax(Double serviceTax) {
		this.serviceTax = serviceTax;
	}
	public Double getTds() {
		return tds;
	}
	public void setTds(Double tds) {
		this.tds = tds;
	}
	public Money getPayableAmount() {
		return payableAmount;
	}
	public void setPayableAmount(Money payableAmount) {
		this.payableAmount = payableAmount;
	}
	public Money getChequeAmount() {
		return chequeAmount;
	}
	public void setChequeAmount(Money chequeAmount) {
		this.chequeAmount = chequeAmount;
	}
	public Money getDueAmount() {
		return dueAmount;
	}
	public void setDueAmount(Money dueAmount) {
		this.dueAmount = dueAmount;
	}
	public String getChequeNumber() {
		return chequeNumber;
	}
	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}
	public LocalDate getChequeDate() {
		return chequeDate;
	}
	public void setChequeDate(LocalDate chequeDate) {
		this.chequeDate = chequeDate;
	}
	public LocalDate getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(LocalDate settlementDate) {
		this.settlementDate = settlementDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
