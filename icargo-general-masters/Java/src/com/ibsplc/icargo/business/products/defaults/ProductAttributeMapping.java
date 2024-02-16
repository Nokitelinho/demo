/*
 * 
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.Calendar;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ibsplc.icargo.business.products.defaults.vo.ProductAttributeMappingVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class ProductAttributeMapping.
 * @author A-6843
 *
 */
@Table(name="PRDATRMPG")
@Entity
@Staleable
public class ProductAttributeMapping {
	/** The log. */
	private Log log = LogFactory.getLogger("ProductGroupRecommendationMapping");
		
	private ProductAttributeMappingPK productAttributeMappingPK; 
	 private String attributeName;
	 private String eDIChannelFlag;
	 private String sOCOChannelFlag;
	 private String portalChannelFlag;
	 private String upsellingProductCodes;
	 private String lastUpdateUser; 
	 private Calendar lastUpdateTime; 
	 
	 /**
 	 * Gets the product attribute mapping pk.
 	 *
 	 * @return the product attribute mapping pk
 	 */
	 @EmbeddedId
	 @AttributeOverrides({
		 @AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		 @AttributeOverride(name="productName", column=@Column(name="PRDNAM"))
			})
 	public ProductAttributeMappingPK getProductAttributeMappingPK() {
			return productAttributeMappingPK;
		}
		
		/**
		 * Sets the product attribute mapping pk.
		 *
		 * @param productAttributeMappingPK the new product attribute mapping pk
		 */
		public void setProductAttributeMappingPK(
				ProductAttributeMappingPK productAttributeMappingPK) {
			this.productAttributeMappingPK = productAttributeMappingPK;
		}
		
		/**
		 * Gets the attribute name.
		 *
		 * @return the attribute name
		 */
		@Column(name="ATRNAM")
		public String getAttributeName() {
			return attributeName;
		}
		
		/**
		 * Sets the attribute name.
		 *
		 * @param attributeName the new attribute name
		 */
		public void setAttributeName(String attributeName) {
			this.attributeName = attributeName;
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
		 * Gets the so co channel flag.
		 *
		 * @return the so co channel flag
		 */
		@Column(name="SOLCFG")
		public String getsOCOChannelFlag() {
			return sOCOChannelFlag;
		}
		
		/**
		 * Sets the so co channel flag.
		 *
		 * @param sOCOChannelFlag the new so co channel flag
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
		 * Gets the upselling product code.
		 *
		 * @return the upselling product code
		 */
		@Column(name="UPSPRDNAM")
		public String getUpsellingProductCodes() {
			return upsellingProductCodes;
		}

		/**
		 * Sets the upselling product code.
		 *
		 * @param upsellingProductCode the new upselling product code
		 */
		public void setUpsellingProductCodes(String upsellingProductCodes) {
			this.upsellingProductCodes = upsellingProductCodes;
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
		 * Gets the last update time.
		 *
		 * @return the last update time
		 */
		@Column(name = "LSTUPDTIM")
		@Temporal(TemporalType.TIMESTAMP)
		public Calendar getLastUpdateTime() {
			return lastUpdateTime;
		}
		
		/**
		 * Sets the last update time.
		 *
		 * @param lastUpdateTime the new last update time
		 */
		public void setLastUpdateTime(Calendar lastUpdateTime) {
			this.lastUpdateTime = lastUpdateTime;
		}
		 /**
	   	 * Default Constructor.
	   	 */
		public ProductAttributeMapping(){
		}

		/**
		 * Constructor for ProductCommodityGroup for creating Product CommodityGroup
		 * @param productCommodityGroupMappingVO
		 * @throws SystemException
		 */
		public ProductAttributeMapping(
				ProductAttributeMappingVO productAttributeMappingVO)
		throws SystemException{
			try{
				populatePk(productAttributeMappingVO);
				populateAttributes(productAttributeMappingVO);
				PersistenceController.getEntityManager().persist(this);
			}catch(CreateException createException){
				throw new SystemException(createException.getErrorCode());
			}
		}
		
		/**
		 * Method for populating the primary key
		 * @param ProductAttributeMappingVO
		 */
	    private void populatePk(
	    		ProductAttributeMappingVO productAttributeMappingVO) {
	    	ProductAttributeMappingPK productAttributeMappingPK =
	    			new ProductAttributeMappingPK();
	    	productAttributeMappingPK.setCompanyCode(
	    			productAttributeMappingVO.getCompanyCode());
	    	productAttributeMappingPK.setProductName(
	    			productAttributeMappingVO.getProductName());
	    	setProductAttributeMappingPK(productAttributeMappingPK);
		}
		/**
		 * @param productAttributePriorityVO
		 */
		public void populateAttributes(
				ProductAttributeMappingVO productAttributeMappingVO) {
			setLastUpdateTime(productAttributeMappingVO.getLastUpdateTime());
			setLastUpdateUser(productAttributeMappingVO.getLastUpdateUser());
			setAttributeName(productAttributeMappingVO.getAttributeName());
			seteDIChannelFlag(productAttributeMappingVO.geteDIChannelFlag());
			setPortalChannelFlag(productAttributeMappingVO.getPortalChannelFlag());
			setsOCOChannelFlag(productAttributeMappingVO.getsOCOChannelFlag());
			setUpsellingProductCodes(productAttributeMappingVO.getUpsellingProductCodes());
		}
		
		/**
		 * Delete Product Attribute Mappings.
		 *
		 * @param companyCode the company code
		 * @throws SystemException the system exception
		 */
		public void deleteProductAttributeMappings(String companyCode)
				throws SystemException{
			log.entering("ProductAttributeMapping",
					"deleteProductAttributeMappings");
			EntityManager entityManager = PersistenceController.getEntityManager();
			ProductDefaultsSqlDAO productDefaultsSqlDAO;
			try {
				productDefaultsSqlDAO = (ProductDefaultsSqlDAO)ProductDefaultsSqlDAO.class
						.cast(entityManager.getQueryDAO("products.defaults"));
				productDefaultsSqlDAO.deleteProductAttributeMappings(
						companyCode);
			} catch (PersistenceException persistenceException) {
				throw new SystemException(persistenceException.getErrorCode());
			}
			log.exiting("ProductAttributeMapping",
					"deleteProductAttributeMappings"); 
		}


}
