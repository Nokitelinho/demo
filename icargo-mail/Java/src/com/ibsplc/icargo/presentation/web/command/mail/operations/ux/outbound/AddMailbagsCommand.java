package com.ibsplc.icargo.presentation.web.command.mail.operations.ux.outbound;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.ibsplc.icargo.business.mail.operations.vo.ContainerDetailsVO;
import com.ibsplc.icargo.business.mail.operations.vo.ContainerVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailAcceptanceVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailConstantsVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailbagVO;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.icargo.framework.web.command.CommandInvocationException;
import com.ibsplc.icargo.framework.web.json.vo.ResponseVO;
import com.ibsplc.icargo.framework.web.spring.command.AbstractCommand;
import com.ibsplc.icargo.framework.web.spring.controller.ActionContext;
import com.ibsplc.icargo.presentation.delegate.mail.operations.MailTrackingDefaultsDelegate;
import com.ibsplc.icargo.presentation.web.model.mail.operations.OutboundModel;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.ContainerDetails;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailAcceptance;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.MailOutboundModelConverter;
import com.ibsplc.icargo.presentation.web.model.mail.operations.common.Mailbag;
import com.ibsplc.xibase.client.framework.delegate.BusinessDelegateException;
import com.ibsplc.xibase.server.framework.vo.ErrorVO;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

public class AddMailbagsCommand extends AbstractCommand {
	private Log log = LogFactory.getLogger("OPERATIONS MAIL OUTBOUND NEO");
	private static final String PREASSIGNMENT_SYS = "mailtracking.defaults.acceptance.preassignmentneeded";
	private static final String INVENTORYENABLED_SYS = "mailtracking.defaults.inventoryenabled";
	private static final String MAIL_COMMODITY_SYS = "mailtracking.defaults.booking.commodity";

	@Override
	public void execute(ActionContext actionContext)
			throws BusinessDelegateException, CommandInvocationException {
		this.log.entering("AddMailbagsCommand", "execute");
		LogonAttributes logonAttributes = getLogonAttribute();
		Collection<ErrorVO> errors = new ArrayList<ErrorVO>();
		String cmpcod = logonAttributes.getCompanyCode();
		OutboundModel outboundModel = (OutboundModel) actionContext
				.getScreenModel();
		Collection<ContainerDetailsVO> containerDetailslistVO = new ArrayList<ContainerDetailsVO>();
		// DefaultScreenSessionImpl screenSession= getScreenSession();
		Map<String, String> paramResults = null;
		Collection<String> codes = new ArrayList<String>();
		codes.add(PREASSIGNMENT_SYS);
		codes.add(INVENTORYENABLED_SYS);
		codes.add(MAIL_COMMODITY_SYS);

		
		this.log.log(
				5,
				new Object[] { "LoginAirport ---> ",
						logonAttributes.getAirportCode() });
		ContainerVO containerVO = new ContainerVO();
		ContainerVO container = new ContainerVO();
		Collection<MailbagVO> newMailbagVOs = new ArrayList<MailbagVO>();
		Collection<Mailbag> newMailbags = null;
		ContainerDetails containerInfo = outboundModel.getContainerInfo();
		MailAcceptance mailAcceptance = outboundModel.getMailAcceptance();
		if (outboundModel.getMailbags() != null) {
			newMailbags = outboundModel.getMailbags();
		}
		MailAcceptanceVO mailacceptanceVO = MailOutboundModelConverter
				.constructMailAcceptanceVO(mailAcceptance, logonAttributes);
		ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		containerDetailsVO = MailOutboundModelConverter
				.constructContainerDetailsVO(containerInfo,logonAttributes);
		containerDetailsVO.setOperationFlag("U");
		for (Mailbag newMailbag : newMailbags) {
			if(newMailbag.getOoe()!=null) {
			MailbagVO newMailbagVO = new MailbagVO();
		//	String wt = weightFormatter(newMailbag.getWeight());
			newMailbagVO.setOoe(newMailbag.getOoe());
			newMailbagVO.setDoe(newMailbag.getDoe());
			newMailbagVO.setMailCategoryCode(newMailbag.getMailCategoryCode());
			newMailbagVO.setMailSubclass(newMailbag.getMailSubclass());
			newMailbagVO.setMailClass(newMailbag.getMailClass());
			newMailbagVO.setYear(newMailbag.getYear());
			newMailbagVO.setDespatchSerialNumber(newMailbag
					.getDespatchSerialNumber());
			newMailbagVO.setReceptacleSerialNumber(newMailbag
					.getReceptacleSerialNumber());
			newMailbagVO.setHighestNumberedReceptacle(newMailbag
					.getHighestNumberedReceptacle());
			newMailbagVO.setRegisteredOrInsuredIndicator(newMailbag
					.getRegisteredOrInsuredIndicator());
			// newMailbagVO.setSubClassDesc(newMails.getS)
			newMailbagVO.setWeight(new Measure(UnitConstants.MAIL_WGT,0.0,
					Double.parseDouble(newMailbag.getMailbagWeight()),newMailbag.getDisplayUnit()));
		//	newMailbagVO.setVolume(newMailbag.getVolume());
			LocalDate sd = new LocalDate(logonAttributes.getAirportCode(),
					Location.ARP, true);
			//sd.setDate(newMailbag.getScannedDate());
			//newMailbagVO.setScannedDate(sd);
			newMailbagVO.setSealNumber(newMailbag.getSealNumber());
			//newMailbagVO.setArrivalSealNumber(newMailbag.getArrivalSealNumber());
			newMailbagVO.setDamageFlag(newMailbag.getDamageFlag());
			newMailbagVO.setMailbagId(newMailbag.getMailbagId());
			newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			newMailbagVO.setContainerNumber(containerDetailsVO
					.getContainerNumber());
			newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			newMailbagVO.setScannedUser(logonAttributes.getUserId()
					.toUpperCase());
			newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			newMailbagVO
					.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			newMailbagVO.setFlightNumber(containerDetailsVO.getFlightNumber());
			newMailbagVO.setDeliveredFlag("N");
			newMailbagVO.setFlightSequenceNumber(containerDetailsVO
					.getFlightSequenceNumber());
			newMailbagVO.setSegmentSerialNumber(containerDetailsVO
					.getSegmentSerialNumber());
			newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			newMailbagVO
					.setContainerType(containerDetailsVO.getContainerType());
			newMailbagVO.setPou(containerDetailsVO.getPou());
			newMailbagVO.setOperationalFlag("I");

			String dsnId = new StringBuilder().append(newMailbagVO.getOoe())
					.append(newMailbagVO.getDoe())
					.append(newMailbagVO.getMailCategoryCode())
					.append(newMailbagVO.getMailSubclass())
					.append(newMailbagVO.getYear())
					.append(newMailbagVO.getDespatchSerialNumber()).toString();
																	// for
																	// ICRD-205027
			StringBuilder mailTagId=new StringBuilder();
			mailTagId= mailTagId.append(newMailbagVO.getOoe())
			 .append(newMailbagVO.getDoe())
			 .append(newMailbagVO.getMailCategoryCode())
			 .append(newMailbagVO.getMailSubclass()).append(newMailbagVO.getYear())
			 .append(newMailbagVO.getDespatchSerialNumber()).append(newMailbagVO.getReceptacleSerialNumber())
			 .append(newMailbagVO.getHighestNumberedReceptacle()).append(newMailbagVO.getRegisteredOrInsuredIndicator());
			// .append(wt);
			newMailbagVO.setDespatchId(dsnId);
			newMailbagVO.setMailbagId(mailTagId.toString());
			newMailbagVO.setCompanyCode(logonAttributes.getCompanyCode());
			newMailbagVO.setContainerNumber(containerDetailsVO
					.getContainerNumber());
			newMailbagVO
					.setCarrierCode(mailacceptanceVO.getFlightCarrierCode());
			newMailbagVO.setFlightDate(mailacceptanceVO.getFlightDate());
			newMailbagVO.setScannedPort(logonAttributes.getAirportCode());
			/*if (MailConstantsVO.FLAG_YES.equals(newMailbag.getArrivalSealNumber())) {
				// No need to update
			} else {
				newMailbagVO.setArrivedFlag("N");
			}*/
			newMailbagVO.setScannedUser(logonAttributes.getUserId()
					.toUpperCase());
			newMailbagVO.setLatestStatus(MailConstantsVO.MAIL_STATUS_ACCEPTED);
			newMailbagVO
					.setOperationalStatus(MailConstantsVO.OPERATION_OUTBOUND);
			newMailbagVO.setCarrierId(containerDetailsVO.getCarrierId());
			newMailbagVO.setFlightNumber(mailacceptanceVO.getFlightNumber());
			newMailbagVO.setFlightSequenceNumber(mailacceptanceVO
					.getFlightSequenceNumber());
			newMailbagVO.setSegmentSerialNumber(containerDetailsVO
					.getSegmentSerialNumber());
			newMailbagVO.setUldNumber(containerDetailsVO.getContainerNumber());
			newMailbagVO
					.setContainerType(containerDetailsVO.getContainerType());
			newMailbagVO.setPou(containerDetailsVO.getPou());
			newMailbagVO.setDisplayUnit(newMailbag.getDisplayUnit());
			newMailbagVOs.add(newMailbagVO);
		}
		}

		containerDetailsVO.setMailDetails(newMailbagVOs);

		Collection<MailbagVO> mailbgVOs = containerDetailsVO.getMailDetails();
		Collection<MailbagVO> newMailbgVOs = new ArrayList<MailbagVO>();
		if (mailbgVOs != null && mailbgVOs.size() > 0) {
			for (MailbagVO mailbagVO : mailbgVOs) {
				if ("I".equals(mailbagVO.getOperationalFlag())
						|| "U".equals(mailbagVO.getOperationalFlag())) {
					newMailbgVOs.add(mailbagVO);
				}
			}
		}
		try {
			new MailTrackingDefaultsDelegate().validateMailBags(newMailbgVOs);
		} catch (BusinessDelegateException businessDelegateException) {
			errors = handleDelegateException(businessDelegateException);
		}
		if (errors.size() > 0) {
			actionContext.addError(new ErrorVO("Invalid Mailbags"));
			return;
		}

		containerDetailsVO.setLegSerialNumber(mailacceptanceVO
				.getLegSerialNumber());
		containerDetailsVO.setDestination(containerDetailsVO.getDestination());
		// containerDetailsVO.setAssignmentFlag("F");
		int flag = 0;
		containerDetailslistVO.add(containerDetailsVO);

		String assignedto = outboundModel.getFlightCarrierflag();
		log.log(Log.FINE, "ASSIGNED TO ------------> ", assignedto);

		// MailAcceptanceVO mailAcceptanceVO = new MailAcceptanceVO();
		if ("F".equals(assignedto)) {

			mailacceptanceVO.setContainerDetails(containerDetailslistVO);

		}/*
		 * else{ AirlineValidationVO airlineValidationVO = null; String
		 * carrierCode =
		 * mailbagEnquiryModel.getCarrierCode().trim().toUpperCase(); if
		 * (carrierCode != null && !"".equals(carrierCode)) { try {
		 * airlineValidationVO = new AirlineDelegate().validateAlphaCode(
		 * logonAttributes.getCompanyCode(),carrierCode); }catch
		 * (BusinessDelegateException businessDelegateException) {
		 * handleDelegateException(businessDelegateException); } }
		 * mailAcceptanceVO
		 * .setFlightCarrierCode(airlineValidationVO.getAlphaCode());
		 * mailAcceptanceVO
		 * .setCarrierId(airlineValidationVO.getAirlineIdentifier());
		 * mailAcceptanceVO.setFlightNumber("-1");
		 * mailAcceptanceVO.setFlightSequenceNumber(-1);
		 * mailAcceptanceVO.setLegSerialNumber(-1);
		 * Collection<ContainerDetailsVO> containers = new
		 * ArrayList<ContainerDetailsVO>();
		 * 
		 * for (ContainerVO vo : selectedContainerVOs) {
		 * 
		 * ContainerDetailsVO containerDetailsVO = new ContainerDetailsVO();
		 * mailAcceptanceVO
		 * .setOwnAirlineCode(logonAttributes.getOwnAirlineCode());
		 * mailAcceptanceVO
		 * .setOwnAirlineId(logonAttributes.getOwnAirlineIdentifier());
		 * mailAcceptanceVO
		 * .setAcceptedUser(logonAttributes.getUserId().toUpperCase());
		 * mailAcceptanceVO.setCompanyCode(logonAttributes.getCompanyCode());
		 * mailAcceptanceVO.setPol(logonAttributes.getAirportCode());
		 * mailAcceptanceVO.setPreassignNeeded(false);
		 * 
		 * containerDetailsVO.setCompanyCode(vo.getCompanyCode());
		 * containerDetailsVO.setContainerNumber(vo.getContainerNumber());
		 * containerDetailsVO.setContainerType(vo.getType()); if
		 * (vo.isReassignFlag()) { containerDetailsVO.setReassignFlag(true);
		 * containerDetailsVO.setCarrierId(vo.getCarrierId());
		 * containerDetailsVO.setCarrierCode(vo.getCarrierCode());
		 * containerDetailsVO.setFlightNumber(vo.getFlightNumber());
		 * containerDetailsVO.setFlightDate(vo.getFlightDate());
		 * containerDetailsVO
		 * .setFlightSequenceNumber(vo.getFlightSequenceNumber());
		 * containerDetailsVO.setLegSerialNumber(vo.getLegSerialNumber());
		 * containerDetailsVO
		 * .setSegmentSerialNumber(vo.getSegmentSerialNumber()); } else {
		 * containerDetailsVO
		 * .setCarrierId(airlineValidationVO.getAirlineIdentifier());
		 * containerDetailsVO
		 * .setCarrierCode(airlineValidationVO.getAlphaCode());
		 * containerDetailsVO.setFlightNumber("-1");
		 * containerDetailsVO.setFlightSequenceNumber(-1);
		 * containerDetailsVO.setLegSerialNumber(-1);
		 * containerDetailsVO.setSegmentSerialNumber(-1);
		 * containerDetailsVO.setFlightDate(null); }
		 * containerDetailsVO.setAcceptedFlag("Y");
		 * containerDetailsVO.setArrivedStatus("N");
		 * containerDetailsVO.setTransactionCode
		 * (MailConstantsVO.MAIL_TXNCOD_ASG);
		 * if("Y".equals(vo.getAcceptanceFlag())){
		 * containerDetailsVO.setOperationFlag("U");
		 * containerDetailsVO.setContainerOperationFlag("U"); } else{
		 * containerDetailsVO.setOperationFlag("I");
		 * containerDetailsVO.setContainerOperationFlag("I");
		 * containerDetailsVO.setAssignmentDate(new
		 * LocalDate(logonAttributes.getAirportCode(),Location.ARP,true)); }
		 * containerDetailsVO.setPol(logonAttributes.getAirportCode());
		 * containerDetailsVO.setPou(vo.getPou());
		 * containerDetailsVO.setOwnAirlineCode
		 * (logonAttributes.getOwnAirlineCode());
		 * containerDetailsVO.setAssignedUser(logonAttributes.getUserId());
		 * containerDetailsVO.setDestination(vo.getFinalDestination());
		 * containerDetailsVO.setPaBuiltFlag(vo.getPaBuiltFlag());
		 * containerDetailsVO.setPaCode(vo.getShipperBuiltCode());
		 * containerDetailsVO.setWareHouse(vo.getWarehouseCode());
		 * containers.add(containerDetailsVO);
		 * 
		 * vo.setCompanyCode(logonAttributes.getCompanyCode());
		 * vo.setCarrierCode(airlineValidationVO.getAlphaCode());
		 * vo.setCarrierId(airlineValidationVO.getAirlineIdentifier());
		 * vo.setFlightSequenceNumber(-1); vo.setSegmentSerialNumber(-1);
		 * vo.setLegSerialNumber(-1); }
		 * mailAcceptanceVO.setContainerDetails(containers);
		 * 
		 * }
		 */

		try {
			new MailTrackingDefaultsDelegate()
					.saveAcceptanceDetails(mailacceptanceVO);

		} catch (BusinessDelegateException businessDelegateException) {
			handleDelegateException(businessDelegateException);
		}

		// //////////////////////////Meera ends////////////////////////

		/*
		 * selContainerDtlsVO.setOperationFlag("U");
		 * selContainerDtlsVO.setContainerOperationFlag("U");
		 * selContainerDtlsVO.setCompanyCode(logonAttributes.getCompanyCode());
		 * selContainerDtlsVO.setPol(logonAttributes.getAirportCode());
		 * selContainerDtlsVO.setPou(newContainer.getPou());
		 * selContainerDtlsVO.setDestination(newContainer.getDestination());
		 * selContainerDtlsVO.setPaBuiltFlag(newContainer.getPaBuiltFlag());
		 * selContainerDtlsVO
		 * .setContainerJnyId(newContainer.getContainerJnyId());
		 * selContainerDtlsVO.setPaCode(newContainer.getPaCode());
		 * selContainerDtlsVO.setContainerType("U");
		 * selContainerDtlsVO.setContainerNumber
		 * (newContainer.getContainerNumber());
		 * selContainerDtlsVO.setPreassignFlag(newContainer.isPreassignFlag());
		 * selContainerDtlsVO.setAssignmentDate(new
		 * LocalDate(logonAttributes.getAirportCode(),Location.ARP,true));
		 * selContainerDtlsVO
		 * .setAssignedUser(logonAttributes.getUserId().toUpperCase());
		 * selContainerDtlsVO.setCarrierId(mailFlight.getCarrierId());
		 * selContainerDtlsVO.setFlightNumber(mailFlight.getFlightNumber());
		 * selContainerDtlsVO
		 * .setFlightSequenceNumber(mailFlight.getFlightSequenceNumber());
		 * 
		 * Collection<ContainerDetailsVO> contDetailVOs = new
		 * ArrayList<ContainerDetailsVO>(); Collection<ContainerDetailsVO>
		 * newContainerDetailsVOs = null;
		 * if(selContainerDtlsVO.getMailDetails()==null &&
		 * selContainerDtlsVO.getDesptachDetailsVOs()== null){
		 * //if(selContainerDtlsVO.getOperationFlag()== null ||
		 * ("U").equals(selContainerDtlsVO.getOperationFlag())){
		 * contDetailVOs.add(selContainerDtlsVO); try { newContainerDetailsVOs
		 * =new
		 * MailTrackingDefaultsDelegate().findMailbagsInContainer(contDetailVOs
		 * ); } catch (BusinessDelegateException businessDelegateException){
		 * errors = handleDelegateException(businessDelegateException); } }
		 * List<ContainerDetail> newContainerModel=
		 * MailOutboundModelConverter.constructContainerDetails
		 * (newContainerDetailsVOs); FlightValidationVO
		 * flightValidatonVO=screenSession.getAttribute("flightValidationVO");
		 * if(flightValidatonVO != null) { String route =
		 * flightValidatonVO.getFlightRoute(); if(route != null &&
		 * !"".equals(route)){ String[] routeArr = route.split("-"); int flag =
		 * 0; Collection<String> pous = new ArrayList<String>(); for(int
		 * i=0;i<routeArr.length;i++){ if(flag == 1){ pous.add(routeArr[i]); }
		 * if(routeArr[i].equals(logonAttributes.getAirportCode())){ flag = 1; }
		 * } pous.remove(logonAttributes.getAirportCode());
		 * outboundModel.getMailFlight().setPouList(pous);
		 * outboundModel.setPouList(pous); } }else{
		 * 
		 * }
		 */
		outboundModel.setAirportCode(logonAttributes.getAirportCode());
		// outboundModel.setNewContainersDetails(newContainerModel);
		ResponseVO responseVO = new ResponseVO();
		List<OutboundModel> results = new ArrayList();
		results.add(outboundModel);
		responseVO.setResults(results);
		actionContext.setResponseVO(responseVO);
		this.log.exiting("ScreenLoadCommand", "execute");

	}

	private Collection<String> getOneTimeParameterTypes() {
		Collection<String> parameterTypes = new ArrayList();
		parameterTypes.add("mailtracking.defaults.registeredorinsuredcode");
		parameterTypes.add("mailtracking.defaults.mailcategory");
		parameterTypes.add("mailtracking.defaults.highestnumbermail");
		parameterTypes.add("mailtracking.defaults.mailclass");
		parameterTypes.add("mailtracking.defaults.return.reasoncode");
		parameterTypes.add("mailtracking.defaults.companycode");
		parameterTypes.add("mailtracking.defaults.containertype");
		return parameterTypes;
	}
	
	private String weightFormatter(Double weight) {
    	String weightString = String.valueOf(weight);
        String weights[] = weightString.split("[.]");
        StringBuilder flatWeight = new StringBuilder(weights[0]);
        if(!"0".equals(weights[1])){
        	flatWeight.append(weights[1]);
        }
        if (flatWeight.length() >= 4) {
            return flatWeight.substring(0, 4);
        }
		StringBuilder zeros = new StringBuilder();
		int zerosRequired = 4 - flatWeight.length();
		for(int i = 0; i < zerosRequired; i++) {
		    zeros.append("0");
		}
		return zeros.append(flatWeight).toString();
	}

}
