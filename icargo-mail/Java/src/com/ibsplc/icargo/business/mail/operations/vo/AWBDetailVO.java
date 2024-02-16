/*
 * AWBDetailVO.java Created on Jun 30, 2016
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.vo;

import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author a-5991
 *
 */
public class AWBDetailVO extends AbstractVO {
    private String companyCode;
    private int ownerId;
    private String masterDocumentNumber;
    private int duplicateNumber;
    private int sequenceNumber;
    private int statedPieces;
    //private double statedWeight;
    //private double statedVolume;
    private Measure statedWeight;//added by A-7371
    private Measure statedVolume;//added by A-7371
    private String shipmentDescription;
    private String origin;
    private String destination;
    private String operationFlag;
    private String shipmentPrefix;
    private String statedWeightCode;
    private String agentCode;
    private String documentSubType;
  //added by A-7371
    /**
     * 
     * @return statedWeight
     */
    public Measure getStatedWeight() {
		return statedWeight;
	}
    /**
     * 
     * @param statedWeight
     */
	public void setStatedWeight(Measure statedWeight) {
		this.statedWeight = statedWeight;
	}
	/**
	 * 
	 * @return statedVolume
	 */
	public Measure getStatedVolume() {
		return statedVolume;
	}
	/**
	 * 
	 * @param statedVolume
	 */
	public void setStatedVolume(Measure statedVolume) {
		this.statedVolume = statedVolume;
	}
    private String ownerCode;
    
    /**
	 * @return Returns the masterDocumentNumber.
	 */
	public String getMasterDocumentNumber() {
		return masterDocumentNumber;
	}
	/**
	 * @param masterDocumentNumber The masterDocumentNumber to set.
	 */
	public void setMasterDocumentNumber(String masterDocumentNumber) {
		this.masterDocumentNumber = masterDocumentNumber;
	}
	/**
	 * @return Returns the shipmentPrefix.
	 */
	public String getShipmentPrefix() {
		return shipmentPrefix;
	}
	/**
	 * @param shipmentPrefix The shipmentPrefix to set.
	 */
	public void setShipmentPrefix(String shipmentPrefix) {
		this.shipmentPrefix = shipmentPrefix;
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
     * @return Returns the destination.
     */
    public String getDestination() {
        return destination;
    }
    /**
     * @param destination The destination to set.
     */
    public void setDestination(String destination) {
        this.destination = destination;
    }
   
    /**
     * @return Returns the duplicateNumber.
     */
    public int getDuplicateNumber() {
        return duplicateNumber;
    }
    /**
     * @param duplicateNumber The duplicateNumber to set.
     */
    public void setDuplicateNumber(int duplicateNumber) {
        this.duplicateNumber = duplicateNumber;
    }
    /**
     * @return Returns the origin.
     */
    public String getOrigin() {
        return origin;
    }
    /**
     * @param origin The origin to set.
     */
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    /**
     * @return Returns the ownerId.
     */
    public int getOwnerId() {
        return ownerId;
    }
    /**
     * @param ownerId The ownerId to set.
     */
    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
    /**
     * @return Returns the sequenceNumber.
     */
    public int getSequenceNumber() {
        return sequenceNumber;
    }
    /**
     * @param sequenceNumber The sequenceNumber to set.
     */
    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }
    /**
     * @return Returns the shipmentDescription.
     */
    public String getShipmentDescription() {
        return shipmentDescription;
    }
    /**
     * @param shipmentDescription The shipmentDescription to set.
     */
    public void setShipmentDescription(String shipmentDescription) {
        this.shipmentDescription = shipmentDescription;
    }
    /**
     * @return Returns the statedPieces.
     */
    public int getStatedPieces() {
        return statedPieces;
    }
    /**
     * @param statedPieces The statedPieces to set.
     */
    public void setStatedPieces(int statedPieces) {
        this.statedPieces = statedPieces;
    }
    /**
     * @return Returns the statedWeight.
     */
   /* public double getStatedWeight() {
        return statedWeight;
    }
    *//**
     * @param statedWeight The statedWeight to set.
     *//*
    public void setStatedWeight(double statedWeight) {
        this.statedWeight = statedWeight;
    }*/
	/**
	 * @return Returns the statedWeightCode.
	 */
	public  String getStatedWeightCode() {
		return statedWeightCode;
	}
	/**
	 * @param statedWeightCode The statedWeightCode to set.
	 */
	public  void setStatedWeightCode(String statedWeightCode) {
		this.statedWeightCode = statedWeightCode;
	}
	/**
	 * @param ownerCode The ownerCode to set.
	 */
	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}
	/**
	 * @return Returns the ownerCode.
	 */
	public String getOwnerCode() {
		return ownerCode;
	}
	/**
	 * @return the statedVolume
	 */
	/*public double getStatedVolume() {
		return statedVolume;
	}
	*//**
	 * @param statedVolume the statedVolume to set
	 *//*
	public void setStatedVolume(double statedVolume) {
		this.statedVolume = statedVolume;
	}*/
    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
	public String getDocumentSubType() {
		return documentSubType;
	}
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
    }
}
