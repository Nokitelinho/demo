package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class UldDamageModel implements Serializable {
	private String overallStatus;
	private String repairStatus;
	private String damageStatus;
	private String uldNumber;
	private List<UldDamageDetailsModel> uldDamageDetails;

	public String getOverallStatus() {
		return overallStatus;
	}

	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	public String getRepairStatus() {
		return repairStatus;
	}

	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	public String getDamageStatus() {
		return damageStatus;
	}

	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	public String getUldNumber() {
		return uldNumber;
	}

	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}

	public List<UldDamageDetailsModel> getUldDamageDetails() {
		return uldDamageDetails;
	}

	public void setUldDamageDetails(List<UldDamageDetailsModel> uldDamageDetails) {
		this.uldDamageDetails = uldDamageDetails;
	}
}
