/*
 * USPSReportingVO.java created on Nov 12 ,2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;



import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author A-3229
 *
 */
public class USPSReportingVO extends AbstractVO {
			
		//dsn pks
		private String companyCode;
	    private String billingBasis;
	    private int csgSequenceNumber;
		private String csgDocumentNumber;
		private String poaCode;
	
		private String dsnNumber;
		private String sectorOrigin;
		private String sectorDestiantion;
		private int GCM;
		private Money baseTotalAmt;
		private double lhDollarRate;
		private double thDollarRate;
		private String invoiceNumber;
		private LocalDate invoiceDate;
		private String recStatus;
		
		private String baseCurrency;
		
	
	
		/**
		 * @return Returns the companyCode
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
		 * @return Returns the dsnNumber.
		 */
		public String getDsnNumber() {
			return dsnNumber;
		}

		/**
		 * @param dsnNumber The dsnNumber to set.
		 */
		public void setDsnNumber(String dsnNumber) {
			this.dsnNumber = dsnNumber;
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
		 * @return Returns the baseCurrency.
		 */
		public String getBaseCurrency() {
			return baseCurrency;
		}
		/**
		 * @param baseCurrency
		 *            The baseCurrency to set.
		 */
		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}
		/**
		 * @return Returns the baseTotalAmt.
		 */
		public Money getBaseTotalAmt() {
			return baseTotalAmt;
		}
		/**
		 * @param baseTotalAmt
		 *            The baseTotalAmt to set.
		 */
		public void setBaseTotalAmt(Money baseTotalAmt) {
			this.baseTotalAmt = baseTotalAmt;
		}
		/**
		 * @return Returns the GCM.
		 */
		public int getGCM() {
			return GCM;
		}
		/**
		 * @param GCM
		 *            The GCM to set.
		 */
		public void setGCM(int gcm) {
			GCM = gcm;
		}
		/**
		 * @return Returns the invoiceDate.
		 */
		public LocalDate getInvoiceDate() {
			return invoiceDate;
		}
		/**
		 * @param invoiceDate
		 *            The invoiceDate to set.
		 */
		public void setInvoiceDate(LocalDate invoiceDate) {
			this.invoiceDate = invoiceDate;
		}
		/**
		 * @return Returns the invoiceNumber.
		 */
		public String getInvoiceNumber() {
			return invoiceNumber;
		}
		/**
		 * @param invoiceNumber
		 *            The invoiceNumber to set.
		 */
		public void setInvoiceNumber(String invoiceNumber) {
			this.invoiceNumber = invoiceNumber;
		}
		/**
		 * @return Returns the lhDollarRate.
		 */
		public double getLhDollarRate() {
			return lhDollarRate;
		}
		/**
		 * @param lhDollarRate
		 *            The lhDollarRate to set.
		 */
		public void setLhDollarRate(double lhDollarRate) {
			this.lhDollarRate = lhDollarRate;
		}
		/**
		 * @return Returns the recStatus.
		 */
		public String getRecStatus() {
			return recStatus;
		}
		/**
		 * @param recStatus
		 *            The recStatus to set.
		 */
		public void setRecStatus(String recStatus) {
			this.recStatus = recStatus;
		}
		/**
		 * @return Returns the sectorDestiantion.
		 */
		public String getSectorDestiantion() {
			return sectorDestiantion;
		}
		/**
		 * @param sectorDestiantion
		 *            The sectorDestiantion to set.
		 */
		public void setSectorDestiantion(String sectorDestiantion) {
			this.sectorDestiantion = sectorDestiantion;
		}
		/**
		 * @return Returns the sectorOrigin.
		 */
		public String getSectorOrigin() {
			return sectorOrigin;
		}
		/**
		 * @param sectorOrigin
		 *            The sectorOrigin to set.
		 */
		public void setSectorOrigin(String sectorOrigin) {
			this.sectorOrigin = sectorOrigin;
		}
		/**
		 * @return Returns the thDollarRate.
		 */
		public double getThDollarRate() {
			return thDollarRate;
		}
		/**
		 * @param thDollarRate
		 *            The thDollarRate to set.
		 */
		public void setThDollarRate(double thDollarRate) {
			this.thDollarRate = thDollarRate;
		}
	
		

	}


