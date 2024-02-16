/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.Receivablemanagement.ReceivableManagementControllerTest.java
 *
 *	Created by	:	A-10647
 *	Created on	:	07-Feb-2022
 *
 *  Copyright 2022 Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved. Ltd. All Rights Reserved.
 *
 * 	This software is the proprietary information of Copyright 2007 IBS Software Services (P) Ltd. All Rights Reserved.  Ltd.
 * 	Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.mra.Receivablemanagement;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ibsplc.ibase.servicelocator.exception.ServiceNotAccessibleException;
import com.ibsplc.icargo.business.mail.mra.gpabilling.GPABillingController;
import com.ibsplc.icargo.business.mail.mra.proxy.MailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedCurrencyProxy;
import com.ibsplc.icargo.business.mail.mra.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlement;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlementDetail;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlementDetailPK;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.MRAGPABatchSettlementPK;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.ReceivableManagementController;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.MRABatchUploadedVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchDetailsVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchFilterVO;
import com.ibsplc.icargo.business.mail.mra.receivablemanagement.vo.PaymentBatchSettlementDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.shared.currency.vo.CurrencyValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.currency.Money;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.mra.receivablemanagement.MRAReceivableManagementSqlDAO;
import com.ibsplc.sprout.exception.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import static org.mockito.Matchers.anyCollectionOf;

/**
 *	Java file	: 	com.ibsplc.icargo.business.mail.mra.Receivablemanagement.ReceivableManagementControllerTest.java
 *	Version		:	Name	:	Date			:	Updation
 * ---------------------------------------------------
 *		0.1		:	A-10647	:	07-Feb-2022	:	Draft
 */
public class ReceivableManagementControllerTest extends AbstractFeatureTest {
	private ReceivableManagementController ReceivableManagementControllerSpy;
	private GPABillingController  GPABillingControllerSpy;
	private MRAReceivableManagementSqlDAO dao;
	Money money;
	MRAGPABatchSettlement batch;
	MRAGPABatchSettlementDetail batchDetail;
	MailTrackingDefaultsProxy mailTrackingDefaultsProxy;
	private static final String CLEAR_BATCH ="Clear Balance performed so that Unapplied balance is cleared";
	private static final Log LOG = LogFactory.getLogger("MRA ReceivableManagement");
    private ReceivableManagementController receivableManagementController; 
    private SharedDefaultsProxy sharedDefaultsProxy;
    private SharedCurrencyProxy sharedCurrencyProxy;


	/**
	 *	Overriding Method	:	@see com.ibsplc.icargo.framework.feature.AbstractFeatureTest#setup()
	 *	Added by 			: A-10647 on 07-Feb-2022
	 * 	Used for 	:
	 *	Parameters	:	@throws Exception 
	 */
	@Override
	public void setup() throws Exception {
		ReceivableManagementControllerSpy = spy(new ReceivableManagementController());
		GPABillingControllerSpy=spy(new GPABillingController());
		EntityManagerMock.mockEntityManager();
		batch= new MRAGPABatchSettlement();
		batchDetail =new MRAGPABatchSettlementDetail();
		dao = mock(MRAReceivableManagementSqlDAO.class);
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		sharedCurrencyProxy=mockProxy(SharedCurrencyProxy.class);
		money = mock(Money.class);
		mailTrackingDefaultsProxy=mockProxy(MailTrackingDefaultsProxy.class);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.receivablemanagement");
		receivableManagementController = mockBean("receivableManagementController", ReceivableManagementController.class);
		
	}
	@Test
	public void clearBatchDetailsValidCase() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, FinderException{
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("CLEARBTCH03");
		detailVO.setPaCode("DE101");
		detailVO.setBatchSequenceNum(485);
		detailVO.setCompanyCode("AA");
		detailVO.setAmountTobeApplied(8.0);
		detailVO.setAppliedAmount(money);
		detailVO.setUnappliedAmount(money);
		detailVO.setRemarks(CLEAR_BATCH);
		detailVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		Collection<PaymentBatchSettlementDetailsVO> settlementDetail = new ArrayList<>();
		PaymentBatchSettlementDetailsVO settlementVO =new PaymentBatchSettlementDetailsVO();
		settlementVO.setBatchID("CLEARBTCH03");
		settlementVO.setBatchSequenceNum(485);
		settlementVO.setCompanyCode("AA");
		settlementVO.setPaCode("DE101");
		settlementVO.setMailSeqNum(773400);
		settlementVO.setMailbagId("DEFRAAUSDFWAAUN24809000000008");
		settlementVO.setAppliedAmount(money);
		settlementVO.setUnappliedAmount(money);
		settlementVO.setLstUpdatedUser("TEST");
		settlementVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		settlementVO.setRemarks(CLEAR_BATCH);
		settlementDetail.add(settlementVO);
		detailVO.setPaymentBatchSettlementDetails(settlementDetail);
		MRAGPABatchSettlementPK pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode("AA");
		pk.setPoaCode("DE101");
		pk.setBatchId("CLEARBTCH03");
		pk.setBatchSequenceNumber(485);
		batch.setmRAGPABatchSettlementPk(pk);
		batch.setAppliedAmount(0.0);
		MRAGPABatchSettlementDetailPK settlementPK = new MRAGPABatchSettlementDetailPK();
		settlementPK = new MRAGPABatchSettlementDetailPK();	
		settlementPK.setBatchId("CLEARBTCH03");
		settlementPK.setBatchSequenceNumber(485);
		settlementPK.setCompanyCode("AA");
		settlementPK.setGpaCode("DE101");
		settlementPK.setMailSeqNum(773400);
		batchDetail.setmRAGPABatchSettlementDetailPK(settlementPK);
		doReturn(batch).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlement.class), any(MRAGPABatchSettlementPK.class));
		doReturn(batchDetail).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlementDetail.class), any(MRAGPABatchSettlementDetailPK.class));
		ReceivableManagementControllerSpy.clearBatchDetails(detailVO);
	}
	@Test
	public void clearBatchDetailsValidCaseWithAppliedAmount() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, FinderException{
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("CLEARBTCH03");
		detailVO.setPaCode("DE101");
		detailVO.setBatchSequenceNum(485);
		detailVO.setCompanyCode("AA");
		detailVO.setAmountTobeApplied(8.0);
		detailVO.setAppliedAmount(money);
		detailVO.setUnappliedAmount(money);
		detailVO.setRemarks(CLEAR_BATCH);
		detailVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		Collection<PaymentBatchSettlementDetailsVO> settlementDetail = new ArrayList<>();
		PaymentBatchSettlementDetailsVO settlementVO =new PaymentBatchSettlementDetailsVO();
		settlementVO.setBatchID("CLEARBTCH03");
		settlementVO.setBatchSequenceNum(485);
		settlementVO.setCompanyCode("AA");
		settlementVO.setPaCode("DE101");
		settlementVO.setMailSeqNum(773400);
		settlementVO.setMailbagId("DEFRAAUSDFWAAUN24809000000008");
		settlementVO.setAppliedAmount(money);
		settlementVO.setUnappliedAmount(money);
		settlementVO.setLstUpdatedUser("TEST");
		settlementVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		settlementVO.setRemarks(CLEAR_BATCH);
		settlementDetail.add(settlementVO);
		detailVO.setPaymentBatchSettlementDetails(settlementDetail);
		MRAGPABatchSettlementPK pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode("AA");
		pk.setPoaCode("DE101");
		pk.setBatchId("CLEARBTCH03");
		pk.setBatchSequenceNumber(485);
		batch.setmRAGPABatchSettlementPk(pk);
		batch.setAppliedAmount(2);
		MRAGPABatchSettlementDetailPK settlementPK = new MRAGPABatchSettlementDetailPK();
		settlementPK = new MRAGPABatchSettlementDetailPK();	
		settlementPK.setBatchId("CLEARBTCH03");
		settlementPK.setBatchSequenceNumber(485);
		settlementPK.setCompanyCode("AA");
		settlementPK.setGpaCode("DE101");
		settlementPK.setMailSeqNum(773400);
		batchDetail.setmRAGPABatchSettlementDetailPK(settlementPK);
		doReturn(batch).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlement.class), any(MRAGPABatchSettlementPK.class));
		doReturn(batchDetail).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlementDetail.class), any(MRAGPABatchSettlementDetailPK.class));
		ReceivableManagementControllerSpy.clearBatchDetails(detailVO);
	}
	
	@Test
	public void clearBatchDetailsException() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, FinderException{
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("CLEARBTCH03");
		detailVO.setPaCode("DE101");
		detailVO.setBatchSequenceNum(485);
		detailVO.setCompanyCode("AA");
		detailVO.setAmountTobeApplied(8.0);
		detailVO.setAppliedAmount(money);
		detailVO.setUnappliedAmount(money);
		detailVO.setRemarks(CLEAR_BATCH);
		Collection<PaymentBatchSettlementDetailsVO> settlementDetail = new ArrayList<>();
		PaymentBatchSettlementDetailsVO settlementVO =new PaymentBatchSettlementDetailsVO();
		settlementVO.setBatchID("CLEARBTCH03");
		settlementVO.setBatchSequenceNum(485);
		settlementVO.setCompanyCode("AA");
		settlementVO.setPaCode("DE101");
		settlementVO.setMailSeqNum(773400);
		settlementVO.setMailbagId("DEFRAAUSDFWAAUN24809000000008");
		settlementVO.setAppliedAmount(money);
		settlementVO.setUnappliedAmount(money);
		settlementVO.setLstUpdatedUser("TEST");
		settlementVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		settlementVO.setRemarks(CLEAR_BATCH);
		settlementDetail.add(settlementVO);
		detailVO.setPaymentBatchSettlementDetails(settlementDetail);
		MRAGPABatchSettlementPK pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode("AA");
		pk.setPoaCode("DE101");
		pk.setBatchId("CLEARBTCH03");
		pk.setBatchSequenceNumber(485);
		batch.setmRAGPABatchSettlementPk(pk);
		batch.setAppliedAmount(2.0);
		batch.setUnAppliedAmount(0.0);
		MRAGPABatchSettlementDetailPK settlementPK = new MRAGPABatchSettlementDetailPK();
		settlementPK = new MRAGPABatchSettlementDetailPK();	
		settlementPK.setBatchId("CLEARBTCH03");
		settlementPK.setBatchSequenceNumber(485);
		settlementPK.setCompanyCode("AA");
		settlementPK.setGpaCode("DE101");
		settlementPK.setMailSeqNum(773400);
		batchDetail.setmRAGPABatchSettlementDetailPK(settlementPK);
		doReturn(batch).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlement.class), any(MRAGPABatchSettlementPK.class));
		doReturn(batchDetail).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlementDetail.class), any(MRAGPABatchSettlementDetailPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlement.class), any(MRAGPABatchSettlementPK.class));
		doThrow(FinderException.class).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlementDetail.class), any(MRAGPABatchSettlementDetailPK.class));
		ReceivableManagementControllerSpy.clearBatchDetails(detailVO);
	}
	
	@Test 
	public void triggerUnappliedCashClearanceAccountingException() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, FinderException{
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("CLEARBTCH03");
		detailVO.setPaCode("DE101");
		detailVO.setBatchSequenceNum(485);
		detailVO.setCompanyCode("AA");
		detailVO.setAmountTobeApplied(8.0);

		detailVO.setAppliedAmount(money);
	
		detailVO.setUnappliedAmount(money);
		detailVO.setRemarks(CLEAR_BATCH);
		Collection<PaymentBatchSettlementDetailsVO> settlementDetail = new ArrayList<>();
		PaymentBatchSettlementDetailsVO settlementVO =new PaymentBatchSettlementDetailsVO();
		settlementVO.setBatchID("CLEARBTCH03");
		settlementVO.setBatchSequenceNum(485);
		settlementVO.setCompanyCode("AA");
		settlementVO.setPaCode("DE101");
		settlementVO.setMailSeqNum(773400);
		settlementVO.setMailbagId("DEFRAAUSDFWAAUN24809000000008");
		settlementVO.setAppliedAmount(money);
		settlementVO.setUnappliedAmount(money);
		settlementVO.setLstUpdatedUser("TEST");
		settlementVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		settlementVO.setRemarks(CLEAR_BATCH);
		settlementDetail.add(settlementVO);
		detailVO.setPaymentBatchSettlementDetails(settlementDetail);
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.receivablemanagement");
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.receivablemanagement");
		ReceivableManagementControllerSpy.triggerUnappliedCashClearanceAccounting(detailVO);
	}

	@Test
	public void clearBatchDetailsValidCaseWithoutUnappliedAmount() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, FinderException{
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("CLEARBTCH03");
		detailVO.setPaCode("DE101");
		detailVO.setBatchSequenceNum(485);
		detailVO.setCompanyCode("AA");
		detailVO.setAmountTobeApplied(8.0);
		detailVO.setAppliedAmount(money);
		detailVO.setUnappliedAmount(money);
		detailVO.setRemarks(CLEAR_BATCH);
		detailVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		Collection<PaymentBatchSettlementDetailsVO> settlementDetail = new ArrayList<>();
		PaymentBatchSettlementDetailsVO settlementVO =new PaymentBatchSettlementDetailsVO();
		settlementVO.setBatchID("CLEARBTCH03");
		settlementVO.setBatchSequenceNum(485); 
		settlementVO.setCompanyCode("AA");
		settlementVO.setPaCode("DE101");
		settlementVO.setMailSeqNum(773400);
		settlementVO.setMailbagId("DEFRAAUSDFWAAUN24809000000008");
		settlementVO.setAppliedAmount(money);
		settlementVO.setUnappliedAmount(money);
		settlementVO.setLstUpdatedUser("TEST");
		settlementVO.setLstUpdatedTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		settlementVO.setRemarks(CLEAR_BATCH);
		settlementDetail.add(settlementVO);
		detailVO.setPaymentBatchSettlementDetails(settlementDetail);
		MRAGPABatchSettlementPK pk = new MRAGPABatchSettlementPK();
		pk.setCompanyCode("AA");
		pk.setPoaCode("DE101");
		pk.setBatchId("CLEARBTCH03");
		pk.setBatchSequenceNumber(485);
		batch.setmRAGPABatchSettlementPk(pk);
		batch.setAppliedAmount(0.0);
		batch.setUnAppliedAmount(8.0);
		MRAGPABatchSettlementDetailPK settlementPK = new MRAGPABatchSettlementDetailPK();
		settlementPK = new MRAGPABatchSettlementDetailPK();	
		settlementPK.setBatchId("CLEARBTCH03");
		settlementPK.setBatchSequenceNumber(485);
		settlementPK.setCompanyCode("AA");
		settlementPK.setGpaCode("DE101");
		settlementPK.setMailSeqNum(773400);
		batchDetail.setmRAGPABatchSettlementDetailPK(settlementPK);
		doReturn(batch).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlement.class), any(MRAGPABatchSettlementPK.class));
		doReturn(batchDetail).when(PersistenceController.getEntityManager()).find(eq(MRAGPABatchSettlementDetail.class), any(MRAGPABatchSettlementDetailPK.class));
		ReceivableManagementControllerSpy.clearBatchDetails(detailVO);
	}
	
	@Test
	public void listPaymentBatchDetailsValidCase() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException{
		PaymentBatchFilterVO filterVO = new PaymentBatchFilterVO();
		filterVO.setBatchFrom(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		filterVO.setBatchTo(new LocalDate(LocalDate.NO_STATION,Location.NONE, true));
		filterVO.setPageNumber(1);
		Page<PaymentBatchDetailsVO> paymentBatchDetailsVOs = new Page<PaymentBatchDetailsVO>();
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		detailVO.setBatchID("BATCHIDTEST");
		paymentBatchDetailsVOs.add(detailVO);
		doReturn(paymentBatchDetailsVOs).when(dao).listPaymentBatchDetails(filterVO);
		Page<PaymentBatchDetailsVO> detailsVOs = ReceivableManagementControllerSpy.listPaymentBatchDetails(filterVO);
		assertNotNull(detailsVOs);
	}
	
	@Test
	public void constructPaymentBatchDetailsException() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException{
		PaymentBatchDetailsVO batchDetailsVO=new PaymentBatchDetailsVO();
		batchDetailsVO.setCompanyCode("AA");
		batchDetailsVO.setBatchID("113");

		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		batchDetailsVO.setBatchSequenceNum(112);
		batchDetailsVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("87654");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		batchDetailsVO.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		detailVO.setBatchID("BATCHIDTEST");
		//doReturn(Long.parseLong("0")).when(dao).findMailBagSequenceNumberFromMailIdr("123","AA");
		doThrow(PersistenceException.class).when(PersistenceController.getEntityManager()).getQueryDAO("mail.mra.receivablemanagement");	
		ReceivableManagementControllerSpy.constructPaymentBatchDetailsVOS(batchDetailsVO);

}
	
	@Test
	public void constructPaymentBatchDetailsTestException() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, NumberFormatException, RemoteException, ServiceNotAccessibleException{
		long mailSeqNumZero = 0;
		long mailSeqNum = 123;
		PaymentBatchDetailsVO batchDetailsVO=new PaymentBatchDetailsVO();
		batchDetailsVO.setCompanyCode("AA");
		batchDetailsVO.setBatchID("113");

		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		batchDetailsVO.setBatchSequenceNum(112);
		batchDetailsVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("BMSGEAUSJFKAAUN20001001000190");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		batchDetailsVO.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		detailVO.setBatchID("BATCHIDTEST");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("1234");
		doReturn(mailSeqNumZero).when(dao).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		doReturn(mailbagVO).when(ReceivableManagementControllerSpy).constructMailbagVO("BMSGEAUSJFKAAUN20001001000190");
		doThrow(ServiceNotAccessibleException.class).when(mailTrackingDefaultsProxy).insertMailbagAndHistory(any(MailbagVO.class));
		System.out.println("ServiceNotAccessibleException e");	
		ReceivableManagementControllerSpy.constructPaymentBatchDetailsVOS(batchDetailsVO);

}
	
	
	@Test
	public void constructPaymentBatchDetailsTestnonempty() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, NumberFormatException, RemoteException, ServiceNotAccessibleException{
		
		long mailSeqNum = 1;
		PaymentBatchDetailsVO batchDetailsVO=new PaymentBatchDetailsVO();
		batchDetailsVO.setCompanyCode("AA");
		batchDetailsVO.setBatchID("113");
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		batchDetailsVO.setBatchSequenceNum(112);
		batchDetailsVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("BMSGEAUSJFKAAUN20001001000190");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		batchDetailsVO.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		detailVO.setBatchID("BATCHIDTEST");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("1234");
		doReturn(mailSeqNum).when(dao).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));	
		ReceivableManagementControllerSpy.constructPaymentBatchDetailsVOS(batchDetailsVO);

}
	
	@Test
	public void constructPaymentBatchDetailsTest() throws SystemException,PersistenceException, com.ibsplc.xibase.server.framework.exceptions.SystemException, NumberFormatException, RemoteException, ServiceNotAccessibleException{
		long mailSeqNumZero = 0;
		long mailSeqNum = 123;
		PaymentBatchDetailsVO batchDetailsVO=new PaymentBatchDetailsVO();
		batchDetailsVO.setCompanyCode("AA");
		batchDetailsVO.setBatchID("113");

		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		batchDetailsVO.setBatchSequenceNum(112);
		batchDetailsVO.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("BMSGEAUSJFKAAUN20001001000190");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		batchDetailsVO.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		detailVO.setBatchID("BATCHIDTEST");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("1234");
		doReturn(mailSeqNumZero).when(dao).findMailBagSequenceNumberFromMailIdr(any(String.class),any(String.class));
		doReturn(mailbagVO).when(ReceivableManagementControllerSpy).constructMailbagVO("BMSGEAUSJFKAAUN20001001000190");
		doReturn(mailSeqNum).when(mailTrackingDefaultsProxy).insertMailbagAndHistory(any(MailbagVO.class));
		System.out.println("ServiceNotAccessibleException e");	
		ReceivableManagementControllerSpy.constructPaymentBatchDetailsVOS(batchDetailsVO);

}
	@Test
	public void validateBatchUploadData() throws com.ibsplc.xibase.server.framework.exceptions.SystemException, ServiceNotAccessibleException, ProxyException{
		PaymentBatchDetailsVO vo=new PaymentBatchDetailsVO();
		double sumAmountDecimal=34.789;
	//	PaymentBatchDetailsVO vo=new PaymentBatchDetailsVO();
		vo.setCompanyCode("AA");
		vo.setBatchID("113");
		vo.setPaCode("BM101");
		vo.setBatchCurrency("EUR");
		money.setAmount(11);
		vo.setBatchAmount(money);
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		vo.setBatchSequenceNum(112);
		vo.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("BMSGEAUSJFKAAUN20001001000190");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		mRABatchUploadedVO.setPayAmount(11.10);
		vo.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		Map<String, String> systemParamcodeCheck = new HashMap<>();
		systemParamcodeCheck.put("mailtracking.mra.overrideroundingvalue", "5"); 
		doReturn(systemParamcodeCheck).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("1234");
		doReturn(mailbagVO).when(ReceivableManagementControllerSpy).constructMailbagVO("BMSGEAUSJFKAAUN20001001000190");
		//doReturn(systemParamcodeCheck).when(sharedDefaultsProxy).saveFileUploadExceptions(anyCollectionOf(String.class));
		doReturn(sumAmountDecimal).when(GPABillingControllerSpy).getScaledValue(any(Double.class),any(Integer.class));
		ReceivableManagementControllerSpy.validateBatchUploadData(vo);
	}
	@Test
	public void validateBatchUploadDataException() throws com.ibsplc.xibase.server.framework.exceptions.SystemException, ServiceNotAccessibleException, ProxyException{
		PaymentBatchDetailsVO vo=new PaymentBatchDetailsVO();
		double sumAmountDecimal=34.789;
	//	PaymentBatchDetailsVO vo=new PaymentBatchDetailsVO();
		vo.setCompanyCode("AA");
		vo.setBatchID("113");
		vo.setPaCode("BM101");
		vo.setBatchCurrency("EUR");
		money.setAmount(11);
		vo.setBatchAmount(money);
		PaymentBatchDetailsVO detailVO = new PaymentBatchDetailsVO();
		vo.setBatchSequenceNum(112);
		vo.setBatchDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		
		Collection<MRABatchUploadedVO> batchUploadedVOs = new ArrayList<>();
		MRABatchUploadedVO mRABatchUploadedVO =new MRABatchUploadedVO();
		mRABatchUploadedVO.setCompanyCode("AA");
		mRABatchUploadedVO.setPaCode("BM101");
		mRABatchUploadedVO.setMailIdr("BMSGEAUSJFKAAUN20001001000190");
		mRABatchUploadedVO.setPayDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setMailDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,false));
		mRABatchUploadedVO.setConsignmentDocNum("BMSGEA062664");
		mRABatchUploadedVO.setCurrencyCode("EUR");
		vo.setBatchUploadedVOs(batchUploadedVOs);
		batchUploadedVOs.add(mRABatchUploadedVO);
		Map<String, String> systemParamcodeCheck = new HashMap<>();
		systemParamcodeCheck.put("mailtracking.mra.overrideroundingvalue", "5"); 
		doThrow(ProxyException.class).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		CurrencyValidationVO currencyValidationVO = new CurrencyValidationVO();
		currencyValidationVO.setRoundingUnit(0.01);
		doReturn(currencyValidationVO).when(sharedCurrencyProxy).validateCurrency("SQ","SGD");
		MailbagVO mailbagVO=new MailbagVO();
		mailbagVO.setMailbagId("1234");
		doReturn(mailbagVO).when(ReceivableManagementControllerSpy).constructMailbagVO("BMSGEAUSJFKAAUN20001001000190");
		doReturn(sumAmountDecimal).when(GPABillingControllerSpy).getScaledValue(any(Double.class),any(Integer.class));
		ReceivableManagementControllerSpy.validateBatchUploadData(vo);
	}


}
