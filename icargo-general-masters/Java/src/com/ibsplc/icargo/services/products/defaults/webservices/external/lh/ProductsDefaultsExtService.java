package com.ibsplc.icargo.services.products.defaults.webservices.external.lh;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductModelMappingResponseType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingRequestType;
import com.ibsplc.icargo.business.products.defaults.types.external.lh.GetProductODMappingResponseType;




@WebService(targetNamespace="http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01", name="ProductsDefaultsExtService")
@XmlSeeAlso({com.ibsplc.icargo.business.products.defaults.types.external.lh.ObjectFactory.class, ObjectFactory.class, com.ibsplc.icargo.business.shared.defaults.types.standard.ObjectFactory.class,com.ibsplc.icargo.framework.services.jaxws.types.common.ObjectFactory.class})
@SOAPBinding(parameterStyle=SOAPBinding.ParameterStyle.BARE)
public abstract interface ProductsDefaultsExtService {

	 @WebResult(name="GetProductModelMappingResponse", targetNamespace="http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01", partName="GetProductModelMappingResponse")
	  @WebMethod(action="http://www.ibsplc.com/icargo/services/external/ProductsDefaultsExtService/getProductModelMapping")
	  public abstract GetProductModelMappingResponseType getProductModelMapping(@WebParam(partName="GetProductModelMappingRequest", name="GetProductModelMappingRequest", targetNamespace="http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01") GetProductModelMappingRequestType paramGetProductModelMappingRequestType)
	    throws ServiceFault, InvalidRequestFault;
	 @WebResult(name="GetProductODMappingResponse", targetNamespace="http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01", partName="GetProductODMappingResponse")
	  @WebMethod(operationName="GetProductODMapping", action="http://www.ibsplc.com/icargo/services/external/ProductsDefaultsExtService/GetProductODMapping")
	  public abstract GetProductODMappingResponseType getProductODMapping(@WebParam(partName="GetProductODMappingRequest", name="GetProductODMappingRequest", targetNamespace="http://www.ibsplc.com/icargo/services/external/lh/ProductsDefaultsExtService/2016/05/23_01") GetProductODMappingRequestType paramGetProductODMappingRequestType)
	    throws InvalidRequestFault, ServiceFault;
}
