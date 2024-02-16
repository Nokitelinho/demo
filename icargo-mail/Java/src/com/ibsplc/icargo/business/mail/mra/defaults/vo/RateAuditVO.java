/*
 * RateAuditVO.java Created on July 14, 2008
 *
 * Copyright 2008 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3108
 *
 */
public class RateAuditVO extends AbstractVO{

	private String companyCode;
	private String billingBasis;
	private int serialNumber;
	private String applyAutd;
	private String dsn;
	private LocalDate dsnDate;
	private String discrepancy;
	private String gpaCode;
	private String category;
	private String malClass;
	private String subClass;
	private String origin;
	private String destination;

	private String carrierCode;
	private String flightNumber;
	private LocalDate flightDate;
	private String pcs;
	private Double grossWt;
	private Double rate;
	private Money presentWtCharge;
	private Money auditedWtCharge;
	private String billTo;
	private String dsnStatus;
	 private LocalDate lastUpdateTime;
	 private String lastUpdateUser;
	 private String operationFlag;

		/**
		 *
		 * change done by indu for rate audit details screen
		 */
		private String conDocNum;
		private int conSerNum;
		private String route;
		private String uld;
		private String flightCarCod;
		private String updWt;
		private String parChangeFlag;
		private double initialRate;
		private String compTotTrigPt;
		private int tRecordCount;

		private String discrepancyNo;

		private Money discrepancyYes;
		private String basType;
		private int year;
		private String dsnaccsta;
		private String hsn;
		private String regInd;
		private String rsn;
		private String saveToHistoryFlg;
		private String poaFlag;
		private String transfercarcode;
		private String raiseCCAFlag;
		//Added By Deepthi for controlling accounting call
		
		private String fromRateAudit;
		
		private String currency;
		private String mailbagId;
		private long mailSequenceNumber;
		private String originOE;
		private String mailCompanyCode;
		private String triggerPoint;
		
		
		private String destinationOE;
		private String originCityCode;
		private String destinationCityCode;
		private LocalDate receivedDate;
		private LocalDate scannedDate;
		private Collection<RateAuditDetailsVO> rateAuditDetails;

		private String mailStatus;
		
		private String autoArrivalFlag; //Added by A-7540

		/**
		 * @return the raiseCCAFlag
		 */
		public String getRaiseCCAFlag() {
			return raiseCCAFlag;
		}
		/**
		 * @param raiseCCAFlag the raiseCCAFlag to set
		 */
		public void setRaiseCCAFlag(String raiseCCAFlag) {
			this.raiseCCAFlag = raiseCCAFlag;
		}
		public String getMailbagId() {
			return mailbagId;
		}
		public void setMailbagId(String mailbagId) {
			this.mailbagId = mailbagId;
		}
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}
		public String getOriginOE() {
			return originOE;
		}
		public void setOriginOE(String originOE) {
			this.originOE = originOE;
		}
		public String getDestinationOE() {
			return destinationOE;
		}
		public void setDestinationOE(String destinationOE) {
			this.destinationOE = destinationOE;
		}
		public String getOriginCityCode() {
			return originCityCode;
		}
		public void setOriginCityCode(String originCityCode) {
			this.originCityCode = originCityCode;
		}
		public String getDestinationCityCode() {
			return destinationCityCode;
		}
		public void setDestinationCityCode(String destinationCityCode) {
			this.destinationCityCode = destinationCityCode;
		}
		public LocalDate getReceivedDate() {
			return receivedDate;
		}
		public void setReceivedDate(LocalDate receivedDate) {
			this.receivedDate = receivedDate;
		}
		/**
		 * @return the saveToHistoryFlg
		 */
		public String getSaveToHistoryFlg() {
			return saveToHistoryFlg;
		}
		/**
		 * @param saveToHistoryFlg the saveToHistoryFlg to set
		 */
		public void setSaveToHistoryFlg(String saveToHistoryFlg) {
			this.saveToHistoryFlg = saveToHistoryFlg;
		}
		/**
		 * @return Returns the flightCarCod.
		 */
		public String getFlightCarCod() {
			return flightCarCod;
		}
		/**
		 * @param flightCarCod The flightCarCod to set.
		 */
		public void setFlightCarCod(String flightCarCod) {
			this.flightCarCod = flightCarCod;
		}
		/**
		 * @return Returns the route.
		 */
		public String getRoute() {
			return route;
		}
		/**
		 * @param route The route to set.
		 */
		public void setRoute(String route) {
			this.route = route;
		}
		/**
		 * @return Returns the uld.
		 */
		public String getUld() {
			return uld;
		}
		/**
		 * @param uld The uld to set.
		 */
		public void setUld(String uld) {
			this.uld = uld;
		}
		/**
		 * @return Returns the updWt.
		 */
		public String getUpdWt() {
			return updWt;
		}
		/**
		 * @param updWt The updWt to set.
		 */
		public void setUpdWt(String updWt) {
			this.updWt = updWt;
		}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getApplyAutd() {
		return applyAutd;
	}
	public void setApplyAutd(String applyAutd) {
		this.applyAutd = applyAutd;
	}
	public String getBillTo() {
		return billTo;
	}
	public void setBillTo(String billTo) {
		this.billTo = billTo;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getDiscrepancy() {
		return discrepancy;
	}
	public void setDiscrepancy(String discrepancy) {
		this.discrepancy = discrepancy;
	}
	public String getDsn() {
		return dsn;
	}
	public void setDsn(String dsn) {
		this.dsn = dsn;
	}
	public LocalDate getDsnDate() {
		return dsnDate;
	}
	public void setDsnDate(LocalDate dsnDate) {
		this.dsnDate = dsnDate;
	}
	public String getDsnStatus() {
		return dsnStatus;
	}
	public void setDsnStatus(String dsnStatus) {
		this.dsnStatus = dsnStatus;
	}

	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public LocalDate getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	public String getFlightNumber() {
		return flightNumber;
	}
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	public String getGpaCode() {
		return gpaCode;
	}
	public void setGpaCode(String gpaCode) {
		this.gpaCode = gpaCode;
	}

	public String getMalClass() {
		return malClass;
	}
	public void setMalClass(String malClass) {
		this.malClass = malClass;
	}
	public String getOrigin() {
		return origin;
	}
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	public String getPcs() {
		return pcs;
	}
	public void setPcs(String pcs) {
		this.pcs = pcs;
	}

	
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public String getBillingBasis() {
		return billingBasis;
	}
	public void setBillingBasis(String billingBasis) {
		this.billingBasis = billingBasis;
	}
	public int getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the conSerNum.
	 */
	public int getConSerNum() {
		return conSerNum;
	}
	/**
	 * @param conSerNum The conSerNum to set.
	 */
	public void setConSerNum(int conSerNum) {
		this.conSerNum = conSerNum;
	}
	/**
	 * @return the rateAuditDetails
	 */
	public Collection<RateAuditDetailsVO> getRateAuditDetails() {
		return rateAuditDetails;
	}
	/**
	 * @param rateAuditDetails the rateAuditDetails to set
	 */
	public void setRateAuditDetails(Collection<RateAuditDetailsVO> rateAuditDetails) {
		this.rateAuditDetails = rateAuditDetails;
	}
	/**
	 * @return the conDocNum
	 */
	public String getConDocNum() {
		return conDocNum;
	}
	/**
	 * @param conDocNum the conDocNum to set
	 */
	public void setConDocNum(String conDocNum) {
		this.conDocNum = conDocNum;
	}
	/**
	 * @return the operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag the operationFlag to set
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return the lastUpdateUser
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser the lastUpdateUser to set
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	/**
	 * @return the parChangeFlag
	 */
	public String getParChangeFlag() {
		return parChangeFlag;
	}
	/**
	 * @param parChangeFlag the parChangeFlag to set
	 */
	public void setParChangeFlag(String parChangeFlag) {
		this.parChangeFlag = parChangeFlag;
	}
	/**
	 * @return the initialRate
	 */
	public double getInitialRate() {
		return initialRate;
	}
	/**
	 * @param initialRate the initialRate to set
	 */
	public void setInitialRate(double initialRate) {
		this.initialRate = initialRate;
	}
	/**
	 * @return the compTotTrigPt
	 */
	public String getCompTotTrigPt() {
		return compTotTrigPt;
	}
	/**
	 * @param compTotTrigPt the compTotTrigPt to set
	 */
	public void setCompTotTrigPt(String compTotTrigPt) {
		this.compTotTrigPt = compTotTrigPt;
	}
	/**
	 * @return the presentWtCharge
	 */
	public Money getPresentWtCharge() {
		return presentWtCharge;
	}
	/**
	 * @param presentWtCharge the presentWtCharge to set
	 */
	public void setPresentWtCharge(Money presentWtCharge) {
		this.presentWtCharge = presentWtCharge;
	}
	/**
	 * @return the auditedWtCharge
	 */
	public Money getAuditedWtCharge() {
		return auditedWtCharge;
	}
	/**
	 * @param auditedWtCharge the auditedWtCharge to set
	 */
	public void setAuditedWtCharge(Money auditedWtCharge) {
		this.auditedWtCharge = auditedWtCharge;
	}
	/**
	 * @return the tRecordCount
	 */
	public int getTRecordCount() {
		return tRecordCount;
	}
	/**
	 * @param recordCount the tRecordCount to set
	 */
	public void setTRecordCount(int recordCount) {
		tRecordCount = recordCount;
	}
	/**
	 * @return the discrepancyNo
	 */
	public String getDiscrepancyNo() {
		return discrepancyNo;
	}
	/**
	 * @param discrepancyNo the discrepancyNo to set
	 */
	public void setDiscrepancyNo(String discrepancyNo) {
		this.discrepancyNo = discrepancyNo;
	}
	/**
	 * @return the discrepancyYes
	 */
	public Money getDiscrepancyYes() {
		return discrepancyYes;
	}
	/**
	 * @param discrepancyYes the discrepancyYes to set
	 */
	public void setDiscrepancyYes(Money discrepancyYes) {
		this.discrepancyYes = discrepancyYes;
	}
	/**
	 * @return the basType
	 */
	public String getBasType() {
		return basType;
	}
	/**
	 * @param basType the basType to set
	 */
	public void setBasType(String basType) {
		this.basType = basType;
	}
	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}
	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}
	/**
	 * @return the dsnaccsta
	 */
	public String getDsnaccsta() {
		return dsnaccsta;
	}
	/**
	 * @param dsnaccsta the dsnaccsta to set
	 */
	public void setDsnaccsta(String dsnaccsta) {
		this.dsnaccsta = dsnaccsta;
	}
	/**
	 * @return the hsn
	 */
	public String getHsn() {
		return hsn;
	}
	/**
	 * @param hsn the hsn to set
	 */
	public void setHsn(String hsn) {
		this.hsn = hsn;
	}
	/**
	 * @return the regInd
	 */
	public String getRegInd() {
		return regInd;
	}
	/**
	 * @param regInd the regInd to set
	 */
	public void setRegInd(String regInd) {
		this.regInd = regInd;
	}
	/**
	 * @return the rsn
	 */
	public String getRsn() {
		return rsn;
	}
	/**
	 * @param rsn the rsn to set
	 */
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	/**
	 * @return the transfercarcode
	 */
	public String getTransfercarcode() {
		return transfercarcode;
	}
	/**
	 * @param transfercarcode the transfercarcode to set
	 */
	public void setTransfercarcode(String transfercarcode) {
		this.transfercarcode = transfercarcode;
	}
	/**
	 * @return the poaFlag
	 */
	public String getPoaFlag() {
		return poaFlag;
	}
	/**
	 * @param poaFlag the poaFlag to set
	 */
	public void setPoaFlag(String poaFlag) {
		this.poaFlag = poaFlag;
	}

	public Double getGrossWt() {
		return grossWt;
	}

	public void setGrossWt(Double grossWt) {
		this.grossWt = grossWt;
	}
	
public Double getRate() {

		return rate;
	}
	public void setRate(Double rate) {

		this.rate = rate;
	}
	/**
	 * @return the fromRateAudit
	 */
	public String getFromRateAudit() {
		return fromRateAudit;
	}
	/**
	 * @param fromRateAudit the fromRateAudit to set
	 */
	public void setFromRateAudit(String fromRateAudit) {
		this.fromRateAudit = fromRateAudit;
	}
	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}
	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getMailCompanyCode() {
		return mailCompanyCode;
	}
	public void setMailCompanyCode(String mailCompanyCode) {
		this.mailCompanyCode = mailCompanyCode;
	}
	public String getTriggerPoint() {
		return triggerPoint;
	}
	public void setTriggerPoint(String triggerPoint) {
		this.triggerPoint = triggerPoint;
	}   
    public String getMailStatus() {
		return mailStatus;
	}
	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}
	/**
	 * @return the autoArrivalFlag
	 */
	public String getAutoArrivalFlag() {
		return autoArrivalFlag;
	}
	/**
	 * @param autoArrivalFlag the autoArrivalFlag to set
	 */
	public void setAutoArrivalFlag(String autoArrivalFlag) {
		this.autoArrivalFlag = autoArrivalFlag;
	}
	public LocalDate getScannedDate() {
		return scannedDate;
	}
	public void setScannedDate(LocalDate scannedDate) {
		this.scannedDate = scannedDate;
	}
    
}
