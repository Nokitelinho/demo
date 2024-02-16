/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.proxy.MailOperationsProxy.java
 *
 *	Created by	:	A-6991
 *	Created on	:	04-May-2017
 *
 *  Copyright 2017 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.uld.defaults.proxy.MailOperationsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-6991	:	04-May-2017	:	Draft
 */
@Module("mail")
@SubModule("operations")
public class MailOperationsProxy extends ProductProxy{

	/**
	 * 
	 * 	Method		:	MailOperationsProxy.updateActualWeightForMailULD
	 *	Added by 	:	A-6991 on 04-May-2017
	 * 	Used for 	:
	 *	Parameters	:	@param containerVo
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	void
	 */
	public boolean isFlightClosedForInboundOperations(OperationalFlightVO operationalFlightVO)
			 throws SystemException,ProxyException{
		 return  despatchRequest("isFlightClosedForInboundOperations",operationalFlightVO);
				
			 }
	/**
	 * 
	 * 	Method		:	MailOperationsProxy.isFlightClosedForMailOperations
	 *	Added by 	:	A-6991 on 21-Nov-2017
	 * 	Used for 	:   ICRD-77772
	 *	Parameters	:	@param operationalFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	boolean
	 */
	public boolean isFlightClosedForMailOperations(
			OperationalFlightVO operationalFlightVO) throws SystemException,ProxyException{
		 return  despatchRequest("isFlightClosedForMailOperations",operationalFlightVO);
			
	 }
	/**
	 * 
	 * 	Method		:	MailOperationsProxy.findULDsInAssignedFlight
	 *	Added by 	:	A-6991 on 20-Nov-2017
	 * 	Used for 	:   ICRD-77772
	 *	Parameters	:	@param operationalFlightVO
	 *	Parameters	:	@return
	 *	Parameters	:	@throws SystemException
	 *	Parameters	:	@throws ProxyException 
	 *	Return type	: 	Collection<ContainerVO>
	 */
	public Collection<ContainerVO> findULDsInAssignedFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException,ProxyException{
		 return  despatchRequest("findULDsInAssignedFlight",operationalFlightVO);
			
	 }
}


