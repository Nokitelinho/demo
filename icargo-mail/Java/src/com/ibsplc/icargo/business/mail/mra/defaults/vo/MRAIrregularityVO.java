/*
 * MRAIrregularityVO.java created on Sep 26, 2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
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
 * 
 * @author A-3229
 *
 */
public class MRAIrregularityVO extends AbstractVO {
			
		
		
		//dsn pks
		private String companyCode;
	    private String billingBasis;
	    private int csgSequenceNumber;
		private String csgDocumentNumber;
		private String poaCode;
		private String dsn;
		
		private String dsnNumber;
		private LocalDate executionDate;
		private String modeOfPayment;
		private String flightNumber;
		private LocalDate flightDate;
		private int flightCarrierId;
		private long flightSequenceNumber;
		private LocalDate offLoadedDate;
		private String offloadedStation;
		private double offloadedWeight;
		private String rescheduledFlightNumber;
		private LocalDate rescheduledFlightDate;
		private int rescheduledFlightCarrierId;
		private long rescheduledFlightSequenceNumber;
		private String awbOrigin;
		private String awbDestination;
		private String awbRoute;
		private double awbWeight;
		private Money frieghtCharges;
		private String irregularityStatus;
		private String currency;
		
		private int count;
		
		private double offloadedPieces;
		
		private Money totalFreightCharges;
		
		private Money totalCharges;
		
		private Money total;
		
		private Collection<MRAIrregularityDetailsVO> flightDetails;
		
		
		/**
		 * @return
		 */
		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 * @param companyCode
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		
		/**
		 * @return
		 */
		public LocalDate getExecutionDate() {
			return executionDate;
		}
		/**
		 * @param executionDate
		 */
		public void setExecutionDate(LocalDate executionDate) {
			this.executionDate = executionDate;
		}
		/**
		 * @return
		 */
		public String getModeOfPayment() {
			return modeOfPayment;
		}
		/**
		 * @param modeOfPayment
		 */
		public void setModeOfPayment(String modeOfPayment) {
			this.modeOfPayment = modeOfPayment;
		}
		/**
		 * @return
		 */
		public String getFlightNumber() {
			return flightNumber;
		}
		/**
		 * @param flightNumber
		 */
		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}
		/**
		 * @return
		 */
		public LocalDate getFlightDate() {
			return flightDate;
		}
		/**
		 * @param flightDate
		 */
		public void setFlightDate(LocalDate flightDate) {
			this.flightDate = flightDate;
		}
		/**
		 * @return
		 */
		public int getFlightCarrierId() {
			return flightCarrierId;
		}
		/**
		 * @param flightCarrierId
		 */
		public void setFlightCarrierId(int flightCarrierId) {
			this.flightCarrierId = flightCarrierId;
		}
		/**
		 * @return
		 */
		public long getFlightSequenceNumber() {
			return flightSequenceNumber;
		}
		/**
		 * @param flightSequenceNumber
		 */
		public void setFlightSequenceNumber(long flightSequenceNumber) {
			this.flightSequenceNumber = flightSequenceNumber;
		}
		/**
		 * @return
		 */
		public LocalDate getOffLoadedDate() {
			return offLoadedDate;
		}
		/**
		 * @param offLoadedDate
		 */
		public void setOffLoadedDate(LocalDate offLoadedDate) {
			this.offLoadedDate = offLoadedDate;
		}
		/**
		 * @return
		 */
		public String getOffloadedStation() {
			return offloadedStation;
		}
		/**
		 * @param offloadedStation
		 */
		public void setOffloadedStation(String offloadedStation) {
			this.offloadedStation = offloadedStation;
		}
		/**
		 * @return
		 */
		public double getOffloadedWeight() {
			return offloadedWeight;
		}
		/**
		 * @param offloadedWeight
		 */
		public void setOffloadedWeight(double offloadedWeight) {
			this.offloadedWeight = offloadedWeight;
		}
		/**
		 * @return
		 */
		public String getRescheduledFlightNumber() {
			return rescheduledFlightNumber;
		}
		/**
		 * @param rescheduledFlightNumber
		 */
		public void setRescheduledFlightNumber(String rescheduledFlightNumber) {
			this.rescheduledFlightNumber = rescheduledFlightNumber;
		}
		/**
		 * @return
		 */
		public LocalDate getRescheduledFlightDate() {
			return rescheduledFlightDate;
		}
		/**
		 * @param rescheduledFlightDate
		 */
		public void setRescheduledFlightDate(LocalDate rescheduledFlightDate) {
			this.rescheduledFlightDate = rescheduledFlightDate;
		}
		/**
		 * @return
		 */
		public int getRescheduledFlightCarrierId() {
			return rescheduledFlightCarrierId;
		}
		/**
		 * @param rescheduledFlightCarrierId
		 */
		public void setRescheduledFlightCarrierId(int rescheduledFlightCarrierId) {
			this.rescheduledFlightCarrierId = rescheduledFlightCarrierId;
		}
		/**
		 * @return
		 */
		public long getRescheduledFlightSequenceNumber() {
			return rescheduledFlightSequenceNumber;
		}
		/**
		 * @param rescheduledFlightSequenceNumber
		 */
		public void setRescheduledFlightSequenceNumber(
				long rescheduledFlightSequenceNumber) {
			this.rescheduledFlightSequenceNumber = rescheduledFlightSequenceNumber;
		}
		/**
		 * @return
		 */
		public String getAwbOrigin() {
			return awbOrigin;
		}
		/**
		 * @param awbOrigin
		 */
		public void setAwbOrigin(String awbOrigin) {
			this.awbOrigin = awbOrigin;
		}
		/**
		 * @return
		 */
		public String getAwbDestination() {
			return awbDestination;
		}
		/**
		 * @param awbDestination
		 */
		public void setAwbDestination(String awbDestination) {
			this.awbDestination = awbDestination;
		}
		/**
		 * @return
		 */
		public String getAwbRoute() {
			return awbRoute;
		}
		/**
		 * @param awbRoute
		 */
		public void setAwbRoute(String awbRoute) {
			this.awbRoute = awbRoute;
		}
		/**
		 * @return
		 */
		public double getAwbWeight() {
			return awbWeight;
		}
		/**
		 * @param awbWeight
		 */
		public void setAwbWeight(double awbWeight) {
			this.awbWeight = awbWeight;
		}
		/**
		 * @return
		 */
		public Money getFrieghtCharges() {
			return frieghtCharges;
		}
		/**
		 * @param frieghtCharges
		 */
		public void setFrieghtCharges(Money frieghtCharges) {
			this.frieghtCharges = frieghtCharges;
		}
		
		/**
		 * @return
		 */
		public Money getTotalCharges() {
			return totalCharges;
		}
		/**
		 * @param totalCharges
		 */
		public void setTotalCharges(Money totalCharges) {
			this.totalCharges = totalCharges;
		}
		/**
		 * @return
		 */
		public int getCount() {
			return count;
		}
		/**
		 * @param count
		 */
		public void setCount(int count) {
			this.count = count;
		}
		
		/**
		 * @return flightDetails
		 */
		public Collection<MRAIrregularityDetailsVO> getFlightDetails() {
			return flightDetails;
		}
		/**
		 * @param flightDetails
		 */
		public void setFlightDetails(Collection<MRAIrregularityDetailsVO> flightDetails) {
			this.flightDetails = flightDetails;
		}
		
		/**
		 * @return offloadedPieces
		 */
		public double getOffloadedPieces() {
			return offloadedPieces;
		}
		/**
		 * @param offloadedPieces
		 */
		public void setOffloadedPieces(double offloadedPieces) {
			this.offloadedPieces = offloadedPieces;
		}
		/**
		 * @return
		 */
		/*public Money getOtherCharges() {
			return otherCharges;
		}*/
		/**
		 * @param otherCharges
		 */
		/*public void setOtherCharges(Money otherCharges) {
			this.otherCharges = otherCharges;
		}*/
		/**
		 * @return
		 */
		public Money getTotalFreightCharges() {
			return totalFreightCharges;
		}
		/**
		 * @param totalFreightCharges
		 */
		public void setTotalFreightCharges(Money totalFreightCharges) {
			this.totalFreightCharges = totalFreightCharges;
		}

		
		/**
		 * @return
		 */
		public String getCurrency() {
			return currency;
		}
		/**
		 * @param currency
		 */
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		/**
		 * @return
		 */
		public Money getTotal() {
			return total;
		}
		/**
		 * @param total
		 */
		public void setTotal(Money total) {
			this.total = total;
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
		 * @return Returns the dsnNumber.
		 */

		public String getDsnNumber() {
			return dsnNumber;
		}
		/**
		 * @param dsnNumber
		 *            The dsnNumber to set.
		 */
		public void setDsnNumber(String dsnNumber) {
			this.dsnNumber = dsnNumber;
		}
		/**
		 * @return Returns the irregularityStatus.
		 */
		public String getIrregularityStatus() {
			return irregularityStatus;
		}
		/**
		 * @param irregularityStatus
		 *            The irregularityStatus to set.
		 */
		public void setIrregularityStatus(String irregularityStatus) {
			this.irregularityStatus = irregularityStatus;
		}

	}

