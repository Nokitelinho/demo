/*
 * ClaimsDefaultsProxy.java Created on 08 Jun  2008 
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.customermanagement.defaults.proxy;


import com.ibsplc.icargo.business.claims.defaults.vo.ClaimFilterVO;
import com.ibsplc.icargo.business.claims.defaults.vo.ClaimListVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * This class represents the product proxy for shared customer subsystem
 *
 * @author A-2883
 *
 *
 **/
 
@Module("claims")
@SubModule("defaults")
public class ClaimsDefaultsProxy extends ProductProxy {
   
	private  Log log = LogFactory.getLogger("ClaimsProxy");
	
    /**
     * 
     * @author a-2883
     * @param claimFilterVO
     * @return Page<ClaimListVO>
     * @throws SystemException
     * @throws ProxyException
     */
    public  Page<ClaimListVO> findClaimList(ClaimFilterVO 
    		claimFilterVO)  throws
			SystemException,ProxyException {
	  return  despatchRequest("findClaimList",claimFilterVO);
    }
    
    
}
