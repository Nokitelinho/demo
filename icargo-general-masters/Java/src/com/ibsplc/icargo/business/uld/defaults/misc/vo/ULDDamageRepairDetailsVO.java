/*
 * ULDDamageRepairDetailsVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;


import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;

import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDamageRepairDetailsVO extends AbstractVO 
                    implements Serializable{
    
    private String companyCode;
    private String uldNumber;
    
    private String damageStatus;
    private String overallStatus;
    private ImageModel image;
   
    private String repairStatus;
    private String supervisor;
    private String investigationReport;
    private String damgePicture;
    
	private LocalDate lastUpdatedTime;
	private String lastUpdatedUser;
	 private String currentStation; 
	 private long damageIndex;
	 private int imageSequenceNumber;

	 
	 public int getImageSequenceNumber() {
		return imageSequenceNumber;
	}

	public void setImageSequenceNumber(int imageSequenceNumber) {
		this.imageSequenceNumber = imageSequenceNumber;
	}
	private ULDDamagePictureVO uldDamagePictureVO;
	 /*This was added as part of ICRD-223777 for retaining the added picture and the damage index in the ULD052 screen damage popup*/
	 private HashMap<Integer,ULDDamagePictureVO> damagePicMap;
	
	 /*This was added as part of ICRD-223777 for showing damage points in the ULD052 screen damage popup*/
	 private String damagePoints;
	/**
	 * Collection<ULDDamageVO>
	 */
	private Collection<ULDDamageVO> uldDamageVOs;
	
	/**
	 * Collection<ULDRepairVO>
	 */
	private Collection<ULDRepairVO> uldRepairVOs;
	/**
	 * ULDDamageRepairDetailsVO
	 */
	private ULDDamageRepairDetailsVO ulddamageRepairDetailsVO;

	/**
	 * @return Returns the ulddamageRepairDetailsVO.
	 */
	public ULDDamageRepairDetailsVO getUlddamageRepairDetailsVO() {
		return ulddamageRepairDetailsVO;
	}

	/**
	 * @param ulddamageRepairDetailsVO The ulddamageRepairDetailsVO to set.
	 */
	public void setUlddamageRepairDetailsVO(
			ULDDamageRepairDetailsVO ulddamageRepairDetailsVO) {
		this.ulddamageRepairDetailsVO = ulddamageRepairDetailsVO;
	}


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

	/**
	 * @return Returns the damageStatus.
	 */
	public String getDamageStatus() {
		return damageStatus;
	}

	/**
	 * @param damageStatus The damageStatus to set.
	 */
	public void setDamageStatus(String damageStatus) {
		this.damageStatus = damageStatus;
	}

	/**
	 * @return Returns the investigationReport.
	 */
	public String getInvestigationReport() {
		return investigationReport;
	}

	/**
	 * @param investigationReport The investigationReport to set.
	 */
	public void setInvestigationReport(String investigationReport) {
		this.investigationReport = investigationReport;
	}

	/**
	 * @return Returns the lastUpdatedTime.
	 */
	public LocalDate getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(LocalDate lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

	/**
	 * @return Returns the lastUpdatedUser.
	 */
	public String getLastUpdatedUser() {
		return lastUpdatedUser;
	}

	/**
	 * @param lastUpdatedUser The lastUpdatedUser to set.
	 */
	public void setLastUpdatedUser(String lastUpdatedUser) {
		this.lastUpdatedUser = lastUpdatedUser;
	}

	/**
	 * @return Returns the overallStatus.
	 */
	public String getOverallStatus() {
		return overallStatus;
	}

	/**
	 * @param overallStatus The overallStatus to set.
	 */
	public void setOverallStatus(String overallStatus) {
		this.overallStatus = overallStatus;
	}

	/**
	 * @return Returns the repairStatus.
	 */
	public String getRepairStatus() {
		return repairStatus;
	}

	/**
	 * @param repairStatus The repairStatus to set.
	 */
	public void setRepairStatus(String repairStatus) {
		this.repairStatus = repairStatus;
	}

	/**
	 * @return Returns the supervisor.
	 */
	public String getSupervisor() {
		return supervisor;
	}

	/**
	 * @param supervisor The supervisor to set.
	 */
	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	/**
	 * @return Returns the uldDamageVOs.
	 */
	public Collection<ULDDamageVO> getUldDamageVOs() {
		return uldDamageVOs;
	}

	/**
	 * @param uldDamageVOs The uldDamageVOs to set.
	 */
	public void setUldDamageVOs(Collection<ULDDamageVO> uldDamageVOs) {
		this.uldDamageVOs = uldDamageVOs;
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
	 * @return Returns the uldRepairVOs.
	 */
	public Collection<ULDRepairVO> getUldRepairVOs() {
		return uldRepairVOs;
	}

	/**
	 * @param uldRepairVOs The uldRepairVOs to set.
	 */
	public void setUldRepairVOs(Collection<ULDRepairVO> uldRepairVOs) {
		this.uldRepairVOs = uldRepairVOs;
	}


	/**
	 * @return Returns the damgePicture.
	 */
	public String getDamgePicture() {
		return damgePicture;
	}

	/**
	 * @param damgePicture The damgePicture to set.
	 */
	public void setDamgePicture(String damgePicture) {
		this.damgePicture = damgePicture;
	}

	//@Column(name = "")
	/**
	 * @return Returns the currentStation.
	 */
	public String getCurrentStation() {
		return currentStation;
	}

	/**
	 * @param currentStation The currentStation to set.
	 */
	public void setCurrentStation(String currentStation) {
		this.currentStation = currentStation;
	}

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
	 * @return Returns the uldDamagePictureVO.
	 */
	public ULDDamagePictureVO getUldDamagePictureVO() {
		return uldDamagePictureVO;
	}

	/**
	 * @param uldDamagePictureVO The uldDamagePictureVO to set.
	 */
	public void setUldDamagePictureVO(ULDDamagePictureVO uldDamagePictureVO) {
		this.uldDamagePictureVO = uldDamagePictureVO;
	}
	public long getDamageIndex() {
		return damageIndex;
	}
	public void setDamageIndex(long damageIndex) {
		this.damageIndex = damageIndex;
	}
	public HashMap<Integer, ULDDamagePictureVO> getDamagePicMap() {
		return damagePicMap;
	}
	public void setDamagePicMap(HashMap<Integer, ULDDamagePictureVO> damagePicMap) {
		this.damagePicMap = damagePicMap;
	}
	public String getDamagePoints() {
		return damagePoints;
	}
	public void setDamagePoints(String damagePoints) {
		this.damagePoints = damagePoints;
	}


}
