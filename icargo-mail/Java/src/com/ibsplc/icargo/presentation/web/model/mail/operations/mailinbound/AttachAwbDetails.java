package com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound;

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
	
	private String shipmentDescription;

	private String density;
	
	private String origin;
	
	private String destination;
	
	private String standardPieces;
	
	@MeasureAnnotation(mappedValue="stdWeightMeasure",unitType="MWT")
	private Double standardWeight;
	private Measure stdWeightMeasure;
	
	private String agentCode;
	
	private String shipmentPrefix;
	
	private String documentNumber;
	
	private AWBDetailVO awbDetailVO;
	
	private HashMap<String,String> systemParameters;
	
	private String weightStandard;
	
	private String statedPieces;
	private Measure statedWeightMeasure;
	private Double statedWeight;
	public Double getStatedWeight() {
		return statedWeight;
	}
	public void setStatedWeight(Double statedWeight) {
		this.statedWeight = statedWeight;
	}
	public Measure getStatedWeightMeasure() {
		return statedWeightMeasure;
	}
	public void setStatedWeightMeasure(Measure statedWeightMeasure) {
		this.statedWeightMeasure = statedWeightMeasure;
	}
	public String getStatedPieces() {
		return statedPieces;
	}
	public void setStatedPieces(String statedPieces) {
		this.statedPieces = statedPieces;
	}
	private String masterDocumentNumber;
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

	public String getStandardPieces() {
		return standardPieces;
	}

	public void setStandardPieces(String standardPieces) {
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

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
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
	/**
	 * @return the masterDocumentNumber
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 * @param masterDocumentNumber the masterDocumentNumber to set
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	
	
	
	

}
