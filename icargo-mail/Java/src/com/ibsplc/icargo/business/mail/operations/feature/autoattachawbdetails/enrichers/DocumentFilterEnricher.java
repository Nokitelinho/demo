/**
 *	Java file	: 	com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 *
 *	Created by	:	A-8330
 *	Created on	:	12-Feb-2020
 *
 *  Copyright 2019 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.enrichers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.feature.DependsOn;
import com.ibsplc.icargo.framework.feature.Enricher;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;

/**
 * Java file :
 * com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-8330 :
 * 12-Feb-2020 : Draft
 */
@DependsOn(AutoAttachAWBDetailsFeatureConstants.AGENT_CODE_VALIDATOR)
@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILETR_ENRICHER)
public class DocumentFilterEnricher extends Enricher<MailManifestDetailsVO> {
	@Autowired
	Proxy proxy;

	@Override
	public void enrich(MailManifestDetailsVO mailManifestDetailsVO) throws SystemException {
		OperationalFlightVO operationalFlightVO = mailManifestDetailsVO.getOperationalFlightVO();
		if(Objects.isNull(operationalFlightVO)){
			return;
		}
		String companyCode = operationalFlightVO.getCompanyCode();
		DocumentFilterVO documentFilterVO = null;
		Collection<MailbagVO> mailbagVOs = mailManifestDetailsVO.getMailbagVOs();
		DocumentValidationVO documentValidationVO = null;

		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			String agentCode = (String) getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
			int airlineId = logonAttributes.getOwnAirlineIdentifier();
			StockcontrolDefaultsProxy proxy1 = new StockcontrolDefaultsProxy();
			if (agentCode != null && agentCode.trim().length() > 0) {
				documentFilterVO = new DocumentFilterVO();
				documentFilterVO.setAirlineIdentifier(airlineId);
				documentFilterVO.setCompanyCode(companyCode);
				documentFilterVO.setDocumentType(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_TYPE);
				documentFilterVO.setStockOwner(agentCode);
				documentFilterVO.setAwbOrigin(mailbagVOs.iterator().next().getOrigin());
				documentFilterVO.setAwbDestination(mailbagVOs.iterator().next().getDestination());// added
																									// by
																									// a-7871
																									// for
																									// ICRD-262511
				// Added and modified as part of IASCB-101735 by U-1519 begins

				String subtype = null;
				try {
					subtype = proxy1.findAutoPopulateSubtype(documentFilterVO);
				} catch (SystemException | ProxyException e2) {
					throw new SystemException(e2.getMessage());
				}
				ProductVO productVO = mailManifestDetailsVO.getProductVO();
				if (subtype != null && subtype.trim().length() > 0) {
					documentFilterVO.setDocumentSubType(subtype);
				} else {
					documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
				}
				// Added and modified as part of IASCB-101735 by U-1519 ends
				/**documentFilterVO.setAwbOrigin(mailbagVOs.iterator().next().getPol());*/
				boolean checkAWBAttach = checkAWBAttached(mailbagVOs);

				documentValidationVO = populateDocumentvalidatorVo(documentFilterVO, checkAWBAttach, agentCode);
				mailManifestDetailsVO.setDocumentFilterVO(documentFilterVO);
				mailManifestDetailsVO.setDocumentValidationVO(documentValidationVO);
				mailManifestDetailsVO.setCheckAWBAttached(checkAWBAttach);
			}

		}
		
	}

	private DocumentValidationVO populateDocumentvalidatorVo(DocumentFilterVO documentFilterVO, boolean checkAWBAttach,
			String agentCode) throws SystemException {
		DocumentValidationVO documentValidationVO = null;
		try {
			documentValidationVO = findNextDocumentNumber(documentFilterVO);
		} catch (StockcontrolDefaultsProxyException e1) {
			throw new SystemException(e1.getErrors());
		}
		if (documentValidationVO != null && !checkAWBAttach) {

			documentFilterVO.setDocumentNumber(documentValidationVO.getDocumentNumber());
			documentFilterVO.setStockOwner(agentCode);
			try {
				deleteDocumentFromStock(documentFilterVO);
			} catch (StockcontrolDefaultsProxyException e) {

				throw new SystemException(e.getErrors());
			}
		}
		return documentValidationVO;

	}

	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws SystemException {
		return OfficeOfExchange.findAgentCodeForPA(companyCode, officeOfExchange);
	}

	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		return new StockcontrolDefaultsProxy().findNextDocumentNumber(documentFilterVO);
	}

	private boolean checkAWBAttached(Collection<MailbagVO> mailbagVOs) {
		boolean isAWBAttached = true;

		for (MailbagVO mailBagVO : mailbagVOs) {
			if (mailBagVO.getDocumentNumber() == null) {
				isAWBAttached = false;
				break;
			}
		}
		return isAWBAttached;
	}

	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		// Added by A-7540 starts
		String product = findSystemParameterValue(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs;
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO;

		productVOs = new ProductDefaultsProxy().findProductsByName(documentFilterVO.getCompanyCode(), product);

		if (productVOs != null && !productVOs.isEmpty()) {

			productValidationVO = productVOs.iterator().next();

			productVO = new ProductDefaultsProxy().findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		// Added by A-7540 ends
		new StockcontrolDefaultsProxy().deleteDocumentFromStock(documentFilterVO);
	}

	public String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = Proxy.getInstance().get(SharedDefaultsProxy.class)
				.findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

}
