package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.validators;


import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.StockcontrolDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
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
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;


public class DocumentInStockValidatorTest extends AbstractFeatureTest {
	private DocumentInStockValidator validator;
	private OperationsShipmentProxy operationsShipmentProxy;
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
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
		validator =  (DocumentInStockValidator) ICargoSproutAdapter
				.getBean(SavePAWBDetailsFeatureConstants.DOCUMENT_IN_STOCK_VALIDATOR);
		productDefaultsProxy = mockProxy(ProductDefaultsProxy.class);



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
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStock_withNoAirlineValidationVO() throws SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		doReturn(null).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		LogonAttributes logon = new LogonAttributes();
		logon.setCompanyCode("IBS");
		logon.setAirlineIdentifier(1134);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		validator.validate(carditVO);
			
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStock_withNocarditPawbDetailsVO() throws SystemException, BusinessException {
		CarditVO carditVo = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = null;
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		validator.validate(carditVo);
			
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStock_withNoStockCheck() throws SystemException, BusinessException {
		CarditVO carditVo = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = null;
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		validator.validate(carditVo);
			
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
	public void validateNextDocNumber_WithNostockHolderCode() throws SystemException, BusinessException, FinderException {
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
	public void validateStock_throwsException() throws SystemException, BusinessException, FinderException {
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
		AWBDocumentValidationVO documentValidationVO = new AWBDocumentValidationVO() ;
		documentValidationVO.setCompanyCode("IBS");
		documentValidationVO.setDocumentNumber("23456");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
//		documentFilterVO.setAirlineIdentifier(0);
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
		doReturn(null).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		doThrow(StockcontrolDefaultsProxyException.class).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));

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
	public void validateStock_withFinderException() throws SystemException, BusinessException, FinderException {
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
	public void validateStock_withNoStock() throws SystemException, BusinessException, FinderException {
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
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(new Page<StockAgentVO>() ).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
		validator.validate(carditVO);
		
	}
	@Test
	public void validateStock_withNoShipmentPrefix() throws SystemException, BusinessException, FinderException {
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
//		consignmentDocumentVO.setConsignmentMasterDocumnetNumber(MSTDOCNUM);
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
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(null);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("N");
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(new Page<StockAgentVO>() ).when(stockcontrolDefaultsProxy).findStockAgentMappings(any(StockAgentFilterVO.class));
		doReturn("").when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));
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
	public void validateStock_CancelCarditWithoutConsignmentDocumentNumber() throws SystemException, BusinessException, FinderException {
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
	public void validateStock_CancelCardit() throws SystemException, BusinessException, FinderException {
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
	public void validateStock_forUpdateCardit() throws SystemException, BusinessException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setShipperCode("FRA");
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setConsignmentMasterDocumentNumber("23456");
		carditPawbDetailsVO.setExistingMailBagsInConsignment(consignmentDocumentVO);
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
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
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(MSTDOCNUM);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).validateDocumentInStock(documentFilterVO);
		validator.validate(carditVO);
}
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStock_throwsSystemException() throws SystemException, BusinessException, FinderException {
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
		doReturn(null).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));

		validator.validate(carditVO);
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void validateStock_WithNoDocumentValidationVo() throws SystemException, BusinessException, FinderException {
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
		doReturn(null).when(stockcontrolDefaultsProxy).findAutoPopulateSubtype(any(DocumentFilterVO.class));
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		SystemException systemException = new SystemException("");
		doReturn(null).when(stockcontrolDefaultsProxy).findNextDocumentNumber(any(DocumentFilterVO.class));

		validator.validate(carditVO);
	}
}
