package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.mailinbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.flight.operation.vo.FlightValidationVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailArrivalVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.OffloadVO;
import com.ibsplc.icargo.business.mail.operations.vo.OperationalFlightVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailBagDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailInboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.mailinbound.MailinboundModel;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.persistence.query.Page;
import com.ibsplc.xibase.server.framework.vo.ErrorDisplayType;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;
/**
 * 
 * @author 215166
 *
 */
public class RemoveMailbagCommand extends AbstractCommand {

	private Log log = LogFactory.getLogger("MAIL OPERATIONS RemoveMailbagCommand");

	public void execute(ActionContext actionContext) throws BusinessDelegateException {

		this.log.entering("RemoveMailbagCommand", "execute");
		MailinboundModel mailinboundModel = (MailinboundModel) actionContext.getScreenModel();
		List<ErrorVO> errors = new ArrayList<ErrorVO>();
		Collection<FlightValidationVO> flightValidationVOs = null;
		MailTrackingDefaultsDelegate mailTrackingDefaultsDelegate = new MailTrackingDefaultsDelegate();
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		ContainerDetails containerDetails = mailinboundModel.getContainerDetail();
		LocalDate flightAta = null;
		try {
			FlightFilterVO flightFilterVO = null;
			flightFilterVO = handleFlightFilterVO(mailinboundModel);
			log.log(Log.FINE, "FlightFilterVO ------------> ", flightFilterVO);
			flightValidationVOs = mailTrackingDefaultsDelegate.validateFlight(flightFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		FlightValidationVO inbflightVO = null;
		// Validating the flight to find out the Actual time of Arrival
		if (flightValidationVOs != null && !flightValidationVOs.isEmpty()) {
			inbflightVO = flightValidationVOs.iterator().next();
			flightAta = inbflightVO.getAta();
		}
		if (flightAta == null) {
			ErrorVO err = new ErrorVO("mail.operations.ux.inbound.err.flightnotarrived");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			errors.add(err);
		}
		if (errors != null && !errors.isEmpty()) {
			actionContext.addAllError(errors);
			return;
		}
		if (mailinboundModel.getRemoveReason() == null || mailinboundModel.getRemoveReason().trim().length() < 1) {
			ErrorVO err = new ErrorVO("mail.operations.ux.inbound.err.reasoncodeandremarksmanadatory");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(err);
			return;
		}
		if (mailinboundModel.getRemoveRemarks() == null || mailinboundModel.getRemoveRemarks().trim().length() < 1) {
			ErrorVO err = new ErrorVO("mail.operations.ux.inbound.err.reasoncodeandremarksmanadatory");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(err);
			return;
		}
		LocalDate curentDate = new LocalDate(logonAttributes.getAirportCode(), Location.ARP, true);
		HashMap<String, MailbagVO> mailDetailsMap = new HashMap<String, MailbagVO>();
		List<MailbagVO> selectedMailBagVos = new ArrayList<>();
		MailinboundDetails mailinboundDetails = (MailinboundDetails) mailinboundModel.getMailinboundDetails();
		OperationalFlightVO operationalFlightVO = null;
		operationalFlightVO = MailInboundModelConverter.constructOperationalFlightVo(mailinboundDetails,
				logonAttributes);
		MailArrivalVO mailArrivalVO = null;
		try {
			mailArrivalVO = new MailTrackingDefaultsDelegate().populateMailArrivalVOForInbound(operationalFlightVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		Collection<ContainerDetailsVO> containerDetailsVos = null;
		if (null != mailArrivalVO) {
			containerDetailsVos = mailArrivalVO.getContainerDetails();
		}
		ContainerDetailsVO containerDetailsVO = null;
		for (ContainerDetailsVO containerDetailsVoIterate : containerDetailsVos) {
			if (containerDetailsVoIterate.getContainerNumber().equals(containerDetails.getContainerno())) {
				containerDetailsVO = containerDetailsVoIterate;
				break;
			}
		}

		if (containerDetailsVO.getContainerNumber() == null
				|| containerDetailsVO.getContainerNumber().trim().length() == 0) {
			log.log(Log.INFO, "container not exists  1-----==-----> ");
			ErrorVO err = new ErrorVO("mail.operations.ux.inbond.containernotexists");
			err.setErrorDisplayType(ErrorDisplayType.ERROR);
			actionContext.addError(err);
			return;
		}

		OffloadVO offloadVO = null;
		if (MailConstantsVO.OFFLOAD_MAILBAG.equals(mailinboundModel.getRemoveType())) {
			offloadVO = new OffloadVO();
			offloadVO.setDepartureOverride(true);
			offloadVO.setCompanyCode(logonAttributes.getCompanyCode());
			offloadVO.setUserCode(logonAttributes.getUserId());
			offloadVO.setFltClosureChkNotReq(true);
			offloadVO.setRemove(true);
			offloadVO.setCarrierCode(inbflightVO.getCarrierCode());
			offloadVO.setCarrierId(inbflightVO.getFlightCarrierId());
			offloadVO.setFlightDate(inbflightVO.getFlightDate());
			offloadVO.setFlightNumber(inbflightVO.getFlightNumber());
			offloadVO.setFlightSequenceNumber(inbflightVO.getFlightSequenceNumber());
			offloadVO.setLegSerialNumber(inbflightVO.getLegSerialNumber());
			offloadVO.setPol(inbflightVO.getLegOrigin());
			offloadVO.setOffloadMailbags(new Page<MailbagVO>(new ArrayList<MailbagVO>(), 0, 0, 0, 0, 0, false));
			mailDetailsMap.clear();
			for (MailbagVO mailbagVO : containerDetailsVO.getMailDetails()) {
				String mailKey = mailbagVO.getMailSequenceNumber() + "_" + mailbagVO.getMailbagId();
				mailDetailsMap.put(mailKey, mailbagVO);
			}
			for (MailBagDetails mailBagDetails : containerDetails.getMailBagDetailsCollection()) {
				if (mailDetailsMap
						.containsKey(mailBagDetails.getMailSequenceNumber() + "_" + mailBagDetails.getMailBagId())) {
					MailbagVO mailbagVO = mailDetailsMap
							.get(mailBagDetails.getMailSequenceNumber() + "_" + mailBagDetails.getMailBagId());
					if (mailbagVO != null) {
						mailbagVO.setIsoffload(true);
						mailbagVO.setRemove(true);
						mailbagVO.setOffloadedReason(mailinboundModel.getRemoveReason());
						mailbagVO.setOffloadedDescription(mailinboundModel.getRemoveReason());
						mailbagVO.setOffloadedRemarks(mailinboundModel.getRemoveRemarks());
						mailbagVO.setScannedUser(logonAttributes.getUserId());
						mailbagVO.setScannedDate(curentDate);
						mailbagVO.setScannedPort(logonAttributes.getAirportCode()); 
						mailbagVO.setMailSource("MTK064");
						mailbagVO.setTriggerForReImport(MailConstantsVO.MAIL_STATUS_OFFLOADED);
						if(MailConstantsVO.FLAG_YES.equals(mailbagVO.getPaBuiltFlag())){
							mailbagVO.setPaContainerNumberUpdate(true);
							mailbagVO.setAcceptancePostalContainerNumber(null);
						}
						mailbagVO.setLastUpdateTime(new LocalDate(LocalDate.NO_STATION, Location.NONE, true));
						mailbagVO.setLastUpdateUser(logonAttributes.getUserId());
						selectedMailBagVos.add(mailbagVO);
					}
				}
			}
			offloadVO.getOffloadMailbags().addAll(selectedMailBagVos);
			offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_MAILBAG);
		} else if (MailConstantsVO.OFFLOAD_CONTAINER.equals(mailinboundModel.getRemoveType())) {
			if (MailConstantsVO.BULK_TYPE.equals(containerDetailsVO.getContainerType())) {
				ErrorVO err = new ErrorVO("mail.operations.ux.inbound.err.bulkcannotremove");
				err.setErrorDisplayType(ErrorDisplayType.ERROR);
				actionContext.addError(err);
				return;
			}
			try {
				offloadVO = findMailOffloadDetails(containerDetailsVO);
			} catch (SystemException e) {
				log.log(Log.INFO, "container not exists  2-----==-----> ",e);
				actionContext.addError(new ErrorVO("mail.operations.ux.inbond.containernotexists"));
				return;
			}
			if (offloadVO != null) {
				offloadVO.setCarrierCode(inbflightVO.getCarrierCode());
				offloadVO.setFlightDate(inbflightVO.getFlightDate());
				offloadVO.setDepartureOverride(true);
				offloadVO.setUserCode(logonAttributes.getUserId());
				offloadVO.setFltClosureChkNotReq(true);
				offloadVO.setRemove(true);
				offloadVO.setOffloadType(MailConstantsVO.OFFLOAD_CONTAINER);
				offloadVO.setOffloadMailbags(null);
				if (offloadVO.getOffloadContainers() != null) {
					for (ContainerVO container : offloadVO.getOffloadContainers()) {
						container.setOffload(true);
						container.setOffloadedReason(mailinboundModel.getRemoveReason());
						container.setOffloadedRemarks(mailinboundModel.getRemoveRemarks());
						container.setLastUpdateUser(logonAttributes.getUserId());
						container.setSource("MTK064");
						container.setRemove(true);
					}
				}
			} else {
				log.log(Log.INFO, "container not exists  3-----==-----> ");
				actionContext.addError(new ErrorVO("mail.operations.ux.inbond.containernotexists"));
				return;
			}
		}
		try {
			mailTrackingDefaultsDelegate.removeFromInbound(offloadVO);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		ResponseVO responseVO = new ResponseVO();
		ArrayList<MailinboundModel> result = new ArrayList<MailinboundModel>();
		result.add(mailinboundModel);
		responseVO.setResults(result);
		if (errors != null && !errors.isEmpty()) {
			actionContext.addAllError(errors);
		} else {
			responseVO.setStatus("success");
		}
		actionContext.setResponseVO(responseVO);
	}

	private OffloadVO findMailOffloadDetails(ContainerDetailsVO containerDetailsVO) throws SystemException {
		OffloadVO mailOffloadVO = null;
		OffloadFilterVO mailOffloadFilterVO = populateMailOffloadFilterVO(containerDetailsVO);
		try {
			mailOffloadVO = new MailTrackingDefaultsDelegate().findOffloadDetails(mailOffloadFilterVO);
		} catch (BusinessDelegateException businessDelegateException) {
			log.log(Log.SEVERE, "findOffloadDetailsg-----==-----> ", businessDelegateException);
		}
		return mailOffloadVO;
	}

	private OffloadFilterVO populateMailOffloadFilterVO(ContainerDetailsVO containerDetailsVO) {
		OffloadFilterVO mailOffloadFilterVO = new OffloadFilterVO();
		mailOffloadFilterVO.setCompanyCode(containerDetailsVO.getCompanyCode());
		mailOffloadFilterVO.setFlightNumber(containerDetailsVO.getFlightNumber());
		mailOffloadFilterVO.setFlightDate(containerDetailsVO.getFlightDate());
		mailOffloadFilterVO.setFlightSequenceNumber(containerDetailsVO.getFlightSequenceNumber());
		mailOffloadFilterVO.setLegSerialNumber(containerDetailsVO.getLegSerialNumber());
		mailOffloadFilterVO.setPol(containerDetailsVO.getPol());
		mailOffloadFilterVO.setCarrierCode(containerDetailsVO.getCarrierCode());
		mailOffloadFilterVO.setCarrierId(containerDetailsVO.getCarrierId());
		mailOffloadFilterVO.setOffloadType(MailConstantsVO.OFFLOAD_CONTAINER);
		mailOffloadFilterVO.setContainerType(MailConstantsVO.ULD_TYPE);
		mailOffloadFilterVO.setContainerNumber(containerDetailsVO.getContainerNumber());
		mailOffloadFilterVO.setRemove(true);
		return mailOffloadFilterVO;
	}

	private FlightFilterVO handleFlightFilterVO(MailinboundModel mailinboundModel) {
		LogonAttributes logonAttributes = (LogonAttributes) getLogonAttribute();
		MailinboundDetails mailinboundDetails = mailinboundModel.getMailinboundDetails();
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(logonAttributes.getCompanyCode());
		flightFilterVO.setFlightNumber(mailinboundDetails.getFlightNo());
		flightFilterVO.setStation(mailinboundDetails.getPort());
		flightFilterVO.setDirection(MailConstantsVO.OPERATION_INBOUND);
		flightFilterVO.setActiveAlone(false);
		flightFilterVO.setFlightDate((new LocalDate(LocalDate.NO_STATION, Location.NONE, false)
				.setDate(mailinboundDetails.getFlightDate().split(" ")[0])));
		flightFilterVO.setFlightSequenceNumber(Long.parseLong(mailinboundDetails.getFlightSeqNumber()));
		flightFilterVO.setFlightCarrierId(Integer.parseInt(mailinboundDetails.getCarrierId()));
		return flightFilterVO;
	}
}
