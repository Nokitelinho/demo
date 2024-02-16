package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;

public class ChangeContainerDetails {

	private String flightNumber;
	
	private String flightCarrierCode;
	
	private String date;
	
	private String time;
	
	private String remarks;
	
	private ContainerDetailsVO containerDetailsVO;
	
	private MailArrivalVO mailArrivalVO;
	
	private ContainerDetails containerDetail;
	
	private MailinboundDetails flightDetail;

	public String getFlightNumber() {
		return flightNumber;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public String getFlightCarrierCode() {
		return flightCarrierCode;
	}

	public void setFlightCarrierCode(String flightCarrierCode) {
		this.flightCarrierCode = flightCarrierCode;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public ContainerDetailsVO getContainerDetailsVO() {
		return containerDetailsVO;
	}

	public void setContainerDetailsVO(ContainerDetailsVO containerDetailsVO) {
		this.containerDetailsVO = containerDetailsVO;
	}

	public MailArrivalVO getMailArrivalVO() {
		return mailArrivalVO;
	}

	public void setMailArrivalVO(MailArrivalVO mailArrivalVO) {
		this.mailArrivalVO = mailArrivalVO;
	}

	public ContainerDetails getContainerDetail() {
		return containerDetail;
	}

	public void setContainerDetail(ContainerDetails containerDetail) {
		this.containerDetail = containerDetail;
	}

	public MailinboundDetails getFlightDetail() {
		return flightDetail;
	}

	public void setFlightDetail(MailinboundDetails flightDetail) {
		this.flightDetail = flightDetail;
	}
	
	
	
	
}
