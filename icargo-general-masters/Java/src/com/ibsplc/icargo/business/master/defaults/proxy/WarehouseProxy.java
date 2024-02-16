/**
 *	Java file	: 	com.ibsplc.icargo.presentation.web.command.warehouse.defaults.handlingareaassignment.ListCommand.java
 *
 *	Created by	:	A-6783
 *	Created on	:	Jun	23, 2017
 *
 *  Copyright 2014 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.master.defaults.proxy;
/**
 *	Java file	: 	com.ibsplc.icargo.business.master.defaults.proxy.WarehouseProxy.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:   A-6783  :	June 23, 2017	: 	Draft
 */
import java.util.Collection;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaTypeFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaTypeVO;
import com.ibsplc.icargo.business.warehouse.defaults.handlingarea.vo.HandlingAreaVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.WarehouseLocationFilterLovVO;
import com.ibsplc.icargo.business.warehouse.defaults.location.vo.WarehouseLocationLovVO;
import com.ibsplc.icargo.business.warehouse.defaults.storagestructure.vo.StorageStructureFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.storagestructure.vo.StorageStructureVO;
import com.ibsplc.icargo.business.warehouse.defaults.zone.vo.ZoneFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.zone.vo.ZoneVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
@Module("warehouse")
@SubModule("defaults")
public class WarehouseProxy extends ProductProxy{
	public WarehouseProxy(){	
	}
	public Collection<HandlingAreaVO> findHandlingAreas(HandlingAreaFilterVO handlingAreaFilterVO) throws ProxyException, SystemException
	{	
		return despatchRequest("findHandlingArea",handlingAreaFilterVO);
	}
	/**
	 * 	Method		:	WarehouseProxy.findWarehouseLocationsToSuggest
	 *	Added by 	:	A-6286 on 10-Aug-2017
	 *	Parameters	:	@param warehouseLocationFilterLovVO
	 *	Parameters	:	@return 
	 *	Return type	: 	Collection<WarehouseLocationLovVO>
	 * @throws SystemException 
	 * @throws ProxyException 
	 */
	public Collection<WarehouseLocationLovVO> findWarehouseLocationsToSuggest(
			WarehouseLocationFilterLovVO warehouseLocationFilterLovVO) throws ProxyException,SystemException {
		
		return despatchRequest("findWarehouseLocationsToSuggest",warehouseLocationFilterLovVO);
	}
	
	public Collection<ZoneVO> findZoneCodeLov(ZoneFilterVO zoneFilterVO) throws SystemException {
		  try {
		  return despatchRequest("findZoneCodeLov",zoneFilterVO);
		  } catch (ProxyException e) {
				throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		}
	}
	public StorageStructureVO findStorageStructureDetails(StorageStructureFilterVO storageStructureFilterVO) throws SystemException{
		 try {
		 return despatchRequest("findStorageStructureDetails",storageStructureFilterVO);
		} catch (ProxyException e) {
			throw new SystemException(SystemException.SERVICE_NOT_ACCESSIBLE,e);
		}
	}
	public Collection<HandlingAreaTypeVO> findAllHandlingAreaTypes(
			HandlingAreaTypeFilterVO handlingAreaTypeFilterVO) throws ProxyException,SystemException  {
		
		return despatchRequest("findAllHandlingAreaTypes",handlingAreaTypeFilterVO);
	}

}
