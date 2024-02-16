package com.ibsplc.icargo.business.mail.operations.errorhandling;


import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.sql.Date;
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
import com.ibsplc.icargo.business.mail.operations.vo.MailWebserviceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailUploadVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
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
public class MailtrackigMessagePostProcessorTest extends AbstractFeatureTest{

	private MailtrackigMessagePostProcessor mailtrackigMessagePostProcessorSpy;
	private ScannedMailDetailsVO scannedMailDetailsVO;

	
	@Override
	public void setup() throws Exception {
		
		mailtrackigMessagePostProcessorSpy = spy(new MailtrackigMessagePostProcessor());		
	}
	
	@Test
	public void testPerformMailOperationForGHA1() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN35678001110013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("ACP");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
		webServiceVo.setScanType("RSGM");
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA2() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN35678001110013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("TRA");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
		
		
	}

	@Test
	public void testPerformMailOperationForGHA3() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN35678001110013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("RSGM");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA4() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN35678001110013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("OFL");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA5() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN35678001110013");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("EXP");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA6() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setMailBagId(null);
		webServiceVo.setFlightNumber("-1");
		webServiceVo.setFlightDate(new LocalDate("SIN", Location.ARP,  new Date(0, 0, 0)));
		webServiceVo.setScanType("ACP");
		webServiceVo.setToContainer("toconatiner");
		webServiceVo.setScanningPort("SIN");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA7() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN356780010013");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("EXP");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	
		
		
	}
	
	@Test
	public void testPerformMailOperationForGHA8() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId(null);
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("OFL");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
		
	}
	
	@Test
	public void testPerformMailOperationForGHA9() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("CC");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAACN356780010013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}
	
	@Test
	public void testPerformMailOperationForGHA10() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMAA1110013");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("OFL");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}
	
	@Test
	public void testPerformMailOperationForGHA11() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMA");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("OFL");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}
	
	@Test
	public void testPerformMailOperationForGHA12() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("SGSINADEHAMA");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("OFL");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}
	
	@Test
	public void testPerformMailOperationForGHA13() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId(null);
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("ACP");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}

	@Test
	public void testPerformMailOperationForGHA14() throws SystemException, BusinessException, PersistenceException, FinderException{
		Collection<MailWebserviceVO> webServicesVos = new ArrayList<>();
		MailWebserviceVO webServiceVo = new MailWebserviceVO();
		webServiceVo.setCarrierCode("");
		webServiceVo.setContainerNumber("AKE99999SQ");
		webServiceVo.setMailBagId("");
		webServiceVo.setScanningPort("SIN");
		webServiceVo.setFlightNumber("fltnum");
		webServiceVo.setScanType("ACP");
		webServiceVo.setToContainer("toconatiner");
		webServicesVos.add(webServiceVo);
		mailtrackigMessagePostProcessorSpy.performMailOperationForGHA(
					webServicesVos,"SIN");
	}
	
	@Test
	public void testValidateMailBagDetails1() throws SystemException, BusinessException, PersistenceException, FinderException{
		MailUploadVO mailUploadVo = new MailUploadVO();
		mailUploadVo.setScannedPort("SIN");
		mailtrackigMessagePostProcessorSpy.validateMailBagDetails(
				mailUploadVo);
	}
	@Test
	public void testSaveMailUploadDetailsForAndroid() throws SystemException, BusinessException, PersistenceException, FinderException{
		MailUploadVO mailUploadVo = new MailUploadVO();
		mailUploadVo.setScannedPort("SIN");
		mailtrackigMessagePostProcessorSpy.saveMailUploadDetailsForAndroid(
				mailUploadVo, "SIN");
	}

	
}