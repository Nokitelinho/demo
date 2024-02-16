/*
 * WarehouseProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;


//import com.ibsplc.icargo.business.uld.defaults.vo.RelocateULDVO;
import java.util.Collection;

import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitValidationFilterVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.StorageUnitValidationVO;
import com.ibsplc.icargo.business.warehouse.defaults.storageunit.vo.ULDCheckinVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.warehouse.defaults.vo.WarehouseVO;


/**
 * 
 * @author A-1763
 *
 */
@Module("warehouse")
@SubModule("defaults")

public class WarehouseProxy extends ProductProxy {

	 private Log log = LogFactory.getLogger("ULD");
	
	 
    /**
     * This method calls the relocate storage unit in the
     * warehouse product 
     * 
     * @param relocateULDVo
     * @throws SystemException
     * @throws ProxyException
     */
//    public void relocateULD(RelocateULDVO relocateULDVo) 
//    throws SystemException, ProxyException {
//        
//    }
    
    
   /**
    * @param companyCode
    * @param uldNumber
    * @return boolean
    * @throws SystemException
    * @throws ProxyException
    */
//  @Action("checkULDInOperations")
   public boolean checkULDInOperations(String companyCode,
   		String uldNumber) throws SystemException, ProxyException {
       	return false;
   }
   
   /**
    * @author A-1950
    * 
    * @param uldCheckinVOs uldCheckinVOs
    * @throws SystemException SystemException
    */
   public void saveAndCheckinULD(Collection<ULDCheckinVO> uldCheckinVOs)
	  throws SystemException , ProxyException{
	  log.entering("WareHouseProxy", "saveAndCheckinULD");
	  	despatchRequest("saveAndCheckinULD",uldCheckinVOs);
   }
   
   /**
    * 
    * A-1950
    * @param storageUnitVOs
    * @throws SystemException
    * @throws ProxyException
    */
	public void deleteStorageUnitDetails(
	Collection<StorageUnitVO> storageUnitVOs) throws SystemException,
	ProxyException{
		log.entering("WareHouseProxy", "deleteStorageUnitDetails");
		log.log(Log.FINE, "The Storage Unit Vo's", storageUnitVOs);
		despatchRequest("deleteStorageUnitDetails",storageUnitVOs);
	}
	
	/**
	 * 
	 * A-1950
	 * @param storageUnitValidationFilterVOs
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Collection<StorageUnitValidationVO> validateMultipleStorageUnit(
			Collection<StorageUnitValidationFilterVO> storageUnitValidationFilterVOs) 
	throws SystemException , ProxyException{
		log.entering("WareHouseProxy", "validateMultipleStorageUnit");
		
		return despatchRequest("validateMultipleStorageUnit",storageUnitValidationFilterVOs);
	}
	
	/**
	 * @author A-3459
     * Finds all the warehouses
     * @param companyCode
     * @param airportCode
     * @return Collection<WarehouseVO>
     * @throws SystemException
     * @throws ProxyException
     */
    public Collection<WarehouseVO> findAllWarehouses(
    		String companyCode,String airportCode)
    throws SystemException , ProxyException{
    	return despatchRequest("findAllWarehouses",companyCode,airportCode);
    }

    /**
     * @author A-4781
     * @param storageUnitFilterVO
     * @param pageNumber
     * @return
     * @throws SystemException
     * @throws ProxyException
     */
    public Page<StorageUnitVO> findStorageUnitDetails(
    		StorageUnitFilterVO storageUnitFilterVO, int pageNumber)
    		throws SystemException, ProxyException {
    	return despatchRequest("findStorageUnitDetails",storageUnitFilterVO,pageNumber);
    }
}
