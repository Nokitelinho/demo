package com.ibsplc.icargo.business.mail.operations.vo;




import java.util.Collection;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * Mail Webesrvice VO for GHA input to iCargo.
 * @author A-3109
 *
 */
public class MailWebserviceVO extends AbstractVO {
	
	 public static final String MAIL_STATUS_REASSIGN ="RSN";
	 
	 public static final String MAIL_STATUS_EXPORT = "EXP";
	 
	 public static final String MAIL_STATUS_CANCEL = "CNL";
	
	private String companyCode;
	
	private String hhtVersion;
	
	private String scanningPort;
	
	private int messagePartId;
	
	private String product;
	
	private String scanType;
	
	private String carrierCode;
	
	private String flightNumber;
	
	private LocalDate flightDate;
	
	private String containerPou;
	
	private String containerNumber;
	
	private String containerType;
	
	private String containerDestination;
	
	private String containerPol;
	
	private String remarks;
	
	private String mailBagId;
	
	private String damageCode;
	
	private String damageRemarks;
	
	private String offloadReason;
	
	private String returnCode;
	
	private String toContainerType;
	
	private String toContainer;
	
	private String toCarrierCod;
	
	private String toFlightNumber;
	
	private Collection<FlightValidationVO> flightValidationVOS;
	
	
	public Collection<FlightValidationVO> getFlightValidationVOS() {
	return flightValidationVOS;
}
public void setFlightValidationVOS(
		Collection<FlightValidationVO> flightValidationVOS) {
	this.flightValidationVOS = flightValidationVOS;
}
	
	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getHhtVersion() {
		return hhtVersion;
	}

	public void setHhtVersion(String hhtVersion) {
		this.hhtVersion = hhtVersion;
	}

	public String getScanningPort() {
		return scanningPort;
	}

	public void setScanningPort(String scanningPort) {
		this.scanningPort = scanningPort;
	}

	public int getMessagePartId() {
		return messagePartId;
	}

	public void setMessagePartId(int messagePartId) {
		this.messagePartId = messagePartId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getScanType() {
		return scanType;
	}

	public void setScanType(String scanType) {
		this.scanType = scanType;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public LocalDate getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(LocalDate flightDate) {
		this.flightDate = flightDate;
	}

	public String getContainerPou() {
		return containerPou;
	}

	public void setContainerPou(String containerPou) {
		this.containerPou = containerPou;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerDestination() {
		return containerDestination;
	}

	public void setContainerDestination(String containerDestination) {
		this.containerDestination = containerDestination;
	}

	public String getContainerPol() {
		return containerPol;
	}

	public void setContainerPol(String containerPol) {
		this.containerPol = containerPol;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getMailBagId() {
		return mailBagId;
	}

	public void setMailBagId(String mailBagId) {
		this.mailBagId = mailBagId;
	}

	public String getDamageCode() {
		return damageCode;
	}

	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}

	public String getDamageRemarks() {
		return damageRemarks;
	}

	public void setDamageRemarks(String damageRemarks) {
		this.damageRemarks = damageRemarks;
	}

	public String getOffloadReason() {
		return offloadReason;
	}

	public void setOffloadReason(String offloadReason) {
		this.offloadReason = offloadReason;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getToContainerType() {
		return toContainerType;
	}

	public void setToContainerType(String toContainerType) {
		this.toContainerType = toContainerType;
	}

	public String getToContainer() {
		return toContainer;
	}

	public void setToContainer(String toContainer) {
		this.toContainer = toContainer;
	}

	public String getToCarrierCod() {
		return toCarrierCod;
	}

	public void setToCarrierCod(String toCarrierCod) {
		this.toCarrierCod = toCarrierCod;
	}

	public String getToFlightNumber() {
		return toFlightNumber;
	}

	public void setToFlightNumber(String toFlightNumber) {
		this.toFlightNumber = toFlightNumber;
	}

	public LocalDate getToFlightDate() {
		return toFlightDate;
	}

	public void setToFlightDate(LocalDate toFlightDate) {
		this.toFlightDate = toFlightDate;
	}

	public String getToContainerPou() {
		return toContainerPou;
	}

	public void setToContainerPou(String toContainerPou) {
		this.toContainerPou = toContainerPou;
	}

	public String getToContainerDestination() {
		return toContainerDestination;
	}

	public void setToContainerDestination(String toContainerDestination) {
		this.toContainerDestination = toContainerDestination;
	}

	public String getConsignmentDocNumber() {
		return consignmentDocNumber;
	}

	public void setConsignmentDocNumber(String consignmentDocNumber) {
		this.consignmentDocNumber = consignmentDocNumber;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public boolean isPAbuilt() {
		return isPAbuilt;
	}

	public void setPAbuilt(boolean isPAbuilt) {
		this.isPAbuilt = isPAbuilt;
	}

	public boolean isDelivered() {
		return isDelivered;
	}

	public void setDelivered(boolean isDelivered) {
		this.isDelivered = isDelivered;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public LocalDate getScanDateTime() {
		return scanDateTime;
	}

	public void setScanDateTime(LocalDate scanDateTime) {
		this.scanDateTime = scanDateTime;
	}
	

	public String getUldFullIndicator() {
		return uldFullIndicator;
	}
	public void setUldFullIndicator(String uldFullIndicator) {
		this.uldFullIndicator = uldFullIndicator;
	}


	private LocalDate toFlightDate;
	
	private String toContainerPou;
	
	private String toContainerDestination;
	
	private String consignmentDocNumber;
	
	private int serialNumber;
	
	private boolean isPAbuilt;
	
	private boolean isDelivered;
	
	private String userName;
	
	private LocalDate scanDateTime;
	
	private String uldFullIndicator;
	
}
