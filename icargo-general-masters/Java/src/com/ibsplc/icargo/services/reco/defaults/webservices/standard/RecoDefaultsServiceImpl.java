
/*
 * RecoDefaultsServiceImpl.java Created on Feb 20, 2018
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.services.reco.defaults.webservices.standard;

import java.util.logging.Logger;

import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryRequestType;
import com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryResponseType;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;


@javax.jws.WebService(name = "RecoDefaultsService",
serviceName = "RecoDefaultsService",
portName = "RecoDefaultsServiceHTTP",	
targetNamespace = "http://www.ibsplc.com/icargo/services/RecoDefaultsService/standard/2018/02/06_01",
wsdlLocation = "file:./wsdl/reco/defaults/standard/RecoDefaultsService.wsdl",
endpointInterface = "com.ibsplc.icargo.services.reco.defaults.webservices.standard.RecoDefaultsService")

@Module("reco")
@SubModule("defaults")                    
public class RecoDefaultsServiceImpl extends RecoDefaultsServiceBaseImpl implements RecoDefaultsService {

	private static final Logger LOG = Logger.getLogger(RecoDefaultsServiceImpl.class.getName());
    

   /**
    * 
    *	Overriding Method	:	@see com.ibsplc.icargo.services.reco.defaults.webservices.standard.RecoDefaultsServiceBaseImpl#getEmbargoEnquiryDetails(com.ibsplc.icargo.business.reco.defaults.types.standard.EmbargoEnquiryRequestType)
    *	Added by 			: A-5153 on Feb 20, 2018
    * 	Used for 	:
    *	Parameters	:	@param requestType
    *	Parameters	:	@return
    *	Parameters	:	@throws ServiceFault
    *	Parameters	:	@throws InvalidRequestFault
    */
	public EmbargoEnquiryResponseType getEmbargoEnquiryDetails(
			EmbargoEnquiryRequestType requestType) throws ServiceFault,
			InvalidRequestFault {
		LOG.entering("RecoDefaultsServiceImpl", "getEmbargoEnquiryDetails");
		return super.getEmbargoEnquiryDetails(requestType);
	}

}
