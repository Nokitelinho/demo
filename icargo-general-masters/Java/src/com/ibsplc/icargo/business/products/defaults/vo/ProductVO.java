/*
 * ProductVO.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.util.time.LocalDate;

import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.xibase.server.framework.vo.AbstractVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestRequestVO;
import com.ibsplc.xibase.util.suggest.vo.SuggestResponseVO;



/**
 * * @author A-1358
 *
 */
public class ProductVO extends AbstractVO implements Serializable{

    private static final long serialVersionUID = 7777191443129761106L;

    /**
     * This is for identifying the product for audit
     */
    public static final String PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME = "products";
    /**
     * This is for identifying the module for audit
     */
    public static final String PRODUCTSDEFAULTS_AUDIT_MODULENAME = "defaults";
    /**
     * This will be used for identifying entity for audit and also for creating the audit vo.
     */
    public static final String PRODUCTSDEFAULTS_AUDIT_ENTITYNAME = "products.defaults.Product";
	/**
	 * This is used for specifying the version number
	 */
    public  static final int  VERSIONNO=1;
    /**
     * Specifies the new status
     */
	public  static final String NEWSTATUS="N";
	/**
	 * Specifies the active status
	 */
	public static final String ACTIVE="A";

	public static final String INACTIVE="I";
	/**
	 * Specifies whether any subproduct is manualy modified
	 */
	public  static final String REASON_MANUALY_UPDATED="MANUALY UPDATED";
	/**
	 * Specifies any booking exists for the subproduct
	 */
	public  static final String REASON_BOOKING_EXISTS="BOOKING EXISTS";
	/**
	 * Specifies the null string
	 */
	public  static final String NULLSTRING = "";

	public  static final String CATEGORY_VALUABLE = "VAL";
	
	public  static final String CATEGORY_COOL = "COL";
	
	public  static final String CATEGORY_PERISHABLE = "PER";

	//Added by A-7979 for ICRD-264016
	public  static final String CATEGORY_TC = "TC";
	
	private String companyCode;

    private String productCode;

    private String productName;

    private String iconContentType;

    /**
     * Fetched from Onetime. Possible values are
     * N - New, I - Inactive, A- Active
     */
    private String status;

    /**
     * Short description of the product
     */
    private String description;

    private String detailedDescription;

    private LocalDate startDate;

    private LocalDate endDate;

    private String finalDate;

    private String finalTransportMode;

    private String finalPriority;

    private String finalService;

    private String finalGenInstructions;

    private String finalCommodities;
    /**
     * To store the icon representing the product
     */
    private String productIcon;

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

	private String displayVolumeCode;

	private String displayWeightCode;

    private String additionalRestrictions;

    // Added as part of CR ICRD-237928 by A-8154
    private String overrideCapacity;

    private boolean isRateDefined;

    private boolean isBookingMandatory;
    
    private boolean coolProduct;

    private boolean displayInPortal;//Added for ICRD-352832
    private String prdPriority;//Added for ICRD-350746

    private byte[] iconForProduct;

    private ImageModel image;

    private boolean isProductIconPresent;

    private String documentType;

    private String documentSubType;

    //Added by A-5867 for  ICRD-111938
    private String route;
    
    private String productPriority;
    
    private String containerType;
    
    private double charge;
    
    private String currency;
    
    private boolean recomentedProduct;
    
    private double minimumDimension;

	private double minimumDimensionDisplay;

    private double maximumDimension;

	private double maximumDimensionDisplay;
	private String displayDimensionCode;
    
    /**
     * @author A-1944
     * for checkbox Enable Proactive Milestone Management
     */
    private String proactiveMilestoneEnabled;

	/**
     * Services offered with th produc
     */
    private Collection<SubProductVO> subProducts;

    /**
     * Services offered with th produc
     */
    private Collection<ProductServiceVO> services;

    /**
     * Transport modes associated with the product
     */
    private Collection<ProductTransportModeVO> transportMode;

    /**
     * Priorities associated with the product
     */
    private Collection<ProductPriorityVO> priority;

    /**
     * SCC's associated with the product
     */
    private Collection<ProductSCCVO> productScc;

    /**
     * Milestones associated with the product. Also contains
     * services in the product which has attached milestones
     */
    private Collection<ProductEventVO> productEvents;

    /**
     * Commodities for which this product is not applicable
     */
    private Collection<RestrictionCommodityVO> restrictionCommodity;

    /**
     * Segments for which this product is not applicable
     */
    private Collection <RestrictionSegmentVO>restrictionSegment;

    /**
     * Stations (Origin or destination) for which this product is
     * not applicable
     */
    private Collection <RestrictionStationVO>restrictionStation;

    /**
     * Customer groups for which this product is not applicable
     */
    private Collection <RestrictionCustomerGroupVO>restrictionCustomerGroup;

    /**
     * Paymen terms for which this product is not applicable
     */
    private Collection <RestrictionPaymentTermsVO>restrictionPaymentTerms;

    /**
     * For optimistic locking
     */
    private LocalDate lastUpdateDate;

    private String lastUpdateUser;

    /**
     * Possible values are 'I-Insert','U-Update' and 'D-Delete'
     */
    private String operationFlag;

    private String productCategory;//Added for ICRD-166985 by A-5117
    //Added for ICRD-259237 by A-7740
    private Collection<ProductParamterVO> productParamters;
	private Map<String, String> productParameterMap;


    /**
	 * @return the productCategory
	 */
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
    /**
	 * @return Returns the iconForProduct.
	 */
	public byte[] getIconForProduct() {
		return iconForProduct;
	}
	/**
	 * @param iconForProduct The iconForProduct to set.
	 */
	public void setIconForProduct(byte[] iconForProduct) {
		this.iconForProduct = iconForProduct;
	}
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
     * @return Returns the description.
     */
    public String getDescription() {
        return description;
    }
    /**
     * @param description The description to set.
     */
    public void setDescription(String description) {
        this.description = description;
    }
    /**
     * @return Returns the detailedDescription.
     */
    public String getDetailedDescription() {
        return detailedDescription;
    }
    /**
     * @param detailedDescription The detailedDescription to set.
     */
    public void setDetailedDescription(String detailedDescription) {
        this.detailedDescription = detailedDescription;
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
     * @return Returns the priority.
     */
    public Collection<ProductPriorityVO> getPriority() {
        return priority;
    }
    /**
     * @param priority The priority to set.
     */
    public void setPriority(Collection<ProductPriorityVO> priority) {
        this.priority = priority;
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
     * @return Returns the productEvents.
     */
    public Collection<ProductEventVO> getProductEvents() {
        return productEvents;
    }
    /**
     * @param productEvents The productEvents to set.
     */
    public void setProductEvents(Collection<ProductEventVO> productEvents) {
        this.productEvents = productEvents;
    }
    /**
     * @return Returns the productIcon.
     */
    public String getProductIcon() {
        return productIcon;
    }
    /**
     * @param productIcon The productIcon to set.
     */
    public void setProductIcon(String productIcon) {
        this.productIcon = productIcon;
    }
    /**
     * @return Returns the productName.
     */
    public String getProductName() {
        return productName;
    }
    /**
     * @param productName The productName to set.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }
    /**
     * @return Returns the productScc.
     */
    public Collection<ProductSCCVO> getProductScc() {
        return productScc;
    }
    /**
     * @param productScc The productScc to set.
     */
    public void setProductScc(Collection<ProductSCCVO> productScc) {
        this.productScc = productScc;
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
    public Collection<RestrictionStationVO> getRestrictionStation() {
        return restrictionStation;
    }
    /**
     * @param restrictionStation The restrictionStation to set.
     */
    public void setRestrictionStation(Collection<RestrictionStationVO> restrictionStation) {
        this.restrictionStation = restrictionStation;
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
     * @return Returns the transportMode.
     */
    public Collection<ProductTransportModeVO> getTransportMode() {
        return transportMode;
    }
    /**
     * @param transportMode The transportMode to set.
     */
    public void setTransportMode(Collection<ProductTransportModeVO> transportMode) {
        this.transportMode = transportMode;
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
	public double getMaximumVolumeDisplay() {
		return maximumVolumeDisplay;
	}

	public void setMaximumVolumeDisplay(double maximumVolumeDisplay) {
		this.maximumVolumeDisplay = maximumVolumeDisplay;
	}

	public double getMaximumWeightDisplay() {
		return maximumWeightDisplay;
	}

	public void setMaximumWeightDisplay(double maximumWeightDisplay) {
		this.maximumWeightDisplay = maximumWeightDisplay;
	}

	public double getMinimumVolumeDisplay() {
		return minimumVolumeDisplay;
	}

	public void setMinimumVolumeDisplay(double minimumVolumeDisplay) {
		this.minimumVolumeDisplay = minimumVolumeDisplay;
	}

	public double getMinimumWeightDisplay() {
		return minimumWeightDisplay;
	}

	public void setMinimumWeightDisplay(double minimumWeightDisplay) {
		this.minimumWeightDisplay = minimumWeightDisplay;
	}

	public String getDisplayVolumeCode() {
		return displayVolumeCode;
	}

	public void setDisplayVolumeCode(String displayVolumeCode) {
		this.displayVolumeCode = displayVolumeCode;
	}

	public String getDisplayWeightCode() {
		return displayWeightCode;
	}

	public void setDisplayWeightCode(String displayWeightCode) {
		this.displayWeightCode = displayWeightCode;
	}

	public Collection<SubProductVO> getSubProducts() {
		return subProducts;
	}

	public void setSubProducts(Collection<SubProductVO> subProducts) {
		this.subProducts = subProducts;
	}
	public boolean getIsRateDefined() {
		return isRateDefined;
	}
	public void setIsRateDefined(boolean rateDefined) {
		this.isRateDefined = rateDefined;
	}
	public String getOverrideCapacity() {
		return overrideCapacity;
	}
	public void setOverrideCapacity(String overrideCapacity) {
		this.overrideCapacity = overrideCapacity;
	}
	public boolean isProductIconPresent() {
		return isProductIconPresent;
	}
	public void setProductIconPresent(boolean isProductIconPresent) {
		this.isProductIconPresent = isProductIconPresent;
	}
	/**
	 * 
	 * @return
	 */
	public boolean getIsProductIconPresent(){
		return (isProductIconPresent);
	}
	/**
	 * 
	 * @param isProductIconPresent
	 */
	public void setIsProductIconPresent(boolean isProductIconPresent){
		setProductIconPresent(isProductIconPresent);
	}
	/**
	 * @param isProductIconPresent The isProductIconPresent to set.
	 */
	/**
	 * @return Returns the finalDate.
	 */
	public String getFinalDate() {
		return finalDate;
	}
	/**
	 * @param finalDate The finalDate to set.
	 */
	public void setFinalDate(String finalDate) {
		this.finalDate = finalDate;
	}
	/**
	 * @return Returns the finalTransportMode.
	 */
	public String getFinalTransportMode() {
		return finalTransportMode;
	}
	/**
	 * @param finalTransportMode The finalTransportMode to set.
	 */
	public void setFinalTransportMode(String finalTransportMode) {
		this.finalTransportMode = finalTransportMode;
	}
	/**
	 * @return Returns the finalPriority.
	 */
	public String getFinalPriority() {
		return finalPriority;
	}
	/**
	 * @param finalPriority The finalPriority to set.
	 */
	public void setFinalPriority(String finalPriority) {
		this.finalPriority = finalPriority;
	}
	/**
	 * @return Returns the finalService.
	 */
	public String getFinalService() {
		return finalService;
	}
	/**
	 * @param finalService The finalService to set.
	 */
	public void setFinalService(String finalService) {
		this.finalService = finalService;
	}
	/**
	 * @return Returns the finalGenInstructions.
	 */
	public String getFinalGenInstructions() {
		return finalGenInstructions;
	}
	/**
	 * @param finalGenInstructions The finalGenInstructions to set.
	 */
	public void setFinalGenInstructions(String finalGenInstructions) {
		this.finalGenInstructions = finalGenInstructions;
	}
	/**
	 * @return Returns the finalCommodities.
	 */
	public String getFinalCommodities() {
		return finalCommodities;
	}
	/**
	 * @param finalCommodities The finalCommodities to set.
	 */
	public void setFinalCommodities(String finalCommodities) {
		this.finalCommodities = finalCommodities;
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
	/**
	 * @return Returns the iconContentType.
	 */
	public String getIconContentType() {
		return iconContentType;
	}
	/**
	 * @param iconContentType The iconContentType to set.
	 */
	public void setIconContentType(String iconContentType) {
		this.iconContentType = iconContentType;
	}

	/**
	 *
	 * @return
	 */
	public boolean isBookingMandatory() {
		return isBookingMandatory;
	}

	/**
	 *
	 * @param isBookingMandatory
	 */
	public void setBookingMandatory(boolean isBookingMandatory) {
		this.isBookingMandatory = isBookingMandatory;
	}

	/**
	 *
	 * @return String
	 */
	public String getDocumentSubType() {
		return documentSubType;
	}

	/**
	 *
	 * @param documentSubType
	 */
	public void setDocumentSubType(String documentSubType) {
		this.documentSubType = documentSubType;
	}

	/**
	 *
	 * @return String
	 */
	public String getDocumentType() {
		return documentType;
	}

	/**
	 *
	 * @param documentType
	 */
	public void setDocumentType(String documentType) {
		this.documentType = documentType;
	}

	/**
	 *
	 * @return
	 */
	public String getProactiveMilestoneEnabled() {
		return proactiveMilestoneEnabled;
	}

	/**
	 *
	 * @param proactiveMilestoneEnabled
	 */
	public void setProactiveMilestoneEnabled(String proactiveMilestoneEnabled) {
		this.proactiveMilestoneEnabled = proactiveMilestoneEnabled;
	}
	/**
	 * @return the coolProduct
	 */
	public boolean isCoolProduct() {
		return coolProduct;
	}
	/**
	 * @param coolProduct the coolProduct to set
	 */
	public void setCoolProduct(boolean coolProduct) {
		this.coolProduct = coolProduct;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	public String getContainerType() {
		return containerType;
	}
	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}
	public double getCharge() {
		return charge;
	}
	public void setCharge(double charge) {
		this.charge = charge;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	public boolean getIsDisplayInPortal() {
		return displayInPortal;
	}
	public void setDisplayInPortal(boolean displayInPortal) {
		this.displayInPortal = displayInPortal;
	}
	public boolean isRecomentedProduct() {
		return recomentedProduct;
	}
	public void setRecomentedProduct(boolean recomentedProduct) {
		this.recomentedProduct = recomentedProduct;
	}
	public String getPrdPriority() {
		return prdPriority;
	}
	public void setPrdPriority(String prdPriority) {
		this.prdPriority = prdPriority;
	}

	public SuggestResponseVO getMappedVO() {
		SuggestResponseVO responseVO = new SuggestResponseVO();
		responseVO.setCode(this.productName);
		responseVO.setDescription(this.productName);
		return responseVO;
	}
	public void setMappingVO(SuggestRequestVO requestVO) {
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
	 * 	Used for :ICRD-232462
	 */
	public String getDisplayDimensionCode() {
		return displayDimensionCode;
	}
	/**
	 *  @param displayDimensionCode the displayDimensionCode to set
	 * 	Setter for displayDimensionCode 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setDisplayDimensionCode(String displayDimensionCode) {
		this.displayDimensionCode = displayDimensionCode;
	}
	/**
	 * 	Getter for productParamters 
	 *	Added by : A-7740 on 22-Nov-2018
	 * 	Used for :
	 */
	public Collection<ProductParamterVO> getProductParamters() {
		return productParamters;
	}
	/**
	 *  @param productParamters the productParamters to set
	 * 	Setter for productParamters 
	 *	Added by : A-7740 on 22-Nov-2018
	 * 	Used for :
	 */
	public void setProductParamters(Collection<ProductParamterVO> productParamters) {
		this.productParamters = productParamters;
	}
	/**
	 * 	Getter for productParameterMap 
	 *	Added by : A-7740 on 22-Nov-2018
	 * 	Used for :
	 */
	public Map<String, String> getProductParameterMap() {
		return productParameterMap;
	}
	/**
	 *  @param productParameterMap the productParameterMap to set
	 * 	Setter for productParameterMap 
	 *	Added by : A-7740 on 22-Nov-2018
	 * 	Used for :
	 */
	public void setProductParameterMap(Map<String, String> productParameterMap) {
		this.productParameterMap = productParameterMap;
	}
}
