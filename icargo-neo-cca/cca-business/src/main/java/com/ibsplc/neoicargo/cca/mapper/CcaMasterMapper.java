package com.ibsplc.neoicargo.cca.mapper;

import com.ibsplc.icargo.ebl.common.types.UnitsOfMeasures;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.AwbRouting;
import com.ibsplc.icargo.ebl.nbridge.types.qualityaudited.QualityAuditedDetails;
import com.ibsplc.neoicargo.cca.constants.CcaConstants;
import com.ibsplc.neoicargo.cca.constants.CustomerType;
import com.ibsplc.neoicargo.cca.dao.entity.CcaMaster;
import com.ibsplc.neoicargo.cca.modal.BulkActionEdge;
import com.ibsplc.neoicargo.cca.modal.CCAAWBDetailData;
import com.ibsplc.neoicargo.cca.modal.CCAMasterData;
import com.ibsplc.neoicargo.cca.modal.CcaAssigneeData;
import com.ibsplc.neoicargo.cca.modal.CcaCassValidationDataRequest;
import com.ibsplc.neoicargo.cca.modal.CcaCustomerDetailData;
import com.ibsplc.neoicargo.cca.modal.CcaListViewModal;
import com.ibsplc.neoicargo.cca.modal.CcaListViewPageInfo;
import com.ibsplc.neoicargo.cca.modal.CcaNumbersNode;
import com.ibsplc.neoicargo.cca.modal.CcaValidationData;
import com.ibsplc.neoicargo.cca.modal.ReasonCodeModel;
import com.ibsplc.neoicargo.cca.modal.RelatedCCAData;
import com.ibsplc.neoicargo.cca.modal.viewfilter.CCAListViewFilterData;
import com.ibsplc.neoicargo.cca.status.CcaStatus;
import com.ibsplc.neoicargo.cca.util.CcaUtil;
import com.ibsplc.neoicargo.cca.util.TriggerPointUtil;
import com.ibsplc.neoicargo.cca.vo.CCAListViewFilterVO;
import com.ibsplc.neoicargo.cca.vo.CCAMasterVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbRoutingDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaAwbVO;
import com.ibsplc.neoicargo.cca.vo.CcaChargeDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaListViewVO;
import com.ibsplc.neoicargo.cca.vo.CcaNumbersNodeVO;
import com.ibsplc.neoicargo.cca.vo.CcaRateDetailsVO;
import com.ibsplc.neoicargo.cca.vo.CcaRatingDetailVO;
import com.ibsplc.neoicargo.cca.vo.GetCcaListMasterVO;
import com.ibsplc.neoicargo.cca.vo.filter.CCAFilterVO;
import com.ibsplc.neoicargo.common.converters.CentralConfig;
import com.ibsplc.neoicargo.framework.core.lang.modal.Units;
import com.ibsplc.neoicargo.framework.util.currency.Money;
import com.ibsplc.neoicargo.framework.util.currency.mapper.MoneyMapper;
import com.ibsplc.neoicargo.framework.util.unit.mapper.QuantityMapper;
import com.ibsplc.neoicargo.qualityaudit.events.AwbDetailDimensionEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbOtherChargeDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRatingDetailEvent;
import com.ibsplc.neoicargo.qualityaudit.events.AwbRoutingEvent;
import com.ibsplc.neoicargo.qualityaudit.events.InvoicedAWBUpdateEvent;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ibsplc.neoicargo.cca.constants.CcaConstants.AUTO_CCA_SOURCE;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_ORIGINAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_RECORD_TYPE_REVISED;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CCA_TYPE_ACTUAL;
import static com.ibsplc.neoicargo.cca.constants.CcaConstants.CRA_SOURCE;
import static java.util.Optional.ofNullable;

@Mapper(config = CentralConfig.class,
        imports = {Objects.class, Collectors.class, DateTimeFormatter.class,
                Arrays.class, LinkedHashSet.class, ReasonCodeModel.class,
                Optional.class, StringUtils.class, CustomerType.class,
                Stream.class, TriggerPointUtil.class, CcaStatus.class, CcaConstants.class, LocalDateTime.class},
        uses = {QuantityMapper.class, MoneyMapper.class, CcaAwbMapper.class, CcaAwbDetailMapper.class,
                CcaWorkflowMapper.class})
public interface CcaMasterMapper {

    @Mapping(target = "ccaRefNumber", source = "ccaReferenceNumber")
    @Mapping(target = "exportBillingStatus", ignore = true)
    @Mapping(target = "importBillingStatus", ignore = true)
    @Mapping(target = "operation", ignore = true)
    @Mapping(target = "invocationSource", ignore = true)
    @Mapping(target = "businessId", ignore = true)
    CCAFilterVO ccaMasterVOToCcaFilterVO(CCAMasterVO ccaMasterVO);

    @Mapping(target = "statusMessage", expression = "java(statusMessage)")
    CcaValidationData constructCcaValidationData(CcaAssigneeData ccaAssigneeData, @Context String statusMessage);

    @Mapping(target = "remarks", source = "ccaRemarks")
    CcaValidationData ccaMasterVOToCcaValidationData(CCAMasterVO ccaMasterVO);

    @Mapping(target = "ccaIssueDate", expression = "java(Objects.nonNull(ccaMasterVO.getCcaIssueDate()) ? ccaMasterVO.getCcaIssueDate().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")) : null)")
    @Mapping(target = "billingPeriodTo", expression = "java(Objects.nonNull(ccaMasterVO.getBillingPeriodTo()) ? ccaMasterVO.getBillingPeriodTo().format(DateTimeFormatter.ofPattern(\"dd-MMM-yyyy\")) : null)")
    @Mapping(target = "billingPeriodFrom", expression = "java(Objects.nonNull(ccaMasterVO.getBillingPeriodFrom()) ? ccaMasterVO.getBillingPeriodFrom().format(DateTimeFormatter.ofPattern(\"dd-MMM-yyyy\")) : null)")
    @Mapping(target = "executionDate", expression = "java(Optional.ofNullable(ccaMasterVO.getOriginalShipmentVO()).map(CcaAwbVO::getExecutionDate).map(localDate-> {return localDate.atStartOfDay().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy HH:mm:ss\"));}).orElse(null))")
    @Mapping(target = "originalAWBData", source = "originalShipmentVO")
    @Mapping(target = "revisedAWBData", source = "revisedShipmentVO")
    @Mapping(target = "outboundBillingStatus", source = "exportBillingStatus")
    @Mapping(target = "inboundBillingStatus", source = "importBillingStatus")
    @Mapping(target = "unitOfMeasure", source = "ccaMasterVO", qualifiedByName = "unitsOfMeasure")
    @Mapping(target = "reasonCodes", expression = "java(Arrays.stream(Optional.ofNullable(ccaMasterVO.getCcaReason()).orElse(\"\").split(\",\")).map(ReasonCodeModel::new).collect(Collectors.toList()))")
    CCAMasterData constructCCAMasterData(CCAMasterVO ccaMasterVO);

    @Mapping(target = "lastUpdatedTime", expression = "java(Objects.nonNull(ccaMasterVO.getLastUpdatedTime())?java.sql.Timestamp.valueOf(ccaMasterVO.getLastUpdatedTime()):null)")
    @Mapping(target = "ccaAwb", source = "shipmentDetailVOs")
    @Mapping(target = "updateAgentFlag", expression = "java(Objects.nonNull(ccaMasterVO.getUpdateAgentFlag())?ccaMasterVO.getUpdateAgentFlag():\"N\")")
    @Mapping(target = "autoCCAFlag", expression = "java(\"Auto CCA\".equals(ccaMasterVO.getCcaSource()) ? \"Y\" : \"N\")")
    CcaMaster constructCCAMasterFromVo(CCAMasterVO ccaMasterVO);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "billingPeriodTo", source = "billingPeriodTo", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "billingPeriodFrom", source = "billingPeriodFrom", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "originalShipmentVO", source = "originalAWBData")
    @Mapping(target = "revisedShipmentVO", source = "revisedAWBData")
    @Mapping(target = "ccaSource", constant = CRA_SOURCE)
    @Mapping(target = "originalShipmentVO.shippingDate", source = "originalAWBData.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedShipmentVO.shippingDate", source = "revisedAWBData.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalShipmentVO.executionDate", source = "originalAWBData.executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "revisedShipmentVO.executionDate", source = "revisedAWBData.executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "executionDate", source = "originalAWBData.executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "firstFlightDate", source = "originalAWBData.firstFlightDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalShipmentVO.payType", expression = "java((StringUtils.isNotBlank(cCAAWBDetailData.getPayType()) && StringUtils.isNotBlank(cCAAWBDetailData.getAwbOtherChargePaymentType())) " +
            "? cCAAWBDetailData.getPayType().substring(0, 1).concat(cCAAWBDetailData.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedShipmentVO.payType", expression = "java((StringUtils.isNotBlank(cCAAWBDetailData.getPayType()) && StringUtils.isNotBlank(cCAAWBDetailData.getAwbOtherChargePaymentType())) " +
            "? cCAAWBDetailData.getPayType().substring(0, 1).concat(cCAAWBDetailData.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedShipmentVO.awbQualityAuditDetail.totalNonAWBCharge", source = "revisedAWBData.totalNonAWBCharge")
    @Mapping(target = "originalShipmentVO.awbQualityAuditDetail.totalNonAWBCharge", source = "originalAWBData.totalNonAWBCharge")
    @Mapping(target = "documentOwnerId", ignore = true)
    @Mapping(target = "ccaReason", expression = "java(Optional.ofNullable(ccaMasterData.getReasonCodes()).orElse(new ArrayList<>()).stream().map(ReasonCodeModel::getParameterCode).collect(Collectors.joining(\",\")))")
    @Mapping(target = "exportBillingStatus", source = "outboundBillingStatus")
    @Mapping(target = "importBillingStatus", source = "inboundBillingStatus")
    @Mapping(target = "triggerPoint", expression = "java(TriggerPointUtil.getTriggerPoint(ccaMasterData.getCcaScreenId()))")
    @Mapping(target = "originalShipmentVO.displayTaxDetails", ignore = true)
    @Mapping(target = "revisedShipmentVO.displayTaxDetails", ignore = true)
    @Mapping(target = "autoCalculateTax", expression = "java(CcaConstants.FLAG_Y.equals(ccaMasterData.getAutoCalculateTax()) ? CcaConstants.FLAG_Y : CcaConstants.FLAG_N)")
    @Mapping(target = "executionStation", source = "originalAWBData.executionStation")
    CCAMasterVO constructCCAMasterVOFromCCAData(CCAMasterData ccaMasterData, @Context Units units);

    List<RelatedCCAData> constructRelatedCCAData(List<CCAMasterVO> ccaMasterVO);

    RelatedCCAData constructRelatedCCAData(CCAMasterVO ccaMasterVO);

    CcaNumbersNode constructCcaNumbersData(CcaNumbersNodeVO ccaNumbersNodeVO);

    List<CcaNumbersNode> constructCcaNumbersData(List<CcaNumbersNodeVO> ccaNumbersNodeVO);

    @AfterMapping
    default void setRatesAndChargesAndDetails(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent, @Context Units units, @MappingTarget CCAMasterVO ccaMasterVO) {
        final var revisedShipmentVO = ccaMasterVO.getRevisedShipmentVO();
        final var originalShipmentVO = ccaMasterVO.getOriginalShipmentVO();

        // TODO replace mocks when when the QA service starts sending all the necessary data
        var awbRatingDetailEvent = invoicedAWBUpdateEvent.getAwbRatingDetailEvent();
        if (awbRatingDetailEvent == null) {
            var mockAwbRatingDetailEvent = new AwbRatingDetailEvent();
            mockAwbRatingDetailEvent.setCommodityCode("GEN");
            mockAwbRatingDetailEvent.setRateType("IATA");
            mockAwbRatingDetailEvent.setCharge(10.0);
            mockAwbRatingDetailEvent.setChargeableWeight(50.0);
            mockAwbRatingDetailEvent.setRate(25.0);

            awbRatingDetailEvent = List.of(mockAwbRatingDetailEvent);
        }

        var awbDetailEvent = invoicedAWBUpdateEvent.getAwbDetailEvent();
        if (awbDetailEvent == null) {
            final var awbDimension = new AwbDetailDimensionEvent();
            awbDimension.setHeight(5.0);
            awbDimension.setWeight(200.0);
            awbDimension.setVolume(10.0);
            awbDimension.setLength(50.0);
            awbDimension.setWidth(100.0);
            awbDimension.setTiltable(true);

            final var mockAwbDetailEvent = new AwbDetailEvent();
            mockAwbDetailEvent.setChargeableWeight(0.0);
            mockAwbDetailEvent.setAdjustedWeight(0.0);
            mockAwbDetailEvent.setAwbDetailDimensionEvent(List.of(awbDimension));
            mockAwbDetailEvent.setCommodityCode("GEN");
            mockAwbDetailEvent.setGrossVolume(0.0);
            mockAwbDetailEvent.setGrossWeight(0.0);
            mockAwbDetailEvent.setNumberOfPieces(12L);
            mockAwbDetailEvent.setVolumetricWeight(0.0);
            mockAwbDetailEvent.setSccCode("GEN");
            mockAwbDetailEvent.setShipmentDescription("GENERAL CARGO");

            awbDetailEvent = List.of(mockAwbDetailEvent);
        }

        var awbOtherChargeDetailEvent = invoicedAWBUpdateEvent.getAwbOtherChargeDetailEvent();
        if (awbOtherChargeDetailEvent == null) {
            final var mockAwbOtherChargeDetailEvent = new AwbOtherChargeDetailEvent();
            mockAwbOtherChargeDetailEvent.setChargeHeadCode("HD");
            mockAwbOtherChargeDetailEvent.setChargeHead("Document Charge");
            mockAwbOtherChargeDetailEvent.setCharge(10.2);
            mockAwbOtherChargeDetailEvent.setDueCarrier(true);
            mockAwbOtherChargeDetailEvent.setDueAgent(false);

            awbOtherChargeDetailEvent = List.of(mockAwbOtherChargeDetailEvent);
        }

        var awbRoutingEvent = invoicedAWBUpdateEvent.getAwbRoutingEvent();
        if (awbRoutingEvent == null) {
            final var mockAwbRoutingEvent = new AwbRoutingEvent();
            mockAwbRoutingEvent.setShipmentPrefix("134");
            mockAwbRoutingEvent.setMasterDocumentNumber("44440000");
            mockAwbRoutingEvent.setCompanyCode("AV");
            mockAwbRoutingEvent.setFlightCarrierCode("AV");
            mockAwbRoutingEvent.setFlightDate("2020-10-12");
            mockAwbRoutingEvent.setFlightNumber("1548");
            mockAwbRoutingEvent.setFlightType("Narrow");
            mockAwbRoutingEvent.setPieces(10);
            mockAwbRoutingEvent.setChgWeight(15.0);
            mockAwbRoutingEvent.setVolWeight(10.0);
            mockAwbRoutingEvent.setWeight(10.0);
            mockAwbRoutingEvent.setVolume(10.0);
            mockAwbRoutingEvent.segOrgCod("CDG");
            mockAwbRoutingEvent.setSegDstCod("DXB");
            mockAwbRoutingEvent.setFlightCarrierId(12);

            awbRoutingEvent = List.of(mockAwbRoutingEvent);
        }


        revisedShipmentVO.setAwbRates(awbRatingDetailEventToAwbRates(awbRatingDetailEvent, units));
        revisedShipmentVO.setRatingDetails(awbDetailEventToCcaRatingDetailVO(awbDetailEvent, units));
        revisedShipmentVO.setAwbCharges(awbOtherChargeDetailEventToCcaChargeDetailsVO(awbOtherChargeDetailEvent, units));
        revisedShipmentVO.setCcaAwbRoutingDetails(awbRoutingEventToCcaAwbRoutingDetails(awbRoutingEvent, units));

        originalShipmentVO.setAwbRates(awbRatingDetailEventToAwbRates(awbRatingDetailEvent, units));
        originalShipmentVO.setRatingDetails(awbDetailEventToCcaRatingDetailVO(awbDetailEvent, units));
        originalShipmentVO.setAwbCharges(awbOtherChargeDetailEventToCcaChargeDetailsVO(awbOtherChargeDetailEvent, units));
        originalShipmentVO.setCcaAwbRoutingDetails(awbRoutingEventToCcaAwbRoutingDetails(awbRoutingEvent, units));
    }

    @AfterMapping
    default void setPaymentTypes(@MappingTarget CCAMasterVO ccaMasterVO) {
        ofNullable(ccaMasterVO.getOriginalShipmentVO())
                .ifPresent(originalShipment -> ofNullable(originalShipment.getAwbCharges())
                        .ifPresent(charges -> charges
                                .forEach(charge -> charge.setPaymentType(originalShipment.getAwbOtherChargePaymentType()))));
        ofNullable(ccaMasterVO.getRevisedShipmentVO())
                .ifPresent(revisedShipment -> ofNullable(revisedShipment.getAwbCharges())
                        .ifPresent(charges -> charges
                                .forEach(charge -> charge.setPaymentType(revisedShipment.getAwbOtherChargePaymentType()))));
    }

    @AfterMapping
    default void setTotals(@MappingTarget CCAMasterVO ccaMasterVO) {
        setTotals(ccaMasterVO.getOriginalShipmentVO());
        setTotals(ccaMasterVO.getRevisedShipmentVO());
    }

    @Named("unitsOfMeasure")
    default Units getUnitsOfMeasure(CCAMasterVO ccaMasterVO) {
        return Optional.ofNullable(ccaMasterVO.getOriginalShipmentVO())
                .map(CcaAwbVO::getUnitOfMeasure).orElse(null);
    }

    private void setTotals(CcaAwbVO ccaAwbVO) {
        if (ccaAwbVO != null) {
            final var awbOtherChargePaymentType = ccaAwbVO.getAwbOtherChargePaymentType();
            final var totalsByType = ccaAwbVO.getAwbCharges().stream()
                    .collect(Collectors.groupingBy(CcaChargeDetailsVO::isDueAgent,
                            Collectors.mapping(charge -> charge.getCharge().getAmount(),
                                    Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))));
            if ("PP".equals(awbOtherChargePaymentType)) {
                ccaAwbVO.setTotalDueAgtPPDChg(totalsByType.getOrDefault(true, BigDecimal.ZERO).doubleValue());
                ccaAwbVO.setTotalDueCarPPDChg(totalsByType.getOrDefault(false, BigDecimal.ZERO).doubleValue());
                ccaAwbVO.setTotalDueAgtCCFChg(0D);
                ccaAwbVO.setTotalDueCarCCFChg(0D);
            } else {
                // awbOtherChargePaymentType == CC
                ccaAwbVO.setTotalDueAgtCCFChg(totalsByType.getOrDefault(true, BigDecimal.ZERO).doubleValue());
                ccaAwbVO.setTotalDueCarCCFChg(totalsByType.getOrDefault(false, BigDecimal.ZERO).doubleValue());
                ccaAwbVO.setTotalDueAgtPPDChg(0D);
                ccaAwbVO.setTotalDueCarPPDChg(0D);
            }
        }
    }

    @AfterMapping
    default void setUnitsOfMeasure(@MappingTarget CCAMasterVO masterVO) {
        if (masterVO != null && masterVO.getUnitOfMeasure() != null) {
            final var units = masterVO.getUnitOfMeasure();

            final var originalAwb = masterVO.getOriginalShipmentVO();
            if (originalAwb != null) {
                setUnitsOfMeasureToInnerElements(units, originalAwb);
            }

            final var revisedAwb = masterVO.getRevisedShipmentVO();
            if (revisedAwb != null) {
                setUnitsOfMeasureToInnerElements(units, revisedAwb);
            }

            if (masterVO.getShipmentDetailVOs() != null) {
                masterVO.getShipmentDetailVOs().forEach(awbVO -> setUnitsOfMeasureToInnerElements(units, awbVO));
            }
        }
    }

    private void setUnitsOfMeasureToInnerElements(Units units, CcaAwbVO awb) {
        awb.setUnitOfMeasure(units);
        final var ratingDetails = awb.getRatingDetails();
        if (ratingDetails != null) {
            for (CcaRatingDetailVO ratingDetail : ratingDetails) {
                ratingDetail.setUnitOfMeasure(units);
                if (!CcaUtil.isNullOrEmpty(ratingDetail.getDimensions())) {
                    ratingDetail.getDimensions().stream().filter(Objects::nonNull).forEach(dimensionsVO -> dimensionsVO.setUnitOfMeasure(units));
                }
                if (!CcaUtil.isNullOrEmpty(ratingDetail.getUlds())) {
                    ratingDetail.getUlds().stream().filter(Objects::nonNull).forEach(uldVO -> uldVO.setUnitOfMeasure(units));
                }
            }
        }
    }

    @AfterMapping
    default void enrichMasterVOWithShipmentDetails(@MappingTarget CCAMasterVO ccaMasterVO) {
        var shipmentDetailVOs = new ArrayList<CcaAwbVO>();
        var originalVO = ccaMasterVO.getOriginalShipmentVO();
        if (originalVO != null) {
            originalVO.setRecordType(CCA_RECORD_TYPE_ORIGINAL);
            originalVO.setShipmentPrefix(ccaMasterVO.getShipmentPrefix());
            originalVO.setMasterDocumentNumber(ccaMasterVO.getMasterDocumentNumber());
            shipmentDetailVOs.add(originalVO);
        }

        var revisedVO = ccaMasterVO.getRevisedShipmentVO();
        if (revisedVO != null) {
            revisedVO.setRecordType(CCA_RECORD_TYPE_REVISED);
            revisedVO.setShipmentPrefix(ccaMasterVO.getShipmentPrefix());
            revisedVO.setMasterDocumentNumber(ccaMasterVO.getMasterDocumentNumber());
            shipmentDetailVOs.add(revisedVO);
        }

        ccaMasterVO.setShipmentDetailVOs(shipmentDetailVOs);
    }

    @Mapping(target = "ccaIssueDate", expression = "java(Objects.nonNull(ccaListViewVO.getCcaIssueDate()) ? ccaListViewVO.getCcaIssueDate().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")) : null)")
    @Mapping(target = "ccaReason", expression = "java(Optional.ofNullable(ccaListViewVO.getCcaReason())" +
            ".map(reason -> Stream.of(reason.split(\",\"))" +
            "       .map(ReasonCodeModel::new)" +
            "       .collect(Collectors.toList()))" +
            ".orElse(new ArrayList<>()))")
    @Mapping(target = "netValueExport", expression = "java(Objects.nonNull(ccaListViewVO.getNetValueExport()) ? ccaListViewVO.getNetValueExport().getAmount().doubleValue() : null)")
    @Mapping(target = "netValueImport", expression = "java(Objects.nonNull(ccaListViewVO.getNetValueImport()) ? ccaListViewVO.getNetValueImport().getAmount().doubleValue() : null)")
    @Mapping(source = "ccaListViewVO", target = "agentDetails", qualifiedByName = "agentDetails")
    @Mapping(source = "ccaListViewVO", target = "inboundCustomerDetails", qualifiedByName = "inboundCustomerDetails")
    @Mapping(source = "ccaListViewVO", target = "outboundCustomerDetails", qualifiedByName = "outboundCustomerDetails")
    CcaListViewModal mapCcaListViewVOToModal(CcaListViewVO ccaListViewVO, @Context int totalPages, @Context long totalElements);

    List<CcaListViewModal> mapCcaListViewVOToModal(Page<CcaListViewVO> ccaListViewVO, @Context int totalPages, @Context long totalElements);

    default CcaListViewPageInfo mapCcaListViewVOToPageInfo(List<CcaListViewModal> ccaListViewModals,
                                                           int totalPages, long totalElements) {
        final var ccaListViewPageInfo = new CcaListViewPageInfo();
        ccaListViewPageInfo.setTotalPages(totalPages);
        ccaListViewPageInfo.setTotalElements(totalElements);
        ccaListViewPageInfo.setEdges(ccaListViewModals);
        return ccaListViewPageInfo;
    }

    @Named("inboundCustomerDetails")
    default CcaCustomerDetailData getInboundCustomerDetails(CcaListViewVO ccaListViewVO) {
        return ccaListViewVO.getInboundCustomerDetails().stream()
                .findAny()
                .orElse(null);
    }

    @Named("outboundCustomerDetails")
    default CcaCustomerDetailData getOutboundCustomerDetails(CcaListViewVO ccaListViewVO) {
        return ccaListViewVO.getOutboundCustomerDetails().stream()
                .findAny()
                .orElse(null);
    }

    @Named("agentDetails")
    default CcaCustomerDetailData getAgentDetails(CcaListViewVO ccaListViewVO) {
        return ccaListViewVO.getAgentDetails().stream()
                .findAny()
                .orElse(null);
    }

    default void setCcaMasterVOToNetValueRequest(CCAMasterVO ccaMasterVO, QualityAuditedDetails qualityAuditedDetails) {
        if (ccaMasterVO.getCcaIssueDate() != null) {
            qualityAuditedDetails.setExecutionDate(ccaMasterVO.getCcaIssueDate().atStartOfDay().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
        qualityAuditedDetails.setUnitOfMeasure(constructFromUnits(ccaMasterVO.getUnitOfMeasure()));
        qualityAuditedDetails.setMasterDocumentNumber(ccaMasterVO.getMasterDocumentNumber());
        qualityAuditedDetails.setShipmentPrefix(ccaMasterVO.getShipmentPrefix());
    }

    @Named("constructFromUnits")
    UnitsOfMeasures constructFromUnits(Units units);

    @Named("awbRoutingDetailsVOSetToAwbRoutingList")
    @Mapping(target = "weight", expression = "java(java.util.Objects.nonNull(awbRoutingDetailsVOS.getWeight()) ? awbRoutingDetailsVOS.getWeight().getRoundedValue().doubleValue() : 0.0)")
    @Mapping(target = "chgWeight", expression = "java(java.util.Objects.nonNull(awbRoutingDetailsVOS.getChgWeight()) ? awbRoutingDetailsVOS.getChgWeight().getRoundedValue().doubleValue() : 0.0)")
    List<AwbRouting> awbRoutingDetailsVOSetToAwbRoutingList(Set<CcaAwbRoutingDetailsVO> awbRoutingDetailsVOS);

    @Mapping(target = "unitOfMeasure", source = "ccaMasterVO.unitOfMeasure", qualifiedByName = "constructFromUnits")
    @Mapping(target = "masterDocumentNumber", source = "ccaMasterVO.masterDocumentNumber")
    @Mapping(target = "shipmentPrefix", source = "ccaMasterVO.shipmentPrefix")
    @Mapping(target = "executionDate", expression = "java(Objects.nonNull(ccaMasterVO.getCcaIssueDate()) ? ccaMasterVO.getCcaIssueDate().atStartOfDay().format(DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")) : null)")
    @Mapping(target = "awbDetails", source = "ccaAwbVO.ratingDetails")
    @Mapping(target = "awbRouting", source = "ccaAwbVO.ccaAwbRoutingDetails", qualifiedByName = "awbRoutingDetailsVOSetToAwbRoutingList")
    @Mapping(target = "awbRatingDetails", source = "ccaAwbVO.awbRates")
    @Mapping(target = "awbOtherChargeDetails", source = "ccaAwbVO.awbCharges")
    @Mapping(target = "chargeableWeight", expression = "java(Objects.nonNull(ccaAwbVO.getChargeableWeight()) ? ccaAwbVO.getChargeableWeight().getValue().doubleValue() : 0.0)")
    @Mapping(target = "volumetricWeight", expression = "java(Objects.nonNull(ccaAwbVO.getVolumetricWeight()) ? ccaAwbVO.getVolumetricWeight().getValue().doubleValue() : 0.0)")
    @Mapping(target = "adjustedWeight", expression = "java(Objects.nonNull(ccaAwbVO.getAdjustedWeight()) ? ccaAwbVO.getAdjustedWeight().getValue().doubleValue() : 0.0)")
    @Mapping(target = "weight", expression = "java(Objects.nonNull(ccaAwbVO.getWeight()) ? ccaAwbVO.getWeight().getValue().doubleValue() : 0.0)")
    @Mapping(target = "volume", expression = "java(Objects.nonNull(ccaAwbVO.getVolume()) ? ccaAwbVO.getVolume().getValue().doubleValue() : 0.0)")
    @Mapping(target = "airFreightCharge", source = "ccaAwbVO.netCharge")
    @Mapping(target = "companyCode", source = "ccaAwbVO.companyCode")
    @Mapping(target = "exportBillingStatus", source = "ccaMasterVO.exportBillingStatus")
    @Mapping(target = "importBillingStatus", source = "ccaMasterVO.importBillingStatus")
    @Mapping(target = "triggerPoint", source = "ccaAwbVO.triggerPoint")
    @Mapping(target = "executionStation", source = "ccaAwbVO.executionStation")
    QualityAuditedDetails constructQualityAuditedDetailsFromCcaAwbVO(CcaAwbVO ccaAwbVO, CCAMasterVO ccaMasterVO);

    @AfterMapping
    default void constructTotalValues(CcaAwbVO ccaAwbVO, @MappingTarget QualityAuditedDetails qualityAuditedDetails) {
        var totalAgentCharge = ccaAwbVO.getAwbCharges().stream()
                .filter(charge -> charge.isDueAgent() && !charge.isDueCarrier())
                .map(CcaChargeDetailsVO::getCharge)
                .map(Money::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        var totalCarrierCharge = ccaAwbVO.getAwbCharges().stream()
                .filter(charge -> !charge.isDueAgent() && charge.isDueCarrier())
                .map(CcaChargeDetailsVO::getCharge)
                .map(Money::getAmount)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();

        if ("PP".equals(ccaAwbVO.getAwbOtherChargePaymentType())) {
            qualityAuditedDetails.setTotalDueAgtPPDChg(totalAgentCharge);
            qualityAuditedDetails.setTotalDueCarPPDChg(totalCarrierCharge);
            qualityAuditedDetails.setTotalDueAgtCCFChg(0.0);
            qualityAuditedDetails.setTotalDueCarCCFChg(0.0);
        } else if ("CC".equals(ccaAwbVO.getAwbOtherChargePaymentType())) {
            qualityAuditedDetails.setTotalDueAgtCCFChg(totalAgentCharge);
            qualityAuditedDetails.setTotalDueCarCCFChg(totalCarrierCharge);
            qualityAuditedDetails.setTotalDueAgtPPDChg(0.0);
            qualityAuditedDetails.setTotalDueCarPPDChg(0.0);
        }
    }

    @Mapping(target = "ccaStatus", expression = "java(Objects.nonNull(ccaMasterVO.getCcaStatus()) ? ccaMasterVO.getCcaStatus().name() : null)")
    @Mapping(target = "statusMessage", expression = "java(ccaMasterVO.getCcaReferenceNumber() + statusMessageSuffix)")
    BulkActionEdge constructBulkActionEdge(GetCcaListMasterVO ccaMasterVO, @Context String statusMessageSuffix);

    List<BulkActionEdge> constructBulkActionEdges(List<GetCcaListMasterVO> ccaMasterVOs, @Context String statusMessageSuffix);

    CCAListViewFilterVO constructCCAListViewFilterVO(CCAListViewFilterData ccaListViewFilterData);

    Collection<CcaRateDetailsVO> awbRatingDetailEventToAwbRates(List<AwbRatingDetailEvent> awbRatingDetailEvents, @Context Units units);

    @Mapping(target = "rateDetails", source = "pricingDetails")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CcaRateDetailsVO awbRatingDetailEventToAwbRates(AwbRatingDetailEvent awbRatingDetailEvents, @Context Units units);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "dimensions", source = "awbDetailDimensionEvent")
    CcaRatingDetailVO awbDetailEventToCcaRatingDetailVO(AwbDetailEvent awbDetailEvent, @Context Units units);

    Collection<CcaRatingDetailVO> awbDetailEventToCcaRatingDetailVO(List<AwbDetailEvent> awbDetailEvent, @Context Units units);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Collection<CcaChargeDetailsVO> awbOtherChargeDetailEventToCcaChargeDetailsVO(List<AwbOtherChargeDetailEvent> awbOtherChargeDetailEvents, @Context Units units);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    Set<CcaAwbRoutingDetailsVO> awbRoutingEventToCcaAwbRoutingDetails(List<AwbRoutingEvent> awbRoutingEvent, @Context Units units);

    @Mapping(target = "autoCCAFlag", constant = "Y")
    @Mapping(target = "ccaSource", constant = AUTO_CCA_SOURCE)
    @Mapping(target = "ccaType", constant = CCA_TYPE_ACTUAL)
    @Mapping(target = "ccaRemarks", expression = "java(\"V\".equals(invoicedAWBUpdateEvent.getShipmentStatus()) ? " +
            "\"Approved, Auto CCA as part of Voiding process\" : \"Auto CCA created as part of updates from Operations\")")
    @Mapping(target = "ccaStatus", expression = "java(CcaStatus.A)")
    @Mapping(target = "ccaReason", expression = "java(\"V\".equals(invoicedAWBUpdateEvent.getShipmentStatus()) ? \"VD\" : \"05\")")
    @Mapping(target = "revisedShipmentVO", source = "invoicedAWBUpdateEvent")
    @Mapping(target = "originalShipmentVO", source = "invoicedAWBUpdateEvent")
    @Mapping(target = "executionDate", source = "executionDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    CCAMasterVO constructCCAMasterVOFromInvoicedAWBUpdateEvent(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent, @Context Units units);

    @Mapping(target = "executionDate", source = "executionDate", dateFormat = "yyyy-MM-dd'T'HH:mm:ss")
    CcaAwbVO invoicedAWBUpdateEventToCcaAwbVO(InvoicedAWBUpdateEvent invoicedAWBUpdateEvent, @Context Units units);

    @Mapping(target = "ccaIssueDate", source = "ccaIssueDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "billingPeriodTo", source = "billingPeriodTo", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "billingPeriodFrom", source = "billingPeriodFrom", dateFormat = "dd-MMM-yyyy")
    @Mapping(target = "originalShipmentVO.payType", expression = "java((StringUtils.isNotBlank(cCAAWBDetailData.getPayType()) && StringUtils.isNotBlank(cCAAWBDetailData.getAwbOtherChargePaymentType())) " +
            "? cCAAWBDetailData.getPayType().substring(0, 1).concat(cCAAWBDetailData.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "revisedShipmentVO.payType", expression = "java((StringUtils.isNotBlank(cCAAWBDetailData.getPayType()) && StringUtils.isNotBlank(cCAAWBDetailData.getAwbOtherChargePaymentType())) " +
            "? cCAAWBDetailData.getPayType().substring(0, 1).concat(cCAAWBDetailData.getAwbOtherChargePaymentType().substring(0, 1)) " +
            ": null)")
    @Mapping(target = "originalShipmentVO.shippingDate", source = "originalAWBData.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "revisedShipmentVO.shippingDate", source = "revisedAWBData.shippingDate", dateFormat = "dd-MM-yyyy")
    @Mapping(target = "originalShipmentVO.executionDate", source = "originalAWBData.executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "revisedShipmentVO.executionDate", source = "revisedAWBData.executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "revisedShipmentVO.awbQualityAuditDetail.totalNonAWBCharge", source = "revisedAWBData.totalNonAWBCharge")
    @Mapping(target = "originalShipmentVO.awbQualityAuditDetail.totalNonAWBCharge", source = "originalAWBData.totalNonAWBCharge")
    @Mapping(target = "exportBillingStatus", source = "outboundBillingStatus")
    @Mapping(target = "importBillingStatus", source = "inboundBillingStatus")
    @Mapping(target = "triggerPoint", expression = "java(TriggerPointUtil.getTriggerPoint(ccaMasterData.getCcaScreenId()))")
    @Mapping(target = "autoCCAFlag", ignore = true)
    @Mapping(target = "ccaSource", ignore = true)
    @Mapping(target = "ccaType", ignore = true)
    @Mapping(target = "ccaRemarks", ignore = true)
    @Mapping(target = "ccaStatus", ignore = true)
    @Mapping(target = "ccaReason", ignore = true)
    @Mapping(target = "documentOwnerId", ignore = true)
    @Mapping(target = "revisedShipmentVO.displayTaxDetails", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    void updateCCAMasterVOFromCCAData(@MappingTarget CCAMasterVO ccaMasterVO, CCAMasterData ccaMasterData, @Context Units units);

    @Mapping(target = "executionDate", source = "executionDate", dateFormat = "dd-MM-yyyy HH:mm:ss")
    void cCAAWBDetailDataToCcaAwbVO(@MappingTarget CcaAwbVO mappingTarget, CCAAWBDetailData cCAAWBDetailData, @Context Units units);

    @Mapping(target = "ccaIssueDate", expression = "java(Objects.nonNull(getCcaListMasterVO.getCcaIssueDate()) ? getCcaListMasterVO.getCcaIssueDate().format(DateTimeFormatter.ofPattern(\"dd-MM-yyyy\")) : null)")
    CCAMasterData constructCcaMasterDataFromGetCcaListMasterVO(GetCcaListMasterVO getCcaListMasterVO);

    CcaAwbVO constructCcaAwbVOForValidateCassIndicator(CcaCassValidationDataRequest ccaCassValidationDataRequest);

    default CCAMasterVO createCCAMasterVOFromCcaAwbVO(CcaCassValidationDataRequest ccaCassValidationDataRequest) {
        final var ccaAwbVO = constructCcaAwbVOForValidateCassIndicator(ccaCassValidationDataRequest);
        final var ccaMasterVO = new CCAMasterVO();
        ccaMasterVO.setOriginalShipmentVO(ccaAwbVO);
        ccaMasterVO.setRevisedShipmentVO(ccaAwbVO);
        return ccaMasterVO;
    }
}
