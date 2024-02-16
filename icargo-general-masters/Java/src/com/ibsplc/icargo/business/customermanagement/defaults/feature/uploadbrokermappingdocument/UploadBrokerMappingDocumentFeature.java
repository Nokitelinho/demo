/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument.UploadBrokerMappingDocumentFeature.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_NO;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.FLAG_YES;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerBusinessException;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.DocumentRepositoryDefaultsProxy;
import com.ibsplc.icargo.framework.feature.AbstractFeature;
import com.ibsplc.icargo.framework.feature.Feature;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.uploadbrokermappingdocument.UploadBrokerMappingDocumentFeature.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	26-Jul-2022	:	Created
 */

@FeatureComponent("customermanagement.defaults.uploadBrokerMappingDocumentFeature")
@Feature(exception = CustomerBusinessException.class)
public class UploadBrokerMappingDocumentFeature extends AbstractFeature<DocumentRepositoryMasterVO> {

	private static final String REFERENCE = "BRK";
	private static final String PURPOSE= "POA";
	private static final String SOURCE = "MNGPOA";
	private static final String CONTENT = "contentType";
	private static final String ATTACHMENTTYPE = "PDF";
	
	@Override
	protected FeatureConfigVO fetchFeatureConfig(DocumentRepositoryMasterVO featureVO) {
		FeatureConfigVO vo = new FeatureConfigVO();
		vo.setValidatorIds(new ArrayList<>());
		vo.setEnricherId(new ArrayList<>());
		return vo;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected DocumentRepositoryMasterVO perform(DocumentRepositoryMasterVO featureVO) throws SystemException, BusinessException {
		Collection<DocumentRepositoryMasterVO> documentVOs = sliceDocumentRepositoryAttachmentVO(featureVO);

		Proxy.getInstance().get(DocumentRepositoryDefaultsProxy.class).uploadMultipleDocumentsToRepository(documentVOs);

		return featureVO;
	}

	/**
	 * Used for : to create individual DocumentRepositoryMasterVO for every attachments
	 */
	private Collection<DocumentRepositoryMasterVO> sliceDocumentRepositoryAttachmentVO(DocumentRepositoryMasterVO featureVO) throws SystemException {
		Collection<DocumentRepositoryMasterVO> documentVOs = new ArrayList<>();
		
		for (DocumentRepositoryAttachmentVO featureAttachmentVO : featureVO.getAttachments()) {
			DocumentRepositoryMasterVO masterVO = new DocumentRepositoryMasterVO();

			masterVO.setCompanyCode(featureVO.getCompanyCode());
			masterVO.setAirportCode(featureAttachmentVO.getAirportCode());
			masterVO.setCreatedUser(ContextUtils.getSecurityContext().getLogonAttributesVO().getUserId());
			masterVO.setCreatedTime(featureAttachmentVO.getCreatedTime());

			masterVO.setOperationFlag(OPERATION_FLAG_INSERT);
			masterVO.setDocumentType(REFERENCE);
			masterVO.setDocumentValue(featureAttachmentVO.getTransactionDataRef1());
			masterVO.setPurpose(PURPOSE);
			masterVO.setRemarks("Auto upload from Manage POA Screen");
			masterVO.setTotalFileNumber(1);
			if("Y".equals(featureAttachmentVO.getVisibilityFlag())){
				masterVO.setVisibilityFlag(FLAG_YES);
			}else{
			masterVO.setVisibilityFlag(FLAG_NO);
			}

			masterVO.setReference1(SOURCE);
			masterVO.setTransactionDataRef1(featureAttachmentVO.getTransactionDataRef2());
			masterVO.setTransactionDataRef2(featureAttachmentVO.getTransactionDataRef4());

			DocumentRepositoryAttachmentVO attachmentVO = new DocumentRepositoryAttachmentVO();
			attachmentVO.setTxnRefereceOverride(true);
			
			attachmentVO.setOperationFlag(OPERATION_FLAG_INSERT);
			attachmentVO.setAttachmentType(ATTACHMENTTYPE);
			
			attachmentVO.setReference1(CONTENT);
			attachmentVO.setTransactionDataRef1(featureAttachmentVO.getTransactionDataRef3());

			attachmentVO.setFileName(featureAttachmentVO.getFileName());
			attachmentVO.setAttachmentData(featureAttachmentVO.getAttachmentData());
			attachmentVO.setCreatedTime(featureAttachmentVO.getCreatedTime());

			masterVO.setAttachments(new ArrayList<DocumentRepositoryAttachmentVO>(Arrays.asList(attachmentVO)));

			documentVOs.add(masterVO);
		}

		return documentVOs;
	}

}
