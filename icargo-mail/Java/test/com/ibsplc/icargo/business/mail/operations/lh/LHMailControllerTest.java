package com.ibsplc.icargo.business.mail.operations.lh;


import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchange;
import com.ibsplc.icargo.business.mail.operations.OfficeOfExchangePK;
import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedGeneralMasterGroupingProxy;
import com.ibsplc.icargo.business.mail.operations.vo.AWBDetailVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditPawbDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditTotalVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentDocumentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailInConsignmentVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.icargo.business.shared.area.airport.vo.AirportValidationVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupDetailsVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupFilterVO;
import com.ibsplc.icargo.business.shared.generalmastergrouping.vo.GeneralMasterGroupVO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import static org.mockito.Matchers.eq;
public class LHMailControllerTest extends AbstractFeatureTest{

	private LHMailController lhMailControllerSpy;
	private MailTrackingDefaultsDAO dao;
	private Collection<MailbagVO> mailBagVOs;
	private MailbagVO mailbagVO;
	private LogonAttributes logonAttributes;
	private MailController mailController;
	private SaveSecurityDetailsFeature saveSecurityDetailsFeature;
	SavePAWBDetailsFeature savePAWBDetailsFeature;
	private AdminUserProxy adminUserProxy;
	private Mailbag mailBag;
	private Mailbag mailbagBean;
	private Collection<CarditPawbDetailsVO>	carditPawbDetailsVOs;
	private Collection<RoutingInConsignmentVO> consignmentRoutingVOs;
	private Collection<MailInConsignmentVO> mailInConsignmentVOs;
	private Collection<CarditTotalVO> totalsInformation;
	private static final String MAIL_OPERATIONS = "mail.operations";
	private PostalAdministrationCache postalAdministrationCache;
	private SharedGeneralMasterGroupingProxy sharedGeneralMasterGroupingProxy;
	private SharedAreaProxy sharedAreaProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private AWBDetailVO aWBDetailVO;
	 private KeyUtilInstance keyUtils;
	private KeyUtilInstance keyUtilsInstance;
	 @Override
	public void setup() throws Exception {
		
		lhMailControllerSpy = spy(new LHMailController());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		logonAttributes = mock(LogonAttributes.class);
		mailController = mock(MailController.class);
		saveSecurityDetailsFeature= mockBean
				(SaveSecurityDetailsFeatureConstants.SAVE_SECURITY_DETAILS_FEATURE,SaveSecurityDetailsFeature.class);
		savePAWBDetailsFeature= mockBean(SavePAWBDetailsFeatureConstants.SAVE_PAWB_FEATURE,SavePAWBDetailsFeature.class);
		mailbagVO = new MailbagVO();
		mailBagVOs = new ArrayList<>();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA24421112001890");
		mailBagVOs.add(mailbagVO);
		adminUserProxy = mockProxy(AdminUserProxy.class);
		mailBag = mock(Mailbag.class);
		mailbagBean = mockBean("MailbagEntity", Mailbag.class);
		totalsInformation =  new ArrayList<>();
		carditPawbDetailsVOs = new ArrayList<>();
		postalAdministrationCache = spy(PostalAdministrationCache.class);
    	doReturn(postalAdministrationCache).when(CacheFactory.getInstance()).getCache(PostalAdministrationCache.ENTITY_NAME);
    	sharedGeneralMasterGroupingProxy = mockProxy(SharedGeneralMasterGroupingProxy.class);
    	sharedAreaProxy = mockProxy(SharedAreaProxy.class);
    	sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
    	consignmentRoutingVOs = new ArrayList<>();
    	 KeyUtilInstanceMock.mockKeyUtilInstance();
    		keyUtils = KeyUtilInstance.getInstance();
    		keyUtilsInstance = KeyUtilInstance.getInstance();
    		mailInConsignmentVOs = new ArrayList<>();
    		
	}
	
	
	@Test
	public void shouldInvokeDaoWhenCreatePAWBForConsignmentIsInvoked()
			throws SystemException, PersistenceException {
		int noOfDays = 7;
		carditPawbDetailsVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		verify(dao, times(1)).findMailbagsForPAWBCreation(noOfDays);
	}
	
	@Test
	public void shouldCreatePAWBForConsignmentWhenCountryValidationTrueTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		doReturn("1345671").when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	
	@Test
	public void shouldCreatePAWBForConsignmentWhenCountryValidationTrueAndAWBDetailNotNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
		awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVO.setMasterDocumentNumber("12345678");
		mailInConsignmentVO.setShipmentPrefix("020");
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(any(CarditVO.class));
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	
	@Test
	public void shouldCreatePAWBForConsignmentWhenCountryValidationFalseTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
		awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVO.setMasterDocumentNumber("12345678");
		mailInConsignmentVO.setShipmentPrefix("020");
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		GeneralMasterGroupDetailsVO detailsVO1 = new GeneralMasterGroupDetailsVO();
		detailsVO1.setGroupedEntity("US");
		detailVOs.add(detailsVO1);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(any(CarditVO.class));
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	
	@Test
	public void shouldCreatePAWBForConsignmentWhenAWBDetailMasterDocNumNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
		awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber(null);
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		Collection<MailInConsignmentVO> mailInConsignmentVOs = new ArrayList<>();
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVO.setMasterDocumentNumber("12345678");
		mailInConsignmentVO.setShipmentPrefix("020");
		mailInConsignmentVO.setCompanyCode("IBS");
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(any(CarditVO.class));
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	
	@Test
	public void shouldNotCratePAWBEndValueWhenSystemParamNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		doReturn("1345671").when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	@Test
	public void shouldNotCratePAWBEndValueWhenSystemParamRangeNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(null,null);
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn("1345671").when(keyUtils).getKey(any(Criterion.class));
		doReturn("1345671").when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	@Test
	public void shouldNotCreatePAWBRangeWhenKeyNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn(null).when(keyUtils).getKey(any(Criterion.class));
		doReturn(null).when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	@Test
	public void shouldNotCreatePAWBRangeWhenKeyLengthLessThanZeroTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn("").when(keyUtils).getKey(any(Criterion.class));
		doReturn("").when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	@Test
	public void shouldNotCreatePAWBRangeWhenKeyGreaterThanRangeTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		int noOfDays = 7;
		logonAttributes.setCompanyCode("IBS");
		HashMap<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put("mailtracking.defaults.pawbendrange", "9345678");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		AWBDetailVO awbDetailVO = new AWBDetailVO();
		awbDetailVO.setCompanyCode("IBS");
	   awbDetailVO.setOwnerId(134);
		awbDetailVO.setMasterDocumentNumber("12345678");
		awbDetailVO.setDuplicateNumber(1);
		awbDetailVO.setSequenceNumber(1);
		
		awbDetailVO.setOrigin("DEL");
		awbDetailVO.setDestination("FRA");
		awbDetailVO.setOwnerCode("DEL");
		
		CarditVO carditVO = new CarditVO();
		carditVO.setSenderId("INA");
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(carditPawbDetailsVOs).when(dao).findMailbagsForPAWBCreation(noOfDays);
		doReturn(aWBDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(carditVO.getCompanyCode(), carditVO.getSenderId());
		OfficeOfExchange officeOfExchange=new OfficeOfExchange();
		officeOfExchange.setAirportCode("DEL");
		OfficeOfExchangePK officeOfExchangePk=new OfficeOfExchangePK();
		officeOfExchangePk.setCompanyCode("IBS");
		officeOfExchangePk.setOfficeOfExchange("DEFRAA");
		officeOfExchange.setOfficeOfExchangePK(officeOfExchangePk);
		doReturn(officeOfExchange).when(PersistenceController.getEntityManager()).find(eq(OfficeOfExchange.class),
				any(OfficeOfExchangePK.class));
		MailInConsignmentVO mailInConsignmentVO = new MailInConsignmentVO();
		mailInConsignmentVO.setMailBagDocumentOwnerIdr(134);
		mailInConsignmentVO.setMailDuplicateNumber(1);
		mailInConsignmentVO.setSequenceNumberOfMailbag(1);
		mailInConsignmentVOs.add(mailInConsignmentVO);
		RoutingInConsignmentVO consignmentRoutingVO = new RoutingInConsignmentVO();
		consignmentRoutingVO.setCompanyCode("IBS");
		consignmentRoutingVO
				.setConsignmentNumber("PAWBTESTAPRSTORY1");
		consignmentRoutingVO.setPaCode("INA");
		consignmentRoutingVO
				.setConsignmentSequenceNumber(1);
		consignmentRoutingVO.setRoutingSerialNumber(1);
		consignmentRoutingVO.setPol("DEL");
		consignmentRoutingVO.setPou("FRA");
		consignmentRoutingVO.setOnwardFlightNumber("0543");
		consignmentRoutingVO.setOnwardCarrierCode("IBS");
		consignmentRoutingVO.setOnwardCarrierId(183);
		
		consignmentRoutingVO.setOnwardFlightDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		
		consignmentRoutingVO.setOnwardCarrierSeqNum(0);
		consignmentRoutingVOs.add(consignmentRoutingVO);
		ConsignmentDocumentVO consignmentDocumentVO = new ConsignmentDocumentVO();
		consignmentDocumentVO.setDestinationUpuCode("DEFRAA");
		 consignmentDocumentVO.setOriginUpuCode("INDELA");
		 consignmentDocumentVO.setShipperUpuCode("INA");
		 consignmentDocumentVO.setConsigneeUpuCode("DEA");
		 consignmentDocumentVO.setCompanyCode("IBS");
		 consignmentDocumentVO.setConsignmentNumber("PAWBTESTAPRSTORY1");
		 consignmentDocumentVO.setConsignmentSequenceNumber(1);
		 consignmentDocumentVO.setPaCode("INA");
		 consignmentDocumentVO.setAirportCode("DEL");
		 consignmentDocumentVO.setOperation("O");
		 consignmentDocumentVO.setRoutingInConsignmentVOs(consignmentRoutingVOs);
		CarditTotalVO totalInformation = new CarditTotalVO();
		CarditPawbDetailsVO carditPawbDetailsVO = new CarditPawbDetailsVO();
		
		carditPawbDetailsVO.setConsigneeCode("DEA");
		carditPawbDetailsVO.setShipmentPrefix("020");
		carditPawbDetailsVO.setMasterDocumentNumber("12345678");
		carditPawbDetailsVO.setConsignmentDestination("DEFRAA");
		carditPawbDetailsVO.setConsignmentOrigin("INDELA");
		carditPawbDetailsVO.setShipperCode("DEA");
		carditPawbDetailsVO.setConsignmentOriginAirport("DEL");
		carditPawbDetailsVO.setConsignmentDestinationAirport("FRA");
		carditPawbDetailsVO.setSourceIndicator("ACP");
		carditPawbDetailsVO.setConsignmentDocumentVO(consignmentDocumentVO);
		carditPawbDetailsVO.setMailInConsignmentVOs(mailInConsignmentVOs);
		carditVO.setCarditType("8");
		carditVO.setCompanyCode("IBS");
		carditVO.setConsignmentNumber("K4IRDPEFGIUA");
		carditVO.setSenderId("INA");
		carditVO.setStationCode("DEL");
		
		totalInformation.setMailClassCode("U");
		totalInformation.setWeightOfReceptacles(new Measure(null, 17.4));
		totalInformation.setNumberOfReceptacles("2");
		totalsInformation.add(totalInformation);
		//carditPawbDetailsVO.setTotalsInformation(totalsInformation);
		carditPawbDetailsVOs.add(carditPawbDetailsVO);
		carditVO.setCarditPawbDetailsVO(carditPawbDetailsVO);
		Map<String, AirportValidationVO> countryCodeMap = new HashMap<String, AirportValidationVO>();
		AirportValidationVO airportValidationVO = new AirportValidationVO();
		airportValidationVO.setCountryCode("US");
		airportValidationVO.setCityCode("DEL");
		AirportValidationVO airportValidationVos = new AirportValidationVO();
		airportValidationVos.setCountryCode("DE");
		airportValidationVos.setCityCode("FRA");
		countryCodeMap.put("DEL", airportValidationVO);
		countryCodeMap.put("FRA", airportValidationVos);
		GeneralMasterGroupVO groupVO = new GeneralMasterGroupVO();
		groupVO.setCompanyCode("IBS");
		GeneralMasterGroupDetailsVO detailsVO = new GeneralMasterGroupDetailsVO();
		detailsVO.setGroupedEntity("DE");
		ArrayList<GeneralMasterGroupDetailsVO> detailVOs = new ArrayList();
		detailVOs.add(detailsVO);
		groupVO.setGroupDetailsVOs(detailVOs);
		doReturn(groupVO).when(sharedGeneralMasterGroupingProxy).listGeneralMasterGroup(any(GeneralMasterGroupFilterVO.class));
		doReturn(countryCodeMap).when(sharedAreaProxy).validateAirportCodes(any(String.class),
				anyCollectionOf(String.class));
		mailController.findFieldsOfCarditPawb(carditVO);
		doReturn(true).when(mailController).findPawbCountryValidation(carditVO,carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO());
		doReturn(awbDetailVO).when(dao).findMstDocNumForAWBDetails(carditVO);
		doReturn("9345679").when(keyUtils).getKey(any(Criterion.class));
		doReturn("9345679").when(keyUtilsInstance).getKey(any(Criterion.class));
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		
	}
	
	
	
	@Test(expected = SystemException.class)
	public void shouldInvokeDaoWhenCreatePAWBForConsignmentIsInvokedExceptionTest()
			throws SystemException, PersistenceException {
		int noOfDays = 7;
		carditPawbDetailsVOs = new ArrayList<>();
		PersistenceException persistenceException = new PersistenceException();
		persistenceException.setErrorCode("Error");
		EntityManagerMock.mockEntityManager();
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(PersistenceException.class).when(dao).findMailbagsForPAWBCreation(noOfDays);
		lhMailControllerSpy.createPAWBForConsignment(noOfDays);
		verify(dao, times(1)).findMailbagsForPAWBCreation(noOfDays);
	}
	
}