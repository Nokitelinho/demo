/*
 * ULDDamagePictureVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.io.Serializable;
import java.sql.Blob;

import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1950
 *
 */
public class ULDDamagePictureVO extends AbstractVO
                    implements Serializable{

    private String companyCode;
    private String uldNumber;
    private ImageModel image;
    private long sequenceNumber;
    private int imageSequenceNumber;
   // private String operationFlag;
    private boolean isPicturePresent;
    private String pictureType;
    
    private String operationFlag;
    private transient Blob uldPicture;
    private byte[] uldPictureByte;
    private String lastUpdateUser;
    private String toRemove;
    private String fileName;
    
    
	public String getToRemove() {
		return toRemove;
	}
	public void setToRemove(String toRemove) {
		this.toRemove = toRemove;
	}
	/**
	 * @return the uldPictureByte
	 */
	public byte[] getUldPictureByte() {
		return uldPictureByte;
	}
	/**
	 * @param uldPictureByte the uldPictureByte to set
	 */
	public void setUldPictureByte(byte[] uldPictureByte) {
		this.uldPictureByte = uldPictureByte;
	}
	/**
	 * @return the uldPicture
	 */
	public Blob getUldPicture() {
		return uldPicture;
	}
	/**
	 * @param uldPicture the uldPicture to set
	 */
	public void setUldPicture(Blob uldPicture) {
		this.uldPicture = uldPicture;
	}
	//@Column(name = "")
	/**
	 * @return Returns the pictureType.
	 */
	public String getPictureType() {
		return pictureType;
	}
	/**
	 * @param pictureType The pictureType to set.
	 */
	public void setPictureType(String pictureType) {
		this.pictureType = pictureType;
	}
	//@Column(name = "")
	/**
	 * @return Returns the companyCode.
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode The companyCode to set.
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	//@Column(name = "")
	/**
	 * @return Returns the image.
	 */
	public ImageModel getImage() {
		return image;
	}
	/**
	 * @param image The image to set.
	 */
	public void setImage(ImageModel image) {
		this.image = image;
	}
	//@Column(name = "")
	/**
	 * @return Returns the isPicturePresent.
	 */
	public boolean isPicturePresent() {
		return isPicturePresent;
	}
	/**
	 * @param isPicturePresent The isPicturePresent to set.
	 */
	public void setPicturePresent(boolean isPicturePresent) {
		this.isPicturePresent = isPicturePresent;
	}
	/**
	 * @return Returns the uldNumber.
	 */
	public String getUldNumber() {
		return uldNumber;
	}
	/**
	 * @param uldNumber The uldNumber to set.
	 */
	public void setUldNumber(String uldNumber) {
		this.uldNumber = uldNumber;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public long getSequenceNumber() {
		return this.sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return this.operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public int getImageSequenceNumber() {
		return imageSequenceNumber;
	}
	public void setImageSequenceNumber(int imageSequenceNumber) {
		this.imageSequenceNumber = imageSequenceNumber;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
