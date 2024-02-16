/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.UploadBrokerMappingDocumentChannelMapper.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event.mapper;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryAttachmentVO;
import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.UploadBrokerMappingDocumentChannelMapper.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	26-Jul-2022	:	Created
 */

@BeanConverterRegistry
public class UploadBrokerMappingDocumentChannelMapper {

	@BeanConversion(from = CustomerVO.class, to = DocumentRepositoryMasterVO.class, group = "CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT_EVENT")
	public DocumentRepositoryMasterVO mapToDocumentRepositoryMasterVOFromCustomerVO(CustomerVO customerVO) {
		DocumentRepositoryMasterVO repositoryMasterVO = new DocumentRepositoryMasterVO();
		
		repositoryMasterVO.setCompanyCode(customerVO.getCompanyCode());

		List<DocumentRepositoryAttachmentVO> repositoryAttachmentVOs = new ArrayList<>();
		repositoryAttachmentVOs.addAll(accumulateAttachmentVO(customerVO.getCustomerAgentVOs(), true));
		repositoryAttachmentVOs.addAll(accumulateAttachmentVO(customerVO.getCustomerConsigneeVOs(), false));
		repositoryMasterVO.setAttachments(repositoryAttachmentVOs);

		return repositoryMasterVO;
	}

	private List<DocumentRepositoryAttachmentVO> accumulateAttachmentVO(Collection<CustomerAgentVO> customerAgentVOs, boolean isBroker) {
		List<DocumentRepositoryAttachmentVO> accumulateAttachmentVOs = new ArrayList<>();

		if (customerAgentVOs != null) {
			for (CustomerAgentVO agentVO : customerAgentVOs) {
				if (OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag())) {
					CustomerSupportingDocumentVO supportingDocumentVO = agentVO.getSupportingDocumentVOs().get(0);

					DocumentRepositoryAttachmentVO attachmentVO = new DocumentRepositoryAttachmentVO();

					attachmentVO.setFileName(supportingDocumentVO.getFileName());
					attachmentVO.setAttachmentData(supportingDocumentVO.getFileData());
					
					if (isBroker) {
						attachmentVO.setTransactionDataRef1(agentVO.getAgentCode());
						attachmentVO.setTransactionDataRef2(agentVO.getCustomerCode());
					} else {
						attachmentVO.setTransactionDataRef1(agentVO.getCustomerCode());
						attachmentVO.setTransactionDataRef2(agentVO.getAgentCode());
					}
					attachmentVO.setTransactionDataRef3(supportingDocumentVO.getContentType());
					attachmentVO.setTransactionDataRef4(Integer.toString(agentVO.getSequenceNumber()));
					
					attachmentVO.setAirportCode(agentVO.getDestination());
					attachmentVO.setCreatedTime(agentVO.getPoaCreationTime());
					if("POAPortal".equals(supportingDocumentVO.getTriggerPoint())){
						attachmentVO.setVisibilityFlag("Y");
					}
					accumulateAttachmentVOs.add(attachmentVO);
				}
			}
		}

		return accumulateAttachmentVOs;
	}

}
