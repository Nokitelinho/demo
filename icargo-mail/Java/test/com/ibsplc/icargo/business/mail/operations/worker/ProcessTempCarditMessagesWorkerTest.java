package com.ibsplc.icargo.business.mail.operations.worker;

import static org.junit.Assert.*;


import static org.mockito.Mockito.spy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.MailBoxId;
import com.ibsplc.icargo.business.mail.operations.MailBoxIdPk;
import com.ibsplc.icargo.business.mail.operations.vo.CarditProcessJobScheduleVO;
import com.ibsplc.icargo.business.mail.operations.vo.EDIInterchangeVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailRdtMasterVO;
import com.ibsplc.icargo.business.mail.operations.vo.RdtMasterFilterVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.CarditTempMsgVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.EDIMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.ReceptacleMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.TotalsInformationMessageVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.cardit.TransportInformationMessageVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;


public class ProcessTempCarditMessagesWorkerTest extends AbstractFeatureTest {

	
	private static final String includeMailBoxIdr = "USPSSME";
	private static final String ORIGIN = "CDG";
	private static final String DESTINATION = "DXB";
	private static final String MAIL_OPERATIONS = "mail.operations";
	private ProcessTempCarditMessagesWorker worker;
	private MailTrackingDefaultsDAO dao;
	
	Collection<CarditTempMsgVO> carditTmpMsgVOs;
	CarditTempMsgVO carditTmpMsgVO;
	CarditProcessJobScheduleVO carditProcessJobScheduleVO;
	HashMap<String,EDIMessageVO> ediMessageMap;
	EDIInterchangeVO ediInterchangeVO;
	LogonAttributes logonAttributes;
	EDIMessageVO ediMessageVO;
	Collection<CarditMessageVO> carditMessages;
	CarditMessageVO carditMessageVO;
	TransportInformationMessageVO transportMessageVO;
	Collection<TransportInformationMessageVO> transportMessageVOs;
	Collection<ReceptacleInformationMessageVO> receptacleMessages;
	ReceptacleInformationMessageVO receptacleMessage;
	ReceptacleMessageVO receptacleVO;
	MailBoxId mailBoxId;
	MailBoxIdPk mailBoxIdPk;
	Collection<MailRdtMasterVO> mailRdtMasterVOs;
	MailRdtMasterVO mailRdtMasterVO;
	RdtMasterFilterVO filterVO;
	Collection<TotalsInformationMessageVO> totalsMessages; 
	TotalsInformationMessageVO totalMessageVO;
	
	@Override
	public void setup() throws Exception {
		worker = spy(new ProcessTempCarditMessagesWorker());
		EntityManagerMock.mockEntityManager();
		dao = mock(MailTrackingDefaultsDAO.class);
	}


	@Test
	public void getEDIInterchangeVO_OriginAndDestination() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		mailRdtMasterVO.setAirportCodes(DESTINATION);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	

	@Test
	public void getEDIInterchangeVO_OriginOnly() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}


	@Test
	public void getEDIInterchangeVO_DestinationOnly() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setAirportCodes(DESTINATION);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}


	@Test
	public void getEDIInterchangeVO_consignmentCompletionDateNull() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(null);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		carditMessageVO.setConsignmentCompletionDate(null);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO); 
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	
	@Test
	public void getEDIInterchangeVO_DepartureAndArrivalPlaceNull() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(null);
		transportMessageVO.setArrivalPlace(null);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(null).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	

	@Test
	public void getEDIInterchangeVO_OriginArportCodeNull() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	


	@Test
	public void getEDIInterchangeVOTest_OriginAndDestinationArportCodeNotCorrect() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(DESTINATION);
		mailRdtMasterVO.setAirportCodes(ORIGIN);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	

	@Test
	public void getEDIInterchangeVOTest_ArrivalPlaceNull() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(null);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	

	@Test
	public void getEDIInterchangeVOTest_DestinationArportCodeNull() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}


	@Test
	public void getEDIInterchangeVOTest_DestintaionArportCodeNotCorrect() throws SystemException, FinderException, PersistenceException{
		
		ediMessageVO = new EDIMessageVO();
		ediMessageVO.setCompanyCode("AA");
		ediMessageVO.setSenderID(includeMailBoxIdr);
		carditMessages = new ArrayList<>();
		carditMessageVO =new CarditMessageVO();
		transportMessageVO = new TransportInformationMessageVO();
		transportMessageVO.setDeparturePlace(ORIGIN);
		transportMessageVO.setArrivalPlace(DESTINATION);		
		transportMessageVOs = new ArrayList<>();
		transportMessageVOs.add(transportMessageVO);
		carditMessageVO.setTransportInformation(transportMessageVOs);
		receptacleMessages = new ArrayList<>();
		receptacleMessage = new ReceptacleInformationMessageVO();
		receptacleVO= new ReceptacleMessageVO();
		receptacleMessage.setReceptacleVO(receptacleVO);
		receptacleMessages.add(receptacleMessage);
		carditMessageVO.setReceptacleInformation(receptacleMessages);
		LocalDate consignmentCompletionDate = new LocalDate(LocalDate.NO_STATION, Location.NONE, false);
		carditMessageVO.setConsignmentCompletionDate(consignmentCompletionDate);
		totalsMessages = new ArrayList<>();
		totalMessageVO = new TotalsInformationMessageVO();
		totalsMessages.add(totalMessageVO);
		carditMessageVO.setTotalsInformation(totalsMessages);
		carditMessages.add(carditMessageVO);
		ediMessageVO.setCarditMessages(carditMessages); 
		carditTmpMsgVOs = new ArrayList<>();
		doReturn(true).when(worker).validateCarditDomesticMailbagDetails(any(CarditMessageVO.class));
		carditTmpMsgVO = new CarditTempMsgVO();
		carditTmpMsgVOs.add(carditTmpMsgVO);
		assertNotNull(carditTmpMsgVOs);
		mailBoxId = new MailBoxId();
		mailBoxIdPk = new MailBoxIdPk();
		mailBoxId.setMailboxStatus("ACTIVE");
		mailBoxId.setMailboxOwner("PA");
		mailBoxId.setMailboxIdPK(mailBoxIdPk);
		mailBoxId.setOwnerCode("USPSSME");
		mailRdtMasterVO = new MailRdtMasterVO();
		mailRdtMasterVO.setOriginAirportCodes(ORIGIN);
		mailRdtMasterVO.setAirportCodes(ORIGIN);
		mailRdtMasterVO.setRdtOffset(400);
		mailRdtMasterVO.setRdtDay(1);
		mailRdtMasterVOs = new ArrayList<>();
		mailRdtMasterVOs.add(mailRdtMasterVO); 
		filterVO = new RdtMasterFilterVO();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailBoxId).when(PersistenceController.getEntityManager()).find(eq(MailBoxId.class),any(MailBoxIdPk.class));
		doReturn(mailRdtMasterVOs).when(dao).findRdtMasterDetails(any(RdtMasterFilterVO.class));
		worker.getEDIInterchangeVO(ediMessageVO);
	
			}
	
}

