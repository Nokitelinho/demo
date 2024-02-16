package com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.enrichers.lh;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
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
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;


public class DocumentFilterEnricherLHTest extends AbstractFeatureTest {
	
	private DocumentFilterEnricherLH enricher;
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
	private MailStockRetrievalWSProxy mailStockRetrievalWSProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private ProductDefaultsProxy productDefaultsProxy;
	private MailTrackingDefaultsDAO mailTrackingDefaultsDAO;

	@Override
	public void setup() throws Exception {
		enricher = spy((DocumentFilterEnricherLH) ICargoSproutAdapter
				.getBean(AutoAttachAWBDetailsFeatureConstants.DOCUMENT_FILTER_ENRICHER_LH));
		stockcontrolDefaultsProxy = mockProxy(StockcontrolDefaultsProxy.class);
		mailStockRetrievalWSProxy = mockProxy(MailStockRetrievalWSProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		productDefaultsProxy = mockProxy(ProductDefaultsProxy.class);
		EntityManagerMock.mockEntityManager();
		mailTrackingDefaultsDAO = mock(MailTrackingDefaultsSqlDAO.class);
		doReturn(mailTrackingDefaultsDAO).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	}
	
	@Test
	public void shouldSetDocumentNumberIfStockCheckEnabledIsY() throws SystemException, ProxyException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		String subtype = "DOCSUBTYP";
		
		DocumentValidationVO documentValidationVO = new DocumentValidationVO();
		documentValidationVO.setDocumentNumber("DOCNUM");
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doReturn(documentValidationVO).when(Proxy.getInstance().get(StockcontrolDefaultsProxy.class)).findNextDocumentNumber(any(DocumentFilterVO.class));
		doNothing().when(enricher).deleteDocumentFromStock(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
		assertEquals("DOCNUM", mailManifestDetailsVO.getDocumentFilterVO().getDocumentNumber());
	}
	
	@Test
	public void shouldSetDocumentNumberIfStockCheckEnabledIsN() throws ProxyException, SystemException, WebServiceException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		ProductVO productVO = new ProductVO();
		mailManifestDetailsVO.setProductVO(productVO);
		
		String subtype = "DOCSUBTYP";
		
		DocumentFilterVO documentFilterVOResponse = new DocumentFilterVO();
		documentFilterVOResponse.setDocumentNumber("DOCNUM");
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("N").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doReturn(documentFilterVOResponse).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
		assertEquals("DOCNUM", mailManifestDetailsVO.getDocumentFilterVO().getDocumentNumber());
	}
	
	@Test
	public void shouldDeleteDocumentFromStock() throws StockcontrolDefaultsProxyException, SystemException {
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mail.operations.productCode", "value");
		
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productVOs.add(productValidationVO);
		
		ProductVO productVO = new ProductVO();
		productVO.setDocumentSubType("DOCSUBTYP");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(any(String.class), any(String.class));
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(any(String.class), any(String.class));
		
		enricher.deleteDocumentFromStock(documentFilterVO);
	}
	
	@Test
	public void shouldReturnIfOperationalFlightVO() throws SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		enricher.enrich(mailManifestDetailsVO);
		assertNull(mailManifestDetailsVO.getDocumentFilterVO());
	}
	
	@Test
	public void shouldNotEnrichIfMailBagVOsIsNull() throws SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		
		enricher.enrich(mailManifestDetailsVO);
		assertNull(mailManifestDetailsVO.getDocumentFilterVO());
	}
	
	@Test
	public void shouldNotEnrichIfMailBagVOsIsEmpty() throws SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(Collections.emptyList());
		
		enricher.enrich(mailManifestDetailsVO);
		assertNull(mailManifestDetailsVO.getDocumentFilterVO());
	}

	@Test
	public void shouldNotpopulateDocumentValidatorVoIfAgentCodeIsNull() throws SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVOs.add(mailbagVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		doReturn(null).when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		
		enricher.enrich(mailManifestDetailsVO);
		assertNull(mailManifestDetailsVO.getDocumentFilterVO());
	}
	
	@Test
	public void shouldNotPopulateDocumentValidatorVoIfAgentCodeIsEmpty() throws SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVOs.add(mailbagVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		doReturn("").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		
		enricher.enrich(mailManifestDetailsVO);
		assertNull(mailManifestDetailsVO.getDocumentFilterVO());
	}
	
	@Test(expected=SystemException.class)
	public void shouldThrowSystemExceptionWhenFindAutoPopulateSubtype() throws ProxyException, SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		ProductVO productVO = new ProductVO();
		mailManifestDetailsVO.setProductVO(productVO);
		
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doThrow(new SystemException("unknown")).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
	}
	
	@Test
	public void shouldPopulateDocumentSubTypeFromProductVOIfSubTypeIsNull() throws ProxyException, SystemException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVO.setDocumentNumber("DOCNO");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		ProductVO productVO = new ProductVO();
		productVO.setDocumentSubType("DOCSUBTYP");
		mailManifestDetailsVO.setProductVO(productVO);
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(null).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		
		enricher.enrich(mailManifestDetailsVO);
		assertEquals("DOCSUBTYP", mailManifestDetailsVO.getDocumentFilterVO().getDocumentSubType());
	}
	
	@Test
	public void shouldPopulateDocumentSubTypeFromProductVOIfSubTypeIsEmpty() throws ProxyException, SystemException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVO.setDocumentNumber("DOCNO");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		ProductVO productVO = new ProductVO();
		productVO.setDocumentSubType("DOCSUBTYP");
		mailManifestDetailsVO.setProductVO(productVO);
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn("").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		
		enricher.enrich(mailManifestDetailsVO);
		assertEquals("DOCSUBTYP", mailManifestDetailsVO.getDocumentFilterVO().getDocumentSubType());
	}
	
	@Test
	public void shouldFindAgentCodeForPA() throws SystemException, PersistenceException {
		String companyCode = "";
		String officeOfExchange = "";
		doReturn("").when(mailTrackingDefaultsDAO).findAgentCodeForPA(any(String.class), any(String.class));
		enricher.findAgentCodeForPA(companyCode, officeOfExchange);
	}
	
	@Test(expected=SystemException.class)
	public void shouldThrowSystemExceptionWhenFindNextDocumentNumber() throws ProxyException, SystemException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		String subtype = "DOCSUBTYP";
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doThrow(StockcontrolDefaultsProxyException.class).when(Proxy.getInstance().get(StockcontrolDefaultsProxy.class)).findNextDocumentNumber(any(DocumentFilterVO.class));
		doNothing().when(enricher).deleteDocumentFromStock(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
	}
	
	@Test(expected=SystemException.class)
	public void shouldThrowStockcontrolDefaultsProxyExceptionWhenDeleteDocumentFromStock() throws SystemException, ProxyException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		String subtype = "DOCSUBTYP";
		
		DocumentValidationVO documentValidationVO = new DocumentValidationVO();
		documentValidationVO.setDocumentNumber("DOCNUM");
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doReturn(documentValidationVO).when(Proxy.getInstance().get(StockcontrolDefaultsProxy.class)).findNextDocumentNumber(any(DocumentFilterVO.class));
		doThrow(StockcontrolDefaultsProxyException.class).when(enricher).deleteDocumentFromStock(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
	}
	
	@Test
	public void shouldNotSetDocumentNumberWhenWebServiceException() throws ProxyException, SystemException, WebServiceException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		ProductVO productVO = new ProductVO();
		mailManifestDetailsVO.setProductVO(productVO);
		
		String subtype = "DOCSUBTYP";
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("N").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doThrow(WebServiceException.class).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
	}
	
	@Test
	public void shouldNotPopulateIfDocumentValidationVOIsNull() throws ProxyException, SystemException, StockcontrolDefaultsProxyException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVO.setDocumentNumber("DOCNO");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		String subtype = "DOCSUBTYP";
		
		DocumentValidationVO documentValidationVO = new DocumentValidationVO();
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("Y").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		doReturn(documentValidationVO).when(Proxy.getInstance().get(StockcontrolDefaultsProxy.class)).findNextDocumentNumber(any(DocumentFilterVO.class));
		
		enricher.enrich(mailManifestDetailsVO);
	}
	
	@Test
	public void shouldNotPopulateDocumentNumberIfCheckAWBAttachIsTrue() throws ProxyException, SystemException {
		MailManifestDetailsVO mailManifestDetailsVO = new MailManifestDetailsVO();
		
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode("LH");
		operationalFlightVO.setCarrierId(183);
		
		Collection<MailbagVO> mailbagVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setOrigin("ORG");
		mailbagVO.setDestination("DESTINATION");
		mailbagVO.setDocumentNumber("DOCNO");
		mailbagVOs.add(mailbagVO);
		
		mailManifestDetailsVO.setOperationalFlightVO(operationalFlightVO);
		mailManifestDetailsVO.setMailbagVOs(mailbagVOs);
		
		ProductVO productVO = new ProductVO();
		mailManifestDetailsVO.setProductVO(productVO);
		
		String subtype = "DOCSUBTYP";
		
		doReturn("AGTCOD").when(enricher).getContextObject(AutoAttachAWBDetailsFeatureConstants.SAVE_AGENTCODE);
		doReturn(subtype).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn("N").when(ParameterUtil.getInstance()).getSystemParameterValue("operations.shipment.stockcheckrequired");
		
		enricher.enrich(mailManifestDetailsVO);
	}

}
