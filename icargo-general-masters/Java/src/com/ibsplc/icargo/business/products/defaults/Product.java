/*
 * Product.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.products.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.ibsplc.icargo.business.products.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.unit.vo.UnitConversionVO;
import com.ibsplc.icargo.framework.model.ImageModel;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsPersistenceConstants;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditAction;
import com.ibsplc.xibase.server.framework.audit.vo.AuditFieldVO;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 *
 * @author A-1358
 *
 */
@Table(name="PRDMST")
@Entity
public class Product {

	private Log log = LogFactory.getLogger("PRODUCT");
	 private static Log loglog = LogFactory.getLogger("PRODUCT");
	/**
	 * It gives the Product Pk
	 */
	private ProductPK productPk;
	/**
	 * productName
	 */
	private String productName;

	/**
	 *
	 * Fetched from Onetime. Possible values are
	 * N - New, I - Inactive, A- Active
	 */
	private String status;

	/**
	 * Short description of the product
	 */
	private String description;

	private String detailedDescription;

	private String bookingIndicator;

	/**
	 * Date from which the product is available
	 */
	private Calendar startDate;

	/**
	 * Expiry date of the product
	 */
	private Calendar endDate;

	/**
	 * To store the icon representing the product
	 */


	/**
	 * ProductHandling Information
	 */
	private String handlingInfo;
	/**
	 * Product Remarks
	 */
	private String remarks;

	/**
	 * Minimum weight displayed to client
	 */
	private double minimumDisplayWeight;

	/**
	 * converted value based on system level weight code
	 */
	private double minimumWeight;

	/**
	 * Maximum weight displayed to client
	 */
	private double maximumDisplayWeight;

	/**
	 * weightcode displayed to client
	 */
	private String displayWeightCode;

	/**
	 * converted value based on system level weight code
	 */
	private double maximumWeight;

	/**
	 * Minimum volume displayed to client
	 */
	private double minimumDisplayVolume;

	/**
	 * converted value based on system level volume code
	 */
	private double minimumVolume;
	//Added as part of CR-ICRD-232462
	private double minimumDimension;
	private double maximumDimension;
	private double maximumDisplayDimension;
	private double minimumDisplayDimension;
	private String displayDimensionCode;

	/**
	 * Maximum volume displayed to client
	 */
	private double maximumDisplayVolume;

	/**
	 *  volumecode displayed to client
	 */
	private String displayVolumeCode;

	/**
	 * converted value based on system level volume code
	 */
	private double maximumVolume;
	/**
	 * Product Additional Restrictions
	 */
	private String additionalRestrictions;
	
	
	// Added as part of CR ICRD-237928 by A-8154
    /** The is override capacity. */
	private String overrideCapacity;
    
    /** Product is rateDefined or not. */
	private String isRateDefined;
	private String coolIndicator;
	private String displayInPortal;//Added for ICRD-352832
	private String productPriority;//Added for ICRD-350746

	private ProductIcon productIcon;

	private String documentType;

	private String documentSubType;
	
	/**
	 * @author A-1944
	 * to set the Enable Proactive Milestone Management
	 */
	private String proactiveMilestoneEnabled;
	/**
	 * Services offered with th produc
	 */
	private Set<ProductService> services;

	/**
	 * Transport modes associated with the product
	 */
	private Set<ProductTransportMode> transportMode;

	/**
	 * Priorities associated with the product
	 */
	private Set<ProductPriority> priority;

	/**
	 * SCC's associated with the product
	 */
	private Set<ProductSCC> productScc;

	/**
	 * Milestones associated with the product. Also contains
	 * services in the product which has attached milestones
	 */
	private Set<ProductEvent> productEvents;

	/**
	 * Commodities for which this product is not applicable
	 */
	private Set<ProductRestrictionCommodity> restrictionCommodity;

	/**
	 * Segments for which this product is not applicable
	 */
	private Set<ProductRestrictionSegment> restrictionSegment;

	/**
	 * Stations (Origin or destination) for which this product is
	 * not applicable
	 */
	private Set<ProductRestrictionStation> restrictionStation;

	/**
	 * Customer groups for which this product is not applicable
	 */
	private Set<ProductRestrictionCustomerGroup> restrictionCustomerGroup;

	/**
	 * Paymen terms for which this product is not applicable
	 */
	private Set<ProductRestrictionPaymentTerms> restrictionPaymentTerms;


	private Set<SubProduct> subProducts;
	/**
	 * For optimistic locking
	 */
	private Calendar lastUpdateTime;

	/**
	 * For optimistic locking
	 */
	private String lastUpdateUser;
	//Added For Bug ICRD-28571 by A-5258
	private static final String CONVERTION_UNIT_ERR = "shared.defaults.unit.invalidunit"; 
	private static final String CONVERTION_UNIT_NOT_FOUND = "products.defaults.convertionunitnotfound";
	private String productCategory;//Added for ICRD-166985 by A-5117
	private Set<ProductParamters> productParamters;//Added for ICRD-259237 by A-7740
	/**
	 * @return Returns the productPk.
	 */
	@EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="productCode", column=@Column(name="PRDCOD"))
		})
	public ProductPK getProductPk() {
		return productPk;
	}
	/**
	 * @param productPk The productPk to set.
	 */
	public void setProductPk(ProductPK productPk) {
		this.productPk = productPk;
	}
	/**
	 * @return Returns the productName.
	 */
	@Column(name="PRDNAM")
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
	 * @return Returns the startDate.
	 */
	@Column(name="PRDSTRDAT")
	@Audit(name="StartDate")
	@Temporal(TemporalType.DATE)
	public Calendar getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate The startDate to set.
	 */
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return Returns the endDate.
	 */
	@Column(name="PRDENDDAT")
	@Audit(name="EndDate")
	@Temporal(TemporalType.DATE)
	public Calendar getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate The endDate to set.
	 */
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return Returns the additionalRestrictions.
	 */
	@Column(name="PRDADLRES")
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
	 * @return Returns the description.
	 */
	@Column(name="PRDDES")
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
	@Column(name="PRDDETDES")
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
	 * @return Returns the handlingInfo.
	 */
	@Column(name="PRDHDLINF")
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
	 * @return Returns the maximumVolume.
	 */
	@Column(name="MAXVOLRES")
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
	@Column(name="MAXWGTRES")
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
	@Column(name="MINVOLRES")
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
	@Column(name="MINWGTRES")
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
	 *
	 * @return maximumDisplayVolume
	 */
	@Column(name="MAXDISVOLRES")
	public double getMaximumDisplayVolume() {
		return maximumDisplayVolume;
	}

	/**
	 *
	 * @param maximumDisplayVolume
	 */
	public void setMaximumDisplayVolume(double maximumDisplayVolume) {
		this.maximumDisplayVolume = maximumDisplayVolume;
	}

	/**
	 *
	 * @return maximumDisplayWeight
	 */
	@Column(name="MAXDISWGTRES")
	public double getMaximumDisplayWeight() {
		return maximumDisplayWeight;
	}

	/**
	 *
	 * @param maximumDisplayWeight
	 */
	public void setMaximumDisplayWeight(double maximumDisplayWeight) {
		this.maximumDisplayWeight = maximumDisplayWeight;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="DISVOLCODRES")
	public String getDisplayVolumeCode() {
		return displayVolumeCode;
	}

	/**
	 *
	 * @param displayVolumeCode
	 */
	public void setDisplayVolumeCode(String displayVolumeCode) {
		this.displayVolumeCode = displayVolumeCode;
	}

	/**
	 *
	 * @return
	 */
	@Column(name="DISWGTCODRES")
	public String getDisplayWeightCode() {
		return displayWeightCode;
	}

	/**
	 *
	 * @param displayWeightCode
	 */
	public void setDisplayWeightCode(String displayWeightCode) {
		this.displayWeightCode = displayWeightCode;
	}

	/**
	 *
	 * @return minimumDisplayVolume
	 */
	@Column(name="MINDISVOLRES")
	public double getMinimumDisplayVolume() {
		return minimumDisplayVolume;
	}

	/**
	 *
	 * @param minimumDisplayVolume
	 */
	public void setMinimumDisplayVolume(double minimumDisplayVolume) {
		this.minimumDisplayVolume = minimumDisplayVolume;
	}


	/**
	 *
	 * @return minimumDisplayWeight
	 */
	@Column(name="MINDISWGTRES")
	public double getMinimumDisplayWeight() {
		return minimumDisplayWeight;
	}

	/**
	 *
	 * @param minimumDisplayWeight
	 */
	public void setMinimumDisplayWeight(double minimumDisplayWeight) {
		this.minimumDisplayWeight = minimumDisplayWeight;
	}
	/**
	 * Gets the checks if override capacity.
	 *
	 * @return the checks if override capacity
	 */
	@Column(name="ISSOVRCAP")
	public String getOverrideCapacity() {
		return overrideCapacity;
	}
	
	/**
	 * Sets the checks if override capacity.
	 *
	 * @param overrideCapacity the new checks if override capacity
	 */
	public void setOverrideCapacity(String overrideCapacity) {
		this.overrideCapacity = overrideCapacity;
	}
	
	/**
	 * Method for getting rateDefined.
	 *
	 * @return String
	 */
	@Column(name="RATDEF")
	public String getIsRateDefined(){
		return this.isRateDefined;
	}
	/**
	 * Method for setting rateDefined
	 * @param rateDefined
	 * @return
	 */
	public void setIsRateDefined(String rateDefined){
		this.isRateDefined=rateDefined;
	}

	/**
	 * @return the coolIndicator
	 */
	//@Column(name="COLIND")//commented in the part of ICRD-166985
	/*public String getCoolIndicator() {
		return coolIndicator;
	}
	*//**
	 * @param coolIndicator the coolIndicator to set
	 *//*
	public void setCoolIndicator(String coolIndicator) {
		this.coolIndicator = coolIndicator;
	}*/
	/**
	 * @return the productCategory
	 */
	@Column(name="PRDCTG")
	public String getProductCategory() {
		return productCategory;
	}
	/**
	 * @param productCategory the productCategory to set
	 */
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
    @Column(name="PRTDSP")
	public String getDisplayInPortal() {
		return displayInPortal;
	}
	public void setDisplayInPortal(String displayInPortal) {
		this.displayInPortal = displayInPortal;
	}
	@Column(name="PRDPTY")
	public String getProductPriority() {
		return productPriority;
	}
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	//set the column Names
	/**
	 * @author A-1944
	 * Method to set the documentType
	 * @return String
	 */
	@Column(name="DOCTYP")
	public String getDocumentType(){
		return this.documentType;
	}

	/**
	 * Method to get the documentType
	 * @param documentType
	 */
	public void setDocumentType(String documentType){
		this.documentType = documentType;
	}

	/**
	 * @author A-1944
	 * Method to get the documentSubType
	 * @return String
	 */
	@Column(name="DOCSUBTYP")
	public String getDocumentSubType(){
		return this.documentSubType;
	}

	/**
	 * Method to set the documentSubType
	 * @param documentSubType
	 */
	public void setDocumentSubType(String documentSubType){
		this.documentSubType = documentSubType;
	}
	
	/**
	 * @author A-1944
	 * Method to get the proactiveMilestoneEnabled
	 * @return String
	 */
	@Column(name="MILMGTFLG")
	public String getProactiveMilestoneEnabled(){
		return this.proactiveMilestoneEnabled;
	}

	/**
	 * Method to set the proactiveMilestoneEnabled
	 * @param proactiveMilestoneEnabled
	 */
	public void setProactiveMilestoneEnabled(String proactiveMilestoneEnabled){
		this.proactiveMilestoneEnabled = proactiveMilestoneEnabled;
	}
	
	
	//@author-1944 ends
	/**
	 * Returns the priority.
	 * @return Set<ProductPriority>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductPriority> getPriority() {
		return priority;
	}
	/**
	 * The priority to set.
	 * @param priority
	 * @return
	 */
	public void setPriority(Set<ProductPriority> priority) {
		this.priority = priority;
	}
	/**
	 * Returns the productEvents.
	 * @return  Set<ProductEvent>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductEvent> getProductEvents() {
		return productEvents;
	}
	/**
	 * The productEvents to set.
	 * @param productEvents
	 * @return
	 */
	public void setProductEvents(Set<ProductEvent> productEvents) {
		this.productEvents = productEvents;
	}
	/**
	 * @return Returns the productIcon.
	 */
	@OneToOne(cascade=CascadeType.ALL)
	 @PrimaryKeyJoinColumns({
		 @PrimaryKeyJoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD"),
		 @PrimaryKeyJoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD")
	 })
	 public ProductIcon getProductIcon() {
	 return productIcon;
	 }
	/**
	 * @param productIcon The productIcon to set.
	 */
	 public void setProductIcon(ProductIcon productIcon) {
	 this.productIcon = productIcon;
	 }
	/**
	 * Returns the productScc.
	 * @return Set<ProductSCC>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductSCC> getProductScc() {
		return productScc;
	}
	/**
	 * The productScc to set.
	 * @param productScc
	 * @return
	 */
	public void setProductScc(Set<ProductSCC> productScc) {
		this.productScc = productScc;
	}
	/**
	 * Returns the remarks.
	 * @return remarks
	 */
	@Column(name="PRDRMK")
	public String getRemarks() {
		return remarks;
	}
	/**
	 * The remarks to set.
	 * @param remarks
	 * @return
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	/**
	 * Returns the status.
	 * @return status
	 */
	@Column(name="PRDSTA")
	public String getStatus() {
		return status;
	}
	/**
	 * The status to set.
	 * @param status
	 * @return
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Returns the restrictionCommodity.
	 * @return Set<ProductRestrictionCommodity>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductRestrictionCommodity> getRestrictionCommodity() {
		return restrictionCommodity;
	}
	/**
	 * The restrictionCommodity to set.
	 * @param restrictionCommodity
	 * @return
	 */
	public void setRestrictionCommodity(Set<ProductRestrictionCommodity> restrictionCommodity) {
		this.restrictionCommodity = restrictionCommodity;
	}
	/**
	 * Returns the restrictionCustomerGroup.
	 * @return Set<ProductRestrictionCustomerGroup>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductRestrictionCustomerGroup> getRestrictionCustomerGroup() {
		return restrictionCustomerGroup;
	}
	/**
	 * The restrictionCustomerGroup to set.
	 * @param restrictionCustomerGroup
	 * @return
	 */
	public void setRestrictionCustomerGroup(Set<ProductRestrictionCustomerGroup> restrictionCustomerGroup) {
		this.restrictionCustomerGroup = restrictionCustomerGroup;
	}
	/**
	 * Returns the restrictionPaymentTerms.
	 * @return Set<ProductRestrictionPaymentTerms>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductRestrictionPaymentTerms> getRestrictionPaymentTerms() {
		return restrictionPaymentTerms;
	}
	/**
	 * The restrictionPaymentTerms to set.
	 * @param restrictionPaymentTerms
	 * @return
	 */
	public void setRestrictionPaymentTerms(Set<ProductRestrictionPaymentTerms> restrictionPaymentTerms) {
		this.restrictionPaymentTerms = restrictionPaymentTerms;
	}
	/**
	 * Returns the restrictionSegment.
	 * @return Set<ProductRestrictionSegment>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductRestrictionSegment> getRestrictionSegment() {
		return restrictionSegment;
	}
	/**
	 * The restrictionSegment to set.
	 * @param restrictionSegment
	 * @return
	 */
	public void setRestrictionSegment(Set<ProductRestrictionSegment> restrictionSegment) {
		this.restrictionSegment = restrictionSegment;
	}
	/**
	 * Returns the restrictionStation.
	 * @return Set<ProductRestrictionStation>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductRestrictionStation> getRestrictionStation() {
		return restrictionStation;
	}
	/**
	 * @param restrictionStation The restrictionStation to set.
	 */
	public void setRestrictionStation(Set<ProductRestrictionStation> restrictionStation) {
		this.restrictionStation = restrictionStation;
	}
	/**
	 * Returns the services
	 * @return Set<ProductService>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductService> getServices() {
		return services;
	}
	/**
	 * @param services The services to set.
	 * @return
	 */
	public void setServices(Set<ProductService> services) {
		this.services = services;
	}
	/**
	 * @return Returns the transportMode.
	 * @return Set<ProductTransportMode>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<ProductTransportMode> getTransportMode() {
		return transportMode;
	}
	/**
	 * @param transportMode The transportMode to set.
	 */
	public void setTransportMode(Set<ProductTransportMode> transportMode) {
		this.transportMode = transportMode;
	}
	/**
	 * Method for getting the subProducts
	 * @return Set<SubProduct>
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false) })
		public Set<SubProduct> getSubProducts() {
		return subProducts;
	}
	/**
	 * The subProducts to set.
	 * @param subProducts
	 * @return
	 */
	public void setSubProducts(Set<SubProduct> subProducts) {
		this.subProducts = subProducts;
	}

	/**
	 * @return Returns the lastUpdateDate.
	 */
	@Version
	@Column(name="LSTUPDTIM")
	@Temporal(TemporalType.TIMESTAMP)
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	/**
	 * @param lastUpdateDate The lastUpdateDate to set.
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	/**
	 * @return Returns the lastUpdateUser.
	 */
	@Column(name="LSTUPDUSR")
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
	 * Default Constructor
	 *
	 */
	public Product (){
	}

	/**
	 * constructor of Product class to create the product
	 * @author A-1885
	 * @param productVO
	 * @throws SystemException
	 * @throws FinderException 
	 */
	public Product(ProductVO productVo)throws SystemException{
		log.entering("Product","doProcess");
		populatePk(productVo);
		populateAttributes(productVo);
		try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		log.log(Log.FINE,"Calling Child");
		populateChildren(productVo);
		log.log(Log.FINE,"Calling subProducts");
		populateSubProducts(productVo);
		log.exiting("Product", "doProcess");
	}


	/**
	 * This method is used to update a particular subproduct
	 * @author A-1883
	 * @param subProductVo
	 * @throws SystemException
	 */
	public void updateSubProduct(SubProductVO subProductVo)
	throws SystemException{
		log.entering("SubProduct","--enter");
		SubProduct subProduct=findSubProduct(subProductVo);
		ProductsDefaultsAuditVO auditVO=
			new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		auditVO = (ProductsDefaultsAuditVO)
		AuditUtils.populateAuditDetails(auditVO,subProduct,false);
		//this.setLastUpdateDate(subProductVo.getLastUpdateDate());
		//this.setLastUpdateUser(subProductVo.getLastUpdateUser());
		//subProduct.setLastUpdateTime(subProductVo.getLastUpdateDate().toCalendar());
		//subProduct.setLastUpdateUser(subProductVo.getLastUpdateUser());
		subProduct.update(subProductVo);
		auditVO = (ProductsDefaultsAuditVO)
		AuditUtils.populateAuditDetails(auditVO,subProduct,false);
		auditSubProducttDetailsUpdate("SubProduct Modified",AuditAction.UPDATE.toString(),
				AuditVO.UPDATE_ACTION,auditVO);
		log.exiting("SubProduct", "exit");
		log.log(Log.INFO,"Updation over");
	}

	/**
	 * This method is used to delete a particular subproduct
	 * @author A-1883
	 * @param subProductVo
	 * @throws SystemException
	 * @throws BookingExistsException
	 * @return
	 */
	public void deleteSubProduct(SubProductVO subProductVo)
	throws SystemException,BookingExistsException{
		log.entering("SubProduct","--enter");
		SubProduct subprd = findSubProduct(subProductVo);
		subprd.setLastUpdateTime(subProductVo.getLastUpdateDate().toCalendar());
		subprd.setLastUpdateUser(subProductVo.getLastUpdateUser());
		subprd.remove();
		ProductsDefaultsAuditVO auditVO=
			new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		auditVO = (ProductsDefaultsAuditVO)
		AuditUtils.populateAuditDetails(auditVO,subprd,false);
		auditSubProducttDetailsUpdate("DELETE SUBPRODUCT",AuditAction.DELETE
				.toString(),AuditVO.DELETE_ACTION,auditVO);
		log.exiting("SubProduct", "exit");
	}

	/**
	 * This method is used to modifythe Product details along with child objects
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 * @throws BookingExistsException
	 * @return Collection<SubProductVO>
	 */
	public Collection<SubProductVO> update(ProductVO productVo)
	throws SystemException,BookingExistsException{
		log.entering("Update Product------------------------>","doProcess");
		if(productVo.getProductName()!=null){
		setProductName(productVo.getProductName().trim());
		}
		setDescription(productVo.getDescription());
		if(productVo.isBookingMandatory()){
			setBookingIndicator(ProductVO.FLAG_YES);
		}else{
			setBookingIndicator(ProductVO.FLAG_NO);
		}
		if(productVo.getIsRateDefined()){
			setIsRateDefined(ProductVO.FLAG_YES);
		}
		else{
			setIsRateDefined(ProductVO.FLAG_NO);
		}
		/*if(productVo.isCoolProduct()){
			setCoolIndicator(ProductVO.FLAG_YES);
		}else
			setCoolIndicator(ProductVO.FLAG_NO);*/
		setProductCategory(productVo.getProductCategory());//Added for ICRD-166985 by A-5117
	    //Added for ICRD-352832
		if(productVo.getIsDisplayInPortal()){
			setDisplayInPortal(ProductVO.FLAG_YES);
		}else{
			setDisplayInPortal(ProductVO.FLAG_NO);
		}
		setProductPriority(productVo.getPrdPriority());//Added for ICRD-350746
		setDocumentType(productVo.getDocumentType());
		setDocumentSubType(productVo.getDocumentSubType());
		setOverrideCapacity(productVo.getOverrideCapacity());//Added for ICRD-237928
		setProactiveMilestoneEnabled(productVo.getProactiveMilestoneEnabled());

		setDetailedDescription(productVo.getDetailedDescription());
		setHandlingInfo(productVo.getHandlingInfo());
		setRemarks(productVo.getRemarks());
		//setStatus(productVo.getStatus());
		setMaximumDisplayWeight(productVo.getMaximumWeightDisplay());
		setMinimumDisplayWeight(productVo.getMinimumWeightDisplay());
		setMaximumDisplayVolume(productVo.getMaximumVolumeDisplay());
		setMinimumDisplayVolume(productVo.getMinimumVolumeDisplay());
		//Added as part CR-ICRD-232462
		setMinimumDisplayDimension(productVo.getMinimumDimensionDisplay());
		setMaximumDisplayDimension(productVo.getMaximumDimensionDisplay());
		setDisplayDimensionCode(productVo.getDisplayDimensionCode());
		setDisplayVolumeCode(productVo.getDisplayVolumeCode());
		setDisplayWeightCode(productVo.getDisplayWeightCode());
		convertionUnit();
		setLastUpdateUser(productVo.getLastUpdateUser());
		setAdditionalRestrictions(productVo.getAdditionalRestrictions());
		setStartDate(productVo.getStartDate());
		setEndDate(productVo.getEndDate());
		//setLastUpdateTime(productVo.getLastUpdateDate());
		if(!ProductVO.NEWSTATUS.equals(this.getStatus())){
			checkStatus(productVo);
		}
		updateChildren(productVo);
		log.exiting("Update product","--exit");
		return updateSubProduct(productVo);
	}

	/**
	 *  Method if used for changing the status of subProduct
	 *  @author A-1883
	 *  @param changeStatus
	 *  @throws SystemException
	 *  @throws RateNotDefinedException
	 *  @return
	 */
	public void updateStatus(ProductVO productVo)
	throws SystemException,RateNotDefinedException{
		log.log(Log.FINE,"updateStatus--Changing the status");
		log.log(Log.FINE, "Making the status--------->", productVo.getStatus());
		if(SubProductVO.STATUS_ACTIVE.equals(productVo.getStatus())){
			if (SubProductVO.FLAG_YES.equals(this.getIsRateDefined())){
				if(SubProductVO.STATUS_NEW.equals(this.getStatus())){
					for(SubProduct subProduct:this.getSubProducts()){
						subProduct.setStatus(productVo.getStatus());
						//subProduct.setLastUpdateTime(productVo.getLastUpdateDate());
						//subProduct.setLastUpdateUser(productVo.getLastUpdateUser());
					}
				}else {
					for(SubProduct subProduct:this.getSubProducts()){
						if(SubProductVO.STATUS_NEW.equals(subProduct.getStatus())){
							subProduct.setStatus(productVo.getStatus());
							//subProduct.setLastUpdateTime(productVo.getLastUpdateDate());
							//subProduct.setLastUpdateUser(productVo.getLastUpdateUser());
						}
					}
				}
				this.setStatus(productVo.getStatus());
			}
			else{
				throw new RateNotDefinedException();
			}
		}
		else if(SubProductVO.STATUS_INACTIVE.equals(productVo.getStatus())){
			this.setStatus(productVo.getStatus());
		}
		this.setLastUpdateTime(productVo.getLastUpdateDate().toCalendar());
		this.setLastUpdateUser(productVo.getLastUpdateUser());
		log.exiting("updateStatus","--exit");
	}

	/**
	 * This method will check if any booking exists for the subproduct.
	 * If there is no booking remove() function will be fired
	 * @param subProduct
	 * @author A-1885
	 * @throws BookingExistsException
	 * @throws SystemException
	 */
	public void removeSubProduct(SubProduct subProduct)
	throws BookingExistsException,SystemException{
		log.log(Log.FINE,"removeSubProduct--remove the status");
		SubProductVO subProductVO = new SubProductVO();
		subProductVO.setCompanyCode(subProduct.getSubProductPk().getCompanyCode());
		subProductVO.setProductCode(subProduct.getSubProductPk().getProductCode());
		subProductVO.setProductPriority(subProduct.getProductPriority());
		subProductVO.setProductTransportMode(subProduct.getProductTransportMode());
		subProductVO.setProductScc(subProduct.getProductScc());
		if(subProduct.hasBooking(subProductVO)){
			throw new BookingExistsException();
		}
		subProduct.remove();
		ProductsDefaultsAuditVO auditVO=
			new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		auditVO = (ProductsDefaultsAuditVO)
		AuditUtils.populateAuditDetails(auditVO,subProduct,false);
		auditSubProducttDetailsUpdate("SubProduct Deleted",AuditAction.DELETE.toString(),
				AuditVO.DELETE_ACTION,auditVO);
		log.exiting("removeSubProduct","--exit");

	}
	/**
	 * @author A-1885
	 * @param addInfo
	 * @param action
	 * @param actioncode
	 * @throws SystemException
	 */
	private void auditSubProducttDetails(String addInfo,String action,
			String actioncode,SubProduct subProduct)
	throws SystemException {
		log.entering("auditSubProducttDetails","Going to construct ProductAuditVO for calling performAudit");
		StringBuilder additionalInfo = new StringBuilder();
		ProductsDefaultsAuditVO auditVO=
			new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		auditVO.setCompanyCode(this.getProductPk().getCompanyCode());
		auditVO.setAuditRemarks(action);
		auditVO.setProductCode(this.getProductPk().getProductCode());
		auditVO.setProductName(this.getProductName());
		auditVO.setUserId(this.getLastUpdateUser());
		auditVO.setActionCode(actioncode);
		auditVO = (ProductsDefaultsAuditVO)
		AuditUtils.populateAuditDetails(auditVO,subProduct,true);
		if(auditVO.getAuditFields()!=null && auditVO.getAuditFields().size()>0){
			for(AuditFieldVO auditField : auditVO.getAuditFields()){
				additionalInfo.append(" Field Name: ").append(auditField.getFieldName())
				.append(",").append(" Field Description: ").append(auditField.getDescription())
				.append(",").append(" Old Value: ").append(auditField.getOldValue())
						.append(",").append(" New Value: ").append(auditField.getNewValue());

			}
		}
		auditVO.setAdditionalInfo(additionalInfo.toString().concat(addInfo));
		AuditUtils.performAudit(auditVO);
		log.exiting("auditSubProducttDetails","Called AuditUtils:performAudit with productsDefaultsAuditVO");

	}
	/**
	 * @author A-1885
	 * @param addInfo
	 * @param action
	 * @param actioncode
	 * @throws SystemException
	 */
	private void auditSubProducttDetailsUpdate(String addInfo,String action,
			String actioncode,ProductsDefaultsAuditVO auditVO)
	throws SystemException {
		log.entering("auditSubProducttDetails","Going to construct ProductAuditVO for calling performAudit");
		StringBuilder additionalInfo = new StringBuilder();
		auditVO.setCompanyCode(this.getProductPk().getCompanyCode());
		auditVO.setAuditRemarks(action);
		auditVO.setProductCode(this.getProductPk().getProductCode());
		auditVO.setProductName(this.getProductName());
		auditVO.setUserId(this.getLastUpdateUser());
		auditVO.setActionCode(actioncode);
		if(auditVO.getAuditFields()!=null && auditVO.getAuditFields().size()>0){
			for(AuditFieldVO auditField : auditVO.getAuditFields()){
				additionalInfo.append(" Field Name: ").append(auditField.getFieldName())
				.append(",").append(" Field Description: ").append(auditField.getDescription())
				.append(",").append(" Old Value: ").append(auditField.getOldValue())
						.append(",").append(" New Value: ").append(auditField.getNewValue());

			}
		}
		auditVO.setAdditionalInfo(additionalInfo.toString().concat(addInfo));
		AuditUtils.performAudit(auditVO);
		log.exiting("auditSubProducttDetails","Called AuditUtils:performAudit with productsDefaultsAuditVO");

	}
	/**
	 * Used to populate the business object with values from VO
	 * @author A-1885
	 * @return ProductVO
	 */
	public ProductVO retrieveVO() {
		log.entering("retrieveVO------------------------>","Enter");
		ProductVO productVO = new ProductVO();
		productVO.setProductName(this.getProductName());
		productVO.setProductCode(this.getProductPk().getProductCode());
		productVO.setCompanyCode(this.getProductPk().getCompanyCode());
		productVO.setAdditionalRestrictions(this.getAdditionalRestrictions());
		productVO.setDescription(this.getDescription());
		productVO.setDetailedDescription(this.getDetailedDescription());
		productVO.setHandlingInfo(this.getHandlingInfo());
		productVO.setLastUpdateUser(this.getLastUpdateUser());
		productVO.setMaximumVolume(this.getMaximumVolume());
		productVO.setMaximumWeight(this.getMaximumWeight());
		productVO.setMinimumVolume(this.getMinimumVolume());
		productVO.setMinimumWeight(this.getMinimumWeight());
		//Added as part of CR-ICRD-232462
		productVO.setMinimumDimension(this.minimumDimension);
		productVO.setMaximumDimension(this.maximumDimension);
		productVO.setRemarks(this.getRemarks());

		productVO.setDocumentType(this.documentType);
		productVO.setDocumentSubType(this.documentSubType);
		productVO.setProactiveMilestoneEnabled(this.proactiveMilestoneEnabled);
		if("Y".equals(this.coolIndicator)){
			productVO.setCoolProduct(true);
		}else
			{
			productVO.setCoolProduct(false);
			}
		
		//Added for ICRD-352832
		if("Y".equals(this.displayInPortal)){
			productVO.setDisplayInPortal(true);
		}else{
			productVO.setDisplayInPortal(false);
		}
		productVO.setPrdPriority(this.productPriority);//Added for ICRD-350746
		if (productVO.getServices() == null) {
			productVO.setServices(new ArrayList<ProductServiceVO>());
		}
		for(ProductService productService: this.getServices()){
			productVO.getServices().add(productService.retrieveVO());
		}
		if (productVO.getProductEvents() == null) {
			productVO.setProductEvents(new ArrayList<ProductEventVO>());
		}
		for(ProductEvent productEvent: this.getProductEvents()){
			productVO.getProductEvents().add(productEvent.retrieveVO());
		}
		if (productVO.getPriority() == null) {
			productVO.setPriority(new ArrayList<ProductPriorityVO>());
		}
		for(ProductPriority prodPriority: this.getPriority()){
			productVO.getPriority().add(prodPriority.retrieveVO());
		}
		if (productVO.getProductScc() == null) {
			productVO.setProductScc(new ArrayList<ProductSCCVO>());
		}
		for(ProductSCC productScc: this.getProductScc()){
			productVO.getProductScc().add(productScc.retrieveVO());
		}
		if (productVO.getRestrictionCommodity() == null) {
			productVO.setRestrictionCommodity(new ArrayList<RestrictionCommodityVO>());
		}
		for(ProductRestrictionCommodity productRestrictionCommodity:
			this.getRestrictionCommodity()){
			productVO.getRestrictionCommodity()
			.add(productRestrictionCommodity.retrieveVO());
		}
		if (productVO.getRestrictionCustomerGroup() == null) {
			productVO.setRestrictionCustomerGroup(new ArrayList<RestrictionCustomerGroupVO>());
		}
		for(ProductRestrictionCustomerGroup productRestrictionCustomerGroup:
			this.getRestrictionCustomerGroup()){
			productVO.getRestrictionCustomerGroup()
			.add(productRestrictionCustomerGroup.retrieveVO());
		}
		if (productVO.getRestrictionPaymentTerms() == null) {
			productVO.setRestrictionPaymentTerms(new ArrayList<RestrictionPaymentTermsVO>());
		}
		for(ProductRestrictionPaymentTerms productRestrictionPaymentTerms:
			this.getRestrictionPaymentTerms()){
			productVO.getRestrictionPaymentTerms()
			.add(productRestrictionPaymentTerms.retrieveVO());
		}
		if (productVO.getRestrictionSegment() == null) {
			productVO.setRestrictionSegment(new ArrayList<RestrictionSegmentVO>());
		}
		for(ProductRestrictionSegment productRestrictionSegment:
			this.getRestrictionSegment()){
			productVO.getRestrictionSegment()
			.add(productRestrictionSegment.retrieveVO());
		}
		if (productVO.getRestrictionStation() == null) {
			productVO.setRestrictionStation(new ArrayList<RestrictionStationVO>());
		}
		for(ProductRestrictionStation productRestrictionStation:
			this.getRestrictionStation()){
			productVO.getRestrictionStation()
			.add(productRestrictionStation.retrieveVO());
		}
		log.exiting("retrieveVO","--Exit");
		return productVO;
	}

	/**
	 * This method is used to remove the business object. It interally
	 * calls the remove method within EntityManager
	 * @author A-1885
	 * @throws SystemException
	 * @throws BookingExistsException
	 * @return
	 */
	public void remove()throws SystemException,BookingExistsException{
		log.entering("Remove Product","doProcess");
		/**
		 * To Do
		 * Check if any active booking exists for the subproducts
		 */
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			entityManager.remove(this);
			for(ProductEvent productEvent : this.getProductEvents()){
				productEvent.remove();
			}
			this.getProductEvents().clear();
			for(ProductPriority prodPriority : this.getPriority()){
				prodPriority.remove();
			}
			this.getPriority().clear();
			for(ProductRestrictionCommodity productRestrictionCommodity :
				this.getRestrictionCommodity()){
				productRestrictionCommodity.remove();
			}
			this.getRestrictionCommodity().clear();
			for(ProductRestrictionCustomerGroup productRestrictionCustomerGroup :
				this.getRestrictionCustomerGroup()){
				productRestrictionCustomerGroup.remove();
			}
			this.getRestrictionCustomerGroup().clear();
			for(ProductRestrictionPaymentTerms productRestrictionPaymentTerms :
				this.getRestrictionPaymentTerms()){
				productRestrictionPaymentTerms.remove();
			}
			this.getRestrictionCustomerGroup().clear();
			for(ProductRestrictionSegment productRestrictionSegment :
				this.getRestrictionSegment()){
				productRestrictionSegment.remove();
			}
			this.getRestrictionSegment().clear();
			for(ProductRestrictionStation productRestrictionStation :
				this.getRestrictionStation()){
				productRestrictionStation.remove();
			}
			this.getRestrictionStation().clear();
			for(ProductSCC productScc : this.getProductScc()){
				productScc.remove();
			}
			this.getProductScc().clear();
			for(ProductService productService : this.getServices()){
				productService.remove();
			}
			this.getServices().clear();
			for(ProductTransportMode productTransportMode : this.getTransportMode()){
				productTransportMode.remove();
			}
			this.getTransportMode().clear();

			for(SubProduct subProduct: this.getSubProducts()){
				removeSubProduct(subProduct);
			}
			this.getSubProducts().clear();
		}catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}
		log.exiting("Remove Product","--Exit");
	}

	/**
	 * Find All Products for the specied filter criteria
	 * @author A-1883
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 */
	public static Page<ProductVO> findProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException{
		
		try {
			loglog.entering("findProducts","enter");
			return constructDAO().findProducts(productFilterVo,displayPage);
		}
		catch (PersistenceException e) {
			loglog.exiting("findProducts","exit"+e.getErrorCode());
			throw new SystemException(e.getErrorCode());
			
		}
		
	}

	/**
	 * Method to find a product using PK
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @throws SystemException
	 * @return Product
	 */
	public static Product find(String companyCode, String productCode)
	throws SystemException{
		loglog.entering("find","enter");
		Product product=null;
		ProductPK productPK = new ProductPK();
		productPK.setCompanyCode(companyCode);
		productPK.setProductCode(productCode);
		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			product=entityManager.find(Product.class,productPK);
		}
		catch (FinderException finderException) {
			throw new SystemException(finderException.getErrorCode());
		}
		return product;
	}
	/**
	 * This method will find the sub products for the given filter criteria
	 * @author A-1883
	 * @param productFilterVo
	 * @param displayPage
	 * @return collection of subproducts
	 * @throws SystemException
	 */
	public static Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage)throws SystemException{
		loglog.entering("findSubProducts","enter");
		try{
			return SubProduct.findSubProducts(productFilterVo,displayPage);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method will change the status of a sub product
	 * @author A-1883
	 * @param subProductVo
	 * @throws SystemException
	 */
	public void setSubProductStatus(SubProductVO subProductVo)
	throws SystemException{
		loglog.entering("setSubProductStatus","enter");
		SubProduct subprd = findSubProduct(subProductVo);
		subprd.setStatus(subProductVo.getStatus());
		subprd.setLastUpdateTime(subProductVo.getLastUpdateDate());
		//subprd.setLastUpdateUser(subProductVo.getLastUpdateUser());
	}

	/**
	 * This method is used to find SubProduct
	 * @author A-1883
	 * @param subProductVO
	 * @return SubProduct
	 * @throws SystemException
	 */
	private  SubProduct findSubProduct(SubProductVO subProductVO)
	throws SystemException{
		loglog.entering("findSubProduct","--enter");
		SubProduct subProductOuter=null;
		for(SubProduct subProduct : this.getSubProducts()){
			String subProductVOKey = new StringBuilder().append(subProductVO.getSubProductCode()).append(this.getProductPk().getProductCode())
					.append(this.getProductPk().getCompanyCode()).append(subProductVO.getVersionNumber()).toString();
			String subProductEntityKey = new StringBuilder().append(subProduct.getSubProductPk().getSubProductCode()).append(subProduct.getSubProductPk().getProductCode())
					.append(subProduct.getSubProductPk().getCompanyCode()).append(subProduct.getSubProductPk().getVersionNumber()).toString();
			if(Objects.equals(subProductVOKey, subProductEntityKey))
			{
				subProductOuter=subProduct;
			}
		}
		return subProductOuter;
	}

	/**
	 * Method used to validate whether the given productName exists
	 * @author A-1885
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @throws SystemException
	 */
	public static String validateProductName(String companyCode,
			String productName,LocalDate startDate,LocalDate endDate)
	throws SystemException{
		loglog.entering("validateProductName","--enter");
		try {
			return constructDAO().validateProductName
			(companyCode,productName,startDate,endDate);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * @author A-1885
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return Collection<String>
	 * @throws SystemException
	 */
	public static Collection<ProductValidationVO> validateProductForAPeriod(String companyCode,
			String productName,LocalDate startDate,LocalDate endDate)
	throws SystemException{
		loglog.entering("validateProductForAPeriod","--enter");
		try {
			return constructDAO().validateProductForAPeriod
			(companyCode,productName,startDate,endDate);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * Method for checking any duplicate product exists during creation
	 * @author A-1885
	 * @param productName
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @throws DuplicateProductExistsException
	 * @throws SystemException
	 */
	public static void  checkDuplicate(String productName,String companyCode,
			LocalDate startDate,LocalDate endDate)
	throws DuplicateProductExistsException,SystemException{
		loglog.entering("checkDuplicate","--enter");
		try{
			if(constructDAO().checkDuplicate(productName,companyCode,
					startDate,endDate))
			{
				throw new DuplicateProductExistsException();
			}
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}

	}
	/**
	 * Method for checking any duplicate during Modification
	 * @author A-1885
	 * @param productName
	 * @param companyCode
	 * @param productCode
	 * @param startDate
	 * @param endDate
	 * @throws DuplicateProductExistsException
	 * @throws SystemException
	 */
	public static void checkDuplicateForModify(String productName,
			String companyCode,String productCode,LocalDate startDate,LocalDate endDate)
	throws DuplicateProductExistsException,SystemException{
		loglog.entering("checkDuplicateForModify","--enter");
		try{
			if(constructDAO().checkDuplicateForModify(productName,companyCode,
					productCode,startDate,endDate))
			{
				throw new DuplicateProductExistsException();
			}
		}catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * Method for finding products by providing its Name
	 * @author A-1885
	 * @param companyCode
	 * @param productName
	 * @return
	 * @throws SystemException
	 */
	public static Collection<ProductValidationVO> findProductsByName
	(String companyCode,String productName)throws SystemException{
		loglog.entering("findProductsByName","--enter");
		try{
			return constructDAO().findProductsByName(companyCode,productName);
		}catch(PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * Used to fetch the details of a particular product
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @return collection of ProductLovVO
	 * @throws SystemException
	 */
	public static ProductVO findProductDetails(String companyCode,
			String productCode) throws SystemException{
		loglog.entering("findProductDetails","--enter");
		try {
			return constructDAO().findProductDetails(companyCode,productCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method returns the lov for products containing product name and description
	 * @author A-1885
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return collection of ProductLovVO
	 * @throws SystemException
	 */
	public static Page<ProductLovVO> findProductLov(ProductLovFilterVO
			productLovFilterVO,int displayPage)throws SystemException{
		loglog.entering("findProductLov","--enter");
		try{
			return constructDAO().findProductLov(productLovFilterVO,displayPage);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @param productLovFilterVO
	 * @return Collection<ProductLovVO>
	 * @throws SystemException
	 */
	public static Collection<ProductLovVO> findProductForMaster(
			ProductLovFilterVO productLovFilterVO) throws SystemException {
		loglog.entering("findProductForMaster","--enter");
		try {
			return constructDAO().findProductForMaster(productLovFilterVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Method for creating the children
	 * @author A-1885
	 * @param productVO
	 * @throws SystemException
	 */
	private void updateChildren(ProductVO productVO)throws SystemException{
		log.entering("updateChildren------------------------>","Enter");
		updateSccDelete(productVO.getProductScc());
		updateSCCInsert(productVO.getProductScc());
		if(productVO.getServices()!=null && productVO.getServices().size()>0){
			updateServiceDelete(productVO.getServices());
			updateServiceInsert(productVO.getServices());
		}
		updatePriorityDelete(productVO.getPriority());
		updatePriorityInsert(productVO.getPriority());
		updateTransportModeDelete(productVO.getTransportMode());
		updateTransportModeInsert(productVO.getTransportMode());
		if(productVO.getProductEvents() != null){
			updateEventDelete(productVO.getProductEvents());
			updateEventModify(productVO.getCompanyCode(),
					productVO.getProductCode(),productVO.getProductEvents());
			updateEventInsert(productVO.getProductEvents());
		}
		if(productVO.getRestrictionCommodity() != null){
			updateRestrictionCommodityDelete(productVO.getRestrictionCommodity());
			updateRestrictionCommodityInsert(productVO.getRestrictionCommodity());
		}
		if(productVO.getRestrictionCustomerGroup() != null){
			updateRestrictionCustomerGroupDelete(productVO.getRestrictionCustomerGroup());
			updateRestrictionCustomerGroupInsert(productVO.getRestrictionCustomerGroup());
		}
		if(productVO.getRestrictionPaymentTerms() != null){
			updateRestrictionPaymentTermsDelete(productVO.getRestrictionPaymentTerms());
			updateRestrictionPaymentTermsInsert(productVO.getRestrictionPaymentTerms());
		}
		if(productVO.getRestrictionSegment() != null){
			updateRestrictionSegmentDelete(productVO.getRestrictionSegment());
			updateRestrictionSegmentInsert(productVO.getRestrictionSegment());
			
			// Added by @author a-3351 for bug (92603) fix starts
			updateRestrictionSegmentUpdate(productVO.getRestrictionSegment()); 
		}
		if(productVO.getRestrictionStation() != null){
			updateRestrictionStationDelete(productVO.getRestrictionStation());
			updateRestrictionStationInsert(productVO.getRestrictionStation());
		}
		//log.log(Log.FINE,"imageModel.getData()::>"+productVO.getImageModel().getData());
		//log.log(Log.FINE,"imageModel.getContentType()::>"+productVO.getImageModel().getContentType());
		if(productVO.getImage() != null){
/*			log.log(Log.FINE,"Image -----DAta-------"+productVO.getImage().getData());
			log.log(Log.FINE,"Image -----DAta-------"+productVO.getImage().getContentType());*/
			if(productVO.isProductIconPresent()){
				log.log(Log.FINE, "Image isProductIconPresent---", productVO.isProductIconPresent());
				log.log(Log.FINE, "Product BusinessAlreadyExuist---", this.getProductIcon());
				ProductIcon productIconUpdate = this.getProductIcon();
				if(productIconUpdate!=null){
					productIconUpdate.update(productVO);
				}
			}
			else{
				log.log(Log.FINE, "Image isProductIconNOtPresent---", productVO.isProductIconPresent());
				setProductIcon(new ProductIcon(productVO ,this.productPk));
			}
		}
			populateProductParamters(productVO);
	
		log.exiting("updateChildren------------------------>","Exit");
	}

	/**
	 * Method for updating ProductSCC
	 * @author A-1885
	 * @param sccVOs
	 * @throws SystemException
	 * @return
	 */
	private void updateSCCInsert(Collection<ProductSCCVO> sccVOs)
	throws SystemException{
		log.entering("updateSCCInsert------------------------>","Enter");
		for (ProductSCCVO productSCCVO : sccVOs){
			if(ProductSCCVO.OPERATION_FLAG_INSERT.equals(productSCCVO.getOperationFlag())){
				log.log(Log.FINE, "New SCC going to insert is---------->",
						productSCCVO.getScc());
				this.productScc.add(new ProductSCC(this.getProductPk().
						getCompanyCode(),this.getProductPk().getProductCode(),productSCCVO));
			}
		}
		log.exiting("updateSCCInsert------------------------>","Exit");
		
	}
	/**
	 * Method for updating scc having delete operational flag
	 * @author A-1885
	 * @param sccVOs
	 * @throws SystemException
	 */
	private void updateSccDelete(Collection<ProductSCCVO> sccVOs)
	throws SystemException{
		log.entering("updateSccDelete------------------------>","Enter");
		for (ProductSCCVO productSCCVO : sccVOs){
			if(ProductSCCVO.OPERATION_FLAG_DELETE.equals(productSCCVO.getOperationFlag())){
				ProductSCC productSCC = findProductSCC(productSCCVO);
				if(productSCC != null){
					productSCC.remove();
					this.productScc.remove(productSCC);
				}
			}
		}
		log.exiting("updateSccDelete------------------------>","Exit");
	}

	/**
	 * Method for find Product Scc
	 * @author A-1885
	 * @param prodcutSCCVO
	 * @return
	 */
	private ProductSCC findProductSCC(ProductSCCVO prodcutSCCVO){
		log.entering("findProductSCC------------------------>","Enter");
		ProductSCCPK productSCCPK=null;
		ProductSCC productSccOuter=null;
		for(ProductSCC productSCC : this.getProductScc()){
			productSCCPK = new ProductSCCPK();
			productSCCPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productSCCPK.setProductCode(this.getProductPk().getProductCode());
			productSCCPK.setSccCode(prodcutSCCVO.getScc());
			if(productSCCPK.equals(productSCC.getProductSccPk())){
				productSccOuter=productSCC;
			}
		}
		log.exiting("findProductSCC------------------------>","Exit");
		return productSccOuter;
	}

	/**
	 * Method for update service having operational flag insert
	 * @author A-1885
	 * @param productServiceVOs
	 * @throws SystemException
	 */
	public void updateServiceInsert(Collection<ProductServiceVO> productServiceVOs)
	throws SystemException{
		for (ProductServiceVO productServiceVo : productServiceVOs){
			if(ProductServiceVO.OPERATION_FLAG_INSERT.equals(productServiceVo.getOperationFlag())){
				this.services.add(new ProductService(this.getProductPk().
						getCompanyCode(),this.getProductPk().getProductCode(),productServiceVo));
			}
		}
		
	}
	/**
	 * Method for updating service with operational flag delete
	 * @author A-1885
	 * @param productServiceVOs
	 * @throws SystemException
	 */
	public void updateServiceDelete(Collection<ProductServiceVO> productServiceVOs)
	throws SystemException{
		for (ProductServiceVO productServiceVo : productServiceVOs){
			if(ProductServiceVO.OPERATION_FLAG_DELETE.equals(productServiceVo.getOperationFlag())){
				ProductService productService = findProductService(productServiceVo);
				if(productService != null){
					productService.remove();
					this.services.remove(productService);
				}
			}
		}
	}
	/**
	 * Method for finding productServices
	 * @author A-1885
	 * @param prodcutServiceVO
	 * @return
	 */
	private ProductService findProductService(ProductServiceVO prodcutServiceVO){
		ProductServicePK productServicePK=null;
		ProductService productServiceOuter=null;
		for(ProductService productService : this.getServices()){
			productServicePK = new ProductServicePK();
			productServicePK.setCompanyCode(this.getProductPk().getCompanyCode());
			productServicePK.setProductCode(this.getProductPk().getProductCode());
			productServicePK.setServiceCode(prodcutServiceVO.getServiceCode());
			if(productServicePK.equals(productService.getProductServicesPk())){
				productServiceOuter=productService;
			}
		}
		return productServiceOuter;
	}
	/**
	 * Method for updating product priority with operational flag insert
	 * @author A-1885
	 * @param productPriorityVOs
	 * @throws SystemException
	 */
	private void updatePriorityInsert(Collection<ProductPriorityVO>
	productPriorityVOs)throws SystemException{
		for (ProductPriorityVO productPriorityVo : productPriorityVOs){
			if(ProductPriorityVO.OPERATION_FLAG_INSERT.equals(productPriorityVo.getOperationFlag())){
				this.priority.add(new ProductPriority(this.getProductPk().
						getCompanyCode(),this.getProductPk().getProductCode(),productPriorityVo));
			}
		}
	}
	/**
	 * Method for updating product priority with operational flag delete
	 * @author A-1885
	 * @param productPriorityVOs
	 * @throws SystemException
	 */
	private void updatePriorityDelete(Collection<ProductPriorityVO>
	productPriorityVOs)throws SystemException{
		for (ProductPriorityVO productPriorityVo : productPriorityVOs){
			if(ProductPriorityVO.OPERATION_FLAG_DELETE.equals(productPriorityVo.getOperationFlag())){
				ProductPriority prodPriority = findProductPriority(productPriorityVo);
				if(prodPriority != null){
					prodPriority.remove();
					this.priority.remove(prodPriority);
				}
			}
		}
	}
	/**
	 * Method for finding productPriority
	 * @author A-1885
	 * @param productPriorityVO
	 * @return
	 */
	private ProductPriority findProductPriority(ProductPriorityVO productPriorityVO){
		ProductPriorityPK productPriorityPK=null;
		ProductPriority productPriorityOuter=null;
		for(ProductPriority prodPriority : this.getPriority()){
			productPriorityPK = new ProductPriorityPK();
			productPriorityPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productPriorityPK.setProductCode(this.getProductPk().getProductCode());
			productPriorityPK.setPriority(productPriorityVO.getPriority());
			if(productPriorityPK.equals(prodPriority.getProductPriorityPK())){
				productPriorityOuter=prodPriority;
			}
		}
		return productPriorityOuter;
	}

	/**
	 * Method for updating product transportMode with operational flag insert
	 * @author A-1885
	 * @param productTransportModeVOs
	 * @throws SystemException
	 */
	private void updateTransportModeInsert(Collection<ProductTransportModeVO>
	productTransportModeVOs)throws SystemException{
		for (ProductTransportModeVO productTransportModeVO : productTransportModeVOs){
			if(ProductTransportModeVO.OPERATION_FLAG_INSERT.equals(productTransportModeVO.getOperationFlag())){
					this.transportMode.add(new ProductTransportMode(
						this.getProductPk().getCompanyCode(),this.getProductPk().
						getProductCode(),productTransportModeVO));
			}
		}
	}
	/**
	 * Method for updating transport mode with operational flag delete
	 * @author A-1885
	 * @param productTransportModeVOs
	 * @throws SystemException
	 */
	private void updateTransportModeDelete(Collection<ProductTransportModeVO>
	productTransportModeVOs) throws SystemException{
		for (ProductTransportModeVO productTransportModeVO : productTransportModeVOs){
			if(ProductTransportModeVO.OPERATION_FLAG_DELETE.equals(productTransportModeVO.getOperationFlag())){
				ProductTransportMode productTransportMode = findProductTransportMode
				(productTransportModeVO);
				if(productTransportMode != null){
					productTransportMode.remove();
					this.transportMode.remove(productTransportMode);
				}
			}
		}
	}
	/**
	 * Method for finding product TransportMode
	 * @author A-1885
	 * @param productTransportModeVO
	 * @return ProductTransportMode
	 */
	private ProductTransportMode findProductTransportMode(ProductTransportModeVO
			productTransportModeVO){
		ProductTransportModePK productTransportModePK=null;
		ProductTransportMode productTransportModeOuter=null;
		for(ProductTransportMode productTransportMode: this.getTransportMode()){
			productTransportModePK = new ProductTransportModePK();
			productTransportModePK.setCompanyCode(this.getProductPk().getCompanyCode());
			productTransportModePK.setProductCode(this.getProductPk().getProductCode());
			productTransportModePK.setTransportMode(productTransportModeVO.getTransportMode());
			if(productTransportModePK.equals(productTransportMode.
					getProductTransportModePk())){
				productTransportModeOuter=productTransportMode;
			}
		}
		return productTransportModeOuter;
	}

	/**
	 * Method for updating restriction commodity with operational flag insert
	 * @author A-1885
	 * @param restrictionCommodityVOs
	 * @throws SystemException
	 */
	private void updateRestrictionCommodityInsert(Collection<RestrictionCommodityVO>
	restrictionCommodityVOs) throws SystemException{
		for (RestrictionCommodityVO restrictionCommodityVO : restrictionCommodityVOs){
			if(RestrictionCommodityVO.OPERATION_FLAG_INSERT.equals(restrictionCommodityVO.getOperationFlag())){
				this.restrictionCommodity.add(new ProductRestrictionCommodity
						(this.getProductPk().getCompanyCode(),this.getProductPk().
								getProductCode(),restrictionCommodityVO));
			}
		}
		
	}
	/**
	 * Method for updating restriction commodity with operational flag delete
	 * @author A-1885
	 * @param restrictionCommodityVOs
	 * @throws SystemException
	 */
	private void updateRestrictionCommodityDelete(Collection<RestrictionCommodityVO>
	restrictionCommodityVOs) throws SystemException{
		for (RestrictionCommodityVO restrictionCommodityVO : restrictionCommodityVOs){
			if(RestrictionCommodityVO.OPERATION_FLAG_DELETE.equals(restrictionCommodityVO.getOperationFlag())){
				ProductRestrictionCommodity productRestrictionCommodity =
					findProductRestrictionCommodity(restrictionCommodityVO);
				if(productRestrictionCommodity != null){
					productRestrictionCommodity.remove();
					this.restrictionCommodity.remove(productRestrictionCommodity);
				}
			}
		}
		
	}
	/**
	 * Method for finding product Restriction commodities
	 * @author A-1885
	 * @param restrictionCommodityVO
	 * @return ProductRestrictionCommodity
	 */
	private ProductRestrictionCommodity findProductRestrictionCommodity
	(RestrictionCommodityVO restrictionCommodityVO){
		ProductRestrictionCommodityPK productRestrictionCommodityPK=null;
		ProductRestrictionCommodity restrictionCommodityOuter=null;
		for(ProductRestrictionCommodity productRestrictionCommodity:
			this.getRestrictionCommodity()){
			productRestrictionCommodityPK = new ProductRestrictionCommodityPK();
			productRestrictionCommodityPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productRestrictionCommodityPK.setProductCode(this.getProductPk().getProductCode());
			productRestrictionCommodityPK.setCommodityCode(restrictionCommodityVO.getCommodity());
			if(productRestrictionCommodityPK.equals(productRestrictionCommodity
					.getProductRestrictionCommodityPK())){
				restrictionCommodityOuter=productRestrictionCommodity;
			}
		}
		return restrictionCommodityOuter;
	}

	/**
	 * Method for updating restriction customer group having operational flag delete
	 * @author A-1885
	 * @param restrictionCustomerGroupVOs
	 * @throws SystemException
	 */
	private void updateRestrictionCustomerGroupInsert
	(Collection<RestrictionCustomerGroupVO> restrictionCustomerGroupVOs)
	throws SystemException{
		for (RestrictionCustomerGroupVO restrictionCustomerGroupVO :
			restrictionCustomerGroupVOs){
			if(RestrictionCustomerGroupVO.OPERATION_FLAG_INSERT.equals(restrictionCustomerGroupVO.getOperationFlag())){
					this.restrictionCustomerGroup.add(new ProductRestrictionCustomerGroup
						(this.getProductPk().getCompanyCode(),this.getProductPk().
								getProductCode(),restrictionCustomerGroupVO));
			}
		}
		
	}

	/**
	 * Method for updating restriction customer group having operational flag insert
	 * @author A-1885
	 * @param restrictionCustomerGroupVOs
	 * @throws SystemException
	 */
	private void updateRestrictionCustomerGroupDelete
	(Collection<RestrictionCustomerGroupVO> restrictionCustomerGroupVOs)
	throws SystemException{
		for (RestrictionCustomerGroupVO restrictionCustomerGroupVO :
			restrictionCustomerGroupVOs){
			if(RestrictionCustomerGroupVO.OPERATION_FLAG_DELETE.equals(restrictionCustomerGroupVO.getOperationFlag())){
				ProductRestrictionCustomerGroup productRestrictionCustomerGroup =
					findProductRestrictionCustomerGroup(restrictionCustomerGroupVO);
				if(productRestrictionCustomerGroup != null){
					productRestrictionCustomerGroup.remove();
					this.restrictionCustomerGroup.remove
					(productRestrictionCustomerGroup);
				}
			}
		}
		
	}
	/**
	 * Method for finding restriction customer group
	 * @author A-1885
	 * @param restrictionCustomerGroupVO
	 * @return ProductRestrictionCustomerGroup
	 */
	private ProductRestrictionCustomerGroup findProductRestrictionCustomerGroup
	(RestrictionCustomerGroupVO restrictionCustomerGroupVO){
		ProductRestrictionCustomerGroupPK productRestrictionCustomerGroupPK=null;
		ProductRestrictionCustomerGroup restrictionCustomerGroupOuter=null;
		for(ProductRestrictionCustomerGroup productRestrictionCustomerGroup:
			this.getRestrictionCustomerGroup()){
			productRestrictionCustomerGroupPK = new ProductRestrictionCustomerGroupPK();
			productRestrictionCustomerGroupPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productRestrictionCustomerGroupPK.setProductCode(this.getProductPk().getProductCode());
			productRestrictionCustomerGroupPK.setCustomerGroup(
				restrictionCustomerGroupVO.getCustomerGroup());
			if(productRestrictionCustomerGroupPK.equals
					(productRestrictionCustomerGroup.getProductRestrictionCustomerGroupPK())){
				restrictionCustomerGroupOuter=productRestrictionCustomerGroup;
			}
		}
		return restrictionCustomerGroupOuter;
	}
	/**
	 * Method for updating restriction payment terms with operational flag insert
	 * @author A-1885
	 * @param restrictionPaymentTermsVOs
	 * @throws SystemException
	 */
	private void updateRestrictionPaymentTermsInsert
	(Collection<RestrictionPaymentTermsVO> restrictionPaymentTermsVOs)
	throws SystemException {
		for (RestrictionPaymentTermsVO restrictionPaymentTermsVO :
			restrictionPaymentTermsVOs){
			if(RestrictionPaymentTermsVO.OPERATION_FLAG_INSERT.equals(restrictionPaymentTermsVO.getOperationFlag())){
					this.restrictionPaymentTerms.add(new
						ProductRestrictionPaymentTerms(this.getProductPk().
								getCompanyCode(),this.getProductPk().getProductCode(),
								restrictionPaymentTermsVO));
			}
		}
		
	}

	/**
	 * Method for updating restriction payment terms with operational flag delete
	 * @author A-1885
	 * @param restrictionPaymentTermsVOs
	 * @throws SystemException
	 */
	private void updateRestrictionPaymentTermsDelete
	(Collection<RestrictionPaymentTermsVO> restrictionPaymentTermsVOs)
	throws SystemException {
		for (RestrictionPaymentTermsVO restrictionPaymentTermsVO :
			restrictionPaymentTermsVOs){
			if(RestrictionPaymentTermsVO.OPERATION_FLAG_DELETE.equals(restrictionPaymentTermsVO.getOperationFlag())){
				ProductRestrictionPaymentTerms productRestrictionPaymentTerms =
					findProductRestrictionPaymentTerms(restrictionPaymentTermsVO);
				if(productRestrictionPaymentTerms != null){
					productRestrictionPaymentTerms.remove();
					this.restrictionPaymentTerms.remove(productRestrictionPaymentTerms);
				}
			}
		}
		
	}
	/**
	 * Method for finding restriction payment terms
	 * @author A-1885
	 * @param restrictionPaymentTermsVO
	 * @return ProductRestrictionPaymentTerms
	 */
	private ProductRestrictionPaymentTerms findProductRestrictionPaymentTerms
	(RestrictionPaymentTermsVO restrictionPaymentTermsVO){
		ProductRestrictionPaymentTermsPK productRestrictionPaymentTermsPK=null;
		ProductRestrictionPaymentTerms restrictionPaymentTermsOuter=null;
		for(ProductRestrictionPaymentTerms productRestrictionPaymentTerms:
			this.getRestrictionPaymentTerms()){
			productRestrictionPaymentTermsPK = new ProductRestrictionPaymentTermsPK();
			productRestrictionPaymentTermsPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productRestrictionPaymentTermsPK.setProductCode(this.getProductPk().getProductCode());
			productRestrictionPaymentTermsPK.setPaymentTerm(restrictionPaymentTermsVO.getPaymentTerm());
			if(productRestrictionPaymentTermsPK.equals(productRestrictionPaymentTerms.
					getProductRestrictionPaymentTermsPK())){
				restrictionPaymentTermsOuter=productRestrictionPaymentTerms;
			}
		}
		return restrictionPaymentTermsOuter;
	}

	/**
	 * Method for updating restriction segment with operational flag insert
	 * @author A-1885
	 * @param restrictionSegmentVOs
	 * @throws SystemException
	 */
	private void updateRestrictionSegmentInsert(Collection<RestrictionSegmentVO>
	restrictionSegmentVOs) throws SystemException{
		for (RestrictionSegmentVO restrictionSegmentVO : restrictionSegmentVOs){
			if(RestrictionSegmentVO.OPERATION_FLAG_INSERT.equals(restrictionSegmentVO.getOperationFlag())){
					this.restrictionSegment.add(new ProductRestrictionSegment
						(this.getProductPk().getCompanyCode(),this.getProductPk().
								getProductCode(),restrictionSegmentVO));
			}
		}
		
	}
	/**
	 * Method for updating restriction segment with operational flag delete
	 * @author A-1885
	 * @param restrictionSegmentVOs
	 * @throws SystemException
	 */
	private void updateRestrictionSegmentDelete(Collection<RestrictionSegmentVO>
	restrictionSegmentVOs) throws SystemException{
		for (RestrictionSegmentVO restrictionSegmentVO : restrictionSegmentVOs){
			if(RestrictionSegmentVO.OPERATION_FLAG_DELETE.equals(restrictionSegmentVO.getOperationFlag())){
				ProductRestrictionSegment productRestrictionSegment =
					findProductRestrictionSegment(restrictionSegmentVO);
				if(productRestrictionSegment != null){
					productRestrictionSegment.remove();
					this.restrictionSegment.remove(productRestrictionSegment);
				}
			}
		}
		
	}
	/**
	 * Method for finding product restriction segment
	 * @author A-1885
	 * @param restrictionSegmentVO
	 * @return ProductRestrictionSegment
	 */
	private ProductRestrictionSegment findProductRestrictionSegment
	(RestrictionSegmentVO restrictionSegmentVO){
		ProductRestrictionSegmentPK productRestrictionSegmentPK=null;
		ProductRestrictionSegment restrictionSegmentOuter=null;
		for(ProductRestrictionSegment productRestrictionSegment: this.
				getRestrictionSegment()){
			productRestrictionSegmentPK = new ProductRestrictionSegmentPK();
			productRestrictionSegmentPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productRestrictionSegmentPK.setProductCode(this.getProductPk().getProductCode());
			productRestrictionSegmentPK.setOrigin(restrictionSegmentVO.getOrigin());
			productRestrictionSegmentPK.setDestination(restrictionSegmentVO.getDestination());
			if(productRestrictionSegmentPK.equals(productRestrictionSegment.
					getProductRestrictionSegmentPK())){
				restrictionSegmentOuter=productRestrictionSegment;
			}
		}
		return restrictionSegmentOuter;
	}
	/**
	 * Method for updating product restriction station
	 * @author A-1885
	 * @param restrictionStationVOs
	 * @throws SystemException
	 */
	private void updateRestrictionStationInsert(Collection<RestrictionStationVO>
	restrictionStationVOs)throws SystemException{
		for (RestrictionStationVO restrictionStationVO : restrictionStationVOs){
			if(RestrictionStationVO.OPERATION_FLAG_INSERT.equals(restrictionStationVO.getOperationFlag())){
					this.restrictionStation.add(new ProductRestrictionStation
						(this.getProductPk().getCompanyCode(),this.getProductPk().
								getProductCode(),restrictionStationVO));
			}
		}
		
	}
	/**
	 * Method for updating product restriction station with operational flag delete
	 * @author A-1885
	 * @param restrictionStationVOs
	 * @throws SystemException
	 */
	private void updateRestrictionStationDelete(Collection<RestrictionStationVO>
	restrictionStationVOs) throws SystemException{
		for (RestrictionStationVO restrictionStationVO : restrictionStationVOs){
			if(RestrictionStationVO.OPERATION_FLAG_DELETE.equals(restrictionStationVO.getOperationFlag())){
				ProductRestrictionStation productRestrictionStation =
					findProductRestrictionStation(restrictionStationVO);
				if(productRestrictionStation != null){
					productRestrictionStation.remove();
					this.restrictionStation.remove(productRestrictionStation);
				}
			}
		}
		
	}
	/**
	 * Method for finding restriction station
	 * @author A-1885
	 * @param restrictionStationVO
	 * @return ProductRestrictionStation
	 */
	private ProductRestrictionStation findProductRestrictionStation
	(RestrictionStationVO restrictionStationVO){
		ProductRestrictionStationPK productRestrictionStationPK=null;
		ProductRestrictionStation restrictionStationOuter =null;
		for(ProductRestrictionStation productRestrictionStation :
			this.getRestrictionStation()){
			productRestrictionStationPK = new ProductRestrictionStationPK();
			productRestrictionStationPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productRestrictionStationPK.setProductCode(this.getProductPk().getProductCode());
			productRestrictionStationPK.setStationCode(restrictionStationVO.getStation());
			if(restrictionStationVO.getIsOrigin())
			{
				productRestrictionStationPK.setIsOrigin("Y");
			}
			else{
				productRestrictionStationPK.setIsOrigin("N");
			}
			if(productRestrictionStationPK.equals(productRestrictionStation.
					getProductRestrictionStationPk())){
				restrictionStationOuter=productRestrictionStation;
			}
		}
		return restrictionStationOuter;
	}

	/**
	 * Method for updating product Event with operational flag insert
	 * @author A-1885
	 * @param productEventVOs
	 * @throws SystemException
	 */
	private void updateEventInsert(Collection<ProductEventVO> productEventVOs)
	throws SystemException{
		for (ProductEventVO productEventVO : productEventVOs){
			if(ProductEventVO.OPERATION_FLAG_INSERT.equals(productEventVO.getOperationFlag())){
				new ProductEvent(this.getProductPk().getCompanyCode(),
						this.getProductPk().getProductCode(),productEventVO);
				log.log(Log.FINE, "inside update event insert", productEventVO.getOperationFlag());
			}
		}
		
	}

	/**
	 * Method for updating product event with operational flag delete
	 * @author A-1885
	 * @param productEventVOs
	 * @throws SystemException
	 */
	private void updateEventDelete(Collection<ProductEventVO> productEventVOs)
	throws SystemException{
		for (ProductEventVO productEventVO : productEventVOs){
			if(ProductEventVO.OPERATION_FLAG_DELETE.equals(productEventVO.getOperationFlag())){
				ProductEvent productEvent = findEvent(productEventVO);
				if(productEvent != null){
					productEvent.remove();
					this.productEvents.remove(productEvent);
				}
			}
		}
		
	}
	/**
	 * Method for modifying product event
	 * @author A-1885
	 * @param companyCode
	 * @param productCode
	 * @param productEventVOs
	 * @throws SystemException
	 */
	private void updateEventModify(String companyCode,String productCode,
			Collection<ProductEventVO> productEventVOs) throws SystemException{
		for (ProductEventVO productEventVO : productEventVOs){
			if(ProductEventVO.OPERATION_FLAG_UPDATE.equals(productEventVO.getOperationFlag())){
				ProductEvent productEvent = findEvent(productEventVO);
				if(productEvent != null){
					productEvent.update(companyCode,productCode,productEventVO);
				}
			}
		}
		
	}
	/**
	 * Method for finding product event
	 * @author A-1885
	 * @param productEventVO
	 * @return ProductEvent
	 */
	private ProductEvent findEvent(ProductEventVO productEventVO){
		ProductEventPK productEventPK=null;
		ProductEvent productEventOuter=null;
		for(ProductEvent productEvent : this.getProductEvents()){
			productEventPK = new ProductEventPK();
			productEventPK.setCompanyCode(this.getProductPk().getCompanyCode());
			productEventPK.setProductCode(this.getProductPk().getProductCode());
			productEventPK.setEventCode(productEventVO.getEventCode());
			productEventPK.setEventType(productEventVO.getEventType());
			if(productEventPK.equals(productEvent.getProductEventPk())){
				productEventOuter=productEvent;
			}
		}
		return productEventOuter;
	}

	/**
	 * Method for populating subproducts from product vo
	 * as a combination of transportmode, scc,priority
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateSubProducts(ProductVO productVo)throws SystemException{
log.entering("populateSubProducts","Enter");
		SubProductVO subProductVo = new SubProductVO();
		Collection<AuditFieldVO> auditFields;
		Collection<ProductSCCVO> productSccCollection = productVo.getProductScc();
		Collection<ProductTransportModeVO> productTransportModeCollection =
			productVo.getTransportMode();
		Collection<ProductPriorityVO> productPriorityCollection =
			productVo.getPriority();
		for(ProductSCCVO productSccVo : productSccCollection){
			for(ProductTransportModeVO productTransportModeVo :
				productTransportModeCollection){
				for(ProductPriorityVO productPrioritVo : productPriorityCollection){
					subProductVo.setCompanyCode(this.getProductPk().getCompanyCode());
					subProductVo.setProductCode(this.getProductPk().getProductCode());
					subProductVo.setVersionNumber(ProductVO.VERSIONNO);
					subProductVo.setAdditionalRestrictions
					(productVo.getAdditionalRestrictions());
					subProductVo.setHandlingInfo(productVo.getHandlingInfo());
					subProductVo.setRemarks(productVo.getRemarks());
					subProductVo.setStatus(ProductVO.NEWSTATUS);
					/*subProductVo.setMaximumVolume(productVo.getMaximumVolume());
					 subProductVo.setMaximumWeight(productVo.getMaximumWeight());
					 subProductVo.setMinimumVolume(productVo.getMinimumVolume());
					 subProductVo.setMinimumWeight(productVo.getMinimumWeight());
					 */
					subProductVo.setMaximumVolumeDisplay(productVo.getMaximumVolumeDisplay());
					subProductVo.setMinimumVolumeDisplay(productVo.getMinimumVolumeDisplay());
					subProductVo.setMaximumWeightDisplay(productVo.getMaximumWeightDisplay());
					subProductVo.setMinimumWeightDisplay(productVo.getMinimumWeightDisplay());
					//Added as part of ICRD-232462 begins
					subProductVo.setMaximumDimensionDisplay(productVo.getMaximumDimensionDisplay());
					subProductVo.setMinimumDimensionDisplay(productVo.getMinimumDimensionDisplay());
					subProductVo.setDisplayDimensionCode(productVo.getDisplayDimensionCode());
					//Added as part of ICRD-232462 ends
					subProductVo.setVolumeUnit(productVo.getDisplayVolumeCode());
					subProductVo.setWeightUnit(productVo.getDisplayWeightCode());
					//To be reviewed some conversion from weight and volume to display weight and volume
					subProductVo.setAlreadyModifed(false);
					subProductVo.setLastUpdateDate(productVo.getLastUpdateDate());
					subProductVo.setLastUpdateUser(productVo.getLastUpdateUser());
					subProductVo.setProductPriority(productPrioritVo.getPriority());
					subProductVo.setProductTransportMode
					(productTransportModeVo.getTransportMode());
					subProductVo.setProductScc(productSccVo.getScc());
					subProductVo.setRestrictionCommodity
					(productVo.getRestrictionCommodity());
					subProductVo.setRestrictionCustomerGroup
					(productVo.getRestrictionCustomerGroup());
					subProductVo.setRestrictionPaymentTerms
					(productVo.getRestrictionPaymentTerms());
					subProductVo.setRestrictionSegment
					(productVo.getRestrictionSegment());
					subProductVo.setRestrictionStation
					(productVo.getRestrictionStation());
					subProductVo.setServices(productVo.getServices());
					subProductVo.setEvents(productVo.getProductEvents());
					SubProduct subProduct=new SubProduct(subProductVo);
						log.log(Log.INFO,"Calling Audit for populating AuditFields");
					auditSubProducttDetails("SubProduct Created",AuditAction.
							INSERT.toString(),AuditVO.CREATE_ACTION,subProduct);

				}

			}
		}

	}
	/**
	 * This method is used to audit subproduct details.
	 * The performAudit method of AuditUtils sends a audit update message to be
	 * handled asynchronously
	 * @param auditFields
	 * @param subProductVO
	 * @param productName
	 * @param actionCode
	 * @throws SystemException
	 * @return
	 */
	/*private void auditSubProducttDetails(Collection<AuditFieldVO> auditFields,
			SubProductVO subProductVO,String productName,String actionCode)
	throws SystemException {
		log.entering("auditSubProducttDetails","Going to construct ProductAuditVO for calling performAudit");
		StringBuilder additionalInfo = new StringBuilder();
		ProductsDefaultsAuditVO productsDefaultsAuditVO=
			new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
					ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
		productsDefaultsAuditVO.setActionCode(actionCode);
		productsDefaultsAuditVO.setCompanyCode(subProductVO.getCompanyCode());
		productsDefaultsAuditVO.setPrdName(productName);
		productsDefaultsAuditVO.setStartDate(subProductVO.getStartDate());
		productsDefaultsAuditVO.setEndDate(subProductVO.getEndDate());
		productsDefaultsAuditVO.setProductCode(subProductVO.getProductCode());
		productsDefaultsAuditVO.setAuditFields(auditFields);
//		constructing additional info with field name, description, old value and new value
		for(AuditFieldVO auditField : auditFields) {
			additionalInfo.append(" Field Name: ").append(auditField.getFieldName()).append(" Field Description: ")
			.append(auditField.getDescription()).append(" Old Value: ").append(auditField.getOldValue())
			.append(" New Value: ").append(auditField.getNewValue());
		}
		productsDefaultsAuditVO.setAdditionalInfo(additionalInfo.toString());
		if(ProductsDefaultsAuditVO.SUBPRODUCT_INSERT_ACTIONCODE.equals(actionCode)){
		productsDefaultsAuditVO.setAuditRemarks(AuditAction.INSERT.toString());
		}
		else if(ProductsDefaultsAuditVO.SUBPRODUCT_UPDATE_ACTIONCODE.equals(actionCode)){
			productsDefaultsAuditVO.setAuditRemarks(AuditAction.UPDATE.toString());
		}
		log.log(Log.INFO,"^^^^^^^Calling AuditUtils:performAudit^^^^^^^");
		AuditUtils.performAudit(productsDefaultsAuditVO);
	   ///*The below code is for testing MDB with the current setup which needs to be removed after testing
    	com.ibsplc.icargo.business.products.audit.ProductsAuditMDB productAuditMdb = new com.ibsplc.icargo.business.products.audit.ProductsAuditMDB();
    	Collection<ProductsDefaultsAuditVO> ary = new ArrayList<ProductsDefaultsAuditVO>();
    	ary.add(productsDefaultsAuditVO);
    	productAuditMdb.audit(ary);
    	//The above code is for testing MDB with the current setup which needs to be removed after testing
    	log.exiting("auditSubProducttDetails","Called AuditUtils:performAudit with productsDefaultsAuditVO");

	}*/
	/**
	 * method will return object of type ProductDefaultsDAO
	 * @author A-1885
	 * @return ProductDefaultsDAO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static ProductDefaultsDAO constructDAO()
	throws SystemException, PersistenceException {
		loglog.entering("constructDAO", "Enter");
		EntityManager em = PersistenceController.getEntityManager();
		return ProductDefaultsDAO.class.cast
		(em.getQueryDAO(ProductDefaultsPersistenceConstants.MODULE_NAME));

	}
	/**
	 * Method for populating the product PK
	 * @author A-1885
	 * @param productVo
	 */
	private void populatePk(ProductVO productVo){
		log.entering("Product populate pk","doProcess");
		ProductPK productpk = new ProductPK();
		productpk.setCompanyCode(productVo.getCompanyCode());
		this.productPk = productpk;
		log.exiting("Product poulate pk", "doProcess");
	}
	/**
	 * Method for populating other attributes of product
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateAttributes(ProductVO productVo)throws SystemException{
		log.entering("Product populate attributes","doProcess");
		setProductName(productVo.getProductName().trim());
		setDescription(productVo.getDescription());
		setDetailedDescription(productVo.getDetailedDescription());
		setHandlingInfo(productVo.getHandlingInfo());
		if(productVo.isBookingMandatory()){
			setBookingIndicator(ProductVO.FLAG_YES);
		}else{
			setBookingIndicator(ProductVO.FLAG_NO);
		}
		setRemarks(productVo.getRemarks());
		setStatus(ProductVO.NEWSTATUS);
		setMaximumDisplayVolume(productVo.getMaximumVolumeDisplay());
		setMinimumDisplayVolume(productVo.getMinimumVolumeDisplay());
		setMaximumDisplayWeight(productVo.getMaximumWeightDisplay());
		setMinimumDisplayWeight(productVo.getMinimumWeightDisplay());
		//Added as part of CR ICRD-232462
		setMinimumDisplayDimension(productVo.getMinimumDimensionDisplay());
		setMaximumDisplayDimension(productVo.getMaximumDimensionDisplay());
		setDisplayDimensionCode(productVo.getDisplayDimensionCode());
		setDisplayVolumeCode(productVo.getDisplayVolumeCode());
		setDisplayWeightCode(productVo.getDisplayWeightCode());
		convertionUnit();
		setLastUpdateUser(productVo.getLastUpdateUser());
		setAdditionalRestrictions(productVo.getAdditionalRestrictions());
		//Added as part of CR ICRD-237928
		setOverrideCapacity(productVo.getOverrideCapacity());
		if(productVo.getIsRateDefined()){
			setIsRateDefined(ProductVO.FLAG_YES);
		}
		else{
			setIsRateDefined(ProductVO.FLAG_NO);
		}
		/*if(productVo.isCoolProduct()){
			setCoolIndicator(ProductVO.FLAG_YES);
		}else
			setCoolIndicator(ProductVO.FLAG_NO);*/
		setProductCategory(productVo.getProductCategory());//Added for ICRD-166985 by A-5117
		//Added for ICRD-352832
		if(productVo.getIsDisplayInPortal()){
			setDisplayInPortal(ProductVO.FLAG_YES);
		}else{
			setDisplayInPortal(ProductVO.FLAG_NO);
		}
		setProductPriority(productVo.getPrdPriority());
		setDocumentType(productVo.getDocumentType());
		setDocumentSubType(productVo.getDocumentSubType());
		setProactiveMilestoneEnabled(productVo.getProactiveMilestoneEnabled());

		setStartDate(productVo.getStartDate());
		setEndDate(productVo.getEndDate());
		setLastUpdateTime(productVo.getLastUpdateDate().toCalendar());
		log.exiting("Product poulate Attribute", "doProcess");
	}

	/**
	 * Method for populating the children of product
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateChildren(ProductVO productVo) throws SystemException{
/*		log.log(Log.FINE,"the image to be saved-----------)))"+
				productVo.getIconForProduct());*/
		populateProductIcon(productVo); 
		populatePriority(productVo);
		populateSCC(productVo);
		if(productVo.getServices()!=null){
			populateService(productVo);
		}
		if(productVo.getProductEvents()!=null){
			populateEvent(productVo);
		}
		populateTransportMode(productVo);
		populateRestrictions(productVo);
		populateProductParamters(productVo);//Added for ICRD-259237 by A-7740
	}
	/**
	 * 	Method		:	Product.populateProductParamters
	 *	Added by 	:	A-7740 on 04-Oct-2018
	 * 	Used for 	:
	 *	Parameters	:	@param productVo 
	 *	Return type	: 	void
	 
	 */
	private void populateProductParamters(ProductVO productVo)throws SystemException {
 		log.entering("----------->>>>>Populating populateProductParamters","doProcess");
		
		if (productVo.getProductParamters()!= null && productVo.getProductParamters().size()>0) {
			log.log(Log.FINE,"ENTERING PRODUCT PARAMETERS!!");
			Set<ProductParamters> prdPars = new HashSet<ProductParamters>();
			for(ProductParamterVO parVO:productVo.getProductParamters()){
				if(ProductParamterVO.OPERATION_FLAG_DELETE.equals(parVO.getOperationalFlag())){
					ProductParamters productPars = new  ProductParamters();
					try{ productPars = ProductParamters.find(productVo,parVO);
					}catch(FinderException e){
						log.log(Log.FINE, "Finder Exception");
					
					      throw new SystemException(e.getErrorCode(), e);

						
					}
					if(productPars!=null){
						getProductParamters().remove(productPars);
						productPars.remove();
					}
				}else if(ProductParamterVO.OPERATION_FLAG_UPDATE.equals(parVO.getOperationalFlag())){
					log.log(Log.INFO, " inside update..***");
					ProductParamters prdtParameters;
					try {
						prdtParameters = ProductParamters.find(productVo,parVO);
						prdtParameters.remove();
					} catch (FinderException e) {
						log.log(Log.FINE, "Finder Exception");
						 ProductParamters prodParams = new ProductParamters(productVo, parVO);
						 prdPars.add(prodParams);
					}
					
					 ProductParamters prodParams = new ProductParamters(productVo, parVO);
					 prdPars.add(prodParams);
				}else if(ProductParamterVO.OPERATION_FLAG_INSERT.equals(parVO.getOperationalFlag())){
					ProductParamters productPars = new ProductParamters(productVo, parVO);
					prdPars.add(productPars);
				}

			}
				this.setProductParamters(prdPars);
		}
		
		log.exiting("----------->>>>>>>>Populating ProductParamters","doProcess");
	}
	/**
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateProductIcon(ProductVO productVo)throws SystemException{
		log.log(Log.FINE,"populateProductIcon");
		ImageModel imageModel =	productVo.getImage();
		if(imageModel!=null){
			if(imageModel.getData()!=null && imageModel.getContentType()!=null){
				setProductIcon(
				new ProductIcon(productVo,this.productPk));
			}
		}
	}


	/**
	 * Method for populating the product transport mode
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateTransportMode(ProductVO productVo)
	throws SystemException{
		log.entering("----------->>>>>>>>>populating TransportMode","doProcess");
		Collection<ProductTransportModeVO> obtainTransportMode=
			productVo.getTransportMode();
		for ( ProductTransportModeVO productTransportModeVo :
			obtainTransportMode) {
			ProductTransportMode productTransportMode =
				new ProductTransportMode(productPk.getCompanyCode(),
						productPk.getProductCode(),productTransportModeVo);
			if (getTransportMode() == null ) {
				transportMode = new HashSet<ProductTransportMode>();
			}
			this.transportMode.add(productTransportMode);
		}
		log.exiting("----------->>>>>>>>Populating TransportMode","doProcess");
	}
	/**
	 * Method for populating the product prioirty
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populatePriority(ProductVO productVo) throws SystemException{
		log.entering("----------->>>>>>>>>>>>>Populating Priority","doProcess");
		Collection<ProductPriorityVO> obtainPriority=productVo.getPriority();
		for ( ProductPriorityVO productPriorityVo : obtainPriority ) {
			ProductPriority prodPriority = new ProductPriority
			(productVo.getCompanyCode(), productPk.getProductCode(),productPriorityVo);
			if (getPriority() == null ) {
				priority= new HashSet<ProductPriority>(); 
			}
			this.priority.add(prodPriority);
		}
		log.exiting("----------->>>>>>>>>>>>>>>>>>Populating Priority","doProcess");
	}

	/**
	 * Method for populating product scc
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	 private void populateSCC(ProductVO productVo) throws SystemException{
		log.entering("----------->>>>>>>>>>>>>>>>>>Populating SCC","doProcess");
		Collection<ProductSCCVO> obtainScc=productVo.getProductScc();
		for ( ProductSCCVO productSccVo : obtainScc) {
			ProductSCC productSccGet= new ProductSCC(productVo.getCompanyCode()
					, productPk.getProductCode(),productSccVo);
			if (getProductScc() == null ) {
				productScc= new HashSet<ProductSCC>();
			}
			this.productScc.add(productSccGet);
		}
		log.exiting("----------->>>>>>>>>>>>>>>>>>Populating SCC","doProcess");
	}

	/**
	 * Method for populating the product services
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateService(ProductVO productVo) throws SystemException{
		log.entering("----------->>>>>>>>>>>>>>>>>>Populating Service","doProcess");
		Collection<ProductServiceVO> obtainService=productVo.getServices();
		for ( ProductServiceVO productServiceVo : obtainService ) {
			ProductService productService = new ProductService
			(productVo.getCompanyCode(), productPk.getProductCode(),productServiceVo);
			if (getServices() == null ) {
				services = new HashSet<ProductService>();
			}
			this.services.add(productService);
		}
		log.exiting("----------->>>>>>>>>>>>>>>>>>Populating Service","doProcess");
	}
	/**
	 * Method for populating the product event
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateEvent(ProductVO productVo) throws SystemException{
		log.entering("----------->>>>>>>>>>>>>>>>>>Populating Events","doProcess");
		Collection<ProductEventVO> obtainEvent=productVo.getProductEvents();
		for ( ProductEventVO productEventVo :obtainEvent ) {
			ProductEvent productEvent = new ProductEvent
			(productVo.getCompanyCode(), productPk.getProductCode(),productEventVo);
			if (getProductEvents() == null ) {
				productEvents = new HashSet<ProductEvent>();
			}
			this.productEvents.add(productEvent);
		}
		log.exiting("----------->>>>>>>>>>>>>>>>>>Populating Events","doProcess");
	}

	/**
	 * Method for populating product restrictions
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictions(ProductVO productVo) throws SystemException{
		if(productVo.getRestrictionCommodity()!=null){
			populateRestrictionCommodity(productVo);
		}
		if(productVo.getRestrictionSegment()!=null){
			populateRestrictionSegment(productVo);
		}
		if(productVo.getRestrictionStation()!=null){
			populateRestrictionStation(productVo);
		}
		if(productVo.getRestrictionCustomerGroup()!=null){
			populateRestrictionCustomerGroup(productVo);
		}
		if(productVo.getRestrictionPaymentTerms()!=null){
			populateRestrictionPaymentTerms(productVo);
		}


	}
	/**
	 * Method for populating the product restriction commodity
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictionCommodity(ProductVO productVo)
	throws SystemException{
		log.entering("----------->>>>>populating RestrictionCommodity","doProcess");
		Collection<RestrictionCommodityVO> obtainRestrictionCommodity=
			productVo.getRestrictionCommodity();
		for(RestrictionCommodityVO restrictionCommodityVo :
			obtainRestrictionCommodity){
			ProductRestrictionCommodity productRestrictionCommodity=new
			ProductRestrictionCommodity(productVo.getCompanyCode(),productPk.
					getProductCode(),restrictionCommodityVo);
			if(getRestrictionCommodity()==null){
				restrictionCommodity=new HashSet<ProductRestrictionCommodity>();
			}
			this.restrictionCommodity.add(productRestrictionCommodity);
		}
		log.exiting("----------->>>>>Populating RestrictionCommodity","doProcess");
	}
	/**
	 * Method for populating the product restriction segments
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictionSegment(ProductVO productVo)
	throws SystemException{
		log.entering("----------->>>>>Populating RestrictionSegment","doProcess");
		Collection<RestrictionSegmentVO> obtainRestrictionSegment=productVo.
		getRestrictionSegment();
		for(RestrictionSegmentVO restrictionSegmentVo : obtainRestrictionSegment){
			ProductRestrictionSegment productRestrictionSegment=new
			ProductRestrictionSegment(productVo.getCompanyCode(),productPk.
					getProductCode(),restrictionSegmentVo);
			if(getRestrictionSegment()==null){
				restrictionSegment=new HashSet<ProductRestrictionSegment>();
			}
			this.restrictionSegment.add(productRestrictionSegment);
		}
		log.exiting("----------->>>>>>>Populating RestrictionSegment","doProcess");
	}
	/**
	 * Method for populating the product restriction station
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictionStation(ProductVO productVo) throws
	SystemException{
		log.entering("----------->>>>>>>Populating RestrictionStation","doProcess");
		Collection<RestrictionStationVO> obtainRestrictionStation=productVo.
		getRestrictionStation();
		for(RestrictionStationVO restrictionStationVo : obtainRestrictionStation){
			ProductRestrictionStation productRestrictionStation=new
			ProductRestrictionStation(productVo.getCompanyCode(),productPk.
					getProductCode(),restrictionStationVo);
			if(getRestrictionStation()==null){
				restrictionStation=new HashSet<ProductRestrictionStation>();
			}
			this.restrictionStation.add(productRestrictionStation);
		}
		log.exiting("----------->>>>>>>>Populating RestrictionStation","doProcess");
	}

	/**
	 * Method for populating the product restricted Customer group
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictionCustomerGroup(ProductVO productVo)
	throws SystemException{
		log.entering("----------->>>>>>>>opulating CustomerGroup","doProcess");
		Collection<RestrictionCustomerGroupVO> obtainRestrictionCustomerGroup=
			productVo.getRestrictionCustomerGroup();
		for(RestrictionCustomerGroupVO restrictionCustomerGroupVo :
			obtainRestrictionCustomerGroup ){
			ProductRestrictionCustomerGroup productRestrictionCustomerGroup=
				new ProductRestrictionCustomerGroup(productVo.getCompanyCode()
						,productPk.getProductCode(),restrictionCustomerGroupVo);
			if(getRestrictionCustomerGroup()==null){
				restrictionCustomerGroup=new
				HashSet<ProductRestrictionCustomerGroup>();
			}
			this.restrictionCustomerGroup.add(productRestrictionCustomerGroup);
		}
		log.exiting("----------->>>>>>>>Populating CustomerGroup","doProcess");
	}
	/**
	 * Method for populating the product restricted payment terms
	 * @author A-1885
	 * @param productVo
	 * @throws SystemException
	 */
	private void populateRestrictionPaymentTerms(ProductVO productVo)
	throws SystemException{
		log.entering("----------->>>>>Populating RestrictionPaymentTerms","doProcess");
		Collection<RestrictionPaymentTermsVO> obtainRestrictionPaymentTerms=
			productVo.getRestrictionPaymentTerms();
		for(RestrictionPaymentTermsVO restrictionPaymentTermsVo :
			obtainRestrictionPaymentTerms){
			ProductRestrictionPaymentTerms  productRestrictionPaymentTerms=
				new ProductRestrictionPaymentTerms(productVo.getCompanyCode(),
						productPk.getProductCode(),restrictionPaymentTermsVo);
			if(getRestrictionPaymentTerms()==null){
				restrictionPaymentTerms=new
				HashSet<ProductRestrictionPaymentTerms>();
			}
			this.restrictionPaymentTerms.add(productRestrictionPaymentTerms);
		}
		log.exiting("----------->>>>>Populating RestrictionPaymentTerms","doProcess");
	}
	/**
	 * Method for updating the subProduct
	 * @author A-1885
	 * @param productVO
	 * @return
	 * @throws SystemException
	 * @throws BookingExistsException
	 */
	private Collection<SubProductVO> updateSubProduct(ProductVO productVO)
	throws SystemException,BookingExistsException{
		log.entering("Update SubProduct-------------------------->","Enter");
		Collection<ProductTransportModeVO> insertTransportMode=
			new ArrayList<ProductTransportModeVO>();
		Collection<ProductTransportModeVO> updateTransportMode=
				new ArrayList<ProductTransportModeVO>();
		Collection<String> deleteTransportMode=new ArrayList<String>();
		Collection<ProductPriorityVO> insertPriority=
			new ArrayList<ProductPriorityVO>();
		Collection<String> deletePriority=new ArrayList<String>();
		Collection<ProductPriorityVO> updatePriority=new ArrayList<ProductPriorityVO>();
		Collection<ProductSCCVO> insertSCC=new ArrayList<ProductSCCVO>();
		Collection<String> deleteSCC=new ArrayList<String>();
		Collection<ProductSCCVO> updateSCC=new ArrayList<ProductSCCVO>();
		for(ProductTransportModeVO productTransportModeVo:productVO.getTransportMode()){
			if(ProductTransportModeVO.OPERATION_FLAG_INSERT.equals(productTransportModeVo.getOperationFlag())||
					productTransportModeVo.getOperationFlag()==null){
				insertTransportMode.add(productTransportModeVo);
			}
			else if(ProductTransportModeVO.OPERATION_FLAG_DELETE.equals(productTransportModeVo.getOperationFlag())){
				deleteTransportMode.add(productTransportModeVo.getTransportMode());
			}
			if(productTransportModeVo.getOperationFlag()==null){
				updateTransportMode.add(productTransportModeVo);
			}
		}
		for(ProductSCCVO productSccVo:productVO.getProductScc()){
			if(ProductSCCVO.OPERATION_FLAG_INSERT.equals(productSccVo.getOperationFlag())||
					productSccVo.getOperationFlag()==null){
				insertSCC.add(productSccVo);
			}
			else if(ProductSCCVO.OPERATION_FLAG_DELETE.equals(productSccVo.getOperationFlag())){
				deleteSCC.add(productSccVo.getScc());
			}
			if(productSccVo.getOperationFlag()==null){
				updateSCC.add(productSccVo);
			}
		}
		for(ProductPriorityVO productPriorityVo:productVO.getPriority()){
			if(ProductPriorityVO.OPERATION_FLAG_INSERT.equals(productPriorityVo.getOperationFlag())||
					productPriorityVo.getOperationFlag()==null){
				insertPriority.add(productPriorityVo);
			}
			else if(ProductPriorityVO.OPERATION_FLAG_DELETE.equals(productPriorityVo.getOperationFlag())){
				deletePriority.add(productPriorityVo.getPriority());
			}
			if(productPriorityVo.getOperationFlag()==null){
				updatePriority.add(productPriorityVo);
			}
		}
		deleteSubProduct(deleteSCC,deletePriority,deleteTransportMode);
		Collection<SubProductVO> modifySubProductList = modifySubProduct(productVO);
		insertSubProduct(insertSCC,insertPriority,insertTransportMode,productVO);
		updateSubProduct(updateSCC,updatePriority,updateTransportMode,productVO);//Added By A-8130 for ICRD-315262
		log.exiting("Update subProduct----------------------->>>>","Exit");
		return modifySubProductList;
	}
	/**
	 * This method will modify the subproducts and will return a collection of
	 * subproductVo having flag isAlreadymodified 'Y' and having active booking
	 * @author A-1885
	 * @param productVO
	 * @return Collection
	 * @throws SystemException
	 */
	private Collection<SubProductVO> modifySubProduct(ProductVO productVO)
	throws SystemException{
		log.entering("Modify SubProduct","Enter");
		ArrayList<SubProductVO> unModifiedSubproducts = new ArrayList<SubProductVO>();

		// Added by @author a-3351 for bug (92603) fix starts
		// This is arred to remove the duplicates
		if(productVO.getRestrictionSegment() != null
				&& productVO.getRestrictionSegment().size() > 0) {
			Collection<RestrictionSegmentVO> restrictionSegmentVOs = new ArrayList<RestrictionSegmentVO>();
			for (RestrictionSegmentVO tempRestrictionSegmentVO : productVO
					.getRestrictionSegment()) {
				if (!restrictionSegmentVOs.contains(tempRestrictionSegmentVO)) {
					restrictionSegmentVOs.add(tempRestrictionSegmentVO);
				}
			}
			productVO.setRestrictionSegment(restrictionSegmentVOs);
		}
		// Added by @author a-3351 for bug (92603) fix ends
		
		/*for(SubProduct subProduct: this.getSubProducts()){
		  if(SubProductVO.FLAG_YES.equals(subProduct.getLatestVersion())){
			if(SubProductVO.FLAG_YES.equals(subProduct.getIsAlreadyModifed())){

				unModifiedSubproducts.add(populateSubProductVO(productVO,
						subProduct,ProductVO.REASON_MANUALY_UPDATED));
			}//To be reviewed
			//Call the proxy method to check if bokking exists, if booking is there add to the collection
			else{
				SubProductVO subProductVO=populateSubProductVO(productVO,subProduct,ProductVO.NULLSTRING);
				
				ProductsDefaultsAuditVO auditVO=
					new ProductsDefaultsAuditVO(ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,
							ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
							ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
				auditVO = (ProductsDefaultsAuditVO)
				AuditUtils.populateAuditDetails(auditVO,subProduct,false);
				//subProduct.setLastUpdateTime(productVO.getLastUpdateDate().toCalendar());
				subProduct.update(subProductVO);
				auditVO = (ProductsDefaultsAuditVO)
				AuditUtils.populateAuditDetails(auditVO,subProduct,false);
				auditSubProducttDetailsUpdate("Modify SubProduct",AuditAction.UPDATE
						.toString(),AuditVO.UPDATE_ACTION,auditVO);
				log.log(Log.INFO,"Updation over");

			}
		  }
		}*/
		log.exiting("Modify SubProduct","doProcess");
		return unModifiedSubproducts;
	}
	/**
	 * Method for populating the subProducts
	 * @author A-1885
	 * @param productVO
	 * @param subProduct
	 * @param reason
	 * @return
	 */
	private SubProductVO populateSubProductVO(ProductVO productVO,SubProduct
			subProduct,String reason){
		log.entering("populateSubProductVO------>>>","Enter");
		SubProductVO subProductVO = new SubProductVO();
		subProductVO.setProductPriority(subProduct.getProductPriority());
		subProductVO.setProductScc(subProduct.getProductScc());
		subProductVO.setProductTransportMode(subProduct.getProductTransportMode());
		subProductVO.setStatus(subProduct.getStatus());
		subProductVO.setSubProductCode(subProduct.getSubProductPk().getSubProductCode());
		subProductVO.setVersionNumber(subProduct.getSubProductPk().getVersionNumber());
		subProductVO.setProductCode(productVO.getProductCode());
		subProductVO.setCompanyCode(productVO.getCompanyCode());
		subProductVO.setHandlingInfo(productVO.getHandlingInfo());
		subProductVO.setAdditionalRestrictions(productVO.getAdditionalRestrictions());
		subProductVO.setVolumeUnit(productVO.getDisplayVolumeCode());
		subProductVO.setWeightUnit(productVO.getDisplayWeightCode());
		subProductVO.setMaximumVolume(productVO.getMaximumVolume());
		subProductVO.setMinimumVolume(productVO.getMinimumVolume());
		subProductVO.setMaximumVolumeDisplay(productVO.getMaximumVolumeDisplay());
		subProductVO.setMaximumWeightDisplay(productVO.getMaximumWeightDisplay());
		subProductVO.setMaximumWeight(productVO.getMaximumWeight());
		subProductVO.setMinimumWeight(productVO.getMinimumWeight());
		subProductVO.setMinimumVolumeDisplay(productVO.getMinimumVolumeDisplay());
		subProductVO.setMinimumWeightDisplay(productVO.getMinimumVolumeDisplay());
		subProductVO.setLastUpdateDate(productVO.getLastUpdateDate());
		subProductVO.setLastUpdateUser(productVO.getLastUpdateUser());
		subProductVO.setRemarks(productVO.getRemarks());
		subProductVO.setServices(productVO.getServices());
		subProductVO.setReason(reason);
		subProductVO.setRestrictionCommodity(productVO.getRestrictionCommodity());
		subProductVO.setRestrictionCustomerGroup(productVO.getRestrictionCustomerGroup());
		subProductVO.setRestrictionPaymentTerms(productVO.getRestrictionPaymentTerms());
		subProductVO.setRestrictionSegment(productVO.getRestrictionSegment());
		subProductVO.setRestrictionStation(productVO.getRestrictionStation());
		subProductVO.setEvents(productVO.getProductEvents());
		//Added as part of ICRD-232462
		subProductVO.setDisplayDimensionCode(productVO.getDisplayDimensionCode());
		subProductVO.setMaximumDimension(productVO.getMaximumDimension());
		subProductVO.setMinimumDimension(productVO.getMinimumDimension());
		subProductVO.setMaximumDimensionDisplay(productVO.getMaximumDimensionDisplay());
		subProductVO.setMinimumDimensionDisplay(productVO.getMinimumDimensionDisplay());
		return subProductVO;
	}

	/** Method for inserting new subproducts
	 * @author A-1885
	 * @param insertSCC
	 * @param insertPriority
	 * @param insertTransportMode
	 * @param productVO
	 * @throws SystemException
	 */
	private void insertSubProduct(Collection<ProductSCCVO> insertSCC,
			Collection<ProductPriorityVO> insertPriority,
			Collection<ProductTransportModeVO> insertTransportMode,ProductVO productVO)
	throws SystemException{
		SubProductVO subProductVo = new SubProductVO();
		Collection<AuditFieldVO> auditFields;
		String sccFlag="";
		String transportModeFlag="";
		String priorityFlag="";
		log.entering("insertSubProduct of modify product------>>>","Enter");
		for(ProductSCCVO insertScc : insertSCC){
			sccFlag=insertScc.getOperationFlag();
			for(ProductPriorityVO insertPriorityStr : insertPriority){
				priorityFlag=insertPriorityStr.getOperationFlag();
				for(ProductTransportModeVO insertTransportModeStr : insertTransportMode){
					transportModeFlag=insertTransportModeStr.getOperationFlag();
					if(sccFlag!=null || priorityFlag!=null || transportModeFlag!=null){
						log.log(Log.FINE,
								"Inside update subProduct creating SCC",
								insertScc.getScc());
						log.log(Log.FINE,
								"Inside update subProduct creating Priority",
								insertPriorityStr.getPriority());
						log
								.log(
										Log.FINE,
										"Inside update subProduct creating Transport mode",
										insertTransportModeStr.getTransportMode());
						subProductVo.setCompanyCode(this.getProductPk().getCompanyCode());
						subProductVo.setProductCode(this.getProductPk().getProductCode());
						subProductVo.setVersionNumber(ProductVO.VERSIONNO);
						log
								.log(
										Log.FINE,
										"Inside update subProduct creating productCode ******>>>>>>",
										subProductVo.getProductCode());
						subProductVo.setAdditionalRestrictions(productVO.
								getAdditionalRestrictions());
						subProductVo.setHandlingInfo(productVO.getHandlingInfo());
						subProductVo.setRemarks(productVO.getRemarks());
						subProductVo.setStatus(ProductVO.NEWSTATUS);
						subProductVo.setMaximumVolumeDisplay(productVO.getMaximumVolumeDisplay());
						subProductVo.setMinimumVolumeDisplay(productVO.getMinimumVolumeDisplay());
						subProductVo.setVolumeUnit(productVO.getDisplayVolumeCode());
						subProductVo.setMaximumWeightDisplay(productVO.getMaximumWeightDisplay());
						subProductVo.setMinimumWeightDisplay(productVO.getMinimumWeightDisplay());
						//Added as part of ICRD-232462
						subProductVo.setDisplayDimensionCode(productVO.getDisplayDimensionCode());
						subProductVo.setMinimumDimensionDisplay(productVO.getMinimumDimensionDisplay());
						subProductVo.setMaximumDimensionDisplay(productVO.getMaximumDimensionDisplay());
						subProductVo.setWeightUnit(productVO.getDisplayWeightCode());
						subProductVo.setAlreadyModifed(false);
						subProductVo.setLastUpdateUser(productVO.getLastUpdateUser());
						subProductVo.setLastUpdateDate(productVO.getLastUpdateDate());
						subProductVo.setProductPriority(insertPriorityStr.getPriority());
						subProductVo.setProductTransportMode(insertTransportModeStr.
								getTransportMode());
						subProductVo.setProductScc(insertScc.getScc());
						subProductVo.setRestrictionCommodity(productVO.
								getRestrictionCommodity());
						subProductVo.setRestrictionCustomerGroup(productVO.
								getRestrictionCustomerGroup());
						subProductVo.setRestrictionPaymentTerms(productVO.
								getRestrictionPaymentTerms());
						subProductVo.setRestrictionSegment(productVO.
								getRestrictionSegment());
						subProductVo.setRestrictionStation(productVO.
								getRestrictionStation());
						subProductVo.setServices(productVO.getServices());
						subProductVo.setEvents(productVO.getProductEvents());
						//String productName=productVO.getProductName();

							SubProduct subProduct=new SubProduct(subProductVo);
							auditSubProducttDetails("SubProduct Created",
									AuditAction.INSERT.toString(),
									AuditVO.CREATE_ACTION,subProduct);

					}

				}
			}
		}
		log.exiting("insertSubProduct of modify product------>>>","doProcess");
	}
	/** Method for deleting a subproduct
	 * @author A-1885
	 * @param deleteSCC
	 * @param deletePriority
	 * @param deleteTransportMode
	 * @throws SystemException
	 * @throws BookingExistsException
	 * @return
	 */
	private void deleteSubProduct(Collection<String> deleteSCC,Collection<String>
	deletePriority,Collection<String> deleteTransportMode)
	throws SystemException,BookingExistsException{
		log.entering("Delete SubProduct","Enter");
		ArrayList<SubProduct> subProductList= new ArrayList<SubProduct>();

		for(SubProduct subProduct : this.getSubProducts()){
			if(deleteSCC.contains(subProduct.getProductScc())){
				subProductList.add(subProduct);
				removeSubProduct(subProduct);
			}else if(deleteTransportMode.contains(subProduct.
					getProductTransportMode())){
				subProductList.add(subProduct);
				removeSubProduct(subProduct);
			}else if(deletePriority.contains(subProduct.getProductPriority())){
				subProductList.add(subProduct);
				removeSubProduct(subProduct);
			}
		}
		deleteSubProductCollection(subProductList);
		log.exiting("Delete SubProduct","Exit");
	}
	/**
	 * Method for deleting the SubProduct Collections
	 * @author A-1885
	 * @param subProductList
	 */
	private void deleteSubProductCollection(Collection<SubProduct> subProductList){
		for(SubProduct subProduct : subProductList){
			if(this.getSubProducts().contains(subProduct)){
				this.getSubProducts().remove(subProduct);
			}
		}
	}
	/**
	 * Converting weight and volume in to system units
	 * @throws SystemException
	 * @return
	 */
	private void convertionUnit()throws SystemException{
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		if(getDisplayWeightCode()!= null && getMaximumDisplayWeight() != 0 ) {

			try{
				log.log(Log.FINE,
						"Converting Maximum Display Weight of Allt. ",
						"Rqst. to system values");
				log.log(Log.FINE, "this.getProductPk()--->", this.getProductPk().getCompanyCode());
				log.log(Log.FINE, "this.getDisplayWeightCode()--->", this.getDisplayWeightCode());
				log.log(Log.FINE, "this.getMaximumDisplayWeight()--->", this.getMaximumDisplayWeight());
				log.log(Log.FINE, "this.getMaximumDisplayWeight()--->", this.getMaximumDisplayDimension());
				setMaximumWeight(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_WEIGHT,this.getDisplayWeightCode(),
								this.getMaximumDisplayWeight()).getToValue());
			}catch(ProxyException proxyException) {
				//Added For Bug ICRD-28571 by A-5258
				if (proxyException.getErrors() != null) {
				for(ErrorVO errorVO : proxyException.getErrors()){
						if (errorVO.getErrorCode().equalsIgnoreCase(
								CONVERTION_UNIT_ERR)) {
							throw new SystemException(CONVERTION_UNIT_NOT_FOUND);
						} else {
							throw new SystemException(
									proxyException.getErrors());
						}
					}
				}
			}
		}
		if(getDisplayWeightCode()!= null && getMinimumDisplayWeight() != 0 ) {

			try{
				log.log(Log.FINE, "Converting Display minimum Weight of Allt.",
						" Rqst. to system values");
				setMinimumWeight(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_WEIGHT,this.getDisplayWeightCode(),
								this.getMinimumDisplayWeight()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO : proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		if(getDisplayVolumeCode()!= null && getMaximumDisplayVolume() != 0 ) {

			try{
				log.log(Log.FINE, "Converting Display maximum Volume of Allt.",
						" Rqst. to system values");
				setMaximumVolume(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_VOLUME,this.getDisplayVolumeCode(),
								this.getMaximumDisplayVolume()).getToValue());
			}catch(ProxyException proxyException) {
				proxyException.getMessage();
				for(ErrorVO errorVO : proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		if(getDisplayVolumeCode()!= null && getMinimumDisplayVolume() != 0 ) {

			try{
				log.log(Log.FINE, "Converting minimum Display Volume of Allt.",
						" Rqst. to system values");
				setMinimumVolume(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_VOLUME,this.getDisplayVolumeCode(),
								this.getMinimumDisplayVolume()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO : proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		//Added as part of CR ICRD-232462 begins
		if(getDisplayDimensionCode()!= null && getMaximumDisplayDimension() != 0 ) {

			try{
				log.log(Log.FINE, "Converting Display maximum Dimension of Allt.",
						" Rqst. to system values");
				setMaximumDimension(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_DIMENSION,this.getDisplayDimensionCode(),
								this.getMaximumDisplayDimension()).getToValue());
			}catch(ProxyException proxyException) {
				proxyException.getMessage();
				for(ErrorVO errorVO : proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		if(getDisplayDimensionCode()!= null && getMinimumDisplayDimension() != 0 ) {

			try{
				log.log(Log.FINE, "Converting minimum Display dimension of Allt.",
						" Rqst. to system values");
				setMinimumDimension(sharedDefaultsProxy.findSystemUnitValue
						(this.getProductPk().getCompanyCode(),UnitConversionVO.
								UNIT_TYPE_DIMENSION,this.getDisplayDimensionCode(),
								this.getMinimumDisplayDimension()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO : proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		//Added as part of CR ICRD-232462 ends
    }

	/**
	 * This method is for setting the status
	 * @author A-1885
	 * @param productVo
	 * @return
	 */
	private void checkStatus(ProductVO productVo){
			if(!(this.getProductName().equals(productVo.getProductName()))){
				setStatus(ProductVO.NEWSTATUS);
				
			}
			else if(!(this.getStartDate().equals(productVo.getStartDate().toCalendar()))){
				setStatus(ProductVO.NEWSTATUS);
				
			}
			else if(!(this.getEndDate().equals(productVo.getEndDate().toCalendar()))){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkSccStatus(productVo.getProductScc())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkPriorityStatus(productVo.getPriority())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkTransportModeStatus(productVo.getTransportMode())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkRestrictionCommodity(productVo.getRestrictionCommodity())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkServiceStatus(productVo.getServices())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkRestrictionCustomerGroup(productVo.getRestrictionCustomerGroup())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkRestrictionSegment(productVo.getRestrictionSegment())){
				setStatus(ProductVO.NEWSTATUS);
				
			}else if(checkRestrictionStation(productVo.getRestrictionStation())){
				setStatus(ProductVO.NEWSTATUS);
				
			}
		}

	/**
	 * Method for checking any SCC added or delete during update
	 * @author A-1885
	 * @param productSccVOs
	 * @return flag
	 */
	private boolean checkSccStatus(Collection<ProductSCCVO> productSccVOs){
			boolean isStatus=false;
			if(productSccVOs!=null && productSccVOs.size()>0){
				for (ProductSCCVO productSCCVO :productSccVOs ){
					if(productSCCVO.getOperationFlag()!=null){
						isStatus=true;
					}
				}
			}
			return isStatus;
		}
	/**
	 * Method for checking any prioirty added or delete during update
	 * @author A-1885
	 * @param productPriorityVOs
	 * @return flag
	 */
	private boolean checkPriorityStatus(Collection<ProductPriorityVO> productPriorityVOs){
		boolean isStatus=false;
		if(productPriorityVOs!=null && productPriorityVOs.size()>0){
			for (ProductPriorityVO productPriorityVO :productPriorityVOs ){
				if(productPriorityVO.getOperationFlag()!=null){
					isStatus=true;
				}
			}
		}
		return isStatus;

	}
	/**
	 * Method for checking any transportmode added or delete during update
	 * @author A-1885
	 * @param productTransportModeVOs
	 * @return flag
	 */
	private boolean checkTransportModeStatus(Collection<ProductTransportModeVO> productTransportModeVOs){
		boolean isStatus=false;
		if(productTransportModeVOs!=null && productTransportModeVOs.size()>0){
			for (ProductTransportModeVO productTransportModeVO :productTransportModeVOs ){
				if(productTransportModeVO.getOperationFlag()!=null){
					isStatus=true;
				}
			}
		}
		return isStatus;
	}
	/**
	 * Method for checking any restricted Commodities added or delete during update
	 * @author A-1885
	 * @param restrictionCommodityVOs
	 * @return flag
	 */
	private boolean checkRestrictionCommodity(Collection<RestrictionCommodityVO> restrictionCommodityVOs){
		boolean isRestricted=false;
		for (RestrictionCommodityVO restrictionCommodityVO :restrictionCommodityVOs ){
			if(restrictionCommodityVO.getOperationFlag()!=null){
				isRestricted=true;
			}
		}
		return isRestricted;
	}
	/**
	 * Method for checking any Services added or delete during update
	 * @author A-1885
	 * @param productServiceVOs
	 * @return flag
	 */
	private boolean checkServiceStatus(Collection<ProductServiceVO> productServiceVOs){
		boolean isRestricted=false;
		if(productServiceVOs!=null && productServiceVOs.size()>0){
			for (ProductServiceVO productServiceVO :productServiceVOs ){
				if(productServiceVO.getOperationFlag()!=null){
					isRestricted=true;
				}
			}
		}
		return isRestricted;
	}
	/**
	 * Method for checking any restricted CustomerGroup added or delete during update
	 * @author A-1885
	 * @param restrictionCustomerGroupVOs
	 * @return flag
	 */
	private boolean checkRestrictionCustomerGroup(Collection<RestrictionCustomerGroupVO> restrictionCustomerGroupVOs){
		boolean isRestricted=false;
		for (RestrictionCustomerGroupVO restrictionCustomerGroupVO :restrictionCustomerGroupVOs ){
			if(restrictionCustomerGroupVO.getOperationFlag()!=null){
				isRestricted=true;
			}
		}
		return isRestricted;
	}
	/**
	 * Method for checking any restricted segment added or delete during update
	 * @author A-1885
	 * @param restrictionSegmentVOs
	 * @return flag
	 */
	private boolean checkRestrictionSegment(Collection<RestrictionSegmentVO> restrictionSegmentVOs){
		boolean isRestricted=false;
		for (RestrictionSegmentVO restrictionSegmentVO :restrictionSegmentVOs ){
			if(restrictionSegmentVO.getOperationFlag()!=null){
				isRestricted=true;
			}
		}
		return isRestricted;
	}

	/**
	 * Method for checking any restricted station added or delete during update
	 * @author A-1885
	 * @param restrictionStationVOs
	 * @return flag
	 */
	private boolean checkRestrictionStation(Collection<RestrictionStationVO> restrictionStationVOs){
		boolean isRestricted=false;
		for (RestrictionStationVO restrictionStationVO :restrictionStationVOs ){
			if(restrictionStationVO.getOperationFlag()!=null){
				isRestricted=true;
			}
		}
		return isRestricted;
	}
	/**
	 * Method for getting ProductEvent
	 * @author A-1883
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductEventVO>
	 * @throws SystemException
	 */
	public static List<ProductEventVO> findProductEvent(String companyCode, String productCode)
	throws SystemException {
		try{
			return constructDAO().findProductEvent(companyCode,productCode);
		}catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return boolean
	 * @throws SystemException
	 */
	public static boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException {
		try{
			return constructDAO().checkStationAvailability(stationAvailabilityFilterVO);
		}catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * Find image of product for the specied filter criteria
	 * @author A-1870
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws SystemException
	 */
	public static ProductVO findImage(ProductFilterVO productFilterVo)
			 throws SystemException{

		try {
			return constructDAO().findImage(productFilterVo);
		}
		catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * @author A-1885
	 * @return bookingIndicator
	 */
	@Column(name = "BKGIND")
	public String getBookingIndicator() {
		return bookingIndicator;
	}
	/**
	 * @author A-1885
	 * @param bookingIndicator
	 */
	public void setBookingIndicator(String bookingIndicator) {
		this.bookingIndicator = bookingIndicator;
	}
    /**
     * Added by A-1945
     * @param companyCode
     * @param productCodes
     * @return Collection<ProductValidationVO>
     * @throws SystemException
     */
    public static Collection<ProductValidationVO> validateProducts(
            String companyCode, Collection<String> productCodes )
            throws SystemException {
        try {
            return constructDAO().validateProducts(companyCode, productCodes);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    /**
     * @author A-1958
     * @param companyCode
     * @param products
     * @return Collection<String>
     * @throws SystemException
     */
    public static Collection<String> findSccCodesForProducts(String companyCode,
    		Collection<String> products) throws SystemException {
        try {
            return constructDAO().findSccCodesForProducts(companyCode, products);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    /**
     *
     * @param companyCode
     * @param productName
     * @param shpgDate
     * @return
     * @throws SystemException
     */
    public static String validateProduct(String companyCode, String productName,
    		LocalDate startDate, LocalDate endDate) throws SystemException {
    	try {
            return constructDAO().validateProduct(companyCode, productName,startDate, endDate );
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    
    /**
     * 	Method		:	Product.validateProductsForListing
     *	Added by 	:	A-8041 on 02-Nov-2017
     * 	Used for 	:	validateProductsForListing
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	Collection<ProductVO>
     */
    public static Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO) throws SystemException {
    	try {
            return constructDAO().validateProductsForListing(productFilterVO);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param documentType
     * @param documentSubType
     * @return
     * @throws SystemException
     */
    public static Collection<ProductStockVO> findProductsForStock(String companyCode,
    		String documentType,String documentSubType)throws SystemException{
    	try {
            return constructDAO().findProductsForStock(companyCode,documentType,
            		documentSubType);
        } catch(PersistenceException e) {
            throw new SystemException(e.getErrorCode(), e);
        }
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param productScc
     * @param productTransportMode
     * @param productPriority
     * @return
     * @throws SystemException
     */
    public static Collection<ProductEventVO> findSubproductEventsForTracking
    (String companyCode,String productCode,String productScc,String
    		productTransportMode,String productPriority)throws SystemException{
    	try {
            return constructDAO().findSubProductEventsForTracking
            (companyCode,productCode,productScc,productTransportMode,
            		productPriority);
        } catch(PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode(),
            		persistenceException);
        }
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @return
     * @throws SystemException
     */
    public static Collection<ProductStockVO> validateProductForDocType
    (String companyCode,String productCode)throws
    SystemException{
    	try {
            return constructDAO().validateProductForDocType
            (companyCode,productCode);
        } catch(PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode(),
            		persistenceException);
        }
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return
     * @throws SystemException
     */
    public static ProductLovVO findProductPkForProductName
    (String companyCode,String productName,LocalDate startDate,LocalDate endDate)
    throws SystemException{
    	try {
            return constructDAO().findProductPkForProductName
            (companyCode,productName,startDate,endDate);
        } catch(PersistenceException persistenceException) {
            throw new SystemException(persistenceException.getErrorCode(),
            		persistenceException);
        }
    }
    
    
    /**
     * @author a-3351 for bug (92603) fix
	 * Method for updating restriction segment with operational flag update
	 * @author A-3072
	 * @param restrictionSegmentVOs
	 * @throws SystemException
	 */
	/*
	 * done for 25155
	 */
	private void updateRestrictionSegmentUpdate(Collection<RestrictionSegmentVO>
	restrictionSegmentVOs) throws SystemException{
		log.entering("updateRestrictionSegmentUpdate------------------------>","doProcess");				
		for (RestrictionSegmentVO restrictionSegmentVO : restrictionSegmentVOs){
			if(RestrictionSegmentVO.OPERATION_FLAG_UPDATE.equals(restrictionSegmentVO.getOperationFlag())){
				ProductRestrictionSegment productRestrictionSegmentUpdate =
					findProductRestrictionSegment(restrictionSegmentVO);
				
				if(productRestrictionSegmentUpdate != null){
					productRestrictionSegmentUpdate.update(restrictionSegmentVO);
				}
			}
		}
		
	}
	public static Map<String,ProductVO> validateProductNames(String companyCode,
			Collection<String> productNames) throws SystemException,
			InvalidProductException{
		try{
			Map<String, ProductVO> validProductNames = new HashMap<String, ProductVO>();	 			

			Collection<ProductVO> codes = constructDAO().
			validateProductNames(companyCode, productNames);
			String err[]=new String[productNames.size()-codes.size()];
			String errCode = "";
			StringBuilder sb1 = new StringBuilder();
			Collection<String> codes1 = new ArrayList<String>();
			int index = 0;
			if (codes.size() == productNames.size()) {
				String key = null;
				for (ProductVO productVO : codes) {
					key = productVO.getProductName();
					validProductNames.put(key, productVO);
				}
			}

			else {
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();

				for(String code : productNames){
					boolean isValid = false;
					for(ProductVO productVO : codes){
						if(code.equalsIgnoreCase(productVO.getProductName())){	 							
							isValid = true;
							break;	 													
						}
					}
					if(!isValid){
						if("".equals(errCode)){
							errCode = code;
							sb1 = new StringBuilder(String.valueOf(code));
							codes1.add(code);	 							
						}else{
							if(!codes1.contains(code)){
								sb1 = sb1.append(",").append(code);
								codes1.add(code);
							}
						}	 						
					}
				}

				errCode = sb1.toString();	 				
				Object[] obj = {errCode}; 				
				ErrorVO errorVO=new ErrorVO(InvalidProductException.
						INVALID_PRODUCT,obj);

				errors.add(errorVO) ;
				InvalidProductException invalidProductException
				=new InvalidProductException();
				invalidProductException.addErrors(errors);
				throw  invalidProductException;

			}

			return validProductNames;
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
		
  /**
   * Validate product codes.
   * A-6843 for ICRD-160333
   * @param companyCode the company code
   * @param productNames the product names
   * @return the map
   * @throws SystemException the system exception
   * @throws InvalidProductException the invalid product exception
   */
  public static Map<String, ProductVO> validateProductCodes(String companyCode, Collection<String> productNames)
		    throws SystemException, InvalidProductException
		  {
		    try {
			      Map<String, ProductVO> validProductNames = new HashMap();
			      Collection<ProductVO> codes = constructDAO()
			        .validateProductNames(companyCode, productNames);
			      String key = null;
			      for (ProductVO productVO : codes) {
			        key = productVO.getProductName();
			        validProductNames.put(key, productVO);
			      }
			      return validProductNames; 
		      }catch (PersistenceException persistenceException) {
		    throw new SystemException(persistenceException.getErrorCode());
		    }
		  }
	/**
	 * 
	 * @author A-5257
	 * @param companyCode
	 * @param productNames
	 * @return
	 */
	public static Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVOs)
			throws SystemException{
		loglog.entering("findPriorityForProducts","##enter");
		try{
			return constructDAO().findPriorityForProducts(productFilterVOs);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}		
	}
	
	/**
	 * Added for ICRD_350746
	 * For checking if another product exist with the same priority
	 * @param productFilterVO
	 * @return
	 * @throws SystemException
	 */
	public static String checkIfDuplicatePrdPriorityExists(ProductVO productVO) throws SystemException{
		try{
			return constructDAO().checkIfDuplicatePrdPriorityExists(productVO);
			
		}catch(PersistenceException persistenceException){
			loglog.log(Log.SEVERE, persistenceException);
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * 
	 * @author A-5867
	 * @param ProductFilterVO
	 * @return Collection<ProductVO>
	 * @throws SystemException
	 */
	public static Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws SystemException{
		loglog.entering("findBookableProducts","##enter");
		try{
			return constructDAO().findBookableProducts(productFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	public static Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) throws SystemException{
		loglog.entering("getProductModelMapping","##enter");
		try{
			return constructDAO().getProductModelMapping(productModelMappingFilterVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	
	/**
	 * 
	 * 	Method		:	Product.findProducts
	 *	Added by 	:	A-7548 on 19-Jan-2018
	 * 	Used for 	:
	 *	Parameters	:	@param productFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public static Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws SystemException {
		loglog.entering("findProducts","##enter");
		try {
			return constructDAO().findProducts(productFilterVO);
		} catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * 	Getter for maximumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :
	 */
	@Column(name="MAXDIMRES")
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
	 * 	Getter for minimumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	@Column(name="MINDIMRES")
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
	 * 	Getter for minimumDisplayDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	@Column(name="MINDISDIMRES")
	public double getMinimumDisplayDimension() {
		return minimumDisplayDimension;
	}
	/**
	 *  @param minimumDisplayDimension the minimumDisplayDimension to set
	 * 	Setter for minimumDisplayDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMinimumDisplayDimension(double minimumDisplayDimension) {
		this.minimumDisplayDimension = minimumDisplayDimension;
	}
	/**
	 * 	Getter for maximumDisplayDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	@Column(name="MAXDISDIMRES")
	public double getMaximumDisplayDimension() {
		return maximumDisplayDimension;
	}
	/**
	 *  @param maximumDisplayDimension the maximumDisplayDimension to set
	 * 	Setter for maximumDisplayDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	public void setMaximumDisplayDimension(double maximumDisplayDimension) {
		this.maximumDisplayDimension = maximumDisplayDimension;
	}
	/**
	 * 	Getter for displayDimensionCode 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
	 */
	@Column(name="DISDIMCODRES")
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
	@OneToMany
	@JoinColumns( {
			@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD",
										insertable = false, updatable = false),
			@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD",
										insertable = false, updatable = false)})
	/**
	 * @return Returns the productParamters.
	 */
	public Set<ProductParamters> getProductParamters(){
		return this.productParamters;
	  }
	  public void setProductParamters(Set<ProductParamters> productParamters){
		this.productParamters = productParamters;
	  }
	public static Map<String,String> findProductParametersByCode(String companyCode,String ProductCode,Collection<String> parameterCodes)
			throws SystemException{
    			try{
    				return constructDAO().findProductParametersByCode(companyCode,ProductCode,parameterCodes);
    			}catch (PersistenceException e) {
    				throw new SystemException(e.getErrorCode());
    			}
    		}
	/**
	 * 
	 * 	Method		:	Product.findProductsByParameters
	 *	Added by 	:	A-8146 on 23-Nov-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param parametersAndParValue
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public static Collection<ProductVO> findProductsByParameters(String companyCode,
			 Map<String,String> parametersAndParValue)
			throws  SystemException {
		try{
			return constructDAO().findProductsByParameters(companyCode,parametersAndParValue);
		}catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
/*
	 * @author A-9025
	 * @description This method validate the products based on the name
	 */
	public static Map<String,ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames)
			throws SystemException, InvalidProductException {
		
		try {
			
			Map<String, ProductVO> validProductNames = new HashMap<String, ProductVO>();	 			
			Collection<ProductVO> codes = constructDAO().validateProductsByNames(companyCode, productNames);
			String errCode = "";
			StringBuilder errCodeSB = new StringBuilder();
			Collection<String> errCodes = new ArrayList<String>();
			
			if (codes.size() == productNames.size()) {
				String key = null;
				for (ProductVO productVO : codes) {
					key = productVO.getProductName();
					validProductNames.put(key, productVO);
				}
			} else {
				Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
				
				for(String code : productNames){
					boolean isValid = false;
					for(ProductVO productVO : codes){
						if(code.equalsIgnoreCase(productVO.getProductName())){	 							
							isValid = true;
							break;	 													
						}
					}
					if(!isValid){
						if("".equals(errCode)){
							errCode = code;
							errCodeSB = new StringBuilder(String.valueOf(code));
							errCodes.add(code);	 							
						} else {
							if(!errCodes.contains(code)){
								errCodeSB = errCodeSB.append(",").append(code);
								errCodes.add(code);
							}
						}	 						
					}
				}

				errCode = errCodeSB.toString();	 				
				Object[] objErrorCode = {errCode}; 				
				ErrorVO errorVO=new ErrorVO(InvalidProductException.INVALID_PRODUCT, objErrorCode);
				errors.add(errorVO) ;
				InvalidProductException invalidProductException = new InvalidProductException();
				invalidProductException.addErrors(errors);
				throw  invalidProductException;
			}
			return validProductNames;
		} catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
	}
	/**
	 * 
	 * 	Method		:	Product.findAllProductParameters
	 *	Added by 	:	A-8130 on 10-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductParamterVO>
	 */
	public static Collection<ProductParamterVO> findAllProductParameters(String companyCode)
			throws  SystemException {
		try{
			return constructDAO().findAllProductParameters(companyCode);
		}catch (PersistenceException e) {
			throw new SystemException(e.getErrorCode());
		}
	}
	/**
	 * 
	 * 	Method		:	Product.updateSubProduct
	 *	Added by 	:	A-8130 on 08-Feb-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductParamterVO>
	 */
	private void updateSubProduct(Collection<ProductSCCVO> updateSCC,
			Collection<ProductPriorityVO> updatePriority,
			Collection<ProductTransportModeVO> updateTransportMode,ProductVO productVO)
	throws SystemException{
		SubProductVO subProductVo = new SubProductVO();
		log.entering("updateSubProduct of modify product------>>>","Enter");
		for(ProductSCCVO updateScc : updateSCC){
			for(ProductPriorityVO updatePriorityStr : updatePriority){
				for(ProductTransportModeVO updateTransportModeStr : updateTransportMode){
						log.log(Log.FINE,
								"Inside update subProduct creating SCC",
								updateScc.getScc());
						log.log(Log.FINE,
								"Inside update subProduct creating Priority",
								updatePriorityStr.getPriority());
						log
								.log(
										Log.FINE,
										"Inside update subProduct creating Transport mode",
										updateTransportModeStr.getTransportMode());
						subProductVo.setCompanyCode(this.getProductPk().getCompanyCode());
						subProductVo.setProductCode(this.getProductPk().getProductCode());
						subProductVo.setVersionNumber(ProductVO.VERSIONNO);
						log
								.log(
										Log.FINE,
										"Inside update subProduct creating productCode ******>>>>>>",
										subProductVo.getProductCode());
						subProductVo.setAdditionalRestrictions(productVO.
								getAdditionalRestrictions());
						subProductVo.setHandlingInfo(productVO.getHandlingInfo());
						subProductVo.setRemarks(productVO.getRemarks());
						subProductVo.setMaximumVolumeDisplay(productVO.getMaximumVolumeDisplay());
						subProductVo.setMinimumVolumeDisplay(productVO.getMinimumVolumeDisplay());
						subProductVo.setVolumeUnit(productVO.getDisplayVolumeCode());
						subProductVo.setMaximumWeightDisplay(productVO.getMaximumWeightDisplay());
						subProductVo.setMinimumWeightDisplay(productVO.getMinimumWeightDisplay());
						//Added as part of ICRD-232462
						subProductVo.setDisplayDimensionCode(productVO.getDisplayDimensionCode());
						subProductVo.setMinimumDimensionDisplay(productVO.getMinimumDimensionDisplay());
						subProductVo.setMaximumDimensionDisplay(productVO.getMaximumDimensionDisplay());
						subProductVo.setWeightUnit(productVO.getDisplayWeightCode());
						subProductVo.setAlreadyModifed(true);
						subProductVo.setLastUpdateUser(productVO.getLastUpdateUser());
						subProductVo.setLastUpdateDate(productVO.getLastUpdateDate());
						subProductVo.setProductPriority(updatePriorityStr.getPriority());
						subProductVo.setProductTransportMode(updateTransportModeStr.
								getTransportMode());
						subProductVo.setProductScc(updateScc.getScc());
						subProductVo.setRestrictionCommodity(productVO.
								getRestrictionCommodity());
						subProductVo.setRestrictionCustomerGroup(productVO.
								getRestrictionCustomerGroup());
						subProductVo.setRestrictionPaymentTerms(productVO.
								getRestrictionPaymentTerms());
						subProductVo.setRestrictionSegment(productVO.
								getRestrictionSegment());
						subProductVo.setRestrictionStation(productVO.
								getRestrictionStation());
						subProductVo.setServices(productVO.getServices());
						subProductVo.setEvents(productVO.getProductEvents());
						//String productName=productVO.getProductName();
						subProductVo.setSubProductCode(subProductVo.getProductPriority()
								+subProductVo.getProductTransportMode()
								+subProductVo.getProductScc()); 
						SubProduct subProduct=findSubProduct(subProductVo);  
						subProductVo.setStatus(subProduct.getStatus());
						subProduct.updateSubProduct(subProductVo);
				}
			}
		}
		log.exiting("updateSubProduct of modify product------>>>","doProcess");
	}
}
