/**
 * ULDDefaultsServiceJMSImpl.java created on 11/05/2018
 * 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.services.uld.defaults.webservices.standard;

import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDetailsRequestType;
import com.ibsplc.icargo.business.uld.defaults.types.standard.SaveULDDetailsResponseType;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;


@javax.jws.WebService( 
		name="ULDDefaultsService",
		serviceName = "ULDDefaultsService", 
		portName = "ULDDefaultsServiceBinding_JMS", 
		targetNamespace = "http://www.ibsplc.com/icargo/services/ULDDefaultsService/standard/2018/04/24", 
		wsdlLocation = "file:./wsdl/uld/defaults/standard/ULDDefaultsService.wsdl", 
		endpointInterface = "com.ibsplc.icargo.services.uld.defaults.webservices.standard.ULDDefaultsService") 

@Module("uld") 
@SubModule("defaults") 
/**
 * 
 * @author A-7918
 * saveULDDetails
 */
public class ULDDefaultsServiceJMSImpl extends ULDDefaultsServiceBaseImpl implements ULDDefaultsService{

	@Override
	public SaveULDDetailsResponseType saveULDDetails(SaveULDDetailsRequestType ulddetailsRequestType)  {
		
		return super.saveULDDetails(ulddetailsRequestType); 
		
		
	}

}
