/**
 *	Java file	: 	com.ibsplc.icargo.business.warehouse.defaults.proxy.WarehouseDefaultsProxy.java
 *
 *	Created by	:	A-5269
 *	Created on	:	May 20, 2014
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.master.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.warehouse.defaults.location.vo.LocationVO;
import com.ibsplc.icargo.business.warehouse.defaults.zone.vo.ZoneFilterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.warehouse.defaults.proxy.WarehouseDefaultsProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-5269	:	May 20, 2014	:	Draft
 */
@Module("warehouse")
@SubModule("defaults")
public class WarehouseDefaultsProxy extends ProductProxy{
	
	/**
	 * 
	 * 	Method		:	WarehouseDefaultsProxy.validateAttributesForCheckin
	 *	Added by 	:	A-5269 on May 20, 2014
	 * 	Used for 	:
	 *	Parameters	:	@param attributeValidationVOs
	 *	Parameters	:	@return
	 *	Parameters	:	@throws ProxyException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	Collection<AttributeViolationVO>
	 */
	public Collection<LocationVO> findAllLocationDetails(ZoneFilterVO zoneFilterVO) throws ProxyException, SystemException {
		return despatchRequest("findAllLocationDetails", zoneFilterVO);
	}
}