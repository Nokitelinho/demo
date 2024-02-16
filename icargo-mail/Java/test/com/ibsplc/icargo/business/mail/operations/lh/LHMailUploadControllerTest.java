package com.ibsplc.icargo.business.mail.operations.lh;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.admin.user.vo.UserParameterVO;
import com.ibsplc.icargo.business.admin.user.vo.UserVO;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeature;
import com.ibsplc.icargo.business.mail.operations.feature.savesecuritydetails.SaveSecurityDetailsFeatureConstants;
import com.ibsplc.icargo.business.mail.operations.proxy.AdminUserProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ConsignmentScreeningVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.DSNVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import static org.mockito.Matchers.eq;
public class LHMailUploadControllerTest extends AbstractFeatureTest{

	private LHMailUploadController lhMailUploadControllerSpy;
	private ScannedMailDetailsVO scannedMailDetailsVO;
	private ConsignmentScreeningVO consignmentScreeningVO;
	private MailTrackingDefaultsDAO dao;
	private Collection<MailbagVO> mailBagVOs;
	private MailbagVO mailbagVO;
	private LogonAttributes logonAttributes;
	private MailController mailController;
	private SaveSecurityDetailsFeature saveSecurityDetailsFeature;
	private AdminUserProxy adminUserProxy;
	private  Collection<UserParameterVO> userParameterVOs;
	private Mailbag mailBag;
	private MailbagPK mailbagPK;
	private Mailbag mailbagBean;
	
	@Override
	public void setup() throws Exception {
		
		lhMailUploadControllerSpy = spy(new LHMailUploadController());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
		logonAttributes = mock(LogonAttributes.class);
		mailController = mock(MailController.class);
		saveSecurityDetailsFeature= mockBean
				(SaveSecurityDetailsFeatureConstants.SAVE_SECURITY_DETAILS_FEATURE,SaveSecurityDetailsFeature.class);
		mailbagVO = new MailbagVO();
		mailBagVOs = new ArrayList<MailbagVO>();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA24421112001890");
		mailBagVOs.add(mailbagVO);
		adminUserProxy = mockProxy(AdminUserProxy.class);
		mailBag = mock(Mailbag.class);
		mailbagBean = mockBean("MailbagEntity", Mailbag.class);
		
	}
	
	@Test
	public void shouldSaveScreeningDetailsForAgentTypeRAIssuing() throws SystemException, BusinessException, PersistenceException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("IBS");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails("FRCDGADEFRAAACA24421112001890","IBS");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID(null);
		consignmentScreeningVO.setIsoCountryCode(null);
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(9190);
		consignmentScreeningVO.setAgentType("RI");

		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}

	@Test
	public void shouldSaveScreeningDetailsForAgentTypeRAAccepting() throws SystemException, BusinessException, PersistenceException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("IBS");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(1).when(dao).findScreeningDetails("FRCDGADEFRAAACA24421112001890","IBS");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID(null);
		consignmentScreeningVO.setIsoCountryCode(null);
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(9190);
		consignmentScreeningVO.setAgentType("RA");
		logonAttributes.setCompanyCode("IBS");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldSaveScreeningDetailsWithSystemException() throws SystemException, BusinessException, PersistenceException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(222);
		Collection<MailbagVO> mailbagVos = new ArrayList<>();
		mailbagVos.add(mailbagVO);
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("IBS");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(dao).findMailSequenceNumber(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
		mailBagVOs = new ArrayList<MailbagVO>();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA24421112001890");
		mailBagVOs.add(mailbagVO);
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID(null);
		consignmentScreeningVO.setIsoCountryCode(null);
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(9190);
		consignmentScreeningVO.setAgentType("RA");
		logonAttributes.setCompanyCode("IBS");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	@Test
	public void shouldSaveScreeningDetailsWithSystemExceptionforfindScreenDetails() throws SystemException, BusinessException, PersistenceException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("IBS");
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		mailBagVOs = new ArrayList<MailbagVO>();
		mailbagVO.setMailbagId("FRCDGADEFRAAACA24421112001890");
		mailbagVO.setMailSequenceNumber(10);
		mailBagVOs.add(mailbagVO);
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		SystemException systemException = new SystemException("system exception");
		doThrow(systemException).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID(null);
		consignmentScreeningVO.setIsoCountryCode(null);
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(9190);
		consignmentScreeningVO.setAgentType("RA");
		logonAttributes.setCompanyCode("IBS");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	
	
	@Test
	public void doThrowProxyExceptionWhileInvokingUserDetails() throws SystemException, BusinessException, PersistenceException,ProxyException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setMailbagId(null);
		Collection<MailbagVO> mailbagVos = new ArrayList<>();
		mailbagVos.add(mailBagVo);
		scannedMailDetailsVO.setMailDetails(mailbagVos);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("LH");
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(1).when(dao).findScreeningDetails("FRCDGADEFRAAACA24421112001890","IBS");
		ProxyException proxyException = new ProxyException("proxy exception", null);
		doThrow(proxyException).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
		consignmentScreeningVO.setScreenLevelValue("M");
		consignmentScreeningVO.setSecurityReasonCode("CS");
		consignmentScreeningVO.setCompanyCode("IBS");
		consignmentScreeningVO.setAgentID("ICARGO");
		consignmentScreeningVO.setIsoCountryCode("US");
		consignmentScreeningVO.setExpiryDate(null);
		consignmentScreeningVO.setSource("HHT");
		consignmentScreeningVO.setMalseqnum(9190);
		consignmentScreeningVO.setAgentType("RA");
		logonAttributes.setCompanyCode("IBS");
		consignmentScreeningVOs.add(consignmentScreeningVO);
		mailController.saveSecurityDetails(consignmentScreeningVOs);
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	
	@Test
	public void shouldSetRaIdAndCountryIfUserVONotNull() throws SystemException, BusinessException, PersistenceException, FinderException{
		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setMailbagId("");
		Collection<MailbagVO> mailbagVos = new ArrayList<>();
		mailbagVos.add(mailBagVo);
		scannedMailDetailsVO.setMailDetails(mailbagVos);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RI");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails("DEFRAADEMUCAA7A80089009095431","LH");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldSetSecurityStatusCodeIfScreeningPresent() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setScreeningPresent(true);
		mailBag.setSecurityStatusCode("SPX");
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(9190);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RI");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails("DEFRAADEMUCAA7A80089009095431","LH");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	
	@Test
	public void shouldSetSecurityDetailsIfMailDetailsIsNotNull() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		 Mailbag mailBag = new Mailbag();
		MailbagVO mailBagVo = new MailbagVO();
		mailBagVo.setMailbagId("DEFRAADEMUCAA7A80089009095431");
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		Collection<MailbagVO> mailbagVos = new ArrayList<>();
		mailbagVos.add(mailBagVo);
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailbagVos);
		scannedMailDetailsVO.setScreeningPresent(true);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		mailBag.setSecurityStatusCode("SPX");
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(9190);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(eq(Mailbag.class), any(MailbagPK.class));
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RI");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails("DEFRAADEMUCAA7A80089009095431","LH");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	
	@Test
	public void shouldSetSecurityScreeningDetailsIfScreeningPresentNot() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		mailBag.setSecurityStatusCode("SPX");
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(9190);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RI");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(0).when(dao).findScreeningDetails("DEFRAADEMUCAA7A80089009095431","LH");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldSetSecurityScreeningDetailsIfRAAccepting() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailBagVOs);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		mailBag.setSecurityStatusCode("SPX");
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(9190);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RA");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(1).when(dao).findScreeningDetails("DEFRAADEMUCAA7A80089009095431","LH");
		doReturn(userVO).when(adminUserProxy).findUserDetails("LH","TESTADMIN");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	
	@Test
	public void shouldNotSaveScreeningDetailsWhenUserVoNull() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setMailSequenceNumber(222);
		Collection<MailbagVO> mailbagVos = new ArrayList<>();
		mailbagVos.add(mailbagVO);
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailbagVos);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
	
		mailBag.setSecurityStatusCode("SPX");
		mailbagPK.setCompanyCode("IBS");
		mailbagPK.setMailSequenceNumber(9190);
		mailBag.setMailbagPK(mailbagPK);
		doReturn(mailBag).when(PersistenceController.getEntityManager()).find(mailbagBean.getClass(), mailbagPK);
		
		logonAttributes = 
				ContextUtils.getSecurityContext().getLogonAttributesVO();
		logonAttributes.setCompanyCode("IBS");
		UserVO userVO = new UserVO();
		UserParameterVO userParameterVO = new UserParameterVO();
		UserParameterVO userParameterCountryVO = new UserParameterVO();
			userParameterVOs = new ArrayList<UserParameterVO>();
			userParameterVO.setParameterCode("admin.user.raid");
			userParameterVO.setParameterValue("ICARGO");
			userParameterCountryVO.setParameterCode("admin.user.country");
			userParameterCountryVO.setParameterValue("US");
			userParameterVOs.add(userParameterVO);
			userParameterVOs.add(userParameterCountryVO);
			userVO.setUserParameterVOs(userParameterVOs);
			consignmentScreeningVO.setScreenLevelValue("M");
			consignmentScreeningVO.setSecurityReasonCode("CS");
			consignmentScreeningVO.setCompanyCode("LH");
			consignmentScreeningVO.setAgentID("ICARGO");
			consignmentScreeningVO.setIsoCountryCode("US");
			consignmentScreeningVO.setExpiryDate(null);
			consignmentScreeningVO.setSource("HHT");
			consignmentScreeningVO.setMalseqnum(9190);
			consignmentScreeningVO.setAgentType("RA");

			consignmentScreeningVOs.add(consignmentScreeningVO);
			mailController.saveSecurityDetails(consignmentScreeningVOs);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
		doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
		doReturn(null).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldNotSaveScreeningDetailsWhenMailDetailsNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setProcessPoint("ACP");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldNotSaveScreeningDetailsWhenMailDetailsEmptyAndProcessPointTransferTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<MailbagVO> mailbagVos = new ArrayList<>(); 
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(mailbagVos);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setProcessPoint("TRA");
       
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	
	@Test
	public void shouldSaveScreeningConsignorDetailsTest() throws SystemException, BusinessException, PersistenceException, FinderException{
	Map<String, Object> contTransferMap= new HashMap<>();
	Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
	ContainerDetailsVO containerDetails = new ContainerDetailsVO() ;
	DSNVO dsnVO = new DSNVO();
	dsnVO.setMailbagId("DEFRAADEMUCAA7A80089009095431");
	dsnVO.setMailSequenceNumber(877);
	Collection<DSNVO> dsnVOs=new ArrayList<>();
	dsnVOs.add(dsnVO);
	containerDetails.setDsnVOs(dsnVOs);
	containerDetails.setCompanyCode("LH");
	containerDetailsColl.add(containerDetails);
	contTransferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
	containerDetailsColl);
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
	doReturn(null).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	
	@Test
	public void shouldSaveScreeningConsignorDetailsWillReturnSystemExceptionTest() throws SystemException, BusinessException, PersistenceException, FinderException{
	Map<String, Object> contTransferMap= new HashMap<>();
	Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
	ContainerDetailsVO containerDetails = new ContainerDetailsVO() ;
	DSNVO dsnVO = new DSNVO();
	dsnVO.setMailbagId("DEFRAADEMUCAA7A80089009095431");
	dsnVO.setMailSequenceNumber(877);
	Collection<DSNVO> dsnVOs=new ArrayList<>();
	dsnVOs.add(dsnVO);
	containerDetails.setDsnVOs(dsnVOs);
	containerDetails.setCompanyCode("LH");
	containerDetailsColl.add(containerDetails);
	contTransferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
	containerDetailsColl);
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
	doReturn(null).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
	SystemException systemException = new SystemException("system exception");
	doThrow(systemException).when(lhMailUploadControllerSpy).saveScreeningDetails(any(ScannedMailDetailsVO.class));
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	
	@Test
	public void shouldSaveScreeningConsignorDetailsWhenDSNIsNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
	Map<String, Object> contTransferMap= new HashMap<>();
	Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
	ContainerDetailsVO containerDetails = new ContainerDetailsVO() ;
	containerDetails.setDsnVOs(null);
	containerDetails.setCompanyCode("LH");
	containerDetailsColl.add(containerDetails);
	contTransferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
	containerDetailsColl);
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
	doReturn(null).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	
	@Test
	public void shouldSaveScreeningConsignorDetailsWhenDSNIsEmptyTest() throws SystemException, BusinessException, PersistenceException, FinderException{
	Map<String, Object> contTransferMap= new HashMap<>();
	Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
	ContainerDetailsVO containerDetails = new ContainerDetailsVO() ;
	Collection<DSNVO> dsnVOs=new ArrayList<>();
	containerDetails.setDsnVOs(dsnVOs);
	containerDetails.setCompanyCode("LH");
	containerDetailsColl.add(containerDetails);
	contTransferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
	containerDetailsColl);
	doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.operations");
	doReturn(1).when(dao).findScreeningDetails(any(String.class),any(String.class));
	doReturn(null).when(adminUserProxy).findUserDetails(any(String.class),any(String.class));
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	@Test
	public void shouldSaveScreeningConsignorDetailsWhenContainerTransferMapNullTest() throws SystemException, BusinessException, PersistenceException, FinderException{
	Map<String, Object> contTransferMap= null;
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	@Test
	public void shouldSaveScreeningConsignorDetailsWhenContainerTransferMapEmptyTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		Map<String, Object> contTransferMap= new HashMap<>();
		Collection<ContainerDetailsVO> containerDetailsColl = new ArrayList<ContainerDetailsVO>();
		contTransferMap.put(MailConstantsVO.CONST_CONTAINER_DETAILS,
				null);
	lhMailUploadControllerSpy.saveScreeningConsginorDetails(contTransferMap);
	}
	
	@Test
	public void shouldSaveScreeningDetailsWhenContainerTransferPointAndMailNotArrivedTest() throws SystemException, BusinessException, PersistenceException, FinderException{
		mailbagPK = new MailbagPK();
		mailBag = new Mailbag();
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		Collection<ConsignmentScreeningVO> consignmentScreeningVOs = new ArrayList<>();
		
		scannedMailDetailsVO.setConsignmentScreeningVos(consignmentScreeningVOs);
		scannedMailDetailsVO.setCompanyCode("LH");
		scannedMailDetailsVO.setScannedUser("TESTADMIN");
		scannedMailDetailsVO.setMailDetails(null);
		scannedMailDetailsVO.setScreeningPresent(false);
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		
		scannedMailDetailsVO.setContainerProcessPoint("CONTAINER_TRANSFER");
		scannedMailDetailsVO.setProcessPoint("ARR");
		lhMailUploadControllerSpy.saveScreeningDetails(scannedMailDetailsVO);
		
	}
	@Test
	public void checkRAFormat_Test() throws SystemException, BusinessException, PersistenceException,ProxyException, FinderException{
		String raid ="DE/RA/4523/0823";
		String companyCode ="LH";
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
		consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
		consignmentScreeningVO.setScreeningMethodCode(MailConstantsVO.RA_ISSUING);
		consignmentScreeningVO.setSource(MailConstantsVO.SOURCE_HHT);
		consignmentScreeningVO.setScreeningLocation(scannedMailDetailsVO.getAirportCode());		
		lhMailUploadControllerSpy.checkRAFormat(raid, consignmentScreeningVO, companyCode, scannedMailDetailsVO);
	}
	@Test
	public void checkRAFormatForRaidNull() throws SystemException, BusinessException, PersistenceException,ProxyException, FinderException{
		String raid =null;
		String companyCode ="LH";
		lhMailUploadControllerSpy.checkRAFormat(raid, consignmentScreeningVO, companyCode, scannedMailDetailsVO);
	}
	@Test
	public void  checkRAFormatForIsRaidFistArrayIs1digit() throws SystemException, BusinessException, PersistenceException,ProxyException, FinderException{
		String raid ="d//";
		String companyCode ="LH";
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
		consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
		consignmentScreeningVO.setScreeningMethodCode(MailConstantsVO.RA_ISSUING);
		consignmentScreeningVO.setSource(MailConstantsVO.SOURCE_HHT);
		consignmentScreeningVO.setScreeningLocation(scannedMailDetailsVO.getAirportCode());		
		lhMailUploadControllerSpy.checkRAFormat(raid, consignmentScreeningVO, companyCode, scannedMailDetailsVO);
	}
	@Test
	public void checkRAFormatForIsRaidArrayIsZero() throws SystemException, BusinessException, PersistenceException,ProxyException, FinderException{
		String raid ="/";
		String companyCode ="LH";		
		scannedMailDetailsVO = new ScannedMailDetailsVO();
		consignmentScreeningVO = new ConsignmentScreeningVO();
		consignmentScreeningVO.setScreenLevelValue(MailConstantsVO.SCREEN_LEVEL_VALUE);
		consignmentScreeningVO.setSecurityReasonCode(MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR);
		consignmentScreeningVO.setScreeningMethodCode(MailConstantsVO.RA_ISSUING);
		consignmentScreeningVO.setSource(MailConstantsVO.SOURCE_HHT);
		consignmentScreeningVO.setScreeningLocation(scannedMailDetailsVO.getAirportCode());
		lhMailUploadControllerSpy.checkRAFormat(raid, consignmentScreeningVO, companyCode, scannedMailDetailsVO);
	}
}