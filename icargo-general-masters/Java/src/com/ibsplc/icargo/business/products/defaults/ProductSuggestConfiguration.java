
/*
 * ProductSCC.java Created on Dec 10, 2014
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestConfigurationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestionVO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.products.defaults.ProductDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.CreateException;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.entity.Staleable;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * The Class ProductSuggestConfiguration.
 *
 * @author A-5111 
 * ProductSuggestConfiguration
 */
@Table(name="PRDSUGCFGMST")
@Entity 
@Staleable 
public class ProductSuggestConfiguration {  

	/** The log. */
	private Log log = LogFactory.getLogger("ProductSuggestConfiguration");
	
	/** The parameter code. */
	private String parameterCode;  
    
    /** The parameter value. */
    private String parameterValue; 
    
    /** The priority. */
    private  int priority;
    
    /** The product suggest configuration pk. */
    private ProductSuggestConfigurationPK productSuggestConfigurationPK;
    
    private String productConditionString;
    
    private String sourceIndicator;
    
    /**
     * Gets the parameter code.
     *
     * @return the parameter code
     */
    @Column(name="PARCOD")
	public String getParameterCode() {
		return parameterCode;
	}
 
	/**
	 * Sets the parameter code.
	 *
	 * @param parameterCode the new parameter code
	 */
	public void setParameterCode(String parameterCode) {
		this.parameterCode = parameterCode;
	}
	
	/**
	 * Gets the parameter value.
	 *
	 * @return the parameter value
	 */
	@Column(name="PARVAL")
	public String getParameterValue() {
		return parameterValue;
	}

	/**
	 * Sets the parameter value.
	 *
	 * @param parameterValue the new parameter value
	 */
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	/**
	 * Gets the priority.
	 *
	 * @return the priority
	 */
	@Column(name="PARPRY")
	public int getPriority() {
		return priority;
	}

	/**
	 * Sets the priority.
	 *
	 * @param priority the new priority
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}
    
    /**
	 * 	Getter for productConditionString 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	@Column(name="PRDCNDSTR")
	public String getProductConditionString() {
		return productConditionString;
	}

	/**
	 *  @param productConditionString the productConditionString to set
	 * 	Setter for productConditionString 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public void setProductConditionString(String productConditionString) {
		this.productConditionString = productConditionString;
	}

	/**
	 * 	Getter for sourceIndicator 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	@Column(name="SRCIND")
	public String getSourceIndicator() {
		return sourceIndicator;
	}

	/**
	 *  @param sourceIndicator the sourceIndicator to set
	 * 	Setter for sourceIndicator 
	 *	Added by : A-8146 on 12-Sep-2018
	 * 	Used for :
	 */
	public void setSourceIndicator(String sourceIndicator) {
		this.sourceIndicator = sourceIndicator;
	}

	/**
     * Gets the product suggest configuration pk.
     *
     * @return the product suggest configuration pk
     */
    @EmbeddedId
    @AttributeOverrides({
		@AttributeOverride(name="companyCode", column=@Column(name="CMPCOD")),
		@AttributeOverride(name="productName", column=@Column(name="PRDNAM")),
		@AttributeOverride(name="serialNumber", column=@Column(name="SERNUM"))
	})
	public ProductSuggestConfigurationPK getProductSuggestConfigurationPK() {
		return productSuggestConfigurationPK;
	}

	/**
	 * Sets the product suggest configuration pk.
	 *
	 * @param productSuggestConfigurationPK the new product suggest configuration pk
	 */
	public void setProductSuggestConfigurationPK(
			ProductSuggestConfigurationPK productSuggestConfigurationPK) {
		this.productSuggestConfigurationPK = productSuggestConfigurationPK;
	}

	/**
	 * Instantiates a new product suggest configuration.
	 */
	public ProductSuggestConfiguration() {
		super();
	}

	/**
	 * Instantiates a new product suggest configuration.
	 *
	 * @param productSuggestConfigurationVO the product suggest configuration vo
	 * @throws SystemException the system exception
	 */
	public ProductSuggestConfiguration(
			ProductSuggestConfigurationVO productSuggestConfigurationVO)
			throws SystemException {

		populateProductSuggestConfigurationPK(productSuggestConfigurationVO);
		populateProductSuggestConfigurationAttributes(productSuggestConfigurationVO);

		try {
			PersistenceController.getEntityManager().persist(this);
		} catch (CreateException createException) {
			throw new SystemException(createException.getErrorCode(),
					createException);
		}

	}

	/**
	 * Populate product suggest configuration attributes.
	 *
	 * @param productSuggestConfigurationVO the product suggest configuration vo
	 */
	private void populateProductSuggestConfigurationAttributes(
			ProductSuggestConfigurationVO productSuggestConfigurationVO) {
		this.parameterCode = productSuggestConfigurationVO.getParameterCode();
		this.parameterValue = productSuggestConfigurationVO.getParameterValue();
		this.priority = productSuggestConfigurationVO.getPriority();
		this.productConditionString=productSuggestConfigurationVO.getProductConditionString();
		this.sourceIndicator=productSuggestConfigurationVO.getSourceIndicator();
	}

	/**
	 * Populate product suggest configuration pk.
	 *
	 * @param productSuggestConfigurationVO the product suggest configuration vo
	 */
	private void populateProductSuggestConfigurationPK(
			ProductSuggestConfigurationVO productSuggestConfigurationVO) {
		ProductSuggestConfigurationPK productSuggestConfigurationPK = new ProductSuggestConfigurationPK();
		productSuggestConfigurationPK
				.setCompanyCode(productSuggestConfigurationVO.getCompanyCode());
		productSuggestConfigurationPK
				.setProductName(productSuggestConfigurationVO.getProductName());
		setProductSuggestConfigurationPK(productSuggestConfigurationPK);
	}  
	  
	
	/**
	 * Delete product suggest configurations.
	 *
	 * @param companyCode the company code
	 * @throws SystemException the system exception
	 */
	public void deleteProductSuggestConfigurations(String companyCode) throws SystemException{
		    log.entering("ProductSuggestConfiguration", "deleteProductSuggestConfigurations");
		    EntityManager entityManager = PersistenceController.getEntityManager();
		    ProductDefaultsSqlDAO productDefaultsSqlDAO;
		    try { productDefaultsSqlDAO = (ProductDefaultsSqlDAO)ProductDefaultsSqlDAO.class.cast(entityManager.getQueryDAO("products.defaults"));
		          productDefaultsSqlDAO.deleteProductSuggestConfigurations(companyCode);
		    } catch (PersistenceException persistenceException) {
		      throw new SystemException(persistenceException.getErrorCode());
		    }
		    log.exiting("ProductSuggestConfiguration", "deleteProductSuggestConfigurations"); 
	}
	   
	/**
	 * Find.
	 *
	 * @param productSuggestConfigurationVO the product suggest configuration vo
	 * @return the product suggest configuration
	 * @throws SystemException the system exception 
	 * @throws FinderException the finder exception
	 */ 
	public static ProductSuggestConfiguration findProductSuggestConfiguration(ProductSuggestConfigurationVO productSuggestConfigurationVO) throws SystemException, FinderException{
		 EntityManager em = PersistenceController.getEntityManager();
		 ProductSuggestConfigurationPK productSuggestConfigurationPK = new ProductSuggestConfigurationPK();
			productSuggestConfigurationPK
					.setCompanyCode(productSuggestConfigurationVO.getCompanyCode());
			productSuggestConfigurationPK
					.setProductName(productSuggestConfigurationVO.getProductName());
		 ProductSuggestConfiguration productSuggestConfiguration = null;
		return (ProductSuggestConfiguration) em.find(ProductSuggestConfiguration.class, productSuggestConfigurationPK);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		return new StringBuilder(getProductSuggestConfigurationPK().toString()).append("ProductSuggestConfiguration[").append(this.parameterCode).append(this.parameterValue).append(this.priority).append("]").toString();
	}
	
	/**  
	 * Retrive vo.
	 *
	 * @return the product suggest configuration vo 
	 */
	public ProductSuggestConfigurationVO retriveVO(){
		ProductSuggestConfigurationVO productSuggestConfigurationVO=new ProductSuggestConfigurationVO();
		productSuggestConfigurationVO.setCompanyCode(this.productSuggestConfigurationPK.getCompanyCode());
		productSuggestConfigurationVO.setParameterCode(this.parameterCode);
		productSuggestConfigurationVO.setProductName(this.productSuggestConfigurationPK.getProductName());
		productSuggestConfigurationVO.setParameterValue(this.parameterValue);
		productSuggestConfigurationVO.setPriority(this.priority);
		productSuggestConfigurationVO.setSerialNumber(this.productSuggestConfigurationPK.getSerialNumber());
		log.log(Log.INFO, "-------- in ProductSuggestConfiguration Entity---------",this.productSuggestConfigurationPK);
		return productSuggestConfigurationVO;
	}
	
	/**
	 * Find product mappings.
	 *
	 * @param companyCode the company code
	 * @param sccCodes the scc codes
	 * @return the hash map
	 * @throws SystemException the system exception
	 */
	public static String findProductMappings(
			String companyCode, String sccCodes) throws SystemException {
		ProductDefaultsDAO productDefaultsDAO;
		EntityManager entityManager = PersistenceController.getEntityManager();
		String product = null;
		try {
			productDefaultsDAO = (ProductDefaultsSqlDAO) ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			product=productDefaultsDAO.findProductMappings(companyCode, sccCodes);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return product;
	}
	/**
	 * Find product mappings.
	 *
	 * @param companyCode the company code
	 * @param sccCodes the scc codes
	 * @return the hash map
	 * @throws SystemException the system exception
	 */
	public ArrayList<ProductSuggestionVO> findProductSuggestions(
			ProductSuggestionVO productSuggestionVO) throws SystemException {
		ProductDefaultsDAO productDefaultsDAO;
		EntityManager entityManager = PersistenceController.getEntityManager();
		ArrayList<ProductSuggestionVO>productSuggestions=null;
		try {
			productDefaultsDAO = (ProductDefaultsSqlDAO) ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			productSuggestions=productDefaultsDAO.findProductSuggestions(productSuggestionVO);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return productSuggestions;
	}
	
	/**
     * @author A-5867.
     * @param companyCode
     * @param parameterCode
     * @param parameterValue
     * @return 
     * @throws SystemException 
     */
	public static HashMap<String, String> findSuggestedProducts(String companyCode,String parameterCode,String parameterValue) throws SystemException {
		ProductDefaultsDAO productDefaultsDAO;
		EntityManager entityManager = PersistenceController.getEntityManager();
		HashMap<String, String> productMap=null;
		try {
			productDefaultsDAO = (ProductDefaultsSqlDAO) ProductDefaultsSqlDAO.class
					.cast(entityManager.getQueryDAO("products.defaults"));
			productMap=productDefaultsDAO.findSuggestedProducts(companyCode,parameterCode,parameterValue);
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode());
		}
		return productMap;
	}
	
}
