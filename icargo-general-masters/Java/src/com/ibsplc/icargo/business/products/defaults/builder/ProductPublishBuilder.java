/*
 * ProductPublishBuilder.java Created on Nov 7, 2012
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.products.defaults.builder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.PublishDataMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.PublishMessageEntityKeyVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.CommodityRestrictionsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.CustomerGroupRestrictionsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.EventsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.PaymentTermsRestrictionsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.PrioritiesMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.ProductMasterMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.SCCsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.SegmentRestrictionsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.ServicesMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.StationRestrictionsMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.masterdata.products.defaults.TransportModesMessageVO;
import com.ibsplc.icargo.business.products.defaults.proxy.MsgbrokerMessageProxy;
import com.ibsplc.icargo.business.products.defaults.vo.ProductEventVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductPriorityVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductSCCVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductServiceVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductTransportModeVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCommodityVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionCustomerGroupVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionPaymentTermsVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionSegmentVO;
import com.ibsplc.icargo.business.products.defaults.vo.RestrictionStationVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.interceptor.action.AbstractActionBuilder;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * Java file :
 * com.ibsplc.icargo.business.shared.product.builder.ProductPublishBuilder.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : a-5277 : Nov 7,
 * 2012 : Draft
 */
public class ProductPublishBuilder extends AbstractActionBuilder {

	private static final Log log = LogFactory.getLogger("PRODUCT PUBLISH");
	
	/** The Constant ZERO. */
	private static final String ZERO = "0";
	
	/** The Constant TIME_SEPERATOR. */
	private static final String TIME_SEPERATOR = ":";

	/**
	 * Save product details.
	 *
	 * @param productVo the product vo
	 * @throws SystemException the system exception
	 */
	public void saveProductDetails(ProductVO productVo) throws SystemException {
		log.entering("ProductPublishBuilder", "saveProductDetails");
		Collection<ProductMasterMessageVO> productMasterMessageVOs = new ArrayList<ProductMasterMessageVO>();
		ProductMasterMessageVO productMasterMessageVO = populateProductMasterMessageVO(productVo);
		if (productMasterMessageVO != null) {
			// adding vo to collection
			productMasterMessageVOs.add(productMasterMessageVO);
		}
		try {
			if (productMasterMessageVOs.size() > 0) {
				new MsgbrokerMessageProxy()
						.encodeMessages(productMasterMessageVOs);
			}
		} catch (ProxyException e) {
		}
		log.exiting("ProductPublishBuilder", "saveProductDetails");
	}

	/**
	 * Update product status.
	 *
	 * @param productVOs the product v os
	 * @throws SystemException the system exception
	 */
	public void updateProductStatus(Collection<ProductVO> productVOs) throws SystemException {
		// Function added for XML generation on update of Product Status - ICRD-28324
		log.entering("ProductPublishBuilder", "updateProductStatus");
		Collection<ProductMasterMessageVO> productMasterMessageVOs = new ArrayList<ProductMasterMessageVO>();
		for(ProductVO productVo:productVOs){
			ProductMasterMessageVO productMasterMessageVO = populateProductMasterMessageVO(productVo);
			if (productMasterMessageVO != null) {
				// adding vo to collection
				productMasterMessageVOs.add(productMasterMessageVO);
			}
			try {
				if (productMasterMessageVOs.size() > 0) {
					new MsgbrokerMessageProxy()
							.encodeMessages(productMasterMessageVOs);
				}
			} catch (ProxyException e) {
			}
		}
		log.exiting("ProductPublishBuilder", "updateProductStatus");
	}

	/**
	 * 
	 * @param productVO
	 * @return
	 * @throws SystemException
	 */
	private ProductMasterMessageVO populateProductMasterMessageVO(
			ProductVO productVO) throws SystemException {
		log.entering("ProductPublishBuilder", "populateProductMasterMessageVO");
		Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs = new ArrayList<PublishMessageEntityKeyVO>();
		ProductMasterMessageVO productMasterMessageVO = new ProductMasterMessageVO();
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();

		LogonAttributes logonAttributesVO = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		boolean isParentQualified = false;
		Map<String, String> businessKeys = new HashMap<String, String>();

		if (productVO.getOperationFlag() != null
				&& (productVO.getOperationFlag().equalsIgnoreCase(
						ProductMasterMessageVO.OPERATION_FLAG_INSERT)
						|| productVO.getOperationFlag().equalsIgnoreCase(
								ProductMasterMessageVO.OPERATION_FLAG_UPDATE) || productVO
						.getOperationFlag().equalsIgnoreCase(
								ProductMasterMessageVO.OPERATION_FLAG_DELETE))) {
			isParentQualified = true;
			businessKeys.put(ProductMasterMessageVO.BUSINESSKEY_PRDCOD,
					String.valueOf(productVO.getProductCode()));
			publishMessageEntityKeyVO
					.setEntityName(ProductMasterMessageVO.ENTITY_PRD);
			publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
			publishMessageEntityKeyVO.setOperationFlag(productVO
					.getOperationFlag());
			publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
		}

		Collection<ServicesMessageVO> servicesForProduct = populateServicesMessageVO(
				productVO.getServices(), productVO.getProductCode(),
				publishMessageEntityKeyVOs);
		if (servicesForProduct != null && servicesForProduct.size() > 0) {
			productMasterMessageVO.setServicesForProduct(servicesForProduct);
		}
		
			if(productVO.getOperationFlag()!=null && productVO.getOperationFlag().equals(ProductVO.OPERATION_FLAG_DELETE)){
				
				if(productVO.getTransportMode()!=null){
					for(ProductTransportModeVO transportmodeVO : productVO.getTransportMode())
					{
						transportmodeVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getPriority()!=null){
					for(ProductPriorityVO productPriorityVO : productVO.getPriority())
					{
						productPriorityVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getProductScc()!=null){
					for(ProductSCCVO productSCCVO : productVO.getProductScc())
					{
						productSCCVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getProductEvents()!=null){
					for(ProductEventVO eventVO : productVO.getProductEvents())
					{
						eventVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getRestrictionCommodity()!=null){
					for(RestrictionCommodityVO restrictionCommodityVO : productVO.getRestrictionCommodity())
					{
						restrictionCommodityVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getRestrictionSegment()!=null){
					for(RestrictionSegmentVO restrictionSegmentVO : productVO.getRestrictionSegment())
					{
						restrictionSegmentVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getRestrictionStation()!=null){
					for(RestrictionStationVO restrictionStationVO : productVO.getRestrictionStation())
					{
						restrictionStationVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getRestrictionCustomerGroup()!=null){
					for(RestrictionCustomerGroupVO customerGroupVO : productVO.getRestrictionCustomerGroup())
					{
						customerGroupVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				if(productVO.getRestrictionPaymentTerms()!=null){
					for(RestrictionPaymentTermsVO paymentTermsVO : productVO.getRestrictionPaymentTerms())
					{
						paymentTermsVO.setOperationFlag(ProductVO.OPERATION_FLAG_DELETE);
					}
				}
				
			}
		
		
		productMasterMessageVO
				.setTransportModesForProduct(populateTransportModesMessageVO(
						productVO.getTransportMode(),
						productVO.getProductCode(), publishMessageEntityKeyVOs));
		productMasterMessageVO
				.setPrioritiesForProduct(populatePrioritiesMessageVO(
						productVO.getPriority(), productVO.getProductCode(),
						publishMessageEntityKeyVOs));
		productMasterMessageVO.setSCCsForProduct(populateSCCsMessageVO(
				productVO.getProductScc(), productVO.getProductCode(),
				publishMessageEntityKeyVOs));
		
		Collection<EventsMessageVO> eventsForProduct = populateEventsMessageVO(
				productVO.getProductEvents(), productVO.getProductCode(),
				publishMessageEntityKeyVOs);
		if (eventsForProduct != null && eventsForProduct.size() > 0) {
			productMasterMessageVO.setEventsForProduct(eventsForProduct);
		}	
		Collection<CommodityRestrictionsMessageVO> comResForProduct = populateCommodityRestrictionsMessageVO(
						productVO.getRestrictionCommodity(),
				productVO.getProductCode(), publishMessageEntityKeyVOs);
		if (comResForProduct != null && comResForProduct.size() > 0) {
		productMasterMessageVO
				.setCommodityRestrictionsForProduct(comResForProduct);
		}	
		Collection<SegmentRestrictionsMessageVO> segResForProduct = populateSegmentRestrictionsMessageVO(
						productVO.getRestrictionSegment(),
				productVO.getProductCode(), publishMessageEntityKeyVOs);
		if (segResForProduct != null && segResForProduct.size() > 0) {
		productMasterMessageVO
				.setSegmentRestrictionsForProduct(segResForProduct);
		}	
		Collection<StationRestrictionsMessageVO> stnResForProduct = populateStationRestrictionsMessageVO(
						productVO.getRestrictionStation(),
				productVO.getProductCode(), publishMessageEntityKeyVOs);
		if (stnResForProduct != null && stnResForProduct.size() > 0) {
		productMasterMessageVO
				.setStationRestrictionsForProduct(stnResForProduct);
		}	
		Collection<CustomerGroupRestrictionsMessageVO> cusGrpResForProduct = populateCustomerGroupRestrictionsMessageVO(
						productVO.getRestrictionCustomerGroup(),
				productVO.getProductCode(), publishMessageEntityKeyVOs);
		if (cusGrpResForProduct != null && cusGrpResForProduct.size() > 0) {
		productMasterMessageVO
				.setCustomerGroupRestrictionsForProduct(cusGrpResForProduct);
		}	
		Collection<PaymentTermsRestrictionsMessageVO> payResForProduct = populatePaymentTermsRestrictionsMessageVO(
						productVO.getRestrictionPaymentTerms(),
				productVO.getProductCode(), publishMessageEntityKeyVOs);
		if (payResForProduct != null && payResForProduct.size() > 0) {
			productMasterMessageVO
				.setPaymentTermsRestrictionsForProduct(payResForProduct);
		}	

		if (isParentQualified
				|| (productMasterMessageVO.getServicesForProduct() != null && productMasterMessageVO
						.getServicesForProduct().size() > 0)
				|| (productMasterMessageVO.getTransportModesForProduct() != null && productMasterMessageVO
						.getTransportModesForProduct().size() > 0)
				|| (productMasterMessageVO.getPrioritiesForProduct() != null && productMasterMessageVO
						.getPrioritiesForProduct().size() > 0)
				|| (productMasterMessageVO.getSCCsForProduct() != null && productMasterMessageVO
						.getSCCsForProduct().size() > 0)
				|| (productMasterMessageVO.getEventsForProduct() != null && productMasterMessageVO
						.getEventsForProduct().size() > 0)
				|| (productMasterMessageVO.getCommodityRestrictionsForProduct() != null && productMasterMessageVO
						.getCommodityRestrictionsForProduct().size() > 0)
				|| (productMasterMessageVO.getSegmentRestrictionsForProduct() != null && productMasterMessageVO
						.getSegmentRestrictionsForProduct().size() > 0)
				|| (productMasterMessageVO.getStationRestrictionsForProduct() != null && productMasterMessageVO
						.getStationRestrictionsForProduct().size() > 0)
				|| (productMasterMessageVO
						.getCustomerGroupRestrictionsForProduct() != null && productMasterMessageVO
						.getCustomerGroupRestrictionsForProduct().size() > 0)
				|| (productMasterMessageVO
						.getPaymentTermsRestrictionsForProduct() != null && productMasterMessageVO
						.getPaymentTermsRestrictionsForProduct().size() > 0)) {

			productMasterMessageVO.setStationCode(logonAttributesVO
					.getAirportCode());
			productMasterMessageVO
					.setEntityName(ProductMasterMessageVO.ENTITY_PRD);
			productMasterMessageVO.setCreationTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, true));
			productMasterMessageVO.setEntityUpdateTime(new LocalDate(
					LocalDate.NO_STATION, Location.NONE, true));
			productMasterMessageVO.setCompanyCode(productVO.getCompanyCode());
			productMasterMessageVO
					.setMessageType(ProductMasterMessageVO.MESSAGE_TYPE_PRDMST);
			productMasterMessageVO
					.setMessageStandard(PublishDataMessageVO.MESSAGE_STANDARD);
			productMasterMessageVO
					.setPublishID(ProductMasterMessageVO.PRODUCT_MASTER_PUBLISHID);

			productMasterMessageVO.setProductCode(productVO.getProductCode());
			productMasterMessageVO.setProductName(productVO.getProductName());
			// Since the status of new Products in entity setting as NEW_STATUS
			if (productVO.getStatus() == null 
					|| productVO.getStatus().trim().length() == 0) {
				productMasterMessageVO.setStatus(ProductVO.NEWSTATUS);
			} else {
			productMasterMessageVO.setStatus(productVO.getStatus());
			}

			productMasterMessageVO.setDescription(productVO.getDescription());
			if (productVO.getDetailedDescription() != null
					&& productVO.getDetailedDescription().trim().length() > 0) {
			productMasterMessageVO.setDetailedDescription(productVO
					.getDetailedDescription());
			}
			productMasterMessageVO
					.setBookingIndicator(productVO.isBookingMandatory() ? ProductMasterMessageVO.FLAG_YES
							: ProductMasterMessageVO.FLAG_NO);
			productMasterMessageVO.setStartDate(productVO.getStartDate());
			productMasterMessageVO.setEndDate(productVO.getEndDate());
			if (productVO.getHandlingInfo() != null
					&& productVO.getHandlingInfo().trim().length() > 0) {
			productMasterMessageVO.setHandlingInfo(productVO.getHandlingInfo());
			}
			if (productVO.getRemarks() != null
					&& productVO.getRemarks().trim().length() > 0) {
			productMasterMessageVO.setRemarks(productVO.getRemarks());
			}
			productMasterMessageVO.setMinimumDisplayWeight(productVO
					.getMinimumWeightDisplay());
			productMasterMessageVO.setMinimumWeight(productVO
					.getMinimumWeight());
			productMasterMessageVO.setMaximumDisplayWeight(productVO
					.getMaximumWeightDisplay());
			productMasterMessageVO.setDisplayWeightCode(productVO
					.getDisplayWeightCode());
			productMasterMessageVO.setMaximumWeight(productVO
					.getMaximumWeight());
			productMasterMessageVO.setMinimumDisplayVolume(productVO
					.getMinimumVolumeDisplay());
			productMasterMessageVO.setMinimumVolume(productVO
					.getMinimumVolume());
			productMasterMessageVO.setMaximumDisplayVolume(productVO
					.getMaximumVolumeDisplay());
			productMasterMessageVO.setDisplayVolumeCode(productVO
					.getDisplayVolumeCode());
			productMasterMessageVO.setMaximumVolume(productVO
					.getMaximumVolume());
			if (productVO.getAdditionalRestrictions() != null
					&& productVO.getAdditionalRestrictions().trim().length() > 0) {
			productMasterMessageVO.setAdditionalRestrictions(productVO
					.getAdditionalRestrictions());
			}
			productMasterMessageVO
					.setIsRateDefined(productVO.getIsRateDefined() ? ProductMasterMessageVO.FLAG_YES
							: ProductMasterMessageVO.FLAG_NO);
			if (productVO.getDocumentType() != null
					&& productVO.getDocumentType().trim().length() > 0) {
			productMasterMessageVO.setDocumentType(productVO.getDocumentType());
			}
			if (productVO.getDocumentSubType() != null
					&& productVO.getDocumentSubType().trim().length() > 0) {
			productMasterMessageVO.setDocumentSubType(productVO
					.getDocumentSubType());
			}
			
			productMasterMessageVO.setProactiveMilestoneEnabled(productVO
					.getProactiveMilestoneEnabled());
			productMasterMessageVO
					.setPublishMessageEntityKeys(publishMessageEntityKeyVOs);

			log.exiting("ProductPublishBuilder",
					"populateProductMasterMessageVO");
			return productMasterMessageVO;
		}
		log.exiting("ProductPublishBuilder", "populateProductMasterMessageVO");
		return null;
	}

	/**
	 * 
	 * @param services
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<ServicesMessageVO> populateServicesMessageVO(
			Collection<ProductServiceVO> services, String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {

		log.entering("ProductPublishBuilder", "populateServicesMessageVO");
		ServicesMessageVO servicesMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (services != null) {
			Collection<ServicesMessageVO> productService = new ArrayList<ServicesMessageVO>();
			for (ProductServiceVO productServiceVO : services) {
				if (productServiceVO.getOperationFlag() != null
						&& (productServiceVO
								.getOperationFlag()
								.equalsIgnoreCase(
										ServicesMessageVO.OPERATION_FLAG_INSERT)
								|| productServiceVO
										.getOperationFlag()
										.equalsIgnoreCase(
												ServicesMessageVO.OPERATION_FLAG_UPDATE) || productServiceVO
								.getOperationFlag()
								.equalsIgnoreCase(
										ServicesMessageVO.OPERATION_FLAG_DELETE))) {
					servicesMessageVO = new ServicesMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(ServicesMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(ServicesMessageVO.BUSINESSKEY_SERCOD,
							productServiceVO.getServiceCode());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDSER);
					publishMessageEntityKeyVO.setOperationFlag(productServiceVO
							.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					servicesMessageVO.setProductCode(productCode);
					servicesMessageVO.setServiceCode(productServiceVO
							.getServiceCode());
					productService.add(servicesMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder", "populateServicesMessageVO");
			return productService;
		}
		log.exiting("ProductPublishBuilder", "populateServicesMessageVO");
		return null;
	}

	/**
	 * 
	 * @param transportMode
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<TransportModesMessageVO> populateTransportModesMessageVO(
			Collection<ProductTransportModeVO> transportMode,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder", "populateTransportModesMessageVO");
		TransportModesMessageVO transportModesMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (transportMode != null) {
			Collection<TransportModesMessageVO> transportModes = new ArrayList<TransportModesMessageVO>();
			for (ProductTransportModeVO productTransportModeVO : transportMode) {
				if (productTransportModeVO.getOperationFlag() == null){
					productTransportModeVO.setOperationFlag(ProductTransportModeVO.OPERATION_FLAG_UPDATE);
				}
				if (productTransportModeVO.getOperationFlag() != null
						&& (productTransportModeVO
								.getOperationFlag()
								.equalsIgnoreCase(
										TransportModesMessageVO.OPERATION_FLAG_INSERT)
								|| productTransportModeVO
										.getOperationFlag()
										.equalsIgnoreCase(
												TransportModesMessageVO.OPERATION_FLAG_UPDATE) || productTransportModeVO
								.getOperationFlag()
								.equalsIgnoreCase(
										TransportModesMessageVO.OPERATION_FLAG_DELETE))) {
					transportModesMessageVO = new TransportModesMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(
							TransportModesMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(
							TransportModesMessageVO.BUSINESSKEY_TRSMOD,
							productTransportModeVO.getTransportMode());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDTRNMOD);
					publishMessageEntityKeyVO
							.setOperationFlag(productTransportModeVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					transportModesMessageVO.setProductCode(productCode);
					transportModesMessageVO
							.setTransportMode(productTransportModeVO
									.getTransportMode());
					transportModes.add(transportModesMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populateTransportModesMessageVO");
			return transportModes;
		}
		log.exiting("ProductPublishBuilder", "populateTransportModesMessageVO");
		return null;
	}

	/**
	 * 
	 * @param priority
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<PrioritiesMessageVO> populatePrioritiesMessageVO(
			Collection<ProductPriorityVO> priority, String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder", "populatePrioritiesMessageVO");
		PrioritiesMessageVO prioritiesMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (priority != null) {
			Collection<PrioritiesMessageVO> productPriority = new ArrayList<PrioritiesMessageVO>();
			for (ProductPriorityVO productPriorityVO : priority) {
				if (productPriorityVO.getOperationFlag() == null) {
					productPriorityVO
							.setOperationFlag(ProductPriorityVO.OPERATION_FLAG_UPDATE);
				}
				if (productPriorityVO.getOperationFlag() != null
						&& (productPriorityVO
								.getOperationFlag()
								.equalsIgnoreCase(
										PrioritiesMessageVO.OPERATION_FLAG_INSERT)
								|| productPriorityVO
										.getOperationFlag()
										.equalsIgnoreCase(
												PrioritiesMessageVO.OPERATION_FLAG_UPDATE) || productPriorityVO
								.getOperationFlag()
								.equalsIgnoreCase(
										PrioritiesMessageVO.OPERATION_FLAG_DELETE))) {
					prioritiesMessageVO = new PrioritiesMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(PrioritiesMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(PrioritiesMessageVO.BUSINESSKEY_PRIORITY,
							productPriorityVO.getPriority());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDPRI);
					publishMessageEntityKeyVO
							.setOperationFlag(productPriorityVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					prioritiesMessageVO.setProductCode(productCode);
					prioritiesMessageVO.setPriority(productPriorityVO
							.getPriority());
					productPriority.add(prioritiesMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder", "populatePrioritiesMessageVO");
			return productPriority;
		}
		log.exiting("ProductPublishBuilder", "populatePrioritiesMessageVO");
		return null;
	}

	/**
	 * 
	 * @param productScc
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<SCCsMessageVO> populateSCCsMessageVO(
			Collection<ProductSCCVO> productScc, String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder", "populateSCCsMessageVO");
		SCCsMessageVO sccsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (productScc != null) {
			Collection<SCCsMessageVO> sccProduct = new ArrayList<SCCsMessageVO>();
			for (ProductSCCVO productSCCVO : productScc) {
				if (productSCCVO.getOperationFlag() == null){
					productSCCVO.setOperationFlag(ProductSCCVO.OPERATION_FLAG_UPDATE);
				}
				if (productSCCVO.getOperationFlag() != null
						&& (productSCCVO.getOperationFlag().equalsIgnoreCase(
								SCCsMessageVO.OPERATION_FLAG_INSERT)
								|| productSCCVO
										.getOperationFlag()
										.equalsIgnoreCase(
												SCCsMessageVO.OPERATION_FLAG_UPDATE) || productSCCVO
								.getOperationFlag().equalsIgnoreCase(
										SCCsMessageVO.OPERATION_FLAG_DELETE))) {
					sccsMessageVO = new SCCsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(SCCsMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(SCCsMessageVO.BUSINESSKEY_SCCCOD,
							productSCCVO.getScc());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDSCC);
					publishMessageEntityKeyVO.setOperationFlag(productSCCVO
							.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					sccsMessageVO.setProductCode(productCode);
					sccsMessageVO.setSccCode(productSCCVO.getScc());
					sccProduct.add(sccsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder", "populateSCCsMessageVO");
			return sccProduct;
		}
		log.exiting("ProductPublishBuilder", "populateSCCsMessageVO");
		return null;
	}

	/**
	 * 
	 * @param productEvents
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<EventsMessageVO> populateEventsMessageVO(
			Collection<ProductEventVO> productEvents, String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder", "populateEventsMessageVO");
		EventsMessageVO eventsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (productEvents != null) {
			Collection<EventsMessageVO> productEvent = new ArrayList<EventsMessageVO>();
			for (ProductEventVO productEventVO : productEvents) {
				if (productEventVO.getOperationFlag() != null
						&& (productEventVO.getOperationFlag().equalsIgnoreCase(
								EventsMessageVO.OPERATION_FLAG_INSERT)
								|| productEventVO
										.getOperationFlag()
										.equalsIgnoreCase(
												EventsMessageVO.OPERATION_FLAG_UPDATE) || productEventVO
								.getOperationFlag().equalsIgnoreCase(
										EventsMessageVO.OPERATION_FLAG_DELETE))) {
					eventsMessageVO = new EventsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(EventsMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(EventsMessageVO.BUSINESSKEY_EVTCOD,
							productEventVO.getEventCode());
					
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDEVT);
					publishMessageEntityKeyVO.setOperationFlag(productEventVO
							.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					eventsMessageVO.setProductCode(productCode);
					eventsMessageVO.setEventCode(productEventVO.getEventCode());
					eventsMessageVO.setEventType(productEventVO.getEventType());
					if (productEventVO.getMinimumTimeStr() != null
							&& productEventVO.getMinimumTimeStr().trim()
									.length() > 0) {
						eventsMessageVO.setMinimumTime(productEventVO
								.getMinimumTimeStr());

					} else if (productEventVO.getMaximumTime() > 0) { //Added else if as part of bug ICRD-65453
						eventsMessageVO
								.setMinimumTime(findTimeString((int) productEventVO
										.getMinimumTime()));
					}
					if (productEventVO.getMaximumTimeStr() != null
							&& productEventVO.getMaximumTimeStr().trim()
									.length() > 0) {
						eventsMessageVO.setMaximumTime(productEventVO
								.getMaximumTimeStr());

					} else if (productEventVO.getMaximumTime() > 0) { //Added else if as part of bug ICRD-65453
						eventsMessageVO
								.setMaximumTime(findTimeString((int) productEventVO
										.getMaximumTime()));
					}
					eventsMessageVO.setDuration(productEventVO.getDuration());
					eventsMessageVO
							.setIsExternal(productEventVO.isExternal() ? EventsMessageVO.FLAG_YES
									: EventsMessageVO.FLAG_NO);
					eventsMessageVO.setIsExport(productEventVO.getIsExport());
					eventsMessageVO.setAlertTime((int)productEventVO.getAlertTime());
					eventsMessageVO.setChaserTime((int)productEventVO
							.getChaserTime());
					eventsMessageVO.setChaserFrequency((int) productEventVO
							.getChaserFrequency());
					eventsMessageVO.setMaxNoOfChasers(productEventVO
							.getMaxNoOfChasers());
					eventsMessageVO
							.setIsInternal(productEventVO.isInternal() ? EventsMessageVO.FLAG_YES
									: EventsMessageVO.FLAG_NO);
					eventsMessageVO
							.setTransitFlag(productEventVO.isTransit() ? EventsMessageVO.FLAG_YES
									: EventsMessageVO.FLAG_NO);
					productEvent.add(eventsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder", "populateEventsMessageVO");
			return productEvent;
		}
		log.exiting("ProductPublishBuilder", "populateEventsMessageVO");
		return null;
	}
	
	/**
	 * Method to return time string getting number of minutes as input.
	 *
	 * @param minutes the minutes
	 * @return the string
	 * @author A-5265
	 */
	private String findTimeString(int minutes){
		StringBuilder timeString = new StringBuilder();
		int hours = 0;
		int mins = 0;
		if(minutes>60){
			hours = minutes/60;
			mins = minutes%60;
			if(hours<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(hours));
			timeString.append(TIME_SEPERATOR);
			if(mins<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(mins));				
		}else{
			timeString.append(ZERO).append(ZERO);
			timeString.append(TIME_SEPERATOR);
			if(minutes<10){
				timeString.append(ZERO);
			}
			timeString.append(String.valueOf(minutes));
		}
		return timeString.toString();
	}

	/**
	 * 
	 * @param restrictionCommodity
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<CommodityRestrictionsMessageVO> populateCommodityRestrictionsMessageVO(
			Collection<RestrictionCommodityVO> restrictionCommodity,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder",
				"populateCommodityRestrictionsMessageVO");
		CommodityRestrictionsMessageVO commodityRestrictionsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (restrictionCommodity != null) {
			Collection<CommodityRestrictionsMessageVO> restrictionCommodityProduct = new ArrayList<CommodityRestrictionsMessageVO>();
			for (RestrictionCommodityVO restrictionCommodityVO : restrictionCommodity) {
				if (restrictionCommodityVO.getOperationFlag() != null
						&& (restrictionCommodityVO
								.getOperationFlag()
								.equalsIgnoreCase(
										CommodityRestrictionsMessageVO.OPERATION_FLAG_INSERT)
								|| restrictionCommodityVO
										.getOperationFlag()
										.equalsIgnoreCase(
												CommodityRestrictionsMessageVO.OPERATION_FLAG_UPDATE) || restrictionCommodityVO
								.getOperationFlag()
								.equalsIgnoreCase(
										CommodityRestrictionsMessageVO.OPERATION_FLAG_DELETE))) {
					commodityRestrictionsMessageVO = new CommodityRestrictionsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(
							CommodityRestrictionsMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(
							CommodityRestrictionsMessageVO.BUSINESSKEY_CMDCOD,
							restrictionCommodityVO.getCommodity());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDRESCMD);
					publishMessageEntityKeyVO
							.setOperationFlag(restrictionCommodityVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					commodityRestrictionsMessageVO.setProductCode(productCode);
					commodityRestrictionsMessageVO
							.setCommodityCode(restrictionCommodityVO
									.getCommodity());
					commodityRestrictionsMessageVO
							.setIsRestricted(restrictionCommodityVO.getIsRestricted() ? CommodityRestrictionsMessageVO.FLAG_YES
									: CommodityRestrictionsMessageVO.FLAG_NO);
					restrictionCommodityProduct
							.add(commodityRestrictionsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populateCommodityRestrictionsMessageVO");
			return restrictionCommodityProduct;
		}
		log.exiting("ProductPublishBuilder",
				"populateCommodityRestrictionsMessageVO");
		return null;
	}

	/**
	 * 
	 * @param restrictionSegment
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<SegmentRestrictionsMessageVO> populateSegmentRestrictionsMessageVO(
			Collection<RestrictionSegmentVO> restrictionSegment,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder",
				"populateSegmentRestrictionsMessageVO");
		SegmentRestrictionsMessageVO segmentRestrictionsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (restrictionSegment != null) {
			Collection<SegmentRestrictionsMessageVO> restrictionSegmentProduct = new ArrayList<SegmentRestrictionsMessageVO>();
			for (RestrictionSegmentVO restrictionSegmentVO : restrictionSegment) {
				if (restrictionSegmentVO.getOperationFlag() != null
						&& (restrictionSegmentVO
								.getOperationFlag()
								.equalsIgnoreCase(
										SegmentRestrictionsMessageVO.OPERATION_FLAG_INSERT)
								|| restrictionSegmentVO
										.getOperationFlag()
										.equalsIgnoreCase(
												SegmentRestrictionsMessageVO.OPERATION_FLAG_UPDATE) || restrictionSegmentVO
								.getOperationFlag()
								.equalsIgnoreCase(
										SegmentRestrictionsMessageVO.OPERATION_FLAG_DELETE))) {
					segmentRestrictionsMessageVO = new SegmentRestrictionsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(
							SegmentRestrictionsMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(
							SegmentRestrictionsMessageVO.BUSINESSKEY_ORGCOD,
							restrictionSegmentVO.getOrigin().toUpperCase());
					businessKeys.put(
							SegmentRestrictionsMessageVO.BUSINESSKEY_DSTCOD,
							restrictionSegmentVO.getDestination().toUpperCase());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDRESSEG);
					publishMessageEntityKeyVO
							.setOperationFlag(restrictionSegmentVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					segmentRestrictionsMessageVO.setProductCode(productCode);
					segmentRestrictionsMessageVO
							.setOriginCode(restrictionSegmentVO.getOrigin().toUpperCase());
					segmentRestrictionsMessageVO
							.setDestinationCode(restrictionSegmentVO
									.getDestination().toUpperCase());
					segmentRestrictionsMessageVO
							.setIsRestricted(restrictionSegmentVO
									.getIsRestricted() == true ? SegmentRestrictionsMessageVO.FLAG_YES
									: SegmentRestrictionsMessageVO.FLAG_NO);
					restrictionSegmentProduct.add(segmentRestrictionsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populateSegmentRestrictionsMessageVO");
			return restrictionSegmentProduct;
		}
		log.exiting("ProductPublishBuilder",
				"populateSegmentRestrictionsMessageVO");
		return null;
	}

	/**
	 * 
	 * @param restrictionStation
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<StationRestrictionsMessageVO> populateStationRestrictionsMessageVO(
			Collection<RestrictionStationVO> restrictionStation,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder",
				"populateStationRestrictionsMessageVO");
		StationRestrictionsMessageVO stationRestrictionsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (restrictionStation != null) {
			Collection<StationRestrictionsMessageVO> restrictionStationProduct = new ArrayList<StationRestrictionsMessageVO>();
			for (RestrictionStationVO restrictionStationVO : restrictionStation) {
				if (restrictionStationVO.getOperationFlag() != null
						&& (restrictionStationVO
								.getOperationFlag()
								.equalsIgnoreCase(
										StationRestrictionsMessageVO.OPERATION_FLAG_INSERT)
								|| restrictionStationVO
										.getOperationFlag()
										.equalsIgnoreCase(
												StationRestrictionsMessageVO.OPERATION_FLAG_UPDATE) || restrictionStationVO
								.getOperationFlag()
								.equalsIgnoreCase(
										StationRestrictionsMessageVO.OPERATION_FLAG_DELETE))) {
					stationRestrictionsMessageVO = new StationRestrictionsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys.put(
							StationRestrictionsMessageVO.BUSINESSKEY_PRDCOD,
							productCode);
					businessKeys.put(
							StationRestrictionsMessageVO.BUSINESSKEY_STNCOD,
							restrictionStationVO.getStation());
					businessKeys.put(
							StationRestrictionsMessageVO.BUSINESSKEY_ISORIGIN,
							restrictionStationVO.getIsOrigin() ? StationRestrictionsMessageVO.FLAG_YES
									: StationRestrictionsMessageVO.FLAG_NO);
					
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDRESSTN);
					publishMessageEntityKeyVO
							.setOperationFlag(restrictionStationVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					stationRestrictionsMessageVO.setProductCode(productCode);
					if(restrictionStationVO.getStation() != null) {
						stationRestrictionsMessageVO
							.setStationCode(restrictionStationVO.getStation().toUpperCase());
					}
					stationRestrictionsMessageVO
							.setIsOrigin(restrictionStationVO.getIsOrigin() ? StationRestrictionsMessageVO.FLAG_YES
									: StationRestrictionsMessageVO.FLAG_NO);
					stationRestrictionsMessageVO
							.setIsRestricted(restrictionStationVO.getIsRestricted() ? StationRestrictionsMessageVO.FLAG_YES
									: StationRestrictionsMessageVO.FLAG_NO);
					restrictionStationProduct.add(stationRestrictionsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populateStationRestrictionsMessageVO");
			return restrictionStationProduct;
		}
		log.exiting("ProductPublishBuilder",
				"populateStationRestrictionsMessageVO");
		return null;
	}

	/**
	 * 
	 * @param restrictionCustomerGroup
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<CustomerGroupRestrictionsMessageVO> populateCustomerGroupRestrictionsMessageVO(
			Collection<RestrictionCustomerGroupVO> restrictionCustomerGroup,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder",
				"populateCustomerGroupRestrictionsMessageVO");
		CustomerGroupRestrictionsMessageVO customerGroupRestrictionsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (restrictionCustomerGroup != null) {
			Collection<CustomerGroupRestrictionsMessageVO> restrictionCustomerGroupProduct = new ArrayList<CustomerGroupRestrictionsMessageVO>();
			for (RestrictionCustomerGroupVO restrictionCustomerGroupVO : restrictionCustomerGroup) {
				if (restrictionCustomerGroupVO.getOperationFlag() != null
						&& (restrictionCustomerGroupVO
								.getOperationFlag()
								.equalsIgnoreCase(
										CustomerGroupRestrictionsMessageVO.OPERATION_FLAG_INSERT)
								|| restrictionCustomerGroupVO
										.getOperationFlag()
										.equalsIgnoreCase(
												CustomerGroupRestrictionsMessageVO.OPERATION_FLAG_UPDATE) || restrictionCustomerGroupVO
								.getOperationFlag()
								.equalsIgnoreCase(
										CustomerGroupRestrictionsMessageVO.OPERATION_FLAG_DELETE))) {
					customerGroupRestrictionsMessageVO = new CustomerGroupRestrictionsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys
							.put(CustomerGroupRestrictionsMessageVO.BUSINESSKEY_PRDCOD,
									productCode);
					businessKeys
							.put(CustomerGroupRestrictionsMessageVO.BUSINESSKEY_CUSGRP,
									restrictionCustomerGroupVO
											.getCustomerGroup());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDRESCUSGRP);
					publishMessageEntityKeyVO
							.setOperationFlag(restrictionCustomerGroupVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					customerGroupRestrictionsMessageVO
							.setProductCode(productCode);
					customerGroupRestrictionsMessageVO
							.setCustomerGroup(restrictionCustomerGroupVO
									.getCustomerGroup());
					customerGroupRestrictionsMessageVO
							.setIsRestricted(restrictionCustomerGroupVO.getIsRestricted() ? CustomerGroupRestrictionsMessageVO.FLAG_YES
									: CustomerGroupRestrictionsMessageVO.FLAG_NO);
					restrictionCustomerGroupProduct
							.add(customerGroupRestrictionsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populateCustomerGroupRestrictionsMessageVO");
			return restrictionCustomerGroupProduct;
		}
		log.exiting("ProductPublishBuilder",
				"populateCustomerGroupRestrictionsMessageVO");
		return null;
	}

	/**
	 * 
	 * @param restrictionPaymentTerms
	 * @param productCode
	 * @param publishMessageEntityKeyVOs
	 * @return
	 */
	private Collection<PaymentTermsRestrictionsMessageVO> populatePaymentTermsRestrictionsMessageVO(
			Collection<RestrictionPaymentTermsVO> restrictionPaymentTerms,
			String productCode,
			Collection<PublishMessageEntityKeyVO> publishMessageEntityKeyVOs) {
		log.entering("ProductPublishBuilder",
				"populatePaymentTermsRestrictionsMessageVO");
		PaymentTermsRestrictionsMessageVO paymentTermsRestrictionsMessageVO = null;
		Map<String, String> businessKeys = null;
		PublishMessageEntityKeyVO publishMessageEntityKeyVO = null;
		if (restrictionPaymentTerms != null) {
			Collection<PaymentTermsRestrictionsMessageVO> restrictionPaymentTermsProduct = new ArrayList<PaymentTermsRestrictionsMessageVO>();
			for (RestrictionPaymentTermsVO restrictionPaymentTermsVO : restrictionPaymentTerms) {
				if (restrictionPaymentTermsVO.getOperationFlag() != null
						&& (restrictionPaymentTermsVO
								.getOperationFlag()
								.equalsIgnoreCase(
										PaymentTermsRestrictionsMessageVO.OPERATION_FLAG_INSERT)
								|| restrictionPaymentTermsVO
										.getOperationFlag()
										.equalsIgnoreCase(
												PaymentTermsRestrictionsMessageVO.OPERATION_FLAG_UPDATE) || restrictionPaymentTermsVO
								.getOperationFlag()
								.equalsIgnoreCase(
										PaymentTermsRestrictionsMessageVO.OPERATION_FLAG_DELETE))) {
					paymentTermsRestrictionsMessageVO = new PaymentTermsRestrictionsMessageVO();
					businessKeys = new HashMap<String, String>();
					publishMessageEntityKeyVO = new PublishMessageEntityKeyVO();
					businessKeys
							.put(PaymentTermsRestrictionsMessageVO.BUSINESSKEY_PRDCOD,
									productCode);
					businessKeys
							.put(PaymentTermsRestrictionsMessageVO.BUSINESSKEY_PAYTRM,
									restrictionPaymentTermsVO.getPaymentTerm());
					publishMessageEntityKeyVO.setBusinessKeys(businessKeys);
					publishMessageEntityKeyVO
							.setEntityName(ProductMasterMessageVO.ENTITY_PRDRESPAYTRM);
					publishMessageEntityKeyVO
							.setOperationFlag(restrictionPaymentTermsVO
									.getOperationFlag());
					publishMessageEntityKeyVOs.add(publishMessageEntityKeyVO);
					paymentTermsRestrictionsMessageVO
							.setProductCode(productCode);
					paymentTermsRestrictionsMessageVO
							.setPaymentTerm(restrictionPaymentTermsVO
									.getPaymentTerm());
					paymentTermsRestrictionsMessageVO
							.setIsRestricted(restrictionPaymentTermsVO.getIsRestricted() ? PaymentTermsRestrictionsMessageVO.FLAG_YES
									: PaymentTermsRestrictionsMessageVO.FLAG_NO);
					restrictionPaymentTermsProduct
							.add(paymentTermsRestrictionsMessageVO);
				}
			}
			log.exiting("ProductPublishBuilder",
					"populatePaymentTermsRestrictionsMessageVO");
			return restrictionPaymentTermsProduct;
		}
		log.exiting("ProductPublishBuilder",
				"populatePaymentTermsRestrictionsMessageVO");
		return null;
	}
}
