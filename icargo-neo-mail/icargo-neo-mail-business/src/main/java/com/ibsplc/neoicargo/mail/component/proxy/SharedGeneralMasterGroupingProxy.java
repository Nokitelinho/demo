/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.operations.proxy.SharedGeneralMasterGroupingProxy.java
 *
 *	Created by	:	A-10647
 *	Created on	:	16-Sep-2022
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.ebl.api.ServiceException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.SharedGeneralMasterGroupingEProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
@Component
public class SharedGeneralMasterGroupingProxy {

	@Autowired
	private SharedGeneralMasterGroupingEProxy sharedGeneralMasterGroupingEProxy;


	public GeneralMasterGroupVO listGeneralMasterGroup(GeneralMasterGroupFilterVO filterVO)
			throws  SystemException {

		try {
			return sharedGeneralMasterGroupingEProxy.listGeneralMasterGroup(filterVO);
		}catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}

	}

	public Collection<String> findGroupNamesOfEntity(
			GeneralMasterGroupFilterVO generalMasterGroupFilterVO) throws SystemException {
		try {
			return sharedGeneralMasterGroupingEProxy.findGroupNamesOfEntity(generalMasterGroupFilterVO);
		} catch(ServiceException serviceException){
			throw new SystemException(serviceException.getMessage());
		}
	}
}
