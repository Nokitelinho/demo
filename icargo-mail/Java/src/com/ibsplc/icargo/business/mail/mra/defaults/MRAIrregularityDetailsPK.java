/**
 * MRAIrregularityDetailsPK.java Created on 25 Aug 2008
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.defaults;

import java.io.Serializable;
import javax.persistence.Embeddable;

	/**
	 * @author a-3229
	 *
	 */
	@Embeddable
	public class MRAIrregularityDetailsPK implements Serializable{
		
		private String companyCode;
		
		private String billingBasis;
		
		private String consigndocno;
		
		private long consigndocseqno;
		
		private String postalcode;
		
		private int rescheduledFlightCarrierId;
		
		private String rescheduledFlightNo;
		
		private long rescheduledFlightSeqNo;
		
		private String flightNumber;
		
		private int flightCarrierId;
		
		private long flightSequenceNumber;
		
		/**
		 * Constructor
		 *
		 */
		public MRAIrregularityDetailsPK(){
			
		}
		
		
		/**
		 * @return companyCode
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
		 * @return billingBasis
		 */
		public String getBillingBasis() {
			return billingBasis;
		}
		/**
		 * @param billingBasis
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
		}		
		/**
		 * 
		 * @return
		 */
		public String getConsigndocno() {
			return consigndocno;
		}
		/**
		 * 
		 * @param consigndocno
		 */
		public void setConsigndocno(String consigndocno) {
			this.consigndocno = consigndocno;
		}
		/**
		 * 
		 * @return
		 */
		public long getConsigndocseqno() {
			return consigndocseqno;
		}
		/**
		 *@param consigndocseqno
		 */
		public void setConsigndocseqno(long consigndocseqno) {
			this.consigndocseqno = consigndocseqno;
		}
		/**
		 * 
		 * @return
		 */
		public String getPostalcode() {
			return postalcode;
		}
		/**
		 * 
		 * @param postalcode
		 */
		public void setPostalcode(String postalcode) {
			this.postalcode = postalcode;
		}
		/**
		 * 
		 * @return
		 */
		public int getRescheduledFlightCarrierId() {
			return rescheduledFlightCarrierId;
		}
		/**
		 * 
		 * @param rescheduledFlightCarrierId
		 */
		public void setRescheduledFlightCarrierId(int rescheduledFlightCarrierId) {
			this.rescheduledFlightCarrierId = rescheduledFlightCarrierId;
		}
		/**
		 * 
		 * @return
		 */
		public String getRescheduledFlightNo() {
			return rescheduledFlightNo;
		}
		/**
		 * 
		 * @param rescheduledFlightNo
		 */
		public void setRescheduledFlightNo(String rescheduledFlightNo) {
			this.rescheduledFlightNo = rescheduledFlightNo;
		}
		/**
		 * 
		 * @return
		 */
		public long getRescheduledFlightSeqNo() {
			return rescheduledFlightSeqNo;
		}
		/**
		 * 
		 * @param rescheduledFlightSeqNo
		 */
		public void setRescheduledFlightSeqNo(long rescheduledFlightSeqNo) {
			this.rescheduledFlightSeqNo = rescheduledFlightSeqNo;
		}
		/**
		 * 
		 * @return
		 */
		public int getFlightCarrierId() {
			return flightCarrierId;
		}
		/**
		 * 
		 * @param flightCarrierId
		 */
		public void setFlightCarrierId(int flightCarrierId) {
			this.flightCarrierId = flightCarrierId;
		}
		/**
		 * 
		 * @return
		 */
		public String getFlightNumber() {
			return flightNumber;
		}
		/**
		 * 
		 * @param flightNumber
		 */

		public void setFlightNumber(String flightNumber) {
			this.flightNumber = flightNumber;
		}
		/**
		 * 
		 * @return
		 */

		public long getFlightSequenceNumber() {
			return flightSequenceNumber;
		}
		/**
		 * 
		 * @param flightSequenceNumber
		 */

		public void setFlightSequenceNumber(long flightSequenceNumber) {
			this.flightSequenceNumber = flightSequenceNumber;
		}
		
		
		/**
		 * @param companyCode
		 * @param billingBasis
		 * @param flightNumber
		 * @param flightSequenceNumber
		 * @param flightCarrierId
		 */
		private MRAIrregularityDetailsPK(String companyCode,String billingBasis,String consigndocno,long consigndocseqno,
				String postalcode, int rescheduledFlightCarrierId,String rescheduledFlightNo,int rescheduledFlightSeqNo,
				int flightCarrierId,String flightNumber,int flightSequenceNumber) {
			
			this.companyCode = companyCode;
			this.billingBasis = billingBasis;
			this.consigndocno = consigndocno;
			this.consigndocseqno = consigndocseqno;
			this.postalcode= postalcode;
			this.rescheduledFlightCarrierId = rescheduledFlightCarrierId;
			this.rescheduledFlightNo = rescheduledFlightNo;
			this.rescheduledFlightSeqNo = rescheduledFlightSeqNo;
			this.flightCarrierId = flightCarrierId;
			this.flightNumber = flightNumber;
			this.flightSequenceNumber = flightSequenceNumber;
		}

		/** 
		 * @param obj
		 * @return
		 */
		public boolean equals(Object obj) {
			if (obj != null) {
				return this.hashCode() == obj.hashCode();
			}
			return false;
		}
		
		/** 
		 * @return int
		 */
		public int hashCode() {
			return new StringBuffer().append(companyCode).append(billingBasis)
				.append(consigndocno).append(consigndocseqno)
					.append(postalcode).append(rescheduledFlightCarrierId)
						.append(rescheduledFlightNo).append(rescheduledFlightSeqNo)
						.append(flightCarrierId).append(flightNumber)
						.append(flightSequenceNumber).toString().hashCode();
		}
		
		/**
		 * generated by xibase.tostring plugin at 1 October, 2014 1:13:54 PM IST
		 */
		@Override
		public String toString() {
			StringBuilder sbul = new StringBuilder(330);
			sbul.append("MRAIrregularityDetailsPK [ ");
			sbul.append("billingBasis '").append(this.billingBasis);
			sbul.append("', companyCode '").append(this.companyCode);
			sbul.append("', consigndocno '").append(this.consigndocno);
			sbul.append("', consigndocseqno '").append(this.consigndocseqno);
			sbul.append("', flightCarrierId '").append(this.flightCarrierId);
			sbul.append("', flightNumber '").append(this.flightNumber);
			sbul.append("', flightSequenceNumber '").append(
					this.flightSequenceNumber);
			sbul.append("', postalcode '").append(this.postalcode);
			sbul.append("', rescheduledFlightCarrierId '").append(
					this.rescheduledFlightCarrierId);
			sbul.append("', rescheduledFlightNo '").append(
					this.rescheduledFlightNo);
			sbul.append("', rescheduledFlightSeqNo '").append(
					this.rescheduledFlightSeqNo);
			sbul.append("' ]");
			return sbul.toString();
		}
		
		
		
		

	}


