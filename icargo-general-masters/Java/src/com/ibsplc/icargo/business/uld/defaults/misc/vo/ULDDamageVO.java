/*
 * ULDDamageVO.java Created on Dec 21, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.misc.vo;



import java.util.Collection;

import com.ibsplc.icargo.business.operations.shipment.cto.vo.ShipmentIrregularityDetailsVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
/**
 * @author A-1347
 *
 */
public class ULDDamageVO extends AbstractVO{
    
	/**
	 *  
	 */
	public static final String MODULE ="uld";
	/**
	 * 
	 */
	public static final String SUBMODULE ="defaults";
	/**
	 * 
	 */
	public static final String ENTITY ="uld.defaults.misc.ULDDamage";
	
	private String companyCode;
    private String damageCode;
    private String position;
    private String severity;    
    private String reportedStation;
    private String repairedBy;
    private LocalDate repairDate;
    private String remarks;
    private long damageReferenceNumber;
    private long sequenceNumber;
    private String operationFlag;
    private String lastUpdateUser;
    private boolean isClosed;
    private LocalDate reportedDate;
    private boolean isPicturePresent;
    private ULDDamagePictureVO pictureVO;
    private LocalDate lastUpdateTime;
    private String imageCount;
    private String imageUpdated;
    
//  Added by Tarun for CRQ AirNZ418
	private String facilityType;
	private String location;
	private String partyType;
	private String party;
	private String abrPartyType;
	private String uldStatus;
	
	private String section;
	private String damageDescription;
	private String damagePoints;
	private Collection<ULDDamageChecklistVO> damageChecklistVOs;
	private String overStatus;
	// Added by A-8368 as part of user story - IASCB-35533
	private String damageNoticePoint;
	// Added by A-9025 as part of the story - IASCB-59572
	private Collection<ShipmentIrregularityDetailsVO> shipmentIrregularityDetailsVOs;

	
	
	
	
	
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
    /**
	 * @return the overStatus
	 */
	public String getOverStatus() {
		return overStatus;
	}
	/**
	 * @param overStatus the overStatus to set
	 */
	public void setOverStatus(String overStatus) {
		this.overStatus = overStatus;
	}
	/**
	 * @return the damageChecklistVOs
	 */
	public Collection<ULDDamageChecklistVO> getDamageChecklistVOs() {
		return damageChecklistVOs;
	}
	/**
	 * @param damageChecklistVOs the damageChecklistVOs to set
	 */
	public void setDamageChecklistVOs(
			Collection<ULDDamageChecklistVO> damageChecklistVOs) {
		this.damageChecklistVOs = damageChecklistVOs;
	}
	/**
	 * @return the section
	 */
	public String getSection() {
		return section;
	}
	/**
	 * @param section the section to set
	 */
	public void setSection(String section) {
		this.section = section;
	}
    /**
	 * @return the uldStatus
	 */
	public String getUldStatus() {
		return uldStatus;
	}
	/**
	 * @param uldStatus the uldStatus to set
	 */
	public void setUldStatus(String uldStatus) {
		this.uldStatus = uldStatus;
	}
	/**
	 * @return the abrPartyType
	 */
	public String getAbrPartyType() {
		return abrPartyType;
	}
	/**
	 * @param abrPartyType the abrPartyType to set
	 */
	public void setAbrPartyType(String abrPartyType) {
		this.abrPartyType = abrPartyType;
	}
	/**
	 * @return the facilityType
	 */
	public String getFacilityType() {
		return facilityType;
	}
	/**
	 * @param facilityType the facilityType to set
	 */
	public void setFacilityType(String facilityType) {
		this.facilityType = facilityType;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}
	/**
	 * @return the partyType
	 */
	public String getPartyType() {
		return partyType;
	}
	/**
	 * @param partyType the partyType to set
	 */
	public void setPartyType(String partyType) {
		this.partyType = partyType;
	}
	
	/**
	 * @return the party
	 */
	public String getParty() {
		return party;
	}
	/**
	 * @param party the party to set
	 */
	public void setParty(String party) {
		this.party = party;
	}
	/**
	 * @return Returns the isPicturePresent.
	 */
	public boolean isPicturePresent() {
		return this.isPicturePresent;
	}
	/**
	 * @param isPicturePresent The isPicturePresent to set.
	 */
	public void setPicturePresent(boolean isPicturePresent) {
		this.isPicturePresent = isPicturePresent;
	}
	/**
     * @return Returns the isClosed.
     */
    public boolean isClosed() {
        return isClosed;
    }
    /**
     * @param isClosed The isClosed to set.
     */
    public void setClosed(boolean isClosed) {
        this.isClosed = isClosed;
    }
	/**
	 * @return Returns the operationFlag.
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag The operationFlag to set.
	 */
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the sequenceNumber.
	 */
	public long getSequenceNumber() {
		return sequenceNumber;
	}
	/**
	 * @param sequenceNumber The sequenceNumber to set.
	 */
	public void setSequenceNumber(long sequenceNumber) {
		this.sequenceNumber = sequenceNumber;
	}
	/**
	 * @return Returns the damageCode.
	 */
	public String getDamageCode() {
		return damageCode;
	}
	/**
	 * @param damageCode The damageCode to set.
	 */
	public void setDamageCode(String damageCode) {
		this.damageCode = damageCode;
	}
	/**
	 * @return Returns the position.
	 */
	public String getPosition() {
		return position;
	}
	/**
	 * @param position The position to set.
	 */
	public void setPosition(String position) {
		this.position = position;
	}
	/**
	 * @return Returns the remarks.
	 */
	public String getRemarks() {
		return remarks;
	}
	/**
	 * @param remarks The remarks to set.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * @return Returns the repairDate.
	 */
	public LocalDate getRepairDate() {
		return repairDate;
	}
	/**
	 * @param repairDate The repairDate to set.
	 */
	public void setRepairDate(LocalDate repairDate) {
		this.repairDate = repairDate;
	}
	/**
	 * @return Returns the repairedBy.
	 */
	public String getRepairedBy() {
		return repairedBy;
	}
	/**
	 * @param repairedBy The repairedBy to set.
	 */
	public void setRepairedBy(String repairedBy) {
		this.repairedBy = repairedBy;
	}
	/**
	 * @return Returns the reportedStation.
	 */
	public String getReportedStation() {
		return reportedStation;
	}
	/**
	 * @param reportedStation The reportedStation to set.
	 */
	public void setReportedStation(String reportedStation) {
		this.reportedStation = reportedStation;
	}
	/**
	 * @return Returns the severity.
	 */
	public String getSeverity() {
		return severity;
	}
	/**
	 * @param severity The severity to set.
	 */
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	/**
	 * @return Returns the damageReferenceNumber.
	 */
	public long getDamageReferenceNumber() {
		return damageReferenceNumber;
	}
	/**
	 * @param damageReferenceNumber The damageReferenceNumber to set.
	 */
	public void setDamageReferenceNumber(long damageReferenceNumber) {
		this.damageReferenceNumber = damageReferenceNumber;
	}
	
	/**
	 * @return Returns the lastUpdateUser.
	 */
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	/**
	 * @param lastUpdateUser The lastUpdateUser to set.
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	//@Column(name = "")
	/**
	 * @return Returns the reportedDate.
	 */
	public LocalDate getReportedDate() {
		return reportedDate;
	}
	/**
	 * @param reportedDate The reportedDate to set.
	 */
	public void setReportedDate(LocalDate reportedDate) {
		this.reportedDate = reportedDate;
	}
	/**
	 * @return Returns the pictureVO.
	 */
	public ULDDamagePictureVO getPictureVO() {
		return this.pictureVO;
	}
	/**
	 * @param pictureVO The pictureVO to set.
	 */
	public void setPictureVO(ULDDamagePictureVO pictureVO) {
		this.pictureVO = pictureVO;
	}
	/**
	 * @return Returns the lastUpdateTime.
	 */
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateTime The lastUpdateTime to set.
	 */
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return the damageDescription
	 */
	public String getDamageDescription() {
		return damageDescription;
	}
	/**
	 * @param damageDescription the damageDescription to set
	 */
	public void setDamageDescription(String damageDescription) {
		this.damageDescription = damageDescription;
	}
	/**
	 * @return the damagePoints
	 */
	public String getDamagePoints() {
		return damagePoints;
	}
	/**
	 * @param damagePoints the damagePoints to set
	 */
	public void setDamagePoints(String damagePoints) {
		this.damagePoints = damagePoints;
	}

	/**
	 * Added as a pat of QF Mobility
	 */
	private Collection<ULDDamagePictureVO> pictureVOs;


	/**
	 * @return the pictureVOs
	 */
	public Collection<ULDDamagePictureVO> getPictureVOs() {
		return pictureVOs;
	}
	/**
	 * @param pictureVOs the pictureVOs to set
	 */
	public void setPictureVOs(Collection<ULDDamagePictureVO> pictureVOs) {
		this.pictureVOs = pictureVOs;
	}
	
	public String getImageCount() {
		return imageCount;
	}
	public void setImageCount(String imageCount) {
		this.imageCount = imageCount;
	}
	public String getImageUpdated() {
		return imageUpdated;
	}
	public void setImageUpdated(String imageUpdated) {
		this.imageUpdated = imageUpdated;
	}
	public String getDamageNoticePoint() {
		return damageNoticePoint;
	}
	public void setDamageNoticePoint(String damageNoticePoint) {
		this.damageNoticePoint = damageNoticePoint;
	}
	public Collection<ShipmentIrregularityDetailsVO> getShipmentIrregularityDetailsVOs() {
		return shipmentIrregularityDetailsVOs;
	}
	public void setShipmentIrregularityDetailsVOs(Collection<ShipmentIrregularityDetailsVO> shipmentIrregularityDetailsVOs) {
		this.shipmentIrregularityDetailsVOs = shipmentIrregularityDetailsVOs;
	}
}
