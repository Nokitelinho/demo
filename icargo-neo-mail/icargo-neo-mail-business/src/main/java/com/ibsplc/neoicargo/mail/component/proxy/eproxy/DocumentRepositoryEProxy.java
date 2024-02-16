package com.ibsplc.neoicargo.mail.component.proxy.eproxy;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.neoicargo.framework.ebl.api.EProductProxy;

import java.util.Collection;

@EProductProxy(module = "documentrepository", submodule = "defaults", name = "documentRepositoryEProxy")
public interface DocumentRepositoryEProxy {
	Collection<DocumentRepositoryMasterVO> uploadMultipleDocumentsToRepository(
			Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs);

	Collection<DocumentRepositoryMasterVO> getDocuments(DocumentRepositoryFilterVO documentRepositoryFilterVO);
}
