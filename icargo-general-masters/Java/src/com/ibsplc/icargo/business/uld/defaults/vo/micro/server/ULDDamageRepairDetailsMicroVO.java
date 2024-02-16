/*
 * ULDMovementVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.vo.micro.server;

import com.ibsplc.xibase.server.framework.vo.AbstractVO;




/**
 * @author A-1347
 *
 */
public class ULDDamageRepairDetailsMicroVO  extends AbstractVO{

    private String companyCode;
    private String uldNumber;

    private String damageStatus;
    private String overallStatus;


    private String supervisor;
    private String investigationReport;
    private String damgePicture;

	private String lastUpdatedTime;
	private String lastUpdatedUser;
	private String currentStation;


	/**
	 * Collection<ULDDamageVO>
	 */
	private ULDDamageMicroVO[] uldDamageMicroVOs;


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
	public String getLastUpdatedTime() {
		return lastUpdatedTime;
	}


	/**
	 * @param lastUpdatedTime The lastUpdatedTime to set.
	 */
	public void setLastUpdatedTime(String lastUpdatedTime) {
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
	 * @return Returns the uLDDamageMicroVOs.
	 */
	public ULDDamageMicroVO[] getUldDamageMicroVOs() {
		return uldDamageMicroVOs;
	}


	/**
	 * @param damageMicroVOs The ULDDamageMicroVOs to set.
	 */
	public void setUldDamageMicroVOs(ULDDamageMicroVO[] uldDamageMicroVOs) {
		this.uldDamageMicroVOs = uldDamageMicroVOs;
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



}
