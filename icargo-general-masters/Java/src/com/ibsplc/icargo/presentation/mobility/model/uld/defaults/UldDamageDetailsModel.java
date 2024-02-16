package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class UldDamageDetailsModel implements Serializable {

	private String damageReferenceNumber;
	private String section;
	private String damageDescription;
	private String damagePoints;
	private String operationFlag;
	private String severity;
	private String uldStatus;
	private String facilityType;
	private String partyType;
	private String location;
	private String partyCode;
	private Integer sequenceNumber;
	private List<UldDamagePictureModel> uldDamagePictures;

	public String getDamageReferenceNumber() {
		return damageReferenceNumber;
	}

	public void setDamageReferenceNumber(String damageReferenceNumber) {
		this.damageReferenceNumber = damageReferenceNumber;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public String getDamageDescription() {
		return damageDescription;
	}

	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}

	public String getDamagePoints() {
		return damagePoints;
	}

	public void setDamagePoints(String damagePoints) {
		this.damagePoints = damagePoints;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getUldStatus() {
		return uldStatus;
	}

	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}

	public String getFacilityType() {
		return facilityType;
	}

	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}

	public String getPartyType() {
		return partyType;
	}

	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPartyCode() {
		return partyCode;
	}

	public void setPartyCode(String partyCode) {
		this.partyCode = partyCode;
	}

	public Integer getSequenceNumber() {
		return sequenceNumber;
	}

	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}

	public List<UldDamagePictureModel> getUldDamagePictures() {
		return uldDamagePictures;
	}

	public void setUldDamagePictures(List<UldDamagePictureModel> uldDamagePictures) {
		this.uldDamagePictures = uldDamagePictures;
	}
}
