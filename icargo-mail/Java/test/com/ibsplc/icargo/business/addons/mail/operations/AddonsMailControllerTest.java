package com.ibsplc.icargo.business.addons.mail.operations;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import com.ibsplc.icargo.business.addons.mail.operations.proxy.AddonMailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.feature.AbstractFeatureTest;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.persistence.dao.addons.mail.operations.AddonsMailOperationsDAO;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.EntityManagerMock;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;

public class AddonsMailControllerTest extends AbstractFeatureTest {

	private static final String ADDON_MAIL_OPERATIONS = "addonsmail.operations";
	private static final String MAILBAG_ID = "USDFWADEFRAAACA01200120001200";
	private static final String COMPANY_CODE = "IBS";

	private AddonsMailController spy;
	private AddonsMailOperationsDAO dao;
	private MailBookingDetail entity;

	private AddonMailTrackingDefaultsProxy addonMailTrackingDefaultsProxy;

	@Override
	public void setup() throws Exception {
		EntityManagerMock.mockEntityManager();
		spy = spy(new AddonsMailController());
		dao = mock(AddonsMailOperationsDAO.class);
		entity=mock(MailBookingDetail.class);
		addonMailTrackingDefaultsProxy = mockProxy(AddonMailTrackingDefaultsProxy.class);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_With_No_MailData_Accepted_Status()
			throws SystemException, ProxyException, FinderException, PersistenceException {
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsInCsg = new ArrayList<MailbagVO>();
		Page<MailbagVO> carditMails = new Page<MailbagVO>();

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		MailbagVO mailbagVo = new MailbagVO();

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVo.setAccepted("Y");
		mailbagVOsInCsg.add(mailbagVo);
		int pageNumber = 0;
		carditMails.add(mailbagVo);

		doReturn(carditMails).when(addonMailTrackingDefaultsProxy).findCarditMails(carditEnquiryFilterVO, pageNumber);
		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test
	public void dettachMailBookingDetails_With_No_MailData_ProxyException()
			throws SystemException, ProxyException, FinderException, PersistenceException {
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);
		int pageNumber = 0;

		when(addonMailTrackingDefaultsProxy.findCarditMails(carditEnquiryFilterVO, pageNumber))
				.thenThrow(ProxyException.class);

		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_MailBagSequenceNumber_SystemException()
			throws SystemException, ProxyException, FinderException, PersistenceException {
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();

		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);
		mailbagVOs.add(mailbagVo);
		mailbagVOs.add(mailbagVos);

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		Collection<MailbagVO> mailbagData = new ArrayList<MailbagVO>();
		mailbagData.add(mailbagVo);
		mailbagData.add(mailbagVos);

		when(addonMailTrackingDefaultsProxy.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode))
				.thenThrow(SystemException.class);
		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);
	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_With_No_MailData_SystemException()
			throws SystemException, ProxyException, FinderException, PersistenceException {
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);
		int pageNumber = 0;

		when(addonMailTrackingDefaultsProxy.findCarditMails(carditEnquiryFilterVO, pageNumber))
				.thenThrow(SystemException.class);

		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_PersistenceException()
			throws SystemException, ProxyException, FinderException, PersistenceException {

		long mailSequenceNumber = 6174;
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;
		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);
		mailbagVOs.add(mailbagVo);
		mailbagVOs.add(mailbagVos);

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		doReturn(mailSequenceNumber).when(addonMailTrackingDefaultsProxy)
				.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);

		when(PersistenceController.getEntityManager().getQueryDAO(ADDON_MAIL_OPERATIONS))
				.thenThrow(PersistenceException.class);

		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_Perform_Detach_SystemException()
			throws SystemException, ProxyException, FinderException, PersistenceException {

		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();
		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);
		mailbagVOs.add(mailbagVo);
		mailbagVOs.add(mailbagVos);
		Collection<MailbagVO> mailbagData = new ArrayList<MailbagVO>();
		mailbagData.add(mailbagVo);
		mailbagData.add(mailbagVos);
		long mailSequenceNumber = 6174;
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;

		doReturn(mailSequenceNumber).when(addonMailTrackingDefaultsProxy)
				.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);

		when(((AddonsMailOperationsDAO) PersistenceController.getEntityManager().getQueryDAO(ADDON_MAIL_OPERATIONS))
				.fetchBookedFlightDetailsForMailbag(mailSequenceNumber)).thenReturn(mailBookingDetailVOs);

		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_PerformDetach_BookingStatus()
			throws SystemException, ProxyException, FinderException, PersistenceException {

		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);
		mailbagVOs.add(mailbagVo);
		mailbagVOs.add(mailbagVos);
		Collection<MailbagVO> mailbagData = new ArrayList<MailbagVO>();
		mailbagData.add(mailbagVo);
		mailbagData.add(mailbagVos);
		long mailSequenceNumber = 6174;
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;
		MailBookingDetailVO mailBookingDetailVO = new MailBookingDetailVO();
		mailBookingDetailVO.setCompanyCode(companyCode);

		mailBookingDetailVO.setBookingFlightNumber("0311");
		mailBookingDetailVO.setBookingCarrierCode("AV");
		mailBookingDetailVO.setSegementserialNumber(0);
		mailBookingDetailVO.setBookingStatus(" ");
		mailBookingDetailVO.setSerialNumber(1);

		mailBookingDetailVOs.add(mailBookingDetailVO);

		MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
		mailBookingDetailPK.setCompanyCode(COMPANY_CODE);
		mailBookingDetailPK.setMailSequenceNumber(6174);
		mailBookingDetailPK.setFlightNumber("0311");
		mailBookingDetailPK.setFlightCarrierId(1134);
		mailBookingDetailPK.setFlightSequenceNumber(0);
		mailBookingDetailPK.setSegementserialNumber(0);
		mailBookingDetailPK.setSerialNumber(1);

		doReturn(mailSequenceNumber).when(addonMailTrackingDefaultsProxy)
				.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);

		when(((AddonsMailOperationsDAO) PersistenceController.getEntityManager().getQueryDAO(ADDON_MAIL_OPERATIONS))
				.fetchBookedFlightDetailsForMailbag(mailSequenceNumber)).thenReturn(mailBookingDetailVOs);

		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test(expected = Exception.class)
	public void dettachMailBookingDetails_With_Exceeded_MailData()
			throws SystemException, ProxyException, FinderException, PersistenceException {

		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);
		carditEnquiryFilterVO.setMailCount(1);

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);
		mailbagVOs.add(mailbagVo);
		mailbagVOs.add(mailbagVos);
		Collection<MailbagVO> mailbagData = new ArrayList<MailbagVO>();
		mailbagData.add(mailbagVo);
		mailbagData.add(mailbagVos);
		long mailSequenceNumber = 6174;
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;

		doReturn(mailSequenceNumber).when(addonMailTrackingDefaultsProxy)
				.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);
		spy.dettachMailBookingDetails(mailbagVOs, carditEnquiryFilterVO);

	}

	@Test
	public void dettachMailBookingDetails_SystemException()
			throws SystemException, ProxyException, FinderException, PersistenceException {

		CarditEnquiryFilterVO carditEnquiryFilterVO = new CarditEnquiryFilterVO();
		Collection<MailbagVO> mailbagVOs = new ArrayList<MailbagVO>();
		Collection<MailbagVO> mailbagVOsInCsg = new ArrayList<MailbagVO>();
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		carditEnquiryFilterVO.setCompanyCode(COMPANY_CODE);
		carditEnquiryFilterVO.setIsAWBAttached("Y");
		carditEnquiryFilterVO.setMailbagId(MAILBAG_ID);

		int pageNumber = 0;
		Page<MailbagVO> carditMails = new Page<MailbagVO>();

		MailbagVO mailbagVo = new MailbagVO();
		MailbagVO mailbagVos = new MailbagVO();

		mailbagVo.setCompanyCode(COMPANY_CODE);
		mailbagVo.setMailbagId(MAILBAG_ID);
		mailbagVos.setCompanyCode(COMPANY_CODE);
		mailbagVos.setMailbagId(MAILBAG_ID);

		mailbagVOsInCsg.add(mailbagVo);
		mailbagVOsInCsg.add(mailbagVos);

		carditMails.add(mailbagVo);
		carditMails.add(mailbagVos);

		Collection<MailbagVO> mailbagData = new ArrayList<MailbagVO>();
		mailbagData.add(mailbagVo);
		mailbagData.add(mailbagVos);
		MailBookingDetailVO mailBookingDetailVO = new MailBookingDetailVO();
		mailBookingDetailVO.setCompanyCode(COMPANY_CODE);

		mailBookingDetailVO.setBookingFlightNumber("0311");
		mailBookingDetailVO.setBookingCarrierCode("AV");
		mailBookingDetailVO.setSegementserialNumber(0);
		mailBookingDetailVO.setBookingStatus("BKD");
		mailBookingDetailVO.setSerialNumber(1);

		MailBookingDetailVO mailBookingDetailVOCopy = new MailBookingDetailVO();
		mailBookingDetailVOCopy.setCompanyCode(COMPANY_CODE);

		mailBookingDetailVOCopy.setBookingFlightNumber("0311");
		mailBookingDetailVOCopy.setBookingCarrierCode("AV");
		mailBookingDetailVOCopy.setSegementserialNumber(0);
		mailBookingDetailVOCopy.setBookingStatus("BKD");
		mailBookingDetailVOCopy.setSerialNumber(1);

		mailBookingDetailVOs.add(mailBookingDetailVO);
		mailBookingDetailVOs.add(mailBookingDetailVOCopy);

		MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
		mailBookingDetailPK.setCompanyCode(COMPANY_CODE);
		mailBookingDetailPK.setMailSequenceNumber(6174);
		mailBookingDetailPK.setFlightNumber("0311");
		mailBookingDetailPK.setFlightCarrierId(1134);
		mailBookingDetailPK.setFlightSequenceNumber(0);
		mailBookingDetailPK.setSegementserialNumber(0);
		mailBookingDetailPK.setSerialNumber(1);
		long mailSequenceNumber = 6174;
		String mailbagId = MAILBAG_ID;
		String companyCode = COMPANY_CODE;

		doReturn(carditMails).when(addonMailTrackingDefaultsProxy).findCarditMails(carditEnquiryFilterVO, pageNumber);
		doReturn(mailSequenceNumber).when(addonMailTrackingDefaultsProxy)
				.findMailBagSequenceNumberFromMailIdr(mailbagId, companyCode);

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);

		when(((AddonsMailOperationsDAO) PersistenceController.getEntityManager().getQueryDAO(ADDON_MAIL_OPERATIONS))
				.fetchBookedFlightDetailsForMailbag(mailSequenceNumber)).thenReturn(mailBookingDetailVOs);

		doThrow(SystemException.class).when(addonMailTrackingDefaultsProxy).dettachMailBookingDetails(mailbagVOs);

	}
	
	@Test
	public void findFlightDetailsforAWB_SuccessCase() throws SystemException, PersistenceException{
		
		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		MailBookingDetailVO mailBookingDetailVo = new MailBookingDetailVO();

		MailBookingDetailVO mailBookingDetailVos = new MailBookingDetailVO();

		mailBookingDetailVo.setBookingFlightNumber("AV-0402");
		mailBookingDetailVo.setPol("CDG");
		mailBookingDetailVo.setPou("FRA");
		mailBookingDetailVo.setBookedPieces(4);
		mailBookingDetailVo.setBookedWeight(15);
		mailBookingDetailVo.setAttachedMailBagCount(5);

		mailBookingDetailVos.setBookingFlightNumber("AV-0402");
		mailBookingDetailVos.setPol("CDG");
		mailBookingDetailVos.setPou("FRA");
		mailBookingDetailVos.setBookedPieces(4);
		mailBookingDetailVos.setBookedWeight(15);
		mailBookingDetailVos.setAttachedMailBagCount(6);

		mailBookingDetailVOs.add(mailBookingDetailVo);
		mailBookingDetailVOs.add(mailBookingDetailVos);
		int attachedMailBagsCount = 0;
		String flightNumber = "0402";
		int flightSequenceNumber = 1;
		int flightCarrierid = 1134;
		int segementserialNumber = 1;

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(mailBookingDetailVOs).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
				masterDocumentNumber);
		doReturn(attachedMailBagsCount).when(dao).findAttachedMailBags(companyCode, shipmentPrefix,
				masterDocumentNumber, flightNumber, flightSequenceNumber, flightCarrierid, segementserialNumber);
	
		spy. findFlightDetailsforAWB(companyCode, shipmentPrefix,masterDocumentNumber);
		
	}

	@Test
	public void findFlightDetailsforAWB_ExceptionCase() throws SystemException, PersistenceException {

		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
	
				
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
				 doThrow(PersistenceException.class).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
							masterDocumentNumber);
		spy.findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);

	}
	
	@Test
	public void findFlightDetailsforAWB_When_FlightNumNull() throws SystemException, PersistenceException {

		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		MailBookingDetailVO mailBookingDetailVo = new MailBookingDetailVO();

		MailBookingDetailVO mailBookingDetailVos = new MailBookingDetailVO();

		mailBookingDetailVo.setPol("CDG");
		mailBookingDetailVo.setPou("FRA");
		mailBookingDetailVo.setBookedPieces(4);
		mailBookingDetailVo.setBookedWeight(15);

		mailBookingDetailVos.setPol("CDG");
		mailBookingDetailVos.setPou("FRA");
		mailBookingDetailVos.setBookedPieces(4);
		mailBookingDetailVos.setBookedWeight(15);

		mailBookingDetailVOs.add(mailBookingDetailVo);
		mailBookingDetailVOs.add(mailBookingDetailVos);

		String flightNumber = " ";
		int flightSequenceNumber = 1;
		int flightCarrierid = 1134;
		int segementserialNumber = 1;
		int attachedMailBagsCount = 0;

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(mailBookingDetailVOs).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
				masterDocumentNumber);
		doReturn(attachedMailBagsCount).when(dao).findAttachedMailBags(companyCode, shipmentPrefix,
				masterDocumentNumber, flightNumber, flightSequenceNumber, flightCarrierid, segementserialNumber);

		spy.findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);

	}

	@Test
	public void findFlightDetailsforAWB_When_FlightNumEmpty() throws SystemException, PersistenceException {

		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		MailBookingDetailVO mailBookingDetailVo = new MailBookingDetailVO();

		MailBookingDetailVO mailBookingDetailVos = new MailBookingDetailVO();

		mailBookingDetailVo.setBookingFlightNumber("");
		mailBookingDetailVo.setPol("CDG");
		mailBookingDetailVo.setPou("FRA");
		mailBookingDetailVo.setBookedPieces(4);
		mailBookingDetailVo.setBookedWeight(15);

		mailBookingDetailVo.setBookingFlightNumber("");
		mailBookingDetailVos.setPol("CDG");
		mailBookingDetailVos.setPou("FRA");
		mailBookingDetailVos.setBookedPieces(4);
		mailBookingDetailVos.setBookedWeight(15);

		mailBookingDetailVOs.add(mailBookingDetailVo);
		mailBookingDetailVOs.add(mailBookingDetailVos);

		String flightNumber = " ";
		int flightSequenceNumber = 1;
		int flightCarrierid = 1134;
		int segementserialNumber = 1;
		int attachedMailBagsCount = 0;

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(mailBookingDetailVOs).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
				masterDocumentNumber);
		doReturn(attachedMailBagsCount).when(dao).findAttachedMailBags(companyCode, shipmentPrefix,
				masterDocumentNumber, flightNumber, flightSequenceNumber, flightCarrierid, segementserialNumber);

		spy.findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);

	}

	@Test
	public void findFlightDetailsforAWB_When_MailBookingDetailVOsNull() throws SystemException, PersistenceException {

		
		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(mailBookingDetailVOs).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
				masterDocumentNumber);

		spy.findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);

	}
	
	@Test
	public void findFlightDetailsforAWB_When_MailBookingDetailVOsEmpty() throws SystemException, PersistenceException {


		String companyCode = COMPANY_CODE;
		String shipmentPrefix = "134";
		String masterDocumentNumber = "45679896";
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<MailBookingDetailVO>();

		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(mailBookingDetailVOs).when(dao).findFlightDetailsforAWB(companyCode, shipmentPrefix,
				masterDocumentNumber);

		spy.findFlightDetailsforAWB(companyCode, shipmentPrefix, masterDocumentNumber);

	}

    
	@Test
	public void findManifestBookings_SuccessCase()
			throws SystemException, PersistenceException {
		Page<MailBookingDetailVO> manifestDetailVos =new Page<>();
		
		int pageNumber = 0;
		MailBookingDetailVO mailBookingDetailVO = new MailBookingDetailVO();
		mailBookingDetailVO.setCompanyCode(COMPANY_CODE);
		mailBookingDetailVO.setBookingFlightNumber("0311");
		mailBookingDetailVO.setBookingCarrierCode("AV");
		mailBookingDetailVO.setSegementserialNumber(0);
		mailBookingDetailVO.setBookingStatus("BKD");
		mailBookingDetailVO.setSerialNumber(1);
		manifestDetailVos.add(mailBookingDetailVO);
		
		MailBookingFilterVO mailBookingFilterVO=new MailBookingFilterVO();
		mailBookingFilterVO.setManifestDateFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		mailBookingFilterVO.setManifestDateTo(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		mailBookingFilterVO.setPou("MFM");
		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(manifestDetailVos).when(dao).findManifestBookings(mailBookingFilterVO,pageNumber);
		spy.findManifestBookings(mailBookingFilterVO, pageNumber);
	}
	
	@Test
	public void findManifestBookings_PersistenceException()
			throws SystemException, PersistenceException {
		Page<MailBookingDetailVO> manifestDetailVos =new Page<>();
		
		int pageNumber = 0;
		MailBookingDetailVO mailBookingDetailVO = new MailBookingDetailVO();
		mailBookingDetailVO.setCompanyCode(COMPANY_CODE);
		mailBookingDetailVO.setBookingFlightNumber("0311");
		mailBookingDetailVO.setBookingCarrierCode("AV");
		mailBookingDetailVO.setSegementserialNumber(0);
		mailBookingDetailVO.setBookingStatus("BKD");
		mailBookingDetailVO.setSerialNumber(1);
		manifestDetailVos.add(mailBookingDetailVO);
		
		MailBookingFilterVO mailBookingFilterVO=new MailBookingFilterVO();
		mailBookingFilterVO.setManifestDateFrom(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));
		mailBookingFilterVO.setManifestDateTo(new LocalDate(LocalDate.NO_STATION, Location.NONE, false));

		
		doReturn(dao).when(PersistenceController.getEntityManager()).getQueryDAO(ADDON_MAIL_OPERATIONS);
		doReturn(manifestDetailVos).when(dao).findManifestBookings(mailBookingFilterVO,pageNumber);
		spy.findManifestBookings(mailBookingFilterVO, pageNumber);
	}
	
}
