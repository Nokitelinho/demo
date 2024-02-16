package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UldDamageChecklistModel implements Serializable {
	private Integer noOfPoints;
	private String description;
	private String section;
	private String sequenceNumber;

	public Integer getNoOfPoints() {
		return noOfPoints;
	}

	public void setNoOfPoints(Integer noOfPoints) {
		this.noOfPoints = noOfPoints;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(String sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
