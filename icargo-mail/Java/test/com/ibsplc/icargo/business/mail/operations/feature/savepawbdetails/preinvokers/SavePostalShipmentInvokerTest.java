package com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.preinvokers;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.ProductDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedCommodityProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedCustomerProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditReceptacleVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.OtherCustomsInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductVO;
import com.ibsplc.icargo.business.products.defaults.vo.ProductValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.util.unit.UnitException;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.query.Page;



public class SavePostalShipmentInvokerTest extends AbstractFeatureTest {

	private SavePostalShipmentInvoker spy;
	private CarditVO carditVO;
	private MailController mAilcontroller;
	private ShipmentValidationVO shipmentValidationVO;
	private ShipmentDetailVO shipmentDetailVOToSave;
	private SharedCustomerProxy sharedCustomerProxy;
	private OperationsShipmentProxy operationsShipmentProxy;
	private ProductDefaultsProxy productDefaultsProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private SharedCommodityProxy sharedCommodityProxy;
	private SharedAreaProxy sharedAreaProxy;
	private static final String MSTDOCNUM = "12345678";
	private static final String DESTN = "FRPART";
	private static final String ORIGIN = "AEDXBT";
	private static final String AGENTCODE="HQMAIL";
	private static final String PRODUCT_CODE_PARAMETER ="mail.operations.productCode";
	private static final String MAILAGENT="MAIL AGENT";
	private static final String IATACODE="9999999";
	private static final String ADDRESS="ABC STREET";
	private SavePostalShipmentInvoker shipmentInvokerMock;
	private Measure measure;
	
	@Override
	public void setup() throws Exception {
		
		EntityManagerMock.mockEntityManager();
		operationsShipmentProxy = mockProxy(OperationsShipmentProxy.class);
		mAilcontroller= mockBean("mAilcontroller", MailController.class);
		sharedCustomerProxy = mockProxy(SharedCustomerProxy.class);
		productDefaultsProxy = mockProxy(ProductDefaultsProxy.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		sharedAreaProxy = mockProxy(SharedAreaProxy.class);
		carditVO = setCarditVO();
		shipmentValidationVO = setShipmentValidationVO();
		shipmentDetailVOToSave = setShipmentDetailVO();
		sharedCommodityProxy=mockProxy(SharedCommodityProxy.class);
		spy = (SavePostalShipmentInvoker) ICargoSproutAdapter
				.getBean(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_SHIPMENT_INVOKER);
		shipmentInvokerMock= mockBean(SavePAWBDetailsFeatureConstants.SAVE_POSTAL_SHIPMENT_INVOKER, SavePostalShipmentInvoker.class);
		measure = mock(Measure.class);
		
	}

	private CarditVO setCarditVO() {
		CarditVO carditVo = new CarditVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVO.setContractIDNumber("1");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		Collection<CarditTotalVO> totalsInformation1=new ArrayList<>();
		CarditTotalVO carditTotalVO1=new CarditTotalVO();
		carditTotalVO1.setNumberOfReceptacles("1");
		carditTotalVO1.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation1.add(carditTotalVO1);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation1);
		carditPawbDetailsVO.setConsigneeCode("USA");
		carditPawbDetailsVO.setShipmentPrefix("134");
		carditPawbDetailsVO.setMasterDocumentNumber(MSTDOCNUM);
		carditPawbDetailsVO.setConsignmentDestination(DESTN);
		carditPawbDetailsVO.setConsignmentOrigin(ORIGIN);
		carditPawbDetailsVO.setShipperCode("FRA");
		carditPawbDetailsVO.setAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsigneeAgentCode("HQMAIL");
		carditPawbDetailsVO.setConsignmentOriginAirport("CDG");
		carditPawbDetailsVO.setConsignmentDestinationAirport("DXB");
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVo.setCarditPawbDetailsVO(carditPawbDetailsVO);
		carditVo.setCarditType("2");
		carditVo.setCompanyCode("IBS");
		carditVo.setConsignmentNumber("TESTDOCNUM212");
		carditVo.setSenderId("FR001");
		carditVo.setSecurityStatusDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		carditVo.setConsignmentIssuerName("TEST POSTNL - KLM");
		
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVo.setReceptacleInformation(receptacleInformation);
		
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();
		CarditTotalVO carditTotalVO=new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		carditTotalVO.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation.add(carditTotalVO);
		carditVo.setTotalsInformation(totalsInformation);
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVo.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		return carditVo;

	}

	private ConsignmentDocumentVO setConsignmentDocumentVO(){
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingvo = new RoutingInConsignmentVO();
		routingvo.setCompanyCode("IBS");
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
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsignmentVO.setContractIDNumber("1");
		mailInConsigmentVOs.add(mailInConsignmentVO);

		consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		consignmentDocumentVO.setMailInConsignmentVOs(mailInConsigmentVOs);
		return consignmentDocumentVO;
	}
	private ShipmentValidationVO setShipmentValidationVO() {
		ShipmentValidationVO shipmentValidationVo = new ShipmentValidationVO();
		shipmentValidationVo.setShipmentPrefix("134");
		shipmentValidationVo.setMasterDocumentNumber(MSTDOCNUM);
		shipmentValidationVo.setOwnerId(1134);
		shipmentValidationVo.setDuplicateNumber(1);
		shipmentValidationVo.setSequenceNumber(1);
		shipmentValidationVo.setCompanyCode("IBS");
		shipmentValidationVo.setStationCode("CDG");
		return shipmentValidationVo;
	}

	private ShipmentDetailVO setShipmentDetailVO() {

		
		ShipmentDetailVO shipmentDetailVO = new ShipmentDetailVO();
		shipmentDetailVO.setCompanyCode("IBS");
		shipmentDetailVO.setOwnerId(1154);
		shipmentDetailVO.setMasterDocumentNumber(MSTDOCNUM);
		shipmentDetailVO.setOperationFlag("I");
		shipmentDetailVO.setStatedWeightCode("K");
		shipmentDetailVO.setDateOfJourney(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
		shipmentDetailVO.setScc("MAL");
		shipmentDetailVO.setSourceIndicator("MAL");
		shipmentDetailVO.setOverrideCertificateValidations("N");
		shipmentDetailVO.setSciValidationToBeSkipped(true);
		shipmentDetailVO.setShipmentPrefix("134");
		shipmentDetailVO.setServiceCargoClass(AutoAttachAWBDetailsFeatureConstants.MAIL_SERVICE_CARGO_CLASS);
		shipmentDetailVO.setScc(AutoAttachAWBDetailsFeatureConstants.MAIL_AWB_SOURCE);
		shipmentDetailVO.setParameterWarningToBeDiscarded(true);
		shipmentDetailVO.setOrigin("CDG");
		shipmentDetailVO.setDestination("DXB");

		shipmentDetailVO.setShipperCode("aaa");
		shipmentDetailVO.setShipperName("aaa");
		shipmentDetailVO.setShipperAddress1("aaa");
		shipmentDetailVO.setShipperCity("aaa");
		shipmentDetailVO.setShipperCountry("aaa");
		shipmentDetailVO.setShipperAccountNumber("aaa");
		shipmentDetailVO.setShipperTelephoneNumber("aaa");
		shipmentDetailVO.setShipperEmailId("aaa");
		shipmentDetailVO.setShipperState("aaa");
		shipmentDetailVO.setShipperPostalCode("aaa");
		shipmentDetailVO.setScc("MAL,SPX");
		shipmentDetailVO.setSenderFileReference("1");
		shipmentDetailVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5,5,"K"));
		shipmentDetailVO.getStatedWeight().setDisplayValue(5.5);
		shipmentDetailVO.getStatedWeight().setDisplayUnit("K");
		return shipmentDetailVO;

	}
	
	@Test
	public void carditTypeAsCancel() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsignmentVO.setContractIDNumber("1");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		shipmentDetailVOToSave.setShipmentStatus(ShipmentDetailVO.AWB_EXECUTED);
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
		
	}
	
	@Test
	public void shouldNotSaveAWBCarditPawbdetailsVoIsNull() throws SystemException, BusinessException {
		carditVO.setCarditPawbDetailsVO(null);
		spy.invoke(carditVO);
		
	}
	
	@Test
	public void shouldReturnExceptionFromMailController() throws SystemException, BusinessException {
		carditVO.setCompanyCode(null);
		doThrow(FinderException.class).when(mAilcontroller).findAgentFromUpucode(null, carditVO.getCarditPawbDetailsVO().getShipperCode());
		spy.invoke(carditVO);
	}
	
	@Test
	public void agentCodeAndConsigneeCodeIsNullFromMailController() throws SystemException, BusinessException {
		doReturn(null).when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		spy.invoke(carditVO);
	}
	
	@Test
	public void agentCodeIsNullFromMailController() throws SystemException, BusinessException {
		doReturn(null).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		spy.invoke(carditVO);
	}
	@Test
	public void consigneeCodeIsNullFromMailController() throws SystemException, BusinessException {
		doReturn(null).when(mAilcontroller).findAgentFromUpucode(any(String.class), any(String.class));
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		spy.invoke(carditVO);
	}
	@Test
	public void consignmentOriginIsNull() throws SystemException, BusinessException {
		carditVO.getCarditPawbDetailsVO().setConsignmentOrigin(null);	
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		spy.invoke(carditVO);
	}
	@Test
	public void consignmentDestnIsNull() throws SystemException, BusinessException {
		carditVO.getCarditPawbDetailsVO().setConsignmentDestination(null);
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		spy.invoke(carditVO);
	}
	@Test
	public void airlineValidationVOIsNull() throws SystemException, FinderException, BusinessException {
		carditVO.getCarditPawbDetailsVO().setShipmentPrefix("8907");	
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		doReturn(null).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		doReturn("EUR").when(sharedAreaProxy).getDefaultCurrencyForStation(any(String.class),any(String.class));
		CommodityValidationVO commodityValidationVo = new CommodityValidationVO();
		Collection<String> commodityColl = new ArrayList<>();
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityColl.add(systemParamterMap1.get(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER));
		commodityValidationVo.setDensityFactor(1);
		commodityValidationVo.setCommodityCode("MAL");
		commodityValidationVo.setCommodityDesc("MAL");
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		spy.invoke(carditVO);
	}
	

	
	@Test
	public void agentLovsAreIsEmpty() throws SystemException, FinderException, BusinessException {
	
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		doReturn(new ArrayList<>()).when(sharedCustomerProxy).findCustomers(customerFilterVO);
		doReturn(new ArrayList<>()).when(sharedCustomerProxy).findCustomers(customerFilterVO);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void productVOsAreNull() throws SystemException, FinderException, BusinessException {
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		Collection<ProductValidationVO> productVOs = null;
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		spy.invoke(carditVO);
	}
	
	@Test
	public void shouldSaveAWBAndReturnShipmentValidationVO() throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
		
		
	}
	
	@Test
	public void shouldNotSaveAWBThrowException() throws SystemException, BusinessException, FinderException{
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doThrow(ProxyException.class).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		spy.invoke(carditVO);
		
		
	}
	
	@Test
	public void routingInfoInConsignementVOIsNull() throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		consignmentDocumentVO.setRoutingInConsignmentVOs(null);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);
		
	}
	@Test
	public void routingInfoInConsignementVOIsEmpty() throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		consignmentDocumentVO.setRoutingInConsignmentVOs(new ArrayList<>());
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);
		
	}
	
	@Test
	public void routingInfoTransportStageQualifierIsNot20InConsignementVO() throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = new ArrayList<>();
		RoutingInConsignmentVO routingvo = new RoutingInConsignmentVO();
		routingvo.setCompanyCode("IBS");
		routingvo.setOnwardCarrierCode("AA");
		routingvo.setOnwardCarrierId(1154);
		routingvo.setPol("CDG");
		routingvo.setPou("DXB");
		routingvo.setOnwardFlightNumber("7525");
		routingvo.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		routingvo.setTransportStageQualifier("17");
		consignmentRoutingVOs.add(routingvo);
		consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);
		
	}
	
	
	@Test
	public void bookingCommodityParameterMapIsEmpty() throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		doReturn(new HashMap<>()).when(sharedDefaultsProxy).findSystemParameterByCodes(systparameters);	
		CommodityValidationVO commodityValidationVo = new CommodityValidationVO();
		Collection<String> commodityColl = new ArrayList<>();
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityColl.add(systemParamterMap1.get(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER));
		commodityValidationVo.setDensityFactor(1);
		commodityValidationVo.setCommodityCode("MAL");
		commodityValidationVo.setCommodityDesc("MAL");
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(new HashMap<>()).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);	
	}
	
	@Test
	public void validateCommodityShouldThrowroxyException() throws SystemException, FinderException, BusinessException {

		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doThrow(ProxyException.class).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);	
	}
	
	@Test
	public void statedPieceVolumMakingLessthanMinVol()throws SystemException, FinderException, BusinessException {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();
		CarditTotalVO carditTotalVO=new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		carditTotalVO.setWeightOfReceptacles(new Measure(null,0));
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);
		
		
	}
	
	
	@Test
	public void findSystemParameterByCodesIsNull() throws SystemException, FinderException, BusinessException   {
		
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(null).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	

		Collection<ProductValidationVO> productVOs = new ArrayList<>();
		ProductVO productVO = new ProductVO();
		ProductValidationVO productValidationVO = new ProductValidationVO();
		productValidationVO.setProductCode("MAL");
		productVOs.add(productValidationVO);
		productVO.setDocumentSubType("MAL");
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		CustomerFilterVO customerFilterVO = new CustomerFilterVO();
		customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
		customerFilterVO.setCustomerCode(AGENTCODE);
		customerFilterVO.setPageNumber(1);
		
		List<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		List<CustomerLovVO> customers = new ArrayList<>();
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		spy.invoke(carditVO);	
	}
	
	@Test
	public void throwExeptionwhileFindCustomers() throws SystemException, FinderException, BusinessException {

		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getShipperCode());
		doReturn(AGENTCODE).when(mAilcontroller).findAgentFromUpucode(carditVO.getCompanyCode(), carditVO.getCarditPawbDetailsVO().getConsigneeCode());
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		
		Collection<CustomerLovVO> agents = new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		doThrow(ProxyException.class).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		
		spy.invoke(carditVO);
		
		
	}
	@Test(expected = MailTrackingBusinessException.class)
	public void saveShipmentDetails_throwSystemException() throws SystemException, FinderException, BusinessException {
		
		
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("CDG");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange(DESTN);
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
		OfficeOfExchange officeOfExchange1=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DXB");
		OfficeOfExchangePK officeOfExchangePk1=new OfficeOfExchangePK();
		officeOfExchangePk1.setCompanyCode("IBS");
		officeOfExchangePk1.setOfficeOfExchange(ORIGIN);
		officeOfExchange1.setOfficeOfExchangePK(officeOfExchangePk1);
		doReturn(officeOfExchange1).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		
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
		String product=systemParameterMap.get(PRODUCT_CODE_PARAMETER);
		doReturn(productVOs).when(productDefaultsProxy).findProductsByName(carditVO.getCompanyCode(),product);
		doReturn(productVO).when(productDefaultsProxy).findProductDetails(carditVO.getCompanyCode(),productValidationVO.getProductCode());
		
		
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode("");
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(operationsShipmentProxy).saveShipmentDetails(any(ShipmentDetailVO.class));
		spy.invoke(carditVO);
		
		
	}
	@Test
	public void getShipmentDetailVO_WithMailBagInCons() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		CarditReceptacleVO carditReceptacleVO1=new CarditReceptacleVO();
		carditReceptacleVO1.setCarditKey("123");
		carditReceptacleVO1.setMasterDocumentNumber(null);
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		receptacleInformation.add(carditReceptacleVO1);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110012");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsignmentVO.setContractIDNumber("1");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void updateShipmentDetails_WithAwbMailBags() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		CarditReceptacleVO carditReceptacleVO1=new CarditReceptacleVO();
		carditReceptacleVO1.setCarditKey("124");
		carditReceptacleVO1.setMasterDocumentNumber("1335679");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		receptacleInformation.add(carditReceptacleVO1);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<MailbagVO>mailbags = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setWeight( new Measure(UnitConstants.MAIL_WGT, 5));
		mailbagVO.setMailbagId("FRCDGADEFRAAACA11001101110011");
		mailbags.add(mailbagVO);
		doReturn(mailbags).when(mAilcontroller).findAWBAttachedMailbags(any(MailbagVO.class),any(String.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110012");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("12345678");
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.WEIGHT, 2.1));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithMailBag() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber(MSTDOCNUM);
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus(ShipmentDetailVO.AWB_EXECUTED);
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("12345677");
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.WEIGHT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void updateShipmentDetails_WithAwbMailBags_withUpdateShipment() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO1=new CarditReceptacleVO();
		carditReceptacleVO1.setCarditKey("124");
		carditReceptacleVO1.setMasterDocumentNumber("1335679");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
 		receptacleInformation.add(carditReceptacleVO1);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<MailbagVO>mailbags = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setWeight( new Measure(UnitConstants.WEIGHT, 5));
		mailbags.add(mailbagVO);
		doReturn(mailbags).when(mAilcontroller).findAWBAttachedMailbags(any(MailbagVO.class),any(String.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110012");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		spy.invoke(carditVO);
	}
	
	@Test
	public void getShipmentDetailVO_WithExistingMailBagInConsignment() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("123456");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithExistingMailBagOfSameConsignment() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithNewMailBags() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithEmptyMasterDocnum() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithEmptyExistingMailbagInconsignment() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithExistingAndNewMailBags() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber(MSTDOCNUM);
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		CarditReceptacleVO carditReceptacleVO1=new CarditReceptacleVO();
		carditReceptacleVO1.setCarditKey("123");
		receptacleInformation.add(carditReceptacleVO1);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		spy.invoke(carditVO);
	}
	@Test
	public void carditTypeAsCancelWithNewMailbag() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
	}
	@Test
	public void carditTypeAsCancelWithEmptyMailInconsignment() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
	}
	@Test
	public void carditTypeAsCancelWithAwbExecuted() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		shipmentDetailVOToSave.setShipmentStatus("E");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
	}
	@Test
	public void carditTypeAsCancelWithoutMailInConsg() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
	}
	@Test
	public void updateShipmentDetails_WithAwbInExecutedStatus() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO1=new CarditReceptacleVO();
		carditReceptacleVO1.setCarditKey("124");
		carditReceptacleVO1.setMasterDocumentNumber("1335679");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
 		receptacleInformation.add(carditReceptacleVO1);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);
		shipmentDetailVOToSave.setShipmentStatus(ShipmentDetailVO.AWB_EXECUTED);
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<MailbagVO>mailbags = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setWeight( new Measure(UnitConstants.WEIGHT, 5));
		mailbags.add(mailbagVO);
		doReturn(mailbags).when(mAilcontroller).findAWBAttachedMailbags(any(MailbagVO.class),any(String.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110012");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		spy.invoke(carditVO);
	}
	@Test
	public void getShipmentDetailVO_WithDifferentMasterDocnum() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	@Test
	public void carditTypeAsCancelWithAwbNotExecuted() throws BusinessException, SystemException {
		carditVO.setCarditType("1");
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber(MSTDOCNUM);
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.MAIL_WGT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(existingDoc);
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber("1335678");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		spy.invoke(carditVO);
	}
	
	@Test
	public void getScreeningTest() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	
	@Test
	public void getScreeningApplicableRegulationDirectionNullTest() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection(null);
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection(null);
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection(null);
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getScreeningApplicableRegulationReferenceIDNullTest() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID(null);
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID(null);
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID(null);
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getScreeningReasonCodeNullTest() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode(null);
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 consignmentScreeningVos.add(consignmentScreening);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	@Test
	public void carditTypeWhenShipmentDetailsVONull() throws BusinessException, SystemException {
		shipmentDetailVOToSave = null;
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		carditReceptacleVO.setMasterDocumentNumber(MSTDOCNUM);
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Collection<MailInConsignmentVO> mailInConsigmentVOs = 
				new ArrayList<MailInConsignmentVO>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("12345677");
		mailInConsignmentVO.setMailStatus("ASG");
		mailInConsignmentVO.setStatedWeight(new Measure(UnitConstants.WEIGHT, 5));
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getScreeningWhenApplicableRegBorderAgencyAuthorityAndApplicableRegReferenceIDNullTest() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority(null);
		  consignmentForApplicableRegulation.setApplicableRegReferenceID(null);
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority(null);
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID(null);
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority(null);
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID(null);
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getShipmentDetailsVOWhenSourceIndicatorACP() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getShipmentDetailsVOWhenSourceIndicatorACPAndAWBExistsForConsignmentTrue() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	@Test
	public void getShipmentDetailsVOWhenSourceIndicatorNotACP() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("2");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getAWBDetailsWhenCarditPAWBTotalsInformationAndTotalsInformationIsEmpty() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		//Collection<CarditTotalVO> totalsInformation1=new ArrayList<>();
		//carditVO.getCarditPawbDetailsVO().setTotalsInformation(totalsInformation1);
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();
		carditVO.setTotalsInformation(totalsInformation);
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setAgentCode("MAILAGENT");
		carditPawbDetailsVO.setConsigneeAgentCode("MAILAGENT");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	
	@Test
	public void getAWBDetailsWhensetContractIDNumberNotNull() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		Collection<CarditTotalVO> totalsInformation1=new ArrayList<>();
		CarditTotalVO carditTotalVO1=new CarditTotalVO();
		carditTotalVO1.setNumberOfReceptacles("1");
		carditTotalVO1.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation1.add(carditTotalVO1);
		//carditVO.getCarditPawbDetailsVO().setTotalsInformation(totalsInformation1);
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();;
		CarditTotalVO carditTotalVO=new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		carditTotalVO.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setAgentCode("MAILAGENT");
		carditPawbDetailsVO.setConsigneeAgentCode("MAILAGENT");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setContractIDNumber("8");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	@Test
	public void getAWBDetailsWhenMailInConsignmentVosNull() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		carditReceptacleVO.setCarditKey("123");
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		Collection<CarditTotalVO> totalsInformation1=new ArrayList<>();
		CarditTotalVO carditTotalVO1=new CarditTotalVO();
		carditTotalVO1.setNumberOfReceptacles("1");
		carditTotalVO1.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation1.add(carditTotalVO1);
		//carditVO.getCarditPawbDetailsVO().setTotalsInformation(totalsInformation1);
		Collection<CarditTotalVO> totalsInformation=new ArrayList<>();;
		CarditTotalVO carditTotalVO=new CarditTotalVO();
		carditTotalVO.setNumberOfReceptacles("1");
		carditTotalVO.setWeightOfReceptacles(new Measure(UnitConstants.WEIGHT, 17.4));
		totalsInformation.add(carditTotalVO);
		carditVO.setTotalsInformation(totalsInformation);
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		carditPawbDetailsVO.setAgentCode("MAILAGENT");
		carditPawbDetailsVO.setConsigneeAgentCode("MAILAGENT");
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = null;
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setContractIDNumber("8");
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getShipmentDetailsVOWhenSourceIndicatorACPAndAWBExistsForConsignmentTrueAndOperationFlagNotUpdate() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		shipmentDetailVOToSave.setOperationFlag("I");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	@Test
	public void getContractIdnumberwhenMailInconsignmentVosNull() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		shipmentDetailVOToSave.setOperationFlag("I");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = null;
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().setMailInConsignmentVOs(mailInConsigmentVOs);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
	
	
	
	@Test
	public void getContractIdnumberwhenMailInconsignmentVosEmpty() throws SystemException, FinderException, BusinessException {
		CarditReceptacleVO carditReceptacleVO=new CarditReceptacleVO();
		Collection<CarditReceptacleVO> receptacleInformation=new ArrayList<>();
		receptacleInformation.add(carditReceptacleVO);
		carditVO.setReceptacleInformation(receptacleInformation);
		carditVO.getCarditPawbDetailsVO().setSourceIndicator("ACP");
		carditVO.getCarditPawbDetailsVO().setAwbExistsForConsignment(true);
		AirlineValidationVO airlineValidationVO=new AirlineValidationVO();
		airlineValidationVO.setAirlineIdentifier(1154);
		doReturn(airlineValidationVO).when(operationsShipmentProxy).validateNumericCode(carditVO.getCompanyCode(),carditVO.getCarditPawbDetailsVO().getShipmentPrefix());
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(PRODUCT_CODE_PARAMETER);
		HashMap<String, String> systemParameterMap =new HashMap<>();
		systemParameterMap.put(PRODUCT_CODE_PARAMETER, "MAL");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameters);	
		shipmentDetailVOToSave.setShipmentStatus("N");
		shipmentDetailVOToSave.setOperationFlag("I");
		doReturn(shipmentDetailVOToSave).when(operationsShipmentProxy).findShipmentDetails(any(ShipmentDetailFilterVO.class));
		Collection<CustomerLovVO> agents = new ArrayList<>();
		Collection<CustomerLovVO> customers =  new ArrayList<>();
		CustomerLovVO agentLov=new CustomerLovVO();
		agentLov.setCustomerCode(AGENTCODE);
		agentLov.setCustomerName(MAILAGENT);
		agentLov.setIataCode(IATACODE);
		agentLov.setAddress1(ADDRESS);
		agentLov.setCity("SIN");
		agentLov.setState("SIN");
		agentLov.setCountry("SG");
		agents.add(agentLov);
		CustomerLovVO consigneeLov=new CustomerLovVO();
		consigneeLov.setCustomerCode(AGENTCODE);
		consigneeLov.setCustomerName(MAILAGENT);
		consigneeLov.setAddress1(ADDRESS);
		consigneeLov.setCity("SIN");
		consigneeLov.setState("SIN");
		consigneeLov.setCountry("SG");
		customers.add(consigneeLov);
		doReturn(agents).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
		doReturn(customers).when(sharedCustomerProxy).findCustomers(any(CustomerFilterVO.class));
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
		commodityValidationVo.setSccCode("MAL");
		commodityMap.put("MAL", commodityValidationVo);
		doReturn(commodityMap).when(sharedCommodityProxy).validateCommodityCodes(carditVO.getCompanyCode(),commodityColl);	
		ConsignmentDocumentVO consignmentDocumentVO = setConsignmentDocumentVO();
		carditVO.getCarditPawbDetailsVO().setConsignmentDocumentVO(consignmentDocumentVO);
		Page<MailInConsignmentVO> mailInConsigmentVOs = new Page<>(
				new ArrayList<MailInConsignmentVO>(), 0, 0, 0, 0, 0, false);
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setCompanyCode("AV");
		mailInConsignmentVO.setMailId("FRCDGADEFRAAACA11001101110011");
		mailInConsignmentVO.setMailSequenceNumber(1);
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(1134);
		mailInConsignmentVO.setMailDuplicateNumber(0);
		mailInConsignmentVO.setMasterDocumentNumber("54321");
		mailInConsignmentVO.setMailStatus("NEW");
		mailInConsignmentVO.setContractIDNumber(null);
		mailInConsigmentVOs.add(mailInConsignmentVO);
		ConsignmentDocumentVO existingDoc = new ConsignmentDocumentVO();
		existingDoc.setMailInConsignmentcollVOs(mailInConsigmentVOs);
		existingDoc.setMailInConsignmentVOs(mailInConsigmentVOs);
		carditVO.getCarditPawbDetailsVO().setExistingMailBagsInConsignment(existingDoc);
		carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().setMailInConsignmentVOs(mailInConsigmentVOs);
		
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = new ArrayList<>();
			 ConsignmentScreeningVO consignmentScreening = new ConsignmentScreeningVO();
			 consignmentScreening.setSecurityReasonCode("CS");
			 consignmentScreening.setScreeningMethodCode("RA");
			 consignmentScreening.setIsoCountryCode("BE");
			 consignmentScreening.setAgentID("00010-01");
			 consignmentScreening.setExpiryDate("0812");
		 
		 ConsignmentScreeningVO consignmentScreeningForAccountConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForAccountConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForAccountConsignor.setScreeningMethodCode("AC");
			 consignmentScreeningForAccountConsignor.setAgentID("SAMPLE");

		 ConsignmentScreeningVO consignmentScreeningForKnownConsignor = new ConsignmentScreeningVO();
			 consignmentScreeningForKnownConsignor.setSecurityReasonCode("CS");
			 consignmentScreeningForKnownConsignor.setScreeningMethodCode("KC");
			 consignmentScreeningForKnownConsignor.setAgentID("SAMPLE");
			 
		 ConsignmentScreeningVO consignmentScreeningMethod = new ConsignmentScreeningVO();
		  consignmentScreeningMethod.setSecurityReasonCode("SM");
		  consignmentScreeningMethod.setScreeningMethodCode("FRD");
		
		  ConsignmentScreeningVO consignmentScreeningExcemption = new ConsignmentScreeningVO();
		  consignmentScreeningExcemption.setSecurityReasonCode("SE");
		  consignmentScreeningExcemption.setScreeningMethodCode("MAIL");
		  
		  ConsignmentScreeningVO consignmentForAdditionalSecurityInfo = new ConsignmentScreeningVO();
		  consignmentForAdditionalSecurityInfo.setSecurityReasonCode("CS");
		  consignmentForAdditionalSecurityInfo.setAdditionalSecurityInfo("TEST STAT");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulation = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulation.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulation.setApplicableRegTransportDirection("1");
		  consignmentForApplicableRegulation.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulation.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationExport = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationExport.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationExport.setApplicableRegTransportDirection("2");
		  consignmentForApplicableRegulationExport.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationExport.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		  
		  ConsignmentScreeningVO consignmentForApplicableRegulationTransit = new ConsignmentScreeningVO();
		  consignmentForApplicableRegulationTransit.setSecurityReasonCode("AR");
		  consignmentForApplicableRegulationTransit.setApplicableRegTransportDirection("3");
		  consignmentForApplicableRegulationTransit.setApplicableRegBorderAgencyAuthority("CUS");
		  consignmentForApplicableRegulationTransit.setApplicableRegReferenceID("TRA-FR-19930702-EU2454/93/30A");
		 
		 consignmentScreeningVos.add(consignmentScreening);
		 consignmentScreeningVos.add(consignmentScreeningForAccountConsignor);
		 consignmentScreeningVos.add(consignmentScreeningForKnownConsignor);
		 consignmentScreeningVos.add(consignmentScreeningMethod);
		 consignmentScreeningVos.add(consignmentScreeningExcemption);
		 consignmentScreeningVos.add(consignmentForAdditionalSecurityInfo);
		 consignmentScreeningVos.add(consignmentForApplicableRegulation);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationExport);
		 consignmentScreeningVos.add(consignmentForApplicableRegulationTransit);
		 carditVO.setConsignementScreeningVOs(consignmentScreeningVos);
		doReturn(shipmentValidationVO).when(operationsShipmentProxy).saveShipmentDetails(shipmentDetailVOToSave);
		spy.invoke(carditVO);
	}
}