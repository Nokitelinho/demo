/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.lh.InterfaceBrokerMappingDocumentChannel.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event.lh;

import java.util.Collection;

import com.ibsplc.icargo.business.customermanagement.defaults.event.evaluator.InterfaceBrokerMappingDocumentChannelEvaluator;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.event.AbstractChannel;
import com.ibsplc.icargo.framework.event.annotations.EventChannel;
import com.ibsplc.icargo.framework.event.annotations.EventListener;
import com.ibsplc.icargo.framework.event.vo.EventVO;
import com.ibsplc.icargo.framework.floworchestration.context.CompanyContext;
import com.ibsplc.xibase.client.framework.delegate.Module;
import com.ibsplc.xibase.client.framework.delegate.SubModule;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.lh.InterfaceBrokerMappingDocumentChannel.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152053	 :	02-Aug-2022	:	Created
 */

@Module("customermanagement")
@SubModule("defaults")
@EventChannel(value = "customermanagement.defaults.lh.interfaceBrokerMappingDocumentChannel", targetClass = POADocumentJMSTemplateVO.class, targetType = ElementType.LIST, 
	listeners = {
			@EventListener(evaluator = InterfaceBrokerMappingDocumentChannelEvaluator.class, event = "CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT") })
@CompanyContext(company = "LH")
public class InterfaceBrokerMappingDocumentChannel extends AbstractChannel {

	@Override
	public void send(EventVO eventVO) throws Throwable {
		@SuppressWarnings("unchecked")
		Collection<POADocumentJMSTemplateVO> templateVOs = (Collection<POADocumentJMSTemplateVO>) eventVO.getPayload();

		despatchRequest("interfaceBrokerMappingDocuments", templateVOs);
	}

}
