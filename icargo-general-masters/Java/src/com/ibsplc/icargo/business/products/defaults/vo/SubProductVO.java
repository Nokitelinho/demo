 /*
 * SubProductVO.java Created on Jul 4, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import java.util.Collection;

import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;

/**
 * @author A-1358
 *
 */
public class SubProductVO extends AbstractVO implements Serializable {


	private static final long serialVersionUID = 7603298975502851419L;
	
	public static final String REASON_MANUALY_UPDATED="MANUALY UPDATED";
	/**
	 * Active status
	 */
	public static final String STATUS_ACTIVE="A";
	/**
	 * Inactive status
	 */
	public static final String STATUS_INACTIVE="I";
	/**
	 * New status
	 */
	public static final String STATUS_NEW="N";

	private String companyCode;

    private String productCode;

    private String subProductCode;
    
    private String operationFlag;
    

    /**
     * Every subProduct keeps versions to track the modifications made on it.
     * This field denotes the latest version of this subproduct
     */
    private int versionNumber;

    /**
     * Fetched from Onetime. Possible values are
     * N - New, I - Inactive, A- Active
     */
    private String status;

    private String handlingInfo;

    private String remarks;


	private double minimumWeight;
	
	private double minimumWeightDisplay;

    private double maximumWeight;
	
	private double maximumWeightDisplay;

    private double minimumVolume;
	
	private double minimumVolumeDisplay;

    private double maximumVolume;
	
	private double maximumVolumeDisplay;
	
	private String volumeUnit;
	
	private String weightUnit;
	
    private String additionalRestrictions;
    
    private LocalDate startDate;

    private LocalDate endDate;
    private double minimumDimension;

	private double minimumDimensionDisplay;

    private double maximumDimension;

	private double maximumDimensionDisplay;
	private String displayDimensionCode;
    /**
     * Transport mode associated with the product
     */
    private String productTransportMode;

    /**
     * Priority associated with the product
     */
    private String productPriority;

    /**
     * SCC associated with the product
     */
    private String productScc;
    
    /**
     * Reason for not modifying the subproduct values can be "MANUALY UPDATED" or "BOOKING EXISTS"
     */
    private String reason;
    
    /**
     * Flag for indicating call is coming from the client
     */
    private boolean isClientCall=false;
    /**
     * Flag for identifying subproduct by latest Version
     */
    private boolean isLatestVersion = false ;

    /**
     * Commodities for which this product is not applicable
     */
    private Collection<RestrictionCommodityVO> restrictionCommodity;

    /**
     * Segments for which this product is not applicable
     */
    private Collection<RestrictionSegmentVO> restrictionSegment;

    /**
     * Stations (Origin or destination) for which this product is
     * not applicable
     */
    private Collection<RestrictionStationVO> restrictionStation;

    /**
     * Customer groups for which this product is not applicable
     */
    private Collection<RestrictionCustomerGroupVO> restrictionCustomerGroup;

    /**
     * Paymen terms for which this product is not applicable
     */
    private Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms;
    
    /**
     * Services
     */
    private Collection<ProductServiceVO> services;
    
    private Collection<ProductEventVO> events;

    /**
     * This flag will be set as true if the subproduct is
     * modified manually. If this flag is true then
     * the product updates will not be reflected this
     * sub product level (if airline parameter is enabled
     * for this check).
     */
    private boolean isAlreadyModifed;
	
	/**
     * For optimistic locking
     */
    private LocalDate lastUpdateDate;

    /**
     * For optimistic locking
     */
    private String lastUpdateUser;

    /**
     * @return Returns the additionalRestrictions.
     */
    public String getAdditionalRestrictions() {
        return additionalRestrictions;
    }
    /**
     * @param additionalRestrictions The additionalRestrictions to set.
     */
    public void setAdditionalRestrictions(String additionalRestrictions) {
        this.additionalRestrictions = additionalRestrictions;
    }
    /**
	 * @return Returns the endDate.
	 */
	public LocalDate getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the startDate.
	 */
	public LocalDate getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
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
     * @return Returns the handlingInfo.
     */
    public String getHandlingInfo() {
        return handlingInfo;
    }
    /**
     * @param handlingInfo The handlingInfo to set.
     */
    public void setHandlingInfo(String handlingInfo) {
        this.handlingInfo = handlingInfo;
    }
    /**
     * @return Returns the isAlreadyModifed.
     */
    public boolean getIsAlreadyModifed() {
        return isAlreadyModifed;
    }
    /**
     * @param isAlreadyModifed The isAlreadyModifed to set.
     */
    public void setAlreadyModifed(boolean isAlreadyModifed) {
        this.isAlreadyModifed = isAlreadyModifed;
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
    /**
     * @return Returns the maximumVolume.
     */
    public double getMaximumVolume() {
        return maximumVolume;
    }
    /**
     * @param maximumVolume The maximumVolume to set.
     */
    public void setMaximumVolume(double maximumVolume) {
        this.maximumVolume = maximumVolume;
    }
    /**
     * @return Returns the maximumWeight.
     */
    public double getMaximumWeight() {
        return maximumWeight;
    }
    /**
     * @param maximumWeight The maximumWeight to set.
     */
    public void setMaximumWeight(double maximumWeight) {
        this.maximumWeight = maximumWeight;
    }
    /**
     * @return Returns the minimumVolume.
     */
    public double getMinimumVolume() {
        return minimumVolume;
    }
    /**
     * @param minimumVolume The minimumVolume to set.
     */
    public void setMinimumVolume(double minimumVolume) {
        this.minimumVolume = minimumVolume;
    }
    /**
     * @return Returns the minimumWeight.
     */
    public double getMinimumWeight() {
        return minimumWeight;
    }
    /**
     * @param minimumWeight The minimumWeight to set.
     */
    public void setMinimumWeight(double minimumWeight) {
        this.minimumWeight = minimumWeight;
    }
    /**
     * @return Returns the productCode.
     */
    public String getProductCode() {
        return productCode;
    }
    /**
     * @param productCode The productCode to set.
     */
    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
    /**
     * @return Returns the productPriority.
     */
    public String getProductPriority() {
        return productPriority;
    }
    /**
     * @param productPriority The productPriority to set.
     */
    public void setProductPriority(String productPriority) {
        this.productPriority = productPriority;
    }
    /**
     * @return Returns the productScc.
     */
    public String getProductScc() {
        return productScc;
    }
    /**
     * @param productScc The productScc to set.
     */
    public void setProductScc(String productScc) {
        this.productScc = productScc;
    }
    /**
     * @return Returns the productTransportMode.
     */
    public String getProductTransportMode() {
        return productTransportMode;
    }
    /**
     * @param productTransportMode The productTransportMode to set.
     */
    public void setProductTransportMode(String productTransportMode) {
        this.productTransportMode = productTransportMode;
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
     * @return Returns the restrictionCommodity.
     */
    public Collection<RestrictionCommodityVO> getRestrictionCommodity() {
        return restrictionCommodity;
    }
    /**
     * @param restrictionCommodity The restrictionCommodity to set.
     */
    public void setRestrictionCommodity(Collection<RestrictionCommodityVO> restrictionCommodity) {
        this.restrictionCommodity = restrictionCommodity;
    }
    /**
     * @return Returns the restrictionCustomerGroup.
     */
    public Collection<RestrictionCustomerGroupVO> getRestrictionCustomerGroup() {
        return restrictionCustomerGroup;
    }
    /**
     * @param restrictionCustomerGroup The restrictionCustomerGroup to set.
     */
    public void setRestrictionCustomerGroup(Collection<RestrictionCustomerGroupVO> restrictionCustomerGroup) {
        this.restrictionCustomerGroup = restrictionCustomerGroup;
    }
    /**
     * @return Returns the restrictionPaymentTerms.
     */
    public Collection<RestrictionPaymentTermsVO> getRestrictionPaymentTerms() {
        return restrictionPaymentTerms;
    }
    /**
     * @param restrictionPaymentTerms The restrictionPaymentTerms to set.
     */
    public void setRestrictionPaymentTerms(Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms) {
        this.restrictionPaymentTerms = restrictionPaymentTerms;
    }
    /**
     * @return Returns the restrictionSegment.
     */
    public Collection<RestrictionSegmentVO> getRestrictionSegment() {
        return restrictionSegment;
    }
    /**
     * @param restrictionSegment The restrictionSegment to set.
     */
    public void setRestrictionSegment(Collection<RestrictionSegmentVO> restrictionSegment) {
        this.restrictionSegment = restrictionSegment;
    }
    /**
     * @return Returns the restrictionStation.
     */
    public Collection<RestrictionStationVO>getRestrictionStation() {
        return restrictionStation;
    }
    /**
     * @param restrictionStation The restrictionStation to set.
     */
    public void setRestrictionStation(Collection<RestrictionStationVO> restrictionStation) {
        this.restrictionStation = restrictionStation;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * @return Returns the subProductCode.
     */
    public String getSubProductCode() {
        return subProductCode;
    }
    /**
     * @param subProductCode The subProductCode to set.
     */
    public void setSubProductCode(String subProductCode) {
        this.subProductCode = subProductCode;
    }
    /**
     * @return Returns the versionNumber.
     */
    public int getVersionNumber() {
        return versionNumber;
    }
    /**
     * @param versionNumber The versionNumber to set.
     */
    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }
	/**
	 * @return
	 */
	public double getMaximumVolumeDisplay() {
		return maximumVolumeDisplay;
	}
	/**
	 * 
	 * @param maximumVolumeDisplay
	 */
	public void setMaximumVolumeDisplay(double maximumVolumeDisplay) {
		this.maximumVolumeDisplay = maximumVolumeDisplay;
	}
	/**
	 * 
	 * @return double
	 */
	public double getMaximumWeightDisplay() {
		return maximumWeightDisplay;
	}
	/**
	 * 
	 * @param maximumWeightDisplay
	 */
	public void setMaximumWeightDisplay(double maximumWeightDisplay) {
		this.maximumWeightDisplay = maximumWeightDisplay;
	}
	/**
	 * 
	 * @return double
	 */
	public double getMinimumVolumeDisplay() {
		return minimumVolumeDisplay;
	}
	/**
	 * 
	 * @param minimumVolumeDisplay
	 */
	public void setMinimumVolumeDisplay(double minimumVolumeDisplay) {
		this.minimumVolumeDisplay = minimumVolumeDisplay;
	}
	/**
	 * 
	 * @return double
	 */
	public double getMinimumWeightDisplay() {
		return minimumWeightDisplay;
	}
	/**
	 * 
	 * @param minimumWeightDisplay
	 */
	public void setMinimumWeightDisplay(double minimumWeightDisplay) {
		this.minimumWeightDisplay = minimumWeightDisplay;
	}
	/**		
	 * 
	 * @return String
	 */
	public String getVolumeUnit() {
		return volumeUnit;
	}
	/**
	 * 
	 * @param volumeUnit
	 */
	public void setVolumeUnit(String volumeUnit) {
		this.volumeUnit = volumeUnit;
	}
	/**
	 * @return
	 */
	public String getWeightUnit() {
		return weightUnit;
	}
	/**
	 * @param weightUnit
	 */
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	/**
	 * @return operationFlag
	 */
	public String getOperationFlag() {
		return operationFlag;
	}
	/**
	 * @param operationFlag
	 */
	
	public void setOperationFlag(String operationFlag) {
		this.operationFlag = operationFlag;
	}
	/**
	 * @return Returns the services.
	 */
	public Collection<ProductServiceVO> getServices() {
		return services;
		
	}
	/**
	 * @param services The services to set.
	 */
	public void setServices(Collection<ProductServiceVO> services) {
		this.services = services;
	}
	/**
	 * @return Returns the reason.
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason The reason to set.
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	/**
	 * @return Returns the events.
	 */
	public Collection<ProductEventVO> getEvents() {
		return events;
	}
	/**
	 * @param events The events to set.
	 */
	public void setEvents(Collection<ProductEventVO> events) {
		this.events = events;
	}
	public boolean isClientCall() {
		return isClientCall;
	}
	public void setClientCall(boolean isClientCall) {
		this.isClientCall = isClientCall;
	}
	/**
	 * @return Returns the isLatestVersion.
	 */
	public boolean isLatestVersion() {
		return isLatestVersion;
	}
	/**
	 * @param isLatestVersion The isLatestVersion to set.
	 */
	public void setLatestVersion(boolean isLatestVersion) {
		this.isLatestVersion = isLatestVersion;
	}
	/**
	 * @return Returns the lastUpdateDate.
	 */
	public LocalDate getLastUpdateDate() {
		return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate The lastUpdateDate to set.
	 */
	public void setLastUpdateDate(LocalDate lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	/**
	 * 	Getter for minimumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public double getMinimumDimension() {
		return minimumDimension;
	}
	/**
	 *  @param minimumDimension the minimumDimension to set
	 * 	Setter for minimumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMinimumDimension(double minimumDimension) {
		this.minimumDimension = minimumDimension;
	}
	/**
	 * 	Getter for minimumDimensionDisplay 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public double getMinimumDimensionDisplay() {
		return minimumDimensionDisplay;
	}
	/**
	 *  @param minimumDimensionDisplay the minimumDimensionDisplay to set
	 * 	Setter for minimumDimensionDisplay 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMinimumDimensionDisplay(double minimumDimensionDisplay) {
		this.minimumDimensionDisplay = minimumDimensionDisplay;
	}
	/**
	 * 	Getter for maximumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public double getMaximumDimension() {
		return maximumDimension;
	}
	/**
	 *  @param maximumDimension the maximumDimension to set
	 * 	Setter for maximumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMaximumDimension(double maximumDimension) {
		this.maximumDimension = maximumDimension;
	}
	/**
	 * 	Getter for maximumDimensionDisplay 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public double getMaximumDimensionDisplay() {
		return maximumDimensionDisplay;
	}
	/**
	 *  @param maximumDimensionDisplay the maximumDimensionDisplay to set
	 * 	Setter for maximumDimensionDisplay 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMaximumDimensionDisplay(double maximumDimensionDisplay) {
		this.maximumDimensionDisplay = maximumDimensionDisplay;
	}
	/**
	 * 	Getter for displayDimensionCode 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public String getDisplayDimensionCode() {
		return displayDimensionCode;
	}
	/**
	 *  @param displayDimensionCode the displayDimensionCode to set
	 * 	Setter for displayDimensionCode 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	public void setDisplayDimensionCode(String displayDimensionCode) {
		this.displayDimensionCode = displayDimensionCode;
	}
	
}
