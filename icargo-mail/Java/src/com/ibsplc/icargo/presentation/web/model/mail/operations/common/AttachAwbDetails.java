package com.ibsplc.icargo.presentation.web.model.mail.operations.common;

import java.util.HashMap;

import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.MeasureAnnotation;

/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.AttachAwbDetails.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8164	:	30-Oct-2018		:	Draft
 */
public class AttachAwbDetails {

	private String companyCode;
	
	private String shipmentDescription;

	private String density;
	
	private String origin;
	
	private String destination;
	
	private int standardPieces;
	
	private Double standardWeight;
	private Measure stdWeightMeasure;
	
	private String agentCode;
	
	private String shipmentPrefix;
	
	private String masterDocumentNumber;
	
	private AWBDetailVO awbDetailVO;
	
	private HashMap<String,String> systemParameters;
	
	private String weightStandard;
	private String operationFlag;
	private String ownerCode;
	private String statedWeightCode;
	private int ownerId;
	private Measure statedWeight;
	private int statedPieces;
	public String getShipmentDescription() {
		return shipmentDescription;
	}

	public void setShipmentDescription(String shipmentDescription) {
		this.shipmentDescription = shipmentDescription;
	}

	public String getDensity() {
		return density;
	}

	public void setDensity(String density) {
		this.density = density;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getStandardPieces() {
		return standardPieces;
	}

	public void setStandardPieces(int standardPieces) {
		this.standardPieces = standardPieces;
	}

	public Double getStandardWeight() {
		return standardWeight;
	}

	public void setStandardWeight(Double standardWeight) {
		this.standardWeight = standardWeight;
	}

	public String getAgentCode() {
		return agentCode;
	}

	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}

	public String getShipmentPrefix() {
		return shipmentPrefix;
	}

	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
	}

	

	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}

	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}

	public AWBDetailVO getAwbDetailVO() {
		return awbDetailVO;
	}

	public void setAwbDetailVO(AWBDetailVO awbDetailVO) {
		this.awbDetailVO = awbDetailVO;
	}

	public Measure getStdWeightMeasure() {
		return stdWeightMeasure;
	}

	public void setStdWeightMeasure(Measure stdWeightMeasure) {
		this.stdWeightMeasure = stdWeightMeasure;
	}

	public HashMap<String, String> getSystemParameters() {
		return systemParameters;
	}

	public void setSystemParameters(HashMap<String, String> systemParameters) {
		this.systemParameters = systemParameters;
	}

	public String getWeightStandard() {
		return weightStandard;
	}

	public void setWeightStandard(String weightStandard) {
		this.weightStandard = weightStandard;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	public String getOwnerCode() {
		return ownerCode;
	}

	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}

	public String getStatedWeightCode() {
		return statedWeightCode;
	}

	public void setStatedWeightCode(String statedWeightCode) {
		this.statedWeightCode = statedWeightCode;
	}

	public int getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(int ownerId) {
		this.ownerId = ownerId;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public Measure getStatedWeight() {
		return statedWeight;
	}
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	public int getStatedPieces() {
		return statedPieces;
	}
	public void setStatedPieces(int statedPieces) {
		this.statedPieces = statedPieces;
	}
	
	
	
	

}
