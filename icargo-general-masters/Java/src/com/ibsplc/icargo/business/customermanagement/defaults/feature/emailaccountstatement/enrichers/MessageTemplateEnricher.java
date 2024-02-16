/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers.MessageTemplateEnricher.java
 *
 * Copyright 2020 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 *	This software is the proprietary information of IBS Software Services (P) Ltd.
 *	Use is subject to license terms.
 */

/**
 * Each method of this class must detail the functionality briefly in the javadoc comments preceding the method.
 * This is to be followed in case of change of functionality also.
 */
package com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers;

import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.EMAIL_MODE;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.FILE_FORMAT_PDF;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.FILE_FORMAT_XL;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.FILE_NAME;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.MESSAGE_STANDARD;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.MESSAGE_TYPE;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.MSG_VERSION;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_AUTO;
import static com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO.TRIGGER_MANUAL;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_PDF;
import static com.ibsplc.icargo.business.shared.customer.vo.CustomerVO.INVOICE_DISTRIBUTION_MODE_XL;
import static com.ibsplc.icargo.framework.report.util.ReportConstants.EMPTY_STRING;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.ibsplc.icargo.business.cra.agentbilling.defaults.vo.CustomerInvoiceAWBDetailsVO;
import com.ibsplc.icargo.business.customermanagement.defaults.CustomerMgmntController;
import com.ibsplc.icargo.business.customermanagement.defaults.profile.vo.EmailAccountStatementFeatureVO;
import com.ibsplc.icargo.business.customermanagement.defaults.proxy.SharedBillingSiteProxy;
import com.ibsplc.icargo.business.msgbroker.message.vo.MessageAttachmentVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.template.email.CustomerAccountStatementEmailTemplateVO;
import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteFilterVO;
import com.ibsplc.icargo.business.shared.billingsite.vo.BillingSiteVO;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.xibase.util.time.TimeConvertor;

/**
 *	Java file	: 	com.ibsplc.icargo.business.customermanagement.defaults.feature.emailaccountstatement.enrichers.MessageTemplateEnricher.java
 *	Version	:	Name						 :	Date				:	Updation
 * ------------------------------------------------------------------------------------------------
 *		0.1		:	for  IASCB-104246  :	26-Jul-2021	:	Created
 */

@FeatureComponent("emailAccountStatementFeature.createMessageTemplateEnricher")
public class MessageTemplateEnricher extends Enricher<EmailAccountStatementFeatureVO> {

	private static final Log LOGGER = LogFactory.getLogger("CUSTOMER MANAGEMENT");

	@Override
	public void enrich(EmailAccountStatementFeatureVO featureVO) throws SystemException {
		LogonAttributes logonAttribute = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String triggerPoint = featureVO.getTriggerPoint();
		Collection<CustomerAccountStatementEmailTemplateVO> templateVOs = new ArrayList<>();

		featureVO.getCustomerInvoiceAWBDetailsVOs()
		.forEach(invoiceVO -> {
			String subject = null;
			String billingEndDate = null;
			
			CustomerAccountStatementEmailTemplateVO templateVO = new CustomerAccountStatementEmailTemplateVO();
			templateVO.setCompanyCode(featureVO.getCompanyCode());
			templateVO.setMessageStandard(MESSAGE_STANDARD);
			templateVO.setMessageType(MESSAGE_TYPE);
			templateVO.setModeofCommunication(EMAIL_MODE);
			templateVO.setMsgVersion(MSG_VERSION);
			templateVO.setMessageAttachments(new ArrayList<MessageAttachmentVO>(2));

			switch (triggerPoint) {
			case TRIGGER_MANUAL:
				LocalDate currentDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
				billingEndDate = currentDate.toDisplayFormat(TimeConvertor.CALENDAR_DATE_FORMAT);
				subject = new StringBuilder(FILE_NAME).append(" as on ").append(billingEndDate).toString();

				populateManualEmailAccessories(templateVO, featureVO, invoiceVO, currentDate);
				break;
			case TRIGGER_AUTO:
				CustomerInvoiceAWBDetailsVO awbVO = invoiceVO.iterator().next();
				
				billingEndDate = awbVO.getInvoiceTooDate().toDisplayFormat(TimeConvertor.CALENDAR_DATE_FORMAT);
				List<String> endDate = Arrays.asList(billingEndDate.split("-"));
				subject = new StringBuilder(FILE_NAME).append(" for ").append(endDate.get(1)).append("-").append(endDate.get(2)).toString();

				populateAutoEmailAccessories(templateVO, invoiceVO, awbVO, logonAttribute.getStationCode());
				break;
			default:
				break;
			}

			templateVO.setSubject(subject);
			templateVO.setBillingEndDate(billingEndDate);

			templateVOs.add(templateVO);
		});

		featureVO.setEmailTemplateVOs(templateVOs);
	}

	private void populateManualEmailAccessories(CustomerAccountStatementEmailTemplateVO templateVO,
			EmailAccountStatementFeatureVO featureVO, List<CustomerInvoiceAWBDetailsVO> invoiceVO,
			LocalDate currentDate) {
		templateVO.setStationCode(featureVO.getStationCode());
		templateVO.setCustomerCode(featureVO.getCustomerCode());
		templateVO.setToAddress(featureVO.getCustomerBillingEmailOne());
		templateVO.setCcAddress(featureVO.getCustomerBillingEmailTwo());
		templateVO.setBccAddress(featureVO.getCustomerBillingEmailThree());

		BillingSiteFilterVO filterVO = new BillingSiteFilterVO();
		filterVO.setCompanyCode(featureVO.getCompanyCode());
		filterVO.setStationCode(featureVO.getStationCode());
		filterVO.setValidDate(currentDate);

		try {
			BillingSiteVO siteVO = Proxy.getInstance().get(SharedBillingSiteProxy.class).findBillingSiteDetails(filterVO);
			templateVO.setAirlineContactEmail(!StringUtils.isEmpty(siteVO.getEmailID()) ? siteVO.getEmailID() : EMPTY_STRING);
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.WARNING, "BillingSiteVO fetching exception");
		}
		
		try {
			createBytesForExcel(featureVO.getInvoiceDistributionMode(), invoiceVO, templateVO.getMessageAttachments());
			createBytesForPdf(featureVO.getInvoiceDistributionMode(), invoiceVO, templateVO.getMessageAttachments());
		} catch (SystemException se) {
			LOGGER.log(Log.SEVERE, "Attachment creation failed");
		}
	}

	private void populateAutoEmailAccessories(CustomerAccountStatementEmailTemplateVO templateVO,
			List<CustomerInvoiceAWBDetailsVO> invoiceVO, CustomerInvoiceAWBDetailsVO awbVO, String stationCode) {
		templateVO.setStationCode(stationCode);
		templateVO.setCustomerCode(awbVO.getCustomerCode());
		templateVO.setToAddress(awbVO.getBillingEmailOne());
		templateVO.setCcAddress(awbVO.getBillingEmailTwo());
		templateVO.setBccAddress(awbVO.getBillingEmailThree());
		templateVO.setAirlineContactEmail(awbVO.getBillingSiteEmail());
		templateVO.setAirlineContactEmail(!StringUtils.isEmpty(awbVO.getBillingSiteEmail()) ? awbVO.getBillingSiteEmail() : EMPTY_STRING);

		try {
			createBytesForExcel(Arrays.asList(awbVO.getInvoiceSendMode()), invoiceVO, templateVO.getMessageAttachments());
			createBytesForPdf(Arrays.asList(awbVO.getInvoiceSendMode()), invoiceVO, templateVO.getMessageAttachments());
		} catch (SystemException e) {
			LOGGER.log(Log.SEVERE, "Attachment creation failed");
		}
	}

	private void createBytesForExcel(Collection<String> distributionMode, List<CustomerInvoiceAWBDetailsVO> invoiceVO,
			List<MessageAttachmentVO> attachmentVOs) throws SystemException {
		if (distributionMode.contains(INVOICE_DISTRIBUTION_MODE_XL)) {
			MessageAttachmentVO vo = new MessageAttachmentVO();
			vo.setAttachmentName(new StringBuilder(FILE_NAME).append(FILE_FORMAT_XL).toString());
			vo.setAttachmentData(getCustomerMgmntController().createWorkBookBytesForExcelReport(invoiceVO));

			attachmentVOs.add(vo);
		}
	}

	private void createBytesForPdf(Collection<String> distributionMode, List<CustomerInvoiceAWBDetailsVO> invoiceVO,
			List<MessageAttachmentVO> attachmentVOs) throws SystemException {
		if (distributionMode.contains(INVOICE_DISTRIBUTION_MODE_PDF)) {
			MessageAttachmentVO vo = new MessageAttachmentVO();
			vo.setAttachmentName(new StringBuilder(FILE_NAME).append(FILE_FORMAT_PDF).toString());
			vo.setAttachmentData(getCustomerMgmntController().createBytesForPDFReport(invoiceVO));

			attachmentVOs.add(vo);
		}
	}

	public CustomerMgmntController getCustomerMgmntController() throws SystemException {
		return (CustomerMgmntController) SpringAdapter.getInstance().getBean("customerMgmntController");
	}

}
