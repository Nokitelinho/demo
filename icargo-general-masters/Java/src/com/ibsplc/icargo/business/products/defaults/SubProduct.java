/*
 * SubProduct.java Created on Jun 27, 2005
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverrides;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.AttributeOverride;
import javax.persistence.EmbeddedId;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import com.ibsplc.icargo.business.capacity.booking.vo.BookingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.products.defaults.proxy.CapacityBookingProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.shared.defaults.unit.vo.UnitConversionVO;
import com.ibsplc.xibase.server.framework.audit.Audit;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


/**
 * @author A-1358
 *
 */
@Table(name="PRDSUBMST")
@Entity
@Staleable
public class SubProduct {


	private SubProductPK subProductPk;

	/**
	 * Fetched from Onetime. Possible values are
	 * N - New, I - Inactive, A- Active
	 */
	private String status;

	private String handlingInfo;

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
	 *  weightcode displayed to client
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
	 * Minimum volumecode displayed to client
	 */
	private String displayVolumeCode;

	/**
	 * converted value based on system level volume code
	 */
	private double minimumVolume;

	/**
	 * Maximum volume displayed to client
	 */
	private double maximumDisplayVolume;

	/**
	 * converted value based on system level volume code
	 */
	private double maximumVolume;


	private String additionalRestrictions;

	/**
	 * Transport mode associated with the subproduct
	 */
	private String productTransportMode;

	/**
	 * Priority associated with the subproduct
	 */
	private String productPriority;

	/**
	 * SCC associated with the subproduct
	 */
	private String productScc;
	/**
	 * latestVersion used to identify subproduct is latest or not
	 */
	private String latestVersion ;

	/**
	 * Commodities for which this subproduct is not applicable
	 */
	private Set<SubProductRestrictionCommodity> restrictionCommodity;

	/**
	 * Segments for which this subproduct is not applicable
	 */
	private Set<SubProductRestrictionSegment> restrictionSegment;

	/**
	 * Stations (Origin or destination) for which this subproduct is
	 * not applicable
	 */
	private Set<SubProductRestrictionStation> restrictionStation;

	/**
	 * Customer groups for which this subproduct is not applicable
	 */
	private Set<SubProductRestrictionCustomerGroup> restrictionCustomerGroup;

	/**
	 * Paymen terms for which this subproduct is not applicable
	 */
	private Set<SubProductRestrictionPaymentTerms> restrictionPaymentTerms;

	/**
	 * services for this subproduct
	 */
	private Set<SubProductService> subProductService;

	/**
	 * Event for this subproduct
	 */
	private Set<SubProductEvent> subProductEvent;

	/**
	 * This flag will be set as true if the subproduct is
	 * modified manually. If this flag is true then
	 * the product updates will not be reflected this
	 * sub product level (if airline parameter is enabled
	 * for this check).
	 */
	private String isAlreadyModifed;

	/**
	 * For optimistic locking
	 */
	private Calendar lastUpdateTime;

	/**
	 * For optimistic locking
	 */
	private String lastUpdateUser;
	//Added as part of ICRD-232462
	private double minimumDimension;
	private double maximumDimension;
	private double maximumDisplayDimension;
	private double minimumDisplayDimension;
	private String displayDimensionCode;

	private Log log = LogFactory.getLogger("SUBPRODUCT");

	/**
	 * @return Returns the subProductPk.
	 */
	@EmbeddedId
		@AttributeOverrides({
			@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
			@AttributeOverride(name="productCode", column=@Column(name="PRDCOD")),
			@AttributeOverride(name="subProductCode", column=@Column(name="SUBPRDCOD")),
			@AttributeOverride(name="versionNumber", column=@Column(name="VERNUM"))
				})
	public SubProductPK getSubProductPk() {
		return subProductPk;
	}
	/**
	 * @param subProductPk The subProductPk to set.
	 */
	public void setSubProductPk(SubProductPK subProductPk) {
		this.subProductPk = subProductPk;
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
	 * @return Returns the isAlreadyModifed.
	 */
	@Column(name="MODFLG")
	public String getIsAlreadyModifed() {
		return isAlreadyModifed;
	}

	/**
	 * @param isAlreadyModifed The isAlreadyModifed to set.
	 */
	public void setIsAlreadyModifed(String isAlreadyModifed) {
		this.isAlreadyModifed = isAlreadyModifed;
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
	 * @return Returns the productPriority.
	 */
	@Column(name="PRYCOD")
	@Audit(name="Priority")
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
	@Column(name="SCCCOD")
	@Audit(name="Scc")
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
	@Column(name="TRAMOD")
	@Audit(name="TransportMode")
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
	@Column(name="PRDRMK")
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
	 * @return Returns the status.
	 */
	@Column(name="SUBPRDSTA")
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
	 * @return Returns the latestVersion.
	 */
	@Column(name="LTSVERFLG")
	public String getLatestVersion() {
		return latestVersion;
	}
	/**
	 * @param latestVersion The latestVersion to set.
	 */
	public void setLatestVersion(String latestVersion) {
		this.latestVersion = latestVersion;
	}
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the restrictionCommodity.
		 */
		public Set<SubProductRestrictionCommodity> getRestrictionCommodity() {
		return restrictionCommodity;
	}
	/**
	 * @param restrictionCommodity The restrictionCommodity to set.
	 */
	public void setRestrictionCommodity(Set<SubProductRestrictionCommodity> restrictionCommodity) {
		this.restrictionCommodity = restrictionCommodity;
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the restrictionCustomerGroup.
		 */
		public Set<SubProductRestrictionCustomerGroup> getRestrictionCustomerGroup() {
		return restrictionCustomerGroup;
	}
	/**
	 * @param restrictionCustomerGroup The restrictionCustomerGroup to set.
	 */
	public void setRestrictionCustomerGroup(Set<SubProductRestrictionCustomerGroup> restrictionCustomerGroup) {
		this.restrictionCustomerGroup = restrictionCustomerGroup;
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the restrictionPaymentTerms.
		 */
		public Set<SubProductRestrictionPaymentTerms> getRestrictionPaymentTerms() {
		return restrictionPaymentTerms;
	}
	/**
	 * @param restrictionPaymentTerms The restrictionPaymentTerms to set.
	 */
	public void setRestrictionPaymentTerms(Set<SubProductRestrictionPaymentTerms> restrictionPaymentTerms) {
		this.restrictionPaymentTerms = restrictionPaymentTerms;
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the restrictionSegment.
		 */
		public Set<SubProductRestrictionSegment> getRestrictionSegment() {
		return restrictionSegment;
	}
	/**
	 * @param restrictionSegment The restrictionSegment to set.
	 */
	public void setRestrictionSegment(Set<SubProductRestrictionSegment> restrictionSegment) {
		this.restrictionSegment = restrictionSegment;
	}
	/**
	 * @return Returns the restrictionStation.
	 */
	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the restrictionStation.
		 */
		public Set<SubProductRestrictionStation> getRestrictionStation() {
		return restrictionStation;
	}
	/**
	 * @param restrictionStation The restrictionStation to set.
	 */
	public void setRestrictionStation(Set<SubProductRestrictionStation> restrictionStation) {
		this.restrictionStation = restrictionStation;
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the subProductService.
		 */
		public Set<SubProductService> getSubProductService() {
		return subProductService;
	}
	/**
	 * @param subProductService The subProductService to set.
	 */
	public void setSubProductService(Set<SubProductService> subProductService) {
		this.subProductService = subProductService;
	}

	@OneToMany
	@JoinColumns( {
		@JoinColumn(name = "CMPCOD", referencedColumnName = "CMPCOD", insertable=false, updatable=false),
		@JoinColumn(name = "PRDCOD", referencedColumnName = "PRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "SUBPRDCOD", referencedColumnName = "SUBPRDCOD", insertable=false, updatable=false),
		@JoinColumn(name = "VERNUM", referencedColumnName = "VERNUM", insertable=false, updatable=false)})
		/**
		 * @return Returns the subProductEvent.
		 */
		public Set<SubProductEvent> getSubProductEvent() {
		return subProductEvent;
	}
	/**
	 * @param subProductEvent The subProductEvent to set.
	 */
	public void setSubProductEvent(Set<SubProductEvent> subProductEvent) {
		this.subProductEvent = subProductEvent;
	}
	/**
	 * @return Returns the lastUpdateDate.
	 */
	
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
	 *
	 *Default Constructor
	 */
	public SubProduct(){
	}

	/**
	 * Constructor with argument.Used to create SubProduct
	 * @param subProductVo
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public SubProduct(SubProductVO subProductVo) throws SystemException{
		log.log(Log.FINE, "11subproductVO for save---------", subProductVo);
		populatePK(subProductVo);
		populateAttributes(subProductVo);
		this.setLastUpdateTime(subProductVo.getLastUpdateDate());
		this.setLatestVersion(SubProductVO.FLAG_YES);
		try{
			PersistenceController.getEntityManager().persist(this);
		}
		catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
		subProductVo.setProductCode(this.getSubProductPk().getProductCode());
		subProductVo.setSubProductCode(this.getSubProductPk().getSubProductCode());
		subProductVo.setVersionNumber(this.getSubProductPk().getVersionNumber());
		populateChildren(subProductVo);
	}

	/**
	 * This method will update a Subproduct
	 * @param subProductVo
	 * @throws SystemException
	 */
	public void update(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","update");
		log.log(Log.FINE, "subProductVo---->", subProductVo);
		if(subProductVo == null){
			return;
		}
		//To check whether booking is there for subproduct, if there is booning
		if(hasBooking(subProductVo)){
			log.log(Log.FINE,"SubProduct has Booking");
			this.latestVersion = SubProductVO.FLAG_NO;
			subProductVo.setVersionNumber(subProductVo.getVersionNumber()+1);
			new SubProduct(subProductVo);
			log.log(Log.FINE, "The Version has changed,New VERSION is:",
					subProductVo.getVersionNumber());
			return;
		}

		this.setLastUpdateTime(subProductVo.getLastUpdateDate());
		populateAttributes(subProductVo);
		if(SubProductVO.REASON_MANUALY_UPDATED.equals(subProductVo.getReason())){
			deleteAllRestrictions();
		}else{
			deleteSubProductRestrictionCommodities(subProductVo);
			deleteSubProductRestrictionCustomerGroup(subProductVo);
			deleteSubProductRestrictionPaymentTerms(subProductVo);
			deleteSubProductRestrictionSegment(subProductVo);
			deleteSubProductRestrictionStation(subProductVo);
			deleteSubProductService(subProductVo);
			deleteSubProductEvent(subProductVo);
			updateEvent(subProductVo);

			// Added by @author a-3351 for bug (92603) fix starts
			updateSubProductRestrictionSegment(subProductVo);
		}
		insertRestrictions(subProductVo);
		log.exiting("SubProduct","update");

	}
	/**
	 * This method will check if any booking exists for the subproduct.
	 * @return boolean
	 * @throws SystemException
	 */
	public boolean hasBooking(SubProductVO subProductVo) throws SystemException{
		log.entering("SubProduct","hasBooking");
		CapacityBookingProxy capacityBookingProxy = new CapacityBookingProxy();
		try{	 Collection<BookingVO> bookingVOs =
				 capacityBookingProxy.findBookingDetailsForProduct(
				 subProductVo.getCompanyCode(),subProductVo.getProductCode(),
				 subProductVo.getProductPriority(),subProductVo.getProductTransportMode(),
				 subProductVo.getProductScc());
		log.log(Log.FINE, "bookingVOs", bookingVOs);
			if(bookingVOs != null && bookingVOs.size() > 0){
				log.log(Log.FINE,"Returns True");
				return true;
			}
		}catch(ProxyException proxyException){
			for(ErrorVO errorVo : proxyException.getErrors()){
				throw new SystemException(errorVo.getErrorCode());
			}
		}
		log.log(Log.FINE,"Returns false");
		return false ;
	}
	
	public void updateSubProduct(SubProductVO subProductVo) throws SystemException{
		try {
			this.remove();
		} catch (BookingExistsException bookingExistsException) {
		}
		new SubProduct(subProductVo);
	}
	/**
	 * Used to populate the business object with values from VO
	 */
	public SubProductVO retrieveVO() {
		SubProductVO subProductVO = new SubProductVO();
		subProductVO.setCompanyCode(this.getSubProductPk().getCompanyCode());
		subProductVO.setProductCode(this.getSubProductPk().getProductCode());
		subProductVO.setSubProductCode(this.getSubProductPk().getSubProductCode());
		subProductVO.setAdditionalRestrictions(this.getAdditionalRestrictions());
		if(SubProductVO.FLAG_YES.equals(this.getIsAlreadyModifed())){
			subProductVO.setAlreadyModifed(true);
		}
		else{
			subProductVO.setAlreadyModifed(false);
		}
		subProductVO.setHandlingInfo(this.getHandlingInfo());
		subProductVO.setMaximumVolume(this.getMaximumVolume());
		subProductVO.setMaximumVolumeDisplay(this.getMaximumDisplayVolume());
		subProductVO.setMaximumWeight(this.getMaximumWeight());
		subProductVO.setMaximumWeightDisplay(this.getMaximumDisplayWeight());
		subProductVO.setMinimumVolume(this.getMinimumVolume());
		subProductVO.setMinimumVolumeDisplay(this.getMinimumDisplayVolume());
		subProductVO.setMinimumWeight(this.getMinimumWeight());
		subProductVO.setMinimumWeightDisplay(this.getMinimumDisplayWeight());
		subProductVO.setProductPriority(this.getProductPriority());
		subProductVO.setProductScc(this.getProductScc());
		subProductVO.setProductTransportMode(this.getProductTransportMode());
		subProductVO.setRemarks(this.getRemarks());
		subProductVO.setStatus(this.getStatus());
		subProductVO.setVersionNumber(this.getSubProductPk().getVersionNumber());
		//Added as part of Cr-ICRD-232462
		subProductVO.setMinimumDimension(this.minimumDimension);
		subProductVO.setMaximumDimension(this.maximumDimension);
		/*subProductVO.setMaximumDisplayVolumeCode(this.getMaximumDisplayVolumeCode());
		 subProductVO.setMaximumDisplayWeightCode(this.getMaximumDisplayWeightCode());
		 subProductVO.setMinimumDisplayVolumeCode(this.getMinimumDisplayVolumeCode());
		 subProductVO.setMinimumDisplayWeightCode(this.getMinimumDisplayWeightCode());*/
		return subProductVO;
	}

	/**
	 * This method is used to remove the business object. It interally
	 * calls the remove method within EntityManager
	 * @throws SystemException
	 * @throws BookingExistsException
	 */
	public void remove()
	throws SystemException,BookingExistsException{
		log.entering("SubProduct","remove");

		EntityManager entityManager = PersistenceController.getEntityManager();
		try{
			entityManager.remove(this);
			for(SubProductRestrictionCommodity subProductRestrictionCommodity : this.getRestrictionCommodity()){
				subProductRestrictionCommodity.remove();
			}
			for(SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup : this.getRestrictionCustomerGroup()){
				subProductRestrictionCustomerGroup.remove();
			}
			for(SubProductRestrictionPaymentTerms subProductRestrictionPaymentTerms : this.getRestrictionPaymentTerms()){
				subProductRestrictionPaymentTerms.remove();
			}
			for(SubProductRestrictionSegment subProductRestrictionSegment : this.getRestrictionSegment()){
				subProductRestrictionSegment.remove();
			}
			for(SubProductRestrictionStation subProductRestrictionStation : this.getRestrictionStation()){
				subProductRestrictionStation.remove();
			}
			for(SubProductEvent subProductEvent : this.getSubProductEvent()){
				subProductEvent.remove();
			}
			for(SubProductService subProductService : this.getSubProductService()){
				subProductService.remove();
			}
			log.exiting("SubProduct","remove");
		}
		catch(RemoveException removeException){
			throw new SystemException(removeException.getErrorCode());
		}

	}

	/**
	 * This method is used to find SubProduct using its PK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @return SubProduct
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static SubProduct find(String companyCode, String productCode,
			String subProductCode)
	throws SystemException,PersistenceException{
		try{
			SubProductPK subProductPK= new SubProductPK();
			subProductPK.setCompanyCode(companyCode);
			subProductPK.setProductCode(productCode);
			subProductPK.setSubProductCode(subProductCode);

			EntityManager entityManager = PersistenceController.getEntityManager();
			return entityManager.find(SubProduct.class,subProductPK);
		}
		catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode());
		}
	}

	/**
	 * added by A-1648
	 * This method is used to find SubProduct using its PK
	 * @param companyCode
	 * @param productCode
	 * @param subProductCode
	 * @return SubProduct
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static SubProduct findSubProductForBooking(String companyCode, String productCode,
			String subProductCode, int versionNumber)
	throws SystemException,PersistenceException{
		try{
			SubProductPK subProductPkForFind= new SubProductPK();
			subProductPkForFind.setCompanyCode(companyCode);
			subProductPkForFind.setProductCode(productCode);
			subProductPkForFind.setSubProductCode(subProductCode);
			subProductPkForFind.setVersionNumber(versionNumber);
			EntityManager entityManager = PersistenceController.getEntityManager();
			return entityManager.find(SubProduct.class,subProductPkForFind);
		}
		catch(FinderException finderException){
			throw new SystemException(finderException.getErrorCode());
		}
	}

	/**
	 * Find All SubProducts for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException,PersistenceException{
		try {
			return constructDAO().findSubProducts(productFilterVo,displayPage);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method is used to find the details of a subproduct
	 * @param subProductVO
	 * @return SubProductVO
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public static SubProductVO findSubProductDetails(SubProductVO subProductVO)
	throws SystemException,PersistenceException{
		try{
			return constructDAO().findSubProductDetails(subProductVO);
		}
		catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}

	/**
	 * This method is used for populating the restriction collections from respective
	 * Business objects
	 * @param subproductVO
	 */
	public void populateSubPrdtRestrictions(SubProductVO subproductVO){

		subproductVO.setRestrictionCommodity(new ArrayList<RestrictionCommodityVO>());
		subproductVO.setRestrictionCustomerGroup(new ArrayList<RestrictionCustomerGroupVO>());
		subproductVO.setRestrictionPaymentTerms(new ArrayList<RestrictionPaymentTermsVO>());
		subproductVO.setRestrictionSegment(new ArrayList<RestrictionSegmentVO>());
		subproductVO.setRestrictionStation(new ArrayList<RestrictionStationVO>());
		subproductVO.setEvents(new ArrayList<ProductEventVO>());

		RestrictionCommodityVO restrictionCommodityVO = new RestrictionCommodityVO();
		RestrictionCustomerGroupVO restrictionCustomerGroupVO = new RestrictionCustomerGroupVO();
		RestrictionPaymentTermsVO restrictionPaymentTermsVO = new RestrictionPaymentTermsVO();
		RestrictionSegmentVO restrictionSegmentVO = new RestrictionSegmentVO();
		RestrictionStationVO restrictionStationVO = new RestrictionStationVO();
		ProductEventVO productEventVO = new ProductEventVO();

		for (SubProductRestrictionCommodity subProductRestrictionCommodity:this.getRestrictionCommodity()){
			restrictionCommodityVO = subProductRestrictionCommodity.retrieveVO();
			restrictionCommodityVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getRestrictionCommodity().add(restrictionCommodityVO);
		}
		for (SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup:this.getRestrictionCustomerGroup()){
			restrictionCustomerGroupVO=subProductRestrictionCustomerGroup.retrieveVO();
			restrictionCustomerGroupVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getRestrictionCustomerGroup().add(restrictionCustomerGroupVO);
		}
		for (SubProductRestrictionPaymentTerms subProductRestrictionPaymentTerms:this.getRestrictionPaymentTerms()){
			restrictionPaymentTermsVO=subProductRestrictionPaymentTerms.retrieveVO();
			restrictionPaymentTermsVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getRestrictionPaymentTerms().add(subProductRestrictionPaymentTerms.retrieveVO());
		}
		for (SubProductRestrictionSegment subProductRestrictionSegment:this.getRestrictionSegment()){
			restrictionSegmentVO=subProductRestrictionSegment.retrieveVO();
			restrictionSegmentVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getRestrictionSegment().add(restrictionSegmentVO);
		}
		for (SubProductRestrictionStation subProductRestrictionStation:this.getRestrictionStation()){
			restrictionStationVO=subProductRestrictionStation.retrieveVO();
			restrictionStationVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getRestrictionStation().add(restrictionStationVO);
		}
		for (SubProductEvent subProductEvent:this.getSubProductEvent()){
			productEventVO=subProductEvent.retrieveVO();
			productEventVO.setOperationFlag(SubProductVO.OPERATION_FLAG_DELETE);
			subproductVO.getEvents().add(productEventVO);
		}
	}

	/**
	 * This method is used to populate the ProductPK from VO
	 * @param subProductVo
	 * @throws  SystemException
	 */
	private void populatePK(SubProductVO subProductVo)throws SystemException{
		log.log(Log.FINE, "in sub populate pk", subProductVo);
		SubProductPK subProductPK = new SubProductPK();
		subProductPK.setCompanyCode(subProductVo.getCompanyCode());
		subProductPK.setProductCode(subProductVo.getProductCode());
		subProductPK.setSubProductCode(subProductVo.getProductPriority()
									+subProductVo.getProductTransportMode()
									+subProductVo.getProductScc());
		subProductPK.setVersionNumber( subProductVo.getVersionNumber());
		log.log(Log.FINE, "in sub populate pk--subprdcode", subProductPK.getSubProductCode());
		this.subProductPk = subProductPK;
	}

	/**
	 * This method is used to populate the attributes of SubProduct from SubProductVO
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateAttributes(SubProductVO subProductVo)throws SystemException{
		String  modifiedFlag=SubProductVO.FLAG_NO;
		this.setRemarks(subProductVo.getRemarks());
		this.setAdditionalRestrictions(subProductVo.getAdditionalRestrictions());
		this.setHandlingInfo(subProductVo.getHandlingInfo());
		this.setDisplayWeightCode(subProductVo.getWeightUnit());
		this.setMinimumDisplayWeight(subProductVo.getMinimumWeightDisplay());
		this.setMaximumDisplayWeight(subProductVo.getMaximumWeightDisplay());
		this.setDisplayVolumeCode(subProductVo.getVolumeUnit());
		this.setMinimumDisplayVolume(subProductVo.getMinimumVolumeDisplay());
		this.setMaximumDisplayVolume(subProductVo.getMaximumVolumeDisplay());
		//Added as part of CR ICRD-232462
		setMinimumDisplayDimension(subProductVo.getMinimumDimensionDisplay());
		setMaximumDisplayDimension(subProductVo.getMaximumDimensionDisplay());
		setDisplayDimensionCode(subProductVo.getDisplayDimensionCode());
		log.log(Log.FINE,"***************************Display values*****************************");
		log.log(Log.FINE, "subProductVo.getWeightUnit()", subProductVo.getWeightUnit());
		log.log(Log.FINE, "subProductVo.getMinimumWeightDisplay()",
				subProductVo.getMinimumWeightDisplay());
		log.log(Log.FINE, "subProductVo.getMaximumWeightDisplay()",
				subProductVo.getMaximumWeightDisplay());
		log.log(Log.FINE, "subProductVo.getVolumeUnit()", subProductVo.getVolumeUnit());
		log.log(Log.FINE, "subProductVo.getMinimumVolumeDisplay()",
				subProductVo.getMinimumVolumeDisplay());
		log.log(Log.FINE, "subProductVo.getMaximumVolumeDisplay()",
				subProductVo.getMaximumVolumeDisplay());
		log.log(Log.FINE,"***************************Display values*****************************");
//		converting display weight and volume to values in system units
		SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy();
		if(getDisplayWeightCode() != null && getMinimumDisplayWeight() != 0 ) {
			try{
				log.log(Log.FINE,"Converting Display Weight to system values");
					this.setMinimumWeight(sharedDefaultsProxy.findSystemUnitValue(getSubProductPk().getCompanyCode(),
					UnitConversionVO.UNIT_TYPE_WEIGHT,getDisplayWeightCode(),getMinimumDisplayWeight()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO:proxyException.getErrors()){
					errorVO.getErrorCode();
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		if(getDisplayWeightCode() != null && getMaximumDisplayWeight() != 0 ) {

			try{
				log.log(Log.FINE,"Converting Display Weight to system values");

			this.setMaximumWeight(sharedDefaultsProxy.findSystemUnitValue(getSubProductPk().getCompanyCode(),
					UnitConversionVO.UNIT_TYPE_WEIGHT,getDisplayWeightCode(),getMaximumDisplayWeight()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO:proxyException.getErrors())
				{
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}

		if( getDisplayVolumeCode() != null && getMinimumDisplayVolume() != 0 ) {

			try{
				log.log(Log.FINE,"Converting Display Volume to system values");
					this.setMinimumVolume(sharedDefaultsProxy.findSystemUnitValue(getSubProductPk().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_VOLUME,getDisplayVolumeCode(),getMinimumDisplayVolume()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO:proxyException.getErrors())
				{
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		if( getDisplayVolumeCode() != null && getMaximumDisplayVolume() != 0 ) {

			try{
				log.log(Log.FINE,"Converting Display Volume to system values");
					this.setMaximumVolume(sharedDefaultsProxy.findSystemUnitValue(getSubProductPk().getCompanyCode(),
						UnitConversionVO.UNIT_TYPE_VOLUME,getDisplayVolumeCode(),getMaximumDisplayVolume()).getToValue());
			}catch(ProxyException proxyException) {
				for(ErrorVO errorVO:proxyException.getErrors())
				{
					throw new SystemException(errorVO.getErrorCode());
				}
			}
		}
		//Added as part of CR-ICRD-232462 begins
				if(getDisplayDimensionCode()!= null && getMaximumDisplayDimension() != 0 ) {

					try{
						log.log(Log.FINE, "Converting Display maximum Volume of Allt.",
								" Rqst. to system values");
						setMaximumDimension(sharedDefaultsProxy.findSystemUnitValue
								(this.getSubProductPk().getCompanyCode(),UnitConversionVO.
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
						log.log(Log.FINE, "Converting minimum Display Volume of Allt.",
								" Rqst. to system values");
						setMinimumDimension(sharedDefaultsProxy.findSystemUnitValue
								(this.getSubProductPk().getCompanyCode(),UnitConversionVO.
										UNIT_TYPE_DIMENSION,this.getDisplayDimensionCode(),
										this.getMinimumDisplayDimension()).getToValue());
					}catch(ProxyException proxyException) {
						for(ErrorVO errorVO : proxyException.getErrors()){
							throw new SystemException(errorVO.getErrorCode());
						}
					}
				}
		//Added as part of CR-ICRD-232462 ends		
		this.setLastUpdateUser(subProductVo.getLastUpdateUser());
		if(SubProductVO.STATUS_ACTIVE.equals(this.getStatus()) && subProductVo.isClientCall()){
			log.log(Log.FINE,"Calling checkForStatusChange()");
			checkForStatusChange(subProductVo);
		}else{
			this.setStatus(subProductVo.getStatus());
			log.log(Log.FINE, "---------------------->inside update---->>",
					subProductVo.getStatus());
		}
		if(subProductVo.getIsAlreadyModifed()){
			modifiedFlag=SubProductVO.FLAG_YES;
		}
		this.setIsAlreadyModifed(modifiedFlag);
		this.setProductPriority(subProductVo.getProductPriority());
		this.setProductScc(subProductVo.getProductScc());
		this.setProductTransportMode(subProductVo.getProductTransportMode());
		//To be reviewed Calendar conversion from Localdate from VO
		//Calendar localLastUpdateDate = Calendar.getInstance();
		//localLastUpdateDate.set(2005,07,25);
		//this.setLastUpdateTime(this.getLastUpdateTime());
		log.log(Log.FINE,"----------------------------->end of populate attribute()");
	}
	/**
	 * This method is used to change the status of subproduct to inactive
	 * when any of the restriction changes
	 * @param subProductVo
	 * @throws SystemException
	 * @return
	 */
	private void checkForStatusChange(SubProductVO subProductVo)throws SystemException {
		log.entering("checkForStatusChange","@@@@@@@@@Subproduct");
		for(RestrictionCommodityVO restrictionCommodityVO:subProductVo.getRestrictionCommodity()){
			if(restrictionCommodityVO.getOperationFlag()!=null) {
				log.log(Log.FINE,
						"!!!!restrictionCommodityVO.getOperationFlag()!",
						restrictionCommodityVO.getOperationFlag());
				log.log(Log.FINE,"Changing status of subproduct to Inactive");
				setStatus(SubProductVO.STATUS_INACTIVE);
				log.exiting("checkForStatusChange","+++++++Subproduct");
				return;
			}
		}
		for(RestrictionCustomerGroupVO restrictionCustomerGroupVO:subProductVo.getRestrictionCustomerGroup()){
			if(restrictionCustomerGroupVO.getOperationFlag()!=null){
				log.log(Log.FINE,
						"!!!!!restrictionCustomerGroupVO.getOperationFlag()",
						restrictionCustomerGroupVO.getOperationFlag());
				log.log(Log.FINE,"Changing status of subproduct to Inactive");
				setStatus(SubProductVO.STATUS_INACTIVE);
				log.exiting("checkForStatusChange","#######Subproduct");
				return;
			}
		}
		/*for(RestrictionPaymentTermsVO restrictionPaymentTermsVO:subProductVo.getRestrictionPaymentTerms()){
			if(restrictionPaymentTermsVO.getOperationFlag()!=null){
				log.log(Log.FINE,"!!!!!!!!!!!!!!"+restrictionPaymentTermsVO.getOperationFlag());
				log.log(Log.FINE,"Changing status of subproduct to Inactive");
				setStatus(SubProductVO.STATUS_INACTIVE);
				log.exiting("checkForStatusChange","$$$$$$$$Subproduct");
				return;
			}
		}*/
		for(RestrictionSegmentVO restrictionSegmentVO:subProductVo.getRestrictionSegment()){
			if(restrictionSegmentVO.getOperationFlag()!=null){
				log.log(Log.FINE,
						"!!!!!!!!!restrictionSegmentVO.getOperationFlag()",
						restrictionSegmentVO.getOperationFlag());
				log.log(Log.FINE,"Changing status of subproduct to Inactive");
				setStatus(SubProductVO.STATUS_INACTIVE);
				log.exiting("checkForStatusChange","%%%%%%%%Subproduct");
				return;
			}
		}
		for(RestrictionStationVO restrictionStationVO:subProductVo.getRestrictionStation()){
			if(restrictionStationVO.getOperationFlag()!=null){
				log.log(Log.FINE,
						"!!!!!!!!restrictionStationVO.getOperationFlag()",
						restrictionStationVO.getOperationFlag());
				log.log(Log.FINE,"Changing status of subproduct to Inactive");
				this.setStatus(SubProductVO.STATUS_INACTIVE);
				log.exiting("checkForStatusChange","^^^^^^^^Subproduct");
				return;
			}
		}
		log.exiting("checkForStatusChange","&&&&&&&&&&&&Subproduct");
	}

	/**
	 * This method is used to create child objects
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateChildren(SubProductVO subProductVo)throws SystemException{
		if(subProductVo.getRestrictionCommodity()!=null){
			populateRestrictionCommodity(subProductVo);
		}
		if(subProductVo.getRestrictionCustomerGroup()!=null){
			populateRestrictionCustomerGroup(subProductVo);
		}
		if(subProductVo.getRestrictionPaymentTerms()!=null){
			populateRestrictionPaymentTerms(subProductVo);
		}
		if(subProductVo.getRestrictionSegment()!=null){
			populateRestrictionSegment(subProductVo);
		}
		if(subProductVo.getRestrictionStation()!=null){
			populateRestrictionStation(subProductVo);
		}
		if(subProductVo.getServices()!= null){
			populateService(subProductVo);
		}
		if(subProductVo.getEvents()!= null){
			populateEvent(subProductVo);
		}
	}

	/**
	 * This method is used to create RestrictionCommodity objects from the collection of VOs
	 * and add the newly created
	 * objects to the Set restrictionCommodity
	 * @param subProductCode
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateRestrictionCommodity(SubProductVO subProductVo)
	throws SystemException{

		Collection<RestrictionCommodityVO> obtainRestrictionCommodity=subProductVo.getRestrictionCommodity();
		for(RestrictionCommodityVO restrictionCommodityVo : obtainRestrictionCommodity){
			SubProductRestrictionCommodity subProductRestrictionCommodity=
				new SubProductRestrictionCommodity(subProductVo.getCompanyCode(),subProductVo.getProductCode(),
						subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),restrictionCommodityVo);
			//To be reviewed check whether getRestrictionCommodity can be used here
			if(this.restrictionCommodity==null){
				this.restrictionCommodity=new HashSet<SubProductRestrictionCommodity>();
			}
			this.restrictionCommodity.add(subProductRestrictionCommodity);
		}
	}

	/***
	 * This method is used to create RestrictionCustomerGroup objects from the collection of VOs
	 * and add the newly created objects to the Set RestrictionCustomerGroup
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateRestrictionCustomerGroup(SubProductVO subProductVo)
	throws SystemException{
		Collection<RestrictionCustomerGroupVO> obtainRestrictionCustomerGroup=subProductVo.getRestrictionCustomerGroup();
		for(RestrictionCustomerGroupVO restrictionCustomerGroupVo : obtainRestrictionCustomerGroup ){
			SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup=
				new SubProductRestrictionCustomerGroup(subProductVo.getCompanyCode(),subProductVo.getProductCode(),
						subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),restrictionCustomerGroupVo);
			if(this.restrictionCustomerGroup==null){
				this.restrictionCustomerGroup=new HashSet<SubProductRestrictionCustomerGroup>();
			}
			this.restrictionCustomerGroup.add(subProductRestrictionCustomerGroup);
		}
	}

	/**
	 * This method is used to create RestrictionPaymentTerms objects from the collection of VOs
	 * and add the newly created objects to the Set RestrictionPaymentTerms
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateRestrictionPaymentTerms(SubProductVO subProductVo)
	throws SystemException{
		Collection<RestrictionPaymentTermsVO> obtainRestrictionPaymentTerms=subProductVo.getRestrictionPaymentTerms();
		for(RestrictionPaymentTermsVO restrictionPaymentTermsVo : obtainRestrictionPaymentTerms){
			SubProductRestrictionPaymentTerms  subProductRestrictionPaymentTerms=
				new SubProductRestrictionPaymentTerms(subProductVo.getCompanyCode(),subProductVo.getProductCode(),
						subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),restrictionPaymentTermsVo);
			if(this.restrictionPaymentTerms==null){
				this.restrictionPaymentTerms=new HashSet <SubProductRestrictionPaymentTerms>();
			}
			this.restrictionPaymentTerms.add(subProductRestrictionPaymentTerms);
		}
	}

	/**
	 * This method is used to create RestrictionSegment objects from the collection of VOs
	 * and add the newly created objects to the Set RestrictionSegment
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateRestrictionSegment(SubProductVO subProductVo)
	throws SystemException{
		Collection<RestrictionSegmentVO> obtainRestrictionSegment=subProductVo.getRestrictionSegment();
		for(RestrictionSegmentVO restrictionSegmentVo : obtainRestrictionSegment){
			// Added/Modified by @author A-3351 for bug(92603) fix
			// Added the operation flag check
			if (!RestrictionSegmentVO.OPERATION_FLAG_DELETE
					.equals(restrictionSegmentVo.getOperationFlag())) {
				SubProductRestrictionSegment subProductRestrictionSegment = new SubProductRestrictionSegment(
						subProductVo.getCompanyCode(), subProductVo
								.getProductCode(), subProductVo
								.getSubProductCode(), subProductVo
								.getVersionNumber(), restrictionSegmentVo);
				if (this.restrictionSegment == null) {
					this.restrictionSegment = new HashSet<SubProductRestrictionSegment>();
				}
				this.restrictionSegment.add(subProductRestrictionSegment);
			}
		}
	}

	/**
	 *This method is used to create RestrictionStation objects from the collection of VOs
	 * and add the newly created objects to the Set RestrictionStation
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateRestrictionStation(SubProductVO subProductVo)
	throws SystemException{
		Collection<RestrictionStationVO> obtainRestrictionStation=subProductVo.getRestrictionStation();
		for(RestrictionStationVO restrictionStationVo : obtainRestrictionStation){
			SubProductRestrictionStation subProductRestrictionStation=
				new SubProductRestrictionStation(subProductVo.getCompanyCode(),subProductVo.getProductCode(),
						subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),restrictionStationVo);
			if(this.restrictionStation==null){
				this.restrictionStation=new HashSet<SubProductRestrictionStation>();
			}
			this.restrictionStation.add(subProductRestrictionStation);
		}
	}
	/**
	 *This method is used to create SubProductService objects from the collection of VOs
	 * and add the newly created objects to the Set SubProductService
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateService(SubProductVO subProductVo)
	throws SystemException{
		Collection<ProductServiceVO> obtainService=subProductVo.getServices();
		log.log(Log.FINE, "--------PopulateServices--------", subProductVo.getServices());
		for(ProductServiceVO productServiceVO : obtainService){
			if(checkForSameServiceExist(productServiceVO)){
				SubProductService localSubProductService= new SubProductService(subProductVo.getCompanyCode(),
						subProductVo.getProductCode(),subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),
						productServiceVO);
				if(this.subProductService==null){
					this.subProductService=new HashSet<SubProductService>();
				}
				this.subProductService.add(localSubProductService);
			}
		}
	}
	/**
	 * @author a-1885
	 * @param serVo
	 * @return
	 * @throws SystemException
	 */
	private boolean checkForSameServiceExist(ProductServiceVO serVo)throws SystemException{
		boolean isNotExist = true;
		if(this.getSubProductService()!=null ){
			for(SubProductService ser :this.getSubProductService()){
				if(ser.getSubProductServicesPk().getServiceCode().equals(
					serVo.getServiceCode())){
					isNotExist = false;
				}
			}
		}
		return isNotExist;
	}

	/**
	 *This method is used to create SubProductService objects from the collection of VOs
	 * and add the newly created objects to the Set SubProductService
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void populateEvent(SubProductVO subProductVo)
	throws SystemException{
		Collection<ProductEventVO> obtainEvent=subProductVo.getEvents();
		for(ProductEventVO productEventVO : obtainEvent){
			SubProductEvent localSubProductEvent= new SubProductEvent(subProductVo.getCompanyCode(),
					subProductVo.getProductCode(),subProductVo.getSubProductCode(),subProductVo.getVersionNumber(),
					productEventVO);
			if(this.subProductEvent==null){
				this.subProductEvent=new HashSet<SubProductEvent>();
			}
			this.subProductEvent.add(localSubProductEvent);
		}
	}

	/**
	 * This method is used to insert  the restrictions of product
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertRestrictions(SubProductVO subProductVo) throws SystemException{
		log.entering("SubProduct","insertRestrictions");
		if(SubProductVO.REASON_MANUALY_UPDATED.equals(subProductVo.getReason())){
			overRideSubProductRestrictionCommodities(subProductVo);
			overRideSubProductRestrictionCustomerGroup(subProductVo);
			overRideSubProductRestrictionPaymentTerms(subProductVo);
			overRideSubProductRestrictionSegment(subProductVo);
			overRideSubProductRestrictionStation(subProductVo);
			overRideSubProductEvent(subProductVo);
		}
		insertSubProductRestrictionCommodities(subProductVo);
		insertSubProductRestrictionCustomerGroup(subProductVo);
		insertSubProductRestrictionPaymentTerms(subProductVo);
		insertSubProductRestrictionSegment(subProductVo);
		insertSubProductRestrictionStation(subProductVo);
		insertSubProductService(subProductVo);
		insertSubProductEvent(subProductVo);
		log.exiting("SubProduct","insertRestrictions");
	}

	/**
	 * This method will delete SubRestrictionCommodity based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductRestrictionCommodities(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteRestrictionCommodities");
		if(subProductVo.getRestrictionCommodity()!=null){
			for(RestrictionCommodityVO restrictionCommodityVO: subProductVo.getRestrictionCommodity()){
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(restrictionCommodityVO.getOperationFlag())){
					SubProductRestrictionCommodity subProductRestrictionCommodity=
						retrieveRestrictionCommodity(restrictionCommodityVO);
					if(subProductRestrictionCommodity != null){
						subProductRestrictionCommodity.remove();
						this.restrictionCommodity.remove(subProductRestrictionCommodity);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteRestrictionCommodities");
	}
	/**
	 * This method will insert SubRestrictionCommodity based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductRestrictionCommodities(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertRestrictionCommodities");
		if(subProductVo.getRestrictionCommodity()!=null){
			for(RestrictionCommodityVO restrictionCommodityVO: subProductVo.getRestrictionCommodity()){
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(restrictionCommodityVO.getOperationFlag())){
					new SubProductRestrictionCommodity(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionCommodityVO);
				}
			}
		}
		log.exiting("SubProduct","insertRestrictionCommodities");
	}

	/**
	 * This method will over ride commodity restriction of the subproduct
	 * with the product commodity restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductRestrictionCommodities(SubProductVO subProductVo)
		throws SystemException{
		log.entering("SubProduct","overRideSubProductRestrictionCommodities");
		if(subProductVo.getRestrictionCommodity()!=null){
			for(RestrictionCommodityVO restrictionCommodityVO: subProductVo.getRestrictionCommodity()){
					if(restrictionCommodityVO.getOperationFlag()==null){
						new SubProductRestrictionCommodity(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionCommodityVO);
				}
			}
		}
		log.exiting("SubProduct","overRideSubProductRestrictionCommodities");
	}

	/**
	 * This method will find SubProductRestrictionCommodity from the Set RestrictionCommodity
	 * @param restrictionCommodityVO
	 * @return SubProductRestrictionCommodity
	 * @throws SystemException
	 */
	private SubProductRestrictionCommodity retrieveRestrictionCommodity(RestrictionCommodityVO restrictionCommodityVO)
	throws SystemException{
		log.entering("SubProduct","findRestrictionCommodity");
		SubProductRestrictionCommodityPK subProductRestrictionCommodityPK=null;
		for(SubProductRestrictionCommodity subProductRestrictionCommodity:this.getRestrictionCommodity()){
			subProductRestrictionCommodityPK=new SubProductRestrictionCommodityPK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
					this.getSubProductPk().getVersionNumber(),restrictionCommodityVO.getCommodity());

			if(subProductRestrictionCommodityPK.equals(subProductRestrictionCommodity.
					getSubProductRestrictionCommodityPK())){
				log.exiting("SubProduct","findRestrictionCommodity");
				return subProductRestrictionCommodity;
			}
		}
		log.log(Log.FINE,"No SubProductRestrictionCommodity found");
		return null;
	}

	/**
	 * This method will delete SubRestrictionCustomerGroup based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductRestrictionCustomerGroup(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteRestrictionCustomerGroup");
		log.log(Log.FINE,"<-------------------Inside deleteSubProductRestrictionCustomerGroup-------------------------->");
		log.log(Log.FINE, "subProductVo-->RestrictionCustomerGroup--->",
				subProductVo.getRestrictionCustomerGroup());
		if(subProductVo.getRestrictionCustomerGroup()!=null){
			for(RestrictionCustomerGroupVO restrictionCustomerGroupVO: subProductVo.getRestrictionCustomerGroup()){
				log.log(Log.FINE, "RestrictionCustomerGroupVO--------------->",
						restrictionCustomerGroupVO);
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(restrictionCustomerGroupVO.getOperationFlag())){
					log.log(Log.FINE,"<-------------------Match Found in deleteRestrictionCustomerGroup---------------->");
					SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup=
						retrieveSubProductRestrictionCustomerGroup(restrictionCustomerGroupVO);
					if(subProductRestrictionCustomerGroup != null){
						log
								.log(
										Log.FINE,
										"------------------>Going to remove subProductRestrictionCustomerGroup--->",
										subProductRestrictionCustomerGroup.getSubProductRestrictionCustomerGroupPK().
										getCustomerGroup());
						subProductRestrictionCustomerGroup.remove();
						this.restrictionCustomerGroup.remove(subProductRestrictionCustomerGroup);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteRestrictionCustomerGroup");
	}
	/**
	 * This method will insert SubRestrictionCustomerGroup based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductRestrictionCustomerGroup(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertRestrictionCustomerGroup");
		log.log(Log.FINE,"<-------------------Inside insertSubProductRestrictionCustomerGroup-------------------------->");
		log.log(Log.FINE, "subProductVo-->RestrictionCustomerGroup--->",
				subProductVo.getRestrictionCustomerGroup());
		if(subProductVo.getRestrictionCustomerGroup()!=null){
			for(RestrictionCustomerGroupVO restrictionCustomerGroupVO: subProductVo.getRestrictionCustomerGroup()){
				log.log(Log.FINE, "RestrictionCustomerGroupVO--------------->",
						restrictionCustomerGroupVO);
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(restrictionCustomerGroupVO.getOperationFlag())){
					new SubProductRestrictionCustomerGroup(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionCustomerGroupVO);
				}

			}
		}
		log.exiting("SubProduct","insertRestrictionCustomerGroup");
	}

	/**
	 * This method will over ride customer group restriction of the subproduct
	 * with the product customer group restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductRestrictionCustomerGroup(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","overRideSubProductRestrictionCustomerGroup");
		log.log(Log.FINE,"<-------------------Inside insertSubProductRestrictionCustomerGroup-------------------------->");
		log.log(Log.FINE, "subProductVo-->RestrictionCustomerGroup--->",
				subProductVo.getRestrictionCustomerGroup());
		if(subProductVo.getRestrictionCustomerGroup()!=null){
			for(RestrictionCustomerGroupVO restrictionCustomerGroupVO: subProductVo.getRestrictionCustomerGroup()){
				log.log(Log.FINE, "RestrictionCustomerGroupVO--------------->",
						restrictionCustomerGroupVO);
				if(restrictionCustomerGroupVO.getOperationFlag()==null){
					new SubProductRestrictionCustomerGroup(this.getSubProductPk().getCompanyCode(),
						this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
						subProductVo.getVersionNumber(),restrictionCustomerGroupVO);
				}
			}
		}
		log.exiting("SubProduct","overRideSubProductRestrictionCustomerGroup");
	}

	/**
	 * This method will find SubProductRestrictionCustomerGroup from the Set RestrictionCustomerGroup
	 * @param restrictionCustomerGroupVO
	 * @return SubProductRestrictionCustomerGroup
	 * @throws SystemException
	 */
	private SubProductRestrictionCustomerGroup retrieveSubProductRestrictionCustomerGroup(RestrictionCustomerGroupVO
			restrictionCustomerGroupVO)throws SystemException{
		log.entering("SubProduct","findSubProductRestrictionCustomerGroup");
		SubProductRestrictionCustomerGroupPK subProductRestrictionCustomerGroupPK=null;
		for(SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup:this.getRestrictionCustomerGroup()){
			subProductRestrictionCustomerGroupPK=new SubProductRestrictionCustomerGroupPK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),this.getSubProductPk().getVersionNumber(),
					restrictionCustomerGroupVO.getCustomerGroup());
			if(subProductRestrictionCustomerGroupPK.
					equals(subProductRestrictionCustomerGroup.getSubProductRestrictionCustomerGroupPK())){
				log.exiting("SubProduct","findSubProductRestrictionCustomerGroup");
				return subProductRestrictionCustomerGroup;
			}
		}
		log.log(Log.FINE,"No SubProductRestrictionCustomerGroup found");
		return null;
	}

	/**
	 * This method will delete SubProductRestrictionPaymentTerms based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductRestrictionPaymentTerms(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteSubProductRestrictionPaymentTerms");
		log.log(Log.FINE,"<-------------------Inside deleteSubProductRestrictionPaymentTerms-------------------------->");
		log.log(Log.FINE,
				"subProductVo-->SubProductRestrictionPaymentTerms--->",
				subProductVo.getRestrictionPaymentTerms());
		if(subProductVo.getRestrictionPaymentTerms()!=null){
			for(RestrictionPaymentTermsVO restrictionPaymentTermsVO: subProductVo.getRestrictionPaymentTerms()){
				log.log(Log.FINE, "RestrictionPaymentTermsVO--------------->",
						restrictionPaymentTermsVO);
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(restrictionPaymentTermsVO.getOperationFlag())){
					log.log(Log.FINE,"<-------------------Match Found in deleteSubProductRestrictionPaymentTerms---------------->");
					SubProductRestrictionPaymentTerms subProductRestrictionPaymentTerms=
						retreiveSubProductRestrictionPaymentTerms(restrictionPaymentTermsVO);
					if(subProductRestrictionPaymentTerms != null){
						log.log(Log.FINE,"------------------>Going to remove subProductRestrictionPaymentTerms--->");
						subProductRestrictionPaymentTerms.remove();
						this.restrictionPaymentTerms.remove(subProductRestrictionPaymentTerms);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductRestrictionPaymentTerms");
	}
	/**
	 * This method will insert SubProductRestrictionPaymentTerms based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductRestrictionPaymentTerms(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertSubProductRestrictionPaymentTerms");
		log.log(Log.FINE,"<-------------------Inside insertSubProductRestrictionPaymentTerms-------------------------->");
		log.log(Log.FINE,
				"subProductVo-->SubProductRestrictionPaymentTerms--->",
				subProductVo.getRestrictionPaymentTerms());
		if(subProductVo.getRestrictionPaymentTerms()!=null){
			for(RestrictionPaymentTermsVO restrictionPaymentTermsVO: subProductVo.getRestrictionPaymentTerms()){
				log.log(Log.FINE, "RestrictionPaymentTermsVO--------------->",
						restrictionPaymentTermsVO);
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(restrictionPaymentTermsVO.getOperationFlag())){
					new SubProductRestrictionPaymentTerms(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionPaymentTermsVO);
				}

			}
		}
		log.exiting("SubProduct","insertSubProductRestrictionPaymentTerms");
	}

	/**
	 * This method will over ride paymentterms restriction of the subproduct
	 * with the product payment term restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductRestrictionPaymentTerms(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","overRideSubProductRestrictionPaymentTerms");
		if(subProductVo.getRestrictionPaymentTerms()!=null){
			for(RestrictionPaymentTermsVO restrictionPaymentTermsVO: subProductVo.getRestrictionPaymentTerms()){
				if(restrictionPaymentTermsVO.getOperationFlag()==null){
					new SubProductRestrictionPaymentTerms(this.getSubProductPk().getCompanyCode(),
						this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
						subProductVo.getVersionNumber(),restrictionPaymentTermsVO);
				}

			}
		}
		log.exiting("SubProduct","overRideSubProductRestrictionPaymentTerms");
	}

	/**
	 * This method will find SubProductRestrictionCustomerGroup from the Set RestrictionCustomerGroup
	 * @param restrictionPaymentTermsVO
	 * @return SubProductRestrictionPaymentTerms
	 * @throws SystemException
	 */
	private SubProductRestrictionPaymentTerms retreiveSubProductRestrictionPaymentTerms(RestrictionPaymentTermsVO
			restrictionPaymentTermsVO)throws SystemException{
		log.entering("SubProduct","findSubProductRestrictionPaymentTerms");
		SubProductRestrictionPaymentTermsPK subProductRestrictionPaymentTermsPK=null;
		for(SubProductRestrictionPaymentTerms subProductRestrictionPaymentTerms:this.getRestrictionPaymentTerms()){
			subProductRestrictionPaymentTermsPK=new SubProductRestrictionPaymentTermsPK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
					this.getSubProductPk().getVersionNumber(),restrictionPaymentTermsVO.getPaymentTerm());
			if(subProductRestrictionPaymentTermsPK.
					equals(subProductRestrictionPaymentTerms.getSubProductRestrictionPaymentTermsPK())){
				log.exiting("SubProduct","findSubProductRestrictionPaymentTerms");
				return subProductRestrictionPaymentTerms;
			}
		}
		log.log(Log.FINE,"No SubProductRestrictionPaymentTerms Found");
		return null;
	}
	/**
	 * This method will delete SubProductRestrictionSegment based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductRestrictionSegment(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteSubProductRestrictionSegment");
		if(subProductVo.getRestrictionSegment()!=null){		
			for(RestrictionSegmentVO restrictionSegmentVO: subProductVo.getRestrictionSegment()){
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(restrictionSegmentVO.getOperationFlag())){
					SubProductRestrictionSegment subProductRestrictionSegment=
						retrieveSubProductRestrictionSegment(restrictionSegmentVO);
					if(subProductRestrictionSegment != null){
						subProductRestrictionSegment.remove();
						this.restrictionSegment.remove(subProductRestrictionSegment);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductRestrictionSegment");
	}
	/**
	 * This method will insert SubProductRestrictionSegment based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductRestrictionSegment(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertSubProductRestrictionSegment");
		if(subProductVo.getRestrictionSegment()!=null){
			for(RestrictionSegmentVO restrictionSegmentVO: subProductVo.getRestrictionSegment()){
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(restrictionSegmentVO.getOperationFlag())){
					new SubProductRestrictionSegment(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionSegmentVO);
				}

			}
		}
		log.exiting("SubProduct","insertSubProductRestrictionSegment");
	}
	
	
	/**
	 * Added by @author a-3351 for bug (92603) fix starts
	 * Method for modifying sub product segment restriction
	 * @author A-3072
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void updateSubProductRestrictionSegment(SubProductVO subProductVo) throws SystemException{
		log.entering("SubProduct","updateSegmentModify");
		if(subProductVo.getRestrictionSegment()!=null){
			for(RestrictionSegmentVO restrictionSegmentVO: subProductVo.getRestrictionSegment()){
				if(SubProductVO.OPERATION_FLAG_UPDATE.equals(restrictionSegmentVO.getOperationFlag())){
					SubProductRestrictionSegment localSubProductRestrictionSegment=
						retrieveSubProductRestrictionSegment(restrictionSegmentVO);
					if(localSubProductRestrictionSegment != null){
						localSubProductRestrictionSegment.update(restrictionSegmentVO);
					}
				}
			}
		}
		log.exiting("SubProduct","updateSegmentModify");
	}
	
	/**
	 * This method will over ride segment restriction of the subproduct
	 * with the product segment restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductRestrictionSegment(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","overRideSubProductRestrictionSegment");
		if(subProductVo.getRestrictionSegment()!=null){
			for(RestrictionSegmentVO restrictionSegmentVO: subProductVo.getRestrictionSegment()){
				if(restrictionSegmentVO.getOperationFlag()==null){
					new SubProductRestrictionSegment(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionSegmentVO);
				}

			}
		}
		log.exiting("SubProduct","overRideSubProductRestrictionSegment");
	}

	/**
	 * This method will find SubProductRestrictionSegment from the Set RestrictionSegment
	 * @param restrictionSegmentVO
	 * @return SubProductRestrictionSegment
	 * @throws SystemException
	 */
	private SubProductRestrictionSegment retrieveSubProductRestrictionSegment(RestrictionSegmentVO
			restrictionSegmentVO)throws SystemException{
		log.entering("SubProduct","findSubProductRestrictionSegment");
		SubProductRestrictionSegmentPK subProductRestrictionSegmentPK=null;
		for(SubProductRestrictionSegment subProductRestrictionSegment:this.getRestrictionSegment()){
			subProductRestrictionSegmentPK=new SubProductRestrictionSegmentPK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),this.getSubProductPk().getVersionNumber(),
					restrictionSegmentVO.getOrigin(),restrictionSegmentVO.getDestination());
			if(subProductRestrictionSegmentPK.
					equals(subProductRestrictionSegment.getSubProductRestrictionSegmentPK())){
				return subProductRestrictionSegment;
			}
		}
		log.exiting("SubProduct","findSubProductRestrictionSegment");
		return null;
	}

	/**
	 * This method will delete SubProductRestrictionStation based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductRestrictionStation(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteSubProductRestrictionStation");
		if(subProductVo.getRestrictionStation()!=null){
			for(RestrictionStationVO restrictionStationVO: subProductVo.getRestrictionStation()){
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(restrictionStationVO.getOperationFlag())){
					SubProductRestrictionStation subProductRestrictionStation=
						retrieveRestrictionStation(restrictionStationVO);
					if(subProductRestrictionStation != null){
						subProductRestrictionStation.remove();
						this.restrictionStation.remove(subProductRestrictionStation);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductRestrictionStation");
	}
	/**
	 * This method will insert SubProductRestrictionStation based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductRestrictionStation(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertSubProductRestrictionStation");
		if(subProductVo.getRestrictionStation()!=null){
			for(RestrictionStationVO restrictionStationVO: subProductVo.getRestrictionStation()){
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(restrictionStationVO.getOperationFlag())){
					new SubProductRestrictionStation(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionStationVO);
				}

			}
		}
		log.exiting("SubProduct","insertSubProductRestrictionStation");
	}

	/**
	 * This method will over ride station restriction of the subproduct
	 * with the product station restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductRestrictionStation(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","overRideSubProductRestrictionStation");
		if(subProductVo.getRestrictionStation()!=null){
			for(RestrictionStationVO restrictionStationVO: subProductVo.getRestrictionStation()){
				if(restrictionStationVO.getOperationFlag()==null){
					new SubProductRestrictionStation(this.getSubProductPk().getCompanyCode(),
							this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
							subProductVo.getVersionNumber(),restrictionStationVO);
				}

			}
		}
		log.exiting("SubProduct","overRideSubProductRestrictionStation");
	}
	/**
	 * This method will find SubProductRestrictionStation from the Set RestrictionStation
	 * @param restrictionStationVO
	 * @return SubProductRestrictionStation
	 * @throws SystemException
	 */
	private SubProductRestrictionStation retrieveRestrictionStation(RestrictionStationVO
			restrictionStationVO)throws SystemException{
		log.entering("SubProduct","findRestrictionStation");
		SubProductRestrictionStationPK subProductRestrictionStationPK=null;
		String isOrigin="";
		for(SubProductRestrictionStation subProductRestrictionStation:this.getRestrictionStation()){
			if(restrictionStationVO.getIsOrigin()){
				isOrigin=SubProductVO.FLAG_YES;
			}
			else{
				isOrigin=SubProductVO.FLAG_NO;
			}
			subProductRestrictionStationPK=new SubProductRestrictionStationPK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
					this.getSubProductPk().getVersionNumber(),restrictionStationVO.getStation(),isOrigin);

			if(subProductRestrictionStationPK.
					equals(subProductRestrictionStation.getSubProductRestrictionStationPk())){
				log.log(Log.FINE, "Returning subProductRestrictionStation");
				return subProductRestrictionStation;
			}
		}
		log.exiting("SubProduct","findRestrictionStation");
		return null;
	}
	/**
	 * This method will delete SubProductService based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductService(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteSubProductService");
		if(subProductVo.getServices()!=null){
			for(ProductServiceVO subProductServiceVO: subProductVo.getServices()){
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(subProductServiceVO.getOperationFlag())){
					SubProductService localSubProductService=
						retrieveSubProductService(subProductServiceVO);
					if(localSubProductService != null){
						localSubProductService.remove();
						this.subProductService.remove(localSubProductService);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductService");
	}
	/**
	 * This method will insert SubProductService based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductService(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertSubProductService");
		if(subProductVo.getServices()!=null){
			for(ProductServiceVO subProductServiceVO: subProductVo.getServices()){
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(subProductServiceVO.getOperationFlag())){
					if(this.subProductService==null){
						this.subProductService = new HashSet<SubProductService>();
					}
					this.subProductService.add(
					new SubProductService(this.getSubProductPk().getCompanyCode(),this.getSubProductPk().getProductCode(),
							this.getSubProductPk().getSubProductCode(),subProductVo.getVersionNumber(),subProductServiceVO));
				}
			}
		}
		log.exiting("SubProduct","insertSubProductService");
	}

	/**
	 * This method will delete SubProductEvent based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void deleteSubProductEvent(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","deleteSubProductEvent");
		if(subProductVo.getEvents()!=null){
			for(ProductEventVO productEventVO: subProductVo.getEvents()){
				if(SubProductVO.OPERATION_FLAG_DELETE.equals(productEventVO.getOperationFlag())){
					SubProductEvent localSubProductEvent=
						retrieveSubProductEvent(productEventVO);
					if(localSubProductEvent != null){
						localSubProductEvent.remove();
						this.subProductEvent.remove(localSubProductEvent);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductEvent");
	}

	/**
	 * This method will insert SubProductEvent based on OperationFlag
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void insertSubProductEvent(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","insertSubProductEvent");
		if(subProductVo.getEvents()!=null){
			for(ProductEventVO productEventVO: subProductVo.getEvents()){
				if(SubProductVO.OPERATION_FLAG_INSERT.equals(productEventVO.getOperationFlag())){
					new SubProductEvent(this.getSubProductPk().getCompanyCode(),this.getSubProductPk().getProductCode(),
							this.getSubProductPk().getSubProductCode(),subProductVo.getVersionNumber(),productEventVO);
				}
			}
		}
		log.exiting("SubProduct","insertSubProductEvent");
	}

	/**
	 * This method will over ride event restriction of the subproduct
	 * with the product event restriction
	 * @param subProductVo
	 * @throws SystemException
	 */
	private void overRideSubProductEvent(SubProductVO subProductVo)throws SystemException{
		log.entering("SubProduct","overRideSubProductEvent");
		if(subProductVo.getEvents()!=null){
			for(ProductEventVO productEventVO: subProductVo.getEvents()){
				if(productEventVO.getOperationFlag()==null ||
						SubProductVO.OPERATION_FLAG_UPDATE.equals(productEventVO.getOperationFlag())){
					new SubProductEvent(this.getSubProductPk().getCompanyCode(),this.getSubProductPk().getProductCode(),
							this.getSubProductPk().getSubProductCode(),subProductVo.getVersionNumber(),productEventVO);
				}

			}
		}
		log.exiting("SubProduct","overRideSubProductEvent");
	}

	/**
	 * This method will find SubProductService from the Set RestrictionStation
	 * @param subProductServiceVO
	 * @return SubProductService
	 * @throws SystemException
	 */
	private SubProductService retrieveSubProductService(ProductServiceVO
			subProductServiceVO)throws SystemException{
		log.entering("SubProduct","findSubProductService");
		SubProductServicePK subProductServicePK=null;
		for(SubProductService subProductService:this.getSubProductService()){
			subProductServicePK=new SubProductServicePK(this.getSubProductPk().getCompanyCode(),
					this.getSubProductPk().getProductCode(),this.getSubProductPk().getSubProductCode(),
					this.getSubProductPk().getVersionNumber(),subProductServiceVO.getServiceCode());

			if(subProductServicePK.
					equals(subProductService.getSubProductServicesPk())){
				return subProductService;
			}
		}
		log.exiting("SubProduct","findSubProductService");
		return null;
	}

	/**
	 * Method for modifying sub product event
	 * @author A-1885
	 * @param subProductVo
	 * @param productEventVOs
	 * @throws SystemException
	 */
	private void updateEvent(SubProductVO subProductVo) throws SystemException{
		log.entering("SubProduct","updateEventModify");
		if(subProductVo.getEvents()!=null){
			for(ProductEventVO productEventVO: subProductVo.getEvents()){
				if(SubProductVO.OPERATION_FLAG_UPDATE.equals(productEventVO.getOperationFlag())){
					SubProductEvent localSubProductEvent=
						retrieveSubProductEvent(productEventVO);
					if(localSubProductEvent != null){
						localSubProductEvent.update(productEventVO);
					}
				}
			}
		}
		log.exiting("SubProduct","deleteSubProductEvent");
	}

	/**
	 * This method will find SubProductEvent from the Set SubProductEvent
	 * @param productEventVO
	 * @return SubProductEvent
	 * @throws SystemException
	 */
	private SubProductEvent retrieveSubProductEvent(ProductEventVO
			productEventVO)throws SystemException{
		log.entering("SubProduct","retrieveSubProductEvent");
		SubProductEventPK subProductEventPK=null;
		for(SubProductEvent subProductEvent:this.getSubProductEvent()){
			subProductEventPK=new SubProductEventPK();
			subProductEventPK.setCompanyCode(this.getSubProductPk().getCompanyCode());
			subProductEventPK.setProductCode(this.getSubProductPk().getProductCode());
			subProductEventPK.setSubProductCode(this.getSubProductPk().getSubProductCode());
			subProductEventPK.setVersionNumber(this.getSubProductPk().getVersionNumber());
			subProductEventPK.setEventType(productEventVO.getEventType());
			subProductEventPK.setEventCode(productEventVO.getEventCode());
			if(subProductEventPK.
					equals(subProductEvent.getSubProductEventPk())){
				return subProductEvent;
			}
		}
		log.exiting("SubProduct","retrieveSubProductEvent");
		return null;
	}

	/**
	 *
	 * @throws SystemException
	 */
	private void deleteAllRestrictions() throws SystemException{
		for(SubProductRestrictionCommodity subProductRestrictionCommodity : this.getRestrictionCommodity()){
			subProductRestrictionCommodity.remove();
		}
		for(SubProductRestrictionCustomerGroup subProductRestrictionCustomerGroup : this.getRestrictionCustomerGroup()){
			subProductRestrictionCustomerGroup.remove();
		}
		for(SubProductRestrictionPaymentTerms subProductRestrictionPaymentTerms : this.getRestrictionPaymentTerms()){
			subProductRestrictionPaymentTerms.remove();
		}
		for(SubProductRestrictionSegment subProductRestrictionSegment : this.getRestrictionSegment()){
			subProductRestrictionSegment.remove();
		}
		for(SubProductRestrictionStation subProductRestrictionStation : this.getRestrictionStation()){
			subProductRestrictionStation.remove();
		}for(SubProductEvent subProductEvent : this.getSubProductEvent()){
			subProductEvent.remove();
		}
	}

	/**
	 * This method is used to construct ProductDefaultsDAO object
	 * @return
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	private static ProductDefaultsDAO constructDAO()
	throws SystemException,PersistenceException{
		EntityManager entityManager = PersistenceController.getEntityManager();
		return ProductDefaultsDAO.class.cast(entityManager.getQueryDAO("products.defaults"));
	}

	/**
	 * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
	 * @param companyCode
	 * @param productCode
	 * @param transportationMode
	 * @param productPriority
	 * @param primaryScc
	 * @return List<ProductServiceVO>
	 * @throws SystemException
	 */
	public static List<ProductServiceVO> findProductServices
 		(String companyCode, String productCode, String transportationMode,
 				String productPriority, String primaryScc)
 			throws SystemException {
		try{
			return constructDAO().findProductServices
				(companyCode, productCode, transportationMode, productPriority, primaryScc);
		} catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),persistenceException);
		}
 	}

	/**
     * added by A-1648 for capacity-booking submodule
	 * This method validates a subproduct.
     * @param companyCode
     * @param productCode
     * @param transportationMode
     * @param productPriority
     * @param primaryScc
     * @return SubProductVO
     * @throws SystemException
     */
    public static SubProductVO validateSubProduct
    	(String companyCode, String productCode, String transportationMode,
			String productPriority, String primaryScc)
    			throws SystemException {
    	try{
			return constructDAO().validateSubProduct
				(companyCode, productCode, transportationMode, productPriority, primaryScc);
		} catch(PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(),persistenceException);
		}
    }
    
    /**
	 * 	Getter for maximumDimension 
	 *	Added by : A-7896 on 05-Sep-2018
	 * 	Used for :ICRD-232462
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

}
