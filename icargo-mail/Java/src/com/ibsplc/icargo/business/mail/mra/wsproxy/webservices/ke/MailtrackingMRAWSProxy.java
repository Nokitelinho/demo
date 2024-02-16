/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.wsproxy.webservices.ke.MailtrackingMRAWSProxy.java
 *
 *	Created by	:	a-8061
 *	Created on	:	Jul 02, 2018
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.wsproxy.webservices.ke;

import java.util.Collection;

import com.ibsplc.icargo.business.mail.mra.defaults.vo.FlightRevenueInterfaceVO;
import com.ibsplc.icargo.framework.services.jaxws.proxy.WebServiceProxy;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ke.xsd.mra.revenueinterface.Response;


/**
 * 
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.wsproxy.webservices.ke.MailtrackingMRAWSProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-8061	:	02-Jul-2018	:	Draft
 */


public class MailtrackingMRAWSProxy  extends WebServiceProxy {

	private static final String FLIGHT_REVENUE_SERVICE_ID = "ICGO_0020_0001_XXAR";
	private static final String FLIGHT_REVENUE_OP_ID = "process";
	private Log log = LogFactory.getLogger("MRA:DEFAULTS:KE");
	
	/**
	 * 
	 * 	Method		:	MailtrackingMRAWSProxy.doInterfaceFlightRevenueDtls
	 *	Added by 	:	a-8061 on 02-Jul-2018
	 * 	Used for 	:	ICRD-245594
	 *	Parameters	:	@param flightRevenueInterfaceVO
	 *	Parameters	:	@throws WebServiceException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
	public Response doInterfaceFlightRevenueDtls(Collection<FlightRevenueInterfaceVO> flightRevenueInterfaceVOs) throws WebServiceException,SystemException{
		
			log.entering("MailtrackingMRAWSProxy", "doInterfaceFlightRevenueDtls");
			Response response=null;
			try{
			 response=	despatchRequest(FLIGHT_REVENUE_SERVICE_ID, FLIGHT_REVENUE_OP_ID,flightRevenueInterfaceVOs);
			}catch(SystemException systemException){
				throw new SystemException(systemException.getMessage());
			}
			log.exiting("MailtrackingMRAWSProxy", "doInterfaceFlightRevenueDtls");
			return response;
		
	}

}
