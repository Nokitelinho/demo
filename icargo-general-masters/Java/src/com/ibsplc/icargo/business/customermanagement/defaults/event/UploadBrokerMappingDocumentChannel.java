/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.UploadBrokerMappingDocumentChannel.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event;

import com.ibsplc.icargo.business.businessframework.documentrepository.defaults.vo.DocumentRepositoryMasterVO;
import com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.UploadBrokerMappingDocumentChannelEvaluator;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.UploadBrokerMappingDocumentChannel.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152054	 :	26-Jul-2022	:	Created
 */

@Module("customermanagement")
@SubModule("defaults")
@EventChannel(value = "customermanagement.defaults.uploadBrokerMappingDocumentChannel", targetClass = DocumentRepositoryMasterVO.class, 
	listeners = {
		@EventListener(evaluator = UploadBrokerMappingDocumentChannelEvaluator.class, event = "CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT") })
public class UploadBrokerMappingDocumentChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		despatchRequest("uploadBrokerMappingDocuments", (DocumentRepositoryMasterVO) eventVO.getPayload());
	}

}
