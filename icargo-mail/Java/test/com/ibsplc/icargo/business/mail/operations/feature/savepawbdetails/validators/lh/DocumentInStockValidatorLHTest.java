package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators.lh;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.webservices.lh.MailStockRetrievalWSProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentFilterVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.StockAgentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.services.jaxws.proxy.exception.WebServiceException;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class DocumentInStockValidatorLHTest extends AbstractFeatureTest {
	private DocumentInStockValidatorLH validator;
	private OperationsShipmentProxy operationsShipmentProxy;
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
	private MailStockRetrievalWSProxy mailStockRetrievalWSProxy;
	private static final String MSTDOCNUM = "12345678";
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private MailController mAilcontroller;
	private MailTrackingDefaultsDAO dao;
	private ProductDefaultsProxy productDefaultsProxy;






	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		operationsShipmentProxy = mockProxy(OperationsShipmentProxy.class);
		stockcontrolDefaultsProxy =mockProxy(StockcontrolDefaultsProxy.class);
		mAilcontroller =mockBean("mAilcontroller", MailController.class);
		dao = mock(MailTrackingDefaultsDAO.class);
		validator =  (DocumentInStockValidatorLH) ICargoSproutAdapter
				.getBean(SavePAWBDetailsFeatureConstants.DOCUMENT_IN_STOCK_VALIDATORLH);
		productDefaultsProxy = mockProxy(ProductDefaultsProxy.class);
		mailStockRetrievalWSProxy=mockProxy(MailStockRetrievalWSProxy.class);



	}
	@Test
	public void validateStock() throws SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
//		documentFilterVO.setAirlineIdentifier(0);
		documentFilterVO.setDocumentNumber(MSTDOCNUM);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).validateDocumentInStock(documentFilterVO);
		validator.validate(carditVO);
			
	}
	
	
	@Test
	public void validateNextDocNumber() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(null);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		Collection<StockAgentVO> stockAgentVOs = new Page<StockAgentVO>();
		StockAgentVO stockAgentVO = new StockAgentVO();
		stockAgentVO.setCompanyCode("IBS");
		stockAgentVO.setStockHolderCode("HQMAIL");
		stockAgentVOs.add(stockAgentVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(stockAgentVOs).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("M").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}
	
	@Test
	public void validateNextDocNumberWithNostockHolderCode() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(null);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(null).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("M").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStockthrowsException() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(null);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		carditVO.setCarditPawbDetailsVO(null);
		validator.validate(carditVO);
	}
	@Test
	public void validateStock_withEmptyDocNum() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(new Page<StockAgentVO>() ).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}
	@Test
	public void validateStockwithFinderException() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(new Page<StockAgentVO>() ).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}

	@Test
	public void validateStockoperationshipmentstockcheckparameterN() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix(null);
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		documentFilterVO.setShipmentPrefix("123");
		documentFilterVO.setDocumentNumber("45678967");
 	    doReturn(documentFilterVO).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}
	
	
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStockoperationshipmentstockcheckparameterNbutnostockfound() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix(null);
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		doReturn(null).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}
	
	
	
	@Test
	public void validateNextDocNumberForCancelCardit() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(null);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("1");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		Collection<StockAgentVO> stockAgentVOs = new Page<StockAgentVO>();
		StockAgentVO stockAgentVO = new StockAgentVO();
		stockAgentVO.setCompanyCode("IBS");
		stockAgentVO.setStockHolderCode("HQMAIL");
		stockAgentVOs.add(stockAgentVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(stockAgentVOs).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("M").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}
	@Test
	public void validateStockCancelCarditWithoutConsignmentDocumentNumber() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(null);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("1");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		Collection<StockAgentVO> stockAgentVOs = new Page<StockAgentVO>();
		StockAgentVO stockAgentVO = new StockAgentVO();
		stockAgentVO.setCompanyCode("IBS");
		stockAgentVO.setStockHolderCode("HQMAIL");
		stockAgentVOs.add(stockAgentVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(stockAgentVOs).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("M").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}
	@Test
	public void validateStockCancelCardit() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DXB");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("1");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		StockAgentFilterVO stockAgentFilterVO = new StockAgentFilterVO();
		stockAgentFilterVO.setCompanyCode("IBS");
		stockAgentFilterVO.setAgentCode("HQMAIL");
		stockAgentFilterVO.setPageNumber(1);
		Collection<StockAgentVO> stockAgentVOs = new Page<StockAgentVO>();
		StockAgentVO stockAgentVO = new StockAgentVO();
		stockAgentVO.setCompanyCode("IBS");
		stockAgentVO.setStockHolderCode("HQMAIL");
		stockAgentVOs.add(stockAgentVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(stockAgentVOs).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("M").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}
	
	
	@Test
	public void validateStockoperationshipmentstockcheckparameterNupdateCardit() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix(null);
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber("23456");
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		validator.validate(carditVO);
		
	}
	
	
	@Test
	public void validateStockoperationshipmentstockcheckparameterYupdateCardit() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix(null);
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber("23456");
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		validator.validate(carditVO);
		
	}
	
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStockoperationshipmentstockcheckparameterNbutNoDocumentNumberReturned() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix(null);
		carditPawbDetailsVO.setMasterDocumentNumber("");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		DocumentFilterVO documentFilterVOResponse = new DocumentFilterVO() ;
		documentFilterVOResponse.setShipmentPrefix("020");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		doReturn(documentFilterVOResponse).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}
	
	
	@Test
	public void validateStockWhenCarditTypeACP() throws SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("ACP");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(MSTDOCNUM);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).validateDocumentInStock(documentFilterVO);
		validator.validate(carditVO);
			
	}
	
	@Test
	public void validateStockoperationshipmentstockcheckparameterNupdateCarditAndExistingMailBagsNull() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode(null);
		ConsignmentDocumentVO consignmentDocumentVO = null;
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setShipmentPrefix("134");
		documentFilterVO.setDocumentNumber(MSTDOCNUM);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).validateDocumentInStock(documentFilterVO);
		 doReturn(documentFilterVO).when(mailStockRetrievalWSProxy).stockRetrievalForPAWB(any(DocumentFilterVO.class));
		validator.validate(carditVO);
	}


}
