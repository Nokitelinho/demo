package com.ibsplc.icargo.services.products.defaults.webservices.external.lh;



import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

import com.ibsplc.icargo.business.products.defaults.types.external.lh.ErrorDetailType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingFilterVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductModelMappingVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductODMappingVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.WebServiceEndPoint;
import com.ibsplc.icargo.framework.services.jaxws.endpoint.exception.WSBusinessException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
@javax.jws.WebService(
			serviceName = "ProductsDefaultsExtService",
			portName ="ProductsDefaultsExtServiceHTTP",
			targetNamespace = "http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01",
			wsdlLocation = "./build-webservices/wsdl/products/defaults/external/lh/ProductsDefaultsExtService.wsdl",
			endpointInterface ="com.ibsplc.icargo.services.products.defaults.webservices.external.lh.ProductsDefaultsExtService")
@Module("products")
@SubModule("defaults")

public class ProductsDefaultsExtServiceImpl extends WebServiceEndPoint implements ProductsDefaultsExtService{
	
	
	private static final String UNEXPECTED_ERROR = "UNEXPECTED_ERROR";
	private static final String RESOURCEBUNDLE = "resources.products.defaults.MaintainProduct_en_US";
    private static final Locale LOCALE = Locale.US;

	 public GetProductModelMappingResponseType getProductModelMapping(
			 GetProductModelMappingRequestType getProductModelMappingRequestType)
	    				throws InvalidRequestFault , ServiceFault    {
		 
		 GetProductModelMappingResponseType getProductModelMappingResponseType = 
				 new GetProductModelMappingResponseType();
		 Collection<ErrorDetailType> errorDetailTypes  = new ArrayList<ErrorDetailType>();
		 ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCEBUNDLE, LOCALE);
		 ProductModelMappingFilterVO productModelMappingFilterVO = null;
		 Collection<ProductModelMappingVO> productModelMappingVOs = null;
		 ProductsDefaultsExtServiceHelper productsDefaultsExtServiceHelper = 
				 new ProductsDefaultsExtServiceHelper();
		 try{
			 productModelMappingFilterVO = productsDefaultsExtServiceHelper.populateProductModelMappingFilterVo(
					 getProductModelMappingRequestType);
			 productModelMappingVOs = despatchRequest("getProductModelMapping",
					 productModelMappingFilterVO);
		 }catch (WSBusinessException wsBusinessException) {
	        	if (wsBusinessException.getErrors() != null
						&& wsBusinessException.getErrors().size() > 0) {
					for (ErrorVO error : wsBusinessException.getErrors()) {
						ErrorDetailType errorType = new ErrorDetailType();
						errorType.setErrorCode(error.getErrorCode());
						String errorDescription = resourceBundle.getString(error
								.getErrorCode());
						if ((errorDescription == null)
								|| (errorDescription.trim().length() == 0)) {
							errorDescription = error.getErrorCode();
						}
						errorDescription = productsDefaultsExtServiceHelper.replaceWithErrorData(
								errorDescription,
								error.getErrorData());
						errorType.setErrorDescription(errorDescription);
						errorDetailTypes.add(errorType);
					}
				}
		 }catch (SystemException e) {
	        	ErrorDetailType error = new ErrorDetailType();
				error.setErrorCode(UNEXPECTED_ERROR);
				error.setErrorDescription(e.getMessage());
				errorDetailTypes.add(error);
			}
							
		 return productsDefaultsExtServiceHelper.constructGetProductModelMappingResponseType(productModelMappingVOs, getProductModelMappingRequestType.getRequestData().getRequestId(), errorDetailTypes);
	 }

	 public GetProductODMappingResponseType getProductODMapping(
			 GetProductODMappingRequestType getProductODMappingRequestType) 
						throws InvalidRequestFault , ServiceFault {
		 GetProductODMappingResponseType getProductODMappingResponseType = 
				 new GetProductODMappingResponseType();
		 Collection<ErrorDetailType> errorDetailTypes = new ArrayList<ErrorDetailType>();
		 ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCEBUNDLE, LOCALE);
		 Collection<ProductODMappingVO> productODMappingVOs = null;
		 ProductsDefaultsExtServiceHelper productsDefaultsExtServiceHelper = 
				 new ProductsDefaultsExtServiceHelper();
		try {
			LogonAttributes logonVO = ContextUtils.getSecurityContext().getLogonAttributesVO();
			String companyCode = logonVO.getCompanyCode();
			productODMappingVOs = despatchRequest("getProductODMapping", companyCode);
		}catch (WSBusinessException wsBusinessException) {
			if (wsBusinessException.getErrors() != null
					&& wsBusinessException.getErrors().size() > 0) {
				for (ErrorVO error : wsBusinessException.getErrors()) {
					errorDetailTypes.add(populateErrorDetailsType(error.getErrorCode(), error.getErrorData()));
				}
			}
		}catch (SystemException e) {
        	ErrorDetailType error = new ErrorDetailType();
			error.setErrorCode(UNEXPECTED_ERROR);
			error.setErrorDescription(e.getMessage());
			errorDetailTypes.add(error);
		}
		return productsDefaultsExtServiceHelper.constructGetProductODMappingResponseType(productODMappingVOs, getProductODMappingRequestType.getRequestData().getRequestId(), errorDetailTypes);
}
	 private ErrorDetailType populateErrorDetailsType(String errorCode,
				Object[] errorData) {
		 ResourceBundle resourceBundle = ResourceBundle.getBundle(RESOURCEBUNDLE, LOCALE);
		 ProductsDefaultsExtServiceHelper productsDefaultsExtServiceHelper = 
				 new ProductsDefaultsExtServiceHelper();
			ErrorDetailType errorType = new ErrorDetailType();
			errorType.setErrorCode(errorCode);
			String errorDescription = resourceBundle.getString(errorCode);
			if ((errorDescription == null)
					|| (errorDescription.trim().length() == 0)) {
				errorDescription = errorCode;
			}
			errorDescription = productsDefaultsExtServiceHelper.replaceWithErrorData(
					errorDescription,
					errorData);
			errorType.setErrorDescription(errorDescription);
			return errorType;
		}
}