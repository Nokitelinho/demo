/*
 * ProductDefaultsController.java Created on Jun 27, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ProductPerformanceVO;
import com.ibsplc.icargo.business.products.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedAgentProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedMasterGroupingProxy;
import com.ibsplc.icargo.business.products.defaults.proxy.SharedSCCProxy;
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
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSelectionRuleMasterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductStockVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestConfigurationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSuggestionVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductsDefaultsAuditVO;
import com.ibsplc.icargo.business.products.defaults.vo.StationAvailabilityFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.SubProductVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParametersVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.BeanHelper;
import com.ibsplc.icargo.framework.util.template.TemplateEncoderUtil;
import com.ibsplc.icargo.framework.util.template.TemplateRenderingException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtils;
import com.ibsplc.xibase.server.framework.audit.vo.AuditAction;
import com.ibsplc.xibase.server.framework.audit.vo.AuditVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.Advice;
import com.ibsplc.xibase.server.framework.interceptor.Phase;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-1358
 *
 */
@Module("products")
@SubModule("defaults")
public class ProductDefaultsController {
	
	private Log log = LogFactory.getLogger("PRODUCT DEFAULT CONTROLLER");
	public static final String PRODUCT_WITHSAMEPRIORITY_EXISTS = "products.defaults.productexistwithsamepriority";
	private static final String EMPTY_SPACE="";
	private static final String FLAGVALUE_NO = "N";
	private static final String ONETIME_PRODUCT_DESCRIPTION = "products.defaults.productdescription";
	private static final String ONETIME_PRODUCT_PRIORITY = "products.defaults.priority";
	private static final String COMMMODITY_GROUP = "CMYGRP";
	private static final String PRODUCT_GROUP = "PRDCODGRP";
	private static final String INVALID_PRODUCT_NAME = "products.defaults.invalidproductnamegiven";
	private static final String INVALID_UPSELLING_PRODUCT_NAME = "products.defaults.invalidupsellingproductgiven";	
	private static final String INVALID_COMMODITY_GROUP = "products.defaults.invalidcommoditygroup";
	private static final String INVALID_PRODUCT_DESCRIPTION = "products.defaults.invalidproductdescription";
	private static final String INVALID_COMMODITY_CODE = "products.defaults.commoditycodenotfound";
	private static final String INVALID_PRODUCT_GROUP = "products.defaults.invalidproductgroupgiven";
	private static final String INVALID_PRODUCT_PRIORITY = "products.defaults.invalidproductpriority";
	private static final String INVALID_PRIORITY_NUMBER = "products.defaults.invalidprioritynumberforproductdescription";
	private static final String DUPLICATE_PRODUCT_COMMODITY_PAIR = "products.defaults.duplicateproductcommoditygroupexists";
	private static final String DUPLICATE_PRODUCT_DESCRIPTION = "products.defaults.duplicateproductdescriptionexists";
	private static final String DUPLICATE_PRODUCT_NAME = "products.defaults.duplicateproductnamesexists";
	private static final String DUPLICATE_COMMODITY_CODE = "products.defaults.duplicatecommoditycodeexists";
	private static final String INVALID_FLAG_FOR_PRODUCT_NAME = "products.defaults.invalidflagforproductname";
	private static final String INVALID_FLAG_FOR_COMMODITY_CODE = "products.defaults.invalidflagforcommoditycode";
	private static final String INVALID_PRIORITY_FOR_EMPTY_PRODUCT_GROUP = "products.defaults.invalidpriorityforemptyproductgroup";
	private static final String INVALID_BOOKABLE_AS = "products.defaults.invalidbookableasvalue";
	private static final String INVALID_CONSOL_STATUS = "products.defaults.invalidconsolstatus";
	private static final String PRDCOD = "PRDCOD";
	private static final String AGTCOD = "AGTCOD";
	private static final String DSTCNTCOD = "DSTCNTCOD";
	private static final String ORGCNTCOD = "ORGCNTCOD";
	private static final String COMCOD = "COMCOD";
	private static final String SCCCOD = "SCCCOD";
	/**
	 * Find All Products for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 */
	public Page<ProductVO> findProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException{
		log.entering("ProductDefaultsController","findProducts");
		return Product.findProducts(productFilterVo,displayPage);
	}

	/**
	 * Find All Sub Products for the specified filter criteria
	 *
	 * @param productFilterVo filter values
	 * @param displayPage
	 * @return Page Object wrapping collection of ProductVO
	 * @throws SystemException
	 */
	public Page<SubProductVO> findSubProducts(ProductFilterVO productFilterVo,
			int displayPage) throws SystemException{
		log.entering("ProductDefaultsController","findSubProducts");
		return Product.findSubProducts(productFilterVo,displayPage);
	}

	/**
	 * used for creating/modifying or modifying a product based on operation flag
	 *
	 * @param productVo coresponding to the product to be deleted
	 * @return Collection<SubProductVO>
	 * @throws SystemException
	 * @throws DuplicateProductExistsException
	 * @throws BookingExistsException
	 * @throws FinderException 
	 */
	@Advice(name = "products.defaults.saveProductDetails" , phase=Phase.POST_INVOKE) 
	public Collection<SubProductVO> saveProductDetails(ProductVO productVo)
	throws SystemException,DuplicateProductExistsException,BookingExistsException {
		ProductsDefaultsAuditVO productsDefaultsAuditVo = new ProductsDefaultsAuditVO(
		    	ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
		    	ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
				//Check if product with same priority exist
				//Added for ICRD_350746 : START
			if("A".equals(productVo.getStatus()) &&
				(productVo.getPrdPriority()!=null && productVo.getPrdPriority().trim().length()>0
						&& Integer.parseInt(productVo.getPrdPriority()) >0)){
					String productCode = Product.checkIfDuplicatePrdPriorityExists(productVo);
					if(productCode!=null && productCode.trim().length()>0){
						//throw new SystemException()
						String[] errors = new String[1];
						errors[0] = productCode;
			            throw new SystemException(PRODUCT_WITHSAMEPRIORITY_EXISTS,errors);
					}
					//Added for ICRD_350746 : END
				}
			if(productVo.getOperationFlag().equals(ProductVO.OPERATION_FLAG_INSERT)){
				Product.checkDuplicate(productVo.getProductName(),productVo.getCompanyCode(),productVo.getStartDate(),productVo.getEndDate());
				Product product=new Product(productVo);
				log.log(Log.FINE,"---------Going for audit after save-------");
				productsDefaultsAuditVo=(ProductsDefaultsAuditVO)AuditUtils.
	    		populateAuditDetails(productsDefaultsAuditVo,product,true);
				auditProductDetails(productVo,product,ProductsDefaultsAuditVO
						.PRODUCT_CREATE,productsDefaultsAuditVo);
				//Added by A-5210 for setting Product Code in message
				productVo.setProductCode(product.getProductPk().getProductCode());
			    return new ArrayList<SubProductVO>();
			}
			else if(productVo.getOperationFlag().equals(ProductVO.OPERATION_FLAG_UPDATE)){
				removeDuplicateTramodPriorityScc(productVo);
				Product.checkDuplicateForModify(productVo.getProductName(),productVo.getCompanyCode(),productVo.getProductCode(),productVo.getStartDate(),productVo.getEndDate());
				Product productUpdate=Product.find(productVo.getCompanyCode(),productVo.getProductCode());
				Collection<SubProductVO> subProductList=null;
				productsDefaultsAuditVo = setAuditOperationalShipment(productUpdate, productsDefaultsAuditVo);
				try{
					productUpdate.setLastUpdateTime(productVo.getLastUpdateDate().toCalendar());
					subProductList=productUpdate.update(productVo);
					productsDefaultsAuditVo = setAuditOperationalShipment(productUpdate, productsDefaultsAuditVo);
					auditProductDetails(productVo,productUpdate
							,ProductsDefaultsAuditVO.PRODUCT_UPDATE,
							productsDefaultsAuditVo);
				}catch(BookingExistsException bookingExistsException){
					for(ErrorVO vo: bookingExistsException.getErrors()){
						vo.setErrorCode("products.defaults.cannotmodify.bookingexists");
					}
					throw bookingExistsException;
				}
				// Added by A-5277 for setting operational Flag U for the mandatory child in the message - PRDMST
				if(productVo.getTransportMode()!=null && productVo
						.getTransportMode().size()>0){
					for(ProductTransportModeVO transModes : productVo.getTransportMode()){
						if(transModes.getOperationFlag()==null){
							transModes.setOperationFlag(ProductTransportModeVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
				if(productVo.getPriority()!=null && productVo
						.getPriority().size()>0){
					for(ProductPriorityVO priority : productVo.getPriority()){
						if(priority.getOperationFlag()==null){
							priority.setOperationFlag(ProductPriorityVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
				if(productVo.getProductScc()!=null && productVo
						.getProductScc().size()>0){
					for(ProductSCCVO scc : productVo.getProductScc()){
						if(scc.getOperationFlag()==null){
							scc.setOperationFlag(ProductSCCVO.OPERATION_FLAG_UPDATE);
						}
					}
				}
				return(subProductList);
		}

		return null;

	}
	/**
	 * 	Method		:	ProductDefaultsController.setAuditOperationalShipment
	 *	Added on 	:	Jan 25, 2021
	 * 	Used for 	:
	 *	Parameters	:	@param productUpdate
	 *	Parameters	:	@param productsDefaultsAuditVo
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	ProductsDefaultsAuditVO
	 */
	public ProductsDefaultsAuditVO setAuditOperationalShipment(Product productUpdate, ProductsDefaultsAuditVO productsDefaultsAuditVo)
			throws SystemException {
		
		 return (ProductsDefaultsAuditVO)AuditUtils.
		populateAuditDetails(productsDefaultsAuditVo,productUpdate,false);
	}
	
	/**
		 * @author a-1885
		 * @param productVo
		 */
		private void removeDuplicateTramodPriorityScc(ProductVO productVo){
			log.log(Log.FINE,"---------removeDuplicateTramodPriorityScc-------");
			Collection<ProductTransportModeVO> transModes = null;
			Collection<ProductPriorityVO> priorities = null;
			Collection<ProductSCCVO> sccs = null;
			if(productVo.getTransportMode()!=null && productVo
					.getTransportMode().size()>0){
				Collection<ProductTransportModeVO> transModesInsert = null;
				Collection<ProductTransportModeVO> transModesDelete = null;
				Collection<ProductTransportModeVO> transModeNull = null;
				for(ProductTransportModeVO outer : productVo.getTransportMode()){
					if(ProductVO.OPERATION_FLAG_INSERT.equals
							(outer.getOperationFlag())){
						if(transModesInsert==null){
							transModesInsert=new ArrayList<ProductTransportModeVO>();
						}
						transModesInsert.add(outer);
					}
					if(ProductVO.OPERATION_FLAG_DELETE.equals
							(outer.getOperationFlag())){
						if(transModesDelete==null){
							transModesDelete=new ArrayList<ProductTransportModeVO>();
						}
						transModesDelete.add(outer);
					}
					if(outer.getOperationFlag()==null){
						if(transModeNull==null){
							transModeNull=new ArrayList<ProductTransportModeVO>();
						}
						transModeNull.add(outer);
					}
				}
				if(transModesInsert!=null && transModesInsert.size()>0 &&
						transModesDelete!=null && transModesDelete.size()>0){
					for(ProductTransportModeVO outer : transModesInsert){
						boolean isFound = false;
						for(ProductTransportModeVO inner : transModesDelete){
							if(outer.getTransportMode().equals(inner
									.getTransportMode())){
								isFound = true;
							}
							if(isFound){
								outer.setOperationFlag(null);
								inner.setOperationFlag(null);
								if(transModes==null){
									transModes=new ArrayList<ProductTransportModeVO>();
								}
								transModes.add(outer);
							}
						}
					}
				}
				if(transModesDelete!=null && transModesDelete.size()>0){
					if(transModes==null){
						transModes=new ArrayList<ProductTransportModeVO>();
					}
					transModes.addAll(transModesDelete);
				}
				if(transModesInsert!=null && transModesInsert.size()>0){
					if(transModes==null){
						transModes=new ArrayList<ProductTransportModeVO>();
					}
					transModes.addAll(transModesInsert);
				}
				if(transModeNull !=null && transModeNull.size()>0){
					if(transModes==null){
						transModes= new ArrayList<ProductTransportModeVO>();
					}
					transModes.addAll(transModeNull);
				}
			}
			productVo.setTransportMode(transModes);

			if(productVo.getPriority()!=null && productVo
					.getPriority().size()>0){
				Collection<ProductPriorityVO> transModesInsert = null;
				Collection<ProductPriorityVO> transModesDelete = null;
				Collection<ProductPriorityVO> transModeNull = null;
				for(ProductPriorityVO outer : productVo.getPriority()){
					if(ProductVO.OPERATION_FLAG_INSERT.equals
							(outer.getOperationFlag())){
						if(transModesInsert==null){
							transModesInsert=new ArrayList<ProductPriorityVO>();
						}
						transModesInsert.add(outer);
					}
					if(ProductVO.OPERATION_FLAG_DELETE.equals
							(outer.getOperationFlag())){
						if(transModesDelete==null){
							transModesDelete=new ArrayList<ProductPriorityVO>();
						}
						transModesDelete.add(outer);
					}
					if(outer.getOperationFlag()==null){
						if(transModeNull==null){
							transModeNull=new ArrayList<ProductPriorityVO>();
						}
						transModeNull.add(outer);
					}
				}
				if(transModesInsert!=null && transModesInsert.size()>0 &&
						transModesDelete!=null && transModesDelete.size()>0){
					for(ProductPriorityVO outer : transModesInsert){
						boolean isFound = false;
						for(ProductPriorityVO inner : transModesDelete){
							if(outer.getPriority().equals(inner
									.getPriority())){
								isFound = true;
							}
							if(isFound){
								outer.setOperationFlag(null);
								inner.setOperationFlag(null);
								if(priorities==null){
									priorities=new ArrayList<ProductPriorityVO>();
								}
								priorities.add(outer);
							}
						}
					}
				}
				if(transModesDelete!=null && transModesDelete.size()>0){
					if(priorities==null){
						priorities=new ArrayList<ProductPriorityVO>();
					}
					priorities.addAll(transModesDelete);
				}
				if(transModesInsert!=null && transModesInsert.size()>0){
					if(priorities==null){
						priorities=new ArrayList<ProductPriorityVO>();
					}
					priorities.addAll(transModesInsert);
				}
				if(transModeNull !=null && transModeNull.size()>0){
					if(priorities==null){
						priorities= new ArrayList<ProductPriorityVO>();
					}
					priorities.addAll(transModeNull);
				}
			}
			productVo.setPriority(priorities);
			if(productVo.getProductScc()!=null && productVo
					.getProductScc().size()>0){
				Collection<ProductSCCVO> transModesInsert = null;
				Collection<ProductSCCVO> transModesDelete = null;
				Collection<ProductSCCVO> transModeNull = null;
				for(ProductSCCVO outer : productVo.getProductScc()){
					if(ProductVO.OPERATION_FLAG_INSERT.equals
							(outer.getOperationFlag())){
						if(transModesInsert==null){
							transModesInsert=new ArrayList<ProductSCCVO>();
						}
						transModesInsert.add(outer);
					}
					if(ProductVO.OPERATION_FLAG_DELETE.equals
							(outer.getOperationFlag())){
						if(transModesDelete==null){
							transModesDelete=new ArrayList<ProductSCCVO>();
						}
						transModesDelete.add(outer);
					}
					if(outer.getOperationFlag()==null){
						if(transModeNull==null){
							transModeNull=new ArrayList<ProductSCCVO>();
						}
						transModeNull.add(outer);
					}
				}
				if(transModesInsert!=null && transModesInsert.size()>0 &&
						transModesDelete!=null && transModesDelete.size()>0){
					for(ProductSCCVO outer : transModesInsert){
						boolean isFound = false;
						for(ProductSCCVO inner : transModesDelete){
							if(outer.getScc().equals(inner
									.getScc())){
								isFound = true;
							}
							if(isFound){
								outer.setOperationFlag(null);
								inner.setOperationFlag(null);
								if(sccs==null){
									sccs=new ArrayList<ProductSCCVO>();
								}
								sccs.add(outer);
							}
						}
					}
				}
				if(transModesDelete!=null && transModesDelete.size()>0){
					if(sccs==null){
						sccs=new ArrayList<ProductSCCVO>();
					}
					sccs.addAll(transModesDelete);
				}
				if(transModesInsert!=null && transModesInsert.size()>0){
					if(sccs==null){
						sccs=new ArrayList<ProductSCCVO>();
					}
					sccs.addAll(transModesInsert);
				}
				if(transModeNull !=null && transModeNull.size()>0){
					if(sccs==null){
						sccs = new ArrayList<ProductSCCVO>();
					}
					sccs.addAll(transModeNull);
				}
			}
			productVo.setProductScc(sccs);
			log.log(Log.FINE, "--The ProductVO after remove dup---", productVo);
	}

	/**
	 * used for creating or modifying a subproduct
	 * @param subProductVos coresponding to the product to be deleted
	 * @throws SystemException
	 */
	public void saveSubProductDetails(Collection<SubProductVO> subProductVos)
	throws SystemException {
		log.entering("ProductDefaultsController","saveSubProductDetails");
		for(SubProductVO subProductVo:subProductVos){
			Product product= Product.find(subProductVo.getCompanyCode(),subProductVo.getProductCode());
			product.updateSubProduct(subProductVo);
		}
		log.exiting("ProductDefaultsController","saveSubProductDetails");

	}


	/**
	 * Used to delete a product. Exception is thrown if active bookings
	 * exist against any of the subproduct created from this product
	 * @param productVo
	 * @throws SystemException
	 * @throws BookingExistsException
	 */
	@Advice(name = "products.defaults.saveProductDetails" , phase=Phase.POST_INVOKE)
	public void deleteProduct(ProductVO productVo)
	throws SystemException,BookingExistsException{
		Product product = Product.find(productVo.getCompanyCode(),productVo.getProductCode());
		//added for masterdata publish message
		ProductVO prd=Product.findProductDetails(productVo.getCompanyCode(), productVo.getProductCode());
		
		BeanHelper.copyProperties(productVo,prd);
		
		productVo.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
		product.remove();
		auditProductDeleteDetails(product,AuditVO.DELETE_ACTION);
	}

	/**
	 * Used to delete a subProduct. Exception is thrown if active bookings
	 * exist against the subproduct
	 * @param subProductVo
	 * @throws SystemException
	 * @throws BookingExistsException
	 */
	public void deleteSubProduct(SubProductVO subProductVo)
	throws SystemException,BookingExistsException{

		log.entering("ProductDefaultsController","deleteSubProduct");
		Product product=Product.find(subProductVo.getCompanyCode(),subProductVo.getProductCode());
		product.deleteSubProduct(subProductVo);
		log.exiting("ProductDefaultsController","deleteSubProduct");

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
	throws SystemException{
		log.entering("ProductDefaultsController","validateProductName");
		return Product.validateProductName(companyCode,productName,startDate,endDate);
	}

	/**
	 * @author A-1885
	 * @param companyCode
	 * @param productName
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws SystemException
	 */
	public Collection<ProductValidationVO>
	validateProductForAPeriod(String companyCode, String productName,
			LocalDate startDate,LocalDate endDate)
	throws SystemException,ProductDefaultsBusinessException{
		log.entering("ProductDefaultsController","validateProductForAPeriod");
		Collection<ProductValidationVO> products =
			Product.validateProductForAPeriod(companyCode,productName,startDate,endDate);
		if(products==null || products.size()==0){
			throw new ProductDefaultsBusinessException(ProductDefaultsBusinessException
					.PRODUCT_NOT_EXISTS);
		}
		log.exiting("ProductDefaultsController","validateProductForAPeriod");
		return products;
	}
	/**
	 * @param companyCode
	 * @param productName
	 * @return Collection<ProductValidationVO>
	 * @throws SystemException
	 */
	public Collection<ProductValidationVO> findProductsByName(String companyCode,String productName)
	throws SystemException{
		return Product.findProductsByName(companyCode,productName);
	}
	/**
	 *
	 * @param productLovFilterVO
	 * @param displayPage
	 * @return collection of ProductLovs
	 * @throws SystemException
	 */
	public Page<ProductLovVO> findProductLov(ProductLovFilterVO productLovFilterVO,int displayPage)
	throws SystemException{
		return Product.findProductLov(productLovFilterVO,displayPage);

	}
	/**
	 * @param productLovFilterVO
	 * @return Collection<ProductLovVO>
	 * @throws SystemException
	 */
	public Collection<ProductLovVO> findProductForMaster(ProductLovFilterVO productLovFilterVO)
	throws SystemException{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		productLovFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		productLovFilterVO.setIsProductNotExpired(true);
		if(productLovFilterVO.getCurrentDate()==null){
			productLovFilterVO.setCurrentDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		}		
		return Product.findProductForMaster(productLovFilterVO);
	}
	/**
	 * Used to fetch the details of a particular product
	 * @param companyCode
	 * @param productCode
	 * @return productVo
	 * @throws SystemException
	 */
	public ProductVO findProductDetails(String companyCode,String productCode)
	throws SystemException{
		return Product.findProductDetails(companyCode,productCode);
	}

	/**
	 * used to fetch the details of a particular sub product
	 * @param subProductVO
	 * @throws SystemException
	 */
	public SubProductVO findSubProductDetails(SubProductVO subProductVO) throws SystemException{
		log.entering("ProductDefaultsController","findSubProductDetails");

		try{
			return SubProduct.findSubProductDetails(subProductVO);
		}catch(PersistenceException persistenceException){
			throw new SystemException(persistenceException.getErrorCode());
		}
	}


	/**
	 * Used to update the status of  product.Status can be 'Activate'
	 * or 'InActivate'
	 * @param productVos
	 * @throws SystemException
	 * @throws RateNotDefinedException
	 */
	@Advice(name = "products.defaults.updateProductStatus" , phase=Phase.POST_INVOKE) 
	public void updateProductStatus(Collection<ProductVO> productVos)
	throws SystemException,RateNotDefinedException{
		if (productVos == null)
		{
			return; 
			}
		for(ProductVO productVo:productVos){
				//Check if product with same priority exist
				//Added for ICRD_350746 : START
			if("A".equals(productVo.getStatus()) &&
				(productVo.getPrdPriority()!=null && productVo.getPrdPriority().trim().length()>0
						&& Integer.parseInt(productVo.getPrdPriority()) >0)){
					String productCode = Product.checkIfDuplicatePrdPriorityExists(productVo);
					if(productCode!=null && productCode.trim().length()>0){
						//throw new SystemException()
						String[] errors = new String[1];
						errors[0] = productCode;
			            throw new SystemException(PRODUCT_WITHSAMEPRIORITY_EXISTS,errors);
					}
					//Added for ICRD_350746 : END
			}
			Product.find(productVo.getCompanyCode(),productVo.getProductCode()).updateStatus(productVo);
			// Added by A-5277 as part of MDA Publish - For publishing in XML on update of status - ICRD-28324
			ProductVO prd=Product.findProductDetails(productVo.getCompanyCode(), productVo.getProductCode());
			prd.setStatus(productVo.getStatus());
			BeanHelper.copyProperties(productVo,prd);
			productVo.setOperationFlag(ProductVO.OPERATION_FLAG_UPDATE);
			// ends
		}
		
	}

	/**
	 * Used to update the status of  sub product.Status can be 'Activate'
	 * or 'InActivate'
	 * @param productVO
	 * @throws SystemException
	 */
	public void updateSubProductStatus(ProductVO productVO)
	throws SystemException{
		if (productVO == null){
			return;
		}
		Product product = Product.find(productVO.getCompanyCode(),productVO.getProductCode());
		for(SubProductVO subProductVo:productVO.getSubProducts()){
			product.setSubProductStatus(subProductVo);

		}

	}

	/**
     * This method persists the audit details.
     * @param capacityAllotmentAuditVo
     * @throws SystemException
     */
     public void auditProductsDefaults(ProductsDefaultsAuditVO productsDefaultsAuditVO)
     throws SystemException {
    	log.entering("auditProductsDefaults"," Inside ProductDefaultsAudiTController");
     	new ProductsDefaultsAudit(productsDefaultsAuditVO);
     	log.exiting("auditProductsDefaults"," Inside ProductDefaultsAudiTController");

     }
     /**
      * Method for auditing the create and modify product
      * @author A-1885
      * @param productVo
      * @param product
      * @param actionCode
      * @param productsDefaultsAuditVo
      * @throws SystemException
      */
     private void auditProductDetails(ProductVO productVo,Product product,
    		 String actionCode,ProductsDefaultsAuditVO productsDefaultsAuditVo)
     throws SystemException{
		    StringBuilder additionalInfo = new StringBuilder();
	    	StringBuilder transportMode=new StringBuilder();
	    	StringBuilder scc=new StringBuilder();
	    	StringBuilder priority=new StringBuilder();
	    	productsDefaultsAuditVo.setCompanyCode(productVo.getCompanyCode());
	    	productsDefaultsAuditVo.setProductCode(product.getProductPk().getProductCode());
	    	productsDefaultsAuditVo.setActionCode(actionCode);
	    	productsDefaultsAuditVo.setUserId(productVo.getLastUpdateUser());
	    	productsDefaultsAuditVo.setProductName(productVo.getProductName());
	     /*	if(productsDefaultsAuditVo.getAuditFields()!=null &&
	    			productsDefaultsAuditVo.getAuditFields().size()>0){
		    	for(AuditFieldVO auditField : productsDefaultsAuditVo.getAuditFields()) {
					additionalInfo.append(" Field Name: ").append(auditField.getFieldName()).
					append(",").append(" Field Description: ")
						.append(auditField.getDescription()).append(",").append(" Old Value: ").append(auditField.getOldValue())
						.append(",").append(" New Value: ").append(auditField.getNewValue());
				}
	    	}*/
	    	additionalInfo.append("Startdate :").append(product.getStartDate()).append(",")
	    	.append("Enddate :").append(product.getEndDate());
	    	if(productVo.getServices()!=null && productVo.getServices().size()>0){
	    		additionalInfo.append(", Services = ");
	    		int k = 0;
	    		for(ProductServiceVO serVo : productVo.getServices()){
	    			if(k==0){
	    				additionalInfo.append(serVo.getServiceCode());
	    			}
	    			else{
	    				additionalInfo.append(",").append(serVo.getServiceCode());
	    			}
	    			k++;
	    		}
	    	}
	    	int k = 0;
	    	additionalInfo.append(", TransportModes = ");
	    	for(ProductTransportModeVO tramode : productVo.getTransportMode()){
	    		if(k==0){
	    			additionalInfo.append(tramode.getTransportMode());
	    		}
	    		else{
	    			additionalInfo.append(",").append(tramode.getTransportMode());
	    		}
	    		k++;
		    }
	    	k=0;
	    	additionalInfo.append(", Scc's = ");
	    	for(ProductSCCVO sccVo : productVo.getProductScc()){
	    		if(k==0){
	    			additionalInfo.append(sccVo.getScc());
	    		}
	    		else{
	    			additionalInfo.append(",").append(sccVo.getScc());
	    		}
	    		k++;
	    	}
	    	k=0;
	    	additionalInfo.append(", Priority = ");
	    	for(ProductPriorityVO pryVo : productVo.getPriority()){
	    		if(k==0){
	    			additionalInfo.append(pryVo.getPriority());
	    		}
	    		else{
	    			additionalInfo.append(",").append(pryVo.getPriority());
	    		}
	    		k++;
	    	}
	    	productsDefaultsAuditVo.setAdditionalInfo(additionalInfo.toString());
	    	if((ProductsDefaultsAuditVO.PRODUCT_CREATE).equals(actionCode)){
	    		productsDefaultsAuditVo.setAuditRemarks(AuditAction.INSERT.toString());
			}else if((ProductsDefaultsAuditVO.PRODUCT_UPDATE).equals(actionCode)) {
				productsDefaultsAuditVo.setAuditRemarks(AuditAction.UPDATE.toString());
			}else if((ProductVO.OPERATION_FLAG_DELETE).equals(productVo.getOperationFlag())) {
				productsDefaultsAuditVo.setAuditRemarks(AuditAction.DELETE.toString());
			}
	    	AuditUtils.performAudit(productsDefaultsAuditVo);
			log.exiting("auditProductSave","Called AuditUtils:performAudit with AuditVo");

     }
     /**
      * @author A-1885
      * @param product
      * @param actionCode
      * @throws SystemException
      */
     private void auditProductDeleteDetails(Product product,String actionCode)throws SystemException{
    	 log.log(Log.FINE,"(((((((((((((((((((PRODUCT DELETE AUDIT))))))))))))))))))");
    	 ProductsDefaultsAuditVO productsDefaultsAuditVo = new ProductsDefaultsAuditVO(
    			 	ProductVO.PRODUCTSDEFAULTS_AUDIT_PRODUCTNAME,ProductVO.PRODUCTSDEFAULTS_AUDIT_MODULENAME,
    		    	ProductVO.PRODUCTSDEFAULTS_AUDIT_ENTITYNAME);
    	 productsDefaultsAuditVo.setCompanyCode(product.getProductPk().getCompanyCode());
    	 productsDefaultsAuditVo.setProductCode(product.getProductPk().getProductCode());
    	 productsDefaultsAuditVo.setActionCode(actionCode);
    	 productsDefaultsAuditVo.setAdditionalInfo("Product Deleted");
    	 productsDefaultsAuditVo.setAuditRemarks(AuditAction.DELETE.toString());
    	 productsDefaultsAuditVo.setUserId(product.getLastUpdateUser());
    	 productsDefaultsAuditVo.setProductName(product.getProductName());
    	 productsDefaultsAuditVo=(ProductsDefaultsAuditVO)
    	 AuditUtils.populateAuditDetails(productsDefaultsAuditVo,product,false);
    	 AuditUtils.performAudit(productsDefaultsAuditVo);
    	 log.exiting("AudtProductDelete","Called AuditUtils:performAudit with auditVo");

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
 			throws SubProductNotExistingBusinessException, SystemException {
 		List<ProductServiceVO> productServices = SubProduct.findProductServices
 			(companyCode, productCode, transportationMode, productPriority, primaryScc);
 		if(productServices != null && productServices.size() > 0) {
 			log.log(Log.INFO,"Checking if product combination is valid.");
 			if(productServices.get(0).getProductCode() ==null)
 			{
 				throw new SubProductNotExistingBusinessException();
 			}
 		}else if(productServices == null ||productServices.size() == 0) {
 			log.log(Log.INFO,"No such product combination!");
 			throw new SubProductNotExistingBusinessException();
 		}
 		return productServices;
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
    			throws SubProductNotExistingBusinessException, SystemException {
    	SubProductVO subProductVo = new SubProductVO();
		subProductVo = SubProduct
			.validateSubProduct(companyCode, productCode, transportationMode, productPriority, primaryScc);
		if(subProductVo == null) {
			throw new SubProductNotExistingBusinessException();
		}

    	return findSubProductDetails(subProductVo);
    }
    /**
     * @author A-1883
     * @param productFeedbackVO
     * @throws SystemException
     */
     public void saveProductFeedback(ProductFeedbackVO productFeedbackVO)
 	throws SystemException {
    	 log.entering("ProductsDefaultsController","saveProductFeedback()");
    	 new ProductFeedback(productFeedbackVO);
    	 log.exiting("ProductsDefaultsController","saveProductFeedback()");
     }
     /**
      * @author A-1883
      * @param productFeedbackFilterVO
      * @param displayPage
      * @return Page<ProductFeedbackVO>
      * @throws SystemException
      */
     public Page<ProductFeedbackVO> listProductFeedback(
 			ProductFeedbackFilterVO productFeedbackFilterVO,int displayPage)
 			throws SystemException {
    	 log.entering("ProductsDefaultsController","listProductFeedback()");
    	 return ProductFeedback.listProductFeedback(productFeedbackFilterVO,displayPage);
     }
     /**
 	 * Method for getting ProductEvent
 	 * @author A-1883
 	 * @param companyCode
 	 * @param productCode
 	 * @return List<ProductEventVO>
 	 * @throws SystemException
 	 */
 	public List<ProductEventVO> findProductEvent(String companyCode, String productCode)
 	throws SystemException {
 		log.entering("ProductsDefaultsController","findProductEvent()");
 		return Product.findProductEvent(companyCode,productCode);
 	}
 	/**
 	 * Method for getting ProductPerformanceDetails
 	 * @author A-1747
 	 * @param productPerformanceFilterVO
 	 * @return Collection<ProductPerformanceVO>
 	 * @throws SystemException
 	 */
 	public Collection<ProductPerformanceVO> getProductPerformanceDetailsForReport(ProductPerformanceFilterVO productPerformanceFilterVO)
 	throws SystemException {
 		log.entering("ProductsDefaultsController","getProductPerformanceDetailsForReport()");

		OperationsShipmentProxy operationsShipmentProxy = new OperationsShipmentProxy() ;
		Collection<ProductPerformanceVO> productPerformanceVOs = null;
		try{
			productPerformanceVOs = operationsShipmentProxy.findProductPerformanceDetailsForReport(
					productPerformanceFilterVO);
		}catch(ProxyException proxyException){
			for(ErrorVO errorVO:proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
		log.exiting("ProductsDefaultsController","getProductPerformanceDetailsForReport()");
		return productPerformanceVOs ;
 	}
 	/**
	 * @author A-1883
	 * @param stationAvailabilityFilterVO
	 * @return Boolean
	 * @throws SystemException
	 */
	public Boolean checkStationAvailability(StationAvailabilityFilterVO stationAvailabilityFilterVO)
	throws SystemException {
		log.entering("ProductsDefaultsController","checkStationAvailability()");
 		if( Product.checkStationAvailability(stationAvailabilityFilterVO)){
 			return Boolean.valueOf(true);
 		}
 		else {
 			return Boolean.valueOf(false);
 		}
	}

	/**
	 * Find image of Product for the specied filter criteria
	 *
	 * @param productFilterVo filter values
	 * @return ProductVO
	 * @throws SystemException
	 */
	public ProductVO findImage(ProductFilterVO productFilterVo)
			 throws SystemException{
		log.entering("ProductDefaultsController","findImage");
		return Product.findImage(productFilterVo);
	}
	/**
	 * @Author a-1885
	 * Sending Message
	 * @param messageVo
	 * @return
	 * @throws SystemException
	 */
	public void sendMessage(MessageVO messageVo)throws SystemException{
		MsgbrokerMessageProxy msgbrokerMessageProxy = new MsgbrokerMessageProxy();
		try{
			msgbrokerMessageProxy.sendMessage(messageVo);
		}catch(ProxyException proxyException){
			for(ErrorVO errorVO:proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
	}

    /**
     * Added by A-1945
     * @param companyCode
     * @param productCodes
     * @return Map<String, ProductValidationVO>
     * @throws SystemException
     * @throws ProductDefaultsBusinessException
     */
    public Map<String, ProductValidationVO> validateProducts(
            String companyCode, Collection<String> productCodes )
            throws SystemException,
                   ProductDefaultsBusinessException {
        log.entering("ProductDefaultsController", "validateProducts");
        Collection<ProductValidationVO> validatedVOs =
                Product.validateProducts(companyCode, productCodes);
        Map<String,ProductValidationVO> productMap = null;
        if(validatedVOs != null &&
           validatedVOs.size() == productCodes.size()) {
            productMap = new HashMap<String, ProductValidationVO>();
            for(ProductValidationVO validationVO : validatedVOs) {
                productMap.put(validationVO.getProductCode(), validationVO);
            }
        }
        else {
            String[] errors = new String[1];
            StringBuilder stringBuilder = new StringBuilder();
            for(String productCode : productCodes) {
                boolean isPresent = false;
                for(ProductValidationVO vo : validatedVOs) {
                    if(productCode.equals(
                            vo.getProductCode())) {
                        isPresent = true;
                        break;
                    }
                }
                if(!isPresent) {
                    stringBuilder.append(productCode).append(",");
                }
            }
            errors[0] = stringBuilder.substring(0, stringBuilder.length() - 1);
            throw new ProductDefaultsBusinessException(
                    ProductDefaultsBusinessException.PRODUCT_NOT_EXISTS,
                    errors);
        }
        log.exiting("ProductDefaultsController", "validateProducts");
        return productMap;
    }
    /**
     * @author A-1958
     * @param companyCode
     * @param products
     * @return Collection<String>
     * @throws SystemException
     */
    public Collection<String> findSccCodesForProducts(String companyCode, Collection<String> products)
    throws SystemException{
		log.entering("ProductDefaultsController","findSccCodesForProducts");
		return Product.findSccCodesForProducts(companyCode, products);
	}
    /**
     *
     * @param companyCode
     * @param productName
     * @param shpgDate
     * @return
     * @throws SystemException
     */
    public String validateProduct(String companyCode, String productName, LocalDate startDate, LocalDate endDate)
    throws SystemException {
    	log.entering("ProductDefaultsController","validateProduct");
		return Product.validateProduct(companyCode, productName, startDate, endDate);
    }
    
    /**
     * 	Method		:	ProductDefaultsController.validateProductsForListing
     *	Added by 	:	A-8041 on 02-Nov-2017
     * 	Used for 	:	validateProductsForListing
     *	Parameters	:	@param productFilterVO
     *	Parameters	:	@return
     *	Parameters	:	@throws SystemException 
     *	Return type	: 	Collection<ProductVO>
     */
    public Collection<ProductVO> validateProductsForListing(ProductFilterVO productFilterVO)
    	    throws SystemException {
    	    	log.entering("ProductDefaultsController","validateProductsForListing");
    			return Product.validateProductsForListing(productFilterVO);
    	    }
    /**
     * @author a-1885
     * @param companyCode
     * @param documentType
     * @param documentSubType
     * @return
     * @throws SystemException
     */
    public Collection<ProductStockVO> findProductsForStock(String companyCode,String
    		documentType,String documentSubType)throws SystemException{
    	log.entering("ProductDefaultsController","findProductsForStock");
    	return arrangeProductsForStock
    	(Product.findProductsForStock(companyCode,documentType,
    			documentSubType));
    }
    /**
     * @author a-1885
     * @param products
     * @return stocks
     */
    private Collection<ProductStockVO> arrangeProductsForStock(Collection
    		<ProductStockVO> products){
    	Collection<ProductStockVO> stocks = null;
    	ProductStockVO productVo = null;
    	Collection<String> productCodes = new ArrayList<String>();
    	if(products!=null && products.size()>0){
    		for(ProductStockVO outer : products){
    			if(!productCodes.contains(outer.getProductCode())){
    				Collection<String> transportModes = new ArrayList<String>();
    		    	Collection<String> priorities = new ArrayList<String>();
    		    	Collection<String> sccs = new ArrayList<String>();
    				for(ProductStockVO inner : products){
    					if(outer.getProductCode().equals(inner.getProductCode())){
    						if(!transportModes.contains(inner.getTransportModeCode())){
    							transportModes.add(inner.getTransportModeCode());
    						}
    						if(!priorities.contains(inner.getPriorityCode())){
    							priorities.add(inner.getPriorityCode());
    						}
    						if(!sccs.contains(inner.getSccCode())){
    							sccs.add(inner.getSccCode());
    						}
    					}
    				}
    				if(stocks==null){
    					stocks = new ArrayList<ProductStockVO>();
    				}
    				outer.setScc(sccs);
    				outer.setTransportMode(transportModes);
    				outer.setPriority(priorities);
    				stocks.add(outer);
    				productCodes.add(outer.getProductCode());
    			}
    		}
    	}
    	return stocks;
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
    public Collection<ProductEventVO> findSubproductEventsForTracking
    (String companyCode,String productCode,String productScc,String
    		productTransportMode,String productPriority)throws SystemException{
    	return Product.findSubproductEventsForTracking(companyCode,
    			productCode,productScc,productTransportMode,productPriority);
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param productCode
     * @param docType
     * @param docSubType
     */
    public String validateProductForDocType(String companyCode,String productCode,
    		String docType,String docSubType)throws SystemException,
    		ProductDefaultsBusinessException{
    	log.log(Log.FINE,"----CONTROLLER--VALIDATEPRODUCTCODFORDOCTYPE----");
    	String isValid = ProductVO.FLAG_NO;
    	Collection<ProductStockVO> stocks = Product.validateProductForDocType
    	(companyCode,productCode);
    	log.log(Log.FINE, "--ResultStock--", stocks);
		boolean isEntered = false;
    	if(stocks!=null && stocks.size()>0){
    		log.log(Log.FINE,"---ENTERING STOCKS IS NOT NULL----");
    		for(ProductStockVO stockVo : stocks){
    			if(stockVo.getDocumentType()!=null && stockVo.getDocumentType()
    					.trim().length()>0){
	    			if(docType.equals(stockVo.getDocumentType())){
	    				isValid = ProductVO.FLAG_YES;
	    				isEntered = true;
	    			}
    			}
    			else{
    				if(!isEntered){
    					isValid = ProductVO.FLAG_YES;
    				}
    			}
    		}
    	}
    	if(ProductVO.FLAG_NO.equals(isValid)){
    		throw new ProductDefaultsBusinessException
    		(ProductDefaultsBusinessException.PRODUCT_ASSIGNED_TOANOTHERDOCTYPE);
    	}
    	return isValid;
    }
    /**
     * @author a-1885
     * @param companyCode
     * @param startDate
     * @param endDate
     * @return
     */
    public ProductLovVO findProductPkForProductName(String companyCode,
    		String productName,LocalDate startDate,LocalDate endDate)
    throws SystemException{
    	return Product.findProductPkForProductName(companyCode,productName,
    			startDate,endDate);
    }
    /**
     * @author a-4823
     * @param companyCode
     * @param productNames
     * @return
     * @throws SystemException
     * @throws InvalidProductException
     */
    public Map<String, ProductVO> validateProductNames(String companyCode,
    		Collection<String> productNames) throws SystemException,
    		InvalidProductException {
    	if (productNames.size() != 0) {
    		return Product.validateProductNames(companyCode, productNames);
    	}else{
    		return null;
    	}	
    }
    /**
     * 
     * @author A-5257
     * @param companyCode
     * @param productNames
     * @return
     */
    public Collection<ProductVO> findPriorityForProducts(Collection<ProductFilterVO> productFilterVOs) throws SystemException{
    	return Product.findPriorityForProducts(productFilterVOs);
    }    
    /**
     * @author A-5111
     * @param productSuggestConfigurationVOs
     * @throws SystemException
     */ 
    public void saveProductSuggestConfigurations(Collection<ProductSuggestConfigurationVO> productSuggestConfigurationVOs) 
			throws SystemException {
    	log.entering("ProductDefaultsController"," Inside saveProductSuggestConfigurations");
    	LogonAttributes logon = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
    	ProductSuggestConfiguration productSuggestConfiguration=new ProductSuggestConfiguration();
    	try { 
			validateProductSuggestConfigurations(logon.getCompanyCode(), productSuggestConfigurationVOs);
		} catch (InvalidProductException exception1) {
			log.log(Log.SEVERE,"ProductDefaultsController Exception Ocuured in Validating SCCCODES AND PRODUCT NAMES");
			for(ErrorVO errorVO:exception1.getErrors()){
				throw new SystemException(errorVO.getErrorCode(),errorVO.getErrorData()); 
			}
		} catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"ProductDefaultsController Exception Ocuured in Validating SCCCODES AND PRODUCT NAMES");
			for(ErrorVO errorVO:proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode(),errorVO.getErrorData());
			}
		}
    	productSuggestConfiguration.deleteProductSuggestConfigurations(logon.getCompanyCode()); 
    	for(ProductSuggestConfigurationVO productSuggestConfigurationVO:productSuggestConfigurationVOs){
    		    productSuggestConfigurationVO.setCompanyCode(logon.getCompanyCode()); 
				new ProductSuggestConfiguration(productSuggestConfigurationVO);
    	     }
    	log.exiting("ProductDefaultsController"," Inside saveProductSuggestConfigurations");
	} 
    /**
     * @author A-5111
     * @param productSuggestConfigurationVOs,companyCode
     * @throws SystemException
     */
    private boolean validateProductSuggestConfigurations(String companyCode,Collection<ProductSuggestConfigurationVO> productSuggestConfigurationVOs) throws InvalidProductException, SystemException, ProxyException{
    	log.entering("ProductDefaultsController"," Inside validateProductSuggestConfigurations");
    	Set<String> productNames=new HashSet<String>();
    	Collection<String> sccCodes=new ArrayList<String>();
    	boolean isValidationFailed = false;
    	for(ProductSuggestConfigurationVO productSuggestConfigurationVO:productSuggestConfigurationVOs){
    		productNames.add(productSuggestConfigurationVO.getProductName());
    		if("SCC".equals(productSuggestConfigurationVO.getParameterCode())){
    		 sccCodes.add(productSuggestConfigurationVO.getParameterValue());
    		}
    	} 
    		validateProductNames(companyCode, productNames);
    		new SharedSCCProxy().validateSCCCodes(companyCode, sccCodes);
    		log.exiting("ProductDefaultsController"," Inside validateProductSuggestConfigurations");
    	return isValidationFailed;
	}
    /**
     * @author A-5111.
     * @param companyCode
     * @param sccCodes
     * @return 
     * @throws SystemException 
     */
	public String findProductMappings(String companyCode,String sccCodes) throws SystemException {
		log.entering("ProductDefaultsController"," Inside findProductMappings");
		String product=ProductSuggestConfiguration.findProductMappings(companyCode, sccCodes);  
		log.exiting("ProductDefaultsController"," Inside findProductMappings");
		return product;
    }
	/**
	 * 
	 * @param companyCode
	 * @param sccCodes
	 * @return
	 * @throws SystemException
	 */
	public HashMap<String, String> findProductMappings(ProductSuggestionVO productSuggestionVO) throws SystemException {
		log.entering("ProductDefaultsController"," Inside findProductMappings-parameter");
		HashMap<String, String> productMap= new HashMap<String, String>();
		ArrayList<ProductSuggestionVO> productSuggestionVOs = new ProductSuggestConfiguration().findProductSuggestions(productSuggestionVO);
		if(productSuggestionVOs!= null){
			for(ProductSuggestionVO productVO : productSuggestionVOs){
				String rawString = productVO.getProductConfigurationString();
				productVO.setServiceCode(productSuggestionVO.getServiceCode());
				boolean isValid = validateStringCondition(productVO , rawString);
				if(isValid){
					String parameterValue = "";
					if(productVO.getParameterValue()==null){
						parameterValue = "DUMMY";
					}else{
						parameterValue = productVO.getParameterValue();
					}
					productMap.put(parameterValue,productVO.getProductName() );
				}
			}
		}
		log.exiting("ProductDefaultsController"," Inside findProductMappings-parameter");
		return productMap;
    }
	
	/**
	 * @param rawString the raw string
	 * @return true, if successful
	 */
	private boolean validateStringCondition(ProductSuggestionVO productSuggestionVO,String rawString) {
		boolean block = false;
		String displayString = "";

		Map<String, ProductSuggestionVO> templateObject = new HashMap<String, ProductSuggestionVO>();			

		templateObject.put("productSuggestionVO", productSuggestionVO);

		if (rawString != null && rawString.length() > 0) {
			try {
				displayString = TemplateEncoderUtil
				.encode(rawString, "productSuggestionVO",
						templateObject, false);
				if(displayString!=null){
					displayString = displayString.trim();
					if("true".equals(displayString)){
							block = true;
					}
				}		
			} catch (TemplateRenderingException e) {
				LogFactory.getLogger("PRODUCT CONTROLLER").log(Log.SEVERE,
						e.toString());

			}
		}
		//Added by A-7558 for ICRD-311859
		else{
			block = true;
		}
		return 	block;
	}
   /**
	 * 
	 * @author A-5867
	 * @param ProductFilterVO
	 * @return Collection<ProductVO>
	 * @throws SystemException
	 */
	public Collection<ProductVO> findBookableProducts(ProductFilterVO productFilterVO) throws SystemException{
		log.entering("ProductDefaultsController"," Inside findBookableProducts");
		return Product.findBookableProducts(productFilterVO);
} 
	/**
     * @author A-5867.
     * @param companyCode
     * @param parameterCode
     * @param parameterValue
     * @return 
     * @throws SystemException 
     */
	public HashMap<String, String> findSuggestedProducts(String companyCode,String parameterCode,String parameterValue) throws SystemException {
		log.entering("ProductDefaultsController"," Inside findSuggestedProducts");
		HashMap<String, String> productMap=ProductSuggestConfiguration.findSuggestedProducts(companyCode,parameterCode, parameterValue);
		log.exiting("ProductDefaultsController"," Inside findSuggestedProducts");
		return productMap;
    }
	/**
	 * 
	 * @param productModelMappingFilterVO
	 * @return
	 * @throws SystemException
	 */
	public Collection<ProductModelMappingVO> getProductModelMapping(ProductModelMappingFilterVO productModelMappingFilterVO) throws SystemException{
		log.entering("ProductDefaultsController"," Inside getProductModelMapping");
		return Product.getProductModelMapping(productModelMappingFilterVO);
}
	/**
	 * A-6843
	 * @param productCommodityGroupMappingVOs
	 */
	public void saveProductCommodityGroupMapping(
			Collection<ProductCommodityGroupMappingVO> productCommodityGroupMappingVOs)
					throws SystemException {
		log.entering("ProductDefaultsController",
				" Inside saveProductCommodityGroupMapping");
		if (productCommodityGroupMappingVOs != null
				&& productCommodityGroupMappingVOs.size() > 0) {
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			String companyCode = logonAttributes.getCompanyCode();
			validateProductCommodityGroupMapping(
					productCommodityGroupMappingVOs, errorVOs, companyCode);
			if (errorVOs.size() > 0) {
				throw new SystemException(errorVOs);
			}
			ProductCommodityGroupMapping productCommodityGroupMapping =
					new ProductCommodityGroupMapping();
			productCommodityGroupMapping.deleteProductCommodityGroupMappings(
					companyCode);
			String userId = logonAttributes.getUserId();
			for (ProductCommodityGroupMappingVO productCommodityGroupMappingVO:
				productCommodityGroupMappingVOs) {
				productCommodityGroupMappingVO.setCompanyCode(companyCode);
				productCommodityGroupMappingVO.setLastUpdateUser(userId);
				productCommodityGroupMappingVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				new ProductCommodityGroupMapping(
						productCommodityGroupMappingVO);
			}
		}
		log.exiting("ProductDefaultsController",
				" Inside saveProductCommodityGroupMapping");
	}
	/**
	 * A-6843
	 * @param productCommodityGroupMappingVOs
	 * @param errorVOs
	 * @throws SystemException
	 */
	public void validateProductCommodityGroupMapping(
			Collection<ProductCommodityGroupMappingVO> productCommodityGroupMappingVOs,
			Collection<ErrorVO> errorVOs, String companyCode)
			throws SystemException {
		
		Collection<String> productNames = new ArrayList<String>();
		Collection<String> commodityGroups = new ArrayList<String>();
		Collection<String> productCommodityPairList = new ArrayList<String>();
		int countDuplicateCommodityGroup =1;
		for (ProductCommodityGroupMappingVO productCommodityGroupMappingVO:
			productCommodityGroupMappingVOs) {
			++countDuplicateCommodityGroup;
				if ((productCommodityGroupMappingVO.getCommodityGroup() != null)
							&& (productCommodityGroupMappingVO.getProductName() != null)){
						String productCommodityPair = new StringBuilder(productCommodityGroupMappingVO.getProductName())
						.append(productCommodityGroupMappingVO.getCommodityGroup()).toString();
						if(!productCommodityPairList.contains(productCommodityPair)){
							productCommodityPairList.add(productCommodityPair);
						}else{
						errorVOs.add(populateErrors(DUPLICATE_PRODUCT_COMMODITY_PAIR,countDuplicateCommodityGroup,productCommodityGroupMappingVO.getCommodityGroup()));
			}
			productNames.add(
					productCommodityGroupMappingVO.getProductName());
			commodityGroups.add(productCommodityGroupMappingVO.getCommodityGroup());
		}
		}
		if (!(errorVOs.size() > 0)){
			validateProductCodes(companyCode,productNames,errorVOs,"ProdCommGrp");
			validateCommodityGroups(companyCode,commodityGroups,errorVOs);
		}	
    }

	/**
	 * A-6843
	 * @param productAttributePriorityVOs
	 */
	public void saveProductAttributePriority(
			Collection<ProductAttributePriorityVO> productAttributePriorityVOs)
					throws SystemException {
		log.entering("ProductDefaultsController",
				" Inside saveProductCommodityGroupMapping");
		if (productAttributePriorityVOs != null
				&& productAttributePriorityVOs.size() > 0) {
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			String companyCode = logonAttributes.getCompanyCode();
			validateProductAttributePriority(
					productAttributePriorityVOs, errorVOs, companyCode);
			if (errorVOs.size() > 0) {
				throw new SystemException(errorVOs);
			}
			ProductAttributePriority productAttributePriority =
					new ProductAttributePriority();
			productAttributePriority.deleteProductAttributePriorities(
					companyCode);
			String userId = logonAttributes.getUserId();
			for (ProductAttributePriorityVO productAttributePriorityVO:
				productAttributePriorityVOs) {
				productAttributePriorityVO.setCompanyCode(companyCode);
				productAttributePriorityVO.setLastUpdateUser(userId);
				productAttributePriorityVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				new ProductAttributePriority(
						productAttributePriorityVO);
			}
		}
		log.exiting("ProductDefaultsController",
				" Inside saveProductCommodityGroupMapping");
	}
	/**
	 * A-6843
	 * @param productCommodityGroupMappingVOs
	 * @param errorVOs
	 * @throws SystemException
	 */
	public void validateProductAttributePriority(
			Collection<ProductAttributePriorityVO> productAttributePriorityVOs,
			Collection<ErrorVO> errorVOs, String companyCode)
			throws SystemException {
		int countAttributename = 1 ;
		Collection<String> attributeNames = new ArrayList<String>();
		Collection<String> productsInSytem= new ArrayList<String>();
		//Onetime population
		try {
			Collection<String> oneTimeCodes = new ArrayList<String>();
			oneTimeCodes.add(ONETIME_PRODUCT_DESCRIPTION);
			Map<String, Collection<OneTimeVO>> onetimevaluesVOs
				=new SharedDefaultsProxy().findOneTimeValues(companyCode, oneTimeCodes); 			
			if(onetimevaluesVOs != null && 
					onetimevaluesVOs.get(ONETIME_PRODUCT_DESCRIPTION) != null) {
				
				for(OneTimeVO oneTimeVO:onetimevaluesVOs.get(ONETIME_PRODUCT_DESCRIPTION))
					{
					productsInSytem.add( oneTimeVO.getFieldValue());		
					}		
			}	
		}catch(ProxyException proxyException){
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
		
		for (ProductAttributePriorityVO productAttributePriorityVO:
			productAttributePriorityVOs) {
			++countAttributename;		
				//Duplicate Product validation
				if(!attributeNames.contains(productAttributePriorityVO.getAttributeName())){
					attributeNames.add(productAttributePriorityVO.getAttributeName());
				}else{
					errorVOs.add(populateErrors(DUPLICATE_PRODUCT_DESCRIPTION,
							countAttributename,productAttributePriorityVO.getAttributeName()));
				}
				//Check for priority values lesser than 0
				if(productAttributePriorityVO.getPriority()<0){
				 errorVOs.add(populateErrors(INVALID_PRIORITY_NUMBER,countAttributename,productAttributePriorityVO.getAttributeName()));
			}
			//Invalid Product Validaiton
			if(!productsInSytem.contains((productAttributePriorityVO.getAttributeName()))){
				errorVOs.add(populateErrors(INVALID_PRODUCT_DESCRIPTION,
						countAttributename,productAttributePriorityVO.getAttributeName()));
			}
		}
    }
	/**
	 * Save product attribute mapping.
	 *
	 * @param productAttributeMappingVOs the product attribute mapping v os
	 * Author A-6843
	 */
	public void saveProductAttributeMapping(Collection<ProductAttributeMappingVO> productAttributeMappingVOs) 	throws SystemException {
		log.entering("ProductDefaultsController",
				" Inside saveProductAttributeMapping");
		if (productAttributeMappingVOs != null
				&& productAttributeMappingVOs.size() > 0) {
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			String companyCode = logonAttributes.getCompanyCode();
			validateProductAttributeMapping(
					productAttributeMappingVOs, errorVOs, companyCode);
			if (errorVOs.size() > 0) {
				throw new SystemException(errorVOs);
			}
			ProductAttributeMapping productAttributeMapping =
					new ProductAttributeMapping();
			productAttributeMapping.deleteProductAttributeMappings(
					companyCode);
			String userId = logonAttributes.getUserId();
			for (ProductAttributeMappingVO productAttributeMappingVO:
				productAttributeMappingVOs) {
				productAttributeMappingVO.setCompanyCode(companyCode);
				productAttributeMappingVO.setLastUpdateUser(userId);
				productAttributeMappingVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				new ProductAttributeMapping(
						productAttributeMappingVO);
			}
		}
		log.exiting("ProductDefaultsController",
				" Inside saveProductAttributeMapping");
	}
	/**
	 * A-6843
	 * @param productAttributeMappingVOs
	 * @param errorVOs
	 * @throws SystemException
	 */
	public void validateProductAttributeMapping(
			Collection<ProductAttributeMappingVO> productAttributeMappingVOs,
			Collection<ErrorVO> errorVOs, String companyCode)
			throws SystemException {
		int countProductName=1;
		Collection<String> productNames = new ArrayList<String>();
		Collection<String> productsInSytem= new ArrayList<String>();
		Collection<String> upsellingProductCodes = new ArrayList<String>();
		
		//Onetime population for checking products in system
			try {
				Collection<String> oneTimeCodes = new ArrayList<String>();
				oneTimeCodes.add(ONETIME_PRODUCT_DESCRIPTION);
				Map<String, Collection<OneTimeVO>> onetimevaluesVOs
					=new SharedDefaultsProxy().findOneTimeValues(companyCode, oneTimeCodes);			
				if(onetimevaluesVOs != null && 
						onetimevaluesVOs.get(ONETIME_PRODUCT_DESCRIPTION) != null) {
					
					for(OneTimeVO oneTimeVO:onetimevaluesVOs.get(ONETIME_PRODUCT_DESCRIPTION))
						{
						productsInSytem.add(oneTimeVO.getFieldValue());	
						}	
				}	
			}catch(ProxyException proxyException){
				throw new SystemException(proxyException.getMessage(),proxyException);
			}
			for (ProductAttributeMappingVO productAttributeMappingVO:productAttributeMappingVOs) {
				++countProductName;
				//Duplicate Product validation
				if(!productNames.contains(productAttributeMappingVO.getProductName())){
					productNames.add(productAttributeMappingVO.getProductName());
				}else{
					errorVOs.add(populateErrors(DUPLICATE_PRODUCT_NAME,
							countProductName,productAttributeMappingVO.getProductName()));
				}
				//Check for flags values other than N or empty value
				if((!FLAGVALUE_NO.equals(productAttributeMappingVO.geteDIChannelFlag())&&!EMPTY_SPACE.equals(productAttributeMappingVO.geteDIChannelFlag().trim())
						&&null!=productAttributeMappingVO.geteDIChannelFlag())
				||(!FLAGVALUE_NO.equals(productAttributeMappingVO.getPortalChannelFlag())&&!EMPTY_SPACE.equals(productAttributeMappingVO.getPortalChannelFlag().trim())
						&&null!=productAttributeMappingVO.getPortalChannelFlag())
				||(!FLAGVALUE_NO.equals(productAttributeMappingVO.getsOCOChannelFlag())&&!EMPTY_SPACE.equals(productAttributeMappingVO.getsOCOChannelFlag().trim())
						&&null!=productAttributeMappingVO.getsOCOChannelFlag())){
						errorVOs.add(populateErrors(INVALID_FLAG_FOR_PRODUCT_NAME,countProductName,productAttributeMappingVO.getProductName()));
				}
				//Invalid Product Validation//this column can also be empty in that case error need not be shown
			if(productAttributeMappingVO.getAttributeName()!=null
					&& productAttributeMappingVO.getAttributeName().trim().length()>0
					&&!productsInSytem.contains((productAttributeMappingVO.getAttributeName()))){
					errorVOs.add(populateErrors(INVALID_PRODUCT_DESCRIPTION,
							countProductName,productAttributeMappingVO.getAttributeName()));
				}
			if(productAttributeMappingVO.getUpsellingProductCodes()!=null){
				upsellingProductCodes.add(productAttributeMappingVO.getUpsellingProductCodes());
			}
		}
		if(!(errorVOs.size() > 0)) {
			validateProductCodes(companyCode,upsellingProductCodes,errorVOs,"Upselling");
			validateProductCodes(companyCode,productNames,errorVOs,"Product");
		}
    }
	/**
	 * Save Product Group Recommendations.
	 *
	 * @param productGroupRecommendationMappingVOs the product group recommendation mappingVOs 
	 * Author A-6843
	 */
	public void saveProductGroupRecommendations(Collection<ProductGroupRecommendationMappingVO> productGroupRecommendationMappingVOs) 	throws SystemException {
		log.entering("ProductDefaultsController",
				" Inside saveProductGroupRecommendations");
		if (productGroupRecommendationMappingVOs != null
				&& productGroupRecommendationMappingVOs.size() > 0) {
			Collection<ErrorVO> errorVOs = new ArrayList<ErrorVO>();
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
					.getLogonAttributesVO();
			String companyCode = logonAttributes.getCompanyCode();
			validateProductGroupRecommendationMapping(
					productGroupRecommendationMappingVOs, errorVOs, companyCode);
			if (errorVOs.size() > 0) {
				throw new SystemException(errorVOs);
			}
			ProductGroupRecommendationMapping productGroupRecommendationMapping =
					new ProductGroupRecommendationMapping();
			productGroupRecommendationMapping.deleteProductGroupRecommendations(
					companyCode);
			String userId = logonAttributes.getUserId();
			for (ProductGroupRecommendationMappingVO productGroupRecommendationMappingVO:
				productGroupRecommendationMappingVOs) {
				productGroupRecommendationMappingVO.setCompanyCode(companyCode);
				productGroupRecommendationMappingVO.setLastUpdateUser(userId);
				productGroupRecommendationMappingVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				new ProductGroupRecommendationMapping(
						productGroupRecommendationMappingVO);
			}
		}
		log.exiting("ProductDefaultsController",
				" Inside saveProductGroupRecommendations");
	}
	/**
	 * A-6843
	 * @param productAttributeMappingVOs
	 * @param errorVOs
	 * @throws SystemException
	 */
	public void validateProductGroupRecommendationMapping(
			Collection<ProductGroupRecommendationMappingVO> productGroupRecommendationMappingVOs,
			Collection<ErrorVO> errorVOs, String companyCode)
			throws SystemException {
		Collection<String> commodityCodes = new ArrayList<String>();
		Collection<String> productGroups = new ArrayList<String>();
		Collection<String> productPriorities = new ArrayList<String>();
		Collection<String> possibleBookingTypes = new ArrayList<String>();
		Collection<String> consolidatedShipments = new ArrayList<String>();
		int countCommodityCode=1;
		for (ProductGroupRecommendationMappingVO productGroupRecommendationMappingVO:
			productGroupRecommendationMappingVOs) {
			++countCommodityCode;
				if(!commodityCodes.contains(productGroupRecommendationMappingVO.getCommodityCode())){
					commodityCodes.add(productGroupRecommendationMappingVO.getCommodityCode());
				}else{
					errorVOs.add(populateErrors(DUPLICATE_COMMODITY_CODE,
							countCommodityCode,productGroupRecommendationMappingVO.getCommodityCode()));
				}
							
				if((!FLAGVALUE_NO.equals(productGroupRecommendationMappingVO.geteDIChannelFlag())&&!EMPTY_SPACE.equals(productGroupRecommendationMappingVO.geteDIChannelFlag().trim())
						&&null!=productGroupRecommendationMappingVO.geteDIChannelFlag())
				||(!FLAGVALUE_NO.equals(productGroupRecommendationMappingVO.getPortalChannelFlag())&&!EMPTY_SPACE.equals(productGroupRecommendationMappingVO.getPortalChannelFlag().trim())
						&&null!=productGroupRecommendationMappingVO.getPortalChannelFlag())
				||(!FLAGVALUE_NO.equals(productGroupRecommendationMappingVO.getsOCOChannelFlag())&&!EMPTY_SPACE.equals(productGroupRecommendationMappingVO.getsOCOChannelFlag().trim())
						&&null!=productGroupRecommendationMappingVO.getsOCOChannelFlag())){
						errorVOs.add(populateErrors(INVALID_FLAG_FOR_COMMODITY_CODE,countCommodityCode,productGroupRecommendationMappingVO.getCommodityCode()));
			}
				if(productGroupRecommendationMappingVO.getProductGroup()==null || EMPTY_SPACE.equals(productGroupRecommendationMappingVO.getProductGroup())){
					if(productGroupRecommendationMappingVO.getProductPriority()!=null && !EMPTY_SPACE.equals(productGroupRecommendationMappingVO.getProductPriority())){
						errorVOs.add(populateErrors(INVALID_PRIORITY_FOR_EMPTY_PRODUCT_GROUP,countCommodityCode,productGroupRecommendationMappingVO.getCommodityCode()));
					}
			}
			productGroups.add(productGroupRecommendationMappingVO.getProductGroup());
			productPriorities.add(productGroupRecommendationMappingVO.getProductPriority());
			possibleBookingTypes.add(productGroupRecommendationMappingVO.getPossibleBookingType());
			consolidatedShipments.add(productGroupRecommendationMappingVO.getConsolShipment());
		}
		if(!(errorVOs.size() > 0)) {
			validateCommodityCodes(companyCode,commodityCodes,errorVOs);
			validateProductGroup(companyCode,productGroups,errorVOs);
			validateProductPriority(companyCode,productPriorities,errorVOs);
			validatePossibleBookingType(possibleBookingTypes, errorVOs);
			validateConsolidatedShipments(consolidatedShipments,errorVOs);
		}
	} 

	/**
	 * Populate errors.
	 * Added by A-6843 for ICRD-160333
	 * @param errorCode the error code
	 * @param index the index
	 * @param errordata the errordata
	 * @return the error vo
	 */
	private ErrorVO populateErrors(String errorCode,int index,String errordata){
		Object[] errorCodes = new Object[3];
		errorCodes[0] = index;	
		errorCodes[1] = errordata;			
		ErrorVO errorVO = new ErrorVO(errorCode);
		errorVO.setErrorData(errorCodes);
		return errorVO;
	}
	 /**
 	 * Validate product codes.
 	 * Added by A-6843 for ICRD-160333
 	 * @param companyCode the company code
 	 * @param productNames the product names
 	 * @return the map
 	 * @throws SystemException the system exception
 	 * @throws InvalidProductException the invalid product exception
 	 */
 	public Map<String,ProductVO> validateProductCodes(String companyCode,
	    		Collection<String> productNames) throws SystemException,
	    		InvalidProductException {
	    	if (productNames.size() != 0) {
	    		return Product.validateProductCodes(companyCode, productNames);
	    	}
	    		return null;
	    }
	 /**
	  * Validate commodity groups.
	  * Added by A-6843 for ICRD-160333
	  * @param companyCode the company code
	  * @param commodityGroups the commodity groups
	  * @param errorVOs the error v os
	  * @throws SystemException the system exception
	  */
	 private void validateCommodityGroups(String companyCode,Collection<String> commodityGroups,Collection<ErrorVO> errorVOs) throws SystemException{
 		try {
					GeneralMasterGroupFilterVO generalMasterGroupFilterVO= new GeneralMasterGroupFilterVO();
					Collection<GeneralMasterGroupVO> generalMasterGroupVOList= null;
					SharedMasterGroupingProxy sharedMasterGroupingProxy= new SharedMasterGroupingProxy();
			int commodityGroupCount=1;
			for(String commodityGroup:commodityGroups){
				++commodityGroupCount;
				generalMasterGroupFilterVO.setCompanyCode(companyCode);
				generalMasterGroupFilterVO.setGroupName(commodityGroup);
				generalMasterGroupFilterVO.setGroupType(COMMMODITY_GROUP);
						generalMasterGroupVOList=sharedMasterGroupingProxy.findGroupNamesToValidate(generalMasterGroupFilterVO);
					if(generalMasterGroupVOList==null|| generalMasterGroupVOList.size()==0){
						errorVOs.add(populateErrors(INVALID_COMMODITY_GROUP,commodityGroupCount, commodityGroup));
					}
			}
		}catch (ProxyException proxyException) {
			log.log(Log.SEVERE,"\n\n  +++++++++++++++PROXY EXCEPTION++++++++++++++++++++++++++++");
			throw new SystemException(proxyException.getMessage(),proxyException);
		}
	}
 	/**
	  * Validate productcodes.
	  * Added by A-6843 for ICRD-160333
	  * @param companyCode the company code
	  * @param productNames the product names
	  * @param errorVOs the error v os
	  * @throws SystemException the system exception
	  */
	 private void validateProductCodes(String companyCode,Collection<String>productNames,Collection<ErrorVO> errorVOs,String productType) 
 			throws SystemException{
			try {
			Map<String,ProductVO> validproducts =validateProductCodes(companyCode, productNames);
			int productNameCount=1;
			ProductVO validatedProductName = null;
			for(String productName:productNames){
				++productNameCount;
				validatedProductName=validproducts.get(productName);
				if (validatedProductName == null && productName!=null && !"".equals(productName) && "Upselling".equals(productType)) {
						errorVOs.add(populateErrors(INVALID_UPSELLING_PRODUCT_NAME,
								productNameCount, productName));
					} else if(validatedProductName == null && !"Upselling".equals(productType)){
						errorVOs.add(populateErrors(INVALID_PRODUCT_NAME,
								productNameCount, productName));
					}
				
			}
		}catch (InvalidProductException prodNameException) {
			log.log(Log.SEVERE,"ProductDefaultsController Exception Ocuured in Validating PRODUCT CODES");
			for(ErrorVO errorVO:prodNameException.getErrors()){
				throw new SystemException(errorVO.getErrorCode(),errorVO.getErrorData()); 
			}
		}
 	}
	
 	/**
	  * Validate commodity codes.
	  * Added by A-6843 for ICRD-160333
	  * @param companyCode the company code
	  * @param commodityCodes the commodity codes
	  * @param errorVOs the error v os
	  * @throws SystemException the system exception
	  */
	 private void validateCommodityCodes(String companyCode,Collection<String> commodityCodes,
 			Collection<ErrorVO> errorVOs) throws SystemException{
 		try {
			SharedCommodityProxy sharedCommodityProxy =new SharedCommodityProxy();
			Map<String,CommodityValidationVO> validCommodityCodes = sharedCommodityProxy.validCommodityCodes(companyCode, commodityCodes);
			int commodityCodesCount=1;
			for(String commodityCode:commodityCodes){
				++commodityCodesCount;
				CommodityValidationVO validatedCommodityCode=validCommodityCodes.get(commodityCode);
				if(validatedCommodityCode==null){
					errorVOs.add(populateErrors(INVALID_COMMODITY_CODE,commodityCodesCount, commodityCode));
				}	
			}	
		} catch (ProxyException proxyException) {
			for(ErrorVO errorVO:proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
 	}
	 /**
	  * Validate product group.
	  * Added by A-6843 for ICRD-160333
	  * @param companyCode the company code
	  * @param productGroups the product groups
	  * @param errorVOs the error v os
	  * @throws SystemException the system exception
	  */
	 private void validateProductGroup(String companyCode,Collection<String> productGroups,
 			Collection<ErrorVO> errorVOs) throws SystemException{
 		try {
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO= new GeneralMasterGroupFilterVO();
			Collection<GeneralMasterGroupVO> generalMasterGroupVOList= null;
			SharedMasterGroupingProxy sharedMasterGroupingProxy= new SharedMasterGroupingProxy();
			int productGroupCount =1;
					for(String productGroup:productGroups){
				++productGroupCount;
						generalMasterGroupFilterVO.setCompanyCode(companyCode);
				generalMasterGroupFilterVO.setGroupName(productGroup);
						generalMasterGroupFilterVO.setGroupType(PRODUCT_GROUP);
					generalMasterGroupVOList=sharedMasterGroupingProxy.findGroupNamesToValidate(generalMasterGroupFilterVO);
							if((generalMasterGroupVOList==null|| generalMasterGroupVOList.size()==0)&&(!EMPTY_SPACE.equals(productGroup))){
						errorVOs.add(populateErrors(INVALID_PRODUCT_GROUP,productGroupCount, productGroup));
					}
			}
		} catch (ProxyException proxyException) {
			for(ErrorVO errorVO:proxyException.getErrors()){
				throw new SystemException(errorVO.getErrorCode());
			}
		}
 	}
	 /**
	  * Validate product priority.
	  * Added by A-6843 for ICRD-160333
	  * @param companyCode the company code
	  * @param productPriorities the product priorities
	  * @param errorVOs the error v os
	  * @throws SystemException the system exception
	  */
	 private void validateProductPriority(String companyCode,
 			Collection<String> productPriorities,Collection<ErrorVO> errorVOs) throws SystemException{
		int productPriorityCount=1;
		boolean isPresent=false;
 		try {
			Collection<String> systemOneTimeValue = new ArrayList<String>();
			systemOneTimeValue.add(ONETIME_PRODUCT_PRIORITY);
			Map<String, Collection<OneTimeVO>> onetimevaluesVOs = null;
			SharedDefaultsProxy sharedDefaultsProxy = new SharedDefaultsProxy() ;
			onetimevaluesVOs=sharedDefaultsProxy.findOneTimeValues(companyCode, systemOneTimeValue);
			if(onetimevaluesVOs != null && 
					onetimevaluesVOs.get(ONETIME_PRODUCT_PRIORITY) != null) {
				Collection<OneTimeVO> oneTimeVOs = onetimevaluesVOs
						.get(ONETIME_PRODUCT_PRIORITY);
				for (String productPriority : productPriorities) {
					isPresent = false;
					++productPriorityCount;
					for (OneTimeVO oneTimeVO : oneTimeVOs) {
						if (productPriority.equals(oneTimeVO
								.getFieldValue())||EMPTY_SPACE.equals(productPriority)) {
							isPresent = true;
								break;
							}
					}
					if(!isPresent)
					{
						errorVOs.add(populateErrors(INVALID_PRODUCT_PRIORITY,productPriorityCount,productPriority));
					}
				}
			}	
			} catch (ProxyException proxyException) {
				for(ErrorVO errorVO:proxyException.getErrors()){
					throw new SystemException(errorVO.getErrorCode());
				}
		}
	}
	/**
	 * @author A-4823
	 * @param productODMappingVOs
	 * @throws SystemException
	 */
	public void saveAllowedProductsForOD(Collection<ProductODMappingVO> productODMappingVOs) 
					throws SystemException {
		log.entering("ProductDefaultsController","Inside saveAllowedProductsForOD");
		if (productODMappingVOs != null
				&& productODMappingVOs.size() > 0) {
			String companyCode = ContextUtils.getSecurityContext()
					.getLogonAttributesVO().getCompanyCode();
			String userId = ContextUtils.getSecurityContext()
					.getLogonAttributesVO().getUserId();
			//remove and insert TODO
			new ProductODMapping().removeProductODMapping(companyCode);
			//BULK INSERT
			for (ProductODMappingVO productODMappingVO :productODMappingVOs) {
				productODMappingVO.setCompanyCode(companyCode);
				productODMappingVO.setLastUpdateUser(userId);
				productODMappingVO.setLastUpdateTime(
						new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
				new ProductODMapping(productODMappingVO);
			}
		}
		log.exiting("ProductDefaultsController","Inside saveProductAttributeMapping");
	} 
	public Collection<ProductODMappingVO> getProductODMapping(String companyCode) throws SystemException {
		log.entering("ProductDefaultsController"," Inside getProductODMapping");
		return ProductODMapping.getProductODMapping(companyCode);
	}
	
	 /**
	  * Validate possible booking types.
	  * Added by A-5642 for ICRD-179439
	  * @param possibleBookingTypes
	  * @param errorVOs the error vos
	  */
	 private void validatePossibleBookingType(
			 Collection<String> possibleBookingTypes, Collection<ErrorVO> errorVOs) {
		 int possibleBookingTypesCount = 1;
		 boolean isValid = false;
		 for(String possibleBookingType:possibleBookingTypes){
			 ++possibleBookingTypesCount;
			 isValid = false;
			 if (possibleBookingType != null && possibleBookingType.trim().length() > 0
					 && (ProductGroupRecommendationMappingVO.POSSIBLE_BKG_TYPE_BUP.equals(possibleBookingType) ||
							 ProductGroupRecommendationMappingVO.POSSIBLE_BKG_TYPE_LOOSE.equals(possibleBookingType)||
							 ProductGroupRecommendationMappingVO.POSSIBLE_BKG_TYPE_BUP_LOOSE.equals(possibleBookingType))) {
				 isValid = true;
			 }
			 if (!isValid) {
				 errorVOs.add(populateErrors(
						 INVALID_BOOKABLE_AS, possibleBookingTypesCount, possibleBookingType));
			 }
		 }
	}
	 
 	/**
 	 * Validate consolidated shipments.
 	 * added by A-6843 for ICRD-181174
 	 * @param consolidatedShipments the consolidated shipments
 	 * @param errorVOs the error v os
 	 */
 	private void validateConsolidatedShipments( Collection<String> consolidatedShipments, Collection<ErrorVO> errorVOs){
		 int consolidatedShipmentsCount = 1;
		 boolean isValidStatus = false;
		 for(String consolidatedShipment:consolidatedShipments){
			++consolidatedShipmentsCount;
			isValidStatus = false;
			if (consolidatedShipment != null
					 && (ProductGroupRecommendationMappingVO.POSSIBLE_CONSOL_STATUS_TYPE_CHECKED.equals(consolidatedShipment) ||
							 ProductGroupRecommendationMappingVO.POSSIBLE_CONSOL_STATUS_TYPE_UNCHECKED.equals(consolidatedShipment)||
							 EMPTY_SPACE.equals(consolidatedShipment))) {
				isValidStatus = true;
			 }
			if (!isValidStatus) {
				 errorVOs.add(populateErrors(
						 INVALID_CONSOL_STATUS, consolidatedShipmentsCount, consolidatedShipment));
			 }
		 }
	}
	
	/**
 	 * 
 	 * 	Method		:	ProductDefaultsController.findProducts
 	 *	Added by 	:	A-7548 on 19-Jan-2018
 	 * 	Used for 	:
 	 *	Parameters	:	@param productFilterVO
 	 *	Parameters	:	@return
 	 *	Parameters	:	@throws RemoteException
 	 *	Parameters	:	@throws SystemException 
 	 *	Return type	: 	Collection<ProductVO>
 	 */
	public Collection<ProductVO> findProducts(ProductFilterVO productFilterVO) throws SystemException {
		log.entering("ProductDefaultsController", "findProducts");
		return Product.findProducts(productFilterVO);
	}
	/**
 	 * 
 	 * 	Method		:	ProductDefaultsController.findProductParametersByCode
 	 *	Added by 	:	A-7740 on 22-nov-2018
 	 * 	Used for 	:
 	 *	Parameters	:	@param productFilterVO
 	 *	Parameters	:	@return
 	 *	Parameters	:	@throws RemoteException
 	 *	Parameters	:	@throws SystemException 
 	 *	Return type	: 	Collection<ProductVO>
 	 */
	public Map<String, String> findProductParametersByCode(String companyCode,
			String ProductCode, Collection<String> parameterCodes)
			throws SystemException {
		return Product.findProductParametersByCode(companyCode, ProductCode,
				parameterCodes);
	}
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.findProductsByParameters
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
			throws  SystemException {
		log.entering("ProductsDefaultsProxy","findProductParametersByCode");
		return Product.findProductsByParameters(companyCode,parametersAndParValue);
	}

    /**
     * @author A-9025
     * @param companyCode
     * @param productNames
     * @return
     * @throws SystemException
     * @throws InvalidProductException
     */
    public Map<String, ProductVO> validateProductsByNames(String companyCode, Collection<String> productNames) 
    		throws SystemException, InvalidProductException {
    	if (productNames.size() != 0) {
    		return Product.validateProductsByNames(companyCode, productNames);
    	} else {
    		return null;
    	}
	}
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.findAllProductParameters
	 *	Added by 	:	A-8130 on 10-Jan-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductParamterVO>
	 */
	public Collection<ProductParamterVO> findAllProductParameters(String companyCode) throws SystemException{
		return Product.findAllProductParameters(companyCode);
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.findProductPksForProductNames
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
			LocalDate fromDate, LocalDate toDate) throws SystemException {
		Map<String, ProductLovVO> productLovVOMaps = null;
		if (productNames != null && productNames.trim().length() > 0) {
			productLovVOMaps = new HashMap<>();
			String[] products = productNames.split(",");
			for (String product : products) {
				ProductLovVO productVO = findProductPkForProductName(companyCode, product, fromDate, toDate);
				if (productVO != null) {
					productLovVOMaps.put(product, productVO);
				}
			}
		}
		return productLovVOMaps;
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.findProductsRestrictions
	 *	Added by 	:	A-8146 on 01-Aug-2019
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param productCode
	 *	Parameters	:	@param transportationMode
	 *	Parameters	:	@param productPriority
	 *	Parameters	:	@param primaryScc
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Map<String,SubProductVO>
	 */
	public Map<String, SubProductVO> findProductsRestrictions(String companyCode,
			Map<String, ProductLovVO> productDetails, String primaryScc)
			throws SystemException {
		Map<String, SubProductVO> subProductVOMaps = null;
		if (productDetails != null && productDetails.size() > 0) {
			subProductVOMaps=new HashMap<>();
			for (String key : productDetails.keySet()) {
				ProductLovVO product = productDetails.get(key);
				if (product != null) {
					SubProductVO subProductVo = null;
					subProductVo = SubProduct.validateSubProduct(companyCode, product.getProductCode(),
							product.getProductTransportMode().iterator().next(),
							product.getProductPriority().iterator().next(), primaryScc);
					if(subProductVo!=null){
						subProductVo=findSubProductDetails(subProductVo);
						subProductVOMaps.put(key, subProductVo);
					}
				}
			}
		}
		return subProductVOMaps;
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.saveProductSelectionRuleMasterDetails
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param productSelectionRuleMasterVOs
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public void saveProductSelectionRuleMasterDetails(Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs)
			throws SystemException {
		log.entering(this.getClass().getSimpleName(), "saveProductSelectionRuleMasterDetails");
		new ProductSelectionRuleMaster().clearProductSelectionRuleMaster();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<ErrorVO> errorVOs = validateProductSelectionRuleMaster(productSelectionRuleMasterVOs,
				logonAttributes);
		if (errorVOs != null && !errorVOs.isEmpty()) {
			throw new SystemException(errorVOs);
		}
		if (Objects.nonNull(productSelectionRuleMasterVOs) && !productSelectionRuleMasterVOs.isEmpty()) {
			AtomicReference<Integer> count = new AtomicReference<>();
			productSelectionRuleMasterVOs.stream().forEach(productSelection -> 
					createNewProductSelectionRuleMaster(logonAttributes, count, productSelection)
			);
		}

		log.exiting(this.getClass().getSimpleName(), "saveProductSelectionRuleMasterDetails");
	}

	private void createNewProductSelectionRuleMaster(LogonAttributes logonAttributes, AtomicReference<Integer> count,
			ProductSelectionRuleMasterVO productSelection)  {
		if (Objects.nonNull(count.get())) {
			count.set(count.get() + 1);
		} else {
			count.set(0);
		}
		productSelection.setSerialNumber(count.get());
		productSelection.setCompanyCode(logonAttributes.getCompanyCode());
		try {
			new ProductSelectionRuleMaster(productSelection);
		} catch (SystemException e) {
			log.log(Log.SEVERE, e);
		}
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.validateProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param productSelectionRuleMasterVOs
	 *	Parameters	:	@param logonAttributes
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<ErrorVO>
	 */
	private Collection<ErrorVO> validateProductSelectionRuleMaster(
			Collection<ProductSelectionRuleMasterVO> productSelectionRuleMasterVOs, LogonAttributes logonAttributes) {
		int rownum = 2;
		Collection<String> uniqueMaster = new HashSet<>();
		Collection<ErrorVO> errorVOs = new ArrayList<>();
		Collection<String> sccCodes = new HashSet<>();
		Collection<String> commodityCodes = new HashSet<>();
		Collection<String> originCountryCodes = new HashSet<>();
		Collection<String> destinationCountryCodes = new HashSet<>();
		Collection<String> agentCodes = new HashSet<>();
		Collection<String> productCodes = new HashSet<>();
		for (ProductSelectionRuleMasterVO productSelectionRuleMasterVO : productSelectionRuleMasterVOs) {
			if (productSelectionRuleMasterVO.getSourceCode() == null
					|| productSelectionRuleMasterVO.getSourceCode().trim().length() == 0) {
				ErrorVO errorVO = new ErrorVO(
						"shared.defaults.genericmaster.productselectionrulemaster.sourcecode.mandatory",
						new Object[] { rownum });
				errorVOs.add(errorVO);
			}
			if (productSelectionRuleMasterVO.getProductCode() == null
					|| productSelectionRuleMasterVO.getProductCode().trim().length() == 0) {
				ErrorVO errorVO = new ErrorVO(
						"shared.defaults.genericmaster.productselectionrulemaster.productcode.mandatory",
						new Object[] { rownum });
				errorVOs.add(errorVO);
			}
			StringBuilder element = new StringBuilder();
			element = element.append(productSelectionRuleMasterVO.getSourceCode())
					.append(productSelectionRuleMasterVO.getSccCode())
					.append(productSelectionRuleMasterVO.getSccGroupCode())
					.append(productSelectionRuleMasterVO.getAgentCode())
					.append(productSelectionRuleMasterVO.getAgentGroupCode())
					.append(productSelectionRuleMasterVO.getInternationalDomesticFlag())
					.append(productSelectionRuleMasterVO.getOriginCountryCode())
					.append(productSelectionRuleMasterVO.getDestinationCountryCode())
					.append(productSelectionRuleMasterVO.getProductCode())
					.append(productSelectionRuleMasterVO.getCommodityCode());
			if (uniqueMaster.contains(element.toString())) {
				ErrorVO errorVO = new ErrorVO(
						"shared.defaults.genericmaster.productselectionrulemaster.duplicateentriesexist",
						new Object[] { rownum });
				errorVOs.add(errorVO);
			} else {
				uniqueMaster.add(element.toString());
			}
			if (productSelectionRuleMasterVO.getSccCode() != null
					&& productSelectionRuleMasterVO.getSccCode().trim().length() > 0) {
				sccCodes.add(productSelectionRuleMasterVO.getSccCode().trim());
			}
			if (productSelectionRuleMasterVO.getCommodityCode() != null
					&& productSelectionRuleMasterVO.getCommodityCode().trim().length() > 0) {
				commodityCodes.add(productSelectionRuleMasterVO.getCommodityCode().trim());
			}
			if (productSelectionRuleMasterVO.getAgentCode() != null
					&& productSelectionRuleMasterVO.getAgentCode().trim().length() > 0) {
				agentCodes.add(productSelectionRuleMasterVO.getAgentCode().trim());
			}
			if (productSelectionRuleMasterVO.getOriginCountryCode() != null
					&& productSelectionRuleMasterVO.getOriginCountryCode().trim().length() > 0) {
				originCountryCodes.add(productSelectionRuleMasterVO.getOriginCountryCode().trim());
			}
			if (productSelectionRuleMasterVO.getDestinationCountryCode() != null
					&& productSelectionRuleMasterVO.getDestinationCountryCode().trim().length() > 0) {
				destinationCountryCodes.add(productSelectionRuleMasterVO.getDestinationCountryCode().trim());
			}
			if (productSelectionRuleMasterVO.getProductCode() != null
					&& productSelectionRuleMasterVO.getProductCode().trim().length() > 0) {
				productCodes.addAll(Arrays.asList(productSelectionRuleMasterVO.getProductCode().trim().split(",")));
			}
			rownum++;
		}
		Map<String, Collection<String>> dataToBeValidated = new HashMap<>();
		dataToBeValidated.put(SCCCOD, sccCodes);
		dataToBeValidated.put(COMCOD, commodityCodes);
		dataToBeValidated.put(ORGCNTCOD, originCountryCodes);
		dataToBeValidated.put(DSTCNTCOD, destinationCountryCodes);
		dataToBeValidated.put(AGTCOD, agentCodes);
		dataToBeValidated.put(PRDCOD, productCodes);
		validateDataWithMasters(logonAttributes, errorVOs, dataToBeValidated);
		return errorVOs;
	}

	private void validateDataWithMasters(LogonAttributes logonAttributes, Collection<ErrorVO> errorVOs,
			Map<String, Collection<String>> dataToBeValidated) {
		for (Map.Entry<String, Collection<String>> entry : dataToBeValidated.entrySet()) {
			if (SCCCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					Proxy.getInstance().get(SharedSCCProxy.class).validateSCCCodes(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (ProxyException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				}
			}
			if (COMCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					Proxy.getInstance().get(SharedCommodityProxy.class).validCommodityCodes(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (ProxyException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				}
			}
			if (ORGCNTCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					Proxy.getInstance().get(SharedAreaProxy.class).validateCountryCodes(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (ProxyException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				}
			}
			if (DSTCNTCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					Proxy.getInstance().get(SharedAreaProxy.class).validateCountryCodes(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (ProxyException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				}
			}
			if (AGTCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					Proxy.getInstance().get(SharedAgentProxy.class).validateAgents(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (ProxyException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				}
			}
			if (PRDCOD.equals(entry.getKey()) && !entry.getValue().isEmpty()) {
				try {
					validateProductNames(logonAttributes.getCompanyCode(), entry.getValue());
				} catch (SystemException e) {
					log.log(Log.SEVERE, e);
				} catch (InvalidProductException e) {
					errorVOs.addAll(e.getErrors());
					log.log(Log.SEVERE, e);
				}
			}
		}
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.listProductSelectionRuleMaster
	 *	Added by 	:	Prashant Behera on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 */
	public Collection<ProductSelectionRuleMasterVO> listProductSelectionRuleMaster(String companyCode) throws SystemException{
		return ProductSelectionRuleMaster.listProductSelectionRuleMaster(companyCode);
	}
	
	/**
	 * 
	 * 	Method		:	ProductDefaultsController.findProductsForBookingFromProductSelectionRule
	 *	Added by 	:	A-8146 on Jun 29, 2022
	 * 	Used for 	:
	 *	Parameters	:	@param companyCode
	 *	Parameters	:	@param filterConditions
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<ProductSelectionRuleMasterVO>
	 */
	public Collection<ProductSelectionRuleMasterVO> findProductsForBookingFromProductSelectionRule(String companyCode,
			Map<String, String> filterConditions)  throws SystemException {
		Collection<GeneralParametersVO> generalParametersVOs = Proxy.getInstance().get(SharedDefaultsProxy.class).findGeneralParameterValues(companyCode, "PRDSELMST");
		return ProductSelectionRuleMaster.findProductsForBookingFromProductSelectionRule(companyCode,filterConditions,generalParametersVOs);
	}
}
