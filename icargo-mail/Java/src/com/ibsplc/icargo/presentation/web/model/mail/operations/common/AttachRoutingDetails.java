package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.ArrayList;

import com.ibsplc.icargo.presentation.web.model.mail.operations.common.OnwardRouting;


public class AttachRoutingDetails {
	
	private String consignemntNumber;
	
	private String paCode;
	
	private ArrayList<OnwardRouting> onwardRouting;
	
	private String consignmentDate;
	
	private String consignmentType;
	
	private String subType;
	
	private String remarks;

	public String getConsignemntNumber() {
		return consignemntNumber;
	}

	public void setConsignemntNumber(String consignemntNumber) {
		this.consignemntNumber = consignemntNumber;
	}

	public String getPaCode() {
		return paCode;
	}

	public void setPaCode(String paCode) {
		this.paCode = paCode;
	}

	public ArrayList<OnwardRouting> getOnwardRouting() {
		return onwardRouting;
	}

	public void setOnwardRouting(ArrayList<OnwardRouting> onwardRouting) {
		this.onwardRouting = onwardRouting;
	}

	public String getConsignmentDate() {
		return consignmentDate;
	}

	public void setConsignmentDate(String consignmentDate) {
		this.consignmentDate = consignmentDate;
	}

	public String getConsignmentType() {
		return consignmentType;
	}

	public void setConsignmentType(String consignmentType) {
		this.consignmentType = consignmentType;
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	
	
	

}
