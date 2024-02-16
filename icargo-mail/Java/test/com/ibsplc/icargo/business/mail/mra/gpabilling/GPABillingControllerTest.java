package com.ibsplc.icargo.business.mail.mra.gpabilling;


import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.icargo.business.cra.defaults.vo.InvoiceTransactionLogVO;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABI;
import com.ibsplc.icargo.business.mail.mra.MailTrackingMRABusinessException;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleDetailsVO;
import com.ibsplc.icargo.business.mail.mra.defaults.vo.BillingScheduleFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51CN66FilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN51SummaryVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.CN66DetailsVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.FileNameLovVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GPAInvoiceVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.GeneratePASSFilterVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceDetailsReportVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.InvoiceSettlementVO;
import com.ibsplc.icargo.business.mail.mra.gpabilling.vo.SettlementDetailsVO;
import com.ibsplc.icargo.business.mail.mra.proxy.CRADefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.business.shared.defaults.filegenerate.vo.FileGenerateVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.currency.CurrencyException;
import com.ibsplc.icargo.framework.util.currency.CurrencyHelper;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.command.InvocationContext;
import com.ibsplc.icargo.persistence.dao.mail.mra.gpabilling.MRAGPABillingSqlDAO;
import com.ibsplc.icargo.presentation.web.struts.form.mail.mra.gpabilling.ListGPABillingInvoiceForm;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.icargo.business.mail.mra.proxy.FrameworkLockProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.MRADefaultProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.framework.report.agent.ReportFrameworkMock;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstanceMock;

public class GPABillingControllerTest extends AbstractFeatureTest {

	private GPABillingController GPABillingControllerSpy;
	private MRAGPABillingSqlDAO dao;
	private FrameworkLockProxy frameworkLockProxy;
	CRADefaultsProxy cRADefaultsProxy;
	private LogonAttributes logonAttributes;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private SharedCurrencyProxy sharedCurrencyProxy;
	private MRADefaultProxy mRADefaultProxy;
	private MailTrackingMRABI mailTrackingMRABIBean;
	CN51Summary CN51Summary ;
	CN66Details cn66Details;
	SettlementDetailsVO settlementDetailsVO;
	InvoiceSettlementVO unSettledInvoiceVO;
	GPABillingController gPABillingController;
	@Override
	public void setup() throws Exception {
		GPABillingControllerSpy = spy(new GPABillingController());
		CN51Summary = mockBean("CN51Summary", CN51Summary.class);
		cn66Details = mockBean("cn66Details", CN66Details.class);
		ReportUtilInstanceMock.mockReportUtilInstance();
		ReportFrameworkMock.mockReportFramework();
		EntityManagerMock.mockEntityManager();
		dao = mock(MRAGPABillingSqlDAO.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.gpabilling");
		frameworkLockProxy=mockProxy(FrameworkLockProxy.class);
		cRADefaultsProxy=mockProxy(CRADefaultsProxy.class);
		logonAttributes = mock(LogonAttributes.class);
		sharedDefaultsProxy=mock(SharedDefaultsProxy.class);
		sharedDefaultsProxy=mockProxy(SharedDefaultsProxy.class);
		sharedCurrencyProxy=mockProxy(SharedCurrencyProxy.class);
		mRADefaultProxy=mockProxy(MRADefaultProxy.class);
		mailTrackingMRABIBean = mockBean("mailMraFlowServices", MailTrackingMRABI.class);
		gPABillingController = mockBean("mRAGpaBillingcontroller", GPABillingController.class);
		setSettlementDetailsVO();
		setInvoiceSettlementVO();
	}
	private void setSettlementDetailsVO() throws CurrencyException {
		settlementDetailsVO = new SettlementDetailsVO();
		settlementDetailsVO.setSettlementId("2023FEB14");
		settlementDetailsVO.setSettlementSequenceNumber(1234);
		settlementDetailsVO.setSerialNumber(23);
		Money chequeAmount = null;
		chequeAmount = CurrencyHelper.getMoney("USD");
		chequeAmount.setAmount(5.0D);
		settlementDetailsVO.setChequeAmount(chequeAmount);
		settlementDetailsVO.setChequeCurrency("USD");
		settlementDetailsVO.setChequeNumber("00001");
	}
	private void setInvoiceSettlementVO() throws CurrencyException {
		unSettledInvoiceVO = new InvoiceSettlementVO();
		unSettledInvoiceVO.setCompanyCode("AA");
		unSettledInvoiceVO.setInvoiceNumber("M22110002");
		unSettledInvoiceVO.setInvSerialNumber(1);
		unSettledInvoiceVO.setGpaCode("HK102");
		unSettledInvoiceVO.setBillingCurrencyCode("USD");
		unSettledInvoiceVO.setSettlementLevel("M");
		unSettledInvoiceVO.setMailsequenceNum(3213);
		Money mcaNumber = null;
		mcaNumber = CurrencyHelper.getMoney("USD");
		mcaNumber.setAmount(0D);
		unSettledInvoiceVO.setMcaNumber(mcaNumber);
		unSettledInvoiceVO.setActualBilled(mcaNumber);
		unSettledInvoiceVO.setSettlementFileType("TEST");
		unSettledInvoiceVO.setNetAmount(mcaNumber);
	}
	@Test
	public void generatePASSFile_withPeriodNumber()throws SystemException, PersistenceException, FinderException{
		CN51Summary summaryEnt=new CN51Summary();
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		passFilterVO.setPeriodNumber("20210501");
		passFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		passFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		GPAInvoiceVO gPAInvoiceVO = new GPAInvoiceVO();
		gPAInvoiceVO.setPeriodNumber("20210501");
		gPAInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoices.add(gPAInvoiceVO);
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);
		List<CN51SummaryVO> summaryVOs = new ArrayList<>(); 
		Page<CN51SummaryVO> billingScheduleDetailVOs= new Page<>(summaryVOs, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(dao).findAllInvoices(any(CN51SummaryFilterVO.class));
		doReturn(summaryVOs).when(dao).findAllInvoicesForPASSFileUpdate(any(CN51SummaryFilterVO.class));
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class),any(CN51SummaryPK.class))).thenReturn(summaryEnt);
		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();
		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	@Test
	public void generatePASSFile_NoInvoice()throws SystemException, PersistenceException{
		
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		doReturn(null).when(dao).findInvoicesforPASS(passFilterVO);
		GPABillingControllerSpy.generatePASSFile(passFilterVO);
	}
	
	@Test
	public void generatePASSFile_EmptyInvoice()throws SystemException, PersistenceException{
		
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		passFilterVO.setPeriodNumber("20210501");
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);
		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();

		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	@Test

	public void generatePASSFile_fileGenerated() throws SystemException, PersistenceException, FinderException{
		CN51Summary summaryEnt=new CN51Summary();
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		FileGenerateVO fileGenerateVO= new FileGenerateVO();
		fileGenerateVO.setStatus("C");
		passFilterVO.setPeriodNumber("20210501");
		passFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		passFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		GPAInvoiceVO gPAInvoiceVO = new GPAInvoiceVO();
		gPAInvoiceVO.setPeriodNumber("20210501");
		gPAInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoices.add(gPAInvoiceVO);
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);

		List<CN51SummaryVO> summaryVOs = new ArrayList<>(); 
		CN51SummaryVO summaryVO= new CN51SummaryVO();
		summaryVO.setPassFileName(null);
		summaryVOs.add(summaryVO);
		Page<CN51SummaryVO> billingScheduleDetailVOs= new Page<>(summaryVOs, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(dao).findAllInvoices(any(CN51SummaryFilterVO.class));
		doReturn(summaryVOs).when(dao).findAllInvoicesForPASSFileUpdate(any(CN51SummaryFilterVO.class));
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class),any(CN51SummaryPK.class))).thenReturn(summaryEnt);
		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();
		doReturn(fileGenerateVO).when(GPABillingControllerSpy).getFileGenerateVO(any(GPAInvoiceVO.class), any(LogonAttributes.class),any(GeneratePASSFilterVO.class));

		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	
	@Test

	public void generatePASSFile_FileAlreadyGen() throws SystemException, PersistenceException, FinderException{
		
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		FileGenerateVO fileGenerateVO= new FileGenerateVO();
		CN51Summary summaryEnt=new CN51Summary();
		fileGenerateVO.setStatus("C");
		passFilterVO.setPeriodNumber("20210501");
		passFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		passFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		GPAInvoiceVO gPAInvoiceVO = new GPAInvoiceVO();
		gPAInvoiceVO.setPeriodNumber("20210501");
		gPAInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoices.add(gPAInvoiceVO);
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);
		
		List<CN51SummaryVO> summaryVOs = new ArrayList<>(); 
		CN51SummaryVO summaryVO= new CN51SummaryVO();
		summaryVO.setPassFileName("Test");
		summaryVOs.add(summaryVO);
		Page<CN51SummaryVO> billingScheduleDetailVOs= new Page<>(summaryVOs, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(dao).findAllInvoices(any(CN51SummaryFilterVO.class));
		doReturn(summaryVOs).when(dao).findAllInvoicesForPASSFileUpdate(any(CN51SummaryFilterVO.class));
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class),any(CN51SummaryPK.class))).thenReturn(summaryEnt);

		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();
		doReturn(fileGenerateVO).when(GPABillingControllerSpy).getFileGenerateVO(any(GPAInvoiceVO.class), any(LogonAttributes.class),any(GeneratePASSFilterVO.class));

		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	@Test

	public void generatePASSFile_AddNew() throws SystemException, PersistenceException, FinderException{
		
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		FileGenerateVO fileGenerateVO= new FileGenerateVO();
		CN51Summary summaryEnt=new CN51Summary();
		fileGenerateVO.setStatus("C");
		passFilterVO.setPeriodNumber("20210501");
		passFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		passFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		GPAInvoiceVO gPAInvoiceVO = new GPAInvoiceVO();
		gPAInvoiceVO.setPeriodNumber("20210501");
		gPAInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setInterfacedFileName("test.csv");
		invoices.add(gPAInvoiceVO);
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);
		
		List<CN51SummaryVO> summaryVOs = new ArrayList<>(); 
		CN51SummaryVO summaryVO= new CN51SummaryVO();
		summaryVO.setPassFileName("Test");
		summaryVOs.add(summaryVO);
		Page<CN51SummaryVO> billingScheduleDetailVOs= new Page<>(summaryVOs, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(dao).findAllInvoices(any(CN51SummaryFilterVO.class));
		doReturn(summaryVOs).when(dao).findAllInvoicesForPASSFileUpdate(any(CN51SummaryFilterVO.class));
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class),any(CN51SummaryPK.class))).thenReturn(summaryEnt);

		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();
		doReturn(fileGenerateVO).when(GPABillingControllerSpy).getFileGenerateVO(any(GPAInvoiceVO.class), any(LogonAttributes.class),any(GeneratePASSFilterVO.class));

		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	@Test
	public void generatePASSFile_NoInvoiceFound() throws SystemException, PersistenceException, FinderException{
		CN51Summary summaryEnt=new CN51Summary();
		GeneratePASSFilterVO passFilterVO= new GeneratePASSFilterVO();
		passFilterVO.setPeriodNumber("20210501");
		passFilterVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		passFilterVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<GPAInvoiceVO>invoices = new ArrayList<>();
		GPAInvoiceVO gPAInvoiceVO = new GPAInvoiceVO();
		gPAInvoiceVO.setPeriodNumber("20210501");
		gPAInvoiceVO.setBillingPeriodFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		gPAInvoiceVO.setBillingPeriodTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		invoices.add(gPAInvoiceVO);
		doReturn(invoices).when(dao).findInvoicesforPASS(passFilterVO);
		List<CN51SummaryVO> summaryVOs = new ArrayList<>(); 
//		Page<CN51SummaryVO> billingScheduleDetailVOs= new Page<>(summaryVOs, 0, 0, 0, 0, 0, false);
//		doReturn(billingScheduleDetailVOs).when(dao).findAllInvoices(any(CN51SummaryFilterVO.class));
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class),any(CN51SummaryPK.class))).thenReturn(summaryEnt);
		doReturn("134").when(logonAttributes).getOwnAirlineNumericCode();
		doReturn("CDG").when(logonAttributes).getStationCode();
		doReturn("ICOADMIN").when(logonAttributes).getUserId();
		GPABillingControllerSpy.generatePASSFile(passFilterVO);
		
		
	}
	
	public void shouldFindPASSFileNames() throws Exception {
		FileNameLovVO fileNameLovVO = new FileNameLovVO();
		fileNameLovVO.setFromDate("01-Jun-2021");
		doReturn(null).when(dao).findPASSFileNames(fileNameLovVO);
		GPABillingControllerSpy.findPASSFileNames(fileNameLovVO);
	}
	
	@Test
	public void generatePASSFileJobScheduler() throws SystemException, ProxyException{
		GPABillingControllerSpy.generatePASSFileJobScheduler(logonAttributes.getCompanyCode());
	}
	@Test
	public void generatePASSFileJobScheduler_NoScheduleDetails() throws SystemException, ProxyException{
		List<BillingScheduleDetailsVO> BillingScheduleDetailsVOList = new ArrayList<>(); 
		Page<BillingScheduleDetailsVO> billingScheduleDetailVOs= new Page<>(BillingScheduleDetailsVOList, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(mRADefaultProxy).findBillingScheduleDetails(any(BillingScheduleFilterVO.class));
		GPABillingControllerSpy.generatePASSFileJobScheduler(logonAttributes.getCompanyCode());

	}
	@Test
	public void generatePASSFileJobScheduler_ScheduleExist() throws SystemException, ProxyException{
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		List<BillingScheduleDetailsVO> BillingScheduleDetailsVOList = new ArrayList<>(); 
		BillingScheduleDetailsVO billingScheduleDetailsVO= new BillingScheduleDetailsVO();
		BillingScheduleDetailsVOList.add(billingScheduleDetailsVO);
		Page<BillingScheduleDetailsVO> billingScheduleDetailVOs= new Page<>(BillingScheduleDetailsVOList, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(mRADefaultProxy).findBillingScheduleDetails(any(BillingScheduleFilterVO.class));
		doReturn(invoiceTransactionLogVO).when(mRADefaultProxy).initiateTransactionLogForInvoiceGeneration(any(InvoiceTransactionLogVO.class));
		GPABillingControllerSpy.generatePASSFileJobScheduler(logonAttributes.getCompanyCode());

	}

	@Test
	public void generateInvoiceJobScheduler()throws SystemException, MailTrackingMRABusinessException, RemoteException, ProxyException{
		GPABillingControllerSpy.generateInvoiceJobScheduler(logonAttributes.getCompanyCode());

	}
	@Test
	public void generateInvoiceJobScheduler_NoSchedule()throws SystemException, MailTrackingMRABusinessException, RemoteException, ProxyException{
		
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		List<BillingScheduleDetailsVO> BillingScheduleDetailsVOList = new ArrayList<>(); 
		Page<BillingScheduleDetailsVO> billingScheduleDetailVOs= new Page<>(BillingScheduleDetailsVOList, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(mRADefaultProxy).findBillingScheduleDetails(any(BillingScheduleFilterVO.class));
		doReturn(invoiceTransactionLogVO).when(mRADefaultProxy).initiateTransactionLogForInvoiceGeneration(any(InvoiceTransactionLogVO.class));
		GPABillingControllerSpy.generateInvoiceJobScheduler(logonAttributes.getCompanyCode());

	}
	
	@Test
	public void generateInvoiceJobScheduler_ScheduleExist()throws SystemException, MailTrackingMRABusinessException, RemoteException, ProxyException{
		
		InvoiceTransactionLogVO invoiceTransactionLogVO = new InvoiceTransactionLogVO();
		List<BillingScheduleDetailsVO> BillingScheduleDetailsVOList = new ArrayList<>(); 
		BillingScheduleDetailsVO billingScheduleDetailsVO= new BillingScheduleDetailsVO();
		billingScheduleDetailsVO.setBillingPeriodFromDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		billingScheduleDetailsVO.setBillingPeriodToDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		BillingScheduleDetailsVOList.add(billingScheduleDetailsVO);
		Page<BillingScheduleDetailsVO> billingScheduleDetailVOs= new Page<>(BillingScheduleDetailsVOList, 0, 0, 0, 0, 0, false);
		doReturn(billingScheduleDetailVOs).when(mRADefaultProxy).findBillingScheduleDetails(any(BillingScheduleFilterVO.class));
		doReturn(invoiceTransactionLogVO).when(mRADefaultProxy).initiateTransactionLogForInvoiceGeneration(any(InvoiceTransactionLogVO.class));
		GPABillingControllerSpy.generateInvoiceJobScheduler(logonAttributes.getCompanyCode());

	}
	
	@Test
	public void generateCoverPageSQ_test()throws SystemException, ProxyException, PersistenceException, CurrencyException
	{
		ReportSpec reportSpec= new ReportSpec();
		InvocationContext invocationContext = new InvocationContext();
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm)invocationContext.screenModel;
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineName("SINGAPORE AIRLINES");
		airlineVO.setBillingAddress("SINGAPORE AIRLINES");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.gpabilling");
		doReturn(airlineVO).when(dao).findAirlineAddress( any(String.class),any(Integer.class));
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		GPABillingControllerSpy.generateCoverPageSQ(reportSpec);
	}
	@Test(expected = SystemException.class)
	public void findSystemParameterByCodes_catch_test()throws SystemException, ProxyException, PersistenceException 
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		HashMap<String, String> systemParameterMap = null;
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<String> systemParCodes = null;
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(null);
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		GPABillingControllerSpy.generateCoverPageSQ(reportSpec);
	}
	@Test
	public void findSystemParameterByCodes_airline_catch_test()throws SystemException, ProxyException, PersistenceException, CurrencyException 
	{
		ReportSpec reportSpec= new ReportSpec();
		InvocationContext invocationContext = new InvocationContext();
		LogonAttributes logon = ContextUtils.getSecurityContext().getLogonAttributesVO();
		logon.setOwnAirlineIdentifier(1618);
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm)invocationContext.screenModel;
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineName("SINGAPORE AIRLINES");
		airlineVO.setBillingAddress("SINGAPORE AIRLINES");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("MAIL_MRA");
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		GPABillingControllerSpy.generateCoverPageSQ(reportSpec);
	}
	
	@Test
	public void generateGPAInvoiceReportPrintAllSQ_test()throws SystemException, MailTrackingMRABusinessException, 
	PersistenceException, ProxyException, CurrencyException, Exception
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =new ArrayList<>(); ;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		cn51DetailsVos.add(cn51DetailsVO);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		cn66Detailsvo.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldDescription("AIRMAIL");
		oneTimeVO.setFieldType("mailtracking.defaults.mailcategory"); 
		oneTimeVO.setFieldValue("C");
		oneTimeVO.setLastUpdateUser("SYSTEM");
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		systemParameterMapCn66.put("mailtracking.defaults.DsnLevelImportToMRA", "Y");
		systemParameterMapCn66.put("mailtracking.mra.gpabilling.levelforgpabillingreports", "M");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
	}
	@Test(expected = SystemException.class)
	public void generateGPAInvoiceReportPrintAllSQ_Catch_test()throws SystemException, MailTrackingMRABusinessException, 
	PersistenceException, ProxyException, CurrencyException, Exception
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =null;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		doThrow(PersistenceException.class).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
		}
	@Test(expected = SystemException.class)
	public void generateGPAInvoiceReportPrintAllSQ_oneTime_test()throws SystemException, MailTrackingMRABusinessException, 
	PersistenceException, ProxyException, CurrencyException, Exception
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =null;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
		}
	@Test(expected = SystemException.class)
	public void generateGPAInvoiceReportPrintAllSQ_CN66_test()throws SystemException, MailTrackingMRABusinessException, 
	PersistenceException, ProxyException, CurrencyException, Exception
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =null;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		doThrow(PersistenceException.class).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
		}
	@Test(expected = SystemException.class)
	public void generateGPAInvoiceReportPrintAllSQ_oneTIME_test() throws PersistenceException, SystemException,
	CurrencyException, ProxyException, MailTrackingMRABusinessException
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =new ArrayList<>(); ;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		cn51DetailsVos.add(cn51DetailsVO);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldDescription("AIRMAIL");
		oneTimeVO.setFieldType("mailtracking.defaults.mailcategory"); 
		oneTimeVO.setFieldValue("C");
		oneTimeVO.setLastUpdateUser("SYSTEM");
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(null).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		Collection<String> oneTimeActiveStatusListt = new ArrayList<>();
		oneTimeActiveStatusListt.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMapp = new HashMap<>();
		oneTimeMapp.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMapp).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListt);
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListt);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
		}
	
	@Test
	public void generateGPAInvoiceReportPrintAllSQ_vorateCalculationsSQ_test() throws PersistenceException, SystemException,
	CurrencyException, ProxyException, MailTrackingMRABusinessException
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =new ArrayList<>(); ;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("D") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		cn51DetailsVos.add(cn51DetailsVO);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		cn66Detailsvo.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldDescription("AIRMAIL");
		oneTimeVO.setFieldType("mailtracking.defaults.mailcategory"); 
		oneTimeVO.setFieldValue("C");
		oneTimeVO.setLastUpdateUser("SYSTEM");
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		systemParameterMapCn66.put("mailtracking.mra.overrideroundingvalue", "N");
		systemParameterMapCn66.put("mailtracking.defaults.DsnLevelImportToMRA", "N");
		systemParameterMapCn66.put("mailtracking.mra.gpabilling.levelforgpabillingreports", "M");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
	}
	@Test
	public void generateGPAInvoiceReportPrintAllSQ_billingreportlevelsq_test() throws PersistenceException, SystemException,
	CurrencyException, ProxyException, MailTrackingMRABusinessException
	{
		ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =new ArrayList<>(); ;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(airlineVO).when(dao).findAirlineAddress("SQ",1618);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		cn66Detailsvo.setTotalAmount(5);
		cn66Detailsvo.setAmount(5);
		cn66Detailsvo.setNetAmount(amt);
		cn66Detailsvo.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldDescription("AIRMAIL");
		oneTimeVO.setFieldType("mailtracking.defaults.mailcategory"); 
		oneTimeVO.setFieldValue("C");
		oneTimeVO.setLastUpdateUser("SYSTEM");
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
	}
	
	@Test
	public void generateGPAInvoiceReportPrintAllSQ_overrounding_test() throws PersistenceException, SystemException,
	CurrencyException, ProxyException, MailTrackingMRABusinessException
	{	ReportSpec reportSpec= new ReportSpec();
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Map<String,String> systemParameterMapCn66 =null;
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<CN51DetailsVO> cn51DetailsVos =null ;
		CN51DetailsVO cn51DetailsVO = new CN51DetailsVO();
		cn51DetailsVO.setMailCategoryCode("C") ;
		cn51DetailsVO.setOrigin("FRA");
		cn51DetailsVO.setDestination("SIN");
		Money amt =null;
		amt=CurrencyHelper.getMoney("USD");
		amt.setAmount(5.0D);
		cn51DetailsVO.setTotalAmount(amt);
		doReturn(cn51DetailsVos).when(dao).generateCN51Report(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "Y");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineCode("SQ");
		airlineVO.setCompanyCode("SQ");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(null).when(dao).findAirlineAddress("",0);
		SystemException systemException = new SystemException("");
		doThrow(systemException).when(dao).findAirlineAddress("",0);
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		CN66DetailsVO cn66Detailsvo = new CN66DetailsVO();
		cn66Detailsvo.setCompanyCode("SQ");
		cn66Detailsvo.setGpaCode("SG101");
		cn66Detailsvo.setInvoiceNumber("OCT-22/10SG101");
		cn66Detailsvo.setCurrencyCode("SGD");
		cn66Detailsvo.setFlightDate(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		Collection<CN66DetailsVO> cn66DetailsVos= new ArrayList<>();
		cn66DetailsVos.add(cn66Detailsvo);
		doReturn(cn66DetailsVos).when(dao).generateCN66Report(cN51CN66FilterVO);
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add("mailtracking.defaults.mailcategory");
		Map<String, Collection<OneTimeVO>> oneTimeMap = new HashMap<>();
		Collection<OneTimeVO> oneTimeVOs = new ArrayList<>();
		OneTimeVO oneTimeVO = new OneTimeVO();
		oneTimeVO.setFieldDescription("AIRMAIL");
		oneTimeVO.setFieldType("mailtracking.defaults.mailcategory"); 
		oneTimeVO.setFieldValue("C");
		oneTimeVO.setLastUpdateUser("SYSTEM");
		oneTimeVOs.add(oneTimeVO);
		oneTimeMap.put("mailtracking.defaults.mailcategory", oneTimeVOs);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusList);
		Collection<String> systemParCodesCN66 = new ArrayList<String>();
		Collection<String> oneTimeActiveStatusListCn66 = new ArrayList<>();
		systemParCodesCN66.add("mailtracking.defaults.DsnLevelImportToMRA");
		systemParCodesCN66.add("mailtracking.mra.gpabilling.levelforgpabillingreports");
		systemParCodesCN66.add("mailtracking.mra.overrideroundingvalue");
		systemParCodesCN66.add("shared.airline.basecurrency");
		oneTimeActiveStatusListCn66.add("mailtracking.defaults.mailcategory");
		oneTimeActiveStatusListCn66.add("mail.mra.defaults.weightunit");
		doReturn(systemParameterMapCn66).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodesCN66);
		doReturn(oneTimeMap).when(sharedDefaultsProxy).findOneTimeValues(
				cN51CN66FilterVO.getCompanyCode(), oneTimeActiveStatusListCn66);
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		cn66DetailsVos.add(cn66Detailsvo);
		GPABillingControllerSpy.generateGPAInvoiceReportPrintAllSQ(reportSpec);
	
	}
	
	@Test(expected=SystemException.class)
	public void generateCoverPageSQ_airline_catch_test()throws SystemException, ProxyException, PersistenceException, CurrencyException
	{
		ReportSpec reportSpec= new ReportSpec();
		InvocationContext invocationContext = new InvocationContext();
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm)invocationContext.screenModel;
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		invoiceDetailsReportVO.setAirlineAddress("Singapore");
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setAirlineName("SINGAPORE AIRLINES");
		airlineVO.setBillingAddress("SINGAPORE AIRLINES");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.gpabilling");
		doReturn(airlineVO).when(dao).findAirlineAddress( any(String.class),any(Integer.class));
		SystemException systemException = new SystemException("");
		doThrow(PersistenceException.class).when(dao).findAirlineAddress( any(String.class),any(Integer.class));
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		GPABillingControllerSpy.generateCoverPageSQ(reportSpec);
		
	}

	@Test
	public void generateCoverPageSQ_airline_else_test()throws SystemException, ProxyException, PersistenceException, CurrencyException
	{
		ReportSpec reportSpec= new ReportSpec();
		InvocationContext invocationContext = new InvocationContext();
		ListGPABillingInvoiceForm form = (ListGPABillingInvoiceForm)invocationContext.screenModel;
		CN51CN66FilterVO cN51CN66FilterVO =new CN51CN66FilterVO();
		Collection<InvoiceDetailsReportVO> invoiceDetailsReportVOs =new ArrayList<>();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		cN51CN66FilterVO.setCompanyCode("SQ");
		cN51CN66FilterVO.setInvoiceNumber("OCT-22/10SG101");
		cN51CN66FilterVO.setGpaCode("SG101");
		reportSpec.addFilterValue(cN51CN66FilterVO);
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		InvoiceDetailsReportVO invoiceDetailsReportVO = new InvoiceDetailsReportVO();
		Money totalAmtinBillingCurr =null;
		totalAmtinBillingCurr=CurrencyHelper.getMoney("USD");
		totalAmtinBillingCurr.setAmount(5.0D);
		invoiceDetailsReportVO.setTotalAmtinBillingCurr(totalAmtinBillingCurr);
		AirlineVO airlineVO = new AirlineVO();
		airlineVO.setBillingPhone1("123");
		airlineVO.setBillingPhone2("123");
		airlineVO.setBillingFax("123");
		doReturn(invoiceDetailsReportVO).when(dao).generateInvoiceReportSQ(cN51CN66FilterVO);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.gpabilling");
		doReturn(airlineVO).when(dao).findAirlineAddress( any(String.class),any(Integer.class));
		invoiceDetailsReportVOs.add(invoiceDetailsReportVO);
		GPABillingControllerSpy.generateCoverPageSQ(reportSpec);
		
	}
	@Test
	public void updateInvoiceStatus() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		CN51Summary summaryEnt = new CN51Summary();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		GPABillingControllerSpy.updateInvoiceStatus(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap);
	}
	@Test
	public void updateInvoiceStatusWhenDueAmntGreater() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException, CurrencyException {
		CN51Summary summaryEnt = new CN51Summary();
		summaryEnt.setSettlementAmount(15);
		summaryEnt.setTotalAmountInBillingCurr(15);
		Money chequeAmount = null;
		chequeAmount = CurrencyHelper.getMoney("USD");
		chequeAmount.setAmount(0.0D);
		settlementDetailsVO.setChequeAmount(chequeAmount);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		GPABillingControllerSpy.updateInvoiceStatus(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap);
	}
	@Test
	public void updateInvoiceStatusForChequeDishonour() throws ProxyException, FinderException, SystemException {
		CN51Summary summaryEnt = new CN51Summary();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		GPABillingControllerSpy.updateInvoiceStatusForChequeDishonour(settlementDetailsVO, unSettledInvoiceVO, 15);
	}
	@Test
	public void updateInvoiceStatusForChequeDishonourMailbaglevel() throws SystemException, ProxyException, FinderException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusForChequeDishonourMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, 15);
	}
	@Test
	public void updateInvoiceStatusMailbaglevel() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(15);
		cn66Ent.setNetAmountInBillingCurr(15);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelChequeAmntZero() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException, CurrencyException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(15);
		cn66Ent.setNetAmountInBillingCurr(15);
		Money chequeAmount = null;
		chequeAmount = CurrencyHelper.getMoney("USD");
		chequeAmount.setAmount(10);
		settlementDetailsVO.setChequeAmount(chequeAmount);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelWhenDueAmntNotZero() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(13);
		cn66Ent.setNetAmountInBillingCurr(15);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelWhenDueAmntIsGreater() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException, CurrencyException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(13);
		cn66Ent.setNetAmountInBillingCurr(15);
		Money chequeAmount = null;
		chequeAmount = CurrencyHelper.getMoney("USD");
		chequeAmount.setAmount(0);
		settlementDetailsVO.setChequeAmount(chequeAmount);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelLastMailbagFalse() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(13);
		cn66Ent.setNetAmountInBillingCurr(15);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,false);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelSettlementTypeAsExcel() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		unSettledInvoiceVO.setSettlementFileType("MRASTLUPD");
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(13);
		cn66Ent.setNetAmountInBillingCurr(15);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
	@Test
	public void updateInvoiceStatusMailbaglevelSettlementTypeAsMRABTHSTL() throws MailTrackingMRABusinessException, SystemException, ProxyException, FinderException {
		unSettledInvoiceVO.setSettlementFileType("MRABTHSTL");
		CN51Summary summaryEnt = new CN51Summary();
		CN66Details cn66Ent=new CN66Details();
		cn66Ent.setSettlementAmt(13);
		cn66Ent.setNetAmountInBillingCurr(15);
		HashMap<String, String> systemParameterMap = new HashMap<>();
		Collection<String> systemParCodes = new ArrayList<String>();
		systemParCodes.add("mailtracking.mra.overrideroundingvalue");
		systemParameterMap.put("mailtracking.mra.overrideroundingvalue", "N");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParCodes);
		Map<String, Double> isConvNeedMap = new HashMap<>();
		isConvNeedMap.put("2023FEB14123423", (double) 5);
		when(PersistenceController.getEntityManager().find(eq(CN51Summary.class), any(CN51SummaryPK.class)))
				.thenReturn(summaryEnt);
		when(PersistenceController.getEntityManager().find(eq(CN66Details.class), any(CN66DetailsPK.class)))
		.thenReturn(cn66Ent);
		GPABillingControllerSpy.updateInvoiceStatusMailbaglevel(settlementDetailsVO, unSettledInvoiceVO, isConvNeedMap,true);
	}
}
