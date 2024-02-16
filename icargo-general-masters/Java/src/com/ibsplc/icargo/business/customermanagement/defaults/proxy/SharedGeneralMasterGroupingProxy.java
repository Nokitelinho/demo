/*
 * SharedGeneralMasterGroupingProxy.java Created on Dec 22, 2021
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * The Class SharedGeneralMasterGroupingProxy.
 *
 * @author A-2569
 */
@Module("shared")
@SubModule("generalmastergrouping")
public class SharedGeneralMasterGroupingProxy extends ProductProxy {
	
	
 
	/**
	 * Done for IASCB-130291
	 * @param generalMasterGroupFilterVO
	 * @return
	 * @throws ProxyException
	 * @throws SystemException
	 */
	public  Collection<GeneralMasterGroupVO> listGroupDetailsForCustomer
					(GeneralMasterGroupFilterVO generalMasterGroupFilterVO)throws ProxyException,SystemException
	{
		return despatchRequest("listGeneralMasterGroupingDetails",generalMasterGroupFilterVO);
		
		
	}
	
	
}
