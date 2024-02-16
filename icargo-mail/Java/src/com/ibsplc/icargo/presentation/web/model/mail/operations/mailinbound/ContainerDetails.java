package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import java.util.ArrayList;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.DespatchDetails;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import java.util.List;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	27-Sep-2018		:	Draft
 */
public class ContainerDetails {

	private String containerno;
	
	private String containerNumberWithBulk;
	
	private String pol;
	
	private String pou;
	
	private String mailBagCount;
	
	private String mailBagWeight;
	
	private String containerPa;
	
	private String assignmentDate;
	
	private String onwardFlight;
	
	private String transfferCarrier;
	
	private String wareHouse;
	
	private String location;
	
	private String remarks;
	
	private String bellyCartId;
	
	private String weightUnit;
	
	private String containerType;
	
	private String carrierId;
	
	private String carrierCode;
	
	private String flightSeqNumber;
	
	private String flightNumber;
	
	private String pageNumber;
	
	private String pageSize;
	
	private boolean barrowCheck;
	
	private boolean recievedCheck;
	
	private boolean deliveredCheck;
	
	private boolean paBuiltCheck;
	
	private boolean intactCheck;
	
	private boolean checkBoxSelect;
	
	private String readyForDeliveryTime;
	
	private String readyForDeliveryDate;
	
	private MailBagDetails mailBagDetails;
	
	private String destination;
	
	private ArrayList<DSNVO> dsnVos;
	
	private ArrayList<DSNDetails> dsnDetailsCollection;
	
	private ArrayList<MailBagDetails> mailBagDetailsCollection;
	
    private String containerPureTransfer;//Added by A-8464 for ICRD-328502

    private String minReqDelveryTime;
    private String containerNumber;
    private double actualWeight;
    private String contentId; 
    private int legSerialNumber;
    
    private String transitFlag;
    private String actWgtSta;

	

	private List<DespatchDetails> desptachDetailsCollection;

    public List<DespatchDetails> getDesptachDetailsCollection() {
		return desptachDetailsCollection;
	}

	public void setDesptachDetailsCollection(List<DespatchDetails> desptachDetailsCollection) {
		this.desptachDetailsCollection = desptachDetailsCollection;
	}
	public String getContainerno() {
		return containerno;
	}

	public void setContainerno(String containerno) {
		this.containerno = containerno;
	}

	public String getPol() {
		return pol;
	}

	public void setPol(String pol) {
		this.pol = pol;
	}

	public String getPou() {
		return pou;
	}

	public void setPou(String pou) {
		this.pou = pou;
	}

	public String getMailBagCount() {
		return mailBagCount;
	}

	public void setMailBagCount(String mailBagCount) {
		this.mailBagCount = mailBagCount;
	}

	public String getMailBagWeight() {
		return mailBagWeight;
	}

	public void setMailBagWeight(String mailBagWeight) {
		this.mailBagWeight = mailBagWeight;
	}

	public MailBagDetails getMailBagDetails() {
		return mailBagDetails;
	}

	public void setMailBagDetails(MailBagDetails mailBagDetails) {
		this.mailBagDetails = mailBagDetails;
	}

	public ArrayList<MailBagDetails> getMailBagDetailsCollection() {
		return mailBagDetailsCollection;
	}

	public void setMailBagDetailsCollection(ArrayList<MailBagDetails> mailBagDetailsCollection) {
		this.mailBagDetailsCollection = mailBagDetailsCollection;
	}

	public String getContainerPa() {
		return containerPa;
	}

	public void setContainerPa(String containerPa) {
		this.containerPa = containerPa;
	}

	public String getAssignmentDate() {
		return assignmentDate;
	}

	public void setAssignmentDate(String assignmentDate) {
		this.assignmentDate = assignmentDate;
	}

	public String getOnwardFlight() {
		return onwardFlight;
	}

	public void setOnwardFlight(String onwardFlight) {
		this.onwardFlight = onwardFlight;
	}

	public String getTransfferCarrier() {
		return transfferCarrier;
	}

	public void setTransfferCarrier(String transfferCarrier) {
		this.transfferCarrier = transfferCarrier;
	}

	public String getWareHouse() {
		return wareHouse;
	}

	public void setWareHouse(String wareHouse) {
		this.wareHouse = wareHouse;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getBellyCartId() {
		return bellyCartId;
	}

	public void setBellyCartId(String bellyCartId) {
		this.bellyCartId = bellyCartId;
	}

	public String getWeightUnit() {
		return weightUnit;
	}

	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public ArrayList<DSNVO> getDsnVos() {
		return dsnVos;
	}

	public void setDsnVos(ArrayList<DSNVO> dsnVos) {
		this.dsnVos = dsnVos;	
	}

	public ArrayList<DSNDetails> getDsnDetailsCollection() {
		return dsnDetailsCollection;
	}

	public void setDsnDetailsCollection(ArrayList<DSNDetails> dsnDetailsCollection) {
		this.dsnDetailsCollection = dsnDetailsCollection;
	}

	public String getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightSeqNumber() {
		return flightSeqNumber;
	}

	public void setFlightSeqNumber(String flightSeqNumber) {
		this.flightSeqNumber = flightSeqNumber;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getContainerNumberWithBulk() {
		return containerNumberWithBulk;
	}

	public void setContainerNumberWithBulk(String containerNumberWithBulk) {
		this.containerNumberWithBulk = containerNumberWithBulk;
	}

	public String getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getPageSize() {
		return pageSize;
	}

	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}

	public boolean isCheckBoxSelect() {
		return checkBoxSelect;
	}

	public void setCheckBoxSelect(boolean checkBoxSelect) {
		this.checkBoxSelect = checkBoxSelect;
	}

	public boolean isBarrowCheck() {
		return barrowCheck;
	}

	public void setBarrowCheck(boolean barrowCheck) {
		this.barrowCheck = barrowCheck;
	}

	public boolean isRecievedCheck() {
		return recievedCheck;
	}

	public void setRecievedCheck(boolean recievedCheck) {
		this.recievedCheck = recievedCheck;
	}

	public boolean isDeliveredCheck() {
		return deliveredCheck;
	}

	public void setDeliveredCheck(boolean deliveredCheck) {
		this.deliveredCheck = deliveredCheck;
	}

	public boolean isPaBuiltCheck() {
		return paBuiltCheck;
	}

	public void setPaBuiltCheck(boolean paBuiltCheck) {
		this.paBuiltCheck = paBuiltCheck;
	}

	public boolean isIntactCheck() {
		return intactCheck;
	}

	public void setIntactCheck(boolean intactCheck) {
		this.intactCheck = intactCheck;
	}

	public String getReadyForDeliveryTime() {
		return readyForDeliveryTime;
	}

	public void setReadyForDeliveryTime(String readyForDeliveryTime) {
		this.readyForDeliveryTime = readyForDeliveryTime;
	}

	public String getReadyForDeliveryDate() {
		return readyForDeliveryDate;
	}

	public void setReadyForDeliveryDate(String readyForDeliveryDate) {
		this.readyForDeliveryDate = readyForDeliveryDate;
	}

	public String getContainerPureTransfer() {
		return containerPureTransfer;
	}

	public void setContainerPureTransfer(String containerPureTransfer) {
		this.containerPureTransfer = containerPureTransfer;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	public String getMinReqDelveryTime() {
		return minReqDelveryTime;
	}
	public void setMinReqDelveryTime(String minReqDelveryTime) {
		this.minReqDelveryTime = minReqDelveryTime;
	}
	/**
	 * @return the containerNumber
	 */
	public String getContainerNumber() {
		return containerNumber;
	}
	/**
	 * @param containerNumber the containerNumber to set
	 */
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}
	/**
	 * @return the actualWeight
	 */
	public double getActualWeight() {
		return actualWeight;
	}
	/**
	 * @param actualWeight the actualWeight to set
	 */
	public void setActualWeight(double actualWeight) {
		this.actualWeight = actualWeight;
	}
	/**
	 * @return the contentId
	 */
	public String getContentId() {
		return contentId;
	}
	/**
	 * @param contentId the contentId to set
	 */
	public void setContentId(String contentId) {
		this.contentId = contentId;
	}
	/**
	 * @return the legSerialNumber
	 */
	public int getLegSerialNumber() {
		return legSerialNumber;
	}
	/**
	 * @param legSerialNumber the legSerialNumber to set
	 */
	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	/**
	 * @return the transitFlag
	 */
	public String getTransitFlag() {
		return transitFlag;
	}

	/**
	 * @param transitFlag the transitFlag to set
	 */
	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}
	
	public String getActWgtSta() {
		return actWgtSta;
	}

	public void setActWgtSta(String actWgtSta) {
		this.actWgtSta = actWgtSta;
	}
	
	
}
