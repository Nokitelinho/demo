

	/*
	 * DSNRoutingFilterVO.java created on Sep 1, 2008
	 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
	 *
	 * This software is the proprietary information of IBS Software Services (P) Ltd.
	 * Use is subject to license terms. 
	 */
	package com.ibsplc.icargo.business.mail.mra.defaults.vo;

	import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

	/**
	 * @author a-3229
	 * 
	 */
	public class DSNRoutingFilterVO extends AbstractVO {


		private String dsn;
		
		private LocalDate dsnDate;

		private String flightNumber;

		private String companyCode;
		
		private String billingBasis;
		
		private int csgSequenceNumber;
		
		private String csgDocumentNumber;
		
		private String poaCode;
	
		private long mailSequenceNumber;
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
		 * @return Returns the companyCode.
		 */
		public String getCompanyCode() {
			return companyCode;
		}

		/**
		 * @param companyCode
		 *            The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		/**
		 * @return Returns the dsn.
		 */
		public String getDsn() {
			return dsn;
		}

		/**
		 * @param dsn
		 *            The dsn to set.
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
		}

		/**
		 * @return Returns the dsnDate.
		 */
		
		public LocalDate getDsnDate() {
			return dsnDate;
		}

		/**
		 * @param dsnDate
		 *            The dsnDate to set.
		 */
		public void setDsnDate(LocalDate dsnDate) {
			this.dsnDate = dsnDate;
		}

		/**
		 * @return Returns the billingBasis.
		 */
		public String getBillingBasis() {
			return billingBasis;
		}
		
		/**
		 * @param billingBasis
		 *            The billingBasis to set.
		 */
		public void setBillingBasis(String billingBasis) {
			this.billingBasis = billingBasis;
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
		 * @return Returns the mailSequenceNumber.
		 */
		public long getMailSequenceNumber() {
			return mailSequenceNumber;
		}
		/**
		 * @param mailSequenceNumber The mailSequenceNumber to set.
		 */
		public void setMailSequenceNumber(long mailSequenceNumber) {
			this.mailSequenceNumber = mailSequenceNumber;
		}
}
