/**
 * 
 */
package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.enrichers.lh;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.enrichers.DocumentFilterEnricher;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.webservices.lh.MailStockRetrievalWSProxy;
import com.ibsplc.icargo.business.mail.operations.vo.MailManifestDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.icargo.framework.feature.FeatureComponent;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * @author A-10556
 *
 */
@FeatureComponent(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILTER_ENRICHER_LH)
public class DocumentFilterEnricherLH extends DocumentFilterEnricher{
	private static final Log LOGGER = LogFactory.getLogger(AutoAttachAWBDetailsFeatureConstants.MODULE_SUBMODULE);
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	@Autowired
	Proxy proxyInstance;

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
			int airlineId = operationalFlightVO.getCarrierId();
			StockcontrolDefaultsProxy proxy1 = Proxy.getInstance().get(StockcontrolDefaultsProxy.class);
			if (agentCode != null && agentCode.trim().length() > 0) {
				LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
				documentFilterVO = new DocumentFilterVO();
				documentFilterVO.setAirlineIdentifier(airlineId);
				documentFilterVO.setCompanyCode(companyCode);
				documentFilterVO.setDocumentType(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_TYPE);
				documentFilterVO.setStockOwner(agentCode);
				documentFilterVO.setAwbOrigin(mailbagVOs.iterator().next().getOrigin());
				documentFilterVO.setAwbDestination(mailbagVOs.iterator().next().getDestination());// added
				documentFilterVO.setShipmentPrefix(logonAttributes.getOwnAirlineNumericCode());																					// by
																									// a-7871
																									// for
																									// ICRD-262511
				// Added and modified as part of IASCB-101735 by U-1519 begins
				String subtype = null;
				String isStockCheckEnabled = ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK);
				if(isStockCheckEnabled.contentEquals("Y")) {
				try {
					subtype = proxy1.findAutoPopulateSubtype(documentFilterVO);
				} catch (SystemException | ProxyException e2) {
					LOGGER.log(Log.FINE, e2);
					throw new SystemException(e2.getMessage());
				}
				documentFilterVO.setAwbOrigin(mailbagVOs.iterator().next().getPol());
				}
				ProductVO productVO = mailManifestDetailsVO.getProductVO();
				if (subtype != null && subtype.trim().length() > 0) {
					documentFilterVO.setDocumentSubType(subtype);
				} else {
					documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
				}
				
				boolean checkAWBAttach = checkAwbAttached(mailbagVOs);

				documentValidationVO = populateDocumentValidatorVo(documentFilterVO, checkAWBAttach, agentCode, isStockCheckEnabled); 
				mailManifestDetailsVO.setDocumentFilterVO(documentFilterVO);
				mailManifestDetailsVO.setDocumentValidationVO(documentValidationVO); 
				mailManifestDetailsVO.setCheckAWBAttached(checkAWBAttach);
			}
				

		}
		
	}

	
	private DocumentValidationVO populateDocumentValidatorVo(DocumentFilterVO documentFilterVO, boolean checkAWBAttach,
			String agentCode, String isStockCheckEnabled) throws SystemException {
		DocumentValidationVO documentValidationVO = null;
		
		if(isStockCheckEnabled.contentEquals("Y")) {
		try {
			documentValidationVO = findNextDocumentNumber(documentFilterVO);
		} catch (StockcontrolDefaultsProxyException e1) {
			LOGGER.log(Log.FINE, e1);
			throw new SystemException(e1.getErrors());
		}
		if (documentValidationVO != null && !checkAWBAttach) {

			documentFilterVO.setDocumentNumber(documentValidationVO.getDocumentNumber());
			documentFilterVO.setStockOwner(agentCode);
			try {
				deleteDocumentFromStock(documentFilterVO);
			} catch (StockcontrolDefaultsProxyException e) {
				LOGGER.log(Log.FINE, e);
				throw new SystemException(e.getErrors());
			}
		}
		}
		else {
			DocumentFilterVO documentFilterVOResponse=null;
			if(!checkAWBAttach) {
			try {
				documentFilterVOResponse = proxyInstance.get(MailStockRetrievalWSProxy.class).stockRetrievalForPAWB(documentFilterVO);
			} catch (WebServiceException e) {
				LOGGER.log(Log.FINE, e);
			}
			
			if(documentFilterVOResponse!=null) {
				documentFilterVO.setDocumentNumber(documentFilterVOResponse.getDocumentNumber()); 
			}
			
			documentFilterVO.setStockOwner(agentCode);
			documentValidationVO=new DocumentValidationVO();
			}
		}
		return documentValidationVO;

	}

	@Override
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws SystemException {
		return OfficeOfExchange.findAgentCodeForPA(companyCode, officeOfExchange);
	}

	@Override
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		return Proxy.getInstance().get(StockcontrolDefaultsProxy.class).findNextDocumentNumber(documentFilterVO);
	}

	
	private boolean checkAwbAttached(Collection<MailbagVO> mailbagVOs) {
		boolean isAWBAttached = true;

		for (MailbagVO mailBagVO : mailbagVOs) {
			if (mailBagVO.getDocumentNumber() == null) {
				isAWBAttached = false;
				break;
			}
		}
		return isAWBAttached;
	}

	@Override
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		// Added by A-7540 starts
		String product = findSystemParameterValue(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs;
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO;

		productVOs = Proxy.getInstance().get(ProductDefaultsProxy.class).findProductsByName(documentFilterVO.getCompanyCode(), product);

		if (productVOs != null) {

			productValidationVO = productVOs.iterator().next();

			productVO = Proxy.getInstance().get(ProductDefaultsProxy.class).findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		// Added by A-7540 ends
		Proxy.getInstance().get(StockcontrolDefaultsProxy.class).deleteDocumentFromStock(documentFilterVO);
	}

	@Override
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
