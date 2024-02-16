/**
 *
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import java.util.List;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-5991
 *
 */
public class HandoverVO extends AbstractVO {


	private String companyCode;
	  private String dstExgOffice;
	  private String destAirport;
	  private String handOverType;
	  private String handOverID;
	  private String nestID;
	  private String handOverHandler;
	  private String handOverCarrierCode;
	  private String carrierCode;
	  private String flightNumber;
	  private String origin;
	  private String destination;
	  private LocalDate handOverdate_time;
	  private List<String> mailId;
      private List<String> action;
	  private String attributeReason;
	  private String attributeCarrier;
	  private String dateTime;
	  private long fltSeqNum;
	  private LocalDate flightDate;
	  private String orgExgOffice;
      private boolean isInvalidFlight;   
      private String tbaFlightNeeded;
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

	  public String getDstExgOffice(){
		  return dstExgOffice;
	  }

	/**
	 * @param dstExgOffice the dstExgOffice to set
	 */
	public void setDstExgOffice(String dstExgOffice) {
		this.dstExgOffice = dstExgOffice;
	}
	/**
	 * @return the destAirport
	 */
	public String getDestAirport() {
		return destAirport;
	}
	/**
	 * @param destAirport the destAirport to set
	 */
	public void setDestAirport(String destAirport) {
		this.destAirport = destAirport;
	}
	/**
	 * @return the handOverType
	 */
	public String getHandOverType() {
		return handOverType;
	}
	/**
	 * @param handOverType the handOverType to set
	 */
	public void setHandOverType(String handOverType) {
		this.handOverType = handOverType;
	}
	/**
	 * @return the handOverID
	 */
	public String getHandOverID() {
		return handOverID;
	}
	/**
	 * @param handOverID the handOverID to set
	 */
	public void setHandOverID(String handOverID) {
		this.handOverID = handOverID;
	}
	/**
	 * @return the carrierCode
	 */
	public String getCarrierCode() {
		return carrierCode;
	}
	/**
	 * @param carrierCode the carrierCode to set
	 */
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	/**
	 * @return the flightNumber
	 */
	public String getFlightNumber() {
		return flightNumber;
	}
	/**
	 * @param flightNumber the flightNumber to set
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}
	/**
	 * @return the origin
	 */
	public String getOrigin() {
		return origin;
	}
	/**
	 * @param origin the origin to set
	 */
	public void setOrigin(String origin) {
		this.origin = origin;
	}
	/**
	 * @return the destination
	 */
	public String getDestination() {
		return destination;
	}
	/**
	 * @param destination the destination to set
	 */
	public void setDestination(String destination) {
		this.destination = destination;
	}
	/**
	 * @return the handOverdate_time
	 */
	public LocalDate getHandOverdate_time() {
		return handOverdate_time;
	}
	/**
	 * @param handOverdate_time the handOverdate_time to set
	 */
	public void setHandOverdate_time(LocalDate handOverdate_time) {
		this.handOverdate_time = handOverdate_time;
	}
	/**
	 * @return the mailId
	 */
	public List<String> getMailId() {
		return mailId;
	}
	/**
	 * @param mailId the mailId to set
	 */
	public void setMailId(List<String> mailId) {
		this.mailId = mailId;
	}
	/**
	 * @return the attributeReason
	 */
	public String getAttributeReason() {
		return attributeReason;
	}
	/**
	 * @param attributeReason the attributeReason to set
	 */
	public void setAttributeReason(String attributeReason) {
		this.attributeReason = attributeReason;
	}
	/**
	 * @return the attributeCarrier
	 */
	public String getAttributeCarrier() {
		return attributeCarrier;
	}
	/**
	 * @param attributeCarrier the attributeCarrier to set
	 */
	public void setAttributeCarrier(String attributeCarrier) {
		this.attributeCarrier = attributeCarrier;
	}

	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}

	/**
	 * @return the dateTime
	 */
	public String getDateTime() {
		return dateTime;
	}
	/**
	 * @param fltSeqNum the fltSeqNum to set
	 */
	public void setFltSeqNum(long fltSeqNum) {
		this.fltSeqNum = fltSeqNum;
	}
	/**
	 * @return the fltSeqNum
	 */
	public long getFltSeqNum() {
		return fltSeqNum;
	}
	/**
	 * @return the flightDate
	 */
	public LocalDate getFlightDate() {
		return flightDate;
	}
	/**
	 * @param flightDate the flightDate to set
	 */
	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}
	/**
	 * @return the nestID
	 */
	public String getNestID() {
		return nestID;
	}
	/**
	 * @param nestID the nestID to set
	 */
	public void setNestID(String nestID) {
		this.nestID = nestID;
	}
	/**
	 * @return the handOverHandler
	 */
	public String getHandOverHandler() {
		return handOverHandler;
	}
	/**
	 * @param handOverHandler the handOverHandler to set
	 */
	public void setHandOverHandler(String handOverHandler) {
		this.handOverHandler = handOverHandler;
	}
	/**
	 * @return the handOverCarrierCode
	 */
	public String getHandOverCarrierCode() {
		return handOverCarrierCode;
	}
	/**
	 * @param handOverCarrierCode the handOverCarrierCode to set
	 */
	public void setHandOverCarrierCode(String handOverCarrierCode) {
		this.handOverCarrierCode = handOverCarrierCode;
	}
	/**
	 * @return the action
	 */
	public List<String> getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(List<String> action) {
		this.action = action;
	}
	public String getOrgExgOffice() {
		return orgExgOffice;
	}
	public void setOrgExgOffice(String orgExgOffice) {
		this.orgExgOffice = orgExgOffice;
	}
	public boolean isInvalidFlight() {
		return isInvalidFlight;
	}
	public void setInvalidFlight(boolean isInvalidFlight) {
		this.isInvalidFlight = isInvalidFlight;
	}	 
    public String getTbaFlightNeeded() {
		return tbaFlightNeeded;
	}

	public void setTbaFlightNeeded(String tbaFlightNeeded) {
		this.tbaFlightNeeded = tbaFlightNeeded;
	}

}
