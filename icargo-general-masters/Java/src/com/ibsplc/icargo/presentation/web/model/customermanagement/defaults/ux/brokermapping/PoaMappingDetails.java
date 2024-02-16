package com.ibsplc.icargo.presentation.web.model.customermanagement.defaults.ux.brokermapping;

public class PoaMappingDetails{
	
	private String deletionRemarks;
	
	private String operatonalFlag;
	
	private String poaType;
	
	private String agentCode;
	
	private String agentName;
	
	private String[] sccCodeInclude;
	
	private String[] sccCodeExclude;
	
	private String[] orginExclude;
	
	private String[] orginInclude;
	
	private String[] destination;
	
	private String station;
	
	private String customerCode;
	
	private int sequenceNumber;
	
	private int index;
	
	private String awbNumber;
	
	private boolean poaFlag;
	
	private String creationDate;
	
	private String vldStartDate;
	
	private String vldEndDate;
	
	public String getVldStartDate() {
		return vldStartDate;
	}

	public void setVldStartDate(String vldStartDate) {
		this.vldStartDate = vldStartDate;
	}

	public String getVldEndDate() {
		return vldEndDate;
	}

	public void setVldEndDate(String vldEndDate) {
		this.vldEndDate = vldEndDate;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getDeletionRemarks() {
		return deletionRemarks;
	}

	public void setDeletionRemarks(String deletionRemarks) {
		this.deletionRemarks = deletionRemarks;
	}

	public String getOperatonalFlag() {
		return operatonalFlag;
	}

	public void setOperatonalFlag(String operatonalFlag) {
		this.operatonalFlag = operatonalFlag;
	}

	public String getPoaType() {
		return poaType;
	}

	public void setPoaType(String poaType) {
		this.poaType = poaType;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

	public String[] getSccCodeInclude() {
		return sccCodeInclude.clone();
	}

	public void setSccCodeInclude(String[] sccCodeInclude) {
		this.sccCodeInclude = sccCodeInclude.clone();
	}

	public String[] getSccCodeExclude() {
		return sccCodeExclude.clone();
	}

	public void setSccCodeExclude(String[] sccCodeExclude) {
		this.sccCodeExclude = sccCodeExclude.clone();
	}

	public String[] getOrginExclude() {
		return orginExclude.clone();
	}

	public void setOrginExclude(String[] orginExclude) {
		this.orginExclude = orginExclude.clone();
	}

	public String[] getOrginInclude() {
		return orginInclude.clone();
	}

	public void setOrginInclude(String[] orginInclude) {
		this.orginInclude = orginInclude.clone();
	}

	public String[] getDestination() {
		return destination.clone();
	}

	public void setDestination(String[] destination) {
		this.destination = destination.clone();
	}

	public String getStation() {
		return station;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String getAwbNumber() {
		return awbNumber;
	}

	public void setAwbNumber(String awbNumber) {
		this.awbNumber = awbNumber;
	}

	public int getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(int sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public boolean isPoaFlag() {
		return poaFlag;
	}

	public void setPoaFlag(boolean poaFlag) {
		this.poaFlag = poaFlag;
	}
	
}
