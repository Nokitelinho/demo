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
package com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.enrichers;


import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

import static com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILETR_ENRICHER;

/**
 * Java file :
 * com.ibsplc.icargo.business.operations.flthandling.feature.savebreakdown.enrichers.AttachStorageUnitDetailsEnricher.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : A-8330 :
 * 12-Feb-2020 : Draft
 */
@Component(DOCUMENT_FILETR_ENRICHER)
public class DocumentFilterEnricher extends Enricher<MailManifestDetailsVO> {

	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private MailController mailController;

	@Autowired
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;

	@Autowired
	private ProductDefaultsProxy productDefaultsProxy;


	@Override
	public void enrich(MailManifestDetailsVO mailManifestDetailsVO)  {
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

			LoginProfile logonAttributes = contextUtil.callerLoginProfile();
			int airlineId = logonAttributes.getOwnAirlineIdentifier();

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
				////TODO: Exception handling to be corrected
				String subtype = null;

				try {
					subtype = stockcontrolDefaultsProxy.findAutoPopulateSubtype(documentFilterVO);
				} catch (BusinessException e) {
					throw new SystemException(e.getMessage());
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

				try {
					documentValidationVO = populateDocumentvalidatorVo(documentFilterVO, checkAWBAttach, agentCode);
				} catch (SystemException e) {
					throw e;
				}
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
			throw new SystemException(e1.getErrorCode());
		}
		if (documentValidationVO != null && !checkAWBAttach) {

			documentFilterVO.setDocumentNumber(documentValidationVO.getDocumentNumber());
			documentFilterVO.setStockOwner(agentCode);
			try {
				deleteDocumentFromStock(documentFilterVO);
			} catch (StockcontrolDefaultsProxyException e) {

				throw new SystemException(e.getErrorCode());
			}
		}
		return documentValidationVO;

	}

	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws SystemException {
		 return mailController.findAgentCodeForPA(companyCode, officeOfExchange);

	}

	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		return stockcontrolDefaultsProxy.findNextDocumentNumber(documentFilterVO);
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

		productVOs = neoMastersServiceUtils.findProductsByName(documentFilterVO.getCompanyCode(), product);

		if (productVOs != null && !productVOs.isEmpty()) {

			productValidationVO = productVOs.iterator().next();

			productVO = productDefaultsProxy.findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		// Added by A-7540 ends
		stockcontrolDefaultsProxy.deleteDocumentFromStock(documentFilterVO);
	}

	public String findSystemParameterValue(String syspar) throws SystemException {
		return  mailController.findSystemParameterValue(syspar);
	}

}
