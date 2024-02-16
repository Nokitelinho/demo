package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-3109
 */
public class MailOnHandDetailsVO extends AbstractVO {
	
	     private String companyCode;
		 /**
		 * @return the companyCode
		 */
		public String getCompanyCode() {
			return companyCode;
		}

		/**
		 * @param companyCode the companyCode to set
		 */
		public void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}
		private String currentAirport;
	     private String destination;
		 private String subclassGroup;
		 private String uldCount; 
		 private int mailTrolleyCount; 
		 private int noOfDaysInCurrentLoc;
		 private LocalDate scnDate; 
		 private String scanPort; 
		 private String uldCountDisplay; 
		 //Added by a-5945 for ICRD-104487
		 private int noOfMTSpace;
		 
		 
		public String getUldCountDisplay() {
			return uldCountDisplay;
		}

		public void setUldCountDisplay(String uldCountDisplay) {
			this.uldCountDisplay = uldCountDisplay;
		}
		 
		public String getScanPort() {
			return scanPort;
		}

		public void setScanPort(String scanPort) {
			this.scanPort = scanPort;
		}

		/**
		 * @return the scnDate
		 */
		public LocalDate getScnDate() {
			return scnDate;
		}

		/**
		 * @param scnDate the scnDate to set
		 */
		public void setScnDate(LocalDate scnDate) {
			this.scnDate = scnDate;
		}

		public String getCurrentAirport() {	
			
			
			return currentAirport;
		}
		
		/**
		 * Sets the current airport.
		 *
		 * @param currentAirport the new current airport
		 */
		public void setCurrentAirport(String currentAirport) {
			this.currentAirport = currentAirport;
		}
		public String getDestination() {
			return destination;
		}
		public void setDestination(String destination) {
			this.destination = destination;
		}
		public String getSubclassGroup() {
			return subclassGroup;
		}
		public void setSubclassGroup(String subclassGroup) {
			this.subclassGroup = subclassGroup;
		}
		public String getUldCount() {
			return uldCount;
		}
		public void setUldCount(String uldCount) {
			this.uldCount = uldCount;
		}
		public int getMailTrolleyCount() {
			return mailTrolleyCount;
		}
		public void setMailTrolleyCount(int mailTrolleyCount) {
			this.mailTrolleyCount = mailTrolleyCount;
		}
		public int getNoOfDaysInCurrentLoc() {
			return noOfDaysInCurrentLoc;
		}
		public void setNoOfDaysInCurrentLoc(int noOfDaysInCurrentLoc) {
			this.noOfDaysInCurrentLoc = noOfDaysInCurrentLoc;
		}
		 //Added by a-5945 for ICRD-104487
		public void setNoOfMTSpace(int noOfMTSpace) {
			this.noOfMTSpace = noOfMTSpace;
		}
		public int getNoOfMTSpace() {
			return noOfMTSpace;
		}
	
	
	
}
