/**
 * MailbagTest.java Created on Aug 21, 2021
 * @author A-8353
 * Copyright 2005 IBS Software Services (P) Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of IBS Software Services (P) Ltd.
 * Use is subject to license terms.
 */
package com.ibsplc.icargo.business.mail.operations;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;

import com.ibsplc.icargo.business.mail.operations.proxy.SharedAreaProxy;
import com.ibsplc.icargo.business.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.DamagedMailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagHistoryVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.ResditEventVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ConsignmentInformationVO;
import com.ibsplc.icargo.business.msgbroker.message.vo.resdit.ReceptacleInformationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.mail.operations.MailTrackingDefaultsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;

public class MailbagTest extends AbstractFeatureTest {
	private Mailbag mailbagSpy;
	private SharedDefaultsProxy sharedDefaultsProxy;
	private SharedAreaProxy sharedAreaProxy;
	private MailTrackingDefaultsDAO dao;
	private MailController mailcontroller;
	private DocumentController documentController;
	private  static final String MAILBAG_ID="FRCDGADEFRAAACA92855323000024";
	private static final  String PA_CODE="US101";
	private static final String MAIL_OPERATIONS = "mail.operations";
	private static final String mailHisMldMsreqFlag="NO";
	@Override
	public void setup() throws Exception {
		mailbagSpy = spy(new Mailbag());
		sharedDefaultsProxy = mockProxy(SharedDefaultsProxy.class);
		sharedAreaProxy = mockProxy(SharedAreaProxy.class);
		dao = mock(MailTrackingDefaultsDAO.class);
	    mailcontroller =mock(MailController.class);
	    documentController =mock(DocumentController.class);
	    EntityManagerMock.mockEntityManager();
		
	}
	@Test
	public void  populateAttributesOfMailbag() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setMailbagId("FRCDGADEFRAAACA92855323000000");
		mailbagVO.setPaCode(PA_CODE);
		HashMap<String, String> map=new HashMap<>();
		mailbagVO.setMailServiceLevel("Y");
		map.put("mailtracking.defaults.uspsinternationalpa", "US101");
		map.put("mailtracking.domesticmra.usps", "US001");
		map.put("mailtracking.mra.defaults.overwriteweightvalue", "K=0.1,L=1");
		mailbagVO.setLatestStatus("ACP");
		mailbagVO.setFlightSequenceNumber(2);
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailbagSpy.populateAttributes(mailbagVO);   
		
	}
	@Test
	public void  populateAttributesOfMailbagStatusNew() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setPaBuiltFlag("Y");
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode(PA_CODE);
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setRfdFlag("Y");
		mailbagVO.setMailServiceLevel("Y");
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestStatus("NEW");
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.defaults.uspsinternationalpa", "US101");
		map.put("mailtracking.domesticmra.usps", "US001");
		map.put("mailtracking.mra.defaults.overwriteweightvalue", "K=0.1,L=1");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		mailbagSpy.populateAttributes(mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDetailNullOnUpdateAcceptanceDetails() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setMailbagSource("MTK060");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode(PA_CODE);
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setFromPanel("CARDIT");
		mailbagVO.setOperationalFlag("I");
		mailbagSpy.updateAcceptanceFlightDetails(mailbagVO);   
	
	}
	@Test
	public void  stampFirstScanDetailOnUpdateAcceptanceDetails() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setPaBuiltFlag("Y");
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode(PA_CODE);
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setMailCompanyCode("AV");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setOperationalFlag("I");
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn("ARR").when(mailbagSpy).getLatestStatus();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn("CDG").when(mailbagSpy).getFirstScanPort();
		mailbagSpy.updateAcceptanceFlightDetails(mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDateNullDetailOnUpdateAcceptanceDetails() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setPaBuiltFlag("Y");
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode(PA_CODE);
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setMailCompanyCode("");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setOperationalFlag("I");
		doReturn("REC").when(mailbagSpy).getLatestStatus();
		doReturn(null).when(mailbagSpy).getFirstScanDate();
		doReturn("CDG").when(mailbagSpy).getFirstScanPort();
		mailbagSpy.updateAcceptanceFlightDetails(mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanPortNullOnUpdateAcceptanceDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setPaBuiltFlag("Y");
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode(PA_CODE);
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setMailCompanyCode("AV");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setOperationalFlag("U");
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		MailbagHistory mailbagHistory=new MailbagHistory();
		EntityManagerMock.mockEntityManager();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(mailbagHistory).when(mailbagSpy).findMailbagHistoryForEvent(mailbagVO);
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn(null).when(mailbagSpy).getFirstScanPort();
		mailbagSpy.updateAcceptanceFlightDetails(mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDetailNullOnUpdateDetailsForTransfer() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		ContainerVO containerVO= new ContainerVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setMailbagSource("MTK060");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode("US001");
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setFromPanel("CARDIT");
		mailbagVO.setOperationalFlag("I");
		mailbagVO.setMailCompanyCode("SQ");
		mailbagVO.setConsignmentNumber("4566");
		containerVO.setFlightSequenceNumber(2);
		containerVO.setCarrierId(6);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getReqDeliveryTime();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn("CDG").when(mailbagSpy).getScannedPort();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateDetailsForTransfer(containerVO,mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDateNullOnUpdateDetailsForTransfer() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		ContainerVO containerVO= new ContainerVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailSource("WS");
		mailbagVO.setMailCompanyCode("AV");
		containerVO.setCarrierId(0);
		containerVO.setFlightNumber("1234");
		containerVO.setFlightSequenceNumber(2);
		mailbagVO.setPaCode("US001");
		mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn("CDG").when(mailbagSpy).getScannedPort();
		doReturn(null).when(mailbagSpy).getFirstScanDate();
		doReturn("CDG").when(mailbagSpy).getFirstScanPort();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateDetailsForTransfer(containerVO,mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanPortNullOnUpdateDetailsForTransfer() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		ContainerVO containerVO= new ContainerVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailStatus(MailConstantsVO.MAIL_STATUS_HNDRCV);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn("CDG").when(mailbagSpy).getScannedPort();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn(null).when(mailbagSpy).getFirstScanPort();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateDetailsForTransfer(containerVO,mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDetailNullOUpdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setConsignmentDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setDamageFlag("Y");
		mailbagVO.setMailbagSource("MTK060");
		mailbagVO.setDespatch(true);
		mailbagVO.setFlightSequenceNumber(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		mailbagVO.setReqDeliveryTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setMailbagId(MAILBAG_ID);
		mailbagVO.setPaCode("US001");
		mailbagVO.setOnTimeDelivery("Y");
		mailbagVO.setArrivedFlag("Y");
		mailbagVO.setDeliveredFlag("Y");
		mailbagVO.setMailUpdateFlag(true);
		mailbagVO.setTransWindowEndTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setLatestAcceptanceTime(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setAcceptanceScanDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setVolume(new Measure(UnitConstants.VOLUME,10));
		mailbagVO.setLatestStatus("NEW");
		mailbagVO.setFlightSequenceNumber(2);
		mailbagVO.setFromPanel("CARDIT");
		mailbagVO.setOperationalFlag("I");
		mailbagVO.setMailCompanyCode("SQ");
		mailbagVO.setConsignmentNumber("4566");
		mailbagVO.setScannedUser("ICOADMIN");
		mailbagVO.setScannedPort("CDG");
		mailbagVO.setOperationalFlag(MailConstantsVO.OPERATION_FLAG_UPDATE);
		mailbagVO.setNeedDestUpdOnDlv(true);
		DamagedMailbagVO damagedMailbagVO= new DamagedMailbagVO();
		damagedMailbagVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		Collection<DamagedMailbagVO>damagedMailbagVOs=new  ArrayList<>();
		damagedMailbagVOs.add(damagedMailbagVO);
		mailbagVO.setDamagedMailbags(damagedMailbagVOs);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn("NEW").when(mailbagSpy).getLatestStatus();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getReqDeliveryTime();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);   
		
	}
	@Test
	public void  stampFirstScanDateNullOnupdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailSource("WS");
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		DamagedMailbagVO damagedMailbagVO= new DamagedMailbagVO();
		damagedMailbagVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		Collection<DamagedMailbagVO>damagedMailbagVOs=new  ArrayList<>();
		damagedMailbagVOs.add(damagedMailbagVO);
		mailbagVO.setDamagedMailbags(damagedMailbagVOs);
		boolean [] arrDlv={true,true,true};
		mailbagVO.setDamageFlag(MailConstantsVO.FLAG_YES);
		doReturn(arrDlv).when(mailbagSpy).updateIfHistoryExists(mailbagVO);
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn(null).when(mailbagSpy).getFirstScanDate();
		doReturn("CDG").when(mailbagSpy).getFirstScanPort();
		doReturn(10).when(mailbagSpy).getCarrierId();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);    
		
	}
	@Test
	public void  stampFirstScanPortNullOnupdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailSource("WS");
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setScannedPort("CDG");
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn(null).when(mailbagSpy).getFirstScanPort();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn(10).when(mailbagSpy).getCarrierId();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);    
		
	}
	@Test
	public void  stampScanPortNullOnupdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailSource("WS");
		mailbagVO.setScannedDate(new LocalDate(LocalDate.NO_STATION,Location.NONE,true));
		mailbagVO.setScannedPort(null);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn("CDG").when(mailbagSpy).getScannedPort();
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn(10).when(mailbagSpy).getCarrierId();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);    
		
	}
	@Test
	public void  stampScanDateNullOnupdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailSource("WS");
		mailbagVO.setScannedDate(null);
		mailbagVO.setScannedPort("CDG");
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn(new LocalDate(LocalDate.NO_STATION,Location.NONE,true).toCalendar()).when(mailbagSpy).getFirstScanDate();
		doReturn(10).when(mailbagSpy).getCarrierId();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);    
		
	}
	@Test
	public void  stampScanDetailsNullOnupdateArrivalDetails() throws SystemException, PersistenceException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setMailSource("WS");
		mailbagVO.setScannedDate(null);
		mailbagVO.setScannedPort(null);
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		EntityManagerMock.mockEntityManager();
		doReturn("AV").when(mailbagSpy).getMailCompanyCode();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		String routingDetails="";
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		doReturn(routingDetails).when(dao).findRoutingDetailsForConsignment(mailbagVO);
		when(Mailbag.findMailbagHistories("AV","",12445))
		.thenReturn(mailbagHistoryVOs);
		doReturn(null).when(mailbagSpy).getFirstScanDate();
		doReturn(10).when(mailbagSpy).getCarrierId();
		MailbagPK mailbagPK=new MailbagPK();
		mailbagPK.setCompanyCode("AV");
		mailbagPK.setMailSequenceNumber(12938);
		HashMap<String, String> map=new HashMap<>();
		map.put("mailtracking.domesticmra.usps", "US001");
		doReturn(map).when(sharedDefaultsProxy).findSystemParameterByCodes(anyCollectionOf(String.class));
		doReturn(mailbagPK).when(mailbagSpy).getMailbagPK();
		mailbagSpy.updateArrivalDetails(mailbagVO);    
		
	}
	@Test
	public void  calculateMailbagVolume_withValidPA() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode("US101");
		String commodityCode="";
		CommodityValidationVO commodityValidationVO = null;
		doReturn(commodityCode).when(mailcontroller).findSystemParameterValue("mailtracking.defaults.booking.commodity");
		doReturn(commodityValidationVO).when(mailcontroller).validateCommodity("AA",commodityCode,"US101");
		Collection<String> parameterCodes = new ArrayList<>();    
		parameterCodes.add("station.defaults.unit.volume");
		// Map<String,String> stationParameters = null;  
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10));
		//doReturn(stationParameters).when(sharedAreaProxy).findStationParametersByCode("AA","DFW",anyCollectionOf(String.class));
		mailbagSpy.calculateMailbagVolume(mailbagVO);    
		
	}
	
	@Test
	public void  calculateMailbagVolume_withZeroCommodityCode() throws SystemException{

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode(null);
		String commodityCode="";
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		doReturn(commodityCode).when(mailcontroller).findSystemParameterValue("mailtracking.defaults.booking.commodity");
		doReturn(commodityValidationVO).when(mailcontroller).validateCommodity("AA","MAIL","US101");
		commodityValidationVO.setDensityFactor(10);
		Collection<String> parameterCodes = new ArrayList<>();    
		parameterCodes.add("station.defaults.unit.volume");
		// Map<String,String> stationParameters = null;  
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10)); 
		//doReturn(stationParameters).when(sharedAreaProxy).findStationParametersByCode("AA","DFW",anyCollectionOf(String.class));
		mailbagSpy.calculateMailbagVolume(mailbagVO);    
		
	   
		
	}  
	@Test
	public void  validateCommodity_validDensityFactor() throws SystemException {

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode("US101");
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setDensityFactor(10);
		mailbagSpy.validateCommodity(commodityValidationVO,mailbagVO);    
			   
		
	}  
	@Test
	public void  validateCommodity_zeroDensityFactor() throws SystemException {

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode("US101");
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setDensityFactor(0);
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,22)); 
		mailbagSpy.validateCommodity(commodityValidationVO,mailbagVO);    
			   
		
	}  
	@Test
	public void  validateCommodity_weightnull() throws SystemException {

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode("US101");
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setDensityFactor(0);
		mailbagVO.setWeight(null); 
		mailbagSpy.validateCommodity(commodityValidationVO,mailbagVO);    
			   
		
	}  
	
	@Test
	public void  validateCommodity_withStationparameters() throws SystemException{

		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setCompanyCode("AA");
		mailbagVO.setPaCode("US001");
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		 HashMap<String, String> stationParameters=new HashMap<>();
		 stationParameters.put("station.defaults.unit.volume", "F");
		doReturn(stationParameters).when(sharedAreaProxy).findStationParametersByCode(any(String.class),any(String.class),anyCollectionOf(String.class));
		 mailbagSpy.validateCommodity(commodityValidationVO,mailbagVO);  
	}  
	
	@Test
	public void shouldPopulateSenderAndRecipientForResdit() throws Exception {
		ResditEventVO resditEventVO = new ResditEventVO();
		resditEventVO.setCompanyCode(getCompanyCode());
		resditEventVO.setEventPort("SYD");
		ConsignmentInformationVO consignmentInformationVO = new ConsignmentInformationVO();
		consignmentInformationVO.setTransportInformationVOs(new ArrayList<>());
		ReceptacleInformationVO receptacleInformationVO = new ReceptacleInformationVO();
		receptacleInformationVO.setReceptacleID(MAILBAG_ID);
		receptacleInformationVO.setMailSequenceNumber(2);
		EntityManagerMock.mockEntityManager();
		mailbagSpy.insertMailbagHistoryForResdit(resditEventVO, consignmentInformationVO, receptacleInformationVO);
	}
	
	@Test
	public void  findMailbagHistories() throws SystemException, PersistenceException{
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		Mailbag.findMailbagHistories("AV","",12445);
	}
	@Test
	public void  findMailbagHistories_throwsException() throws SystemException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(SystemException.class).when(dao).findMailbagHistories(null,MAILBAG_ID,0,mailHisMldMsreqFlag);
		Mailbag.findMailbagHistories("AV","",12445);
	}
	@Test
	public void  findMailbagHistoriesWithoutCarditDetails() throws SystemException, PersistenceException{
		Collection<MailbagHistoryVO>  mailbagHistoryVOs = new ArrayList<>();
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doReturn(mailbagHistoryVOs).when(dao).findMailbagHistories("AV",MAILBAG_ID,12443,mailHisMldMsreqFlag);
		Mailbag.findMailbagHistoriesWithoutCarditDetails("AV",MAILBAG_ID);
	}
	@Test
	public void  findMailbagHistoriesWithoutCarditDetails_throwsException() throws SystemException, PersistenceException{
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(MAIL_OPERATIONS);
		doThrow(SystemException.class).when(dao).findMailbagHistories(null,MAILBAG_ID,0,mailHisMldMsreqFlag);
		Mailbag.findMailbagHistoriesWithoutCarditDetails("AV",MAILBAG_ID);
	}
	@Test
	public void  shouldSetMailbagVolume() throws SystemException{
		MailbagVO mailbagVO= new MailbagVO();
		mailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,10.25));
		CommodityValidationVO commodityValidationVO = new CommodityValidationVO();
		commodityValidationVO.setDensityFactor(120);
		mailbagSpy.validateCommodity(commodityValidationVO,mailbagVO);
	}
}
