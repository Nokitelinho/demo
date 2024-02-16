/*
 * MRAAccountingVO.java created on Nov 12 ,2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */
package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author A-3229
 *
 */
public class MRAAccountingVO extends AbstractVO {
			
		//dsn pks
		private String companyCode;
	    private String billingBasis;
	    private int csgSequenceNumber;
		private String csgDocumentNumber;
		private String poaCode;
		private String dsn;
		
		private String accEntryId;
		private String functionPoint;
		private String accountingMonth;
		private String accountingString;
		private String accountCode;
		private String accountName;
		private Money debitAmount;
		private Money creditAmount;
		private String details;
		
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
		 * @return Returns the accEntryId
		 */
		public String getAccEntryId() {
			return accEntryId;
		}
		/**
		 * @param accEntryId the accEntryId to set
		 */
		public void setAccEntryId(String accEntryId) {
			this.accEntryId = accEntryId;
		}
		
		/**
		 * @return Returns the accountCode
		 */
		public String getAccountCode() {
			return accountCode;
		}
		/**
		 * @param accountCode the accountCode to set
		 */
		public void setAccountCode(String accountCode) {
			this.accountCode = accountCode;
		}
		/**
		 * @return Returns the accountingMonth
		 */
		public String getAccountingMonth() {
			return accountingMonth;
		}
		/**
		 * @param accountingMonth the accountingMonth to set
		 */
		public void setAccountingMonth(String accountingMonth) {
			this.accountingMonth = accountingMonth;
		}
		/**
		 * @return Returns the accountingString
		 */
		public String getAccountingString() {
			return accountingString;
		}
		/**
		 * @param accountingString the accountingString to set
		 */
		public void setAccountingString(String accountingString) {
			this.accountingString = accountingString;
		}
		/**
		 * @return Returns the accountName
		 */
		public String getAccountName() {
			return accountName;
		}
		/**
		 * @param accountName the accountName to set
		 */
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		/**
		 * @return Returns the baseCurrency
		 */
		public String getBaseCurrency() {
			return baseCurrency;
		}
		/**
		 * @param baseCurrency the baseCurrency to set
		 */
		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}
		/**
		 * @return Returns the creditAmount
		 */
		public Money getCreditAmount() {
			return creditAmount;
		}
		/**
		 * @param creditAmount the creditAmount to set
		 */
		public void setCreditAmount(Money creditAmount) {
			this.creditAmount = creditAmount;
		}
		/**
		 * @return Returns the debitAmount
		 */
		public Money getDebitAmount() {
			return debitAmount;
		}
		/**
		 * @param debitAmount the debitAmount to set
		 */
		public void setDebitAmount(Money debitAmount) {
			this.debitAmount = debitAmount;
		}
		/**
		 * @return Returns the details
		 */
		public String getDetails() {
			return details;
		}
		/**
		 * @param details the details to set
		 */
		public void setDetails(String details) {
			this.details = details;
		}
		/**
		 * @return Returns the functionPoint
		 */
		public String getFunctionPoint() {
			return functionPoint;
		}
		/**
		 * @param functionPoint the functionPoint to set
		 */
		public void setFunctionPoint(String functionPoint) {
			this.functionPoint = functionPoint;
		}

		

	}

