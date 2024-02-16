/*
 * ExportProxy.java Created on Jul 8, 2005
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.uld.defaults.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.uld.defaults.vo.ULDValidationVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * @author A-1763
 *
 */
@Module("operations")
@SubModule("flthandling")

public class ExportProxy extends ProductProxy {

 	
	private Log log = LogFactory.getLogger("ULD");

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
   
   /**
	 * 
	 * A-1950
	 * @param uldValidationVOs
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public void checkULDInOperartions(Collection<ULDValidationVO> uldValidationVOs)
	throws SystemException , ProxyException{
		log.entering("ExportProxy", "checkULDInOperartions");
		despatchRequest("checkULDInOperartions",uldValidationVOs);
	}
   
}