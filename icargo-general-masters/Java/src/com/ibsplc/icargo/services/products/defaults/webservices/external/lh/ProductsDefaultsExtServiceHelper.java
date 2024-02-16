package com.ibsplc.icargo.services.products.defaults.webservices.external.lh;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ibsplc.ibase.util.time.TimeConvertor;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.BookableChannelType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.ErrorDetailType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingResponseData;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingResponseData;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.ProductModelType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.ProductODMappingType;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.business.shared.defaults.types.standard.MessageHeaderType;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class ProductsDefaultsExtServiceHelper {
	
	private static final String ERROR_NO_RECORD_FOUND ="product.defaults.nomappingfound";
	private static final String STATUS_SUCCESS = "S";
	private static final String STATUS_FAIL = "F";
	private static final String MESSAGE_TYPE_D = "D";
	private static final String SOURCE_SYSTEM_ICARGO = "ICARGO";
	private static final String DATANDTIM_FORMAT = "dd-MMM-yyyy";
	private static final String PATTERN = "\\{([0-9]{1}[0-9]*?)\\}";
	private static final String RESOURCEBUNDLE =
	    		"resources.products.defaults.MaintainProduct_en_US";
	    private static final Locale LOCALE = Locale.US;
	
	public ProductModelMappingFilterVO populateProductModelMappingFilterVo(
			GetProductModelMappingRequestType getProductModelMappingRequestType)
							throws SystemException{
		LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
		ProductModelMappingFilterVO productModelMappingFilterVO = null;
		if(getProductModelMappingRequestType!=null){
			productModelMappingFilterVO = new ProductModelMappingFilterVO();
			productModelMappingFilterVO.setCompanyCode(logonVO.getCompanyCode());
			
			
		}
		return productModelMappingFilterVO;
	}
	/**
	 * 
	 * @param productModelMappingVOs
	 * @param requestId
	 * @param errorDetailTypes
	 * @return
	 */
	GetProductModelMappingResponseType constructGetProductModelMappingResponseType(
			 Collection<ProductModelMappingVO> productModelMappingVOs, String requestId,Collection<ErrorDetailType> errorDetailTypes){
		MessageHeaderType messageHeaderType = new MessageHeaderType();
		messageHeaderType.setMessageType(MESSAGE_TYPE_D);
		messageHeaderType.setSourceSystem(SOURCE_SYSTEM_ICARGO);
		GetProductModelMappingResponseType getProductModelMappingResponseType = 
				new GetProductModelMappingResponseType();
		GetProductModelMappingResponseData getProductModelMappingResponseData = 
				new GetProductModelMappingResponseData();
		getProductModelMappingResponseData.setRequestId(requestId);
		if(productModelMappingVOs!=null){
			getProductModelMappingResponseData.getProductModelMappingDetails().addAll(new ArrayList<ProductModelType>());
			populateProductModelMappingData(productModelMappingVOs,getProductModelMappingResponseData);
			
		} else if (errorDetailTypes == null || errorDetailTypes.size() == 0) {
			 errorDetailTypes = new ArrayList<ErrorDetailType>();
			 ErrorVO error = new ErrorVO(ERROR_NO_RECORD_FOUND);
			 handleErrors(error, errorDetailTypes);
		 } if (errorDetailTypes != null && errorDetailTypes.size() > 0){
			 getProductModelMappingResponseData.setStatus(STATUS_FAIL);
			getProductModelMappingResponseData.getErrorDetails().addAll(errorDetailTypes);
		 } else {
			 getProductModelMappingResponseData.setStatus(STATUS_SUCCESS);
		 }
		 getProductModelMappingResponseType.setMessageHeader(messageHeaderType);
		 getProductModelMappingResponseType.setResponseDetails(getProductModelMappingResponseData);
		
		return getProductModelMappingResponseType;
	}
	
	
	/**
	 * 
	 * @param productModelMappingVOs
	 * @param getProductModelMappingResponseData
	 */
	private void populateProductModelMappingData(
			 Collection<ProductModelMappingVO> productModelMappingVOs,
			 GetProductModelMappingResponseData getProductModelMappingResponseData) {
		
		ProductModelType productModelType = null;
		for (ProductModelMappingVO productModelMappingVO : productModelMappingVOs) {
			Collection<BookableChannelType> bookableChannelTypesForCommodity = new ArrayList<BookableChannelType>();
			Collection<BookableChannelType> bookableChannelTypesForProduct = new ArrayList<BookableChannelType>();
			BookableChannelType bookableChannelType =null;
			productModelType = new ProductModelType();
			productModelType.getCommodityChannels().addAll(new ArrayList<BookableChannelType>());
			productModelType.getProductChannels().addAll(new ArrayList<BookableChannelType>());
			productModelType.setCommodityCode(productModelMappingVO.getCommodityName());
			productModelType.setCommodityGroup(productModelMappingVO.getCommodityGroup());
			productModelType.setProductName(productModelMappingVO.getProductName());
			productModelType.setProductPriority(productModelMappingVO.getProductPriority());
			productModelType.setProductGroup(productModelMappingVO.getProductGroup());
			productModelType.setCommodityDescription(productModelMappingVO.getCommodityDescription());
			productModelType.setCommodityGroupDescription(productModelMappingVO.getCommodityGroupDescription());
			productModelType.setProductGroupDescription(productModelMappingVO.getProductGroupDescription());
			productModelType.setAttributeName(productModelMappingVO.getAttributeName());
			productModelType.setRecommendedProductGroup(productModelMappingVO.getRecProductGroup());
			productModelType.setRecommendedPriority(productModelMappingVO.getRecProductPriority());
			productModelType.setPossibleBookingType(productModelMappingVO.getPossibleBookingType());
			if(productModelMappingVO.getConsolShipment() !=null){
				productModelType.setConsolShipment(productModelMappingVO.getConsolShipment());
			}	
			if(productModelMappingVO.getProductStartDate() != null){
				String productStartDate = TimeConvertor.toStringFormat(
						productModelMappingVO.getProductStartDate().toCalendar(), DATANDTIM_FORMAT);
				productModelType.setProductStartDate(productStartDate);
				
			}
			if(productModelMappingVO.getProductEndDate() != null){
				String ProductEndDate = TimeConvertor.toStringFormat(
						productModelMappingVO.getProductEndDate().toCalendar(), DATANDTIM_FORMAT);
				productModelType.setProductEndDate(ProductEndDate);
				
			}			
			if(productModelMappingVO.getIscommodityValidForBookingChannelEDI()!=null && 
					productModelMappingVO.getIscommodityValidForBookingChannelEDI().length()>0){
			    bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("EDI");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIscommodityValidForBookingChannelEDI());
				bookableChannelTypesForCommodity.add(bookableChannelType);
			}
			if(productModelMappingVO.getIscommodityValidForBookingChannelEbooking()!=null && 
					productModelMappingVO.getIscommodityValidForBookingChannelEbooking().length()>0){
				bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("Ebooking");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIscommodityValidForBookingChannelEbooking());
				bookableChannelTypesForCommodity.add(bookableChannelType);	
			}
			if(productModelMappingVO.getIscommodityAvailableInSoCo()!=null && 
					productModelMappingVO.getIscommodityAvailableInSoCo().length()>0){
				bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("SOCO");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIscommodityAvailableInSoCo());
				bookableChannelTypesForCommodity.add(bookableChannelType);	
			}
			if(bookableChannelTypesForCommodity!=null && bookableChannelTypesForCommodity.size()>0){
			productModelType.getCommodityChannels().addAll(bookableChannelTypesForCommodity);
			}
			
			if(productModelMappingVO.getIsProductValidForBookingChannelEDI()!=null && 
					productModelMappingVO.getIsProductValidForBookingChannelEDI().length()>0){
				bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("EDI");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIsProductValidForBookingChannelEDI());
				bookableChannelTypesForProduct.add(bookableChannelType);	
			}
			if(productModelMappingVO.getIsProductValidForBookingChannelEbooking()!=null && 
					productModelMappingVO.getIsProductValidForBookingChannelEbooking().length()>0){
				bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("Ebooking");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIsProductValidForBookingChannelEbooking());
				bookableChannelTypesForProduct.add(bookableChannelType);	
			}
			if(productModelMappingVO.getIsProductAvailableInSoCo()!=null && 
					productModelMappingVO.getIsProductAvailableInSoCo().length()>0){
				bookableChannelType = new BookableChannelType();
				bookableChannelType.setChannelName("SOCO");
				bookableChannelType.setBookableFlag(productModelMappingVO.getIsProductAvailableInSoCo());
				bookableChannelTypesForProduct.add(bookableChannelType);	
			}
			if(bookableChannelTypesForProduct!=null && bookableChannelTypesForProduct.size()>0){
			productModelType.getProductChannels().addAll(bookableChannelTypesForProduct);
			}
			if(productModelMappingVO.getUpsellingProductCodes() !=null){
				productModelType.setUpsellingProductCode(productModelMappingVO.getUpsellingProductCodes());
			}
			getProductModelMappingResponseData.getProductModelMappingDetails().add(productModelType);
			
		
			
			
		}
		
	}
	/**
	 * 
	 * @param error
	 * @param errorDetailTypes
	 */
	private void handleErrors(ErrorVO error , Collection<ErrorDetailType> errorDetailTypes){
		
		String errorDescription = null;
		ErrorDetailType errorType = new ErrorDetailType();
		errorType.setErrorCode(error.getErrorCode());
		try{
			ResourceBundle resourceBundle = ResourceBundle.getBundle(
					RESOURCEBUNDLE, LOCALE);
			errorDescription = resourceBundle.getString(error.getErrorCode());
			
		}catch(Exception exception) {
			
		}
		if ((errorDescription == null)
				|| (errorDescription.trim().length() == 0)) {
			errorDescription = error.getErrorCode();
		}else {
			errorDescription = replaceWithErrorData(errorDescription ,error.getErrorData());
		}
		errorType.setErrorDescription(errorDescription);
		errorDetailTypes.add(errorType);
		
	}
	
	/**
	 * 
	 * @param errorDescription
	 * @param errorData
	 * @return
	 */
	public String replaceWithErrorData(String errorDescription,
			Object[] errorData){
		String errorMessage = errorDescription;
		if ((errorData != null) && (errorData.length > 0)
				&& (errorMessage != null)) {
			int errorDataLength= errorData.length;
			Pattern pattern = Pattern.compile(PATTERN);
			Matcher matcher = pattern.matcher(errorMessage);
			while (matcher.find()) {
				int arrayIndex = Integer.parseInt(matcher.group(1));
				if ((arrayIndex < errorDataLength)
						&& (errorData[arrayIndex] != null)) {
					String data = String.valueOf(errorData[arrayIndex]);
					String patternValue = matcher.group();
					errorMessage = errorMessage.replace(patternValue, data);
					
				}
				
			}
			
			
		}
		return errorMessage;
	}
	GetProductODMappingResponseType constructGetProductODMappingResponseType(
			Collection<ProductODMappingVO> productODMappingVOs, String requestId,
			Collection<ErrorDetailType> errorDetailTypes) {
		MessageHeaderType messageHeaderType = new MessageHeaderType();
		messageHeaderType.setMessageType(MESSAGE_TYPE_D);
		messageHeaderType.setSourceSystem(SOURCE_SYSTEM_ICARGO);
		GetProductODMappingResponseType getProductODMappingResponseType = new
				GetProductODMappingResponseType();
		GetProductODMappingResponseData getProductODMappingResponseData = new
				GetProductODMappingResponseData();
		getProductODMappingResponseData.setRequestId(requestId);
		if(productODMappingVOs != null && productODMappingVOs.size() > 0) {
			getProductODMappingResponseData.getProductODMappingDetails().addAll(new ArrayList<ProductODMappingType>());
			populateProductODMappingData(productODMappingVOs,getProductODMappingResponseData);
		}else if(errorDetailTypes == null || errorDetailTypes.size() == 0) {
			errorDetailTypes = new ArrayList<ErrorDetailType>();
			ErrorVO error = new ErrorVO(ERROR_NO_RECORD_FOUND);
			 handleErrors(error, errorDetailTypes);
		}if(errorDetailTypes != null && errorDetailTypes.size() > 0) {
			getProductODMappingResponseData.setStatus(STATUS_FAIL);
			getProductODMappingResponseData.getErrorDetails().addAll(errorDetailTypes);
		}else {
			getProductODMappingResponseData.setStatus(STATUS_SUCCESS);
		}
		getProductODMappingResponseType.setMessageHeader(messageHeaderType);
		getProductODMappingResponseType.setResponseDetails(getProductODMappingResponseData);
		return getProductODMappingResponseType;
	}
	public void populateProductODMappingData(Collection<ProductODMappingVO> productODMappingVOs,
			GetProductODMappingResponseData getProductODMappingResponseData) {
		ProductODMappingType productODMappingType = null;
		for(ProductODMappingVO productODMappingVO : productODMappingVOs) {
			productODMappingType = new ProductODMappingType();
			productODMappingType.setOrigin(productODMappingVO.getOrigin());
			productODMappingType.setDestination(productODMappingVO.getDestination());
			productODMappingType.setProductName(productODMappingVO.getProductName());
			getProductODMappingResponseData.getProductODMappingDetails().add(productODMappingType);
		}
	}
	
}
