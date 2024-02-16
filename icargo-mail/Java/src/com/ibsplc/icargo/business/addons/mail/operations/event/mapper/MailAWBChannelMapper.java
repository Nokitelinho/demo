package com.ibsplc.icargo.business.addons.mail.operations.event.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;

import com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsFlightHandlingProxy;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.OperationsShipmentProxy;
import com.ibsplc.icargo.business.addons.mail.operations.proxy.SharedDefaultsProxy;
import com.ibsplc.icargo.business.flight.operation.vo.FlightFilterVO;
import com.ibsplc.icargo.business.mail.operations.vo.MailFlightSummaryVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingAWBVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingULDVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.ramp.vo.RampHandlingVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.BreakdownVO;
import com.ibsplc.icargo.business.operations.flthandling.cto.vo.ShipmentBreakdownVO;
import com.ibsplc.icargo.business.operations.flthandling.interline.vo.CTMVO;
import com.ibsplc.icargo.business.operations.flthandling.interline.vo.ShipmentInCTMVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ArrivalVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.OperationalFlightVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.ShipmentInUldArrivalVO;
import com.ibsplc.icargo.business.operations.flthandling.vo.UldArrivalVO;
import com.ibsplc.icargo.business.operations.shipment.cto.delivery.vo.ShipmentDeliveryVO;
import com.ibsplc.icargo.business.operations.shipment.cto.delivery.vo.ULDDeliveryVO;
import com.ibsplc.icargo.business.operations.shipment.delivery.vo.DeliveryDetailsVO;
import com.ibsplc.icargo.business.operations.shipment.delivery.vo.DeliveryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.SaveCollectionListDeliveryFeatureVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentSummaryVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentVO;
import com.ibsplc.icargo.business.shared.defaults.onetime.vo.OneTimeVO;
import com.ibsplc.icargo.framework.bean.BeanConversion;
import com.ibsplc.icargo.framework.bean.BeanConverterRegistry;
import com.ibsplc.icargo.framework.feature.Proxy;
import com.ibsplc.icargo.framework.proxy.ProxyException;
import com.ibsplc.icargo.framework.security.vo.LogonAttributes;
import com.ibsplc.icargo.framework.util.unit.Measure;
import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.xibase.server.framework.exceptions.SystemException;
import com.ibsplc.xibase.server.framework.util.ContextUtils;
import com.ibsplc.xibase.util.log.Log;
import com.ibsplc.xibase.util.log.factory.LogFactory;

/**
 * 
 * Java file :
 * com.ibsplc.icargo.business.addons.mail.operations.event.mapper.ExportManifestMailAWBChannelMapper.java
 * Version : Name : Date : Updation
 * --------------------------------------------------- 0.1 : Ashil M N :
 * 23-Sep-2021 : Draft 
 */
@BeanConverterRegistry
public class MailAWBChannelMapper {

	@Autowired
	Proxy proxy;

	private static final Log LOGGER = LogFactory.getLogger(MailAWBChannelMapper.class.getSimpleName());
	private static final String MAIL_SCC = "mailtracking.defaults.mailscccode";
	private static final String EVENT_ACP = "ACP";
	private static final String EVENT_ARR = "ARR";
	private static final String EVENT_DLV = "DLV";
	private static final String EVENT_RMPTRA = "RMPTRA";
	private static final String EVENT_TRA = "TRA";

	@BeanConversion(from = OperationalFlightVO.class, to = MailFlightSummaryVO.class, group = "SAVEMANIFEST_EVENT_EVENT")
	public MailFlightSummaryVO mapOperationalFlightVOToMailFlightSummaryVOManifestExportFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		return convertOperationalFlightVOToMailFlightSummaryVO(operationalFlightVO);
	}

	@BeanConversion(from = OperationalFlightVO.class, to = MailFlightSummaryVO.class, group = "OPERATION_FLTHANDLING_FINALISEEXPORTFLIGHT_EVENT")
	public MailFlightSummaryVO mapOperationalFlightVOToMailFlightSummaryVOFinalizeExportFlight(
			OperationalFlightVO operationalFlightVO) throws SystemException {
		return convertOperationalFlightVOToMailFlightSummaryVO(operationalFlightVO);

	}

	@BeanConversion(from = SaveCollectionListDeliveryFeatureVO.class, to = MailFlightSummaryVO.class, group = "OPERATIONS_SHIPMENT_SAVECOLLECTIONLISTDELIVERY_EVENT")
	public MailFlightSummaryVO mapSaveCollectionListDeliveryFeatureVOToMailFlightSummaryVO(
			SaveCollectionListDeliveryFeatureVO savecollectionListdeliveryVO) throws SystemException, ProxyException {
		MailFlightSummaryVO mailFlightSummaryVO = null;
		Collection<ULDDeliveryVO> uldDeliverVOs = savecollectionListdeliveryVO.getUldDeliveryDetails();
		if (Objects.nonNull(uldDeliverVOs) && !uldDeliverVOs.isEmpty()) {
			mailFlightSummaryVO = convertULDDeliveryVOsToMailFlightSummaryVO(uldDeliverVOs);
		}
		return mailFlightSummaryVO;

	}

	@BeanConversion(from = ArrivalVO.class, to = MailFlightSummaryVO.class, group = "SAVE_ULD_ARRIVAL_EVENT")
	public MailFlightSummaryVO mapArrivalVOToMailFlightSummaryVOULDArrival(ArrivalVO arrivalVO) throws SystemException {
		return getMailFlightSummaryVO(arrivalVO);
	}

	@BeanConversion(from = DeliveryVO.class, to = MailFlightSummaryVO.class, group = "SAVE_DELIVERY_DETAILS_EVENT")
	public MailFlightSummaryVO mapDeliveryVOToMailFlightSummaryVOQuickDelivery(DeliveryVO deliveryVO)
			throws SystemException, ProxyException {
		return getMailFlightSummaryVO(deliveryVO);
	}

	
	@BeanConversion(from = BreakdownVO.class, to = MailFlightSummaryVO.class, group = "SAVE_BREAKDOWN_EVENT")
	public MailFlightSummaryVO mapBreakdownVOToMailFlightSummaryVOULDBreakdown(BreakdownVO breakdownVO) throws SystemException {
		return getMailFlightSummaryVO(breakdownVO);
	}
	
	@BeanConversion(from = RampHandlingVO.class, to = MailFlightSummaryVO.class, group = "SAVE_RAMPTRANSFER_DETAILS_EVENT")
	public MailFlightSummaryVO mapRampHandlingVOToMailFlightSummaryVORampTransfer(RampHandlingVO rampHandlingVO) throws SystemException {
		return convertRampHandlingVOToMailFlightSummaryVO(rampHandlingVO);
	}
	@BeanConversion(from = CTMVO.class, to = MailFlightSummaryVO.class, group = "SAVE_CTM_DETAILS_EVENT")
	public MailFlightSummaryVO mapCTMVOToMailFlightSummaryVOSaveCTM(CTMVO cTMVO) throws SystemException {
		return convertCTMVOToMailFlightSummaryVO(cTMVO);
	}
	
	@BeanConversion(from = CTMVO.class, to = MailFlightSummaryVO.class, group = "REJECT_CTM_SHIPMENT_EVENT")
	public MailFlightSummaryVO mapCTMVOToMailFlightSummaryVORejectCTM(CTMVO cTMVO) throws SystemException {
		return convertCTMVOToMailFlightSummaryVO(cTMVO);
	}
	
	private MailFlightSummaryVO getMailFlightSummaryVO(BreakdownVO breakdownVO) throws SystemException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);

		Collection<ShipmentBreakdownVO> shipmentBreakdownVOs = breakdownVO.getBreakdownDetails();

		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();

		getShipmentSummaryVOs(oneTimeValues, shipmentBreakdownVOs, shipmentSummaryVOsToMail);

		return convertToMailFlightSummaryVOForULDBreakDown(breakdownVO, shipmentSummaryVOsToMail);

	}

	
	public static MailFlightSummaryVO convertToMailFlightSummaryVOForULDBreakDown(

			BreakdownVO breakdownVO, Collection<ShipmentSummaryVO> shipmentSummaryVOs) {
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(breakdownVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(breakdownVO.getAirportCode());
		mailFlightSummaryVO.setCarrierCode(breakdownVO.getCarrierCode());
		mailFlightSummaryVO.setCarrierId(breakdownVO.getCarrierId());
		mailFlightSummaryVO.setFlightDate(breakdownVO.getFlightDate());
		mailFlightSummaryVO.setFlightNumber(breakdownVO.getFlightNumber());
		mailFlightSummaryVO.setFlightSequenceNumber(breakdownVO.getFlightSequenceNumber());
		mailFlightSummaryVO.setLegSerialNumber(breakdownVO.getLegSerialNumber());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		if (shipmentSummaryVOs != null && !shipmentSummaryVOs.isEmpty()) {
			Map<String, String> uldAwbMap = new HashMap<>();
			for (ShipmentSummaryVO shipmentSummaryVO : shipmentSummaryVOs) {
				String awbKey = new StringBuilder("").append(shipmentSummaryVO.getShipmentPrefix()).append("~")
						.append(shipmentSummaryVO.getMasterDocumentNumber()).append("~")
						.append(shipmentSummaryVO.getDuplicateNumber()).append("~")
						.append(shipmentSummaryVO.getSequenceNumber()).toString();
				if (!uldAwbMap.containsKey(awbKey)) {
					uldAwbMap.put(awbKey, breakdownVO.getUldNumber());
				}
			}
			mailFlightSummaryVO.setUldAwbMap(uldAwbMap);
		}
		mailFlightSummaryVO.setEventCode(EVENT_ARR);
		return mailFlightSummaryVO;
	}
	
	private void getShipmentSummaryVOs(Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentBreakdownVO> shipmentBreakdownVOs,
			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail) {
		if (shipmentBreakdownVOs != null && !shipmentBreakdownVOs.isEmpty()) {

			for (ShipmentBreakdownVO shipmentBreakdownVO : shipmentBreakdownVOs) {
				String[] sccs = null;
				if (shipmentBreakdownVO.getScc() != null && shipmentBreakdownVO.getScc().trim().length() > 0) {
					sccs = shipmentBreakdownVO.getScc().split(",");
				}

				convertShipmentSummaryVOToMail(oneTimeValues, shipmentSummaryVOsToMail, shipmentBreakdownVO, sccs);

			}
		}
	}

	private void convertShipmentSummaryVOToMail(Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail, ShipmentBreakdownVO shipmentBreakdownVO,
			String[] sccs) {
		if ((sccs != null && sccs.length > 0) && oneTimeValues != null) {
			boolean isShipmentSummaryVOAdded = false;
			for (OneTimeVO oneTimeVO : oneTimeValues) {
				for (String scc : sccs) {
					if (oneTimeVO.getFieldValue().equals(scc)) {

						ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
						shipmentSummaryVO.setCompanyCode(shipmentBreakdownVO.getCompanyCode());
						shipmentSummaryVO.setShipmentPrefix(shipmentBreakdownVO.getShipmentPrefix());
						shipmentSummaryVO
								.setMasterDocumentNumber(shipmentBreakdownVO.getMasterDocumentNumber());
						shipmentSummaryVO.setOwnerId(shipmentBreakdownVO.getOwnerId());
						shipmentSummaryVO.setSequenceNumber(shipmentBreakdownVO.getSequenceNumber());
						shipmentSummaryVO.setDuplicateNumber(shipmentBreakdownVO.getDuplicateNumber());
						shipmentSummaryVO.setStatedPiece(shipmentBreakdownVO.getStatedPieces());
						shipmentSummaryVO.setStatedWeight(shipmentBreakdownVO.getStatedWeight());
						shipmentSummaryVO.setDestination(shipmentBreakdownVO.getDestination());
						shipmentSummaryVO.setScc(shipmentBreakdownVO.getScc());
						shipmentSummaryVOsToMail.add(shipmentSummaryVO);
						isShipmentSummaryVOAdded = true;
						break;
					}
				}
				if (isShipmentSummaryVOAdded) {
					break;
				}
			}
			
		}
	}

	private MailFlightSummaryVO getMailFlightSummaryVO(DeliveryVO deliveryVO) throws SystemException, ProxyException {

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<DeliveryDetailsVO> deliveryDetailsVOs = deliveryVO.getDeliveryDetailsVO();
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();
		MailFlightSummaryVO mailFlightSummaryVO = null;
		if (deliveryDetailsVOs != null && !deliveryDetailsVOs.isEmpty()) {
			for (DeliveryDetailsVO deliveryDetailsVO : deliveryDetailsVOs) {

				Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);

				Collection<ShipmentVO> shipmentVOs = getShipmentVOsQuickDelivery(deliveryVO, deliveryDetailsVO);

				MailFlightSummaryVO mailFlightSummaryVOTemp = convertToMailFlightSummaryVOForDelivery(deliveryDetailsVO,
						shipmentVOs);

				mailFlightSummaryVO = addShipmentSummaryVOs(shipmentSummaryVOsToMail, mailFlightSummaryVO,
						oneTimeValues, mailFlightSummaryVOTemp);

			}

			if (mailFlightSummaryVO != null) {
				mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOsToMail);
			}

		}
		return mailFlightSummaryVO;
	}

	private MailFlightSummaryVO addShipmentSummaryVOs(Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail,
			MailFlightSummaryVO mailFlightSummaryVO, Collection<OneTimeVO> oneTimeValues,
			MailFlightSummaryVO mailFlightSummaryVOTemp) {
		if (mailFlightSummaryVOTemp.getShipmentSummaryVOs() != null
				&& !mailFlightSummaryVOTemp.getShipmentSummaryVOs().isEmpty()) {
			for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVOTemp.getShipmentSummaryVOs()) {

				if (mailFlightSummaryVO == null) {
					mailFlightSummaryVO = new MailFlightSummaryVO();
					mailFlightSummaryVO.setCompanyCode(mailFlightSummaryVOTemp.getCompanyCode());
					mailFlightSummaryVO.setAirportCode(mailFlightSummaryVOTemp.getAirportCode());
					mailFlightSummaryVO.setEventCode(EVENT_DLV);
				}

				String[] sccs = null;
				if (shipmentSummaryVO.getScc() != null && shipmentSummaryVO.getScc().trim().length() > 0) {
					sccs = shipmentSummaryVO.getScc().split(",");
				}

				shipmentSummaryVOsToMail(oneTimeValues, shipmentSummaryVOsToMail, shipmentSummaryVO, sccs);

			}
		}
		return mailFlightSummaryVO;
	}
	
	public static MailFlightSummaryVO convertToMailFlightSummaryVOForDelivery(DeliveryDetailsVO deliveryDetailsVO,
			Collection<ShipmentVO> shipmentVOs) {
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = new ArrayList<>();
		ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
		shipmentSummaryVO.setCompanyCode(deliveryDetailsVO.getCompanyCode());
		shipmentSummaryVO.setShipmentPrefix(deliveryDetailsVO.getShipmentPrefix());
		shipmentSummaryVO.setMasterDocumentNumber(deliveryDetailsVO.getMasterDocumentNumber());
		shipmentSummaryVO.setDocumentNumber(deliveryDetailsVO.getDocumentNumber());
		shipmentSummaryVO.setOwnerId(deliveryDetailsVO.getOwnerId());
		shipmentSummaryVO.setSequenceNumber(deliveryDetailsVO.getSequenceNumber());
		shipmentSummaryVO.setDuplicateNumber(deliveryDetailsVO.getDuplicateNumber());
		shipmentSummaryVO.setShipmentDescription(deliveryDetailsVO.getShipmentDescription());
		shipmentSummaryVO.setAgentCode(deliveryDetailsVO.getAgent());
		shipmentSummaryVO.setConsigneeName(deliveryDetailsVO.getConsigneeCode());
		shipmentSummaryVO.setStatedPiece(deliveryDetailsVO.getStatedPieces());
		shipmentSummaryVO.setStatedWeight((new Measure(UnitConstants.WEIGHT, deliveryDetailsVO.getStatedWeight())));
		shipmentSummaryVO.setStatedPiece(deliveryDetailsVO.getStatedPieces());
		shipmentSummaryVO.setAvailablePiece(deliveryDetailsVO.getDeliveredPieces());
		shipmentSummaryVO.setOrigin(deliveryDetailsVO.getOriginCode());
		shipmentSummaryVO.setScc(((ArrayList<ShipmentVO>) shipmentVOs).get(0).getScc());
		shipmentSummaryVOs.add(shipmentSummaryVO);
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(deliveryDetailsVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(deliveryDetailsVO.getAirportCode());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		return mailFlightSummaryVO;
	}

	private Collection<ShipmentVO> getShipmentVOsQuickDelivery(DeliveryVO deliveryVO,
			DeliveryDetailsVO deliveryDetailsVO) throws ProxyException, SystemException {
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode(deliveryVO.getCompanyCode());
		shipmentFilterVO.setMasterDocumentNumber(deliveryDetailsVO
				.getMasterDocumentNumber());
		shipmentFilterVO.setDocumentNumber(deliveryDetailsVO
				.getMasterDocumentNumber());
		shipmentFilterVO.setOwnerId(deliveryDetailsVO.getOwnerId());
		
		return Proxy.getInstance().get(OperationsShipmentProxy.class).findShipments(shipmentFilterVO);
	}

	public MailFlightSummaryVO convertULDDeliveryVOsToMailFlightSummaryVO(Collection<ULDDeliveryVO> uldDeliverVOs)
			throws SystemException, ProxyException {

		MailFlightSummaryVO mailFlightSummaryVO = null;
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<OneTimeVO> oneTimeValues = null;

		oneTimeValues = getMailSCC(logonAttributes);

		for (ULDDeliveryVO deliveryVO : uldDeliverVOs) {
			if (deliveryVO.getShipmentDeliveryDetails() != null && !deliveryVO.getShipmentDeliveryDetails().isEmpty()) {
				for (ShipmentDeliveryVO shipmentDeliveryVO : deliveryVO.getShipmentDeliveryDetails()) {

					Collection<ShipmentVO> shipmentVOs = getShipmentVOs(deliveryVO, shipmentDeliveryVO);

					MailFlightSummaryVO mailFlightSummaryVOTemp = convertToMailFlightSummaryVOForDelivery(
							shipmentDeliveryVO, shipmentVOs);

					if (mailFlightSummaryVO == null) {
						mailFlightSummaryVO = new MailFlightSummaryVO();
						mailFlightSummaryVO.setCompanyCode(mailFlightSummaryVOTemp.getCompanyCode());
						mailFlightSummaryVO.setAirportCode(mailFlightSummaryVOTemp.getAirportCode());
						mailFlightSummaryVO.setEventCode(EVENT_DLV);
					}

					getMailFlightSummaryVO(shipmentSummaryVOsToMail, oneTimeValues,
							mailFlightSummaryVOTemp);

				}
			}
		}
		if(mailFlightSummaryVO!=null){
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOsToMail);
		}
		return mailFlightSummaryVO;
	}

	private void getMailFlightSummaryVO(Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail,
			Collection<OneTimeVO> oneTimeValues, MailFlightSummaryVO mailFlightSummaryVOTemp) {
		if (mailFlightSummaryVOTemp.getShipmentSummaryVOs() != null
				&& !mailFlightSummaryVOTemp.getShipmentSummaryVOs().isEmpty()) {
			for (ShipmentSummaryVO shipmentSummaryVO : mailFlightSummaryVOTemp.getShipmentSummaryVOs()) {
				String[] sccs = null;
				if (shipmentSummaryVO.getScc() != null && shipmentSummaryVO.getScc().trim().length() > 0) {
					sccs = shipmentSummaryVO.getScc().split(",");
				}
				shipmentSummaryVOsToMail(oneTimeValues, shipmentSummaryVOsToMail, shipmentSummaryVO, sccs);
			}
		}
	}

	private void shipmentSummaryVOsToMail(Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail, ShipmentSummaryVO shipmentSummaryVO,
			String[] sccs) {
		boolean isShipmentSummaryVOAdded = false;

		if ((sccs != null && sccs.length > 0) && oneTimeValues != null) {
			for (OneTimeVO oneTimeVO : oneTimeValues) {
				for (String scc : sccs) {
					if (oneTimeVO.getFieldValue().equals(scc)) {
						shipmentSummaryVOsToMail.add(shipmentSummaryVO);
						isShipmentSummaryVOAdded = true;
						break;
					}
				}
				if (isShipmentSummaryVOAdded) {
					break;
				}
			}
		}
	}

	private MailFlightSummaryVO convertToMailFlightSummaryVOForDelivery(ShipmentDeliveryVO deliveryVO,
			Collection<ShipmentVO> shipmentVOs) {
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = new ArrayList<>();
		ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
		shipmentSummaryVO.setCompanyCode(deliveryVO.getCompanyCode());
		shipmentSummaryVO.setShipmentPrefix(deliveryVO.getShipmentPrefix());
		shipmentSummaryVO.setMasterDocumentNumber(deliveryVO.getMasterDocumentNumber());
		shipmentSummaryVO.setDocumentNumber(deliveryVO.getDocumentNumber());
		shipmentSummaryVO.setOwnerId(deliveryVO.getOwnerId());
		shipmentSummaryVO.setSequenceNumber(deliveryVO.getSequenceNumber());
		shipmentSummaryVO.setDuplicateNumber(deliveryVO.getDuplicateNumber());
		shipmentSummaryVO.setShipmentDescription(deliveryVO.getShipmentDescription());
		shipmentSummaryVO.setAgentCode(deliveryVO.getAgentCode());
		shipmentSummaryVO.setConsigneeName(deliveryVO.getConsigneeCode());
		shipmentSummaryVO.setStatedPiece(deliveryVO.getStatedPieces());
		shipmentSummaryVO.setStatedWeight(deliveryVO.getStatedWeight());
		shipmentSummaryVO.setDestination(deliveryVO.getDestination());
		shipmentSummaryVO.setOrigin(deliveryVO.getOrigin());
		shipmentSummaryVO.setStatedPiece(deliveryVO.getStatedPieces());
		shipmentSummaryVO.setAvailablePiece(deliveryVO.getPieces());
		shipmentSummaryVO.setScc(((ArrayList<ShipmentVO>) shipmentVOs).get(0).getScc());
		shipmentSummaryVOs.add(shipmentSummaryVO);
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(deliveryVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(deliveryVO.getAirportCode());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		return mailFlightSummaryVO;
	}

	private Collection<ShipmentVO> getShipmentVOs(ULDDeliveryVO deliveryVO, ShipmentDeliveryVO shipmentDeliveryVO)
			throws ProxyException, SystemException {
		ShipmentFilterVO shipmentFilterVO = new ShipmentFilterVO();
		shipmentFilterVO.setCompanyCode(deliveryVO.getCompanyCode());
		shipmentFilterVO.setMasterDocumentNumber(shipmentDeliveryVO.getMasterDocumentNumber());
		shipmentFilterVO.setDocumentNumber(shipmentDeliveryVO.getMasterDocumentNumber());
		shipmentFilterVO.setOwnerId(shipmentDeliveryVO.getOwnerId());
		return Proxy.getInstance().get(OperationsShipmentProxy.class).findShipments(shipmentFilterVO);
	}

	/**
	 * 
	 * Method :
	 * ExportManifestMailAWBChannelMapper.convertOperationalFlightVOToMailFlightSummaryVO
	 * Added by : Ashil M N on 23-Sep-2021 Used for : Parameters : @param
	 * operationalFlightVO Parameters : @return Parameters : @throws
	 * SystemException Return type : MailFlightSummaryVO
	 */
	public MailFlightSummaryVO convertOperationalFlightVOToMailFlightSummaryVO(OperationalFlightVO operationalFlightVO)
			throws SystemException {

		LOGGER.log(Log.FINE, "convertOperationalFlightVOToMailFlightSummaryVO");

		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);

		FlightFilterVO flightFilterVO = constructFlightFilterVO(operationalFlightVO, logonAttributes);
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = Proxy.getInstance().get(OperationsFlightHandlingProxy.class)
				.findShipmentsInFlight(flightFilterVO);
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = getShipmentSummaryVO( oneTimeValues,
				shipmentSummaryVOs);
		return convertToMailFlightSummaryVO(shipmentSummaryVOsToMail, operationalFlightVO);

	}

	/**
	 * 
	 * Method : ExportManifestMailAWBChannelMapper.getShipmentSummaryVO Added by
	 * : Ashil M N on 23-Sep-2021 Used for : Parameters : @param logonAttributes
	 * Parameters : @param oneTimeValues Parameters : @param shipmentSummaryVOs
	 * Parameters : @return Parameters : @throws SystemException Return type :
	 * Collection<ShipmentSummaryVO>
	 */
	private Collection<ShipmentSummaryVO> getShipmentSummaryVO(Collection<OneTimeVO> oneTimeValues, Collection<ShipmentSummaryVO> shipmentSummaryVOs)
			 {
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();

		if (shipmentSummaryVOs != null && !shipmentSummaryVOs.isEmpty()) {
			for (ShipmentSummaryVO shipmentSummaryVO : shipmentSummaryVOs) {
				String[] sccs = null;
				if (shipmentSummaryVO.getScc() != null && shipmentSummaryVO.getScc().trim().length() > 0) {
					sccs = shipmentSummaryVO.getScc().split(",");
				}
				shipmentSummaryVOsToMail(oneTimeValues, shipmentSummaryVOsToMail, shipmentSummaryVO,
						sccs);
			}
		}
		return shipmentSummaryVOsToMail;
	}


	/**
	 * 
	 * Method : ExportManifestMailAWBChannelMapper.constructFlightFilterVO Added
	 * by : Ashil M N on 23-Sep-2021 Used for : Parameters : @param
	 * operationalFlightVO Parameters : @param logonAttributes Parameters
	 * : @return Return type : FlightFilterVO
	 */
	private FlightFilterVO constructFlightFilterVO(OperationalFlightVO operationalFlightVO,
			LogonAttributes logonAttributes) {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		flightFilterVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		flightFilterVO.setFlightDate(operationalFlightVO.getFlightDate());
		flightFilterVO.setFlightCarrierId(operationalFlightVO.getCarrierId());
		flightFilterVO.setOrigin(operationalFlightVO.getAirportCode() == null ? logonAttributes.getAirportCode()
				: operationalFlightVO.getAirportCode());
		flightFilterVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		return flightFilterVO;
	}

	/**
	 * 
	 * Method : ExportManifestMailAWBChannelMapper.convertToMailFlightSummaryVO
	 * Added by : Ashil M N on 23-Sep-2021 Used for : Parameters : @param
	 * shipmentSummaryVOs Parameters : @param operationalFlightVO Parameters
	 * : @return Return type : MailFlightSummaryVO
	 */
	private MailFlightSummaryVO convertToMailFlightSummaryVO(Collection<ShipmentSummaryVO> shipmentSummaryVOs,
			OperationalFlightVO operationalFlightVO) {
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(operationalFlightVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(operationalFlightVO.getAirportCode());
		mailFlightSummaryVO.setCarrierCode(operationalFlightVO.getCarrierCode());
		mailFlightSummaryVO.setCarrierId(operationalFlightVO.getCarrierId());
		mailFlightSummaryVO.setFlightDate(operationalFlightVO.getFlightDate());
		mailFlightSummaryVO.setFlightNumber(operationalFlightVO.getFlightNumber());
		mailFlightSummaryVO.setFlightSequenceNumber(operationalFlightVO.getFlightSequenceNumber());
		mailFlightSummaryVO.setLegSerialNumber(operationalFlightVO.getLegSerialNumber());
		mailFlightSummaryVO.setPol(operationalFlightVO.getPol());
		mailFlightSummaryVO.setPou(operationalFlightVO.getPou());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		mailFlightSummaryVO.setRoute(operationalFlightVO.getRoute());
		mailFlightSummaryVO.setEventCode(EVENT_ACP);
		return mailFlightSummaryVO;
	}

	public MailFlightSummaryVO getMailFlightSummaryVO(ArrivalVO arrivalVO) throws SystemException {

		MailFlightSummaryVO mailFlightSummaryVO = null;
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = getShipmentSummaryVOs(arrivalVO);
		Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();

		if (shipmentSummaryVOs != null && !shipmentSummaryVOs.isEmpty()) {
			getShipmentSummaryVOsToMail(oneTimeValues, shipmentSummaryVOs, shipmentSummaryVOsToMail);
			mailFlightSummaryVO = convertToMailFlightSummaryVOForArrival(arrivalVO, shipmentSummaryVOs);

		}
		return mailFlightSummaryVO;

	}

	public static MailFlightSummaryVO convertToMailFlightSummaryVOForArrival(ArrivalVO arrivalVO,
			Collection<ShipmentSummaryVO> shipmentSummaryVOs) {
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCompanyCode(arrivalVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(arrivalVO.getAirportCode());
		mailFlightSummaryVO.setCarrierCode(arrivalVO.getCarrierCode());
		mailFlightSummaryVO.setCarrierId(arrivalVO.getCarrierId());
		mailFlightSummaryVO.setFlightDate(arrivalVO.getFlightDate());
		mailFlightSummaryVO.setFlightNumber(arrivalVO.getFlightNumber());
		mailFlightSummaryVO.setFlightSequenceNumber(arrivalVO.getFlightSequenceNumber());
		mailFlightSummaryVO.setLegSerialNumber(arrivalVO.getLegSerialNumber());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		mailFlightSummaryVO.setEventCode(EVENT_ARR);
		Collection<UldArrivalVO> uldArrivalVOs = arrivalVO.getUldArrivalVOs();
		Map<String, String> uldAwbMap = new HashMap<>();
		if (uldArrivalVOs != null && !uldArrivalVOs.isEmpty()) {
			for (UldArrivalVO uldArrivalVO : uldArrivalVOs) {
				for (ShipmentInUldArrivalVO shpInULDArrivalVO : uldArrivalVO.getShipments()) {
					String awbKey = new StringBuilder("").append(shpInULDArrivalVO.getShipmentPrefix()).append("~")
							.append(shpInULDArrivalVO.getMasterDocumentNumber()).append("~")
							.append(shpInULDArrivalVO.getDuplicateNumber()).append("~")
							.append(shpInULDArrivalVO.getSequenceNumber()).toString();
					if (!uldAwbMap.containsKey(awbKey)) {
						uldAwbMap.put(awbKey, uldArrivalVO.getUldNumber());
					}
				}
			}
			mailFlightSummaryVO.setUldAwbMap(uldAwbMap);
		}
		return mailFlightSummaryVO;
	}

	private void getShipmentSummaryVOsToMail(Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentSummaryVO> shipmentSummaryVOs, Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail) {
		for (ShipmentSummaryVO shipmentSummaryVO : shipmentSummaryVOs) {
			String[] sccs = null;
			if (shipmentSummaryVO.getScc() != null && shipmentSummaryVO.getScc().trim().length() > 0) {
				sccs = shipmentSummaryVO.getScc().split(",");
			}

			shipmentSummaryVOsToMail(oneTimeValues, shipmentSummaryVOsToMail, shipmentSummaryVO, sccs);
		}
	}

	private Collection<ShipmentSummaryVO> getShipmentSummaryVOs(ArrivalVO arrivalVO) throws SystemException {
		FlightFilterVO flightFilterVO = new FlightFilterVO();
		flightFilterVO.setCompanyCode(arrivalVO.getCompanyCode());
		flightFilterVO.setFlightNumber(arrivalVO.getFlightNumber());
		flightFilterVO.setFlightSequenceNumber(arrivalVO.getFlightSequenceNumber());
		flightFilterVO.setFlightDate(arrivalVO.getFlightDate());
		flightFilterVO.setFlightCarrierId(arrivalVO.getCarrierId());
		flightFilterVO.setOrigin(((ArrayList<UldArrivalVO>) arrivalVO.getUldArrivalVOs()).get(0).getPol());
		flightFilterVO.setCarrierCode(arrivalVO.getCarrierCode());
		return Proxy.getInstance().get(OperationsFlightHandlingProxy.class).findShipmentsInFlight(flightFilterVO);
	}

	private Collection<OneTimeVO> getMailSCC(LogonAttributes logonAttributes) throws SystemException {
		Collection<OneTimeVO> oneTimeValues = null;
		Collection<String> parameterTypes = new ArrayList<>();
		Map<String, Collection<OneTimeVO>> oneTimeMap = null;
		parameterTypes.add(MAIL_SCC);
		oneTimeMap = Proxy.getInstance().get(SharedDefaultsProxy.class).findOneTimeValues(logonAttributes.getCompanyCode(), parameterTypes);
		if (oneTimeMap != null) {
			oneTimeValues = oneTimeMap.get(MAIL_SCC);
		}
		return oneTimeValues;
	}
	
	
	private MailFlightSummaryVO convertRampHandlingVOToMailFlightSummaryVO(RampHandlingVO rampHandlingVO)
			throws SystemException {

		Collection<RampHandlingULDVO> rampHandlingULDVOs = rampHandlingVO.getUldDeails();
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		MailFlightSummaryVO mailFlightSummaryVO = null;
		if (rampHandlingULDVOs != null && !rampHandlingULDVOs.isEmpty()) {

			Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);

			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail = new ArrayList<>();
			for (RampHandlingULDVO rampHandlingULDVO : rampHandlingULDVOs) {

				Collection<RampHandlingAWBVO> rampHandlingAWBVOs = rampHandlingULDVO.getAwbDetails();

				getShipmentSummaryVOsToMail(rampHandlingVO, oneTimeValues, shipmentSummaryVOsToMail,
						rampHandlingAWBVOs);

				if (mailFlightSummaryVO == null) {
					mailFlightSummaryVO = constructMailFlightSummaryVOForRampTransfer(rampHandlingULDVO,
							rampHandlingVO);
				}

			}

			if (mailFlightSummaryVO != null) {
				mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOsToMail);
			}

		}
		return mailFlightSummaryVO;
	}

	private void getShipmentSummaryVOsToMail(RampHandlingVO rampHandlingVO, Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail, Collection<RampHandlingAWBVO> rampHandlingAWBVOs) {
		if (rampHandlingAWBVOs != null && !rampHandlingAWBVOs.isEmpty()) {

			for (RampHandlingAWBVO rampHandlingAWBVO : rampHandlingAWBVOs) {

				String[] sccs = null;
				if (rampHandlingAWBVO.getSccCode() != null && rampHandlingAWBVO.getSccCode().trim().length() > 0) {
					sccs = rampHandlingAWBVO.getSccCode().split(",");
				}

				addShipmentSummaryVOToMail(rampHandlingVO, oneTimeValues, shipmentSummaryVOsToMail, rampHandlingAWBVO,
						sccs);

			}
		}
	}

	private void addShipmentSummaryVOToMail(RampHandlingVO rampHandlingVO, Collection<OneTimeVO> oneTimeValues,
			Collection<ShipmentSummaryVO> shipmentSummaryVOsToMail, RampHandlingAWBVO rampHandlingAWBVO,
			String[] sccs) {
		if ((sccs != null && sccs.length > 0) && oneTimeValues != null) {
			boolean isShipmentSummaryVOAdded = false;
			for (OneTimeVO oneTimeVO : oneTimeValues) {
				for (String scc : sccs) {
					if (oneTimeVO.getFieldValue().equals(scc)) {
						ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
						shipmentSummaryVO.setCompanyCode(rampHandlingVO.getCompanyCode());
						shipmentSummaryVO.setShipmentPrefix(rampHandlingAWBVO.getShipmentPrefix());
						shipmentSummaryVO.setMasterDocumentNumber(rampHandlingAWBVO.getMasterDocumentNumber());
						shipmentSummaryVO.setOwnerId(rampHandlingAWBVO.getOwnerId());
						shipmentSummaryVO.setSequenceNumber(rampHandlingAWBVO.getSequenceNumber());
						shipmentSummaryVO.setDuplicateNumber(rampHandlingAWBVO.getDuplicateNumber());
						shipmentSummaryVO.setStatedPiece(rampHandlingAWBVO.getStatedPieces());
						shipmentSummaryVO.setStatedWeight(rampHandlingAWBVO.getStatedWeight());
						shipmentSummaryVO.setDestination(rampHandlingAWBVO.getAwbDestination());
						shipmentSummaryVO.setScc(rampHandlingAWBVO.getSccCode());
						shipmentSummaryVOsToMail.add(shipmentSummaryVO);
						isShipmentSummaryVOAdded = true;
						break;
					}
				}
				if (isShipmentSummaryVOAdded) {
					break;
				}
			}

		}
	}

	private  MailFlightSummaryVO constructMailFlightSummaryVOForRampTransfer(RampHandlingULDVO rampHandlingULDVO,
			RampHandlingVO rampHandlingVO) {

		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		mailFlightSummaryVO.setCarrierId(rampHandlingVO.getFlightCarrierId());
		mailFlightSummaryVO.setFlightDate(rampHandlingVO.getFlightDate());
		mailFlightSummaryVO.setFlightNumber(rampHandlingVO.getFlightNumber());
		mailFlightSummaryVO.setFlightSequenceNumber(rampHandlingVO.getFlightSequenceNumber());
		mailFlightSummaryVO.setCarrierCode(rampHandlingVO.getCarrierCode());
		mailFlightSummaryVO.setFinalDestination(rampHandlingULDVO.getOutboundPou());
		mailFlightSummaryVO.setToContainerNumber(rampHandlingULDVO.getUldNumber());
		mailFlightSummaryVO.setCompanyCode(rampHandlingVO.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(rampHandlingVO.getAirportCode());
		mailFlightSummaryVO.setToCarrierId(rampHandlingULDVO.getOutboundFlightCarrierId());
		mailFlightSummaryVO.setToFlightDate(rampHandlingULDVO.getOutboundFlightDate());
		mailFlightSummaryVO.setToFlightNumber(rampHandlingULDVO.getOutboundFlightNumber());
		mailFlightSummaryVO.setToFlightSequenceNumber(rampHandlingULDVO.getOutboundFlightSequenceNumber());
		mailFlightSummaryVO.setToCarrierCode(rampHandlingULDVO.getOutboundCarrierCode());
		mailFlightSummaryVO.setToLegSerialNumber(rampHandlingULDVO.getOutboundLegSerialNumber());
		mailFlightSummaryVO.setToSegmentSerialNumber(rampHandlingULDVO.getOutboundSegmentSerialNumber());
		mailFlightSummaryVO.setEventCode(EVENT_RMPTRA);
		
		Collection<RampHandlingAWBVO> rampHandlingULDVOs = rampHandlingULDVO.getAwbDetails();
		if (rampHandlingULDVOs != null && !rampHandlingULDVOs.isEmpty()) {
			for (RampHandlingAWBVO RampHandlingULDVo : rampHandlingULDVOs) {
				Map<String, String> uldAwbMap = new HashMap<>();
				String awbKey = new StringBuilder("").append(RampHandlingULDVo.getShipmentPrefix()).append("~")
						.append(RampHandlingULDVo.getMasterDocumentNumber()).append("~")
						.append(RampHandlingULDVo.getDuplicateNumber()).append("~")
						.append(RampHandlingULDVo.getSequenceNumber()).toString();
				if (!uldAwbMap.containsKey(awbKey)) {
					uldAwbMap.put(awbKey, rampHandlingULDVO.getUldNumber());
				}
				mailFlightSummaryVO.setUldAwbMap(uldAwbMap);
			}

		}

		return mailFlightSummaryVO;
	}
	public MailFlightSummaryVO convertCTMVOToMailFlightSummaryVO(CTMVO ctmVo) throws SystemException {
		LogonAttributes logonAttributes = ContextUtils.getSecurityContext().getLogonAttributesVO();
		boolean isMailScc = false;
		if (ctmVo != null && !ctmVo.getShipmentsInCTM().isEmpty()) {
			for (ShipmentInCTMVO shipmentInCTMVO : ctmVo.getShipmentsInCTM()) {
				isMailScc=setIsMailScc(shipmentInCTMVO, logonAttributes);
				if (isMailScc) {
					return convertToMailFlightSummaryVOForCTM(ctmVo);
				}
			}
		}
		return null;
	}
	private boolean setIsMailScc(ShipmentInCTMVO shipmentInCTMVO,LogonAttributes logonAttributes) throws SystemException{	
		if (!shipmentInCTMVO.getScc().isEmpty()) {
			String[] sccs = shipmentInCTMVO.getScc().split(",");
			Collection<OneTimeVO> oneTimeValues = getMailSCC(logonAttributes);
			if ((sccs != null && sccs.length > 0) && oneTimeValues != null) {
				for (OneTimeVO oneTimeVO : oneTimeValues) {
					for (String scc : sccs) {
						if (oneTimeVO.getFieldValue().equals(scc)) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
	/**
	 * 
	 * Method :
	 * OperationsFltHandlingVOConverter.convertToMailFlightSummaryVOForCTM Added
	 * by : a-9951 on 08-Sep-2017 Used for : Parameters : @param ctmVo
	 * Parameters : @return Return type : MailFlightSummaryVO
	 */
	public static MailFlightSummaryVO convertToMailFlightSummaryVOForCTM(CTMVO ctmVo) {
		Collection<ShipmentSummaryVO> shipmentSummaryVOs = new ArrayList<>();
		MailFlightSummaryVO mailFlightSummaryVO = new MailFlightSummaryVO();
		if (!ctmVo.getShipmentsInCTM().isEmpty()) {
			for (ShipmentInCTMVO shipmentInCTMVO : ctmVo.getShipmentsInCTM()) {
				ShipmentSummaryVO shipmentSummaryVO = new ShipmentSummaryVO();
				shipmentSummaryVO.setCompanyCode(shipmentInCTMVO.getCompanyCode());
				shipmentSummaryVO.setShipmentPrefix(shipmentInCTMVO.getShipmentPrefix());
				shipmentSummaryVO.setMasterDocumentNumber(shipmentInCTMVO.getMasterDocumentNumber());
				shipmentSummaryVO.setOwnerId(shipmentInCTMVO.getOwnerId());
				shipmentSummaryVO.setSequenceNumber(shipmentInCTMVO.getSequenceNumber());
				shipmentSummaryVO.setDuplicateNumber(shipmentInCTMVO.getDuplicateNumber());
				shipmentSummaryVO.setShipmentDescription(shipmentInCTMVO.getShipmentDescription());
				shipmentSummaryVO.setAgentCode(shipmentInCTMVO.getAgentCode());
				shipmentSummaryVO.setStatedPiece(shipmentInCTMVO.getStatedPieces());
				shipmentSummaryVO.setStatedWeight(shipmentInCTMVO.getStatedWeight());
				shipmentSummaryVO.setDestination(shipmentInCTMVO.getDestination());
				shipmentSummaryVO.setOrigin(shipmentInCTMVO.getOrigin());
				shipmentSummaryVO.setScc(shipmentInCTMVO.getScc());
				if (shipmentInCTMVO.isTransferComplete()) {
					mailFlightSummaryVO.setTransferStatus("COMPLETE");
				}
				if (shipmentInCTMVO.getRejectShipmentFlag()!=null&&shipmentInCTMVO.getRejectShipmentFlag().equals("Y")){
					mailFlightSummaryVO.setTransferStatus("TRANSFER_REJECT");
				} 
				shipmentSummaryVOs.add(shipmentSummaryVO);
			}
		}
		mailFlightSummaryVO.setCompanyCode(ctmVo.getCompanyCode());
		mailFlightSummaryVO.setAirportCode(ctmVo.getAirportCode());
		mailFlightSummaryVO.setCarrierId(ctmVo.getIncomingCarrierId());
		mailFlightSummaryVO.setFlightDate(ctmVo.getIncomingFlightDate());
		mailFlightSummaryVO.setFlightNumber(ctmVo.getIncomingFlightNumber());
		mailFlightSummaryVO.setFlightSequenceNumber(ctmVo.getIncomingFlightSequenceNumber());
		mailFlightSummaryVO.setCarrierCode(ctmVo.getIncomingCarrier());
		mailFlightSummaryVO.setToCarrierId(ctmVo.getOutgoingCarrierId());
		mailFlightSummaryVO.setToFlightDate(ctmVo.getOutgoingFlightDate());
		mailFlightSummaryVO.setToFlightNumber(ctmVo.getOutgoingFlightNumber());
		mailFlightSummaryVO.setToFlightSequenceNumber(ctmVo.getOutgoingFlightSequenceNumber());
		mailFlightSummaryVO.setToCarrierCode(ctmVo.getOutgoingCarrier());
		mailFlightSummaryVO.setShipmentSummaryVOs(shipmentSummaryVOs);
		mailFlightSummaryVO.setEventCode(EVENT_TRA);
		return mailFlightSummaryVO;
	}

}
