/*
 * ImportProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;


import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * 
 * @author A-1763
 *
 */
@Module("import")
@SubModule("?")

public class ImportProxy extends ProductProxy {

 	
	 

   /**
    * 
    * 
    * @param companyCode
    * @param uldNumber
    * 
    * @return boolean
    * 
    * @throws SystemException
    * @throws ProxyException
    */
//  @Action("checkULDInOperations")
   public boolean checkULDInOperations(String companyCode,
   		String uldNumber) throws SystemException, ProxyException {
       	return false;
   }
   
}