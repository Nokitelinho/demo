/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.lh.InterfaceBrokerMappingDocumentChannelMapper.java
 *
 * Copyright 2022 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.lh;

import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.CONTENTTYPE_PDF;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.CONTENTTYPE_TEXT;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.MESSAGE_STANDARD;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.MESSAGE_TYPE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_AWBCARRIERCODE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_AWBDOCUMENTNUMBER;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_BROKERCODE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_BROKERNAME;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_CONSIGNEECODE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_CONSIGNEENAME;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_CONTENTTYPE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_DESTINATION;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_EXCLUDEDORIGIN;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_EXCLUDEDSCC;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_FILENAME;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_INCLUDEDORIGIN;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_INCLUDEDSCC;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_POACREATIONTIME;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_STATION;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.METAINFO_TYPEOFPOA;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.POATYPE_GENERAL;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.POATYPE_SINGLE;
import static com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO.POATYPE_SPECIAL;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageAttachmentVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.jms.lh.POADocumentJMSTemplateVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerAgentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerSupportingDocumentVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.bean.ElementType;
import com.ibsplc.icargo.framework.context.ContextAware;
import com.ibsplc.icargo.framework.floworchestration.context.CompanyContext;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.event.mapper.lh.InterfaceBrokerMappingDocumentChannelMapper.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for IASCB-152053	 :	02-Aug-2022	:	Created
 */

@CompanyContext(company = "LH")
@BeanConverterRegistry
public class InterfaceBrokerMappingDocumentChannelMapper implements ContextAware {

	@BeanConversion(from = CustomerVO.class, to = POADocumentJMSTemplateVO.class, toType = ElementType.LIST, 
			group = {"CUSTOMERMANAGEMENT_SAVEBROKERMAPPINGDOCUMENT_EVENT" })
	public Collection<POADocumentJMSTemplateVO> mapToPOADocumentJMSTemplateVOListFromCustomerVO(CustomerVO customerVO) {
		Collection<POADocumentJMSTemplateVO> templateVOs = new ArrayList<>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		
		templateVOs.addAll(accumulatePOATemplateVO(customerVO.getCustomerAgentVOs(), customerVO.getCustomerName(), dateFormat, true));
		templateVOs.addAll(accumulatePOATemplateVO(customerVO.getCustomerConsigneeVOs(), customerVO.getCustomerName(), dateFormat, false));

		return templateVOs;
	}

	private Collection<POADocumentJMSTemplateVO> accumulatePOATemplateVO(Collection<CustomerAgentVO> customerAgentVOs,
			String customerName, DateFormat dateFormat, boolean isBroker) {
		Collection<POADocumentJMSTemplateVO> templateVOs = new ArrayList<>();

		if (customerAgentVOs != null) {
			for (CustomerAgentVO agentVO : customerAgentVOs) {
				if (OPERATION_FLAG_INSERT.equals(agentVO.getOperationFlag())) {
					POADocumentJMSTemplateVO templateVO = new POADocumentJMSTemplateVO();

					templateVO.setCompanyCode(agentVO.getCompanyCode());
					templateVO.setStationCode(agentVO.getStationCode());

					templateVO.setMessageStandard(MESSAGE_STANDARD);
					templateVO.setMessageType(MESSAGE_TYPE);

					Map<String, String> referenceDetails = new HashMap<>();
					setReferenceValue(METAINFO_TYPEOFPOA, agentVO.getPoaType(), 0, referenceDetails);
					setReferenceValue(METAINFO_STATION, agentVO.getStationCode(), 3, referenceDetails);
					setReferenceValue(METAINFO_POACREATIONTIME, dateFormat.format(agentVO.getPoaCreationTime().getTime()), 0, referenceDetails);

					populateSpecificAttributes(templateVO, referenceDetails, agentVO, customerName, isBroker);

					templateVO.setMiscellaneousDetails(referenceDetails);

					templateVOs.add(templateVO);
				}
			}
		}

		return templateVOs;
	}
	
	private void populateSpecificAttributes(POADocumentJMSTemplateVO templateVO,
			Map<String, String> referenceDetails, CustomerAgentVO agentVO, String customerName, boolean isBroker) {
		String fileName = null;
		String contentType = null;
		MessageAttachmentVO attachmentVO = new MessageAttachmentVO();

		String poaType = agentVO.getPoaType();

		StringBuilder reference = new StringBuilder();

		switch (poaType) {
		case POATYPE_GENERAL:
		case POATYPE_SPECIAL:
			contentType = CONTENTTYPE_PDF;

			if (isBroker) {
				setReferenceValue(METAINFO_BROKERCODE, agentVO.getAgentCode(), 0, referenceDetails);
				setReferenceValue(METAINFO_BROKERNAME, agentVO.getAgentName(), 150, referenceDetails);
				setReferenceValue(METAINFO_CONSIGNEECODE, agentVO.getCustomerCode(), 0, referenceDetails);
				setReferenceValue(METAINFO_CONSIGNEENAME, customerName, 150, referenceDetails);

				reference = reference.append(agentVO.getAgentCode()).append("-").append(poaType).append("-")
						.append(agentVO.getCustomerCode());
			} else {
				setReferenceValue(METAINFO_BROKERCODE, agentVO.getCustomerCode(), 0, referenceDetails);
				setReferenceValue(METAINFO_BROKERNAME, customerName, 150, referenceDetails);
				setReferenceValue(METAINFO_CONSIGNEECODE, agentVO.getAgentCode(), 0, referenceDetails);
				setReferenceValue(METAINFO_CONSIGNEENAME, agentVO.getAgentName(), 150, referenceDetails);

				reference = reference.append(agentVO.getCustomerCode()).append("-").append(poaType).append("-")
						.append(agentVO.getAgentCode());
			}
			setReferenceValue(METAINFO_DESTINATION, agentVO.getDestination(), 3, referenceDetails);

			/*
			 * allowed length is 50 but since we use three lettered codes so final string would be 
			 * CHA,CHB,CHC,CHD,CHE,CHF,CHG,CHH,CHI,CHJ,CHK,CHL,CH 
			 * then last code will be invalid so substring length with 48
			 */
			if (POATYPE_SPECIAL.equals(poaType)) {
				setReferenceValue(METAINFO_INCLUDEDSCC, agentVO.getScc(), 48, referenceDetails);
				setReferenceValue(METAINFO_EXCLUDEDSCC, agentVO.getExcludedScc(), 48, referenceDetails);
				setReferenceValue(METAINFO_INCLUDEDORIGIN, agentVO.getIncludedOrigins(), 48, referenceDetails);
				setReferenceValue(METAINFO_EXCLUDEDORIGIN, agentVO.getExcludedOrigins(), 48, referenceDetails);
			}

			CustomerSupportingDocumentVO supportingDocumentVO = agentVO.getSupportingDocumentVOs().get(0);
			fileName = supportingDocumentVO.getFileName();
			attachmentVO.setAttachmentData(supportingDocumentVO.getFileData());

			break;
		case POATYPE_SINGLE:
			contentType = CONTENTTYPE_TEXT;

			setReferenceValue(METAINFO_BROKERCODE, agentVO.getCustomerCode(), 0, referenceDetails);
			setReferenceValue(METAINFO_BROKERNAME, customerName, 150, referenceDetails);

			String awbNumber = agentVO.getAwbNum();

			String[] awbnum = awbNumber.split(" ");
			setReferenceValue(METAINFO_AWBCARRIERCODE, awbnum[0], 3, referenceDetails);
			setReferenceValue(METAINFO_AWBDOCUMENTNUMBER, awbnum[1], 8, referenceDetails);

			fileName = new StringBuilder().append(awbnum[0]).append("-").append(awbnum[1]).append("_").append("POA.txt")
					.toString();

			attachmentVO.setAttachmentData(new StringBuilder().append("A single POA has been created for AWB ")
					.append(awbNumber).append(" with broker code '").append(agentVO.getCustomerCode())
					.append("' and broker name '").append(customerName).append("' at station '")
					.append(agentVO.getStationCode()).append("'.").toString().getBytes());

			reference = reference.append(agentVO.getCustomerCode()).append("-").append(poaType).append("-")
					.append(awbNumber);

			break;
		default:
			break;
		}

		setReferenceValue(METAINFO_CONTENTTYPE, contentType, 50, referenceDetails);
		setReferenceValue(METAINFO_FILENAME, fileName, 50, referenceDetails);

		templateVO.setReference(reference.toString());
		templateVO.setFileName(fileName);

		attachmentVO.setAttachmentName(fileName);
		attachmentVO.setContentType(contentType);
		templateVO.setMessageAttachments(new ArrayList<MessageAttachmentVO>(Arrays.asList(attachmentVO)));
	}
	
	private void setReferenceValue(String key, String original, int allowedLength, Map<String, String> referenceDetails) {
		if (original != null) {
			String modified = null;

			if (allowedLength != 0 && original.length() > allowedLength) {
				modified = original.substring(0, allowedLength);

				if (modified.endsWith(",")) {
					modified = modified.substring(0, modified.length() - 1);
				}
			} else {
				modified = original;
			}

			referenceDetails.put(key, modified);
		}
	}

}
