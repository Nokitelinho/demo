package com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.DocumentRepositoryDefaultsProxy;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

public class UploadBrokerMappingDocumentFeatureTest extends AbstractFeatureTest {

	private UploadBrokerMappingDocumentFeature feature;
	private DocumentRepositoryDefaultsProxy proxy;
	
	@Override
	public void setup() throws Exception {
		feature = (UploadBrokerMappingDocumentFeature) ICargoSproutAdapter.getBean("customermanagement.defaults.uploadBrokerMappingDocumentFeature");
		proxy = mockProxy(DocumentRepositoryDefaultsProxy.class);
	}
	
	@Test
	public void doVerifyAllEnrichersAndProxyHasBeenInvoked() throws SystemException, BusinessException {
		DocumentRepositoryAttachmentVO attachmentVO = new DocumentRepositoryAttachmentVO();
		attachmentVO.setAttachmentData("POAUPL".getBytes());
		
		DocumentRepositoryMasterVO featureVO = new DocumentRepositoryMasterVO();
		featureVO.setAttachments(new ArrayList<DocumentRepositoryAttachmentVO>(Arrays.asList(attachmentVO)));

		doNothing().when(proxy).uploadMultipleDocumentsToRepository(anyCollectionOf(DocumentRepositoryMasterVO.class));
		feature.execute(featureVO);

		verify(proxy, times(1)).uploadMultipleDocumentsToRepository(anyCollectionOf(DocumentRepositoryMasterVO.class));
	}

}
