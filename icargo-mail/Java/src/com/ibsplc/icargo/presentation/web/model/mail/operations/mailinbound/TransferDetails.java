package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import java.util.ArrayList;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;

public class TransferDetails {
	
	private String container;
	
	private String mailbagId;
	
	private String reassignedto;
	
	private String flightCarrierCode;
	
	private String flightNumber;
	
	private String flightPou;	
	
	private String remarks;
	
	private String flightDate;
	
	private String carrier;
	
	private String destination;
	
	private String scanDate;
	
	private String scanTime;
	
	private String fromFlightCarrier;
	
	private String fromFlightNumber;
	
	private String fromFlightDate;
	
	private ContainerDetails containerDetails;
	
	private MailinboundDetails mailinboundDetails;
	
	private ArrayList<OnwardRouting> onwardRoutingDetailsCollection;
	
	private ContainerDetailsVO containerDetailsVO;

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getMailbagId() {
		return mailbagId;
	}

	public void setMailbagId(String mailbagId) {
		this.mailbagId = mailbagId;
	}

	public String getReassignedto() {
		return reassignedto;
	}

	public void setReassignedto(String reassignedto) {
		this.reassignedto = reassignedto;
	}

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getFlightPou() {
		return flightPou;
	}

	public void setFlightPou(String flightPou) {
		this.flightPou = flightPou;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ArrayList<OnwardRouting> getOnwardRoutingDetailsCollection() {
		return onwardRoutingDetailsCollection;
	}

	public void setOnwardRoutingDetailsCollection(ArrayList<OnwardRouting> onwardRoutingDetailsCollection) {
		this.onwardRoutingDetailsCollection = onwardRoutingDetailsCollection;
	}

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getScanDate() {
		return scanDate;
	}

	public void setScanDate(String scanDate) {
		this.scanDate = scanDate;
	}

	public String getScanTime() {
		return scanTime;
	}

	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}

	public ContainerDetailsVO getContainerDetailsVO() {
		return containerDetailsVO;
	}

	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
		this.containerDetailsVO = containerDetailsVO;
	}

	public String getFromFlightCarrier() {
		return fromFlightCarrier;
	}

	public void setFromFlightCarrier(String fromFlightCarrier) {
		this.fromFlightCarrier = fromFlightCarrier;
	}

	public String getFromFlightNumber() {
		return fromFlightNumber;
	}

	public void setFromFlightNumber(String fromFlightNumber) {
		this.fromFlightNumber = fromFlightNumber;
	}

	public String getFromFlightDate() {
		return fromFlightDate;
	}

	public void setFromFlightDate(String fromFlightDate) {
		this.fromFlightDate = fromFlightDate;
	}

	public ContainerDetails getContainerDetails() {
		return containerDetails;
	}

	public void setContainerDetails(ContainerDetails containerDetails) {
		this.containerDetails = containerDetails;
	}

	public MailinboundDetails getMailinboundDetails() {
		return mailinboundDetails;
	}

	public void setMailinboundDetails(MailinboundDetails mailinboundDetails) {
		this.mailinboundDetails = mailinboundDetails;
	}
	
	
	
	

}
