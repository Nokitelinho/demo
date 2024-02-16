package com.ibsplc.neoicargo.mail.component.proxy;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryFilterVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.mail.component.proxy.eproxy.DocumentRepositoryEProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Slf4j
public class DocumentRepositoryProxy {
	@Autowired
	private DocumentRepositoryEProxy documentRepositoryEProxy;

	public Collection<DocumentRepositoryMasterVO> uploadMultipleDocumentsToRepository(
			Collection<DocumentRepositoryMasterVO> documentRepositoryMasterVOs) throws BusinessException {
		return documentRepositoryEProxy.uploadMultipleDocumentsToRepository(documentRepositoryMasterVOs);
	}

	public Collection<DocumentRepositoryMasterVO> getDocumentsfromRepository(
			DocumentRepositoryFilterVO documentRepositoryFilterVO) throws BusinessException {
		log.debug("DocumentRepositoryDefaultsProxy" + " : " + "getDocuments" + " Entering");
		return documentRepositoryEProxy.getDocuments(documentRepositoryFilterVO);
	}
}
