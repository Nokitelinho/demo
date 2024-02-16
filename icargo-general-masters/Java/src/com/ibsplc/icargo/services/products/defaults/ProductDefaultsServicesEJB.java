/*
 * ProductDefaultsServicesEJB.java Created on Jun 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.products.defaults;

import java.io.Serializable;
import java.rmi.RemoteException;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO;
import com.ibsplc.icargo.business.products.defaults.BookingExistsException;
import com.ibsplc.icargo.business.products.defaults.DuplicateProductExistsException;
import com.ibsplc.icargo.business.products.defaults.InvalidProductException;
import com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI;
import com.ibsplc.icargo.business.products.defaults.ProductDefaultsBusinessException;
import com.ibsplc.icargo.business.products.defaults.ProductDefaultsController;
import com.ibsplc.icargo.business.products.defaults.RateNotDefinedException;
import com.ibsplc.icargo.business.products.defaults.SubProductNotExistingBusinessException;
import com.ibsplc.icargo.business.products.defaults.audit.ProductsDefaultsAuditController;
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
import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.xibase.server.framework.audit.AuditException;
import com.ibsplc.xibase.server.framework.audit.Auditor;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.ejb.AbstractFacadeEJB;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.Flow;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ExecutableFlow;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ReflectionInvoker;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.ReflectionInvokerWithCache;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.SimpleClassFinder;
import com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.SpringAdapterBeanLookup;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

 /**
  * Bean implementation class for Enterprise Bean: ProductDefaultsServices
  *
  * @ejb.bean description="ProductDefaultsServices"
  *           display-name="ProductDefaultsServices"
  *           jndi-name="com.ibsplc.icargo.services.products.defaults.ProductDefaultsServicesHome"
  *           name="ProductDefaultsServices"
  *           type="Stateless"
  *           view-type="remote"
  *           remote-business-interface="com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI"
  *
  * @ejb.transaction type="Supports"
 */
public class ProductDefaultsServicesEJB extends AbstractFacadeEJB
implements ProductDefaultsBI,Auditor{
	private Log log = LogFactory.getLogger("SYSTEM ENVIRONMENT");
	//private ProductDefaultsController productDefaultsController;
	
	
	/**
	 * Find All Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 */
	public Page<ProductVO> findProducts(ProductFilterVO productFilterVo,int displayPage)
		throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","findProducts");
		return new ProductDefaultsController().findProducts(productFilterVo,displayPage);
	}
	/*
	@Override
    public Serializable doFlow(Flow flow) throws BusinessException, SystemException, RemoteException {
           boolean disableMethodCache= Boolean.getBoolean("xibase.eblflow.methodcache.disabled");
	        com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker invoker = 
	                                        ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (disableMethodCache ? ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvoker()))
	                                        : ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvokerWithCache()))));          
	        ExecutableFlow eflow = new ExecutableFlow(flow, invoker,new SimpleClassFinder(), new SpringAdapterBeanLookup());
	        Serializable answer = (Serializable) eflow.execute();                
	        return answer;
    }
*/
	/**
	 * used for creating or modifying a product
	 *
	 * @param productVo coresponding to the product to be deleted
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	public Collection<SubProductVO> saveProductDetails(ProductVO productVo)
	throws SystemException,RemoteException,ProductDefaultsBusinessException{
		try{
			ProductDefaultsController productdefaultsController = (ProductDefaultsController)SpringAdapter.getInstance().getBean("productsdefaultsController");
			return productdefaultsController.saveProductDetails(productVo);
		}catch(BookingExistsException bookingExistsException){
			throw new ProductDefaultsBusinessException(bookingExistsException);
		}catch(DuplicateProductExistsException duplicateProductExistsException){
			throw new ProductDefaultsBusinessException(duplicateProductExistsException);
		}

	}

	/**
	 * Find All Sub Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 */
	public Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","findSubProducts");
		return new ProductDefaultsController().findSubProducts(productFilterVo,displayPage);
	}

	/**
	 * used for  modifying a subproduct
	 *
	 * @param subProductVOs
	 * @throws SystemException
	 */
	public void saveSubProductDetails(Collection<SubProductVO> subProductVOs)
	throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","saveSubProductDetails");
		new ProductDefaultsController().saveSubProductDetails(subProductVOs);

	}

	/**
	 * Used to delete a product. Exception is thrown if active bookings
	 * exist against any of the subproduct created from this product
	 * @param productVo
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	public void deleteProduct(ProductVO productVo)
	throws SystemException,RemoteException,
	ProductDefaultsBusinessException{
		try{
			ProductDefaultsController productdefaultsController = (ProductDefaultsController)SpringAdapter.getInstance().getBean("productsdefaultsController");
			productdefaultsController.deleteProduct(productVo);
		}catch(BookingExistsException bookingExistsException){
			throw new ProductDefaultsBusinessException(bookingExistsException);
		}
	}

	/**
	 * Used to delete a subProduct. Exception is thrown if active bookings
	 * exist against the subproduct
	 * @param subProductVo
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	public void deleteSubProduct(SubProductVO subProductVo)
	throws SystemException,RemoteException,
	ProductDefaultsBusinessException{
		log.entering("ProductDefaultsServicesEJB","deleteSubProduct");
		try{
			new ProductDefaultsController().deleteSubProduct(subProductVo);
		}catch(BookingExistsException bookingExistsException){
			throw new ProductDefaultsBusinessException(bookingExistsException);
		}
		log.exiting("ProductDefaultsServicesEJB","deleteSubProduct");
	}

	/**
	 * Used to validate whether the given productname exists in the
	 * database. If product exists, product code is returned
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @throws SystemException
	 */
	public String validateProductName(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws SystemException,RemoteException{
		return new ProductDefaultsController().validateProductName(companyCode,productName,startDate,endDate);
	}
	/**
	 * @param companyCode
	 * @param productName
	 * @return  Collection<ProductValidationVO>
	 * @throws SystemException
	 */
	public Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
	throws SystemException,RemoteException{
		return new ProductDefaultsController().findProductsByName(companyCode,productName);
	}
	/**
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return Page<ProductLovVO>
	 * @throws SystemException
	 */
	public Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO,int displayPage)
	throws SystemException,RemoteException{
		return new ProductDefaultsController().findProductLov(productLovFilterVO,displayPage);
	}
	
	/**
	 * Used to fetch the details of a particular product
	 * @param companyCode
	 * @param productCode
	 * @return ProductVO
	 * @throws SystemException
	 */
	public ProductVO findProductDetails(String companyCode, String productCode)
	throws SystemException,RemoteException{
		return new ProductDefaultsController().findProductDetails(companyCode,productCode);
	}

	/**
	 * used to fetch the details of a particular sub product
	 * @param subProductVO
	 * @throws SystemException
	 */
	public SubProductVO findSubProductDetails(SubProductVO subProductVO)
	throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","findSubProductDetails");
		return new ProductDefaultsController().findSubProductDetails(subProductVO);
	}

	/**
	 * Used to update the status of  product.Status can be 'Activate'
	 * or 'Inactivate'
	 * @param productVos
	 * @throws SystemException
	 * @throws ProductDefaultsBusinessException
	 */
	public void updateProductStatus(Collection<ProductVO> productVos)
	throws SystemException,RemoteException,ProductDefaultsBusinessException{
		try{
			log.entering("ProductDefaultsServicesEJB","updateProductStatus");
			ProductDefaultsController productdefaultsController = (ProductDefaultsController)SpringAdapter.getInstance().getBean("productsdefaultsController");
			productdefaultsController.updateProductStatus(productVos);
			log.exiting("ProductDefaultsServicesEJB","updateProductStatus");
		}catch(RateNotDefinedException rateNotDefinedException){
			throw new ProductDefaultsBusinessException(rateNotDefinedException);
		}
	}

	/**
	 * Used to update the status of  sub product.Status can be 'Activate'
	 * or 'Inactivate'
	 * @param productVO
	 * @throws SystemException
	 */
	public void updateSubProductStatus(ProductVO productVO)
		throws SystemException,RemoteException
	{
		log.entering("ProductDefaultsServicesEJB","updateSubProductStatus");
		new ProductDefaultsController().updateSubProductStatus(productVO);
		log.exiting("ProductDefaultsServicesEJB","updateSubProductStatus");
	}
	/**
	 * This method implements the audit method in Auditor.
	 * It calls the audit method of the controller.
	 * @param auditVo
	 * @throws AuditException
	 * @return
	 */
	public void audit(AuditVO auditVo) throws AuditException,RemoteException {
		log.log(Log.FINE,"(((((((((((((((((EJB AUDIT))))))))))))))))))))");
		new ProductsDefaultsAuditController().audit((ProductsDefaultsAuditVO)auditVo);
	}

	/**
	 * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
	 * @param productCode
	 * @param transportationMode
	 * @param productPriority
	 * @return List<ProductServiceVO>
	 * @throws SubProductNotExistingBusinessException
	 * @throws SystemException
	 */
	public List<ProductServiceVO> findProductServices
		(String companyCode, String productCode, String transportationMode,
				String productPriority, String primaryScc)
			throws RemoteException,
			SubProductNotExistingBusinessException, SystemException {
		return new ProductDefaultsController()
			.findProductServices(companyCode, productCode, transportationMode, productPriority, primaryScc);
	}

	/**
     * added by A-1648 for capacity-booking submodule
	 * This method finds all restrictions for a subproduct.
     * @param companyCode
     * @param productCode
     * @param transportationMode
     * @param productPriority
     * @param primaryScc
     * @return SubProductVO
     * @throws SubProductNotExistingBusinessException
     * @throws SystemException
     */
    public SubProductVO findProductRestrictions
    	(String companyCode, String productCode, String transportationMode,
			String productPriority, String primaryScc)
    			throws RemoteException,
    			SubProductNotExistingBusinessException, SystemException {
    	return new ProductDefaultsController()
    		.findProductRestrictions(companyCode, productCode, transportationMode, productPriority, primaryScc);
    }
    /**
     * @author A-1885
     */
    public Collection<ProductValidationVO> 
    validateProductForAPeriod(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws SystemException,RemoteException,ProductDefaultsBusinessException{
    	return new ProductDefaultsController().validateProductForAPeriod(companyCode,
    			productName,startDate,endDate);
    }
    /**
     * @author A-1883
     * @param productFeedbackVO
     * @throws SystemException
     * @throws RemoteException
     */
     public void saveProductFeedback(ProductFeedbackVO productFeedbackVO)
 	throws SystemException,RemoteException {
    	 log.entering("ProductsDefaultsServicesEJB","saveProductFeedback()");
    	 new ProductDefaultsController().saveProductFeedback(productFeedbackVO);
    	 log.exiting("ProductsDefaultsServicesEJB","saveProductFeedback()");
     }
     /**
      * @author A-1883
      * @param productFeedbackFilterVO
      * @param displayPage
      * @return Page<ProductFeedbackVO>
      * @throws SystemException
      * @throws RemoteException
      */
     public Page<ProductFeedbackVO> listProductFeedback(
 			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage)
 			throws SystemException,RemoteException {
    	 log.entering("ProductsDefaultsServicesEJB","listProductFeedback()");
    	// findProductsForStock("AV","AWB","S");
    	return  new ProductDefaultsController().listProductFeedback(productFeedbackFilterVO,displayPage);
     }
     /**
 	 * Method for getting ProductEvent
 	 * @author A-1883
 	 * @param companyCode
 	 * @param productCode
 	 * @return List<ProductEventVO>
 	 * @throws SystemException
 	 * @throws RemoteException
 	 */
 	public List<ProductEventVO> findProductEvent(String companyCode, String productCode)
 	throws SystemException,RemoteException {
 		log.entering("ProductsDefaultsServicesEJB","findProductEvent()");
    	return  new ProductDefaultsController().findProductEvent(companyCode,productCode);
 	}
 	/**
 	 * Method for getting ProductEvent
 	 * @author A-1747
 	 * @param productPerformanceFilterVO
 	 * @return Collection<ProductPerformanceVO>
 	 * @throws SystemException
 	 * @throws RemoteException
 	 */
 	public Collection<ProductPerformanceVO> getProductPerformanceDetailsForReport(ProductPerformanceFilterVO productPerformanceFilterVO)
 	throws SystemException,RemoteException {

 		log.entering("ProductsDefaultsServicesEJB","getProductPerformanceDetailsForReport()");
    	return  new ProductDefaultsController().getProductPerformanceDetailsForReport(productPerformanceFilterVO);
 	}
 	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return Boolean
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException, RemoteException {
 		log.entering("ProductsDefaultsServicesEJB","getProductPerformanceDetailsForReport()");
    	return  new ProductDefaultsController().checkStationAvailability(stationAvailabilityFilterVO);
 	}

	/**
	 * Find image of product for the specied filter criteria
	 * @author A-1870
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws SystemException
	 */
	public ProductVO findImage(ProductFilterVO productFilterVo)
		throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","findImage");
		return new ProductDefaultsController().findImage(productFilterVo);
	}
	/**
	 * sending message
	 * @author A-1885
	 * @param messageVo
	 * @return
	 * @throws SystemException
	 */
	public void sendMessageForProduct(MessageVO messageVo)
	throws SystemException,RemoteException{
		log.entering("ProductDefaultsServicesEJB","send message");
		new ProductDefaultsController().sendMessage(messageVo);
	}

     /**
      * Added by A-1945
      * @param companyCode
      * @param productCodes
      * @return Map<String, ProductValidationVO>
      * @throws SystemException
      * @throws RemoteException
      * @throws ProductDefaultsBusinessException
      */
     public Map<String, ProductValidationVO> validateProducts(String companyCode,
                             Collection<String> productCodes )
             throws SystemException,
                    RemoteException,
                    ProductDefaultsBusinessException {
         log.entering("ProductDefaultsServicesEJB", "validateProducts");
         Map<String, ProductValidationVO> validatedVOs =
                 new ProductDefaultsController().validateProducts(
                                      companyCode, productCodes);
         log.exiting("ProductDefaultsServicesEJB", "validateProducts");
         return validatedVOs;
     }
     
     /**
      * @author A-1958
      * @param companyCode
      * @param products
      * @return Collection<String>
      * @throws RemoteException
      * @throws SystemException
      */
     public Collection<String> findSccCodesForProducts(String companyCode, Collection<String> products) 
     throws RemoteException,SystemException {
    	 log.entering("ProductDefaultsServicesEJB","findSccCodesForProducts");
    	 return new ProductDefaultsController().findSccCodesForProducts(companyCode, products);
     }
     /**
      * 
      * @param companyCode
      * @param productName
      * @param shpgDate
      * @return
      * @throws RemoteException
      * @throws SystemException
      */
     public String validateProduct(String companyCode, String productName, LocalDate startDate,LocalDate endDate)
     throws RemoteException,SystemException {
    	 log.entering("ProductDefaultsServicesEJB","validateProduct");
    	 return new ProductDefaultsController().validateProduct(companyCode, productName, startDate, endDate);
     }
     
     /**
     *	Overriding Method	:	@see com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI#validateProductsForListing(com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO)
     *	Added by 			: A-8041 on 02-Nov-2017
     * 	Used for 	:	validateProductsForListing
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException 
     */
    public Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO)
    	     throws RemoteException,SystemException {
    	    	 log.entering("ProductDefaultsServicesEJB","validateProductsForListing");
    	    	 return new ProductDefaultsController().validateProductsForListing(productFilterVO);
    	     }
     /**
      * @author a-1885
      */
     public Collection<ProductStockVO> findProductsForStock(String companyCode,
    		 String documentType,String documentSubType)throws RemoteException,
    		 SystemException{
    	 log.entering("ProductDefaultsServicesEJB","findProductsForStock");
    	 Collection<ProductStockVO> stocks = new ProductDefaultsController().findProductsForStock
    	 (companyCode,documentType,documentSubType);
    	 log.log(Log.FINE, "-------findProductsForStock-------", stocks);
		return stocks;
     }
     /**
      * @author a-1885
      */
     public Collection<ProductEventVO> findSubproductEventsForTracking
     (String companyCode,String productCode,String productScc,String 
     		productTransportMode,String productPriority)throws SystemException,
     		RemoteException{
    	 return new ProductDefaultsController().findSubproductEventsForTracking
    	 (companyCode,productCode,productScc,productTransportMode,
    			 productPriority);
     }
     /**
      * @author a-1885
      * companyCode
      * productCode
      * docType
      * docSubType
      */
     public String validateProductForDocType(String companyCode,String productCode,
     		String docType,String docSubType)throws SystemException,
     		ProductDefaultsBusinessException,RemoteException{
    	 log.log(Log.FINE,"---EJB----validateProductForDocType---");
    	 return new ProductDefaultsController().validateProductForDocType
    	 (companyCode,productCode,docType,docSubType);
     }
     /**
      * @author a-1885
      * 
      */
     public ProductLovVO findProductPkForProductName(String companyCode,
    		 String productName,LocalDate startDate,LocalDate endDate)
     throws SystemException,RemoteException{
    	 log.log(Log.FINE,"---EJB----findProductPkForProductName---");
    	 return new ProductDefaultsController().findProductPkForProductName
    	 (companyCode,productName,startDate,endDate);
     }
     /**
      * @author a-4823
      * @param companyCode
      * @param productNames
      * @return
      * @throws RemoteException
      * @throws SystemException
      * @throws ProductDefaultsBusinessException
      */
     public Map<String,ProductVO> validateProductNames(String companyCode,
    		 Collection<String> productNames) throws RemoteException, 
    		 SystemException,ProductDefaultsBusinessException{

    	 try {
    		 return new ProductDefaultsController().validateProductNames(companyCode,
    				 productNames);
    	 } catch (InvalidProductException e) {				
    		 throw new ProductDefaultsBusinessException(e);
    	 } 			

     }
     /**
      * 
      * @author A-5257
      * @param companyCode
      * @param productFilterVOs
      * @return
      * @throws SystemException
      * @throws PersistenceException
      */
     public Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVOs)
		throws RemoteException,SystemException{	
    		 return new ProductDefaultsController().findPriorityForProducts(productFilterVOs);    		
     }
     /**
      * 
      * @author A-5111
      * @param productSuggestConfigurationvos 
      * @return
      * @throws RemoteException
      * @throws SystemException
      * @throws ProductDefaultsBusinessException
      */
     public void saveProductSuggestConfigurations(Collection<ProductSuggestConfigurationVO> productSuggestConfigurationvos)
			throws RemoteException, SystemException {
    	 log.entering("ProductDefaultsServicesEJB","saveProductSuggestConfigurations");
    	   new ProductDefaultsController().saveProductSuggestConfigurations(productSuggestConfigurationvos);
	}
     /**
      * 
      * @author A-5111
      * @param  companyCode
      * @param  sccCodes
      * @return
      * @throws RemoteException
      * @throws SystemException
      * @throws ProductDefaultsBusinessException
      */
     public String findProductMappings(String companyCode,String sccCodes) throws SystemException {
 		log.entering("ProductDefaultsServicesEJB"," Inside findProductMappings");
 		String product= new ProductDefaultsController().findProductMappings(companyCode, sccCodes);
 		log.exiting("ProductDefaultsServicesEJB"," Inside findProductMappings");
 		return product;
     }
     /**
     * @param  companyCode
      * @param  sccCodes
      * @return
      * @throws RemoteException
      * @throws SystemException
      * @throws ProductDefaultsBusinessException
      */
     public HashMap<String, String> findProductMappings(ProductSuggestionVO productSuggestionVO) throws RemoteException,SystemException{
    	log.entering("ProductDefaultsServicesEJB"," Inside findProductMappings");
  		HashMap<String, String> productMap= new ProductDefaultsController().findProductMappings(productSuggestionVO);
  		log.exiting("ProductDefaultsServicesEJB"," Inside findProductMappings");
  		return productMap;
     }
     
     /**
 	 * 
 	 * @author A-5867
 	 * @param ProductFilterVO
 	 * @return Collection<ProductVO>
 	 * @throws RemoteException
 	 * @throws SystemException
 	 */
 	public Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws RemoteException,SystemException{	
		 return new ProductDefaultsController().findBookableProducts(productFilterVO);    		
 	}
 	
 	/**
	 * 
	 * @author A-5867
	 * @param companyCode
	 * @param parameterCode
	 * @param parameterValue
	 * @return 
	 * @throws SystemException
	 */
    public HashMap<String, String> findSuggestedProducts(String companyCode,String parameterCode,String parameterValue) throws SystemException {
		log.entering("ProductDefaultsServicesEJB"," Inside findSuggestedProducts");
		HashMap<String, String> productMap= new ProductDefaultsController().findSuggestedProducts(companyCode,parameterCode,parameterValue);
		log.exiting("ProductDefaultsServicesEJB"," Inside findSuggestedProducts");
		return productMap;
    }
    /**
     * 
     * @author A-6843
     * @param productCommodityGroupMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductCommodityGroupMapping(
			Collection<ProductCommodityGroupMappingVO> productCommodityGroupMappingVOs)
			throws RemoteException, SystemException {
    	log.entering("ProductDefaultsServicesEJB","saveProductCommodityGroupMapping");
    	new ProductDefaultsController().saveProductCommodityGroupMapping(productCommodityGroupMappingVOs);
    }
    /**
     * 
     * @author A-6843
     * @param productAttributePriorityVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductAttributePriority(
			Collection<ProductAttributePriorityVO> productAttributePriorityVOs)
			throws RemoteException, SystemException {
    	log.entering("ProductDefaultsServicesEJB","saveProductAttributePriority");
    	new ProductDefaultsController().saveProductAttributePriority(productAttributePriorityVOs);
    }
    /**
     * 
     * @author A-6843
     * @param productAttributeMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductAttributeMapping(
			Collection<ProductAttributeMappingVO> productAttributeMappingVOs)
			throws RemoteException, SystemException {
    	log.entering("ProductDefaultsServicesEJB","saveProductAttributeMapping");
    	new ProductDefaultsController().saveProductAttributeMapping(productAttributeMappingVOs);
    }
    /**
     * 
     * @author A-6843
     * @param productGroupRecommendationMappingVOs
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveProductGroupRecommendations(
			Collection<ProductGroupRecommendationMappingVO> productGroupRecommendationMappingVOs)
			throws RemoteException, SystemException {
    	log.entering("ProductDefaultsServicesEJB","saveProductGroupRecommendations");
    	new ProductDefaultsController().saveProductGroupRecommendations(productGroupRecommendationMappingVOs);
    }
    public Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) 
            throws RemoteException, SystemException{
       return new ProductDefaultsController().getProductModelMapping(productModelMappingFilterVO);
     }
    /**
	 * @param productLovFilterVO
	 * @return Collection<ProductLovVO>
	 * @throws SystemException
	 */
	public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
	throws SystemException,RemoteException{
		return new ProductDefaultsController().findProductForMaster(productLovFilterVO);
	}
	    
    /**
     * @author A-4823
     * @param productODMappingVOs
     * @throws RemoteException
     * @throws SystemException
     */
    public void saveAllowedProductsForOD(Collection<ProductODMappingVO> productODMappingVOs)
			throws RemoteException, SystemException{
		log.entering("ProductsDefaultsDelegate",
				"saveAllowedProductsForOD");
		new ProductDefaultsController().saveAllowedProductsForOD(productODMappingVOs);
     }
    /**
     * 
     * @param compnayCode
     * @return
     * @throws RemoteException
     * @throws SystemException
     */
    public Collection<ProductODMappingVO> getProductODMapping(String compnayCode) 
       throws RemoteException, SystemException{
    	return new ProductDefaultsController().getProductODMapping(compnayCode);
    }
    /**
     * 
     *	Overriding Method	:	@see com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI#findProducts(com.ibsplc.icargo.business.products.defaults.vo.ProductFilterVO)
     *	Added by 			: A-7548 on 19-Jan-2018
     * 	Used for 	:
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws RemoteException
     *	Parameters	:	@throws SystemException
     */
    public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws RemoteException,SystemException{
    	 new ProductDefaultsController().findProducts(productFilterVO,1);
    	 return null;
    }
    /**
	 * @author A-7740
	 * @param companyCode
	 * @param ProductCode
	 * @param parameterCodes
	 * @return
	 * @throws SystemException
	 * @throws RemoteException
	 */
	public Map<String, String> findProductParametersByCode(String companyCode,
			String ProductCode, Collection<String> parameterCodes)
			throws SystemException, RemoteException {
		return new ProductDefaultsController().findProductParametersByCode(companyCode,
				ProductCode, parameterCodes);
    }
	/**
	 * 
	 * 	Method		:	ProductDefaultsServicesEJB.findProductsByParameters
	 *	Added by 	:	A-8146 on 23-Nov-2018
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param parametersAndParValue
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductVO>
	 */
	public Collection<ProductVO> findProductsByParameters(String companyCode,
			 Map<String,String> parametersAndParValue)
			throws SystemException, RemoteException {
		return new ProductDefaultsController().findProductsByParameters(companyCode,parametersAndParValue);
	}
     /**
     * @author A-9025
     * @param companyCode
     * @param productNames
     * @return
     * @throws RemoteException
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    public Map<String,ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames) 
    		throws RemoteException, SystemException, ProductDefaultsBusinessException {
    	try {
    		return new ProductDefaultsController().validateProductsByNames(companyCode, productNames);
    	} catch (InvalidProductException e) {
    		throw new ProductDefaultsBusinessException(e);
    	} 			
    }

	/**
	 * @author A-8130
	 * @param companyCode
	 * @return Collection<ProductParamterVO>
	 * @throws SystemException
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode)
			throws SystemException, RemoteException {
		return new ProductDefaultsController().findAllProductParameters(companyCode);
	 }
	/**
	 * 
	 * 	Method		:	ProductDefaultsServicesEJB.findProductPksForProductNames
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
			LocalDate fromDate, LocalDate toDate) throws SystemException,RemoteException {
		return new ProductDefaultsController().findProductPksForProductNames(companyCode, productNames, fromDate,
				toDate);
	}
	/**
	 * 
	 * 	Method		:	ProductDefaultsServicesEJB.findProductsRestrictions
	 *	Added by 	:	A-8146 on 01-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param productDetails
	 *	Parameters	:	@param primaryScc
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,SubProductVO>
	 */
	public Map<String, SubProductVO> findProductsRestrictions(String companyCode,
			Map<String, ProductLovVO> productDetails, String primaryScc) throws SystemException,RemoteException {
		return new ProductDefaultsController().findProductsRestrictions(companyCode, productDetails, primaryScc);
	 }
     /**
      *
      * @param flow
      * @return
      * @throws BusinessException
      * @throws SystemException
      * @throws RemoteException
      */
     @Override
     public Serializable doFlow(Flow flow) throws BusinessException, SystemException, RemoteException {
         boolean disableMethodCache= Boolean.getBoolean("xibase.eblflow.methodcache.disabled");
         com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker invoker =
                 ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (disableMethodCache ? ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvoker()))
                         : ((com.ibsplc.xibase.server.framework.requestprocessor.eblflow.execute.Invoker) (new ReflectionInvokerWithCache()))));
         ExecutableFlow eflow = new ExecutableFlow(flow, invoker,new SimpleClassFinder(), new SpringAdapterBeanLookup());
         Serializable answer = (Serializable) eflow.execute();
         return answer;
     }
     
     /**
      * 
      *	Overriding Method	:	@see com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI#saveProductSelectionRuleMasterDetails(java.util.Collection)
      *	Added by 			: 	Prashant Behera on Jun 29, 2022
      * 	Used for 	:
      *	Parameters	:	@param productSelectionRuleMasterVOs
      *	Parameters	:	@throws SystemException
      */
	@Override
	public void saveProductSelectionRuleMasterDetails(
			Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs) throws RemoteException,SystemException {
		new ProductDefaultsController().saveProductSelectionRuleMasterDetails(productSelectionRuleMasterVOs);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI#listProductSelectionRuleMaster()
	 *	Added by 			:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode)
			throws RemoteException, SystemException {
		return new ProductDefaultsController().listProductSelectionRuleMaster(companyCode);
	}
	
	/**
	 * 
	 *	Overriding Method	:	@see com.ibsplc.icargo.business.products.defaults.ProductDefaultsBI#findProductsForBookingFromProductSelectionRule(java.lang.String, java.util.Map)
	 *	Added by 			: A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param filterConditions
	 *	Parameters	:	@return
	 *	Parameters	:	@throws RemoteException
	 *	Parameters	:	@throws SystemException
	 */
	@Override
	public Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule(String companyCode,
			Map<String, String> filterConditions) throws RemoteException, SystemException {
		return new ProductDefaultsController().findProductsForBookingFromProductSelectionRule(companyCode,filterConditions);
	}
    
 }
