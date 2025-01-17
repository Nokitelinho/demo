package com.ibsplc.icargo.business.mail.operations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.cache.PostalAdministrationCache;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditVO;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentRoutingVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailResditAddressVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.PostalAdministrationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ResditMessageVO;

import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;

import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;





import static org.mockito.Matchers.any;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
public class ResditTest extends AbstractFeatureTest {
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String FR001 = "FR001";
	private static final String US22AJF02481 = "US22AJF02481";

    Resdit spy;
    Cardit cardit;
	Collection<ResditEventVO> resditEvents;
	ResditEventVO firstResditEvent;
	ResditMessageVO resditMessageVO;
	ResditEventVO resditEventVO;
	ConsignmentInformationVO consignInfoVO;
	CarditEnquiryFilterVO carditEnqFilterVO;
	Collection<ConsignmentRoutingVO> consignmentRoutingVOs;
	Map<String, String> connectionARLsMap;
	ArrayList<String> conArlList;
	LogonAttributes logonAttributes;
	private MailTrackingDefaultsDAO dao;
	MailConstantsVO mailConstantsVo;
	private KeyUtilInstance keyUtils;
	private KeyUtilInstance keyUtilsInstance;
	private PostalAdministrationCache postalAdministrationCache;
	private MailController controllerSpy;
	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new Resdit());
		cardit = spy(new Cardit());
		dao = mock(MailTrackingDefaultsDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
	resditEvents =  new ArrayList<>();
	firstResditEvent =  new ResditEventVO();
	resditMessageVO = new ResditMessageVO();
	resditEventVO = new ResditEventVO();
	consignInfoVO = new ConsignmentInformationVO();
	carditEnqFilterVO = new CarditEnquiryFilterVO();
	consignmentRoutingVOs = new ArrayList<>();
	conArlList = new ArrayList<>();
	logonAttributes = new LogonAttributes();
	KeyUtilInstanceMock.mockKeyUtilInstance();
	keyUtils = KeyUtilInstance.getInstance();
	keyUtilsInstance =  KeyUtilInstance.getInstance();
	mailConstantsVo =  new MailConstantsVO();
	 postalAdministrationCache = spy(PostalAdministrationCache.class);
 	doReturn(postalAdministrationCache).when(CacheFactory.getInstance()).getCache(PostalAdministrationCache.ENTITY_NAME);
	}

	@Test
	public void buildResditMessageVoTest() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("32");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		boolean flag=true;
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		spy.buildResditMessageVO(resditEvents);

		
	}
	
	@Test
	public void buildResditMessageVoPostalAdministrationVoDetailsParCodeCheckTest() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("0");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		boolean flag=true;
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWPARCODE");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		spy.buildResditMessageVO(resditEvents);

		
	}
	
	@Test
	public void buildResditMessageVoPostalAdministrationVoDetailsParamNoCheckTest() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode("FR002");
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID(null);
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		boolean flag=true;
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("NO");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		spy.buildResditMessageVO(resditEvents);

		
	}

	@Test
	public void buildResditMessageVoPostalAdministrationVoDetailsEmptyCheckTest() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode("FR002");
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		boolean flag=true;
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("NO");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(new HashMap<>());
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		spy.buildResditMessageVO(resditEvents);

		
	}

	@Test
	public void buildResditMessageVoTestForRdtAddAddresDetl() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("32");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<MailResditAddressVO> mailResditAddressVO =new ArrayList<>();
		doReturn(mailResditAddressVO).when(dao).findMailResditAddtnlAddressDetails(any(),any());
		spy.buildResditMessageVO(resditEvents);	
	}
	@Test
	public void buildResditMessageVoTestForRdtAddAddresDetlSuccess() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("32");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<MailResditAddressVO> mailResditAddressVOs =new ArrayList<>();
		MailResditAddressVO mailResditAddressVO=new MailResditAddressVO();
		mailResditAddressVO.setCompanyCode("AV");
		mailResditAddressVO.setMessageAddressSequenceNumber((long)32);
		mailResditAddressVOs.add(mailResditAddressVO);
		List<Long> adrss=new ArrayList<>();
		adrss.add((long)32);
		doReturn(mailResditAddressVOs).when(dao).findMailResditAddtnlAddressDetails(resditEventVO.getCompanyCode(),adrss);
		spy.buildResditMessageVO(resditEvents);	
	}
	@Test
	public void buildResditMessageVoTestForRdtAddAddresDetl_PersExceptn() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("32");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<MailResditAddressVO> mailResditAddressVOs =new ArrayList<>();
		MailResditAddressVO mailResditAddressVO=new MailResditAddressVO();
		mailResditAddressVO.setCompanyCode("AV");
		mailResditAddressVO.setMessageAddressSequenceNumber((long)32);
		mailResditAddressVOs.add(mailResditAddressVO);
		List<Long> adrss=new ArrayList<>();
		adrss.add((long)32);
		//doReturn(mailResditAddressVOs).when(dao).findMailResditAddtnlAddressDetails(resditEventVO.getCompanyCode(),adrss);
		doThrow(PersistenceException.class).when(dao).findMailResditAddtnlAddressDetails(any(),any());
		spy.buildResditMessageVO(resditEvents);	
	}
	@Test
	public void buildResditMessageVoTestForRdtAddAddresDetl_SysmExceptn() throws SystemException, PersistenceException{
		CarditVO carditVO =  new CarditVO(); 
		resditMessageVO.setMessageType("IFTSTA1.1");
		resditMessageVO.setMessageStandard("EDIFACT");
		resditMessageVO.setRecipientID(FR001);
		resditMessageVO.setPreparationDate("220406");
		resditMessageVO.setPreparationTime("1230");
		resditMessageVO.setCompanyCode("AV");
		resditMessageVO.setLocNeeded(true);
		resditMessageVO.setTestIndicator("1");
		resditMessageVO.setResditFileName("HHJS");
	    resditEventVO.setResditVersion("1.1");
		resditEventVO.setCompanyCode("AV");
		resditEventVO.setEventPort("CDG");
		resditEventVO.setPaCode(FR001);
		resditEventVO.setCarditExist("true");
		resditEventVO.setMsgEventLocationEnabled(true);
		resditEventVO.setPartialResdit(true);
		resditEventVO.setActualResditEvent("XX");
		resditEventVO.setConsignmentNumber(US22AJF02481);
		resditEventVO.setShipmentPrefix("134");
		resditEventVO.setMasterDocumentNumber("65566546");
		resditEventVO.setResditEventCode("XY");
		resditEventVO.setEventPortName("CDG");
		resditEventVO.setAdditionlAddressID("32");
		resditEvents.add(resditEventVO);
		consignInfoVO.setConsignmentPAWBNo("134-65566546");
		String companyCode ="AV";
		String consignmentId ="1";
		resditMessageVO.setResditEventVOs(resditEvents);
		Cardit.findCarditDetailsForResdit(companyCode, consignmentId);
		verify(dao, times(1)).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setCompanyCode("AV");
		carditVO.setConsignmentNumber(US22AJF02481);
		doReturn(carditVO).when(dao).findCarditDetailsForResdit(any(String.class), any(String.class));
		carditVO.setTstIndicator(1);
		resditMessageVO.setTestIndicator("1");		
		doReturn("1").when(keyUtils).getKey(any(Criterion.class));
		doReturn("2").when(keyUtilsInstance).getKey(any(Criterion.class));
		resditMessageVO.setInterchangeControlReference("1");
		resditMessageVO.setMessageReferenceNumber("2");
		carditEnqFilterVO.setCompanyCode("AV");
		carditEnqFilterVO.setPaoCode(FR001);
		carditEnqFilterVO.setConsignmentDocument(US22AJF02481);
		PostalAdministrationVO postalAdministrationVO = new PostalAdministrationVO();
		HashMap<String, Collection<PostalAdministrationDetailsVO>> postalAdministrationDetailsVOs=new HashMap<>();
		Collection<PostalAdministrationDetailsVO> paDetails=new ArrayList<>();
		PostalAdministrationDetailsVO paDet=new PostalAdministrationDetailsVO();
		paDet.setParCode("PAWBASSCONENAB");
		paDet.setParameterValue("YES");
		paDetails.add(paDet);
		postalAdministrationDetailsVOs.put("INVINFO", paDetails);
		postalAdministrationVO.setPostalAdministrationDetailsVOs(postalAdministrationDetailsVOs);
		doReturn(postalAdministrationVO).when(postalAdministrationCache).findPACode(resditEventVO.getCompanyCode(), resditEventVO.getPaCode());
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		Collection<MailResditAddressVO> mailResditAddressVOs =new ArrayList<>();
		MailResditAddressVO mailResditAddressVO=new MailResditAddressVO();
		mailResditAddressVO.setCompanyCode("AV");
		mailResditAddressVO.setMessageAddressSequenceNumber((long)32);
		mailResditAddressVOs.add(mailResditAddressVO);
		List<Long> adrss=new ArrayList<>();
		adrss.add((long)32);
		//doReturn(mailResditAddressVOs).when(dao).findMailResditAddtnlAddressDetails(resditEventVO.getCompanyCode(),adrss);
		SystemException se = new SystemException(SystemException.DATABASE_UNAVAILABLE);
		doThrow(se).when(dao).findMailResditAddtnlAddressDetails(any(),any());
		spy.buildResditMessageVO(resditEvents);	
	}

}
