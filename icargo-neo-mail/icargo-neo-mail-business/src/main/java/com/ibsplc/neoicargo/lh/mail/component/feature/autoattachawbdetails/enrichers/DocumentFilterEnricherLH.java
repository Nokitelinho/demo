/**
 * 
 */
package com.ibsplc.neoicargo.lh.mail.component.feature.autoattachawbdetails.enrichers;

import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentValidationVO;
import com.ibsplc.neoicargo.common.types.Pair;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.framework.orchestration.Enricher;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.ProductDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.neoicargo.mail.component.proxy.webservices.lh.MailStockRetrievalWSProxy;
import com.ibsplc.neoicargo.mail.exception.StockcontrolDefaultsProxyException;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.vo.MailManifestDetailsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OperationalFlightVO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.WebServiceException;
import java.util.*;

/**
 * @author A-10556
 *
 */
@Slf4j
@Component(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILTER_ENRICHER_LH)
public class DocumentFilterEnricherLH  extends Enricher<MailManifestDetailsVO> {
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";

	@Autowired
	private ContextUtil contextUtil;

	@Autowired
	private ProductDefaultsProxy productDefaultsProxy;

	@Autowired
	private	StockcontrolDefaultsProxy stockcontrolDefaultsProxy;

	@Autowired
	private	SharedDefaultsProxy sharedDefaultsProxy;

	@Autowired
	NeoMastersServiceUtils neoMastersServiceUtils;


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
			int airlineId = operationalFlightVO.getCarrierId();

			if (agentCode != null && agentCode.trim().length() > 0) {
				LoginProfile logonAttributes = contextUtil.callerLoginProfile();
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
				List<String> systemParameters = new ArrayList<String>();
				systemParameters.add(OPERATION_SHIPMENT_STOCK_CHECK);
				String subtype = null;
				List<Pair> systemParameterList = null;
				String isStockCheckEnabled = null;
				try {
					systemParameterList = neoMastersServiceUtils.findSystemParameters(systemParameters);
				} catch (BusinessException e) {
					e.printStackTrace();
				}
				isStockCheckEnabled = systemParameterList.stream()
						.filter(pair -> pair.getName().equals(OPERATION_SHIPMENT_STOCK_CHECK))
						.findFirst().get().getValue().toString();
				if(isStockCheckEnabled.contentEquals("Y")) {
				try {
					subtype = stockcontrolDefaultsProxy.findAutoPopulateSubtype(documentFilterVO);
				} catch (BusinessException e) {
					e.printStackTrace();
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

				try {
					documentValidationVO = populateDocumentValidatorVo(documentFilterVO, checkAWBAttach, agentCode, isStockCheckEnabled);
				} catch (SystemException e) {
					e.printStackTrace();
				}
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
			log.error(e1.getMessage());
			throw new SystemException(e1.getErrorCode());
		}
		if (documentValidationVO != null && !checkAWBAttach) {

			documentFilterVO.setDocumentNumber(documentValidationVO.getDocumentNumber());
			documentFilterVO.setStockOwner(agentCode);
			try {
				deleteDocumentFromStock(documentFilterVO);
			} catch (StockcontrolDefaultsProxyException e) {
				log.error(e.getMessage());
				throw new SystemException(e.getErrorCode());
			}
		}
		}
		else {
			DocumentFilterVO documentFilterVOResponse=null;
			if(!checkAWBAttach) {
			try {
				documentFilterVOResponse =new MailStockRetrievalWSProxy().stockRetrievalForPAWB(documentFilterVO);
			} catch (WebServiceException e) {
				log.error(e.getMessage());
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

	//@Override
	public String findAgentCodeForPA(String companyCode, String officeOfExchange) throws SystemException {
		//TODO:Neo to correct
		//return OfficeOfExchange.findAgentCodeForPA(companyCode, officeOfExchange);
		return null;
	}

	//@Override
	public DocumentValidationVO findNextDocumentNumber(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		return stockcontrolDefaultsProxy.findNextDocumentNumber(documentFilterVO);
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

	//@Override
	public void deleteDocumentFromStock(DocumentFilterVO documentFilterVO)
			throws SystemException, StockcontrolDefaultsProxyException {
		// Added by A-7540 starts
		String product = findSystemParameterValue(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_PRODUCT);
		Collection<ProductValidationVO> productVOs;
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO;

		productVOs = neoMastersServiceUtils.findProductsByName(documentFilterVO.getCompanyCode(), product);

		if (productVOs != null) {

			productValidationVO = productVOs.iterator().next();

			productVO = productDefaultsProxy.findProductDetails(documentFilterVO.getCompanyCode(),
					productValidationVO.getProductCode());
		}
		documentFilterVO.setDocumentSubType(productVO.getDocumentSubType());
		// Added by A-7540 ends
		stockcontrolDefaultsProxy.deleteDocumentFromStock(documentFilterVO);
	}

	//@Override
	public String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

}
