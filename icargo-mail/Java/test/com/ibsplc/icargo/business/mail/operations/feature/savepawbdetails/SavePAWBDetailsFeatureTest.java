package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.ConsignmentDocument;
import com.ibsplc.icargo.business.mail.operations.ConsignmentDocumentPK;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.persistors.SavePAWBDetailsPersistor;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers.SavePostalHAWBInvoker;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers.SavePostalShipmentInvoker;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.StockcontrolDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.AWBDocumentValidationVO;
import com.ibsplc.icargo.business.stockcontrol.defaults.vo.DocumentFilterVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.vo.FeatureConfigVO;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.parameter.ParameterUtil;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;


public class SavePAWBDetailsFeatureTest extends AbstractFeatureTest {

	
	private SavePAWBDetailsFeature spy;
	private CarditVO carditVO;
	private SavePAWBDetailsPersistor savePAWBDetailsPersistor;
	private OperationsShipmentProxy operationsShipmentProxy;
	private StockcontrolDefaultsProxy stockcontrolDefaultsProxy;
	private Mailbag mailbagBean;
	private MailController mAilcontroller;
	private static final String MSTDOCNUM = "12345678";
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private static final String OPERATION_SHIPMENT_STOCK_CHECK = "operations.shipment.stockcheckrequired";
	private static final String PRODUCT_CODE_PARAMETER ="mail.operations.productCode";
	private SharedDefaultsProxy sharedDefaultsProxy;
	private ProductDefaultsProxy productDefaultsProxy;
	private SharedCustomerProxy sharedCustomerProxy;
	private SharedCommodityProxy sharedCommodityProxy;
	private FeatureConfigVO featureConfigVO;
	private SavePostalShipmentInvoker savePostalShipmentInvoker;
	private SavePostalHAWBInvoker savePostalHAWBInvoker;
	private ConsignmentDocument consignmentDocumentEntity;


	@Override
	public void setup() throws Exception {
		
		carditVO = setUpCarditVO();	
		savePAWBDetailsPersistor = mockBean(SavePAWBDetailsFeatureConstants.CARDIT_PAWB_PERSISTOR, SavePAWBDetailsPersistor.class);
		operationsShipmentProxy = mockProxy(OperationsShipmentProxy.class);
		mailbagBean = mockBean("MailbagEntity", Mailbag.class);
		EntityManagerMock.mockEntityManager();
		mAilcontroller= mockBean("mAilcontroller", MailController.class);
		spy = spy((SavePAWBDetailsFeature) ICargoSproutAdapter.getBean(SavePAWBDetailsFeatureConstants.SAVE_PAWB_FEATURE));
		stockcontrolDefaultsProxy =mockProxy(StockcontrolDefaultsProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		productDefaultsProxy = mockProxy(ProductDefaultsProxy.class);
		sharedCustomerProxy = mockProxy(SharedCustomerProxy.class);
		sharedCommodityProxy=mockProxy(SharedCommodityProxy.class);
		featureConfigVO = new FeatureConfigVO();
		constructFeatureConfigVO();
		savePostalShipmentInvoker = mockBean(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_SHIPMENT_INVOKER, SavePostalShipmentInvoker.class);
		savePostalHAWBInvoker = mockBean(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_HAWB_INVOKER, SavePostalHAWBInvoker.class); 
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentNumber("CDG054");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setConsignmentSequenceNumber(2);
		consignmentDocumentEntity = new ConsignmentDocument(consignmentDocumentVO);
	}

	private CarditVO setUpCarditVO() {
		CarditVO carditVo = new CarditVO();
		carditVo.setCompanyCode("AV");
		
		CarditPawbDetailsVO carditPawbDetailsVO=new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("JPA");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setConsignmentDestinationAirport("DXB");
		
		ShipmentValidationVO shipmentValidationVo=new ShipmentValidationVO();
		shipmentValidationVo.setShipmentPrefix("134");
		shipmentValidationVo.setMasterDocumentNumber(MSTDOCNUM);
		shipmentValidationVo.setOwnerId(1134);
		shipmentValidationVo.setDuplicateNumber(1);
		shipmentValidationVo.setSequenceNumber(1);
		shipmentValidationVo.setCompanyCode("AV");
		shipmentValidationVo.setStationCode("CDG");
		
		ShipmentDetailVO shipmentDetailVO=new ShipmentDetailVO();
		shipmentDetailVO.setShipmentPrefix("134");
		shipmentDetailVO.setMasterDocumentNumber(MSTDOCNUM);
		shipmentDetailVO.setOwnerId(1134);
		shipmentDetailVO.setDuplicateNumber(1);
		shipmentDetailVO.setSequenceNumber(1);
		shipmentDetailVO.setCompanyCode("AV");
		shipmentDetailVO.setStationCode("CDG");
		shipmentDetailVO.setOwnerCode("aa");
		
		Collection<MailInConsignmentVO> mailInConsigmentVOs=new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO=new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGASGSINAACN24034005000972");
		mailInConsignmentVO.setMailSequenceNumber(3691);
		mailInConsigmentVOs.add(mailInConsignmentVO);
		
		carditPawbDetailsVO.setShipmentValidationVO(shipmentValidationVo);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditPawbDetailsVO.setShipmentDetailVO(shipmentDetailVO);
		carditVo.setCarditPawbDetailsVO(carditPawbDetailsVO);
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVo.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Collection<CarditTotalVO>carditTotalVOs = new ArrayList<>();
		CarditTotalVO carditTotal = new CarditTotalVO();
		carditTotal.setNumberOfReceptacles("2");
		carditTotal.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 4));
		carditTotalVOs.add(carditTotal);
		carditVo.setTotalsInformation(carditTotalVOs);
		return carditVo;
	}
	
	private ConsignmentDocumentVO setConsignmentDocumentVO(){
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingvo = new RoutingInConsignmentVO();
		routingvo.setCompanyCode("AV");
		routingvo.setOnwardCarrierCode("AA");
		routingvo.setOnwardCarrierId(1154);
		routingvo.setPol("CDG");
		routingvo.setPou("DXB");
		routingvo.setOnwardFlightNumber("7525");
		routingvo.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingvo.setTransportStageQualifier("20");
		consignmentRoutingVOs.add(routingvo);
		
		

		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setOriginExchangeOffice("FRCDGA");
		mailInConsignmentVO.setDestinationExchangeOffice("FRCDGA");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsigmentVOs.add(mailInConsignmentVO);

		consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		consignmentDocumentVO.setMailInConsignmentVOs(mailInConsigmentVOs);
		return consignmentDocumentVO;
	}

	@Test(expected = MailTrackingBusinessException.class)
	public void shouldUpdatMailbagWhenFeatureIsExecuted() throws SystemException, FinderException, BusinessException {
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setExistingMailBagsInConsignment(new ConsignmentDocumentVO());
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("AV");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("AV");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		when(ParameterUtil.getInstance().getSystemParameterValue("mail.operations.productCode")).thenReturn("MAL");
		doReturn(null).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),"MAL");
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode("HQMAIL");
		customerFilterVO.setPageNumber(1);
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode("HQMAIL");
		agentLov.setCustomerName("MAIL AGENT");
		agentLov.setIataCode("9999999");
		agentLov.setAddress1("ABC STREET");
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode("HQMAIL");
		consigneeLov.setCustomerName("MAIL AGENT");
		consigneeLov.setAddress1("ABC STREET");
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		Page<CustomerLovVO> agentLovVOs = new Page<>(
				agents, 1, 25, 2, 1, 3, false);
		Page<CustomerLovVO> consigneeLovVOs = new Page<>(
				customers, 1, 25, 2, 1, 3,  false);
		doReturn(agentLovVOs).when(sharedCustomerProxy).findCustomers(customerFilterVO);
		doReturn(consigneeLovVOs).when(sharedCustomerProxy).findCustomers(customerFilterVO);
		Collection<String> systparameters = new ArrayList<>();
		systparameters.add(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		Map<String, String> systemParamterMap1=new HashMap<>();
		systemParamterMap1.put(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER, "MAL");
		doReturn(systemParamterMap1).when(sharedDefaultsProxy).findSystemParameterByCodes(systparameters);	
		CommodityValidationVO commodityValidationVo = new CommodityValidationVO();
		Collection<String> commodityColl = new ArrayList<>();
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityColl.add(systemParamterMap1.get(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER));
		commodityValidationVo.setDensityFactor(1);
		commodityValidationVo.setCommodityCode("MAL");
		commodityValidationVo.setCommodityDesc("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		spy.execute(carditVO);
		

	}
	@Test
	public void validateStock() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditPawbDetailsVO.setExistingMailBagsInConsignment(new ConsignmentDocumentVO());
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode("AV");
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
		documentValidationVO.setCompanyCode("AV");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(1134);
		documentFilterVO.setDocumentNumber(MSTDOCNUM);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("FRA");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("AV");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("AV");
		officeOfExchangePk1.setOfficeOfExchange(DESTN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		doReturn(documentValidationVO).when(stockcontrolDefaultsProxy).validateDocumentInStock(any(DocumentFilterVO.class));
		spy.validate(carditVO);
	}
	@Test
	public void validateStock_withException() throws SystemException, BusinessException, FinderException {
		CarditVO carditVO = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setConsignmentOriginAirport("FRA");
		carditPawbDetailsVO.setConsignmentDestinationAirport("DFW");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditPawbDetailsVO.setExistingMailBagsInConsignment(new ConsignmentDocumentVO());
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVO.setCarditType("2");
		carditVO.setCompanyCode(null);
		carditVO.setConsignmentNumber("TESTDOCNUM212");
		carditVO.setSenderId("FR001");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(0);
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
		documentValidationVO.setCompanyCode("AV");
		DocumentFilterVO documentFilterVO = new DocumentFilterVO();
		documentFilterVO.setCompanyCode(carditVO.getCompanyCode());
		documentFilterVO.setAirlineIdentifier(0);
		documentFilterVO.setDocumentType("AWB");
		when(ParameterUtil.getInstance().getSystemParameterValue(OPERATION_SHIPMENT_STOCK_CHECK)).thenReturn("Y");
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("FRA");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("AV");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange1.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("AV");
		officeOfExchangePk1.setOfficeOfExchange(DESTN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn("HQMAIL").when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(stockcontrolDefaultsProxy).validateDocumentInStock(any(DocumentFilterVO.class));	
		spy.validate(carditVO);
		
	}
	private void constructFeatureConfigVO() {
		ArrayList<String>invokerIds =new ArrayList<>();
		invokerIds.add("mail.operations.savepawbdetails.preinvokers.savepostalhawbinvoker");
		invokerIds.add("mail.operations.savepawbdetails.preinvokers.savepostalshipmentinvoker");
		featureConfigVO.setPreInvokerIds(invokerIds);

	}
	@Test
	public void savePAWBDetailsPerform() throws SystemException, BusinessException, FinderException {
		Mailbag mailbag=new Mailbag();
		mailbag.setMailIdr("FRCDGASGSINAACN24034005000972");
		MailbagPK mailbagPK = new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(3691);
		mailbag.setMailbagPK(mailbagPK);
		doReturn(mailbag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		doNothing().when(savePAWBDetailsPersistor).persist(carditVO.getCarditPawbDetailsVO());
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setCompanyCode("IBS");
		consignmentDocumentVO.setConsignmentNumber("CDG054");
		consignmentDocumentVO.setPaCode("FR001");
		consignmentDocumentVO.setConsignmentSequenceNumber(2);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		ConsignmentDocument consignmentDocument = new ConsignmentDocument();
		consignmentDocument.setAirportCode("FRA");
		doReturn(consignmentDocumentEntity).when(PersistenceController.getEntityManager()).find(eq(ConsignmentDocument.class), any(ConsignmentDocumentPK.class));
		consignmentDocument.update(consignmentDocumentVO);
		spy.fetchFeatureConfig(carditVO);
		spy.perform(carditVO);

	}

}
