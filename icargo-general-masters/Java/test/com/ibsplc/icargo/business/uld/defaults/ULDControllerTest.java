/*
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of
 * IBS Software Services (P) Ltd.
 *
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.uld.defaults;

import com.ibsplc.icargo.business.msgbroker.message.vo.MessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.luc.*;
import com.ibsplc.icargo.business.msgbroker.message.vo.ucm.UCMMessageVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.business.shared.message.vo.MessageConfigConstants;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeVO;
import com.ibsplc.icargo.business.shared.uld.vo.ULDTypeValidationVO;
import com.ibsplc.icargo.business.uld.defaults.feature.createuldloantransaction.SaveULDLoanTransactionFeature;
import com.ibsplc.icargo.business.uld.defaults.feature.lucmessage.LUCMessageFeature;
import com.ibsplc.icargo.business.uld.defaults.feature.returnuld.SaveReturnTransactionFeature;
import com.ibsplc.icargo.business.uld.defaults.feature.sendscmreminder.SCMReminderNotificationsFeature;
import com.ibsplc.icargo.business.uld.defaults.feature.uldcount.ULDCountFeature;
import com.ibsplc.icargo.business.uld.defaults.feature.uldcount.ULDCountFeatureConstants;
import com.ibsplc.icargo.business.uld.defaults.feature.updateulddemurragedetails.UpdateULDDemurrageDetailsFeature;
import com.ibsplc.icargo.business.uld.defaults.message.ULDFlightMessageReconcile;
import com.ibsplc.icargo.business.uld.defaults.message.ULDFlightMessageReconcileDetails;
import com.ibsplc.icargo.business.uld.defaults.message.ULDFlightMessageReconcileDetailsPK;
import com.ibsplc.icargo.business.uld.defaults.message.ULDSCMReconcileDetails;
import com.ibsplc.icargo.business.uld.defaults.message.ULDSCMReconcileDetailsPK;
import com.ibsplc.icargo.business.uld.defaults.message.vo.*;
import com.ibsplc.icargo.business.uld.defaults.misc.ULDDiscrepancy;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAgreementVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDAirportLocationVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDDiscrepancyVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementDetailVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementFilterVO;
import com.ibsplc.icargo.business.uld.defaults.misc.vo.ULDMovementVO;
import com.ibsplc.icargo.business.uld.defaults.proxy.*;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDChargingInvoice;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDChargingInvoicePK;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransaction;
import com.ibsplc.icargo.business.uld.defaults.transaction.ULDTransactionPK;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionListVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.TransactionVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDChargingInvoiceVO;
import com.ibsplc.icargo.business.uld.defaults.transaction.vo.ULDTransactionDetailsVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDConfigAuditVO;
import com.ibsplc.icargo.business.uld.defaults.vo.ULDNumberVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.floworchestration.context.ICargoSproutAdapter;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstance;
import com.ibsplc.icargo.framework.report.util.ReportUtilInstanceMock;
import com.ibsplc.icargo.framework.report.vo.ReportSpec;
import com.ibsplc.icargo.framework.util.airline.cache.AirlineCache;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsDAO;
import com.ibsplc.icargo.persistence.dao.uld.defaults.ULDDefaultsSqlDAO;
import com.ibsplc.xibase.server.framework.audit.util.AuditUtilsInstance;
import com.ibsplc.xibase.server.framework.cache.CacheFactory;
import com.ibsplc.xibase.server.framework.exceptions.BusinessException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.keygen.provider.Criterion;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstance;
import com.ibsplc.xibase.server.framework.util.keygen.KeyUtilInstanceMock;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import org.junit.Test;

import java.rmi.RemoteException;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;
import com.ibsplc.icargo.business.uld.defaults.proxy.SharedAirlineProductProxy;

/**
 * @author A-7900
 */
public class ULDControllerTest extends AbstractFeatureTest {
    private ULDFlightMessageReconcile uldFlightMessageReconcileMock;
    private ULDController uldController;
    private Collection<FlightDetailsVO> flightDetailsVOs;
    private ULDController uldContrller;
    private ULDDefaultsDAO ULDDefaultsMockDAO;
    private SCMReminderNotificationsFeature scmReminderNotificationsFeature;
    private ULDDefaultsSqlDAO uldDefaultsSqlDAO;
    private ULDTransaction uldTransaction;
    private OperationsFltHandlingProxy operationsFltHandlingProxy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private SharedAirlineProductProxy sharedAirlineProxy;
    private ULDCountFeature uldCountFeature;
    private SaveReturnTransactionFeature demurrageFeature;
    private UpdateULDDemurrageDetailsFeature updateULDDemurrageDetailsFeature;
    private SaveULDLoanTransactionFeature saveULDLoanTransactionFeature;
    private KeyUtilInstance keyUtils; 
    private Map <String, String> systemParamterMap = null;
    TransactionListVO transactionListVO;
    Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs;
    ULDTransactionDetailsVO uldTransactionDetailVO;
    private static final String ULDNUM="AKE0099EK";
    private static final String TRANSACTION="T";
    private static final String CMPCOD = "AV";
    private static final String FLTNO = "AV1231";
    private static final int FLTIDR = 98;
    private static final int FLTSEQNUM = 76;
    private static final String ARPCOD = "CDG";
	private static final String TRANSACTIONTYPE="uld.defaults.TxnType";
	private static final String AGREEMENTSTATUS="uld.defaults.agreementstatus";
	private static final String PARTYTYPE="uld.defaults.PartyType";
    FlightFilterMessageVO uldFlightMessageFilterVO = new FlightFilterMessageVO();
    Collection<String> consolidatedULDs;
    Collection<UldManifestVO> manifestedVOs;
    ArrayList<ULDFlightMessageReconcileVO> uldFlightMessageReconcileVOs;
    ULDFlightMessageReconcileVO uldFlightMessageReconcileVO;
    Collection<ULDFlightMessageReconcileDetailsVO> reconcileDetailsVOs;
    ULDFlightMessageReconcileDetailsVO reconcileDetailsVO;
    //ULDCountFeature uldCountFeature;
    ULDFlightMessageReconcileVO ucmCountVO;
    UldManifestVO uldManifestVO;
    LUCMessageFeature lucMessageFeature;
    ULDDiscrepancyVO uldDiscrepancyVO;
    Collection<ULDDiscrepancyVO> uldDiscrepancyVOs;
    Collection<ULDSCMReconcileDetailsVO> uldDetails;
    ULDSCMReconcileDetailsVO uldSCMReconcileDetailsVO;
    ULD uld;
    ULDPK uldPk;
    ULDSCMReconcileDetails uldSCMReconcileDetails;
    ULDSCMReconcileDetailsPK reconcileDetailsPK; 
    ULDMovementVO uldMovementVO;
    Collection<ULDMovementVO> uldMovement;
    Collection<String> uldNOs;
    Collection<ULDMovementVO> uldMovementVos;
    List<ULDMovementVO> uldMovementVOsList;
    Collection<ULDDiscrepancy> uldDiscrepancies;
    ULDDiscrepancy uldDiscrepancy;
    AirlineValidationVO airlineValidationVO;
    Collection<String> systemParameterCodes;
    Collection<String> systemParameterCodess;

    Map<String, String> systemParameterMap = new HashMap<String, String>();
    Map<String, String> systemParameterMaps = new HashMap<String, String>();
    ULDChargingInvoiceVO uldChargingInvoiceVO;
    ULDTransaction uLDTransaction;
    ULDChargingInvoice uLDChargingInvoice;
    ULDChargingInvoicePK uldChargingInvoicePK;
      
    @Override
    public void setup() throws Exception {

        uldController = mockBean("ULDController", ULDController.class);
        uldContrller = new ULDController();
        ULDDefaultsMockDAO = mockBean("ULDDefaultsSqlDAO", ULDDefaultsSqlDAO.class);
        uldDefaultsSqlDAO = mock(ULDDefaultsSqlDAO.class);
        sharedAirlineProxy = mockProxy(SharedAirlineProductProxy.class);
        airlineValidationVO = new AirlineValidationVO();
        EntityManagerMock.mockEntityManager();
		ReportUtilInstanceMock.mockReportUtilInstance();
        scmReminderNotificationsFeature = mockBean("uld.defaults.scmRemainderNotificationsFeature", SCMReminderNotificationsFeature.class);
        uldTransaction = spy(new ULDTransaction());
        operationsFltHandlingProxy = mockProxy(OperationsFltHandlingProxy.class);
        uldFlightMessageReconcileMock = mock(ULDFlightMessageReconcile.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
        uldFlightMessageReconcileVO = new ULDFlightMessageReconcileVO();
        uldFlightMessageReconcileVOs = new ArrayList<ULDFlightMessageReconcileVO>();
        uldDiscrepancyVOs = new ArrayList<>();
        uldDetails = new ArrayList<>();
        manifestedVOs = new ArrayList<>();
        uldDiscrepancyVO = new ULDDiscrepancyVO();
        consolidatedULDs = new ArrayList<>();
        uldManifestVO = new UldManifestVO();
        manifestedVOs.add(uldManifestVO);
        reconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
        reconcileDetailsVOs = new ArrayList<>();
        reconcileDetailsVOs.add(reconcileDetailsVO);
        uldFlightMessageReconcileVOs.add(uldFlightMessageReconcileVO);
        uldSCMReconcileDetailsVO = new ULDSCMReconcileDetailsVO();
        systemParamterMap = new HashMap<>();
//		reconcileDetailsVO.setUldStatus("S");
//		reconcileDetailsVO.setUldNumber("AKE1234EK");
//		reconcileDetailsVO.setCarrierCode("SQ");
//		reconcileDetailsVOs.add(reconcileDetailsVO);
//		
//		uldFlightMessageReconcileVO.setReconcileDetailsVOs(reconcileDetailsVOs);
        uldCountFeature = mockBean("ULDCountFeature", ULDCountFeature.class);
        demurrageFeature = mockBean("SaveReturnTransactionFeature", SaveReturnTransactionFeature.class);
        updateULDDemurrageDetailsFeature = mockBean("UpdateULDDemurrageDetailsFeature", UpdateULDDemurrageDetailsFeature.class);
        saveULDLoanTransactionFeature = mockBean("SaveULDLoanTransactionFeature", SaveULDLoanTransactionFeature.class);
        transactionListVO = new TransactionListVO();
        uldTransactionDetailVO = new ULDTransactionDetailsVO();
        uldTransactionDetailsVOs = new  ArrayList<>();
        lucMessageFeature =  mockBean("LUCMessageFeature", LUCMessageFeature.class);
        uld = new ULD();
        uldPk = new ULDPK();
        uldSCMReconcileDetails = new ULDSCMReconcileDetails();
       uldSCMReconcileDetails  = spy(new ULDSCMReconcileDetails());
       reconcileDetailsPK = new ULDSCMReconcileDetailsPK();

       uldMovementVO = new ULDMovementVO();
       uldNOs = new ArrayList<>();
       uldMovementVos = new ArrayList<>();
       uldMovementVOsList = new ArrayList<>();
       uldMovement = new ArrayList<>(); 
       systemParameterCodes = new ArrayList<>();
       systemParameterCodess = new ArrayList<>();
       systemParameterMap = new HashMap<String, String>();
       systemParameterMaps = new HashMap<String, String>();
       uldChargingInvoiceVO = new ULDChargingInvoiceVO();
       uLDTransaction = new ULDTransaction();
       uLDChargingInvoice =new ULDChargingInvoice(); 
       uldChargingInvoicePK = new ULDChargingInvoicePK();
    }

    private FlightDetailsVO populateFlightDetails() {
        flightDetailsVOs = new ArrayList<>();
        FlightDetailsVO flightDetailsVOFirst = new FlightDetailsVO();
        flightDetailsVOFirst.setCompanyCode("AV");
        flightDetailsVOs.add(flightDetailsVOFirst);
        FlightDetailsVO flightDetailsVOSecond = new FlightDetailsVO();
        flightDetailsVOSecond.setCompanyCode("AV");
        flightDetailsVOs.add(flightDetailsVOSecond);
        return flightDetailsVOFirst;
    }
    /*=Test cases for the addition of method updateULDsForOperations(flightDetailsVOs) as part of UpdateULDForOperationsChannelMapper starts=*/

    /**
     * Here we have two flightdetail records naming flightDetailsVOFirst and
     * flightDetailsVOSecond. As per updateULDsForOperations(flightDetailsVOs)
     * in uldController, these records have been iterated and
     * updateULDForOperations(flightDetailsVO) is being called twice. so in
     * verify we are checking whether updateULDForOperations() have been called
     * 2 times.
     */
    @Test
    public void shouldIterateAndInvoke_UpdateULDForOperations_ForEachFlightDetail_When_MultipleFlightDetailRecordsArePresent()
            throws Exception {
        populateFlightDetails();
        doNothing().when(uldController).updateULDForOperations(any(FlightDetailsVO.class));
        uldContrller.updateULDsForOperations(flightDetailsVOs);
        verify(uldController, times(2)).updateULDForOperations(any());
    }

    @Test(expected = NoClassDefFoundError.class)
    public void shouldSetMissingWhenSCMValidationVOisNonNull()
            throws Exception {
        SharedDefaultsProxy sharedDefaultsProxy;
        Collection<String> systemParameterCodes;
        SCMValidationVO scmValidationVO = new SCMValidationVO();
        Collection<SCMValidationVO> scmValidationVOs = new ArrayList<>();
        ;
        Map parameterMap = new HashMap();
        ReportSpec reportSpec = new ReportSpec();
        SCMValidationFilterVO scmValidationFilterVO = new SCMValidationFilterVO();
        scmValidationFilterVO.setCompanyCode("DNAE");
        scmValidationFilterVO.setAirportCode("DXB");
        scmValidationFilterVO.setAirlineIdentifier(1176);
        reportSpec.addFilterValue(scmValidationFilterVO);
        EntityManagerMock.mockEntityManager();
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(scmValidationVOs).when(uldDefaultsSqlDAO).findSCMValidationListColl(scmValidationFilterVO);
        scmValidationVO.setAirportCode("DXB");
        scmValidationVO.setCompanyCode("DNAE");
        scmValidationVO.setNotSighted("63");
        scmValidationVO.setTotal("100");
        scmValidationVOs.add(scmValidationVO);
        sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
        systemParameterCodes = new ArrayList<>();
        systemParameterCodes.add("uld.defaults.displaydiscrepancyonstockcheck");
        doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
        uldContrller.printSCMValidationReport(reportSpec);
        verify(uldContrller, times(1)).printSCMValidationReport(any());

    }


    @Test(expected = ULDDefaultsBusinessException.class)
    public void shouldThrowULDDefaultsBusinessException_When_DimensionCoversionFailesOnUpdateULDForOperations_ForMultipleFlightDetailRecords()
            throws Exception {
        populateFlightDetails();
        doThrow(DimensionConversionException.class).when(uldController).updateULDForOperations(any(FlightDetailsVO.class));
        uldContrller.updateULDsForOperations(flightDetailsVOs);
    }

    @Test(expected = ULDDefaultsBusinessException.class)
    public void shouldThrowULDDefaultsBusinessException_When_CurrencyCoversionFailesOnUpdateULDForOperations_ForMultipleFlightDetailRecords()
            throws Exception {
        populateFlightDetails();
        doThrow(CurrencyConversionException.class).when(uldController).updateULDForOperations(any(FlightDetailsVO.class));
        uldContrller.updateULDsForOperations(flightDetailsVOs);
    }

    @Test(expected = ULDDefaultsBusinessException.class)
    public void shouldThrowULDDefaultsBusinessException_When_MessageConfigurationFailesOnUpdateULDForOperations_ForMultipleFlightDetailRecords()
            throws Exception {
        populateFlightDetails();
        doThrow(MessageConfigException.class).when(uldController).updateULDForOperations(any(FlightDetailsVO.class));
        uldContrller.updateULDsForOperations(flightDetailsVOs);
    }

    /*=Test cases for the addition of method updateULDsForOperations(flightDetailsVOs) as part of UpdateULDForOperationsChannel ends=*/
    @Test
    public void shouldInvoke_findConsolidatedUCMsForFlight()
            throws Exception {
        FlightFilterMessageVO uldFlightMessageFilterVO = new FlightFilterMessageVO();
        uldController.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
        verify(uldController, times(1)).findConsolidatedUCMsForFlight(any());
    }

    @Test
    public void shouldInvokeUpdateUCMULDDetailsWhenOperationFlagIsD()
            throws Exception {
        ULDFlightMessageReconcileDetailsVO uLDFlightMessageReconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
        ULDFlightMessageReconcile uLDFlightMessageReconcile = new ULDFlightMessageReconcile();
        ULDFlightMessageReconcileDetails uLDFlightMessageReconcileDetails = new ULDFlightMessageReconcileDetails();
        uLDFlightMessageReconcileDetailsVO.setSequenceNumber("1");
        uLDFlightMessageReconcileDetailsVO.setCompanyCode("ABC");
        uLDFlightMessageReconcileDetailsVO.setAirportCode("CDG");
        uLDFlightMessageReconcileDetailsVO.setFlightCarrierIdentifier(1);
        uLDFlightMessageReconcileDetailsVO.setFlightNumber("9330");
        uLDFlightMessageReconcileDetailsVO.setFlightSequenceNumber(1);
        uLDFlightMessageReconcileDetailsVO.setMessageType("UCM");
        uLDFlightMessageReconcileDetailsVO.setUldNumber("AKE1111AV");
        uLDFlightMessageReconcileDetailsVO.setOperationFlag("D");
        Collection<ULDFlightMessageReconcileDetailsVO> uldDetails = new ArrayList<>();
        uldDetails.add(uLDFlightMessageReconcileDetailsVO);
        Set<ULDFlightMessageReconcileDetails> reconcileDetails = new HashSet<>();
        ULDFlightMessageReconcileDetailsPK uLDFlightMessageReconcileDetailsPK = new ULDFlightMessageReconcileDetailsPK();
        uLDFlightMessageReconcileDetailsPK.setSequenceNumber(1);
        uLDFlightMessageReconcileDetailsPK.setUldNumber("AKE1111AV");
        uLDFlightMessageReconcileDetails.setDetailsPK(uLDFlightMessageReconcileDetailsPK);
        reconcileDetails.add(uLDFlightMessageReconcileDetails);
        uLDFlightMessageReconcile.setReconcileDetails(reconcileDetails);
        doReturn(uLDFlightMessageReconcile).when(PersistenceController.getEntityManager()).find(eq(ULDFlightMessageReconcile.class),
                any(ULDFlightMessageReconcileDetailsPK.class));
        uldContrller.updateUCMULDDetails(uldDetails);
    }

    @Test
    public void shouldInvokeUpdateUCMULDDetailsWhenOperationFlagIsU()
            throws Exception {
        ULDFlightMessageReconcileDetailsVO uLDFlightMessageReconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
        ULDFlightMessageReconcile uLDFlightMessageReconcile = new ULDFlightMessageReconcile();
        ULDFlightMessageReconcileDetails uLDFlightMessageReconcileDetails = new ULDFlightMessageReconcileDetails();
        uLDFlightMessageReconcileDetailsVO.setSequenceNumber("1");
        uLDFlightMessageReconcileDetailsVO.setCompanyCode("ABC");
        uLDFlightMessageReconcileDetailsVO.setAirportCode("CDG");
        uLDFlightMessageReconcileDetailsVO.setFlightCarrierIdentifier(1);
        uLDFlightMessageReconcileDetailsVO.setFlightNumber("9330");
        uLDFlightMessageReconcileDetailsVO.setFlightSequenceNumber(1);
        uLDFlightMessageReconcileDetailsVO.setMessageType("UCM");
        uLDFlightMessageReconcileDetailsVO.setUldNumber("AKE1111AV");
        uLDFlightMessageReconcileDetailsVO.setOperationFlag("U");
        Collection<ULDFlightMessageReconcileDetailsVO> uldDetails = new ArrayList<>();
        uldDetails.add(uLDFlightMessageReconcileDetailsVO);
        Set<ULDFlightMessageReconcileDetails> reconcileDetails = new HashSet<>();
        ULDFlightMessageReconcileDetailsPK uLDFlightMessageReconcileDetailsPK = new ULDFlightMessageReconcileDetailsPK();
        uLDFlightMessageReconcileDetailsPK.setSequenceNumber(1);
        uLDFlightMessageReconcileDetailsPK.setUldNumber("AKE1111AV");
        uLDFlightMessageReconcileDetails.setDetailsPK(uLDFlightMessageReconcileDetailsPK);
        reconcileDetails.add(uLDFlightMessageReconcileDetails);
        uLDFlightMessageReconcile.setReconcileDetails(reconcileDetails);
        doReturn(uLDFlightMessageReconcile).when(PersistenceController.getEntityManager()).find(eq(ULDFlightMessageReconcile.class),
                any(ULDFlightMessageReconcileDetailsPK.class));
        uldContrller.updateUCMULDDetails(uldDetails);
    }

    @Test
    public void shouldNotPerformWhenSequenceNumberIsNotEqual()
            throws Exception {
        ULDFlightMessageReconcileDetailsVO uLDFlightMessageReconcileDetailsVO = new ULDFlightMessageReconcileDetailsVO();
        ULDFlightMessageReconcile uLDFlightMessageReconcile = new ULDFlightMessageReconcile();
        ULDFlightMessageReconcileDetails uLDFlightMessageReconcileDetails = new ULDFlightMessageReconcileDetails();
        uLDFlightMessageReconcileDetailsVO.setSequenceNumber("2");
        uLDFlightMessageReconcileDetailsVO.setCompanyCode("ABC");
        uLDFlightMessageReconcileDetailsVO.setAirportCode("CDG");
        uLDFlightMessageReconcileDetailsVO.setFlightCarrierIdentifier(1);
        uLDFlightMessageReconcileDetailsVO.setFlightNumber("9330");
        uLDFlightMessageReconcileDetailsVO.setFlightSequenceNumber(1);
        uLDFlightMessageReconcileDetailsVO.setMessageType("UCM");
        uLDFlightMessageReconcileDetailsVO.setUldNumber("AKE1112AV");
        Collection<ULDFlightMessageReconcileDetailsVO> uldDetails = new ArrayList<>();
        uldDetails.add(uLDFlightMessageReconcileDetailsVO);
        Set<ULDFlightMessageReconcileDetails> reconcileDetails = new HashSet<>();
        ULDFlightMessageReconcileDetailsPK uLDFlightMessageReconcileDetailsPK = new ULDFlightMessageReconcileDetailsPK();
        uLDFlightMessageReconcileDetailsPK.setSequenceNumber(1);
        uLDFlightMessageReconcileDetailsPK.setUldNumber("AKE1111AV");
        uLDFlightMessageReconcileDetails.setDetailsPK(uLDFlightMessageReconcileDetailsPK);
        reconcileDetails.add(uLDFlightMessageReconcileDetails);
        uLDFlightMessageReconcile.setReconcileDetails(reconcileDetails);
        doReturn(uLDFlightMessageReconcile).when(PersistenceController.getEntityManager()).find(eq(ULDFlightMessageReconcile.class),
                any(ULDFlightMessageReconcileDetailsPK.class));
        uldContrller.updateUCMULDDetails(uldDetails);

    }

    @Test(expected = ULDDefaultsBusinessException.class)
    public void printExternalMovementsReportTest() throws BusinessException, SystemException, RemoteException, PersistenceException {
        ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
        Page<ULDMovementDetailVO> uldmovementDetails = new Page<ULDMovementDetailVO>
                (new ArrayList<ULDMovementDetailVO>(), 0, 0, 0, 0, 0, false);
        uldMovementFilterVO.setCompanyCode("AV");
        uldMovementFilterVO.setUldNumber("AKE5675AA");
        uldMovementFilterVO.setFromDate(new LocalDate("CDG", Location.ARP, true));
        uldMovementFilterVO.setToDate(new LocalDate("DXB", Location.ARP, true));
        ReportSpec reportSpec = new ReportSpec();
        reportSpec.addFilterValue(uldMovementFilterVO);
        doReturn(ULDDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        ULDNumberVO uLDNumberVO = new ULDNumberVO();
        doReturn(uLDNumberVO).when(ULDDefaultsMockDAO).findULDHistoryCounts(uldMovementFilterVO);
        doReturn(uldmovementDetails).when(ULDDefaultsMockDAO).findULDMovementHistory(uldMovementFilterVO, 0);
        doThrow(ULDDefaultsBusinessException.class).when(uldController).printExternalMovementsReport(reportSpec);
        uldContrller.printExternalMovementsReport(reportSpec);
    }

    @Test(expected = ULDDefaultsBusinessException.class)
    public void printExternalMovementsReportTestWithUldMovementDetailsNull() throws BusinessException, SystemException, RemoteException, PersistenceException {
        ULDMovementFilterVO uldMovementFilterVO = new ULDMovementFilterVO();
        Page<ULDMovementDetailVO> uldmovementDetails = null;
        uldMovementFilterVO.setCompanyCode("AV");
        uldMovementFilterVO.setUldNumber("AKE5675AA");
        uldMovementFilterVO.setFromDate(new LocalDate("CDG", Location.ARP, true));
        uldMovementFilterVO.setToDate(new LocalDate("DXB", Location.ARP, true));
        ReportSpec reportSpec = new ReportSpec();
        reportSpec.addFilterValue(uldMovementFilterVO);
        doReturn(ULDDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        ULDNumberVO uLDNumberVO = new ULDNumberVO();
        doReturn(uLDNumberVO).when(ULDDefaultsMockDAO).findULDHistoryCounts(uldMovementFilterVO);
        doReturn(uldmovementDetails).when(ULDDefaultsMockDAO).findULDMovementHistory(uldMovementFilterVO, 0);
        doThrow(ULDDefaultsBusinessException.class).when(uldController).printExternalMovementsReport(reportSpec);
        uldContrller.printExternalMovementsReport(reportSpec);
    }

    @Test
    public void shouldReturnCollectionofAirportsForSCMMissingJob() throws PersistenceException, SystemException {
        String companyCode = "SQ";
        Collection<String> arpgrp = new ArrayList<>();
        arpgrp.add("TEST");
        arpgrp.add("TEST1");
        String days = "7";
        doReturn(ULDDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(arpgrp).when(ULDDefaultsMockDAO).findAirportsforSCMJob(companyCode, arpgrp, days);
        uldContrller.findAirportsforSCMJob(companyCode, arpgrp, days);
        verify(ULDDefaultsMockDAO, times(1)).findAirportsforSCMJob(companyCode, arpgrp, days);
    }

    @Test
    public void checkIfFeatureCallable() throws BusinessException, SystemException {
        Collection<String> airports = new ArrayList<>();
        airports.add("SIN");
        airports.add("DXB");
        uldContrller.sendSCMReminderNotifications(airports);
        verify(scmReminderNotificationsFeature, times(2)).execute(any());
    }

    @Test
    public void shouldInvokeprocessLUCMessage() throws RemoteException, SystemException, PersistenceException, BusinessException {
        LUCMessageVO lucMessageVO = new LUCMessageVO();
        LUCULDDetailsVO uldDetailsVO = new LUCULDDetailsVO();
        Collection<LUCULDDetailsVO> uldDetails = new ArrayList<>();
        LUCULDIdentificationVO identificationVo = new LUCULDIdentificationVO();
        identificationVo.setUldOwnerCode("SQ");
        identificationVo.setUldType("PMC");
        identificationVo.setUldSerialNumber("12345");
        uldDetailsVO.setUldIdentificationVO(identificationVo);
        uldDetailsVO.setTransactionDate(new LocalDate("SIN", Location.ARP, true));
        uldDetailsVO.setLocationOftransfer("SIN");
        uldDetailsVO.setDateofTransfer("02DEC2021");
        LUCTransferringPartyDetailsVO transferPartyVO = new LUCTransferringPartyDetailsVO();
        transferPartyVO.setCarrierCode("SQ");
        uldDetailsVO.setTransferringPartyDetailsVO(transferPartyVO);
        uldDetailsVO.setUldReceiptNumber("012-300");
        LUCReceivingPartyDetailsVO receivingPartyVO = new LUCReceivingPartyDetailsVO();
        receivingPartyVO.setCarrierCode("EK");
        uldDetailsVO.setReceivingPartyDetailsVO(receivingPartyVO);
        lucMessageVO.setCompanyCode("SQ");
        uldDetails.add(uldDetailsVO);
        lucMessageVO.setUldDetails(uldDetails);
        lucMessageVO.setStationCode("SIN");
        uldDetailsVO.setUldConditionDetailsVO(new LUCULDConditionDetailsVO());
        EntityManagerMock.mockEntityManager();
        SharedAirlineProxy airlineproxy = mockProxy(SharedAirlineProxy.class);
        AirlineValidationVO airlineValidationVO = new AirlineValidationVO();
        airlineValidationVO.setAirlineIdentifier(1176);
        airlineValidationVO.setNumericCode("012");
        doReturn(airlineValidationVO).when(airlineproxy).validateAlphaCode(any(), any());
        AirlineCache airlineCache = mock(AirlineCache.class);
        doReturn(airlineCache).when(CacheFactory.getInstance()).getCache(
                AirlineCache.ENTITY_NAME);
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(null).when(uldDefaultsSqlDAO).findLastTransactionsForUld(any());
        ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
        doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
        Collection<ULDTransaction> uldTransactionDetails = new ArrayList<>();
        doReturn(uldTransactionDetails).when(uldObjectDAO).findOpenTxnULDObjects(any());
        doReturn(true).when(uldController).validateULDFormat(any(), any());
        doReturn("SQ").when(airlineproxy).validateOwnerCode(any(), any(), any());
        SharedULDProxy uldProxy = mockProxy(SharedULDProxy.class);
        Map<String, ULDTypeValidationVO> map = new HashMap<String, ULDTypeValidationVO>();
        map.put("PMC", new ULDTypeValidationVO());
        doReturn(map).when(uldProxy).validateULDTypeCodes(any(), any());
        ULDTypeVO typeVo = new ULDTypeVO();
        List<ULDTypeVO> typeVos = new ArrayList<>();
        typeVos.add(typeVo);
        MsgBrokerMessageProxy msgbroker = mockProxy(MsgBrokerMessageProxy.class);
        Collection<MessageVO> messageVOs = new ArrayList<>();
        doReturn(messageVOs).when(msgbroker).encodeAndSaveMessage(any());
        doReturn(typeVos).when(uldProxy).findULDTypes(any());
        SharedDefaultsProxy sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
        Map parameterMap = new HashMap();
        parameterMap.put("uld.default.transactioncreationbasedon", "ULDCARCOD");
        parameterMap.put("system.defaults.unit.currency", "USD");
        parameterMap.put("uld.defaults.exchangeratepriorityorder", "4,2");
        doReturn(parameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(any());
        doReturn(null).when(uldProxy).findULDTareWeight(any());
        doNothing().when(AuditUtilsInstance.getInstance()).performAudit(any(ULDConfigAuditVO.class));
        uldContrller.processLUCMessage(lucMessageVO);
        verify(uldDefaultsSqlDAO, times(0)).checkULDInUse(any(), any());
    }

    @Test
    public void shouldExecuteSaveULDTransactionForUpdateWhenLeaseEndDateIsCaptured() throws ULDDefaultsBusinessException, CurrencyConversionException, DimensionConversionException, MessageConfigException, SystemException, FinderException, PersistenceException {
        ULDTransaction uldTransn = new ULDTransaction();
        ULDTransactionPK uldTransactionPK = new ULDTransactionPK();
        uldTransactionPK.setCompanyCode("DNAE");
        uldTransactionPK.setUldNumber("AKE41457AV");
        uldTransn.setUldTransactionPK(uldTransactionPK);
        TransactionVO transactionVO = new TransactionVO();
        transactionVO.setOperationalFlag("U");
        ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
        uldTransactionDetailsVO.setUldNumber("AKE12457AV");
        uldTransactionDetailsVO.setCompanyCode("DNAE");
        uldTransactionDetailsVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
        uldTransactionDetailsVO.setLeaseEndDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
        Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
        uldTransactionDetailsVOs.add(uldTransactionDetailsVO);
        transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
        doReturn(uldTransn).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),
                any(ULDTransactionPK.class));
        doNothing().when(AuditUtilsInstance.getInstance()).performAudit(any(ULDConfigAuditVO.class));
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(false).when(uldDefaultsSqlDAO).checkforPoolOwner(any());
        doReturn(1176).when(uldController).getAirline1(any());
        Collection<ErrorVO> errors = uldContrller.saveULDTransaction(transactionVO);
        assertTrue(Objects.isNull(errors));
    }

    /**
     * Method		:	ULDControllerTest.shouldExecutefindConsolidatedUCMsForFlight
     * Added on 	:	05-Jul-2022
     * Used for 	:   For execute findConsolidatedUCMsForFlight
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws FinderException
     * Parameters	:	@throws PersistenceException
     * Parameters	:	@throws BusinessException
     * Return type	: 	void
     */
    @Test
    public void shouldExecutefindConsolidatedUCMsForFlight() throws SystemException, PersistenceException, BusinessException {

        uldFlightMessageFilterVO.setCompanyCode(CMPCOD);
        uldFlightMessageFilterVO.setFlightCarrierId(FLTIDR);
        uldFlightMessageFilterVO.setFlightNumber(FLTNO);
        uldFlightMessageFilterVO.setFlightSequenceNumber(FLTSEQNUM);
        uldFlightMessageFilterVO.setAirportCode(ARPCOD);
        doReturn(manifestedVOs).when(operationsFltHandlingProxy).findManifestedUlds(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).uldManifestVOToUldFlightMessageReconcileDetailVOs(manifestedVOs, uldFlightMessageFilterVO, consolidatedULDs);
        EntityManagerMock.mockEntityManager();
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldFlightMessageReconcileVOs).when(uldDefaultsSqlDAO).findlatestUCMsFromAllSources(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).getUCMCount(any());
        uldContrller.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
    }

    /**
     * Method		:	ULDControllerTest.shouldThrowProxyException
     * Added on 	:	07-Jul-2022
     * Used for 	:	Should Throw Exception
     * Parameters	:	@throws BusinessException
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws PersistenceException
     * Return type	: 	void
     */
    @Test
    public void shouldThrowProxyException() throws BusinessException, SystemException, PersistenceException {

        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldFlightMessageReconcileVOs).when(uldDefaultsSqlDAO).findlatestUCMsFromAllSources(any());
        doThrow(new ProxyException("", null)).when(operationsFltHandlingProxy).findManifestedUlds(any());
        uldContrller.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
    }

    /**
     * Method		:	ULDControllerTest.shouldThrowBusinessException
     * Added on 	:	07-Jul-2022
     * Used for 	:	Should Throw Exception
     * Parameters	:	@throws BusinessException
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws PersistenceException
     * Return type	: 	void
     */
    @Test
    public void shouldThrowBusinessException() throws BusinessException, SystemException, PersistenceException {

        BusinessException businessException = new BusinessException() {
        };
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldFlightMessageReconcileVOs).when(uldDefaultsSqlDAO).findlatestUCMsFromAllSources(any());
        doThrow(businessException).when(uldCountFeature).execute(any());
        uldContrller.getUCMCount(uldFlightMessageReconcileVO);
    }

    /**
     * Method		:	ULDControllerTest.ShouldReturnManifestedVO
     * Added on 	:	26-Jul-2022
     * Used for 	:	Should return ManifestedVO
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws PersistenceException
     * Parameters	:	@throws BusinessException
     * Return type	: 	void
     */
    @Test
    public void shouldReturnManifestedVO() throws SystemException, PersistenceException, BusinessException {

        uldFlightMessageFilterVO.setCompanyCode(CMPCOD);
        uldFlightMessageFilterVO.setFlightCarrierId(FLTIDR);
        uldFlightMessageFilterVO.setFlightNumber(FLTNO);
        uldFlightMessageFilterVO.setFlightSequenceNumber(FLTSEQNUM);
        uldFlightMessageFilterVO.setAirportCode(ARPCOD);
        doReturn(manifestedVOs).when(operationsFltHandlingProxy).findManifestedUlds(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).uldManifestVOToUldFlightMessageReconcileDetailVOs(manifestedVOs, uldFlightMessageFilterVO, consolidatedULDs);
        EntityManagerMock.mockEntityManager();
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldFlightMessageReconcileVOs).when(uldDefaultsSqlDAO).findlatestUCMsFromAllSources(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).getUCMCount(any());
        uldContrller.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
        assertTrue(Objects.nonNull(ContextUtils.getTxBusinessParameter(ULDCountFeatureConstants.MANIFEST)));

    }

    /**
     * Method		:	ULDControllerTest.ShouldNotReturnManifestedVO
     * Added on 	:	26-Jul-2022
     * Used for 	:	Should not return ManifestedVO
     * Parameters	:	@throws SystemException
     * Parameters	:	@throws PersistenceException
     * Parameters	:	@throws BusinessException
     * Return type	: 	void
     */
    @Test
    public void shouldNotReturnManifestedVOwhenExceptionIsThrown() throws SystemException, PersistenceException, BusinessException {

        uldFlightMessageFilterVO.setCompanyCode(CMPCOD);
        uldFlightMessageFilterVO.setFlightCarrierId(FLTIDR);
        uldFlightMessageFilterVO.setFlightNumber(FLTNO);
        uldFlightMessageFilterVO.setFlightSequenceNumber(FLTSEQNUM);
        uldFlightMessageFilterVO.setAirportCode(ARPCOD);
        doThrow(new ProxyException(new SystemException("error"))).when(operationsFltHandlingProxy)
                .findManifestedUlds(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).uldManifestVOToUldFlightMessageReconcileDetailVOs(manifestedVOs, uldFlightMessageFilterVO, consolidatedULDs);
        EntityManagerMock.mockEntityManager();
        doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
        doReturn(uldFlightMessageReconcileVOs).when(uldDefaultsSqlDAO).findlatestUCMsFromAllSources(any());
        doReturn(uldFlightMessageReconcileVO).when(uldController).getUCMCount(any());
        uldContrller.findConsolidatedUCMsForFlight(uldFlightMessageFilterVO);
        assertTrue(Objects.isNull(ContextUtils.getTxBusinessParameter(ULDCountFeatureConstants.MANIFEST)));


    }

    @Test(expected = SystemException.class)
    public void generateUCMMessageTest() throws SystemException, ProxyException, ULDDefaultsBusinessException {
        ULDController uldController = new ULDController();
        MsgBrokerMessageProxy msgBrokerMessageProxy = mockProxy(MsgBrokerMessageProxy.class);
        Collection<MessageVO> messageVOs = Arrays.asList(new MessageVO(), new MessageVO());
        Collection<UCMMessageVO> UCMMessageVOs = Arrays.asList(new UCMMessageVO(), new UCMMessageVO());

        doReturn(messageVOs).when(msgBrokerMessageProxy).encodeAndSaveMessage(anyObject());
        uldController.generateUCMMessage(UCMMessageVOs);

        assertEquals(2, messageVOs.size());

        doThrow(new ProxyException(new SystemException("error"))).when(msgBrokerMessageProxy).encodeAndSaveMessage(anyObject());
        uldController.generateUCMMessage(UCMMessageVOs);
    }
	@Test
	public void printListULDAgreementReportTest()
			throws BusinessException, SystemException, RemoteException, PersistenceException {
		Collection<ULDAgreementVO> uldAgreementVOs = new ArrayList<>();
		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();
		ReportSpec reportSpec = new ReportSpec();
		uldAgreementFilterVO.setCompanyCode(CMPCOD);
		uldAgreementFilterVO.setPageNumber(1);
		reportSpec.addFilterValue(uldAgreementFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = new HashMap<>();
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add(TRANSACTIONTYPE);
		oneTimeActiveStatusList.add(AGREEMENTSTATUS);
		oneTimeActiveStatusList.add(PARTYTYPE);
		Collection<OneTimeVO> transactionTypes = new ArrayList<>();
		Collection<OneTimeVO> partyTypes = new ArrayList<>();
		Collection<OneTimeVO> status = new ArrayList<>();
		OneTimeVO oneTimeVO1 = new OneTimeVO();
		OneTimeVO oneTimeVO2 = new OneTimeVO();
		OneTimeVO oneTimeVO3 = new OneTimeVO();
		oneTimeVO1.setFieldValue("L");
		oneTimeVO2.setFieldValue("A");
		oneTimeVO3.setFieldValue("I");
		transactionTypes.add(oneTimeVO1);
		partyTypes.add(oneTimeVO2);
		status.add(oneTimeVO3);
		oneTimeHashMap.put(TRANSACTIONTYPE, transactionTypes);
		oneTimeHashMap.put(AGREEMENTSTATUS, partyTypes);
		oneTimeHashMap.put(PARTYTYPE, status);
		ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
		uldAgreementVO.setCompanyCode(CMPCOD);
		uldAgreementVO.setAgreementFromDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setAgreementToDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setLastUpdatedTime(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setTxnType("L");
		uldAgreementVO.setPartyType("A");
		uldAgreementVO.setAgreementStatus("I");
		uldAgreementVO.setFromPartyType("A");
		uldAgreementVOs.add(uldAgreementVO);
		reportSpec.addFilterValue(uldAgreementVOs);
		byte[] reportDatainBytes = new byte[10];
		doReturn(uldAgreementVOs).when(uldController).listULDAgreementsColl(uldAgreementFilterVO);
		EntityManagerMock.mockEntityManager();
		doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
		doReturn(uldAgreementVOs).when(uldDefaultsSqlDAO).listULDAgreementsColl(any());
		doReturn(oneTimeHashMap).when(sharedDefaultsProxy).findOneTimeValues(uldAgreementFilterVO.getCompanyCode(),
				oneTimeActiveStatusList);
		doReturn(reportDatainBytes).when(ReportUtilInstance.getIstance()).exportReport(any());
		uldContrller.printListULDAgreement(reportSpec);
	}

	@Test
	public void printListULDAgreementReportWithoutPartyTypesTest()
			throws BusinessException, SystemException, RemoteException, PersistenceException {
		Collection<ULDAgreementVO> uldAgreementVOs = new ArrayList<>();
		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();
		ReportSpec reportSpec = new ReportSpec();
		uldAgreementFilterVO.setCompanyCode(CMPCOD);
		uldAgreementFilterVO.setPageNumber(1);
		reportSpec.addFilterValue(uldAgreementFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = new HashMap<>();
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add(TRANSACTIONTYPE);
		oneTimeActiveStatusList.add(AGREEMENTSTATUS);
		oneTimeActiveStatusList.add(PARTYTYPE);
		Collection<OneTimeVO> transactionTypes = new ArrayList<>();
		Collection<OneTimeVO> partyTypes = new ArrayList<>();
		Collection<OneTimeVO> status = new ArrayList<>();
		OneTimeVO oneTimeVO1 = new OneTimeVO();
		OneTimeVO oneTimeVO2 = new OneTimeVO();
		OneTimeVO oneTimeVO3 = new OneTimeVO();
		oneTimeVO1.setFieldValue("L");
		oneTimeVO2.setFieldValue("A");
		oneTimeVO3.setFieldValue("I");
		transactionTypes.add(oneTimeVO1);
		partyTypes.add(oneTimeVO2);
		status.add(oneTimeVO3);
		oneTimeHashMap.put(TRANSACTIONTYPE, transactionTypes);
		oneTimeHashMap.put(AGREEMENTSTATUS, partyTypes);
		oneTimeHashMap.put(PARTYTYPE, status);
		ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
		uldAgreementVO.setCompanyCode(CMPCOD);
		uldAgreementVO.setAgreementFromDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setAgreementToDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setLastUpdatedTime(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setTxnType("L");
		uldAgreementVO.setPartyType("A");
		uldAgreementVO.setAgreementStatus("I");
		uldAgreementVOs.add(uldAgreementVO);
		reportSpec.addFilterValue(uldAgreementVOs);
		byte[] reportDatainBytes = new byte[10];
		doReturn(uldAgreementVOs).when(uldController).listULDAgreementsColl(uldAgreementFilterVO);
		EntityManagerMock.mockEntityManager();
		doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
		doReturn(uldAgreementVOs).when(uldDefaultsSqlDAO).listULDAgreementsColl(any());
		doReturn(oneTimeHashMap).when(sharedDefaultsProxy).findOneTimeValues(uldAgreementFilterVO.getCompanyCode(),
				oneTimeActiveStatusList);
		doReturn(reportDatainBytes).when(ReportUtilInstance.getIstance()).exportReport(any());
		uldContrller.printListULDAgreement(reportSpec);
	}
	
	@Test
	public void printListULDAgreementReportDifferentFromPartyTypeTest()
			throws BusinessException, SystemException, RemoteException, PersistenceException {
		Collection<ULDAgreementVO> uldAgreementVOs = new ArrayList<>();
		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();
		ReportSpec reportSpec = new ReportSpec();
		uldAgreementFilterVO.setCompanyCode(CMPCOD);
		uldAgreementFilterVO.setPageNumber(1);
		reportSpec.addFilterValue(uldAgreementFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = new HashMap<>();
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add(TRANSACTIONTYPE);
		oneTimeActiveStatusList.add(AGREEMENTSTATUS);
		oneTimeActiveStatusList.add(PARTYTYPE);
		Collection<OneTimeVO> transactionTypes = new ArrayList<>();
		Collection<OneTimeVO> partyTypes = new ArrayList<>();
		Collection<OneTimeVO> status = new ArrayList<>();
		OneTimeVO oneTimeVO1 = new OneTimeVO();
		OneTimeVO oneTimeVO2 = new OneTimeVO();
		OneTimeVO oneTimeVO3 = new OneTimeVO();
		oneTimeVO1.setFieldValue("L");
		oneTimeVO2.setFieldValue("A");
		oneTimeVO3.setFieldValue("I");
		transactionTypes.add(oneTimeVO1);
		partyTypes.add(oneTimeVO2);
		status.add(oneTimeVO3);
		oneTimeHashMap.put(TRANSACTIONTYPE, transactionTypes);
		oneTimeHashMap.put(AGREEMENTSTATUS, partyTypes);
		oneTimeHashMap.put(PARTYTYPE, status);
		ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
		uldAgreementVO.setCompanyCode(CMPCOD);
		uldAgreementVO.setAgreementFromDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setAgreementToDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setLastUpdatedTime(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setTxnType("L");
		uldAgreementVO.setPartyType("A");
		uldAgreementVO.setFromPartyType("P");
		uldAgreementVO.setAgreementStatus("I");
		uldAgreementVOs.add(uldAgreementVO);
		reportSpec.addFilterValue(uldAgreementVOs);
		byte[] reportDatainBytes = new byte[10];
		doReturn(uldAgreementVOs).when(uldController).listULDAgreementsColl(uldAgreementFilterVO);
		EntityManagerMock.mockEntityManager();
		doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
		doReturn(uldAgreementVOs).when(uldDefaultsSqlDAO).listULDAgreementsColl(any());
		doReturn(oneTimeHashMap).when(sharedDefaultsProxy).findOneTimeValues(uldAgreementFilterVO.getCompanyCode(),
				oneTimeActiveStatusList);
		doReturn(reportDatainBytes).when(ReportUtilInstance.getIstance()).exportReport(any());
		uldContrller.printListULDAgreement(reportSpec);
	}
		@Test
	public void printListULDAgreementReportSameFromPartyTypeTest()
			throws BusinessException, SystemException, RemoteException, PersistenceException {
		Collection<ULDAgreementVO> uldAgreementVOs = new ArrayList<>();
		ULDAgreementFilterVO uldAgreementFilterVO = new ULDAgreementFilterVO();
		ReportSpec reportSpec = new ReportSpec();
		uldAgreementFilterVO.setCompanyCode(CMPCOD);
		uldAgreementFilterVO.setPageNumber(1);
		reportSpec.addFilterValue(uldAgreementFilterVO);
		Map<String, Collection<OneTimeVO>> oneTimeHashMap = new HashMap<>();
		Collection<String> oneTimeActiveStatusList = new ArrayList<>();
		oneTimeActiveStatusList.add(TRANSACTIONTYPE);
		oneTimeActiveStatusList.add(AGREEMENTSTATUS);
		oneTimeActiveStatusList.add(PARTYTYPE);
		Collection<OneTimeVO> transactionTypes = new ArrayList<>();
		Collection<OneTimeVO> partyTypes = new ArrayList<>();
		Collection<OneTimeVO> status = new ArrayList<>();
		OneTimeVO oneTimeVO1 = new OneTimeVO();
		OneTimeVO oneTimeVO2 = new OneTimeVO();
		OneTimeVO oneTimeVO3 = new OneTimeVO();
		oneTimeVO1.setFieldValue("L");
		oneTimeVO2.setFieldValue("A");
		oneTimeVO3.setFieldValue("I");
		transactionTypes.add(oneTimeVO1);
		partyTypes.add(oneTimeVO2);
		status.add(oneTimeVO3);
		oneTimeHashMap.put(TRANSACTIONTYPE, transactionTypes);
		oneTimeHashMap.put(AGREEMENTSTATUS, partyTypes);
		oneTimeHashMap.put(PARTYTYPE, status);
		ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
		uldAgreementVO.setCompanyCode(CMPCOD);
		uldAgreementVO.setAgreementFromDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setAgreementToDate(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setLastUpdatedTime(new LocalDate("LHR", Location.ARP, true));
		uldAgreementVO.setTxnType("L");
		uldAgreementVO.setPartyType("A");
		uldAgreementVO.setFromPartyType("I");
		uldAgreementVO.setAgreementStatus("I");
		uldAgreementVOs.add(uldAgreementVO);
		reportSpec.addFilterValue(uldAgreementVOs);
		byte[] reportDatainBytes = new byte[10];
		doReturn(uldAgreementVOs).when(uldController).listULDAgreementsColl(uldAgreementFilterVO);
		EntityManagerMock.mockEntityManager();
		doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
		doReturn(uldAgreementVOs).when(uldDefaultsSqlDAO).listULDAgreementsColl(any());
		doReturn(oneTimeHashMap).when(sharedDefaultsProxy).findOneTimeValues(uldAgreementFilterVO.getCompanyCode(),
				oneTimeActiveStatusList);
		doReturn(reportDatainBytes).when(ReportUtilInstance.getIstance()).exportReport(any());
		uldContrller.printListULDAgreement(reportSpec);
	}
	/**
	 * 	
	 * 	Method		:	ULDControllerTest.invokeSaveReturnTransactionFeature
	 *	Added on 	:	29-Nov-2022
	 * 	Used for 	:	invokeSaveReturnTransactionFeature
	 *	Parameters	:	@throws BusinessException
	 *	Parameters	:	@throws SystemException 
	 *	Return type	: 	void
	 */
		@Test
		public void invokeSaveReturnTransactionFeature() throws BusinessException, SystemException{
		
		uldTransactionDetailVO.setCompanyCode(CMPCOD);
		uldTransactionDetailVO.setUldNumber("AKE0099EK");
		uldTransactionDetailVO.setTransactionStatus(TRANSACTION);
		uldTransactionDetailVO.setAwbNumber("121-10110111");
		uldTransactionDetailsVOs.add(uldTransactionDetailVO);
		transactionListVO.setUldTransactionsDetails(uldTransactionDetailsVOs);
		uldContrller.saveReturnTransactionFeature(transactionListVO);
        verify(demurrageFeature, times(1)).execute(any());
	}

	@Test(expected=ULDDefaultsBusinessException.class)
	public void shouldThrowBusinessExceptionWhenInvokeSaveReturnTransactionFeature() throws BusinessException, SystemException{
	
		BusinessException businessException = new BusinessException() {};
		transactionListVO.setUldTransactionsDetails(null);
		doThrow(businessException).when(demurrageFeature).execute(any());
		uldContrller.saveReturnTransactionFeature(transactionListVO);
        verify(demurrageFeature, times(1)).execute(any());
	  }
	@Test
	public void populateTransactionIdFromActionAndReturnTransIdForBreakdown() throws BusinessException, SystemException{
		String transactionId = uldContrller.populateTransactionIdFromAction(FlightDetailsVO.BREAKDOWN);
		assertEquals(MessageConfigConstants.TXN_SAVE_BREAKDOWN, transactionId);
	}
	@Test
	public void populateTransactionIdFromActionAndReturnTransIdForFlightClosure() throws BusinessException, SystemException{
		String transactionId = uldContrller.populateTransactionIdFromAction(FlightDetailsVO.CLOSURE);
		assertEquals(MessageConfigConstants.TXN_CLOSE_FLT, transactionId);
	}
	@Test
	public void populateTransactionIdFromActionAndReturnTransIdForBreakdownFlightFInalization() throws BusinessException, SystemException{
		String transactionId = uldContrller.populateTransactionIdFromAction(FlightDetailsVO.FINALISATION);
		assertEquals(MessageConfigConstants.TXN_FINALIZE_FLT, transactionId);
	}
	@Test
	public void populateTransactionIdFromActionAndReturnTransIdForAcceptance() throws BusinessException, SystemException{
		String transactionId = uldContrller.populateTransactionIdFromAction(FlightDetailsVO.ACCEPTANCE);
		assertEquals(MessageConfigConstants.TXN_SAVE_ACCEPTANCE, transactionId);
	}
	@Test
	public void populateTransactionIdFromActionAndReturnTransIdForOther() throws BusinessException, SystemException{
		String transactionId = uldContrller.populateTransactionIdFromAction("ABC");
		assertEquals(MessageConfigConstants.TXN_SCREEN, transactionId);
	}
	
	@Test
	public void shouldCallLUCMessageFeature() throws SystemException, BusinessException {
		TransactionVO transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		uldContrller.createAndSendLUCMessage(transactionVO);
		verify(lucMessageFeature, times(1)).execute(any());
	}
	
	@Test(expected = ULDDefaultsBusinessException.class)
	public void shouldThrowBusinessExceptionFromLUCMessageFeature() throws SystemException, BusinessException {
	
		TransactionVO transactionVO = new TransactionVO();
		Collection<ULDTransactionDetailsVO> uldTransactionDetailsVOs = new ArrayList<>();
		transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
		doThrow(ULDDefaultsBusinessException.class).when(lucMessageFeature).execute(any());
		uldContrller.createAndSendLUCMessage(transactionVO);
		verify(lucMessageFeature, times(1)).execute(any());
	}
	
	@Test
	 public void shouldSaveULDDiscrepancyForInsertOperation()
	            throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException {
	         uldDiscrepancyVO.setCompanyCode("DNAE");
	         uldDiscrepancyVO.setUldNumber("AKE8271EK");
	         uldDiscrepancyVO.setReportingStation("DXB");
	         uldDiscrepancyVO.setOperationFlag("I");
	         uldDiscrepancyVO.setDiscrepencyDate(new LocalDate("CDG", Location.ARP, true));
	         uld.setCurrentStation("CDG");
	         uldPk.setCompanyCode("DNAE");
	         uldPk.setUldNumber("AKE8271EK");
	         doReturn(uld).when(PersistenceController.getEntityManager()).find(ULD.class,
	                 uldPk);
	         uldDiscrepancyVOs.add(uldDiscrepancyVO);
	         ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
	         doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
	         doReturn(null).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());
	         uldContrller.saveULDDiscrepencyDetails(uldDiscrepancyVOs);     

	    }
	 @Test
	   public void shouldSaveULDDiscrepancyForUpdateOperation()
	            throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException {
		 uldDiscrepancyVO.setCompanyCode("DNAE");
         uldDiscrepancyVO.setUldNumber("AKE8271EK");
         uldDiscrepancyVO.setReportingStation("DXB");
         uldDiscrepancyVO.setOperationFlag("U");
         uldDiscrepancyVOs.add(uldDiscrepancyVO);
         uldDiscrepancyVO.setDiscrepencyDate(new LocalDate("CDG", Location.ARP, true));
         uld.setCurrentStation("CDG");
         uldPk.setCompanyCode("DNAE");
         uldPk.setUldNumber("AKE8271EK");
         doReturn(uld).when(PersistenceController.getEntityManager()).find(ULD.class,
                 uldPk);
         uldDiscrepancyVOs.add(uldDiscrepancyVO);
         ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
         doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
         doReturn(null).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());
         uldContrller.saveULDDiscrepencyDetails(uldDiscrepancyVOs);     
	}
	
	@Test
	  public void shouldSaveULDDiscrepancyForDeleteOperation()
            throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException {
		uldDiscrepancyVO.setCompanyCode("DNAE");
        uldDiscrepancyVO.setUldNumber("AKE8271EK");
        uldDiscrepancyVO.setReportingStation("DXB");
        uldDiscrepancyVO.setOperationFlag("D");
        uldDiscrepancyVOs.add(uldDiscrepancyVO);
        uldDiscrepancyVO.setDiscrepencyDate(new LocalDate("CDG", Location.ARP, true));
        uld.setCurrentStation("CDG");
        uldPk.setCompanyCode("DNAE");
        uldPk.setUldNumber("AKE8271EK");
        doReturn(uld).when(PersistenceController.getEntityManager()).find(ULD.class,
                uldPk);
        uldDiscrepancyVOs.add(uldDiscrepancyVO);
        ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
        doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
        doReturn(null).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());
        uldContrller.saveULDDiscrepencyDetails(uldDiscrepancyVOs); 
			
} 
	  @Test
	  public void shouldSaveULDDiscrepancyForMissingOperation()
            throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException{
		uldDiscrepancyVO.setCompanyCode("DNAE");
        uldDiscrepancyVO.setUldNumber("AKE8271EK");
        uldDiscrepancyVO.setReportingStation("DXB");
        uldDiscrepancyVO.setOperationFlag("I");
        uldDiscrepancyVOs.add(uldDiscrepancyVO);
        uldDiscrepancyVO.setDiscrepencyCode("M");
        uldDiscrepancyVO.setDiscrepencyDate(new LocalDate("CDG", Location.ARP, true));
        uld.setCurrentStation("CDG");
        uldPk.setCompanyCode("DNAE");
        uldPk.setUldNumber("AKE82011EK");
        FinderException finderException=new FinderException();
        doThrow(finderException).when(PersistenceController.getEntityManager())
        .find(eq(ULD.class), any(ULDPK.class));
        uldDiscrepancyVOs.add(uldDiscrepancyVO);
        ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
        doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
        doReturn(null).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());
        uldContrller.saveULDDiscrepencyDetails(uldDiscrepancyVOs); 		
	}
	
	@Test
		public void shouldDeleteULDsInSCM()
	            throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException{
			uldSCMReconcileDetailsVO.setAirportCode("DXB");
			uldSCMReconcileDetailsVO.setCompanyCode("DNAE");
			uldDetails.add(uldSCMReconcileDetailsVO);
			uldDiscrepancyVO.setCompanyCode("DNAE");
	        uldDiscrepancyVO.setUldNumber("AKE8271EK");
	        uldDiscrepancyVO.setReportingStation("DXB");
	        uldDiscrepancyVO.setOperationFlag("I");
	        uldDiscrepancyVOs.add(uldDiscrepancyVO);
	        uldDiscrepancyVO.setDiscrepencyCode("M");
	        uld.setCurrentStation("CDG");
	        uldPk.setCompanyCode("DNAE");
	        uldPk.setUldNumber("AKE82011EK");
	        reconcileDetailsPK.setAirportCode("CDG");
			reconcileDetailsPK.setCompanyCode("DNAE");
			reconcileDetailsPK.setAirlineIdentifier(1176);
			reconcileDetailsPK.setSequenceNumber("1");
			reconcileDetailsPK.setUldNumber("AKE82011EK");
	        doReturn(uldSCMReconcileDetails).when(PersistenceController.getEntityManager()).find(eq(ULDSCMReconcileDetails.class),
	        		any(ULDSCMReconcileDetailsPK.class));
	        doNothing().when(uldSCMReconcileDetails).remove();
	        ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
	        doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
	        doReturn(new ArrayList<>()).when(uldObjectDAO).findULDDiscrepanciesObjects(any(),any(),any());
	        uldContrller.deleteULDsInSCM(uldDetails); 	
		}
	@Test
		public void shouldSaveULDMovement()
			     throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException{
					
			uldMovementVO.setCompanyCode("DNAE");
			uldMovementVO.setUldNumber("AKE82011EK");
			uldMovementVO.setCarrierCode("EK");
			uldMovementVO.setCurrentStation("CDG");
			uldMovementVO.setUpdateCurrentStation(true);
			uldMovementVos.add(uldMovementVO);	
			uldNOs.add("AKE82011EK");
			ULDObjectDAO uldObjectDAO = mockBean("ULDObjectDAO", ULDObjectDAO.class);
			doReturn(uldObjectDAO).when(PersistenceController.getEntityManager()).getObjectQueryDAO("uld.defaults");
			doReturn(new ArrayList<>()).when(uldObjectDAO).findULDDiscrepancies(any(),any());
			KeyUtilInstanceMock.mockKeyUtilInstance();
			keyUtils = KeyUtilInstance.getInstance();
			doReturn("1").when(keyUtils).getKey(any(Criterion.class));
			new LocalDate("CDG", Location.ARP, true);
			doReturn(ULDDefaultsMockDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
			ULDAirportLocationVO 	uldAirportLocationVO = new ULDAirportLocationVO();
		    doReturn(uldAirportLocationVO).when(ULDDefaultsMockDAO).findCurrentLocation(any(),any(),any());
			doReturn(uld).when(PersistenceController.getEntityManager()).find(eq(ULD.class),
			                 any(ULDPK.class));			        			          
			airlineValidationVO.setCompanyCode("DNAE");
			airlineValidationVO.setAlphaCode("ak");
			doReturn(airlineValidationVO).when(sharedAirlineProxy).findAirline(any(String.class),any(Integer.class));
			uldContrller.saveULDMovement(uldNOs,uldMovementVos);	
				}	
	
	@Test
	public void shouldgenerateInvoiceWhenCurrencyIsnotThere()
		throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException{
		uldTransactionDetailVO.setCompanyCode("DNAE");
		uldTransactionDetailVO.setUldNumber("AKE838EK");
		uldTransactionDetailVO.setTransactionRefNumber(8);
		uldTransactionDetailsVOs.add(uldTransactionDetailVO);
		uLDTransaction.setInvoiceRefNumber("73993");
		uldChargingInvoicePK.setInvoiceRefNumber("73993");
		uldChargingInvoicePK.setCompanyCode("DNAE");
		uLDChargingInvoice.setUldChargingInvoicePK(uldChargingInvoicePK);
		systemParameterCodes.add("uld.defaults.uldinvoicingcurrency");
		systemParameterMap.put("uld.defaults.uldinvoicingcurrency","est");
		doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
		doReturn(uLDTransaction).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),any(ULDTransactionPK.class));
		uldContrller.generateInvoiceForReturnedUlds(uldChargingInvoiceVO, uldTransactionDetailsVOs);
			}

    @Test
    public void shouldgenerateInvoiceWhenCurrencyIsthere()
		throws BusinessException, SystemException, RemoteException, PersistenceException, FinderException{
    	uldTransactionDetailVO.setCurrency("AED");
    	uldTransactionDetailVO.setCompanyCode("DNAE");
    	uldTransactionDetailVO.setUldNumber("AKE838EK");
    	uldTransactionDetailVO.setTransactionRefNumber(8);
    	uldTransactionDetailsVOs.add(uldTransactionDetailVO);
    	uLDTransaction.setInvoiceRefNumber("73993");
    	uldChargingInvoicePK.setInvoiceRefNumber("73993");
    	uldChargingInvoicePK.setCompanyCode("DNAE");
    	uLDChargingInvoice.setUldChargingInvoicePK(uldChargingInvoicePK);
    	systemParameterCodes.add("uld.defaults.uldinvoicingcurrency");
    	systemParameterMap.put("uld.defaults.uldinvoicingcurrency","AED");
    	doReturn(systemParameterMap).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodes);
    	systemParameterCodess.add("uld.defaults.exchangeratepriorityorder");
    	systemParameterMaps.put("uld.defaults.exchangeratepriorityorder","est");
    	doReturn(systemParameterMaps).when(sharedDefaultsProxy).findSystemParameterByCodes(systemParameterCodess);
    	doReturn(uLDTransaction).when(PersistenceController.getEntityManager()).find(eq(ULDTransaction.class),any(ULDTransactionPK.class));
    	uldContrller.generateInvoiceForReturnedUlds(uldChargingInvoiceVO, uldTransactionDetailsVOs);
     }
    @Test
    public void shouldFindBestFItAgreementForLoanTransaction() throws SystemException, PersistenceException{
    	ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
    	ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
    	uldTransactionDetailsVO.setTransactionType("L");
    	uldTransactionDetailsVO.setCompanyCode("DNAE");
    	uldTransactionDetailsVO.setToPartyCode("LH");
    	uldTransactionDetailsVO.setPartyType("A");
    	uldTransactionDetailsVO.setFromPartyCode("EK");
    	uldTransactionDetailsVO.setTransactionDate(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
    	 EntityManagerMock.mockEntityManager();
         doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
         doReturn(uldAgreementVO).when(uldDefaultsSqlDAO).findULDAgreementForReturnTransaction(any()); 
		 uldContrller.findBestFitULDAgreement(uldTransactionDetailsVO);
    }
    @Test
    public void shouldFindBestFItAgreementWhenTransactionTypeIsReturn() throws SystemException, PersistenceException{
    	ULDAgreementVO uldAgreementVO = new ULDAgreementVO();
    	ULDTransactionDetailsVO uldTransactionDetailsVO = new ULDTransactionDetailsVO();
    	uldTransactionDetailsVO.setTransactionType("R");
    	uldTransactionDetailsVO.setCompanyCode("DNAE");
    	uldTransactionDetailsVO.setToPartyCode("LH");
    	uldTransactionDetailsVO.setPartyType("A");
    	uldTransactionDetailsVO.setFromPartyCode("EK");
    	 EntityManagerMock.mockEntityManager();
         doReturn(uldDefaultsSqlDAO).when(PersistenceController.getEntityManager()).getQueryDAO("uld.defaults");
         doReturn(uldAgreementVO).when(uldDefaultsSqlDAO).findULDAgreementForReturnTransaction(any()); 
		 uldContrller.findBestFitULDAgreement(uldTransactionDetailsVO);
    }
	@Test
	public void invokeUpdateULDDemurrageDetailsFeature() throws BusinessException, SystemException{
	
	uldTransactionDetailVO.setCompanyCode(CMPCOD);
	uldTransactionDetailVO.setUldNumber("AKE0099EK");
	uldTransactionDetailsVOs.add(uldTransactionDetailVO);
	TransactionVO transactionVO = new TransactionVO();
	transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
	uldContrller.updateULDTransactionWithDemurrageDetails(transactionVO);
    verify(updateULDDemurrageDetailsFeature, times(1)).execute(any());
	}
	@Test
	public void invokeCreateUldLoanTransactionFeature() throws BusinessException, SystemException{
	
	uldTransactionDetailVO.setCompanyCode(CMPCOD);
	uldTransactionDetailVO.setUldNumber("AKE0099EK");
	uldTransactionDetailsVOs.add(uldTransactionDetailVO);
	TransactionVO transactionVO = new TransactionVO();
	transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
	uldContrller.createULDLoan(transactionVO);
    verify(saveULDLoanTransactionFeature, times(1)).execute(any());
	}
	@Test
	public void shouldThrowBusinessExcpetionIfIssuesAreEncountered() throws BusinessException, SystemException{
	
	uldTransactionDetailVO.setCompanyCode(CMPCOD);
	uldTransactionDetailVO.setUldNumber("AKE0099EK");
	uldTransactionDetailsVOs.add(uldTransactionDetailVO);
	TransactionVO transactionVO = new TransactionVO();
	transactionVO.setUldTransactionDetailsVOs(uldTransactionDetailsVOs);
	doThrow(ULDDefaultsBusinessException.class).when(saveULDLoanTransactionFeature).execute(any());
	uldContrller.createULDLoan(transactionVO);
    verify(saveULDLoanTransactionFeature, times(1)).execute(any());
}
}