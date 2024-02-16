package com.ibsplc.icargo.presentation.mobility.model.uld.defaults;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UldDamagePictureModel implements Serializable {
	private String uldDamagePicture;
	private byte[] image;
	private String operationFlag;
	private Integer sequenceNumber;
	public String getUldDamagePicture() {
		return uldDamagePicture;
	}
	public void setUldDamagePicture(String uldDamagePicture) {
		this.uldDamagePicture = uldDamagePicture;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public Integer getSequenceNumber() {
		return sequenceNumber;
	}
	public void setSequenceNumber(Integer sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
}
