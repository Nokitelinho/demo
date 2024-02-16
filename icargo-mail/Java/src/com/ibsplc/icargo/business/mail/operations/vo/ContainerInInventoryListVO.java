/*
 * ContainerInInventoryListVO.java Created on JUN 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-5991 
 * 
 *
 */
public class ContainerInInventoryListVO  extends AbstractVO{
		   
	
	        private String companyCode; 
		    private String  containerType;
		    private String uldNumber;
		    private int totalBags;
		    //private double totalWeight; 
		    private Measure totalWeight; //added by A-7371
		    private int carrierID;
		    private String currentAirport;
		    private String paBuiltFlag;
	    
	    
	    private LocalDate   scanDate;
	    
	    private Collection<MailInInventoryListVO> mailInInventoryList;
		/**
		 * @return Returns the companyCode.
		 */
		public String getCompanyCode() {
			return companyCode;
		}
		/**
		 * 
		 * @return totalWeight
		 */
		public Measure getTotalWeight() {
			return totalWeight;
		}
		/**
		 * 
		 * @param totalWeight
		 */
		public void setTotalWeight(Measure totalWeight) {
			this.totalWeight = totalWeight;
		}
		/**
		 * @param companyCode The companyCode to set.
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		/**
		 * @return Returns the mailInInventoryList.
		 */
		public Collection<MailInInventoryListVO> getMailInInventoryList() {
			return mailInInventoryList;
		}
		/**
		 * @param mailInInventoryList The mailInInventoryList to set.
		 */
		public void setMailInInventoryList(
				Collection<MailInInventoryListVO> mailInInventoryList) {
			this.mailInInventoryList = mailInInventoryList;
		}
		/**
		 * @return Returns the totalBags.
		 */
		public int getTotalBags() {
			return totalBags;
		}
		/**
		 * @param totalBags The totalBags to set.
		 */
		public void setTotalBags(int totalBags) {
			this.totalBags = totalBags;
		}
		/**
		 * @return Returns the totalWeight.
		 */
		/*public double getTotalWeight() {
			return totalWeight;
		}
		*//**
		 * @param totalWeight The totalWeight to set.
		 *//*
		public void setTotalWeight(double totalWeight) {
			this.totalWeight = totalWeight;
		}*/
		/**
		 * @return Returns the uldNumber.
		 */
		public String getUldNumber() {
			return uldNumber;
		}
		/**
		 * @param uldNumber The uldNumber to set.
		 */
		public void setUldNumber(String uldNumber) {
			this.uldNumber = uldNumber;
		}
		/**
		 * @return Returns the carrierID.
		 */
		public int getCarrierID() {
			return carrierID;
		}
		/**
		 * @param carrierID The carrierID to set.
		 */
		public void setCarrierID(int carrierID) {
			this.carrierID = carrierID;
		}
		/**
		 * @return Returns the currentAirport.
		 */
		public String getCurrentAirport() {
			return currentAirport;
		}
		/**
		 * @param currentAirport The currentAirport to set.
		 */
		public void setCurrentAirport(String currentAirport) {
			this.currentAirport = currentAirport;
		}
		/**
		 * @return Returns the containerType.
		 */
		public String getContainerType() {
			return containerType;
		}
		/**
		 * @param containerType The containerType to set.
		 */
		public void setContainerType(String containerType) {
			this.containerType = containerType;
		}
	
		
		/*
		 * Added By Karthick V as the  part of the NCA Mail Tracking CR .. 
		 */
		
		/**
		 * 
		 * @return
		 */
		public LocalDate  getScanDate() {
			return scanDate ;
		}
		
		/**
		 * @param ScanDate
		 */
		public void setScanDate(LocalDate scanDate) {
			this.scanDate = scanDate;
		}
		public String getPaBuiltFlag() {
			return paBuiltFlag;
		}
		public void setPaBuiltFlag(String paBuiltFlag) {
			this.paBuiltFlag = paBuiltFlag;
		}
}
