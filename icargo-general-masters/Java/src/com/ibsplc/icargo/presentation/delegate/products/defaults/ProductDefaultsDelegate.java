/*
 * ProductDefaultsDelegate.java Created on Jun 29, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.presentation.delegate.products.defaults;




import java.util.Collection;
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
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductParamterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestConfigurationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.xibase.client.framework.delegate.Action;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegate;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * ProductDefaultDelegate class
 * @author
 *
 */
@Module("products")
@SubModule("defaults")
public class ProductDefaultsDelegate extends BusinessDelegate {

	private Log log = LogFactory.getLogger("PRODUCTS");
	/**
	 * Find All Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws BusinessDelegateException
	 */
	@Action("findProducts")
	public Page<ProductVO> findProducts(ProductFilterVO productFilterVo,int displayPage)
	throws BusinessDelegateException {
		return despatchRequest("findProducts",productFilterVo,displayPage);
	}

	/**
	 * Find All Sub Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws BusinessDelegateException
	 */
	@Action("findSubProducts")
	public Page findSubProducts(ProductFilterVO productFilterVo,int displayPage)
	throws BusinessDelegateException {
		return despatchRequest("findSubProducts",productFilterVo,displayPage);
	}

	/**
	 * used for  modifying a subproduct
	 * @param productVo
	 * @return Collection<SubProductVO>
	 * @throws BusinessDelegateException
	 */
	@Action("saveProductDetails")
	public Collection<SubProductVO> saveProductDetails(ProductVO productVo)
	throws BusinessDelegateException {
		return despatchRequest("saveProductDetails",productVo);
	}

	/**
	 * used for creating or modifying a subproduct
	 * @param subProductVos coresponding to the product to be deleted
	 * @throws BusinessDelegateException
	 */
	@Action("saveSubProductDetails")
	public void saveSubProductDetails(Collection<SubProductVO> subProductVos)
	throws BusinessDelegateException {
		despatchRequest("saveSubProductDetails",subProductVos);
	}

	/**
	 * Used to delete a product. Exception is thrown if active bookings exist against
	 * any of the subproduct created from this product
	 * @param productVo
	 * @throws BusinessDelegateException
	 */
	@Action("deleteProduct")
	public void deleteProduct(ProductVO productVo)
	throws BusinessDelegateException {
		despatchRequest("deleteProduct",productVo);
	}

	/**
	 * Used to delete a subProduct. Exception is thrown if active bookings exist against
	 * the subproduct
	 * @param subProductVo
	 * @throws BusinessDelegateException
	 */
	@Action("deleteSubProduct")
	public void deleteSubProduct(SubProductVO subProductVo)
	throws BusinessDelegateException {
		despatchRequest("deleteSubProduct",subProductVo);
	}

	/**
	 * Used to validate whether the given productname exists in the
	 * database. If product exists, product code is returned
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return productCode
	 * @throws BusinessDelegateException
	 */
	@Action("validateProductName")
	public String validateProductName(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws BusinessDelegateException {
		return despatchRequest("validateProductName",companyCode,productName,startDate,endDate);
	}

	/**
	 * Used to validate whether the given productname exists in the
	 * database. If product exists, product code is returned
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return productCode
	 * @throws BusinessDelegateException
	 */
	public Collection<ProductValidationVO> 
	validateProductForAPeriod(String companyCode, String productName,LocalDate startDate,LocalDate endDate)
	throws BusinessDelegateException {
		return despatchRequest("validateProductForAPeriod",companyCode,productName,startDate,endDate);
	}
	/**
	 * This will check for any duplicate product
	 * @param productName
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessDelegateException
	 */
	/*
	 @Action("checkDuplicate")
	 public boolean checkDuplicate(String productName,String companyCode,LocalDate startDate,LocalDate endDate)
	 throws BusinessDelegateException {
	 return despatchRequest("checkDuplicate",productName,companyCode,startDate,endDate);
	 }
	 */

	/**
	 * This method will find a collection of products with same name
	 * @param companyCode
	 * @param productName
	 * @return
	 * @throws BusinessDelegateException
	 */
	@Action("findProductsByName")
	public Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
	throws BusinessDelegateException {
		return despatchRequest("findProductsByName",companyCode,productName);
	}
	/**
	 *
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return
	 * @throws BusinessDelegateException
	 */
	@Action("findProductLov")
	public Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO,int displayPage)
	throws BusinessDelegateException {
		return despatchRequest("findProductLov",productLovFilterVO,displayPage);
	}
	/**
	 *  Used to fetch the details of a particular product
	 * @param companyCode
	 * @param productCode
	 * @return ProductVO
	 * @throws BusinessDelegateException
	 */
	@Action("findProductDetails")
	public ProductVO findProductDetails(String companyCode, String productCode)
	throws BusinessDelegateException {
		return despatchRequest("findProductDetails",companyCode,productCode);
	}
	/**
	 * Used to fetch the details of a particular subproduct
	 * @param subProductVO
	 * @return SubProductVO
	 * @throws BusinessDelegateException
	 */
	@Action("findSubProductDetails")
	public SubProductVO findSubProductDetails(SubProductVO subProductVO) throws BusinessDelegateException {
		return despatchRequest("findSubProductDetails",subProductVO);
	}

	/**
	 * Used to update the status of product.Status can be 'Activate' or 'Inactivate'
	 * @param productVos
	 * @throws BusinessDelegateException
	 */
	@Action("updateProductStatus")
	public void updateProductStatus(Collection<ProductVO> productVos )
	throws BusinessDelegateException {
		despatchRequest("updateProductStatus",productVos);
	}

	/**
	 * Used to update the status of subproduct.Status can be 'Activate' or 'Inactivate'
	 * @param productVO
	 * @throws BusinessDelegateException
	 */
	@Action("updateSubProductStatus")
	public void updateSubProductStatus(ProductVO productVO )
	throws BusinessDelegateException {
		despatchRequest("updateSubProductStatus",productVO);
	}

	/**
	 * added by A-1648 for capacity-booking submodule
	 * This method finds the services for a product.
	 * @param productCode
	 * @param transportationMode
	 * @param productPriority
	 * @return List<ProductServiceVO>
	 * @throws BusinessDelegateException
	 */
	public List<ProductServiceVO> findProductServices
		(String companyCode, String productCode, String transportationMode,
				String productPriority, String primaryScc)
			throws BusinessDelegateException {
		return despatchRequest("findProductServices",companyCode, productCode, transportationMode, productPriority, primaryScc);
	}
	/**
	 * @author A-1883
	 * @param productFeedbackVO
	 * @throws BusinessDelegateException
	 */
	public void saveProductFeedback(ProductFeedbackVO productFeedbackVO)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate","saveProductFeedback()");
		despatchRequest("saveProductFeedback",productFeedbackVO);
		log.exiting("ProductsDefaultsDelegate","saveProductFeedback()");
	}
	/**
	 * @author A-1883
	 * @param productFeedbackFilterVO
	 * @param displayPage
	 * @return Page<ProductFeedbackVO>
	 * @throws BusinessDelegateException
	 */
	public Page<ProductFeedbackVO> listProductFeedback(
			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate","listProductFeedback()");
		return despatchRequest("listProductFeedback",productFeedbackFilterVO,displayPage);
	}
	/**
	 * @author A-1747
	 * @param productPerformanceFilterVO
	 * @return Collection<ProductPerformanceVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ProductPerformanceVO> getProductPerformanceDetailsForReport(ProductPerformanceFilterVO productPerformanceFilterVO)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate","getProductPerformanceDetailsForReport()");
		return despatchRequest("getProductPerformanceDetailsForReport",productPerformanceFilterVO);
	}
	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return Boolean
	 * @throws BusinessDelegateException
	 */
	public Boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate","checkStationAvailability()");
		return despatchRequest("checkStationAvailability",stationAvailabilityFilterVO);
	}

	/**
	 * @author A-1870
	 * @param productFilterVO
	 * @return ProductVO
	 * @throws BusinessDelegateException
	 */
	public ProductVO findImage(ProductFilterVO productFilterVO)
	throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate","findImage()");
		return despatchRequest("findImage",productFilterVO);
	}
	/**
	 * @author A-1885
	 * @param messageVo
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void sendMessageForProduct(MessageVO messageVo)throws BusinessDelegateException{
		log.entering("ProductsDefaultsDelegate","send message for product");
		despatchRequest("sendMessageForProduct",messageVo);
	}
	/**
	 * @author a-1885
	 * @param companyCode
	 * @param documentType
	 * @param documentSubType
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Collection<ProductStockVO> findProductsForStock(String companyCode,
			String documentType,String documentSubType)throws BusinessDelegateException{
		log.entering("ProductsDefaultsDelegate","findProductsForStock");
		return despatchRequest("findProductsForStock",companyCode,documentType,
				documentSubType);
	}
	/**
	 * @author a-1885
	 * @param companyCode
	 * @param productCode
	 * @param productScc
	 * @param productTransportMode
	 * @param productPriority
	 * @return
	 * @throws BusinessDelegateException
	 */
	 public Collection<ProductEventVO> findSubproductEventsForTracking
     (String companyCode,String productCode,String productScc,String
     		productTransportMode,String productPriority)throws
     BusinessDelegateException{
		 log.entering("ProductsDefaultsDelegate","findSubproductEventsForTracking");
		 return despatchRequest("findSubproductEventsForTracking",
				 companyCode,productCode,productScc,productTransportMode,
				 productPriority);
	 }
	 /**
	  * @author a-1885
	  * @param companyCode
	  * @param productCode
	  * @param docType
	  * @param docSubType
	  * @return
	  * @throws BusinessDelegateException
	  */
	 public String validateProductForDocType(String companyCode,String productCode,
	     		String docType,String docSubType)throws BusinessDelegateException{
		 log.entering("ProductsDefaultsDelegate","validateProductForDocType");
		 return despatchRequest("validateProductForDocType",companyCode,
				 productCode,docType,docSubType);
	 }
	/**
	 * @author a-1885
	 * @param companyCode
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws BusinessDelegateException
	 */
	 public ProductLovVO findProductPkForProductName(String companyCode,
			 String productName,LocalDate startDate,LocalDate endDate)throws
			 BusinessDelegateException{
		 log.entering("ProductsDefaultsDelegate","findProductPkForProductName");
		 return despatchRequest("findProductPkForProductName",companyCode,productName,
				 startDate,endDate);
	 }
	/**
	 * @author a-5111
	 * @param Collection
	 *            <ProductSuggestConfigurationVO>
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProductSuggestConfigurations(
			Collection<ProductSuggestConfigurationVO> productSuggestConfigurationvos)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveProductSuggestConfiguration");
		despatchRequest("saveProductSuggestConfigurations",
				productSuggestConfigurationvos);
	 }
	/**
	 * @author a-6843
	 * @param Collection
	 *            <ProductCommodityGroupMappingVO>
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProductCommodityGroupMapping(
			Collection<ProductCommodityGroupMappingVO> productCommodityGroupMappingVOs)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveProductCommodityGroupMapping");
		despatchRequest("saveProductCommodityGroupMapping",
				productCommodityGroupMappingVOs);
	 }
	/**
	 * @author a-6843
	 * @param Collection
	 *            <ProductAttributePriorityVO>
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProductAttributePriority(
			Collection<ProductAttributePriorityVO> productAttributePriorityVOs)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveProductAttributePriority");
		despatchRequest("saveProductAttributePriority",
				productAttributePriorityVOs);
	 }
	/**
	 * @author a-6843
	 * @param Collection
	 *           <ProductAttributeMappingVO>
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProductAttributeMapping(
			Collection<ProductAttributeMappingVO> productAttributeMappingVOs)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveProductAttributeMapping");
		despatchRequest("saveProductAttributeMapping",
				productAttributeMappingVOs);
	 }
	/**
	 * @author a-6843
	 * @param Collection
	 *            <ProductGroupRecommendationMappingVO>
	 * @return
	 * @throws BusinessDelegateException
	 */
	public void saveProductGroupRecommendations(
			Collection<ProductGroupRecommendationMappingVO> productGroupRecommendationMappingVOs)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveProductGroupRecommendations");
		despatchRequest("saveProductGroupRecommendations",
				productGroupRecommendationMappingVOs);
	}
	/**
	 * @author A-4823
	 * @param productODMappingVOs
	 * @throws BusinessDelegateException
	 */
	public void saveAllowedProductsForOD(Collection<ProductODMappingVO> productODMappingVOs)
			throws BusinessDelegateException {
		log.entering("ProductsDefaultsDelegate",
				"saveAllowedProductsForOD");
		despatchRequest("saveAllowedProductsForOD",
				productODMappingVOs);
	 }
	/**
	 * @author A-7740
	 * @param companyCode
	 * @param ProductCode
	 * @param parameterCodes
	 * @return
	 * @throws BusinessDelegateException
	 */
	public Map<String, String> findProductParametersByCode(String companyCode,
			String ProductCode, Collection<String> parameterCodes)
			throws BusinessDelegateException {
		return despatchRequest("findProductParametersByCode", companyCode,
				ProductCode, parameterCodes);
	 }
	/**
	 * @author A-8130
	 * @param companyCode
	 * @return Collection<ProductParamterVO>
	 * @throws BusinessDelegateException
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode)
			throws BusinessDelegateException {
		return despatchRequest("findAllProductParameters", companyCode);
	 }
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsDelegate.saveProductSelectionRuleMasterDetails
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param productSelectionRuleMasterVOs
	 *	Parameters	:	@throws BusinessDelegateException 
	 *	Return type	: 	void
	 */
	public void saveProductSelectionRuleMasterDetails(
			Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs) 	throws BusinessDelegateException {
		 despatchRequest("saveProductSelectionRuleMasterDetails", productSelectionRuleMasterVOs);
	 }
}
