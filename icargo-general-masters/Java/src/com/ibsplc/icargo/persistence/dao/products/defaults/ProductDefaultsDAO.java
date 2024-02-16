/*
 * ProductDefaultsDAO.java Created on Jun 28, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.persistence.dao.products.defaults;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestionVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParametersVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * 
 * @author A-1358
 *
 */
public interface ProductDefaultsDAO {
    
	/**
	 * Find All Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */    
    Page<ProductVO> findProducts(ProductFilterVO productFilterVo,int displayPage) 
    	throws PersistenceException,SystemException;
    
    /**
	 * Find All SubProducts for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of SubProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */    
    Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,int displayPage) 
    	throws PersistenceException,SystemException;
     
    
    /**
     * Checks whether the given product name exists in the database. Returns the 
     * product code if the product exiss. Returns NULL otherwise
     * @param companyCode
     * @param productName
     * @param startDate
     * @param endDate
	 * @return String 
	 * @throws PersistenceException
	 * @throws SystemException
     */
    String validateProductName(String companyCode, 
            String productName,Calendar startDate,Calendar endDate) throws PersistenceException,SystemException;
    /**
     * @author A-1885
     * @param companyCode
     * @param productName
     * @param startDate
     * @param endDate
     * @return Collection<String>
     * @throws PersistenceException
     * @throws SystemException
     */
    Collection<ProductValidationVO> validateProductForAPeriod(String companyCode, 
            String productName,Calendar startDate,Calendar endDate) throws PersistenceException,SystemException;

    /**
     * Used to fetch the details of a particular product 
     * @param companyCode
     * @param productCode
     * @return collection of ProductLovVO
     * @throws PersistenceException
	 * @throws SystemException 
     */
    ProductVO findProductDetails(String companyCode,String productCode)
    	throws PersistenceException,SystemException;

    /**
     * Used to fetch lov for products containing productname and description 
     * @param productLovFilterVO
     * @param displayPage
     * @return  Page<ProductLovVO>
     * @throws PersistenceException
	 * @throws SystemException 
     */
    Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO,int displayPage)
    	throws PersistenceException,SystemException;
   
    /**
     * Used to fetch the details of a particular subproduct 
     * @param subProductVO
     * @return SubProductVO
     * @throws PersistenceException
     * @throws SystemException
     */
    SubProductVO findSubProductDetails(SubProductVO subProductVO)  
    	throws PersistenceException,SystemException;
    /**
     * 
     * @param productName
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return boolean
     * @throws PersistenceException
     * @throws SystemException
     */
    boolean checkDuplicate(String productName,String companyCode,Calendar startDate,Calendar endDate)
    	throws PersistenceException,SystemException;
    /**
     * 
     * @param productName
     * @param companyCode
     * @param productCode
     * @param startDate
     * @param endDate
     * @return boolean
     * @throws PersistenceException
     * @throws SystemException
     */
    boolean checkDuplicateForModify(String productName,String companyCode,String productCode,Calendar startDate,Calendar endDate)
	throws PersistenceException,SystemException;
	/**
	 * 
	 * @param companyCode
	 * @param productName
	 * @return Collection of ProductValidationVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */
    Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
    	throws PersistenceException,SystemException;
    
    /**
     * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
     * @param companyCode
     * @param productCode
     * @param transportationMode
     * @param productPriority
     * @param primaryScc
     * @return List<ProductServiceVO>
     * @throws PersistenceException
     * @throws SystemException
     */
    List<ProductServiceVO> findProductServices
		(String companyCode, String productCode, String transportationMode, 
				String productPriority, String primaryScc) 
			throws PersistenceException, SystemException;
    
   /**
    * added by A-1648 for capacity-booking submodule
	* This method validates a subproduct.
    * @param companyCode
    * @param productCode
    * @param transportationMode
    * @param productPriority
    * @param primaryScc
    * @return SubProductVO
    * @throws PersistenceException
    * @throws SystemException
    */
   SubProductVO validateSubProduct
   		(String companyCode, String productCode, String transportationMode, 
			String productPriority, String primaryScc) 
   				throws PersistenceException, SystemException;
   /**
    * @author A-1883
    * @param productFeedbackFilterVO
    * @param displayPage
    * @return Page<ProductFeedbackVO>
    * @throws SystemException
    * @throws PersistenceException
    */
   Page<ProductFeedbackVO> listProductFeedback(
			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage) 
			throws SystemException,PersistenceException;
   /**
	 * Method for getting ProductEvent
	 * @author A-1883
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductEventVO>
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	List<ProductEventVO> findProductEvent(String companyCode, 
			String productCode)
	throws SystemException,PersistenceException;
	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return boolean
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	boolean checkStationAvailability(
			StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException,PersistenceException;
	
	/**
	 * Find image of Product for the specied filter criteria
	 * @author A-1870
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws PersistenceException
	 * @throws SystemException
	 */    
	ProductVO findImage(ProductFilterVO productFilterVo) 
    	throws PersistenceException,SystemException;

    /**
     * Added by A-1945
     * @param companyCode
     * @param productCodes
     * @return Collection<ProductValidationVO>
     * @throws SystemException
     */
    Collection<ProductValidationVO> validateProducts(
            String companyCode, Collection<String> productCodes )
            throws SystemException;
    /**
     * @author A-1958
     * @param companyCode
     * @param products
     * @return Collection<String>
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<String> findSccCodesForProducts(String companyCode, 
    		Collection<String> products) throws SystemException, PersistenceException;
    /**
     * 
     * @param companyCode
     * @param productName
     * @param shpgDate
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
    throws SystemException, PersistenceException;
    
    /**
     * 	Method		:	ProductDefaultsDAO.validateProductsForListing
     *	Added by 	:	A-8041 on 02-Nov-2017
     * 	Used for 	:	validateProductsForListing
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException
     *	Parameters	:	@throws PersistenceException 
     *	Return type	: 	Collection<ProductVO>
     */
    Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO)
    	    throws SystemException, PersistenceException;
    /**
     * @author a-1885
     * @param companyCode
     * @param docType
     * @param docSubType
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ProductStockVO> findProductsForStock
    (String companyCode,String docType,String docSubType)throws SystemException,
    PersistenceException;
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param productScc
     * @param productTransportMode
     * @param productPriority
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ProductEventVO> findSubProductEventsForTracking
    (String companyCode,String productCode,String productScc,
    		String productTransportMode,String productPriority)throws 
    		SystemException,PersistenceException;
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param docType
     * @param docSubType
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ProductStockVO> validateProductForDocType
    (String companyCode,String productCode)
    throws SystemException,PersistenceException;
    /**
     * @author a-1885
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    ProductLovVO findProductPkForProductName
    (String companyCode,String productName,LocalDate startDate,LocalDate endDate)
    throws SystemException,PersistenceException;
    /**
     * @author a-4823
     * @param companyCode
     * @param productNames
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ProductVO> validateProductNames(String companyCode,
            Collection<String> productNames)
  	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-5257
     * @param companyCode
     * @param productNames
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVOs)
	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-5111
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public void deleteProductSuggestConfigurations(String companyCode)
	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-5111
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    String findProductMappings(String companyCode, String sccCodes)
	throws SystemException,PersistenceException;

  
    /**
	 * 
	 * @author A-5867
	 * @param ProductFilterVO
	 * @return Collection<ProductVO>
	 * @throws SystemException
	 */
	public Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws SystemException;
	/**
	 * Added for ICRD_350746
	 * For checking if another product exist with the same priority
	 * @param productFilterVO
	 * @return
	 * @throws SystemException
	 */
	public String checkIfDuplicatePrdPriorityExists(ProductVO productVO)throws SystemException;
	 /**
	 * 
	 * @author A-5867
	 * @param companyCode
	 * @param parameterCode
	 * @param parameterValue
	 * @return 
	 * @throws SystemException
	 */
	public HashMap<String, String> findSuggestedProducts(String companyCode,String parameterCode,String parameterValue)
		throws SystemException,PersistenceException;
    /**
     * 
     * @author A-5642
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public void deleteProductCommodityGroupMappings(String companyCode)
	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-6843
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public void deleteProductAttributePriorities(String companyCode)
	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-6843
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public void deleteProductAttributeMappings(String companyCode)
	throws SystemException,PersistenceException;
    /**
     * 
     * @author A-6843
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    public void deleteProductGroupRecommendations(String companyCode)
	throws SystemException,PersistenceException;  


public Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) 
throws SystemException;

public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
		throws SystemException,PersistenceException;
	/**
	 * 
	 * @param companyCode
	 * @throws SystemException
	 * @throws PersistenceException
	 */
	public void removeProductODMapping(String companyCode)
			throws SystemException,PersistenceException;  
	/**
	 * 
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */
	public Collection<ProductODMappingVO> getProductODMapping(String companyCode)
			throws SystemException;
	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.findProducts
	 *	Added by 	:	A-7548 on 19-Jan-2018
	 * 	Used for 	:
	 *	Parameters	:	@param productFilterVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws SystemException;

	/**
	 * 
	 * @param productSuggestionVO
	 * @return
	 * @throws SystemException
	 */
	public ArrayList<ProductSuggestionVO> findProductSuggestions(ProductSuggestionVO productSuggestionVO) throws PersistenceException,SystemException;
	/**
	 * Added by 	:	A-7740 
	 * @param companyCode
	 * @return
	 * @throws SystemException
	 */ 
	public Map<String,String> findProductParametersByCode(String companyCode,
			   String ProductCode,Collection<String> parameterCodes)
	     throws SystemException;
	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.findProductsByParameters
	 *	Added by 	:	A-8146 on 23-Nov-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param parametersAndParValue
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public Collection<ProductVO> findProductsByParameters(String companyCode,
			 Map<String,String> parametersAndParValue)
			throws  SystemException ;
    /**
     * Validate the products based on the names
     * 
     * @author A-9025
     * @param companyCode
     * @param productNames
     * @return
     * @throws SystemException
     * @throws PersistenceException
     */
    Collection<ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames)
    		throws SystemException,PersistenceException;


	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.findProductsByParameters
	 *	Added by 	:	A-8130 on 10-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductParamterVO>
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode)
			throws  SystemException ;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.clearProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void clearProductSelectionRuleMaster(String companyCode) throws SystemException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.listProductSelectionRuleMaster
	 *	Added by 	:	A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 */
	public Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode) throws SystemException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsDAO.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param filterConditions
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 * @param generalParametersVOs 
	 */
	public Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule(String companyCode,
			Map<String, String> filterConditions, Collection<GeneralParametersVO> generalParametersVOs)  throws SystemException;
	
}
