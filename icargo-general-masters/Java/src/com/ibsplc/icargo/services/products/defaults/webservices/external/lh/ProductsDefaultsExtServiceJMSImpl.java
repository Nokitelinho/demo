package com.ibsplc.icargo.services.products.defaults.webservices.external.lh;




import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingResponseType;
import com.ibsplc.icargo.services.products.defaults.webservices.external.lh.InvalidRequestFault;
import com.ibsplc.icargo.services.products.defaults.webservices.external.lh.ServiceFault;


@javax.jws.WebService(
        serviceName = "ProductsDefaultsExtService",
        portName = "ProductsDefaultsExtServiceSOAPJMS",
        targetNamespace = "http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01",
        wsdlLocation = "./build-webservices/wsdl/products/defaults/external/lh/ProductsDefaultsExtService.wsdl",
        endpointInterface = "com.ibsplc.icargo.services.products.defaults.webservices.external.lh.ProductsDefaultsExtService")


public class ProductsDefaultsExtServiceJMSImpl extends ProductsDefaultsExtServiceImpl implements ProductsDefaultsExtService{

	 public GetProductModelMappingResponseType getProductModelMapping(
			 GetProductModelMappingRequestType getProductModelMappingRequestType)
	    				throws InvalidRequestFault , ServiceFault    { 
	    	
	        return super.getProductModelMapping(getProductModelMappingRequestType);
	    }
	 public GetProductODMappingResponseType getProductODMapping(
			 GetProductODMappingRequestType getProductODMappingRequestType)
					 throws InvalidRequestFault , ServiceFault {
		 return super.getProductODMapping(getProductODMappingRequestType);
	    }

}
