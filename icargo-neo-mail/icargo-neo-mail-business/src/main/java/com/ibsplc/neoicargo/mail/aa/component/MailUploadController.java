package com.ibsplc.neoicargo.mail.aa.component;

import com.ibsplc.xibase.server.framework.persistence.FinderException;
import com.ibsplc.neoicargo.framework.core.security.LoginProfile;
import java.time.ZonedDateTime;
import com.ibsplc.neoicargo.framework.core.lang.SystemException;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import static com.ibsplc.neoicargo.mail.vo.MailConstantsVO.DESTN_FLT;
import com.ibsplc.neoicargo.mail.exception.ForceAcceptanceException;
import com.ibsplc.neoicargo.mail.component.MailAcceptance;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.exception.MailMLDBusniessException;
import com.ibsplc.neoicargo.mail.component.Mailbag;
import com.ibsplc.neoicargo.mail.component.MailbagPK;
import com.ibsplc.neoicargo.mail.errorhandling.exception.MailHHTBusniessException;
import com.ibsplc.neoicargo.mail.vo.MailAcceptanceVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.OfficeOfExchangeVO;
import com.ibsplc.neoicargo.mail.vo.ScannedMailDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
public class MailUploadController extends  com.ibsplc.neoicargo.mail.component.MailUploadController {
	@Autowired
	private LocalDate localDateUtil;
	@Autowired
	private ContextUtil contextUtil;

	/** 
	* @author A-7871
	* @return
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	* @throws SystemException 
	*/
	public ScannedMailDetailsVO doSpecificValidations(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException {
		OfficeOfExchangeVO originOfficeOfExchangeVO;
		OfficeOfExchangeVO destOfficeOfExchangeVO;
		boolean coTerminusDelivery;
		String poaCode = null;
		String destinationPort = null;
		String airportCode = null;
		String isCoterminusConfigured = findSystemParameterValue(MailConstantsVO.IS_COTERMINUS_CONFIGURED);
		log.debug("MailUploadController" + " : " + "doSpecificValidations" + " Entering");
		Mailbag mailBag = null;
		LoginProfile logonAttributes = null;
		if (scannedMailDetailsVO.getMailDetails() != null && scannedMailDetailsVO.getMailDetails().size() > 0) {
			try {
				logonAttributes = contextUtil.callerLoginProfile();
			} finally {
			}
			for (MailbagVO scannedMailbagVO : scannedMailDetailsVO.getMailDetails()) {
				ZonedDateTime dspDate = localDateUtil.getLocalDate(scannedMailbagVO.getScannedPort(), true);
				MailbagPK mailbagPk = new MailbagPK();
				mailbagPk.setCompanyCode(scannedMailbagVO.getCompanyCode());
				mailbagPk.setMailSequenceNumber(Mailbag.findMailBagSequenceNumberFromMailIdr(
						scannedMailbagVO.getMailbagId(), scannedMailbagVO.getCompanyCode()));
				try {
					mailBag = Mailbag.find(mailbagPk);
				} catch (FinderException e) {
					log.error("Finder Exception Caught");
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				if (mailBag != null) {
					dspDate = localDateUtil.getLocalDateTime(mailBag.getDespatchDate(), null);
					if (mailBag.getPaCode() != null) {
						poaCode = mailBag.getPaCode();
					}
					if (scannedMailbagVO.getDestination() == null || scannedMailbagVO.getDestination().isEmpty()) {
						scannedMailbagVO.setDestination(mailBag.getDestination());
					}
				} else if (mailBag == null || mailBag.getOrigin() == null || mailBag.getOrigin().isEmpty()
						|| mailBag.getPaCode() == null || mailBag.getPaCode().isEmpty()
						|| mailBag.getDestination() == null || mailBag.getDestination().isEmpty()) {
					originOfficeOfExchangeVO = new MailController()
							.validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getOoe());
					if (originOfficeOfExchangeVO != null) {
						poaCode = originOfficeOfExchangeVO.getPoaCode();
						if (originOfficeOfExchangeVO != null && originOfficeOfExchangeVO.getAirportCode() == null) {
							String orginOfficeExchange = null;
							orginOfficeExchange = originOfficeOfExchangeVO.getCode();
							airportCode = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
									orginOfficeExchange);
							originOfficeOfExchangeVO.setAirportCode(airportCode);
						}
					}
					if (scannedMailbagVO.getDestination() == null || scannedMailbagVO.getDestination().isEmpty()) {
						destOfficeOfExchangeVO = new MailController()
								.validateOfficeOfExchange(scannedMailbagVO.getCompanyCode(), scannedMailbagVO.getDoe());
						if (destOfficeOfExchangeVO != null && destOfficeOfExchangeVO.getAirportCode() == null) {
							String destOfficeOfExchange = destOfficeOfExchangeVO.getCode();
							destinationPort = findNearestAirportOfCity(scannedMailbagVO.getCompanyCode(),
									destOfficeOfExchange);
							scannedMailbagVO.setDestination(destinationPort);
							destOfficeOfExchangeVO.setAirportCode(destinationPort);
						}
					}
				} else {
				}
				if (("DLV".equals(scannedMailDetailsVO.getProcessPoint())
						|| "Y".equals(scannedMailbagVO.getDeliveredFlag())) && scannedMailbagVO.getDestination() != null
						&& scannedMailDetailsVO.getAirportCode() != null
						&& (!(scannedMailbagVO.getDestination().equals(scannedMailDetailsVO.getAirportCode())))
						&& isCoterminusConfigured != null && "Y".equals(isCoterminusConfigured)) {
					coTerminusDelivery = new MailController().validateCoterminusairports(
							scannedMailbagVO.getDestination(), scannedMailDetailsVO.getAirportCode(),
							MailConstantsVO.RESDIT_DELIVERED, poaCode, dspDate);
					if (!(coTerminusDelivery || isCommonCityForAirport(scannedMailDetailsVO.getCompanyCode(),
							scannedMailDetailsVO.getAirportCode(), scannedMailbagVO.getDestination()))) {
						scannedMailDetailsVO = constructAndroidException(MailConstantsVO.INVALID_DELIVERY_PORT,
								MailHHTBusniessException.INVALID_DELIVERY_EXCEPTION, scannedMailDetailsVO);
					}
				}
			}
		}
		log.debug("MailUploadController" + " : " + "doSpecificValidations" + " Exiting");
		return scannedMailDetailsVO;
	}

	/** 
	* @param errorCode
	* @param errorDescriptionForHHT
	* @param scannedMailDetailsVO
	* @throws MailMLDBusniessException
	* @throws MailHHTBusniessException
	*/
	public ScannedMailDetailsVO constructAndroidException(String errorCode, String errorDescriptionForHHT,
			ScannedMailDetailsVO scannedMailDetailsVO) throws MailMLDBusniessException, MailHHTBusniessException {
		if (errorDescriptionForHHT == null) {
			errorDescriptionForHHT = "Exception";
		}
		if (scannedMailDetailsVO != null) {
			MailbagVO mailbagVO = new MailbagVO();
			mailbagVO.setErrorCode(errorCode);
			mailbagVO.setErrorType("Warning");
			if (MailConstantsVO.FLAG_YES.equals(scannedMailDetailsVO.getContainerAsSuchArrOrDlvFlag())) {
				mailbagVO.setErrorType("E");
			} else if (MailHHTBusniessException.INVALID_OFFICEOFEXCHANGE.equals(errorCode)) {
				mailbagVO.setErrorType("Error");
			} else if (MailConstantsVO.INVALID_DELIVERY_PORT.equals(errorCode)) {
				mailbagVO.setErrorType("Error");
			}
			mailbagVO.setErrorDescription(errorDescriptionForHHT);
			scannedMailDetailsVO.getErrorMailDetails().add(mailbagVO);
		}
		return scannedMailDetailsVO;
	}

	/** 
	* @author A-8353 for IASCB-45360
	* @param scannedMailDetailsVO
	* @throws SystemException 
	* @throws MailHHTBusniessException 
	* @throws MailMLDBusniessException 
	* @throws ForceAcceptanceException 
	*/
	public void flagResditForAcceptanceInTruck(ScannedMailDetailsVO scannedMailDetailsVO)
			throws MailMLDBusniessException, MailHHTBusniessException, ForceAcceptanceException {
		LoginProfile logonAttributes = null;
		try {
			logonAttributes = contextUtil.callerLoginProfile();
		} finally {
		}
		scannedMailDetailsVO.setProcessPoint(MailConstantsVO.MAIL_STATUS_ACCEPTED);
		scannedMailDetailsVO.getMailDetails().iterator().next().setIsFromTruck(null);
		MailAcceptanceVO mailAcceptanceVO = null;
		mailAcceptanceVO = makeMailAcceptanceVO(scannedMailDetailsVO, logonAttributes);
		boolean hasFlightDeparted = false;
		if (mailAcceptanceVO.getFlightSequenceNumber() != DESTN_FLT) {
			if (mailAcceptanceVO.getFlightStatus() == null) {
				hasFlightDeparted = new MailAcceptance().checkForDepartedFlight(mailAcceptanceVO);
			} else if (MailConstantsVO.FLIGHT_STATUS_DEPARTED.equals(mailAcceptanceVO.getFlightStatus())) {
				hasFlightDeparted = true;
			}
		}
		new MailAcceptance().flagResditsForAcceptance(mailAcceptanceVO, scannedMailDetailsVO.getMailDetails(),
				hasFlightDeparted);
	}
}
