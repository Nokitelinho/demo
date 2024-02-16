/*
 * CRAProrationProxy.java Created on June 04, 2007
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */

package com.ibsplc.icargo.business.mail.mra.proxy;

import com.ibsplc.icargo.business.cra.proration.defaults.vo.CraProrateFactorFilterVO;
import com.ibsplc.icargo.business.cra.proration.defaults.vo.ProrateFactorVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

/**
 * @author A-3251
 *
 */
@Module("cra")
@SubModule("proration")
public class CRAProrationProxy extends ProductProxy{
	private static final String SERVICE_NAME = "CRA_PRORATION";
	
	
	
	
	 /**
	 * @param craProrateFactorFilterVO
	 * @return
	 * @throws SystemException
	 * @throws ProxyException
	 */
	public Page<ProrateFactorVO> listProrateFactors(CraProrateFactorFilterVO craProrateFactorFilterVO) 
		
	    throws SystemException, ProxyException {
		
		 return despatchRequest("listProrateFactors", craProrateFactorFilterVO);	
	}
	 
	
	
}
