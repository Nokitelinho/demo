package com.ibsplc.icargo.business.addons.mail.operations;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.ibsplc.icargo.business.addons.mail.operations.proxy.AddonMailTrackingDefaultsProxy;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.AddonsMailOperationProxy;

import org.apache.commons.beanutils.BeanUtils;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.FlightOperationsProxy;
import com.ibsplc.icargo.business.addons.mail.operations.MailBookingDetail;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.MailOperationsProxy;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationalShipmentProxy;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingDetailVO;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsFltHandlingProxy;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.addons.mail.operations.vo.MailBookingFilterVO;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedAirlineProxy;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.flight.operation.vo.FlightSegmentSummaryVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.MailController;
import com.ibsplc.icargo.business.mail.operations.MailDefaultStorageUnitException;
import com.ibsplc.icargo.business.mail.operations.Mailbag;
import com.ibsplc.icargo.business.mail.operations.CapacityBookingProxyException;
import com.ibsplc.icargo.business.mail.operations.ContainerAssignmentException;
import com.ibsplc.icargo.business.mail.operations.DuplicateDSNException;
import com.ibsplc.icargo.business.mail.operations.DuplicateMailBagsException;
import com.ibsplc.icargo.business.mail.operations.FlightClosedException;
import com.ibsplc.icargo.business.mail.operations.ForceAcceptanceException;
import com.ibsplc.icargo.business.mail.operations.InvalidFlightSegmentException;
import com.ibsplc.icargo.business.mail.operations.MailBookingException;
import com.ibsplc.icargo.business.mail.operations.vo.CarditEnquiryFilterVO;
import com.ibsplc.icargo.business.mail.operations.MailMLDBusniessException;
import com.ibsplc.icargo.business.mail.operations.MailTrackingBusinessException;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.MailUploadController;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OfficeOfExchangeVO;
import com.ibsplc.icargo.business.mail.operations.MailbagPK;
import com.ibsplc.icargo.business.mail.operations.ULDDefaultsProxyException;
import com.ibsplc.icargo.business.mail.operations.errorhandling.MailHHTBusniessException;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.floworchestration.context.SpringAdapter;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.icargo.business.mail.operations.vo.ScannedMailDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.TransferManifestVO;
import com.ibsplc.xibase.util.log.factory.LogFactory;
import com.ibsplc.icargo.business.mail.operations.vo.converter.MailtrackingDefaultsVOConverter;
import com.ibsplc.xibase.util.time.DateUtilities;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.CTOShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ManifestFilterVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ShipmentManifestVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldManifestVO;
import com.ibsplc.icargo.business.operations.shipment.cto.vo.CTOAcceptanceVO;
import com.ibsplc.icargo.business.operations.shipment.vo.AcceptanceFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.airline.vo.AirlineValidationVO;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.persistence.dao.addons.mail.operations.AddonsMailOperationsDAO;
import com.ibsplc.xibase.server.framework.persistence.EntityManager;
import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.xibase.server.framework.persistence.PersistenceController;
import com.ibsplc.xibase.server.framework.persistence.PersistenceException;
import com.ibsplc.xibase.server.framework.persistence.tx.Transaction;
import com.ibsplc.xibase.server.framework.persistence.tx.TransactionProvider;

import com.ibsplc.xibase.server.framework.vo.ErrorVO;

public class AddonsMailController {

	private static final Log LOGGER = LogFactory.getLogger(AddonsMailController.class.getSimpleName());
	private static final String SYS_PARA_IMPORT_MAILSFOR_SPLITBOOKING = "mail.operations.importmailsforsplitbooking";
	private static final String EVENT_ACP = "ACP";
	private static final String EVENT_ARR = "ARR";
	private static final String EVENT_DLV = "DLV";
	public static final String STATUS_EXECUTED = "E";
	private static final String FIRST_FLIGHT = "F";
	private static final String BOOKED_FLIGHT = "B";
	public static final String FLAG_YES = "Y";
	private static final String ERROR_INVALID_EXCHANGE = "Invalid Office Of Exchange";
	private static final String ERROR_INVALID_DSN = "Invalid DSN";
	public static final String STATUS_AGG_TYPE_LEASE = "L";
	public static final String DATE_FORMAT = "dd-MMM-yyy HH:mm:ss";
	private static final String MAIL_COUNT_EXCEED = "Mailbag Count exceeded";
	private static final String CLASS = "AddonsMailController";
	private static final String EVENT_RMPTRA = "RMPTRA";
	private static final String EVENT_TRA = "TRA";
	private static final String STATUS_ROUTE_INCOMPLETE = "Please select flights for all segments";
	private static final String STATUS_DETACH = "AWB is either executed or accepted";

	public Page<MailBookingDetailVO> findMailBookingAWBs(MailBookingFilterVO mailBookingFilterVO, int pageNumber)
			throws SystemException {

		return MailBookingDetail.findMailBookingAWBs(mailBookingFilterVO, pageNumber);
	}

	public int saveMailBookingDetails(Collection<MailbagVO> mailbagVOs, MailBookingDetailVO mailBookingDetailVO,
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException, ProxyException {

		Collection<MailbagVO> mailbagVOsInCsg = null;
		int mailCountFromSyspar = carditEnquiryFilterVO.getMailCount();
		if (mailbagVOs.isEmpty()) {
			carditEnquiryFilterVO.setConsignmentLevelAWbAttachRequired(FLAG_YES);
			mailbagVOsInCsg = Proxy.getInstance().get(MailOperationsProxy.class).findCarditMails(carditEnquiryFilterVO,
					0);
			if (mailbagVOsInCsg != null && !mailbagVOsInCsg.isEmpty()) {
				mailbagVOs.addAll(mailbagVOsInCsg);
			}
		}
		if (!mailbagVOs.isEmpty() && (mailCountFromSyspar > 0 && mailbagVOs.size() > mailCountFromSyspar)) {
			throw new SystemException(MAIL_COUNT_EXCEED);
		}

		// validate destination of AWB
		int mismatchCount = 0;
		if (mailBookingDetailVO != null && mailBookingDetailVO.isDestinationCheckReq()) {
			mismatchCount = validateAWBDestination(mailbagVOs, mailBookingDetailVO);
		}
		if (mismatchCount == 0) {
			validateSaveBookingDetails(mailBookingDetailVO, mailbagVOs);
		}
		return mismatchCount;
	}

	private void validateSaveBookingDetails(MailBookingDetailVO mailBookingDetailVO, Collection<MailbagVO> mailbagVOs)
			throws ProxyException, SystemException {
		Collection<MailBookingDetailVO> mailBkgDetailVOs = new ArrayList<>();
		mailBkgDetailVOs.add(mailBookingDetailVO);
		validateFlightDateandTime(mailBookingDetailVO);
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;

		if (mailBookingDetailVO.isSplitBooking()) {
			mailBookingDetailVOs = populateSplitBookedDetails(mailBookingDetailVO, mailbagVOs);
		} else {
			mailBookingDetailVOs = populateBookedDetails(mailBookingDetailVO, mailbagVOs);
		}
		if (mailBookingDetailVOs != null && !mailBookingDetailVOs.isEmpty()) {
			for (MailBookingDetailVO mailBookingDetailVOAttached : mailBookingDetailVOs) {

				if (mailBookingDetailVOAttached.getMasterDocumentNumber() == null
						|| mailBookingDetailVOAttached.getMasterDocumentNumber().isEmpty()) {
					new MailBookingDetail(mailBookingDetailVOAttached);
				}
			}
		}

		Proxy.getInstance().get(MailOperationsProxy.class).attachAWBForMail(mailBkgDetailVOs, mailbagVOs);
		MailController mailController = (MailController) SpringAdapter.getInstance().getBean("mAilcontroller");
		mailController.flagHistoryForMailAwbAttachment(mailbagVOs);
	}

	public void validateMailTags(Collection<MailbagVO> selectedMailBagVO) throws SystemException, ProxyException {

		OfficeOfExchangeVO officeOfExchangeVO = null;
		for (MailbagVO mailbagVO : selectedMailBagVO) {
			try {
				officeOfExchangeVO = (Proxy.getInstance().get(MailOperationsProxy.class))
						.validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getOoe());
			} catch (SystemException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
			if (officeOfExchangeVO == null) {
				throw new SystemException(ERROR_INVALID_EXCHANGE);
			}
			try {
				officeOfExchangeVO = (Proxy.getInstance().get(MailOperationsProxy.class))
						.validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getDoe());
			} catch (SystemException ex) {
				throw new SystemException(ex.getMessage(), ex);
			}
			if (officeOfExchangeVO == null) {
				throw new SystemException(ERROR_INVALID_EXCHANGE);
			}

			try {
				Integer.parseInt(mailbagVO.getDespatchSerialNumber().trim());
			} catch (NumberFormatException exception) {
				throw new SystemException(ERROR_INVALID_DSN, exception);
			}

		}

	}

	private void validateFlightDateandTime(MailBookingDetailVO mailBookingDetailVO)
			throws SystemException, ProxyException {

		Collection<FlightValidationVO> flightValidationVOs = null;
		MailBookingDetailVO mailBookingDetailVOToCompare = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<FlightFilterVO> flightFilterVOs = null;
		try {
			flightFilterVOs = constructFlightFilterVOs(mailBookingDetailVO);
			if (!flightFilterVOs.isEmpty()) {
				for (FlightFilterVO flightFilterVO : flightFilterVOs) {
					flightValidationVOs = (Proxy.getInstance().get(MailOperationsProxy.class))
							.validateFlight(flightFilterVO);
					if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
						for (FlightValidationVO flightValidationVO : flightValidationVOs) {
							boolean isFirstFlight = false;
							populateMailBookingDetailVO(mailBookingDetailVOToCompare, mailBookingDetailVO,
									isFirstFlight, flightValidationVO, flightFilterVO, logonAttributes);
						}
					}
				}
			}

		} catch (SystemException e) {
			throw new SystemException(e.getMessage(), e);
		}
		mailBookingDetailVO.setSegementserialNumber(getSegmentSerialNumber(mailBookingDetailVO));

		for (MailBookingDetailVO bookedFlightVO : mailBookingDetailVO.getBookedFlights()) {
			bookedFlightVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
			bookedFlightVO.setSegementserialNumber(getSegmentSerialNumber(bookedFlightVO));
		}
	}

	private void populateMailBookingDetailVO(MailBookingDetailVO mailBookingDetailVOToCompare,
			MailBookingDetailVO mailBookingDetailVO, boolean isFirstFlight, FlightValidationVO flightValidationVO,
			FlightFilterVO flightFilterVO, LogonAttributes logonAttributes) {
		if ((logonAttributes.getOwnAirlineCode().equals(flightValidationVO.getCarrierCode()))
				|| (!logonAttributes.getOwnAirlineCode().equals(flightValidationVO.getCarrierCode())
						&& flightValidationVO.getAgreementType() != null
						&& STATUS_AGG_TYPE_LEASE.equals(flightValidationVO.getAgreementType()))) {

			if (mailBookingDetailVOToCompare == null) {
				isFirstFlight = true;
				mailBookingDetailVOToCompare = new MailBookingDetailVO();
				mailBookingDetailVOToCompare.setBookingFlightDate(flightValidationVO.getStd());
				mailBookingDetailVO.setBookingFlightNumber(flightFilterVO.getFlightNumber());
				mailBookingDetailVO.setBookingCarrierCode(flightFilterVO.getCarrierCode());
				mailBookingDetailVO.setBookingFlightCarrierid(flightFilterVO.getFlightCarrierId());
				mailBookingDetailVO.setBookingFlightSequenceNumber(
						Integer.parseInt(flightFilterVO.getFlightSequenceNumber() + ""));
				mailBookingDetailVO.setOrigin(flightFilterVO.getSegmentOrigin());
				mailBookingDetailVO.setDestination(flightFilterVO.getSegmentDestination());

			}

			if (!isFirstFlight) {
				String firstDate = mailBookingDetailVOToCompare.getBookingFlightDate().toDisplayFormat(DATE_FORMAT);
				String secondDate = flightValidationVO.getStd().toDisplayFormat(DATE_FORMAT);
				if (DateUtilities.isGreaterThan(firstDate, secondDate, DATE_FORMAT)) {
					mailBookingDetailVOToCompare.setBookingFlightDate(flightValidationVO.getStd());
					mailBookingDetailVO.setBookingFlightNumber(flightFilterVO.getFlightNumber());
					mailBookingDetailVO.setBookingCarrierCode(flightFilterVO.getCarrierCode());
					mailBookingDetailVO.setBookingFlightCarrierid(flightFilterVO.getFlightCarrierId());
					mailBookingDetailVO.setBookingFlightSequenceNumber(
							Integer.parseInt(flightFilterVO.getFlightSequenceNumber() + ""));
					mailBookingDetailVO.setOrigin(flightFilterVO.getSegmentOrigin());
					mailBookingDetailVO.setDestination(flightFilterVO.getSegmentDestination());
				}
			}
		}
	}

	private int validateAWBDestination(Collection<MailbagVO> mailbagVOs, MailBookingDetailVO mailBookingDetailVO)
			throws SystemException, ProxyException {
		int mismatchCount = 0;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {

			for (MailbagVO mailbagVO : mailbagVOs) {
				String mailDestination = null;
				OfficeOfExchangeVO officeOfExchangeVO = null;

				officeOfExchangeVO = (Proxy.getInstance().get(MailOperationsProxy.class))
						.validateOfficeOfExchange(mailbagVO.getCompanyCode(), mailbagVO.getDoe());

				if (officeOfExchangeVO != null && officeOfExchangeVO.getAirportCode() != null
						&& !officeOfExchangeVO.getAirportCode().isEmpty()) {
					mailDestination = officeOfExchangeVO.getAirportCode();
				} else {
					mailDestination = (Proxy.getInstance().get(MailOperationsProxy.class))
							.findNearestAirportOfCity(mailbagVO.getCompanyCode(), mailbagVO.getDoe());
				}
				if (mailDestination != null && mailBookingDetailVO.getAwbDestination() != null
						&& !mailDestination.equalsIgnoreCase(mailBookingDetailVO.getAwbDestination())) {
					mismatchCount = mismatchCount + 1;
				}
			}

		}
		return mismatchCount;
	}

	public Collection<MailBookingDetailVO> populateSplitBookedDetails(MailBookingDetailVO mailBookingDetailVO,
			Collection<MailbagVO> mailbagVOs) throws SystemException, ProxyException {

		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<>();
		MailBookingDetailVO mailBookingDetailVOToAttach = null;
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				mailBookingDetailVOToAttach = new MailBookingDetailVO();

				try {
					BeanUtils.copyProperties(mailBookingDetailVOToAttach, mailBookingDetailVO);
				} catch (IllegalAccessException e) {
					throw new SystemException(e.getMessage(), e);
				} catch (InvocationTargetException ex) {
					throw new SystemException(ex.getMessage(), ex);
				}

				// added by a-8061 for ICRD-229330 end

				mailBookingDetailVOToAttach.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
				long mailSequenceNumber = 0;
				try {
					mailSequenceNumber = (Proxy.getInstance().get(MailOperationsProxy.class))
							.findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
				} catch (SystemException e) {
					throw new SystemException(e.getMessage(), e);
				}
				mailBookingDetailVOToAttach.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOToAttach.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
				mailbagVO.setMailSequenceNumber(mailSequenceNumber);
				mailBookingDetailVOs.add(mailBookingDetailVOToAttach);
			}
		}
		return mailBookingDetailVOs;
	}

	public Collection<MailBookingDetailVO> populateBookedDetails(MailBookingDetailVO mailBookingDetailVO,
			Collection<MailbagVO> mailbagVOs) throws SystemException, ProxyException {

		Collection<MailBookingDetailVO> mailBookingDetailVOs = new ArrayList<>();
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {

				for (MailBookingDetailVO bookedFlight : mailBookingDetailVO.getBookedFlights()) {
					populateBookingDetails(bookedFlight, mailBookingDetailVO, mailbagVO,mailBookingDetailVOs);
				}
			}
		}
		return mailBookingDetailVOs;
	}

	private Collection<MailBookingDetailVO> populateBookingDetails(MailBookingDetailVO bookedFlight,
			MailBookingDetailVO mailBookingDetailVO, MailbagVO mailbagVO, Collection<MailBookingDetailVO> mailBookingDetailVOs) throws SystemException, ProxyException {
		MailBookingDetailVO mailBookingDetailVOToAttach = new MailBookingDetailVO();
		try {
			BeanUtils.copyProperties(mailBookingDetailVOToAttach, bookedFlight);
		} catch (IllegalAccessException e) {
			throw new SystemException(e.getMessage(), e);
		} catch (InvocationTargetException ex) {
			throw new SystemException(ex.getMessage(), ex);
		}

		mailBookingDetailVOToAttach.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
		mailBookingDetailVOToAttach.setCompanyCode(mailBookingDetailVO.getCompanyCode());
		long mailSequenceNumber = 0;
		try {
			mailSequenceNumber = (Proxy.getInstance().get(MailOperationsProxy.class))
					.findMailSequenceNumber(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
		} catch (SystemException e) {
			throw new SystemException(e.getMessage(), e);
		}
		mailBookingDetailVOToAttach.setMailSequenceNumber(mailSequenceNumber);
		mailBookingDetailVOToAttach.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		mailBookingDetailVOToAttach.setAttachmentSource(mailBookingDetailVO.getAttachmentSource());
		
		if(mailBookingDetailVOToAttach.getBookingFlightNumber()==null &&
				mailBookingDetailVOToAttach.getPlannedFlight()!=null){
			mailBookingDetailVOToAttach.setBookingFlightNumber(mailBookingDetailVOToAttach.getPlannedFlight().split(" ")[1]);
		}

		mailbagVO.setMailSequenceNumber(mailSequenceNumber);
		mailBookingDetailVOs.add(mailBookingDetailVOToAttach);
		return mailBookingDetailVOs;
	}

	private Collection<FlightFilterVO> constructFlightFilterVOs(MailBookingDetailVO mailBookingDetailVO) {
		Collection<FlightFilterVO> flightFilterVOs = new ArrayList<>();
		FlightFilterVO flightFilterVO = null;
		String[] flightNumbr=null;

		if(mailBookingDetailVO.getSelectedFlightNumber()!=null){
			 flightNumbr = mailBookingDetailVO.getSelectedFlightNumber().split(",");
		for (int i = 0; i < flightNumbr.length; i++) {
			flightFilterVO = new FlightFilterVO();
			String[] flightDetails = flightNumbr[i].split(" ");

			if (flightDetails[0] != null && flightDetails[0].trim().length() > 0) {

				flightFilterVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
				flightFilterVO.setFlightCarrierId(Integer.parseInt(flightDetails[3]));
				flightFilterVO.setCarrierCode(flightDetails[2]);
				flightFilterVO.setFlightNumber(flightDetails[0]);
				flightFilterVO.setFlightDate(mailBookingDetailVO.getBookingFlightDate());
				flightFilterVO.setAirportCode(mailBookingDetailVO.getAwbOrgin());
				flightFilterVO.setStation(mailBookingDetailVO.getBookingStation());
				flightFilterVO.setFlightSequenceNumber(Integer.parseInt(flightDetails[4]));
				flightFilterVO.setSegmentOrigin(flightDetails[5]);
				flightFilterVO.setSegmentDestination(flightDetails[6]);
				flightFilterVOs.add(flightFilterVO);
			}
		}
			 
		}else if(mailBookingDetailVO.getPlannedFlight()!=null){
			 flightNumbr = mailBookingDetailVO.getPlannedFlight().split(",");
			 for (int i = 0; i < flightNumbr.length; i++) {
					flightFilterVO = new FlightFilterVO();
					String[] flightDetails = flightNumbr[i].split(" ");

						flightFilterVO.setCompanyCode(mailBookingDetailVO.getCompanyCode());
						flightFilterVO.setFlightCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
						flightFilterVO.setCarrierCode(flightDetails[0]);
						flightFilterVO.setFlightNumber(flightDetails[1]);
						flightFilterVO.setFlightDate(mailBookingDetailVO.getFlightDate());
						flightFilterVO.setAirportCode(mailBookingDetailVO.getOrigin());
						flightFilterVO.setStation(mailBookingDetailVO.getBookingStation());
						flightFilterVO.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
						flightFilterVO.setSegmentOrigin(mailBookingDetailVO.getPol());
						flightFilterVO.setSegmentDestination(mailBookingDetailVO.getPou());
						flightFilterVOs.add(flightFilterVO);
					}
				}
		
		return flightFilterVOs;
	}

	public void performMailAWBTransactions(MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {

		LOGGER.entering(CLASS, "performMailAWBTransactions");

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String importMailsForSplitBooking = findSystemParameterValue(SYS_PARA_IMPORT_MAILSFOR_SPLITBOOKING);

		LOGGER.log(Log.FINE, "Event Code = ", mailFlightSummaryVO.getEventCode());

		switch (mailFlightSummaryVO.getEventCode()) {
		case EVENT_ACP:
			performMailExportOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
			break;
		case EVENT_ARR:
			performMailArrivalOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
			break;
		case EVENT_DLV:
			performMailDeliveryOperations(mailFlightSummaryVO, logonAttributes);
			break;
		case EVENT_RMPTRA:
			performMailTransferOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
			break;
		case EVENT_TRA:
			performMailTransferOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
			break;
		default:
			break;
		}
		LOGGER.exiting(CLASS, "performMailAWBTransactions");
	}

	public void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs, CarditEnquiryFilterVO carditEnquiryFilterVO)
			throws SystemException, FinderException {

		Collection<MailbagVO> mailbagData = findMailBagVos(mailbagVOs, carditEnquiryFilterVO);

		if (!mailbagData.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagData) {
				
				findShipmentVos(mailbagVO);

				long mailSequenceNumber = mailSequenceNumber(mailbagVO);

				if (mailSequenceNumber != 0) {
					Collection<MailBookingDetailVO> mailBookingDetailVOs = fetchBookedFlightDetailsForMailbag(
							mailSequenceNumber);

					performDetach(mailSequenceNumber, mailBookingDetailVOs);
				}

			}

			dettachMailBookingDetails(mailbagVOs);

		}
	}
	
	private void findShipmentVos(MailbagVO mailbagVO) throws SystemException {
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
		shipmentFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
		shipmentFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
		shipmentFilterVO.setDocumentNumber(mailbagVO.getDocumentNumber());

		shipmentFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
		shipmentFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
		shipmentFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());

		
		Collection<ShipmentVO> shipmentVOs = null;
		try {
			shipmentVOs = new OperationsShipmentProxy().findShipments(shipmentFilterVO);
		} catch (ProxyException e) {
			LOGGER.log(Log.INFO, e);
		}
		Collection<ErrorVO> errors = new ArrayList<>();
		if(shipmentVOs!=null && !shipmentVOs.isEmpty()){
			for(ShipmentVO shipmentVO : shipmentVOs){
				if(STATUS_EXECUTED.equals(shipmentVO.getShipmentStatus())){
					ErrorVO errorVO = new ErrorVO(STATUS_DETACH);
					errors.add(errorVO);
					throw new SystemException(errors);
				}
			}
		}
	}
	

	private Collection<MailbagVO> findMailBagVos(Collection<MailbagVO> mailbagVOs,
			CarditEnquiryFilterVO carditEnquiryFilterVO) throws SystemException {
		Collection<MailbagVO> mailbagVOsInCsg = null;
		if (mailbagVOs.isEmpty()) {
			carditEnquiryFilterVO.setConsignmentLevelAWbAttachRequired(MailConstantsVO.FLAG_YES);
			mailbagVOsInCsg = findCarditMails(carditEnquiryFilterVO, 0);

			if (mailbagVOsInCsg != null && !mailbagVOsInCsg.isEmpty()) {

				for (MailbagVO mailbagVO : mailbagVOsInCsg) {
					if (!"Y".equals(mailbagVO.getAccepted())) {

						mailbagVOs.add(mailbagVO);
					} else {
						throw new SystemException(STATUS_DETACH);
					}
				}
			}
		}
		int mailCountFromSyspar = carditEnquiryFilterVO.getMailCount();
		if (!mailbagVOs.isEmpty() && (mailCountFromSyspar > 0 && mailbagVOs.size() > mailCountFromSyspar)) {

			throw new SystemException("mailtracking.defaults.searchconsignment.selectedmailcountexceeded");
		}

		return mailbagVOs;

	}

	private void performDetach(long mailSequenceNumber, Collection<MailBookingDetailVO> mailBookingDetailVOs)
			throws SystemException, FinderException {
		Collection<ErrorVO> errors = new ArrayList<>();
		if (mailBookingDetailVOs != null && !mailBookingDetailVOs.isEmpty()) {
			for (MailBookingDetailVO mailBookingDetailVO : mailBookingDetailVOs) {

				if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBookingDetailVO.getBookingStatus())
						|| MailConstantsVO.MAIL_STATUS_NEW.equals(mailBookingDetailVO.getBookingStatus())) {

					MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
					mailBookingDetailPK.setCompanyCode(mailBookingDetailVO.getCompanyCode());
					mailBookingDetailPK.setMailSequenceNumber(mailSequenceNumber);
					mailBookingDetailPK.setFlightNumber(mailBookingDetailVO.getBookingFlightNumber());
					mailBookingDetailPK.setFlightCarrierId(mailBookingDetailVO.getBookingFlightCarrierid());
					mailBookingDetailPK.setFlightSequenceNumber(mailBookingDetailVO.getBookingFlightSequenceNumber());
					mailBookingDetailPK.setSegementserialNumber(mailBookingDetailVO.getSegementserialNumber());
					mailBookingDetailPK.setSerialNumber(mailBookingDetailVO.getSerialNumber());
					mailBookingDetail(mailBookingDetailPK);

				} else {
					throw new SystemException(errors);
				}

			}
		} else {
			LOGGER.entering("dettachMailBookingDetails", "dettachMailBookingDetailsElsee");
			throw new SystemException(errors);
		}
	}

	private long mailSequenceNumber(MailbagVO mailbagVO) {
		long mailSequenceNumber = 0;
		AddonMailTrackingDefaultsProxy addonMailTrackingDefaultsProxy = Proxy.getInstance()
				.get(AddonMailTrackingDefaultsProxy.class);
		try {
			mailSequenceNumber = addonMailTrackingDefaultsProxy
					.findMailBagSequenceNumberFromMailIdr(mailbagVO.getMailbagId(), mailbagVO.getCompanyCode());
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.INFO, e);
		}
		return mailSequenceNumber;

	}

	private void dettachMailBookingDetails(Collection<MailbagVO> mailbagVOs) {
		AddonMailTrackingDefaultsProxy addonMailTrackingDefaultsProxy = Proxy.getInstance()
				.get(AddonMailTrackingDefaultsProxy.class);
		try {
			addonMailTrackingDefaultsProxy.dettachMailBookingDetails(mailbagVOs);
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.INFO, e);
		}
	}

	private void mailBookingDetail(MailBookingDetailPK mailBookingDetailPK) throws FinderException, SystemException {
		MailBookingDetail mailBookingFlightDetail;
		mailBookingFlightDetail = MailBookingDetail.find(mailBookingDetailPK);
		if (mailBookingFlightDetail != null) {
			mailBookingFlightDetail.remove();
		}
	}

	private Page<MailbagVO> findCarditMails(CarditEnquiryFilterVO carditEnquiryFilterVO, int pageNumber)
			throws SystemException {
		LOGGER.entering(CLASS, "findCarditMails");
		Page<MailbagVO> carditMails = null;
		AddonMailTrackingDefaultsProxy addonMailTrackingDefaultsProxy = Proxy.getInstance()
				.get(AddonMailTrackingDefaultsProxy.class);
		try {
			carditMails = addonMailTrackingDefaultsProxy.findCarditMails(carditEnquiryFilterVO, pageNumber);
		} catch (ProxyException | SystemException e) {
			LOGGER.log(Log.INFO, e);
		}
		return carditMails;

	}

	private Collection<MailBookingDetailVO> fetchBookedFlightDetailsForMailbag(long mailSequenceNumber)
			throws SystemException {
		return constructDAO().fetchBookedFlightDetailsForMailbag(mailSequenceNumber);

	}

	private static AddonsMailOperationsDAO constructDAO() throws SystemException {
		try {
			EntityManager em = PersistenceController.getEntityManager();
			return AddonsMailOperationsDAO.class.cast(em.getQueryDAO("addonsmail.operations"));
		} catch (PersistenceException persistenceException) {
			throw new SystemException(persistenceException.getErrorCode(), persistenceException);
		}
	}

	private void performMailExportOperations(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			String importMailsForSplitBooking) throws SystemException {

		if (mailFlightSummaryVO != null && mailFlightSummaryVO.getShipmentSummaryVOs() != null
				&& !mailFlightSummaryVO.getShipmentSummaryVOs().isEmpty()) {
			performMailAcceptanceOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
		} else {
			perfromMailOffloadOperations(mailFlightSummaryVO, logonAttributes);
		}
	}

	private void performMailAcceptanceOperations(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, String importMailsForSplitBooking) throws SystemException {

		String airportCode = null;
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		Set<String> spccontKey = new HashSet<>();
		String fltDate = getFormatedFlightDate(mailFlightSummaryVO);
		AirlineValidationVO airlineValidationVO = new SharedAirlineProxy()
				.findAirline(mailFlightSummaryVO.getCompanyCode(), mailFlightSummaryVO.getCarrierId());
		airportCode = getAirportCode(mailFlightSummaryVO, logonAttributes);
		Collection<FlightValidationVO> flightValidationVOs = getFlightValidationVOs(mailFlightSummaryVO,
				logonAttributes);
		List<String> awbKey = new ArrayList<>();
		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {
		Collection<MailbagVO> acpMails = new ArrayList<>();

			boolean reassignaftOFL = false;
			ArrayList<MailbagVO> oflChkMails = new ArrayList<>();
			Collection<MailbagVO> reassignMails = new ArrayList<>();

			scannedMailDetailsVO = getScannedMailDetailsVOForExport(mailFlightSummaryVO, importMailsForSplitBooking,
					shipmentSummaryVO);
			boolean reassignFlag = false;
			if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {
				reassignFlag = findMailBagsforReassign(scannedMailDetailsVO, shipmentSummaryVO, mailFlightSummaryVO);
				reassignaftOFL = getMailBagsAndOFLFlag(airportCode, scannedMailDetailsVO, acpMails, shipmentSummaryVO,
						oflChkMails, reassignMails, reassignFlag);

			}
			if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO) && (reassignaftOFL || reassignFlag)) {
				perfromReassignOffloadExport(mailFlightSummaryVO, scannedMailDetailsVO, fltDate, flightValidationVOs,
						awbKey, shipmentSummaryVO, reassignMails);
			}

			performMailAccceptance(mailFlightSummaryVO, scannedMailDetailsVO, airlineValidationVO, flightValidationVOs,
					awbKey, acpMails, shipmentSummaryVO);
		}
		perofromPartialOffloadExport(mailFlightSummaryVO, logonAttributes, airportCode, scannedMailDetailsVO,
				spccontKey, fltDate, awbKey);
	}

	private void perofromPartialOffloadExport(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			String airportCode, ScannedMailDetailsVO scannedMailDetailsVO, Set<String> spccontKey, String fltDate,
			List<String> awbKey) throws SystemException {

		boolean offloadReq = false;
		ScannedMailDetailsVO scnMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails(null, mailFlightSummaryVO);
		addAWBKeysExportPartialOffload(mailFlightSummaryVO, awbKey);
		Collection<MailbagVO> mailbags = new ArrayList<>();
		double weight = 0.0;
		Collection<UldManifestVO> uldManifestVOs = getUldManifestVOsPartialOffload(mailFlightSummaryVO,
				logonAttributes);

		HashMap<String, String> uldMap = new HashMap<>();
		HashMap<String, Integer> oflUldMap = new HashMap<>();
		HashMap<String, String> acpUldMap = new HashMap<>();
		HashMap<String, String> addedULDMap = new HashMap<>();

		List<HashMap> uldMapList = new ArrayList<>();

		uldMapList.add(uldMap);
		uldMapList.add(oflUldMap);
		uldMapList.add(acpUldMap);
		uldMapList.add(addedULDMap);

		HashMap<Long, String> dupCheckMap = new HashMap<>();

		Collection<MailbagVO> duplicationRemovedMails = new ArrayList<>();
		getUldMapPartialOffload(mailFlightSummaryVO, fltDate, uldManifestVOs, uldMap);
		setMailDetailsExportPartialOffload(mailFlightSummaryVO, awbKey, scnMailDetailsVO, acpUldMap, dupCheckMap,
				duplicationRemovedMails);
		scnMailDetailsVO.setMailDetails(duplicationRemovedMails);
		boolean restrictContainerOffload = false;

		HashMap<String, ScannedMailDetailsVO> restrictedAWBDetailsForOffload = new HashMap<>();
		if (isScanMailBagDetailsNotEmpty(scnMailDetailsVO)) {

			for (MailbagVO mailVO : scnMailDetailsVO.getMailDetails()) {
				if (mailVO.getShipmentPrefix() != null && mailVO.getDocumentNumber() != null
						&& mailVO.getDuplicateNumber() > 0 && mailVO.getSequenceNumber() > 0) {

					String mailOpsKey = new StringBuilder("").append(mailVO.getShipmentPrefix()).append("~")
							.append(mailVO.getDocumentNumber()).append("~").append(mailVO.getDuplicateNumber())
							.append("~").append(mailVO.getSequenceNumber()).toString();

					if (!awbKey.contains(mailOpsKey)) {
						offloadReq = true;
						weight = setPartialOffloadDetailsExport(mailFlightSummaryVO, spccontKey, scnMailDetailsVO,
								mailbags, weight, uldMapList, mailVO);
					}
					restrictContainerOffload = restrictContainerOffload(mailFlightSummaryVO, airportCode,
							scannedMailDetailsVO, restrictedAWBDetailsForOffload, mailVO);
				}
			}
			scnMailDetailsVO.setMailDetails(mailbags);

		}

		savePartialOffloadExport(mailFlightSummaryVO, offloadReq, scnMailDetailsVO, mailbags, weight, uldMapList,
				restrictContainerOffload);

	}

	private double setPartialOffloadDetailsExport(MailFlightSummaryVO mailFlightSummaryVO, Set<String> spccontKey,
			ScannedMailDetailsVO scnMailDetailsVO, Collection<MailbagVO> mailbags, double weight,
			List<HashMap> uldMapList, MailbagVO mailVO) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String airportCode = getAirportCode(mailFlightSummaryVO, logonAttributes);
		HashMap<String, String> uldMap = (HashMap<String, String>) uldMapList.get(0);
		HashMap<String, Integer> oflUldMap = (HashMap<String, Integer>) uldMapList.get(1);
		HashMap<String, String> acpUldMap = (HashMap<String, String>) uldMapList.get(2);

		if (mailVO.getContainerNumber() != null && !uldMap.containsKey(mailVO.getContainerNumber())
				&& !acpUldMap.containsKey(mailVO.getContainerNumber())) {
			if (oflUldMap.containsKey(mailVO.getContainerNumber())) {
				oflUldMap.put(mailVO.getContainerNumber(), oflUldMap.get((mailVO.getContainerNumber())) + 1);
			} else {
				oflUldMap.put(mailVO.getContainerNumber(), 1);
			}
		} else {

			if (mailVO.getContainerNumber() != null) {
				spccontKey.add(mailVO.getContainerNumber());
			}
			weight = weight + mailVO.getWeight().getDisplayValue();

			setMailVODetailsExportPartialOffload(mailFlightSummaryVO, logonAttributes, airportCode, scnMailDetailsVO,
					mailbags, mailVO);

		}
		return weight;
	}

	private void savePartialOffloadExport(MailFlightSummaryVO mailFlightSummaryVO, boolean offloadReq,
			ScannedMailDetailsVO scnMailDetailsVO, Collection<MailbagVO> mailbags, double weight,
			List<HashMap> uldMapList, boolean restrictContainerOffload) {
		try {

			LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

			HashMap<String, String> uldMap = (HashMap<String, String>) uldMapList.get(0);
			HashMap<String, Integer> oflUldMap = (HashMap<String, Integer>) uldMapList.get(1);
			HashMap<String, String> acpUldMap = (HashMap<String, String>) uldMapList.get(2);
			HashMap<String, String> addedULDMap = (HashMap<String, String>) uldMapList.get(3);

			if (offloadReq) {
				scnMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);
				scnMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
				scnMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
				scnMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				scnMailDetailsVO.setScannedUser(logonAttributes.getUserId());
				scnMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
				scnMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);

				if (restrictContainerOffload) {
					scnMailDetailsVO.setScannedContainerDetails(null);
				} else {
					setContainerDetailsExportPartialOffload(mailFlightSummaryVO, scnMailDetailsVO, weight, uldMap,
							oflUldMap, acpUldMap, addedULDMap);
				}

				if (isScannedContainerDetailsNotEmpty(scnMailDetailsVO)) {
					scnMailDetailsVO.setContOffloadReq(true);
					new MailOperationsProxy().saveAndProcessMailBags(scnMailDetailsVO);
				}
				if (mailbags != null && !mailbags.isEmpty()) {
					scnMailDetailsVO.setContOffloadReq(false);
					scnMailDetailsVO.setScannedContainerDetails(null);
					scnMailDetailsVO.setMailDetails(mailbags);
					new MailOperationsProxy().saveAndProcessMailBags(scnMailDetailsVO);
				}

				checkforATDCaptureFlight(mailFlightSummaryVO);

			}

		} catch (SystemException e) {
			LOGGER.log(Log.FINE, e);
		}
	}

	private void setContainerDetailsExportPartialOffload(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scnMailDetailsVO, double weight, HashMap<String, String> uldMap,
			HashMap<String, Integer> oflUldMap, HashMap<String, String> acpUldMap, HashMap<String, String> addedULDMap)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		if (isScannedContainerDetailsNotEmpty(scnMailDetailsVO)) {
			Collection<ContainerVO> contVOs = new ArrayList<>();
			for (ContainerVO containerVO : scnMailDetailsVO.getScannedContainerDetails()) {
				if (oflUldMap.containsKey(containerVO.getContainerNumber())
						&& !addedULDMap.containsKey(containerVO.getContainerNumber())
						&& !uldMap.containsKey(containerVO.getContainerNumber())
						&& !acpUldMap.containsKey(containerVO.getContainerNumber())) {
					addedULDMap.put(containerVO.getContainerNumber(), containerVO.getContainerNumber());
					containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
					containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
					containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
					containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
					if (mailFlightSummaryVO.getPou() != null && !mailFlightSummaryVO.getPou().isEmpty()) {
						containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
					} else {
						containerVO.setFinalDestination(containerVO.getPou());
					}
						containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
						containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
						containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
						containerVO.setOffload(true);
						containerVO.setBags(oflUldMap.get(containerVO.getContainerNumber()));
						containerVO.setWeight(new Measure(UnitConstants.WEIGHT, weight));
						containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
						containerVO
								.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
						containerVO.setLastUpdateUser(logonAttributes.getUserId());
						containerVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
						contVOs.add(containerVO);
				}
			}
			scnMailDetailsVO.setScannedContainerDetails(contVOs);
		}
	}

	private boolean restrictContainerOffload(MailFlightSummaryVO mailFlightSummaryVO, String airportCode,
			ScannedMailDetailsVO scannedMailDetailsVO,
			HashMap<String, ScannedMailDetailsVO> restrictedAWBDetailsForOffload, MailbagVO mailVO)
			throws SystemException {
		boolean restrictContainerOffload = false;
		if (restrictedAWBDetailsForOffload != null && !restrictedAWBDetailsForOffload.isEmpty()
				&& restrictedAWBDetailsForOffload.containsKey(mailVO.getDocumentNumber())) {
			LOGGER.log(Log.FINE, "");
		} else if (restrictedAWBDetailsForOffload != null) {
			ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
			shipmentSummaryVO.setCompanyCode(mailVO.getCompanyCode());
			shipmentSummaryVO.setOwnerId(mailVO.getCarrierId());
			shipmentSummaryVO.setMasterDocumentNumber(mailVO.getDocumentNumber());
			shipmentSummaryVO.setSequenceNumber(mailVO.getSequenceNumber());
			shipmentSummaryVO.setDuplicateNumber(mailVO.getDuplicateNumber());
			ScannedMailDetailsVO newScannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO,
					mailFlightSummaryVO);
			for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
				restrictedAWBDetailsForOffload.put(mailVO.getDocumentNumber(), newScannedMailDetailsVO);

				if ((airportCode != null && !airportCode.equals(mailbagVO.getScannedPort()))
						|| MailConstantsVO.MAIL_STATUS_DELIVERED.equals(mailbagVO.getMailStatus())
						|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus())
						|| (mailbagVO.getFlightNumber() != null
								&& !mailbagVO.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()))
						|| mailbagVO.getFlightSequenceNumber() != mailFlightSummaryVO.getFlightSequenceNumber()) {

					restrictContainerOffload = true;
					break;
				}
			}

		}
		return restrictContainerOffload;
	}

	private void setMailVODetailsExportPartialOffload(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, String airportCode, ScannedMailDetailsVO scnMailDetailsVO,
			Collection<MailbagVO> mailbags, MailbagVO mailVO) throws SystemException {
		mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
		mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
		mailVO.setScannedUser(logonAttributes.getUserId());
		mailVO.setLastUpdateUser(logonAttributes.getUserId());
		mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
		mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
		mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
		mailVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		mailVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		mailVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		mailVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());

		try {
			MailbagPK pk = new MailbagPK();
			pk.setCompanyCode(logonAttributes.getCompanyCode());
			pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
			Mailbag bag = Mailbag.find(pk);
			if (bag != null) {
				mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
				mailVO.setDespatchId(bag.getDsnIdr());

				scnMailDetailsVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());

				if (MailConstantsVO.OPERATION_OUTBOUND.equals(bag.getOperationalStatus())
						&& (bag.getFlightNumber() != null
								&& bag.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber())
								&& bag.getFlightSequenceNumber() == mailFlightSummaryVO.getFlightSequenceNumber())
						&& (airportCode != null && airportCode.equals(mailVO.getScannedPort()))) {
					mailbags.add(mailVO);
				}

			}

		} catch (FinderException exception) {
			LOGGER.log(Log.FINE, exception);
		}
	}

	private void addAWBKeysExportPartialOffload(MailFlightSummaryVO mailFlightSummaryVO, List<String> awbKey) {
		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {
			String mftAwbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
					.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~")
					.append(shipmentSummaryVO.getDuplicateNumber()).append("~")
					.append(shipmentSummaryVO.getSequenceNumber()).toString();
			awbKey.add(mftAwbKey);
		}
	}

	private void setMailDetailsExportPartialOffload(MailFlightSummaryVO mailFlightSummaryVO, List<String> awbKey,
			ScannedMailDetailsVO scnMailDetailsVO, HashMap<String, String> acpUldMap, HashMap<Long, String> dupCheckMap,
			Collection<MailbagVO> duplicationRemovedMails) throws SystemException {

		if (isScanMailBagDetailsNotEmpty(scnMailDetailsVO)) {

			for (MailbagVO mailVO : scnMailDetailsVO.getMailDetails()) {
				if (mailVO.getShipmentPrefix() != null && mailVO.getDocumentNumber() != null
						&& mailVO.getDuplicateNumber() > 0 && mailVO.getSequenceNumber() > 0) {

					String mailOpsKey = new StringBuilder("").append(mailVO.getShipmentPrefix()).append("~")
							.append(mailVO.getDocumentNumber()).append("~").append(mailVO.getDuplicateNumber())
							.append("~").append(mailVO.getSequenceNumber()).toString();

					if (awbKey.contains(mailOpsKey) && mailVO.getContainerNumber() != null
							&& !acpUldMap.containsKey(mailVO.getContainerNumber())) {
						acpUldMap.put(mailVO.getContainerNumber(), mailVO.getContainerNumber());
					}
				}
				putDupCheckMapPartialOffloadExport(mailFlightSummaryVO, dupCheckMap, duplicationRemovedMails, mailVO);
			}
		}
	}

	private boolean isScanMailBagDetailsNotEmpty(ScannedMailDetailsVO scnMailDetailsVO) {
		return scnMailDetailsVO != null && scnMailDetailsVO.getMailDetails() != null
				&& !scnMailDetailsVO.getMailDetails().isEmpty();
	}

	private void putDupCheckMapPartialOffloadExport(MailFlightSummaryVO mailFlightSummaryVO,
			HashMap<Long, String> dupCheckMap, Collection<MailbagVO> duplicationRemovedMails, MailbagVO mailVO)
			throws SystemException {
		try {
			MailbagPK pk = new MailbagPK();
			pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
			pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
			Mailbag bag = Mailbag.find(pk);
			mailVO.setFlightNumber(bag.getFlightNumber());
			mailVO.setFlightSequenceNumber(bag.getFlightSequenceNumber());
			mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
			if (!dupCheckMap.containsKey(mailVO.getMailSequenceNumber())) {
				duplicationRemovedMails.add(mailVO);
				dupCheckMap.put(mailVO.getMailSequenceNumber(), mailVO.getMailbagId());
			}
		} catch (FinderException ex) {
			LOGGER.log(Log.FINE, ex);
		}
	}

	private void getUldMapPartialOffload(MailFlightSummaryVO mailFlightSummaryVO, String fltDate,
			Collection<UldManifestVO> uldManifestVOs, HashMap<String, String> uldMap) {
		if (uldManifestVOs != null && !uldManifestVOs.isEmpty()) {
			for (UldManifestVO uldManifestVO : uldManifestVOs) {
				uldMap.put(uldManifestVO.getUldNumber(), uldManifestVO.getBarrowFlag());
				if (MailConstantsVO.CONST_BULK.equals(uldManifestVO.getUldNumber())) {
					StringBuilder bulkNum = new StringBuilder("").append(uldManifestVO.getUldNumber())
							.append(uldManifestVO.getPou()).append(mailFlightSummaryVO.getFlightNumber())
							.append(fltDate);
					uldMap.put(bulkNum.toString(), uldManifestVO.getBarrowFlag());
				}
			}
		}
	}

	private Collection<UldManifestVO> getUldManifestVOsPartialOffload(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes) throws SystemException {
		ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
		manifestFilterVO.setManifestPrint(false);
		manifestFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());

		return new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
	}

	private Collection<MailbagVO> performMailAccceptance(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, AirlineValidationVO airlineValidationVO,
			Collection<FlightValidationVO> flightValidationVOs, List<String> awbKey, Collection<MailbagVO> acpMails,
			ShipmentSummaryVO shipmentSummaryVO) throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		String fltDate = getFormatedFlightDate(mailFlightSummaryVO);
		boolean blockReceivedResdit = false;
		CTOAcceptanceVO cTOAcceptanceVO = null;

		if (MailConstantsVO.FLAG_YES.equals(shipmentSummaryVO.getTranshipmentFlag())) {
			blockReceivedResdit = true;
		}
		
		findTransferCarrier(acpMails, shipmentSummaryVO, logonAttributes, cTOAcceptanceVO);

		if (!acpMails.isEmpty() && isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {

			HashMap<String, String> uldMap = new HashMap<>();
			scannedMailDetailsVO.setMailDetails(acpMails);

			String cargoOpsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
					.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~")
					.append(shipmentSummaryVO.getDuplicateNumber()).append("~")
					.append(shipmentSummaryVO.getSequenceNumber()).toString();

			awbKey.add(cargoOpsKey);

			scannedMailDetailsVO.setProcessPoint(mailFlightSummaryVO.getEventCode());

			setAirportCode(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO);

			setPOUDestinationDetailsExport(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO,
					flightValidationVOs, shipmentSummaryVO);

			scannedMailDetailsVO.setMailSource("EXPFLTFIN_ACPMAL");
			scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());

			setFlightDetailsAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, flightValidationVOs);

			ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
			manifestFilterVO.setManifestPrint(false);
			manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());

			manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
			manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());

			setPOLAcceptance(logonAttributes, scannedMailDetailsVO, manifestFilterVO);

			Collection<UldManifestVO> uldManifestVOs = new OperationsFltHandlingProxy()
					.findManifestShipmentDetails(manifestFilterVO);
			Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<>();
			getManifestShipmentDetails(uldManifestVOs, manifestShipmentDetails, uldMap);

			if (!manifestShipmentDetails.isEmpty()) {
				setScanMailDetailsForAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, fltDate, shipmentSummaryVO,
						blockReceivedResdit, uldMap, manifestShipmentDetails);
			}

			try {
				new MailUploadController().saveAcceptanceFromUpload(scannedMailDetailsVO, logonAttributes);
				checkforATDCaptureFlight(mailFlightSummaryVO);
				acpMails = new ArrayList<>();
			} catch (MailHHTBusniessException | MailMLDBusniessException | ForceAcceptanceException e) {
				LOGGER.log(Log.FINE, e);
			}

		}
		return acpMails;
	}
	
	private void findTransferCarrier(Collection<MailbagVO> acpMails, ShipmentSummaryVO shipmentSummaryVO,
			LogonAttributes logonAttributes, CTOAcceptanceVO cTOAcceptanceVO) throws SystemException {

		if (!shipmentSummaryVO.getOrigin().equals(logonAttributes.getAirportCode())) {
			AcceptanceFilterVO acceptanceFilterVO = new AcceptanceFilterVO();
			acceptanceFilterVO.setCompanyCode(shipmentSummaryVO.getCompanyCode());
			acceptanceFilterVO.setDocumentNumber(shipmentSummaryVO.getMasterDocumentNumber());
			acceptanceFilterVO.setOwnerId(shipmentSummaryVO.getOwnerId());
			acceptanceFilterVO.setSequenceNumber(shipmentSummaryVO.getSequenceNumber());
			acceptanceFilterVO.setDuplicateNumber(shipmentSummaryVO.getDuplicateNumber());
			acceptanceFilterVO.setAirportCode(logonAttributes.getAirportCode());

			cTOAcceptanceVO = Proxy.getInstance().get(OperationalShipmentProxy.class)
					.findCTOAcceptanceDetails(acceptanceFilterVO);
		}

		if (!acpMails.isEmpty() && cTOAcceptanceVO != null) {
			for (MailbagVO mailbag : acpMails) {

				if (cTOAcceptanceVO.isTranshipment()) {
					shipmentSummaryVO.setTranshipmentFlag("Y");
				}

				if (cTOAcceptanceVO.getFromCarrier() != null) {
					mailbag.setTransferFromCarrier(cTOAcceptanceVO.getFromCarrier());
				}

			}
		}
	}
	
	

	private void setScanMailDetailsForAcceptance(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String fltDate, ShipmentSummaryVO shipmentSummaryVO,
			boolean blockReceivedResdit, HashMap<String, String> uldMap,
			Collection<ShipmentManifestVO> manifestShipmentDetails) {

		for (ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails) {
			if (shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix())
					&& shipmentSummaryVO.getMasterDocumentNumber()
							.equals(shipmentManifestVO.getMasterDocumentNumber())) {

				for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {

					if (blockReceivedResdit) {
						mailBagVO.setBlockReceivedResdit(blockReceivedResdit);
					}

					setScanMailDetailsFromShipmentManifestAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, fltDate,
							shipmentSummaryVO, uldMap, shipmentManifestVO, mailBagVO);
				}
			}
		}

	}

	private void setScanMailDetailsFromShipmentManifestAcceptance(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String fltDate, ShipmentSummaryVO shipmentSummaryVO,
			HashMap<String, String> uldMap, ShipmentManifestVO shipmentManifestVO, MailbagVO mailBagVO) {

		scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
		scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
		scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
		mailBagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		mailBagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		mailBagVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
		mailBagVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());

		if ("Y".equals(uldMap.get(shipmentManifestVO.getUldNumber()))
				|| MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())) {
			mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
			scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
			if (shipmentManifestVO.getPou() != null && shipmentManifestVO.getPou().trim().length() > 0
					&& mailFlightSummaryVO.getRoute() != null && mailFlightSummaryVO.getRoute().split("-").length > 2) {
				scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
				if (shipmentManifestVO.getDestination() != null) {
					scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
				}
			}
		} else {
			mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
			scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());// a-8061
																					// added
																					// for
																					// ICRD-255007
			if (shipmentManifestVO.getPou() != null && shipmentManifestVO.getPou().trim().length() > 0
					&& mailFlightSummaryVO.getRoute() != null && mailFlightSummaryVO.getRoute().split("-").length > 2) {
				scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
				if (shipmentManifestVO.getDestination() != null) {
					scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
				}
			}

		}

		setContainerNumberExportAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, fltDate, shipmentManifestVO,
				mailBagVO);
	}

	private void setContainerNumberExportAcceptance(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String fltDate, ShipmentManifestVO shipmentManifestVO,
			MailbagVO mailBagVO) {
		StringBuilder conNum;
		if (MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())) {
			conNum = new StringBuilder("").append(shipmentManifestVO.getUldNumber())
					.append(scannedMailDetailsVO.getPou()).append(mailFlightSummaryVO.getFlightNumber());
			if (fltDate != null) {
				conNum.append(fltDate);
			}
			mailBagVO.setContainerNumber(conNum.toString());
			scannedMailDetailsVO.setContainerNumber(conNum.toString());
		} else {
			mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
			scannedMailDetailsVO.setContainerNumber(shipmentManifestVO.getUldNumber());
		}

		if (MailConstantsVO.BULK_TYPE.equals(scannedMailDetailsVO.getContainerType())) {
			scannedMailDetailsVO.setDestination(scannedMailDetailsVO.getPou());
		}
	}

	private void setPOLAcceptance(LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO,
			ManifestFilterVO manifestFilterVO) {
		if (scannedMailDetailsVO.getAirportCode() != null) {
			manifestFilterVO.setPointOfLading(scannedMailDetailsVO.getAirportCode());
		} else {
			manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
		}
	}

	private void setFlightDetailsAcceptance(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, Collection<FlightValidationVO> flightValidationVOs) {
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			scannedMailDetailsVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());

			if (mailFlightSummaryVO.getFlightDate() != null) {
				scannedMailDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
			} else {
				scannedMailDetailsVO
						.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
			}

		}
	}

	private void setPOUDestinationDetailsExport(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightValidationVOs, ShipmentSummaryVO shipmentSummaryVO) {
		if (mailFlightSummaryVO.getPou() == null) {
			mailFlightSummaryVO
					.setPou(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegDestination());
		}

		if (mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())) {
			scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
			scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
		} else if (mailFlightSummaryVO.getRoute() != null) {
			String[] routes = mailFlightSummaryVO.getRoute().split("-");
			if (Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())) {
				scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
				scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
			} else {
				scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
				scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
			}
		} else {
			scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
		}

		if (scannedMailDetailsVO.getAirportCode() != null) {
			scannedMailDetailsVO.setPol(scannedMailDetailsVO.getAirportCode());
		} else {
			scannedMailDetailsVO.setPol(logonAttributes.getAirportCode());
		}
	}

	private void setAirportCode(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		if (mailFlightSummaryVO.getAirportCode() != null) {
			scannedMailDetailsVO.setAirportCode(mailFlightSummaryVO.getAirportCode());
		} else {
			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
		}
	}

	private boolean perfromReassignOffloadExport(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String fltDate,
			Collection<FlightValidationVO> flightValidationVOs, List<String> awbKey,
			ShipmentSummaryVO shipmentSummaryVO, Collection<MailbagVO> reassignMails) throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		boolean reassignRequired = false;

		String opsKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
				.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~")
				.append(shipmentSummaryVO.getDuplicateNumber()).append("~")
				.append(shipmentSummaryVO.getSequenceNumber()).toString();
		awbKey.add(opsKey);

		setScannedMailDetailsVODetailsExport(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO,
				flightValidationVOs, shipmentSummaryVO);

		setFlightDetailsAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, flightValidationVOs);

		if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {

			Collection<UldManifestVO> uldManifestVOs = getUldManifestVOs(mailFlightSummaryVO, logonAttributes,
					scannedMailDetailsVO);

			Collection<ShipmentManifestVO> manifestShipmentDetails = new ArrayList<>();
			HashMap<String, String> uldMap = new HashMap<>();
			getManifestShipmentDetails(uldManifestVOs, manifestShipmentDetails, uldMap);

			setScanMailDetailsFromShipmentManifestVOExport(mailFlightSummaryVO, scannedMailDetailsVO, fltDate,
					shipmentSummaryVO, manifestShipmentDetails, uldMap);

			Collection<MailbagVO> reassignedMailbags = new ArrayList<>();

			reassignRequired = isReAssignRequired(mailFlightSummaryVO, logonAttributes, reassignRequired,
					scannedMailDetailsVO, reassignedMailbags);

			if (reassignRequired) {
				reassignMails = reassignedMailbags;
			}

				scannedMailDetailsVO.setMailDetails(reassignMails);

		}

		setContainerDetailsExportReAssign(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO);

		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_REASSIGNMAIL);

		try {
			new MailOperationsProxy().saveAndProcessMailBags(scannedMailDetailsVO);
			if (reassignRequired) {
				unassignEmptyULDs(mailFlightSummaryVO);
			}
		} catch (SystemException e) {
			LOGGER.log(Log.FINE, e);
		}

		return reassignRequired;
	}

	private void setContainerDetailsExportReAssign(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO) {
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
		containerDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		containerDetailsVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
		containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		containerDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
		containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		containerDetailsVO.setSegmentSerialNumber(scannedMailDetailsVO.getSegmentSerialNumber());
		containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
		containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
		containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
		containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
		containerDetailsVO.setContainerType(scannedMailDetailsVO.getContainerType());
		containerDetailsVO.setDestination(scannedMailDetailsVO.getDestination());
		containerDetailsVO.setPou(scannedMailDetailsVO.getPou());
		containerDetailsVO.setPol(logonAttributes.getAirportCode());
		containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerDetailsVO.setAcceptedFlag("Y");
		containerDetailsVO.setArrivedStatus("N");
		containerDetailsVO.setTransactionCode("ASG");
		containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		containerDetailsVO.setContainerOperationFlag("I");
		containerDetailsVO.setOflToRsnFlag(true);
		containerDetailsVO.setTransitFlag("Y");
		scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
		scannedMailDetailsVO.setScannedContainerDetails(null);
	}

	private boolean isReAssignRequired(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			boolean reassignRequired, ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<MailbagVO> reassignedMailbags) throws SystemException {
		for (MailbagVO mailbagInTransaction : scannedMailDetailsVO.getMailDetails()) {

			MailbagPK mailPK = new MailbagPK();
			Mailbag mail = null;
			mailPK.setCompanyCode(mailbagInTransaction.getCompanyCode());
			mailPK.setMailSequenceNumber(mailbagInTransaction.getMailSequenceNumber());
			try {
				mail = Mailbag.find(mailPK);
			} catch (FinderException e) {
				LOGGER.log(Log.FINE, e);
			}
			if (mail != null && MailConstantsVO.OPERATION_OUTBOUND.equals(mail.getOperationalStatus())
					&& mail.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber())
					&& mail.getFlightSequenceNumber() == mailFlightSummaryVO.getFlightSequenceNumber()
					&& mail.getScannedPort().equals(logonAttributes.getAirportCode())) {
				if (mail.getUldNumber() != null
						&& mail.getUldNumber().equals(mailbagInTransaction.getContainerNumber())) {
					reassignRequired = false;
				} else {
					reassignedMailbags.add(mailbagInTransaction);
					reassignRequired = true;
				}

			}
		}
		return reassignRequired;
	}

	private void setScanMailDetailsFromShipmentManifestVOExport(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String fltDate, ShipmentSummaryVO shipmentSummaryVO,
			Collection<ShipmentManifestVO> manifestShipmentDetails, HashMap<String, String> uldMap) {
		if (manifestShipmentDetails != null && !manifestShipmentDetails.isEmpty()) {
			for (ShipmentManifestVO shipmentManifestVO : manifestShipmentDetails) {
				if (shipmentSummaryVO.getShipmentPrefix().equals(shipmentManifestVO.getShipmentPrefix())
						&& shipmentSummaryVO.getMasterDocumentNumber()
								.equals(shipmentManifestVO.getMasterDocumentNumber())) {
					for (MailbagVO mailBagVO : scannedMailDetailsVO.getMailDetails()) {
						setMailDetailsFromShipmentManifest(mailFlightSummaryVO, scannedMailDetailsVO, shipmentSummaryVO,
								uldMap, shipmentManifestVO, mailBagVO);
						scannedMailDetailsVO.setSegmentSerialNumber(shipmentManifestVO.getSegmentSerialNumber());
						setContainerNumberExportAcceptance(mailFlightSummaryVO, scannedMailDetailsVO, fltDate,
								shipmentManifestVO, mailBagVO);

					}
				}
			}
		}
	}

	private void setMailDetailsFromShipmentManifest(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, ShipmentSummaryVO shipmentSummaryVO,
			HashMap<String, String> uldMap, ShipmentManifestVO shipmentManifestVO, MailbagVO mailBagVO) {
		mailBagVO.setContainerNumber(shipmentManifestVO.getUldNumber());
		if ("Y".equals(uldMap.get(shipmentManifestVO.getUldNumber()))
				|| MailConstantsVO.CONST_BULK.equals(shipmentManifestVO.getUldNumber())) {
			mailBagVO.setContainerType(MailConstantsVO.BULK_TYPE);
			scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
			if (shipmentManifestVO.getPou() != null && shipmentManifestVO.getPou().trim().length() > 0
					&& mailFlightSummaryVO.getRoute() != null && mailFlightSummaryVO.getRoute().split("-").length > 2) {
				scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
				if (shipmentManifestVO.getDestination() != null) {
					scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
				}
			}
		} else {
			mailBagVO.setContainerType(MailConstantsVO.ULD_TYPE);
			scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
			if (shipmentManifestVO.getPou() != null && shipmentManifestVO.getPou().trim().length() > 0
					&& mailFlightSummaryVO.getRoute() != null && mailFlightSummaryVO.getRoute().split("-").length > 2) {
				scannedMailDetailsVO.setPou(shipmentManifestVO.getPou());
				if (shipmentManifestVO.getDestination() != null) {
					scannedMailDetailsVO.setDestination(shipmentManifestVO.getDestination());
				}
			}
		}
	}

	private void getManifestShipmentDetails(Collection<UldManifestVO> uldManifestVOs,
			Collection<ShipmentManifestVO> manifestShipmentDetails, HashMap<String, String> uldMap) {
		if (uldManifestVOs != null && !uldManifestVOs.isEmpty()) {
			for (UldManifestVO uldManifestVO : uldManifestVOs) {
				uldMap.put(uldManifestVO.getUldNumber(), uldManifestVO.getBarrowFlag());
				manifestShipmentDetails.addAll(uldManifestVO.getManifestShipmentDetails());
			}
		}
	}

	private Collection<UldManifestVO> getUldManifestVOs(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
		manifestFilterVO.setManifestPrint(false);
		manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
		return new OperationsFltHandlingProxy().findManifestShipmentDetails(manifestFilterVO);
	}

	private void setScannedMailDetailsVODetailsExport(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightValidationVOs, ShipmentSummaryVO shipmentSummaryVO) {

		scannedMailDetailsVO.setProcessPoint(mailFlightSummaryVO.getEventCode());
		scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
		scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getCarrierCode());
		for (MailbagVO mailbag : scannedMailDetailsVO.getMailDetails()) {

			if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
				mailbag.setLegSerialNumber(
						((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			}
			mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
			mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
			mailbag.setFinalDestination(mailbag.getPou());
			mailbag.setInventoryContainer(mailbag.getContainerNumber());
			mailbag.setInventoryContainerType(mailbag.getContainerType());
			mailbag.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);

		}
		scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getCarrierId());
		scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getFlightNumber());
		scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getFlightDate());
		scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		scannedMailDetailsVO.setScannedContainerDetails(null);

		if (mailFlightSummaryVO.getPou() == null && flightValidationVOs != null) {
			mailFlightSummaryVO
					.setPou(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegDestination());
		}

		if (mailFlightSummaryVO.getPou().equals(shipmentSummaryVO.getDestination())) {
			scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
			scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
		} else {
			String[] routes = mailFlightSummaryVO.getRoute().split("-");
			if (Arrays.asList(routes).contains(shipmentSummaryVO.getDestination())) {
				scannedMailDetailsVO.setPou(shipmentSummaryVO.getDestination());
				scannedMailDetailsVO.setDestination(shipmentSummaryVO.getDestination());
			} else {
				scannedMailDetailsVO.setPou(mailFlightSummaryVO.getPou());
				scannedMailDetailsVO.setDestination(mailFlightSummaryVO.getPou());
			}
		}

	}

	private boolean getMailBagsAndOFLFlag(String airportCode, ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<MailbagVO> acpMails, ShipmentSummaryVO shipmentSummaryVO, ArrayList<MailbagVO> oflChkMails,
			Collection<MailbagVO> reassignMails, boolean reassignFlag) {
		boolean reassignaftOFL = false;

		if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {
			for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {

				reassignaftOFL = getMailBagsAndOFLFlagDetails(airportCode, acpMails, shipmentSummaryVO, oflChkMails,
						reassignMails, reassignFlag, mailbagVO);
			}
		}
		return reassignaftOFL;
	}

	private boolean getMailBagsAndOFLFlagDetails(String airportCode, Collection<MailbagVO> acpMails,
			ShipmentSummaryVO shipmentSummaryVO, ArrayList<MailbagVO> oflChkMails, Collection<MailbagVO> reassignMails,
			boolean reassignFlag, MailbagVO mailbagVO) {
		boolean reassignaftOFL = false;
		if ((MailConstantsVO.MAIL_STATUS_ASSIGNED.equals(mailbagVO.getMailStatus())
				|| MailConstantsVO.MAIL_STATUS_OFFLOADED.equals(mailbagVO.getMailStatus()))
				&& "OFLMAILBAG".equals(mailbagVO.getOffloadedRemarks()) || reassignFlag) {

			if ("OFLMAILBAG".equals(mailbagVO.getOffloadedRemarks())
					&& (mailbagVO.getMailStatus().equals(MailConstantsVO.MAIL_TXNCOD_ASG)
							|| mailbagVO.getMailStatus().equals(MailConstantsVO.MAIL_STATUS_OFFLOADED))) {
				reassignaftOFL = true;
				reassignMails.add(mailbagVO);
			}
		} else if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO.getMailStatus())
				|| MailConstantsVO.MAIL_STATUS_ARRIVED.equals(mailbagVO.getMailStatus())) {
			if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO.getMailStatus())
					|| (shipmentSummaryVO.getDestination() != null
							&& !shipmentSummaryVO.getDestination().equals(airportCode))) {
				acpMails.add(mailbagVO);
			}
		} else if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getMailStatus())) {
			oflChkMails.add(mailbagVO);
		}
		return reassignaftOFL;
	}

	private ScannedMailDetailsVO getScannedMailDetailsVOForExport(MailFlightSummaryVO mailFlightSummaryVO,
			String importMailsForSplitBooking, ShipmentSummaryVO shipmentSummaryVO) throws SystemException {
		ScannedMailDetailsVO scannedMailDetailsVO = null;
		if (BOOKED_FLIGHT.equals(importMailsForSplitBooking)) {
			scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails(shipmentSummaryVO, mailFlightSummaryVO);
		} else if (FIRST_FLIGHT.equals(importMailsForSplitBooking)) {
			scannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO, mailFlightSummaryVO);
		}
		return scannedMailDetailsVO;
	}

	private String getAirportCode(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes) {
		String airportCode;
		if (mailFlightSummaryVO.getAirportCode() != null) {
			airportCode = mailFlightSummaryVO.getAirportCode();
		} else {
			airportCode = logonAttributes.getAirportCode();
		}
		return airportCode;
	}

	private Collection<FlightValidationVO> getFlightValidationVOs(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes) throws SystemException {
		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		filghtFilterVO.setDirection(FlightFilterVO.OUTBOUND);
		if (mailFlightSummaryVO.getAirportCode() != null) {
			filghtFilterVO.setStation(mailFlightSummaryVO.getAirportCode());
		} else {
			filghtFilterVO.setStation(logonAttributes.getAirportCode());
		}

		return new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	}

	private String getFormatedFlightDate(MailFlightSummaryVO mailFlightSummaryVO) {
		String fltDate = null;
		if (mailFlightSummaryVO.getFlightDate() != null) {
			fltDate = DateUtilities.format(mailFlightSummaryVO.getFlightDate(), "ddMM");
		}
		return fltDate;
	}

	private void checkforATDCaptureFlight(MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {

		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		flightFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		flightFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		flightFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		flightFilterVO.setStation(mailFlightSummaryVO.getPol());
		flightFilterVO.setDirection(FlightFilterVO.OUTBOUND);

		Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy()
				.validateFlightForAirport(flightFilterVO);
		Collection<OperationalFlightVO> operationalFlightVOs = new ArrayList<>();
		for (FlightValidationVO flightValidationVO : flightValidationVOs) {
			if (flightValidationVO.getAtd() != null) {

				OperationalFlightVO operationalFlightVO = MailtrackingDefaultsVOConverter
						.constructOperationalFlightVO(flightValidationVO);
				operationalFlightVOs.add(operationalFlightVO);
				try {
					new MailController().flagUpliftedResditForMailbags(operationalFlightVOs);
				} catch (SystemException ex) {
					LOGGER.log(Log.FINE, ex);
				}

			}
		}
	}

	public void unassignEmptyULDs(MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		operationalFlightVO.setPol(mailFlightSummaryVO.getAirportCode());

		new MailController().unassignEmptyUldsinMailFlight(operationalFlightVO);

	}

	public boolean findMailBagsforReassign(ScannedMailDetailsVO scannedMailDetailsVO,
			ShipmentSummaryVO shipmentSummaryVO, MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {

		boolean result;

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		List<MailbagVO> mailbagVOs = null;
		try {
			mailbagVOs = Mailbag.findMailBagsforReassign(shipmentSummaryVO, mailFlightSummaryVO);
		} catch (PersistenceException e) {
			LOGGER.log(Log.FINE, e);
		}
		result = isMailBagsforReassign(scannedMailDetailsVO, mailbagVOs, mailFlightSummaryVO);

		if (result) {
			return true;
		}

		boolean reassignFlag = false;
		for (MailbagVO mailbagInTransaction : scannedMailDetailsVO.getMailDetails()) {

			MailbagPK mailPK = new MailbagPK();
			Mailbag mail = null;
			mailPK.setCompanyCode(mailbagInTransaction.getCompanyCode());
			mailPK.setMailSequenceNumber(mailbagInTransaction.getMailSequenceNumber());
			try {
				mail = Mailbag.find(mailPK);

				mailbagInTransaction.setOrigin(mail.getOrigin());
				if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mail.getLatestStatus())) {
					return false;
				} else if ("ARR".equals(mail.getLatestStatus())) {
					mailbagInTransaction.setArrivedFlag("Y");
				}

				if (MailConstantsVO.OPERATION_OUTBOUND.equals(mail.getOperationalStatus())
						&& mail.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber())
						&& mail.getFlightSequenceNumber() == mailFlightSummaryVO.getFlightSequenceNumber()
						&& mail.getScannedPort().equals(logonAttributes.getAirportCode())) {

					reassignFlag = true;

				}
			} catch (FinderException e) {
				LOGGER.log(Log.FINE, e);
			}
		}

		return reassignFlag;

	}

	private boolean isMailBagsforReassign(ScannedMailDetailsVO scannedMailDetailsVO, List<MailbagVO> mailbagVOs,
			MailFlightSummaryVO mailFlightSummaryVO) {

		boolean result = false;

		if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO) && mailbagVOs != null && !mailbagVOs.isEmpty()) {

			for (MailbagVO mailbagInTransaction : scannedMailDetailsVO.getMailDetails()) {

				for (MailbagVO mailbag : mailbagVOs) {

					if (mailbag.getMailSequenceNumber() == mailbagInTransaction.getMailSequenceNumber()
							&& ((mailbag.getFlightNumber() != null && mailFlightSummaryVO.getFlightNumber() != null
									&& !mailbag.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()))
									|| (mailbag.getFlightSequenceNumber() != mailFlightSummaryVO
											.getFlightSequenceNumber()))) {
						result = true;
						break;

					}
				}
			}
		}
		return result;

	}

	private void perfromMailOffloadOperations(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes)
			throws SystemException {

		boolean restrictContainerOffload = false;
		int segSerial = 1;
		String arpCod = null;
		HashMap<String, String> uldMap = new HashMap<>();
		OperationalFlightVO operationalFlightVO = new OperationalFlightVO();
		operationalFlightVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		operationalFlightVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		operationalFlightVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		operationalFlightVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		operationalFlightVO.setDirection(FlightFilterVO.OUTBOUND);

		arpCod = getAirportCode(mailFlightSummaryVO, logonAttributes);

		ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails(null,
				mailFlightSummaryVO);

		if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {

			scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_OFFLOADED);

			scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
			scannedMailDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
			scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			scannedMailDetailsVO.setScannedUser(logonAttributes.getUserId());
			scannedMailDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
			scannedMailDetailsVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
			scannedMailDetailsVO.setContOffloadReq(true);

			int count = 0;
			double weight = 0.0;
			Collection<MailbagVO> offloadMails = new ArrayList<>();

			Collection<MailbagVO> duplicationRemovedMails = addDupCheckMapFullOffload(mailFlightSummaryVO,
					scannedMailDetailsVO);

			scannedMailDetailsVO.setMailDetails(duplicationRemovedMails);

			HashMap<String, ScannedMailDetailsVO> restrictedAWBDetailsForOffload = new HashMap<>();
			for (MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()) {

				count++;

				weight = weight + mailVO.getWeight().getDisplayValue();
				mailVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				segSerial = addOffloadMails(mailFlightSummaryVO, logonAttributes, arpCod, offloadMails, mailVO);

				restrictContainerOffload = restrictContainerOffload(mailFlightSummaryVO, arpCod, scannedMailDetailsVO,
						restrictedAWBDetailsForOffload, mailVO);

			}

			scannedMailDetailsVO.setMailDetails(offloadMails);
			if (restrictContainerOffload) {
				scannedMailDetailsVO.setScannedContainerDetails(null);
			} else if (isScannedContainerDetailsNotEmpty(scannedMailDetailsVO)) {

				Collection<ContainerVO> contVOs = new ArrayList<>();

				setContainerDetailsFullOffload(mailFlightSummaryVO, segSerial, uldMap, scannedMailDetailsVO, count,
						weight, contVOs);

			}

			try {
				new MailOperationsProxy().saveAndProcessMailBags(scannedMailDetailsVO);
			} catch (SystemException e) {
				LOGGER.log(Log.FINE, e);
			}

			checkforATDCaptureFlight(mailFlightSummaryVO);
		}
	}

	private void setContainerDetailsFullOffload(MailFlightSummaryVO mailFlightSummaryVO, int segSerial,
			HashMap<String, String> uldMap, ScannedMailDetailsVO scannedMailDetailsVO, int count, double weight,
			Collection<ContainerVO> contVOs) throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		for (ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()) {

			if (!uldMap.containsKey(containerVO.getContainerNumber())) {

				uldMap.put(containerVO.getContainerNumber(), containerVO.getContainerNumber());

				containerVO.setAcceptanceFlag(MailConstantsVO.FLAG_YES);
				containerVO.setSegmentSerialNumber(segSerial);
				containerVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
				containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
				containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
				containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				containerVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
				if (mailFlightSummaryVO.getPou() != null && !mailFlightSummaryVO.getPou().isEmpty()) {
					containerVO.setFinalDestination(mailFlightSummaryVO.getPou());
				} else {
					containerVO.setFinalDestination(containerVO.getPou());
				}
					containerVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
					containerVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
					containerVO.setOffload(true);
					containerVO.setMailSource(MailConstantsVO.MAIL_SOURCE_MAWB_OFFLOAD);
					containerVO.setBags(count);
					containerVO.setWeight(new Measure(UnitConstants.WEIGHT, weight));
					containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
					containerVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
					containerVO.setLastUpdateUser(logonAttributes.getUserId());
					containerVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
					contVOs.add(containerVO);
			}
		}
		scannedMailDetailsVO.setScannedContainerDetails(contVOs);
	}

	private int addOffloadMails(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes, String arpCod,
			Collection<MailbagVO> offloadMails, MailbagVO mailVO) {
		int segSerial = 0;
		mailVO.setLegSerialNumber(mailFlightSummaryVO.getLegSerialNumber());
		mailVO.setScannedUser(logonAttributes.getUserId());
		mailVO.setLastUpdateUser(logonAttributes.getUserId());
		mailVO.setOffloadedReason(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON_CODE);
		mailVO.setOffloadedRemarks(MailConstantsVO.CARGO_OPS_DEFAULT_OFFLOAD_REASON);
		mailVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
		mailVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		try {
			MailbagPK pk = new MailbagPK();
			pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
			pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
			Mailbag bag = Mailbag.find(pk);
			if (bag != null && bag.getSegmentSerialNumber() > 0) {
				segSerial = bag.getSegmentSerialNumber();
			}
			if (bag != null && MailConstantsVO.OPERATION_OUTBOUND.equals(bag.getOperationalStatus())
					&& (bag.getFlightNumber() != null
							&& bag.getFlightNumber().equals(mailFlightSummaryVO.getFlightNumber()))
					&& bag.getFlightSequenceNumber() == mailFlightSummaryVO.getFlightSequenceNumber()
					&& (arpCod != null && arpCod.equals(mailVO.getScannedPort()))) {
				offloadMails.add(mailVO);

			}
		} catch (FinderException | SystemException exception) {
			LOGGER.log(Log.FINE, exception);
		}
		return segSerial;
	}

	private Collection<MailbagVO> addDupCheckMapFullOffload(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		HashMap<Long, String> dupCheckMap = new HashMap<>();
		Collection<MailbagVO> duplicationRemovedMails = new ArrayList<>();

		for (MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()) {
			try {
				MailbagPK pk = new MailbagPK();
				pk.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
				pk.setMailSequenceNumber(mailVO.getMailSequenceNumber());
				Mailbag bag = Mailbag.find(pk);
				mailVO.setFlightNumber(bag.getFlightNumber());
				mailVO.setFlightSequenceNumber(bag.getFlightSequenceNumber());
				mailVO.setSegmentSerialNumber(bag.getSegmentSerialNumber());
				if (!dupCheckMap.containsKey(mailVO.getMailSequenceNumber())) {
					duplicationRemovedMails.add(mailVO);
					dupCheckMap.put(mailVO.getMailSequenceNumber(), mailVO.getMailbagId());
				}
			} catch (FinderException | SystemException exception) {
				LOGGER.log(Log.FINE, exception);
			}

		}
		return duplicationRemovedMails;
	}

	private void performMailArrivalOperations(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			String importMailsForSplitBooking) throws SystemException {

		AirlineValidationVO airlineValidationVO;
		int segSerial = 1;
		int shpSegSerNum = 0;
		airlineValidationVO = new SharedAirlineProxy().findAirline(mailFlightSummaryVO.getCompanyCode(),
				mailFlightSummaryVO.getCarrierId());

		Collection<FlightValidationVO> flightValidationVOs = getFlightValidationVOsImport(mailFlightSummaryVO);

		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {

			String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
					.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~")
					.append(shipmentSummaryVO.getDuplicateNumber()).append("~")
					.append(shipmentSummaryVO.getSequenceNumber()).toString();

			if (mailFlightSummaryVO.getUldAwbMap() != null && mailFlightSummaryVO.getUldAwbMap().containsKey(awbKey)) {

				ScannedMailDetailsVO scannedMailDetailsVO = getScannedMailDetailsVOsArrival(mailFlightSummaryVO,
						importMailsForSplitBooking, shipmentSummaryVO);

				if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {
					if (MailConstantsVO.MAIL_STATUS_ARRIVED.equals(scannedMailDetailsVO.getStatus())) {
						continue;
					}

					segSerial = getSegSerNumber(scannedMailDetailsVO, shipmentSummaryVO, mailFlightSummaryVO,
							logonAttributes);

					String containerNumber = getContainerNumber(mailFlightSummaryVO, awbKey);
					String uldPol = getULDPOL(mailFlightSummaryVO, awbKey);
					shpSegSerNum = getShpSegSerNum(mailFlightSummaryVO, shpSegSerNum, awbKey);

					if (shpSegSerNum > 0) {
						segSerial = shpSegSerNum;
					}

					setScanMailDetailsArrival(mailFlightSummaryVO, airlineValidationVO, segSerial, flightValidationVOs,
							scannedMailDetailsVO, containerNumber, uldPol);
					saveMailArrival(mailFlightSummaryVO, logonAttributes, airlineValidationVO, segSerial,
							flightValidationVOs, scannedMailDetailsVO);

				}
			}
		}

	}

	private void saveMailArrival(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			AirlineValidationVO airlineValidationVO, int segSerial, Collection<FlightValidationVO> flightValidationVOs,
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		if (isScannedContainerDetailsNotEmpty(scannedMailDetailsVO)) {
			setContainerDetailsArrival(mailFlightSummaryVO, airlineValidationVO, segSerial, flightValidationVOs,
					scannedMailDetailsVO);
			try {
				new MailUploadController().saveArrivalFromUpload(scannedMailDetailsVO, logonAttributes);
			} catch (MailHHTBusniessException | MailMLDBusniessException | ForceAcceptanceException e) {
				LOGGER.log(Log.FINE, e);
			}
		}
	}

	private void setContainerDetailsArrival(MailFlightSummaryVO mailFlightSummaryVO,
			AirlineValidationVO airlineValidationVO, int segSerial, Collection<FlightValidationVO> flightValidationVOs,
			ScannedMailDetailsVO scannedMailDetailsVO) {
		for (ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()) {
			containerVO.setCarrierCode(airlineValidationVO.getAlphaCode());
			containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
			containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			if (mailFlightSummaryVO.getFlightDate() != null) {
				containerVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
			} else {
				containerVO.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
			}
			containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			if (segSerial > 0) {
				containerVO.setSegmentSerialNumber(segSerial);
			}
			containerVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			containerVO.setType(scannedMailDetailsVO.getContainerType());
		}
	}

	private void setScanMailDetailsArrival(MailFlightSummaryVO mailFlightSummaryVO,
			AirlineValidationVO airlineValidationVO, int segSerial, Collection<FlightValidationVO> flightValidationVOs,
			ScannedMailDetailsVO scannedMailDetailsVO, String containerNumber, String uldPol) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		boolean isBKDExist = false;
		boolean isACPExist = false;

		for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
			if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailbagVO.getMailStatus())) {
				isBKDExist = true;
			}
			if (MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getMailStatus())) {
				isACPExist = true;
			}
		}

		setScanMailDetailVOArrivalContainerDetails(mailFlightSummaryVO, scannedMailDetailsVO, containerNumber, uldPol,
				isBKDExist, isACPExist);

		scannedMailDetailsVO.setProcessPoint(mailFlightSummaryVO.getEventCode());
		scannedMailDetailsVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));

		setAirportCode(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO);

		scannedMailDetailsVO.setCarrierCode(airlineValidationVO.getAlphaCode());

		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			scannedMailDetailsVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());

			if (mailFlightSummaryVO.getFlightDate() != null) {
				scannedMailDetailsVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
			} else {
				scannedMailDetailsVO
						.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
			}

		}

		setMailBagDetailsArrival(mailFlightSummaryVO, segSerial, flightValidationVOs, scannedMailDetailsVO, uldPol,
				isBKDExist, isACPExist);

	}

	private void setMailBagDetailsArrival(MailFlightSummaryVO mailFlightSummaryVO, int segSerial,
			Collection<FlightValidationVO> flightValidationVOs, ScannedMailDetailsVO scannedMailDetailsVO,
			String uldPol, boolean isBKDExist, boolean isACPExist) throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		AirlineValidationVO airlineValidationVO = new SharedAirlineProxy()
				.findAirline(mailFlightSummaryVO.getCompanyCode(), mailFlightSummaryVO.getCarrierId());
		for (MailbagVO mailbagVO : scannedMailDetailsVO.getMailDetails()) {
			if (isBKDExist && isACPExist && MailConstantsVO.MAIL_STATUS_ACCEPTED.equals(mailbagVO.getMailStatus())) {
				scannedMailDetailsVO.setContainerNumber(mailbagVO.getContainerNumber());
				scannedMailDetailsVO.setContainerType(mailbagVO.getContainerType());
				scannedMailDetailsVO.setPol(mailbagVO.getPol());
				scannedMailDetailsVO.setDestination(mailbagVO.getPou());
			}
			scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
			scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			scannedMailDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
			scannedMailDetailsVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			if (segSerial > 0) {
				scannedMailDetailsVO.setSegmentSerialNumber(segSerial);
			}
			mailbagVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			mailbagVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			if (segSerial > 0) {
				mailbagVO.setSegmentSerialNumber(segSerial);
			}
			mailbagVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			if (scannedMailDetailsVO.isFoundArrival() && uldPol != null && uldPol.trim().length() > 0) {
				mailbagVO.setPol(uldPol);
			}
			mailbagVO.setCarrierCode(airlineValidationVO.getAlphaCode());
			mailbagVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
			mailbagVO.setContainerType(scannedMailDetailsVO.getContainerType());
			mailbagVO.setUldNumber(scannedMailDetailsVO.getContainerNumber());
			mailbagVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			if (mailFlightSummaryVO.getFlightDate() != null) {
				mailbagVO.setFlightDate(mailFlightSummaryVO.getFlightDate());
			} else {
				mailbagVO.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
			}
			mailbagVO.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
			mailbagVO.setScannedUser(logonAttributes.getUserId());

		}
	}

	private void setScanMailDetailVOArrivalContainerDetails(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO, String containerNumber, String uldPol, boolean isBKDExist,
			boolean isACPExist) {
		if ((scannedMailDetailsVO.getContainerNumber() == null || (!(mailFlightSummaryVO.getFlightNumber()
				.equals(scannedMailDetailsVO.getFlightNumber())
				&& mailFlightSummaryVO.getFlightSequenceNumber() == scannedMailDetailsVO.getFlightSequenceNumber())))
				&& !(isBKDExist && isACPExist)) {

			scannedMailDetailsVO.setFoundArrival(true);
			scannedMailDetailsVO.setContainerNumber(containerNumber);
			if (uldPol != null && uldPol.trim().length() > 0) {
				scannedMailDetailsVO.setPol(uldPol);
			}
			if (MailConstantsVO.CONST_BULK.equals(containerNumber)) {
				scannedMailDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			} else {
				scannedMailDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}

		}
	}

	private int getShpSegSerNum(MailFlightSummaryVO mailFlightSummaryVO, int shpSegSerNum, String awbKey) {
		if (mailFlightSummaryVO.getShpDetailMap() != null && !mailFlightSummaryVO.getShpDetailMap().isEmpty()) {// ICRD-320931
			try {
				shpSegSerNum = Integer.parseInt(mailFlightSummaryVO.getShpDetailMap().get(awbKey));
			} catch (NumberFormatException numberFormatException) {
				LOGGER.log(Log.FINE, numberFormatException);
			}

		}
		return shpSegSerNum;
	}

	private String getULDPOL(MailFlightSummaryVO mailFlightSummaryVO, String awbKey) {

		String[] uldArr = null;
		String uldPol = null;
		if (mailFlightSummaryVO.getUldAwbMap() != null && !mailFlightSummaryVO.getUldAwbMap().isEmpty()) {
			uldArr = mailFlightSummaryVO.getUldAwbMap().get(awbKey).split("~");
			if (uldArr.length > 1) {
				uldPol = uldArr[1];
			}
		}
		return uldPol;
	}

	private String getContainerNumber(MailFlightSummaryVO mailFlightSummaryVO, String awbKey) {
		String[] uldArr = null;
		String containerNumber = null;
		if (mailFlightSummaryVO.getUldAwbMap() != null && !mailFlightSummaryVO.getUldAwbMap().isEmpty()) {
			uldArr = mailFlightSummaryVO.getUldAwbMap().get(awbKey).split("~");
			containerNumber = uldArr[0];
		}
		return containerNumber;
	}

	private int getSegSerNumber(ScannedMailDetailsVO scannedMailDetailsVO, ShipmentSummaryVO shipmentSummaryVO,
			MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes) throws SystemException {

		int segSerial = 1;
		ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
		manifestFilterVO.setManifestPrint(false);
		manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());

		manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
		manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());

		manifestFilterVO.setAirportCode(logonAttributes.getAirportCode());
		manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
		ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
		shipmentValidationVO.setOwnerId(shipmentSummaryVO.getOwnerId());
		shipmentValidationVO.setDocumentNumber(shipmentSummaryVO.getMasterDocumentNumber());
		shipmentValidationVO.setDuplicateNumber(shipmentSummaryVO.getDuplicateNumber());
		shipmentValidationVO.setSequenceNumber(shipmentSummaryVO.getSequenceNumber());
		CTOShipmentManifestVO manifestvo = new OperationsFltHandlingProxy()
				.findShipmentForImportManifest(manifestFilterVO, shipmentValidationVO);

		if (manifestvo != null) {
			segSerial = manifestvo.getSegmentSerialNumber();
		}

		return segSerial;

	}

	private ScannedMailDetailsVO getScannedMailDetailsVOsArrival(MailFlightSummaryVO mailFlightSummaryVO,
			String importMailsForSplitBooking, ShipmentSummaryVO shipmentSummaryVO) throws SystemException {
		ScannedMailDetailsVO scannedMailDetailsVO = new ScannedMailDetailsVO();
		if (BOOKED_FLIGHT.equals(importMailsForSplitBooking)) {
			scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails(shipmentSummaryVO, mailFlightSummaryVO);
		} else if (FIRST_FLIGHT.equals(importMailsForSplitBooking)) {
			scannedMailDetailsVO = new Mailbag().findAWBAttachedMailbags(shipmentSummaryVO, mailFlightSummaryVO);
		}
		return scannedMailDetailsVO;
	}

	private Collection<FlightValidationVO> getFlightValidationVOsImport(MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException {
		Collection<FlightValidationVO> flightValidationVOs;

		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		filghtFilterVO.setDirection("I");
		flightValidationVOs = new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
		return flightValidationVOs;
	}

	public String findSystemParameterValue(String syspar) throws SystemException {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		HashMap<String, String> systemParameterMap = (HashMap<String, String>) Proxy.getInstance()
				.get(SharedDefaultsProxy.class).findSystemParameterByCodes(systemParameters);
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	public void performMailDeliveryOperations(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes)
			throws SystemException {

		int segSerial = 1;
		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {

			Collection<MailbagVO> dlvMails = new ArrayList<>();
			HashMap<String, String> dupCheckMap = new HashMap<>();
			ScannedMailDetailsVO scannedMailDetailsVO = new Mailbag().findAwbAtachedMailbagDetails(shipmentSummaryVO,
					mailFlightSummaryVO);
			scannedMailDetailsVO.setProcessPoint(mailFlightSummaryVO.getEventCode());
			scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());

			setFlightDetailsDelivery(mailFlightSummaryVO, scannedMailDetailsVO);

			scannedMailDetailsVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));

			for (MailbagVO mailbagVo : scannedMailDetailsVO.getMailDetails()) {

				mailbagVo.setScannedDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));

				Collection<FlightValidationVO> flightValidationVOs = getFlightValidationVOsDelivery(mailFlightSummaryVO,
						logonAttributes, scannedMailDetailsVO, mailbagVo);

				setMailBagFlightDetailsDelivery(mailbagVo, flightValidationVOs);

				CTOShipmentManifestVO manifestvo = getCTOShipmentManifestVO(mailFlightSummaryVO, logonAttributes,
						shipmentSummaryVO, scannedMailDetailsVO);

				if (manifestvo != null) {
					segSerial = manifestvo.getSegmentSerialNumber();
				}

				setFlightDetailsOfNonSplitBookings(mailFlightSummaryVO, segSerial, scannedMailDetailsVO, mailbagVo,
						flightValidationVOs);

				if (flightValidationVOs != null) {
					setContainerDetailsDelivery(mailFlightSummaryVO, logonAttributes, segSerial, scannedMailDetailsVO,
							flightValidationVOs);
				}

				if (!dupCheckMap.containsKey(mailbagVo.getMailbagId())) {
					dlvMails.add(mailbagVo);
				}
				dupCheckMap.put(mailbagVo.getMailbagId(), mailbagVo.getMailbagId());
			}
			scannedMailDetailsVO.setMailDetails(dlvMails);

			try {
				new MailUploadController().saveDeliverFromUpload(scannedMailDetailsVO, logonAttributes);
			} catch (MailHHTBusniessException | MailMLDBusniessException | ForceAcceptanceException e) {
				LOGGER.log(Log.FINE, e);

			}
		}
	}

	private void setFlightDetailsOfNonSplitBookings(MailFlightSummaryVO mailFlightSummaryVO, int segSerial,
			ScannedMailDetailsVO scannedMailDetailsVO, MailbagVO mailbagVo,
			Collection<FlightValidationVO> flightValidationVOs) {
		if (!scannedMailDetailsVO.isSplitBooking() && flightValidationVOs != null) {
			scannedMailDetailsVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
			scannedMailDetailsVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			scannedMailDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
			scannedMailDetailsVO.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			scannedMailDetailsVO.setSegmentSerialNumber(segSerial);
			mailbagVo.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			mailbagVo.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			mailbagVo.setSegmentSerialNumber(segSerial);
			mailbagVo.setLegSerialNumber(
					((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			mailbagVo.setPol(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegOrigin());

		}
	}

	private void setContainerDetailsDelivery(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			int segSerial, ScannedMailDetailsVO scannedMailDetailsVO,
			Collection<FlightValidationVO> flightValidationVOs) {

		if (isScannedContainerDetailsNotEmpty(scannedMailDetailsVO)) {
			for (ContainerVO containerVO : scannedMailDetailsVO.getScannedContainerDetails()) {
				if (!scannedMailDetailsVO.isSplitBooking()) {
					containerVO.setCarrierCode(
							((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getCarrierCode());
					containerVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
					containerVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());

					if (((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getSta() != null) {
						containerVO
								.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getSta());
					} else {
						containerVO.setFlightDate(
								((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
					}

					containerVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
					if (segSerial > 0) {
						containerVO.setSegmentSerialNumber(segSerial);
					}
					containerVO.setLegSerialNumber(
							((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
				}
				containerVO.setContainerNumber(scannedMailDetailsVO.getContainerNumber());
				containerVO.setType(scannedMailDetailsVO.getContainerType());
				containerVO.setLastUpdateTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
				containerVO
						.setAssignedPort(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegOrigin());
				containerVO.setPol(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegOrigin());

			}
		}
	}

	private boolean isScannedContainerDetailsNotEmpty(ScannedMailDetailsVO scannedMailDetailsVO) {
		return scannedMailDetailsVO.getScannedContainerDetails() != null
				&& !scannedMailDetailsVO.getScannedContainerDetails().isEmpty();
	}

	private CTOShipmentManifestVO getCTOShipmentManifestVO(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ShipmentSummaryVO shipmentSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		ManifestFilterVO manifestFilterVO = new ManifestFilterVO();
		manifestFilterVO.setManifestPrint(false);
		manifestFilterVO.setCompanyCode(scannedMailDetailsVO.getCompanyCode());
		if (scannedMailDetailsVO.isSplitBooking()) {
			manifestFilterVO.setCarrierId(scannedMailDetailsVO.getCarrierId());
			manifestFilterVO.setFlightNumber(scannedMailDetailsVO.getFlightNumber());
			manifestFilterVO.setFlightSequenceNumber(scannedMailDetailsVO.getFlightSequenceNumber());
		} else {
			manifestFilterVO.setCarrierId(mailFlightSummaryVO.getCarrierId());
			manifestFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			manifestFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		}
		manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
		manifestFilterVO.setAirportCode(logonAttributes.getAirportCode());
		manifestFilterVO.setPointOfLading(logonAttributes.getAirportCode());
		ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
		shipmentValidationVO.setOwnerId(shipmentSummaryVO.getOwnerId());
		shipmentValidationVO.setDocumentNumber(shipmentSummaryVO.getMasterDocumentNumber());
		shipmentValidationVO.setDuplicateNumber(shipmentSummaryVO.getDuplicateNumber());
		shipmentValidationVO.setSequenceNumber(shipmentSummaryVO.getSequenceNumber());
		return new OperationsFltHandlingProxy().findShipmentForImportManifest(manifestFilterVO, shipmentValidationVO);
	}

	private void setMailBagFlightDetailsDelivery(MailbagVO mailbagVo,
			Collection<FlightValidationVO> flightValidationVOs) {
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			mailbagVo.setCarrierCode(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getCarrierCode());

			if (((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getSta() != null) {
				mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getSta());
			} else {
				mailbagVo.setFlightDate(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getFlightDate());
			}

		}
	}

	private Collection<FlightValidationVO> getFlightValidationVOsDelivery(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO, MailbagVO mailbagVo)
			throws SystemException {
		FlightFilterVO flightFilterVO;
		flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(mailbagVo.getCompanyCode());
		flightFilterVO.setDirection(FlightFilterVO.INBOUND);
		if (scannedMailDetailsVO.isSplitBooking()) {
			flightFilterVO.setFlightNumber(mailbagVo.getFlightNumber());
			flightFilterVO.setFlightCarrierId(mailbagVo.getCarrierId());
			flightFilterVO.setFlightSequenceNumber(mailbagVo.getFlightSequenceNumber());
		} else {
			flightFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			flightFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
			flightFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		}

		if (mailFlightSummaryVO.getAirportCode() != null) {
			flightFilterVO.setStation(mailFlightSummaryVO.getAirportCode());
		} else {
			flightFilterVO.setStation(logonAttributes.getAirportCode());
		}

		return new FlightOperationsProxy().validateFlightForAirport(flightFilterVO);
	}

	private void setFlightDetailsDelivery(MailFlightSummaryVO mailFlightSummaryVO,
			ScannedMailDetailsVO scannedMailDetailsVO) throws SystemException {
		if (scannedMailDetailsVO.getMailDetails() != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			for (MailbagVO mailVO : scannedMailDetailsVO.getMailDetails()) {
				MailbagPK mailPK = new MailbagPK();
				mailPK.setCompanyCode(mailVO.getCompanyCode());
				mailPK.setMailSequenceNumber(mailVO.getMailSequenceNumber());
				try {
					Mailbag mail = Mailbag.find(mailPK);
					if (mail != null && mail.getFlightNumber() != null && !"-1".equals(mail.getFlightNumber())) {
						scannedMailDetailsVO.setCarrierId(mail.getCarrierId());
						scannedMailDetailsVO.setFlightNumber(mail.getFlightNumber());
						scannedMailDetailsVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
						mailFlightSummaryVO.setCarrierId(mail.getCarrierId());
						mailFlightSummaryVO.setFlightNumber(mail.getFlightNumber());
						mailFlightSummaryVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
						mailVO.setCarrierId(mail.getCarrierId());
						mailVO.setFlightNumber(mail.getFlightNumber());
						mailVO.setFlightSequenceNumber(mail.getFlightSequenceNumber());
					}
				} catch (FinderException e) {
					LOGGER.log(Log.FINE, e);
				}
			}
		}
	}

	private int getSegmentSerialNumber(MailBookingDetailVO mailBookingDetailVO) {
		Collection<FlightSegmentSummaryVO> segmentSummaryVos = null;
		int segmentSerialNumber = 0;
		try {
			if(mailBookingDetailVO.getBookingFlightNumber()!=null){
			segmentSummaryVos = new FlightOperationsProxy().findFlightSegments(mailBookingDetailVO.getCompanyCode(),
					mailBookingDetailVO.getBookingFlightCarrierid(),mailBookingDetailVO.getBookingFlightNumber(),
					mailBookingDetailVO.getBookingFlightSequenceNumber());
			}else{
				if(mailBookingDetailVO.getPlannedFlight()!=null){
						segmentSummaryVos = new FlightOperationsProxy().findFlightSegments(mailBookingDetailVO.getCompanyCode(),
					mailBookingDetailVO.getBookingFlightCarrierid(),mailBookingDetailVO.getPlannedFlight().split(" ")[1],
					mailBookingDetailVO.getBookingFlightSequenceNumber());
				}
			}
		} catch (SystemException e) {
			LOGGER.log(Log.FINE, e);
		}
		if (segmentSummaryVos != null && !segmentSummaryVos.isEmpty()) {
			for (FlightSegmentSummaryVO segmentVo : segmentSummaryVos) {
				if (segmentVo.getSegmentOrigin() != null && segmentVo.getSegmentDestination() != null
						&& segmentVo.getSegmentOrigin().equals(mailBookingDetailVO.getOrigin())
						&& segmentVo.getSegmentDestination().equals(mailBookingDetailVO.getDestination())) {
					segmentSerialNumber = segmentVo.getSegmentSerialNumber();
					break;
				}
			}
		}
		return segmentSerialNumber;
	}

	public void performMailTransferOperations(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			String importMailsForSplitBooking) throws SystemException {

		if (EVENT_RMPTRA.equals(mailFlightSummaryVO.getEventCode())) {
			mailFlightSummaryVO.setEventCode(EVENT_ARR);
			performMailArrivalOperations(mailFlightSummaryVO, logonAttributes, importMailsForSplitBooking);
			mailFlightSummaryVO.setEventCode(EVENT_RMPTRA);
		}

		Collection<FlightValidationVO> flightValidationVOs = flightValidationVOsTransfer(mailFlightSummaryVO,
				logonAttributes);

		for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVO.getShipmentSummaryVOs()) {
			ScannedMailDetailsVO scannedMailDetailsVO = null;
			List<TransferManifestVO> transferManifestRejVOList = new ArrayList<>();
			scannedMailDetailsVO = getScannedMailDetailsVOForExport(mailFlightSummaryVO, importMailsForSplitBooking,
					shipmentSummaryVO);
			setMailSourceAndTransferManifestVO(scannedMailDetailsVO, mailFlightSummaryVO, transferManifestRejVOList);
			if (!transferManifestRejVOList.isEmpty()) {
				for (TransferManifestVO transferManifest : transferManifestRejVOList) {
					new MailController().rejectTransferFromManifest(transferManifest);
				}
			} else {
			boolean success = true;

			if (isScanMailBagDetailsNotEmpty(scannedMailDetailsVO)) {

				setScanMailDetailsTransfer(mailFlightSummaryVO, logonAttributes, flightValidationVOs,
						scannedMailDetailsVO);

				boolean isWetLeased = false;
				if (mailFlightSummaryVO.getToFlightNumber() != null
						&& mailFlightSummaryVO.getToFlightNumber().trim().length() > 0) {
					isWetLeased = checkForWetleasedFlight(mailFlightSummaryVO, logonAttributes);
				}

				success = setContainerDetailsTransfer(mailFlightSummaryVO, logonAttributes, scannedMailDetailsVO,
						success, isWetLeased);
				if (success) {
					try {
						new MailUploadController().saveTransferFromUpload(scannedMailDetailsVO, logonAttributes);
					} catch (MailHHTBusniessException | MailMLDBusniessException | MailTrackingBusinessException
							| ForceAcceptanceException e) {
						LOGGER.log(Log.FINE, e);
					}
				}

			}
		}
		}

	}

	private void setMailSourceAndTransferManifestVO(ScannedMailDetailsVO scannedMailDetailsVO,MailFlightSummaryVO mailFlightSummaryVO,List<TransferManifestVO> transferManifestVOList){
		if (scannedMailDetailsVO != null && !scannedMailDetailsVO.getMailDetails().isEmpty()) {
			
			scannedMailDetailsVO.getMailDetails().forEach(mailbagVo -> {
				if (mailFlightSummaryVO.getTransferStatus()!=null&&mailFlightSummaryVO.getTransferStatus().equals("COMPLETE")) {
					mailbagVo.setMailSource("EXPFLTFIN_TRAMAL");
					mailbagVo.setMailStatus(MailConstantsVO.MAIL_STATUS_TRANSFERRED);
				}
				if(mailFlightSummaryVO.getTransferStatus()!=null&&mailFlightSummaryVO.getTransferStatus().equals("TRANSFER_REJECT")){
					TransferManifestVO transferManifestVO=new TransferManifestVO();
					transferManifestVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
					transferManifestVO.setMailsequenceNumber(mailbagVo.getMailSequenceNumber());
					transferManifestVO.setTranferSource("EXPFLTFIN_TRAREJCTMAL");
					transferManifestVO.setTransferredToCarrierCode(mailFlightSummaryVO.getToCarrierCode());
					if(transferManifestVOList.stream().noneMatch(transfr->transfr.getMailsequenceNumber()==transferManifestVO.getMailsequenceNumber())){
					transferManifestVOList.add(transferManifestVO);}
				}
			});
			
		}
	}
	private boolean setContainerDetailsTransfer(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes, ScannedMailDetailsVO scannedMailDetailsVO, boolean success,
			boolean isWetLeased) throws SystemException {
		if (logonAttributes.getOwnAirlineCode().equals(scannedMailDetailsVO.getToCarrierCode()) || isWetLeased) {
			ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
			containerDetailsVO.setCarrierCode(mailFlightSummaryVO.getToCarrierCode());
			containerDetailsVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
			containerDetailsVO.setContainerNumber(mailFlightSummaryVO.getToContainerNumber());
			containerDetailsVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
			containerDetailsVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
			containerDetailsVO.setFlightDate(mailFlightSummaryVO.getToFlightDate());
			containerDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
			containerDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
			containerDetailsVO.setLegSerialNumber(mailFlightSummaryVO.getToLegSerialNumber());
			containerDetailsVO.setPou(mailFlightSummaryVO.getFinalDestination());
			containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
			containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_FLAG_INSERT);
			if (MailConstantsVO.CONST_BULK.equals(containerDetailsVO.getContainerNumber())) {
				containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
			} else {
				containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
			}
			containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
			scannedMailDetailsVO.setValidatedContainer(containerDetailsVO);
			success = saveContainerforTransfer(containerDetailsVO, mailFlightSummaryVO);
			scannedMailDetailsVO.setScannedContainerDetails(null);
		}
		scannedMailDetailsVO.setScannedContainerDetails(null);
		return success;
	}

	private boolean saveContainerforTransfer(ContainerDetailsVO containerDetailsVO,
			MailFlightSummaryVO mailFlightSummaryVO) throws SystemException {
		return new AddonsMailOperationProxy().saveContainerTransfer(containerDetailsVO, mailFlightSummaryVO);
	}

	public boolean saveContainerTransfer(ContainerDetailsVO containerDetailsVO, MailFlightSummaryVO mailFlightSummaryVO)
			throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();

		Collection<ContainerDetailsVO> containers = new ArrayList<>();
		containerDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		containerDetailsVO.setContainerNumber(mailFlightSummaryVO.getToContainerNumber());

		if (MailConstantsVO.CONST_BULK.equals(containerDetailsVO.getContainerNumber())) {
			containerDetailsVO.setContainerType(MailConstantsVO.BULK_TYPE);
		} else {
			containerDetailsVO.setContainerType(MailConstantsVO.ULD_TYPE);
		}

		containerDetailsVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
		containerDetailsVO.setPol(logonAttributes.getAirportCode());
		containerDetailsVO.setSegmentSerialNumber(mailFlightSummaryVO.getToSegmentSerialNumber());
		containerDetailsVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		containerDetailsVO.setAcceptedFlag(MailConstantsVO.FLAG_YES);
		containerDetailsVO.setArrivedStatus(MailConstantsVO.FLAG_NO);
		containerDetailsVO.setTransactionCode(MailConstantsVO.MAIL_TXNCOD_ASG);
		containerDetailsVO.setAssignmentDate(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		containerDetailsVO.setOperationFlag(MailConstantsVO.OPERATION_INBOUND);
		containerDetailsVO.setContainerOperationFlag(MailConstantsVO.OPERATION_INBOUND);
		containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
		containerDetailsVO.setDestination(mailFlightSummaryVO.getFinalDestination());
		containers.add(containerDetailsVO);
		MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		mailAcceptanceVO.setFlightCarrierCode(mailFlightSummaryVO.getToCarrierCode());
		mailAcceptanceVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
		mailAcceptanceVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
		mailAcceptanceVO.setCarrierId(mailFlightSummaryVO.getToCarrierId());
		mailAcceptanceVO.setLegSerialNumber(mailFlightSummaryVO.getToLegSerialNumber());
		mailAcceptanceVO.setFlightDate(mailFlightSummaryVO.getToFlightDate());
		mailAcceptanceVO.setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		mailAcceptanceVO.setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		mailAcceptanceVO.setAcceptedUser(logonAttributes.getUserId().toUpperCase());
		mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
		mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
		mailAcceptanceVO.setPreassignNeeded(false);
		mailAcceptanceVO.setDestination(mailFlightSummaryVO.getFinalDestination());
		mailAcceptanceVO.setContainerDetails(containers);
		boolean success = false;
		Transaction tx = null;
		TransactionProvider tm = PersistenceController.getTransactionProvider();
		tx = tm.getNewTransaction(false);
		try {
			new MailController().saveAcceptanceDetails(mailAcceptanceVO);
			success = true;
		} catch (DuplicateMailBagsException | FlightClosedException | ContainerAssignmentException
				| InvalidFlightSegmentException | ULDDefaultsProxyException | DuplicateDSNException
				| CapacityBookingProxyException | MailBookingException | MailDefaultStorageUnitException e) {
			throw new SystemException(SystemException.UNEXPECTED_SERVER_ERROR, e);
		} finally {
			if (success) {
				tx.commit();
			} else {
				tx.rollback();
			}
		}
		return success;
	}

	private void setScanMailDetailsTransfer(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes,
			Collection<FlightValidationVO> flightValidationVOs, ScannedMailDetailsVO scannedMailDetailsVO)
			throws SystemException {
		scannedMailDetailsVO.setProcessPoint(mailFlightSummaryVO.getEventCode());
		scannedMailDetailsVO.setAirportCode(logonAttributes.getAirportCode());
		scannedMailDetailsVO.setCompanyCode(logonAttributes.getCompanyCode());
		scannedMailDetailsVO.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
		scannedMailDetailsVO.setToCarrierCode(mailFlightSummaryVO.getToCarrierCode());

		for (MailbagVO mailbag : scannedMailDetailsVO.getMailDetails()) {
			MailbagPK mailPK = new MailbagPK();
			Mailbag mail = null;
			mailPK.setCompanyCode(mailbag.getCompanyCode());
			mailPK.setMailSequenceNumber(mailbag.getMailSequenceNumber());
			try {
				mail = Mailbag.find(mailPK);
			} catch (FinderException e) {
				LOGGER.log(Log.FINE, e);
			}
			if (mail != null && "BKD".equals(mail.getLatestStatus())) {
				break;
			}

			mailbag.setScannedPort(logonAttributes.getAirportCode());

			if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
				mailbag.setLegSerialNumber(
						((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getLegSerialNumber());
			}
			mailbag.setCarrierCode(mailFlightSummaryVO.getCarrierCode());
			mailbag.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
			mailbag.setFlightDate(mailFlightSummaryVO.getFlightDate());
			if (mailbag.getFlightSequenceNumber() == 0) {
				scannedMailDetailsVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
				mailbag.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
			}
			mailbag.setArrivedFlag(MailConstantsVO.FLAG_YES);
			mailbag.setCarrierId(mailFlightSummaryVO.getCarrierId());
			mailbag.setFinalDestination(mailbag.getPou());
			mailbag.setInventoryContainer(mailbag.getContainerNumber());
			mailbag.setInventoryContainerType(mailbag.getContainerType());
			mailbag.setOperationalStatus(MailConstantsVO.OPERATION_INBOUND);

		}
		scannedMailDetailsVO.setToCarrierid(mailFlightSummaryVO.getToCarrierId());
		scannedMailDetailsVO.setToFlightNumber(mailFlightSummaryVO.getToFlightNumber());
		scannedMailDetailsVO.setToFlightDate(mailFlightSummaryVO.getToFlightDate());
		scannedMailDetailsVO.setToFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		scannedMailDetailsVO.setOperationTime(new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true));
		scannedMailDetailsVO.setScannedContainerDetails(null);
	}

	private Collection<FlightValidationVO> flightValidationVOsTransfer(MailFlightSummaryVO mailFlightSummaryVO,
			LogonAttributes logonAttributes) throws SystemException {
		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getFlightSequenceNumber());
		filghtFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
		return new FlightOperationsProxy().validateFlightForAirport(filghtFilterVO);
	}

	protected boolean checkForWetleasedFlight(MailFlightSummaryVO mailFlightSummaryVO, LogonAttributes logonAttributes)
			throws SystemException {

		FlightFilterVO filghtFilterVO = new FlightFilterVO();
		filghtFilterVO.setCompanyCode(mailFlightSummaryVO.getCompanyCode());
		filghtFilterVO.setFlightNumber(mailFlightSummaryVO.getToFlightNumber());
		filghtFilterVO.setFlightCarrierId(mailFlightSummaryVO.getToCarrierId());
		filghtFilterVO.setFlightSequenceNumber(mailFlightSummaryVO.getToFlightSequenceNumber());
		filghtFilterVO.setDirection(MailConstantsVO.OPERATION_OUTBOUND);
		filghtFilterVO.setAirportCode(logonAttributes.getAirportCode());
		Collection<FlightValidationVO> flightValidationVOs = new FlightOperationsProxy()
				.validateFlightForAirport(filghtFilterVO);
		return (flightValidationVOs != null && !flightValidationVOs.isEmpty()
				&& FlightValidationVO.FLIGHT_AGRMNT_TYP_LEASED
						.equals(((ArrayList<FlightValidationVO>) flightValidationVOs).get(0).getAgreementType()));

	}

	public Collection<MailBookingDetailVO> fetchBookedFlightDetails(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException {
		LOGGER.entering(CLASS, "fetchBookedFlightDetails");
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;
		try {
			mailBookingDetailVOs = new MailBookingDetail().fetchBookedFlightDetails(companyCode, shipmentPrefix,
					masterDocumentNumber);
		} catch (PersistenceException e) {
			LOGGER.log(Log.INFO, e);
		}
		LOGGER.exiting(CLASS, "fetchBookedFlightDetails");
		return mailBookingDetailVOs;
	}

	public void saveMailBookingFlightDetails(Collection<MailbagVO> mailbagVOs,
			Collection<MailBookingDetailVO> selectedMailBookingDetailVOs)
			throws SystemException, ProxyException, FinderException {
		LOGGER.entering(CLASS, "saveMailBookingFlightDetails");
		setShipmntOrgnAndDes(mailbagVOs, selectedMailBookingDetailVOs);
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				Mailbag mailbag = null;
				long mailSequenceNumber = mailSequenceNumber(mailbagVO);
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(mailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(mailSequenceNumber);
				try {
					mailbag = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					LOGGER.log(Log.INFO, e);
				}
				if ("N".equals(mailbagVO.getAccepted()) && ( mailbag != null && mailbag.getLatestStatus() != null
						&& mailbag.getLatestStatus().trim().length() > 0)) {
					mailbag.setLatestStatus(mailbag.getLatestStatus());
				}
				findAndDeleteExistingMailBookingDetails(mailSequenceNumber);
				for (MailBookingDetailVO mailBookingDetailVO : selectedMailBookingDetailVOs) {
					mailBookingDetailVO.setBookingStatus(MailConstantsVO.MAIL_STATUS_AWB_BOOKED);
					mailBookingDetailVO.setMailSequenceNumber(mailSequenceNumber);
					mailBookingDetailVO.setCompanyCode(mailbagVO.getCompanyCode());
					new MailBookingDetail(mailBookingDetailVO);
				}
			}
		}
		LOGGER.exiting(CLASS, "saveMailBookingFlightDetails");
	}

	/**
	 * @author A-9951
	 * @param mailBookingDetailVOs
	 * @param shipmentOrigin
	 * @param shipmentDestination
	 * @return
	 */
	private boolean isValidRoute(Collection<MailBookingDetailVO> mailBookingDetailVOs, String shipmentOrigin,
			String shipmentDestination) {
		String tmpOrigin = "";
		String tmpDestination = "";
		for (MailBookingDetailVO mailBookingDetailVO : mailBookingDetailVOs) {
			if ("".equals(tmpOrigin)) {
				tmpOrigin = mailBookingDetailVO.getOrigin();
				tmpDestination = mailBookingDetailVO.getDestination();
			} else {
				if (tmpDestination.equals(mailBookingDetailVO.getOrigin())) {
					tmpDestination = mailBookingDetailVO.getDestination();
				} else {
					return false;
				}
			}
		}
		return (tmpOrigin.equals(shipmentOrigin) && tmpDestination.equals(shipmentDestination)) ? true : false;
	}

	private void setShipmntOrgnAndDes(Collection<MailbagVO> mailbagVOs,
			Collection<MailBookingDetailVO> selectedMailBookingDetailVOs) throws ProxyException, SystemException {
		Collection<ErrorVO> errors = new ArrayList<>();
		String shipmentOrigin = "";
		String shipmentDestination = "";
		if (mailbagVOs != null && !mailbagVOs.isEmpty()) {
			for (MailbagVO mailbagVO : mailbagVOs) {
				ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
				shipmentFilterVO.setCompanyCode(mailbagVO.getCompanyCode());
				shipmentFilterVO.setShipmentPrefix(mailbagVO.getShipmentPrefix());
				shipmentFilterVO.setMasterDocumentNumber(mailbagVO.getDocumentNumber());
				shipmentFilterVO.setDocumentNumber(mailbagVO.getDocumentNumber());
				shipmentFilterVO.setOwnerId(mailbagVO.getDocumentOwnerIdr());
				shipmentFilterVO.setSequenceNumber(mailbagVO.getSequenceNumber());
				shipmentFilterVO.setDuplicateNumber(mailbagVO.getDuplicateNumber());
				Collection<ShipmentVO> shipmentVOs = new OperationsShipmentProxy().findShipments(shipmentFilterVO);
				if (shipmentVOs != null && !shipmentVOs.isEmpty()) {
					for (ShipmentVO shipmentVO : shipmentVOs) {
						shipmentOrigin = shipmentVO.getOrigin();
						shipmentDestination = shipmentVO.getDestination();
					}
				}
			}
			validateFlightRoute(selectedMailBookingDetailVOs, shipmentOrigin, shipmentDestination, errors);
		}
	}

	private void validateFlightRoute(Collection<MailBookingDetailVO> selectedMailBookingDetailVOs,
			String shipmentOrigin, String shipmentDestination, Collection<ErrorVO> errors) throws SystemException {
		if (!isValidRoute(selectedMailBookingDetailVOs, shipmentOrigin, shipmentDestination)) {
			ErrorVO errorVO = new ErrorVO(STATUS_ROUTE_INCOMPLETE);
			errors.add(errorVO);
			throw new SystemException(errors);
		}
	}

	private void findAndDeleteExistingMailBookingDetails(long mailSequenceNumber)
			throws SystemException, FinderException {
		Collection<MailBookingDetailVO> mailBookingDetailVOs = new MailBookingDetail()
				.fetchBookedFlightDetailsForMailbag(mailSequenceNumber);
		if (mailBookingDetailVOs != null) {
			for (MailBookingDetailVO mailBookingDetailVOInMailbag : mailBookingDetailVOs) {
				if (MailConstantsVO.MAIL_STATUS_AWB_BOOKED.equals(mailBookingDetailVOInMailbag.getBookingStatus())
						|| MailConstantsVO.MAIL_STATUS_NEW.equals(mailBookingDetailVOInMailbag.getBookingStatus())) {
					MailBookingDetail mailBookingFlightDetail = null;
					MailBookingDetailPK mailBookingDetailPK = new MailBookingDetailPK();
					mailBookingDetailPK.setCompanyCode(mailBookingDetailVOInMailbag.getCompanyCode());
					mailBookingDetailPK.setMailSequenceNumber(mailSequenceNumber);
					mailBookingDetailPK.setFlightNumber(mailBookingDetailVOInMailbag.getBookingFlightNumber());
					mailBookingDetailPK.setFlightCarrierId(mailBookingDetailVOInMailbag.getBookingFlightCarrierid());
					mailBookingDetailPK
							.setFlightSequenceNumber(mailBookingDetailVOInMailbag.getBookingFlightSequenceNumber());
					mailBookingDetailPK.setSegementserialNumber(mailBookingDetailVOInMailbag.getSegementserialNumber());
					mailBookingDetailPK.setSerialNumber(mailBookingDetailVOInMailbag.getSerialNumber());
					try {
						mailBookingFlightDetail = MailBookingDetail.find(mailBookingDetailPK);
						if (mailBookingFlightDetail != null) {
							mailBookingFlightDetail.remove();
						}
					} catch (FinderException e) {
						LOGGER.log(Log.INFO, e);
					}
				} 
			}
		}
	}

	public Page<MailBookingDetailVO> findLoadPlanBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws SystemException {
		return constructDAO().findLoadPlanBookings(mailBookingFilterVO,pageNumber);
	}

	public Collection<MailBookingDetailVO> findFlightDetailsforAWB(String companyCode, String shipmentPrefix,
			String masterDocumentNumber) throws SystemException {
		LOGGER.entering(CLASS, "findFlightDetailsforAWB");
		Collection<MailBookingDetailVO> mailBookingDetailVOs = null;
		try {
			mailBookingDetailVOs = new MailBookingDetail().findFlightDetailsforAWB(companyCode, shipmentPrefix,
					masterDocumentNumber);
		} catch (PersistenceException e) {
			LOGGER.log(Log.INFO, e);
		}
		LOGGER.exiting(CLASS, "findFlightDetailsforAWB"); 
		return mailBookingDetailVOs;
	}
	 public Page<MailBookingDetailVO> findManifestBookings(MailBookingFilterVO mailBookingFilterVO, int pageNumber) throws SystemException {
		return constructDAO().findManifestBookings(mailBookingFilterVO,pageNumber);
	}
	 
	public int findSplitCount(String mstDocunum, String docOwnerId, String companyCode, int seqNum, int dupNum)
			throws SystemException {

		return constructDAO().findSplitCount(mstDocunum, docOwnerId, companyCode, seqNum, dupNum);
	}


}
