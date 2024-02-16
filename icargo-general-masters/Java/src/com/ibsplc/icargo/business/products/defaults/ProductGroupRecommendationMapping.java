/*
 * ProductGroupRecommendationMapping Class.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.products.defaults.vo.ProductGroupRecommendationMappingVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;


// TODO: Auto-generated Javadoc
/**
 * The Class ProductGroupRecommendationMapping.
 * @author A-6843
 *
 */
@Table(name="PRDGRPRMDMPG")
@Entity
@Staleable
public class ProductGroupRecommendationMapping {
	/** The log. */
	private Log log = LogFactory.getLogger("ProductGroupRecommendationMapping");
	
	/** The product group recommendation mapping pk. */
	private ProductGroupRecommendationMappingPK productGroupRecommendationMappingPK; 
	 
 	/** The product group. */
 	private String productGroup;
	 
 	/** The product priority. */
 	private String productPriority;
	 
 	/** The e di channel flag. */
 	private String eDIChannelFlag;
	 
 	/** The so-co channel flag. */
 	private String sOCOChannelFlag;
	 
 	/** The portal channel flag. */
 	private String portalChannelFlag;
	 
 	/** The last updated user. */
 	private String lastUpdateUser; 
	 
 	/** The last updated time. */
 	private Calendar lastUpdateTime; 
	
 	/** The possible booking type. */
 	private String possibleBookingType;
 	
 	/** The consol shipment. */
	 private String consolShipment;
	 /**
 	 * Gets the product group recommendation mapping pk.
 	 *
 	 * @return the product group recommendation mapping pk
 	 */
	 @EmbeddedId
	 @AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="commodityCode", column=@Column(name="COMCOD"))
			})
 	public ProductGroupRecommendationMappingPK getProductGroupRecommendationMappingPK() {
		return productGroupRecommendationMappingPK;
	}
	
	/**
	 * Sets the product group recommendation mapping pk.
	 *
	 * @param productGroupRecommendationMappingPK the new product group recommendation mapping pk
	 */
	public void setProductGroupRecommendationMappingPK(
			ProductGroupRecommendationMappingPK productGroupRecommendationMappingPK) {
		this.productGroupRecommendationMappingPK = productGroupRecommendationMappingPK;
	}
	
	/**
	 * Gets the product group.
	 *
	 * @return the product group
	 */
	@Column(name="PRDGRP")
	public String getProductGroup() {
		return productGroup;
	}
	
	/**
	 * Sets the product group.
	 *
	 * @param productGroup the new product group
	 */
	public void setProductGroup(String productGroup) {
		this.productGroup = productGroup;
	}
	
	/**
	 * Gets the product priority.
	 *
	 * @return the product priority
	 */
	@Column(name="PRDPRY")
	public String getProductPriority() {
		return productPriority;
	}
	
	/**
	 * Sets the product priority.
	 *
	 * @param productPriority the new product priority
	 */
	public void setProductPriority(String productPriority) {
		this.productPriority = productPriority;
	}
	
	/**
	 * Gets the e di channel flag.
	 *
	 * @return the e di channel flag
	 */
	@Column(name="EDIMSG")
	public String geteDIChannelFlag() {
		return eDIChannelFlag;
	}
	
	/**
	 * Sets the e di channel flag.
	 *
	 * @param eDIChannelFlag the new e di channel flag
	 */
	public void seteDIChannelFlag(String eDIChannelFlag) {
		this.eDIChannelFlag = eDIChannelFlag;
	}
	
	/**
	 * Gets the s oco channel flag.
	 *
	 * @return the s oco channel flag
	 */
	@Column(name="SOLCFG")
	public String getsOCOChannelFlag() {
		return sOCOChannelFlag;
	}
	
	/**
	 * Sets the s oco channel flag.
	 *
	 * @param sOCOChannelFlag the new s oco channel flag
	 */
	public void setsOCOChannelFlag(String sOCOChannelFlag) {
		this.sOCOChannelFlag = sOCOChannelFlag;
	}
	
	/**
	 * Gets the portal channel flag.
	 *
	 * @return the portal channel flag
	 */
	@Column(name="PORBKG")
	public String getPortalChannelFlag() {
		return portalChannelFlag;
	}
	
	/**
	 * Sets the portal channel flag.
	 *
	 * @param portalChannelFlag the new portal channel flag
	 */
	public void setPortalChannelFlag(String portalChannelFlag) {
		this.portalChannelFlag = portalChannelFlag;
	}
	
	/**
	 * Gets the last update user.
	 *
	 * @return the last update user
	 */
	@Column(name="LSTUPDUSR")
	public String getLastUpdateUser() {
		return lastUpdateUser;
	}
	
	/**
	 * Sets the last update user.
	 *
	 * @param lastUpdateUser the new last update user
	 */
	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}
	
	/**
	 * Gets the last updated time.
	 *
	 * @return the last update time
	 */
	@Column(name="LSTUPDTIM")
	public Calendar getLastUpdateTime() {
		return lastUpdateTime;
	}
	
	/**
	 * Sets the last updated time.
	 *
	 * @param lastUpdateTime the new last update time
	 */
	public void setLastUpdateTime(Calendar lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}
	 /**
	 * Gets the possible booking type.
	 *
	 * @return the possible booking type
	 */
	@Column(name="POSBKGTYP")
	public String getPossibleBookingType() {
		return possibleBookingType;
	}
	/**
	 * Sets the possible booking type.
	 *
	 * @param possibleBookingType the new possible booking type
	 */
	public void setPossibleBookingType(String possibleBookingType) {
		this.possibleBookingType = possibleBookingType;
	}
	
	/**
	 * Gets the consolidated shipment.
	 *
	 * @return the consolidated shipment
	 */
	@Column(name="CONSOL")
	 public String getConsolShipment() {
		return consolShipment;
	}

	/**
	 * Sets the consolidated shipment.
	 *
	 * @param consolidatedShipment the new consolidated shipment
	 */
	public void setConsolShipment(String consolShipment) {
		this.consolShipment = consolShipment;
	}

	/**
	 * Default Constructor.
	 */
	public ProductGroupRecommendationMapping(){
	}

	/**
	 * Constructor for ProductGroupRecommendationMapping for creating Product Group Recommendation Mapping.
	 *
	 * @param productGroupRecommendationMappingVO the product group recommendation mapping vo
	 * @throws SystemException the system exception
	 */
	public ProductGroupRecommendationMapping(
			ProductGroupRecommendationMappingVO productGroupRecommendationMappingVO)
	throws SystemException{
		try{
			populatePk(productGroupRecommendationMappingVO);
			populateAttributes(productGroupRecommendationMappingVO);
			PersistenceController.getEntityManager().persist(this);
		}catch(CreateException createException){
			throw new SystemException(createException.getErrorCode());
		}
	}
	
	/**
	 * Method for populating the primary key.
	 *
	 * @param productGroupRecommendationMappingVO the product group recommendation mapping vo
	 */
    private void populatePk(
    		ProductGroupRecommendationMappingVO productGroupRecommendationMappingVO) {
    	ProductGroupRecommendationMappingPK productGroupRecommendationMappingPK =
    			new ProductGroupRecommendationMappingPK();
    	productGroupRecommendationMappingPK.setCompanyCode(
    			productGroupRecommendationMappingVO.getCompanyCode());
    	productGroupRecommendationMappingPK.setCommodityCode(
    			productGroupRecommendationMappingVO.getCommodityCode());
		setProductGroupRecommendationMappingPK(productGroupRecommendationMappingPK);
	}
	
	/**
	 * Populate attributes.
	 *
	 * @param productGroupRecommendationMappingVO the product group recommendation mapping vo
	 */
	public void populateAttributes(
			ProductGroupRecommendationMappingVO productGroupRecommendationMappingVO) {
		setLastUpdateTime(
				productGroupRecommendationMappingVO.getLastUpdateTime());
		setLastUpdateUser(
				productGroupRecommendationMappingVO.getLastUpdateUser());
		setProductGroup(productGroupRecommendationMappingVO.getProductGroup());
		setProductPriority(productGroupRecommendationMappingVO.getProductPriority());
		seteDIChannelFlag(productGroupRecommendationMappingVO.geteDIChannelFlag());
		setPortalChannelFlag(productGroupRecommendationMappingVO.getPortalChannelFlag());
		setsOCOChannelFlag(productGroupRecommendationMappingVO.getsOCOChannelFlag());
		setPossibleBookingType(productGroupRecommendationMappingVO.getPossibleBookingType());
		setConsolShipment(productGroupRecommendationMappingVO.getConsolShipment());
	}
	/**
	 * Delete Product Group Recommendation Mappings.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void deleteProductGroupRecommendations(String companyCode)
			throws SystemException{
		log.entering("ProductGroupRecommendationMapping",
				"deleteProductGroupRecommendations");
		EntityManager entityManager = PersistenceController.getEntityManager();
		ProductDefaultsSqlDAO productDefaultsSqlDAO;
		try {
			productDefaultsSqlDAO = (ProductDefaultsSqlDAO)ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			productDefaultsSqlDAO.deleteProductGroupRecommendations(
					companyCode);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		log.exiting("ProductGroupRecommendationMapping",
				"deleteProductGroupRecommendations"); 
	}
}
