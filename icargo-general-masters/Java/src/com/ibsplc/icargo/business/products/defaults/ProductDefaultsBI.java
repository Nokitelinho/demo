/*
 * ProductDefaultsBI.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductAttributeMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductAttributePriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductCommodityGroupMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFeedbackVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductGroupRecommendationMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductLovVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestConfigurationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestionVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interfaces.BusinessInterface;
import com.ibsplc.xibase.server.framework.persistence.entity.AvoidForcedStaleDataChecks;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.FlowService;

/**
 * @author A-1358
 *
 */
public interface ProductDefaultsBI extends BusinessInterface,FlowService{

	/**
	 * Find All Products for the specified filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<ProductVO> findProducts(ProductFilterVO productFilterVo,
			int displayPage) throws RemoteException,SystemException;

	/**
	 * Find All SubProducts for the specified filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage) throws RemoteException,SystemException;

	/**
	 * used for creating or modifying a product
	 *
	 * @param productVo coresponding to the product to be deleted
	 * @throws RemoteException
	 * @throws SystemException

	 */
	@AvoidForcedStaleDataChecks
	Collection<SubProductVO> saveProductDetails(ProductVO productVo)
	throws RemoteException,SystemException,ProductDefaultsBusinessException;

	/**
	 * used for creating or modifying a subproduct
	 *
	 * @param subProductVos coresponding to the subproduct to be modified
	 * @throws RemoteException
	 * @throws SystemException
	 */
	void saveSubProductDetails(Collection<SubProductVO> subProductVos)
	throws RemoteException,SystemException;

	/**
	 * Used to delete a subProduct. Exception is thrown if active bookings
	 * exist against the subproduct
	 * @param subProductVo
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	void deleteSubProduct(SubProductVO subProductVo)
	throws RemoteException,SystemException,
	ProductDefaultsBusinessException;

	/**
	 * Used to delete a product. Exception is thrown if active bookings
	 * exist against any of the subproduct created from this product
	 * @param productVo
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	@AvoidForcedStaleDataChecks
	void deleteProduct(ProductVO productVo)
	throws RemoteException,SystemException,
	ProductDefaultsBusinessException;

	/**
	 * Used to delete a product. Exception is thrown if active bookings
	 * exist against any of the subproduct created from this product
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return productCode
	 * @throws RemoteException
	 * @throws SystemException
	 */
	String validateProductName(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws RemoteException,SystemException;

	/**
	 * Used to fetch the details of a particular product
	 * @param companyCode
	 * @param productCode
	 * @return productVo
	 * @throws RemoteException
	 * @throws SystemException
	 */
	ProductVO findProductDetails(String companyCode, String productCode)
	throws RemoteException,SystemException;

	/**
	 * Used to fetch the details of a sub product
	 * @param subProductVo
	 * @throws RemoteException
	 * @throws SystemException
	 */
	SubProductVO findSubProductDetails(SubProductVO subProductVo)
	throws RemoteException,SystemException;

	/**
	 * Used to update the status of  product.Status can be 'Activate'
	 * or 'Inactivate'
	 * @param productVOs
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 *
	 */
	@AvoidForcedStaleDataChecks
	void updateProductStatus(Collection<ProductVO> productVOs)throws RemoteException,
	SystemException,ProductDefaultsBusinessException;

	/**
	 * Used to update the status of  sub product.Status can be 'Activate'
	 * or 'Inactivate'
	 * @param productVO
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	void updateSubProductStatus(ProductVO productVO)
	throws RemoteException,SystemException;

	/**
	 * This method is used to fetch ProductLovVOs
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductLovVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO,int displayPage)
	throws RemoteException,SystemException;

	/**
	 * This method will implement the audit method in Auditor.
	 * It will calls the audit method of the audit controller.
	 * @param auditVo
	 * @throws AuditException
	 * @throws RemoteException
	 * @return
	 */
    void audit(Collection<AuditVO> auditVo) throws AuditException, RemoteException;

    /**
	 * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
	 * @param productCode
	 * @param transportationMode
	 * @param productPriority
	 * @return List<ProductServiceVO>
	 * @throws SubProductNotExistingBusinessException
	 * @throws RemoteException
	 * @throws SystemException
	 */
    List<ProductServiceVO> findProductServices
		(String companyCode, String productCode, String transportationMode,
				String productPriority, String primaryScc)
			throws SubProductNotExistingBusinessException, SystemException, RemoteException;

	/**
	 * @param companyCode
	 * @param productName
	 * @return  Collection<ProductValidationVO>
	 * @throws SystemException
	 */
	public Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
		throws SystemException, RemoteException;

    /**
     * added by A-1648 for capacity-booking submodule
	 * This method finds all restrictions for a subproduct.
     * @param companyCode
     * @param productCode
     * @param transportationMode
     * @param productPriority
     * @param primaryScc
     * @return
     * @throws SubProductNotExistingBusinessException
     * @throws SystemException
     * @throws RemoteException
     */
    SubProductVO findProductRestrictions
    	(String companyCode, String productCode, String transportationMode,
			String productPriority, String primaryScc)
    			throws SubProductNotExistingBusinessException, SystemException, RemoteException;
    /**
     * @author A-1885
     * @param companyCode
     * @param productName
     * @param startDate
     * @param endDate
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    Collection<ProductValidationVO> 
    validateProductForAPeriod(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws SystemException,RemoteException,ProductDefaultsBusinessException;
   /**
    * @author A-1883
    * @param productFeedbackVO
    * @throws SystemException
    * @throws RemoteException
    */
    void saveProductFeedback(ProductFeedbackVO productFeedbackVO)
	throws SystemException,RemoteException ;
    /**
     * @author A-1883
     * @param productFeedbackFilterVO
     * @param  displayPage
     * @return Page<ProductFeedbackVO>
     * @throws SystemException
     * @throws RemoteException
     */
    Page<ProductFeedbackVO> listProductFeedback(
			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage)
			throws SystemException,RemoteException ;
    /**
	 * Method for getting ProductEvent
	 * @author A-1883
	 * @param companyCode
	 * @param productCode
	 * @return List<ProductEventVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	List<ProductEventVO> findProductEvent(String companyCode, String productCode)
	throws SystemException,RemoteException ;

	/**
	 * Method for getting ProductPerformanceDetails
	 * @author A-1747
	 * @param productPerformanceFilterVO
	 * @return Collection<ProductPerformanceVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	Collection<ProductPerformanceVO> getProductPerformanceDetailsForReport(ProductPerformanceFilterVO productPerformanceFilterVO)
	throws SystemException,RemoteException ;
	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return Boolean
	 * @throws SystemException
	 * @throws RemoteException
	 */
	 Boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException, RemoteException;

	 /**
	 * Find All Products for the specified filter criteria
	 * @author A-1870
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws RemoteException
	 * @throws SystemException
	 */
	 ProductVO findImage(ProductFilterVO productFilterVo)
				 throws RemoteException,SystemException;
    /**
     * Sending Message
     * @author A-1885
     * @param messageVo
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    void sendMessageForProduct(MessageVO messageVo)throws RemoteException,SystemException;

    /**
     * Added by A-1945
     * @param companyCode
     * @param productCodes
     * @return Map<String, ProductValidationVO>
     * @throws SystemException
     * @throws RemoteException
     * @throws ProductDefaultsBusinessException
     */ 
    Map<String, ProductValidationVO> validateProducts(String companyCode,
            Collection<String> productCodes )
            throws SystemException,
                   RemoteException,
                   ProductDefaultsBusinessException;
    /**
     * @author A-1958
     * @param companyCode
     * @param products
     * @return Collection<String>
     * @throws RemoteException
     * @throws SystemException
     */
    Collection<String> findSccCodesForProducts(String companyCode, Collection<String> products) 
    throws RemoteException,SystemException;
    /**
     * 
     * @param companyCode
     * @param productName
     * @param shpgDate
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    String validateProduct(String companyCode, String productName, LocalDate startDate,
    		LocalDate endDate)
    throws RemoteException,SystemException;
    
    /**
     * 	Method		:	ProductDefaultsBI.validateProductsForListing
     *	Added by 	:	A-8041 on 02-Nov-2017
     * 	Used for 	:	validateProductsForListing
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	Collection<ProductVO>
     */
    Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO)
    	    throws RemoteException,SystemException;
    /**
     * @author a-1885
     * @param companyCode
     * @param documentType
     * @param documentSubType
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    Collection<ProductStockVO> findProductsForStock(String companyCode,
    		String documentType,String documentSubType)throws RemoteException,
    		SystemException;
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param productScc
     * @param productTransportMode
     * @param productPriority
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    Collection<ProductEventVO> findSubproductEventsForTracking
    (String companyCode,String productCode,String productScc,String 
    		productTransportMode,String productPriority)throws SystemException,
    		RemoteException;
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param docType
     * @param docSubType
     * @return
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     * @throws RemoteException
     */
    String validateProductForDocType(String companyCode,String productCode,
    		String docType,String docSubType)throws SystemException,
    		ProductDefaultsBusinessException,RemoteException;
    /**
     * @author a-1885
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return
     * @throws SystemException
     * @throws RemoteException
     */
    ProductLovVO findProductPkForProductName(String companyCode,String productName,
    		LocalDate startDate,LocalDate endDate)throws SystemException,
    		RemoteException;
    /**
     * 
     * @param companyCode
     * @param productNames
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    Map<String,ProductVO> validateProductNames(String companyCode,
	    	Collection<String> productNames) throws RemoteException,SystemException,ProductDefaultsBusinessException;
	/**
	 * @param adviceAsyncHelperVO
	 * @throws RemoteException
	 */
    public void handleAdvice(com.ibsplc.xibase.server.framework.interceptor.vo.AsyncAdviceHelperVO adviceAsyncHelperVO) throws RemoteException;
    /**
     * 
     * @author A-5257
     * @param companyCode
     * @param productNames
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    public Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVOs)
	throws RemoteException,SystemException;
    /**
     * 
     * @author A-5111
     * productSuggestConfigurationvos
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    public void saveProductSuggestConfigurations(Collection<ProductSuggestConfigurationVO> productSuggestConfigurationvos)
	throws RemoteException,SystemException;
    /**
     * 
     * @author A-5111
     * findProductMappings
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    public String findProductMappings(String companyCode,String sccCodes)
	throws RemoteException,SystemException;
    /**
     * 
     * @param companyCode
     * @param sccCodes
     * @param parameter
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public HashMap<String, String> findProductMappings(ProductSuggestionVO productSuggestionVO)
    		throws RemoteException,SystemException;
	    	
    /**
	 * 
	 * @author A-5867
	 * @param ProductFilterVO
	 * @return Collection<ProductVO>
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws RemoteException,SystemException;

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
	throws RemoteException,SystemException;
    /**
     * 
     * @param productModelMappingFilterVO
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) 
     throws RemoteException,SystemException;
    /**
     * 
     * @author A-6843
     * productCommodityGroupMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductCommodityGroupMapping(
			Collection<ProductCommodityGroupMappingVO> productCommodityGroupMappingVOs)
	throws RemoteException,SystemException;  
    /**
     * 
     * @author A-6843
     * productAttributePriorityVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductAttributePriority(
			Collection<ProductAttributePriorityVO> productAttributePriorityVOs)
	throws RemoteException,SystemException; 
    /**
     * 
     * @author A-6843
     * productAttributeMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductAttributeMapping(
			Collection<ProductAttributeMappingVO> productAttributeMappingVOs)
	throws RemoteException,SystemException; 
    /**
     * 
     * @author A-6843
     * productGroupRecommendationMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductGroupRecommendations(
			Collection<ProductGroupRecommendationMappingVO> productGroupRecommendationMappingVOs)
	throws RemoteException,SystemException; 
    /**
     * 
     * @author
     * findProductForMaster
     * @return
     * @throws RemoteException
     * @throws SystemException
     */  
	public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
	throws SystemException,RemoteException;
    /**
     * @author A-4823
     * @param productODMappingVOs
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveAllowedProductsForOD(Collection<ProductODMappingVO> productODMappingVOs)
	throws RemoteException,SystemException; 
  /**
   * 
   * @param companyCode
   * @return
   * @throws RemoteException
   * @throws SystemException
   * @author A-6858
   */
    public Collection<ProductODMappingVO> getProductODMapping(String companyCode)
    	throws RemoteException,SystemException;
		
public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws RemoteException,SystemException;
/**
 * @author A-7740
 * @param companyCode
 * @param ProductCode
 * @param parameterCodes
 * @return
 * @throws SystemException
 * @throws RemoteException
 */
	Map<String,String> findProductParametersByCode(String companyCode,
		String ProductCode,Collection<String> parameterCodes)throws SystemException,RemoteException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.findProductsByParameters
	 *	Added by 	:	A-8146 on 23-Nov-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param parametersAndParValue
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public Collection<ProductVO> findProductsByParameters(String companyCode,
			 Map<String,String> parametersAndParValue)
			throws RemoteException, SystemException;
		;
/**
	 * @author A-9025
	 * @param companyCode
	 * @param productNames
	 * @return
	 * @throws RemoteException
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	Map<String,ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames)
			throws RemoteException,SystemException,ProductDefaultsBusinessException;


	/**
	 * @author A-8130
	 * @param companyCode
	 * @return Collection<ProductParamterVO>
	 * @throws SystemException
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode)
				throws RemoteException, SystemException;
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.findProductPksForProductNames
	 *	Added by 	:	A-8146 on 01-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param productNames
	 *	Parameters	:	@param fromDate
	 *	Parameters	:	@param toDate
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,ProductLovVO>
	 */
	public Map<String, ProductLovVO> findProductPksForProductNames(String companyCode, String productNames,
			LocalDate fromDate, LocalDate toDate) throws RemoteException,SystemException;
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.findProductsRestrictions
	 *	Added by 	:	A-8146 on 01-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param productDetails
	 *	Parameters	:	@param primaryScc
	 *	Parameters	:	@return 
	 *	Return type	: 	Map<String,SubProductVO>
	 */
	public Map<String, SubProductVO> findProductsRestrictions(String companyCode,
			Map<String, ProductLovVO> productDetails, String primaryScc) throws RemoteException,SystemException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.saveProductSelectionRuleMasterDetails
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param productSelectionRuleMasterVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveProductSelectionRuleMasterDetails(Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs) throws RemoteException, SystemException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.listProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 */
	public Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode)  throws RemoteException, SystemException;
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsBI.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param filterConditions
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 */
	public Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule (String companyCode , Map<String, String> filterConditions) throws RemoteException, SystemException;
}
