package com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.preinvokers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ibsplc.icargo.framework.util.unit.UnitConstants;
import com.ibsplc.neoicargo.framework.core.util.ContextUtil;
import com.ibsplc.neoicargo.mail.util.NeoMastersServiceUtils;
import com.ibsplc.neoicargo.mail.util.OperationsShipmentUtil;
import com.ibsplc.neoicargo.mailmasters.exception.MailTrackingBusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.ibsplc.neoicargo.framework.orchestration.Invoker;
import com.ibsplc.neoicargo.framework.core.lang.BusinessException;
import com.ibsplc.neoicargo.framework.util.unit.Quantity;
import com.ibsplc.neoicargo.mail.component.MailController;
import com.ibsplc.neoicargo.mail.component.feature.autoattachawbdetails.AutoAttachAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.feature.savepawbdetails.SavePAWBDetailsFeatureConstants;
import com.ibsplc.neoicargo.mail.component.proxy.OperationsShipmentProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedAreaProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedCommodityProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedCustomerProxy;
import com.ibsplc.neoicargo.mail.component.proxy.SharedDefaultsProxy;
import com.ibsplc.neoicargo.mail.vo.CarditPawbDetailsVO;
import com.ibsplc.neoicargo.mail.vo.CarditReceptacleVO;
import com.ibsplc.neoicargo.mail.vo.CarditTotalVO;
import com.ibsplc.neoicargo.mail.vo.CarditVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentDocumentVO;
import com.ibsplc.neoicargo.mail.vo.ConsignmentScreeningVO;
import com.ibsplc.neoicargo.mail.vo.MailConstantsVO;
import com.ibsplc.neoicargo.mail.vo.MailInConsignmentVO;
import com.ibsplc.neoicargo.mail.vo.MailbagVO;
import com.ibsplc.neoicargo.mail.vo.RoutingInConsignmentVO;
import com.ibsplc.icargo.business.operations.shipment.vo.OtherCustomsInformationVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RatingDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.RoutingVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailFilterVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentDetailVO;
import com.ibsplc.icargo.business.operations.shipment.vo.ShipmentValidationVO;
import com.ibsplc.icargo.business.shared.commodity.vo.CommodityValidationVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerFilterVO;
import com.ibsplc.icargo.business.shared.customer.vo.CustomerLovVO;
import lombok.extern.slf4j.Slf4j;
import com.ibsplc.neoicargo.framework.util.time.LocalDate;
import com.ibsplc.icargo.framework.util.time.Location;
import com.ibsplc.neoicargo.framework.util.unit.quantity.Quantities;
import java.math.BigDecimal;
import com.ibsplc.icargo.framework.util.unit.MeasureMapper;
import org.springframework.stereotype.Component;
import com.ibsplc.icargo.framework.util.time.LocalDateMapper;

import javax.swing.text.AbstractDocument;

@Component("savepostalshipmentinvoker")
@Slf4j
public class SavePostalShipmentInvoker extends Invoker<CarditVO> {
	@Autowired
	private MeasureMapper measureMapper;
	@Autowired
	private Quantities quantities;
	@Autowired
	private SharedCommodityProxy sharedCommodityProxy;
	@Autowired
	private SharedAreaProxy sharedAreaProxy;
	@Autowired
	private SharedDefaultsProxy sharedDefaultsProxy;
	@Autowired
	private SharedCustomerProxy sharedCustomerProxy;

	@Autowired
	private LocalDate localDateUtil;
	private static final String CARDIT = "CARDIT";

	@Qualifier("mailController")
	private MailController mailController;

	@Autowired
	private OperationsShipmentUtil operationsShipmentUtil;

	@Autowired
	private NeoMastersServiceUtils neoMastersServiceUtils;
	@Override
	public void invoke(CarditVO carditVO) throws BusinessException {
		try {
			if (!MailConstantsVO.CDT_TYP_CANCEL.equalsIgnoreCase(carditVO.getCarditType())) {
				ShipmentDetailVO shipmentDetailVOToSave = getShipmentDetailVO(carditVO);
				if (shipmentDetailVOToSave != null) {

					ShipmentValidationVO shipmentValidationVO = operationsShipmentUtil
							.saveShipmentDetails(shipmentDetailVOToSave);
					carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDetailVOToSave);
					carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
				}
			} else {
				processCancellationCardit(carditVO);
			}
		} catch (Exception e) {
			throw new BusinessException(MailTrackingBusinessException.ERROR_IN_CREATING_PAWB,MailTrackingBusinessException.ERROR_IN_CREATING_PAWB);
		}
	}

	private ShipmentDetailVO getShipmentDetailVO(CarditVO carditVO) throws BusinessException {
		if (carditVO.getCarditPawbDetailsVO() != null) {
			CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
			Collection<CarditReceptacleVO> mailBagInCons = createMailBagsInConsignmentReceptacles(carditVO);
			Collection<ShipmentDetailVO> shipmentDetails = new ArrayList<>();
			Collection<ShipmentValidationVO> shipmentValidations = new ArrayList<>();
			ShipmentDetailVO shipmentDetailVO = null;
			String product = findSystemParameterValue(MailConstantsVO.MAIL_AWB_PRODUCT);
			if (!mailBagInCons.isEmpty()) {
				for (CarditReceptacleVO mailBag : mailBagInCons) {
					if (mailBag.getMasterDocumentNumber() == null
							|| mailBag.getMasterDocumentNumber().equals(carditPawbDetail.getMasterDocumentNumber())) {
						if (mailBag.getMasterDocumentNumber() == null) {
							shipmentDetailVO = new ShipmentDetailVO();
							shipmentDetailVO = createShipment(carditVO, carditPawbDetail, shipmentDetailVO,
									ShipmentDetailVO.OPERATION_FLAG_INSERT);
						} else {
							shipmentDetailVO = operationsShipmentUtil
									.findShipmentDetails(createShipmentFilterVO(mailBag, carditVO));
							if (shipmentDetailVO != null
									&& !shipmentDetailVO.getShipmentStatus().equals(ShipmentDetailVO.AWB_EXECUTED)) {
								shipmentDetailVO = createShipment(carditVO, carditPawbDetail, shipmentDetailVO,
										ShipmentDetailVO.OPERATION_FLAG_UPDATE);
							}
						}
					} else {
						updateShipmentDetails(carditVO, mailBag, shipmentDetails, shipmentDetailVO, carditPawbDetail,
								shipmentValidations);
					}
				}
				carditVO.getCarditPawbDetailsVO().setShipmentDetailVOs(shipmentDetails);
				carditVO.getCarditPawbDetailsVO().setUpdatedshipmentValidationVOs(shipmentValidations);
			} else {
				shipmentDetailVO = new ShipmentDetailVO();
				shipmentDetailVO = createShipment(carditVO, carditPawbDetail, shipmentDetailVO,
						ShipmentDetailVO.OPERATION_FLAG_INSERT);
			}
			if (Objects.nonNull(shipmentDetailVO)) {
				shipmentDetailVO.setProduct(product);
			}
			return shipmentDetailVO;
		}
		return null;
	}

	private MailbagVO createMailbagVO(CarditReceptacleVO mailBag, String companyCode) {
		MailbagVO mailbagVO = new MailbagVO();
		mailbagVO.setCompanyCode(companyCode);
		mailbagVO.setDuplicateNumber(mailBag.getDuplicateNumber());
		mailbagVO.setDocumentNumber(mailBag.getMasterDocumentNumber());
		mailbagVO.setSequenceNumber(mailBag.getSequenceNumber());
		return mailbagVO;
	}

	private ShipmentDetailFilterVO createShipmentFilterVO(CarditReceptacleVO mailBagInCons, CarditVO carditVO) {
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(carditVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailBagInCons.getOwnerId());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailBagInCons.getMasterDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailBagInCons.getDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailBagInCons.getSequenceNumber());
		return shipmentDetailFilterVO;
	}

	private ShipmentValidationVO createShipmentValidationVO(ShipmentDetailVO shipmentDetailVO, CarditVO carditVO) {
		ShipmentValidationVO shipmentValidationVO = new ShipmentValidationVO();
		shipmentValidationVO.setCompanyCode(carditVO.getCompanyCode());
		shipmentValidationVO.setOwnerId(shipmentDetailVO.getOwnerId());
		shipmentValidationVO.setMasterDocumentNumber(shipmentDetailVO.getMasterDocumentNumber());
		shipmentValidationVO.setDocumentNumber(shipmentDetailVO.getMasterDocumentNumber());
		shipmentValidationVO.setDuplicateNumber(shipmentDetailVO.getDuplicateNumber());
		shipmentValidationVO.setSequenceNumber(shipmentDetailVO.getSequenceNumber());
		shipmentValidationVO.setShipmentPrefix(shipmentDetailVO.getShipmentPrefix());
		return shipmentValidationVO;
	}

	private ShipmentDetailVO createShipment(CarditVO carditVO, CarditPawbDetailsVO carditPawbDetail,
											ShipmentDetailVO shipmentDetailVO, String operationFlag) throws BusinessException {
		String agentCode = null;
		String consigneeCode = null;
		agentCode = carditPawbDetail.getAgentCode();
		consigneeCode = carditPawbDetail.getConsigneeAgentCode();
		Collection<RoutingInConsignmentVO> routingVo = createRoutingVOfromCarditPawbDetailVO(carditPawbDetail);
		if (agentCode != null && consigneeCode != null && !carditVO.getTotalsInformation().isEmpty()
				&& carditPawbDetail.getConsignmentOrigin() != null
				&& carditPawbDetail.getConsignmentDestination() != null && !routingVo.isEmpty()) {
			createShipmentDetailVO(carditVO, shipmentDetailVO, operationFlag);
			Collection<CustomerLovVO> agentLovVOS = new ArrayList<>();
			Collection<CustomerLovVO> consigneeLovVOS = new ArrayList<>();
			CustomerFilterVO customerFilterVO = new CustomerFilterVO();
			customerFilterVO.setCompanyCode(carditVO.getCompanyCode());
			customerFilterVO.setCustomerCode(agentCode);
			customerFilterVO.setPageNumber(1);
			try {
				agentLovVOS = sharedCustomerProxy.findCustomers(customerFilterVO);
				customerFilterVO.setCustomerCode(consigneeCode);
				consigneeLovVOS = sharedCustomerProxy.findCustomers(customerFilterVO);
			} catch (BusinessException ex) {
				log.info("Exception :", ex);
			}
			if (agentLovVOS!=null&&agentLovVOS.isEmpty()) {
				shipmentDetailVO.setAgentCode(agentCode);
				shipmentDetailVO.setAgentName(agentLovVOS.iterator().next().getCustomerName());
				shipmentDetailVO.setIataCode(agentLovVOS.iterator().next().getIataCode());
				setShipperDetails(agentLovVOS, shipmentDetailVO);
			}
			if (consigneeLovVOS!=null&&!consigneeLovVOS.isEmpty()) {
				setConsigneeDetails(consigneeLovVOS, shipmentDetailVO);
			}
			collectPieceWieghtdetails(carditVO, shipmentDetailVO, 0,
					quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(0)));
			populateShipmentRoutingDetails(shipmentDetailVO, routingVo, operationFlag);
			shipmentDetailVO.setAWBDataCaptureDone(true);
			return shipmentDetailVO;
		}
		return null;
	}

	private Collection<RoutingInConsignmentVO> createRoutingVOfromCarditPawbDetailVO(
			CarditPawbDetailsVO carditPawbDetailVO) {
		Collection<RoutingInConsignmentVO> routingVo = new ArrayList<>();
		Collection<RoutingInConsignmentVO> consignmentRoutingVOs = carditPawbDetailVO.getConsignmentDocumentVO()
				.getRoutingInConsignmentVOs();
		if (consignmentRoutingVOs != null && !consignmentRoutingVOs.isEmpty()) {
			routingVo = consignmentRoutingVOs;
		}
		return routingVo;
	}

	private ShipmentDetailVO createShipmentDetailVO(CarditVO carditVO, ShipmentDetailVO shipmentDetailVO,
													String operationFlag) {
		CarditPawbDetailsVO carditPawbDetail = carditVO.getCarditPawbDetailsVO();
		shipmentDetailVO.setCompanyCode(carditVO.getCompanyCode());
		shipmentDetailVO.setOwnerId(carditPawbDetail.getOwnerId());
		shipmentDetailVO.setMasterDocumentNumber(carditPawbDetail.getMasterDocumentNumber());
		shipmentDetailVO.setOperationFlag(operationFlag);
		shipmentDetailVO.setDateOfJourney(
				new com.ibsplc.icargo.framework.util.time.LocalDate(
						com.ibsplc.icargo.framework.util.time.LocalDate.NO_STATION, Location.NONE, false));
		shipmentDetailVO.setSourceIndicator(MailConstantsVO.DOCUMENT_TYPE);
		shipmentDetailVO.setOverrideCertificateValidations("N");
		shipmentDetailVO.setSciValidationToBeSkipped(true);
		shipmentDetailVO.setOwnerCode(carditVO.getCompanyCode());
		shipmentDetailVO.setShipmentPrefix(carditPawbDetail.getShipmentPrefix());
		shipmentDetailVO.setServiceCargoClass(AutoAttachAWBDetailsFeatureConstants.MAIL_SERVICE_CARGO_CLASS);
		shipmentDetailVO.setParameterWarningToBeDiscarded(true);
		shipmentDetailVO.setOrigin(carditPawbDetail.getConsignmentOriginAirport());
		shipmentDetailVO.setDestination(carditPawbDetail.getConsignmentDestinationAirport());
		shipmentDetailVO.setDocumentSubType(carditPawbDetail.getSubType());
		shipmentDetailVO.setShipperSignature(carditPawbDetail.getConsignmentOrigin());
		shipmentDetailVO.setExecutionDate(LocalDateMapper.toLocalDate(carditPawbDetail.getConsignmentDocumentVO().getConsignmentDate()));
		shipmentDetailVO.setExecutedBy(carditPawbDetail.getConsignmentDocumentVO().getConsignmentIssuerName());
		shipmentDetailVO.setAgentReference(carditPawbDetail.getConsignmentDocumentVO().getConsignmentNumber());
		if (carditPawbDetail.getConsignmentDocumentVO().getMailInConsignmentVOs() != null && carditPawbDetail
				.getConsignmentDocumentVO().getMailInConsignmentVOs().iterator().next().getContractIDNumber() != null) {
			shipmentDetailVO.setSenderFileReference(carditPawbDetail.getConsignmentDocumentVO()
					.getMailInConsignmentVOs().iterator().next().getContractIDNumber());
		}
		createOtherCustomsInfoVOfromCarditPawbDetailVO(carditVO, shipmentDetailVO);
		return shipmentDetailVO;
	}

	private void createOtherCustomsInfoVOfromCarditPawbDetailVO(CarditVO carditVO, ShipmentDetailVO shipmentDetailVO) {
		Collection<OtherCustomsInformationVO> otherCustomsInfoVos = new ArrayList<>();
		Collection<ConsignmentScreeningVO> consignmentScreeningVos = carditVO.getConsignementScreeningVOs();
		if (Objects.nonNull(consignmentScreeningVos)) {
			consignmentScreeningVos.stream().forEach(consignmentScreeningVo -> {
				StringBuilder appRegBorderAgencyAuthVal = null;
				StringBuilder appRegReferenceId = null;
				if (Objects.nonNull(consignmentScreeningVo.getSecurityReasonCode())) {
					setSecurityScreeningDetails(carditVO, otherCustomsInfoVos, consignmentScreeningVo);
					setAdditionalSecurityInfoDetails(otherCustomsInfoVos, consignmentScreeningVo);
					setApplicableRegulationValues(otherCustomsInfoVos, consignmentScreeningVo,
							appRegBorderAgencyAuthVal, appRegReferenceId);
				}
			});
		}
		shipmentDetailVO.setOtherCustomsInformationVOs(otherCustomsInfoVos);
	}

	private void setSecurityScreeningDetails(CarditVO carditVO,
											 Collection<OtherCustomsInformationVO> otherCustomsInfoVos, ConsignmentScreeningVO consignmentScreeningVo) {
		if (MailConstantsVO.SECURITY_REASON_CODE_CONSIGNOR
				.equalsIgnoreCase(consignmentScreeningVo.getSecurityReasonCode())) {
			setConsignorDetails(carditVO, otherCustomsInfoVos, consignmentScreeningVo);
		}
		if (MailConstantsVO.SECURITY_REASON_CODE_SCREENING
				.equalsIgnoreCase(consignmentScreeningVo.getSecurityReasonCode())) {
			OtherCustomsInformationVO otherCustInfVoForSM = new OtherCustomsInformationVO();
			otherCustInfVoForSM.setOtherCusInfoIdentifier("SM");
			otherCustInfVoForSM.setSourceIndicator(CARDIT);
			otherCustInfVoForSM.setCustomsInfomation(consignmentScreeningVo.getScreeningMethodCode());
			otherCustomsInfoVos.add(otherCustInfVoForSM);
		}
		if (SavePAWBDetailsFeatureConstants.SECURITY_REASON_CODE_EXCEMPTION
				.equalsIgnoreCase(consignmentScreeningVo.getSecurityReasonCode())) {
			OtherCustomsInformationVO otherCustInfVoForSE = new OtherCustomsInformationVO();
			otherCustInfVoForSE.setOtherCusInfoIdentifier("L");
			otherCustInfVoForSE.setSourceIndicator(CARDIT);
			otherCustInfVoForSE.setCustomsInfomation(consignmentScreeningVo.getScreeningMethodCode());
			otherCustomsInfoVos.add(otherCustInfVoForSE);
		}
	}

	private void setAdditionalSecurityInfoDetails(Collection<OtherCustomsInformationVO> otherCustomsInfoVos,
												  ConsignmentScreeningVO consignmentScreeningVo) {
		if (consignmentScreeningVo.getAdditionalSecurityInfo() != null) {
			OtherCustomsInformationVO otherCustInfVoForAdditionalSecurityInfo = new OtherCustomsInformationVO();
			otherCustInfVoForAdditionalSecurityInfo.setOtherCusInfoIdentifier("ST");
			otherCustInfVoForAdditionalSecurityInfo.setSourceIndicator(CARDIT);
			otherCustInfVoForAdditionalSecurityInfo
					.setCustomsInfomation(consignmentScreeningVo.getAdditionalSecurityInfo());
			otherCustomsInfoVos.add(otherCustInfVoForAdditionalSecurityInfo);
		}
	}

	private void setConsignorDetails(CarditVO carditVO, Collection<OtherCustomsInformationVO> otherCustomsInfoVos,
									 ConsignmentScreeningVO consignmentScreeningVo) {
		if (MailConstantsVO.RA_ACCEPTING.equalsIgnoreCase(consignmentScreeningVo.getScreeningMethodCode())) {
			OtherCustomsInformationVO otherCustInfVoForRA = new OtherCustomsInformationVO();
			otherCustInfVoForRA.setCountryCode(consignmentScreeningVo.getIsoCountryCode());
			otherCustInfVoForRA.setInfoIdentifier("ISS");
			otherCustInfVoForRA.setOtherCusInfoIdentifier("RA");
			otherCustInfVoForRA.setSourceIndicator(CARDIT);
			otherCustInfVoForRA.setCustomsInfomation(consignmentScreeningVo.getAgentID());
			OtherCustomsInformationVO otherCustInfVoForRAExpiryDate = new OtherCustomsInformationVO();
			otherCustInfVoForRAExpiryDate.setOtherCusInfoIdentifier("ED");
			otherCustInfVoForRAExpiryDate.setSourceIndicator(CARDIT);
			otherCustInfVoForRAExpiryDate.setCustomsInfomation(consignmentScreeningVo.getExpiryDate());
			OtherCustomsInformationVO otherCustInfVoForStatusDate = new OtherCustomsInformationVO();
			otherCustInfVoForStatusDate.setOtherCusInfoIdentifier("SD");
			otherCustInfVoForStatusDate.setSourceIndicator(CARDIT);
			if (carditVO.getSecurityStatusDate() != null) {
				otherCustInfVoForStatusDate
						.setCustomsInfomation(LocalDateMapper.toLocalDate(carditVO.getSecurityStatusDate()).toDisplayFormat("yyMMddHHmm"));
			}
			OtherCustomsInformationVO otherCustInfVoForSecurityIssuer = new OtherCustomsInformationVO();
			otherCustInfVoForSecurityIssuer.setOtherCusInfoIdentifier("SN");
			otherCustInfVoForSecurityIssuer.setSourceIndicator(CARDIT);
			otherCustInfVoForSecurityIssuer.setCustomsInfomation(carditVO.getConsignmentIssuerName());
			otherCustomsInfoVos.add(otherCustInfVoForRA);
			otherCustomsInfoVos.add(otherCustInfVoForRAExpiryDate);
			otherCustomsInfoVos.add(otherCustInfVoForStatusDate);
			otherCustomsInfoVos.add(otherCustInfVoForSecurityIssuer);
		}
		if (MailConstantsVO.ACCOUNT_CONSIGNOR.equalsIgnoreCase(consignmentScreeningVo.getScreeningMethodCode())) {
			OtherCustomsInformationVO otherCustInfVoForAC = new OtherCustomsInformationVO();
			otherCustInfVoForAC.setOtherCusInfoIdentifier("AC");
			otherCustInfVoForAC.setSourceIndicator(CARDIT);
			otherCustInfVoForAC.setCustomsInfomation(consignmentScreeningVo.getAgentID());
			otherCustomsInfoVos.add(otherCustInfVoForAC);
		}
		if (MailConstantsVO.KNOWN_CONSIGNOR.equalsIgnoreCase(consignmentScreeningVo.getScreeningMethodCode())) {
			OtherCustomsInformationVO otherCustInfVoForKC = new OtherCustomsInformationVO();
			otherCustInfVoForKC.setOtherCusInfoIdentifier("KC");
			otherCustInfVoForKC.setSourceIndicator(CARDIT);
			otherCustInfVoForKC.setCustomsInfomation(consignmentScreeningVo.getAgentID());
			otherCustomsInfoVos.add(otherCustInfVoForKC);
		}
	}

	private void setApplicableRegulationValues(Collection<OtherCustomsInformationVO> otherCustomsInfoVos,
											   ConsignmentScreeningVO consignmentScreeningVo, StringBuilder appRegBorderAgencyAuthVal,
											   StringBuilder appRegReferenceId) {
		if (SavePAWBDetailsFeatureConstants.APPLICABLE_REGULATION
				.equalsIgnoreCase(consignmentScreeningVo.getSecurityReasonCode())) {
			OtherCustomsInformationVO otherCustInfVoForTransportDirection = new OtherCustomsInformationVO();
			otherCustInfVoForTransportDirection.setOtherCusInfoIdentifier("M");
			otherCustInfVoForTransportDirection.setSourceIndicator(CARDIT);
			setApplicableTransportDirection(consignmentScreeningVo, otherCustInfVoForTransportDirection);
			OtherCustomsInformationVO otherCustInfVoForAuthorityAndRegulation = new OtherCustomsInformationVO();
			otherCustInfVoForAuthorityAndRegulation.setOtherCusInfoIdentifier("I");
			otherCustInfVoForAuthorityAndRegulation.setSourceIndicator(CARDIT);
			if (consignmentScreeningVo.getApplicableRegBorderAgencyAuthority() != null) {
				appRegBorderAgencyAuthVal = new StringBuilder(
						consignmentScreeningVo.getApplicableRegBorderAgencyAuthority());
			}
			if (consignmentScreeningVo.getApplicableRegReferenceID() != null) {
				appRegReferenceId = new StringBuilder(consignmentScreeningVo.getApplicableRegReferenceID());
			}
			if (appRegBorderAgencyAuthVal != null && appRegReferenceId != null) {
				String arVal = appRegBorderAgencyAuthVal.append("-").append(appRegReferenceId).toString();
				otherCustInfVoForAuthorityAndRegulation.setCustomsInfomation(arVal);
			}
			otherCustomsInfoVos.add(otherCustInfVoForTransportDirection);
			otherCustomsInfoVos.add(otherCustInfVoForAuthorityAndRegulation);
		}
	}

	private void setApplicableTransportDirection(ConsignmentScreeningVO consignmentScreeningVo,
												 OtherCustomsInformationVO otherCustInfVoForTransportDirection) {
		if (consignmentScreeningVo.getApplicableRegTransportDirection() != null) {
			if ("1".equals(consignmentScreeningVo.getApplicableRegTransportDirection())) {
				otherCustInfVoForTransportDirection.setCustomsInfomation("Export");
			}
			if ("2".equals(consignmentScreeningVo.getApplicableRegTransportDirection())) {
				otherCustInfVoForTransportDirection.setCustomsInfomation("Import");
			}
			if ("3".equals(consignmentScreeningVo.getApplicableRegTransportDirection())) {
				otherCustInfVoForTransportDirection.setCustomsInfomation("Transit");
			}
		}
	}

	private String findSystemParameterValue(String syspar) {
		String sysparValue = null;
		ArrayList<String> systemParameters = new ArrayList<>();
		systemParameters.add(syspar);
		Map<String, String> systemParameterMap = null;
		try {
			systemParameterMap = neoMastersServiceUtils.findSystemParameterByCodes(systemParameters);
		} catch (BusinessException e) {
			return sysparValue;
		}
		if (systemParameterMap != null) {
			sysparValue = systemParameterMap.get(syspar);
		}
		return sysparValue;
	}

	private void setShipperDetails(Collection<CustomerLovVO> shipperLovVOS, ShipmentDetailVO shipmentDetailVO) {
		shipmentDetailVO.setShipperCode(shipperLovVOS.iterator().next().getCustomerCode());
		shipmentDetailVO.setShipperName(shipperLovVOS.iterator().next().getCustomerName());
		shipmentDetailVO.setShipperAddress1(shipperLovVOS.iterator().next().getAddress1());
		shipmentDetailVO.setShipperCity(shipperLovVOS.iterator().next().getCity());
		shipmentDetailVO.setShipperCountry(shipperLovVOS.iterator().next().getCountry());
		shipmentDetailVO.setShipperAccountNumber(shipperLovVOS.iterator().next().getAccountNumber());
		shipmentDetailVO.setShipperTelephoneNumber(shipperLovVOS.iterator().next().getTelephone());
		shipmentDetailVO.setShipperEmailId(shipperLovVOS.iterator().next().getEmail());
		shipmentDetailVO.setShipperState(shipperLovVOS.iterator().next().getState());
		shipmentDetailVO.setShipperPostalCode(shipperLovVOS.iterator().next().getZipCode());
	}

	private void setConsigneeDetails(Collection<CustomerLovVO> consigneeLovVOS, ShipmentDetailVO shipmentDetailVO) {
		shipmentDetailVO.setConsigneeCode(consigneeLovVOS.iterator().next().getCustomerCode());
		shipmentDetailVO.setConsigneeName(consigneeLovVOS.iterator().next().getCustomerName());
		shipmentDetailVO.setConsigneeAddress1(consigneeLovVOS.iterator().next().getAddress1());
		shipmentDetailVO.setConsigneeCity(consigneeLovVOS.iterator().next().getCity());
		shipmentDetailVO.setConsigneeCountry(consigneeLovVOS.iterator().next().getCountry());
		shipmentDetailVO.setConsigneeAccountNumber(consigneeLovVOS.iterator().next().getAccountNumber());
		shipmentDetailVO.setConsigneeTelephoneNumber(consigneeLovVOS.iterator().next().getTelephone());
		shipmentDetailVO.setConsigneeEmailId(consigneeLovVOS.iterator().next().getEmail());
		shipmentDetailVO.setConsigneeState(consigneeLovVOS.iterator().next().getState());
		shipmentDetailVO.setConsigneePostalCode(consigneeLovVOS.iterator().next().getZipCode());
	}

	private void collectPieceWieghtdetails(CarditVO carditVO, ShipmentDetailVO shipmentDetailVO, int statedPieces,
										   Quantity statedWieght) throws BusinessException {
		Quantity statedVolume = quantities.getQuantity(Quantities.VOLUME, BigDecimal.valueOf(0));
		String commodityCode = null;
		String companyCode = null;
		Collection<String> systemParameters = new ArrayList<>();
		Collection<RatingDetailVO> ratingDetails = new ArrayList<>();
		RatingDetailVO ratingDetailVO = new RatingDetailVO();
		systemParameters.add(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		Map<String, String> systemParamterMap = findSystemParameterValuesForBooking(systemParameters);
		if (!systemParamterMap.isEmpty()) {
			commodityCode = systemParamterMap.get(MailConstantsVO.BOOKING_MAIL_COMMODITY_PARAMETER);
		}
		companyCode = shipmentDetailVO.getCompanyCode();
		CommodityValidationVO commodityValidationVO = validateCommodity(companyCode, commodityCode);
		if (carditVO != null) {
			boolean isAcceptedMailBag = false;
			ConsignmentDocumentVO consignmentDocumentVO = carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO();
			List<MailInConsignmentVO> existingAcceptedMailBags = new ArrayList<>();
			if (carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment() != null
					&& carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment()
					.getMailInConsignmentcollVOs() != null) {
				existingAcceptedMailBags = carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment()
						.getMailInConsignmentcollVOs().stream().filter(x -> !x.getMailStatus().equals("NEW"))
						.collect(Collectors.toList());
			}
			Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
			List<MailInConsignmentVO> result = existingAcceptedMailBags.stream().filter(mailbag -> mailInConsignmentVOs
					.stream().anyMatch(mal -> mal.getMailId().equals(mailbag.getMailId())))
					.collect(Collectors.toList());
			if (!result.isEmpty()) {
				isAcceptedMailBag = true;
			}
			carditVO.getCarditPawbDetailsVO().setMailInConsignmentVOs(mailInConsignmentVOs);
			int pieces = 0;
			Double weight = 0.0;
			if (isAcceptedMailBag) {
				for (MailInConsignmentVO mailInConsignment : consignmentDocumentVO.getMailInConsignmentVOs()) {
					pieces++;
					weight = weight + mailInConsignment.getStatedWeight().getDisplayValue().doubleValue();
				}
				statedWieght = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(weight),
						BigDecimal.valueOf(0), MailConstantsVO.WEIGHT_CODE);
				statedPieces = pieces;
			} else {
				if (ShipmentDetailVO.OPERATION_FLAG_UPDATE.equals(shipmentDetailVO.getOperationFlag())
						&& carditVO.getCarditPawbDetailsVO()!=null&&"ACP".equals(carditVO.getCarditPawbDetailsVO().getSourceIndicator())) {
					statedPieces = shipmentDetailVO.getStatedPieces() + Integer
							.parseInt(carditVO.getTotalsInformation().iterator().next().getNumberOfReceptacles());
					statedWieght = quantities.getQuantity(Quantities.WEIGHT,
							BigDecimal.valueOf(shipmentDetailVO.getStatedWeight().getDisplayValue()
									+ carditVO.getTotalsInformation().iterator().next().getWeightOfReceptacles()
									.getDisplayValue().doubleValue()),
							BigDecimal.valueOf(0), shipmentDetailVO.getStatedWeight().getDisplayUnit());
				} else {
					for (CarditTotalVO carditTotal : carditVO.getTotalsInformation()) {
						statedPieces = Integer.parseInt(carditTotal.getNumberOfReceptacles());
						statedWieght = quantities.getQuantity(Quantities.WEIGHT,
								BigDecimal.valueOf(carditTotal.getWeightOfReceptacles().getDisplayValue().doubleValue()),
								BigDecimal.valueOf(0), carditTotal.getWeightOfReceptacles().getDisplayUnit().getName());
					}
				}
			}
		}
		ratingDetailVO.setOperationFlag(RatingDetailVO.OPERATION_FLAG_UPDATE);
		ratingDetailVO.setRateLineSerialNumber(1);
		ratingDetailVO.setGrossWeight(measureMapper.toMeasure(statedWieght));
		ratingDetailVO.setGrossVolume(measureMapper.toMeasure(statedVolume));
		shipmentDetailVO.setGrossStatedVolumeUnit(UnitConstants.VOLUME_UNIT_CUBIC_METERS);
		shipmentDetailVO.setVolumeUnit(UnitConstants.VOLUME_UNIT_CUBIC_METERS);
		if (commodityValidationVO != null) {
			ratingDetailVO.setCommodityName(commodityValidationVO.getCommodityCode());
			ratingDetailVO.setDescription(commodityValidationVO.getCommodityDesc());
		}
		ratingDetailVO.setPieces(statedPieces);
		ratingDetails.add(ratingDetailVO);
		shipmentDetailVO.setRatingDetails(ratingDetails);
		shipmentDetailVO.setStatedPieces(statedPieces);
		shipmentDetailVO.setStatedWeight(measureMapper.toMeasure(statedWieght));
		shipmentDetailVO.setStatedVolume(measureMapper.toMeasure(statedVolume));
		shipmentDetailVO.setTotalVolume(measureMapper.toMeasure(statedVolume));
		shipmentDetailVO.setTotalAcceptedPieces(statedPieces);
		shipmentDetailVO.setTotalAcceptedWeight(measureMapper.toMeasure(statedWieght));
		shipmentDetailVO.setStatedWeightCode(statedWieght.getDisplayUnit().getName());
		shipmentDetailVO.setGrossStatedVolume(measureMapper.toMeasure(statedVolume));
		shipmentDetailVO.setGrossDisplayedVolume(measureMapper.toMeasure(statedVolume));
		shipmentDetailVO.setShipmentDescription(SavePAWBDetailsFeatureConstants.POSTAL_MAIL);
		setSccValues(carditVO, shipmentDetailVO, commodityValidationVO);
		String currencyCode = sharedAreaProxy.getDefaultCurrencyForStation(shipmentDetailVO.getCompanyCode(),
				shipmentDetailVO.getOrigin());
		if (currencyCode != null) {
			shipmentDetailVO.setCurrency(currencyCode);
		}
	}

	private void setSccValues(CarditVO carditVO, ShipmentDetailVO shipmentDetailVO,
							  CommodityValidationVO commodityValidationVO) {
		if (commodityValidationVO != null) {
			shipmentDetailVO.setScc(commodityValidationVO.getSccCode());
			if (carditVO != null && carditVO.getSecurityStatusCode() != null) {
				StringBuilder stringBuilder = new StringBuilder(commodityValidationVO.getSccCode());
				String sccVal = stringBuilder.append(",").append(carditVO.getSecurityStatusCode()).toString();
				shipmentDetailVO.setScc(sccVal);
			}
		} else {
			if (carditVO != null && carditVO.getSecurityStatusCode() != null) {
				shipmentDetailVO.setScc(carditVO.getSecurityStatusCode());
			}
		}
	}

	private Map<String, String> findSystemParameterValuesForBooking(Collection<String> systemParameterCodes) {
		try {
			return neoMastersServiceUtils.findSystemParameterByCodes(systemParameterCodes);
		} catch (BusinessException e) {
			return new HashMap<>();
		}
	}

	public CommodityValidationVO validateCommodity(String companyCode, String commodityCode) {
		CommodityValidationVO commodityValidationVo = null;
		Collection<String> commodityColl = new ArrayList<>();
		Map<String, CommodityValidationVO> commodityMap = new HashMap<>();
		commodityColl.add(commodityCode);
		try {
			commodityMap = sharedCommodityProxy.validateCommodityCodes(companyCode, commodityColl);
		} catch (BusinessException ex) {
			log.info("Exception :", ex);
		}
		if (!commodityMap.isEmpty()) {
			commodityValidationVo = commodityMap.get(commodityCode);
		}
		return commodityValidationVo;
	}

	private void populateShipmentRoutingDetails(ShipmentDetailVO shipmentDetailVO,
												Collection<RoutingInConsignmentVO> consignmentRoutingVOs, String operationFlag) {
		Collection<RoutingVO> routingVOs = new ArrayList<>();
		int sequenceNumber = 1;
		for (RoutingInConsignmentVO consignmentRoutingVO : consignmentRoutingVOs) {
			RoutingVO routingVO = new RoutingVO();
			routingVO.setCompanyCode(consignmentRoutingVO.getCompanyCode());
			routingVO.setCarrierCode(consignmentRoutingVO.getOnwardCarrierCode());
			routingVO.setCarrierId(consignmentRoutingVO.getOnwardCarrierId());
			routingVO.setOrigin(consignmentRoutingVO.getPol());
			routingVO.setDestination(consignmentRoutingVO.getPou());
			routingVO.setAirportCode(consignmentRoutingVO.getPou());
			routingVO.setFlightNumber(consignmentRoutingVO.getOnwardFlightNumber());
			routingVO.setFlightDate(LocalDateMapper.toLocalDate(consignmentRoutingVO.getOnwardFlightDate()));
			routingVO.setRoutingSequenceNumber(sequenceNumber);
			routingVO.setOperationFlag(operationFlag);
			routingVOs.add(routingVO);
			sequenceNumber++;
		}
		shipmentDetailVO.setRoutingDetails(routingVOs);
	}

	private Collection<CarditReceptacleVO> findMasterDocumentFromMailInConsignmentVO(CarditVO carditVO) {
		Collection<CarditReceptacleVO> mailInConsignmentVOs = carditVO.getReceptacleInformation();
		List<CarditReceptacleVO> mails = mailInConsignmentVOs.stream().filter(x -> x.getMasterDocumentNumber() != null)
				.collect(Collectors.toList());
		List<CarditReceptacleVO> mailbags = new ArrayList<>();
		mails.forEach(mail -> {
			if (mailbags.isEmpty()) {
				mailbags.add(mail);
			} else {
				if (!mailbags.stream()
						.anyMatch(malbag -> malbag.getMasterDocumentNumber().equals(mail.getMasterDocumentNumber()))) {
					mailbags.add(mail);
				}
			}
		});
		//update cardit awb no a2 with mailbags from a1 and new mailbags
		if (!mails.isEmpty()) {
			List<CarditReceptacleVO> newMails = mailInConsignmentVOs.stream()
					.filter(x -> x.getMasterDocumentNumber() == null).collect(Collectors.toList());
			if (newMails != null && !newMails.isEmpty()) {
				newMails.forEach(mail -> {
					if (!mailbags.stream().anyMatch(
							malbag -> malbag.getMasterDocumentNumber() == null || malbag.getMasterDocumentNumber()
									.equals(carditVO.getCarditPawbDetailsVO().getMasterDocumentNumber()))) {
						mailbags.add(mail);
					}
				});
			} 	//update cardit awb no a3 with mailbags from a1 and a2(no new mailbags)
			else {
				if (carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment() != null
						&& carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment()
						.getMailInConsignmentcollVOs() != null) {
					Collection<MailInConsignmentVO> existingMailBagsInConsignment = carditVO.getCarditPawbDetailsVO()
							.getExistingMailBagsInConsignment().getMailInConsignmentcollVOs();
					List<MailInConsignmentVO> existingMails = existingMailBagsInConsignment.stream()
							.filter(x -> x.getMasterDocumentNumber()
									.equals(carditVO.getCarditPawbDetailsVO().getMasterDocumentNumber()))
							.collect(Collectors.toList());
					if (existingMails == null || existingMails.isEmpty()) {
						CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
						receptacleVO.setMasterDocumentNumber(null);
						mailbags.add(receptacleVO);
					} else {
						mailbags.add(createReceptacleVO(existingMails.iterator().next()));
					}
				}
			}
		} //update cardit with awb no  A2 for c1 with 2 different awbs mailbags
		else {
			if (carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment() != null
					&& carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment()
					.getMailInConsignmentcollVOs() != null &&carditVO.getCarditPawbDetailsVO().getMasterDocumentNumber()!=null) {
				//List<MailInConsignmentVO> existingMailsOfConsignemnt = new ArrayList<>();
				List<MailInConsignmentVO> existingMailsOfConsignemnt = carditVO.getCarditPawbDetailsVO()
						.getExistingMailBagsInConsignment().getMailInConsignmentcollVOs().stream()
						.filter(x -> carditVO.getCarditPawbDetailsVO().getMasterDocumentNumber()
								.equals(x.getMasterDocumentNumber()))
						.collect(Collectors.toList());
				if (!existingMailsOfConsignemnt.isEmpty()) {
					mailbags.add(createReceptacleVO(existingMailsOfConsignemnt.iterator().next()));
				} else {
					MailInConsignmentVO mailbag = carditVO.getCarditPawbDetailsVO().getExistingMailBagsInConsignment()
							.getMailInConsignmentcollVOs().iterator().next();
					if (mailbag.getMasterDocumentNumber() != null && !mailbag.getMasterDocumentNumber().isEmpty()) {
						mailbags.add(createReceptacleVO(mailbag));
					}
					if (!mailbags.isEmpty()) {
						CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
						receptacleVO.setMasterDocumentNumber(null);
						mailbags.add(receptacleVO);
					}
				}
			}
		}
		return mailbags;
	}

	private ShipmentDetailVO updateShipmentDetails(CarditVO carditVO, CarditReceptacleVO mailBag,
												   Collection<ShipmentDetailVO> shipmentDetails, ShipmentDetailVO shipmentDetailVO,
												   CarditPawbDetailsVO carditPawbDetail, Collection<ShipmentValidationVO> shipmentValidations)
			throws BusinessException {
		ShipmentDetailVO shipmentDelVO = operationsShipmentUtil
				.findShipmentDetails(createShipmentFilterVO(mailBag, carditVO));
		if (shipmentDelVO!=null&&!shipmentDelVO.getShipmentStatus().equals(ShipmentDetailVO.AWB_EXECUTED)) {
			ConsignmentDocumentVO consignmentDocumentVO = carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO();
			String consignmentNumber = consignmentDocumentVO.getConsignmentNumber();
			Collection<MailbagVO> awbMailBags = ContextUtil.getInstance().getBean(MailController.class)
					.findAWBAttachedMailbags(createMailbagVO(mailBag, carditVO.getCompanyCode()), consignmentNumber);
			if (!awbMailBags.isEmpty()) {
				int pieces = 0;
				Double weight = 0.0;
				for (MailbagVO mail : awbMailBags) {
					pieces++;
					weight = weight + mail.getWeight().getDisplayValue().doubleValue();
				}
				Quantity statedWieght = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(weight),
						BigDecimal.valueOf(0), MailConstantsVO.WEIGHT_CODE);
				collectPieceWieghtdetails(null, shipmentDelVO, pieces, statedWieght);
				shipmentDelVO.setOperationFlag(ShipmentDetailVO.OPERATION_FLAG_UPDATE);
				Collection<MailInConsignmentVO> mailInConsignmentVOs = consignmentDocumentVO.getMailInConsignmentVOs();
				carditVO.getCarditPawbDetailsVO().setMailInConsignmentVOs(mailInConsignmentVOs);
				ShipmentValidationVO shipmentValidationVO = operationsShipmentUtil.saveShipmentDetails(shipmentDelVO);
				shipmentDetails.add(shipmentDelVO);
				shipmentValidations.add(shipmentValidationVO);
			} else {
				shipmentDetails.add(shipmentDelVO);
				operationsShipmentUtil.deleteAWB(createShipmentValidationVO(shipmentDelVO, carditVO), "AWB");
				shipmentDetailVO = new ShipmentDetailVO();
				shipmentDetailVO = createShipment(carditVO, carditPawbDetail, shipmentDetailVO,
						ShipmentDetailVO.OPERATION_FLAG_INSERT);
			}
		}
		return shipmentDetailVO;
	}

	private CarditReceptacleVO createReceptacleVO(MailInConsignmentVO mailbag) {
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setMasterDocumentNumber(mailbag.getMasterDocumentNumber());
		receptacleVO.setMailBagId(mailbag.getMailId());
		receptacleVO.setDuplicateNumber(mailbag.getMailDuplicateNumber());
		receptacleVO.setOwnerId(mailbag.getMailBagDocumentOwnerIdr());
		receptacleVO.setSequenceNumber(mailbag.getSequenceNumberOfMailbag());
		return receptacleVO;
	}

	private void processCancellationCardit(CarditVO carditVO) throws BusinessException {
		if (!carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO().getMailInConsignmentcollVOs().isEmpty()) {
			List<MailInConsignmentVO> notNewMailBags = carditVO.getCarditPawbDetailsVO().getConsignmentDocumentVO()
					.getMailInConsignmentcollVOs().stream().filter(x -> !x.getMailStatus().equals("NEW"))
					.collect(Collectors.toList());
			if (notNewMailBags.isEmpty()) {
				Collection<CarditReceptacleVO> mailBagInCons = findMasterDocumentFromMailInConsignmentVO(carditVO);
				if (!mailBagInCons.isEmpty()) {
					for (CarditReceptacleVO mailBag : mailBagInCons) {
						ShipmentDetailVO shipmentDelVO = operationsShipmentUtil
								.findShipmentDetails(createShipmentFilterVO(mailBag, carditVO));
						if (!shipmentDelVO.getShipmentStatus().equals(ShipmentDetailVO.AWB_EXECUTED)) {
							ShipmentValidationVO shipmentValidationVO = createShipmentValidationVO(shipmentDelVO,
									carditVO);
							operationsShipmentUtil.deleteAWB(shipmentValidationVO, "AWB");
						}
					}
				}
			} else {
				updateAwbOfCancellationCardit(carditVO, notNewMailBags);
			}
		}
	}

	private void updateAwbOfCancellationCardit(CarditVO carditVO, Collection<MailInConsignmentVO> notNewMails)
			throws BusinessException {
		ShipmentDetailVO shipmentDelVO = operationsShipmentUtil
				.findShipmentDetails(createShipmentFilterVOFromConsignmentVO(notNewMails.iterator().next(), carditVO));
		if (!shipmentDelVO.getShipmentStatus().equals(ShipmentDetailVO.AWB_EXECUTED)) {
			int pieces = 0;
			Double weight = 0.0;
			for (MailInConsignmentVO mail : notNewMails) {
				pieces++;
				weight = weight + mail.getStatedWeight().getDisplayValue().doubleValue();
			}
			Quantity statedWieght = quantities.getQuantity(Quantities.WEIGHT, BigDecimal.valueOf(weight),
					BigDecimal.valueOf(0), MailConstantsVO.WEIGHT_CODE);
			collectPieceWieghtdetails(null, shipmentDelVO, pieces, statedWieght);
			shipmentDelVO.setOperationFlag(ShipmentDetailVO.OPERATION_FLAG_UPDATE);
			carditVO.getCarditPawbDetailsVO().setMailInConsignmentVOs(notNewMails);
			ShipmentValidationVO shipmentValidationVO = operationsShipmentUtil.saveShipmentDetails(shipmentDelVO);
			carditVO.getCarditPawbDetailsVO().setShipmentDetailVO(shipmentDelVO);
			carditVO.getCarditPawbDetailsVO().setShipmentValidationVO(shipmentValidationVO);
		}
	}

	private ShipmentDetailFilterVO createShipmentFilterVOFromConsignmentVO(MailInConsignmentVO mailBagInCons,
																		   CarditVO carditVO) {
		ShipmentDetailFilterVO shipmentDetailFilterVO = new ShipmentDetailFilterVO();
		shipmentDetailFilterVO.setCompanyCode(carditVO.getCompanyCode());
		shipmentDetailFilterVO.setOwnerId(mailBagInCons.getMailBagDocumentOwnerIdr());
		shipmentDetailFilterVO.setMasterDocumentNumber(mailBagInCons.getMasterDocumentNumber());
		shipmentDetailFilterVO.setDuplicateNumber(mailBagInCons.getMailDuplicateNumber());
		shipmentDetailFilterVO.setSequenceNumber(mailBagInCons.getSequenceNumberOfMailbag());
		return shipmentDetailFilterVO;
	}

	private CarditReceptacleVO createReceptacleVOForCarditPAWB(CarditPawbDetailsVO carditPawbDetailsVO) {
		CarditReceptacleVO receptacleVO = new CarditReceptacleVO();
		receptacleVO.setMasterDocumentNumber(carditPawbDetailsVO.getMasterDocumentNumber());
		receptacleVO.setMailBagId(carditPawbDetailsVO.getMailInConsignmentVOs().iterator().next().getMailId());
		receptacleVO.setDuplicateNumber(
				carditPawbDetailsVO.getMailInConsignmentVOs().iterator().next().getMailDuplicateNumber());
		receptacleVO.setOwnerId(
				carditPawbDetailsVO.getMailInConsignmentVOs().iterator().next().getMailBagDocumentOwnerIdr());
		receptacleVO.setSequenceNumber(
				carditPawbDetailsVO.getMailInConsignmentVOs().iterator().next().getSequenceNumberOfMailbag());
		return receptacleVO;
	}

	private Collection<CarditReceptacleVO> createMailBagsInConsignmentReceptacles(CarditVO carditVO) {
		Collection<CarditReceptacleVO> mailBagInCons;
		if (carditVO.getCarditPawbDetailsVO().getSourceIndicator() != null
				&& carditVO.getCarditPawbDetailsVO().getSourceIndicator().equals("ACP")) {
			if (carditVO.getCarditPawbDetailsVO().isAwbExistsForConsignment()) {
				mailBagInCons = new ArrayList<>();
				mailBagInCons.add(createReceptacleVOForCarditPAWB(carditVO.getCarditPawbDetailsVO()));
			} else {
				mailBagInCons = new ArrayList<>();
			}
		} else {
			mailBagInCons = findMasterDocumentFromMailInConsignmentVO(carditVO);
		}
		return mailBagInCons;
	}
}
