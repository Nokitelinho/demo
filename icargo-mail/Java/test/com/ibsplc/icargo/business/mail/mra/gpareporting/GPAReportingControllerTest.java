package com.ibsplc.icargo.business.mail.mra.gpareporting;

import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Matchers.eq;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
//import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicJobScheduleVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController;
import com.ibsplc.icargo.business.shared.defaults.SharedDefaultsBI;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.jobscheduler.SchedulerAgent;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpareporting.MRAGPAReportingSqlDAO;
import com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;

public class GPAReportingControllerTest extends AbstractFeatureTest {
	private GPAReportingController GPAReportingControllerMock ;
	private GPAReportingController GPAReportingControllerSpy;
	private MRAGPAReportingSqlDAO dao;
	private SharedDefaultsProxy prxy;
	private FrameworkLockProxy lockprxy;
	private MailGPAInvoicMaster mst;
	private SharedDefaultsBI SharedDefaultsBIBean ;
	private FrameworkLockProxy FrameworkLockProxymock;
	private MailInvoicMessageMaster msgmst;
	MailInvoicMessageMaster MailInvoicMessageMasterobj;
	MailInvoicMessageMasterPK MailGPAInvoicMasterPKobj;
//	SchedulerAgent SchedulerAgentMock;
	
	@Override
	public void setup() throws Exception {
		GPAReportingControllerSpy = spy(new GPAReportingController());
		GPAReportingControllerMock = mock(GPAReportingController.class);
		dao = mock(MRAGPAReportingSqlDAO.class);
		//MailInvoicMessageMasterobj = mock(MailInvoicMessageMaster.class);
		MailInvoicMessageMasterobj = new MailInvoicMessageMaster();
		MailGPAInvoicMasterPKobj = mock(MailInvoicMessageMasterPK.class);
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.gpareporting");
	//	MailInvoicMessageMasterobj = new MailInvoicMessageMaster();
	//	MailInvoicMessageMasterobj.setCompanyCode("AV");
	//	MailInvoicMessageMasterobj.setSerialNumber(1000L);
	//	doReturn(MailInvoicMessageMasterobj).when(PersistenceController.getEntityManager()).find(MailInvoicMessageMaster.class,any(MailGPAInvoicMasterPK.class));
		//prxy = mockProxy(SharedDefaultsProxy.class);
		//SharedDefaultsBIBean = mockBean("mailMraFlowServices", SharedDefaultsBI.class);
		prxy = mockProxy(SharedDefaultsProxy.class);
		lockprxy = mockProxy(FrameworkLockProxy.class);
		SharedDefaultsBIBean = mock( SharedDefaultsBI.class);
	//	SchedulerAgentMock = mock(SchedulerAgent.class);
		mst = mock(MailGPAInvoicMaster.class);
		FrameworkLockProxymock=mock(FrameworkLockProxy.class);
		msgmst = spy(new MailInvoicMessageMaster());
	}
	@Test //(expected = SystemException.class)
	public void processInvoicValidCase() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>(); 

		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		
		
		doReturn("Y-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		doReturn(100L).when(dao).findBatchNo(invoicVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());

		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	 
	@Test//(expected = SystemException.class)
	public void processInvoicInValidCase() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		//systemParameterMap.put("mail.mra.gpareporting.noofjobsrequiredforinvoic","1");
		//systemParameterMap.put("mail.mra.gpareporting.invoicprocessinglevel","F");
		//systemParameters.add("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		//systemParameters.add("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic")).thenReturn("2");
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel")).thenReturn("F");
	//	doNothing().when(prxy).findSystemParameterByCodes(systemParameters);
	//	doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn("").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
		
		doReturn("").when(dao).updateBatchNumForInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());
		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	@Test //(expected = SystemException.class)
	public void processInvoicInValidCaseNullResp() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		//systemParameterMap.put("mail.mra.gpareporting.noofjobsrequiredforinvoic","1");
		//systemParameterMap.put("mail.mra.gpareporting.invoicprocessinglevel","F");
		//systemParameters.add("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		//systemParameters.add("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic")).thenReturn("2");
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel")).thenReturn("F");
	//	doNothing().when(prxy).findSystemParameterByCodes(systemParameters);
	//	doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn("").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
		
		doReturn(null).when(dao).updateBatchNumForInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	@Test (expected = SystemException.class)
	public void processInvoicInValidCasePE() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PE");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		//systemParameterMap.put("mail.mra.gpareporting.noofjobsrequiredforinvoic","1");
		//systemParameterMap.put("mail.mra.gpareporting.invoicprocessinglevel","F");
		//systemParameters.add("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		//systemParameters.add("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic")).thenReturn("2");
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel")).thenReturn("F");
	//	doNothing().when(prxy).findSystemParameterByCodes(systemParameters);
	//	doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn("").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
	PersistenceException systemexception = new PersistenceException("");
		doThrow(systemexception).when(dao).updateBatchNumForInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	 
	 
	@Test//(expected = SystemException.class)
	public void processInvoicORCase() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		//systemParameterMap.put("mail.mra.gpareporting.noofjobsrequiredforinvoic","1");
		//systemParameterMap.put("mail.mra.gpareporting.invoicprocessinglevel","F");
		//systemParameters.add("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		//systemParameters.add("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic")).thenReturn("2");
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel")).thenReturn("F");
	//	doNothing().when(prxy).findSystemParameterByCodes(systemParameters);
	//	doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
		
		doReturn("N-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	@Test
	public void processInvoicFileFromJobTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		
		doReturn("OK").when(dao).updateBatchNumForInvoic(invoicVO);
	
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoicFileFromJob(invoicVO);
	} 
	
	@Test //(expected = SystemException.class)
	public void processInvoicInValidCaseEntity() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
		
		doReturn("").when(dao).updateBatchNumForInvoic(invoicVO);
		doThrow(SystemException.class).when(mst).processInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	@Test //(expected = SystemException.class)
	public void processInvoicInValidCaseDbcountNull() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>(); 

		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		
		
		doReturn("").when(dao).updateBatchNumForInvoic(invoicVO);
		
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());

		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	@Test //(expected = SystemException.class)
	public void processInvoicInValidCaseDbcountNullPREMPTY() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("AA");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>(); 

		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		
		
		doReturn("").when(dao).updateBatchNumForInvoic(invoicVO);
		
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());

		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	
	@Test//(expected = SystemException.class)
	public void processInvoicORCaseOKCase1() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		//systemParameterMap.put("mail.mra.gpareporting.noofjobsrequiredforinvoic","1");
		//systemParameterMap.put("mail.mra.gpareporting.invoicprocessinglevel","F");
		//systemParameters.add("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		//systemParameters.add("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic")).thenReturn("2");
	//	Mockito.when(GPAReportingControllerSpy.findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel")).thenReturn("F");
	//	doNothing().when(prxy).findSystemParameterByCodes(systemParameters);
	//	doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
	doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		//doReturn("F").when(prxy).findSystemParameterByCodes(systemParameters);
		
		doReturn("N-O").when(dao).updateBatchNumForInvoic(invoicVO);
		//doNothing().when(SchedulerAgentMock);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
//		SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.processInvoic(invoicVO);
	}
	
	@Test //(expected = SystemException.class)
	public void processInvoicValidCaseOkCase() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>(); 

		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		
		
		doReturn("Y-O").when(dao).updateBatchNumForInvoic(invoicVO);
		
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());

		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	  
	@Test //(expected = SystemException.class)
	public void processInvoicValidCaseTotmalcnt() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 	   LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>(); 

		doReturn(systemParameterMap).when(prxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn("2").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");

	doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		
		
		doReturn("Y-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		doReturn(50L).when(dao).findBatchNo(invoicVO);
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		doNothing().when(lockprxy).releaseLocks(any());

		GPAReportingControllerSpy.processInvoic(invoicVO);
	} 
	@Test
	public void updateInvoicProcessingStatusFromJobTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
	//	GPAReportingController gpa =new GPAReportingController();
 
	
	//	SchedulerAgent.getInstance().createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicProcessingStatusFromJob("AV");
	} 
	
	@Test(expected = SystemException.class)
	public void updateInvoicProcessingStatusFromJobExcpTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		PersistenceException systemexception = new PersistenceException("");
		doThrow(systemexception).when(dao).updateInvoicProcessingStatusFromJob("AV");
		GPAReportingControllerSpy.updateInvoicProcessingStatusFromJob("AV");
	} 
	@Test//(expected = SystemException.class)
	public void createAppJobForInvoicProcessingPRTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		 LogonAttributes logonAttributes = (LogonAttributes)ContextUtils.getSecurityContext().getLogonAttributesVO();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatus("PR");
		InvoicJobScheduleVO jobScheduleVO = new InvoicJobScheduleVO();
		 jobScheduleVO.setCompanyCode("AV");
         jobScheduleVO.setStartBatchnum(1);
    		jobScheduleVO.setEndBatchnum(100);  
    		jobScheduleVO.setJobName("INVOIC_PROCESSING_JOB");
      		jobScheduleVO.setRepeatCount(0);
      		jobScheduleVO.setRepeatStrategy("ONE-TIME");
      		jobScheduleVO.setScheduleId(String.valueOf(1));
      		jobScheduleVO.setStartTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true));
      		jobScheduleVO.setEndTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP,true).addHours(5));
		HashMap<String, String> systemParameterMap = null;
		ArrayList<String> systemParameters = new ArrayList<String>();
		doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.createAppJobForInvoicProcessing(invoicVO,"1");
	} 
	@Test//(expected = SystemException.class)
	public void updateInvoicRejectORATest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("N-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test//(expected = SystemException.class)
	public void updateInvoicRejectDBCNTNULLTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn("").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("N-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test//(expected = SystemException.class)
	public void updateInvoicRejectpgsqlTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("Y-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	} 
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		val.add(100L);
		doReturn(val).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("Y-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectNoSchedTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		invoicVO.setInvoicStatus("PR");
		invoicVO.setSerNums("1000");
		rejectrecords.add(invoicVO);
		doReturn("T").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		val.add(100L);
		doReturn(val).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectNoCaseTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		val.add(100L);
		doReturn(val).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("YO-OK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectLevelNullCaseTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		val.add(100L);
		doReturn(val).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("YO-NOK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectInvoicNullCaseTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn(null).when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		val.add(100L);
		doReturn(null).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("YO-NOK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
	@Test //(expected = SystemException.class)
	public void updateInvoicRejectInvoicEmptyCaseTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException, FinderException{
		Collection<InvoicVO> rejectrecords = new ArrayList<>();
		InvoicVO invoicVO = new InvoicVO();
		invoicVO.setFileName("International_1.edi");
		invoicVO.setCompanyCode("AV");
		invoicVO.setPoaCode("US101");
		invoicVO.setInvoicStatusCode("PR");
		rejectrecords.add(invoicVO);
		doReturn("F").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.invoicprocessinglevel");
		ArrayList<Long> val = new ArrayList<Long>();
		//val.add(100L);
		doReturn(val).when(dao).findInvoicsByFileName("AV","International_1.edi");
		MailGPAInvoicMasterPK MailGPAInvoicMasterPKobj= new MailGPAInvoicMasterPK();
		doReturn(new MailInvoicMessageMaster()).when(PersistenceController.getEntityManager()).find(eq(MailInvoicMessageMaster.class),any(MailGPAInvoicMasterPK.class));
		//doReturn(MailInvoicMessageMasterobj).when(MailInvoicMessageMasterobj).find( MailGPAInvoicMasterPKobj);
		doReturn("1").when(GPAReportingControllerSpy).findSystemParameterValue("mail.mra.gpareporting.noofjobsrequiredforinvoic");
		doReturn("Y-NOK").when(dao).updateBatchNumForInvoic(invoicVO);
		//doReturn("Ne").when(SchedulerAgent.getInstance()).createScheduleForJob(jobScheduleVO);
		GPAReportingControllerSpy.updateInvoicReject((Collection<InvoicVO>) rejectrecords);
	}
}
