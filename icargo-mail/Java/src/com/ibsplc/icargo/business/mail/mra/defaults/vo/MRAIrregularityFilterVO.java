/*
 * MRAIrregularityFilterVO.java created on Sep 26, 2008
 *Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms. 
 */

package com.ibsplc.icargo.business.mail.mra.defaults.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * 
 * @author A-3229
 *
 */

public class MRAIrregularityFilterVO extends AbstractVO{
	
  private String companyCode;
	
  private LocalDate fromDate;
  
  private LocalDate toDate;
  
  private String irpStatus;
  
  private String offloadStation;
  
  private String origin;
  
  private String destination;
  
  private String shipmentPrefix;
  
  private String documentNumber;
  
  private LocalDate effectiveDate;
  
  private int pageNumber;
  
  private String dsn;
  
  private String billingBasis;
  
  private int csgSequenceNumber;
  
  private String csgDocumentNumber;
  
  private String poaCode;
  
  private String subSystem;
  
  private String baseCurrency;
	
  
  /**
   * for pagination in page aware multimapper
   */
  private int absoluteIndex;
		  /**
		   * @return Returns the destination.
		   */
		  
		  public String getDestination() {
			return destination;
		  }
			/**
			 * @param destination The destination to set.
			 */
			public void setDestination(String destination) {
				this.destination = destination;
			}
			/**
			 * @return Returns the documentNumber.
			 */
		public String getDocumentNumber() {
			return documentNumber;
		}
		/**
		 * @param documentNumber The documentNumber to set.
		 */
		
		public void setDocumentNumber(String documentNumber) {
			this.documentNumber = documentNumber;
		}
		/**
		 * @return Returns the effectiveDate.
		 */
		public LocalDate getEffectiveDate() {
			return effectiveDate;
		}
		/**
		 * @param effectiveDate The effectiveDate to set.
		 */
		public void setEffectiveDate(LocalDate effectiveDate) {
			this.effectiveDate = effectiveDate;
		}
		/**
		 * @return Returns the fromDate.
		 */
		public LocalDate getFromDate() {
			return fromDate;
		}
		/**
		 * @param fromDate The fromDate to set.
		 */
		public void setFromDate(LocalDate fromDate) {
			this.fromDate = fromDate;
		}
		/**
		 * @return Returns the irpStatus.
		 */
		public String getIrpStatus() {
			return irpStatus;
		}
		/**
		 * @param irpStatus The irpStatus to set.
		 */
		public void setIrpStatus(String irpStatus) {
			this.irpStatus = irpStatus;
		}
		/**
		 * @return Returns the offloadStation.
		 */
		public String getOffloadStation() {
			return offloadStation;
		}
		/**
		 * @param offloadStation The offloadStation to set.
		 */
		public void setOffloadStation(String offloadStation) {
			this.offloadStation = offloadStation;
		}
		/**
		 * @return Returns the origin.
		 */
		public String getOrigin() {
			return origin;
		}
		/**
		 * @param origin The origin to set.
		 */
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		/**
		 * @return Returns the shipmentPrefix.
		 */
		public String getShipmentPrefix() {
			return shipmentPrefix;
		}
		/**
		 * @param shipmentPrefix The shipmentPrefix to set.
		 */
		public void setShipmentPrefix(String shipmentPrefix) {
			this.shipmentPrefix = shipmentPrefix;
		}
		/**
		 * @return Returns the toDate.
		 */
		public LocalDate getToDate() {
			return toDate;
		}
		/**
		 * @param toDate The toDate to set.
		 */
		public void setToDate(LocalDate toDate) {
			this.toDate = toDate;
		}
		/**
		 * @return Returns the companyCode.
		 */
		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 * @param companyCode The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		/**
		 * @return Returns the pageNumber.
		 */
		public int getPageNumber() {
			return pageNumber;
		}
		/**
		 * @param pageNumber The pageNumber to set.
		 */
		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}
		  
		/**
		 * @return absoluteIndex
		 */
		public int getAbsoluteIndex() {
			return absoluteIndex;
		}
		/**
		 * @param absoluteIndex
		 */
		public void setAbsoluteIndex(int absoluteIndex) {
			this.absoluteIndex = absoluteIndex;
		}
		/**
		 * @return dsn
		 */
		public String getDsn() {
			return dsn;
		}
		/**
		 * @param dsn
		 */
		public void setDsn(String dsn) {
			this.dsn = dsn;
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
		 * @return csgDocumentNumber
		 */
		public String getCsgDocumentNumber() {
			return csgDocumentNumber;
		}
		/**
		 * @param csgDocumentNumber
		 */
		public void setCsgDocumentNumber(String csgDocumentNumber) {
			this.csgDocumentNumber = csgDocumentNumber;
		}
		/**
		 * @return csgSequenceNumber
		 */
		public int getCsgSequenceNumber() {
			return csgSequenceNumber;
		}
		/**
		 * @param csgSequenceNumber
		 */
		public void setCsgSequenceNumber(int csgSequenceNumber) {
			this.csgSequenceNumber = csgSequenceNumber;
		}
		/**
		 * @return poaCode
		 */
		public String getPoaCode() {
			return poaCode;
		}
		/**
		 * @param poaCode
		 */
		public void setPoaCode(String poaCode) {
			this.poaCode = poaCode;
		}
		/**
		 * @return subSystem
		 */
		public String getSubSystem() {
			return subSystem;
		}
		/**
		 * @param subSystem
		 */
		public void setSubSystem(String subSystem) {
			this.subSystem = subSystem;
		}
		/**
		 * @return baseCurrency
		 */
		public String getBaseCurrency() {
			return baseCurrency;
		}
		/**
		 * @param baseCurrency
		 */
		public void setBaseCurrency(String baseCurrency) {
			this.baseCurrency = baseCurrency;
		}
  
	
	
}
