/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.DocumentRepositoryDefaultsProxy.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.proxy;

import java.util.Collection;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.proxy.DocumentRepositoryDefaultsProxy.java
 *	Version	:	Date				:	Updation
 * ---------------------------------------------------------------
 *		0.1		:	26-Jul-2022	:	Created
 */

@Module("documentrepository")
@SubModule("defaults")
public class DocumentRepositoryDefaultsProxy extends ProductProxy {

	public void uploadMultipleDocumentsToRepository(Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs)
			throws SystemException, ProxyException {
		despatchRequest("uploadMultipleDocumentsToRepository", documentRepositoryMasterVOs);
	}

}
