
	/*
	* MailProrationLogVO.java Created on Sep 17, 2008
	 *
	 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms.
	 */
	 package com.ibsplc.icargo.business.mail.mra.defaults.vo;

	import java.util.Calendar;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;

	/**
	 * @author A-3229
	 *
	 */
	 public class MailProrationLogVO extends AbstractVO {

		private String companyCode;
		private String billingBasis;
		private int serialNumber;
		private int csgSequenceNumber;
		private String csgDocumentNumber;
		private String poaCode;
		private int sequenceNumber;
		private String dsn;
	
		
		private double aplRate;
		private int pieces;
		private double weight;
		private Calendar flightDate;
		private int flightCarrierID;
		private String flightNumber;
		private String flightCarrierCode;
		private int flightSeqNumber;
		private int segSerialNumber;
		private Money wgtCharge;
		private Money updWgtCharge;
		private Money wgtChargeBas;
		private Money wgtChargeSdr;
		private Money wgtChargeUsd;
		private double dueAirline;
		
		private String discrepancy;
		private String applyAudit;
		private String billTo;
		private String updBillTo;
		private String prorationType;
		private double prorationPercentage;
		private double proratedValue;
		private int prorationFactor;
		private String sectorStatus;
		private String paymentFlag;
		private String gpaArlBlgStatus;
		private String blgStatus;
		private String contractCurrCode;
		private String revFlag;
		private String accStatus;
		private String accTransactionId;
	
		
		private int versionNo;
		private String carrierCode;
		private int carrierId;
		private String triggerPoint;
		private String carriageFrom;
		private String carriageTo;
		private LocalDate dateTime;
		private String user;
		private String remarks;
		//Added by A-7794 as part of MRA revamp activity
		private long mailSequenceNumber;
		private String postalAuthorityName;

		public MailProrationLogVO(){

		}


		

		/**
		 * @return the postalAuthorityName
		 */
		public String getPostalAuthorityName() {
			return postalAuthorityName;
		}




		/**
		 * @param postalAuthorityName the postalAuthorityName to set
		 */
		public void setPostalAuthorityName(String postalAuthorityName) {
			this.postalAuthorityName = postalAuthorityName;
		}




		/**
		 * @return the mailSequenceNumber
		 */
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}




		/**
		 * @param mailSequenceNumber the mailSequenceNumber to set
		 */
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}




		/**
		 * @return Returns the dsn.
		 */
		public String getDsn() {
			return dsn;
		}



		/**
		 * @param dsn The dsn to set.
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}




		/**
		 * @return Returns the dsn.
		 */

		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 *
		 * @param companyCode
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}



		/**
		 * @return Returns the billingBasis.
		 */

		public String getBillingBasis() {
			return billingBasis;
		}


		/**
		 * @param billingBasis The billingBasis to set.
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
		}


		/**
		 * @return Returns the poaCode.
		 */

		public String getPoaCode() {
			return poaCode;
		}


		/**
		 * @param poaCode The poaCode to set.
		 */
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}

		
		

		/**
		 * @return Returns the carrierCode.
		 */

		public String getCarrierCode() {
			return carrierCode;
		}


		/**
		 * @param carrierCode
		 *            The carrierCode to set.
		 */

		public void setCarrierCode(String carrierCode) {
			this.carrierCode = carrierCode;
		}


		/**
		 * @return Returns the carrierId.
		 */

		public int getCarrierId() {
			return carrierId;
		}

		/**
		 * @param carrierId
		 *            The carrierId to set.
		 */


		public void setCarrierId(int carrierId) {
			this.carrierId = carrierId;
		}


		/**
		 * @return Returns the serialNumber.
		 */

		public int getSerialNumber() {
			return serialNumber;
		}

		/**
		 * @param serialNumber
		 *            The serialNumber to set.
		 */


		public void setSerialNumber(int serialNumber) {
			this.serialNumber = serialNumber;
		}


		/**
		 * @return Returns the csgDocumentNumber.
		 */

		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}

		/**
		 * @param csgDocumentNumber
		 *            The csgDocumentNumber to set.
		 */


		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}

		/**
		 * @return Returns the csgSequenceNumber.
		 */


		public int getCsgSequenceNumber() {
			return csgSequenceNumber;
		}


		/**
		 * @param csgSequenceNumber
		 *            The csgSequenceNumber to set.
		 */

		public void setCsgSequenceNumber(int csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}


		/**
		 * @return Returns the carriageFrom.
		 */

		public String getCarriageFrom() {
			return carriageFrom;
		}



		/**
		 * @param carriageFrom
		 *            The carriageFrom to set.
		 */
		public void setCarriageFrom(String carriageFrom) {
			this.carriageFrom = carriageFrom;
		}

		/**
		 * @return Returns the carriageTo.
		 */
		

		public String getCarriageTo() {
			return carriageTo;
		}


		/**
		 * @param carriageTo
		 *            The carriageTo to set.
		 */

		public void setCarriageTo(String carriageTo) {
			this.carriageTo = carriageTo;
		}

		/**
		 * @return Returns the dateTime.
		 */


		public LocalDate getDateTime() {
			return dateTime;
		}


		/**
		 * @param dateTime
		 *            The dateTime to set.
		 */

		public void setDateTime(LocalDate dateTime) {
			this.dateTime = dateTime;
		}


		/**
		 * @return Returns the remarks.
		 */

		public String getRemarks() {
			return remarks;
		}

		/**
		 * @param remarks
		 *            The remarks to set.
		 */


		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		/**
		 * @return Returns the sequenceNumber.
		 */


		public int getSequenceNumber() {
			return sequenceNumber;
		}


		/**
		 * @param sequenceNumber
		 *            The sequenceNumber to set.
		 */

		public void setSequenceNumber(int sequenceNumber) {
			this.sequenceNumber = sequenceNumber;
		}



		/**
		 * @return Returns the triggerPoint.
		 */
		public String getTriggerPoint() {
			return triggerPoint;
		}


		/**
		 * @param triggerPoint
		 *            The triggerPoint to set.
		 */

		public void setTriggerPoint(String triggerPoint) {
			this.triggerPoint = triggerPoint;
		}



		/**
		 * @return Returns the user.
		 */
		public String getUser() {
			return user;
		}

		/**
		 * @param user
		 *            The user to set.
		 */


		public void setUser(String user) {
			this.user = user;
		}


		/**
		 * @return Returns the versionNo.
		 */

		public int getVersionNo() {
			return versionNo;
		}


		/**
		 * @param versionNo
		 *            The versionNo to set.
		 */

		public void setVersionNo(int versionNo) {
			this.versionNo = versionNo;
		}



		/**
		 * @return Returns the aplRate.
		 */
		public double getAplRate() {
			return aplRate;
		}

		/**
		 * @param aplRate
		 *            The aplRate to set.
		 */


		public void setAplRate(double aplRate) {
			this.aplRate = aplRate;
		}


		/**
		 * @return Returns the applyAudit.
		 */

		public String getApplyAudit() {
			return applyAudit;
		}


		/**
		 * @param applyAudit
		 *            The applyAudit to set.
		 */

		public void setApplyAudit(String applyAudit) {
			this.applyAudit = applyAudit;
		}



		/**
		 * @return Returns the billTo.
		 */
		public String getBillTo() {
			return billTo;
		}

		/**
		 * @param billTo
		 *            The billTo to set.
		 */


		public void setBillTo(String billTo) {
			this.billTo = billTo;
		}


		/**
		 * @return Returns the blgStatus.
		 */

		public String getBlgStatus() {
			return blgStatus;
		}
		/**
		 * @param blgStatus
		 *            The blgStatus to set.
		 */



		public void setBlgStatus(String blgStatus) {
			this.blgStatus = blgStatus;
		}


		/**
		 * @return Returns the contractCurrCode.
		 */

		public String getContractCurrCode() {
			return contractCurrCode;
		}


		/**
		 * @param contractCurrCode
		 *            The contractCurrCode to set.
		 */

		public void setContractCurrCode(String contractCurrCode) {
			this.contractCurrCode = contractCurrCode;
		}


		/**
		 * @return Returns the discrepancy.
		 */

		public String getDiscrepancy() {
			return discrepancy;
		}


		/**
		 * @param discrepancy
		 *            The discrepancy to set.
		 */

		public void setDiscrepancy(String discrepancy) {
			this.discrepancy = discrepancy;
		}

		/**
		 * @return Returns the dueAirline.
		 */


		public double getDueAirline() {
			return dueAirline;
		}



		/**
		 * @param dueAirline
		 *            The dueAirline to set.
		 */
		public void setDueAirline(double dueAirline) {
			this.dueAirline = dueAirline;
		}


		/**
		 * @return Returns the flightCarrierCode.
		 */

		public String getFlightCarrierCode() {
			return flightCarrierCode;
		}


		/**
		 * @param flightCarrierCode
		 *            The flightCarrierCode to set.
		 */

		public void setFlightCarrierCode(String flightCarrierCode) {
			this.flightCarrierCode = flightCarrierCode;
		}


		/**
		 * @return Returns the flightCarrierID.
		 */

		public int getFlightCarrierID() {
			return flightCarrierID;
		}


		/**
		 * @param flightCarrierID
		 *            The flightCarrierID to set.
		 */

		public void setFlightCarrierID(int flightCarrierID) {
			this.flightCarrierID = flightCarrierID;
		}


		/**
		 * @return Returns the flightDate.
		 */

		public Calendar getFlightDate() {
			return flightDate;
		}

		/**
		 * @param flightDate
		 *            The flightDate to set.
		 */


		public void setFlightDate(Calendar flightDate) {
			this.flightDate = flightDate;
		}

		/**
		 * @return Returns the flightNumber.
		 */


		public String getFlightNumber() {
			return flightNumber;
		}

		/**
		 * @param flightNumber
		 *            The flightNumber to set.
		 */


		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}


		/**
		 * @return Returns the flightSeqNumber.
		 */

		public int getFlightSeqNumber() {
			return flightSeqNumber;
		}


		/**
		 * @param flightSeqNumber
		 *            The flightSeqNumber to set.
		 */

		public void setFlightSeqNumber(int flightSeqNumber) {
			this.flightSeqNumber = flightSeqNumber;
		}


		/**
		 * @return Returns the gpaArlBlgStatus.
		 */

		public String getGpaArlBlgStatus() {
			return gpaArlBlgStatus;
		}

		/**
		 * @param gpaArlBlgStatus
		 *            The gpaArlBlgStatus to set.
		 */


		public void setGpaArlBlgStatus(String gpaArlBlgStatus) {
			this.gpaArlBlgStatus = gpaArlBlgStatus;
		}


		/**
		 * @return Returns the paymentFlag.
		 */

		public String getPaymentFlag() {
			return paymentFlag;
		}



		/**
		 * @param paymentFlag
		 *            The paymentFlag to set.
		 */
		public void setPaymentFlag(String paymentFlag) {
			this.paymentFlag = paymentFlag;
		}



		/**
		 * @return Returns the pieces.
		 */
		public int getPieces() {
			return pieces;
		}


		/**
		 * @param pieces
		 *            The pieces to set.
		 */

		public void setPieces(int pieces) {
			this.pieces = pieces;
		}


		/**
		 * @return Returns the prorationPercentage.
		 */

		public double getProrationPercentage() {
			return prorationPercentage;
		}


		/**
		 * @param prorationPercentage
		 *            The prorationPercentage to set.
		 */

		public void setProrationPercentage(double prorationPercentage) {
			this.prorationPercentage = prorationPercentage;
		}



		/**
		 * @return Returns the prorationType.
		 */
		public String getProrationType() {
			return prorationType;
		}


		/**
		 * @param prorationType
		 *            The prorationType to set.
		 */

		public void setProrationType(String prorationType) {
			this.prorationType = prorationType;
		}


		/**
		 * @return Returns the revFlag.
		 */

		public String getRevFlag() {
			return revFlag;
		}

		/**
		 * @param revFlag
		 *            The revFlag to set.
		 */


		public void setRevFlag(String revFlag) {
			this.revFlag = revFlag;
		}



		/**
		 * @return Returns the segSerialNumber.
		 */
		public int getSegSerialNumber() {
			return segSerialNumber;
		}


		/**
		 * @param segSerialNumber
		 *            The segSerialNumber to set.
		 */

		public void setSegSerialNumber(int segSerialNumber) {
			this.segSerialNumber = segSerialNumber;
		}


		/**
		 * @return Returns the updBillTo.
		 */

		public String getUpdBillTo() {
			return updBillTo;
		}

		/**
		 * @param updBillTo
		 *            The updBillTo to set.
		 */


		public void setUpdBillTo(String updBillTo) {
			this.updBillTo = updBillTo;
		}


		/**
		 * @return Returns the updWgtCharge.
		 */

		public Money getUpdWgtCharge() {
			return updWgtCharge;
		}


		/**
		 * @param updWgtCharge
		 *            The updWgtCharge to set.
		 */

		public void setUpdWgtCharge(Money updWgtCharge) {
			this.updWgtCharge = updWgtCharge;
		}


		/**
		 * @return Returns the weight.
		 */

		public double getWeight() {
			return weight;
		}



		/**
		 * @param weight
		 *            The weight to set.
		 */
		public void setWeight(double weight) {
			this.weight = weight;
		}



		/**
		 * @return Returns the wgtCharge.
		 */
		public Money getWgtCharge() {
			return wgtCharge;
		}


		/**
		 * @param wgtCharge
		 *            The wgtCharge to set.
		 */

		public void setWgtCharge(Money wgtCharge) {
			this.wgtCharge = wgtCharge;
		}


		/**
		 * @return Returns the wgtChargeBas.
		 */

		public Money getWgtChargeBas() {
			return wgtChargeBas;
		}



		/**
		 * @param wgtChargeBas
		 *            The wgtChargeBas to set.
		 */
		public void setWgtChargeBas(Money wgtChargeBas) {
			this.wgtChargeBas = wgtChargeBas;
		}



		/**
		 * @return Returns the wgtChargeSdr.
		 */
		public Money getWgtChargeSdr() {
			return wgtChargeSdr;
		}


		/**
		 * @param wgtChargeSdr
		 *            The wgtChargeSdr to set.
		 */

		public void setWgtChargeSdr(Money wgtChargeSdr) {
			this.wgtChargeSdr = wgtChargeSdr;
		}


		/**
		 * @return Returns the wgtChargeUsd.
		 */

		public Money getWgtChargeUsd() {
			return wgtChargeUsd;
		}



		/**
		 * @param wgtChargeUsd
		 *            The wgtChargeUsd to set.
		 */
		public void setWgtChargeUsd(Money wgtChargeUsd) {
			this.wgtChargeUsd = wgtChargeUsd;
		}


		/**
		 * @return Returns the accTransactionId.
		 */

		public String getAccTransactionId() {
			return accTransactionId;
		}


		/**
		 * @param accTransactionId
		 *            The accTransactionId to set.
		 */

		public void setAccTransactionId(String accTransactionId) {
			this.accTransactionId = accTransactionId;
		}


		/**
		 * @return Returns the accStatus.
		 */


		public String getAccStatus() {
			return accStatus;
		}

		/**
		 * @param accStatus
		 *            The accStatus to set.
		 */


		public void setAccStatus(String accStatus) {
			this.accStatus = accStatus;
		}


		/**
		 * @return Returns the proratedValue.
		 */
		public double getProratedValue() {
			return proratedValue;
		}
		/**
		 * @param proratedValue The proratedValue to set.
		 */
		public void setProratedValue(double proratedValue) {
			this.proratedValue = proratedValue;
		}



		/**
		 * @return Returns the prorationFactor.
		 */
		public int getProrationFactor() {
			return prorationFactor;
		}

		/**
		 * @param prorationFactor The prorationFactor to set.
		 */
		public void setProrationFactor(int prorationFactor) {
			this.prorationFactor = prorationFactor;
		}

		/**
		 * @return Returns the sectorStatus.
		 */
		public String getSectorStatus() {
			return sectorStatus;
		}

		/**
		 * @param sectorStatus The sectorStatus to set.
		 */
		public void setSectorStatus(String sectorStatus) {
			this.sectorStatus = sectorStatus;
		}

		

		
	}

