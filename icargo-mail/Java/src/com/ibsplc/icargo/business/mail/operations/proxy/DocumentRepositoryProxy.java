/*
 * MailbagVO.java Created on OCT 07, 2020
 *
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.proxy;


import java.util.Collection;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.framework.proxy.ProductProxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;


@Module("documentrepository")
@SubModule("defaults")
public class DocumentRepositoryProxy extends ProductProxy{
  
	
	
	  public Collection<DocumentRepositoryMasterVO> uploadMultipleDocumentsToRepository(Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs)
			    throws  ProxyException, SystemException
			  {
			    
			    return despatchRequest("uploadMultipleDocumentsToRepository", documentRepositoryMasterVOs);
			  }
	  
	  public Collection<DocumentRepositoryMasterVO> getDocumentsfromRepository(DocumentRepositoryFilterVO documentRepositoryFilterVO)
				throws  ProxyException, SystemException {
			
			return despatchRequest("getDocuments", documentRepositoryFilterVO);
		}	
}
