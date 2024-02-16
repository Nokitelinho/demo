package com.ibsplc.icargo.presentation.web.model.xaddons.lh.mail.operations;

import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.web.spring.model.ScreenModel;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.presentation.web.model.xaddons.lh.mail.operations.HbaMarkingModel.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	203168	:	14-09-2022		:	Draft
 */
public class HbaMarkingModel implements ScreenModel  {
	


	public long getUldReferenceNumber() {
		return uldReferenceNumber;
	}

	public void setUldReferenceNumber(long uldReferenceNumber) {
		this.uldReferenceNumber = uldReferenceNumber;
	}

	private static final String PRODUCT = "lhmail";
	private static final String SUBPRODUCT = "operations";
	private static final String SCREENID = "xaddons.mail.operations.hbamarking";
	


	private String hbaType;
	private String position;
	private long uldReferenceNumber ;
	private Map<String, Collection<OneTimeVO>> oneTimeValues;
	private String operationFlag;
	private String flightNumber;
	private long flightSequenceNumber;
	private int legSerialNumber;
	private int carrierId;
	private String carrierCode;
	private String flightDate;
	private String assignedPort;
	private String containerNumber;
	private String type;
	
	public String getFlightNumber() {
		return flightNumber;
	}

	public String getAssignedPort() {
		return assignedPort;
	}

	public void setAssignedPort(String assignedPort) {
		this.assignedPort = assignedPort;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	public long getFlightSequenceNumber() {
		return flightSequenceNumber;
	}

	public void setFlightSequenceNumber(long flightSequenceNumber) {
		this.flightSequenceNumber = flightSequenceNumber;
	}

	public int getLegSerialNumber() {
		return legSerialNumber;
	}

	public void setLegSerialNumber(int legSerialNumber) {
		this.legSerialNumber = legSerialNumber;
	}

	public int getCarrierId() {
		return carrierId;
	}

	public void setCarrierId(int carrierId) {
		this.carrierId = carrierId;
	}

	public String getOperationFlag() {
		return operationFlag;
	}

	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}

	@Override
	public String getProduct() {
		return PRODUCT;
	}

	@Override
	public String getScreenId() {
		return SCREENID;
	}
	
	@Override
	public String getSubProduct() {
		return SUBPRODUCT;
	}
	
	

	public Map<String, Collection<OneTimeVO>> getOneTimeValues() {
		return oneTimeValues;
	}

	public void setOneTimeValues(Map<String, Collection<OneTimeVO>> oneTimeValues) {
		this.oneTimeValues = oneTimeValues;
	}

	public String getHbaType() {
		return hbaType;
	}

	public void setHbaType(String hbaType) {
		this.hbaType = hbaType;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getCarrierCode() {
		return carrierCode;
	}

	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	


}
