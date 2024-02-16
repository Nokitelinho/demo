package com.ibsplc.icargo.business.mail.mra.defaults;

import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_DELETE;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_INSERT;
import static com.ibsplc.xibase.server.framework.vo.AbstractVO.OPERATION_FLAG_UPDATE;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.USPSPostalCalendarVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceDetailVO;
import com.ibsplc.icargo.business.cra.accounting.vo.GLInterfaceFilterVO;
import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.proxy.TariffTaxProxy;
import com.ibsplc.icargo.business.shared.defaults.generalparameters.vo.GeneralParameterConfigurationVO;
import com.ibsplc.icargo.business.tariff.tax.vo.TaxFilterVO;
import com.ibsplc.icargo.business.tariff.tax.vo.TaxVO;

import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineParameterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingLineVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingMatrixVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.DocumentBillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRABillingDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.MRAConstantsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateAuditVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.RateLineVO;
import com.ibsplc.icargo.business.mail.mra.flown.vo.FlownMailSegmentVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.MRAGPABillingDetails;
import com.ibsplc.icargo.business.mail.mra.gpabilling.MRAGPABillingDetailsPK;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPABillingEntriesFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.InvoicVO;
import com.ibsplc.icargo.business.mail.mra.gpareporting.vo.USPSIncentiveVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingMRAProxy;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.BillingLineMultiMapper;
import com.ibsplc.icargo.persistence.dao.mail.mra.defaults.MRADefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.OptimisticConcurrencyException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.RemoveException;
import com.ibsplc.xibase.server.framework.persistence.lock.LockVO;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectAlreadyLockedException;
import com.ibsplc.xibase.server.framework.persistence.lock.ObjectNotLockedException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.persistence.query.PageableNativeQuery;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.jobscheduler.business.job.JobSchedulerException;



public class MRADefaultsControllerTest extends AbstractFeatureTest {

	private MRADefaultsController MRADefaultsControllerSpy;
	private MRADefaultsSqlDAO dao;
	private BillingMatrix billingMatrix ;
	MailTrackingDefaultsProxy mailTrackingDefaultsProxy;
	TariffTaxProxy tariffTaxProxy;
	CRADefaultsProxy cRADefaultsProxy;
	private static String mailbagID="KRSELBUSLAXAACA02810002000100";
	private static String periodNumber="20210501";
	private static String gpaBilling="G";
	private static String billingPeriod="B";
	private static final String RATE_LINE_BACK_DATE_REQUIRED="mailtracking.mra.ratelinebackdaterequired";
	private SharedDefaultsProxy sharedDefaultsProxy;
	private static final String MRA_TAX_CONFIGURATION="MRATAXVALUE";
	private static final String TAX_VALUE="taxValue";
	private static final String TAX_COUNTRY="taxCountry";
	private static final String MRA_COUNTRY_CONFIGURATION="MRATAXCOUNTRY";
	private static final String TAX_GROUP="Tax Group";
	private MRAGPABillingDetails mraGPABillingDetails;
	private MailDetailsTemp mailDetailsTemp;
	private FrameworkLockProxy lockprxy;
	MailTrackingMRAProxy mailTrackingMRAProxy;
	USPSIncentiveVO uspsIncentiveVO;
	
	@Override
	public void setup() throws Exception {
		MRADefaultsControllerSpy = spy(new MRADefaultsController());
		EntityManagerMock.mockEntityManager();
		dao = mock(MRADefaultsSqlDAO.class);
		billingMatrix= mock(BillingMatrix.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.defaults");
		mailTrackingDefaultsProxy=mockProxy(MailTrackingDefaultsProxy.class);
		cRADefaultsProxy=mockProxy(CRADefaultsProxy.class);
		uspsIncentiveVO = new USPSIncentiveVO();
		sharedDefaultsProxy=mock(SharedDefaultsProxy.class);
		sharedDefaultsProxy=mockProxy(SharedDefaultsProxy.class);
		mraGPABillingDetails= mock(MRAGPABillingDetails.class);
		mailDetailsTemp=mock(MailDetailsTemp.class);
		tariffTaxProxy= mockProxy(TariffTaxProxy.class);
		lockprxy = mockProxy(FrameworkLockProxy.class);
		mailTrackingMRAProxy=mockProxy(MailTrackingMRAProxy.class);
	}
	
	
	
	@Test
	public void importFlownMailsWithDateRangeAndMailbag() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		documentBillingVO.setBillingBasis(mailbagID);
		String txnlogInfo="121212-121212";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	

	
	@Test
	public void importFlownMailsWithDateRange() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		documentBillingVO.setBillingBasis("");
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	
	@Test
	public void importFlownMailsFlightClose() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList();
		long mailseqNum=0;
		String txnlogInfo="121212-121212";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, null, txnlogInfo);
		
		
	}
	
	
	@Test
	public void importFlownMailsSaveTransaction() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		doReturn(true).when(MRADefaultsControllerSpy).isTaxRequired();
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	
	@Test
	public void importFlownMailsWithDateRangeAndMailbagEmptyTransaction() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		long mailseqNum=0;
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		documentBillingVO.setBillingBasis(mailbagID);
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, null);
		
		
	}
	
	@Test
	public void importFlownMailsFlightclose() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		long mailseqNum=0;
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, null, null);
		
		
	}
	
	@Test
	public void importFlownMailsWithDateRangeAndMailbagEmptyProc() throws SystemException, ProxyException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		long mailseqNum=0;
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		documentBillingVO.setTriggerPoint("");
		documentBillingVO.setBillingBasis(mailbagID);
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, null);
		
		
	}
	
	@Test(expected = SystemException.class)
	public void testImportFlownMailsThrowsException() throws SystemException,PersistenceException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
      	doThrow(PersistenceException.class).when(dao).importFlownMails(any(FlightValidationVO.class),any(Collection.class),any(DocumentBillingDetailsVO.class));
      	MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, null, null);
      	
	}
	@Test
	public void findBillingTypevalidcase() throws SystemException, ProxyException,PersistenceException {
		
		BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
		billingScheduleFilterVO.setBillingPeriod(billingPeriod);
		billingScheduleFilterVO.setBillingType(gpaBilling);
		int pageno=1;
		Page<BillingScheduleDetailsVO> ouput= new Page<BillingScheduleDetailsVO>();
		BillingScheduleDetailsVO result= new BillingScheduleDetailsVO();
		result.setBillingPeriod(billingPeriod);
		ouput.add(result);
		doReturn(ouput).when(dao).findBillingType(billingScheduleFilterVO,1);
		Page<BillingScheduleDetailsVO> details= MRADefaultsControllerSpy.findBillingType(billingScheduleFilterVO, pageno);	
		assertNotNull(details);
	}
	@Test(expected = SystemException.class)
	public void findBillingTypeThrowsException() throws SystemException,PersistenceException {
		BillingScheduleFilterVO billingScheduleFilterVO = new BillingScheduleFilterVO();
		billingScheduleFilterVO.setBillingPeriod(billingPeriod);
		billingScheduleFilterVO.setBillingType(gpaBilling);
		int pageno=1;
		Page<BillingScheduleDetailsVO> ouput= new Page<BillingScheduleDetailsVO>();
		BillingScheduleDetailsVO result= new BillingScheduleDetailsVO();
		result.setBillingPeriod(billingPeriod);
		ouput.add(result);
		doThrow(PersistenceException.class).when(dao).findBillingType(billingScheduleFilterVO,1);
		MRADefaultsControllerSpy.findBillingType(billingScheduleFilterVO, pageno);		
	}
	
	@Test
	public void validateBillingSchedulemastervalidcase() throws SystemException, ProxyException,PersistenceException {
		
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setBillingPeriod(billingPeriod);
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		doReturn(true).when(dao).validateBillingSchedulemaster(billingScheduleDetailsVO);
		boolean flag= MRADefaultsControllerSpy.validateBillingSchedulemaster(billingScheduleDetailsVO);	
		assertNotNull(flag);
	}
	@Test(expected = SystemException.class)
	public void validateBillingSchedulemasterThrowsException() throws SystemException,PersistenceException {
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setBillingPeriod(billingPeriod);
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		doThrow(PersistenceException.class).when(dao).validateBillingSchedulemaster(billingScheduleDetailsVO);
		MRADefaultsControllerSpy.validateBillingSchedulemaster(billingScheduleDetailsVO);		
	}
	@Test
	public void deletebillingetails() throws SystemException,FinderException, RemoveException, OptimisticConcurrencyException{
		BillingScheduleMaster billingScheduleMaster = new BillingScheduleMaster();
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		billingScheduleDetailsVO.setBillingPeriod("M");
		billingScheduleDetailsVO.setOpearationFlag(OPERATION_FLAG_DELETE);
		 when(PersistenceController.getEntityManager().find(eq(BillingScheduleMaster.class),any(BillingScheduleMasterPK.class))).thenReturn(billingScheduleMaster);
		 doNothing().when(PersistenceController.getEntityManager()).remove(eq(BillingScheduleDetailsVO.class));
		MRADefaultsControllerSpy.saveBillingSchedulemaster(billingScheduleDetailsVO);
		
	}
	
	@Test
	public void saveBillingSchedulemasterdetails() throws SystemException,FinderException {
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setOpearationFlag(OPERATION_FLAG_INSERT);
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		billingScheduleDetailsVO.setPeriodNumber(periodNumber);
		MRADefaultsControllerSpy.saveBillingSchedulemaster(billingScheduleDetailsVO);
		
	}
	@Test
	public void saveBillingSchedulemasterwhennull() throws SystemException,FinderException {
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setOpearationFlag(null);
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		billingScheduleDetailsVO.setPeriodNumber(periodNumber);
		MRADefaultsControllerSpy.saveBillingSchedulemaster(billingScheduleDetailsVO);	
	}
	@Test
	public void saveBillingSchedulemasterwhenFlagnotI() throws SystemException,FinderException {
		BillingScheduleDetailsVO billingScheduleDetailsVO = new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setOpearationFlag(OPERATION_FLAG_UPDATE);
		billingScheduleDetailsVO.setBillingType(gpaBilling);
		billingScheduleDetailsVO.setPeriodNumber(periodNumber);
		MRADefaultsControllerSpy.saveBillingSchedulemaster(billingScheduleDetailsVO);	
	}
	@Test
	public void importMailProvisionalRateData() throws PersistenceException, SystemException {
		Collection<RateAuditVO> rateAuditVOs = new ArrayList();
		Collection<RateAuditDetailsVO> rateAuditDetailsVOs = new ArrayList();
		RateAuditVO auditVO = new RateAuditVO();
		auditVO.setCompanyCode("IBS");
		auditVO.setMailSequenceNumber(234567);
		RateAuditDetailsVO auditDetail = new RateAuditDetailsVO();
		auditDetail.setSource("TRA");
		rateAuditDetailsVOs.add(auditDetail);
		auditVO.setRateAuditDetails(rateAuditDetailsVOs);
		rateAuditVOs.add(auditVO);
		String output = "Ok";
		doReturn(output).when(dao).saveMRADataForProvisionalRate(any(RateAuditVO.class));
		MRADefaultsControllerSpy.importMailProvisionalRateData(rateAuditVOs);
	}
	
	@Test
	public void changeEnddateTest() throws SystemException,FinderException, PersistenceException, ProxyException {
	Collection<BillingLineVO> selectedBlgLineVOssdao = new ArrayList<>();
	BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
	BillingLineVO rateLine2=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","13-DEC-2022");
	BillingLineVO rateLine3=setdaovalues(2,"TESTRATE1","A","AV","29-NOV-2021","28-DEC-2023");
	BillingLineVO rateLine4=setdaovalues(3,"TESTRATE1","A","AV","25-NOV-2023","28-DEC-2023");
	BillingLineVO rateLine5=setdaovalues(5,"TESTRATE5","A","AV","30-NOV-2021","10-DEC-2022");
	selectedBlgLineVOssdao.add(rateLine1);
	selectedBlgLineVOssdao.add(rateLine2);
	selectedBlgLineVOssdao.add(rateLine3);
	selectedBlgLineVOssdao.add(rateLine4);
	selectedBlgLineVOssdao.add(rateLine5);
		
		
	Collection<BillingLineVO> selectedBlgLines = new ArrayList<>();
	selectedBlgLines.add(rateLine1);
	selectedBlgLines.add(rateLine4);
	doReturn(selectedBlgLineVOssdao).when(dao).findOverlappingBillingLines(rateLine1,"A");
	ArrayList<String> systemParameterCodes = new ArrayList<>();
	systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
	Map<String, String> systemParameterMap = new HashMap<>();
	systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
	try{
	MRADefaultsControllerSpy.changeEnddate(selectedBlgLines,"13-DEC-2022");
	MRADefaultsControllerSpy.changeEnddate(selectedBlgLines,"13-DEC-2023");
	}
	catch(SystemException e){
		
	}
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateTest6() throws SystemException, ProxyException, PersistenceException{	
		
			Collection<BillingLineVO> selectedBlgLineVOssdao = new ArrayList<>();
			BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
	
			doReturn(selectedBlgLineVOssdao).when(dao).findOverlappingBillingLines(rateLine1,"A");
			ArrayList<String> systemParameterCodes = new ArrayList<>();
			systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
			Map<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
			MRADefaultsControllerSpy.checkOverlapBillingline(rateLine1);
			
			
			BillingLineVO rateLine6=setdaovalues(1,"TESTRATE1","A","AV","25-DEC-2022","28-DEC-2022");
			BillingLineVO rateLine7=setdaovalues(5,"TESTRATE5","A","AV","30-NOV-2021","10-DEC-2021");
			selectedBlgLineVOssdao.add(rateLine6);
			selectedBlgLineVOssdao.add(rateLine7);
	
			doReturn(selectedBlgLineVOssdao).when(dao).findOverlappingBillingLines(rateLine6,"A");
	}
	
	@Test
	public void changeBillingMatrixStatusUpdateTest7() throws SystemException, ProxyException, PersistenceException{	
		
			Collection<BillingLineVO> selectedBlgLineVOssdao = new ArrayList<>();
			BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
			selectedBlgLineVOssdao.add(rateLine1);
			doReturn(selectedBlgLineVOssdao).when(dao).findOverlappingBillingLines(rateLine1,"A");
			ArrayList<String> systemParameterCodes = new ArrayList<>();
			systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
			Map<String, String> systemParameterMap = new HashMap<>();
			systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
			doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
			MRADefaultsControllerSpy.checkOverlapBillingline(rateLine1);
			
	}
	@Test
	public void changeEnddateTest1() throws SystemException,FinderException, PersistenceException, ProxyException {
	Collection<BillingLineVO> selectedBlgLineVOssdao = new ArrayList<>();
	BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
	selectedBlgLineVOssdao.add(rateLine1);
	
		
		
	Collection<BillingLineVO> selectedBlgLines = new ArrayList<>();
	selectedBlgLines.add(rateLine1);
	doReturn(selectedBlgLineVOssdao).when(dao).findOverlappingBillingLines(rateLine1,"A");
	//doReturn(rateLine1).when(billingLine).find("AV","TESTRATE1",1);
	ArrayList<String> systemParameterCodes = new ArrayList<>();
	systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
	Map<String, String> systemParameterMap = new HashMap<>();
	systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
	try{
	MRADefaultsControllerSpy.changeEnddate(selectedBlgLines,"13-DEC-2022");
	}
	catch(SystemException e){
		
	}
	}



	private BillingLineVO  setdaovalues(int seqId,String matrixId, String status, String companyCode, String startDate, String endDate) {
		BillingLineVO blgLineVOsdao=new BillingLineVO();
		LocalDate localStartDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(startDate);
		LocalDate localeEndDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate(endDate);
		blgLineVOsdao.setBillingLineSequenceNumber(seqId);
		blgLineVOsdao.setBillingMatrixId(matrixId);
		blgLineVOsdao.setBillingLineStatus(status);
		blgLineVOsdao.setBillingSector("ORG-DST");
		blgLineVOsdao.setBillingBasis("TEST");
		blgLineVOsdao.setCompanyCode(companyCode);
		blgLineVOsdao.setCurrencyCode("USD");
		blgLineVOsdao.setValidityStartDate(localStartDate);
		blgLineVOsdao.setValidityEndDate(localeEndDate);
		blgLineVOsdao.setBillingSector("ORG-DST");
		
		
		BillingLineParameterVO  billingLineParameterVO=new BillingLineParameterVO();
		Collection<BillingLineParameterVO> parametervo=new ArrayList<>();
		billingLineParameterVO.setParameterCode("ORGARPCOD");
		billingLineParameterVO.setParameterValue("CDG");
		BillingLineParameterVO  billingLineParameterVO1=new BillingLineParameterVO();
		billingLineParameterVO1.setParameterCode("DSTARPCOD");
		billingLineParameterVO1.setParameterValue("SIN");
		parametervo.add(billingLineParameterVO);
		parametervo.add(billingLineParameterVO1);   
		blgLineVOsdao.setBillingLineParameters(parametervo);
		return blgLineVOsdao;
		
	}
	@Test
	public void findOverlappingBillingLines_test() throws PersistenceException, SystemException, ProxyException {
		BillingLineVO billingLineVO= new BillingLineVO();
		String billingLineStatus ="A";
		billingLineVO.setCompanyCode("AV");
		billingLineVO.setBillingLineStatus("I");
		billingLineVO.setRevenueExpenditureFlag("N");
		billingLineVO.setBillingBasis("F");
		billingLineVO.setBillingLineSequenceNumber(1);
		billingLineVO.setBillingMatrixId("TESTRATE5");
		billingLineVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-DEC-2021"));
		Collection<BillingLineVO> billingLineVos= new ArrayList<>();
		billingLineVos.add(billingLineVO);
		doReturn(billingLineVos).when(dao).findOverlappingBillingLines(billingLineVO, billingLineStatus);
		ArrayList<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		Map<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
		MRADefaultsControllerSpy.findOverlappingBillingLines(billingLineVO, billingLineStatus);
	}
	@Test(expected = SystemException.class)
	public void findOverlappingBillingLines_proxy_catch_test() throws PersistenceException, SystemException, ProxyException {
		BillingLineVO billingLineVO= new BillingLineVO();
		String billingLineStatus ="A";
		billingLineVO.setCompanyCode("AV");
		billingLineVO.setBillingLineStatus("I");
		billingLineVO.setRevenueExpenditureFlag("N");
		billingLineVO.setBillingBasis("F");
		billingLineVO.setBillingLineSequenceNumber(3);
		billingLineVO.setBillingMatrixId("TESTRATE5");
		billingLineVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-DEC-2021"));
		Collection<BillingLineVO> billingLineVos= new ArrayList<>();
		billingLineVos.add(billingLineVO);
		doReturn(billingLineVos).when(dao).findOverlappingBillingLines(billingLineVO, billingLineStatus);
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		MRADefaultsControllerSpy.findOverlappingBillingLines(billingLineVO, billingLineStatus);
	}
	@Test
	public void findOverlappingBillingLines_validaity_date_test() throws PersistenceException, SystemException, ProxyException {
		BillingLineVO billingLineVO= new BillingLineVO();
		String billingLineStatus ="A";
		billingLineVO.setCompanyCode("AV");
		billingLineVO.setBillingLineStatus("I");
		billingLineVO.setRevenueExpenditureFlag("N");
		billingLineVO.setBillingBasis("F");
		billingLineVO.setBillingLineSequenceNumber(1);
		billingLineVO.setBillingMatrixId("TESTRATE5");
		billingLineVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-DEC-2021"));
		billingLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		BillingLineVO billingLineVoDAO= new BillingLineVO();
		billingLineVoDAO.setCompanyCode("AV");
		billingLineVoDAO.setBillingLineStatus("I");
		billingLineVoDAO.setRevenueExpenditureFlag("N");
		billingLineVoDAO.setBillingBasis("F");
		billingLineVoDAO.setBillingLineSequenceNumber(3);
		billingLineVoDAO.setBillingMatrixId("SINCDG");
		billingLineVoDAO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-OCT-2021"));
		billingLineVoDAO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVoDAO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVoDAO.setLastUpdatedUser("SYSTEM");
		Collection<BillingLineVO> billingLineVos= new ArrayList<>();
		billingLineVos.add(billingLineVoDAO);
		doReturn(billingLineVos).when(dao).findOverlappingBillingLines(billingLineVO, billingLineStatus);
		ArrayList<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		Map<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
		MRADefaultsControllerSpy.findOverlappingBillingLines(billingLineVO, billingLineStatus);
	}
	@Test
	public void findOverlappingBillingLines_N_SAME_DATE_Value_test() throws PersistenceException, SystemException, ProxyException {
		BillingLineVO billingLineVO= new BillingLineVO();
		String billingLineStatus ="A";
		billingLineVO.setCompanyCode("AV");
		billingLineVO.setBillingLineStatus("I");
		billingLineVO.setRevenueExpenditureFlag("N");
		billingLineVO.setBillingBasis("F");
		billingLineVO.setBillingLineSequenceNumber(3);
		billingLineVO.setBillingMatrixId("TESTRATE5");
		billingLineVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-DEC-2021"));
		Collection<BillingLineVO> billingLineVos= new ArrayList<>();
		billingLineVos.add(billingLineVO);
		doReturn(billingLineVos).when(dao).findOverlappingBillingLines(billingLineVO, billingLineStatus);
		ArrayList<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		Map<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
		MRADefaultsControllerSpy.findOverlappingBillingLines(billingLineVO, billingLineStatus);
	}
	@Test
	public void findOverlappingBillingLines_N_Value_test() throws PersistenceException, SystemException, ProxyException {
		BillingLineVO billingLineVO= new BillingLineVO();
		String billingLineStatus ="A";
		billingLineVO.setCompanyCode("AV");
		billingLineVO.setBillingLineStatus("I");
		billingLineVO.setRevenueExpenditureFlag("N");
		billingLineVO.setBillingBasis("F");
		billingLineVO.setBillingLineSequenceNumber(3);
		billingLineVO.setBillingMatrixId("TESTRATE5");
		billingLineVO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-DEC-2021"));
		Collection<BillingLineVO> billingLineVos= new ArrayList<>();
		BillingLineVO billingLineVoDAO= new BillingLineVO();
		billingLineVoDAO.setCompanyCode("AV");
		billingLineVoDAO.setBillingLineStatus("I");
		billingLineVoDAO.setRevenueExpenditureFlag("N");
		billingLineVoDAO.setBillingBasis("F");
		billingLineVoDAO.setBillingLineSequenceNumber(2);
		billingLineVoDAO.setBillingMatrixId("SINCDG");
		billingLineVoDAO.setValidityStartDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-OCT-2021"));
		billingLineVoDAO.setValidityEndDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVoDAO.setLastUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE, true).setDate("25-NOV-2021"));
		billingLineVoDAO.setLastUpdatedUser("SYSTEM");
		billingLineVos.add(billingLineVoDAO);
		BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
		BillingLineVO rateLine2=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","13-DEC-2022");
		BillingLineVO rateLine3=setdaovalues(2,"TESTRATE1","A","AV","29-NOV-2021","28-DEC-2023");
		BillingLineVO rateLine4=setdaovalues(3,"TESTRATE1","A","AV","25-NOV-2023","28-DEC-2023");
		BillingLineVO rateLine5=setdaovalues(5,"TESTRATE5","A","AV","30-NOV-2021","10-DEC-2022");
		billingLineVos.add(rateLine1);
		billingLineVos.add(rateLine2);
		billingLineVos.add(rateLine3);
		billingLineVos.add(rateLine4);
		billingLineVos.add(rateLine5);
		doReturn(billingLineVos).when(dao).findOverlappingBillingLines(billingLineVO, billingLineStatus);
		ArrayList<String> systemParameterCodes = new ArrayList<>();
		systemParameterCodes.add(RATE_LINE_BACK_DATE_REQUIRED);
		Map<String, String> systemParameterMap = new HashMap<>();
		systemParameterMap.put(RATE_LINE_BACK_DATE_REQUIRED, "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
		MRADefaultsControllerSpy.findOverlappingBillingLines(billingLineVO, billingLineStatus);
		
	}
	@Test
	public void calculateProvisionalRateTest() throws SystemException, ProxyException, PersistenceException, JobSchedulerException{
		long numb = 1000l;
		doNothing().when(dao).calculateProvisionalRate(numb);
		MRADefaultsControllerSpy.calculateProvisionalRate(numb);
	} 
	
	
	
	
	@Test
	public void changeBillingMatrixStatusUpdateTestRate() throws SystemException, ProxyException, PersistenceException{	
		BillingMatrixFilterVO billingMatrixFilterVO =new BillingMatrixFilterVO();
		billingMatrixFilterVO.setBillingMatrixId("TESTRATE1");
		billingMatrixFilterVO.setAirlineCode("av");
		billingMatrixFilterVO.setCompanyCode("AV");
		String status="I-0-0";
		
	//	BillingLineVO billingLineVO =new BillingLineVO();
		Page<BillingLineVO> bilingLineDetails=new Page<BillingLineVO>();
		PageableNativeQuery<BillingLineVO> qry ;
		BillingLineFilterVO blgLineFilterVO = new BillingLineFilterVO();
		blgLineFilterVO.setBillingLineId(1);
		blgLineFilterVO.setCompanyCode(billingMatrixFilterVO.getCompanyCode());
		blgLineFilterVO.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
		blgLineFilterVO.setAbsoluteIndex(1);
		blgLineFilterVO.setPageNumber(1);
		blgLineFilterVO.setDestinationCity("");
		
		blgLineFilterVO.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
	    blgLineFilterVO.setAirlineCode(billingMatrixFilterVO.getAirlineCode());
		blgLineFilterVO.setCompanyCode(billingMatrixFilterVO.getCompanyCode());
		blgLineFilterVO.setAbsoluteIndex(1);
		blgLineFilterVO.setPageNumber(1);
		
		BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
		bilingLineDetails.add(rateLine1);
		bilingLineDetails.size();
		bilingLineDetails.setTotalRecordCount(1);
		
		
	
		doReturn(bilingLineDetails).when(dao).findBillingLines(blgLineFilterVO);
		Page<BillingLineVO> bilingLineDetails2=new Page<BillingLineVO>();
		StringBuilder checkOverlapBillinglinetotal=new StringBuilder("");
		
		
		
        BillingMatrixVO billingMatrixVOs=new BillingMatrixVO();
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setBillingMatrixId("TESTRATE1");
	//	billingMatrixVOs.setBillingLineVOs(billingLineVos);
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setLastUpdatedTime(billingMatrixVOs
				.getLastUpdatedTime());
		billingMatrixVOs.setLastUpdatedUser(billingMatrixVOs
				.getLastUpdatedUser());
		doReturn(billingMatrixVOs).when(dao).findBillingMatrixDetails(billingMatrixFilterVO);	
		MRADefaultsControllerSpy.findbillinglineDetailsforStatuChage(blgLineFilterVO, "A", bilingLineDetails2, checkOverlapBillinglinetotal, billingMatrixFilterVO);
		System.out.println(bilingLineDetails2);
		System.out.println(bilingLineDetails2);	
	    MRADefaultsControllerSpy.changeBillingMatrixStatusUpdate(billingMatrixFilterVO,status);
	}
	
	
	@Test
	public void changeBillingMatrixStatusUpdateTestRate1() throws SystemException, ProxyException, PersistenceException{	
		BillingMatrixFilterVO billingMatrixFilterVO =new BillingMatrixFilterVO();
		billingMatrixFilterVO.setBillingMatrixId("TESTRATE1");
		billingMatrixFilterVO.setAirlineCode("av");
		billingMatrixFilterVO.setCompanyCode("AV");
		String status="I-0-0";
		
	//	BillingLineVO billingLineVO =new BillingLineVO();
		Page<BillingLineVO> bilingLineDetails=new Page<BillingLineVO>();
		PageableNativeQuery<BillingLineVO> qry ;
		BillingLineFilterVO blgLineFilterVO = new BillingLineFilterVO();
		blgLineFilterVO.setBillingLineId(1);
		blgLineFilterVO.setCompanyCode(billingMatrixFilterVO.getCompanyCode());
		blgLineFilterVO.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
		blgLineFilterVO.setAbsoluteIndex(1);
		blgLineFilterVO.setPageNumber(1);
		blgLineFilterVO.setDestinationCity("");
		
		blgLineFilterVO.setBillingMatrixId(billingMatrixFilterVO.getBillingMatrixId());
	    blgLineFilterVO.setAirlineCode(billingMatrixFilterVO.getAirlineCode());
		blgLineFilterVO.setCompanyCode(billingMatrixFilterVO.getCompanyCode());
		blgLineFilterVO.setAbsoluteIndex(1);
		blgLineFilterVO.setPageNumber(1);
		
		BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
		bilingLineDetails.add(rateLine1);
		bilingLineDetails.size();
		bilingLineDetails.setTotalRecordCount(1);
		
		
	
		doReturn(bilingLineDetails).when(dao).findBillingLines(blgLineFilterVO);
		Page<BillingLineVO> bilingLineDetails2=new Page<BillingLineVO>();
		StringBuilder checkOverlapBillinglinetotal=new StringBuilder("test");
		
		
		
        BillingMatrixVO billingMatrixVOs=new BillingMatrixVO();
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setBillingMatrixId("TESTRATE1");
	//	billingMatrixVOs.setBillingLineVOs(billingLineVos);
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setLastUpdatedTime(billingMatrixVOs
				.getLastUpdatedTime());
		billingMatrixVOs.setLastUpdatedUser(billingMatrixVOs
				.getLastUpdatedUser());
		doReturn(billingMatrixVOs).when(dao).findBillingMatrixDetails(billingMatrixFilterVO);	
		MRADefaultsControllerSpy.findbillinglineDetailsforStatuChage(blgLineFilterVO, "A", bilingLineDetails2, checkOverlapBillinglinetotal, billingMatrixFilterVO);
		System.out.println(bilingLineDetails2);
		System.out.println(bilingLineDetails2);	
	    MRADefaultsControllerSpy.changeBillingMatrixStatusUpdate(billingMatrixFilterVO,status);
	}
	

	@Test 
	public void updateStatusBiilingMatrixTest() throws SystemException, PersistenceException{
		Collection<BillingLineVO> billingLineVos=new ArrayList<>();
		BillingLineVO rateLine1=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","28-DEC-2022");
		BillingLineVO rateLine2=setdaovalues(1,"TESTRATE1","A","AV","25-NOV-2021","13-DEC-2022");
		BillingLineVO rateLine3=setdaovalues(2,"TESTRATE1","A","AV","29-NOV-2021","28-DEC-2023");
		BillingLineVO rateLine4=setdaovalues(3,"TESTRATE1","A","AV","25-NOV-2023","28-DEC-2023");
		BillingLineVO rateLine5=setdaovalues(5,"TESTRATE5","A","AV","30-NOV-2021","10-DEC-2022");
		billingLineVos.add(rateLine1);
		billingLineVos.add(rateLine2);
		billingLineVos.add(rateLine3);
		billingLineVos.add(rateLine4);
		billingLineVos.add(rateLine5);
		BillingMatrixVO billingMatrixVOs=new BillingMatrixVO();
		
		BillingMatrixFilterVO billingMatrixFilterVO = new BillingMatrixFilterVO();
		billingMatrixFilterVO.setBillingMatrixId("TESTRATE1");
		
		
		//String status="I";
		StringBuilder checkOverlapBillinglinetotal=new StringBuilder("test1");
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setBillingMatrixId("TESTRATE1");
	//	billingMatrixVOs.setBillingLineVOs(billingLineVos);
		billingMatrixVOs.setBillingMatrixStatus("A");
		billingMatrixVOs.setLastUpdatedTime(billingMatrixVOs
				.getLastUpdatedTime());
		billingMatrixVOs.setLastUpdatedUser(billingMatrixVOs
				.getLastUpdatedUser());
		
		
		doReturn(billingMatrixVOs).when(dao).findBillingMatrixDetails(billingMatrixFilterVO);
		List <String> statusLists=new ArrayList<String>(
			    Arrays.asList("I","A","C","N","E"));
		for(String status :statusLists){
		MRADefaultsControllerSpy.updateStatusBiilingMatrix(billingLineVos, billingMatrixFilterVO, status, checkOverlapBillinglinetotal);
		 checkOverlapBillinglinetotal=new StringBuilder("");
		}
	}
	/**
	 * Scenerio: get MRA accounting details
	 */
	@Test
	public void shouldCallGetMRAGLAccountingEntries() throws SystemException, ProxyException, PersistenceException {
		GLInterfaceFilterVO glInterfaceFilterVO = new GLInterfaceFilterVO();
		glInterfaceFilterVO.setCompanyCode("CMPCOD");
		List<GLInterfaceDetailVO> glInterfaceFilterVOs = new ArrayList<GLInterfaceDetailVO>();
		doReturn(glInterfaceFilterVOs).when(dao).findMRAGLAccountingEntries(glInterfaceFilterVO);
		MRADefaultsControllerSpy.findMRAGLAccountingEntries(glInterfaceFilterVO);
		assertNotNull(glInterfaceFilterVO);
	}   
@Test
	public void importFlownMailsForUpdateTAX_test() throws SystemException, ProxyException, PersistenceException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(7356);
		mraBillingDetails.add(billingDetailsVO);
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		doReturn(true).when(MRADefaultsControllerSpy).isTaxRequired();
		doReturn(mraBillingDetails).when(dao).findBillingEntriesForFlight(flightValidationVO);
		doReturn("18.0").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	@Test(expected = SystemException.class)
	public void importFlownMailsForUpdateTAX_exception_test() throws SystemException, ProxyException, PersistenceException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		
		cRADefaultsProxy.updateTransactionandRemarks(null);
		doReturn(true).when(MRADefaultsControllerSpy).isTaxRequired();
		doThrow(PersistenceException.class).when(dao).findBillingEntriesForFlight(flightValidationVO);

		doReturn("18.0").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	@Test
	public void importFlownMailsForUpdateTAX_null_test() throws SystemException, ProxyException, PersistenceException {
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		
		cRADefaultsProxy.updateTransactionandRemarks(null);
		doReturn(true).when(MRADefaultsControllerSpy).isTaxRequired();
		doReturn(null).when(dao).findBillingEntriesForFlight(flightValidationVO);

		doReturn("18.0").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	@Test
	public void importFlownMailsForUpdateTAXElse_test() throws SystemException, ProxyException, PersistenceException, CurrencyException, FinderException,OptimisticConcurrencyException{
		
		FlightValidationVO flightValidationVO= new FlightValidationVO ();
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		LocalDate csgDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate("14-FEB-2023");
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		TaxFilterVO taxFilterVO= new TaxFilterVO();
		HashMap<String,TaxFilterVO> taxFilterMap = new HashMap<>();
		billingDetailsVO.setOrgCountryCode("US");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(7356);
		billingDetailsVO.setGpaCountryCode("US");
		billingDetailsVO.setMailbagOrigin("LAX");
		billingDetailsVO.setMailbagDestination("SYD");
		billingDetailsVO.setConsignmentDate(csgDate);
		billingDetailsVO.setRecieveDate(csgDate);
		billingDetailsVO.setContractCurrCode("USD");
		billingDetailsVO.setPaymentFlag("");
		mraBillingDetails.add(billingDetailsVO);
		Collection<FlownMailSegmentVO> flownMailSegmentVOs= new ArrayList<>();
		HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails = new HashMap<>();
		TaxVO taxVO = new TaxVO();
		Money netAmount= CurrencyHelper.getMoney("USD");
		netAmount.plusEquals(1.95000000);
		taxVO.setCargoType("M");
		taxVO.setConfigurationCode("TDSDUEGPA");
		taxVO.setConfigurationType("TDS");
		Collection<TaxVO> taxVOs = new ArrayList<>();
		taxVOs.add(taxVO);
		TaxVO taxVO2 = new TaxVO();
		taxVO2.setCargoType("M");
		taxVO2.setConfigurationCode("ST");
		taxVO2.setConfigurationType("TAX");
		taxVOs.add(taxVO2);
		HashMap<String, Collection<TaxVO>> hash1 = new HashMap<>();
		
		hash1.put("TDS", taxVOs);
		hash1.put("TAX", taxVOs);
		taxDetails.put(null, hash1);
		taxFilterMap.put("USLAXAAUSYDAAUX33604187910032", taxFilterVO);
		doReturn(taxDetails).when(tariffTaxProxy).computeTax(taxFilterMap);

		DocumentBillingDetailsVO documentBillingVO= new DocumentBillingDetailsVO();
		long mailseqNum=0;
		documentBillingVO.setTriggerPoint(MRAConstantsVO.TRGPNT_PROCESS_MANAGER);
		String txnlogInfo="";
		FlownMailSegmentVO flownMailSegmentVO= new FlownMailSegmentVO();
		flownMailSegmentVOs.add(flownMailSegmentVO);
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_TAX_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_VALUE);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "18.0");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		mraGPABillingdetails.setContractCurrencyCode("USD");
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
	
		doReturn(mailseqNum).when(mailTrackingDefaultsProxy).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		cRADefaultsProxy.updateTransactionandRemarks(null);
		doReturn(true).when(MRADefaultsControllerSpy).isTaxRequired();
		doReturn(mraBillingDetails).when(dao).findBillingEntriesForFlight(flightValidationVO);
		doReturn(null).when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		MRADefaultsControllerSpy.importFlownMails(flightValidationVO, flownMailSegmentVOs, documentBillingVO, txnlogInfo);
		
		
	}
	@Test 
	public void taxMraIntUpdate_test() throws SystemException, ProxyException, FinderException
	{
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode("AV");
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(1);
		mraBillingDetails.add(billingDetailsVO);
		double tax=18.0;
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_COUNTRY_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_COUNTRY);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "IN");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		mraGPABillingdetails.setContractCurrencyCode("USD");
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		doReturn(mailDetailsTemp).when(PersistenceController.getEntityManager()).find(eq(MailDetailsTemp.class), any( MailDetailsTempPK.class));

		doReturn("IN").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
		
		MRADefaultsControllerSpy.taxMraIntUpdate(mraBillingDetails, tax);
		
	}
	
	@Test
	public void taxMraIntUpdate_null_test() throws SystemException, ProxyException, FinderException
	{
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		mraBillingDetails.add(billingDetailsVO);
		double tax=18.0;
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_COUNTRY_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_COUNTRY);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "IN");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		mraGPABillingdetails.setContractCurrencyCode("USD");
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		
		doReturn(null).when(MRADefaultsControllerSpy).taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
		
		MRADefaultsControllerSpy.taxMraIntUpdate(mraBillingDetails, tax);
		
	}
	@Test 
	public void taxMraIntUpdate_temp_exception_test() throws SystemException, ProxyException, FinderException
	{
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		mraBillingDetails.add(billingDetailsVO);
		double tax=18.0;
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_COUNTRY_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_COUNTRY);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "IN");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		mraGPABillingdetails.setContractCurrencyCode("USD");
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
	doReturn("IN").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		doReturn(null).when(PersistenceController.getEntityManager()).find(eq(MailDetailsTemp.class), any( MailDetailsTempPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MailDetailsTemp.class), any( MailDetailsTempPK.class));
		MRADefaultsControllerSpy.taxMraIntUpdate(mraBillingDetails, tax);
	}
	@Test(expected = SystemException.class)
	public void taxMraIntUpdate_mraGPABillingdetails_test() throws SystemException, ProxyException, FinderException
	{
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode("AV");
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(1);
		mraBillingDetails.add(billingDetailsVO);
		double tax=18.0;
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_COUNTRY_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_COUNTRY);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "IN");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		doReturn(mailDetailsTemp).when(PersistenceController.getEntityManager()).find(eq(MailDetailsTemp.class), any( MailDetailsTempPK.class));
		doReturn("IN").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
		MRADefaultsControllerSpy.taxMraIntUpdate(mraBillingDetails, tax);
	}
	@Test 
	public void updateTaxForMRA_test() throws SystemException, PersistenceException, ProxyException
	{
		
		String companyCode ="AV";
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(7356);
		mraBillingDetails.add(billingDetailsVO);
		
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_TAX_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_VALUE);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "18.0");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		
		doReturn("18.0").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);

		doReturn(mraBillingDetails).when(dao).findBillingEntriesAtMailbagLevel(companyCode);

		MRADefaultsControllerSpy.updateTaxForMRA(companyCode);
	}
	
	@Test(expected = SystemException.class)
	public void updateTaxForMRA_exception_test() throws SystemException, PersistenceException, ProxyException
	{
		String companyCode ="AV";
		Collection<MRABillingDetailsVO> mraBillingDetails =null;
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_TAX_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_VALUE);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "18.0");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(configVOTaxValue);
		doReturn("18.0").when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		doReturn(null).when(dao).findBillingEntriesAtMailbagLevel("");
		doThrow(PersistenceException.class).when(dao).findBillingEntriesAtMailbagLevel("");
		MRADefaultsControllerSpy.updateTaxForMRA("");
	}
	@Test 
	public void updateTaxForMRANoTax_test() throws SystemException, PersistenceException, CurrencyException, ProxyException, FinderException,OptimisticConcurrencyException
	{
		LocalDate csgDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false).setDate("14-FEB-2023");
		TaxFilterVO taxFilterVO= new TaxFilterVO();
		HashMap<String,TaxFilterVO> taxFilterMap = new HashMap<String, TaxFilterVO>();
		String companyCode ="AV";
		HashMap<String ,HashMap<String ,Collection<TaxVO>>> taxDetails = new HashMap<String ,HashMap<String ,Collection<TaxVO>>>();
		TaxVO taxVO = new TaxVO();
		Money netAmount= CurrencyHelper.getMoney("USD");
		netAmount.plusEquals(1.95000000);
		taxVO.setCargoType("M");
		taxVO.setConfigurationCode("TDSDUEGPA");
		taxVO.setConfigurationType("TDS");
		Collection<TaxVO> taxVOs = new ArrayList<>();
		taxVOs.add(taxVO);
		TaxVO taxVO2 = new TaxVO();
		taxVO2.setCargoType("M");
		taxVO2.setConfigurationCode("ST");
		taxVO2.setConfigurationType("TAX");
		taxVOs.add(taxVO2);
		HashMap<String, Collection<TaxVO>> hash1 = new HashMap<>();
		
		hash1.put("TDS", taxVOs);
		hash1.put("TAX", taxVOs);
		taxDetails.put(null, hash1);
		MailDetailsTemp mailDetailsTemp= new  MailDetailsTemp();
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("US");
		billingDetailsVO.setCompanyCode(companyCode);
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(7356);
		billingDetailsVO.setGpaCountryCode("US");
		billingDetailsVO.setMailbagOrigin("LAX");
		billingDetailsVO.setMailbagDestination("SYD");
		billingDetailsVO.setConsignmentDate(csgDate);
		billingDetailsVO.setRecieveDate(csgDate);
		billingDetailsVO.setContractCurrCode("USD");
		billingDetailsVO.setPaymentFlag("");
		
		mraGPABillingDetails.setContractCurrencyCode("USD");
		mraGPABillingDetails.setAppliedRate(0.49000000);
		mraGPABillingDetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingDetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingDetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
	
		billingDetailsVO.setNetAmount(netAmount);
		billingDetailsVO.setUpdBillTo("US101");
		billingDetailsVO.setWgtCharge(1.95000000);
		billingDetailsVO.setValCharges(1.95000000);
		billingDetailsVO.setSurCharge(1.95000000);
		mraBillingDetails.add(billingDetailsVO);
		doReturn(null).when(MRADefaultsControllerSpy).taxValuesMRA(MRA_TAX_CONFIGURATION,TAX_VALUE);
		taxFilterMap.put("USLAXAAUSYDAAUX33604187910032", taxFilterVO);
	
		doReturn(taxDetails).when(tariffTaxProxy).computeTax(taxFilterMap);

		doReturn(mraBillingDetails).when(dao).findBillingEntriesAtMailbagLevel(companyCode);
		doReturn(mailDetailsTemp).when(PersistenceController.getEntityManager()).find(eq(MailDetailsTemp.class), any( MailDetailsTempPK.class));
		MRADefaultsControllerSpy.updateTaxForMRA(companyCode);
	}
	
	@Test 
	public void taxValuesMRA() throws SystemException, ProxyException
	{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		logonAttributes.setCompanyCode("AV");
		GeneralParameterConfigurationVO configVOTaxValue = new GeneralParameterConfigurationVO();
		configVOTaxValue.setCompanyCode("AV");
		configVOTaxValue.setMasterType(MRA_COUNTRY_CONFIGURATION);
		configVOTaxValue.setConfigurationReferenceOne(TAX_GROUP);
		configVOTaxValue.setParmeterCode(TAX_COUNTRY);
		Map<String, HashMap<String, String>> configParamsTaxValues = new HashMap<>();
		HashMap<String, String> configParamsTaxValue = new HashMap<>();
		configParamsTaxValue.put(TAX_COUNTRY, "18.0");
		configParamsTaxValues.put(TAX_GROUP, configParamsTaxValue);
		doReturn(configParamsTaxValues).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(any(GeneralParameterConfigurationVO.class));
		
		
		 MRADefaultsControllerSpy.taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
	}
	@Test
	public void taxValuesMRA_exception() throws SystemException, ProxyException
	{
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext()
				.getLogonAttributesVO();
		logonAttributes.setCompanyCode("AV");		
		doReturn(null).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(any(GeneralParameterConfigurationVO.class));
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findGeneralParameterConfigurationDetails(any(GeneralParameterConfigurationVO.class));
		 MRADefaultsControllerSpy.taxValuesMRA(MRA_COUNTRY_CONFIGURATION,TAX_COUNTRY);
	}
	@Test
	public void updateBilling_test() throws FinderException, SystemException
	{
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode("AV");
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(1);
		mraBillingDetails.add(billingDetailsVO);
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		mraGPABillingdetails.setContractCurrencyCode("USD");
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		MRADefaultsControllerSpy.updateBillingDetail(billingDetailsVO);
	}
	@Test
	public void updateBilling_currency_test() throws FinderException, SystemException
	{
		LogonAttributes logonAttributes = new LogonAttributes();
		logonAttributes.setCompanyCode("AV");
		MRABillingDetailsVO billingDetailsVO = new MRABillingDetailsVO();
		Collection<MRABillingDetailsVO> mraBillingDetails =new ArrayList<>();
		billingDetailsVO.setOrgCountryCode("IN");
		billingDetailsVO.setCompanyCode("AV");
		billingDetailsVO.setMailSequenceNumber(29965005);
		billingDetailsVO.setSequenceNumber(1);
		mraBillingDetails.add(billingDetailsVO);
		MRAGPABillingDetails mraGPABillingdetails = new MRAGPABillingDetails();
		mraGPABillingdetails.setAppliedRate(0.49000000);
		mraGPABillingdetails.setWieghtChargeInContractCurrency(5.39000000);
		mraGPABillingdetails.setOtherChargeInContractCurrency(0);
		doReturn(mraGPABillingdetails).when(PersistenceController.getEntityManager()).find(eq(MRAGPABillingDetails.class), any(MRAGPABillingDetailsPK.class));
		MRADefaultsControllerSpy.updateBillingDetail(billingDetailsVO);
	}

	@Test
	public void testAddAutoMraMcaLocksSuccess() throws ProxyException, SystemException {
		 Collection<LockVO> acquiredLockVOs = new ArrayList<LockVO>();
		 doReturn(acquiredLockVOs).when(lockprxy).addLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.addAutoMraMcaLocks();
	}
	@Test
	public void testAddAutoMraMcaLocksThrowsProxyException() throws ProxyException, SystemException {
		 doThrow(ProxyException.class).when(lockprxy).addLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.addAutoMraMcaLocks();
	}
	@Test(expected=ObjectAlreadyLockedException.class)
	public void testAddAutoMraMcaLocksObjectAlreadyLockedException() throws ProxyException, SystemException {
		 SystemException e =  new SystemException("persistence.lock.objectalreadylocked");
		 doThrow(e).when(lockprxy).addLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.addAutoMraMcaLocks();
			
	}
	
	@Test(expected=SystemException.class)
	public void testAddAutoMraMcaLocksException() throws ProxyException, SystemException {
		 SystemException e =  new SystemException("error");
		 doThrow(e).when(lockprxy).addLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.addAutoMraMcaLocks();
	}
	@Test
	public void testReleaseaLocksSuccess() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 doNothing().when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
	}
	@Test(expected=SystemException.class)
	public void testReleaseaLocksThrowsProxyException() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 doThrow(ProxyException.class).when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
	}
	@Test(expected=ObjectNotLockedException.class)
	public void testReleaseaLocksObjectsNotAlreadyLockedException() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 SystemException e =  new SystemException("persistence.lock.objectnotlocked");
		 doThrow(e).when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
	}
	@Test(expected=SystemException.class)
	public void testReleaseLocks() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 Collection<LockVO> acquiredLockVOs = new ArrayList<LockVO>();
		 doThrow(SystemException.class).when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
	}
	@Test(expected=SystemException.class)
	public void testReleaseLocksException() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 SystemException e =  new SystemException("error");
		 doThrow(e).when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
	}
	@Test
	public void testUpdateInvoiceTransferLogForAutoMca() throws ProxyException, SystemException {
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = new GPABillingEntriesFilterVO();
		 doNothing().when(cRADefaultsProxy).updateTransactionandRemarks(any(InvoiceTransactionLogVO.class));
		 MRADefaultsControllerSpy.updateInvoiceTransferLogForAutoMca(gpaBillingEntriesFilterVO);
	}
	@Test(expected=SystemException.class)
	public void testUpdateInvoiceTransferLogForAutoMcaThrowsProxyException() throws ProxyException, SystemException {
		GPABillingEntriesFilterVO gpaBillingEntriesFilterVO = new GPABillingEntriesFilterVO();
		 doThrow(ProxyException.class).when(cRADefaultsProxy).updateTransactionandRemarks(any(InvoiceTransactionLogVO.class));
		 MRADefaultsControllerSpy.updateInvoiceTransferLogForAutoMca(gpaBillingEntriesFilterVO);
	}
	
	@Test(expected=SystemException.class)
	public void testReleaseLocksExceptionEmpty() throws ProxyException, SystemException {
		 Collection<LockVO> lockVOs = new ArrayList<LockVO>();
		 Collection<com.ibsplc.xibase.server.framework.vo.ErrorVO> errors = new ArrayList<>();
		 SystemException e =  new SystemException(errors);
		 doThrow(e).when(lockprxy).releaseLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.releaseLocks(lockVOs);
			
	}
	
	@Test(expected=SystemException.class)
	public void testAddAutoMraMcaLocksExceptionEmpty() throws ProxyException, SystemException {
		 Collection<com.ibsplc.xibase.server.framework.vo.ErrorVO> errors = new ArrayList<>();
		 SystemException e =  new SystemException(errors);
		 doThrow(e).when(lockprxy).addLocks(anyCollectionOf(LockVO.class));
		 MRADefaultsControllerSpy.addAutoMraMcaLocks();
			
	}

	
	@Test
	public void calculateUSPSIncentiveValid() throws SystemException, ProxyException, PersistenceException{	
		uspsIncentiveVO.setCompanyCode("AV");
		uspsIncentiveVO.setExcAmot("Y");
		Collection<USPSPostalCalendarVO> uspsPostalCalendarVOs = new ArrayList<USPSPostalCalendarVO>();
		LocalDate currentDate = new LocalDate(LocalDate.NO_STATION,Location.NONE,false);
		USPSPostalCalendarVO uspsPostalCalendarVO = new USPSPostalCalendarVO();
		uspsPostalCalendarVO.setIncCalcDate(currentDate);
		uspsPostalCalendarVOs.add(uspsPostalCalendarVO);
		doReturn(uspsPostalCalendarVOs).when(dao).findUSPSInternationalIncentiveJobDetails("AV");
		MRADefaultsControllerSpy.calculateUSPSIncentive(uspsIncentiveVO);
	}
	@Test
	public void calculateUSPSIncentiveAmtValid() throws SystemException, ProxyException, PersistenceException{	
		uspsIncentiveVO.setCompanyCode("AV");
		uspsIncentiveVO.setExcAmot("Y");
		USPSPostalCalendarVO uspsPostalCalendarVO = new USPSPostalCalendarVO();
		doNothing().when(dao).calculateUSPSIncentive(uspsPostalCalendarVO,uspsIncentiveVO);
		MRADefaultsControllerSpy.calculateUSPSIncentiveAmount(uspsPostalCalendarVO,uspsIncentiveVO);
	}
	


}	


