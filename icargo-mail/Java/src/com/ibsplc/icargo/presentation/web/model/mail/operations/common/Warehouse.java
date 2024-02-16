package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import com.ibsplc.icargo.framework.util.time.LocalDate;

public class Warehouse {

	  private String companyCode;
	  private String airportCode;
	  private String warehouseCode;
	  private String description;
	  private double exactMatchWeightage;
	  private double alsoMatchWeightage;
	  private double preferredMatchWeightage;
	  private String zoneSelectionMode;
	  private String structureSelectionMode;
	  private String operationFlag;
	  
	  private LocalDate lastUpdateTime;
	  private String lastUpdateUser;
	  private String customsEstablishmentCode;
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getAirportCode() {
		return airportCode;
	}
	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}
	public String getWarehouseCode() {
		return warehouseCode;
	}
	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getExactMatchWeightage() {
		return exactMatchWeightage;
	}
	public void setExactMatchWeightage(double exactMatchWeightage) {
		this.exactMatchWeightage = exactMatchWeightage;
	}
	public double getAlsoMatchWeightage() {
		return alsoMatchWeightage;
	}
	public void setAlsoMatchWeightage(double alsoMatchWeightage) {
		this.alsoMatchWeightage = alsoMatchWeightage;
	}
	public double getPreferredMatchWeightage() {
		return preferredMatchWeightage;
	}
	public void setPreferredMatchWeightage(double preferredMatchWeightage) {
		this.preferredMatchWeightage = preferredMatchWeightage;
	}
	public String getZoneSelectionMode() {
		return zoneSelectionMode;
	}
	public void setZoneSelectionMode(String zoneSelectionMode) {
		this.zoneSelectionMode = zoneSelectionMode;
	}
	public String getStructureSelectionMode() {
		return structureSelectionMode;
	}
	public void setStructureSelectionMode(String structureSelectionMode) {
		this.structureSelectionMode = structureSelectionMode;
	}
	public String getOperationFlag() {
		return operationFlag;
	}
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	public LocalDate getLastUpdateTime() {
		return lastUpdateTime;
	}
	public void setLastUpdateTime(LocalDate lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	public String getCustomsEstablishmentCode() {
		return customsEstablishmentCode;
	}
	public void setCustomsEstablishmentCode(String customsEstablishmentCode) {
		this.customsEstablishmentCode = customsEstablishmentCode;
	}
	  
	  
}
